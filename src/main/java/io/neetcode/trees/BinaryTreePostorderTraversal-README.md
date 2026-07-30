# Binary Tree Postorder Traversal

## Problem Description

**Difficulty**: Easy

You are given the `root` of a binary tree, return the **postorder traversal** of its nodes' values.

**Postorder Traversal Order**: **Left → Right → Root**

**Key Concepts:**
- **Postorder Traversal**: Process left subtree, then right subtree, then root
- **Recursive Solution**: Natural implementation (trivial)
- **Iterative Solution**: More complex than preorder/inorder (follow-up)
- **Order Property**: Root processed after both children
- **Use Cases**: Tree deletion, postfix expression evaluation

**Visual Example:**
```
Tree:
       1
      / \
     2   3
    / \ / \
   4  5 6  7

Postorder Traversal: Left → Right → Root
Process order:
  1. Go left to 2
  2. Go left to 4, visit 4 (leaf)
  3. Back to 2, go right to 5, visit 5 (leaf)
  4. Visit 2 (both children done)
  5. Back to 1, go right to 3
  6. Go left to 6, visit 6 (leaf)
  7. Back to 3, go right to 7, visit 7 (leaf)
  8. Visit 3 (both children done)
  9. Visit 1 (both children done - root last)

Result: [4, 5, 2, 6, 7, 3, 1]
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

Output: [4,5,2,6,7,3,1]

Explanation:
Postorder (Left → Right → Root):
  Visit 4 (left of 2)
  Visit 5 (right of 2)
  Visit 2 (both children done)
  Visit 6 (left of 3)
  Visit 7 (right of 3)
  Visit 3 (both children done)
  Visit 1 (both children done - root last)
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

Output: [4,2,5,3,1]

Explanation:
Postorder traversal:
  4 (right child of 2, leaf)
  2 (left child processed - null, right child done)
  5 (left child of 3, leaf)
  3 (both children done)
  1 (root last)
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
No children, visit root immediately
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
Go all the way left
Visit leaves first, work up to root
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

Output: [4,3,2,1]

Explanation:
Only right children
Visit rightmost first, work up to root
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

Output: [1,4,3,6,8,7,5]

Explanation:
Postorder traversal of BST
Not sorted (unlike inorder)
Root comes last
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

Output: [2,1]

Explanation:
Visit right child first, then root
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

Output: [8,9,4,10,5,2,6,7,3,1]

Explanation:
Postorder: Left → Right → Root
Process children before parent
Root is very last
```

---

## Constraints
- `0 <= number of nodes in the tree <= 100`
- `-100 <= Node.val <= 100`

**Recommended Complexity**: 
- Time: O(n) where n = number of nodes
- Space: O(h) where h = height (O(n) worst case for skewed tree)

---

## Pattern Recognition

**Primary Pattern**: **Tree Traversal - Depth First Search (DFS)**

**Why This Pattern?**
- **Postorder** is one of three DFS traversals (preorder, inorder, postorder)
- Processes **left subtree** before **right subtree** before **root**
- Natural **recursive** structure matches tree structure
- **Iterative** more complex: need to track visited state or use two stacks
- **Use cases**: Tree deletion, postfix expression, dependency resolution

**Key Insight**: Three DFS Traversal Orders
```
For any node:
  Preorder:  Root → Left → Right
  Inorder:   Left → Root → Right
  Postorder: Left → Right → Root  ← This problem

Tree:
    1
   / \
  2   3

Preorder:  [1, 2, 3]
Inorder:   [2, 1, 3]
Postorder: [2, 3, 1]

Postorder visits root LAST!
```

**Visual: Postorder Traversal Flow**
```
Tree:
       1
      / \
     2   3
    / \
   4   5

Recursive calls (Postorder: Left → Right → Root):

postorder(1):
  postorder(2):          // Left of 1
    postorder(4):        // Left of 2
      postorder(null)    // Left of 4
      postorder(null)    // Right of 4
      add 4 ✓            // Root of 4 (leaf)
    postorder(5):        // Right of 2
      postorder(null)    // Left of 5
      postorder(null)    // Right of 5
      add 5 ✓            // Root of 5 (leaf)
    add 2 ✓              // Root of 2 (after children)
  postorder(3):          // Right of 1
    postorder(null)      // Left of 3
    postorder(null)      // Right of 3
    add 3 ✓              // Root of 3 (leaf)
  add 1 ✓                // Root of 1 (LAST)

Result: [4, 5, 2, 3, 1]
```

