# Binary Tree Preorder Traversal

## Problem Description

**Difficulty**: Easy

You are given the `root` of a binary tree, return the **preorder traversal** of its nodes' values.

**Preorder Traversal Order**: **Root → Left → Right**

**Key Concepts:**
- **Preorder Traversal**: Process root, then left subtree, then right subtree
- **Recursive Solution**: Natural and trivial implementation
- **Iterative Solution**: Uses explicit stack (follow-up requirement)
- **Order Property**: Root processed before children
- **Use Cases**: Tree serialization, prefix expression evaluation

**Visual Example:**
```
Tree:
       1
      / \
     2   3
    / \ / \
   4  5 6  7

Preorder Traversal: Root → Left → Right
Process order:
  1. Visit 1 (root)
  2. Go left to 2
  3. Visit 2 (root of left subtree)
  4. Go left to 4, visit 4
  5. Back to 2, go right to 5, visit 5
  6. Back to 1, go right to 3
  7. Visit 3 (root of right subtree)
  8. Go left to 6, visit 6
  9. Back to 3, go right to 7, visit 7

Result: [1, 2, 4, 5, 3, 6, 7]
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

Output: [1,2,4,5,3,6,7]

Explanation:
Preorder (Root → Left → Right):
  Visit 1 (main root)
  Visit 2 (root of left subtree)
  Visit 4 (left of 2)
  Visit 5 (right of 2)
  Visit 3 (root of right subtree)
  Visit 6 (left of 3)
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

Output: [1,2,4,3,5]

Explanation:
Preorder traversal:
  1 (root)
  2 (left child of 1)
  4 (right child of 2)
  3 (right child of 1)
  5 (left child of 3)
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
Preorder traversal returns [1]
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

Output: [1,2,3,4]

Explanation:
Visit root first, then traverse left
Same as level-by-level for left-skewed
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
Visit each root before going right
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

Output: [5,3,1,4,7,6,8]

Explanation:
Preorder traversal of BST
Not sorted (unlike inorder)
```

### Example 8 (Two Nodes - Left Child):
```
Input: root = [1,2]

Tree:
  1
 /
2

Output: [1,2]

Explanation:
Visit root first, then left child
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

Output: [1,2,4,8,9,5,10,3,6,7]

Explanation:
Preorder: Root first, then left subtree, then right subtree
Process recursively at each level
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
- **Preorder** is one of three DFS traversals (preorder, inorder, postorder)
- Processes **root** before **left subtree** before **right subtree**
- Natural **recursive** structure matches tree structure
- Can be done **iteratively** with explicit stack
- **Use cases**: Tree copying, prefix expression, serialization

**Key Insight**: Three DFS Traversal Orders
```
For any node:
  Preorder:  Root → Left → Right  ← This problem
  Inorder:   Left → Root → Right
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

**Visual: Preorder Traversal Flow**
```
Tree:
       1
      / \
     2   3
    / \
   4   5

Recursive calls (Preorder: Root → Left → Right):

preorder(1):
  add 1 ✓               // Root (1)
  preorder(2):          // Left
    add 2 ✓             // Root (2)
    preorder(4):        // Left
      add 4 ✓           // Root (4)
      preorder(null)    // Left of 4
      preorder(null)    // Right of 4
    preorder(5):        // Right
      add 5 ✓           // Root (5)
      preorder(null)    // Left of 5
      preorder(null)    // Right of 5
  preorder(3):          // Right
    add 3 ✓             // Root (3)
    preorder(null)      // Left of 3
    preorder(null)      // Right of 3

Result: [1, 2, 4, 5, 3]
```

**Why Recursive is Natural**:
```
Tree is recursive structure:
  - Node has left child (subtree)
  - Node has right child (subtree)
  - Each subtree is also a tree

Recursive traversal mirrors structure:
  visit(root)      // Process current node first
  traverse(left)   // Process left subtree
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
Approach 1 (Stack with Right-First Push):
  1. Push root to stack
  2. While stack not empty:
     - Pop node
     - Visit node
     - Push right child (if exists)
     - Push left child (if exists)
  3. Repeat

Why push right before left?
  Stack is LIFO: Last In, First Out
  Want to process left before right
  So push right first, then left
  Left will be popped first! ✓

Approach 2 (Traditional - similar to inorder):
  Use current pointer and stack
  More similar to inorder pattern
```

