# Online Stock Span

## Problem Description

**Difficulty**: Medium

Design an algorithm that collects daily price quotes for some stock and returns the **span** of that stock's price for the current day.

The **span** of the stock's price in one day is the **maximum number of consecutive days** (starting from that day and going **backward**) for which the stock price was **less than or equal** to the price of that day.

**Examples:**
- If prices in last 4 days: `[7, 2, 1, 2]` and today's price is `2`
  - Span = **4** (all 4 days: 2 ≤ 2, 1 ≤ 2, 2 ≤ 2, 7 > 2 stop)
  
- If prices in last 4 days: `[7, 34, 1, 2]` and today's price is `8`
  - Span = **3** (today: 8, yesterday: 2 ≤ 8, before: 1 ≤ 8, before: 34 > 8 stop)

**Implement the StockSpanner class:**
- `StockSpanner()` — Initializes the object
- `int next(int price)` — Returns the span of the stock's price given today's price

## Examples

### Example 1:
```
Input: 
["StockSpanner", "next", "next", "next", "next", "next", "next", "next"]
[[], [100], [80], [60], [70], [60], [75], [85]]

Output: 
[null, 1, 1, 1, 2, 1, 4, 6]

Explanation:
StockSpanner stockSpanner = new StockSpanner();

stockSpanner.next(100); // return 1
  Prices: [100]
  Span: 1 (only today, no previous days)

stockSpanner.next(80); // return 1
  Prices: [100, 80]
  80 < 100, span = 1 (only today)

stockSpanner.next(60); // return 1
  Prices: [100, 80, 60]
  60 < 80, span = 1 (only today)

stockSpanner.next(70); // return 2
  Prices: [100, 80, 60, 70]
  70 >= 60 (1 day back), 70 < 80 (stop)
  Span: 2 (today + 1 day back)

stockSpanner.next(60); // return 1
  Prices: [100, 80, 60, 70, 60]
  60 < 70, span = 1 (only today)

stockSpanner.next(75); // return 4
  Prices: [100, 80, 60, 70, 60, 75]
  75 >= 60 (1 back), 75 >= 70 (2 back), 75 >= 60 (3 back), 75 < 80 (stop)
  Span: 4 (today + 3 days back)

stockSpanner.next(85); // return 6
  Prices: [100, 80, 60, 70, 60, 75, 85]
  85 >= 75 (1 back), 85 >= 60 (2 back), 85 >= 70 (3 back), 
  85 >= 60 (4 back), 85 >= 80 (5 back), 85 < 100 (stop)
  Span: 6 (today + 5 days back)
```

### Example 2:
```
Input: 
["StockSpanner", "next", "next", "next"]
[[], [31], [41], [48]]

Output: 
[null, 1, 2, 3]

Explanation:
next(31): Span = 1 (no previous)
next(41): 41 >= 31, span = 2
next(48): 48 >= 41 >= 31, span = 3 (all consecutive)
```

### Example 3:
```
Input: 
["StockSpanner", "next", "next", "next"]
[[], [100], [80], [60]]

Output: 
[null, 1, 1, 1]

Explanation:
Strictly decreasing prices
Each day has span = 1 (only itself)
```

### Example 4:
```
Input: 
["StockSpanner", "next", "next", "next", "next"]
[[], [50], [50], [50], [50]]

Output: 
[null, 1, 2, 3, 4]

Explanation:
All equal prices (50 <= 50)
Span keeps growing: 1, 2, 3, 4
```

### Example 5:
```
Input: 
["StockSpanner", "next", "next", "next", "next", "next"]
[[], [10], [20], [15], [25], [30]]

Output: 
[null, 1, 2, 1, 4, 5]

Explanation:
next(10): span = 1
next(20): 20 >= 10, span = 2
next(15): 15 < 20, span = 1 (only itself)
next(25): 25 >= 15, 25 >= 20, 25 >= 10, span = 4
next(30): 30 >= 25 >= 15 >= 20 >= 10, span = 5 (all)
```

## Constraints
- 1 <= price <= 100,000
- At most 10,000 calls will be made to `next`

**Recommended Complexity**: O(1) amortized time per `next()` call, O(n) space

---

## Pattern Recognition

**Primary Pattern**: **Monotonic Stack (Previous Less or Equal Element Count)**

**Why This Pattern?**
- Need to count consecutive days going backward
- Looking for previous greater element (first price > current)
- Brute force scanning backward is O(n) per call
- Stack can process in O(1) amortized per call

