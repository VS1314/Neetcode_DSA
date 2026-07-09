# Search a 2D Matrix

## Problem Description

**Difficulty**: Medium

You are given an `m x n` **2-D integer array** `matrix` and an integer `target`.

- Each row in `matrix` is sorted in **non-decreasing** order.
- The first integer of every row is **greater than** the last integer of the previous row.

Return `true` if `target` exists within `matrix` or `false` otherwise.

Can you write a solution that runs in **O(log(m * n))** time?

## Examples

### Example 1:
```
Input: matrix = [[1,2,4,8],[10,11,12,13],[14,20,30,40]], target = 10
Output: true

Explanation:
  Matrix:
    [1,  2,  4,  8]
    [10, 11, 12, 13]
    [14, 20, 30, 40]
    
  10 exists at position (1, 0)
  Return true
```

### Example 2:
```
Input: matrix = [[1,2,4,8],[10,11,12,13],[14,20,30,40]], target = 15
Output: false

Explanation:
  Matrix:
    [1,  2,  4,  8]
    [10, 11, 12, 13]
    [14, 20, 30, 40]
    
  15 does not exist in matrix
  Return false
```

### Example 3:
```
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
Output: true

Explanation:
  3 exists at position (0, 1)
```

### Example 4:
```
Input: matrix = [[1]], target = 1
Output: true

Explanation:
  Single element matrix, target found
```

### Example 5:
```
Input: matrix = [[1]], target = 2
Output: false

Explanation:
  Single element matrix, target not found
```

### Example 6:
```
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
Output: false

Explanation:
  13 falls between 11 and 16, not in matrix
```

### Example 7:
```
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 1
Output: true

Explanation:
  First element in matrix
```

### Example 8:
```
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 60
Output: true

Explanation:
  Last element in matrix
```

### Example 9:
```
Input: matrix = [[-10,-9,-8],[-5,-2,0],[1,4,7]], target = -5
Output: true

Explanation:
  Works with negative numbers
```

### Example 10:
```
Input: matrix = [[1,1,1],[1,1,1],[1,1,1]], target = 1
Output: true

Explanation:
  Works with duplicate values (non-decreasing allows duplicates)
```

## Constraints
- m == matrix.length
- n == matrix[i].length
- 1 <= m, n <= 100
- -10,000 <= matrix[i][j], target <= 10,000
- Each row is sorted in **non-decreasing** order
- First element of each row is **greater than** last element of previous row

**Recommended Complexity**: O(log(m × n)) time and O(1) space, where m is rows and n is columns

---

## Pattern Recognition

**Primary Pattern**: **Binary Search on Virtually Flattened 2D Matrix**

**Why This Pattern?**
- Matrix has **special sorting property** (each row sorted + first of row > last of previous)
- This makes entire matrix **sorted when flattened**
- Can treat 2D matrix as 1D sorted array
- Binary search applies naturally with index mapping

**Key Insight**: Matrix is Effectively 1D Sorted Array
```
Matrix structure:
  Each row sorted: [a₀ < a₁ < a₂ < ...]
  Row constraint: first of row i > last of row i-1
  
This means:
  matrix[0][0] < ... < matrix[0][n-1] <
  matrix[1][0] < ... < matrix[1][n-1] <
  matrix[2][0] < ... < matrix[2][n-1] < ...
  
Flattened view:
  [matrix[0][0], matrix[0][1], ..., matrix[0][n-1],
   matrix[1][0], matrix[1][1], ..., matrix[1][n-1],
   matrix[2][0], ...]
   
This is a sorted 1D array!
Binary search applies with O(log(m*n)) time.
```

