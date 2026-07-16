# Linked List Cycle Detection

## Problem Description

**Difficulty**: Easy

Given the beginning of a linked list `head`, return `true` if there is a cycle in the linked list. Otherwise, return `false`.

There is a **cycle** in a linked list if at least one node in the list can be visited again by following the `next` pointer.

**Cycle Definition:**
- A cycle occurs when a node's `next` pointer points back to a previous node in the list
- This creates a loop where you can traverse indefinitely
- Internally, `index` determines where the cycle begins (0-indexed)
- If `index = -1`, there is no cycle (tail points to `null`)

**Important:** The `index` parameter is **NOT** given to you. You must detect the cycle without knowing where it starts.

**Visual Example:**
```
No Cycle:
  1 → 2 → 3 → 4 → null

With Cycle (index = 1):
  1 → 2 → 3 → 4
      ↑         ↓
      ←---------←
  (tail connects back to node at index 1)
```

## Examples

### Example 1:
```
Input: head = [1,2,3,4], index = 1
Output: true

Explanation:
List structure:
  1 → 2 → 3 → 4
      ↑         ↓
      ←---------←

Node 4's next points to node 2 (index 1)
This creates a cycle: 1 → 2 → 3 → 4 → 2 → 3 → 4 → ...
Return true
```

### Example 2:
```
Input: head = [1,2], index = -1
Output: false

Explanation:
List structure:
  1 → 2 → null

No cycle, tail points to null
Return false
```

### Example 3:
```
Input: head = [1], index = -1
Output: false

Explanation:
Single node, no cycle
1 → null
Return false
```

### Example 4:
```
Input: head = [1,2], index = 0
Output: true

Explanation:
  1 → 2
  ↑   ↓
  ←---←

Node 2 points back to node 1
Cycle exists
```

### Example 5:
```
Input: head = [3,2,0,-4], index = 1
Output: true

Explanation:
  3 → 2 → 0 → -4
      ↑         ↓
      ←---------←

Classic cycle starting at index 1
```

### Example 6:
```
Input: head = [], index = -1
Output: false

Explanation:
Empty list, no cycle
```

### Example 7:
```
Input: head = [1], index = 0
Output: true

Explanation:
  1 (self-loop)
  ↑ ↓
  ←-←

Node points to itself
Cycle exists
```

### Example 8:
```
Input: head = [1,2,3,4,5], index = -1
Output: false

Explanation:
  1 → 2 → 3 → 4 → 5 → null

Long list, no cycle
```

### Example 9:
```
Input: head = [1,2,3,4,5], index = 4
Output: true

Explanation:
  1 → 2 → 3 → 4 → 5
                  ↑ ↓
                  ←-←

Tail points to itself (last node)
```

### Example 10:
```
Input: head = [1,2,3,4,5,6], index = 2
Output: true

Explanation:
  1 → 2 → 3 → 4 → 5 → 6
          ↑             ↓
          ←-------------←

Cycle starts at middle node (index 2)
```

## Constraints
- `0 <= Length of the list <= 1000`
- `-1000 <= Node.val <= 1000`
- `index` is `-1` or a valid index in the linked list
- **`index` is NOT provided as a parameter** — you must detect cycle without it

**Recommended Complexity**: 
- Time: O(n) where n is the length of the list
- Space: O(1) — constant extra space

---

## Pattern Recognition

**Primary Pattern**: **Floyd's Cycle Detection Algorithm (Fast & Slow Pointers / Tortoise & Hare)**

**Why This Pattern?**
- Need to detect **cycle** in linked list
- Cannot modify list structure (no marking nodes)
- Must use **O(1) space** (no hash set)
- **Two pointers at different speeds** detect cycles

**Key Insight**: Fast Pointer Eventually Catches Slow Pointer
```
If there's a cycle:
  Fast pointer moves 2 steps per iteration
  Slow pointer moves 1 step per iteration
  
  Fast pointer enters cycle first
  Then catches up to slow pointer inside cycle
  
  Why? Fast pointer gains 1 node per iteration
       Gap decreases by 1 each time
       Eventually gap becomes 0 → they meet!

If there's no cycle:
  Fast pointer reaches null
  Loop ends
  Return false
```

