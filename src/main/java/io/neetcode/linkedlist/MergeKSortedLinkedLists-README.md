# Merge K Sorted Linked Lists

## Problem Description

**Difficulty**: Hard

You are given an array of `k` linked lists `lists`, where each list is sorted in ascending order.

Return the sorted linked list that is the result of merging all of the individual linked lists.

**Key Concepts:**
- **K Sorted Lists**: Multiple linked lists, each individually sorted
- **Merge All**: Combine into single sorted list
- **Maintain Order**: Result must be fully sorted
- **Optimal Solution**: Better than O(n·k) time complexity
- **In-Place**: Can be done with O(1) extra space (excluding heap)

**Visual Example:**
```
Input: 3 sorted lists
List 1: 1 → 2 → 4
List 2: 1 → 3 → 5
List 3: 3 → 6

Merged Result:
1 → 1 → 2 → 3 → 3 → 4 → 5 → 6

All elements from all lists, sorted!
```

**Recommended Complexity:**
- **Time**: O(n·log k) where n = total nodes, k = number of lists
- **Space**: O(k) for heap, or O(log k) for divide & conquer recursion

---

## Examples

### Example 1 (Main Example):
```
Input: lists = [[1,2,4],[1,3,5],[3,6]]

Output: [1,1,2,3,3,4,5,6]

Explanation:
List 0: 1 → 2 → 4
List 1: 1 → 3 → 5
List 2: 3 → 6

Merging process (Min Heap):
  Min: 1 (from list 0 or 1) → add to result
  Min: 1 (from other list) → add to result
  Min: 2 (from list 0) → add to result
  Min: 3 (from list 1 or 2) → add to result
  Min: 3 (from other list) → add to result
  Min: 4 (from list 0) → add to result
  Min: 5 (from list 1) → add to result
  Min: 6 (from list 2) → add to result

Result: 1 → 1 → 2 → 3 → 3 → 4 → 5 → 6
```

### Example 2 (Empty Array):
```
Input: lists = []

Output: []

Explanation:
No lists to merge
Return null (empty list)
```

### Example 3 (Array with Empty List):
```
Input: lists = [[]]

Output: []

Explanation:
Single empty list
Return null (empty list)
```

### Example 4 (Single List):
```
Input: lists = [[1,2,3]]

Output: [1,2,3]

Explanation:
Only one list, already sorted
Return as-is
```

### Example 5 (Two Lists):
```
Input: lists = [[1,3,5],[2,4,6]]

Output: [1,2,3,4,5,6]

Explanation:
List 0: 1 → 3 → 5
List 1: 2 → 4 → 6

Interleaved merge:
1 (L0) → 2 (L1) → 3 (L0) → 4 (L1) → 5 (L0) → 6 (L1)
```

### Example 6 (Lists of Different Lengths):
```
Input: lists = [[1],[2,3],[4,5,6,7]]

Output: [1,2,3,4,5,6,7]

Explanation:
List 0: 1
List 1: 2 → 3
List 2: 4 → 5 → 6 → 7

All merged together in sorted order
```

### Example 7 (Overlapping Values):
```
Input: lists = [[1,1,2],[1,1,3],[2,2,4]]

Output: [1,1,1,1,2,2,2,3,4]

Explanation:
Multiple duplicate values across lists
All included in result
```

### Example 8 (Negative Numbers):
```
Input: lists = [[-10,-5,0],[−8,−3,2],[−6,1,5]]

Output: [-10,-8,-6,-5,-3,0,1,2,5]

Explanation:
Handle negative numbers correctly
Sort by value, not absolute value
```

### Example 9 (All Same Values):
```
Input: lists = [[5,5],[5,5],[5,5]]

Output: [5,5,5,5,5,5]

Explanation:
All nodes have same value
Order doesn't matter, all included
```

### Example 10 (Large K):
```
Input: lists = [[1],[2],[3],[4],[5]]

Output: [1,2,3,4,5]

Explanation:
Many lists, each with single node
Heap efficiently handles many lists
```

---

## Constraints
- `0 <= lists.length <= 1000` (k lists)
- `0 <= lists[i].length <= 100` (nodes per list)
- `-1000 <= lists[i][j] <= 1000` (node values)
- Each `lists[i]` is sorted in ascending order

**Recommended Complexity**: 
- Time: O(n·log k) where n = total nodes, k = number of lists
- Space: O(k) for min heap, or O(log k) for divide & conquer

---

## Pattern Recognition

**Primary Pattern**: **Min Heap (Priority Queue) with K-Way Merge**

**Why This Pattern?**
- Need to find **minimum among k lists** repeatedly
- **Min Heap** gives minimum in O(log k)
- **K-way merge**: Classic use case for priority queue
- Process **n total nodes**, each heap operation O(log k)
- Total: **O(n·log k)** — optimal!

**Key Insight**: Multi-Source Selection Problem
```
Problem: Choose minimum from k sources repeatedly

Naive approach:
  Scan all k lists to find minimum: O(k) per node
  Process n nodes: O(n·k) total ❌
  Too slow for large k!

Heap approach:
  Min heap maintains k candidates (one per list)
  Extract min: O(log k)
  Insert next from same list: O(log k)
  Process n nodes: O(n·log k) total ✓
  Much better!

Heap optimizes the "find minimum" operation!
```

