# Lowest Common Ancestor in Binary Search Tree

## Problem Description

**Difficulty**: Medium

Given a **binary search tree (BST)** where all node values are unique, and two nodes from the tree `p` and `q`, return the **lowest common ancestor (LCA)** of the two nodes.

The **lowest common ancestor** between two nodes `p` and `q` is the **lowest node in a tree T** such that both `p` and `q` are descendants. The ancestor is allowed to be a descendant of itself.

## Examples

### Example 1:
```
Input: root = [5,3,8,1,4,7,9,null,2], p = 3, q = 8

BST Structure:
            5
           / \
          3   8
         / \ / \
        1  4 7  9
          \
           2

Output: 5
Explanation: Node 3 is in the left subtree of 5, node 8 is in the right subtree.
             The split happens at node 5, so 5 is the LCA.
```

### Example 2:
```
Input: root = [5,3,8,1,4,7,9,null,2], p = 3, q = 4

BST Structure:
            5
           / \
          3   8
         / \ / \
        1  4 7  9
          \
           2

Output: 3
Explanation: Node 4 is in the right subtree of node 3.
             Since node 3 is an ancestor of node 4, and a node can be a
             descendant of itself, the LCA is 3.
```

### Example 3:
```
Input: root = [5,3,8,1,4,7,9,null,2], p = 1, q = 2

BST Structure:
            5
           / \
          3   8
         / \ / \
        1  4 7  9
          \
           2

Output: 3
Explanation: Node 1 is the left child of 3, node 2 is a descendant of 4 (right child of 3).
             The split happens at node 3, so 3 is the LCA.
```

## Constraints
- 2 <= The number of nodes in the tree <= 100
- -100 <= Node.val <= 100
- `p != q`
- `p` and `q` will both exist in the BST

---

## Pattern Recognition

**Primary Pattern**: **BST Property-Guided Traversal (Recursive/Iterative)**

**Why This Pattern?**
- A BST has a special property: left subtree values < node value < right subtree values
- We can use this to **navigate directly** toward the LCA without visiting all nodes
- No need for full tree traversal — the BST structure guides our path

**Key Insight**:
- If both `p` and `q` are **less than** current node → LCA is in the **left subtree**
- If both `p` and `q` are **greater than** current node → LCA is in the **right subtree**
- If they are on **different sides** (or one equals the current node) → current node is the **LCA**

**Pattern Breakdown:**
```
At each node, compare node.val with p.val and q.val:
├─ Both < node.val  → go left  (LCA is in left subtree)
├─ Both > node.val  → go right (LCA is in right subtree)
└─ Split / match    → current node is the LCA
```

**Related Patterns**:
1. **Binary Search Tree Traversal** - Using BST property to navigate
2. **Diameter of Binary Tree** - DFS with global state
3. **Balanced Binary Tree** - Post-order traversal checking properties
4. **Validate BST** - BST property verification

---

## Algorithm & Approach

### Core Insight

**BST Property makes LCA Easy:**
- In a regular binary tree, finding LCA requires checking all paths
- In a BST, the ordering property tells us exactly which direction to go

**Three Cases at Every Node:**

| Condition | Meaning | Action |
|-----------|---------|--------|
| `p.val < node.val && q.val < node.val` | Both nodes in left subtree | Go left |
| `p.val > node.val && q.val > node.val` | Both nodes in right subtree | Go right |
| Otherwise | Current node is the split point (or equals one of p/q) | Return current node |

**Why "Otherwise" is the LCA:**
- If values split across left and right → current node is where they diverge (LCA)
- If current node equals `p` or `q` → that node is ancestor of the other (since it exists in its subtree)

**Decision Flow:**
```
lcaBST(root, p, q):
    ├─ If root is null → return null
    │
    ├─ If both p.val < root.val AND q.val < root.val
    │  └─ LCA must be in left subtree → recurse left
    │
    ├─ If both p.val > root.val AND q.val > root.val
    │  └─ LCA must be in right subtree → recurse right
    │
    └─ Otherwise (split or match)
       └─ return root (current node is the LCA)
```

### Visual Understanding

```
Example: root = [5,3,8,1,4,7,9], p = 3, q = 8

            5         ← Start here
           / \
          3   8       p=3 is left of 5, q=8 is right of 5
         / \          → SPLIT at 5 → LCA = 5
        1   4
           / \
          7   9

Step 1: root = 5
  p.val = 3 < 5,  q.val = 8 > 5 → SPLIT → return node 5

Result: 5
```

