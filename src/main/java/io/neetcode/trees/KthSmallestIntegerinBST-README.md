# Kth Smallest Integer in BST

## Problem Description

**Difficulty**: Medium

Given the root of a **binary search tree**, and an integer `k`, return the **kth smallest value** (1-indexed) in the tree.

A binary search tree satisfies the following constraints:
- The **left subtree** of every node contains only nodes with keys **less than** the node's key.
- The **right subtree** of every node contains only nodes with keys **greater than** the node's key.
- Both the left and right subtrees are also **binary search trees**.

## Examples

### Example 1:
```
Input: root = [2,1,3], k = 1

Tree Structure:
      2
     / \
    1   3

In-order traversal: [1, 2, 3]
Output: 1
Explanation: The 1st smallest element is 1.
```

### Example 2:
```
Input: root = [4,3,5,2,null], k = 4

Tree Structure:
        4
       / \
      3   5
     /
    2

In-order traversal: [2, 3, 4, 5]
Output: 5
Explanation: The 4th smallest element is 5.
```

## Constraints
- 1 <= k <= The number of nodes in the tree <= 1000
- 0 <= Node.val <= 1000

---

## Pattern Recognition

**Primary Pattern**: **In-Order DFS Traversal on BST**

**Why This Pattern?**
- In a BST, **in-order traversal** (left → root → right) always yields values in **ascending sorted order**
- We don't need to sort — the BST structure guarantees order for free
- Simply count nodes as we visit them in-order; stop at the kth node

**Key Insight**:
- Naive approach: collect all values into a list → sort → return `list[k-1]` → O(n log n)
- Optimised: in-order traversal with a counter → stop early at kth node → **O(n)** time

**Related Patterns**:
1. **Valid Binary Search Tree** – Uses BST property and DFS
2. **Lowest Common Ancestor in BST** – Leverages BST ordering
3. **Insert into / Delete from BST** – BST traversal and manipulation
4. **Binary Tree Inorder Traversal** – Core traversal technique used here

---

## Algorithm & Approach

### Core Insight

**Why In-Order Traversal?**
```
BST:          4
             / \
            2   6
           / \ / \
          1  3 5  7

In-order (L → Root → R): 1, 2, 3, 4, 5, 6, 7  ← always sorted!

For k = 3 → answer = 3
For k = 5 → answer = 5
```

In-order visits nodes in **non-decreasing order** due to BST property.
We just need to count: when count reaches `k`, that node's value is the answer.

---

### Step-by-Step Algorithm

#### **Approach 1: Recursive In-Order DFS (OPTIMAL)**

**Core Idea**:
- Traverse the tree in-order (left → current → right)
- Maintain a counter; increment on each visit
- When counter equals `k`, capture the current value

**Algorithm**
```
kthSmallest(root, k):
    counter = 0
    result = -1

    inorder(node):
        if node is null or result found:
            return
        inorder(node.left)
        counter++
        if counter == k:
            result = node.val
            return
        inorder(node.right)

    inorder(root)
    return result
```

**Code Implementation**
```java
class Solution {
    private int count = 0;
    private int result = -1;

    public int kthSmallest(TreeNode root, int k) {
        inorder(root, k);
        return result;
    }

    private void inorder(TreeNode node, int k) {
        // Base case: null node or answer already found
        if (node == null || result != -1) {
            return;
        }

        // Traverse left subtree first (smaller values)
        inorder(node.left, k);

        // Visit current node
        count++;
        if (count == k) {
            result = node.val;
            return;
        }

        // Traverse right subtree (larger values)
        inorder(node.right, k);
    }
}
```

**Example Walkthrough**

Input: root = [4,3,5,2,null], k = 4

