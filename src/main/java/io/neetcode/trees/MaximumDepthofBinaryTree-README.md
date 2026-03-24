# Maximum Depth of Binary Tree

## Problem Description

**Difficulty**: Easy

Given the root of a binary tree, return its **depth**.

The **depth of a binary tree** is defined as the number of nodes along the longest path from the root node down to the farthest leaf node.

## Examples

### Example 1:
```
Input: root = [1,2,3,null,null,4]

Tree Structure:
        1
       / \
      2   3
           \
            4

Output: 3
Explanation: The longest path is 1 → 3 → 4 (3 nodes)
```

### Example 2:
```
Input: root = []

Output: 0
Explanation: Empty tree has depth 0.
```

### Example 3:
```
Input: root = [1]

Output: 1
Explanation: Single node has depth 1.
```

## Constraints
- 0 <= The number of nodes in the tree <= 100
- -100 <= Node.val <= 100

---

## Pattern Recognition

**Primary Pattern**: **Depth-First Search (DFS) with Recursion**

**Why This Pattern?**
- Need to explore **all paths** from root to leaves
- Depth is determined by the **longest path**
- At each node, depth = 1 + max(left subtree depth, right subtree depth)
- Perfect use case for recursion (divide and conquer)

**Key Insight**: The depth of a tree is 1 (current node) + the maximum depth of its left or right subtree. Base case: null node has depth 0.

**Related Patterns**:
1. **Tree Height Calculation** - Same as depth
2. **Minimum Depth of Binary Tree** - Find shortest path
3. **Balanced Binary Tree** - Uses depth calculation
4. **Diameter of Binary Tree** - Combines left and right depths

---

## Algorithm & Approach

### Core Insight
The maximum depth can be calculated recursively:
- **Base case**: If node is null → depth = 0
- **Recursive case**: depth = 1 + max(leftDepth, rightDepth)
- The `+1` accounts for the current node

**Why it works:**
```
Tree:       1
           / \
          2   3
         /
        4

Depth calculation:
- Node 4: max(0, 0) + 1 = 1
- Node 2: max(1, 0) + 1 = 2
- Node 3: max(0, 0) + 1 = 1
- Node 1: max(2, 1) + 1 = 3 ✓
```

### Visual Understanding
```
        1           depth = 3
       / \
      2   3         depth = 2 (left), 1 (right)
     /
    4               depth = 1

Process:
1. Calculate depth of left subtree (rooted at 2) = 2
2. Calculate depth of right subtree (rooted at 3) = 1
3. Depth of tree = 1 + max(2, 1) = 3
```

### Step-by-Step Algorithm

#### **Approach 1: Recursive DFS (OPTIMAL)**

**Core Idea**: Use recursion to calculate depth of left and right subtrees, then return 1 + max of both.

**Algorithm**
```
maxDepth(root):
    if root is null:
        return 0
    
    leftDepth = maxDepth(root.left)
    rightDepth = maxDepth(root.right)
    
    return 1 + max(leftDepth, rightDepth)
```

**Code Implementation**
```java
class Solution {
    public int maxDepth(TreeNode root) {
        // Base case: empty tree has depth 0
        if (root == null) {
            return 0;
        }
        
        // Recursively find depth of left subtree
        int leftDepth = maxDepth(root.left);
        
        // Recursively find depth of right subtree
        int rightDepth = maxDepth(root.right);
        
        // Depth = 1 (current node) + max of left and right depths
        return 1 + Math.max(leftDepth, rightDepth);
    }
}
```

**Alternative - One-liner**
```java
class Solution {
    public int maxDepth(TreeNode root) {
        return root == null ? 0 : 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
}
```

**Example Walkthrough**

Input: root = [1,2,3,null,null,4]