**Visual: Iterative with Stack**
```
Tree:
    1
   / \
  2   3

Using Approach 1 (Right-First Push):

Initial: stack = [1], result = []

1. Pop 1:
   Visit 1, result = [1]
   Push 3 (right), Push 2 (left)
   stack = [3, 2]

2. Pop 2:
   Visit 2, result = [1, 2]
   2 has no children
   stack = [3]

3. Pop 3:
   Visit 3, result = [1, 2, 3]
   3 has no children
   stack = []

4. Stack empty, done!

Result: [1, 2, 3] ✓

Simple and intuitive! ✓
```

**Why Preorder Useful for Serialization**:
```
Preorder gives root-first structure:
  Can reconstruct tree from preorder + inorder
  Can serialize tree: root first, then children
  Can create copy: process root, then clone children

Use cases:
  - Tree serialization/deserialization
  - Creating deep copies
  - Prefix expression evaluation
```

**Core Operations**:

**Recursive Approach**:
```java
List<Integer> result = new ArrayList<>();

preorder(TreeNode root):
    if root == null:
        return
    
    result.add(root.val)    // Root first
    preorder(root.left)     // Left
    preorder(root.right)    // Right
```

**Iterative Approach (Stack with Right-First Push)**:
```java
List<Integer> result = new ArrayList<>();
if (root == null) return result;

Stack<TreeNode> stack = new Stack<>();
stack.push(root);

while (!stack.isEmpty()):
    TreeNode node = stack.pop()
    result.add(node.val)
    
    // Push right first, then left (LIFO order)
    if (node.right != null):
        stack.push(node.right)
    if (node.left != null):
        stack.push(node.left)
```

**Related Patterns**:
1. **Preorder Traversal** — This problem (Root → Left → Right)
2. **Inorder Traversal** — Left → Root → Right
3. **Postorder Traversal** — Left → Right → Root
4. **Level Order Traversal** — BFS, level by level

---

## Algorithm & Approach

### Core Insight

**Why Preorder Traversal Works:**
```
Key observations:
  1. Tree is recursive: each node has left/right subtrees
  2. Preorder: process root first, then left, then right
  3. Recursive solution mirrors tree structure
  4. Iterative solution uses stack (right before left)
  5. Visit each node exactly once: O(n)
```

**The Optimal Strategy**:
```
Recursive (Simple):
  - Base case: null node returns
  - Recursive case: visit → left → right
  - Natural and clean
  - O(h) space for call stack

Iterative (Follow-up):
  - Explicit stack instead of call stack
  - Push right, then left (LIFO gives left first)
  - Pop, visit, continue
  - Same O(h) space for stack
```

### Step-by-Step Algorithm

---

#### **Approach 1: Recursive - SIMPLEST**

**Core Idea**:
- Visit current node first
- Recursively traverse left subtree
- Recursively traverse right subtree
- Natural and clean implementation

**Algorithm**
```java
preorderTraversal(TreeNode root):
    result = new ArrayList<>()
    preorderHelper(root, result)
    return result

preorderHelper(TreeNode node, List<Integer> result):
    if node == null:
        return
    
    result.add(node.val)               // Root first
    preorderHelper(node.left, result)  // Left
    preorderHelper(node.right, result) // Right
```

**Complete Code Implementation (Recursive)**
```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }
    
    private void preorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        // Root first
        result.add(node.val);
        
        // Left
        preorderHelper(node.left, result);
        
        // Right
        preorderHelper(node.right, result);
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
preorder(1):
  add 1                 // Root (1)
  preorder(2):          // Left of 1
    add 2               // Root (2)
    preorder(null)      // Left of 2
    preorder(null)      // Right of 2
  preorder(3):          // Right of 1
    add 3               // Root (3)
    preorder(null)      // Left of 3
    preorder(null)      // Right of 3

Result: [1, 2, 3] ✓
```

