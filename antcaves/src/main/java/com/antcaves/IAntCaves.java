package com.antcaves;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author liyuan
 * @description 接口
 * @email thisuper@qq.com
 * @date 17/3/2 上午10:04
 */

public interface IAntCaves {
    /**
     * 跳转
     *
     * @return
     */
    boolean go();

    /**
     * 组装path
     * 完成对uri 进行解析，进行匹配
     *
     * @param path
     * @return
     */
    IAntCaves prepare(String path);

    /**
     * 装备字符串参数
     *
     * @param key
     * @param value
     * @return
     */
    IAntCaves equipString(String key, String value);

    /**
     * 装备整型参数
     *
     * @param key
     * @param value
     * @return
     */
    IAntCaves equipInt(String key, int value);

    /**
     * 装备长整型参数
     *
     * @param key
     * @param value
     * @return
     */
    IAntCaves equipLong(String key, long value);

    /**
     * 装备Double类型参数
     *
     * @param key
     * @param value
     * @return
     */
    IAntCaves equipDouble(String key, double value);

    /**
     * 装备char 类型参数
     *
     * @param key
     * @param value
     * @return
     */
    IAntCaves equipChar(String key, char value);

    /**
     * 装备Bundle 类型参数
     *
     * @param key
     * @param value
     * @return
     */
    IAntCaves equipExtra(String key, Bundle value);

    /**
     * 装备Serializable 类型参数
     *
     * @param key
     * @param value
     * @return
     */
    IAntCaves equipExtra(String key, Serializable value);

    /**
     * 装备 Parcelable 参数
     *
     * @param key
     * @param value
     * @return
     */
    IAntCaves equipExtra(String key, Parcelable value);

}
