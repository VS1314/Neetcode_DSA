# Binary Tree Inorder Traversal

## Problem Description

**Difficulty**: Easy

You are given the root of a binary tree, return the **inorder traversal** of its nodes' values.

**Inorder Traversal**: Left → Root → Right (for each subtree)

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

Output: [4,2,5,1,6,3,7]
Explanation: Inorder traversal visits left subtree, then root, then right subtree.
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

Output: [2,4,1,5,3]
```

### Example 3:
```
Input: root = []

Output: []
Explanation: Empty tree returns empty list.
```

## Constraints
- 0 <= number of nodes in the tree <= 100
- -100 <= Node.val <= 100

---

## Pattern Recognition

**Primary Pattern**: **Tree Traversal - Inorder (DFS)**

**Why This Pattern?**
- Tree traversal is a fundamental operation
- Inorder follows: **Left → Root → Right**
- Results in **sorted order** for Binary Search Trees
- Classic DFS (Depth-First Search) variation

**Key Insight**: Inorder traversal can be done both recursively (natural) and iteratively (using a stack to simulate recursion).

**Related Patterns**:
1. **Preorder Traversal** - Root → Left → Right
2. **Postorder Traversal** - Left → Right → Root
3. **Level Order Traversal** - BFS (uses queue instead)
4. **Morris Traversal** - O(1) space traversal

---

## Algorithm & Approach

### Core Insight
Inorder traversal visits nodes in this order:
1. Visit all nodes in the **left subtree**
2. Visit the **current node**
3. Visit all nodes in the **right subtree**

For a BST, this produces values in **sorted ascending order**.

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
4. 4 has no left → Visit 4 → Add 4 to result
5. 4 has no right → Back to 2
6. Visit 2 → Add 2 to result
7. Go right to 5
8. 5 has no left → Visit 5 → Add 5 to result
9. Back to 1
10. Visit 1 → Add 1 to result
11. Go right to 3
12. 3 has no left → Visit 3 → Add 3 to result

Result: [4, 2, 5, 1, 3]
```

### Step-by-Step Algorithm

#### **Approach 1: Recursive (MOST NATURAL)**

**Core Idea**: Recursion naturally mimics the tree structure.

**Algorithm**
```
inorder(node):
    if node is null:
        return
    
    inorder(node.left)      // Process left subtree
    result.add(node.val)     // Visit current node
    inorder(node.right)      // Process right subtree
```

**Code Implementation**
```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }
    
    private void inorder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        inorder(node.left, result);    // Left
        result.add(node.val);           // Root
        inorder(node.right, result);    // Right
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

inorder(1)
├─ inorder(2)
│  ├─ inorder(4)
│  │  ├─ inorder(null)      → return
│  │  ├─ add 4              → result = [4]
│  │  └─ inorder(null)      → return
│  ├─ add 2                 → result = [4,2]
│  └─ inorder(5)
│     ├─ inorder(null)      → return
│     ├─ add 5              → result = [4,2,5]
│     └─ inorder(null)      → return
├─ add 1                    → result = [4,2,5,1]
└─ inorder(3)
   ├─ inorder(6)
   │  ├─ inorder(null)      → return
   │  ├─ add 6              → result = [4,2,5,1,6]
   │  └─ inorder(null)      → return
   ├─ add 3                 → result = [4,2,5,1,6,3]
   └─ inorder(7)
      ├─ inorder(null)      → return
      ├─ add 7              → result = [4,2,5,1,6,3,7]
      └─ inorder(null)      → return

Final Result: [4,2,5,1,6,3,7]
```

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node exactly once
- **Space Complexity**: O(h) - Recursion stack depth (h = height of tree)
  - Best case (balanced tree): O(log n)
  - Worst case (skewed tree): O(n)

---

#### **Approach 2: Iterative Using Stack (OPTIMAL SPACE CONTROL)**

**Core Idea**: Use an explicit stack to simulate the recursive call stack.

**Why Iterative?**
- Avoids recursion overhead
- More control over stack usage
- Better for very deep trees (prevents stack overflow)
- Follow-up challenge in the problem

