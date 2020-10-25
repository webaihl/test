package com.web.bobo.heap;

import com.web.bobo.arraylist.Array;

/**
 * @author web
 * @version 1.0.0
 * @ClassName MaxHeep.java
 * @Description 最大堆 完全二叉树 log(n）
 * @createTime 2020年09月19日 13:57:00
 */
public class MaxHeap<E extends Comparable<E>> {

    private Array<E> data;

    public MaxHeap(int capcity){
        data = new Array<>(capcity);
    }

    public MaxHeap(){
        data = new Array<>();
    }

    public int getSize(){
        return data.getSize();
    }

    public boolean isEmpty(){
        return data.isEmpty();
    }

    private int parent(int index){
        if (index == 0){
            throw new IllegalArgumentException("index-0 have not parent index");
        }
        return (index - 1) / 2;
    }

    private int leftChild(int index){
        return index*2 + 1;
    }

    private int rightChild(int index){
        return index*2 + 2;
    }

    public void add(E e){
        data.addLast(e);
        siftUp(data.getSize() - 1);//最后一个元素上浮
    }

    private void siftUp(int k) {
        // 不是root 以及父节点小于当前节点时，替换
        while (k>0 && data.get(parent(k)).compareTo(data.get(k)) < 0){
            data.swap(k, parent(k));
            k = parent(k);
        }
    }

    public E findMax(){
        if (data.getSize() == 0){
            throw new IllegalArgumentException("queue is empty!");
        }
        return data.get(0);
    }

    //获取并删除
    public E extractMax(){
        E e = findMax();
        data.swap(0, getSize() - 1);//将最后一个元素与堆顶交换
        data.removeLast();//删除最后一个元素(之前的堆顶元素)
        siftDown(0);//重新调整
        return e;
    }

    private void siftDown(int k) {

        //存在左孩子
        while (leftChild(k) < data.getSize()){
            int j = leftChild(k);
            //判断是否存在右节点
            if (j+1 < data.getSize() && data.get(j).compareTo(data.get(j+1))<0){
                j = rightChild(k);//data[j] 存放left，right的max
            }

//            if (data.get(k).compareTo(data.get(j)) <0 ){ //会导致死循环
//                data.swap(k,j);
//                k = j;
//            }
            if (data.get(k).compareTo(data.get(j)) >= 0) break; //快速中止
            data.swap(k,j);
            k = j;

        }
    }

    // 取出堆中的最大元素，并且替换成元素e
    public E replace(E e){

        E ret = findMax();
        data.set(0, e);
        siftDown(0);
        return ret;
    }

}
