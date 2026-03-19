# Binary Tree Preorder Traversal

## Problem Description

**Difficulty**: Easy

You are given the root of a binary tree, return the preorder traversal of its nodes' values.

**Follow-up**: Recursive solution is trivial, could you do it iteratively?

## Examples

### Example 1:
```
Input: root = [1,2,3,4,5,6,7]
Output: [1,2,4,5,3,6,7]

Tree structure:
       1
      / \
     2   3
    / \ / \
   4  5 6  7
```

### Example 2:
```
Input: root = [1,2,3,null,4,5,null]
Output: [1,2,4,3,5]

Tree structure:
       1
      / \
     2   3
      \ /
      4 5
```

### Example 3:
```
Input: root = []
Output: []
```

## Constraints
- 0 <= number of nodes in the tree <= 100
- -100 <= Node.val <= 175

---

## Pattern Recognition

**Primary Pattern**: **Depth-First Search (DFS) with Stack**

**Why This Pattern?**
- Tree traversal requires visiting nodes in specific order
- Preorder follows: Root → Left → Right
- Stack naturally maintains traversal order
- Iterative approach avoids recursion overhead

**Key Insight**: Use stack to simulate recursive call stack. Push right child before left child to ensure left is processed first (LIFO).

**Related Patterns**:
1. **Inorder Traversal** - Left → Root → Right
2. **Postorder Traversal** - Left → Right → Root
3. **Level Order Traversal** - BFS approach using queue

---

## Algorithm & Approach

### Core Insight
Preorder traversal visits nodes in Root → Left → Right order. Using a stack, we can simulate the recursive process iteratively by:
1. Process current node (add to result)
2. Push right child to stack (processed later)
3. Push left child to stack (processed next due to LIFO)

**Why it works:**
```
For tree:     1
             / \
            2   3
           
Visit order: 1 (root) → 2 (left) → 3 (right)
Stack helps maintain this order without recursion!
```

### Step-by-Step Algorithm

#### **Approach 1: Recursive (Trivial)**
```
1. If node is null, return
2. Add current node value to result
3. Recursively traverse left subtree
4. Recursively traverse right subtree
```

**Code Implementation**
```java
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    preorder(root, result);
    return result;
}

private void preorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    result.add(node.val);         // Visit root
    preorder(node.left, result);  // Visit left
    preorder(node.right, result); // Visit right
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node once
- **Space Complexity**: O(h) - Recursion stack depth, h = height of tree

**Why Not Optimal?** Recursion uses implicit stack space. Follow-up asks for iterative solution.

#### **Approach 2: Iterative with Stack (OPTIMAL)**
```
1. Create empty result list and stack
2. Push root to stack
3. While stack is not empty:
   a. Pop node from stack
   b. Add node value to result
   c. Push right child (if exists)
   d. Push left child (if exists)
4. Return result
```

**Example Walkthrough**

Input: root = [1,2,3,4,5,6,7]

| Step | current | stack     | Action                 | result          |
|------|---------|-----------|------------------------|-----------------|
| 1    | 1       | []        | Start, push 1          | []              |
| 2    | 1       | []        | Pop 1, add to result   | [1]             |
| 3    | 1       | [3,2]     | Push right(3), left(2) | [1]             |
| 4    | 2       | [3]       | Pop 2, add to result   | [1,2]           |
| 5    | 2       | [3,5,4]   | Push right(5), left(4) | [1,2]           |
| 6    | 4       | [3,5]     | Pop 4, add to result   | [1,2,4]         |
| 7    | 4       | [3,5]     | No children            | [1,2,4]         |
| 8    | 5       | [3]       | Pop 5, add to result   | [1,2,4,5]       |
| 9    | 5       | [3]       | No children            | [1,2,4,5]       |
| 10   | 3       | []        | Pop 3, add to result   | [1,2,4,5,3]     |
| 11   | 3       | [7,6]     | Push right(7), left(6) | [1,2,4,5,3]     |
| 12   | 6       | [7]       | Pop 6, add to result   | [1,2,4,5,3,6]   |
| 13   | 6       | [7]       | No children            | [1,2,4,5,3,6]   |
| 14   | 7       | []        | Pop 7, add to result   | [1,2,4,5,3,6,7] |
| 15   | -       | []        | Stack empty, done      | [1,2,4,5,3,6,7] |

Output: [1,2,4,5,3,6,7]

**Explanation:**
- Visit root 1 first, push children 3 and 2
- Process left subtree (2,4,5) completely before right subtree (3,6,7)
- Stack ensures LIFO order maintains preorder traversal

**Code Implementation**
```java
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;
    
    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);
    
    while (!stack.isEmpty()) {
        TreeNode current = stack.pop();
        result.add(current.val);
        
        // Push right first, then left (so left is processed first)
        if (current.right != null) {
            stack.push(current.right);
        }
        if (current.left != null) {
            stack.push(current.left);
        }
    }
    
    return result;
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node exactly once
- **Space Complexity**: O(n) - Stack can hold up to n nodes in worst case (skewed tree)

---

## Why This Strategy?

### Greedy Choice
Process nodes immediately upon popping from stack, ensuring Root → Left → Right order.

### Optimality Proof
- Each node is pushed and popped exactly once → O(n) operations
- Stack maintains correct traversal order via LIFO
- No need for recursion or additional data structures

