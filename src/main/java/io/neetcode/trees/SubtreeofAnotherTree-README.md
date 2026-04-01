# Subtree of Another Tree

## Problem Description

**Difficulty**: Easy

Given the roots of two binary trees `root` and `subRoot`, return `true` if there is a **subtree** of `root` with the same structure and node values of `subRoot` and `false` otherwise.

**Definition**: A **subtree** of a binary tree `tree` is a tree that consists of a node in `tree` and **all of this node's descendants**. The tree `tree` could also be considered as a subtree of itself.

## Examples

### Example 1:
```
Input: root = [1,2,3,4,5], subRoot = [2,4,5]

Main Tree (root):        SubRoot Tree:
        1                      2
       / \                    / \
      2   3                  4   5
     / \
    4   5

Output: true
Explanation: The subtree rooted at node 2 in the main tree is identical to subRoot.
- Node 2 has left child 4 and right child 5 ✓
- Structure and values match exactly ✓
```

### Example 2:
```
Input: root = [1,2,3,4,5,null,null,6], subRoot = [2,4,5]

Main Tree (root):              SubRoot Tree:
        1                            2
       / \                          / \
      2   3                        4   5
     / \
    4   5
   /
  6

Output: false
Explanation: The subtree rooted at node 2 has an extra child (node 6).
- Subtree at node 2: [2,4,5,6] ✗
- Expected subRoot: [2,4,5] ✗
- Structure doesn't match!
```

### Example 3:
```
Input: root = [3,4,5,1,2], subRoot = [4,1,2]

Main Tree (root):        SubRoot Tree:
        3                      4
       / \                    / \
      4   5                  1   2
     / \
    1   2

Output: true
Explanation: Subtree rooted at node 4 matches subRoot exactly.
```

### Example 4:
```
Input: root = [3,4,5,1,null,2], subRoot = [3,1,2]

Main Tree (root):        SubRoot Tree:
        3                      3
       / \                    / \
      4   5                  1   2
     /     \
    1       2

Output: false
Explanation: No subtree in root matches subRoot's structure.
```

## Constraints
- 1 <= The number of nodes in both trees <= 100
- -100 <= root.val, subRoot.val <= 100

---

## Pattern Recognition

**Primary Pattern**: **Tree Traversal + Tree Comparison (DFS + Same Tree Check)**

**Why This Pattern?**
- Need to **find a node** in main tree that could be root of subtree
- For each candidate node, **check if subtree matches** subRoot
- Combines two problems:
  1. Traverse main tree to find candidates (DFS)
  2. Check if two trees are identical (Same Tree problem)

**Key Insight**:
- A subtree match requires:
  1. Find a node in `root` with same value as `subRoot.val`
  2. Check if entire subtree rooted at that node is identical to `subRoot`
  3. If not found, continue searching in left and right subtrees

**Pattern Breakdown:**
```
For each node in main tree:
├─ If current node's subtree matches subRoot → return true
├─ Else, check if subRoot is in left subtree
├─ Else, check if subRoot is in right subtree
└─ Return false if not found anywhere
```

**Related Patterns**:
1. **Same Tree** - Core helper function for comparison
2. **Path Sum** - Similar recursive search pattern
3. **Binary Tree Paths** - Exploring all possible subtrees
4. **Validate Binary Search Tree** - Recursive validation pattern

---

## Algorithm & Approach

### Core Insight

**The Subtree Problem = Traversal + Comparison:**
1. **Traverse** the main tree (visit every node)
2. **Compare** each node's subtree with subRoot
3. **Return true** if any subtree matches

**Why it works:**
```
For subtree to exist:
- Either current node's subtree matches subRoot, OR
- SubRoot exists in left subtree, OR
- SubRoot exists in right subtree

Use helper function isSameTree(p, q) from "Same Tree" problem
```

**Two-Function Approach:**
```
isSubtree(root, subRoot)  →  Traverse main tree, search for subRoot
isSameTree(p, q)          →  Check if two trees are identical
```

**Decision Flow:**
```
isSubtree(root, subRoot):
    ├─ If root is null → return false (can't find subRoot in empty tree)
    │
    ├─ Step 1: Check if current node's subtree matches
    │  └─ If isSameTree(root, subRoot) → return true ✓
    │
    ├─ Step 2: Search in left subtree
    │  └─ If isSubtree(root.left, subRoot) → return true ✓
    │
    └─ Step 3: Search in right subtree
       └─ return isSubtree(root.right, subRoot)
```