**The Tortoise and Hare Analogy**:
```
Imagine a race track (circular):
  Tortoise: slow runner (1 step/time)
  Hare: fast runner (2 steps/time)
  
If track is circular (cycle):
  Hare will eventually lap tortoise
  They meet at some point on track ✓
  
If track has an end (no cycle):
  Hare reaches end first
  Race ends, they never meet ❌
```

**Visual Example**:
```
List with cycle: 1 → 2 → 3 → 4 → 5
                     ↑           ↓
                     ←-----------←

Initial:
  slow = 1, fast = 1

Step 1:
  slow = 2 (moved 1)
  fast = 3 (moved 2)

Step 2:
  slow = 3 (moved 1)
  fast = 5 (moved 2)

Step 3:
  slow = 4 (moved 1)
  fast = 3 (moved 2, wrapped around cycle)

Step 4:
  slow = 5 (moved 1)
  fast = 5 (moved 2, wrapped around)
  
slow == fast → Cycle detected! ✓
```

**Why Fast Pointer Catches Slow Pointer**:
```
Mathematical proof:

When slow enters cycle:
  Fast is already inside (moved faster)
  
Inside cycle:
  Let gap = distance between fast and slow
  
Each iteration:
  slow moves +1
  fast moves +2
  gap decreases by 1
  
Example with gap = 5:
  Iteration 1: gap = 5 - 1 = 4
  Iteration 2: gap = 4 - 1 = 3
  Iteration 3: gap = 3 - 1 = 2
  Iteration 4: gap = 2 - 1 = 1
  Iteration 5: gap = 1 - 1 = 0 → Meet!
  
Gap guaranteed to reach 0!
Fast catches slow in at most cycle_length iterations.
```

**Why Two Different Speeds?**
```
If both pointers move at same speed:
  They never catch up (maintain same gap)
  No way to detect cycle! ❌

If one moves faster:
  Gap changes each iteration
  Eventually meet (if cycle exists) ✓
  
Speed ratio doesn't have to be 2:1
Could be 3:1, 2:1, etc.
But 2:1 is simplest and most efficient!
```

**No Cycle Case**:
```
List: 1 → 2 → 3 → 4 → null

Initial:
  slow = 1, fast = 1

Step 1:
  slow = 2
  fast = 3

Step 2:
  slow = 3
  fast = null (moved past end)
  
fast == null → No cycle! ✓
```

**Edge Cases Handled**:
```
Empty list:
  head = null
  fast = null
  Loop doesn't execute
  Return false ✓

Single node, no cycle:
  head = 1 → null
  fast = 1, fast.next = null
  Loop doesn't execute
  Return false ✓

Single node, self-loop:
  head = 1 (points to itself)
  slow = 1, fast = 1
  Move: slow = 1, fast = 1
  slow == fast → true ✓
```

**Comparison with HashSet Approach**:
```
HashSet approach:
  Set<Node> visited = new HashSet<>();
  while (head != null):
      if (visited.contains(head)):
          return true  // Seen before, cycle!
      visited.add(head)
      head = head.next
  return false
  
  Time: O(n) ✓
  Space: O(n) ❌ (stores all nodes)

Fast & Slow approach:
  slow = head, fast = head
  while (fast != null && fast.next != null):
      slow = slow.next
      fast = fast.next.next
      if (slow == fast):
          return true
  return false
  
  Time: O(n) ✓
  Space: O(1) ✓ (only two pointers)
  
Two-pointer is optimal!
```

**Time Complexity Analysis**:
```
Non-cycle part: n nodes
Cycle part: c nodes (c ≤ n)

Slow pointer:
  Visits all n nodes
  Then enters cycle
  Meets fast in at most c iterations
  Total: O(n + c) = O(n)

Fast pointer:
  Moves twice as fast
  Total steps: O(n)

Overall: O(n) ✓
```

**Related Patterns**:
1. **Two Pointers** — Different speeds
2. **Fast & Slow** — Used in many cycle problems
3. **Floyd's Algorithm** — Classic cycle detection
4. **Linked List Traversal** — Pointer manipulation

---

