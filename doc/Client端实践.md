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

说明：nginx的默认访问地址是`http://jmenv.tbsite.net:8080/diamond/dsaddr_default`，如果如果不想通过该域名或者想通过ip的方式访问请设置相关参数，相关参数如下：

```java
System.setProperty("diamond.discovery.default.domain","127.0.0.1");
System.setProperty("diamond.discovery.daily.domain","127.0.0.1");
System.setProperty("diamond.discovery.port","8000");
System.setProperty("diamond.server.port","8080");
```

参数说明：如果修改这些参数，请在初始化Diamond-client之前设置好

```
diamond.discovery.default.domain nginx域名或者ip(发布环境)，默认jmenv.tbsite.net
diamond.discovery.daily.domain nginx域名或者ip(日常环境)，默认jmenv.tbsite.net
diamond.discovery.port nginx 服务发现的端口，默认8080，如有需要请修改
diamond.server.port Diamond-Server的端口，默认8080，如有需要请修改
```