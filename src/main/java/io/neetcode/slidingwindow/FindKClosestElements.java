package io.neetcode.slidingwindow;

import java.util.ArrayList;
import java.util.List;

public class FindKClosestElements {

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> ans = new ArrayList<>();
        int i = 0, j = arr.length - 1;
        while (j - i + 1 > k) {
            int l = Math.abs(arr[i] - x);
            int r = Math.abs(arr[j] - x);
            if (l > r)
                i++;
            else
                j--;
        }
        for (int a = i; a <= j; a++) {
            ans.add(arr[a]);
        }
        return ans;
    }

    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int l = 0;
        int r = arr.length - k;
        while (l < r) {
            int m = l + (r - l) / 2;
            if (x - arr[m] > arr[m + k] - x)
                l = m + 1;
            else
                r = m;
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = l; i < l + k; i++) {
            ans.add(arr[i]);
        }
        return ans;
    }
}
