package com.web.algorithm.other;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ConstSort.java
 * @Description 数组包含1-n，将奇数下标元素打印，偶数下标元素插入队尾，
 * 依次循环，打印出顺序为1，2，3，...,n.求1-n排列顺序。
 * @createTime 2020年08月20日 16:58:00
 */
public class ConstSort {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        int n = 8;
        IntStream.rangeClosed(1, n).forEach(queue::add);
        int[] arr = sort(queue);
        System.out.println(Arrays.toString(arr));
    }

    public static int[] sort(Queue<Integer> queue) {
        int[] arr = new int[queue.size()];
        for (int i = 0; !queue.isEmpty(); i++) {
            arr[queue.poll() - 1] = i + 1;
            if (queue.peek() != null) {
                queue.add(queue.poll());
            }
        }
        return arr;
    }
}
