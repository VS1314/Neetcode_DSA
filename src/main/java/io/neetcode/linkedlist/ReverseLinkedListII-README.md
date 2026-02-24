# Reverse Linked List II

## Problem Statement

**Difficulty:** Medium

You are given the head of a singly linked list and two integers `left` and `right` where `left <= right`. Reverse the nodes of the list from position `left` to position `right` (1-indexed), and return the reversed list.

**Follow up:** Could you do it in one pass?

### Examples

**Example 1:**
```
Input: head = [1,2,3,4,5], left = 1, right = 3
Output: [3,2,1,4,5]

Visual:
Before: 1 -> 2 -> 3 -> 4 -> 5
After:  3 -> 2 -> 1 -> 4 -> 5
        └─────┘
        reversed
```

**Example 2:**
```
Input: head = [1,1], left = 1, right = 1
Output: [1,1]

Explanation: Reversing a single node results in the same list
```

### Constraints
- The number of nodes in the list is `n`
- 1 <= n <= 500
- -500 <= Node.val <= 500
- 1 <= left <= right <= n

---

## Pattern Identification

**Primary Pattern:** In-place Linked List Reversal with Position Tracking  
**Secondary Pattern:** Dummy Node for Edge Cases

### Why this pattern?

- We need to reverse only a **portion** of a linked list, not the entire list
- We must keep track of connections **before and after** the reversed section
- The reversal happens **in-place** (O(1) space)
- Similar to "Reverse Linked List" but with **precise position control**

### Pattern Recognition Clues

- Reversing a sublist in a linked list
- Position-based operations (left, right indices)
- Need to maintain connections to non-reversed parts
- In-place modification required

---

## Problem Breakdown

### Key Observations

1. **Three Sections:** The list has three parts:
   - Before reversal: nodes [1, left-1]
   - Reversal section: nodes [left, right]
   - After reversal: nodes [right+1, n]

2. **Critical Connections:**
   - Node at position (left-1) → should point to node at position right (after reversal)
   - Node at position left → should point to node at position (right+1) (after reversal)

3. **Edge Cases:**
   - left = 1 (reversing from head)
   - left = right (single node, no reversal needed)
   - left = 1, right = n (entire list reversal)

### Visual Breakdown

```
Original: 1 -> 2 -> 3 -> 4 -> 5
          ↑         ↑    ↑
         left-1   left  right

Step 1: Identify sections
  [1] -> [2 -> 3 -> 4] -> [5]
  prev   ← reverse →     after

Step 2: Reverse middle section
  [1] -> [4 -> 3 -> 2] -> [5]

Step 3: Reconnect
  1 -> 4 -> 3 -> 2 -> 5
```

---

## Approach & Strategy

### Core Strategy

1. **Use Dummy Node:** Simplifies edge cases (especially when left = 1)
2. **Find Position (left-1):** Track the node before the reversal starts
3. **Reverse Sublist:** Reverse only nodes from left to right
4. **Reconnect:** Fix pointers to maintain list continuity

### Why Dummy Node?

Without dummy node:
```java
if (left == 1) {
    // Special handling needed
    head = newHead;
}
```

With dummy node:
```java
// No special cases!
dummy.next always points to the head
```

---

## Solution Approaches

### Approach 1: Iterative with Dummy Node ✅ (OPTIMAL - One Pass)

**Time:** O(n) | **Space:** O(1)

This is the optimal solution that completes in one pass!

---

### Approach 2: Recursive Approach

**Time:** O(n) | **Space:** O(n) due to recursion stack

Less optimal due to space complexity.

---

### Approach 3: Extract, Reverse, Reinsert ❌ (Multiple Passes)

**Time:** O(n) | **Space:** O(1)

```
1. Extract sublist [left, right]
2. Reverse the extracted sublist
3. Reinsert back into original list
```

**Why not optimal?** Requires multiple passes, more complex

---

## Algorithm Explanation

### Optimal Solution: One-Pass Iterative Reversal

