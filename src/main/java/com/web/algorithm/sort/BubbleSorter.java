package com.web.algorithm.sort;

import java.util.Comparator;

public class BubbleSorter {

    public static void main(String[] args) {
        String[] ints = new String[]{"1", "2"};
        Sort(ints);
        System.out.println(binarySearch(ints, "2", null));
    }

    public static <T extends Comparable<T>> void Sort(T[] list) {
        boolean swaped = true;
        for (int i = 1, len = list.length; i < len && swaped; i++) {
            swaped = false;
            for (int j = 0; j < len - i; j++) {
                if (list[j].compareTo(list[j + 1]) > 0) {
                    T temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                    swaped = true;
                }
            }
        }

        System.out.println(list);
    }

    // 使用循环实现的二分查找
    public static <T> int binarySearch(T[] x, T key, Comparator<T> comp) {
        int low = 0;
        int high = x.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = comp.compare(x[mid], key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }


}
