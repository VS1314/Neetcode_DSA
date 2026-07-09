# Guess Number Higher Or Lower

## Problem Description

**Difficulty**: Easy

We are playing the **Guess Game**. The game is as follows:

I pick a number from `1` to `n`. You have to guess which number I picked.

Every time you guess wrong, I will tell you whether the number I picked is **higher** or **lower** than your guess.

You call a pre-defined API `int guess(int num)`, which returns three possible results:

- `0`: Your guess is equal to the number I picked (i.e., `num == pick`).
- `-1`: Your guess is **higher** than the number I picked (i.e., `num > pick`).
- `1`: Your guess is **lower** than the number I picked (i.e., `num < pick`).

Return the number that I picked.

## Examples

### Example 1:
```
Input: n = 5, pick = 3
Output: 3

Explanation:
  We need to find 3 in range [1, 5]
  
  guess(3):
    Returns 0 (correct!)
    
  Return: 3
```

### Example 2:
```
Input: n = 15, pick = 10
Output: 10

Explanation:
  We need to find 10 in range [1, 15]
  
  Step 1: guess(8)
    Returns 1 (too low, pick > 8)
    Search [9, 15]
    
  Step 2: guess(12)
    Returns -1 (too high, pick < 12)
    Search [9, 11]
    
  Step 3: guess(10)
    Returns 0 (correct!)
    
  Return: 10
```

### Example 3:
```
Input: n = 1, pick = 1
Output: 1

Explanation:
  Only one number, must be the answer
  guess(1) returns 0
  Return: 1
```

### Example 4:
```
Input: n = 10, pick = 6
Output: 6

Explanation:
  Binary search finds 6:
    guess(5) → 1 (too low)
    guess(8) → -1 (too high)
    guess(6) → 0 (correct!)
```

### Example 5:
```
Input: n = 100, pick = 1
Output: 1

Explanation:
  Target at beginning of range
  Binary search converges to 1
```

### Example 6:
```
Input: n = 100, pick = 100
Output: 100

Explanation:
  Target at end of range
  Binary search converges to 100
```

### Example 7:
```
Input: n = 2, pick = 1
Output: 1

Explanation:
  Two elements:
    guess(1) → 0 (correct!)
  Or:
    guess(2) → -1 (too high)
    guess(1) → 0 (correct!)
```

### Example 8:
```
Input: n = 2, pick = 2
Output: 2

Explanation:
  guess(1) → 1 (too low)
  guess(2) → 0 (correct!)
```

### Example 9:
```
Input: n = 2147483647, pick = 1234567890
Output: 1234567890

Explanation:
  Very large range, binary search still O(log n)
  Must use safe mid calculation to avoid overflow
```

### Example 10:
```
Input: n = 1000, pick = 500
Output: 500

Explanation:
  Target in middle:
    guess(500) → 0 (correct in first try!)
```

## Constraints
- 1 <= pick <= n <= 2³¹ - 1
- `pick` is guaranteed to be in range [1, n]
- The `guess(int num)` API is pre-defined

**Recommended Complexity**: O(log n) time and O(1) space, where n is the upper bound

---

## Pattern Recognition

**Primary Pattern**: **Binary Search on Answer Space**

**Why This Pattern?**
- Search space is **sorted** (numbers 1 to n in order)
- Have a **decision function** (guess API tells us direction)
- Need to find **exact value** efficiently
- Large range possible (up to 2³¹ - 1) requires O(log n)

**Key Insight**: API Guides Binary Search
```
Traditional binary search:
  Compare nums[mid] with target
  Adjust left/right based on comparison
  
This problem:
  Call guess(mid)
  Adjust left/right based on API response
  
Same algorithm, different comparison method!

API return values:
  guess(mid) == 0  → Found! (nums[mid] == target)
  guess(mid) == -1 → Too high, go left (nums[mid] > target)
  guess(mid) == 1  → Too low, go right (nums[mid] < target)

Perfect mapping to binary search!
```

**The Binary Search Strategy**:
```
Range: [1, n]
Target: pick (unknown number in range)

Process:
  1. Start: left = 1, right = n
  2. Calculate mid = left + (right - left) / 2
  3. Call guess(mid):
       0 → found, return mid
      -1 → mid > pick, search [left, mid-1]
       1 → mid < pick, search [mid+1, right]
  4. Repeat until found

Why this works:
  Each guess eliminates half the search space
  Guaranteed to find answer in O(log n) guesses
```

