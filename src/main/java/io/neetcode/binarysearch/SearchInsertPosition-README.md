# Search Insert Position

## Problem Description

**Difficulty**: Easy

You are given a **sorted array** of **distinct integers** and a **target value**, return the **index** if the target is found. If not, return the **index where it would be if it were inserted in order**.

You must write an algorithm with **O(log n)** runtime complexity.

## Examples

### Example 1:
```
Input: nums = [-1, 0, 2, 4, 6, 8], target = 5
Output: 4

Explanation:
  5 is not in array
  5 should be inserted between 4 (index 3) and 6 (index 4)
  Insert at index 4
```

### Example 2:
```
Input: nums = [-1, 0, 2, 4, 6, 8], target = 10
Output: 6

Explanation:
  10 is larger than all elements
  Insert at end (index 6)
```

### Example 3:
```
Input: nums = [1, 3, 5, 6], target = 5
Output: 2

Explanation:
  5 is found at index 2
```

### Example 4:
```
Input: nums = [1, 3, 5, 6], target = 2
Output: 1

Explanation:
  2 should be inserted between 1 and 3
  Insert at index 1
```

### Example 5:
```
Input: nums = [1, 3, 5, 6], target = 7
Output: 4

Explanation:
  7 is larger than all elements
  Insert at end (index 4)
```

### Example 6:
```
Input: nums = [1, 3, 5, 6], target = 0
Output: 0

Explanation:
  0 is smaller than all elements
  Insert at beginning (index 0)
```

### Example 7:
```
Input: nums = [1], target = 1
Output: 0

Explanation:
  Single element array, target found at index 0
```

### Example 8:
```
Input: nums = [1], target = 0
Output: 0

Explanation:
  Insert before the only element
```

### Example 9:
```
Input: nums = [1], target = 2
Output: 1

Explanation:
  Insert after the only element
```

### Example 10:
```
Input: nums = [1, 3], target = 2
Output: 1

Explanation:
  Insert between 1 and 3
```

## Constraints
- 1 <= nums.length <= 10,000
- -10,000 < nums[i], target < 10,000
- `nums` contains **distinct values** sorted in **ascending order**

**Recommended Complexity**: O(log n) time and O(1) space, where n is the size of the input array

---

## Pattern Recognition

**Primary Pattern**: **Binary Search with Insertion Position**

**Why This Pattern?**
- Array is **sorted** (enables binary search)
- Need O(log n) time (binary search requirement)
- Different from standard binary search: return insertion position instead of -1
- Need to find where element belongs, even if not present

**Key Insight**: Left Pointer Tracks Insertion Position
```
Search Insert Position problem:
  Given: sorted array
  Find: target's index OR where it should be inserted
  
Standard binary search:
  If not found: return -1
  
Modified binary search:
  If not found: return left pointer
  
Why left pointer?
  When loop ends (left > right):
    right: last index where nums[right] < target
    left: first index where nums[left] >= target
    
  left is the correct insertion position!

Example: nums = [1, 3, 5, 7], target = 4

  Binary search process:
    mid=1, nums[1]=3, 4>3 → left=2
    mid=2, nums[2]=5, 4<5 → right=1
    Loop ends: left=2, right=1
    
  At end:
    nums[1] = 3 < 4  (right pointer)
    nums[2] = 5 > 4  (left pointer)
    
  Insert at left = 2 ✓
  Result: [1, 3, 4, 5, 7]
```

**The Binary Search Strategy**:
```
Same as standard binary search but different return:

Standard:
  while left <= right:
      mid = left + (right - left) / 2
      if nums[mid] == target:
          return mid
      else if nums[mid] < target:
          left = mid + 1
      else:
          right = mid - 1
  return -1  // Not found

Modified:
  while left <= right:
      mid = left + (right - left) / 2
      if nums[mid] == target:
          return mid
      else if nums[mid] < target:
          left = mid + 1
      else:
          right = mid - 1
  return left  // Insertion position!

Only difference: return left instead of -1
```

