# Reverse Linked List II

## Problem Description

**Difficulty**: Medium

You are given the `head` of a **singly linked list** and two integers `left` and `right` where `left <= right`, reverse the nodes of the list from position `left` to position `right` (**1-indexed**), and return the reversed list.

**Key Constraints:**
- Positions are **1-indexed** (not 0-indexed!)
- Reverse nodes from position `left` to `right` **inclusive**
- `left <= right` (always valid range)
- Must handle edge cases (reversing from head, single node, etc.)

**Important Observations:**
- If `left == right`, no reversal needed (single node)
- If `left == 1`, reversing from head (need dummy node)
- If `right == n`, reversing to end
- Need to track nodes **before** and **after** reversal range

**Follow-up Challenge:**
Could you do it in **one pass**?

**Visual Example:**
```
Original: 1 → 2 → 3 → 4 → 5
Reverse positions 2 to 4:

Step 1: Identify range
  Before range: 1
  Range to reverse: 2 → 3 → 4
  After range: 5

Step 2: Reverse the range
  Reversed: 4 → 3 → 2

Step 3: Reconnect
  1 → 4 → 3 → 2 → 5

Result: [1, 4, 3, 2, 5]
```

---

## Examples

### Example 1:
```
Input: head = [1,2,3,4,5], left = 2, right = 4
Output: [1,4,3,2,5]

Explanation:
  Original: 1 → 2 → 3 → 4 → 5
  Reverse positions 2 to 4:
    Keep: 1
    Reverse: 2 → 3 → 4 becomes 4 → 3 → 2
    Keep: 5
  Result: 1 → 4 → 3 → 2 → 5
```

### Example 2:
```
Input: head = [1,2,3,4,5], left = 1, right = 3
Output: [3,2,1,4,5]

Explanation:
  Reverse from head (position 1)
  Reverse: 1 → 2 → 3 becomes 3 → 2 → 1
  Keep: 4 → 5
  Result: 3 → 2 → 1 → 4 → 5
```

### Example 3:
```
Input: head = [1,1], left = 1, right = 1
Output: [1,1]

Explanation:
  Single position (left == right)
  No reversal needed
  Result: [1, 1] (unchanged)
```

### Example 4:
```
Input: head = [1,2,3,4,5], left = 1, right = 5
Output: [5,4,3,2,1]

Explanation:
  Reverse entire list
  Same as "Reverse Linked List I"
  Result: [5, 4, 3, 2, 1]
```

### Example 5:
```
Input: head = [5], left = 1, right = 1
Output: [5]

Explanation:
  Single node list
  No reversal needed
  Result: [5]
```

### Example 6:
```
Input: head = [1,2,3], left = 2, right = 3
Output: [1,3,2]

Explanation:
  Reverse last two nodes
  Keep: 1
  Reverse: 2 → 3 becomes 3 → 2
  Result: 1 → 3 → 2
```

### Example 7:
```
Input: head = [1,2,3,4,5], left = 2, right = 2
Output: [1,2,3,4,5]

Explanation:
  Reversing single node (position 2)
  No change
  Result: [1, 2, 3, 4, 5]
```

### Example 8:
```
Input: head = [1,2,3,4,5,6,7], left = 3, right = 5
Output: [1,2,5,4,3,6,7]

Explanation:
  Keep: 1 → 2
  Reverse: 3 → 4 → 5 becomes 5 → 4 → 3
  Keep: 6 → 7
  Result: 1 → 2 → 5 → 4 → 3 → 6 → 7
```

### Example 9:
```
Input: head = [3,5], left = 1, right = 2
Output: [5,3]

Explanation:
  Two-node list, reverse both
  Result: [5, 3]
```

### Example 10:
```
Input: head = [1,2,3,4,5,6], left = 4, right = 6
Output: [1,2,3,6,5,4]

Explanation:
  Reverse from position 4 to end
  Keep: 1 → 2 → 3
  Reverse: 4 → 5 → 6 becomes 6 → 5 → 4
  Result: 1 → 2 → 3 → 6 → 5 → 4
```

## Constraints
- The number of nodes in the list is `n`
- `1 <= n <= 500`
- `-500 <= Node.val <= 500`
- `1 <= left <= right <= n`
- Positions are **1-indexed**

**Recommended Complexity**: 
- Time: O(n) — one pass through list
- Space: O(1) — constant extra space

---

## Pattern Recognition

**Primary Pattern**: **In-place Partial Reversal with Pointer Manipulation**

**Why This Pattern?**
- Need to reverse only a **portion** of the list
- Must maintain connections to **before** and **after** reversed section
- Requires careful **pointer tracking**
- Can be done in **one pass**
- Similar to full reversal but with boundaries

**Key Insight**: Three-Pointer Reversal with Boundaries
```
Full list reversal uses:
  prev = null
  curr = head
  
  while curr != null:
    next = curr.next
    curr.next = prev
    prev = curr
    curr = next

Partial reversal needs:
  1. Find node BEFORE left position
  2. Reverse from left to right
  3. Connect reversed section back
```

