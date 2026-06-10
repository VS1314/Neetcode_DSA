package io.neetcode.slidingwindow;

public class BestTimetoBuyandSellStock {
    public int maxProfit(int[] prices) {
        int min = Integer.MAX_VALUE, profit = 0;
        for(int i=0; i<prices.length; i++){
            min = Math.min(min, prices[i]);
            profit = Math.max(profit, Math.abs(min-prices[i]));
        }
        return profit;
    }
}
