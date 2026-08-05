# Diameter of Binary Tree

## Problem Description

**Difficulty**: Easy

The **diameter** of a binary tree is defined as the **length of the longest path between any two nodes** within the tree. The path does not necessarily have to pass through the root.

The **length of a path** between two nodes in a binary tree is the **number of edges** between the nodes. Note that the path cannot include the same node twice.

Given the root of a binary tree `root`, return the **diameter** of the tree.

**Key Concepts:**
- **Diameter**: Longest path between any two nodes (measured in edges)
- **Path doesn't need root**: Can be anywhere in the tree
- **Edges not nodes**: Path length counts edges, not nodes
- **Global maximum**: Track diameter across all nodes
- **Height-based calculation**: Diameter at node = leftHeight + rightHeight

**Visual Example:**
```
Tree:
       1
      / \
     2   3
    / \
   4   5

Longest path: 4 → 2 → 1 → 3 (or 5 → 2 → 1 → 3)
Number of edges: 3

Diameter: 3 edges
```

**Critical Distinction:**
```
Maximum Depth: counts NODES on path
Diameter: counts EDGES on path

Tree: 1 → 2 → 3
  Max Depth: 3 nodes
  Diameter: 2 edges

Different problems! ✓
```

**Recommended Complexity**: O(n) time, O(n) space

---

## Examples

### Example 1 (Path Not Through Root):
```
Input: root = [1,null,2,3,4,5]

Tree Structure:
    1
     \
      2
     / \
    3   4
   /
  5

Output: 3

Explanation:
Longest paths (3 edges each):
  Path 1: 5 → 3 → 2 → 4 (doesn't go through root 1)
  Path 2: 1 → 2 → 3 → 5 (goes through root)

The path 5 → 3 → 2 → 4 has 3 edges
Diameter = 3
```

### Example 2 (Simple Tree):
```
Input: root = [1,2,3]

Tree:
    1
   / \
  2   3

Output: 2

Explanation:
Longest path: 2 → 1 → 3
Number of edges: 2
Diameter = 2
```

### Example 3 (Single Node):
```
Input: root = [1]

Tree:
  1

Output: 0

Explanation:
No edges in tree (only one node)
Diameter = 0 edges
```

### Example 4 (Two Nodes):
```
Input: root = [1,2]

Tree:
  1
 /
2

Output: 1

Explanation:
One edge: 2 → 1
Diameter = 1 edge
```

### Example 5 (Complete Binary Tree):
```
Input: root = [1,2,3,4,5,6,7]

Tree:
       1
      / \
     2   3
    / \ / \
   4  5 6  7

Output: 4

Explanation:
Longest path: 4 → 2 → 1 → 3 → 6 (or 7)
Number of edges: 4
Diameter = 4
```

### Example 6 (Left Skewed Tree):
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

Output: 3

Explanation:
Longest path: 4 → 3 → 2 → 1
Number of edges: 3
Diameter = 3
```

### Example 7 (Right Skewed Tree):
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

Output: 3

Explanation:
Longest path: 1 → 2 → 3 → 4
Number of edges: 3
Diameter = 3
```

### Example 8 (Unbalanced - Diameter Not Through Root):
```
Input: root = [1,2,3,4,5,null,null,6,7]

Tree:
       1
      / \
     2   3
    / \
   4   5
  / \
 6   7

Output: 4

Explanation:
Longest path: 6 → 4 → 2 → 5 (doesn't go through root 1)
Number of edges: 4
Diameter = 4 edges

Note: Height at node 2:
  left height (to 4): 2 edges
  right height (to 5): 1 edge
  diameter through 2: 2 + 1 = 3

Height at node 4:
  left height (to 6): 1 edge
  right height (to 7): 1 edge
  diameter through 4: 1 + 1 = 2

But path from 6 through 4, 2, to 5: 4 edges ✓
```

### Example 9 (Balanced Tree):
```
Input: root = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]

Tree:
           1
         /   \
        2     3
       / \   / \
      4   5 6   7
     /|\ /|\ ...
   8 9...

Output: 6

Explanation:
Longest path goes from leftmost leaf to rightmost leaf
Through root: left height + right height
Diameter = 3 + 3 = 6 edges
```

### Example 10 (Three Nodes - Right Heavy):
```
Input: root = [1,2,3,null,null,4,5]

Tree:
       1
      / \
     2   3
        / \
       4   5

Output: 3

Explanation:
Longest path: 2 → 1 → 3 → 4 (or 5)
Number of edges: 3
Diameter = 3
```

---

## Constraints
- `1 <= number of nodes in the tree <= 100`
- `-100 <= Node.val <= 100`

**Recommended Complexity**: 
- Time: O(n) where n = number of nodes
- Space: O(n) for recursion stack (worst case)

---

## Pattern Recognition

**Primary Pattern**: **Tree DFS with Global State Tracking**

