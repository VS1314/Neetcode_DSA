# Search in Rotated Sorted Array

## Problem Description

**Difficulty**: Medium

You are given an array of length `n` which was originally sorted in **ascending order**. It has now been **rotated** between 1 and n times. For example, the array `nums = [1,2,3,4,5,6]` might become:

- `[3,4,5,6,1,2]` if it was rotated 4 times.
- `[1,2,3,4,5,6]` if it was rotated 6 times (back to original).

Given the rotated sorted array `nums` and an integer `target`, return the **index** of `target` within `nums`, or **-1** if it is not present.

You may assume all elements in the sorted rotated array `nums` are **unique**.

A solution that runs in **O(n)** time is trivial, can you write an algorithm that runs in **O(log n) time**?

## Examples

### Example 1:
```
Input: nums = [3,4,5,6,1,2], target = 1
Output: 4

Explanation:
  Target 1 is at index 4
  Array is rotated sorted
```

### Example 2:
```
Input: nums = [3,5,6,0,1,2], target = 4
Output: -1

Explanation:
  Target 4 is not in array
  Return -1
```

### Example 3:
```
Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4

Explanation:
  Target 0 is at index 4
  0 is the minimum (pivot point)
```

### Example 4:
```
Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1

Explanation:
  Target 3 not present
  Return -1
```

### Example 5:
```
Input: nums = [1], target = 1
Output: 0

Explanation:
  Single element array
  Target found at index 0
```

### Example 6:
```
Input: nums = [1], target = 0
Output: -1

Explanation:
  Single element, target not found
```

### Example 7:
```
Input: nums = [1,3], target = 3
Output: 1

Explanation:
  Two elements, no rotation
  Target at index 1
```

### Example 8:
```
Input: nums = [3,1], target = 1
Output: 1

Explanation:
  Two elements, rotated once
  Target at index 1
```

### Example 9:
```
Input: nums = [5,1,3], target = 5
Output: 0

Explanation:
  Target at first position
```

### Example 10:
```
Input: nums = [4,5,6,7,8,1,2,3], target = 8
Output: 4

Explanation:
  Large rotated array
  Target in left sorted segment
```

### Example 11:
```
Input: nums = [4,5,6,7,0,1,2], target = 5
Output: 1

Explanation:
  Target in left sorted segment
```

### Example 12:
```
Input: nums = [4,5,6,7,0,1,2], target = 1
Output: 5

Explanation:
  Target in right sorted segment
```

## Constraints
- 1 <= nums.length <= 1,000
- -1,000 <= nums[i] <= 1,000
- -1,000 <= target <= 1,000
- All values of `nums` are **unique**
- `nums` is an ascending array that is possibly rotated

**Recommended Complexity**: O(log n) time and O(1) space, where n is array length

---

## Pattern Recognition

**Primary Pattern**: **Binary Search on Rotated Sorted Array (Find Target)**

**Why This Pattern?**
- Array has **sorted property** (two sorted segments)
- Need **O(log n)** time (binary search requirement)
- Finding **target index** in rotated array
- Can eliminate half of search space each iteration

**Key Insight**: At Least One Half is Always Sorted
```
Rotated sorted array has two ascending segments:

Original: [0, 1, 2, 3, 4, 5, 6]
Rotated:  [4, 5, 6, 0, 1, 2, 3]
           |sorted| |sorted|
                ↑ pivot

Key observation:
  When you pick mid point, at least ONE of:
    [left, mid] or [mid, right]
  is SORTED (no rotation point in that half)

If [left, mid] sorted:
  Check if target in [left, mid] range
  If yes, search left; else search right

If [mid, right] sorted:
  Check if target in [mid, right] range
  If yes, search right; else search left
```

**The Two Sorted Segments Strategy**:
```
nums = [4, 5, 6, 7, 0, 1, 2], target = 0

Step 1: mid = 3, nums[mid] = 7
  Left half [4,5,6,7]: SORTED (4 < 7)
  Right half [7,0,1,2]: NOT sorted
  
  Is target in sorted left [4, 7]? No
  Search right half

Step 2: mid = 5, nums[mid] = 1
  Left half [0,1]: NOT sorted
  Right half [1,2]: SORTED (1 < 2)
  
  Is target in sorted right [1, 2]? No
  Search left half

Step 3: Found at index 4!
```

