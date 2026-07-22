# Find the Duplicate Number

## Problem Description

**Difficulty**: Medium

You are given an array of integers `nums` containing `n + 1` integers. Each integer in `nums` is in the range `[1, n]` inclusive.

There is exactly **one repeated integer** in `nums`, and every other integer appears at most once.

Return the repeated integer.

**Key Constraints:**
- Array has `n + 1` elements
- Values are in range `[1, n]`
- Exactly **one** number is repeated
- Duplicate can appear 2 or more times
- Other numbers appear **at most once** (can be missing)

**Important Observation:**
- Since we have `n + 1` numbers in range `[1, n]`, by **pigeonhole principle**, at least one number must repeat
- Array can be viewed as a **linked list** where `nums[i]` points to index `nums[i]`

**Follow-up Challenge:**
Can you solve the problem **without** modifying the array `nums` and using **O(1)** extra space?

---

## Examples

### Example 1:
```
Input: nums = [1,2,3,2,2]
Output: 2

Explanation:
  n = 4 (since length = 5 = n + 1)
  Range: [1, 4]
  Number 2 appears 3 times
  Numbers 1, 3 present once
  Number 4 is missing
  Duplicate: 2
```

### Example 2:
```
Input: nums = [1,2,3,4,4]
Output: 4

Explanation:
  n = 4
  Range: [1, 4]
  Number 4 appears twice
  Numbers 1, 2, 3 present once
  Duplicate: 4
```

### Example 3:
```
Input: nums = [3,1,3,4,2]
Output: 3

Explanation:
  n = 4
  Number 3 appears twice
  All other numbers present once
```

### Example 4:
```
Input: nums = [1,1]
Output: 1

Explanation:
  n = 1
  Smallest possible case
  Number 1 appears twice
```

### Example 5:
```
Input: nums = [1,1,2]
Output: 1

Explanation:
  n = 2
  Number 1 appears twice
  Number 2 appears once
```

### Example 6:
```
Input: nums = [2,5,9,6,9,3,8,9,7,1,4]
Output: 9

Explanation:
  n = 10 (length = 11)
  Number 9 appears 3 times
  Duplicate can appear more than twice
```

### Example 7:
```
Input: nums = [3,3,3,3,3]
Output: 3

Explanation:
  n = 4
  Number 3 appears 5 times (all elements)
  Numbers 1, 2, 4 are missing
```

### Example 8:
```
Input: nums = [2,1,3,4,5,6,7,8,9,9]
Output: 9

Explanation:
  Large array with duplicate at end
  Floyd's algorithm efficiently finds it
```

### Example 9:
```
Input: nums = [1,2,2,3,4]
Output: 2

Explanation:
  Duplicate in middle of sorted array
```

### Example 10:
```
Input: nums = [4,3,1,4,2]
Output: 4

Explanation:
  Unsorted array
  Duplicate is 4
```

## Constraints
- `1 <= n <= 10,000`
- `nums.length == n + 1`
- `1 <= nums[i] <= n`
- There is exactly **one repeated integer**
- All other integers appear **at most once**

**Recommended Complexity (Follow-up)**: 
- Time: O(n)
- Space: O(1) without modifying array

---

## Pattern Recognition

**Primary Pattern**: **Floyd's Cycle Detection (Tortoise and Hare)**

**Why This Pattern?**
- Array can be treated as a **linked list**
- Each value points to an index: `nums[i] → nums[nums[i]]`
- Duplicate creates a **cycle** in this implicit linked list
- Use **fast/slow pointers** to detect cycle
- Find **cycle entrance** = duplicate number

**Key Insight**: Array as Implicit Linked List
```
Example: nums = [1,3,4,2,2]
Indices:      0  1  2  3  4

View as linked list:
  Start at index 0
  nums[0] = 1 → go to index 1
  nums[1] = 3 → go to index 3
  nums[3] = 2 → go to index 2
  nums[2] = 4 → go to index 4
  nums[4] = 2 → go to index 2 ← CYCLE!
  
Path: 0 → 1 → 3 → 2 → 4 → 2 → 4 → 2 ...
                    ↑_________↓
                       Cycle!
  
Cycle entrance is at index 2
nums[2] = 4? No!
The VALUE that causes cycle = 2 ✓
```

**Why Duplicate Creates Cycle**:
```
If number X appears twice at indices i and j:
  nums[i] = X
  nums[j] = X
  
Both point to same index X:
  From index i → go to index X
  From index j → go to index X
  
Two arrows pointing to same node = cycle entrance!

Example: [1,3,4,2,2]
  Index 3: nums[3] = 2 → index 2
  Index 4: nums[4] = 2 → index 2
  
  Two paths to index 2 = cycle entrance
  The duplicate value is 2 ✓
```

