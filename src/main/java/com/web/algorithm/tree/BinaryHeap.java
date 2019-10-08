package com.web.algorithm.tree;

import java.util.Arrays;

/**
 * 二叉堆 -- 构建最小堆
 * leftChild = parent * 2 + 1
 * rightChild= parent * 2 + 2
 * parent = (child-1)/2
 */
public class BinaryHeap {

    /**
     * 上浮
     *
     * @param array
     */
    public static void upAdjust(int[] array) {
        int childIndex = array.length - 1;
        int parentIndex = (childIndex - 1) / 2;
        //保存叶子节点，用与填充父节点
        int temp = array[childIndex];
        while (childIndex > 0 && temp < array[parentIndex]) {
            array[childIndex] = array[parentIndex];
            //重新计算父子节点的下标
            childIndex = parentIndex;
            parentIndex = (parentIndex - 1) / 2;
        }
        //填充父节点
        array[childIndex] = temp;
    }

    /**
     * 下沉
     * 让所有非叶子节点下沉
     *
     * @param array
     * @param parentIndex
     * @param length
     */
    public static void downAdjust(int[] array, int parentIndex, int length) {
        int temp = array[parentIndex];
        int childIndex = parentIndex * 2 + 1;
        while (childIndex < length) {
            //左右孩子对比
            if (childIndex + 1 < length && array[childIndex + 1] < array[childIndex]) {
                childIndex++;
            }
            if (temp < array[childIndex]) break;
            //将子节点赋值给父节点
            array[parentIndex] = array[childIndex];
            //重新计算父子节点的下标
            parentIndex = childIndex;
            childIndex = 2 * childIndex + 1;
        }
        array[parentIndex] = temp;
    }

    /**
     * 构建二叉堆
     *
     * @param array
     */
    public static void buildHeap(int[] array) {
        //最后一个非叶子节点开始，依次下沉
        for (int i = (array.length - 1) / 2; i >= 0; i--) {
            downAdjust(array, i, array.length);
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, 3, 2, 6, 5, 7, 8, 9, 10, 0}; //[0, 1, 2, 6, 3, 7, 8, 9, 10, 5]
        upAdjust(array);
        System.out.println(Arrays.toString(array));

        array = new int[]{7, 1, 3, 10, 5, 2, 8, 9, 6};
        buildHeap(array);
        System.out.println(Arrays.toString(array));
    }
}