### Visual Understanding

```
Example: root = [1,2,3,4,5], subRoot = [2,4,5]

Main Tree:              SubRoot:
        1                    2
       / \                  / \
      2   3                4   5
     / \
    4   5

Search Process:

1. Check node 1:
   - isSameTree([1,2,3,4,5], [2,4,5]) → false (roots differ: 1 ≠ 2)

2. Search left subtree → Check node 2:
   - isSameTree([2,4,5], [2,4,5]) → true ✓ FOUND!

Result: true
```

**Failed Search Example:**
```
root = [1,2,3,4,5,null,null,6], subRoot = [2,4,5]

Main Tree:
        1
       / \
      2   3
     / \
    4   5
   /
  6

1. Check node 1: false (1 ≠ 2)
2. Check node 2: false (extra node 6 under node 4)
3. Check node 3: false (3 ≠ 2)
4. Check node 4: false (4 ≠ 2, and has child 6)
5. Check node 5: false (5 ≠ 2)
6. Check node 6: false (6 ≠ 2)

Result: false (no match found anywhere)
```

---

### Step-by-Step Algorithm

#### **Approach 1: Recursive DFS with Same Tree Helper (OPTIMAL)**

**Core Idea**:
- Traverse main tree using DFS
- At each node, check if subtree matches using `isSameTree` helper
- Use OR logic: match found at current OR in left OR in right

**Algorithm**
```
isSubtree(root, subRoot):
    if root is null → return false
    if isSameTree(root, subRoot) → return true
    return isSubtree(root.left, subRoot) OR isSubtree(root.right, subRoot)

isSameTree(p, q):
    if p is null AND q is null → return true
    if p is null OR q is null OR p.val != q.val → return false
    return isSameTree(p.left, q.left) AND isSameTree(p.right, q.right)
```

**Code Implementation**
```java
class Solution {
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        // Base case: can't find subRoot in empty tree
        if (root == null) {
            return false;
        }

        // Check if current node's subtree matches subRoot
        if (isSameTree(root, subRoot)) {
            return true;
        }

        // Search in left or right subtrees (OR: one match is enough)
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    // Helper: Check if two trees are identical (reused from Same Tree problem)
    private boolean isSameTree(TreeNode p, TreeNode q) {
        // Both null → trees are same
        if (p == null && q == null) {
            return true;
        }

        // One is null or values differ → not same
        if (p == null || q == null || p.val != q.val) {
            return false;
        }

        // Values match → check both subtrees (AND: all must match)
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```

**Example Walkthrough**

Input: root = [1,2,3,4,5], subRoot = [2,4,5]

```
Call Stack Visualization:

isSubtree(1, [2,4,5])
├─ isSameTree(1, 2)? → 1 ≠ 2 → false
├─ isSubtree(2, [2,4,5])           ← search left
│  ├─ isSameTree(2, 2)?
│  │  ├─ 2.val == 2.val ✓
│  │  ├─ isSameTree(4, 4)?
│  │  │  ├─ 4.val == 4.val ✓
│  │  │  ├─ isSameTree(null, null) → true
│  │  │  ├─ isSameTree(null, null) → true
│  │  │  └─ return true
│  │  ├─ isSameTree(5, 5)?
│  │  │  ├─ 5.val == 5.val ✓
│  │  │  ├─ isSameTree(null, null) → true
│  │  │  ├─ isSameTree(null, null) → true
│  │  │  └─ return true
│  │  └─ return true ✓ MATCH FOUND!
│  └─ return true
└─ return true (short-circuit, right not checked)

Final Result: true
```

**Step-by-Step Trace:**

| Step | Current Node | isSameTree Result | Decision |
|------|-------------|-------------------|----------|
| 1 | Node 1 | false (1 ≠ 2) | Continue search |
| 2 | Node 2 (left of 1) | **true** ✓ | Match found! |
| 3 | — | — | Return true |

**Final Result: true**

**Complexity Analysis**
- **Time Complexity**: O(m × n)
  - m = nodes in `root`, n = nodes in `subRoot`
  - For each of m nodes, isSameTree may take O(n) time
- **Space Complexity**: O(h₁ + h₂)
  - h₁ = height of `root` (isSubtree call stack)
  - h₂ = height of `subRoot` (isSameTree call stack)
  - Best case (balanced): O(log m + log n)
  - Worst case (skewed): O(m + n)

---

#### **Approach 2: Iterative BFS with Queue**

