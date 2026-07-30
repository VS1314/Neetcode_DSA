# Binary Tree Inorder Traversal

## Problem Description

**Difficulty**: Easy

You are given the `root` of a binary tree, return the **inorder traversal** of its nodes' values.

**Inorder Traversal Order**: **Left → Root → Right**

**Key Concepts:**
- **Inorder Traversal**: Process left subtree, then root, then right subtree
- **Recursive Solution**: Natural and trivial implementation
- **Iterative Solution**: Uses explicit stack (follow-up requirement)
- **Morris Traversal**: O(1) space solution using threading (advanced)
- **Order Property**: For BST, inorder gives sorted values

**Visual Example:**
```
Tree:
       1
      / \
     2   3
    / \ / \
   4  5 6  7

Inorder Traversal: Left → Root → Right
Process order:
  1. Go left to 2
  2. Go left to 4
  3. Visit 4 (leftmost)
  4. Back to 2, visit 2
  5. Go right to 5, visit 5
  6. Back to 1, visit 1
  7. Go right to 3
  8. Go left to 6, visit 6
  9. Back to 3, visit 3
  10. Go right to 7, visit 7

Result: [4, 2, 5, 1, 6, 3, 7]
```

**Follow-up**: Recursive solution is trivial, could you do it iteratively?

---

## Examples

### Example 1 (Complete Binary Tree):
```
Input: root = [1,2,3,4,5,6,7]

Tree Structure:
       1
      / \
     2   3
    / \ / \
   4  5 6  7

Output: [4,2,5,1,6,3,7]

Explanation:
Inorder (Left → Root → Right):
  Visit 4 (left of 2)
  Visit 2 (root of left subtree)
  Visit 5 (right of 2)
  Visit 1 (main root)
  Visit 6 (left of 3)
  Visit 3 (root of right subtree)
  Visit 7 (right of 3)
```

### Example 2 (Incomplete Tree):
```
Input: root = [1,2,3,null,4,5,null]

Tree Structure:
       1
      / \
     2   3
      \ /
      4 5

Output: [2,4,1,5,3]

Explanation:
Inorder traversal:
  2 (left child of 1, no left child of its own)
  4 (right child of 2)
  1 (root)
  5 (left child of 3)
  3 (root of right subtree)
```

### Example 3 (Empty Tree):
```
Input: root = []

Output: []

Explanation:
No nodes to traverse
Return empty list
```

### Example 4 (Single Node):
```
Input: root = [1]

Tree:
  1

Output: [1]

Explanation:
Only root node
Inorder traversal returns [1]
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

Output: [4,3,2,1]

Explanation:
Only left children
Traverse to leftmost (4), then back up
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

Output: [1,2,3,4]

Explanation:
Only right children
Visit root first, then traverse right
```

### Example 7 (Binary Search Tree):
```
Input: root = [5,3,7,1,4,6,8]

Tree (BST):
       5
      / \
     3   7
    / \ / \
   1  4 6  8

Output: [1,3,4,5,6,7,8]

Explanation:
Inorder traversal of BST gives sorted values
```

### Example 8 (Two Nodes - Left Child):
```
Input: root = [1,2]

Tree:
  1
 /
2

Output: [2,1]

Explanation:
Visit left child first, then root
```

### Example 9 (Two Nodes - Right Child):
```
Input: root = [1,null,2]

Tree:
1
 \
  2

Output: [1,2]

Explanation:
Visit root first, then right child
```

### Example 10 (Larger Tree):
```
Input: root = [1,2,3,4,5,6,7,8,9,10]

Tree:
           1
         /   \
        2     3
       / \   / \
      4   5 6   7
     / \ /
    8  9 10

Output: [8,4,9,2,10,5,1,6,3,7]

Explanation:
Inorder traversal processes all nodes
Left subtree → Root → Right subtree recursively
```

---

## Constraints
- `0 <= number of nodes in the tree <= 100`
- `-100 <= Node.val <= 100`

**Recommended Complexity**: 
- Time: O(n) where n = number of nodes
- Space: O(h) where h = height (O(n) worst case for skewed tree)
- Advanced (Morris): O(n) time, O(1) space

---

## Pattern Recognition

