# Koko Eating Bananas

## Problem Description

**Difficulty**: Medium

You are given an integer array `piles` where `piles[i]` is the number of bananas in the `i-th` pile. You are also given an integer `h`, which represents the number of hours you have to eat all the bananas.

You may decide your **bananas-per-hour eating rate** of `k`. Each hour, you may choose a pile of bananas and eat `k` bananas from that pile. If the pile has **less than** `k` bananas, you may **finish eating the pile** but you **cannot eat from another pile** in the same hour.

Return the **minimum integer** `k` such that you can eat all the bananas within `h` hours.

## Examples

### Example 1:
```
Input: piles = [1,4,3,2], h = 9
Output: 2

Explanation:
  With eating rate k = 2:
    Pile 1: 1 banana → ceil(1/2) = 1 hour
    Pile 4: 4 bananas → ceil(4/2) = 2 hours
    Pile 3: 3 bananas → ceil(3/2) = 2 hours
    Pile 2: 2 bananas → ceil(2/2) = 1 hour
    Total: 1 + 2 + 2 + 1 = 6 hours ≤ 9 ✓
    
  With eating rate k = 1:
    Pile 1: 1 hour
    Pile 4: 4 hours
    Pile 3: 3 hours
    Pile 2: 2 hours
    Total: 10 hours > 9 ❌
    
  Minimum k = 2
```

### Example 2:
```
Input: piles = [25,10,23,4], h = 4
Output: 25

Explanation:
  4 piles, 4 hours → must eat each pile in 1 hour
  Largest pile is 25 → need k = 25
  
  With k = 25:
    Pile 25: ceil(25/25) = 1 hour
    Pile 10: ceil(10/25) = 1 hour
    Pile 23: ceil(23/25) = 1 hour
    Pile 4: ceil(4/25) = 1 hour
    Total: 4 hours ✓
```

### Example 3:
```
Input: piles = [30,11,23,4,20], h = 5
Output: 30

Explanation:
  5 piles, 5 hours → one pile per hour
  Need k = max(piles) = 30
```

### Example 4:
```
Input: piles = [30,11,23,4,20], h = 6
Output: 23

Explanation:
  With k = 23:
    Pile 30: ceil(30/23) = 2 hours
    Pile 11: ceil(11/23) = 1 hour
    Pile 23: ceil(23/23) = 1 hour
    Pile 4: ceil(4/23) = 1 hour
    Pile 20: ceil(20/23) = 1 hour
    Total: 6 hours ✓
    
  With k = 22:
    Pile 30: ceil(30/22) = 2 hours
    Pile 23: ceil(23/22) = 2 hours
    Others: 3 hours
    Total: 7 hours > 6 ❌
```

### Example 5:
```
Input: piles = [3,6,7,11], h = 8
Output: 4

Explanation:
  With k = 4:
    3: ceil(3/4) = 1 hour
    6: ceil(6/4) = 2 hours
    7: ceil(7/4) = 2 hours
    11: ceil(11/4) = 3 hours
    Total: 8 hours ✓
```

### Example 6:
```
Input: piles = [1], h = 1
Output: 1

Explanation:
  Single pile, single hour
  k = 1 works
```

### Example 7:
```
Input: piles = [1000000000], h = 2
Output: 500000000

Explanation:
  Very large pile, 2 hours available
  k = 500000000 → 2 hours needed
  k = 499999999 → 3 hours needed
  Minimum: 500000000
```

### Example 8:
```
Input: piles = [312884470], h = 968709470
Output: 1

Explanation:
  Many hours available
  Can eat very slowly
  k = 1 works
```

### Example 9:
```
Input: piles = [805306368,805306368,805306368], h = 1000000000
Output: 3

Explanation:
  Three equal large piles
  Plenty of time
  Can eat slowly
```

### Example 10:
```
Input: piles = [2,2], h = 2
Output: 2

Explanation:
  Two piles of 2, 2 hours
  Need k = 2 to finish each in 1 hour
```

## Constraints
- 1 <= piles.length <= 1,000
- piles.length <= h <= 1,000,000
- 1 <= piles[i] <= 1,000,000,000
- h >= piles.length (always have at least one hour per pile)

**Recommended Complexity**: O(n log m) time and O(1) space, where n is number of piles and m is max pile size

---

## Pattern Recognition

**Primary Pattern**: **Binary Search on Answer Space (Minimum Speed with Constraint)**

