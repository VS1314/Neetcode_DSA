# Range Sum Query 2D - Immutable

## Problem Description

**Difficulty**: Medium

You are given a 2D matrix `matrix`. Handle multiple queries of the following type:

Calculate the **sum of elements** inside the rectangle defined by its upper left corner `(row1, col1)` and lower right corner `(row2, col2)`.

Implement the `NumMatrix` class:
- `NumMatrix(int[][] matrix)` — Initializes the object with the integer matrix.
- `int sumRegion(int row1, int col1, int row2, int col2)` — Returns the sum of elements inside the given rectangle.

`sumRegion` must work in **O(1) time**.

## Examples

### Example 1:
```
Input:
matrix =
  3  0  1  4  2
  5  6  3  2  1
  1  2  0  1  5
  4  1  0  1  7
  1  0  3  0  5

sumRegion(2, 1, 4, 3) → 8   (red rectangle)
sumRegion(1, 1, 2, 2) → 11  (green rectangle)
sumRegion(1, 2, 2, 4) → 12  (blue rectangle)
```

## Constraints
- m == matrix.length
- n == matrix[i].length
- 1 <= m, n <= 200
- -10,000 <= matrix[i][j] <= 10,000
- 0 <= row1 <= row2 < m
- 0 <= col1 <= col2 < n
- At most 10,000 calls to sumRegion

---

## Pattern Recognition

**Primary Pattern**: **2D Prefix Sum (Cumulative Area Sum)**

**Why This Pattern?**
- Matrix is immutable — precomputation at construction time is safe
- Multiple queries are made — O(m×n) per query would be 10,000 × 40,000 = too slow
- We need O(1) per query → precompute all rectangle sums from `(0,0)`
- Any rectangle sum can then be derived using inclusion-exclusion in O(1)

**Key Insight**: Build a `prefix` array where `prefix[r][c]` = sum of all elements from `(0,0)` to `(r-1, c-1)` inclusive. Then any rectangle query is just 4 array lookups and arithmetic.

**Related Patterns**:
1. **1D Prefix Sum** — Simpler version: `prefix[i]` = sum from index 0 to i-1
2. **Subarray Sum = K** — Uses prefix sum with HashMap
3. **Range Sum Query Mutable** — Same idea but with Fenwick Tree for updates
4. **Maximum Subarray** — Kadane extends prefix sum thinking

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**

```
Brute force: for each query, loop over all cells in the rectangle
  → O(m × n) per query
  → 10,000 queries × 40,000 cells = 400,000,000 operations ❌

Prefix Sum: precompute once O(m × n), then each query is O(1) ✓
```

**The Big Idea — Think in Areas:**

```
prefix[r][c] = total sum of rectangle from (0,0) to (r-1, c-1)

Visually:
  (0,0)─────────(0,c-1)
    |                |
    |  prefix[r][c]  |
    |                |
  (r-1,0)──────(r-1,c-1)
```

### Visual Understanding
```
Building prefix[r][c] at each cell:

+─────────+─────+
│    A    │  B  │
+─────────+─────+
│    C    │  X  │
+─────────+─────+

prefix[r][c] = X + A + B + C
             = current cell + top area + left area - top-left (counted twice)
             = matrix[r-1][c-1] + prefix[r-1][c] + prefix[r][c-1] - prefix[r-1][c-1]
```

**Why subtract `prefix[r-1][c-1]`?**
Area `A` is included in both `prefix[r-1][c]` (top) and `prefix[r][c-1]` (left), so it gets counted twice. We subtract it once to fix that.

**For sumRegion(row1, col1, row2, col2):**
```
+──────────+──────────+
│  REMOVE  │  REMOVE  │
│   top    │  top-mid │
+──────────+──────────+
│  REMOVE  │   WANT   │
│   left   │          │
+──────────+──────────+

sum = Big − Top − Left + TopLeft
    = prefix[row2+1][col2+1]
    - prefix[row1][col2+1]
    - prefix[row2+1][col1]
    + prefix[row1][col1]
```

**Why add `prefix[row1][col1]` back?** The top-left corner was removed twice (once in "top", once in "left"), so we add it back once.

### Step-by-Step Algorithm

---

#### **Approach 1: (m+1)×(n+1) Prefix Matrix — OPTIMAL & RECOMMENDED**

