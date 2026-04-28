# Valid Binary Search Tree

## Problem Description

**Difficulty**: Medium

Given the root of a binary tree, return `true` if it is a **valid binary search tree**, otherwise return `false`.

A valid binary search tree satisfies the following constraints:
- The **left subtree** of every node contains only nodes with keys **less than** the node's key.
- The **right subtree** of every node contains only nodes with keys **greater than** the node's key.
- Both the left and right subtrees are also **binary search trees**.

## Examples

### Example 1:
```
Input: root = [2,1,3]

Tree Structure:
      2
     / \
    1   3

Output: true
Explanation:
- Node 1 < 2 (in left subtree) ✓
- Node 3 > 2 (in right subtree) ✓
Valid BST!
```

### Example 2:
```
Input: root = [1,2,3]

Tree Structure:
      1
     / \
    2   3

Output: false
Explanation:
- Node 2 is in the left subtree of 1, but 2 > 1 ✗
NOT a valid BST.
```

## Constraints
- 1 <= The number of nodes in the tree <= 1000
- -1000 <= Node.val <= 1000

---

## Pattern Recognition

**Primary Pattern**: **Depth-First Search (DFS) with Valid Range Tracking**

**Why This Pattern?**
- We need to validate the BST property at **every node**, not just locally
- A node's valid range depends on all its **ancestors**, not just its immediate parent
- DFS naturally propagates constraints down the tree
- Each node must satisfy: `min < node.val < max`

**Key Insight**:
- Naively checking left < root < right only at each node is **NOT sufficient**
- Example: `[5, 4, 6, null, null, 3, 7]` — node 3 is in the right subtree of 5, but 3 < 5, violating BST property
- We must track a **valid interval [min, max]** for each node as we traverse

**Related Patterns**:
1. **Maximum Depth of Binary Tree** – Basic DFS recursion
2. **Balanced Binary Tree** – DFS with information propagated up
3. **Lowest Common Ancestor in BST** – Leverages BST ordering property
4. **Insert into BST / Delete from BST** – BST structure traversal

---

## Algorithm & Approach

### Core Insight

**The Valid Range Approach:**
- Start with root having interval `(-∞, +∞)`
- When going **left**, update the **upper bound** to `current node's value`
- When going **right**, update the **lower bound** to `current node's value`
- At every node, verify: `min < node.val < max`

**Why it works:**
```
Tree:       5
           / \
          4   6
             / \
            3   7

Traversal with intervals:
- Node 5: interval (-∞, +∞)   → 5 ∈ (-∞, +∞) ✓
- Node 4: interval (-∞, 5)    → 4 ∈ (-∞, 5)  ✓
- Node 6: interval (5, +∞)    → 6 ∈ (5, +∞)  ✓
- Node 3: interval (5, 6)     → 3 ∉ (5, 6)   ✗  NOT VALID!
- Node 7: interval (6, +∞)    → 7 ∈ (6, +∞)  ✓
```

---

### Step-by-Step Algorithm

#### **Approach 1: Recursive DFS with Min/Max Bounds (OPTIMAL)**

**Core Idea**:
- Pass `min` and `max` bounds down through recursion
- At each node, check `min < node.val < max`
- Update bounds as we go left or right

**Algorithm**
```
isValidBST(root):
    return validate(root, -infinity, +infinity)

validate(node, min, max):
    if node is null:
        return true
    
    if node.val <= min or node.val >= max:
        return false
    
    return validate(node.left, min, node.val)
        && validate(node.right, node.val, max)
```

**Code Implementation**
```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean validate(TreeNode node, long min, long max) {
        // Base case: null node is valid
        if (node == null) {
            return true;
        }

        // Current node's value must be strictly within (min, max)
        if (node.val <= min || node.val >= max) {
            return false;
        }

        // Left subtree: all values must be < node.val → update max
        // Right subtree: all values must be > node.val → update min
        return validate(node.left, min, node.val)
            && validate(node.right, node.val, max);
    }
}
```

**Example Walkthrough**

Input: root = [5,3,8,1,4,7,9]

