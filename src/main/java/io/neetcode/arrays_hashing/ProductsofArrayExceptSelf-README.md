# Products of Array Except Self

## Problem Description

**Difficulty**: Medium

Given an integer array `nums`, return an array `output` where `output[i]` is the product of all elements of `nums` except `nums[i]`.

Each product is guaranteed to fit in a 32-bit integer.

**Follow-up**: Could you solve it in O(n) time **without using the division operation**?

## Examples

### Example 1:
```
Input: nums = [1,2,4,6]

Output: [48,24,12,8]
Explanation:
  output[0] = 2×4×6 = 48
  output[1] = 1×4×6 = 24
  output[2] = 1×2×6 = 12
  output[3] = 1×2×4 = 8
```

### Example 2:
```
Input: nums = [-1,0,1,2,3]

Output: [0,-6,0,0,0]
Explanation: Zero in the array makes most products 0, except for index 1
             where the product of everything except 0 is -1×1×2×3 = -6.
```

## Constraints
- 2 <= nums.length <= 1000
- -20 <= nums[i] <= 20

---

## Pattern Recognition

**Primary Pattern**: **Prefix × Suffix Product**

**Why This Pattern?**
- We need the product of everything *except* the current element
- Division would be simple but is disallowed (and breaks for zeros)
- For every index `i`:
  - Everything to the **left** = prefix product
  - Everything to the **right** = suffix product
  - `output[i] = prefix[i] × suffix[i]`
- Both prefix and suffix arrays can be built in O(n), combined in O(n) → total O(n)

**Key Insight**: Precompute products from the left and right separately. At each index, the answer is simply the product of those two precomputed values — no repeated work, no division.

**Related Patterns**:
1. **Prefix Sum** — Same left-to-right precompute strategy but for sums
2. **Range Sum Query** — Prefix arrays for answering range queries fast
3. **Trapping Rain Water** — Prefix max from left, suffix max from right
4. **Running Sum** — Simpler prefix pattern building block

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**

```
Brute force:
  For every index i, loop through all other indices → O(n²)
  nums.length = 1000 → 1,000,000 operations ❌
```

**The Prefix × Suffix Idea:**

```
nums    =  [ 1,  2,  4,  6 ]

For output[2] = everything except nums[2]=4:
  Left side:  1 × 2 = 2    ← prefix product before index 2
  Right side: 6      = 6   ← suffix product after index 2
  Answer:     2 × 6  = 12  ✓
```

### Visual Understanding

```
nums    = [ 1,  2,  4,  6 ]

prefix[i] = product of all elements BEFORE index i
            (prefix[0] = 1, nothing to the left)

  index:     0   1   2   3
  prefix:  [ 1,  1,  2,  8 ]
              ↑   ↑   ↑   ↑
              1  1×1 1×2 2×4

suffix[i] = product of all elements AFTER index i
            (suffix[n-1] = 1, nothing to the right)

  index:     0   1   2   3
  suffix:  [48, 24,  6,  1]
              ↑   ↑   ↑   ↑
           2×4×6 4×6  6  1

output[i] = prefix[i] × suffix[i]

  i=0: 1  × 48 = 48
  i=1: 1  × 24 = 24
  i=2: 2  ×  6 = 12
  i=3: 8  ×  1 =  8

Output: [48, 24, 12, 8] ✓
```

### Step-by-Step Algorithm

---

#### **Approach 1: Prefix + Suffix Arrays — O(n) time, O(n) space**

**Core Idea**:
- Build a `prefix` array left to right: `prefix[i]` = product of all elements before `i`
- Build a `suffix` array right to left: `suffix[i]` = product of all elements after `i`
- Multiply them: `output[i] = prefix[i] × suffix[i]`

**Algorithm**
```
1. prefix[0] = 1
   for i from 1 to n-1:
       prefix[i] = prefix[i - 1] × nums[i - 1]

2. suffix[n-1] = 1
   for i from n-2 down to 0:
       suffix[i] = suffix[i + 1] × nums[i + 1]

3. output[i] = prefix[i] × suffix[i]
```

