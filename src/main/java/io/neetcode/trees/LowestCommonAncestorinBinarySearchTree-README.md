# Lowest Common Ancestor in Binary Search Tree

## Problem Description

**Difficulty**: Medium

Given a binary search tree (BST) where all node values are **unique**, and two nodes from the tree `p` and `q`, return the lowest common ancestor (LCA) of the two nodes.

The **lowest common ancestor** between two nodes `p` and `q` is defined as the lowest node in a tree `T` such that both `p` and `q` are descendants (where a node is allowed to be a descendant of itself).

**Key Concepts:**
- **BST Invariant**: For every node $N$, all nodes in its left subtree have values $< N.val$, and all nodes in its right subtree have values $> N.val$.
- **Split Point Principle**:
  - If both `p.val < root.val` and `q.val < root.val`, the LCA must lie entirely in the **left subtree**.
  - If both `p.val > root.val` and `q.val > root.val`, the LCA must lie entirely in the **right subtree**.
  - If one value is $\le root.val$ and the other is $\ge root.val$ (a split occurs, or `root` equals `p` or `q`), the current `root` **is the LCA**.
- **No Full Traversal Needed**: We eliminate half the remaining search space at each step, identical to binary search.

**Visual Overview:**
```
BST:
             5
           /   \
          3     8
         / \   / \
        1   4 7   9
         \
          2

Case 1: p = 3, q = 8
  At Node 5: p.val (3) < 5 and q.val (8) > 5 -> Split occurs!
  LCA is 5.

Case 2: p = 3, q = 4
  At Node 5: Both 3 < 5 and 4 < 5 -> Move Left to Node 3.
  At Node 3: Current node == p (3) -> Split / Match!
  LCA is 3.

Case 3: p = 7, q = 9
  At Node 5: Both 7 > 5 and 9 > 5 -> Move Right to Node 8.
  At Node 8: 7 < 8 and 9 > 8 -> Split occurs!
  LCA is 8.
```

**Recommended Complexity**:
- Time: $O(h)$ where $h$ is the height of the BST ($O(\log n)$ for balanced, $O(n)$ worst case).
- Space: $O(1)$ iterative, or $O(h)$ recursive call stack.

---

## Examples

### Example 1 (Split at Root):
```
Input: root = [5,3,8,1,4,7,9,null,2], p = 3, q = 8

Tree:
        5
      /   \
     3     8
    / \   / \
   1   4 7   9
    \
     2

Output: 5

Explanation:
p.val = 3 is in the left subtree of 5, and q.val = 8 is in the right subtree of 5.
Since they diverge at 5, node 5 is the Lowest Common Ancestor.
```

### Example 2 (LCA is One of the Nodes):
```
Input: root = [5,3,8,1,4,7,9,null,2], p = 3, q = 4

Tree:
        5
      /   \
     3     8
    / \   / \
   1   4 7   9
    \
     2

Output: 3

Explanation:
Both p (3) and q (4) are < 5, so we move to node 3.
At node 3, root.val == p.val (3), and 4 is in its right subtree.
Node 3 is an ancestor of 4 and a descendant of itself, so LCA = 3.
```

### Example 3 (Both Nodes in Right Subtree):
```
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 7, q = 9

Tree:
        6
      /   \
     2     8
    / \   / \
   0   4 7   9
      / \
     3   5

Output: 8

Explanation:
Both 7 > 6 and 9 > 6 -> Move right to 8.
At 8: 7 < 8 and 9 > 8 -> Split occurs!
LCA = 8.
```

### Example 4 (Two-Node Tree):
```
Input: root = [2,1], p = 2, q = 1

Tree:
    2
   /
  1

Output: 2

Explanation:
root is 2, which matches p. LCA is 2.
```

### Example 5 (Deep Left Subtree LCA):
```
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 3, q = 5

Tree:
        6
      /   \
     2     8
    / \   / \
   0   4 7   9
      / \
     3   5

Output: 4

Explanation:
- At 6: Both 3 < 6 and 5 < 6 -> Go Left (to 2)
- At 2: Both 3 > 2 and 5 > 2 -> Go Right (to 4)
- At 4: 3 < 4 and 5 > 4 -> Split at 4!
LCA = 4.
```

### Example 6 (Nodes Reversed Order `p > q`):
```
Input: root = [5,3,8], p = 8, q = 3

Output: 5

Explanation:
Order of p and q does not affect the split condition at 5.
```

### Example 7 (Linear / Skewed BST):
```
Input: root = [1,null,2,null,3,null,4], p = 2, q = 4

Tree:
  1
   \
    2
     \
      3
       \
        4

Output: 2

Explanation:
- At 1: Both 2 > 1 and 4 > 1 -> Go Right (to 2)
- At 2: root.val == p.val (2) -> Return 2.
```

