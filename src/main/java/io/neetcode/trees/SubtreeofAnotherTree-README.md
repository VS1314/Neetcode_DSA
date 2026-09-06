# Subtree of Another Tree

## Problem Description

**Difficulty**: Easy

Given the roots of two binary trees `root` and `subRoot`, return `true` if there is a subtree of `root` with the same structure and node values of `subRoot` and `false` otherwise.

A **subtree** of a binary tree `tree` is a tree that consists of a node in `tree` and all of this node's descendants. The tree `tree` could also be considered as a subtree of itself.

**Key Concepts:**
- **Subtree Definition**: Must include a node and **ALL** of its descendants (cannot stop early in a branch).
- **Two-Tier Recursion**:
  - Outer recursion: Traverse each node `node` in `root`.
  - Inner recursion: At each `node`, invoke `isSameTree(node, subRoot)`.
- **Base Cases**:
  - If `subRoot == null` $\rightarrow$ `true` (empty tree is a subtree of any tree).
  - If `root == null` $\rightarrow$ `false` (non-empty `subRoot` cannot be a subtree of an empty tree).
- **Subtree Condition**: `isSameTree(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot)`.

**Visual Overview:**
```
Tree root:                     Tree subRoot:
       1                             2
      / \                           / \
     2   3                         4   5
    / \
   4   5

Step 1: isSameTree(root(1), subRoot(2)) -> false
Step 2: Recurse to left child:
        isSubtree(root.left(2), subRoot(2)):
        isSameTree(root.left(2), subRoot(2)) -> true ✓
Result: true
```

**Recommended Complexity**:
- Time: $O(m \times n)$ where $n$ is nodes in `root` and $m$ is nodes in `subRoot` (or $O(n + m)$ via serialization/KMP).
- Space: $O(h_n + h_m)$ recursion stack depth ($O(n + m)$ worst case).

---

## Examples

### Example 1 (Valid Subtree):
```
Input: root = [1,2,3,4,5], subRoot = [2,4,5]

Tree root:               Tree subRoot:
       1                       2
      / \                     / \
     2   3                   4   5
    / \
   4   5

Output: true

Explanation:
Subtree rooted at node 2 in `root` has identical structure and values to `subRoot`.
```

### Example 2 (Invalid Subtree - Extra Descendants):
```
Input: root = [1,2,3,4,5,null,null,6], subRoot = [2,4,5]

Tree root:               Tree subRoot:
       1                       2
      / \                     / \
     2   3                   4   5
    / \
   4   5
  /
 6

Output: false

Explanation:
Subtree rooted at node 2 has an extra child 6 under 4. A subtree must include all descendants.
```

### Example 3 (Identical Trees):
```
Input: root = [1,2,3], subRoot = [1,2,3]

Output: true

Explanation:
A tree is considered a subtree of itself.
```

### Example 4 (subRoot Matching Right Child):
```
Input: root = [1,null,2,null,3], subRoot = [2,null,3]

Tree root:          Tree subRoot:
    1                     2
     \                     \
      2                     3
       \
        3

Output: true
```

### Example 5 (Same Values, Different Structure):
```
Input: root = [3,4,5,1,null,2], subRoot = [4,1,2]

Tree root:               Tree subRoot:
       3                       4
      / \                     / \
     4   5                   1   2
    /   /
   1   2

Output: false

Explanation:
In root, node 2 is a child of 5, not a child of 4.
```

### Example 6 (Single Node Match):
```
Input: root = [1], subRoot = [1]

Output: true
```

### Example 7 (Single Node Mismatch):
```
Input: root = [1], subRoot = [2]

Output: false
```

### Example 8 (subRoot at Leaf Level):
```
Input: root = [1,2,3], subRoot = [3]

Output: true
```

### Example 9 (Repeated Values in Root):
```
Input: root = [1,1,1], subRoot = [1]

Output: true
```

### Example 10 (subRoot Bigger Than Root):
```
Input: root = [1], subRoot = [1,2,3]

Output: false
```

---

