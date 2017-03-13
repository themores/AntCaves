package com.antcaves;

import android.util.Log;

/**
 * @author liyuan
 * @description 打印日志类
 * @email thisuper@qq.com
 * @date 17/3/13 下午8:21
 */

public class ALogger {
    private static boolean DEBUG = BuildConfig.DEBUG;

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }
}