**Visual: Min Heap Approach**
```
Lists:
  L0: 1 → 4 → 7
  L1: 2 → 5 → 8
  L2: 3 → 6 → 9

Min Heap (stores head of each list):
     1 (L0)
    / \
   2   3
  (L1) (L2)

Step 1: Extract min = 1
  Add 1 to result
  Insert next from L0 (4)
  
Heap:
     2 (L1)
    / \
   4   3
  (L0) (L2)

Step 2: Extract min = 2
  Add 2 to result
  Insert next from L1 (5)

Heap:
     3 (L2)
    / \
   4   5
  (L0) (L1)

Continue until all nodes processed...

Result: 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9 ✓
```

**Why Min Heap is Perfect**:
```
Heap properties:
  - Always maintains minimum at root: O(1) access
  - Insert: O(log k) where k = heap size
  - Extract: O(log k)
  - Perfect for k-way merge!

In our case:
  - Heap size = number of non-empty lists ≤ k
  - Extract min: O(log k)
  - Add next node: O(log k)
  - Process n total nodes: O(n·log k) ✓
```

**Alternative Pattern: Divide & Conquer**
```
Idea: Merge pairs recursively

Round 1: Merge pairs
  Merge(L0, L1), Merge(L2, L3), Merge(L4, L5), ...
  k lists → k/2 lists

Round 2: Merge pairs again
  k/2 lists → k/4 lists

Round 3:
  k/4 lists → k/8 lists

...

log k rounds, each merging n total nodes: O(n·log k) ✓

Also optimal! But more complex to implement.
```

**Comparison: Merge One by One vs Heap vs Divide & Conquer**

```
Merge One by One:
  result = lists[0]
  result = merge(result, lists[1])
  result = merge(result, lists[2])
  ...
  
  First merge: n1 + n2 operations
  Second merge: (n1+n2) + n3 operations
  Third merge: (n1+n2+n3) + n4 operations
  ...
  
  Total: O(n·k) ❌
  Too slow!

Min Heap:
  Heap size ≤ k
  Each of n nodes: O(log k) operations
  Total: O(n·log k) ✓
  Optimal!

Divide & Conquer:
  log k levels
  Each level merges n total nodes
  Total: O(n·log k) ✓
  Optimal!

Both heap and divide & conquer are optimal! ✓
Heap is simpler to implement.
```

**Why Not Just Sort All?**
```
Brute force:
  1. Collect all n nodes in array: O(n)
  2. Sort array: O(n·log n)
  3. Rebuild linked list: O(n)
  Total: O(n·log n)

Compare to heap: O(n·log k)

If k << n (many short lists): log k < log n
  Heap is better! ✓

If k ≈ n (many single-node lists): log k ≈ log n
  Similar performance

Heap leverages the fact that lists are already sorted! ✓
```

**Core Operations**:

**Min Heap Approach**:
```java
1. Create min heap (priority queue)
2. Add all list heads to heap
3. While heap not empty:
   - Extract min from heap
   - Add to result list
   - If min node has next, add next to heap
4. Return result list

Time: O(n·log k)
Space: O(k) for heap
```

**Divide & Conquer Approach**:
```java
1. If lists.length == 0: return null
2. If lists.length == 1: return lists[0]
3. Divide lists into two halves
4. Recursively merge left half
5. Recursively merge right half
6. Merge two results
7. Return merged list

Time: O(n·log k)
Space: O(log k) recursion stack
```

**Related Patterns**:
1. **Merge Two Sorted Lists** — Building block (O(n))
2. **Merge K Sorted Lists** — This problem (O(n·log k))
3. **K-way Merge** — General pattern for merging k streams
4. **Merge Sort** — Divide & conquer approach similar

---

## Algorithm & Approach

### Core Insight

**Why Min Heap Approach Works:**
```
Key observations:
  1. Each list already sorted (don't need to sort again)
  2. Minimum of all lists is at one of the k heads
  3. Min heap finds minimum among k items in O(log k)
  4. After extracting min, replace with next from same list
  5. Process all n nodes with O(log k) per node
  6. Total: O(n·log k) — optimal! ✓
```

**The Optimal Strategy**:
```
Data structure:
  - Min Heap (PriorityQueue) storing ListNode objects
  - Comparator: compare node values
  - Heap size ≤ k (number of lists)

Algorithm:
  1. Add all non-empty list heads to heap
  2. Dummy head for result list
  3. While heap not empty:
     - Extract minimum node
     - Add to result list
     - If node has next, add next to heap
  4. Return dummy.next

Time: O(n·log k) where n = total nodes, k = number of lists
Space: O(k) for heap
```

### Step-by-Step Algorithm

---

#### **Approach 1: Min Heap (Priority Queue) - OPTIMAL**

**Core Idea**:
- Use min heap to maintain k candidates (current head of each list)
- Extract minimum, add to result, insert next from same list
- Heap operations O(log k), process n nodes → O(n·log k)