## Algorithm & Approach

### Core Insight

**Why Floyd's Algorithm Works:**
```
Key observations:
  1. Fast pointer moves twice as fast as slow
  2. If cycle exists, they must meet
  3. If no cycle, fast reaches null
  4. Only O(1) space needed (two pointers)
```

**The Optimal Strategy**:
```
Key steps:
  1. Initialize slow and fast to head
  2. Move slow 1 step, fast 2 steps
  3. If they meet → cycle exists
  4. If fast reaches null → no cycle
```

### Step-by-Step Algorithm

---

#### **Approach 1: Floyd's Cycle Detection (Fast & Slow Pointers) - OPTIMAL**

**Core Idea**:
- Use two pointers moving at different speeds
- Slow moves 1 step, fast moves 2 steps
- If they meet, cycle exists
- If fast reaches null, no cycle

**Algorithm**
```
hasCycle(head):
    // Edge case: empty or single node
    if head == null or head.next == null:
        return false
    
    // Initialize pointers
    slow = head
    fast = head
    
    // Move pointers until fast reaches end or they meet
    while fast != null and fast.next != null:
        // Move slow by 1
        slow = slow.next
        
        // Move fast by 2
        fast = fast.next.next
        
        // Check if they meet
        if slow == fast:
            return true  // Cycle detected
    
    // Fast reached null, no cycle
    return false
```

**Code Implementation**
```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */

public class Solution {
    public boolean hasCycle(ListNode head) {
        // Edge case: empty list or single node without cycle
        if (head == null || head.next == null) {
            return false;
        }
        
        // Initialize slow and fast pointers
        ListNode slow = head;
        ListNode fast = head;
        
        // Move pointers until fast reaches end or they meet
        while (fast != null && fast.next != null) {
            // Move slow pointer by 1 step
            slow = slow.next;
            
            // Move fast pointer by 2 steps
            fast = fast.next.next;
            
            // Check if pointers meet (cycle detected)
            if (slow == fast) {
                return true;
            }
        }
        
        // Fast pointer reached null, no cycle
        return false;
    }
}
```

**Optimized Version (No Edge Case Check)**
```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        // No edge case check needed!
        // Loop condition handles it
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                return true;
            }
        }
        
        return false;
    }
}
```

**Example Walkthrough**

Input: `head = [3,2,0,-4]`, `index = 1`

```
List structure:
  3 → 2 → 0 → -4
      ↑         ↓
      ←---------←

Initial state:
  slow = 3
  fast = 3
```

**Iteration 1:**
```
Before:
  slow = 3, fast = 3

Move:
  slow = slow.next = 2
  fast = fast.next.next = 0

Check:
  slow (2) == fast (0)? No

After:
  slow = 2, fast = 0
```

**Iteration 2:**
```
Before:
  slow = 2, fast = 0

Move:
  slow = slow.next = 0
  fast = fast.next.next = 2 (wrapped around cycle)

Check:
  slow (0) == fast (2)? No

After:
  slow = 0, fast = 2
```

**Iteration 3:**
```
Before:
  slow = 0, fast = 2

Move:
  slow = slow.next = -4
  fast = fast.next.next = -4 (wrapped around)

Check:
  slow (-4) == fast (-4)? Yes! ✓

Return true
```

**Trace through cycle:**
```
Positions in list [3,2,0,-4]:
  Index: 0  1  2  3
  Value: 3  2  0 -4
  
  Node 3 (-4) points back to node 1 (2)

Step 0: slow=0, fast=0 (both at 3)
Step 1: slow=1, fast=2 (2 and 0)
Step 2: slow=2, fast=1 (0 and 2) [fast wrapped]
Step 3: slow=3, fast=3 (-4 and -4) [both meet!]
```

**Complexity Analysis**
- **Time**: O(n) — At most 2n steps
- **Space**: O(1) — Only two pointers

**Why At Most O(n)?**
```
Case 1: No cycle
  Fast pointer reaches end in n/2 steps
  Time: O(n)

Case 2: Cycle exists
  Non-cycle part: k nodes
  Cycle part: c nodes (c ≤ n - k)
  
  Slow reaches cycle entrance: k steps
  Fast already in cycle (moved faster)
  
  Inside cycle:
    Gap between fast and slow ≤ c
    Gap decreases by 1 per iteration
    Meet in at most c steps
  
  Total: k + c ≤ n steps
  Time: O(n)
```

