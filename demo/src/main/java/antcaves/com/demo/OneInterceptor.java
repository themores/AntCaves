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
//        Intent intent = new Intent(context, AntCaves.getRouters().get("activity://two"));
//        iInterceptorCallBack.interceptor(intent);
    }
}
