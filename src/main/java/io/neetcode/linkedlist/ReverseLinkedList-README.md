# Reverse Linked List

## Problem Description

**Difficulty**: Easy

Given the beginning of a singly linked list `head`, reverse the list, and return the new beginning of the list.

A **singly linked list** is a data structure where each node contains:
- A value (`val`)
- A pointer to the next node (`next`)

**Reversing** means:
- The last node becomes the first node (new head)
- Each node's `next` pointer points to its previous node instead of its next node
- The original head becomes the last node (points to `null`)

**Example Structure:**
```
Original: 1 → 2 → 3 → 4 → null
Reversed: 4 → 3 → 2 → 1 → null
```

## Examples

### Example 1:
```
Input: head = [0,1,2,3]
Output: [3,2,1,0]

Visualization:
Original: 0 → 1 → 2 → 3 → null
Reversed: 3 → 2 → 1 → 0 → null
```

### Example 2:
```
Input: head = []
Output: []

Explanation:
Empty list remains empty
```

### Example 3:
```
Input: head = [1]
Output: [1]

Explanation:
Single node list remains unchanged
head → 1 → null
```

### Example 4:
```
Input: head = [1,2]
Output: [2,1]

Visualization:
Original: 1 → 2 → null
Reversed: 2 → 1 → null
```

### Example 5:
```
Input: head = [5,4,3,2,1]
Output: [1,2,3,4,5]

Visualization:
Original: 5 → 4 → 3 → 2 → 1 → null
Reversed: 1 → 2 → 3 → 4 → 5 → null
```

### Example 6:
```
Input: head = [-1,-2,-3]
Output: [-3,-2,-1]

Explanation:
Works with negative values
Original: -1 → -2 → -3 → null
Reversed: -3 → -2 → -1 → null
```

### Example 7:
```
Input: head = [1,1,1,1]
Output: [1,1,1,1]

Explanation:
Works with duplicate values
All nodes have same value but positions reversed
```

### Example 8:
```
Input: head = [1,2,3,4,5,6,7,8,9,10]
Output: [10,9,8,7,6,5,4,3,2,1]

Explanation:
Longer list reversal
Original: 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9 → 10 → null
Reversed: 10 → 9 → 8 → 7 → 6 → 5 → 4 → 3 → 2 → 1 → null
```

### Example 9:
```
Input: head = [100,200]
Output: [200,100]

Explanation:
Works with any integer values within constraints
```

### Example 10:
```
Input: head = [1,2,3]
Output: [3,2,1]

Visualization:
Original: 1 → 2 → 3 → null
         ↓
Step 1:  null ← 1   2 → 3 → null
         ↓
Step 2:  null ← 1 ← 2   3 → null
         ↓
Step 3:  null ← 1 ← 2 ← 3
                         ↑ (new head)
```

## Constraints
- `0 <= The length of the list <= 1000`
- `-1000 <= Node.val <= 1000`

**Recommended Complexity**: 
- Time: O(n) where n is the length of the list
- Space: O(1) — constant extra space (excluding output)

---

## Pattern Recognition

**Primary Pattern**: **Iterative Pointer Manipulation (In-Place Reversal)**

**Why This Pattern?**
- Need to **reverse links** between nodes
- Should work **in-place** (O(1) space)
- Must traverse list **once** (O(n) time)
- **Three-pointer technique** tracks previous, current, next nodes

**Key Insight**: Reverse Each Link One at a Time
```
Original linked list:
  1 → 2 → 3 → null

What we need to do:
  null ← 1 ← 2 ← 3

Cannot do all at once because:
  If we change 1 → 2 to 1 ← 2
  We lose reference to the rest of the list!

Solution: Track three nodes at once
  prev = node before current
  curr = node we're currently reversing
  next = node after current (so we don't lose it)

Process:
  1. Save next node (so we don't lose it)
  2. Reverse current node's pointer
  3. Move all three pointers forward
  4. Repeat until end of list
```

