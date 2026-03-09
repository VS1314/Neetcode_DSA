# Concatenation of Array

## Problem Description

**Difficulty**: Easy

Given an integer array `nums` of length `n`, you want to create an array `ans` of length `2n` where `ans[i] == nums[i]` and `ans[i + n] == nums[i]` for `0 <= i < n` (0-indexed).

Specifically, `ans` is the concatenation of two `nums` arrays.

Return the array `ans`.

## Examples

### Example 1:
```
Input: nums = [1,2,1]
Output: [1,2,1,1,2,1]
Explanation: The array ans is formed as follows:
ans = [nums[0],nums[1],nums[2],nums[0],nums[1],nums[2]]
ans = [1,2,1,1,2,1]
```

### Example 2:
```
Input: nums = [1,3,2,1]
Output: [1,3,2,1,1,3,2,1]
Explanation: The array ans is formed as follows:
ans = [nums[0],nums[1],nums[2],nums[3],nums[0],nums[1],nums[2],nums[3]]
ans = [1,3,2,1,1,3,2,1]
```

## Constraints
- n == nums.length
- 1 <= n <= 1000
- 1 <= nums[i] <= 1000

---

## Pattern Recognition

**Primary Pattern**: **Array Construction / Direct Mapping**

**Why This Pattern?**
- Need to create a new array with specific values at specific positions
- No sorting, searching, or optimization required
- Direct index mapping: ans[i] = nums[i] and ans[i+n] = nums[i]
- Simple construction problem with fixed logic

**Key Insight**: This is a straightforward array construction problem where each element from the input array is placed at two specific positions in the output array.

**Related Patterns**:
1. **Array Manipulation** - Basic array operations
2. **Index Mapping** - Direct position-to-position mapping
3. **Simulation** - Following problem description exactly

---

## Algorithm & Approach

### Core Insight
The problem requires creating a new array that contains the input array twice - first copy at indices [0, n-1] and second copy at indices [n, 2n-1].

**Why it works:**
- For each element at index i in nums, we place it at two positions:
  - Position i (first half)
  - Position i + n (second half)
- This creates the concatenation effect

### Step-by-Step Algorithm

#### **Approach 1: Two Separate Loops**
```
1. Create new array ans of size 2n
2. First loop: Copy nums[i] to ans[i] for i in [0, n-1]
3. Second loop: Copy nums[i] to ans[i+n] for i in [0, n-1]
4. Return ans
```

**Code Implementation**
```java
public int[] getConcatenation(int[] nums) {
    int n = nums.length;
    int[] ans = new int[2 * n];
    
    // First copy
    for (int i = 0; i < n; i++) {
        ans[i] = nums[i];
    }
    
    // Second copy
    for (int i = 0; i < n; i++) {
        ans[i + n] = nums[i];
    }
    
    return ans;
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) - Two passes through array
- **Space Complexity**: O(n) - Output array of size 2n

#### **Approach 2: Single Loop (OPTIMAL)**
```
1. Create new array ans of size 2n
2. Single loop: For each index i in [0, n-1]:
   - Set ans[i] = nums[i]
   - Set ans[i + n] = nums[i]
3. Return ans
```
**Example Walkthrough**

Input: nums = [1,2,1]

| i | nums[i] | ans[i] | ans[i+n] | ans array |
|---|---------|--------|----------|-----------|
| 0 | 1 | ans[0]=1 | ans[3]=1 | [1,\_,\_,1,\_,\_] |
| 1 | 2 | ans[1]=2 | ans[4]=2 | [1,2,\_,1,2,\_] |
| 2 | 1 | ans[2]=1 | ans[5]=1 | [1,2,1,1,2,1] |

Output: [1,2,1,1,2,1]

**Code Implementation**
```java
public int[] getConcatenation(int[] nums) {
    int n = nums.length;
    int[] ans = new int[2 * n];
    
    for (int i = 0; i < n; i++) {
        ans[i] = nums[i];
        ans[i + n] = nums[i];
    }
    
    return ans;
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) - Single pass through array
- **Space Complexity**: O(n) - Output array of size 2n (required for output)

#### **Approach 3: Using System.arraycopy()**
```
1. Create new array ans of size 2n
2. Copy nums to ans[0...n-1] using System.arraycopy
3. Copy nums to ans[n...2n-1] using System.arraycopy
4. Return ans
```

**Code Implementation**
```java
public int[] getConcatenation(int[] nums) {
    int n = nums.length;
    int[] ans = new int[2 * n];
    
    System.arraycopy(nums, 0, ans, 0, n);
    System.arraycopy(nums, 0, ans, n, n);
    
    return ans;
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) - arraycopy is optimized but still O(n)
- **Space Complexity**: O(n) - Output array

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Two Loops | Single Loop | System.arraycopy |
|-------------|-----------|-------------|------------------|
| Correctness | ✓ | ✓ | ✓ |
| Time complexity | O(n) | O(n) | O(n) |
| Space complexity | O(n) | O(n) | O(n) |
| Code simplicity | Medium | ✅ **Simplest** | Medium |
| Readability | ✓ | ✅ **Best** | Medium |

**Winner**: Single loop approach - simplest, most readable, optimal complexity!

### Why Single Loop is Best?
- **Clarity**: Immediately clear that each element goes to two positions
- **Efficiency**: Only one loop iteration
- **Simplicity**: Minimal code, easy to understand

---

## Critical Edge Cases & Gotchas

### 1. **Single Element Array**
```java
Input: nums = [1]
Output: [1,1]
Explanation: n=1, ans has size 2
```

### 2. **Maximum Size Array**
```java
Input: nums = [1,2,...,1000] (n=1000)
Output: Array of size 2000
Explanation: Still O(n) time and space
```

### 3. **All Same Elements**
```java
Input: nums = [5,5,5]
Output: [5,5,5,5,5,5]
```

### 4. **Two Elements**
```java
Input: nums = [1,2]
Output: [1,2,1,2]
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Off-by-One Error in Second Half**
```java
// WRONG - Index out of bounds!
for (int i = 0; i < n; i++) {
    ans[i] = nums[i];
    ans[i + n + 1] = nums[i];  // Should be i+n, not i+n+1!
}
```