**Example Showing Binary Search**:
```
n = 15, pick = 10

Initial: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
         ↑                                               ↑
        left                                          right

Guess 1: mid = 8
  guess(8) = 1 (too low, pick > 8)
  Search right half
  
Range: [9, 10, 11, 12, 13, 14, 15]
        ↑                       ↑
       left                  right

Guess 2: mid = 12
  guess(12) = -1 (too high, pick < 12)
  Search left half
  
Range: [9, 10, 11]
        ↑      ↑
       left right

Guess 3: mid = 10
  guess(10) = 0 (found!)
  
Return: 10 ✓

Only 3 guesses for range of 15!
```

**Why Binary Search is Optimal**:
```
Linear search (1, 2, 3, ...):
  Worst case: n guesses
  For n = 2³¹-1: billions of guesses ❌

Binary search:
  Worst case: log₂(n) guesses
  For n = 2³¹-1: only 31 guesses ✓

Exponentially faster!

Example:
  n = 1,000,000
  Linear: up to 1,000,000 guesses
  Binary: up to 20 guesses
  
50,000x improvement!
```

**Critical Overflow Issue**:
```
WRONG mid calculation:
  mid = (left + right) / 2
  
Problem: left + right can overflow!

Example:
  left = 1,500,000,000
  right = 2,000,000,000
  left + right = 3,500,000,000
  Max int = 2,147,483,647
  Overflow! ❌

CORRECT calculation:
  mid = left + (right - left) / 2
  
Why safe?
  right - left ≤ n < 2³¹
  No overflow possible ✓
  
Always use safe version!
```

**Related Patterns**:
1. **Binary Search** — Core algorithm
2. **Search Space Reduction** — Eliminate half each iteration
3. **Interactive Problem** — Use API/oracle for decisions
4. **Overflow-Safe Arithmetic** — Handle large numbers

---

## Algorithm & Approach

### Core Insight

**Why Binary Search is Natural Here:**
```
The guess game gives perfect feedback:
  Too high → go lower
  Too low → go higher
  Correct → done
  
This is exactly what binary search needs!

The "array" is conceptual: [1, 2, 3, ..., n]
The "target" is pick
The "comparison" is guess() API

Same algorithm, different interface!
```

**The Optimal Strategy**:
```
Key observations:
  1. Search space is ordered (1 to n)
  2. API provides ternary comparison (=, <, >)
  3. Each guess eliminates half the space
  4. Guaranteed to converge in log n steps
  
Operations per guess:
  Calculate mid: O(1)
  Call guess(): O(1) (assumed)
  Update pointers: O(1)
  
Total: O(log n) guesses × O(1) per guess = O(log n)
```

### Step-by-Step Algorithm

---

#### **Approach 1: Binary Search with API - OPTIMAL**

**Core Idea**:
- Standard binary search
- Use guess() API instead of array comparison
- Map API results to search direction

**Algorithm**
```
guessNumber(n):
    left = 1
    right = n
    
    while left <= right:
        mid = left + (right - left) / 2  // Overflow safe!
        
        result = guess(mid)
        
        if result == 0:
            return mid  // Found!
        else if result == -1:
            right = mid - 1  // Guess too high, go left
        else:  // result == 1
            left = mid + 1  // Guess too low, go right
    
    return -1  // Never reached (pick is guaranteed in range)
```

**Code Implementation**
```java
/** 
 * Forward declaration of guess API.
 * @param  num   your guess
 * @return 	     -1 if num is higher than the picked number
 *			      1 if num is lower than the picked number
 *               otherwise return 0
 * int guess(int num);
 */

public class Solution extends GuessGame {
    public int guessNumber(int n) {
        int left = 1;
        int right = n;
        
        while (left <= right) {
            // Overflow-safe mid calculation
            int mid = left + (right - left) / 2;
            
            int result = guess(mid);
            
            if (result == 0) {
                return mid;  // Found the number
            } else if (result == -1) {
                right = mid - 1;  // Guess too high
            } else {  // result == 1
                left = mid + 1;  // Guess too low
            }
        }
        
        // Never reached since pick is guaranteed in [1, n]
        return -1;
    }
}
```

