# Sliding Window Maximum

## Problem Description

**Difficulty**: Hard

You are given an array of integers `nums` and an integer `k`. There is a sliding window of size `k` that starts at the left edge of the array. The window slides one position to the right until it reaches the right edge of the array.

Return a list that contains the **maximum element** in the window at each step.

## Examples

### Example 1:
```
Input: nums = [1,2,1,0,4,2,6], k = 3
Output: [2,2,4,4,6]

Explanation:
Window position            Max
---------------           -----
[1  2  1] 0  4  2  6        2
 1 [2  1  0] 4  2  6        2
 1  2 [1  0  4] 2  6        4
 1  2  1 [0  4  2] 6        4
 1  2  1  0 [4  2  6]       6
```

### Example 2:
```
Input: nums = [1], k = 1
Output: [1]
Explanation: Single element, single window.
```

### Example 3:
```
Input: nums = [1,-1], k = 1
Output: [1,-1]
Explanation: k=1, each element is its own window maximum.
```

### Example 4:
```
Input: nums = [9,8,7,6,5], k = 3
Output: [9,8,7]
Explanation: Decreasing array, first element of each window is max.
```

### Example 5:
```
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
Explanation:
[1  3 -1] → max=3
[3 -1 -3] → max=3
[-1 -3  5] → max=5
[-3  5  3] → max=5
[5  3  6] → max=6
[3  6  7] → max=7
```

## Constraints
- 1 <= nums.length <= 10,000
- -10,000 <= nums[i] <= 10,000
- 1 <= k <= nums.length

**Recommended Complexity**: O(n) time, O(n) space where n is the size of input array

---

## Pattern Recognition

**Primary Pattern**: **Fixed Sliding Window + Monotonic Deque**

**Why This Pattern?**
- Fixed window size k (slides one position at a time)
- Need maximum at each window position
- Window moves left to right through entire array
- Need efficient way to track maximum as window slides

**Key Insight**: Monotonic Deque Strategy
```
Problem: Find maximum in each sliding window efficiently

Critical observation:
  When a new larger element enters the window,
  ALL smaller elements to its left become IRRELEVANT!
  
Example: Window [3, 2, 1], new element 5 enters
  Current max: 3
  After 5 enters: [3, 2, 1, 5]
  
  Can 3, 2, or 1 ever be max again while 5 is in window? NO!
  → 5 is larger and will stay longer in the window
  → We can remove 3, 2, 1 from consideration
  
Key insight: Maintain decreasing order!
  Keep only elements that could potentially be maximum
```

**Why Sliding Window?**
```
Problem characteristics:
  ✓ Fixed window size k
  ✓ Moves one step at a time
  ✓ Need result for each position
  ✓ Contiguous subarray
  
This is a textbook sliding window problem!

Challenge:
  Standard sliding window: O(1) operations per step
  Finding max: normally O(k) per window
  
  Need: Data structure that supports:
    1. Add element to window: O(1) amortized
    2. Remove element from window: O(1) amortized
    3. Query maximum: O(1)
    
  Solution: Monotonic Deque!
```

**The Monotonic Deque Strategy**:
```
Deque (Double-Ended Queue):
  Can add/remove from both front and back in O(1)
  
Monotonic Decreasing Deque:
  Elements in deque are in DECREASING order (largest to smallest)
  Front of deque = current maximum
  
Invariant: deque[0] >= deque[1] >= deque[2] >= ...

Operations:
  1. Add element to window (right side):
     - Remove all elements from back that are < new element
     - Add new element at back
     
  2. Remove element from window (left side):
     - If deque front equals element leaving window, remove from front
     
  3. Query maximum:
     - Return deque front (always the maximum!)
```

