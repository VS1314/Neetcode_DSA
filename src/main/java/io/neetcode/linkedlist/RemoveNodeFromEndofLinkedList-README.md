# Remove Nth Node From End of List

## Problem Description

**Difficulty**: Medium

Given the `head` of a linked list and an integer `n`, remove the **nth node from the end** of the list and return its head.

**Important Constraints:**
- Must remove from the **end**, not the beginning
- `n` is guaranteed to be valid (1 ≤ n ≤ list length)
- Need to return the modified list's head

**Visual Example:**
```
Original: 1 → 2 → 3 → 4 → 5 → null
          ↑       ↑       ↑
        5th     3rd     1st from end

Remove 2nd from end (node 4):
Result: 1 → 2 → 3 → 5 → null
```

## Examples

### Example 1:
```
Input: head = [1,2,3,4], n = 2
Output: [1,2,4]

Explanation:
Original: 1 → 2 → 3 → 4 → null
          ↑   ↑   ↑   ↑
        4th 3rd 2nd 1st from end

Remove 2nd from end (node 3)
Result: 1 → 2 → 4 → null
```

### Example 2:
```
Input: head = [5], n = 1
Output: []

Explanation:
Single node list
Remove 1st from end (only node)
Result: empty list
```

### Example 3:
```
Input: head = [1,2], n = 2
Output: [2]

Explanation:
Remove 2nd from end (first node, the head)
Result: [2]
```

### Example 4:
```
Input: head = [1,2], n = 1
Output: [1]

Explanation:
Remove 1st from end (last node)
Result: [1]
```

### Example 5:
```
Input: head = [1,2,3,4,5], n = 5
Output: [2,3,4,5]

Explanation:
Remove 5th from end (first node, the head)
Result: [2,3,4,5]
```

### Example 6:
```
Input: head = [1,2,3,4,5], n = 1
Output: [1,2,3,4]

Explanation:
Remove 1st from end (last node)
Result: [1,2,3,4]
```

### Example 7:
```
Input: head = [1,2,3], n = 2
Output: [1,3]

Explanation:
Remove 2nd from end (middle node)
Result: [1,3]
```

### Example 8:
```
Input: head = [1,2,3,4,5,6], n = 3
Output: [1,2,3,5,6]

Explanation:
Remove 3rd from end (node 4)
Result: [1,2,3,5,6]
```

### Example 9:
```
Input: head = [1], n = 1
Output: []

Explanation:
Single node, remove it
Empty list
```

### Example 10:
```
Input: head = [1,2,3,4,5,6,7], n = 4
Output: [1,2,3,5,6,7]

Explanation:
Remove 4th from end (node 4)
Result: [1,2,3,5,6,7]
```

## Constraints
- The number of nodes in the list is `sz`
- `1 <= sz <= 30`
- `0 <= Node.val <= 100`
- `1 <= n <= sz`

**Recommended Complexity**: 
- Time: O(n) where n is the length of the list
- Space: O(1) — constant extra space (one pass solution)

---

## Pattern Recognition

**Primary Pattern**: **Two-Pointer with Gap (Dummy Node Technique)**

**Why This Pattern?**
- Need to find **nth node from end** without knowing list length
- Must do in **one pass** (O(n) time)
- Need **O(1) space** (no array storage)
- **Two pointers with gap n** solve this elegantly

**Key Insight**: Gap Between Pointers
```
If two pointers are n nodes apart:
  When first pointer reaches end
  Second pointer is at (length - n)th node
  Which is n nodes from end!

Example: [1,2,3,4,5], n=2

First pointer moves n steps ahead:
  first starts at 1
  Move 2 steps: first at 3
  
Now move both together:
  first=3, second=1
  first=4, second=2
  first=5, second=3
  first=null, second=4
  
second is at node before target (3)
3 is 2 nodes from end ✓
Remove 3's next (node 4)
```

**Why Dummy Node?**
```
Problem: Removing head is special case

Without dummy:
  If n = length, remove head
  Need special handling
  
  if (n == length):
      return head.next
  else:
      // find and remove node
  
  Complex! ❌

With dummy:
  dummy → head → rest of list
  
  Dummy always stays before actual list
  If removing head, dummy.next changes
  No special case!
  
  Always return dummy.next
  Simple! ✓
```

