package com.web.designpattern.singlecase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService eService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 50; i++) {
            Runnable work = ()->{
                for (int j = 0; j < 20; j++) {
                    System.out.println(j+" "+Thread.currentThread().getName() + "---"
                            + InnerClassSingleton.getInstance().getDate());
                }
            } ;
            eService.execute(work);
        }
        eService.shutdown();
    }
}
