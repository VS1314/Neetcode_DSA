package io.neetcode.stack;

import java.util.Stack;

public class ImplementQueueusingStacks {
    class MyQueue {
        private Stack<Integer> i;
        private Stack<Integer> o;

        public MyQueue() {
            i = new Stack<>();
            o = new Stack<>();
        }

        public void push(int x) {
            i.push(x);
        }

        public int pop() {
            if (o.isEmpty()) {
                while (!i.isEmpty()) {
                    o.push(i.pop());
                }
            }
            return o.pop();
        }

        public int peek() {
            if (o.isEmpty()) {
                while (!i.isEmpty()) {
                    o.push(i.pop());
                }
            }
            return o.peek();
        }

        public boolean empty() {
            return i.isEmpty() && o.isEmpty();
        }
    }

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
}
