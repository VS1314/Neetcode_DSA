package io.neetcode.linkedlist;

public class DesignCircularQueue {

    class MyCircularQueue {

        private int[] queue;
        private int size, top, bottom, k;

        public MyCircularQueue(int k) {
            queue = new int[k];
            size = 0;
            top = 0;
            bottom = -1;
            this.k = k;
        }

        public boolean enQueue(int value) {
            if (isFull())
                return false;
            else {
                bottom = (bottom + 1) % k;
                queue[bottom] = value;
                size++;
                return true;
            }
        }

        public boolean deQueue() {
            if (isEmpty())
                return false;
            else {
                top = (top + 1) % k;
                size--;
                return true;
            }
        }

        public int Front() {
            if (!isEmpty())
                return queue[top];
            return -1;
        }

        public int Rear() {
            if (!isEmpty())
                return queue[bottom];
            return -1;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == k;
        }
    }

    /**
     * Your MyCircularQueue object will be instantiated and called as such:
     * MyCircularQueue obj = new MyCircularQueue(k);
     * boolean param_1 = obj.enQueue(value);
     * boolean param_2 = obj.deQueue();
     * int param_3 = obj.Front();
     * int param_4 = obj.Rear();
     * boolean param_5 = obj.isEmpty();
     * boolean param_6 = obj.isFull();
     */

    class MyCircularQueue {
        class Node {
            int val;
            Node next;

            Node(int val) {
                this.val = val;
            }
        }

        private int capacity;
        private int count;
        private Node head, tail;

        public MyCircularQueue(int k) {
            this.capacity = k;
            this.count = 0;
            this.head = null;
            this.tail = null;
        }

        public boolean enQueue(int value) {
            if (isFull())
                return false;
            Node curr = new Node(value);
            if (isEmpty())
                head = tail = curr;
            else {
                tail.next = curr;
                tail = curr;
            }
            count++;
            return true;
        }

        public boolean deQueue() {
            if (isEmpty())
                return false;
            head = head.next;
            count--;
            if (isEmpty())
                tail = null;
            return true;
        }

        public int Front() {
            return isEmpty() ? -1 : head.val;
        }

        public int Rear() {
            return isEmpty() ? -1 : tail.val;
        }

        public boolean isEmpty() {
            return count == 0;
        }

        public boolean isFull() {
            return count == capacity;
        }
    }

    /**
     * Your MyCircularQueue object will be instantiated and called as such:
     * MyCircularQueue obj = new MyCircularQueue(k);
     * boolean param_1 = obj.enQueue(value);
     * boolean param_2 = obj.deQueue();
     * int param_3 = obj.Front();
     * int param_4 = obj.Rear();
     * boolean param_5 = obj.isEmpty();
     * boolean param_6 = obj.isFull();
     */
}
