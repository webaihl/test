package com.web.juc.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreDemo {

    private static final int threadCount = 20;

    public static void main(String[] args){

        ExecutorService service = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i< threadCount; i++){
            final  int threadNum = i;
            service.submit(()->{
                try {
                    //if (semaphore.tryAcquire()){} //尝试获取许可，未获取到的线程直接丢弃
                    semaphore.acquire();
                    test(threadNum);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        service.shutdown();
    }

    private static void test(int threadNum) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       log.info(String.valueOf(threadNum));
    }
}
