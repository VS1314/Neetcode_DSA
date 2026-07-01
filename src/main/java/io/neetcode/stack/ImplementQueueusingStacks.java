package io.neetcode.stack;

import java.util.Stack;

public class ImplementQueueusingStacks {

    class MyQueue {

        private Stack<Integer> s1;
        private Stack<Integer> s2;

        public MyQueue() {
            s1 = new Stack<>();
            s2 = new Stack<>();
        }

        public void push(int x) {
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
            s1.add(x);
            while (!s2.isEmpty()) {
                s1.push(s2.pop());
            }
        }

        public int pop() {
            return s1.pop();
        }

        public int peek() {
            return s1.peek();
        }

        public boolean empty() {
            return s1.isEmpty();
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

    /**
     * What is "Amortized" Time Complexity?
     * Amortized time means the average time taken per operation over a long sequence of actions.
     * Think of it like buying an annual bus pass for ₹12,000:
     * On Day 1, you pay ₹12,000 (An expensive operation).
     * For the next 364 days, your bus ride costs you ₹0 (Extremely cheap operations).
     * If you look only at the worst-case single day (Day 1), the cost is massive. 
     * But if you average it out across all 365 days, your amortized cost is only about ₹33 per day.
     * In our Amortized Queue structure:
     * A pop() is usually cheap O(1) because elements are sitting ready in s2.
     * Only when s2 runs completely dry do we spend O(N) time transferring all elements from s1.
     * Because an element is only transferred from s1 to s2 exactly once in its entire lifespan, the high cost is spread thin across multiple actions, averaging out to an Amortized O(1) speed per call.
     */
    class MyQueue {//"Amortized" Time Complexity
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
