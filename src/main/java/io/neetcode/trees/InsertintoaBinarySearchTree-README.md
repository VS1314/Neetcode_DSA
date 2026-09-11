# Insert into a Binary Search Tree

## Problem Description

**Difficulty**: Medium

You are given the `root` node of a binary search tree (BST) and a value `val` to insert into the tree. Return the root node of the BST after the insertion. It is **guaranteed** that the new value does not exist in the original BST.

**Note:** There may exist multiple valid ways for the insertion, as long as the tree remains a BST after insertion. You can return any of them. The standard and simplest way is to insert the new node as a **new leaf node**.

**Key Concepts:**
- **BST Invariant**: For every node $N$, values in the left subtree are $< N.val$, and values in the right subtree are $> N.val$.
- **Leaf Insertion Principle**:
  - Compare `val` with `curr.val`.
  - If `val < curr.val`:
    - If `curr.left == null`, attach `new TreeNode(val)` to `curr.left` and finish.
    - Else, move `curr = curr.left`.
  - If `val > curr.val`:
    - If `curr.right == null`, attach `new TreeNode(val)` to `curr.right` and finish.
    - Else, move `curr = curr.right`.
- **Base Case (Empty Tree)**: If `root == null`, return `new TreeNode(val)`.

**Visual Overview:**
```
Original BST (val = 6):
             5
           /   \
          3     9
         / \
        1   4

Step 1: Compare 6 with root 5 -> 6 > 5 -> Move Right to Node 9
Step 2: Compare 6 with node 9 -> 6 < 9 -> Left child of 9 is null!
Step 3: Attach new TreeNode(6) to node 9's left child.

Resulting BST:
             5
           /   \
          3     9
         / \   /
        1   4 6
```

**Recommended Complexity**:
- Time: $O(h)$ where $h$ is the height of the BST ($O(\log n)$ average for balanced tree, $O(n)$ worst case for skewed tree).
- Space: $O(1)$ auxiliary space for iterative insertion, or $O(h)$ call stack for recursive insertion.

---

## Examples

### Example 1 (Standard Leaf Insertion):
```
Input: root = [5,3,9,1,4], val = 6

Tree before insertion:
        5
      /   \
     3     9
    / \
   1   4

Tree after insertion:
        5
      /   \
     3     9
    / \   /
   1   4 6

Output: [5,3,9,1,4,6]

Explanation:
6 > 5 -> go right to 9.
6 < 9 -> 9.left is null, attach 6 as left child of 9.
```

### Example 2 (Deep Skewed Insertion):
```
Input: root = [5,3,6,null,4,null,10,null,null,7], val = 9

Tree before insertion:
    5
   / \
  3   6
   \   \
    4   10
       /
      7

Tree after insertion:
    5
   / \
  3   6
   \   \
    4   10
       /
      7
       \
        9

Output: [5,3,6,null,4,null,10,null,null,7,null,null,9]

Explanation:
9 > 5 -> right (6) -> 9 > 6 -> right (10) -> 9 < 10 -> left (7) -> 9 > 7 -> right (null) -> attach 9 as 7.right.
```

### Example 3 (Empty Tree):
```
Input: root = [], val = 5

Output: [5]

Explanation:
Original tree is null, so new node with value 5 becomes the root.
```

### Example 4 (Single Node Tree - Insert Left):
```
Input: root = [4], val = 2

Output: [4,2]

Explanation:
2 < 4, attached to 4.left.
```

### Example 5 (Single Node Tree - Insert Right):
```
Input: root = [4], val = 7

Output: [4,null,7]

Explanation:
7 > 4, attached to 4.right.
```

### Example 6 (Insert Smallest Value in BST):
```
Input: root = [4,2,7,1,3], val = 0

Output: [4,2,7,1,3,0]

Explanation:
0 is smaller than all nodes, attached to the leftmost leaf 1.left.
```

### Example 7 (Insert Largest Value in BST):
```
Input: root = [4,2,7,1,3], val = 9

Output: [4,2,7,1,3,null,null,null,null,null,9]

Explanation:
9 is larger than all nodes, attached to the rightmost leaf 7.right.
```

### Example 8 (Insert into Fully Left-Skewed Tree):
```
Input: root = [4,3,null,2], val = 1

Tree:
    4
   /
  3
 /
2
/
1

Output: [4,3,null,2,null,1]
```

### Example 9 (Insert into Fully Right-Skewed Tree):
```
Input: root = [1,null,2,null,3], val = 4

Output: [1,null,2,null,3,null,4]
```

