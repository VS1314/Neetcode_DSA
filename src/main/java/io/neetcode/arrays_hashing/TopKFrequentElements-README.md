# Top K Frequent Elements

## Problem Description

**Difficulty**: Medium

Given an integer array `nums` and an integer `k`, return the **k most frequent elements** within the array.

The test cases are generated such that the answer is always unique.

You may return the output in any order.

## Examples

### Example 1:
```
Input: nums = [1,2,2,3,3,3], k = 2

Output: [2,3]
Explanation: 3 appears 3 times, 2 appears 2 times — top 2 frequent.
```

### Example 2:
```
Input: nums = [7,7], k = 1

Output: [7]
Explanation: Only one distinct element — it's the most frequent.
```

## Constraints
- 1 <= nums.length <= 10^4
- -1000 <= nums[i] <= 1000
- 1 <= k <= number of distinct elements in nums

---

## Pattern Recognition

**Primary Pattern**: **HashMap + Bucket Sort**

**Why This Pattern?**
- We need to rank elements **by frequency**, not by value
- O(n) time is expected — sorting (`O(n log n)`) is too slow
- Frequency of any element is bounded between `1` and `n` — bucket sort naturally fits
- Buckets indexed by frequency let us retrieve top-k in one reverse scan

**Key Insight**: Create `n+1` buckets where index = frequency. Place each number into the bucket matching its frequency. Traverse from index `n` down to `0` collecting elements — the first `k` found are the answer.

**Related Patterns**:
1. **HashMap** – Always needed when frequency counting is required
2. **Heap (Min-Heap)** – Alternative O(n log k) approach using priority queue
3. **Sort by frequency** – Simpler but slower O(n log n) approach
4. **Sort Characters by Frequency** – Same pattern applied to characters

---

## Algorithm & Approach

### Core Insight

**Why Bucket Sort Works Here:**

For an array of length `n`, the maximum frequency any element can have is `n` (all elements the same). So frequencies range from `1` to `n`. We create `n+1` buckets (index 0 to n), place each element into its frequency bucket, then read from highest to lowest to pick top-k.

```
Rule:
  bucket[freq] = list of all numbers with that frequency

After building:
  Scan from bucket[n] → bucket[1]
  Collect elements until we have k
```

**Why `n+1` buckets and not `n`?**
```
nums.length = 6
Max possible frequency = 6  (e.g., [5,5,5,5,5,5])
Bucket indices needed: 0, 1, 2, 3, 4, 5, 6  → 7 slots = n+1

If you use < n → missing bucket[n] → IndexOutOfBoundsException ❌
Use <= n      → all frequencies covered ✓
```

### Visual Understanding
```
nums = [1,2,2,3,3,3]

Frequency Map:
  1 → 1
  2 → 2
  3 → 3

Buckets (index = frequency):
  bucket[0] = []     ← unused
  bucket[1] = [1]
  bucket[2] = [2]
  bucket[3] = [3]
  bucket[4] = []
  bucket[5] = []
  bucket[6] = []

Traverse right → left:
  i=6 → empty
  i=5 → empty
  i=4 → empty
  i=3 → take 3   (collected 1)
  i=2 → take 2   (collected 2 = k) ✓ STOP

Result: [3, 2]
```

### Step-by-Step Algorithm

---

#### **Approach 1: Bucket Sort — O(n) (OPTIMAL & RECOMMENDED)**

**Core Idea**:
- Step 1: Build a frequency map using HashMap
- Step 2: Create `n+1` bucket lists — `bucket.get(i)` holds numbers with frequency `i`
- Step 3: Fill buckets from the map
- Step 4: Scan from highest bucket to lowest, collect first `k` elements

**Algorithm**
```
1. Build map: num → frequency
2. Create List<List<Integer>> buckets of size n+1
3. For each (num, freq) in map:
      buckets.get(freq).add(num)
4. result = []
   for freq from n down to 0:
      for num in buckets.get(freq):
         result.add(num)
         if result.size() == k → return
```

