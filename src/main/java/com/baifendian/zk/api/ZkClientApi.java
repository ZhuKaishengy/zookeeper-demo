package com.baifendian.zk.api;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zhukaishengy
 * @Date: 2019/6/10 16:03
 * @Description: zk api 操作demo
 */
public class ZkClientApi {

    ZooKeeper zkClient;

    /**
     * 初始化 ZooKeeper 对象
     * @throws IOException
     */
    @Before
    public void init() throws IOException {
        Watcher watcher = watchedEvent -> {
            // 监听逻辑，监听根结点下的子节点路径变化
            Watcher.Event.EventType type = watchedEvent.getType();
            String path = watchedEvent.getPath();
            System.out.println(type + "-->" + path);
            try {
                List<String> children = zkClient.getChildren("/", true);
                System.out.println(children);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        zkClient = new ZooKeeper(ZkProperties.CONNECSTRING, ZkProperties.SESSIONTIMEOUT, watcher);
    }

    /**
     * 创建一个子节点，类型为持久型
     * ZooDefs.Ids.OPEN_ACL_UNSAFE：所有人都可访问
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void testCreateNode() throws KeeperException, InterruptedException {
        String resp = zkClient.create("/test", "zks".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(resp);
    }

    /**
     * 获取子节点并监听
     * [zks, zookeeper, test]
     * NodeChildrenChanged-->/
     */
    @Test
    public void getChildNode() throws KeeperException, InterruptedException {
        /// 在watcher中处理逻辑
        List<String> childrenNodes = zkClient.getChildren("/", true);
        System.out.println(childrenNodes);
        // 挂起程序，验证监听
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 判断 znode 是否存在
     */
    @Test
    public void ifNodeExists() throws KeeperException, InterruptedException {
        Stat stat = zkClient.exists("/test", false);
        String str = Objects.isNull(stat)? "此路径不存在" : "此路径存在";
        System.out.println(str);
    }
}
