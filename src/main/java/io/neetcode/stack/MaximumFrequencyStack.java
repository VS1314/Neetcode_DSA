package io.neetcode.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class FreqStack {

    private Map<Integer, Stack<Integer>> stack;
    private Map<Integer, Integer> freq;
    private int maxfreq;

    public FreqStack() {
        stack = new HashMap<>();
        freq = new HashMap<>();
        maxfreq = 0;
    }

    public void push(int val) {
        freq.put(val, freq.getOrDefault(val, 0) + 1);
        maxfreq = Math.max(maxfreq, freq.get(val));
        stack.putIfAbsent(freq.get(val), new Stack<>());
        stack.get(freq.get(val)).push(val);
    }

    public int pop() {
        int ans = stack.get(maxfreq).pop();
        freq.put(ans, freq.get(ans) - 1);
        if (stack.get(maxfreq).isEmpty())
            maxfreq--;
        return ans;
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */

public class MaximumFrequencyStack {

    private Map<Integer, Stack<Integer>> ms;
    private Map<Integer, Integer> map;
    private int freq;

    public MaximumFrequencyStack() {
        ms = new HashMap<>();
        map = new HashMap<>();
        freq = 0;
    }

    public void push(int val) {
        int f = map.getOrDefault(val, 0) + 1;
        map.put(val, f);
        if (!ms.containsKey(f)) {
            ms.put(f, new Stack<>());
        }
        ms.get(f).push(val);
        freq = Math.max(f, freq);
    }

    public int pop() {
        int f = ms.get(freq).pop();
        map.put(f, map.get(f) - 1);
        if (ms.get(freq).isEmpty())
            freq--;
        return f;
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */
