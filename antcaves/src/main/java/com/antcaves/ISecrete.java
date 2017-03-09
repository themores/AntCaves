package com.antcaves;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author liyuan
 * @description 蚂蚁间的通信主要是靠分泌气味
 * @email thisuper@qq.com
 * @date 17/3/2 上午10:04
 */

public interface ISecrete {
    /**
     * 跳转
     *
     * @return
     */
    boolean go();

    /**
     * 跳转
     *
     * @param iAntCallBack
     */
    void go(IAntCallBack iAntCallBack);

    /**
     * 跳转 startActivityForResult
     *
     * @param requestCode
     * @return
     */
    boolean go(int requestCode);

    /**
     * 跳转 startActivityForResult
     *
     * @param requestCode
     * @param iAntCallBack
     */
    void go(int requestCode, IAntCallBack iAntCallBack);

    /**
     * 组装path
     * 完成对uri 进行解析，进行匹配
     *
     * @param path
     * @return
     */
    ISecrete prepare(Context context, String path);


    /**
     * 装备字符串参数
     *
     * @param key
     * @param value
     * @return
     */
    ISecrete equipString(String key, String value);

    /**
     * 装备整型参数
     *
     * @param key
     * @param value
     * @return
     */
    ISecrete equipInt(String key, int value);

    /**
     * 装备长整型参数
     *
     * @param key
     * @param value
     * @return
     */
    ISecrete equipLong(String key, long value);

    /**
     * 装备Double类型参数
     *
     * @param key
     * @param value
     * @return
     */
    ISecrete equipDouble(String key, double value);

    /**
     * Float
     *
     * @param key
     * @param value
     * @return
     */
    ISecrete equipFloat(String key, float value);

    /**
     * Boolean
     *
     * @param key
     * @param value
     * @return
     */
    ISecrete equipBoolean(String key, boolean value);

    /**
     * 装备char 类型参数
     *
     * @param key
     * @param value
     * @return
     */
    ISecrete equipChar(String key, char value);

    /**
     * 装备Bundle 类型参数
     *
     * @param key
     * @param value
     * @return
     */
    ISecrete equipExtra(String key, Bundle value);

    /**
     * 装备Serializable 类型参数
     *
     * @param key
     * @param value
     * @return
     */
    ISecrete equipExtra(String key, Serializable value);

    /**
     * 装备 Parcelable 参数
     *
     * @param key
     * @param value
     * @return
     */
    ISecrete equipExtra(String key, Parcelable value);

    /**
     * 添加拦截器
     *
     * @param iInterceptor
     * @return
     */
    ISecrete addInterceptor(Interceptor iInterceptor);


}
