# Find Minimum in Rotated Sorted Array

## Problem Description

**Difficulty**: Medium

You are given an array of length `n` which was originally sorted in **ascending order**. It has now been **rotated** between 1 and n times. For example, the array `nums = [1,2,3,4,5,6]` might become:

- `[3,4,5,6,1,2]` if it was rotated 4 times.
- `[1,2,3,4,5,6]` if it was rotated 6 times (back to original).

Notice that rotating the array 4 times moves the last four elements of the array to the beginning. Rotating the array n times produces the original array.

Assuming **all elements** in the rotated sorted array `nums` are **unique**, return the **minimum element** of this array.

A solution that runs in **O(n)** time is trivial, can you write an algorithm that runs in **O(log n)** time?

## Examples

### Example 1:
```
Input: nums = [3,4,5,6,1,2]
Output: 1

Explanation:
  Original: [1,2,3,4,5,6]
  Rotated 4 times: [3,4,5,6,1,2]
  Minimum: 1 (at index 4)
```

### Example 2:
```
Input: nums = [4,5,0,1,2,3]
Output: 0

Explanation:
  Original: [0,1,2,3,4,5]
  Rotated 2 times: [4,5,0,1,2,3]
  Minimum: 0 (at index 2)
```

### Example 3:
```
Input: nums = [4,5,6,7]
Output: 4

Explanation:
  Array not rotated (or rotated 4 times = full rotation)
  Already sorted ascending
  Minimum: first element
```

### Example 4:
```
Input: nums = [2,1]
Output: 1

Explanation:
  Rotated once
  Minimum: 1
```

### Example 5:
```
Input: nums = [1]
Output: 1

Explanation:
  Single element
  Minimum: 1
```

### Example 6:
```
Input: nums = [2,3,4,5,1]
Output: 1

Explanation:
  Rotated once (last element moved to front? No)
  Original: [1,2,3,4,5]
  Rotated 4 times: [2,3,4,5,1]
  Minimum: 1 (at last position)
```

### Example 7:
```
Input: nums = [5,1,2,3,4]
Output: 1

Explanation:
  Rotated once
  Minimum: 1 (at index 1)
```

### Example 8:
```
Input: nums = [11,13,15,17]
Output: 11

Explanation:
  No rotation (sorted ascending)
  Minimum: first element
```

### Example 9:
```
Input: nums = [3,1,2]
Output: 1

Explanation:
  Small array with rotation
  Minimum: 1 (at index 1)
```

### Example 10:
```
Input: nums = [2,3,1]
Output: 1

Explanation:
  Rotated twice
  Minimum: 1 (at last position)
```

## Constraints
- 1 <= nums.length <= 1,000
- -1,000 <= nums[i] <= 1,000
- All elements are **unique**
- Array is rotated between 1 and n times

**Recommended Complexity**: O(log n) time and O(1) space, where n is array length

---

## Pattern Recognition

**Primary Pattern**: **Binary Search on Rotated Sorted Array (Find Pivot/Minimum)**

**Why This Pattern?**
- Array has **sorted property** (two sorted segments)
- Need **O(log n)** time (binary search requirement)
- Finding **minimum** = finding **rotation pivot point**
- Can eliminate half of search space each iteration

**Key Insight**: Two Sorted Segments with Pivot
```
Rotated sorted array has two ascending segments:

Original: [1, 2, 3, 4, 5, 6]
          ↑ (minimum)

Rotated:  [3, 4, 5, 6, 1, 2]
           |sorted|  |sorted|
                     ↑ (minimum = pivot)

Properties:
  1. Left segment: all elements > last element
  2. Right segment: all elements ≤ last element
  3. Minimum element: first element of right segment
  4. Pivot point: where array "breaks" from sorted order

Key observation:
  nums[mid] > nums[right] → minimum in right half
  nums[mid] < nums[right] → minimum in left half (including mid)
```

**The Pivot Point Strategy**:
```
The minimum element is at the pivot (rotation point):

[3, 4, 5, 6, 1, 2]
             ↑ pivot (minimum)
 
[4, 5, 0, 1, 2, 3]
       ↑ pivot (minimum)

[4, 5, 6, 7] (no rotation)
 ↑ pivot = first element

Binary search finds this pivot efficiently!
```