**Core Idea**:
- Create a `prefix` array of size `(m+1) × (n+1)` — the extra row/column acts as a zero-padding border, eliminating all boundary checks
- Fill prefix using: `prefix[r][c] = matrix[r-1][c-1] + prefix[r-1][c] + prefix[r][c-1] - prefix[r-1][c-1]`
- Answer each query using: `prefix[row2+1][col2+1] - prefix[row1][col2+1] - prefix[row2+1][col1] + prefix[row1][col1]`

**Memory Trick**:
```
Building:  Current + Top + Left − Diagonal
Query:     Big     − Top − Left + TopLeft
```

**Code Implementation**
```java
class NumMatrix {
    private int[][] prefix;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        prefix = new int[m + 1][n + 1];

        for (int r = 1; r <= m; r++) {
            for (int c = 1; c <= n; c++) {
                prefix[r][c] = matrix[r - 1][c - 1]
                              + prefix[r - 1][c]
                              + prefix[r][c - 1]
                              - prefix[r - 1][c - 1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return prefix[row2 + 1][col2 + 1]
             - prefix[row1][col2 + 1]
             - prefix[row2 + 1][col1]
             + prefix[row1][col1];
    }
}
```

**Example Walkthrough**

Input matrix:
```
3  0  1  4  2
5  6  3  2  1
1  2  0  1  5
4  1  0  1  7
1  0  3  0  5
```

Built prefix (size 6×6):
```
 0   0   0   0   0   0
 0   3   3   4   8  10
 0   8  14  18  24  27
 0   9  17  21  28  36
 0  13  22  26  34  49
 0  14  23  30  38  58
```

Query: `sumRegion(2, 1, 4, 3)`

| Value | Cell | Meaning |
|-------|------|---------|
| prefix[5][4] | 38 | Big rectangle (0,0)→(4,3) |
| prefix[2][4] | 24 | Top to remove (0,0)→(1,3) |
| prefix[5][1] | 14 | Left to remove (0,0)→(4,0) |
| prefix[2][1] | 3  | TopLeft to add back |

`38 - 24 - 14 + 3 = 3` → **8** ✓

**Complexity Analysis**
- **Time Complexity**: O(m × n) preprocessing, O(1) per query
- **Space Complexity**: O(m × n) for prefix array

---

#### **Approach 2: Same-size prefix with boundary checks (ALTERNATIVE)**

**Core Idea**: Build prefix of same size as matrix. Use ternary guards for border indices instead of padding.

**Code Implementation**
```java
class NumMatrix {
    private int[][] prefix;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        prefix = new int[m][n];

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                int top     = (r > 0)          ? prefix[r - 1][c]     : 0;
                int left    = (c > 0)          ? prefix[r][c - 1]     : 0;
                int topLeft = (r > 0 && c > 0) ? prefix[r - 1][c - 1] : 0;
                prefix[r][c] = matrix[r][c] + top + left - topLeft;
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int total   =                                prefix[row2][col2];
        int top     = (row1 > 0)           ?         prefix[row1 - 1][col2]     : 0;
        int left    = (col1 > 0)           ?         prefix[row2][col1 - 1]     : 0;
        int topLeft = (row1 > 0 && col1 > 0) ?       prefix[row1 - 1][col1 - 1] : 0;
        return total - top - left + topLeft;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(m × n) preprocessing, O(1) per query
- **Space Complexity**: O(m × n)

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | Approach 2 (same-size) | Approach 1 (m+1 x n+1) |
|-------------|-------------|------------------------|-------------------------|
| sumRegion time | O(m×n) ❌ | O(1) ✓ | O(1) ✓ |
| Preprocessing | O(1) | O(m×n) | O(m×n) |
| Border handling | None needed | Ternary checks | No checks needed ✅ |
| Code simplicity | ✅ Simple | Medium | ✅ Cleanest |
| Interview friendly | ❌ (too slow) | ⚠️ | ✅ **Best** |

**Winner**: **(m+1)×(n+1) prefix matrix** — clean formula, no border checks, single return statement in query.

---

## Critical Edge Cases & Gotchas

### 1. **Top-left corner query `(0,0,0,0)`**
```java
sumRegion(0, 0, 0, 0)
// Approach 1: prefix[1][1] - prefix[0][1] - prefix[1][0] + prefix[0][0]
//           = prefix[1][1] - 0 - 0 + 0 = matrix[0][0] ✓
```

### 2. **Full matrix query**
```java
sumRegion(0, 0, m-1, n-1)
// Returns prefix[m][n] which is total sum of entire matrix ✓
```

### 3. **Single row query**
```java
sumRegion(2, 1, 2, 3)
// Works correctly — row1 == row2, regular formula applies ✓
```

### 4. **Single column query**
```java
sumRegion(1, 2, 3, 2)
// Works correctly — col1 == col2, regular formula applies ✓
```

### 5. **Single cell query**
```java
sumRegion(1, 1, 1, 1)
// Returns value of exactly matrix[1][1] ✓
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Using preSum variable as running row total**
```java
// WRONG — adds row running sum on top of 2D prefix formula
int preSum = 0;
for (int c = 0; c < col; c++) {
    preSum += matrix[r][c] + prefix[r-1][c] + prefix[r][c-1] - prefix[r-1][c-1];
    prefix[r][c] = preSum;   // ❌ double counting!
}
```
**Why wrong**: `preSum` accumulates the row, but `prefix[r][c-1]` already includes everything to the left. You're adding the left sum twice.