**Why Boundaries Matter**:
```
Example: 1 → 2 → 3 → 4 → 5, left=2, right=4

Before reversal:
  beforeLeft = node 1
  leftNode = node 2
  rightNode = node 4
  afterRight = node 5

After reversal:
  beforeLeft.next should be node 4 (new start of reversed)
  leftNode.next should be node 5 (afterRight)
  
Connections:
  1 → 4 (beforeLeft → new head of reversed)
  2 → 5 (old leftNode → afterRight)
  4 → 3 → 2 (reversed section)
```

**The One-Pass Strategy**:
```
Pass 1: Navigate to position left-1
  Track the node before left position
  This is where we'll reconnect later
  
Pass 2: Reverse from left to right
  Standard three-pointer reversal
  But stop after (right - left + 1) nodes
  
Pass 3: Reconnect
  beforeLeft.next = reversed head
  leftNode.next = afterRight
  
All in ONE traversal! ✓
```

**Example: One-Pass Reversal**
```
List: 1 → 2 → 3 → 4 → 5
left = 2, right = 4

Step 1: Find beforeLeft (position 1)
  dummy → 1 → 2 → 3 → 4 → 5
  beforeLeft = node 1
  curr = node 2 (leftNode)

Step 2: Reverse 2 → 3 → 4
  Start: 2 → 3 → 4
  After 1 reversal: 3 → 2 (4 pending)
  After 2 reversals: 4 → 3 → 2
  
  curr now at node 5 (afterRight)

Step 3: Reconnect
  beforeLeft.next = node 4 (reversed head)
  leftNode.next = node 5 (curr/afterRight)
  
  Result: 1 → 4 → 3 → 2 → 5 ✓
```

**Why Dummy Node Helps**:
```
Problem: What if left = 1? (reversing from head)
  
Without dummy:
  beforeLeft doesn't exist!
  Need special case ❌
  
With dummy:
  dummy → 1 → 2 → 3 → 4 → 5
  beforeLeft = dummy
  Always exists! ✓
  Return dummy.next at end
  
Dummy eliminates edge case!
```

**Tracking Key Nodes**:
```
Four critical nodes:

1. beforeLeft: Node at position left-1
   - Where to attach reversed section
   - Use dummy if left = 1

2. leftNode: Node at position left
   - Will become tail of reversed section
   - Needs to point to afterRight at end

3. rightNode: Node at position right
   - Will become head of reversed section
   - Don't actually need to track explicitly

4. afterRight: Node at position right+1
   - Remains unchanged
   - leftNode points here after reversal
```

**Visual: Pointer Movements**
```
Original: 1 → 2 → 3 → 4 → 5
Reverse 2 to 4:

Before:
  beforeLeft = 1
  curr = 2
  prev = beforeLeft
  
Reversal step 1:
  next = 3
  2.next = 1
  prev = 2
  curr = 3
  
Reversal step 2:
  next = 4
  3.next = 2
  prev = 3
  curr = 4
  
Reversal step 3:
  next = 5
  4.next = 3
  prev = 4
  curr = 5
  
After:
  1.next = 4 (beforeLeft.next = prev)
  2.next = 5 (leftNode.next = curr)
  
Result: 1 → 4 → 3 → 2 → 5 ✓
```

**Alternative: Iterative with Stack**:
```
Could use stack to reverse:
  1. Push nodes from left to right onto stack
  2. Pop and rebuild
  
But uses O(right - left + 1) space ❌
In-place reversal is better ✓
```

**Why One Pass Works**:
```
Count operations:
  1. Navigate to left-1: O(left)
  2. Reverse left to right: O(right - left + 1)
  3. Reconnect: O(1)
  
Total: O(left) + O(right - left + 1) = O(right)
Since right ≤ n, this is O(n) ✓

No need to traverse multiple times!
```

**Edge Cases Handled**:
```
1. left = 1: Dummy node handles
2. right = n: Works naturally (curr becomes null)
3. left = right: Reverses 0 nodes (no-op)
4. Single node: Works (n=1, left=1, right=1)
5. Two nodes: Works for all combinations
```

**Related Patterns**:
1. **Reverse Linked List** — Full reversal (simpler)
2. **Reverse Nodes in k-Group** — Multiple reversals
3. **Rotate List** — Partial list manipulation

---

## Algorithm & Approach

### Core Insight

**Why One-Pass In-Place Reversal Works:**
```
Key observations:
  1. Need node before left position for reconnection
  2. Reverse sublist using standard three-pointer technique
  3. Track boundaries to reconnect properly
  4. Dummy node eliminates edge case for left = 1
  5. One traversal is sufficient
```

**The Optimal Strategy**:
```
Key steps:
  1. Create dummy node pointing to head
  2. Navigate to node before left position
  3. Reverse nodes from left to right using three pointers
  4. Reconnect reversed section to rest of list
  5. Return dummy.next
```

