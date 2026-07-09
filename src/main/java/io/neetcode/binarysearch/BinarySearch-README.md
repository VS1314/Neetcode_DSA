# Binary Search

## Problem Description

**Difficulty**: Easy

You are given an array of **distinct** integers `nums`, sorted in **ascending order**, and an integer `target`.

Implement a function to search for `target` within `nums`. If it exists, then return its **index**, otherwise, return **-1**.

Your solution must run in **O(log n)** time.

## Examples

### Example 1:
```
Input: nums = [-1, 0, 2, 4, 6, 8], target = 4
Output: 3

Explanation:
  4 is at index 3
```

### Example 2:
```
Input: nums = [-1, 0, 2, 4, 6, 8], target = 3
Output: -1

Explanation:
  3 does not exist in the array
```

### Example 3:
```
Input: nums = [5], target = 5
Output: 0

Explanation:
  Single element array, target found at index 0
```

### Example 4:
```
Input: nums = [1, 2, 3, 4, 5], target = 1
Output: 0

Explanation:
  Target is the first element
```

### Example 5:
```
Input: nums = [1, 2, 3, 4, 5], target = 5
Output: 4

Explanation:
  Target is the last element
```

### Example 6:
```
Input: nums = [2, 5], target = 5
Output: 1

Explanation:
  Two element array, target is second element
```

### Example 7:
```
Input: nums = [-10, -5, 0, 5, 10, 15, 20], target = 15
Output: 5

Explanation:
  Target 15 is at index 5
```

### Example 8:
```
Input: nums = [1, 3, 5, 7, 9], target = 6
Output: -1

Explanation:
  Target 6 falls between 5 and 7, doesn't exist
```

### Example 9:
```
Input: nums = [-100, -50, 0, 50, 100], target = -50
Output: 1

Explanation:
  Negative number at index 1
```

### Example 10:
```
Input: nums = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], target = 10
Output: 9

Explanation:
  Last element in larger array
```

## Constraints
- 1 <= nums.length <= 10,000
- -10,000 < nums[i], target < 10,000
- All the integers in `nums` are **unique**
- `nums` is sorted in **ascending order**

**Recommended Complexity**: O(log n) time and O(1) space, where n is the size of the input array

---

## Pattern Recognition

**Primary Pattern**: **Binary Search (Divide and Conquer on Sorted Array)**

**Why This Pattern?**
- Array is **sorted** (critical requirement)
- Need to find specific element
- Must achieve O(log n) time (better than linear)
- Can eliminate half the search space each iteration

**Key Insight**: Sorted Array Enables Halving Search Space
```
Binary Search problem:
  Given: sorted array
  Find: target element's index
  Constraint: O(log n) time
  
Linear search approach:
  Check each element one by one
  Time: O(n) ❌ (doesn't meet constraint)
  
Binary search approach:
  1. Look at middle element
  2. If target < middle: search left half
  3. If target > middle: search right half
  4. If target == middle: found!
  
  Each step eliminates half the array
  Time: O(log n) ✓

Example: nums = [1, 2, 3, 4, 5, 6, 7, 8, 9], target = 7

  Step 1: mid = 5 (index 4)
    7 > 5 → search right half [6, 7, 8, 9]
    
  Step 2: mid = 7 (index 6)
    7 == 7 → found at index 6!
    
Only 2 comparisons instead of 7!
```

**The Pointer Strategy**:
```
Use two pointers: left and right

  left: start of search range
  right: end of search range
  mid: middle of current range
  
Algorithm:
  while left <= right:
      mid = left + (right - left) / 2
      
      if nums[mid] == target:
          return mid (found!)
      else if nums[mid] < target:
          left = mid + 1 (search right)
      else:
          right = mid - 1 (search left)
  
  return -1 (not found)

Search space shrinks:
  Iteration 1: full array
  Iteration 2: half array
  Iteration 3: quarter array
  ...
  
Stops when left > right (target not found)
```

**Example Showing Search Space Evolution**:
```
nums = [1, 3, 5, 7, 9, 11, 13, 15, 17, 19]
target = 13

Iteration 1:
  left=0, right=9
  mid = (0+9)/2 = 4
  nums[4] = 9
  13 > 9 → search right
  left = 5
  
  Search space: [11, 13, 15, 17, 19]

Iteration 2:
  left=5, right=9
  mid = (5+9)/2 = 7
  nums[7] = 15
  13 < 15 → search left
  right = 6
  
  Search space: [11, 13]

Iteration 3:
  left=5, right=6
  mid = (5+6)/2 = 5
  nums[5] = 11
  13 > 11 → search right
  left = 6
  
  Search space: [13]

Iteration 4:
  left=6, right=6
  mid = (6+6)/2 = 6
  nums[6] = 13
  13 == 13 → found!
  
Return: 6
```