```
Tree:
        1
       / \
      2   3
           \
            4

Call Stack Visualization:

maxDepth(1)
├─ maxDepth(2)
│  ├─ maxDepth(null) → return 0
│  ├─ maxDepth(null) → return 0
│  └─ return 1 + max(0, 0) = 1
├─ maxDepth(3)
│  ├─ maxDepth(4)
│  │  ├─ maxDepth(null) → return 0
│  │  ├─ maxDepth(null) → return 0
│  │  └─ return 1 + max(0, 0) = 1
│  ├─ maxDepth(null) → return 0
│  └─ return 1 + max(1, 0) = 2
└─ return 1 + max(1, 2) = 3

Final Result: 3
```

**Step-by-Step Trace:**

| Call | Node | Left Depth | Right Depth | Calculation | Result |
|------|------|------------|-------------|-------------|--------|
| 1 | null | - | - | - | 0 |
| 2 | 2 | 0 | 0 | 1 + max(0,0) | 1 |
| 3 | null | - | - | - | 0 |
| 4 | 4 | 0 | 0 | 1 + max(0,0) | 1 |
| 5 | 3 | 0 | 1 | 1 + max(0,1) | 2 |
| 6 | 1 | 1 | 2 | 1 + max(1,2) | **3** |

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node exactly once
- **Space Complexity**: O(h) - Recursion stack depth (h = height of tree)
  - Best case (balanced tree): O(log n)
  - Worst case (skewed tree): O(n)

---

#### **Approach 2: Iterative BFS with Queue (LEVEL-ORDER FLATTENED)**

**Core Idea**: Use BFS to traverse level by level with a queue, simply collecting all node values. The depth equals the number of levels traversed.

**Why This Works:**
- Queue processes nodes level by level (BFS)
- Each iteration of the outer while loop processes one complete level
- Count levels to determine depth
- Simple flattened traversal without nested loops

**Algorithm**
```
1. If root is null, return 0
2. Create queue and add root
3. Initialize depth = 0
4. While queue is not empty:
   a. Get current level size
   b. Increment depth
   c. Process all nodes at current level
   d. Add children to queue
5. Return depth
```

**Code Implementation (Simplified)**
```java
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int depth = 0;
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            depth++;
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.remove();
                
                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }
            }
        }
        
        return depth;
    }
}
```

**Example Walkthrough**

Input: root = [1,2,3,null,null,4]

```
Tree:
        1
       / \
      2   3
           \
            4
```

| Step | queue | levelSize | depth | Action |
|------|-------|-----------|-------|--------|
| 1 | [1] | 1 | 1 | Process level 1: node 1, add 2,3 |
| 2 | [2,3] | 2 | 2 | Process level 2: nodes 2,3, add 4 |
| 3 | [4] | 1 | 3 | Process level 3: node 4 |
| 4 | [] | - | 3 | Queue empty, return 3 |

**Detailed Execution:**
```
Initial: queue = [1], depth = 0

Iteration 1:
- levelSize = 1, depth = 1
- Process: 1 → add children (2, 3)
- queue = [2, 3]

Iteration 2:
- levelSize = 2, depth = 2
- Process: 2 → no children
- Process: 3 → add child (4)
- queue = [4]

Iteration 3:
- levelSize = 1, depth = 3
- Process: 4 → no children
- queue = []

Result: depth = 3 ✓
```

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node exactly once
- **Space Complexity**: O(w) - Queue holds nodes at current level (w = max width)
  - Worst case (complete tree): O(n/2) = O(n)

---

#### **Approach 3: Iterative DFS with Stack**

**Core Idea**: Use a stack to traverse the tree and track depth at each node. Keep track of maximum depth encountered.

**Why This Works:**
- Stack stores (node, currentDepth) pairs
- Process nodes depth-first
- Update maximum depth as we go

**Algorithm**
```
1. If root is null, return 0
2. Create stack, push (root, 1)
3. Initialize maxDepth = 0
4. While stack is not empty:
   a. Pop (node, depth) from stack
   b. Update maxDepth = max(maxDepth, depth)
   c. Push left child with depth+1 (if exists)
   d. Push right child with depth+1 (if exists)
5. Return maxDepth
```