**Example Showing Why Left is Correct**:
```
Case 1: Insert at beginning
  nums = [2, 3, 4], target = 1
  
  mid=1, nums[1]=3, 1<3 → right=0
  mid=0, nums[0]=2, 1<2 → right=-1
  Loop ends: left=0, right=-1
  
  Insert at index 0 ✓
  Result: [1, 2, 3, 4]

Case 2: Insert at end
  nums = [1, 2, 3], target = 4
  
  mid=1, nums[1]=2, 4>2 → left=2
  mid=2, nums[2]=3, 4>3 → left=3
  Loop ends: left=3, right=2
  
  Insert at index 3 (end) ✓
  Result: [1, 2, 3, 4]

Case 3: Insert in middle
  nums = [1, 2, 4, 5], target = 3
  
  mid=1, nums[1]=2, 3>2 → left=2
  mid=2, nums[2]=4, 3<4 → right=1
  Loop ends: left=2, right=1
  
  Insert at index 2 ✓
  Result: [1, 2, 3, 4, 5]

Left always points to correct insertion position!
```

**Why This Works Mathematically**:
```
Loop invariant:
  All elements at indices < left are < target
  All elements at indices > right are > target

When loop ends (left > right):
  left is smallest index where nums[left] >= target
  
This is exactly where we should insert target!

Proof:
  If target should be at index i:
    nums[i-1] < target < nums[i]
    
  Binary search ensures:
    right ends at i-1 (last index < target)
    left ends at i (first index >= target)
    
  So left = i = insertion position ✓
```

**Edge Cases Handled**:
```
1. Target smaller than all:
   right becomes -1, left stays 0
   Insert at 0 ✓

2. Target larger than all:
   left becomes n, right stays n-1
   Insert at n (end) ✓

3. Target found:
   Return mid immediately ✓

4. Single element:
   Works same as regular binary search ✓

All cases handled correctly!
```

**Related Patterns**:
1. **Binary Search** — Core technique
2. **Insertion Position** — Finding where to insert
3. **Lower Bound** — First position >= target
4. **Sorted Array Modification** — Maintaining order

---

## Algorithm & Approach

### Core Insight

**Why This is Simple Binary Search:**
```
Same algorithm as standard binary search!

Only difference:
  return left instead of return -1

Why?
  When target not found, binary search naturally
  positions left pointer at insertion position
  
No extra logic needed!
Just change the final return statement.
```

**The Optimal Strategy**:
```
Key observations:
  1. Array is sorted → use binary search
  2. Binary search already tracks correct position
  3. Left pointer = insertion position when not found
  4. Same O(log n) time as standard binary search
  
Operations:
  Each iteration: O(1) comparison
  Number of iterations: O(log n)
  
Total: O(log n)
```

### Step-by-Step Algorithm

---

#### **Approach 1: Binary Search with Left Return - OPTIMAL**

**Core Idea**:
- Use standard binary search
- If found, return index
- If not found, return left (insertion position)

**Algorithm**
```
searchInsert(nums, target):
    left = 0
    right = nums.length - 1
    
    while left <= right:
        mid = left + (right - left) / 2
        
        if nums[mid] == target:
            return mid  // Found
        else if nums[mid] < target:
            left = mid + 1  // Search right
        else:
            right = mid - 1  // Search left
    
    return left  // Insertion position
```

**Code Implementation**
```java
class Solution {
    public int searchInsert(int[] nums, int target) {
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
        
        return left;
    }
}
```

**Example Walkthrough**

Input: `nums = [1, 3, 5, 6], target = 2`

| Iteration | left | right | mid | nums[mid] | Comparison | Action |
|-----------|------|-------|-----|-----------|------------|--------|
| 1 | 0 | 3 | 1 | 3 | 2 < 3 | right = 0 |
| 2 | 0 | 0 | 0 | 1 | 2 > 1 | left = 1 |
| End | 1 | 0 | - | - | left > right | Stop |

Return: **left = 1** (insertion position)

Result array after insertion: [1, **2**, 3, 5, 6]

