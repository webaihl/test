package com.web.concurrent;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author web
 * @title: CountDownLatchDemo
 * @projectName javacode
 * @description: TODO
 * @date 19-9-11下午12:23
 */

@Slf4j
public class CountDownLatchDemo {

    private static final org.slf4j.Logger LOGGER = log;

    public static void main(String[] args) throws Exception {
        //MyCountDownLatch();
        //countDownLatch();
       // cyclicBarrier();
        executorService();
    }

    public static void MyCountDownLatch() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int thread = 3;
        final CountDownLatch countDownLatch = new CountDownLatch(thread);
        for (int i=0; i<thread; i++){
            new Thread(()->{

                try {
                    Thread.sleep(2000);
                    countDownLatch.countDown();
                    LOGGER.info("线程结束...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        countDownLatch.await();
        long stop = System.currentTimeMillis();
        LOGGER.info("main over total time={}",stop-startTime);
    }

    private static void countDownLatch() throws Exception{
        int thread = 3 ;
        long start = System.currentTimeMillis();
        final CountDownLatch countDown = new CountDownLatch(thread);
        for (int i= 0 ;i<thread ; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LOGGER.info("thread run");
                    try {
                        Thread.sleep(2000);
                        countDown.countDown();

                        LOGGER.info("thread end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        countDown.await();
        long stop = System.currentTimeMillis();
        LOGGER.info("main over total time={}",stop-start);
    }

    private static void cyclicBarrier() throws Exception {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3) ;

        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("thread run");
                try {
                    cyclicBarrier.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                LOGGER.info("thread end do something");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("thread run");
                try {
                    cyclicBarrier.await() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                LOGGER.info("thread end do something");
            }
        }).start();

        new Thread(() -> {
            LOGGER.info("thread run");
            try {
                Thread.sleep(5000);
                cyclicBarrier.await() ;
            } catch (Exception e) {
                e.printStackTrace();
            }

            LOGGER.info("thread end do something");
        }).start();

        LOGGER.info("main thread");
    }

    private static void executorService() throws Exception{
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10) ;
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,5,1, TimeUnit.MILLISECONDS,queue) ;
        poolExecutor.execute(() -> {
            LOGGER.info("running");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        poolExecutor.execute(() -> {
            LOGGER.info("running2");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        poolExecutor.shutdown();
        while (!poolExecutor.awaitTermination(1,TimeUnit.SECONDS)){
            LOGGER.info("线程还在执行。。。");
        }
        LOGGER.info("main over");
    }
}
