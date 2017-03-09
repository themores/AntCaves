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

#####3.添加path方式
step1:注解的方式添加

|         注解             |参数      |       请求        |
| :---------------------- |:---------|:-----------------|
| @Router(path="module://activity/about")|无参数 |module://activity/about |
| @Router(path="module://activity/about",params="id->int")|1个参数|module://activity/about?id=123 |
| @Router(path="module://activity/about",params={"id->int","name->String"})|多个参数|module://activity/about?id=123&name=ant

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
####友盟事件埋点太繁琐
