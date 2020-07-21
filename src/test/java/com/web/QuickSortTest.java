package com.web;

import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.Arrays;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName QuickSortTest.java
 * @Description
 * @createTime 2020年04月06日 10:20:00
 */
public class QuickSortTest {

    @Test
    public void quickSort(){
        int[] arr = {0,2,3,5,2,3,6,3,9,5,0};
        sort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public void sort(int[] a, int lo, int hi){
        if (lo >= hi){
            return;
        }
        int mid = parttion(a, lo, hi);
        sort(a, lo, mid-1);
        sort(a, mid+1, hi);
    }
    public boolean less (int v1, int v2){
        return v1 < v2;
    }

    public void exch(int[] a, int v1, int v2){
        int temp = a[v1];
        a[v1] = a[v2];
        a[v2] = temp;
    }
    public int parttion(int[] a, int lo, int hi){
        int i = lo, j=hi+1;
        int v = a[lo];
        while (true){
            while (less(a[++i] ,v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
            if (i>=j) break;
            exch(a, i,j);
        }
        exch(a, lo, j);
        return j;
    }
}
