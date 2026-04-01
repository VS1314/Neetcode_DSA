# Same Binary Tree

## Problem Description

**Difficulty**: Easy

Given the roots of two binary trees `p` and `q`, return `true` if the trees are **equivalent**, otherwise return `false`.

Two binary trees are considered **equivalent** if they:
1. Share the **exact same structure** (same shape)
2. Have the **same values** at corresponding nodes

## Examples

### Example 1:
```
Input: p = [1,2,3], q = [1,2,3]

Tree p:          Tree q:
    1                1
   / \              / \
  2   3            2   3

Output: true
Explanation: Both trees have identical structure and values.
```

### Example 2:
```
Input: p = [4,7], q = [4,null,7]

Tree p:          Tree q:
    4                4
   /                  \
  7                    7

Output: false
Explanation: Structures differ - p has left child, q has right child.
```

### Example 3:
```
Input: p = [1,2,3], q = [1,3,2]

Tree p:          Tree q:
    1                1
   / \              / \
  2   3            3   2

Output: false
Explanation: Structures are same but values at corresponding positions differ.
- p's left child is 2, q's left child is 3 ✗
```

### Example 4:
```
Input: p = [1,2], q = [1,null,2]

Tree p:          Tree q:
    1                1
   /                  \
  2                    2

Output: false
Explanation: Different structures (left vs right child).
```

## Constraints
- 0 <= The number of nodes in both trees <= 100
- -100 <= Node.val <= 100

---

## Pattern Recognition

**Primary Pattern**: **Simultaneous Tree Traversal with Comparison**

**Why This Pattern?**
- Need to compare **corresponding nodes** in both trees
- Must traverse **both trees in parallel** (same order)
- Check three conditions at each step: structure match, value match, null handling
- Perfect use case for **recursive DFS** (compare current, then compare subtrees)

**Key Insight**: 
- Two trees are same if:
  1. Both are null → true (base case)
  2. One is null, other isn't → false (structure mismatch)
  3. Values differ → false (value mismatch)
  4. Values match AND left subtrees match AND right subtrees match → true

**Related Patterns**:
1. **Symmetric Tree** - Similar comparison logic but compares tree with its mirror
2. **Subtree of Another Tree** - Similar tree matching logic
3. **Merge Two Binary Trees** - Simultaneous traversal of two trees
4. **Tree Traversal** - DFS/BFS patterns

---

## Algorithm & Approach

### Core Insight

**The Comparison Problem Has Three Checks:**
1. **Both null**: Trees are same (base case - empty trees match)
2. **One null**: Trees differ in structure (mismatch)
3. **Both non-null**: Check if values match AND recursively check subtrees

**Why it works:**
```
Compare p and q:
1. If both null → Same ✓
2. If only one null → Different ✗
3. If p.val ≠ q.val → Different ✗
4. If values match → Check left subtrees AND right subtrees

Recursive nature ensures all corresponding nodes are checked
```

**Decision Flow:**

```
Step 1: Check if both null
├─ YES → return true ✓
└─ NO → Continue to Step 2

Step 2: Check if one is null
├─ YES → return false ✗ (structure mismatch)
└─ NO → Continue to Step 3

Step 3: Check if values differ
├─ YES → return false ✗ (value mismatch)
└─ NO → Continue to Step 4

Step 4: Check subtrees recursively
└─ return isSame(left) AND isSame(right)
```

**Complete Truth Table:**

| Case | Tree p | Tree q | Values Match? | Left Subtrees? | Right Subtrees? | Final Result |
|------|--------|--------|---------------|----------------|-----------------|--------------|
| 1 | `null` | `null` | - | - | - | ✅ `true` |
| 2 | `null` | `node` | - | - | - | ❌ `false` |
| 3 | `node` | `null` | - | - | - | ❌ `false` |
| 4 | `node` | `node` | ❌ No | - | - | ❌ `false` |
| 5 | `node` | `node` | ✅ Yes | ❌ `false` | - | ❌ `false` |
| 6 | `node` | `node` | ✅ Yes | ✅ `true` | ❌ `false` | ❌ `false` |
| 7 | `node` | `node` | ✅ Yes | ✅ `true` | ✅ `true` | ✅ `true` |

**Key:** Only Case 7 returns `true` - when ALL conditions are met!

**Critical Understanding:**
- Must check **all three conditions**: both null, one null, values match
- Use **AND logic**: ALL conditions must be true for trees to be same
- **Short-circuit evaluation**: If current nodes differ, no need to check subtrees

### Visual Understanding
```
Example: p = [1,2,3], q = [1,2,3]

    p: 1         q: 1
      / \          / \
     2   3        2   3

Step-by-step comparison:
1. Compare roots: p(1) == q(1) ✓
2. Compare left subtrees:
   - p(2) == q(2) ✓
   - p(2).left == null, q(2).left == null ✓
   - p(2).right == null, q(2).right == null ✓
3. Compare right subtrees:
   - p(3) == q(3) ✓
   - p(3).left == null, q(3).left == null ✓
   - p(3).right == null, q(3).right == null ✓

Result: true
```

