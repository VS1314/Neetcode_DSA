package io.neetcode.slidingwindow;

public class LongestRepeatingCharacterReplacement {
    public int characterReplacement(String s, int k) {
        int[] freq = new int[26];
        int res = 0, start = 0, max = 0;
        for (int end = 0; end < s.length(); end++) {
            int i = s.charAt(end) - 'A';
            freq[i]++;
            max = Math.max(freq[i], max);
            while ((end - start + 1) - max > k) {
                freq[s.charAt(start) - 'A']--;
                start++;
            }
            res = Math.max(res, end - start + 1);
        }
        return res;
    }
}
