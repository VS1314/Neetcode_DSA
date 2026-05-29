# Majority Element II

## Problem Description

**Difficulty**: Medium

Given an integer array `nums` of size `n`, find all elements that appear more than `⌊n/3⌋` times. Return the result in any order.

**Follow-up**: Could you solve it in O(n) time and O(1) space?

## Examples

### Example 1:
```
Input: nums = [5,2,3,2,2,2,2,5,5,5]

Output: [2,5]
Explanation: n=10, n/3=3. Element 2 appears 5 times, element 5 appears 4 times.
             Both appear more than 3 times.
```

### Example 2:
```
Input: nums = [4,4,4,4,4]

Output: [4]
Explanation: n=5, n/3=1. Only 4 appears more than once → [4].
```

### Example 3:
```
Input: nums = [1,2,3]

Output: []
Explanation: n=3, n/3=1. Each element appears exactly once, not more than 1 → [].
```

## Constraints
- 1 <= nums.length <= 50,000
- -1,000,000,000 <= nums[i] <= 1,000,000,000

---

## Pattern Recognition

**Primary Pattern**: **Boyer–Moore Voting Algorithm (Extended — 2 Candidates)**

**Why This Pattern?**
- Elements appearing more than `⌊n/3⌋` times can be at most **2** — this is the key mathematical fact
- Boyer–Moore generalizes to track at most `k-1` candidates for elements appearing more than `⌊n/k⌋` times
- For `k=3` → track at most 2 candidates in O(1) space
- A second verification pass confirms which candidates actually exceed the threshold

**Key Insight — Why at most 2 elements?**
```
If 3 elements each appeared more than ⌊n/3⌋ times:
  3 × (⌊n/3⌋ + 1) > n  → impossible!

So there can be at most 2 such elements.
→ Maintain 2 candidates and 2 counts.
```

**Key Insight — Two Phases:**
1. **Phase 1**: Find the 2 "survivor" candidates using cancellation (groups of 3 distinct elements cancel out)
2. **Phase 2**: Verify both candidates actually appear more than `⌊n/3⌋` times

**Related Patterns**:
1. **Majority Element (> n/2)** — same algorithm with 1 candidate; no verification needed (guaranteed to exist)
2. **Boyer–Moore Voting** — core cancellation technique
3. **General > n/k** — extend to k-1 candidates

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**

```
HashMap approach: O(n) time, O(n) space
  → Acceptable but fails the O(1) space follow-up

Sort approach: O(n log n) time
  → Too slow

Boyer–Moore: O(n) time, O(1) space  ✓
```

**The Cancellation Idea:**

```
Think of the array as a mix of elements.
If we always cancel 3 DIFFERENT elements at once,
the elements that survive the most rounds are the majority candidates.

Why 3? Because we're looking for elements appearing more than n/3 times.
Cancelling in groups of 3 ensures true majority elements can't be fully cancelled.
```

### Visual Understanding

```
nums = [5, 2, 3, 2, 2, 2, 2, 5, 5, 5]

Phase 1 — Cancellation tracker:

num  cand1  count1  cand2  count2  Action
 5     5      1      -      0     cand1 = 5
 2     5      1      2      1     cand2 = 2
 3     5      0      2      0     decrement both (5,2,3 → 3 different → cancel)
 2     2      1      -      0     cand1 = 2 (count1 was 0)
 2     2      2      -      0     matches cand1
 2     2      3      -      0     matches cand1
 2     2      4      -      0     matches cand1
 5     2      4      5      1     cand2 = 5 (count2 was 0)
 5     2      4      5      2     matches cand2
 5     2      4      5      3     matches cand2

Survivors: cand1=2, cand2=5

Phase 2 — Verify actual counts:
  2 → appears 5 times → 5 > 10/3=3 ✓
  5 → appears 4 times → 4 > 3 ✓

Output: [2, 5] ✓
```

### Step-by-Step Algorithm

---

#### **Approach 1: Boyer–Moore Voting (Extended) — OPTIMAL**

**Core Idea**:
- Maintain two candidates (`cand1`, `cand2`) and their counts
- For each number, follow this **strict priority order**:
  1. If matches `cand1` → increment `count1`
  2. Else if matches `cand2` → increment `count2`
  3. Else if `count1 == 0` → assign `cand1 = num, count1 = 1`
  4. Else if `count2 == 0` → assign `cand2 = num, count2 = 1`
  5. Else → decrement both counts (cancellation)
