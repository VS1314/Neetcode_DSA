# Merge Two Sorted Linked Lists

## Problem Description

**Difficulty**: Easy

You are given the heads of two sorted linked lists `list1` and `list2`.

Merge the two lists into one **sorted** linked list and return the head of the new sorted linked list.

The new list should be made up of nodes from `list1` and `list2`.

**Key Requirements:**
- Both input lists are **already sorted** in ascending order
- Output must be a **single sorted list**
- Reuse existing nodes (no need to create new nodes)
- Maintain sorted order in merged list

**Visual Example:**
```
list1: 1 → 2 → 4 → null
list2: 1 → 3 → 5 → null

Merged: 1 → 1 → 2 → 3 → 4 → 5 → null
        ↑   ↑   ↑   ↑   ↑   ↑
       l2  l1  l1  l2  l1  l2
```

## Examples

### Example 1:
```
Input: list1 = [1,2,4], list2 = [1,3,5]
Output: [1,1,2,3,4,5]

Visualization:
list1: 1 → 2 → 4 → null
list2: 1 → 3 → 5 → null

Merge process:
  Compare 1 and 1: take 1 from list2 (or list1, either works)
  Compare 1 and 1: take 1 from list1
  Compare 2 and 3: take 2 from list1
  Compare 4 and 3: take 3 from list2
  Compare 4 and 5: take 4 from list1
  Only list2 remains: take 5 from list2

Result: 1 → 1 → 2 → 3 → 4 → 5 → null
```

### Example 2:
```
Input: list1 = [], list2 = [1,2]
Output: [1,2]

Explanation:
list1 is empty, return list2 as is
```

### Example 3:
```
Input: list1 = [], list2 = []
Output: []

Explanation:
Both lists empty, return empty list
```

### Example 4:
```
Input: list1 = [1], list2 = [2]
Output: [1,2]

Visualization:
list1: 1 → null
list2: 2 → null

Result: 1 → 2 → null
```

### Example 5:
```
Input: list1 = [5], list2 = [1,2,3,4]
Output: [1,2,3,4,5]

Explanation:
All elements of list2 come before list1's element
list2 is exhausted first
Then append remaining list1
```

### Example 6:
```
Input: list1 = [1,3,5,7], list2 = [2,4,6,8]
Output: [1,2,3,4,5,6,7,8]

Explanation:
Alternating pattern - perfectly interleaved
```

### Example 7:
```
Input: list1 = [1,2,3], list2 = [1,2,3]
Output: [1,1,2,2,3,3]

Explanation:
Both lists identical
Result has duplicates from both
```

### Example 8:
```
Input: list1 = [-10,-5,0], list2 = [-8,-3,1]
Output: [-10,-8,-5,-3,0,1]

Explanation:
Works with negative numbers
Maintain sorted order
```

### Example 9:
```
Input: list1 = [1,1,1], list2 = [1,1,1]
Output: [1,1,1,1,1,1]

Explanation:
All elements equal
All six nodes in result
```

### Example 10:
```
Input: list1 = [1,2,100], list2 = [3,4,5]
Output: [1,2,3,4,5,100]

Explanation:
Large gap in list1 values
list2 elements inserted in middle
Last element of list1 appended at end
```

## Constraints
- `0 <= The length of each list <= 100`
- `-100 <= Node.val <= 100`
- Both `list1` and `list2` are sorted in **non-decreasing** order

**Recommended Complexity**: 
- Time: O(n + m) where n = length of list1, m = length of list2
- Space: O(1) — constant extra space (excluding output)

---

## Pattern Recognition

**Primary Pattern**: **Two-Pointer Merge with Dummy Node**

**Why This Pattern?**
- Two **sorted** lists need merging
- Must maintain **sorted order**
- Process nodes **one by one** comparing values
- **Dummy node** simplifies edge cases (no special handling for head)

**Key Insight**: Merge Sort's Merge Step
```
This is exactly the merge step from merge sort!

Given two sorted arrays/lists:
  [1, 3, 5] and [2, 4, 6]

Merge process:
  Compare first elements: 1 vs 2
  1 is smaller → add 1
  
  Compare next: 3 vs 2
  2 is smaller → add 2
  
  Compare next: 3 vs 4
  3 is smaller → add 3
  
  Continue until both exhausted...
  
Result: [1, 2, 3, 4, 5, 6]

Same idea for linked lists!
```

