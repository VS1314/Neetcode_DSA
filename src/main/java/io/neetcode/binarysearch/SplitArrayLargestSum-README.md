# Split Array Largest Sum

## Problem Description

**Difficulty**: Hard

You are given an integer array `nums` and an integer `k`. Split `nums` into **k non-empty subarrays** such that the **largest sum** of any subarray is **minimized**.

Return the **minimized largest sum** of the split.

A **subarray** is a contiguous part of the array.

## Examples

### Example 1:
```
Input: nums = [2,4,10,1,5], k = 2
Output: 16

Explanation:
  The best way is to split into [2,4,10] and [1,5]
  
  Subarray 1: [2,4,10], sum = 16
  Subarray 2: [1,5], sum = 6
  
  Largest sum = max(16, 6) = 16
  
  This is the minimum possible largest sum.
```

### Example 2:
```
Input: nums = [1,0,2,3,5], k = 4
Output: 5

Explanation:
  The best way is to split into [1], [0,2], [3], [5]
  
  Subarray 1: [1], sum = 1
  Subarray 2: [0,2], sum = 2
  Subarray 3: [3], sum = 3
  Subarray 4: [5], sum = 5
  
  Largest sum = max(1, 2, 3, 5) = 5
  
  This is the minimum possible.
```

### Example 3:
```
Input: nums = [7,2,5,10,8], k = 2
Output: 18

Explanation:
  Best split: [7,2,5] and [10,8]
  
  Sums: 14 and 18
  Largest: 18
```

### Example 4:
```
Input: nums = [1,2,3,4,5], k = 2
Output: 9

Explanation:
  Best split: [1,2,3,4] and [5]
  
  Sums: 10 and 5
  Largest: 10
  
  Wait, let me recalculate:
  [1,2,3] and [4,5]: sums 6 and 9, max = 9
  [1,2,3,4] and [5]: sums 10 and 5, max = 10
  [1,2] and [3,4,5]: sums 3 and 12, max = 12
  
  Best is [1,2,3] and [4,5] with max = 9
```

### Example 5:
```
Input: nums = [10,5,13,4,8,4,5,11,14,9,16,10,20,8], k = 8
Output: 25

Explanation:
  Split into 8 subarrays to minimize largest sum
```

### Example 6:
```
Input: nums = [1,4,4], k = 3
Output: 4

Explanation:
  Split into [1], [4], [4]
  Each element becomes its own subarray
  Largest sum = 4
```

### Example 7:
```
Input: nums = [10], k = 1
Output: 10

Explanation:
  Only one subarray possible
  Sum = 10
```

### Example 8:
```
Input: nums = [1,2,3,4,5], k = 1
Output: 15

Explanation:
  All elements in one subarray
  Sum = 1+2+3+4+5 = 15
```

### Example 9:
```
Input: nums = [1,2,3,4,5], k = 5
Output: 5

Explanation:
  Each element in its own subarray
  Largest = max(1,2,3,4,5) = 5
```

### Example 10:
```
Input: nums = [5,5,5,5,5], k = 2
Output: 15

Explanation:
  Best split: [5,5,5] and [5,5]
  Sums: 15 and 10
  Largest: 15
```

## Constraints
- 1 <= nums.length <= 1,000
- 0 <= nums[i] <= 1,000,000
- 1 <= k <= min(50, nums.length)

**Recommended Complexity**: O(n * log(sum)) time and O(1) space, where n is array length and sum is sum of all elements

---

## Pattern Recognition

**Primary Pattern**: **Binary Search on Answer Space (Minimize Maximum)**

**Why This Pattern?**
- Need to **minimize the maximum** (largest sum)
- Answer is in a **range** with monotonic property
- Can **validate** if a given maximum sum works
- **Binary search** on answer efficiently

**Key Insight**: Binary Search on Maximum Sum
```
What are we searching for?
  The minimum possible value of the largest subarray sum

What's the search space?
  Lower bound: max(nums) - at least one subarray contains the largest element
  Upper bound: sum(nums) - all elements in one subarray

Example: nums = [2,4,10,1,5], k = 2
  Lower bound: max(2,4,10,1,5) = 10
  Upper bound: 2+4+10+1+5 = 22
  
  Search space: [10, 22]

Binary search on this range!
For each candidate maximum sum, check if we can split into ≤ k subarrays.
```

