# Remove Element

## Problem Description

**Difficulty**: Easy

Given an integer array `nums` and an integer `val`, remove all occurrences of `val` from `nums` **in-place**.

After removing, return the number of remaining elements `k`, such that the **first `k` elements** of `nums` contain only elements not equal to `val`.

**Note**:
- The order of the remaining elements does **not** matter
- Elements beyond the first `k` positions are ignored

## Examples

### Example 1:
```
Input:  nums = [1,1,2,3,4], val = 1
Output: k = 3, nums = [2,3,4,_,_]
Explanation: 3 elements are not equal to 1.
             First 3 positions of nums: [2,3,4]
```

### Example 2:
```
Input:  nums = [0,1,2,2,3,0,4,2], val = 2
Output: k = 5, nums = [0,1,3,0,4,_,_,_]
Explanation: 5 elements are not equal to 2.
             First 5 positions of nums: [0,1,3,0,4]
```

## Constraints
- 0 <= nums.length <= 100
- 0 <= nums[i] <= 50
- 0 <= val <= 100

---

## Pattern Recognition

**Primary Pattern**: **Two Pointers — Write Pointer (In-Place Overwrite)**

**Why This Pattern?**
- We must modify the array **in-place** without extra space
- A **write pointer** `k` tracks the next position to place a valid element (one not equal to `val`)
- A **read pointer** `i` scans every element — when it finds a valid element, it writes it at position `k` and advances `k`
- At the end, `k` equals the count of valid elements and the first `k` positions hold the answer

**Key Insight**:
```
Read pointer i  → scans all elements left to right
Write pointer k → only advances when nums[i] != val

When nums[i] != val:
    nums[k] = nums[i]   ← overwrite position k with valid element
    k++                 ← advance write pointer

When nums[i] == val:
    skip (i advances, k stays)
```

**Pattern Elimination:**

| Pattern | Needed? | Why |
|---------|---------|-----|
| Sorting | ❌ | Order doesn't need to be maintained, but sorting isn't required |
| HashMap | ❌ | No frequency counting needed |
| Sliding Window | ❌ | No subarray range |
| Binary Search | ❌ | Array not sorted |
| **Two Pointers (Write)** | ✅ | In-place overwrite with read/write pointer |

**Related Patterns**:
1. **Move Zeroes** — Same write-pointer pattern (move non-zeros to front)
2. **Remove Duplicates from Sorted Array** — Same in-place overwrite pattern
3. **Sort Colors** — Two/three pointer in-place partitioning

---

## Algorithm & Approach

### Core Insight

**Why In-Place Overwrite Works:**

We don't need to physically "delete" elements — we just need the first `k` positions to hold valid elements. By overwriting with a write pointer, we effectively compact all valid elements to the front without any extra array.

```
Write pointer k: next slot to fill with a valid element
Read pointer  i: current element being examined

Rule:
  if nums[i] != val → copy nums[i] to nums[k], increment k
  if nums[i] == val → skip (do nothing, k stays)
```

**Decision Flow:**
```
removeElement(nums, val):
    k = 0  ← write pointer

    for i from 0 to nums.length - 1:
        ├─ If nums[i] != val:
        │   ├─ nums[k] = nums[i]   ← place valid element at write position
        │   └─ k++                 ← advance write pointer
        └─ (else: skip, read pointer i still advances)

    return k
```

### Visual Understanding

```
Example 1: nums = [1,1,2,3,4], val = 1

Initial: k=0
         [1, 1, 2, 3, 4]
          ↑
          i=0, k=0

i=0: nums[0]=1 == val → skip
         [1, 1, 2, 3, 4]    k=0

i=1: nums[1]=1 == val → skip
         [1, 1, 2, 3, 4]    k=0

i=2: nums[2]=2 != val → nums[0]=2, k=1
         [2, 1, 2, 3, 4]    k=1
          ↑write

i=3: nums[3]=3 != val → nums[1]=3, k=2
         [2, 3, 2, 3, 4]    k=2
             ↑write

i=4: nums[4]=4 != val → nums[2]=4, k=3
         [2, 3, 4, 3, 4]    k=3
                ↑write

Result: k=3, first 3 elements = [2,3,4] ✓
```

```
Example 2: nums = [0,1,2,2,3,0,4,2], val = 2

i=0: nums[0]=0 != 2 → nums[0]=0, k=1
i=1: nums[1]=1 != 2 → nums[1]=1, k=2
i=2: nums[2]=2 == 2 → skip,      k=2
i=3: nums[3]=2 == 2 → skip,      k=2
i=4: nums[4]=3 != 2 → nums[2]=3, k=3
i=5: nums[5]=0 != 2 → nums[3]=0, k=4
i=6: nums[6]=4 != 2 → nums[4]=4, k=5
i=7: nums[7]=2 == 2 → skip,      k=5

Result: k=5, nums = [0,1,3,0,4,_,_,_] ✓
```