### Step-by-Step Algorithm

---

#### **Approach: One-Pass In-Place Reversal - OPTIMAL**

**Core Idea**:
- Use dummy node to handle edge cases
- Navigate to position before left
- Reverse sublist in-place
- Reconnect boundaries
- O(n) time, O(1) space

**Algorithm**
```
reverseBetween(head, left, right):
    // Edge case: no reversal needed
    if left == right:
        return head
    
    // Create dummy node
    dummy = new ListNode(0)
    dummy.next = head
    
    // Step 1: Navigate to node before left
    beforeLeft = dummy
    for i = 1 to left - 1:
        beforeLeft = beforeLeft.next
    
    // Step 2: Reverse from left to right
    prev = beforeLeft
    curr = beforeLeft.next
    leftNode = curr  // Save reference to leftNode
    
    for i = 0 to (right - left + 1):
        next = curr.next
        curr.next = prev
        prev = curr
        curr = next
    
    // Step 3: Reconnect
    beforeLeft.next = prev      // Connect to reversed head
    leftNode.next = curr        // Connect to afterRight
    
    return dummy.next
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
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // Edge case: no reversal needed (though guaranteed left <= right)
        if (left == right) {
            return head;
        }
        
        // Create dummy node to handle edge case where left = 1
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Step 1: Navigate to the node before left position
        ListNode beforeLeft = dummy;
        for (int i = 1; i < left; i++) {
            beforeLeft = beforeLeft.next;
        }
        
        // Step 2: Reverse the sublist from left to right
        ListNode prev = beforeLeft;
        ListNode curr = beforeLeft.next;
        ListNode leftNode = curr;  // Save reference to node at left position
        
        // Reverse (right - left + 1) nodes
        for (int i = 0; i <= right - left; i++) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        // Step 3: Reconnect the reversed section
        beforeLeft.next = prev;   // Connect beforeLeft to new head of reversed section
        leftNode.next = curr;     // Connect tail of reversed section to afterRight
        
        // Return new head (skip dummy)
        return dummy.next;
    }
}
```

**Example Walkthrough**

Input: `head = [1,2,3,4,5]`, `left = 2`, `right = 4`
Expected: `[1,4,3,2,5]`

```
Initial State:
  List: 1 → 2 → 3 → 4 → 5
  left = 2, right = 4
  Create dummy: 0 → 1 → 2 → 3 → 4 → 5
```

**Step 1: Navigate to Node Before Left**
```
beforeLeft = dummy (position 0)

Loop: i = 1 to left - 1 = 1
  i = 1:
    beforeLeft = beforeLeft.next = node 1

After loop:
  beforeLeft = node 1 (position 1, before left)
  
State:
  dummy → 1 → 2 → 3 → 4 → 5
          ↑
      beforeLeft
```

**Step 2: Reverse Sublist**
```
Initialize:
  prev = beforeLeft = node 1
  curr = beforeLeft.next = node 2
  leftNode = node 2 (saved reference)

Loop: i = 0 to right - left = 0 to 2 (3 iterations)

Iteration 1 (i = 0):
  next = curr.next = node 3
  curr.next = prev = node 1
  prev = curr = node 2
  curr = next = node 3
  
  State: 1 ← 2    3 → 4 → 5

Iteration 2 (i = 1):
  next = curr.next = node 4
  curr.next = prev = node 2
  prev = curr = node 3
  curr = next = node 4
  
  State: 1 ← 2 ← 3    4 → 5

Iteration 3 (i = 2):
  next = curr.next = node 5
  curr.next = prev = node 3
  prev = curr = node 4
  curr = next = node 5
  
  State: 1 ← 2 ← 3 ← 4    5

After loop:
  prev = node 4 (new head of reversed section)
  curr = node 5 (afterRight)
  leftNode = node 2 (tail of reversed section)
```

**Step 3: Reconnect**
```
beforeLeft.next = prev
  node 1.next = node 4
  
leftNode.next = curr
  node 2.next = node 5

Final connections:
  dummy → 1 → 4 → 3 → 2 → 5
  
Return dummy.next = node 1
Result: 1 → 4 → 3 → 2 → 5 ✓
```

**Complexity Analysis**
- **Time**: O(n) — Single pass, traverse up to n nodes
- **Space**: O(1) — Only pointer variables

---

**Example with left = 1 (Reversing from Head)**

Input: `head = [1,2,3,4,5]`, `left = 1`, `right = 3`
Expected: `[3,2,1,4,5]`

```
Initial:
  dummy → 1 → 2 → 3 → 4 → 5
```

**Step 1: Navigate**
```
Loop: i = 1 to left - 1 = 0 (no iterations)
  beforeLeft stays at dummy

State:
  dummy → 1 → 2 → 3 → 4 → 5
  ↑
beforeLeft
```

