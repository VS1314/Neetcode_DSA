# Longest Consecutive Sequence

## Problem Description

**Difficulty**: Hard

Given an unsorted array of integers `nums`, return the length of the longest consecutive elements sequence.

You must write an algorithm that runs in **O(n) time**.

## Examples

### Example 1:
```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
```

### Example 2:
```
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
Explanation: The longest consecutive sequence is [0,1,2,3,4,5,6,7,8]. Length is 9.
```

### Example 3:
```
Input: nums = [9,1,4,7,3,-1,0,5,8,-2,6,2]
Output: 7
Explanation: The longest consecutive sequence is [-2,-1,0,1,2,3,4]. Length is 7.
```

## Constraints
- 0 <= nums.length <= 10^5
- -10^9 <= nums[i] <= 10^9

**Critical Constraint:** Must be O(n) time complexity

---

## Pattern Recognition

**Primary Pattern**: **HashSet + Sequence Start Detection**

**Why This Pattern?**
- Need O(n) time → cannot sort (O(n log n))
- Need fast lookup → O(1) for HashSet
- Must find consecutive sequences
- Key insight: Only start counting from sequence beginnings

**Key Insight**: A number is the **start of a sequence** ONLY if `(num - 1)` does NOT exist. This prevents redundant counting and ensures O(n) time.

**Related Patterns**:
1. **Union Find** - Alternative approach for connected components
2. **HashSet Lookup** - Fast existence checking
3. **Sequence Detection** - Finding continuous ranges

---

## Algorithm & Approach

### Core Insight

**Critical Observation:** Only start counting when the number is the **start** of a sequence.

**Why this works:**
```
Array: [2, 3, 4, 5]

num | num-1 exists? | Is start? | Action
----|---------------|-----------|--------
2   | ❌ No        | ✅ YES    | Count 2→3→4→5 (length 4)
3   | ✅ Yes (2)   | ❌ NO     | Skip (2 will handle it)
4   | ✅ Yes (3)   | ❌ NO     | Skip
5   | ✅ Yes (4)   | ❌ NO     | Skip
```

**Result:** We count the entire sequence **exactly once** starting from 2!

### Why Brute Force Fails

**Naive approach:**
```
For every number:
  Count num, num+1, num+2, num+3...
  
Example: [1,2,3,4,5,6,7]
- Start at 1: count 1→2→3→4→5→6→7 (7 operations)
- Start at 2: count 2→3→4→5→6→7 (6 operations)
- Start at 3: count 3→4→5→6→7 (5 operations)
...
Total: 7+6+5+4+3+2+1 = 28 operations = O(n²) ❌
```

### Optimal Idea

**Smart approach:**
```
Only count from sequence START (where num-1 doesn't exist)

Example: [1,2,3,4,5,6,7]
- Start at 1: count 1→2→3→4→5→6→7 (7 operations)
- Skip 2,3,4,5,6,7 (they're not starts)

Total: 7 operations = O(n) ✅
```

### Step-by-Step Algorithm
```
1. Build HashSet from array (for O(1) lookup)
2. Initialize longest = 0
3. For each number in set:
   a. If (num - 1) exists in set:
      - SKIP this number (it's not a sequence start)
   b. Else (num - 1) doesn't exist:
      - This IS a sequence start
      - Count: num, num+1, num+2... while they exist
      - Update longest
4. Return longest
```

### Code Implementation

```java
class Solution {
    public int longestConsecutive(int[] nums) {
        // Step 1: Build HashSet for O(1) lookup
        HashSet<Integer> set = new HashSet<>();
        for (int n : nums) {
            set.add(n);
        }
        
        int longest = 0;
        
        // Step 2: Check each number
        for (int num : set) {
            // Only start if num-1 doesn't exist (sequence start)
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int count = 1;
                
                // Count consecutive numbers
                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    count++;
                }
                
                longest = Math.max(longest, count);
            }
        }
        
        return longest;
    }
}
```

### Understanding Each Line

**Line 1: Build HashSet**
```java
HashSet<Integer> set = new HashSet<>();
for (int n : nums) {
    set.add(n);
}
```
- **Why?** O(1) lookup for contains() operations
- **Note:** Automatically handles duplicates (set property)

**Line 2: Check if sequence start**
```java
if (!set.contains(num - 1)) {
```
- **Why?** If `num-1` exists, some earlier number will count this sequence
- **Example:** For `[2,3,4]`, only 2 satisfies this condition

**Line 3: Count sequence length**
```java
while (set.contains(currentNum + 1)) {
    currentNum++;
    count++;
}
```
- **Why?** Keep extending while consecutive numbers exist
- **Guarantee:** Each number counted at most once across all sequences

### Example Walkthrough

**Input:** nums = [2,20,4,10,3,4,5]

**Step 1: Build HashSet**
```
Original: [2,20,4,10,3,4,5]
HashSet:  {2,3,4,5,10,20} (duplicates removed)
```

**Step 2: Iterate through set**

