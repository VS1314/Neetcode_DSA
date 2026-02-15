# Reorder Linked List

**Difficulty:** Medium  
**Pattern:** Two Pointers (Fast & Slow), Linked List Reversal, Linked List Merging  
**Recommended Time & Space Complexity:**  
- Time: O(n)  
- Space: O(1)  

---

## 1. Problem Understanding

Given the head of a singly linked list, reorder it so that nodes alternate between the start and end of the list.

- **Pattern:** For a list of length n: [0, 1, 2, 3, 4, 5, 6]  
  Reorder to: [0, 6, 1, 5, 2, 4, 3]  
  General pattern: [0, n-1, 1, n-2, 2, n-3, ...]

- **Example 1:**  
  Input: [2,4,6,8]  
  Output: [2,8,4,6]

- **Example 2:**  
  Input: [2,4,6,8,10]  
  Output: [2,10,4,8,6]

- **Key Constraint:** You cannot modify node values, only reorder the nodes themselves.

---

## 2. Pattern to Use

This is a **Three-Step Linked List** problem combining multiple patterns:
1. **Find Middle** - Use Fast & Slow pointers (Floyd's Algorithm)
2. **Reverse List** - Reverse the second half
3. **Merge Lists** - Interleave two lists

---

## 3. Algorithm & Approach

### Brute Force (Not optimal)
- Store all node values in an array
- Reorder the array values according to the pattern
- Build a new linked list from the reordered array
- **Drawback:** Uses O(n) extra space

### Optimal Approach (Three-Step In-Place Solution)

**High-Level Strategy:**
1. Find the middle of the list (split into two halves)
2. Reverse the second half
3. Merge the first half with the reversed second half

**Detailed Steps:**

**Step 1: Find the Middle**
- Use slow and fast pointers
- Slow moves 1 step, fast moves 2 steps
- When fast reaches the end, slow is at the middle
- Split the list into two halves: L1 and L2

**Step 2: Reverse Second Half**
- Reverse L2 using standard reversal technique
- Use prev, current, and next pointers

**Step 3: Merge Two Lists**
- Take one node from L1, then one from L2
- Repeat until all nodes are merged
- The first half determines the length (if odd length, first half has one extra node)

---

## 4. Why This Strategy?

### Why split and reverse?

**Visual Understanding:**
```
Original: 1 → 2 → 3 → 4 → 5
Goal:     1 → 5 → 2 → 4 → 3

Split:    L1: 1 → 2 → 3
          L2: 4 → 5

Reverse:  L1: 1 → 2 → 3
          L2: 5 → 4

Merge:    1 → 5 → 2 → 4 → 3 ✓
```

**Why this works:**
- The reordering pattern connects node[i] with node[n-1-i]
- By reversing the second half, the last node becomes first in L2
- Merging alternately gives us the desired pattern

**Advantages:**
- **In-place:** O(1) space complexity
- **Single pass:** Each step is O(n), total is still O(n)
- **No extra structures:** Works with existing nodes

---

## 5. Pseudocode

```pseudo
function reorderList(head):
    if head is null or head.next is null:
        return
    
    // Step 1: Find middle
    slow = head
    fast = head
    while fast.next != null and fast.next.next != null:
        slow = slow.next
        fast = fast.next.next
    
    // Split the list
    second = slow.next
    slow.next = null
    
    // Step 2: Reverse second half
    prev = null
    current = second
    while current != null:
        next = current.next
        current.next = prev
        prev = current
        current = next
    second = prev
    
    // Step 3: Merge two halves
    first = head
    while second != null:
        temp1 = first.next
        temp2 = second.next
        
        first.next = second
        second.next = temp1
        
        first = temp1
        second = temp2
```

---

## 6. Example Walkthrough

### Example: [1, 2, 3, 4, 5]

**Step 1: Find Middle**
```
Initial: 1 → 2 → 3 → 4 → 5
         s           f

After:   1 → 2 → 3 → 4 → 5
              s           f

Split:   L1: 1 → 2 → 3
         L2: 4 → 5
```

**Step 2: Reverse Second Half**
```
Before:  L2: 4 → 5 → null
After:   L2: 5 → 4 → null
```

**Step 3: Merge**
```
L1: 1 → 2 → 3
L2: 5 → 4

Iteration 1: Connect 1 → 5
             1 → 5    2 → 3
                      4

Iteration 2: Connect 5 → 2
             1 → 5 → 2    3
                          4

Iteration 3: Connect 2 → 4
             1 → 5 → 2 → 4    3

Iteration 4: Connect 4 → 3
             1 → 5 → 2 → 4 → 3

Result: [1, 5, 2, 4, 3] ✓
```

---

## 7. Code (Java)

```java
public void reorderList(ListNode head) {
    if (head == null || head.next == null) {
        return;
    }
    
    // Step 1: Find the middle of the list
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    
    // Split the list into two halves
    ListNode second = slow.next;
    slow.next = null;  // End first half
    
    // Step 2: Reverse the second half
    ListNode prev = null;
    ListNode current = second;
    
    while (current != null) {
        ListNode nextTemp = current.next;
        current.next = prev;
        prev = current;
        current = nextTemp;
    }
    second = prev;
    
    // Step 3: Merge the two halves
    ListNode first = head;
    
    while (second != null) {
        ListNode temp1 = first.next;
        ListNode temp2 = second.next;
        
        first.next = second;
        second.next = temp1;
        
        first = temp1;
        second = temp2;
    }
}
```

---

## 8. Key Points

### Why `fast.next != null && fast.next.next != null`?
- For even length: Ensures we split exactly in the middle
- For odd length: First half gets one extra node (which is fine)

### Edge Cases:
- **Single node:** [1] → No reordering needed
- **Two nodes:** [1,2] → No reordering needed (already correct)
- **Three nodes:** [1,2,3] → [1,3,2]
- **Even length:** [1,2,3,4] → [1,4,2,3]
- **Odd length:** [1,2,3,4,5] → [1,5,2,4,3]

### Important Observations:
- After finding middle, we need to **break the connection** (set `slow.next = null`)
- The merging always ends when `second` becomes null (first half is equal or longer)
- We must save `next` pointers before modifying links
- The original head remains the head of the reordered list

### Common Pitfalls:
- Forgetting to split the list (setting `slow.next = null`)
- Not saving next pointers during merge
- Wrong middle-finding condition

---

## 9. Time & Space Complexity Analysis

### Time Complexity: O(n)
- **Find middle:** O(n/2) ≈ O(n)
- **Reverse second half:** O(n/2) ≈ O(n)
- **Merge:** O(n/2) ≈ O(n)
- **Total:** O(n) + O(n) + O(n) = O(n)

### Space Complexity: O(1)
- Only a constant number of pointers used
- No recursion or additional data structures
- All operations are in-place

---

## 10. Step-by-Step Dry Run

### Input: [2, 4, 6, 8]

**Initial State:**
```
2 → 4 → 6 → 8 → null
```

**Step 1: Find Middle**
```
Iteration 1: slow=2, fast=2
             slow=4, fast=6

Iteration 2: slow=4, fast=8
             slow=6, fast=null (stop)

Split at slow=4:
L1: 2 → 4 → null
L2: 6 → 8 → null
```

**Step 2: Reverse L2**
```
Before: 6 → 8 → null
After:  8 → 6 → null
```

**Step 3: Merge**
```
L1: 2 → 4 → null
L2: 8 → 6 → null

Step 1: 2.next = 8
        8.next = 4
        Result: 2 → 8 → 4

Step 2: 4.next = 6
        6.next = null
        Result: 2 → 8 → 4 → 6 → null
```

**Final Output:** [2, 8, 4, 6] ✓

---

## 11. Related Problems

This problem combines three fundamental linked list techniques:
1. **Finding middle:** Similar to "Middle of Linked List"
2. **Reversing list:** Similar to "Reverse Linked List"
3. **Merging lists:** Similar to "Merge Two Sorted Lists"

Master these three patterns, and you can solve many linked list problems!

---

## 12. Common Mistakes to Avoid

1. **Not breaking the link:** Forgetting `slow.next = null` causes infinite loops
2. **Wrong middle condition:** Using `fast != null` instead of `fast.next != null`
3. **Losing references:** Not saving next pointers before modifying
4. **Wrong merge logic:** Trying to merge both directions at once
5. **Null pointer exceptions:** Not checking for empty or single-node lists

---

This is a classic medium-level linked list problem that tests your understanding of multiple linked list manipulation techniques. Practice each step separately before combining them!

