# Maximum Depth of Binary Tree

## Problem Description

**Difficulty**: Easy

Given the `root` of a binary tree, return its **depth**.

The **depth** of a binary tree is defined as the number of nodes along the longest path from the root node down to the farthest leaf node.

**Key Concepts:**
- **Depth/Height**: Number of nodes on longest path from root to leaf
- **Recursive Solution**: Natural DFS approach
- **Iterative Solution**: BFS or DFS with queue/stack
- **Base Case**: Null node has depth 0
- **Recursive Formula**: `depth = 1 + max(leftDepth, rightDepth)`

**Visual Example:**
```
Tree:
       1
      / \
     2   3
        / \
       4   5

Longest paths:
  1 → 3 → 4: length 3 nodes
  1 → 3 → 5: length 3 nodes

Maximum Depth: 3
```

**Recommended Complexity**: O(n) time, O(n) space

---

## Examples

### Example 1 (Unbalanced Tree):
```
Input: root = [1,2,3,null,null,4]

Tree Structure:
       1
      / \
     2   3
        /
       4

Output: 3

Explanation:
Paths from root to leaves:
  1 → 2: length 2
  1 → 3 → 4: length 3 ← longest

Maximum depth: 3 nodes
```

### Example 2 (Empty Tree):
```
Input: root = []

Output: 0

Explanation:
Empty tree has no nodes
Depth is 0
```

### Example 3 (Single Node):
```
Input: root = [1]

Tree:
  1

Output: 1

Explanation:
Only root node
Depth is 1
```

### Example 4 (Complete Binary Tree):
```
Input: root = [1,2,3,4,5,6,7]

Tree:
       1
      / \
     2   3
    / \ / \
   4  5 6  7

Output: 3

Explanation:
All paths to leaves have length 3
  1 → 2 → 4: 3
  1 → 2 → 5: 3
  1 → 3 → 6: 3
  1 → 3 → 7: 3
Maximum: 3
```

### Example 5 (Left Skewed Tree):
```
Input: root = [1,2,null,3,null,4,null]

Tree:
    1
   /
  2
 /
3
/
4

Output: 4

Explanation:
Single path: 1 → 2 → 3 → 4
Length: 4 nodes
```

### Example 6 (Right Skewed Tree):
```
Input: root = [1,null,2,null,3,null,4]

Tree:
1
 \
  2
   \
    3
     \
      4

Output: 4

Explanation:
Single path: 1 → 2 → 3 → 4
Length: 4 nodes
```

### Example 7 (Two Nodes - Left Child):
```
Input: root = [1,2]

Tree:
  1
 /
2

Output: 2

Explanation:
Path: 1 → 2
Length: 2 nodes
```

### Example 8 (Two Nodes - Right Child):
```
Input: root = [1,null,2]

Tree:
1
 \
  2

Output: 2

Explanation:
Path: 1 → 2
Length: 2 nodes
```

### Example 9 (Three Nodes):
```
Input: root = [1,2,3]

Tree:
    1
   / \
  2   3

Output: 2

Explanation:
Two paths of equal length:
  1 → 2: 2 nodes
  1 → 3: 2 nodes
Maximum: 2
```

### Example 10 (Unbalanced - Right Heavy):
```
Input: root = [1,2,3,null,null,4,5,null,null,6]

Tree:
       1
      / \
     2   3
        / \
       4   5
          /
         6

Output: 4

Explanation:
Longest path: 1 → 3 → 5 → 6
Length: 4 nodes
```

---

## Constraints
- `0 <= The number of nodes in the tree <= 100`
- `-100 <= Node.val <= 100`

**Recommended Complexity**: 
- Time: O(n) where n = number of nodes
- Space: O(n) for recursion/queue (worst case)

---

## Pattern Recognition

**Primary Pattern**: **Tree Depth-First Search (DFS) - Post-Order**

**Why This Pattern?**
- **Height calculation**: Need info from children before computing parent
- **Post-order DFS**: Process children first, then current node
- **Bottom-up approach**: Depths propagate from leaves to root
- **Recursive formula**: `depth = 1 + max(leftDepth, rightDepth)`
- **Natural recursion**: Tree structure matches recursive solution

**Key Insight**: Bottom-Up Height Calculation
```
To find depth of a node:
  1. Find depth of left subtree
  2. Find depth of right subtree
  3. Current depth = 1 + max(left, right)

Base case:
  - Null node: depth = 0
  - Leaf node: depth = 1 (no children, so 1 + max(0, 0) = 1)

Recursive formula! ✓
```