---

#### **Approach 2: HashSet - Alternative (Not Optimal)**

**Core Idea**:
- Store visited nodes in hash set
- If we see a node again, cycle exists
- If we reach null, no cycle

**Algorithm**
```
hasCycle(head):
    visited = new HashSet()
    current = head
    
    while current != null:
        if visited.contains(current):
            return true  // Seen before, cycle!
        visited.add(current)
        current = current.next
    
    return false  // Reached null, no cycle
```

**Code Implementation**
```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        Set<ListNode> visited = new HashSet<>();
        ListNode current = head;
        
        while (current != null) {
            // If we've seen this node before, there's a cycle
            if (visited.contains(current)) {
                return true;
            }
            
            // Mark node as visited
            visited.add(current);
            
            // Move to next node
            current = current.next;
        }
        
        // Reached end of list, no cycle
        return false;
    }
}
```

**Example Walkthrough**

Input: `head = [1,2,3,4]`, `index = 1`

```
Iteration 1:
  current = 1
  visited is empty
  Add 1 to visited
  visited = {1}

Iteration 2:
  current = 2
  2 not in visited
  Add 2 to visited
  visited = {1, 2}

Iteration 3:
  current = 3
  3 not in visited
  Add 3 to visited
  visited = {1, 2, 3}

Iteration 4:
  current = 4
  4 not in visited
  Add 4 to visited
  visited = {1, 2, 3, 4}

Iteration 5:
  current = 2 (cycle back)
  2 IS in visited! ✓
  Return true
```

**Complexity Analysis**
- **Time**: O(n) — Visit each node once
- **Space**: O(n) — Store all nodes in set

**When to Use**:
- HashSet is simpler to understand
- But uses O(n) extra space
- **Floyd's algorithm is preferred** (O(1) space)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Recommended |
|----------|------|-------|-------------|
| **Floyd's (Fast & Slow)** | **O(n)** | **O(1)** | **Yes ✅** |
| HashSet | O(n) | O(n) | No (extra space) |
| Modify nodes | O(n) | O(1) | No (destructive) |

**Winner**: **Floyd's Cycle Detection** — O(1) space, optimal!

### Why Fast Pointer Must Check fast.next

```
Loop condition: while (fast != null && fast.next != null)

Why check both?

If we only check fast != null:
  fast.next might be null
  fast = fast.next.next would cause NullPointerException!
  
Example:
  1 → 2 → null
  
  fast = 2
  fast != null? Yes (continue)
  fast = fast.next.next
       = (2.next).next
       = null.next → NullPointerException! ❌

With both checks:
  fast = 2
  fast != null? Yes
  fast.next != null? No (2.next = null)
  Exit loop ✓
  
Must check fast.next to safely do fast.next.next!
```

### Why Move Pointers Before Checking

```
Option 1: Check first (WRONG for some implementations)
  if (slow == fast):
      return true
  slow = slow.next
  fast = fast.next.next

  Problem: Both start at head
           First check: slow == fast (both at head)
           Returns true immediately! ❌
           False positive for no-cycle lists

Option 2: Move first (CORRECT)
  slow = slow.next
  fast = fast.next.next
  if (slow == fast):
      return true
  
  Both start at head
  Move them first
  Then check
  Avoids false positive ✓
```

**Note:** In our implementation, we move then check, which is correct!

### Why Initialize Both to head

```
Option 1: Both start at head
  slow = head
  fast = head
  
  Move immediately
  Then check
  Works correctly ✓

Option 2: Start at different positions
  slow = head
  fast = head.next
  
  More complex logic
  Need to handle fast being one ahead
  Not necessary

Option 1 is simpler and standard!
```

### Why Fast Catches Slow (Mathematical Proof)

