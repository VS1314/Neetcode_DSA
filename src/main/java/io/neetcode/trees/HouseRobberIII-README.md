# House Robber III

## Problem Description

**Difficulty**: Medium

The thief has found himself a new place for his thievery. There is only one entrance to this area, called `root`.

All houses in this place form a **binary tree**. It will automatically contact the police if **two directly-linked houses** were broken into.

Given the `root` of the binary tree, return the **maximum amount of money** the thief can rob without alerting the police.

## Examples

### Example 1:
```
Input: root = [1,4,null,2,3,3]

Tree Structure:
        1
       /
      4
     / \
    2   3
   /
  3

Output: 7
Explanation: Rob node(4) + node(3 leaf under 2) = 4 + 3 = 7
             OR: Rob node(1) + node(3 right of 4) + node(3 leaf under 2) = 1 + 3 + 3 = 7
```

### Example 2:
```
Input: root = [1,null,2,3,5,4,2]

Tree Structure:
        1
         \
          2
         / \
        3   5
       / \
      4   2

Output: 12
Explanation: Rob 1 + 5 + 4 + 2 = 12
             Rob root(1), skip node(2), skip node(3), rob node(5),
             and rob the two children of node(3): 4 and 2
```

## Constraints
- 1 <= The number of nodes in the tree <= 10,000
- 0 <= Node.val <= 10,000

---

## Pattern Recognition

**Primary Pattern**: **Post-Order DFS (Bottom-Up Dynamic Programming on Tree)**

**Why This Pattern?**
- At each node, we have **two choices**: rob this node (skip children) or skip this node (take best of children)
- The decision at a parent depends on decisions made at its children → post-order (children first)
- Each subtree returns **two values**: max money if we rob this node, max money if we skip this node

**Key Insight**:
- `rob(node)` = `node.val + skip(left) + skip(right)`
- `skip(node)` = `max(rob(left), skip(left)) + max(rob(right), skip(right))`
- By returning both values from each subtree call, we avoid recomputation — **O(n)** time

**Related Patterns**:
1. **House Robber I & II** – Same rob/skip DP logic on a linear array / circle
2. **Count Good Nodes in Binary Tree** – Tracking state while doing DFS
3. **Diameter of Binary Tree** – Post-order DFS returning computed value per subtree
4. **Balanced Binary Tree** – Bottom-up DFS returning multiple values per node

---

## Algorithm & Approach

### Core Insight

```
At every node we must decide:

  ROB this node:
      money = node.val + (skip left child) + (skip right child)

  SKIP this node:
      money = max(rob left, skip left) + max(rob right, skip right)
             ^^^ best we can do from left   ^^^ best we can do from right

Return both options up the tree so the parent can choose optimally.
```

```
Example tree:
        3
       / \
      2   3
       \   \
        3   1

At node 3 (left-right leaf):  rob=3, skip=0
At node 2:
    rob  = 2 + skip(null) + skip(3) = 2 + 0 + 0 = 2
    skip = max(rob null, skip null) + max(rob 3, skip 3)
         = 0 + max(3, 0) = 3
    return (rob=2, skip=3)

At node 1 (right-right leaf): rob=1, skip=0
At node 3 (right child of root):
    rob  = 3 + 0 + 0 = 3
    skip = 0 + max(1, 0) = 1
    return (rob=3, skip=1)

At root 3:
    rob  = 3 + skip(left=3) + skip(right=1) = 3 + 3 + 1 = 7
    skip = max(2,3) + max(3,1) = 3 + 3 = 6

Answer = max(7, 6) = 7
```

---

### Step-by-Step Algorithm

#### **Approach 1: Post-Order DFS returning pair (OPTIMAL — O(n))**

**Core Idea**:
- For each node, recursively compute `[robAmount, skipAmount]` from both children
- Use those to compute current node's rob and skip values
- At the root, return `max(rob, skip)`

**Algorithm**
```
dfs(node):
    if node is null:
        return [0, 0]   // rob=0, skip=0

    [leftRob, leftSkip]   = dfs(node.left)
    [rightRob, rightSkip] = dfs(node.right)

    rob  = node.val + leftSkip + rightSkip
    skip = max(leftRob, leftSkip) + max(rightRob, rightSkip)

    return [rob, skip]

rob(root):
    [robRoot, skipRoot] = dfs(root)
    return max(robRoot, skipRoot)
```

**Code Implementation**
```java
class Solution {
    public int rob(TreeNode root) {
        int[] result = dfs(root);
        return Math.max(result[0], result[1]);
    }

    // Returns int[]{robAmount, skipAmount} for the subtree rooted at node
    private int[] dfs(TreeNode node) {
        // Base case: null node contributes nothing either way
        if (node == null) {
            return new int[]{0, 0};
        }

        // Post-order: solve children first
        int[] left  = dfs(node.left);
        int[] right = dfs(node.right);

        // Rob this node → must skip both children
        int rob  = node.val + left[1] + right[1];

        // Skip this node → take best option at each child independently
        int skip = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return new int[]{rob, skip};
    }
}
```

**Example Walkthrough**

Input: root = [1,4,null,2,3,3]