**Complexity Analysis**
- **Time**: O(log n) — Same as binary search
- **Space**: O(1) — Only constant variables

---

#### **Approach 2: Iterative with Explicit Check - VERBOSE**

**Core Idea**: Check all cases explicitly for clarity.

**Code Implementation**
```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        // Edge case: target smaller than first element
        if (target < nums[0]) {
            return 0;
        }
        
        // Edge case: target larger than last element
        if (target > nums[nums.length - 1]) {
            return nums.length;
        }
        
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
        
        return left;
    }
}
```

**Key Difference**: 
- Explicit edge case checks
- More lines but potentially clearer
- Same complexity

**Complexity Analysis**
- **Time**: O(log n) — Same binary search
- **Space**: O(1) — Constant space

---

#### **Approach 3: Lower Bound Template - GENERAL**

**Core Idea**: Use generalized lower bound template.

**Code Implementation**
```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length;  // Note: length, not length-1
        
        while (left < right) {  // Note: <, not <=
            int mid = left + (right - left) / 2;
            
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;  // Note: mid, not mid-1
            }
        }
        
        return left;
    }
}
```

**Key Difference**: 
- Different template (right = length, left < right)
- Always finds lower bound (first >= target)
- More general but less intuitive

**Complexity Analysis**
- **Time**: O(log n)
- **Space**: O(1)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Code Complexity | Recommended |
|----------|------|-------|-----------------|-------------|
| **Binary Search (return left)** | **O(log n)** | **O(1)** | **Simple ✅** | **Yes ✅** |
| Explicit Edge Cases | O(log n) | O(1) | Medium | Alternative |
| Lower Bound Template | O(log n) | O(1) | Medium | General |
| Linear Search | O(n) | O(1) | Simple | Too slow ❌ |

**Winner**: **Binary Search with left return** — minimal change from standard!

### Why Return Left?

```
When binary search ends without finding target:
  left > right (loop condition violated)
  
At this point:
  right: largest index where nums[right] < target
  left: smallest index where nums[left] >= target
  
Insertion position is where target should go:
  Between right and left
  Since left = right + 1, insert at left!

Example: nums = [1, 3, 5, 7, 9], target = 6

  After binary search:
    right = 2 (nums[2] = 5 < 6)
    left = 3 (nums[3] = 7 > 6)
    
  Insert at left = 3:
    [1, 3, 5, 6, 7, 9] ✓

Left is the natural insertion position!
```

### Why Not Return Right + 1?

```
Could we return right + 1?
  Yes! Since left = right + 1
  
But:
  return left is more direct
  Less computation
  Clearer intent
  
Both work, left is preferred.
```

### Why This Works for All Cases

```
Case 1: Target at beginning
  nums = [2, 3, 4], target = 1
  Binary search: right = -1, left = 0
  Return 0 ✓

Case 2: Target at end
  nums = [1, 2, 3], target = 4
  Binary search: right = 2, left = 3
  Return 3 (= length) ✓

Case 3: Target in middle
  nums = [1, 3, 5], target = 4
  Binary search: right = 1, left = 2
  Return 2 ✓

Case 4: Target exists
  Binary search finds it
  Return mid ✓

All cases handled without special logic!
```

### Why Not Use Linear Search

```
Linear search:
  for i in range(len(nums)):
      if nums[i] >= target:
          return i
  return len(nums)
  
Time: O(n) ❌

Binary search:
  Same logic but O(log n) ✓

For n=10,000:
  Linear: up to 10,000 comparisons
  Binary: up to 14 comparisons
  
Binary search is much faster!
```

### Why Sorted Array is Critical

```
Binary search requires sorted array!

Example with unsorted: [5, 1, 3], target = 2
  mid = 1
  nums[1] = 1 < 2
  Search right: [3]
  But 2 should be between 1 and 3!
  Wrong result ❌

Only works on sorted arrays!
```

---

## Critical Edge Cases & Gotchas

### 1. **Insert at Beginning (Smaller Than All)**
```java
Input: nums = [1, 3, 5], target = 0
Binary search: left = 0, right = -1
Output: 0
Result: [0, 1, 3, 5]
```

