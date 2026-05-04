# Serialize and Deserialize Binary Tree

## Problem Description

**Difficulty**: Hard

Implement an algorithm to **serialize** and **deserialize** a binary tree.

- **Serialization**: Converting an in-memory binary tree into a string so it can be stored or transmitted
- **Deserialization**: Reconstructing the original binary tree from that string

You just need to ensure the tree can be correctly round-tripped (serialize → deserialize → same tree). There is **no restriction** on the format you choose.

## Examples

### Example 1:
```
Input: root = [1,2,3,null,null,4,5]

Tree:
       1
      / \
     2   3
        / \
       4   5

Serialized: "1,2,N,N,3,4,N,N,5,N,N"
Deserialized: same tree structure

Output: [1,2,3,null,null,4,5]
```

### Example 2:
```
Input: root = []

Serialized: "N"
Deserialized: null (empty tree)

Output: []
```

## Constraints
- 0 <= The number of nodes in the tree <= 1000
- -1000 <= Node.val <= 1000

---

## Pattern Recognition

**Primary Pattern**: **Pre-Order DFS with Null Markers**

**Why This Pattern?**
- Pre-order traversal (current → left → right) naturally encodes the **root first**, which is exactly what we need during deserialization to reconstruct the tree top-down
- Explicitly encoding `null` nodes with a placeholder (e.g., `"N"`) preserves the **exact tree structure** — without it, multiple different trees could produce the same serialized string
- During deserialization, we process the tokens left-to-right in the same pre-order sequence, using `"N"` to know when to stop building a subtree

**Key Insight — Why null markers are essential:**
```
Without null markers:
  Tree A:  1        Tree B:    1
            \                 /
             2               2
  Both serialize to "1,2" — AMBIGUOUS!

With null markers:
  Tree A: "1,N,2,N,N"   Tree B: "1,2,N,N,N"
  Completely unambiguous ✓
```

**Related Patterns**:
1. **Binary Tree Preorder Traversal** — Core traversal used in serialization
2. **Construct Binary Tree from Preorder and Inorder** — Rebuilding a tree from a traversal sequence
3. **Binary Tree Level Order Traversal** — Alternative BFS-based serialization approach

---

## Algorithm & Approach

### Core Insight

**Why Pre-Order for Serialization?**

In pre-order traversal, the **root is always the first element**. When deserializing, we read the first token and immediately know it's the root — then we recursively build the left subtree, then the right subtree. This mirrors the serialization order perfectly.

```
Serialize (Pre-Order):           Deserialize (Pre-Order):
  visit current node               read next token
  recurse left                     if "N" → return null
  recurse right                    create node with token value
                                   node.left  = recurse()
                                   node.right = recurse()
                                   return node
```

**Decision Flow:**
```
serialize(node):
    ├─ If node is null → append "N,"
    └─ Else:
        ├─ append node.val + ","
        ├─ serialize(node.left)
        └─ serialize(node.right)

deserialize(tokens, index):
    ├─ Read token at index, advance index
    ├─ If token == "N" → return null
    └─ Else:
        ├─ node = new TreeNode(token value)
        ├─ node.left  = deserialize(tokens, index)
        ├─ node.right = deserialize(tokens, index)
        └─ return node
```

### Visual Understanding

```
Example 1: root = [1,2,3,null,null,4,5]

Tree:
       1
      / \
     2   3
        / \
       4   5

Serialization (Pre-Order traversal):

Visit 1   → append "1,"
Visit 2   → append "2,"
Visit null (left of 2)  → append "N,"
Visit null (right of 2) → append "N,"
Visit 3   → append "3,"
Visit 4   → append "4,"
Visit null (left of 4)  → append "N,"
Visit null (right of 4) → append "N,"
Visit 5   → append "5,"
Visit null (left of 5)  → append "N,"
Visit null (right of 5) → append "N,"

Result: "1,2,N,N,3,4,N,N,5,N,N"
```

