# Diameter of Binary Tree

## Problem Description

**Difficulty**: Easy

The **diameter of a binary tree** is defined as the **length of the longest path between any two nodes** within the tree. The path does not necessarily have to pass through the root.

The **length of a path** between two nodes in a binary tree is the **number of edges** between the nodes. Note that the path cannot include the same node twice.

Given the root of a binary tree `root`, return the **diameter** of the tree.

## Examples

### Example 1:
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
Explanation: 3 is the length of the path [1,2,3,5] or [5,3,2,4].
- Path [5,3,2,4]: 5→3→2→4 (3 edges)
- Path [1,2,3,5]: 1→2→3→5 (3 edges)
```

### Example 2:
```
Input: root = [1,2,3]

Tree Structure:
        1
       / \
      2   3

Output: 2
Explanation: The longest path is 2→1→3 (2 edges)
```

### Example 3:
```
Input: root = [1,2]

Tree Structure:
        1
       /
      2

Output: 1
Explanation: The longest path is 1→2 (1 edge)
```

## Constraints
- 1 <= number of nodes in the tree <= 100
- -100 <= Node.val <= 100

---

## Pattern Recognition

**Primary Pattern**: **Depth-First Search (DFS) with Global State**

**Why This Pattern?**
- Need to explore **all paths** between any two nodes
- Diameter at any node = leftHeight + rightHeight (sum of edges going left and right)
- Must track **maximum diameter found** across all nodes
- Perfect use case for **post-order DFS** (calculate children first, then parent)

**Key Insight**: 
- The diameter at any given node = height of left subtree + height of right subtree
- We need to track the **maximum diameter** seen across all nodes (global variable)
- While calculating diameter, we also calculate and return the **height** for parent nodes

**Related Patterns**:
1. **Maximum Depth of Binary Tree** - Calculates height
2. **Binary Tree Maximum Path Sum** - Similar global max tracking
3. **Balanced Binary Tree** - Uses height calculation
4. **Lowest Common Ancestor** - Path finding in trees

---

## Algorithm & Approach

### Core Insight

**The Diameter Problem Has Two Components:**
1. **Height**: Distance from current node to farthest leaf (needed for parent calculations)
2. **Diameter**: Longest path through current node = leftHeight + rightHeight

**Why it works:**
```
Tree:       1
           / \
          2   3
         / \
        4   5

At each node, longest path through it:
- Node 4: left(0) + right(0) = 0
- Node 5: left(0) + right(0) = 0
- Node 2: left(1) + right(1) = 2  ← This is the diameter!
- Node 3: left(0) + right(0) = 0
- Node 1: left(2) + right(0) = 2

Maximum diameter = 2 (path: 4→2→5)
```

**Critical Understanding:**
- Diameter might NOT pass through the root
- We must check diameter at **every node**
- Use a **global/class variable** to track the maximum
- Return **height** from recursion (needed by parent), update **diameter** as side effect

### Visual Understanding
```
        1
         \
          2
         / \
        3   4
       /
      5

Heights (edges to farthest leaf):
- Node 5: 0 (leaf node)
- Node 3: 1 (one edge to node 5)
- Node 4: 0 (leaf node)
- Node 2: 2 (two edges to node 5: 2→3→5)
- Node 1: 3 (three edges to node 5: 1→2→3→5)

