package io.neetcode.arrays_hashing;

import java.util.ArrayList;
import java.util.List;

public class EncodeandDecodeStrings {
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s.length()).append("#").append(s);
        }
        return sb.toString();
    }

    public List<String> decode(String str) {
        List<String> res = new ArrayList<>();
        int i = 0;
        while (i < str.length()) {
            int j = i;
            while (str.charAt(j) != '#') j++;
            int l = Integer.parseInt(str.substring(i, j));
            j++;
            res.add(str.substring(j, j + l));
            i = j + l;
        }
        return res;
    }
}
