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

    public boolean checkInclusion(String s1, String s2) {
        if(s1.length()>s2.length()) return false;
        int[] arr = new int[26];
        for(int i=0; i<s1.length(); i++){
            arr[s1.charAt(i)-'a']++;
        }
        for(int i =0, j=0; j<s2.length(); j++){
            char c = s2.charAt(j);
            arr[c-'a']--;
            if(j-i+1>s1.length()) {
                arr[s2.charAt(i)-'a']++;
                i++;
            }
            if(j-i+1==s1.length()) {
                if(zeros(arr)) return true;
            }
        }
        return false;
    }

    private boolean zeros(int[] arr){
        for(int i : arr){
            if(i!=0) return false;
        }
        return true;
    }
}
