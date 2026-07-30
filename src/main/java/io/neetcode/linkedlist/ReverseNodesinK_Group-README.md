# Reverse Nodes in K-Group

## Problem Description

**Difficulty**: Hard

You are given the head of a singly linked list `head` and a positive integer `k`.

You must reverse the first `k` nodes in the linked list, and then reverse the next `k` nodes, and so on. If there are fewer than k nodes left, leave the nodes as they are.

Return the modified list after reversing the nodes in each group of `k`.

**You are only allowed to modify the nodes' `next` pointers, not the values of the nodes.**

**Key Concepts:**
- **Group Reversal**: Reverse every k consecutive nodes
- **Partial Groups**: Leave groups with fewer than k nodes unchanged
- **In-Place**: Modify pointers only, O(1) extra space
- **Pointer Tracking**: Track previous group tail, current group, next group head
- **Reconnection**: Link reversed groups back into main list

**Visual Example:**
```
Input: 1 → 2 → 3 → 4 → 5 → 6, k = 3

Group 1 (nodes 1,2,3): Reverse
  Before: 1 → 2 → 3
  After:  3 → 2 → 1

Group 2 (nodes 4,5,6): Reverse
  Before: 4 → 5 → 6
  After:  6 → 5 → 4

Final: 3 → 2 → 1 → 6 → 5 → 4 ✓

Input: 1 → 2 → 3 → 4 → 5, k = 3

Group 1 (nodes 1,2,3): Reverse
  After:  3 → 2 → 1

Group 2 (nodes 4,5): Only 2 nodes (< k), leave unchanged
  After:  4 → 5

Final: 3 → 2 → 1 → 4 → 5 ✓
```

**Recommended Complexity:**
- **Time**: O(n) where n = number of nodes
- **Space**: O(1) — in-place pointer manipulation

---

## Examples

### Example 1 (Main Example - Complete Groups):
```
Input: head = [1,2,3,4,5,6], k = 3

Output: [3,2,1,6,5,4]

Explanation:
Original: 1 → 2 → 3 → 4 → 5 → 6

Group 1: [1,2,3] → reverse → [3,2,1]
Group 2: [4,5,6] → reverse → [6,5,4]

Result: 3 → 2 → 3 → 6 → 5 → 4

All groups have exactly k=3 nodes, all reversed.
```

### Example 2 (Partial Group - Leave Unchanged):
```
Input: head = [1,2,3,4,5], k = 3

Output: [3,2,1,4,5]

Explanation:
Original: 1 → 2 → 3 → 4 → 5

Group 1: [1,2,3] → reverse → [3,2,1]
Group 2: [4,5] → only 2 nodes < k=3 → leave as is

Result: 3 → 2 → 1 → 4 → 5

Last group has fewer than k nodes, not reversed.
```

### Example 3 (k = 1 - No Change):
```
Input: head = [1,2,3,4], k = 1

Output: [1,2,3,4]

Explanation:
Each group has 1 node
Reversing single node changes nothing
Result is same as input
```

### Example 4 (k = 2 - Pairs):
```
Input: head = [1,2,3,4,5,6], k = 2

Output: [2,1,4,3,6,5]

Explanation:
Group 1: [1,2] → [2,1]
Group 2: [3,4] → [4,3]
Group 3: [5,6] → [6,5]

All pairs reversed
```

### Example 5 (k = 2 with Odd Length):
```
Input: head = [1,2,3,4,5], k = 2

Output: [2,1,4,3,5]

Explanation:
Group 1: [1,2] → [2,1]
Group 2: [3,4] → [4,3]
Group 3: [5] → only 1 node, leave as is

Last node unchanged
```

### Example 6 (k = n - Reverse Entire List):
```
Input: head = [1,2,3,4], k = 4

Output: [4,3,2,1]

Explanation:
One group with all 4 nodes
Reverse entire list
```

### Example 7 (k > n - No Reversal):
```
Input: head = [1,2,3], k = 5

Output: [1,2,3]

Explanation:
Only 3 nodes, k=5
First (and only) group has fewer than k nodes
Leave unchanged
```

### Example 8 (Single Node):
```
Input: head = [1], k = 1

Output: [1]

Explanation:
Single node, any k ≥ 1
Returns same node
```

### Example 9 (Two Nodes, k = 2):
```
Input: head = [1,2], k = 2

Output: [2,1]

Explanation:
One complete group of 2
Reverse: 2 → 1
```

### Example 10 (Large k with Groups):
```
Input: head = [1,2,3,4,5,6,7,8,9], k = 3

Output: [3,2,1,6,5,4,9,8,7]

Explanation:
Group 1: [1,2,3] → [3,2,1]
Group 2: [4,5,6] → [6,5,4]
Group 3: [7,8,9] → [9,8,7]

All groups complete, all reversed
```

---

## Constraints
- The length of the linked list is `n`
- `1 <= k <= n <= 100`
- `0 <= Node.val <= 100`

**Recommended Complexity**: 
- Time: O(n) — visit each node once
- Space: O(1) — in-place pointer manipulation

---

## Pattern Recognition

**Primary Pattern**: **Iterative In-Place Linked List Reversal with Group Management**

