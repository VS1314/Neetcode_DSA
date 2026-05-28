# Sort Colors

## Problem Description

**Difficulty**: Medium

Given an integer array `nums` where:
- `0` = red
- `1` = white
- `2` = blue

Sort the array **in-place** so that all `0`s come first, then `1`s, then `2`s.

You must not use built-in sort.

---

## Examples

### Example 1
```text
Input: nums = [1,0,1,2]
Output: [0,1,1,2]
```

### Example 2
```text
Input: nums = [2,1,0]
Output: [0,1,2]
```

---

## Constraints

- `1 <= nums.length <= 300`
- `0 <= nums[i] <= 2`

---

## Approach 1: Counting (Two Passes)

Count how many `0`s, `1`s, and `2`s, then overwrite the array in that order.

### Java
```java
public void sortColors(int[] nums) {
    int[] count = new int[3];

    for (int num : nums) {
        count[num]++;
    }

    int i = 0;
    for (int color = 0; color <= 2; color++) {
        while (count[color]-- > 0) {
            nums[i++] = color;
        }
    }
}
```

- **Time**: `O(n)`
- **Space**: `O(1)` (array size is fixed: 3)

---

## Approach 2 (Follow-up): Dutch National Flag (One Pass)

Use three pointers:
- `low` = next position for `0`
- `mid` = current index
- `high` = next position for `2` from right

Rules while `mid <= high`:
- If `nums[mid] == 0`: swap with `low`, increment both `low` and `mid`
- If `nums[mid] == 1`: just increment `mid`
- If `nums[mid] == 2`: swap with `high`, decrement `high` only

### Java
```java
public void sortColors(int[] nums) {
    int low = 0, mid = 0, high = nums.length - 1;

    while (mid <= high) {
        if (nums[mid] == 0) {
            swap(nums, low, mid);
            low++;
            mid++;
        } else if (nums[mid] == 1) {
            mid++;
        } else {
            swap(nums, mid, high);
            high--;
        }
    }
}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}
```

- **Time**: `O(n)`
- **Space**: `O(1)`
- **Best for follow-up**: one pass + constant extra space

---

## Quick Walkthrough (Dutch National Flag)

For `nums = [2,0,2,1,1,0]`:

1. Start: `low=0, mid=0, high=5`
2. `nums[mid]=2` -> swap(mid, high) => `[0,0,2,1,1,2]`, `high=4`
3. `nums[mid]=0` -> swap(low, mid) => `[0,0,2,1,1,2]`, `low=1, mid=1`
4. `nums[mid]=0` -> swap(low, mid) => `[0,0,2,1,1,2]`, `low=2, mid=2`
5. `nums[mid]=2` -> swap(mid, high) => `[0,0,1,1,2,2]`, `high=3`
6. `nums[mid]=1` -> `mid=3`
7. `nums[mid]=1` -> `mid=4` stop (`mid > high`)

Result: `[0,0,1,1,2,2]`

---

## Why This Works

The array is divided into 4 regions during the one-pass method:
- `[0 ... low-1]` are all `0`
- `[low ... mid-1]` are all `1`
- `[mid ... high]` are unknown
- `[high+1 ... n-1]` are all `2`

Each step shrinks the unknown region, so the algorithm finishes in linear time.

---

## Common Mistakes

- Incrementing `mid` after swapping with `high` (wrong)
  - The new value at `mid` is unprocessed, so check it again.
- Using built-in sort (not allowed by problem).
- Forgetting in-place requirement.

---

## Final Recommendation

Use **Dutch National Flag** for interviews:
- Meets follow-up exactly
- One pass, constant space
- Clean pointer logic

