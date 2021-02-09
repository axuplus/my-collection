package com.utils.algorithm;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class LRUCache {

    private int initSize;
    private Map<Integer, Node<Integer, Integer>> map;
    DoubleLinkedList<Integer, Integer> linkedList;
    public LRUCache(int initSize) {
        this.initSize = initSize;
        map = new HashMap<>();
        linkedList = new DoubleLinkedList<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node<Integer, Integer> node = map.get(key);
        linkedList.removeNode(node);
        linkedList.addHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node<Integer, Integer> node = map.get(key);
            node.value = value;
            map.put(key, node);
            linkedList.removeNode(node);
            linkedList.addHead(node);
        } else {
            if (map.size() == initSize) {
                Node lastNode = linkedList.getLast();
                map.remove(lastNode.key);
                linkedList.removeNode(lastNode);
            }
            Node<Integer, Integer> newNode = new Node<>(key, value);
            map.put(key, newNode);
            linkedList.addHead(newNode);
        }
    }


    // 1：构建Node节点，作为数据载体
    class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        public Node() {
            this.prev = this.next = null;
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.prev = this.next = null;
        }
    }

    // 2：构建一个虚拟的双向链表，里面保存Node
    class DoubleLinkedList<K, V> {
        Node head;
        Node tail;

        // 2.1 构造方法
        public DoubleLinkedList() {
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.prev = head;
        }

        // 2.2 添加到头
        public void addHead(Node node) {
            node.next = head.next;
            //node.prev = tail.prev;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        // 2.3 删除节点
        public void removeNode(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.prev = null;
            node.next = null;
        }

        // 2.4 获得最后一个节点
        public Node getLast() {
            return tail.prev;
        }
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(5);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);
        lruCache.get(1);
        System.out.println(lruCache);
    }
}
