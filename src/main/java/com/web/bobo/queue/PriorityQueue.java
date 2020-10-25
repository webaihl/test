package com.web.bobo.queue;

import com.web.bobo.heap.MaxHeap;

/**
 * @author web
 * @version 1.0.0
 * @ClassName PriorityQueue.java
 * @Description 优先队列
 * @createTime 2020年09月19日 13:27:00
 */
public class PriorityQueue<E extends Comparable<E>> implements Queue<E>{
    private MaxHeap<E> maxHeap;

    public PriorityQueue(){
        maxHeap = new MaxHeap<>();
    }

    @Override
    public int getSize(){
        return maxHeap.getSize();
    }

    @Override
    public boolean isEmpty(){
        return maxHeap.isEmpty();
    }

    @Override
    public E getFront(){
        return maxHeap.findMax();
    }

    @Override
    public void enqueue(E e){
        maxHeap.add(e);
    }

    @Override
    public E dequeue(){
        return maxHeap.extractMax();
    }
}