**The Three-Pointer Dance**:
```
Initial state:
  prev = null
  curr = head (1)
  next = undefined

List: null    1 → 2 → 3 → null
      prev  curr

Step 1: Reverse first link
  next = curr.next  // Save 2
  curr.next = prev  // 1 → null
  prev = curr       // prev becomes 1
  curr = next       // curr becomes 2

Result: null ← 1    2 → 3 → null
             prev  curr

Step 2: Reverse second link
  next = curr.next  // Save 3
  curr.next = prev  // 2 → 1
  prev = curr       // prev becomes 2
  curr = next       // curr becomes 3

Result: null ← 1 ← 2    3 → null
                  prev  curr

Step 3: Reverse third link
  next = curr.next  // Save null
  curr.next = prev  // 3 → 2
  prev = curr       // prev becomes 3
  curr = next       // curr becomes null

Result: null ← 1 ← 2 ← 3    null
                       prev curr

Loop ends (curr == null)
Return prev (which is 3, the new head)
```

**Why Three Pointers?**
```
With only two pointers (prev, curr):
  curr.next = prev  // Reverse link
  curr = ???        // Lost reference to next!

With three pointers:
  next = curr.next  // Save reference
  curr.next = prev  // Reverse link
  prev = curr       // Move prev
  curr = next       // Move curr (using saved reference)

All three are necessary!
```

**Iterative vs Recursive**:
```
Iterative (recommended):
  Uses three pointers
  O(n) time, O(1) space
  No stack overflow risk
  Explicit control flow

Recursive:
  Recursively reverse sublist
  O(n) time, O(n) space (call stack)
  Risk of stack overflow for long lists
  More elegant but less efficient
```

**Example with Details**:
```
List: 1 → 2 → 3 → null

Initial:
  prev = null, curr = 1

Iteration 1:
  next = 2 (save it!)
  1.next = null (reverse)
  prev = 1, curr = 2
  
Iteration 2:
  next = 3 (save it!)
  2.next = 1 (reverse)
  prev = 2, curr = 3
  
Iteration 3:
  next = null (save it!)
  3.next = 2 (reverse)
  prev = 3, curr = null
  
Loop ends
Return prev = 3

Result: null ← 1 ← 2 ← 3
```

**Why This is Optimal**:
```
Time: O(n)
  Must visit each node exactly once
  Cannot do better than O(n)

Space: O(1)
  Only three pointers (prev, curr, next)
  No recursion, no extra data structures
  In-place modification

This is optimal!
```

**Related Patterns**:
1. **Two-Pointer Technique** — Extended to three pointers
2. **In-Place Modification** — No extra space
3. **Linked List Traversal** — Visit each node once
4. **Pointer Manipulation** — Reverse links

---

## Algorithm & Approach

### Core Insight

**Why Three-Pointer Iterative Works:**
```
Key observations:
  1. Need to reverse direction of each link
  2. Must not lose reference to rest of list
  3. Three pointers track: previous, current, next
  4. Process each node once, moving pointers forward
```

**The Optimal Strategy**:
```
Key steps:
  1. Initialize prev = null (will become tail)
  2. Initialize curr = head (start of list)
  3. While curr is not null:
     a. Save next node
     b. Reverse current link
     c. Move prev and curr forward
  4. Return prev (new head)
```

### Step-by-Step Algorithm

---

#### **Approach 1: Iterative Three-Pointer - OPTIMAL**

**Core Idea**:
- Use three pointers (prev, curr, next) to reverse links one at a time
- Traverse list once, reversing each link in-place
- Return prev at the end (new head)

**Algorithm**
```
reverseList(head):
    // Edge case: empty or single node
    if head == null or head.next == null:
        return head
    
    // Initialize three pointers
    prev = null
    curr = head
    
    // Traverse and reverse
    while curr != null:
        // Step 1: Save next node (so we don't lose it)
        next = curr.next
        
        // Step 2: Reverse current node's pointer
        curr.next = prev
        
        // Step 3: Move pointers forward
        prev = curr
        curr = next
    
    // prev is now the new head
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
    public ListNode reverseList(ListNode head) {
        // Edge case: empty list or single node
        if (head == null || head.next == null) {
            return head;
        }
        
        // Initialize pointers
        ListNode prev = null;
        ListNode curr = head;
        
        // Traverse and reverse
        while (curr != null) {
            // Save next node before we change curr.next
            ListNode next = curr.next;
            
            // Reverse the link
            curr.next = prev;
            
            // Move pointers forward
            prev = curr;
            curr = next;
        }
        
        // prev is now the new head of the reversed list
        return prev;
    }
}
```