**Why This Works**:
```
Example: nums = [4, 3, 2, 5], k = 3

Step 1: Add 4
  deque = [4]
  
Step 2: Add 3
  3 < 4, keep both (3 might be max after 4 leaves)
  deque = [4, 3]
  
Step 3: Add 2, window full
  2 < 3, keep both
  deque = [4, 3, 2]
  max = 4 ✓
  
Step 4: Slide window (remove 4, add 5)
  Remove 4 from front: deque = [3, 2]
  Add 5: 5 > 2 → remove 2
         5 > 3 → remove 3
         deque = [5]
  max = 5 ✓
  
Key: When 5 enters, we remove all smaller elements
     because 5 will always be larger AND stay longer!
```

**Critical Detail**: Store Indices, Not Values
```
Why indices?
  1. Check if element is still in window
  2. Handle duplicate values correctly
  3. Know when to remove from front
  
Example: nums = [1, 3, 1, 2], k = 3
  If we store values only:
    deque = [3, 1]
    When window slides and we need to remove first 1,
    we can't tell which 1 to remove!
  
  With indices:
    deque = [1, 2] (indices pointing to 3 and first 1)
    Window slides: check if deque front index is out of bounds
    Clear and unambiguous!
```

**Related Patterns**:
1. **Fixed Sliding Window** — Window size = k
2. **Monotonic Stack/Deque** — Maintain increasing/decreasing order
3. **Deque** — Add/remove from both ends
4. **Next Greater Element** — Similar monotonic structure

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: For each window, scan k elements to find max
  for i from 0 to n-k:
    max = findMax(nums[i...i+k-1])
    result.add(max)
  
Time: O((n-k+1) × k) = O(n × k)
  When k is large (e.g., k = n/2), this becomes O(n²) ❌
  Too slow!

Heap Approach:
  Use max heap to track maximum
  Add elements with indices
  Remove elements outside window
  → O(n log n) — Better but not optimal

Monotonic Deque:
  Maintain decreasing order
  Each element added/removed at most once
  → O(n) — Optimal! ✅
```

**The Optimal Strategy**:
```
Key observations:
  1. When larger element enters, smaller elements become useless
  2. Front of deque always contains current maximum
  3. Each element added/removed from deque at most once
  
Deque invariant:
  - Stores indices of elements
  - Elements in decreasing order of VALUES
  - All elements in deque are within current window
  
Operations per step:
  1. Remove front if out of window: O(1)
  2. Remove back elements smaller than new: O(1) amortized
  3. Add new element: O(1)
  4. Query front for max: O(1)
```

### Step-by-Step Algorithm

---

#### **Approach 1: Monotonic Deque (OPTIMAL)**

**Core Idea**:
- Use deque to store indices of potential maximums in decreasing order
- Front of deque is always current window maximum
- Remove elements that can never be maximum

**Algorithm**
```
maxSlidingWindow(nums, k):
    deque = empty deque (stores indices)
    result = empty list
    
    for i from 0 to n-1:
        // Remove indices outside window
        while deque not empty and deque.front < i - k + 1:
            deque.removeFront()
        
        // Remove indices with smaller values
        while deque not empty and nums[deque.back] < nums[i]:
            deque.removeBack()
        
        // Add current index
        deque.addBack(i)
        
        // Record maximum (front of deque) when window is full
        if i >= k - 1:
            result.add(nums[deque.front])
    
    return result