**Decision Logic**:
```
At each step, determine which half is sorted:

1. Check if LEFT half [left, mid] is sorted:
   if nums[left] <= nums[mid]:
     Left is sorted
     
     Check if target in [nums[left], nums[mid]]:
       if nums[left] <= target < nums[mid]:
         Search left half
       else:
         Search right half
   
2. Otherwise, RIGHT half [mid, right] is sorted:
     Right is sorted
     
     Check if target in [nums[mid], nums[right]]:
       if nums[mid] < target <= nums[right]:
         Search right half
       else:
         Search left half
```

**Why At Least One Half is Sorted**:
```
Rotation creates ONE pivot point (deflection)

Case 1: Pivot in right half
  [4, 5, 6, 7 | 0, 1, 2]
   ←sorted→     ↑pivot
  Left half has no pivot → sorted!

Case 2: Pivot in left half (or no rotation)
  [6, 7 | 0, 1, 2, 3, 4]
      ↑pivot  ←sorted→
  Right half has no pivot → sorted!

Cannot have pivot in BOTH halves!
So at least one is always sorted.
```

**Example Showing Decision Process**:
```
nums = [4, 5, 6, 7, 0, 1, 2], target = 1

Iteration 1: left=0, right=6, mid=3
  nums[left]=4, nums[mid]=7, nums[right]=2
  4 <= 7 → left half [4,5,6,7] is SORTED
  
  Is target 1 in [4, 7)? No
  Search right: left = mid + 1 = 4

Iteration 2: left=4, right=6, mid=5
  nums[left]=0, nums[mid]=1, nums[right]=2
  0 <= 1 → left half [0,1] is SORTED
  
  Is target 1 in [0, 1)? No (need 1 <= 1 < 1? No)
  Actually: 0 <= 1 <= 1? YES!
  Search left: right = mid = 5

Wait, let me reconsider the condition...
Actually, for target to be in sorted segment [left, mid]:
  nums[left] <= target <= nums[mid]
  
But we compare target < nums[mid] to exclude mid in some cases?
No, let's be precise about the algorithm.

Standard approach:
  if nums[left] <= nums[mid]: # left sorted
    if nums[left] <= target < nums[mid]:
      right = mid - 1
    else:
      left = mid + 1
  else: # right sorted
    if nums[mid] < target <= nums[right]:
      left = mid + 1
    else:
      right = mid - 1

With this, if target == nums[mid], we find it!
```

**Why This is Optimal**:
```
Linear search:
  Check every element: O(n)
  For n=1,000: 1,000 comparisons ❌

Binary search on rotated array:
  Halve space each iteration: O(log n)
  For n=1,000: log₂(1,000) ≈ 10 comparisons ✓
  
100× improvement!

Required for O(log n) constraint.
```

**Related Patterns**:
1. **Binary Search** — Core technique
2. **Rotated Array Search** — This problem
3. **Find Minimum in Rotated Array** — Similar structure
4. **Two Sorted Segments** — Split sorted array

---

## Algorithm & Approach

### Core Insight

**Why Binary Search Works on Rotated Array:**
```
Key properties:
  1. Array has two sorted segments
  2. At each mid point, at least one half is sorted
  3. Can determine which half is sorted
  4. Check if target is in sorted half
  5. Eliminate half based on target location
```

**The Optimal Strategy**:
```
Key observations:
  1. Determine which half is sorted (left or right)
  2. Check if target is in the sorted half's range
  3. If yes, search sorted half; else search other half
  4. Each iteration eliminates half
  5. Check mid == target before updating pointers
```

### Step-by-Step Algorithm

---

#### **Approach 1: Binary Search with Sorted Half Detection - OPTIMAL**

**Core Idea**:
- Binary search with left and right pointers
- At each step, check if mid is target
- Determine which half is sorted
- Check if target is in sorted half's range
- Search accordingly

**Algorithm**
```
search(nums, target):
    left = 0
    right = nums.length - 1
    
    while left <= right:
        mid = left + (right - left) / 2
        
        // Found target
        if nums[mid] == target:
            return mid
        
        // Determine which half is sorted
        if nums[left] <= nums[mid]:
            // Left half is sorted
            if nums[left] <= target < nums[mid]:
                right = mid - 1  // Target in left
            else:
                left = mid + 1   // Target in right
        else:
            // Right half is sorted
            if nums[mid] < target <= nums[right]:
                left = mid + 1   // Target in right
            else:
                right = mid - 1  // Target in left
    
    return -1  // Not found
```