**Example Walkthrough**

Input: `head = [1,2,3]`

```
Initial state:
  List: 1 → 2 → 3 → null
  prev = null
  curr = 1
```

**Iteration 1:**
```
Before:
  prev = null, curr = 1 (val=1)
  List: null    1 → 2 → 3 → null
        prev  curr

Steps:
  next = curr.next = 2 (save it!)
  curr.next = prev     (1 → null)
  prev = curr          (prev = 1)
  curr = next          (curr = 2)

After:
  prev = 1, curr = 2
  List: null ← 1    2 → 3 → null
             prev  curr
```

**Iteration 2:**
```
Before:
  prev = 1, curr = 2 (val=2)
  List: null ← 1    2 → 3 → null
             prev  curr

Steps:
  next = curr.next = 3 (save it!)
  curr.next = prev     (2 → 1)
  prev = curr          (prev = 2)
  curr = next          (curr = 3)

After:
  prev = 2, curr = 3
  List: null ← 1 ← 2    3 → null
                  prev  curr
```

**Iteration 3:**
```
Before:
  prev = 2, curr = 3 (val=3)
  List: null ← 1 ← 2    3 → null
                  prev  curr

Steps:
  next = curr.next = null (save it!)
  curr.next = prev        (3 → 2)
  prev = curr             (prev = 3)
  curr = next             (curr = null)

After:
  prev = 3, curr = null
  List: null ← 1 ← 2 ← 3    null
                       prev curr
```

**Loop ends (curr == null)**

```
Return prev (which points to 3)
Final list: 3 → 2 → 1 → null
Output: [3,2,1] ✓
```

**Complexity Analysis**
- **Time**: O(n) — Visit each node once
- **Space**: O(1) — Only three pointers

---

#### **Approach 2: Recursive - Alternative**

**Core Idea**:
- Recursively reverse the rest of the list
- Then fix the links for current node
- Base case: single node or empty list

**Algorithm**
```
reverseList(head):
    // Base case
    if head == null or head.next == null:
        return head
    
    // Recursively reverse rest of list
    newHead = reverseList(head.next)
    
    // Fix links
    head.next.next = head  // Point next node back to current
    head.next = null       // Current node points to null
    
    return newHead
```

**Code Implementation**
```java
class Solution {
    public ListNode reverseList(ListNode head) {
        // Base case: empty or single node
        if (head == null || head.next == null) {
            return head;
        }
        
        // Recursively reverse the rest
        ListNode newHead = reverseList(head.next);
        
        // Fix the links
        // head.next is now the last node of reversed sublist
        // Make it point back to current node
        head.next.next = head;
        head.next = null;
        
        return newHead;
    }
}
```

**Example Walkthrough**

Input: `head = [1,2,3]`

```
Call Stack:

reverseList(1):
  head = 1, head.next = 2
  
  → reverseList(2):
      head = 2, head.next = 3
      
      → reverseList(3):
          head = 3, head.next = null
          Base case! Return 3
      
      Back in reverseList(2):
        newHead = 3
        head.next.next = head  // 3.next = 2
        head.next = null       // 2.next = null
        List now: 3 → 2 → null, 1 still separate
        Return 3
  
  Back in reverseList(1):
    newHead = 3
    head.next.next = head  // 2.next = 1
    head.next = null       // 1.next = null
    List now: 3 → 2 → 1 → null
    Return 3

Final: 3 → 2 → 1 → null ✓
```

**Complexity Analysis**
- **Time**: O(n) — Visit each node once
- **Space**: O(n) — Recursive call stack

**When to Use**:
- Recursive is elegant and easier to understand conceptually
- But uses O(n) space due to call stack
- Risk of stack overflow for very long lists (n > 1000)
- **Iterative is preferred** for production code

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Recommended |
|----------|------|-------|-------------|
| **Iterative (3 pointers)** | **O(n)** | **O(1)** | **Yes ✅** |
| Recursive | O(n) | O(n) | No (uses stack) |
| Array conversion | O(n) | O(n) | No (extra space) |

**Winner**: **Iterative three-pointer** — meets O(1) space requirement!

### Why Three Pointers Are Necessary