**Algorithm**
```java
mergeKLists(ListNode[] lists):
    if lists == null or lists.length == 0:
        return null
    
    // Create min heap with custom comparator
    PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
        (a, b) -> a.val - b.val
    )
    
    // Add all list heads to heap
    for list in lists:
        if list != null:
            minHeap.offer(list)
    
    // Dummy head for result
    dummy = new ListNode(0)
    current = dummy
    
    // Extract min, add to result, insert next
    while !minHeap.isEmpty():
        minNode = minHeap.poll()
        current.next = minNode
        current = current.next
        
        if minNode.next != null:
            minHeap.offer(minNode.next)
    
    return dummy.next
```

**Complete Code Implementation (Min Heap)**
```java
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // Min heap with custom comparator
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
            (a, b) -> a.val - b.val
        );
        
        // Add all list heads to heap
        for (ListNode list : lists) {
            if (list != null) {
                minHeap.offer(list);
            }
        }
        
        // Dummy head for result
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Process all nodes
        while (!minHeap.isEmpty()) {
            // Extract minimum
            ListNode minNode = minHeap.poll();
            
            // Add to result
            current.next = minNode;
            current = current.next;
            
            // Add next from same list to heap
            if (minNode.next != null) {
                minHeap.offer(minNode.next);
            }
        }
        
        return dummy.next;
    }
}
```

**Example Walkthrough (Min Heap)**

Input: `lists = [[1,4,5],[1,3,4],[2,6]]`

```
Lists:
  L0: 1 → 4 → 5
  L1: 1 → 3 → 4
  L2: 2 → 6
```

**Step 1: Initialize**
```
Create min heap
Add all heads to heap:
  minHeap.offer(L0[1])
  minHeap.offer(L1[1])
  minHeap.offer(L2[2])

Heap (min at top):
     1 (L0)
    / \
   1   2
  (L1) (L2)

Result: dummy → (empty)
```

**Step 2: Extract min = 1 (L0)**
```
minNode = minHeap.poll() = 1 (L0)

Add to result:
  dummy → 1

minNode.next = 4 (L0)
minHeap.offer(4 (L0))

Heap:
     1 (L1)
    / \
   2   4
  (L2) (L0)

Result: dummy → 1
```

**Step 3: Extract min = 1 (L1)**
```
minNode = minHeap.poll() = 1 (L1)

Add to result:
  dummy → 1 → 1

minNode.next = 3 (L1)
minHeap.offer(3 (L1))

Heap:
     2 (L2)
    / \
   3   4
  (L1) (L0)

Result: dummy → 1 → 1
```

**Step 4: Extract min = 2 (L2)**
```
minNode = minHeap.poll() = 2 (L2)

Add to result:
  dummy → 1 → 1 → 2

minNode.next = 6 (L2)
minHeap.offer(6 (L2))

Heap:
     3 (L1)
    / \
   4   6
  (L0) (L2)

Result: dummy → 1 → 1 → 2
```

**Step 5: Extract min = 3 (L1)**
```
minNode = minHeap.poll() = 3 (L1)

Add to result:
  dummy → 1 → 1 → 2 → 3

minNode.next = 4 (L1)
minHeap.offer(4 (L1))

Heap:
     4 (L0)
    / \
   4   6
  (L1) (L2)

Result: dummy → 1 → 1 → 2 → 3
```

**Step 6: Extract min = 4 (L0 or L1)**
```
minNode = minHeap.poll() = 4 (L0)

Add to result:
  dummy → 1 → 1 → 2 → 3 → 4

minNode.next = 5 (L0)
minHeap.offer(5 (L0))

Heap:
     4 (L1)
    / \
   5   6
  (L0) (L2)

Result: dummy → 1 → 1 → 2 → 3 → 4
```

**Step 7: Extract min = 4 (L1)**
```
minNode = minHeap.poll() = 4 (L1)

Add to result:
  dummy → 1 → 1 → 2 → 3 → 4 → 4

minNode.next = null
Don't add to heap

Heap:
     5 (L0)
      \
       6
      (L2)

Result: dummy → 1 → 1 → 2 → 3 → 4 → 4
```

**Step 8: Extract min = 5 (L0)**
```
minNode = minHeap.poll() = 5 (L0)

Add to result:
  dummy → 1 → 1 → 2 → 3 → 4 → 4 → 5

minNode.next = null
Don't add to heap

Heap:
     6 (L2)

Result: dummy → 1 → 1 → 2 → 3 → 4 → 4 → 5
```

**Step 9: Extract min = 6 (L2)**
```
minNode = minHeap.poll() = 6 (L2)

Add to result:
  dummy → 1 → 1 → 2 → 3 → 4 → 4 → 5 → 6

minNode.next = null
Don't add to heap

Heap: (empty)

Result: dummy → 1 → 1 → 2 → 3 → 4 → 4 → 5 → 6
```

**Step 10: Heap empty, return**
```
return dummy.next = 1 → 1 → 2 → 3 → 4 → 4 → 5 → 6 ✓
```

---

#### **Approach 2: Divide & Conquer - OPTIMAL ALTERNATIVE**

**Core Idea**:
- Recursively divide lists into two halves
- Merge each half
- Merge the two results
- log k levels, each merges n nodes → O(n·log k)