```
Example: root = [5,3,8,1,4,7,9], p = 3, q = 4

            5         ← Start here
           / \
          3   8
         / \
        1   4
            
Step 1: root = 5
  p.val = 3 < 5,  q.val = 4 < 5 → Both left → go left

Step 2: root = 3
  p.val = 3 == 3 → current node IS p → return node 3

Result: 3
```

---

### Step-by-Step Algorithm

#### **Approach 1: Recursive (OPTIMAL)**

**Core Idea**:
- Recursively compare `p.val` and `q.val` with the current node's value
- Use BST property to navigate to the LCA
- Return current node as soon as a split or match is found

**Algorithm**:
```
lcaBST(root, p, q):
    if root is null → return null
    if p.val < root.val AND q.val < root.val → return lcaBST(root.left, p, q)
    if p.val > root.val AND q.val > root.val → return lcaBST(root.right, p, q)
    return root  ← split point or exact match
```

**Code Implementation**
```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base case: empty tree
        if (root == null) {
            return null;
        }

        // Both p and q are smaller → LCA is in the left subtree
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }

        // Both p and q are larger → LCA is in the right subtree
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }

        // Split point OR current node equals p or q → current node is LCA
        return root;
    }
}
```

**Example Walkthrough**

Input: root = [5,3,8,1,4,7,9,null,2], p = 3, q = 4

```
Call Stack Visualization:

lowestCommonAncestor(5, p=3, q=4)
├─ p.val=3 < root.val=5 ✓
├─ q.val=4 < root.val=5 ✓
├─ Both smaller → go left
└─ lowestCommonAncestor(3, p=3, q=4)
   ├─ p.val=3 == root.val=3 → NOT both smaller, NOT both larger
   └─ return node 3 ← LCA found!

Final Result: node 3
```

**Step-by-Step Trace:**

| Step | Current Node | p.val | q.val | Condition | Decision |
|------|-------------|-------|-------|-----------|----------|
| 1 | 5 | 3 | 4 | Both < 5 | Go left |
| 2 | 3 | 3 | 4 | p.val == node.val (split/match) | Return node 3 |

**Final Result: 3**

**Complexity Analysis**
- **Time Complexity**: O(h)
  - h = height of the BST
  - At each step, we go left or right — at most h steps
  - Best case (balanced BST): O(log n)
  - Worst case (skewed BST): O(n)
- **Space Complexity**: O(h)
  - Recursive call stack depth = height of traversal path
  - Best case (balanced): O(log n)
  - Worst case (skewed): O(n)

---

#### **Approach 2: Iterative (No Recursion Stack)**

**Core Idea**:
- Apply the same BST logic iteratively using a `while` loop
- Move the current pointer left or right until the split/match is found
- No recursive call stack — O(1) extra space (excluding input)

**Why Iterative?**
- Avoids recursion stack overhead
- Handles very deep/skewed trees without stack overflow
- Same time complexity but better constant factor in practice

**Code Implementation**
```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode current = root;

        while (current != null) {
            // Both p and q are smaller → LCA is in the left subtree
            if (p.val < current.val && q.val < current.val) {
                current = current.left;
            }
            // Both p and q are larger → LCA is in the right subtree
            else if (p.val > current.val && q.val > current.val) {
                current = current.right;
            }
            // Split point OR current node equals p or q → current node is LCA
            else {
                return current;
            }
        }

        return null; // Should never reach here (p and q guaranteed to exist)
    }
}
```

**Example Walkthrough**

Input: root = [5,3,8,1,4,7,9,null,2], p = 3, q = 8

```
BST Structure:
        5
       / \
      3   8
     / \ / \
    1  4 7  9
        \
         2

Iteration Trace:

current = 5
  p.val=3 < 5, q.val=8 > 5 → split → return node 5

Result: 5 (found in 1 step!)
```

**Iteration Table:**

| Iteration | current | p.val < current? | q.val < current? | Decision |
|-----------|---------|-----------------|-----------------|----------|
| 1 | 5 | 3 < 5 ✓ | 8 > 5 ✗ | Split → return 5 |

**Final Result: 5**

**Complexity Analysis**
- **Time Complexity**: O(h) — same as recursive
- **Space Complexity**: O(1) — only a pointer variable, no call stack

---

## Comparison of Approaches

