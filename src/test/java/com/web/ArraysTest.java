package com.web;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ArraysTest.java
 * @Description
 * @createTime 2020年04月16日 09:56:00
 */

public class ArraysTest {

    @Test
    public void testArrayCopy(){
        int[] a = {1,2,3,4,5};
        int[] b = new int[a.length-1];
        //remove  element 3
        System.arraycopy(a, 0, b,0,2);
        System.arraycopy(a, 3, b,2,2);
        System.out.println(Arrays.toString(b));
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        ConcurrentHashMap map = new ConcurrentHashMap(16);
    }
}
