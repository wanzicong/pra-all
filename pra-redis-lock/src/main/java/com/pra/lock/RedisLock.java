package com.pra.lock;

import org.redisson.Redisson;
import org.redisson.RedissonFairLock;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisLock {

    //https://www.xiaerblog.com/articles/2021/06/20/1624178582211.html
    public static void main(String[] args) {

        // 设置连接redis的配置信息 单机信息 主从信息 sentinel信息 cluster信息
        Config config = new Config();
        config.useSingleServer().setAddress("redis://47.115.42.52:6380")
                .setPassword("123456")
                .setDatabase(0)
                .setPassword("password");

        // 获取操作redis 的客户端
        RedissonClient client = Redisson.create(config);
        // 获取布隆过滤器
        RBloomFilter<Object> filter = client.getBloomFilter("key");
        // 判断是否存在 key 在布隆过滤器中
        boolean key = filter.contains("key");

        // 获取锁信息
        RLock lock = client.getLock("lock");
        //设置锁
        lock.lock();
        //释放锁
        lock.unlock();
    }
}
