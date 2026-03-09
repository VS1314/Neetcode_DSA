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

**Follow-up:** Could you solve the problem in linear time and in O(1) space?

---

## Pattern Recognition

**Primary Pattern**: **Boyer-Moore Voting Algorithm (Optimal) / HashMap Frequency Counting**

**Why This Pattern?**
- Need to find element appearing > n/2 times
- Follow-up requires O(n) time and O(1) space
- Guaranteed that majority element exists
- Key insight: Majority element appears more than all others combined

**Key Insight**: If we pair each occurrence of the majority element with one occurrence of any other element and cancel them out, the majority element will still have occurrences left over.

**Related Patterns**:
1. **Frequency Counting** - HashMap approach
2. **Sorting** - Alternative approach
3. **Randomization** - Probabilistic approach

---

## Algorithm & Approach

### Core Insight

**Critical Observation:** The majority element appears MORE than n/2 times.

**What this means:**
- In an array of 7 elements, majority must appear ≥ 4 times
- In an array of 8 elements, majority must appear ≥ 5 times
- The majority element always "wins" if we pit it against all others

**Visual Example:**
```
Array: [2,2,1,1,1,2,2]
       M M O O O M M    (M = majority, O = other)

Count: M appears 4 times, all others combined appear 3 times
Result: M > others, so M wins!
```

### Approach Comparison
| Approach | Time | Space | Meets Follow-up? |
|----------|------|-------|------------------|
| HashMap | O(n) | O(n) | ❌ Space not O(1) |
| Sorting | O(n log n) | O(1) | ❌ Time not O(n) |
| **Boyer-Moore** | **O(n)** ✅ | **O(1)** ✅ | **✅ Optimal** |

### Step-by-Step Algorithm

#### **Approach 1: HashMap Frequency Counting**

```
1. Create HashMap to count frequencies
2. For each element:
   a. Increment its count
   b. If count > n/2, return element
3. Return any element (guaranteed to exist)
```

**Code Implementation**
```java
public int majorityElement(int[] nums) {
    HashMap<Integer, Integer> map = new HashMap<>();
    int n = nums.length;
    
    for (int num : nums) {
        map.put(num, map.getOrDefault(num, 0) + 1);
        if (map.get(num) > n / 2) {
            return num;
        }
    }
    
    return -1; // Never reached if majority exists
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) - single pass through array
- **Space Complexity**: O(n) - HashMap stores frequencies

**Pros:** Easy to understand, clear logic  
**Cons:** Uses O(n) extra space, doesn't meet follow-up requirement

#### **Approach 2: Sorting**

```
1. Sort the array
2. Return middle element (index n/2)
```

**Why this works:**
- Majority element appears > n/2 times
- After sorting, it MUST occupy the middle position

**Example:**
```
[2,2,1,1,1,2,2] → sorted → [1,1,1,2,2,2,2]
                           Index: 0 1 2 3 4 5 6
                           Middle (n/2 = 3): nums[3] = 2 ✓
```

**Code Implementation**
```java
public int majorityElement(int[] nums) {
    Arrays.sort(nums);
    return nums[nums.length / 2];
}
```

**Complexity Analysis**
- **Time Complexity**: O(n log n) - sorting
- **Space Complexity**: O(1) - in-place sort (or O(n) for some sort algorithms)

**Pros:** Very simple, elegant  
**Cons:** O(n log n) time doesn't meet follow-up requirement

#### **Approach 3: Boyer-Moore Voting Algorithm (OPTIMAL)**
```
Phase 1: Find Candidate
1. Initialize candidate = first element, count = 0
2. For each element:
   a. If count == 0, set candidate = current element
   b. If element == candidate, count++
   c. If element != candidate, count--
3. Candidate is the potential majority element