**Why This Pattern?**
- Need to **reverse segments** of linked list
- Must track **multiple pointers** (previous group tail, current group, next group)
- **In-place** requirement → O(1) space
- **Conditional reversal** → only if group has k nodes
- Classic **pointer manipulation** problem

**Key Insight**: Segment Reversal with Reconnection
```
Problem: Reverse every k nodes, but only if k nodes available

Naive approach:
  Store values in array: O(n) space ❌
  Reverse array segments
  Rebuild list
  Not in-place!

Optimal approach:
  1. Check if k nodes available
  2. If yes, reverse those k nodes in-place
  3. Track pointers to reconnect groups
  4. Move to next group
  5. Repeat
  
  In-place, O(1) space! ✓
```

**Visual: Pointer Tracking**
```
Original: dummy → 1 → 2 → 3 → 4 → 5, k = 3

Pointers needed:
  prevGroupTail: points to tail of previous group (dummy initially)
  groupStart: first node of current group (1)
  groupEnd: last node of current group (3)
  nextGroupHead: first node of next group (4)

After reversing first group:
  prevGroupTail = dummy
  Group: 3 → 2 → 1
  nextGroupHead = 4

Reconnect:
  dummy.next = 3 (new head of reversed group)
  1.next = 4 (tail of reversed group to next group)
  prevGroupTail = 1 (update for next iteration)

Continue with next group...
```

**Why Dummy Node is Essential**:
```
Without dummy:
  If first group reversed, need to return new head
  Special case handling ❌
  Complex pointer tracking

With dummy:
  dummy.next always points to current list head
  After first group reversed, dummy.next updated
  No special cases! ✓
  Return dummy.next at end

Dummy simplifies head management!
```

**The Core Algorithm Structure**:
```
1. Create dummy node pointing to head
2. Initialize prevGroupTail = dummy
3. While more nodes exist:
   a. Check if k nodes available from current position
   b. If not, break (leave remaining nodes)
   c. If yes:
      - Save nextGroupHead (k+1th node)
      - Reverse k nodes
      - Get new groupStart and groupEnd after reversal
      - Reconnect:
        * prevGroupTail.next = groupStart (new head of group)
        * groupEnd.next = nextGroupHead
      - Update prevGroupTail = groupEnd
4. Return dummy.next
```

**Visual: Step-by-Step Reversal**
```
Input: 1 → 2 → 3 → 4 → 5, k = 3

Initial:
  dummy → 1 → 2 → 3 → 4 → 5
  prevGroupTail = dummy

Step 1: Check if 3 nodes available from node 1
  Yes: 1, 2, 3 ✓

Step 2: Save nextGroupHead = 4

Step 3: Reverse [1, 2, 3]
  Before: 1 → 2 → 3 → 4 → 5
  After:  3 → 2 → 1 (→ null)
  
  groupStart = 3 (new head after reversal)
  groupEnd = 1 (new tail after reversal)

Step 4: Reconnect
  dummy.next = 3
  1.next = 4
  
  Result: dummy → 3 → 2 → 1 → 4 → 5
  
Step 5: Update prevGroupTail = 1

Step 6: Check if 3 nodes available from node 4
  No: only 4, 5 (2 nodes) ❌
  Break

Final: dummy → 3 → 2 → 1 → 4 → 5
Return: dummy.next = 3 → 2 → 1 → 4 → 5 ✓
```

**Key Operations**:

**1. Count k Nodes**:
```java
// Check if k nodes available starting from current
ListNode temp = current;
int count = 0;
while (temp != null && count < k) {
    temp = temp.next;
    count++;
}
if (count < k) {
    break;  // Not enough nodes, leave as is
}
```

**2. Reverse k Nodes**:
```java
// Reverse k nodes starting from groupStart
ListNode prev = null;
ListNode curr = groupStart;
ListNode next = null;

for (int i = 0; i < k; i++) {
    next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}

// After reversal:
// prev = new head of reversed group
// curr = first node of next group (nextGroupHead)
// groupStart = new tail of reversed group
```

**3. Reconnect Groups**:
```java
// Connect previous group to reversed group
prevGroupTail.next = prev;  // prev is new head

// Connect reversed group to next group
groupStart.next = curr;  // groupStart is now tail, curr is nextGroupHead

// Update prevGroupTail for next iteration
prevGroupTail = groupStart;
```

**Why In-Place Reversal**:
```
Space requirement: O(1)

Array approach:
  Store all values: O(n) space ❌
  
In-place reversal:
  Only pointer variables: O(1) space ✓
  
Meets constraint! ✓
```

