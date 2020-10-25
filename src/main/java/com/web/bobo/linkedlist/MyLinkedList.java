package com.web.bobo.linkedlist;

/**
 * 自定义LinkedList. 虚拟头节点的运用
 */

public class MyLinkedList<E> {

    private class Node {
        private E e;
        private Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this.e = e;
        }

        public Node() {
            this.e = null;
            this.next = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "e=" + e +
                    '}';
        }
    }

    private Node dummyHead;
    private int size;

    public MyLinkedList(){
        dummyHead = new Node();
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty(){return size == 0;}

    public void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("下标范围异常！");
        }
    }

    public void insert(int index, E e) {
        checkIndex(index);
        Node prev = dummyHead;
        //寻找要插入节点的前一个节点
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
//            Node node = new Node(e);
//            node.next = prev.next;
//            prev.next = node;
        prev.next = new Node(e, prev.next);
        size++;

    }

    public void addFirst(E e) {
//        Node node = new Node(e);
//        node.next = head;
//        head = node;
        // 等价 head = new Node(e, head);
        insert(0, e);
        size++;
    }

    public void addLast(E e) {
        insert(size, e);
    }

    public E get(int index){
        checkIndex(index);
        //直接从第一个真实节点开始遍历
        Node current = dummyHead.next;
        for(int i = 0; i < index; i++){
            current = current.next;
        }
        return current.e;
    }

    public E getFirst(){
        return get(0);
    }

    public E getLast(){
        return get(size-1);
    }

    public void set(int index , E e){
        checkIndex(index);

        Node current = dummyHead.next;
        for(int i = 0; i < index; i++){
            current = current.next;
        }
        current.e = e;

    }

    public boolean contain(E e){
        Node current = dummyHead.next;
        while (current != null){
            if (current.e.equals(e)){
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void remove(int index){
        checkIndex(index);
        Node prev = dummyHead;
        for(int i = 0; i < index; i++){
            prev = prev.next;
        }

        Node delNode = prev.next;
        prev.next = delNode.next;
        size--;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Node cur = dummyHead.next; cur != null; cur = cur.next){
            stringBuilder.append(cur).append("-->");
        }
        stringBuilder.append("NULL");
        return stringBuilder.toString();
    }
}