**Failure Example:**
```
Example: p = [1,2], q = [1,null,2]

    p: 1         q: 1
      /            \
     2              2

Comparison:
1. Compare roots: p(1) == q(1) ✓
2. Compare left subtrees:
   - p.left = node(2), q.left = null ✗
   
Result: false (structure mismatch)
```

### Step-by-Step Algorithm

#### **Approach 1: Recursive DFS (OPTIMAL)**

**Core Idea**: 
- Traverse both trees simultaneously using recursion
- At each step, check if current nodes match (null handling + value check)
- Recursively verify left and right subtrees match

**Algorithm**
```
isSameTree(p, q):
    // Case 1: Both null → trees are same
    if p is null AND q is null:
        return true
    
    // Case 2: One null, other not → different structure
    if p is null OR q is null:
        return false
    
    // Case 3: Values differ → not same
    if p.val != q.val:
        return false
    
    // Case 4: Values match → check subtrees recursively
    return isSameTree(p.left, q.left) AND isSameTree(p.right, q.right)
```

**Code Implementation**
```java
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // Case 1: Both trees are empty - they are the same
        if (p == null && q == null) {
            return true;
        }
        
        // Case 2: One tree is empty, the other is not - different structure
        if (p == null || q == null) {
            return false;
        }
        
        // Case 3: Values at current nodes differ - not the same
        if (p.val != q.val) {
            return false;
        }
        
        // Case 4: Values match - recursively check left and right subtrees
        // Trees are same only if BOTH left and right subtrees are same
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```

**Alternative - Concise Version**
```java
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // Both null → same
        if (p == null && q == null) return true;
        
        // One null or values differ → not same
        if (p == null || q == null || p.val != q.val) return false;
        
        // Check both subtrees
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```

**Example Walkthrough**

Input: p = [1,2,3], q = [1,2,3]

```
Tree p:          Tree q:
    1                1
   / \              / \
  2   3            2   3

Call Stack Visualization:

isSameTree(p:1, q:1)
├─ p(1) != null, q(1) != null ✓
├─ p.val(1) == q.val(1) ✓
├─ isSameTree(p:2, q:2)
│  ├─ p(2) != null, q(2) != null ✓
│  ├─ p.val(2) == q.val(2) ✓
│  ├─ isSameTree(p:null, q:null)
│  │  └─ both null → return true
│  ├─ isSameTree(p:null, q:null)
│  │  └─ both null → return true
│  └─ return true && true = true
├─ isSameTree(p:3, q:3)
│  ├─ p(3) != null, q(3) != null ✓
│  ├─ p.val(3) == q.val(3) ✓
│  ├─ isSameTree(p:null, q:null)
│  │  └─ both null → return true
│  ├─ isSameTree(p:null, q:null)
│  │  └─ both null → return true
│  └─ return true && true = true
└─ return true && true = true

Final Result: true
```

**Step-by-Step Trace:**

| Call | p | q | Both Null? | One Null? | Values Match? | Result |
|------|---|---|------------|-----------|---------------|--------|
| 1 | 1 | 1 | No | No | Yes (1==1) | Continue |
| 2 | 2 | 2 | No | No | Yes (2==2) | Continue |
| 3 | null | null | Yes | - | - | **true** |
| 4 | null | null | Yes | - | - | **true** |
| 5 | 2's result | - | - | - | - | true && true = **true** |
| 6 | 3 | 3 | No | No | Yes (3==3) | Continue |
| 7 | null | null | Yes | - | - | **true** |
| 8 | null | null | Yes | - | - | **true** |
| 9 | 3's result | - | - | - | - | true && true = **true** |
| 10 | 1's result | - | - | - | - | true && true = **true** |

**Final Result: true**

**Failure Case Walkthrough**

Input: p = [4,7], q = [4,null,7]

```
Tree p:          Tree q:
    4                4
   /                  \
  7                    7

Call Stack:

isSameTree(p:4, q:4)
├─ p(4) != null, q(4) != null ✓
├─ p.val(4) == q.val(4) ✓
├─ isSameTree(p:7, q:null)
│  ├─ p(7) != null, but q == null
│  └─ return false ✗
└─ return false (short-circuit)

Final Result: false
```

**Why This Works:**
1. **Base Case**: Both null → matching empty subtrees
2. **Structure Check**: One null → different structure
3. **Value Check**: Different values → not same
4. **Recursive Check**: Verify all subtrees match

**Complexity Analysis**
- **Time Complexity**: O(min(n, m)) - Visit nodes until mismatch found
  - Best case: O(1) - roots differ
  - Worst case: O(n) - all nodes match (n = min number of nodes)
