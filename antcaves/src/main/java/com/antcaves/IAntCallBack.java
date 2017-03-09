package com.antcaves;

import android.content.Context;

/**
 * @author liyuan
 * @description 蚂蚁路途回传信息
 * @email thisuper@qq.com
 * @date 17/3/6 下午3:35
 */

public interface IAntCallBack {

    /**
     * 跳转失败
     *
     * @param context
     * @param message
     */
    void onLost(Context context, String message);

    /**
     * 跳转成功
     *
     * @param context
     * @param message
     */
    void onArrival(Context context, String message);

    /**
     * 被拦截
     *
     * @param context
     * @param message
     */
    void onInterceptor(Context context, String message);
}
