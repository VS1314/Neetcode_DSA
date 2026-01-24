package io.neetcode.stack;

import java.util.Stack;

class OnlineStockSpan {

    private Stack<int[]> s;

    public OnlineStockSpan() {
        s = new Stack<>();
    }

    public int next(int price) {
        int i = 1;
        while (!s.isEmpty() && s.peek()[0] <= price) i += s.pop()[1];
        s.push(new int[]{price, i});
        return i;
    }


/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */
}
