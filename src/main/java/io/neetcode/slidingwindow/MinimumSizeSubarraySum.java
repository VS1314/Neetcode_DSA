package io.neetcode.slidingwindow;

public class MinimumSizeSubarraySum {
    public int minSubArrayLen(int target, int[] nums) {
        int sum = 0, min = Integer.MAX_VALUE, start = 0;
        for (int end = 0; end < nums.length; end++) {
            sum += nums[end];
            while (sum >= target) {
                min = Math.min(min, end - start + 1);
                sum -= nums[start];
                start++;
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;

        // Step 1: Build prefix sum
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        int minLen = Integer.MAX_VALUE;

        // Step 2: For each prefix[i], binary search
        for (int i = 0; i < n; i++) {
            int required = prefix[i] + target;

            int j = binarySearch(prefix, required);

            if (j != -1) {
                minLen = Math.min(minLen, j - i);
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    // Step 3: Binary search for smallest index >= target
    private int binarySearch(int[] prefix, int target) {
        int left = 0, right = prefix.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (prefix[mid] >= target) {
                result = mid;
                right = mid - 1; // search left side
            } else {
                left = mid + 1;
            }
        }

        return result;
    }
}