**Example Showing Flattened Structure**:
```
Matrix:
  [1,  2,  4,  8]
  [10, 11, 12, 13]
  [14, 20, 30, 40]
  
Flattened (conceptual):
  [1, 2, 4, 8, 10, 11, 12, 13, 14, 20, 30, 40]
   0  1  2  3   4   5   6   7   8   9  10  11 (indices)
  
Properties:
  ✓ Sorted array
  ✓ Size: m × n = 3 × 4 = 12
  ✓ Binary search possible
  
Index mapping:
  1D index → 2D coordinates
  
  index = 5 (value 11)
  row = 5 / 4 = 1
  col = 5 % 4 = 1
  matrix[1][1] = 11 ✓
  
  index = 8 (value 14)
  row = 8 / 4 = 2
  col = 8 % 4 = 0
  matrix[2][0] = 14 ✓
```

**The Index Mapping Formula**:
```
Given:
  m = number of rows
  n = number of columns
  mid = 1D index in range [0, m*n-1]
  
Convert 1D to 2D:
  row = mid / n
  col = mid % n
  
Why this works:
  Each row has n elements
  
  mid = 0: row=0, col=0 → first element
  mid = n-1: row=0, col=n-1 → end of first row
  mid = n: row=1, col=0 → start of second row
  mid = 2n-1: row=1, col=n-1 → end of second row
  ...
  
Perfect mapping from 1D to 2D!

Example: m=3, n=4, mid=6
  row = 6 / 4 = 1
  col = 6 % 4 = 2
  matrix[1][2] ✓
```

**Binary Search Strategy**:
```
1. Treat matrix as 1D array of size m*n
2. Binary search on range [0, m*n-1]
3. For each mid:
   - Convert to 2D: row = mid/n, col = mid%n
   - Get value: matrix[row][col]
   - Compare with target
   - Adjust left/right as in standard binary search
4. Return true if found, false otherwise

Time: O(log(m*n))
Space: O(1)
```

**Why This is Optimal**:
```
Brute force: Check every element
  Time: O(m*n)
  For 100×100 matrix: 10,000 comparisons ❌

Binary search on flattened:
  Time: O(log(m*n))
  For 100×100 matrix: log₂(10,000) ≈ 14 comparisons ✓
  
700× improvement!

Alternative: Two binary searches
  1. Binary search to find row: O(log m)
  2. Binary search in row: O(log n)
  Total: O(log m + log n)
  
But log(m*n) = log m + log n
So both approaches have same complexity!

Single binary search is simpler to implement.
```

**Related Patterns**:
1. **Binary Search** — Core technique
2. **2D to 1D Mapping** — Index conversion
3. **Virtual Array** — Treat 2D as 1D
4. **Sorted Matrix Search** — Various techniques

---

## Algorithm & Approach

### Core Insight

**Why Single Binary Search Works:**
```
Matrix properties guarantee global sort:
  1. Each row sorted
  2. First of row i > last of row i-1
  
Consequence:
  Reading row by row gives sorted sequence
  
Example:
  [[1, 3, 5],
   [7, 9, 11],
   [13, 15, 17]]
   
  Row by row: 1, 3, 5, 7, 9, 11, 13, 15, 17
  Sorted! ✓
  
Can do binary search directly!
Just need to map 1D index to 2D coordinates.
```

**The Optimal Strategy**:
```
Key observations:
  1. Matrix is globally sorted (when flattened)
  2. Total elements: m × n
  3. Can use 1D binary search with coordinate mapping
  4. Each comparison: O(1) with index conversion
  
Result: O(log(m×n)) time, O(1) space
```

### Step-by-Step Algorithm

---

#### **Approach 1: Single Binary Search (1D View) - OPTIMAL**

**Core Idea**:
- Treat m×n matrix as sorted array of size m×n
- Binary search with 1D index
- Convert 1D index to 2D coordinates on the fly

**Algorithm**
```
searchMatrix(matrix, target):
    m = matrix.length
    n = matrix[0].length
    left = 0
    right = m * n - 1
    
    while left <= right:
        mid = left + (right - left) / 2
        
        // Convert 1D to 2D
        row = mid / n
        col = mid % n
        value = matrix[row][col]
        
        if value == target:
            return true
        else if value < target:
            left = mid + 1  // Search right
        else:
            right = mid - 1  // Search left
    
    return false
```