- **Space Complexity**: O(min(h₁, h₂)) - Recursion stack depth
  - Best case (balanced): O(log n)
  - Worst case (skewed): O(n)

---

#### **Approach 2: Iterative BFS with Queue**

**Core Idea**: 
Use level-order traversal (BFS) to compare trees:
- Use a queue to store pairs of nodes from both trees
- Process pairs one by one, checking if they match
- Add children pairs to queue for further comparison

**Why BFS?**
- Compare trees level by level
- Can detect mismatches early (level-by-level comparison)
- Iterative approach avoids recursion overhead

**Algorithm**
```
1. Use queue to store pairs (nodeP, nodeQ)
2. Start with roots: queue.add((p, q))
3. While queue not empty:
   a. Dequeue pair (nodeP, nodeQ)
   b. If both null → continue (matching empty nodes)
   c. If one null → return false (structure mismatch)
   d. If values differ → return false (value mismatch)
   e. Add children pairs to queue: (nodeP.left, nodeQ.left), (nodeP.right, nodeQ.right)
4. If queue empties without finding mismatch → return true
```

**Code Implementation**
```java
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // Use queue to store pairs of nodes to compare
        Queue<TreeNode[]> queue = new LinkedList<>();
        queue.offer(new TreeNode[]{p, q});
        
        while (!queue.isEmpty()) {
            TreeNode[] pair = queue.poll();
            TreeNode nodeP = pair[0];
            TreeNode nodeQ = pair[1];
            
            // Both null - continue to next pair
            if (nodeP == null && nodeQ == null) {
                continue;
            }
            
            // One is null - structure mismatch
            if (nodeP == null || nodeQ == null) {
                return false;
            }
            
            // Values differ - not same
            if (nodeP.val != nodeQ.val) {
                return false;
            }
            
            // Add children pairs for comparison
            queue.offer(new TreeNode[]{nodeP.left, nodeQ.left});
            queue.offer(new TreeNode[]{nodeP.right, nodeQ.right});
        }
        
        return true;
    }
}
```

**Example Walkthrough**

Input: p = [1,2,3], q = [1,2,3]

```
Tree p:          Tree q:
    1                1
   / \              / \
  2   3            2   3

BFS Processing:

Initial: queue = [(1,1)]

Step 1: Process (1,1)
- Both non-null ✓
- Values: 1 == 1 ✓
- Add pairs: (2,2), (3,3)
- queue = [(2,2), (3,3)]

Step 2: Process (2,2)
- Both non-null ✓
- Values: 2 == 2 ✓
- Add pairs: (null,null), (null,null)
- queue = [(3,3), (null,null), (null,null)]

Step 3: Process (3,3)
- Both non-null ✓
- Values: 3 == 3 ✓
- Add pairs: (null,null), (null,null)
- queue = [(null,null), (null,null), (null,null), (null,null)]

Step 4-7: Process all (null,null) pairs
- Both null → continue

Queue empty → return true
```

**Complexity Analysis**
- **Time Complexity**: O(min(n, m)) - Process nodes until mismatch
- **Space Complexity**: O(min(n, m)) - Queue stores node pairs
  - In worst case (complete tree): O(n/2) = O(n) for last level

---

## Comparison of Approaches

| Aspect | Recursive DFS | Iterative BFS |
|--------|---------------|---------------|
| **Time Complexity** | O(min(n,m)) | O(min(n,m)) |
| **Space Complexity** | O(h) | O(n) |
| **Code Simplicity** | Very Simple | Moderate |
| **Intuition** | Natural (recursive comparison) | Requires queue management |
| **Early Termination** | Yes | Yes |
| **Traversal Order** | Depth-first | Level-by-level |
| **Preferred?** | ✅ Yes | Alternative approach |

**Recommendation**: Use **Recursive DFS** approach - cleaner, more intuitive, better space complexity.

---

## Key Takeaways

1. **Three Conditions to Check**
   - Both null → same (base case)
   - One null → different structure
   - Values differ → not same
   - All match → check subtrees

2. **Simultaneous Traversal**
   - Traverse both trees in parallel
   - Compare corresponding nodes at each step
   - Use same traversal order for both trees

3. **AND Logic**
   - Trees are same only if ALL checks pass
   - Current nodes match AND left subtrees match AND right subtrees match
   - One failure → entire comparison fails

4. **Early Termination**
   - Stop as soon as mismatch found
   - No need to check remaining nodes
   - Improves average-case performance

5. **Structure vs Value**
   - Structure: Tree shape (where nodes are positioned)
   - Value: Data stored in nodes
   - Both must match for trees to be same

---

## Common Pitfalls

❌ **Mistake 1**: Not handling null cases properly
```java
// WRONG: Doesn't check if one is null
if (p.val != q.val) return false;  // NPE if p or q is null!
```

