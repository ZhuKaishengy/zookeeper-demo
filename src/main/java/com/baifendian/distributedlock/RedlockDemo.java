package com.baifendian.distributedlock;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/24 14:24
 * @Description: redis 官方提供redlock算法，redis实现分布式锁
 */
public class RedlockDemo {

    public static void main(String[] args) {
        // 1. Create config object
        Config config = new Config();
        config.useClusterServers()
                // use "rediss://" for SSL connection
                .addNodeAddress("redis://172.16.133.200:7000")
                .addNodeAddress("redis://172.16.133.202:7005")
                .addNodeAddress("redis://172.16.133.202:7004");

        // or read config from file
//        config = Config.fromYAML(new File("config-file.yaml"));

        // 2. Create Redisson instance

        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);

        // Reactive API
        RedissonReactiveClient redissonReactive = Redisson.createReactive(config);

        // RxJava2 API
        RedissonRxClient redissonRx = Redisson.createRx(config);

        // 3. Get Redis based Map
        RMap<String, Object> map = redisson.getMap("myMap");

        RMapReactive<String, Object> mapReactive = redissonReactive.getMap("myMap");

        RMapRx<String, Object> mapRx = redissonRx.getMap("myMap");
        // 4. Get Redis based Lock
        RLock lock = redisson.getLock("myLock");

        RLockReactive lockReactive = redissonReactive.getLock("myLock");

        RLockRx lockRx = redissonRx.getLock("myLock");

        // 4. Get Redis based ExecutorService
        RExecutorService executor = redisson.getExecutorService("myExecutorService");

        // over 30 different Redis based objects and services ...
    }
}
