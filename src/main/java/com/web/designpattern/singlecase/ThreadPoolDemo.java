package com.web.designpattern.singlecase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService eService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            Runnable work = ()->{
                for (int j = 0; j < 20; j++) {
                    System.out.println(j+" "+Thread.currentThread().getName() + "---"
                            + SingleCase.getSingleCase().getDate());
                }
            } ;
            eService.execute(work);
        }
        eService.shutdown();
    }
}