**Visual: Recursive Depth Calculation**
```
Tree:
       1
      / \
     2   3
    /     \
   4       5

Bottom-Up Calculation:

Depth(4): 
  left = null → 0
  right = null → 0
  depth = 1 + max(0, 0) = 1

Depth(5):
  left = null → 0
  right = null → 0
  depth = 1 + max(0, 0) = 1

Depth(2):
  left = 4 → 1
  right = null → 0
  depth = 1 + max(1, 0) = 2

Depth(3):
  left = null → 0
  right = 5 → 1
  depth = 1 + max(0, 1) = 2

Depth(1):
  left = 2 → 2
  right = 3 → 2
  depth = 1 + max(2, 2) = 3 ✓

Maximum Depth: 3
```

**Why Post-Order DFS is Natural**:
```
Post-order: Process children before parent

For depth calculation:
  Need children's depths first
  Then compute parent's depth
  
Perfect match! ✓

Recursive calls:
  depth(left)    // Get left depth
  depth(right)   // Get right depth
  return 1 + max(left, right)  // Compute current

Post-order pattern! ✓
```

**Why Recursive is Simplest**:
```
Tree is recursive structure:
  - Node has left subtree
  - Node has right subtree
  
Depth is recursive calculation:
  - Depth of node = 1 + max(depth of subtrees)
  
Natural fit! ✓

Code:
  if (root == null) return 0;
  return 1 + Math.max(
    maxDepth(root.left),
    maxDepth(root.right)
  );

Elegant! ✓
```

**Alternative: Iterative BFS**:
```
Level-order traversal:
  Count number of levels
  Each level increments depth

Use queue:
  Start with root at level 1
  Process all nodes at current level
  Move to next level
  
Depth = number of levels ✓

Good for understanding, but recursive simpler!
```

**Alternative: Iterative DFS**:
```
Use stack with (node, depth) pairs:
  Track depth at each node
  Track maximum seen
  
While stack not empty:
  Pop (node, depth)
  Update max depth
  Push children with depth+1

Works but more complex than recursion!
```

**Visual: BFS Level-by-Level**
```
Tree:
       1
      / \
     2   3
        /
       4

BFS by levels:

Level 1: [1]         → depth so far: 1
Level 2: [2, 3]      → depth so far: 2
Level 3: [4]         → depth so far: 3
No more levels

Maximum Depth: 3 ✓
```

**Depth vs Height Clarification**:
```
In this problem:
  Depth = number of nodes on path
  
Some definitions use edges:
  Height = number of edges on path
  
For this problem:
  Count NODES, not edges
  
Single node: depth = 1 (not 0)
```

**Recursive Call Flow**:
```
maxDepth(root):
  if root == null:
    return 0           // Base case
  
  leftDepth = maxDepth(root.left)   // Recurse left
  rightDepth = maxDepth(root.right) // Recurse right
  
  return 1 + max(leftDepth, rightDepth) // Current depth

Simple and elegant! ✓
```

**Why +1 in Formula**:
```
Formula: 1 + max(leftDepth, rightDepth)

The +1 accounts for current node:
  leftDepth: max path through left subtree
  rightDepth: max path through right subtree
  +1: current node itself

Total: current node + longest subtree path ✓
```

**Core Operations**:

**Recursive Approach**:
```java
int maxDepth(TreeNode root):
    if root == null:
        return 0
    
    int leftDepth = maxDepth(root.left)
    int rightDepth = maxDepth(root.right)
    
    return 1 + Math.max(leftDepth, rightDepth)
```

**Iterative BFS**:
```java
int maxDepth(TreeNode root):
    if root == null:
        return 0
    
    queue = new LinkedList<>()
    queue.offer(root)
    depth = 0
    
    while !queue.isEmpty():
        size = queue.size()
        depth++
        
        for i = 0 to size-1:
            node = queue.poll()
            if node.left != null:
                queue.offer(node.left)
            if node.right != null:
                queue.offer(node.right)
    
    return depth
```

**Related Patterns**:
1. **Post-Order DFS** — Process children before parent
2. **Bottom-Up Recursion** — Information flows from leaves to root
3. **Level-Order BFS** — Count levels for depth
4. **Tree Height** — Same as maximum depth

