# Boats to Save People

## Problem Description

**Difficulty**: Medium

You are given an integer array `people` where `people[i]` is the weight of the i-th person, and an infinite number of boats where each boat can carry a maximum weight of `limit`. Each boat carries **at most two people** at the same time, provided the sum of the weight of those people is at most `limit`.

Return the **minimum number of boats** to carry every given person.

## Examples

### Example 1:
```
Input: people = [5,1,4,2], limit = 6
Output: 2
Explanation:
First boat: [5,1] (weight = 6)
Second boat: [4,2] (weight = 6)
```

### Example 2:
```
Input: people = [1,3,2,3,2], limit = 3
Output: 4
Explanation:
First boat: [3] (weight = 3, can't pair with anyone)
Second boat: [3] (weight = 3, can't pair with anyone)
Third boat: [1,2] (weight = 3)
Fourth boat: [2] (weight = 2, only one person left)
```

### Example 3:
```
Input: people = [3,2,2,1], limit = 3
Output: 3
Explanation:
First boat: [3] (weight = 3, can't fit anyone else)
Second boat: [2,1] (weight = 3)
Third boat: [2] (weight = 2, only one person left)
```

## Constraints
- 1 <= people.length <= 50,000
- 1 <= people[i] <= limit <= 30,000

**Recommended Complexity**: O(n log n) time, O(1) space

---

## Pattern Recognition

**Primary Pattern**: **Sorting + Two Pointers (Greedy Pairing: Lightest with Heaviest)**

**Why This Pattern?**
- Need to minimize number of boats
- Each boat carries at most 2 people
- Greedy strategy: pair lightest with heaviest
- Sorting enables two-pointer pairing from both ends

**Key Insight**: Pair Heaviest with Lightest (Greedy Choice)
```
Why this greedy strategy works:

Heavy people are the hardest to pair:
  - If the heaviest person can pair with anyone, 
    it must be with the lightest person
  - If even lightest + heaviest > limit,
    then heaviest must go alone (can't pair with anyone)
  
Strategy:
  Sort array: [lightest ... heaviest]
  Try to pair people[left] (lightest) with people[right] (heaviest)
  
  If people[left] + people[right] <= limit:
    ✓ Put them together, use 1 boat
    Move both pointers: left++, right--
  
  Else:
    ✗ Heaviest can't fit with lightest (can't fit with anyone!)
    Heaviest goes alone, use 1 boat
    Move only right: right--
  
Example:
  people = [1, 2, 4, 5], limit = 6
  
  Sorted: [1, 2, 4, 5]
           ↑        ↑
           L        R
  
  Step 1: 1 + 5 = 6 <= 6 ✓
    Boat 1: [1, 5]
    L++, R--
  
  Step 2: 2 + 4 = 6 <= 6 ✓
    Boat 2: [2, 4]
    L++, R--
  
  Total: 2 boats
```

**Why Greedy Works - Proof:**
```
Claim: Pairing lightest with heaviest is always optimal (or as good as any other strategy)

Proof by Exchange Argument:

Assume optimal solution pairs people differently:
  - Lightest (L) paired with someone other than Heaviest (H)
  - Heaviest (H) paired with someone other than Lightest (L)

Case 1: H goes alone
  Then we can try pairing H with L:
    If L + H <= limit: We save a boat! Original wasn't optimal. ✗
    If L + H > limit: H must go alone anyway. Our greedy matches. ✓

Case 2: H paired with someone Middle (M)
  And L paired with someone else or goes alone
  
  Since L + H <= limit (otherwise H goes alone):
    We have: L <= M (L is lightest)
    So: L + H <= M + H <= limit
    
  We can swap: pair L with H, and M either pairs with L's partner or goes alone
  This uses same or fewer boats!
  
Conclusion: Greedy choice (pair lightest with heaviest) is always optimal!
```

**Critical Detail**: Each boat holds at most 2 people
```
Unlike bin packing where we try to fit as many as possible,
here we can only fit 1 or 2 people per boat.

This simplifies the problem:
  - No need to consider complex combinations
  - Only two choices per boat: 1 person or 2 people
  - Greedy pairing works perfectly!
```

**Related Patterns**:
1. **Two Pointers** — Core technique
2. **Greedy Algorithm** — Pair lightest with heaviest
3. **Sorting** — Enables optimal pairing
4. **Bin Packing** — Related but more complex (allows multiple items)

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Try all possible pairings
  → Exponential combinations
  → O(2^n) time
  → Too slow!

