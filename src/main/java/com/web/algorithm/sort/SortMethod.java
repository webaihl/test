package com.web.algorithm.sort;

public class SortMethod {

    private static Comparable[] aux;//归并中间数组

    public static <T> void sort(Comparable<T>[] a) {
        //具体的排序算法实现代码
    }

    private static <T> boolean less(Comparable<T> v, Comparable<T> w) {
        return v.compareTo((T) w) < 0;
    }

    private static <T> void exch(Comparable<T>[] a, int i, int j) {
        Comparable<T> t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static <T> void show(Comparable<T>[] a) {
        //在单行中打印数组
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + "");
            System.out.println();
        }
    }

    public static <T> boolean isSorted(Comparable<T>[] a) {
        //测试数组元素是否有序
        if (a.length < 1) {
            return true;
        }
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1]))
                return false;
        }
        return true;
    }


    public static <T> void shellSort(Comparable<T>[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = h * 3 + 1; //由大到小
        while (h >= 1) { //因为h/=3的原因所以需要>=
            for (int i = h; i < N; i++) { //排序[h,N]间的元素
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) { //与前h位的比较，按条件交换
                    exch(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

    public static <T> void mergeSort(Comparable<T>[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) / 2;
        mergeSort(a, lo, mid);
        mergeSort(a, mid + 1, hi); //如果mid+1 改为mid会造成无限递归 存在（0，1）（1，2）情况
        merge(a, lo, mid, hi);
    }

    private static <T> void merge(Comparable<T>[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1; //指针的初始指向
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];//中间数组赋值
        }
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++]; //左边数组用尽
            else if (j > hi) a[k] = aux[i++];//右边数组用尽
            else if (less(aux[j], aux[i])) a[k] = aux[j++];//aux右边数组小于左边数组
            else a[k] = aux[i++];//右边数组大于等于左边数组
        }

    }

    public static <T> void QuickSort(Comparable<T>[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = pratition(a, lo, hi);
        QuickSort(a, lo, mid - 1);
        QuickSort(a, mid + 1, hi); //a[mid]不能参与排序，不然无限循环
    }

    private static <T> int pratition(Comparable<T>[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;//对应--j
        Comparable<T> v = a[lo];//t'y切分元素
        while (true) {
            while (less(a[++i], v)) if (i == hi) break;//控制指针的上限，以及是够存在交换的元素
            while (less(v, a[--j])) if (j == lo) break;
            if (i >= j) break; //i，j相遇时跳出循环
            exch(a, i, j);//交换i，j元素
        }
        //切分元素和左子数组的最右端j交换，总是不大于v，保证切分元素在中间;
        exch(a, lo, j);
        return j;
    }

    public static void main(String[] args) {
        Integer[] test = new Integer[]{3, 5, 6, 4, 24, 6};
//        shellSort(test);
        /*aux = new Comparable[test.length];
        mergeSort(test, 0, test.length-1);//传递下标*/
        QuickSort(test, 0, test.length - 1);
        System.out.println(isSorted(test));
        ;
    }
}
