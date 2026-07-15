# Median of Two Sorted Arrays

## Problem Description

**Difficulty**: Hard

You are given two integer arrays `nums1` and `nums2` of size `m` and `n` respectively, where each is **sorted in ascending order**. Return the **median value** among all elements of the two arrays.

Your solution must run in **O(log(m+n))** time.

## Examples

### Example 1:
```
Input: nums1 = [1,2], nums2 = [3]
Output: 2.0

Explanation:
  Merged array: [1, 2, 3]
  Total length: 3 (odd)
  Median: middle element = 2
```

### Example 2:
```
Input: nums1 = [1,3], nums2 = [2,4]
Output: 2.5

Explanation:
  Merged array: [1, 2, 3, 4]
  Total length: 4 (even)
  Median: (2 + 3) / 2 = 2.5
```

### Example 3:
```
Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.5

Explanation:
  Merged array: [1, 2, 3, 4]
  Median: (2 + 3) / 2 = 2.5
```

### Example 4:
```
Input: nums1 = [], nums2 = [1]
Output: 1.0

Explanation:
  One array empty
  Median is middle of non-empty array
```

### Example 5:
```
Input: nums1 = [2], nums2 = []
Output: 2.0

Explanation:
  One array empty
```

### Example 6:
```
Input: nums1 = [1,2,3], nums2 = [4,5,6,7,8]
Output: 4.0

Explanation:
  Merged: [1, 2, 3, 4, 5, 6, 7, 8]
  Length: 8 (even)
  Median: (4 + 5) / 2 = 4.5
  
  Wait, let me recalculate:
  Middle two: indices 3 and 4
  Values: 4 and 5
  Median: (4 + 5) / 2 = 4.5
```

### Example 7:
```
Input: nums1 = [1,3,5], nums2 = [2,4,6]
Output: 3.5

Explanation:
  Merged: [1, 2, 3, 4, 5, 6]
  Length: 6 (even)
  Median: (3 + 4) / 2 = 3.5
```

### Example 8:
```
Input: nums1 = [1], nums2 = [2,3,4,5,6]
Output: 3.5

Explanation:
  Merged: [1, 2, 3, 4, 5, 6]
  Median: (3 + 4) / 2 = 3.5
```

### Example 9:
```
Input: nums1 = [100001], nums2 = [100000]
Output: 100000.5

Explanation:
  Merged: [100000, 100001]
  Median: (100000 + 100001) / 2 = 100000.5
```

### Example 10:
```
Input: nums1 = [-5,-3,-1], nums2 = [-4,-2,0]
Output: -2.5

Explanation:
  Merged: [-5, -4, -3, -2, -1, 0]
  Median: (-3 + -2) / 2 = -2.5
```

### Example 11:
```
Input: nums1 = [1,2], nums2 = [1,2]
Output: 1.5

Explanation:
  Merged: [1, 1, 2, 2]
  Median: (1 + 2) / 2 = 1.5
  Duplicates allowed
```

### Example 12:
```
Input: nums1 = [3], nums2 = [-2,-1]
Output: -1.0

Explanation:
  Merged: [-2, -1, 3]
  Length: 3 (odd)
  Median: -1
```

## Constraints
- nums1.length == m
- nums2.length == n
- 0 <= m <= 1,000
- 0 <= n <= 1,000
- 1 <= m + n <= 2,000
- -10^6 <= nums1[i], nums2[i] <= 10^6

**Recommended Complexity**: O(log(min(m, n))) time and O(1) space

---

## Pattern Recognition

**Primary Pattern**: **Binary Search on Partition (Merge Two Sorted Arrays Median)**

**Why This Pattern?**
- Need **O(log(m+n))** time (suggests binary search)
- Two **sorted arrays** (can leverage sorted property)
- Finding **median** = finding partition point
- Don't need to merge fully, just find correct partition

**Key Insight**: Partition Both Arrays, Not Merge
```
Instead of merging and finding median:
  Merge: O(m + n) time ❌
  Find median: O(1)
  Total: O(m + n) ❌

Better approach:
  Partition both arrays such that:
    - Left partition has (m+n+1)/2 elements
    - All elements in left ≤ all elements in right
  
  Then median is at partition boundary!
  Time: O(log(min(m, n))) ✓
```

