package io.neetcode.slidingwindow;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMaximum {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n - k + 1];
        Deque<Integer> q = new ArrayDeque<>();
        int s = 0;
        for (int e = 0; e < n; e++) {
            if (!q.isEmpty() && q.peekFirst() <= e - k) q.pollFirst();
            while (!q.isEmpty() && nums[q.peekLast()] < nums[e]) q.pollLast();
            q.offerLast(e);
            if (e >= k - 1) res[s++] = nums[q.peekFirst()];
        }
        return res;
    }
}