- After Phase 1, verify both candidates by counting their actual occurrences

**The order is critical** — matching existing candidates must be checked BEFORE assigning new ones.

**Code Implementation**
```java
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        int cand1 = 0, cand2 = 0;
        int count1 = 0, count2 = 0;

        // Phase 1: Find potential candidates
        for (int num : nums) {
            if (cand1 == num) {
                count1++;
            } else if (cand2 == num) {
                count2++;
            } else if (count1 == 0) {
                cand1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        // Phase 2: Verify actual counts
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == cand1) count1++;
            else if (num == cand2) count2++;
        }

        List<Integer> ans = new ArrayList<>();
        if (count1 > nums.length / 3) ans.add(cand1);
        if (count2 > nums.length / 3) ans.add(cand2);

        return ans;
    }
}
```

**Example Walkthrough — Phase 1 detailed**

Input: `nums = [5,2,3,2,2,2,2,5,5,5]`

| num | cand1 | count1 | cand2 | count2 | Action |
|-----|-------|--------|-------|--------|--------|
| 5   | 5     | 1      | 0     | 0      | count1==0 → cand1=5, count1=1 |
| 2   | 5     | 1      | 2     | 1      | count2==0 → cand2=2, count2=1 |
| 3   | 5     | 0      | 2     | 0      | else → count1--, count2-- |
| 2   | 2     | 1      | 2     | 0      | count1==0 → cand1=2, count1=1 |
| 2   | 2     | 2      | 2     | 0      | matches cand1 |
| 2   | 2     | 3      | 2     | 0      | matches cand1 |
| 2   | 2     | 4      | 2     | 0      | matches cand1 |
| 5   | 2     | 4      | 5     | 1      | count2==0 → cand2=5, count2=1 |
| 5   | 2     | 4      | 5     | 2      | matches cand2 |
| 5   | 2     | 4      | 5     | 3      | matches cand2 |

Candidates after Phase 1: `cand1=2, cand2=5`

Phase 2 verification:
- `2` appears 5 times → 5 > 10/3=3 ✓
- `5` appears 4 times → 4 > 3 ✓

**Return [2, 5]** ✓

**Complexity Analysis**
- **Time Complexity**: O(n) — two O(n) passes
- **Space Complexity**: O(1) — only 4 variables

---

#### **Approach 2: HashMap Frequency Count (ALTERNATIVE)**

**Core Idea**: Count frequencies in a map, then collect all entries exceeding `⌊n/3⌋`.

**Code Implementation**
```java
class Solution {
    public List<Integer> majorityElement(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }

        List<Integer> ans = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            if (entry.getValue() > nums.length / 3) {
                ans.add(entry.getKey());
            }
        }
        return ans;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n)
- **Space Complexity**: O(n) — does NOT satisfy the O(1) space follow-up

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | HashMap | Boyer–Moore |
|-------------|-------------|---------|-------------|
| Time complexity | O(n²) ❌ | O(n) ✓ | O(n) ✓ |
| Space complexity | O(1) | O(n) ❌ | O(1) ✓ |
| Handles follow-up | ✓ | ❌ | ✅ |
| Code complexity | Medium | Simple | Medium |

**Winner**: **Boyer–Moore** — only approach meeting both the O(n) time and O(1) space requirements of the follow-up.

---

## Critical Edge Cases & Gotchas

### 1. **Two elements, both majority**
```java
Input: nums = [1, 2]
n=2, n/3=0
Output: [1, 2]  ← both appear 1 time > 0 ✓
```

### 2. **All same elements**
```java
Input: nums = [4,4,4,4,4]
n=5, n/3=1
Output: [4]  ← 4 appears 5 times > 1 ✓
```

### 3. **No majority elements**
```java
Input: nums = [1,2,3]
n=3, n/3=1
Output: []  ← each appears exactly 1 time, NOT more than 1
```

### 4. **Array with zeros**
```java
Input: nums = [0,0,0,1,1,1,2]
n=7, n/3=2
Output: [0,1]  ← both appear 3 times > 2 ✓
Note: Initial cand=0 doesn't conflict — a candidate is only valid when count > 0
```

### 5. **Large array with exactly 2 elements exceeding threshold**
```java
Input: nums = [1,1,1,2,2,2,3,3,4,4,5,5]
n=12, n/3=4
Neither 1,2,3,4,5 appears more than 4 times → Output: []
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Assigning candidates before checking existing matches**
```java
// WRONG — wrong order of conditions
for (int num : nums) {
    if (count1 == 0) cand1 = num;           // ❌ assigned before checking match
    else if (count2 == 0) cand2 = num;
    else if (cand1 == num) count1++;
    else if (cand2 == num) count2++;
    else { count1--; count2--; }
}
```
**Why wrong**: If `num == cand1`, the first condition (count1==0) might be false and skip correctly, but `cand1` can get overwritten on a next iteration before it's confirmed. The match check must come **first**.

