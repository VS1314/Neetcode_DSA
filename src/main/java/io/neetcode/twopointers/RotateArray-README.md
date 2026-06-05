# Rotate Array

## Problem Description

**Difficulty**: Medium

Given an integer array `nums`, rotate the array to the right by `k` steps, where `k` is non-negative.

**Note**: The rotation must be done **in-place** with O(1) extra space.

## Examples

### Example 1:
```
Input: nums = [1,2,3,4,5,6,7,8], k = 4
Output: [5,6,7,8,1,2,3,4]
Explanation:
rotate 1 steps to the right: [8,1,2,3,4,5,6,7]
rotate 2 steps to the right: [7,8,1,2,3,4,5,6]
rotate 3 steps to the right: [6,7,8,1,2,3,4,5]
rotate 4 steps to the right: [5,6,7,8,1,2,3,4]
```

### Example 2:
```
Input: nums = [1000,2,4,-3], k = 2
Output: [4,-3,1000,2]
Explanation:
rotate 1 steps to the right: [-3,1000,2,4]
rotate 2 steps to the right: [4,-3,1000,2]
```

### Example 3:
```
Input: nums = [1,2], k = 3
Output: [2,1]
Explanation: k = 3 % 2 = 1, so rotate 1 step to the right: [2,1]
```

## Constraints
- 1 <= nums.length <= 100,000
- -2^31 <= nums[i] <= 2^31 - 1
- 0 <= k <= 100,000

**Follow-up**: Could you do it in-place with O(1) extra space?

---

## Pattern Recognition

**Primary Pattern**: **Array Reversal (Two Pointers for Segment Reversal)**

**Why This Pattern?**
- Need to rotate array in-place
- Must achieve O(1) extra space
- Moving elements one by one is O(n*k) — too slow!
- Reversal trick enables O(n) time with O(1) space

**Key Insight**: Rotate Right by k = Three Reversals
```
Rotating right by k means:
  - Last k elements move to the front
  - First n-k elements move to the back

Example: [1,2,3,4,5,6,7,8], k=4
  Last 4: [5,6,7,8]
  First 4: [1,2,3,4]
  Result: [5,6,7,8,1,2,3,4]

The Reversal Trick:
  Step 1: Reverse entire array
    [1,2,3,4,5,6,7,8] → [8,7,6,5,4,3,2,1]
  
  Step 2: Reverse first k elements
    [8,7,6,5,4,3,2,1] → [5,6,7,8,4,3,2,1]
  
  Step 3: Reverse remaining n-k elements
    [5,6,7,8,4,3,2,1] → [5,6,7,8,1,2,3,4] ✓
```

**Why Reversal Works?**
```
Original: [A | B]  (A = first n-k, B = last k)
Goal:     [B | A]  (rotate right by k)

Reverse all: [B' | A']  (reverse of B, reverse of A)
Reverse B':  [B | A']   (B' reversed back to B)
Reverse A':  [B | A]    (A' reversed back to A) ✓

Example with symbols:
  A = [1,2,3,4], B = [5,6,7,8]
  
  Reverse all: [8,7,6,5,4,3,2,1]
               [B' | A']
  
  Reverse B': [5,6,7,8,4,3,2,1]
              [B | A']
  
  Reverse A': [5,6,7,8,1,2,3,4]
              [B | A] ✓
```

**Critical Detail**: k can be larger than n!
```
If k > n: rotation repeats!
Example: n=4, k=6
  Rotate 6 times = rotate 2 times (6 % 4 = 2)
  
Always use: k = k % n
```

**Related Patterns**:
1. **Array Reversal** — Core technique
2. **Two Pointers** — Used within reversal
3. **Cyclic Replacements** — Alternative O(1) space approach
4. **String Rotation** — Similar problem with strings

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Rotate one step k times
  → Each rotation: shift all n elements right
  → O(n * k) time
  → If k = 100,000 and n = 100,000: 10 billion operations! ❌