**Why Dummy Node?**
```
Without dummy node:
  Need special logic for determining head of result
  
  if (list1.val <= list2.val):
      result = list1
      list1 = list1.next
  else:
      result = list2
      list2 = list2.next
      
  Then build rest...
  Complex! ❌

With dummy node:
  dummy = new Node(0)  // Value doesn't matter
  current = dummy
  
  // Now we can always do current.next = ...
  // No special case for first node!
  
  Return dummy.next (skip dummy)
  Simple! ✓
```

**The Two-Pointer Dance**:
```
Maintain two pointers (one for each list):
  l1 → list1
  l2 → list2

Also maintain current pointer for result:
  current → building merged list

While both l1 and l2 exist:
  Compare l1.val and l2.val
  Link smaller one to current
  Move that pointer forward
  Move current forward

After loop, one or both lists exhausted:
  If l1 remains: link rest of l1
  If l2 remains: link rest of l2
  
Done!
```

**Detailed Process**:
```
list1: 1 → 3 → 5 → null
list2: 2 → 4 → null

dummy → null
current = dummy, l1 = 1, l2 = 2

Step 1: Compare 1 and 2
  1 ≤ 2 → take from list1
  dummy → 1
  l1 = 3, current = 1

Step 2: Compare 3 and 2
  2 < 3 → take from list2
  dummy → 1 → 2
  l2 = 4, current = 2

Step 3: Compare 3 and 4
  3 ≤ 4 → take from list1
  dummy → 1 → 2 → 3
  l1 = 5, current = 3

Step 4: Compare 5 and 4
  4 < 5 → take from list2
  dummy → 1 → 2 → 3 → 4
  l2 = null, current = 4

Step 5: l2 is null
  Append rest of l1
  dummy → 1 → 2 → 3 → 4 → 5

Return dummy.next = 1
```

**Why This Works**:
```
Both lists are sorted!
Key property: The smallest unprocessed element
             is always at the front of one of the lists.

If l1.val ≤ l2.val:
  l1.val is the smallest overall
  Add it to result
  
If l2.val < l1.val:
  l2.val is the smallest overall
  Add it to result

This greedy choice is optimal!
```

**Comparison Strategy**:
```
When values are equal:
  list1 = [1, 2], list2 = [1, 3]
  
  When both are 1, which to choose?
  Doesn't matter for correctness!
  
  Typically choose list1 (or list2 consistently)
  Result is still sorted either way
  
  if (l1.val <= l2.val):  // Uses <= 
      take l1
  
  This takes l1 when equal
```

**Time Complexity Intuition**:
```
Must process every node from both lists:
  n nodes in list1
  m nodes in list2
  Total: n + m nodes

Each node processed once:
  Compare: O(1)
  Link: O(1)
  Move pointer: O(1)

Total: O(n + m) ✓
Cannot do better (must look at all nodes)!
```

**Space Complexity Intuition**:
```
Only need:
  - dummy node: O(1)
  - current pointer: O(1)
  - l1 pointer: O(1)
  - l2 pointer: O(1)

No recursion, no extra data structures
Space: O(1) ✓

Note: We reuse existing nodes!
Not creating new nodes (except dummy)
```

**Related Patterns**:
1. **Merge Sort** — Uses this as merge step
2. **Two Pointers** — Compare elements from two sources
3. **Dummy Node** — Simplify linked list construction
4. **Sorted List Operations** — Leverage sorted property

---

## Algorithm & Approach

### Core Insight

**Why Two-Pointer with Dummy Works:**
```
Key observations:
  1. Both lists already sorted (leverage this!)
  2. Smallest unprocessed element always at front
  3. Greedy choice: always take smaller front element
  4. Dummy node eliminates head edge case
```

**The Optimal Strategy**:
```
Key steps:
  1. Create dummy node (simplifies logic)
  2. Use two pointers for input lists
  3. Compare front elements, link smaller one
  4. Move pointers forward
  5. Append remaining list when one exhausted
  6. Return dummy.next
```

### Step-by-Step Algorithm

---

#### **Approach 1: Iterative Two-Pointer with Dummy - OPTIMAL**

**Core Idea**:
- Use dummy node to build result list
- Compare front elements of both lists
- Always choose smaller element
- Link remaining list when one exhausted

