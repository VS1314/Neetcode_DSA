# Delete Node in a BST

## Problem Description

**Difficulty**: Medium

You are given a **root node reference of a BST** and a `key`. Delete the node with the given key in the BST, if present. Return the **root node reference (possibly updated)** of the BST.

Deletion can be divided into two stages:
1. **Search** for the node to remove.
2. **Delete** the node and restructure the BST.

**Note**: There can be multiple valid results after deleting the node — return any one of them.

## Examples

### Example 1:
```
Input: root = [5,3,9,1,4], key = 3

Before Deletion:          After Deletion (one valid answer):
        5                         5
       / \                       / \
      3   9                     4   9
     / \                       /
    1   4                     1

Output: [5,4,9,1]
Explanation: Node 3 has two children. Replace it with its in-order
             successor (smallest node in right subtree → 4), or with
             in-order predecessor. Here 4 replaces 3.
```

### Example 2:
```
Input: root = [5,3,6,null,4,null,10,null,null,7], key = 3

Before Deletion:               After Deletion:
        5                               5
       / \                             / \
      3   6                           4   6
       \   \                               \
        4   10                             10
           /                              /
          7                              7

Output: [5,4,6,null,null,null,10,7]
Explanation: Node 3 has one child (right child 4).
             Replace node 3 with its right child 4.
```

### Example 3:
```
Input: root = [], key = 0

Output: []
Explanation: Empty tree, nothing to delete.
```

## Constraints
- 0 <= The number of nodes in the tree <= 10,000
- -100,000 <= key, Node.val <= 100,000
- All the values `Node.val` are unique

---

## Pattern Recognition

**Primary Pattern**: **BST Property-Guided Search + Three-Case Node Deletion**

**Why This Pattern?**
- BST guarantees: left subtree values < node value < right subtree values
- We can **navigate directly** to the target node in O(h) time
- Once found, the deletion logic splits into **3 distinct cases** based on the node's children

**Key Insight**:
- If `key < current.val` → target node is in the **left subtree**
- If `key > current.val` → target node is in the **right subtree**
- If `key == current.val` → **found the node to delete** → apply 3-case deletion

**The 3 Deletion Cases:**
```
Found node to delete:
├─ Case 1: Node has NO children (leaf)
│   └─ Simply return null (remove the node)
│
├─ Case 2: Node has ONE child
│   ├─ Only left child  → return left child
│   └─ Only right child → return right child
│
└─ Case 3: Node has TWO children
    ├─ Find in-order successor (smallest in right subtree)
    ├─ Replace node's value with successor's value
    └─ Delete the successor from right subtree (recursive)
```

**Related Patterns**:
1. **Insert into a BST** — Same BST navigation, simpler operation
2. **Lowest Common Ancestor in BST** — Same BST left/right navigation
3. **Search in a Binary Search Tree** — Core BST navigation logic
4. **Validate Binary Search Tree** — BST property verification

---

## Algorithm & Approach

### Core Insight

**Why Three Cases?**
- A node with no children is trivial — just remove it.
- A node with one child is simple — the child takes its place.
- A node with two children is the hard case — we must **maintain BST order** without breaking the tree structure.

**The In-Order Successor Trick (for two children):**
- The **in-order successor** is the smallest value in the right subtree (go right once, then keep going left)
- It is always **greater** than the node being deleted and **less than** all other right-subtree nodes
- So we can safely **replace the deleted node's value** with the successor's value, then delete the successor (which has at most one child — no left child)

**Decision Flow:**
```
deleteNode(root, key):
    ├─ If root is null → return null (key not found)
    │
    ├─ If key < root.val
    │  └─ root.left = deleteNode(root.left, key)   ← search left
    │
    ├─ If key > root.val
    │  └─ root.right = deleteNode(root.right, key)  ← search right
    │
    └─ If key == root.val  (FOUND — apply 3 cases)
        ├─ No left child  → return root.right
        ├─ No right child → return root.left
        └─ Both children:
            ├─ Find min of right subtree (in-order successor)
            ├─ root.val = successor.val
            └─ root.right = deleteNode(root.right, successor.val)
    
    Return root
```

### Visual Understanding