**Alternative Approaches (Why They're Not Optimal)**:

1. **Recursive Approach**:
```
Recursively reverse groups
  Elegant, but O(n/k) = O(n) space for recursion stack ❌
  Not truly O(1) space
```

2. **Array-Based**:
```
Store values in array: O(n) space
Reverse array segments
Rebuild list
  Easy to implement, but violates space constraint ❌
```

3. **Value Swapping**:
```
Swap node values instead of pointers
  Problem says "only modify next pointers, not values" ❌
  Explicitly forbidden!
```

**Related Patterns**:
1. **Reverse Linked List** — Base operation (reverse all)
2. **Reverse Linked List II** — Reverse segment [left, right]
3. **Reverse Nodes in K-Group** — This problem (conditional group reversal)
4. **Swap Nodes in Pairs** — Special case where k=2

---

## Algorithm & Approach

### Core Insight

**Why Iterative In-Place Reversal Works:**
```
Key observations:
  1. Each group can be reversed independently
  2. Need to track pointers to reconnect groups
  3. Must count nodes before reversing (to check if k available)
  4. Dummy node simplifies head management
  5. After reversing, original group start becomes tail
  6. Process groups left to right, one at a time
```

**The Optimal Strategy**:
```
Algorithm components:
  - Dummy node: simplify head changes
  - prevGroupTail: track where to attach reversed group
  - Count check: ensure k nodes available
  - In-place reversal: reverse k nodes
  - Reconnection: link groups together
  - Iteration: move to next group

Steps:
  1. Create dummy → head
  2. While more nodes:
     a. Count k nodes
     b. If found, reverse them
     c. Reconnect
     d. Move to next group
  3. Return dummy.next

Time: O(n) — visit each node constant times
Space: O(1) — only pointer variables
```

### Step-by-Step Algorithm

---

#### **Approach: Iterative In-Place Reversal - OPTIMAL**

**Core Idea**:
- Use dummy node to handle head changes
- For each group: count k nodes, reverse if available, reconnect
- Track prevGroupTail to attach reversed groups
- After reversal, original group head becomes tail
- Continue until fewer than k nodes remain

**Algorithm**
```java
reverseKGroup(head, k):
    if head == null or k == 1:
        return head
    
    // Dummy node
    dummy = new ListNode(0)
    dummy.next = head
    prevGroupTail = dummy
    
    while true:
        // Count k nodes
        current = prevGroupTail.next
        count = 0
        while current != null and count < k:
            current = current.next
            count++
        
        // If fewer than k nodes, break
        if count < k:
            break
        
        // Reverse k nodes
        groupStart = prevGroupTail.next
        prev = null
        curr = groupStart
        
        for i from 0 to k-1:
            next = curr.next
            curr.next = prev
            prev = curr
            curr = next
        
        // Reconnect
        prevGroupTail.next = prev  // prev is new head of reversed group
        groupStart.next = curr     // groupStart is now tail, curr is next group
        prevGroupTail = groupStart // update for next iteration
    
    return dummy.next
```

**Complete Code Implementation**
```java
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        // Edge cases
        if (head == null || k == 1) {
            return head;
        }
        
        // Dummy node to simplify head changes
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode prevGroupTail = dummy;
        
        while (true) {
            // Count k nodes from current position
            ListNode current = prevGroupTail.next;
            int count = 0;
            while (current != null && count < k) {
                current = current.next;
                count++;
            }
            
            // If fewer than k nodes remain, leave them as is
            if (count < k) {
                break;
            }
            
            // Reverse k nodes
            ListNode groupStart = prevGroupTail.next;
            ListNode prev = null;
            ListNode curr = groupStart;
            
            for (int i = 0; i < k; i++) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            
            // Reconnect reversed group
            // prev is now the new head of reversed group
            // curr is the first node of next group (or null)
            // groupStart is now the tail of reversed group
            
            prevGroupTail.next = prev;      // Connect previous group to new head
            groupStart.next = curr;         // Connect tail to next group
            prevGroupTail = groupStart;     // Update for next iteration
        }
        
        return dummy.next;
    }
}
```

**Example Walkthrough**

Input: `head = [1,2,3,4,5], k = 3`

```
Initial:
  List: 1 → 2 → 3 → 4 → 5
  dummy → 1 → 2 → 3 → 4 → 5
  prevGroupTail = dummy
```

**Iteration 1:**

**Step 1: Count k=3 nodes**
```
current = prevGroupTail.next = 1
Count: 1, 2, 3
count = 3 ✓

Enough nodes, proceed with reversal
```

**Step 2: Prepare for reversal**
```
groupStart = 1
prev = null
curr = 1
```

**Step 3: Reverse 3 nodes**
```
i = 0:
  next = 1.next = 2
  1.next = null
  prev = 1
  curr = 2
  State: 1 → null, 2 → 3 → 4 → 5

i = 1:
  next = 2.next = 3
  2.next = 1
  prev = 2
  curr = 3
  State: 2 → 1 → null, 3 → 4 → 5

i = 2:
  next = 3.next = 4
  3.next = 2
  prev = 3
  curr = 4
  State: 3 → 2 → 1 → null, 4 → 5

After reversal:
  prev = 3 (new head of reversed group)
  curr = 4 (first node of next group)
  groupStart = 1 (now tail of reversed group)
```

**Step 4: Reconnect**
```
prevGroupTail.next = prev
  → dummy.next = 3

groupStart.next = curr
  → 1.next = 4

prevGroupTail = groupStart
  → prevGroupTail = 1

Current list:
  dummy → 3 → 2 → 1 → 4 → 5
```

**Iteration 2:**

**Step 1: Count k=3 nodes**
```
current = prevGroupTail.next = 4
Count: 4, 5
count = 2 < k=3 ❌

Not enough nodes, break
```

**Final Result:**
```
dummy → 3 → 2 → 1 → 4 → 5

Return dummy.next = 3 → 2 → 1 → 4 → 5 ✓
```

---

**Detailed Reversal Visualization:**

```
Original group: 1 → 2 → 3 → 4 → 5
Want to reverse first 3 nodes

Before reversal loop:
  groupStart = 1
  prev = null
  curr = 1

Loop iteration by iteration:

i = 0:
  curr = 1, next = 2
  1.next = null (was 2)
  prev = 1, curr = 2
  
  Visual: null ← 1    2 → 3 → 4 → 5

i = 1:
  curr = 2, next = 3
  2.next = 1 (was 3)
  prev = 2, curr = 3
  
  Visual: null ← 1 ← 2    3 → 4 → 5

i = 2:
  curr = 3, next = 4
  3.next = 2 (was 4)
  prev = 3, curr = 4
  
  Visual: null ← 1 ← 2 ← 3    4 → 5

After loop:
  prev = 3 (points to new head of reversed portion)
  curr = 4 (points to first node after reversed portion)
  groupStart = 1 (still points to original start, now the tail)

Reversed group: 3 → 2 → 1
Next group starts at: 4
```

**Complexity Analysis**
- **Time**: O(n) — each node visited constant times
- **Space**: O(1) — only pointer variables

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | In-Place | Pointers Only | Recommended |
|----------|------|-------|----------|---------------|-------------|
| **Iterative In-Place** | **O(n)** | **O(1)** | **Yes** | **Yes** | **Yes ✅** |
| Recursive | O(n) | O(n/k) | Yes | Yes | No (space) |
| Array-Based | O(n) | O(n) | No | No | No (space) |
| Value Swapping | O(n) | O(1) | Yes | No | No (forbidden) |

**Winner**: **Iterative In-Place Reversal** — meets all constraints!

### Why Iterative Beats Recursive

```
Recursive approach:
  Elegant, clean code
  But recursion stack depth = n/k ≈ O(n) ❌
  Not truly O(1) space

Iterative approach:
  Only uses pointer variables: O(1) space ✓
  More complex but meets constraint
  
Problem requires O(1) space: iterative wins! ✓
```

### Why Dummy Node is Critical

```
Without dummy:
  Head might change after first group reversal
  Need special case:
    if (first group) {
      head = newHead;
    } else {
      prevGroupTail.next = newHead;
    }
  More code, more bugs ❌

With dummy:
  dummy.next always points to current head
  After first group: dummy.next = newHead
  Same logic for all groups ✓
  Return dummy.next
  
  Unified handling! ✓
```

### Why Count Before Reversing

```
Problem requirement:
  Only reverse if k nodes available
  Leave partial group unchanged

Without counting:
  Start reversing
  Reach end prematurely
  Need to reverse back (wasteful) ❌

With counting:
  Check availability first
  Only reverse if k nodes found ✓
  No wasted work
  
Efficient! ✓
```

### Why Track prevGroupTail

```
Need to reconnect groups after reversal

Without tracking:
  After reversing group, how to attach it? ❌
  Lost reference to previous group

With prevGroupTail:
  Always points to tail of previous group
  After reversing current group:
    prevGroupTail.next = new head
  Then update: prevGroupTail = new tail
  
  Seamless reconnection! ✓
```

### Why This is Optimal

```
Time complexity:
  Each node visited in:
    1. Counting phase: O(1) per node
    2. Reversal phase: O(1) per node (if in group)
  Total visits per node: constant
  Total time: O(n) ✓
  
  Can't do better than O(n) (must process all nodes)
  Optimal! ✓

Space complexity:
  Only pointer variables:
    - dummy
    - prevGroupTail
    - groupStart
    - prev, curr, next (reversal)
  Total: O(1) ✓
  
  Meets constraint! ✓

Both time and space optimal! ✓
```

---

## Critical Edge Cases & Gotchas

### 1. **k = 1 (No Reversal Needed)**
```java
head = [1,2,3,4], k = 1
// Each group has 1 node
// Reversing single node does nothing
// Return same list
```

### 2. **k = n (Reverse Entire List)**
```java
head = [1,2,3,4], k = 4
// One group with all nodes
// Reverse entire list
// Result: [4,3,2,1]
```

### 3. **k > n (No Reversal)**
```java
head = [1,2,3], k = 5
// Only 3 nodes, need 5
// No complete group
// Return unchanged: [1,2,3]
```

### 4. **Single Node**
```java
head = [1], k = any
// Only one node
// No reversal possible (or no effect)
// Return [1]
```

### 5. **All Groups Complete**
```java
head = [1,2,3,4,5,6], k = 2
// 6 nodes, k=2, perfect division
// All 3 groups reversed
// Result: [2,1,4,3,6,5]
```

### 6. **Last Group Incomplete**
```java
head = [1,2,3,4,5], k = 2
// First 2 groups reversed: [2,1,4,3,...]
// Last group [5] has only 1 node < k=2
// Leave last node: [2,1,4,3,5]
```

### 7. **Two Nodes, k = 2**
```java
head = [1,2], k = 2
// Exactly one group
// Reverse: [2,1]
```

### 8. **Empty List**
```java
head = null, k = any
// No nodes
// Return null
```

### 9. **Large k with Multiple Complete Groups**
```java
head = [1,2,3,4,5,6,7,8,9], k = 3
// Three complete groups
// All reversed
// Result: [3,2,1,6,5,4,9,8,7]
```

### 10. **Alternating Pattern (k = 2)**
```java
head = [1,2,3,4,5,6,7], k = 2
// Groups: [1,2], [3,4], [5,6], [7]
// First 3 groups reversed, last unchanged
// Result: [2,1,4,3,6,5,7]
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Counting Nodes Before Reversing**
```java
// WRONG - reversing without checking availability
while (prevGroupTail.next != null) {
    // Directly reverse k nodes ❌
    // What if fewer than k nodes remain?
}
```

**Why wrong**: Might reverse incomplete group!

**Dry run failure:**
```
head = [1,2,3,4,5], k = 3

First group [1,2,3]: reverse ✓
Second group [4,5]: only 2 nodes
  But code tries to reverse 3 nodes ❌
  Reaches null, breaks logic

Should leave [4,5] unchanged!
```

**Fix**: Count before reversing
```java
// Count k nodes
int count = 0;
ListNode current = prevGroupTail.next;
while (current != null && count < k) {
    current = current.next;
    count++;
}

if (count < k) {  ✓
    break;  // Not enough nodes
}
// Now safe to reverse
```

### ❌ **MISTAKE 2: Wrong Reconnection After Reversal**
```java
// WRONG - incorrect reconnection
prevGroupTail.next = curr;  // ❌ curr is next group, not reversed head!
groupStart.next = prev;     // ❌ Wrong direction!
```

**Why wrong**: Connects to wrong nodes!

**Dry run failure:**
```
After reversing [1,2,3] → [3,2,1]:
  prev = 3 (new head)
  curr = 4 (next group)
  groupStart = 1 (new tail)

Wrong reconnection:
  prevGroupTail.next = curr = 4 ❌
    Skips reversed group entirely!
  groupStart.next = prev = 3 ❌
    Creates cycle!

List corrupted!
```

**Fix**: Correct reconnection
```java
prevGroupTail.next = prev;   ✓  // prev is new head of reversed group
groupStart.next = curr;      ✓  // groupStart (now tail) to next group
prevGroupTail = groupStart;  ✓  // Update for next iteration
```

### ❌ **MISTAKE 3: Not Using Dummy Node**
```java
// WRONG - no dummy node
ListNode prevGroupTail = null;  // ❌ How to handle first group?

if (prevGroupTail == null) {
    // Special case for first group ❌
    head = prev;
} else {
    prevGroupTail.next = prev;
}
```

**Why wrong**: Unnecessary complexity!

**Issue:**
```
First group reversal changes head
Need special case handling ❌
More code, more bugs

Dummy node unifies all cases ✓
```

**Fix**: Use dummy node
```java
ListNode dummy = new ListNode(0);  ✓
dummy.next = head;
ListNode prevGroupTail = dummy;

// Same logic for all groups
prevGroupTail.next = prev;

// Return new head
return dummy.next;  ✓
```

### ❌ **MISTAKE 4: Losing Reference to Next Group**
```java
// WRONG - not saving next group head
for (int i = 0; i < k; i++) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}

