package com.web.bobo.heap;

import java.util.*;

/**
 * @author web
 * @version 1.0.0
 * @ClassName TopKFrequent.java
 * @Description top k 使用java提供的PriorityQueue 是最小堆
 * @createTime 2020年09月19日 17:20:00
 */
public class TopKFrequentJava {

    private class Freq{
        public int e,freq;

        public Freq(int e,int freq) {
            this.e = e;
            this.freq = freq;
        }
    }
    public List<Integer> topKFrequent(Integer[] nums, int k){

        //1、计算频率
        Map<Integer, Integer> map = new TreeMap<>();
        for (Integer num: nums) {
            if (map.containsKey(num)){
                map.put(num, map.get(num)+1);
            }else {
                map.put(num, 1);
            }
        }

        //2、构建K大小的堆， 然后依次遍历map，替换堆中的最值。直到找到top K
        //直接存值 不是频率
        PriorityQueue<Integer> pg = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                //匿名内部类的变量捕获
                return map.get(a) - map.get(b);
            }
        });
        for (Map.Entry<Integer, Integer> entrySet : map.entrySet()) {
            if (pg.size() < k){
                pg.add(entrySet.getKey());
            }else if (map.get(entrySet.getValue()) > map.get(pg.peek())){
                pg.remove();
                pg.add(entrySet.getValue());
            }
        }

        LinkedList<Integer> res = new LinkedList<>();
        while (!pg.isEmpty()){
            res.add(pg.remove());
        }

        return res;
    }

    public static void main(String[] args) {
        Integer[] nums = {1,2,2,3,3,3,4,4,4,4,5,5,5,5,5,6,6,6,6,6,6};
        List<Integer> res = new TopKFrequentJava().topKFrequent(nums, 2);
        System.out.println(res);
    }
}
