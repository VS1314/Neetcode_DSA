# Search in Rotated Sorted Array II

## Problem Description

**Difficulty**: Medium

You are given an array of length `n` which was originally sorted in **non-decreasing order** (not necessarily with **distinct** values). It has now been **rotated** between 1 and n times. For example, the array `nums = [1,2,3,4,5,6]` might become:

- `[3,4,5,6,1,2]` if it was rotated 4 times.
- `[1,2,3,4,5,6]` if it was rotated 6 times (back to original).

Given the rotated sorted array `nums` and an integer `target`, return **true** if `target` is in `nums`, or **false** if it is not present.

You must decrease the overall operation steps as much as possible.

## Examples

### Example 1:
```
Input: nums = [3,4,4,5,6,1,2,2], target = 1
Output: true

Explanation:
  Target 1 is present in the array
  Array has duplicates (4 appears twice, 2 appears twice)
```

### Example 2:
```
Input: nums = [3,5,6,0,0,1,2], target = 4
Output: false

Explanation:
  Target 4 is not in array
  Array has duplicates (0 appears twice)
```

### Example 3:
```
Input: nums = [1,0,1,1,1], target = 0
Output: true

Explanation:
  Many duplicates (1 appears 4 times)
  Target 0 is present
```

### Example 4:
```
Input: nums = [1,1,1,1,1,1,1], target = 2
Output: false

Explanation:
  All elements are same
  Target not present
```

### Example 5:
```
Input: nums = [1], target = 1
Output: true

Explanation:
  Single element, found
```

### Example 6:
```
Input: nums = [1], target = 0
Output: false

Explanation:
  Single element, not found
```

### Example 7:
```
Input: nums = [2,2,2,0,2,2], target = 0
Output: true

Explanation:
  Mostly duplicates
  Target in middle
```

### Example 8:
```
Input: nums = [1,3,5], target = 3
Output: true

Explanation:
  No duplicates, no rotation
  Standard case
```

### Example 9:
```
Input: nums = [3,1], target = 1
Output: true

Explanation:
  Two elements, rotated
```

### Example 10:
```
Input: nums = [1,1,1,1,1,1,2], target = 2
Output: true

Explanation:
  Many duplicates, target at end
```

### Example 11:
```
Input: nums = [2,2,2,3,1], target = 1
Output: true

Explanation:
  Duplicates at start, target at end
```

### Example 12:
```
Input: nums = [1,1,1,3,1], target = 3
Output: true

Explanation:
  Duplicates surrounding target
```

## Constraints
- 1 <= nums.length <= 5,000
- -10,000 <= nums[i] <= 10,000
- -10,000 <= target <= 10,000
- `nums` is guaranteed to be rotated at some pivot
- **Duplicates allowed** (key difference from version I)

**Recommended Complexity**: O(log n) average, O(n) worst case for time; O(1) space

---

## Pattern Recognition

**Primary Pattern**: **Binary Search on Rotated Sorted Array with Duplicates**

**Why This Pattern?**
- Array has **sorted property** (two sorted segments, but with duplicates)
- Need **efficient search** (better than O(n) on average)
- Finding **target existence** (return boolean)
- **Duplicates complicate** determining sorted half

**Key Insight**: Duplicates Create Ambiguity
```
WITHOUT duplicates (Search in Rotated Sorted Array I):
  nums = [4, 5, 6, 7, 0, 1, 2]
  Can always determine which half is sorted
  Always O(log n)

WITH duplicates (this problem):
  nums = [1, 0, 1, 1, 1]
         ↑        ↑  ↑
        left     mid right
  
  nums[left] = 1, nums[mid] = 1, nums[right] = 1
  All equal!
  
  Cannot determine which half is sorted!
  Left could be: [1, 0, 1] (not sorted)
  Right could be: [1, 1, 1] (sorted)
  
  Must handle this ambiguity!
```

