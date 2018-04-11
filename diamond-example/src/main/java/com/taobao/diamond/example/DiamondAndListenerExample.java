package com.taobao.diamond.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executor;
import com.taobao.diamond.client.impl.DiamondEnv;
import com.taobao.diamond.manager.ManagerListener;

/**
 * 可以使用host配置将 discoveryServer的地址配置为jmenv.tbsite.net
 */
public class DiamondAndListenerExample {

    public static void main(String[] args) throws IOException, InterruptedException {
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
        Thread.sleep(5000000);
    }
}
