package com.web.bobo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName MyList.java
 * @Description 自定义List
 * @createTime 2020年07月21日 11:49:00
 */
public class MyList {

    //存放元素
    private Integer[] items;

    //元素个数
    private int size;

    public MyList(int initSize) {
        items = new Integer[initSize];
    }

    public MyList() {
        this(8);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        Integer[] new_a = new Integer[items.length << 1];
        System.arraycopy(items, 0, new_a, 0, items.length);
        items = new_a;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    public void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    //末尾追加
    public boolean add(Integer e) {
        if (size  == items.length){
            resize();
        }

        items[size++] = e;
        return true;
    }

    //删除第一个出现的元素
    public boolean remove(Integer e) {

        for (int i = 0; i < size; i++) {
            if (Objects.equals(e, items[i])) {
                remove(i);
                return true;
            }
        }

        return true;
    }

    //删除指定下标的元素
    public Integer remove(int index) {
        checkIndex(index);
        Integer ele = items[index];
        for (int i = index; i < size - 1; i++) {
            items[i] = items[i + 1];
        }
        items[--size] = null;
        return ele;
    }

    // 获取指定位置的元素
    public Integer get(int index) {
        checkIndex(index);

        return items[index];
    }

    public Integer set(int index, Integer e) {
        checkIndex(index);
        Integer ele = items[index];
        items[index] = e;
        return ele;
    }

    public static void main(String[] args) {
        MyList myList = new MyList(3);
        myList.add(1);
        myList.add(null);
        myList.add(3);
        myList.remove(null);
        Arrays.stream(myList.items).forEach(System.out::println);
    }
}