**Code Implementation**
```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        int left = 0;
        int right = m * n - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Convert 1D index to 2D coordinates
            int row = mid / n;
            int col = mid % n;
            int value = matrix[row][col];
            
            if (value == target) {
                return true;
            } else if (value < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
}
```

**Example Walkthrough**

Input: `matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3`

m = 3, n = 4, total = 12

| Iteration | left | right | mid | row | col | value | Comparison | Action |
|-----------|------|-------|-----|-----|-----|-------|------------|--------|
| 1 | 0 | 11 | 5 | 1 | 1 | 11 | 11 > 3 | right = 4 |
| 2 | 0 | 4 | 2 | 0 | 2 | 5 | 5 > 3 | right = 1 |
| 3 | 0 | 1 | 0 | 0 | 0 | 1 | 1 < 3 | left = 1 |
| 4 | 1 | 1 | 1 | 0 | 1 | 3 | 3 == 3 | Found! |

Return: **true** ✓

**Complexity Analysis**
- **Time**: O(log(m × n)) — Binary search on m×n elements
- **Space**: O(1) — Only constant variables

---

#### **Approach 2: Two Binary Searches - ALTERNATIVE**

**Core Idea**: 
- First binary search to find correct row
- Second binary search within that row

**Algorithm**
```
searchMatrix(matrix, target):
    // Binary search for row
    Find row where:
      matrix[row][0] <= target <= matrix[row][n-1]
    
    // Binary search in row
    Binary search for target in matrix[row]
    
    Return result
```

**Code Implementation**
```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        // Find the row where target might be
        int top = 0;
        int bottom = m - 1;
        int targetRow = -1;
        
        while (top <= bottom) {
            int mid = top + (bottom - top) / 2;
            
            if (matrix[mid][0] <= target && target <= matrix[mid][n-1]) {
                targetRow = mid;
                break;
            } else if (target < matrix[mid][0]) {
                bottom = mid - 1;
            } else {
                top = mid + 1;
            }
        }
        
        if (targetRow == -1) {
            return false;
        }
        
        // Binary search in the row
        int left = 0;
        int right = n - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (matrix[targetRow][mid] == target) {
                return true;
            } else if (matrix[targetRow][mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return false;
    }
}
```

**Key Difference**: 
- Two separate binary searches
- More code but conceptually simpler for some
- Same time complexity

**Complexity Analysis**
- **Time**: O(log m + log n) = O(log(m×n))
- **Space**: O(1)

---

#### **Approach 3: Staircase Search - SUBOPTIMAL**

**Core Idea**: Start from top-right or bottom-left corner.

**Algorithm**
```
searchMatrix(matrix, target):
    row = 0
    col = n - 1
    
    while row < m and col >= 0:
        if matrix[row][col] == target:
            return true
        else if matrix[row][col] > target:
            col--  // Move left
        else:
            row++  // Move down
    
    return false
```

**Code Implementation**
```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        int row = 0;
        int col = n - 1;
        
        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                col--;  // Move left
            } else {
                row++;  // Move down
            }
        }
        
        return false;
    }
}
```

**Key Difference**: 
- Works but doesn't use full sorting property
- O(m + n) time instead of O(log(m×n))
- Not optimal for this problem

**Complexity Analysis**
- **Time**: O(m + n) — Linear in dimensions ❌
- **Space**: O(1)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Code Lines | Uses Full Sort | Recommended |
|----------|------|-------|------------|----------------|-------------|
| **Single Binary Search** | **O(log(m×n))** | **O(1)** | **~20** | **Yes ✅** | **Yes ✅** |
| Two Binary Searches | O(log(m×n)) | O(1) | ~35 | Yes ✅ | Alternative |
| Staircase Search | O(m + n) | O(1) | ~15 | No | No ❌ |
| Linear Scan | O(m×n) | O(1) | ~10 | No | Too slow ❌ |