**Floyd's Two-Phase Algorithm**:
```
Phase 1: Detect cycle exists
  slow = nums[slow] (move 1 step)
  fast = nums[nums[fast]] (move 2 steps)
  
  Continue until slow == fast
  They meet inside the cycle
  
Phase 2: Find cycle entrance
  slow2 = 0 (start from beginning)
  slow = meeting point
  
  Move both one step at a time:
    slow = nums[slow]
    slow2 = nums[slow2]
  
  When slow == slow2:
    This is the cycle entrance
    This is the duplicate number! ✓
```

**Example: Finding Duplicate**
```
nums = [1,3,4,2,2]
Indices: 0  1  2  3  4

Phase 1: Detect cycle
  slow = 0, fast = 0
  
  Step 1:
    slow = nums[0] = 1
    fast = nums[nums[0]] = nums[1] = 3
  
  Step 2:
    slow = nums[1] = 3
    fast = nums[nums[3]] = nums[2] = 4
  
  Step 3:
    slow = nums[3] = 2
    fast = nums[nums[4]] = nums[2] = 4
  
  Step 4:
    slow = nums[2] = 4
    fast = nums[nums[4]] = nums[2] = 4
  
  slow == fast = 4 (met!)

Phase 2: Find entrance
  slow = 4 (meeting point)
  slow2 = 0 (start)
  
  Step 1:
    slow = nums[4] = 2
    slow2 = nums[0] = 1
  
  Step 2:
    slow = nums[2] = 4
    slow2 = nums[1] = 3
  
  Step 3:
    slow = nums[4] = 2
    slow2 = nums[3] = 2
  
  slow == slow2 = 2 ✓
  Duplicate is 2!
```

**Why This Works (Mathematical Proof)**:
```
Let:
  μ = distance from start to cycle entrance
  λ = cycle length
  k = distance traveled by slow in cycle when they meet

When they meet:
  slow traveled: μ + k
  fast traveled: 2(μ + k)
  
  fast is k steps ahead in cycle:
    2(μ + k) = μ + k + nλ (for some n)
    μ + k = nλ
    μ = nλ - k
  
From meeting point, distance to entrance:
  λ - k (going forward in cycle)
  
From start, distance to entrance:
  μ = nλ - k
  
After μ steps from start:
  Reach entrance
  
After μ steps from meeting point:
  Travel λ - k, then (n-1)λ + λ - k = nλ - k
  Also reach entrance!
  
Both reach entrance simultaneously! ✓
```

**Alternative Approaches**:

1. **Hash Set** (O(n) space):
```
Use set to track seen numbers
  for num in nums:
    if num in seen:
      return num
    seen.add(num)

Simple but uses O(n) space ❌
```

2. **Marking Negative** (modifies array):
```
Mark visited indices as negative
  for num in nums:
    index = abs(num)
    if nums[index] < 0:
      return index  // Already visited
    nums[index] *= -1

Uses O(1) space but modifies array ❌
```

3. **Sorting** (modifies array):
```
Sort array, check adjacent elements
  sort(nums)
  for i in range(len(nums) - 1):
    if nums[i] == nums[i+1]:
      return nums[i]

O(n log n) time, modifies array ❌
```

**Floyd's Algorithm Wins**:
- O(n) time ✓
- O(1) space ✓
- Doesn't modify array ✓
- Satisfies all follow-up requirements! ✓

**Related Patterns**:
1. **Cycle Detection** — Fast and slow pointers
2. **Linked List Cycle** — Same algorithm
3. **Array as Graph** — Implicit graph traversal

---

## Algorithm & Approach

### Core Insight

**Why Floyd's Cycle Detection Works:**
```
Key observations:
  1. Array represents implicit linked list
  2. Duplicate value creates cycle entrance
  3. Phase 1: Detect cycle exists
  4. Phase 2: Find cycle entrance (duplicate)
  5. O(n) time, O(1) space, no modification
```

**The Optimal Strategy**:
```
Key steps:
  1. Initialize slow = nums[0], fast = nums[0]
  2. Phase 1: Move slow 1 step, fast 2 steps until they meet
  3. Phase 2: Start slow2 from 0, move slow and slow2 one step
  4. When slow == slow2, that's the duplicate
```

### Step-by-Step Algorithm

---

#### **Approach 1: Floyd's Cycle Detection - OPTIMAL (Follow-up)**

**Core Idea**:
- Treat array as linked list where value points to next index
- Find cycle using fast/slow pointers
- Find cycle entrance (duplicate) using two pointers
- O(n) time, O(1) space, no modification