**The Two-Pointer Dance**:
```
Setup:
  dummy → 1 → 2 → 3 → 4 → 5 → null
  ↑
  slow, fast

Step 1: Move fast n steps ahead
  If n = 2:
    fast moves: dummy → 1 → 2
    
  dummy → 1 → 2 → 3 → 4 → 5 → null
  ↑           ↑
  slow      fast

Step 2: Move both until fast reaches end
  Move 1: slow=1, fast=3
  Move 2: slow=2, fast=4
  Move 3: slow=3, fast=5
  Move 4: slow=4, fast=null
  
  dummy → 1 → 2 → 3 → 4 → 5 → null
                      ↑
                     slow

Step 3: Remove node after slow
  slow.next = slow.next.next
  4.next = 5.next = null
  
  Result: 1 → 2 → 3 → 5 → null ✓
```

**Why Move Fast n Steps (Not n+1)**:
```
We want slow to end at node BEFORE target

Example: [1,2,3,4,5], n=2, remove node 4

If fast moves n=2 steps from dummy:
  dummy → 1 → 2 → 3 → 4 → 5
  ↑           ↑
 slow        fast
 
When fast reaches null:
  slow at node before target ✓

If fast moves n+1 steps:
  slow would be AT target
  Can't easily remove (need previous node)
  
n steps is correct!
```

**Edge Case: Removing Head**:
```
List: [1,2,3], n=3 (remove head)

With dummy:
  dummy → 1 → 2 → 3 → null
  
  Move fast 3 steps: fast at 3
  dummy → 1 → 2 → 3 → null
  ↑               ↑
 slow            fast
 
  Move both until fast=null:
    Move 1: slow=1, fast=null
  
  slow at 1 (before head from dummy's perspective)
  
  Wait, that's wrong! Let me reconsider...

Actually, slow should be at dummy when removing head:
  Move fast n steps from dummy:
    fast at node n
  
  Move both until fast reaches null:
    slow ends at node (length - n)
    Which is 0 when n = length
    So slow at dummy ✓
    
  slow.next = slow.next.next
  dummy.next = head.next
  Removes head! ✓
```

**Two-Pass vs One-Pass**:
```
Two-Pass approach:
  Pass 1: Count length (L nodes)
  Pass 2: Go to (L-n)th node, remove next
  
  Time: O(n) + O(n) = O(n)
  Space: O(1)
  Works but makes two traversals

One-Pass approach (two pointers):
  Move fast n steps
  Move both until fast reaches end
  Remove node after slow
  
  Time: O(n) single traversal
  Space: O(1)
  Optimal! ✓
```

**Calculating Position from End**:
```
List length: L
Remove nth from end

From end: position n
From start: position (L - n + 1)

To remove, need node at position (L - n)
(one before target)

Example: L=5, n=2
  From start: position 4
  Need node at position 3
  
Two-pointer achieves this:
  Gap of n between pointers
  When fast at end, slow at (L-n)
```

**Why This Works (Mathematical Proof)**:
```
List has L nodes (0-indexed: 0 to L-1)

Setup:
  fast at position n (after moving n steps from dummy)
  slow at position 0 (at dummy)
  Gap = n

Moving both together:
  After k steps:
    fast at position (n + k)
    slow at position k
    Gap still n
  
When fast reaches end (null):
  fast at position L (beyond last node)
  slow at position (L - n)
  
Position (L - n) is the node before nth from end!

Example: L=5, n=2
  slow at position 5-2 = 3
  Node 3 is before node 4
  Node 4 is 2nd from end ✓
```

**Related Patterns**:
1. **Two Pointers** — Different speeds/gaps
2. **Dummy Node** — Simplify head removal
3. **Linked List Traversal** — One pass
4. **Gap Technique** — Maintain fixed distance

---

## Algorithm & Approach

### Core Insight

**Why Two-Pointer with Gap Works:**
```
Key observations:
  1. Gap of n nodes between pointers
  2. When first reaches end, second at right position
  3. Dummy node eliminates head removal edge case
  4. One pass through list
```

**The Optimal Strategy**:
```
Key steps:
  1. Create dummy node before head
  2. Move fast pointer n steps ahead
  3. Move both pointers until fast reaches end
  4. Remove node after slow pointer
  5. Return dummy.next (new head)
```