### Example 8 (Leaf Node and Parent):
```
Input: root = [5,3,8,1,4], p = 1, q = 3

Output: 3

Explanation:
Both 1 < 5 and 3 < 5 -> Go Left (to 3).
At 3: matches q. LCA = 3.
```

### Example 9 (Outer Leaves):
```
Input: root = [5,3,8,1,4,7,9], p = 1, q = 9

Output: 5

Explanation:
1 < 5 and 9 > 5 -> Split at 5. LCA = 5.
```

### Example 10 (Direct Left and Right Children of a Leaf):
```
Input: root = [5,3,8,1,4,null,null], p = 1, q = 4

Output: 3

Explanation:
Both 1 < 5 and 4 < 5 -> Go Left to 3.
At 3: 1 < 3 and 4 > 3 -> Split at 3. LCA = 3.
```

---

## Constraints
- The number of nodes in the tree is in the range `[2, 100]`.
- `-100 <= Node.val <= 100`
- All `Node.val` are **unique**.
- `p != q`
- `p` and `q` are guaranteed to exist in the BST.

---

## Pattern Recognition

**Primary Pattern**: **BST Property Navigation / Binary Search on Tree**

**Why This Pattern?**
- Unlike a standard binary tree where we must traverse both left and right subtrees ($O(n)$ DFS), a **BST** provides deterministic directionality based on node values.
- At any given node:
  1. If `p.val < curr.val` AND `q.val < curr.val` $\implies$ LCA must be in `curr.left`.
  2. If `p.val > curr.val` AND `q.val > curr.val` $\implies$ LCA must be in `curr.right`.
  3. Otherwise, `curr` is the divergence/split point $\implies$ `curr` is the LCA.

```
       BST LCA Decision Table:
       +------------------------------------+-------------------------+
       | Condition                          | Action                  |
       +------------------------------------+-------------------------+
       | p.val < curr.val && q.val < curr.val| Traverse curr.left      |
       | p.val > curr.val && q.val > curr.val| Traverse curr.right     |
       | Otherwise (Split or curr == p || q)| RETURN curr (Found LCA!)|
       +------------------------------------+-------------------------+
```

---

## Algorithm & Approach

### Approach 1: Iterative BST Traversal ($O(1)$ Space) — Recommended / Optimal

#### Method Explanation:
1. Initialize a pointer `curr = root`.
2. Loop while `curr != null`:
   - If both `p.val < curr.val` and `q.val < curr.val`, advance `curr = curr.left`.
   - Else if both `p.val > curr.val` and `q.val > curr.val`, advance `curr = curr.right`.
   - Else, we have found the split point or one of the nodes. Return `curr`.

#### Java Code Implementation:
```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class LowestCommonAncestorinBinarySearchTree {

    /**
     * Finds the lowest common ancestor (LCA) of nodes p and q in a BST using iterative traversal.
     * 
     * @param root The root node of the BST
     * @param p First target node
     * @param q Second target node
     * @return The LCA TreeNode
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;

        while (curr != null) {
            if (p.val < curr.val && q.val < curr.val) {
                // Both nodes lie in the left subtree
                curr = curr.left;
            } else if (p.val > curr.val && q.val > curr.val) {
                // Both nodes lie in the right subtree
                curr = curr.right;
            } else {
                // Split point found: curr is the LCA (or curr equals p or q)
                return curr;
            }
        }

        return null;
    }
}
```

---

### Approach 2: Recursive BST Traversal ($O(h)$ Space) — Alternative

#### Java Code Implementation:
```java
public class LowestCommonAncestorRecursive {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }

        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }

        return root;
    }
}
```

---

## Why This Strategy?

### Comparison of Approaches

| Criteria | Iterative BST (Approach 1) | Recursive BST (Approach 2) | General Binary Tree LCA |
| :--- | :--- | :--- | :--- |
| **Time Complexity** | $O(h)$ | $O(h)$ | $O(n)$ |
| **Space Complexity**| $O(1)$ Auxiliary | $O(h)$ Call Stack | $O(h)$ Call Stack |
| **Code Length** | ~15 lines | ~10 lines | ~20 lines |
| **Subtree Visits** | Single branch only | Single branch only | Both branches |
| **Recommendation** | ⭐ **Top Choice ($O(1)$ Space)**| Clean alternative | Overkill for BST |

---

## Critical Edge Cases & Gotchas

1. **`p` is an Ancestor of `q` (or vice-versa)**:
   - When `curr.val == p.val`, `p.val < curr.val` and `p.val > curr.val` are both false. The `else` branch triggers immediately, correctly returning `p`.
2. **`p.val > q.val` vs `p.val < q.val`**:
   - The condition checks both with `&&`, so the order of `p` and `q` does not matter.
3. **Small Trees (2 nodes)**:
   - Loop handles 2-node trees seamlessly.
