# Sqrt(x)

## Problem Description

**Difficulty**: Easy

You are given a **non-negative integer** `x`, return the **square root** of `x` **rounded down** to the nearest integer. The returned integer should be **non-negative** as well.

You **must not use** any built-in exponent function or operator.
- For example, do not use `pow(x, 0.5)` in C++ or `x ** 0.5` in Python.

In other words, return **⌊√x⌋** (floor of square root).

## Examples

### Example 1:
```
Input: x = 9
Output: 3

Explanation:
  √9 = 3.0
  Floor(3.0) = 3
```

### Example 2:
```
Input: x = 13
Output: 3

Explanation:
  √13 ≈ 3.605...
  Floor(3.605) = 3
  
  Verify: 3² = 9 ≤ 13 ✓
          4² = 16 > 13 ✓
```

### Example 3:
```
Input: x = 0
Output: 0

Explanation:
  √0 = 0
```

### Example 4:
```
Input: x = 1
Output: 1

Explanation:
  √1 = 1
```

### Example 5:
```
Input: x = 2
Output: 1

Explanation:
  √2 ≈ 1.414...
  Floor(1.414) = 1
  
  Verify: 1² = 1 ≤ 2 ✓
          2² = 4 > 2 ✓
```

### Example 6:
```
Input: x = 8
Output: 2

Explanation:
  √8 ≈ 2.828...
  Floor(2.828) = 2
  
  Verify: 2² = 4 ≤ 8 ✓
          3² = 9 > 8 ✓
```

### Example 7:
```
Input: x = 4
Output: 2

Explanation:
  √4 = 2.0 (perfect square)
  Floor(2.0) = 2
```

### Example 8:
```
Input: x = 16
Output: 4

Explanation:
  √16 = 4.0 (perfect square)
```

### Example 9:
```
Input: x = 2147395599
Output: 46339

Explanation:
  Large input near max int
  √2147395599 ≈ 46339.999...
  Floor = 46339
  
  Verify: 46339² = 2147395921 (would overflow!)
  Need careful overflow handling
```

### Example 10:
```
Input: x = 2147483647
Output: 46340

Explanation:
  Maximum input (2³¹ - 1)
  √2147483647 ≈ 46340.95...
  Floor = 46340
```

## Constraints
- 0 <= x <= 2³¹ - 1
- Cannot use built-in functions like `sqrt()`, `pow()`, or `**`
- Return value must be non-negative integer

**Recommended Complexity**: O(log x) time and O(1) space

---

## Pattern Recognition

**Primary Pattern**: **Binary Search on Answer Space**

