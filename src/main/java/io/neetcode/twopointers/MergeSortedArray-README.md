# Merge Sorted Array

## Problem Description

**Difficulty**: Easy

You are given two integer arrays `nums1` and `nums2`, both sorted in **non-decreasing order**, along with two integers `m` and `n`, where:
- `m` is the number of valid elements in `nums1`
- `n` is the number of elements in `nums2`

The array `nums1` has a total length of `(m+n)`, with the first `m` elements containing the values to be merged, and the last `n` elements set to 0 as placeholders.

Your task is to merge the two arrays such that the final merged array is also sorted in non-decreasing order and stored entirely within `nums1`.

**You must modify `nums1` in-place and do not return anything from the function.**

## Examples

### Example 1:
```
Input: nums1 = [10,20,20,40,0,0], m = 4, nums2 = [1,2], n = 2
Output: [1,2,10,20,20,40]
Explanation: 
- Valid elements in nums1: [10,20,20,40]
- Elements in nums2: [1,2]
- Merge from the end to avoid overwriting
- Final sorted array: [1,2,10,20,20,40]
```

### Example 2:
```
Input: nums1 = [0,0], m = 0, nums2 = [1,2], n = 2
Output: [1,2]
Explanation:
- nums1 has no valid elements (m = 0)
- Simply copy nums2 entirely into nums1
```

### Example 3:
```
Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
Output: [1,2,2,3,5,6]
Explanation:
- Valid elements: [1,2,3] and [2,5,6]
- Merge alternates and maintains sorted order
```

## Constraints
- 0 <= m, n <= 200
- 1 <= (m + n) <= 200
- nums1.length == (m + n)
- nums2.length == n
- -1,000,000,000 <= nums1[i], nums2[i] <= 1,000,000,000

---

## Pattern Recognition

**Primary Pattern**: **Two Pointers - Backward Traversal (In-Place Merge)**

**Why This Pattern?**
- Two sorted arrays need to be merged
- Must modify in-place (no extra array allowed)
- nums1 has extra space at the end (perfect for backward merge)
- Forward merge would require shifting elements (expensive O(n²))
- Backward merge fills the empty space without overwriting

**Key Insight**: Why Merge Backward?
```
Forward Merge Problem:
  nums1 = [1,3,5,0,0,0], nums2 = [2,4,6]
  If we merge forward starting at index 0:
    - Placing 1 at nums1[0] is fine
    - Placing 2 at nums1[1] overwrites the 3!
    - Need to shift all remaining elements right → O(n²)

Backward Merge Solution:
  Start from the END of nums1 (index m+n-1)
  The end positions are EMPTY (filled with 0s)
  Place largest elements at the end → no overwriting!
  Work backward → O(m+n) single pass
```

**Key Insight**: Three Pointers
- Pointer `i`: Last valid element in nums1 (index m-1)
- Pointer `j`: Last element in nums2 (index n-1)
- Pointer `k`: Last position in nums1 (index m+n-1)
- Compare nums1[i] vs nums2[j], place larger at nums1[k]
- Move pointers backward

**Related Patterns**:
1. **Merge Two Sorted Lists** — Same merge logic but with linked lists
2. **Squares of a Sorted Array** — Two pointers from both ends
3. **Sort Colors** — Three pointers in-place sorting
4. **Merge Intervals** — Merge based on sorted order

---

## Algorithm & Approach

### Core Insight

**Why Forward Merge Fails:**
```
Forward approach (starting from index 0):
  → Must shift elements right to make space
  → Each shift costs O(n)
  → Total: O(n²) time
  → Not optimal!

Backward approach (starting from index m+n-1):
  → Empty space at the end
  → No shifting needed
  → Single pass: O(m+n) time
  → Optimal! ✓
```

**The Three-Pointer Strategy:**
```
nums1 = [1, 3, 5, 0, 0, 0]  m=3
         ↑        ↑        ↑
         i=2      unused   k=5 (write position)

nums2 = [2, 4, 6]  n=3
                ↑
                j=2

Step 1: Compare nums1[i]=5 vs nums2[j]=6
        → 6 is larger, place at k
        → nums1[k--] = nums2[j--]
        
Step 2: Compare nums1[i]=5 vs nums2[j]=4
        → 5 is larger, place at k
        → nums1[k--] = nums1[i--]
        
Continue until all elements placed...
```