**Example Showing Binary Search Logic**:
```
nums = [4, 5, 6, 7, 0, 1, 2]
       
Step 1: left=0, right=6, mid=3
  nums[mid]=7, nums[right]=2
  7 > 2 → left segment is sorted
  Minimum must be in right half
  left = mid + 1 = 4

Step 2: left=4, right=6, mid=5
  nums[mid]=1, nums[right]=2
  1 < 2 → right segment is sorted
  Minimum could be mid or left of mid
  right = mid = 5

Step 3: left=4, right=5, mid=4
  nums[mid]=0, nums[right]=1
  0 < 1 → right segment sorted
  right = mid = 4

Step 4: left=4, right=4
  Loop ends
  
Return nums[4] = 0 ✓
```

**Why Compare with Right Endpoint**:
```
Compare nums[mid] with nums[right]:

Case 1: nums[mid] > nums[right]
  [3, 4, 5, 6, 1, 2]
           ↑mid    ↑right
  5 > 2 → mid is in left sorted segment
  Minimum must be in (mid, right]
  
Case 2: nums[mid] < nums[right]
  [3, 4, 5, 6, 1, 2]
     ↑mid          ↑right
  4 < 2? No, this is case 1
  
  [3, 4, 5, 6, 1, 2]
              ↑mid ↑right
  1 < 2 → mid could be minimum or in right segment
  Minimum in [left, mid]
  
Case 3: nums[mid] == nums[right]
  Impossible! All elements are unique.
```

**Why This is Optimal**:
```
Linear search:
  Check every element: O(n)
  For n=1,000: 1,000 comparisons ❌

Binary search:
  Halve space each iteration: O(log n)
  For n=1,000: log₂(1,000) ≈ 10 comparisons ✓
  
100× improvement!

Required for O(log n) constraint.
```

**Related Patterns**:
1. **Binary Search** — Core technique
2. **Rotated Array Search** — Similar structure
3. **Find Pivot** — Rotation point detection
4. **Two Sorted Segments** — Split sorted array

---

## Algorithm & Approach

### Core Insight

**Why Binary Search Works on Rotated Array:**
```
Key properties:
  1. Array has two sorted segments
  2. Can determine which segment mid is in
  3. Can eliminate half based on segment
  4. Minimum is at pivot (boundary between segments)
  
Strategy:
  Use nums[right] as reference point
  Compare nums[mid] with nums[right]
  Determine which half contains minimum
```

**The Optimal Strategy**:
```
Key observations:
  1. If nums[mid] > nums[right]: mid in left segment, min in right
  2. If nums[mid] < nums[right]: mid in right segment or is min, min in left including mid
  3. Each iteration eliminates half
  4. Converge to minimum element
```

### Step-by-Step Algorithm

---

#### **Approach 1: Binary Search Comparing with Right - OPTIMAL**

**Core Idea**:
- Binary search with left and right pointers
- Compare mid with right endpoint
- Eliminate half based on which segment mid is in

**Algorithm**
```
findMin(nums):
    left = 0
    right = nums.length - 1
    
    while left < right:
        mid = left + (right - left) / 2
        
        if nums[mid] > nums[right]:
            // Mid in left segment, minimum in right
            left = mid + 1
        else:
            // Mid in right segment or is minimum
            right = mid
    
    return nums[left]
```

**Code Implementation**
```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                // Mid is in the left sorted segment
                // Minimum must be in the right half
                left = mid + 1;
            } else {
                // Mid is in the right sorted segment
                // or mid is the minimum
                // Minimum is in left half including mid
                right = mid;
            }
        }
        
        return nums[left];
    }
}
```

**Example Walkthrough**

Input: `nums = [4,5,6,7,0,1,2]`

| Iteration | left | right | mid | nums[mid] | nums[right] | Comparison | Action |
|-----------|------|-------|-----|-----------|-------------|------------|--------|
| 1 | 0 | 6 | 3 | 7 | 2 | 7 > 2 | left = 4 |
| 2 | 4 | 6 | 5 | 1 | 2 | 1 < 2 | right = 5 |
| 3 | 4 | 5 | 4 | 0 | 1 | 0 < 1 | right = 4 |
| End | 4 | 4 | - | - | - | left == right | Stop |

