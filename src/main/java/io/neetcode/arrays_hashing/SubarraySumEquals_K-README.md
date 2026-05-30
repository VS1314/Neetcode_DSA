# Subarray Sum Equals K

## Problem Description

**Difficulty**: Medium

Given an array of integers `nums` and an integer `k`, return the **total number of subarrays whose sum equals `k`**.

A subarray is a contiguous non-empty sequence of elements within an array.

## Examples

### Example 1:
```
Input: nums = [2,-1,1,2], k = 2

Output: 4
Explanation: The 4 subarrays with sum = 2 are:
  [2]         → starts at index 0
  [2,-1,1]    → starts at index 0, ends at index 2
  [-1,1,2]    → starts at index 1, ends at index 3
  [2]         → starts at index 3
```

### Example 2:
```
Input: nums = [4,4,4,4,4,4], k = 4

Output: 6
Explanation: Each individual element 4 forms a valid subarray.
             n=6 elements, each one individually sums to k.
```

## Constraints
- 1 <= nums.length <= 20,000
- -1,000 <= nums[i] <= 1,000
- -10,000,000 <= k <= 10,000,000

---

## Pattern Recognition

**Primary Pattern**: **Prefix Sum + HashMap (Frequency Count)**

**Why This Pattern?**
- We need counts of subarrays — counting, not finding
- Array contains **negative numbers** → sliding window does NOT work (window shrinking is invalid when negatives can decrease sum unpredictably)
- The prefix sum identity `prefixSum[i] - prefixSum[j] = subarray sum from j+1 to i` lets us turn a subarray-sum problem into a lookup problem
- HashMap stores how many times each prefix sum has been seen — one pass, O(n) time

**Key Mathematical Identity:**
```
A subarray from index j+1 to i has sum = k  ↔  prefixSum[i] - prefixSum[j] = k

Rearranged:
  prefixSum[j] = prefixSum[i] - k

So: at each index i, how many previous indices j gave prefixSum[j] = prefixSum[i] - k?
    → look up (currentSum - k) in the HashMap
```

**Key Insight — Why map.put(0, 1) as base case?**
```
If the entire subarray from index 0 to i sums to k:
  prefixSum[i] - prefixSum[-1] = k
  prefixSum[-1] = 0  ← the "empty prefix" before index 0 has sum 0

Without putting 0→1 in the map, subarrays starting at index 0 would be missed.
```

**Related Patterns**:
1. **Range Sum Query** — 2D extension of prefix sums
2. **Products of Array Except Self** — left/right precompute, same prefix philosophy
3. **Continuous Subarray Sum** — same prefix + map, check divisibility instead of equality
4. **Longest Subarray with Sum K** — prefix sum + map, track index instead of count

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**

```
Brute force: check all O(n²) subarrays, compute each sum in O(n) → O(n³)
Better brute force: precompute prefix sums, check all pairs → O(n²)

n = 20,000: O(n²) = 400,000,000 operations ❌

We need O(n).
```

**The Prefix Sum Trick:**

```
nums       =  [ 2,  -1,   1,   2 ]
prefixSum  =  [ 2,   1,   2,   4 ]   (running sum from left)

Add a virtual "0" before the array:
  prefixSum[-1] = 0  (covered by map.put(0, 1))

Now subarray sum from index j+1 to i:
  = prefixSum[i] - prefixSum[j]

We want: prefixSum[i] - prefixSum[j] = k
So:      prefixSum[j] = prefixSum[i] - k

At each position i:
  → look up (prefixSum[i] - k) in map
  → the count stored there is how many valid j's exist
  → add that count to answer
```

### Visual Understanding

```
nums = [2, -1, 1, 2],  k = 2

 index:       -1   0   1   2   3
 prefixSum:    0   2   1   2   4
               ↑
           base case (map: {0→1})

Valid subarray pairs (j, i) where prefixSum[i] - prefixSum[j] = 2:

  (−1, 0): 2 − 0 = 2  ✓  → subarray [2]
  (−1, 2): 2 − 0 = 2  ✓  → subarray [2,-1,1]
  ( 1, 3): 4 − 2 = 2  ✓  → subarray [-1,1,2]... wait
  ( 0, 3): 4 − 2 = 2  ✓  → subarray [2] (index 3)

At i=3, sum=4: look up 4-2=2 in map → map has 2 stored twice → +2 to count
```

### Step-by-Step Algorithm

---

#### **Approach 1: Prefix Sum + HashMap — OPTIMAL**

**Core Idea**:
- Maintain a running `sum` (prefix sum up to current index)
- Maintain a `map` of `{prefixSum → frequency}`
- Before starting, put `map.put(0, 1)` as the base case
- At each element: add it to `sum`, look up `(sum - k)` in map, add its count to result, then record `sum` in map