**Step 2: Reverse**
```
prev = dummy
curr = node 1
leftNode = node 1

Iteration 1:
  1.next = dummy
  prev = 1, curr = 2

Iteration 2:
  2.next = 1
  prev = 2, curr = 3

Iteration 3:
  3.next = 2
  prev = 3, curr = 4

After:
  dummy ← 1 ← 2 ← 3    4 → 5
```

**Step 3: Reconnect**
```
dummy.next = node 3
node 1.next = node 4

Result: dummy → 3 → 2 → 1 → 4 → 5

Return dummy.next = 3 → 2 → 1 → 4 → 5 ✓
```

---

**Example with left = right (No Reversal)**

Input: `head = [1,2,3,4,5]`, `left = 3`, `right = 3`
Expected: `[1,2,3,4,5]` (unchanged)

```
Early return: left == right
  return head immediately
  
No processing needed ✓
```

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Passes | Complexity | Recommended |
|----------|------|-------|--------|------------|-------------|
| **One-Pass In-Place** | **O(n)** | **O(1)** | **1** | **Medium** | **Yes ✅** |
| Two-Pass (Find then Reverse) | O(n) | O(1) | 2 | Simple | No (inefficient) |
| Stack-Based | O(n) | O(k) | 1 | Simple | No (extra space) |
| Recursion | O(n) | O(n) stack | 1 | Complex | No (stack space) |

**Winner**: **One-pass in-place reversal** — optimal time, space, and satisfies follow-up!

### Why One Pass Works

```
Traditional two-pass:
  Pass 1: Find left and right nodes
  Pass 2: Reverse between them
  Total: 2 traversals ❌

One-pass approach:
  Navigate + Reverse + Reconnect in one traversal
  Stop at right position, no need to continue
  Total: 1 traversal ✓
  
Both O(n) but one-pass more efficient!
```

### Why Dummy Node Simplifies

```
Without dummy (left = 1):
  beforeLeft doesn't exist
  Need special case:
    if left == 1:
        head = reversed head
        // Different logic
    else:
        beforeLeft.next = reversed head
        // Normal logic
  Complex! ❌

With dummy:
  beforeLeft = dummy (when left = 1)
  Always have beforeLeft
  Same logic for all cases! ✓
  Just return dummy.next
  
Dummy eliminates edge case!
```

### Why Save leftNode Reference

```
During reversal:
  leftNode (originally at left position)
  Becomes tail of reversed section
  
Need to connect leftNode.next to afterRight
  
If we don't save reference:
  After reversal, hard to find leftNode
  Would need to traverse reversed section ❌
  
By saving reference:
  Direct access to leftNode
  O(1) reconnection ✓
```

### Why Loop (right - left + 1) Times

```
Positions are inclusive:
  left = 2, right = 4
  Positions: 2, 3, 4 (3 nodes)
  right - left + 1 = 4 - 2 + 1 = 3 ✓

Example: [1,2,3,4,5], left=2, right=4
  Need to reverse nodes 2, 3, 4
  That's 3 nodes
  Loop 3 times: i = 0, 1, 2 ✓
  
Formula: right - left + 1 gives count!
```

### Why This is Optimal

```
Time complexity:
  Navigate to left-1: O(left)
  Reverse: O(right - left + 1)
  Reconnect: O(1)
  Total: O(right) ≤ O(n)
  Optimal! ✓

Space complexity:
  Variables: dummy, beforeLeft, prev, curr, next, leftNode
  All O(1)
  No arrays, no recursion
  Optimal! ✓

Satisfies follow-up (one pass)! ✓
```

---

## Critical Edge Cases & Gotchas

### 1. **Reverse from Head (left = 1)**
```java
Input: head = [1,2,3,4,5], left = 1, right = 3
Output: [3,2,1,4,5]

beforeLeft = dummy
Dummy node handles this cleanly
New head returned via dummy.next
```

### 2. **Reverse to End (right = n)**
```java
Input: head = [1,2,3,4,5], left = 3, right = 5
Output: [1,2,5,4,3]

curr becomes null after reversal
leftNode.next = null (correct)
No special handling needed
```

### 3. **Single Node Reversal (left = right)**
```java
Input: head = [1,2,3,4,5], left = 3, right = 3
Output: [1,2,3,4,5]

Early return (no change)
Or reverses 1 node (no-op)
Both work correctly
```

### 4. **Entire List Reversal (left = 1, right = n)**
```java
Input: head = [1,2,3,4,5], left = 1, right = 5
Output: [5,4,3,2,1]

Same as "Reverse Linked List I"
Algorithm handles naturally
```

### 5. **Two-Node List**
```java
Input: head = [1,2], left = 1, right = 2
Output: [2,1]

Smallest non-trivial case
Dummy handles left = 1
Works correctly
```

### 6. **Single-Node List**
```java
Input: head = [1], left = 1, right = 1
Output: [1]

Early return (no change)
Handles correctly
```

