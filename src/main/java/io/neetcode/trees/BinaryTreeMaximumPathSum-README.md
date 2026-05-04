# Binary Tree Maximum Path Sum

## Problem Description

**Difficulty**: Hard

Given the root of a **non-empty binary tree**, return the **maximum path sum** of any non-empty path.

A **path** in a binary tree is a sequence of nodes where each pair of adjacent nodes has an edge connecting them. A node **cannot appear more than once** in the path. The path does **not** need to include the root.

The **path sum** is the sum of all node values along the path.

## Examples

### Example 1:
```
Input: root = [1,2,3]

Tree:
       1
      / \
     2   3

Path: 2 → 1 → 3
Output: 6
Explanation: 2 + 1 + 3 = 6 (passes through root, using both subtrees)
```

### Example 2:
```
Input: root = [-15,10,20,null,null,15,5,-5]

Tree:
         -15
         /  \
       10    20
            /  \
           15    5
          /
        -5

Path: 15 → 20 → 5
Output: 40
Explanation: 15 + 20 + 5 = 40
             Note: -15 and 10 are excluded (they reduce the sum)
             Note: -5 is the left child of 15 and is excluded (negative, hurts the path)
```

## Constraints
- 1 <= The number of nodes in the tree <= 1000
- -1000 <= Node.val <= 1000

---

## Pattern Recognition

**Primary Pattern**: **Post-Order DFS with Global Maximum Tracking**

**Why This Pattern?**
- At each node, we need to know the best path sum from the **left subtree** and from the **right subtree** before we can compute the best path **through** the current node
- Post-order (process children first, then current node) gives us exactly this information
- A **global variable** tracks the running maximum across all nodes

**Key Insight — Two Different Roles at Each Node:**
```
At each node, we compute TWO different things:

1. "Split path" through this node (used to UPDATE global max):
   = node.val + max(0, leftGain) + max(0, rightGain)
   Can use BOTH left and right subtrees — but CANNOT extend to parent

2. "Extending path" returned to parent (what we RETURN):
   = node.val + max(0, leftGain, rightGain)
   Can only use ONE subtree — so the parent can extend the path further
```

**Why max(0, ...)?**
- If a subtree contributes a **negative** sum, it's better to not include it at all
- `max(0, gain)` means "take this subtree's contribution only if it's positive"

**Related Patterns**:
1. **Diameter of Binary Tree** — Same post-order structure, tracking global max of left+right
2. **Maximum Depth of Binary Tree** — Same post-order DFS
3. **Count Good Nodes** — Post-order DFS with value tracking

---

## Algorithm & Approach

### Core Insight

**The Split vs. Extend Distinction:**

```
At node X with left child L and right child R:

"Split" path (the actual candidate for max):
    L_gain → X → R_gain
    = X.val + max(0, bestFrom_L) + max(0, bestFrom_R)
    This path CANNOT be extended upward (it goes both left and right)

"Extend" path (returned to X's parent):
    X → better_of(L_gain, R_gain)
    = X.val + max(0, bestFrom_L, bestFrom_R)
    This path CAN be extended upward (it only goes one direction)
```

**Decision Flow:**
```
dfs(node):
    ├─ If node is null → return 0
    │
    ├─ leftGain  = max(0, dfs(node.left))   ← ignore if negative
    ├─ rightGain = max(0, dfs(node.right))  ← ignore if negative
    │
    ├─ splitSum = node.val + leftGain + rightGain
    ├─ globalMax = max(globalMax, splitSum)  ← update answer
    │
    └─ return node.val + max(leftGain, rightGain)  ← extend to parent
```

### Visual Understanding