**Algorithm**
```
1. Initialize empty stack and result list
2. Start with current = root
3. While current is not null OR stack is not empty:
   a. Push all left nodes onto stack
   b. Pop from stack (this is the node to visit)
   c. Add popped node's value to result
   d. Move to right child
4. Return result
```

**Code Implementation**
```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        
        while (current != null || !stack.isEmpty()) {
            // Go to the leftmost node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // Current is null, so pop from stack
            current = stack.pop();
            result.add(current.val);  // Visit node
            
            // Move to right subtree
            current = current.right;
        }
        
        return result;
    }
}
```

**Step-by-Step Dry Run**

Input: root = [1,2,3,4,5]

```
Tree:
        1
       / \
      2   3
     / \
    4   5

Execution Steps:

| Step | current | stack | Action | result |
|------|---------|-------|--------|--------|
| 1 | 1 | [] | Start | [] |
| 2 | 2 | [1] | Push 1, go left | [] |
| 3 | 4 | [1,2] | Push 2, go left | [] |
| 4 | null | [1,2,4] | Push 4, go left | [] |
| 5 | 4 | [1,2] | Pop 4 | [4] |
| 6 | null | [1,2] | 4 has no right | [4] |
| 7 | 2 | [1] | Pop 2 | [4,2] |
| 8 | 5 | [1] | 2.right = 5 | [4,2] |
| 9 | null | [1,5] | Push 5, go left | [4,2] |
| 10 | 5 | [1] | Pop 5 | [4,2,5] |
| 11 | null | [1] | 5 has no right | [4,2,5] |
| 12 | 1 | [] | Pop 1 | [4,2,5,1] |
| 13 | 3 | [] | 1.right = 3 | [4,2,5,1] |
| 14 | null | [3] | Push 3, go left | [4,2,5,1] |
| 15 | 3 | [] | Pop 3 | [4,2,5,1,3] |
| 16 | null | [] | 3 has no right | [4,2,5,1,3] |
| 17 | - | [] | Stack empty, done | [4,2,5,1,3] |

Output: [4,2,5,1,3]
```

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node exactly once
- **Space Complexity**: O(h) - Stack size
  - Best case (balanced tree): O(log n)
  - Worst case (skewed tree): O(n)

---

#### **Approach 3: Morris Traversal (SPACE OPTIMAL)**

**Core Idea**: Use **threaded binary tree** concept - temporarily modify the tree to create links back to parent, avoiding both recursion and stack.

**Why Morris?**
- **O(1) space complexity** (excluding output)
- No recursion, no stack
- Advanced technique, rarely needed in interviews

**Algorithm**
```
1. current = root
2. While current is not null:
   a. If current has no left child:
      - Visit current
      - Move to right child
   b. If current has left child:
      - Find rightmost node in left subtree (predecessor)
      - If predecessor.right is null:
          * Create thread: predecessor.right = current
          * Move to left child
      - If predecessor.right == current:
          * Remove thread: predecessor.right = null
          * Visit current
          * Move to right child
3. Return result
```