Return: **nums[4] = 0** ✓

**Complexity Analysis**
- **Time**: O(log n) — Binary search halves space each iteration
- **Space**: O(1) — Only constant variables

---

#### **Approach 2: Binary Search Comparing with Left - ALTERNATIVE**

**Core Idea**: Compare mid with left endpoint instead.

**Algorithm**
```
findMin(nums):
    left = 0
    right = nums.length - 1
    
    while left < right:
        mid = left + (right - left) / 2
        
        if nums[mid] < nums[left]:
            // Mid in right segment, minimum in [left+1, mid]
            right = mid
        else if nums[mid] > nums[right]:
            // Mid in left segment, minimum in (mid, right]
            left = mid + 1
        else:
            // No rotation, array sorted
            return nums[left]
    
    return nums[left]
```

**Code Implementation**
```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        
        // If array is not rotated or has only one element
        if (nums[left] <= nums[right]) {
            return nums[left];
        }
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return nums[left];
    }
}
```

**Key Difference**: 
- Early return for non-rotated case
- Same core logic

**Complexity Analysis**
- **Time**: O(log n)
- **Space**: O(1)

---

#### **Approach 3: Linear Scan - TOO SLOW**

**Core Idea**: Check every element to find minimum.

**Code Implementation**
```java
class Solution {
    public int findMin(int[] nums) {
        int min = nums[0];
        for (int num : nums) {
            min = Math.min(min, num);
        }
        return min;
    }
}
```

**Key Difference**: 
- O(n) time
- Doesn't use sorted property
- Too slow for O(log n) requirement

**Complexity Analysis**
- **Time**: O(n) ❌
- **Space**: O(1)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Code Lines | Uses Sorted Property | Recommended |
|----------|------|-------|------------|----------------------|-------------|
| **Binary Search (vs right)** | **O(log n)** | **O(1)** | **~15** | **Yes ✅** | **Yes ✅** |
| Binary Search (vs left) | O(log n) | O(1) | ~20 | Yes ✅ | Alternative |
| Linear Scan | O(n) | O(1) | ~7 | No | Too slow ❌ |

**Winner**: **Binary Search comparing with right** — simplest logic, optimal time!

### Why Binary Search is Required

```
Problem asks for O(log n) time
Only binary search achieves this!

For n = 1,000:
  Linear: 1,000 comparisons ❌
  Binary: ~10 comparisons ✓
  
100× faster!

Must use binary search to meet requirement.
```

### Why Compare with Right Endpoint

```
Comparing nums[mid] with nums[right] is clearer:

nums[mid] > nums[right]:
  [3, 4, 5, 6, 1, 2]
           ↑mid    ↑right
  6 > 2 → mid in left segment
  Minimum definitely in right half
  left = mid + 1

nums[mid] < nums[right]:
  [3, 4, 5, 6, 1, 2]
              ↑mid ↑right
  1 < 2 → mid in right segment or is minimum
  Minimum in [left, mid]
  right = mid

Clean two-case logic!
No need to check nums[left].
```

### Why left < right Not left <= right

```
Loop condition: left < right

Why not left <= right?
  We're finding index, not checking existence
  
  When left == right:
    Found the minimum position
    Return nums[left]
    
  With left <= right:
    Would need to check and return inside loop
    More complex

left < right is cleaner for finding minimum index.
```

### Why left = mid + 1 vs right = mid

```
When nums[mid] > nums[right]:
  Mid is in left segment
  Mid cannot be minimum (something smaller exists in right)
  left = mid + 1 (exclude mid)

When nums[mid] < nums[right]:
  Mid could be minimum
  Don't exclude mid
  right = mid (include mid)

Asymmetric update ensures convergence!
```

### Why This Works for Non-Rotated Arrays

