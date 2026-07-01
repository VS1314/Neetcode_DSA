package io.neetcode.stack;

import java.util.Stack;

class StockSpanner {

    private int[] prices;
    private int[] spans;
    private int top;

    public StockSpanner() {
        prices = new int[10000];
        spans = new int[10000];
        top = 0;
    }

    public int next(int price) {
        int span = 1;
        while (top > 0 && prices[top - 1] <= price) {
            span += spans[top - 1];
            top--;
        }
        prices[top] = price;
        spans[top] = span;
        top++;

        return span;
    }
}

/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */

class OnlineStockSpan {

    private Stack<int[]> s;

    public OnlineStockSpan() {
        s = new Stack<>();
    }

    public int next(int price) {
        int i = 1;
        while (!s.isEmpty() && s.peek()[0] <= price)
            i += s.pop()[1];
        s.push(new int[] { price, i });
        return i;
    }

    /**
     * Your StockSpanner object will be instantiated and called as such:
     * StockSpanner obj = new StockSpanner();
     * int param_1 = obj.next(price);
     */
}