**The Monotonic Property**:
```
Key observation:
  If we can split array with max sum = X into ≤ k subarrays,
  then we can also do it with max sum = X+1, X+2, etc.
  
  Larger max sum → easier to achieve → fewer subarrays needed

Example: nums = [2,4,10,1,5], k = 2

Can split with max sum = 10?
  [2,4] (sum=6), [10] (sum=10), [1,5] (sum=6)
  Need 3 subarrays > k=2
  NO ✗

Can split with max sum = 16?
  [2,4,10] (sum=16), [1,5] (sum=6)
  Need 2 subarrays = k=2
  YES ✓

Can split with max sum = 20?
  Even easier! Still can do [2,4,10] and [1,5]
  YES ✓

Monotonic: if X works, then X+1, X+2, ... all work!
Binary search finds the minimum X that works.
```

**The Validation Function**:
```
canSplit(nums, k, maxSum):
  Count how many subarrays needed if max sum per subarray ≤ maxSum
  
  Greedy approach:
    Keep adding elements to current subarray
    When adding next element exceeds maxSum:
      Start new subarray
      
  If subarrays_needed <= k: return true
  Else: return false

Example: nums = [2,4,10,1,5], maxSum = 16
  
  Current subarray: []
  Add 2: [2], sum=2 ≤ 16 ✓
  Add 4: [2,4], sum=6 ≤ 16 ✓
  Add 10: [2,4,10], sum=16 ≤ 16 ✓
  Add 1: sum would be 17 > 16 ✗
    Start new subarray
    Subarrays: 1
  Add 1: [1], sum=1 ≤ 16 ✓
  Add 5: [1,5], sum=6 ≤ 16 ✓
  End: subarrays = 2
  
  2 <= k=2? YES ✓
```

**Why Greedy Validation Works**:
```
Greedy approach: pack elements into each subarray as much as possible

Why is this optimal for validation?
  If we can't achieve ≤ k subarrays with greedy packing,
  we can't do it with any other packing!
  
  Greedy uses minimum number of subarrays for given maxSum.
  
Proof by contradiction:
  Suppose greedy uses n subarrays
  And some other split uses m < n subarrays
  
  Then some subarray in the other split must have more elements
  But greedy already packs maximally!
  Contradiction.
  
Greedy validation is correct!
```

**Example Showing Binary Search**:
```
nums = [2,4,10,1,5], k = 2

Search space: [10, 22]

Step 1: left=10, right=22, mid=16
  Can split with maxSum=16?
  [2,4,10] and [1,5]: YES, uses 2 subarrays
  
  16 works! Try smaller: right = 16

Step 2: left=10, right=16, mid=13
  Can split with maxSum=13?
  [2,4] (sum=6), [10] (sum=10), [1,5] (sum=6): NO, uses 3 subarrays
  
  13 doesn't work! Try larger: left = 14

Step 3: left=14, right=16, mid=15
  Can split with maxSum=15?
  [2,4] (sum=6), [10] (sum=10), [1,5] (sum=6): NO, uses 3 subarrays
  
  15 doesn't work! Try larger: left = 16

Step 4: left=16, right=16
  Done! Answer = 16 ✓
```

**Why This is Optimal**:
```
Brute force:
  Try all possible ways to split into k subarrays
  Calculate max sum for each split
  Return minimum
  Exponential time! O(k^n) ❌

Binary search on answer:
  Search space: O(sum of array)
  Each validation: O(n)
  Total: O(n * log(sum)) ✓
  
Much faster!

For n=1000, sum=1,000,000:
  Brute force: intractable
  Binary search: ~1000 * 20 = 20,000 operations ✓
```

**Related Patterns**:
1. **Binary Search on Answer Space** — Core technique
2. **Minimize Maximum** — Optimization goal
3. **Greedy Validation** — Check if answer works
4. **Similar to Koko Eating Bananas** — Same pattern
5. **Similar to Capacity to Ship Packages** — Same pattern

