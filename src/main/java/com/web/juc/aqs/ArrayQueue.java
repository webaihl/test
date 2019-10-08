package com.web.juc.aqs;

/**
 * @author web
 * @title: ArrayQueue
 * @projectName javacode
 * @description: TODO
 * @date 19-9-11下午1:30
 */
public class ArrayQueue<T> {

    //队列数量
    private int count = 0;

    //存放数据
    private Object[] items;

    //队列满锁
    private Object full = new Object(); //

    //队列空锁
    private Object empty = new Object(); //

    //存放元素下标
    private int putIndex;

    //获取元素的下标
    private int getIndex;


    public ArrayQueue(int size) {
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

}
