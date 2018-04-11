#### diamond
来自淘宝diamond：http://code.taobao.org/p/diamond/src/

#### 基本架构
![](https://img-blog.csdn.net/20160801191913134)

#### 部署架构

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