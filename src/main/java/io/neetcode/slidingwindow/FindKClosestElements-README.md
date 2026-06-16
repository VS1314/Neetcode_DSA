# Find K Closest Elements

## Problem Description

**Difficulty**: Medium

You are given a **sorted** integer array `arr`, two integers `k` and `x`. Return the `k` **closest** integers to `x` in the array. The result should also be sorted in ascending order.

An integer `a` is closer to `x` than an integer `b` if:
- `|a - x| < |b - x|`, or
- `|a - x| == |b - x|` and `a < b`

## Examples

### Example 1:
```
Input: arr = [2,4,5,8], k = 2, x = 6
Output: [4,5]
Explanation: 
Distance from 6: |2-6|=4, |4-6|=2, |5-6|=1, |8-6|=2
Two closest: 5 (distance 1), 4 (distance 2)
Result sorted: [4,5]
```

### Example 2:
```
Input: arr = [2,3,4], k = 3, x = 1
Output: [2,3,4]
Explanation: 
All elements are closest. Return all 3 in sorted order.
```

### Example 3:
```
Input: arr = [1,2,3,4,5], k = 4, x = 3
Output: [1,2,3,4]
Explanation:
Distance from 3: |1-3|=2, |2-3|=1, |3-3|=0, |4-3|=1, |5-3|=2
Four closest: 3,2,4,1 (or 3,2,4,5 equal distance)
Since 1 < 5, choose 1: [1,2,3,4]
```

### Example 4:
```
Input: arr = [1,2,3,4,5], k = 4, x = -1
Output: [1,2,3,4]
Explanation:
All elements to the right of x. Take first k elements.
```

## Constraints
- 1 <= k <= arr.length <= 10,000
- -10,000 <= arr[i], x <= 10,000
- arr is sorted in **ascending order**

**Recommended Complexity**: O(log(n-k) + k) time, O(1) space (excluding output)

---

## Pattern Recognition

**Primary Pattern**: **Binary Search + Two Pointers / Sliding Window**

**Why This Pattern?**
- Array is sorted (suggests binary search)
- Need k closest elements
- Result must be contiguous subarray (key insight!)
- Want to find optimal window of size k

**Key Insight**: Closest Elements Form Contiguous Subarray
```
Problem: Find k closest elements to x in sorted array

Critical observation:
  Since array is SORTED, the k closest elements
  will ALWAYS form a contiguous subarray!
  
Why?
  Assume closest elements are NOT contiguous
  → There exists gap with element y between them
  → y must be farther from x than both neighbors
  → But array is sorted, so y is between neighbors in value
  → Contradiction! If neighbors are close, middle value also close
  
Example: arr = [1,2,3,4,5,6,7,8,9], k=3, x=5
  Closest: 4,5,6 (contiguous!)
  NOT: 4,5,7 or 3,5,7 (not contiguous, not optimal)
  
Result: Problem reduces to finding best window of size k
```

**Why Binary Search?**
```
Brute force: Try all windows
  for i from 0 to n-k:
    calculate how good window [i...i+k-1] is
  → O(n) windows to check
  
Binary Search:
  Search space: window start index from 0 to n-k
  For each potential start, determine if we should go left or right
  → O(log(n-k)) to find optimal start
```

**The Window Comparison Strategy**:
```
Given window starting at index 'mid':
  Window: [arr[mid], arr[mid+1], ..., arr[mid+k-1]]
  
  Compare:
    - Left distance: x - arr[mid]
    - Right distance: arr[mid+k] - x
  
  Decision:
    If left distance > right distance:
      → Move window right (mid++)
      → Current window too far left
    
    Else:
      → Move window left (or keep it)
      → Current window is good or too far right
      
This works because array is sorted!
```

**Critical Detail**: Tie-Breaking Rule
```
When distances are equal: |a-x| == |b-x|
  Choose the smaller number (a < b)
  
This is automatically handled by:
  1. Starting from leftmost valid window
  2. Comparing distances and moving appropriately
  
Example: arr = [1,2,3,4,5], x = 3, k = 4
  Window [1,2,3,4]: valid
  Window [2,3,4,5]: also valid
  
  Compare:
    [1,2,3,4]: left edge 1, right after window 5
    Distance from left: 3-1=2
    Distance from right: 5-3=2 (equal!)
    
  Since equal and 1 < 5, choose window with 1: [1,2,3,4]
```

