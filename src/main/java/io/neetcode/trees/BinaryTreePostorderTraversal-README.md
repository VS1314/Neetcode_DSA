# Binary Tree Postorder Traversal

## Problem Description

**Difficulty**: Easy

You are given the root of a binary tree, return the **postorder traversal** of its nodes' values.

**Postorder Traversal**: Left → Right → Root (for each subtree)

**Follow-up**: Recursive solution is trivial, could you do it iteratively?

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

Output: [4,5,2,6,7,3,1]
Explanation: Postorder traversal visits left subtree, then right subtree, then root.
```

### Example 2:
```
Input: root = [1,2,3,null,4,5,null]

Tree Structure:
        1
       / \
      2   3
       \ /
       4 5

Output: [4,2,5,3,1]
```

### Example 3:
```
Input: root = []

Output: []
Explanation: Empty tree returns empty list.
```

## Constraints
- 0 <= number of nodes in the tree <= 100
- -100 <= Node.val <= 175

---

## Pattern Recognition

**Primary Pattern**: **Tree Traversal - Postorder (DFS)**

**Why This Pattern?**
- Tree traversal is a fundamental operation
- Postorder follows: **Left → Right → Root**
- Used for **deleting/freeing nodes**, **evaluating expression trees**
- Classic DFS (Depth-First Search) variation

**Key Insight**: Postorder traversal can be done both recursively (natural) and iteratively (using stack or reverse preorder technique).

**Related Patterns**:
1. **Preorder Traversal** - Root → Left → Right
2. **Inorder Traversal** - Left → Root → Right
3. **Level Order Traversal** - BFS (uses queue instead)
4. **Morris Traversal** - O(1) space traversal

---

## Algorithm & Approach

### Core Insight
Postorder traversal visits nodes in this order:
1. Visit all nodes in the **left subtree**
2. Visit all nodes in the **right subtree**
3. Visit the **current node**

This is useful for operations that need to process children before parent (e.g., deleting nodes, calculating tree height).

### Visual Understanding
```
Tree:       1
           / \
          2   3
         / \
        4   5

Execution Flow:
1. Start at 1
2. Go left to 2
3. Go left to 4
4. 4 has no left/right → Visit 4 → Add 4 to result
5. Back to 2, go right to 5
6. 5 has no left/right → Visit 5 → Add 5 to result
7. Back to 2, both children done → Visit 2 → Add 2 to result
8. Back to 1, go right to 3
9. 3 has no left/right → Visit 3 → Add 3 to result
10. Back to 1, both children done → Visit 1 → Add 1 to result

Result: [4, 5, 2, 3, 1]
```

### Step-by-Step Algorithm

#### **Approach 1: Recursive (MOST NATURAL)**

**Core Idea**: Recursion naturally mimics the tree structure.

**Algorithm**
```
postorder(node):
    if node is null:
        return
    
    postorder(node.left)     // Process left subtree
    postorder(node.right)    // Process right subtree
    result.add(node.val)     // Visit current node
```

**Code Implementation**
```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }
    
    private void postorder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        postorder(node.left, result);   // Left
        postorder(node.right, result);  // Right
        result.add(node.val);           // Root
    }
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

Call Stack Visualization:

postorder(1)
├─ postorder(2)
│  ├─ postorder(4)
│  │  ├─ postorder(null)    → return
│  │  ├─ postorder(null)    → return
│  │  └─ add 4              → result = [4]
│  ├─ postorder(5)
│  │  ├─ postorder(null)    → return
│  │  ├─ postorder(null)    → return
│  │  └─ add 5              → result = [4,5]
│  └─ add 2                 → result = [4,5,2]
├─ postorder(3)
│  ├─ postorder(6)
│  │  ├─ postorder(null)    → return
│  │  ├─ postorder(null)    → return
│  │  └─ add 6              → result = [4,5,2,6]
│  ├─ postorder(7)
│  │  ├─ postorder(null)    → return
│  │  ├─ postorder(null)    → return
│  │  └─ add 7              → result = [4,5,2,6,7]
│  └─ add 3                 → result = [4,5,2,6,7,3]
└─ add 1                    → result = [4,5,2,6,7,3,1]