**Why This Pattern?**
- Don't search in an array, search for the **answer itself**
- Answer is in range [0, x] which is **sorted**
- Can check if a number is the answer in O(1): `mid * mid <= x`
- Need O(log x) time (can't use built-in functions)

**Key Insight**: Search for Largest Valid Answer
```
We want: largest integer ans where ans² ≤ x

In other words:
  ans² ≤ x < (ans+1)²

Example: x = 13
  3² = 9 ≤ 13 ✓
  4² = 16 > 13 ✓
  
  Answer: 3

This is a search problem!
Search space: [0, x]
Target: largest mid where mid² ≤ x
```

**Binary Search on Answer Space**:
```
Traditional binary search:
  Search for value in array
  Array: [1, 3, 5, 7, 9]
  Target: 5
  
This problem:
  Search for answer in range
  Range: [0, 1, 2, 3, ..., x]
  Answer: largest k where k² ≤ x
  
Same algorithm, different application!
```

**The Strategy**:
```
Binary search on [0, x]:
  mid = (left + right) / 2
  
  Check: mid² ≤ x?
  
  If yes:
    mid could be answer
    But maybe larger answer exists
    Save mid, search right: [mid+1, right]
    
  If no:
    mid is too large
    Search left: [left, mid-1]
    
Keep best answer found
```

**Example Showing Search Process**:
```
x = 13, find ⌊√13⌋

Range: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]

Step 1: mid = 6
  6² = 36 > 13 ❌
  Too large, search left
  
Step 2: mid = 3
  3² = 9 ≤ 13 ✓
  Could be answer! Save it.
  Try larger: search [4, 5]
  
Step 3: mid = 4
  4² = 16 > 13 ❌
  Too large
  
Best answer: 3 ✓
```

**Why This is Optimal**:
```
Brute force:
  for i = 0 to x:
      if i² > x:
          return i-1
  
  Time: O(x) — too slow for x = 2³¹-1

Binary search:
  Search space: x
  Each iteration: halve space
  Time: O(log x) ✓
  
For x = 2³¹-1:
  Linear: 2 billion iterations ❌
  Binary: 31 iterations ✓
  
Must use binary search!
```

**Critical Overflow Issue**:
```
When checking mid² ≤ x:

WRONG:
  if (mid * mid <= x)
  
Problem: mid * mid can overflow!

Example:
  mid = 50,000
  mid * mid = 2,500,000,000
  Max int = 2,147,483,647
  
  If mid > 46,340:
    mid * mid > 2³¹-1
    Overflow! ❌

CORRECT solutions:
  1. Use long: if ((long)mid * mid <= x)
  2. Division: if (mid <= x / mid)
  
Must handle overflow!
```

**Related Patterns**:
1. **Binary Search** — Core technique
2. **Answer Space Search** — Search for answer not in array
3. **Integer Square Root** — Classic problem
4. **Overflow-Safe Arithmetic** — Handle large numbers

---

## Algorithm & Approach

### Core Insight

**Why Binary Search Fits Perfectly:**
```
We need: largest ans where ans² ≤ x

Observation:
  If k² ≤ x, then all i² ≤ x for i < k
  If k² > x, then all i² > x for i > k
  
This creates a sorted property:
  [0, 1, 2, ..., ans] → valid (squares ≤ x)
  [ans+1, ans+2, ...] → invalid (squares > x)
  
Binary search finds boundary!
```

**The Optimal Strategy**:
```
Key observations:
  1. Answer is in range [0, x]
  2. Can check validity in O(1): compare mid² with x
  3. Monotonic property: if k valid, all smaller valid
  4. Need largest valid answer
  
Template: Upper bound binary search
  Find largest value satisfying condition
```

### Step-by-Step Algorithm

---

#### **Approach 1: Binary Search with Long Cast - MOST COMMON**

**Core Idea**:
- Binary search on [0, x]
- Cast to long to avoid overflow when squaring
- Keep track of best answer found

**Algorithm**
```
mySqrt(x):
    if x == 0 or x == 1:
        return x
    
    left = 0
    right = x
    ans = 0
    
    while left <= right:
        mid = left + (right - left) / 2
        
        if (long)mid * mid == x:
            return mid  // Perfect square
        else if (long)mid * mid < x:
            ans = mid  // Save answer, try larger
            left = mid + 1
        else:
            right = mid - 1  // Too large
    
    return ans
```

**Code Implementation**
```java
class Solution {
    public int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        
        int left = 0;
        int right = x;
        int ans = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid;
            
            if (square == x) {
                return mid;  // Perfect square
            } else if (square < x) {
                ans = mid;  // mid is valid, save it
                left = mid + 1;  // Try larger
            } else {
                right = mid - 1;  // mid too large
            }
        }
        
        return ans;
    }
}
```

**Example Walkthrough**

Input: `x = 13`

| Iteration | left | right | mid | mid² | Comparison | ans | Action |
|-----------|------|-------|-----|------|------------|-----|--------|
| 1 | 0 | 13 | 6 | 36 | 36 > 13 | 0 | right = 5 |
| 2 | 0 | 5 | 2 | 4 | 4 < 13 | 2 | left = 3 |
| 3 | 3 | 5 | 4 | 16 | 16 > 13 | 2 | right = 3 |
| 4 | 3 | 3 | 3 | 9 | 9 < 13 | 3 | left = 4 |
| End | 4 | 3 | - | - | left > right | 3 | Stop |

Return: **3** ✓

**Complexity Analysis**
- **Time**: O(log x) — Binary search on range [0, x]
- **Space**: O(1) — Only constant variables

---

#### **Approach 2: Binary Search with Division Check - NO OVERFLOW**

**Core Idea**: Avoid overflow by using division instead of multiplication.

**Algorithm**
```
Check: mid² ≤ x
Instead: mid ≤ x / mid

This avoids computing mid² (which can overflow)
```

**Code Implementation**
```java
class Solution {
    public int mySqrt(int x) {
        if (x == 0 || x == 1) {
            return x;
        }
        
        int left = 1;
        int right = x;
        int ans = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Avoid overflow: check mid <= x / mid instead of mid * mid <= x
            if (mid == x / mid) {
                return mid;  // Perfect square
            } else if (mid < x / mid) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return ans;
    }
}
```

**Key Advantage**: 
- No casting to long needed
- Purely integer arithmetic
- No overflow possible

**Complexity Analysis**
- **Time**: O(log x)
- **Space**: O(1)

---

#### **Approach 3: Optimized Range Binary Search**

**Core Idea**: For large x, sqrt(x) ≤ x/2 + 1, so search smaller range.

**Algorithm**
```
For x > 4:
  √x < x/2
  
Example:
  x = 100
  √100 = 10
  100/2 = 50
  10 < 50 ✓
  
So search [0, x/2 + 1] instead of [0, x]
```

**Code Implementation**
```java
class Solution {
    public int mySqrt(int x) {
        if (x < 2) {
            return x;
        }
        
        int left = 1;
        int right = x / 2 + 1;  // Optimized upper bound
        int ans = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid;
            
            if (square == x) {
                return mid;
            } else if (square < x) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return ans;
    }
}
```

**Key Advantage**: 
- Smaller search space for large x
- Faster convergence
- Same complexity but fewer iterations

**Complexity Analysis**
- **Time**: O(log(x/2)) = O(log x)
- **Space**: O(1)

---

#### **Approach 4: Newton's Method - ADVANCED**

**Core Idea**: Iterative refinement using calculus.

**Algorithm**
```
Newton-Raphson method:
  f(y) = y² - x
  f'(y) = 2y
  
  y_{n+1} = y_n - f(y_n) / f'(y_n)
           = y_n - (y_n² - x) / (2y_n)
           = (y_n + x/y_n) / 2
```

**Code Implementation**
```java
class Solution {
    public int mySqrt(int x) {
        if (x < 2) {
            return x;
        }
        
        long y = x;
        while (y * y > x) {
            y = (y + x / y) / 2;
        }
        
        return (int) y;
    }
}
```

**Key Points**: 
- Very fast convergence (quadratic)
- More complex to understand
- Fewer iterations than binary search

**Complexity Analysis**
- **Time**: O(log log x) — Quadratic convergence
- **Space**: O(1)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Overflow Handling | Code Complexity | Recommended |
|----------|------|-------|-------------------|-----------------|-------------|
| **Binary Search (long cast)** | **O(log x)** | **O(1)** | **Cast to long ✅** | **Simple ✅** | **Yes ✅** |
| Binary Search (division) | O(log x) | O(1) | Division check ✅ | Simple | Alternative |
| Optimized Range Binary | O(log x) | O(1) | Cast to long ✅ | Simple | Faster |
| Newton's Method | O(log log x) | O(1) | Long variable ✅ | Complex | Advanced |
| Linear Search | O(x) | O(1) | No overflow | Very Simple | Too slow ❌ |

**Winner**: **Binary Search with long cast** — simple, clear, efficient!

### Why Binary Search is Perfect

```
Problem requirements:
  ✓ Find integer square root
  ✓ Can't use built-in functions
  ✓ Need O(log x) or better
  ✓ x can be very large (2³¹-1)
  
Binary search properties:
  ✓ Searches range [0, x]
  ✓ O(log x) time
  ✓ Simple to implement
  ✓ Handles large inputs
  
Perfect fit!

Each iteration:
  Eliminates half of search space
  Simple comparison: mid² vs x
  Guaranteed progress
  
Converges to answer in ~31 iterations max!
```

### Why Answer Space Search

```
Not searching in array of values
Searching for the answer itself!

Conceptual array:
  [0, 1, 2, 3, 4, 5, ..., x]
  
Each number is a candidate answer
Check: is mid² ≤ x?

This is "answer space" binary search:
  Search space is possible answers
  Not an actual array
  Virtual sorted sequence
  
Same binary search logic applies!
```

### Why Keep Best Answer

```
Standard binary search:
  Find exact match
  Return when found
  Return -1 if not found
  
This problem:
  Find largest valid (mid² ≤ x)
  May not be exact match
  Return best found
  
Need to track best answer:
  ans = mid when mid² ≤ x
  Return ans at end
  
Ensures we don't lose valid answer!
```

### Why Overflow Matters

```
Naive check:
  if (mid * mid <= x)
  
Problem:
  mid can be up to x
  x up to 2³¹-1
  mid * mid up to (2³¹-1)² = 2⁶² - ...
  
  But int max is 2³¹-1
  Overflow! ❌

Example:
  mid = 50,000
  mid * mid = 2,500,000,000
  This fits in int ✓
  
  mid = 46,341
  mid * mid = 2,147,488,281
  Overflow! Becomes negative! ❌

Solutions:
  1. Cast to long: (long)mid * mid
  2. Division: mid <= x / mid
  
Must handle overflow!
```

### Why Long Cast is Preferred

```
Option 1: Cast to long
  if ((long)mid * mid <= x)
  
  Pros:
    Clear intent (computing square)
    Easy to understand
    Direct comparison
  
  Cons:
    Uses 64-bit arithmetic

Option 2: Division check
  if (mid <= x / mid)
  
  Pros:
    Pure 32-bit arithmetic
    No overflow possible
  
  Cons:
    Less intuitive
    Edge case when mid = 0

Most common: long cast
  Clearer code
  Minimal overhead
  Recommended in interviews
```

### Why Optimized Range Helps

```
For large x, √x ≤ x/2:

Proof:
  √x ≤ x/2
  2√x ≤ x
  4x ≤ x² (for x ≥ 4)
  True for x ≥ 4 ✓

So:
  Standard range: [0, x]
  Optimized: [0, x/2 + 1]
  
Example: x = 1000
  Standard: search [0, 1000]
  Optimized: search [0, 501]
  
  √1000 ≈ 31.6
  Still in [0, 501] ✓
  
  Faster convergence!

For very large x, saves iterations.
```

---

## Critical Edge Cases & Gotchas

### 1. **Zero**
```java
Input: x = 0
Output: 0
√0 = 0
Must handle explicitly or works naturally
```

### 2. **One**
```java
Input: x = 1
Output: 1
√1 = 1
Edge case for binary search starting point
```

### 3. **Two**
```java
Input: x = 2
Output: 1
√2 ≈ 1.414
Floor = 1
Verify: 1² = 1 ≤ 2 ✓, 2² = 4 > 2 ✓
```

### 4. **Perfect Square**
```java
Input: x = 16
Output: 4
√16 = 4.0 exactly
Should return 4, not continue searching
```

### 5. **Non-Perfect Square**
```java
Input: x = 15
Output: 3
√15 ≈ 3.872
Floor = 3
Must return floor, not round
```

### 6. **Large Perfect Square**
```java
Input: x = 46340 * 46340 = 2147395600
Output: 46340
Must handle overflow in mid * mid
```

### 7. **Maximum Input**
```java
Input: x = 2147483647 (2³¹ - 1)
Output: 46340
√2147483647 ≈ 46340.95
Must use long cast or division check
```

### 8. **Just Below Overflow**
```java
Input: x = 46341 * 46341 - 1 = 2147488280
Would overflow in int!
Must handle carefully
```

### 9. **Small Non-Square**
```java
Input: x = 3
Output: 1
√3 ≈ 1.732
Floor = 1
```

### 10. **Power of 2**
```java
Input: x = 64
Output: 8
2⁶ = 64, √64 = 8
Common test case
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Integer Overflow in Multiplication**
```java
// WRONG - mid * mid overflows
if (mid * mid <= x) {
    ans = mid;
}
```

**Why wrong**: For large mid, mid * mid overflows int!

**Dry run failure for x=2147483647:**
```
mid = 50000
mid * mid = 2,500,000,000
Fits in int ✓

mid = 46341
mid * mid = 2,147,488,281
Max int = 2,147,483,647
Overflow! Becomes negative ❌

Comparison: negative <= 2147483647? true
Wrong branch taken!
```

**Fix**: Cast to long
```java
if ((long)mid * mid <= x) {
    ans = mid;
}
```

### ❌ **MISTAKE 2: Not Saving Best Answer**
```java
// WRONG - doesn't track best answer
while (left <= right) {
    int mid = left + (right - left) / 2;
    if ((long)mid * mid < x) {
        left = mid + 1;  // Forgot: ans = mid
    } else {
        right = mid - 1;
    }
}
return left;  // May be wrong!
```

**Why wrong**: Doesn't save valid answers!

**Dry run failure for x=13:**
```
Iteration 1: mid=6, 36>13, right=5
Iteration 2: mid=2, 4<13, left=3 (didn't save 2!)
Iteration 3: mid=4, 16>13, right=3
Iteration 4: mid=3, 9<13, left=4 (didn't save 3!)
Loop ends: left=4, right=3

Return left=4 ❌
But 4²=16>13, wrong!

Should have saved 3 when found!
```

**Fix**: Save answer when valid
```java
if ((long)mid * mid <= x) {
    ans = mid;  // Save it!
    left = mid + 1;
}
```

### ❌ **MISTAKE 3: Wrong Comparison (< instead of <=)**
```java
// WRONG - uses < instead of <=
if ((long)mid * mid < x) {
    ans = mid;
    left = mid + 1;
}
```

**Why wrong**: Misses perfect squares!

**Dry run failure for x=9:**
```
mid = 3
3 * 3 = 9
9 < 9? false
Doesn't save 3 ❌

Goes to else branch:
right = mid - 1 = 2

Returns ans=0 or previous saved value
Wrong! Should return 3 for perfect square.
```

**Fix**: Use <=
```java
if ((long)mid * mid <= x) {  // Include equal!
    ans = mid;
    left = mid + 1;
}
```

### ❌ **MISTAKE 4: Using Built-in Functions**
```java
// WRONG - uses Math.sqrt()
return (int) Math.sqrt(x);
```

**Why wrong**: Problem explicitly forbids it!

**Instant disqualification in interview!**

**Fix**: Implement binary search or Newton's method

### ❌ **MISTAKE 5: Wrong Initial Range**
```java
// WRONG - right = x / 2 without checking small x
int left = 1;
int right = x / 2;  // Fails for x < 4!
```

**Why wrong**: For small x, sqrt(x) > x/2!

**Dry run failure for x=2:**
```
left = 1
right = 2 / 2 = 1

Search [1, 1]:
mid = 1
1 * 1 = 1 < 2 ✓
Save ans = 1
left = 2

Loop ends
Return 1 ✓ (Works by luck!)

But for x=3:
left = 1
right = 3 / 2 = 1

Same range [1, 1]
Returns 1 ✓ (Also works!)

Actually, fails for x=1:
right = 1 / 2 = 0
left = 1
left > right immediately
May return wrong answer!
```

**Fix**: Check for small x
```java
if (x < 2) return x;
int right = x / 2 + 1;  // Or just use x
```

### ❌ **MISTAKE 6: Division by Zero Check**
```java
// WRONG when using division approach
if (mid <= x / mid) {  // Fails when mid = 0!
    ans = mid;
}
```

**Why wrong**: Division by zero!

**Dry run failure for x=0:**
```
mid = 0
Check: 0 <= 0 / 0?
Division by zero! ❌
```

**Fix**: Handle zero explicitly
```java
if (x == 0) return 0;
int left = 1;  // Start from 1, not 0
```

### ❌ **MISTAKE 7: Returning Wrong Final Value**
```java
// WRONG - returns left instead of ans
return left;
```

**Why wrong**: Left pointer may overshoot!

**Dry run failure for x=8:**
```
Final state: left=4, right=2, ans=2

If return left=4:
  4²=16>8 ❌ Wrong!
  
Should return ans=2 ✓
```

**Fix**: Return ans variable
```java
int ans = 0;
// ... binary search updates ans
return ans;
```

---

## Complexity Analysis

### Time Complexity: **O(log x)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **While loop iterations** | O(log x) | O(1) | O(log x) |
| **Calculate mid** | O(log x) | O(1) | O(log x) |
| **Square mid** | O(log x) | O(1) | O(log x) |
| **Compare** | O(log x) | O(1) | O(log x) |
| **Update pointers** | O(log x) | O(1) | O(log x) |
| **Total** | - | - | **O(log x)** |

**Time analysis**:
```
Binary search on range [0, x]
Each iteration halves space

Search space: x
After iteration k: x / 2^k

Converges when: x / 2^k = 1
Solving: k = log₂(x)

Maximum iterations: ⌈log₂(x)⌉

Examples:
  x = 10: log₂(10) ≈ 3.32 → 4 iterations max
  x = 100: log₂(100) ≈ 6.64 → 7 iterations max
  x = 2³¹-1: log₂(2³¹-1) ≈ 31 → 31 iterations max

Very efficient!
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| left | O(1) | Single integer |
| right | O(1) | Single integer |
| mid | O(1) | Single integer |
| ans | O(1) | Single integer |
| square | O(1) | Long variable |
| **Total** | **O(1)** | Constant space |

**Space analysis**:
```
Only fixed number of variables
No arrays, no recursion stack
Space: O(1) ✓

Iterative approach preferred over recursive
  Recursive would use O(log x) stack space
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `x = 13`

**Expected Output:** `3` (since √13 ≈ 3.605, floor = 3)

---

**Initial State:**
```
Search range: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]
               ↑                                          ↑
              left                                     right

left = 0
right = 13
ans = 0
target = ⌊√13⌋ = 3
```

---

**Iteration 1:**
```
Calculate mid:
  mid = 0 + (13-0)/2 = 6
  
Range: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]
        ↑              ↑                          ↑
       left           mid                      right

Check:
  mid² = 6² = 36
  36 ≤ 13? false
  36 > 13 (too large!)
  
Action: Search left half
  right = mid - 1 = 5
  ans remains 0
  
New range: [0, 1, 2, 3, 4, 5]
  left=0, right=5, ans=0
```

---

**Iteration 2:**
```
Calculate mid:
  mid = 0 + (5-0)/2 = 2
  
Range: [0, 1, 2, 3, 4, 5]
        ↑     ↑        ↑
       left  mid    right

Check:
  mid² = 2² = 4
  4 ≤ 13? true ✓
  4 < 13 (valid, but maybe larger exists)
  
Action: Save answer, search right
  ans = 2
  left = mid + 1 = 3
  
New range: [3, 4, 5]
  left=3, right=5, ans=2
```

---

**Iteration 3:**
```
Calculate mid:
  mid = 3 + (5-3)/2 = 4
  
Range: [3, 4, 5]
        ↑  ↑  ↑
      left mid right

Check:
  mid² = 4² = 16
  16 ≤ 13? false
  16 > 13 (too large!)
  
Action: Search left half
  right = mid - 1 = 3
  ans remains 2
  
New range: [3]
  left=3, right=3, ans=2
```

---

**Iteration 4:**
```
Calculate mid:
  mid = 3 + (3-3)/2 = 3
  
Range: [3]
        ↑
     left=mid=right

Check:
  mid² = 3² = 9
  9 ≤ 13? true ✓
  9 < 13 (valid!)
  
Action: Save answer, search right
  ans = 3
  left = mid + 1 = 4
  
New state:
  left=4, right=3, ans=3
```

---

**Loop Ends:**
```
Condition: left <= right?
  4 <= 3? false
  
Loop exits

Return: ans = 3 ✓

Verification:
  3² = 9 ≤ 13 ✓
  4² = 16 > 13 ✓
  
Answer is correct!
```

---

**Summary:**
```
Total iterations: 4
Values checked: 6, 2, 4, 3
Best answer saved: 2 → 3
Final answer: 3 ✓

Binary search efficiently found floor(√13)!
```

---

### Another Example: Perfect Square

**Input:** `x = 16`

```
Iteration 1:
  mid = 8
  8² = 64 > 16
  Search left: right = 7
  
Iteration 2:
  mid = 3
  3² = 9 < 16
  Save ans = 3
  Search right: left = 4
  
Iteration 3:
  mid = 5
  5² = 25 > 16
  Search left: right = 4
  
Iteration 4:
  mid = 4
  4² = 16 == 16 ✓
  Return 4 immediately!
  
Perfect square found early!
```

---

### Visualization of Answer Space

```
x = 13

Answer space: [0, 1, 2, 3, 4, 5, 6, 7, 8, ...]

Validity:
  0² = 0 ≤ 13 ✓ valid
  1² = 1 ≤ 13 ✓ valid
  2² = 4 ≤ 13 ✓ valid
  3² = 9 ≤ 13 ✓ valid ← ANSWER
  4² = 16 > 13 ✗ invalid
  5² = 25 > 13 ✗ invalid
  ...

Pattern: [✓, ✓, ✓, ✓, ✗, ✗, ...]

Binary search finds last ✓ (boundary)
```

---

### Range Reduction Visualization

```
x = 100, find ⌊√100⌋ = 10

Start:     [0 ...................... 100]
           
Iter 1:    [0 ... 50] (50² > 100)
           
Iter 2:    [0 . 25] (25² > 100)
           
Iter 3:    [0 12] (12² > 100)
           
Iter 4:    [0..6] (6² < 100, save)
           
Iter 5:    [7.12] (9 works)
           
Iter 6:    [10.12] (10 found!)
           
Found 10 in 6 iterations!
```

---

## Comparison of Approaches

| Approach | Time | Space | Overflow Handling | Code Lines | Clarity | Recommended |
|----------|------|-------|-------------------|------------|---------|-------------|
| **Binary Search (long)** | **O(log x)** | **O(1)** | **Cast to long ✅** | **~25** | **Excellent ✅** | **Yes ✅** |
| Binary Search (division) | O(log x) | O(1) | Division check ✅ | ~25 | Good | Alternative |
| Optimized Range Binary | O(log x) | O(1) | Cast to long ✅ | ~27 | Excellent | Faster |
| Newton's Method | O(log log x) | O(1) | Long variable ✅ | ~10 | Complex | Advanced |
| Linear Search | O(x) | O(1) | N/A | ~8 | Simple | Too slow ❌ |

**Winner**: **Binary Search with long cast** — clear, efficient, standard!

---

## Key Takeaways

1. **Binary search on answer space** — search for answer itself, not in array
2. **Answer range is [0, x]** — all possible square roots
3. **Check validity: mid² ≤ x** — is mid a valid answer?
4. **Save best answer found** — track largest valid mid
5. **Handle overflow** — use (long)mid*mid or mid <= x/mid
6. **Return saved answer** — not left or right pointer
7. **Include equal in check** — mid² ≤ x, not just <
8. **Perfect squares** — can return early when mid² == x
9. **Optimize range** — for x ≥ 4, √x ≤ x/2, so search [0, x/2+1]
10. **O(log x) time** — efficient even for 2³¹-1

---

## Interview Tips

**What to say in an interview:**

> "This problem asks me to find the integer square root without using built-in functions. Since I need an efficient solution and the answer lies in a sorted range from 0 to x, I'll use binary search on the answer space. The key insight is that I'm searching for the largest integer whose square is less than or equal to x. I'll maintain a variable to track the best answer found so far. In each iteration, I calculate mid and check if mid squared is less than or equal to x. If yes, mid is a valid answer so I save it and search for potentially larger values. If mid squared is too large, I search the left half. One critical detail is handling integer overflow when computing mid squared—for large values of mid, multiplying mid by itself can exceed integer max value. I'll cast to long before multiplication to avoid this. The time complexity is O(log x) since we're doing binary search, and space is O(1) using only a few variables."

**Key points to mention:**
1. **Binary search on answer space** — not searching in array
2. **Range [0, x]** — all possible answers
3. **Looking for largest valid** — mid² ≤ x
4. **Save best answer** — don't lose valid results
5. **Overflow handling** — cast to long before squaring
6. **Return ans variable** — not pointer
7. **O(log x) time** — efficient for large inputs
8. **Can optimize range** — search [0, x/2+1] for x ≥ 4

**Common Follow-ups:**
- "How do you handle overflow?" → Cast to long: `(long)mid * mid`
- "Can you optimize the search space?" → Yes, for x ≥ 4, use range [0, x/2+1]
- "What about Newton's method?" → Faster (O(log log x)) but more complex
- "Why save answer variable?" → Because we need largest valid, may not find exact match
- "What if x is a perfect square?" → Can return immediately when mid² == x

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Sqrt(x)** | Easy | **Binary Search on Answer Space** | **This problem** |
| Guess Number Higher Or Lower | Easy | Binary Search with API | API-based search |
| Search Insert Position | Easy | Binary Search | Find insertion point |
| Valid Perfect Square | Easy | Binary Search | Check if perfect square |
| Pow(x, n) | Medium | Binary Search / Divide & Conquer | Exponentiation |
| Koko Eating Bananas | Medium | Binary Search on Answer | Search for minimum speed |
| Capacity To Ship Packages | Medium | Binary Search on Answer | Search for minimum capacity |
| Split Array Largest Sum | Hard | Binary Search on Answer | Search for minimum maximum |

**Pattern Progression**:
1. **Standard binary search** — Find element in sorted array
2. **Binary search on answer space** (this problem) — Search for answer itself
3. **Binary search with validation** — Check if answer works
4. **Minimize/maximize answer** — Find optimal value in range

---

## Final Pattern Label

✅ **Binary Search on Answer Space (Integer Square Root)**

**Remember:** This is **binary search on answer space** [0, x], not on an array. We're searching for the **largest integer** whose **square is ≤ x**. The algorithm checks if `mid²` is valid and **saves the best answer** found. **Critical**: handle **overflow** by casting to `long`: `(long)mid * mid`, because for large mid values (e.g., > 46,340), mid² exceeds integer max. We **save valid answers** as we find them (`ans = mid` when `mid² ≤ x`) and search for larger values. **Return the saved answer**, not the pointer. Include **equality** in the check (`mid² ≤ x`, not just `<`) to handle perfect squares. Can **optimize** the search range to [0, x/2+1] for x ≥ 4 since √x ≤ x/2. Time complexity is **O(log x)** with at most **31 iterations** for maximum input, space is **O(1)**. Not a trick—just **answer space binary search**!