**Key Insight**: Stack Tracks Unresolved Prices
```
Stock Span Problem:
  For each price, count how many consecutive previous prices are <= current
  
Naive approach: Scan backward for each price
  next(price):
      span = 1
      for each previous price (going backward):
          if previous <= price:
              span++
          else:
              break
      return span
  
Time: O(n) per call, O(n²) total for n calls
Too slow for 10,000 calls!

Monotonic Stack approach:
  Use stack to track prices with their spans
  When current price >= stack top, merge spans
  → O(1) amortized per call ✓
```

**The Stack Strategy**:
```
Stack stores pairs: (price, span)

Invariant: Stack contains prices in DECREASING order
  stack[0].price > stack[1].price > stack[2].price ...
  
Why pairs (price, span)?
  Span represents accumulated count of days
  When merging, add spans directly (no need to track individual days)

Algorithm:
  1. Initialize empty stack
  2. For each next(price) call:
     a. Initialize span = 1 (count today)
     b. While stack not empty AND stack.top().price <= price:
        - Pop (prevPrice, prevSpan)
        - Add prevSpan to current span (merge days)
     c. Push (price, span) onto stack
     d. Return span
```

**Example Showing Span Merging**:
```
Input: [100, 80, 60, 70]

next(100):
  Stack: []
  span = 1, push (100, 1)
  Stack: [(100, 1)]
  Return: 1

next(80):
  Stack: [(100, 1)]
  80 < 100, no merge
  span = 1, push (80, 1)
  Stack: [(100, 1), (80, 1)]
  Return: 1

next(60):
  Stack: [(100, 1), (80, 1)]
  60 < 80, no merge
  span = 1, push (60, 1)
  Stack: [(100, 1), (80, 1), (60, 1)]
  Return: 1

next(70):
  Stack: [(100, 1), (80, 1), (60, 1)]
  
  70 >= 60, pop (60, 1)
    span = 1 + 1 = 2
  70 < 80, stop
  
  Push (70, 2)
  Stack: [(100, 1), (80, 1), (70, 2)]
  Return: 2

Key: (70, 2) represents "70 covers today + 1 previous day"
```

**Why Monotonic Decreasing?**
```
Stack maintains decreasing prices (bottom to top)

Example: [100, 80, 60, 70, 60, 75, 85]

After next(75):
  Stack before: [(100, 1), (80, 1), (70, 2), (60, 1)]
  
  Process 75:
    75 >= 60, pop (60, 1), span = 1 + 1 = 2
    75 >= 70, pop (70, 2), span = 2 + 2 = 4
    75 < 80, stop
    Push (75, 4)
  
  Stack after: [(100, 1), (80, 1), (75, 4)]
  Prices: 100 > 80 > 75 — decreasing ✓

Why remove smaller prices?
  75 "blocks" view of 60 and 70
  Future prices will never see 60 or 70 directly
  They'll encounter 75 first, which has accumulated span
  
This ensures O(1) amortized time!
```

**Critical Detail**: Store (Price, Span) Pairs
```
Why store span with price?
  When merging, need to know how many days each price represents
  
Example: After next(70) with span=2
  Stack: [(70, 2)]
  
  This means: Price 70 represents TODAY + 1 day back
  
  When next(75) comes:
    75 >= 70, pop (70, 2)
    Add its span: mySpan = 1 + 2 = 3
    
  Without storing span:
    Would need to track each individual day
    Or scan backward (slow!)

Span = accumulated count, enables O(1) merging!
```

**Comparison with Daily Temperatures**:
```
Daily Temperatures:
  Look FORWARD for next greater temperature
  Return: days to wait
  
Online Stock Span:
  Look BACKWARD for previous less/equal prices
  Return: consecutive count
  
Both use monotonic stack, but:
  - Daily Temps: forward-looking, resolve past days
  - Stock Span: backward-looking, accumulate spans

Similar pattern, different direction!
```

**Related Patterns**:
1. **Monotonic Stack** — This problem
2. **Next/Previous Greater Element** — Core concept
3. **Running Computation** — Accumulate results
4. **Design Problem** — Stateful class

---

## Algorithm & Approach

### Core Insight