```
Tree:
        1
       /
      4
     / \
    2   3
   /
  3

dfs(3 — leaf under 2):  rob=3, skip=0  → [3, 0]
dfs(2):
    left  = dfs(3) = [3,0]
    right = dfs(null) = [0,0]
    rob   = 2 + 0 + 0 = 2
    skip  = max(3,0) + max(0,0) = 3 + 0 = 3
    → [2, 3]
dfs(3 — right of 4):  rob=3, skip=0  → [3, 0]
dfs(4):
    left  = dfs(2) = [2,3]
    right = dfs(3) = [3,0]
    rob   = 4 + 3 + 0 = 7
    skip  = max(2,3) + max(3,0) = 3 + 3 = 6
    → [7, 6]
dfs(1 — root):
    left  = dfs(4) = [7,6]
    right = dfs(null) = [0,0]
    rob   = 1 + 6 + 0 = 7
    skip  = max(7,6) + max(0,0) = 7 + 0 = 7
    → [7, 7]

Answer = max(7, 7) = 7 ✓
```

**Step-by-Step Trace:**

| Node | left[] | right[] | rob | skip | return |
|------|--------|---------|-----|------|--------|
| 3 (leaf under 2) | [0,0] | [0,0] | 3 | 0 | [3,0] |
| 2 | [3,0] | [0,0] | 2 | 3 | [2,3] |
| 3 (right of 4) | [0,0] | [0,0] | 3 | 0 | [3,0] |
| 4 | [2,3] | [3,0] | 7 | 6 | [7,6] |
| 1 (root) | [7,6] | [0,0] | 7 | 7 | [7,7] |

**Answer = max(7, 7) = 7**

**Why This Works:**
1. **Post-order** ensures children are fully solved before their parent makes a decision
2. **Pair return `[rob, skip]`** gives the parent all the information it needs without any global state
3. **No memoization needed** — each node is visited exactly once; O(n) naturally
4. **Greedy is wrong** — local maximum at a node might not be globally optimal; DP handles this correctly

**Complexity Analysis**
- **Time Complexity**: O(n) — every node visited exactly once
- **Space Complexity**: O(h) — recursion stack depth (h = height of tree)
  - Best case (balanced): O(log n)
  - Worst case (skewed): O(n)

---

#### **Approach 2: Recursive with Memoization (O(n) but more overhead)**

**Core Idea**:
- Naively, for each node: `rob(node) = max(robThisNode, skipThisNode)`
- But `robThisNode` requires skipping children, which means checking grandchildren — overlapping subproblems
- Use a HashMap to cache results per node

**Code Implementation**
```java
class Solution {
    private Map<TreeNode, Integer> memo = new HashMap<>();

    public int rob(TreeNode root) {
        if (root == null) return 0;
        if (memo.containsKey(root)) return memo.get(root);

        // Option 1: rob this node, skip children, take grandchildren
        int robThis = root.val;
        if (root.left  != null) robThis += rob(root.left.left)  + rob(root.left.right);
        if (root.right != null) robThis += rob(root.right.left) + rob(root.right.right);

        // Option 2: skip this node, take best of children
        int skipThis = rob(root.left) + rob(root.right);

        int result = Math.max(robThis, skipThis);
        memo.put(root, result);
        return result;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) — each node computed once due to memoization
- **Space Complexity**: O(n) — HashMap stores result for every node + O(h) recursion stack

---

## Common Mistakes & Edge Cases

| Scenario | Issue | Fix |
|----------|-------|-----|
| Greedy (always rob highest node) | Locally optimal ≠ globally optimal | Use DP; consider both rob/skip at every node |
| Returning only one value from DFS | Parent can't know whether child was robbed or skipped | Return **pair** `[rob, skip]` from every DFS call |
| Naive recursion without memo | O(2^n) — re-solves grandchildren for every node | Use pair-return DFS (Approach 1) or memoization |
| Single node tree | Only one node, must return its value | Base case returns [0,0] for null; root naturally handled |
| All zeros | Every node is 0 | Returns 0 correctly; max(0,0) = 0 |

---

## Visual Summary

```
Rob/Skip Decision at Each Node:

          [rob=?, skip=?]
               |
        ┌──────┴──────┐
  [rob=L, skip=L]  [rob=R, skip=R]

rob  = node.val + skip(L) + skip(R)     ← must skip both children
skip = max(rob L, skip L) + max(rob R, skip R)  ← children choose freely

Propagate [rob, skip] bottom-up → root gives final answer.

Example:
        3          rob=7,  skip=6  → answer = 7
       / \
      2   3        rob=2,skip=3   rob=3,skip=1
       \   \
        3   1      [3,0]          [1,0]
```

---

## Complexity Summary

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| Post-Order DFS with pair return | O(n) | O(h) | Optimal; no extra data structures |
| Recursive + Memoization (HashMap) | O(n) | O(n) | Also correct but higher constant factor |
| Naive recursion (no memo) | O(2^n) | O(h) | Avoid — exponential recomputation |

---

## Key Takeaways

1. **Return a pair `[rob, skip]`** from each DFS call — gives parent full information to decide optimally
2. **Post-order DFS** is the natural fit — solve children before parent
3. **Greedy fails here** — robbing the richest visible node locally can block better global choices
4. **This is tree DP** — same rob/skip pattern as House Robber I/II, but applied recursively on a tree
5. **O(n) without memoization** — pair-return DFS visits each node exactly once, no repeated work

