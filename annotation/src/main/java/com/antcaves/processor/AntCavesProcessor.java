package com.antcaves.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
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

import static com.antcaves.processor.Consts.ANNOTATION_TYPE_ROUTER;

/**
 * 自定义注解类
 */
@AutoService(Process.class)
@SupportedAnnotationTypes({ANNOTATION_TYPE_ROUTER})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AntCavesProcessor extends AbstractProcessor {
    private final static String MATCH_ROLE = "[a-zA-z]+://[^\\s]*";
    private Elements elements;
    private Types types;
    private TypeMirror typeMirror = null;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elements = processingEnv.getElementUtils();
        types = processingEnv.getTypeUtils();
        filer = processingEnv.getFiler();
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
        for (Element element : routeElements) {
            typeMirror = element.asType();
            if (types.isSubtype(typeMirror, typeElement.asType())) {
                Router router = element.getAnnotation(Router.class);
                if (checkPathRole(router.path())) {
                    StringBuffer keys = new StringBuffer();
                    for (int i = 0; i < router.param().length; i++) {
                        System.out.println("keys=" + router.param()[i]);
                        if (i < router.param().length - 1) {
                            keys.append(router.param()[i] + "@");
                        } else {
                            keys.append(router.param()[i]);
                        }
                    }
                    map.put(router.path(), ClassName.get((TypeElement) element));
                    param.put(router.path(), new String(keys));
                    System.out.println("success:[" + element.getSimpleName() + "] path=" + router.path());
                } else {
                    System.out.println("failure:[" + element.getSimpleName() + "] Your path does not conform to the rules,you must change your path(module://activity)!");
                }
            } else {
                System.out.println("failure:not find activity");
            }
        }
        generateAntCaves(map, param);
        return true;
    }

    private void generateAntCaves(Map<String, ClassName> annoationMaps, Map<String, String> params) {
//        ClassName map = ClassName.get("java.util", "Map");
//        ClassName list = ClassName.get("java.util", "List");
//        ClassName hashMap = ClassName.get("java.util", "HashMap");
//        ClassName string = ClassName.get("java.lang", "String");
//        ClassName clazzNm = ClassName.get("java.lang", "Class");
//        TypeName maps = ParameterizedTypeName.get(map, string, clazzNm);
//        TypeName arrayList = ParameterizedTypeName.get(list, string);
//        TypeName hashMaps = ParameterizedTypeName.get(hashMap, string, clazzNm);
//        TypeName mapsString = ParameterizedTypeName.get(map, string, string);
//
//
//        TypeName paramsMaps = ParameterizedTypeName.get(map, string, string);
//        TypeName paramsHashMaps = ParameterizedTypeName.get(hashMap, string, string);
//
//        FieldSpec fieldSpec = FieldSpec.builder(maps, "mRouterTable")
//                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
//                .initializer("new $T()", hashMaps)
//                .build();
//        FieldSpec paramField = FieldSpec.builder(paramsMaps, "mRouterParamTable")
//                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
//                .initializer("new $T()", paramsHashMaps)
//                .build();
//        ParameterSpec path = ParameterSpec.builder(String.class, "path").build();
//        ParameterSpec clazz = ParameterSpec.builder(Class.class, "clazz").build();
//        ParameterSpec param = ParameterSpec.builder(arrayList, "params").build();
//        MethodSpec addRouterNoparam = MethodSpec.methodBuilder("addRouter")
//                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                .returns(Void.TYPE)
//                .addParameter(path)
//                .addParameter(clazz)
//                .addStatement("mRouterTable.put($N,$N)", "path", "clazz")
//                .build();
//
//        MethodSpec addRouter = MethodSpec.methodBuilder("addRouter")
//                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                .returns(Void.TYPE)
//                .addParameter(path)
//                .addParameter(param)
//                .addParameter(clazz)
//                .addStatement("mRouterTable.put($N,$N)", "path", "clazz")
//                .addStatement("String paramStr=\"\"")
//                .addStatement("for(int i=0;i<params.size();i++){\nif(i<params.size()-1)\n{ paramStr+=params.get(i)+\"@\";}\nelse\n{ paramStr+=params.get(i);}}")
//                .addStatement("mRouterParamTable.put($N,$N)", "path", "paramStr")
//                .build();
//        MethodSpec.Builder builder = MethodSpec.methodBuilder("initRouters");
//
//        MethodSpec getRouters = builder
//                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                .returns(maps)
//                .addStatement("return $N", "mRouterTable")
//                .build();
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

        TypeSpec typeSpec = TypeSpec.classBuilder("AntCavesSDK")
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
}
