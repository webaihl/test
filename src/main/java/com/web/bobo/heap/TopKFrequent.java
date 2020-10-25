package com.web.bobo.heap;

import java.util.*;
import java.util.stream.IntStream;

import com.web.bobo.queue.PriorityQueue;

/**
 * @author web
 * @version 1.0.0
 * @ClassName TopKFrequent.java
 * @Description top k
 * @createTime 2020年09月19日 17:20:00
 */
public class TopKFrequent {

    private class Freq implements Comparable<Freq>{
        public int e,freq;

        public Freq(int e,int freq) {
            this.e = e;
            this.freq = freq;
        }

        //频率最低的在堆顶，因为最大堆
        @Override
        public int compareTo(Freq another) {
            if (this.freq < another.freq){
                return 1;
            }else if(this.freq > another.freq){
                return -1;
            }else {
                return 0;
            }
        }

    }
    public List<Integer> topKFrequent(Integer[] nums, int k){

        //1、计算频率
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (Integer num: nums) {
            if (map.containsKey(num)){
                map.put(num, map.get(num)+1);
            }else {
                map.put(num, 1);
            }
        }

        //2、构建K大小的堆， 然后依次遍历map，替换堆中的最值。直到找到top K
        PriorityQueue<Freq> pg = new PriorityQueue<>();
        for (Map.Entry<Integer, Integer> entrySet : map.entrySet()) {
            if (pg.getSize() < k){
                pg.enqueue(new Freq(entrySet.getKey(), entrySet.getValue()));
            }else if (entrySet.getValue() > pg.getFront().freq){
                pg.dequeue();
                pg.enqueue(new Freq(entrySet.getKey(), entrySet.getValue()));
            }
        }

        LinkedList<Integer> res = new LinkedList<>();
        while (!pg.isEmpty()){
            res.add(pg.dequeue().e);
        }

        return res;
    }

    public static void main(String[] args) {
        Integer[] nums = {1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,6,6,6,6,6,6};
        List<Integer> res = new TopKFrequent().topKFrequent(nums, 2);
        System.out.println(res);
    }
}
