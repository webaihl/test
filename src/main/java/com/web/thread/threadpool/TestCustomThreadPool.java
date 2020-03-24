package com.web.thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName TestCustomThreadPool.java
 * @Description
 * @createTime 2020年03月22日 23:10:00
 */
public class TestCustomThreadPool {
    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(4);
        CustomThreadPool pool = new CustomThreadPool(3, 5, 1,
                TimeUnit.SECONDS, queue,null);
        for (int i = 0; i< 10; i++){
            pool.execute(()->{
                System.out.println("web");
            });
        }
        pool.shutDown();
    }
}