### Step-by-Step Algorithm

---

#### **Approach 1: Three Pointers Backward Merge (OPTIMAL)**

**Core Idea**:
- Use three pointers: i (nums1 last valid), j (nums2 last), k (nums1 write position)
- Compare nums1[i] vs nums2[j], place larger element at nums1[k]
- Move pointers backward
- After main loop, copy any remaining nums2 elements (nums1 elements already in place)

**Algorithm**
```
merge(nums1, m, nums2, n):
    i = m - 1       // last valid element in nums1
    j = n - 1       // last element in nums2
    k = m + n - 1   // last position in nums1
    
    // Merge from back to front
    while i >= 0 AND j >= 0:
        if nums1[i] > nums2[j]:
            nums1[k] = nums1[i]
            i--
        else:
            nums1[k] = nums2[j]
            j--
        k--
    
    // Copy remaining nums2 elements (if any)
    while j >= 0:
        nums1[k] = nums2[j]
        j--
        k--
    
    // No need to copy nums1 elements - already in correct position
```

**Code Implementation**
```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;       // Last valid element in nums1
        int j = n - 1;       // Last element in nums2
        int k = m + n - 1;   // Last position in nums1
        
        // Merge from back to front
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
        
        // Copy remaining elements from nums2 (if any)
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
        
        // No need to copy nums1 - already in correct position
    }
}
```

**Example Walkthrough**

Input: `nums1 = [1,2,3,0,0,0]`, `m = 3`, `nums2 = [2,5,6]`, `n = 3`

| Step | i | j | k | nums1[i] | nums2[j] | Compare | Action | nums1 State |
|------|---|---|---|----------|----------|---------|--------|-------------|
| Init | 2 | 2 | 5 | 3 | 6 | — | — | [1,2,3,0,0,0] |
| 1 | 2 | 2 | 5 | 3 | 6 | 3 < 6 | Place 6 at k=5 | [1,2,3,0,0,**6**] |
| 2 | 2 | 1 | 4 | 3 | 5 | 3 < 5 | Place 5 at k=4 | [1,2,3,0,**5**,6] |
| 3 | 2 | 0 | 3 | 3 | 2 | 3 > 2 | Place 3 at k=3 | [1,2,**3**,3,5,6] |
| 4 | 1 | 0 | 2 | 2 | 2 | 2 = 2 | Place 2 from nums2 | [1,**2**,2,3,5,6] |
| 5 | 1 | -1 | 1 | 2 | — | j < 0 | Exit main loop | [1,2,2,3,5,6] |
| End | — | — | — | — | — | i >= 0 | nums1 already placed | **[1,2,2,3,5,6]** ✓ |

**Complexity Analysis**
- **Time Complexity**: O(m + n) — Single pass through both arrays
- **Space Complexity**: O(1) — Only three pointer variables, in-place modification

---

#### **Approach 2: Copy and Sort (BRUTE FORCE - NOT OPTIMAL)**

**Core Idea**: Copy nums2 to the end of nums1, then sort the entire array.

**Code Implementation**
```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // Copy nums2 elements to end of nums1
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        
        // Sort entire array
        Arrays.sort(nums1);
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O((m + n) log(m + n)) — Sorting dominates
- **Space Complexity**: O(log(m + n)) — Sorting requires stack space
- **Why Not Optimal**: Doesn't leverage the fact that inputs are already sorted!

---

#### **Approach 3: Forward Merge with Extra Array (ALTERNATIVE)**

**Core Idea**: Create a temporary array, merge forward, then copy back.

**Code Implementation**
```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] temp = new int[m + n];
        int i = 0, j = 0, k = 0;
        
        // Merge into temp array
        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                temp[k++] = nums1[i++];
            } else {
                temp[k++] = nums2[j++];
            }
        }
        
        // Copy remaining
        while (i < m) temp[k++] = nums1[i++];
        while (j < n) temp[k++] = nums2[j++];
        
        // Copy back to nums1
        for (i = 0; i < m + n; i++) {
            nums1[i] = temp[i];
        }
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(m + n)
- **Space Complexity**: O(m + n) — Extra array violates in-place requirement!

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force (Sort) | Extra Array | **Backward Merge** |
|-------------|-------------------|-------------|-------------------|
| Time complexity | O((m+n)log(m+n)) ❌ | O(m+n) ✓ | **O(m+n) ✅** |
| Space complexity | O(log(m+n)) | O(m+n) ❌ | **O(1) ✅** |
| In-place | ✓ | ❌ | **✅** |
| Leverages sorted input | ❌ | ✓ | **✅** |
| Optimal | ❌ | ❌ | **✅** |

