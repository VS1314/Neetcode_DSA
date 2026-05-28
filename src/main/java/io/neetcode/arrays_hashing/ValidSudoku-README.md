# Valid Sudoku

## Problem Description

**Difficulty**: Medium

You are given a 9×9 Sudoku board. A Sudoku board is **valid** if all of the following rules are followed:

- Each **row** must contain the digits 1-9 without duplicates.
- Each **column** must contain the digits 1-9 without duplicates.
- Each of the nine **3×3 sub-boxes** must contain the digits 1-9 without duplicates.

Return `true` if the board is valid, otherwise `false`.

**Note**: A board does not need to be full or solvable to be valid. Empty cells (`.`) are ignored.

## Examples

### Example 1:
```
Input: board =
[["1","2",".",".","3",".",".",".","."],
 ["4",".",".","5",".",".",".",".","."],
 [".","9","8",".",".",".",".",".","3"],
 ["5",".",".",".","6",".",".",".","4"],
 [".",".",".","8",".","3",".",".","5"],
 ["7",".",".",".","2",".",".",".","6"],
 [".",".",".",".",".",".","2",".","."],
 [".",".",".","4","1","9",".",".","8"],
 [".",".",".",".","8",".",".","7","9"]]

Output: true
```

### Example 2:
```
Input: board =
[["1","2",".",".","3",".",".",".","."],
 ["4",".",".","5",".",".",".",".","."],
 [".","9","1",".",".",".",".",".","3"],   ← '1' violates the top-left 3×3 box
 ...same rest...]

Output: false
Explanation: There are two 1's in the top-left 3×3 sub-box.
```

## Constraints
- board.length == 9
- board[i].length == 9
- board[i][j] is a digit 1-9 or '.'

---

## Pattern Recognition

**Primary Pattern**: **HashSet per Row / Column / Box**

**Why This Pattern?**
- We need to detect duplicates across three dimensions: rows, columns, and 3×3 boxes
- HashSet gives O(1) insert and O(1) lookup for duplicate detection
- Since the board is always 9×9, the entire solution is O(81) = **O(1) effectively**
- We maintain 9 sets for rows, 9 for columns, 9 for boxes — 27 sets total

**Key Insight**: For every non-empty cell `(r, c)`, check if its value already exists in:
1. `rows[r]` — the set for row `r`
2. `cols[c]` — the set for column `c`
3. `boxes[boxIndex]` — the set for the 3×3 box containing `(r, c)`

If any check fails → invalid. Otherwise, add the value to all three sets.

**The Critical Formula — Box Index:**
```
boxIndex = (r / 3) * 3 + (c / 3)

The 9 boxes are numbered:
  0 | 1 | 2
  ---------
  3 | 4 | 5
  ---------
  6 | 7 | 8
```

**Related Patterns**:
1. **HashSet for Duplicates** — same single-pass duplicate detection technique
2. **Contains Duplicate** — simpler version (single array)
3. **Group Anagrams** — grouping by a computed key (same box-index concept)
4. **Encode/Decode Strings** — key-based separation of data streams

---

## Algorithm & Approach

### Core Insight

**What we are NOT doing:**
```
NOT solving Sudoku.
NOT filling in blanks.
Only checking: do any filled cells violate the three rules?
```

**Single-pass idea:**

```
For each cell (r, c):
  if board[r][c] == '.' → skip
  else:
    val = board[r][c]
    boxIndex = (r / 3) * 3 + (c / 3)

    if val in rows[r]       → ❌ duplicate in row
    if val in cols[c]       → ❌ duplicate in column
    if val in boxes[box]    → ❌ duplicate in box

    add val to rows[r], cols[c], boxes[box]

return true
```

### Visual Understanding

```
The 9 boxes and their indices:

   col→  0  1  2  |  3  4  5  |  6  7  8
row ↓  ┌──────────┼───────────┼──────────┐
  0    │          │           │          │
  1    │  box 0   │   box 1   │  box 2   │
  2    │          │           │          │
       ├──────────┼───────────┼──────────┤
  3    │          │           │          │
  4    │  box 3   │   box 4   │  box 5   │
  5    │          │           │          │
       ├──────────┼───────────┼──────────┤
  6    │          │           │          │
  7    │  box 6   │   box 7   │  box 8   │
  8    │          │           │          │
       └──────────┴───────────┴──────────┘

boxIndex = (r / 3) * 3 + (c / 3)

Examples:
  (r=0, c=0): (0/3)*3 + (0/3) = 0*3 + 0 = 0  → box 0
  (r=0, c=8): (0/3)*3 + (8/3) = 0*3 + 2 = 2  → box 2
  (r=4, c=5): (4/3)*3 + (5/3) = 1*3 + 1 = 4  → box 4
  (r=7, c=1): (7/3)*3 + (1/3) = 2*3 + 0 = 6  → box 6
```

