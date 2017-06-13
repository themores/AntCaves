package antlite.com.demo2;

import android.app.Activity;
import android.os.Bundle;

import com.antcaves.processor.Module;
import com.antcaves.processor.Router;

/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/6/12 下午4:09
 */
@Module(module = "demoq")
@Router(module = "demos", path = "activity/demos")
public class Demo2Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