```

**Code Implementation**
```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        
        int n = nums.length;
        int[] result = new int[n - k + 1];
        int resultIndex = 0;
        
        // Deque stores indices, maintains decreasing order of values
        Deque<Integer> deque = new LinkedList<>();
        
        for (int i = 0; i < n; i++) {
            // Remove indices that are out of current window
            // Window: [i - k + 1, i]
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            
            // Remove indices whose values are less than current element
            // They can never be maximum while current element is in window
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            
            // Add current index to deque
            deque.offerLast(i);
            
            // Once we have a full window, record the maximum
            if (i >= k - 1) {
                result[resultIndex++] = nums[deque.peekFirst()];
            }
        }
        
        return result;
    }
}
```

**Example Walkthrough**

Input: `nums = [1,3,-1,-3,5,3,6,7], k = 3`

| i | nums[i] | Window | Deque (indices) | Deque (values) | Max | Explanation |
|---|---------|--------|-----------------|----------------|-----|-------------|
| 0 | 1 | [1] | [0] | [1] | - | Add index 0 |
| 1 | 3 | [1,3] | [1] | [3] | - | 3 > 1, remove 0, add 1 |
| 2 | -1 | [1,3,-1] | [1,2] | [3,-1] | 3 | -1 < 3, keep both, add 2 |
| 3 | -3 | [3,-1,-3] | [1,2,3] | [3,-1,-3] | 3 | -3 < -1, keep all, add 3 |
| 4 | 5 | [-1,-3,5] | [4] | [5] | 5 | Remove 1 (out), 5 > -1, remove 2,3, add 4 |
| 5 | 3 | [-3,5,3] | [4,5] | [5,3] | 5 | 3 < 5, add 5 |
| 6 | 6 | [5,3,6] | [6] | [6] | 6 | Remove 4 (out), 6 > 3, remove 5, add 6 |
| 7 | 7 | [3,6,7] | [7] | [7] | 7 | Remove 6 (out), 7 > 6, add 7 |

**Output:** `[3,3,5,5,6,7]`

**Complexity Analysis**
- **Time Complexity**: O(n) — Each element added and removed from deque at most once
- **Space Complexity**: O(k) — Deque stores at most k indices

---

#### **Approach 2: Max Heap with Lazy Deletion**

**Core Idea**: Use max heap to track maximum, remove stale elements lazily.

**Code Implementation**
```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        int resultIndex = 0;
        
        // Max heap: stores [value, index]
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) {
                return b[0] - a[0];  // Max heap by value
            }
            return b[1] - a[1];  // Prefer later index for ties
        });
        
        for (int i = 0; i < n; i++) {
            // Add current element
            maxHeap.offer(new int[]{nums[i], i});
            
            // Remove elements outside current window
            while (!maxHeap.isEmpty() && maxHeap.peek()[1] < i - k + 1) {
                maxHeap.poll();
            }
            
            // Record maximum once window is full
            if (i >= k - 1) {
                result[resultIndex++] = maxHeap.peek()[0];
            }
        }
        
        return result;
    }
}
```

**Key Difference**: 
- Heap doesn't maintain strict window property
- Elements outside window remain in heap (lazy deletion)
- Clean up only when accessing maximum

**Complexity Analysis**
- **Time Complexity**: O(n log n) — Each of n elements: add O(log n), may remove O(log n)
- **Space Complexity**: O(n) — Heap can grow to size n (stores stale elements)

---

#### **Approach 3: Brute Force**

**Core Idea**: For each window, scan k elements to find maximum.

**Code Implementation**
```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        
        for (int i = 0; i <= n - k; i++) {
            int max = nums[i];
            for (int j = i; j < i + k; j++) {
                max = Math.max(max, nums[j]);
            }
            result[i] = max;
        }
        
        return result;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n × k) — For each of n-k+1 windows, scan k elements
- **Space Complexity**: O(1) — Excluding output
- **Why Not Optimal**: Too slow for large inputs

---

#### **Approach 4: Dynamic Programming (Alternative)**

**Core Idea**: Divide array into blocks of size k, track max from left and right.

**Code Implementation**
```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] left = new int[n];   // Max from left of block
        int[] right = new int[n];  // Max from right of block
        
        // Build left array
        for (int i = 0; i < n; i++) {
            if (i % k == 0) {
                left[i] = nums[i];
            } else {
                left[i] = Math.max(left[i - 1], nums[i]);
            }
        }
        
        // Build right array
        for (int i = n - 1; i >= 0; i--) {
            if (i == n - 1 || (i + 1) % k == 0) {
                right[i] = nums[i];
            } else {
                right[i] = Math.max(right[i + 1], nums[i]);
            }
        }
        
        // Build result
        int[] result = new int[n - k + 1];
        for (int i = 0; i <= n - k; i++) {
            // Window max = max(right[i], left[i+k-1])
            result[i] = Math.max(right[i], left[i + k - 1]);
        }
        
        return result;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) — Three passes through array
- **Space Complexity**: O(n) — Two auxiliary arrays
- **Note**: Clever but less intuitive than deque

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | Max Heap | DP | **Monotonic Deque** |
|-------------|-------------|----------|----|--------------------|
| Time complexity | O(n × k) ❌ | O(n log n) ⚠️ | O(n) ✓ | **O(n) ✅** |
| Space complexity | O(1) ✓ | O(n) ❌ | O(n) ⚠️ | **O(k) ✅** |
| Code simplicity | Simple | Medium | Complex | **Medium ✅** |
| Intuitive | ✓ | ✓ | ❌ | **✅** |
| Optimal | ❌ | ⚠️ | ✓ | **✅** |

**Winner**: **Monotonic Deque** — optimal time/space and intuitive!

### Why Monotonic Deque is Optimal?

```
Key insight: Remove useless elements immediately!

