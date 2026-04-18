# Binary Tree Level Order Traversal

## Problem Description

**Difficulty**: Medium

Given a binary tree root, return the **level order traversal** of its nodes' values as a nested list, where each sublist contains the values of nodes at a particular level in the tree, from left to right.

## Examples

### Example 1:
```
Input: root = [1,2,3,4,5,6,7]

Tree Structure:
        1
       / \
      2   3
     / \ / \
    4  5 6  7

Output: [[1],[2,3],[4,5,6,7]]
Explanation: Level 1 → [1], Level 2 → [2,3], Level 3 → [4,5,6,7]
```

### Example 2:
```
Input: root = [1]

Output: [[1]]
Explanation: Only one node at the root level.
```

### Example 3:
```
Input: root = []

Output: []
Explanation: Empty tree returns empty list.
```

## Constraints
- 0 <= The number of nodes in the tree <= 1000
- -1000 <= Node.val <= 1000

---

## Pattern Recognition

**Primary Pattern**: **Breadth-First Search (BFS) using Queue**

**Why This Pattern?**
- BFS naturally visits nodes level by level
- A queue (FIFO) ensures we process nodes in the correct order
- Each iteration of the outer `while` loop corresponds to one level

**Key Insight**: By recording the queue's size before processing each level, we know exactly how many nodes belong to the current level. We process only that many nodes, then add their children for the next level.

**Related Patterns**:
1. **Inorder / Preorder / Postorder Traversal** – DFS variations
2. **Zigzag Level Order** – BFS with alternating direction
3. **Right Side View** – BFS, pick last node at each level
4. **Maximum Depth** – BFS counting levels

---

## Algorithm & Approach

### Core Insight
Level order traversal visits all nodes at depth d before any node at depth d+1. This is exactly what BFS does using a queue.

### Visual Understanding
```
Tree:       1
           / \
          2   3
         / \ / \
        4  5 6  7

BFS Queue Evolution:

Start   : queue = [1]
Level 1 : poll 1         → sublist = [1]       → enqueue 2, 3
Level 2 : poll 2, 3      → sublist = [2,3]     → enqueue 4,5,6,7
Level 3 : poll 4,5,6,7   → sublist = [4,5,6,7] → no children

Result: [[1],[2,3],[4,5,6,7]]
```

### Step-by-Step Algorithm

---

#### **Approach 1: Iterative BFS Using Queue (OPTIMAL & RECOMMENDED)**

**Core Idea**: Use a queue. Before processing a level, record the current queue size — that tells us exactly how many nodes are on this level. Process that many nodes, collect their values, and enqueue their children.

**Algorithm**
```
1. If root is null, return []
2. Create queue and add root
3. While queue is not empty:
   a. size = queue.size()       ← number of nodes at current level
   b. Create empty sublist
   c. Repeat size times:
      - poll node from queue
      - add node.val to sublist
      - if node.left exists, enqueue it
      - if node.right exists, enqueue it
   d. Add sublist to result
4. Return result
```

**Code Implementation**
```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> level = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            TreeNode curr = queue.remove();
            level.add(curr.val);
            if (curr.left != null)  queue.add(curr.left);
            if (curr.right != null) queue.add(curr.right);
        }

        result.add(level);
    }

    return result;
}
```

**Example Walkthrough**

Input: root = [1,2,3,4,5,6,7]

```
Tree:
        1
       / \
      2   3
     / \ / \
    4  5 6  7

Step-by-step:

Iteration 1 (Level 1):
  size = 1
  poll 1 → level = [1] → enqueue 2, 3
  result = [[1]]

Iteration 2 (Level 2):
  size = 2
  poll 2 → level = [2] → enqueue 4, 5
  poll 3 → level = [2,3] → enqueue 6, 7
  result = [[1],[2,3]]

Iteration 3 (Level 3):
  size = 4
  poll 4 → level = [4] → no children
  poll 5 → level = [4,5] → no children
  poll 6 → level = [4,5,6] → no children
  poll 7 → level = [4,5,6,7] → no children
  result = [[1],[2,3],[4,5,6,7]]

Final Result: [[1],[2,3],[4,5,6,7]]
```

**Complexity Analysis**
- **Time Complexity**: O(n) – Every node is enqueued and dequeued exactly once
- **Space Complexity**: O(n) – At most the widest level is held in the queue at any time

---

#### **Approach 2: Iterative BFS – Flattened Single List (VARIANT)**

**Core Idea**: Same BFS queue approach, but instead of grouping nodes by level into sublists, all values are added directly into a single flat list. This is simpler but loses level information.

> **Note**: This does NOT satisfy the problem requirement of returning a nested list. It is shown here only to demonstrate how a flat BFS traversal works.

**Algorithm**
```
1. If root is null, return []
2. Create queue and add root
3. While queue is not empty:
   - poll node from queue
   - add node.val to result list
   - enqueue left child if present
   - enqueue right child if present
4. Return result
```