**Algorithm**
```java
mergeKLists(ListNode[] lists):
    if lists == null or lists.length == 0:
        return null
    
    return mergeRange(lists, 0, lists.length - 1)

mergeRange(lists, left, right):
    if left == right:
        return lists[left]
    
    if left > right:
        return null
    
    mid = left + (right - left) / 2
    
    leftMerged = mergeRange(lists, left, mid)
    rightMerged = mergeRange(lists, mid + 1, right)
    
    return mergeTwoLists(leftMerged, rightMerged)

mergeTwoLists(l1, l2):
    // Standard merge two sorted lists
    // (See Merge Two Sorted Lists problem)
```

**Complete Code Implementation (Divide & Conquer)**
```java
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        return mergeRange(lists, 0, lists.length - 1);
    }
    
    private ListNode mergeRange(ListNode[] lists, int left, int right) {
        // Base case: single list
        if (left == right) {
            return lists[left];
        }
        
        // Base case: no lists
        if (left > right) {
            return null;
        }
        
        // Divide
        int mid = left + (right - left) / 2;
        
        // Conquer
        ListNode leftMerged = mergeRange(lists, left, mid);
        ListNode rightMerged = mergeRange(lists, mid + 1, right);
        
        // Combine
        return mergeTwoLists(leftMerged, rightMerged);
    }
    
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // Dummy head
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Merge
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        
        // Append remaining
        if (l1 != null) {
            current.next = l1;
        }
        if (l2 != null) {
            current.next = l2;
        }
        
        return dummy.next;
    }
}
```

**Complexity Analysis**
- **Min Heap**: O(n·log k) time, O(k) space
- **Divide & Conquer**: O(n·log k) time, O(log k) space

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Complexity | Recommended |
|----------|------|-------|------------|-------------|
| **Min Heap** | **O(n·log k)** | **O(k)** | **Medium** | **Yes ✅** |
| **Divide & Conquer** | **O(n·log k)** | **O(log k)** | **Medium** | **Yes ✅** |
| Merge One by One | O(n·k) | O(1) | Low | No |
| Brute Force (Sort All) | O(n·log n) | O(n) | Low | No |
| Scan All K Lists | O(n·k) | O(1) | Low | No |

**Winner**: **Min Heap or Divide & Conquer** — both optimal O(n·log k)!

### Why Min Heap is Optimal

```
Problem: Find minimum among k candidates repeatedly

Without heap:
  Scan all k list heads: O(k)
  Do this n times: O(n·k) ❌
  Too slow!

With min heap:
  Heap size = k
  Extract min: O(log k)
  Insert next: O(log k)
  Do this n times: O(n·log k) ✓
  Much better!

Heap reduces "find min" from O(k) to O(log k)! ✓
```

### Why Divide & Conquer is Optimal

```
Merge one by one:
  Merge 1st and 2nd: n1 + n2 ops
  Merge result and 3rd: (n1+n2) + n3 ops
  Merge result and 4th: (n1+n2+n3) + n4 ops
  ...
  Total: n + 2n + 3n + ... + kn ≈ O(n·k²/2) = O(n·k) ❌

Divide & conquer:
  Level 1: k lists → k/2 lists (merge n nodes)
  Level 2: k/2 lists → k/4 lists (merge n nodes)
  ...
  Level log k: 1 list (merge n nodes)
  
  Total: log k levels × n nodes = O(n·log k) ✓
  
Each level merges all n nodes, but only log k levels! ✓
```

### Why Not Brute Force Sort?

```
Brute force:
  Collect all nodes: O(n)
  Sort: O(n·log n)
  Rebuild: O(n)
  Total: O(n·log n)

Min heap: O(n·log k)

If k << n (few long lists):
  log k << log n
  Heap much better! ✓

If k ≈ n (many short lists):
  log k ≈ log n
  Similar, but heap leverages sorted property ✓

Heap is always as good or better! ✓
```

### Why Heap Over Divide & Conquer?

```
Both are O(n·log k), so why choose heap?

Heap advantages:
  ✓ Simpler to implement
  ✓ Iterative (no recursion overhead)
  ✓ More intuitive (clear min selection)
  ✓ Space O(k) is acceptable

Divide & Conquer advantages:
  ✓ Better space O(log k) recursion
  ✓ No external data structure needed
  ✓ Cache-friendly (merges contiguous lists)

Both are great! Heap is more common in interviews. ✓
```

### Why This is Optimal

```
Time complexity:
  Process n total nodes
  Each node: O(log k) heap operations
  Total: O(n·log k) ✓
  
Can we do better?
  Must process all n nodes: Ω(n)
  Must compare across k lists: Ω(log k) per node
  Lower bound: Ω(n·log k)
  
Our solution matches lower bound: OPTIMAL! ✓

Space complexity:
  Heap: O(k) for k candidates
  Minimal for the task! ✓

Best possible solution! ✓
```

---

## Critical Edge Cases & Gotchas

### 1. **Empty Input Array**
```java
lists = []
// Should return null
```

### 2. **Array with Empty Lists**
```java
lists = [[],[],[]]
// All empty lists
// Should return null
```

### 3. **Single List**
```java
lists = [[1,2,3]]
// Only one list
// Return as-is (no merging needed)
```

### 4. **Two Lists (Classic Merge)**
```java
lists = [[1,3,5],[2,4,6]]
// Reduces to merge two sorted lists
// Should work correctly
```

### 5. **Lists of Different Lengths**
```java
lists = [[1],[1,2],[1,2,3],[1,2,3,4]]
// Different lengths
// Some lists exhaust early
// Handle null checks properly
```