**Example Walkthrough**

Input: `n = 10, pick = 6`

| Iteration | left | right | mid | guess(mid) | Interpretation | Action |
|-----------|------|-------|-----|------------|----------------|--------|
| 1 | 1 | 10 | 5 | 1 | Too low (6 > 5) | left = 6 |
| 2 | 6 | 10 | 8 | -1 | Too high (6 < 8) | right = 7 |
| 3 | 6 | 7 | 6 | 0 | Found! | Return 6 |

Return: **6** ✓

**Complexity Analysis**
- **Time**: O(log n) — Halve search space each iteration
- **Space**: O(1) — Only constant variables

---

#### **Approach 2: Ternary Search - ALTERNATIVE**

**Core Idea**: Instead of dividing into 2 parts, divide into 3 parts.

**Code Implementation**
```java
public class Solution extends GuessGame {
    public int guessNumber(int n) {
        int left = 1;
        int right = n;
        
        while (left <= right) {
            int mid1 = left + (right - left) / 3;
            int mid2 = right - (right - left) / 3;
            
            int res1 = guess(mid1);
            if (res1 == 0) return mid1;
            
            int res2 = guess(mid2);
            if (res2 == 0) return mid2;
            
            if (res1 < 0) {
                right = mid1 - 1;
            } else if (res2 > 0) {
                left = mid2 + 1;
            } else {
                left = mid1 + 1;
                right = mid2 - 1;
            }
        }
        
        return -1;
    }
}
```

**Key Difference**: 
- Makes 2 guesses per iteration
- Eliminates 2/3 of space (but costs 2 guesses)
- Actually worse than binary search!

**Complexity Analysis**
- **Time**: O(log₃ n) iterations but 2 API calls per iteration
  - log₃ n ≈ 0.63 log₂ n iterations
  - But 2 × 0.63 ≈ 1.26 × binary search API calls
  - Worse than binary search! ❌
- **Space**: O(1)

---

#### **Approach 3: Recursive Binary Search - ELEGANT**

**Core Idea**: Same binary search logic but recursive implementation.

**Code Implementation**
```java
public class Solution extends GuessGame {
    public int guessNumber(int n) {
        return binarySearch(1, n);
    }
    
    private int binarySearch(int left, int right) {
        if (left > right) {
            return -1;  // Not found (never happens)
        }
        
        int mid = left + (right - left) / 2;
        int result = guess(mid);
        
        if (result == 0) {
            return mid;
        } else if (result == -1) {
            return binarySearch(left, mid - 1);  // Search left
        } else {
            return binarySearch(mid + 1, right);  // Search right
        }
    }
}
```

**Key Difference**: 
- Recursive instead of iterative
- More elegant code
- Uses call stack

**Complexity Analysis**
- **Time**: O(log n) — Same as iterative
- **Space**: O(log n) — Recursion call stack (worse than iterative!)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | API Calls | Code Complexity | Recommended |
|----------|------|-------|-----------|-----------------|-------------|
| **Binary Search (Iterative)** | **O(log n)** | **O(1)** | **O(log n)** | **Simple ✅** | **Yes ✅** |
| Ternary Search | O(log n) | O(1) | ~1.26 × binary | Complex | No ❌ |
| Recursive Binary Search | O(log n) | O(log n) | O(log n) | Medium | Alternative |
| Linear Search | O(n) | O(1) | O(n) | Very Simple | Too slow ❌ |

**Winner**: **Iterative Binary Search** — optimal API calls, minimal space!

### Why Binary Search is Perfect

```
Problem characteristics:
  ✓ Ordered search space (1 to n)
  ✓ Ternary decision function (=, <, >)
  ✓ Need exact answer
  ✓ Large range possible (2³¹ - 1)
  
Binary search properties:
  ✓ Works on ordered space
  ✓ Uses ternary comparison
  ✓ Finds exact match
  ✓ O(log n) time
  
Perfect match!

Each guess:
  Eliminates 50% of remaining space
  Guaranteed progress
  No wasted information
  
Optimal strategy!
```

### Why Not Ternary Search