---

## Algorithm & Approach

### Core Insight

**Why Binary Search on Answer Space Works:**
```
Key properties:
  1. Answer is in range [max(nums), sum(nums)]
  2. Monotonic property: if X works, X+1 works
  3. Can validate if given maxSum works in O(n)
  4. Binary search finds minimum valid answer
```

**The Optimal Strategy**:
```
Key observations:
  1. Binary search on the answer (maximum sum)
  2. For each candidate, use greedy validation
  3. If valid, try smaller; if invalid, try larger
  4. Converge to minimum valid maximum sum
```

### Step-by-Step Algorithm

---

#### **Approach 1: Binary Search on Answer Space - OPTIMAL**

**Core Idea**:
- Binary search on possible maximum sum: [max(nums), sum(nums)]
- For each candidate, check if we can split into ≤ k subarrays
- Greedy validation: pack elements into subarrays greedily

**Algorithm**
```
splitArray(nums, k):
    left = max(nums)     // Minimum possible max sum
    right = sum(nums)    // Maximum possible max sum
    
    while left < right:
        mid = left + (right - left) / 2
        
        if canSplit(nums, k, mid):
            // mid works! Try smaller
            right = mid
        else:
            // mid doesn't work, need larger
            left = mid + 1
    
    return left

canSplit(nums, k, maxSum):
    subarrays = 1
    currentSum = 0
    
    for num in nums:
        if currentSum + num > maxSum:
            // Start new subarray
            subarrays++
            currentSum = num
            
            if subarrays > k:
                return false
        else:
            currentSum += num
    
    return true
```

**Code Implementation**
```java
class Solution {
    public int splitArray(int[] nums, int k) {
        // Binary search bounds
        int left = 0;
        int right = 0;
        
        for (int num : nums) {
            left = Math.max(left, num);  // Max element
            right += num;                 // Sum of all elements
        }
        
        // Binary search on answer
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (canSplit(nums, k, mid)) {
                // mid works, try smaller
                right = mid;
            } else {
                // mid doesn't work, need larger
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    private boolean canSplit(int[] nums, int k, int maxSum) {
        int subarrays = 1;
        int currentSum = 0;
        
        for (int num : nums) {
            if (currentSum + num > maxSum) {
                // Start new subarray
                subarrays++;
                currentSum = num;
                
                // Early termination: too many subarrays
                if (subarrays > k) {
                    return false;
                }
            } else {
                currentSum += num;
            }
        }
        
        return true;
    }
}
```

**Example Walkthrough**

Input: `nums = [2,4,10,1,5]`, `k = 2`

**Initialize:**
```
left = max(2,4,10,1,5) = 10
right = 2+4+10+1+5 = 22
```

**Iteration 1:**
```
left=10, right=22, mid=16

canSplit(nums, 2, 16)?
  Start: subarrays=1, currentSum=0
  
  Process 2: 0+2=2 ≤ 16, currentSum=2
  Process 4: 2+4=6 ≤ 16, currentSum=6
  Process 10: 6+10=16 ≤ 16, currentSum=16
  Process 1: 16+1=17 > 16
    New subarray! subarrays=2, currentSum=1
  Process 5: 1+5=6 ≤ 16, currentSum=6
  
  subarrays=2 ≤ k=2? YES ✓

16 works! Try smaller: right = 16
```

**Iteration 2:**
```
left=10, right=16, mid=13

canSplit(nums, 2, 13)?
  Start: subarrays=1, currentSum=0
  
  Process 2: 0+2=2 ≤ 13, currentSum=2
  Process 4: 2+4=6 ≤ 13, currentSum=6
  Process 10: 6+10=16 > 13
    New subarray! subarrays=2, currentSum=10
  Process 1: 10+1=11 ≤ 13, currentSum=11
  Process 5: 11+5=16 > 13
    New subarray! subarrays=3, currentSum=5
  
  subarrays=3 > k=2? NO ✗

13 doesn't work! Try larger: left = 14
```

