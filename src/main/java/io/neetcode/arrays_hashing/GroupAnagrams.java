package io.neetcode.arrays_hashing;

import java.util.*;

public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> res = new HashMap<>();
        for (String s : strs) {
            int[] ch = new int[26];
            for (char c : s.toCharArray()) {
                ch[c - 'a']++;
            }
            String key = Arrays.toString(ch);
            res.putIfAbsent(key, new ArrayList<>());
            res.get(key).add(s);
        }
        return new ArrayList<>(res.values());
    }
}
