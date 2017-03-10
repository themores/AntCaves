# AntCaves
Android Router 框架，取名为AntCaves，中文名”蚁穴“。
####关于名字的一个故事
说起名字的由来，得从大四那年在武汉学车的时候。记得是学科目二的时候一个下午，在学车地方看到了一个蚂蚁窝。虽然小时候经常看到蚂蚁窝，
但是经过4年计算机技术体系的学习，对软件架构有了一定的认知。于是对蚂蚁窝的建筑架构有了一定的兴趣，当时把一个蚂蚁洞口堵住，完全不影响其他洞口的工作。
不得不佩服，蚂蚁间的工作车间的解藕，分工明确。道路四通八达，互不影响,他们的架构思维远远超过大多数软件研发。于是当时就跟同学说蚁穴的架构思想。
工作契机，于是正好有机会实现这一想法。
题外话，佩服的动物莫过于蚂蚁，最佩服的人莫过于唐朝郭子仪。
####如何导入？
step1:在project-build.gradle 添加<pre>classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'</pre>

step2:￼

####如何使用？
#####1.初始化
<pre>
1.build项目
2.重写Application类，在其onCreate()方法中初始化，添加<code>AntCavesSDK.init();</code>
</pre>
#####2.说明
<pre>
关于path,必须遵循http url形式。如:module://activity/about
关于参数param,必须遵循key->type(基本数据类型+String)的形式进行规范。如:id->int,name->String,isClose->boolean
</pre>

#####3.多种方式添加path
step1:注解的方式添加
<pre>
@Router(path = "module://activity/about", param = {"id->int", "name->String"})
public class AboutActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, getIntent().getStringExtra("name") + "", Toast.LENGTH_LONG).show();
        Toast.makeText(this, getIntent().geIntExtra("id", 0) + "", Toast.LENGTH_LONG).show();
    }
}
</pre>

|         注解             |参数      |       请求        |
| :---------------------- |:---------|:-----------------|
| @Router(path="module://activity/about")|无参数 |module://activity/about |
| @Router(path="module://activity/about",param="id->int")|1个参数|module://activity/about?id=123 |
| @Router(path="module://activity/about",param={"id->int","name->String"})|多个参数|module://activity/about?id=123&name=ant

step2:代码的方式添加
<pre>(无参数)
AntCavesRouter.addRouter("module://activity/about",Activity.class)
</pre>
<pre>(有参数)
List<String> paramList = new ArrayList();
paramList.add("id->int");
paramList.add("name->String");
AntCavesRouter.addRouter("module://activity/about",paramList,Activity.class);
</pre>
#####4.最常见的跳转方式
<pre>AntCavesRouter.getInstance().prepare(Activity.this, path).go();</pre>
#####5.支持传递Object
User:
<pre>
public class User implements Serializable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
</pre>
<pre>
A->B
A:
User user = new User();
user.setId(10086);
user.setName("this is a object");
AntCavesRouter.getInstance().prepare(Activity.this, path).equipExtra("user", user).go();

B:
User user = (User) getIntent().getSerializableExtra("user");
</pre>
#####6.支持跳转事件回调
通过跳转事件回调，得知是否跳转成功/失败/被拦截
<pre>
 AntCavesRouter.getInstance().prepare(Activity.this, path).go(new IAntCallBack() {
            @Override
            public void onLost(Context context, String message) {
            }

            @Override
            public void onArrival(Context context, String message) {

            }

            @Override
            public void onInterceptor(Context context, String message) {

            }
        });
</pre>
#####7.支持startActivityForResult和setResult跳转方式
<pre>
A->B->A
A->B: int requestCode = 1;
      AntCavesRouter.getInstance().prepare(this, "activity://aba").go(requestCode);
      
B->A: setResult(RESULT_OK,intent);

A: 
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    //from B 
}
</pre>
#####8.支持添加拦截处理
自定义添加拦截器 CustomInterceptor extends Interceptor
<pre>
public class CustomInterceptor extends Interceptor {
    @Override
    public void process(Context context, String path, IInterceptorCallBack iInterceptorCallBack) {
     //do something: show Dialog,intent to another Activity etc.
   }
}
</pre>
添加拦截器
<pre>
AntCavesRouter.getInstance().prepare(Activity.this, path).addInterceptor(new CustomInterceptor()).go();
</pre>
####友盟等事件埋点太繁琐
####注意事项
1.跨module 有待测试
2.暂时不支持跨进程

####沟通联系：
加群：284430347