### Example 10 (Insert Intermediate Value between Internal Nodes):
```
Input: root = [8,3,10,1,6], val = 4

Tree after insertion:
      8
     / \
    3   10
   / \
  1   6
     /
    4

Output: [8,3,10,1,6,null,null,4]
```

---

## Constraints
- The number of nodes in the tree is in the range `[0, 10,000]`.
- `-100,000,000 <= val, Node.val <= 100,000,000`
- All `Node.val` are **unique**.
- It is **guaranteed** that `val` does not exist in the original BST.

---

## Pattern Recognition

**Primary Pattern**: **BST Property Navigation / Leaf Node Insertion**

**Why This Pattern?**
- BST structure dictates a unique path from root to the insertion point for any unique value.
- By comparing `val` with the current node value:
  - If `val < curr.val`, the new value must be placed in the left subtree.
  - If `val > curr.val`, the new value must be placed in the right subtree.
- When an empty slot (`null`) is found in the required direction, that empty slot is the exact valid location for the new leaf node.

```
       BST Insertion Decision:
       
       if (val < curr.val):
           if curr.left == null:
               curr.left = new TreeNode(val)
               break
           curr = curr.left
       else:
           if curr.right == null:
               curr.right = new TreeNode(val)
               break
           curr = curr.right
```

---

## Algorithm & Approach

### Approach 1: Iterative Traversal ($O(1)$ Space) — Recommended / Optimal

#### Method Explanation:
1. Handle base case: If `root == null`, return `new TreeNode(val)`.
2. Initialize pointer `curr = root`.
3. Loop infinitely (`while (true)`):
   - If `val < curr.val`:
     - If `curr.left == null`, set `curr.left = new TreeNode(val)` and return `root`.
     - Else, `curr = curr.left`.
   - Else (`val > curr.val`):
     - If `curr.right == null`, set `curr.right = new TreeNode(val)` and return `root`.
     - Else, `curr = curr.right`.

#### Java Code Implementation:
```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
public class InsertintoaBinarySearchTree {

    /**
     * Inserts a value into a BST and returns the root of the modified tree.
     * 
     * @param root The root node of the BST
     * @param val The value to insert
     * @return The root node of the BST after insertion
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // Base case: If tree is empty, new node is the root
        if (root == null) {
            return new TreeNode(val);
        }

        TreeNode curr = root;
        while (true) {
            if (val < curr.val) {
                if (curr.left == null) {
                    curr.left = new TreeNode(val);
                    break;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    curr.right = new TreeNode(val);
                    break;
                }
                curr = curr.right;
            }
        }

        return root;
    }
}
```

---

### Approach 2: Recursive DFS ($O(h)$ Call Stack Space) — Alternative

#### Method Explanation:
1. If `root == null`, return `new TreeNode(val)`.
2. If `val < root.val`, `root.left = insertIntoBST(root.left, val)`.
3. If `val > root.val`, `root.right = insertIntoBST(root.right, val)`.
4. Return `root`.

#### Java Code Implementation:
```java
public class InsertintoaBinarySearchTreeRecursive {

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (val < root.val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }

        return root;
    }
}
```

---

## Why This Strategy?

### Comparison of Approaches

| Criteria | Iterative (Approach 1) | Recursive (Approach 2) |
| :--- | :--- | :--- |
| **Time Complexity** | $O(h)$ | $O(h)$ |
| **Space Complexity** | $O(1)$ Auxiliary | $O(h)$ Call Stack |
| **Stack Overflow Risk**| None | Possible if $h \ge 10,000$ (tree skewed) |
| **Code Length** | ~18 lines | ~10 lines |
| **Recommendation** | ⭐ **Top Choice ($O(1)$ Space)** | Clean alternative |

---

## Critical Edge Cases & Gotchas

1. **Empty Tree (`root == null`)**:
   - Must return `new TreeNode(val)` immediately so that the caller gets the newly created root.
2. **Inserting into Single-Node Tree**:
   - `curr.left` or `curr.right` will be null immediately, correctly attaching at depth 1.
3. **Deep Skewed BST ($n = 10,000$)**:
   - Recursive approach may cause `StackOverflowError` if $N = 10,000$; iterative approach uses $O(1)$ memory and handles deep trees safely.
4. **Large Values (up to $10^8$)**:
   - Values fit cleanly in standard 32-bit signed integers.