Extra Array:
  → Copy to new array with rotated positions
  → O(n) time, O(n) space
  → Violates O(1) space requirement ❌

Reversal Trick:
  → Three reversal operations
  → O(n) time, O(1) space
  → Optimal! ✓
```

### Step-by-Step Algorithm

---

#### **Approach 1: Three Reversals (OPTIMAL)**

**Core Idea**:
- Normalize k: `k = k % n`
- Reverse entire array
- Reverse first k elements
- Reverse remaining n-k elements

**Algorithm**
```
rotate(nums, k):
    n = length(nums)
    k = k % n  // Handle k > n
    
    reverse(nums, 0, n-1)     // Reverse entire array
    reverse(nums, 0, k-1)     // Reverse first k elements
    reverse(nums, k, n-1)     // Reverse remaining elements

reverse(nums, left, right):
    while left < right:
        swap(nums[left], nums[right])
        left++
        right--
```

**Code Implementation**
```java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;  // Normalize k
        
        // Step 1: Reverse entire array
        reverse(nums, 0, n - 1);
        
        // Step 2: Reverse first k elements
        reverse(nums, 0, k - 1);
        
        // Step 3: Reverse remaining n-k elements
        reverse(nums, k, n - 1);
    }
    
    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
```

**Example Walkthrough**

Input: `nums = [1,2,3,4,5,6,7,8]`, `k = 4`

**Step 0: Normalize k**
```
k = 4 % 8 = 4 (no change)
```

**Step 1: Reverse Entire Array (0 to 7)**
```
Before: [1,2,3,4,5,6,7,8]
         ↑             ↑
         L             R

Swap and move:
[8,2,3,4,5,6,7,1]
   ↑         ↑
   L         R

[8,7,3,4,5,6,2,1]
     ↑     ↑
     L     R

[8,7,6,4,5,3,2,1]
       ↑ ↑
       L R

[8,7,6,5,4,3,2,1]
        ↑
       L>R (stop)

After: [8,7,6,5,4,3,2,1]
```

**Step 2: Reverse First k=4 Elements (0 to 3)**
```
Before: [8,7,6,5,4,3,2,1]
         ↑     ↑
         L     R

Swap and move:
[5,7,6,8,4,3,2,1]
   ↑ ↑
   L R

[5,6,7,8,4,3,2,1]
     ↑
    L,R (stop)

After: [5,6,7,8,4,3,2,1]
```

**Step 3: Reverse Remaining n-k=4 Elements (4 to 7)**
```
Before: [5,6,7,8,4,3,2,1]
                 ↑     ↑
                 L     R

Swap and move:
[5,6,7,8,1,3,2,4]
         ↑ ↑
         L R

[5,6,7,8,1,2,3,4]
           ↑
          L>R (stop)

After: [5,6,7,8,1,2,3,4] ✓
```

**Complexity Analysis**
- **Time Complexity**: O(n) — Three passes through array, each O(n)
- **Space Complexity**: O(1) — Only a few variables, in-place reversal

---

#### **Approach 2: Extra Array (NOT OPTIMAL - Space Violation)**

**Core Idea**: Create new array and place each element at rotated position.

**Code Implementation**
```java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        int[] rotated = new int[n];
        
        for (int i = 0; i < n; i++) {
            rotated[(i + k) % n] = nums[i];
        }
        
        // Copy back
        for (int i = 0; i < n; i++) {
            nums[i] = rotated[i];
        }
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n)
- **Space Complexity**: O(n) — Extra array
- **Why Not Optimal**: Violates O(1) space requirement

---

#### **Approach 3: Cyclic Replacements (OPTIMAL BUT COMPLEX)**

**Core Idea**: Follow the cycle of replacements, each element goes to its final position.

