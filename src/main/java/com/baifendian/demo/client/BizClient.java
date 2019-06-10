package com.baifendian.demo.client;

import com.baifendian.demo.ZkUtils;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhukaishengy
 * @Date: 2019/6/10 23:20
 * @Description: 模拟 service invoker
 */
public class BizClient {

    private static ZooKeeper zooKeeper;
    private static volatile List<String> serverList = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        // connect and listen
        zooKeeper = ZkUtils.connect(watchedEvent -> {
            try {
                List<String> nodes = zooKeeper.getChildren("/servers", true);
                List<String> servers = new ArrayList<>();
                for (String node : nodes) {
                    String data = new String(zooKeeper.getData("/servers/" + node, false, null));
                    servers.add(data);
                }
                serverList = servers;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        BizClient bizClient = new BizClient();
        // biz process
        bizClient.processBiz();
        // mock server
        Thread.sleep(Long.MAX_VALUE);
    }

    private void processBiz() {
        System.out.println(serverList);
    }
}