**Iteration 3:**
```
left=14, right=16, mid=15

canSplit(nums, 2, 15)?
  Same as 13: needs 3 subarrays
  NO ✗

left = 16
```

**End:**
```
left=16, right=16
Condition: 16 < 16? No

Return 16 ✓
```

**Complexity Analysis**
- **Time**: O(n * log(sum)) — Binary search O(log sum), each validation O(n)
- **Space**: O(1) — Only constant variables

---

#### **Approach 2: Dynamic Programming - ALTERNATIVE**

**Core Idea**: DP[i][j] = min largest sum to split nums[0..i] into j subarrays.

**Algorithm**
```
dp[i][j] = min largest sum to split first i elements into j subarrays

Base case:
  dp[i][1] = sum(nums[0..i]) for all i

Transition:
  dp[i][j] = min over all p < i:
    max(dp[p][j-1], sum(nums[p+1..i]))
    
  Meaning: split at position p
    First p elements into j-1 subarrays
    Elements p+1 to i in the j-th subarray
```

**Code Implementation**
```java
class Solution {
    public int splitArray(int[] nums, int k) {
        int n = nums.length;
        
        // Prefix sums for range sum queries
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        
        // dp[i][j] = min largest sum for first i elements in j subarrays
        int[][] dp = new int[n + 1][k + 1];
        
        // Initialize with large values
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        
        // Base case: 0 elements in 0 subarrays
        dp[0][0] = 0;
        
        // Fill DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(i, k); j++) {
                // Try all split positions
                for (int p = j - 1; p < i; p++) {
                    int maxSum = Math.max(
                        dp[p][j - 1],
                        prefixSum[i] - prefixSum[p]
                    );
                    dp[i][j] = Math.min(dp[i][j], maxSum);
                }
            }
        }
        
        return dp[n][k];
    }
}
```

**Key Difference**: 
- DP is O(n² * k) time, O(n * k) space
- Binary search is O(n * log(sum)) time, O(1) space
- DP more complex, slower for most inputs

**Complexity Analysis**
- **Time**: O(n² * k)
- **Space**: O(n * k)

---

#### **Approach 3: Brute Force - TOO SLOW**

**Core Idea**: Try all possible splits recursively.

**Why It's Too Slow**:
```
Exponential time: O(k^n)
For n=20, k=5: ~95 trillion combinations
Completely intractable!
```

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Code Lines | Recommended |
|----------|------|-------|------------|-------------|
| **Binary Search on Answer** | **O(n log sum)** | **O(1)** | **~30** | **Yes ✅** |
| Dynamic Programming | O(n² * k) | O(n * k) | ~40 | Alternative |
| Brute Force | O(k^n) | O(n) | Complex | Too slow ❌ |

**Winner**: **Binary search** — optimal time and space!

### Why Binary Search on Answer Space

```
Problem asks for minimum of maximum
This is classic "binary search on answer" pattern!

Answer is in range [max(nums), sum(nums)]
Monotonic property exists
Can validate in linear time

Perfect for binary search!
```

### Why Greedy Validation

```
For given maxSum, need to check if we can split into ≤ k subarrays

Greedy approach: pack as much as possible into each subarray
  Minimizes number of subarrays needed
  If greedy can't do it, no other method can
  
Optimal for validation!
```

### Why left < right Not left <= right

```
Loop condition: left < right

This is for finding minimum valid value
When left == right, we've found it
No need to check again

Classic binary search pattern for finding minimum.
```

### Why right = mid Not right = mid - 1

```
When mid works:
  mid might be the answer
  Don't exclude it!
  right = mid (keep mid in search space)

When mid doesn't work:
  mid definitely not the answer
  left = mid + 1 (exclude mid)

Asymmetric updates for finding minimum!
```

### Why Start with max(nums) Not 0

```
Lower bound = max(nums):
  At least one subarray must contain the largest element
  That subarray's sum ≥ max(nums)
  
  No point searching below max(nums)!

Tighter bounds = fewer iterations!
```

### Why DP is Slower

