package com.web.juc.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ConditionExample {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(()->{
            try {
                lock.lock();
                log.info("wait signal..."); // 1
                condition.await();
                log.info("get signal...."); //4
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }, "T-1").start();

        new Thread(()->{
            lock.lock();
            log.info("get lock..."); //2
            try {
                TimeUnit.SECONDS.sleep(3);
                condition.signalAll();
                log.info("send signal....");//3
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }, "T-2").start();
    }
}