**Code Implementation**
```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> count = new HashMap<>();
    for (int num : nums) {
        count.put(num, count.getOrDefault(num, 0) + 1);
    }

    List<List<Integer>> buckets = new ArrayList<>();
    for (int i = 0; i <= nums.length; i++) {
        buckets.add(new ArrayList<>());
    }

    for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
        int num  = entry.getKey();
        int freq = entry.getValue();
        buckets.get(freq).add(num);
    }

    int[] result = new int[k];
    int idx = 0;

    for (int freq = buckets.size() - 1; freq >= 0 && idx < k; freq--) {
        for (int num : buckets.get(freq)) {
            result[idx++] = num;
            if (idx == k) return result;
        }
    }
    return result;
}
```

**Example Walkthrough**

Input: nums = [1,2,2,3,3,3], k = 2

| Step | Action | State |
|------|--------|-------|
| Build map | count all frequencies | {1→1, 2→2, 3→3} |
| Fill buckets | bucket[1]=[1], bucket[2]=[2], bucket[3]=[3] | |
| freq=3 | take 3 | result=[3], idx=1 |
| freq=2 | take 2 | result=[3,2], idx=2 = k → return |

**Return [3, 2]** ✓

**Complexity Analysis**
- **Time Complexity**: O(n)
  - Frequency map: O(n)
  - Fill buckets: O(n)
  - Scan buckets: O(n)
  - Total: O(n)
- **Space Complexity**: O(n)
  - HashMap: O(n)
  - Buckets: O(n)

---

#### **Approach 2: Min-Heap — O(n log k) (ALTERNATIVE)**

**Core Idea**:
- Build frequency map
- Use a min-heap of size `k` keyed by frequency
- If heap exceeds `k`, remove the element with lowest frequency
- At the end, heap contains top k frequent elements

**Code Implementation**
```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> count = new HashMap<>();
    for (int num : nums) {
        count.put(num, count.getOrDefault(num, 0) + 1);
    }

    PriorityQueue<Map.Entry<Integer, Integer>> minHeap =
            new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

    for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
        minHeap.offer(entry);
        if (minHeap.size() > k) minHeap.poll();
    }

    int[] result = new int[k];
    int i = 0;
    while (!minHeap.isEmpty()) result[i++] = minHeap.poll().getKey();
    return result;
}
```

**Complexity Analysis**
- **Time Complexity**: O(n log k) — heap operations are O(log k), done n times
- **Space Complexity**: O(n) — map + heap

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Sort by Freq | Min-Heap | Bucket Sort |
|-------------|-------------|----------|-------------|
| Time complexity | O(n log n) ❌ | O(n log k) ✓ | O(n) ✓ |
| Space complexity | O(n) ✓ | O(n) ✓ | O(n) ✓ |
| Code simplicity | ✅ Simple | Medium | ✅ Simple |
| Meets O(n) target | ❌ | ❌ | ✅ |

**Winner**: **Bucket Sort** — Only approach that achieves O(n) time while staying simple.

---

## Critical Edge Cases & Gotchas

### 1. **All Same Elements**
```java
Input: nums = [5,5,5,5], k = 1
Frequency: {5 → 4}
bucket[4] = [5]
Output: [5] ✓
```

### 2. **All Distinct Elements**
```java
Input: nums = [1,2,3,4], k = 2
Frequency: {1→1, 2→1, 3→1, 4→1}
All in bucket[1]
Output: any 2 of [1,2,3,4] ✓
```

### 3. **k Equals Total Distinct Count**
```java
Input: nums = [1,1,2,3], k = 3
Must return all 3 distinct elements.
Output: [1,2,3] ✓
```

### 4. **Single Element Array**
```java
Input: nums = [7], k = 1
Frequency: {7 → 1}
bucket[1] = [7]
Output: [7] ✓
```

