package antcaves.com.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.antcaves.processor.Router;



/**
 * @author liyuan
 * @description
 * @email thisuper@qq.com
 * @date 17/3/8 上午11:27
 */
@Router(path = "activity://aba")
public class ABAActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final LinearLayout layout = new LinearLayout(this);
        TextView userInfo = new TextView(this);
        layout.addView(userInfo);
        setContentView(layout);
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

}