// After loop, curr might be null or lost ❌
groupStart.next = ???  // Don't know where next group is!
```

**Why wrong**: Lost reference during reversal!

**Issue:**
```
During reversal, breaking links
After reversal, curr points to next group
But what if not saved properly?

Actually, curr is correct after loop ✓
But the code structure needs clarity
```

**Better**: Clear variable naming
```java
// Save next group head
ListNode nextGroupHead = curr;  // After counting

// Reverse k nodes
for (int i = 0; i < k; i++) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}

// curr still equals nextGroupHead after reversal ✓
groupStart.next = curr;  ✓
```

### ❌ **MISTAKE 5: Wrong Loop Termination**
```java
// WRONG - checking wrong condition
while (prevGroupTail != null) {  // ❌ prevGroupTail never null!
    // ...
}
```

**Why wrong**: Infinite loop or wrong termination!

**Issue:**
```
prevGroupTail is always valid (dummy or previous tail)
Need to check if k nodes available, not prevGroupTail!
```

**Fix**: Check node availability
```java
while (true) {  ✓
    // Count k nodes
    if (count < k) {
        break;  // Correct termination
    }
    // Process group
}
```

### ❌ **MISTAKE 6: Off-by-One in Counting**
```java
// WRONG - counting incorrectly
int count = 1;  // ❌ Wrong initial count
ListNode current = prevGroupTail.next;
while (current != null && count < k) {
    current = current.next;
    count++;
}
```

**Why wrong**: Counts one extra node!

**Dry run failure:**
```
prevGroupTail.next = 1 → 2 → 3 → 4