**Why Naive Approach Fails:**
```
Naive: Scan backward for each call
  next(price):
      span = 1
      for i from current-1 down to 0:
          if prices[i] <= price:
              span++
          else:
              break
      return span
  
Time: O(n) per call
Total for k calls: O(k*n)
For 10,000 calls with n=10,000: 10^8 operations ❌
Too slow!

Monotonic Stack:
  Each price pushed once
  Each price popped at most once
  Amortized O(1) per call
  Total: O(k) for k calls ✓
```

**The Optimal Strategy**:
```
Key observations:
  1. Store (price, span) pairs in stack
  2. Stack maintains decreasing price order
  3. When price >= stack.top(), merge spans
  4. Each price processed once (amortized)
  5. Span accumulation avoids re-counting
  
All operations O(1) amortized:
  Push: O(1)
  Pop: O(1) per price (each popped at most once)
  Span addition: O(1)
  
Total: O(1) amortized per next() call
```

### Step-by-Step Algorithm

---

#### **Approach 1: Monotonic Stack with (Price, Span) Pairs - OPTIMAL**

**Core Idea**:
- Stack stores (price, span) pairs
- Maintain decreasing price order
- Merge spans when current price >= stack top

**Algorithm**
```
class StockSpanner:
    stack = new Stack()  // Stores (price, span) pairs
    
    next(price):
        span = 1  // Count today
        
        // Merge all prices <= current
        while (stack not empty AND stack.top().price <= price):
            (prevPrice, prevSpan) = stack.pop()
            span += prevSpan  // Accumulate spans
        
        // Push current with accumulated span
        stack.push((price, span))
        
        return span
```

**Code Implementation**
```java
class StockSpanner {
    private Stack<int[]> stack;  // Stores [price, span]
    
    public StockSpanner() {
        stack = new Stack<>();
    }
    
    public int next(int price) {
        int span = 1;  // Count today
        
        // Pop and merge all prices <= current price
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            int[] prev = stack.pop();
            span += prev[1];  // Add previous span
        }
        
        // Push current price with accumulated span
        stack.push(new int[]{price, span});
        
        return span;
    }
}
```

**Example Walkthrough**

Calls: `next(100), next(80), next(60), next(70)`

| Call | Price | Stack Before | Action | Stack After | Return |
|------|-------|--------------|--------|-------------|--------|
| next(100) | 100 | [] | span=1, push(100,1) | [(100,1)] | 1 |
| next(80) | 80 | [(100,1)] | 80<100, span=1, push(80,1) | [(100,1),(80,1)] | 1 |
| next(60) | 60 | [(100,1),(80,1)] | 60<80, span=1, push(60,1) | [(100,1),(80,1),(60,1)] | 1 |
| next(70) | 70 | [(100,1),(80,1),(60,1)] | 70>=60 pop, span=1+1=2, push(70,2) | [(100,1),(80,1),(70,2)] | 2 |

**Complexity Analysis**
- **Time**: O(1) amortized per call — Each price pushed/popped at most once
- **Space**: O(n) — Stack holds at most n prices for n calls

---

#### **Approach 2: Monotonic Stack with Custom Pair Class - CLEANER**

**Core Idea**: Use custom class for better readability.

**Code Implementation**
```java
class StockSpanner {
    private Stack<Pair> stack;
    
    private static class Pair {
        int price;
        int span;
        
        Pair(int price, int span) {
            this.price = price;
            this.span = span;
        }
    }
    
    public StockSpanner() {
        stack = new Stack<>();
    }
    
    public int next(int price) {
        int span = 1;
        
        // Merge spans for prices <= current
        while (!stack.isEmpty() && stack.peek().price <= price) {
            span += stack.pop().span;
        }
        
        stack.push(new Pair(price, span));
        return span;
    }
}
```

**Key Difference**: 
- Custom Pair class instead of int[]
- More readable: pair.price, pair.span
- Slightly more memory per pair (object overhead)

**Complexity Analysis**
- **Time**: O(1) amortized per call
- **Space**: O(n) — Stack storage

---

#### **Approach 3: Two Stacks (Price and Span Separate) - ALTERNATIVE**

**Core Idea**: Store prices and spans in separate stacks.

**Code Implementation**
```java
class StockSpanner {
    private Stack<Integer> prices;
    private Stack<Integer> spans;
    
    public StockSpanner() {
        prices = new Stack<>();
        spans = new Stack<>();
    }
    
    public int next(int price) {
        int span = 1;
        
        // Pop and merge while top price <= current
        while (!prices.isEmpty() && prices.peek() <= price) {
            prices.pop();
            span += spans.pop();  // Add corresponding span
        }
        
        prices.push(price);
        spans.push(span);
        
        return span;
    }
}
```