**Why Recursive is Natural**:
```
Tree is recursive structure:
  - Node has left child (subtree)
  - Node has right child (subtree)
  - Each subtree is also a tree

Recursive traversal mirrors structure:
  traverse(left)   // Process left subtree first
  traverse(right)  // Process right subtree
  visit(root)      // Process current node LAST

Natural fit! ✓
```

**Why Iterative is Complex for Postorder**:
```
Problem: Need to visit root AFTER both children

Challenge:
  When we reach a node, we can't visit it yet
  Must process left, then right, THEN root
  Hard to track "already visited children"

Solutions:
  1. Two stacks (easier)
  2. One stack + visited tracking (complex)
  3. Reverse of modified preorder (clever)

More complex than preorder/inorder! ✓
```

**Iterative Algorithm Strategies**:

**Approach 1: Two Stacks (Easiest to Understand)**
```
Key insight:
  Postorder: Left → Right → Root
  Reverse:   Root → Right → Left (modified preorder)
  
Algorithm:
  1. Use stack1 to traverse: Root → Right → Left
  2. Push results to stack2
  3. Pop from stack2 for final result
  
Example:
  Tree: 1 → (2, 3)
  Stack1: Root → Right → Left gives [1, 3, 2]
  Stack2: Reverse gives [2, 3, 1] ✓
  
Clever! ✓
```

**Approach 2: One Stack with Last Visited Tracking**
```
Key insight:
  Track last visited node
  Only visit root if both children processed
  
Algorithm:
  1. Go left completely (push all)
  2. Check if right child exists and not visited
  3. If right not visited, process right
  4. If right visited or no right, visit current
  
Complex but optimal space! ✓
```

**Visual: Two Stacks Approach**
```
Tree:
    1
   / \
  2   3

Step 1: Modified preorder (Root → Right → Left)
  Process: 1 → 3 → 2
  Stack2: [1, 3, 2]

Step 2: Pop from stack2
  Result: [2, 3, 1] ✓

Postorder achieved! ✓
```

**Why Postorder Useful for Tree Deletion**:
```
When deleting tree:
  Must delete children before parent
  Otherwise lose reference to children!

Postorder gives children-first order:
  Delete left subtree
  Delete right subtree
  Delete root
  
Perfect for cleanup! ✓
```

**Core Operations**:

**Recursive Approach**:
```java
List<Integer> result = new ArrayList<>();

postorder(TreeNode root):
    if root == null:
        return
    
    postorder(root.left)     // Left first
    postorder(root.right)    // Right second
    result.add(root.val)     // Root LAST
```

**Iterative Approach (Two Stacks)**:
```java
List<Integer> result = new ArrayList<>();
if (root == null) return result;

Stack<TreeNode> stack1 = new Stack<>();
Stack<TreeNode> stack2 = new Stack<>();
stack1.push(root);

// Build reverse postorder in stack2
while (!stack1.isEmpty()):
    TreeNode node = stack1.pop()
    stack2.push(node)
    
    // Push left first, then right (for Root → Right → Left)
    if (node.left != null):
        stack1.push(node.left)
    if (node.right != null):
        stack1.push(node.right)

// Pop from stack2 for postorder
while (!stack2.isEmpty()):
    result.add(stack2.pop().val)
```

**Related Patterns**:
1. **Preorder Traversal** — Root → Left → Right
2. **Inorder Traversal** — Left → Root → Right
3. **Postorder Traversal** — This problem (Left → Right → Root)
4. **Level Order Traversal** — BFS, level by level

---

## Algorithm & Approach

### Core Insight

**Why Postorder Traversal Works:**
```
Key observations:
  1. Tree is recursive: each node has left/right subtrees
  2. Postorder: process left, then right, then root
  3. Recursive solution mirrors tree structure
  4. Iterative complex: root visited AFTER children
  5. Visit each node exactly once: O(n)
```

**The Optimal Strategy**:
```
Recursive (Simple):
  - Base case: null node returns
  - Recursive case: left → right → visit
  - Natural and clean
  - O(h) space for call stack

Iterative - Two Stacks (Recommended):
  - Build reverse postorder in stack2
  - Modified preorder: Root → Right → Left
  - Pop stack2 for postorder
  - O(n) space but simpler logic

Iterative - One Stack (Optimal Space):
  - Track last visited node
  - Complex logic
  - O(h) space
```

