# Minimum Size Subarray Sum

## Problem Description

**Difficulty**: Medium

You are given an array of positive integers `nums` and a positive integer `target`. Return the **minimal length** of a subarray whose sum is greater than or equal to `target`. If there is no such subarray, return `0` instead.

A **subarray** is a contiguous non-empty sequence of elements within an array.

## Examples

### Example 1:
```
Input: target = 10, nums = [2,1,5,1,5,3]
Output: 3
Explanation: The subarray [5,1,5] has sum = 11 >= 10 with minimal length 3.
```

### Example 2:
```
Input: target = 5, nums = [1,2,1]
Output: 0
Explanation: No subarray has sum >= 5. Return 0.
```

### Example 3:
```
Input: target = 7, nums = [2,3,1,2,4,3]
Output: 2
Explanation: The subarray [4,3] has sum = 7 >= 7 with minimal length 2.
```

### Example 4:
```
Input: target = 11, nums = [1,1,1,1,1,1,1,1]
Output: 0
Explanation: All elements are 1. Max possible sum of any subarray < 11. Return 0.
```

## Constraints
- 1 <= nums.length <= 100,000
- 1 <= nums[i] <= 10,000
- 1 <= target <= 1,000,000,000

**Recommended Complexity**: O(n) time, O(1) space

**Follow-up**: Can you solve it in O(n log n)?

---

## Pattern Recognition

**Primary Pattern**: **Variable-Size Sliding Window (Expand-Shrink Strategy)**

**Why This Pattern?**
- Need to find subarray (contiguous)
- Condition: sum >= target
- Want minimum length
- **Critical**: All numbers are positive (enables sliding window)

**Key Insight**: Positive Numbers Enable Monotonic Sum Behavior
```
Problem: Find minimum-length subarray with sum >= target

Observation (CRUCIAL):
  All numbers are positive → Adding elements ALWAYS increases sum
  
  This creates monotonic behavior:
    Expand window (add element) → sum increases
    Shrink window (remove element) → sum decreases
  
  This property allows greedy sliding window approach!
  
If negatives were allowed:
  Expanding might decrease sum
  Shrinking might increase sum
  → Sliding window would NOT work!
```

**Why Variable-Size Sliding Window?**
```
Brute force: Try all subarrays
  for i = 0 to n:
    for j = i to n:
      calculate sum of subarray[i..j]
      if sum >= target:
        update min length
  → O(n²) or O(n³) depending on sum calculation
  → Too slow!

Variable Sliding Window:
  Expand: Move right pointer, add to sum
  Shrink: When sum >= target, move left pointer to minimize length
  Track: Minimum length seen
  → O(n) time, each element visited at most twice!
```

**The Expand-Shrink Strategy**:
```
Two pointers: left, right
  Both start at 0
  
Phase 1: EXPAND (move right)
  Add nums[right] to sum
  right++
  
Phase 2: SHRINK (move left while valid)
  While sum >= target:
    Update min length
    Remove nums[left] from sum
    left++
  
Continue until right reaches end

Example: target=7, nums=[2,3,1,2,4,3]
  
  Window [2]: sum=2 < 7 → expand
  Window [2,3]: sum=5 < 7 → expand
  Window [2,3,1]: sum=6 < 7 → expand
  Window [2,3,1,2]: sum=8 >= 7 ✓ len=4
    Shrink: [3,1,2] sum=6 < 7 → stop shrinking
  Window [3,1,2,4]: sum=10 >= 7 ✓ len=4
    Shrink: [1,2,4] sum=7 >= 7 ✓ len=3
    Shrink: [2,4] sum=6 < 7 → stop shrinking
  Window [2,4,3]: sum=9 >= 7 ✓ len=3
    Shrink: [4,3] sum=7 >= 7 ✓ len=2 (minimum!)
    
  Answer: 2
```

**Critical Detail**: When to Shrink
```
Shrink ONLY when sum >= target
  Because we want MINIMUM length
  Keep removing from left as long as condition holds
  
Use WHILE loop for shrinking:
  while (sum >= target) {
    update min length
    remove left element
    left++
  }
  
NOT if statement!
  We might be able to shrink multiple times
```