```
When slow enters cycle:
  Fast is already inside (or entering)
  
Let's say:
  Cycle length = c
  Distance between fast and slow = d (0 < d < c)
  
Each iteration inside cycle:
  slow position: (pos_slow + 1) mod c
  fast position: (pos_fast + 2) mod c
  
  Gap: (pos_fast - pos_slow) mod c
  
  Gap decreases by 1 each iteration:
    New gap = (old_gap - 1) mod c
  
Example: c = 5, initial gap = 3
  Iteration 1: gap = 3 - 1 = 2
  Iteration 2: gap = 2 - 1 = 1
  Iteration 3: gap = 1 - 1 = 0 → Meet!
  
Gap guaranteed to reach 0 in at most c iterations!
```

### Why This is Optimal

```
Time: O(n)
  Must check all nodes to detect cycle
  Cannot do better than O(n)

Space: O(1)
  Only two pointers
  No extra data structures
  Optimal!

This is the best possible solution!
```

---

## Critical Edge Cases & Gotchas

### 1. **Empty List**
```java
Input: head = null
Output: false

Edge case: no nodes
fast = null
Loop doesn't execute
Return false ✓
```

### 2. **Single Node, No Cycle**
```java
Input: head = [1], index = -1
Output: false

Structure: 1 → null
fast.next = null
Loop doesn't execute
Return false ✓
```

### 3. **Single Node, Self-Loop**
```java
Input: head = [1], index = 0
Output: true

Structure: 1 points to itself
slow = 1, fast = 1
After move: slow = 1, fast = 1
slow == fast → true ✓
```

### 4. **Two Nodes, No Cycle**
```java
Input: head = [1,2], index = -1
Output: false

Structure: 1 → 2 → null
fast reaches null
Return false ✓
```

### 5. **Two Nodes, Cycle**
```java
Input: head = [1,2], index = 0
Output: true

Structure: 1 → 2
           ↑   ↓
           ←---←

Pointers meet after few iterations
Return true ✓
```

### 6. **Long List, No Cycle**
```java
Input: head = [1,2,3,...,1000], index = -1
Output: false

Fast pointer reaches end in ~500 steps
Return false ✓
```

### 7. **Cycle at Start**
```java
Input: head = [1,2,3], index = 0
Output: true

Structure: 1 → 2 → 3
           ↑       ↓
           ←-------←

All nodes in cycle
Pointers meet quickly
```

### 8. **Cycle at End**
```java
Input: head = [1,2,3,4,5], index = 4
Output: true

Structure: 1 → 2 → 3 → 4 → 5
                           ↑ ↓
                           ←-←

Last node points to itself
```

### 9. **Large Cycle**
```java
Input: head = [1,2,3,...,1000], index = 500
Output: true

Long non-cycle part, then large cycle
Still O(n) time
```

### 10. **Cycle in Middle**
```java
Input: head = [1,2,3,4,5,6], index = 2
Output: true

Structure: 1 → 2 → 3 → 4 → 5 → 6
                   ↑             ↓
                   ←-------------←

Some nodes outside cycle
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Only Checking fast != null**
```java
// WRONG - will cause NullPointerException
while (fast != null) {
    slow = slow.next;
    fast = fast.next.next;  // What if fast.next is null? ❌
    
    if (slow == fast) {
        return true;
    }
}
```

**Why wrong**: fast.next might be null!

**Dry run failure:**
```
List: 1 → 2 → null

Iteration 1:
  fast = 2
  fast != null? Yes
  fast = fast.next.next = (2.next).next = null.next
  NullPointerException! ❌
```

**Fix**: Check both fast and fast.next
```java
while (fast != null && fast.next != null) {
    // Safe to do fast.next.next
}
```

### ❌ **MISTAKE 2: Checking Before Moving**
```java
// WRONG - false positive on first iteration
ListNode slow = head;
ListNode fast = head;

while (fast != null && fast.next != null) {
    if (slow == fast) {  // WRONG: Check before move
        return true;
    }
    slow = slow.next;
    fast = fast.next.next;
}
```

**Why wrong**: Both start at head, false positive!

**Dry run failure:**
```
List: 1 → 2 → 3 → null (no cycle)

Initial:
  slow = 1, fast = 1

First iteration:
  Check: slow == fast? Yes (both at head)
  Return true ❌
  
