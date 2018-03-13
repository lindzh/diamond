package com.taobao.diamond;

import com.taobao.diamond.client.DiamondEnv;
import com.taobao.diamond.manager.ManagerListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ʹ��˵��
 * 1. ��װ���� diamond-server �˿�Ҫ��8080
 * 2. ���nginx������diamond server��������diamond server ip������ {user.home}/diamond/ServerAddress
 * nginx ����������: ���� jmenv.tbsite.net �˿�:8080 doc: /diamond/dsaddr-default doc���� diamond server ip
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
