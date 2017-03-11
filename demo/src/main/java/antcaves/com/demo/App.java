package antcaves.com.demo;

import android.app.Application;

import com.antcaves.AntCavesSDK;
import com.antcaves.processor.Modules;


/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/9 上午10:12
 */
@Modules(module = {"app", "demo"})
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AntCavesSDK.init();
    }
}