4. **Skewed Trees**:
   - Handled correctly in $O(n)$ time with $O(1)$ memory in the iterative solution.

---

## Major Areas Where We Might Go Wrong

### ❌ Mistake 1: Treating BST as a General Binary Tree
```java
// WRONG: Searching both left and right subtrees unconditionally in a BST
TreeNode left = lowestCommonAncestor(root.left, p, q);
TreeNode right = lowestCommonAncestor(root.right, p, q);
if (left != null && right != null) return root; // O(n) instead of O(h)!
```
**Why wrong**: Fails to exploit BST ordering, degrading $O(\log n)$ performance to $O(n)$.
**Correction**: Direct the traversal to only left or only right based on values.

---

### ❌ Mistake 2: Missing the Self-Descendant Case
```java
// WRONG: Explicitly skipping when curr == p, trying to find another parent
if (curr == p) curr = curr.right; // ❌ A node is its own descendant!
```
**Correction**: When `curr.val == p.val || curr.val == q.val`, `curr` is the LCA.

---

### ❌ Mistake 3: Using `||` Instead of `&&`
```java
// WRONG: Moves left if EITHER p or q is smaller!
if (p.val < curr.val || q.val < curr.val) curr = curr.left; // ❌
```
**Correction**: Both must be smaller (`p.val < curr.val && q.val < curr.val`) to move left.

---

## Complexity Analysis

- **Time Complexity**: $O(h)$
  - Balanced BST: $h = O(\log n)$ $\implies$ $O(\log n)$ steps.
  - Skewed BST: $h = O(n)$ $\implies$ $O(n)$ steps.
  - At each step, we do $O(1)$ comparisons and move down one level.
- **Space Complexity**:
  - Iterative Approach: $O(1)$ auxiliary space.
  - Recursive Approach: $O(h)$ call stack space.

---

## Visualization

```
BST:
             6
           /   \
          2     8
         / \   / \
        0   4 7   9
           / \
          3   5

Query: p = 3, q = 5

Step 1: curr = 6
        p.val (3) < 6 AND q.val (5) < 6  --> Go Left (curr = 2)

Step 2: curr = 2
        p.val (3) > 2 AND q.val (5) > 2  --> Go Right (curr = 4)

Step 3: curr = 4
        p.val (3) < 4 BUT q.val (5) > 4  --> Split detected!
        Return 4.

LCA(3, 5) = 4 ✓
```

---

## Comparison of Approaches

```
+-------------------+--------------------+----------------------+
| Feature           | Iterative BST LCA  | Recursive BST LCA    |
+-------------------+--------------------+----------------------+
| Time Complexity   | O(h)               | O(h)                 |
| Space Complexity  | O(1)               | O(h)                 |
| Stack Overhead    | None               | Call stack per depth |
| Risk of Overflow  | None               | Possible if skewed   |
+-------------------+--------------------+----------------------+
```

---

## Key Takeaways

1. **BST Invariant is Key**: Value comparison eliminates half the remaining nodes at every step.
2. **The Split Point is the LCA**: The first node where `p` and `q` diverge to opposite sides (or one equals `curr`) is mathematically guaranteed to be the lowest common ancestor.
3. **$O(1)$ Auxiliary Space**: Iterative pointer traversal avoids all recursion overhead.

---

## Interview Tips

- **How to explain**:
  > *"Because this is a Binary Search Tree, we can determine whether `p` and `q` are in the left subtree, right subtree, or split across the current node in $O(1)$ time. If both values are smaller than `curr.val`, we move left; if both are larger, we move right. The first node where they split—or where `curr` equals `p` or `q`—is the LCA. This runs in $O(h)$ time and $O(1)$ space iteratively."*
- **Follow-up question**: "What if this were a general Binary Tree without the BST property?"
  - *Answer*: "We would use post-order DFS to search both left and right subtrees in $O(n)$ time and $O(h)$ space."

---

## Related Problems

| Problem | Difficulty | Key Difference |
| :--- | :--- | :--- |
| **Lowest Common Ancestor of a Binary Tree** | Medium | No BST property; requires full DFS traversal ($O(n)$). |
| **Lowest Common Ancestor of a Binary Tree II** | Medium | `p` or `q` might not exist in the tree. |
| **Lowest Common Ancestor of a Binary Tree III** | Medium | Nodes have parent pointers ($O(1)$ space with two pointers). |
| **Lowest Common Ancestor of Deepest Leaves** | Medium | Finds LCA of all nodes at maximum depth. |

---

## Final Pattern Label

✅ **BST Divergence Navigation (Binary Search on Tree)**

**Summary**: Navigate from root using BST properties. Move left if both `p, q < curr.val`, move right if both `p, q > curr.val`, and return `curr` as LCA at the divergence split point in $O(h)$ time and $O(1)$ space.