Final Result: [4,5,2,6,7,3,1]
```

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node exactly once
- **Space Complexity**: O(h) - Recursion stack depth (h = height of tree)
  - Best case (balanced tree): O(log n)
  - Worst case (skewed tree): O(n)

---

#### **Approach 2: Iterative Using Two Stacks (CONCEPTUALLY SIMPLE)**

**Core Idea**: Use two stacks to reverse the order. First stack processes in Root → Right → Left order, second stack reverses to Left → Right → Root.

**Why This Works?**
- Postorder is reverse of modified preorder
- Modified preorder: Root → Right → Left
- Reverse it: Left → Right → Root (Postorder!)

**Algorithm**
```
1. Initialize two stacks: stack1 (for traversal), stack2 (for result order)
2. Push root to stack1
3. While stack1 is not empty:
   a. Pop node from stack1
   b. Push node to stack2
   c. Push left child to stack1 (if exists)
   d. Push right child to stack1 (if exists)
4. Pop all elements from stack2 to result
5. Return result
```

**Code Implementation**
```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        
        stack1.push(root);
        
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            
            // Push left first, then right (so right is processed first)
            if (node.left != null) stack1.push(node.left);
            if (node.right != null) stack1.push(node.right);
        }
        
        // Pop all elements from stack2 to get postorder
        while (!stack2.isEmpty()) {
            result.add(stack2.pop().val);
        }
        
        return result;
    }
}
```

**Step-by-Step Dry Run**

Input: root = [1,2,3,4,5]

Tree:
```
        1
       / \
      2   3
     / \
    4   5
```

Execution Steps:

| Step | stack1 | stack2 | Action | Notes |
|------|--------|--------|--------|-------|
| 1 | [1] | [] | Push 1 to stack1 | Start |
| 2 | [] | [1] | Pop 1, push to stack2 | Process root |
| 3 | [2,3] | [1] | Push 1's children (left, right) | Left first |
| 4 | [2] | [1,3] | Pop 3, push to stack2 | Process right child |
| 5 | [2] | [1,3] | 3 has no children | Skip |
| 6 | [] | [1,3,2] | Pop 2, push to stack2 | Process left child |
| 7 | [4,5] | [1,3,2] | Push 2's children (left, right) | Left first |
| 8 | [4] | [1,3,2,5] | Pop 5, push to stack2 | Process right |
| 9 | [] | [1,3,2,5,4] | Pop 4, push to stack2 | Process left |
| 10 | [] | [] | Pop stack2 to result | [4,5,2,3,1] |

Output: [4,5,2,3,1]

**Complexity Analysis**
- **Time Complexity**: O(n) - Each node pushed/popped twice (once per stack)
- **Space Complexity**: O(n) - Two stacks can hold up to n nodes total

---

#### **Approach 3: Iterative Using Single Stack - Reverse PreOrder (OPTIMAL)**

**Core Idea**: Process nodes in reverse postorder (Root → Right → Left) and add to front of result list, which automatically reverses to get postorder.

**Why This Works?**
- Instead of building result then reversing, build in reverse order directly
- Use LinkedList's `addFirst()` to prepend elements
- This is essentially modified preorder with result reversal

**Algorithm**
```
1. Initialize stack and LinkedList result
2. Push root to stack
3. While stack is not empty:
   a. Pop node from stack
   b. Add node value to FRONT of result (addFirst)
   c. Push left child to stack (if exists)
   d. Push right child to stack (if exists)
4. Return result
```

**Code Implementation**
```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.addFirst(node.val);  // Add to front to reverse order
            
            // Push left first, then right (opposite of preorder)
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }
        
        return result;
    }
}
```

**Step-by-Step Dry Run**

Input: root = [1,2,3,4,5,6,7]

| Step | current | stack | Action | result (front→back) |
|------|---------|-------|--------|---------------------|
| 1 | 1 | [] | Pop 1, addFirst(1) | [1] |
| 2 | 1 | [2,3] | Push left(2), right(3) | [1] |
| 3 | 3 | [2] | Pop 3, addFirst(3) | [3,1] |
| 4 | 3 | [2,6,7] | Push left(6), right(7) | [3,1] |
| 5 | 7 | [2,6] | Pop 7, addFirst(7) | [7,3,1] |
| 6 | 6 | [2] | Pop 6, addFirst(6) | [6,7,3,1] |
| 7 | 2 | [] | Pop 2, addFirst(2) | [2,6,7,3,1] |
| 8 | 2 | [4,5] | Push left(4), right(5) | [2,6,7,3,1] |
| 9 | 5 | [4] | Pop 5, addFirst(5) | [5,2,6,7,3,1] |
| 10 | 4 | [] | Pop 4, addFirst(4) | [4,5,2,6,7,3,1] |

Output: [4,5,2,6,7,3,1] ✓

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node once
- **Space Complexity**: O(n) - Stack size

---

#### **Approach 4: Iterative Using Single Stack - Traditional (MOST COMPLEX)**

**Core Idea**: Use a single stack with a "last visited" tracker to ensure we visit nodes in correct postorder sequence. Only process a node after both children are visited.

**Why This Works?**
- Keep track of last visited node
- Only visit current node if:
  - It has no right child, OR
  - Right child was just visited
- This ensures children are processed before parent

**Algorithm**
```
1. Initialize stack, current = root, lastVisited = null
2. While current is not null OR stack is not empty:
   a. Go to leftmost node, pushing all nodes to stack
   b. Peek at top of stack
   c. If right child exists and not visited yet:
      - Move to right child
   d. Else:
      - Visit the node (add to result)
      - Mark as last visited
      - Pop from stack