**Fix**: Check existing candidates first, assign new ones only after:
```java
if (cand1 == num) count1++;
else if (cand2 == num) count2++;
else if (count1 == 0) { cand1 = num; count1 = 1; }
else if (count2 == 0) { cand2 = num; count2 = 1; }
else { count1--; count2--; }
```

### ❌ **MISTAKE 2: Not setting count to 1 when assigning a new candidate**
```java
// WRONG — missing count1 = 1
else if (count1 == 0) cand1 = num;   // ❌ count1 stays 0!
```
**Why wrong**: If count stays at 0, the very next different element will overwrite this candidate immediately, losing it entirely.

**Dry run failure for `[1, 2]`**:
```
i=1: count1==0 → cand1=1  (count1 still 0)
i=2: count1==0 → cand1=2  (overwrites 1!) ← 1 is LOST
Result: only [2] returned instead of [1,2] ❌
```

**Fix**: Set count to 1 immediately:
```java
else if (count1 == 0) { cand1 = num; count1 = 1; }   // ✓
```

### ❌ **MISTAKE 3: Skipping the verification phase**
```java
// WRONG — returning candidates directly without verifying
List<Integer> ans = new ArrayList<>();
ans.add(cand1);
ans.add(cand2);
return ans;   // ❌ may include false candidates
```
**Why wrong**: Phase 1 finds "survivors of cancellation", not guaranteed majority elements. Candidates can survive even if they don't meet the threshold.

**Counterexample**:
```
nums = [1,2,3,4]  (n=4, n/3=1)
Phase 1 survivors might be: cand1=3, cand2=4
But none appears more than once → should return []
Without verification: incorrectly returns [3,4] ❌
```

**Fix**: Always verify by recounting actual occurrences.

### ❌ **MISTAKE 4: Using `if` instead of `else if` — double counting**
```java
// WRONG — not using else-if chain
if (cand1 == num) count1++;
if (cand2 == num) count2++;   // ❌ separate if, not else if
else { count1--; count2--; }
```
**Why wrong**: When `cand1 == num` but `cand2 != num`, the code increments `count1` then falls into the `else` and decrements both, immediately cancelling the increment just made.

**Fix**: Strict `else if` chain — each case is mutually exclusive.

### ❌ **MISTAKE 5: Using `if` instead of `else if` in verification phase**
```java
// WRONG — double counts when cand1 == cand2 (rare edge case)
for (int num : nums) {
    if (num == cand1) count1++;
    if (num == cand2) count2++;   // ❌ should be else if
}
```
**Why wrong**: If somehow `cand1 == cand2`, both counts get incremented for the same element. Use `else if` to ensure mutual exclusivity.

**Fix**:
```java
if (num == cand1) count1++;
else if (num == cand2) count2++;   // ✓
```

---

## Complexity Analysis

### Boyer–Moore Extended Approach

**Time Complexity: O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Phase 1 — candidate selection | O(n) | Single pass through array |
| Phase 2 — verification | O(n) | Single pass to count |
| **Total** | **O(n)** | Two linear passes |

**Space Complexity: O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| cand1, cand2 | O(1) | Two integer variables |
| count1, count2 | O(1) | Two integer variables |
| Result list | O(1) | At most 2 elements |
| **Total** | **O(1)** | Constant extra space |

---

## Visualization

### Phase 1 Cancellation for Example 3 — No Majority

