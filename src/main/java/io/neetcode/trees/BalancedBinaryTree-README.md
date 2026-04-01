# Balanced Binary Tree

## Problem Description

**Difficulty**: Easy

Given a binary tree, return `true` if it is **height-balanced** and `false` otherwise.

A **height-balanced binary tree** is defined as a binary tree in which the **left and right subtrees of every node differ in height by no more than 1**.

## Examples

### Example 1:
```
Input: root = [1,2,3,null,null,4]

Tree Structure:
        1
       / \
      2   3
           \
            4

Output: true
Explanation: 
- At node 1: |leftHeight(1) - rightHeight(2)| = 1 ✓
- At node 2: |leftHeight(0) - rightHeight(0)| = 0 ✓
- At node 3: |leftHeight(0) - rightHeight(1)| = 1 ✓
- At node 4: |leftHeight(0) - rightHeight(0)| = 0 ✓
All nodes satisfy the balance condition.
```

### Example 2:
```
Input: root = [1,2,3,null,null,4,null,5]

Tree Structure:
        1
       / \
      2   3
         /
        4
       /
      5

Output: false
Explanation:
- At node 1: |leftHeight(1) - rightHeight(3)| = 2 ✗
The tree is NOT balanced at the root.
```

### Example 3:
```
Input: root = []

Output: true
Explanation: An empty tree is considered balanced.
```

## Constraints
- The number of nodes in the tree is in the range [0, 1000]
- -1000 <= Node.val <= 1000

---

## Pattern Recognition

**Primary Pattern**: **Depth-First Search (DFS) with Height Calculation**

**Why This Pattern?**
- Need to check balance condition at **every node** in the tree
- Balance depends on **heights** of left and right subtrees
- Height calculation is naturally recursive (bottom-up)
- Perfect use case for **post-order DFS** (process children first, then parent)

**Key Insight**: 
- A tree is balanced if **EVERY** node satisfies: |leftHeight - rightHeight| <= 1
- We need to calculate heights while checking balance condition
- Use **early termination**: if any subtree is unbalanced, entire tree is unbalanced
- Efficient approach combines height calculation + balance check in single pass

**Related Patterns**:
1. **Maximum Depth of Binary Tree** - Uses same height calculation
2. **Diameter of Binary Tree** - Also uses height + global state
3. **Minimum Depth of Binary Tree** - Similar height-based problem
4. **Binary Tree Maximum Path Sum** - Combines calculation with validation

---

## Algorithm & Approach

### Core Insight

**The Balance Problem Has Two Components:**
1. **Height**: Calculate height of each subtree (needed for balance check)
2. **Balance**: Check if |leftHeight - rightHeight| <= 1 at every node

**Why it works:**
```
Tree:       1
           / \
          2   3
         /
        4

Balance check at each node:
- Node 4: |left(0) - right(0)| = 0 ✓ balanced
- Node 2: |left(1) - right(0)| = 1 ✓ balanced
- Node 3: |left(0) - right(0)| = 0 ✓ balanced
- Node 1: |left(2) - right(1)| = 1 ✓ balanced

Tree is BALANCED
```

**Unbalanced Example:**
```
Tree:       1
           /
          2
         /
        3

- Node 3: |left(0) - right(0)| = 0 ✓
- Node 2: |left(1) - right(0)| = 1 ✓
- Node 1: |left(2) - right(0)| = 2 ✗ NOT balanced!
```

**Critical Understanding:**
- Must check balance at **every node**, not just root
- Unbalanced subtree → entire tree is unbalanced
- Return **height** for parent calculations
- Use special value (like -1) to signal "unbalanced" for early termination

### Visual Understanding
```
        1
       / \
      2   3
           \
            4

Heights:
- Node 2: height = 1 (one edge to leaf)
- Node 4: height = 1 (one edge to leaf)
- Node 3: height = 2 (two edges: 3→4→leaf)
- Node 1: height = 3 (max path)

Balance checks:
- Node 1: |1 - 2| = 1 ✓ (difference is 1, allowed)
- Node 2: |0 - 0| = 0 ✓
- Node 3: |0 - 1| = 1 ✓
- Node 4: |0 - 0| = 0 ✓

Result: BALANCED
```