**Winner**: **Backward Merge with Three Pointers** — only approach meeting all requirements!

### Why Backward Merge is Brilliant?
```
The genius insight:
  nums1 has EXACTLY n empty slots at the end
  Merging backward fills these slots perfectly
  No risk of overwriting unprocessed elements
  Single pass, no extra space needed!

Compare with forward merge:
  Would need to shift elements → expensive
  Or use extra array → wastes space
  Backward merge avoids both problems!
```

### Why We Don't Need to Copy Remaining nums1 Elements?
```
When j < 0 (nums2 exhausted):
  All remaining nums1 elements are ALREADY in their correct positions!
  They're at the front of nums1, already sorted
  No copying needed!

When i < 0 (nums1 exhausted):
  nums2 still has elements → must copy them
  Hence the second while loop for nums2
```

---

## Critical Edge Cases & Gotchas

### 1. **nums1 Empty (m = 0)**
```java
Input: nums1 = [0,0,0], m = 0, nums2 = [1,2,3], n = 3
Output: [1,2,3]
Explanation: nums1 has no valid elements, just copy all of nums2.
```

### 2. **nums2 Empty (n = 0)**
```java
Input: nums1 = [1,2,3], m = 3, nums2 = [], n = 0
Output: [1,2,3]
Explanation: Nothing to merge, nums1 already complete.
```

### 3. **All nums2 Elements Smaller**
```java
Input: nums1 = [4,5,6,0,0,0], m = 3, nums2 = [1,2,3], n = 3
Output: [1,2,3,4,5,6]
Explanation: All nums2 elements go to the front.
```

### 4. **All nums2 Elements Larger**
```java
Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [4,5,6], n = 3
Output: [1,2,3,4,5,6]
Explanation: nums1 elements stay in front, nums2 appends at end.
```

### 5. **Interleaved Elements**
```java
Input: nums1 = [1,3,5,0,0,0], m = 3, nums2 = [2,4,6], n = 3
Output: [1,2,3,4,5,6]
Explanation: Perfect alternation.
```

### 6. **Duplicate Elements**
```java
Input: nums1 = [1,2,2,0,0], m = 3, nums2 = [2,2], n = 2
Output: [1,2,2,2,2]
Explanation: Duplicates are allowed, maintain order.
```