**Algorithm**
```
findDuplicate(nums):
    // Phase 1: Find intersection point in cycle
    slow = nums[0]
    fast = nums[0]
    
    do:
        slow = nums[slow]        // Move 1 step
        fast = nums[nums[fast]]  // Move 2 steps
    while slow != fast
    
    // Phase 2: Find entrance to cycle (duplicate)
    slow2 = nums[0]
    
    while slow != slow2:
        slow = nums[slow]    // Move 1 step
        slow2 = nums[slow2]  // Move 1 step
    
    return slow  // or slow2, they're equal
```

**Code Implementation**
```java
class Solution {
    public int findDuplicate(int[] nums) {
        // Phase 1: Find intersection point in the cycle
        // Start both pointers at the first element
        int slow = nums[0];
        int fast = nums[0];
        
        // Move slow one step and fast two steps until they meet
        do {
            slow = nums[slow];           // Move 1 step
            fast = nums[nums[fast]];     // Move 2 steps
        } while (slow != fast);
        
        // Phase 2: Find the entrance to the cycle (duplicate number)
        // Reset one pointer to start
        slow = nums[0];
        
        // Move both pointers one step at a time
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        
        // They meet at the cycle entrance (duplicate)
        return slow;
    }
}
```

**Example Walkthrough**

Input: `nums = [1,3,4,2,2]`

```
Array visualization as linked list:
Index: 0  1  2  3  4
Value: 1  3  4  2  2

Links:
  0 → 1 (nums[0] = 1)
  1 → 3 (nums[1] = 3)
  3 → 2 (nums[3] = 2)
  2 → 4 (nums[2] = 4)
  4 → 2 (nums[4] = 2)  ← Creates cycle!

Path: 0 → 1 → 3 → 2 → 4 → 2 → 4 → 2 ...
                    ↑_________↓
```

**Phase 1: Detect Cycle**
```
Initialize:
  slow = nums[0] = 1
  fast = nums[0] = 1

Iteration 1:
  slow = nums[1] = 3
  fast = nums[nums[1]] = nums[3] = 2
  slow ≠ fast, continue

Iteration 2:
  slow = nums[3] = 2
  fast = nums[nums[2]] = nums[4] = 2
  slow == fast = 2, STOP!

Meeting point: index 2 (value 4)
```

**Phase 2: Find Entrance**
```
Initialize:
  slow = nums[0] = 1 (reset to start)
  fast = 2 (meeting point from phase 1)

Iteration 1:
  slow = nums[1] = 3
  fast = nums[2] = 4
  slow ≠ fast, continue

Iteration 2:
  slow = nums[3] = 2
  fast = nums[4] = 2
  slow == fast = 2, STOP!

Entrance: 2
Return: 2 ✓
```

**Complexity Analysis**
- **Time**: O(n) — Each pointer moves at most n steps
- **Space**: O(1) — Only two/three pointer variables

---

#### **Approach 2: Hash Set - SIMPLE (Not Follow-up)**

**Core Idea**:
- Track seen numbers in a hash set
- When we see a number again, it's the duplicate
- O(n) time, O(n) space

**Algorithm**
```
findDuplicate(nums):
    seen = new HashSet()
    
    for num in nums:
        if seen.contains(num):
            return num
        seen.add(num)
    
    return -1  // Should never reach
```

**Code Implementation**
```java
class Solution {
    public int findDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        
        for (int num : nums) {
            if (seen.contains(num)) {
                return num;  // Found duplicate
            }
            seen.add(num);
        }
        
        return -1;  // Should never reach (guaranteed duplicate exists)
    }
}
```

**Complexity Analysis**
- **Time**: O(n) — Single pass
- **Space**: O(n) — Hash set stores up to n elements

**Why Not Recommended**: Uses extra space, doesn't satisfy follow-up

---

#### **Approach 3: Marking Negative - CLEVER (Modifies Array)**

**Core Idea**:
- Use array itself as hash set
- Mark visited indices by negating values
- If value at index is already negative, found duplicate
- O(n) time, O(1) space, but modifies array

**Algorithm**
```
findDuplicate(nums):
    for num in nums:
        index = abs(num)
        
        if nums[index] < 0:
            return index  // Already visited
        
        nums[index] *= -1  // Mark as visited
    
    return -1
```

**Code Implementation**
```java
class Solution {
    public int findDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]);
            
            // If value at this index is already negative
            // it means we've seen this number before
            if (nums[index] < 0) {
                return index;
            }
            
            // Mark as visited by negating
            nums[index] = -nums[index];
        }
        
        return -1;  // Should never reach
    }
}
```

**Example Walkthrough**

Input: `nums = [1,3,4,2,2]`