```
Binary search:
  O(n * log(sum))
  For n=1000, sum=10^9: ~1000 * 30 = 30,000 ops

DP:
  O(n² * k)
  For n=1000, k=50: ~50,000,000 ops
  
Binary search much faster!

Also, binary search uses O(1) space vs O(n*k).
```

---

## Critical Edge Cases & Gotchas

### 1. **k = 1 (Single Subarray)**
```java
Input: nums = [1,2,3,4,5], k = 1
Output: 15
All elements in one subarray
Sum = 15
```

### 2. **k = n (Each Element Separate)**
```java
Input: nums = [1,2,3,4,5], k = 5
Output: 5
Each element in its own subarray
Largest = max element = 5
```

### 3. **Single Element Array**
```java
Input: nums = [10], k = 1
Output: 10
Only one element, one subarray
```

### 4. **All Elements Same**
```java
Input: nums = [5,5,5,5], k = 2
Output: 10
Best split: [5,5] and [5,5]
Each sum = 10
```

### 5. **Large Elements**
```java
Input: nums = [1000000, 1000000], k = 2
Output: 1000000
Each in its own subarray
Must handle large sums!
```

### 6. **Array with Zeros**
```java
Input: nums = [0,0,0,1], k = 2
Output: 1
Best: [0,0,0] and [1]
Sums: 0 and 1
Largest: 1
```

### 7. **k Equals Array Length**
```java
Input: nums = [7,2,5,10,8], k = 5
Output: 10
Each element separate
Largest = 10
```

### 8. **Minimum Elements**
```java
Input: nums = [1,2], k = 2
Output: 2
Split: [1] and [2]
Largest = 2
```

### 9. **Maximum Sum Equals Minimum Sum**
```java
Input: nums = [10], k = 1
Lower bound = 10
Upper bound = 10
No search needed!
```

### 10. **Large k, Small Array**
```java
Input: nums = [1,2,3], k = 10
k > n, but problem states k ≤ n
If allowed, answer = max(nums)
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Wrong Loop Condition (left <= right)**
```java
// WRONG - uses <= instead of <
while (left <= right) {
    int mid = left + (right - left) / 2;
    if (canSplit(nums, k, mid)) {
        right = mid;  // Infinite loop possible!
    }
}
```

**Why wrong**: Infinite loop when left == right!

**Dry run failure:**
```
left=16, right=16
Condition: 16 <= 16? Yes
mid = 16

canSplit returns true
right = mid = 16 (unchanged!)

Infinite loop! ❌
```

**Fix**: Use left < right
```java
while (left < right) {
    // ...
}
```

### ❌ **MISTAKE 2: Wrong Pointer Update (right = mid - 1)**
```java
// WRONG - excludes mid when it might be answer
if (canSplit(nums, k, mid)) {
    right = mid - 1;  // Should be mid
}
```

**Why wrong**: Might exclude the actual answer!

**Dry run failure for nums=[2,4,10,1,5], k=2:**
```
left=10, right=22, mid=16

canSplit(16) = true
right = mid - 1 = 15

Next: left=10, right=15, mid=12
canSplit(12) = false
left = 13

...eventually return 15

But answer is 16! ❌
We excluded it!
```

**Fix**: Keep mid when it works
```java
right = mid;
```

### ❌ **MISTAKE 3: Starting currentSum at num Instead of 0**
```java
// WRONG - in validation function
if (currentSum + num > maxSum) {
    subarrays++;
    currentSum = 0;  // Should be num!
}
```

**Why wrong**: Loses the current element!

**Dry run failure:**
```
nums = [2,4,10], maxSum = 13

Process 2: currentSum=2
Process 4: currentSum=6
Process 10: 6+10=16 > 13
  subarrays++
  currentSum = 0  (forgot about 10!)

