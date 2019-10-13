package com.web.redis;

import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;

/**
 * @author web
 * @title: RedisSharedPool
 * @projectName javacode
 * @description: TODO
 * @date 19-10-13下午1:49
 */
public class RedisSharedPool {
    private static final String host = "localhost";
    private static final Integer port = 6379;
    private static final Integer timeout = 1000 * 2;

    private static final String host_2 = "localhost";
    private static final Integer port_2 = 6380;

    private static ShardedJedisPool pool;
    private static Integer maxTotal = 10;
    private static Integer maxIdel = 5;
    private static Integer minIdel = 2;
    private static Boolean testOnBorrow = true; //连接的可用性验证
    private static Boolean testOnReturn = true;//所还连接可用性的验证


    static {
        initPool();
    }

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdel);
        config.setMinIdle(minIdel);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);//连接耗尽 阻塞（true） 直接抛出异常(false)

        JedisShardInfo info_1 = new JedisShardInfo(host, port, timeout);
        JedisShardInfo info_2 = new JedisShardInfo(host_2, port_2, timeout);

        ArrayList<JedisShardInfo> jedisShardInfos = new ArrayList<>();
        jedisShardInfos.add(info_1);
        jedisShardInfos.add(info_2);

        pool = new ShardedJedisPool(config, jedisShardInfos,
                Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    public static ShardedJedis getJedis() {
        return pool.getResource();
    }

    public static void returnResource(ShardedJedis jedis) {
        jedis.close();
    }

    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
        for (int i=0;i<10;i++){
            jedis.set("key"+i, "value"+i);
        }

        returnResource(jedis);
        pool.destroy();
    }
}