**Key Difference**: 
- Two separate stacks instead of one with pairs
- Synchronized operations on both stacks
- Slightly more space efficient (no array/object wrapper)

**Complexity Analysis**
- **Time**: O(1) amortized per call
- **Space**: O(n) — Two stacks

---

#### **Approach 4: Brute Force with Array - FOR COMPARISON**

**Core Idea**: Store all prices, scan backward for each call.

**Code Implementation**
```java
class StockSpanner {
    private List<Integer> prices;
    
    public StockSpanner() {
        prices = new ArrayList<>();
    }
    
    public int next(int price) {
        prices.add(price);
        int span = 1;
        
        // Scan backward
        for (int i = prices.size() - 2; i >= 0; i--) {
            if (prices.get(i) <= price) {
                span++;
            } else {
                break;
            }
        }
        
        return span;
    }
}
```

**Key Difference**: 
- No stack, simple array scan
- Easy to understand but slow
- O(n) time per call

**Complexity Analysis**
- **Time**: O(n) per call, O(n²) total
- **Space**: O(n) — Store all prices

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time per Call | Space | Code Complexity | Recommended |
|----------|---------------|-------|-----------------|-------------|
| **Stack with Pairs** | **O(1) amortized** | **O(n)** | **Medium ✅** | **Yes ✅** |
| Stack with Custom Class | O(1) amortized | O(n) | Medium | More readable |
| Two Separate Stacks | O(1) amortized | O(n) | Medium | Alternative |
| Brute Force | O(n) | O(n) | Easy | Too slow |

**Winner**: **Monotonic Stack with (Price, Span) Pairs** — optimal time, simple!

### Why Store Span with Price?

```
Key insight: Span represents accumulated days

Without span:
  Stack: [100, 80, 60]
  next(70):
    70 >= 60, how many days does 60 represent?
    Don't know! Would need to track separately or re-scan.

With span:
  Stack: [(100,1), (80,1), (60,1)]
  next(70):
    70 >= 60, pop (60, 1)
    span = 1 + 1 = 2 ✓ (directly add)

Span enables O(1) merging!

Example showing accumulation:
  next(100): span=1, stack=[(100,1)]
  next(80): span=1, stack=[(100,1), (80,1)]
  next(60): span=1, stack=[(100,1), (80,1), (60,1)]
  next(70):
    70>=60, pop (60,1), span=1+1=2
    Stack: [(100,1), (80,1), (70,2)]
  next(60): span=1, stack=[(100,1), (80,1), (70,2), (60,1)]
  next(75):
    75>=60, pop (60,1), span=1+1=2
    75>=70, pop (70,2), span=2+2=4  ← Accumulated!
    Stack: [(100,1), (80,1), (75,4)]

(75,4) means: "75 covers 4 consecutive days total"
```

### Why Monotonic Decreasing Stack?

```
Stack must maintain decreasing prices:

Example: [100, 80, 60, 70]

After 60: Stack [(100,1), (80,1), (60,1)]
  Prices: 100 > 80 > 60 — decreasing ✓

Process 70:
  70 >= 60, pop (60,1)
  70 < 80, stop
  Push (70,2)
  Stack: [(100,1), (80,1), (70,2)]
  Prices: 100 > 80 > 70 — still decreasing ✓

Why this property?
  Smaller prices "hidden" behind larger ones
  Future prices encounter larger price first
  Larger price blocks view of smaller ones
  
This ensures each price popped at most once!

Example showing blocking:
  Stack: [(100,1), (80,1), (70,2)]
  
  next(75):
    Sees 70 first (top)
    70 < 75, merge
    Sees 80 next
    80 > 75, stop
    
  Never needs to check 60 (already merged into 70)!

Monotonic property = amortized O(1) time!
```

### Why Use <= Not <?

```
Important: Use <= not < for comparison!

Problem states: "price was less than or equal"

Example: [50, 50, 50]
  
  next(50): span = 1
  next(50):
    50 <= 50? Yes, pop (50,1)
    span = 1 + 1 = 2 ✓
  next(50):
    50 <= 50? Yes, pop (50,2)
    span = 1 + 2 = 3 ✓

If we used < (strictly less than):
  next(50): span = 1
  next(50):
    50 < 50? No, don't pop
    span = 1 ❌ (should be 2!)

Equal prices count toward span!
Use <= for comparison.
```

