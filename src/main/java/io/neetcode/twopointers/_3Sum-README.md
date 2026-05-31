# 3Sum

## Problem Description

**Difficulty**: Medium

Given an integer array `nums`, return all the triplets `[nums[i], nums[j], nums[k]]` where `nums[i] + nums[j] + nums[k] == 0`, and the indices `i`, `j`, and `k` are all distinct.

The output should not contain any duplicate triplets. You may return the output and the triplets in any order.

## Examples

### Example 1:
```
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
Explanation:
- nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0
- nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0
- nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0
- The distinct triplets are [-1,0,1] and [-1,-1,2]
```

### Example 2:
```
Input: nums = [0,1,1]
Output: []
Explanation: The only possible triplet does not sum up to 0.
```

### Example 3:
```
Input: nums = [0,0,0]
Output: [[0,0,0]]
Explanation: The only possible triplet sums up to 0.
```

## Constraints
- 3 <= nums.length <= 1000
- -10^5 <= nums[i] <= 10^5

**Recommended Complexity**: O(n²) time, O(1) space (excluding output)

---

## Pattern Recognition

**Primary Pattern**: **Sorting + Two Pointers (Fix One Element + Two Sum II)**

**Why This Pattern?**
- Need to find three numbers that sum to target (0)
- Must avoid duplicate triplets
- Sorting enables two-pointer technique and easy duplicate skipping
- 3Sum reduces to fixing one element and solving 2Sum for remaining elements

**Key Insight**: 3Sum = Fixed Element + Two Sum II
```
Original Problem: Find i, j, k such that nums[i] + nums[j] + nums[k] = 0

Rearrange: nums[j] + nums[k] = -nums[i]

Strategy:
  1. Sort the array
  2. Fix nums[i] (outer loop)
  3. Find nums[j] + nums[k] = -nums[i] using two pointers
  4. This is exactly the Two Sum II problem on sorted array!
  
Example:
  nums = [-4, -1, -1, 0, 1, 2] (sorted)
  
  Fix i=0: nums[0]=-4, target = -(-4) = 4
  Find two numbers in [-1,-1,0,1,2] that sum to 4
  
  Fix i=1: nums[1]=-1, target = -(-1) = 1
  Find two numbers in [-1,0,1,2] that sum to 1
  Found: -1 + 2 = 1 → triplet [-1,-1,2]
         0 + 1 = 1 → triplet [-1,0,1]
```

**Key Insight**: Duplicate Handling
- After sorting, duplicates are adjacent
- Skip duplicate fixed elements: if nums[i] == nums[i-1], skip
- Skip duplicate left pointers: while nums[left] == nums[left+1], left++
- Skip duplicate right pointers: while nums[right] == nums[right-1], right--

**Related Patterns**:
1. **Two Sum II** — Two pointers on sorted array
2. **4Sum** — Extend to four elements
3. **3Sum Closest** — Find triplet closest to target
4. **3Sum Smaller** — Count triplets with sum less than target

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Try all triplets
  → Three nested loops
  → O(n³) time
  → Too slow for n=1000 (1 billion operations)!

Sorting + Two Pointers:
  → Sort array: O(n log n)
  → For each element, two-pointer scan: O(n)
  → Total: O(n²)
  → Optimal! ✓
```

**The Reduction to Two Sum:**
```
3Sum: a + b + c = 0
Rearrange: b + c = -a

Algorithm:
  For each element a (fixed):
    Find b and c such that b + c = -a
    This is Two Sum II on the remaining sorted portion!
    
Sorted array enables:
  - Two pointers from both ends
  - Easy duplicate skipping
  - O(n) time for each fixed element
```

### Step-by-Step Algorithm

---

#### **Approach 1: Sort + Two Pointers (OPTIMAL)**

**Core Idea**:
- Sort the array
- Fix first element with outer loop (i)
- Use two pointers (left, right) to find pairs that sum to -nums[i]
- Skip duplicates at all three positions

**Algorithm**
```
threeSum(nums):
    result = []
    sort(nums)
    
    for i = 0 to n-3:
        // Skip duplicate fixed elements
        if i > 0 AND nums[i] == nums[i-1]:
            continue
        
        left = i + 1
        right = n - 1
        target = -nums[i]
        
        while left < right:
            sum = nums[left] + nums[right]
            
            if sum == target:
                result.add([nums[i], nums[left], nums[right]])
                
                // Skip duplicate left values
                while left < right AND nums[left] == nums[left+1]:
                    left++
                
                // Skip duplicate right values
                while left < right AND nums[right] == nums[right-1]:
                    right--
                
                left++
                right--
                
            else if sum < target:
                left++
            else:
                right--
    
    return result
