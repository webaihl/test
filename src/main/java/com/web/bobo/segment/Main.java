package com.web.bobo.segment;

/**
 * @author web
 * @version 1.0.0
 * @ClassName Nain.java
 * @Description
 * @createTime 2020年09月17日 21:36:00
 */
public class Main {

    public static void main(String[] args) {
        Integer[] num = {-2,0,3,-5,2,-1};
        SegmentTree<Integer> segmentTree = new SegmentTree<Integer>(num, Integer::sum);
        System.out.println(segmentTree);
        System.out.println(segmentTree.query(0, 2));
    }
}
