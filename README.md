# AntCaves
Android Router 框架，取名为AntCaves，中文名”蚁穴“。


 <img src="https://github.com/themores/AntCaves/raw/master/img/IMG_3063(20170311-183321).jpg" width = "360" height = "480" align=center />
####关于名字的一个故事
说起名字的由来，得从大四那年在武汉学车的时候。记得是学科目二的时候一个下午，在学车地方看到了一个蚂蚁窝。虽然小时候经常看到蚂蚁窝，
但是经过4年计算机技术体系的学习，对软件架构有了一定的认知。于是对蚂蚁窝的建筑架构有了一定的兴趣，当时把一个蚂蚁洞口堵住，完全不影响其他洞口的工作。
不得不佩服，蚂蚁间的工作车间的解藕，分工明确。道路四通八达，互不影响,他们的架构思维远远超过大多数软件研发。于是当时就跟同学说蚁穴的架构思想。
工作契机，于是正好有机会实现这一想法。
题外话，佩服的动物莫过于蚂蚁，最佩服的人莫过于唐朝郭子仪。
####如何导入？
step1:在project build.gradle 文件中添加如下仓库
<pre>
allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
</pre>
step2:￼在项目module中build.gradle 添加如下引用(如果是多module 的情况下，在不同的module下同样添加以下引入)
<pre>
  compile 'com.github.themores.AntCaves:antcaves:lastVersion'
  compile 'com.github.themores.AntCaves:annotation:lastVersion'
</pre>
其中最新版本lastVersion = 1.0.6
####如何使用？
#####1.初始化
<pre>
1.注册module
在module中，常见为app_module,在Application类或者新建一个类，添加注解。
@Modules(module = "app")
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
同时在主module 上的任意一个activity 上添加改注解module 的名字 
@Router(module = "app", path = "activity/about")
2.build项目
3.重写Application类，在其onCreate()方法中初始化，添加<code>AntCavesSDK.init();</code>
</pre>
#####2.说明
<pre>
关于path,必须遵循http url形式。如:module://activity/about
关于参数param,必须遵循key->type(基本数据类型+String)的形式进行规范。如:id->int,name->String,isClose->boolean
</pre>

#####3.多种方式添加path
step1:注解的方式添加
<pre>
@Router(path = "activity/about", param = {"id->int", "name->String"})
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
| @Router(path="activity/about")|无参数 |module://activity/about |
| @Router(path="activity/about",param="id->int")|1个参数|module://activity/about?id=123 |
| @Router(path="activity/about",param={"id->int","name->String"})|多个参数|module://activity/about?id=123&name=ant

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
#####9.支持多module方式
1.注册多module
<pre>在主module中，常见为app_module,在Application类或者新建一个类，添加注解。
@Modules(module = {"app", "demo"})
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
</pre>
同时在主module 上的任意一个activity 上添加改注解module 的名字 
<pre>
@Router(module = "app", path = "activity://aba")
</pre>
同样在其他module 上的任意activity 上添加改注解module 的名字 
<pre>
@Router(module = "demo", path = "activity/demo")
</pre>
####迭代优化
1.暂时不支持跨进程
2.日志打印优化
3.代码规范优化
####感谢
感谢ActivityRouter作者曹神，ARouter作者Alex.
####联系：
个人邮箱：thisuper@163.com
加群沟通：284430347