**Related Patterns**:
1. **Binary Search** — Find optimal window start
2. **Fixed Sliding Window** — Window size = k
3. **Two Pointers** — Expand from closest point
4. **Sorted Array** — Leverage sorted property

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Calculate distances, sort, take k elements
  Calculate |arr[i] - x| for all elements
  Sort by distance (with tie-breaking)
  Take first k elements
  Sort result
  → O(n log n) for sorting distances
  → Too slow!

Binary Search Approach:
  Find optimal window start using binary search
  Window size fixed at k
  Return window elements
  → O(log(n-k) + k) time
  → Much faster!
```

**The Binary Search Strategy**:
```
Key observations:
  1. Answer is contiguous subarray of length k
  2. Window can start at indices [0, n-k]
  3. Can binary search for optimal start
  
Search logic:
  If left edge too far → move window right
  If right edge too far → move window left
  
Comparison at mid:
  leftDist = x - arr[mid]
  rightDist = arr[mid+k] - x
  
  If leftDist > rightDist:
    Window too far left, search right half
  Else:
    Window too far right or perfect, search left half
```

### Step-by-Step Algorithm

---

#### **Approach 1: Binary Search for Window Start (OPTIMAL)**

**Core Idea**:
- Binary search to find the best starting index for window of size k
- Compare distances of left and right boundaries to decide direction
- Return subarray starting from optimal index

**Algorithm**
```
findClosestElements(arr, k, x):
    left = 0
    right = arr.length - k
    
    // Binary search for window start
    while left < right:
        mid = left + (right - left) / 2
        
        // Compare distances
        leftDist = x - arr[mid]
        rightDist = arr[mid + k] - x
        
        if leftDist > rightDist:
            // Window too far left, move right
            left = mid + 1
        else:
            // Window too far right or perfect, move left
            right = mid
    
    // left is the optimal window start
    return arr[left ... left + k - 1]
```

**Code Implementation**
```java
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int left = 0;
        int right = arr.length - k;
        
        // Binary search for the start of the window
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Compare distances from x
            int leftDist = x - arr[mid];
            int rightDist = arr[mid + k] - x;
            
            if (leftDist > rightDist) {
                // Current window too far left, move right
                left = mid + 1;
            } else {
                // Current window too far right or just right, move left
                right = mid;
            }
        }
        
        // Build result from optimal window
        List<Integer> result = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            result.add(arr[i]);
        }
        
        return result;
    }
}
```

**Example Walkthrough**

Input: `arr = [1,2,3,4,5], k = 4, x = 3`

| Iteration | left | right | mid | Window | leftDist | rightDist | Decision |
|-----------|------|-------|-----|--------|----------|-----------|----------|
| Init | 0 | 1 | - | - | - | - | - |
| 1 | 0 | 1 | 0 | [1,2,3,4] | 3-1=2 | 5-3=2 | Equal, right=mid=0 |
| Exit | 0 | 0 | - | - | - | - | left==right |

**Output:** `[1,2,3,4]`

**Complexity Analysis**
- **Time Complexity**: O(log(n-k) + k) — Binary search + build result
- **Space Complexity**: O(1) — Excluding output array

---

#### **Approach 2: Binary Search + Two Pointers (ALTERNATIVE)**

**Core Idea**: Find closest element, then expand window using two pointers.

**Code Implementation**
```java
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // Binary search to find closest element to x
        int closestIndex = findClosestIndex(arr, x);
        
        // Two pointers to expand window
        int left = closestIndex;
        int right = closestIndex;
        
        // Expand window to k elements
        while (right - left + 1 < k) {
            if (left == 0) {
                // Can only expand right
                right++;
            } else if (right == arr.length - 1) {
                // Can only expand left
                left--;
            } else {
                // Compare distances
                int leftDist = x - arr[left - 1];
                int rightDist = arr[right + 1] - x;
                
                if (leftDist <= rightDist) {
                    left--;
                } else {
                    right++;
                }
            }
        }
        
        // Build result
        List<Integer> result = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            result.add(arr[i]);
        }
        
        return result;
    }
    
    private int findClosestIndex(int[] arr, int x) {
        int left = 0, right = arr.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] < x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        // Check if left-1 is closer
        if (left > 0 && Math.abs(arr[left - 1] - x) <= Math.abs(arr[left] - x)) {
            return left - 1;
        }
        
        return left;
    }
}
```

**Key Difference**: 
- Find closest element first
- Then expand window symmetrically
- More intuitive but similar complexity

**Complexity Analysis**
- **Time Complexity**: O(log n + k) — Binary search + expand window
- **Space Complexity**: O(1) — Excluding output

---

#### **Approach 3: Sort by Distance (BRUTE FORCE)**

**Core Idea**: Calculate all distances, sort, take k closest, then sort result.

**Code Implementation**
```java
class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // Create list of pairs (value, distance)
        List<int[]> pairs = new ArrayList<>();
        for (int num : arr) {
            pairs.add(new int[]{num, Math.abs(num - x)});
        }
        
        // Sort by distance, then by value (for tie-breaking)
        Collections.sort(pairs, (a, b) -> {
            if (a[1] != b[1]) {
                return a[1] - b[1];  // Sort by distance
            }
            return a[0] - b[0];  // Tie-break by value
        });
        
        // Take first k elements
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(pairs.get(i)[0]);
        }
        
        // Sort result in ascending order
        Collections.sort(result);
        
        return result;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n log n) — Sorting all elements