| Aspect | Recursive | Iterative |
|--------|-----------|-----------|
| **Time Complexity** | O(h) | O(h) |
| **Space Complexity** | O(h) | ✅ O(1) |
| **Code Simplicity** | ✅ Very Simple | Simple |
| **Stack Overflow Risk** | Possible (very deep tree) | ✅ None |
| **Readability** | ✅ More intuitive | Equally clear |
| **Preferred?** | Great default | ✅ Best for production |

**Recommendation**: Use **Iterative** for production (better space). Use **Recursive** for interviews (more intuitive to explain).

---

## Key Takeaways

1. **BST Property is the Superpower**
   - Left < Node < Right at every level
   - This guides us to the LCA without full traversal
   - O(h) instead of O(n) unlike a regular binary tree

2. **Three Simple Cases**
   - Both smaller → go left
   - Both larger → go right
   - Otherwise → current node is the LCA

3. **Split = LCA**
   - When p and q are on different sides of a node, that node separates their paths
   - The first node where they "split" is their lowest common ancestor

4. **Node Can Be Its Own Ancestor**
   - If current node == p, then p is an ancestor of q (since q is guaranteed in the tree)
   - The constraint says a node is allowed to be a descendant of itself

5. **No Need to Find Both Nodes**
   - Unlike brute force, we don't need to trace full paths to p and q
   - BST property lets us stop as soon as we find the split/match

---

## Common Pitfalls

❌ **Mistake 1**: Using plain DFS without BST property (works, but O(n))
```java
// WRONG (for BST): Ignores BST property
if (root == p || root == q) return root;
TreeNode left  = lca(root.left, p, q);
TreeNode right = lca(root.right, p, q);
if (left != null && right != null) return root;
return left != null ? left : right;
```
✅ **Correct**: Use BST property to navigate in O(h)
```java
if (p.val < root.val && q.val < root.val) return lca(root.left, p, q);
if (p.val > root.val && q.val > root.val) return lca(root.right, p, q);
return root;
```

❌ **Mistake 2**: Only checking equality, missing the split condition
```java
// WRONG: doesn't handle split
if (root.val == p.val || root.val == q.val) return root;
```
✅ **Correct**: Split case (one on each side) is also LCA
```java
// The else/default case covers: split OR exact match
return root;
```

❌ **Mistake 3**: Wrong direction when using `min/max` shorthand
```java
// WRONG: going right when both are smaller
if (Math.min(p.val, q.val) > root.val) current = current.left;
```
✅ **Correct**: If both are smaller → go left; both larger → go right
```java
if (p.val < root.val && q.val < root.val) current = current.left;
if (p.val > root.val && q.val > root.val) current = current.right;
```

---

## Related Problems

1. **Lowest Common Ancestor of a Binary Tree** (Medium) - General tree, without BST property
2. **Same Binary Tree** (Easy) - Basic tree comparison
3. **Validate Binary Search Tree** (Medium) - BST property verification
4. **Kth Smallest Element in a BST** (Medium) - In-order traversal of BST
5. **Insert into a Binary Search Tree** (Medium) - BST insertion using same navigation
6. **Search in a Binary Search Tree** (Easy) - Core BST traversal pattern

---

## Edge Cases to Consider

1. **One Node is the LCA of the Other**
   ```
   root = [5,3,8], p = 3, q = 1 (1 is in subtree of 3)
   Result: 3 (node 3 is ancestor of node 1)
   ```

2. **LCA is the Root**
   ```
   root = [5,3,8], p = 3, q = 8
   p is left, q is right → split at root
   Result: 5
   ```

3. **Both Nodes are in the Left Subtree**
   ```
   root = [5,3,8,1,4], p = 1, q = 4
   Both < 5 → go left to 3
   1 < 3, 4 > 3 → split at 3
   Result: 3
   ```

4. **Both Nodes are in the Right Subtree**
   ```
   root = [5,3,8,null,null,7,9], p = 7, q = 9
   Both > 5 → go right to 8
   7 < 8, 9 > 8 → split at 8
   Result: 8
   ```

5. **p and q are Adjacent (Parent-Child)**
   ```
   root = [5,3,8], p = 5, q = 3
   p.val = 5 == root.val → current node IS p → return 5
   Result: 5
   ```

---

## Summary

**Problem**: Find the lowest common ancestor of two nodes `p` and `q` in a BST.

**Solution**:
- Traverse the BST using its ordering property
- If both nodes are smaller → go left; both larger → go right
- When they split or match the current node → current node is the LCA

**Time**: O(h) | **Space**: O(h) recursive / O(1) iterative

**Pattern**: BST property-guided navigation — the BST's sorted structure eliminates the need for full traversal, making this O(h) instead of O(n).

