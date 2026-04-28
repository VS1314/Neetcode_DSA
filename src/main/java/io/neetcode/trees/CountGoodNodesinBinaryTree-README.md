# Count Good Nodes in Binary Tree

## Problem Description

**Difficulty**: Medium

Within a binary tree, a node `x` is considered **good** if the path from the root of the tree to the node `x` contains **no nodes with a value greater than the value of node x**.

Given the root of a binary tree `root`, return the **number of good nodes** within the tree.

## Examples

### Example 1:
```
Input: root = [2,1,1,3,null,1,5]

Tree Structure:
        2
       / \
      1   1
     /   / \
    3   1   5

Output: 3
Explanation: Good nodes are: 2 (root, path=[2]), 1 (right child, path=[2,1]),
             5 (path=[2,1,5], no value > 5 on path), 3 (path=[2,1,3], 2 > 3? No, 2 < 3, so good)
             Wait — path to 1 (left) is [2,1], max=2 > 1, so NOT good.
             Good nodes: 2, right-1, 3(right-left? No), 5, 3(left child of left-1)
             Actually: 2(root)=good, 3(2>3? No)=good, right-1(2>1? Yes)=not good,
             left-child-1(path=[2,1,1])=not good, 5(path=[2,1,5], max=2<5)=good
             → Good nodes: 2, 3, 5 = 3 total
```

### Example 2:
```
Input: root = [1,2,-1,3,4]

Tree Structure:
        1
       / \
      2  -1
     / \
    3   4

Output: 4
Explanation:
- Node 1 (root): path=[1], max=1≤1 → GOOD
- Node 2: path=[1,2], max=1≤2 → GOOD
- Node -1: path=[1,-1], max=1>-1 → NOT GOOD
- Node 3: path=[1,2,3], max=2≤3 → GOOD
- Node 4: path=[1,2,4], max=2≤4 → GOOD
Total good nodes = 4
```

### Example 3:
```
Input: root = []

Output: 0
```

## Constraints
- 1 <= number of nodes in the tree <= 100
- -100 <= Node.val <= 100

---

## Pattern Recognition

**Primary Pattern**: **Depth-First Search (DFS) with Path Tracking**

**Why This Pattern?**
- Need to explore **all root-to-node paths**
- At each node, need to know the **maximum value seen so far** on the path from root
- Perfect for **pre-order DFS** (process current node before going deeper)

**Key Insight**:
- A node is **good** if `node.val >= maxSoFar` (no value on the path is greater than this node)
- Pass the running maximum down the recursion tree
- Use a **global counter** to count good nodes

**Related Patterns**:
1. **Path Sum** - Tracks running sum down to leaves
2. **Maximum Depth of Binary Tree** - Simple DFS traversal
3. **Diameter of Binary Tree** - DFS with global state
4. **Balanced Binary Tree** - DFS with information passed up

---

## Algorithm & Approach

### Core Insight

**The Good Node Definition:**
- A node `x` is good if **no ancestor has a value greater than x.val**
- Equivalently: `x.val >= max(values on path from root to x)`

**Strategy:**
- Traverse the tree with DFS
- At each node, pass down the maximum value seen so far
- If `node.val >= maxSoFar`, the node is good → increment count

### Visual Understanding
```
Tree: [1,2,-1,3,4]

        1        ← path max = -∞, node=1, 1≥-∞ ✓ GOOD, pass max=1 down
       / \
      2  -1      ← left: max=1, node=2, 2≥1 ✓ GOOD, pass max=2
                 ← right: max=1, node=-1, -1≥1? ✗ NOT GOOD, pass max=1
     / \
    3   4        ← left: max=2, node=3, 3≥2 ✓ GOOD
                 ← right: max=2, node=4, 4≥2 ✓ GOOD

Good nodes: 1, 2, 3, 4 → count = 4
```

### Step-by-Step Algorithm

#### **Approach 1: Recursive DFS (OPTIMAL)**

**Core Idea**:
- Use DFS, passing the maximum value on the current path down the recursion
- At each node: if `node.val >= maxSoFar`, it's a good node
- Recurse into left and right with updated max

**Algorithm**
```
count = 0  (global variable)

dfs(node, maxSoFar):
    if node is null:
        return
    
    if node.val >= maxSoFar:
        count++
    
    newMax = max(maxSoFar, node.val)
    dfs(node.left, newMax)
    dfs(node.right, newMax)

goodNodes(root):
    count = 0
    dfs(root, Integer.MIN_VALUE)
    return count
```

