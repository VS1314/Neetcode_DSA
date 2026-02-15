package io.neetcode.binarysearch;


/**
 * // This is MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface MountainArray {
 * public int get(int index) {}
 * public int length() {}
 * }
 */


public class FindinMountainArray {

    public int findInMountainArray(int target, MountainArray mountainArr) {
        int l = 0, r = mountainArr.length() - 1;
        int peak = 0;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (mountainArr.get(mid) < mountainArr.get(mid + 1)) l = mid + 1;
            else r = mid;
        }
        peak = l;
        //search left
        l = 0;
        r = peak;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            int val = mountainArr.get(mid);
            if (val == target) return mid;
            else if (target < val) r = mid - 1;
            else l = mid + 1;
        }
        //search right
        l = peak + 1;
        r = mountainArr.length() - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            int val = mountainArr.get(mid);
            if (val == target) return mid;
            else if (target > val) r = mid - 1;
            else l = mid + 1;
        }
        return -1;
    }
}