---

### Step-by-Step Algorithm

#### **Approach 1: Write Pointer — In-Place Overwrite (OPTIMAL)**

**Core Idea**:
- `k` is both the write pointer and the final count
- Copy every non-`val` element to position `k`, increment `k`
- At the end, first `k` elements are the answer

**Code Implementation**
```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int k = 0;  // write pointer — next position for a valid element

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[k] = nums[i];  // overwrite position k with valid element
                k++;                // advance write pointer
            }
            // if nums[i] == val → skip, k stays, i advances
        }

        return k;  // k = count of elements not equal to val
    }
}
```

**Step-by-Step Trace:**

Input: nums = [0,1,2,2,3,0,4,2], val = 2

| i | nums[i] | != val? | Action | k | nums (first k) |
|---|---------|---------|--------|---|----------------|
| 0 | 0 | ✅ | nums[0]=0, k++ | 1 | [0] |
| 1 | 1 | ✅ | nums[1]=1, k++ | 2 | [0,1] |
| 2 | 2 | ❌ | skip | 2 | [0,1] |
| 3 | 2 | ❌ | skip | 2 | [0,1] |
| 4 | 3 | ✅ | nums[2]=3, k++ | 3 | [0,1,3] |
| 5 | 0 | ✅ | nums[3]=0, k++ | 4 | [0,1,3,0] |
| 6 | 4 | ✅ | nums[4]=4, k++ | 5 | [0,1,3,0,4] |
| 7 | 2 | ❌ | skip | 5 | [0,1,3,0,4] |

**Return k = 5** ✓

**Complexity Analysis**
- **Time Complexity**: O(n)
  - Single pass through the entire array
- **Space Complexity**: O(1)
  - Only one extra variable `k` — fully in-place

---

#### **Approach 2: Two Pointers from Both Ends (SWAP-based)**

**Core Idea**:
- Use a left pointer `l` (starts at 0) and right pointer `r` (starts at end)
- When `nums[l] == val`, swap it with `nums[r]` and shrink `r`
- When `nums[l] != val`, advance `l`
- Stop when `l > r`

**When to prefer this?**
- Useful when the number of elements to remove is small
- Avoids unnecessary writes (elements equal to `val` at the front are swapped, not written over repeatedly)

**Code Implementation**
```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int l = 0, r = nums.length - 1;

        while (l <= r) {
            if (nums[l] == val) {
                nums[l] = nums[r];  // overwrite val with rightmost element
                r--;                // shrink right boundary
            } else {
                l++;                // valid element, advance left
            }
        }

        return l;  // l = count of valid elements
    }
}
```

**Step-by-Step Trace:**

Input: nums = [1,1,2,3,4], val = 1

```
l=0, r=4: nums[0]=1 == 1 → nums[0]=nums[4]=4, r=3
          [4,1,2,3,4]  l=0, r=3

l=0, r=3: nums[0]=4 != 1 → l=1
          [4,1,2,3,4]  l=1, r=3

l=1, r=3: nums[1]=1 == 1 → nums[1]=nums[3]=3, r=2
          [4,3,2,3,4]  l=1, r=2

l=1, r=2: nums[1]=3 != 1 → l=2
          [4,3,2,3,4]  l=2, r=2

l=2, r=2: nums[2]=2 != 1 → l=3
          [4,3,2,3,4]  l=3, r=2

l=3 > r=2 → stop

Return l = 3, first 3 elements = [4,3,2] ✓ (order doesn't matter)
```

**Complexity Analysis**
- **Time Complexity**: O(n) — each element is processed at most once
- **Space Complexity**: O(1) — in-place, no extra space

---

## Comparison of Approaches

| Aspect | Write Pointer | Two-End Swap |
|--------|--------------|--------------|
| **Time Complexity** | O(n) | O(n) |
| **Space Complexity** | O(1) | O(1) |
| **Order Preserved** | ✅ Relative order kept | ❌ Order may change |
| **Write Operations** | Copies all valid elements | Fewer writes (skips valid at back) |
| **Code Simplicity** | ✅ Cleaner | Slightly more complex |
| **Preferred?** | ✅ General use | When removals are rare |

