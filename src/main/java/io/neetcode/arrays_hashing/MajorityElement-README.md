# Majority Element

## Problem Description

**Difficulty**: Easy

Given an array `nums` of size `n`, return the **majority element**.

The majority element is the element that appears **more than ⌊n / 2⌋ times**. You may assume that the majority element always exists in the array.

## Examples

### Example 1:
```
Input: nums = [3,2,3]
Output: 3
Explanation: 3 appears 2 times out of 3 elements (2 > 3/2 = 1.5)
```

### Example 2:
```
Input: nums = [2,2,1,1,1,2,2]
Output: 2
Explanation: 2 appears 4 times out of 7 elements (4 > 7/2 = 3.5)
```

### Example 3:
```
Input: nums = [1]
Output: 1
Explanation: Only one element, which is the majority
```

## Constraints
- 1 <= nums.length <= 5 * 10^4
- -10^9 <= nums[i] <= 10^9

- Follow-up: Could you solve in **O(n) time** and **O(1) space**?

---

## Pattern Recognition

**Primary Pattern**: **Boyer-Moore Voting Algorithm**

**Why This Pattern?**
- The majority element appears **more than all other elements combined** (> n/2 times)
- This lets us "cancel" each majority vote against one non-majority vote — majority always survives
- Only Boyer-Moore satisfies **both** O(n) time and O(1) space required by the follow-up

**Key Insight**:
```
Let M = count of majority element, O = count of all others combined
    M + O = n   and   M > n/2
    ∴ M > O

Pair and cancel one M with one O → M always has leftover count.
The last surviving candidate must be the majority element.
```

**Pattern Elimination:**

| Pattern | Time | Space | Meets Follow-up? | Why |
|---------|------|-------|-----------------|-----|
| HashMap | O(n) | O(n) | ❌ | Extra space |
| Sorting | O(n log n) | O(1) | ❌ | Too slow |
| **Boyer-Moore** | ✅ O(n) | ✅ O(1) | **✅ Yes** | Optimal |

**Related Patterns**:
1. **Majority Element II** — Extended Boyer-Moore with 2 candidates (> n/3)
2. **HashMap Frequency Count** — General frequency pattern (Valid Anagram, Top K)
3. **Single Number** — XOR-based cancellation (same pairing/cancellation intuition)

---

## Algorithm & Approach

### Core Insight

**The Cancellation Principle:**

```
Think of each element as a vote:
  Majority element = positive votes (+1)
  All other elements = negative votes (-1)

When running total (count) hits 0:
  → Current lead is completely cancelled out
  → Pick a fresh candidate

Since M > O (majority > all others):
  → After all cancellations, majority element always survives as the final candidate
```

**Decision Flow:**
```
majorityElement(nums):
    candidate = 0, count = 0

    for each num in nums:
        ├─ If count == 0  → candidate = num   (pick new candidate)
        ├─ If num == candidate → count++      (support current candidate)
        └─ If num != candidate → count--      (cancel one vote)

    return candidate   (majority guaranteed → no verification needed)
```

### Visual Understanding

```
Array:  [2, 2, 1, 1, 1, 2, 2]
         M  M  O  O  O  M  M    (M = majority=2, O = other)

Pairing M against O (cancellations):
  2 vs 1 → cancel
  2 vs 1 → cancel
  2 vs 1 → cancel
  Remaining: [2, 2]  ← majority survives ✓
```

```
Boyer-Moore trace — nums = [7,7,5,7,5,1,5,7,5,5,7,7,7]:

 num | candidate | count | Action
-----|-----------|-------|---------------------------
  7  |     7     |   1   | count=0 → candidate=7, +1
  7  |     7     |   2   | matches → +1
  5  |     7     |   1   | no match → -1
  7  |     7     |   2   | matches → +1
  5  |     7     |   1   | no match → -1
  1  |     7     |   0   | no match → -1
  5  |     5     |   1   | count=0 → candidate=5, +1
  7  |     5     |   0   | no match → -1
  5  |     5     |   1   | count=0 → candidate=5, +1
  5  |     5     |   2   | matches → +1
  7  |     5     |   1   | no match → -1
  7  |     5     |   0   | no match → -1
  7  |     7     |   1   | count=0 → candidate=7, +1

Final candidate: 7  (appears 7/13 times > 6.5) ✓
```

