package io.neetcode.slidingwindow;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMaximum {
    public int[] maxSlidingWindow(int[] nums, int k) {// 1,2,3
        int[] ans = new int[nums.length - k + 1];
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0, j = 0; j < nums.length; j++) {
            while (!q.isEmpty() && nums[j] > nums[q.peekLast()])
                q.removeLast();
            q.addLast(j);
            if (q.peekFirst() < j - k + 1)
                q.removeFirst();
            if (j >= k - 1)
                ans[i++] = nums[q.peekFirst()];
        }
        return ans;
    }
}