**The Ambiguity Problem**:
```
When nums[left] == nums[mid] == nums[right]:
  
Example: [1, 0, 1, 1, 1], target = 0
        left=0, right=4, mid=2
        
  All are 1!
  
  Option 1: Target could be in left [1, 0, 1]
  Option 2: Target could be in right [1, 1, 1]
  
  Cannot eliminate either half!
  
Solution:
  Incrementally skip duplicates
  left++ or right--
  
  After left++:
    left=1, nums[left]=0
    Now we can make progress!
```

**Modified Decision Logic**:
```
At each step:

1. Check if nums[mid] == target:
   Return true immediately

2. Check if nums[left] == nums[mid] == nums[right]:
   Cannot determine sorted half
   Skip duplicates: left++ (or right--)
   Continue to next iteration
   
3. Otherwise, determine which half is sorted:
   
   If nums[left] <= nums[mid]:
     Left half sorted
     Check if target in [nums[left], nums[mid]]
     
   Else:
     Right half sorted
     Check if target in [nums[mid], nums[right]]
```

**Why Worst Case O(n)**:
```
All elements same except one:

nums = [1, 1, 1, 1, 1, 1, 2], target = 2

Every iteration:
  nums[left] == nums[mid] == nums[right] = 1
  Must skip: left++
  
Eventually: left advances to index 6
Essentially linear scan!

Worst case: O(n) ⚠️
Average case: O(log n) ✓

This is the best possible for this problem!
Cannot do better than O(n) worst case with duplicates.
```

**Comparison with Version I**:
```
Search in Rotated Sorted Array I:
  - All unique elements
  - Always O(log n)
  - Can always determine sorted half
  - Returns index (or -1)
  
Search in Rotated Sorted Array II (this):
  - Duplicates allowed
  - O(log n) average, O(n) worst case
  - Sometimes cannot determine sorted half
  - Returns boolean (true/false)
```

**Example Showing Duplicate Handling**:
```
nums = [2, 5, 6, 0, 0, 1, 2], target = 0

Step 1: left=0, right=6, mid=3
  nums[mid]=0 == target? YES!
  Return true ✓

nums = [2, 5, 6, 0, 0, 1, 2], target = 3

Step 1: left=0, right=6, mid=3
  nums[mid]=0 != target
  nums[left]=2, nums[mid]=0, nums[right]=2
  2 == 0 == 2? No
  
  0 < 2 (right sorted? Check)
  nums[mid]=0 < nums[right]=2? Yes, right sorted
  Is target 3 in [0, 2]? No
  Search left: right = mid - 1 = 2

Step 2: left=0, right=2, mid=1
  nums[mid]=5 != target
  nums[left]=2 <= nums[mid]=5? Yes, left sorted
  Is target 3 in [2, 5]? Yes!
  Search left: right = mid - 1 = 0

Step 3: left=0, right=0, mid=0
  nums[mid]=2 != target
  nums[left]=2 <= nums[mid]=2? Yes
  Is target 3 in [2, 2]? No
  Search right: left = 1

left=1 > right=0
Return false ✓
```

**Why This is Still Better Than Linear**:
```
Average case:
  Duplicates are scattered, not all same
  Binary search mostly works: O(log n)
  
Worst case:
  Most/all elements are same
  Must skip duplicates: O(n)
  
Real-world:
  Usually closer to O(log n)
  Better than always O(n) linear scan
  
This is optimal for this problem!
```

**Related Patterns**:
1. **Binary Search with Duplicates** — Core technique
2. **Rotated Array Search** — Structure
3. **Ambiguity Handling** — Skip duplicates when unsure
4. **Modified Binary Search** — Adaptive approach

---

## Algorithm & Approach

### Core Insight

**Why Binary Search Still Works (Mostly):**
```
Key properties:
  1. Array has two sorted segments (with duplicates)
  2. Most of the time can determine which half is sorted
  3. When ambiguous (all equal), skip duplicates
  4. Converges to target or exhausts search space
```

**The Optimal Strategy**:
```
Key observations:
  1. Try binary search first (fast path)
  2. Check mid == target (early exit)
  3. If nums[left] == nums[mid] == nums[right], skip duplicates
  4. Otherwise, determine sorted half and search accordingly
  5. Average O(log n), worst O(n)
```