```
Tree:
        4
       / \
      3   5
     /
    2

In-order traversal steps:

inorder(4)
├─ inorder(3)
│  ├─ inorder(2)
│  │  ├─ inorder(null) → return
│  │  ├─ visit 2 → count = 1 (k=4, not yet)
│  │  └─ inorder(null) → return
│  ├─ visit 3 → count = 2 (k=4, not yet)
│  └─ inorder(null) → return
├─ visit 4 → count = 3 (k=4, not yet)
└─ inorder(5)
   ├─ inorder(null) → return
   ├─ visit 5 → count = 4 == k → result = 5 ✓
   └─ early return (result found)

Final Result: 5
```

**Step-by-Step Trace:**

| Step | Node Visited | Count | k | Action |
|------|-------------|-------|---|--------|
| 1 | 2 | 1 | 4 | Continue |
| 2 | 3 | 2 | 4 | Continue |
| 3 | 4 | 3 | 4 | Continue |
| 4 | 5 | 4 | 4 | **result = 5, stop** |

**Final Result: 5**

**Why This Works:**
1. **BST In-Order = Sorted Order**: Left subtree always has smaller values
2. **Early Termination**: Stop as soon as we find the kth element, no need to traverse the whole tree
3. **No Extra Sorting**: BST structure eliminates the need for O(n log n) sort
4. **Counter Tracks Rank**: Incrementing after left traversal ensures ascending count order

**Complexity Analysis**
- **Time Complexity**: O(n) — in worst case visit all nodes (k = n, skewed tree)
- **Space Complexity**: O(h) — recursion stack depth (h = height of tree)
  - Best case (balanced): O(log n)
  - Worst case (skewed): O(n)

---

#### **Approach 2: Iterative In-Order with Stack**

**Core Idea**:
- Simulate in-order traversal iteratively using an explicit stack
- Push all left nodes first, then pop and process, then go right
- Count each popped node; return when count equals `k`

**Code Implementation**
```java
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        int count = 0;

        while (current != null || !stack.isEmpty()) {
            // Go as far left as possible
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // Process current node (leftmost unprocessed)
            current = stack.pop();
            count++;

            // If this is the kth node, return its value
            if (count == k) {
                return current.val;
            }

            // Move to right subtree
            current = current.right;
        }

        return -1; // Should never reach here given valid input
    }
}
```

**Example Walkthrough**

Input: root = [2,1,3], k = 1

```
Stack-based in-order:

Iteration 1:
  Push left chain: stack = [2, 1]
  Pop 1 → count = 1 == k → return 1 ✓
```

**Complexity Analysis**
- **Time Complexity**: O(H + k) — H to reach leftmost node, then k steps
- **Space Complexity**: O(H) — stack stores at most H nodes (H = height of tree)

---

## Common Mistakes & Edge Cases

| Scenario | Issue | Fix |
|----------|-------|-----|
| Use pre-order or post-order | Does NOT give sorted order | Must use **in-order** (left → root → right) |
| No early termination | Traverses entire tree even after finding answer | Return/skip once `count == k` |
| k larger than number of nodes | Undefined per constraints | Constraints guarantee `k <= n`, safe to ignore |
| Single node tree, k = 1 | Should return that node's value | Base case handles null correctly |

---

## Visual Summary

```
In-Order Traversal Order in BST:

          4
         / \
        2   6        In-order: 1 → 2 → 3 → 4 → 5 → 6 → 7
       / \ / \
      1  3 5  7      k=1 → 1
                     k=3 → 3
                     k=6 → 6

Left subtree values < Root < Right subtree values
→ In-order gives ascending sorted sequence automatically!
```

---

## Complexity Summary

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| Recursive In-Order | O(n) | O(h) | Clean, with early termination |
| Iterative In-Order (Stack) | O(H + k) | O(H) | Slightly better in practice |
| Naive (sort array) | O(n log n) | O(n) | Avoid — doesn't use BST property |

---

## Key Takeaways

1. **In-order traversal of BST = ascending sorted sequence** — this is the core property to exploit
2. **No sorting needed** — BST structure gives order for free
3. **Early termination** — stop as soon as counter reaches `k`, don't traverse the rest
4. **Iterative is slightly better** — O(H + k) vs O(n) in the average case
5. **Counter = rank** — incrementing after visiting left subtree ensures correct ascending rank

