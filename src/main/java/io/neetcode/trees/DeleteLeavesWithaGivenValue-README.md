# Delete Leaves With a Given Value

## Problem Description

**Difficulty**: Medium

You are given a **binary tree** `root` and an integer `target`. Delete all the **leaf nodes** with value equal to `target`.

**Note**: Once you delete a leaf node with value `target`, if its **parent node becomes a leaf** and also has value `target`, it should also be deleted. Continue doing this until no more deletions are possible.

## Examples

### Example 1:
```
Input: root = [1,2,3,5,2,2,5], target = 2

Before Deletion:
          1
        /   \
       2     3
      / \   / \
     5   2 2   5

After Deletion:
          1
        /   \
       2     3
      /       \
     5         5

Output: [1,2,3,5,null,null,5]
Explanation: First pass: delete leaf nodes with value 2 → nodes at 5,2,2 level
             Node 2 (right child of root's left) is a leaf with value 2 → deleted
             Node 2 (left child of root's right) is a leaf with value 2 → deleted
             Now root's right child (3) had its left removed. Left child of root (2)
             still has a child (5) so it remains.
```

### Example 2:
```
Input: root = [3,null,3,3], target = 3

Before Deletion:
    3
     \
      3
     /
    3

Step 1: Delete leaf node 3 (bottom)  →  parent 3 becomes a leaf with value 3
Step 2: Delete that leaf 3           →  root 3 becomes a leaf with value 3
Step 3: Delete root 3                →  tree is empty

Output: []
Explanation: All nodes are eventually deleted.
```

## Constraints
- 1 <= number of nodes in the tree <= 3000
- 1 <= Node.val, target <= 1000

---

## Pattern Recognition

**Primary Pattern**: **Post-Order DFS (Bottom-Up Recursion)**

**Why This Pattern?**
- We must process **children before parents** — a node can only become a "new leaf" after its children are removed
- Post-order traversal (left → right → current) naturally handles this bottom-up deletion
- At each node, after recursing on both children, we check if the current node has become a leaf with the target value

**Key Insight**:
```
A node should be deleted if:
    1. It is a LEAF (both children are null after recursion)
    AND
    2. Its value equals target

If we return null for such a node, the parent automatically loses that child.
```

**Related Patterns**:
1. **Maximum Depth of Binary Tree** — Same post-order DFS structure
2. **Balanced Binary Tree** — Bottom-up height computation
3. **Diameter of Binary Tree** — Post-order with information passed upward

---

## Algorithm & Approach

### Core Insight

**Why Post-Order (Bottom-Up)?**

If we used pre-order (top-down), we would delete leaves in the current pass but miss the cascading deletions — nodes that become new leaves only after their children are deleted. Post-order handles all cases in a **single traversal**.

**Decision Flow:**
```
removeLeafNodes(node, target):
    ├─ If node is null → return null
    │
    ├─ Recurse LEFT:  node.left  = removeLeafNodes(node.left, target)
    ├─ Recurse RIGHT: node.right = removeLeafNodes(node.right, target)
    │
    └─ After children processed:
        ├─ If node is now a LEAF (left == null && right == null)
        │   AND node.val == target
        │   └─ return null  ← delete this node
        └─ else return node ← keep this node
```

### Visual Understanding

```
Example 1: root = [1,2,3,5,2,2,5], target = 2

Original Tree:
          1
        /   \
       2     3
      / \   / \
     5   2 2   5

Post-Order Traversal (process children first):

Visit node 5 (leaf, val≠2) → keep → return node 5
Visit node 2 (leaf, val==2) → DELETE → return null
  → node 2 (root's left child) now has: left=5, right=null

Visit node 2 (root's left child, val==2):
  Has left child (5) → NOT a leaf → keep → return this node

Visit node 2 (leaf, val==2) → DELETE → return null
  → node 3 now has: left=null
Visit node 5 (leaf, val≠2) → keep → return node 5
  → node 3 now has: left=null, right=5

Visit node 3 (val≠2) → keep → return node 3

Visit node 1 (root, val≠2) → keep → return node 1

Final Tree:
          1
        /   \
       2     3
      /       \
     5         5
```

```
Example 2: root = [3,null,3,3], target = 3

Original Tree:
    3 (root)
     \
      3
     /
    3 (leaf)

Post-Order Traversal:

Visit 3 (bottom leaf, val==3) → DELETE → return null
  → middle node 3: left=null, right=null → now a leaf with val==3

Visit middle 3 (now leaf, val==3) → DELETE → return null
  → root 3: right=null → now a leaf with val==3

Visit root 3 (now leaf, val==3) → DELETE → return null

Result: null (empty tree)
Output: []
```

---

### Step-by-Step Algorithm

#### **Approach 1: Recursive Post-Order DFS (CLEAN & INTUITIVE)**

