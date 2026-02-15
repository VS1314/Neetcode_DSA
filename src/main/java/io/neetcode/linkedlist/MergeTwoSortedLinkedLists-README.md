# Merge Two Sorted Linked Lists

**Difficulty:** Easy  
**Pattern:** Linked List, Two Pointers, Merge  
**Recommended Time & Space Complexity:**  
- Time: O(n + m)  
- Space: O(1)  

---

## 1. Problem Understanding

Given two sorted linked lists, merge them into one sorted linked list using the original nodes (no new nodes created, just rearrange pointers).

- **Example 1:**  
  Input: list1 = [1,2,4], list2 = [1,3,5]  
  Output: [1,1,2,3,4,5]
  
- **Example 2:**  
  Input: list1 = [], list2 = [1,2]  
  Output: [1,2]

- **Example 3:**  
  Input: list1 = [], list2 = []  
  Output: []

---

## 2. Pattern to Use

This is a **Two Pointers + Linked List Merge** problem.  
The key pattern is to use a **dummy node** and compare values from both lists one by one, always picking the smaller value.

---

## 3. Algorithm & Approach

### Brute Force (Not optimal)
- Store all values from both lists in an array.
- Sort the array.
- Create a new linked list from the sorted array.
- **Drawback:** Uses O(n + m) extra space and O((n+m)log(n+m)) time due to sorting.

### Optimal Approach (Two Pointers with Dummy Node)
- Create a dummy node to simplify edge cases and track the head of the merged list.
- Use two pointers to traverse both lists.
- At each step, compare the current nodes and attach the smaller one to the result.
- Move the pointer of the list whose node was selected.
- After one list is exhausted, attach the remaining part of the other list.

**Step-by-step:**
1. Create a `dummy` node and a `current` pointer pointing to it.
2. While both `list1` and `list2` are not null:
   - Compare `list1.val` and `list2.val`.
   - Attach the smaller node to `current.next`.
   - Move the pointer of the list whose node was selected.
   - Move `current` forward.
3. If one list is exhausted, attach the remaining list to `current.next`.
4. Return `dummy.next` (the actual head of the merged list).

---

## 4. Why This Strategy?

- **Leverages sorted property:** Since both lists are already sorted, we can merge them in a single pass without sorting.
- **In-place:** We're reusing the existing nodes, not creating new ones, so space complexity is O(1) (excluding the output).
- **Linear time:** We visit each node exactly once, so time complexity is O(n + m).
- **Dummy node trick:** Simplifies handling edge cases like empty lists and makes the code cleaner.

---

## 5. Pseudocode

```pseudo
dummy = new ListNode(0)
current = dummy

while list1 != null AND list2 != null:
    if list1.val <= list2.val:
        current.next = list1
        list1 = list1.next
    else:
        current.next = list2
        list2 = list2.next
    current = current.next

if list1 != null:
    current.next = list1
if list2 != null:
    current.next = list2

return dummy.next
```

---

## 6. Example Walkthrough

Given: list1 = [1,2,4], list2 = [1,3,5]

- **Initial:** dummy → current, list1 = 1, list2 = 1
- **Step 1:** Compare 1 vs 1, pick list1's 1 → current.next = 1, list1 = 2
- **Step 2:** Compare 2 vs 1, pick list2's 1 → current.next = 1, list2 = 3
- **Step 3:** Compare 2 vs 3, pick list1's 2 → current.next = 2, list1 = 4
- **Step 4:** Compare 4 vs 3, pick list2's 3 → current.next = 3, list2 = 5
- **Step 5:** Compare 4 vs 5, pick list1's 4 → current.next = 4, list1 = null
- **Step 6:** list1 is null, attach remaining list2 (5)

Result: 1 → 1 → 2 → 3 → 4 → 5

---

## 7. Code (Java)

```java
public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    // Create a dummy node to simplify edge cases
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    
    // Traverse both lists and merge
    while (list1 != null && list2 != null) {
        if (list1.val <= list2.val) {
            current.next = list1;
            list1 = list1.next;
        } else {
            current.next = list2;
            list2 = list2.next;
        }
        current = current.next;
    }
    
    // Attach the remaining nodes from either list
    if (list1 != null) {
        current.next = list1;
    }
    if (list2 != null) {
        current.next = list2;
    }
    
    return dummy.next; // Return the head of merged list
}
```

---

## 8. Key Points

- **Dummy node:** Helps avoid special handling for the head of the result list.
- **In-place merging:** We reuse existing nodes instead of creating new ones.
- **Edge cases:** Works correctly for empty lists (one or both).
- **Comparison:** Use `<=` to ensure stability (when values are equal, pick from list1 first).
- **Remaining nodes:** After one list is exhausted, simply attach the rest of the other list (since both are already sorted).

---

## 9. Common Mistakes to Avoid

- Forgetting to handle empty lists.
- Not moving the `current` pointer after attaching a node.
- Creating new nodes instead of reusing existing ones.
- Forgetting to return `dummy.next` instead of `dummy`.

