package com.antcaves;

import android.content.Context;

/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/6 下午5:18
 */

public interface IInterceptor {
    /**
     * 拦截器处理
     *
     * @param context
     * @param path
     * @param iInterceptorCallBack
     */
    void process(Context context, String path, IInterceptorCallBack iInterceptorCallBack);
}