**Code Implementation**
```java
class Solution {
    private int count = 0;

    public int goodNodes(TreeNode root) {
        dfs(root, Integer.MIN_VALUE);
        return count;
    }

    private void dfs(TreeNode node, int maxSoFar) {
        // Base case: null node
        if (node == null) {
            return;
        }

        // If current node's value >= max value on path from root, it's a good node
        if (node.val >= maxSoFar) {
            count++;
        }

        // Update max for the path going forward
        int newMax = Math.max(maxSoFar, node.val);

        // Recurse into left and right subtrees
        dfs(node.left, newMax);
        dfs(node.right, newMax);
    }
}
```

**Example Walkthrough**

Input: root = [2,1,1,3,null,1,5]

```
Tree:
        2
       / \
      1   1
     /   / \
    3   1   5

Call Stack Visualization:

dfs(2, MIN)
├─ 2 >= MIN → count=1, newMax=2
├─ dfs(1, 2)          [left child]
│  ├─ 1 >= 2? NO → count stays 1
│  ├─ newMax = max(2,1) = 2
│  ├─ dfs(3, 2)
│  │  ├─ 3 >= 2 → count=2, newMax=3
│  │  ├─ dfs(null, 3) → return
│  │  └─ dfs(null, 3) → return
│  └─ dfs(null, 2) → return
└─ dfs(1, 2)          [right child]
   ├─ 1 >= 2? NO → count stays 2
   ├─ newMax = max(2,1) = 2
   ├─ dfs(1, 2)
   │  ├─ 1 >= 2? NO → count stays 2
   │  ├─ dfs(null, 2) → return
   │  └─ dfs(null, 2) → return
   └─ dfs(5, 2)
      ├─ 5 >= 2 → count=3, newMax=5
      ├─ dfs(null, 5) → return
      └─ dfs(null, 5) → return

Final count: 3
```

**Step-by-Step Trace:**

| Node | maxSoFar | node.val >= maxSoFar | Count | newMax |
|------|----------|----------------------|-------|--------|
| 2 | MIN_VALUE | ✓ | 1 | 2 |
| 1 (left) | 2 | ✗ | 1 | 2 |
| 3 | 2 | ✓ | 2 | 3 |
| 1 (right) | 2 | ✗ | 2 | 2 |
| 1 (right-left) | 2 | ✗ | 2 | 2 |
| 5 | 2 | ✓ | **3** | 5 |

**Final Result: 3**

**Why This Works:**
1. **maxSoFar** tracks the highest value on the path from root to current node
2. If `node.val >= maxSoFar`, no ancestor has a greater value → good node
3. We always pass `max(maxSoFar, node.val)` to children so the max stays current
4. Single pass through every node → O(n)

**Complexity Analysis**
- **Time Complexity**: O(n) – Visit each node exactly once
- **Space Complexity**: O(h) – Recursion stack depth (h = height of tree)
  - Best case (balanced): O(log n)
  - Worst case (skewed): O(n)

---

#### **Approach 2: Iterative DFS with Stack**

**Core Idea**:
Use an explicit stack to simulate the DFS. Push `(node, maxSoFar)` pairs onto the stack. For each popped pair, check if the node is good, then push children with updated max.

**Algorithm**
```
1. Push (root, Integer.MIN_VALUE) onto stack
2. While stack is not empty:
   a. Pop (node, maxSoFar)
   b. If node.val >= maxSoFar → count++
   c. newMax = max(maxSoFar, node.val)
   d. Push (node.left, newMax) if not null
   e. Push (node.right, newMax) if not null
3. Return count
```

**Code Implementation**
```java
class Solution {
    public int goodNodes(TreeNode root) {
        if (root == null) return 0;

        int count = 0;
        // Stack stores [node, maxSoFar]
        Deque<Object[]> stack = new ArrayDeque<>();
        stack.push(new Object[]{root, Integer.MIN_VALUE});

        while (!stack.isEmpty()) {
            Object[] top = stack.pop();
            TreeNode node = (TreeNode) top[0];
            int maxSoFar = (int) top[1];

            // Check if this node is good
            if (node.val >= maxSoFar) {
                count++;
            }

            int newMax = Math.max(maxSoFar, node.val);

            // Push children with updated max
            if (node.left != null) {
                stack.push(new Object[]{node.left, newMax});
            }
            if (node.right != null) {
                stack.push(new Object[]{node.right, newMax});
            }
        }

        return count;
    }
}
```

**Example Walkthrough**

Input: root = [1,2,-1,3,4]