```

**Code Implementation**
```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);  // Sort array first
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicate fixed elements
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i];  // We want nums[left] + nums[right] = target
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    // Found a valid triplet
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
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
                    
                } else if (sum < target) {
                    // Sum too small, need larger value
                    left++;
                } else {
                    // Sum too large, need smaller value
                    right--;
                }
            }
        }
        
        return result;
    }
}
```

**Example Walkthrough**

Input: `nums = [-1,0,1,2,-1,-4]`

**Step 1: Sort**
```
[-1,0,1,2,-1,-4] → [-4,-1,-1,0,1,2]
```

**Step 2: Iterate with fixed element**

| i | nums[i] | target | left | right | nums[left] | nums[right] | sum | Action | Triplet |
|---|---------|--------|------|-------|------------|-------------|-----|--------|---------|
| 0 | -4 | 4 | 1 | 5 | -1 | 2 | 1 | sum < 4, left++ | — |
| 0 | -4 | 4 | 2 | 5 | -1 | 2 | 1 | sum < 4, left++ | — |
| 0 | -4 | 4 | 3 | 5 | 0 | 2 | 2 | sum < 4, left++ | — |
| 0 | -4 | 4 | 4 | 5 | 1 | 2 | 3 | sum < 4, left++ | — |
| 0 | -4 | 4 | 5 | 5 | — | — | — | left >= right | — |
| **1** | **-1** | **1** | **2** | **5** | **-1** | **2** | **1** | **sum == 1** ✓ | **[-1,-1,2]** |
| 1 | -1 | 1 | 3 | 4 | 0 | 1 | 1 | sum == 1 ✓ | **[-1,0,1]** |
| 1 | -1 | 1 | 4 | 4 | — | — | — | left >= right | — |
| 2 | -1 | — | — | — | — | — | — | Skip duplicate | — |
| 3 | 0 | 0 | 4 | 5 | 1 | 2 | 3 | sum > 0, right-- | — |
| 3 | 0 | 0 | 4 | 4 | — | — | — | left >= right | — |

**Output:** `[[-1,-1,2], [-1,0,1]]`

**Complexity Analysis**
- **Time Complexity**: O(n²) — O(n log n) sorting + O(n) outer loop × O(n) two-pointer inner loop
- **Space Complexity**: O(1) or O(n) — O(1) if we don't count output, O(n) for sorting stack/output

---

#### **Approach 2: Brute Force (NOT OPTIMAL)**

**Core Idea**: Try all possible triplets with three nested loops.

**Code Implementation**
```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> resultSet = new HashSet<>();
        int n = nums.length;
        
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(triplet);
                        resultSet.add(triplet);
                    }
                }
            }
        }
        
        return new ArrayList<>(resultSet);
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n³) — Three nested loops
- **Space Complexity**: O(n) — HashSet to store unique triplets
- **Why Not Optimal**: Too slow, doesn't scale for n=1000

---

#### **Approach 3: HashMap (ALTERNATIVE)**

**Core Idea**: For each pair (i, j), use HashMap to find third element.

**Code Implementation**
```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> resultSet = new HashSet<>();
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            Set<Integer> seen = new HashSet<>();
            
            for (int j = i + 1; j < nums.length; j++) {
                int complement = -(nums[i] + nums[j]);
                
                if (seen.contains(complement)) {
                    List<Integer> triplet = Arrays.asList(nums[i], complement, nums[j]);
                    Collections.sort(triplet);
                    resultSet.add(triplet);
                }
                seen.add(nums[j]);
            }
        }
        
        return new ArrayList<>(resultSet);
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n²)
- **Space Complexity**: O(n) — HashMap for each fixed element
- **Why Not Optimal**: Uses extra space, slower in practice than two pointers

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | HashMap | **Sort + Two Pointers** |
|-------------|-------------|---------|------------------------|
| Time complexity | O(n³) ❌ | O(n²) ✓ | **O(n²) ✅** |
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
  - O(n²) time achievable
  - Cleaner code
```