```
Deserialization: tokens = ["1","2","N","N","3","4","N","N","5","N","N"]
                 index starts at 0

index=0: token="1" → create node(1)
  node(1).left  = recurse()
    index=1: token="2" → create node(2)
      node(2).left  = recurse()
        index=2: token="N" → return null
      node(2).right = recurse()
        index=3: token="N" → return null
      return node(2)
  node(1).right = recurse()
    index=4: token="3" → create node(3)
      node(3).left  = recurse()
        index=5: token="4" → create node(4)
          node(4).left  = recurse()
            index=6: token="N" → return null
          node(4).right = recurse()
            index=7: token="N" → return null
          return node(4)
      node(3).right = recurse()
        index=8: token="5" → create node(5)
          node(5).left  = recurse()
            index=9: token="N" → return null
          node(5).right = recurse()
            index=10: token="N" → return null
          return node(5)
      return node(3)
  return node(1)

Reconstructed tree:
       1
      / \
     2   3
        / \
       4   5   ✓
```

---

### Step-by-Step Algorithm

#### **Approach 1: Recursive Pre-Order DFS (CLEAN & INTUITIVE)**

**Core Idea**:
- **Serialize**: DFS pre-order, appending values separated by `","`, and `"N"` for null nodes
- **Deserialize**: Use an index pointer (wrapped in an array for mutability in Java) to consume tokens one by one in the same pre-order sequence

**Code Implementation**
```java
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeDFS(root, sb);
        return sb.toString();
    }

    private void serializeDFS(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("N,");
            return;
        }
        sb.append(node.val).append(",");  // Pre-order: current first
        serializeDFS(node.left,  sb);     // then left
        serializeDFS(node.right, sb);     // then right
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] tokens = data.split(",");
        int[] index = {0};               // index[0] acts as a mutable pointer
        return deserializeDFS(tokens, index);
    }

    private TreeNode deserializeDFS(String[] tokens, int[] index) {
        String token = tokens[index[0]++];  // Read current token, advance index

        if (token.equals("N")) {
            return null;                    // Null marker → no node here
        }

        TreeNode node = new TreeNode(Integer.parseInt(token));
        node.left  = deserializeDFS(tokens, index);  // Build left subtree
        node.right = deserializeDFS(tokens, index);  // Build right subtree
        return node;
    }
}
```

**Example Walkthrough**

Input: root = [1,2,3,null,null,4,5]

```
serialize(root):
  serializeDFS(1) → "1,"
    serializeDFS(2) → "2,"
      serializeDFS(null) → "N,"
      serializeDFS(null) → "N,"
    serializeDFS(3) → "3,"
      serializeDFS(4) → "4,"
        serializeDFS(null) → "N,"
        serializeDFS(null) → "N,"
      serializeDFS(5) → "5,"
        serializeDFS(null) → "N,"
        serializeDFS(null) → "N,"

Output string: "1,2,N,N,3,4,N,N,5,N,N,"
```

**Step-by-Step Trace for Deserialization:**

| index | Token | Action | Node Created |
|-------|-------|--------|--------------|
| 0 | "1" | Create node | node(1) |
| 1 | "2" | Create node (left of 1) | node(2) |
| 2 | "N" | Return null (left of 2) | — |
| 3 | "N" | Return null (right of 2) | — |
| 4 | "3" | Create node (right of 1) | node(3) |
| 5 | "4" | Create node (left of 3) | node(4) |
| 6 | "N" | Return null (left of 4) | — |
| 7 | "N" | Return null (right of 4) | — |
| 8 | "5" | Create node (right of 3) | node(5) |
| 9 | "N" | Return null (left of 5) | — |
| 10 | "N" | Return null (right of 5) | — |

**Final Reconstructed Tree: [1,2,3,null,null,4,5] ✓**

**Complexity Analysis**
- **Time Complexity**: O(n) for both serialize and deserialize
  - Every node (and its null children) is visited exactly once
- **Space Complexity**: O(n)
  - Serialized string stores n values + ~(n+1) null markers
  - Recursive call stack: O(h) where h is tree height
  - Best case (balanced): O(log n) stack, O(n) string
  - Worst case (skewed): O(n) stack, O(n) string

---

#### **Approach 2: BFS Level-Order (Queue-Based)**

**Core Idea**:
- **Serialize**: BFS level-order traversal; enqueue child nodes (including nulls) and record values
- **Deserialize**: BFS level-order reconstruction; use a queue of "parent" nodes and assign children left-to-right from the token list

**Why BFS?**
- Produces the familiar `[1,2,3,null,null,4,5]` NeetCode format
- Intuitive — mirrors how trees are commonly visualized level by level
- Slightly more code but very readable