---

## Algorithm & Approach

### Core Insight

**Why Recursive Depth Works:**
```
Key observations:
  1. Null node has depth 0
  2. Leaf node has depth 1
  3. Non-leaf depth = 1 + max(children depths)
  4. Depth calculation is post-order (children first)
  5. Must visit all nodes to find maximum: O(n)
```

**The Optimal Strategy**:
```
Recursive DFS (Simplest):
  - Base case: null → 0
  - Recursive case: 1 + max(left, right)
  - Natural post-order traversal
  - O(n) time, O(h) space

Iterative BFS (Alternative):
  - Level-order traversal
  - Count number of levels
  - O(n) time, O(w) space where w = max width
```

### Step-by-Step Algorithm

---

#### **Approach 1: Recursive DFS - SIMPLEST**

**Core Idea**:
- Base case: null node has depth 0
- Recursively find left subtree depth
- Recursively find right subtree depth
- Current depth = 1 + max(left, right)

**Algorithm**
```java
maxDepth(TreeNode root):
    // Base case
    if root == null:
        return 0
    
    // Recursively find depths of subtrees
    leftDepth = maxDepth(root.left)
    rightDepth = maxDepth(root.right)
    
    // Current depth = 1 + max of subtree depths
    return 1 + Math.max(leftDepth, rightDepth)
```

**Complete Code Implementation (Recursive)**
```java
class Solution {
    public int maxDepth(TreeNode root) {
        // Base case: null node has depth 0
        if (root == null) {
            return 0;
        }
        
        // Recursively find depth of left subtree
        int leftDepth = maxDepth(root.left);
        
        // Recursively find depth of right subtree
        int rightDepth = maxDepth(root.right);
        
        // Current node depth = 1 + max of children depths
        return 1 + Math.max(leftDepth, rightDepth);
    }
}
```

**Compact Version**
```java
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
}
```

**Example Walkthrough (Recursive)**

Input: root = [1,2,3,null,null,4]
```
Tree:
       1
      / \
     2   3
        /
       4
```

**Recursive Calls:**
```
maxDepth(1):
  leftDepth = maxDepth(2):
    leftDepth = maxDepth(null) = 0
    rightDepth = maxDepth(null) = 0
    return 1 + max(0, 0) = 1
  
  rightDepth = maxDepth(3):
    leftDepth = maxDepth(4):
      leftDepth = maxDepth(null) = 0
      rightDepth = maxDepth(null) = 0
      return 1 + max(0, 0) = 1
    rightDepth = maxDepth(null) = 0
    return 1 + max(1, 0) = 2
  
  return 1 + max(1, 2) = 3 ✓

Maximum Depth: 3
```

---

#### **Approach 2: Iterative BFS (Level-Order) - ALTERNATIVE**

**Core Idea**:
- Use queue for level-order traversal
- Count number of levels
- Each level increments depth counter
- Process all nodes at current level before moving to next

**Algorithm**
```java
maxDepth(TreeNode root):
    if root == null:
        return 0
    
    queue = new LinkedList<>()
    queue.offer(root)
    depth = 0
    
    while !queue.isEmpty():
        // Process entire current level
        levelSize = queue.size()
        depth++  // Increment depth for this level
        
        for i = 0 to levelSize-1:
            node = queue.poll()
            
            // Add children to queue for next level
            if node.left != null:
                queue.offer(node.left)
            if node.right != null:
                queue.offer(node.right)
    
    return depth
```

**Complete Code Implementation (BFS)**
```java
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        
        while (!queue.isEmpty()) {
            // Get size of current level
            int levelSize = queue.size();
            depth++;  // Increment depth for this level
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // Add children to queue for next level
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        
        return depth;
    }
}
```

**Example Walkthrough (BFS)**

Input: root = [1,2,3,null,null,4]
```
Tree:
       1
      / \
     2   3
        /
       4
```

**BFS Level-by-Level:**
```
Initial:
  queue = [1], depth = 0

Level 1:
  levelSize = 1
  depth = 1
  Process 1: add 2, 3
  queue = [2, 3]

Level 2:
  levelSize = 2
  depth = 2
  Process 2: no children
  Process 3: add 4
  queue = [4]

Level 3:
  levelSize = 1
  depth = 3
  Process 4: no children
  queue = []

Loop ends (queue empty)

Result: depth = 3 ✓
```

---