5. **No Duplicate Values**:
   - Guaranteed by problem statement that `val` does not exist in the original tree.

---

## Major Areas Where We Might Go Wrong

### ❌ Mistake 1: Forgetting to Return the Root for Empty Tree
```java
// WRONG: Returning null when root is null
if (root == null) return null; // ❌ Loses the inserted node!
```
**Correction**: Return `new TreeNode(val)`.

---

### ❌ Mistake 2: Losing Child Links in Iteration
```java
// WRONG: Overwriting curr without setting parent's left/right pointer
while (curr != null) {
    if (val < curr.val) curr = curr.left;
    else curr = curr.right;
}
curr = new TreeNode(val); // ❌ Modifies local variable curr, does not attach to tree!
```
**Correction**: Check `curr.left == null` or `curr.right == null` before advancing, or track `parent`.

---

### ❌ Mistake 3: Inverting BST Comparison Logic
```java
// WRONG: Moving left when val is greater
if (val > curr.val) curr.left = new TreeNode(val); // ❌ Violates BST invariant!
```
**Correction**: `val < curr.val` $\implies$ left, `val > curr.val` $\implies$ right.

---

## Complexity Analysis

- **Time Complexity**: $O(h)$
  - Balanced Tree: $h = O(\log n)$ comparisons.
  - Skewed Tree (Worst Case): $h = O(n)$ comparisons where $n \le 10,000$.
  - At each step, a single comparison is performed and one level is descended.
- **Space Complexity**:
  - **Iterative Approach**: $O(1)$ auxiliary space.
  - **Recursive Approach**: $O(h)$ call stack space ($O(\log n)$ balanced, $O(n)$ skewed).

---

## Visualization

```
Insert val = 6 into BST:
             5
           /   \
          3     9
         / \
        1   4

Step 1: curr = 5
        6 > 5 -> curr.right is 9 (not null) -> curr = 9

Step 2: curr = 9
        6 < 9 -> curr.left is null!
        Set 9.left = new TreeNode(6)
        Break and return root (5).

Result:
             5
           /   \
          3     9
         / \   /
        1   4 6
```

---

## Comparison of Approaches

```
+-------------------+---------------------+-----------------------+
| Metric            | Iterative Solution  | Recursive Solution    |
+-------------------+---------------------+-----------------------+
| Time Complexity   | O(h)                | O(h)                  |
| Space Complexity  | O(1)                | O(h)                  |
| Memory Location   | Pointer only (heap) | Call stack per level  |
| Stack Safety      | 100% Safe           | Risk on N=10,000      |
+-------------------+---------------------+-----------------------+
```

---

## Key Takeaways

1. **Leaf Insertion is Sufficient**: Any valid BST insertion can always be placed as a leaf without needing tree rotations.
2. **One-Way Traversal**: Because `val` is unique, there is exactly one deterministic path down the tree to the insertion leaf position.
3. **$O(1)$ Space Iterative Pattern**: Checking `curr.left == null` / `curr.right == null` allows immediate attachment without needing a separate parent pointer.

---

## Interview Tips

- **How to explain**:
  > *"To insert a value into a BST while preserving the BST invariant, we can traverse down the tree from the root. At each node, we compare `val` with `curr.val`: if `val < curr.val`, we move left, and if `val > curr.val`, we move right. When we find an empty child pointer in the chosen direction, we attach the new `TreeNode(val)` as a leaf. An iterative implementation achieves this in $O(h)$ time and $O(1)$ space."*
- **Follow-up question**: "Could we insert the new node at the root instead of a leaf?"
  - *Answer*: "Yes, using tree rotations or by restructuring the tree, but leaf insertion is simpler, preserves existing topology, and achieves the same $O(h)$ complexity."

---

## Related Problems

| Problem | Difficulty | Relation |
| :--- | :--- | :--- |
| **Search in a Binary Search Tree** | Easy | Same navigation without the insertion step. |
| **Delete Node in a BST** | Medium | Inverse operation; requires handling node replacement with predecessor/successor. |
| **Kth Smallest Element in a BST** | Medium | Inorder traversal of BST. |
| **Validate Binary Search Tree** | Medium | Validates the BST property across all nodes. |

---

## Final Pattern Label

✅ **BST Binary Navigation & Leaf Insertion**

**Summary**: Traverse down the BST comparing `val` against node values. When a `null` child is encountered in the required direction (`left` if smaller, `right` if larger), attach `new TreeNode(val)` as a leaf and return the root in $O(h)$ time and $O(1)$ space.
