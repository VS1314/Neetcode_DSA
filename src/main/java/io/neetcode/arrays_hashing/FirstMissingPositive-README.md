# First Missing Positive

## Problem Description

**Difficulty**: Hard

Given an unsorted integer array `nums`, return the smallest positive integer that is not present in the array.

You must implement an algorithm that runs in **O(n) time** and uses **O(1) auxiliary space**.

## Examples

### Example 1:
```
Input: nums = [1,2,0]
Output: 3
Explanation: The numbers in the range [1,2] are all in the array.
```

### Example 2:
```
Input: nums = [3,4,-1,1]
Output: 2
Explanation: 1 is in the array but 2 is missing.
```

### Example 3:
```
Input: nums = [7,8,9,11,12]
Output: 1
Explanation: The smallest positive integer 1 is missing.
```

## Constraints
- 1 <= nums.length <= 10^5
- -2^31 <= nums[i] <= 2^31 - 1

**Critical Constraints:**
- Must be O(n) time complexity
- Must be O(1) extra space

---

## Pattern Recognition

**Primary Pattern**: **Cyclic Sort / Index as Hash (In-Place Array Manipulation)**

**Why This Pattern?**
- Need O(n) time → cannot sort (O(n log n))
- Need O(1) space → cannot use HashSet/HashMap
- Must work with unsorted array in-place
- Range constraint: answer must be in [1, n+1]

**Key Insight**: Array length is n, so the smallest missing positive MUST be in range [1, n+1]. We can use the array indices themselves as a hash table by placing each number at index (value - 1).

**Related Patterns**:
1. **Missing Number** - Similar cyclic sort pattern
2. **Find All Duplicates** - Index marking technique
3. **Set Mismatch** - Cyclic placement

---

## Algorithm & Approach

### Core Insight

**Critical Observation 1:** If array has length n, the answer is in range [1, n+1]

**Why?**
- Best case: array contains [1, 2, 3, ..., n] → answer is n+1
- Otherwise: some number in [1, n] is missing → that's the answer
- Numbers ≤ 0 or > n are irrelevant

**Critical Observation 2:** We can use array indices as a hash table

**Ideal positioning:**
- Value 1 should be at index 0
- Value 2 should be at index 1
- Value x should be at index (x-1)

**Strategy:**
1. Place each valid positive number at its "correct" index
2. Scan array to find first index where nums[i] ≠ i+1
3. That index + 1 is the answer

### What Numbers Matter?

