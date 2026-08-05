# Invert Binary Tree

## Problem Description

**Difficulty**: Easy

You are given the root of a binary tree `root`. **Invert the binary tree** and return its root.

**Inverting a Binary Tree**: Swap the left and right children of every node in the tree.

**Key Concepts:**
- **Inversion**: Mirror the tree horizontally
- **Recursive Solution**: Natural DFS approach
- **Iterative Solution**: BFS or DFS with queue/stack
- **Swap Operation**: Exchange left and right child pointers at each node
- **In-Place**: Modify tree structure directly

**Visual Example:**
```
Original Tree:
       1
      / \
     2   3
    / \ / \
   4  5 6  7

Inverted Tree (mirrored):
       1
      / \
     3   2
    / \ / \
   7  6 5  4

Every node's children are swapped!
```

**Recommended Complexity**: O(n) time, O(n) space

---

## Examples

### Example 1 (Complete Binary Tree):
```
Input: root = [1,2,3,4,5,6,7]

Original:
       1
      / \
     2   3
    / \ / \
   4  5 6  7

Output: [1,3,2,7,6,5,4]

Inverted:
       1
      / \
     3   2
    / \ / \
   7  6 5  4

Explanation:
At each node, swap left and right children
Root 1: swap 2 and 3
Node 2: swap 4 and 5
Node 3: swap 6 and 7
```

### Example 2 (Simple Tree):
```
Input: root = [3,2,1]

Original:
    3
   / \
  2   1

Output: [3,1,2]

Inverted:
    3
   / \
  1   2

Explanation:
Only root node has children
Swap left (2) and right (1)
```

### Example 3 (Empty Tree):
```
Input: root = []

Output: []

Explanation:
Empty tree remains empty
No nodes to invert
```

### Example 4 (Single Node):
```
Input: root = [1]

Output: [1]

Explanation:
Single node has no children
Nothing to swap
```

### Example 5 (Left Skewed Tree):
```
Input: root = [1,2,null,3,null,4,null]

Original:
    1
   /
  2
 /
3
/
4

Output: [1,null,2,null,3,null,4]

Inverted:
1
 \
  2
   \
    3
     \
      4

Explanation:
Left skewed becomes right skewed
Each node's left child becomes right child
```

### Example 6 (Right Skewed Tree):
```
Input: root = [1,null,2,null,3,null,4]

Original:
1
 \
  2
   \
    3
     \
      4

Output: [1,2,null,3,null,4,null]

Inverted:
    1
   /
  2
 /
3
/
4

Explanation:
Right skewed becomes left skewed
Each node's right child becomes left child
```

### Example 7 (Unbalanced Tree):
```
Input: root = [1,2,3,4,null,null,5]

Original:
       1
      / \
     2   3
    /     \
   4       5

Output: [1,3,2,null,5,null,4]

Inverted:
       1
      / \
     3   2
    /     \
   5       4

Explanation:
Node 2 and 3 swapped at root
Node 4 becomes right child of 2
Node 5 becomes left child of 3
```

### Example 8 (Two Levels):
```
Input: root = [1,2,3]

Original:
    1
   / \
  2   3

Output: [1,3,2]

Inverted:
    1
   / \
  3   2

Explanation:
Simple swap at root
```

### Example 9 (Three Levels - Left Heavy):
```
Input: root = [1,2,3,4,5]

Original:
       1
      / \
     2   3
    / \
   4   5

Output: [1,3,2,null,null,5,4]

Inverted:
       1
      / \
     3   2
        / \
       5   4

Explanation:
Level 1: swap 2 and 3
Level 2: swap 4 and 5 (now under node 2 which is on right)
```

### Example 10 (Larger Tree):
```
Input: root = [1,2,3,4,5,6,7,8,9]

Original:
           1
         /   \
        2     3
       / \   / \
      4   5 6   7
     / \
    8   9

Output: [1,3,2,7,6,5,4,null,null,null,null,null,null,9,8]

Inverted:
           1
         /   \
        3     2
       / \   / \
      7   6 5   4
                / \
               9   8

Explanation:
Each level's children swapped recursively
Tree is mirrored horizontally
```