**Core Idea**:
- Use BFS to visit all nodes in main tree level by level
- For each node, check if its subtree matches subRoot using isSameTree
- Return true immediately when a match is found

**Why BFS?**
- Visits nodes level by level
- Finds match early if subRoot is near the root
- Avoids recursion for main traversal

**Code Implementation**
```java
class Solution {
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) return false;

        // BFS traversal of main tree
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            // Check if current node's subtree matches subRoot
            if (isSameTree(current, subRoot)) {
                return true;
            }

            // Add children to queue for further exploration
            if (current.left != null)  queue.offer(current.left);
            if (current.right != null) queue.offer(current.right);
        }

        return false;  // No match found
    }

    private boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null || p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```

**Example Walkthrough**

Input: root = [1,2,3,4,5], subRoot = [2,4,5]

```
Main Tree:
        1
       / \
      2   3
     / \
    4   5

BFS Processing:

Initial: queue = [1]

Step 1: Process node 1
- isSameTree(1, subRoot)? → false (1 ≠ 2)
- Add children: queue = [2, 3]

Step 2: Process node 2
- isSameTree(2, subRoot)? → true ✓ FOUND!
- Return true immediately

Result: true
```

**Complexity Analysis**
- **Time Complexity**: O(m × n) — same as recursive
- **Space Complexity**: O(m + h₂)
  - Queue holds up to O(m) nodes in worst case
  - isSameTree recursion stack: O(h₂)

---

## Comparison of Approaches

| Aspect | Recursive DFS | Iterative BFS |
|--------|---------------|---------------|
| **Time Complexity** | O(m × n) | O(m × n) |
| **Space Complexity** | O(h₁ + h₂) | O(m + h₂) |
| **Code Simplicity** | ✅ Very Simple | Moderate |
| **Early Termination** | ✅ Yes | ✅ Yes |
| **Traversal Order** | Depth-first | Level-by-level |
| **Preferred?** | ✅ Yes | Only if recursion not allowed |

**Recommendation**: Use **Recursive DFS** — cleaner, more intuitive, better space in balanced trees.

---

## Key Takeaways

1. **Two-Part Problem**
   - Part 1: Traverse main tree (find candidates)
   - Part 2: Compare subtrees (same tree check)
   - Reuse `isSameTree` logic as helper function

2. **OR vs AND Logic**
   - `isSubtree` → **OR** (`||`): one match anywhere is enough
   - `isSameTree` → **AND** (`&&`): all parts must match

3. **Complete Subtree Requirement**
   - Must include **all descendants** of the node
   - Can't be a partial match
   - Structure and values must both match exactly