**Algorithm**
```
1. map = {0: 1}
2. sum = 0, count = 0
3. for each num in nums:
       sum += num
       if (sum - k) in map:
           count += map[sum - k]
       map[sum] += 1
4. return count
```

**Code Implementation**
```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);   // base case: empty prefix

    int sum = 0;
    int count = 0;

    for (int num : nums) {
        sum += num;

        if (map.containsKey(sum - k)) {
            count += map.get(sum - k);
        }

        map.put(sum, map.getOrDefault(sum, 0) + 1);
    }

    return count;
}
```

**Example Walkthrough**

Input: `nums = [2,-1,1,2]`, `k = 2`

| Step | num | sum | sum-k | map has (sum-k)? | count | map after |
|------|-----|-----|-------|-----------------|-------|-----------|
| init | -   | 0   | -     | -               | 0     | {0→1} |
| 1    | 2   | 2   | 0     | YES (×1)        | 1     | {0→1, 2→1} |
| 2    | -1  | 1   | -1    | NO              | 1     | {0→1, 2→1, 1→1} |
| 3    | 1   | 2   | 0     | YES (×1)        | 2     | {0→1, 2→2, 1→1} |
| 4    | 2   | 4   | 2     | YES (×2)        | 4     | {0→1, 2→2, 1→1, 4→1} |

**Return 4** ✓

Step 4 found 2 subarrays at once because `sum=2` appeared twice in the map:
- Once from the "0 prefix" (covers `[2,-1,1]`)
- Once from `sum=2` at index 2 (covers `[2]` at index 3)

**Complexity Analysis**
- **Time Complexity**: O(n) — single pass, O(1) HashMap operations
- **Space Complexity**: O(n) — HashMap stores at most n+1 distinct prefix sums

---

#### **Approach 2: Brute Force with Prefix Array (ALTERNATIVE)**

**Core Idea**: Precompute all prefix sums, then check all pairs `(i, j)`.

**Code Implementation**
```java
public int subarraySum(int[] nums, int k) {
    int n = nums.length;
    int[] prefix = new int[n + 1];
    for (int i = 0; i < n; i++) {
        prefix[i + 1] = prefix[i] + nums[i];
    }

    int count = 0;
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j <= n; j++) {
            if (prefix[j] - prefix[i] == k) count++;
        }
    }
    return count;
}
```

**Complexity Analysis**
- **Time Complexity**: O(n²)
- **Space Complexity**: O(n)

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force O(n²) | Sliding Window | Prefix + HashMap |
|-------------|------------------|----------------|-----------------|
| Time complexity | O(n²) ❌ | O(n) ✓ | O(n) ✓ |
| Handles negatives | ✓ | ❌ Fails | ✓ |
| Handles k = 0 | ✓ | ❌ | ✓ |
| Space | O(1)/O(n) | O(1) | O(n) |
| Interview ready | ❌ | ❌ | ✅ **Best** |

**Why Sliding Window fails:**
```
Sliding window works only when all elements are positive.
With negatives, expanding the window doesn't guarantee sum increases,
and shrinking doesn't guarantee sum decreases.

Example: [2,-1,1,2], k=2
Window [2,-1,1] = 2 ✓ — this can never be found by simple shrinking!
```

**Winner**: **Prefix Sum + HashMap** — only approach that handles negatives correctly in O(n).

---

## Critical Edge Cases & Gotchas

### 1. **Subarray starting at index 0**
```java
Input: nums = [3, 1, -1, 2], k = 3
Output: includes [3] as a valid subarray
Explanation: Requires map.put(0, 1) to detect — without it, [3] would be missed.
```

### 2. **Negative numbers**
```java
Input: nums = [1,-1,1,-1,1], k = 0
Output: 4
Explanation: [1,-1], [-1,1], [1,-1,1,-1], [-1,1,-1,1] all sum to 0.
             Sliding window cannot handle this.
```

### 3. **k = 0**
```java
Input: nums = [1,-1,1,-1], k = 0
Output: 4
Explanation: Every pair [1,-1] sums to 0. The base case {0→1} is critical here.
```

### 4. **Single element equals k**
```java
Input: nums = [5], k = 5
Output: 1
Explanation: sum=5, sum-k=0 → found in map (base case) → count=1 ✓
```

### 5. **Same prefix sum appears multiple times**
```java
Input: nums = [4,4,4,4,4,4], k = 4
Output: 6
Explanation: Each 4 individually sums to k. The map accumulates counts of
             repeated prefix sums, correctly counting all 6 subarrays.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Forgetting the base case `map.put(0, 1)`**
```java
// WRONG — missing base case
Map<Integer, Integer> map = new HashMap<>();
// ❌ forgot: map.put(0, 1)

