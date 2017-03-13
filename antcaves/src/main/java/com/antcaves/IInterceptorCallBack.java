package com.antcaves;

import android.content.Intent;

/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/6 下午6:31
 */

public interface IInterceptorCallBack {
    /**
     * 回调，重新定义intent
     *
     * @param intent
     */
    void interceptor(Intent intent);
}
