package com.web.designpattern.singlecase;

import java.util.Date;

//双重锁机制 -- 适应于java1.5+以及只有一个类加载器
public class SingleCase {

    //保证可见性
    private  volatile static SingleCase singleCase;
    private Date date = new Date();

    private SingleCase() {
    }


    public Date getDate(){
        return date;
    }
    public static SingleCase getSingleCase() {
        if (singleCase == null) {//只有第一次才会彻底执行
            synchronized (SingleCase.class) { //保证只有一个线程执行
                if (singleCase == null) {
                    singleCase = new SingleCase();
                }
            }
        }
        return singleCase;
    }
}