- **Space Complexity**: O(n) — Store pairs
- **Why Not Optimal**: Too slow

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Sort by Distance | Two Pointers | **Binary Search Window** |
|-------------|------------------|--------------|--------------------------|
| Time complexity | O(n log n) ❌ | O(log n + k) ✓ | **O(log(n-k) + k) ✅** |
| Space complexity | O(n) ❌ | O(1) ✓ | **O(1) ✅** |
| Code simplicity | Simple | Medium | **Clean ✅** |
| Optimal | ❌ | ✅ | **✅** |

**Winner**: **Binary Search for Window Start** — optimal and clean!

### Why Contiguous Subarray?

```
Proof by contradiction:
  Assume optimal k elements are NOT contiguous in sorted array
  
  → Exists gap: elements a, c are selected, but b between them is not
  → Array sorted: a < b < c
  → If a and c are both closest to x, what about b?
  
  Case 1: x < a
    Distance: (a-x) < (b-x) < (c-x)
    But c is selected over b? Contradiction!
  
  Case 2: a < x < c
    b is between a and c
    If |a-x| and |c-x| both ≤ |b-x|:
      Then x must be very close to a or c
      But then b would be closer than one of them!
      Contradiction!
  
  Case 3: c < x
    Distance: (x-c) < (x-b) < (x-a)
    But a is selected over b? Contradiction!
  
Conclusion: k closest elements must be contiguous!
```

### Why Binary Search Works?

```
Search space: window start index [0, n-k]
  Each index represents a potential window start
  
Monotonic property:
  If window [i, i+k-1] is "too far left"
  → All windows [0...i] are also too far left
  → Search in [i+1, n-k]
  
  If window [i, i+k-1] is "too far right"
  → All windows [i...n-k] are also too far right
  → Search in [0, i-1]
  
This monotonicity enables binary search!

Decision criterion:
  Compare: x - arr[mid] vs arr[mid+k] - x
  
  If x - arr[mid] > arr[mid+k] - x:
    Left boundary farther than right boundary
    → Window should move right
  
  Else:
    Right boundary farther or equal
    → Window should move left or is optimal
```

---

## Critical Edge Cases & Gotchas

### 1. **k Equals Array Length**
```java
Input: arr = [1,2,3], k = 3, x = 5
Output: [1,2,3]
Explanation: Return entire array.
```

### 2. **x Smaller Than All Elements**
```java
Input: arr = [5,6,7,8], k = 2, x = 1
Output: [5,6]
Explanation: All elements to the right. Take first k.
```

### 3. **x Larger Than All Elements**
```java
Input: arr = [1,2,3,4], k = 2, x = 10
Output: [3,4]
Explanation: All elements to the left. Take last k.
```

