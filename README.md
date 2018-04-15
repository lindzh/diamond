#### diamond
来自淘宝diamond：http://code.taobao.org/p/diamond/src/

#### 基本架构
![](https://img-blog.csdn.net/20160801191913134)

#### 部署架构
![](http://processon.com/chart_image/5ad34130e4b04721d61a8d4c.png)

1. Diamond Server : DiamondServer为整个Diamond的后端服务，底层存储分为两层存储，第一层Mysql存储，第二层本次磁盘。当新建配置或者更新配置时，先存到Mysql中，然后持久化到本地磁盘。同时，会有异步线程不断从Mysql将数据捞取出来存本地磁盘。这样当数据库宕机时不会导致已有的服务异常和数据丢失。
2. Nginx：Nginx在整个架构中作为服务发现的核心，Nginx通过手动配置Hotdoc的方式对Diamond提供服务发现，配置Hotdoc的方式是将DiamondServer列表写入Nginx的文件系统中，在Diamond client来访问时直接返回文件即可。
3. Diamond Client：Diamond Client提供了业务获取配置，监听配置更新的客户端功能。交互流程是：业务获取配置时通过unit（多机房单元化）获取到nginx上的不同hotdoc得到不同机房的DiamondServer列表，通过访问Diamond Server获取数据。当监听数据的变化时，Diamond Client会将监听的数据加入到本地Subscriber中，Subscriber通过异步的方式轮训获取配置的变化情况（MD5对比），有变化回调业务Listener。

#### 客户端入门

```
 String simple1 = DiamondEnv.defaultEnv.getConfig("simple", "DEFAULT_GROUP", 1000);
 System.out.println(simple1);
 DiamondEnv.defaultEnv.addListeners("simple","DEFAULT_GROUP", Arrays.asList(new ManagerListener() {
     @Override
     public Executor getExecutor() {
         return null;
     }
     @Override
     public void receiveConfigInfo(String configInfo) {
         System.out.println("configChanged:"+configInfo);
     }
 }));
```

#### 本库修复的问题
1. 【bug】修正listener通知的bug
2. 【bug】多单元支持问题
3. 【bug】修复nginx配置发现diamond server列表支持
4. 【新增】支持diamond配置后，maven插件打包

#### 服务端部署流程
1. 部署diamond server
2. 部署nginx服务发现
3. 管理控制台配置

#### 文档待更新

Doc：https://github.com/lindzh/diamond/tree/master/doc

http://www.iteye.com/news/28294

https://blog.csdn.net/szwandcj/article/details/51165954

#### 如何参与项目
1. 参与讨论或者报问题：https://github.com/lindzh/diamond/issues
2. 参与开发：https://github.com/lindzh/diamond/pulls

#### RoadMap
1. newconsole
2. spring config support
2. spring version update
3. raft
