package com.web.algorithm.other;


/**
 * N阶乘末尾0的个数
 */
public class NFactorialEndZero {

    public static void main(String[] args) {
        zeroCount(100);
    }
    public static void zeroCount(int n){
        int count = 0;
        // 5个数字可得1个0，5*5个数字可得2个0,5*5*5个数字可得3个0...
        while (n >= 5){
            // 第1次得到 5 10 15 20 25 30 35 40 45 50 55 60 65 70 75 80 85 90 95 100
            // 第2次得到 25 50 75 100
            // 第3次...
            count += n/5;
            n /= 5;
        }
        System.out.println(count);
    }
}