### 6. **All Same Values**
```java
lists = [[5,5],[5,5],[5,5]]
// All nodes have value 5
// Order doesn't matter, all should be included
```

### 7. **Negative Numbers**
```java
lists = [[-10,-5],[−8,-3],[−6,1]]
// Negative values valid
// Compare correctly (not absolute value)
```

### 8. **Single Node Lists**
```java
lists = [[1],[2],[3],[4],[5]]
// Many lists, one node each
// Heap handles efficiently
```

### 9. **Very Long List with Short Lists**
```java
lists = [[1,2,3,...,1000],[5],[10]]
// One very long list
// Other lists short
// Should handle efficiently
```

### 10. **Null Pointers in Array**
```java
lists = [[1,2],null,[3,4]]
// Null pointer in array
// Skip null lists when adding to heap
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Wrong Comparator (Comparing Nodes, Not Values)**
```java
// WRONG - comparing node references, not values
PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
    (a, b) -> a.compareTo(b)  // ❌ ListNode doesn't implement Comparable
);
```

**Why wrong**: Nodes aren't comparable by default!

**Dry run failure:**
```
Tries to compare node objects directly
ClassCastException or compile error ❌
```

**Fix**: Compare values
```java
PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
    (a, b) -> a.val - b.val  ✓
);
```

### ❌ **MISTAKE 2: Integer Overflow in Comparator**
```java
// WRONG - can overflow for large values
PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
    (a, b) -> a.val - b.val  // ❌ Can overflow!
);
```

**Why wrong**: Subtraction can overflow!

**Issue:**
```
a.val = 1000
b.val = -1000
a.val - b.val = 2000 ✓

But with larger range:
a.val = Integer.MAX_VALUE = 2147483647
b.val = -1000
a.val - b.val = 2147484647 (overflow!) ❌

Wraps to negative, wrong comparison!
```

**Fix**: Use Integer.compare
```java
PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
    (a, b) -> Integer.compare(a.val, b.val)  ✓
);
```

### ❌ **MISTAKE 3: Adding Null Lists to Heap**
```java
// WRONG - not checking for null
for (ListNode list : lists) {
    minHeap.offer(list);  // ❌ Might add null!
}
```

**Why wrong**: Null lists cause issues in heap!

**Dry run failure:**
```
lists = [[1,2],null,[3,4]]

Add all to heap:
  minHeap.offer(1) ✓
  minHeap.offer(null) ❌ NullPointerException in comparator!
```

**Fix**: Check for null
```java
for (ListNode list : lists) {
    if (list != null) {  ✓
        minHeap.offer(list);
    }
}
```

### ❌ **MISTAKE 4: Forgetting to Add Next Node to Heap**
```java
// WRONG - not adding next to heap
while (!minHeap.isEmpty()) {
    ListNode minNode = minHeap.poll();
    current.next = minNode;
    current = current.next;
    // Missing: minHeap.offer(minNode.next); ❌
}
```

**Why wrong**: Only processes first node from each list!

**Dry run failure:**
```
Lists:
  L0: 1 → 2 → 3
  L1: 4 → 5 → 6

Heap: [1, 4]

Extract 1, DON'T add 2:
  Result: 1
  Heap: [4]

Extract 4, DON'T add 5:
  Result: 1 → 4
  Heap: []

Final: 1 → 4 ❌ (missing 2, 3, 5, 6!)
```

**Fix**: Add next to heap
```java
while (!minHeap.isEmpty()) {
    ListNode minNode = minHeap.poll();
    current.next = minNode;
    current = current.next;
    
    if (minNode.next != null) {  ✓
        minHeap.offer(minNode.next);
    }
}
```

### ❌ **MISTAKE 5: Not Using Dummy Head**
```java
// WRONG - no dummy head
ListNode result = null;  // ❌
ListNode current = null;

while (!minHeap.isEmpty()) {
    ListNode minNode = minHeap.poll();
    if (result == null) {  // Special case ❌
        result = minNode;
        current = result;
    } else {
        current.next = minNode;
        current = current.next;
    }
    // ...
}

return result;
```

**Why wrong**: Unnecessary complexity!

**Issue:**
```
Need special case for first node
More code, more bugs ❌
```

**Fix**: Use dummy head
```java
ListNode dummy = new ListNode(0);  ✓
ListNode current = dummy;

while (!minHeap.isEmpty()) {
    ListNode minNode = minHeap.poll();
    current.next = minNode;
    current = current.next;
    // ...
}

return dummy.next;  ✓
```

### ❌ **MISTAKE 6: Modifying Original Lists Incorrectly**
```java
// WRONG - breaking links prematurely
while (!minHeap.isEmpty()) {
    ListNode minNode = minHeap.poll();
    current.next = minNode;
    current = current.next;
    current.next = null;  // ❌ Breaks link to rest of list!
    
    if (minNode.next != null) {
        minHeap.offer(minNode.next);  // But we just set it to null! ❌
    }
}
```

**Why wrong**: Breaks links before saving next!

**Dry run failure:**
```
minNode = 1 → 2 → 3

current.next = minNode (1 → 2 → 3) ✓
current = current.next (now at 1)
current.next = null (1 → null) ❌
  Lost reference to 2 → 3!