### Step-by-Step Algorithm

---

#### **Approach 1: Modified Binary Search with Duplicate Handling - OPTIMAL**

**Core Idea**:
- Binary search with left and right pointers
- Check if mid is target
- **Handle ambiguity**: when all equal, skip duplicates
- Otherwise, determine sorted half and search

**Algorithm**
```
search(nums, target):
    left = 0
    right = nums.length - 1
    
    while left <= right:
        mid = left + (right - left) / 2
        
        // Found target
        if nums[mid] == target:
            return true
        
        // Ambiguous case: cannot determine which half is sorted
        if nums[left] == nums[mid] == nums[right]:
            left++
            right--
            continue
        
        // Determine which half is sorted
        if nums[left] <= nums[mid]:
            // Left half is sorted
            if nums[left] <= target < nums[mid]:
                right = mid - 1
            else:
                left = mid + 1
        else:
            // Right half is sorted
            if nums[mid] < target <= nums[right]:
                left = mid + 1
            else:
                right = mid - 1
    
    return false  // Not found
```

**Code Implementation**
```java
class Solution {
    public boolean search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Found the target
            if (nums[mid] == target) {
                return true;
            }
            
            // Handle duplicates: cannot determine which half is sorted
            if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
                // Skip duplicates from both ends
                left++;
                right--;
                continue;
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
        
        return false; // Target not found
    }
}
```

**Example Walkthrough**

Input: `nums = [2,5,6,0,0,1,2]`, `target = 0`

| Iteration | left | right | mid | nums[mid] | All Equal? | Which Sorted? | Target in Range? | Action |
|-----------|------|-------|-----|-----------|------------|---------------|------------------|--------|
| 1 | 0 | 6 | 3 | 0 | No (2≠0≠2) | - | mid==target? | Return true |

Return: **true** ✓

**Example with Duplicates**

Input: `nums = [1,0,1,1,1]`, `target = 0`

| Iteration | left | right | mid | nums[mid] | All Equal? | Action |
|-----------|------|-------|-----|-----------|------------|--------|
| 1 | 0 | 4 | 2 | 1 | Yes (1=1=1) | left++, right-- |
| 2 | 1 | 3 | 2 | 1 | No (0≠1≠1) | Check sorted |

At iteration 2:
- nums[left]=0, nums[mid]=1
- 0 <= 1? Yes, left sorted
- Is 0 in [0, 1)? Yes!
- right = mid - 1 = 1

Iteration 3: left=1, right=1, mid=1
- nums[mid]=0 == target? YES!
- Return true ✓

**Complexity Analysis**
- **Time**: O(log n) average, O(n) worst case
- **Space**: O(1)

---

#### **Approach 2: Linear Search - SIMPLE BUT SLOW**

**Core Idea**: Check every element.

**Code Implementation**
```java
class Solution {
    public boolean search(int[] nums, int target) {
        for (int num : nums) {
            if (num == target) {
                return true;
            }
        }
        return false;
    }
}
```

**Key Difference**: 
- Always O(n)
- Doesn't use sorted property
- Simpler but slower on average

**Complexity Analysis**
- **Time**: O(n) — Always
- **Space**: O(1)

---

#### **Approach 3: Remove Duplicates Then Binary Search - NOT RECOMMENDED**

**Core Idea**: 
- Remove duplicates first
- Then do binary search
- Problem: Loses rotation structure!

**Why This Doesn't Work Well**:
```
Original: [2, 5, 6, 0, 0, 1, 2]
After removing duplicates: [2, 5, 6, 0, 1]

But this doesn't maintain rotated sorted property correctly!
The array is no longer "rotated sorted"

Not a valid approach for this problem.
```

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time (Avg) | Time (Worst) | Space | Uses Sorted Property | Recommended |
|----------|-----------|--------------|-------|----------------------|-------------|
| **Modified Binary Search** | **O(log n)** | **O(n)** | **O(1)** | **Yes ✅** | **Yes ✅** |
| Linear Search | O(n) | O(n) | O(1) | No | Too slow ❌ |
| Remove Duplicates | N/A | N/A | O(n) | Breaks structure | Invalid ❌ |

