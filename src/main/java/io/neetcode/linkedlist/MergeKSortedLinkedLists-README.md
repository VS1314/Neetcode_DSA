# Merge K Sorted Linked Lists

## Problem Description

**Difficulty**: Hard

You are given an array of `k` linked lists `lists`, where each list is sorted in ascending order.

Merge all the linked lists into one sorted linked list and return the head of the merged linked list.

## Examples

### Example 1:
```
Input: lists = [[1,2,4],[1,3,5],[3,6]]
Output: [1,1,2,3,3,4,5,6]
Explanation: The linked-lists are:
[
  1->2->4,
  1->3->5,
  3->6
]
merging them into one sorted list:
1->1->2->3->3->4->5->6
```

### Example 2:
```
Input: lists = []
Output: []
```

### Example 3:
```
Input: lists = [[]]
Output: []
```

## Constraints
- k == lists.length
- 0 <= k <= 10,000
- 0 <= lists[i].length <= 100
- -1000 <= lists[i][j] <= 1000
- Each `lists[i]` is sorted in ascending order
- At most 10,000 total nodes across all lists

---

## Pattern Recognition

**Primary Pattern**: **Divide & Conquer / Min Heap (K-way Merge)**

**Why This Pattern?**
- Multiple sorted lists need to be merged
- Each list is already sorted - leverage this property
- Need to repeatedly pick the smallest element among k candidates
- Classic k-way merge problem

**Key Insight**: When merging k sorted lists, we can either:
1. Use Divide & Conquer (like merge sort) - O(n log k) time, O(1) space
2. Use Min Heap to track k smallest candidates - O(n log k) time, O(k) space

**Related Patterns**:
1. **Merge Two Sorted Lists** - Building block for divide & conquer
2. **Merge Sort** - Same divide & conquer principle
3. **Priority Queue** - Min heap for efficient selection
4. **K-way Merge** - General pattern for multiple sorted sequences

---

## Algorithm & Approach

### Core Insight
Since each individual list is sorted, we don't need to sort everything from scratch. We can leverage the sorted property to merge efficiently.

**Key Observations:**
- Brute force (collect all, sort): O(n log n) - doesn't use sorted property
- Sequential merge: O(n*k) - inefficient, traverses result multiple times
- **Divide & Conquer**: O(n log k) - optimal, balances work evenly
- **Min Heap**: O(n log k) - optimal, always picks smallest among k

### Step-by-Step Algorithm

#### **Approach 1: Brute Force (Collect All & Sort)**
```
1. Traverse all k lists and collect all node values into an array
2. Sort the array using any sorting algorithm
3. Create a new linked list from the sorted array
4. Return the head
```

**Code Implementation**
```java
public ListNode mergeKLists(ListNode[] lists) {
    List<Integer> values = new ArrayList<>();
    
    // Collect all values
    for (ListNode list : lists) {
        while (list != null) {
            values.add(list.val);
            list = list.next;
        }
    }
    
    // Sort all values
    Collections.sort(values);
    
    // Build new linked list
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    for (int val : values) {
        current.next = new ListNode(val);
        current = current.next;
    }
    
    return dummy.next;
}
```

**Complexity Analysis**
- **Time Complexity**: O(n log n) where n = total nodes
- **Space Complexity**: O(n) for the array

**Why Not Optimal?**
- Doesn't leverage the fact that individual lists are already sorted
- Uses extra O(n) space
- Slower than necessary due to sorting everything from scratch

---

#### **Approach 2: Iterative Sequential Merge**
```
1. Start with result = lists[0]
2. For i from 1 to k-1:
   - Merge result with lists[i]
   - Update result
3. Return result
```

**Code Implementation**
```java
public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) return null;
    
    ListNode result = lists[0];
    for (int i = 1; i < lists.length; i++) {
        result = mergeTwoLists(result, lists[i]);
    }
    
    return result;
}

private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    
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
    
    current.next = (l1 != null) ? l1 : l2;
    return dummy.next;
}
```

**Complexity Analysis**
- **Time Complexity**: O(n * k) where n = total nodes, k = number of lists
  - First merge: n1 + n2 operations
  - Second merge: (n1 + n2) + n3 operations
  - Total: approximately n * k operations
- **Space Complexity**: O(1) (in-place merging)

**Why Not Optimal?**
- The first list gets traversed k-1 times
- Inefficient when k is large
- Each merge operation gets progressively longer

---

#### **Approach 3: Divide & Conquer (OPTIMAL for Space)**
```
1. Pair up k lists and merge each pair
2. After first round, k lists become k/2 lists
3. Repeat until only 1 list remains
4. Return the final list
```