### 5. **Multiple Elements With Same Frequency**
```java
Input: nums = [1,1,2,2,3], k = 2
Frequency: {1→2, 2→2, 3→1}
bucket[2] = [1, 2] ← both in same bucket
Output: [1, 2] ✓  (order within bucket doesn't matter)
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Ranking by value instead of frequency**
```java
// WRONG — ranks by value, not frequency!
Set<Integer> set = new HashSet<>();
for (int i : nums) set.add(i);

PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
for (int i : set) max.add(i);
```
**Why wrong**: The heap contains raw values. `max.poll()` gives the largest number, not the most frequent one.

**Counterexample**:
```
nums = [1,1,1,2,2,3], k = 2
Correct answer: [1, 2]
Wrong output:   [3, 2]  ← 3 appears once but has highest value!
```

**Fix**: Use frequency as the comparison key, not the number itself.
```java
// CORRECT — compare by frequency
PriorityQueue<Map.Entry<Integer, Integer>> heap =
    new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
```

### ❌ **MISTAKE 2: One slot per frequency (overwrite problem)**
```java
// WRONG — multiple numbers can share same frequency!
int[] freq = new int[nums.length];
freq[value] = key;  // ❌ overwrites earlier numbers at same frequency
```
**Why wrong**: If two numbers have the same frequency, the second one overwrites the first.

**Dry run failure**:
```
nums = [1,1,2,2,3], k = 2
count: {1→2, 2→2, 3→1}

freq[2] = 1   (key=1)
freq[2] = 2   (key=2) ← 1 is lost! ❌
freq[1] = 3

Result: [2, 3]  Wrong! ❌
```

**Fix**: Use a list per bucket, not a single integer.
```java
// CORRECT — list holds all numbers at that frequency
List<List<Integer>> buckets = new ArrayList<>();
buckets.get(freq).add(num);  // ✓ multiple numbers allowed
```

### ❌ **MISTAKE 3: ArrayList index vs bracket access**
```java
// WRONG
freq[i] = new ArrayList<>();   // ❌ Can't use [] on ArrayList
```
**Why wrong**: `[]` is array syntax. ArrayList requires `.get(i)` and `.add(...)`.

**Fix**:
```java
buckets.get(freq).add(num);   // ✓ correct ArrayList access
```

### ❌ **MISTAKE 4: Confusing capacity with size**
```java
// WRONG thinking
List<List<Integer>> freq = new ArrayList<>(nums.length);
freq.get(0);   // 💥 IndexOutOfBoundsException!
```
**Why wrong**: `new ArrayList<>(n)` sets internal capacity only. Size is still 0.

**Fix**: Explicitly populate with `add()`:
```java
for (int i = 0; i <= nums.length; i++) {
    buckets.add(new ArrayList<>());  // ✓ now size = n+1
}
```

### ❌ **MISTAKE 5: Using `< nums.length` instead of `<= nums.length`**
```java
// WRONG
for (int i = 0; i < nums.length; i++) {  // ❌ missing bucket[n]
    buckets.add(new ArrayList<>());
}
```
**Why wrong**: If all elements are the same, frequency = n. `bucket[n]` must exist.

**Fix**: Use `<= nums.length`.

---

## Complexity Analysis

### Bucket Sort Approach

**Time Complexity: O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Build frequency map | O(n) | One pass through nums |
| Initialise buckets | O(n) | n+1 empty lists created |
| Fill buckets | O(n) | One pass through map entries |
| Scan buckets | O(n) | At most n+1 buckets scanned |
| **Total** | **O(n)** | **Linear** |

**Space Complexity: O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| HashMap | O(n) | At most n distinct entries |
| Buckets | O(n) | n+1 lists, total elements = n |
| Result array | O(k) | k ≤ n |
| **Total** | **O(n)** | |

---

## Visualization

### Bucket Sort Full Walk-Through

**Input:** nums = [1,1,2,2,3], k = 2

```
Step 1 — Build Frequency Map:
  map = { 1→2, 2→2, 3→1 }