#### Step-by-Step Process

**Setup Phase:**
```java
1. Create dummy node pointing to head
2. Move to position (left - 1) → This is "prev"
3. "curr" points to position left (start of reversal)
```

**Reversal Phase:**
```java
For (right - left) iterations:
    1. Extract the next node after curr
    2. Move it to the front of the reversed section
    3. Adjust pointers
```

#### Detailed Visualization

**Example:** `head = [1,2,3,4,5], left = 2, right = 4`

```
Initial Setup:
  dummy -> 1 -> 2 -> 3 -> 4 -> 5
           ↑    ↑
         prev  curr

Iteration 1: Move 3 to front
  dummy -> 1 -> 3 -> 2 -> 4 -> 5
           ↑         ↑
         prev      curr

Iteration 2: Move 4 to front
  dummy -> 1 -> 4 -> 3 -> 2 -> 5
           ↑              ↑
         prev           curr

Result: [1,4,3,2,5] ✅
```

#### The Reversal Technique

```java
// This is the core logic that reverses the nodes
ListNode next = curr.next;     // Save the node to move
curr.next = next.next;         // Skip over it
next.next = prev.next;         // Insert at front of reversed section
prev.next = next;              // Update prev's next
```

**Visual of Single Iteration:**
```
Before:
  prev -> curr -> next -> ...
  
After:
  prev -> next -> curr -> ...
```

### Why Does This Work?

**Key Insight:** Instead of reversing by changing all pointers, we **repeatedly move the next node to the front** of the reversed section.

**Example with [2,3,4]:**
```
Start:     2 -> 3 -> 4
Move 3:    3 -> 2 -> 4  (moved 3 to front)
Move 4:    4 -> 3 -> 2  (moved 4 to front)
Result:    4 -> 3 -> 2  ✅ Reversed!
```

This technique is more efficient than traditional reversal because:
- We don't need to traverse to find the end
- We maintain connection with the rest of the list
- Only need to track 2 pointers (prev and curr)

---

## Code Implementation

### Solution 1: One-Pass Iterative (RECOMMENDED)

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
        // Edge case: empty list or single node
        if (head == null || left == right) {
            return head;
        }
        
        // Step 1: Create dummy node to handle edge cases
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // Step 2: Move to the node before 'left' position
        ListNode prev = dummy;
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }
        
        // Step 3: 'curr' points to the 'left' position
        ListNode curr = prev.next;
        
        // Step 4: Reverse the sublist by moving nodes
        for (int i = 0; i < right - left; i++) {
            ListNode next = curr.next;      // Node to move to front
            curr.next = next.next;          // Skip over 'next'
            next.next = prev.next;          // Insert 'next' at front
            prev.next = next;               // Update prev's next pointer
        }
        
        // Step 5: Return the new head (dummy.next)
        return dummy.next;
    }
}
```

**Time Complexity:** O(n) - Single pass through the list  
**Space Complexity:** O(1) - Only using a few pointers

---

### Solution 2: Traditional Reversal Approach

```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right) return head;
        
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        
        // Move to position before left
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }
        
        // Start of reversal section
        ListNode start = prev.next;
        ListNode then = start.next;
        
        // Standard reversal technique
        for (int i = 0; i < right - left; i++) {
            start.next = then.next;
            then.next = prev.next;
            prev.next = then;
            then = start.next;
        }
        
        return dummy.next;
    }
}
```

---

### Solution 3: Recursive Approach (Less Optimal)

```java
class Solution {
    private ListNode successor = null;  // Node after right position
    
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == 1) {
            return reverseN(head, right);
        }
        head.next = reverseBetween(head.next, left - 1, right - 1);
        return head;
    }
    
    // Reverse first N nodes
    private ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            successor = head.next;
            return head;
        }
        ListNode newHead = reverseN(head.next, n - 1);
        head.next.next = head;
        head.next = successor;
        return newHead;
    }
}
```

**Space Complexity:** O(n) due to recursion stack

---

## Complexity Analysis

### One-Pass Iterative Solution

**Time Complexity: O(n)**
- Find position (left-1): O(left)
- Reverse sublist: O(right - left)
- **Total: O(n)** in worst case

**Space Complexity: O(1)**
- Only using fixed number of pointers
- No additional data structures

**Best/Worst Case:**
- Best case: left = right → O(left) time
- Worst case: left = 1, right = n → O(n) time

---

## Dry Run (Step-by-Step)

**Input:** `head = [1,2,3,4,5], left = 2, right = 4`

### Initial Setup

```
dummy -> 1 -> 2 -> 3 -> 4 -> 5
         ↑    
       prev (after moving left-1 times)
              ↑
            curr
