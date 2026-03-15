# Reverse Nodes in K-Group

**Difficulty:** Hard  
**Pattern:** Linked List, In-place Reversal, Pointer Manipulation  
**Recommended Time & Space Complexity:**  
- Time: O(n)  
- Space: O(1)  

---

## 1. Problem Understanding

Given the head of a singly linked list and a positive integer k, reverse the nodes in groups of k. If there are fewer than k nodes remaining at the end, leave them as they are.

- **Example 1:**  
  Input: head = [1,2,3,4,5,6], k = 3  
  Output: [3,2,1,6,5,4]  
  Explanation: Reverse first 3 nodes [1,2,3] → [3,2,1], then next 3 nodes [4,5,6] → [6,5,4]

- **Example 2:**  
  Input: head = [1,2,3,4,5], k = 3  
  Output: [3,2,1,4,5]  
  Explanation: Reverse first 3 nodes [1,2,3] → [3,2,1], leave remaining [4,5] as is (less than k nodes)

- **Constraints:**
  - 1 <= k <= n <= 100
  - 0 <= Node.val <= 100
  - Can only modify `next` pointers, not node values

---

## 2. Pattern to Use

This is a **Linked List In-place Reversal** problem.  
The key patterns involved are:
1. **Dummy Node:** To handle head modifications easily
2. **Group Reversal:** Reverse k nodes at a time
3. **Pointer Management:** Track previous group's tail, current group's head/tail, and next group's head

---

## 3. Algorithm & Approach

### Brute Force (Not optimal)
- Store all node values in an array.
- Reverse k elements at a time in the array.
- Build a new linked list from the modified array.
- **Drawback:** Uses O(n) extra space.

### Optimal Approach (In-place Group Reversal)
- Use a dummy node to handle edge cases.
- For each group of k nodes:
  1. Check if k nodes are available
  2. Reverse the k nodes in-place
  3. Connect the reversed group with the previous group and next group
  4. Move to the next group

**Step-by-step:**
1. Create a dummy node pointing to head.
2. Use `groupPrev` pointer to track the tail of the previous group.
3. For each iteration:
   - Check if k nodes exist ahead using `getKth()` helper
   - If yes, reverse the k nodes using `reverseGroup()` helper
   - Connect: `groupPrev.next` → new head of reversed group
   - Connect: tail of reversed group → next group's head
   - Move `groupPrev` to the tail of the current group
4. Return `dummy.next`.

---

## 4. Why This Strategy?

- **In-place:** No extra space used, only pointer manipulation.
- **Efficient:** Each node is visited exactly once, giving O(n) time.
- **Handles edge cases:** Dummy node simplifies head modifications.
- **Respects constraint:** Leaves remaining nodes (< k) unchanged.
- **Clean separation:** Helper functions make the code modular and easier to understand.

---

## 5. Pseudocode

```pseudo
function reverseKGroup(head, k):
    dummy = new ListNode(0)
    dummy.next = head
    groupPrev = dummy
    
    while true:
        kth = getKth(groupPrev, k)
        if kth == null:
            break
        
        groupNext = kth.next
        
        # Reverse the group
        prev = groupNext
        curr = groupPrev.next
        
        while curr != groupNext:
            temp = curr.next
            curr.next = prev
            prev = curr
            curr = temp
        
        # Connect with previous and next groups
        temp = groupPrev.next
        groupPrev.next = kth
        groupPrev = temp
    
    return dummy.next

function getKth(curr, k):
    while curr != null and k > 0:
        curr = curr.next
        k = k - 1
    return curr
```

---

## 6. Example Walkthrough

**Input:** head = [1,2,3,4,5,6], k = 3

**Initial State:**
```
dummy → 1 → 2 → 3 → 4 → 5 → 6 → null
        ↑
    groupPrev.next
```

**Step 1: Find kth node (k=3 from dummy)**
```
kth = 3
groupNext = 4
```

**Step 2: Reverse first group [1,2,3]**
- Start: 1 → 2 → 3 → 4
- After reversal: 3 → 2 → 1 → 4

```
dummy → 3 → 2 → 1 → 4 → 5 → 6 → null
```

**Step 3: Move groupPrev to node 1 (tail of reversed group)**

**Step 4: Find next kth node (k=3 from node 1)**
```
kth = 6
groupNext = null
```

**Step 5: Reverse second group [4,5,6]**
- Start: 4 → 5 → 6 → null
- After reversal: 6 → 5 → 4 → null

```
dummy → 3 → 2 → 1 → 6 → 5 → 4 → null
```

**Output:** [3,2,1,6,5,4]

---

## 7. Code (Java)

```java
public ListNode reverseKGroup(ListNode head, int k) {
    // Dummy node to handle edge cases
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode groupPrev = dummy;
    
    while (true) {
        // Find the kth node from groupPrev
        ListNode kth = getKth(groupPrev, k);
        if (kth == null) {
            break; // Less than k nodes remaining
        }
        
        ListNode groupNext = kth.next;
        
        // Reverse the group
        ListNode prev = groupNext;
        ListNode curr = groupPrev.next;
        
        while (curr != groupNext) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        
        // Connect the reversed group with previous and next groups
        ListNode temp = groupPrev.next; // This will be the new tail
        groupPrev.next = kth; // Connect to new head
        groupPrev = temp; // Move groupPrev to new tail
    }
    
    return dummy.next;
}

// Helper function to find the kth node from current
private ListNode getKth(ListNode curr, int k) {
    while (curr != null && k > 0) {
        curr = curr.next;
        k--;
    }
    return curr;
}
```