---

### Step-by-Step Algorithm

#### **Approach 1: HashMap Frequency Counting**

**Core Idea**:
- Count each element's frequency using a HashMap
- Return the element whose count exceeds n/2

**Code Implementation**
```java
class Solution {
    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = nums.length;

        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.get(num) > n / 2) {
                return num;  // early exit once majority found
            }
        }

        return -1;  // unreachable — majority is guaranteed
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) — single pass
- **Space Complexity**: O(n) — HashMap stores up to n entries

---

#### **Approach 2: Sorting**

**Core Idea**:
- After sorting, the majority element must occupy index `n/2` because it fills more than half the array

```
[2,2,1,1,1,2,2] → sorted → [1,1,1,2,2,2,2]
 Index:                      0  1  2  3  4  5  6
                                    ↑ n/2 = 3 → nums[3] = 2 ✓
```

**Code Implementation**
```java
class Solution {
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n log n) — sorting
- **Space Complexity**: O(1) — in-place sort

---

#### **Approach 3: Boyer-Moore Voting Algorithm (OPTIMAL)**

**Core Idea**:
- Maintain `candidate` and `count`
- When `count == 0` → switch to current element as new candidate
- `+1` for a match, `-1` for a mismatch
- The surviving candidate is the majority element

**Code Implementation**
```java
class Solution {
    public int majorityElement(int[] nums) {
        int candidate = 0;
        int count = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;    // pick new candidate
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;  // no verification needed — majority is guaranteed
    }
}
```

**Step-by-Step Trace:**

Input: nums = [2,2,1,1,1,2,2]

| i | num | count (before) | Action | candidate | count (after) |
|---|-----|---------------|--------|-----------|---------------|
| 0 | 2 | 0 | count=0 → candidate=2, +1 | 2 | 1 |
| 1 | 2 | 1 | matches → +1 | 2 | 2 |
| 2 | 1 | 2 | no match → -1 | 2 | 1 |
| 3 | 1 | 1 | no match → -1 | 2 | 0 |
| 4 | 1 | 0 | count=0 → candidate=1, +1 | 1 | 1 |
| 5 | 2 | 1 | no match → -1 | 1 | 0 |
| 6 | 2 | 0 | count=0 → candidate=2, +1 | 2 | 1 |

**Return candidate = 2** ✓ (2 appears 4/7 times > 3.5)

**Complexity Analysis**
- **Time Complexity**: O(n) — single pass
- **Space Complexity**: O(1) — only two variables

---

## Comparison of Approaches

| Aspect | HashMap | Sorting | Boyer-Moore |
|--------|---------|---------|-------------|
| **Time Complexity** | O(n) | O(n log n) | ✅ O(n) |
| **Space Complexity** | O(n) | O(1) | ✅ O(1) |
| **Meets Follow-up?** | ❌ extra space | ❌ too slow | ✅ Yes |
| **Code Simplicity** | Simple | ✅ Trivial | Simple |
| **Preferred?** | Brute force | Brute force | ✅ Always |

**Recommendation**: Use **Boyer-Moore** — the only approach satisfying both O(n) time and O(1) space. State HashMap first in interviews, then optimize.

---

## Key Takeaways

1. **Majority > All Others Combined**
   - Since majority appears > n/2 times: `M > n - M` → `M > O`
   - This is the mathematical foundation that makes Boyer-Moore correct

2. **Check count == 0 BEFORE Updating**
   - Switch candidate when count reaches 0, then update count
   - Order matters: check → assign → update

3. **No Verification Needed When Majority is Guaranteed**
   - Problem says majority always exists → return candidate directly
   - Only add a second verification pass if majority is NOT guaranteed

