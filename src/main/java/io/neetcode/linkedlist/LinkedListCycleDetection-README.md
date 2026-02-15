# Linked List Cycle Detection

**Difficulty:** Easy  
**Pattern:** Two Pointers (Fast & Slow), Cycle Detection (Floyd's Algorithm)  
**Recommended Time & Space Complexity:**  
- Time: O(n)  
- Space: O(1)  

---

## 1. Problem Understanding

Given the head of a linked list, determine if there is a cycle in the list. Return `true` if a cycle exists, otherwise return `false`.

- **What is a cycle?**  
  A cycle exists when a node's `next` pointer points back to a previous node in the list, creating a loop.

- **Example 1:**  
  Input: [1,2,3,4], cycle starts at index 1  
  Output: true  
  (The tail node points back to the node at index 1)

- **Example 2:**  
  Input: [1,2], no cycle (index = -1)  
  Output: false

- **Key Constraint:** You cannot modify the list or use the `index` parameter (it's internal).

---

## 2. Pattern to Use

This is a classic **Two Pointer (Fast & Slow)** problem, also known as **Floyd's Cycle Detection Algorithm** or the **Tortoise and Hare Algorithm**.

The pattern involves:
- Using two pointers moving at different speeds
- If there's a cycle, the faster pointer will eventually "lap" the slower one
- If there's no cycle, the faster pointer will reach the end (null)

---

## 3. Algorithm & Approach

### Brute Force (Not optimal)
- Use a **HashSet** to store visited nodes.
- Traverse the list and check if the current node already exists in the set.
- If yes, there's a cycle. If you reach null, there's no cycle.
- **Drawback:** Uses O(n) extra space.

### Optimal Approach (Floyd's Cycle Detection - Two Pointers)
- Use two pointers: `slow` and `fast`.
- `slow` moves 1 step at a time.
- `fast` moves 2 steps at a time.
- If there's a cycle, `fast` and `slow` will eventually meet.
- If there's no cycle, `fast` will reach null.

**Step-by-step:**
1. Initialize both `slow` and `fast` to `head`.
2. While `fast` and `fast.next` are not null:
   - Move `slow` by 1 step: `slow = slow.next`
   - Move `fast` by 2 steps: `fast = fast.next.next`
   - If `slow == fast`, return `true` (cycle detected)
3. If the loop ends, return `false` (no cycle)

---

## 4. Why This Strategy?

### Why does the fast and slow pointer approach work?

**Mathematical Proof:**
- If there's a cycle, both pointers will eventually enter the cycle.
- Once both are in the cycle, the gap between them decreases by 1 with each iteration.
- **Why?** 
  - In each step, `slow` moves 1 position forward
  - `fast` moves 2 positions forward
  - Relative to `slow`, `fast` moves 1 position closer per iteration
  - Eventually, the gap becomes 0 and they meet

**Example:** If the gap is 10 nodes:
- After 1 iteration: slow moves +1, fast moves +2 → gap = 9
- After 2 iterations: gap = 8
- ...continues until gap = 0

**Advantages:**
- **Space efficient:** O(1) space, no extra data structures needed
- **Time efficient:** O(n) time, we visit each node at most twice
- **No modification:** The original list remains unchanged

---

## 5. Pseudocode

```pseudo
if head is null:
    return false

slow = head
fast = head

while fast != null and fast.next != null:
    slow = slow.next
    fast = fast.next.next
    
    if slow == fast:
        return true

return false
```

---

## 6. Example Walkthrough

### Example 1: List with cycle [1→2→3→4] (4 points back to 2)

```
1 → 2 → 3 → 4
    ↑_______|
```

- Initial: slow = 1, fast = 1
- Step 1: slow = 2, fast = 3
- Step 2: slow = 3, fast = 2 (fast wrapped around)
- Step 3: slow = 4, fast = 4 → **They meet! Return true**

### Example 2: List without cycle [1→2→3→null]

- Initial: slow = 1, fast = 1
- Step 1: slow = 2, fast = 3
- Step 2: slow = 3, fast = null → **Loop ends, return false**

---

## 7. Code (Java)

```java
public boolean hasCycle(ListNode head) {
    if (head == null) {
        return false;
    }
    
    ListNode slow = head;
    ListNode fast = head;
    
    while (fast != null && fast.next != null) {
        slow = slow.next;          // Move 1 step
        fast = fast.next.next;     // Move 2 steps
        
        if (slow == fast) {
            return true;           // Cycle detected
        }
    }
    
    return false;                  // No cycle
}
```

---

## 8. Key Points

### Why check `fast != null && fast.next != null`?
- We need to ensure `fast.next.next` is valid
- If `fast` is null, we can't access `fast.next`
- If `fast.next` is null, we can't access `fast.next.next`

### Edge Cases:
- **Empty list:** head = null → return false
- **Single node, no cycle:** [1] → return false
- **Single node, self-cycle:** [1] (1 points to itself) → return true
- **Two nodes, cycle:** [1,2] (2 points to 1) → return true

### Important Observations:
- The slow pointer moves at half the speed of the fast pointer
- If there's a cycle, the fast pointer will never become null
- The meeting point is NOT necessarily the start of the cycle
- This algorithm only detects if a cycle exists, not where it starts

### Variations:
- **Find cycle start:** Use Floyd's algorithm phase 2 (move one pointer to head, then move both at same speed)
- **Find cycle length:** Count steps from meeting point back to itself

---

## 9. Time & Space Complexity Analysis

### Time Complexity: O(n)
- **No cycle:** Fast pointer reaches end in n/2 iterations
- **With cycle:** 
  - Both pointers enter cycle in at most n steps
  - They meet within the cycle in at most cycle_length steps
  - Total: O(n)

### Space Complexity: O(1)
- Only two pointers used, regardless of input size
- No additional data structures

---

## 10. Common Mistakes to Avoid

1. **Forgetting null checks:** Always check `fast != null && fast.next != null`
2. **Wrong initialization:** Both pointers should start at `head`
3. **Checking wrong condition:** Check if `slow == fast` AFTER moving pointers
4. **Modifying the list:** Don't change node values or structure
5. **Using slow == fast initially:** They start at the same position, so check after first move

---

This problem is fundamental to understanding linked list algorithms and the two-pointer technique. Master this, and you'll find many similar problems easier to solve!