---

## Constraints
- `0 <= The number of nodes in the tree <= 100`
- `-100 <= Node.val <= 100`

**Recommended Complexity**: 
- Time: O(n) where n = number of nodes
- Space: O(n) for recursion stack or queue/stack

---

## Pattern Recognition

**Primary Pattern**: **Tree Traversal with Swap Operation (DFS/BFS)**

**Why This Pattern?**
- **Swap at every node**: Need to visit all nodes
- **DFS natural**: Recursive solution mirrors tree structure
- **BFS also works**: Level-by-level swapping
- **Post-order DFS**: Swap children after processing subtrees (or pre-order works too)
- **In-place modification**: Just swap pointers

**Key Insight**: Swap Then Recurse (or Recurse Then Swap)
```
For each node:
  1. Swap left and right children
  2. Recursively invert left subtree
  3. Recursively invert right subtree

Or equivalently:
  1. Recursively invert left subtree
  2. Recursively invert right subtree  
  3. Swap left and right children

Both work! Swap before or after recursion.
```

**Visual: Recursive Inversion**
```
Original Tree:
       1
      / \
     2   3
    / \
   4   5

Recursive Process:

invert(1):
  Swap 2 and 3:
       1
      / \
     3   2        (swapped)
    / \
   4   5
  
  invert(3):      (was right, now left)
    No children, return 3
  
  invert(2):      (was left, now right)
    Swap 4 and 5:
         2
        / \
       5   4      (swapped)
    
    invert(5): return 5
    invert(4): return 4
    return 2
  
  return 1

Final Result:
       1
      / \
     3   2
        / \
       5   4
```

**Why Recursive is Natural**:
```
Tree is recursive structure:
  - Node has left subtree
  - Node has right subtree
  
Inversion is recursive operation:
  - Invert left subtree
  - Invert right subtree
  - Swap them

Perfect fit! ✓
```

**Why BFS Also Works**:
```
Level-by-level swapping:
  
Level 0: Swap children of root
Level 1: Swap children of all level 1 nodes
Level 2: Swap children of all level 2 nodes
...

Use queue for BFS:
  Add root
  While queue not empty:
    Dequeue node
    Swap its children
    Enqueue children (if exist)

Also O(n) time! ✓
```

**Visual: BFS Approach**
```
Original:
       1
      / \
     2   3
    / \
   4   5

BFS Queue Processing:

Initial: queue = [1]

Process 1:
  Swap children: 3 and 2
       1
      / \
     3   2
    / \
   4   5
  Enqueue 3, 2
  queue = [3, 2]

Process 3:
  No children, nothing to swap
  queue = [2]

Process 2:
  Swap children: 5 and 4
       1
      / \
     3   2
        / \
       5   4
  Enqueue 5, 4
  queue = [5, 4]

Process 5:
  No children
  queue = [4]

Process 4:
  No children
  queue = []

Done!

Result:
       1
      / \
     3   2
        / \
       5   4
```

**Swap Before vs After Recursion**:
```
Approach 1: Swap first, then recurse
  temp = root.left
  root.left = root.right
  root.right = temp
  invert(root.left)   // Now the swapped left
  invert(root.right)  // Now the swapped right

Approach 2: Recurse first, then swap
  invert(root.left)   // Original left
  invert(root.right)  // Original right
  temp = root.left
  root.left = root.right
  root.right = temp

Both produce same result! ✓
Order doesn't matter for correctness.
```

**Core Operations**:

**Recursive DFS (Swap First)**:
```java
TreeNode invertTree(TreeNode root):
    if root == null:
        return null
    
    // Swap left and right
    temp = root.left
    root.left = root.right
    root.right = temp
    
    // Recurse on swapped children
    invertTree(root.left)
    invertTree(root.right)
    
    return root
```

**Iterative BFS**:
```java
TreeNode invertTree(TreeNode root):
    if root == null:
        return null
    
    queue = new LinkedList<>()
    queue.offer(root)
    
    while !queue.isEmpty():
        node = queue.poll()
        
        // Swap children
        temp = node.left
        node.left = node.right
        node.right = temp
        
        // Add children to queue
        if node.left != null:
            queue.offer(node.left)
        if node.right != null:
            queue.offer(node.right)
    
    return root
```