```
Non-rotated: [1, 2, 3, 4, 5]

Iteration 1:
  mid = 2
  nums[mid] = 3, nums[right] = 5
  3 < 5 → right = mid = 2

Iteration 2:
  left = 0, right = 2, mid = 1
  nums[mid] = 2, nums[right] = 3
  2 < 3 → right = mid = 1

Iteration 3:
  left = 0, right = 1, mid = 0
  nums[mid] = 1, nums[right] = 2
  1 < 2 → right = mid = 0

Loop ends: left = right = 0
Return nums[0] = 1 ✓

Works correctly even without rotation!
```

### Why All Unique Matters

```
Problem states: all elements unique

This guarantees:
  nums[mid] != nums[right]
  Only two cases: > or <
  
If duplicates allowed:
  [2, 2, 2, 0, 2, 2]
         ↑mid    ↑right
  2 == 2 → cannot determine which segment!
  Would need different approach (linear worst case)

Uniqueness enables clean O(log n) solution.
```

---

## Critical Edge Cases & Gotchas

### 1. **No Rotation (Already Sorted)**
```java
Input: nums = [1, 2, 3, 4, 5]
Output: 1
First element is minimum
Binary search still works correctly
```

### 2. **Rotated Once**
```java
Input: nums = [2, 1]
Output: 1
Minimum at last position
```

### 3. **Single Element**
```java
Input: nums = [1]
Output: 1
Only one element, it's the minimum
Loop doesn't execute (left = right = 0)
```

### 4. **Two Elements - Rotated**
```java
Input: nums = [2, 1]
Output: 1
mid = 0, nums[0]=2 > nums[1]=1
left = 1, return nums[1] = 1
```

### 5. **Two Elements - Not Rotated**
```java
Input: nums = [1, 2]
Output: 1
mid = 0, nums[0]=1 < nums[1]=2
right = 0, return nums[0] = 1
```

### 6. **Minimum at End**
```java
Input: nums = [2, 3, 4, 5, 1]
Output: 1
Rotated 4 times
Minimum at last position
```

### 7. **Minimum at Beginning (No Rotation)**
```java
Input: nums = [1, 2, 3, 4, 5]
Output: 1
Not rotated
Minimum at first position
```

### 8. **Large Rotation**
```java
Input: nums = [7, 8, 9, 1, 2, 3, 4, 5, 6]
Output: 1
Rotated 3 times
Minimum at index 3
```

### 9. **Negative Numbers**
```java
Input: nums = [-1, 0, 1, 2, -2]
Wait, this would be: [1, 2, -2, -1, 0]? No...
Original sorted: [-2, -1, 0, 1, 2]
Rotated: [1, 2, -2, -1, 0]
Output: -2
Works with negative numbers
```

### 10. **All Same Except One**
```java
Input: nums = [3, 3, 3, 3, 1, 3]
Wait - problem says all unique!
This case doesn't apply.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Wrong Loop Condition (left <= right)**
```java
// WRONG - uses <= instead of <
while (left <= right) {
    int mid = left + (right - left) / 2;
    // ... logic
}
```

**Why wrong**: Need to return inside loop!

**Dry run failure for nums=[3,1,2]:**
```
Iteration 1: left=0, right=2, mid=1
  nums[1]=1, nums[2]=2
  1 < 2 → right = 1

Iteration 2: left=0, right=1, mid=0
  nums[0]=3, nums[1]=1
  3 > 1 → left = 1

Iteration 3: left=1, right=1, mid=1
  Condition: 1 <= 1? true
  nums[1]=1, nums[1]=1
  Compare 1 with itself? Logic breaks!
  
With left < right:
  After left=1, right=1
  Condition: 1 < 1? false
  Exit loop, return nums[1] = 1 ✓
```

**Fix**: Use left < right
```java
while (left < right) {
    // ...
}
return nums[left];
```

### ❌ **MISTAKE 2: Comparing with Left Instead of Right**
```java
// WRONG - compares with left
if (nums[mid] > nums[left]) {
    left = mid + 1;
} else {
    right = mid;
}
```

**Why wrong**: Logic doesn't work correctly!

**Dry run failure for nums=[3,4,5,1,2]:**
```
Iteration 1: left=0, right=4, mid=2
  nums[2]=5, nums[0]=3
  5 > 3 → left = 3
  
