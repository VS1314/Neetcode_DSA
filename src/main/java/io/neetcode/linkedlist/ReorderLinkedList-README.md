# Reorder Linked List

## Problem Description

**Difficulty**: Medium

You are given the head of a singly linked-list.

The positions of a linked list of `length = 7` for example, can initially be represented as:
```
[0, 1, 2, 3, 4, 5, 6]
```

Reorder the nodes of the linked list to be in the following order:
```
[0, 6, 1, 5, 2, 4, 3]
```

Notice that in the general case for a list of `length = n` the nodes are reordered to be in the following order:
```
[0, n-1, 1, n-2, 2, n-3, ...]
```

**Important:** You may **not** modify the values in the list's nodes, but instead you must **reorder the nodes themselves**.

**Visual Example:**
```
Original: 1 → 2 → 3 → 4 → 5 → 6 → null

Reordered: 1 → 6 → 2 → 5 → 3 → 4 → null
           ↑   ↑   ↑   ↑   ↑   ↑
         1st last 2nd 2nd-last 3rd 3rd-last

Pattern: Alternate between front and back
```

## Examples

### Example 1:
```
Input: head = [2,4,6,8]
Output: [2,8,4,6]

Visualization:
Original: 2 → 4 → 6 → 8 → null
          0   1   2   3 (positions)

Reorder pattern: [0, 3, 1, 2]
Result: 2 → 8 → 4 → 6 → null
```

### Example 2:
```
Input: head = [2,4,6,8,10]
Output: [2,10,4,8,6]

Visualization:
Original: 2 → 4 → 6 → 8 → 10 → null
          0   1   2   3    4  (positions)

Reorder pattern: [0, 4, 1, 3, 2]
Result: 2 → 10 → 4 → 8 → 6 → null
        ↑    ↑   ↑   ↑   ↑
      first last 2nd 2nd-last middle
```

### Example 3:
```
Input: head = [1]
Output: [1]

Single node, no reordering needed
```

### Example 4:
```
Input: head = [1,2]
Output: [1,2]

Two nodes: 1 → 2 → null
Reorder: [0, 1] → [1, 2]
Already in correct order
```

### Example 5:
```
Input: head = [1,2,3]
Output: [1,3,2]

Visualization:
Original: 1 → 2 → 3 → null
Reorder: [0, 2, 1]
Result: 1 → 3 → 2 → null
```

### Example 6:
```
Input: head = [1,2,3,4,5,6]
Output: [1,6,2,5,3,4]

Visualization:
Original: 1 → 2 → 3 → 4 → 5 → 6 → null
Reorder: [0, 5, 1, 4, 2, 3]
Result: 1 → 6 → 2 → 5 → 3 → 4 → null
```

### Example 7:
```
Input: head = [1,2,3,4,5,6,7]
Output: [1,7,2,6,3,5,4]

Visualization:
Original: 1 → 2 → 3 → 4 → 5 → 6 → 7 → null
Reorder: [0, 6, 1, 5, 2, 4, 3]
Result: 1 → 7 → 2 → 6 → 3 → 5 → 4 → null
```

### Example 8:
```
Input: head = [1,2,3,4]
Output: [1,4,2,3]

Even length list
Original: 1 → 2 → 3 → 4 → null
Result: 1 → 4 → 2 → 3 → null
```

### Example 9:
```
Input: head = [10,20,30,40,50]
Output: [10,50,20,40,30]

Odd length list
Original: 10 → 20 → 30 → 40 → 50 → null
Result: 10 → 50 → 20 → 40 → 30 → null
```

### Example 10:
```
Input: head = [5,4,3,2,1]
Output: [5,1,4,2,3]

Descending values (order of values doesn't matter)
Original: 5 → 4 → 3 → 2 → 1 → null
Result: 5 → 1 → 4 → 2 → 3 → null
```

## Constraints
- `1 <= Length of the list <= 1000`
- `1 <= Node.val <= 1000`

**Recommended Complexity**: 
- Time: O(n) where n is the length of the list
- Space: O(1) — constant extra space (in-place reordering)

---

## Pattern Recognition

**Primary Pattern**: **Three-Step In-Place Reordering (Find Middle + Reverse + Merge)**