**Winner**: **Single Binary Search** — optimal time, minimal code!

### Why Single Binary Search is Best

```
Matrix properties:
  ✓ Each row sorted
  ✓ First of row i > last of row i-1
  
Consequence:
  Entire matrix is sorted when read row by row
  
This is perfect for binary search!

Example:
  [[1, 3, 5],
   [7, 9, 11]]
   
  Flattened: [1, 3, 5, 7, 9, 11]
  Perfectly sorted ✓
  
Binary search on 6 elements: log₂(6) ≈ 3 comparisons
Much better than checking all 6!

For 100×100 matrix:
  10,000 elements
  Binary: log₂(10,000) ≈ 14 comparisons
  Linear: up to 10,000 comparisons
  
700× faster!
```

### Why Index Mapping Works

```
Mapping formula:
  row = mid / n
  col = mid % n
  
Why?
  Matrix has n columns
  Elements 0 to n-1: row 0
  Elements n to 2n-1: row 1
  Elements 2n to 3n-1: row 2
  ...
  
  Division by n gives row number
  Remainder gives column within row
  
Example: mid = 7, n = 4
  row = 7 / 4 = 1
  col = 7 % 4 = 3
  matrix[1][3] (8th element, row 1, last column)
  
Visual:
  [0, 1, 2, 3]  <- indices 0-3, row 0
  [4, 5, 6, 7]  <- indices 4-7, row 1
  [8, 9, 10, 11] <- indices 8-11, row 2
  
  Index 7: row = 7/4 = 1, col = 7%4 = 3 ✓

Perfect bijection between 1D and 2D!
```

### Why Two Binary Searches is Alternative

```
Approach:
  1. Binary search rows: O(log m)
  2. Binary search in row: O(log n)
  Total: O(log m + log n)
  
Mathematically:
  log(m×n) = log m + log n
  
Same complexity!

But:
  More code (two loops)
  More complex logic (finding row)
  
Single binary search:
  One loop
  Simple index mapping
  Cleaner code
  
Prefer single binary search!
```

### Why Staircase Search is Not Optimal Here

```
Staircase search:
  Start from top-right
  If value > target: move left
  If value < target: move down
  
Time: O(m + n)

Example: 100×100 matrix
  Staircase: up to 200 steps
  Binary: ~14 steps
  
14× slower!

Why not use staircase?
  This problem has stronger property:
    First of row i > last of row i-1
  
  Staircase is for weaker property:
    Only rows and columns sorted
    (Search a 2D Matrix II - different problem)
  
Here, we have full sorting → use binary search!
```

### Why Linear Scan is Too Slow

```
Linear scan:
  for each row:
      for each col:
          if matrix[row][col] == target:
              return true
  
Time: O(m×n)

For 100×100 matrix:
  10,000 comparisons ❌

Binary search:
  14 comparisons ✓
  
Problem asks for O(log(m×n)) time
Linear scan doesn't meet requirement!
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Element Matrix**
```java
Input: matrix = [[5]], target = 5
Output: true

m=1, n=1, total=1
mid=0, row=0, col=0
Works correctly
```

### 2. **Single Row Matrix**
```java
Input: matrix = [[1, 3, 5, 7]], target = 3
Output: true

m=1, n=4, total=4
Standard binary search on one row
mid=1, row=0, col=1, value=3 ✓
```

### 3. **Single Column Matrix**
```java
Input: matrix = [[1], [3], [5], [7]], target = 5
Output: true

m=4, n=1, total=4
mid=2, row=2, col=0, value=5 ✓
```

### 4. **Target Smaller Than All**
```java
Input: matrix = [[1, 3], [5, 7]], target = 0
Output: false

Binary search converges to left=0
matrix[0][0]=1 > 0
Not found ✓
```

### 5. **Target Larger Than All**
```java
Input: matrix = [[1, 3], [5, 7]], target = 10
Output: false

Binary search converges to right end
matrix[1][1]=7 < 10
Not found ✓
```

### 6. **Target Between Rows**
```java
Input: matrix = [[1, 3], [5, 7]], target = 4
Output: false

