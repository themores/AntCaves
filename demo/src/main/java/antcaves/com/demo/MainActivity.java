package antcaves.com.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.antcaves.AntCaves;
import com.antcaves.AntCavesRouter;
import com.antcaves.processor.Router;

import java.util.ArrayList;
import java.util.List;

;

@Router(path = "activity/main",
        param = {"id->int", "name->String", "price->double"})
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> params = new ArrayList<>();
        params.add("two->String");
        params.add("open->boolean");
        AntCaves.addRouter("app://activity/two", params, TwoActivity.class);
    }


    public void go(View view) {
        AntCavesRouter.getInstance().prepare(this, "app://activity/one?one=true&name=liyuan").go();
    }

    public void go1(View view) {
        AntCavesRouter.getInstance().prepare(this, "app://activity/two?two=women&open=false").go();
    }

    public void go2(View view) {
        User user = new User();
        user.setId(10086);
        user.setName("this is a object");
        AntCavesRouter.getInstance().prepare(this, "app://activity/three").equipExtra("user", user).go();
    }

    public void go3(View view) {
        AntCavesRouter.getInstance().prepare(this, "app://activity/aba").addInterceptor(new OneInterceptor()).go(1);
    }

    public void go4(View view) {
        AntCavesRouter.getInstance().prepare(this, "demo://activity/demo").cross();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult", "onActivityResult");
        if (resultCode == RESULT_OK)
            Toast.makeText(this, "requestCode=" + requestCode + "resultCode=" + resultCode, Toast.LENGTH_LONG).show();
    }
}