### Step-by-Step Algorithm

#### **Approach 1: Recursive DFS with Height Calculation (OPTIMAL)**

**Core Idea**: 
- Calculate height of each subtree recursively
- At each node, check if |leftHeight - rightHeight| <= 1
- If unbalanced anywhere, return -1 as signal
- Otherwise, return actual height

**Algorithm**
```
calculateHeight(node):
    if node is null:
        return 0
    
    leftHeight = calculateHeight(node.left)
    // Early termination: left subtree is unbalanced
    if leftHeight == -1:
        return -1
    
    rightHeight = calculateHeight(node.right)
    // Early termination: right subtree is unbalanced
    if rightHeight == -1:
        return -1
    
    // Check balance at current node
    if |leftHeight - rightHeight| > 1:
        return -1  // Signal unbalanced
    
    // Return actual height for parent
    return 1 + max(leftHeight, rightHeight)

isBalanced(root):
    return calculateHeight(root) != -1
```

**Code Implementation**
```java
class Solution {
    public boolean isBalanced(TreeNode root) {
        return calculateHeight(root) != -1;
    }
    
    private int calculateHeight(TreeNode node) {
        // Base case: null node has height 0
        if (node == null) {
            return 0;
        }
        
        // Calculate height of left subtree
        int leftHeight = calculateHeight(node.left);
        
        // Early termination: if left subtree is unbalanced
        if (leftHeight == -1) {
            return -1;
        }
        
        // Calculate height of right subtree
        int rightHeight = calculateHeight(node.right);
        
        // Early termination: if right subtree is unbalanced
        if (rightHeight == -1) {
            return -1;
        }
        
        // Check balance condition at current node
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;  // Signal that tree is unbalanced
        }
        
        // Return height of current subtree for parent calculation
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
```

**Example Walkthrough**

Input: root = [1,2,3,null,null,4,null,5]

```
Tree:
        1
       / \
      2   3
         /
        4
       /
      5

Call Stack Visualization:

calculateHeight(1)
├─ calculateHeight(2)
│  ├─ calculateHeight(null) → return 0
│  ├─ calculateHeight(null) → return 0
│  ├─ |0 - 0| = 0 ≤ 1 ✓
│  └─ return 1 + max(0, 0) = 1
├─ calculateHeight(3)
│  ├─ calculateHeight(4)
│  │  ├─ calculateHeight(5)
│  │  │  ├─ calculateHeight(null) → return 0
│  │  │  ├─ calculateHeight(null) → return 0
│  │  │  ├─ |0 - 0| = 0 ≤ 1 ✓
│  │  │  └─ return 1 + max(0, 0) = 1
│  │  ├─ calculateHeight(null) → return 0
│  │  ├─ |1 - 0| = 1 ≤ 1 ✓
│  │  └─ return 1 + max(1, 0) = 2
│  ├─ calculateHeight(null) → return 0
│  ├─ |2 - 0| = 2 > 1 ✗ NOT BALANCED!
│  └─ return -1
├─ rightHeight = -1 (unbalanced detected)
└─ return -1

Final Result: false (tree is NOT balanced)
```

**Step-by-Step Trace:**

| Call | Node | Left Height | Right Height | Balance Check | Result |
|------|------|-------------|--------------|---------------|--------|
| 1 | 5 | 0 | 0 | \|0-0\|=0 ≤ 1 ✓ | 1 |
| 2 | 4 | 1 | 0 | \|1-0\|=1 ≤ 1 ✓ | 2 |
| 3 | 3 | 2 | 0 | \|2-0\|=2 > 1 ✗ | **-1** |
| 4 | 2 | 0 | 0 | \|0-0\|=0 ≤ 1 ✓ | 1 |
| 5 | 1 | 1 | -1 | -1 detected | **-1** |