```
Example 2: root = [-15, 10, 20, null, null, 15, 5, -5], target max = 40

Tree:
         -15
         /  \
       10    20
            /  \
           15    5
          /
        -5

Post-Order DFS Traversal:

Step 1: Visit node -5 (left child of 15, leaf)
  leftGain=0, rightGain=0
  splitSum = -5 + 0 + 0 = -5  → globalMax = -5
  return -5

Step 2: Visit node 15
  leftGain  = max(0, -5) = 0  ← -5 ignored!
  rightGain = 0 (no right child)
  splitSum  = 15 + 0 + 0 = 15  → globalMax = 15
  return 15 + max(0, 0) = 15

Step 3: Visit node 5 (right child of 20, leaf)
  leftGain=0, rightGain=0
  splitSum = 5 + 0 + 0 = 5   → globalMax stays 15
  return 5

Step 4: Visit node 20
  leftGain  = max(0, 15) = 15
  rightGain = max(0, 5)  = 5
  splitSum  = 20 + 15 + 5 = 40  → globalMax = 40  ✓ ANSWER
  return 20 + max(15, 5) = 35  ← left (15) branch gives better gain

Step 5: Visit node 10 (leaf)
  splitSum = 10 → globalMax stays 40
  return 10

Step 6: Visit node -15 (root)
  leftGain  = max(0, 10) = 10
  rightGain = max(0, 35) = 35
  splitSum  = -15 + 10 + 35 = 30  → globalMax stays 40
  return -15 + max(10, 35) = 20

Final Answer: 40
```

---

### Step-by-Step Algorithm

#### **Approach 1: Recursive Post-Order DFS (OPTIMAL)**

**Core Idea**:
- Use post-order DFS to compute the best "extending gain" from each subtree
- At each node, compute the best "split path" and update the global max
- Return only the best "one-direction" gain to the parent so it can extend further

**Algorithm**:
```
maxPathSum(root):
    globalMax = -infinity
    dfs(node):
        if node is null → return 0
        leftGain  = max(0, dfs(node.left))
        rightGain = max(0, dfs(node.right))
        globalMax = max(globalMax, node.val + leftGain + rightGain)
        return node.val + max(leftGain, rightGain)
    dfs(root)
    return globalMax
```

**Code Implementation**
```java
class Solution {
    private int globalMax;

    public int maxPathSum(TreeNode root) {
        globalMax = Integer.MIN_VALUE;
        dfs(root);
        return globalMax;
    }

    private int dfs(TreeNode node) {
        // Base case: null node contributes 0
        if (node == null) {
            return 0;
        }

        // Post-order: compute best gains from left and right subtrees
        // Use max(0, ...) to skip subtrees with negative sums
        int leftGain  = Math.max(0, dfs(node.left));
        int rightGain = Math.max(0, dfs(node.right));

        // Best "split" path through this node (uses both subtrees)
        // Update global max — this path cannot extend further up
        globalMax = Math.max(globalMax, node.val + leftGain + rightGain);

        // Return best "extending" path to parent (only one direction)
        return node.val + Math.max(leftGain, rightGain);
    }
}
```

**Example Walkthrough**

Input: root = [1,2,3]

```
Tree:
       1
      / \
     2   3

dfs(1)
├─ leftGain  = max(0, dfs(2))
│  ├─ dfs(2): leftGain=0, rightGain=0
│  ├─ globalMax = max(-∞, 2+0+0) = 2
│  └─ return 2 + max(0,0) = 2
├─ rightGain = max(0, dfs(3))
│  ├─ dfs(3): leftGain=0, rightGain=0
│  ├─ globalMax = max(2, 3+0+0) = 3
│  └─ return 3 + max(0,0) = 3
├─ leftGain  = max(0, 2) = 2
├─ rightGain = max(0, 3) = 3
├─ globalMax = max(3, 1+2+3) = 6  ✓
└─ return 1 + max(2,3) = 4  (not used, we're at root)

Answer: 6
```

**Step-by-Step Trace:**

| Node | leftGain | rightGain | splitSum (val+L+R) | globalMax | Returned to Parent |
|------|----------|-----------|---------------------|-----------|-------------------|
| 2 | 0 | 0 | 2 | 2 | 2 |
| 3 | 0 | 0 | 3 | 3 | 3 |
| 1 | 2 | 3 | 6 | **6** | 4 |