Example: Window [5, 3, 2], new element 6 enters
  
  Without optimization:
    Keep all elements: [5, 3, 2, 6]
    Max = 6
    When 5 leaves, still need to scan [3, 2, 6] for max
  
  With monotonic deque:
    When 6 enters:
      6 > 2 → remove 2 (will never be max while 6 is here)
      6 > 3 → remove 3 (same reason)
      6 > 5 → remove 5 (same reason)
      Deque = [6]
    
    When 5 "leaves" (already gone), deque = [6], max = 6
    No extra work needed!

Amortized O(1):
  Each element:
    - Added to deque exactly once: O(1)
    - Removed from deque at most once: O(1)
  
  Total: n additions + n removals = O(n) for entire algorithm
```

### Why Heap is Slower?

```
Heap approach:
  - Add element: O(log n)
  - Remove element: O(log n)
  - Total: O(n log n)
  
Problem: Heap maintains global order (unnecessary!)
  We only need to know current window maximum
  Don't care about elements outside window
  
Heap also stores stale elements:
  Element at index 0 might still be in heap at index 1000
  Wastes space: O(n) instead of O(k)
```

### Why Store Indices Instead of Values?

```
Problem: When to remove front of deque?

With values only:
  deque = [5, 3, 2]
  Window slides, need to remove element that left
  But which element left? Can't tell!
  
  If array has duplicates:
    nums = [5, 3, 5, 2], k = 3
    deque = [5, 5, 2]
    Which 5 to remove? Ambiguous!

With indices:
  deque = [0, 2, 3] (values: [5, 5, 2])
  Window slides from [0,2] to [1,3]
  Check: deque front = 0, window start = 1
  0 < 1? Yes, remove from front!
  
  Clear and unambiguous!
```

---

## Critical Edge Cases & Gotchas

### 1. **k = 1 (Each Element is Own Window)**
```java
Input: nums = [1,3,-1], k = 1
Output: [1,3,-1]
Explanation: Each element is its own maximum.
```

### 2. **k = n (Entire Array is Window)**
```java
Input: nums = [1,3,2,5], k = 4
Output: [5]
Explanation: Only one window containing all elements.
```

### 3. **Decreasing Array**
```java
Input: nums = [5,4,3,2,1], k = 3
Output: [5,4,3]
Explanation: First element of each window is always max.
Deque pattern: First element always at front, others removed immediately.
```

### 4. **Increasing Array**
```java
Input: nums = [1,2,3,4,5], k = 3
Output: [3,4,5]
Explanation: Last element of each window is always max.
Deque pattern: Deque always size 1 (new element removes all previous).
```

### 5. **All Same Elements**
```java
Input: nums = [3,3,3,3], k = 2
Output: [3,3,3]
Explanation: All windows have same maximum.
```

### 6. **Negative Numbers**
```java
Input: nums = [-1,-3,-5,-2], k = 2
Output: [-1,-3,-2]
Explanation: Works with negatives (max is least negative).
```

### 7. **Single Element**
```java
Input: nums = [5], k = 1
Output: [5]
Explanation: Edge case: single element array.
```

### 8. **Large Window at End**
```java
Input: nums = [1,2,3,4,5], k = 4
Output: [4,5]
Explanation: Only 2 windows possible.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Storing Values Instead of Indices**
```java
// WRONG - stores values, can't determine when to remove
Deque<Integer> deque = new LinkedList<>();
deque.offerLast(nums[i]);  // WRONG! Need index, not value
```