---

#### **Approach 2: Iterative with Stack (Right-First Push) - FOLLOW-UP**

**Core Idea**:
- Use explicit stack starting with root
- Pop node, visit it
- Push right child first (LIFO: want left processed first)
- Push left child (will be popped before right)
- Simple and intuitive for preorder

**Algorithm**
```java
preorderTraversal(TreeNode root):
    result = new ArrayList<>()
    if root == null:
        return result
    
    stack = new Stack<>()
    stack.push(root)
    
    while !stack.isEmpty():
        node = stack.pop()
        result.add(node.val)
        
        // Push right first (processed later)
        if node.right != null:
            stack.push(node.right)
        
        // Push left (processed next)
        if node.left != null:
            stack.push(node.left)
    
    return result
```

**Complete Code Implementation (Iterative - Right-First)**
```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            
            // Push right first (LIFO: left will be processed first)
            if (node.right != null) {
                stack.push(node.right);
            }
            
            // Push left (will be popped before right)
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        
        return result;
    }
}
```

**Example Walkthrough (Iterative)**

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
Initial:
  stack = [1], result = []

Step 1: Pop 1
  result = [1]
  Push 3 (right), Push 2 (left)
  stack = [3, 2]

Step 2: Pop 2
  result = [1, 2]
  Push 5 (right), Push 4 (left)
  stack = [3, 5, 4]

Step 3: Pop 4
  result = [1, 2, 4]
  4 has no children
  stack = [3, 5]

Step 4: Pop 5
  result = [1, 2, 4, 5]
  5 has no children
  stack = [3]

Step 5: Pop 3
  result = [1, 2, 4, 5, 3]
  3 has no children
  stack = []

Done: stack empty

Result: [1, 2, 4, 5, 3] ✓
```

---

#### **Approach 3: Iterative with Current Pointer - ALTERNATIVE**

**Core Idea**:
- Similar pattern to inorder traversal
- Use current pointer and stack
- Visit node when first encountered (not when popped)
- More similar to inorder/postorder patterns

**Algorithm**
```java
preorderTraversal(TreeNode root):
    result = new ArrayList<>()
    stack = new Stack<>()
    current = root
    
    while current != null or !stack.isEmpty():
        if current != null:
            result.add(current.val)  // Visit when first encountered
            stack.push(current)
            current = current.left   // Go left
        else:
            current = stack.pop()
            current = current.right  // Go right
    
    return result
```

**Complete Code Implementation (Iterative - Current Pointer)**
```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                // Visit node when first encountered (preorder)
                result.add(current.val);
                
                // Push to stack to remember right subtree
                stack.push(current);
                
                // Go left
                current = current.left;
            } else {
                // Done with left subtree, go right
                current = stack.pop();
                current = current.right;
            }
        }
        
        return result;
    }
}
```

**Complexity Analysis**
- **Recursive**: O(n) time, O(h) space (call stack)
- **Iterative (Both)**: O(n) time, O(h) space (explicit stack)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Difficulty | Clean Code | Recommended |
|----------|------|-------|------------|------------|-------------|
| **Recursive** | **O(n)** | **O(h)** | **Easy** | **Yes** | **Yes (default)** |
| **Iterative (Right-First)** | **O(n)** | **O(h)** | **Easy** | **Yes** | **Yes (follow-up)** |
| Iterative (Current) | O(n) | O(h) | Medium | Medium | Alternative |

**Winner**: **Recursive** for simplicity, **Iterative Right-First** for follow-up

### Why Recursive is Natural

```
Tree is recursive structure:
  Node {
    value
    left: subtree
    right: subtree
  }

Preorder traversal mirrors structure:
  process(node)
  process(left subtree)
  process(right subtree)

Perfect match! ✓
Natural and clean code ✓
```

### Why Iterative Right-First Push is Elegant for Preorder

```
Preorder: Root → Left → Right

Want to visit root immediately
Then visit left before right

Stack is LIFO:
  Push right first → processed later
  Push left second → processed next (before right)

When pop:
  Always get next node in preorder ✓