4 is between 3 and 5 (different rows)
Binary search won't find it ✓
```

### 7. **Negative Numbers**
```java
Input: matrix = [[-10, -5], [-3, 0], [5, 10]], target = -5
Output: true

Works with negative numbers
Same binary search logic
```

### 8. **All Same Values**
```java
Input: matrix = [[1, 1], [1, 1]], target = 1
Output: true

Non-decreasing allows duplicates
Binary search finds it
```

### 9. **Large Matrix**
```java
Input: matrix = 100×100 filled with sorted values, target = 5000
Output: depends on presence

Efficient with log(10,000) ≈ 14 comparisons
```

### 10. **Target at Last Position**
```java
Input: matrix = [[1, 2], [3, 4]], target = 4
Output: true

Last element found correctly
mid=3, row=1, col=1, value=4 ✓
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Wrong Index Mapping**
```java
// WRONG - reversed row and col calculation
int row = mid % n;  // Should be mid / n
int col = mid / n;  // Should be mid % n
```

**Why wrong**: Division and modulo swapped!

**Dry run failure for mid=5, n=4:**
```
WRONG calculation:
  row = 5 % 4 = 1
  col = 5 / 4 = 1
  Access: matrix[1][1]
  
But 1D index 5 should map to:
  [0, 1, 2, 3]  row 0
  [4, 5, 6, 7]  row 1
     ↑
  Index 5 is at position (1, 1)?
  
Actually should be:
  row = 5 / 4 = 1 ✓
  col = 5 % 4 = 1 ✓
  
Wait, it's the same! Let me recalculate...

Actually for index 5, n=4:
  Correct: row=5/4=1, col=5%4=1
  Wrong: row=5%4=1, col=5/4=1
  
Both give (1,1) in this case!

Let's try mid=6, n=4:
  Correct: row=6/4=1, col=6%4=2 → matrix[1][2]
  Wrong: row=6%4=2, col=6/4=1 → matrix[2][1]
  
Different positions! ❌
If matrix is 3×4, matrix[2][1] exists but is wrong element
```

**Fix**: Use correct formula
```java
int row = mid / n;
int col = mid % n;
```

### ❌ **MISTAKE 2: Wrong Total Size**
```java
// WRONG - off by one
int right = m * n;  // Should be m * n - 1
```

**Why wrong**: Indices are 0-based!

**Dry run failure for m=2, n=3:**
```
Total elements: 6
Indices: 0, 1, 2, 3, 4, 5

WRONG: right = 6
  mid = (0 + 6) / 2 = 3
  Works initially...
  
  But eventually: left=6
  row = 6 / 3 = 2
  matrix[2][...] out of bounds! ❌
  (Only have rows 0 and 1)

CORRECT: right = 5
  All indices stay in range [0, 5] ✓
```

**Fix**: Use m * n - 1
```java
int right = m * n - 1;
```

### ❌ **MISTAKE 3: Not Checking Empty Matrix**
```java
// WRONG - assumes matrix has elements
int m = matrix.length;
int n = matrix[0].length;  // Crashes if matrix empty!
```

**Why wrong**: matrix[0] fails if matrix is empty!

**Fix**: Check constraints
```java
// Given constraints: 1 <= m, n <= 100
// So matrix is never empty, no check needed

// But for robustness:
if (matrix == null || matrix.length == 0) return false;
int n = matrix[0].length;
```

### ❌ **MISTAKE 4: Integer Overflow in m * n**
```java
// WRONG - potential overflow
int right = m * n - 1;
```

**Why wrong**: m × n might overflow for very large matrices!

**For this problem**: m, n ≤ 100, so m×n ≤ 10,000, no overflow

**In general**: For larger matrices, be careful

**Fix**: Not needed here due to constraints

### ❌ **MISTAKE 5: Using Wrong Comparison**
```java
// WRONG - checking wrong condition
if (matrix[row][col] <= target) {
    return true;  // Should check equality!
}
```