### Amortized O(1) Analysis

```
Claim: Each next() call is O(1) amortized

Proof:
  Each price pushed exactly once: n pushes for n calls
  Each price popped at most once: ≤ n pops for n calls
  
  Total operations: pushes + pops = n + n = 2n
  Amortized per call: 2n / n = 2 = O(1)

Example: [100, 80, 60, 70, 60, 75, 85]
  
  Pushes: 7 prices → 7 pushes
  Pops:
    60 popped when 70 arrives: 1 pop
    70 and 60 popped when 75 arrives: 2 pops
    75 and 80 popped when 85 arrives: 2 pops
  Total pops: 5
  
  Total operations: 7 pushes + 5 pops = 12
  Average per call: 12 / 7 ≈ 1.7 = O(1) ✓

Even though while loop can iterate multiple times,
total iterations across ALL calls is bounded by n!
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Call**
```java
next(100)
Stack: [(100, 1)]
Return: 1
```

### 2. **Strictly Increasing Prices**
```java
next(10): span=1, stack=[(10,1)], return 1
next(20): 20>=10, pop(10,1), span=2, stack=[(20,2)], return 2
next(30): 30>=20, pop(20,2), span=3, stack=[(30,3)], return 3
Each price covers all previous
```

### 3. **Strictly Decreasing Prices**
```java
next(100): span=1, return 1
next(80): 80<100, span=1, return 1
next(60): 60<80, span=1, return 1
Each price has span=1 (only itself)
Stack grows: [(100,1), (80,1), (60,1)]
```

### 4. **All Equal Prices**
```java
next(50): span=1, return 1
next(50): 50<=50, span=2, return 2
next(50): 50<=50, span=3, return 3
next(50): 50<=50, span=4, return 4
Span grows linearly
```

### 5. **Alternating High-Low**
```java
next(100): span=1, return 1
next(50): 50<100, span=1, return 1
next(100): 100>=50, 100>=100, span=3, return 3
next(50): 50<100, span=1, return 1
```

### 6. **Large Then Small Then Large**
```java
next(100): span=1, stack=[(100,1)]
next(10): span=1, stack=[(100,1),(10,1)]
next(20): 20>=10, span=2, stack=[(100,1),(20,2)]
next(30): 30>=20, span=3, stack=[(100,1),(30,3)]
```

### 7. **Multiple Merges**
```java
next(10): span=1, stack=[(10,1)]
next(20): span=2, stack=[(20,2)]
next(15): span=1, stack=[(20,2),(15,1)]
next(25): 25>=15, 25>=20, span=4, stack=[(25,4)]
One call merges multiple spans
```

### 8. **Maximum Price**
```java
next(100000): Always spans everything if called after smaller prices
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Storing Span with Price**
```java
// WRONG - only stores prices
Stack<Integer> stack = new Stack<>();

public int next(int price) {
    int span = 1;
    while (!stack.isEmpty() && stack.peek() <= price) {
        stack.pop();
        span++;  // WRONG! Only counting 1 per pop
    }
    stack.push(price);
    return span;
}
```

**Why wrong**: Each popped price might represent multiple days!

**Dry run failure for [10, 20, 15, 25]:**
```
next(10): span=1, stack=[10], return 1 ✓
next(20): 20>=10, pop 10, span=2, stack=[20], return 2 ✓
next(15): 15<20, span=1, stack=[20,15], return 1 ✓
next(25):
  25>=15, pop 15, span=2
  25>=20, pop 20, span=3 ❌
  
  Should be span=4! (25 covers: 25,15,20,10)
  But we only counted pops, not accumulated spans!

20 represented 2 days, but we only added 1!
```

**Fix**: Store and accumulate spans
```java
Stack<int[]> stack = new Stack<>();

public int next(int price) {
    int span = 1;
    while (!stack.isEmpty() && stack.peek()[0] <= price) {
        span += stack.pop()[1];  // Add span, not just count
    }
    stack.push(new int[]{price, span});
    return span;
}
```

### ❌ **MISTAKE 2: Using < Instead of <=**
```java
// WRONG - uses < instead of <=
while (!stack.isEmpty() && stack.peek()[0] < price) {
    span += stack.pop()[1];
}
```

**Why wrong**: Equal prices should be included in span!