### Step-by-Step Algorithm

---

#### **Approach 1: Array of HashSets — OPTIMAL & RECOMMENDED**

**Core Idea**:
- Create 3 arrays of 9 HashSets each: `rows[9]`, `cols[9]`, `boxes[9]`
- For each non-empty cell, compute `boxIndex` using `(r/3)*3 + (c/3)`
- Check all three sets — if any contains the value, return `false`
- Otherwise add the value to all three sets
- Return `true` at the end

**Code Implementation**
```java
class Solution {
    public boolean isValidSudoku(char[][] board) {
        HashSet<Character>[] rows  = new HashSet[9];
        HashSet<Character>[] cols  = new HashSet[9];
        HashSet<Character>[] boxes = new HashSet[9];

        for (int i = 0; i < 9; i++) {
            rows[i]  = new HashSet<>();
            cols[i]  = new HashSet<>();
            boxes[i] = new HashSet<>();
        }

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char val = board[r][c];
                if (val == '.') continue;

                int boxIndex = (r / 3) * 3 + (c / 3);

                if (rows[r].contains(val) ||
                    cols[c].contains(val) ||
                    boxes[boxIndex].contains(val)) {
                    return false;
                }

                rows[r].add(val);
                cols[c].add(val);
                boxes[boxIndex].add(val);
            }
        }

        return true;
    }
}
```

**Example Walkthrough**

Processing cell `(2, 1)` with value `'9'`:
```
r = 2, c = 1, val = '9'
boxIndex = (2/3)*3 + (1/3) = 0*3 + 0 = 0

Check:
  rows[2].contains('9')  → false ✓
  cols[1].contains('9')  → false ✓
  boxes[0].contains('9') → false ✓

Add '9' to rows[2], cols[1], boxes[0]
```

Processing cell `(2, 2)` with value `'1'` (Example 2 — invalid case):
```
r = 2, c = 2, val = '1'
boxIndex = (2/3)*3 + (2/3) = 0*3 + 0 = 0

Check:
  rows[2].contains('1')  → false ✓
  cols[2].contains('1')  → false ✓
  boxes[0].contains('1') → TRUE ❌  (row 0, col 0 already had '1')

return false  ← invalid board detected
```

**Complexity Analysis**
- **Time Complexity**: O(9²) = O(81) = **O(1)** — fixed 9×9 board
- **Space Complexity**: O(9²) = O(81) = **O(1)** — 27 sets, each holding at most 9 characters

---

#### **Approach 2: Single HashSet with Encoded Strings (ALTERNATIVE)**

**Core Idea**:
- Use a single `HashSet<String>` and encode each value with its context (row/column/box)
- Example: value `'5'` in row 3 is encoded as `"r3:5"`, in col 6 as `"c6:5"`, in box 1 as `"b1:5"`
- If `seen.add(encodedKey)` returns `false`, a duplicate exists

**Code Implementation**
```java
class Solution {
    public boolean isValidSudoku(char[][] board) {
        HashSet<String> seen = new HashSet<>();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char val = board[r][c];
                if (val == '.') continue;

                int box = (r / 3) * 3 + (c / 3);

                if (!seen.add("r" + r + val) ||
                    !seen.add("c" + c + val) ||
                    !seen.add("b" + box + val)) {
                    return false;
                }
            }
        }

        return true;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(81) = **O(1)**
- **Space Complexity**: O(81 × 3) = **O(1)** — at most 243 strings in the set

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force (9×9×9 loops) | HashSet per dim | Single HashSet |
|-------------|--------------------------|-----------------|----------------|
| Time complexity | O(n³) | O(n²) ✅ | O(n²) ✅ |
| Duplicate detection | Slow nested loops | O(1) per cell ✅ | O(1) per cell ✅ |
| Code clarity | ❌ Complex | ✅ Very clear | Medium |
| Box index mapping | Needs formula | Uses formula ✅ | Uses formula ✅ |
| Interview friendly | ❌ | ✅ **Best** | ✓ Acceptable |

**Winner**: **Array of HashSets** — most readable, clear separation of row/col/box tracking.

---

## Critical Edge Cases & Gotchas

### 1. **Empty board (all dots)**
```java
Input: all '.'
Output: true
Explanation: No digits placed → no rules violated.
```

### 2. **Single digit placed correctly**
```java
board[0][0] = '5', rest = '.'
Output: true
Explanation: Only one cell filled, no duplicates possible.
```

### 3. **Duplicate in same row**
```java
Row 0: ["1",".",".","1",...]
Output: false
Explanation: '1' appears twice in row 0 → rows[0] detects it.
```

### 4. **Duplicate in same column**
```java
board[0][0] = '5', board[5][0] = '5'
Output: false
Explanation: '5' appears twice in column 0 → cols[0] detects it.
```

### 5. **Duplicate in 3×3 box only (not same row/col)**
```java
board[0][0] = '1', board[1][1] = '1'
Output: false
Explanation: Both are in box 0 → boxes[0] detects it even though
             they are in different rows and columns.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Using `HashSet<Character[]>` instead of `HashSet<Character>`**
