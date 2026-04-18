# Binary Tree Right Side View

## Problem Description

**Difficulty**: Medium

You are given the root of a binary tree. Return only the values of the nodes that are **visible from the right side** of the tree, ordered from top to bottom.

## Examples

### Example 1:
```
Input: root = [1,2,3,null,4,null,5]

Tree Structure:
        1
       / \
      2   3
       \    \
        4    5

Output: [1,3,5]
Explanation: From the right side: 1 (level 1), 3 (level 2), 5 (level 3)
```

### Example 2:
```
Input: root = [1,2,3,4,null,null,null,5]

Tree Structure:
        1
       / \
      2   3
     /
    4
   /
  5

Output: [1,3,4,5]
Explanation: From the right side: 1, 3, then 4 (3 has no left child deeper), then 5
```

### Example 3:
```
Input: root = [1,null,2]

Tree Structure:
    1
     \
      2

Output: [1,2]
```

### Example 4:
```
Input: root = []

Output: []
Explanation: Empty tree returns empty list.
```

## Constraints
- 0 <= number of nodes in the tree <= 100
- -100 <= Node.val <= 100

---

## Pattern Recognition

**Primary Pattern**: **Breadth-First Search (BFS) using Queue**

**Why This Pattern?**
- The right side view shows the **last node at each level**
- BFS naturally visits nodes level by level
- After processing each level, the last node polled is the rightmost visible node

**Key Insight**: At each level, when `i == 1` (last node in the level), we add its value to the result. This works because we count down from `queue.size()` to `1`, so `i == 1` corresponds to the last node being processed at that level.

**Related Patterns**:
1. **Level Order Traversal** – BFS visiting all nodes per level
2. **Left Side View** – BFS, pick first node at each level
3. **Zigzag Traversal** – BFS with alternating direction

---

## Algorithm & Approach

### Core Insight
The right side view consists of the **rightmost node visible at each depth level**. Using BFS, the last node dequeued at each level is exactly that rightmost visible node.

### Visual Understanding
```
Tree:       1
           / \
          2   3
           \    \
            4    5

BFS Queue Evolution:

Start   : queue = [1]
Level 1 : poll 1         → last node = 1 → result = [1]       → enqueue 2, 3
Level 2 : poll 2, 3      → last node = 3 → result = [1,3]     → enqueue 4, 5
Level 3 : poll 4, 5      → last node = 5 → result = [1,3,5]   → no children

Final Result: [1,3,5]
```

### Step-by-Step Algorithm

---

#### **Approach 1: Iterative BFS Using Queue (OPTIMAL & RECOMMENDED)**

**Core Idea**: Use a queue with a countdown `for (int i = q.size(); i > 0; i--)`. When `i == 1`, we are processing the last (rightmost) node of that level — add it to the result.

**Algorithm**
```
1. If root is null, return []
2. Create queue and add root
3. While queue is not empty:
   a. For i = queue.size() down to 1:
      - poll node from queue
      - if i == 1 → add node.val to result  (last node at this level)
      - enqueue left child if present
      - enqueue right child if present
4. Return result
```

**Code Implementation**
```java
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> q = new LinkedList<>();
    q.add(root);
    while (!q.isEmpty()) {
        for (int i = q.size(); i > 0; i--) {
            TreeNode curr = q.remove();
            if (i == 1) res.add(curr.val);
            if (curr.left != null)  q.add(curr.left);
            if (curr.right != null) q.add(curr.right);
        }
    }
    return res;
}
```

**Example Walkthrough**

Input: root = [1,2,3,null,4,null,5]

```
Tree:
        1
       / \
      2   3
       \    \
        4    5

Iteration 1 (Level 1):
  i=1: poll 1 → i==1 → res=[1] → enqueue 2, 3

Iteration 2 (Level 2):
  i=2: poll 2 → i≠1 → enqueue 4
  i=1: poll 3 → i==1 → res=[1,3] → enqueue 5

Iteration 3 (Level 3):
  i=2: poll 4 → i≠1 → no children
  i=1: poll 5 → i==1 → res=[1,3,5] → no children

Final Result: [1,3,5]
```

**Complexity Analysis**
- **Time Complexity**: O(n) – Every node is enqueued and dequeued exactly once
- **Space Complexity**: O(n) – At most the widest level is held in the queue at any time

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | BFS (Iterative) | DFS (Recursive) |
|-------------|-----------------|-----------------|
| Right-most per level | ✅ Natural | ⚠️ Needs depth tracking |
| Code simplicity | ✅ Clean | Medium |
| Time complexity | O(n) ✓ | O(n) ✓ |
| Space complexity | O(n) ✓ | O(n) ✓ |
| Interview friendly | ✅ Yes | ⚠️ Less natural |

**Winner**: **Iterative BFS** ✅ – Directly picks the last node at each level.

---

## Critical Edge Cases & Gotchas

### 1. **Empty Tree**
```java
Input: root = null
Output: []
Explanation: Guard clause at the start handles this.
```

### 2. **Single Node**
```java
Input: root = [1]
Output: [1]
Explanation: One level, that single node is the rightmost.
```