3. Return result
```

**Code Implementation**
```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        TreeNode lastVisited = null;
        
        while (curr != null || !stack.isEmpty()) {
            // Go to the leftmost node
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            
            // Peek at the top of stack
            TreeNode peekNode = stack.peek();
            
            // If right child exists and hasn't been visited yet
            if (peekNode.right != null && lastVisited != peekNode.right) {
                curr = peekNode.right;
            } else {
                // Visit the node
                result.add(peekNode.val);
                lastVisited = stack.pop();
            }
        }
        
        return result;
    }
}
```

**Step-by-Step Dry Run**

Input: root = [1,2,3,4,5]

Tree:
```
        1
       / \
      2   3
     / \
    4   5
```

| Step | curr | stack | lastVisited | Action | result |
|------|------|-------|-------------|--------|--------|
| 1 | 1 | [] | null | Start | [] |
| 2 | null | [1,2,4] | null | Push 1,2,4 (go left) | [] |
| 3 | null | [1,2,4] | null | Peek 4, no right | [] |
| 4 | null | [1,2] | 4 | Visit 4, pop | [4] |
| 5 | null | [1,2] | 4 | Peek 2, has right(5) | [] |
| 6 | 5 | [1,2] | 4 | Move to right(5) | [4] |
| 7 | null | [1,2,5] | 4 | Push 5, go left | [4] |
| 8 | null | [1,2,5] | 4 | Peek 5, no right | [4] |
| 9 | null | [1,2] | 5 | Visit 5, pop | [4,5] |
| 10 | null | [1,2] | 5 | Peek 2, right=5 visited | [4,5] |
| 11 | null | [1] | 2 | Visit 2, pop | [4,5,2] |
| 12 | null | [1] | 2 | Peek 1, has right(3) | [4,5,2] |
| 13 | 3 | [1] | 2 | Move to right(3) | [4,5,2] |
| 14 | null | [1,3] | 2 | Push 3, go left | [4,5,2] |
| 15 | null | [1,3] | 2 | Peek 3, no right | [4,5,2] |
| 16 | null | [1] | 3 | Visit 3, pop | [4,5,2,3] |
| 17 | null | [1] | 3 | Peek 1, right=3 visited | [4,5,2,3] |
| 18 | null | [] | 1 | Visit 1, pop | [4,5,2,3,1] |

Output: [4,5,2,3,1] ✓

**Complexity Analysis**
- **Time Complexity**: O(n) - Each node visited once
- **Space Complexity**: O(h) - Stack depth equals tree height

---

## Why This Strategy?

### Greedy Choice
Process children completely before processing parent node, ensuring Left → Right → Root order.

### Optimality Proof
- Each node is processed exactly once → O(n) operations
- Stack/recursion maintains correct traversal order
- No need for multiple passes or additional structures (except for two-stack approach)

### Alternative Approaches Comparison

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Recursive | O(n) | O(h) | Simple, intuitive, clean code | Uses implicit stack, potential stack overflow |
| Two Stacks | O(n) | O(n) | Easy to understand, clear logic | Uses extra space for second stack |
| **Single Stack (Reverse)** | **O(n)** | **O(n)** | **Elegant, efficient, easy to implement** ✓ | **Requires LinkedList for addFirst** |
| Single Stack (Traditional) | O(n) | O(h) | Most space efficient iterative | Complex logic, harder to understand |
| Morris Traversal | O(n) | O(1) | Constant space | Modifies tree, very complex |

---

## Major Areas Where We Might Go Wrong

### ❌ MISTAKE 1: Confusing with Preorder Traversal
```java
// WRONG - This is preorder (Root → Left → Right), not postorder!
result.add(node.val);
postorder(node.left, result);
postorder(node.right, result);
```

**Why wrong**: Processing root before children violates postorder sequence.

**Fix**: Process children first, then root

```java
// CORRECT - Postorder (Left → Right → Root)
postorder(node.left, result);
postorder(node.right, result);
result.add(node.val);
```

### ❌ MISTAKE 2: Wrong Push Order in Two-Stack Approach
```java
// WRONG - This gives incorrect order!
if (node.right != null) stack1.push(node.right);
if (node.left != null) stack1.push(node.left);
```

**Why wrong**: We want stack2 to have nodes in reverse postorder. Pushing right before left means left is processed first, which breaks the reverse order.

**Fix**: Push left first, then right

```java
// CORRECT
if (node.left != null) stack1.push(node.left);
if (node.right != null) stack1.push(node.right);
```

### ❌ MISTAKE 3: Using ArrayList with addFirst()
```java
// WRONG - ArrayList doesn't have efficient addFirst()!
List<Integer> result = new ArrayList<>();
result.add(0, node.val);  // O(n) operation each time!
```

**Why wrong**: ArrayList's add(0, element) is O(n) because it shifts all elements.

**Fix**: Use LinkedList for O(1) addFirst()

```java
// CORRECT
LinkedList<Integer> result = new LinkedList<>();
result.addFirst(node.val);  // O(1) operation
```

### ❌ MISTAKE 4: Forgetting lastVisited in Traditional Approach
```java
// WRONG - Without lastVisited, we revisit nodes infinitely!
while (curr != null || !stack.isEmpty()) {
    TreeNode peek = stack.peek();
    if (peek.right != null) {
        curr = peek.right;  // Will keep going right forever!
    }
}
```

**Why wrong**: Without tracking visited nodes, we keep processing right child repeatedly.

**Fix**: Track last visited node

```java
// CORRECT
TreeNode lastVisited = null;
if (peek.right != null && lastVisited != peek.right) {
    curr = peek.right;
}
```

### ❌ MISTAKE 5: Not Handling Null Root
```java
// WRONG - NullPointerException if root is null!
Stack<TreeNode> stack = new Stack<>();
stack.push(root);  // Pushing null causes issues
```

**Why wrong**: Null root should return empty list immediately.

**Fix**: Check for null before processing

```java
// CORRECT
if (root == null) return result;
Stack<TreeNode> stack = new Stack<>();
stack.push(root);
```

### ❌ MISTAKE 6: Reversing in Wrong Direction
```java
// WRONG - Using addLast when need addFirst!
LinkedList<Integer> result = new LinkedList<>();
result.addLast(node.val);  // Same as normal add(), no reversal!
```

**Why wrong**: addLast() is same as regular add(), doesn't reverse order.

**Fix**: Use addFirst() to prepend

```java
// CORRECT
result.addFirst(node.val);
```

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
Reason: Only root exists, process it
```

