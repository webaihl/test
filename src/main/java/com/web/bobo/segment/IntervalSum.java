package com.web.bobo.segment;

/**
 * @author web
 * @version 1.0.0
 * @ClassName IntervalSum.java
 * @Description 区间求和(数组不可变) -- 非分段树算法
 * @createTime 2020年09月18日 14:05:00
 */
public class IntervalSum {

    //用来存储前n个元素的值 预处理 sum[i] = sum[0...i-1]
    public int[] sum;
    public IntervalSum(int [] arr){
        sum = new int[arr.length + 1];
        sum[0] = 0;
        for (int i = 1; i < arr.length; i++) {
            sum[i] = arr[i-1] + sum[i-1];
        }
    }

    public int sumRang(int i, int j){
        return sum[j+1] - sum[i];
    }
}
