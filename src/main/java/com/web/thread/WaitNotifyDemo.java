package com.web.thread;


public class WaitNotifyDemo {
    private int start = 1;
    private boolean flag = false;

    public static void main(String[] args) {
        WaitNotifyDemo waitNotifyDemo = new WaitNotifyDemo();
        Thread ou = new Thread(new OuNumThread(waitNotifyDemo), "ou");
        Thread ji = new Thread(new JiNumThread(waitNotifyDemo), "ji");

        ou.start();
        ji.start();
    }

    public static class OuNumThread implements Runnable{

        private WaitNotifyDemo waitNotifyDemo;

        public OuNumThread(WaitNotifyDemo waitNotifyDemo){
            this.waitNotifyDemo = waitNotifyDemo;
        }

        @Override
        public void run() {
            while (waitNotifyDemo.start<=100){
                //共用WaitNotifyDemo的类锁
                synchronized (WaitNotifyDemo.class){
                    if (waitNotifyDemo.flag){
                        System.out.println(Thread.currentThread().getName()+" : "+waitNotifyDemo.start);
                        waitNotifyDemo.start++;
                        waitNotifyDemo.flag = false;
                        //唤醒奇数线程
                        WaitNotifyDemo.class.notify();
                    }else {
                        try {
                            WaitNotifyDemo.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    public static class JiNumThread implements Runnable{

        private WaitNotifyDemo waitNotifyDemo;

        public JiNumThread(WaitNotifyDemo waitNotifyDemo){
            this.waitNotifyDemo = waitNotifyDemo;
        }

        @Override
        public void run() {
            while (waitNotifyDemo.start<=100){
                synchronized (WaitNotifyDemo.class){
                    if (!waitNotifyDemo.flag){
                        System.out.println(Thread.currentThread().getName()+" : "+waitNotifyDemo.start);
                        waitNotifyDemo.start++;
                        waitNotifyDemo.flag = true;
                        WaitNotifyDemo.class.notify();
                    }else {
                        try {
                            WaitNotifyDemo.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