But minimum is at index 3!
We just excluded it by setting left=3
Wrong! ❌

Correct with nums[right]:
  nums[2]=5, nums[4]=2
  5 > 2 → left = 3
  Search [3, 4], finds minimum ✓
```

**Fix**: Compare with right
```java
if (nums[mid] > nums[right]) {
    left = mid + 1;
}
```

### ❌ **MISTAKE 3: Wrong Pointer Update (left = mid)**
```java
// WRONG - doesn't exclude mid
if (nums[mid] > nums[right]) {
    left = mid;  // Should be mid + 1
}
```

**Why wrong**: Infinite loop!

**Dry run failure:**
```
nums = [2, 1]
left = 0, right = 1, mid = 0

nums[0]=2 > nums[1]=1
left = mid = 0 (unchanged!)

Next iteration: same state
Infinite loop ❌
```

**Fix**: Exclude mid
```java
left = mid + 1;
```

### ❌ **MISTAKE 4: Wrong Pointer Update (right = mid - 1)**
```java
// WRONG - excludes mid when it could be minimum
if (nums[mid] < nums[right]) {
    right = mid - 1;  // Should be mid
}
```

**Why wrong**: Might exclude the minimum!

**Dry run failure for nums=[2,1]:**
```
left = 0, right = 1, mid = 0
nums[0]=2 > nums[1]=1
left = 1

left = right = 1
Return nums[1] = 1 ✓ (works here)

But for nums=[3,1,2]:
left=0, right=2, mid=1
nums[1]=1 < nums[2]=2
right = mid - 1 = 0

left=0, right=0
Return nums[0] = 3 ❌

Minimum is 1, not 3!
We excluded it!
```

**Fix**: Include mid
```java
right = mid;
```

### ❌ **MISTAKE 5: Returning Mid Instead of Left**
```java
// WRONG - returns mid
return nums[mid];
```

**Why wrong**: Mid is local variable, out of scope!

**Fix**: Return nums[left] after loop
```java
while (left < right) {
    // ...
}
return nums[left];  // Or nums[right], same when equal
```

### ❌ **MISTAKE 6: Not Handling Single Element**
```java
// WRONG - assumes multiple elements
int mid = left + (right - left) / 2;
// No check for left == right
```

**Why wrong**: Actually this is fine!

**For nums=[5]:**
```
left = 0, right = 0
Condition: 0 < 0? false
Loop doesn't execute
Return nums[0] = 5 ✓

No special handling needed!
```

### ❌ **MISTAKE 7: Using left <= right with Wrong Return**
```java
// WRONG combination
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] > nums[right]) {
        left = mid + 1;
    } else {
        right = mid;
    }
}
return nums[left];  // Might be out of bounds!
```

**Why wrong**: When left > right, accessing nums[left] might be invalid!

**Actually**: With this logic, right will be less than left, so should return nums[right] or nums[left-1]? Confusing!

**Better**: Use left < right and return nums[left]

---

## Complexity Analysis

### Time Complexity: **O(log n)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **While loop iterations** | O(log n) | O(1) | O(log n) |
| **Calculate mid** | O(log n) | O(1) | O(log n) |
| **Compare nums[mid] with nums[right]** | O(log n) | O(1) | O(log n) |
| **Update pointers** | O(log n) | O(1) | O(log n) |
| **Total** | - | - | **O(log n)** |

**Time analysis**:
```
Binary search halves search space each iteration
Search space: n
After k iterations: n / 2^k

Converges when: n / 2^k = 1
Solving: k = log₂(n)

Maximum iterations: ⌈log₂(n)⌉

Examples:
  n = 10: log₂(10) ≈ 3.3 → 4 iterations
  n = 100: log₂(100) ≈ 6.6 → 7 iterations
  n = 1,000: log₂(1,000) ≈ 10 → 10 iterations

Very efficient!
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
No arrays, no recursion stack (iterative)
Space: O(1) ✓

Optimal space usage!
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `nums = [4,5,6,7,0,1,2]`

**Expected Output:** `0`

---

