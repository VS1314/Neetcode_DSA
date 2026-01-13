package io.neetcode.twopointers;

public class MergeStringsAlternately {
    public String mergeAlternately(String word1, String word2) {
        StringBuilder sb = new StringBuilder();
        int i = 0, j = 0;
        while (i < word1.length() && j < word2.length()) {
            sb.append(word1.charAt(i)).append(word2.charAt(j));
            i++;
            j++;
        }
        if (word1.length() < word2.length()) sb.append(word2.substring(j));
        else sb.append(word1.substring(i));
        return sb.toString();
    }
}