**Why This Pattern?**
- **Diameter anywhere**: Path can be at any node, not just root
- **Height-based formula**: Diameter at node = leftHeight + rightHeight
- **Global tracking**: Need to remember maximum across all nodes
- **Post-order DFS**: Calculate heights bottom-up, update diameter
- **Return height, track diameter**: Two pieces of information

**Key Insight**: Diameter vs Height Relationship
```
For any node:
  - Height: longest path down to a leaf (edges)
  - Diameter through node: left height + right height

Why?
  Longest path through this node:
    - Goes down left subtree: leftHeight edges
    - Through current node: 0 edges (it's a point)
    - Goes down right subtree: rightHeight edges
    - Total: leftHeight + rightHeight edges ✓

Check all nodes, track maximum! ✓
```

**Visual: Diameter Calculation**
```
Tree:
       1
      / \
     2   3
    / \
   4   5

At each node, calculate diameter:

Node 4:
  leftHeight = 0, rightHeight = 0
  diameter = 0 + 0 = 0

Node 5:
  leftHeight = 0, rightHeight = 0
  diameter = 0 + 0 = 0

Node 2:
  leftHeight = 1 (to 4), rightHeight = 1 (to 5)
  diameter = 1 + 1 = 2 ✓
  (path: 4 → 2 → 5)

Node 3:
  leftHeight = 0, rightHeight = 0
  diameter = 0 + 0 = 0

Node 1:
  leftHeight = 2 (to 4 or 5), rightHeight = 1 (to 3)
  diameter = 2 + 1 = 3 ✓✓
  (path: 4 → 2 → 1 → 3 or 5 → 2 → 1 → 3)

Maximum diameter: 3 ✓
```

**Why Global Variable is Needed**:
```
Problem: Return diameter, but recursion returns height

Solution:
  - Use global variable for diameter
  - Recursion returns height (for parent's calculation)
  - Update global diameter at each node
  
Two pieces of info:
  1. Return value: height (for parent)
  2. Side effect: update global diameter

Classic pattern! ✓
```

**Why Can't Just Use Root**:
```
Wrong approach:
  diameter = leftHeight(root) + rightHeight(root)

Problem:
  Diameter might not pass through root!

Example:
       1
      /
     2
    / \
   3   4

Root 1:
  leftHeight = 2, rightHeight = 0
  diameter = 2 + 0 = 2

But actual diameter (3 → 2 → 4) = 2 edges
Through node 2: leftHeight=1, rightHeight=1 → 1+1=2 ✓

Need to check ALL nodes! ✓
```

**Height Calculation (Edges)**:
```
Height in edges (not nodes):

Null node: height = -1 (below leaf)
OR
Null node: height = 0, leaf = 0

Common convention for this problem:
  Null: -1
  Leaf: 0 (max(-1, -1) + 1 = 0)
  Parent of leaf: 1 (max(0, 0) + 1 = 1)

OR alternative:
  Null: 0
  Leaf: 0 (max(0, 0) = 0, no +1 for null)
  Parent of leaf: 1

Use first convention (null = -1) ✓
```

**Recursive Formula**:
```
height(node):
  if node == null:
    return -1  // Below leaf level
  
  leftHeight = height(node.left)
  rightHeight = height(node.right)
  
  // Update global diameter
  diameter = max(diameter, leftHeight + rightHeight + 2)
  
  // Return height for parent
  return max(leftHeight, rightHeight) + 1

Wait, +2? Why?

If null = -1:
  Leaf node:
    leftHeight = -1, rightHeight = -1
    diameter = -1 + -1 + 2 = 0 ✓ (correct, leaf has 0 diameter)
    height = max(-1, -1) + 1 = 0 ✓

  Node with two children:
    leftHeight = 0, rightHeight = 0
    diameter = 0 + 0 + 2 = 2 ✓ (two edges)
    
Cleaner alternative: null = 0, don't add +2
  But then leaf height = 0 still
  
Let's use simpler: null returns 0 ✓
```

**Simplified Formula (null = 0)**:
```
height(node):
  if node == null:
    return 0
  
  leftHeight = height(node.left)
  rightHeight = height(node.right)
  
  // Diameter at this node
  currentDiameter = leftHeight + rightHeight
  diameter = max(diameter, currentDiameter)
  
  // Height to return for parent
  return max(leftHeight, rightHeight) + 1

Clean! ✓
```

**Why Post-Order DFS**:
```
Need children's heights before computing:
  1. Diameter at current node
  2. Height of current node

Post-order: left → right → process current

Perfect fit! ✓
```

**Visual: Information Flow**
```
Tree:
       1
      / \
     2   3
    / \
   4   5

Bottom-up processing:

Node 4: height=1, diameter through it=0
Node 5: height=1, diameter through it=0
Node 2: height=2, diameter through it=2 (4→2→5)
Node 3: height=1, diameter through it=0
Node 1: height=3, diameter through it=3 (4→2→1→3)

Max diameter tracked: 3 ✓
```

**Core Operations**:

**Recursive with Global Variable**:
```java
int diameter = 0;  // Global variable

int height(TreeNode root):
    if root == null:
        return 0
    
    leftHeight = height(root.left)
    rightHeight = height(root.right)
    
    // Update global diameter
    diameter = max(diameter, leftHeight + rightHeight)
    
    // Return height for parent
    return max(leftHeight, rightHeight) + 1

diameterOfBinaryTree(TreeNode root):
    diameter = 0
    height(root)
    return diameter
```

**Related Patterns**:
1. **Maximum Depth** — Similar post-order DFS, but returns depth directly
2. **Global State Tracking** — Use instance variable for running maximum
3. **Diameter Calculation** — Height-based formula at each node
4. **Path Problems** — Combine information from left and right subtrees

---

## Algorithm & Approach

### Core Insight

**Why Height-Based Diameter Works:**
```
Key observations:
  1. Diameter = longest path between any two nodes (edges)
  2. Path through node = left height + right height
  3. Need to check ALL nodes for maximum
  4. Use global variable to track max across nodes
  5. Recursion returns height, updates diameter as side effect
```

**The Optimal Strategy**:
```
Recursive DFS (Only O(n) Solution):
  - Post-order traversal (children before parent)
  - Calculate height at each node
  - Update global diameter: leftHeight + rightHeight
  - Return height for parent's calculation
  - O(n) time, O(h) space

No iterative alternative:
  - Need height information from children
  - Would need to store heights separately
  - Recursive is natural and optimal
```

### Step-by-Step Algorithm

---

#### **Approach 1: Recursive DFS with Global State - OPTIMAL**

**Core Idea**:
- Use instance/global variable to track maximum diameter
- Recursive helper returns height of subtree (in edges)
- At each node: update diameter = leftHeight + rightHeight
- Return max(leftHeight, rightHeight) + 1 as height to parent

**Algorithm**
```java
diameterOfBinaryTree(TreeNode root):
    diameter = 0  // Global/instance variable
    height(root)
    return diameter

height(TreeNode node):
    if node == null:
        return 0  // Null has height 0
    
    // Recursively get heights of subtrees
    leftHeight = height(node.left)
    rightHeight = height(node.right)
    
    // Update global diameter (path through this node)
    diameter = max(diameter, leftHeight + rightHeight)
    
    // Return height of this subtree to parent
    return max(leftHeight, rightHeight) + 1
```

**Complete Code Implementation**
```java
class Solution {
    private int diameter = 0;  // Instance variable to track max diameter
    
    public int diameterOfBinaryTree(TreeNode root) {
        height(root);
        return diameter;
    }
    
    private int height(TreeNode node) {
        // Base case: null node has height 0
        if (node == null) {
            return 0;
        }
        
        // Recursively calculate height of left subtree
        int leftHeight = height(node.left);
        
        // Recursively calculate height of right subtree
        int rightHeight = height(node.right);
        
        // Update diameter if path through this node is longer
        diameter = Math.max(diameter, leftHeight + rightHeight);
        
        // Return height of this subtree (max of children + 1)
        return Math.max(leftHeight, rightHeight) + 1;
    }
}
```

**Example Walkthrough**

Input: root = [1,2,3,4,5]
```
Tree:
       1
      / \
     2   3
    / \
   4   5
```

**Recursive Execution:**
```
Initial: diameter = 0

height(1):
  leftHeight = height(2):
    leftHeight = height(4):
      leftHeight = height(null) = 0
      rightHeight = height(null) = 0
      diameter = max(0, 0 + 0) = 0
      return max(0, 0) + 1 = 1
    
    rightHeight = height(5):
      leftHeight = height(null) = 0
      rightHeight = height(null) = 0
      diameter = max(0, 0 + 0) = 0
      return max(0, 0) + 1 = 1
    
    diameter = max(0, 1 + 1) = 2  ← Updated!
    return max(1, 1) + 1 = 2
  
  rightHeight = height(3):
    leftHeight = height(null) = 0
    rightHeight = height(null) = 0
    diameter = max(2, 0 + 0) = 2
    return max(0, 0) + 1 = 1
  
  diameter = max(2, 2 + 1) = 3  ← Updated!
  return max(2, 1) + 1 = 3

Return diameter = 3 ✓

Path visualization:
  Node 2: diameter = 1 + 1 = 2 (path: 4 → 2 → 5)
  Node 1: diameter = 2 + 1 = 3 (path: 4 → 2 → 1 → 3)
  Maximum: 3 edges ✓
```

