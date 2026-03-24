# Invert Binary Tree

## Problem Description

**Difficulty**: Easy

You are given the root of a binary tree `root`. **Invert the binary tree** and return its root.

**Inverting a Tree**: Swap the left and right children of every node in the tree.

## Examples

### Example 1:
```
Input: root = [1,2,3,4,5,6,7]

Original Tree:          Inverted Tree:
        1                      1
       / \                    / \
      2   3                  3   2
     / \ / \                / \ / \
    4  5 6  7              7  6 5  4

Output: [1,3,2,7,6,5,4]
Explanation: Every node's left and right children are swapped.
```

### Example 2:
```
Input: root = [3,2,1]

Original Tree:          Inverted Tree:
        3                      3
       / \                    / \
      2   1                  1   2

Output: [3,1,2]
```

### Example 3:
```
Input: root = []

Output: []
Explanation: Empty tree returns null.
```

## Constraints
- 0 <= The number of nodes in the tree <= 100
- -100 <= Node.val <= 100

---

## Pattern Recognition

**Primary Pattern**: **Tree Traversal + Swap (DFS or BFS)**

**Why This Pattern?**
- Need to visit **every node** in the tree
- At each node, perform **swap operation** (left ↔ right)
- Can use any traversal order (preorder, postorder, level-order)
- Simple recursive or iterative approach

**Key Insight**: Inverting a tree means swapping left and right children at every node. Any tree traversal (DFS or BFS) works as long as we swap children at each node.

**Related Patterns**:
1. **Tree Traversal** - Visiting all nodes
2. **Tree Modification** - Changing tree structure
3. **Symmetric Tree** - Checking if tree equals its mirror
4. **Mirror Tree** - Same as invert

---

## Algorithm & Approach

### Core Insight
To invert a tree:
1. Swap left and right children of current node
2. Recursively invert left subtree
3. Recursively invert right subtree