**Code Implementation**
```java
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        TreeNode current = root;
        
        while (current != null) {
            if (current.left == null) {
                // No left child, visit current and go right
                result.add(current.val);
                current = current.right;
            } else {
                // Find predecessor (rightmost node in left subtree)
                TreeNode predecessor = current.left;
                while (predecessor.right != null && predecessor.right != current) {
                    predecessor = predecessor.right;
                }
                
                if (predecessor.right == null) {
                    // Create thread
                    predecessor.right = current;
                    current = current.left;
                } else {
                    // Remove thread and visit
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
- **Time Complexity**: O(n) - Each node visited at most 3 times
- **Space Complexity**: O(1) - No stack or recursion (excluding output)

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Recursive | Iterative | Morris |
|-------------|-----------|-----------|--------|
| Correctness | ✓ | ✓ | ✓ |
| Time complexity | O(n) ✓ | O(n) ✓ | O(n) ✓ |
| Space complexity | O(h) | O(h) | O(1) ✅ **Best** |
| Code simplicity | ✅ **Simplest** | Medium | ❌ Complex |
| Interview friendly | ✅ **Yes** | ✅ Yes | ⚠️ Only if asked |
| Easy to debug | ✅ | Medium | ❌ |

**Winner for Interviews**: **Recursive** ✓ (Start here, then show iterative if asked)

### Why Recursive is Preferred?
1. **Natural fit** - Trees are recursive structures
2. **Clean code** - 3 lines in the helper function
3. **Easy to understand** - Mirrors the definition
4. **Less error-prone** - Fewer edge cases to handle

### When to Use Iterative?
1. **Follow-up question** asks for it
2. **Very deep trees** - Risk of stack overflow with recursion
3. **Performance critical** - Slight overhead reduction

### When to Use Morris?
1. **Explicitly asked** for O(1) space
2. **Embedded systems** - Limited memory
3. **To impress** - Shows advanced knowledge

---

## Critical Edge Cases & Gotchas

### 1. **Empty Tree**
```java
Input: root = null
Output: []
Explanation: No nodes to traverse.
```

### 2. **Single Node**
```java
Input: root = [1]
Output: [1]
Explanation: Just the root.
```

### 3. **Only Left Skewed Tree**
```java
Input: root = [3,2,null,1]

Tree:    3
        /
       2
      /
     1

Output: [1,2,3]
Space: O(n) - Stack grows to tree height
```

### 4. **Only Right Skewed Tree**
```java
Input: root = [1,null,2,null,3]

Tree:  1
        \
         2
          \
           3

Output: [1,2,3]
Space: O(n) - Worst case for stack
```

### 5. **Balanced Tree**
```java
Input: root = [4,2,6,1,3,5,7]

Tree:       4
          /   \
         2     6
        / \   / \
       1   3 5   7

Output: [1,2,3,4,5,6,7]
Note: BST gives sorted output!
```

### 6. **Tree with Negative Values**
```java
Input: root = [0,-1,1]

Tree:     0
         / \
       -1   1

Output: [-1,0,1]
Explanation: Values can be negative per constraints.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Wrong Order of Operations**
```java
// WRONG - This is PREORDER, not INORDER!
private void inorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    result.add(node.val);        // ❌ Adding root FIRST
    inorder(node.left, result);
    inorder(node.right, result);
}
```

**Why wrong**: This is **Preorder** (Root → Left → Right), not **Inorder** (Left → Root → Right).

**Fix**: Visit left first, then root, then right
```java
// CORRECT - INORDER
private void inorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    inorder(node.left, result);   // Left first
    result.add(node.val);          // Then root
    inorder(node.right, result);   // Then right
}
```

### ❌ **MISTAKE 2: Forgetting Base Case in Recursion**
```java
// WRONG - No base case!
private void inorder(TreeNode node, List<Integer> result) {
    inorder(node.left, result);   // NullPointerException!
    result.add(node.val);
    inorder(node.right, result);
}
```

**Why wrong**: When node is null, calling node.left causes NullPointerException.

**Fix**: Always check for null first
```java
// CORRECT
private void inorder(TreeNode node, List<Integer> result) {
    if (node == null) return;  // ✓ Base case
    
    inorder(node.left, result);
    result.add(node.val);
    inorder(node.right, result);
}
```

### ❌ **MISTAKE 3: Iterative - Wrong Loop Condition**
```java
// WRONG
while (!stack.isEmpty()) {  // ❌ Misses initial nodes!
    TreeNode node = stack.pop();
    result.add(node.val);
    // ...
}
```

**Why wrong**: If you start with current = root, and only check stack, you'll never push the first nodes!

**Fix**: Check both current AND stack
```java
// CORRECT
while (current != null || !stack.isEmpty()) {
    // ...
}
```

### ❌ **MISTAKE 4: Iterative - Not Going All the Way Left**
```java
// WRONG
while (current != null || !stack.isEmpty()) {
    if (current != null) {  // ❌ Only pushes one left node
        stack.push(current);
        current = current.left;
    }
    current = stack.pop();
    result.add(current.val);
    current = current.right;
}
```

**Why wrong**: The if statement prevents going all the way to the leftmost node.