**Detailed Step-by-Step with Edges:**
```
Leaf nodes (4, 5, 3):
  - No children
  - Height = 1 (one edge to parent)
  - Diameter through them = 0

Node 2 (parent of 4 and 5):
  - leftHeight = 1 (one edge to 4)
  - rightHeight = 1 (one edge to 5)
  - Diameter through 2 = 1 + 1 = 2 edges
  - Path: 4 → 2 → 5 (2 edges) ✓
  - Height = max(1, 1) + 1 = 2 (two edges to leaf)

Node 1 (root):
  - leftHeight = 2 (two edges to leaves through 2)
  - rightHeight = 1 (one edge to 3)
  - Diameter through 1 = 2 + 1 = 3 edges
  - Path: 4 → 2 → 1 → 3 (3 edges) ✓
  - Height = max(2, 1) + 1 = 3

Maximum diameter: 3 ✓
```

**Why Height Returns Edge Count:**
```
With null = 0:

Leaf node:
  left = null → 0
  right = null → 0
  height = max(0, 0) + 1 = 1
  
This means: 1 edge from leaf to parent ✓

Parent of leaf:
  left = 1 (one edge to leaf)
  right = 0 or similar
  height = max(1, 0) + 1 = 2
  
This means: 2 edges from this node to furthest leaf ✓

Counts edges correctly! ✓
```

**Alternative: Using -1 for Null**
```java
private int height(TreeNode node) {
    if (node == null) {
        return -1;  // -1 for null
    }
    
    int leftHeight = height(node.left);
    int rightHeight = height(node.right);
    
    // Diameter calculation same
    diameter = Math.max(diameter, leftHeight + rightHeight + 2);
    
    return Math.max(leftHeight, rightHeight) + 1;
}
```

**Why +2 with null=-1?**
```
Leaf node:
  leftHeight = -1, rightHeight = -1
  diameter = -1 + -1 + 2 = 0 ✓
  
Node with two leaves:
  leftHeight = 0, rightHeight = 0
  diameter = 0 + 0 + 2 = 2 ✓

The +2 compensates for the -1s!

Simpler to use null = 0 without +2 ✓
```

**Complexity Analysis**
- **Time**: O(n) — visit each node once
- **Space**: O(h) — recursion stack, h = height

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Difficulty | Optimal | Recommended |
|----------|------|-------|------------|---------|-------------|
| **Recursive DFS + Global** | **O(n)** | **O(h)** | **Medium** | **Yes** | **✓ Only solution** |
| Brute Force (height at each node) | O(n²) | O(h) | Easy | No | ❌ Too slow |

**Winner**: **Recursive DFS** — only O(n) solution

### Why Global Variable is Necessary

```
Problem structure:
  - Need to return diameter (main answer)
  - Need to return height (for parent's calculation)
  - Can't return two values directly

Solution:
  - Return height from helper
  - Update global diameter as side effect
  
Classic two-value problem! ✓
```

### Why Can't Return Diameter Directly

```
Wrong approach:
  maxDiameter(node):
    return max diameter in subtree rooted at node

Problem:
  Parent needs HEIGHT, not diameter!
  
Example:
       1
      / \
     2   3
    / \
   4   5

Node 2's diameter = 2
But node 1 needs node 2's HEIGHT (2 edges)
Not node 2's diameter!

Must return height, track diameter separately ✓
```

### Why O(n²) Brute Force is Too Slow

```
Brute force:
  For each node:
    Calculate leftHeight: O(n)
    Calculate rightHeight: O(n)
    Update max diameter
  
Total: O(n) nodes × O(n) height = O(n²)

Optimized:
  Single DFS traversal
  Calculate height once per node
  Update diameter during same traversal
  
Total: O(n) ✓

Much better! ✓
```

### Why This is Optimal

```
Time complexity:
  Must visit all nodes: Ω(n)
  Each node visited once: O(n)
  Optimal! ✓

Space complexity:
  Recursion stack: O(h)
  Best (balanced): O(log n)
  Worst (skewed): O(n)
  Acceptable! ✓

Can't do better than O(n) time! ✓
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Node**
```java
root = [1]

// No edges (only one node)
// Diameter = 0
// Height = 1

height calculation:
  leftHeight = 0, rightHeight = 0
  diameter = 0 + 0 = 0 ✓
  return max(0, 0) + 1 = 1
```

### 2. **Two Nodes**
```java
root = [1,2]

// One edge: 2 → 1
// Diameter = 1

height(1):
  leftHeight = height(2) = 1
  rightHeight = 0
  diameter = 1 + 0 = 1 ✓
```

### 3. **Complete Binary Tree**
```java
       1
      / \
     2   3
    / \ / \
   4  5 6  7

// Diameter through root
// leftHeight = 2, rightHeight = 2
// diameter = 2 + 2 = 4 ✓
```

### 4. **Left Skewed (Diameter Through Root)**
```java
    1
   /
  2
 /
3

// All edges in line
// Diameter = 2 (3 → 2 → 1)
```

### 5. **Diameter Not Through Root**
```java
       1
      /
     2
    / \
   3   4

// Diameter through node 2, not root 1
// Path: 3 → 2 → 4
// diameter = 1 + 1 = 2 ✓