#### **Approach 3: Iterative DFS with Stack - ALTERNATIVE**

**Core Idea**:
- Use stack with (node, depth) pairs
- Track maximum depth seen
- DFS traversal updating max as we go

**Complete Code Implementation (DFS Stack)**
```java
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, 1));
        int maxDepth = 0;
        
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> pair = stack.pop();
            TreeNode node = pair.getKey();
            int depth = pair.getValue();
            
            // Update max depth
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

**Note**: Java doesn't have built-in Pair in all versions. Alternative:
```java
class Solution {
    private static class NodeDepth {
        TreeNode node;
        int depth;
        
        NodeDepth(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }
    
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Stack<NodeDepth> stack = new Stack<>();
        stack.push(new NodeDepth(root, 1));
        int maxDepth = 0;
        
        while (!stack.isEmpty()) {
            NodeDepth nd = stack.pop();
            maxDepth = Math.max(maxDepth, nd.depth);
            
            if (nd.node.left != null) {
                stack.push(new NodeDepth(nd.node.left, nd.depth + 1));
            }
            if (nd.node.right != null) {
                stack.push(new NodeDepth(nd.node.right, nd.depth + 1));
            }
        }
        
        return maxDepth;
    }
}
```

**Complexity Analysis**
- **Recursive**: O(n) time, O(h) space (call stack)
- **BFS**: O(n) time, O(w) space (queue, w = max width)
- **DFS Stack**: O(n) time, O(h) space (explicit stack)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Difficulty | Clean Code | Recommended |
|----------|------|-------|------------|------------|-------------|
| **Recursive DFS** | **O(n)** | **O(h)** | **Easy** | **Yes** | **✓ Simplest** |
| BFS Level-Order | O(n) | O(w) | Easy | Medium | Alternative |
| DFS Stack | O(n) | O(h) | Medium | Medium | Alternative |

**Winner**: **Recursive DFS** — simplest and most elegant

### Why Recursive is Most Natural

```
Depth calculation is inherently recursive:
  depth(node) = 1 + max(depth(left), depth(right))

Tree structure is recursive:
  - Node has left subtree
  - Node has right subtree

Perfect match! ✓

Code is extremely concise:
  if (root == null) return 0;
  return 1 + Math.max(
    maxDepth(root.left),
    maxDepth(root.right)
  );

Natural and elegant! ✓
```

### Why BFS Works Well for Depth

```
Depth = number of levels

BFS processes level-by-level:
  Level 1: process root
  Level 2: process children
  Level 3: process grandchildren
  ...
  
Count levels = depth ✓

Clear mental model!
Good alternative to recursion!
```

### Why Post-Order (Bottom-Up) is Key

```
Need children's depths before computing parent's:

Wrong approach (top-down):
  Can't know max depth going down
  
Right approach (bottom-up):
  Get children depths first
  Then compute parent: 1 + max(children)
  
Post-order DFS! ✓
```

### Why This is Optimal

```
Time complexity:
  Must visit all n nodes: Ω(n)
  Each node visited once: O(n)
  Optimal! ✓

Space complexity:
  Recursive/DFS Stack: O(h)
    Best (balanced): h = log n
    Worst (skewed): h = n
  BFS Queue: O(w)
    Best: w = 1
    Worst: w = n/2
  
  All O(n) worst case
  Acceptable! ✓

Recursive is simplest! ✓
```

---

## Critical Edge Cases & Gotchas

### 1. **Empty Tree (Null Root)**
```java
root = null
// Return 0 immediately
```

### 2. **Single Node**
```java
root = [1]
// Depth = 1
// Base case: no children, so 1 + max(0, 0) = 1
```

### 3. **Two Nodes - Left Child**
```java
    1
   /
  2

// Depth = 2
// Path: 1 → 2
```

### 4. **Two Nodes - Right Child**
```java
1
 \
  2

// Depth = 2
// Path: 1 → 2
```

### 5. **Balanced Tree**
```java
       1
      / \
     2   3
    / \ / \
   4  5 6  7

// All leaf paths same length
// Depth = 3
```

### 6. **Left Skewed (Worst Case)**
```java
    1
   /
  2
 /
3
/
4

// Depth = 4
// Space: O(4) = O(n) for recursion
```

### 7. **Right Skewed (Worst Case)**
```java
1
 \
  2
   \
    3
     \
      4

