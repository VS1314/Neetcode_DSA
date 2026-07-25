package io.neetcode.linkedlist;

import java.util.HashMap;
import java.util.Map;

class LFUCache {

    class Node {
        int key, value, freq;
        Node prev, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    class DL {
        Node head, tail;
        int size;

        public DL() {
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            this.size = 0;
        }

        public void insertAtTail(Node curr) {
            Node prev = tail.prev;
            prev.next = curr;
            curr.prev = prev;
            curr.next = tail;
            tail.prev = curr;
            size++;
        }

        public void remove(Node curr) {
            Node prev = curr.prev;
            Node next = curr.next;
            prev.next = next;
            next.prev = prev;
            curr.prev = null;
            curr.next = null;
            size--;
        }
    }

    private final Node[] arr;
    private final DL[] farr;
    private final int capacity;
    private int minfreq, arrsize;

    public LFUCache(int capacity) {
        this.arr = new Node[100001];
        this.farr = new DL[200001];
        this.capacity = capacity;
        this.minfreq = 0;
        this.arrsize = 0;
    }

    public int get(int key) {
        if (arr[key] == null)
            return -1;
        Node curr = arr[key];
        farr[curr.freq].remove(curr);
        if (farr[curr.freq].size == 0) {
            farr[curr.freq] = null;
            if (curr.freq == minfreq)
                minfreq++;
        }
        curr.freq++;
        if (farr[curr.freq] == null)
            farr[curr.freq] = new DL();
        farr[curr.freq].insertAtTail(curr);
        return curr.value;
    }

    public void put(int key, int value) {
        if (arr[key] == null) {
            if (arrsize == capacity) {
                Node curr = farr[minfreq].head.next;
                farr[minfreq].remove(curr);
                arr[curr.key] = null;
                arrsize--;
            }
            Node curr = new Node(key, value);
            arr[key] = curr;
            if (farr[curr.freq] == null)
                farr[curr.freq] = new DL();
            farr[curr.freq].insertAtTail(curr);
            minfreq = 1;
            arrsize++;
        } else {
            Node curr = arr[key];
            curr.value = value;
            farr[curr.freq].remove(curr);
            if (farr[curr.freq].size == 0) {
                farr[curr.freq] = null;
                if (curr.freq == minfreq)
                    minfreq++;
            }
            curr.freq++;
            if (farr[curr.freq] == null)
                farr[curr.freq] = new DL();
            farr[curr.freq].insertAtTail(curr);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

class LFUCache {

    class Node {
        int key, value, freq;
        Node prev, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    class DL {
        Node head, tail;
        int size;

        public DL() {
            this.head = new Node(0, 0);
            this.tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            this.size = 0;
        }

        public void insertAtTail(Node curr) {
            Node prev = tail.prev;
            prev.next = curr;
            curr.prev = prev;
            curr.next = tail;
            tail.prev = curr;
            size++;
        }

        public void remove(Node curr) {
            Node prev = curr.prev;
            Node next = curr.next;
            prev.next = next;
            next.prev = prev;
            curr.prev = null;
            curr.next = null;
            size--;
        }
    }

    private final Map<Integer, Node> map;
    private final Map<Integer, DL> fmap;
    private final int capacity;
    private int minfreq;

    public LFUCache(int capacity) {
        this.map = new HashMap<>();
        this.fmap = new HashMap<>();
        this.capacity = capacity;
        this.minfreq = 0;
    }

    public int get(int key) {
        if (!map.containsKey(key))
            return -1;
        Node curr = map.get(key);
        fmap.get(curr.freq).remove(curr);
        if (fmap.get(curr.freq).size == 0) {
            fmap.remove(curr.freq);
            if (curr.freq == minfreq)
                minfreq++;
        }
        curr.freq++;
        fmap.putIfAbsent(curr.freq, new DL());
        fmap.get(curr.freq).insertAtTail(curr);
        return curr.value;
    }

    public void put(int key, int value) {
        if (!map.containsKey(key)) {
            if (map.size() == capacity) {
                Node curr = fmap.get(minfreq).head.next;
                fmap.get(minfreq).remove(curr);
                map.remove(curr.key);
            }
            Node curr = new Node(key, value);
            map.put(key, curr);
            fmap.putIfAbsent(curr.freq, new DL());
            fmap.get(curr.freq).insertAtTail(curr);
            minfreq = 1;
        } else {
            Node curr = map.get(key);
            curr.value = value;
            fmap.get(curr.freq).remove(curr);
            if (fmap.get(curr.freq).size == 0) {
                fmap.remove(curr.freq);
                if (curr.freq == minfreq)
                    minfreq++;
            }
            curr.freq++;
            fmap.putIfAbsent(curr.freq, new DL());
            fmap.get(curr.freq).insertAtTail(curr);
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

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
            if (size == 0)
                return null;
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
        if (!map.containsKey(key))
            return -1;
        Node node = map.get(key);
        updateFreq(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (capacity == 0)
            return;
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
            if (oldfreq == minFreq)
                minFreq++;
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
