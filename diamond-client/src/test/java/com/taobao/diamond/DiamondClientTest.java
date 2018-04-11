package com.taobao.diamond;


import com.taobao.diamond.client.impl.DiamondEnv;
import com.taobao.diamond.manager.ManagerListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executor;

public class DiamondClientTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("diamond.discovery.default.domain","127.0.0.1");
        System.setProperty("diamond.discovery.daily.domain","127.0.0.1");
        System.setProperty("diamond.discovery.port","8000");
        System.setProperty("diamond.server.port","8080");

        String simple1 = DiamondEnv.defaultEnv.getConfig("simple", "DEFAULT_GROUP", 1000);
        System.out.println(simple1);
        System.out.println("----------------------------");
        simple1 = DiamondEnv.getDiamondUnitEnv("simple").getConfig("simple", "DEFAULT_GROUP", 1000);
        System.out.println(simple1);
        DiamondEnv.getDiamondUnitEnv("simple").addListeners("simple","DEFAULT_GROUP", Arrays.asList(new ManagerListener() {
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
