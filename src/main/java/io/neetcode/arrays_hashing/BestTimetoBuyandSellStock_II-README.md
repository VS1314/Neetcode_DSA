# Best Time to Buy and Sell Stock II

## Problem Description

**Difficulty**: Medium

You are given an integer array `prices` where `prices[i]` is the price of a given stock on the `i`-th day.

On each day, you may decide to buy and/or sell the stock. You can only hold **at most one share** of the stock at any time. However, you can buy it then immediately sell it on the same day.

Find and return the **maximum profit** you can achieve.

## Examples
### Example 1:
```
Input: prices = [7,1,5,3,6,4]
Output: 7
Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
Total profit = 4 + 3 = 7.
```

### Example 2:
```
Input: prices = [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
Total profit = 4.
```

### Example 3:
```
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: There is no way to make a positive profit, so we never buy the stock to achieve the maximum profit of 0.
```

## Constraints
- 1 <= prices.length <= 3 × 10^4
- 0 <= prices[i] <= 10^4

---

## Pattern Recognition

**Primary Pattern**: **Greedy Algorithm - Sum of All Profitable Moves**

**Why This Pattern?**
- Unlimited transactions allowed
- Can buy and sell on same day
- Goal: capture ALL upward price movements
- Each upward step contributes to total profit

**Key Insight**: Any increasing sequence a→b→c→d has profit (d-a) = (b-a) + (c-b) + (d-c)

**Related Patterns**:
1. **Dynamic Programming** - Alternative O(n) approach
2. **Peak-Valley** - Identify buy/sell points explicitly
3. **Single Transaction** - Stock I (simpler version)

---

## Algorithm & Approach

### Core Insight
The greedy approach works because with unlimited transactions, we can capture every upward price movement. Any increasing sequence can be decomposed into the sum of smaller increases.

**Why it works:**
```
Sequence: 1 → 5 → 3 → 6
Option 1: Buy at 1, sell at 6 → profit = 5
Option 2: Buy at 1 sell at 5, buy at 3 sell at 6 → profit = 4 + 3 = 7 ✓
Greedy automatically captures Option 2!
```

### Mathematical Proof
```
For a < b < c < d:
(d - a) = (b - a) + (c - b) + (d - c)

Example: 1 < 2 < 3 < 5
(5 - 1) = (2 - 1) + (3 - 2) + (5 - 3)
    4   =    1    +    1    +    2    ✓
```

### Step-by-Step Algorithm

#### **Approach 1: Brute Force**
```
For each starting day i:
  For each ending day j > i:
    Calculate profit from transaction [i, j]
    Recursively find profit from remaining days
    Track maximum total profit
```

**Complexity Analysis**
- **Time Complexity**: O(n!) - Exponential
- **Space Complexity**: O(n) - Recursion stack

**Why Not Optimal?** Extremely slow, explores billions of combinations for large inputs.

#### **Approach 2: Peak-Valley**
```
1. Start from index 0
2. Find valley (local minimum where price stops decreasing)
3. Find peak (local maximum where price stops increasing)
4. Add (peak - valley) to profit
5. Repeat until end of array
```

**Code Implementation**
```java
public int maxProfit(int[] prices) {
    int i = 0, maxProfit = 0;
    int n = prices.length;
    
    while (i < n - 1) {
        // Find valley
        while (i < n - 1 && prices[i] >= prices[i + 1]) i++;
        int valley = prices[i];
        
        // Find peak
        while (i < n - 1 && prices[i] <= prices[i + 1]) i++;
        int peak = prices[i];
        
        maxProfit += peak - valley;
    }
    
    return maxProfit;
}
```

**Complexity Analysis**
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)

#### **Approach 3: Greedy - Sum All Upward Moves (OPTIMAL)**
```
1. profit = 0
2. For each day i from 1 to n-1:
   - If prices[i] > prices[i-1]:
     profit += prices[i] - prices[i-1]
3. Return profit
```

**Example Walkthrough**

Input: prices = [7,1,5,3,6,4]

| Day | Price | Prev | Diff | Add? | Profit | Total |
|-----|-------|------|------|------|--------|-------|
| 0 | 7 | - | - | - | 0 | 0 |
| 1 | 1 | 7 | -6 | No | 0 | 0 |
| 2 | 5 | 1 | +4 | **Yes** | +4 | 4 |
| 3 | 3 | 5 | -2 | No | 0 | 4 |
| 4 | 6 | 3 | +3 | **Yes** | +3 | 7 |
| 5 | 4 | 6 | -2 | No | 0 | 7 |

