package com.web.juc;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class HelloTask implements Runnable {
    String name;

    public HelloTask(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println("Hello, " + name + "! It is " + LocalTime.now());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        System.out.println("Goodbye, " + name + "! It is " + LocalTime.now());
    }
}

public class ScheduleManager {

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        //每2秒执行一次,如果上一次执行时间大于delay，马上执行  max(exec_time, delay)
        executor.scheduleAtFixedRate(new HelloTask("Bob"), 2, 5, TimeUnit.SECONDS);
        //线程完成后，上一次执行完成后，每2秒执行  exec_time+delay
        executor.scheduleWithFixedDelay(new HelloTask("Alice"), 2, 5, TimeUnit.SECONDS);
    }

}
