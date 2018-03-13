package com.taobao.diamond;

import com.taobao.diamond.client.DiamondEnv;
import com.taobao.diamond.manager.ManagerListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 使用说明
 * 1. 安装部署 diamond-server 端口要求8080
 * 2. 添加nginx服务发现diamond server或者配置diamond server ip到本地 {user.home}/diamond/ServerAddress
 * nginx 服务发现配置: 域名 jmenv.tbsite.net 端口:8080 doc: /diamond/dsaddr-default doc内容 diamond server ip
 */

/**
    nginx config
 server {
     listen       8080;
     server_name  jmenv.tbsite.net;

     location /diamond {
     root   hotdoc/;
     }

     location /rocketmq {
     root hotdoc/;
     }
 }

 cat hotdoc/diamond/dsaddr-default
 127.0.0.1

 */
public class DiamondExample {

    public static final String GROUP = "DEFAULT_GROUP";

    public static final String DATA_ID = "fgrgrg";

    public static final int TIMEOUT = 1000;

    public static void main(String[] args) throws InterruptedException, IOException {

        String config = DiamondEnv.getConfig(DATA_ID, GROUP, TIMEOUT);
        System.out.println("getconfig:\r\n"+config);
        DiamondEnv.addListeners(DATA_ID,GROUP, Arrays.asList(new ManagerListener() {
            public Executor getExecutor() {
                return Executors.newFixedThreadPool(3);
            }

            public void receiveConfigInfo(String configInfo) {
                System.out.println("new:\r\n"+configInfo);
            }
        }));
        Thread.sleep(30000);
    }
}