Simple and intuitive! ✓
```

### Why Preorder Differs from Inorder Iteration

```
Inorder: Left → Root → Right
  Go left completely (push all)
  Pop and visit
  Go right

Preorder: Root → Left → Right
  Visit when encountered (not when popped)
  Or use right-first push pattern
  
Different visiting points! ✓
```

### Why Preorder Useful

```
Use cases:

1. Tree Serialization:
   Serialize: preorder gives structure
   Deserialize: can reconstruct

2. Copy Tree:
   Create node first (root)
   Then copy left, right
   Natural preorder!

3. Prefix Expression:
   Operator comes before operands
   Matches preorder!

4. Directory Listing:
   Show folder before contents
   Preorder structure!
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

// Result: [1, 2, 3]
// Root first, then traverse left
```

### 4. **Right Skewed Tree**
```java
1
 \
  2
   \
    3

// Result: [1, 2, 3]
// Same as left-skewed for preorder
```

### 5. **Complete Binary Tree**
```java
       1
      / \
     2   3
    / \ / \
   4  5 6  7

// Result: [1, 2, 4, 5, 3, 6, 7]
// Root first, then left subtree, then right
```

### 6. **Binary Search Tree**
```java
     4
    / \
   2   6
  / \ / \
 1  3 5  7

// Result: [4, 2, 1, 3, 6, 5, 7]
// NOT sorted (unlike inorder)
```

### 7. **Tree with Null Children**
```java
    1
   /
  2
   \
    3

// Result: [1, 2, 3]
// Handle null pointers correctly
```

### 8. **Two Nodes**
```java
  1
 /
2

// Result: [1, 2]

1
 \
  2

// Result: [1, 2]
// Same result (root first)
```

### 9. **Negative Values**
```java
     0
    / \
  -5   5

// Result: [0, -5, 5]
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

### ❌ **MISTAKE 1: Wrong Traversal Order (Inorder Instead)**
```java
// WRONG - this is INORDER, not preorder
private void preorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    preorderHelper(node.left, result);  // ❌ Left first (inorder)
    result.add(node.val);
    preorderHelper(node.right, result);
}
```

**Why wrong**: Processes left before root!

**Issue:**
```
Preorder: Root → Left → Right
Inorder:  Left → Root → Right

Wrong order! ❌
```

**Fix**: Root → Left → Right
```java
private void preorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    result.add(node.val);               // Root first ✓
    preorderHelper(node.left, result);  // Left ✓
    preorderHelper(node.right, result); // Right ✓
}
```

### ❌ **MISTAKE 2: Iterative - Pushing Left Before Right**
```java
// WRONG - wrong push order
while (!stack.isEmpty()) {
    TreeNode node = stack.pop();
    result.add(node.val);
    
    if (node.left != null) {
        stack.push(node.left);   // ❌ Left first
    }
    if (node.right != null) {
        stack.push(node.right);  // ❌ Right second
    }
}
```

**Why wrong**: Right will be processed before left!

**Dry run failure:**
```
Tree: 1 → (2, 3)

Pop 1, push 2, then 3
Stack: [2, 3] (3 on top)

Pop 3 next ❌ (should be 2)

Wrong order!
```

**Fix**: Push right first
```java
// Push right first (LIFO gives correct order)
if (node.right != null) {
    stack.push(node.right);  ✓
}
if (node.left != null) {
    stack.push(node.left);   ✓
}
```

### ❌ **MISTAKE 3: Not Handling Null Root in Iterative**
```java
// WRONG - pushes null to stack
Stack<TreeNode> stack = new Stack<>();
stack.push(root);  // ❌ What if root is null?

while (!stack.isEmpty()) {
    TreeNode node = stack.pop();  // NullPointerException if root was null
    result.add(node.val);  // ❌
}
```

**Why wrong**: Null root causes issues!

**Fix**: Check null before pushing
```java
if (root == null) {  ✓
    return result;
}

Stack<TreeNode> stack = new Stack<>();
stack.push(root);  // Safe now
```