### 2. **Insert at End (Larger Than All)**
```java
Input: nums = [1, 3, 5], target = 10
Binary search: left = 3, right = 2
Output: 3 (= length)
Result: [1, 3, 5, 10]
```

### 3. **Target Found**
```java
Input: nums = [1, 3, 5], target = 3
Binary search finds at mid = 1
Output: 1
No insertion needed
```

### 4. **Single Element - Target Smaller**
```java
Input: nums = [5], target = 1
left = 0, right = -1
Output: 0
Result: [1, 5]
```

### 5. **Single Element - Target Larger**
```java
Input: nums = [5], target = 10
left = 1, right = 0
Output: 1
Result: [5, 10]
```

### 6. **Single Element - Target Equal**
```java
Input: nums = [5], target = 5
Found at mid = 0
Output: 0
```

### 7. **Two Elements - Insert Middle**
```java
Input: nums = [1, 5], target = 3
left = 1, right = 0
Output: 1
Result: [1, 3, 5]
```

### 8. **Two Elements - Insert Beginning**
```java
Input: nums = [3, 5], target = 1
left = 0, right = -1
Output: 0
Result: [1, 3, 5]
```

### 9. **Two Elements - Insert End**
```java
Input: nums = [1, 3], target = 5
left = 2, right = 1
Output: 2
Result: [1, 3, 5]
```

### 10. **Large Array**
```java
Input: nums = [1..10000], target = 5555
Binary search: ~14 comparisons
Output: 5554 or exact if found
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Returning -1 Instead of left**
```java
// WRONG - returns -1 like standard binary search
while (left <= right) {
    // ... binary search logic
}
return -1;  // Should be: return left
```

**Why wrong**: Doesn't return insertion position!

**Dry run failure for nums=[1,3,5], target=2:**
```
Binary search doesn't find 2
Returns -1 ❌

Should return 1 (insertion position) ✓
```

**Fix**: Return left
```java
return left;
```

### ❌ **MISTAKE 2: Returning right Instead of left**
```java
// WRONG - returns right pointer
return right;
```

**Why wrong**: Right points to wrong position!

**Dry run failure for nums=[1,3,5], target=2:**
```
After search: left=1, right=0
return right = 0 ❌

But 2 should go at index 1!
[1, 2, 3, 5] needs insertion at 1 ✓

Right points to element before insertion point!
```

**Fix**: Return left
```java
return left;
```

### ❌ **MISTAKE 3: Off-by-One in Edge Cases**
```java
// WRONG - doesn't handle end insertion
if (target > nums[nums.length - 1]) {
    return nums.length - 1;  // Should be nums.length!
}
```

**Why wrong**: Returns last index, not index after!

**Dry run failure for nums=[1,2,3], target=5:**
```
5 > 3
return 2 ❌

But 5 should go at index 3 (after array)
[1, 2, 3, 5] needs insertion at 3 ✓
```

**Fix**: Return length
```java
return nums.length;
```

### ❌ **MISTAKE 4: Using Wrong Loop Condition**
```java
// WRONG - uses < instead of <=
while (left < right) {
    // ...
}
```

**Why wrong**: Misses single element case!

**Dry run failure for nums=[5], target=5:**
```
left=0, right=0
Condition: 0 < 0? false
Loop doesn't execute
return 0 ❌

Should check nums[0] and return 0 if found ✓
```

**Fix**: Use <=
```java
while (left <= right) {
    // ...
}
```

### ❌ **MISTAKE 5: Not Excluding mid Correctly**
```java
// WRONG - doesn't exclude mid
if (nums[mid] < target) {
    left = mid;  // Should be mid + 1
}
```

**Why wrong**: Infinite loop!

**Dry run failure:**
```
Same as standard binary search mistake
left doesn't move, infinite loop ❌
```

**Fix**: Exclude mid
```java
left = mid + 1;
right = mid - 1;
```

### ❌ **MISTAKE 6: Overthinking Edge Cases**
```java
// WRONG - unnecessary special handling
if (nums.length == 0) return 0;
if (target < nums[0]) return 0;
if (target > nums[nums.length-1]) return nums.length;
// ... then binary search