**Related Patterns**:
1. **Tree Traversal** — DFS (recursive) or BFS (iterative)
2. **In-Place Modification** — Swap pointers directly
3. **Mirror/Symmetry** — Related to symmetric tree problems
4. **Post-order/Pre-order** — Both work for this problem

---

## Algorithm & Approach

### Core Insight

**Why Inversion Works:**
```
Key observations:
  1. Inversion = swap left/right at every node
  2. Need to visit all n nodes
  3. Recursive DFS natural: matches tree structure
  4. Iterative BFS also works: level-by-level
  5. Swap before or after recursion both valid
  6. Return root (root pointer doesn't change)
```

**The Optimal Strategy**:
```
Recursive DFS (Simplest):
  - Base case: null returns null
  - Swap left and right children
  - Recursively invert left subtree
  - Recursively invert right subtree
  - Return root
  - O(n) time, O(h) space for stack

Iterative BFS (Alternative):
  - Use queue for level-order traversal
  - For each node: swap children, add to queue
  - Continue until queue empty
  - O(n) time, O(w) space where w = max width
```

### Step-by-Step Algorithm

---

#### **Approach 1: Recursive DFS - SIMPLEST**

**Core Idea**:
- Swap left and right children at current node
- Recursively invert left subtree
- Recursively invert right subtree
- Return modified root

**Algorithm**
```java
invertTree(TreeNode root):
    // Base case
    if root == null:
        return null
    
    // Swap left and right children
    temp = root.left
    root.left = root.right
    root.right = temp
    
    // Recursively invert subtrees
    invertTree(root.left)   // Invert swapped left
    invertTree(root.right)  // Invert swapped right
    
    // Return modified root
    return root
```

**Complete Code Implementation (Recursive)**
```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        // Base case: null node
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
        
        // Return the root of inverted tree
        return root;
    }
}
```

**Example Walkthrough (Recursive)**

Input: root = [1,2,3,4,5]
```
Original:
       1
      / \
     2   3
    / \
   4   5
```

**Recursive Calls:**
```
invertTree(1):
  Swap: left=3, right=2
       1
      / \
     3   2
    / \
   4   5
  
  invertTree(3):     // Now left child
    Swap: left=null, right=null
    No children
    return 3
  
  invertTree(2):     // Now right child
    Swap: left=5, right=4
         2
        / \
       5   4
    
    invertTree(5):
      No children
      return 5
    
    invertTree(4):
      No children
      return 4
    
    return 2
  
  return 1

Final Result:
       1
      / \
     3   2
        / \
       5   4
```

---

#### **Approach 2: Iterative BFS (Queue-Based)**

**Core Idea**:
- Use queue for level-order traversal
- For each node: swap its children
- Add children to queue for processing
- Continue until queue empty

**Algorithm**
```java
invertTree(TreeNode root):
    if root == null:
        return null
    
    queue = new LinkedList<>()
    queue.offer(root)
    
    while !queue.isEmpty():
        node = queue.poll()
        
        // Swap left and right children
        temp = node.left
        node.left = node.right
        node.right = temp
        
        // Add children to queue (if exist)
        if node.left != null:
            queue.offer(node.left)
        if node.right != null:
            queue.offer(node.right)
    
    return root
```

**Complete Code Implementation (BFS)**
```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            // Swap children
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            // Add children to queue
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        
        return root;
    }
}
```

---

#### **Approach 3: Iterative DFS (Stack-Based)**

**Core Idea**:
- Use stack for DFS traversal
- For each node: swap its children
- Push children to stack for processing
- Similar to BFS but with stack (LIFO)

**Complete Code Implementation (DFS with Stack)**
```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            
            // Swap children
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            // Push children to stack
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        
        return root;
    }
}
```

