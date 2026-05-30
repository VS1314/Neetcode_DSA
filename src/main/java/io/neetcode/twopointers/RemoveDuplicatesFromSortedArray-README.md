# Remove Duplicates From Sorted Array

## Problem Description

**Difficulty**: Easy

You are given an integer array `nums` sorted in **non-decreasing order**. Your task is to remove duplicates from `nums` **in-place** so that each element appears only once.

After removing the duplicates, return the number of unique elements, denoted as `k`, such that the first `k` elements of `nums` contain the unique elements.

**Note:**
- The order of the unique elements should remain the same as in the original array
- It is not necessary to consider elements beyond the first `k` positions of the array
- To be accepted, the first `k` elements of `nums` must contain all the unique elements

**Return `k` as the final result.**

## Examples

### Example 1:
```
Input: nums = [1,1,2,3,4]
Output: k = 4, nums = [1,2,3,4,_]
Explanation: 
- Unique elements: [1,2,3,4]
- Return k = 4 as we have four unique elements
- First 4 elements of nums contain the unique values
```

### Example 2:
```
Input: nums = [2,10,10,30,30,30]
Output: k = 3, nums = [2,10,30,_,_,_]
Explanation: 
- Unique elements: [2,10,30]
- Return k = 3 as we have three unique elements
- Elements beyond k don't matter
```

### Example 3:
```
Input: nums = [1,1,1,1,1]
Output: k = 1, nums = [1,_,_,_,_]
Explanation:
- Only one unique element: 1
- Return k = 1
```

## Constraints
- 1 <= nums.length <= 30,000
- -100 <= nums[i] <= 100
- nums is sorted in non-decreasing order

---

## Pattern Recognition

**Primary Pattern**: **Two Pointers - Slow/Fast (In-Place Array Modification)**

**Why This Pattern?**
- Array is sorted (duplicates are adjacent)
- Must modify in-place (no extra array allowed)
- Need to maintain relative order
- Two pointers: one for writing unique elements, one for reading/scanning

**Key Insight**: Why Two Pointers?
```
Sorted array means duplicates are ADJACENT:
  [1, 1, 1, 2, 2, 3] ← all 1's together, all 2's together

Strategy:
  - Slow pointer (i): position to write next unique element
  - Fast pointer (j): scans array to find next unique element
  - Compare nums[j] with previous unique element
  - If different → copy to position i, increment i
  - Result: first i elements contain all unique values

Why it works:
  - Sorted → duplicates are consecutive
  - Fast pointer finds unique values
  - Slow pointer overwrites duplicates with unique values
```

**Key Insight**: Slow-Fast Pointer Pattern
- **Slow pointer (i)**: Next position to place a unique element
- **Fast pointer (j)**: Scans through array looking for unique elements
- When nums[j] != nums[j-1] (or nums[i-1]), we found a unique element
- Copy nums[j] to nums[i], then increment i

**Related Patterns**:
1. **Remove Element** — Similar two-pointer in-place modification
2. **Move Zeroes** — Slow-fast pointers to partition array
3. **Remove Duplicates from Sorted Array II** — Allow duplicates up to k times
4. **Partition Array** — Two pointers for in-place partitioning

---

## Algorithm & Approach

### Core Insight

**Why Extra Array Doesn't Work:**
```
Using extra array:
  → Create new array for unique elements
  → Copy unique values to it
  → O(n) space - violates in-place requirement!

Using two pointers in-place:
  → Slow pointer tracks write position
  → Fast pointer scans for unique elements
  → Overwrite duplicates with unique values
  → O(1) space - optimal! ✓
```

**The Two-Pointer Strategy:**
```
nums = [1, 1, 2, 2, 3]
        ↑
        i=0 (write position)
        
Step 1: j=0, nums[0]=1 is first element → place at i=0
        nums = [1, 1, 2, 2, 3], i=1

Step 2: j=1, nums[1]=1 == nums[0]=1 → skip (duplicate)

Step 3: j=2, nums[2]=2 != nums[1]=1 → place at i=1
        nums = [1, 2, 2, 2, 3], i=2

Step 4: j=3, nums[3]=2 == nums[2]=2 → skip (duplicate)

Step 5: j=4, nums[4]=3 != nums[3]=2 → place at i=2
        nums = [1, 2, 3, 2, 3], i=3

Result: First i=3 elements are unique → return 3
```