### Why Two Pointers After Fixing One Element?
```
After fixing nums[i]:
  Need: nums[left] + nums[right] = -nums[i]
  
  This is exactly Two Sum II on sorted array!
  
  Two pointers approach:
    - Start: left = i+1, right = n-1
    - If sum < target: left++ (need larger value)
    - If sum > target: right-- (need smaller value)
    - If sum == target: found triplet!
    
  Why it works:
    - Sorted array enables pointer movement based on sum
    - Each comparison eliminates one element
    - O(n) time for each fixed element
```

---

## Critical Edge Cases & Gotchas

### 1. **All Same Elements**
```java
Input: nums = [0,0,0,0]
Output: [[0,0,0]]
Explanation: Only one unique triplet [0,0,0].
```

### 2. **No Valid Triplets**
```java
Input: nums = [1,2,3]
Output: []
Explanation: All positive, can't sum to 0.
```

### 3. **All Negative**
```java
Input: nums = [-3,-2,-1]
Output: []
Explanation: All negative, can't sum to 0.
```

### 4. **Mix with Duplicates**
```java
Input: nums = [-2,0,0,2,2]
Output: [[-2,0,2]]
Explanation: Multiple ways to form [-2,0,2], but only count once.
```

### 5. **Minimum Size (n=3)**
```java
Input: nums = [0,0,0]
Output: [[0,0,0]]
Explanation: Exact triplet, all zeros.
```

### 6. **Two Elements Same**
```java
Input: nums = [-1,-1,2]
Output: [[-1,-1,2]]
Explanation: Valid triplet with two same elements.
```