**Visualization:**
```
Round 1: [L1, L2, L3, L4, L5, L6, L7, L8]
         Merge: (L1,L2), (L3,L4), (L5,L6), (L7,L8)
         Result: [M1, M2, M3, M4]

Round 2: [M1, M2, M3, M4]
         Merge: (M1,M2), (M3,M4)
         Result: [F1, F2]

Round 3: [F1, F2]
         Merge: (F1,F2)
         Result: [Final]
```

**Example Walkthrough**

Input: lists = [[1,4,5], [1,3,4], [2,6]]

**Round 1:** Pair and merge
- Merge [1,4,5] and [1,3,4] → [1,1,3,4,4,5]
- Take [2,6] as is (no pair)
- Result: [[1,1,3,4,4,5], [2,6]]

**Round 2:** Pair and merge
- Merge [1,1,3,4,4,5] and [2,6] → [1,1,2,3,4,4,5,6]
- Result: [[1,1,2,3,4,4,5,6]]

**Final:** Return [1,1,2,3,4,4,5,6]

**Code Implementation**
```java
public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) {
        return null;
    }
    
    // Keep merging in pairs until one list remains
    while (lists.length > 1) {
        List<ListNode> mergedLists = new ArrayList<>();
        
        for (int i = 0; i < lists.length; i += 2) {
            ListNode l1 = lists[i];
            ListNode l2 = (i + 1 < lists.length) ? lists[i + 1] : null;
            mergedLists.add(mergeTwoLists(l1, l2));
        }
        
        lists = mergedLists.toArray(new ListNode[0]);
    }
    
    return lists[0];
}

private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    
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
    
    current.next = (l1 != null) ? l1 : l2;
    return dummy.next;
}
```

**Complexity Analysis**
- **Time Complexity**: O(n * log k)
  - log k rounds (each round halves the number of lists)
  - Each round processes all n nodes
- **Space Complexity**: O(1) for iterative version (O(log k) for recursive)

---

#### **Approach 4: Min Heap / Priority Queue (OPTIMAL for Clarity)**
```
1. Create a min heap
2. Add the head of each non-empty list to the heap
3. While heap is not empty:
   - Extract the minimum node
   - Add it to the result
   - If the node has a next, add next to heap
4. Return the result
```

**Step-by-Step Process:**

Input: lists = [[1,4,5], [1,3,4], [2,6]]

**Initial Heap:** [1(L1), 1(L2), 2(L3)]

| Step | Extract | Result List | Insert | Heap After |
|------|---------|-------------|--------|------------|
| 1 | 1(L1) | [1] | 4(L1) | [1(L2), 2(L3), 4(L1)] |
| 2 | 1(L2) | [1,1] | 3(L2) | [2(L3), 3(L2), 4(L1)] |
| 3 | 2(L3) | [1,1,2] | 6(L3) | [3(L2), 4(L1), 6(L3)] |
| 4 | 3(L2) | [1,1,2,3] | 4(L2) | [4(L1), 4(L2), 6(L3)] |
| 5 | 4(L1) | [1,1,2,3,4] | 5(L1) | [4(L2), 5(L1), 6(L3)] |
| 6 | 4(L2) | [1,1,2,3,4,4] | null | [5(L1), 6(L3)] |
| 7 | 5(L1) | [1,1,2,3,4,4,5] | null | [6(L3)] |
| 8 | 6(L3) | [1,1,2,3,4,4,5,6] | null | [] |

Output: [1,1,2,3,4,4,5,6]

**Code Implementation**
```java
public ListNode mergeKLists(ListNode[] lists) {
    if (lists == null || lists.length == 0) {
        return null;
    }
    
    // Min heap based on node values
    PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
    
    // Add all non-null list heads to heap
    for (ListNode head : lists) {
        if (head != null) {
            minHeap.offer(head);
        }
    }
    
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    
    while (!minHeap.isEmpty()) {
        ListNode smallest = minHeap.poll();
        current.next = smallest;
        current = current.next;
        
        if (smallest.next != null) {
            minHeap.offer(smallest.next);
        }
    }
    
    return dummy.next;
}
```

**Complexity Analysis**
- **Time Complexity**: O(n * log k)
  - n nodes, each inserted and extracted from heap
  - Each heap operation is O(log k)
- **Space Complexity**: O(k) for the heap

---

## Why This Strategy?

### Comparison of All Approaches

| Approach | Time | Space | Best When | Pros | Cons |
|----------|------|-------|-----------|------|------|
| Brute Force | O(n log n) | O(n) | Never | Simple to understand | Doesn't use sorted property, extra space |
| Sequential Merge | O(n*k) | O(1) | k ≤ 3 | No extra space | Inefficient for large k |
| **Divide & Conquer** | **O(n log k)** | **O(1)** | **Space critical** | **Optimal time & space** | Slightly complex |
| **Min Heap** | **O(n log k)** | **O(k)** | **Clarity matters** | **Clear logic, optimal time** | Extra O(k) space |