**Why This Pattern?**
- Not searching for value in array
- Searching for **minimum speed k** that satisfies constraint
- Answer range: [1, max(piles)] is **bounded** and **searchable**
- Can **validate** any k in O(n) time
- Monotonic property: if k works, all larger k also work

**Key Insight**: Hours Decrease as Speed Increases
```
Eating speed k and total hours are inversely related:

k = 1 (slow):   many hours needed
k = 10:         fewer hours needed  
k = 100:        even fewer hours
k = max(piles): minimum hours (one per pile)

This is monotonic!

For piles = [3, 6, 7, 11], h = 8:

k = 1:  3+6+7+11 = 27 hours ❌ (too slow)
k = 2:  2+3+4+6 = 15 hours ❌
k = 3:  1+2+3+4 = 10 hours ❌
k = 4:  1+2+2+3 = 8 hours ✓ (minimum!)
k = 5:  1+2+2+3 = 8 hours ✓
k = 11: 1+1+1+1 = 4 hours ✓

Pattern: [❌ ❌ ❌ ✓ ✓ ... ✓]
         (too slow) (works)
         
Binary search finds first ✓ (minimum k)
```

**Hours Calculation for Given k**:
```
For each pile with x bananas:
  hours = ceil(x / k)
  
Why ceil?
  If pile has 7 bananas and k=3:
    Hour 1: eat 3 (4 left)
    Hour 2: eat 3 (1 left)
    Hour 3: eat 1 (done)
    
  Total: 3 hours = ceil(7/3) = ceil(2.33) = 3
  
Ceiling formula (integer math):
  ceil(x / k) = (x + k - 1) / k
  
Total hours for all piles:
  sum(ceil(pile[i] / k) for all i)
```

**Binary Search Strategy**:
```
Search for minimum k in range [1, max(piles)]:

Lower bound: k = 1 (slowest possible)
Upper bound: k = max(piles) (eat largest pile in 1 hour)

For each mid:
  Calculate total hours with speed k = mid
  
  If hours <= h:
    k is valid, try smaller (search left)
    Save as potential answer
    
  If hours > h:
    k too slow, need faster (search right)
    
Return minimum valid k found
```

**Example Showing Binary Search**:
```
piles = [3, 6, 7, 11], h = 8
Range: [1, 11]

Step 1: mid = 6
  Hours: ceil(3/6) + ceil(6/6) + ceil(7/6) + ceil(11/6)
       = 1 + 1 + 2 + 2 = 6 ≤ 8 ✓
  Valid! Try smaller: search [1, 5]
  ans = 6

Step 2: mid = 3
  Hours: 1 + 2 + 3 + 4 = 10 > 8 ❌
  Too slow! Search [4, 5]

Step 3: mid = 4
  Hours: 1 + 2 + 2 + 3 = 8 ≤ 8 ✓
  Valid! Try smaller: search [4, 3]
  ans = 4
  
Loop ends (left > right)
Return ans = 4 ✓
```

**Why This is Optimal**:
```
Brute force: Try k = 1, 2, 3, ..., max(piles)
  Time: O(m × n) where m = max(piles)
  For max=1,000,000,000 and n=1,000:
    Up to 10¹² operations ❌

Binary search: O(log m × n)
  Iterations: log₂(m) ≈ 30 for m=10⁹
  Each iteration: O(n) to calculate hours
  Total: 30 × 1,000 = 30,000 operations ✓
  
33 million times faster!

Must use binary search for large inputs.
```

**Related Patterns**:
1. **Binary Search on Answer Space** — Search for answer, not in array
2. **Minimum Speed Problem** — Find minimum rate satisfying constraint
3. **Capacity Problems** — Similar to ship packages, split array
4. **Validation Function** — Check if answer works in O(n)

---

## Algorithm & Approach

### Core Insight

**Why Binary Search on Speed Works:**
```
Key properties:
  1. Valid speed range: [1, max(piles)]
  2. Monotonic: if speed k works, all k' > k also work
  3. Can validate speed in O(n): sum(ceil(pile/k))
  4. Want minimum k that works
  
This is perfect for binary search!

Search template: Find minimum valid value
  Binary search on [1, max]
  For each mid, check validity
  If valid, save and search left (smaller)
  If invalid, search right (larger)
```

**The Optimal Strategy**:
```
Key observations:
  1. Slower speed → more hours needed
  2. Faster speed → fewer hours needed
  3. There exists a threshold: speeds ≥ threshold work
  4. Binary search finds minimum threshold
  
Validation:
  For given k, total hours = sum(ceil(pile[i] / k))
  Valid if total hours ≤ h
```