**Why This Pattern?**
- Need to **interleave front and back** elements
- Must reorder **in-place** (O(1) space)
- **Cannot modify values** (must rearrange nodes)
- Combines: **fast/slow pointers** + **reverse list** + **merge lists**

**Key Insight**: Split, Reverse Second Half, Merge
```
The reorder pattern [0, n-1, 1, n-2, 2, n-3, ...] suggests:
  - Take from front
  - Take from back
  - Repeat alternately

This is like merging two lists:
  First half: [0, 1, 2, ...]
  Second half (reversed): [n-1, n-2, n-3, ...]
  
Merge alternately: 0, n-1, 1, n-2, 2, n-3, ...

Three steps:
  1. Find middle (split into two halves)
  2. Reverse second half
  3. Merge two halves alternately
```

**The Three-Phase Strategy**:
```
Original list: 1 → 2 → 3 → 4 → 5 → 6 → null

Phase 1: Find middle and split
  Use fast/slow pointers
  Slow ends at middle
  
  First half: 1 → 2 → 3 → null
  Second half: 4 → 5 → 6 → null

Phase 2: Reverse second half
  Reverse 4 → 5 → 6
  
  First half: 1 → 2 → 3 → null
  Second half: 6 → 5 → 4 → null

Phase 3: Merge alternately
  Take from first: 1
  Take from second: 6
  Take from first: 2
  Take from second: 5
  Take from first: 3
  Take from second: 4
  
  Result: 1 → 6 → 2 → 5 → 3 → 4 → null ✓
```

**Why This Works**:
```
Original positions: [0, 1, 2, 3, 4, 5]

After splitting at middle:
  L1: [0, 1, 2]
  L2: [3, 4, 5]

After reversing L2:
  L1: [0, 1, 2]
  L2: [5, 4, 3]

Merge alternately:
  0, 5, 1, 4, 2, 3 ✓

This matches the required pattern!
```

**Finding the Middle (Fast & Slow Pointers)**:
```
Goal: Split list into two halves

For even-length list [1,2,3,4]:
  Want: [1,2] and [3,4]
  
For odd-length list [1,2,3,4,5]:
  Want: [1,2,3] and [4,5]
  (First half gets the extra node)

Fast/Slow approach:
  slow = head
  fast = head
  
  while fast.next != null and fast.next.next != null:
      slow = slow.next
      fast = fast.next.next
  
  slow ends at last node of first half
  
  Second half starts at slow.next
  Cut: slow.next = null
```

**Example: Finding Middle**:
```
List: 1 → 2 → 3 → 4 → 5 → null (odd length)

Initial: slow=1, fast=1

Iteration 1:
  slow = 2
  fast = 3

Iteration 2:
  slow = 3
  fast = 5

Iteration 3:
  fast.next = null, stop
  
slow = 3 (middle)
First half: 1 → 2 → 3
Second half: 4 → 5

Perfect split! ✓
```

**Reversing Second Half**:
```
Use standard three-pointer reversal:
  prev = null
  curr = second_half_head
  
  while curr != null:
      next = curr.next
      curr.next = prev
      prev = curr
      curr = next
  
  return prev (new head of reversed list)

Example:
  Original: 4 → 5 → 6 → null
  Reversed: 6 → 5 → 4 → null
```

**Merging Two Lists Alternately**:
```
l1 = first half
l2 = reversed second half

while l2 != null:
    // Save next pointers
    next1 = l1.next
    next2 = l2.next
    
    // Link l1 to l2
    l1.next = l2
    
    // Link l2 to next1 (if exists)
    if next1 != null:
        l2.next = next1
    
    // Move pointers
    l1 = next1
    l2 = next2

Example:
  L1: 1 → 2 → 3 → null
  L2: 6 → 5 → 4 → null

Step 1:
  Link 1 → 6
  Link 6 → 2
  Result: 1 → 6 → 2 → 3 → null
          L2: 5 → 4 → null

Step 2:
  Link 2 → 5
  Link 5 → 3
  Result: 1 → 6 → 2 → 5 → 3 → null
          L2: 4 → null

Step 3:
  Link 3 → 4
  4.next = null (already)
  Result: 1 → 6 → 2 → 5 → 3 → 4 → null ✓
```