**Why wrong**: Can't tell when element leaves window!

**Dry run failure for nums=[1,3,1,2], k=3:**
```
deque = [3, 1] (values)
Window slides from [0,2] to [1,3]
  Need to check if front element (3) is still in window
  But we only have value 3, not its index!
  Index 1 has value 3, is it in window [1,3]? Can't determine!
  
If we had indices:
  deque = [1, 2] (indices with values 3, 1)
  Window [1, 3]: check if index 1 >= 1? Yes, keep it!
```

**Fix**: Store indices
```java
Deque<Integer> deque = new LinkedList<>();
deque.offerLast(i);  // Store index i
```

### ❌ **MISTAKE 2: Wrong Window Boundary Check**
```java
// WRONG - checks if index < i instead of < i - k + 1
while (!deque.isEmpty() && deque.peekFirst() < i) {
    deque.pollFirst();
}
```

**Why wrong**: Removes elements that are still in window!

**Dry run failure for i=3, k=3:**
```
Window: [i-k+1, i] = [3-3+1, 3] = [1, 3]
  Valid indices: 1, 2, 3

Using wrong condition (< i):
  deque front = 2
  2 < 3? Yes, remove!
  But index 2 is in window [1,3]! Should keep!

Correct condition (< i - k + 1):
  2 < 1? No, keep (correct!)
```

**Fix**: Check against window start
```java
while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
    deque.pollFirst();
}
```

### ❌ **MISTAKE 3: Using <= Instead of < for Removal**
```java
// WRONG - removes equal elements, breaks monotonic property
while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
    deque.pollLast();  // WRONG! Should only remove if strictly less
}
```

**Why wrong**: Removes elements that could be max later!

**Dry run failure for nums=[1,3,3,2], k=3:**
```
i=0: deque=[0] (value 1)
i=1: 3 > 1, remove 0, deque=[1] (value 3)
i=2: 3 <= 3? Yes, remove 1! deque=[2] (value 3)
  Window [1,3,3]: max should be first 3 at index 1
  But we removed it! Now deque=[2]
  
When window slides and index 2 leaves:
  deque empty! Lost track of maximum!

Using < (correct):
  3 < 3? No, keep both: deque=[1,2] (values 3,3)
  Both 3's can be max, keep them!
```

**Fix**: Use < not <=
```java
while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
    deque.pollLast();
}
```

### ❌ **MISTAKE 4: Recording Result Too Early**
```java
// WRONG - records result before window is full
for (int i = 0; i < n; i++) {
    // ... deque operations ...
    result[i] = nums[deque.peekFirst()];  // WRONG! Not enough elements yet
}
```

**Why wrong**: First k-1 iterations don't have full window!

**Dry run failure for k=3:**
```
i=0: Window size = 1 (< k), can't report max yet
i=1: Window size = 2 (< k), can't report max yet
i=2: Window size = 3 (= k), NOW can report max
```

**Fix**: Check if window is full
```java
if (i >= k - 1) {
    result[resultIndex++] = nums[deque.peekFirst()];
}
```

### ❌ **MISTAKE 5: Wrong Result Array Size**
```java
// WRONG - allocates wrong size
int[] result = new int[n];  // WRONG! Should be n - k + 1
```

**Why wrong**: Number of windows is n - k + 1, not n!

**Dry run failure for n=5, k=3:**
```
Windows: [0,2], [1,3], [2,4] → 3 windows
Correct size: 5 - 3 + 1 = 3
Wrong size: 5 → wastes space, might cause issues
```

**Fix**: Use correct formula
```java
int[] result = new int[n - k + 1];
```

