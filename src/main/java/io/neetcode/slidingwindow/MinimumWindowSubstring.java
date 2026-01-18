package io.neetcode.slidingwindow;

import java.util.HashMap;

public class MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        if (t.length() > s.length()) return "";
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int start = 0, min = Integer.MAX_VALUE, cf = 0, ss = 0;
        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0) cf++;
            }
            while (cf == map.size()) {
                if (end - start + 1 < min) {
                    ss = start;
                    min = end - start + 1;
                }
                char ch = s.charAt(start);
                start++;
                if (map.containsKey(ch)) {
                    if (map.get(ch) == 0) cf--;
                    map.put(ch, map.get(ch) + 1);
                }
            }
        }
        if (min == Integer.MAX_VALUE) return "";
        return s.substring(ss, ss + min);
    }
}