**Why wrong**: Returns true for any value ≤ target!

**Dry run failure:**
```
target = 5
Found matrix[row][col] = 3
3 <= 5? true
Return true ❌

But 3 ≠ 5, should continue searching!
```

**Fix**: Check equality
```java
if (matrix[row][col] == target) {
    return true;
}
```

### ❌ **MISTAKE 6: Not Excluding Mid**
```java
// WRONG - doesn't exclude mid
if (value < target) {
    left = mid;  // Should be mid + 1
}
```

**Why wrong**: Infinite loop when left == right!

**Dry run failure:**
```
left = 5, right = 5
mid = 5
value < target
left = 5 (unchanged!)

Next iteration: same state
Infinite loop ❌
```

**Fix**: Always exclude mid
```java
left = mid + 1;
right = mid - 1;
```

### ❌ **MISTAKE 7: Confusing With Search a 2D Matrix II**
```java
// WRONG - using staircase search for this problem
// This is for a DIFFERENT problem!
int row = 0, col = n - 1;
while (row < m && col >= 0) {
    if (matrix[row][col] == target) return true;
    if (matrix[row][col] > target) col--;
    else row++;
}
```

**Why wrong**: This is O(m+n), not optimal for this problem!

**This problem**: First of row i > last of row i-1
**Matrix II**: Only rows and columns sorted (weaker property)

**Use staircase for Matrix II, binary search for this problem!**

**Fix**: Use binary search with 1D view
```java
// Treat as 1D sorted array
```

---

## Complexity Analysis

### Time Complexity: **O(log(m × n))**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **While loop iterations** | O(log(m×n)) | O(1) | O(log(m×n)) |
| **Calculate mid** | O(log(m×n)) | O(1) | O(log(m×n)) |
| **Convert to 2D** | O(log(m×n)) | O(1) | O(log(m×n)) |
| **Matrix access** | O(log(m×n)) | O(1) | O(log(m×n)) |
| **Compare** | O(log(m×n)) | O(1) | O(log(m×n)) |
| **Update pointers** | O(log(m×n)) | O(1) | O(log(m×n)) |
| **Total** | - | - | **O(log(m×n))** |

**Time analysis**:
```
Binary search on m×n elements
Each iteration halves search space

Total elements: m × n
After k iterations: (m×n) / 2^k

Converges when: (m×n) / 2^k = 1
Solving: k = log₂(m×n)

Maximum iterations: ⌈log₂(m×n)⌉

Examples:
  3×4 matrix (12 elements): log₂(12) ≈ 3.6 → 4 iterations
  10×10 matrix (100 elements): log₂(100) ≈ 6.6 → 7 iterations
  100×100 matrix (10,000 elements): log₂(10,000) ≈ 13.3 → 14 iterations

Very efficient even for large matrices!
```

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| m, n | O(1) | Matrix dimensions |
| left, right | O(1) | Binary search pointers |
| mid | O(1) | Middle index |
| row, col | O(1) | 2D coordinates |
| value | O(1) | Matrix element |
| **Total** | **O(1)** | Constant space |

**Space analysis**:
```
Only fixed number of integer variables
No arrays, no recursion
Space: O(1) ✓

Very space-efficient!
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 11`

**Expected Output:** `true`

---

**Initial State:**
```
Matrix (3×4):
  [1,  3,  5,  7]   row 0
  [10, 11, 16, 20]  row 1
  [23, 30, 34, 60]  row 2

Flattened view (conceptual):
  [1, 3, 5, 7, 10, 11, 16, 20, 23, 30, 34, 60]
   0  1  2  3   4   5   6   7   8   9  10  11 (1D indices)

m = 3, n = 4
left = 0, right = 11
target = 11
```

---