**Code Implementation**
```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Found the target
            if (nums[mid] == target) {
                return mid;
            }
            
            // Determine which half is sorted
            if (nums[left] <= nums[mid]) {
                // Left half [left, mid] is sorted
                if (nums[left] <= target && target < nums[mid]) {
                    // Target is in sorted left half
                    right = mid - 1;
                } else {
                    // Target is in right half
                    left = mid + 1;
                }
            } else {
                // Right half [mid, right] is sorted
                if (nums[mid] < target && target <= nums[right]) {
                    // Target is in sorted right half
                    left = mid + 1;
                } else {
                    // Target is in left half
                    right = mid - 1;
                }
            }
        }
        
        return -1; // Target not found
    }
}
```

**Example Walkthrough**

Input: `nums = [4,5,6,7,0,1,2]`, `target = 1`

| Iteration | left | right | mid | nums[mid] | nums[left] | nums[right] | Which Sorted? | Target in Range? | Action |
|-----------|------|-------|-----|-----------|------------|-------------|---------------|------------------|--------|
| 1 | 0 | 6 | 3 | 7 | 4 | 2 | Left [4,7] | 1 in [4,7)? No | left=4 |
| 2 | 4 | 6 | 5 | 1 | 0 | 2 | Left [0,1] | 1 in [0,1)? No | left=6 |
| 3 | 6 | 6 | 6 | 2 | 2 | 2 | Left [2,2] | 1 in [2,2)? No | left=7 |
| End | 7 | 6 | - | - | - | - | left > right | - | Return -1 |

Wait, this doesn't look right. Let me trace more carefully:

```
nums = [4,5,6,7,0,1,2], target = 1

Iteration 1:
  left=0, right=6, mid=3
  nums[mid]=7, nums[left]=4, nums[right]=2
  
  Check mid == target: 7 == 1? No
  
  Check if left sorted: nums[left]=4 <= nums[mid]=7? Yes
  Left half [4,5,6,7] is sorted
  
  Is target in left [4, 7)? 
    4 <= 1 < 7? No
  
  Search right: left = 4

Iteration 2:
  left=4, right=6, mid=5
  nums[mid]=1, nums[left]=0, nums[right]=2
  
  Check mid == target: 1 == 1? YES!
  
  Return 5 ✓
```

Return: **5** ✓

**Complexity Analysis**
- **Time**: O(log n) — Binary search halves space each iteration
- **Space**: O(1) — Only constant variables

---

#### **Approach 2: Find Pivot Then Binary Search - TWO PHASE**

**Core Idea**: 
- First, find the rotation pivot (minimum element index)
- Then, perform standard binary search on appropriate segment

**Algorithm**
```
search(nums, target):
    // Phase 1: Find pivot (minimum index)
    pivot = findPivot(nums)
    
    // Phase 2: Determine which segment to search
    if target >= nums[0]:
        // Search left segment
        return binarySearch(nums, 0, pivot - 1, target)
    else:
        // Search right segment
        return binarySearch(nums, pivot, nums.length - 1, target)
```