### Step-by-Step Algorithm

---

#### **Approach 1: Two Pointers (Slow-Fast) - OPTIMAL**

**Core Idea**:
- Start with i=1 (first element is always unique)
- Use j to scan from 1 to n-1
- When nums[j] != nums[j-1], copy nums[j] to nums[i], increment i
- Return i as the count of unique elements

**Algorithm**
```
removeDuplicates(nums):
    if nums.length == 0:
        return 0
    
    i = 1  // write position for next unique element
    
    for j = 1 to nums.length - 1:
        if nums[j] != nums[j - 1]:
            nums[i] = nums[j]
            i++
    
    return i  // number of unique elements
```

**Code Implementation**
```java
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        
        int i = 1;  // Position for next unique element
        
        for (int j = 1; j < nums.length; j++) {
            // Found a unique element (different from previous)
            if (nums[j] != nums[j - 1]) {
                nums[i] = nums[j];
                i++;
            }
        }
        
        return i;  // Count of unique elements
    }
}
```

**Alternative Implementation (Compare with nums[i-1])**
```java
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        
        int i = 1;  // Position for next unique element
        
        for (int j = 1; j < nums.length; j++) {
            // If current element differs from last unique element
            if (nums[j] != nums[i - 1]) {
                nums[i] = nums[j];
                i++;
            }
        }
        
        return i;
    }
}
```

**Example Walkthrough**

Input: `nums = [1,1,2,2,3]`

| Step | j | nums[j] | nums[j-1] | Different? | Action | i | nums State |
|------|---|---------|-----------|------------|--------|---|------------|
| Init | — | — | — | — | i=1 | 1 | [1,1,2,2,3] |
| 1 | 1 | 1 | 1 | No | Skip | 1 | [1,1,2,2,3] |
| 2 | 2 | 2 | 1 | **Yes** | nums[1]=2, i++ | 2 | [1,**2**,2,2,3] |
| 3 | 3 | 2 | 2 | No | Skip | 2 | [1,2,2,2,3] |
| 4 | 4 | 3 | 2 | **Yes** | nums[2]=3, i++ | 3 | [1,2,**3**,2,3] |
| End | — | — | — | — | return i=3 | — | First 3 elements: **[1,2,3]** ✓ |

**Complexity Analysis**
- **Time Complexity**: O(n) — Single pass through array
- **Space Complexity**: O(1) — Only two pointer variables, in-place modification

---

#### **Approach 2: Using HashSet (NOT OPTIMAL)**

**Core Idea**: Use a HashSet to track seen elements, rebuild array with unique values.

**Code Implementation**
```java
class Solution {
    public int removeDuplicates(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        int i = 0;
        
        for (int num : nums) {
            if (!seen.contains(num)) {
                seen.add(num);
                nums[i++] = num;
            }
        }
        
        return i;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n)
- **Space Complexity**: O(n) — HashSet violates in-place O(1) requirement!
- **Why Not Optimal**: Doesn't leverage the sorted property, uses extra space

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | HashSet | **Two Pointers** |
|-------------|---------|-----------------|
| Time complexity | O(n) ✓ | **O(n) ✅** |
| Space complexity | O(n) ❌ | **O(1) ✅** |
| In-place | Partial | **✅** |
| Leverages sorted input | ❌ | **✅** |
| Optimal | ❌ | **✅** |

**Winner**: **Two Pointers (Slow-Fast)** — only approach meeting all requirements!

### Why Two Pointers Work Perfectly?
```
The genius insight:
  Sorted array → duplicates are ADJACENT
  No need to check entire array for duplicates
  Just compare with previous element!
  
Compare with unsorted:
  [3,1,2,1,3] → duplicates scattered, need HashSet
  [1,1,2,3,3] → duplicates adjacent, compare neighbors!

Two pointers optimize for sorted arrays:
  - Fast pointer finds unique elements
  - Slow pointer overwrites duplicates
  - Single pass, no extra space
```

### Why Compare with Previous Element?
```
In sorted array: [1,1,2,2,3]
  If nums[j] != nums[j-1], it's a NEW unique element
  Why? Because all duplicates are consecutive!
  