### Step-by-Step Algorithm

---

#### **Approach 1: Two-Pointer with Dummy Node - OPTIMAL**

**Core Idea**:
- Use dummy node to handle head removal
- Create gap of n between two pointers
- Move both until first reaches end
- Remove node after second pointer

**Algorithm**
```
removeNthFromEnd(head, n):
    // Create dummy node
    dummy = new ListNode(0)
    dummy.next = head
    
    // Initialize pointers
    slow = dummy
    fast = dummy
    
    // Move fast n steps ahead
    for i = 0 to n:
        fast = fast.next
    
    // Move both until fast reaches end
    while fast != null:
        slow = slow.next
        fast = fast.next
    
    // Remove node after slow
    slow.next = slow.next.next
    
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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // Create dummy node to handle edge cases (removing head)
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Initialize two pointers
        ListNode slow = dummy;
        ListNode fast = dummy;
        
        // Move fast pointer n steps ahead
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        
        // Move both pointers until fast reaches the end
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        
        // Remove the nth node from end
        slow.next = slow.next.next;
        
        // Return new head (dummy.next)
        return dummy.next;
    }
}
```

**Alternative: Move Fast n+1 Steps**
```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode slow = dummy;
        ListNode fast = dummy;
        
        // Move fast n+1 steps ahead
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        
        // Move both until fast reaches null
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        
        // Remove node
        slow.next = slow.next.next;
        
        return dummy.next;
    }
}
```

**Example Walkthrough**

Input: `head = [1,2,3,4,5]`, `n = 2`

**Setup:**
```
Create dummy:
  dummy → 1 → 2 → 3 → 4 → 5 → null
  
Initialize:
  slow = dummy
  fast = dummy
```

**Phase 1: Move fast n steps ahead**
```
n = 2, so move fast 2 steps

Step 1: fast = fast.next (fast at 1)
Step 2: fast = fast.next (fast at 2)

After loop:
  dummy → 1 → 2 → 3 → 4 → 5 → null
  ↑           ↑
 slow       fast
```

**Phase 2: Move both until fast.next is null**
```
Iteration 1:
  fast.next = 3 (not null)
  slow = slow.next (slow at 1)
  fast = fast.next (fast at 3)
  
  dummy → 1 → 2 → 3 → 4 → 5 → null
          ↑           ↑
         slow       fast

Iteration 2:
  fast.next = 4 (not null)
  slow = slow.next (slow at 2)
  fast = fast.next (fast at 4)
  
  dummy → 1 → 2 → 3 → 4 → 5 → null
                  ↑       ↑
                 slow   fast

Iteration 3:
  fast.next = 5 (not null)
  slow = slow.next (slow at 3)
  fast = fast.next (fast at 5)
  
  dummy → 1 → 2 → 3 → 4 → 5 → null
                      ↑       ↑
                     slow   fast

Iteration 4:
  fast.next = null (stop)
  
Final positions:
  slow at node 3
  fast at node 5 (last node)
```

**Phase 3: Remove node after slow**
```
slow.next = slow.next.next
3.next = 4.next = 5

Result:
  dummy → 1 → 2 → 3 → 5 → null
```

**Return:**
```
return dummy.next = 1

Output: [1,2,3,5] ✓
```

**Complexity Analysis**
- **Time**: O(n) — Single pass through list
- **Space**: O(1) — Only two pointers and dummy

---

#### **Approach 2: Two-Pass (Count Length First) - Alternative**

**Core Idea**:
- First pass: count list length
- Second pass: go to (length - n)th node
- Remove next node

**Algorithm**
```
removeNthFromEnd(head, n):
    // First pass: count length
    length = 0
    curr = head
    while curr != null:
        length++
        curr = curr.next
    
    // Edge case: remove head
    if length == n:
        return head.next
    
    // Second pass: find (length - n)th node
    curr = head
    for i = 1 to (length - n - 1):
        curr = curr.next
    
    // Remove next node
    curr.next = curr.next.next
    
    return head
```