```
Tree:
        1
       / \
      2  -1
     / \
    3   4

Stack Simulation:

Initial: stack = [(1, MIN)]

Step 1: Pop (1, MIN)
  - 1 >= MIN → count=1, newMax=1
  - Push (2, 1), (-1, 1)
  stack = [(2,1), (-1,1)]

Step 2: Pop (-1, 1)
  - -1 >= 1? NO → count stays 1
  - newMax = max(1,-1) = 1
  - No children
  stack = [(2,1)]

Step 3: Pop (2, 1)
  - 2 >= 1 → count=2, newMax=2
  - Push (3, 2), (4, 2)
  stack = [(3,2), (4,2)]

Step 4: Pop (4, 2)
  - 4 >= 2 → count=3, newMax=4
  - No children
  stack = [(3,2)]

Step 5: Pop (3, 2)
  - 3 >= 2 → count=4, newMax=3
  - No children
  stack = []

Final count: 4
```

**Complexity Analysis**
- **Time Complexity**: O(n) – Visit each node once
- **Space Complexity**: O(n) – Stack holds at most O(n) entries in worst case

---

## Comparison of Approaches

| Aspect | Recursive DFS | Iterative DFS |
|--------|---------------|---------------|
| **Time Complexity** | O(n) | O(n) |
| **Space Complexity** | O(h) | O(n) |
| **Code Simplicity** | Very Simple | Moderate |
| **Intuition** | Natural top-down | Explicit stack |
| **Preferred?** | ✅ Yes | Only if recursion not allowed |

**Recommendation**: Use **Recursive DFS** – cleaner, more readable, same time complexity.

---

## Key Takeaways

1. **Good Node Condition**: `node.val >= max value on path from root to node`
2. **Pass Information Down**: Unlike diameter (pass up), here we pass `maxSoFar` **down** the tree
3. **Pre-order Traversal**: Process node before children (opposite of diameter)
4. **Initialize with MIN_VALUE**: Ensures the root is always counted as a good node
5. **No Backtracking Needed**: Each path is self-contained with its own `maxSoFar`

---

## Common Pitfalls

❌ **Mistake 1**: Using a shared mutable max (forgetting each path is independent)
```java
// WRONG: Mutating a global max breaks different paths
maxSoFar = Math.max(maxSoFar, node.val);  // as class field
```

✅ **Correct**: Pass max as a parameter so each recursive branch has its own copy
```java
dfs(node.left, Math.max(maxSoFar, node.val));
```

❌ **Mistake 2**: Initializing maxSoFar with 0 instead of MIN_VALUE
```java
// WRONG: Root node with negative value won't be counted
dfs(root, 0);
```

✅ **Correct**: Use Integer.MIN_VALUE so root is always good
```java
dfs(root, Integer.MIN_VALUE);
```

❌ **Mistake 3**: Forgetting null base case
```java
// WRONG: Will throw NullPointerException
if (node.val >= maxSoFar) { ... }
```

✅ **Correct**: Handle null first
```java
if (node == null) return;
```

---

## Related Problems

1. **Path Sum** (Easy) – Pass running sum down DFS
2. **Path Sum II** (Medium) – Collect all root-to-leaf paths meeting a condition
3. **Diameter of Binary Tree** (Easy) – DFS with global state
4. **Maximum Depth of Binary Tree** (Easy) – Simple DFS
5. **Binary Tree Maximum Path Sum** (Hard) – Global max with DFS

---

## Edge Cases to Consider

1. **Single Node Tree**
   ```
   Tree: [5]
   Good nodes: 1 (root is always good)
   ```

2. **All Nodes Decreasing (Left Skewed)**
   ```
   Tree: 5 → 3 → 1
   Good: only root (5)
   Output: 1
   ```

3. **All Nodes Increasing**
   ```
   Tree: 1 → 2 → 3
   Good: all nodes (each >= predecessor)
   Output: 3
   ```

4. **Negative Values**
   ```
   Tree: [-3, -5, -1]
   - Root(-3): good (always)
   - Left(-5): -5 >= -3? No → not good
   - Right(-1): -1 >= -3? Yes → good
   Output: 2
   ```

---

## Summary

**Problem**: Count nodes where no ancestor has a greater value.

**Solution**:
- Use DFS, passing `maxSoFar` (max on current path) down the tree
- If `node.val >= maxSoFar`, count it as a good node
- Recurse with `max(maxSoFar, node.val)` for children

**Time**: O(n) | **Space**: O(h)

**Pattern**: Pre-order DFS with path-state tracking (top-down)