**Primary Pattern**: **Tree Traversal - Depth First Search (DFS)**

**Why This Pattern?**
- **Inorder** is one of three DFS traversals (preorder, inorder, postorder)
- Processes **left subtree** before **root** before **right subtree**
- Natural **recursive** structure matches tree structure
- Can be done **iteratively** with explicit stack
- **BST property**: Inorder gives sorted values

**Key Insight**: Three DFS Traversal Orders
```
For any node:
  Preorder:  Root → Left → Right
  Inorder:   Left → Root → Right  ← This problem
  Postorder: Left → Right → Root

Tree:
    1
   / \
  2   3

Preorder:  [1, 2, 3]
Inorder:   [2, 1, 3]
Postorder: [2, 3, 1]

Different orders serve different purposes!
```

**Visual: Inorder Traversal Flow**
```
Tree:
       1
      / \
     2   3
    / \
   4   5

Recursive calls (Inorder: Left → Root → Right):

inorder(1):
  inorder(2):           // Left
    inorder(4):         // Left
      inorder(null)     // Left of 4
      add 4 ✓           // Root (4)
      inorder(null)     // Right of 4
    add 2 ✓             // Root (2)
    inorder(5):         // Right
      inorder(null)     // Left of 5
      add 5 ✓           // Root (5)
      inorder(null)     // Right of 5
  add 1 ✓               // Root (1)
  inorder(3):           // Right
    inorder(null)       // Left of 3
    add 3 ✓             // Root (3)
    inorder(null)       // Right of 3

Result: [4, 2, 5, 1, 3]
```

**Why Recursive is Natural**:
```
Tree is recursive structure:
  - Node has left child (subtree)
  - Node has right child (subtree)
  - Each subtree is also a tree

Recursive traversal mirrors structure:
  traverse(left)   // Process left subtree
  visit(root)      // Process current node
  traverse(right)  // Process right subtree

Natural fit! ✓
```

**Why Iterative Needs Stack**:
```
Recursive uses implicit call stack

To convert to iterative:
  Need explicit stack to track nodes
  Simulate recursive call behavior
  
Stack-based iterative traversal! ✓
```

**Iterative Algorithm Strategy**:
```
1. Start with root
2. Go left as far as possible, pushing nodes to stack
3. When can't go left, pop from stack:
   - Process node
   - Go to right child
4. Repeat until stack empty and no more nodes

Simulates recursive traversal! ✓
```

**Visual: Iterative with Stack**
```
Tree:
    1
   / \
  2   3

Step-by-step:

Initial: current = 1, stack = [], result = []

1. Go left: push 1, current = 2
   stack = [1], current = 2

2. Go left: push 2, current = null
   stack = [1, 2], current = null

3. Can't go left, pop 2
   result = [2], current = 2.right = null
   stack = [1]

4. Can't go left, pop 1
   result = [2, 1], current = 1.right = 3
   stack = []

5. Go left: push 3, current = null
   stack = [3], current = null

6. Can't go left, pop 3
   result = [2, 1, 3], current = 3.right = null
   stack = []

7. Stack empty and current null, done!

Result: [2, 1, 3] ✓
```

**Morris Traversal (Advanced - O(1) Space)**:
```
Idea: Use tree structure itself instead of stack

Technique: Threading
  - Create temporary links (threads)
  - Use right pointers of predecessors
  - No extra space needed!

Complex but achieves O(1) space ✓
```

**Core Operations**:

**Recursive Approach**:
```java
List<Integer> result = new ArrayList<>();

inorder(TreeNode root):
    if root == null:
        return
    
    inorder(root.left)      // Left
    result.add(root.val)    // Root
    inorder(root.right)     // Right
```

**Iterative Approach**:
```java
List<Integer> result = new ArrayList<>();
Stack<TreeNode> stack = new Stack<>();
TreeNode current = root;

while current != null or !stack.isEmpty():
    // Go left as far as possible
    while current != null:
        stack.push(current)
        current = current.left
    
    // Process node
    current = stack.pop()
    result.add(current.val)
    
    // Go right
    current = current.right
```

