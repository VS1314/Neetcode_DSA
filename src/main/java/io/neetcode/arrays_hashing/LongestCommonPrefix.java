package io.neetcode.arrays_hashing;

public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        String result = strs[0];
        for (int i = 1; i < strs.length; i++) {
            int j = 0;
            while (j < Math.min(result.length(), strs[i].length())) {
                if (result.charAt(j) != strs[i].charAt(j)) break;
                j++;
            }
            result = result.substring(0, j);
        }
        return result;
    }
}