Diameter at each node (leftHeight + rightHeight):
- Node 5: 0 + 0 = 0
- Node 3: 1 + 0 = 1 (left child 5 has height 1 from node 3's perspective)
- Node 4: 0 + 0 = 0
- Node 2: 2 + 1 = 3 ✓ (left subtree height 2, right subtree height 1)
- Node 1: 0 + 3 = 3

Maximum diameter = 3
```

### Step-by-Step Algorithm

#### **Approach 1: Recursive DFS with Global Max (OPTIMAL)**

**Core Idea**: 
- Use DFS to calculate height of each subtree
- At each node, calculate diameter as leftHeight + rightHeight
- Track maximum diameter in a global variable
- Return height to parent node

**Algorithm**
```
diameter = 0  (global variable)

height(node):
    if node is null:
        return 0
    
    leftHeight = height(node.left)
    rightHeight = height(node.right)
    
    // Update diameter (longest path through this node)
    diameter = max(diameter, leftHeight + rightHeight)
    
    // Return height for parent calculation
    return 1 + max(leftHeight, rightHeight)

diameterOfBinaryTree(root):
    diameter = 0
    height(root)
    return diameter
```

**Code Implementation**
```java
class Solution {
    private int diameter = 0;
    
    public int diameterOfBinaryTree(TreeNode root) {
        calculateHeight(root);
        return diameter;
    }
    
    private int calculateHeight(TreeNode node) {
        // Base case: null node has height 0
        if (node == null) {
            return 0;
        }
        
        // Recursively calculate height of left and right subtrees
        int leftHeight = calculateHeight(node.left);
        int rightHeight = calculateHeight(node.right);
        
        // Update diameter: longest path through current node
        // is the sum of left and right heights
        diameter = Math.max(diameter, leftHeight + rightHeight);
        
        // Return height of current node to parent
        // Height = 1 (current) + max of children heights
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
```

**Example Walkthrough**

Input: root = [1,null,2,3,4,5]

```
Tree:
        1
         \
          2
         / \
        3   4
       /
      5

Call Stack Visualization:

calculateHeight(1)
├─ calculateHeight(null) → return 0, diameter stays 0
├─ calculateHeight(2)
│  ├─ calculateHeight(3)
│  │  ├─ calculateHeight(5)
│  │  │  ├─ calculateHeight(null) → return 0
│  │  │  ├─ calculateHeight(null) → return 0
│  │  │  ├─ diameter = max(0, 0+0) = 0
│  │  │  └─ return 1 + max(0,0) = 1
│  │  ├─ calculateHeight(null) → return 0
│  │  ├─ diameter = max(0, 1+0) = 1
│  │  └─ return 1 + max(1,0) = 2
│  ├─ calculateHeight(4)
│  │  ├─ calculateHeight(null) → return 0
│  │  ├─ calculateHeight(null) → return 0
│  │  ├─ diameter = max(1, 0+0) = 1
│  │  └─ return 1 + max(0,0) = 1
│  ├─ diameter = max(1, 2+1) = 3  ← Maximum found here!
│  └─ return 1 + max(2,1) = 3
├─ diameter = max(3, 0+3) = 3
└─ return 1 + max(0,3) = 4

Final diameter: 3
```

**Step-by-Step Trace:**

| Call | Node | Left Height | Right Height | Diameter Calc | Update Diameter | Return Height |
|------|------|-------------|--------------|---------------|-----------------|---------------|
| 1 | 5 | 0 | 0 | 0+0=0 | max(0,0)=0 | 1 |
| 2 | 3 | 1 | 0 | 1+0=1 | max(0,1)=1 | 2 |
| 3 | 4 | 0 | 0 | 0+0=0 | max(1,0)=1 | 1 |
| 4 | 2 | 2 | 1 | 2+1=3 | max(1,3)=**3** | 3 |
| 5 | 1 | 0 | 3 | 0+3=3 | max(3,3)=3 | 4 |

**Final Result: 3**

**Why This Works:**
1. **Height Calculation**: Each node returns its height (edges to farthest leaf)
2. **Diameter Tracking**: At each node, calculate path through it (left + right)
3. **Global Max**: Keep updating maximum diameter found
4. **Single Pass**: O(n) - each node visited once

**Complexity Analysis**
- **Time Complexity**: O(n) - Visit each node exactly once
- **Space Complexity**: O(h) - Recursion stack depth (h = height of tree)
  - Best case (balanced tree): O(log n)
  - Worst case (skewed tree): O(n)

---

#### **Approach 2: Iterative Post-order with Stack**

**Core Idea**: 
Use iterative post-order traversal with a stack. For diameter calculation:
- Process children before parent (post-order)
- Store heights in a HashMap
- Track diameter globally as we process each node

**Why Post-order?**
- We need height information from children before processing parent
- Post-order visits: left → right → root
- Ensures children processed before parent

**Algorithm**
```
1. Use stack for post-order traversal
2. Use HashMap to store heights
3. For each node (in post-order):
   a. Get left and right heights from map
   b. Update diameter = max(diameter, leftHeight + rightHeight)
   c. Store current height in map
4. Return diameter
```

**Code Implementation**
```java
class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        
        int diameter = 0;
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
                    // Process current node
                    int leftHeight = heightMap.getOrDefault(peekNode.left, 0);
                    int rightHeight = heightMap.getOrDefault(peekNode.right, 0);
                    
                    // Update diameter
                    diameter = Math.max(diameter, leftHeight + rightHeight);
                    
                    // Store height for parent
                    heightMap.put(peekNode, 1 + Math.max(leftHeight, rightHeight));
                    
                    lastVisited = stack.pop();
                }
            }
        }
        
        return diameter;
    }
}
```

**Example Walkthrough**

Input: root = [1,null,2,3,4,5]

```
Tree:
        1
         \
          2
         / \
        3   4
       /
      5