**Why left + (right - left) / 2?**
```
Calculating middle index:

Naive approach:
  mid = (left + right) / 2
  
Problem: integer overflow!
  If left + right > Integer.MAX_VALUE
  Example: left=2000000000, right=2000000000
  left + right = 4000000000 → overflow!
  
Safe approach:
  mid = left + (right - left) / 2
  
Why this works:
  (right - left) is always positive and small
  No overflow!
  
Mathematically equivalent:
  left + (right - left) / 2
  = left + right/2 - left/2
  = left/2 + right/2
  = (left + right) / 2
  
Same result, but safer!
```

**Critical Detail**: Loop Condition left <= right
```
Why <= not <?

Example: single element [5], target=5
  left=0, right=0
  
  With left < right:
    Loop doesn't execute (0 < 0 is false)
    Return -1 ❌
  
  With left <= right:
    Loop executes (0 <= 0 is true)
    mid=0, nums[0]=5, found! ✓

Must use <= to handle single element case!

When to stop:
  left > right means search space is empty
  Target not in array
```

**When to Use left = mid + 1 vs right = mid - 1?**
```
Key: We know nums[mid] is NOT the target!

Case 1: nums[mid] < target
  Target must be in right half
  mid is too small, exclude it
  left = mid + 1 ✓
  
Case 2: nums[mid] > target
  Target must be in left half
  mid is too large, exclude it
  right = mid - 1 ✓

Always exclude mid when not found!

Example: [1, 3, 5], target=4
  mid=1, nums[1]=3
  3 < 4 → search right
  left = 1+1 = 2
  Search [5]
  
  If we used left=mid:
    left=1, infinite loop! ❌
```

**Related Patterns**:
1. **Binary Search** — Core technique
2. **Divide and Conquer** — Split problem in half
3. **Two Pointers** — Left and right boundaries
4. **Sorted Array Search** — Foundational pattern

---

## Algorithm & Approach

### Core Insight

**Why Linear Search is Not Enough:**
```
Linear search:
  for (int i = 0; i < n; i++):
      if nums[i] == target:
          return i
  return -1
  
Time: O(n)
Problem: Doesn't use sorted property!
Wastes information!

Binary search:
  Use sorted property to eliminate half each step
  Time: O(log n) ✓
  
For n=10,000:
  Linear: up to 10,000 comparisons
  Binary: up to 14 comparisons (log₂ 10,000 ≈ 13.3)
  
Massive improvement!
```

**The Optimal Strategy**:
```
Key observations:
  1. Array is sorted
  2. Can compare target with middle element
  3. Comparison tells us which half to search
  4. Repeat until found or search space empty
  
Operations:
  Each iteration: O(1) comparison
  Number of iterations: O(log n)
  
Total: O(log n)
```

### Step-by-Step Algorithm

---

#### **Approach 1: Iterative Binary Search - OPTIMAL**

**Core Idea**:
- Maintain left and right pointers
- Calculate middle, compare with target
- Adjust pointers based on comparison
- Repeat until found or search space empty

**Algorithm**
```
binarySearch(nums, target):
    left = 0
    right = nums.length - 1
    
    while left <= right:
        mid = left + (right - left) / 2
        
        if nums[mid] == target:
            return mid
        else if nums[mid] < target:
            left = mid + 1
        else:
            right = mid - 1
    
    return -1
```

**Code Implementation**
```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
}
```

**Example Walkthrough**

Input: `nums = [-1, 0, 2, 4, 6, 8], target = 4`

| Iteration | left | right | mid | nums[mid] | Comparison | Action |
|-----------|------|-------|-----|-----------|------------|--------|
| 1 | 0 | 5 | 2 | 2 | 2 < 4 | left = 3 |
| 2 | 3 | 5 | 4 | 6 | 6 > 4 | right = 3 |
| 3 | 3 | 3 | 3 | 4 | 4 == 4 | Found! |

Return: **3**

**Complexity Analysis**
- **Time**: O(log n) — Halve search space each iteration
- **Space**: O(1) — Only use constant extra variables

---