**Initial State:**
```
Array: [4, 5, 6, 7, 0, 1, 2]
Index:  0  1  2  3  4  5  6

Original sorted: [0, 1, 2, 4, 5, 6, 7]
Rotated 4 times: [4, 5, 6, 7, 0, 1, 2]

Two segments:
  Left (sorted): [4, 5, 6, 7]
  Right (sorted): [0, 1, 2]
  
Minimum: 0 (at index 4, pivot point)

left = 0, right = 6
```

---

**Iteration 1:**
```
left = 0, right = 6
mid = 0 + (6-0)/2 = 3

Array: [4, 5, 6, 7, 0, 1, 2]
        ↑        ↑        ↑
       left     mid    right

Compare:
  nums[mid] = 7
  nums[right] = 2
  7 > 2 → mid is in left segment
  
Minimum must be in right half (after mid)

Action: left = mid + 1 = 4

New range: [4, 6]
  Array: [0, 1, 2]
```

---

**Iteration 2:**
```
left = 4, right = 6
mid = 4 + (6-4)/2 = 5

Array: [4, 5, 6, 7, 0, 1, 2]
                    ↑  ↑  ↑
                  left mid right

Compare:
  nums[mid] = 1
  nums[right] = 2
  1 < 2 → mid is in right segment or could be min
  
Minimum in left half including mid

Action: right = mid = 5

New range: [4, 5]
  Array: [0, 1]
```

---

**Iteration 3:**
```
left = 4, right = 5
mid = 4 + (5-4)/2 = 4

Array: [4, 5, 6, 7, 0, 1, 2]
                    ↑  ↑
                  left right
                   mid

Compare:
  nums[mid] = 0
  nums[right] = 1
  0 < 1 → mid could be minimum
  
Action: right = mid = 4

New range: [4, 4]
```

---

**Loop Ends:**
```
left = 4, right = 4
Condition: 4 < 4? false

Exit loop

Return: nums[left] = nums[4] = 0 ✓
```

---

**Summary:**
```
Total iterations: 3
Search space: 7 → 3 → 2 → 1
Found minimum in log₂(7) ≈ 3 iterations!
```

---

### Another Example: No Rotation

**Input:** `nums = [1, 2, 3, 4, 5]`

```
Iteration 1:
  left=0, right=4, mid=2
  nums[2]=3, nums[4]=5
  3 < 5 → right = 2

Iteration 2:
  left=0, right=2, mid=1
  nums[1]=2, nums[2]=3
  2 < 3 → right = 1

Iteration 3:
  left=0, right=1, mid=0
  nums[0]=1, nums[1]=2
  1 < 2 → right = 0

Loop ends: left = right = 0
Return nums[0] = 1 ✓

Works correctly even without rotation!
```

---

### Visualization of Segments

```
nums = [4, 5, 6, 7, 0, 1, 2]

Left segment (sorted): [4, 5, 6, 7]
  All elements > 2 (last element)
  
Right segment (sorted): [0, 1, 2]
  All elements ≤ 2 (last element)
  
Pivot/Minimum: 0 (first of right segment)

Binary search finds where transition happens!
```

---

### Decision Tree