**Code Implementation**
```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // First pass: count length
        int length = 0;
        ListNode curr = head;
        while (curr != null) {
            length++;
            curr = curr.next;
        }
        
        // Edge case: removing head
        if (length == n) {
            return head.next;
        }
        
        // Second pass: find node before target
        curr = head;
        for (int i = 0; i < length - n - 1; i++) {
            curr = curr.next;
        }
        
        // Remove target node
        curr.next = curr.next.next;
        
        return head;
    }
}
```

**Complexity Analysis**
- **Time**: O(n) — Two passes
- **Space**: O(1) — Only pointers

**When to Use**:
- Two-pass is simpler to understand
- But one-pass (two-pointer) is more elegant
- **Two-pointer with dummy is preferred**

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Passes | Recommended |
|----------|------|-------|--------|-------------|
| **Two-Pointer with Dummy** | **O(n)** | **O(1)** | **1 ✅** | **Yes ✅** |
| Two-Pass (count first) | O(n) | O(1) | 2 | No (less elegant) |
| Array storage | O(n) | O(n) | 1 | No (extra space) |

**Winner**: **Two-pointer with dummy** — one pass, O(1) space!

### Why Use Dummy Node

```
Problem: Removing head needs special handling

Test case: [1,2], n=2 (remove head)

Without dummy:
  if (n == length):
      return head.next  // Special case
  else:
      // Normal removal
  
  Need to count length first! ❌

With dummy:
  dummy → 1 → 2 → null
  
  Move fast 2 steps: fast at 2
  Move both: slow stays at dummy, fast at null
  
  slow.next = slow.next.next
  dummy.next = 2
  
  return dummy.next = 2 ✓
  
  No special case needed!
```

### Why Move Fast n Steps (Not n+1)

```
Two valid approaches:

Approach 1: Move n steps, check fast.next
  for i = 0 to n-1:
      fast = fast.next
  
  while fast.next != null:
      move both
  
  slow ends at node before target ✓

Approach 2: Move n+1 steps, check fast
  for i = 0 to n:
      fast = fast.next
  
  while fast != null:
      move both
  
  slow ends at node before target ✓

Both work! Approach 1 is more common.
```

### Why Check fast.next (Not fast)

```
With n steps ahead and checking fast.next:

Example: [1,2,3], n=2

Move fast 2 steps: fast at 2
  dummy → 1 → 2 → 3 → null
  ↑           ↑
 slow       fast

Loop: while fast.next != null
  Iteration 1:
    fast.next = 3 (not null)
    slow = 1, fast = 3
    
  Iteration 2:
    fast.next = null (stop)
    
  slow at 1 (before target 2) ✓

If we checked fast != null:
  Would need to move fast n+1 steps
  Same result, just different style
```

### Why This is Optimal

```
Time: O(n)
  Single pass through list
  Visit each node at most once
  Cannot do better!

Space: O(1)
  Only three pointers (dummy, slow, fast)
  No recursion, no arrays
  Optimal!

One pass vs two pass:
  Two-pass works but less elegant
  One-pass is preferred in interviews
```

---

## Critical Edge Cases & Gotchas

### 1. **Remove Head (n = length)**
```java
Input: head = [1,2,3], n = 3
Output: [2,3]

Remove first node
Dummy node handles this automatically
```

### 2. **Remove Tail (n = 1)**
```java
Input: head = [1,2,3], n = 1
Output: [1,2]

Remove last node
Most common case
```

### 3. **Single Node List**
```java
Input: head = [1], n = 1
Output: []

Remove only node
Result is empty list
```

### 4. **Two Node List, Remove First**
```java
Input: head = [1,2], n = 2
Output: [2]

Remove head
Return second node
```

### 5. **Two Node List, Remove Second**
```java
Input: head = [1,2], n = 1
Output: [1]

Remove tail
Return first node
```

### 6. **Remove Middle Node**
```java
Input: head = [1,2,3,4,5], n = 3
Output: [1,2,4,5]

Remove middle node (3)
Common case
```

### 7. **Large List, Remove Near Start**
```java
Input: head = [1,2,3,...,30], n = 28
Output: [1,2,4,5,...,30]

Remove 3rd node from start
```

### 8. **Large List, Remove Near End**
```java
Input: head = [1,2,3,...,30], n = 2
Output: [1,2,3,...,28,30]

Remove 29th node
```

