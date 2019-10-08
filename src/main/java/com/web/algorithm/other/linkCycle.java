package com.web.algorithm.other;


public class linkCycle {
    private static Node n1 = null;

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    /**
     * 判断是否有环
     *
     * @param head
     * @return
     */
    public static boolean isCycle(Node head) {
        Node p1 = head;
        Node p2 = head;
        while (p2 != null && p2.next != null) {
            p1 = p1.next;
            p2 = p2.next.next;
            if (p1 == p2) {
                n1 = p1;
                return true;
            }
        }
        return false;
    }

    /**
     * 如果有环 求入环节点
     *
     * @param head
     * @return
     */
    public static Node getCycleNode(Node head) {
        Node cycleNode = null;
        Node n2 = head;
        while (true) {
            n1 = n1.next;
            n2 = n2.next;
            if (n1 == n2) {
                cycleNode = n1;
                break;
            }
        }
        return cycleNode;
    }

    /**
     * 如果有环 求环的长度
     *
     * @return
     */
    public static int getCycleLength() {
        int len = 0;
        Node n2 = n1;
        while (true) {
            n1 = n1.next;
            n2 = n2.next.next;
            len++;
            if (n1 == n2) {
                break;
            }
        }
        return len;
    }

    public static void main(String[] args) {
        Node node1 = new Node(5);
        Node node2 = new Node(3);
        Node node3 = new Node(7);
        Node node4 = new Node(2);
        Node node5 = new Node(6);
        Node node6 = new Node(8);
        Node node7 = new Node(1);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = node4;
        System.out.println(isCycle(node1));
        if (isCycle(node1)) {
            System.out.println(getCycleNode(node1).data);
            System.out.println(getCycleLength());
        }
    }
}
