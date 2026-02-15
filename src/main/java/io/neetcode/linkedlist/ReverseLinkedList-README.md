# Reverse Linked List

**Difficulty:** Easy  
**Pattern:** Linked List, Iterative/Pointer Manipulation  
**Recommended Time & Space Complexity:**  
- Time: O(n)  
- Space: O(1)  

---

## 1. Problem Understanding

Given the head of a singly linked list, reverse the list and return the new head.

- **Example:**  
  Input: [0,1,2,3]  
  Output: [3,2,1,0]
- **Edge Case:**  
  Input: []  
  Output: []

---

## 2. Pattern to Use

This is a classic **Linked List** manipulation problem.  
The key pattern is **pointer reversal**: you need to reverse the direction of the `next` pointers in the list.

---

## 3. Algorithm & Approach

### Brute Force (Not optimal)
- Store all node values in an array.
- Reverse the array.
- Build a new linked list from the reversed array.
- **Drawback:** Uses O(n) extra space.

### Optimal Approach (In-place reversal)
- Use three pointers: `prev`, `current`, and `next`.
- Traverse the list once, reversing the `next` pointer of each node.
- No extra space is used.

**Step-by-step:**
1. Initialize `prev` as `null` and `current` as `head`.
2. While `current` is not `null`:
   - Store `current.next` in `next`.
   - Set `current.next` to `prev` (reverse the pointer).
   - Move `prev` to `current`.
   - Move `current` to `next`.
3. When done, `prev` will be the new head.

---

## 4. Why This Strategy?

- **In-place:** No extra memory is used, so space complexity is O(1).
- **Single pass:** Each node is visited once, so time complexity is O(n).
- **Pointer manipulation:** This is the most efficient way to reverse a singly linked list.

---

## 5. Pseudocode

```pseudo
prev = null
current = head
while current != null:
    next = current.next
    current.next = prev
    prev = current
    current = next
return prev
```

---

## 6. Example Walkthrough

Given: 1 → 2 → 3 → null

- Initial: prev = null, current = 1
- Step 1: next = 2, 1.next = null, prev = 1, current = 2
- Step 2: next = 3, 2.next = 1, prev = 2, current = 3
- Step 3: next = null, 3.next = 2, prev = 3, current = null

Return prev (3 → 2 → 1 → null)

---

## 7. Code (Java)

```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode current = head;
    while (current != null) {
        ListNode nextTemp = current.next;
        current.next = prev;
        prev = current;
        current = nextTemp;
    }
    return prev;
}
```

---

## 8. Key Points

- Always keep track of the next node before reversing the pointer.
- After reversal, the original head becomes the tail (its next is null).
- The process works for empty lists and single-node lists as well.