### Step-by-Step Algorithm

---

#### **Approach 1: Recursive - SIMPLEST**

**Core Idea**:
- Recursively traverse left subtree first
- Recursively traverse right subtree
- Visit current node LAST (after both children)
- Natural and clean implementation

**Algorithm**
```java
postorderTraversal(TreeNode root):
    result = new ArrayList<>()
    postorderHelper(root, result)
    return result

postorderHelper(TreeNode node, List<Integer> result):
    if node == null:
        return
    
    postorderHelper(node.left, result)  // Left first
    postorderHelper(node.right, result) // Right second
    result.add(node.val)                // Root LAST
```

**Complete Code Implementation (Recursive)**
```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }
    
    private void postorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // Left first
        postorderHelper(node.left, result);
        
        // Right second
        postorderHelper(node.right, result);
        
        // Root LAST
        result.add(node.val);
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
postorder(1):
  postorder(2):          // Left of 1
    postorder(null)      // Left of 2
    postorder(null)      // Right of 2
    add 2               // Root of 2 (leaf)
  postorder(3):          // Right of 1
    postorder(null)      // Left of 3
    postorder(null)      // Right of 3
    add 3               // Root of 3 (leaf)
  add 1                 // Root of 1 (LAST)

Result: [2, 3, 1] ✓
```

---

#### **Approach 2: Iterative - Two Stacks (EASIEST ITERATIVE)**

**Core Idea**:
- Postorder is reverse of modified preorder
- Modified preorder: Root → Right → Left
- Use stack1 for modified preorder
- Push to stack2, then pop for postorder
- Simple and intuitive

**Algorithm**
```java
postorderTraversal(TreeNode root):
    result = new ArrayList<>()
    if root == null:
        return result
    
    stack1 = new Stack<>()
    stack2 = new Stack<>()
    stack1.push(root)
    
    // Modified preorder: Root → Right → Left
    while !stack1.isEmpty():
        node = stack1.pop()
        stack2.push(node)
        
        // Push left first (processed later)
        if node.left != null:
            stack1.push(node.left)
        
        // Push right (processed before left)
        if node.right != null:
            stack1.push(node.right)
    
    // Pop from stack2 gives postorder
    while !stack2.isEmpty():
        result.add(stack2.pop().val)
    
    return result
```

**Complete Code Implementation (Two Stacks)**
```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        stack1.push(root);
        
        // Build reverse postorder in stack2
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            
            // Push left first (for Root → Right → Left order)
            if (node.left != null) {
                stack1.push(node.left);
            }
            
            // Push right
            if (node.right != null) {
                stack1.push(node.right);
            }
        }
        
        // Pop from stack2 for postorder
        while (!stack2.isEmpty()) {
            result.add(stack2.pop().val);
        }
        
        return result;
    }
}
```

**Example Walkthrough (Two Stacks)**

Input: root = [1,2,3,4,5]
```
Tree:
       1
      / \
     2   3
    / \
   4   5
```

**Step-by-Step:**
```
Phase 1: Build reverse in stack2

Initial: stack1 = [1], stack2 = []

Step 1: Pop 1, push to stack2
  stack2 = [1]
  Push left(2), right(3)
  stack1 = [2, 3]

Step 2: Pop 3, push to stack2
  stack2 = [1, 3]
  3 has no children
  stack1 = [2]

Step 3: Pop 2, push to stack2
  stack2 = [1, 3, 2]
  Push left(4), right(5)
  stack1 = [4, 5]

Step 4: Pop 5, push to stack2
  stack2 = [1, 3, 2, 5]
  5 has no children
  stack1 = [4]

Step 5: Pop 4, push to stack2
  stack2 = [1, 3, 2, 5, 4]
  4 has no children
  stack1 = []

Phase 2: Pop from stack2
  Pop 4: result = [4]
  Pop 5: result = [4, 5]
  Pop 2: result = [4, 5, 2]
  Pop 3: result = [4, 5, 2, 3]
  Pop 1: result = [4, 5, 2, 3, 1] ✓

Postorder achieved!
```

---

#### **Approach 3: Iterative - One Stack with Last Visited (OPTIMAL SPACE)**

**Core Idea**:
- Track last visited node
- Only visit current if both children processed
- More complex but uses O(h) space
- Similar to postorder iteration pattern