**Algorithm**
```
mergeTwoLists(list1, list2):
    // Edge cases
    if list1 == null:
        return list2
    if list2 == null:
        return list1
    
    // Create dummy node
    dummy = new ListNode(0)
    current = dummy
    
    // Two pointers for input lists
    l1 = list1
    l2 = list2
    
    // Merge while both have nodes
    while l1 != null and l2 != null:
        if l1.val <= l2.val:
            current.next = l1
            l1 = l1.next
        else:
            current.next = l2
            l2 = l2.next
        current = current.next
    
    // Append remaining nodes
    if l1 != null:
        current.next = l1
    else:
        current.next = l2
    
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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Edge cases: if either list is empty
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        
        // Create dummy node to simplify edge cases
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Pointers for traversing input lists
        ListNode l1 = list1;
        ListNode l2 = list2;
        
        // Merge while both lists have nodes
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                // Take from list1
                current.next = l1;
                l1 = l1.next;
            } else {
                // Take from list2
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        
        // Append remaining nodes from whichever list is not exhausted
        if (l1 != null) {
            current.next = l1;
        } else {
            current.next = l2;
        }
        
        // Return head of merged list (skip dummy)
        return dummy.next;
    }
}
```

**Optimized Version (Without Edge Case Checks)**
```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Dummy node eliminates need for edge case checks!
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        // Merge while both lists have nodes
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
        
        // Append remaining (works even if both are null!)
        current.next = (list1 != null) ? list1 : list2;
        
        return dummy.next;
    }
}
```

**Example Walkthrough**

Input: `list1 = [1,2,4]`, `list2 = [1,3,5]`

```
Initial state:
  list1: 1 → 2 → 4 → null
  list2: 1 → 3 → 5 → null
  
  dummy: 0 → null
  current = dummy
  l1 = 1, l2 = 1
```

**Iteration 1:**
```
Compare: l1.val (1) <= l2.val (1)? Yes
Action: Link l1 to current

Before:
  dummy → null
  l1 = 1, l2 = 1

Steps:
  current.next = l1
  l1 = l1.next (move to 2)
  current = current.next (move to 1)

After:
  dummy → 1 → (rest of list1)
          ↑
       current
  l1 = 2, l2 = 1
```

**Iteration 2:**
```
Compare: l1.val (2) <= l2.val (1)? No
Action: Link l2 to current

Before:
  dummy → 1 → null
          ↑
       current
  l1 = 2, l2 = 1

Steps:
  current.next = l2
  l2 = l2.next (move to 3)
  current = current.next (move to 1)

After:
  dummy → 1 → 1 → (rest of list2)
              ↑
           current
  l1 = 2, l2 = 3
```

**Iteration 3:**
```
Compare: l1.val (2) <= l2.val (3)? Yes
Action: Link l1 to current

Steps:
  current.next = l1
  l1 = l1.next (move to 4)
  current = current.next (move to 2)

After:
  dummy → 1 → 1 → 2 → (rest of list1)
                  ↑
               current
  l1 = 4, l2 = 3
```

**Iteration 4:**
```
Compare: l1.val (4) <= l2.val (3)? No
Action: Link l2 to current

Steps:
  current.next = l2
  l2 = l2.next (move to 5)
  current = current.next (move to 3)

After:
  dummy → 1 → 1 → 2 → 3 → (rest of list2)
                      ↑
                   current
  l1 = 4, l2 = 5
```

**Iteration 5:**
```
Compare: l1.val (4) <= l2.val (5)? Yes
Action: Link l1 to current

Steps:
  current.next = l1
  l1 = l1.next (move to null)
  current = current.next (move to 4)

After:
  dummy → 1 → 1 → 2 → 3 → 4 → null
                          ↑
                       current
  l1 = null, l2 = 5
```

**Loop ends (l1 is null)**

```
Append remaining l2:
  current.next = l2

Final:
  dummy → 1 → 1 → 2 → 3 → 4 → 5 → null

Return dummy.next (skip dummy node)
Result: 1 → 1 → 2 → 3 → 4 → 5 → null ✓
```

**Complexity Analysis**
- **Time**: O(n + m) — Process each node once
- **Space**: O(1) — Only pointers (dummy, current, l1, l2)

---

#### **Approach 2: Recursive - Alternative**

**Core Idea**:
- Base cases: if either list empty, return other
- Recursive case: choose smaller head, recurse on rest
- Build result by linking chosen node to recursive result

**Algorithm**
```
mergeTwoLists(list1, list2):
    // Base cases
    if list1 == null:
        return list2
    if list2 == null:
        return list1
    
    // Recursive case
    if list1.val <= list2.val:
        list1.next = mergeTwoLists(list1.next, list2)
        return list1
    else:
        list2.next = mergeTwoLists(list1, list2.next)
        return list2
```

