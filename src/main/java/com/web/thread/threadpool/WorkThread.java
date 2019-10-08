package com.web.thread.threadpool;

public class WorkThread implements Runnable {

    private String flag = null;

    public WorkThread(String s) {
        this.flag = s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " start." + " flag= " + flag);
        flagprocess();
        System.out.println(Thread.currentThread().getName() + " end.");
    }

    private void flagprocess() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return flag.toString();
    }

}