4. **Base Cases**
   - Main tree null → false (can't find in empty tree)
   - Both trees null in comparison → true (empty subtrees match)

5. **Short-Circuit Optimization**
   - If match found at current node → return immediately
   - OR operator (`||`) short-circuits on first `true`

---

## Common Pitfalls

❌ **Mistake 1**: Checking only root values, not full subtree
```java
// WRONG
if (root.val == subRoot.val) return true;
```
✅ **Correct**: Use isSameTree to verify entire subtree
```java
if (isSameTree(root, subRoot)) return true;
```

❌ **Mistake 2**: Using AND instead of OR when searching
```java
// WRONG: Requires match in BOTH subtrees simultaneously
return isSubtree(root.left, subRoot) && isSubtree(root.right, subRoot);
```
✅ **Correct**: One match anywhere is sufficient
```java
return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
```

❌ **Mistake 3**: Not handling null root
```java
// WRONG: NPE if root is null
if (isSameTree(root, subRoot)) return true;
```
✅ **Correct**: Check null first
```java
if (root == null) return false;
if (isSameTree(root, subRoot)) return true;
```

❌ **Mistake 4**: Not propagating null check in isSameTree
```java
// WRONG: Missing one null check causes NPE
if (p == null || q == null) return false;
// This returns false even when both are null!
```
✅ **Correct**: Handle both-null case first
```java
if (p == null && q == null) return true;   // both null → same
if (p == null || q == null) return false;  // one null → different
```

---

## Related Problems

1. **Same Tree** (Easy) - Core helper function reused here
2. **Symmetric Tree** (Easy) - Similar tree comparison logic
3. **Merge Two Binary Trees** (Easy) - Simultaneous tree traversal
4. **Count Univalue Subtrees** (Medium) - Finding specific subtrees
5. **Maximum Depth of Binary Tree** (Easy) - Basic tree traversal
6. **Serialize and Deserialize Binary Tree** (Hard) - Alternative string-based approach

---

## Edge Cases to Consider

1. **SubRoot is the Entire Tree**
   ```
   root = [1,2,3], subRoot = [1,2,3]
   Result: true (a tree is a subtree of itself)
   ```

2. **SubRoot is a Single Node**
   ```
   root = [1,2,3], subRoot = [2]
   Result: true (node 2 with no children matches leaf)
   ```

3. **SubRoot Not Found**
   ```
   root = [1,2,3], subRoot = [4,5]
   Result: false (no node with value 4)
   ```

4. **Same Structure, Different Values**
   ```
   root = [1,2,3,4,5], subRoot = [2,4,6]
   Result: false (value 5 ≠ 6)
   ```

5. **Extra Children Cause Mismatch**
   ```
   root = [1,2,3,4,5,null,null,6], subRoot = [2,4,5]
   Node 2 in root has extra descendant (6) → not a match
   Result: false
   ```

6. **SubRoot Matches a Leaf Node**
   ```
   root = [1,2,3,4,5], subRoot = [4]
   Result: true (leaf node 4 matches)
   ```

7. **Multiple Candidates — Only One Matches**
   ```
   root = [1,2,2,3,4,5,6], subRoot = [2,5,6]
   First node 2: doesn't match
   Second node 2: matches ✓
   Result: true
   ```

---

## Practice Tips

1. **Master "Same Tree" First** — this problem directly builds on it
2. **Draw Both Trees** — visualize main tree and subRoot side by side
3. **Trace Both Functions Separately** — follow isSubtree (search) and isSameTree (compare) independently
4. **OR vs AND** — search uses OR, comparison uses AND
5. **Test Edge Cases** — single node, full tree match, extra children

---

## Detailed Explanation: OR vs AND Logic

**Question**: "Why do we use OR (`||`) for `isSubtree` but AND (`&&`) for `isSameTree`?"

### OR Logic in isSubtree → Search

**Goal**: Find if subRoot exists **anywhere** in main tree

```
isSubtree returns true if ANY of these hold:
- Current node's subtree == subRoot, OR
- subRoot found in left subtree, OR
- subRoot found in right subtree

ONE match is enough → use ||
```

**Example:**
```
root:      1          subRoot:  2
          / \                  / \
         2   3                4   5
        / \
       4   5

Check node 1: false
Check node 2: true ✓

Result: false || true = TRUE ← only ONE match needed
```

### AND Logic in isSameTree → Comparison

**Goal**: Verify two trees are **completely identical**

```
isSameTree returns true only if ALL hold:
- Current nodes match, AND
- Left subtrees match, AND
- Right subtrees match

ALL parts must match → use &&
```

**Example:**
```
p:    2          q:    2
     / \              / \
    4   5            4   5

roots: 2 == 2 ✓
left:  4 == 4 ✓
right: 5 == 5 ✓

Result: true && true && true = TRUE ← ALL must match
```

### Summary Table

| Function | Operator | Goal | Meaning |
|----------|----------|------|---------|
| `isSubtree` | **OR** `\|\|` | Search | Find **any** occurrence |
| `isSameTree` | **AND** `&&` | Validate | Verify **complete** match |

---

## Alternative Approach: String Serialization (Advanced)

**Idea**: Serialize both trees to strings, then check if subRoot's string is a substring of root's string.

**Code:**
```java
public boolean isSubtree(TreeNode root, TreeNode subRoot) {
    String rootStr = serialize(root);
    String subStr  = serialize(subRoot);
    return rootStr.contains(subStr);
}

private String serialize(TreeNode node) {
    if (node == null) return "#";
    // Use "^" prefix to avoid false match: e.g. "2" inside "12"
    return "^" + node.val + "," + serialize(node.left) + "," + serialize(node.right);
}
```

**Why delimiters?**
- Without `^`, node value `2` could falsely match inside `12`
- `^` prefix ensures each node value is uniquely bounded

**Complexity**: O(m + n) time, O(m + n) space

---

## Summary

**Problem**: Find if one tree exists as a complete subtree of another tree.

**Solution**:
- Traverse main tree using DFS
- At each node, use `isSameTree` to check if subtree matches `subRoot`
- Return `true` if any match found (OR logic)

**Time**: O(m × n) | **Space**: O(h₁ + h₂)

**Pattern**: Tree traversal + tree comparison — reuse Same Tree as helper