count = 1 initially ❌
Iterations:
  current = 1, count = 1
  current = 2, count = 2
  current = 3, count = 3
  Exit (count >= k)

But only moved through 2 nodes, not 3! ❌
```

**Fix**: Start count at 0
```java
int count = 0;  ✓
ListNode current = prevGroupTail.next;
while (current != null && count < k) {
    current = current.next;
    count++;
}
```

### ❌ **MISTAKE 7: Modifying Node Values Instead of Pointers**
```java
// WRONG - swapping values instead of reversing pointers
for (int i = 0; i < k/2; i++) {
    // Swap values ❌
    int temp = nodes[i].val;
    nodes[i].val = nodes[k-1-i].val;
    nodes[k-1-i].val = temp;
}
```

**Why wrong**: Problem explicitly forbids value modification!

**Issue:**
```
Constraint: "only modify next pointers, not values"
Value swapping violates this ❌

Must reverse by changing next pointers ✓
```

**Fix**: Reverse pointers
```java
for (int i = 0; i < k; i++) {
    ListNode next = curr.next;
    curr.next = prev;  ✓  // Modify pointer
    prev = curr;
    curr = next;
}
```

### ❌ **MISTAKE 8: Not Updating prevGroupTail**
```java
// WRONG - forgetting to update prevGroupTail
prevGroupTail.next = prev;
groupStart.next = curr;
// Missing: prevGroupTail = groupStart; ❌
```

**Why wrong**: Next iteration has wrong prevGroupTail!

**Dry run failure:**
```
After first group:
  prevGroupTail = dummy (still pointing to old position) ❌