**The Partition Concept**:
```
Two arrays:
  A = [1, 3, 5, 7, 9]
  B = [2, 4, 6, 8, 10, 12]

Merged (conceptually):
  [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12]
   ←  left half  →|← right half →
                  ↑ partition (median point)

Partition strategy:
  Partition A: [1, 3, 5 | 7, 9]
              ←left→ ←right→
              
  Partition B: [2, 4 | 6, 8, 10, 12]
              ←left→ ←right→
  
  Left partition: [1, 3, 5] from A, [2, 4] from B
    Total: 5 elements
    
  Right partition: [7, 9] from A, [6, 8, 10, 12] from B
    Total: 6 elements
    
  For valid partition:
    max(left A) ≤ min(right B)  (5 ≤ 6) ✓
    max(left B) ≤ min(right A)  (4 ≤ 7) ✓
    
  Valid! Median = (max(left) + min(right)) / 2
                = (5 + 6) / 2 = 5.5
```

**Why Binary Search on Smaller Array**:
```
Search space = partition positions in one array

If we search on array A (length m):
  Partition A at index i: [A[0..i-1] | A[i..m-1]]
  Must partition B at index j = (m+n+1)/2 - i
  
  For each i, calculate j and check validity
  
Search on smaller array for efficiency:
  If m < n: search on A, O(log m) ✓
  If n < m: search on B, O(log n) ✓
  
  Result: O(log(min(m, n))) ✓
```

**Valid Partition Condition**:
```
Partition is valid when:

  A_left_max ≤ B_right_min  AND  B_left_max ≤ A_right_min
  
Where:
  A_left_max = A[partitionA - 1]    (largest in left A)
  A_right_min = A[partitionA]       (smallest in right A)
  B_left_max = B[partitionB - 1]    (largest in left B)
  B_right_min = B[partitionB]       (smallest in right B)

Example validation:
  A = [1, 3, 5, 7], B = [2, 4, 6, 8]
  partitionA = 2, partitionB = 2
  
  A_left_max = A[1] = 3
  A_right_min = A[2] = 5
  B_left_max = B[1] = 4
  B_right_min = B[2] = 6
  
  Check: 3 ≤ 6? Yes, 4 ≤ 5? Yes
  Valid partition! ✓
```

**Binary Search Strategy**:
```
If partition is invalid, how to adjust?

Case 1: A_left_max > B_right_min
  Example: A[i-1] = 7, B[j] = 5
  
  Left A has too large element!
  Need fewer elements from A in left partition
  Search left: right = partitionA - 1

Case 2: B_left_max > A_right_min
  Example: B[j-1] = 8, A[i] = 6
  
  Left B has too large element!
  Need more elements from A in left partition
  Search right: left = partitionA + 1
```

**Example Showing Binary Search**:
```
A = [1, 3, 5], B = [2, 4, 6, 8]
Total length = 7 (odd)
Need left partition size = (7+1)/2 = 4

Binary search on A:

Step 1: left=0, right=3, partitionA=1
  partitionB = 4 - 1 = 3
  
  A partitioned: [1 | 3, 5]
  B partitioned: [2, 4, 6 | 8]
  
  A_left_max = 1, A_right_min = 3
  B_left_max = 6, B_right_min = 8
  
  Check: 1 ≤ 8? Yes, 6 ≤ 3? No ✗
  
  B_left_max > A_right_min
  Need more from A: left = 2

Step 2: left=2, right=3, partitionA=2
  partitionB = 4 - 2 = 2
  
  A partitioned: [1, 3 | 5]
  B partitioned: [2, 4 | 6, 8]
  
  A_left_max = 3, A_right_min = 5
  B_left_max = 4, B_right_min = 6
  
  Check: 3 ≤ 6? Yes, 4 ≤ 5? Yes ✓
  
  Valid! Median = max(3, 4) = 4 (odd length)
```

**Computing Median from Partition**:
```
Once valid partition found:

If total length is ODD:
  Median = max(A_left_max, B_left_max)
  (The largest element in left partition)

If total length is EVEN:
  Median = (max(A_left_max, B_left_max) + min(A_right_min, B_right_min)) / 2.0
  (Average of largest left and smallest right)

Example (even):
  A_left_max = 3, B_left_max = 4
  A_right_min = 5, B_right_min = 6
  Median = (max(3, 4) + min(5, 6)) / 2 = (4 + 5) / 2 = 4.5 ✓
```