```
Example 1: root = [5,3,9,1,4], key = 3  (TWO children case)

        5           Step 1: key=3 < 5 → go left
       / \
      3   9         Step 2: key=3 == 3 → FOUND (has two children: 1, 4)
     / \
    1   4           Step 3: Find in-order successor = min of right subtree = 4
                    Step 4: Replace node 3's value with 4 → node becomes 4
                    Step 5: Delete 4 from right subtree of node

After Deletion:
        5
       / \
      4   9
     /
    1   ← BST property still holds: 1 < 4 < 5 < 9 ✓
```

```
Example 2: root = [5,3,6,null,4,null,10], key = 3  (ONE child case)

        5           Step 1: key=3 < 5  → go left
       / \
      3   6         Step 2: key=3 == 3 → FOUND (only right child: 4)
       \   \
        4   10      Step 3: No left child → return right child (4)
                    Step 4: Node 4 replaces node 3

After Deletion:
        5
       / \
      4   6
           \
           10   ← BST property holds: 4 < 5 < 6 < 10 ✓
```

---

### Step-by-Step Algorithm

#### **Approach 1: Recursive (CLEAN & INTUITIVE)**

**Core Idea**:
- Use recursion to navigate to the target node via BST property
- Once found, handle the 3 deletion cases
- Return the updated subtree root back up the call stack — this re-links the parent automatically

**Algorithm**:
```
deleteNode(root, key):
    if root is null → return null
    if key < root.val → root.left  = deleteNode(root.left, key)
    if key > root.val → root.right = deleteNode(root.right, key)
    else (found):
        if root.left  is null → return root.right
        if root.right is null → return root.left
        // Two children: find in-order successor
        successor = findMin(root.right)
        root.val   = successor.val
        root.right = deleteNode(root.right, successor.val)
    return root
```

**Code Implementation**
```java
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        // Base case: key not found (or empty tree)
        if (root == null) {
            return null;
        }

        // Navigate: key is smaller → go left
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        }
        // Navigate: key is larger → go right
        else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        }
        // Found the node to delete
        else {
            // Case 1 & 2: No left child → replace with right child (covers leaf too)
            if (root.left == null) {
                return root.right;
            }
            // Case 2: No right child → replace with left child
            if (root.right == null) {
                return root.left;
            }
            // Case 3: Two children → find in-order successor (min of right subtree)
            TreeNode successor = findMin(root.right);
            root.val = successor.val;                              // Replace value
            root.right = deleteNode(root.right, successor.val);   // Delete successor
        }

        return root;  // Return updated node back up the call stack
    }

    // Helper: find the minimum node in a subtree (leftmost node)
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
```

**Example Walkthrough**

Input: root = [5,3,9,1,4], key = 3

```
Call Stack Visualization:

deleteNode(5, 3)
├─ 3 < 5 → go left
├─ root.left = deleteNode(3, 3)
│  ├─ 3 == 3 → FOUND
│  ├─ root.left (1) is not null
│  ├─ root.right (4) is not null → two-children case
│  ├─ successor = findMin(4) → node 4 (no left child)
│  ├─ root.val = 4  (node now holds value 4)
│  ├─ root.right = deleteNode(4, 4)
│  │  ├─ 4 == 4 → FOUND
│  │  ├─ root.left is null → return root.right (null)
│  │  └─ return null
│  ├─ node's right = null
│  └─ return updated node (val=4, left=1, right=null)
├─ node 5's left = updated node(4)
└─ return node 5

Result: [5,4,9,1]
```

**Step-by-Step Trace:**

| Step | Current Node | key vs node.val | Decision |
|------|-------------|----------------|----------|
| 1 | 5 | 3 < 5 | Go left |
| 2 | 3 | 3 == 3 | Found → two children |
| 3 | — | — | findMin(right=4) → successor = 4 |
| 4 | 3→4 | — | Replace val with 4, delete 4 from right subtree |
| 5 | 4 (old) | 4 == 4 | Found → no left child → return null |

**Final Result: [5,4,9,1]**

**Complexity Analysis**
- **Time Complexity**: O(h)
  - h = height of the BST
  - We traverse at most one root-to-leaf path to find the node
  - Deleting successor also takes O(h) in the worst case
  - Best case (balanced BST): O(log n)
  - Worst case (skewed BST): O(n)
