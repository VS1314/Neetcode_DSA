package io.neetcode.twopointers;

public class ContainerWithMostWater {
    public int maxArea(int[] heights) {
        int area = 0;
        int l = 0;
        int r = heights.length - 1;
        while (l < r) {
            int h = Math.min(heights[l], heights[r]);
            int w = r - l;
            int a = h * w;
            area = Math.max(area, a);
            if (heights[l] < heights[r]) l++;
            else r--;
        }
        return area;
    }
}
