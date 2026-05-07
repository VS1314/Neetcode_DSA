# Two Sum

## Problem Description

**Difficulty**: Easy

Given an array of integers `nums` and an integer `target`, return the **indices** `i` and `j` such that `nums[i] + nums[j] == target` and `i != j`.

You may assume that every input has **exactly one pair** of indices that satisfy the condition.

Return the answer with the **smaller index first**.

## Examples

### Example 1:
```
Input:  nums = [3,4,5,6], target = 7
Output: [0,1]
Explanation: nums[0] + nums[1] = 3 + 4 = 7
```

### Example 2:
```
Input:  nums = [4,5,6], target = 10
Output: [0,2]
Explanation: nums[0] + nums[2] = 4 + 6 = 10
```

### Example 3:
```
Input:  nums = [5,5], target = 10
Output: [0,1]
Explanation: nums[0] + nums[1] = 5 + 5 = 10
```

## Constraints
- 2 <= nums.length <= 1000
- -10,000,000 <= nums[i] <= 10,000,000
- -10,000,000 <= target <= 10,000,000
- Only one valid answer exists

---

## Pattern Recognition

**Primary Pattern**: **Hashing — Complement Lookup**

**Why This Pattern?**
- For every element `nums[i]`, we need to check if its **complement** (`target - nums[i]`) has already been seen
- A `HashMap` gives O(1) lookup — turning a brute-force O(n²) search into a single O(n) pass
- The array is **not sorted**, so Two Pointers cannot be directly applied (sorting would destroy the original indices)

**Key Insight**:
```
nums[i] + nums[j] == target
         ↓  rearrange
nums[j] == target - nums[i]

So: for each nums[i], look up (target - nums[i]) in the map.
If found → return {map.get(complement), i}
If not   → store nums[i] → i in the map and continue
```

**Pattern Elimination:**

| Pattern | Needed? | Why |
|---------|---------|-----|
| Two Pointers | ❌ | Array is unsorted; sorting loses original indices |
| Binary Search | ❌ | Unsorted array |
| Sliding Window | ❌ | No subarray/range required |
| Brute Force O(n²) | ⚠️ | Works but checks every pair — too slow |
| **Hashing** | ✅ | O(1) complement lookup per element |

**Related Patterns**:
1. **Valid Anagram** — Hashing for frequency counting
2. **Contains Duplicate** — Hashing for existence check
3. **Subarray Sum Equals K** — HashMap with prefix sums (extension of complement lookup)

---

## Algorithm & Approach

### Core Insight

**The Complement Trick:**

```
For any pair (i, j) where nums[i] + nums[j] = target:
    nums[j] = target - nums[i]   ← this is the "complement" of nums[i]

As we scan left to right:
    → Store every element we've seen so far in a map: value → index
    → For the current element, check if its complement already exists in the map
    → If yes → we found the pair (stored index, current index)
    → If no  → add current element to map and move on
```

**Why this gives smaller index first automatically:**
- We process left to right and store earlier elements in the map
- When we find a match, `map.get(complement)` returns the **earlier (smaller) index**
- The current index `i` is always the **larger** index
- So returning `{map.get(complement), i}` always gives `[smaller, larger]` ✓

**Decision Flow:**
```
twoSum(nums, target):
    map = {}  (value → index)

    for i from 0 to n-1:
        ├─ complement = target - nums[i]
        ├─ If complement exists in map:
        │   └─ return [map.get(complement), i]  ← found!
        └─ Else:
            └─ map.put(nums[i], i)              ← store for future lookups
```

### Visual Understanding

```
Example 1: nums = [3,4,5,6], target = 7

i=0: nums[0]=3, complement=7-3=4
     map={} → 4 not found → store {3:0}
     map = {3:0}

i=1: nums[1]=4, complement=7-4=3
     map={3:0} → 3 FOUND at index 0!
     return [0, 1]  ✓

Answer: [0, 1]
```

```
Example 2: nums = [4,5,6], target = 10

i=0: nums[0]=4, complement=10-4=6
     map={} → 6 not found → store {4:0}
     map = {4:0}

i=1: nums[1]=5, complement=10-5=5
     map={4:0} → 5 not found → store {5:1}
     map = {4:0, 5:1}

i=2: nums[2]=6, complement=10-6=4
     map={4:0, 5:1} → 4 FOUND at index 0!
     return [0, 2]  ✓

Answer: [0, 2]
```

