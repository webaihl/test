package com.web.algorithm.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class PriorityQueue {

    private List<Integer> array;

    public PriorityQueue() {
        array = new ArrayList<>(32);
    }

    public void enQueue(Integer key) {
       /* if (size >= array.size()){
            resize();
        }*/
        array.add(key);
        int[] temp = array.stream().mapToInt(x -> x).toArray();
        BinaryHeap.upAdjust(temp);
        array = IntStream.of(temp).boxed().collect(Collectors.toList());
    }

    public int deQueue() throws Exception {
        if (array.size() <= 0) {
            throw new Exception("the queue is empty!");
        }

        int head = array.get(0);
        array.set(0, array.get(array.size() - 1));
        int[] temp = array.stream().mapToInt(x -> x).toArray();
        BinaryHeap.downAdjust(temp, 0, array.size());
        array = IntStream.of(temp).boxed().collect(Collectors.toList());
        array.remove(array.size() - 1);
        return head;
    }


   /* private void resize(){
        int newSize = this.size * 2;
        this.array = Arrays.copyOf(this.array, newSize);
    }*/

    public static void main(String[] args) throws Exception {
        PriorityQueue priorityQueue = new PriorityQueue();
        priorityQueue.enQueue(3);
        priorityQueue.enQueue(5);
        priorityQueue.enQueue(10);
        priorityQueue.enQueue(2);
        priorityQueue.enQueue(7);

        System.out.println(priorityQueue.array.toString());
        System.out.println(priorityQueue.deQueue());
        System.out.println(priorityQueue.deQueue());
        System.out.println(priorityQueue.deQueue());
        System.out.println(priorityQueue.deQueue());
        System.out.println(priorityQueue.deQueue());
    }
}
