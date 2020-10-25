package com.web.bobo.segment;


/**
 * @author web
 * @version 1.0.0
 * @ClassName SegmentTree.java
 * @Description 线段树 用于区间操作(最值，求和...)
 * @createTime 2020年09月08日 14:22:00
 */
public class SegmentTree<E> {

    //存放原始数据
    private E[] data;

    //存放分段树结构的数据
    private E[] tree;

    //处理数据的方法
    private Merger<E> merger;

    public SegmentTree(E[] arr, Merger<E> merger){
        this.merger = merger;
        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
        }

        //需要是元数据的4倍
        tree = (E[]) new Object[arr.length << 2];
        buildSegmentTree(0, 0, arr.length - 1);
    }

    //构建以indexTree为根节点的[l..r]区间
    private void buildSegmentTree(int treeIndex, int l, int r) {

        //单节点时，tree[]直接设置data[l]中的原始值
        if (l == r){
            tree[treeIndex] = data[l];
            return;
        }

        int leftChild = leftChild(treeIndex);
        int rightChild = rightChild(treeIndex);
        // avoid overflow
        int mid = l + (r - l) / 2;
        buildSegmentTree(leftChild,l, mid);
        buildSegmentTree(rightChild,mid+1, r);

        tree[treeIndex] = this.merger.merger(tree[leftChild], tree[rightChild]);
    }

    public int getSize(){
        return data.length;
    }


    public E query(int queryL, int queryR){
        return query(0, 0, data.length-1, queryL, queryR);
    }

    // 在以treeIndex为根的线段树中[l...r]的范围里，搜索区间[queryL...queryR]的值
    private E query(int treeIndex, int l, int r, int queryL, int queryR) {
        if (l==queryL && r==queryR){
            return tree[treeIndex];
        }

        int leftChildIndex = leftChild(treeIndex);
        int rightChildIndex = rightChild(treeIndex);
        int mid = l + (r - l) / 2;

        //查询区间完全在 左/右节点区间时
        if (queryR <= mid){
            return query(leftChildIndex, l, mid, queryL, queryR);
        }else if (queryL >= mid+1){
            return query(rightChildIndex, mid+1, r,queryL, queryR);
        }

        //同时存在左右区间时
        E leftResult = query(leftChildIndex,l,mid,queryL,mid);//左子节点区间查找
        E rightResult = query(rightChildIndex, mid+1,r,mid+1,queryR);//右子节点区间查找

        return this.merger.merger(leftResult, rightResult);
    }

    public void set(int index, E e){
        checkIndex(index);
        set(0, 0, data.length,index, e);
    }

    //在l...r的范围内的index修改e
    private void set(int treeIndex, int l, int r, int index, E e) {

        if (l==r){
            tree[treeIndex] = e;
            return;
        }

        int mid = l + (r - l) / 2;
        int leftChildIndex = leftChild(treeIndex);
        int rightChildIndex = rightChild(treeIndex);

        if (index <= mid){ //替换的在左子节点
            set(leftChildIndex, l, mid,index, e);
        }else {//右子节点
            set(rightChildIndex, mid+1, r,index, e);
        }

        //操作子节点，使改变依次生效
        tree[treeIndex] = this.merger.merger(tree[leftChildIndex],tree[rightChildIndex]);
    }

    public void checkIndex(int index) {
        if (index < 0 || index >= data.length) {
            throw new  IllegalArgumentException("index is Illegal..");
        }
    }

    public E get(int index){
        checkIndex(index);
        return data[index];
    }

    public int leftChild(int index){
        checkIndex(index);
        return 2*index + 1;
    }

    public int rightChild(int index){
        checkIndex(index);
        return 2*index + 2;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append('[');
        for(int i = 0 ; i < tree.length ; i ++){
            if(tree[i] != null)
                res.append(tree[i]);
            else
                res.append("null");

            if(i != tree.length - 1)
                res.append(", ");
        }
        res.append(']');
        return res.toString();
    }
}