But there's no cycle! False positive!
```

**Fix**: Move first, then check
```java
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    
    if (slow == fast) {  // Check after move
        return true;
    }
}
```

### ❌ **MISTAKE 3: Starting Fast One Node Ahead**
```java
// WRONG - complicates logic and misses some cycles
ListNode slow = head;
ListNode fast = head.next;  // Start one ahead

while (fast != null && fast.next != null) {
    if (slow == fast) {
        return true;
    }
    slow = slow.next;
    fast = fast.next.next;
}
```

**Why wrong**: Might miss cycle!

**Dry run failure:**
```
List: 1 → 2
      ↑   ↓
      ←---←

Initial:
  slow = 1, fast = 2

Iteration 1:
  Check: 1 == 2? No
  slow = 2
  fast = 1

Iteration 2:
  Check: 2 == 1? No
  slow = 1
  fast = 2

Infinite loop! Never detects cycle ❌

Actually, with check-before-move this can miss cycles.
Starting both at head is simpler and correct!
```

**Fix**: Start both at head
```java
ListNode slow = head;
ListNode fast = head;  // Same starting point
```

### ❌ **MISTAKE 4: Moving Slow by 2, Fast by 1**
```java
// WRONG - reversed speeds
slow = slow.next.next;  // Moving by 2
fast = fast.next;       // Moving by 1
```

**Why wrong**: Defeats the purpose!

**Issue:**
```
Slow should be slower!
Fast should be faster!

If slow moves faster than fast:
  - Naming is confusing
  - Logic is backwards
  
Doesn't break algorithm completely,
but is confusing and non-standard.

Standard: slow by 1, fast by 2
```

### ❌ **MISTAKE 5: Using Node Values Instead of References**
```java
// WRONG - comparing values, not nodes
if (slow.val == fast.val) {
    return true;  // WRONG!
}
```

**Why wrong**: Values can be same without cycle!

**Dry run failure:**
```
List: 1 → 2 → 1 → null (no cycle)

Iteration:
  slow = 1 (first node)
  fast = 1 (third node)
  
  slow.val == fast.val? Yes (both 1)
  Return true ❌
  
But there's no cycle! Just duplicate values!
```

**Fix**: Compare node references
```java
if (slow == fast) {  // Compare references
    return true;
}
```

### ❌ **MISTAKE 6: Not Returning False After Loop**
```java
// WRONG - missing return statement
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    
    if (slow == fast) {
        return true;
    }
}
// Missing: return false;
```

**Why wrong**: Compilation error or undefined behavior!

**Fix**: Return false after loop
```java
while (fast != null && fast.next != null) {
    // ... cycle detection logic
}
return false;  // No cycle found
```

### ❌ **MISTAKE 7: Checking slow.next Instead of fast.next**
```java
// WRONG - checking wrong pointer
while (fast != null && slow.next != null) {  // WRONG!
    slow = slow.next;
    fast = fast.next.next;
    
    if (slow == fast) {
        return true;
    }
}
```

**Why wrong**: We need to check fast.next!

**Issue:**
```
We're doing fast = fast.next.next

This requires:
  1. fast != null
  2. fast.next != null
  
If we check slow.next:
  - Doesn't prevent fast.next.next from failing
  - Wrong pointer checked ❌

Must check fast.next!
```

**Fix**: Check fast.next
```java
while (fast != null && fast.next != null) {
    // Safe to do fast.next.next
}
```

### ❌ **MISTAKE 8: Modifying Node Values to Mark Visited**
```java
// WRONG - destructive, changes data
while (head != null) {
    if (head.val == Integer.MAX_VALUE) {
        return true;  // Already visited
    }
    head.val = Integer.MAX_VALUE;  // Mark as visited
    head = head.next;
}
```

**Why wrong**: Modifies input data!

**Issues:**
```
1. Changes node values (destructive)
2. What if Integer.MAX_VALUE is actual data?
3. Not truly O(1) space (modifies structure)
4. Bad practice in interviews

Don't modify input unless told to!
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| **No cycle case** | O(n) | Fast pointer reaches end |
| **With cycle case** | O(n) | Pointers meet in cycle |
| **Total** | **O(n)** | Linear in list length |

