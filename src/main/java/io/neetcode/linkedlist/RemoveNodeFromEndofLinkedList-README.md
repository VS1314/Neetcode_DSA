# Remove Node From End of Linked List

**Difficulty:** Medium  
**Pattern:** Two Pointers (Fast & Slow with Fixed Gap)  
**Recommended Time & Space Complexity:**  
- Time: O(n)  
- Space: O(1)  

---

## 1. Problem Understanding

Given the head of a linked list and an integer n, remove the nth node from the end of the list and return the new head.

- **Example 1:**  
  Input: head = [1,2,3,4], n = 2  
  Output: [1,2,4]  
  (Remove node with value 3, which is 2nd from the end)

- **Example 2:**  
  Input: head = [5], n = 1  
  Output: []  
  (Remove the only node)

- **Example 3:**  
  Input: head = [1,2], n = 2  
  Output: [2]  
  (Remove the head node)

- **Key Constraint:** 1 ≤ n ≤ size of list (n is always valid)

---

## 2. Pattern to Use

This is a **Two Pointer with Fixed Gap** problem, also known as the **Sliding Window on Linked List** pattern.

The key insight:
- Maintain two pointers with a gap of n nodes between them
- When the front pointer reaches the end, the back pointer is at the node before the one to delete

---

## 3. Algorithm & Approach

### Brute Force (Not optimal)
- Store all nodes in an array
- Remove the nth node from the end of the array
- Rebuild the linked list from the array
- **Drawback:** Uses O(n) extra space

### Two-Pass Solution (Better)
1. First pass: Count total nodes (length = L)
2. Second pass: Traverse to the (L - n)th node
3. Remove the next node
- **Drawback:** Requires two traversals

### Optimal: One-Pass Two-Pointer Solution

**High-Level Strategy:**
- Use two pointers: `fast` and `slow`
- Move `fast` pointer n steps ahead
- Move both pointers together until `fast` reaches the end
- `slow` will be at the node **before** the one to delete
- Remove `slow.next`

**Special Case:**
- Use a **dummy node** to handle edge cases (like removing the head)

---

## 4. Why This Strategy?

### Why maintain a gap of n nodes?

**Visual Understanding:**
```
List: 1 → 2 → 3 → 4 → 5, n = 2
Goal: Remove 4 (2nd from end)

Step 1: Move fast pointer n=2 steps ahead
        1 → 2 → 3 → 4 → 5
        s       f

Step 2: Move both until fast reaches end
        1 → 2 → 3 → 4 → 5 → null
                s       f

Now slow is at node 3 (before the target node 4)
Remove slow.next: 1 → 2 → 3 → 5
```

**Why this works:**
- If `fast` is n nodes ahead of `slow`
- When `fast` reaches the end (null), `slow` is n nodes from the end
- But we need to be **before** the node to delete, so we adjust the positioning

**Advantages:**
- **Single pass:** Only one traversal through the list
- **O(1) space:** No extra data structures
- **Handles edge cases:** Dummy node simplifies head removal

---

## 5. Pseudocode

```pseudo
function removeNthFromEnd(head, n):
    // Create dummy node to handle edge cases
    dummy = new Node(0)
    dummy.next = head
    
    // Initialize two pointers
    fast = dummy
    slow = dummy
    
    // Move fast pointer n+1 steps ahead
    for i = 0 to n:
        fast = fast.next
    
    // Move both pointers until fast reaches end
    while fast != null:
        fast = fast.next
        slow = slow.next
    
    // Remove the nth node
    slow.next = slow.next.next
    
    return dummy.next
```

---

## 6. Example Walkthrough

### Example: head = [1,2,3,4,5], n = 2

**Initial Setup with Dummy Node:**
```
dummy → 1 → 2 → 3 → 4 → 5 → null
```

**Step 1: Move fast n+1=3 steps ahead**
```
dummy → 1 → 2 → 3 → 4 → 5 → null
slow             fast
```

**Step 2: Move both pointers until fast reaches null**
```
Iteration 1:
dummy → 1 → 2 → 3 → 4 → 5 → null
        slow         fast

Iteration 2:
dummy → 1 → 2 → 3 → 4 → 5 → null
             slow         fast

Iteration 3:
dummy → 1 → 2 → 3 → 4 → 5 → null
                  slow         fast=null
```

**Step 3: Remove slow.next (node 4)**
```
Before: 1 → 2 → 3 → 4 → 5
                  slow

After:  1 → 2 → 3 → 5
                  slow
```

**Result:** [1,2,3,5] ✓

---

