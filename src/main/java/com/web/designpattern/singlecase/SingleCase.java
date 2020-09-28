package com.web.designpattern.singlecase;

import java.util.Date;

//双重锁机制 -- 适应于java1.5+以及只有一个类加载器
public class SingleCase {

    //保证可见性
    /**
     * pInst = new T()
     * 1、分配内存
     * 2、内存位置调用初始化函数
     * 3、内存地址复制给pInst
     *
     * 2和3可能颠倒，另一个线程并发getSingleCase，会返回尚未构造完全的内存地址，
     * 而此时singleCase ！= null。导致直接返回singleCase(null)
     */
    private volatile static SingleCase singleCase;
    private final Date date = new Date();

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
