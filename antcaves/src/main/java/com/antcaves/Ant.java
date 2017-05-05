package com.antcaves;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liyuan
 * @description 干活的小蚂蚁
 * @email thisuper@qq.com
 * @date 17/5/4 下午3:36
 */

public class Ant {
    private Context mContext;
    private Intent mIntent;
    private boolean isInterceptor = false;
    private String mPath;


    public Ant(Context context, String path) {
        this.mContext = context;
        this.mPath = path;
        initAnt(path);
    }

    private void initAnt(String path) {
        String url[] = path.split("\\?");
        mIntent = new Intent(mContext, AntCaves.getRouters().get(url[0]));
        equipString("url", path);
        parseUrl(path);
    }

    public Ant go() {
        if (!isInterceptor)
            mContext.startActivity(mIntent);
        return this;
    }

    public Ant go(int requestCode) {
        if (!isInterceptor)
            ((Activity) mContext).startActivityForResult(mIntent, requestCode);
        return this;
    }

    public Ant cross() {
        if (!isInterceptor) {
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(mIntent);
        }
        return this;
    }

    public Ant cross(int requestCode) {
        if (!isInterceptor) {
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ((Activity) mContext).startActivityForResult(mIntent, requestCode);
        }
        return this;
    }

    public Ant addInterceptor(final Interceptor iInterceptor) {
        if (iInterceptor != null)
            iInterceptor.process(mContext, mPath, new IInterceptorCallBack() {
                @Override
                public void interceptor(String path) {
                    mPath = path;
                    initAnt(path);
                }

                @Override
                public void interceptor() {
                    isInterceptor = true;
                }
            });
        return this;
    }

    public Ant equipString(String key, String value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public Ant equipInt(String key, int value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public Ant equipLong(String key, long value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public Ant equipDouble(String key, double value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public Ant equipFloat(String key, float value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public Ant equipBoolean(String key, boolean value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public Ant equipChar(String key, char value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public Ant equipExtra(String key, Bundle value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public Ant equipExtra(String key, Serializable value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public Ant equipExtra(String key, Parcelable value) {
        mIntent.putExtra(key, value);
        return this;
    }

    public Ant equipStringArrayList(String key, ArrayList<String> value) {
        mIntent.putStringArrayListExtra(key, value);
        return this;
    }

    private void parseUrl(String path) {
        if (!checkPathRole(path)) {
            ALogger.e("parseUrl", "Ant got lost: [reason] your path mismatch,please check again");
        } else {
            //第一步取出参数module://activity?name=''&value=123
            String url[] = path.split("\\?");
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
                            ALogger.e("failure:", "you lost some name or type(name->type)");
                        }
                    }

                } else {
                    ALogger.e("failure:", "you lost some parameters");
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
            } else {
                ALogger.e("success:", "there is no parameter ");
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