**Final Result: false**

**Why This Works:**
1. **Bottom-up Calculation**: Process children before parent (post-order)
2. **Early Termination**: Stop as soon as we find unbalanced subtree
3. **Single Pass**: O(n) - each node visited once
4. **Height + Balance**: Combine both checks efficiently

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node exactly once
- **Space Complexity**: O(h) - Recursion stack depth (h = height of tree)
  - Best case (balanced tree): O(log n)
  - Worst case (skewed tree): O(n)

---

#### **Approach 2: Iterative Post-order with Stack**

**Core Idea**: 
Use iterative post-order traversal with a stack:
- Process children before parent (post-order)
- Store heights in a HashMap
- Check balance condition at each node
- Return false immediately if any node is unbalanced

**Why Post-order?**
- We need height information from children before checking parent
- Post-order visits: left → right → root
- Ensures children processed before parent

**Algorithm**
```
1. Use stack for post-order traversal
2. Use HashMap to store heights of processed nodes
3. For each node (in post-order):
   a. Get left and right heights from map (or 0 if null)
   b. Check if |leftHeight - rightHeight| > 1
   c. If unbalanced, return false immediately
   d. Store current height in map
4. Return true if all nodes pass balance check
```

**Code Implementation**
```java
class Solution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        
        Map<TreeNode, Integer> heightMap = new HashMap<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        
        // Post-order traversal using stack
        while (!stack.isEmpty() || current != null) {
            // Go to leftmost node
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                TreeNode peekNode = stack.peek();
                
                // If right child exists and not processed yet
                if (peekNode.right != null && lastVisited != peekNode.right) {
                    current = peekNode.right;
                } else {
                    // Process current node (post-order: after children)
                    int leftHeight = heightMap.getOrDefault(peekNode.left, 0);
                    int rightHeight = heightMap.getOrDefault(peekNode.right, 0);
                    
                    // Check balance condition
                    if (Math.abs(leftHeight - rightHeight) > 1) {
                        return false;  // Not balanced
                    }
                    
                    // Store height for parent
                    heightMap.put(peekNode, 1 + Math.max(leftHeight, rightHeight));
                    
                    lastVisited = stack.pop();
                }
            }
        }
        
        return true;
    }
}
```

**Example Walkthrough**

Input: root = [1,2,3,null,null,4]

```
Tree:
        1
       / \
      2   3
           \
            4

Post-order Processing Order: 2 → 4 → 3 → 1

Step-by-step:

1. Process Node 2:
   - leftHeight = 0, rightHeight = 0
   - |0 - 0| = 0 ≤ 1 ✓
   - heightMap[2] = 1

2. Process Node 4:
   - leftHeight = 0, rightHeight = 0
   - |0 - 0| = 0 ≤ 1 ✓
   - heightMap[4] = 1

3. Process Node 3:
   - leftHeight = 0, rightHeight = 1 (from map[4])
   - |0 - 1| = 1 ≤ 1 ✓
   - heightMap[3] = 2

4. Process Node 1:
   - leftHeight = 1 (from map[2]), rightHeight = 2 (from map[3])
   - |1 - 2| = 1 ≤ 1 ✓
   - heightMap[1] = 3

Result: true (all nodes balanced)
```

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node once
- **Space Complexity**: O(n) - HashMap + Stack
  - HashMap stores height for n nodes: O(n)
  - Stack in worst case (skewed): O(n)

---

## Comparison of Approaches

| Aspect | Recursive DFS | Iterative Post-order |
|--------|---------------|---------------------|
| **Time Complexity** | O(n) | O(n) |
| **Space Complexity** | O(h) | O(n) |
| **Code Simplicity** | Very Simple | More Complex |
| **Intuition** | Natural (bottom-up) | Requires stack simulation |
| **Early Termination** | Yes (return -1) | Yes (return false) |
| **Preferred?** | ✅ Yes | Only if recursion not allowed |