### 9. **n = length (Maximum n)**
```java
Input: head = [1,2,3,4,5], n = 5
Output: [2,3,4,5]

Always removes head
```

### 10. **n = 1 (Minimum n)**
```java
Input: head = [1,2,3,4,5], n = 1
Output: [1,2,3,4]

Always removes tail
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Using Dummy Node**
```java
// WRONG - no dummy node
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode slow = head;
    ListNode fast = head;
    
    // Move fast n steps
    for (int i = 0; i < n; i++) {
        fast = fast.next;
    }
    
    // What if fast is now null? (removing head)
    // Need special case! ❌
    if (fast == null) {
        return head.next;
    }
    
    while (fast.next != null) {
        slow = slow.next;
        fast = fast.next;
    }
    
    slow.next = slow.next.next;
    return head;
}
```

**Why wrong**: Needs special case for head removal!

**Fix**: Use dummy node
```java
ListNode dummy = new ListNode(0);
dummy.next = head;
// ... rest of logic
return dummy.next;
```

### ❌ **MISTAKE 2: Moving Fast Wrong Number of Steps**
```java
// WRONG - moving n-1 steps instead of n
for (int i = 0; i < n - 1; i++) {  // WRONG!
    fast = fast.next;
}
```

**Why wrong**: Gap is only n-1!

**Dry run failure:**
```
List: [1,2,3], n=2

Move fast n-1=1 step: fast at 1
  dummy → 1 → 2 → 3
  ↑       ↑
 slow   fast

Move both:
  slow=1, fast=2
  slow=2, fast=3
  slow=3, fast=null
  
slow at 3 (should be at 1!) ❌
```

**Fix**: Move n steps
```java
for (int i = 0; i < n; i++) {
    fast = fast.next;
}
```

### ❌ **MISTAKE 3: Wrong Loop Condition**
```java
// WRONG - checking fast instead of fast.next
while (fast != null) {  // WRONG!
    slow = slow.next;
    fast = fast.next;
}
```

**Why wrong**: Moves one step too far!

**Dry run failure:**
```
If we moved fast n steps and check fast != null:
  slow would end at target node, not before it
  Can't remove node without previous node! ❌
```

**Fix**: Check fast.next
```java
while (fast.next != null) {
    slow = slow.next;
    fast = fast.next;
}
```

### ❌ **MISTAKE 4: Not Removing Node Correctly**
```java
// WRONG - trying to remove by setting to null
slow.next = null;  // WRONG! Cuts off rest of list
```

**Why wrong**: Loses all nodes after target!

**Dry run failure:**
```
List: 1 → 2 → 3 → 4 → 5
Remove 2nd from end (node 4)

slow at node 3
slow.next = null

Result: 1 → 2 → 3 → null ❌
Lost node 5!
```

**Fix**: Skip target node
```java
slow.next = slow.next.next;
```

### ❌ **MISTAKE 5: Returning head Instead of dummy.next**
```java
// WRONG - returning original head
return head;  // What if head was removed?
```

**Why wrong**: Head might have been removed!

**Dry run failure:**
```
List: [1,2], n=2 (remove head)

After removal:
  dummy → 2 → null
  head still points to 1
  
return head = 1 ❌
Should return 2!
```

**Fix**: Return dummy.next
```java
return dummy.next;
```

### ❌ **MISTAKE 6: Off-by-One in Loop**
```java
// WRONG - <= instead of <
for (int i = 0; i <= n; i++) {  // WRONG!
    fast = fast.next;
}
```

**Why wrong**: Moves n+1 steps instead of n!

**Impact:**
```
If we want gap of n but move n+1 steps:
  Gap becomes n+1
  Wrong position! ❌
  
Use i < n to move exactly n steps
```

**Fix**: Use i < n
```java
for (int i = 0; i < n; i++) {
    fast = fast.next;
}
```

### ❌ **MISTAKE 7: Not Checking if slow.next is null**
```java
// WRONG - might cause NullPointerException
slow.next = slow.next.next;  // What if slow.next is null?
```

**Why wrong**: In this problem, slow.next is guaranteed non-null!

**Actually**: Since n is guaranteed valid (1 ≤ n ≤ length):
  - slow will always have a next node
  - No check needed
  
But defensive programming doesn't hurt:
```java
if (slow.next != null) {
    slow.next = slow.next.next;
}
```

### ❌ **MISTAKE 8: Using Three Pointers**
```java
// WRONG - unnecessarily complex
ListNode prev = dummy;
ListNode slow = head;
ListNode fast = head;

