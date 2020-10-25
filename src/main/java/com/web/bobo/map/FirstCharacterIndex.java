package com.web.bobo.map;

import java.util.HashMap;

/**
 * @author web
 * @version 1.0.0
 * @ClassName FirstCharacterIndex.java
 * @Description 第一个出现的唯一字符的下标(假设都是小写字母)
 * @createTime 2020年10月13日 08:40:00
 */
public class FirstCharacterIndex {

    public static int getIndex(String str){

        //每个小写字母出现的个数 字符 --> 索引  a--0  b--1 ...
        int[] count = new int[26];
        for (int i = 0; i < str.length(); i++) {
            count[str.charAt(i)- 'a'] ++;
        }

        for (int i = 0; i < str.length(); i++) {
            if (count[str.charAt(i) - 'a'] == 1) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
      //  System.out.println(getIndex("hhoowwrrff"));
        System.out.println(((Integer) 34).hashCode());
        int b = -34;
        System.out.println(((Integer) b).hashCode());

        System.out.println("web".hashCode());
    }
}