**Code Implementation**
```java
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] prefix = new int[n];
    int[] suffix = new int[n];
    int[] output = new int[n];

    prefix[0] = 1;
    for (int i = 1; i < n; i++) {
        prefix[i] = prefix[i - 1] * nums[i - 1];
    }

    suffix[n - 1] = 1;
    for (int i = n - 2; i >= 0; i--) {
        suffix[i] = suffix[i + 1] * nums[i + 1];
    }

    for (int i = 0; i < n; i++) {
        output[i] = prefix[i] * suffix[i];
    }

    return output;
}
```

**Example Walkthrough**

Input: nums = [1, 2, 4, 6]

| i | prefix[i] | suffix[i] | output[i] |
|---|-----------|-----------|-----------|
| 0 | 1         | 48        | 48        |
| 1 | 1         | 24        | 24        |
| 2 | 2         | 6         | 12        |
| 3 | 8         | 1         | 8         |

**Return [48, 24, 12, 8]** ✓

**Complexity Analysis**
- **Time Complexity**: O(n) — three separate O(n) passes
- **Space Complexity**: O(n) — prefix and suffix arrays

---

#### **Approach 2: O(1) Extra Space — Suffix Variable (OPTIMAL Follow-up)**

**Core Idea**:
- Reuse the `output` array as the prefix array (built in first pass)
- In the second pass, maintain a running `suffix` variable and multiply into `output[i]` in place
- No separate prefix or suffix arrays needed

**Algorithm**
```
1. output[0] = 1
   for i from 1 to n-1:
       output[i] = output[i-1] × nums[i-1]    ← output now holds prefix products

2. suffix = 1
   for i from n-1 down to 0:
       output[i] = output[i] × suffix          ← multiply in suffix
       suffix    = suffix × nums[i]            ← update running suffix
```

**Code Implementation**
```java
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];

    // Pass 1: fill result with prefix products
    result[0] = 1;
    for (int i = 1; i < n; i++) {
        result[i] = result[i - 1] * nums[i - 1];
    }

    // Pass 2: multiply suffix into result in-place
    int suffix = 1;
    for (int i = n - 1; i >= 0; i--) {
        result[i] = result[i] * suffix;
        suffix   *= nums[i];
    }

    return result;
}
```

**Example Walkthrough**

Input: nums = [1, 2, 4, 6]

After Pass 1 (prefix):
```
result = [1, 1, 2, 8]
```

Pass 2 (suffix in-place), suffix starts at 1:
```
i=3: result[3] = 8 × 1 = 8,   suffix = 1 × 6 = 6
i=2: result[2] = 2 × 6 = 12, suffix = 6 × 4 = 24
i=1: result[1] = 1 × 24 = 24, suffix = 24 × 2 = 48
i=0: result[0] = 1 × 48 = 48, suffix = 48 × 1 = 48
```

**Return [48, 24, 12, 8]** ✓

