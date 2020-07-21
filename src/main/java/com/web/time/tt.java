package com.web.time;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName tt.java
 * @Description
 * @createTime 2020年04月19日 15:23:00
 */
public class tt {

    final Object a = new Object();

    public static void main(String[] args) {
        String s = "2018-02-11 10:10:10";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        System.out.println(formatter.parse(s));
        Thread t1 = new Thread(()->{new tt().homework();});
        Thread t2 = new Thread(()->{new tt().homework();});
        t1.start();
        t2.start();

    }

    public void homework(){
        while (true) {
            System.out.println(Thread.currentThread().getName()+"执行！！！！");
            synchronized (a){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

         //   System.out.println(Thread.currentThread().getName()+"执行....");
        }
    }
}