### 7. **Reverse Last Two Nodes**
```java
Input: head = [1,2,3,4,5], left = 4, right = 5
Output: [1,2,3,5,4]

Navigate to position 3
Reverse 4 and 5
Reconnect correctly
```

### 8. **Reverse First Two Nodes**
```java
Input: head = [1,2,3,4,5], left = 1, right = 2
Output: [2,1,3,4,5]

beforeLeft = dummy
Reverse 1 and 2
Return dummy.next = 2
```

### 9. **Long List with Small Range**
```java
Input: head = [1,2,3,4,5,6,7,8,9,10], left = 5, right = 7
Output: [1,2,3,4,7,6,5,8,9,10]

Navigate to position 4
Reverse 5,6,7
Rest unchanged
```

### 10. **Maximum Size List**
```java
Input: 500-node list, left = 1, right = 500
Output: Fully reversed list

Maximum constraint
O(n) time handles efficiently
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Off-by-One in Navigation Loop**
```java
// WRONG - navigates to position left, not left-1
ListNode beforeLeft = dummy;
for (int i = 1; i <= left; i++) {  // WRONG! Should be < left ❌
    beforeLeft = beforeLeft.next;
}
```

**Why wrong**: Need node BEFORE left!

**Dry run failure:**
```
List: 1 → 2 → 3 → 4 → 5, left = 2

Wrong loop (i = 1 to 2):
  i = 1: beforeLeft = node 1
  i = 2: beforeLeft = node 2 ❌
  
Now beforeLeft is AT left position, not before it!

Correct loop (i = 1 to 1):
  i = 1: beforeLeft = node 1 ✓
  
beforeLeft at position 1, before left = 2 ✓
```

**Fix**: Loop while i < left
```java
for (int i = 1; i < left; i++) {  ✓
    beforeLeft = beforeLeft.next;
}
```

### ❌ **MISTAKE 2: Wrong Number of Reversal Iterations**
```java
// WRONG - reversing wrong number of nodes
for (int i = 0; i < right - left; i++) {  // WRONG! Missing +1 ❌
    // reversal logic
}
```

**Why wrong**: Need to reverse (right - left + 1) nodes!

**Dry run failure:**
```
left = 2, right = 4
Nodes to reverse: positions 2, 3, 4 (3 nodes)

Wrong: right - left = 2
  Only 2 iterations
  Only reverse nodes 2 and 3 ❌
  Node 4 not reversed!

Correct: right - left + 1 = 3
  3 iterations
  Reverse nodes 2, 3, 4 ✓
```

**Fix**: Loop right - left + 1 times
```java
for (int i = 0; i <= right - left; i++) {  ✓
    // or: for (int i = 0; i < right - left + 1; i++)
}
```

### ❌ **MISTAKE 3: Not Saving leftNode Reference**
```java
// WRONG - losing reference to leftNode
ListNode prev = beforeLeft;
ListNode curr = beforeLeft.next;
// Missing: ListNode leftNode = curr; ❌

// Later after reversal:
// Need leftNode but don't have reference! ❌
```

**Why wrong**: Can't reconnect without leftNode!

**Dry run failure:**
```
After reversal:
  prev = node 4 (new head of reversed)
  curr = node 5 (afterRight)
  
Need to set: leftNode.next = curr
But we don't have leftNode reference! ❌

Would need to traverse:
  Start at prev = node 4
  Follow: 4 → 3 → 2 to find node 2
  Extra O(k) work ❌
```

**Fix**: Save leftNode before reversal
```java
ListNode leftNode = curr;  ✓
// Now can use leftNode.next = curr later
```

### ❌ **MISTAKE 4: Forgetting Dummy Node**
```java
// WRONG - no dummy node
ListNode beforeLeft = head;  // WRONG when left = 1! ❌
for (int i = 1; i < left; i++) {
    beforeLeft = beforeLeft.next;
}
```

**Why wrong**: Fails when left = 1!

**Dry run failure:**
```
List: 1 → 2 → 3 → 4 → 5, left = 1

Without dummy:
  beforeLeft = head = node 1
  Loop: i = 1 to 0 (no iterations)
  beforeLeft still at node 1 ❌
  
  Need beforeLeft BEFORE node 1
  But there's no node before head! ❌
  
  Special case needed ❌
```

**Fix**: Use dummy node
```java
ListNode dummy = new ListNode(0);
dummy.next = head;
ListNode beforeLeft = dummy;  ✓
// Now beforeLeft can be at position 0 (dummy)
```

### ❌ **MISTAKE 5: Wrong Reconnection**
```java
// WRONG - connecting in wrong order
beforeLeft.next = curr;       // WRONG! ❌
leftNode.next = prev;         // WRONG! ❌
```

**Why wrong**: Mixed up prev and curr!

**Dry run failure:**
```
After reversal:
  prev = node 4 (head of reversed section)
  curr = node 5 (afterRight)
  leftNode = node 2 (tail of reversed section)