- **Space Complexity**: O(h)
  - Recursive call stack depth = height of traversal path
  - Best case (balanced): O(log n)
  - Worst case (skewed): O(n)

---

#### **Approach 2: Iterative (OPTIMAL SPACE)**

**Core Idea**:
- Use a `parent` pointer and `current` pointer to walk to the target node
- Once found, handle the 3 deletion cases manually
- Re-link the parent to the correct replacement node

**Why Iterative?**
- Avoids recursive call stack — O(1) extra space (excluding result)
- No risk of stack overflow for very deep/skewed trees
- Same O(h) time complexity but better constant factor

**Code Implementation**
```java
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        TreeNode parent = null;
        TreeNode current = root;

        // Step 1: Search for the node to delete
        while (current != null && current.val != key) {
            parent = current;
            if (key < current.val) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        // Key not found
        if (current == null) {
            return root;
        }

        // Step 2: Delete the found node (3 cases)
        TreeNode replacement = getReplacement(current);

        // Re-link parent to replacement
        if (parent == null) {
            // Deleting root itself
            return replacement;
        }
        if (parent.left == current) {
            parent.left = replacement;
        } else {
            parent.right = replacement;
        }

        return root;
    }

    // Returns the node that should replace the deleted node
    private TreeNode getReplacement(TreeNode node) {
        // Case 1: Leaf node
        if (node.left == null && node.right == null) {
            return null;
        }
        // Case 2a: Only right child
        if (node.left == null) {
            return node.right;
        }
        // Case 2b: Only left child
        if (node.right == null) {
            return node.left;
        }
        // Case 3: Two children — find in-order successor
        // Attach left subtree of deleted node to leftmost of right subtree
        TreeNode rightSubtree = node.right;
        TreeNode leftmost = rightSubtree;
        while (leftmost.left != null) {
            leftmost = leftmost.left;
        }
        leftmost.left = node.left;  // Attach deleted node's left subtree
        return rightSubtree;
    }
}
```

**Example Walkthrough**

Input: root = [5,3,9,1,4], key = 3

```
BST Structure:
        5
       / \
      3   9
     / \
    1   4

Iteration Trace (Search Phase):

parent = null, current = 5  → 3 < 5  → go left,  parent = 5
parent = 5,    current = 3  → 3 == 3 → FOUND

Deletion Phase (Case 3 — two children):
  rightSubtree = 4
  leftmost = 4 (no left child of 4)
  leftmost.left = node 3's left = node 1
  replacement = node 4 (with left=1, right=null)

Re-link:
  parent (5).left was node 3 → now = node 4

Result: [5,4,9,1]  ✓
```

**Complexity Analysis**
- **Time Complexity**: O(h) — same as recursive
- **Space Complexity**: O(1) — only pointer variables, no call stack

---

## Comparison of Approaches

| Aspect | Recursive | Iterative |
|--------|-----------|-----------|
| **Time Complexity** | O(h) | O(h) |
| **Space Complexity** | O(h) | ✅ O(1) |
| **Code Simplicity** | ✅ Very Clean | Moderate |
| **Stack Overflow Risk** | Possible (deep tree) | ✅ None |
| **Handles All 3 Cases** | ✅ Yes | ✅ Yes |
| **Preferred?** | ✅ Great for interviews | ✅ Best for production |

**Recommendation**: Use **Recursive** in interviews (clean, easy to explain and trace). Use **Iterative** in production (better space, no risk with 10,000 nodes).

---

## Key Takeaways

1. **Three Cases for Deletion**
   - Leaf → return `null`
   - One child → return that child
   - Two children → replace with in-order successor, then delete successor

2. **In-Order Successor is the Key**
   - The smallest value in the right subtree preserves BST order
   - It always has at most one child (no left child), so deleting it is simple

3. **Recursive Return Value Re-links the Tree**
   - Returning `root` back up the call stack automatically re-attaches the updated subtree to its parent
   - This is the same elegant trick used in BST insertion

4. **Root Can Change**
   - Unlike insertion, deleting the root is possible
   - Handle this with `if (parent == null)` in iterative, or naturally in recursive

5. **BST Property Must Hold After Deletion**
   - In-order successor (min of right subtree) is the only value that can safely replace the deleted node
   - All left-subtree values < successor value < all right-subtree values ✓

---

## Common Pitfalls