### 3. **Left-Skewed Tree**
```java
Input: root = [3,2,null,1]

Tree:   3
       /
      2
     /
    1

Output: [3,2,1]
Explanation: Even though nodes are only on the left, they are still
the rightmost (and only) node at each level, so they appear in the view.
```

### 4. **Right-Skewed Tree**
```java
Input: root = [1,null,2,null,3]

Tree:  1
        \
         2
          \
           3

Output: [1,2,3]
Explanation: One node per level, all on the right.
```

### 5. **Deeper Left Subtree**
```java
Input: root = [1,2,3,4]

Tree:     1
         / \
        2   3
       /
      4

Output: [1,3,4]
Explanation: At level 3, node 4 is the only (and thus rightmost) node,
even though it's physically on the left side of the tree.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Adding First Node Instead of Last**
```java
// WRONG - captures leftmost node, not rightmost!
if (i == queue.size()) res.add(curr.val);
```
**Fix**: Capture when `i == 1` (last node of the level).

### ❌ **MISTAKE 2: Forgetting the Null Check at the Start**
```java
// WRONG - NullPointerException when root is null!
Queue<TreeNode> q = new LinkedList<>();
q.add(root);   // ❌ Adds null to queue!
```
**Fix**:
```java
if (root == null) return res;   // ✓ Guard clause
```

### ❌ **MISTAKE 3: Adding Null Children to the Queue**
```java
// WRONG
q.add(curr.left);   // ❌ May add null!
q.add(curr.right);  // ❌ May add null!
```
**Fix**:
```java
if (curr.left != null)  q.add(curr.left);   // ✓
if (curr.right != null) q.add(curr.right);  // ✓
```

### ❌ **MISTAKE 4: Using a Stack Instead of Queue**
```java
// WRONG - Stack gives LIFO, not FIFO!
Stack<TreeNode> stack = new Stack<>();
```
**Fix**: Use `Queue<TreeNode> q = new LinkedList<>();`

---

## Complexity Analysis

### BFS Iterative Approach

**Time Complexity: O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Enqueue each node | O(1) | LinkedList add is O(1) |
| Dequeue each node | O(1) | LinkedList remove is O(1) |
| Process n nodes | O(n) | Each node visited exactly once |
| Total | O(n) | Linear in number of nodes |

**Space Complexity: O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Queue | O(w) | w = max width of tree |
| Result list | O(h) | h = height (one value per level) |
| Total | O(n) | Dominated by queue in worst case |

---

## Visualization

### Complete BFS Animation

**Input:** root = [1,2,3,null,4,null,5]

```
        1
       / \
      2   3
       \    \
        4    5

Queue State at Each Level:

── Level 1 ──────────────────────────────────
  queue before: [1]
  i=1: poll 1 → i==1 → res=[1] → enqueue 2,3
  queue after:  [2,3]

── Level 2 ──────────────────────────────────
  queue before: [2,3]
  i=2: poll 2 → i≠1 → enqueue 4
  i=1: poll 3 → i==1 → res=[1,3] → enqueue 5
  queue after:  [4,5]

── Level 3 ──────────────────────────────────
  queue before: [4,5]
  i=2: poll 4 → i≠1 → no children
  i=1: poll 5 → i==1 → res=[1,3,5] → no children
  queue after:  []

Queue empty → Done!
Final Result: [1,3,5]
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Complexity | When to Use |
|----------|------|-------|-----------------|-------------|
| **BFS Iterative** | O(n) | O(n) | ✅ **Simple** | **Default choice** ✅ |
| **DFS Recursive** | O(n) | O(n) | Medium | Alternative if BFS not allowed |

**Recommendation**: Always use **BFS Iterative** for this problem — it naturally maps to level-by-level traversal.

---

## Key Takeaways

1. **Right Side View = Last node at each level** – that's the core insight
2. **BFS countdown (`i == 1`)** cleanly identifies the last node per level
3. **Left-skewed trees** still contribute to the right side view — depth matters, not physical position
4. **Guard null children** before enqueuing
5. **Queue = FIFO**, Stack = LIFO – don't mix them up
6. **Time & Space = O(n)** for BFS approach

---

## Interview Tips

**What to say in an interview:**

> "For the right side view, I'll use BFS. At each level I iterate the queue using a countdown from `queue.size()` down to 1. When the counter hits 1, that's the last node of the level — the one visible from the right side — so I add its value to the result. This gives O(n) time and O(n) space."

**Key points to mention:**
1. **BFS** for level-by-level traversal
2. **Last node per level** is the right-side visible node
3. **Countdown trick** (`i == 1`) or snapshot + check last index
4. **Edge case**: left-only nodes at deeper levels still appear in the view

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Binary Tree Right Side View** | Medium | **BFS** | **Last node per level** ← This problem |
| Binary Tree Level Order Traversal | Medium | BFS | All nodes grouped by level |
| Binary Tree Left Side View | Easy | BFS | First node per level |
| Binary Tree Zigzag Level Order | Medium | BFS | Alternate direction each level |
| Maximum Depth of Binary Tree | Easy | BFS/DFS | Count levels |
| Populating Next Right Pointers | Medium | BFS | Link same-level nodes |

---

## Final Pattern Label

✅ **Tree Traversal – BFS Level Order (Queue) — Right Side View**

**Remember:** BFS + Queue + last node per level (`i == 1`) = Right Side View!