minNode.next is now null ❌
Can't add next to heap!
```

**Fix**: Don't break links (or save next first)
```java
while (!minHeap.isEmpty()) {
    ListNode minNode = minHeap.poll();
    ListNode next = minNode.next;  // Save first ✓
    
    current.next = minNode;
    current = current.next;
    
    if (next != null) {
        minHeap.offer(next);
    }
}
```

### ❌ **MISTAKE 7: Not Handling Empty Lists Array**
```java
// WRONG - no check for empty array
public ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
        (a, b) -> a.val - b.val  // ❌ Fails if lists.length == 0!
    );
    // ...
}
```

**Why wrong**: PriorityQueue needs initial capacity > 0!

**Issue:**
```
lists = []
lists.length = 0

new PriorityQueue<>() with comparator
  If no initial capacity, uses default (11)
  Actually might work, but better to check
  
Better: return null early for empty input
```

**Fix**: Check for empty input
```java
public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) {  ✓
        return null;
    }
    // ...
}
```

### ❌ **MISTAKE 8: Off-by-One in Divide & Conquer**
```java
// WRONG - incorrect mid calculation
int mid = (left + right) / 2;  // Can overflow! ❌

ListNode leftMerged = mergeRange(lists, left, mid - 1);  // ❌ Wrong bounds!
ListNode rightMerged = mergeRange(lists, mid, right);
```

**Why wrong**: Incorrect mid, overflow potential!

**Dry run failure:**
```
left = 0, right = 3

mid = (0 + 3) / 2 = 1

Left: [0, mid-1] = [0, 0] ✓
Right: [mid, right] = [1, 3] ✓

But what if left = Integer.MAX_VALUE - 1, right = Integer.MAX_VALUE?
left + right overflows! ❌
```

**Fix**: Proper mid calculation
```java
int mid = left + (right - left) / 2;  ✓

ListNode leftMerged = mergeRange(lists, left, mid);  ✓
ListNode rightMerged = mergeRange(lists, mid + 1, right);  ✓
```

### ❌ **MISTAKE 9: Inefficient Heap Size**
```java
// WRONG - heap size could be huge
PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
    lists.length  // ❌ Might allocate too much if many null lists
);
```

**Why wrong**: Wastes memory if many null lists!

**Issue:**
```
lists.length = 1000
But only 10 non-null lists

Heap size 1000 is wasteful ❌
Only need size 10

Not a bug, just inefficient
```

**Better**: Let heap grow dynamically
```java
PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
    (a, b) -> Integer.compare(a.val, b.val)
);
// No initial capacity, grows as needed ✓
```

### ❌ **MISTAKE 10: Returning Wrong Node**
```java
// WRONG - returning dummy instead of dummy.next
ListNode dummy = new ListNode(0);
ListNode current = dummy;

while (!minHeap.isEmpty()) {
    // ... merge logic ...
}

return dummy;  // ❌ Returning dummy node!
```

**Why wrong**: Dummy is placeholder, not part of result!

**Issue:**
```
Result list: dummy → 1 → 2 → 3

Should return 1 → 2 → 3
But returns dummy → 1 → 2 → 3 ❌

Dummy (value 0) shouldn't be in result!
```

**Fix**: Return dummy.next
```java
return dummy.next;  ✓
```

---

## Complexity Analysis

### Time Complexity: **O(n·log k)**

```
Where:
  n = total number of nodes across all lists
  k = number of lists

Min Heap Approach:
  1. Add k list heads to heap: O(k·log k)
  2. Process n nodes:
     - Extract min from heap: O(log k)
     - Add next to heap: O(log k)
     - Per node: O(log k)
     - Total for n nodes: O(n·log k)
  
  Total: O(k·log k) + O(n·log k) = O(n·log k)
  (Since n ≥ k typically, n·log k dominates)

Divide & Conquer Approach:
  1. Recursion depth: log k levels
  2. Each level merges n total nodes: O(n)
  3. Total: O(n·log k)

Both approaches: O(n·log k) ✓
```

**Detailed Analysis (Min Heap)**:
```
Initialize heap: O(k)
  Add k non-null list heads
  Each insertion: O(log k)
  Total: O(k·log k)

Process all nodes: O(n·log k)
  For each of n nodes:
    - poll() from heap: O(log k)
    - offer() to heap: O(log k)
  
  Total: n × O(log k) = O(n·log k)

Overall: O(k·log k + n·log k) = O(n·log k)
```

**Why O(n·log k) is Optimal**:
```
Lower bound analysis:
  - Must process all n nodes: Ω(n)
  - Must maintain order among k lists
  - Minimum comparisons needed: Ω(log k) per node
  - Total lower bound: Ω(n·log k)

Our solution: O(n·log k)

Matches lower bound: OPTIMAL! ✓
```

### Space Complexity

**Min Heap Approach: O(k)**
```
Space used:
  - Min heap: O(k) for k list heads
  - Dummy node: O(1)
  - Current pointer: O(1)
  
Total: O(k)