#### **Approach 2: Recursive Binary Search - ELEGANT**

**Core Idea**: Same logic but implemented recursively.

**Code Implementation**
```java
class Solution {
    public int search(int[] nums, int target) {
        return binarySearchRecursive(nums, target, 0, nums.length - 1);
    }
    
    private int binarySearchRecursive(int[] nums, int target, int left, int right) {
        // Base case: search space empty
        if (left > right) {
            return -1;
        }
        
        int mid = left + (right - left) / 2;
        
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            return binarySearchRecursive(nums, target, mid + 1, right);
        } else {
            return binarySearchRecursive(nums, target, left, mid - 1);
        }
    }
}
```

**Key Difference**: 
- Uses call stack instead of loop
- More elegant but uses O(log n) space
- Same time complexity

**Complexity Analysis**
- **Time**: O(log n) — Same number of comparisons
- **Space**: O(log n) — Call stack depth

---

#### **Approach 3: Using Built-in Binary Search - LIBRARY**

**Core Idea**: Java provides Arrays.binarySearch().

**Code Implementation**
```java
import java.util.Arrays;

class Solution {
    public int search(int[] nums, int target) {
        int result = Arrays.binarySearch(nums, target);
        return result >= 0 ? result : -1;
    }
}
```

**Key Difference**: 
- Uses standard library
- Arrays.binarySearch returns negative if not found
- Production code often uses this

**Complexity Analysis**
- **Time**: O(log n) — Same algorithm internally
- **Space**: O(1) — Iterative implementation

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Code Complexity | Recommended |
|----------|------|-------|-----------------|-------------|
| **Iterative Binary Search** | **O(log n)** | **O(1)** | **Simple ✅** | **Yes ✅** |
| Recursive Binary Search | O(log n) | O(log n) | Medium | Educational |
| Arrays.binarySearch | O(log n) | O(1) | Trivial | Production |
| Linear Search | O(n) | O(1) | Simple | Too slow ❌ |

**Winner**: **Iterative Binary Search** — optimal time, minimal space, clear logic!

### Why Binary Search is Faster

```
Comparison of approaches for n=1000:

Linear Search:
  Worst case: 1000 comparisons
  Average: 500 comparisons
  
Binary Search:
  Worst case: 10 comparisons (log₂ 1000 ≈ 10)
  Average: ~9 comparisons
  
Speedup: 100x faster!

For n=1,000,000:
  Linear: 1,000,000 comparisons
  Binary: 20 comparisons
  
Speedup: 50,000x faster!

Binary search scales amazingly well!
```

### Why Sorted Array is Required

```
Binary search ONLY works on sorted arrays!

Example: unsorted array [5, 2, 8, 1, 9], target=1

  mid = 8
  1 < 8 → search left
  Left half: [5, 2]
  But 1 is actually in right half! ❌

Sorted array required for:
  Comparison to guide direction
  Guarantee target is in chosen half
  Eliminate other half safely

Without sorting: binary search fails!
```

### Why Use Two Pointers

```
Two pointers define current search range

  left: smallest index we're still considering
  right: largest index we're still considering
  
Each iteration shrinks this range:
  [left, right] → [left, mid-1] or [mid+1, right]
  
Size: right - left + 1

Example: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]

  Start: left=0, right=9, size=10
  After 1: left=5, right=9, size=5
  After 2: left=5, right=6, size=2
  After 3: left=6, right=6, size=1
  After 4: left=7, right=6, size=0 (stop)

Pointers track shrinking search space!
```

### Why left + (right - left) / 2

```
Three ways to calculate mid:

Method 1: (left + right) / 2
  Problem: overflow if left + right > MAX_INT
  Example: left=2×10⁹, right=2×10⁹
  left + right = 4×10⁹ > MAX_INT ❌
  
Method 2: left + (right - left) / 2
  Safe: (right - left) never overflows
  Always works ✓
  
Method 3: (left + right) >>> 1
  Unsigned right shift
  Also safe
  Slightly faster but less readable
  
Recommended: Method 2 for clarity
```

### Why <= Not < in Loop