**Core Idea**:
- Recurse to the deepest nodes first (post-order)
- After processing children, check if the current node has become a deletable leaf
- Returning `null` automatically unlinks the node from its parent

**Algorithm**:
```
removeLeafNodes(root, target):
    if root is null → return null
    root.left  = removeLeafNodes(root.left,  target)   ← process left subtree
    root.right = removeLeafNodes(root.right, target)   ← process right subtree
    if root.left == null && root.right == null && root.val == target:
        return null   ← delete this node
    return root       ← keep this node
```

**Code Implementation**
```java
class Solution {
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        // Base case: null node
        if (root == null) {
            return null;
        }

        // Post-order: process children FIRST
        root.left  = removeLeafNodes(root.left,  target);
        root.right = removeLeafNodes(root.right, target);

        // After children are processed, check if current node is a deletable leaf
        if (root.left == null && root.right == null && root.val == target) {
            return null;  // Delete this node by returning null to parent
        }

        return root;  // Keep this node
    }
}
```

**Example Walkthrough**

Input: root = [1,2,3,5,2,2,5], target = 2

```
Call Stack (Post-Order):

removeLeafNodes(1, 2)
├─ root.left  = removeLeafNodes(2_left, 2)
│  ├─ root.left  = removeLeafNodes(5, 2)
│  │  ├─ left  = removeLeafNodes(null) → null
│  │  ├─ right = removeLeafNodes(null) → null
│  │  └─ leaf but val=5 ≠ 2 → return node 5
│  ├─ root.right = removeLeafNodes(2_inner, 2)
│  │  ├─ left  = removeLeafNodes(null) → null
│  │  ├─ right = removeLeafNodes(null) → null
│  │  └─ leaf and val=2 == 2 → return null  ← DELETED
│  ├─ 2_left now: left=5, right=null → NOT a leaf → return node 2_left
│
├─ root.right = removeLeafNodes(3, 2)
│  ├─ root.left  = removeLeafNodes(2_right, 2)
│  │  ├─ left  = null, right = null → leaf and val=2 → return null ← DELETED
│  ├─ root.right = removeLeafNodes(5_right, 2)
│  │  └─ leaf but val=5 ≠ 2 → return node 5
│  └─ node 3: left=null, right=5, val=3 ≠ 2 → return node 3
│
└─ node 1: val=1 ≠ 2 → return node 1
```

**Step-by-Step Trace:**