**Why This is Optimal**:
```
Merge and find: O(m + n) ❌

Binary search on partition: O(log(min(m, n))) ✓

For m=1000, n=1000:
  Merge: 2,000 operations
  Binary: ~10 operations
  
200× faster!

This is the only approach that achieves required time complexity.
```

**Related Patterns**:
1. **Binary Search** — Core technique
2. **Partition Problem** — Divide into two halves
3. **Median Finding** — Statistical property
4. **Two Arrays** — Coordinated search

---

## Algorithm & Approach

### Core Insight

**Why Binary Search on Partition Works:**
```
Key properties:
  1. Both arrays sorted
  2. Median = element at middle position
  3. Can partition arrays to find middle without merging
  4. Binary search finds correct partition in log time
```

**The Optimal Strategy**:
```
Key observations:
  1. Always search on smaller array (efficiency)
  2. Partition one array → other partition determined
  3. Check if partition valid (left ≤ right)
  4. Adjust partition based on comparison
  5. Compute median from partition boundaries
```

### Step-by-Step Algorithm

---

#### **Approach 1: Binary Search on Partition - OPTIMAL**

**Core Idea**:
- Binary search on partition index of smaller array
- Other array's partition determined by size constraint
- Validate partition: all left ≤ all right
- Compute median from partition boundaries

**Algorithm**
```
findMedianSortedArrays(nums1, nums2):
    // Ensure nums1 is smaller
    if len(nums1) > len(nums2):
        swap(nums1, nums2)
    
    m = len(nums1)
    n = len(nums2)
    
    left = 0
    right = m
    halfLen = (m + n + 1) / 2
    
    while left <= right:
        partitionA = (left + right) / 2
        partitionB = halfLen - partitionA
        
        // Get boundary elements
        maxLeftA = (partitionA == 0) ? -∞ : nums1[partitionA - 1]
        minRightA = (partitionA == m) ? +∞ : nums1[partitionA]
        maxLeftB = (partitionB == 0) ? -∞ : nums2[partitionB - 1]
        minRightB = (partitionB == n) ? +∞ : nums2[partitionB]
        
        // Check if partition is valid
        if maxLeftA <= minRightB AND maxLeftB <= minRightA:
            // Valid partition found!
            if (m + n) % 2 == 1:
                // Odd total length
                return max(maxLeftA, maxLeftB)
            else:
                // Even total length
                return (max(maxLeftA, maxLeftB) + min(minRightA, minRightB)) / 2.0
        
        else if maxLeftA > minRightB:
            // Too many elements from A on left
            right = partitionA - 1
        
        else:
            // Too few elements from A on left
            left = partitionA + 1
```

**Code Implementation**
```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int m = nums1.length;
        int n = nums2.length;
        
        int left = 0;
        int right = m;
        int halfLen = (m + n + 1) / 2;
        
        while (left <= right) {
            int partitionA = (left + right) / 2;
            int partitionB = halfLen - partitionA;
            
            // Get max of left partition and min of right partition
            int maxLeftA = (partitionA == 0) ? Integer.MIN_VALUE : nums1[partitionA - 1];
            int minRightA = (partitionA == m) ? Integer.MAX_VALUE : nums1[partitionA];
            int maxLeftB = (partitionB == 0) ? Integer.MIN_VALUE : nums2[partitionB - 1];
            int minRightB = (partitionB == n) ? Integer.MAX_VALUE : nums2[partitionB];
            
            // Check if partition is valid
            if (maxLeftA <= minRightB && maxLeftB <= minRightA) {
                // Found valid partition!
                if ((m + n) % 2 == 1) {
                    // Odd total length: median is max of left partition
                    return Math.max(maxLeftA, maxLeftB);
                } else {
                    // Even total length: median is average of middle two
                    return (Math.max(maxLeftA, maxLeftB) + Math.min(minRightA, minRightB)) / 2.0;
                }
            } else if (maxLeftA > minRightB) {
                // Too many elements from A on left side
                // Move partition left in A
                right = partitionA - 1;
            } else {
                // Too few elements from A on left side
                // Move partition right in A
                left = partitionA + 1;
            }
        }
        
        // Should never reach here with valid input
        throw new IllegalArgumentException();
    }
}
```

**Example Walkthrough**

Input: `nums1 = [1,3]`, `nums2 = [2,4]`

**Initialize:**
```
m = 2, n = 2
left = 0, right = 2
halfLen = (2 + 2 + 1) / 2 = 2
```