### Step-by-Step Algorithm

---

#### **Approach 1: Binary Search on Eating Speed - OPTIMAL**

**Core Idea**:
- Binary search on speed range [1, max(piles)]
- For each speed, calculate total hours needed
- Find minimum speed where total hours ≤ h

**Algorithm**
```
minEatingSpeed(piles, h):
    left = 1
    right = max(piles)
    result = right
    
    while left <= right:
        mid = left + (right - left) / 2
        
        // Calculate hours needed with speed mid
        hours = 0
        for pile in piles:
            hours += ceil(pile / mid)
            
        if hours <= h:
            result = mid  // Valid, save answer
            right = mid - 1  // Try smaller
        else:
            left = mid + 1  // Too slow, need faster
    
    return result
```

**Code Implementation**
```java
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = 0;
        
        // Find max pile (upper bound for speed)
        for (int pile : piles) {
            right = Math.max(right, pile);
        }
        
        int result = right;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Calculate total hours with speed mid
            long hours = 0;
            for (int pile : piles) {
                hours += (pile + mid - 1) / mid;  // Ceiling division
            }
            
            if (hours <= h) {
                result = mid;  // Valid speed, try smaller
                right = mid - 1;
            } else {
                left = mid + 1;  // Too slow, need faster
            }
        }
        
        return result;
    }
}
```

**Example Walkthrough**

Input: `piles = [3, 6, 7, 11], h = 8`

max(piles) = 11

| Iteration | left | right | mid | Hours Calculation | Total Hours | Comparison | Action |
|-----------|------|-------|-----|-------------------|-------------|------------|--------|
| 1 | 1 | 11 | 6 | ceil(3/6)+ceil(6/6)+ceil(7/6)+ceil(11/6) | 1+1+2+2 = 6 | 6 ≤ 8 ✓ | result=6, right=5 |
| 2 | 1 | 5 | 3 | ceil(3/3)+ceil(6/3)+ceil(7/3)+ceil(11/3) | 1+2+3+4 = 10 | 10 > 8 ❌ | left=4 |
| 3 | 4 | 5 | 4 | ceil(3/4)+ceil(6/4)+ceil(7/4)+ceil(11/4) | 1+2+2+3 = 8 | 8 ≤ 8 ✓ | result=4, right=3 |
| End | 4 | 3 | - | - | - | left > right | Stop |

Return: **4** ✓

**Complexity Analysis**
- **Time**: O(n log m) where n = piles.length, m = max(piles)
  - Binary search: O(log m) iterations
  - Each iteration: O(n) to calculate hours
- **Space**: O(1) — Only constant variables

---

#### **Approach 2: Binary Search with Helper Function - CLEANER**

**Core Idea**: Extract hours calculation into helper function for clarity.

**Code Implementation**
```java
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = getMax(piles);
        int result = right;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canFinish(piles, mid, h)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    private boolean canFinish(int[] piles, int k, int h) {
        long hours = 0;
        for (int pile : piles) {
            hours += (pile + k - 1) / k;
            if (hours > h) return false;  // Early termination
        }
        return hours <= h;
    }
    
    private int getMax(int[] piles) {
        int max = 0;
        for (int pile : piles) {
            max = Math.max(max, pile);
        }
        return max;
    }
}
```

**Key Advantage**: 
- Cleaner code structure
- Early termination in validation
- More testable (separate functions)

**Complexity Analysis**
- **Time**: O(n log m)
- **Space**: O(1)

---

#### **Approach 3: Optimized Upper Bound**

**Core Idea**: Upper bound could be total_bananas / h instead of max(piles).

**Algorithm**
```
Upper bound optimization:
  Instead of max(piles), use:
    upper = ceil(sum(piles) / h)
    
  Why?
    If total bananas = 100, hours = 10
    Average speed needed = 10 per hour
    
  But max(piles) might be much larger
  
  Problem: Doesn't always work correctly!
  Edge case: h = piles.length
    Need speed = max(piles)
    But average might be much smaller
    
  Better to stick with max(piles) as upper bound
```

**Code Implementation**
```java
class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = 1000000000;  // Or use max(piles)
        
        // Can also calculate total / h as upper bound
        // But max(piles) is simpler and always correct
        
        int result = right;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (canFinish(piles, mid, h)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    private boolean canFinish(int[] piles, int k, int h) {
        long hours = 0;
        for (int pile : piles) {
            hours += (pile + k - 1) / k;
        }
        return hours <= h;
    }
}
```