```
Loop condition: while (left <= right)

Why <=?
  Need to check when left == right
  This is single element range!
  
Example: [5], target=5
  left=0, right=0
  
  With left < right:
    0 < 0 is false
    Loop doesn't run
    Return -1 ❌
  
  With left <= right:
    0 <= 0 is true
    Check mid=0
    Found! ✓

When left > right:
  Search space is empty
  Target definitely not in array
  Return -1
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Element Array - Target Found**
```java
Input: nums = [1], target = 1
left=0, right=0, mid=0
nums[0] == 1
Output: 0
```

### 2. **Single Element Array - Target Not Found**
```java
Input: nums = [1], target = 2
left=0, right=0, mid=0
nums[0] < 2 → left=1
left > right → stop
Output: -1
```

### 3. **Target is First Element**
```java
Input: nums = [1, 2, 3, 4, 5], target = 1
First iteration finds it at mid (or quickly narrows to it)
Output: 0
```

### 4. **Target is Last Element**
```java
Input: nums = [1, 2, 3, 4, 5], target = 5
Binary search narrows to right end
Output: 4
```

### 5. **Target Smaller Than All**
```java
Input: nums = [5, 6, 7], target = 1
mid=6, 1<6 → right=mid-1
Eventually left > right
Output: -1
```

### 6. **Target Larger Than All**
```java
Input: nums = [1, 2, 3], target = 10
mid=2, 10>2 → left=mid+1
Eventually left > right
Output: -1
```

### 7. **Target Between Elements**
```java
Input: nums = [1, 3, 5], target = 4
mid=3, 4>3 → search right
Right half: [5]
mid=5, 4<5 → search left
Empty → -1
Output: -1
```

### 8. **Two Element Array**
```java
Input: nums = [1, 3], target = 3
mid=0, nums[0]=1, 3>1 → left=1
mid=1, nums[1]=3, found!
Output: 1
```

### 9. **Large Array**
```java
Input: nums = [1..10000], target = 9999
Binary search: ~14 comparisons
Linear search: 9999 comparisons
Output: 9998
```

### 10. **Negative Numbers**
```java
Input: nums = [-100, -50, 0, 50], target = -50
Binary search works same as positive
Output: 1
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Using (left + right) / 2**
```java
// WRONG - potential overflow
int mid = (left + right) / 2;
```

**Why wrong**: Integer overflow for large indices!

**Dry run failure:**
```
left = 2000000000
right = 2000000000
left + right = 4000000000 > Integer.MAX_VALUE
Overflows to negative number!
mid becomes negative ❌
```

**Fix**: Use safe calculation
```java
int mid = left + (right - left) / 2;
```

### ❌ **MISTAKE 2: Using left < right Instead of left <= right**
```java
// WRONG - misses single element case
while (left < right) {
    // ...
}
```

**Why wrong**: Doesn't check when left == right!

**Dry run failure for nums=[5], target=5:**
```
left = 0, right = 0
Condition: 0 < 0? false
Loop doesn't execute
Return -1 ❌

Should check nums[0] == 5 and return 0!
```

**Fix**: Use <=
```java
while (left <= right) {
    // ...
}
```

### ❌ **MISTAKE 3: Using left = mid or right = mid**
```java
// WRONG - doesn't exclude mid
if (nums[mid] < target) {
    left = mid;  // Should be mid + 1!
}
```

**Why wrong**: Infinite loop or wrong result!

**Dry run failure for nums=[1,3], target=3:**
```
Iteration 1:
  left=0, right=1, mid=0
  nums[0]=1 < 3
  left = mid = 0 (doesn't move!)
  
Iteration 2:
  left=0, right=1, mid=0
  Same state, infinite loop! ❌
```

**Fix**: Always add/subtract 1
```java
left = mid + 1;
right = mid - 1;
```

### ❌ **MISTAKE 4: Wrong Comparison Direction**
```java
// WRONG - backwards logic
if (nums[mid] < target) {
    right = mid - 1;  // Should be left = mid + 1!
} else if (nums[mid] > target) {
    left = mid + 1;  // Should be right = mid - 1!
}
```

**Why wrong**: Searches wrong half!

**Dry run failure for nums=[1,2,3,4,5], target=4:**
```
mid=2, nums[2]=3
3 < 4, but we set right=1
Search [1,2] instead of [4,5]
Never find target! ❌
```

**Fix**: Correct logic
```java
if (nums[mid] < target) {
    left = mid + 1;  // Search right
} else {
    right = mid - 1;  // Search left
}
```

### ❌ **MISTAKE 5: Not Returning -1**
```java
// WRONG - no return after loop
while (left <= right) {
    // ... search logic
}
// Missing: return -1;
```

**Why wrong**: No return for not found case!

**Fix**: Return -1 after loop
```java
return -1;
```