**Time analysis**:
```
Case 1: No cycle
  Fast pointer moves 2 steps per iteration
  Reaches end in n/2 iterations
  Time: O(n)

Case 2: With cycle
  Let k = nodes before cycle
  Let c = cycle length
  
  Phase 1: Reach cycle (k nodes)
    Slow takes k steps
    Fast takes k steps
    Time: O(k)
  
  Phase 2: Meet inside cycle
    Gap between fast and slow ≤ c
    Gap decreases by 1 per iteration
    Meet in at most c steps
    Time: O(c)
  
  Total: O(k + c) ≤ O(n)

Overall: O(n) ✓
```

**Detailed Analysis**:
```
For list with n nodes:

Best case: O(1)
  - Empty list or single node
  - Immediate return

Average case: O(n)
  - Traverse significant portion of list

Worst case: O(n)
  - No cycle: traverse all nodes
  - Large cycle: traverse all nodes plus cycle

Amortized: O(n) across all cases
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| slow pointer | O(1) | Single reference |
| fast pointer | O(1) | Single reference |
| **Total** | **O(1)** | Constant space |

**Space analysis**:
```
Floyd's algorithm:
  Only two pointers (slow, fast)
  No recursion
  No extra data structures
  Space: O(1) ✓

HashSet approach:
  Stores all visited nodes
  Space: O(n) ❌

Floyd's is optimal for space!
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `head = [3,2,0,-4]`, `index = 1`

**Expected Output:** `true`

---

**List Structure:**
```
Index:  0  1  2  3
Value:  3  2  0 -4

  3 → 2 → 0 → -4
      ↑         ↓
      ←---------←

Node at index 3 (-4) points back to index 1 (2)
```

---

**Initial State:**
```
slow = 3 (index 0)
fast = 3 (index 0)

Both pointers start at head
```

---

**Iteration 1:**
```
Before move:
  slow at index 0 (value 3)
  fast at index 0 (value 3)

Move:
  slow = slow.next → index 1 (value 2)
  fast = fast.next.next → index 2 (value 0)

Check:
  slow (index 1) == fast (index 2)? No

After:
  slow at index 1 (value 2)
  fast at index 2 (value 0)
```

---

**Iteration 2:**
```
Before move:
  slow at index 1 (value 2)
  fast at index 2 (value 0)

Move:
  slow = slow.next → index 2 (value 0)
  fast = fast.next.next → index 1 (value 2)
  [fast wrapped: 0 → -4 → 2]

Check:
  slow (index 2) == fast (index 1)? No

After:
  slow at index 2 (value 0)
  fast at index 1 (value 2)
```

---

**Iteration 3:**
```
Before move:
  slow at index 2 (value 0)
  fast at index 1 (value 2)

Move:
  slow = slow.next → index 3 (value -4)
  fast = fast.next.next → index 3 (value -4)
  [fast moved: 2 → 0 → -4]

Check:
  slow (index 3) == fast (index 3)? Yes! ✓

Both pointers at same node
Cycle detected!

Return true
```

---

**Trace Table:**

| Iteration | Slow Position | Fast Position | Meet? |
|-----------|---------------|---------------|-------|
| Initial | index 0 (3) | index 0 (3) | — |
| 1 | index 1 (2) | index 2 (0) | No |
| 2 | index 2 (0) | index 1 (2) | No |
| 3 | index 3 (-4) | index 3 (-4) | **Yes!** |

---