**Code Implementation**
```java
public List<Integer> levelOrderFlat(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
        TreeNode curr = queue.remove();
        result.add(curr.val);
        if (curr.left != null)  queue.add(curr.left);
        if (curr.right != null) queue.add(curr.right);
    }

    return result;
}
```

**Example Walkthrough**

Input: root = [1,2,3,4,5,6,7]

```
Queue Steps:
Start     : [1]
Poll 1    : result=[1],     queue=[2,3]
Poll 2    : result=[1,2],   queue=[3,4,5]
Poll 3    : result=[1,2,3], queue=[4,5,6,7]
Poll 4    : result=[1,2,3,4]
Poll 5    : result=[1,2,3,4,5]
Poll 6    : result=[1,2,3,4,5,6]
Poll 7    : result=[1,2,3,4,5,6,7]

Output: [1,2,3,4,5,6,7]
```

**Key Difference from Approach 1**

| Feature | Approach 1 (Nested) | Approach 2 (Flat) |
|---------|--------------------|--------------------|
| Output type | `List<List<Integer>>` | `List<Integer>` |
| Level grouping | ✅ Yes | ❌ No |
| Matches problem | ✅ Yes | ❌ No |
| Code complexity | Slightly more | Simplest |

**Complexity Analysis**
- **Time Complexity**: O(n) – Same, every node visited once
- **Space Complexity**: O(n) – Same queue usage

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | BFS (Iterative) | DFS (Recursive) |
|-------------|-----------------|-----------------|
| Level grouping | ✅ Natural | ❌ Needs depth tracking |
| Code simplicity | ✅ Clean | Medium |
| Time complexity | O(n) ✓ | O(n) ✓ |
| Space complexity | O(n) ✓ | O(n) ✓ |
| Interview friendly | ✅ Yes | ⚠️ Less natural |

**Winner**: **Iterative BFS** ✅ – The most natural approach for level-by-level traversal.

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
Output: [[1]]
Explanation: One level, one element.
```

### 3. **Left-Skewed Tree**
```java
Input: root = [3,2,null,1]

Tree:   3
       /
      2
     /
    1

Output: [[3],[2],[1]]
Each level has exactly one node.
```

### 4. **Right-Skewed Tree**
```java
Input: root = [1,null,2,null,3]

Tree:  1
        \
         2
          \
           3

Output: [[1],[2],[3]]
Same as left-skewed — one node per level.
```

### 5. **Complete Binary Tree**
```java
Input: root = [1,2,3,4,5,6,7]
Output: [[1],[2,3],[4,5,6,7]]
Level widths: 1, 2, 4 — each level doubles.
```

### 6. **Tree with Negative Values**
```java
Input: root = [0,-1,1]

Tree:     0
         / \
       -1   1

Output: [[0],[-1,1]]
Negative values are handled naturally.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Using a Size Snapshot**
```java
// WRONG - processes newly added children in the same level!
while (!queue.isEmpty()) {
    List<Integer> level = new ArrayList<>();
    while (!queue.isEmpty()) {   // ❌ Drains entire queue!
        TreeNode curr = queue.remove();
        level.add(curr.val);
        if (curr.left != null) queue.add(curr.left);
        if (curr.right != null) queue.add(curr.right);
    }
    result.add(level);
}
```
**Why wrong**: Children added during processing get included in the same level.

**Fix**: Snapshot the size before the inner loop.
```java
int size = queue.size();   // ✓ Snapshot before processing
for (int i = 0; i < size; i++) { ... }
```

### ❌ **MISTAKE 2: Forgetting the Null Check at the Start**
```java
// WRONG - NullPointerException when root is null!
Queue<TreeNode> queue = new LinkedList<>();
queue.add(root);   // ❌ Adds null to queue!
```
**Fix**:
```java
if (root == null) return result;   // ✓ Guard clause
```

### ❌ **MISTAKE 3: Adding Null Children to the Queue**
```java
// WRONG
queue.add(curr.left);   // ❌ May add null!
queue.add(curr.right);  // ❌ May add null!
```
**Why wrong**: queue.poll() will return null, causing NullPointerException when accessing `.val`.

**Fix**:
```java
if (curr.left != null)  queue.add(curr.left);   // ✓
if (curr.right != null) queue.add(curr.right);  // ✓
```

### ❌ **MISTAKE 4: Using a Stack Instead of Queue**
```java
// WRONG - Stack gives LIFO, not FIFO!
Stack<TreeNode> stack = new Stack<>();
```
**Why wrong**: BFS requires FIFO (First-In-First-Out) ordering. A stack gives LIFO and produces DFS order.

**Fix**: Use `Queue<TreeNode> queue = new LinkedList<>();`

### ❌ **MISTAKE 5: Forgetting to Add Sublist to Result**
```java
for (int i = 0; i < size; i++) {
    TreeNode curr = queue.remove();
    level.add(curr.val);
    ...
}
// ❌ Forgot: result.add(level);
```
**Fix**: Always add the level list to result after the inner loop.

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
| Result list | O(n) | Stores all node values |
| Total | O(n) | Dominated by result list |