```

### Iteration-by-Iteration

**Iteration 1:** (i = 0, need to do right - left = 2 iterations)

```
Before:
  dummy -> 1 -> 2 -> 3 -> 4 -> 5
           ↑    ↑    ↑
         prev curr next

Operations:
  next = curr.next         // next = 3
  curr.next = next.next    // 2.next = 4
  next.next = prev.next    // 3.next = 2
  prev.next = next         // 1.next = 3

After:
  dummy -> 1 -> 3 -> 2 -> 4 -> 5
           ↑         ↑
         prev      curr
```

**Iteration 2:** (i = 1)

```
Before:
  dummy -> 1 -> 3 -> 2 -> 4 -> 5
           ↑         ↑    ↑
         prev      curr next

Operations:
  next = curr.next         // next = 4
  curr.next = next.next    // 2.next = 5
  next.next = prev.next    // 4.next = 3
  prev.next = next         // 1.next = 4

After:
  dummy -> 1 -> 4 -> 3 -> 2 -> 5
           ↑              ↑
         prev           curr
```

**Final Result:** `[1,4,3,2,5]` ✅

---

## Edge Cases

### 1. Reverse from Head (left = 1)

**Input:** `head = [1,2,3,4,5], left = 1, right = 3`  
**Output:** `[3,2,1,4,5]`

```
dummy -> 1 -> 2 -> 3 -> 4 -> 5
↑        ↑
prev    curr

After reversal:
dummy -> 3 -> 2 -> 1 -> 4 -> 5
```

✅ Dummy node handles this automatically!

---

### 2. Single Node Reversal (left = right)

**Input:** `head = [1,2,3], left = 2, right = 2`  
**Output:** `[1,2,3]`

```
Loop runs 0 times (right - left = 0)
No changes made ✅
```

---

### 3. Entire List Reversal

**Input:** `head = [1,2,3,4,5], left = 1, right = 5`  
**Output:** `[5,4,3,2,1]`

```
Reverses the complete list ✅
```

---

### 4. Two-Node List

**Input:** `head = [1,2], left = 1, right = 2`  
**Output:** `[2,1]`

```
dummy -> 1 -> 2
↑        ↑
prev    curr