**Complexity Analysis**
- **Time Complexity**: O(n) — two O(n) passes
- **Space Complexity**: O(1) extra space (output array doesn't count as extra)

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | With Division | Prefix+Suffix (Approach 1) | O(1) Space (Approach 2) |
|-------------|-------------|---------------|---------------------------|--------------------------|
| Time complexity | O(n²) ❌ | O(n) ✓ | O(n) ✓ | O(n) ✓ |
| No division | ✓ | ❌ | ✓ | ✓ |
| Handles zeros | ✓ | ❌ div-by-zero | ✓ | ✓ |
| Extra space | O(1) | O(1) | O(n) | O(1) ✅ |
| Interview ready | ❌ | ❌ | ✓ | ✅ **Best** |

**Winner**: **Approach 2 (O(1) extra space)** — meets all constraints, answers the follow-up, no division.

---

## Critical Edge Cases & Gotchas

### 1. **Array with one zero**
```java
Input: nums = [-1, 0, 1, 2, 3]
Output: [0, -6, 0, 0, 0]
Explanation: Only index 1 gets a non-zero product (product of everything except 0).
             All other indices have 0 somewhere in their product.
```

### 2. **Array with two zeros**
```java
Input: nums = [0, 0, 2, 3]
Output: [0, 0, 0, 0]
Explanation: Every position has at least one 0 in its product range.
```

### 3. **Array with negatives**
```java
Input: nums = [-1, -2, -3]
Output: [6, -3, 2]
Explanation: Prefix/suffix logic handles negatives automatically.
```

### 4. **Two-element array**
```java
Input: nums = [3, 4]
Output: [4, 3]
Explanation: output[0] = 4, output[1] = 3 — minimum valid case.
```

### 5. **All ones**
```java
Input: nums = [1, 1, 1, 1]
Output: [1, 1, 1, 1]
Explanation: Product of all 1s except any is still 1.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Starting prefix from index 1 using nums[0] directly**
```java
// WRONG — misunderstands what prefix[0] should be
prefix[0] = nums[0];       // ❌ this is the value AT index 0, not the product before it
prefix[1] = nums[0] * nums[1];
```
**Why wrong**: `prefix[i]` should be the product of everything **before** index `i`. There is nothing before index 0, so `prefix[0]` must be `1` (multiplicative identity).

**Fix**:
```java
prefix[0] = 1;                              // ✓ nothing to the left of index 0
for (int i = 1; i < n; i++) {
    prefix[i] = prefix[i - 1] * nums[i - 1];
}
```

### ❌ **MISTAKE 2: Using division (fails for zeros)**
```java
// WRONG — breaks when any nums[i] = 0
int total = 1;
for (int num : nums) total *= num;
for (int i = 0; i < n; i++) output[i] = total / nums[i];  // ❌ division by zero!
```
**Why wrong**: When `nums[i] = 0`, division blows up. The prefix × suffix approach avoids division entirely — zeros are handled correctly and automatically.

### ❌ **MISTAKE 3: In suffix pass, updating suffix BEFORE using it**
```java
// WRONG — updates suffix before multiplying into result
for (int i = n - 1; i >= 0; i--) {
    suffix *= nums[i];           // ❌ updated too early
    result[i] = result[i] * suffix;
}
```
**Why wrong**: `suffix` at position `i` should be the product of elements **after** `i` — not including `nums[i]` itself. Updating suffix before multiplying includes `nums[i]` in the suffix.

**Fix**: Multiply first, then update suffix.
```java
result[i] = result[i] * suffix;   // ✓ use current suffix (excludes nums[i])
suffix   *= nums[i];              // ✓ then update for next iteration
```

### ❌ **MISTAKE 4: Off-by-one in prefix fill**
```java
// WRONG — skips first element or goes out of bounds
for (int i = 0; i < n - 1; i++) {
    prefix[i + 1] = prefix[i] * nums[i + 1];  // ❌ wrong index: should be nums[i]
}
```
**Why wrong**: `prefix[i+1]` = product of all elements before index `i+1` = `prefix[i] × nums[i]`, not `nums[i+1]`.

**Fix**:
```java
for (int i = 1; i < n; i++) {
    prefix[i] = prefix[i - 1] * nums[i - 1];   // ✓ multiply by nums[i-1]
}
```

### ❌ **MISTAKE 5: Using separate prefix/suffix without combining**
```java
// WRONG — builds arrays but never multiplies them
for (int i = 0; i < n; i++) {
    output[i] = prefix[i];   // ❌ forgot to multiply by suffix[i]
}
```
**Fix**:
```java
for (int i = 0; i < n; i++) {
    output[i] = prefix[i] * suffix[i];   // ✓ both sides multiplied
}
```

---

## Complexity Analysis

### Approach 2 — O(1) Space

**Time Complexity: O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Pass 1 — fill prefix | O(n) | Single left-to-right loop |
| Pass 2 — multiply suffix | O(n) | Single right-to-left loop |
| **Total** | **O(n)** | **Two linear passes** |

**Space Complexity: O(1)** extra

| Component | Space | Reason |
|-----------|-------|--------|
| result array | O(n) | Required output (not counted as extra) |
| suffix variable | O(1) | Single integer |
| **Extra space** | **O(1)** | |

---

## Visualization

### Full Walk-Through — Approach 2

**Input:** nums = [-1, 0, 1, 2, 3]

```
Pass 1 — Build prefix in result:

  result[0] = 1
  result[1] = result[0] × nums[0] = 1 × (-1) = -1
  result[2] = result[1] × nums[1] = (-1) × 0  = 0
  result[3] = result[2] × nums[2] = 0 × 1    = 0
  result[4] = result[3] × nums[3] = 0 × 2    = 0

  result (after pass 1) = [1, -1, 0, 0, 0]

Pass 2 — Multiply suffix into result:

  suffix = 1

  i=4: result[4] = 0  × 1 = 0,   suffix = 1 × 3  = 3
  i=3: result[3] = 0  × 3 = 0,   suffix = 3 × 2  = 6
  i=2: result[2] = 0  × 6 = 0,   suffix = 6 × 1  = 6
  i=1: result[1] = -1 × 6 = -6,  suffix = 6 × 0  = 0
  i=0: result[0] = 1  × 0 = 0,   suffix = 0 × (-1) = 0

  result (final) = [0, -6, 0, 0, 0] ✓
```

---

## Comparison of Approaches

| Approach | Time | Extra Space | Division | When to Use |
|----------|------|-------------|----------|-------------|
| Brute Force | O(n²) ❌ | O(1) | No | ❌ Never |
| Division trick | O(n) | O(1) | ✅ Uses it | ❌ Not allowed |
| **Prefix + Suffix arrays** | **O(n)** | **O(n)** | No | Good for clarity |
| **O(1) space (in-place suffix)** | **O(n)** | **O(1) ✅** | No | ✅ **Best for interviews** |

**Recommendation**: Use **Approach 2** — it answers the follow-up, avoids division, and is the standard expected answer.

---

## Key Takeaways

1. **`output[i]` = (product of all left) × (product of all right)**
2. **prefix[i] = 1 at index 0** — there is nothing to the left, so use the multiplicative identity
3. **suffix variable starts at 1** for the same reason at the rightmost index
4. **Always multiply suffix BEFORE updating it** — suffix at index `i` excludes `nums[i]`
5. **Zeros are handled automatically** — no special case needed
6. **No division** — the prefix × suffix approach works even when elements are zero
7. **O(1) extra space** is possible by reusing the output array as the prefix array

---

## Interview Tips

**What to say in an interview:**

> "For every index, the answer is the product of everything to the left times the product of everything to the right. I'll first scan left to right filling the output array with prefix products. Then I'll scan right to left with a running suffix variable, multiplying it into the output array in place. This gives O(n) time and O(1) extra space with no division."

**Key points to mention:**
1. **Why no division** — zeros break division; prefix × suffix avoids it entirely
2. **prefix[0] = 1** — multiplicative identity since nothing is to the left
3. **In suffix pass, multiply before updating** — order matters
4. **O(1) space trick** — reuse output array for prefix, use one variable for suffix
5. **Zeros handled naturally** — no special casing needed

**If asked about division approach:**
> "We could compute total product and divide by each element, but that breaks for zeros. Even with zero-count tracking it becomes complicated. The prefix × suffix approach handles zeros automatically and is cleaner."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Products of Array Except Self** | Medium | **Prefix × Suffix** | **This problem** ← |
| Trapping Rain Water | Hard | Prefix max + suffix max | Same left/right precompute idea |
| Range Sum Query - Immutable | Easy | Prefix sum | Sum instead of product |
| Maximum Product Subarray | Medium | DP with prefix/suffix | Max product, not exclude-self |
| Sum of Subarray Ranges | Medium | Prefix sum variant | Range sums |

**Pattern Progression**:
1. **Prefix Sum** — build left → right, query in O(1)
2. **Prefix Product** (this problem) — same idea, multiplication instead of addition
3. **Prefix Max / Min** — same structure, Trapping Rain Water
4. **2D Prefix Sum** — extend to matrices

---

## Final Pattern Label

✅ **Prefix × Suffix Product — Left-Right Precompute**

**Remember:** `output[i]` = product of everything to the **left** × product of everything to the **right** — build both in two O(n) passes, no division needed!
