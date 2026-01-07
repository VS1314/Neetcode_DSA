package io.neetcode.arrays_hashing;

public class ConcatenationofArray {
    public int[] getConcatenation(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n * 2];
        int i = 0;
        while (i < n) {
            ans[i] = nums[i];
            ans[i + n] = ans[i];
            i++;
        }
        return ans;
    }
}