### 7. **Large Range**
```java
Input: nums = [-100000, 50000, 50000]
Output: [[-100000, 50000, 50000]]
Explanation: Works with extreme values.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Sorting the Array**
```java
// WRONG - no sorting
public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    // Directly using two pointers without sorting!
    for (int i = 0; i < nums.length - 2; i++) {
        int left = i + 1, right = nums.length - 1;
        // This won't work on unsorted array!
    }
    return result;
}
```

**Why wrong**: Two pointers only work on sorted arrays! Without sorting, pointer movement based on sum comparison is meaningless.

**Fix**: Always sort first
```java
Arrays.sort(nums);
```

### ❌ **MISTAKE 2: Not Skipping Duplicate Fixed Elements**
```java
// WRONG - allows duplicate fixed elements
for (int i = 0; i < nums.length - 2; i++) {
    // Missing: if (i > 0 && nums[i] == nums[i-1]) continue;
    int left = i + 1, right = nums.length - 1;
    // ... rest of code
}
```

**Why wrong**: Same fixed element will produce duplicate triplets!

**Dry run failure for nums=[-1,-1,0,1]:**
```
i=0: nums[0]=-1 → finds triplet [-1,0,1]
i=1: nums[1]=-1 → finds same triplet [-1,0,1] again (DUPLICATE!)
```

**Fix**: Skip duplicate fixed elements
```java
if (i > 0 && nums[i] == nums[i - 1]) {
    continue;
}
```

### ❌ **MISTAKE 3: Not Skipping Duplicate Left/Right Pointers**
```java
// WRONG - doesn't skip duplicate pointers
if (sum == target) {
    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
    left++;   // Just move once
    right--;  // Just move once
}
```

**Why wrong**: Multiple pairs with same values will create duplicate triplets!

**Dry run failure for nums=[-2,0,0,0,2]:**
```
Fixed i=-2, target=2
left=1 (0), right=4 (2) → sum=2 → add [-2,0,2]
left=2 (0), right=3 (0) → incorrect processing
Result includes duplicates!
```

**Fix**: Skip duplicates after finding match
```java
if (sum == target) {
    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
    
    while (left < right && nums[left] == nums[left + 1]) left++;
    while (left < right && nums[right] == nums[right - 1]) right--;
    
    left++;
    right--;
}
```

### ❌ **MISTAKE 4: Wrong Loop Bounds**
```java
// WRONG - i goes to n-1
for (int i = 0; i < nums.length; i++) {
    int left = i + 1;
    int right = nums.length - 1;
    // When i = n-2, left = n-1, right = n-1 (no space for triplet!)
}
```

**Why wrong**: Need at least 3 elements total. If i goes to n-1, no room for left and right!

**Fix**: Loop to n-3 (or n-2 with i < nums.length - 2)
```java
for (int i = 0; i < nums.length - 2; i++) { ... }
```

### ❌ **MISTAKE 5: Using HashSet to Remove Duplicates**
```java
// WRONG - inefficient duplicate removal
Set<List<Integer>> resultSet = new HashSet<>();
// ... find triplets and add to set
return new ArrayList<>(resultSet);
```

**Why wrong**: Wastes O(n) space and slower than skipping duplicates inline!

**Better approach**: Skip duplicates during iteration (as shown in correct solution)

### ❌ **MISTAKE 6: Forgetting to Move Pointers After Match**
```java
// WRONG - infinite loop
if (sum == target) {
    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
    // Missing: left++; right--;
    // Infinite loop because pointers don't move!
}
```

**Why wrong**: Pointers stay at same position, checking same triplet forever!

**Fix**: Always move both pointers after finding match
```java
left++;
right--;
```

---

## Complexity Analysis

### Time Complexity: **O(n²)**

| Operation | Time | Reason |
|-----------|------|--------|
| Sorting | O(n log n) | Built-in sort |
| Outer loop | O(n) | Iterate through each element |
| Two-pointer inner loop | O(n) | At most n iterations per fixed element |
| Duplicate skipping | O(1) amortized | Each element visited at most twice |
| **Total** | **O(n²)** | Dominated by nested loops |

### Space Complexity: **O(1) or O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Sorting (depends on implementation) | O(log n) to O(n) | Quicksort stack or merge sort |
| Pointer variables | O(1) | Few integers |
| Output list | O(k) where k = triplets | Not counted as auxiliary space |
| **Total** | **O(1)** | Excluding output, O(log n) to O(n) for sorting |

**Why O(n²) Time is Optimal:**
- Must examine combinations of elements
- Can't do better than O(n²) for this problem without additional constraints
- Sort + two pointers is the most efficient approach

---

## Visualization

### Example Walkthrough

**Input:** `nums = [-1,0,1,2,-1,-4]`

**Step 1: Sort**
```
Original: [-1, 0, 1, 2, -1, -4]
Sorted:   [-4, -1, -1, 0, 1, 2]
           0   1   2  3  4  5  (indices)
```

**Step 2: Fix i=0, nums[0]=-4, target=4**
```
[-4, -1, -1, 0, 1, 2]
  ↑   ↑              ↑
  i   L              R

Sum = -1 + 2 = 1 < 4 → L++

[-4, -1, -1, 0, 1, 2]
  ↑       ↑          ↑
  i       L          R

Sum = -1 + 2 = 1 < 4 → L++

Continue... No triplet found for i=0
```

**Step 3: Fix i=1, nums[1]=-1, target=1**
```
[-4, -1, -1, 0, 1, 2]
      ↑   ↑          ↑
      i   L          R

Sum = -1 + 2 = 1 == 1 ✓
Triplet: [-1, -1, 2]

Skip duplicates:
nums[L]=nums[L+1]=-1, so L++ to 3
R stays at 5

[-4, -1, -1, 0, 1, 2]
      ↑       ↑      ↑
      i       L      R

Move both: L++, R--

[-4, -1, -1, 0, 1, 2]
      ↑          ↑  ↑
      i          L  R

Sum = 0 + 1 = 1 == 1 ✓
Triplet: [-1, 0, 1]

L++, R-- → L >= R, exit inner loop
```

**Step 4: i=2, nums[2]=-1**
```
Skip because nums[2] == nums[1] (duplicate)
```

**Step 5: Fix i=3, nums[3]=0, target=0**
```
[-4, -1, -1, 0, 1, 2]
              ↑  ↑  ↑
              i  L  R