Output: 7

**Explanation:**
- Day 2: Price increased from 1 to 5, add profit = 4
- Day 4: Price increased from 3 to 6, add profit = 3
- Total profit = 4 + 3 = 7

**Code Implementation**
```java
public int maxProfit(int[] prices) {
    int profit = 0;
    
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] > prices[i - 1]) {
            profit += prices[i] - prices[i - 1];
        }
    }
    
    return profit;
}
```

**Alternative - More Concise**
```java
public int maxProfit(int[] prices) {
    int profit = 0;
    for (int i = 1; i < prices.length; i++) {
        profit += Math.max(0, prices[i] - prices[i-1]);
    }
    return profit;
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) - Single pass through array
- **Space Complexity**: O(1) - Only using variables

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | Peak-Valley | Greedy | DP |
|-------------|-------------|-------------|--------|-----|
| Unlimited transactions | ✓ | ✓ | ✓ | ✓ |
| Time complexity | O(n!) ❌ | O(n) ✓ | O(n) ✓ | O(n) ✓ |
| Space complexity | O(n) | O(1) ✓ | O(1) ✓ | O(1) ✓ |
| Code simplicity | ❌ Complex | Medium | ✅ **Simplest** | Medium |
| Intuitiveness | ❌ | ✓ | ✅ **Most intuitive** | ❌ |

**Winner**: Greedy approach - simplest code, most intuitive, optimal complexity!

### Why Greedy Works?
With unlimited transactions, we can:
- Capture every upward movement
- Break large gains into smaller consecutive gains
- Sum of all small gains = total maximum profit

### Why O(n) Time is Optimal?
- Must examine each price at least once
- Can't skip any elements
- Single pass achieves minimum possible time

---

## Critical Edge Cases & Gotchas

### 1. **All Decreasing Prices**
```java
Input: prices = [5, 4, 3, 2, 1]
Output: 0
Explanation: No upward movements, no profit possible.
```

### 2. **All Increasing Prices**
```java
Input: prices = [1, 2, 3, 4, 5]
Output: 4
Explanation: Sum all increases = (2-1)+(3-2)+(4-3)+(5-4) = 4
```

### 3. **Single Day**
```java
Input: prices = [5]
Output: 0
Explanation: Need at least 2 days to make a transaction.
```

### 4. **Two Days - Profit Possible**
```java
Input: prices = [1, 5]
Output: 4
```

### 5. **Two Days - No Profit**
```java
Input: prices = [5, 1]
Output: 0
```

### 6. **Same Prices All Days**
```java
Input: prices = [3, 3, 3, 3]
Output: 0
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Trying to Track Buy/Sell Explicitly**
```java
// WRONG - Overcomplicated!
int buy = 0, sell = 0;
// Trying to track individual buy and sell days
// Not needed for this problem!
```

**Why wrong**: The greedy solution doesn't need to track individual transactions.

**Fix**: Just sum positive differences
```java
// CORRECT
for (int i = 1; i < prices.length; i++) {
    if (prices[i] > prices[i - 1]) {
        profit += prices[i] - prices[i - 1];
    }
}
```

### ❌ **MISTAKE 2: Confusing with Stock I (Single Transaction)**
```java
// WRONG - This is Stock I logic, not Stock II!
int minPrice = Integer.MAX_VALUE;
int maxProfit = 0;

for (int price : prices) {
    minPrice = Math.min(minPrice, price);
    maxProfit = Math.max(maxProfit, price - minPrice);
}
```

**Why wrong**: Stock I allows only ONE transaction. Stock II allows unlimited!

### ❌ **MISTAKE 3: Adding Negative Differences**
```java
// WRONG
profit += prices[i] - prices[i - 1];  // This adds negative values too!
```

**Why wrong**: Negative differences reduce profit. We should skip them.

**Fix**: Only add when positive
```java
// CORRECT
if (prices[i] > prices[i - 1]) {
    profit += prices[i] - prices[i - 1];
}
```

### ❌ **MISTAKE 4: Starting Loop from Index 0**
```java
// WRONG
for (int i = 0; i < prices.length; i++) {
    if (prices[i] > prices[i - 1]) {  // ArrayIndexOutOfBounds when i=0!
        profit += prices[i] - prices[i - 1];
    }
}
```