### ❌ **MISTAKE 6: Off-by-One in Initialization**
```java
// WRONG - wrong initial right
int right = nums.length;  // Should be nums.length - 1!
```

**Why wrong**: Out of bounds access!

**Dry run failure:**
```
nums = [1, 2, 3], length = 3
right = 3 (out of bounds!)
mid = 1
If we access right: nums[3] → ArrayIndexOutOfBoundsException ❌
```

**Fix**: Use length - 1
```java
int right = nums.length - 1;
```

### ❌ **MISTAKE 7: Forgetting to Return Mid**
```java
// WRONG - doesn't return when found
if (nums[mid] == target) {
    // Missing: return mid;
}
```

**Why wrong**: Continues searching unnecessarily!

**Fix**: Return immediately when found
```java
if (nums[mid] == target) {
    return mid;
}
```

---

## Complexity Analysis

### Time Complexity: **O(log n)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **While loop iterations** | O(log n) | O(1) | O(log n) |
| **Calculate mid** | O(log n) | O(1) | O(log n) |
| **Compare nums[mid]** | O(log n) | O(1) | O(log n) |
| **Update pointers** | O(log n) | O(1) | O(log n) |
| **Total** | - | - | **O(log n)** |

**Time analysis**:
```
Each iteration halves search space:
  Iteration 0: n elements
  Iteration 1: n/2 elements
  Iteration 2: n/4 elements
  Iteration 3: n/8 elements
  ...
  Iteration k: n/(2^k) elements
  
Stop when n/(2^k) = 1
  2^k = n
  k = log₂(n)
  
Number of iterations: O(log n)

For n=10,000:
  log₂(10,000) ≈ 13.3
  At most 14 iterations!

For n=1,000,000:
  log₂(1,000,000) ≈ 19.9
  At most 20 iterations!

Scales incredibly well!
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| left | O(1) | Single integer |
| right | O(1) | Single integer |
| mid | O(1) | Single integer |
| **Total** | **O(1)** | Constant space |

**Space analysis**:
```
Iterative binary search:
  Only 3 integer variables
  No extra arrays or data structures
  Space: O(1) ✓

Recursive binary search:
  Call stack depth: O(log n)
  Each call uses O(1) space
  Total: O(log n) space
  
Iterative is more space-efficient!
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `nums = [1, 3, 5, 7, 9, 11, 13, 15], target = 11`

**Expected Output:** `5`

---

**Initial State:**
```
Array:  [1, 3, 5, 7, 9, 11, 13, 15]
Index:   0  1  2  3  4   5   6   7

left = 0
right = 7
target = 11
```

---

**Iteration 1:**
```
Calculate mid:
  mid = 0 + (7-0)/2 = 0 + 3 = 3
  
Array:  [1, 3, 5, 7, 9, 11, 13, 15]
Index:   0  1  2  3  4   5   6   7
                   ↑
                  mid

Compare:
  nums[3] = 7
  7 < 11 (target is larger)
  
Action: Search right half
  left = mid + 1 = 4
  
New search space: [9, 11, 13, 15]
  left=4, right=7
```

---

**Iteration 2:**
```
Calculate mid:
  mid = 4 + (7-4)/2 = 4 + 1 = 5
  
Array:  [1, 3, 5, 7, 9, 11, 13, 15]
Index:   0  1  2  3  4   5   6   7
                          ↑
                         mid

Compare:
  nums[5] = 11
  11 == 11 (found!)
  
Action: Return mid
  
Return: 5 ✓
```

---

### Another Example: Target Not Found

**Input:** `nums = [1, 3, 5, 7, 9], target = 6`

```
Iteration 1:
  left=0, right=4, mid=2
  nums[2]=5, 5<6
  left=3
  Search: [7, 9]

Iteration 2:
  left=3, right=4, mid=3
  nums[3]=7, 7>6
  right=2
  Search: empty (left > right)

Return: -1 (not found)
```

---

### Search Space Visualization

```
nums = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
target = 7

Iteration 1: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
             |← left        mid         right →|
             mid=5, 7>5, search right

Iteration 2:                [6, 7, 8, 9, 10]
                            |← left  mid  right →|
                            mid=8, 7<8, search left

Iteration 3:                [6, 7]
                            |← left=right →|
                            mid=6, 7>6, search right

Iteration 4:                   [7]
                               |← left=right →|
                               mid=7, found!
```

---

### Binary Tree Representation