**Final Answer: 6**

**Complexity Analysis**
- **Time Complexity**: O(n)
  - Each node is visited exactly once in post-order traversal
- **Space Complexity**: O(h)
  - Recursive call stack depth equals tree height
  - Best case (balanced): O(log n)
  - Worst case (skewed): O(n)

---

#### **Approach 2: Iterative Post-Order DFS using Stack**

**Core Idea**:
- Simulate post-order traversal iteratively using a stack
- Process each node after both its children, storing computed gains in a map
- Update the global max at each node

**Why Iterative?**
- Avoids recursion stack overflow for very deep/skewed trees (up to 1000 nodes)
- Same O(n) time and O(h) space

**Code Implementation**
```java
class Solution {
    public int maxPathSum(TreeNode root) {
        int globalMax = Integer.MIN_VALUE;
        Map<TreeNode, Integer> gainMap = new HashMap<>();
        gainMap.put(null, 0);

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode prev = null;
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            // Go as far left as possible
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.peek();

            // If right child exists and hasn't been processed yet
            if (curr.right != null && curr.right != prev) {
                curr = curr.right;
                continue;
            }

            stack.pop();

            // Post-order processing
            int leftGain  = Math.max(0, gainMap.getOrDefault(curr.left,  0));
            int rightGain = Math.max(0, gainMap.getOrDefault(curr.right, 0));

            // Update global max with split path
            globalMax = Math.max(globalMax, curr.val + leftGain + rightGain);

            // Store the extending gain for this node
            gainMap.put(curr, curr.val + Math.max(leftGain, rightGain));

            prev = curr;
            curr = null;
        }

        return globalMax;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) — each node visited once
- **Space Complexity**: O(n) — gainMap stores a value for every node

---

## Comparison of Approaches

| Aspect | Recursive (Post-Order) | Iterative (Stack) |
|--------|----------------------|-------------------|
| **Time Complexity** | O(n) | O(n) |
| **Space Complexity** | O(h) | O(n) (gainMap) |
| **Code Simplicity** | ✅ Clean & concise | More verbose |
| **Stack Overflow Risk** | Possible (deep tree) | ✅ None |
| **Global Variable** | Yes (instance field) | No (local variable) |
| **Preferred?** | ✅ Best for interviews | For safety in production |

**Recommendation**: Use **Recursive** in interviews — it's clean, easy to trace, and naturally expresses the post-order logic. The global variable pattern is standard for tree problems.

---

## Key Takeaways

1. **Two Roles at Every Node**
   - **Split sum** (val + leftGain + rightGain) → updates the global answer, cannot extend up
   - **Extend sum** (val + max(leftGain, rightGain)) → returned to parent, keeps path going one direction

2. **max(0, gain) is the Core Trick**
   - If a subtree has a negative best gain, simply don't include it (treat as 0)
   - This handles all-negative trees correctly (answer is the single largest node)

3. **Global Variable Pattern**
   - Many tree problems tracking a "best across all nodes" use a global/instance variable updated during DFS
   - Same pattern: Diameter of Binary Tree, Count Good Nodes

4. **Post-Order is Non-Negotiable**
   - We need children's information before processing the current node
   - Only post-order (left → right → current) guarantees this

5. **All-Negative Trees**
   - Constraints guarantee at least 1 node, and node values can be negative
   - Initialize `globalMax = Integer.MIN_VALUE` — the minimum valid answer is the single largest-value node

---

## Common Pitfalls

❌ **Mistake 1**: Forgetting `max(0, ...)` around subtree gains
```java
// WRONG: negative subtrees drag the sum down unnecessarily
int leftGain  = dfs(node.left);
int rightGain = dfs(node.right);
globalMax = Math.max(globalMax, node.val + leftGain + rightGain);
```
✅ **Correct**: Clamp negative gains to 0
```java
int leftGain  = Math.max(0, dfs(node.left));
int rightGain = Math.max(0, dfs(node.right));
```

❌ **Mistake 2**: Returning the split sum (both branches) to the parent
```java
// WRONG: a path using both left and right cannot be extended further up
return node.val + leftGain + rightGain;
```
✅ **Correct**: Return only the best single-direction path
```java
return node.val + Math.max(leftGain, rightGain);
```

❌ **Mistake 3**: Initializing globalMax to 0 (fails for all-negative trees)
```java
// WRONG: if all nodes are negative, answer should be the least-negative node
private int globalMax = 0;
```
✅ **Correct**: Initialize to minimum integer
```java
private int globalMax = Integer.MIN_VALUE;
```

❌ **Mistake 4**: Updating globalMax only at the root (misses paths not through root)
```java
// WRONG: the maximum path might be entirely in a subtree
dfs(root);
globalMax = root.val + leftGain + rightGain;  // computed only at root
```
✅ **Correct**: Update at every node during traversal
```java
globalMax = Math.max(globalMax, node.val + leftGain + rightGain);
```

---

## Related Problems

1. **Diameter of Binary Tree** (Easy) — Same post-order pattern; track max(left+right) at each node
2. **Maximum Depth of Binary Tree** (Easy) — Post-order DFS returning height
3. **Count Good Nodes in Binary Tree** (Medium) — Post-order with global counter
4. **Path Sum II** (Medium) — Find all root-to-leaf paths with a target sum
5. **Balanced Binary Tree** (Easy) — Post-order returning height, checking balance globally
6. **Longest Univalue Path** (Medium) — Very similar: max gain from each subtree + global max update

---

## Edge Cases to Consider

1. **Single Node**
   ```
   Input: root = [5]
   leftGain=0, rightGain=0
   splitSum = 5 + 0 + 0 = 5
   Answer: 5
   ```

2. **All Negative Values**
   ```
   Input: root = [-3,-2,-1]
   dfs(-2): splitSum=-2, return -2
   dfs(-1): splitSum=-1, return -1
   dfs(-3): leftGain=max(0,-2)=0, rightGain=max(0,-1)=0
            splitSum=-3+0+0=-3, globalMax = max(-2,-1,-3) = -1
   Answer: -1  ← the single best node
   ```

3. **Path Does Not Go Through Root**
   ```
   Input: root = [-15,10,20,null,null,15,5,-5]
   Tree: -15 is root; 20's children are 15 (left) and 5 (right); -5 is left child of 15
   Best path: 15→20→5 = 40, entirely in right subtree of root
   Root (-15) is never part of the answer path
   Answer: 40
   ```

4. **Negative Root, Positive Subtrees**
   ```
   Input: root = [-10,9,20,null,null,15,7]
   At node 20: leftGain=15, rightGain=7, splitSum=42
   globalMax = 42
   At root -10: leftGain=9, rightGain=max(0,35)=35, splitSum=-10+9+35=34
   Answer: 42  ← path 15→20→7
   ```

5. **Long Chain (Skewed Tree)**
   ```
   Input: root = [1,2,3,4,5]  (left-skewed)
   Best path might be 5→4→3→2→1 or 5→4→3→2
   DFS naturally handles this by propagating gains upward
   ```

---

## Summary

**Problem**: Find the maximum sum of any path in a binary tree (path can start and end at any nodes).

**Solution**:
- Use **post-order DFS** to process children before the parent
- At each node, compute `leftGain = max(0, dfs(left))` and `rightGain = max(0, dfs(right))`
- Update global max with the **split sum**: `node.val + leftGain + rightGain`
- Return the **extend sum** to parent: `node.val + max(leftGain, rightGain)`

**Time**: O(n) | **Space**: O(h)

**Pattern**: Post-Order DFS with Global Maximum. The critical insight is distinguishing between the **split path** (updates answer, uses both children) and the **extending path** (returned to parent, uses only one child). Using `max(0, gain)` elegantly handles negative subtrees.