// Depth = 4
// Space: O(4) = O(n) for recursion
```

### 8. **Unbalanced - One Side Deeper**
```java
       1
      / \
     2   3
    /     \
   4       5
  /
 6

// Left deeper: 1 → 2 → 4 → 6 (4 nodes)
// Right: 1 → 3 → 5 (3 nodes)
// Max: 4
```

### 9. **Complete Binary Tree (BFS Worst Case)**
```java
       1
      / \
     2   3
    / \ / \
   4  5 6  7

// Last level: 4 nodes = n/2
// BFS queue space: O(n/2) = O(n)
```

### 10. **Negative Node Values**
```java
     -1
    /  \
  -5    5

// Values don't affect depth
// Depth = 2
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Handling Null Root**
```java
// WRONG - no null check
public int maxDepth(TreeNode root) {
    int leftDepth = maxDepth(root.left);   // NullPointerException if root is null! ❌
    int rightDepth = maxDepth(root.right);
    return 1 + Math.max(leftDepth, rightDepth);
}
```

**Why wrong**: Null pointer exception!

**Fix**: Check null first
```java
if (root == null) {  ✓
    return 0;
}
```

### ❌ **MISTAKE 2: Forgetting +1 in Formula**
```java
// WRONG - missing +1 for current node
public int maxDepth(TreeNode root) {
    if (root == null) return 0;
    
    int leftDepth = maxDepth(root.left);
    int rightDepth = maxDepth(root.right);
    
    return Math.max(leftDepth, rightDepth);  // ❌ Missing +1!
}
```

**Why wrong**: Not counting current node!

**Dry run failure:**
```
Tree: 1 → 2

maxDepth(1):
  leftDepth = maxDepth(2):
    return 0  (no children, but should be 1)
  rightDepth = 0
  return max(0, 0) = 0  (should be 1)

Result: 0 (should be 2) ❌
```

**Fix**: Add +1 for current node
```java
return 1 + Math.max(leftDepth, rightDepth);  ✓
```

### ❌ **MISTAKE 3: Counting Edges Instead of Nodes**
```java
// WRONG - counting edges (common misunderstanding)
// This gives height in terms of edges, not nodes

// If problem asks for nodes:
Tree: 1 → 2 → 3

Edges: 2
Nodes: 3

Make sure to count correctly based on problem!
```

**Why wrong**: Problem specifies "number of nodes"!

**Fix**: Use formula with +1 (counts nodes)
```java
return 1 + Math.max(leftDepth, rightDepth);  ✓
// The +1 ensures we count nodes
```

### ❌ **MISTAKE 4: BFS - Not Processing Full Level**
```java
// WRONG - not using level size
while (!queue.isEmpty()) {
    TreeNode node = queue.poll();  // ❌ Only one node per iteration
    depth++;
    
    if (node.left != null) queue.offer(node.left);
    if (node.right != null) queue.offer(node.right);
}
```

**Why wrong**: Increments depth for each node, not each level!

**Dry run failure:**
```
Tree: 1 → (2, 3)

Process 1: depth = 1, add 2, 3
Process 2: depth = 2  ❌ (should still be 2)
Process 3: depth = 3  ❌ (should still be 2)

Result: 3 (should be 2) ❌
```

**Fix**: Process entire level at once
```java
while (!queue.isEmpty()) {
    int levelSize = queue.size();  ✓
    depth++;
    
    for (int i = 0; i < levelSize; i++) {  ✓
        TreeNode node = queue.poll();
        // Add children...
    }
}
```

### ❌ **MISTAKE 5: Using Min Instead of Max**
```java
// WRONG - using min instead of max
return 1 + Math.min(leftDepth, rightDepth);  // ❌
```

**Why wrong**: Need maximum depth, not minimum!

**Dry run failure:**
```
Tree:
    1
   / \
  2   3
 /
4

leftDepth = 2 (path 1→2→4)
rightDepth = 1 (path 1→3)

min(2, 1) = 1
1 + 1 = 2

But maximum depth is 3! ❌
```

**Fix**: Use max
```java
return 1 + Math.max(leftDepth, rightDepth);  ✓
```

### ❌ **MISTAKE 6: DFS Stack - Not Tracking Depth**
```java
// WRONG - not tracking depth with nodes
Stack<TreeNode> stack = new Stack<>();
stack.push(root);
int maxDepth = 0;

while (!stack.isEmpty()) {
    TreeNode node = stack.pop();
    maxDepth++;  // ❌ Wrong! Counts nodes, not depth
    
    if (node.left != null) stack.push(node.left);
    if (node.right != null) stack.push(node.right);
}
```