**Code Implementation**
```java
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        
        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, 1));
        int maxDepth = 0;
        
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> current = stack.pop();
            TreeNode node = current.getKey();
            int depth = current.getValue();
            
            // Update maximum depth
            maxDepth = Math.max(maxDepth, depth);
            
            // Push children with incremented depth
            if (node.left != null) {
                stack.push(new Pair<>(node.left, depth + 1));
            }
            if (node.right != null) {
                stack.push(new Pair<>(node.right, depth + 1));
            }
        }
        
        return maxDepth;
    }
}
```

**Alternative - Using Custom Class or Array**
```java
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();
        
        nodeStack.push(root);
        depthStack.push(1);
        int maxDepth = 0;
        
        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            int depth = depthStack.pop();
            
            maxDepth = Math.max(maxDepth, depth);
            
            if (node.left != null) {
                nodeStack.push(node.left);
                depthStack.push(depth + 1);
            }
            if (node.right != null) {
                nodeStack.push(node.right);
                depthStack.push(depth + 1);
            }
        }
        
        return maxDepth;
    }
}
```

**Example Walkthrough**

Input: root = [1,2,3,null,null,4]

```
Tree:
        1
       / \
      2   3
           \
            4
```

| Step | stack | node | depth | maxDepth | Action |
|------|-------|------|-------|----------|--------|
| 1 | [(1,1)] | - | - | 0 | Start |
| 2 | [] | 1 | 1 | 1 | Pop, push children |
| 3 | [(2,2),(3,2)] | 1 | 1 | 1 | Pushed 2 and 3 |
| 4 | [(2,2)] | 3 | 2 | 2 | Pop 3, push 4 |
| 5 | [(2,2),(4,3)] | 3 | 2 | 2 | Pushed 4 |
| 6 | [(2,2)] | 4 | 3 | 3 | Pop 4, no children |
| 7 | [] | 2 | 2 | 3 | Pop 2, no children |
| 8 | - | - | - | **3** | Stack empty, return 3 |

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node exactly once
- **Space Complexity**: O(n) - Stack can hold up to n nodes in worst case

---


## Why This Strategy?

### Why Recursion is Natural
- Tree is a recursive structure (subtrees are also trees)
- Depth definition is recursive: depth(tree) = 1 + max(depth(left), depth(right))
- Base case is clear: null node has depth 0
- Code mirrors the mathematical definition

### Mathematical Proof
For any tree with root r:
```
depth(r) = 1 + max(depth(r.left), depth(r.right))

Base case: depth(null) = 0

Example:
Tree:    A
        / \
       B   C
      /
     D

depth(D) = 1 + max(0, 0) = 1
depth(B) = 1 + max(1, 0) = 2
depth(C) = 1 + max(0, 0) = 1
depth(A) = 1 + max(2, 1) = 3 ✓
```

### Optimality Proof
- Must visit every node to determine longest path → O(n) time minimum
- Recursive approach visits each node exactly once → O(n) optimal
- Cannot do better than O(h) space for recursive approach (call stack needed)

---

## Common Mistakes & Edge Cases

### Mistake 1: Counting Edges Instead of Nodes
```java
// ❌ WRONG - returns number of edges, not nodes
public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return Math.max(maxDepth(root.left), maxDepth(root.right));  // Missing +1
}

// ✅ CORRECT - counts nodes
public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

### Mistake 2: Wrong Base Case
```java
// ❌ WRONG - single node returns 2 instead of 1
public int maxDepth(TreeNode root) {
    if (root.left == null && root.right == null) return 0;  // Wrong!
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}