### ❌ **MISTAKE 4: Iterative - Visiting When Popping (Wrong for This Pattern)**
```java
// WRONG - confusing with other patterns
while (!stack.isEmpty()) {
    TreeNode node = stack.pop();
    // Should visit here for right-first pattern ✓
    
    // But if using current pointer pattern:
    // Visit when encountered, not when popped ❌
}
```

**Why wrong**: Different patterns have different visiting points!

**Clarification:**
```
Right-First Pattern:
  Visit when popped ✓

Current Pointer Pattern:
  Visit when first encountered ✓

Don't mix patterns!
```

### ❌ **MISTAKE 5: Not Returning Result**
```java
// WRONG - void return type
public void preorderTraversal(TreeNode root) {  // ❌
    List<Integer> result = new ArrayList<>();
    // ... traversal ...
    // Missing return!
}
```

**Why wrong**: Need to return the result list!

**Fix**: Return List<Integer>
```java
public List<Integer> preorderTraversal(TreeNode root) {  ✓
    List<Integer> result = new ArrayList<>();
    // ... traversal ...
    return result;  ✓
}
```

### ❌ **MISTAKE 6: Confusing Preorder with Level Order**
```java
// WRONG - this is LEVEL ORDER (BFS), not preorder (DFS)
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
Level Order (BFS): Process by levels
  Result: [1, 2, 3, 4, 5, 6, 7]

Preorder (DFS): Root → Left subtree → Right subtree
  Result: [1, 2, 4, 5, 3, 6, 7]

Completely different! ❌
```

**Fix**: Use stack (DFS), not queue (BFS)
```java
Stack<TreeNode> stack = new Stack<>();  ✓
```

### ❌ **MISTAKE 7: Iterative Current Pointer - Wrong Visit Point**
```java
// WRONG - visiting when popped (should visit when encountered)
while (current != null || !stack.isEmpty()) {
    if (current != null) {
        stack.push(current);
        current = current.left;
    } else {
        current = stack.pop();
        result.add(current.val);  // ❌ Too late for preorder!
        current = current.right;
    }
}
```

**Why wrong**: In preorder with current pointer, visit when encountered!

**Fix**: Visit before going left
```java
while (current != null || !stack.isEmpty()) {
    if (current != null) {
        result.add(current.val);  ✓ Visit when encountered
        stack.push(current);
        current = current.left;
    } else {
        current = stack.pop();
        current = current.right;
    }
}
```

### ❌ **MISTAKE 8: Not Initializing Result List**
```java
// WRONG - result not initialized
private List<Integer> result;  // ❌ null

public List<Integer> preorderTraversal(TreeNode root) {
    preorderHelper(root, result);  // NullPointerException!
    return result;
}
```

**Why wrong**: Result list is null!

**Fix**: Initialize result list
```java
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();  ✓
    preorderHelper(root, result);
    return result;
}
```

### ❌ **MISTAKE 9: Postorder Instead of Preorder**
```java
// WRONG - this is POSTORDER, not preorder
private void preorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    preorderHelper(node.left, result);   // ❌ Left first
    preorderHelper(node.right, result);  // ❌ Right second
    result.add(node.val);                // ❌ Root last (postorder)
}
```

**Why wrong**: Root processed last!

**Issue:**
```
Postorder: Left → Right → Root
Preorder:  Root → Left → Right

Opposite! ❌
```

**Fix**: Root first
```java
result.add(node.val);               // Root first ✓
preorderHelper(node.left, result);  // Left
preorderHelper(node.right, result); // Right
```

### ❌ **MISTAKE 10: Stack Overflow with No Base Case**
```java
// WRONG - no base case
private void preorderHelper(TreeNode node, List<Integer> result) {
    // Missing: if (node == null) return; ❌
    
    result.add(node.val);
    preorderHelper(node.left, result);   // Stack overflow if null!
    preorderHelper(node.right, result);
}
```

**Why wrong**: Infinite recursion on null!

**Fix**: Add base case
```java
private void preorderHelper(TreeNode node, List<Integer> result) {
    if (node == null) {  ✓
        return;
    }
    
    result.add(node.val);
    preorderHelper(node.left, result);
    preorderHelper(node.right, result);
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
```