**Code Implementation**
```java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        
        int count = 0;
        for (int start = 0; count < n; start++) {
            int current = start;
            int prev = nums[start];
            
            do {
                int next = (current + k) % n;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n)
- **Space Complexity**: O(1)
- **Why Not Preferred**: More complex, harder to understand and debug

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | Extra Array | Cyclic | **Reversal** |
|-------------|-------------|-------------|--------|-------------|
| Time complexity | O(n*k) ❌ | O(n) ✓ | O(n) ✓ | **O(n) ✅** |
| Space complexity | O(1) ✓ | O(n) ❌ | O(1) ✓ | **O(1) ✅** |
| Code simplicity | Simple | Simple | Complex ❌ | **Clean ✅** |
| In-place | ✓ | ❌ | ✓ | **✅** |
| Optimal | ❌ | ❌ | ✓ | **✅** |

**Winner**: **Three Reversals** — optimal time and space, cleanest code!

### Why Reversal is Brilliant
```
Intuition: Rotating means reordering two segments

Original: [A | B]
Goal:     [B | A]

Direct approach: Move elements one by one → O(n*k) or O(n) space

Reversal approach: Transform through inversions
  [A | B] 
  → [B' | A']  (reverse all)
  → [B | A']   (un-reverse B')
  → [B | A]    (un-reverse A')
  
Why it works:
  - Reversal is O(n/2) = O(n)
  - Three reversals = 3*O(n) = O(n)
  - In-place, no extra space
  - Each element visited constant times
```

### Visual Proof
```
nums = [1,2,3,4,5,6,7], k=3

Goal: Move [5,6,7] to front

Step 1: Reverse all
[1,2,3,4,5,6,7] → [7,6,5,4,3,2,1]

Notice: Last k elements are at front (but reversed!)
[7,6,5 | 4,3,2,1]
  ↑↑↑

Step 2: Reverse first k
[7,6,5,4,3,2,1] → [5,6,7,4,3,2,1]

Now first k are correct!
[5,6,7 | 4,3,2,1]
  ✓✓✓

Step 3: Reverse remaining
[5,6,7,4,3,2,1] → [5,6,7,1,2,3,4]

All correct! ✓
[5,6,7,1,2,3,4]
  ✓✓✓ ✓✓✓✓
```

---

## Critical Edge Cases & Gotchas

### 1. **k Larger Than n**
```java
Input: nums = [1,2,3], k = 4
Expected: [3,1,2]  // Rotate 4 = rotate 1 (since 4 % 3 = 1)
Explanation: k = k % n is critical!
```

### 2. **k Equals n**
```java
Input: nums = [1,2,3,4], k = 4
Expected: [1,2,3,4]  // No change (full rotation)
Explanation: k % n = 0, array stays same.
```

### 3. **k is Zero**
```java
Input: nums = [1,2,3,4], k = 0
Expected: [1,2,3,4]  // No rotation
Explanation: k = 0, no change needed.
```

### 4. **Single Element**
```java
Input: nums = [1], k = 100
Expected: [1]  // Single element always stays same
Explanation: Any rotation of single element is itself.
```

### 5. **Two Elements**
```java
Input: nums = [1,2], k = 1
Expected: [2,1]  // Swap elements
Explanation: Rotate 1 step swaps two elements.
```

### 6. **All Same Elements**
```java
Input: nums = [5,5,5,5], k = 2
Expected: [5,5,5,5]  // Looks unchanged but rotation happens
Explanation: Works correctly even with duplicates.
```

### 7. **Negative Numbers**
```java
Input: nums = [-1,-100,3,99], k = 2
Expected: [3,99,-1,-100]
Explanation: Works with negative numbers.
```

### 8. **Large k (k >> n)**
```java
Input: nums = [1,2], k = 100000
Expected: [1,2]  // 100000 % 2 = 0
Explanation: Must normalize k first!
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Forgetting to Normalize k**
```java
// WRONG - doesn't handle k > n
public void rotate(int[] nums, int k) {
    int n = nums.length;
    // Missing: k = k % n
    
    reverse(nums, 0, n - 1);
    reverse(nums, 0, k - 1);  // k might be >= n, ArrayIndexOutOfBounds!
    reverse(nums, k, n - 1);
}
```

**Why wrong**: If k >= n, array indices go out of bounds!

**Dry run failure for nums=[1,2,3], k=5:**
```
n = 3, k = 5
reverse(nums, 0, 2) → [3,2,1] ✓
reverse(nums, 0, 4) → ERROR! Index 4 out of bounds for array size 3!
```

**Fix**: Always normalize k first
```java
k = k % n;
```

### ❌ **MISTAKE 2: Wrong Reversal Order**
```java
// WRONG - reverses in wrong order
public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n;
    
    reverse(nums, 0, k - 1);     // WRONG ORDER!
    reverse(nums, k, n - 1);
    reverse(nums, 0, n - 1);
}
```

**Why wrong**: Must reverse entire array FIRST, then the two segments!

**Dry run failure for nums=[1,2,3,4,5], k=2:**
```
Step 1: reverse(0, 1) → [2,1,3,4,5]
Step 2: reverse(2, 4) → [2,1,5,4,3]
Step 3: reverse(0, 4) → [3,4,5,1,2]

Expected: [4,5,1,2,3]
Got:      [3,4,5,1,2] ❌
```

**Fix**: Correct order
```java
reverse(nums, 0, n - 1);     // 1. Entire array
reverse(nums, 0, k - 1);     // 2. First k
reverse(nums, k, n - 1);     // 3. Remaining
```

### ❌ **MISTAKE 3: Off-by-One in Reverse Bounds**
```java
// WRONG - incorrect indices
public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n;
    
    reverse(nums, 0, n - 1);
    reverse(nums, 1, k);        // WRONG! Should be 0 to k-1
    reverse(nums, k + 1, n);    // WRONG! Should be k to n-1
}
```

**Why wrong**: Indices are off, misses elements or goes out of bounds!

**Dry run failure for nums=[1,2,3,4], k=2:**
```
Step 1: reverse(0, 3) → [4,3,2,1]
Step 2: reverse(1, 2) → [4,2,3,1]  (missed index 0!)
Step 3: reverse(3, 4) → Out of bounds!

Expected: [3,4,1,2]
Got:      ERROR!
```

**Fix**: Correct indices
```java
reverse(nums, 0, k - 1);     // First k elements: 0 to k-1
reverse(nums, k, n - 1);     // Remaining: k to n-1
```

### ❌ **MISTAKE 4: Rotating Left Instead of Right**
```java
// WRONG - rotates left instead of right
public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n;
    
    reverse(nums, 0, n - 1);
    reverse(nums, 0, n - k - 1);  // WRONG! This rotates LEFT
    reverse(nums, n - k, n - 1);
}
```

**Why wrong**: Reverses wrong segments, achieves left rotation instead!

**Dry run for nums=[1,2,3,4,5], k=2:**
```
Right rotate by 2: [4,5,1,2,3]
Left rotate by 2:  [3,4,5,1,2]

This code produces: [3,4,5,1,2] (LEFT rotation!) ❌
```

**Fix**: Remember formula for RIGHT rotation
```java
// Right rotate by k:
reverse(nums, 0, n - 1);
reverse(nums, 0, k - 1);
reverse(nums, k, n - 1);
```

### ❌ **MISTAKE 5: Not Handling k=0 or k=n**
```java
// WRONG - unnecessary work
public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n;
    // Missing early return check
    
    reverse(nums, 0, n - 1);
    reverse(nums, 0, k - 1);  // If k=0, this reverses nothing but still executes
    reverse(nums, k, n - 1);
}
```

**Why wrong**: Not technically wrong, but inefficient. When k=0, no rotation needed!

**Fix**: Early return for efficiency
```java
k = k % n;
if (k == 0) return;  // No rotation needed
```

### ❌ **MISTAKE 6: Using Wrong Swap Logic**
```java
// WRONG - incorrect swap
private void reverse(int[] nums, int left, int right) {
    while (left < right) {
        nums[left] = nums[right];   // WRONG! Lost original nums[left]
        nums[right] = nums[left];   // WRONG! Both become nums[right]
        left++;
        right--;
    }
}
```

**Why wrong**: Doesn't use temp variable, loses data!

**Dry run failure:**
```
[1,2,3,4], reverse(0,3)
left=0, right=3:
  nums[0] = nums[3] = 4  → [4,2,3,4]
  nums[3] = nums[0] = 4  → [4,2,3,4] (not swapped!)
```

**Fix**: Use temp variable
```java
int temp = nums[left];
nums[left] = nums[right];
nums[right] = temp;
```

### ❌ **MISTAKE 7: Modifying k Before Using It**
```java
// WRONG - modifies k incorrectly
public void rotate(int[] nums, int k) {
    int n = nums.length;
    
    reverse(nums, 0, n - 1);
    k = k % n;  // WRONG! Should normalize k BEFORE any operations
    reverse(nums, 0, k - 1);
    reverse(nums, k, n - 1);
}
```

**Why wrong**: If k > n, first reverse happens, but subsequent ones use wrong k!

**Better practice**: Normalize k at the very beginning
```java
int n = nums.length;
k = k % n;  // First thing after getting n
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Normalize k | O(1) | Simple modulo |
| Reverse entire array | O(n) | Visit each element once |
| Reverse first k | O(k) | Visit k elements |
| Reverse remaining n-k | O(n-k) | Visit n-k elements |
| **Total** | **O(n)** | n + k + (n-k) = 2n → O(n) |

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| Variables (k, n, temp) | O(1) | Few integers |
| Reverse operation | O(1) | In-place, no extra array |
| **Total** | **O(1)** | Constant extra space |

**Why O(n) Time is Optimal:**
- Must touch each element at least once
- Three reversals visit each element exactly twice
- Can't do better than O(n)

---

## Visualization

### Complete Example Walkthrough

**Input:** `nums = [1,2,3,4,5,6,7]`, `k = 3`

**Goal:** Move last 3 elements to front → `[5,6,7,1,2,3,4]`

---

**Step 0: Normalize k**
```
k = 3 % 7 = 3 (no change)
```

---

**Step 1: Reverse Entire Array**
```
Original: [1, 2, 3, 4, 5, 6, 7]
           ↑                 ↑
           L                 R

After swap: [7, 2, 3, 4, 5, 6, 1]
               ↑           ↑
               L           R

After swap: [7, 6, 3, 4, 5, 2, 1]
                  ↑     ↑
                  L     R

After swap: [7, 6, 5, 4, 3, 2, 1]
                  ↑
                 L,R (stop)

Result: [7, 6, 5, 4, 3, 2, 1]
```

**Notice:** Last k=3 elements [5,6,7] are now at front (but reversed!)
```
[7, 6, 5, 4, 3, 2, 1]
 ↑  ↑  ↑  ← These are 5,6,7 reversed!
```

---

**Step 2: Reverse First k=3 Elements**
```
Current: [7, 6, 5, 4, 3, 2, 1]
          ↑     ↑
          L     R

After swap: [5, 6, 7, 4, 3, 2, 1]
    ↑
   L,R (stop)

Result: [5, 6, 7, 4, 3, 2, 1]
```

**Notice:** First k=3 elements are now correct! [5,6,7] ✓
```
[5, 6, 7, 4, 3, 2, 1]
 ✓  ✓  ✓  ← Correct!
```

---

**Step 3: Reverse Remaining n-k=4 Elements**
```
Current: [5, 6, 7, 4, 3, 2, 1]
                   ↑        ↑
                   L        R

After swap: [5, 6, 7, 1, 3, 2, 4]
               ↑  ↑
               L  R

After swap: [5, 6, 7, 1, 2, 3, 4]
                  ↑
                 L>R (stop)

Final Result: [5, 6, 7, 1, 2, 3, 4] ✓
```

**Perfect!** All elements in correct position!

---

### Why Each Reversal is Necessary

```
Original:        [1, 2, 3, 4, 5, 6, 7]
                  ↑________↑  ↑_____↑
                     A          B

Goal: [B, A]  →  [5, 6, 7, 1, 2, 3, 4]

After reverse all:   [7, 6, 5, 4, 3, 2, 1]
                      ↑_____↑  ↑________↑
                        B'        A'
                     (reversed B, reversed A)

After reverse B':    [5, 6, 7, 4, 3, 2, 1]
                      ↑_____↑  ↑________↑
                         B        A'
                      (correct!)  (still reversed)

After reverse A':    [5, 6, 7, 1, 2, 3, 4]
                      ↑_____↑  ↑________↑
                         B         A
                      (correct!)  (correct!) ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | In-Place | Code Complexity | Optimal |
|----------|------|-------|----------|-----------------|---------|
| Brute Force (shift k times) | O(n*k) | O(1) | ✅ | Simple | ❌ |
| Extra Array | O(n) | O(n) | ❌ | Simple | ❌ |
| Cyclic Replacements | O(n) | O(1) | ✅ | Complex | ✅ |
| **Three Reversals** | **O(n)** | **O(1)** | **✅** | **Clean** | **✅** |

**Recommendation**: Always use **Three Reversals** — optimal, clean, and easy to understand!

---

## Key Takeaways

1. **Normalize k first** — always use `k = k % n` to handle k > n
2. **Three reversals** — reverse all, reverse first k, reverse remaining
3. **Order matters** — must reverse entire array FIRST
4. **In-place with O(1) space** — no extra array needed
5. **O(n) time is optimal** — can't do better, must touch each element
6. **Rotation intuition** — moving last k to front = reordering two segments
7. **Reversal is the key** — transforms segment reordering into simple inversions

---

## Interview Tips

**What to say in an interview:**

> "This is an array rotation problem that requires in-place O(1) space solution. The key insight is using three reversals: first reverse the entire array, then reverse the first k elements, then reverse the remaining n-k elements. This works because rotation is essentially reordering two segments [A|B] to [B|A], and reversals achieve this transformation elegantly. I need to normalize k using k % n to handle cases where k is larger than the array length. The time complexity is O(n) with three passes, and space complexity is O(1) since we only use a few variables for in-place swapping."

**Key points to mention:**
1. **Normalize k** — handle k > n with modulo
2. **Three reversals pattern** — reverse all, first k, remaining
3. **Why it works** — segment reordering via inversions
4. **In-place O(1) space** — meets the follow-up requirement
5. **Complexity** — O(n) time (optimal), O(1) space

**If asked about alternatives:**
> "I could use an extra array to place each element at position (i+k)%n, which is O(n) time and O(n) space, but violates the O(1) space requirement. Or cyclic replacements which is O(n) time and O(1) space, but more complex. The three reversals approach is optimal and cleanest."

**Common Follow-ups:**
- "How would you rotate left?" → Use k = n - k, then same algorithm
- "What if k is negative?" → Convert to positive: k = (k % n + n) % n
- "Prove the reversal works" → Show transformation: [A|B] → [B'|A'] → [B|A']→ [B|A]

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Rotate Array** | Medium | **Array Reversal** | **This problem** ← **Rotate right** |
| Rotate String | Easy | String Rotation | Strings instead of array |
| Rotate List | Medium | Linked List Rotation | Linked list, find tail and reconnect |
| Rotate Image | Medium | Matrix Rotation | 2D rotation, in-place matrix manipulation |
| Reverse String | Easy | Two Pointers | Simple reversal |
| Reverse Words in String | Medium | String Reversal | Similar reversal pattern on words |

**Pattern Connection**:
- **Reversal** is the core technique
- **Rotate** = reorder segments using reversals
- **Two Pointers** used within reversal operation

---

## Final Pattern Label

✅ **Array Reversal (Two Pointers for Segment Reversal)**

**Remember:** Normalize k first with `k % n`. Use three reversals: (1) reverse entire array, (2) reverse first k elements, (3) reverse remaining n-k elements. Each reversal uses two pointers moving inward while swapping. O(n) time, O(1) space. This is the optimal in-place solution!