// ✅ CORRECT
public int maxDepth(TreeNode root) {
    if (root == null) return 0;  // Correct base case
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

### Mistake 3: Not Handling Null in Iterative Approach
```java
// ❌ WRONG - doesn't check for null
if (root == null) return 0;
stack.push(root.left);   // Could push null!
stack.push(root.right);  // Could push null!

// ✅ CORRECT
if (node.left != null) {
    stack.push(node.left);
}
if (node.right != null) {
    stack.push(node.right);
}
```

### Edge Cases
1. **Empty Tree**: `root = null` → Return `0`
2. **Single Node**: `root = [1]` → Return `1`
3. **Skewed Tree**: All left or all right → Depth = number of nodes
4. **Complete Binary Tree**: All levels full except possibly last

**Example Edge Cases:**
```
Empty: null → depth = 0

Single:  1  → depth = 1

Left Skewed:
    1
   /
  2
 /
3
→ depth = 3

Right Skewed:
1
 \
  2
   \
    3
→ depth = 3

Balanced:
    1
   / \
  2   3
 / \
4   5
→ depth = 3
```

---

## Variations & Related Problems

### 1. Minimum Depth of Binary Tree
Find the **shortest** path from root to a leaf.

**Difference**: Use `min` instead of `max`, but be careful with nodes that have only one child.

### 2. Balanced Binary Tree
Check if tree is height-balanced (left and right subtree heights differ by at most 1).

**Solution**: Calculate depth at each node and check difference.

### 3. Diameter of Binary Tree
Find the longest path between any two nodes (may not pass through root).

**Solution**: At each node, diameter = leftDepth + rightDepth.

### 4. Binary Tree Maximum Path Sum
Find path with maximum sum.

**Connection**: Similar recursive structure, tracking max at each node.

---

## Interview Tips

### What Interviewer Looks For
1. **Pattern Recognition**: Immediately identify this as a recursive problem
2. **Base Case**: Correctly identify null → 0
3. **Recursive Case**: Understand the 1 + max formula
4. **Multiple Approaches**: Know both recursive and iterative solutions

### Common Follow-ups
- "Can you do it iteratively?" → Yes, using stack (DFS) or queue (BFS)
- "What's the space complexity?" → O(h) for recursion, O(n) for iterative
- "What if we want minimum depth?" → Similar, but use min and handle one-child case
- "Can you do it in O(1) space?" → No, need to store call stack or queue
- "How would you handle a very deep tree?" → Use iterative to avoid stack overflow

### Optimization Discussion
- **Time**: O(n) is optimal - must visit all nodes
- **Space**: 
  - Recursive: O(h) best we can do (call stack)
  - Iterative: O(n) worst case
  - Cannot achieve O(1) without threading/Morris-like technique

---

## Complete Solution with Comments

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int maxDepth(TreeNode root) {
        // Base case: empty tree has depth 0
        // This is crucial - it handles leaf node's children (which are null)
        if (root == null) {
            return 0;
        }
        
        // Recursively calculate the depth of the left subtree
        // This will explore all the way down the left branch
        int leftDepth = maxDepth(root.left);
        
        // Recursively calculate the depth of the right subtree
        // This will explore all the way down the right branch
        int rightDepth = maxDepth(root.right);
        
        // The depth at current node is:
        // 1 (current node) + maximum of left and right subtree depths
        // We take the max because we want the LONGEST path
        return 1 + Math.max(leftDepth, rightDepth);
    }
}
```

**One-Liner Version:**
```java
class Solution {
    public int maxDepth(TreeNode root) {
        return root == null ? 0 : 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
}
```

---

## Summary

| Aspect | Recursive | Iterative DFS | Iterative BFS |
|--------|-----------|---------------|---------------|
| **Pattern** | DFS Recursion | DFS with Stack | BFS with Queue |
| **Time Complexity** | O(n) | O(n) | O(n) |
| **Space Complexity** | O(h) | O(n) | O(w) |
| **Code Simplicity** | Very Simple | Moderate | Moderate |
| **Best For** | Interviews | Avoiding recursion | Level counting |
| **Intuitive?** | Most natural | Less intuitive | Natural for levels |

**Remember**: 
- Depth = 1 + max(leftDepth, rightDepth)
- Base case: null node has depth 0
- The +1 accounts for the current node
- Recursive solution is most elegant and interview-friendly

