package antcaves.com.demo;

import android.app.Application;

import com.antcaves.AntCavesSDK;


/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/9 上午10:12
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AntCavesSDK.init();
    }
}
