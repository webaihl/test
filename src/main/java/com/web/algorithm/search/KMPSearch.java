package com.web.algorithm.search;

public class KMPSearch {

    public static void main(String[] args) {
        KMP("aaaaaaaaaaaaaaabaaaaaaab", "aaaab");
    }

    private static int[] prefi_table(String pattren) {
        char[] pattres = pattren.toCharArray();
        int n = pattren.length();
        int[] prefix_table = new int[n];
        int len = 0;
        prefix_table[0] = 0;
        int i = 1;
        while (i < n) {
            if (pattres[i] == pattres[len]) { //相等的时候长度加1，继续往后比较
                len++;
                prefix_table[i] = len;
                i++;
            } else {//遇到不相等元素
                if (len > 0) {//取左邻元素的最大前缀
                    len = prefix_table[len - 1];
                    //prefix_table[i] = len;
                } else {//len=0时
                    prefix_table[i] = 0;// len
                    i++;
                }
            }
        }

        for (int k = n - 1; k > 0; k--) { //生成最终的前缀表
            prefix_table[k] = prefix_table[k - 1];
        }
        prefix_table[0] = -1;
        return prefix_table;
    }

    public static void KMP(String text, String pattren) {
        char[] texts = text.toCharArray();
        char[] pattrens = pattren.toCharArray();
        int[] prefix_table = prefi_table(pattren);
        int m = texts.length;
        int n = pattrens.length;
        // texts[i] pattrens[j]
        int i = 0, j = 0;
        while (i < m) {
            if (j == n - 1 && texts[i] == pattrens[j]) {//匹配到模式最后一位时成功
                System.out.println("在第" + (i - j + 1) + "位处匹配成功");
                j = prefix_table[j]; //第一次匹配成功后， 继续匹配
            }
            if (texts[i] == pattrens[j]) {
                i++;
                j++;
            } else {
                j = prefix_table[j]; //j的位置不回溯， 等于前缀表的对应的值
                if (j == -1) { //前缀表的对应的值为-1时，整体后移
                    i++;
                    j++;
                }
            }
        }
    }
}
