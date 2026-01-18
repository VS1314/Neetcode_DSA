Perfect — this is one of the cleanest “single-pass greedy” problems and an absolute interview favorite.
Let’s lock the pattern into your head so you never forget it.

🧠 Pattern Recognition
Key constraints

Buy once

Sell after buying

Maximize sell - buy

👉 This is NOT sliding window
👉 This is Greedy + Prefix Minimum

❌ Brute Force (Why it’s bad)
For each day i:
try selling on every future day j > i


⏱ Time: O(n²)
💥 Fails for large inputs

🧠 Key Insight (The Whole Problem)

If today is the selling day, then:

The best buying day is the minimum price seen before today

So:

profit = prices[i] - minPriceSoFar


We track:

minPrice → best buy so far

maxProfit → best profit so far

🔁 One-Pass Strategy
As you scan left → right:

Update minimum price

Calculate profit if sold today

Update max profit

🧪 Dry Run (Example 1)
prices = [10,1,5,6,7,1]

Day	Price	minPrice	profit	maxProfit
0	10	10	0	0
1	1	1	0	0
2	5	1	4	4
3	6	1	5	5
4	7	1	6	6
5	1	1	0	6

✅ Answer = 6

🧪 Dry Run (Example 2)
prices = [10,8,7,5,2]


Prices always fall → profit always negative → return 0

✅ Optimal Java Solution (Greedy)
class Solution {
public int maxProfit(int[] prices) {
int minPrice = Integer.MAX_VALUE;
int maxProfit = 0;

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            } else {
                maxProfit = Math.max(maxProfit, price - minPrice);
            }
        }
        return maxProfit;
    }
}

⏱ Complexity
Metric	Value
Time	O(n)
Space	O(1)
🧠 Why This Works (Interview Explanation)

“For every selling day, the best buying day is the cheapest price before it.
We track that minimum price and compute the best possible profit in one pass.”

💯 Clean, confident explanation.

🚫 Common Mistakes

❌ Using two nested loops
❌ Thinking you must buy and sell on consecutive days
❌ Forgetting you can return 0
❌ Trying sliding window unnecessarily

🔥 Pattern Summary (VERY IMPORTANT)
Problem Type	Pattern
Buy once, sell once	Greedy
Max difference (j > i)	Prefix Min
Stock problems (basic)	Single pass
🔁 Related Problems (Same Family)

Best Time to Buy and Sell Stock II (multiple transactions)

Maximum Subarray (Kadane’s)

Minimum Difference Pair

Container With Most Water

Buy & Sell Stock with Cooldown