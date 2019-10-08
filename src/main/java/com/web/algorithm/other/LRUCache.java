package com.web.algorithm.other;

import java.util.HashMap;

public class LRUCache {

    private Node head;
    private Node end;
    private int limit;
    private HashMap<String, Node> hashMap;

    class Node {
        private Node pre;
        private Node next;
        private String key;
        private String value;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public LRUCache(int limit) {
        this.limit = limit;
        hashMap = new HashMap<>();
    }


    public String get(String key) {
        Node node = hashMap.get(key);
        if (node == null) {
            return null;
        }
        //刷新使用节点
        refreshNode(node);
        return node.value;
    }

    public void put(String key, String value) {
        Node node = hashMap.get(key);
        if (node == null) {
            //达到最大值，删除左侧元素
            if (hashMap.size() >= limit) {
                String oldKey = removeNode(head);
                hashMap.remove(oldKey);
            }
            node = new Node(key, value);
            addNode(node);
            hashMap.put(key, node);
        } else {
            //如果存在更新value,以及使用
            node.value = value;
            refreshNode(node);
        }
    }

    public void remove(String key) {
        Node node = hashMap.get(key);
        removeNode(node);
        hashMap.remove(key);
    }

    private void refreshNode(Node node) {
        //尾节点直接删除
        if (node == end) {
            return;
        }
        //删除、更新到最右
        removeNode(node);
        addNode(node);
    }

    private String removeNode(Node node) {
        if (node == head && node == end) {
            head = null;
            end = null;
        } else if (node == end) {
            end = end.pre;
            end = null;
        } else if (node == head) {
            head = head.next;
            head.pre = null;
        } else {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        return node.key;
    }

    private void addNode(Node node) {
        if (end != null) {
            end.next = node;
            node.pre = end;
            node.next = null;
        }
        end = node;
        if (head == null) {
            head = node;
        }
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(5);
        lruCache.put("001", "user info of 1");
        lruCache.put("002", "user info of 2");
        lruCache.put("003", "user info of 3");
        lruCache.put("004", "user info of 4");
        lruCache.put("005", "user info of 5");
        lruCache.remove("003");
        System.out.println(lruCache.get("001"));
        lruCache.put("004", "user info of 2");
        lruCache.put("006", "user info of 6");
        System.out.println(lruCache.get("001"));
        System.out.println(lruCache.get("006"));
    }
}