for (int num : nums) {
    sum += num;
    if (map.containsKey(sum - k)) count += map.get(sum - k);
    map.put(sum, map.getOrDefault(sum, 0) + 1);
}
```
**Why wrong**: Any subarray starting at index 0 has `prefixSum[i] - 0 = k`. Without `0 → 1` in the map, `sum - k = 0` won't be found → all subarrays starting from the beginning are missed.

**Dry run failure**:
```
nums=[2,-1,1,2], k=2
Without base case:
  Step 1: sum=2, sum-k=0, map has 0? NO → miss [2] ❌
  Result: count=3 instead of 4
```

**Fix**: Always initialize `map.put(0, 1)` before the loop.

### ❌ **MISTAKE 2: Using a HashSet instead of HashMap**
```java
// WRONG — Set loses frequency information
Set<Integer> seen = new HashSet<>();
seen.add(0);

for (int num : nums) {
    sum += num;
    if (seen.contains(sum - k)) count++;   // ❌ adds 1 even if seen multiple times
    seen.add(sum);
}
```
**Why wrong**: If the same prefix sum appears 3 times, there are 3 valid subarrays ending at the current index — but a Set would only count it once.

**Counterexample**:
```
nums = [4,4,4,4,4,4], k = 4
At index 5: sum=24, sum-k=20, 20 appeared once in Set → count += 1
But 20 appeared exactly once, so this is actually correct...

Better counterexample: nums = [1,1,1], k=2
Expected: 2 subarrays ([1,1] starting at 0, [1,1] starting at 1)
With Set: at index 2, sum=3, sum-k=1, seen has 1 → count+=1 (only 1, not 2) ❌
```

**Fix**: Use `HashMap<Integer, Integer>` to store frequencies.

### ❌ **MISTAKE 3: Adding to map BEFORE checking**
```java
// WRONG — records current sum before checking
for (int num : nums) {
    sum += num;
    map.put(sum, map.getOrDefault(sum, 0) + 1);  // ❌ added first
    if (map.containsKey(sum - k)) count += map.get(sum - k);
}
```
**Why wrong**: Adding `sum` to the map before looking up `sum - k` can cause the same index to be used twice as both the start and end of a subarray (zero-length subarray), incorrectly inflating the count.

**Dry run failure**:
```
nums=[3], k=0
sum=3, add 3 to map first → map={0:1, 3:1}
Then check sum-k = 3-0 = 3 → found! count += 1
But [3] does NOT sum to 0 ❌
```

**Fix**: Always check `(sum - k)` BEFORE adding `sum` to the map.

### ❌ **MISTAKE 4: Using sliding window with negatives**
```java
// WRONG for arrays with negatives
int left = 0, windowSum = 0;
for (int right = 0; right < nums.length; right++) {
    windowSum += nums[right];
    while (windowSum > k && left <= right) windowSum -= nums[left++];
    if (windowSum == k) count++;
}
```
**Why wrong**: Shrinking the window when `windowSum > k` is invalid with negatives. Adding a negative element might decrease sum below k, requiring the window to expand from the left — but the algorithm already moved `left` past it.

**Fix**: Use prefix sum + HashMap instead.

### ❌ **MISTAKE 5: Counting only first occurrence of each prefix sum**
```java
// WRONG — resets to 1 instead of incrementing
map.put(sum, 1);   // ❌ always puts 1, loses prior count
```
**Why wrong**: If prefix sum 4 appears 3 times, putting 1 loses the information that there are 3 valid starting positions for future subarrays.

**Fix**: Increment correctly:
```java
map.put(sum, map.getOrDefault(sum, 0) + 1);   // ✓ accumulate frequency
```

---

## Complexity Analysis

**Time Complexity: O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Single loop over nums | O(n) | Visit each element once |
| HashMap lookup | O(1) | Average case hash lookup |
| HashMap insert | O(1) | Average case hash insert |
| **Total** | **O(n)** | Single linear pass |

**Space Complexity: O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| HashMap | O(n) | At most n+1 distinct prefix sums |
| sum, count variables | O(1) | Constant extra |
| **Total** | **O(n)** | |

---

## Visualization

### Full Walk-Through — Example 2

**Input:** `nums = [4,4,4,4,4,4]`, `k = 4`

```
Initial: map = {0→1}, sum=0, count=0

