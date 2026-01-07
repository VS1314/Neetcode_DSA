package io.neetcode.arrays_hashing;

import java.util.Arrays;

public class DesignHashMap {
    class MyHashMap {

        private int[] res;

        public MyHashMap() {
            res = new int[1000001];
            Arrays.fill(res, -1);
        }

        public void put(int key, int value) {
            res[key] = value;
        }

        public int get(int key) {
            return res[key];
        }

        public void remove(int key) {
            res[key] = -1;
        }
    }

    /**
     * Your MyHashMap object will be instantiated and called as such:
     * MyHashMap obj = new MyHashMap();
     * obj.put(key,value);
     * int param_2 = obj.get(key);
     * obj.remove(key);
     */

    class MyHashMap {

        private static class ListNode {
            int key;
            int value;
            ListNode next;

            ListNode(int key, int value, ListNode next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }

            ListNode() {
                this(-1, -1, null);
            }
        }

        private final ListNode[] map;

        public MyHashMap() {
            map = new ListNode[1000];
            for (int i = 0; i < 1000; i++) {
                map[i] = new ListNode();
            }
        }

        private int hash(int key) {
            return key % map.length;
        }

        public void put(int key, int value) {
            ListNode cur = map[hash(key)];
            while (cur.next != null) {
                if (cur.next.key == key) {
                    cur.next.value = value;
                    return;
                }
                cur = cur.next;
            }
            cur.next = new ListNode(key, value, null);
        }

        public int get(int key) {
            ListNode cur = map[hash(key)];
            while (cur.next != null) {
                if (cur.next.key == key) {
                    return cur.next.value;
                }
                cur = cur.next;
            }
            return -1;
        }

        public void remove(int key) {
            ListNode cur = map[hash(key)];
            while (cur.next != null) {
                if (cur.next.key == key) {
                    cur.next = cur.next.next;
                    return;
                }
                cur = cur.next;
            }
        }
    }
}