```
Ternary search seems better:
  Eliminate 2/3 instead of 1/2
  Fewer iterations!
  
But:
  Needs 2 API calls per iteration
  Binary: log₂ n calls total
  Ternary: 2 × log₃ n calls total
  
Calculation:
  2 × log₃ n = 2 × (log₂ n / log₂ 3)
             = 2 × (log₂ n / 1.585)
             ≈ 1.26 × log₂ n
  
Ternary is 26% worse! ❌

Binary search minimizes API calls!
```

### Why Iterative Over Recursive

```
Both have same time complexity
But:

Iterative:
  Space: O(1) ✓
  No stack overhead ✓
  Tail call optimization not needed ✓
  
Recursive:
  Space: O(log n) ❌
  Stack overhead ❌
  Looks elegant but costs space ❌

For interviews: iterative preferred!
```

### Why Overflow-Safe Mid Matters

```
Standard formula:
  mid = (left + right) / 2
  
Seems fine, but:

When n = 2³¹ - 1:
  left = 1,500,000,000
  right = 2,100,000,000
  left + right = 3,600,000,000
  
But max int = 2,147,483,647
Overflow! Negative mid! ❌

Safe formula:
  mid = left + (right - left) / 2
  
Why safe?
  right - left < 2³¹ (fits in int)
  Division by 2 makes smaller
  Add to left → still in range ✓

Always use safe version!
```

### Why This is Just Standard Binary Search

```
People often overthink this problem!

It's literally standard binary search:

Traditional:
  if nums[mid] == target: return mid
  if nums[mid] < target: left = mid + 1
  else: right = mid - 1

This problem:
  if guess(mid) == 0: return mid
  if guess(mid) == 1: left = mid + 1
  else: right = mid - 1

Same logic!
Just different comparison method.

Not a trick question, just binary search!
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Element (n = 1)**
```java
Input: n = 1, pick = 1
Binary search: mid = 1, guess(1) = 0
Output: 1
```

### 2. **Two Elements - Pick First**
```java
Input: n = 2, pick = 1
Iteration 1: mid = 1, guess(1) = 0
Output: 1
```

### 3. **Two Elements - Pick Second**
```java
Input: n = 2, pick = 2
Iteration 1: mid = 1, guess(1) = 1 (too low)
  left = 2
Iteration 2: mid = 2, guess(2) = 0
Output: 2
```

### 4. **Pick at Beginning (pick = 1)**
```java
Input: n = 100, pick = 1
Binary search converges left
~7 guesses to reach 1
Output: 1
```

### 5. **Pick at End (pick = n)**
```java
Input: n = 100, pick = 100
Binary search converges right
~7 guesses to reach 100
Output: 100
```

### 6. **Pick in Middle**
```java
Input: n = 100, pick = 50
First guess: mid = 50
guess(50) = 0 (lucky!)
Output: 50
```

### 7. **Maximum n (2³¹ - 1)**
```java
Input: n = 2147483647, pick = 1234567890
Must use overflow-safe mid!
mid = left + (right - left) / 2 ✓
Not: mid = (left + right) / 2 ❌
~31 guesses maximum
```

### 8. **Consecutive Guesses**
```java
Input: n = 5, pick = 3
guess(3) on first try
Output: 3
Best case: O(1) time!
```

### 9. **Off-by-One at Boundaries**
```java
Input: n = 10, pick = 5
mid = 5, guess(5) = 0
Must return immediately!
Don't accidentally narrow range
```

### 10. **Large Range, Pick Near Boundary**
```java
Input: n = 1000000, pick = 999999
Binary search handles efficiently
~20 guesses to converge
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Wrong API Interpretation**
```java
// WRONG - reversed the conditions
if (guess(mid) == 1) {
    right = mid - 1;  // Should be left = mid + 1
} else if (guess(mid) == -1) {
    left = mid + 1;  // Should be right = mid - 1
}
```

**Why wrong**: guess() returns 1 when our guess is TOO LOW!

**Dry run failure for n=10, pick=6:**
```
mid = 5, guess(5) = 1 (too low, need higher)
But code does: right = 4
Searches [1, 4] ❌
6 is not in this range!
Never finds answer!
```