index 0: num=4  sum=4   sum-k=0  map[0]=1 → count=1   map={0→1, 4→1}
index 1: num=4  sum=8   sum-k=4  map[4]=1 → count=2   map={0→1, 4→1, 8→1}
index 2: num=4  sum=12  sum-k=8  map[8]=1 → count=3   map={0→1, 4→1, 8→1, 12→1}
index 3: num=4  sum=16  sum-k=12 map[12]=1 → count=4  map={...16→1}
index 4: num=4  sum=20  sum-k=16 map[16]=1 → count=5  map={...20→1}
index 5: num=4  sum=24  sum-k=20 map[20]=1 → count=6  map={...24→1}

Final count = 6 ✓
```

Each of the 6 individual elements forms one valid subarray — and each time we look back exactly one step to find the matching prefix sum.

### Why `map.put(0, 1)` Is the Base Case

```
Without base case:
  Subarrays starting at index 0 are of the form: nums[0..i]
  Their sum = prefixSum[i] - prefixSum[-1] = prefixSum[i] - 0
  We need (prefixSum[i] - k) = 0 to be in the map
  → That's why 0 must be pre-seeded with count 1

With base case {0→1}:
  At index 0, num=4: sum=4, sum-k=0 → found in map → count correctly increments
  Without: sum-k=0 → NOT found → first element always missed ❌
```

---

## Comparison of Approaches

| Approach | Time | Space | Handles Negatives | When to Use |
|----------|------|-------|-------------------|-------------|
| Brute Force O(n²) | O(n²) ❌ | O(n) | ✓ | Never |
| Sliding Window | O(n) | O(1) | ❌ Fails | Only if all elements positive |
| **Prefix Sum + HashMap** | **O(n)** | **O(n)** | **✓** | **Always ✅** |

**Recommendation**: Use **Prefix Sum + HashMap** — the only correct O(n) solution for arrays with negatives.

---

## Key Takeaways

1. **`prefix[i] - prefix[j] = k` is the core identity** — transforms subarray sum to a lookup
2. **`map.put(0, 1)` is mandatory** — covers all subarrays starting from index 0
3. **Check before adding to map** — don't add `sum` to map before looking up `sum - k`
4. **Use HashMap not HashSet** — frequency counts are needed
5. **Sliding window fails with negatives** — use prefix sum + map for arrays with negatives
6. **Increment, don't overwrite** — `map.put(sum, map.getOrDefault(sum, 0) + 1)`, never `map.put(sum, 1)`
7. **Count is additive** — when `sum - k` appears multiple times, all those positions form valid subarrays

---

## Interview Tips

**What to say in an interview:**

> "I use prefix sums and a HashMap. For each index, the subarray sum from j+1 to i equals prefixSum[i] minus prefixSum[j]. I want this to equal k, so I need prefixSum[j] = prefixSum[i] - k. I maintain a HashMap of prefix sum frequencies, seeded with {0:1} as the base case for subarrays starting at index 0. At each position I look up (currentSum - k) in the map and add its count to the result. This is O(n) time and O(n) space and handles negative numbers correctly."

**Key points to mention:**
1. **Why prefix sum** — transforms range sum to single lookup
2. **Why `{0:1}` base case** — handles subarrays starting at index 0
3. **Why HashMap not HashSet** — frequency counts are needed
4. **Why sliding window fails** — doesn't work with negatives
5. **Check before insert** — order of operations in the loop

**If asked about sliding window:**
> "Sliding window only works when all elements are non-negative. With negatives, adding elements doesn't guarantee the sum increases, so the two-pointer invariant breaks down. For this problem, prefix sum with a HashMap is the correct O(n) approach."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Subarray Sum Equals K** | Medium | **Prefix Sum + HashMap (count)** | **This problem** ← |
| Continuous Subarray Sum | Medium | Prefix Sum + HashMap | Check if sum divisible by k |
| Longest Subarray with Sum K | Medium | Prefix Sum + HashMap | Track index, not count |
| Range Sum Query - Immutable (1D) | Easy | Prefix Sum | Point query, not subarray count |
| Subarray Product Less Than K | Medium | Sliding Window | All positive → sliding window works |
| Binary Subarrays with Sum | Medium | Prefix Sum + HashMap | Binary arrays, same technique |

**Pattern Progression**:
1. **Range Sum Query** — prefix sum for single range sum in O(1)
2. **Subarray Sum Equals K** (this problem) — prefix sum + map to count matching subarrays
3. **Continuous Subarray Sum** — same map, check `(sum % k) == 0`
4. **Longest Subarray** — same map, store first occurrence index instead of count

---

## Final Pattern Label

✅ **Prefix Sum + HashMap (Frequency Count)**

**Remember:** At each index, count how many previous prefix sums equal `currentSum - k`. Seed the map with `{0:1}`, check before inserting, and use HashMap (not HashSet) to track frequencies!