**Input:** `nums = [1,2,3]`, n=3, n/3=1

```
num=1: count1==0 → cand1=1, count1=1
       cand1=1, count1=1, cand2=0, count2=0

num=2: no match, count2==0 → cand2=2, count2=1
       cand1=1, count1=1, cand2=2, count2=1

num=3: no match, count1>0 && count2>0 → decrement both
       cand1=1, count1=0, cand2=2, count2=0

Survivors: cand1=1, cand2=2

Phase 2:
  1 appears 1 time → 1 > 1? NO ❌
  2 appears 1 time → 1 > 1? NO ❌

Output: []  ✓  (verification correctly filtered both out)
```

### Why Verification Is the Safety Net

```
Phase 1 analogy:
  🗳️  Election Nomination — many candidates enter, strongest 2 survive

Phase 2 analogy:
  📊  Vote Counting — only candidates who actually crossed the threshold win

Survivors ≠ Winners.
Always verify.
```

---

## Comparison of Approaches

| Approach | Time | Space | Follow-up | When to Use |
|----------|------|-------|-----------|-------------|
| Brute Force | O(n²) ❌ | O(1) | ❌ | Never |
| HashMap | O(n) | O(n) ❌ | ❌ | When O(1) space not required |
| **Boyer–Moore Extended** | **O(n)** | **O(1) ✅** | **✅** | **Default — always** |

**Recommendation**: Use **Boyer–Moore Extended** — it's the only approach meeting all constraints and satisfying the follow-up.

---

## Key Takeaways

1. **At most 2 majority elements** — mathematically impossible to have 3 elements each exceeding `n/3`
2. **Strict else-if chain** — each condition in Phase 1 must be mutually exclusive
3. **Match before assign** — check if num matches existing candidates BEFORE checking if count is 0
4. **Set count = 1 on assignment** — when assigning a new candidate, immediately set its count to 1
5. **Verification is mandatory** — Phase 1 gives survivors, not winners; Phase 2 confirms
6. **Initial cand=0 is not a problem** — a candidate is valid only when its count > 0
7. **Boyer–Moore generalizes** — for `> n/k`, maintain `k-1` candidates

---

## Interview Tips

**What to say in an interview:**

> "Since at most 2 elements can appear more than n/3 times, I use the extended Boyer–Moore voting algorithm with 2 candidates. In Phase 1, I maintain two candidates and their counts — if a number matches a candidate I increment its count, if a count is zero I assign a new candidate, otherwise I decrement both counts, effectively cancelling out triplets of distinct elements. In Phase 2, I verify by counting actual occurrences. This runs in O(n) time and O(1) space."

**Key points to mention:**
1. **Why at most 2** — mathematical proof: 3 elements × (n/3+1) > n
2. **Why else-if strictly** — conditions must be mutually exclusive
3. **count=1 on assignment** — otherwise candidate gets immediately overwritten
4. **Why verification needed** — Phase 1 gives candidates, not guaranteed majorities
5. **Generalizes to n/k** — track k-1 candidates

**If asked about Majority Element I vs II:**
> "Majority Element I guarantees exactly one element exceeds n/2 — so verification is optional and we track 1 candidate. Majority Element II asks for all elements exceeding n/3 — no guarantee they exist, so we must verify, and we track 2 candidates."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Majority Element II** | Medium | **Boyer–Moore (2 candidates)** | **This problem** ← |
| Majority Element | Easy | Boyer–Moore (1 candidate) | Only 1 candidate, no verification needed |
| Find All Numbers > n/k | Hard | Boyer–Moore (k-1 candidates) | Generalised version |
| Top K Frequent Elements | Medium | Bucket Sort / Heap | Frequency ranking, not threshold |

**Pattern Progression**:
1. **Majority Element (>n/2)** — 1 candidate, guaranteed result, no verification
2. **Majority Element II (>n/3)** (this problem) — 2 candidates, verification required
3. **General >n/k** — k-1 candidates, same verification pattern

---

## Final Pattern Label

✅ **Boyer–Moore Voting Algorithm (Extended — 2 Candidates)**

**Remember:** Phase 1 finds survivors by cancellation, Phase 2 confirms by counting. Match existing candidates FIRST, then assign new ones, then cancel — strict else-if order is critical!