## Constraints
- The number of nodes in the `root` tree is in the range `[1, 2000]`.
- The number of nodes in the `subRoot` tree is in the range `[1, 1000]`.
- $-10^4 \le \text{root.val} \le 10^4$
- $-10^4 \le \text{subRoot.val} \le 10^4$

---

## Pattern Recognition

**Primary Pattern**: **Tree Traversal with Nested Helper Function (DFS + Same Tree)**

**Why This Pattern?**
- To verify if `subRoot` exists as a subtree, we must check every node in `root` as a candidate root.
- For each candidate node, we run the classic `isSameTree` validation.
- Outer recursion navigates `root` (`O(n)` calls), inner recursion compares trees (`O(m)` per candidate).

```
Structure:
  isSubtree(root, subRoot):
    - Base checks: root == null -> false
    - If isSameTree(root, subRoot) -> true
    - Else check isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot)
```

---

## Algorithm & Approach

### Approach 1: Double DFS (Recursive Tree Traversal + Same Tree) — Recommended

#### Algorithm:
1. If `subRoot == null`, return `true` (empty tree is always a subtree).
2. If `root == null`, return `false` (`subRoot` is non-empty, but `root` is empty).
3. Check if `isSameTree(root, subRoot)` is `true`. If so, return `true`.
4. Otherwise, recursively search in left and right subtrees: `isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot)`.

#### Java Code Implementation:
```java
public class SubtreeofAnotherTree {

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (subRoot == null) {
            return true;
        }
        if (root == null) {
            return false;
        }

        // Check if current subtree matches subRoot
        if (isSameTree(root, subRoot)) {
            return true;
        }

        // Recurse on left or right subtree
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null || p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```

---

### Approach 2: String Serialization + Substring Matching (KMP / Rabin-Karp) — $O(n + m)$

#### Method Explanation:
- Serialize both trees using pre-order traversal with distinct boundary delimiters and null markers (e.g., `,#` and `,^nodeVal`).
- Check if `serializedSubRoot` is a substring of `serializedRoot` using `contains()` or KMP.

```java
public class SubtreeSerialization {

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        StringBuilder sbRoot = new StringBuilder();
        StringBuilder sbSub = new StringBuilder();
        
        serialize(root, sbRoot);
        serialize(subRoot, sbSub);
        
        return sbRoot.toString().contains(sbSub.toString());
    }

    private void serialize(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append(",#");
            return;
        }
        sb.append(",^").append(node.val);
        serialize(node.left, sb);
        serialize(node.right, sb);
    }
}
```

---

## Why This Strategy?

### Comparison of Approaches

| Criteria | Recursive Double DFS | Tree Serialization (KMP) |
| :--- | :--- | :--- |
| **Time Complexity** | $O(n \times m)$ average $O(n)$ | $O(n + m)$ |
| **Space Complexity** | $O(h_n + h_m)$ | $O(n + m)$ string memory |
| **Code Simplicity** | ⭐ **Extremely clean & intuitive** | Requires string formatting & boundary tokens |
| **Interview Expectation** | Primary standard solution | Advanced follow-up |

---

## Critical Edge Cases & Gotchas

1. **Extra Descendants in `root`**:
   - If `root` has extra children below where `subRoot` ends, `isSameTree` correctly detects `p != null && q == null` and returns `false`.
2. **Identical Values with Different Shapes**:
   - Handled correctly by structural equality check in `isSameTree`.
3. **Empty `subRoot`**:
   - `subRoot == null` is always a valid subtree of any tree $\rightarrow$ returns `true`.
4. **Empty `root` with Non-Empty `subRoot`**:
   - Returns `false`.
5. **Multiple Candidate Nodes**:
   - `root` contains multiple nodes matching `subRoot.val`; `||` search continues checking all candidate branches.

---

## Major Areas Where We Might Go Wrong

### ❌ Mistake 1: Merging `isSubtree` and `isSameTree` into a Single Recursive Function
```java
// WRONG: Calling isSubtree directly for children inside value check
if (root.val == subRoot.val) {
    return isSubtree(root.left, subRoot.left) && isSubtree(root.right, subRoot.right); // ❌
}
```
**Why wrong**: If roots match in value but descendants mismatch, this logic fails to restart checking `subRoot` against `root.left` or `root.right`.
**Correction**: Keep `isSameTree` and `isSubtree` as separate functions with distinct responsibilities.