**Key Difference**: 
- Fixed upper bound (10⁹)
- Simpler but same iterations for large piles
- max(piles) is better optimization

**Complexity Analysis**
- **Time**: O(n log(10⁹)) ≈ O(30n)
- **Space**: O(1)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Iterations | Code Complexity | Recommended |
|----------|------|-------|------------|-----------------|-------------|
| **Binary Search on Speed** | **O(n log m)** | **O(1)** | **~log₂ m** | **Medium ✅** | **Yes ✅** |
| Binary with Helper | O(n log m) | O(1) | ~log₂ m | Simple | Cleaner |
| Linear Search Speeds | O(n × m) | O(1) | up to m | Simple | Too slow ❌ |

**Winner**: **Binary Search on Speed** — optimal time, required for large inputs!

### Why Binary Search is Essential

```
Problem constraints:
  piles[i] up to 10⁹
  piles.length up to 1,000
  h up to 10⁶
  
Linear search:
  Try k = 1, 2, 3, ..., max(piles)
  For max = 10⁹:
    Up to 10⁹ iterations
    Each iteration: O(n) = 1,000 operations
    Total: 10¹² operations ❌
    
Binary search:
  Iterations: log₂(10⁹) ≈ 30
  Each iteration: O(n) = 1,000 operations
  Total: 30,000 operations ✓
  
33 million times faster!

For large inputs, linear is impossible.
Binary search is required.
```

### Why Monotonic Property Matters

```
Key insight: If speed k works, all speeds > k also work

Proof:
  If k works: sum(ceil(pile[i] / k)) ≤ h
  
  For k' > k:
    ceil(pile[i] / k') ≤ ceil(pile[i] / k)
    (Larger denominator → smaller ceiling)
    
  So: sum(ceil(pile[i] / k')) ≤ sum(ceil(pile[i] / k)) ≤ h
  
  Therefore k' also works ✓

This creates pattern: [❌ ❌ ❌ ✓ ✓ ✓ ...]
                      (too slow) (fast enough)
                      
Binary search finds boundary (first ✓)
```

### Why Ceiling Division

```
Integer division truncates:
  7 / 3 = 2 (not 2.33)
  
But we need ceiling:
  pile = 7, k = 3
  Actually need 3 hours (not 2)
  
Ceiling formula:
  ceil(a / b) = (a + b - 1) / b
  
Proof:
  If a % b == 0:
    (a + b - 1) / b = (a - 1) / b + 1 = a/b - 1 + 1 = a/b ✓
    
  If a % b != 0:
    (a + b - 1) / b rounds up correctly ✓
    
Example: ceil(7 / 3)
  (7 + 3 - 1) / 3 = 9 / 3 = 3 ✓
  
Use (pile + k - 1) / k in code!
```

### Why Search for Minimum

```
Many speeds might work:

piles = [3, 6, 7, 11], h = 8

k = 4:  8 hours ✓
k = 5:  8 hours ✓
k = 6:  6 hours ✓
k = 11: 4 hours ✓

All work, but we want minimum!

Why minimum?
  Problem asks for "minimum integer k"
  Koko wants to eat slowly
  Minimize eating speed
  
Binary search naturally finds minimum:
  When mid works, search left (smaller)
  Save best answer found
  Return smallest valid k
```

### Why Upper Bound is max(piles)

```
Upper bound for speed:
  If k = max(piles):
    Each pile takes at most 1 hour
    Total hours = piles.length
    
  Since h >= piles.length (constraint):
    k = max(piles) always works
    
Can we use smaller upper bound?
  total_bananas / h might work
  But edge cases when h = piles.length
  
max(piles) is:
  ✓ Always correct
  ✓ Simple to compute
  ✓ Often optimal
  
Best choice for upper bound!
```

### Why Long for Hours Accumulation