Only positive numbers in range [1, n] matter:
- **Numbers ≤ 0**: Ignore (we need positive)
- **Numbers > n**: Ignore (answer can't be larger than n+1)

### Step-by-Step Algorithm

#### **Approach: Cyclic Sort / Index as Hash (OPTIMAL)**

```
Phase 1: Place numbers in correct positions
1. For each index i:
   2. While nums[i] is a valid number AND not in correct position:
      - Swap nums[i] to its correct position
      - A number x belongs at index (x-1)
      
Phase 2: Find first missing positive
1. Scan array from left to right
2. First index i where nums[i] ≠ i+1:
   - Return i+1
3. If all positions correct:
   - Return n+1
```

### Understanding the While Loop Condition

```java
while (
    nums[i] > 0 &&              // Condition 1
    nums[i] <= n &&             // Condition 2
    nums[nums[i] - 1] != nums[i] // Condition 3
)
```

**Condition 1: `nums[i] > 0`**
- **Why?** We only care about positive integers
- Numbers ≤ 0 are useless, cannot be the answer
- Example: [-2, -1, 0] → answer is 1, negatives irrelevant

**Condition 2: `nums[i] <= n`**
- **Why?** Answer must be in [1, n+1], so numbers > n are useless
- Example: [1, 2, 3], n=3 → answer is 4, number 100 is irrelevant

**Condition 3: `nums[nums[i] - 1] != nums[i]`**
- **Why?** Prevents infinite loops and handles duplicates
- Only swap if number is NOT already in correct position
- Example: [1, 1] → if we keep swapping, infinite loop!

**Why all three together?**
> "Keep swapping nums[i] to its correct position IF it is a useful positive number AND not already correctly placed."

### Code Implementation

```java
class Solution {
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        
        // Phase 1: Place each number at its correct index
        for (int i = 0; i < n; i++) {
            while (
                nums[i] > 0 &&                    // Valid positive
                nums[i] <= n &&                   // Within range
                nums[nums[i] - 1] != nums[i]      // Not in correct position
            ) {
                int correctIndex = nums[i] - 1;
                
                // Swap nums[i] with nums[correctIndex]
                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;
            }
        }
        
        // Phase 2: Find first missing positive
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        
        // Phase 3: All numbers [1..n] are present
        return n + 1;
    }
}
```

### Example Walkthrough

**Input:** nums = [3, 4, -1, 1], n = 4

**Phase 1: Cyclic Sort**
| Step | i | nums[i] | Action | Array State |
|------|---|---------|--------|-------------|
| 1 | 0 | 3 | Swap to index 2 | [-1, 4, 3, 1] |
| 1.1 | 0 | -1 | Skip (≤ 0) | [-1, 4, 3, 1] |
| 2 | 1 | 4 | Swap to index 3 | [-1, 1, 3, 4] |
| 2.1 | 1 | 1 | Swap to index 0 | [1, -1, 3, 4] |
| 2.2 | 1 | -1 | Skip (≤ 0) | [1, -1, 3, 4] |
| 3 | 2 | 3 | Already at correct index | [1, -1, 3, 4] |
| 4 | 3 | 4 | Already at correct index | [1, -1, 3, 4] |

**Phase 2: Find Missing**

| Index | Expected | Actual | Match? |
|-------|----------|--------|--------|
| 0 | 1 | 1 | ✓ |
| 1 | 2 | -1 | ❌ **Missing!** |

**Output:** 2

### Detailed Dry Run: Example 3

**Input:** nums = [7, 8, 9, 11, 12, 1, 2, 4, 5, 6, 3, 1], n = 12

```
Initial: [7, 8, 9, 11, 12, 1, 2, 4, 5, 6, 3, 1]

i=0: nums[0]=7, swap to index 6
     [2, 8, 9, 11, 12, 1, 7, 4, 5, 6, 3, 1]
     
i=0: nums[0]=2, swap to index 1
     [8, 2, 9, 11, 12, 1, 7, 4, 5, 6, 3, 1]
     
i=0: nums[0]=8, swap to index 7
     [4, 2, 9, 11, 12, 1, 7, 8, 5, 6, 3, 1]
     
i=0: nums[0]=4, swap to index 3
     [11, 2, 9, 4, 12, 1, 7, 8, 5, 6, 3, 1]
     
i=0: nums[0]=11, swap to index 10
     [3, 2, 9, 4, 12, 1, 7, 8, 5, 6, 11, 1]
     
i=0: nums[0]=3, swap to index 2
     [9, 2, 3, 4, 12, 1, 7, 8, 5, 6, 11, 1]
     
i=0: nums[0]=9, swap to index 8
     [5, 2, 3, 4, 12, 1, 7, 8, 9, 6, 11, 1]
     
i=0: nums[0]=5, swap to index 4
     [12, 2, 3, 4, 5, 1, 7, 8, 9, 6, 11, 1]
     
i=0: nums[0]=12, swap to index 11
     [1, 2, 3, 4, 5, 1, 7, 8, 9, 6, 11, 12]
     
i=0: nums[0]=1, already correct

Continue for remaining indices...

Final: [1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 11, 12]
                                    ^
                                    Index 9: expected 10, found 6

Answer: 10
```

### Complexity Analysis
- **Time Complexity**: O(n)
  - Each number is swapped at most once to its correct position
  - Total swaps ≤ n
  - Two passes: O(n) + O(n) = O(n)
- **Space Complexity**: O(1)
  - Only using constant extra space (temp variable)
  - Modifying array in-place

---

## Why This Strategy?

### Problem Requirements Analysis
| Requirement | Brute Force | Sort | HashSet | Cyclic Sort |
|-------------|-------------|------|---------|-------------|
| Time complexity | O(n²) ❌ | O(n log n) ❌ | O(n) ✓ | **O(n)** ✅ |
| Space complexity | O(1) ✓ | O(1)-O(n) | O(n) ❌ | **O(1)** ✅ |
| Meets constraints | ❌ | ❌ | ❌ | **✅** |

**Winner**: Cyclic Sort - ONLY approach that meets BOTH O(n) time and O(1) space!

### Why Cyclic Sort Works

**Key Realization:**
- We have n positions (indices 0 to n-1)
- We care about n values (1 to n)
- Perfect 1-to-1 mapping: value x → index (x-1)

**Why In-Place Swapping is Safe:**
- Each swap moves at least one number to correct position
- No number is swapped more than once to its final position
- Total swaps bounded by n

---

## Critical Edge Cases & Gotchas

### 1. **All Negatives**
```java
Input: nums = [-1, -2, -3]
Output: 1
Explanation: No positive numbers, so answer is 1
```

### 2. **All Numbers Greater Than n**
```java
Input: nums = [100, 200, 300]
Output: 1
Explanation: All numbers > n are irrelevant
```

### 3. **Perfect Sequence**
```java
Input: nums = [1, 2, 3, 4, 5]
Output: 6
Explanation: All [1..n] present, answer is n+1
```

### 4. **Duplicates**
```java
Input: nums = [1, 1]
Output: 2
Explanation: Condition 3 prevents infinite swapping
```

### 5. **Single Element - Missing 1**
```java
Input: nums = [2]
Output: 1
```

### 6. **Single Element - Has 1**
```java
Input: nums = [1]
Output: 2
```

### 7. **Zero in Array**
```java
Input: nums = [1, 0, 2]
Output: 3
Explanation: 0 is ignored, [1,2] present
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Infinite Loop - Missing Condition 3**
```java
// WRONG - Infinite loop with duplicates!
while (nums[i] > 0 && nums[i] <= n) {
    int correctIndex = nums[i] - 1;
    swap(nums, i, correctIndex);
}
```

**Why wrong**: With duplicates like [1, 1], keeps swapping forever.

**Example:**
```
nums = [1, 1]
i = 1: swap nums[1] with nums[0]
       Result: [1, 1] - nothing changed!
       Loop repeats infinitely
```

**Fix**: Add `nums[nums[i] - 1] != nums[i]` condition

### ❌ **MISTAKE 2: Swapping Wrong Indices**
```java
// WRONG - Swapping i with i+1 instead of correct position!
int temp = nums[i];
nums[i] = nums[i + 1];
nums[i + 1] = temp;
```

**Why wrong**: Not placing number at its correct position.

**Fix**: Swap with `nums[nums[i] - 1]`

### ❌ **MISTAKE 3: Not Handling Numbers > n**
```java
// WRONG - Will cause ArrayIndexOutOfBoundsException!
while (nums[i] > 0 && nums[nums[i] - 1] != nums[i]) {
    // If nums[i] = 100 and n = 5, accessing nums[99] crashes!
}
```

**Why wrong**: Large numbers create invalid indices.

**Fix**: Add `nums[i] <= n` condition

### ❌ **MISTAKE 4: Using For Loop Instead of While**
```java
// WRONG - Only swaps once, doesn't keep swapping!
for (int i = 0; i < n; i++) {
    if (nums[i] > 0 && nums[i] <= n && nums[nums[i]-1] != nums[i]) {
        swap(nums, i, nums[i] - 1);
    }
}
```

**Why wrong**: After first swap, nums[i] changes but we move to next i without checking new value.

**Fix**: Use `while` loop to keep swapping until current position is correct

### ❌ **MISTAKE 5: Returning i Instead of i+1**
```java
// WRONG - Off by one error!
for (int i = 0; i < n; i++) {
    if (nums[i] != i + 1) {
        return i;  // Should be i+1!
    }
}
```

**Why wrong**: Index 0 represents number 1, index i represents number i+1.

**Fix**: Return `i + 1`

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Explanation |
|-----------|------|-------------|
| Phase 1: Cyclic sort | O(n) | Each element swapped at most once |
| Inner while loop | O(1) amortized | Total swaps across all iterations ≤ n |
| Phase 2: Scan | O(n) | Single pass |
| Total | O(2n) = O(n) | Linear time |

**Why while loop is still O(n) total:**
- Each number can be swapped to its correct position only ONCE
- After a number reaches its correct position, it's never moved again
- Total swaps across entire algorithm ≤ n
- Amortized time per element: O(1)

### Space Complexity: **O(1)**

| Component | Space |
|-----------|-------|
| temp variable | O(1) |
| Loop variables | O(1) |
| In-place modification | O(1) |
| Total | O(1) - Constant space |

---

## Visualization

### Example Walkthrough
```
nums = [3, 4, -1, 1]
Indices: 0  1   2  3

Goal: Place each number at index (value - 1)
- 1 should be at index 0
- 2 should be at index 1  (missing!)
- 3 should be at index 2
- 4 should be at index 3

Step-by-step swapping:

i=0: [3, 4, -1, 1]
     nums[0]=3 → belongs at index 2
     Swap: [**, 4, 3, 1]  (swap with index 2)
     nums[0]=-1 → skip

i=1: [-1, 4, 3, 1]
     nums[1]=4 → belongs at index 3
     Swap: [-1, **, 3, 4]  (swap with index 3)
     nums[1]=1 → belongs at index 0
     Swap: [1, **, 3, 4]  (swap with index 0)
     nums[1]=-1 → skip

i=2: [1, -1, 3, 4]
     nums[2]=3 → already at index 2 ✓

i=3: [1, -1, 3, 4]
     nums[3]=4 → already at index 3 ✓

Final: [1, -1, 3, 4]

Scan: Index 1 expects 2, found -1
Answer: 2
```

---

## Comparison of Approaches

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Brute Force | O(n²) | O(1) | Simple | Too slow |
| Sorting | O(n log n) | O(1)-O(n) | Straightforward | Violates time constraint |
| HashSet | O(n) | O(n) | Easy to implement | Violates space constraint |
| **Cyclic Sort** | **O(n)** | **O(1)** | **Meets all constraints** ✅ | **Requires insight** |

**Best Choice**: Cyclic Sort ✓ - ONLY solution that works!

---

## Key Takeaways

1. **Constraint-Driven Pattern**: O(n) time + O(1) space → in-place manipulation
2. **Index as Hash**: Use array indices as hash table for values [1, n]
3. **Range Insight**: Answer must be in [1, n+1]
4. **Cyclic Placement**: Value x belongs at index (x-1)
5. **Three Conditions**: All three `while` conditions are critical
6. **Amortized O(n)**: Each element swapped at most once
7. **Hard Problem Pattern**: Common in advanced interviews (FAANG)

---

## Interview Tips

**What to say in an interview:**

> "Since I need O(n) time and O(1) space, I cannot sort or use a HashSet. The key insight is that with an array of length n, the answer must be in [1, n+1]. I can use cyclic sort to place each number at its correct index - value x goes to index (x-1). Numbers outside [1, n] are ignored. After rearranging, I scan for the first index where nums[i] ≠ i+1, and that's my answer. This is O(n) because each element is swapped at most once."

**Key points to mention:**
1. **Why other approaches fail**: Sort is O(n log n), HashSet is O(n) space
2. **Pattern**: Cyclic sort / index as hash
3. **Range insight**: Answer in [1, n+1]
4. **Three conditions**: Positive, within range, not already placed
5. **Complexity**: O(n) time because total swaps ≤ n

**If asked about the while loop:**
> "The while loop has three conditions - the number must be positive, within range [1, n], and not already in its correct position. The third condition is crucial to prevent infinite loops with duplicates. Each number is swapped at most once, so total swaps are bounded by n, giving O(n) time."

**If asked about edge cases:**
> "I handle negatives and numbers > n by the first two conditions. For duplicates, the third condition ensures we don't infinitely swap. If all positions are correct, the answer is n+1."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **First Missing Positive** | Hard | **Cyclic Sort** | **Find missing in [1,n+1]** ← This problem |
| Missing Number | Easy | Cyclic Sort / XOR | Numbers in [0, n] |
| Find All Duplicates | Medium | Index marking | Find all duplicates in [1, n] |
| Find All Missing Numbers | Medium | Index marking | Find all missing in [1, n] |
| Set Mismatch | Easy | Index marking | Find duplicate and missing |

**Pattern Family**: Cyclic Sort / Index as Hash

---

## Final Pattern Label

✅ **Cyclic Sort – Index as Hash for Range [1, n]**

**Remember:** When you see O(n) time + O(1) space + unsorted array + range [1, n] → immediately think Cyclic Sort pattern! This is a HARD problem pattern common in advanced interviews.