Sum = 1 + 2 = 3 > 0 → R--

L >= R, exit inner loop
```

**Result:** `[[-1,-1,2], [-1,0,1]]`

### Why Two Pointers Work

```
Sorted array allows intelligent pointer movement:

If sum < target:
  Need larger sum → move left pointer right (larger value)
  
If sum > target:
  Need smaller sum → move right pointer left (smaller value)
  
If sum == target:
  Found triplet! Skip duplicates and move both

Example:
[-4, -1, -1, 0, 1, 2]
      ↑           ↑
      L           R
      
Sum = -1 + 2 = 1
If target > 1: need larger sum → L++
If target < 1: need smaller sum → R--
If target == 1: found it! ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Handles Duplicates | Optimal |
|----------|------|-------|-------------------|---------|
| Brute Force | O(n³) | O(n) | With HashSet | ❌ |
| HashMap | O(n²) | O(n) | With HashSet | Partial |
| **Sort + Two Pointers** | **O(n²)** | **O(1)** | **Built-in ✅** | **✅** |

**Recommendation**: Always use **Sort + Two Pointers** — it's the optimal solution!

---

## Key Takeaways

1. **3Sum reduces to 2Sum** — fix one element, find two that sum to its negative
2. **Sort first** — enables two pointers and easy duplicate handling
3. **Three levels of duplicate skipping** — fixed element, left pointer, right pointer
4. **Two pointers from both ends** — move based on sum comparison
5. **O(n²) time is optimal** — can't do better for this problem
6. **Skip duplicates inline** — don't use HashSet, skip during iteration
7. **Check i > 0 before skipping** — avoid checking nums[i-1] when i=0

---

## Interview Tips

**What to say in an interview:**

> "This is a classic 3Sum problem that reduces to 2Sum. I'll first sort the array which takes O(n log n). Then I'll fix the first element with an outer loop and use two pointers on the remaining sorted portion to find pairs that sum to the negative of the fixed element. This is exactly the Two Sum II problem. I'll skip duplicates at all three positions to avoid duplicate triplets. The overall complexity is O(n²) time and O(1) space excluding output."

**Key points to mention:**
1. **Reduction to 2Sum** — fix one element, solve 2Sum for others
2. **Why sorting** — enables two pointers and duplicate handling
3. **Duplicate skipping** — three places: fixed element, left pointer, right pointer
4. **Two-pointer logic** — move based on sum comparison with target
5. **Complexity** — O(n²) time (optimal), O(1) space excluding output

**If asked about alternatives:**
> "I could use a HashMap approach which is also O(n²) but uses O(n) extra space. Or brute force with three loops is O(n³) which is too slow. The sort + two pointers approach is optimal because it achieves O(n²) time with O(1) space and handles duplicates naturally."

**Common Follow-ups:**
- "What if we need 4Sum?" → Extend pattern: fix two elements, two pointers for remaining
- "What about 3Sum closest?" → Same structure, track minimum difference
- "Can you do better than O(n²)?" → No, this is optimal for general case

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| Two Sum | Easy | HashMap | Unsorted, find pair |
| Two Sum II | Easy | Two Pointers | Sorted, find pair |
| **3Sum** | Medium | **Sort + Two Pointers** | **This problem** ← |
| 3Sum Closest | Medium | Sort + Two Pointers | Find closest sum, not exact |
| 3Sum Smaller | Medium | Sort + Two Pointers | Count triplets less than target |
| 4Sum | Medium | Sort + Two Pointers | Four elements, fix two |

**Pattern Progression**:
1. **Two Sum** — HashMap for unsorted, O(n)
2. **Two Sum II** — Two pointers for sorted, O(n)
3. **3Sum** (this problem) — Fix one + two pointers, O(n²)
4. **4Sum** — Fix two + two pointers, O(n³)

---

## Final Pattern Label

✅ **Sorting + Two Pointers (Fixed Element + Two Sum II)**

**Remember:** Sort first. Fix one element (outer loop). Use two pointers to find pairs that sum to negative of fixed element. Skip duplicates at all three positions (fixed, left, right). O(n²) time, O(1) space. This is the optimal solution!