```
Potential overflow:
  piles.length up to 1,000
  Each pile up to 10⁹
  k = 1 (worst case)
  
  Total hours = 1,000 × 10⁹ = 10¹²
  Max int = 2 × 10⁹ (approx)
  
  Overflow! ❌

Solution:
  Use long for hours accumulation
  long max = 9 × 10¹⁸ (plenty of room)
  
In code:
  long hours = 0;  // Not int!
  for (int pile : piles) {
      hours += (pile + k - 1) / k;
  }
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Pile, Single Hour**
```java
Input: piles = [10], h = 1
Output: 10
Must eat entire pile in 1 hour
k = max(piles) = 10
```

### 2. **Multiple Piles, Minimum Hours (h = n)**
```java
Input: piles = [30, 11, 23, 4, 20], h = 5
Output: 30
One hour per pile, need k = max(piles)
```

### 3. **Many Hours Available**
```java
Input: piles = [3, 6, 7, 11], h = 100
Output: 1
Plenty of time, k = 1 works
```

### 4. **All Piles Equal**
```java
Input: piles = [5, 5, 5, 5], h = 8
Output: 3
With k = 3: each pile takes 2 hours
Total: 8 hours ✓
```

### 5. **Very Large Single Pile**
```java
Input: piles = [1000000000], h = 2
Output: 500000000
Need to split into 2 hours
k = ceil(10⁹ / 2) = 5×10⁸
```

### 6. **One Large, Many Small**
```java
Input: piles = [100, 1, 1, 1, 1], h = 6
Output: 100
Large pile dominates
Need k = 100
```

### 7. **Exact Division**
```java
Input: piles = [6, 9, 12], h = 6
Output: 4
k = 4: 2+3+3 = 8 hours ❌
k = 5: 2+2+3 = 7 hours ❌
k = 6: 1+2+2 = 5 hours ✓
Actually k = 6 works
```

### 8. **Powers of 2**
```java
Input: piles = [32, 16, 8, 4], h = 8
Output: 8
With k = 8: 4+2+1+1 = 8 hours ✓
```

### 9. **Minimum h (h = n)**
```java
Input: piles = [10, 20, 30], h = 3
Output: 30
Constraint: h >= piles.length
When h = n, must use max(piles)
```

### 10. **Large Array, Small Max**
```java
Input: piles = [1, 1, 1, ..., 1] (1000 times), h = 1000
Output: 1
All piles are 1
k = 1 sufficient
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Integer Overflow in Hours**
```java
// WRONG - hours can overflow int
int hours = 0;
for (int pile : piles) {
    hours += (pile + k - 1) / k;
}
```

**Why wrong**: Total hours can exceed int max!

**Dry run failure for piles=[10⁹, 10⁹, ...], k=1:**
```
1,000 piles each 10⁹ bananas
k = 1
hours per pile = 10⁹

Total hours = 1,000 × 10⁹ = 10¹²
Max int = 2,147,483,647 ≈ 2 × 10⁹

Overflow! Becomes negative ❌
Comparison with h fails
Wrong answer!
```

**Fix**: Use long
```java
long hours = 0;
for (int pile : piles) {
    hours += (pile + k - 1) / k;
}
```

### ❌ **MISTAKE 2: Wrong Ceiling Calculation**
```java
// WRONG - uses regular division
hours += pile / mid;  // Should be ceiling!
```

**Why wrong**: Underestimates hours needed!

**Dry run failure for pile=7, k=3:**
```
WRONG: 7 / 3 = 2 hours

But actually:
  Hour 1: eat 3 (4 left)
  Hour 2: eat 3 (1 left)
  Hour 3: eat 1 (done)
  
Need 3 hours, not 2! ❌

This makes algorithm think k=3 works
When it actually doesn't
Returns wrong answer!
```

**Fix**: Use ceiling formula
```java
hours += (pile + mid - 1) / mid;
// Or: hours += (int) Math.ceil((double) pile / mid);
```

### ❌ **MISTAKE 3: Wrong Search Direction**
```java
// WRONG - searches in wrong direction
if (hours <= h) {
    left = mid + 1;  // Should search left for smaller!
} else {
    right = mid - 1;
}
```

**Why wrong**: Finds maximum valid k, not minimum!

**Dry run failure:**
```
piles = [3, 6, 7, 11], h = 8

k = 4: works, search right (left = 5)
k = 5: works, search right (left = 6)
k = 6: works, search right (left = 7)
...

Returns largest k, not minimum ❌

Should return 4, but returns 11!
```

**Fix**: Search left when valid
```java
if (hours <= h) {
    result = mid;
    right = mid - 1;  // Try smaller
}
```

### ❌ **MISTAKE 4: Wrong Upper Bound**
```java
// WRONG - upper bound too small
int right = piles.length;
```

**Why wrong**: Speed needs to match pile size, not count!

