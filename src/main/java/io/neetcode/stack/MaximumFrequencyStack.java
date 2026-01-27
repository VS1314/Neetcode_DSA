package io.neetcode.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
        if (ms.get(freq).isEmpty()) freq--;
        return f;
    }
}

/**
 * Your FreqStack object will be instantiated and called as such:
 * FreqStack obj = new FreqStack();
 * obj.push(val);
 * int param_2 = obj.pop();
 */

