package com.baifendian.demo;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @Author: zhukaishengy
 * @Date: 2019/6/10 18:33
 * @Description:
 */
public class ZkUtils {

    /**
     * 连接zookeeper
     * @return
     * @throws IOException
     */
    public static ZooKeeper connect(Watcher watcher) throws IOException {
        return new ZooKeeper(ZkProperties.CONNECSTRING, ZkProperties.SESSIONTIMEOUT, watcher);
    }

    /**
     * 向zookeeper上注册服务
     * @param zooKeeper
     */
    public static void regist(ZooKeeper zooKeeper, String path, String data, CreateMode createMode) throws KeeperException, InterruptedException {
        zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
    }
}
