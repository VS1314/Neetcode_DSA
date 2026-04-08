# Insert into a Binary Search Tree

## Problem Description

**Difficulty**: Medium

You are given the **root node of a binary search tree (BST)** and a value `val` to insert into the tree. Return the **root node of the BST** after the insertion. It is guaranteed that the new value does not exist in the original BST.

**Note**: There may exist multiple valid ways for the insertion, as long as the tree remains a BST after insertion. You can return any of them.

## Examples

### Example 1:
```
Input: root = [5,3,9,1,4], val = 6

Before Insertion:          After Insertion:
        5                         5
       / \                       / \
      3   9                     3   9
     / \                       / \ /
    1   4                     1  4 6

Output: [5,3,9,1,4,6]
Explanation: 6 < 9 → go left of 9 → insert as left child of 9.
```

### Example 2:
```
Input: root = [5,3,6,null,4,null,10,null,null,7], val = 9

Before Insertion:               After Insertion:
        5                               5
       / \                             / \
      3   6                           3   6
       \   \                           \   \
        4   10                          4   10
           /                               /
          7                               7
                                           \
                                            9

Output: [5,3,6,null,4,null,10,null,null,7,null,null,9]
Explanation: 9 > 5 → right, 9 > 6 → right, 9 < 10 → left,
             9 > 7 → insert as right child of 7.
```

### Example 3:
```
Input: root = [], val = 5

Output: [5]
Explanation: Empty tree → new node becomes the root.
```

## Constraints
- 0 <= The number of nodes in the tree <= 10,000
- -100,000,000 <= val, Node.val <= 100,000,000
- All the values `Node.val` are unique
- It is guaranteed that `val` does not exist in the original BST

---

## Pattern Recognition

**Primary Pattern**: **BST Property-Guided Traversal + Leaf Insertion**

**Why This Pattern?**
- A BST guarantees: left subtree values < node value < right subtree values
- We can **navigate directly** to the correct insertion position in O(h) time
- Every new node can always be inserted as a **leaf node** — no restructuring needed

**Key Insight**:
- If `val < current.val` → new node belongs in the **left subtree**
- If `val > current.val` → new node belongs in the **right subtree**
- When we reach a `null` child → that is the **correct insertion spot**

**Pattern Breakdown:**
```
At each node, compare val with current.val:
├─ val < current.val  → go left
│   ├─ If left is null → insert here
│   └─ Else → recurse/iterate left
├─ val > current.val  → go right
│   ├─ If right is null → insert here
│   └─ Else → recurse/iterate right
└─ Always insert as a leaf node
```

**Related Patterns**:
1. **Lowest Common Ancestor in BST** - Same BST navigation logic
2. **Search in a Binary Search Tree** - Navigate left/right by comparing values
3. **Delete Node in a BST** - Uses same traversal, more complex restructuring
4. **Validate Binary Search Tree** - BST property verification

---

## Algorithm & Approach

### Core Insight

**Why Every Insertion Lands at a Leaf:**
- In a BST, for any value `val`, there is exactly one position where it can be correctly placed
- Following the BST property at each node, we are always guided to the unique correct empty slot
- That slot is always a missing left or right child — i.e., a **leaf position**

**Decision Flow:**
```
insertBST(root, val):
    ├─ If root is null
    │  └─ Return new TreeNode(val)  ← base case: found the insertion spot
    │
    ├─ If val < root.val
    │  └─ root.left = insertBST(root.left, val)  ← go left
    │
    └─ If val > root.val
       └─ root.right = insertBST(root.right, val)  ← go right
    
    Return root  ← return unchanged node back up the call stack
```

### Visual Understanding

```
Example: root = [5,3,9,1,4], val = 6

        5           Step 1: val=6 > 5 → go right
       / \
      3   9         Step 2: val=6 < 9 → go left
     / \
    1   4           Step 3: root.left is null → INSERT here

After Insertion:
        5
       / \
      3   9
     / \ /
    1  4 6   ← new node 6 inserted as left child of 9
```

```
Example: root = [5,3,6,null,4,null,10,null,null,7], val = 9

        5           Step 1: val=9 > 5  → go right
       / \
      3   6         Step 2: val=9 > 6  → go right
       \   \
        4   10      Step 3: val=9 < 10 → go left
           /
          7         Step 4: val=9 > 7  → go right (null) → INSERT

After Insertion:
        5
       / \
      3   6
       \   \
        4   10
           /
          7
           \
            9   ← new node 9 inserted as right child of 7
```

---

### Step-by-Step Algorithm

#### **Approach 1: Recursive (CLEAN & INTUITIVE)**

**Core Idea**:
- At each node, decide left or right using BST property
- Recursively go down until reaching `null`
- At `null`, create and return the new node
- Returning back up the stack re-links the parent to the new subtree