### 4. **x Equals Array Element**
```java
Input: arr = [1,2,3,4,5], k = 3, x = 3
Output: [2,3,4]
Explanation: Expand symmetrically around 3.
```

### 5. **Multiple Equal Distances**
```java
Input: arr = [1,3,5,7], k = 2, x = 4
Output: [3,5]
Explanation: |3-4|=1, |5-4|=1. Both equal distance, take both.
```

### 6. **Tie-Breaking Favors Smaller**
```java
Input: arr = [1,2,3,4,5], k = 4, x = 3
Output: [1,2,3,4]
Explanation: Distance to 1 and 5 both = 2. Choose 1 (smaller).
```

### 7. **Single Element**
```java
Input: arr = [1], k = 1, x = 5
Output: [1]
Explanation: Only one element.
```

### 8. **Negative Numbers**
```java
Input: arr = [-5,-3,-1,0,2,4], k = 3, x = -2
Output: [-3,-1,0]
Explanation: Works with negatives too.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Wrong Binary Search Bounds**
```java
// WRONG - searches entire array instead of valid window starts
int left = 0;
int right = arr.length - 1;  // WRONG! Should be arr.length - k
```

**Why wrong**: Window needs k elements, so last valid start is `n-k`!

**Dry run failure for arr=[1,2,3,4,5], k=3:**
```
If right = 4:
  mid = 2
  Window would be [3,4,5] → arr[mid+k] = arr[5] → IndexOutOfBounds!
  
Correct: right = 5-3 = 2
  Valid starts: 0,1,2 (windows [1,2,3], [2,3,4], [3,4,5])
```

**Fix**: Use `arr.length - k`
```java
int right = arr.length - k;
```

### ❌ **MISTAKE 2: Wrong Distance Comparison**
```java
// WRONG - compares absolute distances incorrectly
if (Math.abs(x - arr[mid]) > Math.abs(arr[mid + k] - x)) {
    left = mid + 1;
}
```

**Why wrong**: Need to compare x - arr[mid] vs arr[mid+k] - x (signed)!

**Dry run failure for arr=[1,5,10], k=2, x=6:**
```
mid=0: Window [1,5]
  Using abs: |6-1|=5 vs |10-6|=4 → 5>4, move right
  But [1,5] is actually optimal! (distances 5,1 vs 4,5)
  
Without abs: 6-1=5 vs 10-6=4 → 5>4, move right
  Correct decision based on boundary distances!
```

**Fix**: Don't use Math.abs for comparison
```java
if (x - arr[mid] > arr[mid + k] - x) {
    left = mid + 1;
}
```

### ❌ **MISTAKE 3: Off-by-One in Result Building**
```java
// WRONG - doesn't include all k elements
for (int i = left; i < left + k - 1; i++) {  // WRONG! Missing last element
    result.add(arr[i]);
}
```

**Why wrong**: Should iterate k times, not k-1!

**Fix**: Use `i < left + k` or `i <= left + k - 1`
```java
for (int i = left; i < left + k; i++) {
    result.add(arr[i]);
}
```

### ❌ **MISTAKE 4: Not Handling k = n Case**
```java
// Potential issue: what if k == arr.length?
int right = arr.length - k;  // right = 0

// Binary search with left=0, right=0
// Returns left=0, which is correct!
// But should verify algorithm handles it
```

**Fix**: Algorithm naturally handles this, but worth testing
```java
// When k == n, only one valid window: entire array
// right = n - n = 0
// Binary search: left=0, right=0 → immediately returns left=0
// Result: arr[0...n-1] = entire array ✓
```

### ❌ **MISTAKE 5: Wrong Condition for Moving Pointers**
```java
// WRONG - uses wrong comparison
if (x - arr[mid] < arr[mid + k] - x) {  // WRONG! Should be >
    left = mid + 1;
}
```

**Why wrong**: Logic is inverted!

**Correct logic**:
```java
// If left boundary farther than right boundary
// → Window too far left → Move right
if (x - arr[mid] > arr[mid + k] - x) {
    left = mid + 1;
} else {
    right = mid;
}
```

### ❌ **MISTAKE 6: Using left + 1 Instead of mid + 1**
```java
// WRONG - doesn't make progress
if (x - arr[mid] > arr[mid + k] - x) {
    left = left + 1;  // WRONG! Should be mid + 1
}
```

**Why wrong**: Need to eliminate left half, not just move one position!

**Fix**: Use `mid + 1`
```java
left = mid + 1;
```

### ❌ **MISTAKE 7: Not Considering Equal Distance Tie-Breaking**
```java
// WRONG - doesn't handle equal distances properly
if (x - arr[mid] >= arr[mid + k] - x) {  // WRONG! Using >=
    left = mid + 1;
}
```

**Why wrong**: When distances equal, favor smaller (left) element!

**Dry run failure for arr=[1,2,3,4,5], k=4, x=3:**
```
Window [1,2,3,4]: leftDist=3-1=2, rightDist=5-3=2 (equal)
Using >=: 2 >= 2 → move right
  Would choose [2,3,4,5]
  