**Iteration 1:**
```
Calculate mid:
  mid = 0 + (11-0)/2 = 5
  
Convert to 2D:
  row = 5 / 4 = 1
  col = 5 % 4 = 1
  
Matrix view:
  [1,  3,  5,  7]   
  [10, 11, 16, 20]  
       ↑ (1,1)
  [23, 30, 34, 60]

value = matrix[1][1] = 11

Compare:
  11 == 11? Yes! ✓
  
Action: Return true

Found in 1 iteration!
```

---

### Another Example: Target Not Found

**Input:** `matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13`

```
Iteration 1:
  mid = 5, row=1, col=1
  matrix[1][1] = 11
  11 < 13 → search right
  left = 6

Iteration 2:
  left=6, right=11
  mid = 8, row=2, col=0
  matrix[2][0] = 23
  23 > 13 → search left
  right = 7

Iteration 3:
  left=6, right=7
  mid = 6, row=1, col=2
  matrix[1][2] = 16
  16 > 13 → search left
  right = 5

Loop ends: left=6 > right=5
Return false ✓

13 is between 11 and 16, doesn't exist
```

---

### Index Mapping Visualization

```
Matrix (3×4):
  [a₀ a₁ a₂ a₃]
  [a₄ a₅ a₆ a₇]
  [a₈ a₉ a₁₀ a₁₁]

1D indices: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11

Mapping formula: row = mid / 4, col = mid % 4

mid=0:  row=0, col=0 → a₀
mid=1:  row=0, col=1 → a₁
mid=2:  row=0, col=2 → a₂
mid=3:  row=0, col=3 → a₃
mid=4:  row=1, col=0 → a₄
mid=5:  row=1, col=1 → a₅
mid=6:  row=1, col=2 → a₆
mid=7:  row=1, col=3 → a₇
mid=8:  row=2, col=0 → a₈
mid=9:  row=2, col=1 → a₉
mid=10: row=2, col=2 → a₁₀
mid=11: row=2, col=3 → a₁₁

Perfect 1D ↔ 2D mapping!
```

---

### Binary Search Space Reduction

```
matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]]
target = 3

Flattened: [1, 3, 5, 7, 10, 11, 16, 20, 23, 30, 34, 60]

Iteration 1: [0-11] → mid=5, value=11 > 3
  Search left: [0-4]
  
Iteration 2: [0-4] → mid=2, value=5 > 3
  Search left: [0-1]
  
Iteration 3: [0-1] → mid=0, value=1 < 3
  Search right: [1-1]
  
Iteration 4: [1-1] → mid=1, value=3 == 3
  Found! ✓

Space: 12 → 5 → 2 → 1 → found
4 iterations for 12 elements
```

---

### Visual Search Path