```
Initial: [1, 3, 4, 2, 2]

i = 0, num = 1, index = 1
  nums[1] = 3 (positive)
  Mark: nums[1] = -3
  Array: [1, -3, 4, 2, 2]

i = 1, num = -3, index = 3
  nums[3] = 2 (positive)
  Mark: nums[3] = -2
  Array: [1, -3, 4, -2, 2]

i = 2, num = 4, index = 4
  nums[4] = 2 (positive)
  Mark: nums[4] = -2
  Array: [1, -3, 4, -2, -2]

i = 3, num = -2, index = 2
  nums[2] = 4 (positive)
  Mark: nums[2] = -4
  Array: [1, -3, -4, -2, -2]

i = 4, num = -2, index = 2
  nums[2] = -4 (NEGATIVE!) ← Already visited
  Return: 2 ✓
```

**Complexity Analysis**
- **Time**: O(n) — Single pass
- **Space**: O(1) — No extra space

**Why Not Recommended**: Modifies input array, violates follow-up

---

#### **Approach 4: Sorting - SIMPLE (Modifies Array)**

**Core Idea**:
- Sort array
- Check adjacent elements for duplicates
- O(n log n) time, modifies array

**Code Implementation**
```java
class Solution {
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return nums[i];
            }
        }
        
        return -1;  // Should never reach
    }
}
```

**Complexity Analysis**
- **Time**: O(n log n) — Sorting
- **Space**: O(1) or O(n) depending on sort implementation

**Why Not Recommended**: Slower, modifies array

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Modifies Array | Satisfies Follow-up |
|----------|------|-------|----------------|---------------------|
| **Floyd's Cycle Detection** | **O(n)** | **O(1)** | **No ✅** | **Yes ✅** |
| Hash Set | O(n) | O(n) | No | No (space) |
| Marking Negative | O(n) | O(1) | Yes ❌ | No (modifies) |
| Sorting | O(n log n) | O(1) | Yes ❌ | No (time & modifies) |
| Binary Search | O(n log n) | O(1) | No | No (time) |

**Winner**: **Floyd's Cycle Detection** — only approach satisfying all requirements!

### Why Floyd's Algorithm Works

```
Array as linked list property:
  - Values in [1, n], indices in [0, n]
  - Value can be used as next index
  - nums[i] always valid index
  - No index 0 in values → start always "outside" cycle
  
Duplicate creates cycle:
  - If X appears twice at indices i and j
  - Both nums[i] and nums[j] point to index X
  - Two edges to same node = cycle!
  - X is the cycle entrance
  
Floyd's detects and finds entrance:
  - Phase 1: Detect cycle exists
  - Phase 2: Find entrance (duplicate)
  - Guaranteed to work! ✓
```

### Why Two Phases Needed

```
Phase 1 alone not enough:

Meeting point ≠ entrance
  slow and fast meet somewhere in cycle
  But not necessarily at entrance
  
Example: nums = [2,5,9,6,9,3,8,9,7,1,4]
  They might meet at index 6
  But entrance might be at index 4
  
Phase 2 finds entrance:
  Mathematical property:
    Distance from start to entrance
    = Distance from meeting point to entrance (mod cycle length)
  
  Moving both one step from start and meeting point
  Guarantees they meet at entrance! ✓
```

### Why Start at nums[0]

```
Important: Don't start at index 0!
  Start at VALUE nums[0]
  
Reason:
  Index 0 is never in range [1, n]
  So it's never pointed to
  Index 0 is "outside" the cycle
  This ensures clean cycle detection
  
If we started at 0:
  Might think 0 is part of cycle ❌
  
Starting at nums[0]:
  We enter the "linked list" properly ✓
```

### Why This is Optimal

```
Follow-up requirements:
  ✓ O(n) time — two passes, still linear
  ✓ O(1) space — only 3 variables
  ✓ No modification — read-only access
  
No other approach satisfies all three!

Hash set: uses O(n) space ❌
Marking: modifies array ❌
Sorting: modifies array, O(n log n) time ❌

Floyd's is the ONLY solution! ✓
```

---

## Critical Edge Cases & Gotchas

### 1. **Minimum Size Array**
```java
Input: nums = [1,1]
Output: 1

Smallest possible case
n = 1, length = 2
Immediate cycle detection
```

### 2. **Duplicate Appears Twice**
```java
Input: nums = [1,3,4,2,2]
Output: 2

Standard case
Each duplicate appears exactly twice
```

### 3. **Duplicate Appears Many Times**
```java
Input: nums = [3,3,3,3,3]
Output: 3

Same number repeated multiple times
All point to same index
Strong cycle
```

### 4. **Duplicate at Start**
```java
Input: nums = [2,1,2,3]
Output: 2

Duplicate is smallest value
Multiple paths to index 2
```

### 5. **Duplicate at End**
```java
Input: nums = [1,2,3,4,4]
Output: 4

Duplicate is largest value
Still forms cycle
```