**Algorithm**
```java
postorderTraversal(TreeNode root):
    result = new ArrayList<>()
    stack = new Stack<>()
    current = root
    lastVisited = null
    
    while current != null or !stack.isEmpty():
        // Go left completely
        while current != null:
            stack.push(current)
            current = current.left
        
        // Peek (don't pop yet)
        peekNode = stack.peek()
        
        // If right child exists and not visited yet
        if peekNode.right != null and lastVisited != peekNode.right:
            current = peekNode.right  // Process right
        else:
            // No right or right already visited
            result.add(peekNode.val)
            lastVisited = stack.pop()
            current = null  // Don't go left again
    
    return result
```

**Complete Code Implementation (One Stack)**
```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        
        while (current != null || !stack.isEmpty()) {
            // Go left completely
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // Peek at top (don't pop yet)
            TreeNode peekNode = stack.peek();
            
            // If right child exists and not yet visited
            if (peekNode.right != null && lastVisited != peekNode.right) {
                current = peekNode.right;
            } else {
                // No right or right already visited, safe to visit
                result.add(peekNode.val);
                lastVisited = stack.pop();
                current = null; // Don't go left again
            }
        }
        
        return result;
    }
}
```

**Complexity Analysis**
- **Recursive**: O(n) time, O(h) space (call stack)
- **Two Stacks**: O(n) time, O(n) space (both stacks can hold all nodes)
- **One Stack**: O(n) time, O(h) space (optimal)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Difficulty | Clean Code | Recommended |
|----------|------|-------|------------|------------|-------------|
| **Recursive** | **O(n)** | **O(h)** | **Easy** | **Yes** | **Yes (default)** |
| **Two Stacks** | **O(n)** | **O(n)** | **Medium** | **Yes** | **Yes (follow-up)** |
| One Stack | O(n) | O(h) | Hard | Medium | Optimal but complex |

**Winner**: **Recursive** for simplicity, **Two Stacks** for iterative follow-up

### Why Recursive is Natural

```
Tree is recursive structure:
  Node {
    value
    left: subtree
    right: subtree
  }

Postorder traversal mirrors structure:
  process(left subtree)
  process(right subtree)
  process(node)

Perfect match! ✓
Natural and clean code ✓
```

### Why Two Stacks Approach is Clever

```
Postorder: Left → Right → Root
  [4, 5, 2, 3, 1]

Reverse: Root → Right → Left
  [1, 3, 2, 5, 4]

Modified Preorder!
  Just push left before right
  Then reverse result

Elegant insight! ✓
```

### Why Postorder More Complex Than Preorder/Inorder

```
Preorder: Root → Left → Right
  Visit root immediately
  Simple iterative with stack

Inorder: Left → Root → Right
  Go left, visit on pop, go right
  Straightforward pattern

Postorder: Left → Right → Root
  Can't visit root until AFTER both children
  Need to track visited state
  More complex! ✓
```

### Why Postorder Useful for Tree Deletion

```
Tree deletion:
  Must free children before parent
  Otherwise lose references!

Postorder gives children-first:
  delete(left)
  delete(right)
  delete(root)

Perfect for cleanup! ✓

Also useful for:
  - Postfix expression evaluation
  - Dependency resolution
  - Bottom-up calculations
```

### Why This is Optimal

```
Time complexity:
  Must visit all n nodes: Ω(n)
  Each node visited once: O(n)
  Optimal! ✓

Space complexity:
  Recursive/One Stack: O(h) for stack
    Balanced tree: h = log n
    Skewed tree: h = n
    Optimal for DFS! ✓
  
  Two Stacks: O(n) worst case
    Simpler but more space
    Still acceptable ✓

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
// No children, visit root immediately
```

### 3. **Left Skewed Tree**
```java
    1
   /
  2
 /
3

// Result: [3, 2, 1]
// Leaves first, work up to root
```

### 4. **Right Skewed Tree**
```java
1
 \
  2
   \
    3

// Result: [3, 2, 1]
// Same as left-skewed for postorder
```

### 5. **Complete Binary Tree**
```java
       1
      / \
     2   3
    / \ / \
   4  5 6  7

// Result: [4, 5, 2, 6, 7, 3, 1]
// Children before parents, root last
```

### 6. **Binary Search Tree**
```java
     4
    / \
   2   6
  / \ / \
 1  3 5  7

// Result: [1, 3, 2, 5, 7, 6, 4]
// NOT sorted (unlike inorder)
```