Second group:
  Tries to connect from dummy again ❌
  Loses first reversed group!

Must update prevGroupTail for next iteration!
```

**Fix**: Update prevGroupTail
```java
prevGroupTail.next = prev;
groupStart.next = curr;
prevGroupTail = groupStart;  ✓  // Update for next iteration
```

### ❌ **MISTAKE 9: Incorrect Reversal Loop Bounds**
```java
// WRONG - reversing too many or too few nodes
for (int i = 0; i <= k; i++) {  // ❌ <= instead of <
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
```

**Why wrong**: Reverses k+1 nodes instead of k!

**Dry run failure:**
```
k = 3, want to reverse [1,2,3]

Loop with i <= 3:
  i=0: reverse 1
  i=1: reverse 2
  i=2: reverse 3
  i=3: reverse 4 ❌ (should stop here!)

Reverses 4 nodes instead of 3!
```

**Fix**: Loop k times exactly
```java
for (int i = 0; i < k; i++) {  ✓
    // Reverse k nodes
}
```

### ❌ **MISTAKE 10: Returning Wrong Node**
```java
// WRONG - returning dummy instead of dummy.next
ListNode dummy = new ListNode(0);
dummy.next = head;

// ... process groups ...

return dummy;  // ❌ Returning dummy node!
```

**Why wrong**: Dummy is placeholder, not part of result!

**Issue:**
```
Result list: dummy → 3 → 2 → 1 → 4 → 5

Should return: 3 → 2 → 1 → 4 → 5
But returns: dummy → 3 → 2 → 1 → 4 → 5 ❌

Dummy (value 0) shouldn't be in result!
```

**Fix**: Return dummy.next
```java
return dummy.next;  ✓
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

```
Where n = number of nodes in list

Analysis:
  1. Counting phase:
     - Each node counted once
     - Total counting: O(n)
  
  2. Reversal phase:
     - Each node reversed once (if in complete group)
     - At most n nodes reversed
     - Total reversal: O(n)
  
  3. Reconnection:
     - Constant time per group
     - At most n/k groups
     - Total reconnection: O(n/k)
  
  Overall: O(n) + O(n) + O(n/k) = O(n)

Each node visited constant times: O(n) ✓
```

**Detailed Analysis**:
```
For each complete group:
  - Count k nodes: O(k)
  - Reverse k nodes: O(k)
  - Reconnect: O(1)
  Total per group: O(k)

Number of complete groups: floor(n/k)
Total time: floor(n/k) × O(k) = O(n)

Plus counting remaining nodes: O(n mod k)

Total: O(n) ✓
```

**Why O(n) is Optimal**:
```
Must process each node at least once
Lower bound: Ω(n)

Our solution: O(n)

Matches lower bound: OPTIMAL! ✓
```

### Space Complexity: **O(1)**

```
Space used:
  - dummy: O(1)
  - prevGroupTail: O(1)
  - groupStart: O(1)
  - prev, curr, next: O(1)
  - count, i: O(1)

Total: O(1) constant space ✓

No recursion, no extra data structures
Truly in-place! ✓
```

**Space is Optimal**:
```
Problem requires O(1) space
Our solution: O(1)

Meets requirement! ✓

Note: Result list is not counted
  (it's the output, not extra space)
```

### Optimal Complexity

```
Time: O(n)
  - Must visit each node: Ω(n)
  - Our solution: O(n)
  - Optimal! ✓

Space: O(1)
  - Constraint requires O(1)
  - Our solution: O(1)
  - Meets requirement! ✓

Both time and space optimal! ✓
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `head = [1,2,3,4,5,6,7], k = 3`

```
Initial List:
  1 → 2 → 3 → 4 → 5 → 6 → 7
```

---

**Setup:**
```
Create dummy:
  dummy → 1 → 2 → 3 → 4 → 5 → 6 → 7

prevGroupTail = dummy
```

---

**Iteration 1 (Group 1: nodes 1,2,3):**

**Count Phase:**
```
current = prevGroupTail.next = 1
Count nodes: 1, 2, 3
count = 3 ✓

Enough nodes for one group
```

**Reversal Setup:**
```
groupStart = 1
prev = null
curr = 1
```

**Reversal Loop:**
```
i = 0:
  next = 2
  1.next = null
  prev = 1, curr = 2
  
  State: null ← 1    2 → 3 → 4 → 5 → 6 → 7

i = 1:
  next = 3
  2.next = 1
  prev = 2, curr = 3
  
  State: null ← 1 ← 2    3 → 4 → 5 → 6 → 7

i = 2:
  next = 4
  3.next = 2
  prev = 3, curr = 4
  
  State: null ← 1 ← 2 ← 3    4 → 5 → 6 → 7

After reversal:
  Reversed: 3 → 2 → 1
  prev = 3 (new head)
  curr = 4 (next group start)
  groupStart = 1 (now tail)
```

**Reconnection:**
```
prevGroupTail.next = prev
  → dummy.next = 3

groupStart.next = curr
  → 1.next = 4

prevGroupTail = groupStart
  → prevGroupTail = 1

Result so far:
  dummy → 3 → 2 → 1 → 4 → 5 → 6 → 7
```

---

**Iteration 2 (Group 2: nodes 4,5,6):**

**Count Phase:**
```
current = prevGroupTail.next = 4
Count nodes: 4, 5, 6
count = 3 ✓

Enough nodes for another group
```

**Reversal Setup:**
```
groupStart = 4
prev = null
curr = 4
```

**Reversal Loop:**
```
i = 0:
  next = 5
  4.next = null
  prev = 4, curr = 5
  
  State: 3 → 2 → 1 → (null ← 4)    5 → 6 → 7

i = 1:
  next = 6
  5.next = 4
  prev = 5, curr = 6
  
  State: 3 → 2 → 1 → (null ← 4 ← 5)    6 → 7

i = 2:
  next = 7
  6.next = 5
  prev = 6, curr = 7
  
  State: 3 → 2 → 1 → (null ← 4 ← 5 ← 6)    7

After reversal:
  Reversed: 6 → 5 → 4
  prev = 6 (new head)
  curr = 7 (next group start)
  groupStart = 4 (now tail)
```

**Reconnection:**
```
prevGroupTail.next = prev
  → 1.next = 6

groupStart.next = curr
  → 4.next = 7

prevGroupTail = groupStart
  → prevGroupTail = 4

Result so far:
  dummy → 3 → 2 → 1 → 6 → 5 → 4 → 7
```

---

**Iteration 3 (Remaining: node 7):**

**Count Phase:**
```
current = prevGroupTail.next = 7
Count nodes: 7
count = 1 < k=3 ❌

Not enough nodes, break
```

**Final Result:**
```
dummy → 3 → 2 → 1 → 6 → 5 → 4 → 7

Return dummy.next:
  3 → 2 → 3 → 6 → 5 → 4 → 7 ✓

Group 1 [1,2,3] reversed to [3,2,1] ✓
Group 2 [4,5,6] reversed to [6,5,4] ✓
Remaining [7] left unchanged ✓
```

---

### Visual: Pointer Tracking

```
Before Group 1 Reversal:
  dummy → [1 → 2 → 3] → 4 → 5 → 6 → 7
  ^       ^
  |       groupStart
  prevGroupTail

After Group 1 Reversal:
  dummy → [3 → 2 → 1] → 4 → 5 → 6 → 7
          ^       ^     ^
          |       |     next group
          new     new
          head    tail

Reconnected:
  dummy → 3 → 2 → 1 → 4 → 5 → 6 → 7
  ^               ^
  |               new prevGroupTail
  prevGroupTail before

Before Group 2 Reversal:
  3 → 2 → 1 → [4 → 5 → 6] → 7
              ^
              groupStart

After Group 2 Reversal:
  3 → 2 → 1 → [6 → 5 → 4] → 7
              ^       ^     ^
              new     new   next
              head    tail  group

Reconnected:
  3 → 2 → 1 → 6 → 5 → 4 → 7

All groups reversed and connected! ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | In-Place | Pointers Only | Recommended |
|----------|------|-------|----------|---------------|-------------|
| **Iterative In-Place** | **O(n)** | **O(1)** | **Yes** | **Yes** | **Yes ✅** |
| Recursive | O(n) | O(n/k) | Yes | Yes | No (space) |
| Array-Based | O(n) | O(n) | No | No | No (space) |
| Stack-Based | O(n) | O(k) | No | No | No (space) |
| Value Swapping | O(n) | O(1) | Yes | No | No (forbidden) |

**Winner**: **Iterative In-Place** — only solution meeting all constraints!

**Why Each Approach Fails or Succeeds:**
- **Iterative**: O(1) space, modifies pointers ✓
- **Recursive**: Recursion stack O(n/k) ≈ O(n) space ❌
- **Array-Based**: Stores all nodes O(n) space ❌
- **Stack-Based**: Stack of size k, O(k) space ❌
- **Value Swapping**: Violates "pointers only" constraint ❌

---

## Key Takeaways

1. **Dummy node** — simplifies head management
2. **Count before reverse** — ensure k nodes available
3. **Track prevGroupTail** — for reconnection
4. **In-place reversal** — O(1) space requirement
5. **After reversal** — original head becomes tail
6. **Reconnect carefully** — prevGroupTail to new head, new tail to next group
7. **Update prevGroupTail** — for next iteration
8. **Loop exactly k times** — no off-by-one errors
9. **Check constraints** — k=1 (no change), k>n (no reversal)
10. **Return dummy.next** — not dummy itself

---

## Interview Tips

**What to say in an interview:**

> "To reverse nodes in k-groups in-place, I'll use an iterative approach with careful pointer management. The key insight is that I need to reverse each group of k nodes independently, then reconnect them.
>
> I'll start with a dummy node pointing to the head to simplify handling head changes. For each iteration, I'll first count k nodes to ensure there are enough nodes to reverse—if not, I'll leave the remaining nodes unchanged as required. If k nodes are available, I'll save the position where the next group starts, then reverse the current k nodes using the standard reversal technique with three pointers.
>
> After reversing, the original group head becomes the tail. I'll reconnect by setting the previous group's tail to point to the new head of the reversed group, and the new tail to point to the next group's head. Then I'll update my prevGroupTail pointer to the new tail for the next iteration.
>
> The time complexity is O(n) since each node is visited a constant number of times during counting and reversal. The space complexity is O(1) since I only use a fixed number of pointers regardless of input size. This meets all the constraints: in-place modification with only next pointer changes."

**Key points to mention:**
1. **Dummy node** for head management
2. **Count k nodes** before reversing each group
3. **Standard reversal** technique for k nodes
4. **Track pointers**: prevGroupTail, groupStart, next group
5. **Reconnection**: two steps after reversal
6. **Update prevGroupTail** for next iteration
7. **Partial groups** left unchanged
8. **In-place**: only pointer manipulation
9. **Time**: O(n), **Space**: O(1)
10. **Edge cases**: k=1, k=n, k>n

**Common Follow-ups:**
- "What if k=1?" → No reversal needed, return same list
- "Can you do it recursively?" → Yes, but O(n/k) space for recursion stack, not truly O(1)
- "What if we should reverse all remaining nodes?" → Remove count check, always reverse
- "How to handle empty list?" → Return null immediately
- "Can you reverse without counting?" → Possible but would need to handle incomplete groups carefully, counting is cleaner

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| Reverse Linked List | Easy | In-Place Reversal | Reverse entire list (k=n) |
| Reverse Linked List II | Medium | Segment Reversal | Reverse [left, right] range |
| **Reverse Nodes in K-Group** | Hard | **Group Reversal** | **This problem** |
| Swap Nodes in Pairs | Medium | Group Reversal | Special case k=2 |
| Rotate List | Medium | Pointer Manipulation | Rotation, not reversal |
| Palindrome Linked List | Easy | Reversal + Two Pointers | Check palindrome |

**Pattern Progression**:
1. **Reverse Linked List** — Base operation (reverse all)
2. **Reverse Linked List II** — Reverse segment [left, right]
3. **Swap Nodes in Pairs** — Reverse groups of 2
4. **Reverse Nodes in K-Group** (this) — Reverse groups of k with condition

---

## Final Pattern Label

✅ **Iterative In-Place Group Reversal with Conditional Processing**

**Remember:** This is a **group reversal** problem requiring **O(1) space** and **pointer-only modifications**. Use **dummy node** pointing to head for clean head management. **Main algorithm**: (1) Count k nodes from current position, (2) if count < k, break (leave remaining unchanged), (3) if count == k, reverse those k nodes in-place using standard three-pointer technique (prev, curr, next), (4) after reversal, original groupStart becomes tail and prev becomes new head, (5) reconnect: `prevGroupTail.next = prev` (new head), `groupStart.next = curr` (tail to next group), (6) update `prevGroupTail = groupStart` for next iteration, (7) repeat until fewer than k nodes remain. **Reversal loop**: exactly k iterations with `curr.next = prev`, advancing prev and curr. **Time**: O(n) each node visited constant times (once in counting, once in reversal if in complete group). **Space**: O(1) only pointer variables (dummy, prevGroupTail, groupStart, prev, curr, next). **Common mistakes**: not counting before reversing (might reverse incomplete group), wrong reconnection (connecting to wrong nodes), not using dummy (complex head handling), not updating prevGroupTail (next iteration fails), off-by-one in loop (reversing k+1 or k-1 nodes), modifying values instead of pointers (explicitly forbidden), returning dummy instead of dummy.next. **Critical insight**: groupStart before reversal = tail after reversal, prev after reversal = new head. Pattern: **conditional group processing** with **in-place segment reversal** and **careful reconnection**! ✓
