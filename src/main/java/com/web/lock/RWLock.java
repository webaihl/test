package com.web.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * 读写锁--  可以多个线程同时读取，同一时刻只许一个线程写
 * 适合读多写少的场景
 *
 * @author 96531
 */
class LockTest {

    private int value = 0;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final WriteLock wLock = lock.writeLock();
    private final ReadLock rLock = lock.readLock();


    public void add() {
        wLock.lock();
        try {
            this.value += 1;
        } finally {
            wLock.unlock();
        }
    }

    public void dec() {
        wLock.lock();
        try {
            this.value -= 1;
        } finally {
            wLock.unlock();
        }
    }

    public int getValue() {
        rLock.lock();
        try {
            return this.value;
        } finally {
            rLock.unlock();
        }
    }

}


public class RWLock {
    final static int LOOP = 1000000;

    public static void main(String[] args) throws InterruptedException {

        final LockTest lockDemo = new LockTest();
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
 