```java
// WRONG
HashSet<Character[]> rows = new HashSet<>();
```
**Why wrong**: `Character[]` is an array. Java arrays do **not** override `equals()` or `hashCode()` — HashSet compares by memory reference, not content. Two arrays `['1','2']` and `['1','2']` are considered different objects.

**Fix**: Use `HashSet<Character>` (wrapper type, not array):
```java
HashSet<Character>[] rows = new HashSet[9];   // ✓ array of 9 sets
rows[r] = new HashSet<>();
rows[r].add('5');
rows[r].contains('5');  // ✓ correctly returns true
```

### ❌ **MISTAKE 2: One set for all rows instead of one set per row**
```java
// WRONG — one set for ALL rows
HashSet<Character> rows = new HashSet<>();
```
**Why wrong**: This mixes values from all rows. A '5' in row 0 would block '5' from appearing in row 1, which is incorrect.

**Fix**: Use an array of 9 sets — one per row/column/box:
```java
HashSet<Character>[] rows = new HashSet[9];
for (int i = 0; i < 9; i++) rows[i] = new HashSet<>();
// Now rows[r] tracks only row r
```

### ❌ **MISTAKE 3: Wrong box index formula**
```java
// WRONG — common incorrect attempts
boxIndex = r * 3 + c;           // ❌ gives 81 unique values, not 9
boxIndex = (r / 3) + (c / 3);  // ❌ gives wrong grouping
```
**Why wrong**: The correct formula must map every cell in the same 3×3 box to the same index.

**Fix**: Use `(r / 3) * 3 + (c / 3)`:
```
Rows 0-2 → r/3 = 0 → contributes 0, 3, 6... (based on col group)
Rows 3-5 → r/3 = 1 → contributes 3, 4, 5
Rows 6-8 → r/3 = 2 → contributes 6, 7, 8
```

### ❌ **MISTAKE 4: Not skipping '.' cells**
```java
// WRONG — checks and adds '.' to all sets
for (int r = 0; r < 9; r++) {
    for (int c = 0; c < 9; c++) {
        char val = board[r][c];
        // ❌ missing: if (val == '.') continue;
        if (rows[r].contains(val)) return false;
        rows[r].add(val);
    }
}
```
**Why wrong**: Adding `'.'` to all sets means a second `'.'` in the same row would be flagged as a duplicate. Empty cells are valid and must be ignored.

**Fix**: Skip empty cells immediately:
```java
if (val == '.') continue;   // ✓ ignore empty cells
```

### ❌ **MISTAKE 5: Checking but forgetting to add to sets**
```java
// WRONG — checks but never records the value
if (rows[r].contains(val) || cols[c].contains(val) || boxes[box].contains(val)) {
    return false;
}
// ❌ Forgot to add val to the sets!
```
**Why wrong**: Without adding, every check will pass — duplicates will never be detected.

**Fix**: Always add after a successful check:
```java
rows[r].add(val);
cols[c].add(val);
boxes[boxIndex].add(val);   // ✓ record so future cells can detect duplicates
```

---

## Complexity Analysis

**Time Complexity: O(n²)** where n = 9 → **effectively O(1)**

| Operation | Time | Reason |
|-----------|------|--------|
| Outer loops | O(81) | 9 rows × 9 cols = 81 cells |
| HashSet lookup | O(1) | Per cell, 3 set checks |
| HashSet insert | O(1) | Per cell, 3 set inserts |
| **Total** | **O(81) = O(1)** | Fixed-size board |

**Space Complexity: O(n²)** → **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| rows array | O(81) | 9 sets × up to 9 chars each |
| cols array | O(81) | Same |
| boxes array | O(81) | Same |
| **Total** | **O(243) = O(1)** | Bounded by fixed 9×9 board |

---

## Visualization

### Box Index Mapping — Full Table

