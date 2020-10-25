package com.web.bobo.bst;

/**
 * @author web
 * @version 1.0.0
 * @ClassName Main.java
 * @Description TODO
 * @createTime 2020年08月31日 21:11:00
 */
public class Main {

    public static void main(String[] args) {

        BST<Integer> bst = new BST<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for(int num: nums)
            bst.add(num);

        /////////////////
        //      5      //
        //    /   \    //
        //   3    6    //
        //  / \    \   //
        // 2  4     8  //
        /////////////////
//        bst.preOrder();
//        System.out.println();
//
//        bst.inOrder();
//        System.out.println();
//
//        bst.postOrder();
//        System.out.println();

//        System.out.println(bst.contains(6));
        bst.levelTraversal();
    }
}