| Step | Node | val==target? | Is Leaf After Recursion? | Action |
|------|------|--------------|--------------------------|--------|
| 1 | 5 (leftmost) | No | Yes | Keep |
| 2 | 2 (inner left) | Yes | Yes | **Delete** → null |
| 3 | 2 (root's left) | Yes | No (has child 5) | Keep |
| 4 | 2 (root's right's left) | Yes | Yes | **Delete** → null |
| 5 | 5 (root's right's right) | No | Yes | Keep |
| 6 | 3 | No | No | Keep |
| 7 | 1 (root) | No | No | Keep |

**Final Result: [1,2,3,5,null,null,5]**

**Complexity Analysis**
- **Time Complexity**: O(n)
  - Every node is visited exactly once in the post-order traversal
- **Space Complexity**: O(h)
  - Recursive call stack depth equals the height of the tree
  - Best case (balanced): O(log n)
  - Worst case (skewed): O(n)

---

#### **Approach 2: Iterative Post-Order using Stack**

**Core Idea**:
- Simulate post-order traversal iteratively using a stack
- Use a `prev` pointer to track the last visited node (to detect when we return from the right child)
- After processing both children of a node, check if it became a deletable leaf and re-link the parent

**Why Iterative?**
- Avoids recursion stack overflow for deeply skewed trees
- Explicit control over traversal order

**Code Implementation**
```java
class Solution {
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null) return null;

        // Use a parent map to re-link nodes
        // Easier approach: use a dummy root to avoid special-casing the root
        TreeNode dummy = new TreeNode(0);
        dummy.left = root;

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode prev = null;
        TreeNode curr = dummy;

        while (curr != null || !stack.isEmpty()) {
            // Go as far left as possible
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.peek();

            // If right subtree exists and hasn't been processed yet
            if (curr.right != null && curr.right != prev) {
                curr = curr.right;
                continue;
            }

            // Process current node (both children done)
            stack.pop();

            // Check if current is a deletable leaf
            if (curr.left == null && curr.right == null && curr.val == target) {
                // Remove from parent
                TreeNode parent = stack.isEmpty() ? null : stack.peek();
                if (parent != null) {
                    if (parent.left == curr)  parent.left  = null;
                    else                       parent.right = null;
                }
            }

            prev = curr;
            curr = null;
        }

        return dummy.left;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) — each node visited once
- **Space Complexity**: O(h) — stack holds at most h nodes at any time

---

## Comparison of Approaches

| Aspect | Recursive (Post-Order) | Iterative (Stack) |
|--------|----------------------|-------------------|
| **Time Complexity** | O(n) | O(n) |
| **Space Complexity** | O(h) | O(h) |
| **Code Simplicity** | ✅ Very Clean (5 lines) | Moderate (verbose) |
| **Stack Overflow Risk** | Possible (deep tree) | ✅ None |
| **Cascading Deletion** | ✅ Handled naturally | ✅ Handled |
| **Preferred?** | ✅ Best for interviews | For deep skewed trees |

**Recommendation**: Use **Recursive** in interviews — it's elegant, short, and easy to explain. The post-order nature of recursion handles cascading deletions automatically.

---

## Key Takeaways

1. **Post-Order is Essential**
   - Children must be processed before the parent
   - Only post-order guarantees that when we check a node, its subtree is already cleaned up

2. **Returning null Unlinks the Node**
   - `root.left = removeLeafNodes(root.left, target)` — if the call returns `null`, the parent's child pointer is set to `null`, effectively removing the node

3. **Cascading Deletion is Automatic**
   - Because we process bottom-up, a node that becomes a leaf only after its child is deleted will be checked in the same traversal pass — no second pass needed

4. **Single Pass is Sufficient**
   - Unlike an iterative approach that might need multiple passes, recursive post-order handles all cascading deletions in one traversal

---

## Common Pitfalls

❌ **Mistake 1**: Using pre-order (top-down) traversal
```java
// WRONG: deletes current leaf but misses cascading effect
if (root.val == target && isLeaf(root)) { return null; }
root.left  = removeLeafNodes(root.left, target);
root.right = removeLeafNodes(root.right, target);
```
✅ **Correct**: Process children FIRST (post-order)
```java
root.left  = removeLeafNodes(root.left,  target);
root.right = removeLeafNodes(root.right, target);
if (root.left == null && root.right == null && root.val == target) return null;
```

❌ **Mistake 2**: Only checking `val == target` without checking if it's a leaf
```java
// WRONG: deletes internal nodes that shouldn't be deleted
if (root.val == target) return null;
```
✅ **Correct**: Both conditions required
```java
if (root.left == null && root.right == null && root.val == target) return null;
```

❌ **Mistake 3**: Not assigning recursive return values back to children
```java
// WRONG: deletions in subtree are lost
removeLeafNodes(root.left, target);   // return value ignored!
removeLeafNodes(root.right, target);  // return value ignored!
```
✅ **Correct**: Always assign back
```java
root.left  = removeLeafNodes(root.left,  target);
root.right = removeLeafNodes(root.right, target);
```

---

## Related Problems

1. **Maximum Depth of Binary Tree** (Easy) — Same post-order DFS pattern
2. **Diameter of Binary Tree** (Easy) — Post-order with value propagation upward
3. **Balanced Binary Tree** (Easy) — Bottom-up height checking
4. **Delete Node in a BST** (Medium) — Node deletion with structural re-linking
5. **Pruning a Binary Tree** (similar concept) — Removing subtrees based on values
6. **Count Good Nodes in Binary Tree** (Medium) — DFS with value tracking

---

## Edge Cases to Consider

1. **Empty Tree**
   ```
   Input: root = [], target = 2
   Result: []  ← null returned immediately
   ```

2. **Single Node — Matches Target**
   ```
   Input: root = [2], target = 2
   Node 2 is a leaf with val == 2 → return null
   Result: []
   ```

3. **Single Node — Does Not Match**
   ```
   Input: root = [1], target = 2
   Node 1 is a leaf but val ≠ 2 → keep
   Result: [1]
   ```

4. **All Nodes Match Target — Full Tree Deleted**
   ```
   Input: root = [3,3,3], target = 3
   Leaves 3,3 deleted first → root 3 becomes a leaf → deleted
   Result: []
   ```

5. **Root Matches But Has Children That Don't**
   ```
   Input: root = [2,1,1], target = 2
   Leaves 1,1 don't match → kept
   Root 2: has children → NOT a leaf → kept
   Result: [2,1,1]  ← root 2 is preserved
   ```

6. **Deep Cascading Deletion**
   ```
   Input: root = [3,null,3,3], target = 3
   Bottom 3 → deleted
   Middle 3 becomes leaf → deleted
   Root 3 becomes leaf → deleted
   Result: []
   ```

---

## Summary

**Problem**: Delete all leaf nodes with value equal to `target`, cascading upward as nodes become new leaves.

**Solution**:
- Use **post-order DFS** (process left → right → current)
- After both children are processed, check if the current node is now a leaf with `val == target`
- Return `null` to delete it, or return the node to keep it
- The recursive return value automatically re-links (or unlinks) the node from its parent

**Time**: O(n) | **Space**: O(h)

**Pattern**: Post-Order DFS. Processing children before the parent is the key — it ensures that cascading deletions are handled in a single traversal pass without any re-processing.