### Alternative Approaches Comparison

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Recursive | O(n) | O(h) | Simple, intuitive | Uses implicit stack |
| **Iterative** | **O(n)** | **O(n)** | **Explicit control, no recursion** ✓ | **Slightly more code** |
| Morris Traversal | O(n) | O(1) | Constant space | Modifies tree structure |

---

## Major Areas Where We Might Go Wrong

### ❌ MISTAKE 1: Pushing Left Before Right
```java
// WRONG - This produces incorrect order!
if (current.left != null) {
    stack.push(current.left);
}
if (current.right != null) {
    stack.push(current.right);
}
```

**Why wrong**: Stack is LIFO. Pushing left first means right is processed before left, violating preorder.

**Fix**: Push right first, then left

```java
// CORRECT
if (current.right != null) {
    stack.push(current.right);
}
if (current.left != null) {
    stack.push(current.left);
}
```

### ❌ MISTAKE 2: Processing Node After Popping Children
```java
// WRONG - This is more like postorder!
stack.push(current);
if (current.left != null) stack.push(current.left);
if (current.right != null) stack.push(current.right);
// Process current later
```

**Why wrong**: Preorder requires processing root BEFORE children.

**Fix**: Add node value to result immediately after popping

### ❌ MISTAKE 3: Forgetting Null Check
```java
// WRONG - NullPointerException if root is null!
Stack<TreeNode> stack = new Stack<>();
stack.push(root);
```

**Why wrong**: Attempting to push null causes issues later.

**Fix**: Check if root is null before starting

```java
// CORRECT
if (root == null) return result;
Stack<TreeNode> stack = new Stack<>();
stack.push(root);
```

### ❌ MISTAKE 4: Modifying Tree During Traversal
```java
// WRONG - Don't do this!
TreeNode current = stack.pop();
current.left = null;  // Destroying tree structure!
```

**Why wrong**: Tree traversal should be read-only operation.

**Fix**: Only read nodes, never modify

### ❌ MISTAKE 5: Using Queue Instead of Stack
```java
// WRONG - This gives level-order traversal, not preorder!
Queue<TreeNode> queue = new LinkedList<>();
```

**Why wrong**: Queue (FIFO) gives breadth-first search, not depth-first.

**Fix**: Use Stack (LIFO) for DFS traversals

---

## Critical Edge Cases

### 1. Empty Tree
```java
Input: root = []
Output: []
Reason: No nodes to traverse
```

### 2. Single Node
```java
Input: root = [1]
Output: [1]
Reason: Only root exists
```

### 3. Only Left Children (Skewed Left)
```java
Input: root = [1,2,null,3,null]
Output: [1,2,3]
Tree:    1
        /
       2
      /
     3
```

### 4. Only Right Children (Skewed Right)
```java
Input: root = [1,null,2,null,3]
Output: [1,2,3]
Tree:  1
        \
         2
          \
           3
```

### 5. Full Binary Tree
```java
Input: root = [1,2,3,4,5,6,7]
Output: [1,2,4,5,3,6,7]
Reason: All levels completely filled
```

---

## Comparison of Approaches

| Approach | Time | Space | Difficulty | Use Case |
|----------|------|-------|------------|----------|
| Recursive | O(n) | O(h) | Easy | Quick solution, interviews |
| **Iterative** | **O(n)** | **O(n)** | **Medium** ✓ | **Follow-up requirement** |
| Morris | O(n) | O(1) | Hard | Space-constrained environments |

---

## Key Takeaways

1. **Preorder Order**: Root → Left → Right
2. **Stack-Based**: Use stack for iterative DFS
3. **Push Order Matters**: Right before left (LIFO ensures left processed first)
4. **Process Immediately**: Add to result when popping, not later
5. **Edge Cases**: Handle null root, single node, skewed trees
6. **Space Tradeoff**: O(n) space for explicit stack vs O(h) for recursion

---

## Interview Tips

**What to say in an interview:**

> "Preorder traversal visits nodes in Root → Left → Right order. The recursive solution is straightforward, but the follow-up asks for an iterative approach. I'll use a stack to simulate the recursion. The key insight is pushing the right child before the left child, so when we pop from the stack (LIFO), we process the left child first. This ensures the correct preorder sequence. The time complexity is O(n) as we visit each node once, and space is O(n) for the stack in the worst case."

**Key points to mention:**
1. **Why stack?** - Simulates recursive call stack for DFS
2. **Why right before left?** - LIFO property ensures left processed first
3. **Process on pop** - Add to result immediately when popping
4. **Complexity** - Time O(n), Space O(n)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| Binary Tree Inorder Traversal | Easy | DFS + Stack | Left → Root → Right order |
| Binary Tree Postorder Traversal | Easy | DFS + Stack | Left → Right → Root order |
| **Binary Tree Preorder Traversal** | **Easy** | **DFS + Stack** | **Root → Left → Right** ✓ This problem |
| Binary Tree Level Order Traversal | Medium | BFS + Queue | Level-by-level traversal |
| N-ary Tree Preorder Traversal | Easy | DFS + Stack | Multiple children per node |

---

## Final Pattern Label

✅ **Depth-First Search (DFS) - Iterative with Stack**

**Remember:** Preorder = Root first, then left, then right. Use stack, push right before left!