**Related Patterns**:
1. **Preorder Traversal** — Root → Left → Right
2. **Inorder Traversal** — This problem (Left → Root → Right)
3. **Postorder Traversal** — Left → Right → Root
4. **Level Order Traversal** — BFS, level by level

---

## Algorithm & Approach

### Core Insight

**Why Inorder Traversal Works:**
```
Key observations:
  1. Tree is recursive: each node has left/right subtrees
  2. Inorder: process left, then root, then right
  3. Recursive solution mirrors tree structure
  4. Iterative solution uses stack to simulate recursion
  5. Visit each node exactly once: O(n)
```

**The Optimal Strategy**:
```
Recursive (Simple):
  - Base case: null node returns
  - Recursive case: left → visit → right
  - Natural and clean
  - O(h) space for call stack

Iterative (Follow-up):
  - Explicit stack instead of call stack
  - Go left, push nodes
  - Pop, process, go right
  - Same O(h) space for stack

Morris (Advanced):
  - Threading technique
  - Use tree structure itself
  - O(1) space!
  - More complex
```

### Step-by-Step Algorithm

---

#### **Approach 1: Recursive - SIMPLEST**

**Core Idea**:
- Recursively traverse left subtree
- Visit current node
- Recursively traverse right subtree
- Natural and clean implementation

**Algorithm**
```java
inorderTraversal(TreeNode root):
    result = new ArrayList<>()
    inorderHelper(root, result)
    return result

inorderHelper(TreeNode node, List<Integer> result):
    if node == null:
        return
    
    inorderHelper(node.left, result)   // Left
    result.add(node.val)               // Root
    inorderHelper(node.right, result)  // Right
```

**Complete Code Implementation (Recursive)**
```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }
    
    private void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // Left
        inorderHelper(node.left, result);
        
        // Root
        result.add(node.val);
        
        // Right
        inorderHelper(node.right, result);
    }
}
```

**Example Walkthrough (Recursive)**

Input: root = [1,2,3]
```
Tree:
    1
   / \
  2   3
```

**Recursive Calls:**
```
inorder(1):
  inorder(2):           // Left of 1
    inorder(null)       // Left of 2
    add 2               // Root (2)
    inorder(null)       // Right of 2
  add 1                 // Root (1)
  inorder(3):           // Right of 1
    inorder(null)       // Left of 3
    add 3               // Root (3)
    inorder(null)       // Right of 3

Result: [2, 1, 3] ✓
```

---

#### **Approach 2: Iterative with Stack - FOLLOW-UP**

**Core Idea**:
- Use explicit stack to simulate recursion
- Go left as far as possible, pushing nodes
- Pop, process, then go right
- Same traversal order as recursive

**Algorithm**
```java
inorderTraversal(TreeNode root):
    result = new ArrayList<>()
    stack = new Stack<>()
    current = root
    
    while current != null or !stack.isEmpty():
        // Go left as far as possible
        while current != null:
            stack.push(current)
            current = current.left
        
        // Process node
        current = stack.pop()
        result.add(current.val)
        
        // Go right
        current = current.right
    
    return result
```

**Complete Code Implementation (Iterative)**
```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            // Go left as far as possible, pushing nodes
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // Process node
            current = stack.pop();
            result.add(current.val);
            
            // Go right
            current = current.right;
        }
        
        return result;
    }
}
```

**Example Walkthrough (Iterative)**

Input: root = [1,2,3]
```
Tree:
    1
   / \
  2   3
```

**Step-by-Step:**
```
Initial:
  current = 1, stack = [], result = []

Step 1: Go left, push 1
  stack.push(1)
  current = 2
  stack = [1], current = 2

Step 2: Go left, push 2
  stack.push(2)
  current = null
  stack = [1, 2], current = null

Step 3: Can't go left (current = null)
  current = stack.pop() = 2
  result.add(2)
  current = 2.right = null
  stack = [1], result = [2]

Step 4: Can't go left (current = null)
  current = stack.pop() = 1
  result.add(1)
  current = 1.right = 3
  stack = [], result = [2, 1]

Step 5: Go left, push 3
  stack.push(3)
  current = null
  stack = [3], current = null

Step 6: Can't go left
  current = stack.pop() = 3
  result.add(3)
  current = 3.right = null
  stack = [], result = [2, 1, 3]

Step 7: current = null, stack empty → done

Result: [2, 1, 3] ✓
```