**Code Implementation**
```java
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Base case: if either list is null, return the other
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        
        // Recursive case: choose smaller head
        if (list1.val <= list2.val) {
            // list1's head is smaller
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            // list2's head is smaller
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
    }
}
```

**Example Walkthrough**

Input: `list1 = [1,3]`, `list2 = [2,4]`

```
Call Stack:

mergeTwoLists([1,3], [2,4]):
  1 <= 2? Yes
  list1.next = mergeTwoLists([3], [2,4])
  
  → mergeTwoLists([3], [2,4]):
      3 <= 2? No
      list2.next = mergeTwoLists([3], [4])
      
      → mergeTwoLists([3], [4]):
          3 <= 4? Yes
          list1.next = mergeTwoLists(null, [4])
          
          → mergeTwoLists(null, [4]):
              Base case: return [4]
          
          Back: [3] → [4]
          Return [3]
      
      Back: [2] → [3] → [4]
      Return [2]
  
  Back: [1] → [2] → [3] → [4]
  Return [1]

Final: [1] → [2] → [3] → [4] ✓
```

**Complexity Analysis**
- **Time**: O(n + m) — Process each node once
- **Space**: O(n + m) — Recursive call stack

**When to Use**:
- Recursive is elegant and concise
- But uses O(n + m) space for call stack
- Risk of stack overflow for long lists
- **Iterative is preferred** for production code

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Recommended |
|----------|------|-------|-------------|
| **Iterative (dummy + 2 pointers)** | **O(n+m)** | **O(1)** | **Yes ✅** |
| Recursive | O(n+m) | O(n+m) | No (stack) |
| Array conversion | O(n+m) | O(n+m) | No (extra space) |
| Priority Queue/Heap | O((n+m)log(n+m)) | O(n+m) | No (overkill) |

**Winner**: **Iterative two-pointer** — O(1) space, optimal time!

### Why Dummy Node is Essential

```
Without dummy node:
  Need to determine head first
  Special case for first comparison
  
  if (list1.val <= list2.val):
      head = list1
      current = list1
      list1 = list1.next
  else:
      head = list2
      current = list2
      list2 = list2.next
  
  Then continue merging...
  Extra 5-6 lines of code ❌

With dummy node:
  dummy = new ListNode(0)
  current = dummy
  
  // Same logic for all nodes!
  while (list1 && list2):
      compare and link
  
  return dummy.next
  
  Clean and simple! ✓
```

### Why Check Both Lists in Loop Condition

```
Loop: while (l1 != null && l2 != null)

Why both?
  Need elements from BOTH lists to compare
  
If l1 becomes null:
  No more elements to compare
  Just append rest of l2
  Exit loop

If l2 becomes null:
  No more elements to compare
  Just append rest of l1
  Exit loop

If only checked one:
  while (l1 != null):
    What if l2 is null? l2.val would throw NullPointerException! ❌
  
Must check both!
```

### Why Append Remaining List

```
After loop, at least one list is exhausted:
  Either l1 == null or l2 == null (or both)

Key insight: Remaining list is already sorted!
  No need to process node by node
  Can link entire remaining list at once!

Example:
  list1 = [1, 2], list2 = [3, 4, 5, 6]
  
  After merging 1, 2, 3:
    l1 = null
    l2 = [4, 5, 6]
  
  Just do: current.next = l2
  
  Result: [1, 2, 3, 4, 5, 6] ✓
  
  No need to iterate through [4, 5, 6]!
```

### Why Use <= Instead of <

```
When values are equal:
  list1 = [1], list2 = [1]

If we use <:
  if (l1.val < l2.val):
      take l1
  else:
      take l2
  
  When equal, takes l2 first
  Result: [1 from l2, 1 from l1] ✓

If we use <=:
  if (l1.val <= l2.val):
      take l1
  else:
      take l2
  
  When equal, takes l1 first
  Result: [1 from l1, 1 from l2] ✓

Both correct! Convention is to use <=
This maintains "stability" (preserve relative order when equal)
```

### Why This is Optimal

```
Time: O(n + m)
  Must visit every node from both lists
  Each node processed once
  Cannot do better!

Space: O(1)
  Only 4 pointers (dummy, current, l1, l2)
  Reuse existing nodes
  No extra data structures
  Optimal!

This is the best possible solution!
```

---

## Critical Edge Cases & Gotchas

### 1. **Both Lists Empty**
```java
Input: list1 = null, list2 = null
Output: null

Handle in edge case check:
  if (list1 == null) return list2; // returns null ✓
```

