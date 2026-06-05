# 4Sum

## Problem Description

**Difficulty**: Medium

Given an integer array `nums` of size `n` and an integer `target`, return an array of all the unique quadruplets `[nums[a], nums[b], nums[c], nums[d]]` such that:

- `0 <= a, b, c, d < n`
- `a`, `b`, `c`, and `d` are distinct
- `nums[a] + nums[b] + nums[c] + nums[d] == target`

You may return the answer in any order.

**Note**: `[1,0,3,2]` and `[3,0,1,2]` are considered as the same quadruplet.

## Examples

### Example 1:
```
Input: nums = [3,2,3,-3,1,0], target = 3
Output: [[-3,0,3,3],[-3,1,2,3]]
Explanation:
- nums[3] + nums[4] + nums[1] + nums[0] = (-3) + 1 + 2 + 3 = 3
- nums[3] + nums[5] + nums[0] + nums[2] = (-3) + 0 + 3 + 3 = 3
- The distinct quadruplets are [-3,0,3,3] and [-3,1,2,3]
```

### Example 2:
```
Input: nums = [1,-1,1,-1,1,-1], target = 2
Output: [[-1,1,1,1]]
Explanation: The only unique quadruplet that sums to 2 is [-1,1,1,1].
```

### Example 3:
```
Input: nums = [0,0,0,0], target = 0
Output: [[0,0,0,0]]
Explanation: The only unique quadruplet is [0,0,0,0].
```

## Constraints
- 1 <= nums.length <= 200
- -10^9 <= nums[i] <= 10^9
- -10^9 <= target <= 10^9

**Recommended Complexity**: O(n³) time, O(1) space (excluding output)

---

## Pattern Recognition

**Primary Pattern**: **Sorting + Nested Loops + Two Pointers (Fix Two Elements + Two Sum II)**

**Why This Pattern?**
- Need to find four numbers that sum to target
- Must avoid duplicate quadruplets
- Sorting enables two-pointer technique and easy duplicate skipping
- 4Sum reduces to fixing two elements and solving 2Sum for remaining elements

**Key Insight**: 4Sum = Fix Two Elements + Two Sum II
```
Original Problem: Find a, b, c, d such that nums[a] + nums[b] + nums[c] + nums[d] = target

Rearrange: nums[c] + nums[d] = target - nums[a] - nums[b]

Strategy:
  1. Sort the array
  2. Fix nums[i] (outer loop)
  3. Fix nums[j] (second loop, j > i)
  4. Find nums[left] + nums[right] = target - nums[i] - nums[j] using two pointers
  5. This is exactly the Two Sum II problem on sorted array!
  
Example:
  nums = [-3, -1, 0, 1, 2, 3] (sorted), target = 3
  
  Fix i=0, j=1: nums[0]=-3, nums[1]=-1
  Need: nums[left] + nums[right] = 3 - (-3) - (-1) = 7
  Find two numbers in [0,1,2,3] that sum to 7
  Found: 3 + 4 (doesn't exist in our case)
  
  Fix i=0, j=2: nums[0]=-3, nums[2]=0
  Need: nums[left] + nums[right] = 3 - (-3) - 0 = 6
  Find two numbers in [1,2,3] that sum to 6
  Found: 3 + 3 → quadruplet [-3,0,3,3] ✓
  
  Fix i=0, j=3: nums[0]=-3, nums[3]=1
  Need: nums[left] + nums[right] = 3 - (-3) - 1 = 5
  Find two numbers in [2,3] that sum to 5
  Found: 2 + 3 → quadruplet [-3,1,2,3] ✓
```

**Key Insight**: Duplicate Handling (4 Levels)
- After sorting, duplicates are adjacent
- Skip duplicate i: if nums[i] == nums[i-1], skip
- Skip duplicate j: if nums[j] == nums[j-1], skip
- Skip duplicate left: while nums[left] == nums[left+1], left++
- Skip duplicate right: while nums[right] == nums[right-1], right--

