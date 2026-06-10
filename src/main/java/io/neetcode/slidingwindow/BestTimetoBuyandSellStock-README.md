# Best Time to Buy and Sell Stock

## Problem Description

**Difficulty**: Easy

You are given an integer array `prices` where `prices[i]` is the price of NeetCoin on the i-th day.

You may choose a **single day** to buy one NeetCoin and choose a **different day in the future** to sell it.

Return the **maximum profit** you can achieve. You may choose to **not make any transactions**, in which case the profit would be `0`.

## Examples

### Example 1:
```
Input: prices = [10,1,5,6,7,1]
Output: 6
Explanation: 
Buy on day 1 (price = 1) and sell on day 4 (price = 7)
Profit = 7 - 1 = 6
```

### Example 2:
```
Input: prices = [10,8,7,5,2]
Output: 0
Explanation: 
Prices keep decreasing. No profitable transaction possible.
Return 0 (choose not to trade).
```

### Example 3:
```
Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation:
Buy on day 1 (price = 1) and sell on day 4 (price = 6)
Profit = 6 - 1 = 5
```

## Constraints
- 1 <= prices.length <= 100,000
- 0 <= prices[i] <= 10,000

**Recommended Complexity**: O(n) time, O(1) space

---

## Pattern Recognition

**Primary Pattern**: **One-Pass Greedy (Track Minimum + Maximum Profit)**

**Why This Pattern?**
- Need to find best buy and sell days (buy before sell)
- Profit = sell price - buy price
- Want to maximize profit
- Can solve in single pass by tracking minimum price seen so far

**Key Insight**: For Each Selling Day, Best Buying Day is Minimum Price Before It
```
Problem: maximize (prices[sell] - prices[buy]) where buy < sell

Reframe: For each position i (potential selling day):
  Best buying day is the day with MINIMUM price before day i
  
  profit[i] = prices[i] - min(prices[0..i-1])
  
  Answer = max(profit[0..n-1])

Example:
  prices = [10, 1, 5, 6, 7, 1]
  
  Day 0: price=10, minBefore=∞, profit=0 (no previous day)
  Day 1: price=1, minBefore=10, profit=1-10=-9 (negative)
  Day 2: price=5, minBefore=1, profit=5-1=4
  Day 3: price=6, minBefore=1, profit=6-1=5
  Day 4: price=7, minBefore=1, profit=7-1=6 ✓ (best!)
  Day 5: price=1, minBefore=1, profit=1-1=0
  
  Maximum profit = 6
```

**Why One Pass Works?**
```
At each position i, we need two things:
  1. Minimum price seen so far (best buy price)
  2. Maximum profit seen so far
  
Both can be updated in O(1) as we iterate:
  minPrice = min(minPrice, prices[i])
  maxProfit = max(maxProfit, prices[i] - minPrice)

No need to revisit previous positions!
```

**Critical Detail**: Can Choose Not to Trade
```
If all profits are negative (prices always decrease):
  Return 0 instead of negative profit
  
Handled by:
  Initialize maxProfit = 0
  Only update if profit > 0
  
Example:
  prices = [10, 8, 7, 5, 2]
  All decreasing → all profits negative
  Return 0 (don't trade)
```

**Related Patterns**:
1. **Greedy Algorithm** — Track min, calculate profit
2. **Prefix Minimum** — Track minimum seen so far
3. **Kadane's Algorithm** — Similar single-pass maximum finding
4. **Stock Problems** — Series with different constraints

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Try all pairs (buy, sell) with buy < sell
  → For each day i, try selling on every day j > i
  → O(n²) time
  → Too slow for n=100,000!

Greedy One-Pass:
  → Track minimum price seen so far
  → Calculate profit at each position
  → Update maximum profit
  → O(n) time
  → Optimal! ✓
```

**The Greedy Strategy**:
```
Key observation:
  To maximize (sell - buy), we want:
    - Smallest possible buy price
    - Largest possible sell price
    - Buy before sell
  
For each position i (potential sell day):
  Best buy day = day with minimum price before i
  profit[i] = prices[i] - minPrice
  
Track maximum profit across all positions:
  maxProfit = max(all profit[i])

Single pass:
  As we iterate left to right:
    1. Update minPrice (best buy price so far)
    2. Calculate profit if we sell today
    3. Update maxProfit if better