**Fix**: Use nested while loop
```java
// CORRECT
while (current != null || !stack.isEmpty()) {
    while (current != null) {  // ✓ Goes ALL the way left
        stack.push(current);
        current = current.left;
    }
    current = stack.pop();
    result.add(current.val);
    current = current.right;
}
```

### ❌ **MISTAKE 5: Creating New List Inside Recursive Function**
```java
// WRONG - Creates multiple lists!
private List<Integer> inorder(TreeNode node) {
    List<Integer> result = new ArrayList<>();  // ❌ New list each call!
    if (node == null) return result;
    
    result.addAll(inorder(node.left));
    result.add(node.val);
    result.addAll(inorder(node.right));
    return result;
}
```

**Why wrong**: 
- Creates n lists (one per node)
- Uses O(n²) space due to copying
- Inefficient

**Fix**: Pass the same list through all calls
```java
// CORRECT
private void inorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    inorder(node.left, result);   // ✓ Same list
    result.add(node.val);
    inorder(node.right, result);
}
```

### ❌ **MISTAKE 6: Confusing Inorder with Other Traversals**

| Traversal | Order | Use Case |
|-----------|-------|----------|
| **Inorder** | **Left → Root → Right** | **BST: Get sorted values** ← This problem |
| Preorder | Root → Left → Right | Copy tree structure |
| Postorder | Left → Right → Root | Delete tree, evaluate expression |
| Level-order | Level by level | BFS, shortest path |

**Remember**: 
- **IN**order = **IN** sorted order (for BST)
- **PRE**order = Root comes **PRE**viously (before children)
- **POST**order = Root comes **POST**erior (after children)

---

## Complexity Analysis

### Recursive Approach

**Time Complexity: O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Visit each node | O(n) | Each node visited exactly once |
| Add to list | O(1) | ArrayList add is amortized O(1) |
| Total | O(n) | Linear in number of nodes |

**Space Complexity: O(h)**

| Component | Space | Reason |
|-----------|-------|--------|
| Recursion stack | O(h) | Maximum depth of call stack |
| Result list | O(n) | Stores all node values |
| Total | O(n) | Dominated by result list |

Where h = height of tree:
- Balanced tree: h = O(log n)
- Skewed tree: h = O(n)

### Iterative Approach

**Time Complexity: O(n)**
- Same as recursive - each node visited once

**Space Complexity: O(h)**
- Stack size instead of recursion stack
- Same space usage as recursive

### Morris Traversal

**Time Complexity: O(n)**
- Each node visited at most 3 times
- Still linear overall

**Space Complexity: O(1)**
- No stack, no recursion
- Only constant extra variables
- **Best space complexity** ✅

---

## Visualization

### Complete Example Walkthrough

**Input:** root = [1,2,3,4,5,6,7]

```
Tree Structure:
        1
       / \
      2   3
     / \ / \
    4  5 6  7

Inorder Traversal Order (with levels):

Level 3: Visit 4 (leftmost)
  ↓
Level 2: Visit 2 (parent)
  ↓
Level 3: Visit 5 (right child of 2)
  ↓
Level 1: Visit 1 (root)
  ↓
Level 3: Visit 6 (leftmost in right subtree)
  ↓
Level 2: Visit 3 (parent)
  ↓
Level 3: Visit 7 (rightmost)

Path Visualization:
4 → 2 → 5 → 1 → 6 → 3 → 7

Result: [4, 2, 5, 1, 6, 3, 7]
```

### Comparison of Traversals