**Detailed Analysis**:
```
For each node:
  Recursive: visit once (process, left, right)
  Iterative: push once, pop once
  
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
  - Stores nodes along path from root
  - Maximum size = height
  
  Best: O(log n)
  Worst: O(n)
```

### Optimal Complexity

```
Time: O(n)
  - Must visit all nodes: Ω(n)
  - All approaches: O(n)
  - Optimal! ✓

Space: O(h)
  - Need to track path: Ω(h)
  - All approaches: O(h)
  - Optimal for DFS! ✓

Choose based on code clarity!
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
Call preorder(1):
  Add 1 to result          // Root of 1
  Call preorder(2):        // Left of 1
    Add 2 to result        // Root of 2
    Call preorder(4):      // Left of 2
      Add 4 to result      // Root of 4
      Call preorder(null)  // Left of 4 → return
      Call preorder(null)  // Right of 4 → return
    Call preorder(5):      // Right of 2
      Add 5 to result      // Root of 5
      Call preorder(null)  // Left of 5 → return
      Call preorder(null)  // Right of 5 → return
  Call preorder(3):        // Right of 1
    Add 3 to result        // Root of 3
    Call preorder(null)    // Left of 3 → return
    Call preorder(null)    // Right of 3 → return

Result: [1, 2, 4, 5, 3] ✓
```

---

### Complete Example Walkthrough (Iterative - Right-First)

**Input:** `root = [1,2,3,4,5]`

**Step-by-Step:**

```
Initial:
  stack = [1], result = []

Iteration 1: Process 1
  Pop 1, add to result: [1]
  Push 3 (right), Push 2 (left)
  stack = [3, 2]

Iteration 2: Process 2
  Pop 2, add to result: [1, 2]
  Push 5 (right), Push 4 (left)
  stack = [3, 5, 4]

Iteration 3: Process 4
  Pop 4, add to result: [1, 2, 4]
  4 has no children
  stack = [3, 5]

Iteration 4: Process 5
  Pop 5, add to result: [1, 2, 4, 5]
  5 has no children
  stack = [3]

Iteration 5: Process 3
  Pop 3, add to result: [1, 2, 4, 5, 3]
  3 has no children
  stack = []

Done: stack empty

Result: [1, 2, 4, 5, 3] ✓
```

---

### Visual: Stack State at Each Step

