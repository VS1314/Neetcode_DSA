package io.neetcode.linkedlist;

import java.util.HashMap;
import java.util.Map;

class LRUCache {

    class Node {
        int key, value;
        Node prev, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final Node[] arr;
    private final Node head, tail;
    private final int capacity;
    private int size;

    public LRUCache(int capacity) {
        this.arr = new Node[10001];
        this.capacity = capacity;
        this.size = 0;
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (arr[key] == null)
            return -1;
        Node curr = arr[key];
        remove(curr);
        insertAtTail(curr);
        return curr.value;
    }

    public void put(int key, int value) {
        if (arr[key] != null) {
            Node curr = arr[key];
            curr.value = value;
            remove(curr);
            insertAtTail(curr);
        } else {
            Node curr = new Node(key, value);
            arr[key] = curr;
            insertAtTail(curr);
            size++;
            if (size > capacity) {
                Node next = head.next;
                remove(next);
                arr[next.key] = null;
                size--;
            }
        }
    }

    private void remove(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
        node.next = null;
        node.prev = null;
    }

    private void insertAtTail(Node node) {
        Node prev = tail.prev;
        prev.next = node;
        node.prev = prev;
        node.next = tail;
        tail.prev = node;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

public class LRUCache {

    static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
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
        if (!map.containsKey(key))
            return -1;
        Node node = map.get(key);
        remove(node);
        insertAtTail(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            remove(node);
            insertAtTail(node);
        } else {
            Node node = new Node(key, value);
            map.put(key, node);
            insertAtTail(node);
            if (map.size() > capacity) {
                Node newNode = head.next;
                remove(newNode);
                map.remove(newNode.key);
            }
        }
    }

    private void remove(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }

    private void insertAtTail(Node node) {
        Node prevNode = tail.prev;
        prevNode.next = node;
        node.prev = prevNode;
        node.next = tail;
        tail.prev = node;
    }
}