---

## 8. Complexity Analysis

**Time Complexity:** O(n)
- We visit each node exactly once during the reversal process.
- The `getKth()` function moves k steps at a time, but overall each node is visited once.

**Space Complexity:** O(1)
- Only using a constant number of pointers (dummy, groupPrev, kth, curr, prev, temp).
- No recursion, no additional data structures.

---

## 9. Key Points

- **Dummy node trick:** Simplifies handling when the head changes.
- **getKth helper:** Efficiently checks if k nodes are available.
- **In-place reversal:** Same technique as reversing entire list, but applied to groups.
- **Pointer connections:** Critical to properly connect reversed group with previous and next groups.
- **Edge case handling:** When fewer than k nodes remain, they stay in original order.

---

## 10. Common Mistakes to Avoid

### ❌ MISTAKE 1: Not checking if k nodes exist
```java
// WRONG - Reverses even if fewer than k nodes remain
ListNode kth = curr;
for (int i = 0; i < k; i++) {
    kth = kth.next; // Can become null!
}
```
**Fix:** Always check if `getKth()` returns null before reversing.

### ❌ MISTAKE 2: Losing track of group tail
```java
// WRONG - Can't connect groups properly
groupPrev.next = kth;
// Forgot to update groupPrev to the tail!
```
**Fix:** Save the original `groupPrev.next` (which becomes the tail after reversal) and update `groupPrev` to it.

### ❌ MISTAKE 3: Wrong reversal termination
```java
// WRONG - Reverses entire remaining list
while (curr != null) {
    // Should stop at groupNext, not null!
}
```
**Fix:** Use `while (curr != groupNext)` to reverse only k nodes.

### ❌ MISTAKE 4: Forgetting dummy node
```java
// WRONG - Difficult to handle when head changes
ListNode groupPrev = head;
```
**Fix:** Always use a dummy node for problems involving head modifications.

### ❌ MISTAKE 5: Modifying node values instead of pointers
```java
// WRONG - Problem specifically says not to modify values
for (int i = 0; i < k/2; i++) {
    swap(nodes[i].val, nodes[k-1-i].val);
}
```
**Fix:** Only modify `next` pointers, not values.

---

## 11. Edge Cases to Test

### Case 1: k equals list length
```
Input: head = [1,2,3], k = 3
Output: [3,2,1]
```

### Case 2: k equals 1 (no reversal)
```
Input: head = [1,2,3,4], k = 1
Output: [1,2,3,4]
```

### Case 3: List length not divisible by k
```
Input: head = [1,2,3,4,5], k = 2
Output: [2,1,4,3,5]
```

### Case 4: Single node
```
Input: head = [1], k = 1
Output: [1]
```

### Case 5: Exactly two groups
```
Input: head = [1,2,3,4,5,6], k = 3
Output: [3,2,1,6,5,4]
```

---

## 12. Visual Representation

**Original List (k=3):**
```
1 → 2 → 3 → 4 → 5 → 6 → null
[  Group 1  ] [  Group 2  ]
```

**After Reversing Group 1:**
```
3 → 2 → 1 → 4 → 5 → 6 → null
[  Group 1  ] [  Group 2  ]
(reversed)    (original)
```

**After Reversing Group 2:**
```
3 → 2 → 1 → 6 → 5 → 4 → null
[  Group 1  ] [  Group 2  ]
(reversed)    (reversed)
```

---

## 13. Related Problems

- **Reverse Linked List:** Same reversal technique, but for entire list
- **Reverse Linked List II:** Reverse between positions left and right
- **Swap Nodes in Pairs:** Special case where k = 2
- **Rotate List:** Similar pointer manipulation techniques

---

## 14. Interview Tips

**What to say in an interview:**
> "This problem requires reversing the linked list in groups of k nodes. I'll use a dummy node to handle edge cases and implement a helper function to check if k nodes are available. For each group, I'll reverse it in-place using the standard three-pointer technique, then carefully reconnect it with the previous and next groups. The time complexity is O(n) since we visit each node once, and space complexity is O(1) as we only use pointers."

**Key points to mention:**
1. **Dummy node:** Simplifies head modifications
2. **Helper function:** `getKth()` to validate k nodes exist
3. **In-place reversal:** Standard technique with three pointers
4. **Pointer management:** Track previous tail, current head/tail, next head
5. **Edge case:** Leave remaining nodes (< k) unchanged
6. **Complexity:** O(n) time, O(1) space

---

## 15. Alternative Approaches

### Approach 2: Recursive Solution
```java
public ListNode reverseKGroup(ListNode head, int k) {
    ListNode curr = head;
    int count = 0;
    
    // Check if k nodes exist
    while (curr != null && count < k) {
        curr = curr.next;
        count++;
    }
    
    if (count == k) {
        // Reverse k nodes
        curr = reverseKGroup(curr, k); // Recursively reverse rest
        
        // Reverse current k nodes
        while (count > 0) {
            ListNode temp = head.next;
            head.next = curr;
            curr = head;
            head = temp;
            count--;
        }
        head = curr;
    }
    
    return head;
}
```

**Pros:** Cleaner conceptually  
**Cons:** Uses O(n/k) recursion stack space

---

## 16. Final Pattern Label

✅ **Linked List - In-place Group Reversal with Pointer Management**

**Remember:** When you see "reverse in groups" + "in-place" → use dummy node + helper to check k nodes + standard reversal with careful reconnection!