Example:
  j=2: nums[2]=2, nums[1]=1 → 2!=1 → unique!
  j=3: nums[3]=2, nums[2]=2 → 2==2 → duplicate, skip
  j=4: nums[4]=3, nums[3]=2 → 3!=2 → unique!
```

---

## Critical Edge Cases & Gotchas

### 1. **Array with All Duplicates**
```java
Input: nums = [5,5,5,5,5]
Output: k = 1, nums = [5,_,_,_,_]
Explanation: Only one unique element.
```

### 2. **Array with No Duplicates**
```java
Input: nums = [1,2,3,4,5]
Output: k = 5, nums = [1,2,3,4,5]
Explanation: All elements unique, array unchanged.
```

### 3. **Array with Single Element**
```java
Input: nums = [1]
Output: k = 1, nums = [1]
Explanation: Single element is always unique.
```

### 4. **Two Elements (Same)**
```java
Input: nums = [1,1]
Output: k = 1, nums = [1,_]
Explanation: Both are duplicates of 1.
```

### 5. **Two Elements (Different)**
```java
Input: nums = [1,2]
Output: k = 2, nums = [1,2]
Explanation: Both are unique.
```

### 6. **Duplicates at Start**
```java
Input: nums = [1,1,1,2,3]
Output: k = 3, nums = [1,2,3,_,_]
Explanation: Multiple duplicates at beginning.
```

### 7. **Duplicates at End**
```java
Input: nums = [1,2,3,3,3]
Output: k = 3, nums = [1,2,3,_,_]
Explanation: Multiple duplicates at end.
```

### 8. **Negative Numbers**
```java
Input: nums = [-3,-3,-1,0,0,1]
Output: k = 4, nums = [-3,-1,0,1,_,_]
Explanation: Works with negative numbers too.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Starting i from 0 Instead of 1**
```java
// WRONG - i starts from 0
int i = 0;
for (int j = 0; j < nums.length; j++) {
    if (j == 0 || nums[j] != nums[j - 1]) {
        nums[i++] = nums[j];
    }
}
```

**Why wrong**: Adds unnecessary complexity with the j == 0 check. The first element is always unique!

**Better approach**: Start i=1, first element already in place
```java
// CORRECT
int i = 1;
for (int j = 1; j < nums.length; j++) {
    if (nums[j] != nums[j - 1]) {
        nums[i++] = nums[j];
    }
}
```

### ❌ **MISTAKE 2: Forgetting to Return i (Not i-1)**
```java
// WRONG - returns i-1
int i = 1;
for (int j = 1; j < nums.length; j++) {
    if (nums[j] != nums[j - 1]) {
        nums[i++] = nums[j];
    }
}
return i - 1;  // WRONG!
```

**Why wrong**: i already points to the position AFTER the last unique element, which equals the count!

**Dry run failure for nums=[1,2,3]:**
```
j=1: 2!=1 → nums[1]=2, i=2
j=2: 3!=2 → nums[2]=3, i=3
Return i-1 = 2 (WRONG! Should be 3)
```

**Fix**: Return i directly
```java
return i;  // i is the count of unique elements
```

### ❌ **MISTAKE 3: Using i and j Wrong Way**
```java
// WRONG - i scans, j writes (reversed!)
int j = 1;
for (int i = 1; i < nums.length; i++) {
    if (nums[i] != nums[i - 1]) {
        nums[j++] = nums[i];
    }
}
return j;
```

**Why wrong**: While this actually works, it's confusing! Convention is i=slow (write), j=fast (read).

**Fix**: Use standard convention
```java
// CORRECT - i writes, j reads (clear intent)
int i = 1;
for (int j = 1; j < nums.length; j++) {
    if (nums[j] != nums[j - 1]) {
        nums[i++] = nums[j];
    }
}
return i;
```

### ❌ **MISTAKE 4: Not Handling Empty Array**
```java
// WRONG - no empty array check
int i = 1;
for (int j = 1; j < nums.length; j++) {  // What if nums.length = 0?
    if (nums[j] != nums[j - 1]) {
        nums[i++] = nums[j];
    }
}
return i;  // Returns 1 for empty array! WRONG!
```

**Why wrong**: If nums is empty (though constraints say length >= 1), this returns 1 instead of 0!