```
Example 3: nums = [5,5], target = 10  (duplicate values)

i=0: nums[0]=5, complement=10-5=5
     map={} → 5 not found → store {5:0}
     map = {5:0}

i=1: nums[1]=5, complement=10-5=5
     map={5:0} → 5 FOUND at index 0!
     return [0, 1]  ✓  (i != j guaranteed since map only stores earlier index)

Answer: [0, 1]
```

---

### Step-by-Step Algorithm

#### **Approach 1: HashMap — Complement Lookup (OPTIMAL)**

**Core Idea**:
- Single pass: for each element, check if its complement exists in the map
- Store elements as we go — the map always holds only previously seen elements
- This guarantees `i != j` (we never look up the current element against itself)

**Code Implementation**
```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();  // value → index

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (map.containsKey(complement)) {
                // complement was seen earlier → return [earlier index, current index]
                return new int[]{map.get(complement), i};
            }

            // Not found yet → store current element for future lookups
            map.put(nums[i], i);
        }

        return new int[]{};  // unreachable (problem guarantees exactly one solution)
    }
}
```

**Step-by-Step Trace:**

Input: nums = [3,4,5,6], target = 7

| i | nums[i] | complement | map (before) | Found? | map (after) |
|---|---------|------------|--------------|--------|-------------|
| 0 | 3 | 4 | {} | ❌ | {3:0} |
| 1 | 4 | 3 | {3:0} | ✅ → return [0,1] | — |

Input: nums = [4,5,6], target = 10

| i | nums[i] | complement | map (before) | Found? | map (after) |
|---|---------|------------|--------------|--------|-------------|
| 0 | 4 | 6 | {} | ❌ | {4:0} |
| 1 | 5 | 5 | {4:0} | ❌ | {4:0, 5:1} |
| 2 | 6 | 4 | {4:0, 5:1} | ✅ → return [0,2] | — |

**Complexity Analysis**
- **Time Complexity**: O(n)
  - Single pass through the array
  - Each HashMap lookup and insert is O(1) average
- **Space Complexity**: O(n)
  - In the worst case, all n elements are stored in the map before a match is found

---

#### **Approach 2: Brute Force — Nested Loops (SIMPLE but suboptimal)**

**Core Idea**:
- Check every possible pair (i, j) where i < j
- Return the pair whose sum equals target

**Code Implementation**
```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};  // unreachable
    }
}
```

**Why This is Suboptimal**:
- Checks every pair — O(n²) time
- Fine to state in interviews as a starting point, then optimize

**Complexity Analysis**
- **Time Complexity**: O(n²)
- **Space Complexity**: O(1) — no extra space used

---

## Comparison of Approaches

| Aspect | HashMap (Complement) | Brute Force |
|--------|---------------------|-------------|
| **Time Complexity** | ✅ O(n) | O(n²) |
| **Space Complexity** | O(n) | ✅ O(1) |
| **Code Simplicity** | Simple | ✅ Trivial |
| **Scales to n=10⁶?** | ✅ Yes | ❌ No |
| **Preferred?** | ✅ Always | Only as starting point |

**Recommendation**: Always use the **HashMap approach** — it's the expected O(n) solution. Mention brute force first in interviews to show you understand the problem, then immediately optimize.

---

## Key Takeaways

1. **The Complement Trick is the Core**
   - `nums[i] + nums[j] = target` rearranges to `nums[j] = target - nums[i]`
   - This transforms "find a pair" into "find one value" — a simple O(1) map lookup

2. **Store as You Go (Not Before)**
   - By checking the map BEFORE inserting the current element, we guarantee `i != j`
   - If we inserted first then checked, `nums[i]` could match itself when `2 * nums[i] == target`

3. **Smaller Index First is Automatic**
   - We scan left to right; the map always holds earlier indices
   - `map.get(complement)` always returns the smaller index, current `i` is always larger

4. **Two Pointers Won't Work Here**
   - Two pointers require a sorted array
   - Sorting destroys original indices — you'd need to remap, adding complexity
   - HashMap is cleaner and faster for this problem