**Why it works:**
- If we invert every subtree, the entire tree becomes inverted
- Base case: null node returns null (nothing to invert)
- Works bottom-up or top-down (order doesn't matter for swap)

### Visual Understanding
```
Original:       1
               / \
              2   3
             /     \
            4       5

Step 1: Swap 1's children
                1
               / \
              3   2
             /     \
            5       4

Step 2: Swap 3's children (5 and null)
                1
               / \
              3   2
               \   \
                5   4

Step 3: Swap 2's children (null and 4)
                1
               / \
              3   2
               \ /
                5 4

Result: Tree is inverted!
```

### Step-by-Step Algorithm

#### **Approach 1: Recursive DFS (MOST INTUITIVE)**

**Core Idea**: Recursively swap left and right children at each node.

**Algorithm**
```
invertTree(root):
    if root is null:
        return null
    
    // Swap left and right children
    temp = root.left
    root.left = root.right
    root.right = temp
    
    // Recursively invert subtrees
    invertTree(root.left)
    invertTree(root.right)
    
    return root
```

**Code Implementation**
```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        // Base case: empty tree
        if (root == null) {
            return null;
        }
        
        // Swap left and right children
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        
        // Recursively invert left and right subtrees
        invertTree(root.left);
        invertTree(root.right);
        
        return root;
    }
}
```

**Alternative - More Concise**
```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        
        // Swap and recurse in one step
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        
        root.left = right;
        root.right = left;
        
        return root;
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

invertTree(1)
├─ Swap 1's children: left=2 ↔ right=3
│  Now: 1's left=3, right=2
├─ invertTree(3)  [was left child]
│  ├─ Swap 3's children: left=6 ↔ right=7
│  │  Now: 3's left=7, right=6
│  ├─ invertTree(7) → null children → return 7
│  └─ invertTree(6) → null children → return 6
│  └─ return 3
├─ invertTree(2)  [was right child]
│  ├─ Swap 2's children: left=4 ↔ right=5
│  │  Now: 2's left=5, right=4
│  ├─ invertTree(5) → null children → return 5
│  └─ invertTree(4) → null children → return 4
│  └─ return 2
└─ return 1

Final Tree:
        1
       / \
      3   2
     / \ / \
    7  6 5  4

Output: [1,3,2,7,6,5,4] ✓
```

**Step-by-Step Trace:**

| Step | Node | Action | Tree State |
|------|------|--------|------------|
| 1 | 1 | Swap(2,3) → left=3, right=2 | 1 has 3 on left, 2 on right |
| 2 | 3 | Swap(6,7) → left=7, right=6 | 3 has 7 on left, 6 on right |
| 3 | 7 | No children, return | Leaf node |
| 4 | 6 | No children, return | Leaf node |
| 5 | 2 | Swap(4,5) → left=5, right=4 | 2 has 5 on left, 4 on right |
| 6 | 5 | No children, return | Leaf node |
| 7 | 4 | No children, return | Leaf node |
| 8 | - | Done | Tree fully inverted |

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node exactly once
- **Space Complexity**: O(h) - Recursion stack depth (h = height of tree)
  - Best case (balanced tree): O(log n)
  - Worst case (skewed tree): O(n)

---

#### **Approach 2: Iterative DFS with Stack (EXPLICIT CONTROL)**

**Core Idea**: Use a stack to simulate recursion. Process nodes iteratively, swapping children at each step.

**Why This Works:**
- Stack maintains nodes to process
- Pop node, swap its children, push children to stack
- Continue until stack is empty

**Algorithm**
```
1. If root is null, return null
2. Create stack and push root
3. While stack is not empty:
   a. Pop node from stack
   b. Swap node's left and right children
   c. Push left child to stack (if exists)
   d. Push right child to stack (if exists)
4. Return root
```

**Code Implementation**
```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode current = stack.pop();
            
            // Swap left and right children
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;
            
            // Push children to stack for processing
            if (current.left != null) {
                stack.push(current.left);
            }
            if (current.right != null) {
                stack.push(current.right);
            }
        }
        
        return root;
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

| Step | current | Action | stack (after) | Tree State |
|------|---------|--------|---------------|------------|
| 1 | - | Push 1 | [1] | Original |
| 2 | 1 | Pop 1, swap(2,3) → left=3, right=2 | [3,2] | 1's children swapped |
| 3 | 2 | Pop 2, swap(4,5) → left=5, right=4 | [3,5,4] | 2's children swapped |
| 4 | 4 | Pop 4, no children | [3,5] | Leaf node |
| 5 | 5 | Pop 5, no children | [3] | Leaf node |
| 6 | 3 | Pop 3, no children | [] | Leaf node |
| 7 | - | Stack empty, done | [] | **Inverted!** |

**Final Tree:**
```
        1
       / \
      3   2
         / \
        5   4
```

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node exactly once
- **Space Complexity**: O(n) - Stack can hold up to n nodes in worst case

---

#### **Approach 3: Iterative BFS with Queue (LEVEL-BY-LEVEL)**

**Core Idea**: Use a queue to process nodes level by level (BFS), swapping children at each node.

**Why This Works:**
- Queue processes nodes in level-order
- Swap children at each node
- BFS ensures all nodes are visited

**Algorithm**
```
1. If root is null, return null
2. Create queue and add root
3. While queue is not empty:
   a. Remove node from queue
   b. Swap node's left and right children
   c. Add left child to queue (if exists)
   d. Add right child to queue (if exists)
4. Return root
```

**Code Implementation**
```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            TreeNode current = queue.remove();
            
            // Swap left and right children
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;
            
            // Add children to queue for processing
            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
        
        return root;
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
```

| Step | current | Action | queue (after) | Tree State |
|------|---------|--------|---------------|------------|
| 1 | - | Add 1 | [1] | Original |
| 2 | 1 | Remove 1, swap(2,3) | [3,2] | 1: left=3, right=2 |
| 3 | 3 | Remove 3, swap(6,7) | [2,7,6] | 3: left=7, right=6 |
| 4 | 2 | Remove 2, swap(4,5) | [7,6,5,4] | 2: left=5, right=4 |
| 5 | 7 | Remove 7, no children | [6,5,4] | Leaf |
| 6 | 6 | Remove 6, no children | [5,4] | Leaf |
| 7 | 5 | Remove 5, no children | [4] | Leaf |
| 8 | 4 | Remove 4, no children | [] | Leaf |

**Final Tree:**
```
        1
       / \
      3   2
     / \ / \
    7  6 5  4
```

Output: [1,3,2,7,6,5,4] ✓

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node exactly once
- **Space Complexity**: O(w) - Queue size (w = maximum width of tree)
  - Worst case (complete tree): O(n/2) = O(n)

---

## Why This Strategy?

### Why Any Traversal Works
- **Key Insight**: Order of swapping doesn't matter!
- Swapping at root doesn't affect swapping at children
- Whether you swap top-down or bottom-up, result is the same
- This is why DFS (recursion/stack) and BFS (queue) both work

**Proof:**
```
Tree:     A
         / \
        B   C

Option 1 (Top-down):
1. Swap A's children → A has C on left, B on right
2. Swap C's children
3. Swap B's children

Option 2 (Bottom-up):
1. Swap B's children
2. Swap C's children
3. Swap A's children → A has C on left, B on right

Both produce same result! ✓
```

### Optimality Proof
- Must visit each node to swap its children → O(n) time minimum
- Cannot avoid storing some nodes during traversal → O(h) or O(w) space needed
- All three approaches are optimal at O(n) time

---

## Common Mistakes & Edge Cases

### Mistake 1: Not Returning the Root
```java
// ❌ WRONG - forgetting to return root
public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    
    TreeNode temp = root.left;
    root.left = root.right;
    root.right = temp;
    
    invertTree(root.left);
    invertTree(root.right);
    
    // Missing return statement!
}