**Even vs Odd Length**:
```
Even length (n=6): [1,2,3,4,5,6]
  Split: [1,2,3] and [4,5,6]
  Reverse: [1,2,3] and [6,5,4]
  Merge: 1,6,2,5,3,4 ✓
  All nodes used

Odd length (n=5): [1,2,3,4,5]
  Split: [1,2,3] and [4,5]
  Reverse: [1,2,3] and [5,4]
  Merge: 1,5,2,4,3 ✓
  Middle node (3) at end
  
First half gets extra node for odd length
Merge ends when second half exhausted
```

**Why O(1) Space?**
```
Phase 1 (Find middle): O(1)
  Only two pointers (slow, fast)

Phase 2 (Reverse): O(1)
  Only three pointers (prev, curr, next)

Phase 3 (Merge): O(1)
  Only four pointers (l1, l2, next1, next2)

Total: O(1) space ✓
All done in-place!
```

**Time Complexity**:
```
Phase 1: O(n) - traverse to find middle
Phase 2: O(n/2) - reverse second half
Phase 3: O(n/2) - merge two halves

Total: O(n) + O(n/2) + O(n/2) = O(n) ✓
```

**Related Patterns**:
1. **Fast & Slow Pointers** — Find middle
2. **Reverse Linked List** — Reverse second half
3. **Merge Two Lists** — Merge alternately
4. **In-Place Modification** — O(1) space

---

## Algorithm & Approach

### Core Insight

**Why Three-Phase Approach Works:**
```
Key observations:
  1. Reorder pattern alternates front and back
  2. Split list at middle creates two halves
  3. Reversing second half aligns back elements
  4. Merge alternately produces required pattern
```

**The Optimal Strategy**:
```
Key steps:
  1. Find middle using fast/slow pointers
  2. Split list into two halves
  3. Reverse second half
  4. Merge two halves alternately
  5. Return modified head
```

### Step-by-Step Algorithm

---

#### **Approach 1: Three-Phase In-Place Reordering - OPTIMAL**

**Core Idea**:
- Phase 1: Find middle and split into two halves
- Phase 2: Reverse second half
- Phase 3: Merge two halves alternately

**Algorithm**
```
reorderList(head):
    // Edge cases
    if head == null or head.next == null:
        return  // No reordering needed
    
    // Phase 1: Find middle and split
    slow = head
    fast = head
    
    while fast.next != null and fast.next.next != null:
        slow = slow.next
        fast = fast.next.next
    
    // slow is at last node of first half
    second = slow.next
    slow.next = null  // Cut list
    
    // Phase 2: Reverse second half
    second = reverseList(second)
    
    // Phase 3: Merge two halves
    first = head
    while second != null:
        next1 = first.next
        next2 = second.next
        
        first.next = second
        second.next = next1
        
        first = next1
        second = next2

reverseList(head):
    prev = null
    curr = head
    
    while curr != null:
        next = curr.next
        curr.next = prev
        prev = curr
        curr = next
    
    return prev
```

**Code Implementation**
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

class Solution {
    public void reorderList(ListNode head) {
        // Edge case: empty or single node
        if (head == null || head.next == null) {
            return;
        }
        
        // Phase 1: Find middle and split
        ListNode slow = head;
        ListNode fast = head;
        
        // Find middle using fast/slow pointers
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Split into two halves
        ListNode second = slow.next;
        slow.next = null;  // Cut the list
        
        // Phase 2: Reverse second half
        second = reverseList(second);
        
        // Phase 3: Merge two halves alternately
        ListNode first = head;
        while (second != null) {
            // Save next pointers
            ListNode next1 = first.next;
            ListNode next2 = second.next;
            
            // Link first node to second node
            first.next = second;
            
            // Link second node to next of first
            second.next = next1;
            
            // Move pointers
            first = next1;
            second = next2;
        }
    }
    
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        return prev;
    }
}
```

**Example Walkthrough**

Input: `head = [1,2,3,4,5,6]`

**Phase 1: Find Middle and Split**
```
Initial: slow=1, fast=1

Iteration 1:
  slow = 2
  fast = 3

Iteration 2:
  slow = 3
  fast = 5

Iteration 3:
  fast.next.next = null, stop

