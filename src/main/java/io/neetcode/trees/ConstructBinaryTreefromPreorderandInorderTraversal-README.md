# Construct Binary Tree from Preorder and Inorder Traversal

## Problem Description

**Difficulty**: Medium

You are given two integer arrays `preorder` and `inorder`.

- `preorder` is the **preorder traversal** of a binary tree
- `inorder` is the **inorder traversal** of the same tree
- Both arrays are of the same size and consist of **unique values**

Rebuild the binary tree from the preorder and inorder traversals and return its **root**.

## Examples

### Example 1:
```
Input: preorder = [1,2,3,4], inorder = [2,1,3,4]

Tree Structure:
        1
       / \
      2   3
           \
            4

Output: [1,2,3,null,null,null,4]
```

### Example 2:
```
Input: preorder = [1], inorder = [1]

Tree Structure:
    1

Output: [1]
```

## Constraints
- 1 <= inorder.length <= 1000
- inorder.length == preorder.length
- -1000 <= preorder[i], inorder[i] <= 1000

---

## Pattern Recognition

**Primary Pattern**: **Recursive DFS with Divide & Conquer**

**Why This Pattern?**
- Preorder gives us the **root** at every level (first element = current root)
- Inorder lets us **split** the remaining nodes into left and right subtrees
- The two together fully determine the tree structure

**Key Insight**:
- Preorder: `[root | left subtree | right subtree]`
- Inorder:  `[left subtree | root | right subtree]`
- At every step, pick the next preorder element as the root, find it in inorder, split left and right, recurse

**Related Patterns**:
1. **Binary Tree Inorder / Preorder Traversal** – Core traversal techniques used here
2. **Construct Binary Tree from Inorder and Postorder** – Same idea with postorder
3. **Serialize and Deserialize Binary Tree** – Reconstruction of trees from encoded data

---

## Algorithm & Approach

### Core Insight

```
preorder = [1, 2, 3, 4]
inorder  = [2, 1, 3, 4]

Step 1: preorder[0] = 1 → root = 1
        Find 1 in inorder → index 1
        Left inorder:  [2]       (indices 0..0)
        Right inorder: [3, 4]    (indices 2..3)

Step 2 (left subtree):
        preorder[1] = 2 → root = 2
        Find 2 in inorder[0..0] → index 0
        Left inorder:  []   → no left child
        Right inorder: []   → no right child

Step 3 (right subtree):
        preorder[2] = 3 → root = 3
        Find 3 in inorder[2..3] → index 2
        Left inorder:  []        → no left child
        Right inorder: [4]       (index 3)

Step 4 (right-right subtree):
        preorder[3] = 4 → root = 4
        Leaf node

Final Tree:
        1
       / \
      2   3
           \
            4
```

---

### Step-by-Step Algorithm

#### **Approach 1: Recursive DFS with HashMap (OPTIMAL — O(n))**

**Core Idea**:
- Use a global pointer into `preorder` to always get the current root
- Use a HashMap of `value → inorder index` for O(1) lookup
- Recursively build left subtree from `[l, mid-1]` and right subtree from `[mid+1, r]` in the inorder range

**Algorithm**
```
buildTree(preorder, inorder):
    build a HashMap: inorder value → index
    preIdx = 0

    dfs(l, r):
        if l > r: return null
        rootVal = preorder[preIdx++]
        node = new TreeNode(rootVal)
        mid = map[rootVal]
        node.left  = dfs(l, mid - 1)
        node.right = dfs(mid + 1, r)
        return node

    return dfs(0, inorder.length - 1)
```

**Code Implementation**
```java
class Solution {
    private int preIdx = 0;
    private Map<Integer, Integer> inorderMap = new HashMap<>();

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        // Build map: value -> index in inorder
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        return dfs(preorder, 0, inorder.length - 1);
    }

    private TreeNode dfs(int[] preorder, int l, int r) {
        // Base case: no elements to construct the tree
        if (l > r) {
            return null;
        }

        // The next element in preorder is always the current root
        int rootVal = preorder[preIdx++];
        TreeNode node = new TreeNode(rootVal);

        // Find root's position in inorder to split left and right subtrees
        int mid = inorderMap.get(rootVal);

        // Build left subtree first (preorder: root, LEFT, right)
        node.left  = dfs(preorder, l, mid - 1);
        node.right = dfs(preorder, mid + 1, r);

        return node;
    }
}
```

**Example Walkthrough**

Input: preorder = [1,2,3,4], inorder = [2,1,3,4]