**Key Insight**: Integer Overflow Prevention
- With range -10^9 to 10^9, sum of two integers can overflow!
- Use `long` for intermediate sum calculation
- Or check overflow before addition

**Related Patterns**:
1. **Two Sum II** — Two pointers on sorted array
2. **3Sum** — Fix one element + two pointers
3. **4Sum** (this problem) — Fix two elements + two pointers
4. **kSum** — Generalize to k elements

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Try all quadruplets
  → Four nested loops
  → O(n⁴) time
  → Too slow for n=200 (1.6 billion operations)!

Sorting + Fix Two + Two Pointers:
  → Sort array: O(n log n)
  → Fix two elements: O(n²)
  → For each pair, two-pointer scan: O(n)
  → Total: O(n³)
  → Optimal! ✓
```

**The Reduction to Two Sum:**
```
4Sum: a + b + c + d = target
Rearrange: c + d = target - a - b

Algorithm:
  For each pair (a, b) (fixed):
    Find c and d such that c + d = target - a - b
    This is Two Sum II on the remaining sorted portion!
    
Sorted array enables:
  - Two pointers from both ends
  - Easy duplicate skipping
  - O(n) time for each fixed pair
```

### Step-by-Step Algorithm

---

#### **Approach 1: Sort + Fix Two + Two Pointers (OPTIMAL)**

**Core Idea**:
- Sort the array
- Fix first element with outer loop (i)
- Fix second element with second loop (j > i)
- Use two pointers (left, right) to find pairs that sum to target - nums[i] - nums[j]
- Skip duplicates at all four positions

**Algorithm**
```
fourSum(nums, target):
    result = []
    sort(nums)
    n = length(nums)
    
    for i = 0 to n-4:
        // Skip duplicate i
        if i > 0 AND nums[i] == nums[i-1]:
            continue
        
        for j = i+1 to n-3:
            // Skip duplicate j
            if j > i+1 AND nums[j] == nums[j-1]:
                continue
            
            left = j + 1
            right = n - 1
            pairTarget = target - nums[i] - nums[j]
            
            while left < right:
                sum = nums[left] + nums[right]
                
                if sum == pairTarget:
                    result.add([nums[i], nums[j], nums[left], nums[right]])
                    
                    // Skip duplicate left
                    while left < right AND nums[left] == nums[left+1]:
                        left++
                    
                    // Skip duplicate right
                    while left < right AND nums[right] == nums[right-1]:
                        right--
                    
                    left++
                    right--
                    
                else if sum < pairTarget:
                    left++
                else:
                    right--
    
    return result