**Iteration 1:**
```
left=0, right=2, partitionA=1
partitionB = 2 - 1 = 1

Partitions:
  nums1: [1 | 3]
  nums2: [2 | 4]

Boundary elements:
  maxLeftA = nums1[0] = 1
  minRightA = nums1[1] = 3
  maxLeftB = nums2[0] = 2
  minRightB = nums2[1] = 4

Validate:
  1 ≤ 4? Yes
  2 ≤ 3? Yes
  Valid! ✓

Total length: 4 (even)
Median = (max(1, 2) + min(3, 4)) / 2
       = (2 + 3) / 2
       = 2.5 ✓
```

**Complexity Analysis**
- **Time**: O(log(min(m, n))) — Binary search on smaller array
- **Space**: O(1) — Only constant variables

---

#### **Approach 2: Merge and Find Median - TOO SLOW**

**Core Idea**: Merge both arrays, then find median.

**Code Implementation**
```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] merged = new int[m + n];
        
        int i = 0, j = 0, k = 0;
        
        // Merge arrays
        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) {
                merged[k++] = nums1[i++];
            } else {
                merged[k++] = nums2[j++];
            }
        }
        
        while (i < m) {
            merged[k++] = nums1[i++];
        }
        
        while (j < n) {
            merged[k++] = nums2[j++];
        }
        
        // Find median
        int total = m + n;
        if (total % 2 == 1) {
            return merged[total / 2];
        } else {
            return (merged[total / 2 - 1] + merged[total / 2]) / 2.0;
        }
    }
}
```

**Key Difference**: 
- O(m + n) time ❌
- O(m + n) space ❌
- Doesn't meet time requirement

**Complexity Analysis**
- **Time**: O(m + n) ❌
- **Space**: O(m + n) ❌

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Meets Requirement | Recommended |
|----------|------|-------|-------------------|-------------|
| **Binary Search on Partition** | **O(log(min(m,n)))** | **O(1)** | **Yes ✅** | **Yes ✅** |
| Merge Arrays | O(m + n) | O(m + n) | No ❌ | Too slow |

**Winner**: **Binary search** — only approach that meets requirement!

### Why Search on Smaller Array

```
Binary search on A (length m):
  O(log m) time

Binary search on B (length n):
  O(log n) time

If m < n:
  Search on A: O(log m) ✓ (faster)
  
If n < m:
  Swap arrays, search on smaller: O(log n) ✓

Always search on smaller = O(log(min(m, n))) ✓
```

### Why Partition Size is (m+n+1)/2

```
Total elements: m + n

If odd (e.g., 7):
  Left partition: (7+1)/2 = 4 elements
  Right partition: 3 elements
  Median is largest of left ✓

If even (e.g., 8):
  Left partition: (8+1)/2 = 4 elements (integer division)
  Right partition: 4 elements
  Median is average of largest left and smallest right ✓

(m+n+1)/2 works for both cases!
```

### Why Use Integer.MIN_VALUE and MAX_VALUE

```
Edge cases for partition boundaries:

If partitionA == 0:
  No elements from A on left
  maxLeftA = -∞ (won't affect max)
  
If partitionA == m:
  No elements from A on right
  minRightA = +∞ (won't affect min)

Integer.MIN_VALUE and MAX_VALUE handle these elegantly!

Example:
  A = [1, 3], partitionA = 0
  maxLeftA = Integer.MIN_VALUE
  maxLeftB = 2
  max(maxLeftA, maxLeftB) = max(-∞, 2) = 2 ✓
```

### Why left <= right Not left < right

```
Loop condition: left <= right

Must check all partition positions including edges:
  partitionA can be 0 to m (inclusive)
  Need to check when left == right
  
Example: A = [2], B = [1, 3]
  Initially: left=0, right=1
  
  Eventually: left=1, right=1
  Need to check partitionA=1!
  
left <= right is correct!
```

### Why Return in Two Cases (Odd/Even)

```
Median definition:

Odd total length:
  Median = middle element
  = largest element in left partition
  = max(maxLeftA, maxLeftB)

Even total length:
  Median = average of middle two
  = (largest left + smallest right) / 2
  = (max(maxLeftA, maxLeftB) + min(minRightA, minRightB)) / 2

Different formulas for odd and even!
```

---

## Critical Edge Cases & Gotchas

### 1. **One Array Empty**
```java
Input: nums1 = [], nums2 = [1]
Output: 1.0
Handle with Integer.MIN_VALUE/MAX_VALUE
```