**Dry run failure for piles=[100], h=1:**
```
right = 1 (piles.length)
But need k = 100!

Binary search [1, 1]:
  mid = 1
  hours = 100 > 1 ❌
  left = 2
  
Loop ends immediately
Returns 1 ❌

But k=1 needs 100 hours, not 1!
Wrong answer!
```

**Fix**: Use max(piles)
```java
int right = 0;
for (int pile : piles) {
    right = Math.max(right, pile);
}
```

### ❌ **MISTAKE 5: Not Saving Best Answer**
```java
// WRONG - doesn't track result
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (canFinish(piles, mid, h)) {
        right = mid - 1;  // Forgot: result = mid
    } else {
        left = mid + 1;
    }
}
return left;  // May be wrong!
```

**Why wrong**: Loses valid answer during search!

**Dry run failure:**
```
Final state: left = 5, right = 4
Last valid k found was 4

But code returns left = 5
Need to verify if 5 works? Risky!

Better to save known valid answer
Return saved result ✓
```

**Fix**: Save result
```java
int result = right;  // Or max(piles)
if (canFinish(piles, mid, h)) {
    result = mid;  // Save it!
    right = mid - 1;
}
return result;
```

### ❌ **MISTAKE 6: Using Double Division**
```java
// WRONG - floating point issues
hours += Math.ceil((double) pile / mid);
```

**Why wrong**: Slower and potential precision issues!

**Better**: Integer ceiling formula
```java
hours += (pile + mid - 1) / mid;
// Faster, no float conversion, exact
```

### ❌ **MISTAKE 7: Starting from 0**
```java
// WRONG - k must be at least 1
int left = 0;
```

**Why wrong**: Division by zero!

**Dry run failure:**
```
mid = 0
hours += pile / 0  // Division by zero! ❌
Program crashes!
```

**Fix**: Start from 1
```java
int left = 1;  // Minimum eating speed
```

---

## Complexity Analysis

### Time Complexity: **O(n log m)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **Find max(piles)** | 1 | O(n) | O(n) |
| **Binary search iterations** | O(log m) | - | - |
| **Calculate hours per iteration** | O(log m) | O(n) | O(n log m) |
| **Compare hours with h** | O(log m) | O(1) | O(log m) |
| **Total** | - | - | **O(n log m)** |

where n = piles.length, m = max(piles)

**Time analysis**:
```
Binary search on range [1, max(piles)]:
  Range size: m = max(piles)
  Iterations: log₂(m)
  
Each iteration:
  Calculate hours for n piles: O(n)
  
Total: O(n log m)

Examples:
  n = 1,000, m = 10⁹
  log₂(10⁹) ≈ 30
  Total: 30,000 operations ✓
  
  Linear search: 10⁹ × 1,000 = 10¹² operations ❌
  
Binary search is essential!
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| left, right | O(1) | Binary search bounds |
| mid | O(1) | Current speed |
| hours | O(1) | Long accumulator |
| result | O(1) | Best answer |
| **Total** | **O(1)** | Constant space |

**Space analysis**:
```
Only fixed number of variables
No arrays, no recursion
Space: O(1) ✓

Very space-efficient!
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `piles = [3, 6, 7, 11], h = 8`

**Expected Output:** `4`

---

**Initial State:**
```
Piles: [3, 6, 7, 11]
Hours available: 8
max(piles) = 11

Search range: [1, 11]
Find minimum k where Koko can finish in ≤ 8 hours
```

---

**Iteration 1:**
```
left = 1, right = 11
mid = (1 + 11) / 2 = 6

Calculate hours with k = 6:
  Pile 3: ceil(3/6) = ceil(0.5) = 1 hour
  Pile 6: ceil(6/6) = ceil(1.0) = 1 hour
  Pile 7: ceil(7/6) = ceil(1.17) = 2 hours
  Pile 11: ceil(11/6) = ceil(1.83) = 2 hours
  
  Total: 1 + 1 + 2 + 2 = 6 hours

Compare: 6 ≤ 8? Yes ✓

Action: k = 6 works, try smaller
  result = 6
  right = 5
  
New range: [1, 5]
```

---

**Iteration 2:**
```
left = 1, right = 5
mid = (1 + 5) / 2 = 3

Calculate hours with k = 3:
  Pile 3: ceil(3/3) = 1 hour
  Pile 6: ceil(6/3) = 2 hours
  Pile 7: ceil(7/3) = ceil(2.33) = 3 hours
  Pile 11: ceil(11/3) = ceil(3.67) = 4 hours
  
  Total: 1 + 2 + 3 + 4 = 10 hours

Compare: 10 ≤ 8? No ❌

Action: k = 3 too slow, need faster
  left = 4
  
New range: [4, 5]
```