```

**Code Implementation**
```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);  // Sort array first
        int n = nums.length;
        
        for (int i = 0; i < n - 3; i++) {
            // Skip duplicate first elements
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            for (int j = i + 1; j < n - 2; j++) {
                // Skip duplicate second elements
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                
                int left = j + 1;
                int right = n - 1;
                
                // Use long to prevent overflow
                long pairTarget = (long) target - nums[i] - nums[j];
                
                while (left < right) {
                    long sum = (long) nums[left] + nums[right];
                    
                    if (sum == pairTarget) {
                        // Found a valid quadruplet
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        
                        // Skip duplicate left values
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        
                        // Skip duplicate right values
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        
                        // Move both pointers
                        left++;
                        right--;
                        
                    } else if (sum < pairTarget) {
                        // Sum too small, need larger value
                        left++;
                    } else {
                        // Sum too large, need smaller value
                        right--;
                    }
                }
            }
        }
        
        return result;
    }
}
```

**Example Walkthrough**

Input: `nums = [3,2,3,-3,1,0]`, `target = 3`

**Step 1: Sort**
```
[3,2,3,-3,1,0] → [-3,0,1,2,3,3]
```

**Step 2: Iterate with fixed elements**

| i | j | nums[i] | nums[j] | pairTarget | left | right | nums[left] | nums[right] | sum | Action | Quadruplet |
|---|---|---------|---------|------------|------|-------|------------|-------------|-----|--------|------------|
| 0 | 1 | -3 | 0 | 6 | 2 | 5 | 1 | 3 | 4 | sum < 6, left++ | — |
| 0 | 1 | -3 | 0 | 6 | 3 | 5 | 2 | 3 | 5 | sum < 6, left++ | — |
| 0 | 1 | -3 | 0 | 6 | 4 | 5 | 3 | 3 | 6 | sum == 6 ✓ | **[-3,0,3,3]** |
| 0 | 2 | -3 | 1 | 5 | 3 | 5 | 2 | 3 | 5 | sum == 5 ✓ | **[-3,1,2,3]** |
| 0 | 3 | -3 | 2 | 4 | 4 | 5 | 3 | 3 | 6 | sum > 4, right-- | — |
| 0 | 3 | -3 | 2 | 4 | 4 | 4 | — | — | — | left >= right | — |
| 0 | 4 | -3 | 3 | 3 | 5 | 5 | — | — | — | left >= right | — |
| 1 | 2 | 0 | 1 | 2 | 3 | 5 | 2 | 3 | 5 | sum > 2, right-- | — |
| 1 | 2 | 0 | 1 | 2 | 3 | 4 | 2 | 3 | 5 | sum > 2, right-- | — |
| 1 | 2 | 0 | 1 | 2 | 3 | 3 | — | — | — | left >= right | — |
| ... | ... | ... | ... | ... | ... | ... | ... | ... | ... | ... | — |

**Output:** `[[-3,0,3,3], [-3,1,2,3]]`

**Complexity Analysis**
- **Time Complexity**: O(n³) — O(n log n) sorting + O(n²) outer loops × O(n) two-pointer inner loop
- **Space Complexity**: O(1) or O(n) — O(1) if we don't count output, O(n) for sorting stack/output

---

#### **Approach 2: Brute Force (NOT OPTIMAL)**

**Core Idea**: Try all possible quadruplets with four nested loops.

**Code Implementation**
```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Set<List<Integer>> resultSet = new HashSet<>();
        int n = nums.length;
        
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    for (int l = k + 1; l < n; l++) {
                        long sum = (long) nums[i] + nums[j] + nums[k] + nums[l];
                        if (sum == target) {
                            List<Integer> quad = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);
                            Collections.sort(quad);
                            resultSet.add(quad);
                        }
                    }
                }
            }
        }
        
        return new ArrayList<>(resultSet);
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n⁴) — Four nested loops
- **Space Complexity**: O(n) — HashSet to store unique quadruplets
- **Why Not Optimal**: Too slow, doesn't scale for n=200

---

#### **Approach 3: HashMap (ALTERNATIVE)**

**Core Idea**: Fix two elements, use HashMap to find third and fourth elements.

**Code Implementation**
```java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Set<List<Integer>> resultSet = new HashSet<>();
        Arrays.sort(nums);
        int n = nums.length;
        
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                Map<Long, Integer> seen = new HashMap<>();
                
                for (int k = j + 1; k < n; k++) {
                    long complement = (long) target - nums[i] - nums[j] - nums[k];
                    
                    if (seen.containsKey(complement)) {
                        List<Integer> quad = Arrays.asList(nums[i], nums[j], (int) complement, nums[k]);
                        Collections.sort(quad);
                        resultSet.add(quad);
                    }
                    seen.put((long) nums[k], k);
                }
            }
        }
        
        return new ArrayList<>(resultSet);
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n³)
- **Space Complexity**: O(n) — HashMap for each fixed pair
- **Why Not Optimal**: Uses extra space, slower in practice than two pointers

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | HashMap | **Sort + Two Pointers** |
|-------------|-------------|---------|------------------------|
| Time complexity | O(n⁴) ❌ | O(n³) ✓ | **O(n³) ✅** |
| Space complexity | O(n) | O(n) | **O(1) ✅** |
| Handles duplicates | With HashSet | With HashSet | **Built-in ✅** |
| Code simplicity | Simple | Medium | **Clean ✅** |
| Optimal | ❌ | Partial | **✅** |

**Winner**: **Sort + Two Pointers** — optimal time and space, clean duplicate handling!

### Why Sorting is Essential?
```
Without sorting:
  - Can't use two pointers efficiently
  - Hard to skip duplicates
  - Need extra data structures