**Dry run failure for [50, 50]:**
```
next(50): span=1, stack=[(50,1)], return 1 ✓
next(50):
  50 < 50? No, don't pop
  span = 1 ❌ (should be 2!)
  stack = [(50,1), (50,1)]
  return 1 ❌

Problem says "less than or equal"!
```

**Fix**: Use <=
```java
while (!stack.isEmpty() && stack.peek()[0] <= price)
```

### ❌ **MISTAKE 3: Not Checking isEmpty Before Peek**
```java
// WRONG - doesn't check empty
while (stack.peek()[0] <= price) {
    span += stack.pop()[1];
}
```

**Why wrong**: peek() on empty stack throws exception!

**Dry run failure for first call:**
```
next(100):
  Condition: stack.peek()[0] <= 100
  Stack empty! peek() throws EmptyStackException ❌
```

**Fix**: Check isEmpty first
```java
while (!stack.isEmpty() && stack.peek()[0] <= price)
```

### ❌ **MISTAKE 4: Forgetting to Push Current Price**
```java
// WRONG - forgets to push
public int next(int price) {
    int span = 1;
    while (!stack.isEmpty() && stack.peek()[0] <= price) {
        span += stack.pop()[1];
    }
    // WRONG! Forgot: stack.push(new int[]{price, span});
    return span;
}
```

**Why wrong**: Current price not added, future calls can't see it!

**Dry run failure for [10, 20]:**
```
next(10): span=1, forgot push, stack=[], return 1
next(20): stack empty, span=1, forgot push, stack=[], return 1 ❌

Should be 2! But 10 never added to stack.
```

**Fix**: Always push current
```java
stack.push(new int[]{price, span});
```

### ❌ **MISTAKE 5: Initializing Span to 0 Instead of 1**
```java
// WRONG - starts at 0
int span = 0;  // WRONG! Should be 1
```

**Why wrong**: Doesn't count today!

**Dry run failure for [10]:**
```
next(10):
  span = 0
  Stack empty, no pops
  span stays 0
  return 0 ❌ (should be 1!)

Today counts as 1 day!
```

**Fix**: Initialize to 1
```java
int span = 1;  // Count today
```

### ❌ **MISTAKE 6: Returning Stack Size**
```java
// WRONG - returns stack size
public int next(int price) {
    // ... merge logic ...
    stack.push(new int[]{price, span});
    return stack.size();  // WRONG! Should return span
}
```

**Why wrong**: Stack size ≠ span!

**Dry run failure for [100, 80, 60, 70]:**
```
After next(70):
  span = 2 (correct)
  stack = [(100,1), (80,1), (70,2)]
  stack.size() = 3 ❌
  
Should return 2, not 3!
```

**Fix**: Return span
```java
return span;
```

### ❌ **MISTAKE 7: Not Accumulating Span Correctly**
```java
// WRONG - doesn't accumulate, just counts
int span = 0;
while (!stack.isEmpty() && stack.peek()[0] <= price) {
    stack.pop();
    span++;  // WRONG! Should add prev[1]
}
span++;  // Count today
```

**Why wrong**: Same as MISTAKE 1, loses accumulated spans!

**Fix**: Add span from popped element
```java
int span = 1;
while (!stack.isEmpty() && stack.peek()[0] <= price) {
    span += stack.pop()[1];  // Add accumulated span
}
```

---

## Complexity Analysis

### Time Complexity: **O(1) amortized per next() call**

| Operation | Count (n calls) | Time Each | Total |
|-----------|-----------------|-----------|-------|
| **Push to stack** | n | O(1) | O(n) |
| **Pop from stack** | ≤ n | O(1) | O(n) |
| **Span addition** | ≤ n | O(1) | O(n) |
| **Total** | - | - | **O(n) for n calls** |

**Amortized per call**: O(n) / n = **O(1) amortized**

**Amortized Analysis**:
```
Each price pushed exactly once: n pushes
Each price popped at most once: ≤ n pops

Total operations: n + n = 2n
Amortized per call: 2n / n = 2 = O(1) ✓

Example: 10 calls
  10 pushes (one per call)
  ≤ 10 pops (each price popped at most once)
  Total: ≤ 20 operations
  Average: 2 operations per call = O(1)

Worst case single call: O(n) if all prices popped
But amortized across all calls: O(1)
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Stack | O(n) | Worst case: n prices (decreasing) |
| Variables | O(1) | span, price (constant) |
| **Total** | **O(n)** | Stack dominates |

**Space analysis**:
```
Worst case: Strictly decreasing prices
  All pushed, none popped
  Stack size: n for n calls
  