### 7. **Tree with Only Left Children**
```java
    1
   /
  2
 /
3

// Result: [3, 2, 1]
// Bottom-up order
```

### 8. **Tree with Only Right Children**
```java
1
 \
  2
   \
    3

// Result: [3, 2, 1]
// Bottom-up order
```

### 9. **Negative Values**
```java
     0
    / \
  -5   5

// Result: [-5, 5, 0]
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

### ❌ **MISTAKE 1: Wrong Traversal Order (Preorder Instead)**
```java
// WRONG - this is PREORDER, not postorder
private void postorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    result.add(node.val);               // ❌ Root first (preorder)
    postorderHelper(node.left, result);
    postorderHelper(node.right, result);
}
```

**Why wrong**: Processes root before children!

**Issue:**
```
Postorder: Left → Right → Root
Preorder:  Root → Left → Right

Completely wrong! ❌
```

**Fix**: Root LAST
```java
private void postorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    postorderHelper(node.left, result);  // Left first ✓
    postorderHelper(node.right, result); // Right second ✓
    result.add(node.val);                // Root LAST ✓
}
```

### ❌ **MISTAKE 2: Two Stacks - Wrong Push Order**
```java
// WRONG - wrong push order for modified preorder
while (!stack1.isEmpty()) {
    TreeNode node = stack1.pop();
    stack2.push(node);
    
    if (node.right != null) {
        stack1.push(node.right);  // ❌ Right first
    }
    if (node.left != null) {
        stack1.push(node.left);   // ❌ Left second
    }
}
```

**Why wrong**: Gives Root → Left → Right, not Root → Right → Left!

**Dry run failure:**
```
Tree: 1 → (2, 3)

Pop 1, push right(3), then left(2)
Stack1: [3, 2] (2 on top)

Pop 2 next (should be 3)
Wrong order! ❌
```

**Fix**: Push left before right
```java
// Push left first (for Root → Right → Left)
if (node.left != null) {
    stack1.push(node.left);  ✓
}
if (node.right != null) {
    stack1.push(node.right); ✓
}
```

### ❌ **MISTAKE 3: Inorder Instead of Postorder**
```java
// WRONG - this is INORDER, not postorder
private void postorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    postorderHelper(node.left, result);  // Left
    result.add(node.val);                // ❌ Root in middle (inorder)
    postorderHelper(node.right, result); // Right
}
```

**Why wrong**: Root in middle!

**Issue:**
```
Inorder:   Left → Root → Right
Postorder: Left → Right → Root

Root position wrong! ❌
```

**Fix**: Root after right
```java
postorderHelper(node.left, result);  // Left ✓
postorderHelper(node.right, result); // Right ✓
result.add(node.val);                // Root LAST ✓
```

### ❌ **MISTAKE 4: Not Handling Null Root in Two Stacks**
```java
// WRONG - pushes null to stack
Stack<TreeNode> stack1 = new Stack<>();
Stack<TreeNode> stack2 = new Stack<>();
stack1.push(root);  // ❌ What if root is null?

while (!stack1.isEmpty()) {
    TreeNode node = stack1.pop();  // NullPointerException if root was null
    stack2.push(node);
}
```

**Why wrong**: Null root causes issues!

**Fix**: Check null before starting
```java
if (root == null) {  ✓
    return result;
}

Stack<TreeNode> stack1 = new Stack<>();
stack1.push(root);  // Safe now
```

### ❌ **MISTAKE 5: One Stack - Not Tracking Last Visited**
```java
// WRONG - no lastVisited tracking
while (current != null || !stack.isEmpty()) {
    while (current != null) {
        stack.push(current);
        current = current.left;
    }
    
    TreeNode node = stack.pop();
    result.add(node.val);  // ❌ Might visit before right child!
    current = node.right;
}
```

**Why wrong**: Visits node before processing right child!

**Fix**: Track last visited
```java
TreeNode lastVisited = null;  ✓

while (current != null || !stack.isEmpty()) {
    while (current != null) {
        stack.push(current);
        current = current.left;
    }
    
    TreeNode peekNode = stack.peek();
    
    if (peekNode.right != null && lastVisited != peekNode.right) {
        current = peekNode.right;  // Process right first
    } else {
        result.add(peekNode.val);  // Safe to visit now
        lastVisited = stack.pop(); ✓
    }
}
```

### ❌ **MISTAKE 6: Two Stacks - Forgetting Second Phase**
```java
// WRONG - only builds stack2, doesn't pop it
while (!stack1.isEmpty()) {
    TreeNode node = stack1.pop();
    stack2.push(node);
    
    if (node.left != null) stack1.push(node.left);
    if (node.right != null) stack1.push(node.right);
}