// Move fast n steps
for (int i = 0; i < n; i++) {
    fast = fast.next;
}

// Move all three
while (fast != null) {
    prev = slow;
    slow = slow.next;
    fast = fast.next;
}

prev.next = slow.next;
```

**Why wrong**: Overcomplicates!

**Better**: Just use slow and fast starting at dummy
```java
ListNode slow = dummy;
ListNode fast = dummy;
// ... simpler logic
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| **Move fast n steps** | O(n) | At most n steps |
| **Move both to end** | O(n) | At most n steps |
| **Remove node** | O(1) | Single pointer update |
| **Total** | **O(n)** | Single pass |

**Time analysis**:
```
Two phases:
  Phase 1: Move fast n steps
    Steps: n
    
  Phase 2: Move both until fast at end
    Steps: (length - n)
    
Total steps: n + (length - n) = length = O(n)

Single pass through list ✓
Cannot do better (must traverse list)
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| dummy node | O(1) | Single node |
| slow pointer | O(1) | Single reference |
| fast pointer | O(1) | Single reference |
| **Total** | **O(1)** | Constant space |

**Space analysis**:
```
Only three items:
  1. dummy node
  2. slow pointer
  3. fast pointer

No recursion, no arrays
Space: O(1) ✓
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `head = [1,2,3,4,5,6]`, `n = 3`

**Expected Output:** `[1,2,3,5,6]` (remove 4)

---

**Setup:**
```
Create dummy:
  dummy → 1 → 2 → 3 → 4 → 5 → 6 → null

Initialize:
  slow = dummy
  fast = dummy

Goal: Remove 3rd from end (node 4)
```

---

**Phase 1: Move fast n=3 steps ahead**

```
Step 1: fast = fast.next (fast at 1)
  dummy → 1 → 2 → 3 → 4 → 5 → 6 → null
  ↑       ↑
 slow   fast

Step 2: fast = fast.next (fast at 2)
  dummy → 1 → 2 → 3 → 4 → 5 → 6 → null
  ↑           ↑
 slow       fast

Step 3: fast = fast.next (fast at 3)
  dummy → 1 → 2 → 3 → 4 → 5 → 6 → null
  ↑               ↑
 slow           fast

After loop:
  Gap of 3 between slow and fast
```

---

**Phase 2: Move both until fast.next is null**

```
Iteration 1: fast.next = 4 (not null)
  slow = slow.next (slow at 1)
  fast = fast.next (fast at 4)
  
  dummy → 1 → 2 → 3 → 4 → 5 → 6 → null
          ↑               ↑
         slow           fast

Iteration 2: fast.next = 5 (not null)
  slow = slow.next (slow at 2)
  fast = fast.next (fast at 5)
  
  dummy → 1 → 2 → 3 → 4 → 5 → 6 → null
                  ↑               ↑
                 slow           fast

Iteration 3: fast.next = 6 (not null)
  slow = slow.next (slow at 3)
  fast = fast.next (fast at 6)
  
  dummy → 1 → 2 → 3 → 4 → 5 → 6 → null
                      ↑               ↑
                     slow           fast

Iteration 4: fast.next = null (stop)

Final positions:
  slow at node 3
  fast at node 6 (last node)
```

---

**Phase 3: Remove node after slow**

```
slow.next = slow.next.next
3.next = 4.next = 5

Result:
  dummy → 1 → 2 → 3 → 5 → 6 → null
```

---

**Return:**
```
return dummy.next = 1

Output: [1,2,3,5,6] ✓

Node 4 (3rd from end) successfully removed!
```

---

### Visual Timeline