**Winner**: **Modified binary search** — best average case!

### Why Cannot Guarantee O(log n)

```
With duplicates, worst case is unavoidable:

nums = [1, 1, 1, 1, 1, 1, 2]
target = 2

Every iteration until near end:
  nums[left] == nums[mid] == nums[right] = 1
  Must skip: left++ or right--
  
No way to eliminate half!

This is a fundamental limitation:
  With duplicates, cannot always determine sorted half
  Must fall back to linear elimination
  
Worst case O(n) is OPTIMAL for this problem!
```

### Why Skip Both left++ and right--

```
When nums[left] == nums[mid] == nums[right]:

Option 1: Only left++
  Works, but slower
  
Option 2: Only right--
  Works, but slower
  
Option 3: Both left++ and right--
  Faster! Shrinks from both ends
  Still safe: we checked mid != target
  
We use Option 3 for better average performance.
```

### Why This is Better Than Always Linear

```
Average case (scattered duplicates):
  nums = [3, 4, 5, 6, 0, 1, 2, 2]
  Most iterations can determine sorted half
  Binary search works: O(log n)
  
Worst case (all same):
  nums = [1, 1, 1, 1, 1, 1, 1]
  Every iteration is ambiguous
  Must skip: O(n)
  
Real-world data:
  Usually has variety, not all same
  Average O(log n) is achieved
  Much better than always O(n)!
```

### Why Still Use left <= right

```
Same as version I:
  Need to check single element case
  Must use left <= right
  
Example: [5], target = 5
  left=0, right=0
  Condition: 0 <= 0? Yes
  mid=0, nums[0] == 5? Yes ✓
```

### Why Check mid == target First

```
Before handling ambiguity:
  Check if nums[mid] == target
  
This is crucial!
Without this, we'd miss target at mid position.

Also, this check allows us to safely skip duplicates:
  If mid == target, we return
  If mid != target, safe to skip left++ or right--
```

---

## Critical Edge Cases & Gotchas

### 1. **All Elements Same - Target Present**
```java
Input: nums = [1,1,1,1,1], target = 1
Output: true
First check nums[mid] == target? Yes
Return true immediately
```

### 2. **All Elements Same - Target Not Present**
```java
Input: nums = [1,1,1,1,1], target = 2
Output: false
Every iteration: left++ and right--
Eventually left > right
Return false
Worst case O(n)
```

### 3. **Duplicates at Boundaries Only**
```java
Input: nums = [2,2,5,6,0,1,2,2], target = 0
Output: true
Middle part acts like version I
Binary search works efficiently
```

### 4. **Single Element**
```java
Input: nums = [1], target = 1
Output: true
left=0, right=0, mid=0
nums[0] == 1? Yes
```

### 5. **Two Elements - Both Same**
```java
Input: nums = [1,1], target = 1
Output: true
mid=0, nums[0] == 1? Yes
```

### 6. **Two Elements - Different**
```java
Input: nums = [3,1], target = 1
Output: true
Works like version I
```

### 7. **Target at Mid**
```java
Input: nums = [1,1,1,2,1,1,1], target = 2
Output: true
Mid check finds it immediately
```

### 8. **Duplicates Surrounding Target**
```java
Input: nums = [1,1,1,3,1,1,1], target = 3
Output: true
Must skip duplicates to narrow down
```

### 9. **No Rotation, With Duplicates**
```java
Input: nums = [1,1,2,2,3,3], target = 2
Output: true
Sorted with duplicates
```

### 10. **Rotation at Duplicate Point**
```java
Input: nums = [2,2,2,0,0,1], target = 0
Output: true
Pivot within duplicate region
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Handling Duplicate Ambiguity**
```java
// WRONG - no duplicate handling
while (left <= right) {
    int mid = left + (right - left) / 2;
    
    if (nums[mid] == target) return true;
    
    if (nums[left] <= nums[mid]) {
        // ... no check for nums[left] == nums[mid] == nums[right]
    }
}
```

**Why wrong**: Fails when all equal!

**Dry run failure for nums=[1,1,1,1,1], target=2:**
```
Iteration 1: left=0, right=4, mid=2
  nums[mid]=1 != target
  nums[left]=1 <= nums[mid]=1? Yes
  Left sorted? YES (but actually ambiguous!)
  
  Is target 2 in [1, 1)? No
  Search right: left = 3
  