```
Binary search as decision tree for [1,2,3,4,5,6,7]:

                   mid=4 (3)
                   /      \
              <3            >3
              /              \
        mid=2 (1)          mid=6 (5)
        /      \           /      \
      <1       >1        <5       >5
      /         \        /         \
   mid=1(0)  mid=3(2)  mid=5(4) mid=7(6)

Each level halves the search space
Height of tree: log₂(n)
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Lines | Clarity | Recommended |
|----------|------|-------|------------|---------|-------------|
| **Iterative Binary Search** | **O(log n)** | **O(1)** | **~15** | **Excellent ✅** | **Yes ✅** |
| Recursive Binary Search | O(log n) | O(log n) | ~12 | Good | Educational |
| Arrays.binarySearch | O(log n) | O(1) | ~3 | Trivial | Production |
| Linear Search | O(n) | O(1) | ~5 | Simple | Too slow ❌ |

**Winner**: **Iterative Binary Search** — optimal, clear, fundamental!

---

## Key Takeaways

1. **Binary search only works on sorted arrays** — critical requirement
2. **Use two pointers** — left and right define search range
3. **Calculate mid safely** — left + (right-left)/2 prevents overflow
4. **Loop condition is <=** — must check single element case
5. **Exclude mid when not found** — left=mid+1, right=mid-1
6. **Compare and eliminate half** — core of divide and conquer
7. **Return -1 if not found** — after loop exits
8. **O(log n) time** — halve search space each iteration
9. **O(1) space** — only use constant variables
10. **Fundamental algorithm** — basis for many advanced techniques

---

## Interview Tips

**What to say in an interview:**

> "This problem requires finding a target in a sorted array in O(log n) time. Since the array is sorted, I can use binary search. I'll maintain two pointers, left and right, representing the current search range. In each iteration, I calculate the middle index using left + (right-left)/2 to avoid overflow. If the middle element equals the target, I return its index. If the middle element is less than the target, the target must be in the right half, so I set left to mid+1. If the middle element is greater, the target is in the left half, so I set right to mid-1. This eliminates half the search space each iteration. I continue until left exceeds right, meaning the target isn't in the array, and return -1. Time complexity is O(log n) since we halve the search space each time, and space complexity is O(1) using only a few variables."

**Key points to mention:**
1. **Sorted array enables binary search** — core requirement
2. **Two pointers** — left and right boundaries
3. **Safe mid calculation** — avoid overflow
4. **Three cases** — equal (found), less than (search right), greater (search left)
5. **Exclude mid** — use mid+1 or mid-1
6. **Loop until left > right** — search space empty
7. **O(log n) time** — halve each iteration
8. **O(1) space** — constant variables

**Common Follow-ups:**
- "What if array is not sorted?" → Must sort first O(n log n) or use linear search
- "Can you do better than O(log n)?" → No, need to look at input (Ω(log n) lower bound)
- "What about duplicates?" → This problem has unique elements; variants exist
- "How would you find first/last occurrence?" → Modified binary search with different conditions

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Binary Search** | Easy | **Basic Binary Search** | **This problem** |
| Search Insert Position | Easy | Binary Search | Find insertion index if not found |
| First Bad Version | Easy | Binary Search | API call instead of array access |
| Search in Rotated Sorted Array | Medium | Modified Binary Search | Rotated, need extra checks |
| Find Minimum in Rotated Sorted Array | Medium | Modified Binary Search | Find pivot point |
| Search a 2D Matrix | Medium | Binary Search | 2D treated as 1D |
| Find Peak Element | Medium | Binary Search | Local maximum |
| Sqrt(x) | Easy | Binary Search | Search answer space |

**Pattern Progression**:
1. **Basic binary search** (this problem) — Foundation
2. **Modified conditions** (Rotated array) — Handle special cases
3. **Answer space search** (Sqrt) — Search for answer not element
4. **2D binary search** (Matrix) — Higher dimensions

---

## Final Pattern Label

✅ **Binary Search (Classic Divide and Conquer on Sorted Array)**

**Remember:** Binary search requires **sorted array**. Use **two pointers** left and right. Calculate mid with **left + (right-left)/2** to avoid overflow. Loop while **left <= right** (not just <). Compare nums[mid] with target: **equal→return**, **less→search right** (left=mid+1), **greater→search left** (right=mid-1). Always **exclude mid** when not found. Return **-1** if loop exits. **O(log n) time** by halving search space, **O(1) space** with constant variables. Fundamental algorithm for sorted array search!
