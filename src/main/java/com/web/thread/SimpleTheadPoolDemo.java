package com.web.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author web
 * @title: SimpleTheadPoolDemo
 * @projectName javacode
 * @description: TODO
 * @date 19-9-22下午4:40
 */
public class SimpleTheadPoolDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0; i<10; i++){
            executorService.execute(new task());
        }

        executorService.shutdown();
    }


}

class task implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}