### 2. **One Array Much Larger**
```java
Input: nums1 = [1], nums2 = [2,3,4,5,6]
Output: 3.5
Binary search still O(log 1) = O(1)
```

### 3. **All Elements Same**
```java
Input: nums1 = [1,1], nums2 = [1,1]
Output: 1.0
Duplicates handled correctly
```

### 4. **Negative Numbers**
```java
Input: nums1 = [-5,-3], nums2 = [-4,-2]
Output: -3.5
Works with negative values
```

### 5. **Large Numbers**
```java
Input: nums1 = [1000000], nums2 = [1000000]
Output: 1000000.0
No overflow with proper division
```

### 6. **Partition at Boundary**
```java
Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.5
Partition might be at edge
```

### 7. **Single Element Each**
```java
Input: nums1 = [1], nums2 = [2]
Output: 1.5
Simple case but must handle correctly
```

### 8. **Odd vs Even Total**
```java
Input: nums1 = [1], nums2 = [2,3]
Output: 2.0
Odd total: return max of left
```

### 9. **Arrays Different Sizes**
```java
Input: nums1 = [1,2], nums2 = [3,4,5,6,7,8]
Output: 4.5
Always search on smaller
```

### 10. **Partition at Start/End**
```java
Input: nums1 = [3,4], nums2 = [1,2]
Output: 2.5
Valid partition at extremes
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Swapping to Search on Smaller Array**
```java
// WRONG - always searches on first array
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    // Missing swap check!
    int m = nums1.length;
    int n = nums2.length;
    // Binary search on nums1 regardless of size
}
```

**Why wrong**: If nums1 larger, O(log m) > O(log n)!

**Fix**: Always search on smaller
```java
if (nums1.length > nums2.length) {
    return findMedianSortedArrays(nums2, nums1);
}
```

### ❌ **MISTAKE 2: Wrong Partition Size Calculation**
```java
// WRONG - uses (m+n)/2
int halfLen = (m + n) / 2;  // Should be (m+n+1)/2
```

**Why wrong**: Doesn't work for odd total!

**Dry run failure for total=5:**
```
Wrong: halfLen = 5/2 = 2
  Left: 2 elements
  Right: 3 elements
  Median should be at position 2 (0-indexed)
  But we need 3 in left!

Correct: halfLen = (5+1)/2 = 3
  Left: 3 elements
  Right: 2 elements
  Median is largest of left ✓
```

**Fix**: Use (m+n+1)/2
```java
int halfLen = (m + n + 1) / 2;
```

### ❌ **MISTAKE 3: Not Handling Edge Partitions**
```java
// WRONG - doesn't handle partitionA == 0
int maxLeftA = nums1[partitionA - 1];  // ArrayIndexOutOfBounds!
```

**Why wrong**: When partitionA = 0, index -1 is invalid!

**Fix**: Use boundary values
```java
int maxLeftA = (partitionA == 0) ? Integer.MIN_VALUE : nums1[partitionA - 1];
```

### ❌ **MISTAKE 4: Wrong Validation Condition**
```java
// WRONG - only checks one direction
if (maxLeftA <= minRightB) {
    // Found partition (missing second check!)
}
```

**Why wrong**: Must check BOTH cross-comparisons!

**Fix**: Check both
```java
if (maxLeftA <= minRightB && maxLeftB <= minRightA) {
    // Valid partition
}
```

### ❌ **MISTAKE 5: Integer Division for Median**
```java
// WRONG - integer division
return (max + min) / 2;  // Should be / 2.0
```

**Why wrong**: Loses decimal part!

**Dry run failure:**
```
max = 2, min = 3
Result: (2 + 3) / 2 = 5 / 2 = 2 (integer)
Should be: 2.5 ✗
```

**Fix**: Use floating-point division
```java
return (max + min) / 2.0;
```

### ❌ **MISTAKE 6: Wrong Pointer Updates**
```java
// WRONG - doesn't exclude current partition
if (maxLeftA > minRightB) {
    right = partitionA;  // Should be partitionA - 1
}
```

**Why wrong**: Might not converge or infinite loop!

**Fix**: Exclude current partition
```java
right = partitionA - 1;
```

### ❌ **MISTAKE 7: Using left < right**
```java
// WRONG - might miss valid partition at boundary
while (left < right) {
    // ...
}
```

**Why wrong**: When left == right, need to check that partition!

**Fix**: Use <=
```java
while (left <= right) {
    // ...
}
```

---

## Complexity Analysis

### Time Complexity: **O(log(min(m, n)))**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **Swap check** | 1 | O(1) | O(1) |
| **Binary search iterations** | O(log min) | O(1) | O(log min) |
| **Partition calculation** | O(log min) | O(1) | O(log min) |
| **Validation check** | O(log min) | O(1) | O(log min) |
| **Total** | - | - | **O(log(min(m,n)))** |

**Time analysis**:
```
Binary search on smaller array:
  Search space: min(m, n)
  Iterations: log(min(m, n))
  