---

#### **Approach 3: Morris Traversal - O(1) SPACE (ADVANCED)**

**Core Idea**:
- Use threading to avoid stack
- Create temporary links using right pointers
- No extra space needed
- More complex but optimal space

**Algorithm**
```java
inorderTraversal(TreeNode root):
    result = new ArrayList<>()
    current = root
    
    while current != null:
        if current.left == null:
            // No left child, visit current
            result.add(current.val)
            current = current.right
        else:
            // Find predecessor (rightmost node in left subtree)
            predecessor = current.left
            while predecessor.right != null and predecessor.right != current:
                predecessor = predecessor.right
            
            if predecessor.right == null:
                // Create thread
                predecessor.right = current
                current = current.left
            else:
                // Thread already exists, remove it
                predecessor.right = null
                result.add(current.val)
                current = current.right
    
    return result
```

**Complete Code Implementation (Morris)**
```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        
        while (current != null) {
            if (current.left == null) {
                // No left child, visit current node
                result.add(current.val);
                current = current.right;
            } else {
                // Find inorder predecessor
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    // Create thread
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Thread exists, remove it and visit
                    predecessor.right = null;
                    result.add(current.val);
                    current = current.right;
                }
            }
        }
        
        return result;
    }
}
```

**Complexity Analysis**
- **Recursive**: O(n) time, O(h) space (call stack)
- **Iterative**: O(n) time, O(h) space (explicit stack)
- **Morris**: O(n) time, O(1) space (threading)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Difficulty | Recommended |
|----------|------|-------|------------|-------------|
| **Recursive** | **O(n)** | **O(h)** | **Easy** | **Yes (simple)** |
| **Iterative** | **O(n)** | **O(h)** | **Medium** | **Yes (follow-up)** |
| Morris | O(n) | O(1) | Hard | Advanced |

**Winner**: **Recursive** for simplicity, **Iterative** for follow-up, **Morris** for space optimization

### Why Recursive is Natural

```
Tree is recursive structure:
  Node {
    left: subtree
    right: subtree
  }

Recursive traversal mirrors structure:
  process(left subtree)
  process(node)
  process(right subtree)

Perfect match! ✓
Natural and clean code ✓
```

### Why Iterative Needs Stack

```
Recursive uses implicit call stack

To make iterative:
  Need to track "where we are"
  Need to remember nodes to process
  Stack stores pending nodes
  
Explicit stack replaces call stack ✓
```

### Why Morris Achieves O(1) Space

```
Key insight: Use tree structure itself

Threading:
  - Temporarily modify tree
  - Create links using right pointers
  - Use links to navigate
  - Restore tree structure
  
No extra data structure needed! ✓
```

### Why Inorder Matters for BST

```
Binary Search Tree property:
  left < root < right

Inorder traversal of BST:
  Visit nodes in ascending order
  [left values] → root → [right values]
  
Result: Sorted array! ✓

Use case: Validate BST, find kth smallest, etc.
```

### Why This is Optimal

```
Time complexity:
  Must visit all n nodes: Ω(n)
  Each node visited once: O(n)
  Optimal! ✓

Space complexity:
  Recursive/Iterative: O(h) for stack
    Balanced tree: h = log n
    Skewed tree: h = n
    Acceptable for most cases ✓
  
  Morris: O(1) no extra space
    Optimal space! ✓
    But more complex

Choose based on requirements!
```

---

## Critical Edge Cases & Gotchas

### 1. **Empty Tree**
```java
root = null
// Should return empty list []
```

### 2. **Single Node**
```java
root = [1]
// Return [1]
```

### 3. **Left Skewed Tree**
```java
    1
   /
  2
 /
3

// Result: [3, 2, 1]
// Stack depth = height = n (worst case)
```

### 4. **Right Skewed Tree**
```java
1
 \
  2
   \
    3

// Result: [1, 2, 3]
// Looks like preorder for right-skewed
```

### 5. **Complete Binary Tree**
```java
       1
      / \
     2   3
    / \ / \
   4  5 6  7

// Result: [4, 2, 5, 1, 6, 3, 7]
// Balanced, stack depth = log n
```

