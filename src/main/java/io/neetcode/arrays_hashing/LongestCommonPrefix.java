package io.neetcode.arrays_hashing;

public class LongestCommonPrefix {
    //vertical comparing
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

    /* --horizontal comparing
    public String longestCommonPrefix(String[] strs) {
        if(strs.length==0 && strs==null) return "";
        if(strs.length==1) return strs[0];
        String ans = strs[0];
        for(int i=1; i<strs.length; i++) {
            while(strs[i].indexOf(ans)!=0) {
                ans=ans.substring(0,ans.length()-1);
                if(ans.isEmpty()) return "";
            }
        }
        return ans;
    }
    */
}