```
Tree:       1
           / \
          2   3
         / \
        4   5

Inorder:    [4, 2, 5, 1, 3]  ← Left, Root, Right
Preorder:   [1, 2, 4, 5, 3]  ← Root, Left, Right
Postorder:  [4, 5, 2, 3, 1]  ← Left, Right, Root
Level-order:[1, 2, 3, 4, 5]  ← Level by level
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Complexity | When to Use |
|----------|------|-------|-----------------|-------------|
| **Recursive** | O(n) | O(h) | ✅ **Very Simple** | **Default choice** ✅ |
| **Iterative** | O(n) | O(h) | Medium | Follow-up or deep trees |
| **Morris** | O(n) | O(1) ✅ | ❌ Complex | O(1) space required |

**Recommendation**:
1. Start with **Recursive** in interviews
2. Mention **Iterative** as alternative
3. Only implement **Morris** if explicitly asked

---

## Key Takeaways

1. **Inorder = Left → Root → Right** (memorize this!)
2. **BST property**: Inorder gives sorted values
3. **Recursive is simplest** - 3 lines of code
4. **Iterative uses stack** - simulates recursion
5. **Morris is O(1) space** - but complex
6. **Base case critical** - always check for null
7. **Order matters** - don't confuse with preorder/postorder
8. **Space = O(height)** for recursive/iterative

---

## Interview Tips

**What to say in an interview:**

> "For inorder traversal, I need to visit nodes in Left → Root → Right order. I'll use a recursive approach since it's the most natural and clean. The base case is when the node is null. For each node, I recursively traverse the left subtree, add the current node's value, then traverse the right subtree. This gives O(n) time and O(h) space for the recursion stack."

**Key points to mention:**
1. **Define inorder**: Left → Root → Right
2. **Recursive is natural** - trees are recursive structures
3. **Base case**: null node returns immediately
4. **Complexity**: O(n) time, O(h) space
5. **BST property**: Inorder gives sorted output

**If asked for iterative:**
> "I can also solve this iteratively using a stack. I'll push all left nodes onto the stack, then pop and visit each node, moving to its right child. This simulates the recursion stack explicitly. Same complexity but more control over the stack."

**If asked about space optimization:**
> "For O(1) space, there's Morris traversal which uses threaded binary tree concept. It temporarily modifies the tree structure to create links, avoiding both recursion and stack. However, it's more complex and rarely needed in practice."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Binary Tree Inorder Traversal** | Easy | **Inorder DFS** | **Left → Root → Right** ← This problem |
| Binary Tree Preorder Traversal | Easy | Preorder DFS | Root → Left → Right |
| Binary Tree Postorder Traversal | Easy | Postorder DFS | Left → Right → Root |
| Binary Tree Level Order Traversal | Medium | BFS | Use queue, level by level |
| Validate Binary Search Tree | Medium | Inorder DFS | Check if inorder is sorted |
| Kth Smallest Element in BST | Medium | Inorder DFS | Stop after k elements |
| Binary Search Tree Iterator | Medium | Inorder DFS | Lazy evaluation with stack |
| Recover Binary Search Tree | Medium | Inorder DFS | Find swapped nodes |
| Flatten Binary Tree to Linked List | Medium | Preorder/Morris | Tree modification |

**Pattern Progression**:
1. **Basic Traversals** (this problem) - Foundation
2. **BST Validation** - Apply inorder property
3. **BST Operations** - Use inorder for sorted access
4. **Tree Modifications** - Advanced Morris-like techniques

---

## Additional Notes

### Why Inorder is Special for BST?

**Binary Search Tree Property:**
```
For every node:
- All left subtree values < node.val
- All right subtree values > node.val
```

**Inorder Traversal on BST:**
```
Tree (BST):      4
               /   \
              2     6
             / \   / \
            1   3 5   7

Inorder: [1, 2, 3, 4, 5, 6, 7]  ← SORTED! ✓
```

This property is used in:
- **Validating BST**: Check if inorder is strictly increasing
- **Finding kth element**: Stop inorder after k steps
- **Range queries**: Prune traversal based on value ranges

### Time Complexity Proof

**Why is Morris Traversal O(n)?**

Even though we revisit some nodes, each edge is traversed at most twice:
```
1st pass: Create thread (go down)
2nd pass: Remove thread (come back up)
Total: 2 × (n-1) edges = O(n)
```

### Real-World Applications

1. **Database Indexing**: B-tree inorder traversal
2. **Expression Trees**: Inorder gives infix notation
3. **Compiler Design**: AST traversal
4. **File Systems**: Directory traversal
5. **Serialization**: Tree to sorted array conversion

---

## Final Pattern Label

✅ **Tree Traversal – Inorder DFS (Left → Root → Right)**

**Remember:** Inorder on BST = Sorted order! Start with recursion, mention iterative as follow-up.