**Complexity Analysis**
- **All Approaches**: O(n) time, O(n) space
  - Recursive: O(h) space for call stack (h = height, worst O(n))
  - BFS: O(w) space for queue (w = max width, worst O(n))
  - DFS Stack: O(h) space for stack (worst O(n))

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Difficulty | Clean Code | Recommended |
|----------|------|-------|------------|------------|-------------|
| **Recursive DFS** | **O(n)** | **O(h)** | **Easy** | **Yes** | **✓ Simplest** |
| BFS Queue | O(n) | O(w) | Easy | Yes | ✓ Alternative |
| DFS Stack | O(n) | O(h) | Easy | Yes | Alternative |

**Winner**: **Recursive DFS** — simplest and most natural

### Why Recursive is Most Natural

```
Tree is recursive structure:
  Node {
    value
    left: subtree
    right: subtree
  }

Inversion is recursive:
  Swap left and right
  Invert left subtree
  Invert right subtree

Perfect match! ✓
```

### Why BFS Also Works Well

```
Level-by-level processing:
  - Clear mental model
  - Iterative (no stack space)
  - Queue usage straightforward
  
Good alternative if recursion not preferred! ✓
```

### Why Swap Before or After Both Work

```
Swap Before Recursion:
  swap(left, right)
  invert(left)   // The swapped left
  invert(right)  // The swapped right
  
  Result: subtrees inverted after swap ✓

Swap After Recursion:
  invert(left)   // Original left
  invert(right)  // Original right
  swap(left, right)
  
  Result: swap after subtrees inverted ✓

Both produce correct result!
Order doesn't matter for correctness.
```

### Why This is Optimal

```
Time complexity:
  Must visit all n nodes: Ω(n)
  Each node visited once: O(n)
  Optimal! ✓

Space complexity:
  Recursive: O(h) for stack
    Best (balanced): h = log n
    Worst (skewed): h = n
  BFS: O(w) for queue
    Best: w = 1
    Worst: w = n/2 (complete tree last level)
  
  All approaches O(n) worst case
  Acceptable! ✓
```

---

## Critical Edge Cases & Gotchas

### 1. **Empty Tree (Null Root)**
```java
root = null
// Return null immediately
// No inversion needed
```

### 2. **Single Node**
```java
root = [1]
// No children to swap
// Return same node
```

### 3. **Two Nodes - Left Child**
```java
    1
   /
  2

// Swap: left=null, right=2
// Result: 1 with right child 2
    1
     \
      2
```

### 4. **Two Nodes - Right Child**
```java
1
 \
  2

// Swap: left=2, right=null
// Result: 1 with left child 2
    1
   /
  2
```

### 5. **Complete Binary Tree**
```java
       1
      / \
     2   3
    / \ / \
   4  5 6  7

// Every level swapped
// Perfect mirror image
```

### 6. **Left Skewed Tree**
```java
    1
   /
  2
 /
3

// Becomes right skewed
1
 \
  2
   \
    3
```

### 7. **Right Skewed Tree**
```java
1
 \
  2
   \
    3

// Becomes left skewed
    1
   /
  2
 /
3
```

### 8. **Unbalanced Tree**
```java
       1
      / \
     2   3
    /
   4

// Becomes:
       1
      / \
     3   2
          \
           4
```

### 9. **All Same Values**
```java
    1
   / \
  1   1

// Structure changes, not values
    1
   / \
  1   1
// Swapped but same values
```

### 10. **Negative Values**
```java
     0
    / \
  -5   5

// Values don't affect inversion
     0
    / \
   5  -5
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Handling Null Root**
```java
// WRONG - no null check
public TreeNode invertTree(TreeNode root) {
    TreeNode temp = root.left;  // NullPointerException if root is null! ❌
    root.left = root.right;
    root.right = temp;
    
    invertTree(root.left);
    invertTree(root.right);
    
    return root;
}
```

**Why wrong**: Null pointer exception!

**Fix**: Check null first
```java
if (root == null) {  ✓
    return null;
}
```

### ❌ **MISTAKE 2: Not Returning Root**
```java
// WRONG - void return
public void invertTree(TreeNode root) {  // ❌
    if (root == null) return;
    
    TreeNode temp = root.left;
    root.left = root.right;
    root.right = temp;
    
    invertTree(root.left);
    invertTree(root.right);
    
    // Missing return!
}
```

**Why wrong**: Problem asks to return root!

**Fix**: Return TreeNode
```java
public TreeNode invertTree(TreeNode root) {  ✓
    // ... inversion ...
    return root;  ✓
}
```

### ❌ **MISTAKE 3: Swapping Without Temp Variable**
```java
// WRONG - direct swap loses reference
root.left = root.right;   // ❌ Lost original root.left!
root.right = root.left;   // ❌ Now both point to same node!
```

**Why wrong**: Lost reference to original left!

**Dry run failure:**
```
Original: left=2, right=3