### 2. **One List Empty**
```java
Input: list1 = null, list2 = [1,2,3]
Output: [1,2,3]

Return the non-empty list as is
No merging needed
```

### 3. **Lists of Different Lengths**
```java
Input: list1 = [1], list2 = [2,3,4,5]
Output: [1,2,3,4,5]

After first comparison:
  l1 becomes null
  Append rest of l2
```

### 4. **All Elements in One List Smaller**
```java
Input: list1 = [1,2,3], list2 = [4,5,6]
Output: [1,2,3,4,5,6]

Process all of list1 first
Then append all of list2
```

### 5. **All Elements in One List Larger**
```java
Input: list1 = [4,5,6], list2 = [1,2,3]
Output: [1,2,3,4,5,6]

Process all of list2 first
Then append all of list1
```

### 6. **Identical Lists**
```java
Input: list1 = [1,2,3], list2 = [1,2,3]
Output: [1,1,2,2,3,3]

All comparisons result in ties
With <=, list1 elements come first
Result interleaves or groups depending on ties
```

### 7. **Single Node in Each**
```java
Input: list1 = [1], list2 = [2]
Output: [1,2]

Minimal non-empty case
One comparison, then append
```

### 8. **Negative Values**
```java
Input: list1 = [-5,-3,-1], list2 = [-4,-2,0]
Output: [-5,-4,-3,-2,-1,0]

Comparison works same with negatives
```

### 9. **Boundary Values**
```java
Input: list1 = [-100], list2 = [100]
Output: [-100,100]

Min and max values in constraints
```

### 10. **Maximum Length Lists**
```java
Input: list1 = [1,2,3,...,100], list2 = [101,102,...,200]
Output: [1,2,3,...,200]

Maximum constraint: 100 nodes each
Iterative handles easily: O(1) space
Recursive might risk stack: O(n+m) space
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Forgetting to Move current Pointer**
```java
// WRONG - current never moves
while (l1 != null && l2 != null) {
    if (l1.val <= l2.val) {
        current.next = l1;
        l1 = l1.next;
    } else {
        current.next = l2;
        l2 = l2.next;
    }
    // Missing: current = current.next;
}
```

**Why wrong**: current stays at dummy, keeps overwriting same link!

**Dry run failure:**
```
list1 = [1,2], list2 = [3,4]

Iteration 1:
  current.next = l1 (1)
  l1 = 2
  // current still at dummy

Iteration 2:
  current.next = l1 (2)  // Overwrites link to 1! ❌
  l1 = null
  
Result: dummy → 2 → null
Lost node 1! ❌
```

**Fix**: Move current pointer
```java
while (l1 != null && l2 != null) {
    if (l1.val <= l2.val) {
        current.next = l1;
        l1 = l1.next;
    } else {
        current.next = l2;
        l2 = l2.next;
    }
    current = current.next;  // Move current!
}
```

### ❌ **MISTAKE 2: Not Returning dummy.next**
```java
// WRONG - returns dummy node itself
return dummy;  // ❌
```

**Why wrong**: Dummy is placeholder with value 0!

**Dry run failure:**
```
After merging [1,2] and [3,4]:
  dummy → 1 → 2 → 3 → 4

return dummy gives: [0,1,2,3,4] ❌
Should return: [1,2,3,4] ✓
```

**Fix**: Return dummy.next
```java
return dummy.next;  // Skip dummy node
```

### ❌ **MISTAKE 3: Modifying Input Lists Incorrectly**
```java
// WRONG - moving wrong pointer
while (l1 != null && l2 != null) {
    if (l1.val <= l2.val) {
        current.next = l1;
        l2 = l2.next;  // WRONG! Should be l1 = l1.next
    } else {
        current.next = l2;
        l1 = l1.next;  // WRONG! Should be l2 = l2.next
    }
    current = current.next;
}
```

**Why wrong**: Moving wrong list's pointer!

**Dry run failure:**
```
list1 = [1,2], list2 = [3,4]

Iteration 1:
  1 <= 3, take l1 (1)
  l2 = l2.next = 4  // WRONG! Should move l1
  
Iteration 2:
  1 <= 4 (still comparing 1!), take l1 again
  Infinite loop or wrong result ❌
