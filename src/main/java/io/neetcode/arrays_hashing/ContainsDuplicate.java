package io.neetcode.arrays_hashing;

import java.util.HashMap;

public class ContainsDuplicate {
    public boolean hasDuplicate(int[] nums) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i : nums){
            if(map.containsKey(i)) return true;
            map.put(i,map.getOrDefault(i,0)+1);
        }

    /*HashSet<Integer> set = new HashSet<>();
    for(int i : nums){
       if( !set.add(i)) return true;
    }*/
        return false;
    }
}