Element 10 is lost! ❌
```

**Fix**: Start new subarray with current element
```java
currentSum = num;
```

### ❌ **MISTAKE 4: Not Checking Element > maxSum**
```java
// INCOMPLETE - doesn't handle impossible case
for (int num : nums) {
    if (currentSum + num > maxSum) {
        subarrays++;
        currentSum = num;
    }
    // What if num > maxSum itself?
}
```

**Why incomplete**: If single element > maxSum, impossible!

**Actually**: Our algorithm handles this correctly:
```
left = max(nums) ensures maxSum ≥ any element
So num > maxSum never happens in valid search space!
```

**No fix needed**: Starting left at max(nums) prevents this!

### ❌ **MISTAKE 5: Wrong Calculation of left Bound**
```java
// WRONG - starts at 0
int left = 0;  // Should be max(nums)
```

**Why wrong**: Wastes iterations!

**Dry run:**
```
nums = [10,20,30]

With left=0:
  Search space: [0, 60]
  Many iterations checking impossible values
  0, 1, 2, ..., 29 all fail!

With left=max(nums)=30:
  Search space: [30, 60]
  Skips impossible values
  Fewer iterations ✓
```

**Fix**: Start at max(nums)
```java
int left = Integer.MIN_VALUE;
for (int num : nums) {
    left = Math.max(left, num);
}
```

### ❌ **MISTAKE 6: Integer Overflow in Sum**
```java
// WRONG - might overflow
int right = 0;
for (int num : nums) {
    right += num;
}
```

**Why risky**: Sum might exceed Integer.MAX_VALUE!

**With constraints:**
```
nums.length ≤ 1000
nums[i] ≤ 1,000,000

Max sum: 1000 * 1,000,000 = 1,000,000,000
< Integer.MAX_VALUE (2,147,483,647)

Actually safe! ✓
```

**But if constraints were larger**: Use long!
```java
long right = 0;
```

### ❌ **MISTAKE 7: Not Initializing subarrays to 1**
```java
// WRONG - starts at 0
int subarrays = 0;  // Should be 1

for (int num : nums) {
    if (currentSum + num > maxSum) {
        subarrays++;
        currentSum = num;
    } else {
        currentSum += num;
    }
}
```

**Why wrong**: Off by one!

**Dry run failure:**
```
nums = [10], maxSum = 10

Process 10: 0+10=10 ≤ 10
  currentSum = 10

End: subarrays = 0 ❌

Should be 1 (one subarray)!
```

**Fix**: Start at 1
```java
int subarrays = 1;
```

---

## Complexity Analysis

### Time Complexity: **O(n * log(sum))**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **Calculate bounds** | 1 | O(n) | O(n) |
| **Binary search iterations** | O(log sum) | - | - |
| **Validation per iteration** | O(log sum) | O(n) | O(n log sum) |
| **Total** | - | - | **O(n log sum)** |

**Time analysis**:
```
Binary search range: [max(nums), sum(nums)]
  Maximum iterations: log(sum(nums) - max(nums))
  Upper bound: log(sum(nums))
  
Each validation: O(n) linear scan

Total: O(n * log(sum))

Examples:
  n=1000, sum=1,000,000:
    log(1,000,000) ≈ 20
    Total: 1000 * 20 = 20,000 operations ✓
    
  n=1000, sum=1,000,000,000:
    log(1,000,000,000) ≈ 30
    Total: 1000 * 30 = 30,000 operations ✓

Very efficient!
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| left, right | O(1) | Two integers |
| mid | O(1) | One integer |
| Validation variables | O(1) | subarrays, currentSum |
| **Total** | **O(1)** | Constant space |

**Space analysis**:
```
Only integer variables
No arrays, no recursion stack
Space: O(1) ✓

Optimal space usage!
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `nums = [2,4,10,1,5]`, `k = 2`

**Expected Output:** `16`

---

**Initialize Bounds:**
```
Array: [2, 4, 10, 1, 5]

left = max(2, 4, 10, 1, 5) = 10
  (Largest element - minimum possible max sum)

right = 2 + 4 + 10 + 1 + 5 = 22
  (Total sum - maximum possible max sum)

Search space: [10, 22]
```

---

**Binary Search Iteration 1:**
```
left = 10, right = 22
mid = 10 + (22-10)/2 = 16

Validate: Can split with maxSum = 16?