```
Matrix:
  [1,  3,  5,  7]
  [10, 11, 16, 20]
  [23, 30, 34, 60]

Target = 16

Step 1: Check middle (index 5)
  [1,  3,  5,  7]
  [10, 11⚡16, 20]  ← 11 < 16
  [23, 30, 34, 60]
  Search right →

Step 2: Check right section (index 8)
  [1,  3,  5,  7]
  [10, 11, 16, 20]
  [23⚡30, 34, 60]  ← 23 > 16
  Search left ←

Step 3: Check between (index 6)
  [1,  3,  5,  7]
  [10, 11, 16⚡20]  ← 16 == 16 ✓
  [23, 30, 34, 60]
  Found!

3 iterations to find element!
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Lines | Iterations (avg) | Clarity | Recommended |
|----------|------|-------|------------|------------------|---------|-------------|
| **Single Binary Search** | **O(log(m×n))** | **O(1)** | **~20** | **~log₂(m×n)** | **Excellent ✅** | **Yes ✅** |
| Two Binary Searches | O(log(m×n)) | O(1) | ~35 | ~log₂m + log₂n | Good | Alternative |
| Staircase Search | O(m+n) | O(1) | ~15 | ~m+n | Simple | No ❌ |
| Linear Scan | O(m×n) | O(1) | ~10 | ~m×n/2 | Very Simple | Too slow ❌ |

**Winner**: **Single Binary Search** — cleanest code, optimal complexity!

---

## Key Takeaways

1. **Treat 2D as 1D** — matrix is sorted when flattened row by row
2. **Index mapping formula** — row = mid / n, col = mid % n
3. **Single binary search** — O(log(m×n)) time on virtual 1D array
4. **Right bound is m*n-1** — 0-indexed, don't forget -1
5. **Each row sorted + row constraint** — first of row i > last of row i-1
6. **Standard binary search logic** — compare, adjust left/right
7. **O(1) space** — only need a few variables
8. **Not Search Matrix II** — different problem, don't confuse
9. **Two approaches same complexity** — single binary vs two binary searches
10. **Perfect for this problem** — uses full sorting property

---

## Interview Tips

**What to say in an interview:**

> "This problem has a special property: each row is sorted and the first element of each row is greater than the last element of the previous row. This means if I flatten the matrix by reading it row by row, I get a completely sorted 1D array. So I can apply binary search directly on this virtual 1D array. I'll use a 1D index from 0 to m×n-1 for binary search, and convert it to 2D coordinates using: row = mid / n and col = mid % n. This gives me O(log(m×n)) time complexity with O(1) space. The key insight is recognizing that the matrix is globally sorted when viewed as a flattened array, not just sorted within rows."

**Key points to mention:**
1. **Globally sorted when flattened** — row-by-row reading gives sorted sequence
2. **1D to 2D mapping** — row = mid / n, col = mid % n
3. **Virtual 1D array** — treat m×n matrix as array of size m×n
4. **Standard binary search** — same logic as 1D array
5. **Right bound m*n-1** — 0-indexed range
6. **O(log(m×n)) time** — same as binary search on m×n elements
7. **O(1) space** — only constant variables
8. **Different from Matrix II** — that problem has weaker sorting property

**Common Follow-ups:**
- "Why not use two binary searches?" → Same complexity, but single search is simpler
- "How does index mapping work?" → Explain division for row, modulo for column
- "What if rows sorted but no row constraint?" → That's Search Matrix II, use staircase O(m+n)
- "Can you optimize further?" → No, O(log(m×n)) is optimal for this problem
- "What about space?" → Already O(1), can't do better

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Search a 2D Matrix** | Medium | **Binary Search on 2D** | **This problem** |
| Search a 2D Matrix II | Medium | Staircase Search | Weaker sorting (only rows/cols) |
| Binary Search | Easy | Standard Binary Search | 1D array |
| Find Peak Element | Medium | Binary Search | Find local maximum |
| Kth Smallest Element in Sorted Matrix | Medium | Binary Search / Heap | Matrix not globally sorted |
| Search Insert Position | Easy | Binary Search | Find insertion point |
| Guess Number Higher Or Lower | Easy | Binary Search with API | Interactive search |
| Sqrt(x) | Easy | Binary Search on Answer | Search answer space |

**Pattern Progression**:
1. **Standard binary search** — 1D sorted array
2. **Search 2D Matrix** (this problem) — 2D globally sorted
3. **Search 2D Matrix II** — 2D partially sorted (rows/cols only)
4. **Answer space search** — Binary search on possible answers

---

## Final Pattern Label

✅ **Binary Search on Virtually Flattened 2D Matrix (1D View with Index Mapping)**

**Remember:** The **matrix is globally sorted** when read row by row because each row is sorted AND first of row i > last of row i-1. This means we can treat it as a **virtual 1D sorted array** of size m×n. Use **standard binary search** with 1D index from 0 to m×n-1, and convert to 2D coordinates using **row = mid / n** and **col = mid % n**. The division gives the row number (which row of n elements), and modulo gives the column within that row. Time complexity is **O(log(m×n))** with **O(1) space**. Don't confuse with **Search a 2D Matrix II** which has weaker sorting (only rows and columns, not globally) and requires different approach (staircase search). The key insight is recognizing the **global sorted property** and applying **single binary search** with **coordinate mapping**!
