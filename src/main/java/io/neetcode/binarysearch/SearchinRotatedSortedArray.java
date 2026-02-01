package io.neetcode.binarysearch;

public class SearchinRotatedSortedArray {
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] > nums[r]) l = mid + 1;
            else r = mid;
        }
        if (nums[l] == target) return l;
        else if (target >= nums[l] && target <= nums[nums.length - 1]) r = nums.length - 1;
        else {
            r = l - 1;
            l = 0;
        }
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) l = mid + 1;
            else r = mid - 1;
        }
        return -1;
    }
}