root.left = root.right  → left=3, right=3 (both 3!)
root.right = root.left  → left=3, right=3 (no change)

Wrong! Lost node 2 ❌
```

**Fix**: Use temp variable
```java
TreeNode temp = root.left;  ✓
root.left = root.right;
root.right = temp;
```

### ❌ **MISTAKE 4: Not Recursing on Swapped Children**
```java
// WRONG - recursing before swap
public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    
    invertTree(root.left);   // ❌ Process before swap
    invertTree(root.right);
    
    // Swap after (actually this works too!)
    TreeNode temp = root.left;
    root.left = root.right;
    root.right = temp;
    
    return root;
}
```

**Why wrong**: Actually, this is NOT wrong! Both orders work.

**Clarification:**
```
Swap then recurse: ✓ Works
Recurse then swap: ✓ Also works

Both produce correct result!
```

### ❌ **MISTAKE 5: BFS - Not Checking Children Before Enqueue**
```java
// WRONG - enqueueing null nodes
queue.offer(node.left);   // ❌ What if null?
queue.offer(node.right);  // ❌ What if null?
```

**Why wrong**: Processing null nodes wastes time!

**Fix**: Check before enqueueing
```java
if (node.left != null) {  ✓
    queue.offer(node.left);
}
if (node.right != null) {  ✓
    queue.offer(node.right);
}
```

### ❌ **MISTAKE 6: Creating New Nodes Instead of Swapping**
```java
// WRONG - creating new nodes
root.left = new TreeNode(root.right.val);   // ❌
root.right = new TreeNode(root.left.val);   // ❌
```

**Why wrong**: Should swap pointers, not create new nodes!

**Fix**: Swap pointers directly
```java
TreeNode temp = root.left;  ✓
root.left = root.right;
root.right = temp;
```

### ❌ **MISTAKE 7: BFS - Not Initializing Queue**
```java
// WRONG - queue not initialized
Queue<TreeNode> queue;  // ❌ null reference

queue.offer(root);  // NullPointerException!
```

**Why wrong**: Queue not created!

**Fix**: Initialize queue
```java
Queue<TreeNode> queue = new LinkedList<>();  ✓
```

### ❌ **MISTAKE 8: Recursive - Not Using Return Value**
```java
// WRONG - not using or returning recursive result
public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    
    TreeNode temp = root.left;
    root.left = root.right;
    root.right = temp;
    
    invertTree(root.left);   // Not using return value
    invertTree(root.right);  // Not using return value
    
    return root;  // ✓ This is correct though!
}
```

**Why wrong**: Actually, this is CORRECT for this problem!

**Clarification:**
```
We modify tree in-place
Don't need to reassign:
  root.left = invertTree(root.left)

Just call is fine:
  invertTree(root.left) ✓

Return value used at top level! ✓
```

### ❌ **MISTAKE 9: Modifying Values Instead of Structure**
```java
// WRONG - swapping values, not structure
int temp = root.left.val;   // ❌
root.left.val = root.right.val;
root.right.val = temp;
```

**Why wrong**: Should swap subtrees, not just values!

**Issue:**
```
    1
   / \
  2   3
 /
4

Swapping values of 2 and 3:
    1
   / \
  3   2  ← values swapped
 /
4  ← structure wrong! Node 4 still under left child