Each iteration: O(1) operations
  Calculate partitions
  Get boundary elements
  Compare

Total: O(log(min(m, n))) ✓

Examples:
  m=1000, n=1000: log(1000) ≈ 10 iterations
  m=100, n=1000: log(100) ≈ 7 iterations
  m=1, n=1000: log(1) = 0 iterations (instant)

Very efficient!
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| left, right | O(1) | Two integers |
| partitionA, partitionB | O(1) | Two integers |
| Boundary variables | O(1) | Four integers |
| **Total** | **O(1)** | Constant space |

**Space analysis**:
```
Only integer variables
No arrays created
No recursion stack (iterative)
Space: O(1) ✓

Optimal space usage!
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `nums1 = [1,3,5]`, `nums2 = [2,4,6,8]`

**Expected Output:** `4.0`

---

**Initialize:**
```
nums1 is smaller (length 3 < 4)
m = 3, n = 4
left = 0, right = 3
halfLen = (3 + 4 + 1) / 2 = 4
```

---

**Iteration 1:**
```
left=0, right=3, partitionA=1
partitionB = 4 - 1 = 3

Partitions:
  nums1: [1 | 3, 5]
        ↑left↑ ↑right↑
        
  nums2: [2, 4, 6 | 8]
        ↑←left→↑ ↑right↑

Boundary elements:
  maxLeftA = nums1[0] = 1
  minRightA = nums1[1] = 3
  maxLeftB = nums2[2] = 6
  minRightB = nums2[3] = 8

Validate:
  1 ≤ 8? Yes ✓
  6 ≤ 3? No ✗
  
  maxLeftB > minRightA
  Need more from A in left partition
  
Action: left = partitionA + 1 = 2
```

---

**Iteration 2:**
```
left=2, right=3, partitionA=2
partitionB = 4 - 2 = 2

Partitions:
  nums1: [1, 3 | 5]
        ↑left→↑ ↑right↑
        
  nums2: [2, 4 | 6, 8]
        ↑left↑ ↑right→↑

Boundary elements:
  maxLeftA = nums1[1] = 3
  minRightA = nums1[2] = 5
  maxLeftB = nums2[1] = 4
  minRightB = nums2[2] = 6

Validate:
  3 ≤ 6? Yes ✓
  4 ≤ 5? Yes ✓
  
  Valid partition! ✓

Total length: 7 (odd)
Median = max(maxLeftA, maxLeftB)
       = max(3, 4)
       = 4 ✓
```

---

**Summary:**
```
Binary search iterations: 2
Found median: 4.0

Merged conceptually: [1, 2, 3, 4, 5, 6, 8]
                              ↑ median (middle of 7 elements)

Partition divides at index 4 (0-indexed)
```

---

### Visualization of Partition

```
nums1 = [1, 3, 5, 7, 9]
nums2 = [2, 4, 6, 8]

Valid partition:

nums1: [1, 3, 5 | 7, 9]
nums2: [2, 4 | 6, 8]

Left partition (total 5):
  From nums1: [1, 3, 5]
  From nums2: [2, 4]
  Max left: 5

Right partition (total 4):
  From nums1: [7, 9]
  From nums2: [6, 8]
  Min right: 6

Validation:
  All left ≤ all right? Yes (5 ≤ 6)
  
Total: 9 (odd)
Median: 5 ✓
```

---

### Decision Tree

```
nums1 = [1,3,5], nums2 = [2,4,6,8]

            partitionA=1 (invalid)
            6 > 3, need more from A
           /                    \
    partition=2 (valid)      (not explored)
        Found!
      Return 4.0