Phase 2: Verify (only needed if majority not guaranteed)
1. Count occurrences of candidate
2. If count > n/2, return candidate
3. Else, no majority exists
```

**Note:** Phase 2 is NOT needed if problem guarantees majority exists!

**How It Works - The Voting Analogy:**
```
Think of it as voting:
- Each occurrence of an element is a vote
- We pair votes: one for candidate, one against
- When paired votes cancel (count = 0), switch candidate
- The candidate left standing is the majority
```

**Code Implementation**
```java
class Solution {
    public int majorityElement(int[] nums) {
        int candidate = nums[0];
        int count = 0;
        
        // Phase 1: Find candidate
        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            
            if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }
        
        // Phase 2: Verify (not needed if majority guaranteed)
        // int verifyCount = 0;
        // for (int num : nums) {
        //     if (num == candidate) verifyCount++;
        // }
        // return verifyCount > nums.length / 2 ? candidate : -1;
        
        return candidate;
    }
}
```

**Alternative - More Concise:**
```java
public int majorityElement(int[] nums) {
    int candidate = 0, count = 0;
    
    for (int num : nums) {
        if (count == 0) candidate = num;
        count += (num == candidate) ? 1 : -1;
    }
    
    return candidate;
}
```

### Understanding Boyer-Moore Voting

**Key Insight:** If we cancel out each occurrence of the majority element with one occurrence of any other element, the majority element will survive because it appears more than n/2 times.

**Visual Example:**
```
Array: [7, 7, 5, 7, 5, 1, 5, 7, 5, 5, 7, 7, 7]

Step-by-step:

num | candidate | count | Explanation
----|-----------|-------|-------------
7   | 7         | 1     | count=0, set candidate=7, increment
7   | 7         | 2     | matches candidate, increment
5   | 7         | 1     | doesn't match, decrement
7   | 7         | 2     | matches, increment
5   | 7         | 1     | doesn't match, decrement
1   | 7         | 0     | doesn't match, decrement
5   | 5         | 1     | count=0, set candidate=5, increment
7   | 5         | 0     | doesn't match, decrement
5   | 5         | 1     | count=0, set candidate=5, increment
5   | 5         | 2     | matches, increment
7   | 5         | 1     | doesn't match, decrement
7   | 5         | 0     | doesn't match, decrement
7   | 7         | 1     | count=0, set candidate=7, increment

Final candidate: 7

Count of 7 in array: 7 times out of 13 (7 > 13/2 = 6.5) ✓
```

### Example Walkthrough

**Input:** nums = [2,2,1,1,1,2,2]
| Index | num | count before | candidate before | Action | count after | candidate after |
|-------|-----|--------------|------------------|--------|-------------|-----------------|
| 0 | 2 | 0 | - | count=0, set candidate=2 | 1 | 2 |
| 1 | 2 | 1 | 2 | matches, count++ | 2 | 2 |
| 2 | 1 | 2 | 2 | doesn't match, count-- | 1 | 2 |
| 3 | 1 | 1 | 2 | doesn't match, count-- | 0 | 2 |
| 4 | 1 | 0 | 2 | count=0, set candidate=1 | 1 | 1 |
| 5 | 2 | 1 | 1 | doesn't match, count-- | 0 | 1 |
| 6 | 2 | 0 | 1 | count=0, set candidate=2 | 1 | 2 |
**Output:** 2
**Verification:** 2 appears 4 times out of 7 (4 > 3.5) ✓
### Complexity Analysis
- **Time Complexity**: O(n) - single pass through array
- **Space Complexity**: O(1) - only two variables (candidate, count)
---
## Why This Strategy?
### Problem Requirements Analysis
**For the follow-up (O(n) time, O(1) space):**
| Approach | Time | Space | Meets Requirements? |
|----------|------|-------|---------------------|
| HashMap | O(n) ✓ | O(n) ❌ | NO - extra space |
| Sorting | O(n log n) ❌ | O(1) ✓ | NO - too slow |
| **Boyer-Moore** | **O(n)** ✅ | **O(1)** ✅ | **YES** ✅ |
**Winner**: Boyer-Moore Voting Algorithm - ONLY approach meeting both requirements!
### Why Boyer-Moore Works
**Mathematical Proof:**
- Let M = count of majority element
- Let O = count of all other elements combined
- Given: M > n/2
- Therefore: M > O (because M + O = n and M > n/2 means M > O)
**In the algorithm:**
- Each time we pair M with O, we decrement count
- After all pairings, M still has elements left (because M > O)
- The surviving candidate is the majority element
---
## Critical Edge Cases & Gotchas

### 1. **Single Element**
```java
Input: nums = [1]
Output: 1
Explanation: Only element is the majority
```

### 2. **All Same Elements**
```java
Input: nums = [5,5,5,5,5]
Output: 5
Explanation: 100% majority
```

### 3. **Majority at Start**
```java
Input: nums = [1,1,1,2,3]
Output: 1
```

### 4. **Majority at End**
```java
Input: nums = [1,2,3,3,3]
Output: 3
```

### 5. **Alternating with Majority**
```java
Input: nums = [1,2,1,2,1]
Output: 1
```

### 6. **Exactly n/2 + 1 occurrences**
```java
Input: nums = [1,2,1,3,1] // 1 appears 3 times out of 5
Output: 1
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Resetting Candidate When Count = 0**
```java
// WRONG - Doesn't update candidate when count reaches 0!
for (int num : nums) {
    if (num == candidate) {
        count++;
    } else {
        count--;
    }
}
```