return result;  // ❌ Result is empty!
```

**Why wrong**: Forgot to pop from stack2!

**Fix**: Pop from stack2
```java
// Phase 2: Pop from stack2 for postorder
while (!stack2.isEmpty()) {  ✓
    result.add(stack2.pop().val);
}

return result;  ✓
```

### ❌ **MISTAKE 7: Using Queue Instead of Stack**
```java
// WRONG - Queue gives level order, not postorder
Queue<TreeNode> queue = new LinkedList<>();
queue.offer(root);

while (!queue.isEmpty()) {
    TreeNode node = queue.poll();
    result.add(node.val);  // ❌ Level order (BFS)
    
    if (node.left != null) queue.offer(node.left);
    if (node.right != null) queue.offer(node.right);
}
```

**Why wrong**: Different traversal type!

**Issue:**
```
Level Order (BFS): Process by levels
Postorder (DFS): Left → Right → Root

Completely different! ❌
```

**Fix**: Use stack (DFS), not queue (BFS)
```java
Stack<TreeNode> stack = new Stack<>();  ✓
```

### ❌ **MISTAKE 8: One Stack - Popping Instead of Peeking**
```java
// WRONG - pops too early
TreeNode node = stack.pop();  // ❌ Should peek first

if (node.right != null && lastVisited != node.right) {
    // Can't push back! Already popped!
    current = node.right;
}
```

**Why wrong**: Can't check right child after popping!

**Fix**: Peek first, pop only when visiting
```java
TreeNode peekNode = stack.peek();  ✓

if (peekNode.right != null && lastVisited != peekNode.right) {
    current = peekNode.right;
} else {
    result.add(peekNode.val);
    lastVisited = stack.pop();  ✓ Pop only here
}
```

### ❌ **MISTAKE 9: Two Stacks - Pushing to Wrong Stack**
```java
// WRONG - pushes to result directly
while (!stack1.isEmpty()) {
    TreeNode node = stack1.pop();
    result.add(node.val);  // ❌ Should push to stack2
    
    if (node.left != null) stack1.push(node.left);
    if (node.right != null) stack1.push(node.right);
}
```

**Why wrong**: No reversal happens!

**Fix**: Push to stack2, then pop
```java
while (!stack1.isEmpty()) {
    TreeNode node = stack1.pop();
    stack2.push(node);  ✓ Push to stack2
    
    if (node.left != null) stack1.push(node.left);
    if (node.right != null) stack1.push(node.right);
}

// Then pop from stack2
while (!stack2.isEmpty()) {
    result.add(stack2.pop().val);  ✓
}
```

### ❌ **MISTAKE 10: No Base Case in Recursion**
```java
// WRONG - no base case
private void postorderHelper(TreeNode node, List<Integer> result) {
    // Missing: if (node == null) return; ❌
    
    postorderHelper(node.left, result);   // Stack overflow if null!
    postorderHelper(node.right, result);
    result.add(node.val);
}
```

**Why wrong**: Infinite recursion on null!

**Fix**: Add base case
```java
private void postorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) {  ✓
        return;
    }
    
    postorderHelper(node.left, result);
    postorderHelper(node.right, result);
    result.add(node.val);
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

Iterative (Both):
  Each node pushed and popped once: O(n)
```

**Detailed Analysis**:
```
For each node:
  Recursive: visit once (left, right, process)
  Two Stacks: push to stack1, push to stack2, pop from stack2
  One Stack: push once, pop once, possible peek
  
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

**Two Stacks: O(n)**
```
Space for two stacks:
  - Stack1: Can hold up to n nodes
  - Stack2: Can hold up to n nodes
  - Worst case: O(n)
  
Trade-off: Simpler logic, more space
```

**One Stack: O(h)** where h = height
```
Space for explicit stack:
  - Same as recursive call stack
  - Stores nodes along path
  - Maximum size = height
  
  Best: O(log n)
  Worst: O(n)
  
Optimal but complex! ✓
```

### Optimal Complexity