**Fix**: Add early return (defensive programming)
```java
// CORRECT
if (nums.length == 0) return 0;
int i = 1;
// ... rest of code
```

### ❌ **MISTAKE 5: Using nums[i] Instead of nums[i-1] for Comparison**
```java
// WRONG - compares with write position
int i = 1;
for (int j = 1; j < nums.length; j++) {
    if (nums[j] != nums[i]) {  // WRONG! Should be nums[i-1]
        nums[i++] = nums[j];
    }
}
```

**Why wrong**: nums[i] is the write position, not the last unique element!

**Dry run failure for nums=[1,1,2]:**
```
j=1: nums[1]=1, nums[i]=nums[1]=1 → 1==1 → skip (OK by accident)
j=2: nums[2]=2, nums[i]=nums[1]=1 → 2!=1 → write (OK)
But for [1,2,2]:
j=1: nums[1]=2, nums[i]=nums[1]=2 → 2==2 → skip (WRONG! Should copy)
```

**Fix**: Compare with nums[i-1] (last unique) OR nums[j-1] (previous element)
```java
// CORRECT - either works for sorted array
if (nums[j] != nums[i - 1]) { ... }
// OR
if (nums[j] != nums[j - 1]) { ... }
```

### ❌ **MISTAKE 6: Incrementing i Before Assignment**
```java
// WRONG - increments before writing
int i = 1;
for (int j = 1; j < nums.length; j++) {
    if (nums[j] != nums[j - 1]) {
        i++;
        nums[i] = nums[j];  // Writes to NEXT position, skips one!
    }
}
return i;
```

**Why wrong**: Increments i before writing, leaving gaps!

**Dry run failure for nums=[1,2,3]:**
```
j=1: 2!=1 → i=2, nums[2]=2 (skipped nums[1]!)
j=2: 3!=2 → i=3, nums[3]=3 (skipped nums[2]!)
Result: [1,_,2,3] (WRONG! Should be [1,2,3])
```