```
Original: 1 → 2 → 3 → 4 → 5 → 6
          ↑       ↑       ↑
        6th     4th     2nd from end

Target: Remove 3rd from end (node 4)

After gap creation (n=3):
  dummy → 1 → 2 → 3 → 4 → 5 → 6
  ↑               ↑
 slow           fast
  
After moving both:
  dummy → 1 → 2 → 3 → 4 → 5 → 6
                      ↑               ↑
                     slow           fast
                     
After removal:
  1 → 2 → 3 → 5 → 6 ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Passes | Recommended |
|----------|------|-------|--------|-------------|
| **Two-Pointer with Dummy** | **O(n)** | **O(1)** | **1 ✅** | **Yes ✅** |
| Two-Pass (count first) | O(n) | O(1) | 2 | No (less elegant) |
| Array storage | O(n) | O(n) | 1 | No (extra space) |
| Recursion | O(n) | O(n) | 1 | No (stack space) |

**Winner**: **Two-pointer with dummy** — one pass, O(1) space, elegant!

---

## Key Takeaways

1. **Dummy node** — eliminates head removal edge case
2. **Two pointers** — maintain gap of n
3. **Move fast n steps** — create initial gap
4. **Move both together** — maintain gap
5. **slow ends before target** — allows removal
6. **Return dummy.next** — new head (might be different)
7. **O(n) time, O(1) space** — optimal single pass
8. **Check fast.next** — correct loop condition
9. **Skip node** — slow.next = slow.next.next
10. **Works for all cases** — head, tail, middle

---

## Interview Tips

**What to say in an interview:**

> "To remove the nth node from the end in one pass, I'll use the two-pointer technique with a dummy node. The dummy node simplifies handling the edge case where we need to remove the head. I'll maintain two pointers with a gap of n between them. First, I move the fast pointer n steps ahead from the dummy. Then, I move both pointers together until fast reaches the last node. At this point, slow will be at the node just before the one we need to remove. I can then remove the target node by setting slow.next to slow.next.next. Finally, I return dummy.next as the new head, which handles the case where the original head was removed. This solution runs in O(n) time with a single pass through the list and uses O(1) space since we only need two pointers and a dummy node."

**Key points to mention:**
1. **Dummy node** — handles head removal elegantly
2. **Two pointers** — with gap of n
3. **Move fast n steps** — establish gap
4. **Move both together** — until fast at end
5. **slow at node before target** — enables removal
6. **Skip target node** — slow.next = slow.next.next
7. **Return dummy.next** — new head
8. **O(n) time** — single pass
9. **O(1) space** — only pointers

**Common Follow-ups:**
- "Why use a dummy node?" → Eliminates special case for removing head
- "Can you do it in two passes?" → Yes, but one pass is more elegant
- "What if n is larger than list length?" → Problem guarantees n is valid
- "How to remove from the beginning?" → Standard deletion, or use dummy with n = length
- "Can you do it recursively?" → Yes, but uses O(n) stack space

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Remove Nth Node From End** | Medium | **Two-Pointer Gap** | **This problem** |
| Delete Node in Linked List | Easy | Direct Deletion | Given node to delete |
| Remove Linked List Elements | Easy | Filter | Remove all nodes with value |
| Middle of Linked List | Easy | Fast/Slow | Find middle, not remove |
| Reverse Linked List | Easy | Three Pointers | Reverse, not remove |
| Linked List Cycle Detection | Easy | Fast/Slow | Detect cycle, not remove |

**Pattern Progression**:
1. **Middle of Linked List** — Fast/slow pointers
2. **Remove Nth Node From End** (this) — Two pointers with gap
3. **Advanced deletion** — Multiple node removal patterns

---

## Final Pattern Label

✅ **Two-Pointer with Gap (Dummy Node Technique for Nth from End)**

**Remember:** This is the **two-pointer with gap technique** for removing nth node from end. Use a **dummy node** pointing to head to eliminate edge cases. Initialize both **slow and fast at dummy**. Move **fast n steps ahead** to create a gap of n (loop: `for i = 0 to n-1`). Then move **both pointers together** until **fast.next is null** (not fast itself!). At this point, **slow is at the node before target**. Remove target by **slow.next = slow.next.next**. Always **return dummy.next** (new head, which might be different if original head was removed). Achieves **O(n) time** (single pass) and **O(1) space** (only pointers). Critical: dummy node makes head removal seamless, gap of n ensures slow ends at correct position, and checking fast.next (not fast) positions slow before target. This elegant one-pass solution is preferred over two-pass approach!
