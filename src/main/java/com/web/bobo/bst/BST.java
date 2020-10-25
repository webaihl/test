package com.web.bobo.bst;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author web
 * @version 1.0.0
 * @ClassName BST.java
 * @Description 二分搜索树
 * @createTime 2020年08月31日 17:27:00
 */
public class BST<E extends Comparable<E>> {

    private class Node{
       private E val;
       private Node right, left;

        public Node(E val) {
            this.val = val;
        }
    }

    private Node root;
    private int size;

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }
/*
    // 向二分搜索树中添加新的元素e
    public void add(E e){
        if (root == null){
            root = new Node(e);
            size++;
        }else {
            add(root, e);
        }
    }
    // 向以node为根的二分搜索树中插入元素e，递归算法
    private void add(Node node, E e) {
       if (Objects.equals(node.val, e)) return;
       else if (node.val.compareTo(e) < 0 && node.left == null){
            node.left = new Node(e);
            size++;
            return;
       }else if (node.val.compareTo(e)>0 && node.right == null){
            node.right = new Node(e);
            size++;
            return;
       }

       if (node.val.compareTo(e) < 0){
           add(node.left, e);
       }else if(node.val.compareTo(e) > 0){
           add(node.right, e);
       }
    }
   */

    public void add(E e){
        root = add(root, e);
    }

    //返回插入新节点后的root节点
    private Node add(Node node, E e) {
        // 如果root节点为null，直接将该节点作为root
        if (node == null) {
            return new Node(e);
        }

        // 如果是等于==， 则什么都不做，保证唯一
        if (e.compareTo(node.val) < 0){
            node.left = add(node.left, e);
        }else if (e.compareTo(node.val) > 0){
            node.right = add(node.right, e);
        }
        return node;
    }

    public boolean contains(E e){
        return contains(root, e);
    }

    private boolean contains(Node node, E e) {

        if(node == null)
            return false;

        if (e.compareTo(node.val) == 0) return true;
        else if (e.compareTo(node.val) < 0){
            return contains(node.left, e);
        }else {
            return contains(node.right, e);
        }
    }

    //前序遍历
    public void preOrder(){
        preOrder(root);
    }

    private void preOrder(Node node){
        if (node == null) return;
        System.out.println(node.val);
        preOrder(node.left);
        preOrder(node.right);
    }

    //非递归前序遍历 Stack模拟函数执行的系统栈
    //ArrayQueue<Integer> stack = new ArrayQueue<>(getSize());
    public void preOrderNoRecursive(){
    Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()){
            Node cur = stack.pop();
            System.out.println(cur.val);
            if (cur.right != null){
                stack.push(cur.right);
            }
            if (cur.left != null){
                stack.push(cur.left);
            }
        }
    }

    //用Queue保存各层级的节点，用于搜索策略--广度优先搜索
    public void levelTraversal(){
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Node node = queue.poll();
            System.out.println(node.val);
            if (node.left != null){
                queue.add(node.left);
            }
            if (node.right != null){
                queue.add(node.right);
            }
        }
    }

    //中序遍历
    public void inOrder(){
        inOrder(root);
    }

    private void inOrder(Node node){
        if (node == null) return;
        inOrder(node.left);
        System.out.println(node.val);
        inOrder(node.right);
    }

    //后序遍历
    public void postOrder(){
        postOrder(root);
    }

    private void postOrder(Node node){
        if (node == null) return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.val);
    }


    //获取树中的最小值
    public E minNum(){
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");

        Node minNode = minNum(root);
        return minNode.val;
    }

    //返回以node为根的二分搜索树的 最小值所在的节点
    private Node minNum(Node node) {
        if (node.left == null)
            return node;

        return minNum(node.left);
    }

    //获取树中的最大值
    public E maxNum(){
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");

        Node minNode = maxNum(root);
        return minNode.val;
    }

    //返回以node为根的二分搜索树的 最大值所在的节点
    private Node maxNum(Node node) {
        if (node.right == null)
            return node;

        return maxNum(node.right);
    }

    //删除最小值并返回
    public E removeMin(){
       E minNum = minNum();
       root = removeMin(root);
       return minNum;
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node) {

        if (node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        //一直查询左子树
        node.left = removeMin(node.left);
        return node;
    }

    //删除最大值并返回
    public E removeMax(){
       E maxNum = maxNum();
       root = removeMax(root);
       return maxNum;
    }

    private Node removeMax(Node node) {
        if (node.right == null){
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }

    //删除元素为e的节点
    public void remove(E e){
        root = remove(root, e);
    }

    private Node remove(Node node, E e) {
        if (node == null) return null;
        if (e.compareTo(node.val) < 0){
            node.left = remove(node.left, e);
            return node;
        }else if(e.compareTo(node.val) > 0){
            node.right = remove(node.right, e);
            return node;
        }else if(e.compareTo(node.val) == 0){

            //左子树为null
            if (node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }

            //右子数为null
            if (node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }

            //1、已经找到要删除的节点d
            //2、找出删除节点d最近的最小节点s (后继、前驱) -- 保证顺序性
            //3、替换s为d
            Node minNode = minNum(node);
            minNode.right = removeMin(node.right);;
            minNode.left = node.left;
            node.left = node.right =null;
            //size--; removeMin操作已经 --
            return minNode;




        }
        return node;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        generateBSTString(root, 0, res);
        return res.toString();
    }

    // 生成以node为根节点，深度为depth的描述二叉树的字符串
    private void generateBSTString(Node node, int depth, StringBuilder res){

        if(node == null){
            res.append(generateDepthString(depth)).append("null\n");
            return;
        }

        res.append(generateDepthString(depth)).append(node.val).append("\n");
        generateBSTString(node.left, depth + 1, res);
        generateBSTString(node.right, depth + 1, res);
    }

    private String generateDepthString(int depth){
        StringBuilder res = new StringBuilder();
        for(int i = 0 ; i < depth ; i ++)
            res.append("--");
        return res.toString();
    }

}
