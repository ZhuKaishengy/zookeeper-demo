package com.baifendian.demo.server;

import com.baifendian.demo.ZkUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @Author: zhukaishengy
 * @Date: 2019/6/10 23:20
 * @Description: 模拟service provider
 */
public class BizServer {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String hostName = args[0];
        BizServer bizServer = new BizServer();
        // connect
        ZooKeeper zooKeeper = ZkUtils.connect(watchedEvent -> {});
        // regist
        ZkUtils.regist(zooKeeper, "/servers/server", hostName, CreateMode.EPHEMERAL_SEQUENTIAL);
        // biz process
        bizServer.processBiz(hostName);
        // mock server
        Thread.sleep(Long.MAX_VALUE);
    }

    private void processBiz(String hostName) {
        System.out.println("server " + hostName + "已上线！");
    }
}