**Fix**: Each cell in prefix is independent — compute it directly, don't use running totals.
```java
// CORRECT
prefix[r][c] = matrix[r-1][c-1]
             + prefix[r-1][c]
             + prefix[r][c-1]
             - prefix[r-1][c-1];
```

### ❌ **MISTAKE 2: Same-size prefix without boundary guards**
```java
// WRONG — crashes when r=0 or c=0
prefix[r][c] = matrix[r][c] + prefix[r-1][c] + prefix[r][c-1] - prefix[r-1][c-1];
// r=0: prefix[-1][c] → ArrayIndexOutOfBoundsException ❌
```
**Fix**: Either use the (m+1)×(n+1) approach, or add boundary checks:
```java
int top = (r > 0) ? prefix[r-1][c] : 0;
```

### ❌ **MISTAKE 3: Wrong query formula indices**
```java
// WRONG — off by one in query due to 0-based prefix without padding
return prefix[row2][col2] - prefix[row1-1][col2] - prefix[row2][col1-1] + prefix[row1-1][col1-1];
// row1=0: prefix[-1][...] → crash ❌
```
**Fix**: Use the (m+1)×(n+1) prefix so indices never go negative:
```java
// CORRECT
return prefix[row2+1][col2+1] - prefix[row1][col2+1] - prefix[row2+1][col1] + prefix[row1][col1];
```

### ❌ **MISTAKE 4: `return int sum = ...` Java syntax**
```java
// WRONG — cannot declare variable inside return
return int sum = prefix[row2][col2] - ...;  // ❌ compile error
```
**Fix**:
```java
// CORRECT
return prefix[row2+1][col2+1] - prefix[row1][col2+1] - prefix[row2+1][col1] + prefix[row1][col1];
```

### ❌ **MISTAKE 5: Forgetting the +TopLeft in query**
```java
// WRONG — forgets to add back the doubly removed top-left
return prefix[row2+1][col2+1] - prefix[row1][col2+1] - prefix[row2+1][col1];
// Top-left area was subtracted twice → result is too small ❌
```
**Fix**: Always include the `+prefix[row1][col1]` to add the top-left back once.

---

## Complexity Analysis

### (m+1)×(n+1) Prefix Approach

**Time Complexity**

| Operation | Time | Reason |
|-----------|------|--------|
| Constructor — fill prefix | O(m × n) | Visit every cell once |
| sumRegion query | O(1) | 4 array lookups + arithmetic |
| k calls to sumRegion | O(k) | Each call is O(1) |
| **Total** | **O(m × n + k)** | |

**Space Complexity**

| Component | Space | Reason |
|-----------|-------|--------|
| prefix array | O(m × n) | (m+1) × (n+1) array |
| sumRegion variables | O(1) | Constant extra space |
| **Total** | **O(m × n)** | |

---

## Visualization

### Complete Build + Query Walk-Through

**Matrix (3×3 example):**
```
1  2  3
4  5  6
7  8  9
```