---

**Iteration 3:**
```
left = 4, right = 5
mid = (4 + 5) / 2 = 4

Calculate hours with k = 4:
  Pile 3: ceil(3/4) = ceil(0.75) = 1 hour
  Pile 6: ceil(6/4) = ceil(1.5) = 2 hours
  Pile 7: ceil(7/4) = ceil(1.75) = 2 hours
  Pile 11: ceil(11/4) = ceil(2.75) = 3 hours
  
  Total: 1 + 2 + 2 + 3 = 8 hours

Compare: 8 ≤ 8? Yes ✓

Action: k = 4 works, try smaller
  result = 4
  right = 3
  
New range: [4, 3]
```

---

**Loop Ends:**
```
left = 4, right = 3
Condition: 4 <= 3? No

Exit loop

Return: result = 4 ✓
```

---

**Verification:**
```
With k = 4:
  Total hours = 8 ≤ 8 ✓

With k = 3:
  Total hours = 10 > 8 ❌
  
k = 4 is minimum valid speed!
```

---

### Speed vs Hours Visualization

```
piles = [3, 6, 7, 11], h = 8

k = 1:  3+6+7+11 = 27 hours ❌
k = 2:  2+3+4+6 = 15 hours ❌
k = 3:  1+2+3+4 = 10 hours ❌
k = 4:  1+2+2+3 = 8 hours ✓ ← minimum!
k = 5:  1+2+2+3 = 8 hours ✓
k = 6:  1+1+2+2 = 6 hours ✓
k = 7:  1+1+1+2 = 5 hours ✓
...
k = 11: 1+1+1+1 = 4 hours ✓

Pattern: [❌ ❌ ❌ ✓ ✓ ✓ ...]
               ↑
         First valid (answer)

Binary search finds boundary efficiently!
```

---

### Binary Search Tree Visualization

```
piles = [3, 6, 7, 11], h = 8

                    mid=6 (6h ✓)
                   /            \
                  /              \
           mid=3 (10h ❌)      [7-11]
          /          \
         /            \
    [1-2]           mid=4 (8h ✓)
                   /          \
                  /            \
              [4-3]          mid=5 (8h ✓)
              stop           /        \
                            /          \
                        [5-4]      [6-5]
                         stop       stop

Found minimum: 4
```

---

### Hour Calculation Example

```
pile = 7 bananas, k = 3 bananas/hour

Process:
  Hour 1: eat 3, remaining 4
  Hour 2: eat 3, remaining 1
  Hour 3: eat 1, remaining 0
  
Total: 3 hours

Formula: ceil(7 / 3) = ceil(2.33) = 3 ✓

Integer formula: (7 + 3 - 1) / 3 = 9 / 3 = 3 ✓
```

---

### Search Space Reduction

```
piles = [3, 6, 7, 11], h = 8

Start:     [1 ........... 11] (11 speeds)
           
Iter 1:    [1 ..... 5] (k=6 works, try smaller)
           
Iter 2:    [4 .. 5] (k=3 too slow, go faster)
           
Iter 3:    found k=4!

Space: 11 → 5 → 2 → found
3 iterations to find answer in range of 11!
```

---

## Comparison of Approaches

| Approach | Time | Space | Iterations | Code Lines | Clarity | Recommended |
|----------|------|-------|------------|------------|---------|-------------|
| **Binary Search** | **O(n log m)** | **O(1)** | **~log₂ m** | **~25** | **Good ✅** | **Yes ✅** |
| Binary with Helper | O(n log m) | O(1) | ~log₂ m | ~30 | Excellent | Cleaner |
| Linear Speed Search | O(n × m) | O(1) | up to m | ~20 | Simple | Too slow ❌ |

**Winner**: **Binary Search** — only approach that handles large inputs efficiently!

---

## Key Takeaways

1. **Binary search on eating speed** — not on array, on answer space [1, max(piles)]
2. **Monotonic property** — if speed k works, all k' > k also work
3. **Ceiling division formula** — (pile + k - 1) / k for integer math
4. **Use long for hours** — accumulation can exceed int max
5. **Search for minimum** — when valid, save answer and search left
6. **Upper bound is max(piles)** — eating largest pile in 1 hour always works
7. **Validate in O(n)** — sum of ceiling divisions
8. **O(n log m) time** — essential for large m (up to 10⁹)
9. **O(1) space** — only need a few variables
10. **Pattern: ❌ ❌ ✓ ✓** — binary search finds first ✓