Greedy simulation:
  Subarray 1: []
  Add 2: [2], sum=2 ≤ 16 ✓
  Add 4: [2,4], sum=6 ≤ 16 ✓
  Add 10: [2,4,10], sum=16 ≤ 16 ✓
  Add 1: sum would be 17 > 16 ✗
    
  Subarray 2: []
  Add 1: [1], sum=1 ≤ 16 ✓
  Add 5: [1,5], sum=6 ≤ 16 ✓
  
  Total subarrays: 2
  2 ≤ k=2? YES ✓

16 works! Try smaller: right = 16
```

---

**Binary Search Iteration 2:**
```
left = 10, right = 16
mid = 10 + (16-10)/2 = 13

Validate: Can split with maxSum = 13?

Greedy simulation:
  Subarray 1: []
  Add 2: [2], sum=2 ≤ 13 ✓
  Add 4: [2,4], sum=6 ≤ 13 ✓
  Add 10: sum would be 16 > 13 ✗
    
  Subarray 2: []
  Add 10: [10], sum=10 ≤ 13 ✓
  Add 1: sum would be 11 ≤ 13 ✓
  Add 5: sum would be 16 > 13 ✗
    
  Subarray 3: []
  Add 5: [5], sum=5 ≤ 13 ✓
  
  Total subarrays: 3
  3 > k=2? NO ✗

13 doesn't work! Try larger: left = 14
```

---

**Binary Search Iteration 3:**
```
left = 14, right = 16
mid = 14 + (16-14)/2 = 15

Validate: Can split with maxSum = 15?

Greedy simulation:
  Subarray 1: []
  Add 2: [2], sum=2 ≤ 15 ✓
  Add 4: [2,4], sum=6 ≤ 15 ✓
  Add 10: sum would be 16 > 15 ✗
    
  Subarray 2: []
  Add 10: [10], sum=10 ≤ 15 ✓
  Add 1: [10,1], sum=11 ≤ 15 ✓
  Add 5: sum would be 16 > 15 ✗
    
  Subarray 3: []
  Add 5: [5], sum=5 ≤ 15 ✓
  
  Total subarrays: 3
  3 > k=2? NO ✗

15 doesn't work! Try larger: left = 16
```

---

**End:**
```
left = 16, right = 16
Condition: 16 < 16? No

Exit loop

Return: 16 ✓
```

---

**Summary:**
```
Binary search iterations: 3
Found minimum max sum: 16

Optimal split: [2,4,10] and [1,5]
  Sums: 16 and 6
  Maximum: 16 ✓
```

---

### Visualization of Monotonic Property

```
maxSum:  10   11   12   13   14   15   16   17   18   ...   22
Can?     NO   NO   NO   NO   NO   NO   YES  YES  YES  ...  YES
         ✗    ✗    ✗    ✗    ✗    ✗    ✓    ✓    ✓         ✓

Binary search finds first YES: 16 ✓

Monotonic: once YES appears, all after are YES
This enables binary search!
```

---

### Decision Tree

```
                    [10, 22]
                    mid=16 ✓
                   /        \
            [10,16]          [17,22]
            mid=13 ✗      (not explored)
           /      \
        [14,16]   [10,12]
        mid=15 ✗  (not explored)
       /      \
    [16,16]   [14,14]
    Found!    (not explored)
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Lines | Clarity | Recommended |
|----------|------|-------|------------|---------|-------------|
| **Binary Search** | **O(n log sum)** | **O(1)** | **~30** | **Excellent ✅** | **Yes ✅** |
| Dynamic Programming | O(n² * k) | O(n * k) | ~40 | Moderate | Alternative |
| Brute Force | O(k^n) | O(n) | Complex | Poor | Too slow ❌ |

**Winner**: **Binary search** — optimal time and space!

---

## Key Takeaways

1. **Binary search on answer space** — minimize maximum pattern
2. **Search range**: [max(nums), sum(nums)]
3. **Monotonic property** — if X works, X+1 works
4. **Greedy validation** — pack elements greedily into subarrays
5. **Loop condition: left < right** — for finding minimum
6. **Asymmetric updates** — right = mid, left = mid + 1
7. **Start subarrays at 1** — first subarray already exists
8. **Reset currentSum to num** — when starting new subarray
9. **Early termination** — return false when subarrays > k
10. **O(n log sum) time, O(1) space** — optimal solution