4. **count is Not the Final Frequency**
   - `count` at the end is NOT how many times majority appears
   - It only tracks the current candidate's lead over opponents

5. **Sorting Trick as a Quick Alternative**
   - `nums[n/2]` after sorting always gives the majority element
   - Simple O(n log n) fallback when Boyer-Moore is too complex to explain

---

## Common Pitfalls

❌ **Mistake 1**: Checking count == 0 AFTER updating (wrong order)
```java
// WRONG: count is updated first — candidate switches one step too late
for (int num : nums) {
    count += (num == candidate) ? 1 : -1;
    if (count == 0) candidate = num;  // ← must be at the TOP
}
```
✅ **Correct**: Check count BEFORE the update
```java
if (count == 0) candidate = num;
count += (num == candidate) ? 1 : -1;
```

❌ **Mistake 2**: Never switching the candidate
```java
// WRONG: candidate is fixed to first element — never changes
int candidate = nums[0];
for (int num : nums) {
    if (num == candidate) count++;
    else count--;
}
```
✅ **Correct**: Switch candidate whenever count drops to 0
```java
if (count == 0) candidate = num;
```

❌ **Mistake 3**: Using `>= n/2` instead of `> n/2` in HashMap check
```java
// WRONG: majority means STRICTLY more than half, not equal to
if (map.get(num) >= n / 2) return num;
```
✅ **Correct**: Strict greater-than
```java
if (map.get(num) > n / 2) return num;
```

❌ **Mistake 4**: Adding unnecessary verification when majority is guaranteed
```java
// UNNECESSARY: wastes an extra O(n) pass
int verify = 0;
for (int n : nums) if (n == candidate) verify++;
if (verify > nums.length / 2) return candidate;
return -1;
```
✅ **Correct**: Simply return when majority is guaranteed
```java
return candidate;
```

---

## Related Problems

1. **Majority Element II** (Medium) — Find all elements appearing > n/3 times; 2-candidate Boyer-Moore
2. **Single Number** (Easy) — XOR cancellation; same pairing/cancellation intuition
3. **Find the Duplicate Number** (Medium) — In-place array index manipulation
4. **Top K Frequent Elements** (Medium) — Frequency counting with HashMap + Bucket Sort
5. **Contains Duplicate** (Easy) — Existence-based HashSet pattern

---

## Edge Cases to Consider

1. **Single Element**
   ```
   nums = [1]
   count=0 → candidate=1, count=1 → return 1 ✓
   ```

2. **All Same Elements**
   ```
   nums = [5,5,5,5,5]
   count never drops to 0 → candidate stays 5 → return 5 ✓
   ```

3. **Majority at Start**
   ```
   nums = [1,1,1,2,3]
   1 appears 3/5 times → candidate=1 survives → return 1 ✓
   ```

4. **Majority at End**
   ```
   nums = [1,2,3,3,3]
   candidate switches until 3 dominates at the end → return 3 ✓
   ```

5. **Exactly ⌊n/2⌋ + 1 Occurrences (Minimum Majority)**
   ```
   nums = [1,2,1,3,1]  (1 appears 3/5 times)
   1 still survives all cancellations → return 1 ✓
   ```

6. **Negative Numbers**
   ```
   nums = [-1,-1,2,-1]
   -1 appears 3/4 times → return -1 ✓
   HashMap and Boyer-Moore both handle negatives naturally
   ```

---

## Summary

**Problem**: Find the element appearing more than ⌊n/2⌋ times in an array (guaranteed to exist).

**Solution**:
- Use **Boyer-Moore Voting Algorithm**: maintain `candidate` and `count`
- When `count == 0` → switch `candidate` to current element
- Increment count on match, decrement on mismatch
- Return `candidate` at the end

**Time**: O(n) | **Space**: O(1)

**Pattern**: Boyer-Moore Voting. The majority element always survives all cancellations because it appears more times than all other elements combined. This is the only approach satisfying both O(n) time and O(1) space.