## 7. Code (Java)

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    // Create dummy node to handle edge cases
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    // Initialize two pointers
    ListNode fast = dummy;
    ListNode slow = dummy;
    
    // Move fast pointer n+1 steps ahead
    // We do n+1 so that slow stops at the node BEFORE the one to delete
    for (int i = 0; i <= n; i++) {
        fast = fast.next;
    }
    
    // Move both pointers until fast reaches the end
    while (fast != null) {
        fast = fast.next;
        slow = slow.next;
    }
    
    // Remove the nth node from the end
    slow.next = slow.next.next;
    
    // Return the new head (dummy.next handles case where head was removed)
    return dummy.next;
}
```

### Alternative Implementation (without moving n+1 steps):

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    ListNode fast = dummy;
    ListNode slow = dummy;
    
    // Move fast pointer n steps ahead
    for (int i = 0; i < n; i++) {
        fast = fast.next;
    }
    
    // Move both pointers until fast.next is null
    while (fast.next != null) {
        fast = fast.next;
        slow = slow.next;
    }
    
    // Remove the nth node
    slow.next = slow.next.next;
    
    return dummy.next;
}
```

---

## 8. Key Points

### Why use a dummy node?
- **Simplifies edge cases:** Removing the head becomes a regular operation
- **Uniform handling:** No special logic needed for different positions
- **Prevents null checks:** Always have a node before the head

### Why move fast n+1 steps (or use fast.next in condition)?
- We need `slow` to stop at the node **before** the one to delete
- To delete a node, we need access to its previous node
- Gap of n+1 ensures this positioning

### Edge Cases:
- **Remove head:** [1,2], n=2 → [2]
- **Single node:** [1], n=1 → []
- **Remove tail:** [1,2,3], n=1 → [1,2]
- **Two nodes, remove first:** [1,2], n=2 → [2]
- **Two nodes, remove second:** [1,2], n=1 → [1]

### Important Observations:
- The dummy node's value doesn't matter (often set to 0 or -1)
- After deletion, return `dummy.next`, not `head` (head might have been deleted)
- The gap between pointers is crucial for correct positioning

---

## 9. Time & Space Complexity Analysis

### Time Complexity: O(n)
- Move fast pointer n steps: O(n)
- Move both pointers to end: O(L-n) where L is list length
- Total: O(n) + O(L-n) = O(L) = O(n)
- Single pass through the list

### Space Complexity: O(1)
- Only three pointers used: dummy, fast, slow
- No additional data structures
- In-place modification

---

## 10. Step-by-Step Dry Run

### Input: [1,2,3,4], n = 2

**Setup:**
```
dummy → 1 → 2 → 3 → 4 → null
f,s
```

**Move fast n+1=3 steps:**
```
dummy → 1 → 2 → 3 → 4 → null
s                    f
```

**Move both until fast is null:**
```
Iteration 1:
dummy → 1 → 2 → 3 → 4 → null
        s                f=null
```

**Remove slow.next (node 3):**
```
Before: 1 → 2 → 3 → 4
        s

After:  1 → 2 → 4
        s
```

**Return dummy.next:**
```
Final: [1,2,4] ✓
```

---

## 11. Visual Comparison: Two-Pass vs One-Pass

### Two-Pass Approach:
```
Pass 1: Count length = 4
Pass 2: Go to position (4-2) = 2, remove next node
Total: 2 traversals
```

### One-Pass Approach (Optimal):
```
Single Pass: Maintain gap of n, remove when fast reaches end
Total: 1 traversal
```

---

## 12. Common Mistakes to Avoid

1. **Not using dummy node:** Makes removing head complicated
2. **Wrong gap calculation:** Moving fast exactly n steps vs n+1 steps
3. **Forgetting to return dummy.next:** Returning `head` fails if head was removed
4. **Null pointer exception:** Not handling single-node list
5. **Off-by-one errors:** Incorrect loop conditions for moving fast pointer
6. **Wrong condition in while loop:** Using `fast != null` vs `fast.next != null` depends on initial gap

---

## 13. Why Dummy Node is Crucial

**Without Dummy Node (Complex):**
```java
// Need special handling for removing head
if (/* calculate if removing head */) {
    return head.next;
}
// Different logic for other nodes
ListNode prev = head;
// ... complex logic
```

**With Dummy Node (Simple):**
```java
// Uniform logic for all cases
dummy.next = head;
// ... same logic for all positions
return dummy.next;  // Works for all cases!
```

---

## 14. Related Patterns

This problem uses the **"Two Pointers with Fixed Gap"** pattern, which is also useful for:
- Finding the kth node from the end
- Detecting if a list has a specific structure
- Splitting a list at a certain position from the end

---

This is a fundamental linked list problem that teaches you the power of the two-pointer technique with a fixed gap. The dummy node trick is essential for simplifying edge cases in linked list problems!