```
Problem: Reversing a link loses reference to rest of list

Example with only prev and curr:
  List: 1 → 2 → 3 → null
  prev = null, curr = 1
  
  curr.next = prev  // 1 → null (reversed!)
  
  Now what? We lost reference to node 2!
  Can't continue ❌

Solution: Save next before reversing
  next = curr.next  // Save 2
  curr.next = prev  // Reverse 1 → null
  curr = next       // Move to 2 ✓

Three pointers let us:
  1. Keep reference to next node
  2. Reverse current link
  3. Move forward without losing data
```

### Why We Initialize prev = null

```
Original list: 1 → 2 → 3 → null
Reversed list: null ← 1 ← 2 ← 3

The old head (1) becomes the new tail.
New tail must point to null.

When we reverse first node:
  1.next = prev
  
If prev = null, then 1 → null ✓
This is correct!

If we initialized prev = head:
  1.next = 1 (cycle!) ❌

prev = null is correct!
```

### Why We Return prev Instead of curr

```
At the end of the loop:
  curr = null (exited loop)
  prev = last node in original list
  
Last node in original list = first node in reversed list
prev is the new head!

Example: 1 → 2 → 3 → null

After all iterations:
  prev = 3 (new head)
  curr = null
  
Return prev ✓
```

### Why We Check head.next == null

```
Edge case: single node list
  head = 1 → null

If we don't check:
  prev = null, curr = 1
  
  Iteration 1:
    next = null
    curr.next = null (already was!)
    prev = 1, curr = null
  
  Return 1 ✓ (works!)

Actually, the loop handles it correctly!
But checking early is cleaner and avoids unnecessary work.

Also handles empty list (head == null):
  Return null immediately ✓
```

### Why Save next Before Reversing

```
Order matters!

Correct order:
  next = curr.next  // Save next
  curr.next = prev  // Reverse link

Wrong order:
  curr.next = prev  // Reverse link
  next = curr.next  // next is now prev! ❌
  
If we reverse first, curr.next no longer points to next node.
We lose reference to rest of list!

Always save next BEFORE modifying curr.next!
```

---

## Critical Edge Cases & Gotchas

### 1. **Empty List**
```java
Input: head = null
Output: null

Edge case: no nodes to reverse
Return null immediately
```

### 2. **Single Node**
```java
Input: head = [5]
Output: [5]

Single node list is already "reversed"
head.next = null, return head
```

### 3. **Two Nodes**
```java
Input: head = [1,2]
Output: [2,1]

Simplest case requiring actual reversal
Original: 1 → 2 → null
Reversed: 2 → 1 → null
```

### 4. **All Same Values**
```java
Input: head = [3,3,3,3]
Output: [3,3,3,3]

Values same but node positions reversed
Important: we reverse the list structure, not values
```

### 5. **Negative Values**
```java
Input: head = [-1,-2,-3]
Output: [-3,-2,-1]

Works with any integer values
```

### 6. **Large List**
```java
Input: head = [1,2,3,...,1000] (1000 nodes)
Output: [1000,999,...,2,1]

Maximum constraint: 1000 nodes
Iterative handles it easily (O(1) space)
Recursive might cause stack overflow!
```

### 7. **Alternating Values**
```java
Input: head = [1,0,1,0,1,0]
Output: [0,1,0,1,0,1]

Pattern in values doesn't affect algorithm
```

### 8. **Boundary Values**
```java
Input: head = [-1000,0,1000]
Output: [1000,0,-1000]

Min and max values within constraints
```

### 9. **Long Chain**
```java
Input: head = [1,2,3,4,5,6,7,8,9,10]
Output: [10,9,8,7,6,5,4,3,2,1]

Longer list still O(n) time, O(1) space
```

### 10. **Consecutive Values**
```java
Input: head = [5,6,7,8,9]
Output: [9,8,7,6,5]

Order of values doesn't matter
We reverse physical structure
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Wrong Order of Operations**
```java
// WRONG - reverse before saving next
while (curr != null) {
    curr.next = prev;  // Lost reference to next!
    ListNode next = curr.next;  // next is now prev, not next node!
    prev = curr;
    curr = next;  // Moving to wrong node
}
```

**Why wrong**: Lose reference to rest of list!

**Dry run failure:**
```
List: 1 → 2 → 3 → null
prev = null, curr = 1