### 6. **Many Missing Numbers**
```java
Input: nums = [5,5,5,5,5]
Output: 5

Only one unique value
Numbers 1,2,3,4 all missing
```

### 7. **Long Cycle Path**
```java
Input: nums = [2,5,9,6,9,3,8,9,7,1,4]
Output: 9

Long path before cycle
Floyd's efficiently handles it
```

### 8. **Sequential Numbers with One Duplicate**
```java
Input: nums = [1,2,3,4,5,6,7,8,8]
Output: 8

Nearly sequential array
Duplicate near end
```

### 9. **Reverse Order with Duplicate**
```java
Input: nums = [5,4,3,2,1,1]
Output: 1

Descending order
Duplicate is smallest
```

### 10. **Maximum Size Array**
```java
Input: nums with 10,001 elements (n = 10,000)
Output: Varies

Maximum constraint
Floyd's handles efficiently in O(n) time
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Starting Pointers at Index 0**
```java
// WRONG - starting at wrong place
int slow = 0;  // WRONG! ❌
int fast = 0;
```

**Why wrong**: Should start at nums[0]!

**Dry run failure:**
```
nums = [1,3,4,2,2]

If slow = 0, fast = 0:
  slow = nums[0] = 1
  fast = nums[nums[0]] = nums[1] = 3
  
  Continue...
  
But we're thinking of 0 as part of the list structure!
Index 0 is outside the cycle by design.
```

**Fix**: Start at nums[0]
```java
int slow = nums[0];  ✓
int fast = nums[0];
```

### ❌ **MISTAKE 2: Using While Loop for Phase 1**
```java
// WRONG - might not execute if slow == fast initially
while (slow != fast) {  // WRONG if both start at nums[0]! ❌
    slow = nums[slow];
    fast = nums[nums[fast]];
}
```

**Why wrong**: If both start at nums[0], loop never executes!

**Fix**: Use do-while
```java
do {
    slow = nums[slow];
    fast = nums[nums[fast]];
} while (slow != fast);  ✓
```

### ❌ **MISTAKE 3: Not Resetting Pointer for Phase 2**
```java
// WRONG - not resetting
// After phase 1
while (slow != fast) {  // They're already equal! ❌
    slow = nums[slow];
    fast = nums[fast];
}
```

**Why wrong**: They already equal from phase 1!

**Dry run failure:**
```
After Phase 1:
  slow == fast (meeting point)
  
Without reset:
  Loop condition slow != fast is false
  Never enters loop! ❌
  Returns wrong value
```

**Fix**: Reset one pointer
```java
slow = nums[0];  // Reset to start ✓
while (slow != fast) {
    slow = nums[slow];
    fast = nums[fast];
}
```

### ❌ **MISTAKE 4: Moving Fast Pointer Incorrectly**
```java
// WRONG - not moving 2 steps
fast = nums[fast];  // Only 1 step! ❌
```

**Why wrong**: Fast should move 2 steps!

**Dry run failure:**
```
If both move 1 step:
  They move together
  Never meet (or meet immediately)
  Doesn't detect cycle properly ❌
```

**Fix**: Move fast 2 steps
```java
fast = nums[nums[fast]];  // 2 steps ✓
```

### ❌ **MISTAKE 5: Returning Wrong Value**
```java
// WRONG - returning index instead of value
public int findDuplicate(int[] nums) {
    // ... Floyd's algorithm
    return slow;  // What if slow is index? ❌
}
```

**Why wrong**: Need to be clear about what slow represents!

**Clarification:**
```
In our algorithm:
  slow stores VALUES, not indices
  slow = nums[0] (value at index 0)
  slow = nums[slow] (value at index slow)
  
So return slow is correct! ✓

But if you stored indices:
  slowIndex = 0
  slowIndex = nums[slowIndex]  // This is a value!
  
Need to return nums[slowIndex] ❌
```

**Our approach is correct**:
```java
return slow;  // slow is a value ✓
```

### ❌ **MISTAKE 6: Checking fast.next for null**
```java
// WRONG - treating like linked list pointers
while (fast != null && fast.next != null) {  // WRONG! ❌
    slow = nums[slow];
    fast = nums[nums[fast]];
}
```

**Why wrong**: This is an array, not linked list!

**Issue:**
```
Arrays don't have null
  fast is an integer (value in array)
  nums[fast] is also an integer
  
No null checks needed!
  All values in [1, n]
  All valid indices
  