### ❌ **MISTAKE 6: Not Handling Empty Input**
```java
// WRONG - doesn't check for null or empty
public int[] maxSlidingWindow(int[] nums, int k) {
    // Directly accesses nums[i] without checking
    Deque<Integer> deque = new LinkedList<>();
    for (int i = 0; i < nums.length; i++) {  // NullPointerException if nums is null!
```

**Fix**: Handle edge cases
```java
if (nums == null || nums.length == 0) {
    return new int[0];
}
```

### ❌ **MISTAKE 7: Forgetting to Remove Front When Out of Bounds**
```java
// WRONG - doesn't remove elements outside window
for (int i = 0; i < n; i++) {
    // Missing: check and remove front if out of bounds
    
    while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
        deque.pollLast();
    }
    deque.offerLast(i);
}
```

**Why wrong**: Deque grows unbounded, wrong maximum!

**Dry run failure:**
```
nums = [1,3,1,2], k = 2
i=0: deque=[0]
i=1: deque=[1] (3 > 1, remove 0)
i=2: deque=[1,2] (window [1,2], front=1 still valid)
i=3: deque=[1,2,3] (should remove 1, it's outside window [2,3]!)
  Without removal: front = 1 (value 3)
  But index 1 not in window [2,3]! Wrong answer!
```

**Fix**: Always check and remove front
```java
while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
    deque.pollFirst();
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Each element added | O(1) | offerLast() is O(1) |
| Each element removed from back | O(1) amortized | Each removed at most once |
| Each element removed from front | O(1) | Each removed at most once |
| Query maximum | O(1) | peekFirst() is O(1) |
| **Total** | **O(n)** | Each of n elements: added once, removed at most once |

**Why O(n) not O(n × k)?**
```
Key insight: Amortized analysis!

Each element's lifecycle:
  1. Added to deque once: O(1)
  2. Removed from deque once: O(1)
  
Even though we have while loops:
  - While loop at front: each element removed at most once across ALL iterations
  - While loop at back: each element removed at most once across ALL iterations
  
Total operations:
  n additions + at most n removals = O(n)

Example: nums = [1,2,3,4,5], k=3
  Element 1: added, removed (2 operations)
  Element 2: added, removed (2 operations)  
  Element 3: added, removed (2 operations)
  Element 4: added, removed (2 operations)
  Element 5: added (1 operation)
  
  Total: 9 operations for 5 elements = O(n)
```

### Space Complexity: **O(k)**

| Component | Space | Reason |
|-----------|-------|--------|
| Deque | O(k) | At most k indices (window size) |
| Result array | O(n-k+1) | Output (not counted in space complexity) |
| Variables | O(1) | Counters and pointers |
| **Total** | **O(k)** | Deque size bounded by window size |

**Why deque size ≤ k?**
```
Observation: Deque only contains indices in current window

Proof:
  - We remove front when index < i - k + 1 (outside window)
  - We add current index i
  - Current window: [i - k + 1, i] (size = k)
  - All indices in deque are in range [i - k + 1, i]
  - Maximum possible indices: k
  
Therefore: deque size ≤ k at all times
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `nums = [1,3,-1,-3,5,3,6,7], k = 3`

**Goal:** Find maximum in each sliding window of size 3.

---

**Step 1: Initialize**
```
nums = [1, 3, -1, -3, 5, 3, 6, 7]
k = 3
deque = [] (empty)
result = []
```

---

**Step 2: Process i=0, nums[0]=1**
```
Operations:
  1. Remove front if out of bounds: deque empty, skip
  2. Remove back while nums[back] < nums[0]: deque empty, skip
  3. Add index 0 to back
  4. Window not full (i=0 < k-1=2), don't record result

State:
  deque = [0]
  deque values = [1]
  Window: [1] (size 1)
```

---

**Step 3: Process i=1, nums[1]=3**
```
Operations:
  1. Remove front: deque=[0], 0 < 1-3+1=-1? No, keep
  2. Remove back: nums[0]=1 < nums[1]=3? Yes!
     Remove index 0
     deque empty now
  3. Add index 1
  4. Window not full (i=1 < 2), don't record

State:
  deque = [1]
  deque values = [3]
  Window: [1,3] (size 2)
  
Explanation: 3 > 1, so 1 can never be max while 3 is in window
```

