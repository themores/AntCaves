package antcaves.com.demo;

import android.content.Context;

import com.antcaves.IInterceptorCallBack;
import com.antcaves.Interceptor;


/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/6 下午6:49
 */

public class OneInterceptor extends Interceptor {
    @Override
    public void process(Context context, String path, IInterceptorCallBack iInterceptorCallBack) {
        iInterceptorCallBack.interceptor("app://activity/two?two=123&open=false");
    }
}