```
Time: O(n)
  - Must visit all nodes: Ω(n)
  - All approaches: O(n)
  - Optimal! ✓

Space:
  - Recursive/One Stack: O(h) optimal for DFS
  - Two Stacks: O(n) but simpler
  
Choose based on trade-off:
  Simple: Two Stacks
  Optimal: One Stack
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
Call postorder(1):
  Call postorder(2):        // Left of 1
    Call postorder(4):      // Left of 2
      Call postorder(null)  // Left of 4 → return
      Call postorder(null)  // Right of 4 → return
      Add 4 to result       // Root of 4
    Call postorder(5):      // Right of 2
      Call postorder(null)  // Left of 5 → return
      Call postorder(null)  // Right of 5 → return
      Add 5 to result       // Root of 5
    Add 2 to result         // Root of 2 (after children)
  Call postorder(3):        // Right of 1
    Call postorder(null)    // Left of 3 → return
    Call postorder(null)    // Right of 3 → return
    Add 3 to result         // Root of 3
  Add 1 to result           // Root of 1 (LAST)

Result: [4, 5, 2, 3, 1] ✓
```

---

### Complete Example Walkthrough (Two Stacks)

**Input:** `root = [1,2,3,4,5]`

**Phase 1: Build Reverse Postorder in Stack2**

```
Initial:
  stack1 = [1], stack2 = []

Iteration 1: Process 1
  Pop 1 from stack1, push to stack2
  stack2 = [1]
  Push left(2), right(3) to stack1
  stack1 = [2, 3]

Iteration 2: Process 3
  Pop 3 from stack1, push to stack2
  stack2 = [1, 3]
  3 has no children
  stack1 = [2]

Iteration 3: Process 2
  Pop 2 from stack1, push to stack2
  stack2 = [1, 3, 2]
  Push left(4), right(5) to stack1
  stack1 = [4, 5]

Iteration 4: Process 5
  Pop 5 from stack1, push to stack2
  stack2 = [1, 3, 2, 5]
  5 has no children
  stack1 = [4]

Iteration 5: Process 4
  Pop 4 from stack1, push to stack2
  stack2 = [1, 3, 2, 5, 4]
  4 has no children
  stack1 = []

Phase 2: Pop from Stack2
  Pop 4: result = [4]
  Pop 5: result = [4, 5]
  Pop 2: result = [4, 5, 2]
  Pop 3: result = [4, 5, 2, 3]
  Pop 1: result = [4, 5, 2, 3, 1] ✓

Postorder achieved!
```

---

### Visual: Stack States (Two Stacks)