// At root 1:
// leftHeight = 2, rightHeight = 0
// diameter would be 2 + 0 = 2
// Same! But calculated at node 2 first.
```

### 6. **Very Deep Left Subtree**
```java
       1
      / \
     2   3
    /
   4
  /
 5

// Diameter might be 5 → 4 → 2 → 1 → 3
// Or 5 → 4 → 2 (if no right)
// Check all nodes!
```

### 7. **Balanced Tree (Diameter Through Root)**
```java
       1
      / \
     2   3
    /|\ /|\
  ... ... ...

// Symmetric
// Diameter through root
// Max height on each side ✓
```

### 8. **All Same Values**
```java
    1
   / \
  1   1

// Values don't matter
// Structure only
// Diameter = 2
```

### 9. **Negative Values**
```java
     -1
    /  \
  -5    5

// Values don't affect diameter
// Diameter = 2
```

### 10. **Path Must Be Valid (No Repeats)**
```java
// Can't go: 2 → 1 → 2
// Each node visited once in path
// Our algorithm naturally handles this
// Because we combine left + right heights
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Using Global Variable**
```java
// WRONG - trying to return diameter directly
public int diameterOfBinaryTree(TreeNode root) {
    if (root == null) return 0;
    
    int leftHeight = height(root.left);
    int rightHeight = height(root.right);
    
    return leftHeight + rightHeight;  // ❌ Only checks root!
}
```

**Why wrong**: Only checks diameter through root!

**Dry run failure:**
```
Tree:
    1
   /
  2
 / \
3   4

At root 1:
  leftHeight = 2
  rightHeight = 0
  return 2 + 0 = 2

But actual diameter through node 2:
  leftHeight = 1, rightHeight = 1
  diameter = 1 + 1 = 2

Same in this case, but what about:

Tree:
       1
        \
         2
        / \
       3   4

At root 1:
  leftHeight = 0
  rightHeight = 2
  return 0 + 2 = 2

But diameter through node 2:
  leftHeight = 1, rightHeight = 1
  Should be 1 + 1 = 2

Actually same! But conceptually wrong.

Better example:
       1
      / \
     2   5
    / \
   3   4

At root 1:
  leftHeight = 2, rightHeight = 1
  return 2 + 1 = 3

At node 2:
  leftHeight = 1, rightHeight = 1
  diameter = 1 + 1 = 2

Root gives correct answer here, but algorithm is wrong
because doesn't check all nodes systematically!
```

**Fix**: Use global variable and check all nodes
```java
private int diameter = 0;

public int diameterOfBinaryTree(TreeNode root) {
    height(root);  ✓
    return diameter;  ✓
}

private int height(TreeNode node) {
    if (node == null) return 0;
    
    int leftHeight = height(node.left);
    int rightHeight = height(node.right);
    
    diameter = Math.max(diameter, leftHeight + rightHeight);  ✓
    
    return Math.max(leftHeight, rightHeight) + 1;
}
```

### ❌ **MISTAKE 2: Returning Diameter from Height Function**
```java
// WRONG - returning diameter instead of height
private int height(TreeNode node) {
    if (node == null) return 0;
    
    int leftHeight = height(node.left);
    int rightHeight = height(node.right);
    
    diameter = Math.max(diameter, leftHeight + rightHeight);
    
    return leftHeight + rightHeight;  // ❌ Should return height!
}
```

**Why wrong**: Parent needs height, not diameter!

**Dry run failure:**
```
Tree:
    1
   / \
  2   3

height(2):
  return 0 + 0 = 0 ✓

height(3):
  return 0 + 0 = 0 ✓

height(1):
  leftHeight = height(2) = 0  ❌ (should be 1)
  rightHeight = height(3) = 0 ❌ (should be 1)
  return 0 + 0 = 0

Wrong! Node 1's height should be 2, not 0!
```

**Fix**: Return height to parent
```java
return Math.max(leftHeight, rightHeight) + 1;  ✓
```

### ❌ **MISTAKE 3: Forgetting +1 in Height Return**
```java
// WRONG - not adding 1 for current node
private int height(TreeNode node) {
    if (node == null) return 0;
    
    int leftHeight = height(node.left);
    int rightHeight = height(node.right);
    
    diameter = Math.max(diameter, leftHeight + rightHeight);
    
    return Math.max(leftHeight, rightHeight);  // ❌ Missing +1
}
```

**Why wrong**: Height doesn't count current node's edge!

**Dry run failure:**
```
Tree: 1 → 2

height(2):
  return max(0, 0) = 0 ❌ (should be 1)

height(1):
  leftHeight = 0 ❌ (should be 1)
  diameter = max(0, 0 + 0) = 0 ❌ (should be 1)

Wrong diameter! Should be 1 edge.
```

**Fix**: Add +1 for current edge
```java
return Math.max(leftHeight, rightHeight) + 1;  ✓
```

### ❌ **MISTAKE 4: Not Initializing Diameter Variable**
```java
// WRONG - diameter not initialized
private int diameter;  // ❌ Could be garbage value in some languages

public int diameterOfBinaryTree(TreeNode root) {
    height(root);
    return diameter;
}
```