Should swap entire subtrees! ✓
```

**Fix**: Swap pointers to subtrees
```java
TreeNode temp = root.left;  ✓
root.left = root.right;
root.right = temp;
```

### ❌ **MISTAKE 10: BFS - Wrong Queue Type**
```java
// WRONG - using Stack instead of Queue
Queue<TreeNode> queue = new Stack<>();  // ❌ Stack, not Queue!
```

**Why wrong**: Stack is LIFO, Queue is FIFO!

**Fix**: Use proper Queue implementation
```java
Queue<TreeNode> queue = new LinkedList<>();  ✓
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

```
Where n = number of nodes

All approaches:
  - Visit each node exactly once
  - Swap operation at each node: O(1)
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
  - Swap children: O(1)
  - Add to queue/stack: O(1)
  - Process once: O(1)
  
All nodes: n × O(1) = O(n) ✓
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
  - Average: O(n/2) = O(n)

Maximum nodes in queue = tree width
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
  - Worst case all O(n)
  - Acceptable! ✓

Can't do better than O(n) time! ✓
```

---

## Visualization

### Complete Example Walkthrough (Recursive)

**Input:** `root = [1,2,3,4,5,6,7]`

```
Original Tree:
       1
      / \
     2   3
    / \ / \
   4  5 6  7
```

**Recursive Call Stack:**

```
invertTree(1):
  Swap: left=3, right=2
       1
      / \
     3   2
    / \ / \
   6  7 4  5
  
  invertTree(3):         // Now left child
    Swap: left=7, right=6
         3
        / \
       7   6
    
    invertTree(7):
      No children, return 7
    
    invertTree(6):
      No children, return 6
    
    return 3
  
  invertTree(2):         // Now right child
    Swap: left=5, right=4
         2
        / \
       5   4
    
    invertTree(5):
      No children, return 5
    
    invertTree(4):
      No children, return 4
    
    return 2
  
  return 1

Final Result:
       1
      / \
     3   2
    / \ / \
   7  6 5  4
```

---

### Complete Example Walkthrough (BFS)

**Input:** `root = [1,2,3,4,5]`

**BFS Queue Processing:**

```
Original:
       1
      / \
     2   3
    / \
   4   5

Initial: queue = [1]

Process 1:
  Swap: left=3, right=2
       1
      / \
     3   2
    / \
   4   5
  
  Enqueue 3, 2
  queue = [3, 2]

Process 3:
  No children to swap
  queue = [2]

Process 2:
  Swap: left=5, right=4
       1
      / \
     3   2
        / \
       5   4
  
  Enqueue 5, 4
  queue = [5, 4]

Process 5:
  No children
  queue = [4]

Process 4:
  No children
  queue = []

Done!

Result:
       1
      / \
     3   2
        / \
       5   4
```

---

### Visual: Before and After

```
Before Inversion:
       1
      / \
     2   3
    / \ / \
   4  5 6  7

After Inversion (Mirror):
       1
      / \
     3   2
    / \ / \
   7  6 5  4

Every level swapped:
  Level 1: 2 ↔ 3
  Level 2: 4 ↔ 5, 6 ↔ 7
```

---

## Comparison of Approaches

| Approach | Time | Space | Difficulty | Clean Code | Recommended |
|----------|------|-------|------------|------------|-------------|
| **Recursive DFS** | **O(n)** | **O(h)** | **Easy** | **Yes** | **✓ Simplest** |
| BFS Queue | O(n) | O(w) | Easy | Yes | ✓ Alternative |
| DFS Stack | O(n) | O(h) | Easy | Yes | Alternative |

**When to Use Each:**
- **Interview default**: Recursive DFS (simplest, most natural)
- **Prefer iterative**: BFS Queue (clear level-by-level)
- **Consistent with other problems**: DFS Stack (similar to other DFS)

---

## Key Takeaways

1. **Inversion = Swap**: Swap left and right at every node
2. **Recursive natural**: Matches tree recursive structure
3. **BFS also works**: Level-by-level swapping
4. **Swap before or after**: Both recursion orders work
5. **Use temp variable**: For swapping pointers
6. **Visit all nodes**: O(n) time required
7. **In-place modification**: Just swap pointers
8. **Return root**: Root pointer doesn't change
9. **Handle null**: Check root null first
10. **Mirror image**: Tree reflected horizontally

---

## Interview Tips

**What to say in an interview:**