curr.next = prev  // 1 → null
next = curr.next  // next = null (not 2!)

Can't continue to node 2! ❌
```

**Fix**: Save next BEFORE modifying curr.next
```java
while (curr != null) {
    ListNode next = curr.next;  // Save first!
    curr.next = prev;           // Then reverse
    prev = curr;
    curr = next;
}
```

### ❌ **MISTAKE 2: Returning curr Instead of prev**
```java
// WRONG - return curr at the end
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
return curr;  // curr is null! ❌
```

**Why wrong**: curr is null when loop ends!

**Dry run failure:**
```
List: 1 → 2 → null

After all iterations:
  prev = 2 (new head)
  curr = null (exited loop)

return curr → returns null ❌
Should return prev = 2 ✓
```

**Fix**: Return prev
```java
return prev;  // prev is the new head
```

### ❌ **MISTAKE 3: Not Moving Pointers Forward**
```java
// WRONG - missing pointer updates
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    // Missing: prev = curr; curr = next;
}
```

**Why wrong**: Infinite loop! Pointers never move.

**Dry run failure:**
```
List: 1 → 2 → 3 → null
prev = null, curr = 1

Iteration 1:
  next = 2
  curr.next = null
  // prev still null, curr still 1
  
Iteration 2:
  next = 2
  curr.next = null
  // Same state! Infinite loop ❌
```

**Fix**: Move pointers forward
```java
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;  // Move prev
    curr = next;  // Move curr
}
```

### ❌ **MISTAKE 4: Initializing prev = head**
```java
// WRONG - prev should start as null
ListNode prev = head;  // Wrong!
ListNode curr = head;
```

**Why wrong**: Creates a cycle!

**Dry run failure:**
```
List: 1 → 2 → 3 → null
prev = 1, curr = 1

Iteration 1:
  next = 2
  curr.next = prev  // 1.next = 1 (cycle!) ❌
  
1 points to itself! Lost reference to 2 and 3!
```

**Fix**: Initialize prev = null
```java
ListNode prev = null;  // Will become new tail
ListNode curr = head;
```

### ❌ **MISTAKE 5: Modifying Node Values Instead of Links**
```java
// WRONG - trying to reverse by swapping values
// This is NOT reversing the linked list structure!
while (curr != null) {
    // Some code to swap values
    // This doesn't reverse the list structure!
}
```

**Why wrong**: Problem asks to reverse list structure, not values!

**Difference:**
```
Original list: A(1) → B(2) → C(3) → null

Reversing structure (correct):
  C(3) → B(2) → A(1) → null
  Node C is now first, then B, then A

Swapping values (wrong):
  A(3) → B(2) → C(1) → null
  Still same nodes in same order!
  Just values changed
```

**Fix**: Reverse links, not values
```java
curr.next = prev;  // Reverse the link
```

### ❌ **MISTAKE 6: Forgetting Edge Cases**
```java
// WRONG - no check for empty or single node
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    
    while (curr != null) {
        // This works even for edge cases!
        // But checking early is cleaner
    }
    return prev;
}
```

**Why it's not wrong**: Actually works for edge cases!

**Dry run for empty list:**
```
head = null
prev = null, curr = null

while (curr != null) → false
Loop doesn't execute

return prev = null ✓
```

**Dry run for single node:**
```
head = 1 → null
prev = null, curr = 1

Iteration 1:
  next = null
  curr.next = null
  prev = 1, curr = null

return prev = 1 ✓
```

**Not wrong, but**: Checking early is cleaner
```java
if (head == null || head.next == null) {
    return head;
}
```

### ❌ **MISTAKE 7: Using curr.next.next**
```java
// WRONG - might cause NullPointerException
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = curr.next.next;  // What if curr.next is null? ❌
}
```

**Why wrong**: curr.next might be null!

**Dry run failure:**
```
List: 1 → 2 → null
After first iteration:
  prev = 1, curr = 2
  curr.next = null

  curr = curr.next.next → null.next → NullPointerException! ❌
```

**Fix**: Use saved next variable
```java
curr = next;  // Use the saved next
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| **Traverse list** | O(n) | Visit each node once |
| **Reverse each link** | O(1) | Constant per node |
| **Total** | **O(n)** | Linear in list length |