```

---

## Comparison of Approaches

| Approach | Time | Space | Meets O(log) | Recommended |
|----------|------|-------|--------------|-------------|
| **Binary Search Partition** | **O(log(min(m,n)))** | **O(1)** | **Yes ✅** | **Yes ✅** |
| Merge Arrays | O(m + n) | O(m + n) | No ❌ | Too slow |

**Winner**: **Binary search** — only valid solution!

---

## Key Takeaways

1. **Binary search on partition** — not on elements
2. **Always search on smaller array** — O(log(min(m,n)))
3. **Partition size** — (m+n+1)/2 works for both odd and even
4. **Valid partition** — maxLeft ≤ minRight on both sides
5. **Boundary handling** — use Integer.MIN_VALUE and MAX_VALUE
6. **Odd total** — median = max of left partition
7. **Even total** — median = average of max left and min right
8. **Two cross-checks** — must validate both directions
9. **Use floating-point** — divide by 2.0 for decimal result
10. **O(log(min(m,n))) time, O(1) space** — optimal and required

---

## Interview Tips

**What to say in an interview:**

> "This problem requires finding the median of two sorted arrays in logarithmic time, which suggests binary search. Instead of merging the arrays (which would be O(m+n)), I'll use binary search to find the correct partition point. I'll always search on the smaller array for efficiency, giving O(log(min(m,n))) time. The key idea is to partition both arrays such that the left partition has (m+n+1)/2 elements total, and all elements in the left partition are less than or equal to all elements in the right partition. For each partition candidate, I calculate where to partition the second array, then validate using two cross-checks: the maximum of the left partition in array A should be ≤ the minimum of the right partition in array B, and vice versa. If the partition is invalid, I adjust based on which side violates the condition. Once I find a valid partition, if the total length is odd, the median is the maximum of the left partition; if even, it's the average of the maximum left element and minimum right element. I handle edge cases where the partition is at array boundaries using Integer.MIN_VALUE and MAX_VALUE. This solution achieves O(log(min(m,n))) time with O(1) space."

**Key points to mention:**
1. **Binary search on partition** — not merging
2. **Search on smaller array** — efficiency
3. **Partition size (m+n+1)/2** — handles odd and even
4. **Two cross-checks for validation** — both directions
5. **Boundary handling** — MIN_VALUE and MAX_VALUE
6. **Odd vs even total** — different median formulas
7. **O(log(min(m,n))) time** — meets requirement
8. **O(1) space** — no extra arrays

**Common Follow-ups:**
- "Why search on smaller array?" → O(log min) is faster than O(log max)
- "What if one array is empty?" → Handled by boundary values
- "Why (m+n+1)/2 not (m+n)/2?" → Works for both odd and even totals
- "Can you optimize further?" → Already optimal time and space
- "Handle duplicates?" → Algorithm handles them correctly

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Median of Two Sorted Arrays** | Hard | **Binary Search Partition** | **This problem** |
| Kth Smallest Element in Sorted Matrix | Medium | Binary Search / Heap | 2D matrix instead of arrays |
| Find K Pairs with Smallest Sums | Medium | Heap / Binary Search | Finding pairs, not median |
| Merge Two Sorted Lists | Easy | Two Pointers | Linked lists, must merge |
| Merge Sorted Array | Easy | Two Pointers | One array, in-place |
| Find Median from Data Stream | Hard | Two Heaps | Dynamic stream, not static arrays |

**Pattern Progression**:
1. **Merge two sorted** — Basic two pointer
2. **Median of two sorted** (this problem) — Binary search partition
3. **Advanced statistics** — Multiple arrays, streaming data

---

## Final Pattern Label

✅ **Binary Search on Partition to Find Median**

**Remember:** This is **binary search on partition point**, not on array elements! **Always search on smaller array** for O(log(min(m,n))) efficiency. **Partition size is (m+n+1)/2** which works for both odd and even totals. **Validate partition** with two cross-checks: maxLeftA ≤ minRightB AND maxLeftB ≤ minRightA. **Handle boundaries** with Integer.MIN_VALUE (no left elements) and MAX_VALUE (no right elements). When partition **invalid**, adjust based on which side violates: if maxLeftA > minRightB, move left in A; if maxLeftB > minRightA, move right in A. **Compute median** from partition: if odd total, return max(maxLeftA, maxLeftB); if even, return (max left + min right) / 2.0. Must use **floating-point division** (2.0 not 2). This is the **only approach** that achieves required O(log(m+n)) time - merging would be O(m+n)! Space is O(1) with only a few variables. Critical insight: **don't merge arrays, just find where they would split** at median position!