### 7. **Negative Numbers**
```java
Input: nums1 = [-3,-1,0,0], m = 2, nums2 = [-2,0], n = 2
Output: [-3,-2,-1,0]
Explanation: Works with negative numbers too.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Merging Forward (Overwrites Elements)**
```java
// WRONG - merges from front
int i = 0, j = 0, k = 0;
while (i < m && j < n) {
    if (nums1[i] <= nums2[j]) {
        nums1[k++] = nums1[i++];  // Overwrites unprocessed nums1 elements!
    } else {
        nums1[k++] = nums2[j++];
    }
}
```

**Why wrong**: Writing at k=0, k=1, etc. destroys original nums1 elements that haven't been processed yet!

**Dry run failure for nums1=[1,3,5,0,0], nums2=[2,4]:**
```
Step 1: Compare 1 vs 2 → place 1 at k=0 (OK, it was already there)
Step 2: Compare 3 vs 2 → place 2 at k=1 (OVERWRITES the 3!)
Now 3 is lost forever → WRONG result
```

**Fix**: Merge backward
```java
int i = m - 1, j = n - 1, k = m + n - 1;  // Start from END
```

### ❌ **MISTAKE 2: Forgetting to Copy Remaining nums2 Elements**
```java
// WRONG - missing second while loop
while (i >= 0 && j >= 0) {
    if (nums1[i] > nums2[j]) {
        nums1[k--] = nums1[i--];
    } else {
        nums1[k--] = nums2[j--];
    }
}
// Missing: while (j >= 0) { ... }
```

**Why wrong**: When nums1 is exhausted first, remaining nums2 elements are never copied!

**Dry run failure for nums1=[4,5,0,0], nums2=[1,2,3]:**
```
After main loop: nums2 still has [1,2] left
Without copying: nums1 = [4,5,0,0] (WRONG!)
Should be: [1,2,4,5]
```

**Fix**: Add while loop for remaining nums2
```java
while (j >= 0) {
    nums1[k--] = nums2[j--];
}
```

### ❌ **MISTAKE 3: Trying to Copy Remaining nums1 Elements**
```java
// WRONG - unnecessary third loop
while (j >= 0) {
    nums1[k--] = nums2[j--];
}
// This is WRONG:
while (i >= 0) {
    nums1[k--] = nums1[i--];  // Unnecessary!
}
```

**Why wrong**: If nums1 has remaining elements, they're ALREADY in their correct positions at the front!

**Example**: nums1=[1,2,3,0], nums2=[4]
```
After placing 4, nums1 = [1,2,3,4]
Elements 1,2,3 are already at indices 0,1,2 (correct!)
Copying them again would overwrite and create wrong result
```

**Fix**: Don't copy remaining nums1 elements - they're already placed!

### ❌ **MISTAKE 4: Off-by-One in Pointer Initialization**
```java
// WRONG - index out of bounds
int i = m;      // Should be m-1!
int j = n;      // Should be n-1!
int k = m + n;  // Should be m+n-1!
```

**Why wrong**: Array indices are 0-based. Last valid index is length-1, not length!

**Fix**:
```java
int i = m - 1;
int j = n - 1;
int k = m + n - 1;
```

### ❌ **MISTAKE 5: Wrong Comparison (Using < Instead of >)**
```java
// WRONG - wrong comparison for backward merge
while (i >= 0 && j >= 0) {
    if (nums1[i] < nums2[j]) {  // WRONG! Should be >
        nums1[k--] = nums1[i--];
    } else {
        nums1[k--] = nums2[j--];
    }
}
```

**Why wrong**: We're merging backward - we want to place the LARGER element at the end, not smaller!

**Dry run failure:**
```
nums1=[1,3,0], nums2=[2]
Compare 3 < 2? False → place 2 (WRONG! Should place 3)
Result: [1,2,3] but with wrong intermediate steps
```

**Fix**: Use `>` for backward merge
```java
if (nums1[i] > nums2[j]) { ... }
```

---

## Complexity Analysis

### Time Complexity: **O(m + n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Main merge loop | O(min(m, n)) | Process shorter array completely |
| Copy remaining nums2 | O(n - processed) | At most n elements |
| Total iterations | O(m + n) | Each element processed exactly once |
| **Total** | **O(m + n)** | Linear time, optimal for merging |

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| Pointer variables (i, j, k) | O(1) | Three integers |
| In-place modification | O(1) | No extra array |
| **Total** | **O(1)** | Constant space, truly in-place |

**Why O(m+n) Time is Optimal:**
- Must examine each element at least once to determine its final position
- Cannot skip any elements
- Linear time is the theoretical minimum for merging

---

## Visualization

### Example Walkthrough

**Input:** `nums1 = [1,3,5,0,0,0]`, `m = 3`, `nums2 = [2,4,6]`, `n = 3`

```
Initial State:
nums1: [1,  3,  5,  0,  0,  0]
        ↑           ↑           ↑
        i=2         k=5         (end)

nums2: [2,  4,  6]
                ↑
                j=2

Step 1: Compare nums1[2]=5 vs nums2[2]=6
        → 6 > 5, place 6 at k=5
nums1: [1,  3,  5,  0,  0,  6]
        ↑                   ↑
        i=2         j=1     k=4

Step 2: Compare nums1[2]=5 vs nums2[1]=4
        → 5 > 4, place 5 at k=4
nums1: [1,  3,  5,  0,  5,  6]
        ↑               ↑
        i=1     j=1     k=3

Step 3: Compare nums1[1]=3 vs nums2[1]=4
        → 4 > 3, place 4 at k=3
nums1: [1,  3,  5,  4,  5,  6]
        ↑           ↑
        i=1 j=0     k=2

Step 4: Compare nums1[1]=3 vs nums2[0]=2
        → 3 > 2, place 3 at k=2
nums1: [1,  3,  3,  4,  5,  6]
        ↑       ↑
        i=0 j=0 k=1