**Fix**: Understand return values
```java
// guess(mid) == 1 means mid < pick
if (guess(mid) == 1) {
    left = mid + 1;  // Search higher
}
```

### ❌ **MISTAKE 2: Overflow in Mid Calculation**
```java
// WRONG - can overflow
int mid = (left + right) / 2;
```

**Why wrong**: left + right can exceed Integer.MAX_VALUE!

**Dry run failure for n=2147483647, pick=2000000000:**
```
left = 1500000000
right = 2147483647
left + right = 3647483647
Max int = 2147483647
Overflow! Result is negative ❌

mid becomes negative
Array access fails or wrong range
```

**Fix**: Overflow-safe calculation
```java
int mid = left + (right - left) / 2;
```

### ❌ **MISTAKE 3: Wrong Loop Condition**
```java
// WRONG - uses < instead of <=
while (left < right) {
    // ...
}
```

**Why wrong**: Misses case when left == right!

**Dry run failure for n=1, pick=1:**
```
left = 1, right = 1
Condition: 1 < 1? false
Loop doesn't execute ❌
Return -1
But answer is 1!
```

**Fix**: Use <=
```java
while (left <= right) {
    // ...
}
```

### ❌ **MISTAKE 4: Not Excluding mid**
```java
// WRONG - doesn't exclude mid
if (guess(mid) == 1) {
    left = mid;  // Should be mid + 1
}
```

**Why wrong**: Infinite loop when left == right!

**Dry run failure:**
```
left = 5, right = 5
mid = 5, guess(5) = 1
left = 5 (not updated!)
Next iteration: same state
Infinite loop ❌
```

**Fix**: Always exclude mid
```java
left = mid + 1;
right = mid - 1;
```

### ❌ **MISTAKE 5: Wrong Initial Range**
```java
// WRONG - starts from 0
int left = 0;  // Should be 1
int right = n;
```

**Why wrong**: Pick is in range [1, n], not [0, n]!

**Dry run failure for n=5, pick=1:**
```
left = 0, but valid range starts at 1
mid = 2, guess(2) = -1
right = 1
mid = 0, guess(0) likely invalid or wrong ❌
```

**Fix**: Correct range
```java
int left = 1;  // Range is [1, n]
int right = n;
```

### ❌ **MISTAKE 6: Returning wrong when not found**
```java
// WRONG - thinking it's possible to not find
if (left > right) {
    return -1;
}
```

**Actually**: This is fine! But could be confusing.

**Why it's okay**: The problem **guarantees** pick is in [1, n], so we always find it. The return -1 is unreachable but doesn't hurt.

**Better**: Add comment
```java
return -1;  // Never reached - pick guaranteed in range
```

### ❌ **MISTAKE 7: Not Handling Result Immediately**
```java
// WRONG - stores result but doesn't check all cases
int result = guess(mid);
if (result == 0) return mid;
if (result == 1) left = mid + 1;
// Forgot: else { right = mid - 1; }
```

**Why wrong**: Doesn't handle result == -1!

**Dry run failure:**
```
guess(5) = -1 (too high)
But no code to handle it
left and right don't update
Infinite loop ❌
```

**Fix**: Handle all three cases
```java
if (result == 0) return mid;
else if (result == 1) left = mid + 1;
else right = mid - 1;  // Must handle -1!
```

---

## Complexity Analysis

### Time Complexity: **O(log n)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **While loop iterations** | O(log n) | O(1) | O(log n) |
| **Calculate mid** | O(log n) | O(1) | O(log n) |
| **Call guess(mid)** | O(log n) | O(1) | O(log n) |
| **Update pointers** | O(log n) | O(1) | O(log n) |
| **Total** | - | - | **O(log n)** |

**Time analysis**:
```
Binary search halves space each iteration
Search space: n
After iteration k: n / 2^k

Converges when: n / 2^k = 1
Solving: k = log₂(n)

Maximum iterations: ⌈log₂(n)⌉

Examples:
  n = 10: log₂(10) ≈ 3.32 → 4 guesses max
  n = 100: log₂(100) ≈ 6.64 → 7 guesses max
  n = 1,000,000: log₂(1,000,000) ≈ 19.93 → 20 guesses max
  n = 2³¹-1: log₂(2³¹-1) ≈ 31 → 31 guesses max

Extremely efficient even for huge ranges!
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| left | O(1) | Single integer |
| right | O(1) | Single integer |
| mid | O(1) | Single integer |
| result | O(1) | Single integer |
| **Total** | **O(1)** | Constant space |

**Space analysis**:
```
Only four integer variables
No arrays, no recursion stack (iterative)
Space: O(1) ✓