| num | num-1 in set? | Is start? | Sequence | Count | longest |
|-----|---------------|-----------|----------|-------|---------|
| 2 | ❌ (1 not in set) | ✅ YES | 2→3→4→5 | 4 | 4 |
| 3 | ✅ (2 in set) | ❌ NO | Skip | \_\_ | 4 |
| 4 | ✅ (3 in set) | ❌ NO | Skip | \_\_ | 4 |
| 5 | ✅ (4 in set) | ❌ NO | Skip | \_\_ | 4 |
| 10 | ❌ (9 not in set) | ✅ YES | 10 (stop) | 1 | 4 |
| 20 | ❌ (19 not in set) | ✅ YES | 20 (stop) | 1 | 4 |

**Output:** 4

**Detailed trace for num=2:**
```
num = 2
2-1 = 1 → not in set → START!

currentNum = 2, count = 1
  2+1 = 3 in set? YES → currentNum = 3, count = 2
  3+1 = 4 in set? YES → currentNum = 4, count = 3
  4+1 = 5 in set? YES → currentNum = 5, count = 4
  5+1 = 6 in set? NO → STOP

longest = max(0, 4) = 4
```

### Full Dry Run - Example 2

**Input:** nums = [0,3,2,5,4,6,1,1]

**Step 1: Build HashSet**
```
HashSet: {0,1,2,3,4,5,6}
```

**Step 2: Process**
```
Only 0 is a start (0-1=-1 not in set)

num = 0:
  0 → 1 → 2 → 3 → 4 → 5 → 6
  count = 7
  
All others (1,2,3,4,5,6) are skipped because their predecessors exist.
```

**Output:** 7

### Complexity Analysis
- **Time Complexity**: O(n)
  - Building set: O(n)
  - For loop: O(n) iterations
  - While loop: Each number visited at most once across all sequences
  - Total: O(n) + O(n) = O(n)
- **Space Complexity**: O(n) for the HashSet

---

## Why This Strategy?

### Problem Requirements Analysis
| Approach | Time | Space | Meets O(n) constraint? |
|----------|------|-------|------------------------|
| Brute Force (nested loops) | O(n²) | O(1) | ❌ Too slow |
| **Sorting** | **O(n log n)** | **O(1)-O(n)** | **❌ Violates constraint** |
| **HashSet + Smart Start** | **O(n)** ✅ | **O(n)** | **✅ Optimal** |
| Union Find | O(n α(n)) | O(n) | ✅ Complex |

**Winner**: HashSet + Sequence Start Detection - ONLY simple O(n) solution!

### Why NOT Sorting?

**Sorting approach:**
```java
Arrays.sort(nums); // O(n log n) ❌
// Then scan for consecutive sequences
```
- ✅ Easy to implement
- ❌ O(n log n) time - violates constraint!

**Our approach:**
- ✅ True O(n) time
- ✅ Simple logic
- ✅ Meets all requirements

### Why Each Number is Visited At Most Twice?

**Claim:** Total operations across all while loops = O(n)

**Proof:**
- Each number can be in at most ONE sequence
- Each number is counted at most ONCE in a while loop
- Outer loop: n iterations
- Inner while loops combined: at most n increments total
- Total: O(n) + O(n) = O(n) ✓

---

## Critical Edge Cases & Gotchas

### 1. **Empty Array**
```java
Input: nums = []
Output: 0
Explanation: No elements
```

### 2. **Single Element**
```java
Input: nums = [1]
Output: 1
Explanation: Single element is a sequence of length 1
```

### 3. **Duplicates**
```java
Input: nums = [1,2,2,3]
Output: 3
Explanation: HashSet removes duplicates → {1,2,3}
```

### 4. **No Consecutive Numbers**
```java
Input: nums = [1,3,5,7]
Output: 1
Explanation: Each element is its own sequence
```

### 5. **Negative Numbers**
```java
Input: nums = [-3,-2,-1,0,1]
Output: 5
Explanation: Works with negatives
```

### 6. **All Same Number**
```java
Input: nums = [5,5,5,5]
Output: 1
Explanation: Set becomes {5}, length = 1
```