After:
dummy -> 2 -> 1 ✅
```

---

### 5. Single Node List

**Input:** `head = [1], left = 1, right = 1`  
**Output:** `[1]`

```
Early return: left == right ✅
```

---

## Common Mistakes

### ❌ Mistake 1: Not Using Dummy Node

```java
// WRONG - Special cases needed
ListNode prev = null;
if (left == 1) {
    // Complex head handling
    return reversedHead;
}
```

**Fix:** Always use dummy node for cleaner code

---

### ❌ Mistake 2: Off-by-One Errors in Loop

```java
// WRONG
for (int i = 1; i <= left; i++) {  // Should be < left
    prev = prev.next;
}
```

**Fix:** Move `left - 1` times to reach position before left

---

### ❌ Mistake 3: Wrong Number of Reversals

```java
// WRONG
for (int i = left; i <= right; i++) {  // Wrong iteration count
```

**Fix:** Iterate exactly `right - left` times

---

### ❌ Mistake 4: Losing References

```java
// WRONG - Lost reference to curr
ListNode next = curr.next;
curr = next;  // DON'T move curr!
```

**Fix:** Keep `curr` fixed; only move nodes around it

---

### ❌ Mistake 5: Forgetting Edge Case Check

```java
// WRONG - No check for left == right
public ListNode reverseBetween(ListNode head, int left, int right) {
    // Always processes even when no reversal needed
}
```

**Fix:** Add early return for `left == right`

---

## Why This Strategy?

### Advantages of One-Pass Approach

1. ✅ **Optimal Time:** O(n) - single pass
2. ✅ **Optimal Space:** O(1) - no extra structures
3. ✅ **Clean Code:** Dummy node eliminates edge cases
4. ✅ **In-Place:** No extra nodes created
5. ✅ **Meets Follow-up:** One pass requirement

### Key Insights

**Why move nodes to front instead of traditional reversal?**
- Traditional reversal needs to track 3 pointers and reverse direction
- Moving to front only needs 2 pointers (prev, curr)
- Maintains connection with rest of list automatically
- Simpler pointer manipulation

**Why dummy node?**
- Handles left = 1 without special code
- Provides a stable reference point
- Simplifies return statement (always dummy.next)

---

## Visualization of Pointer Movement

### Traditional Reversal (More Complex)

```
Initial: A -> B -> C -> D

Step 1: A <- B    C -> D  (reverse A-B)
Step 2: A <- B <- C    D  (reverse B-C)
Step 3: A <- B <- C <- D  (reverse C-D)

Need to track: prev, curr, next
Need to reconnect after reversal
```

### Move-to-Front Technique (Simpler)

```
Initial: prev -> A -> B -> C -> D

Step 1: prev -> B -> A -> C -> D  (move B to front)
Step 2: prev -> C -> B -> A -> D  (move C to front)
Step 3: prev -> D -> C -> B -> A  (move D to front)

Only track: prev (fixed), curr (fixed)
Auto-maintains connections!
```

---

## Interview Tips

### What to Say in Interview

1. **Clarify:** "Should I reverse in-place or is creating new nodes okay?"
2. **Edge Cases:** "Let me consider: left = 1, left = right, entire list reversal"
3. **Approach:** "I'll use a dummy node to simplify edge cases and reverse in one pass"
4. **Complexity:** "This will be O(n) time and O(1) space"

### Expected Follow-up Questions

**Q:** "Can you do it without a dummy node?"  
**A:** "Yes, but we'd need special handling when left = 1. Dummy node is cleaner."

**Q:** "What if left or right are invalid?"  
**A:** "The constraints guarantee 1 <= left <= right <= n, but I could add validation."

**Q:** "Can you do it recursively?"  
**A:** "Yes, but it would use O(n) space for the call stack. Iterative is better."

**Q:** "How would you reverse k-sized groups?"  
**A:** "That's 'Reverse Nodes in k-Group' - similar approach but iterate in groups."

---

## Related Problems

- **Reverse Linked List** - Easier version (reverse entire list)
- **Reverse Nodes in k-Group** - Harder version (reverse in groups)
- **Swap Nodes in Pairs** - Similar pointer manipulation
- **Rotate List** - Similar list manipulation concept

---

## Pattern Recognition

**When you see:**
- "Reverse a portion of linked list"
- "Reverse from position X to Y"
- "Reverse between indices"

**Think:**
- Dummy node for edge cases
- Move-to-front reversal technique
- Track position before reversal starts
- O(n) time, O(1) space solution exists

---

## Summary

- **Pattern:** In-place Linked List Reversal with Position Tracking
- **Technique:** Move-to-front reversal (not traditional reversal)
- **Time:** O(n) - One pass
- **Space:** O(1) - Only pointers
- **Key Tools:** Dummy node, two pointers (prev, curr)
- **Critical Insight:** Keep curr fixed, move subsequent nodes to front
- **Edge Cases:** left = 1, left = right, entire list
- **Interview Gold:** Demonstrates mastery of pointer manipulation

