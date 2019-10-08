package com.web.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockDemoTest {

    private int value = 0;
    private Lock lock = new ReentrantLock();


    public void add() {
        lock.lock();
        try {
            this.value += 1;

        } finally {
            lock.unlock();
        }
    }

    public void dec() {
        lock.lock();
        try {
            this.value -= 1;
        } finally {
            lock.unlock();
        }
    }

    public int getValue() {
        lock.lock();
        try {
            return this.value;
        } finally {
            lock.unlock();
        }
    }

}


public class LockDemo {
    final static int LOOP = 1000000;

    public static void main(String[] args) throws InterruptedException {

        LockDemoTest lockDemo = new LockDemoTest();
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
        t1.join();
        t2.join();
        System.out.println(lockDemo.getValue());
    }
}
 
