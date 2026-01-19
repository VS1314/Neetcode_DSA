package io.neetcode.stack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ImplementStackUsingQueues {

    class MyStack {
        private Queue<Integer> q;

        public MyStack() {
            q = new LinkedList<>();
        }

        public void push(int x) {
            q.offer(x);
            for (int i = q.size() - 1; i > 0; i--) {
                q.offer(q.poll());
            }
        }

        public int pop() {
            return q.poll();
        }

        public int top() {
            return q.peek();
        }

        public boolean empty() {
            return q.isEmpty();
        }
    }

    /**
     * Your MyStack object will be instantiated and called as such:
     * MyStack obj = new MyStack();
     * obj.push(x);
     * int param_2 = obj.pop();
     * int param_3 = obj.top();
     * boolean param_4 = obj.empty();
     */

    class MyStack {

        private Queue<Integer> q1;
        private Queue<Integer> q2;

        public MyStack() {
            q1 = new LinkedList<>();
            q2 = new LinkedList<>();
        }

        public void push(int x) {
            q2.offer(x);
            while (!q1.isEmpty()) {
                q2.offer(q1.poll());
            }
            Queue<Integer> temp = q1;
            q1 = q2;
            q2 = temp;
        }

        public int pop() {
            return q1.poll();
        }

        public int top() {
            return q1.peek();
        }

        public boolean empty() {
            return q1.isEmpty();
        }
    }

    /**
     * Your MyStack object will be instantiated and called as such:
     * MyStack obj = new MyStack();
     * obj.push(x);
     * int param_2 = obj.pop();
     * int param_3 = obj.top();
     * boolean param_4 = obj.empty();
     */

    class MyStack {

        private Stack<Integer> stack;

        public MyStack() {
            stack = new Stack<>();
        }

        public void push(int x) {
            stack.push(x);
        }

        public int pop() {
            return stack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public boolean empty() {
            return stack.isEmpty();
        }
    }

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */


}