**Why wrong**: Uninitialized variable!

**Fix**: Initialize to 0
```java
private int diameter = 0;  ✓
```

### ❌ **MISTAKE 5: Using Local Variable Instead of Instance Variable**
```java
// WRONG - local variable not shared across recursive calls
public int diameterOfBinaryTree(TreeNode root) {
    int diameter = 0;  // ❌ Local, not accessible in helper
    height(root);
    return diameter;
}

private int height(TreeNode node) {
    // Can't access diameter here! ❌
    diameter = Math.max(diameter, leftHeight + rightHeight);  // Error!
}
```

**Why wrong**: Can't access local variable in helper!

**Fix**: Use instance variable
```java
private int diameter = 0;  ✓
```

### ❌ **MISTAKE 6: Counting Nodes Instead of Edges**
```java
// WRONG - confusing with max depth (nodes)
// If you count nodes on path instead of edges:

Leaf node should have:
  diameter through it = 0 edges
  
But if counting nodes:
  diameter = 1 node (itself)
  
Wrong interpretation! ✓

Problem says: "length of path = number of edges"
Must count edges! ✓
```

**Why wrong**: Problem specifies edges!

**Fix**: Use edge-based height
```java
// Leaf: height = 1 edge to parent
// Null: height = 0
// This gives edge counts ✓
```

### ❌ **MISTAKE 7: Not Updating Diameter for All Nodes**
```java
// WRONG - only updating diameter at leaves
private int height(TreeNode node) {
    if (node == null) return 0;
    
    int leftHeight = height(node.left);
    int rightHeight = height(node.right);
    
    // Only update if leaf? ❌
    if (node.left == null && node.right == null) {
        diameter = Math.max(diameter, leftHeight + rightHeight);
    }
    
    return Math.max(leftHeight, rightHeight) + 1;
}
```

**Why wrong**: Diameter could be at any node!

**Fix**: Update at every node
```java
diameter = Math.max(diameter, leftHeight + rightHeight);  ✓
// At EVERY node, not just leaves!
```

### ❌ **MISTAKE 8: Using Max Height Instead of Sum**
```java
// WRONG - taking max instead of sum
private int height(TreeNode node) {
    if (node == null) return 0;
    
    int leftHeight = height(node.left);
    int rightHeight = height(node.right);
    
    // Wrong formula! ❌
    diameter = Math.max(diameter, Math.max(leftHeight, rightHeight));
    
    return Math.max(leftHeight, rightHeight) + 1;
}
```

**Why wrong**: Diameter is sum of both heights!

**Dry run failure:**
```
Tree:
    1
   / \
  2   3

At node 1:
  leftHeight = 1, rightHeight = 1
  diameter = max(0, max(1, 1)) = 1 ❌
  
Should be: leftHeight + rightHeight = 1 + 1 = 2 ✓
```

**Fix**: Sum heights
```java
diameter = Math.max(diameter, leftHeight + rightHeight);  ✓
```

### ❌ **MISTAKE 9: Not Handling Null Root**
```java
// WRONG - no null check in main function
public int diameterOfBinaryTree(TreeNode root) {
    height(root);  // What if root is null?
    return diameter;
}
```

**Why wrong**: Could process null root unnecessarily!

**Fix**: Check in helper (already handled by base case)
```java
private int height(TreeNode node) {
    if (node == null) return 0;  ✓
    // ... rest of code
}

// This handles null root fine!
// height(null) returns 0
// diameter stays 0 ✓
```

### ❌ **MISTAKE 10: Forgetting to Reset Diameter Between Test Cases**
```java
// WRONG - reusing same instance variable across test cases
class Solution {
    private int diameter = 0;  // ❌ Not reset!
    
    public int diameterOfBinaryTree(TreeNode root) {
        height(root);
        return diameter;  // Carries over from previous calls!
    }
}
```

**Why wrong**: Diameter accumulates across calls!

**Fix**: Reset in main method
```java
public int diameterOfBinaryTree(TreeNode root) {
    diameter = 0;  ✓ Reset before each call
    height(root);
    return diameter;
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

```
Where n = number of nodes

Visit each node exactly once:
  - Calculate leftHeight: visit left subtree once
  - Calculate rightHeight: visit right subtree once
  - Update diameter: O(1)
  - Return height: O(1)

Recurrence:
  T(n) = T(left) + T(right) + O(1)
       = O(n)

Optimal! ✓
```

**Detailed Analysis**:
```
For each node:
  - Visited once in DFS
  - Constant work at each node
  
Total: n × O(1) = O(n) ✓

Compared to brute force O(n²):
  Much better! ✓
```

### Space Complexity: **O(h)** where h = height

```
Recursion call stack:
  - Maximum depth = tree height
  - Best case (balanced): h = log n → O(log n)
  - Worst case (skewed): h = n → O(n)
  - Average: O(log n)

