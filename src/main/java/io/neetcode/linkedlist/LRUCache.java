package io.neetcode.linkedlist;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value){
            this.key = key;
            this.value = value;
        }
    }

    private final Map<Integer, Node> map;
    private final int capacity;
    private final Node head;
    private final Node tail;

    public LRUCache(int capacity) {
        this.map = new HashMap<>();
        this.capacity = capacity;
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if(! map.containsKey(key)) return -1;
        Node node = map.get(key);
        remove(node);
        insertAtTail(node);
        return node.value;
    }

    public void put(int key, int value) {
        if(map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            remove(node);
            insertAtTail(node);
        } else {
            Node node = new Node(key, value);
            map.put(key, node);
            insertAtTail(node);
            if(map.size() > capacity){
                Node newNode = head.next;
                remove(newNode);
                map.remove(newNode.key);
            }
        }
    }

    private void remove(Node node){
        Node prevNode = node.prev;
        Node nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }

    private void insertAtTail(Node node){
        Node prevNode = tail.prev;
        prevNode.next = node;
        node.prev = prevNode;
        node.next = tail;
        tail.prev = node;
    }
}