### 6. **Binary Search Tree**
```java
     4
    / \
   2   6
  / \ / \
 1  3 5  7

// Result: [1, 2, 3, 4, 5, 6, 7] (sorted!)
// Inorder of BST gives ascending order
```

### 7. **Tree with Null Children**
```java
    1
   /
  2
   \
    3

// Result: [2, 3, 1]
// Handle null pointers correctly
```

### 8. **Two Nodes**
```java
  1
 /
2

// Result: [2, 1]

1
 \
  2

// Result: [1, 2]
```

### 9. **Negative Values**
```java
     0
    / \
  -5   5

// Result: [-5, 0, 5]
// Values can be negative
```

### 10. **All Same Values**
```java
    1
   / \
  1   1

// Result: [1, 1, 1]
// Duplicate values allowed
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Wrong Traversal Order**
```java
// WRONG - this is PREORDER, not inorder
private void inorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    result.add(node.val);          // ❌ Root first (preorder)
    inorderHelper(node.left, result);
    inorderHelper(node.right, result);
}
```

**Why wrong**: Processes root before left subtree!

**Issue:**
```
Preorder: Root → Left → Right
Inorder:  Left → Root → Right

Wrong order! ❌
```

**Fix**: Left → Root → Right
```java
private void inorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    inorderHelper(node.left, result);   // Left ✓
    result.add(node.val);               // Root ✓
    inorderHelper(node.right, result);  // Right ✓
}
```

### ❌ **MISTAKE 2: Iterative - Not Going Left First**
```java
// WRONG - not pushing left nodes first
while (!stack.isEmpty()) {
    TreeNode node = stack.pop();
    result.add(node.val);  // ❌ Wrong order
    
    if (node.right != null) stack.push(node.right);
    if (node.left != null) stack.push(node.left);
}
```

**Why wrong**: This is preorder, not inorder!

**Issue:**
```
Need to go left completely first
Then process, then go right

This processes immediately ❌
```

**Fix**: Go left, then pop and process
```java
TreeNode current = root;
while (current != null || !stack.isEmpty()) {
    while (current != null) {
        stack.push(current);
        current = current.left;  // Go left ✓
    }
    
    current = stack.pop();
    result.add(current.val);  // Process ✓
    current = current.right;  // Go right ✓
}
```

### ❌ **MISTAKE 3: Not Handling Null Root**
```java
// WRONG - NullPointerException if root is null
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);  // ❌ What if root is null?
    // ...
}
```

**Why wrong**: Null root causes issues!

**Fix**: Check null or handle properly
```java
// Iterative handles null automatically:
TreeNode current = root;  // Can be null ✓
while (current != null || !stack.isEmpty()) {
    // Works even if root is null
}

// Recursive handles with base case:
if (node == null) return;  ✓
```

### ❌ **MISTAKE 4: Forgetting to Return Result**
```java
// WRONG - void return type
public void inorderTraversal(TreeNode root) {  // ❌
    List<Integer> result = new ArrayList<>();
    // ... traversal ...
    // Missing return!
}
```

**Why wrong**: Need to return the result list!

**Fix**: Return List<Integer>
```java
public List<Integer> inorderTraversal(TreeNode root) {  ✓
    List<Integer> result = new ArrayList<>();
    // ... traversal ...
    return result;  ✓
}
```

### ❌ **MISTAKE 5: Iterative - Wrong Loop Condition**
```java
// WRONG - only checks stack, misses current
while (!stack.isEmpty()) {  // ❌
    // What if current != null but stack empty?
}
```

**Why wrong**: Misses nodes when stack empty!

**Dry run failure:**
```
Initial: current = 1, stack = []

Loop condition: !stack.isEmpty() → false ❌
Loop exits immediately!

