package io.neetcode.linkedlist;

import java.util.HashMap;
import java.util.Map;

public class LFUCache {

    class Node {
        int key;
        int value;
        int freq;
        Node prev, next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    class DoublyLL {
        Node head, tail;
        int size;

        DoublyLL() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        void addToHead(Node node) {
            Node nextnode = head.next;
            head.next = node;
            node.next = nextnode;
            node.prev = head;
            nextnode.prev = node;
            size++;
        }

        void remove(Node node) {
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
            size--;
        }

        Node removeTail() {
            if (size == 0) return null;
            Node prev = tail.prev;
            remove(prev);
            return prev;
        }
    }

    private final Map<Integer, Node> map;
    private final Map<Integer, DoublyLL> fmap;
    private final int capacity;
    private int minFreq;

    public LFUCache(int capacity) {
        map = new HashMap<>();
        fmap = new HashMap<>();
        this.capacity = capacity;
        this.minFreq = 0;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        updateFreq(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) return;
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            updateFreq(node);
        } else {
            if (map.size() >= capacity) {
                DoublyLL mf = fmap.get(minFreq);
                Node evict = mf.removeTail();
                map.remove(evict.key);
            }
            Node node = new Node(key, value);
            map.put(key, node);
            fmap.putIfAbsent(1, new DoublyLL());
            fmap.get(1).addToHead(node);
            minFreq = 1;
        }
    }

    private void updateFreq(Node node) {
        int oldfreq = node.freq;
        DoublyLL oldLL = fmap.get(oldfreq);
        oldLL.remove(node);
        if (oldLL.size == 0) {
            fmap.remove(oldfreq);
            if (oldfreq == minFreq) minFreq++;
        }
        node.freq++;
        fmap.putIfAbsent(node.freq, new DoublyLL());
        fmap.get(node.freq).addToHead(node);
    }

}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