### Why Divide & Conquer Works:
- **Balanced work distribution**: Each merge is roughly the same size
- **Logarithmic levels**: Halving k lists each time gives log k levels
- **Leverages sorted property**: Uses efficient two-list merge as building block
- **Space efficient**: No extra data structures needed

### Why Min Heap Works:
- **Always optimal choice**: Heap guarantees we pick the smallest among k candidates
- **Efficient selection**: O(log k) is much better than O(k) for finding minimum
- **Simple logic**: Easy to understand and implement
- **Good for streaming**: Works well if lists arrive dynamically

---

## Critical Edge Cases & Gotchas

### 1. **Empty Array of Lists**
```java
Input: lists = []
Output: null
Explanation: No lists to merge
```

### 2. **Array with All Empty Lists**
```java
Input: lists = [[], [], []]
Output: null
Explanation: All lists are empty
```

### 3. **Single List**
```java
Input: lists = [[1,2,3]]
Output: [1,2,3]
Explanation: Only one list, return it as is
```

### 4. **Two Lists with No Overlap**
```java
Input: lists = [[1,2,3], [4,5,6]]
Output: [1,2,3,4,5,6]
Explanation: All elements of first list are smaller
```

### 5. **Lists with Duplicate Values**
```java
Input: lists = [[1,1,1], [1,1,1]]
Output: [1,1,1,1,1,1]
Explanation: Need to handle equal values correctly
```

### 6. **Lists of Different Lengths**
```java
Input: lists = [[1], [1,3,5,7,9], [2,4]]
Output: [1,1,2,3,4,5,7,9]
Explanation: Lists can have vastly different sizes
```

### 7. **Odd Number of Lists (Divide & Conquer)**
```java
Input: lists = [[1], [2], [3]]
Output: [1,2,3]
Explanation: Last list in round has no pair - handle correctly
```

---

## Common Mistakes to Avoid

### ❌ **MISTAKE 1: Comparing List References Instead of Values (Min Heap)**
```java
// WRONG - Default comparator compares object references!
PriorityQueue<ListNode> minHeap = new PriorityQueue<>();
```

**Why wrong:** Without a custom comparator, PriorityQueue will compare object references, not node values.

**Fix:**
```java
// CORRECT
PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);
```

---

### ❌ **MISTAKE 2: Forgetting to Add Next Node to Heap**
```java
// WRONG - Only processes first node of each list!
while (!minHeap.isEmpty()) {
    ListNode smallest = minHeap.poll();
    current.next = smallest;
    current = current.next;
    // Missing: minHeap.offer(smallest.next)
}
```

**Why wrong:** Only processes the first node of each list, loses the rest.

**Fix:**
```java
// CORRECT
if (smallest.next != null) {
    minHeap.offer(smallest.next);
}
```

---

### ❌ **MISTAKE 3: Not Handling Odd Number of Lists (Divide & Conquer)**
```java
// WRONG - ArrayIndexOutOfBounds when i+1 >= length!
for (int i = 0; i < lists.length; i += 2) {
    mergedLists.add(mergeTwoLists(lists[i], lists[i + 1]));
}
```

**Why wrong:** When k is odd, the last list has no pair, causing index out of bounds.

**Fix:**
```java
// CORRECT
for (int i = 0; i < lists.length; i += 2) {
    ListNode l1 = lists[i];
    ListNode l2 = (i + 1 < lists.length) ? lists[i + 1] : null;
    mergedLists.add(mergeTwoLists(l1, l2));
}
```

---

### ❌ **MISTAKE 4: Returning Wrong Node**
```java
// WRONG - Returns the dummy node, not the actual head!
ListNode dummy = new ListNode(0);
// ... build list ...
return dummy;
```

**Why wrong:** The dummy node is a placeholder, not part of the result.

**Fix:**
```java
// CORRECT
return dummy.next;
```

---

### ❌ **MISTAKE 5: Not Checking for Empty/Null Input**
```java
// WRONG - NullPointerException or ArrayIndexOutOfBounds!
public ListNode mergeKLists(ListNode[] lists) {
    return mergeHelper(lists, 0, lists.length - 1);
}
```

**Why wrong:** If lists is null or empty, you'll get an exception.

**Fix:**
```java
// CORRECT
if (lists == null || lists.length == 0) {
    return null;
}
```

---

### ❌ **MISTAKE 6: Merging Sequentially (Inefficient)**
```java
// WRONG - Works but O(n*k) instead of O(n log k)
ListNode result = lists[0];
for (int i = 1; i < lists.length; i++) {
    result = mergeTwoLists(result, lists[i]);
}
```

**Why wrong:** This is O(n*k) because the first list gets traversed k times.

**Fix:** Use divide & conquer or min heap for O(n log k).

---