```
nums = [4, 5, 6, 7, 0, 1, 2]

                mid=3 (7>2)
               /           \
        left=[4,6]       right=[0,2] ✓
              
           mid=5 (1<2)
          /          \
     left=[4,4] ✓   right=[6,6]
     
     mid=4 (0<1)
    /          \
left=[4,4] ✓  right=[5,5]

Found minimum at index 4!
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Lines | Clarity | Recommended |
|----------|------|-------|------------|---------|-------------|
| **Binary Search (vs right)** | **O(log n)** | **O(1)** | **~15** | **Excellent ✅** | **Yes ✅** |
| Binary Search (vs left) | O(log n) | O(1) | ~20 | Good | Alternative |
| Linear Scan | O(n) | O(1) | ~7 | Simple | Too slow ❌ |

**Winner**: **Binary Search comparing with right** — cleanest logic, optimal!

---

## Key Takeaways

1. **Binary search on rotated array** — two sorted segments with pivot
2. **Compare mid with right endpoint** — determines which segment
3. **nums[mid] > nums[right]** → mid in left segment, search right (left = mid+1)
4. **nums[mid] < nums[right]** → mid in right segment or is min, search left including mid (right = mid)
5. **Loop condition: left < right** — not <=, for index finding
6. **Asymmetric updates** — left = mid+1, right = mid
7. **Return nums[left]** — when left == right, found minimum
8. **Works for non-rotated** — handles sorted array correctly
9. **All elements unique** — no mid == right case
10. **O(log n) time, O(1) space** — optimal solution

---

## Interview Tips

**What to say in an interview:**

> "This problem involves finding the minimum element in a rotated sorted array. The key insight is that a rotated sorted array consists of two sorted segments, and the minimum element is at the pivot point where the array transitions from the left segment to the right segment. I'll use binary search comparing the middle element with the right endpoint. If nums[mid] is greater than nums[right], the mid is in the left sorted segment, so the minimum must be in the right half and I move left to mid+1. If nums[mid] is less than nums[right], the mid is in the right segment or could be the minimum itself, so I keep mid in the search space by setting right to mid. I use the loop condition left < right, and when they converge, nums[left] is the minimum. This approach runs in O(log n) time with O(1) space."

**Key points to mention:**
1. **Two sorted segments** — rotated array has pivot point
2. **Compare with right endpoint** — nums[mid] vs nums[right]
3. **Two cases**: mid > right (search right), mid < right (search left including mid)
4. **Asymmetric updates** — left = mid+1 excludes mid, right = mid includes mid
5. **Loop condition left < right** — for finding index
6. **Return nums[left]** — when converged
7. **O(log n) time** — binary search requirement
8. **Handles non-rotated** — works when already sorted

**Common Follow-ups:**
- "What if array has duplicates?" → Need to handle nums[mid] == nums[right], worst case O(n)
- "Why compare with right not left?" → Right comparison has cleaner two-case logic
- "What if you need to find maximum?" → Similar logic, compare differently
- "Can you do better than O(log n)?" → No, Ω(log n) lower bound for this problem
- "How do you handle empty array?" → Check constraints (n >= 1, not applicable)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Find Minimum in Rotated Sorted Array** | Medium | **Binary Search on Rotated Array** | **This problem** |
| Find Minimum in Rotated Sorted Array II | Hard | Binary Search with Duplicates | Handles duplicate elements |
| Search in Rotated Sorted Array | Medium | Binary Search on Rotated Array | Find target value, not minimum |
| Search in Rotated Sorted Array II | Medium | Binary Search with Duplicates | Search with duplicates allowed |
| Find Peak Element | Medium | Binary Search | Find local maximum |
| Peak Index in Mountain Array | Medium | Binary Search | Find peak in mountain array |
| Kth Smallest Element | Medium | Binary Search / Quickselect | Find kth element |
| Binary Search | Easy | Standard Binary Search | Basic binary search on sorted array |

**Pattern Progression**:
1. **Standard binary search** — Find element in sorted array
2. **Find Minimum in Rotated Array** (this problem) — Find pivot in rotated array
3. **Search in Rotated Array** — Find target in rotated array
4. **With Duplicates** — Handle non-unique elements

---

## Final Pattern Label

✅ **Binary Search on Rotated Sorted Array (Find Pivot/Minimum)**

**Remember:** This is **binary search on rotated sorted array** to find the **minimum element** which is at the **pivot point**. The array has **two sorted segments** separated by rotation. Use **nums[mid] vs nums[right]** comparison: if **nums[mid] > nums[right]**, mid is in **left segment**, search **right half** (left = mid+1); if **nums[mid] < nums[right]**, mid is in **right segment** or is minimum, search **left including mid** (right = mid). Use loop condition **left < right** (not <=) for index finding. **Asymmetric updates** ensure convergence: exclude mid when in left segment, include mid when in right. When **left == right**, found minimum position, return **nums[left]**. Works for **non-rotated arrays** (already sorted) and requires **O(log n) time** with **O(1) space**. All elements **unique** simplifies logic (no mid == right case). Key is recognizing the **two-segment structure** and using **right endpoint** as reference to determine which segment contains minimum!
