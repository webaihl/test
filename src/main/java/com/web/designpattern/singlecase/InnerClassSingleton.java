package com.web.designpattern.singlecase;

import java.util.Date;

public class InnerClassSingleton {
    /***
     *
     * @author 96531
     *1、私有构造函数
     *2、静态内部类线程安全
     */
    private Date date = new Date();

    public Date getDate(){
        return date;
    }
    // 直接  private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
    private static final class SingletonHolder {
        private static final InnerClassSingleton INSTANCE = new InnerClassSingleton();
    }

    private InnerClassSingleton() {
    }

    public static final InnerClassSingleton getInstance() {
        // return InnerClassSingleton.getInstance();
        return SingletonHolder.INSTANCE;
    }
}
