package io.neetcode.slidingwindow;

public class BestTimetoBuyandSellStock {
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        int minprice = prices[0];
        for (int i : prices) {
            maxProfit = Math.max(maxProfit, i - minprice);
            minprice = Math.min(minprice, i);
        }
        return maxProfit;
    }
}