Step 2 — Initialise Buckets (n = 5):
  index:   0    1    2    3    4    5
  bucket: [ ]  [ ]  [ ]  [ ]  [ ]  [ ]

Step 3 — Fill Buckets:
  1 → freq 2 → bucket[2].add(1)
  2 → freq 2 → bucket[2].add(2)
  3 → freq 1 → bucket[1].add(3)

  index:   0    1     2       3    4    5
  bucket: [ ]  [3]  [1,2]   [ ]  [ ]  [ ]

Step 4 — Traverse from highest:
  i=5 → empty
  i=4 → empty
  i=3 → empty
  i=2 → [1,2] → take 1 (idx=1), take 2 (idx=2 = k) ✓ STOP

Result: [1, 2]
```

---

## Comparison of Approaches

| Approach | Time | Space | Code | When to Use |
|----------|------|-------|------|-------------|
| Sort by frequency | O(n log n) | O(n) | ✅ Simplest | ❌ Too slow |
| **Bucket Sort** | **O(n)** | **O(n)** | **✅ Simple** | **Default choice** ✅ |
| Min-Heap | O(n log k) | O(n) | Medium | When interviewer asks for heap |

**Recommendation**: Use **Bucket Sort** — it's the only O(n) solution and is just as readable as sorting.

---

## Key Takeaways

1. **"Most frequent" = count first** — always build a frequency map before ranking
2. **Top-K by frequency → Bucket Sort** — index by frequency, scan from high to low
3. **`n+1` buckets** — max frequency is `n`, need indices `0..n`
4. **List per bucket** — multiple numbers can share the same frequency
5. **`add()` to populate ArrayList** — capacity constructor sets internal capacity, not size
6. **Heap alternative** — valid but O(n log k), use only if explicitly asked
7. **Don't rank by value** — `PriorityQueue<Integer>` gives top-k by value, not frequency

---

## Interview Tips

**What to say in an interview:**

> "Since we need the top k frequent elements, I'll first count frequencies using a HashMap. Then I apply bucket sort — I create n+1 buckets where each index represents a frequency. I place each number into its frequency bucket. Finally I scan from the highest bucket index down, collecting numbers until I have k. This gives O(n) time and O(n) space."

**Key points to mention:**
1. **Why HashMap** — O(1) frequency lookup/insert
2. **Why Bucket Sort over sorting** — avoids the O(n log n) bottleneck
3. **Why n+1 buckets** — max frequency can be n (all same element)
4. **Why List per bucket** — ties in frequency must all be stored
5. **Heap as alternative** — O(n log k), mention if asked

**If asked about the heap solution:**
> "We can also use a min-heap of size k. We iterate through the frequency map, push each entry, and if the heap size exceeds k we remove the minimum frequency element. After processing all entries, the heap holds the top k. This is O(n log k) — slightly worse than bucket sort but heap is more general-purpose."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Top K Frequent Elements** | Medium | **Bucket Sort** | **Numbers** ← This problem |
| Sort Characters by Frequency | Medium | Bucket Sort | Characters instead of numbers |
| Top K Frequent Words | Medium | Heap + Freq | Lexicographic tie-breaking |
| Kth Largest Element in Array | Medium | QuickSelect / Heap | Rank by value, not frequency |
| Find K Closest Elements | Medium | Binary Search + Heap | Distance-based ranking |

**Pattern Progression**:
- **Top K by Frequency** (this problem) — Bucket Sort foundation
- **Sort by Frequency** — Same map, then sort
- **Top K Words** — Same idea with tie-breaking
- **K Closest / K Largest** — Heap-based Top-K on different keys

---

## Final Pattern Label

✅ **Frequency Map + Bucket Sort — Top K**

**Remember:** Whenever you see "top k" + "frequent" + O(n) expected → **HashMap to count, Bucket Sort to rank**!
