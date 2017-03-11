package antcaves.com.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.antcaves.processor.Router;

/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/3 下午8:41
 */
@Router(path = "activity/one", param = {"one->boolean", "name->String"})
public class OneActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, getIntent().getStringExtra("name") + "", Toast.LENGTH_LONG).show();
        Toast.makeText(this, getIntent().getBooleanExtra("one", false) + "", Toast.LENGTH_LONG).show();
    }
}