```
Tree:
          5
         / \
        3   8
       / \ / \
      1  4 7  9

Call Stack Visualization:

validate(5, -∞, +∞)
├─ 5 ∈ (-∞, +∞) ✓
├─ validate(3, -∞, 5)
│  ├─ 3 ∈ (-∞, 5) ✓
│  ├─ validate(1, -∞, 3)
│  │  ├─ 1 ∈ (-∞, 3) ✓
│  │  ├─ validate(null, ...) → true
│  │  └─ validate(null, ...) → true
│  │  └─ return true
│  └─ validate(4, 3, 5)
│     ├─ 4 ∈ (3, 5) ✓
│     ├─ validate(null, ...) → true
│     └─ validate(null, ...) → true
│     └─ return true
│  └─ return true
└─ validate(8, 5, +∞)
   ├─ 8 ∈ (5, +∞) ✓
   ├─ validate(7, 5, 8)
   │  ├─ 7 ∈ (5, 8) ✓
   │  └─ return true
   └─ validate(9, 8, +∞)
      ├─ 9 ∈ (8, +∞) ✓
      └─ return true
   └─ return true

Final Result: true ✓
```

**Step-by-Step Trace:**

| Node | Min Bound | Max Bound | Check | Result |
|------|-----------|-----------|-------|--------|
| 5 | -∞ | +∞ | -∞ < 5 < +∞ | ✓ |
| 3 | -∞ | 5 | -∞ < 3 < 5 | ✓ |
| 1 | -∞ | 3 | -∞ < 1 < 3 | ✓ |
| 4 | 3 | 5 | 3 < 4 < 5 | ✓ |
| 8 | 5 | +∞ | 5 < 8 < +∞ | ✓ |
| 7 | 5 | 8 | 5 < 7 < 8 | ✓ |
| 9 | 8 | +∞ | 8 < 9 < +∞ | ✓ |

**Final Result: true**

**Why This Works:**
1. **Top-Down**: Constraints flow downward, accumulating as we go deeper
2. **Strict Bounds**: Ensures the **entire subtree** satisfies BST property
3. **O(n)**: Each node is visited exactly once
4. **Short-circuit**: Returns `false` immediately on first violation

**Complexity Analysis**
- **Time Complexity**: O(n) — every node visited once
- **Space Complexity**: O(h) — recursion stack (h = height of tree)
  - Best case (balanced): O(log n)
  - Worst case (skewed): O(n)

---

#### **Approach 2: Iterative DFS with Stack**

**Core Idea**:
- Simulate the recursion iteratively using a stack
- Each stack entry stores `(node, min, max)` tuple
- Process nodes one at a time, pushing children with updated bounds

**Code Implementation**
```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;

        // Stack stores: [node, min, max]
        Deque<Object[]> stack = new ArrayDeque<>();
        stack.push(new Object[]{root, Long.MIN_VALUE, Long.MAX_VALUE});

        while (!stack.isEmpty()) {
            Object[] entry = stack.pop();
            TreeNode node = (TreeNode) entry[0];
            long min = (long) entry[1];
            long max = (long) entry[2];

            if (node == null) continue;

            // Validate current node's value against bounds
            if (node.val <= min || node.val >= max) {
                return false;
            }

            // Push left child with updated max = node.val
            stack.push(new Object[]{node.left, min, (long) node.val});
            // Push right child with updated min = node.val
            stack.push(new Object[]{node.right, (long) node.val, max});
        }

        return true;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) — every node visited once
- **Space Complexity**: O(n) — stack can hold up to n entries in worst case

---

## Common Mistakes & Edge Cases

| Scenario | Issue | Fix |
|----------|-------|-----|
| Only check parent vs immediate child | Misses ancestor constraint violations | Use `min/max` bounds passed from root |
| Use `int` for bounds | Fails when node values equal `Integer.MIN_VALUE` or `Integer.MAX_VALUE` | Use `Long.MIN_VALUE` / `Long.MAX_VALUE` |
| Allow equal values | BST requires **strict** inequality | Use `<` and `>`, not `<=` and `>=` for children |
| Null root | Should return true | Handle with base case `if node == null return true` |

---

## Visual Summary

```
BST Valid Range Propagation:

                  5          → range: (-∞, +∞)
                 / \
                3   8        → left: (-∞, 5)   right: (5, +∞)
               / \ / \
              1  4 7  9      → 1:(-∞,3) 4:(3,5) 7:(5,8) 9:(8,+∞)

Each node must fit STRICTLY within its inherited range.
```

---

## Complexity Summary

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| Recursive DFS (min/max) | O(n) | O(h) | Clean, concise |
| Iterative DFS (stack) | O(n) | O(n) | No recursion stack risk |

---

## Key Takeaways

1. **Local check is not enough** — a node's validity depends on ALL ancestors
2. **Pass bounds down** — update max when going left, min when going right
3. **Use `long` bounds** — avoid edge cases with `Integer.MIN/MAX_VALUE`
4. **BST uses strict inequalities** — equal values are NOT allowed
5. **DFS (pre-order)** fits naturally — validate current node before exploring children

