package com.web.thread;

class threadDemo1 implements Runnable {
    int ticket = 10;

    @Override
    public void run() {

        // synchronized (this) {} //代码块的同步
        for (int i = 0; i < 100; i++) {
            test();
        }
    }

    public synchronized void test() { // 方法的同步
        if (ticket > 0) {
            System.out.println(ticket--);
        }
    }

}

public class ThreadImplementDemo {

    public static void main(String[] args) {
        threadDemo1 threadDemo1 = new threadDemo1();
        Thread t1 = new Thread(threadDemo1);
        Thread t2 = new Thread(threadDemo1);
        Thread t3 = new Thread(threadDemo1);
        t1.start();
        t2.start();
        t3.start(); // start()开启新线程时、会自动调用 run()，不必等待
        // run方法体的代码执行。run方法不会开启新线程。
    }
}