```

### Step-by-Step Algorithm

---

#### **Approach 1: One-Pass Greedy (OPTIMAL)**

**Core Idea**:
- Track minimum price seen so far (best buy price)
- Calculate profit if selling at current price
- Track maximum profit

**Algorithm**
```
maxProfit(prices):
    minPrice = infinity
    maxProfit = 0
    
    for each price in prices:
        if price < minPrice:
            minPrice = price  // Better buy price
        else:
            profit = price - minPrice
            maxProfit = max(maxProfit, profit)
    
    return maxProfit
```

**Code Implementation**
```java
class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        
        for (int price : prices) {
            if (price < minPrice) {
                // Found new minimum price (better buy opportunity)
                minPrice = price;
            } else {
                // Calculate profit if we sell at current price
                int profit = price - minPrice;
                maxProfit = Math.max(maxProfit, profit);
            }
        }
        
        return maxProfit;
    }
}
```

**Alternative Clean Implementation**
```java
class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        
        for (int price : prices) {
            minPrice = Math.min(minPrice, price);
            maxProfit = Math.max(maxProfit, price - minPrice);
        }
        
        return maxProfit;
    }
}
```

**Example Walkthrough**

Input: `prices = [10,1,5,6,7,1]`

| Day | Price | minPrice Before | minPrice After | Profit (price - minPrice) | maxProfit |
|-----|-------|-----------------|----------------|---------------------------|-----------|
| 0 | 10 | ∞ (MAX_VALUE) | 10 | 0 (can't sell before buy) | 0 |
| 1 | 1 | 10 | 1 | 0 (1-10=-9, but max(0,-9)=0) | 0 |
| 2 | 5 | 1 | 1 | 5-1=4 | 4 |
| 3 | 6 | 1 | 1 | 6-1=5 | 5 |
| 4 | 7 | 1 | 1 | 7-1=6 | 6 |
| 5 | 1 | 1 | 1 | 1-1=0 | 6 |

**Output:** `6`

**Complexity Analysis**
- **Time Complexity**: O(n) — Single pass through array
- **Space Complexity**: O(1) — Only two variables

---

#### **Approach 2: Dynamic Programming (ALTERNATIVE)**

**Core Idea**: Track minimum price and maximum profit at each position.

**Code Implementation**
```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (n == 0) return 0;
        
        int[] minPrice = new int[n];
        int[] maxProfit = new int[n];
        
        minPrice[0] = prices[0];
        maxProfit[0] = 0;
        
        for (int i = 1; i < n; i++) {
            minPrice[i] = Math.min(minPrice[i - 1], prices[i]);
            maxProfit[i] = Math.max(maxProfit[i - 1], prices[i] - minPrice[i - 1]);
        }
        
        return maxProfit[n - 1];
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n)
- **Space Complexity**: O(n) — Two arrays
- **Why Not Optimal**: Uses extra space

---

#### **Approach 3: Brute Force (NOT OPTIMAL)**

**Core Idea**: Try all pairs of buy and sell days.

**Code Implementation**
```java
class Solution {
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                int profit = prices[j] - prices[i];
                maxProfit = Math.max(maxProfit, profit);
            }
        }
        
        return maxProfit;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n²) — Nested loops
- **Space Complexity**: O(1)
- **Why Not Optimal**: Too slow

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | DP | **One-Pass Greedy** |
|-------------|-------------|----|--------------------|
| Time complexity | O(n²) ❌ | O(n) ✓ | **O(n) ✅** |
| Space complexity | O(1) ✓ | O(n) ❌ | **O(1) ✅** |
| Code simplicity | Simple | Medium | **Clean ✅** |
| Optimal | ❌ | Partial | **✅** |

**Winner**: **One-Pass Greedy** — optimal time and space!

### Why Tracking Minimum Works?

```
Mathematical proof:
  profit = prices[sell] - prices[buy]
  
To maximize profit with fixed sell day:
  Need to minimize buy price
  → Best buy day = day with minimum price before sell day

For each position i as sell day:
  profit[i] = prices[i] - min(prices[0..i-1])
  
We can track min(prices[0..i-1]) as we iterate:
  minPrice = running minimum
  
Result: O(n) time, O(1) space!
```

### Why Greedy Choice is Optimal?

```
Greedy choice: Always use minimum price seen so far as buy price

Proof by contradiction:
  Assume optimal solution uses non-minimum buy price B
  Let M be the minimum price before sell day S
  
  If B > M:
    Profit with B = S - B
    Profit with M = S - M
    Since M < B: S - M > S - B
    → Using M gives better profit! ✗
    