**Why wrong**: Not tracking depth per node!

**Fix**: Store (node, depth) pairs
```java
Stack<NodeDepth> stack = new Stack<>();  ✓
stack.push(new NodeDepth(root, 1));
int maxDepth = 0;

while (!stack.isEmpty()) {
    NodeDepth nd = stack.pop();
    maxDepth = Math.max(maxDepth, nd.depth);  ✓
    
    if (nd.node.left != null) {
        stack.push(new NodeDepth(nd.node.left, nd.depth + 1));  ✓
    }
    // ...
}
```

### ❌ **MISTAKE 7: BFS - Initializing Depth to 1 Instead of 0**
```java
// WRONG - depth starts at 1 before processing
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);
int depth = 1;  // ❌ Should start at 0

while (!queue.isEmpty()) {
    int levelSize = queue.size();
    depth++;  // ❌ Now starts at 2 for first level!
    // ...
}
```

**Why wrong**: First increment makes it 2!

**Fix**: Start at 0
```java
int depth = 0;  ✓

while (!queue.isEmpty()) {
    int levelSize = queue.size();
    depth++;  // Now correctly 1 for first level ✓
    // ...
}
```

### ❌ **MISTAKE 8: Recursive - Not Returning Result**
```java
// WRONG - void return type
public void maxDepth(TreeNode root) {  // ❌
    if (root == null) return;
    
    int leftDepth = maxDepth(root.left);   // Compile error!
    int rightDepth = maxDepth(root.right);
    // ...
}
```

**Why wrong**: Need to return int!

**Fix**: Return int
```java
public int maxDepth(TreeNode root) {  ✓
    if (root == null) return 0;  ✓
    
    int leftDepth = maxDepth(root.left);
    int rightDepth = maxDepth(root.right);
    return 1 + Math.max(leftDepth, rightDepth);  ✓
}
```

### ❌ **MISTAKE 9: Confusing Height and Depth**
```java
// WRONG - if problem asks for depth but you compute height
// (In some definitions, height counts edges, depth counts nodes)

// This problem: depth = nodes on path
// Some definitions: height = edges on path

// Make sure to understand problem definition!
```

**Why wrong**: Different definitions in different contexts!

**Fix**: Read problem carefully
```java
// This problem: "number of nodes along longest path"
// Use: 1 + max(left, right)  ✓
// Counts nodes correctly
```

### ❌ **MISTAKE 10: BFS - Not Checking Null Before Enqueue**
```java
// WRONG - enqueueing without null check
queue.offer(node.left);   // ❌ What if null?
queue.offer(node.right);  // ❌ What if null?
```

**Why wrong**: Processing null nodes!

**Fix**: Check before enqueue
```java
if (node.left != null) {  ✓
    queue.offer(node.left);
}
if (node.right != null) {  ✓
    queue.offer(node.right);
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

```
Where n = number of nodes

All approaches:
  - Must visit each node to find maximum path
  - Each node processed once
  - Total: O(n)

Recursive:
  T(n) = T(left) + T(right) + O(1)
       = O(n)

Iterative (BFS/DFS):
  Each node enqueued/dequeued once: O(n)
```

**Detailed Analysis**:
```
For each node:
  - Recursive: visited once in DFS
  - BFS: enqueued once, dequeued once
  - DFS Stack: pushed once, popped once
  
All O(n) ✓
```

### Space Complexity

**Recursive: O(h)** where h = height
```
Space for call stack:
  - Best case (balanced): h = log n → O(log n)
  - Worst case (skewed): h = n → O(n)
  - Average: O(log n)

Recursion depth = tree height
```

**BFS (Queue): O(w)** where w = max width
```
Space for queue:
  - Best case: w = 1 (skewed tree) → O(1)
  - Worst case: w = n/2 (complete tree last level) → O(n)
  - Average: O(n)

Maximum nodes in queue = tree width at widest level
```

**DFS (Stack): O(h)** where h = height
```
Space for explicit stack:
  - Same as recursive call stack
  - Best: O(log n)
  - Worst: O(n)
```

### Optimal Complexity

```
Time: O(n)
  - Must visit all nodes: Ω(n)
  - Each node O(1) work
  - Total O(n)
  - Optimal! ✓