**Why wrong**: When count = 0, we must consider a new candidate.

**Fix**: Check `if (count == 0) candidate = num;` before incrementing/decrementing

### ❌ **MISTAKE 2: Checking count == 0 After Update**
```java
// WRONG - Checks count after decrementing!
for (int num : nums) {
    if (num == candidate) {
        count++;
    } else {
        count--;
    }
    if (count == 0) candidate = num; // Too late!
}
```

**Why wrong**: Should check BEFORE updating count, not after.

**Fix**: Check `count == 0` at the beginning of the loop

### ❌ **MISTAKE 3: Returning Without Verification (If Majority Not Guaranteed)**
```java
// WRONG - If majority might not exist!
return candidate; // What if there's no majority?
```

**Why wrong**: If problem doesn't guarantee majority exists, must verify.

**Fix**: Add verification phase to count candidate occurrences

### ❌ **MISTAKE 4: Wrong Majority Definition**
```java
// WRONG - Using >= instead of >
if (map.get(num) >= n / 2) {
    return num;
}
```

**Why wrong**: Majority means MORE than n/2, not equal to.

**Fix**: Use `> n / 2`

### ❌ **MISTAKE 5: Integer Division Confusion**
```java
// WRONG - Might miss the point
int threshold = n / 2; // This is floor division
// For n=5: threshold = 2, but we need count > 2.5
```

**Why wrong**: While `n / 2` gives floor value, using `>` makes it correct.

**Correct understanding:**
- n=5: n/2 = 2, need count > 2 (i.e., ≥ 3) ✓
- n=6: n/2 = 3, need count > 3 (i.e., ≥ 4) ✓

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Approach | Time | Explanation |
|----------|------|-------------|
| HashMap | O(n) | Single pass, O(1) operations per element |
| Sorting | O(n log n) | Sorting dominates |
| Boyer-Moore | O(n) | Single pass, O(1) operations per element |

**Boyer-Moore detailed:**
- Phase 1: O(n) - one pass through array
- Phase 2 (if needed): O(n) - one pass to verify
- Total: O(2n) = O(n)

### Space Complexity

| Approach | Space | Explanation |
|----------|-------|-------------|
| HashMap | O(n) | Stores up to n different elements |
| Sorting | O(1) or O(n) | Depends on sort algorithm |
| Boyer-Moore | O(1) | Only 2 variables |

---

## Visualization