**Time analysis**:
```
Must visit each node exactly once:
  n nodes → n iterations
  Each iteration: O(1) work
  Total: O(n)

Cannot do better:
  Must touch every node to reverse it
  O(n) is optimal!
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| prev pointer | O(1) | Single reference |
| curr pointer | O(1) | Single reference |
| next pointer | O(1) | Single reference |
| **Total (Iterative)** | **O(1)** | Constant space |
| **Total (Recursive)** | **O(n)** | Call stack |

**Space analysis**:
```
Iterative approach:
  Only three pointers (prev, curr, next)
  No extra data structures
  Space: O(1) ✓

Recursive approach:
  Call stack depth = n
  Each call stores: head, newHead
  Space: O(n)
  
For constraint of n ≤ 1000:
  Recursive might cause stack overflow
  Iterative is safer and meets O(1) requirement
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `head = [1,2,3,4]`

**Expected Output:** `[4,3,2,1]`

---

**Initial State:**
```
List: 1 → 2 → 3 → 4 → null

Pointers:
  prev = null
  curr = 1
  next = undefined

Visualization:
  null    1 → 2 → 3 → 4 → null
  prev  curr
```

---

**Iteration 1: Reverse node 1**
```
Before:
  prev = null
  curr = 1 (val=1)
  
Steps:
  1. next = curr.next = 2 (save it!)
  2. curr.next = prev (1 → null)
  3. prev = curr (prev = 1)
  4. curr = next (curr = 2)

After:
  prev = 1
  curr = 2
  
List state:
  null ← 1    2 → 3 → 4 → null
       prev  curr
```

---

**Iteration 2: Reverse node 2**
```
Before:
  prev = 1
  curr = 2 (val=2)
  
Steps:
  1. next = curr.next = 3 (save it!)
  2. curr.next = prev (2 → 1)
  3. prev = curr (prev = 2)
  4. curr = next (curr = 3)

After:
  prev = 2
  curr = 3
  
List state:
  null ← 1 ← 2    3 → 4 → null
            prev  curr
```

---

**Iteration 3: Reverse node 3**
```
Before:
  prev = 2
  curr = 3 (val=3)
  
Steps:
  1. next = curr.next = 4 (save it!)
  2. curr.next = prev (3 → 2)
  3. prev = curr (prev = 3)
  4. curr = next (curr = 4)

After:
  prev = 3
  curr = 4
  
List state:
  null ← 1 ← 2 ← 3    4 → null
                 prev  curr
```

---

**Iteration 4: Reverse node 4**
```
Before:
  prev = 3
  curr = 4 (val=4)
  
Steps:
  1. next = curr.next = null (save it!)
  2. curr.next = prev (4 → 3)
  3. prev = curr (prev = 4)
  4. curr = next (curr = null)

After:
  prev = 4
  curr = null
  
List state:
  null ← 1 ← 2 ← 3 ← 4    null
                    prev  curr
```

---

**Loop Ends (curr == null)**

```
Return prev = 4

Final reversed list:
  4 → 3 → 2 → 1 → null

Output: [4,3,2,1] ✓
```

---

### Step-by-Step Pointer Visualization

```
Original: 1 → 2 → 3 → null

Step 0 (initial):
  null    1 → 2 → 3 → null
  ↑       ↑
  prev  curr

Step 1 (after reversing 1):
  null ← 1    2 → 3 → null
         ↑    ↑
       prev  curr

Step 2 (after reversing 2):
  null ← 1 ← 2    3 → null
             ↑    ↑
           prev  curr

Step 3 (after reversing 3):
  null ← 1 ← 2 ← 3    null
                 ↑    ↑
               prev  curr (loop ends)

Return prev
Result: 3 → 2 → 1 → null
```

---

## Comparison of Approaches

| Approach | Time | Space | Stack Safe | Recommended |
|----------|------|-------|------------|-------------|
| **Iterative (3 pointers)** | **O(n)** | **O(1)** | **Yes ✅** | **Yes ✅** |
| Recursive | O(n) | O(n) | No (n≤1000) | No |
| Array conversion | O(n) | O(n) | Yes | No (extra space) |