**Code Implementation**
```java
public class Codec {

    // Encodes a tree to a single string (BFS / Level-Order).
    public String serialize(TreeNode root) {
        if (root == null) return "N";

        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("N,");
            } else {
                sb.append(node.val).append(",");
                queue.offer(node.left);   // Enqueue even if null
                queue.offer(node.right);  // Enqueue even if null
            }
        }

        return sb.toString();
    }

    // Decodes your encoded data to tree (BFS / Level-Order).
    public TreeNode deserialize(String data) {
        if (data.equals("N")) return null;

        String[] tokens = data.split(",");
        TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int i = 1;

        while (!queue.isEmpty() && i < tokens.length) {
            TreeNode node = queue.poll();

            // Assign left child
            if (!tokens[i].equals("N")) {
                node.left = new TreeNode(Integer.parseInt(tokens[i]));
                queue.offer(node.left);
            }
            i++;

            // Assign right child
            if (i < tokens.length && !tokens[i].equals("N")) {
                node.right = new TreeNode(Integer.parseInt(tokens[i]));
                queue.offer(node.right);
            }
            i++;
        }

        return root;
    }
}
```

**Example Walkthrough**

Input: root = [1,2,3,null,null,4,5]

```
serialize(root) — BFS:
  Queue: [1]
  Poll 1   → "1,"  Enqueue: [2, 3]
  Poll 2   → "2,"  Enqueue: [3, null, null]
  Poll 3   → "3,"  Enqueue: [null, null, 4, 5]
  Poll null → "N,"
  Poll null → "N,"
  Poll 4   → "4,"  Enqueue: [5, null, null]
  Poll 5   → "5,"  Enqueue: [null, null, null, null]
  ... (remaining nulls appended)

Output: "1,2,3,N,N,4,5,N,N,N,N,"

deserialize — BFS:
  root = node(1), queue = [node(1)], i=1
  Poll node(1): left=tokens[1]="2" → node(2), right=tokens[2]="3" → node(3)
  Queue: [node(2), node(3)], i=3
  Poll node(2): left=tokens[3]="N" → null, right=tokens[4]="N" → null
  Queue: [node(3)], i=5
  Poll node(3): left=tokens[5]="4" → node(4), right=tokens[6]="5" → node(5)
  Queue: [node(4), node(5)], i=7
  ...nulls consumed for node(4) and node(5)

Reconstructed: [1,2,3,null,null,4,5] ✓
```

**Complexity Analysis**
- **Time Complexity**: O(n) — each node visited once
- **Space Complexity**: O(n) — queue holds at most one full level of the tree

---

## Comparison of Approaches

| Aspect | Recursive DFS (Pre-Order) | Iterative BFS (Level-Order) |
|--------|--------------------------|------------------------------|
| **Time Complexity** | O(n) | O(n) |
| **Space Complexity** | O(n) | O(n) |
| **Code Simplicity** | ✅ Very clean | Moderate |
| **Stack Overflow Risk** | Possible (deep tree) | ✅ None |
| **Output Format** | Pre-order with nulls | ✅ Familiar level-order |
| **Null Handling** | Inline in DFS | Enqueue null nodes |
| **Preferred?** | ✅ Best for interviews | Good if level-order needed |

**Recommendation**: Use **Recursive DFS** in interviews — it's elegant, short, and the index pointer trick is impressive. Use **BFS** if you need the NeetCode / LeetCode standard level-order format.

---

## Key Takeaways

1. **Null Markers Are Non-Negotiable**
   - Without `"N"` placeholders, the serialization is ambiguous — multiple trees can produce the same string
   - Every leaf node has **two** null children that must be recorded

2. **Pre-Order + Null Markers = Unique Serialization**
   - Pre-order uniquely identifies a tree when null nodes are explicitly marked
   - The root is always the first token — perfect for top-down reconstruction

3. **Index Pointer Trick (for Java)**
   - Java passes primitives by value, so a plain `int` index can't be mutated across recursive calls
   - Using `int[] index = {0}` wraps it in an array, making it effectively mutable — a common Java interview trick

4. **Serialization ≠ Just Node Values**
   - Many students make the mistake of only recording non-null values
   - Structure (the shape of the tree) requires null markers to be preserved

