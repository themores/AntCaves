package antcaves.com.demo1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.antcaves.processor.Router;

/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/10 上午10:14
 */
@Router(module = "demo", path = "activity/demo")
public class DemoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