### 3. Only Left Children (Skewed Left)
```java
Input: root = [1,2,null,3,null]
Output: [3,2,1]
Tree:    1
        /
       2
      /
     3

Explanation: Process 3 (leaf), then 2, then 1
```

### 4. Only Right Children (Skewed Right)
```java
Input: root = [1,null,2,null,3]
Output: [3,2,1]
Tree:  1
        \
         2
          \
           3

Explanation: Process 3 (leaf), then 2, then 1
```

### 5. Full Binary Tree
```java
Input: root = [1,2,3,4,5,6,7]
Output: [4,5,2,6,7,3,1]
Tree:        1
           /   \
          2     3
         / \   / \
        4   5 6   7

Explanation: All levels completely filled, process leaves first
```

### 6. Complete Binary Tree with Missing Nodes
```java
Input: root = [1,2,3,null,4,5,null]
Output: [4,2,5,3,1]
Tree:      1
          / \
         2   3
          \ /
          4 5

Explanation: Process leaves (4,5), then parents (2,3), then root (1)
```

---

## Comparison of Approaches

| Approach | Time | Space | Difficulty | Use Case |
|----------|------|-------|------------|----------|
| Recursive | O(n) | O(h) | Easy | Quick solution, simple trees |
| Two Stacks | O(n) | O(n) | Medium | Learning/understanding |
| **Single Stack (Reverse)** | **O(n)** | **O(n)** | **Medium** ✓ | **Most practical iterative solution** |
| Single Stack (Traditional) | O(n) | O(h) | Hard | Space-critical applications |
| Morris | O(n) | O(1) | Very Hard | Extreme space constraints |