Checking for null is wrong! ❌
```

**Fix**: No null checks
```java
do {
    slow = nums[slow];
    fast = nums[nums[fast]];
} while (slow != fast);  ✓
```

### ❌ **MISTAKE 7: Using Two Slow Pointers in Phase 2**
```java
// WRONG - creating new variables confusingly
public int findDuplicate(int[] nums) {
    int slow = nums[0];
    int fast = nums[0];
    
    // Phase 1
    do {
        slow = nums[slow];
        fast = nums[nums[fast]];
    } while (slow != fast);
    
    // Phase 2
    int slow2 = nums[0];  // Good
    while (slow != slow2) {
        slow = nums[slow];
        slow2 = nums[slow2];  // But don't move fast! ❌
    }
    
    return slow2;
}
```

**Why potentially confusing**: Not moving fast in phase 2!

**Clarification:**
```
In Phase 2:
  We need one pointer at meeting point (slow or fast)
  One pointer at start (new variable)
  
Common to reuse slow and fast:
  slow = nums[0] (reset slow to start)
  fast stays at meeting point
  
Or use new variable:
  slow2 = nums[0]
  slow stays at meeting point
  
Both work! Just be consistent.
```

**Clear approach**:
```java
// Phase 2: Reset one pointer
slow = nums[0];
// fast stays at meeting point
while (slow != fast) {
    slow = nums[slow];
    fast = nums[fast];  // Now move 1 step, not 2!
}
return slow;  ✓
```

### ❌ **MISTAKE 8: Moving Fast 2 Steps in Phase 2**
```java
// WRONG - moving 2 steps in phase 2
slow = nums[0];
while (slow != fast) {
    slow = nums[slow];
    fast = nums[nums[fast]];  // WRONG! Should move 1 step ❌
}
```

**Why wrong**: Phase 2 requires 1 step each!

**Dry run failure:**
```
In Phase 2, we need equal movement:
  Distance from start to entrance = d
  Distance from meeting to entrance = d (mod cycle)
  
Both move 1 step to meet at entrance
  
If fast moves 2 steps:
  They won't meet at entrance! ❌
```

**Fix**: Move 1 step each in phase 2
```java
slow = nums[slow];   // 1 step
fast = nums[fast];   // 1 step (not nums[nums[fast]]) ✓
```

### ❌ **MISTAKE 9: Trying to Find Index of Duplicate**
```java
// WRONG - problem asks for VALUE, not index
public int findDuplicate(int[] nums) {
    // ... Floyd's algorithm
    // slow and fast meet at the DUPLICATE VALUE
    
    // Trying to find index
    for (int i = 0; i < nums.length; i++) {  // UNNECESSARY! ❌
        if (nums[i] == slow) {
            return i;  // Returning index ❌
        }
    }
}
```

**Why wrong**: Problem wants duplicate value, not its index!

**Fix**: Return the value directly
```java
return slow;  // This IS the duplicate value ✓
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

**Phase 1: Detect Cycle**
```
Fast pointer moves 2 steps per iteration
Slow pointer moves 1 step per iteration

In worst case:
  - Fast might need to traverse entire cycle
  - Slow follows behind
  - They meet within n steps
  
Time: O(n) for phase 1
```

**Phase 2: Find Entrance**
```
Both pointers move 1 step per iteration

Distance from start to entrance ≤ n
Both reach entrance in ≤ n steps

Time: O(n) for phase 2
```

**Total Time: O(n) + O(n) = O(n)**

**Detailed Analysis:**
```
Phase 1:
  Let μ = distance to cycle entrance
  Let λ = cycle length
  
  Fast catches slow within λ steps after slow enters cycle
  Total: O(μ + λ) ≤ O(n)

Phase 2:
  Distance from start to entrance = μ
  Move μ steps to meet at entrance
  Total: O(μ) ≤ O(n)

Combined: O(n) ✓
```

### Space Complexity: **O(1)**

```
Variables used:
  - slow: O(1)
  - fast: O(1)
  - (optional) slow2: O(1)

No arrays, lists, or other data structures
No recursion (no stack space)

Total space: O(1) ✓
```

**Comparison with Other Approaches:**
```
Hash Set: O(n) space ❌
Marking: O(1) space but modifies array ❌
Sorting: O(1) or O(n) depending on implementation ❌
Floyd's: O(1) space, no modification ✓✓✓
```

### Optimal Complexity

```
Time: O(n)
  Must examine enough elements to find duplicate
  Can't do better than linear
  Optimal! ✓

Space: O(1)
  Constant extra space
  No modification
  Optimal! ✓

Floyd's Cycle Detection achieves optimal complexity
while satisfying all follow-up requirements! ✓
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `nums = [1,3,4,2,2]`
**Expected Output:** `2`

---

**Array as Linked List:**
```
Index: 0   1   2   3   4
Value: 1   3   4   2   2

Linked list representation:
  0 → 1 → 3 → 2 → 4 → 2 (cycle back to 2)
              ↑_______↓