Greedy + Two Pointers:
  → Sort array: O(n log n)
  → Pair from both ends: O(n)
  → Total: O(n log n)
  → Optimal! ✓
```

**The Greedy Strategy**:
```
After sorting: [lightest ... heaviest]

At each step:
  Try to pair lightest remaining with heaviest remaining
  
  Why?
    - Heavy people are hardest to fit
    - If heavy can't fit with light, can't fit with anyone
    - Pairing them maximizes boat utilization
    
  Result:
    - Each decision uses exactly 1 boat
    - Minimizes total boats needed
```

### Step-by-Step Algorithm

---

#### **Approach 1: Sort + Two Pointers (OPTIMAL)**

**Core Idea**:
- Sort people by weight
- Use two pointers: left (lightest), right (heaviest)
- Try to pair them; if can't, take heavier person alone
- Count boats used

**Algorithm**
```
numRescueBoats(people, limit):
    sort(people)
    left = 0
    right = n - 1
    boats = 0
    
    while left <= right:
        // Try to pair lightest with heaviest
        if people[left] + people[right] <= limit:
            // Can fit both
            left++
            right--
        else:
            // Heaviest goes alone
            right--
        
        boats++  // Each iteration uses one boat
    
    return boats
```

**Code Implementation**
```java
class Solution {
    public int numRescueBoats(int[] people, int limit) {
        // Sort to enable greedy pairing
        Arrays.sort(people);
        
        int left = 0;
        int right = people.length - 1;
        int boats = 0;
        
        while (left <= right) {
            // Try to pair lightest with heaviest
            if (people[left] + people[right] <= limit) {
                // Both fit in one boat
                left++;
                right--;
            } else {
                // Heaviest person goes alone
                right--;
            }
            
            boats++;  // Each iteration uses exactly one boat
        }
        
        return boats;
    }
}
```

**Example Walkthrough**

Input: `people = [5,1,4,2]`, `limit = 6`

**Step 1: Sort**
```
[5,1,4,2] → [1,2,4,5]
```

**Step 2: Two Pointers**

| Step | L | R | people[L] | people[R] | Sum | Can Pair? | Action | Boats |
|------|---|---|-----------|-----------|-----|-----------|--------|-------|
| 0 | 0 | 3 | 1 | 5 | 6 | ✓ (6≤6) | L++, R-- | 1 |
| 1 | 1 | 2 | 2 | 4 | 6 | ✓ (6≤6) | L++, R-- | 2 |
| — | 2 | 1 | — | — | — | — | L>R, stop | — |

**Output:** `2`

**Complexity Analysis**
- **Time Complexity**: O(n log n) — Sorting dominates, two-pointer pass is O(n)
- **Space Complexity**: O(1) or O(log n) — O(1) extra space, O(log n) for sorting

---

#### **Approach 2: Brute Force (NOT FEASIBLE)**

**Core Idea**: Try all possible pairings and combinations.

**Why Not Feasible**: 
- Exponential number of pairing combinations
- O(2^n) time complexity
- Too slow even for small inputs

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | **Sort + Two Pointers** |
|-------------|-------------|------------------------|
| Time complexity | O(2^n) ❌ | **O(n log n) ✅** |
| Space complexity | O(n) | **O(1) ✅** |
| Code simplicity | Complex | **Clean ✅** |
| Optimal | ❌ | **✅** |

**Winner**: **Sort + Two Pointers** — optimal and clean greedy solution!

### Why Sorting is Essential?
```
Without sorting:
  - Can't identify lightest/heaviest efficiently
  - Need to search for best pairs
  - Lose greedy property

With sorting:
  - Lightest always at left pointer
  - Heaviest always at right pointer
  - Greedy pairing becomes O(1) decision
  - Overall O(n log n) time
```

### Why Greedy Pairing Works?
```
Intuition: Heavy people are the bottleneck

If heaviest (H) can share a boat:
  Best partner is lightest (L) — maximizes capacity used
  
