Cool, this is a classic binary search + matrix trick.
I’ll explain it slowly, line-by-line, and also show every step with an example, so nothing feels magical.

🔍 Key Observation (MOST IMPORTANT)

Given:

Each row is sorted

First element of a row > last element of previous row

👉 This means the entire matrix is sorted if you read it row by row.

Example matrix:

[
[ 1,  2,  4,  8],
[10, 11, 12, 13],
[14, 20, 30, 40]
]


If you flatten it:

[1, 2, 4, 8, 10, 11, 12, 13, 14, 20, 30, 40]


This is a sorted array ✅
So we can do binary search in O(log(m*n)).

🧠 Trick: Treat 2D matrix as 1D array

Let:

m = number of rows

n = number of columns

Index mapping:

If we are at 1D index mid, then:

row = mid / n;
col = mid % n;


Why?

Each row has n elements

/ tells which row

% tells position inside that row

✅ Full Code (Correct & Interview-Ready)
class Solution {
public boolean searchMatrix(int[][] matrix, int target) {

        int m = matrix.length;
        int n = matrix[0].length;

        int l = 0;
        int r = m * n - 1;

        while (l <= r) {

            int mid = l + (r - l) / 2;

            int row = mid / n;
            int col = mid % n;

            int midValue = matrix[row][col];

            if (midValue == target) {
                return true;
            } else if (midValue < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return false;
    }
}

🪜 Step-by-Step Walkthrough (Example 1)
Input
matrix = [[1,2,4,8],
[10,11,12,13],
[14,20,30,40]]
target = 10


m = 3, n = 4

Total elements = 12

l = 0, r = 11

🔁 Iteration 1
mid = 0 + (11 - 0) / 2 = 5
row = 5 / 4 = 1
col = 5 % 4 = 1

matrix[1][1] = 11


Compare:

11 > 10


👉 Move left:

r = mid - 1 = 4

🔁 Iteration 2
l = 0, r = 4
mid = 0 + (4 - 0) / 2 = 2
row = 2 / 4 = 0
col = 2 % 4 = 2

matrix[0][2] = 4


Compare:

4 < 10


👉 Move right:

l = mid + 1 = 3

🔁 Iteration 3
l = 3, r = 4
mid = 3 + (4 - 3) / 2 = 3
row = 3 / 4 = 0
col = 3 % 4 = 3

matrix[0][3] = 8


Compare:

8 < 10


👉 Move right:

l = 4

🔁 Iteration 4
l = 4, r = 4
mid = 4
row = 4 / 4 = 1
col = 4 % 4 = 0

matrix[1][0] = 10


🎯 FOUND → return true

⏱ Time & Space Complexity
Metric	Value
Time	O(log(m * n))
Space	O(1)
💡 Interview Explanation (1 line)

“Because the matrix is globally sorted, I treat it as a 1D array and apply binary search using index-to-row/column mapping.”