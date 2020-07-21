package com.web;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName MergeSortTest.java
 * @Description
 * @createTime 2020年04月06日 10:46:00
 */
public class MergeSortTest {

    int[] aux ;
    @Test
    public void mergeSort(){
        int[] arr = {0,2,3,5,2,3,6,3,9,5,0};
        aux = new int[arr.length];
        sort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public void sort(int[] a, int lo, int hi){
        if (lo >= hi){
            return;
        }
        int mid = lo + (hi-lo)/2;
        sort(a, lo, mid);
        sort(a, mid+1, hi);
        merge(a, lo, mid, hi);
    }

    public void merge(int[] a, int lo,int mid, int hi){
        int i = lo, j = mid+1;
        for (int k = lo; k<=hi; k++){
            aux[k] = a[k];
        }

        for (int k = lo; k<=hi; k++){
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[i] > aux[j]) a[k] = aux[j++];
            else a[k] = aux[i++];
        }

    }
}
