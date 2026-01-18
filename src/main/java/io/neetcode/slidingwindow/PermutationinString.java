package io.neetcode.slidingwindow;

import java.util.Arrays;

public class PermutationinString {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) return false;
        int[] s1f = new int[26];
        int[] s2f = new int[26];
        for (char c : s1.toCharArray()) {
            s1f[c - 'a']++;
        }
        int start = 0;
        for (int end = 0; end < s2.length(); end++) {
            s2f[s2.charAt(end) - 'a']++;
            if (end - start + 1 > s1.length()) {
                s2f[s2.charAt(start++) - 'a']--;
            }
            if (Arrays.equals(s1f, s2f)) return true;
        }
        return false;
    }
}
