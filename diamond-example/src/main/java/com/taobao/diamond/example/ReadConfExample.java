package com.taobao.diamond.example;

import com.taobao.diamond.client.impl.DiamondEnv;

import java.io.*;

public class ReadConfExample {

    //init env
    static {
        //如果已经设置域名 jmenv.tbsite.net 就不用配置
        System.setProperty("diamond.discovery.default.domain","127.0.0.1");
        System.setProperty("diamond.discovery.daily.domain","127.0.0.1");
        //端口默认8080
        System.setProperty("diamond.discovery.port","8000");
        System.setProperty("diamond.server.port","8080");
    }

    public static void main(String[] args) throws Exception {
        //update with mvn compile
        File file = new File("diamond-example/src/conf/dev.properties");
        String simple1 = DiamondEnv.getDiamondUnitEnv("simple").getConfig("simple", "DEFAULT_GROUP", 1000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder stringBuilder = new StringBuilder();
        String line = reader.readLine();
        while(line!=null){
            stringBuilder.append(line+"\n");
            line = reader.readLine();
        }
        reader.close();
        System.out.println(simple1);
        System.out.println(stringBuilder.toString());
    }
}