slow = 3
second = slow.next = 4

Cut: slow.next = null

Result:
  First half: 1 → 2 → 3 → null
  Second half: 4 → 5 → 6 → null
```

**Phase 2: Reverse Second Half**
```
Original: 4 → 5 → 6 → null

Reverse process:
  prev=null, curr=4
  
  Step 1: 4.next = null, prev=4, curr=5
  Step 2: 5.next = 4, prev=5, curr=6
  Step 3: 6.next = 5, prev=6, curr=null

Reversed: 6 → 5 → 4 → null

second = 6
```

**Phase 3: Merge Alternately**
```
first = 1 → 2 → 3 → null
second = 6 → 5 → 4 → null

Iteration 1:
  next1 = 2, next2 = 5
  1.next = 6
  6.next = 2
  first = 2, second = 5
  
  Result: 1 → 6 → 2 → 3 → null
          second: 5 → 4 → null

Iteration 2:
  next1 = 3, next2 = 4
  2.next = 5
  5.next = 3
  first = 3, second = 4
  
  Result: 1 → 6 → 2 → 5 → 3 → null
          second: 4 → null

Iteration 3:
  next1 = null, next2 = null
  3.next = 4
  4.next = null
  first = null, second = null
  
  Result: 1 → 6 → 2 → 5 → 3 → 4 → null ✓

Loop ends (second = null)
```

**Final Result:**
```
Original: [1,2,3,4,5,6]
Reordered: [1,6,2,5,3,4] ✓
```

**Complexity Analysis**
- **Time**: O(n) — Three O(n) phases
- **Space**: O(1) — Only pointers, in-place

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | In-Place | Recommended |
|----------|------|-------|----------|-------------|
| **Three-Phase (Split+Reverse+Merge)** | **O(n)** | **O(1)** | **Yes ✅** | **Yes ✅** |
| Array storage | O(n) | O(n) | No | No (extra space) |
| Recursion | O(n) | O(n) | No | No (stack space) |
| Deque/Stack | O(n) | O(n) | No | No (extra space) |

**Winner**: **Three-phase in-place** — optimal time and space!

### Why Split at Middle

```
Goal: Create two halves to merge alternately

Pattern: [0, n-1, 1, n-2, 2, n-3, ...]

Observation:
  First half: 0, 1, 2, ... (in order)
  Second half: n-1, n-2, n-3, ... (reversed)

If we split at middle:
  First half: [0, 1, 2, ...]
  Second half: [..., n-2, n-1]
  
  Reverse second half: [n-1, n-2, ...]
  
  Then merge alternately!

Splitting at middle creates balanced halves
for alternating merge.
```

### Why Reverse Second Half

```
Without reversing:
  L1: 1 → 2 → 3
  L2: 4 → 5 → 6
  
  Alternating merge: 1, 4, 2, 5, 3, 6 ❌
  Wrong pattern!

With reversing:
  L1: 1 → 2 → 3
  L2: 6 → 5 → 4 (reversed)
  
  Alternating merge: 1, 6, 2, 5, 3, 4 ✓
  Correct pattern!

Reversing aligns back elements in right order
for alternating merge.
```

### Why Use fast.next and fast.next.next

```
Condition: while (fast.next != null && fast.next.next != null)

Why not fast != null?

For even length [1,2,3,4]:
  Goal: slow at 2 (middle of first half)
  
  With fast != null:
    Initial: slow=1, fast=1
    Iter 1: slow=2, fast=3
    Iter 2: slow=3, fast=null (stop)
    slow = 3 (too far!)
  
  With fast.next and fast.next.next:
    Initial: slow=1, fast=1
    Iter 1: slow=2, fast=3
    fast.next.next = null (stop)
    slow = 2 ✓

Checking fast.next.next stops at right position!
```

### Why Merge Until second is null

```
For even length:
  First: [1, 2, 3]
  Second: [6, 5, 4]
  
  Both have same length
  Merge all pairs

For odd length:
  First: [1, 2, 3]
  Second: [5, 4]
  
  First has one extra
  Merge until second exhausted
  Last node of first stays at end

Condition: while (second != null)
  Works for both cases!
  