Best case: Strictly increasing prices
  Each pushed then immediately popped (all merged)
  Max stack size: 1
  
Average case: Mixed
  Stack size: O(n) worst case
  
Space complexity: O(n)
```

---

## Visualization

### Complete Example Walkthrough

**Calls:** `next(100), next(80), next(60), next(70), next(60), next(75), next(85)`

---

**Call 1: next(100)**
```
price = 100
span = 1
Stack empty, no merges

Action: Push (100, 1)

Stack:
   ┌────────┐
   │(100,1) │ ← top
   └────────┘

Return: 1
```

---

**Call 2: next(80)**
```
price = 80
span = 1
Comparison: 80 <= 100? No

Action: Push (80, 1)

Stack:
   ┌────────┐
   │ (80,1) │ ← top
   ├────────┤
   │(100,1) │
   └────────┘

Return: 1
```

---

**Call 3: next(60)**
```
price = 60
span = 1
Comparison: 60 <= 80? No

Action: Push (60, 1)

Stack:
   ┌────────┐
   │ (60,1) │ ← top
   ├────────┤
   │ (80,1) │
   ├────────┤
   │(100,1) │
   └────────┘

Return: 1
```

---

**Call 4: next(70)**
```
price = 70
span = 1

Merge 1:
  70 <= 60? No, 60 <= 70? Yes!
  Pop (60, 1)
  span = 1 + 1 = 2

Check next:
  70 <= 80? No, stop

Action: Push (70, 2)

Stack:
   ┌────────┐
   │ (70,2) │ ← top (represents 2 days!)
   ├────────┤
   │ (80,1) │
   ├────────┤
   │(100,1) │
   └────────┘

Return: 2
```

---

**Call 5: next(60)**
```
price = 60
span = 1
Comparison: 60 <= 70? No

Action: Push (60, 1)

Stack:
   ┌────────┐
   │ (60,1) │ ← top
   ├────────┤
   │ (70,2) │
   ├────────┤
   │ (80,1) │
   ├────────┤
   │(100,1) │
   └────────┘

Return: 1
```

---

**Call 6: next(75)**
```
price = 75
span = 1

Merge 1:
  75 >= 60? Yes!
  Pop (60, 1)
  span = 1 + 1 = 2

Merge 2:
  75 >= 70? Yes!
  Pop (70, 2)  ← This had span 2!
  span = 2 + 2 = 4  ← Accumulated!

Check next:
  75 >= 80? No, stop

Action: Push (75, 4)

Stack:
   ┌────────┐
   │ (75,4) │ ← top (4 consecutive days!)
   ├────────┤
   │ (80,1) │
   ├────────┤
   │(100,1) │
   └────────┘

Return: 4
```

---

**Call 7: next(85)**
```
price = 85
span = 1

Merge 1:
  85 >= 75? Yes!
  Pop (75, 4)
  span = 1 + 4 = 5

Merge 2:
  85 >= 80? Yes!
  Pop (80, 1)
  span = 5 + 1 = 6

Check next:
  85 >= 100? No, stop

Action: Push (85, 6)

Stack:
   ┌────────┐
   │ (85,6) │ ← top (6 consecutive days!)
   ├────────┤
   │(100,1) │
   └────────┘

Return: 6
```

---

### Span Accumulation Diagram

```
Prices:  100   80   60   70   60   75   85
Spans:    1    1    1    2    1    4    6

How span=6 is computed:
  
  85 call:
    span = 1 (today)
    
    Pop (75, 4):
      span = 1 + 4 = 5
      (75 represented days: 75, 60, 70, 60)
    
    Pop (80, 1):
      span = 5 + 1 = 6
      (80 represented 1 day: 80)
    
    Total: 85 covers 6 days: [85, 75, 60, 70, 60, 80]

Accumulation enables O(1) computation!
No need to count individual days.
```

---

### Stack Evolution

```
Operation Sequence:

next(100):
  Stack: [(100,1)]
  
next(80):
  Stack: [(100,1), (80,1)]
  
next(60):
  Stack: [(100,1), (80,1), (60,1)]
  
next(70):
  Pop (60,1), merge
  Stack: [(100,1), (80,1), (70,2)]
  
next(60):
  Stack: [(100,1), (80,1), (70,2), (60,1)]
  
next(75):
  Pop (60,1), pop (70,2), merge both
  Stack: [(100,1), (80,1), (75,4)]
  