```
Tree:       1
           / \
          2   3
         / \
        4   5

Two Stacks visualization:

Initial:
  stack1 = [1]
  stack2 = []

After pop 1:
  stack1 = [2, 3] (3 on top)
  stack2 = [1]

After pop 3:
  stack1 = [2]
  stack2 = [1, 3]

After pop 2:
  stack1 = [4, 5] (5 on top)
  stack2 = [1, 3, 2]

After pop 5:
  stack1 = [4]
  stack2 = [1, 3, 2, 5]

After pop 4:
  stack1 = []
  stack2 = [1, 3, 2, 5, 4]

Pop from stack2:
  [4, 5, 2, 3, 1] ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Difficulty | Clean Code | Recommended |
|----------|------|-------|------------|------------|-------------|
| **Recursive** | **O(n)** | **O(h)** | **Easy** | **Yes** | **Yes (default)** |
| **Two Stacks** | **O(n)** | **O(n)** | **Medium** | **Yes** | **Yes (follow-up)** |
| One Stack | O(n) | O(h) | Hard | Medium | Optimal but complex |

**When to Use Each:**
- **Interview default**: Recursive (clean, simple)
- **Follow-up asked**: Two Stacks (easier iterative)
- **Optimal space**: One Stack (complex but O(h))

---

## Key Takeaways

1. **Postorder**: Left → Right → Root traversal order
2. **Recursive**: Natural, root processed LAST
3. **Two Stacks**: Reverse of modified preorder
4. **Root visited last**: Core of postorder
5. **More complex**: Than preorder/inorder iteratively
6. **Stack depth**: O(height) = O(log n) to O(n)
7. **Visit each node once**: O(n) time
8. **Three DFS traversals**: Preorder, Inorder, Postorder
9. **Use cases**: Tree deletion, postfix expressions, dependency resolution
10. **Handle null**: Both null root and null children

---

## Interview Tips

**What to say in an interview:**

> "Postorder traversal processes nodes in Left-Right-Root order, meaning we visit both children before the parent. The recursive solution is straightforward—we recursively traverse the left subtree first, then the right subtree, and finally visit the current node. This naturally gives us the postorder.
>
> For the follow-up iterative solution, postorder is trickier than preorder or inorder because we can't visit a node until both its children are processed. I'll use a two-stack approach, which is elegant. The key insight is that postorder is the reverse of a modified preorder traversal (Root-Right-Left). So I'll use stack1 to build this modified preorder by pushing left before right, collecting nodes in stack2. Then popping from stack2 gives us the postorder.
>
> Both solutions visit each node exactly once, giving O(n) time complexity. The recursive approach uses O(h) space for the call stack, while the two-stack approach uses O(n) space in the worst case. There's also a one-stack approach with O(h) space that tracks the last visited node, but it's more complex.
>
> Postorder traversal is particularly useful for tree deletion and dependency resolution, since we process children before parents, ensuring we don't lose references."

**Key points to mention:**
1. **Postorder order**: Left → Right → Root
2. **Recursive**: Natural, process root LAST
3. **Two Stacks**: Reverse of modified preorder (Root-Right-Left)
4. **Complex iterative**: Need to track children processed
5. **Time**: O(n) visit each node once
6. **Space**: O(h) recursive, O(n) two stacks, O(h) one stack
7. **Why tricky**: Root visited after both children
8. **Three DFS types**: Preorder, Inorder, Postorder
9. **Use cases**: Tree deletion, postfix expressions, bottom-up calculations
10. **Handle null**: Check before processing

**Common Follow-ups:**
- "Can you do it iteratively?" → Yes, with two stacks (simpler) or one stack (optimal)
- "Why is postorder more complex iteratively?" → Root visited AFTER children, need tracking
- "What's the two-stack approach?" → Build reverse postorder, then pop
- "When is postorder useful?" → Tree deletion, postfix expression, dependency resolution
- "Can you optimize space?" → One stack with last visited tracking (O(h) space)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| Binary Tree Preorder Traversal | Easy | DFS | Root → Left → Right |
| Binary Tree Inorder Traversal | Easy | DFS | Left → Root → Right |
| **Binary Tree Postorder Traversal** | Easy | **DFS** | **This problem (Left → Right → Root)** |
| Binary Tree Level Order Traversal | Medium | BFS | Level by level |
| Delete Nodes And Return Forest | Medium | Postorder | Use postorder for deletion |
| Binary Tree Maximum Path Sum | Hard | Postorder | Bottom-up calculation |
| Lowest Common Ancestor | Medium | Postorder | Process children first |

**Pattern Progression**:
1. **Preorder/Inorder/Postorder** — Learn DFS patterns
2. **Tree Deletion** — Apply postorder
3. **Bottom-up Calculations** — Use postorder
4. **Path Problems** — Postorder for returning results

---

## Final Pattern Label

✅ **Depth-First Search (DFS) - Postorder Traversal**

**Remember:** This is **postorder traversal** with order **Left → Right → Root**. **Recursive solution** (simplest): base case `if (node == null) return`, recursive case: process left subtree first, process right subtree, add current value LAST (after both children). **Iterative two-stack solution** (follow-up): use stack1 to build modified preorder (Root → Right → Left) by pushing left before right and collecting in stack2, then pop stack2 for postorder (reverse). **Why two stacks**: Postorder is reverse of modified preorder, clever reversal trick. **Iterative one-stack solution** (optimal): track last visited node, only visit current if both children processed, more complex but O(h) space. **Time complexity**: O(n) visit each node once. **Space complexity**: O(h) recursive/one-stack (best O(log n) balanced, worst O(n) skewed), O(n) two-stack approach. **Use cases**: tree deletion (children before parent), postfix expression evaluation, dependency resolution, bottom-up calculations. **Common mistakes**: wrong order (preorder/inorder instead), two-stack wrong push order (right before left gives wrong modified preorder), forgetting to pop from stack2, one-stack not tracking last visited (visits before children processed), popping instead of peeking. **Three DFS orders**: Preorder (Root-L-R), Inorder (L-Root-R), Postorder (L-R-Root). **Why postorder complex**: root visited AFTER children, need tracking. Pattern: **DFS postorder** for **children-before-parent processing**, especially useful for **tree cleanup and bottom-up problems**! ✓