---

## Interview Tips

**What to say in an interview:**

> "This is a binary search on answer space problem. I'm not searching for a value in an array; instead, I'm searching for the minimum eating speed k that allows Koko to finish all bananas within h hours. The key insight is that the relationship between speed and hours is monotonic—slower speeds require more hours, faster speeds require fewer hours. This creates a pattern where speeds below some threshold don't work, and speeds at or above the threshold do work. I'll binary search on the range [1, max(piles)] to find the minimum valid speed. For each candidate speed, I calculate the total hours needed by summing ceiling(pile[i] / k) for all piles. The ceiling is important because if a pile isn't evenly divisible, Koko needs an extra hour for the remainder. I'll use the integer formula (pile + k - 1) / k to avoid floating point. Also, I'll use long for hours accumulation to prevent overflow. The time complexity is O(n log m) where n is the number of piles and m is the maximum pile size."

**Key points to mention:**
1. **Binary search on answer space** — searching for minimum speed k
2. **Range [1, max(piles)]** — 1 is slowest, max(piles) lets you eat any pile in 1 hour
3. **Monotonic property** — if k works, all larger k work too
4. **Validation function** — sum(ceil(pile/k)) ≤ h
5. **Ceiling division** — (pile + k - 1) / k for integer math
6. **Use long for hours** — prevent overflow
7. **Search left when valid** — find minimum
8. **O(n log m) time** — log m iterations, each O(n) validation

**Common Follow-ups:**
- "Why not linear search?" → Would be O(n × m), too slow for m = 10⁹
- "How do you handle ceiling?" → Use (pile + k - 1) / k integer formula
- "What if h < piles.length?" → Impossible by constraints (h >= n guaranteed)
- "Can you optimize further?" → Already optimal, O(n log m) is required lower bound
- "Why long for hours?" → Sum can reach 10¹², exceeds int max (2 × 10⁹)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Koko Eating Bananas** | Medium | **Binary Search on Answer (Speed)** | **This problem** |
| Capacity To Ship Packages Within D Days | Medium | Binary Search on Answer (Capacity) | Find minimum capacity not speed |
| Split Array Largest Sum | Hard | Binary Search on Answer (Max Sum) | Minimize maximum subarray sum |
| Minimize Max Distance to Gas Station | Hard | Binary Search on Answer (Distance) | Add stations to minimize distance |
| Magnetic Force Between Two Balls | Medium | Binary Search on Answer (Force) | Maximize minimum force |
| Aggressive Cows | Hard | Binary Search on Answer (Distance) | Maximize minimum distance |
| Sqrt(x) | Easy | Binary Search on Answer | Find integer square root |
| Guess Number Higher Or Lower | Easy | Binary Search with API | Interactive search |

**Pattern Progression**:
1. **Standard binary search** — Find element in sorted array
2. **Binary search on answer space** — Find minimum/maximum value satisfying constraint
3. **Koko Eating Bananas** (this problem) — Minimize speed with time constraint
4. **Ship Packages** — Minimize capacity with day constraint
5. **Split Array** — Minimize maximum with split constraint

---

## Final Pattern Label

✅ **Binary Search on Answer Space (Minimize Speed with Time Constraint)**

**Remember:** This is **binary search on eating speed** k, not on the piles array. We're searching for the **minimum speed** that allows finishing within h hours. The key insight is the **monotonic relationship**: slower speeds need more hours, faster speeds need fewer hours, creating a **threshold pattern** [❌ ❌ ❌ ✓ ✓ ✓]. Binary search finds the **first ✓** (minimum valid k). For each candidate speed, **validate** by calculating total hours: `sum(ceil(pile[i] / k))`, checking if it's ≤ h. Use **ceiling formula** `(pile + k - 1) / k` for integer math. **Critical**: use `long` for hours accumulation to avoid overflow (can reach 10¹²). Search range is **[1, max(piles)]** because k=1 is slowest possible and k=max(piles) lets you eat any pile in 1 hour. When speed works, **save it and search left** (smaller speeds) to find minimum. Time complexity is **O(n log m)** where n is pile count and m is max pile—essential for large inputs (m up to 10⁹). Pattern: binary search on answer space with validation function!