**Winner**: **Iterative three-pointer** — O(1) space, stack safe!

**Array Conversion Approach (not recommended):**
```java
// Convert to array, reverse, rebuild list
// O(n) time, O(n) space
public ListNode reverseList(ListNode head) {
    List<Integer> values = new ArrayList<>();
    while (head != null) {
        values.add(head.val);
        head = head.next;
    }
    Collections.reverse(values);
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    for (int val : values) {
        curr.next = new ListNode(val);
        curr = curr.next;
    }
    return dummy.next;
}
// Works but uses O(n) space ❌
```

---

## Key Takeaways

1. **Three pointers essential** — prev, curr, next track state
2. **Save next first** — before modifying curr.next
3. **Reverse in-place** — change links, not values
4. **prev starts as null** — becomes new tail
5. **Return prev** — new head after loop
6. **O(n) time, O(1) space** — optimal solution
7. **Iterative preferred** — no stack overflow risk
8. **Edge cases handled** — empty, single node work correctly
9. **Order matters** — save, reverse, move
10. **Visit each node once** — single pass through list

---

## Interview Tips

**What to say in an interview:**

> "To reverse a singly linked list in-place with O(1) space, I'll use the three-pointer technique. I need three pointers: prev (initially null, will become the new tail), curr (initially head), and next (to save the reference before changing links). The algorithm is straightforward: while curr is not null, I first save the next node, then reverse the current node's link by pointing it to prev, and finally move both prev and curr forward. When the loop ends, curr will be null and prev will point to the last node of the original list, which is the new head. This works because we're reversing each link one at a time while maintaining references to all necessary nodes. The time complexity is O(n) since we visit each node exactly once, and the space complexity is O(1) because we only use three pointers regardless of list length."

**Key points to mention:**
1. **Three pointers** — prev, curr, next
2. **prev = null initially** — becomes new tail
3. **Save next first** — before reversing link
4. **Reverse each link** — curr.next = prev
5. **Move pointers forward** — prev = curr, curr = next
6. **Return prev** — new head of reversed list
7. **O(n) time** — single pass
8. **O(1) space** — only three pointers

**Common Follow-ups:**
- "Can you do it recursively?" → Yes, but uses O(n) space for call stack
- "What if it's a doubly linked list?" → Easier, just swap next and prev for each node
- "Can you reverse only part of the list?" → Yes, similar technique with boundaries
- "What's the space complexity of recursive?" → O(n) due to call stack
- "How do you handle cycles?" → This algorithm doesn't handle cycles; would need cycle detection first

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Reverse Linked List** | Easy | **Three-Pointer In-Place** | **This problem** |
| Reverse Linked List II | Medium | Three-Pointer + Boundaries | Reverse subsection |
| Reverse Nodes in k-Group | Hard | Reverse + Grouping | Reverse in groups of k |
| Palindrome Linked List | Easy | Reverse + Two Pointers | Find middle, reverse half |
| Swap Nodes in Pairs | Medium | Pointer Manipulation | Swap adjacent nodes |
| Reorder List | Medium | Find Middle + Reverse | Interleave two halves |

**Pattern Progression**:
1. **Reverse Linked List** (this) — Basic reversal
2. **Reverse Linked List II** — Reverse subsection [left, right]
3. **Reverse Nodes in k-Group** — Reverse in groups of k
4. **Advanced applications** — Palindrome check, reordering

---

## Final Pattern Label

✅ **Three-Pointer Iterative In-Place Reversal**

**Remember:** This is the **three-pointer technique** for reversing a singly linked list in-place. Initialize **prev = null** (will become new tail) and **curr = head**. While curr is not null: first **save next node** (next = curr.next), then **reverse the link** (curr.next = prev), finally **move pointers forward** (prev = curr, curr = next). The order is critical: **save before reversing**! When the loop ends, curr is null and **prev is the new head**. This achieves **O(n) time and O(1) space**, meeting the optimal complexity requirement. The key insight is that three pointers let us reverse each link without losing references to the rest of the list. For the recursive approach, the idea is to reverse the rest of the list first, then fix the current node's links, but this uses O(n) space for the call stack, so iterative is preferred. Always test with edge cases: empty list, single node, and two nodes!