Note: Recursive version uses O(log n) space
      for call stack (less efficient)
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `n = 15, pick = 10`

**Expected Output:** `10`

---

**Initial State:**
```
Range: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
        ↑                                              ↑
       left                                         right

left = 1
right = 15
target = 10 (hidden)
```

---

**Iteration 1:**
```
Calculate mid:
  mid = 1 + (15-1)/2 = 1 + 7 = 8
  
Range: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
        ↑                    ↑                          ↑
       left                mid                       right

Call guess(8):
  Is 8 == 10? No
  Is 8 < 10? Yes
  Return: 1 (too low, need to go higher)
  
Action: Search right half
  left = mid + 1 = 9
  
New range: [9, 10, 11, 12, 13, 14, 15]
  left=9, right=15
```

---

**Iteration 2:**
```
Calculate mid:
  mid = 9 + (15-9)/2 = 9 + 3 = 12
  
Range: [9, 10, 11, 12, 13, 14, 15]
        ↑        ↑              ↑
       left     mid          right

Call guess(12):
  Is 12 == 10? No
  Is 12 > 10? Yes
  Return: -1 (too high, need to go lower)
  
Action: Search left half
  right = mid - 1 = 11
  
New range: [9, 10, 11]
  left=9, right=11
```

---

**Iteration 3:**
```
Calculate mid:
  mid = 9 + (11-9)/2 = 9 + 1 = 10
  
Range: [9, 10, 11]
        ↑  ↑   ↑
      left mid right

Call guess(10):
  Is 10 == 10? Yes!
  Return: 0 (found!)
  
Action: Return mid
  
Return: 10 ✓
```

---

**Summary:**
```
Total guesses: 3
Guesses made: 8, 12, 10
Range reduction: 15 → 7 → 3 → found

Approximately halves space each time!
```

---

### Another Example: Edge Case at Boundary

**Input:** `n = 100, pick = 1`

```
Iteration 1:
  mid = 50
  guess(50) = -1 (too high)
  Search [1, 49]
  
Iteration 2:
  mid = 25
  guess(25) = -1 (too high)
  Search [1, 24]
  
Iteration 3:
  mid = 12
  guess(12) = -1 (too high)
  Search [1, 11]
  
Iteration 4:
  mid = 6
  guess(6) = -1 (too high)
  Search [1, 5]
  
Iteration 5:
  mid = 3
  guess(3) = -1 (too high)
  Search [1, 2]
  
Iteration 6:
  mid = 1
  guess(1) = 0 (found!)
  
Return: 1
Total: 6 guesses for range of 100 ✓
```

---

### Pointer Movement Visualization

```
n = 10, pick = 6

Start:
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
 ↑                          ↑
 L                          R

After iter 1: guess(5)=1 (too low)
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
                ↑          ↑
                L          R

After iter 2: guess(8)=-1 (too high)
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
                ↑  ↑
                L  R

After iter 3: guess(6)=0 (found!)
[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
                ↑
             Found!
```

---

### Search Space Reduction

```
n = 16, pick = 11

Iteration 1: [1-16] → guess(8) = 1 → [9-16] (size: 8)
Iteration 2: [9-16] → guess(12) = -1 → [9-11] (size: 3)
Iteration 3: [9-11] → guess(10) = 1 → [11-11] (size: 1)
Iteration 4: [11-11] → guess(11) = 0 → Found!

Space: 16 → 8 → 3 → 1
Perfect binary reduction!
```

---

## Comparison of Approaches

| Approach | Time | Space | API Calls | Code Lines | Clarity | Recommended |
|----------|------|-------|-----------|------------|---------|-------------|
| **Binary Search (Iterative)** | **O(log n)** | **O(1)** | **O(log n)** | **~20** | **Excellent ✅** | **Yes ✅** |
| Binary Search (Recursive) | O(log n) | O(log n) | O(log n) | ~15 | Good | Alternative |
| Ternary Search | O(log n) | O(1) | 1.26×O(log n) | ~25 | Complex | No ❌ |
| Linear Search | O(n) | O(1) | O(n) | ~10 | Simple | Too slow ❌ |