Never processes any nodes!
```

**Fix**: Check both current and stack
```java
while (current != null || !stack.isEmpty()) {  ✓
    // Process when either has nodes
}
```

### ❌ **MISTAKE 6: Modifying Tree Structure (Unintentionally)**
```java
// WRONG - modifying tree in Morris without restoring
predecessor.right = current;  // Create thread
// ... but never restore predecessor.right = null ❌
```

**Why wrong**: Tree structure permanently changed!

**Issue:**
```
Morris traversal creates temporary links
Must remove them after use
Otherwise tree is corrupted ❌
```

**Fix**: Restore tree structure
```java
if (predecessor.right == null) {
    predecessor.right = current;  // Create thread
    current = current.left;
} else {
    predecessor.right = null;  // Restore ✓
    result.add(current.val);
    current = current.right;
}
```

### ❌ **MISTAKE 7: Iterative - Not Updating Current After Pop**
```java
// WRONG - not moving to right child
while (current != null || !stack.isEmpty()) {
    while (current != null) {
        stack.push(current);
        current = current.left;
    }
    
    current = stack.pop();
    result.add(current.val);
    // Missing: current = current.right; ❌
}
```

**Why wrong**: Infinite loop or wrong traversal!

**Dry run failure:**
```
After processing node:
  current still points to processed node
  Inner while loop pushes it again ❌
  Infinite loop or duplicate processing!
```

**Fix**: Move to right child
```java
current = stack.pop();
result.add(current.val);
current = current.right;  ✓
```

### ❌ **MISTAKE 8: Using BFS (Level Order) Instead of DFS**
```java
// WRONG - this is level order, not inorder
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);

while (!queue.isEmpty()) {
    TreeNode node = queue.poll();
    result.add(node.val);  // ❌ Level order
    
    if (node.left != null) queue.offer(node.left);
    if (node.right != null) queue.offer(node.right);
}
```

**Why wrong**: Different traversal type!

**Issue:**
```
BFS (Level Order): Process by levels
  Result: [1, 2, 3, 4, 5, 6, 7]

DFS Inorder: Left → Root → Right
  Result: [4, 2, 5, 1, 6, 3, 7]

Completely different! ❌
```

**Fix**: Use DFS with stack or recursion
```java
// Stack for DFS ✓
Stack<TreeNode> stack = new Stack<>();
```

### ❌ **MISTAKE 9: Not Creating New ArrayList for Result**
```java
// WRONG - result not initialized
private List<Integer> result;  // ❌ null

public List<Integer> inorderTraversal(TreeNode root) {
    inorderHelper(root, result);  // NullPointerException!
    return result;
}
```

**Why wrong**: Result list is null!

**Fix**: Initialize result list
```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();  ✓
    inorderHelper(root, result);
    return result;
}
```

### ❌ **MISTAKE 10: Morris - Infinite Loop (Wrong Predecessor Check)**
```java
// WRONG - doesn't check predecessor.right != current
TreeNode predecessor = current.left;
while (predecessor.right != null) {  // ❌
    predecessor = predecessor.right;
}
```

**Why wrong**: Infinite loop with thread!

**Issue:**
```
After creating thread:
  predecessor.right = current (not null)

Loop condition: predecessor.right != null
  Always true! ❌
  Infinite loop!