But should choose [1,2,3,4] (smaller element 1 < 5)!

Correct: Use > (not >=)
  When equal, right=mid keeps current window (favors left)
```

**Fix**: Use `>` not `>=`
```java
if (x - arr[mid] > arr[mid + k] - x) {
    left = mid + 1;
} else {
    right = mid;  // Equal distances handled here
}
```

---

## Complexity Analysis

### Time Complexity: **O(log(n-k) + k)**

| Operation | Time | Reason |
|-----------|------|--------|
| Binary search | O(log(n-k)) | Search space is [0, n-k] |
| Build result | O(k) | Copy k elements to result |
| **Total** | **O(log(n-k) + k)** | Optimal for this problem |

**Why log(n-k)?**
```
Search space: indices [0, 1, 2, ..., n-k]
  → Size = n-k+1 ≈ n-k
  
Binary search: halves search space each iteration
  → O(log(n-k)) iterations
  
Special cases:
  k = 1: O(log n) — search for single closest
  k = n: O(log 1) = O(1) — only one window
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| left, right, mid | O(1) | Binary search variables |
| Result list | O(k) | Output (not counted in space complexity) |
| **Total** | **O(1)** | Constant extra space |

**Note**: Output array O(k) is not counted in space complexity analysis

---

## Visualization

### Complete Example Walkthrough

**Input:** `arr = [1,2,3,4,5], k = 4, x = 3`

**Goal:** Find 4 closest elements to 3.

---

**Step 1: Setup Binary Search**
```
arr = [1, 2, 3, 4, 5]
k = 4, x = 3

Possible window starts: 0 to n-k = 5-4 = 1
  Window starting at 0: [1,2,3,4]
  Window starting at 1: [2,3,4,5]
  
Binary search range: [0, 1]
```

---

**Step 2: Binary Search Iteration 1**
```
left = 0, right = 1
mid = 0 + (1-0)/2 = 0

Window at mid=0: [1,2,3,4]
  arr[mid] = arr[0] = 1
  arr[mid+k] = arr[4] = 5
  
Compare distances:
  leftDist = x - arr[mid] = 3 - 1 = 2
  rightDist = arr[mid+k] - x = 5 - 3 = 2
  
Decision:
  leftDist > rightDist? → 2 > 2? → No
  Equal distances! Use else branch
  right = mid = 0
  
State: left=0, right=0
```

---

**Step 3: Binary Search Exit**
```
left = 0, right = 0
Condition: left < right? → 0 < 0? → No

Exit binary search
Optimal window start: left = 0
```

---

**Step 4: Build Result**
```
Window: [arr[0], arr[1], arr[2], arr[3]]
      = [1, 2, 3, 4]

Result: [1, 2, 3, 4]
```

---

**Final Result:** `[1, 2, 3, 4]`

**Explanation:**
```
Distance from 3:
  1: |1-3| = 2
  2: |2-3| = 1
  3: |3-3| = 0
  4: |4-3| = 1
  5: |5-3| = 2
  
Four closest: 3(0), 2(1), 4(1), then 1 or 5 (both 2)
Since 1 < 5, choose 1
Result: [1,2,3,4]
```

### Another Example with Different Decision

**Input:** `arr = [1,2,3,4,5,6,7,8], k = 3, x = 6`