---

### ❌ Mistake 2: Missing Delimiters in Serialization
```java
// WRONG: "12" matches "2" as a substring without delimiters!
sb.append(node.val);
```
**Correction**: Prefix with unique delimiters: `sb.append(",^").append(node.val);`.

---

### ❌ Mistake 3: Returning `false` Immediately if Root Values Mismatch
```java
// WRONG: Failing without searching subtrees
if (root.val != subRoot.val) return false;
```
**Correction**: Recurse on `root.left` and `root.right` even if `root.val != subRoot.val`.

---

## Complexity Analysis

- **Time Complexity**:
  - **Worst Case**: $O(n \times m)$ where $n$ is nodes in `root` and $m$ is nodes in `subRoot` (occurs when every node in `root` matches `subRoot.val` but fails at the leaf).
  - **Average Case**: $O(n)$ because mismatches are detected near the root of `subRoot` in $O(1)$ comparisons.
- **Space Complexity**:
  - $O(h_n + h_m)$ recursion stack depth, where $h_n, h_m$ are tree heights. For balanced trees, $O(\log n + \log m)$; for skewed trees, $O(n + m)$.

---

## Visualization

```
root = [1, 2, 3, 4, 5], subRoot = [2, 4, 5]

isSubtree(root=1, subRoot=2)
  ├── isSameTree(1, 2) -> false (1 != 2)
  ├── isSubtree(root.left=2, subRoot=2)
  │     ├── isSameTree(2, 2)
  │     │     ├── 2.val == 2.val ✓
  │     │     ├── isSameTree(4, 4) -> true ✓
  │     │     └── isSameTree(5, 5) -> true ✓
  │     │     └── returns true!
  │     └── returns true!
  └── Result: true (Short-circuits, root.right=3 never evaluated)
```

---

## Comparison of Approaches

```
+------------------------+----------------------+--------------------------+
| Metric                 | Double DFS           | Serialization + KMP      |
+------------------------+----------------------+--------------------------+
| Time Complexity (Worst)| O(n * m)             | O(n + m)                 |
| Time Complexity (Avg)  | O(n)                 | O(n + m)                 |
| Auxiliary Space        | O(h) (Call Stack)    | O(n + m) (String/Heap)   |
| Implementation Effort  | Minimal (~15 lines)  | Moderate (~35 lines)     |
+------------------------+----------------------+--------------------------+
```

---

## Key Takeaways

1. **Subtree vs Subgraph**: Subtree requires matching all descendant nodes down to every `null` leaf.
2. **Separation of Concerns**: `isSubtree` explores candidates; `isSameTree` strictly validates equality from a given candidate.
3. **Short-Circuit Logic**: Using `||` allows early return as soon as any matching subtree is found.
4. **Foundation Building**: Direct composition of the **Same Binary Tree** pattern.

---

## Interview Tips

- **How to explain**:
  > *"I will solve this by checking whether `subRoot` matches the tree starting at `root` using an `isSameTree` helper. If not, I recursively check whether `subRoot` is a subtree of `root.left` or `root.right`. This visits each candidate node in $O(n)$ average time."*
- **Follow-up question**: "How to optimize worst-case $O(n \times m)$ to $O(n + m)$?"
  - *Answer*: "Serialize both trees with pre-order traversal including null markers and unique delimiters, then perform KMP string matching."

---

## Related Problems

| Problem | Difficulty | Relation |
| :--- | :--- | :--- |
| **Same Tree** | Easy | Core helper function used in this problem. |
| **Symmetric Tree** | Easy | Checks mirror equality between left and right subtrees. |
| **Count Univalue Subtrees** | Medium | Checks subtree property bottom-up. |
| **Find Duplicate Subtrees** | Medium | Uses tree serialization/hashing to find duplicate subtrees. |

---

## Final Pattern Label

✅ **Nested Tree DFS (Traversal + Strict Identity Validation)**

**Summary**: Traverse `root` using outer DFS. At each candidate node, invoke `isSameTree` inner DFS to verify structural and value equality across all descendants.