But we eliminated left half [1,1,1]!
Without checking for duplicates, we assume left is sorted
But it might not be if there are duplicates!

Logic breaks! ❌
```

**Fix**: Check for ambiguity first
```java
if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
    left++;
    right--;
    continue;
}
```

### ❌ **MISTAKE 2: Only Incrementing left (Not Decrementing right)**
```java
// SUBOPTIMAL - only left++
if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
    left++;  // Only this
    continue;
}
```

**Why suboptimal**: Slower convergence!

**Dry run for nums=[1,1,1,1,1,1,2], target=2:**
```
With only left++:
  Iterations: 6 (must increment left 6 times)
  
With left++ and right--:
  Iterations: 3 (shrinks from both ends)
  
Both work, but second is faster!
```

**Fix**: Increment left AND decrement right
```java
left++;
right--;
```

### ❌ **MISTAKE 3: Forgetting to Continue After Skipping**
```java
// WRONG - no continue
if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
    left++;
    right--;
    // Missing continue!
}

// Falls through to sorted half logic below!
```

**Why wrong**: Falls through to wrong logic!

**Dry run failure:**
```
After left++ and right--:
  New left and right values
  But we immediately check sorted half with OLD mid!
  
mid was calculated with OLD left/right
Now left/right changed but mid didn't recalculate!

Logic breaks! ❌
```

**Fix**: Add continue
```java
left++;
right--;
continue;  // Recalculate mid in next iteration
```

### ❌ **MISTAKE 4: Wrong Duplicate Check (Using OR Instead of AND)**
```java
// WRONG - uses || instead of &&
if (nums[left] == nums[mid] || nums[mid] == nums[right]) {
    left++;
    right--;
    continue;
}
```

**Why wrong**: Too aggressive!

**Dry run failure for nums=[3,1,2,3], target=1:**
```
left=0, right=3, mid=1
nums[left]=3, nums[mid]=1, nums[right]=3

Check: 3 == 1 || 1 == 3? No

OK here, but consider:
nums=[3,3,5,6,0,1,2]
left=0, right=6, mid=3
nums[left]=3, nums[mid]=6, nums[right]=2

Check: 3 == 6 || 6 == 2? No

But what if:
nums=[3,1,2,3,4]
left=0, right=4, mid=2
nums[left]=3, nums[mid]=2, nums[right]=4

Check: 3 == 2 || 2 == 4? No

Actually, let me think of a better example:
nums=[1,3,1,1,1]
left=0, right=4, mid=2
nums[left]=1, nums[mid]=1, nums[right]=1

With ||: 1 == 1 || 1 == 1? Yes
Skips, but should we?

Actually we SHOULD skip here because all three are equal!

Let me reconsider:
nums=[2,3,3,3,3]
left=0, right=4, mid=2
nums[left]=2, nums[mid]=3, nums[right]=3

With ||: 2 == 3 || 3 == 3? Yes!
Would skip!

But we CAN determine sorted half:
  2 < 3, so left half [2,3] is sorted
  Should NOT skip!

Skipping when only two are equal is wrong! ❌
```

**Fix**: Must be all three equal
```java
if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
    // ...
}
```

### ❌ **MISTAKE 5: Checking Only Two of Three**
```java
// WRONG - only checks left and mid
if (nums[left] == nums[mid]) {
    left++;
    continue;
}
```

**Why wrong**: Can still determine sorted half!

**Dry run failure for nums=[3,3,5,1,2], target=1:**
```
left=0, right=4, mid=2
nums[left]=3, nums[mid]=5

Check: 3 == 5? No

Continue normally...

But what if nums=[3,3,1,2,5]?
left=0, right=4, mid=2
nums[left]=3, nums[mid]=1