next(85):
  Pop (75,4), pop (80,1), merge both
  Stack: [(100,1), (85,6)]

Monotonic decreasing property maintained throughout!
```

---

## Comparison of Approaches

| Approach | Time per Call | Space | Code Lines | Clarity | Recommended |
|----------|---------------|-------|------------|---------|-------------|
| **Stack with int[] Pairs** | **O(1) amortized** | **O(n)** | **~15** | **Good ✅** | **Yes ✅** |
| Stack with Custom Class | O(1) amortized | O(n) | ~20 | Excellent | More readable |
| Two Separate Stacks | O(1) amortized | O(n) | ~18 | Good | Alternative |
| Brute Force | O(n) | O(n) | ~12 | Easy | Too slow |

**All optimal approaches have same complexity: O(1) amortized time, O(n) space**

**Recommendation**: Use **Stack with int[] Pairs** — simple, efficient, standard!

---

## Key Takeaways

1. **Store (price, span) pairs** — enables O(1) span accumulation
2. **Monotonic decreasing stack** — prices decrease bottom to top
3. **Use <= not <** — equal prices count toward span
4. **Accumulate spans when merging** — add prev[1], don't just count pops
5. **Initialize span to 1** — count today
6. **Each price pushed/popped once** — amortized O(1)
7. **Span represents multiple days** — accumulated count
8. **Check isEmpty() before peek()** — avoid exceptions

---

## Interview Tips

**What to say in an interview:**

> "This problem asks for the span of consecutive days going backward where prices are less than or equal to today's price. The naive approach of scanning backward for each call would be O(n) per call, giving O(n²) total which is too slow. Instead, I'll use a monotonic stack that stores (price, span) pairs. The key insight is that span represents an accumulated count of days, so when I merge spans, I can add them directly in O(1) time rather than counting individual days. The stack maintains decreasing prices from bottom to top. For each new price, I pop all stack entries with price less than or equal to current price, accumulating their spans. Then I push the current price with the accumulated span. Since each price is pushed and popped at most once, this gives O(1) amortized time per call with O(n) space for the stack."

**Key points to mention:**
1. **Monotonic stack pattern** — Decreasing prices
2. **Store (price, span) pairs** — Accumulate counts
3. **Merge spans when popping** — Add spans, don't recount
4. **Use <= for comparison** — Equal prices included
5. **Amortized O(1)** — Each price pushed/popped once
6. **Similar to Daily Temperatures** — But looking backward

**If asked about implementation details:**
> "I use a stack of int[] arrays where each entry is [price, span]. When processing a new price, I initialize span to 1 for today. Then I loop while the stack isn't empty and the top price is less than or equal to current price. For each pop, I add that entry's span to my current span—this accumulates all the days that entry represented. After the loop, I push [price, span] and return span. The use of <= instead of < is critical because equal prices should be included in the span according to the problem statement."

**Common Follow-ups:**
- "Why store span with price?" → Enables O(1) accumulation instead of recounting
- "Why monotonic stack?" → Ensures each price processed once, O(1) amortized
- "Why <= not <?" → Problem includes equal prices in span
- "Space optimization?" → Two stacks instead of pairs, but same O(n)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Online Stock Span** | Medium | **Monotonic Stack (Span Count)** | **This problem** |
| Daily Temperatures | Medium | Monotonic Stack | Forward-looking, days to wait |
| Next Greater Element I | Easy | Monotonic Stack | Find next greater value |
| Next Greater Element II | Medium | Monotonic Stack | Circular array |
| Largest Rectangle in Histogram | Hard | Monotonic Stack | Previous/next smaller |

**Pattern Progression**:
1. **Span counting backward** (this problem) — Accumulate consecutive count
2. **Distance forward** (Daily Temps) — Days until next greater
3. **Value lookup** (Next Greater) — Find greater value
4. **Area computation** (Histogram) — Use bounds for calculation

---

## Final Pattern Label

✅ **Monotonic Stack (Previous Less/Equal Element Span) with Accumulation**

**Remember:** Use monotonic stack storing (price, span) pairs to compute stock span in O(1) amortized time. Maintain decreasing price order (bottom to top). When new price >= stack top, pop and **accumulate spans** (don't just count pops). Use **<= not <** since equal prices count. Initialize span=1 (today). Each price pushed/popped once across all calls. Stack enables O(1) merging by storing accumulated span with each price. Return span after pushing current (price, span)!