**Build prefix (4×4, 1-indexed):**
```
Step-by-step (r from 1→3, c from 1→3):

r=1,c=1: matrix[0][0]=1 + 0 + 0 - 0 = 1
r=1,c=2: matrix[0][1]=2 + 0 + 1 - 0 = 3
r=1,c=3: matrix[0][2]=3 + 0 + 3 - 0 = 6
r=2,c=1: matrix[1][0]=4 + 1 + 0 - 0 = 5
r=2,c=2: matrix[1][1]=5 + 3 + 5 - 1 = 12
r=2,c=3: matrix[1][2]=6 + 6 + 12 - 3 = 21
r=3,c=1: matrix[2][0]=7 + 5 + 0 - 0 = 12
r=3,c=2: matrix[2][1]=8 + 12 + 12 - 5 = 27
r=3,c=3: matrix[2][2]=9 + 21 + 27 - 12 = 45

Final prefix:
   0   0   0   0
   0   1   3   6
   0   5  12  21
   0  12  27  45
```

**Query: `sumRegion(1, 1, 2, 2)` → expected: 5+6+8+9 = 28**
```
prefix[3][3] = 45  (big)
prefix[1][3] = 6   (top)
prefix[3][1] = 12  (left)
prefix[1][1] = 1   (topLeft)

45 - 6 - 12 + 1 = 28 ✓
```

---

## Comparison of Approaches

| Approach | Build Time | Query Time | Space | Code | When to Use |
|----------|-----------|-----------|-------|------|-------------|
| Brute Force | O(1) | O(m×n) ❌ | O(1) | Simple | ❌ Too slow for many queries |
| **Prefix (m+1)×(n+1)** | **O(m×n)** | **O(1) ✅** | **O(m×n)** | **✅ Cleanest** | **Default choice ✅** |
| Prefix same-size | O(m×n) | O(1) ✅ | O(m×n) | Verbose | When avoiding extra space |

**Recommendation**: Always use the **(m+1)×(n+1) prefix** approach — no boundary checks, clean indexing, single-line query.

---

## Key Takeaways

1. **"Immutable + multiple range queries" = Prefix Sum** — recognize this pattern instantly
2. **2D prefix[r][c]** = sum of entire rectangle from `(0,0)` to `(r-1, c-1)`
3. **Building formula**: `Current + Top + Left − TopLeft` (fix double counting)
4. **Query formula**: `Big − Top − Left + TopLeft` (inclusion-exclusion)
5. **(m+1)×(n+1) is cleaner** — zero-padding row/col removes all boundary checks
6. **Don't use running row sum** for 2D prefix — each cell is computed independently
7. **O(1) query** is only possible because we precomputed all prefix areas

---

## Interview Tips

**What to say in an interview:**

> "Since the matrix is immutable and we need O(1) range queries, I'll use a 2D prefix sum. I precompute a (m+1)×(n+1) array where prefix[r][c] stores the sum of all elements from (0,0) to (r-1, c-1). Building it takes O(m×n) using the inclusion-exclusion formula: each cell equals current + top + left − topLeft. Then each sumRegion query is just four prefix lookups: big − top − left + topLeft."

**Key points to mention:**
1. **Why prefix sum** — multiple queries + immutable = precompute
2. **Why (m+1)×(n+1)** — avoids all boundary index checks
3. **Build formula** — include-exclude to avoid double counting
4. **Query formula** — same inclusion-exclusion in reverse
5. **Complexity** — O(m×n) build, O(1) query, O(m×n) space

**If asked about mutable matrix:**
> "If the matrix were mutable, we'd use a Binary Indexed Tree (Fenwick Tree) or 2D Segment Tree to handle point updates in O(log m × log n) while keeping range queries efficient."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Range Sum Query 2D - Immutable** | Medium | **2D Prefix Sum** | **This problem** ← |
| Range Sum Query - Immutable (1D) | Easy | 1D Prefix Sum | 1D version |
| Subarray Sum Equals K | Medium | Prefix Sum + HashMap | Count subarrays, not range query |
| Range Sum Query - Mutable | Medium | Fenwick Tree / BIT | Matrix has updates |
| Matrix Block Sum | Medium | 2D Prefix Sum | Variant of this problem |
| Count Vowel Substrings | Medium | Prefix Sum variant | Different domain |

**Pattern Progression**:
1. **1D Prefix Sum** — simplest form (`prefix[i]` = sum of first i elements)
2. **Range Sum Query (1D)** — direct application
3. **2D Prefix Sum** (this problem) — extend to 2D areas
4. **Mutable Prefix** — Fenwick Tree / Segment Tree for dynamic updates

---

## Final Pattern Label

✅ **2D Prefix Sum — Inclusion-Exclusion**

**Remember:** `"immutable matrix + many rectangle queries"` → **Build (m+1)×(n+1) prefix once, answer every query in O(1) with Big − Top − Left + TopLeft!**