### ❌ **MISTAKE 7: Adding Null Nodes to Heap**
```java
// WRONG - NullPointerException during comparison!
for (ListNode head : lists) {
    minHeap.offer(head); // Adds null if list is empty!
}
```

**Why wrong:** Null nodes in heap cause NullPointerException during comparison.

**Fix:**
```java
// CORRECT
for (ListNode head : lists) {
    if (head != null) {
        minHeap.offer(head);
    }
}
```

---

### ❌ **MISTAKE 8: Creating New Nodes Instead of Reusing**
```java
// WRONG - Wastes space!
ListNode newNode = new ListNode(smallest.val);
current.next = newNode;
```

**Why wrong:** Wastes space and defeats the purpose of in-place merging.

**Fix:**
```java
// CORRECT - Reuse existing nodes
current.next = smallest;
```

---

## Visualization

### Divide & Conquer Process:
```
Initial: [L1, L2, L3, L4, L5, L6, L7, L8]
         ↓
Level 1: Merge pairs
         (L1,L2)  (L3,L4)  (L5,L6)  (L7,L8)
           ↓        ↓        ↓        ↓
         [M1,     M2,     M3,     M4]
         ↓
Level 2: Merge pairs
         (M1,M2)          (M3,M4)
            ↓                ↓
         [F1,             F2]
         ↓
Level 3: Merge pairs
         (F1,F2)
            ↓
         [FINAL]

Number of levels = log₂(8) = 3
Work per level = all n nodes
Total: O(n * log k)
```

### Min Heap Process:
```
Lists: [1→4→5], [1→3→4], [2→6]

Initial Heap: [1(L1), 1(L2), 2(L3)]
              ↓ Extract min
Result: [1(L1)]
Heap: [1(L2), 2(L3), 4(L1)] ← Insert 4
              ↓ Extract min
Result: [1(L1), 1(L2)]
Heap: [2(L3), 3(L2), 4(L1)] ← Insert 3
              ↓ Extract min
Result: [1(L1), 1(L2), 2(L3)]
... continues until heap is empty
```

---

## Interview Tips

**What to say in an interview:**

> "This is a k-way merge problem. Since each list is already sorted, I can leverage that property. There are several approaches:
>
> 1. **Brute Force:** Collect all values, sort, and rebuild - O(n log n) time but doesn't use the sorted property efficiently.
>
> 2. **Sequential Merge:** Merge lists one by one - O(n*k) time because we traverse the result multiple times.
>
> 3. **Divide & Conquer:** Merge lists in pairs, halving the problem each round - O(n log k) time with O(1) space. Similar to merge sort's merge process.
>
> 4. **Min Heap:** Use a priority queue to always pick the smallest among k candidates - O(n log k) time with O(k) space.
>
> I'll implement the **Divide & Conquer** approach as it's optimal in both time and space, giving us O(n log k) time with O(1) space. The key insight is that merging in pairs reduces the number of lists by half each round, creating log k levels, and at each level we process all n nodes."

**Key points to mention:**
1. **Why O(n log k) is optimal**: Can't do better than visiting all nodes (n) and comparison overhead is at least log k
2. **Divide & Conquer analogy**: Similar to merge sort's merge phase
3. **Trade-off discussion**: D&C for space, Min Heap for clarity
4. **Sorted property**: Critical for efficiency
5. **Reuse merge two lists**: Shows code reusability

**Follow-up questions you might get:**
- "What if k is very large?" → Min heap might be better as it processes in one pass
- "What if lists arrive dynamically?" → Min heap handles streaming better
- "Can you do it recursively?" → Show recursive D&C version
- "How would you test this?" → Mention edge cases like empty lists, single list, odd k

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| Merge Two Sorted Lists | Easy | Two Pointers | Only 2 lists (building block) |
| **Merge K Sorted Lists** | **Hard** | **Divide & Conquer / Heap** | **k lists** ← This problem |
| Merge Sorted Array | Easy | Two Pointers | Arrays instead of linked lists |
| Kth Smallest in Sorted Matrix | Medium | Min Heap | 2D matrix, find kth element |
| Find K Pairs with Smallest Sums | Medium | Min Heap | Similar heap usage pattern |
| Merge K Sorted Arrays | Medium | Divide & Conquer / Heap | Arrays instead of linked lists |
| Sort List | Medium | Merge Sort | Single list, need to sort it |

---

## Final Pattern Label

✅ **Divide & Conquer – Pair-wise Merging** (Best for space efficiency)  
✅ **Min Heap – K-way Merge** (Best for clarity and understanding)

**Remember:**
- **k sorted inputs + merge all** → Think Divide & Conquer or Min Heap
- **O(n log k)** is the gold standard for this problem
- **Choose D&C** when space is tight or k is very large
- **Choose Heap** when k is moderate and clarity matters