```
Board cell → boxIndex = (r/3)*3 + (c/3)

     c=0  c=1  c=2  c=3  c=4  c=5  c=6  c=7  c=8
r=0 [ 0    0    0    1    1    1    2    2    2 ]
r=1 [ 0    0    0    1    1    1    2    2    2 ]
r=2 [ 0    0    0    1    1    1    2    2    2 ]
r=3 [ 3    3    3    4    4    4    5    5    5 ]
r=4 [ 3    3    3    4    4    4    5    5    5 ]
r=5 [ 3    3    3    4    4    4    5    5    5 ]
r=6 [ 6    6    6    7    7    7    8    8    8 ]
r=7 [ 6    6    6    7    7    7    8    8    8 ]
r=8 [ 6    6    6    7    7    7    8    8    8 ]
```

### Dry Run — Example 2 (detecting the invalid case)

```
Board (relevant cells):
Row 0: "1" at (0,0)  → box 0
Row 2: "1" at (2,2)  → box (2/3)*3+(2/3) = 0  ← same box!

Processing (0,0):
  val='1', box=0
  rows[0].contains('1') → false ✓
  cols[0].contains('1') → false ✓
  boxes[0].contains('1') → false ✓
  → Add '1' to rows[0], cols[0], boxes[0]

Processing (2,2):
  val='1', box=0
  rows[2].contains('1') → false ✓
  cols[2].contains('1') → false ✓
  boxes[0].contains('1') → TRUE ❌

→ return false  (two 1's in the same 3×3 box)
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Clarity | When to Use |
|----------|------|-------|-------------|-------------|
| Brute force nested check | O(n³) | O(1) | ❌ Verbose | Never |
| **Array of HashSets** | **O(n²)** | **O(n²)** | **✅ Cleanest** | **Default ✅** |
| Single HashSet with encoding | O(n²) | O(n²) | Medium | Concise alternative |

**Recommendation**: Use **Array of HashSets** — cleanest separation of concerns, easiest to explain in an interview.

---

## Key Takeaways

1. **Three constraints = three arrays of sets** — one per row, one per column, one per box
2. **Box index formula**: `(r / 3) * 3 + (c / 3)` — integer division groups 3 rows and 3 cols into blocks
3. **Skip empty cells** — `'.'` is not a digit, never add to sets
4. **Check before adding** — if already present, return `false` immediately
5. **Use `HashSet<Character>`, not `HashSet<Character[]>`** — arrays don't work as HashSet keys
6. **One set per row, not one set for all rows** — `rows[r]` tracks only row `r`
7. **Board is fixed 9×9** — all complexities simplify to O(1)

---

## Interview Tips

**What to say in an interview:**

> "For each non-empty cell I need to check three constraints simultaneously: row, column, and 3×3 box. I'll maintain three arrays of HashSets — one array for rows, one for columns, one for boxes. For each number I encounter, I compute its box index using `(r/3)*3 + (c/3)`, check if the number already exists in the corresponding row set, column set, and box set. If any contains it, the board is invalid. Otherwise I add it to all three and continue. The whole thing is O(81) time and space."

**Key points to mention:**
1. **Three arrays of sets** — not three single sets
2. **Box index formula** — and why it works (integer division groups 3 rows/cols)
3. **Skip '.'** — empty cells don't violate any rule
4. **Check first, then add** — order of operations matters
5. **Why not `Character[]`** — arrays don't override equals/hashCode in Java

**If asked for optimization:**
> "Since the board is always 9×9, we could also use a `boolean[9][10]` for each dimension (rows, cols, boxes) where the second dimension indexes digits 1-9, getting true O(1) space with faster boolean lookups."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Valid Sudoku** | Medium | **HashSet per dimension** | **This problem** ← |
| Sudoku Solver | Hard | Backtracking | Actually fills the board |
| Contains Duplicate | Easy | HashSet | Single array, one dimension |
| Find the Duplicate Number | Medium | HashSet / Floyd's | Find which number duplicates |
| Group Anagrams | Medium | HashMap by key | Grouping by computed key |

**Pattern Progression**:
1. **Contains Duplicate** — one set, one dimension
2. **Valid Sudoku** (this problem) — three arrays of sets, three dimensions
3. **Sudoku Solver** — valid check + backtracking to fill the board

---

## Final Pattern Label

✅ **Multi-Dimensional Duplicate Detection — Array of HashSets**

**Remember:** For each cell, check `rows[r]`, `cols[c]`, and `boxes[(r/3)*3+(c/3)]` — if any contains the current value it's invalid, otherwise add it to all three!

````

