package io.neetcode.arrays_hashing;

import java.util.ArrayList;
import java.util.List;

public class DesignHashset {
    class MyHashSet {

        private List<Integer> result;

        public MyHashSet() {
            result = new ArrayList<>();
        }

        public void add(int key) {
            if (!result.contains(key)) result.add(key);
        }

        public void remove(int key) {
            result.remove(Integer.valueOf(key));
        }

        public boolean contains(int key) {
            return result.contains(key);
        }
    }

    /**
     * Your MyHashSet object will be instantiated and called as such:
     * MyHashSet obj = new MyHashSet();
     * obj.add(key);
     * obj.remove(key);
     * boolean param_3 = obj.contains(key);
     */

    class MyHashSet {
        private boolean[] res;

        public MyHashSet() {
            res = new boolean[1000001];
        }

        public void add(int key) {
            res[key] = true;
        }

        public void remove(int key) {
            res[key] = false;
        }

        public boolean contains(int key) {
            return res[key];
        }
    }

    class MyHashSet {

        private static class ListNode {
            int key;
            ListNode next;

            ListNode(int key) {
                this.key = key;
            }
        }

        private final ListNode[] set;

        public MyHashSet() {
            set = new ListNode[10000];
            for (int i = 0; i < set.length; i++) {
                set[i] = new ListNode(0);
            }
        }

        public void add(int key) {
            ListNode cur = set[key % set.length];
            while (cur.next != null) {
                if (cur.next.key == key) return;
                cur = cur.next;
            }
            cur.next = new ListNode(key);
        }

        public void remove(int key) {
            ListNode cur = set[key % set.length];
            while (cur.next != null) {
                if (cur.next.key == key) {
                    cur.next = cur.next.next;
                    return;
                }
                cur = cur.next;
            }
        }

        public boolean contains(int key) {
            ListNode cur = set[key % set.length];
            while (cur.next != null) {
                if (cur.next.key == key) {
                    return true;
                }
                cur = cur.next;
            }
            return false;
        }
    }

}