Space:
  - Recursive/DFS Stack: O(h)
  - BFS Queue: O(w)
  - Both O(n) worst case
  - Acceptable! ✓

Can't do better than O(n) time! ✓
```

---

## Visualization

### Complete Example Walkthrough (Recursive)

**Input:** `root = [1,2,3,null,null,4,5]`

```
Tree:
       1
      / \
     2   3
        / \
       4   5
```

**Recursive Call Stack (Bottom-Up):**

```
maxDepth(1):
  leftDepth = maxDepth(2):
    leftDepth = maxDepth(null) = 0
    rightDepth = maxDepth(null) = 0
    return 1 + max(0, 0) = 1
  
  rightDepth = maxDepth(3):
    leftDepth = maxDepth(4):
      leftDepth = maxDepth(null) = 0
      rightDepth = maxDepth(null) = 0
      return 1 + max(0, 0) = 1
    
    rightDepth = maxDepth(5):
      leftDepth = maxDepth(null) = 0
      rightDepth = maxDepth(null) = 0
      return 1 + max(0, 0) = 1
    
    return 1 + max(1, 1) = 2
  
  return 1 + max(1, 2) = 3 ✓

Maximum Depth: 3
```

**Visual Depth Calculation:**
```
Tree with depths marked:

       1 (depth 3)
      / \
     2   3 (depth 2)
        / \
       4   5 (depth 1)

Bottom-up calculation:
  Nodes 4, 5: depth 1 (leaves)
  Node 3: depth 1 + max(1, 1) = 2
  Node 2: depth 1 + max(0, 0) = 1
  Node 1: depth 1 + max(1, 2) = 3 ✓
```

---

### Complete Example Walkthrough (BFS)

**Input:** `root = [1,2,3,null,null,4,5]`

**BFS Level-by-Level:**

```
Initial:
  queue = [1], depth = 0

Level 1:
  levelSize = 1
  depth = 1
  Process 1: add children 2, 3
  queue = [2, 3]

Level 2:
  levelSize = 2
  depth = 2
  Process 2: no children
  Process 3: add children 4, 5
  queue = [4, 5]

Level 3:
  levelSize = 2
  depth = 3
  Process 4: no children
  Process 5: no children
  queue = []

Loop ends (queue empty)

Result: depth = 3 ✓
```

---

### Visual: Comparison of Approaches

```
Tree:
       1
      / \
     2   3
        /
       4

Recursive DFS:
  Call stack grows to depth 3
  Space: O(3) = O(h)
  
  Stack state at deepest:
    maxDepth(1) waiting
    maxDepth(3) waiting
    maxDepth(4) executing
    
  Returns: 3

BFS:
  Queue at widest: [2, 3] (2 nodes)
  Space: O(2) = O(w)
  
  Levels:
    Level 1: [1] → depth 1
    Level 2: [2, 3] → depth 2
    Level 3: [4] → depth 3
    
  Returns: 3

Both correct! ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Difficulty | Clean Code | Recommended |
|----------|------|-------|------------|------------|-------------|
| **Recursive DFS** | **O(n)** | **O(h)** | **Easy** | **Yes** | **✓ Simplest** |
| BFS Level-Order | O(n) | O(w) | Easy | Medium | Alternative |
| DFS Stack | O(n) | O(h) | Medium | Medium | Alternative |

**When to Use Each:**
- **Interview default**: Recursive DFS (simplest, most elegant)
- **Prefer iterative**: BFS (clear level-by-level)
- **Wide tree**: DFS Stack (better than BFS for space)

---

## Key Takeaways

1. **Depth = nodes on longest path**: From root to farthest leaf
2. **Recursive formula**: `depth = 1 + max(leftDepth, rightDepth)`
3. **Base case**: Null node has depth 0
4. **Post-order DFS**: Process children before parent
5. **+1 for current node**: Don't forget in formula
6. **BFS counts levels**: Alternative approach
7. **Visit all nodes**: O(n) time required
8. **Space varies**: O(h) recursive, O(w) BFS
9. **Bottom-up calculation**: Information flows from leaves to root
10. **Simplest solution**: Three-line recursive function

---

## Interview Tips

**What to say in an interview:**