Check: 3 == 1? No

Continue...

Actually wait, let me think of when this breaks:
nums=[1,3,1,1,1]
left=0, right=4, mid=2
nums[left]=1, nums[mid]=1

With this check: 1 == 1? Yes
Skip left++

But we CAN determine sorted half!
  nums[right]=1
  All three are 1!
  
Should skip from both ends!
But if only nums[left]==nums[mid]:
  Maybe right is different
  Can still determine

Example where only left==mid:
nums=[1,1,2,3,4]
left=0, right=4, mid=2
nums[left]=1, nums[mid]=2, nums[right]=4

1 == 2? No

OK so this check wouldn't trigger.

Let me find where checking only left==mid breaks:
nums=[1,1,5,6,0,2,3]
left=0, right=6, mid=3
nums[left]=1, nums[mid]=6, nums[right]=3

1 == 6? No

Hmm, hard to find case.

Actually, the issue is:
  If we only check nums[left] == nums[mid]
  We skip left++
  But what if right half is ambiguous?
  
The correct condition is ALL THREE equal!
```

**Fix**: Check all three
```java
if (nums[left] == nums[mid] && nums[mid] == nums[right]) {
    // ...
}
```

### ❌ **MISTAKE 6: Using < Instead of <= for Sorted Check**
```java
// WRONG - uses < instead of <=
if (nums[left] < nums[mid]) {
    // left sorted
}
```

**Why wrong**: Fails when left == mid (duplicates)!

**Dry run failure for nums=[1,1,2,3], target=2:**
```
left=0, right=3, mid=1
nums[left]=1, nums[mid]=1, nums[right]=3

All equal? 1 == 1 == 3? No

Check sorted: 1 < 1? No

Goes to else (thinks right sorted)
  nums[mid]=1 < nums[right]=3? Yes
  Right is sorted [1,2,3]
  
  Is target 2 in (1, 3]? Yes!
  left = mid + 1 = 2
  
Eventually finds it, but logic is confusing!

With <=:
  1 <= 1? Yes
  Left is sorted [1,1]
  Target 2 in [1, 1)? No
  Search right
  
Clearer logic!
```

**Fix**: Use <=
```java
if (nums[left] <= nums[mid]) {
    // ...
}
```

### ❌ **MISTAKE 7: Returning Index Instead of Boolean**
```java
// WRONG - returns mid instead of true
if (nums[mid] == target) {
    return mid;  // Should be true
}
```

**Why wrong**: Return type is boolean!

**Fix**: Return true/false
```java
if (nums[mid] == target) {
    return true;
}
// ...
return false;
```

---

## Complexity Analysis

### Time Complexity: **O(log n) average, O(n) worst case**

| Operation | Count (Avg) | Count (Worst) | Time Each | Total (Avg) | Total (Worst) |
|-----------|------------|---------------|-----------|-------------|---------------|
| **While loop iterations** | O(log n) | O(n) | O(1) | O(log n) | O(n) |
| **Check mid == target** | O(log n) | O(n) | O(1) | O(log n) | O(n) |
| **Check all equal** | O(log n) | O(n) | O(1) | O(log n) | O(n) |
| **Skip duplicates** | Few | Many | O(1) | O(1) | O(n) |
| **Determine sorted half** | O(log n) | Few | O(1) | O(log n) | O(1) |
| **Total** | - | - | - | **O(log n)** | **O(n)** |

**Time analysis**:
```
AVERAGE CASE (few duplicates):
  Binary search mostly works
  Few ambiguous cases (all equal)
  Halving works: O(log n) ✓
  
WORST CASE (all or most same):
  nums = [1, 1, 1, 1, 1, 1, 2]
  
  Every iteration until near end:
    All equal, must skip
    left++ and right--
  
  Essentially linear scan: O(n) ⚠️
  
  This is OPTIMAL worst case!
  Cannot do better with duplicates.

Examples:
  n = 100, few duplicates: ~7 iterations
  n = 100, all same: ~50 iterations (left++ and right--)
  n = 100, all same but target: 100 iterations
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