### Boyer-Moore Pairing Concept
```
Array: [A, A, B, A, C, A, A]
       M  M  O  M  O  M  M

Pairing (canceling M with O):
Round 1: A vs B → cancel → [A, _, _, A, C, A, A]
Round 2: A vs C → cancel → [A, _, _, _, _, A, A]
Remaining: [A, A, A]

Result: A is the majority!
```

### Step-by-Step Trace
```
Input: [2,2,1,1,1,2,2]

Initial: candidate = ?, count = 0

i=0, num=2: count=0 → candidate=2, count=1
            candidate: 2, count: 1

i=1, num=2: num==candidate → count++
            candidate: 2, count: 2

i=2, num=1: num!=candidate → count--
            candidate: 2, count: 1

i=3, num=1: num!=candidate → count--
            candidate: 2, count: 0

i=4, num=1: count=0 → candidate=1, count=1
            candidate: 1, count: 1

i=5, num=2: num!=candidate → count--
            candidate: 1, count: 0

i=6, num=2: count=0 → candidate=2, count=1
            candidate: 2, count: 1

Final: candidate = 2
```

---

## Comparison of Approaches

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| HashMap | O(n) | O(n) | Easy to understand | Extra space |
| Sorting | O(n log n) | O(1) | Very simple | Slower than optimal |
| **Boyer-Moore** | **O(n)** | **O(1)** | **Optimal for follow-up** ✅ | **Requires understanding** |
| Randomization | O(n) expected | O(1) | Interesting | Not deterministic |

**Best Choice**: Boyer-Moore for optimal solution ✓

---

## Key Takeaways

1. **Pattern Recognition**: "Majority element" + "appears > n/2 times" → Boyer-Moore Voting
2. **Core Insight**: Majority element survives when paired with all others
3. **Candidate Switching**: When count = 0, switch to new candidate
4. **Guaranteed Majority**: If guaranteed, no verification needed
5. **Follow-up Mastery**: O(n) time + O(1) space = Boyer-Moore
6. **Interview Critical**: Must explain WHY algorithm works, not just HOW

---

## Interview Tips

**What to say in an interview:**

> "For the optimal solution with O(n) time and O(1) space, I'll use the Boyer-Moore Voting Algorithm. The key insight is that the majority element appears more than all other elements combined. I maintain a candidate and count - when count reaches zero, I switch candidates. Since the majority element appears more than n/2 times, it will survive all the cancellations and be the final candidate."

**Key points to mention:**
1. **Pattern**: Boyer-Moore Voting Algorithm
2. **Why it works**: Majority > all others combined
3. **Candidate switching**: When count = 0, consider new candidate
4. **Complexity**: O(n) time, O(1) space
5. **Alternative**: HashMap (O(n) space) or Sorting (O(n log n) time)

**If asked "Why does Boyer-Moore work?"**
> "The majority element appears more than n/2 times, which means it appears more than all other elements combined. When we pair each majority element with any other element and cancel them out (decrement count), the majority will still have occurrences remaining. The algorithm simulates this pairing process by switching candidates when count reaches zero."

**If asked about verification:**
> "If the problem guarantees a majority element exists, we don't need verification. If not, we should add a second pass to count the candidate's occurrences and verify it exceeds n/2."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Majority Element** | Easy | **Boyer-Moore Voting** | **Find element > n/2** ← This problem |
| Majority Element II | Medium | Modified Boyer-Moore | Find all elements > n/3 |
| Single Number | Easy | XOR | Element appearing once |
| Find Peak Element | Medium | Binary Search | Local maximum |
| Kth Largest Element | Medium | QuickSelect | Not about majority |

**Pattern Family**: Boyer-Moore Voting / Frequency Counting

---

## Final Pattern Label

✅ **Boyer-Moore Voting Algorithm – Optimal Majority Finding**

**Remember:** When you see "majority element" + "appears > n/2 times" + "O(1) space" → think Boyer-Moore Voting! The key is candidate switching when count = 0.