package antcaves.com.demo1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.antcaves.AntCavesRouter;
import com.antcaves.processor.Module;
import com.antcaves.processor.Router;

/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/10 上午10:14
 */
@Module(module = "demo")
@Router(path = "activity/demo")
public class DemoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
    }

    public void go(View view) {
        AntCavesRouter.getInstance().prepare(this, "demos://activity/demos").cross();

    }

    public void go1(View view) {
        AntCavesRouter.getInstance().prepare(this, "demo://activity/demo1").cross();

    }

    public void go2(View view) {
        AntCavesRouter.getInstance().prepare(this, "demo://activity/amode").go();

    }
}
