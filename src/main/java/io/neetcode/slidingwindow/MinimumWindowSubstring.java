package io.neetcode.slidingwindow;

import java.util.HashMap;

public class MinimumWindowSubstring {

    public String minWindow(String s, String t) {
        if (s.length() < t.length())
            return "";
        int[] arr = new int[128];
        int match = 0, min = Integer.MAX_VALUE, substrstart = 0;
        for (char c : t.toCharArray()) {
            arr[c]++;
        }
        for (int i = 0, j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            arr[c]--;
            if (arr[c] == 0)
                match++;
            while (match == t.length()) {
                if (j - i + 1 < min) {
                    min = j - i + 1;
                    substrstart = i;
                }
                char a = s.charAt(i++);
                if (arr[a] == 0) {
                    match--;
                }
                arr[a]++;
            }
        }
        return (min == Integer.MAX_VALUE) ? "" : s.substring(substrstart, substrstart + min);
    }

    public String minWindow(String s, String t) {
        if (t.length() > s.length())
            return "";
        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : t.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        int start = 0, min = Integer.MAX_VALUE, cf = 0, ss = 0;
        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0)
                    cf++;
            }
            while (cf == map.size()) {
                if (end - start + 1 < min) {
                    ss = start;
                    min = end - start + 1;
                }
                char ch = s.charAt(start);
                start++;
                if (map.containsKey(ch)) {
                    if (map.get(ch) == 0)
                        cf--;
                    map.put(ch, map.get(ch) + 1);
                }
            }
        }
        if (min == Integer.MAX_VALUE)
            return "";
        return s.substring(ss, ss + min);
    }
}