Conclusion: Using minimum is always optimal!
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Day**
```java
Input: prices = [5]
Output: 0
Explanation: Can't buy and sell on same day.
```

### 2. **Strictly Decreasing**
```java
Input: prices = [10,9,8,7,6,5,4,3,2,1]
Output: 0
Explanation: All profits negative, return 0.
```

### 3. **Strictly Increasing**
```java
Input: prices = [1,2,3,4,5,6,7,8,9,10]
Output: 9
Explanation: Buy at 1, sell at 10.
```

### 4. **All Same Price**
```java
Input: prices = [5,5,5,5,5]
Output: 0
Explanation: No profit possible.
```

### 5. **Valley Then Peak**
```java
Input: prices = [5,1,10]
Output: 9
Explanation: Buy at 1, sell at 10.
```

### 6. **Peak Then Valley**
```java
Input: prices = [10,1,5]
Output: 4
Explanation: Can't buy at 10, must buy after. Buy at 1, sell at 5.
```

### 7. **Multiple Valleys**
```java
Input: prices = [3,1,4,1,5]
Output: 4
Explanation: Buy at first 1, sell at 5. (Or buy at second 1, sell at 5)
```

### 8. **Zero Prices**
```java
Input: prices = [0,0,0]
Output: 0
Explanation: No profit.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Handling Negative Profit**
```java
// WRONG - doesn't initialize maxProfit to 0
int maxProfit = Integer.MIN_VALUE;  // WRONG!

for (int price : prices) {
    // ...
    maxProfit = Math.max(maxProfit, price - minPrice);
}
return maxProfit;
```

**Why wrong**: When all prices decrease, all profits are negative, but we should return 0!

**Dry run failure for prices=[10,9,8,7]:**
```
Day 0: minPrice=10, profit=0
Day 1: minPrice=9, profit=9-10=-1
Day 2: minPrice=8, profit=8-9=-1
Day 3: minPrice=7, profit=7-8=-1

maxProfit = -1 (WRONG! Should be 0)
```

**Fix**: Initialize maxProfit to 0
```java
int maxProfit = 0;
```

### ❌ **MISTAKE 2: Comparing with Wrong minPrice**
```java
// WRONG - doesn't update minPrice before calculating profit
int minPrice = Integer.MAX_VALUE;
int maxProfit = 0;

for (int price : prices) {
    int profit = price - minPrice;  // Calculate first
    maxProfit = Math.max(maxProfit, profit);
    minPrice = Math.min(minPrice, price);  // WRONG! Update after
}
```

**Why wrong**: Calculates profit before updating minPrice, so first iteration uses Integer.MAX_VALUE as buy price!

**Dry run failure for prices=[7,1,5]:**
```
Day 0: price=7
  profit = 7 - MAX_VALUE (huge negative!)
  maxProfit = max(0, negative) = 0
  minPrice = 7

Day 1: price=1
  profit = 1 - 7 = -6
  maxProfit = 0
  minPrice = 1

Day 2: price=5
  profit = 5 - 1 = 4
  maxProfit = 4 ✓ (works but only by luck!)
```

**Fix**: Update minPrice before or at same time as calculating profit
```java
minPrice = Math.min(minPrice, price);
maxProfit = Math.max(maxProfit, price - minPrice);
```

### ❌ **MISTAKE 3: Trying to Sell Before Buy**
```java
// WRONG - allows selling before buying
int maxProfit = 0;
for (int i = 0; i < prices.length; i++) {
    for (int j = 0; j < prices.length; j++) {  // WRONG! j can be < i
        maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
    }
}
```

**Why wrong**: Allows j < i, meaning sell before buy!

**Fix**: Ensure j > i
```java
for (int j = i + 1; j < prices.length; j++) { ... }
```

### ❌ **MISTAKE 4: Not Updating maxProfit**
```java
// WRONG - only updates minPrice, forgets maxProfit
int minPrice = Integer.MAX_VALUE;
int maxProfit = 0;