Note: Result list is not counted as extra space
  (it's the output, must be created anyway)
```

**Divide & Conquer Approach: O(log k)**
```
Space used:
  - Recursion stack: O(log k) depth
  - Each frame: O(1) local variables
  
Total: O(log k)

Divide & conquer has better space! ✓
```

**Space Optimal**:
```
Can we do O(1)?
  - Merge one by one: O(1) space
  - But O(n·k) time ❌
  
For O(n·log k) time:
  - Need Ω(log k) space (heap or recursion)
  - O(k) or O(log k) is optimal for this time complexity ✓
```

### Comparison of Approaches

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| **Min Heap** | **O(n·log k)** | **O(k)** | **Optimal time, simple** |
| **Divide & Conquer** | **O(n·log k)** | **O(log k)** | **Optimal time & space** |
| Merge One by One | O(n·k) | O(1) | Too slow |
| Brute Force Sort | O(n·log n) | O(n) | Doesn't leverage sorted |
| Scan All Lists | O(n·k) | O(1) | Too slow |

**Winner**: **Min Heap or Divide & Conquer** — both optimal!

---

## Visualization

### Complete Example Walkthrough (Min Heap)

**Input:** `lists = [[1,4,7],[2,5,8],[3,6,9]]`

```
Initial Lists:
  L0: 1 → 4 → 7
  L1: 2 → 5 → 8
  L2: 3 → 6 → 9
```

---

**Step 1: Initialize Heap**
```
Add all heads to heap:
  minHeap.offer(1 from L0)
  minHeap.offer(2 from L1)
  minHeap.offer(3 from L2)

Heap (min at root):
     1 (L0)
    / \
   2   3
  (L1) (L2)

Result: dummy → (empty)
```

---

**Step 2: Extract 1**
```
minNode = minHeap.poll() = 1 (L0)

Add to result:
  dummy → 1

Next from L0 = 4
minHeap.offer(4)

Heap:
     2 (L1)
    / \
   3   4
  (L2) (L0)

Result: dummy → 1
```

---

**Step 3: Extract 2**
```
minNode = minHeap.poll() = 2 (L1)

Add to result:
  dummy → 1 → 2

Next from L1 = 5
minHeap.offer(5)

Heap:
     3 (L2)
    / \
   4   5
  (L0) (L1)

Result: dummy → 1 → 2
```

---

**Step 4: Extract 3**
```
minNode = minHeap.poll() = 3 (L2)

Add to result:
  dummy → 1 → 2 → 3

Next from L2 = 6
minHeap.offer(6)

Heap:
     4 (L0)
    / \
   5   6
  (L1) (L2)

Result: dummy → 1 → 2 → 3
```

---

**Step 5: Extract 4**
```
minNode = minHeap.poll() = 4 (L0)

Add to result:
  dummy → 1 → 2 → 3 → 4

Next from L0 = 7
minHeap.offer(7)

Heap:
     5 (L1)
    / \
   6   7
  (L2) (L0)

Result: dummy → 1 → 2 → 3 → 4
```

---

**Step 6: Extract 5**
```
minNode = minHeap.poll() = 5 (L1)

Add to result:
  dummy → 1 → 2 → 3 → 4 → 5

Next from L1 = 8
minHeap.offer(8)

Heap:
     6 (L2)
    / \
   7   8
  (L0) (L1)

Result: dummy → 1 → 2 → 3 → 4 → 5
```

---

**Step 7: Extract 6**
```
minNode = minHeap.poll() = 6 (L2)

Add to result:
  dummy → 1 → 2 → 3 → 4 → 5 → 6

Next from L2 = 9
minHeap.offer(9)

Heap:
     7 (L0)
    / \
   8   9
  (L1) (L2)

Result: dummy → 1 → 2 → 3 → 4 → 5 → 6
```

---

**Step 8: Extract 7**
```
minNode = minHeap.poll() = 7 (L0)

Add to result:
  dummy → 1 → 2 → 3 → 4 → 5 → 6 → 7

Next from L0 = null (end of list)
Don't add to heap

Heap:
     8 (L1)
      \
       9
      (L2)

Result: dummy → 1 → 2 → 3 → 4 → 5 → 6 → 7
```

---

**Step 9: Extract 8**
```
minNode = minHeap.poll() = 8 (L1)

Add to result:
  dummy → 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8

Next from L1 = null
Don't add to heap

Heap:
     9 (L2)

Result: dummy → 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
```

---

**Step 10: Extract 9**
```
minNode = minHeap.poll() = 9 (L2)

Add to result:
  dummy → 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9

Next from L2 = null
Don't add to heap

Heap: (empty)

Result: dummy → 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9
```

---

**Step 11: Return**
```
Heap is empty, exit loop

return dummy.next = 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9 ✓

All nodes from all lists, fully sorted! ✓
```

---

### Divide & Conquer Visualization

**Input:** `lists = [L0, L1, L2, L3]` (4 lists)

```
Level 0 (Initial):
  [L0, L1, L2, L3]

Level 1 (Divide):
  [L0, L1]  [L2, L3]
   /    \    /    \
  L0   L1   L2   L3

Level 2 (Merge pairs):
  merge(L0, L1) = M01
  merge(L2, L3) = M23
  
  [M01, M23]

Level 3 (Merge result):
  merge(M01, M23) = Final

Return: Final

Total levels: log k = log 4 = 2
Each level merges n total nodes
Total time: O(n·log k) ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| **Min Heap** | **O(n·log k)** | **O(k)** | Simple, intuitive, optimal time | Extra space for heap |
| **Divide & Conquer** | **O(n·log k)** | **O(log k)** | Optimal time & space | More complex |
| Merge One by One | O(n·k) | O(1) | Simple, no extra space | Too slow |
| Brute Force Sort | O(n·log n) | O(n) | Very simple | Doesn't leverage sorted, extra space |
| Scan All Lists | O(n·k) | O(1) | No extra structure | Too slow |

**Winner**: **Min Heap** (simple) or **Divide & Conquer** (optimal space)

**When to Use Each:**
- **Interview**: Min Heap (simpler to code, easier to explain)
- **Production**: Divide & Conquer (better space, no external structure)
- **Simple cases**: Merge one by one (k = 2 or 3)

---

## Key Takeaways

1. **K-way merge** — min heap perfect for multi-source selection
2. **Min heap** — O(log k) find min among k candidates
3. **Process n nodes** — each with O(log k) → O(n·log k) total
4. **Heap stores heads** — one candidate per list
5. **Extract and replace** — poll min, offer next from same list
6. **Dummy head** — simplifies list building
7. **Check null** — both null lists and null next pointers
8. **Comparator** — use Integer.compare to avoid overflow
9. **Divide & conquer** — alternative with O(log k) space
10. **Optimal** — O(n·log k) matches lower bound

---

## Interview Tips

**What to say in an interview:**

> "To merge k sorted linked lists optimally, I'll use a min heap approach. The key insight is that the minimum element across all lists is always at the head of one of the lists. Instead of scanning all k lists repeatedly, which would be O(k) per node, I'll use a min heap to maintain the current head of each non-empty list. The heap gives me the minimum in O(log k) time.
>
> I'll initialize a priority queue with a custom comparator that compares node values. I'll add all non-null list heads to the heap. Then I'll use a dummy head for the result list and repeatedly extract the minimum node from the heap, add it to the result, and if that node has a next pointer, add the next node to the heap. This continues until the heap is empty.
>
> Since I process n total nodes and each heap operation is O(log k), the total time complexity is O(n log k), which is optimal. The space complexity is O(k) for the heap storing at most k nodes at once. An alternative approach is divide and conquer, which also achieves O(n log k) time but with O(log k) space for the recursion stack. Both are optimal, but the heap approach is simpler to implement."

**Key points to mention:**
1. **Min heap** for efficient k-way merge
2. **Store list heads** in heap (one per list)
3. **Extract min** in O(log k), process n nodes → O(n·log k)
4. **Custom comparator** for node values
5. **Check null** when adding to heap
6. **Dummy head** for clean list building
7. **Add next** to heap after extracting
8. **Alternative**: divide & conquer also optimal
9. **Time**: O(n·log k), **Space**: O(k)
10. **Optimal**: matches lower bound

**Common Follow-ups:**
- "Why not merge one by one?" → O(n·k) too slow, heap reduces to O(n·log k)
- "Can you do it without extra space?" → Divide & conquer uses O(log k) recursion, but same time complexity
- "What if k is very large?" → Heap still efficient, O(log k) grows slowly
- "What if lists have different lengths?" → Heap handles naturally, adds next from same list
- "Can you do better than O(n·log k)?" → No, this is optimal (matches lower bound)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| Merge Two Sorted Lists | Easy | Two Pointers | Only 2 lists (O(n)) |
| **Merge K Sorted Lists** | Hard | **Min Heap / D&C** | **This problem** |
| Merge Sorted Array | Easy | Two Pointers | Arrays instead of lists |
| Kth Smallest in Sorted Matrix | Medium | Min Heap | 2D matrix k-way merge |
| Find K Pairs with Smallest Sums | Medium | Min Heap | K-way merge variant |
| Smallest Range Covering K Lists | Hard | Min Heap + Sliding Window | K-way merge with range |

**Pattern Progression**:
1. **Merge Two Sorted Lists** — Base case (O(n))
2. **Merge K Sorted Lists** (this) — K-way with heap (O(n·log k))
3. **Kth Smallest in Matrix** — 2D k-way merge
4. **Smallest Range** — K-way merge with constraint

---

## Final Pattern Label

✅ **K-Way Merge with Min Heap (Priority Queue)**

**Remember:** This is a **k-way merge** problem requiring efficient selection of minimum among k candidates. Use **Min Heap (PriorityQueue)** with custom comparator `(a,b) → Integer.compare(a.val, b.val)` to compare node values. **Initialize heap** with all non-null list heads (check `list != null` before adding). Use **dummy head** for clean result list building. **Main loop**: while heap not empty, (1) poll minimum node, (2) add to result list, (3) if min node has next, offer next to heap. **Time complexity**: O(n·log k) where n = total nodes, k = number of lists (process n nodes, each with O(log k) heap operation). **Space complexity**: O(k) for heap storing at most k nodes. **Alternative**: Divide & Conquer merging pairs recursively achieves O(n·log k) time with O(log k) space but more complex. **Common mistakes**: not checking null lists before adding to heap, wrong comparator (subtraction can overflow, use Integer.compare), forgetting to add next node to heap, not using dummy head, returning dummy instead of dummy.next. **Critical insight**: heap reduces "find min among k" from O(k) to O(log k), making overall O(n·log k) optimal. Pattern: **k-way merge** problems → use min heap for efficient multi-source selection! ✓