### 7. **Large Gap**
```java
Input: nums = [1,2,3,100,101,102]
Output: 3
Explanation: Two sequences, both length 3
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Checking for Sequence Start**
```java
// WRONG - O(n²) solution!
for (int num : set) {
    int count = 1;
    while (set.contains(num + count)) {
        count++;
    }
    // This counts EVERY number, not just starts!
}
```

**Why wrong**: Every number in a sequence triggers a full count.

**Fix**: Only count if `!set.contains(num - 1)`

### ❌ **MISTAKE 2: Using Array Instead of HashSet**
```java
// WRONG - Slow lookup!
for (int num : nums) {
    if (!arrayContains(nums, num - 1)) {
        // arrayContains is O(n), making total O(n²)!
    }
}
```

**Why wrong**: Array lookup is O(n), not O(1).

**Fix**: Use HashSet for O(1) lookup

### ❌ **MISTAKE 3: Sorting the Array**
```java
// WRONG - Violates O(n) constraint!
Arrays.sort(nums); // O(n log n) ❌
```

**Why wrong**: Problem requires O(n) time.

**Fix**: Don't sort! Use HashSet approach

### ❌ **MISTAKE 4: Not Handling Empty Array**
```java
// WRONG - Might return wrong value!
public int longestConsecutive(int[] nums) {
    // What if nums is empty?
    HashSet<Integer> set = new HashSet<>();
    // ...
    return longest; // Should return 0 for empty
}
```

**Why wrong**: Empty array should return 0.

**Fix**: Initialize `longest = 0` (already correct in our code)

### ❌ **MISTAKE 5: Counting from num+1 Instead of num**
```java
// WRONG - Off by one!
if (!set.contains(num - 1)) {
    int currentNum = num + 1; // Should start at num!
    int count = 0; // Should start at 1!
}
```

**Why wrong**: Misses the starting number itself.

**Fix**: Start with `currentNum = num` and `count = 1`

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Explanation |
|-----------|------|-------------|
| Build HashSet | O(n) | Add n elements |
| Outer for loop | O(n) | n iterations |
| Inner while loop | O(n) total | Each number checked at most once |
| Total | O(n + n) = O(n) | Linear time |

**Why while loop is O(n) total:**
- Each number in the array appears in the while loop at most once
- Even if we have 100 sequences, total elements checked = n
- Amortized O(1) per outer loop iteration

### Space Complexity: **O(n)**

| Component | Space |
|-----------|-------|
| HashSet | O(n) - stores all unique elements |
| Variables | O(1) - longest, currentNum, count |
| Total | O(n) |

---

## Visualization

### Example Walkthrough
```
Input: [100, 4, 200, 1, 3, 2]

HashSet: {1, 2, 3, 4, 100, 200}

Visual sequences:
1 → 2 → 3 → 4   (length 4) ✓
100             (length 1)
200             (length 1)

Processing:

num=1:  1-1=0 not in set → START
        1 → 2 → 3 → 4
        count = 4
        longest = 4

num=2:  2-1=1 in set → SKIP
num=3:  3-1=2 in set → SKIP  
num=4:  4-1=3 in set → SKIP

num=100: 99 not in set → START
         100 (no 101)
         count = 1
         longest = 4

num=200: 199 not in set → START
         200 (no 201)
         count = 1
         longest = 4

Answer: 4
```

---

## Comparison of Approaches

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Brute Force | O(n²) | O(1) | Simple | Too slow |
| Sorting | O(n log n) | O(1)-O(n) | Straightforward | Violates O(n) constraint |
| **HashSet + Start Detection** | **O(n)** ✅ | **O(n)** | **Optimal, meets constraint** | Requires insight |
| Union Find | O(n α(n)) | O(n) | Elegant | Overcomplicated |

**Best Choice**: HashSet + Sequence Start Detection ✓

---

## Key Takeaways

1. **Constraint-Driven**: O(n) requirement → rules out sorting
2. **Sequence Start**: Only count from numbers where `num-1` doesn't exist
3. **HashSet Power**: O(1) lookup makes linear time possible
4. **Amortized Analysis**: Each number visited at most twice total
5. **Pattern Recognition**: "Consecutive sequence" + "O(n)" → HashSet pattern
6. **Interview Critical**: Must explain why it's O(n), not O(n²)

---

## Interview Tips

**What to say in an interview:**

> "Since I need O(n) time, I cannot sort. I'll use a HashSet for O(1) lookups. The key insight is to only start counting from sequence beginnings - numbers where num-1 doesn't exist in the set. For each sequence start, I count consecutive numbers. Since each number is counted at most once across all sequences, this is O(n) time with O(n) space."

**Key points to mention:**
1. **Why HashSet**: O(1) lookup vs O(n) for array
2. **Sequence start logic**: `!set.contains(num - 1)` prevents redundant work
3. **Why O(n)**: Each number visited at most twice (once in loop, once in while)
4. **Why not sort**: O(n log n) violates constraint
5. **Edge cases**: Empty array, duplicates, negatives

**If asked "Why is the while loop O(n)?"**
> "Each number in the array can only be counted in one sequence. Even though we have a nested loop, the while loop across all iterations of the for loop will visit each number at most once. So the total work is O(n) for the outer loop + O(n) total for all inner loops = O(n)."

**If asked about space optimization:**
> "We could use Union Find with path compression for O(n α(n)) time and O(n) space, but it's more complex. The HashSet approach is simpler and already optimal at O(n)."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Longest Consecutive Sequence** | Hard | **HashSet + Start Detection** | **Find longest sequence** ← This problem |
| Missing Number | Easy | HashSet / XOR | Find single missing number |
| First Missing Positive | Hard | Index as Hash | Find in O(1) space |
| Longest Substring Without Repeating | Medium | Sliding Window | Substrings, not elements |
| Contains Duplicate | Easy | HashSet | Simple existence check |

**Pattern Family**: HashSet Lookup / Sequence Detection

---

## Final Pattern Label

✅ **HashSet + Sequence Start Detection**

**Remember:** When you see "consecutive sequence" + "O(n) time" → think HashSet with smart start detection! Only count from sequence beginnings where `num-1` doesn't exist.