> "I need to find the maximum depth of the binary tree, which is the number of nodes along the longest path from root to leaf. I'll use a recursive DFS approach since it's the most natural for this problem.
>
> The key insight is that the depth of any node equals 1 plus the maximum depth of its subtrees. The base case is when we reach a null node, which has depth 0. For any non-null node, I recursively find the depth of the left subtree and the depth of the right subtree, then return 1 plus the maximum of those two values. The +1 accounts for the current node itself.
>
> This is a post-order DFS traversal because we need information from the children before we can compute the parent's depth. The information flows bottom-up from the leaves to the root.
>
> Time complexity is O(n) since we visit each of the n nodes exactly once. Space complexity is O(h) where h is the tree height, due to the recursion call stack. For a balanced tree that's O(log n), but for a skewed tree it could be O(n).
>
> An alternative is BFS with a queue, processing level by level and counting the number of levels. This also runs in O(n) time but uses O(w) space where w is the maximum width of the tree."

**Key points to mention:**
1. **Formula**: `1 + max(leftDepth, rightDepth)`
2. **Base case**: Null returns 0
3. **Recursive approach**: Natural for tree depth
4. **Post-order**: Children before parent
5. **+1 for current**: Counts current node
6. **Time**: O(n) visit all nodes
7. **Space**: O(h) recursion stack
8. **BFS alternative**: Count levels
9. **Bottom-up**: Information from leaves to root
10. **Simple code**: Can write in 3 lines

**Common Follow-ups:**
- "Can you do it iteratively?" → Yes, with BFS (count levels) or DFS (track depth with nodes)
- "Why +1 in the formula?" → Accounts for current node in the path
- "What's the space complexity?" → O(h) recursive, worst O(n) for skewed tree
- "Could you use BFS instead?" → Yes, count number of levels
- "What if we wanted minimum depth?" → Similar but need to handle non-full branches carefully

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Maximum Depth of Binary Tree** | Easy | **DFS Post-Order** | **This problem** |
| Minimum Depth of Binary Tree | Easy | DFS/BFS | Find shortest path to leaf |
| Balanced Binary Tree | Easy | DFS | Check if depth difference ≤ 1 |
| Diameter of Binary Tree | Easy | DFS | Longest path between any two nodes |
| Maximum Depth of N-ary Tree | Easy | DFS | Same concept, N children |
| Binary Tree Level Order Traversal | Medium | BFS | Return nodes at each level |
| Lowest Common Ancestor | Medium | DFS | Find common ancestor at depth |

**Pattern Progression**:
1. **Maximum Depth** — Learn post-order DFS depth calculation
2. **Minimum Depth** — Apply similar approach with different logic
3. **Balanced Tree** — Use depth calculation at each node
4. **Diameter** — Combine left and right depths differently

---

## Final Pattern Label

✅ **Tree Post-Order DFS - Bottom-Up Depth Calculation**

**Remember:** This problem finds **maximum depth** (number of nodes on longest path from root to farthest leaf) using **recursive post-order DFS**. **Core formula**: `depth = 1 + max(leftDepth, rightDepth)` where **+1 counts current node**, max selects deeper subtree. **Base case**: `if (root == null) return 0` — null node has depth 0. **Recursive case**: recursively get left subtree depth (`int leftDepth = maxDepth(root.left)`), recursively get right subtree depth (`int rightDepth = maxDepth(root.right)`), return 1 + max. **Post-order pattern**: must process **children before parent** because parent's depth depends on children's depths (bottom-up information flow from leaves to root). **Compact 3-line code**: `if (root == null) return 0; return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));` — extremely elegant. **Alternative BFS**: use queue for level-order traversal, process entire level at once using `levelSize = queue.size()` loop, increment depth counter per level, count total levels. **Time complexity**: O(n) must visit all nodes to find maximum path. **Space complexity**: O(h) for recursive call stack (best O(log n) balanced, worst O(n) skewed), O(w) for BFS queue where w = max width (worst O(n/2) for complete tree last level). **Common mistakes**: forgetting +1 in formula (doesn't count current node), using min instead of max (finds minimum not maximum), not handling null root (NullPointerException), BFS not processing full level at once (increments depth per node not per level), counting edges instead of nodes (wrong interpretation), starting BFS depth at 1 instead of 0 (off-by-one). **Why post-order**: need children's depths first to compute parent's depth (can't determine depth going down, only coming up). Pattern: **bottom-up recursive DFS** with **1 + max(children)** formula for **tree depth/height calculation problems**! ✓