---

**Step 4: Process i=2, nums[2]=-1**
```
Operations:
  1. Remove front: 1 < 0? No, keep
  2. Remove back: nums[1]=3 < nums[2]=-1? No, keep
  3. Add index 2
  4. Window full (i=2 >= 2)! Record max

State:
  deque = [1, 2]
  deque values = [3, -1]
  Window: [1,3,-1] (size 3)
  Max: nums[1] = 3 ✓
  result = [3]
```

---

**Step 5: Process i=3, nums[3]=-3**
```
Operations:
  1. Remove front: 1 < 3-3+1=1? No, keep
  2. Remove back: nums[2]=-1 < nums[3]=-3? No, keep
  3. Add index 3
  4. Record max

State:
  deque = [1, 2, 3]
  deque values = [3, -1, -3]
  Window: [3,-1,-3] (size 3)
  Max: nums[1] = 3 ✓
  result = [3, 3]
```

---

**Step 6: Process i=4, nums[4]=5**
```
Operations:
  1. Remove front: 1 < 4-3+1=2? Yes! Remove index 1
     deque = [2, 3]
  2. Remove back: nums[3]=-3 < nums[4]=5? Yes! Remove index 3
     deque = [2]
     nums[2]=-1 < 5? Yes! Remove index 2
     deque = []
  3. Add index 4
  4. Record max

State:
  deque = [4]
  deque values = [5]
  Window: [-1,-3,5] (size 3)
  Max: nums[4] = 5 ✓
  result = [3, 3, 5]
  
Explanation: 5 is larger than all previous elements, removes them all!
```

---

**Step 7: Process i=5, nums[5]=3**
```
Operations:
  1. Remove front: 4 < 5-3+1=3? No, keep
  2. Remove back: nums[4]=5 < nums[5]=3? No, keep
  3. Add index 5
  4. Record max

State:
  deque = [4, 5]
  deque values = [5, 3]
  Window: [-3,5,3] (size 3)
  Max: nums[4] = 5 ✓
  result = [3, 3, 5, 5]
  
Explanation: 3 < 5, keep both (3 might be max after 5 leaves)
```

---

**Step 8: Process i=6, nums[6]=6**
```
Operations:
  1. Remove front: 4 < 6-3+1=4? No, keep
     Wait, 4 < 4? No, boundary case: keep
  2. Remove back: nums[5]=3 < nums[6]=6? Yes! Remove index 5
     deque = [4]
     nums[4]=5 < 6? Yes! Remove index 4
     deque = []
  3. Add index 6
  4. Record max

State:
  deque = [6]
  deque values = [6]
  Window: [5,3,6] (size 3)
  Max: nums[6] = 6 ✓
  result = [3, 3, 5, 5, 6]
```

---

**Step 9: Process i=7, nums[7]=7**
```
Operations:
  1. Remove front: 6 < 7-3+1=5? No, keep
  2. Remove back: nums[6]=6 < nums[7]=7? Yes! Remove index 6
     deque = []
  3. Add index 7
  4. Record max

State:
  deque = [7]
  deque values = [7]
  Window: [3,6,7] (size 3)
  Max: nums[7] = 7 ✓
  result = [3, 3, 5, 5, 6, 7]
```

---

**Final Result:** `[3, 3, 5, 5, 6, 7]`

### Visual Diagram

```
nums = [1, 3, -1, -3, 5, 3, 6, 7]
        0  1   2   3  4  5  6  7

Window 1: [1, 3, -1]
           --------
           deque=[1,2], max=3

Window 2: [3, -1, -3]
              --------
              deque=[1,2,3], max=3

Window 3: [-1, -3, 5]
               --------
               deque=[4], max=5 (5 removed all!)

Window 4: [-3, 5, 3]
                   --------
                   deque=[4,5], max=5

Window 5: [5, 3, 6]
                  --------
                  deque=[6], max=6 (6 removed all!)

Window 6: [3, 6, 7]
                     --------
                     deque=[7], max=7 (7 removed all!)
```