Path:
  Start at index 0 (outside cycle)
  Go to index 1 (nums[0] = 1)
  Go to index 3 (nums[1] = 3)
  Go to index 2 (nums[3] = 2)  ← Cycle entrance
  Go to index 4 (nums[2] = 4)
  Go to index 2 (nums[4] = 2)  ← Back to entrance (cycle!)
```

---

**Phase 1: Detect Cycle**

```
Initialize:
  slow = nums[0] = 1
  fast = nums[0] = 1

Iteration 1:
  slow = nums[1] = 3
  fast = nums[nums[1]] = nums[3] = 2
  
  Position:
    slow at index 3 (value 3)
    fast at index 2 (value 2)
    slow ≠ fast, continue

Iteration 2:
  slow = nums[3] = 2
  fast = nums[nums[2]] = nums[4] = 2
  
  Position:
    slow at index 2 (value 2)
    fast at index 2 (value 2)
    slow == fast = 2, STOP!

Meeting Point: value 2 (at index 2)
```

**Visual of Phase 1:**
```
Path: 0 → 1 → 3 → 2 → 4 → 2 → 4 → 2 ...
         ↑_______↓

Slow (1 step/iteration):
  Start: 1
  Step 1: 1 → 3
  Step 2: 3 → 2 (stop)

Fast (2 steps/iteration):
  Start: 1
  Step 1: 1 → 3 → 2
  Step 2: 2 → 4 → 2 (stop)

Both at 2 (met inside cycle) ✓
```

---

**Phase 2: Find Cycle Entrance**

```
Initialize:
  slow = nums[0] = 1 (reset to start)
  fast = 2 (stays at meeting point)

Iteration 1:
  slow = nums[1] = 3
  fast = nums[2] = 4
  
  slow ≠ fast, continue

Iteration 2:
  slow = nums[3] = 2
  fast = nums[4] = 2
  
  slow == fast = 2, STOP!

Entrance: 2
Return: 2 ✓
```

**Visual of Phase 2:**
```
Slow from start:
  0 → 1 → 3 → 2 (stop)

Fast from meeting point (index 2):
  2 → 4 → 2 (stop)

Both paths lead to index 2 (value 2) ✓
This is the cycle entrance = duplicate! ✓
```

---

**Why They Meet at Entrance:**
```
Distance from start (index 0) to entrance (index 2):
  0 → 1 → 3 → 2
  2 steps (through indices 1 and 3)

Distance from meeting point (index 2) to entrance (index 2):
  In the cycle: 2 → 4 → 2
  2 steps

Both travel same distance → meet at entrance! ✓
```

---

### Example with Longer Cycle

**Input:** `nums = [2,5,9,6,9,3,8,9,7,1,4]`

```
Index: 0  1  2  3  4  5  6  7  8  9  10
Value: 2  5  9  6  9  3  8  9  7  1  4

Linked list:
  0 → 2 → 9 → 1 → 5 → 3 → 6 → 8 → 7 → 9 (cycle)
              ↑___________________________↓

Cycle:
  Entrance at index 9 (value 1)
  Cycle: 9 → 1 → 5 → 3 → 6 → 8 → 7 → 9
```

**Phase 1: Fast catches slow in cycle**
```
Multiple iterations...
Eventually meet somewhere in cycle
```

**Phase 2: Both converge to entrance**
```
One from start, one from meeting point
Meet at index 9
nums[9] = 1? No!
Wait, which value repeats?

Actually, value 9 appears 3 times (indices 2, 4, 7)
All point to index 9
So paths converge to index 9
The value that repeats is 9 ✓
```

---

### Edge Case: Minimum Array

**Input:** `nums = [1,1]`

```
Index: 0  1
Value: 1  1

Linked list:
  0 → 1 → 1 (cycle immediately)
      ↺

Phase 1:
  slow = nums[0] = 1
  fast = nums[0] = 1
  
  Iteration 1:
    slow = nums[1] = 1
    fast = nums[nums[1]] = nums[1] = 1
    Meet immediately!

Phase 2:
  slow = nums[0] = 1
  fast = 1 (already equal)
  
  Iteration 1:
    slow = nums[1] = 1
    fast = nums[1] = 1
    Still equal, return 1 ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Modifies | Complexity | Follows Constraints |
|----------|------|-------|----------|------------|---------------------|
| **Floyd's Cycle Detection** | **O(n)** | **O(1)** | **No** | **Medium** | **✅ Yes** |
| Hash Set | O(n) | O(n) | No | Simple | ❌ Space |
| Marking Negative | O(n) | O(1) | Yes | Medium | ❌ Modifies |
| Sorting | O(n log n) | O(1) | Yes | Simple | ❌ Time & Modifies |
| Binary Search | O(n log n) | O(1) | No | Complex | ❌ Time |

