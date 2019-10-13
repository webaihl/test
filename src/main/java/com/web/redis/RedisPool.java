package com.web.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author web
 * @title: RedisPool
 * @projectName javacode
 * @description: TODO
 * @date 19-10-13下午1:49
 */
public class RedisPool {
    private static final String host = "localhost";
    private static final Integer port = 6379;
    private static final Integer timeout = 1000 * 2;
    private static JedisPool pool;
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

        pool = new JedisPool(config, host, port, timeout);

    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis) {
        jedis.close();
    }
}
