# Find in Mountain Array

## Problem Pattern

- **Pattern:** Binary Search (with variations)
- **Why:** The problem requires efficient searching in a special array (mountain array) with strict constraints on the number of allowed accesses. Binary search is optimal for such scenarios.

## Approach

### 1. Understanding the Mountain Array

- A mountain array increases strictly to a single peak, then decreases strictly.
- Example: `[1, 3, 5, 4, 2]` (peak at index 2, value 5).

### 2. Steps to Solve

#### Step 1: Find the Peak Index

- Use binary search to find the peak (maximum) element.
- At each step, compare `arr[mid]` with `arr[mid+1]`:
  - If `arr[mid] < arr[mid+1]`, move right (`l = mid + 1`).
  - Else, move left (`r = mid`).
- Stop when `l == r` (peak found).

#### Step 2: Binary Search on Ascending Part

- Perform standard binary search from start to peak.
- If target found, return index.

#### Step 3: Binary Search on Descending Part

- Perform binary search from peak+1 to end, but reverse the comparison:
  - If `arr[mid] < target`, move left (`r = mid - 1`).
  - If `arr[mid] > target`, move right (`l = mid + 1`).

#### Step 4: Return Result

- If target is not found in either part, return -1.

## Why This Strategy?

- **Efficiency:** Each binary search is O(log n), and we do at most 3 searches.
- **API Constraints:** We minimize calls to `get()` by not scanning linearly.
- **Correctness:** The mountain property guarantees only one peak and two monotonic subarrays.

## Algorithm

```java
// Pseudocode
1. Find peak index using binary search.
2. Binary search for target in [0, peak] (ascending).
3. If not found, binary search for target in [peak+1, n-1] (descending).
4. Return the minimum index found, or -1 if not found.
```

## Example

Input: `mountainArr = [1,2,3,4,2,1], target = 2`

- Peak at index 3 (value 4).
- Search [0,3]: find 2 at index 1.
- Return 1.

Input: `mountainArr = [2,4,5,2,1], target = 2`

- Peak at index 2 (value 5).
- Search [0,2]: find 2 at index 0.
- Return 0.

Input: `mountainArr = [1,2,3,4,2,1], target = 6`

- Not found in either part.
- Return -1.

## Key Points

- Use binary search to minimize API calls.
- Handle both ascending and descending parts.
- Always check both sides for the minimum index.