```

**Fix**: Move the pointer of the list you just used
```java
if (l1.val <= l2.val) {
    current.next = l1;
    l1 = l1.next;  // Move l1 since we used it
}
```

### ❌ **MISTAKE 4: Not Handling Remaining Nodes**
```java
// WRONG - missing append step
while (l1 != null && l2 != null) {
    // ... merge logic
}
// Missing: current.next = (l1 != null) ? l1 : l2;
return dummy.next;
```

**Why wrong**: Incomplete merge!

**Dry run failure:**
```
list1 = [1], list2 = [2,3,4]

After loop:
  merged: 1 → 2
  l1 = null, l2 = [3,4]
  
Without appending:
  Result: [1,2] ❌
  Lost [3,4]!

Should be: [1,2,3,4] ✓
```

**Fix**: Append remaining list
```java
while (l1 != null && l2 != null) {
    // ... merge logic
}
// Append whichever list remains
current.next = (l1 != null) ? l1 : l2;
```

### ❌ **MISTAKE 5: Creating New Nodes Instead of Reusing**
```java
// WRONG - creating new nodes (wastes space and time)
while (l1 != null && l2 != null) {
    if (l1.val <= l2.val) {
        current.next = new ListNode(l1.val);  // WRONG!
        l1 = l1.next;
    } else {
        current.next = new ListNode(l2.val);  // WRONG!
        l2 = l2.next;
    }
    current = current.next;
}
```

**Why wrong**: Wastes space and time creating new nodes!

**Impact:**
```
Creating new nodes:
  Space: O(n + m) for all new nodes ❌
  Time: O(n + m) extra for node creation
  
Reusing nodes:
  Space: O(1) only pointers ✓
  Time: O(n + m) just linking ✓
  
Problem says "made up of nodes from list1 and list2"
Reuse existing nodes!
```

**Fix**: Link existing nodes
```java
current.next = l1;  // Link to existing node
```

### ❌ **MISTAKE 6: Wrong Comparison Operator**
```java
// WRONG - using > instead of <=
if (l1.val > l2.val) {
    current.next = l1;  // Should be l2!
    l1 = l1.next;
} else {
    current.next = l2;  // Should be l1!
    l2 = l2.next;
}
```

**Why wrong**: Takes larger element instead of smaller!

**Dry run failure:**
```
list1 = [1,3], list2 = [2,4]

Iteration 1:
  1 > 2? No → take l2 (2)
  Result so far: [2]
  
Iteration 2:
  1 > 4? No → take l2 (4)
  Result so far: [2,4]
  
Append l1: [2,4,1,3]
Not sorted! ❌
```

**Fix**: Use correct comparison
```java
if (l1.val <= l2.val) {
    current.next = l1;
    l1 = l1.next;
} else {
    current.next = l2;
    l2 = l2.next;
}
```

### ❌ **MISTAKE 7: Not Checking for Null Before Appending**
```java
// WRONG - this is actually fine!
// But overly cautious code might look like:
if (l1 != null) {
    current.next = l1;
} else if (l2 != null) {
    current.next = l2;
}
// This works but is verbose
```

**Why it's not wrong**: Actually correct, just verbose!

**Cleaner version:**
```java
// This handles all cases:
current.next = (l1 != null) ? l1 : l2;

// Even if both null, current.next = null is correct!
// Empty lists merge to empty list ✓
```

### ❌ **MISTAKE 8: Using Single Pointer for Both Lists**
```java
// WRONG - trying to use list1 and list2 directly
while (list1 != null && list2 != null) {
    if (list1.val <= list2.val) {
        current.next = list1;
        list1 = list1.next;  // Modifying parameter!
    } else {
        current.next = list2;
        list2 = list2.next;  // Modifying parameter!
    }
    current = current.next;
}
```

**Why it's not wrong**: Actually works in Java!

**Note:**
```
In Java, modifying list1 and list2 (the references)
doesn't affect the original references outside the method.

ListNode list1 = ...;  // Local copy of reference
list1 = list1.next;    // Modifies local copy only

This approach is fine!
Using separate l1, l2 variables is just clearer.
```

---

## Complexity Analysis

### Time Complexity: **O(n + m)**

| Operation | Time | Reason |
|-----------|------|--------|
| **Traverse list1** | O(n) | Visit each node once |
| **Traverse list2** | O(m) | Visit each node once |
| **Comparisons** | O(min(n,m)) | Until one list exhausted |
| **Append remaining** | O(1) | Just link pointer |
| **Total** | **O(n + m)** | Process all nodes |

**Time analysis**:
```
Must process every node from both lists:
  n nodes in list1
  m nodes in list2
  
Each node processed at most once:
  Either compared and linked, or
  Part of remaining list appended
  
