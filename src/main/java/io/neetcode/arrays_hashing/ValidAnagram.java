package io.neetcode.arrays_hashing;

import java.util.HashMap;

public class ValidAnagram {

    public boolean isAnagram(String s, String t) {
        HashMap<Character,Integer> map = new HashMap<>();
        for(char c : s.toCharArray()){
            map.put(c,map.getOrDefault(c,0)+1);
        }
        for(char c : t.toCharArray()){
            if(map.containsKey(c)){
                map.put(c,map.get(c)-1);
                if(map.get(c)==0) map.remove(c);
            } else return false;
        }
        return map.size()==0;
    }
}