**Winner**: **Floyd's Cycle Detection** — only approach satisfying all follow-up requirements!

**When to Use Each:**
- **Interview with follow-up**: Floyd's (REQUIRED)
- **Quick solution allowed**: Hash Set (simplest)
- **Array can be modified**: Marking Negative (clever)
- **No time limit**: Sorting (easy to understand)

---

## Key Takeaways

1. **Array as linked list** — value points to index
2. **Duplicate creates cycle** — two paths to same index
3. **Floyd's two phases** — detect then find entrance
4. **Phase 1**: Fast (2 steps), slow (1 step) until meet
5. **Phase 2**: Both 1 step from start and meeting point
6. **Meeting point in phase 2 = duplicate** value
7. **Use do-while** for phase 1 (both start at same value)
8. **Reset one pointer** for phase 2
9. **O(n) time, O(1) space** — optimal!
10. **No modification** — read-only access

---

## Interview Tips

**What to say in an interview:**

> "To find the duplicate without modifying the array and using O(1) space, I'll use Floyd's Cycle Detection algorithm. The key insight is that we can treat the array as an implicit linked list where each value points to the next index. Since there's a duplicate value, multiple indices will point to the same index, creating a cycle in this implicit linked list. The algorithm has two phases: First, I'll use fast and slow pointers (fast moves 2 steps, slow moves 1 step) to detect that a cycle exists. They'll eventually meet inside the cycle. Second, I'll reset one pointer to the start and move both pointers one step at a time. The point where they meet again is the entrance to the cycle, which corresponds to the duplicate number. This works because the distance from the start to the cycle entrance equals the distance from the meeting point to the entrance modulo the cycle length. The solution runs in O(n) time with two passes through the array and uses O(1) space with just a few pointer variables."

**Key points to mention:**
1. **Array as linked list** — value → index mapping
2. **Duplicate creates cycle** — multiple paths to same index
3. **Two phases** — detect cycle, find entrance
4. **Phase 1**: Fast/slow pointers (2 steps vs 1 step)
5. **Phase 2**: Reset one pointer, move both 1 step
6. **Meeting point = entrance = duplicate**
7. **O(n) time** — two linear passes
8. **O(1) space** — only pointer variables
9. **No modification** — read-only access

**Common Follow-ups:**
- "Why does this work mathematically?" → Explain cycle properties and distance relationship
- "Can you use a simpler approach?" → Yes (hash set), but uses O(n) space
- "What if we can modify the array?" → Marking negative is clever O(1) space alternative
- "What about sorting?" → O(n log n) time and modifies array
- "Why start at nums[0]?" → Index 0 never in values [1,n], ensures we're outside cycle

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Find the Duplicate Number** | Medium | **Floyd's Cycle Detection** | **This problem** |
| Linked List Cycle | Easy | Floyd's Detection | Actual linked list structure |
| Linked List Cycle II | Medium | Floyd's Detection | Find cycle entrance in linked list |
| Missing Number | Easy | Math/XOR | Find missing instead of duplicate |
| Set Mismatch | Easy | Hash/Mark | Find both duplicate and missing |
| First Missing Positive | Hard | Array Marking | Find smallest missing positive |

**Pattern Progression**:
1. **Linked List Cycle** — Detect cycle in linked list
2. **Linked List Cycle II** — Find entrance in linked list
3. **Find Duplicate Number** (this) — Same algorithm on implicit linked list
4. **First Missing Positive** — Similar array-as-hash-set technique

---

## Final Pattern Label

✅ **Floyd's Cycle Detection on Implicit Linked List (Tortoise and Hare)**

**Remember:** This is a **cycle detection problem** disguised as an array problem. Treat array as **implicit linked list** where value points to index (`nums[i] → nums[nums[i]]`). **Duplicate creates cycle** because multiple indices point to same index (cycle entrance). Use **Floyd's two-phase algorithm**: Phase 1 uses fast/slow pointers (fast moves 2 steps, slow moves 1) to detect cycle — **use do-while** since both start at nums[0]. When they meet, Phase 2 begins: **reset one pointer to nums[0]**, move both **1 step** at a time. They meet at **cycle entrance = duplicate value**. Mathematical property: distance from start to entrance equals distance from meeting point to entrance (mod cycle length). Achieves **O(n) time** (two passes) and **O(1) space** (only pointer variables) **without modifying array**. Only approach satisfying all follow-up requirements! Critical: start at **nums[0]** not 0 (index 0 is outside cycle), use **do-while** for phase 1, **reset pointer** for phase 2, move both **1 step** in phase 2 (not 2). Return the value where pointers meet (duplicate)!