No additional data structures:
  - Only one integer variable (diameter)
  - Rest is recursion stack

Space = O(h) ✓
```

**Space Breakdown**:
```
Stack frames:
  - Each frame: O(1) local variables
  - Maximum h frames active
  - Total: O(h)

Instance variable:
  - diameter: O(1)

Total: O(h) + O(1) = O(h) ✓
```

### Optimal Complexity

```
Time: O(n)
  - Must visit all nodes: Ω(n)
  - Single traversal: O(n)
  - Optimal! ✓

Space: O(h)
  - Recursion required for tree
  - O(h) is optimal for DFS
  - Can't do better! ✓

This is the best possible! ✓
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `root = [1,2,3,4,5,null,null,6,7]`

```
Tree:
       1
      / \
     2   3
    / \
   4   5
  / \
 6   7
```

**Recursive Execution (Bottom-Up):**

```
Initial: diameter = 0

Process leaf nodes first:

height(6):
  leftHeight = 0, rightHeight = 0
  diameter = max(0, 0 + 0) = 0
  return 1 (height: 1 edge to parent)

height(7):
  leftHeight = 0, rightHeight = 0
  diameter = max(0, 0 + 0) = 0
  return 1

height(4):
  leftHeight = height(6) = 1
  rightHeight = height(7) = 1
  diameter = max(0, 1 + 1) = 2  ← Path: 6 → 4 → 7
  return max(1, 1) + 1 = 2

height(5):
  leftHeight = 0, rightHeight = 0
  diameter = max(2, 0 + 0) = 2
  return 1

height(2):
  leftHeight = height(4) = 2
  rightHeight = height(5) = 1
  diameter = max(2, 2 + 1) = 3  ← Path: 6 → 4 → 2 → 5
  return max(2, 1) + 1 = 3

height(3):
  leftHeight = 0, rightHeight = 0
  diameter = max(3, 0 + 0) = 3
  return 1

height(1):
  leftHeight = height(2) = 3
  rightHeight = height(3) = 1
  diameter = max(3, 3 + 1) = 4  ← Path: 6 → 4 → 2 → 1 → 3
  return max(3, 1) + 1 = 4

Final diameter = 4 ✓
```

**Path Visualization:**
```
Diameter = 4 edges

Longest path: 6 → 4 → 2 → 1 → 3

Visual with edges marked:
       1
      ╱ ╲
     2   3 ←─┐
    ╱ ╲       │
   4   5      │ These 4 edges
  ╱ ╲         │ form the
 6   7        │ longest path
 └────────────┘

Edges counted:
  6 to 4: 1 edge
  4 to 2: 1 edge
  2 to 1: 1 edge
  1 to 3: 1 edge
  Total: 4 edges ✓
```

**Diameter Calculation at Key Nodes:**
```
Node 4 (has children 6 and 7):
  leftHeight = 1 (to 6)
  rightHeight = 1 (to 7)
  diameter through 4 = 1 + 1 = 2
  Path: 6 → 4 → 7 (2 edges)

Node 2 (has children 4 and 5):
  leftHeight = 2 (to 6 through 4)
  rightHeight = 1 (to 5)
  diameter through 2 = 2 + 1 = 3
  Path: 6 → 4 → 2 → 5 (3 edges)

Node 1 (root, has children 2 and 3):
  leftHeight = 3 (to 6 through 2 and 4)
  rightHeight = 1 (to 3)
  diameter through 1 = 3 + 1 = 4
  Path: 6 → 4 → 2 → 1 → 3 (4 edges)

Maximum = 4 ✓
```

---

### Visual: Height vs Diameter