---

## Interview Tips

**What to say in an interview:**

> "This is a minimize maximum problem, which is a classic binary search on answer space pattern. I need to find the minimum possible value of the largest subarray sum when splitting the array into k subarrays. The answer must be between max(nums) - since at least one subarray contains the largest element - and sum(nums) - if all elements are in one subarray. There's a monotonic property: if I can split the array with maximum sum X into k or fewer subarrays, then I can definitely do it with any value larger than X. So I'll binary search on this range. For each candidate maximum sum, I'll validate if it's achievable using a greedy approach: I'll pack as many elements as possible into each subarray without exceeding the maximum sum. This greedy approach minimizes the number of subarrays needed, so if it uses more than k subarrays, that maximum sum is too small. When I find a valid maximum sum, I'll search for smaller values; when invalid, I'll search for larger values. The binary search converges to the minimum valid maximum sum. This runs in O(n log sum) time with O(1) space, which is optimal for this problem."

**Key points to mention:**
1. **Minimize maximum problem** — binary search on answer
2. **Search range** — [max(nums), sum(nums)]
3. **Monotonic property** — larger maxSum → easier
4. **Greedy validation** — pack elements greedily
5. **Loop left < right** — finding minimum value
6. **Asymmetric updates** — keep mid when valid
7. **O(n log sum) time** — efficient for constraints
8. **O(1) space** — no extra data structures

**Common Follow-ups:**
- "Why not DP?" → DP is O(n² k), slower and more space
- "Why greedy validation works?" → Greedy minimizes subarrays for given maxSum
- "Can you optimize further?" → Already optimal time and space
- "What if k > n?" → Each element in own subarray, answer = max(nums)
- "Handle negative numbers?" → Problem states 0 ≤ nums[i], but same logic works

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Split Array Largest Sum** | Hard | **Binary Search on Answer** | **This problem** |
| Capacity to Ship Packages Within D Days | Medium | Binary Search on Answer | Similar minimize maximum |
| Koko Eating Bananas | Medium | Binary Search on Answer | Similar monotonic property |
| Minimize Maximum of Array | Medium | Binary Search / Greedy | Similar pattern |
| Allocate Mailboxes | Hard | Dynamic Programming | Different optimization |
| Divide Chocolate | Hard | Binary Search on Answer | Maximize minimum variant |
| Cutting Ribbons | Medium | Binary Search on Answer | Similar validation |
| Magnetic Force Between Balls | Medium | Binary Search on Answer | Maximize minimum variant |

**Pattern Progression**:
1. **Koko Eating Bananas** — Intro to binary search on answer
2. **Ship Packages** — Similar with different validation
3. **Split Array Largest Sum** (this) — Minimize maximum, harder
4. **Advanced variants** — Different constraints and optimizations

---

## Final Pattern Label

✅ **Binary Search on Answer Space (Minimize Maximum with Greedy Validation)**

**Remember:** This is **binary search on answer space** to **minimize the maximum** (largest subarray sum). **Search range** is [max(nums), sum(nums)]. The problem has **monotonic property**: if maxSum = X works, then X+1, X+2, ... all work. Use **greedy validation**: pack elements into subarrays greedily without exceeding maxSum, count subarrays needed. If count ≤ k, that maxSum is **valid**; try smaller with `right = mid`. If count > k, maxSum is **too small**; try larger with `left = mid + 1`. Use **left < right** loop for finding minimum. **Start with subarrays = 1** (first subarray exists). When element exceeds maxSum, **start new subarray with currentSum = num** (don't lose the element!). Similar pattern to **Koko Eating Bananas** and **Ship Packages** but with minimize maximum goal. Achieves **O(n log sum) time** with **O(1) space** - optimal solution! Key insight: binary search on **what we're minimizing** (the maximum sum), not on array indices!