When second is exhausted, first's remaining
node(s) are already in correct position.
```

### Why This is Optimal

```
Time: O(n)
  Phase 1: O(n) find middle
  Phase 2: O(n/2) reverse half
  Phase 3: O(n/2) merge halves
  Total: O(n) ✓

Space: O(1)
  Only constant pointers
  No recursion, no extra structures
  In-place modification ✓

Cannot do better than O(n) time
(must touch all nodes)
Cannot do better than O(1) space
(in-place requirement)

This is optimal!
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Node**
```java
Input: head = [1]
Output: [1]

No reordering needed
Return immediately
```

### 2. **Two Nodes**
```java
Input: head = [1,2]
Output: [1,2]

Split: [1] and [2]
Reverse: [1] and [2]
Merge: 1 → 2 (same)
```

### 3. **Three Nodes (Odd)**
```java
Input: head = [1,2,3]
Output: [1,3,2]

Split: [1,2] and [3]
Reverse: [1,2] and [3]
Merge: 1,3,2 ✓
```

### 4. **Four Nodes (Even)**
```java
Input: head = [1,2,3,4]
Output: [1,4,2,3]

Split: [1,2] and [3,4]
Reverse: [1,2] and [4,3]
Merge: 1,4,2,3 ✓
```

### 5. **Large Even List**
```java
Input: head = [1,2,3,4,5,6,7,8]
Output: [1,8,2,7,3,6,4,5]

Split into equal halves
Perfect alternation
```

### 6. **Large Odd List**
```java
Input: head = [1,2,3,4,5,6,7]
Output: [1,7,2,6,3,5,4]

First half gets extra node
Middle node ends up at end
```

### 7. **Already Reordered**
```java
Input: head = [1,4,2,3]
Output: [1,3,2,4]

Algorithm doesn't check if already reordered
Performs transformation regardless
```

### 8. **Descending Values**
```java
Input: head = [5,4,3,2,1]
Output: [5,1,4,2,3]

Values don't affect algorithm
Works with any values
```

### 9. **Duplicate Values**
```java
Input: head = [1,1,1,1]
Output: [1,1,1,1]

Duplicates don't matter
Reorders positions, not values
```

### 10. **Maximum Length**
```java
Input: head = [1,2,3,...,1000]
Output: [1,1000,2,999,3,998,...]

Handles maximum constraint (1000 nodes)
Still O(n) time, O(1) space
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Cutting the List After Finding Middle**
```java
// WRONG - forgot to cut list
ListNode slow = head;
ListNode fast = head;

while (fast.next != null && fast.next.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}

ListNode second = slow.next;
// Missing: slow.next = null;

second = reverseList(second);
```

**Why wrong**: First and second halves still connected!

**Dry run failure:**
```
List: 1 → 2 → 3 → 4 → null

After finding middle:
  slow = 2
  second = 3
  
Without cutting:
  First half: 1 → 2 → 3 → 4 (whole list!)
  Second half: 3 → 4
  
When reversing second:
  Changes: 4 → 3
  But 2 still points to 3!
  
Creates cycle or wrong structure! ❌
```

**Fix**: Cut the list
```java
ListNode second = slow.next;
slow.next = null;  // Cut!
```

### ❌ **MISTAKE 2: Wrong Fast/Slow Loop Condition**
```java
// WRONG - using fast != null
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
```

**Why wrong**: slow ends at wrong position!

**Dry run failure:**
```
List: [1,2,3,4]

With fast != null:
  Iter 1: slow=2, fast=3
  Iter 2: slow=3, fast=null
  slow = 3 (wrong!)
  
  Split: [1,2,3] and [4]
  Should be: [1,2] and [3,4]

With fast.next and fast.next.next:
  Iter 1: slow=2, fast=3
  fast.next.next = null, stop
  slow = 2 ✓
  
  Split: [1,2] and [3,4] ✓
```

**Fix**: Use correct condition
```java
while (fast.next != null && fast.next.next != null) {
    // ...
}
```

### ❌ **MISTAKE 3: Forgetting to Save Next Pointers in Merge**
```java
// WRONG - not saving next pointers
while (second != null) {
    first.next = second;
    second.next = first.next;  // WRONG! Lost reference
    
    first = first.next;
    second = second.next;
}
```

**Why wrong**: Lose references, create cycles!

**Dry run failure:**
```
first = 1 → 2 → null
second = 4 → 3 → null