Total operations: n + m
Time: O(n + m) ✓

Cannot do better:
  Must at least look at every node
  O(n + m) is optimal!
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| dummy node | O(1) | Single node |
| current pointer | O(1) | Single reference |
| l1 pointer | O(1) | Single reference |
| l2 pointer | O(1) | Single reference |
| **Total (Iterative)** | **O(1)** | Constant space |
| **Total (Recursive)** | **O(n+m)** | Call stack |

**Space analysis**:
```
Iterative approach:
  Only fixed number of pointers
  No extra data structures
  Reuse existing nodes
  Space: O(1) ✓

Recursive approach:
  Call stack depth = n + m (worst case)
  Each call stores local variables
  Space: O(n + m)
  
For constraints n, m ≤ 100:
  Recursive won't overflow
  But iterative is still preferred (true O(1))
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `list1 = [1,3,5]`, `list2 = [2,4,6]`

**Expected Output:** `[1,2,3,4,5,6]`

---

**Initial State:**
```
list1: 1 → 3 → 5 → null
list2: 2 → 4 → 6 → null

Create dummy:
  dummy: 0 → null
  current = dummy
  l1 = 1, l2 = 2
```

---

**Iteration 1: Compare 1 and 2**
```
Compare: l1.val (1) <= l2.val (2)? Yes
Action: Take from list1

Steps:
  current.next = l1
  l1 = l1.next (move to 3)
  current = current.next (move to 1)

Result:
  dummy → 1 → null
          ↑
       current
  l1 = 3, l2 = 2
```

---

**Iteration 2: Compare 3 and 2**
```
Compare: l1.val (3) <= l2.val (2)? No
Action: Take from list2

Steps:
  current.next = l2
  l2 = l2.next (move to 4)
  current = current.next (move to 2)

Result:
  dummy → 1 → 2 → null
              ↑
           current
  l1 = 3, l2 = 4
```

---

**Iteration 3: Compare 3 and 4**
```
Compare: l1.val (3) <= l2.val (4)? Yes
Action: Take from list1

Steps:
  current.next = l1
  l1 = l1.next (move to 5)
  current = current.next (move to 3)

Result:
  dummy → 1 → 2 → 3 → null
                  ↑
               current
  l1 = 5, l2 = 4
```

---

**Iteration 4: Compare 5 and 4**
```
Compare: l1.val (5) <= l2.val (4)? No
Action: Take from list2

Steps:
  current.next = l2
  l2 = l2.next (move to 6)
  current = current.next (move to 4)

Result:
  dummy → 1 → 2 → 3 → 4 → null
                      ↑
                   current
  l1 = 5, l2 = 6
```

---

**Iteration 5: Compare 5 and 6**
```
Compare: l1.val (5) <= l2.val (6)? Yes
Action: Take from list1

Steps:
  current.next = l1
  l1 = l1.next (move to null)
  current = current.next (move to 5)

Result:
  dummy → 1 → 2 → 3 → 4 → 5 → null
                          ↑
                       current
  l1 = null, l2 = 6
```

---

**Loop Ends (l1 == null)**

```
Append remaining l2:
  current.next = l2

Final:
  dummy → 1 → 2 → 3 → 4 → 5 → 6 → null

Return dummy.next
Result: [1,2,3,4,5,6] ✓
```

---

### Visual Comparison Process

```
list1: 1 → 3 → 5
list2: 2 → 4 → 6

Step-by-step merge:

Initial:
  Result: dummy
  Compare: 1 vs 2 → take 1

After 1:
  Result: dummy → 1
  Compare: 3 vs 2 → take 2

After 2:
  Result: dummy → 1 → 2
  Compare: 3 vs 4 → take 3

After 3:
  Result: dummy → 1 → 2 → 3
  Compare: 5 vs 4 → take 4

After 4:
  Result: dummy → 1 → 2 → 3 → 4
  Compare: 5 vs 6 → take 5

After 5:
  Result: dummy → 1 → 2 → 3 → 4 → 5
  list1 exhausted, append rest of list2

Final:
  Result: dummy → 1 → 2 → 3 → 4 → 5 → 6
  Return dummy.next = 1
