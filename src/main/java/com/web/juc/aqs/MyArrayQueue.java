package com.web.juc.aqs;

import java.util.concurrent.TimeUnit;

/**
 * @author web
 * @title: ArrayQueue
 * @projectName javacode
 * @description:  自定义ArrayQueue队列
 * @date 19-9-11下午1:30
 */
public class MyArrayQueue<T> {

    //队列数量
    private int count = 0;

    //存放数据
    private Object[] items;

    //队列已满时
    private final Object full = new Object();

    //队列已空时
    private final Object empty = new Object();

    //存放元素下标
    private int putIndex;

    //获取元素的下标
    private int getIndex;


    public MyArrayQueue(int size) {
        items = new Object[size];
    }

    public void put(T t) {
        //检查队列是否已满
        synchronized (full){
            while (count == items.length){
                try {
                    full.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        //队列未满，写入数据
        synchronized (empty){
            items[putIndex++] = t;
            count++;
            if (putIndex == items.length){
                putIndex = 0;
            }
            empty.notify();
        }
    }

    public T get() {
        //队列是否为空
        synchronized (empty){
            while (count == 0){
                try {
                    empty.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
        }
        //队列中存在数据
        synchronized (full){
            Object result = items[getIndex++];
            count--;

            if (getIndex == items.length) {
                getIndex = 0;
            }

            full.notify();
            return (T) result;
        }

    }

    public boolean isEmpty() {

        return size() == 0;
    }

    public synchronized int size() {
        return count;
    }

    public static void main(String[] args) {
        MyArrayQueue<String> strArr = new MyArrayQueue<>(16);
//        strArr.put("web");
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            strArr.put("web");
        }).start();
        String item =  strArr.get();
        System.out.println(item);
    }

}