Iteration 1:
  first.next = second  // 1 → 4
  second.next = first.next  // 4 → 4 (cycle!) ❌
  
Lost reference to node 2!
```

**Fix**: Save next pointers first
```java
while (second != null) {
    ListNode next1 = first.next;  // Save!
    ListNode next2 = second.next;  // Save!
    
    first.next = second;
    second.next = next1;
    
    first = next1;
    second = next2;
}
```

### ❌ **MISTAKE 4: Merging Wrong Order**
```java
// WRONG - linking in wrong order
while (second != null) {
    ListNode next1 = first.next;
    ListNode next2 = second.next;
    
    second.next = first;  // WRONG! Should be first.next = second
    first.next = next1;
    
    first = next1;
    second = next2;
}
```

**Why wrong**: Wrong alternation!

**Dry run failure:**
```
first = 1 → 2 → null
second = 4 → 3 → null

Result would be:
  4 → 1, 3 → 2 (disconnected)
  
Wrong pattern! ❌
```

**Fix**: Link first to second
```java
first.next = second;
second.next = next1;
```

### ❌ **MISTAKE 5: Returning Modified Head**
```java
// WRONG - void method, don't return
public ListNode reorderList(ListNode head) {
    // ... reorder logic
    
    return head;  // Method should be void!
}
```

**Why wrong**: Method signature is void!

**Correct:**
```java
public void reorderList(ListNode head) {
    // ... reorder logic
    
    // No return statement
    // head is modified in-place
}
```

### ❌ **MISTAKE 6: Not Handling Edge Cases**
```java
// WRONG - no edge case check
public void reorderList(ListNode head) {
    // What if head is null or single node?
    // Will crash on fast.next!
    
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast.next != null && fast.next.next != null) {
        // ...
    }
}
```

**Why wrong**: Crashes on edge cases!

**Fix**: Check edge cases
```java
if (head == null || head.next == null) {
    return;
}
```

### ❌ **MISTAKE 7: Modifying Node Values Instead of Pointers**
```java
// WRONG - problem says can't modify values!
public void reorderList(ListNode head) {
    // Store values in array
    List<Integer> values = new ArrayList<>();
    // ...
    // Reorder values
    // Update node values
    
    // This violates the constraint! ❌
}
```

**Why wrong**: Problem explicitly says "must reorder nodes themselves"!

**Fix**: Reorder pointers, not values
```java
// Change node.next pointers, not node.val
```

### ❌ **MISTAKE 8: Not Checking if first is null in Merge**
```java
// WRONG - might cause NullPointerException
while (second != null) {
    ListNode next1 = first.next;  // What if first is null?
    ListNode next2 = second.next;
    
    first.next = second;  // NullPointerException!
    second.next = next1;
    
    first = next1;
    second = next2;
}
```

**Why wrong**: For even-length lists, first can become null!

**Actually**: With correct splitting, first won't be null when second isn't
  - Even length: both halves same size
  - Odd length: first half has one extra
  
But checking doesn't hurt:

**Better:**
```java
while (second != null) {
    ListNode next1 = first.next;
    ListNode next2 = second.next;
    
    first.next = second;
    if (next1 != null) {  // Extra safety
        second.next = next1;
    }
    
    first = next1;
    second = next2;
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| **Phase 1: Find middle** | O(n) | Traverse list once |
| **Phase 2: Reverse second half** | O(n/2) | Reverse half the list |
| **Phase 3: Merge halves** | O(n/2) | Merge half nodes |
| **Total** | **O(n)** | Linear time |

**Time analysis**:
```
Phase 1: Find middle
  Slow pointer moves n/2 steps
  Fast pointer moves n steps
  Time: O(n)

Phase 2: Reverse second half
  Reverse n/2 nodes
  Time: O(n/2)

Phase 3: Merge
  Process n/2 pairs
  Time: O(n/2)

Total: O(n) + O(n/2) + O(n/2) = O(n) ✓

Cannot do better:
  Must visit every node
  O(n) is optimal!
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| slow, fast pointers | O(1) | Phase 1 |
| prev, curr, next | O(1) | Phase 2 (reverse) |
| first, second, next1, next2 | O(1) | Phase 3 (merge) |
| **Total** | **O(1)** | Constant space |

**Space analysis**:
```
All phases use only constant pointers:
  Phase 1: 2 pointers (slow, fast)
  Phase 2: 3 pointers (prev, curr, next)
  Phase 3: 4 pointers (first, second, next1, next2)

No recursion, no extra data structures
All done in-place

Space: O(1) ✓
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `head = [1,2,3,4,5]`

**Expected Output:** `[1,5,2,4,3]`

---

**Initial List:**
```
1 → 2 → 3 → 4 → 5 → null
```

---

**Phase 1: Find Middle and Split**

```
Find middle using fast/slow:

Initial:
  slow = 1, fast = 1

Iteration 1:
  slow = 2, fast = 3

Iteration 2:
  slow = 3, fast = 5

Iteration 3:
  fast.next = null, stop

slow = 3 (last node of first half)
second = slow.next = 4

Cut: slow.next = null

Result:
  First half:  1 → 2 → 3 → null
  Second half: 4 → 5 → null
```

---

**Phase 2: Reverse Second Half**

```
Original second half: 4 → 5 → null

Reverse process:
  prev = null, curr = 4
  
  Step 1:
    next = 5
    4.next = null
    prev = 4, curr = 5
    
  Step 2:
    next = null
    5.next = 4
    prev = 5, curr = null

Reversed second half: 5 → 4 → null

State:
  First:  1 → 2 → 3 → null
  Second: 5 → 4 → null
```

---

**Phase 3: Merge Alternately**

```
first = 1 → 2 → 3 → null
second = 5 → 4 → null

Iteration 1:
  next1 = 2, next2 = 4
  1.next = 5
  5.next = 2
  first = 2, second = 4
  
  Current: 1 → 5 → 2 → 3 → null
           second: 4 → null

Iteration 2:
  next1 = 3, next2 = null
  2.next = 4
  4.next = 3
  first = 3, second = null
  
  Current: 1 → 5 → 2 → 4 → 3 → null
           second: null

Loop ends (second = null)
```

---

**Final Result:**
```
Original:  [1, 2, 3, 4, 5]
Reordered: [1, 5, 2, 4, 3] ✓

Visual:
1 → 5 → 2 → 4 → 3 → null
↑   ↑   ↑   ↑   ↑
0  n-1  1  n-2  2 (positions in original)
```

---

### Step-by-Step Visual

```
Original: 1 → 2 → 3 → 4 → 5 → 6

Step 1: Find middle
  1 → 2 → 3 → 4 → 5 → 6
          ↑
        slow

Step 2: Split
  L1: 1 → 2 → 3 → null
  L2: 4 → 5 → 6 → null

Step 3: Reverse L2
  L1: 1 → 2 → 3 → null
  L2: 6 → 5 → 4 → null

Step 4: Merge
  Take 1: 1 → ?
  Take 6: 1 → 6 → ?
  Take 2: 1 → 6 → 2 → ?
  Take 5: 1 → 6 → 2 → 5 → ?
  Take 3: 1 → 6 → 2 → 5 → 3 → ?
  Take 4: 1 → 6 → 2 → 5 → 3 → 4 → null

Result: 1 → 6 → 2 → 5 → 3 → 4 ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | In-Place | Recommended |
|----------|------|-------|----------|-------------|
| **Three-Phase (Split+Reverse+Merge)** | **O(n)** | **O(1)** | **Yes ✅** | **Yes ✅** |
| Array storage + reorder | O(n) | O(n) | No | No (extra space) |
| Recursion | O(n) | O(n) | No | No (stack space) |
| Deque (two-ended queue) | O(n) | O(n) | No | No (extra space) |

**Winner**: **Three-phase in-place** — optimal time and space!

**Why Not Array Storage?**
```java
// Store in array, reorder, rebuild
public void reorderList(ListNode head) {
    List<ListNode> nodes = new ArrayList<>();
    
    // Store all nodes
    ListNode curr = head;
    while (curr != null) {
        nodes.add(curr);
        curr = curr.next;
    }
    
    // Reorder
    int left = 0, right = nodes.size() - 1;
    while (left < right) {
        nodes.get(left).next = nodes.get(right);
        left++;
        if (left == right) break;
        nodes.get(right).next = nodes.get(left);
        right--;
    }
    nodes.get(left).next = null;
}

// O(n) time ✓
// O(n) space ❌
// Works but not optimal for space
```

---

## Key Takeaways

1. **Three phases** — find middle, reverse, merge
2. **Fast/slow pointers** — find middle efficiently
3. **Reverse second half** — aligns for alternation
4. **Merge alternately** — interleave two halves
5. **Cut the list** — split into independent halves
6. **Save next pointers** — before modifying links
7. **O(n) time, O(1) space** — optimal solution
8. **In-place modification** — reorder nodes, not values
9. **Works for even and odd** — handles both lengths
10. **Combine multiple patterns** — find middle + reverse + merge

---

## Interview Tips

**What to say in an interview:**

> "To reorder the list to alternate between front and back elements, I'll use a three-phase approach. First, I'll find the middle of the list using the fast and slow pointer technique, where fast moves two steps and slow moves one step. When fast reaches the end, slow will be at the middle. I'll split the list into two halves at this point. Second, I'll reverse the second half using the standard three-pointer reversal technique. Third, I'll merge the two halves alternately by taking one node from the first half, then one from the reversed second half, and so on. This works because after reversing the second half, it has elements in the order [n-1, n-2, ...], which when merged alternately with the first half [0, 1, 2, ...] gives us the required pattern [0, n-1, 1, n-2, ...]. The solution runs in O(n) time since each phase traverses the list once, and uses O(1) space since we only use a constant number of pointers and modify the list in-place."

**Key points to mention:**
1. **Three phases** — split, reverse, merge
2. **Fast/slow pointers** — find middle in O(n)
3. **Cut the list** — split into two independent halves
4. **Reverse second half** — aligns for alternation
5. **Merge alternately** — interleave front and back
6. **Save next pointers** — before modifying links
7. **O(n) time** — each phase is O(n)
8. **O(1) space** — in-place, only pointers

**Common Follow-ups:**
- "What if list is already reordered?" → Algorithm still works, just transforms it
- "Can you do it recursively?" → Possible but uses O(n) stack space
- "How to handle even vs odd length?" → Algorithm handles both automatically
- "What if you can modify values?" → Still need to reorder nodes, not just values
- "Can you reorder differently?" → Yes, adjust merge pattern

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Reorder Linked List** | Medium | **Split+Reverse+Merge** | **This problem** |
| Palindrome Linked List | Easy | Split+Reverse+Compare | Check palindrome instead |
| Reverse Nodes in k-Group | Hard | Reverse in groups | Reverse k nodes at a time |
| Middle of Linked List | Easy | Fast/Slow | Just find middle |
| Reverse Linked List II | Medium | Reverse subsection | Reverse part [left, right] |
| Merge Two Sorted Lists | Easy | Merge | Merge sorted, not alternate |

**Pattern Progression**:
1. **Middle of Linked List** — Find middle with fast/slow
2. **Reverse Linked List** — Reverse technique
3. **Reorder Linked List** (this) — Combine both
4. **Palindrome Linked List** — Similar split+reverse approach

---

## Final Pattern Label

✅ **Three-Phase In-Place Reordering (Split at Middle + Reverse Second Half + Merge Alternately)**

**Remember:** This is a **three-phase algorithm** combining multiple linked list techniques. **Phase 1**: Find middle using **fast/slow pointers** where slow ends at last node of first half (condition: `fast.next && fast.next.next`), then **cut the list** at slow.next to create two independent halves. **Phase 2**: **Reverse the second half** using standard three-pointer technique (prev, curr, next). **Phase 3**: **Merge alternately** by taking one node from first half, then one from reversed second half, carefully **saving next pointers** before modifying links (next1 = first.next, next2 = second.next). The merge continues **while second != null** which works for both even and odd lengths. Achieves **O(n) time** (three O(n) phases) and **O(1) space** (only pointers, in-place modification). Critical: **cut the list** after finding middle, **save next pointers** before linking, and **don't modify values** (reorder nodes). This pattern combines find-middle + reverse-list + merge-lists!