```
left=0, right=5

Iteration 1: mid=2
  Window [3,4,5]
  leftDist = 6-3=3, rightDist = 8-6=2
  3 > 2 → left = mid+1 = 3

Iteration 2: left=3, right=5, mid=4
  Window [5,6,7]
  leftDist = 6-5=1, rightDist = 8-6=2
  1 > 2? No → right = mid = 4

Iteration 3: left=3, right=4, mid=3
  Window [4,5,6]
  leftDist = 6-4=2, rightDist = 7-6=1
  2 > 1? Yes → left = mid+1 = 4

left=4, right=4 → Exit
Result: [5,6,7]
```

---

## Comparison of Approaches

| Approach | Time | Space | Optimal | Notes |
|----------|------|-------|---------|-------|
| Sort by Distance | O(n log n) | O(n) | ❌ | Brute force |
| Two Pointers Expand | O(log n + k) | O(1) | ✅ | Intuitive |
| **Binary Search Window** | **O(log(n-k) + k)** | **O(1)** | **✅** | **Most efficient** |

**Recommendation**: Use **Binary Search for Window Start** — optimal and elegant!

---

## Key Takeaways

1. **Closest elements form contiguous subarray** — sorted array property
2. **Binary search on window start** — not on elements themselves
3. **Compare boundary distances** — leftDist vs rightDist
4. **Right bound is n-k** — last valid window start
5. **Use > not >=** — handles equal distance tie-breaking
6. **Don't use Math.abs** — need signed distances
7. **O(log(n-k) + k) time** — optimal complexity

---

## Interview Tips

**What to say in an interview:**

> "This problem has a key insight: since the array is sorted, the k closest elements will always form a contiguous subarray. This reduces the problem to finding the optimal starting index for a window of size k. I can binary search on the window start position, which ranges from 0 to n-k. For each candidate window starting at mid, I'll compare the distances from x to the left boundary (arr[mid]) and the right boundary just outside the window (arr[mid+k]). If the left boundary is farther from x than the right boundary, the window should shift right, so I search the right half. Otherwise, I search the left half or keep the current window. This gives O(log(n-k)) for the binary search plus O(k) to build the result."

**Key points to mention:**
1. **Contiguous subarray insight** — sorted array property
2. **Binary search on window start** — not elements
3. **Boundary comparison** — leftDist vs rightDist
4. **Tie-breaking** — handled by using > instead of >=
5. **Complexity** — O(log(n-k) + k) time, O(1) space

**If asked about alternatives:**
> "I could also binary search for the closest element to x first, then expand a window using two pointers. This would be O(log n + k) time. Or I could calculate all distances, sort them, and take the k smallest, which would be O(n log n). The binary search on window start is optimal because it directly finds the best window without needing to check all elements."

**Common Follow-ups:**
- "What if array is not sorted?" → Need to sort first or use heap (O(n log k))
- "What if you need exactly k elements at distance d?" → Different problem, sliding window with distance constraint
- "Can you do better than O(log(n-k) + k)?" → No, must build k-element result

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Find K Closest Elements** | Medium | **Binary Search + Window** | **This problem** |
| Find K Closest Points to Origin | Medium | Heap / Quick Select | 2D points, not sorted |
| K Closest Points to Origin | Medium | Max Heap | Maintain k smallest distances |
| Search in Rotated Sorted Array | Medium | Binary Search | Modified binary search |
| Find Minimum in Rotated Sorted Array | Medium | Binary Search | Different search criterion |
| Closest Binary Search Tree Value | Easy | Binary Search (BST) | Tree structure |

**Pattern Progression**:
1. **Sorted array, find k closest** (this problem) — Binary search on window
2. **Unsorted, find k closest** — Heap or quickselect
3. **2D closest** — Different distance metric
4. **BST closest** — Tree traversal

---

## Final Pattern Label

✅ **Binary Search on Window Start (Sorted Array + Fixed Window)**

**Remember:** The k closest elements in a sorted array always form a contiguous subarray. Binary search for the optimal window start index by comparing distances from x to the window's left boundary (arr[mid]) and the element just after the window (arr[mid+k]). If the left boundary is farther, shift the window right. The key trick is searching on window positions [0, n-k], not on array elements. This gives O(log(n-k) + k) time with O(1) extra space!