❌ **Mistake 1**: Forgetting to assign the recursive return value
```java
// WRONG: updated subtree is returned but never linked to parent
if (key < root.val) deleteNode(root.left, key);
```
✅ **Correct**: Assign result back to the child
```java
if (key < root.val) root.left = deleteNode(root.left, key);
```

❌ **Mistake 2**: Not handling the case where key is not in the BST
```java
// WRONG: no null check — crashes on missing key
while (current.val != key) { current = current.left; }
```
✅ **Correct**: Check for null before accessing val
```java
while (current != null && current.val != key) { ... }
if (current == null) return root;  // key not found
```

❌ **Mistake 3**: Using in-order predecessor when you should find the minimum of the right subtree
```java
// CONFUSING: mixing predecessor (max of left) with successor (min of right)
// Both are valid replacements, but be consistent
```
✅ **Correct**: Pick one strategy and stick to it
```java
// Successor approach: go right once, then all the way left
TreeNode successor = findMin(root.right);
```

❌ **Mistake 4**: Not deleting the successor from the right subtree after copying its value
```java
// WRONG: value is copied but the original successor node is never removed
root.val = successor.val;
// forgot: root.right = deleteNode(root.right, successor.val);
```
✅ **Correct**: Always delete the successor after copying
```java
root.val = successor.val;
root.right = deleteNode(root.right, successor.val);  // ← critical!
```

---

## Related Problems

1. **Insert into a Binary Search Tree** (Medium) — Simpler BST modification, same navigation
2. **Lowest Common Ancestor in BST** (Medium) — Same BST left/right split logic
3. **Search in a Binary Search Tree** (Easy) — Core BST navigation used here
4. **Validate Binary Search Tree** (Medium) — Verify BST property holds after changes
5. **Kth Smallest Element in a BST** (Medium) — In-order traversal leverages same successor concept
6. **Trim a Binary Search Tree** (Medium) — Multiple deletions using same BST navigation

---

## Edge Cases to Consider

1. **Empty Tree (root = null)**
   ```
   Input: root = [], key = 5
   Result: []  ← nothing to delete, return null
   ```

2. **Key Not Present in BST**
   ```
   Input: root = [5,3,9], key = 7
   Path: 7 > 5 → right(9), 7 < 9 → left (null) → not found
   Result: [5,3,9]  ← tree unchanged
   ```

3. **Delete a Leaf Node (no children)**
   ```
   Input: root = [5,3,9,1,4], key = 1
   Node 1 is a leaf → return null → parent(3).left = null
   Result: [5,3,9,null,4]
   ```

4. **Delete Node with Only Left Child**
   ```
   Input: root = [5,3,9,1], key = 3
   Node 3 has only left child (1) → return left child (1)
   Result: [5,1,9]
   ```

5. **Delete Node with Only Right Child**
   ```
   Input: root = [5,3,9,null,4], key = 3
   Node 3 has only right child (4) → return right child (4)
   Result: [5,4,9]
   ```

6. **Delete Root Node (two children)**
   ```
   Input: root = [5,3,9,1,4], key = 5
   Node 5 is root with two children
   In-order successor = min of right subtree = 9 (no left child)
   Replace 5 with 9, delete 9 from right subtree
   Result: [9,3,null,1,4]  ← 9 becomes new root
   ```

7. **Delete Root Node (only right child)**
   ```
   Input: root = [5,null,9], key = 5
   Root has no left child → return right child (9)
   Result: [9]  ← 9 becomes new root
   ```

8. **Single Node Tree — Delete the Only Node**
   ```
   Input: root = [5], key = 5
   Node 5 is a leaf (no children) → return null
   Result: []  ← tree becomes empty
   ```

---

## Summary

**Problem**: Delete a node with a given key from a BST and return the root of the updated tree.

**Solution**:
- Use BST property to navigate: go left if `key < node.val`, right if `key > node.val`
- When found, apply the correct deletion case:
  - **Leaf**: return `null`
  - **One child**: return that child
  - **Two children**: replace with in-order successor value, then recursively delete the successor
- Return the updated root

**Time**: O(h) | **Space**: O(h) recursive / O(1) iterative

**Pattern**: BST property-guided search + three-case node deletion. The recursive approach elegantly re-links the tree via return values, the same technique used in BST insertion.

