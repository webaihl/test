package com.web.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName TestBloomFilter.java
 * @Description 布隆过滤器使用--缓存穿透、大数据量去重
 *              不存在是肯定的, 存在只是可能的。误匹配数量跟误匹配率有关
 * @createTime 2020年07月24日 15:23:00
 */
public class TestBloomFilter {

    private static int total = 1000000;
    private static BloomFilter<Integer> bf = BloomFilter.create(Funnels.integerFunnel(), total);
    //private static BloomFilter<Integer> bf = BloomFilter.create(Funnels.integerFunnel(), total, 0.1);

    public static void main(String[] args) {
        //载入数据到过滤器
        for(int i=0; i<total; i++){
            bf.put(i);
        }

        //匹配不上的
        for(int i=0; i<total; i++){
            if (!bf.mightContain(i)){
                System.out.println(i+"匹配不上");
            }
        }

        //不在过滤器中，却匹配出来的
        int count = 0;
        for(int i=total; i<total + 10000; i++){
            if (bf.mightContain(i)){
                count++;
            }
        }
        System.out.println("错匹配数量: "+count);
    }
}