**Related Patterns**:
1. **Variable Sliding Window** — Dynamic window size
2. **Two Pointers** — Left and right boundaries
3. **Greedy** — Shrink as much as possible when valid
4. **Subarray Sum** — Track running sum

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Try all subarrays
  for i = 0 to n:
    sum = 0
    for j = i to n:
      sum += nums[j]
      if sum >= target:
        minLength = min(minLength, j - i + 1)
  
  → O(n²) time
  → Too slow for n=100,000!

Variable Sliding Window:
  Expand window until sum >= target
  Shrink window while maintaining sum >= target
  Track minimum length
  → O(n) time, each element enters and leaves at most once!
```

**The Sliding Window Strategy**:
```
Key observations:
  1. Adding element increases sum (positive numbers)
  2. Removing element decreases sum
  3. Can expand greedily (always beneficial to try)
  4. Can shrink greedily (minimize length while valid)
  
Algorithm:
  Initialize: left=0, sum=0, minLen=infinity
  
  For right from 0 to n-1:
    1. Add nums[right] to sum (expand)
    2. While sum >= target:
       a. Update minLen = min(minLen, right-left+1)
       b. Remove nums[left] from sum
       c. left++ (shrink)
    3. Continue expanding
  
  Return minLen if found, else 0
```

### Step-by-Step Algorithm

---

#### **Approach 1: Variable Sliding Window (OPTIMAL)**

**Core Idea**:
- Use two pointers (left, right) for window
- Expand by moving right, track sum
- Shrink from left while sum >= target
- Track minimum length

**Algorithm**
```
minSubArrayLen(target, nums):
    left = 0
    sum = 0
    minLength = infinity
    
    for right from 0 to nums.length - 1:
        // Expand window
        sum += nums[right]
        
        // Shrink window while valid
        while sum >= target:
            minLength = min(minLength, right - left + 1)
            sum -= nums[left]
            left++
    
    return minLength == infinity ? 0 : minLength