Wrong reconnection:
  beforeLeft.next = curr = node 5 ❌
    Skips reversed section!
    
  leftNode.next = prev = node 4 ❌
    Creates cycle or wrong order!

Correct:
  beforeLeft.next = prev = node 4 ✓
    Connects to head of reversed
    
  leftNode.next = curr = node 5 ✓
    Connects tail to afterRight
```

**Fix**: Connect correctly
```java
beforeLeft.next = prev;    // Connect to reversed head ✓
leftNode.next = curr;      // Connect to afterRight ✓
```

### ❌ **MISTAKE 6: Not Returning dummy.next**
```java
// WRONG - returning original head
public ListNode reverseBetween(ListNode head, int left, int right) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    // ... reversal logic
    
    return head;  // WRONG when left = 1! ❌
}
```

**Why wrong**: Head might change if left = 1!

**Dry run failure:**
```
List: 1 → 2 → 3, left = 1, right = 2

After reversal:
  dummy → 2 → 1 → 3
  Original head = node 1
  New head = node 2

Return head (node 1):
  Returns 1 → 3 ❌
  Missing node 2!

Return dummy.next (node 2):
  Returns 2 → 1 → 3 ✓
```

**Fix**: Return dummy.next
```java
return dummy.next;  ✓
```

### ❌ **MISTAKE 7: Initializing prev Incorrectly**
```java
// WRONG - starting prev at null
ListNode prev = null;  // WRONG! ❌
ListNode curr = beforeLeft.next;
```

**Why wrong**: Need prev = beforeLeft!

**Dry run failure:**
```
List: 1 → 2 → 3 → 4 → 5, left = 2

With prev = null:
  Iteration 1:
    2.next = null ❌
    
  Node 2 disconnects from node 1!
  Lost the connection!

With prev = beforeLeft (node 1):
  Iteration 1:
    2.next = 1 ✓
    
  Maintains connection properly!
```

**Fix**: Initialize prev = beforeLeft
```java
ListNode prev = beforeLeft;  ✓
```

### ❌ **MISTAKE 8: Modifying beforeLeft During Reversal**
```java
// WRONG - moving beforeLeft during reversal
for (int i = 0; i <= right - left; i++) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
    beforeLeft = prev;  // WRONG! ❌
}
```

**Why wrong**: Loses reference to beforeLeft!

**Issue:**
```
Need beforeLeft to stay at position left - 1
For final reconnection

If we move it:
  Can't reconnect properly ❌
  
Keep beforeLeft fixed! ✓
```

**Fix**: Don't modify beforeLeft in loop
```java
// beforeLeft should not appear in reversal loop
```

### ❌ **MISTAKE 9: Not Handling left = right**
```java
// WRONG - doing reversal when left = right
// No early return
ListNode dummy = new ListNode(0);
dummy.next = head;

// Proceeds with reversal even when left = right ❌
// Inefficient but might work
```

**Why suboptimal**: Unnecessary work!

**Fix**: Add early return
```java
if (left == right) {
    return head;  ✓
}
// Avoids unnecessary operations
```

### ❌ **MISTAKE 10: Using 0-Indexed Thinking**
```java
// WRONG - thinking positions are 0-indexed
// Problem says 1-indexed!

// Example: head = [1,2,3,4,5], left = 2, right = 4
// Position 2 is node 2 (not node 3!)
// Need to account for 1-indexed positions