If even L + H > limit:
  H must go alone (can't fit with anyone)
  No point trying other combinations
  
Result: Each decision is locally optimal and globally optimal!
```

### Visual Proof of Optimality
```
Example: [1, 2, 3, 5], limit = 6

Greedy approach:
  Boat 1: [1, 5] (sum = 6)
  Boat 2: [2, 3] (sum = 5)
  Total: 2 boats ✓

Alternative (non-greedy):
  Boat 1: [5] alone (didn't try pairing)
  Boat 2: [1, 3] (sum = 4)
  Boat 3: [2] alone
  Total: 3 boats ✗ (worse!)

Or:
  Boat 1: [2, 3] (sum = 5)
  Boat 2: [1, 5] (sum = 6)
  Total: 2 boats ✓ (same as greedy!)

Greedy always achieves optimal or better!
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Person**
```java
Input: people = [5], limit = 10
Output: 1
Explanation: One person needs one boat.
```

### 2. **All People Need Individual Boats**
```java
Input: people = [5,5,5,5], limit = 5
Output: 4
Explanation: No one can share a boat (5+5=10 > 5).
```

### 3. **Everyone Can Pair Up**
```java
Input: people = [1,2,3,4], limit = 5
Output: 2
Explanation: Pairs [1,4] and [2,3].
```

### 4. **Odd Number of People**
```java
Input: people = [1,2,3], limit = 3
Output: 2
Explanation: Pairs [1,2], then [3] alone.
```

### 5. **Two People, Can't Fit Together**
```java
Input: people = [3,4], limit = 5
Output: 2
Explanation: 3+4=7 > 5, each needs own boat.
```

### 6. **Two People, Exactly at Limit**
```java
Input: people = [3,3], limit = 6
Output: 1
Explanation: 3+3=6 ≤ 6, can share one boat.
```

### 7. **All Same Weight**
```java
Input: people = [2,2,2,2], limit = 5
Output: 2
Explanation: Each boat takes 2 people (2+2=4≤5).
```

### 8. **Everyone at Limit**
```java
Input: people = [100,100,100], limit = 100
Output: 3
Explanation: Each person at limit, must go alone.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Sorting the Array**
```java
// WRONG - no sorting
public int numRescueBoats(int[] people, int limit) {
    int left = 0, right = people.length - 1;
    int boats = 0;
    
    while (left <= right) {
        if (people[left] + people[right] <= limit) {
            left++;
        }
        right--;
        boats++;
    }
    
    return boats;
}
```

**Why wrong**: Without sorting, left isn't lightest and right isn't heaviest! Greedy pairing doesn't work.

**Dry run failure for people=[5,1,4,2], limit=6:**
```
Unsorted: [5,1,4,2]
L=0, R=3: 5+2=7 > 6 → 2 alone, boats=1
L=0, R=2: 5+4=9 > 6 → 4 alone, boats=2
L=0, R=1: 5+1=6 ≤ 6 → pair, boats=3
L=1, R=0: stop

Result: 3 boats (WRONG! Should be 2)
```

**Fix**: Always sort first
```java
Arrays.sort(people);
```

### ❌ **MISTAKE 2: Incrementing Boats Incorrectly**
```java
// WRONG - increments boats in wrong places
while (left <= right) {
    if (people[left] + people[right] <= limit) {
        left++;
        right--;
        boats++;  // WRONG! Also counting below
    } else {
        right--;
    }
    boats++;  // WRONG! Counting twice when pairing
}
```

**Why wrong**: Counts 2 boats when pairing (should be 1)!

**Dry run failure:**
```
[1,5], limit=6
L=0, R=1: 1+5=6 ≤ 6
  boats++ inside → boats=1
  boats++ outside → boats=2 (WRONG! Should be 1)
```

**Fix**: Increment boats exactly once per iteration
```java
while (left <= right) {
    if (people[left] + people[right] <= limit) {
        left++;
        right--;
    } else {
        right--;
    }
    boats++;  // Once per iteration
}
```

### ❌ **MISTAKE 3: Wrong Loop Condition**
```java
// WRONG - uses < instead of <=
while (left < right) {  // WRONG!
    // ...
}
```

**Why wrong**: Misses the last person when left == right!

**Dry run failure for people=[1,2,3], limit=5:**
```
Sorted: [1,2,3]

L=0, R=2: 1+3=4 ≤ 5 → pair, boats=1, L=1, R=1
L=1, R=1: loop stops (L < R is false)

Missed person at index 1! They need a boat too!
Result: 1 boat (WRONG! Should be 2)
```

**Fix**: Use <= to include last person
```java
while (left <= right) { ... }
```

### ❌ **MISTAKE 4: Moving Wrong Pointer**
```java
// WRONG - moves left when can't pair
while (left <= right) {
    if (people[left] + people[right] <= limit) {
        left++;
        right--;
    } else {
        left++;  // WRONG! Should move right
    }
    boats++;
}
```

**Why wrong**: When pairing fails, should take heaviest alone (move right), not lightest!

**Dry run failure for people=[1,5,5], limit=6:**
```
L=0, R=2: 1+5=6 ≤ 6 → pair, boats=1
L=1, R=1: 5+5=10 > 6
  Code moves L: L=2, R=1 (WRONG!)
  L > R, stops

Missed person at R=1!
```

**Fix**: Move right pointer when can't pair
```java
} else {
    right--;  // Heaviest goes alone
}
```

### ❌ **MISTAKE 5: Trying to Fit More Than 2 People**
```java
// WRONG - tries to add third person
while (left <= right) {
    int boatWeight = 0;
    int count = 0;
    
    // Try to fit as many as possible (WRONG approach!)
    while (left <= right && boatWeight + people[left] <= limit && count < 2) {
        boatWeight += people[left];
        left++;
        count++;
    }
    boats++;
}
```

**Why wrong**: Problem says "at most two people" per boat, not "as many as possible"!

**Fix**: Only check pairs, not multiple people
```java
// Correct: only check 1 or 2 people per boat
if (people[left] + people[right] <= limit) {
    // Take both
} else {
    // Take one
}
```

### ❌ **MISTAKE 6: Not Moving Both Pointers When Pairing**
```java
// WRONG - forgets to move left
if (people[left] + people[right] <= limit) {
    right--;  // WRONG! Also need left++
}
```

**Why wrong**: When two people share a boat, both are used up!

**Dry run failure:**
```
[1,2,4,5], limit=6
L=0, R=3: 1+5=6 ≤ 6 → should pair both
  Only moves R: R=2
  L still at 0

Next iteration: L=0, R=2: 1+4=5 ≤ 6
  Uses person 1 again! (WRONG!)
```

**Fix**: Move both pointers when pairing
```java
if (people[left] + people[right] <= limit) {
    left++;   // Both used
    right--;
}
```

### ❌ **MISTAKE 7: Off-by-One in Loop**
```java
// WRONG - forgets to check when left == right
while (left < right) {
    // ...
}
// Forgot to check: what if one person remains?
```

**Why wrong**: Last person (when left == right) still needs a boat!

**Fix**: Use <= and count boat in every iteration

---

## Complexity Analysis

### Time Complexity: **O(n log n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Sorting | O(n log n) | Built-in sort |
| Two-pointer pass | O(n) | Each person considered once |
| Pairing decision | O(1) | Simple comparison |
| **Total** | **O(n log n)** | Sorting dominates |

### Space Complexity: **O(1) or O(log n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Sorting (depends on implementation) | O(log n) to O(n) | Quicksort stack or merge sort |
| Pointer variables | O(1) | Few integers |
| **Total** | **O(1)** | Excluding sorting overhead |

**Why O(n log n) Time is Optimal:**
- Must sort to enable greedy pairing
- Can't do better than O(n log n) for comparison-based sorting
- Two-pointer pass is linear

---

## Visualization

### Complete Example Walkthrough

**Input:** `people = [3, 5, 3, 4]`, `limit = 5`

---

**Step 0: Sort**
```
Original: [3, 5, 3, 4]
Sorted:   [3, 3, 4, 5]
           0  1  2  3  (indices)
```

---

**Step 1: Initialize Pointers**
```
[3, 3, 4, 5]
 ↑        ↑
 L        R

L=0, R=3
boats=0
```

---

**Step 2: Try Pairing (0, 3)**
```
[3, 3, 4, 5]
 ↑        ↑
 L        R

people[L] + people[R] = 3 + 5 = 8
8 > 5 (limit) ✗

Can't pair! Heaviest (5) must go alone.

Boat 1: [5]
Move R--
boats = 1
```

---

**Step 3: Try Pairing (0, 2)**
```
[3, 3, 4, 5]
 ↑     ↑
 L     R

people[L] + people[R] = 3 + 4 = 7
7 > 5 (limit) ✗

Can't pair! Heavy person (4) goes alone.

Boat 2: [4]
Move R--
boats = 2
```

---

**Step 4: Try Pairing (0, 1)**
```
[3, 3, 4, 5]
 ↑  ↑
 L  R

people[L] + people[R] = 3 + 3 = 6
6 > 5 (limit) ✗

Can't pair! Person (3 at R) goes alone.

Boat 3: [3]
Move R--
boats = 3
```

---

**Step 5: Last Person (0, 0)**
```
[3, 3, 4, 5]
 ↑
L,R

L == R (one person remaining)

Boat 4: [3]
Move R--
boats = 4

L > R, stop
```

---

**Final Result:** `4 boats`

### Why Greedy Works - Visual Proof

```
Sorted: [3, 3, 4, 5], limit = 5

Step-by-step reasoning:

1. Heaviest = 5
   Can 5 pair with lightest (3)?
   5 + 3 = 8 > 5 ✗
   
   Since 5 can't fit with 3 (lightest),
   5 can't fit with anyone else either!
   → 5 must go alone ✓

2. Next heaviest = 4 (after removing 5)
   Can 4 pair with lightest (3)?
   4 + 3 = 7 > 5 ✗
   
   4 can't fit with anyone!
   → 4 must go alone ✓

3. Next heaviest = 3
   Can 3 pair with lightest (3)?
   3 + 3 = 6 > 5 ✗
   
   3 can't fit with 3!
   → This 3 must go alone ✓

4. Last person = 3
   → Goes alone ✓

Total: 4 boats (optimal)

Key insight: Each time we can't pair with lightest,
             that person MUST go alone!
```

---

## Comparison of Approaches

| Approach | Time | Space | Optimal | Notes |
|----------|------|-------|---------|-------|
| Brute Force | O(2^n) | O(n) | ❌ | Try all pairings |
| **Sort + Two Pointers** | **O(n log n)** | **O(1)** | **✅** | **Greedy pairing** |

**Recommendation**: Always use **Sort + Two Pointers** — it's the optimal solution!

---

## Key Takeaways

1. **Sort first** — enables greedy pairing from both ends
2. **Pair lightest with heaviest** — greedy choice is always optimal
3. **At most 2 people per boat** — simplifies to binary decision
4. **One boat per iteration** — increment boats every loop
5. **Loop while left <= right** — include last person when left == right
6. **Move right when can't pair** — heavy person goes alone
7. **O(n log n) optimal** — sorting required, can't avoid

---

## Interview Tips

**What to say in an interview:**

> "This is a greedy two-pointer problem. The key insight is that heavy people are the hardest to fit, so we should try to pair the heaviest remaining person with the lightest remaining person. I'll first sort the array in O(n log n). Then I'll use two pointers from both ends. If the lightest and heaviest can fit together (sum ≤ limit), I pair them and move both pointers. Otherwise, the heavy person must go alone since they can't even fit with the lightest person. Each iteration uses exactly one boat. This greedy strategy is optimal because if the heaviest person can share a boat with anyone, it must be with the lightest person."

**Key points to mention:**
1. **Sort first** — enables greedy pairing
2. **Greedy strategy** — pair lightest with heaviest
3. **Why greedy works** — if heavy can't fit with light, can't fit with anyone
4. **Binary decision** — at most 2 people per boat
5. **Complexity** — O(n log n) time (sorting), O(1) space

**If asked about alternatives:**
> "I could try to find optimal pairings without sorting, but that would require trying all combinations, which is exponential. Sorting enables the greedy approach to work in O(n log n) time, which is optimal."

**Common Follow-ups:**
- "What if boats can hold 3 people?" → More complex, need dynamic programming or different greedy approach
- "Prove greedy is optimal" → Show that if heavy can't fit with light, can't fit with anyone
- "What if we want to minimize trips (boats go back and forth)?" → Different problem, needs different approach

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Boats to Save People** | Medium | **Sort + Two Pointers** | **This problem** ← **At most 2 per boat** |
| Two Sum | Easy | Two Pointers | Find pair summing to target |
| 3Sum | Medium | Sort + Two Pointers | Find triplets summing to zero |
| Container With Most Water | Medium | Two Pointers | Maximize container area |
| Valid Triangle Number | Medium | Sort + Two Pointers | Count valid triangles |
| Partition Labels | Medium | Greedy | Partition string into intervals |

**Pattern Connection**:
- **Sorting** — Enables greedy decisions
- **Two Pointers** — Pair from both ends
- **Greedy** — Locally optimal choices lead to global optimum

---

## Final Pattern Label

✅ **Sorting + Two Pointers (Greedy Pairing: Lightest with Heaviest)**

**Remember:** Sort the array first. Use two pointers from both ends. Try to pair lightest with heaviest. If they fit (sum ≤ limit), take both and move both pointers. If they don't fit, heaviest goes alone (move only right pointer). Each iteration uses exactly one boat. Loop while left <= right. This greedy strategy is optimal because if the heaviest person can share a boat, it must be with the lightest person. O(n log n) time, O(1) space. This is the optimal solution!