Same as version I!
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `nums = [2,5,6,0,0,1,2]`, `target = 0`

**Expected Output:** `true`

---

**Initial State:**
```
Array: [2, 5, 6, 0, 0, 1, 2]
Index:  0  1  2  3  4  5  6

Original sorted: [0, 0, 1, 2, 2, 5, 6]
Rotated: [2, 5, 6, 0, 0, 1, 2]

Duplicates: 0 appears twice, 2 appears twice

Target: 0 (should return true)

left = 0, right = 6
```

---

**Iteration 1:**
```
left = 0, right = 6
mid = 0 + (6-0)/2 = 3

Array: [2, 5, 6, 0, 0, 1, 2]
        ↑        ↑        ↑
       left     mid    right

Check: nums[mid] == target?
  nums[3] = 0 == 0? YES! ✓

Return: true
```

---

**Summary:**
```
Found target at mid on first iteration!
Early exit is crucial for efficiency.
```

---

### Example with Duplicate Ambiguity

**Input:** `nums = [1,0,1,1,1]`, `target = 0`

```
Array: [1, 0, 1, 1, 1]
Index:  0  1  2  3  4

Target: 0

left = 0, right = 4
```

---

**Iteration 1:**
```
left = 0, right = 4
mid = 2

Array: [1, 0, 1, 1, 1]
        ↑     ↑     ↑
       left  mid  right

Check: nums[mid] == target?
  nums[2] = 1 == 0? No

Check all equal?
  nums[left]=1, nums[mid]=1, nums[right]=1
  1 == 1 && 1 == 1? YES!
  
Ambiguous! Cannot determine sorted half.

Action: left++, right--
  left = 1, right = 3
```

---

**Iteration 2:**
```
left = 1, right = 3
mid = 2

Array: [1, 0, 1, 1, 1]
           ↑  ↑  ↑
         left mid right

Check: nums[mid] == target?
  nums[2] = 1 == 0? No

Check all equal?
  nums[left]=0, nums[mid]=1, nums[right]=1
  0 == 1 && 1 == 1? No (first part false)
  
Not all equal! Can determine sorted half.

Determine sorted half:
  nums[left]=0 <= nums[mid]=1? Yes
  Left half [0, 1] is sorted
  
Check target in left:
  nums[left] <= target < nums[mid]?
  0 <= 0 < 1? Yes!
  
Search left: right = mid - 1 = 1
```

---

**Iteration 3:**
```
left = 1, right = 1
mid = 1

Array: [1, 0, 1, 1, 1]
           ↑
         left
          mid
         right

Check: nums[mid] == target?
  nums[1] = 0 == 0? YES! ✓

Return: true
```

---

**Summary:**
```
Total iterations: 3
Had to handle duplicate ambiguity once
Found target after skipping duplicates
```

---

### Worst Case Example

**Input:** `nums = [1,1,1,1,1,1,1]`, `target = 2`

```
Every iteration:
  All elements are 1
  nums[left] == nums[mid] == nums[right] = 1
  Must skip: left++, right--
  
Iteration 1: left=0, right=6 → left=1, right=5
Iteration 2: left=1, right=5 → left=2, right=4
Iteration 3: left=2, right=4 → left=3, right=3
Iteration 4: left=3, right=3
  mid=3, nums[3]=1 != 2
  All equal: left++, right--
  left=4, right=2
  
left > right, exit loop
Return false

Essentially O(n) - worst case!
```

---

## Comparison of Approaches

| Approach | Time (Avg) | Time (Worst) | Space | Code Lines | Recommended |
|----------|-----------|--------------|-------|------------|-------------|
| **Modified Binary Search** | **O(log n)** | **O(n)** | **O(1)** | **~30** | **Yes ✅** |
| Linear Search | O(n) | O(n) | O(1) | ~7 | Simple but slow |

**Winner**: **Modified binary search** — best average case!

---

## Key Takeaways

