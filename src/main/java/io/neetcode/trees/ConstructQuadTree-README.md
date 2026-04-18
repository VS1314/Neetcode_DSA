# Construct Quad Tree

## Problem Description

**Difficulty**: Medium

Given an `n x n` matrix `grid` of `0`'s and `1`'s only, construct and return the root of a **Quad-Tree** representing the grid.

A Quad-Tree is a tree where each internal node has exactly **four children**. Each node has two attributes:
- `val`: `true` if the node represents a grid of all `1`'s, `false` for all `0`'s. (Doesn't matter for internal nodes.)
- `isLeaf`: `true` if the node is a leaf (uniform region), `false` if it has four children.

### Construction Rules
1. If the current sub-grid has **all the same value** → make it a **leaf node** with that value, four children = null.
2. If the current sub-grid has **mixed values** → make it an **internal node**, divide into 4 equal quadrants, and recurse.

```
Node class:
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;
}
```

## Examples

### Example 1:
```
Input: grid = [[0,1],[1,0]]

Grid:
  0 | 1
  -----
  1 | 0

Each 1x1 cell is its own quadrant with a unique value → all 4 children are leaves.

Output: [[0,1],[1,0],[1,1],[1,1],[1,0]]
         root  topL topR botL botR
```

### Example 2:
```
Input: grid = [
  [1,1,1,1,0,0,0,0],
  [1,1,1,1,0,0,0,0],
  [1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1],
  [1,1,1,1,0,0,0,0],
  [1,1,1,1,0,0,0,0],
  [1,1,1,1,0,0,0,0],
  [1,1,1,1,0,0,0,0]
]

8x8 grid divided into four 4x4 quadrants:
  topLeft     = all 1s  → leaf(true)
  topRight    = mixed   → internal node, divided further
  bottomLeft  = all 1s  → leaf(true)
  bottomRight = all 0s  → leaf(false)

Output: [[0,1],[1,1],[0,1],[1,1],[1,0],null,null,null,null,[1,0],[1,0],[1,1],[1,1]]
```

## Constraints
- `n == grid.length == grid[i].length`
- `n == 2ˣ` where `0 <= x <= 6` (so n ∈ {1, 2, 4, 8, 16, 32, 64})

---

## Pattern Recognition

**Primary Pattern**: **Divide and Conquer (Recursion)**

**Why This Pattern?**
- The problem naturally divides the grid into 4 equal sub-grids at each step
- Each sub-grid is an independent subproblem of the same type → recursion
- Base case: all values in the sub-grid are the same → leaf node

**Key Insight**: At each recursive call, pass `(row, col, size)` to describe the current sub-grid. Check uniformity, and if not uniform, split `size` in half and recurse into all 4 quadrants.

**Related Patterns**:
1. **Merge Sort** – Divide array into halves, process independently
2. **Segment Tree** – Recursive interval decomposition
3. **Matrix-based Divide & Conquer** – Strassen's matrix multiplication

---

## Algorithm & Approach

### Core Insight
A Quad-Tree is built **top-down**: start with the full grid, check if uniform. If yes → leaf. If no → divide into 4 quadrants and recurse on each.

### Visual Understanding
```
8x8 grid:
┌────────┬────────┐
│        │        │
│ topLeft│topRight│
│ (all 1)│ (mixed)│
├────────┼────────┤
│        │        │
│ botLeft│botRight│
│ (all 1)│ (all 0)│
└────────┴────────┘

topRight (mixed 4x4):
┌────┬────┐
│ TL │ TR │
│(0s)│(0s)│
├────┼────┤
│ BL │ BR │
│(1s)│(1s)│
└────┴────┘
```

---

#### **Approach 1: Recursive Divide and Conquer (OPTIMAL & RECOMMENDED)**

**Core Idea**: Define a helper `build(grid, row, col, size)`. Check if all values in the `size x size` sub-grid starting at `(row, col)` are identical. If yes → return leaf. If no → split `size/2` and recurse into 4 quadrants.

**Algorithm**
```
build(grid, row, col, size):
  1. Scan all cells in [row..row+size) x [col..col+size)
  2. If all same value:
       return new Node(val = that value, isLeaf = true)
  3. Else:
       half = size / 2
       node.topLeft     = build(grid, row,      col,      half)
       node.topRight    = build(grid, row,      col+half, half)
       node.bottomLeft  = build(grid, row+half, col,      half)
       node.bottomRight = build(grid, row+half, col+half, half)
       return node
```

**Code Implementation**
```java
public Node construct(int[][] grid) {
    return build(grid, 0, 0, grid.length);
}

private Node build(int[][] grid, int row, int col, int size) {
    // Check if all values in this sub-grid are the same
    boolean allSame = true;
    int firstVal = grid[row][col];
    for (int i = row; i < row + size && allSame; i++) {
        for (int j = col; j < col + size && allSame; j++) {
            if (grid[i][j] != firstVal) allSame = false;
        }
    }

    // If all same, this is a leaf node
    if (allSame) {
        return new Node(firstVal == 1, true);
    }

    // Otherwise, divide into 4 quadrants
    int half = size / 2;
    Node node = new Node(true, false);
    node.topLeft     = build(grid, row,        col,        half);
    node.topRight    = build(grid, row,        col + half, half);
    node.bottomLeft  = build(grid, row + half, col,        half);
    node.bottomRight = build(grid, row + half, col + half, half);
    return node;
}
```

**Example Walkthrough**

Input: grid = [[0,1],[1,0]]

```
build(grid, 0, 0, 2):
  Check [0..2) x [0..2): values = {0,1,1,0} → mixed
  half = 1
  topLeft     = build(0, 0, 1): grid[0][0]=0 → leaf(false)
  topRight    = build(0, 1, 1): grid[0][1]=1 → leaf(true)
  bottomLeft  = build(1, 0, 1): grid[1][0]=1 → leaf(true)
  bottomRight = build(1, 1, 1): grid[1][1]=0 → leaf(false)
  return internal node with those 4 children
```

**Complexity Analysis**
- **Time Complexity**: O(n² log n) – At each level we scan the sub-grid (O(n²) total per level), and there are O(log n) levels
- **Space Complexity**: O(log n) – Recursion stack depth = log₄(n²) = log n levels

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Divide & Conquer | Brute Force (no recursion) |
|-------------|-----------------|---------------------------|
| Sub-grid handling | ✅ Natural | ❌ Complex indexing |
| Code clarity | ✅ Clean | ❌ Messy |
| Time complexity | O(n² log n) ✓ | Same but harder to implement |
| Space complexity | O(log n) ✓ | O(1) stack but complex |
| Interview friendly | ✅ Yes | ❌ No |

**Winner**: **Recursive Divide & Conquer** ✅

---

## Critical Edge Cases & Gotchas

### 1. **1x1 Grid**
```java
Input: grid = [[1]]
Output: leaf node with val=true, isLeaf=true
Explanation: Single cell is always a leaf.
```

### 2. **Uniform Grid**
```java
Input: grid = [[1,1],[1,1]]
Output: single leaf node, val=true, isLeaf=true, no children
```

### 3. **4 Different Quadrants**
```java
Input: grid = [[0,1],[1,0]]
Output: root (internal) + 4 leaf children
```

### 4. **n is Always a Power of 2**
```
n ∈ {1, 2, 4, 8, 16, 32, 64}
→ Dividing by 2 always gives integer sizes — no boundary issues
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Off-by-one in Sub-grid Bounds**
```java
// WRONG - wrong upper bound
for (int i = row; i <= row + size; i++) {   // ❌ should be < not <=
```
**Fix**: Use `i < row + size`, `j < col + size`.

### ❌ **MISTAKE 2: Wrong Quadrant Starting Points**
```java
// WRONG - all quadrants start at same place!
node.topLeft     = build(grid, row, col, half);
node.topRight    = build(grid, row, col, half);   // ❌
node.bottomLeft  = build(grid, row, col, half);   // ❌
node.bottomRight = build(grid, row, col, half);   // ❌
```
**Fix**: Offset by `half` for right/bottom quadrants:
```java
node.topLeft     = build(grid, row,        col,        half);  // ✓
node.topRight    = build(grid, row,        col + half, half);  // ✓
node.bottomLeft  = build(grid, row + half, col,        half);  // ✓
node.bottomRight = build(grid, row + half, col + half, half);  // ✓
```

### ❌ **MISTAKE 3: Not Passing `size` — Using Grid Length Instead**
```java
// WRONG - always uses full grid size!
private Node build(int[][] grid, int row, int col) {
    int size = grid.length;   // ❌ Should shrink each recursion
```
**Fix**: Pass `size` as a parameter and halve it each recursion.

### ❌ **MISTAKE 4: Forgetting the Leaf Node Has No Children**
```java
// Leaf nodes implicitly have null children — just don't assign them.
// The Node constructor leaves them null by default. ✓
return new Node(firstVal == 1, true);
```

---

## Complexity Analysis

### Recursive Divide and Conquer

**Time Complexity: O(n² log n)**

| Level | Sub-grids | Sub-grid Size | Work per sub-grid | Total Work |
|-------|-----------|---------------|-------------------|------------|
| 0 (root) | 1 | n × n | O(n²) | O(n²) |
| 1 | 4 | n/2 × n/2 | O(n²/4) | O(n²) |
| 2 | 16 | n/4 × n/4 | O(n²/16) | O(n²) |
| ... | ... | ... | ... | ... |
| log n | n² | 1 × 1 | O(1) | O(n²) |
| **Total** | | | | **O(n² log n)** |

**Space Complexity: O(log n)**
- Recursion stack depth = number of levels = log₂(n)
- Each stack frame uses O(1) additional space

> **Note**: The space for the output tree itself is O(n²) in the worst case (every cell is a leaf), but that's output space, not auxiliary space.

---

## Visualization

### Quad-Tree Construction for 4x4 Uniform-Bottom Grid

```
Input:
  1 1 0 0
  1 1 0 0
  1 1 1 1
  1 1 1 1

build(0,0,4):
  Not uniform → split half=2

  ┌──────────────────────────────────────┐
  │ topLeft(0,0,2)   topRight(0,2,2)     │
  │  [1 1]             [0 0]             │
  │  [1 1] → leaf(1)   [0 0] → leaf(0)  │
  │                                      │
  │ botLeft(2,0,2)   botRight(2,2,2)     │
  │  [1 1]             [1 1]             │
  │  [1 1] → leaf(1)   [1 1] → leaf(1)  │
  └──────────────────────────────────────┘

Result tree:
           internal
          /  |  |  \
       L(1) L(0) L(1) L(1)
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Complexity | When to Use |
|----------|------|-------|-----------------|-------------|
| **Recursive Divide & Conquer** | O(n² log n) | O(log n) | ✅ **Simple** | **Always** ✅ |
| **Optimized with Prefix Sums** | O(n²) | O(n²) | Medium | Large n, performance critical |

### Prefix Sum Optimization (O(n²))
Instead of scanning the sub-grid at every level, precompute a 2D prefix sum matrix. The sum of any sub-rectangle can be computed in O(1) — if `sum == 0` → all 0s, if `sum == size*size` → all 1s, else mixed.

---

## Key Takeaways

1. **Quad-Tree = Divide & Conquer** – Split into 4 quadrants at each level
2. **Pass `(row, col, size)`** to describe the current sub-grid — never modify the original grid
3. **Base case**: uniform sub-grid → leaf node
4. **n is always a power of 2** → clean integer division, no edge cases
5. **Time O(n² log n)** due to scanning at each of log n levels
6. **Space O(log n)** for recursion stack (log n levels deep)

---

## Interview Tips

**What to say in an interview:**

> "I'll use divide and conquer. My helper takes `(row, col, size)` describing the current sub-grid. I scan all cells — if they're all the same, I return a leaf node. Otherwise I halve the size and recurse into the four quadrants: top-left, top-right, bottom-left, bottom-right. Time is O(n² log n) and space is O(log n) for the call stack."

**Key points to mention:**
1. **Describe sub-grids by `(row, col, size)`** — not by copying arrays
2. **Base case** = uniform region → leaf
3. **4 recursive calls** with offset `half` for row/col
4. **Mention prefix sum optimization** if asked about O(n²) solution

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Construct Quad Tree** | Medium | **Divide & Conquer** | **4-way split on 2D grid** ← This problem |
| Segment Tree Build | Medium | Divide & Conquer | 2-way split on 1D array |
| Count Complete Tree Nodes | Medium | Divide & Conquer | Binary tree, count nodes |
| Merge k Sorted Lists | Hard | Divide & Conquer | k-way merge |
| Matrix Block Sum | Medium | Prefix Sums | 2D prefix sum technique |

---

## Final Pattern Label

✅ **Divide and Conquer — Quad-Tree Construction**

**Remember:** Check uniformity → if uniform return leaf, else halve the size and recurse into all 4 quadrants with correct `(row, col)` offsets!