**Winner**: **Iterative Binary Search** — minimal space, optimal API calls!

---

## Key Takeaways

1. **Standard binary search** — this is just regular binary search with API
2. **Three return values** — 0 (found), -1 (too high), 1 (too low)
3. **guess(mid)==1 means search right** — our guess is too low, target is higher
4. **guess(mid)==-1 means search left** — our guess is too high, target is lower
5. **Overflow-safe mid** — use left + (right-left)/2, not (left+right)/2
6. **Range is [1, n]** — not [0, n-1], don't forget starting at 1
7. **Loop condition is <=** — must handle single element case
8. **Always exclude mid** — left=mid+1, right=mid-1
9. **O(log n) guesses** — ~31 guesses max even for 2³¹-1
10. **Iterative preferred** — O(1) space vs O(log n) for recursive

---

## Interview Tips

**What to say in an interview:**

> "This is a classic binary search problem disguised with an API interface. The guess game gives me ternary feedback similar to comparing array elements in standard binary search. I'll use binary search on the range [1, n]. The API returns 0 when I find the target, -1 when my guess is too high, and 1 when my guess is too low. The key is correctly mapping these return values: guess returning 1 means my guess is lower than the target, so I should search the right half. One critical implementation detail is using the overflow-safe mid calculation: left + (right - left) / 2, because when n can be as large as 2^31 - 1, the standard (left + right) / 2 can overflow. The time complexity is O(log n) with at most 31 guesses for the maximum input, and space complexity is O(1) for the iterative approach."

**Key points to mention:**
1. **Binary search pattern** — recognize it's standard binary search
2. **API mapping** — 0=found, -1=too high, 1=too low
3. **Overflow safety** — must use left+(right-left)/2
4. **Correct range** — [1, n] not [0, n-1]
5. **Ternary feedback** — three-way comparison guides search
6. **Halving property** — each guess eliminates half the space
7. **Optimal API calls** — O(log n) guesses minimum and sufficient
8. **Iterative vs recursive** — iterative preferred for O(1) space

**Common Follow-ups:**
- "What if guess() API is expensive?" → Already optimal at O(log n) calls
- "Why not ternary search?" → Uses more API calls (1.26× more)
- "What about overflow?" → Use left + (right-left)/2
- "Can you do better than O(log n)?" → No, need Ω(log n) to locate in sorted range

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Guess Number Higher Or Lower** | Easy | **Binary Search with API** | **This problem** |
| Binary Search | Easy | Standard Binary Search | Direct array access |
| Search Insert Position | Easy | Binary Search | Find insertion point |
| First Bad Version | Easy | Binary Search with API | Similar API-based search |
| Sqrt(x) | Easy | Binary Search | Search answer space |
| Peak Element | Medium | Binary Search | Find local maximum |
| Search in Rotated Sorted Array | Medium | Modified Binary Search | Array rotated |
| Find Minimum in Rotated Array | Medium | Binary Search | Find rotation point |

**Pattern Progression**:
1. **Binary Search** — Core algorithm on sorted array
2. **Guess Number** (this problem) — Binary search with API feedback
3. **First Bad Version** — Similar API-based binary search
4. **Search answer space** — Binary search on implicit ranges

---

## Final Pattern Label

✅ **Binary Search with Interactive API (Ternary Feedback)**

**Remember:** This is **standard binary search** with an **API** instead of array. The `guess()` API returns **three values**: `0` (found), `-1` (too high), `1` (too low). The mapping is: `guess(mid)==1` means our guess is **too low** so search **right** (left=mid+1), `guess(mid)==-1` means **too high** so search **left** (right=mid-1). **Critical**: use **overflow-safe mid** calculation `left+(right-left)/2` because n can be up to 2³¹-1. Start with `left=1, right=n` and loop while `left<=right`. Each guess **halves** the search space, giving **O(log n)** time with at most **31 guesses** for maximum input. Space is **O(1)** for iterative (preferred) or **O(log n)** for recursive. Not a trick—just **regular binary search**!