With sorting:
  - Two pointers work from both ends
  - Duplicates are adjacent → easy to skip
  - O(n³) time achievable
  - Cleaner code
```

### Why Fix Two Elements?
```
After fixing nums[i] and nums[j]:
  Need: nums[left] + nums[right] = target - nums[i] - nums[j]
  
  This is exactly Two Sum II on sorted array!
  
  Two pointers approach:
    - Start: left = j+1, right = n-1
    - If sum < pairTarget: left++ (need larger value)
    - If sum > pairTarget: right-- (need smaller value)
    - If sum == pairTarget: found quadruplet!
    
  Why it works:
    - Sorted array enables pointer movement based on sum
    - Each comparison eliminates one element
    - O(n) time for each fixed pair
```

### Comparison with 3Sum
```
3Sum:
  - Fix 1 element (i)
  - Two pointers for remaining
  - O(n²) time
  
4Sum:
  - Fix 2 elements (i, j)
  - Two pointers for remaining
  - O(n³) time
  
Pattern: kSum → O(n^(k-1)) time
```

---

## Critical Edge Cases & Gotchas

### 1. **All Same Elements**
```java
Input: nums = [0,0,0,0], target = 0
Output: [[0,0,0,0]]
Explanation: Only one unique quadruplet [0,0,0,0].
```

### 2. **No Valid Quadruplets**
```java
Input: nums = [1,2,3,4], target = 100
Output: []
Explanation: No combination sums to 100.
```

### 3. **Minimum Size (n=4)**
```java
Input: nums = [1,2,3,4], target = 10
Output: [[1,2,3,4]]
Explanation: Exact four elements sum to 10.
```

### 4. **Multiple Duplicates**
```java
Input: nums = [1,1,1,1,1,1], target = 4
Output: [[1,1,1,1]]
Explanation: Multiple ways to form [1,1,1,1], but only count once.
```

### 5. **Mix of Positive and Negative**
```java
Input: nums = [-2,-1,0,1,2], target = 0
Output: [[-2,-1,1,2],[-1,0,0,1]]
Explanation: Two unique quadruplets sum to 0.
```

### 6. **Large Numbers (Overflow)**
```java
Input: nums = [1000000000,1000000000,1000000000,1000000000], target = -294967296
Output: []
Explanation: Use long to prevent overflow in sum calculation.
```

### 7. **Three Elements Same**
```java
Input: nums = [-1,-1,-1,2], target = -1
Output: [[-1,-1,-1,2]]
Explanation: Valid quadruplet with three same elements.
```

### 8. **Target Zero**
```java
Input: nums = [-3,-2,-1,0,0,1,2,3], target = 0
Output: [[-3,-2,2,3],[-3,-1,1,3],[-3,0,0,3],[-3,0,1,2],[-2,-1,0,3],[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
Explanation: Many combinations sum to 0.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Sorting the Array**
```java
// WRONG - no sorting
public List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> result = new ArrayList<>();
    // Directly using two pointers without sorting!
    for (int i = 0; i < nums.length - 3; i++) {
        for (int j = i + 1; j < nums.length - 2; j++) {
            int left = j + 1, right = nums.length - 1;
            // This won't work on unsorted array!
        }
    }
    return result;
}
```

**Why wrong**: Two pointers only work on sorted arrays! Without sorting, pointer movement based on sum comparison is meaningless.

**Fix**: Always sort first
```java
Arrays.sort(nums);
```

### ❌ **MISTAKE 2: Integer Overflow**
```java
// WRONG - can cause overflow
int pairTarget = target - nums[i] - nums[j];  // OVERFLOW!
int sum = nums[left] + nums[right];           // OVERFLOW!
```

**Why wrong**: With range -10^9 to 10^9, adding two integers can overflow!

**Dry run failure for nums=[1000000000, 1000000000, -1000000000, -1000000000], target=0:**
```
i=0, j=1: nums[i]=10^9, nums[j]=10^9
pairTarget = 0 - 10^9 - 10^9 = -2×10^9 (OVERFLOW! Wraps to positive)
Result: Wrong comparison, miss valid quadruplets
```

**Fix**: Use `long` for intermediate calculations
```java
long pairTarget = (long) target - nums[i] - nums[j];
long sum = (long) nums[left] + nums[right];
```

### ❌ **MISTAKE 3: Not Skipping Duplicate Fixed Elements**
```java
// WRONG - allows duplicate i and j
for (int i = 0; i < nums.length - 3; i++) {
    // Missing: if (i > 0 && nums[i] == nums[i-1]) continue;
    for (int j = i + 1; j < nums.length - 2; j++) {
        // Missing: if (j > i+1 && nums[j] == nums[j-1]) continue;
    }
}
```

**Why wrong**: Same fixed pair will produce duplicate quadruplets!

**Dry run failure for nums=[-1,-1,0,0,1,1], target=0:**
```
i=0, j=2: [-1,0,...] → finds quadruplet [-1,-1,0,1,1] (wrong!)
i=1, j=3: [-1,0,...] → finds same quadruplet again (DUPLICATE!)
```

**Fix**: Skip duplicate fixed elements
```java
if (i > 0 && nums[i] == nums[i - 1]) continue;
if (j > i + 1 && nums[j] == nums[j - 1]) continue;
```

### ❌ **MISTAKE 4: Not Skipping Duplicate Pointers**
```java
// WRONG - doesn't skip duplicate left/right
if (sum == pairTarget) {
    result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
    left++;   // Just move once
    right--;  // Just move once
}
```

**Why wrong**: Multiple pairs with same values will create duplicate quadruplets!

**Dry run failure for nums=[-2,0,0,0,0,2], target=0:**
```
Fixed i=-2, j=2 (0), pairTarget=2
left=3 (0), right=5 (2) → sum=2 → add [-2,0,0,2]
left=4 (0), right=4 (0) → incorrect processing
Result includes duplicates!
```

**Fix**: Skip duplicates after finding match
```java
if (sum == pairTarget) {
    result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
    
    while (left < right && nums[left] == nums[left + 1]) left++;
    while (left < right && nums[right] == nums[right - 1]) right--;
    
    left++;
    right--;
}
```

### ❌ **MISTAKE 5: Wrong Loop Bounds**
```java
// WRONG - incorrect loop bounds
for (int i = 0; i < nums.length; i++) {
    for (int j = i + 1; j < nums.length; j++) {
        int left = j + 1;
        int right = nums.length - 1;
        // When i=n-3, j=n-2, left=n-1, right=n-1 (no space for quadruplet!)
    }
}
```

**Why wrong**: Need at least 4 elements total. If loops go too far, no room for all four positions!

**Fix**: Correct loop bounds
```java
for (int i = 0; i < nums.length - 3; i++) {
    for (int j = i + 1; j < nums.length - 2; j++) {
        // Now guaranteed to have space for left and right
    }
}
```

### ❌ **MISTAKE 6: Using HashSet to Remove Duplicates**
```java
// WRONG - inefficient duplicate removal
Set<List<Integer>> resultSet = new HashSet<>();
// ... find quadruplets and add to set
return new ArrayList<>(resultSet);
```

**Why wrong**: Wastes O(n) space and slower than skipping duplicates inline!

**Better approach**: Skip duplicates during iteration (as shown in correct solution)

### ❌ **MISTAKE 7: Wrong Duplicate Skip Condition for j**
```java
// WRONG - incorrect condition for skipping j
if (j > 0 && nums[j] == nums[j - 1]) {
    continue;  // Should be j > i + 1, not j > 0!
}
```

**Why wrong**: When j = i+1, skipping based on j-1 (which is i) is incorrect logic!

**Dry run failure:**
```
nums = [-1,-1,0,1], target = 0
i=0, j=1: nums[j]=-1, nums[j-1]=nums[0]=-1
Skip j? With wrong condition j > 0 → YES, SKIP (WRONG!)
We should NOT skip because j is the first j for this i!
```

**Fix**: Use correct condition
```java
if (j > i + 1 && nums[j] == nums[j - 1]) {
    continue;
}
```

---

## Complexity Analysis

### Time Complexity: **O(n³)**

| Operation | Time | Reason |
|-----------|------|--------|
| Sorting | O(n log n) | Built-in sort |
| Outer loop (i) | O(n) | Iterate through each element |
| Second loop (j) | O(n) | For each i, iterate remaining |
| Two-pointer inner loop | O(n) | At most n iterations per fixed pair |
| Duplicate skipping | O(1) amortized | Each element visited at most twice |
| **Total** | **O(n³)** | Dominated by nested loops |

### Space Complexity: **O(1) or O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Sorting (depends on implementation) | O(log n) to O(n) | Quicksort stack or merge sort |
| Pointer variables | O(1) | Few integers |
| Output list | O(k) where k = quadruplets | Not counted as auxiliary space |
| **Total** | **O(1)** | Excluding output, O(log n) to O(n) for sorting |

**Why O(n³) Time is Optimal:**
- Must examine combinations of elements
- For 4Sum, O(n³) is optimal without additional constraints
- Can't reduce further without sacrificing correctness

---

## Visualization

### Example Walkthrough

**Input:** `nums = [3,2,3,-3,1,0]`, `target = 3`

**Step 1: Sort**
```
Original: [3, 2, 3, -3, 1, 0]
Sorted:   [-3, 0, 1, 2, 3, 3]
           0  1  2  3  4  5  (indices)
```

**Step 2: Fix i=0, j=1 (nums[0]=-3, nums[1]=0), pairTarget=6**
```
[-3, 0, 1, 2, 3, 3]
  ↑  ↑  ↑        ↑
  i  j  L        R

Sum = 1 + 3 = 4 < 6 → L++

[-3, 0, 1, 2, 3, 3]
  ↑  ↑     ↑     ↑
  i  j     L     R

Sum = 2 + 3 = 5 < 6 → L++

[-3, 0, 1, 2, 3, 3]
  ↑  ↑        ↑  ↑
  i  j        L  R

Sum = 3 + 3 = 6 == 6 ✓
Quadruplet: [-3, 0, 3, 3]

Skip duplicates and move:
L++, R-- → L >= R, exit inner loop
```

**Step 3: Fix i=0, j=2 (nums[0]=-3, nums[2]=1), pairTarget=5**
```
[-3, 0, 1, 2, 3, 3]
  ↑     ↑  ↑     ↑
  i     j  L     R

Sum = 2 + 3 = 5 == 5 ✓
Quadruplet: [-3, 1, 2, 3]

Skip duplicates:
nums[L]=2, nums[L+1]=3 (different)
nums[R]=3, nums[R-1]=3 (same!) → R--
L++, R--

[-3, 0, 1, 2, 3, 3]
  ↑     ↑     ↑
  i     j     L,R

L >= R, exit inner loop
```

**Step 4: Continue with other i, j combinations...**
```
i=0, j=3: pairTarget=4 → no matches
i=0, j=4: left >= right immediately
i=1, j=2: pairTarget=2 → no matches
...
```

**Result:** `[[-3,0,3,3], [-3,1,2,3]]`

### Why Fix Two Elements + Two Pointers Works

```
4Sum = Fix Two + 2Sum

Outer structure:
  for i:           Fix first element
    for j > i:     Fix second element
      Two pointers: Solve 2Sum for remaining

Example:
[-3, 0, 1, 2, 3, 3]
  i  j
  
After fixing i=0, j=1:
  nums[i] + nums[j] + nums[left] + nums[right] = target
  -3 + 0 + nums[left] + nums[right] = 3
  nums[left] + nums[right] = 6
  
This is 2Sum on [1, 2, 3, 3] with target 6!

Two pointers approach:
  left=2, right=5: 1+3=4 < 6 → left++
  left=3, right=5: 2+3=5 < 6 → left++
  left=4, right=5: 3+3=6 ✓ Found!
```

---

## Comparison of Approaches

| Approach | Time | Space | Handles Duplicates | Handles Overflow | Optimal |
|----------|------|-------|-------------------|------------------|---------|
| Brute Force | O(n⁴) | O(n) | With HashSet | Need long | ❌ |
| HashMap | O(n³) | O(n) | With HashSet | Need long | Partial |
| **Sort + Two Pointers** | **O(n³)** | **O(1)** | **Built-in ✅** | **Need long ✅** | **✅** |

**Recommendation**: Always use **Sort + Fix Two + Two Pointers** — it's the optimal solution!

---

## Key Takeaways

1. **4Sum extends 3Sum pattern** — fix two elements instead of one
2. **Sort first** — enables two pointers and easy duplicate handling
3. **Four levels of duplicate skipping** — i, j, left, right (critical!)
4. **Use long for overflow** — sum of two integers can overflow with large values
5. **O(n³) time is optimal** — can't do better for general 4Sum
6. **Correct j skip condition** — j > i+1, not j > 0
7. **Loop bounds matter** — i to n-4, j to n-3

---

## Interview Tips

**What to say in an interview:**

> "This is 4Sum, which extends the 3Sum pattern. I'll first sort the array in O(n log n). Then I'll use two nested loops to fix the first two elements. For the remaining portion, I'll use two pointers to find pairs that sum to target minus the two fixed elements. This reduces 4Sum to 2Sum on a sorted array. I need to skip duplicates at all four positions to avoid duplicate quadruplets. Since the value range is -10^9 to 10^9, I'll use long for intermediate sums to prevent overflow. The overall complexity is O(n³) time and O(1) space excluding output."

**Key points to mention:**
1. **Extension of 3Sum** — fix two elements, solve 2Sum for others
2. **Why sorting** — enables two pointers and duplicate handling
3. **Four levels of duplicate skipping** — i, j, left, right
4. **Overflow prevention** — use long for large value ranges
5. **Complexity** — O(n³) time (optimal), O(1) space excluding output

**If asked about alternatives:**
> "I could use a HashMap approach which is also O(n³) but uses O(n) extra space. Or brute force with four loops is O(n⁴) which is too slow. The sort + two pointers approach is optimal because it achieves O(n³) time with O(1) space and handles duplicates naturally."

**Common Follow-ups:**
- "What if we need kSum?" → Generalize: fix k-2 elements, two pointers for remaining, O(n^(k-1))
- "What about 3Sum?" → Remove one level of nesting, O(n²)
- "Can you do better than O(n³)?" → No, this is optimal for general 4Sum

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| Two Sum | Easy | HashMap | Unsorted, find pair |
| Two Sum II | Easy | Two Pointers | Sorted, find pair |
| 3Sum | Medium | Sort + Two Pointers | Fix one element |
| **4Sum** | Medium | **Sort + Two Pointers** | **This problem** ← **Fix two elements** |
| 4Sum II | Medium | HashMap | Four arrays, different problem |
| kSum | Hard | Recursion + Two Pointers | Generalize to k elements |

**Pattern Progression**:
1. **Two Sum** — HashMap for unsorted, O(n)
2. **Two Sum II** — Two pointers for sorted, O(n)
3. **3Sum** — Fix one + two pointers, O(n²)
4. **4Sum** (this problem) — Fix two + two pointers, O(n³)
5. **kSum** — Fix k-2 + two pointers, O(n^(k-1))

---

## Final Pattern Label

✅ **Sorting + Nested Loops + Two Pointers (Fix Two Elements + Two Sum II)**

**Remember:** Sort first. Fix two elements with nested loops. Use two pointers to find pairs that sum to target minus the two fixed elements. Skip duplicates at all four positions (i, j, left, right). Use long to prevent overflow. O(n³) time, O(1) space. This is the optimal solution!
