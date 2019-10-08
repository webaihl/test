package com.web.designpattern.factory.simple;

/**
 * @author 96531
 * @time 2018-06-22
 */

public class TestMain {
    /**
     * 1、定义逻辑接口，并实现
     * 2、定义工厂接口，实现获取相应的逻辑实现类对象
     * 2、通过得到的对象，调用相应的方法
     */
    public static void main(String[] args) {
        ReadFactory img = new ImgReadFactory();
        img.getRead().read();
    }
}
