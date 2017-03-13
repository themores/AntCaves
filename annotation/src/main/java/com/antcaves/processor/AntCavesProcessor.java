package com.antcaves.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * 自定义注解类
 */
@AutoService(Process.class)
@SupportedAnnotationTypes({Consts.ANNOTATION_TYPE_ROUTER})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AntCavesProcessor extends AbstractProcessor {
    private final static String MATCH_ROLE = "[a-zA-z]+://[^\\s]*";
    private Elements elements;
    private Types types;
    private TypeMirror typeMirror = null;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elements = processingEnv.getElementUtils();
        types = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }

        Set<? extends Element> routeElements = roundEnv.getElementsAnnotatedWith(Router.class);
        TypeElement typeElement = elements.getTypeElement(Consts.ACTIVITY);
        Map<String, ClassName> map = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        String moduleName = "";
        for (Element element : routeElements) {
            typeMirror = element.asType();
            if (types.isSubtype(typeMirror, typeElement.asType())) {
                Router router = element.getAnnotation(Router.class);
                if (!router.module().equals(""))
                    moduleName = router.module();
                if (checkPathRole(moduleName + "://" + router.path())) {
                    StringBuffer keys = new StringBuffer();
                    for (int i = 0; i < router.param().length; i++) {
                        if (i < router.param().length - 1) {
                            keys.append(router.param()[i] + "@");
                        } else {
                            keys.append(router.param()[i]);
                        }
                    }
                    if (!router.path().equals(""))
                        map.put(moduleName + "://" + router.path(), ClassName.get((TypeElement) element));
                    if (!keys.equals(""))
                        param.put(moduleName + "://" + router.path(), new String(keys));
                    debug("success:[" + element.getSimpleName() + "] path=" + router.path());
                } else {
                    debug("failure:[" + element.getSimpleName() + "] Your path does not conform to the rules,you must change your path(module://activity)!");
                }
            } else {
                debug("failure:not find activity");
            }
        }
        boolean isModules = false;
        Set<String> modules = new HashSet<>();
        Set<? extends Element> modulesElement = roundEnv.getElementsAnnotatedWith(Modules.class);
        for (Element element : modulesElement) {
            Modules modules1 = element.getAnnotation(Modules.class);
            for (int i = 0; i < modules1.module().length; i++) {
                modules.add(modules1.module()[i]);
            }
            isModules = true;
        }

        if (isModules) {
            //判断module
            initSDK(modules);
            generateAntCaves(moduleName, map, param);
        } else {
            generateAntCaves(moduleName, map, param);
        }

        return true;
    }

    private void generateAntCaves(String moduleName, Map<String, ClassName> annoationMaps, Map<String, String> params) {
        MethodSpec.Builder paramsBuilder = MethodSpec.methodBuilder("init");
        paramsBuilder.addModifiers(Modifier.STATIC, Modifier.FINAL, Modifier.PUBLIC);
        Set<String> paramKeys = params.keySet();
        for (String key : paramKeys) {
            paramsBuilder.addStatement("com.antcaves.AntCaves.getParams().put($S,$S)", key, params.get(key));
        }
        Set<String> keys = annoationMaps.keySet();
        for (String key : keys) {
            paramsBuilder.addStatement("com.antcaves.AntCaves.getRouters().put($S,$T.class)", key, annoationMaps.get(key));
        }
        MethodSpec getParams = paramsBuilder
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(Void.TYPE)
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("AntCavesSDK_" + moduleName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(getParams)
                .build();
        JavaFile javaFile = JavaFile.builder("com.antcaves", typeSpec).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initSDK(Set<String> moduleNames) {
        MethodSpec.Builder moduleBuilder = MethodSpec.methodBuilder("init");
        moduleBuilder.addModifiers(Modifier.STATIC, Modifier.FINAL, Modifier.PUBLIC);
        for (String moduleName : moduleNames) {
            moduleBuilder.addStatement("com.antcaves.AntCavesSDK_" + moduleName + ".init()");
        }
        MethodSpec antMd = moduleBuilder
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(Void.TYPE)
                .build();
        TypeSpec cavesSDK = TypeSpec.classBuilder("AntCavesSDK")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(antMd)
                .build();
        JavaFile file = JavaFile.builder("com.antcaves", cavesSDK).build();
        try {
            file.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 检查path 是否规则有效
     *
     * @param path
     * @return
     */
    private boolean checkPathRole(String path) {
        Pattern pattern = Pattern.compile(MATCH_ROLE);
        Matcher matcher = pattern.matcher(path);
        return matcher.matches();
    }

    /**
     * 打印日志
     *
     * @param log
     */
    private void debug(String log) {
        messager.printMessage(Diagnostic.Kind.NOTE, log);
    }
}