```
Tree:       1
           / \
          2   3
         / \
        4   5

Stack visualization (Right-First Push):

Initial:
  [1] ← top
  Result: []

After pop 1, push 3, 2:
  [3]
  [2] ← top
  Result: [1]

After pop 2, push 5, 4:
  [3]
  [5]
  [4] ← top
  Result: [1, 2]

After pop 4:
  [3]
  [5] ← top
  Result: [1, 2, 4]

After pop 5:
  [3] ← top
  Result: [1, 2, 4, 5]

After pop 3:
  [] (empty)
  Result: [1, 2, 4, 5, 3] ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Difficulty | Clean Code | Recommended |
|----------|------|-------|------------|------------|-------------|
| **Recursive** | **O(n)** | **O(h)** | **Easy** | **Yes** | **Yes (default)** |
| **Iterative (Right-First)** | **O(n)** | **O(h)** | **Easy** | **Yes** | **Yes (follow-up)** |
| Iterative (Current) | O(n) | O(h) | Medium | Medium | Alternative |

**When to Use Each:**
- **Interview default**: Recursive (clean, simple)
- **Follow-up asked**: Iterative Right-First (intuitive for preorder)
- **Pattern consistency**: Iterative Current (similar to inorder/postorder)

---

## Key Takeaways

1. **Preorder**: Root → Left → Right traversal order
2. **Recursive**: Natural, mirrors tree structure
3. **Iterative Right-First**: Simple—push right, then left
4. **Visit root first**: Core of preorder traversal
5. **LIFO property**: Stack gives correct order with right-first push
6. **Stack depth**: O(height) = O(log n) to O(n)
7. **Visit each node once**: O(n) time
8. **Three DFS traversals**: Preorder, Inorder, Postorder
9. **Use cases**: Serialization, tree copying, prefix expressions
10. **Handle null**: Both null root and null children

---

## Interview Tips

**What to say in an interview:**

> "Preorder traversal processes nodes in Root-Left-Right order. The recursive solution is straightforward—we visit the current node first, then recursively traverse the left subtree, then the right subtree. This naturally matches the tree's structure.
>
> For the follow-up iterative solution, I'll use an explicit stack with a simple pattern. Starting with the root on the stack, I repeatedly pop a node, visit it, then push its right child first (if exists), followed by the left child. Since the stack is LIFO, pushing right before left ensures the left child is processed first when we pop next, maintaining the correct preorder.
>
> Both solutions visit each node exactly once, giving O(n) time complexity. The space complexity is O(h) where h is the tree height—best case O(log n) for balanced trees, worst case O(n) for skewed trees.
>
> Preorder traversal is particularly useful for tree serialization and creating deep copies, since we process the root before its children, which lets us reconstruct or copy the structure naturally."

**Key points to mention:**
1. **Preorder order**: Root → Left → Right
2. **Recursive**: Natural, process root first
3. **Iterative**: Push right, then left (LIFO gives left first)
4. **Simple pattern**: Pop, visit, push children
5. **Time**: O(n) visit each node once
6. **Space**: O(h) for stack/recursion
7. **LIFO**: Why right pushed before left
8. **Three DFS types**: Preorder, Inorder, Postorder
9. **Use cases**: Serialization, copying, prefix expressions
10. **Handle null**: Check before pushing/recursing

**Common Follow-ups:**
- "Can you do it iteratively?" → Yes, with stack (right-first push)
- "Why push right before left?" → Stack is LIFO, want left processed first
- "What's the difference from inorder?" → Order: preorder is Root-L-R, inorder is L-Root-R
- "When is preorder useful?" → Tree serialization, copying, prefix expressions
- "Can you do O(1) space?" → No practical way for preorder (unlike Morris for inorder)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Binary Tree Preorder Traversal** | Easy | **DFS** | **This problem** |
| Binary Tree Inorder Traversal | Easy | DFS | Left → Root → Right |
| Binary Tree Postorder Traversal | Easy | DFS | Left → Right → Root |
| Binary Tree Level Order Traversal | Medium | BFS | Level by level |
| Construct Tree from Preorder and Inorder | Medium | Tree Construction | Use preorder + inorder |
| Flatten Binary Tree to Linked List | Medium | Preorder | Flatten using preorder |
| Serialize and Deserialize Binary Tree | Hard | Preorder | Use preorder for serialization |

**Pattern Progression**:
1. **Preorder Traversal** (this) — Learn DFS pattern
2. **Inorder/Postorder** — Different DFS orders
3. **Tree Construction** — Apply traversal knowledge
4. **Serialization** — Use preorder for structure

---

## Final Pattern Label

✅ **Depth-First Search (DFS) - Preorder Traversal**

**Remember:** This is **preorder traversal** with order **Root → Left → Right**. **Recursive solution** (simplest): base case `if (node == null) return`, recursive case: add current value first, process left subtree, process right subtree. **Iterative solution** (follow-up): use explicit stack, push root, loop while stack not empty: pop node, add value, push right child first (if exists), then push left child (if exists). **Why right before left**: Stack is LIFO (Last In First Out), pushing right first means left is on top and popped next, maintaining correct preorder. **Time complexity**: O(n) visit each node once. **Space complexity**: O(h) where h = height (call stack or explicit stack), best O(log n) balanced, worst O(n) skewed. **Use cases**: tree serialization/deserialization, creating deep copies, prefix expression evaluation. **Common mistakes**: wrong order (inorder/postorder instead), pushing left before right in iterative (gives wrong order), visiting at wrong point (when popped vs encountered), confusing with BFS level order. **Three DFS orders**: Preorder (Root-L-R), Inorder (L-Root-R), Postorder (L-R-Root). Pattern: **DFS preorder** for **root-first processing**, especially useful for **tree serialization and structure problems**! ✓
