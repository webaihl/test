package com.web.algorithm.other;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName Sum1N.java
 * @Description 1+...+N
 * @createTime 2020年08月17日 19:50:00
 */
public class Sum1N {

    public static int solution(int n, int s) {
        boolean b = n - 1 > 0 && (s = solution(n - 1, s)) > 0;
        return s + n;
    }

    public static void main(String[] arg) {
        int s = solution(2, 0);
        System.out.println(s);
    }
}