**Recommended**: Use recursive for simplicity, single stack with reverse for interviews (shows understanding).

---

## Key Takeaways

1. **Postorder Order**: Left → Right → Root
2. **Children Before Parent**: Always process children before processing parent node
3. **Iterative Trick**: Reverse preorder (Root → Right → Left) gives postorder
4. **Stack-Based**: Use stack for iterative DFS approaches
5. **Edge Cases**: Handle null root, single node, skewed trees
6. **Space Tradeoff**: O(h) for recursion vs O(n) for iterative with reversal
7. **Use Case**: Postorder is perfect for tree deletion, calculating height, etc.

---

## Common Use Cases for Postorder

### 1. Deleting/Freeing Tree Nodes
```java
// Must delete children before parent to avoid memory leaks
void deleteTree(TreeNode node) {
    if (node == null) return;
    deleteTree(node.left);   // Delete left subtree
    deleteTree(node.right);  // Delete right subtree
    delete node;             // Delete current node
}
```

### 2. Calculating Tree Height
```java
// Need heights of children to calculate parent's height
int height(TreeNode node) {
    if (node == null) return 0;
    int leftHeight = height(node.left);
    int rightHeight = height(node.right);
    return Math.max(leftHeight, rightHeight) + 1;  // Process after children
}
```

### 3. Evaluating Expression Trees
```java
// Evaluate operators after operands
int evaluate(TreeNode node) {
    if (node == null) return 0;
    if (isLeaf(node)) return node.val;
    
    int left = evaluate(node.left);    // Get left operand
    int right = evaluate(node.right);  // Get right operand
    return apply(node.val, left, right);  // Apply operator
}
```

---

## Interview Tips

**What to say in an interview:**

> "Postorder traversal visits nodes in Left → Right → Root order, meaning we process all children before the parent. The recursive solution is straightforward, but the follow-up asks for an iterative approach. I'll use a single stack with the reverse preorder technique: instead of Root → Left → Right (preorder), we do Root → Right → Left and add elements to the front of the result, which automatically gives us Left → Right → Root (postorder). Using LinkedList's addFirst() keeps this efficient. The time complexity is O(n) as we visit each node once, and space is O(n) for the stack in the worst case."

**Alternative approach to mention:**
> "Another iterative approach uses two stacks: the first processes nodes in Root → Right → Left order and pushes to a second stack, then we pop from the second stack to get the reversed Left → Right → Root order. This is conceptually clearer but uses more space."

**Key points to mention:**
1. **Why postorder?** - Children before parent (deletion, height calculation)
2. **Reverse preorder trick** - Root→Right→Left reversed = Left→Right→Root
3. **Why LinkedList?** - addFirst() is O(1), ArrayList would be O(n)
4. **Complexity** - Time O(n), Space O(n)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| Binary Tree Preorder Traversal | Easy | DFS + Stack | Root → Left → Right order |
| Binary Tree Inorder Traversal | Easy | DFS + Stack | Left → Root → Right order |
| **Binary Tree Postorder Traversal** | **Easy** | **DFS + Stack** | **Left → Right → Root** ✓ This problem |
| Binary Tree Level Order Traversal | Medium | BFS + Queue | Level-by-level traversal |
| N-ary Tree Postorder Traversal | Easy | DFS + Stack | Multiple children per node |
| Binary Tree Maximum Path Sum | Hard | Postorder DFS | Calculate max path using postorder |
| Lowest Common Ancestor | Medium | Postorder DFS | Find LCA using bottom-up approach |

---

## Final Pattern Label

✅ **Depth-First Search (DFS) - Postorder Traversal**

**Remember:** Postorder = Children first, then parent. Left → Right → Root. For iterative: reverse the preorder!

**Mnemonic:** POST-order = POST-pone the root until AFTER children! 📮

