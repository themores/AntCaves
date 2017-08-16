package com.antcaves;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liyuan
 * @description 蚁穴整个router控制
 * @email thisuper@qq.com
 * @date 17/3/2 上午10:02
 */

public class AntCavesRouter implements ISecrete {
    private final static AntCavesRouter antCavesRouter = new AntCavesRouter();
    private Context mContext;
    private Intent intent = null;
    private String path;
    private boolean isInterceptor = false;

    private AntCavesRouter() {

    }

    public static AntCavesRouter getInstance() {
        return antCavesRouter;
    }


    @Override
    public boolean go() {
        if (intent == null && mContext == null) {
            return false;
        } else {
            try {
                mContext.startActivity(intent);
                intent = null;
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean go(int requestCode) {
        if (intent == null && mContext == null) {
            return false;
        } else {
            try {
                ((Activity) mContext).startActivityForResult(intent, requestCode);
                intent = null;
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public void go(IAntCallBack iAntCallBack) {
        if (intent == null && mContext == null) {
            if (isInterceptor) {
                iAntCallBack.onInterceptor(mContext, "Ant got lost: [reason] you add interceptor");
            } else {
                iAntCallBack.onLost(mContext, "Ant got lost: [reason] your path mismatch,please check again");
            }
        } else {
            try {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                intent = null;
                iAntCallBack.onArrival(mContext, "Ant found the way");
            } catch (Exception e) {
                iAntCallBack.onLost(mContext, "Ant got lost :[reason] " + e.getMessage());
            }
        }
    }

    @Override
    public void go(int requestCode, IAntCallBack iAntCallBack) {
        if (intent == null && mContext == null) {
            if (isInterceptor) {
                iAntCallBack.onInterceptor(mContext, "Ant got lost: [reason] you add interceptor");
            } else {
                iAntCallBack.onLost(mContext, "Ant got lost: [reason] your path mismatch,please check again");
            }
        } else {
            try {
                ((Activity) mContext).startActivityForResult(intent, requestCode);
                intent = null;
                iAntCallBack.onArrival(mContext, "Ant found the way");
            } catch (Exception e) {
                iAntCallBack.onLost(mContext, "Ant got lost :[reason] " + e.getMessage());
            }
        }
    }

    @Override
    public ISecrete prepare(Context context, String path) {
        this.mContext = context;
        this.path = path;
        String url[] = path.split("\\?");
        if (url.length > 0) {
            String toUrl = url[0].trim();
            if (AntCaves.getRouters().get(toUrl) == null) {
                Log.e("failure:", "your path mismatch,please check again");
            } else {
                intent = new Intent(mContext, AntCaves.getRouters().get(toUrl));
                if (!(context instanceof Activity))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                equipString("url", path);
                parseUrl(path);
            }

        }
        return antCavesRouter;
    }


    @Override
    public ISecrete equipString(String key, String value) {
        intent.putExtra(key, value);
        return antCavesRouter;
    }

    @Override
    public ISecrete equipInt(String key, int value) {
        intent.putExtra(key, value);
        return antCavesRouter;
    }

    @Override
    public ISecrete equipLong(String key, long value) {
        intent.putExtra(key, value);
        return antCavesRouter;
    }

    @Override
    public ISecrete equipDouble(String key, double value) {
        intent.putExtra(key, value);
        return antCavesRouter;
    }

    @Override
    public ISecrete equipFloat(String key, float value) {
        intent.putExtra(key, value);
        return antCavesRouter;
    }

    @Override
    public ISecrete equipBoolean(String key, boolean value) {
        intent.putExtra(key, value);
        return antCavesRouter;
    }

    @Override
    public ISecrete equipChar(String key, char value) {
        intent.putExtra(key, value);
        return antCavesRouter;
    }

    @Override
    public ISecrete equipExtra(String key, Bundle value) {
        intent.putExtra(key, value);
        return antCavesRouter;
    }

    @Override
    public ISecrete equipExtra(String key, Serializable value) {
        intent.putExtra(key, value);
        return antCavesRouter;
    }

    @Override
    public ISecrete equipExtra(String key, Parcelable value) {
        intent.putExtra(key, value);
        return antCavesRouter;
    }

    @Override
    public ISecrete addInterceptor(final Interceptor iInterceptor) {
        if (iInterceptor != null)
            iInterceptor.process(mContext, path, new IInterceptorCallBack() {
                @Override
                public void interceptor(Intent intent) {
                    antCavesRouter.intent = intent;
                    equipString("url", path);
                    isInterceptor = true;
                    parseUrl(path);
                }
            });
        return antCavesRouter;
    }

    private void parseUrl(String path) {
        if (!checkPathRole(path.replace(" ", "%20"))) {
            Log.e("parseUrl", "Ant got lost: [reason] your path mismatch,please check again");
        } else {
            //第一步取出参数module://activity?name=''&value=123
            String url[] = path.split("\\?");
            Log.e("success:", url.length + "");
            if (url.length == 2) {
                //取出path中的key值
                String keys = AntCaves.getParams().get(url[0]);
                String key[] = keys.split("@");
                String params = url[1];//取出问号后面的字符串
                String param[] = params.split("&");
                Map<String, String> keyMap = new HashMap<>();
                Map<String, String> paramMap = new HashMap<>();
                if (key.length == param.length) {
                    for (int i = 0; i < key.length; i++) {
                        String kv[] = key[i].split("->");
                        String p_kv[] = param[i].split("=");
                        if (kv.length == 2) {
                            keyMap.put(kv[0], kv[1]);
                            paramMap.put(p_kv[0], p_kv[1]);
                        } else {
                            Log.e("failure:", "you lost some name or type(name->type)");
                        }
                    }

                } else {
                    Log.e("failure:", "you lost some parameters");
                }
                Set<String> keySet = keyMap.keySet();
                Set<String> paramSet = paramMap.keySet();
                for (String keyStr : keySet) {
                    for (String paramKeyStr : paramSet) {
                        if (keyStr.equals(paramKeyStr)) {
                            if (keyMap.get(keyStr).equals("int")) {
                                equipInt(keyStr, Integer.parseInt(paramMap.get(paramKeyStr)));
                            }
                            if (keyMap.get(keyStr).equals("String")) {
                                equipString(keyStr, paramMap.get(paramKeyStr));
                            }
                            if (keyMap.get(keyStr).equals("double")) {
                                equipDouble(keyStr, Double.parseDouble(paramMap.get(paramKeyStr)));
                            }
                            if (keyMap.get(keyStr).equals("float")) {
                                equipFloat(keyStr, Float.parseFloat(paramMap.get(paramKeyStr)));
                            }
                            if (keyMap.get(keyStr).equals("long")) {
                                equipLong(keyStr, Long.parseLong(paramMap.get(paramKeyStr)));
                            }
                            if (keyMap.get(keyStr).equals("char")) {

                            }
                            if (keyMap.get(keyStr).equals("boolean")) {
                                equipBoolean(keyStr, Boolean.parseBoolean(paramMap.get(paramKeyStr)));

                            }
                        }

                    }

                }
//
//                for (int i = 0; i < param.length; i++) {
//                    String key_value[] = param[i].split("=");
//                    if (key_value.length == 2) {
//                        if (key_value[1].startsWith("'") && key_value[1].endsWith("'")) {
//                            equipString(key_value[0], key_value[1]);
//                        } else {
//                            if (key_value[1].equals("false") || key_value.equals("true")) {
//                                equipBoolean(key_value[0], Boolean.parseBoolean(key_value[1]));
//                            }
//                            if (key_value[1].endsWith("L")) {
//                                equipLong(key_value[0], Long.parseLong(key_value[1].replace("L", "")));
//                            } else if (key_value[1].endsWith("D")) {
//                                equipDouble(key_value[0], Double.parseDouble(key_value[1].replace("D", "")));
//                            } else if (key_value[1].endsWith("F")) {
//                                equipFloat(key_value[0], Float.parseFloat(key_value[1].replace("F", "")));
//                            } else {
//                                equipInt(key_value[0], Integer.parseInt(key_value[1]));
//                            }
//
//                        }
//                    } else {
//                        Log.e("failure:", "parameter cannot be null");
//                    }
//                }
            } else {
                Log.e("success:", "there is no parameter ");
            }
        }

    }

    /**
     * 检查path 是否规则有效
     *
     * @param path
     * @return
     */
    private boolean checkPathRole(String path) {
        String MATCH_ROLE = "[a-zA-z]+://[^\\s]*";
        Pattern pattern = Pattern.compile(MATCH_ROLE);
        Matcher matcher = pattern.matcher(path);
        return matcher.matches();
    }
}