1. **Binary search with duplicates** — handle ambiguity case
2. **Check mid == target first** — early exit optimization
3. **Detect ambiguity**: `nums[left] == nums[mid] == nums[right]`
4. **When ambiguous**: skip duplicates with `left++` and `right--`, then continue
5. **Otherwise**: determine sorted half same as version I
6. **Must use continue** after skipping to recalculate mid
7. **Check all three equal** — not just two
8. **Returns boolean** — true/false, not index
9. **Worst case O(n)** — unavoidable with duplicates, optimal solution
10. **Average case O(log n)** — still efficient for typical data

---

## Interview Tips

**What to say in an interview:**

> "This problem is similar to Search in Rotated Sorted Array, but now duplicates are allowed, which creates a critical challenge. The main issue is that when nums[left], nums[mid], and nums[right] are all equal, we cannot determine which half of the array is sorted. To handle this, I'll use a modified binary search. First, I check if nums[mid] equals the target for an early exit. Then, if all three values (left, mid, right) are equal, I incrementally skip duplicates by incrementing left and decrementing right, then continue to the next iteration. Otherwise, I determine which half is sorted using the same logic as version I: if nums[left] <= nums[mid], the left half is sorted; otherwise, the right half is sorted. Then I check if the target falls within the sorted half's range and search accordingly. This approach achieves O(log n) time on average, but in the worst case with many duplicates (like all elements being the same), it degrades to O(n), which is optimal for this problem. The space complexity remains O(1)."

**Key points to mention:**
1. **Duplicates create ambiguity** — main challenge
2. **Cannot always determine sorted half** — when all three equal
3. **Handle ambiguity**: skip duplicates from both ends
4. **Must use continue** after skipping to recalculate mid
5. **Check all three equal** — nums[left] == nums[mid] == nums[right]
6. **Average O(log n)** — works well for scattered duplicates
7. **Worst O(n)** — unavoidable, optimal for problem
8. **Returns boolean** — not index

**Common Follow-ups:**
- "Why is worst case O(n)?" → All same values create ambiguity, must check all
- "Can you do better than O(n) worst case?" → No, it's optimal with duplicates
- "Why skip from both ends?" → Faster convergence than skipping from one end
- "What if we remove duplicates first?" → Breaks rotated sorted structure
- "How is this different from version I?" → Version I has unique elements, always O(log n)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Search in Rotated Sorted Array II** | Medium | **Binary Search with Duplicates** | **This problem** |
| Search in Rotated Sorted Array | Medium | Binary Search on Rotated Array | No duplicates, always O(log n) |
| Find Minimum in Rotated Sorted Array II | Hard | Binary Search with Duplicates | Find minimum instead of search target |
| Find Minimum in Rotated Sorted Array | Medium | Binary Search on Rotated Array | Find minimum, no duplicates |
| Remove Duplicates from Sorted Array II | Medium | Two Pointers | Remove duplicates, not search |
| Search a 2D Matrix II | Medium | Binary Search / Divide & Conquer | 2D matrix search with duplicates |

**Pattern Progression**:
1. **Binary search without duplicates** — Always O(log n)
2. **Binary search with duplicates** (this problem) — O(log n) avg, O(n) worst
3. **Understanding trade-offs** — Duplicates complicate algorithms
4. **Ambiguity handling** — Skip when unsure

---

## Final Pattern Label

✅ **Binary Search on Rotated Sorted Array with Duplicates**

**Remember:** This is **binary search on rotated sorted array with duplicates allowed**. The key challenge is **ambiguity when nums[left] == nums[mid] == nums[right]** — we cannot determine which half is sorted. **Solution**: Skip duplicates with `left++` and `right--`, then **continue** to recalculate mid. **Must check all three equal**, not just two. **Check mid == target first** for early exit. Otherwise, use **same sorted half detection** as version I: if `nums[left] <= nums[mid]`, left sorted; else right sorted. **Returns boolean** (true/false), not index. **Worst case O(n)** when most/all elements are same (unavoidable, optimal for problem). **Average case O(log n)** when duplicates are scattered (still very efficient). **Space O(1)** with only three variables. Key insight: **duplicates create fundamental ambiguity**, making worst case linear unavoidable, but average case still logarithmic!