```
Tree:
       1
      / \
     2   3
    / \
   4   5

Heights (edges to furthest leaf):
  Node 4: height = 1
  Node 5: height = 1
  Node 2: height = 2 (max(1,1)+1)
  Node 3: height = 1
  Node 1: height = 3 (max(2,1)+1)

Diameters (left + right heights):
  Node 4: diameter = 0 + 0 = 0
  Node 5: diameter = 0 + 0 = 0
  Node 2: diameter = 1 + 1 = 2 ← Path: 4→2→5
  Node 3: diameter = 0 + 0 = 0
  Node 1: diameter = 2 + 1 = 3 ← Path: 4→2→1→3

Maximum diameter: 3 ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Difficulty | Optimal | Recommended |
|----------|------|-------|------------|---------|-------------|
| **Recursive DFS + Global** | **O(n)** | **O(h)** | **Medium** | **Yes** | **✓ Only O(n) solution** |
| Brute Force | O(n²) | O(h) | Easy | No | ❌ Too slow |

**Why Recursive DFS is Only Option:**
- **Height-based formula**: Need heights to calculate diameter
- **Single pass**: Calculate heights and update diameter together
- **No better alternative**: Must visit all nodes

---

## Key Takeaways

1. **Diameter = longest path (edges)**: Between any two nodes
2. **Formula**: Diameter at node = leftHeight + rightHeight
3. **Global variable**: Track maximum across all nodes
4. **Return height**: For parent's calculation
5. **Update diameter**: As side effect during recursion
6. **Post-order DFS**: Children before parent
7. **Edge count**: Use height in edges, not nodes
8. **Check all nodes**: Diameter might not be through root
9. **O(n) time**: Single traversal visits each node once
10. **Two pieces of info**: Height (return) + diameter (global)

---

## Interview Tips

**What to say in an interview:**

> "I need to find the diameter of the binary tree, which is the longest path between any two nodes, measured in edges. The key insight is that for any node, the longest path passing through it equals the sum of its left subtree height and right subtree height.
>
> I'll use a recursive DFS approach with an instance variable to track the maximum diameter. The helper function will return the height of each subtree (in edges), and as a side effect, it will update the global diameter variable at each node.
>
> At each node, I calculate the left height and right height recursively. Then I update the diameter if the sum of these heights is greater than the current maximum. Finally, I return the maximum of the two heights plus one (representing the edge to the parent).
>
> This is a post-order traversal because I need information from the children before computing the parent's values. The algorithm visits each node exactly once, giving O(n) time complexity. The space complexity is O(h) where h is the tree height, due to the recursion call stack.
>
> An important detail: the diameter might not pass through the root, so I must check all nodes. That's why I use a global variable to track the maximum across the entire tree, not just return a value from the root."

**Key points to mention:**
1. **Diameter = longest path (edges)**: Between any two nodes
2. **Formula**: leftHeight + rightHeight at each node
3. **Global variable**: Track max across all nodes
4. **Return height**: For parent's calculation
5. **Post-order DFS**: Children before parent
6. **O(n) time**: Visit each node once
7. **O(h) space**: Recursion stack
8. **Not always through root**: Must check all nodes
9. **Edge count**: Height measures edges not nodes
10. **Two pieces of info**: Height returned, diameter tracked

**Common Follow-ups:**
- "Why use global variable?" → Need two pieces of info: height (return) and diameter (track)
- "Could diameter be through root?" → Yes, but might be at any node, so check all
- "What's the time complexity of brute force?" → O(n²) computing height at each node
- "Why can't you return diameter directly?" → Parent needs height, not diameter
- "How do you count edges vs nodes?" → Null = 0, leaf = 1 (edge to parent)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| Maximum Depth of Binary Tree | Easy | DFS Post-Order | Return depth directly, no diameter |
| **Diameter of Binary Tree** | Easy | **DFS + Global State** | **This problem** |
| Binary Tree Maximum Path Sum | Hard | DFS + Global State | Sum values instead of counting edges |
| Longest Univalue Path | Medium | DFS + Global State | Same value nodes only |
| Balanced Binary Tree | Easy | DFS Height Check | Check height difference |
| Lowest Common Ancestor | Medium | DFS | Find node instead of path length |

**Pattern Progression**:
1. **Maximum Depth** — Learn height calculation
2. **Diameter** — Use height for path length, track global max
3. **Maximum Path Sum** — Similar pattern, sum values instead of count
4. **Advanced Path Problems** — Build on diameter concept

---

## Final Pattern Label

✅ **Tree DFS Post-Order with Global State Tracking**

**Remember:** This problem finds **diameter** (longest path between any two nodes measured in **edges not nodes**) by checking paths through **every node** not just root. **Core algorithm**: recursive DFS helper returns **height of subtree** (edges to furthest leaf), uses **instance/global variable** to track **maximum diameter** found. **Key formula at each node**: `diameter = leftHeight + rightHeight` (sum of heights from both sides = path through current node). **Two pieces of information**: return value is **height for parent's calculation** (`return max(leftHeight, rightHeight) + 1`), side effect is **updating global diameter** (`diameter = max(diameter, leftHeight + rightHeight)`). **Why global variable needed**: recursion must return height but we need to track diameter separately — can't return both, so return height and update diameter as side effect. **Post-order pattern**: must process **children before parent** because parent's diameter calculation depends on children's heights. **Height counting**: null returns 0, leaf has height 1 (one edge to parent), parent has `max(children) + 1`. **Time complexity**: O(n) single pass visiting each node once with O(1) work per node. **Space complexity**: O(h) recursion stack where h = height (best O(log n) balanced, worst O(n) skewed). **Common mistakes**: returning diameter instead of height from helper (parent needs height!), forgetting +1 when returning height (doesn't count current edge), only checking diameter at root (could be at any node!), using max instead of sum for diameter formula (should be leftHeight + rightHeight not max of them), counting nodes instead of edges (problem specifies edges!), not resetting diameter between test cases (accumulates). **Why must check all nodes**: diameter path might not pass through root — could be deep in subtree between two leaves, so every node is potential "bridge" for longest path. Pattern: **bottom-up DFS** returning **height** while tracking **global maximum** of **combined heights** for **path length problems**! ✓
