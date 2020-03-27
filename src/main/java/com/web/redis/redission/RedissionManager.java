package com.web.redis.redission;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName RedissionManager.java
 * @Description
 * @createTime 2020年03月24日 21:56:00
 */
@Slf4j
public class RedissionManager {

    private static Config config = new Config();
    private static Redisson redisson = null;

    public Redisson getRedisson() {
        return redisson;
    }

    private static final String host = "192.168.0.235";
    private static final Integer port = 6379;

    private static final String host_2 = "192.168.0.235";
    private static final Integer port_2 = 6380;


    static {
        try {
            config.useSingleServer().setAddress("redis://"+host + ":" + port);
            redisson = (Redisson) Redisson.create(config);
            log.info("Rdission初始化成功!");
        } catch (Exception e) {
            log.error("redission init error!",e);
        }
    }

//    @PostConstruct
//    private void init(){
//
//        try {
//            config.useSingleServer().setAddress(host + ":" + port);
//            redisson = (Redisson) Redisson.create(config);
//            log.info("Rdission初始化成功!");
//        } catch (Exception e) {
//            log.error("redission init error!");
//        }
//    }



}
