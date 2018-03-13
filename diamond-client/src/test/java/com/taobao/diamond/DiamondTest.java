package com.taobao.diamond;

import com.taobao.diamond.manager.ManagerListener;
import com.taobao.diamond.manager.impl.DefaultDiamondManager;

import java.util.concurrent.Executor;

public class DiamondTest {

    public static void main(String[] args) throws InterruptedException {
        DefaultDiamondManager defaultDiamondManager = new DefaultDiamondManager("DEFAULT_GROUP", "fgrgrg", new ManagerListener() {
            public Executor getExecutor() {
                return null;
            }

            public void receiveConfigInfo(String configInfo) {
                System.out.println("new:"+configInfo);
            }
        });
        String configureInfomation = defaultDiamondManager.getConfigureInfomation(1000);
        System.out.println(configureInfomation);
        Thread.sleep(1000000);
    }

}