**Why wrong**: `prices[i-1]` when i=0 means `prices[-1]` → ArrayIndexOutOfBoundsException!

**Fix**: Start from index 1
```java
// CORRECT
for (int i = 1; i < prices.length; i++) {
```

### ❌ **MISTAKE 5: Thinking You Need to Find Global Min and Max**
```java
// WRONG WAY OF THINKING
// ❌ Try to find only one lowest price and one highest price
// ❌ This misses intermediate profits!
```

**Why wrong**: You might miss profitable segments in between.

**Example**: [1, 5, 3, 6]
- Global min to max: 6-1 = 5
- Greedy: (5-1) + (6-3) = 7 ✓ Better!

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Single loop | O(n) | Iterate through array once |
| Per iteration | O(1) | Simple comparison and addition |
| Total | O(n) | Linear time |

### Space Complexity: **O(1)**

| Component | Space |
|-----------|-------|
| profit variable | O(1) |
| Loop counter | O(1) |
| Total | O(1) - Constant space |

---

## Visualization

### Example Walkthrough
```
Capacity = prices array

prices = [7, 1, 5, 3, 6, 4]

Visualization:
  7
  |
  |   5     6
  |   |     |
  |   |  3  |  4
  |   |  |  |  |
  |1  |  |  |  |
  +--+--+--+--+--
  0  1  2  3  4  5

Upward movements (capture these):
1 → 5: +4 profit
3 → 6: +3 profit

Total: 7
```

---

## Comparison of Approaches

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Brute Force | O(n!) | O(n) | Explores all options | Impractically slow |
| Peak-Valley | O(n) | O(1) | Explicit buy/sell logic | More complex than needed |
| **Greedy** | **O(n)** | **O(1)** | **Simple, elegant, optimal** ✅ | Requires insight |
| DP | O(n) | O(1) | General framework | Overkill for this problem |

**Best Choice**: Greedy ✓

---

## Key Takeaways

1. **Core Greedy Insight**: Sum all upward price movements
2. **Mathematical Equivalence**: Sum of small profits = total profit
3. **Unlimited Transactions**: Key difference from Stock I
4. **Single Pass**: O(n) time with O(1) space
5. **No Explicit Tracking**: Don't need to remember buy/sell days
6. **Each Upward Move Counts**: Every positive difference contributes
7. **Simpler Than It Appears**: Just one loop with one condition!

---

## Interview Tips

**What to say in an interview:**

> "This problem differs from Stock I because we can make unlimited transactions. The key insight is that any profit can be decomposed into the sum of all upward price movements. So I'll iterate through the array once and sum all positive price differences between consecutive days. This gives O(n) time with O(1) space, which is optimal."

**Key points to mention:**
1. **Why greedy works** - unlimited transactions enable it
2. **Mathematical proof**: (d-a) = (b-a) + (c-b) + (d-c)
3. **Only add positive differences**
4. **Complexity**: Time O(n), Space O(1)
5. **Difference from Stock I** - single vs unlimited transactions

**If asked about alternatives:**
> "I could also use dynamic programming with states for holding/not holding stock, but the greedy approach is simpler and equally efficient. Another option is the peak-valley approach where I explicitly find buy and sell points, but that's more code for the same result."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| Best Time to Buy/Sell Stock | Easy | Greedy (track min) | **Single transaction only** |
| **Best Time to Buy/Sell Stock II** | Medium | **Greedy (sum ups)** | **Unlimited transactions** ← This problem |
| Best Time to Buy/Sell Stock III | Hard | DP | Limited to 2 transactions |
| Best Time to Buy/Sell Stock IV | Hard | DP | Limited to k transactions |
| Best Time to Buy/Sell Stock with Cooldown | Medium | DP with states | Cooldown after selling |
| Best Time to Buy/Sell Stock with Fee | Medium | DP/Greedy | Transaction fee |

**Pattern Evolution**:
- **Stock I**: Track minimum price (greedy)
- **Stock II**: Sum all upward moves (greedy) ← **This problem**
- **Stock III/IV**: Dynamic programming (limited transactions)
- **With constraints**: DP with state machines

---

## Final Pattern Label

✅ **Greedy – Sum of All Profitable Moves**

**Remember:** When you see "unlimited transactions" + "maximize profit" → think sum all positive gains!