```

---

## Comparison of Approaches

| Approach | Time | Space | Stack Safe | Recommended |
|----------|------|-------|------------|-------------|
| **Iterative (dummy + 2 pointers)** | **O(n+m)** | **O(1)** | **Yes ✅** | **Yes ✅** |
| Recursive | O(n+m) | O(n+m) | Yes (small lists) | No |
| Array conversion | O(n+m log(n+m)) | O(n+m) | Yes | No (extra time and space) |

**Winner**: **Iterative two-pointer with dummy** — optimal time and space!

**Why Not Array Conversion?**
```java
// Convert to array, sort, rebuild
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    List<Integer> values = new ArrayList<>();
    
    // Collect all values
    while (l1 != null) {
        values.add(l1.val);
        l1 = l1.next;
    }
    while (l2 != null) {
        values.add(l2.val);
        l2 = l2.next;
    }
    
    // Sort
    Collections.sort(values);  // O(n log n)
    
    // Rebuild list
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    for (int val : values) {
        curr.next = new ListNode(val);
        curr = curr.next;
    }
    
    return dummy.next;
}

// O((n+m)log(n+m)) time ❌
// O(n+m) space ❌
// Doesn't leverage sorted property!
```

---

## Key Takeaways

1. **Dummy node simplifies** — no special head case
2. **Two pointers** — one for each input list
3. **Compare and link** — take smaller element
4. **Append remaining** — rest is already sorted
5. **Return dummy.next** — skip placeholder
6. **O(n+m) time, O(1) space** — optimal solution
7. **Reuse existing nodes** — don't create new ones
8. **Move current pointer** — after each link
9. **Check both lists in loop** — avoid null dereference
10. **Leverage sorted property** — greedy choice is optimal

---

## Interview Tips

**What to say in an interview:**

> "Since both lists are already sorted, I can use a two-pointer approach similar to the merge step in merge sort. I'll use a dummy node to simplify building the result list, avoiding special cases for determining the head. I'll maintain two pointers, one for each list, and compare the front elements. I always take the smaller element and link it to the result, then move that list's pointer forward. This greedy approach works because the smallest unprocessed element is always at the front of one of the two lists. When one list is exhausted, I can simply append the remaining list since it's already sorted. The time complexity is O(n + m) since I process each node exactly once, and the space complexity is O(1) since I only use a few pointers and reuse existing nodes."

**Key points to mention:**
1. **Leverage sorted property** — merge like merge sort
2. **Dummy node** — simplifies edge cases
3. **Two pointers** — track position in each list
4. **Greedy choice** — always take smaller front element
5. **Append remaining** — rest is already sorted
6. **Return dummy.next** — skip placeholder
7. **O(n+m) time** — process each node once
8. **O(1) space** — only pointers, reuse nodes

**Common Follow-ups:**
- "Can you do it recursively?" → Yes, but uses O(n+m) space for call stack
- "What if there are k sorted lists?" → Use min-heap or merge pairs
- "What if lists aren't sorted?" → Would need to sort first (O(n log n))
- "Can you detect cycles?" → Different problem, needs fast/slow pointers
- "What about doubly linked lists?" → Same approach, just more pointers to update

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Merge Two Sorted Lists** | Easy | **Two-Pointer Merge** | **This problem** |
| Merge k Sorted Lists | Hard | Min-Heap or Divide-Conquer | k lists instead of 2 |
| Merge Sorted Array | Easy | Two-Pointer from end | Arrays, not lists |
| Sort List | Medium | Merge Sort on Linked List | Full sorting, not merge |
| Add Two Numbers | Medium | Two Pointers with carry | Addition, not merge |
| Intersection of Two Linked Lists | Easy | Two Pointers | Find common node |

**Pattern Progression**:
1. **Merge Two Sorted Lists** (this) — Basic merge
2. **Merge k Sorted Lists** — Extend to k lists with heap
3. **Sort List** — Use merge sort on unsorted list
4. **Advanced variations** — Merge with constraints

---

## Final Pattern Label

✅ **Two-Pointer Merge with Dummy Node (Merge Sort Merge Step)**

**Remember:** This is the **two-pointer merge technique** used in merge sort. Create a **dummy node** to simplify building the result (no special head case). Use **two pointers** (one for each sorted list) and compare front elements. Always take the **smaller element** using greedy choice (optimal because lists are sorted!). Link it to current, move that list's pointer forward, and move current forward. When one list exhausted, **append the remaining list** since it's already sorted. Finally, **return dummy.next** (skip placeholder). Achieves **O(n+m) time** (process each node once) and **O(1) space** (only pointers, reuse nodes). Critical: **move current pointer** after linking, check **both lists in loop condition** to avoid null, and **append remaining** list outside loop. Recursive version is elegant but uses O(n+m) space, so iterative is preferred for optimal space!