// All this is redundant! Binary search handles it!
```

**Why wrong**: Adds complexity without benefit!

**Better**: Let binary search handle naturally
```java
// Just do binary search
// It handles all cases automatically
```

### ❌ **MISTAKE 7: Wrong Mid Calculation**
```java
// WRONG - potential overflow
int mid = (left + right) / 2;
```

**Why wrong**: Same as standard binary search overflow issue!

**Fix**: Safe calculation
```java
int mid = left + (right - left) / 2;
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
Same as standard binary search!
Each iteration halves search space

Iterations: log₂(n)

For n=10,000:
  log₂(10,000) ≈ 13.3
  At most 14 iterations

Exactly same time as finding element!
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
Only three integer variables
No extra arrays or recursion
Space: O(1) ✓

Same space as standard binary search!
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `nums = [1, 3, 5, 7, 9], target = 6`

**Expected Output:** `3` (insert at index 3)

---

**Initial State:**
```
Array:  [1, 3, 5, 7, 9]
Index:   0  1  2  3  4

left = 0
right = 4
target = 6
```

---

**Iteration 1:**
```
Calculate mid:
  mid = 0 + (4-0)/2 = 2
  
Array:  [1, 3, 5, 7, 9]
Index:   0  1  2  3  4
                ↑
               mid

Compare:
  nums[2] = 5
  6 > 5 (target is larger)
  
Action: Search right half
  left = mid + 1 = 3
  
New search space: [7, 9]
  left=3, right=4
```

---

**Iteration 2:**
```
Calculate mid:
  mid = 3 + (4-3)/2 = 3
  
Array:  [1, 3, 5, 7, 9]
Index:   0  1  2  3  4
                   ↑
                  mid

Compare:
  nums[3] = 7
  6 < 7 (target is smaller)
  
Action: Search left half
  right = mid - 1 = 2
  
New search space: empty
  left=3, right=2
```

---

**Loop Ends:**
```
Condition: left <= right?
  3 <= 2? false
  
Loop exits

State:
  left = 3
  right = 2
  
Interpretation:
  nums[2] = 5 < 6 (right pointer)
  nums[3] = 7 > 6 (left pointer)
  
  Insert position: between index 2 and 3
  Which is index 3 (left pointer)

Return: left = 3 ✓
```

---

**Result After Insertion:**
```
Original: [1, 3, 5, 7, 9]
Insert 6 at index 3: [1, 3, 5, 6, 7, 9] ✓

Maintains sorted order!
```

---

### Another Example: Insert at Beginning

**Input:** `nums = [3, 5, 7, 9], target = 1`

```
Iteration 1:
  mid=1, nums[1]=5
  1 < 5 → right=0
  
Iteration 2:
  mid=0, nums[0]=3
  1 < 3 → right=-1
  
Loop ends: left=0, right=-1

Return: 0 (insert at beginning)
Result: [1, 3, 5, 7, 9] ✓
```

---

### Example: Insert at End

**Input:** `nums = [1, 3, 5, 7], target = 10`

```
Iteration 1:
  mid=1, nums[1]=3
  10 > 3 → left=2
  
Iteration 2:
  mid=3, nums[3]=7
  10 > 7 → left=4
  
Loop ends: left=4, right=3

Return: 4 (insert at end)
Result: [1, 3, 5, 7, 10] ✓
```

---

### Pointer Movement Visualization