**Why wrong**: `ans[i + n + 1]` will go out of bounds when i = n-1.

**Fix**: Use `ans[i + n]`

### ❌ **MISTAKE 2: Wrong Loop Condition**
```java
// WRONG - Will cause ArrayIndexOutOfBoundsException
for (int i = 0; i <= n; i++) {  // Should be i < n, not i <= n
    ans[i] = nums[i];
    ans[i + n] = nums[i];
}
```

**Why wrong**: When i=n, `nums[n]` is out of bounds.

**Fix**: Use `i < n`

### ❌ **MISTAKE 3: Creating Wrong Size Array**
```java
// WRONG - Array size should be 2*n
int[] ans = new int[n];  // Too small!
```

**Why wrong**: Can't fit concatenation in array of size n.

**Fix**: Use `new int[2 * n]`

### ❌ **MISTAKE 4: Overwriting Values**
```java
// WRONG - This only copies to first half
for (int i = 0; i < 2 * n; i++) {
    ans[i] = nums[i];  // nums[i] doesn't exist when i >= n!
}
```

**Why wrong**: nums only has n elements, can't access nums[n] or beyond.

**Fix**: Use modulo or two separate assignments
```java
// CORRECT
for (int i = 0; i < 2 * n; i++) {
    ans[i] = nums[i % n];  // Modulo wraps around
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Create array | O(n) | Allocate space for 2n elements |
| Fill array | O(n) | Loop through n elements |
| Total | O(n) | Linear time |

### Space Complexity: **O(n)**

| Component | Space |
|-----------|-------|
| Output array | O(2n) = O(n) |
| Input array | Not counted (given) |
| Total | O(n) |

**Note**: The output array is required by the problem, so O(n) space is unavoidable.

---

## Visualization

### Example Walkthrough
```
Input: nums = [1, 2, 1]
n = 3

Step 1: Create ans of size 6
ans = [_, _, _, _, _, _]

Step 2: Fill first half (i=0 to 2)
i=0: ans[0] = nums[0] = 1
     ans[3] = nums[0] = 1
     ans = [1, _, _, 1, _, _]

i=1: ans[1] = nums[1] = 2
     ans[4] = nums[1] = 2
     ans = [1, 2, _, 1, 2, _]

i=2: ans[2] = nums[2] = 1
     ans[5] = nums[2] = 1
     ans = [1, 2, 1, 1, 2, 1]

Output: [1, 2, 1, 1, 2, 1]
```

---

## Comparison of Approaches

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Two Loops | O(n) | O(n) | Clear separation | More lines of code |
| **Single Loop** | **O(n)** | **O(n)** | **Concise, clear** ✅ | None |
| System.arraycopy | O(n) | O(n) | Potentially faster | Less clear intent |

**Best Choice**: Single Loop ✓

---

## Key Takeaways

1. **Simple Construction**: No complex algorithm needed
2. **Index Mapping**: ans[i] and ans[i+n] both get nums[i]
3. **Single Pass**: Can fill both halves in one loop
4. **No Optimization Needed**: Problem is straightforward
5. **Watch Array Bounds**: Ensure i+n doesn't exceed array size
6. **Pattern Recognition**: "Create array" + "fixed mapping" = Construction pattern

---

## Interview Tips

**What to say in an interview:**

> "This is a straightforward array construction problem. I need to create an array of size 2n where the first n elements are a copy of the input array, and the second n elements are also a copy of the input array. I'll use a single loop to set ans[i] and ans[i+n] to nums[i] for each index i. This gives O(n) time and O(n) space, which is optimal since the output itself requires O(n) space."

**Key points to mention:**
1. **Pattern**: Array construction / direct mapping
2. **Approach**: Single loop fills both halves simultaneously
3. **Complexity**: O(n) time, O(n) space (unavoidable)
4. **Edge cases**: Single element, maximum size array

**If asked about optimization:**
> "The time complexity is already O(n) which is optimal - we must look at each element at least once. The space complexity is also optimal at O(n) since the output array itself requires 2n space. We could use System.arraycopy for potentially better constant factors, but the asymptotic complexity remains the same."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Concatenation of Array** | Easy | **Array Construction** | **Direct concatenation** ← This problem |
| Build Array from Permutation | Easy | Array Construction | Index-based mapping |
| Shuffle the Array | Easy | Array Construction | Interleaving pattern |
| Running Sum of 1d Array | Easy | Array Construction | Cumulative sum |
| Kids With Greatest Candies | Easy | Array Construction | Comparison-based |

**Pattern Family**: Array Construction / Manipulation

---

## Final Pattern Label

✅ **Array Construction – Direct Concatenation**

**Remember:** When the problem asks to "create/build an array" with fixed mapping rules and no optimization hints → think direct construction pattern!