for (int i = 0; i < left; i++) {  // WRONG! ❌
    beforeLeft = beforeLeft.next;
}
```

**Why wrong**: Off-by-one due to indexing!

**Fix**: Use 1-indexed loops
```java
for (int i = 1; i < left; i++) {  ✓
    beforeLeft = beforeLeft.next;
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

**Step-by-Step Analysis:**
```
Step 1: Navigate to position left - 1
  Worst case: left = n
  Time: O(n)

Step 2: Reverse from left to right
  Number of reversals: right - left + 1
  Worst case: right - left + 1 = n
  Time: O(n)

Step 3: Reconnect
  Constant time operations
  Time: O(1)

Total: O(n) + O(n) + O(1) = O(n)
```

**Best and Worst Cases:**
```
Best case: left = right
  Early return: O(1) ✓

Typical case: Reverse middle portion
  Navigate: O(left)
  Reverse: O(right - left + 1)
  Total: O(right) ≤ O(n)

Worst case: left = 1, right = n
  Reverse entire list
  Time: O(n)

All cases: O(n) ✓
```

**One Pass Confirmation:**
```
Single traversal through list:
  Start at head
  Move to position left - 1
  Reverse to position right
  Total nodes visited: ≤ n
  
Only one pass through list! ✓
Satisfies follow-up requirement! ✓
```

### Space Complexity: **O(1)**

```
Variables used:
  - dummy: O(1)
  - beforeLeft: O(1)
  - prev: O(1)
  - curr: O(1)
  - next: O(1)
  - leftNode: O(1)

No arrays, no recursion, no extra data structures
Total space: O(1) ✓
```

**Comparison:**
```
In-place reversal: O(1) space ✓
Stack-based: O(right - left + 1) space ❌
Recursive: O(right - left + 1) stack space ❌

In-place is optimal! ✓
```

### Optimal Complexity

```
Time: O(n)
  Must visit nodes to reverse them
  Can't do better than linear
  Optimal! ✓

Space: O(1)
  In-place modification
  Only pointer variables
  Optimal! ✓

This solution is optimal in both dimensions! ✓
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `head = [1,2,3,4,5]`, `left = 2`, `right = 4`
**Expected Output:** `[1,4,3,2,5]`

---

**Initial State:**
```
List: 1 → 2 → 3 → 4 → 5 → null
left = 2 (node 2)
right = 4 (node 4)

Goal: Reverse nodes 2, 3, 4
```

---

**Step 0: Create Dummy**
```
dummy → 1 → 2 → 3 → 4 → 5 → null

dummy.val = 0
dummy.next = head (node 1)
```

---

**Step 1: Navigate to Position Before Left**
```
beforeLeft = dummy

Loop: i = 1; i < left (2); i++
  Iteration 1 (i=1):
    beforeLeft = beforeLeft.next = node 1
    
After loop:
  beforeLeft = node 1 (position 1, before left=2)

State:
  dummy → 1 → 2 → 3 → 4 → 5
          ↑
      beforeLeft
```

---

**Step 2: Initialize Reversal Pointers**
```
prev = beforeLeft = node 1
curr = beforeLeft.next = node 2
leftNode = curr = node 2 (saved reference)

State:
  dummy → 1 → 2 → 3 → 4 → 5
         prev  curr
              (leftNode)
```

---

**Step 3: Reverse Loop**

**Iteration 1 (i=0):**
```
Current state: dummy → 1 → 2 → 3 → 4 → 5

next = curr.next = node 3
curr.next = prev = node 1
prev = curr = node 2
curr = next = node 3

After iteration 1:
  dummy → 1 ← 2    3 → 4 → 5
         prev     curr
```

**Iteration 2 (i=1):**
```
Current state: dummy → 1 ← 2    3 → 4 → 5

next = curr.next = node 4
curr.next = prev = node 2
prev = curr = node 3
curr = next = node 4

After iteration 2:
  dummy → 1 ← 2 ← 3    4 → 5
              prev    curr
```

**Iteration 3 (i=2):**
```
Current state: dummy → 1 ← 2 ← 3    4 → 5

next = curr.next = node 5
curr.next = prev = node 3
prev = curr = node 4
curr = next = node 5

After iteration 3:
  dummy → 1 ← 2 ← 3 ← 4    5 → null
                  prev    curr

Loop ends (i > right - left = 2)
```

---

**Step 4: Reconnect**

**Reconnection 1: beforeLeft.next = prev**
```
beforeLeft (node 1).next = prev (node 4)

State:
  dummy → 1 → 4
              ↓
              3
              ↓
              2    5 → null
```

**Reconnection 2: leftNode.next = curr**
```
leftNode (node 2).next = curr (node 5)

Final state:
  dummy → 1 → 4 → 3 → 2 → 5 → null

Complete linked list! ✓
```

---

**Step 5: Return**
```
return dummy.next = node 1

Result: 1 → 4 → 3 → 2 → 5 ✓
```

---

### Visual Summary
```
Original:  1 → 2 → 3 → 4 → 5

Step 1: Navigate to beforeLeft (node 1)
  dummy → [1] → 2 → 3 → 4 → 5
          ↑
      beforeLeft

Step 2: Reverse nodes 2, 3, 4
  Before:  1 → [2 → 3 → 4] → 5
  After:   1 ← 2 ← 3 ← 4    5
           
Step 3: Reconnect
  1.next = 4 (head of reversed)
  2.next = 5 (afterRight)
  
Result:  1 → 4 → 3 → 2 → 5 ✓
```

---

### Edge Case: Reverse from Head

**Input:** `head = [1,2,3,4,5]`, `left = 1`, `right = 3`

```
Initial:
  dummy → 1 → 2 → 3 → 4 → 5

Step 1: Navigate (no iterations, stay at dummy)
  beforeLeft = dummy

Step 2: Reverse 1, 2, 3
  prev = dummy
  curr = 1
  leftNode = 1
  
  After reversals:
    dummy ← 1 ← 2 ← 3    4 → 5
    
Step 3: Reconnect
  dummy.next = 3
  1.next = 4
  
Result:
  dummy → 3 → 2 → 1 → 4 → 5
  
Return dummy.next = 3 → 2 → 1 → 4 → 5 ✓
```

---

### Edge Case: Single Node Reversal

**Input:** `head = [1,2,3,4,5]`, `left = 3`, `right = 3`

```
Early return: left == right
  return head immediately
  
No processing needed
Result: [1, 2, 3, 4, 5] (unchanged) ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Passes | Clarity | Recommended |
|----------|------|-------|--------|---------|-------------|
| **One-Pass In-Place** | **O(n)** | **O(1)** | **1** | **Medium** | **Yes ✅** |
| Two-Pass | O(n) | O(1) | 2 | Simple | No (inefficient) |
| Stack-Based | O(n) | O(k) | 1 | Simple | No (extra space) |
| Recursion | O(n) | O(k) | 1 | Complex | No (stack space) |

Where k = right - left + 1

**Winner**: **One-pass in-place** — optimal time and space, satisfies follow-up!

---

## Key Takeaways

1. **Dummy node** — eliminates edge case for left = 1
2. **Navigate to left - 1** — need node before reversal range
3. **Save leftNode** — reference to tail of reversed section
4. **Reverse (right - left + 1) nodes** — inclusive range
5. **prev = beforeLeft** — maintain connection to before
6. **Two reconnections** — beforeLeft.next and leftNode.next
7. **Return dummy.next** — handles head change when left = 1
8. **O(n) time, O(1) space** — optimal complexity
9. **One pass** — satisfies follow-up requirement
10. **1-indexed positions** — remember indexing starts at 1

---

## Interview Tips

**What to say in an interview:**

> "To reverse a portion of a linked list from position left to right in one pass, I'll use an in-place reversal approach with careful pointer manipulation. First, I'll create a dummy node to handle the edge case where left equals 1, making the head part of the reversed section. Then I'll navigate to the node just before position left, which I'll call beforeLeft. Next, I'll use the standard three-pointer technique (prev, curr, next) to reverse exactly (right - left + 1) nodes. Importantly, I'll save a reference to the node originally at position left because it will become the tail of the reversed section. After reversing, I need two reconnections: first, connect beforeLeft to the new head of the reversed section (which is prev), and second, connect the old leftNode to the node after the reversed section (which is curr). Finally, I return dummy.next to get the new head of the list. This solution runs in O(n) time with a single pass through the list and uses O(1) space with just a few pointer variables."

**Key points to mention:**
1. **Dummy node** — handles left = 1 edge case
2. **Navigate to left - 1** — beforeLeft position
3. **Save leftNode reference** — for reconnection
4. **Three-pointer reversal** — prev, curr, next
5. **Loop (right - left + 1) times** — exact node count
6. **Two reconnections** — beforeLeft.next and leftNode.next
7. **Return dummy.next** — new head
8. **O(n) time** — one pass
9. **O(1) space** — only pointers

**Common Follow-ups:**
- "Can you do it in one pass?" → Yes, this solution is one-pass (follow-up requirement)
- "What if left = 1?" → Dummy node handles this edge case
- "What if left = right?" → Early return for efficiency (or reverses 1 node, which is no-op)
- "What's the space complexity?" → O(1), only pointer variables
- "Can you do it recursively?" → Yes, but uses O(k) stack space (not optimal)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Reverse Linked List II** | Medium | **Partial Reversal** | **This problem** |
| Reverse Linked List | Easy | Full Reversal | Simpler (no boundaries) |
| Reverse Nodes in k-Group | Hard | Multiple Reversals | Reverse every k nodes |
| Swap Nodes in Pairs | Medium | Pair Reversal | k = 2 special case |
| Rotate List | Medium | List Manipulation | Move tail to head |
| Palindrome Linked List | Easy | Half Reversal | Reverse second half |

**Pattern Progression**:
1. **Reverse Linked List** — Full reversal (foundation)
2. **Reverse Linked List II** (this) — Partial reversal with boundaries
3. **Reverse Nodes in k-Group** — Multiple partial reversals
4. **Swap Nodes in Pairs** — Simplest partial reversal (k=2)

---

## Final Pattern Label

✅ **One-Pass In-Place Partial Reversal with Boundary Tracking**

**Remember:** This is a **partial linked list reversal** problem. Use **dummy node** to eliminate edge case for left = 1. **Navigate to position left - 1** (node before reversal range) using loop `for (i = 1; i < left; i++)`. **Save leftNode reference** (`leftNode = curr`) before reversal — this becomes tail of reversed section. **Initialize prev = beforeLeft** (not null) to maintain connection. Use **three-pointer reversal** (prev, curr, next) for exactly **(right - left + 1) iterations** (inclusive range, so +1). After reversal: **prev** = new head of reversed section, **curr** = afterRight node, **leftNode** = tail of reversed section. **Two critical reconnections**: `beforeLeft.next = prev` (connect to reversed head) and `leftNode.next = curr` (connect tail to afterRight). **Return dummy.next** (handles head change). Achieves **O(n) time** (one pass) and **O(1) space** (only pointers). Critical mistakes to avoid: off-by-one in navigation loop (use `i < left` not `i <= left`), wrong reversal count (need `right - left + 1`), not saving leftNode, forgetting dummy node, wrong reconnection order. Remember positions are **1-indexed**!