✅ **Correct**: Check null cases first
```java
if (p == null && q == null) return true;
if (p == null || q == null) return false;  // One is null
if (p.val != q.val) return false;
```

❌ **Mistake 2**: Using OR instead of AND for subtrees
```java
// WRONG: Trees are same if EITHER subtree matches
return isSameTree(p.left, q.left) || isSameTree(p.right, q.right);
```

✅ **Correct**: Both subtrees must match
```java
return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
```

❌ **Mistake 3**: Only comparing values, ignoring structure
```java
// WRONG: Only checks values, not positions
if (p.val == q.val) {
    return true;
}
```

✅ **Correct**: Check values at corresponding positions
```java
if (p.val != q.val) return false;
// Must also check left and right subtrees match
return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
```

❌ **Mistake 4**: Incorrect null comparison
```java
// WRONG: Incorrect logic
if (p == null || q == null) {
    return p == q;  // Comparing references, not checking equality
}
```

✅ **Correct**: Proper null handling
```java
if (p == null && q == null) return true;   // Both null → same
if (p == null || q == null) return false;  // One null → different
```

---

## Related Problems

1. **Symmetric Tree** (Easy) - Check if tree is mirror of itself
2. **Subtree of Another Tree** (Easy) - Check if one tree is subtree of another
3. **Merge Two Binary Trees** (Easy) - Combine two trees
4. **Leaf-Similar Trees** (Easy) - Compare leaf sequences
5. **Flip Equivalent Binary Trees** (Medium) - Trees same with allowed flips
6. **Binary Tree Paths** (Easy) - Find all root-to-leaf paths

---

## Edge Cases to Consider

1. **Both Trees Empty**
   ```
   p = [], q = []
   Result: true (two empty trees are same)
   ```

2. **One Tree Empty**
   ```
   p = [1], q = []
   Result: false (different structure)
   ```

3. **Single Node - Same Value**
   ```
   p = [1], q = [1]
   Result: true
   ```

4. **Single Node - Different Value**
   ```
   p = [1], q = [2]
   Result: false
   ```

5. **Same Structure, Different Values**
   ```
   p = [1,2,3], q = [1,2,4]
   Result: false (right child differs)
   ```

6. **Different Structure, Same Values**
   ```
   p = [1,2], q = [1,null,2]
   Result: false (left vs right child)
   ```

7. **Mirror Trees**
   ```
   p = [1,2,3], q = [1,3,2]
   Result: false (mirrored structure)
   ```

8. **Identical Trees**
   ```
   p = [1,2,3,4,5], q = [1,2,3,4,5]
   Result: true
   ```

---

## Practice Tips

1. **Draw Both Trees**: Visualize the trees side by side
2. **Trace Recursion**: Follow the comparison step by step
3. **Test Null Cases**: Always test with null nodes
4. **Structure First**: Check if structures match before comparing values
5. **Use AND Logic**: Remember both subtrees must match

---

## Detailed Explanation: Why AND Logic?

**Question**: "Why do we use AND (&&) for combining left and right subtree comparisons?"

**Answer**: Trees are same **only if ALL parts match** - current node, left subtree, AND right subtree.

### Truth Table Explanation

```
For trees to be same:
- Current nodes must match ✓
- Left subtrees must match ✓
- Right subtrees must match ✓

If ANY check fails → trees are different
```

**Example:**
```
p:  1          q:  1
   / \            / \
  2   3          2   4

Current: 1 == 1 ✓
Left: 2 == 2 ✓
Right: 3 != 4 ✗

Result: true && true && false = FALSE

Even though roots and left subtrees match,
RIGHT subtree differs → trees are NOT same
```

**Why not OR?**
```
Using OR would mean:
"Trees are same if left subtree matches OR right subtree matches"

This is WRONG because:
p:  1          q:  1
   / \            / \
  2   9          9   3

Left: 2 != 9 ✗
Right: 9 != 3 ✗

With OR: false || false = false ✓ (works here)

But consider:
p:  1          q:  1
   / \            / \
  2   3          2   9

Left: 2 == 2 ✓
Right: 3 != 9 ✗

With OR: true || false = true ✗ (WRONG! Trees are different)
```

**Correct Logic - AND:**
```java
return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);

// Both must be true:
// - true && true = true ✓
// - true && false = false ✓
// - false && true = false ✓
// - false && false = false ✓
```

---

## Summary

**Problem**: Determine if two binary trees have identical structure and values.

**Solution**: 
- Use recursive DFS to traverse both trees simultaneously
- At each step: check null cases, compare values, recurse on subtrees
- Return true only if ALL checks pass (AND logic)

**Time**: O(min(n,m)) | **Space**: O(h)

**Pattern**: Simultaneous tree traversal with comparison logic