**Code Implementation**
```java
class Solution {
    public int search(int[] nums, int target) {
        int n = nums.length;
        
        // Find the pivot (rotation point)
        int pivot = findPivot(nums);
        
        // Determine which sorted segment to search
        if (pivot == 0) {
            // No rotation
            return binarySearch(nums, 0, n - 1, target);
        }
        
        if (target >= nums[0]) {
            // Target in left segment
            return binarySearch(nums, 0, pivot - 1, target);
        } else {
            // Target in right segment
            return binarySearch(nums, pivot, n - 1, target);
        }
    }
    
    private int findPivot(int[] nums) {
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    private int binarySearch(int[] nums, int left, int right, int target) {
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

**Key Difference**: 
- Two separate binary searches
- More code but clearer logic
- Still O(log n) overall

**Complexity Analysis**
- **Time**: O(log n) — Two O(log n) operations
- **Space**: O(1)

---

#### **Approach 3: Linear Search - TOO SLOW**

**Core Idea**: Check every element sequentially.

**Code Implementation**
```java
class Solution {
    public int search(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
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
| **Binary Search (1 pass)** | **O(log n)** | **O(1)** | **~25** | **Yes ✅** | **Yes ✅** |
| Find Pivot + Binary Search | O(log n) | O(1) | ~40 | Yes ✅ | Alternative |
| Linear Scan | O(n) | O(1) | ~7 | No | Too slow ❌ |

**Winner**: **Single pass binary search** — optimal time, cleaner than two-phase!

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

### Why One Pass Better Than Two Pass

```
One pass (Approach 1):
  Single binary search with sorted half detection
  Cleaner logic, fewer lines
  O(log n)

Two pass (Approach 2):
  Find pivot: O(log n)
  Search segment: O(log n)
  Total: O(log n) but with constant overhead
  More code

Both are O(log n), but one pass is simpler!
```

### Why Check Which Half is Sorted

```
At each mid point, we can determine sorted half:

nums[left] <= nums[mid]:
  Left half is sorted (no rotation point)
  Can check if target in range [nums[left], nums[mid]]
  
Otherwise:
  Right half is sorted
  Can check if target in range [nums[mid], nums[right]]

This check allows us to:
  1. Know the range of sorted half precisely
  2. Determine if target is in that range
  3. Eliminate the correct half
```

### Why Use left <= right Not left < right

```
Loop condition: left <= right

Why not left < right?
  We're checking nums[mid] == target inside loop
  Need to check when left == right (single element)
  
  Example: [5], target = 5
    left = 0, right = 0
    Condition: 0 <= 0? Yes
    mid = 0, nums[0] == 5? Yes
    Return 0 ✓
    
  With left < right:
    Condition: 0 < 0? No
    Loop doesn't execute
    Return -1 ❌

Must use left <= right for target search!
```

### Why Check mid == target First

```
Before determining which half is sorted:
  Check if nums[mid] == target
  
This is the base case!
If we found target, return immediately.

Without this check:
  We'd keep searching even after finding target
  Logic would be much more complex
  
Always check equality first in search problems!
```

### Why Target Range Check is Different

```
For LEFT sorted half:
  nums[left] <= target < nums[mid]
  
  Why < not <=?
    We already checked mid == target
    If target == mid, we returned
    So target must be < mid if in left half
    
For RIGHT sorted half:
  nums[mid] < target <= nums[right]
  
  Same logic: target already checked against mid
  Must be > mid if in right half

Asymmetric bounds avoid rechecking mid!
```

### Why This Works for Non-Rotated Arrays

```
Non-rotated: [1, 2, 3, 4, 5], target = 3

Iteration 1:
  left=0, right=4, mid=2
  nums[mid]=3 == target? YES!
  Return 2 ✓
  
Even if we didn't find immediately:
  nums[left]=1 <= nums[mid]=3? Yes
  Left sorted [1,2,3]
  Is 3 in [1,3)? No (need < 3)
  Actually we'd find it when mid==3
  
Works correctly for non-rotated arrays!
```

---

## Critical Edge Cases & Gotchas

### 1. **Target at Mid Point**
```java
Input: nums = [4,5,6,7,0,1,2], target = 7
Output: 3
Must check nums[mid] == target first!
```

### 2. **Target Not Present**
```java
Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1
Search exhausts all possibilities
Return -1
```

### 3. **Single Element - Found**
```java
Input: nums = [1], target = 1
Output: 0
left=0, right=0, mid=0
nums[0] == 1? Yes, return 0
```

### 4. **Single Element - Not Found**
```java
Input: nums = [1], target = 0
Output: -1
left=0, right=0, mid=0
nums[0] == 0? No
Continue, left > right
Return -1
```

### 5. **Two Elements - No Rotation**
```java
Input: nums = [1,3], target = 3
Output: 1
Works with standard binary search
```

### 6. **Two Elements - Rotated**
```java
Input: nums = [3,1], target = 1
Output: 1
Right half [1] is sorted
```

### 7. **Target at First Position**
```java
Input: nums = [5,1,2,3,4], target = 5
Output: 0
Found at beginning
```

### 8. **Target at Last Position**
```java
Input: nums = [2,3,4,5,1], target = 1
Output: 4
Found at end
```

### 9. **No Rotation (Sorted)**
```java
Input: nums = [1,2,3,4,5], target = 3
Output: 2
Standard binary search
Left half always sorted
```

### 10. **All Elements on One Side of Target**
```java
Input: nums = [4,5,6,7,8], target = 1
Output: -1
Target smaller than all elements
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Forgetting to Check mid == target**
```java
// WRONG - doesn't check mid
while (left <= right) {
    int mid = left + (right - left) / 2;
    
    if (nums[left] <= nums[mid]) {
        // ... no check for nums[mid] == target
    }
}
```

**Why wrong**: Will never return the answer!

**Dry run failure for nums=[4,5,6,7,0,1,2], target=7:**
```
Iteration 1: left=0, right=6, mid=3
  nums[mid]=7 (this is our target!)
  
  But we don't check, we continue:
  nums[left]=4 <= nums[mid]=7? Yes
  4 <= 7 < 7? No
  left = 4
  
  We missed the target! ❌

Correct approach:
  Check nums[mid] == target FIRST
  Return 3 immediately ✓
```

**Fix**: Always check mid first
```java
if (nums[mid] == target) {
    return mid;
}
```

### ❌ **MISTAKE 2: Wrong Sorted Half Detection**
```java
// WRONG - uses < instead of <=
if (nums[left] < nums[mid]) {
    // left sorted
}
```

**Why wrong**: Fails when left == mid (single element range)!

**Dry run failure for nums=[1], target=1:**
```
left=0, right=0, mid=0
nums[left]=1, nums[mid]=1

Check: 1 < 1? No

Goes to else (thinks right sorted)
But there is no right!

Logic breaks ❌

Correct with <=:
  1 <= 1? Yes
  Left sorted (just one element)
  Works correctly ✓
```

**Fix**: Use <=
```java
if (nums[left] <= nums[mid]) {
    // left sorted
}
```

### ❌ **MISTAKE 3: Wrong Target Range Check**
```java
// WRONG - uses <= on both bounds
if (nums[left] <= target <= nums[mid]) {
    // target in left
}
```

**Why wrong**: We already checked mid == target!

**Dry run failure for nums=[4,5,6,7,0,1,2], target=7:**
```
mid=3, nums[mid]=7

If we use <=:
  4 <= 7 <= 7? Yes
  Search left: right = mid - 1 = 2
  
But target IS at mid=3!
We already returned it, so this won't execute.

However, if we forgot to check mid == target:
  We'd exclude the target!

Better to use < to be explicit:
  If target == mid, we already found it
  If target < mid, it's in left
```

**Fix**: Use < not <=
```java
if (nums[left] <= target && target < nums[mid]) {
    right = mid - 1;
}
```

### ❌ **MISTAKE 4: Using left < right**
```java
// WRONG - uses < instead of <=
while (left < right) {
    int mid = left + (right - left) / 2;
    if (nums[mid] == target) return mid;
    // ...
}
```

**Why wrong**: Doesn't check last element when left == right!

**Dry run failure for nums=[5], target=5:**
```
left=0, right=0

Condition: 0 < 0? No

Loop doesn't execute!

Never checked nums[0] == 5

Return -1 ❌

Correct with <=:
  0 <= 0? Yes
  mid = 0
  nums[0] == 5? Yes
  Return 0 ✓
```

**Fix**: Use <=
```java
while (left <= right) {
    // ...
}
```

### ❌ **MISTAKE 5: Wrong Pointer Updates**
```java
// WRONG - uses mid instead of mid-1/mid+1
if (nums[left] <= target && target < nums[mid]) {
    right = mid;  // Should be mid - 1
} else {
    left = mid;   // Should be mid + 1
}
```

**Why wrong**: Infinite loop!

**Dry run failure:**
```
nums = [1,3], target = 3
left=0, right=1, mid=0

nums[0]=1 == target? No

nums[left]=1 <= nums[mid]=1? Yes
1 <= 3 < 1? No
left = mid = 0 (unchanged!)

Infinite loop ❌

Correct:
  left = mid + 1 = 1
  Next: left=1, right=1
  nums[1] == 3? Yes ✓
```

**Fix**: Exclude mid with +1/-1
```java
right = mid - 1;
left = mid + 1;
```

### ❌ **MISTAKE 6: Forgetting to Return -1**
```java
// WRONG - no return after loop
while (left <= right) {
    // ... search logic
}
// No return statement here!
```

**Why wrong**: Function must return something!

**Fix**: Return -1 when not found
```java
while (left <= right) {
    // ... search logic
}
return -1;  // Target not found
```

### ❌ **MISTAKE 7: Wrong Order of Conditions**
```java
// WRONG - checks sorted before equality
if (nums[left] <= nums[mid]) {
    if (nums[left] <= target && target < nums[mid]) {
        right = mid - 1;
    } else if (nums[mid] == target) {
        return mid;
    } else {
        left = mid + 1;
    }
}
```

**Why wrong**: Messy logic, harder to reason about!

**Better**: Check equality FIRST
```java
if (nums[mid] == target) {
    return mid;
}

// Then determine sorted half
if (nums[left] <= nums[mid]) {
    // ...
}
```

---

## Complexity Analysis

### Time Complexity: **O(log n)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **While loop iterations** | O(log n) | O(1) | O(log n) |
| **Calculate mid** | O(log n) | O(1) | O(log n) |
| **Check mid == target** | O(log n) | O(1) | O(log n) |
| **Determine sorted half** | O(log n) | O(1) | O(log n) |
| **Check target in range** | O(log n) | O(1) | O(log n) |
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

**Input:** `nums = [4,5,6,7,0,1,2]`, `target = 1`

**Expected Output:** `5`

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
  
Target: 1 (should be at index 5)

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

Check: nums[mid] == target?
  nums[3] = 7 == 1? No

Determine sorted half:
  nums[left]=4 <= nums[mid]=7? Yes
  Left half [4,5,6,7] is SORTED

Check target in sorted left:
  nums[left] <= target < nums[mid]?
  4 <= 1 < 7? No (1 < 4)
  
Target NOT in sorted left half
Search right half

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

Check: nums[mid] == target?
  nums[5] = 1 == 1? YES! ✓

Return: 5
```

---

**Summary:**
```
Total iterations: 2
Search space: 7 → 3 → found!
Found target in log₂(7) ≈ 3 iterations (stopped early at 2)
```

---

### Another Example: Target Not Found

**Input:** `nums = [4,5,6,7,0,1,2]`, `target = 3`

```
Iteration 1: left=0, right=6, mid=3
  nums[mid]=7 == 3? No
  Left sorted [4,5,6,7]
  3 in [4,7)? No
  left = 4

Iteration 2: left=4, right=6, mid=5
  nums[mid]=1 == 3? No
  Left sorted [0,1]
  3 in [0,1)? No
  left = 6

Iteration 3: left=6, right=6, mid=6
  nums[mid]=2 == 3? No
  Left sorted [2,2]
  3 in [2,2)? No
  left = 7

Loop ends: left=7 > right=6
Return -1 (not found) ✓
```

---

### Visualization of Sorted Half Detection

```
nums = [4, 5, 6, 7, 0, 1, 2]
       
When mid = 3:
  Left:  [4, 5, 6, 7]  ← SORTED (4 < 7)
  Right: [7, 0, 1, 2]  ← NOT sorted (7 > 2)

When mid = 5:
  Left:  [0, 1]        ← SORTED (0 < 1)
  Right: [1, 2]        ← SORTED (1 < 2)

When mid = 1:
  Left:  [4, 5]        ← SORTED (4 < 5)
  Right: [5, 6, 7, 0, 1, 2]  ← NOT sorted

At least ONE half is always sorted!
```

---

### Decision Tree

```
nums = [4, 5, 6, 7, 0, 1, 2], target = 1

                    mid=3 (7≠1)
                    Left sorted [4,7]
                    1 in [4,7)? No
                   /              
            search right         
              [4,6]              
                                 
           mid=5 (1==1) ✓        
              Found!             
           Return 5              
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Lines | Clarity | Recommended |
|----------|------|-------|------------|---------|-------------|
| **Binary Search (1 pass)** | **O(log n)** | **O(1)** | **~25** | **Excellent ✅** | **Yes ✅** |
| Find Pivot + Binary Search | O(log n) | O(1) | ~40 | Good | Alternative |
| Linear Scan | O(n) | O(1) | ~7 | Simple | Too slow ❌ |

**Winner**: **Single pass binary search** — cleanest, optimal!

---

## Key Takeaways

1. **Binary search on rotated array** — detect sorted half
2. **Check mid == target first** — base case for finding target
3. **Determine sorted half**: `nums[left] <= nums[mid]` → left sorted, else right sorted
4. **Check if target in sorted half's range** — precise bounds check
5. **If target in sorted half**: search there; **else**: search other half
6. **Use left <= right** — must check single element case
7. **Exclude mid after checking**: `left = mid + 1` or `right = mid - 1`
8. **Return -1 when not found** — after loop exhausts all positions
9. **At least one half always sorted** — key insight for rotated arrays
10. **O(log n) time, O(1) space** — optimal solution

---

## Interview Tips

**What to say in an interview:**

> "This problem involves searching for a target in a rotated sorted array. The key insight is that even though the array is rotated, at least one half of the array (either left or right of mid) is always sorted. I'll use binary search and at each step, I'll first check if the middle element equals the target. If not, I'll determine which half is sorted by comparing nums[left] with nums[mid]. If the left half is sorted and the target falls within its range, I'll search there; otherwise I'll search the right half. The same logic applies if the right half is sorted. I use left <= right as the loop condition to handle all cases including single elements, and I update the pointers by excluding mid (mid+1 or mid-1) since we've already checked it. If we exhaust the search space without finding the target, we return -1. This approach runs in O(log n) time with O(1) space."

**Key points to mention:**
1. **Rotated array has two sorted segments** — pivot creates deflection
2. **At least one half is always sorted** — crucial insight
3. **Check mid == target first** — base case
4. **Determine sorted half**: compare nums[left] with nums[mid]
5. **Target range check** — is target within sorted half's bounds?
6. **Search sorted half if target in range** — else search other half
7. **Use left <= right** — includes single element case
8. **Return -1 if not found** — after loop ends

**Common Follow-ups:**
- "What if array has duplicates?" → Need to handle nums[left] == nums[mid] == nums[right], worst case O(n)
- "Can you do it without checking which half is sorted?" → This is the optimal approach
- "What's the difference from finding minimum?" → This searches for target, minimum problem finds pivot
- "Can you use recursive approach?" → Yes, but iterative is better (no stack space)
- "How do you handle empty array?" → Check constraints (n >= 1, not applicable)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Search in Rotated Sorted Array** | Medium | **Binary Search on Rotated Array** | **This problem** |
| Search in Rotated Sorted Array II | Medium | Binary Search with Duplicates | Handles duplicate elements |
| Find Minimum in Rotated Sorted Array | Medium | Binary Search on Rotated Array | Find minimum, not search target |
| Find Minimum in Rotated Sorted Array II | Hard | Binary Search with Duplicates | Find minimum with duplicates |
| Find Peak Element | Medium | Binary Search | Find local maximum |
| Binary Search | Easy | Standard Binary Search | Basic binary search on sorted array |
| Search in a 2D Matrix | Medium | Binary Search | 2D matrix as virtual 1D array |
| Search Insert Position | Easy | Binary Search | Find position to insert |

**Pattern Progression**:
1. **Standard binary search** — Find element in sorted array
2. **Search in Rotated Array** (this problem) — Find target in rotated array
3. **Find Minimum in Rotated Array** — Find pivot in rotated array
4. **With Duplicates** — Handle non-unique elements

---

## Final Pattern Label

✅ **Binary Search on Rotated Sorted Array (Search Target)**

**Remember:** This is **binary search on rotated sorted array** to **find target index**. The array has **two sorted segments** separated by rotation pivot. At each step, **check if nums[mid] == target first** (base case). Then **determine which half is sorted**: if **nums[left] <= nums[mid]**, left half is sorted; otherwise right half is sorted. **Check if target is in sorted half's range**: for left sorted, check `nums[left] <= target < nums[mid]`; for right sorted, check `nums[mid] < target <= nums[right]`. If target in sorted half, **search there**; else **search other half**. Use **left <= right** (not <) to handle single element. **Exclude mid** with mid+1 or mid-1 since we already checked it. Return **-1** when loop ends without finding target. Key insight: **at least one half is always sorted** (cannot have pivot in both halves). Requires **O(log n) time** with **O(1) space**. All elements **unique** simplifies logic. Essential pattern for rotated array search problems!