**Recommendation**: Use **Recursive DFS** approach - cleaner, more intuitive, better space complexity.

---

## Key Takeaways

1. **Balance Definition**
   - Tree is balanced if **every node** has |leftHeight - rightHeight| <= 1
   - Not just the root - must check all nodes

2. **Efficient Solution Pattern**
   - Combine height calculation + balance check in single pass
   - Use sentinel value (-1) for early termination
   - Avoid redundant height calculations

3. **Post-order Traversal**
   - Calculate children first, then parent
   - Perfect for bottom-up calculations
   - Required when parent depends on children's results

4. **Early Termination**
   - Stop as soon as unbalanced subtree found
   - No need to check remaining nodes
   - Improves average-case performance

5. **Height vs Balance**
   - Height: Max edges from node to any leaf below it
   - Balance: Constraint on height difference between subtrees

---

## Common Pitfalls

❌ **Mistake 1**: Only checking balance at root
```java
// WRONG: Only checks root
int leftHeight = height(root.left);
int rightHeight = height(root.right);
return Math.abs(leftHeight - rightHeight) <= 1;
```

✅ **Correct**: Check every node
```java
// Check balance at current node + all descendants
if (Math.abs(leftHeight - rightHeight) > 1) {
    return -1;  // Unbalanced
}
```

❌ **Mistake 2**: Redundant height calculations (O(n²) solution)
```java
// WRONG: Calculates height separately, causing O(n²)
public boolean isBalanced(TreeNode root) {
    if (root == null) return true;
    
    int left = height(root.left);   // O(n)
    int right = height(root.right); // O(n)
    
    return Math.abs(left - right) <= 1 
        && isBalanced(root.left)    // Recursion
        && isBalanced(root.right);
}

private int height(TreeNode node) {
    if (node == null) return 0;
    return 1 + Math.max(height(node.left), height(node.right));
}
```

✅ **Correct**: Single pass with combined calculation
```java
// Combine height + balance check in one traversal
private int calculateHeight(TreeNode node) {
    if (node == null) return 0;
    
    int leftHeight = calculateHeight(node.left);
    if (leftHeight == -1) return -1;  // Early termination
    
    int rightHeight = calculateHeight(node.right);
    if (rightHeight == -1) return -1;
    
    if (Math.abs(leftHeight - rightHeight) > 1) return -1;
    
    return 1 + Math.max(leftHeight, rightHeight);
}
```

❌ **Mistake 3**: Forgetting to propagate unbalanced state
```java
// WRONG: Doesn't check if children returned -1
int leftHeight = calculateHeight(node.left);
int rightHeight = calculateHeight(node.right);
// Missing: if (leftHeight == -1 || rightHeight == -1) return -1;
```

✅ **Correct**: Check and propagate
```java
int leftHeight = calculateHeight(node.left);
if (leftHeight == -1) return -1;  // Propagate unbalanced state

int rightHeight = calculateHeight(node.right);
if (rightHeight == -1) return -1;
```

---

## Related Problems

1. **Maximum Depth of Binary Tree** (Easy) - Building block for height calculation
2. **Diameter of Binary Tree** (Easy) - Similar height-based calculation
3. **Minimum Depth of Binary Tree** (Easy) - Height variant
4. **Convert Sorted Array to Binary Search Tree** (Easy) - Creates balanced BST
5. **Validate Binary Search Tree** (Medium) - Similar validation pattern
6. **Binary Tree Maximum Path Sum** (Hard) - Advanced DFS with global tracking

---

## Edge Cases to Consider

1. **Empty Tree**
   ```
   Tree: []
   Result: true (empty tree is balanced)
   ```

2. **Single Node**
   ```
   Tree: [1]
   Result: true (single node is balanced)
   ```

3. **Perfect Binary Tree**
   ```
   Tree:     1
           /   \
          2     3
         / \   / \
        4  5  6  7
   Result: true (perfectly balanced)
   ```