```

**Code Implementation**
```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int sum = 0;
        int minLength = Integer.MAX_VALUE;
        
        for (int right = 0; right < nums.length; right++) {
            // Expand window by adding right element
            sum += nums[right];
            
            // Shrink window while sum >= target
            while (sum >= target) {
                // Update minimum length
                minLength = Math.min(minLength, right - left + 1);
                
                // Remove left element and shrink
                sum -= nums[left];
                left++;
            }
        }
        
        // Return 0 if no valid subarray found
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
}
```

**Example Walkthrough**

Input: `target = 7, nums = [2,3,1,2,4,3]`

| Step | right | nums[right] | sum | left | Window | Valid? | minLength |
|------|-------|-------------|-----|------|--------|--------|-----------|
| Init | - | - | 0 | 0 | [] | - | ∞ |
| 1 | 0 | 2 | 2 | 0 | [2] | No (2<7) | ∞ |
| 2 | 1 | 3 | 5 | 0 | [2,3] | No (5<7) | ∞ |
| 3 | 2 | 1 | 6 | 0 | [2,3,1] | No (6<7) | ∞ |
| 4 | 3 | 2 | 8 | 0 | [2,3,1,2] | Yes (8>=7) | 4 |
| 4a | 3 | - | 6 | 0→1 | [3,1,2] | No (6<7), stop | 4 |
| 5 | 4 | 4 | 10 | 1 | [3,1,2,4] | Yes (10>=7) | 4 |
| 5a | 4 | - | 7 | 1→2 | [1,2,4] | Yes (7>=7) | 3 |
| 5b | 4 | - | 6 | 2→3 | [2,4] | No (6<7), stop | 3 |
| 6 | 5 | 3 | 9 | 3 | [2,4,3] | Yes (9>=7) | 3 |
| 6a | 5 | - | 7 | 3→4 | [4,3] | Yes (7>=7) | **2** ✓ |
| 6b | 5 | - | 3 | 4→5 | [3] | No (3<7), stop | 2 |

**Output:** `2`

**Complexity Analysis**
- **Time Complexity**: O(n) — Each element visited at most twice (once by right, once by left)
- **Space Complexity**: O(1) — Only variables for pointers and sum

---

#### **Approach 2: Binary Search + Prefix Sum (O(n log n))**

**Core Idea**: For each position, binary search for shortest subarray ending there.

**Code Implementation**
```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int[] prefixSum = new int[n + 1];
        
        // Build prefix sum array
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        
        int minLength = Integer.MAX_VALUE;
        
        // For each ending position
        for (int right = 0; right < n; right++) {
            // Find leftmost position where sum >= target
            // sum[left..right] = prefixSum[right+1] - prefixSum[left]
            // We want: prefixSum[right+1] - prefixSum[left] >= target
            // So: prefixSum[left] <= prefixSum[right+1] - target
            
            int targetSum = prefixSum[right + 1] - target;
            
            // Binary search for largest index where prefixSum[i] <= targetSum
            int left = binarySearch(prefixSum, 0, right + 1, targetSum);
            
            if (left >= 0) {
                minLength = Math.min(minLength, right - left + 1);
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    private int binarySearch(int[] prefixSum, int start, int end, int target) {
        int left = start, right = end;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (prefixSum[mid] <= target) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
}
```

**Key Difference**: 
- Uses prefix sum for O(1) range sum queries
- Binary search to find optimal left boundary
- More complex but demonstrates O(n log n) approach

**Complexity Analysis**
- **Time Complexity**: O(n log n) — Binary search for each position
- **Space Complexity**: O(n) — Prefix sum array

---

#### **Approach 3: Brute Force (NOT OPTIMAL)**

**Core Idea**: Try all subarrays and check their sums.

**Code Implementation**
```java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int minLength = Integer.MAX_VALUE;
        
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                
                if (sum >= target) {
                    minLength = Math.min(minLength, j - i + 1);
                    break;  // No need to extend further from this start
                }
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n²) — Nested loops
- **Space Complexity**: O(1)
- **Why Not Optimal**: Too slow

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | Binary Search | **Sliding Window** |
|-------------|-------------|---------------|--------------------|
| Time complexity | O(n²) ❌ | O(n log n) ✓ | **O(n) ✅** |
| Space complexity | O(1) ✓ | O(n) ❌ | **O(1) ✅** |
| Code simplicity | Simple | Complex | **Clean ✅** |
| Optimal | ❌ | Partial | **✅** |

**Winner**: **Sliding Window** — optimal time and space!

### Why Sliding Window Works?

```
Key requirement: All numbers are POSITIVE

This guarantees:
  Adding element → sum increases
  Removing element → sum decreases
  
Monotonic sum property:
  If subarray[i..j] has sum >= target
  Then subarray[i..j+1] also has sum >= target
  
  If subarray[i..j] has sum < target
  Then subarray[i+1..j] also has sum < target
  
This monotonicity enables greedy approach:
  Expand until valid
  Shrink while maintaining validity
  
Result: O(n) time, each element visited at most twice
```

### Why Use While Loop for Shrinking?

```
Common mistake: Using if instead of while

WRONG:
  if (sum >= target) {  // Only shrinks once!
    minLength = min(minLength, right - left + 1);
    sum -= nums[left];
    left++;
  }

Problem: Might need to shrink multiple times!

Example: target=7, current window [1,1,1,10]
  sum = 13 >= 7 ✓
  Shrink once: [1,1,10] sum=12 >= 7 (still valid!)
  Shrink again: [1,10] sum=11 >= 7 (still valid!)
  Shrink again: [10] sum=10 >= 7 (still valid!)
  Shrink again: [] sum=0 < 7 (stop)
  
  Need to shrink 3 times to find minimum!
  
CORRECT:
  while (sum >= target) {  // Shrinks until invalid
    minLength = min(minLength, right - left + 1);
    sum -= nums[left];
    left++;
  }
```

---

## Critical Edge Cases & Gotchas

### 1. **No Valid Subarray**
```java
Input: target = 100, nums = [1,2,3,4,5]
Output: 0
Explanation: Sum of entire array = 15 < 100. Return 0.
```

### 2. **Single Element Satisfies**
```java
Input: target = 5, nums = [5,1,2,3]
Output: 1
Explanation: Single element [5] has sum = 5 >= 5.
```

### 3. **Entire Array Needed**
```java
Input: target = 15, nums = [1,2,3,4,5]
Output: 5
Explanation: Need entire array to reach sum = 15.
```

### 4. **First Element Satisfies**
```java
Input: target = 5, nums = [10,2,3]
Output: 1
Explanation: First element [10] >= 5.
```

### 5. **Last Element Satisfies**
```java
Input: target = 5, nums = [1,2,10]
Output: 1
Explanation: Last element [10] >= 5.
```

### 6. **All Same Elements**
```java
Input: target = 10, nums = [2,2,2,2,2,2]
Output: 5
Explanation: Need [2,2,2,2,2] = 10 >= 10.
```

### 7. **Multiple Valid Windows**
```java
Input: target = 7, nums = [2,3,1,2,4,3]
Output: 2
Explanation: Both [4,3] and potentially other windows, but [4,3] is shortest.
```

### 8. **Large Target, Small Array**
```java
Input: target = 1000000000, nums = [1,2,3]
Output: 0
Explanation: Impossible to reach target.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Using If Instead of While for Shrinking**
```java
// WRONG - only shrinks once
for (int right = 0; right < nums.length; right++) {
    sum += nums[right];
    
    if (sum >= target) {  // WRONG! Should be while
        minLength = Math.min(minLength, right - left + 1);
        sum -= nums[left];
        left++;
    }
}
```

**Why wrong**: Might need to shrink multiple times to find minimum!

**Dry run failure for target=7, nums=[1,1,10]:**
```
right=0: sum=1, no shrink
right=1: sum=2, no shrink
right=2: sum=12
  if (sum >= target): shrink once
    Remove nums[0]=1, sum=11, left=1
    Window [1,10], length=2
  Exit if block
  
But we could shrink more!
  Window [10] also has sum=10 >= 7
  Length would be 1 (better!)
  
MISSED OPTIMAL ANSWER!
```

**Fix**: Use while
```java
while (sum >= target) {
    minLength = Math.min(minLength, right - left + 1);
    sum -= nums[left];
    left++;
}
```

### ❌ **MISTAKE 2: Not Checking if MinLength Was Updated**
```java
// WRONG - always returns minLength even if no valid subarray
return minLength;  // Returns Integer.MAX_VALUE if no valid subarray!
```

**Why wrong**: If no subarray has sum >= target, should return 0!

**Fix**: Check if minLength was updated
```java
return minLength == Integer.MAX_VALUE ? 0 : minLength;
```

### ❌ **MISTAKE 3: Off-by-One in Window Length**
```java
// WRONG - incorrect length calculation
minLength = Math.min(minLength, right - left);  // WRONG! Should be +1
```

**Why wrong**: Window from left to right (inclusive) has length `right - left + 1`!

**Fix**: Add 1
```java
minLength = Math.min(minLength, right - left + 1);
```

### ❌ **MISTAKE 4: Updating MinLength Outside While Loop**
```java
// WRONG - updates after shrinking, not during
while (sum >= target) {
    sum -= nums[left];
    left++;
}
minLength = Math.min(minLength, right - left + 1);  // WRONG position!
```

**Why wrong**: Updates length AFTER shrinking, which gives invalid window!

**Dry run failure:**
```
Window [2,3,4], sum=9 >= 7
  Shrink: [3,4], sum=7 >= 7
  Shrink: [4], sum=4 < 7, exit while
  Now update minLength with [4]? NO! Window is invalid!

Should update BEFORE each shrink!
```

**Fix**: Update inside while loop before shrinking
```java
while (sum >= target) {
    minLength = Math.min(minLength, right - left + 1);  // Update first
    sum -= nums[left];
    left++;
}
```

### ❌ **MISTAKE 5: Initializing minLength to 0**
```java
// WRONG - can't distinguish "no valid subarray" from "found length 0"
int minLength = 0;  // WRONG!
```

**Why wrong**: Can't tell if we found a valid subarray or not!

**Fix**: Initialize to Integer.MAX_VALUE
```java
int minLength = Integer.MAX_VALUE;
```

### ❌ **MISTAKE 6: Not Handling Single Element Case**
```java
// Code should naturally handle this, but worth testing
Input: target = 5, nums = [5]
Expected: 1

// Verify algorithm works:
right=0: sum=5
  while (sum >= target): 5 >= 5 ✓
    minLength = min(∞, 0-0+1) = 1 ✓
    sum -= nums[0] = 0
    left = 1
Return 1 ✓
```

**Fix**: Algorithm handles naturally, no special case needed

### ❌ **MISTAKE 7: Expanding Without Checking Bounds**
```java
// WRONG - accesses beyond array bounds
while (right < nums.length && sum < target) {  // WRONG logic!
    sum += nums[right];
    right++;
}
```

**Why wrong**: For loop already handles bounds, don't need manual expansion!

**Fix**: Use for loop as in approach 1
```java
for (int right = 0; right < nums.length; right++) {
    sum += nums[right];
    // ...
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Iterate with right pointer | O(n) | Visit each element once |
| Shrink with left pointer | O(n) total | Each element removed at most once |
| Update minLength | O(1) | Simple comparison |
| **Total** | **O(n)** | Linear time |

**Why O(n) not O(n²)?**
```
At first glance, nested loops (for + while) seem O(n²)

But observe:
  Right pointer: moves from 0 to n-1 → n moves
  Left pointer: moves from 0 to n-1 → at most n moves TOTAL
  
Each element:
  Enters window once (when right reaches it)
  Leaves window at most once (when left passes it)
  
Total operations: 2n → O(n)
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| left pointer | O(1) | Single variable |
| sum variable | O(1) | Single variable |
| minLength variable | O(1) | Single variable |
| **Total** | **O(1)** | Constant space |

---

## Visualization

### Complete Example Walkthrough

**Input:** `target = 7, nums = [2,3,1,2,4,3]`

**Goal:** Find minimum-length subarray with sum >= 7.

---

**Initial State:**
```
left = 0, right = 0
sum = 0
minLength = ∞
```

---

**Step 1: right=0, add nums[0]=2**
```
nums = [2, 3, 1, 2, 4, 3]
        ↑
        L,R

sum = 0 + 2 = 2
Check: 2 >= 7? No
minLength = ∞

Window [2], sum=2 < 7 (expand)
```

---

**Step 2: right=1, add nums[1]=3**
```
nums = [2, 3, 1, 2, 4, 3]
        ↑  ↑
        L  R

sum = 2 + 3 = 5
Check: 5 >= 7? No
minLength = ∞

Window [2,3], sum=5 < 7 (expand)
```

---

**Step 3: right=2, add nums[2]=1**
```
nums = [2, 3, 1, 2, 4, 3]
        ↑     ↑
        L     R

sum = 5 + 1 = 6
Check: 6 >= 7? No
minLength = ∞

Window [2,3,1], sum=6 < 7 (expand)
```

---

**Step 4: right=3, add nums[3]=2**
```
nums = [2, 3, 1, 2, 4, 3]
        ↑        ↑
        L        R

sum = 6 + 2 = 8
Check: 8 >= 7? Yes! Enter while loop

  Iteration 1:
    minLength = min(∞, 3-0+1) = 4
    sum -= nums[0] = 8 - 2 = 6
    left++ → left = 1
    Check: 6 >= 7? No, exit while

Window [2,3,1,2], sum=8 >= 7, length=4 ✓
After shrink: [3,1,2], sum=6 < 7
```

---

**Step 5: right=4, add nums[4]=4**
```
nums = [2, 3, 1, 2, 4, 3]
           ↑        ↑
           L        R

sum = 6 + 4 = 10
Check: 10 >= 7? Yes! Enter while loop

  Iteration 1:
    minLength = min(4, 4-1+1) = 4
    sum -= nums[1] = 10 - 3 = 7
    left++ → left = 2
    Check: 7 >= 7? Yes, continue
    
  Iteration 2:
    minLength = min(4, 4-2+1) = 3 ✓
    sum -= nums[2] = 7 - 1 = 6
    left++ → left = 3
    Check: 6 >= 7? No, exit while

Window [3,1,2,4], sum=10 >= 7
After shrinking: [1,2,4] sum=7 >= 7, length=3 ✓
After more shrinking: [2,4], sum=6 < 7
```

---

**Step 6: right=5, add nums[5]=3**
```
nums = [2, 3, 1, 2, 4, 3]
                 ↑     ↑
                 L     R

sum = 6 + 3 = 9
Check: 9 >= 7? Yes! Enter while loop

  Iteration 1:
    minLength = min(3, 5-3+1) = 3
    sum -= nums[3] = 9 - 2 = 7
    left++ → left = 4
    Check: 7 >= 7? Yes, continue
    
  Iteration 2:
    minLength = min(3, 5-4+1) = 2 ✓✓ (new minimum!)
    sum -= nums[4] = 7 - 4 = 3
    left++ → left = 5
    Check: 3 >= 7? No, exit while

Window [2,4,3], sum=9 >= 7
After shrinking: [4,3] sum=7 >= 7, length=2 ✓✓
After more shrinking: [3], sum=3 < 7
```

---

**Final Result:** `minLength = 2`

**Optimal Subarray:** [4,3] with sum = 7 >= 7

### Visual Summary

```
target = 7, nums = [2,3,1,2,4,3]

Windows explored:
  [2]           → sum=2 < 7
  [2,3]         → sum=5 < 7
  [2,3,1]       → sum=6 < 7
  [2,3,1,2]     → sum=8 >= 7, len=4 ✓
  [3,1,2,4]     → sum=10 >= 7, len=4
  [1,2,4]       → sum=7 >= 7, len=3 ✓
  [2,4,3]       → sum=9 >= 7, len=3
  [4,3]         → sum=7 >= 7, len=2 ✓✓ (minimum!)

Minimum length = 2
```

---

## Comparison of Approaches

| Approach | Time | Space | Optimal | Notes |
|----------|------|-------|---------|-------|
| Brute Force | O(n²) | O(1) | ❌ | Try all subarrays |
| Binary Search + Prefix Sum | O(n log n) | O(n) | Partial | Good for follow-up |
| **Variable Sliding Window** | **O(n)** | **O(1)** | **✅** | **Optimal solution** |

**Recommendation**: Use **Variable Sliding Window** for optimal performance

---

## Key Takeaways

1. **Positive numbers enable sliding window** — monotonic sum behavior
2. **Use while loop for shrinking** — might need multiple shrinks
3. **Update minLength before shrinking** — capture valid window
4. **Each element enters/leaves once** — O(n) time
5. **Initialize minLength to MAX_VALUE** — distinguish "not found"
6. **Window length = right - left + 1** — inclusive range
7. **Return 0 if no valid subarray** — handle edge case

---

## Interview Tips

**What to say in an interview:**

> "This is a variable-size sliding window problem. Since all numbers are positive, adding elements always increases the sum and removing elements always decreases it. This monotonic property allows me to use a greedy two-pointer approach. I'll expand the window by moving the right pointer and adding elements to the sum. Whenever the sum becomes greater than or equal to the target, I'll enter a while loop to shrink the window from the left as much as possible while still maintaining the valid condition. During each shrink iteration, I'll update the minimum length. This ensures I find the shortest valid subarray. Each element is added once and removed at most once, giving O(n) time complexity with O(1) space."

**Key points to mention:**
1. **Positive numbers are crucial** — enable monotonic sum behavior
2. **Variable-size window** — expands and shrinks dynamically
3. **While loop for shrinking** — not just if statement
4. **Update before shrinking** — capture valid window length
5. **Complexity** — O(n) time, O(1) space

**If asked about follow-up (O(n log n)):**
> "For the O(n log n) solution, I can use binary search with prefix sums. First, I'd build a prefix sum array in O(n) time. Then, for each ending position, I'd use binary search to find the leftmost starting position where the subarray sum is at least target. This gives O(n log n) time and O(n) space. While it's slower than the sliding window approach, it demonstrates an alternative technique using binary search on monotonic data."

**Common Follow-ups:**
- "What if numbers can be negative?" → Sliding window won't work, need different approach (prefix sum + hash map)
- "What if you want maximum length instead?" → Similar approach with opposite comparison
- "What if you need to find the actual subarray?" → Track start index when updating minLength

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Minimum Size Subarray Sum** | Medium | **Variable Sliding Window** | **This problem** |
| Maximum Size Subarray Sum Equals k | Medium | Prefix Sum + HashMap | Fixed target, can have negatives |
| Longest Substring Without Repeating Characters | Medium | Variable Sliding Window | Different constraint |
| Longest Repeating Character Replacement | Medium | Variable Sliding Window | With replacements |
| Subarray Sum Equals K | Medium | Prefix Sum + HashMap | Exact sum, not minimum |
| Minimum Window Substring | Hard | Variable Sliding Window | Pattern matching |

**Pattern Progression**:
1. **Minimum size with sum constraint** (this problem) — Basic variable window
2. **Maximum size with sum constraint** — Opposite goal
3. **With negative numbers** — Need prefix sum + hash map
4. **Pattern matching** — More complex validity check

---

## Final Pattern Label

✅ **Variable-Size Sliding Window (Expand-Shrink with While Loop)**

**Remember:** Use two pointers starting at the beginning. Expand by moving right pointer and adding to sum. When sum >= target, enter a while loop to shrink from left as much as possible while updating minimum length. The key insight is that positive numbers create monotonic sum behavior, allowing greedy expansion and shrinking. Each element enters and leaves the window at most once, achieving O(n) time with O(1) space!