// ✅ CORRECT
public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    
    TreeNode temp = root.left;
    root.left = root.right;
    root.right = temp;
    
    invertTree(root.left);
    invertTree(root.right);
    
    return root;  // Must return the root!
}
```

### Mistake 2: Swapping After Recursion Without Storing
```java
// ❌ WRONG - recursing before swap causes issues
public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    
    invertTree(root.left);
    invertTree(root.right);
    
    // Swapping AFTER recursion - pointers already changed!
    TreeNode temp = root.left;
    root.left = root.right;
    root.right = temp;
    
    return root;
}
```

**Why this actually works (but confusing)**: Even though you recurse first, the swap still happens correctly because you're working with the current pointers. However, it's less intuitive.

**Better approach**: Store the children before recursion:
```java
// ✅ BETTER - clearer intent
public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    
    TreeNode left = root.left;
    TreeNode right = root.right;
    
    root.left = invertTree(right);   // Left gets inverted right
    root.right = invertTree(left);   // Right gets inverted left
    
    return root;
}
```

### Edge Cases
1. **Empty Tree**: `root = null` → Return `null`
2. **Single Node**: `root = [1]` → Return `[1]` (no children to swap)
3. **Skewed Tree**: All left or all right → Still inverts correctly
4. **Perfect Binary Tree**: All levels full → Works correctly

---

## Variations & Related Problems

### 1. Symmetric Tree (Mirror Check)
Check if a tree is a mirror of itself (symmetric).

**Solution**: Invert one subtree and compare with the other.

### 2. Serialize and Deserialize Binary Tree
Convert tree to string and back.

**Connection**: Both involve tree structure manipulation.

### 3. Flip Binary Tree To Match Preorder Traversal
Flip children to match a given preorder sequence.

**Connection**: Similar swapping operations on tree nodes.

---

## Interview Tips

### What Interviewer Looks For
1. **Pattern Recognition**: Identify this as a tree traversal + swap problem
2. **Multiple Approaches**: Know both recursive and iterative solutions
3. **Edge Cases**: Handle null, single node, skewed trees
4. **In-Place Modification**: Recognize tree is modified in-place

### Common Follow-ups
- "Can you do it iteratively?" → Yes, using stack (DFS) or queue (BFS)
- "What's the space complexity?" → O(h) for recursion, O(n) for iterative
- "Does the order of traversal matter?" → No, any traversal works!
- "Can you do it in O(1) space?" → No, must store nodes during traversal
- "Is the tree modified in-place?" → Yes, we swap pointers, not create new nodes

### Fun Fact
This problem became famous because [Max Howell](https://twitter.com/mxcl/status/608682016205344768) (creator of Homebrew) was rejected by Google after failing to invert a binary tree on a whiteboard, despite his significant contributions to software. The tweet went viral and sparked debate about interview processes! 😄

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
    public TreeNode invertTree(TreeNode root) {
        // Base case: if tree is empty, return null
        if (root == null) {
            return null;
        }
        
        // Swap the left and right children
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        
        // Recursively invert the left subtree
        // (which is now the original right subtree after swap)
        invertTree(root.left);
        
        // Recursively invert the right subtree
        // (which is now the original left subtree after swap)
        invertTree(root.right);
        
        // Return the root of the inverted tree
        return root;
    }
}
```

---

## Summary

| Aspect | Details |
|--------|---------|
| **Pattern** | Tree Traversal + Swap |
| **Time Complexity** | O(n) |
| **Space Complexity** | O(h) recursive, O(n) iterative |
| **Key Insight** | Swap children at each node, recurse |
| **Order Matters?** | No - any traversal order works |
| **In-Place?** | Yes - modifies existing tree |
| **Common Mistake** | Forgetting to return root |

**Remember**: 
- Any traversal (preorder, postorder, level-order) works for inverting
- Must visit every node to swap its children
- Recursive solution is simplest and most intuitive

