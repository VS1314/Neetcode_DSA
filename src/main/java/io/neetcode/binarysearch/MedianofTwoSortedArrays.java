package io.neetcode.binarysearch;

public class MedianofTwoSortedArrays {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        if (n > m) return findMedianSortedArrays(nums2, nums1);
        int l = 0, r = n;
        while (l <= r) {
            int m1 = (l + r) / 2; //to get mid of min length arr
            int m2 = (n + m + 1) / 2 - m1; //to get mid of max length arr
            int l1 = (m1 == 0) ? Integer.MIN_VALUE : nums1[m1 - 1];
            int r1 = (m1 == n) ? Integer.MAX_VALUE : nums1[m1];
            int l2 = (m2 == 0) ? Integer.MIN_VALUE : nums2[m2 - 1];
            int r2 = (m2 == m) ? Integer.MAX_VALUE : nums2[m2];
            if (l1 > r2) r = m1 - 1;
            else if (l2 > r1) l = m1 + 1;
            else {
                if ((n + m) % 2 == 0) return (Math.max(l1, l2) + Math.min(r1, r2)) / 2.0;
                else return Math.max(l1, l2);
            }
        }
        return 0.0;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {//O(m+n)
        int n = nums1.length, m = nums2.length;
        int nm = n + m;
        int idx2 = nm / 2, ide2 = -1;
        int idx1 = idx2 - 1, ide1 = -1;
        int i = 0, j = 0;
        int count = 0;
        while (i < n && j < m) {
            if (nums1[i] < nums2[j]) {
                if (count == idx1) ide1 = nums1[i];
                if (count == idx2) ide2 = nums1[i];
                count++;
                i++;
            } else {
                if (count == idx1) ide1 = nums2[j];
                if (count == idx2) ide2 = nums2[j];
                count++;
                j++;
            }
        }
        while (i < n) {
            if (count == idx1) ide1 = nums1[i];
            if (count == idx2) ide2 = nums1[i];
            count++;
            i++;
        }
        while (j < m) {
            if (count == idx1) ide1 = nums2[j];
            if (count == idx2) ide2 = nums2[j];
            count++;
            j++;
        }
        if (nm % 2 != 0) return ide2;
        return (double) (ide1 + ide2) / 2;
    }
}