**Recommendation**: Use the **Write Pointer** approach — it's cleaner, preserves relative order, and is the standard expected solution. Mention the Two-End Swap as an optimization when the number of elements to remove is very small.

---

## Key Takeaways

1. **Write Pointer = In-Place Compaction**
   - `k` serves dual purpose: it's both the write position and the final count
   - At the end of the loop, `k` automatically equals the number of valid elements

2. **No Need to Clear Remaining Positions**
   - Elements at positions `k` to `n-1` after the loop are irrelevant — the problem only requires the first `k` to be correct

3. **i != k Only When Elements Were Skipped**
   - If no elements equal `val` exist, `k == i` at every step — we're just copying in-place
   - The "gap" between `k` and `i` grows by 1 for each occurrence of `val`

4. **Same Pattern Appears Frequently**
   - This write-pointer technique is used in: Remove Duplicates, Move Zeroes, Filter arrays
   - Recognizing "in-place compaction" → immediately reach for write pointer

5. **Order Doesn't Matter Here**
   - The problem explicitly states order is irrelevant → opens the door for the two-end swap optimization

---

## Common Pitfalls

❌ **Mistake 1**: Using a separate result array (violates in-place constraint)
```java
// WRONG: uses extra O(n) space
int[] result = new int[nums.length];
int k = 0;
for (int n : nums) {
    if (n != val) result[k++] = n;
}
```
✅ **Correct**: Overwrite in the same array
```java
nums[k] = nums[i];  // write directly into nums
k++;
```

❌ **Mistake 2**: Returning `nums.length - count(val)` without modifying the array
```java
// WRONG: returns correct count but array is unchanged — judge checks array contents!
int count = 0;
for (int n : nums) if (n == val) count++;
return nums.length - count;
```
✅ **Correct**: Modify the array AND return the count

❌ **Mistake 3**: Off-by-one in two-end swap — using `l < r` instead of `l <= r`
```java
// WRONG: misses single-element subarrays
while (l < r) { ... }  // fails when l == r and nums[l] == val
```
✅ **Correct**: Use `l <= r`
```java
while (l <= r) { ... }
```

❌ **Mistake 4**: Advancing `l` after a swap in the two-end approach
```java
// WRONG: the swapped-in element at nums[l] might also equal val — must recheck!
if (nums[l] == val) {
    nums[l] = nums[r--];
    l++;  // ← should NOT advance l here
}
```
✅ **Correct**: Only advance `l` when it holds a valid element
```java
if (nums[l] == val) { nums[l] = nums[r--]; }  // recheck nums[l] next iteration
else                { l++; }
```

---

## Related Problems

1. **Move Zeroes** (Easy) — Same write-pointer pattern; move non-zeros to front, zeros to back
2. **Remove Duplicates from Sorted Array** (Easy) — Write pointer, keep only unique elements
3. **Remove Duplicates from Sorted Array II** (Medium) — Allow at most 2 duplicates
4. **Sort Colors** (Medium) — Three-pointer in-place partitioning
5. **Partition Array** (similar) — Two-pointer in-place split based on a condition

---

## Edge Cases to Consider

1. **Empty Array**
   ```
   nums = [], val = 3
   Loop doesn't execute → return k=0 ✓
   ```

2. **No Elements Equal val**
   ```
   nums = [1,2,3], val = 5
   Every element is valid → k increments every step → return 3
   Array unchanged: [1,2,3] ✓
   ```

3. **All Elements Equal val**
   ```
   nums = [2,2,2], val = 2
   Every element is skipped → k never increments → return 0 ✓
   ```

4. **Single Element — Equals val**
   ```
   nums = [5], val = 5
   nums[0]==val → skip → return k=0 ✓
   ```

5. **Single Element — Not val**
   ```
   nums = [5], val = 3
   nums[0]!=val → nums[0]=5, k=1 → return k=1 ✓
   ```

6. **val Appears at Start and End**
   ```
   nums = [2,1,2], val = 2
   i=0: skip (k=0)
   i=1: nums[0]=1, k=1
   i=2: skip (k=1)
   Return k=1, nums[0]=1 ✓
   ```

---

## Summary

**Problem**: Remove all occurrences of `val` from `nums` in-place and return the count of remaining elements `k`.

**Solution**:
- Use a **write pointer** `k` initialized to 0
- Scan every element with read pointer `i`
- If `nums[i] != val` → copy to `nums[k]` and increment `k`
- Return `k`

**Time**: O(n) | **Space**: O(1)

**Pattern**: Two Pointers — Write Pointer (In-Place Overwrite). The write pointer compacts all valid elements to the front of the array in a single pass. `k` serves as both the write index and the final answer.
