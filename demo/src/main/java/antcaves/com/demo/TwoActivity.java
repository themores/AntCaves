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
 * @date 17/3/3 下午9:02
 */
@Router(path = "activity/two")
public class TwoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, getIntent().getStringExtra("two") + "", Toast.LENGTH_LONG).show();
        Toast.makeText(this, getIntent().getBooleanExtra("open", true) + "", Toast.LENGTH_LONG).show();
    }
}
