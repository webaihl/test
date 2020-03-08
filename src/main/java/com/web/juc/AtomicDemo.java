package com.web.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 局限于只能对一个变量同步操作
 *
 * @author 96531
 */
class AtomicDemoTest {

    private AtomicInteger value = new AtomicInteger(0);


    public void add() {
        this.value.addAndGet(1);
    }

    public void dec() {
        this.value.addAndGet(-1);
    }

    public int getValue() {
        return this.value.get();
    }

}


public class AtomicDemo {
    final static int LOOP = 1000000;

    public static void main(String[] args) throws InterruptedException {

        AtomicDemoTest lockDemo = new AtomicDemoTest();
        Thread t1 = new Thread() {
            public void run() {
                for (int i = 0; i < LOOP; i++) {
                    lockDemo.add();
                }
            }

            ;
        };
        Thread t2 = new Thread() {
            public void run() {
                for (int i = 0; i < LOOP; i++) {
                    lockDemo.dec();
                }
            }

            ;
        };

        t1.start();
        t2.start();
        //不加以下两句会出现同步错误,无论线程调用者是否和被调方法在同一个类中
        //t1.join();
        //t2.join();
        System.out.println(lockDemo.getValue());
    }
}
 