---

## Comparison of Approaches

| Approach | Time | Space | Optimal | Intuitive | Interview Favorite |
|----------|------|-------|---------|-----------|-------------------|
| Brute Force | O(n × k) | O(1) | ❌ | ✅ | ❌ |
| Max Heap | O(n log n) | O(n) | ⚠️ | ✅ | ⚠️ |
| Dynamic Programming | O(n) | O(n) | ✅ | ❌ | ⚠️ |
| **Monotonic Deque** | **O(n)** | **O(k)** | **✅** | **✅** | **✅** |

**Recommendation**: Use **Monotonic Deque** — optimal, intuitive, and most expected in interviews!

---

## Key Takeaways

1. **Monotonic deque** — maintains decreasing order of potential maximums
2. **Store indices, not values** — track window boundaries and handle duplicates
3. **Remove useless elements immediately** — smaller elements won't be max while larger ones exist
4. **Amortized O(1) per element** — each added once, removed at most once
5. **Window boundary check** — remove front when index < i - k + 1
6. **Use < not <=** — keep equal elements (might be max later)
7. **Record result when i >= k-1** — wait for window to be full

---

## Interview Tips

**What to say in an interview:**

> "This is a fixed sliding window problem where I need to track the maximum efficiently as the window slides. The key insight is that when a larger element enters the window, all smaller elements before it become irrelevant — they can never be the maximum while the larger element is present. I'll use a monotonic deque that maintains indices in decreasing order of their values. The front of the deque always contains the index of the current maximum. For each element, I'll remove indices from the back that have smaller values, and remove from the front if the index is outside the current window. This gives O(n) time because each element is added and removed from the deque at most once, with O(k) space for the deque."

**Key points to mention:**
1. **Monotonic deque** — decreasing order of values
2. **Store indices** — track window boundaries
3. **Remove smaller elements** — they won't be max while larger exists
4. **Amortized O(1)** — each element added/removed once
5. **Complexity** — O(n) time, O(k) space

**If asked about alternatives:**
> "I could use a max heap with lazy deletion, which would be O(n log n) time and O(n) space. The heap stores elements with indices, and I remove stale elements when accessing the maximum. However, the monotonic deque is more efficient because it's O(n) time with only O(k) space, and it's the expected solution for this problem."

**Common Follow-ups:**
- "What if we need minimum instead?" → Use monotonic increasing deque
- "What about both min and max?" → Use two deques (one increasing, one decreasing)
- "Can you optimize space?" → Already optimal at O(k)
- "What if k is very large?" → Still O(n) time, deque won't exceed k elements

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Sliding Window Maximum** | Hard | **Monotonic Deque** | **This problem** |
| Sliding Window Minimum | Medium | Monotonic Deque | Maintain increasing order instead |
| Longest Continuous Subarray With Absolute Diff | Medium | Monotonic Deque × 2 | Track both min and max |
| Shortest Subarray with Sum at Least K | Hard | Monotonic Deque + Prefix Sum | More complex condition |
| Maximum of Minimum Values | Hard | Monotonic Stack | Similar monotonic structure |
| Next Greater Element | Medium | Monotonic Stack | Similar pattern, simpler |
| Largest Rectangle in Histogram | Hard | Monotonic Stack | 2D version of monotonic structure |

**Pattern Progression**:
1. **Basic monotonic stack** (easier) — Next Greater Element
2. **Monotonic deque for sliding window** (this problem) — Sliding Window Maximum
3. **Multiple monotonic deques** (harder) — Track multiple properties simultaneously

---

## Final Pattern Label

✅ **Fixed Sliding Window + Monotonic Deque (Decreasing Order)**

**Remember:** Use a deque to store indices in decreasing order of their values. When a new larger element arrives, remove all smaller elements from the back (they can never be maximum while the larger element exists). Remove from the front when indices go out of the window. The front always contains the current maximum. Each element is added and removed at most once, giving O(n) time with O(k) space!