**Algorithm**:
```
insertIntoBST(root, val):
    if root is null → return new TreeNode(val)
    if val < root.val → root.left  = insertIntoBST(root.left,  val)
    if val > root.val → root.right = insertIntoBST(root.right, val)
    return root
```

**Code Implementation**
```java
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // Base case: found the insertion spot (null child)
        if (root == null) {
            return new TreeNode(val);
        }

        // val is smaller → insert into left subtree
        if (val < root.val) {
            root.left = insertIntoBST(root.left, val);
        }
        // val is larger → insert into right subtree
        else {
            root.right = insertIntoBST(root.right, val);
        }

        // Return current node (unchanged) back up the call stack
        return root;
    }
}
```

**Example Walkthrough**

Input: root = [5,3,9,1,4], val = 6

```
Call Stack Visualization:

insertIntoBST(5, 6)
├─ 6 > 5 → go right
├─ root.right = insertIntoBST(9, 6)
│  ├─ 6 < 9 → go left
│  ├─ root.left = insertIntoBST(null, 6)
│  │  └─ root is null → return new TreeNode(6) ✓
│  ├─ node 9's left = node 6
│  └─ return node 9
├─ node 5's right = node 9 (unchanged)
└─ return node 5 (root)

Result: root unchanged, new node 6 added as left child of 9
```

**Step-by-Step Trace:**

| Step | Current Node | val vs node.val | Decision |
|------|-------------|----------------|----------|
| 1 | 5 | 6 > 5 | Go right |
| 2 | 9 | 6 < 9 | Go left |
| 3 | null | — | Insert new node(6) here ✓ |

**Final Result: [5,3,9,1,4,6]**

**Complexity Analysis**
- **Time Complexity**: O(h)
  - h = height of the BST
  - We traverse at most one root-to-leaf path
  - Best case (balanced BST): O(log n)
  - Worst case (skewed BST): O(n)
- **Space Complexity**: O(h)
  - Recursive call stack depth = height of traversal path
  - Best case (balanced): O(log n)
  - Worst case (skewed): O(n)

---

#### **Approach 2: Iterative (OPTIMAL SPACE)**

**Core Idea**:
- Use a `current` pointer to walk the BST
- Track the `parent` node so we can attach the new node
- When `current` becomes `null`, we've found the insertion spot
- Attach the new node to the correct side of `parent`

**Why Iterative?**
- Avoids recursive call stack — O(1) extra space
- No risk of stack overflow for very deep/skewed trees
- Same O(h) time complexity but better constant factor

**Code Implementation**
```java
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // Edge case: empty tree
        if (root == null) {
            return new TreeNode(val);
        }

        TreeNode current = root;

        while (true) {
            // val is smaller → go left
            if (val < current.val) {
                if (current.left == null) {
                    current.left = new TreeNode(val);  // Insert here
                    break;
                }
                current = current.left;
            }
            // val is larger → go right
            else {
                if (current.right == null) {
                    current.right = new TreeNode(val);  // Insert here
                    break;
                }
                current = current.right;
            }
        }

        return root;  // Root is always unchanged
    }
}
```

**Example Walkthrough**

Input: root = [5,3,6,null,4,null,10,null,null,7], val = 9

```
BST Structure:
        5
       / \
      3   6
       \   \
        4   10
           /
          7

Iteration Trace:

current = 5  → 9 > 5  → go right,  right exists
current = 6  → 9 > 6  → go right,  right exists
current = 10 → 9 < 10 → go left,   left exists
current = 7  → 9 > 7  → go right,  right is null → INSERT 9 here

Result: node 9 added as right child of node 7
```

**Iteration Table:**

| Iteration | current | val vs current.val | Direction | Child exists? |
|-----------|---------|-------------------|-----------|--------------|
| 1 | 5 | 9 > 5 | Right | Yes → continue |
| 2 | 6 | 9 > 6 | Right | Yes → continue |
| 3 | 10 | 9 < 10 | Left | Yes → continue |
| 4 | 7 | 9 > 7 | Right | No → **Insert!** |

**Final Result: [5,3,6,null,4,null,10,null,null,7,null,null,9]**

**Complexity Analysis**
- **Time Complexity**: O(h) — same as recursive
- **Space Complexity**: O(1) — only pointer variables, no call stack

---

## Comparison of Approaches

| Aspect | Recursive | Iterative |
|--------|-----------|-----------|
| **Time Complexity** | O(h) | O(h) |
| **Space Complexity** | O(h) | ✅ O(1) |
| **Code Simplicity** | ✅ Very Simple | Simple |
| **Stack Overflow Risk** | Possible (very deep tree) | ✅ None |
| **Return Value** | Returns root via call stack | Returns root directly |
| **Preferred?** | ✅ Great for interviews | ✅ Best for production |