5. **Exactly One Solution Simplifies Things**
   - The guarantee of exactly one valid pair means we never need to handle "no solution" or "multiple solutions"

---

## Common Pitfalls

❌ **Mistake 1**: Inserting into the map BEFORE checking (allows matching with self)
```java
// WRONG: if target = 10 and nums[i] = 5, it matches itself!
map.put(nums[i], i);
if (map.containsKey(complement)) { ... }
```
✅ **Correct**: Check FIRST, then insert
```java
if (map.containsKey(complement)) { return ...; }
map.put(nums[i], i);
```

❌ **Mistake 2**: Using Two Pointers on an unsorted array
```java
// WRONG: two pointers only work on sorted arrays
int left = 0, right = nums.length - 1;
while (left < right) { ... }  // incorrect on [3,4,5,6] for target=7
```
✅ **Correct**: Use HashMap for unsorted arrays (or sort + remap indices if Two Pointers is required)

❌ **Mistake 3**: Returning indices in wrong order
```java
// WRONG: may return [larger, smaller]
return new int[]{i, map.get(complement)};
```
✅ **Correct**: map always holds the earlier (smaller) index
```java
return new int[]{map.get(complement), i};
```

❌ **Mistake 4**: Using an array instead of HashMap for large value ranges
```java
// WRONG: values range from -10,000,000 to 10,000,000 — array too large
int[] seen = new int[20_000_001];
```
✅ **Correct**: Use HashMap — handles negative values and large ranges naturally
```java
HashMap<Integer, Integer> map = new HashMap<>();
```

---

## Related Problems

1. **Contains Duplicate** (Easy) — Hashing for existence check (simpler complement: just check if seen)
2. **Valid Anagram** (Easy) — Hashing for frequency counting
3. **3Sum** (Medium) — Sort + Two Pointers (fix one element, two-pointer on the rest)
4. **4Sum** (Medium) — Extension of 3Sum with one more loop
5. **Subarray Sum Equals K** (Medium) — Prefix sum + HashMap (complement lookup on cumulative sums)
6. **Two Sum II — Input Array Is Sorted** (Medium) — Two Pointers (sorted → no HashMap needed)

---

## Edge Cases to Consider

1. **Duplicate Values**
   ```
   nums = [5,5], target = 10
   i=0: complement=5, map={} → not found → store {5:0}
   i=1: complement=5, map={5:0} → FOUND → return [0,1] ✓
   Inserting after checking prevents self-match
   ```

2. **Negative Numbers**
   ```
   nums = [-3, 7], target = 4
   i=0: complement=4-(-3)=7, map={} → not found → store {-3:0}
   i=1: complement=4-7=-3, map={-3:0} → FOUND → return [0,1] ✓
   HashMap handles negatives naturally
   ```

3. **Target Larger Than All Elements**
   ```
   nums = [1,2,9], target = 11
   Pair: nums[1]+nums[2] = 2+9 = 11
   Answer: [1,2]
   ```

4. **First and Last Elements Form the Pair**
   ```
   nums = [2,5,8,3], target = 5
   i=0: store {2:0}
   i=1: complement=0, not found → store {5:1}
   i=2: complement=-3, not found → store {8:2}
   i=3: complement=2, FOUND at index 0 → return [0,3] ✓
   ```

5. **Array of Length 2 (Minimum)**
   ```
   nums = [3,7], target = 10
   i=0: complement=7, not found → store {3:0}
   i=1: complement=3, FOUND → return [0,1] ✓
   ```

---

## Summary

**Problem**: Find two indices in an unsorted array whose values sum to `target`. Return smaller index first.

**Solution**:
- Single pass with a `HashMap<value, index>`
- For each element, compute `complement = target - nums[i]`
- If complement is in the map → return `[map.get(complement), i]`
- Otherwise → store `nums[i] → i` in the map and continue

**Time**: O(n) | **Space**: O(n)

**Pattern**: Hashing — Complement Lookup. The key insight is rearranging `a + b = target` into `b = target - a`, turning a pair-search into a single-value lookup. Always check the map before inserting to avoid matching an element with itself.