for (int price : prices) {
    minPrice = Math.min(minPrice, price);
    // Missing: maxProfit = Math.max(maxProfit, price - minPrice);
}
return maxProfit;  // Returns 0 always!
```

**Why wrong**: Never calculates or updates profit!

**Fix**: Update maxProfit in loop
```java
maxProfit = Math.max(maxProfit, price - minPrice);
```

### ❌ **MISTAKE 5: Using Else-If Chain That Skips Profit Update**
```java
// WRONG - only updates one value per iteration
for (int price : prices) {
    if (price < minPrice) {
        minPrice = price;
    } else if (price - minPrice > maxProfit) {  // WRONG! Only checks when price >= minPrice
        maxProfit = price - minPrice;
    }
}
```

**Why wrong**: When price equals minPrice on a later day, we skip the profit check entirely (neither condition true).

**Dry run failure for prices=[5,1,3,1,2]:**
```
Day 0: price=5, minPrice=∞→5, maxProfit=0
Day 1: price=1, minPrice=5→1, maxProfit=0 (skipped profit check)
Day 2: price=3, profit=3-1=2, maxProfit=2 ✓
Day 3: price=1, neither condition! maxProfit=2 (didn't check profit=1-1=0)
Day 4: price=2, profit=2-1=1, maxProfit=2
```

While this specific case works, the logic is flawed.

**Fix**: Always update both independently
```java
for (int price : prices) {
    minPrice = Math.min(minPrice, price);
    maxProfit = Math.max(maxProfit, price - minPrice);
}
```

### ❌ **MISTAKE 6: Not Handling Edge Case of Empty or Single Element**
```java
// WRONG - doesn't check for edge cases
public int maxProfit(int[] prices) {
    int minPrice = prices[0];  // Crashes if empty!
    int maxProfit = 0;
    
    for (int i = 1; i < prices.length; i++) {
        maxProfit = Math.max(maxProfit, prices[i] - minPrice);
        minPrice = Math.min(minPrice, prices[i]);
    }
    return maxProfit;
}
```

**Why wrong**: Starting with `prices[0]` assumes array has elements. While constraints guarantee length >= 1, better practice is robust initialization.

**Better approach**: Use Integer.MAX_VALUE
```java
int minPrice = Integer.MAX_VALUE;
for (int price : prices) {
    minPrice = Math.min(minPrice, price);
    maxProfit = Math.max(maxProfit, price - minPrice);
}
```

### ❌ **MISTAKE 7: Returning Negative Profit**
```java
// WRONG - doesn't ensure profit >= 0
return price - minPrice;  // Returns last day's profit, not maximum!
```

**Why wrong**: Returns wrong value!

**Fix**: Return maxProfit
```java
return maxProfit;
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Loop through prices | O(n) | Visit each element once |
| Update minPrice | O(1) | Simple comparison |
| Calculate profit | O(1) | Simple subtraction |
| Update maxProfit | O(1) | Simple comparison |
| **Total** | **O(n)** | Linear time |

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| minPrice variable | O(1) | One integer |
| maxProfit variable | O(1) | One integer |
| **Total** | **O(1)** | Constant space |

**Why O(n) Time is Optimal:**
- Must examine each price at least once
- Can't determine optimal buy/sell without seeing all prices
- O(n) is optimal

---

## Visualization

### Complete Example Walkthrough

**Input:** `prices = [7, 1, 5, 3, 6, 4]`

**Goal:** Find maximum profit by buying once and selling once later.

---

**Initial State:**
```
minPrice = ∞ (Integer.MAX_VALUE)
maxProfit = 0
```

---

**Day 0: price = 7**
```
prices = [7, 1, 5, 3, 6, 4]
          ↑
          
Current price: 7
minPrice: ∞ → 7 (new minimum)
Profit: 7 - 7 = 0
maxProfit: max(0, 0) = 0

State: minPrice=7, maxProfit=0
```

---

**Day 1: price = 1**
```
prices = [7, 1, 5, 3, 6, 4]
             ↑
             
Current price: 1
minPrice: 7 → 1 (new minimum!)
Profit: 1 - 1 = 0
maxProfit: max(0, 0) = 0

State: minPrice=1, maxProfit=0
```

---

**Day 2: price = 5**
```
prices = [7, 1, 5, 3, 6, 4]
                ↑
                
Current price: 5
minPrice: 1 (no change)
Profit: 5 - 1 = 4
maxProfit: max(0, 4) = 4 ✓

State: minPrice=1, maxProfit=4

Interpretation: If we bought at 1 and sell at 5, profit = 4
```

---

**Day 3: price = 3**
```
prices = [7, 1, 5, 3, 6, 4]
                   ↑
                   
Current price: 3
minPrice: 1 (no change)
Profit: 3 - 1 = 2
maxProfit: max(4, 2) = 4 (no change)

State: minPrice=1, maxProfit=4
```

---

**Day 4: price = 6**
```
prices = [7, 1, 5, 3, 6, 4]
                      ↑
                      
Current price: 6
minPrice: 1 (no change)
Profit: 6 - 1 = 5
maxProfit: max(4, 5) = 5 ✓ (new max!)

State: minPrice=1, maxProfit=5

Interpretation: If we bought at 1 and sell at 6, profit = 5 (better!)
```

---

**Day 5: price = 4**
```
prices = [7, 1, 5, 3, 6, 4]
                         ↑
                         
Current price: 4
minPrice: 1 (no change)
Profit: 4 - 1 = 3
maxProfit: max(5, 3) = 5 (no change)

State: minPrice=1, maxProfit=5
```

---

**Final Result:** `maxProfit = 5`

**Optimal Transaction:** Buy at price 1 (day 1), sell at price 6 (day 4)

### Why This Works

```
Visual representation:
       6
     5   
 7       4
   1   3  

The key insight:
  - We want to buy at the LOWEST point
  - And sell at a HIGHER point AFTER that
  
By tracking minimum price:
  - We always know the best buy price so far
  - For each sell day, we calculate profit with that best buy
  - We track the maximum profit across all days
  
Result: Find optimal buy and sell in one pass!
```

---

## Comparison of Approaches

| Approach | Time | Space | Optimal | Notes |
|----------|------|-------|---------|-------|
| Brute Force | O(n²) | O(1) | ❌ | Try all pairs |
| DP (arrays) | O(n) | O(n) | Partial | Stores prefix mins |
| **One-Pass Greedy** | **O(n)** | **O(1)** | **✅** | **Track min and max** |

**Recommendation**: Always use **One-Pass Greedy** — optimal time and space!

---

## Key Takeaways

1. **Track minimum price** — best buy price seen so far
2. **Calculate profit at each position** — current price - minimum
3. **Track maximum profit** — best profit seen so far
4. **One pass suffices** — no need to look back
5. **Initialize maxProfit to 0** — handles all-decreasing case
6. **Greedy is optimal** — using minimum buy price always best
7. **O(n) time, O(1) space** — optimal solution

---

## Interview Tips

**What to say in an interview:**

> "This is a single-pass greedy problem. The key insight is that for any potential selling day, the best buying day is the one with the minimum price before it. I'll iterate through the prices once, tracking two things: the minimum price seen so far (best buy price) and the maximum profit seen so far. For each price, I first check if it's a new minimum (better buy opportunity). Then I calculate what profit I'd get if I sold at the current price using the best buy price, and update the maximum profit if it's better. This gives O(n) time with one pass and O(1) space."

**Key points to mention:**
1. **Minimum tracking** — best buy price so far
2. **Profit calculation** — current price minus minimum
3. **Maximum tracking** — best profit so far
4. **One pass** — iterate left to right once
5. **Complexity** — O(n) time, O(1) space

**If asked about alternatives:**
> "I could try all pairs of buy and sell days with nested loops, but that's O(n²). Or use dynamic programming with arrays to store prefix minimums, but that uses O(n) space. The greedy one-pass approach is optimal at O(n) time and O(1) space."

**Common Follow-ups:**
- "What if you can trade multiple times?" → Different problem (Best Time to Buy and Sell Stock II)
- "What about transaction fees?" → Subtract fee from profit
- "What if you can only trade k times?" → DP problem (Best Time to Buy and Sell Stock IV)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Best Time to Buy and Sell Stock** | Easy | **One-Pass Greedy** | **This problem** ← **One transaction** |
| Best Time to Buy and Sell Stock II | Easy | Greedy | Unlimited transactions |
| Best Time to Buy and Sell Stock III | Hard | DP | At most 2 transactions |
| Best Time to Buy and Sell Stock IV | Hard | DP | At most k transactions |
| Best Time to Buy and Sell with Cooldown | Medium | DP | Cooldown period |
| Best Time to Buy and Sell with Fee | Medium | DP | Transaction fee |

**Pattern Progression**:
1. **Stock I** (this problem) — One transaction, track min
2. **Stock II** — Multiple transactions, greedy sum
3. **Stock III/IV** — Limited transactions, DP
4. **With constraints** — Cooldown/fees, DP

---

## Final Pattern Label

✅ **One-Pass Greedy (Track Minimum + Maximum Profit)**

**Remember:** Track the minimum price seen so far (best buy price). For each day, calculate profit if we sell today (price - minPrice). Track the maximum profit across all days. Initialize maxProfit to 0 to handle the case where all prices decrease (choose not to trade). O(n) time, O(1) space. This is the optimal solution!
