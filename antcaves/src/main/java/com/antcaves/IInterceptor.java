package com.antcaves;

import android.content.Context;

/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/6 下午5:18
 */

public interface IInterceptor {
    void process(Context context, String path, IInterceptorCallBack iInterceptorCallBack);
}