```

**Fix**: Check for thread
```java
while (predecessor.right != null && predecessor.right != current) {  ✓
    predecessor = predecessor.right;
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

```
Where n = number of nodes

All approaches:
  - Visit each node exactly once
  - Process each node in constant time
  - Total: O(n)

Recursive:
  T(n) = T(left) + T(right) + O(1)
       = O(n)

Iterative:
  Each node pushed and popped once: O(n)

Morris:
  Each node visited at most 3 times: O(3n) = O(n)
```

**Detailed Analysis**:
```
For each node:
  Recursive: visit once (left, process, right)
  Iterative: push once, pop once
  Morris: visit at most 3 times (find predecessor, thread, process)

All O(n) ✓
```

### Space Complexity

**Recursive: O(h)** where h = height
```
Space for call stack:
  - Best case (balanced): h = log n → O(log n)
  - Worst case (skewed): h = n → O(n)
  - Average: O(log n)

Call stack depth = height of tree
```

**Iterative: O(h)** where h = height
```
Space for explicit stack:
  - Same as recursive call stack
  - Stores nodes along path from root to current
  - Maximum size = height
  
  Best: O(log n)
  Worst: O(n)
```

**Morris: O(1)**
```
No extra data structure:
  - Only constant pointers
  - Uses tree structure itself
  
True O(1) space! ✓
```

### Optimal Complexity

```
Time: O(n)
  - Must visit all nodes: Ω(n)
  - All approaches: O(n)
  - Optimal! ✓

Space:
  - Recursive/Iterative: O(h)
    Reasonable for most trees
  
  - Morris: O(1)
    Optimal space! ✓
    But more complex

Choose based on needs!
```

---

## Visualization

### Complete Example Walkthrough (Recursive)

**Input:** `root = [1,2,3,4,5]`

```
Tree:
       1
      / \
     2   3
    / \
   4   5
```

**Recursive Call Stack:**

```
Call inorder(1):
  Call inorder(2):         // Left of 1
    Call inorder(4):       // Left of 2
      Call inorder(null)   // Left of 4 → return
      Add 4 to result      // Root of 4
      Call inorder(null)   // Right of 4 → return
    Add 2 to result        // Root of 2
    Call inorder(5):       // Right of 2
      Call inorder(null)   // Left of 5 → return
      Add 5 to result      // Root of 5
      Call inorder(null)   // Right of 5 → return
  Add 1 to result          // Root of 1
  Call inorder(3):         // Right of 1
    Call inorder(null)     // Left of 3 → return
    Add 3 to result        // Root of 3
    Call inorder(null)     // Right of 3 → return

Result: [4, 2, 5, 1, 3] ✓
```

---

### Complete Example Walkthrough (Iterative)

**Input:** `root = [1,2,3,4,5]`

**Step-by-Step:**

```
Initial:
  current = 1, stack = [], result = []

Iteration 1: Go left
  Push 1: stack = [1], current = 2
  Push 2: stack = [1, 2], current = 4
  Push 4: stack = [1, 2, 4], current = null

Iteration 2: Process 4
  Pop 4: result = [4], current = null
  stack = [1, 2]

Iteration 3: Process 2
  Pop 2: result = [4, 2], current = 5
  stack = [1]

Iteration 4: Go left from 5
  Push 5: stack = [1, 5], current = null

Iteration 5: Process 5
  Pop 5: result = [4, 2, 5], current = null
  stack = [1]

Iteration 6: Process 1
  Pop 1: result = [4, 2, 5, 1], current = 3
  stack = []

Iteration 7: Go left from 3
  Push 3: stack = [3], current = null

Iteration 8: Process 3
  Pop 3: result = [4, 2, 5, 1, 3], current = null
  stack = []

Done: current = null, stack empty

Result: [4, 2, 5, 1, 3] ✓
```

---

### Visual: Stack State at Each Step

```
Tree:       1
           / \
          2   3
         / \
        4   5

Stack visualization:

Step 1: Go left to 4
  [1]
  [2]
  [4] ← top

Step 2: Process 4
  [1]
  [2] ← top
  Result: [4]

Step 3: Process 2, go to 5
  [1]
  [5] ← top
  Result: [4, 2]

Step 4: Process 5
  [1] ← top
  Result: [4, 2, 5]

Step 5: Process 1, go to 3
  [3] ← top
  Result: [4, 2, 5, 1]

Step 6: Process 3
  [] (empty)
  Result: [4, 2, 5, 1, 3] ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Difficulty | Clean Code | Recommended |
|----------|------|-------|------------|------------|-------------|
| **Recursive** | **O(n)** | **O(h)** | **Easy** | **Yes** | **Yes (default)** |
| **Iterative** | **O(n)** | **O(h)** | **Medium** | **Medium** | **Yes (follow-up)** |
| Morris | O(n) | O(1) | Hard | No | Advanced only |

**When to Use Each:**
- **Interview default**: Recursive (clean, simple)
- **Follow-up asked**: Iterative (shows understanding)
- **Space critical**: Morris (O(1) space but complex)

---

## Key Takeaways

1. **Inorder**: Left → Root → Right traversal order
2. **Recursive**: Natural, mirrors tree structure
3. **Iterative**: Uses explicit stack, same traversal
4. **BST property**: Inorder gives sorted values
5. **Go left first**: Core of inorder traversal
6. **Stack depth**: O(height) = O(log n) to O(n)
7. **Visit each node once**: O(n) time
8. **Three traversals**: Preorder, Inorder, Postorder (DFS)
9. **Morris**: O(1) space using threading (advanced)
10. **Handle null**: Both null root and null children

---

## Interview Tips

**What to say in an interview:**

> "Inorder traversal processes nodes in Left-Root-Right order. The recursive solution is straightforward—we recursively traverse the left subtree, visit the current node, then recursively traverse the right subtree. This naturally matches the tree's recursive structure.
>
> For the follow-up iterative solution, I'll use an explicit stack to simulate the recursion. The approach is to go left as far as possible while pushing nodes onto the stack. When we can't go left anymore, we pop a node, process it, and move to its right child. We continue until both the current pointer is null and the stack is empty.
>
> Both solutions visit each node exactly once, giving O(n) time complexity. The space complexity is O(h) where h is the tree height—best case O(log n) for balanced trees, worst case O(n) for skewed trees. This is for the call stack in recursion or the explicit stack in the iterative approach.
>
> An interesting property is that for binary search trees, inorder traversal gives us the nodes in sorted ascending order, which is useful for problems like validating BSTs or finding the kth smallest element."

**Key points to mention:**
1. **Inorder order**: Left → Root → Right
2. **Recursive**: Natural, clean, mirrors structure
3. **Iterative**: Explicit stack, go left → pop → process → go right
4. **BST property**: Inorder gives sorted values
5. **Time**: O(n) visit each node once
6. **Space**: O(h) for stack/recursion
7. **Both conditions**: `current != null || !stack.isEmpty()`
8. **Three DFS types**: Preorder, Inorder, Postorder
9. **Handle null**: Base case or loop condition
10. **Morris exists**: O(1) space but complex

**Common Follow-ups:**
- "Can you do it iteratively?" → Yes, with explicit stack
- "What about space complexity?" → O(h) for both, Morris has O(1)
- "What's the difference from preorder?" → Order: inorder is L-Root-R, preorder is Root-L-R
- "For BST, what does inorder give?" → Sorted values in ascending order
- "Can you do O(1) space?" → Yes, Morris traversal with threading (explain if asked)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| Binary Tree Preorder Traversal | Easy | DFS | Root → Left → Right |
| **Binary Tree Inorder Traversal** | Easy | **DFS** | **This problem** |
| Binary Tree Postorder Traversal | Easy | DFS | Left → Right → Root |
| Binary Tree Level Order Traversal | Medium | BFS | Level by level |
| Validate Binary Search Tree | Medium | Inorder | Use inorder to check sorted |
| Kth Smallest in BST | Medium | Inorder | Stop at kth element |
| Binary Tree Zigzag Traversal | Medium | BFS Variant | Alternate direction per level |

**Pattern Progression**:
1. **Inorder Traversal** (this) — Learn DFS pattern
2. **Preorder/Postorder** — Different DFS orders
3. **Validate BST** — Apply inorder property
4. **Kth Smallest BST** — Early termination on inorder

---

## Final Pattern Label

✅ **Depth-First Search (DFS) - Inorder Traversal**

**Remember:** This is **inorder traversal** with order **Left → Root → Right**. **Recursive solution** (simplest): base case `if (node == null) return`, recursive case: process left subtree, add current value, process right subtree. **Iterative solution** (follow-up): use explicit stack, main loop `while (current != null || !stack.isEmpty())`, inner loop goes left pushing nodes `while (current != null) { stack.push(current); current = current.left; }`, then pop and process `current = stack.pop(); result.add(current.val); current = current.right;`. **Time complexity**: O(n) visit each node once. **Space complexity**: O(h) where h = height (call stack or explicit stack), best O(log n) balanced, worst O(n) skewed. **BST property**: inorder traversal gives sorted ascending order (useful for validation, kth smallest). **Common mistakes**: wrong order (preorder vs inorder), iterative loop condition missing `current != null`, not going left first, not moving to right child after pop, wrong traversal type (BFS instead of DFS). **Three DFS orders**: Preorder (Root-L-R), Inorder (L-Root-R), Postorder (L-R-Root). **Morris traversal**: O(1) space using threading but complex, rarely needed. Pattern: **DFS inorder** for ordered tree processing, especially useful for **BST problems**! ✓