**Recommendation**: Use **Recursive** in interviews (clean, easy to explain). Use **Iterative** in production (better space, no stack risk with 10,000 nodes).

---

## Key Takeaways

1. **Always Insert at a Leaf**
   - A new BST node always lands at a `null` child slot
   - No restructuring or rotation is ever needed
   - This is what makes BST insertion simple and O(h)

2. **BST Property Guides the Path**
   - `val < node.val` → always go left
   - `val > node.val` → always go right
   - The path is deterministic — no backtracking needed

3. **Recursive Return Value is the Trick**
   - Returning `root` back up the call stack automatically re-links the tree
   - When `null` is reached, `new TreeNode(val)` is returned and linked to its parent

4. **Root Never Changes (in Standard Insertion)**
   - Leaf insertion never displaces the existing root
   - Always safe to return the original `root`

5. **Multiple Valid Answers**
   - The problem allows any valid BST after insertion
   - The standard leaf insertion is the simplest valid approach

---

## Common Pitfalls

❌ **Mistake 1**: Forgetting to assign the recursive return value
```java
// WRONG: new node is created but never linked
if (val < root.val) insertIntoBST(root.left, val);
```
✅ **Correct**: Assign result back to the child
```java
if (val < root.val) root.left = insertIntoBST(root.left, val);
```

❌ **Mistake 2**: Not returning `root` at the end of recursion
```java
// WRONG: parent loses reference to the subtree
if (val < root.val) {
    root.left = insertIntoBST(root.left, val);
}
// forgot to return root!
```
✅ **Correct**: Always return current node
```java
if (val < root.val) root.left = insertIntoBST(root.left, val);
else root.right = insertIntoBST(root.right, val);
return root;  // ← critical!
```

❌ **Mistake 3**: Using `==` comparison instead of `<` / `>`
```java
// WRONG: only handles exact match (which can't happen per constraints)
if (root.val == val) return root;
```
✅ **Correct**: Navigate by less-than / greater-than
```java
if (val < root.val) root.left  = insertIntoBST(root.left,  val);
else               root.right = insertIntoBST(root.right, val);
```

❌ **Mistake 4**: In iterative version, not returning original root
```java
// WRONG: returns the node where insertion happened, not the root
return current;
```
✅ **Correct**: Save and return original root
```java
TreeNode current = root;
// ... loop ...
return root;  // original root, always unchanged
```

---

## Related Problems

1. **Lowest Common Ancestor in BST** (Medium) - Same BST navigation pattern
2. **Search in a Binary Search Tree** (Easy) - Core BST left/right navigation
3. **Delete Node in a BST** (Medium) - Insert's counterpart, more complex restructuring
4. **Validate Binary Search Tree** (Medium) - Verify BST property holds
5. **Kth Smallest Element in a BST** (Medium) - In-order traversal of BST
6. **Construct BST from Preorder Traversal** (Medium) - Builds BST using repeated insertion

---

## Edge Cases to Consider

1. **Empty Tree (root = null)**
   ```
   Input: root = [], val = 5
   Result: [5]  ← new node becomes the root
   ```

2. **Insert Smaller than All Nodes (goes all the way left)**
   ```
   Input: root = [5,3,9,1,4], val = 0
   Path: 0 < 5 → left, 0 < 3 → left, 0 < 1 → left (null) → insert
   Result: 0 becomes left child of 1
   ```

3. **Insert Larger than All Nodes (goes all the way right)**
   ```
   Input: root = [5,3,9,1,4], val = 100
   Path: 100 > 5 → right, 100 > 9 → right (null) → insert
   Result: 100 becomes right child of 9
   ```

4. **Insert into Single-Node Tree**
   ```
   Input: root = [5], val = 3
   Path: 3 < 5 → left (null) → insert
   Result: [5,3]  ← 3 becomes left child of 5
   ```

5. **Insert Directly Adjacent to Root**
   ```
   Input: root = [5,3,9], val = 7
   Path: 7 > 5 → right (9), 7 < 9 → left (null) → insert
   Result: 7 becomes left child of 9
   ```

6. **Skewed Tree (worst case — O(n) depth)**
   ```
   Input: root = [1,null,2,null,3,null,4], val = 5
   Path: 5 > 1 → right, 5 > 2 → right, 5 > 3 → right, 5 > 4 → right (null) → insert
   Result: 5 becomes right child of 4
   ```

---

## Summary

**Problem**: Insert a value into a BST and return the root of the updated tree.

**Solution**:
- Use BST property to navigate: go left if `val < node.val`, right if `val > node.val`
- When a `null` child is reached, that is the correct insertion spot
- Create a new leaf node there and return the root

**Time**: O(h) | **Space**: O(h) recursive / O(1) iterative

**Pattern**: BST property-guided leaf insertion — every new value always lands at a leaf, no restructuring needed. The same left/right navigation used in BST search applies directly here.