5. **Any Consistent Format Works**
   - The problem does not require a specific format — only that `deserialize(serialize(root))` returns the same tree
   - Comma-separated with `"N"` for nulls is standard and simple

---

## Common Pitfalls

❌ **Mistake 1**: Not recording null nodes during serialization
```java
// WRONG: skips null nodes — structure is lost, multiple trees map to same string
private void serializeDFS(TreeNode node, StringBuilder sb) {
    if (node == null) return;  // ← should append "N," instead
    sb.append(node.val).append(",");
    serializeDFS(node.left,  sb);
    serializeDFS(node.right, sb);
}
```
✅ **Correct**: Explicitly record null nodes
```java
if (node == null) { sb.append("N,"); return; }
```

❌ **Mistake 2**: Using a plain `int` index in Java recursive deserialization
```java
// WRONG: index is passed by value — increments are lost after each recursive call
private TreeNode deserializeDFS(String[] tokens, int index) {
    String token = tokens[index++];  // index change is NOT visible to caller
    ...
}
```
✅ **Correct**: Wrap index in an array for mutability
```java
int[] index = {0};
private TreeNode deserializeDFS(String[] tokens, int[] index) {
    String token = tokens[index[0]++];  // ← mutation is visible across calls
    ...
}
```

❌ **Mistake 3**: Using `==` instead of `.equals()` for string comparison in Java
```java
// WRONG: compares references, not string values — may work sometimes but not always
if (token == "N") return null;
```
✅ **Correct**: Use `.equals()` for string comparison
```java
if (token.equals("N")) return null;
```

❌ **Mistake 4**: Forgetting to handle the empty tree during deserialization
```java
// WRONG: tokens[0] parsed directly without null check — crashes on empty tree
TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
```
✅ **Correct**: Check for the null marker first
```java
if (data.equals("N") || data.isEmpty()) return null;
```

---

## Related Problems

1. **Binary Tree Preorder Traversal** (Easy) — Core traversal used in serialization
2. **Construct Binary Tree from Preorder and Inorder** (Medium) — Rebuilding tree from traversal sequences
3. **Binary Tree Level Order Traversal** (Medium) — BFS approach to serialization
4. **Codec for N-ary Tree** (Hard) — Same concept extended to trees with multiple children
5. **Serialize and Deserialize BST** (Medium) — Simpler version leveraging BST properties
6. **Find Duplicate Subtrees** (Medium) — Subtree serialization as a building block

---

## Edge Cases to Consider

1. **Empty Tree**
   ```
   Input: root = []
   serialize(null) → "N"
   deserialize("N") → null
   Output: []
   ```

2. **Single Node**
   ```
   Input: root = [5]
   serialize → "5,N,N"
   deserialize → node(5) with null left and right
   Output: [5]
   ```

3. **Left-Skewed Tree**
   ```
   Input: root = [1,2,null,3]
   Tree:  1
         /
        2
       /
      3

   Serialized: "1,2,3,N,N,N,N"
   Each node contributes its value + two null markers at the leaves
   ```

4. **Right-Skewed Tree**
   ```
   Input: root = [1,null,2,null,3]
   Tree:  1
           \
            2
             \
              3

   Serialized: "1,N,2,N,3,N,N"
   Left nulls appear immediately for each node
   ```

5. **Negative Values**
   ```
   Input: root = [-1,-2,-3]
   Serialized: "-1,-2,N,N,-3,N,N"
   Integer.parseInt handles negative values correctly ✓
   ```

6. **Tree with All Same Values**
   ```
   Input: root = [1,1,1,1,1]
   Serialized: "1,1,1,N,N,1,N,N,1,N,N"
   Null markers distinguish structure even when all values are identical ✓
   ```

---

## Summary

**Problem**: Design `serialize` and `deserialize` functions to convert a binary tree to a string and back.

**Solution**:
- **Serialize**: Pre-order DFS — append `node.val` for real nodes and `"N"` for null nodes, separated by `","`
- **Deserialize**: Pre-order DFS — read tokens one by one using a shared index pointer; return `null` for `"N"` tokens, otherwise create a node and recursively build left then right subtrees

**Time**: O(n) | **Space**: O(n)

**Pattern**: Pre-Order DFS with Null Markers. The key insight is that null markers are **required** to preserve tree structure unambiguously, and pre-order traversal lets us reconstruct the tree top-down using the same token sequence.