> "I need to invert the binary tree, which means swapping the left and right children at every node to create a mirror image. I'll use a recursive DFS approach since it's the most natural for tree problems.
>
> At each node, I'll first check if it's null and return null if so. Then I'll swap the left and right children using a temporary variable to avoid losing references. After swapping, I'll recursively invert the left subtree and the right subtree. Finally, I'll return the root.
>
> The recursion naturally handles all nodes because each subtree is itself a tree. The base case is when we reach a null node. The recursive case swaps children and processes subtrees.
>
> Time complexity is O(n) since we visit each of the n nodes exactly once. Space complexity is O(h) where h is the tree height, due to the recursion call stack. For a balanced tree that's O(log n), but for a skewed tree it could be O(n).
>
> An alternative approach is to use BFS with a queue to process nodes level by level, swapping children at each node. This has the same O(n) time complexity but uses O(w) space where w is the maximum width of the tree."

**Key points to mention:**
1. **Inversion = swap children**: At every node
2. **Recursive DFS**: Most natural approach
3. **Temp variable**: For safe swapping
4. **Base case**: Null returns null
5. **Recursive case**: Swap then recurse (or vice versa)
6. **Time**: O(n) visit all nodes
7. **Space**: O(h) recursion stack
8. **BFS alternative**: Level-order with queue
9. **In-place**: Modify tree structure directly
10. **Return root**: Tree root doesn't change

**Common Follow-ups:**
- "Can you do it iteratively?" → Yes, with BFS queue or DFS stack
- "Why use temp variable?" → Avoid losing reference when swapping
- "What's the space complexity?" → O(h) for recursion, O(w) for BFS
- "Does order of recursion matter?" → No, swap before or after both work
- "When would BFS be better?" → If stack space limited, or prefer iterative

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Invert Binary Tree** | Easy | **DFS/BFS Swap** | **This problem** |
| Symmetric Tree | Easy | DFS/BFS | Check if tree is mirror of itself |
| Maximum Depth of Binary Tree | Easy | DFS/BFS | Find height instead of inverting |
| Same Tree | Easy | DFS | Compare two trees |
| Mirror Reflection | Medium | Tree Comparison | Compare original and inverted |
| Flip Binary Tree To Match Preorder | Medium | DFS | More complex flipping rules |

**Pattern Progression**:
1. **Invert Binary Tree** — Learn swap operation
2. **Symmetric Tree** — Use inversion concept
3. **Tree Comparison** — Compare inverted structures
4. **Complex Transformations** — Build on inversion

---

## Final Pattern Label

✅ **Tree Traversal with In-Place Swap (DFS/BFS)**

**Remember:** This problem requires **swapping left and right children at every node** to create a **mirror image** of the binary tree. **Recursive DFS approach** (simplest): base case `if (root == null) return null`, swap left and right children using temp variable (`temp = root.left; root.left = root.right; root.right = temp`), recursively invert left subtree (`invertTree(root.left)`), recursively invert right subtree (`invertTree(root.right)`), return root. **Critical swap detail**: Must use **temp variable** to avoid losing reference (`root.left = root.right` loses original left if no temp). **Swap order flexibility**: Can swap **before recursion** (process current then subtrees) or **after recursion** (process subtrees then current) — both produce correct result. **Alternative BFS approach** (iterative): use queue for level-order traversal, for each node swap children then enqueue children (check not null before enqueuing), continue until queue empty. **Time complexity**: O(n) visit each node once, O(1) swap per node. **Space complexity**: O(h) for recursive call stack (best O(log n) balanced, worst O(n) skewed), O(w) for BFS queue where w = max tree width (worst O(n/2) for complete tree). **Common mistakes**: not checking null root (NullPointerException), swapping without temp variable (loses reference), not returning root (problem requires return), enqueueing null children in BFS (wasted processing), swapping node values instead of pointers (wrong - must swap structure not values), creating new nodes instead of swapping (wrong - in-place modification required). **Use cases**: mirror/flip tree, symmetric tree check, tree transformations. Pattern: **visit all nodes with DFS/BFS** + **swap left/right pointers at each node** = complete tree inversion in O(n) time! ✓