In the worst case (complete binary tree), the last level holds n/2 nodes → queue size = O(n).

---

## Visualization

### Complete BFS Animation

**Input:** root = [1,2,3,4,5,6,7]

```
       1
      / \
     2   3
    / \ / \
   4  5 6  7

Queue State at Each Level:

── Level 1 ──────────────────────────────────
  queue before: [1]
  size = 1
  Process 1 → enqueue 2, 3
  level = [1]
  queue after:  [2, 3]
  result: [[1]]

── Level 2 ──────────────────────────────────
  queue before: [2, 3]
  size = 2
  Process 2 → enqueue 4, 5
  Process 3 → enqueue 6, 7
  level = [2, 3]
  queue after:  [4, 5, 6, 7]
  result: [[1],[2,3]]

── Level 3 ──────────────────────────────────
  queue before: [4, 5, 6, 7]
  size = 4
  Process 4,5,6,7 → no children
  level = [4, 5, 6, 7]
  queue after:  []
  result: [[1],[2,3],[4,5,6,7]]

Queue empty → Done!
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Complexity | When to Use |
|----------|------|-------|-----------------|-------------|
| **BFS Iterative (Nested)** | O(n) | O(n) | ✅ **Simple** | **Default choice** ✅ |
| **BFS Iterative (Flat)** | O(n) | O(n) | ✅ **Simplest** | When level grouping not needed |
| **DFS Recursive** | O(n) | O(n) | Medium | Alternative if BFS not allowed |

**Recommendation**: Always use **BFS Iterative (Nested)** for this specific problem.

---

## Key Takeaways

1. **BFS = Level Order** – Queue is the natural data structure
2. **Size snapshot** is the critical trick to group by level
3. **Guard null children** before enqueuing
4. **Queue = FIFO**, Stack = LIFO – don't mix them up
5. **Time & Space = O(n)** for all BFS approaches
6. **Flat BFS** works but doesn't satisfy the nested output requirement
7. **Each outer loop iteration = one level**

---

## Interview Tips

**What to say in an interview:**

> "For level order traversal, I'll use BFS with a queue. At the start of each level, I snapshot the queue size — that's exactly how many nodes belong to the current level. I process that many nodes, collect their values into a sublist, and enqueue their children. Once the inner loop finishes, I add the sublist to the result. This gives O(n) time and O(n) space."

**Key points to mention:**
1. **BFS** is the natural choice for level-by-level traversal
2. **Queue snapshot** (size variable) is key to level grouping
3. **Guard against null** root and null children
4. **Complexity**: O(n) time, O(n) space

**If asked about DFS alternative:**
> "We can also solve this with DFS by passing the current depth as a parameter. At each node, we add its value to result[depth]. But BFS is more intuitive here since we're explicitly traversing level by level."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Binary Tree Level Order Traversal** | Medium | **BFS** | **Nested list per level** ← This problem |
| Binary Tree Zigzag Level Order | Medium | BFS | Alternate direction each level |
| Binary Tree Right Side View | Medium | BFS | Pick last node at each level |
| Maximum Depth of Binary Tree | Easy | BFS/DFS | Count levels |
| Minimum Depth of Binary Tree | Easy | BFS | First leaf level |
| Binary Tree Level Order Traversal II | Medium | BFS | Bottom-up result |
| N-ary Tree Level Order Traversal | Medium | BFS | Multiple children per node |
| Populating Next Right Pointers | Medium | BFS | Link same-level nodes |

**Pattern Progression**:
1. **Level Order** (this problem) – Foundation of BFS on trees
2. **Zigzag / Right Side View** – Variations on BFS output
3. **Populating Next Pointers** – BFS with pointer manipulation
4. **Graph BFS** – Extend the same idea to graphs

---

## Additional Notes

### Why is the Size Snapshot Important?

Without the snapshot, children added during level N processing would be mixed into the same level:

```
Suppose queue = [2, 3] (Level 2)

WITHOUT size snapshot:
  poll 2 → enqueue 4, 5 → queue = [3, 4, 5]
  poll 3 → enqueue 6, 7 → queue = [4, 5, 6, 7]
  ... continues — never ends the level correctly!

WITH size snapshot (size = 2):
  i=0: poll 2 → enqueue 4, 5
  i=1: poll 3 → enqueue 6, 7
  Inner loop ends → level = [2,3] ✓
```

### BFS vs DFS for Level Order

| Property | BFS | DFS |
|----------|-----|-----|
| Data structure | Queue | Stack / Recursion |
| Natural order | Level by level ✅ | Depth first |
| Level grouping | Natural ✅ | Needs depth param |
| Code simplicity | ✅ Simple | Slightly complex |

**BFS is the natural and preferred approach** for any problem involving levels.

---

## Final Pattern Label

✅ **Tree Traversal – BFS Level Order (Queue)**

**Remember:** Snapshot the queue size before each level to group nodes correctly. BFS + Queue = Level Order!