```
nums = [1, 3, 5, 7, 9], target = 6

Start:
[1, 3, 5, 7, 9]
 ↑        ↑
 L        R

After iter 1: 6 > nums[2]=5
[1, 3, 5, 7, 9]
          ↑  ↑
          L  R

After iter 2: 6 < nums[3]=7
[1, 3, 5, 7, 9]
          ↑ ↑
          L R

Loop ends: L > R
[1, 3, 5, 7, 9]
          ↑
       Insert here!
       (index 3)
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Lines | Clarity | Recommended |
|----------|------|-------|------------|---------|-------------|
| **Binary Search (return left)** | **O(log n)** | **O(1)** | **~15** | **Excellent ✅** | **Yes ✅** |
| Explicit Edge Cases | O(log n) | O(1) | ~22 | Good | Verbose |
| Lower Bound Template | O(log n) | O(1) | ~12 | Medium | General |
| Linear Search | O(n) | O(1) | ~7 | Simple | Too slow ❌ |

**Winner**: **Binary Search with left return** — minimal modification, optimal!

---

## Key Takeaways

1. **Same as binary search, different return** — only change: return left instead of -1
2. **Left pointer is insertion position** — when loop ends, left points where to insert
3. **Right pointer is last smaller element** — nums[right] < target (when not found)
4. **No special edge case handling needed** — binary search handles all cases naturally
5. **O(log n) time** — same efficiency as standard binary search
6. **O(1) space** — only constant variables
7. **Loop condition is <=** — must check single element
8. **Use safe mid calculation** — left + (right-left)/2
9. **Exclude mid when not found** — left=mid+1, right=mid-1
10. **Works for all insertion positions** — beginning, middle, end

---

## Interview Tips

**What to say in an interview:**

> "This problem is very similar to standard binary search, but instead of returning -1 when the target isn't found, I need to return the insertion position. The key insight is that when binary search terminates without finding the target, the left pointer naturally points to where the element should be inserted. This is because the loop maintains the invariant that all elements before left are smaller than target, and all elements after right are larger. When the loop ends with left > right, left is at the first position where we could insert target to maintain sorted order. The implementation is identical to standard binary search except I return left instead of -1. Time complexity is O(log n) since we're halving the search space each iteration, and space complexity is O(1) using only a few variables."

**Key points to mention:**
1. **Modified binary search** — same algorithm, different return
2. **Left pointer is key** — tracks insertion position
3. **Loop invariant** — elements before left < target, after right > target
4. **No special cases needed** — naturally handles beginning/middle/end
5. **Same complexity** — O(log n) time, O(1) space
6. **Return left** — not -1, not right
7. **Sorted array required** — prerequisite for binary search
8. **Maintains sorted order** — insertion at left keeps array sorted

**Common Follow-ups:**
- "Why return left instead of right?" → Left points to first element >= target (insertion spot)
- "What if we need to insert duplicates?" → This problem assumes distinct values
- "Can you do better than O(log n)?" → No, need to find position (Ω(log n) lower bound)
- "What about unsorted array?" → Would need O(n) scan or sort first O(n log n)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Search Insert Position** | Easy | **Binary Search Insertion** | **This problem** |
| Binary Search | Easy | Standard Binary Search | Returns -1 if not found |
| Find First and Last Position | Medium | Binary Search Variants | Find range of target |
| Search in Rotated Sorted Array | Medium | Modified Binary Search | Array rotated |
| First Bad Version | Easy | Binary Search | API calls instead of array |
| Sqrt(x) | Easy | Binary Search | Search answer space |
| Peak Element | Medium | Binary Search | Find local maximum |
| Find Minimum in Rotated Array | Medium | Binary Search | Find rotation point |

**Pattern Progression**:
1. **Standard binary search** — Find exact element
2. **Search insert position** (this problem) — Find element or insertion point
3. **Find range** — Find first/last occurrence
4. **Modified conditions** — Rotated, unsorted sections

---

## Final Pattern Label

✅ **Binary Search with Insertion Position (Lower Bound)**

**Remember:** Same algorithm as **standard binary search** with one change: **return left** instead of -1. When loop ends without finding target, **left pointer** is at the **insertion position**. Why? Because loop maintains invariant: **elements before left < target**, **elements after right > target**. When left > right, left is the **first position** where we can insert target to **maintain sorted order**. No special edge case handling needed—binary search naturally handles **beginning**, **middle**, and **end** insertions. **O(log n) time**, **O(1) space**. Just remember: **return left**!
