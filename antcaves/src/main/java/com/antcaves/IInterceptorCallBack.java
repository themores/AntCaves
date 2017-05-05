package com.antcaves;

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
     * @param path
     */
    void interceptor(String path);

    /**
     * 无参数拦截
     */
    void interceptor();
}
