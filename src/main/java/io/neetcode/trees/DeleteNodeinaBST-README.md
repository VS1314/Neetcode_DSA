# Delete Node in a BST

## Problem Description

**Difficulty**: Medium

Given the `root` of a BST and a `key`, delete the node with that value if it exists and return the root of the updated BST.

This is a standard BST modification problem: we search for the target node, then remove it while preserving the BST invariant.

**Key ideas:**
- BST property: left subtree values are smaller, right subtree values are larger.
- If the target node is a leaf, remove it directly.
- If it has one child, replace it with that child.
- If it has two children, replace it with its in-order successor or predecessor.

**Recommended complexity:**
- Time: $O(h)$
- Space: $O(h)$ recursive or $O(1)$ iterative

---

## Examples

### Example 1
```
Input: root = [5,3,9,1,4], key = 3
Output: [5,4,9,1]
```

`3` has two children? no, only left child `1` and right child `4`? Actually `3` has both children. A valid answer is to replace `3` with `4`.

### Example 2
```
Input: root = [5,3,6,null,4,null,10,null,null,7], key = 3
Output: [5,4,6,null,null,null,10,7]
```

Removing `3` with a right child `4` and no left child gives a valid BST.

### Example 3
```
Input: root = [7,3,18,1,4,10,22], key = 18
Output: [7,3,10,1,4,22]
```

### Example 4
```
Input: root = [5], key = 5
Output: []
```

### Example 5
```
Input: root = [2,1,3], key = 4
Output: [2,1,3]
```

---

## Constraints
- `0 <= number of nodes <= 10,000`
- `-100000 <= key, Node.val <= 100000`
- All values are unique

---

## Pattern Recognition

This is a classic **BST delete** problem.

At each node:
- if `key < root.val`, go left
- if `key > root.val`, go right
- if `key == root.val`, delete this node using the BST rules

The important pattern is: **remove the current node while preserving BST ordering**.

---

## Algorithm & Approach

### Recursive approach
```java
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
            return root;
        }
        if (key > root.val) {
            root.right = deleteNode(root.right, key);
            return root;
        }

        // found node to delete
        if (root.left == null) return root.right;
        if (root.right == null) return root.left;

        // two children: replace with inorder successor
        TreeNode successor = findMin(root.right);
        root.val = successor.val;
        root.right = deleteNode(root.right, successor.val);
        return root;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) node = node.left;
        return node;
    }
}
```

**Logic:**
- Node not found: return null / unchanged subtree.
- One child: return the child.
- Two children: copy successor value and delete successor from the right subtree.

---

## Why This Strategy?

BST deletion must keep inorder ordering valid.

Using inorder successor is the safest standard method because:
- it is always greater than the current node
- it is the smallest value in the right subtree
- it preserves the BST structure when inserted at the deleted node

This avoids breaking the ordering rule.

---

## Critical Edge Cases & Gotchas

1. `root == null` -> return `null`
2. `key` not present -> return original tree
3. deleting a leaf node
4. deleting a node with one child
5. deleting a node with two children
6. using successor vs predecessor both work
7. avoid accidentally discarding the right subtree when copying successor

---

## Major Areas Where We Might Go Wrong

### Mistake 1: deleting the wrong node
```java
if (key < root.val) root.left = deleteNode(root.left, key);
else if (key > root.val) root.right = deleteNode(root.right, key);
```
This is required; otherwise you may lose the search path.

### Mistake 2: forgetting to handle two children
```java
if (root.left == null) return root.right;
if (root.right == null) return root.left;
```
This is essential; a node with two children is the tricky case.

### Mistake 3: deleting successor incorrectly
```java
root.right = deleteNode(root.right, successor.val);
```
This removes the copied successor from the right subtree without breaking BST order.

---

## Complexity Analysis

- **Time:** $O(h)$, where `h` is the height of the BST
- **Space:** $O(h)$ recursion stack in recursive solution

Worst case is $O(n)$ for a skewed tree; average case is $O(\log n)$ for a balanced BST.

---

## Visualization

```
      5
     / \
    3   9
   / \   \
  1   4   10

Delete 3:
- 3 has two children
- successor in right subtree = 4
- replace 3 with 4

Result:
      5
     / \
    4   9
   /     \
  1       10
```

---

## Comparison of Approaches

| Approach | Time | Space | Notes |
|---|---:|---:|---|
| Recursive BST delete | $O(h)$ | $O(h)$ | Standard and clean |
| Iterative BST delete | $O(h)$ | $O(1)$ | More manual |
| General tree delete | $O(n)$ | $O(h)$ | Unnecessary for BST |

---

## Key Takeaways

1. Use BST ordering to guide the search.
2. Handle 0, 1, and 2 children separately.
3. For two children, replace with inorder successor or predecessor.
4. Keep the function recursive and return the updated subtree root.

---

## Interview Tips

> "I will use the BST property to navigate to the key. Once found, I handle three cases: leaf, one child, and two children. For two children, I replace the value with the inorder successor and delete that successor from the right subtree. This preserves the BST invariant and runs in $O(h)$ time."

---

## Related Problems

- Lowest Common Ancestor in BST
- Insert into a Binary Search Tree
- Search in a Binary Search Tree
- Validate Binary Search Tree

---

## Final Pattern Label

✅ **BST Search + Inorder Successor Replacement**

This problem is solved by searching via BST comparisons and deleting the node while preserving ordering through a successor-based replacement strategy.