**Fix**: Write first, then increment (or use post-increment)
```java
// CORRECT
nums[i] = nums[j];
i++;
// OR
nums[i++] = nums[j];
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Single pass through array | O(n) | Visit each element once |
| Comparison per element | O(1) | Simple equality check |
| Assignment per unique element | O(1) | Constant time operation |
| **Total** | **O(n)** | Linear time, optimal |

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| Pointer variables (i, j) | O(1) | Two integers |
| In-place modification | O(1) | No extra array |
| **Total** | **O(1)** | Constant space |

**Why O(n) Time is Optimal:**
- Must examine each element at least once to check if it's duplicate
- Cannot skip any elements
- Single pass is the minimum possible

---

## Visualization

### Example Walkthrough

**Input:** `nums = [0,0,1,1,1,2,2,3,3,4]`

```
Initial State:
nums: [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
       ↑
       i=1 (write position)

Step 1: j=1, nums[1]=0, nums[0]=0 → same → skip
nums: [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
       ↑
       i=1

Step 2: j=2, nums[2]=1, nums[1]=0 → different!
        Write 1 at i=1
nums: [0, 1, 1, 1, 1, 2, 2, 3, 3, 4]
          ↑
          i=2

Step 3: j=3, nums[3]=1, nums[2]=1 → same → skip
Step 4: j=4, nums[4]=1, nums[3]=1 → same → skip

Step 5: j=5, nums[5]=2, nums[4]=1 → different!
        Write 2 at i=2
nums: [0, 1, 2, 1, 1, 2, 2, 3, 3, 4]
             ↑
             i=3

Step 6: j=6, nums[6]=2, nums[5]=2 → same → skip

Step 7: j=7, nums[7]=3, nums[6]=2 → different!
        Write 3 at i=3
nums: [0, 1, 2, 3, 1, 2, 2, 3, 3, 4]
                ↑
                i=4

Step 8: j=8, nums[8]=3, nums[7]=3 → same → skip

Step 9: j=9, nums[9]=4, nums[8]=3 → different!
        Write 4 at i=4
nums: [0, 1, 2, 3, 4, 2, 2, 3, 3, 4]
                   ↑
                   i=5

Result: First 5 elements are unique: [0,1,2,3,4] ✓
Return k = 5
```

### How Slow-Fast Pointers Work

```
Fast Pointer (j): Scans through array
  → Finds unique elements by comparing with previous

Slow Pointer (i): Write position
  → Overwrites duplicates with unique elements found by j

Process:
  [1, 1, 2, 2, 3]
   ↑  ↑
   i  j  → j finds 1 is duplicate (1==1), skip
   
  [1, 1, 2, 2, 3]
   ↑     ↑
   i     j  → j finds 2 is unique (2!=1), write at i
   
  [1, 2, 2, 2, 3]
      ↑     ↑
      i     j  → j finds 2 is duplicate (2==2), skip
      
  [1, 2, 2, 2, 3]
      ↑        ↑
      i        j  → j finds 3 is unique (3!=2), write at i
      
  [1, 2, 3, 2, 3]
         ↑
         i=3  → return 3
```

---

## Comparison of Approaches

| Approach | Time | Space | In-Place | Leverages Sorted | Optimal |
|----------|------|-------|----------|------------------|---------|
| HashSet | O(n) | O(n) | Partial | ❌ | ❌ |
| **Two Pointers** | **O(n)** | **O(1)** | **✅** | **✅** | **✅** |

**Recommendation**: Always use **Two Pointers (Slow-Fast)** — it's the only optimal solution!

---

## Key Takeaways

1. **Slow-fast pointer pattern** — i writes unique elements, j scans for them
2. **Sorted array property** — duplicates are adjacent, compare with previous element
3. **First element always unique** — start i from 1, not 0
4. **Return i, not i-1** — i is the count of unique elements
5. **O(n) time, O(1) space** — optimal complexity
6. **In-place modification** — overwrites duplicates with unique values
7. **Compare nums[j] with nums[j-1]** — detects when we've found a new unique element

---

## Interview Tips

**What to say in an interview:**

> "This is a classic two-pointer problem on a sorted array. Since the array is sorted, duplicates will be adjacent. I'll use a slow pointer i to track where to write the next unique element, starting at position 1 since the first element is always unique. A fast pointer j will scan through the array. Whenever I find nums[j] different from nums[j-1], I know I've found a unique element, so I'll write it at position i and increment i. This achieves O(n) time and O(1) space."

**Key points to mention:**
1. **Why two pointers** — slow writes, fast scans for unique elements
2. **Leveraging sorted property** — duplicates are adjacent, compare neighbors
3. **Start from i=1** — first element always unique, no need to check
4. **Return i** — represents count of unique elements (not i-1)
5. **Complexity** — O(n) time (single pass), O(1) space (in-place)

**If asked about alternatives:**
> "I could use a HashSet to track seen elements, but that would use O(n) extra space and wouldn't leverage the sorted property. The two-pointer approach is optimal because it exploits the fact that duplicates are adjacent in a sorted array."

**Common Follow-ups:**
- "What if array wasn't sorted?" → Would need HashSet or sort first
- "What if we allow k duplicates?" → Remove Duplicates II, use counter
- "Can you do it without comparing with previous?" → Yes, compare nums[j] with nums[i-1]

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Remove Duplicates from Sorted Array** | Easy | **Two Pointers (Slow-Fast)** | **This problem** ← |
| Remove Duplicates from Sorted Array II | Medium | Two Pointers + Counter | Allow at most 2 duplicates |
| Remove Element | Easy | Two Pointers (Slow-Fast) | Remove specific value, not duplicates |
| Move Zeroes | Easy | Two Pointers (Slow-Fast) | Move zeros to end, maintain order |
| Remove Duplicates from Sorted List | Easy | Single Pointer (Linked List) | Same logic for linked lists |

**Pattern Progression**:
1. **Remove Duplicates from Sorted Array** (this problem) — Remove all duplicates
2. **Remove Duplicates from Sorted Array II** — Allow up to k occurrences
3. **Remove Element** — Remove specific value
4. **Move Zeroes** — Partition array (non-zeros first)

---

## Final Pattern Label

✅ **Two Pointers – Slow/Fast (In-Place Unique Elements)**

**Remember:** Slow pointer (i) writes unique elements, fast pointer (j) scans for them. Sorted array means duplicates are adjacent - compare nums[j] with nums[j-1]. Return i as count. O(n) time, O(1) space!