Post-order Processing Order: 5 → 3 → 4 → 2 → 1

Step-by-step:

1. Process Node 5:
   - leftHeight = 0, rightHeight = 0
   - diameter = max(0, 0+0) = 0
   - heightMap[5] = 1

2. Process Node 3:
   - leftHeight = 1 (from map[5]), rightHeight = 0
   - diameter = max(0, 1+0) = 1
   - heightMap[3] = 2

3. Process Node 4:
   - leftHeight = 0, rightHeight = 0
   - diameter = max(1, 0+0) = 1
   - heightMap[4] = 1

4. Process Node 2:
   - leftHeight = 2 (from map[3]), rightHeight = 1 (from map[4])
   - diameter = max(1, 2+1) = 3 ✓
   - heightMap[2] = 3

5. Process Node 1:
   - leftHeight = 0, rightHeight = 3 (from map[2])
   - diameter = max(3, 0+3) = 3
   - heightMap[1] = 4

Result: diameter = 3
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
| **Preferred?** | ✅ Yes | Only if recursion not allowed |

**Recommendation**: Use **Recursive DFS** approach - cleaner, more intuitive, better space complexity.

---

## Key Takeaways

1. **Diameter ≠ Height**
   - Height: Max edges from node to any leaf below it
   - Diameter: Max edges between any two nodes through current node

2. **Global Variable Pattern**
   - When you need to track "best result" across all nodes
   - Return one value (height), track another (diameter)

3. **Post-order Traversal**
   - Calculate children first, then parent
   - Perfect for bottom-up calculations

4. **Path vs Node Count**
   - Diameter = number of **edges**, not nodes
   - Path with n nodes has n-1 edges

5. **Diameter Location**
   - Diameter doesn't have to pass through root
   - Must check every node (hence global max tracking)

---

## Common Pitfalls

❌ **Mistake 1**: Counting nodes instead of edges
```java
// WRONG: Counting nodes
diameter = max(diameter, leftHeight + rightHeight + 1);
```

✅ **Correct**: Count edges
```java
diameter = max(diameter, leftHeight + rightHeight);
```

❌ **Mistake 2**: Only checking diameter at root
```java
// WRONG: Only returns path through root
return heightLeft + heightRight;
```

✅ **Correct**: Track max at every node
```java
diameter = max(diameter, leftHeight + rightHeight);  // Global variable
```

❌ **Mistake 3**: Forgetting base case
```java
// WRONG: No null check
int left = calculateHeight(node.left);  // NPE if node is null
```

✅ **Correct**: Handle null
```java
if (node == null) return 0;
```

---

## Related Problems

1. **Maximum Depth of Binary Tree** (Easy) - Building block for diameter
2. **Binary Tree Maximum Path Sum** (Hard) - Similar pattern with global max
3. **Balanced Binary Tree** (Easy) - Also uses height calculation
4. **Longest Univalue Path** (Medium) - Similar diameter concept with constraint
5. **Binary Tree Cameras** (Hard) - Advanced tree DP

---

## Edge Cases to Consider

1. **Single Node Tree**
   ```
   Tree: [1]
   Diameter: 0 (no edges)
   ```

2. **Linear Tree (Linked List)**
   ```
   Tree: 1→2→3→4
   Diameter: 3 (entire path)
   ```

3. **Complete Binary Tree**
   ```
   Tree:     1
           /   \
          2     3
         / \   / \
        4  5  6  7
   Diameter: 4 (e.g., 4→2→1→3→6)
   ```

4. **Skewed Tree**
   ```
   Tree:   1
            \
             2
              \
               3
   Diameter: 2 (1→2→3)
   ```

---

## Practice Tips

1. **Understand Height First**: Master "Maximum Depth" problem first
2. **Draw It Out**: Visualize the tree and mark heights at each node
3. **Trace By Hand**: Follow the recursion with small examples
4. **Global Variable**: Understand why we need it (to track across all nodes)
5. **Edge Counting**: Remember it's edges, not nodes

---

## Summary

**Problem**: Find longest path (in edges) between any two nodes in a tree.

**Solution**: 
- Use DFS to calculate heights
- At each node, diameter through it = leftHeight + rightHeight
- Track maximum diameter globally
- Return height to parent

**Time**: O(n) | **Space**: O(h)

**Pattern**: Post-order DFS with global state tracking