```
inorderMap = {2:0, 1:1, 3:2, 4:3}

dfs(0, 3)
  rootVal = preorder[0] = 1, preIdx = 1
  mid = inorderMap[1] = 1
  node.left  = dfs(0, 0)   ← left subtree [2]
    rootVal = preorder[1] = 2, preIdx = 2
    mid = inorderMap[2] = 0
    node.left  = dfs(0, -1) → null
    node.right = dfs(1, 0)  → null
    return TreeNode(2)
  node.right = dfs(2, 3)   ← right subtree [3,4]
    rootVal = preorder[2] = 3, preIdx = 3
    mid = inorderMap[3] = 2
    node.left  = dfs(2, 1)  → null
    node.right = dfs(3, 3)
      rootVal = preorder[3] = 4, preIdx = 4
      mid = inorderMap[4] = 3
      node.left  = dfs(3, 2)  → null
      node.right = dfs(4, 3)  → null
      return TreeNode(4)
    return TreeNode(3) with right = TreeNode(4)
  return TreeNode(1) with left=TreeNode(2), right=TreeNode(3)

Final Tree:
        1
       / \
      2   3
           \
            4
```

**Step-by-Step Trace:**

| preIdx | rootVal | inorder mid | Left range | Right range |
|--------|---------|-------------|------------|-------------|
| 0 | 1 | 1 | [0,0] | [2,3] |
| 1 | 2 | 0 | [0,-1] → null | [1,0] → null |
| 2 | 3 | 2 | [2,1] → null | [3,3] |
| 3 | 4 | 3 | [3,2] → null | [4,3] → null |

**Why This Works:**
1. **Preorder first element = root** at every recursive level
2. **Inorder split** divides the remaining nodes into exactly left and right subtrees
3. **HashMap** removes the need for linear search in inorder — O(1) per lookup
4. **Global preIdx** automatically advances to the next unprocessed root in preorder

**Complexity Analysis**
- **Time Complexity**: O(n) — each node is processed exactly once; HashMap gives O(1) lookups
- **Space Complexity**: O(n) — HashMap stores n entries; recursion stack O(h), h = height

---

#### **Approach 2: Recursive DFS without HashMap (Brute Force — O(n²))**

**Core Idea**:
- Same recursive approach but find the root's position in inorder by **linear search** each time
- Simpler to understand but O(n²) due to repeated linear scans

**Code Implementation**
```java
class Solution {
    private int preIdx = 0;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return dfs(preorder, inorder, 0, inorder.length - 1);
    }

    private TreeNode dfs(int[] preorder, int[] inorder, int l, int r) {
        if (l > r) {
            return null;
        }

        int rootVal = preorder[preIdx++];
        TreeNode node = new TreeNode(rootVal);

        // Linear search for root in inorder array
        int mid = l;
        while (inorder[mid] != rootVal) {
            mid++;
        }

        node.left  = dfs(preorder, inorder, l, mid - 1);
        node.right = dfs(preorder, inorder, mid + 1, r);

        return node;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n²) — for each of n nodes, linear search costs O(n) in the worst case
- **Space Complexity**: O(h) — recursion stack; no extra HashMap

---

## Common Mistakes & Edge Cases

| Scenario | Issue | Fix |
|----------|-------|-----|
| Forgetting to advance preIdx before recursing left | Wrong root picked for subtrees | Always increment preIdx right after reading the root value |
| Swapping left/right recursive calls | Left subtree built using right inorder range | Build left subtree (`[l, mid-1]`) before right (`[mid+1, r]`) |
| Linear search in inorder without HashMap | O(n²) time | Use HashMap for O(1) lookup |
| Single node tree | Should return that node with no children | `l > r` base case returns null correctly |
| Using the same inorderMap reference | HashMap is shared — correct for this approach | No issue; values are unique so lookup is always correct |

---

## Visual Summary

```
preorder = [root, LEFT_SUBTREE..., RIGHT_SUBTREE...]
inorder  = [LEFT_SUBTREE..., root, RIGHT_SUBTREE...]

At every recursion level:
  1. Take preorder[preIdx] as root
  2. Find root in inorder → index mid
  3. Left subtree  = everything left  of mid in inorder
  4. Right subtree = everything right of mid in inorder
  5. Recurse!

Example with preorder=[3,9,20,15,7], inorder=[9,3,15,20,7]:

        3
       / \
      9  20
        /  \
       15   7
```

---

## Complexity Summary

| Approach | Time | Space | Notes |
|----------|------|-------|-------|
| Recursive + HashMap | O(n) | O(n) | Optimal; O(1) inorder lookup |
| Recursive (linear search) | O(n²) | O(h) | Simple but slow for large inputs |

---

## Key Takeaways

1. **Preorder first element = current root** — exploit this at every recursion level
2. **Inorder root index = left/right split point** — everything left is left subtree, everything right is right subtree
3. **HashMap is essential for O(n)** — avoids repeated O(n) linear search in inorder array
4. **Build left before right** — preorder visits left subtree before right, so preIdx must advance in that order
5. **Unique values are required** — the approach relies on finding an exact match in inorder; duplicates would break it