Step 5: Compare nums1[0]=1 vs nums2[0]=2
        → 2 > 1, place 2 at k=1
nums1: [1,  2,  3,  4,  5,  6]
            ↑
        i=0 j=-1 k=0

Step 6: j < 0, exit main loop
        nums1 elements already in place
        
Final: [1,  2,  3,  4,  5,  6] ✓
```

### Backward vs Forward Comparison

```
Why Backward Works:
  [x, x, x, _, _, _]
           ↑  ↑  ↑
        These positions are EMPTY
        Safe to write largest elements here
        Work backward → never overwrite

Why Forward Fails:
  [x, x, x, _, _, _]
   ↑
  Writing here overwrites original data!
  Would need shifting → O(n²)
```

---

## Comparison of Approaches

| Approach | Time | Space | In-Place | Leverages Sorted | Optimal |
|----------|------|-------|----------|------------------|---------|
| Brute Force (Sort) | O((m+n)log(m+n)) | O(log(m+n)) | ✓ | ❌ | ❌ |
| Extra Array | O(m+n) | O(m+n) | ❌ | ✓ | ❌ |
| **Backward Merge** | **O(m+n)** | **O(1)** | **✅** | **✅** | **✅** |

**Recommendation**: Always use **Backward Three-Pointer Merge** — it's the only optimal solution!

---

## Key Takeaways

1. **Merge backward when in-place is required** — avoids overwriting unprocessed elements
2. **Three pointers: i, j, k** — track positions in nums1 (valid), nums2, and write position
3. **Place larger element first** — because we're working backward from the end
4. **Copy remaining nums2 only** — nums1 elements already in correct position
5. **O(m+n) time, O(1) space** — optimal complexity for this problem
6. **Empty slots at end are key** — they provide the space needed for in-place merge
7. **Forward merge fails** — would require shifting or extra space

---

## Interview Tips

**What to say in an interview:**

> "This is a classic backward merge problem. The key insight is that nums1 has empty space at the end, which is perfect for placing elements. I'll use three pointers: i at the last valid element of nums1, j at the last element of nums2, and k at the last position of nums1. I'll compare elements and place the larger one at position k, working backward. This avoids overwriting unprocessed elements and achieves O(m+n) time with O(1) space."

**Key points to mention:**
1. **Why backward** — avoids overwriting, uses empty space efficiently
2. **Three pointers** — i for nums1, j for nums2, k for write position
3. **Place larger element** — because we're filling from the end
4. **Handle remaining nums2** — nums1 elements already in place
5. **Complexity** — O(m+n) time (optimal), O(1) space (truly in-place)

**If asked about alternatives:**
> "I could use an extra array and merge forward, but that violates the in-place requirement. Or I could copy nums2 and sort, but that's O((m+n)log(m+n)) and doesn't leverage the sorted property. Backward merge is the only approach that's both optimal and in-place."

**Common Follow-ups:**
- "What if arrays weren't sorted?" → Would need to sort first, O((m+n)log(m+n))
- "Can you merge forward?" → Would need extra space or shifting (expensive)
- "Why not just sort after copying?" → Doesn't use the fact that inputs are sorted, slower

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Merge Sorted Array** | Easy | **Three Pointers Backward** | **This problem** ← |
| Merge Two Sorted Lists | Easy | Two Pointers (linked list) | Merge linked lists, not arrays |
| Merge k Sorted Lists | Hard | Heap/Priority Queue | Multiple arrays, need min-heap |
| Squares of a Sorted Array | Easy | Two Pointers from ends | Square elements, merge from ends |
| Sort Colors | Medium | Three Pointers (Dutch Flag) | In-place partitioning, not merging |

**Pattern Progression**:
1. **Merge Sorted Array** (this problem) — Two arrays, backward merge
2. **Merge Two Sorted Lists** — Same logic for linked lists
3. **Merge k Sorted Lists** — Extend to k arrays with heap
4. **Squares of a Sorted Array** — Similar backward pointer technique

---

## Final Pattern Label

✅ **Two Pointers – Backward Traversal (In-Place Merge)**

**Remember:** Three pointers (i, j, k) starting from the end. Compare and place larger element backward. Copy remaining nums2 if any. O(m+n) time, O(1) space. The backward approach is the key insight that makes this problem elegant!