4. **Complete Binary Tree with Extra Leaf**
   ```
   Tree:     1
           /   \
          2     3
         /
        4
   Result: true (height diff = 1)
   ```

5. **Linear Tree (Linked List)**
   ```
   Tree: 1→2→3→4
   Result: false (unbalanced)
   ```

6. **Unbalanced at Leaf Level**
   ```
   Tree:       1
             /   \
            2     3
           /
          4
         /
        5
   Result: false (root has |2 - 1| = 1 ✓, but node 3 has |2 - 0| = 2 ✗)
   ```

---

## Practice Tips

1. **Understand Height First**: Master "Maximum Depth" problem before this
2. **Draw Balance Checks**: Visualize height differences at each node
3. **Trace Recursion**: Follow the call stack with small examples
4. **Sentinel Value**: Understand the -1 pattern for early termination
5. **Avoid O(n²)**: Don't calculate height separately for each node

---

## Detailed Explanation: How Values Get Increased

**Question**: "How does the height value increase if we just traverse?"

**Answer**: The height increases through the **return value** and **call stack unwinding**, not during traversal itself.

### Step-by-Step Breakdown

```java
return 1 + Math.max(leftHeight, rightHeight);
```

**What happens:**

1. **Leaf Node (Base Case)**:
   ```
   Node with no children:
   - leftHeight = 0 (left child is null)
   - rightHeight = 0 (right child is null)
   - Return: 1 + max(0, 0) = 1
   ```

2. **Internal Node**:
   ```
   Node with children:
   - First, recursively calculate leftHeight (goes deep into left subtree)
   - Then, recursively calculate rightHeight (goes deep into right subtree)
   - Finally, return 1 + max(leftHeight, rightHeight)
   
   The "+1" accounts for the CURRENT node
   ```

3. **Example Trace**:
   ```
   Tree:
           1
          / \
         2   3
        /
       4
   
   Execution Order (Post-order):
   
   Step 1: Visit Node 4 (leaf)
   - leftHeight = 0 (no left child)
   - rightHeight = 0 (no right child)
   - Return: 1 + max(0, 0) = 1
   - "Height from node 4 to farthest leaf below = 1"
   
   Step 2: Visit Node 2
   - leftHeight = 1 (returned from node 4)
   - rightHeight = 0 (no right child)
   - Return: 1 + max(1, 0) = 2
   - "Height from node 2 to farthest leaf below = 2"
   
   Step 3: Visit Node 3 (leaf)
   - leftHeight = 0
   - rightHeight = 0
   - Return: 1 + max(0, 0) = 1
   
   Step 4: Visit Node 1 (root)
   - leftHeight = 2 (returned from node 2)
   - rightHeight = 1 (returned from node 3)
   - Return: 1 + max(2, 1) = 3
   - "Height of entire tree = 3"
   ```

**Key Insight**: 
- The traversal goes **DOWN** (from root to leaves)
- The height calculation happens **UP** (from leaves to root)
- Each recursive call adds +1 as it returns
- The call stack "remembers" where to return to
- Values accumulate as the stack unwinds

**Visual Representation**:
```
Going DOWN (traversal):     Coming UP (height calculation):

    1                           1 (return 3)
   / \                         / \
  2   3         →            2   3 (return 1)
 /                          /
4                          4 (return 1)

Call stack unwinding:
4 returns 1 → 2 calculates 1+max(1,0)=2 → 2 returns 2 → 
1 calculates 1+max(2,1)=3 → 1 returns 3
```

---

## Summary

**Problem**: Check if every node in a binary tree has left and right subtree heights differing by at most 1.

**Solution**: 
- Use DFS to calculate heights bottom-up
- At each node, check if |leftHeight - rightHeight| <= 1
- Return -1 if unbalanced (for early termination)
- Return actual height otherwise

**Time**: O(n) | **Space**: O(h)

**Pattern**: Post-order DFS with sentinel value for validation