**Visual Movement:**
```
Initial:
  S,F
  ↓
  3 → 2 → 0 → -4
      ↑         ↓
      ←---------←

After Iteration 1:
      S       F
      ↓       ↓
  3 → 2 → 0 → -4
      ↑         ↓
      ←---------←

After Iteration 2:
          S   F
          ↓   ↓
  3 → 2 → 0 → -4
      ↑         ↓
      ←---------←

After Iteration 3:
              S,F
              ↓
  3 → 2 → 0 → -4
      ↑         ↓
      ←---------←

Pointers meet! Cycle detected! ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Recommended |
|----------|------|-------|-------------|
| **Floyd's (Fast & Slow)** | **O(n)** | **O(1)** | **Yes ✅** |
| HashSet | O(n) | O(n) | No (extra space) |
| Modify nodes | O(n) | O(1) | No (destructive) |
| Reverse list | O(n) | O(1) | No (complex, destructive) |

**Winner**: **Floyd's Cycle Detection** — optimal time and space!

---

## Key Takeaways

1. **Floyd's algorithm** — fast and slow pointers
2. **Different speeds** — fast moves 2, slow moves 1
3. **Meet in cycle** — guaranteed if cycle exists
4. **Check fast.next** — prevent NullPointerException
5. **Move then check** — avoid false positive
6. **O(n) time, O(1) space** — optimal solution
7. **Compare references** — not values
8. **Both start at head** — simplest approach
9. **Loop until fast reaches null** — no cycle
10. **Classic algorithm** — used in many problems

---

## Interview Tips

**What to say in an interview:**

> "To detect a cycle in a linked list with O(1) space, I'll use Floyd's Cycle Detection Algorithm, also known as the tortoise and hare algorithm. I'll use two pointers: slow moves one step at a time, and fast moves two steps at a time. If there's a cycle, the fast pointer will eventually catch up to the slow pointer inside the cycle because it's moving faster and the gap between them decreases by one each iteration. If there's no cycle, the fast pointer will reach the end (null) first. The loop condition checks both fast and fast.next to safely perform fast.next.next. I move the pointers first, then check if they're equal to avoid a false positive on the first iteration when both start at head. This solution runs in O(n) time since the fast pointer traverses at most 2n nodes, and uses O(1) space since we only need two pointers."

**Key points to mention:**
1. **Floyd's algorithm** — standard cycle detection
2. **Two pointers** — slow (1 step) and fast (2 steps)
3. **Meet in cycle** — fast catches slow
4. **Check fast.next** — avoid NullPointerException
5. **Move before check** — avoid false positive
6. **O(n) time** — traverse list once
7. **O(1) space** — only two pointers
8. **Compare references** — not values

**Common Follow-ups:**
- "Where does the cycle start?" → Use Floyd's to find starting node (requires extra phase)
- "What's the length of the cycle?" → Continue moving one pointer after meeting
- "Can you use recursion?" → Possible but not recommended (O(n) space)
- "Why not use a HashSet?" → Works but uses O(n) space, not optimal
- "What if there are multiple entry points?" → Not possible in singly linked list

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Linked List Cycle** | Easy | **Floyd's Algorithm** | **This problem** |
| Linked List Cycle II | Medium | Floyd's + Find Start | Find cycle starting node |
| Happy Number | Easy | Floyd's on Numbers | Detect cycle in number sequence |
| Find Duplicate Number | Medium | Floyd's on Array | Cycle in array treated as list |
| Intersection of Two Lists | Easy | Two Pointers | Find meeting point |
| Remove Nth Node From End | Medium | Fast & Slow | Fast moves n ahead |

**Pattern Progression**:
1. **Linked List Cycle** (this) — Detect cycle (boolean)
2. **Linked List Cycle II** — Find where cycle starts (node)
3. **Happy Number** — Apply Floyd's to math problem
4. **Find Duplicate** — Apply Floyd's to array

---

## Final Pattern Label

✅ **Floyd's Cycle Detection (Tortoise & Hare / Fast & Slow Pointers)**

**Remember:** This is **Floyd's Cycle Detection Algorithm**, a classic two-pointer technique. Initialize **slow** and **fast** both to head. In each iteration, move **slow by 1 step** and **fast by 2 steps**. If they **meet** (slow == fast), a cycle exists. If **fast reaches null**, there's no cycle. The loop condition must check **both fast != null AND fast.next != null** to safely execute fast.next.next without NullPointerException. **Move pointers first, then check** to avoid false positive (both start at head). The algorithm works because inside a cycle, the gap between fast and slow decreases by 1 each iteration, guaranteeing they eventually meet. Achieves **O(n) time** (traverse list once) and **O(1) space** (only two pointers), making it optimal. Compare **node references**, not values. This is preferred over HashSet approach which uses O(n) space!

