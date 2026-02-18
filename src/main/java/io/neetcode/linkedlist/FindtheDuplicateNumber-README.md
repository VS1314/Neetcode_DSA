# Find the Duplicate Number

## Problem Statement

**Difficulty:** Medium

You are given an array of integers nums containing n + 1 integers. Each integer in nums is in the range [1, n] inclusive.

Every integer appears exactly once, except for one integer which appears two or more times. Return the integer that appears more than once.

**Follow-up:** Can you solve the problem without modifying the array nums and using O(1) extra space?

### Examples

**Example 1:**
```
Input: nums = [1,2,3,2,2]
Output: 2
```

**Example 2:**
```
Input: nums = [1,2,3,4,4]
Output: 4
```

### Constraints
- 1 <= n <= 10000
- nums.length == n + 1
- 1 <= nums[i] <= n

---

## Pattern Identification

**Primary Pattern:** Floyd's Cycle Detection (Tortoise and Hare)  
**Secondary Pattern:** Array as HashMap (In-place marking)

### Why this pattern?

- The problem has a constraint: O(1) space and cannot modify the array
- The array elements are in range [1, n] where array length is n+1
- This creates a "linked list" structure where each value points to an index
- Since there's a duplicate, there MUST be a cycle (two indices point to the same value)

### Pattern Recognition Clues

- Detecting duplicates with O(1) space
- Values represent valid indices
- Guaranteed to have exactly one duplicate
- Similar structure to "Linked List Cycle Detection"

---

## Approach & Strategy

### Key Observations

1. **Pigeonhole Principle:** Since we have n+1 numbers in range [1, n], at least one number must repeat
2. **Implicit Linked List:** We can treat the array as a "linked list" where `nums[i]` is a pointer to index `nums[i]`
3. **Cycle Formation:** The duplicate creates a cycle in this implicit linked list
4. **Safe Starting Point:** Index 0 can never be part of the cycle (since values are 1 to n)

### Visual Representation

```
Array: [1, 3, 4, 2, 2]
Index:  0  1  2  3  4

As Linked List:
0 -> 1 -> 3 -> 2 -> 4 -> 2 (cycle!)
          ^              |
          |______________|
```

---

## Solution Approaches

### Approach 1: Hash Set ❌ (Not Optimal)

**Time:** O(n) | **Space:** O(n)

```java
public int findDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int num : nums) {
        if (seen.contains(num)) return num;
        seen.add(num);
    }
    return -1;
}
```

**Why not optimal?** Violates the O(1) space constraint

---

### Approach 2: Sorting ❌ (Not Optimal)

**Time:** O(n log n) | **Space:** O(1) or O(n)

```java
public int findDuplicate(int[] nums) {
    Arrays.sort(nums);
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] == nums[i-1]) return nums[i];
    }
    return -1;
}
```

**Why not optimal?** Modifies the original array

---

### Approach 3: Array as HashMap ⚠️ (Good but modifies array)

**Time:** O(n) | **Space:** O(1)

```java
public int findDuplicate(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
        int index = Math.abs(nums[i]);
        
        // If already negative, we've seen this before
        if (nums[index] < 0) {
            return index;
        }
        
        // Mark as visited by negating
        nums[index] = -nums[index];
    }
    return -1;
}
```

**Why not perfect?** Modifies the original array (violates follow-up requirement)

---

### Approach 4: Floyd's Cycle Detection ✅ (OPTIMAL - Best for Interviews)

**Time:** O(n) | **Space:** O(1)

This is the optimal solution that meets all requirements!

---

### Approach 5: Binary Search on Value Range ✅ (ALTERNATIVE OPTIMAL)

**Time:** O(n log n) | **Space:** O(1)

Another elegant solution that doesn't modify the array!

```java
public int findDuplicate(int[] nums) {
    int left = 1, right = nums.length - 1;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        // Count how many numbers are <= mid
        int count = 0;
        for (int num : nums) {
            if (num <= mid) count++;
        }
        
        // If count > mid, duplicate is in [left, mid]
        if (count > mid) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    
    return left;
}
```

**How it works:**
- Binary search on the value range [1, n], not on array indices
- For each mid value, count how many numbers in array are ≤ mid
- If count > mid, by Pigeonhole Principle, duplicate must be in [1, mid]
- Otherwise, duplicate is in [mid+1, n]

**Example:** `nums = [1,3,4,2,2]`
- Range [1,4], mid=2, count=3 (values: 1,2,2) → 3 > 2, so duplicate in [1,2]
- Range [1,2], mid=1, count=1 (value: 1) → 1 ≤ 1, so duplicate in [2,2]
- Answer: 2

**Pros:** Elegant, doesn't modify array, O(1) space  
**Cons:** Slower than Floyd's (O(n log n) vs O(n))

---

### Approach 6: Bit Manipulation (XOR) ⚠️ (Only for specific cases)

**Time:** O(n) | **Space:** O(1)

**Note:** This only works if the duplicate appears exactly twice and all other numbers appear once.

```java
public int findDuplicate(int[] nums) {
    int xor = 0;
    
    // XOR all array elements
    for (int num : nums) {
        xor ^= num;
    }
    
    // XOR with [1, n]
    for (int i = 1; i < nums.length; i++) {
        xor ^= i;
    }
    
    return xor;
}
```

**Limitation:** Doesn't work if duplicate appears more than twice (as in Example 1: [1,2,3,2,2])

---

### Approach 7: Sum Formula ⚠️ (Only for specific cases)

**Time:** O(n) | **Space:** O(1)

**Note:** This only works if the duplicate appears exactly twice.

```java
public int findDuplicate(int[] nums) {
    int n = nums.length - 1;
    int expectedSum = n * (n + 1) / 2;
    int actualSum = 0;
    
    for (int num : nums) {
        actualSum += num;
    }
    
    return actualSum - expectedSum;
}
```

**Limitation:** Same as XOR approach - doesn't handle multiple occurrences correctly

---

## Approach Comparison Table

| Approach | Time | Space | Modifies Array? | Works for Multiple Duplicates? | Difficulty |
|----------|------|-------|-----------------|-------------------------------|------------|
| Hash Set | O(n) | O(n) | ❌ No | ✅ Yes | Easy |
| Sorting | O(n log n) | O(1)* | ✅ Yes | ✅ Yes | Easy |
| Array as HashMap | O(n) | O(1) | ✅ Yes | ✅ Yes | Medium |
| **Floyd's Cycle** | **O(n)** | **O(1)** | **❌ No** | **✅ Yes** | **Hard** |
| **Binary Search** | **O(n log n)** | **O(1)** | **❌ No** | **✅ Yes** | **Medium** |
| XOR | O(n) | O(1) | ❌ No | ❌ No (only 2 occurrences) | Easy |
| Sum Formula | O(n) | O(1) | ❌ No | ❌ No (only 2 occurrences) | Easy |

*Sorting space complexity depends on algorithm (QuickSort: O(1), MergeSort: O(n))

### Best Approach Decision Tree

```
Can you modify the array?
├─ YES → Use Array as HashMap (O(n) time, O(1) space, simpler)
└─ NO
   ├─ Need O(n) time? → Use Floyd's Cycle Detection (optimal but complex)
   └─ Okay with O(n log n)? → Use Binary Search (easier to understand)
```

---

## Algorithm Explanation

### Method 1: Floyd's Cycle Detection (Most Popular Interview Solution)

### Floyd's Cycle Detection - Two Phases

#### Phase 1: Detect Cycle (Find Intersection Point)

1. Use two pointers: `slow` and `fast`
2. `slow` moves one step: `slow = nums[slow]`
3. `fast` moves two steps: `fast = nums[nums[fast]]`
4. They will eventually meet inside the cycle

#### Phase 2: Find Cycle Entry Point (The Duplicate)

1. Reset one pointer to the start (index 0)
2. Move both pointers one step at a time
3. Where they meet is the entrance to the cycle = **the duplicate number**

### Why Does This Work?

**Mathematical Proof:**

Let's say:
- Distance from start to cycle entry: `F`
- Distance from cycle entry to intersection: `a`
- Cycle length: `C`

When slow and fast meet:
- Slow traveled: `F + a`
- Fast traveled: `F + a + nC` (n complete cycles)
- Since fast moves twice as fast: `2(F + a) = F + a + nC`
- Simplifying: `F + a = nC`
- Therefore: **F = nC - a**

This means the distance from start to cycle entry equals the distance from intersection to cycle entry!

---

### Method 2: Binary Search on Value Range (Easier Alternative)

#### Core Intuition

Instead of binary searching on **array indices**, we binary search on the **value range [1, n]**.

**Key Insight:** By the Pigeonhole Principle, if we count how many numbers are ≤ mid:
- If count > mid → There are more numbers than "slots", so duplicate must be in [1, mid]
- If count ≤ mid → Duplicate must be in [mid+1, n]

#### Algorithm Steps

1. Initialize search range: `left = 1, right = n`
2. While `left < right`:
   - Calculate `mid = left + (right - left) / 2`
   - Count how many array elements are ≤ mid
   - If count > mid: narrow to left half `[left, mid]`
   - Else: narrow to right half `[mid+1, right]`
3. Return `left` (or `right`, they're equal)

#### Detailed Example

**Input:** `nums = [1,3,4,2,2]`, n=4

```
Iteration 1:
  left=1, right=4, mid=2
  Count elements ≤ 2: {1, 2, 2} → count = 3
  Since 3 > 2 → duplicate in [1, 2]
  Update: right = 2

Iteration 2:
  left=1, right=2, mid=1
  Count elements ≤ 1: {1} → count = 1
  Since 1 ≤ 1 → duplicate in [2, 2]
  Update: left = 2

Iteration 3:
  left=2, right=2 → Exit loop
  Return 2 ✅
```

#### Why Does This Work?

**Pigeonhole Principle Application:**

For a range [1, mid], if all numbers appeared at most once:
- We could have at most `mid` numbers in this range
- If we count more than `mid` numbers ≤ mid, one must be duplicated

**Example Visualization:**

```
Array: [1, 3, 4, 2, 2]
Check mid = 2:

Slots for [1, 2]: [ ][ ]  (2 slots)
Values ≤ 2: [1][2][2]     (3 values) ← MORE than slots!

This proves duplicate exists in [1, 2]
```

#### Binary Search Dry Run Table

| Iteration | left | right | mid | count(≤mid) | count>mid? | Action |
|-----------|------|-------|-----|-------------|------------|--------|
| 1 | 1 | 4 | 2 | 3 | ✅ Yes | right=2 |
| 2 | 1 | 2 | 1 | 1 | ❌ No | left=2 |
| 3 | 2 | 2 | - | - | - | **Return 2** |

---

## Code Implementation

### Solution 1: Floyd's Cycle Detection (RECOMMENDED)

```java
class Solution {
    public int findDuplicate(int[] nums) {
        // Phase 1: Find intersection point in the cycle
        int slow = nums[0];
        int fast = nums[0];
        
        // Move until they meet (must use do-while since both start at same position)
        do {
            slow = nums[slow];           // Move 1 step
            fast = nums[nums[fast]];     // Move 2 steps
        } while (slow != fast);
        
        // Phase 2: Find the entrance to the cycle (duplicate number)
        slow = nums[0];  // Reset slow to start
        
        while (slow != fast) {
            slow = nums[slow];  // Move both 1 step at a time
            fast = nums[fast];
        }
        
        return slow;  // or fast, they're equal at this point
    }
}
```

**When to use:** Interview wants O(n) time, demonstrates advanced algorithm knowledge

---

### Solution 2: Binary Search on Value Range (EASIER TO EXPLAIN)

```java
class Solution {
    public int findDuplicate(int[] nums) {
        int left = 1, right = nums.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // Count how many numbers are <= mid
            int count = 0;
            for (int num : nums) {
                if (num <= mid) {
                    count++;
                }
            }
            
            // By Pigeonhole Principle:
            // If count > mid, duplicate is in [left, mid]
            if (count > mid) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
}
```

**When to use:** Easier to explain in interviews, still meets follow-up requirements

---

### Solution 3: Array as HashMap (If Modification Allowed)

```java
class Solution {
    public int findDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]);
            
            // If already negative, we've seen this before
            if (nums[index] < 0) {
                return index;
            }
            
            // Mark as visited by negating
            nums[index] = -nums[index];
        }
        return -1;
    }
}
```

**When to use:** Follow-up doesn't prohibit modification, simpler implementation

---

## Complexity Analysis

### Floyd's Cycle Detection

**Time Complexity: O(n)**
- Phase 1: At most O(n) to find intersection
- Phase 2: At most O(n) to find cycle entry
- **Total: O(n)**

**Space Complexity: O(1)**
- Only using two integer pointers

---

### Binary Search Approach

**Time Complexity: O(n log n)**
- Binary search runs log n iterations
- Each iteration counts all n elements
- **Total: O(n × log n)**

**Space Complexity: O(1)**
- Only using a few integer variables

---

### Which Approach to Choose in Interview?

| Criteria | Floyd's | Binary Search |
|----------|---------|---------------|
| Best Time Complexity | ✅ O(n) | O(n log n) |
| Easier to Explain | | ✅ More intuitive |
| Demonstrates Skills | ✅ Advanced | Standard BS |
| Less Error-Prone | | ✅ Simpler logic |
| Most Popular | ✅ Classic | Alternative |

**Recommendation:** 
- **Start with Binary Search** if you're not confident with Floyd's
- **Use Floyd's** if you want to impress with optimal O(n) solution
- **Mention both** to show you know multiple approaches

---

## Dry Run (Step-by-Step)

**Input:** `nums = [1, 3, 4, 2, 2]`

```
Index:  0  1  2  3  4
Value: [1, 3, 4, 2, 2]

As Linked List:
0 -> 1 -> 3 -> 2 -> 4 -> 2 (cycle at index 2)
```

### Phase 1: Find Intersection

| Step | slow | fast | slow→value | fast→value |
|------|------|------|------------|------------|
| Init | 0    | 0    | nums[0]=1  | nums[0]=1  |
| 1    | 1    | 3    | nums[1]=3  | nums[3]=2  |
| 2    | 3    | 4    | nums[3]=2  | nums[2]=4  |
| 3    | 2    | 4    | nums[2]=4  | nums[4]=2  |
| 4    | 4    | 4    | **MEET!**  | **MEET!**  |

### Phase 2: Find Entry Point

Reset slow to 0:

| Step | slow | fast | 
|------|------|------|
| Init | 0    | 4    |
| 1    | 1    | 2    |
| 2    | 3    | 4    |
| 3    | 2    | 2    | 

**Answer: 2** ✅

---

## Edge Cases

1. **Minimum size:** `[1, 1]` → Answer: 1
2. **Duplicate at beginning:** `[2, 2, 2, 2]` → Answer: 2
3. **Duplicate at end:** `[1, 2, 3, 4, 4]` → Answer: 4
4. **Multiple occurrences:** `[1, 2, 3, 2, 2]` → Answer: 2
5. **Large array:** Works efficiently for n up to 10,000

---

## Common Mistakes

### ❌ Mistake 1: Using while instead of do-while in Phase 1

```java
// WRONG - exits immediately!
int slow = nums[0], fast = nums[0];
while (slow != fast) {  // They're already equal!
    slow = nums[slow];
    fast = nums[nums[fast]];
}
```

**Fix:** Use `do-while` loop

### ❌ Mistake 2: Moving fast pointer incorrectly

```java
// WRONG
fast = nums[fast];
fast = nums[fast];  // Two separate steps can cause issues
```

**Fix:** `fast = nums[nums[fast]]` in one step

### ❌ Mistake 3: Not resetting slow in Phase 2

```java
// WRONG - must reset one pointer to start
while (slow != fast) {
    slow = nums[slow];
    fast = nums[fast];
}
```

**Fix:** Reset `slow = nums[0]` before Phase 2

### ❌ Mistake 4: Using O(n) extra space

```java
// Works but violates follow-up constraint
Set<Integer> seen = new HashSet<>();  // O(n) space
```

**Fix:** Use Floyd's algorithm for O(1) space

---

## Why This Strategy?

### Advantages of Floyd's Cycle Detection

1. ✅ **Meets O(1) space requirement**
2. ✅ **Doesn't modify the array**
3. ✅ **O(n) time complexity**
4. ✅ **Guaranteed to work** (mathematical proof)
5. ✅ **Elegant and efficient**

### Key Insight

The problem is **disguised** as an array problem, but it's actually a **linked list cycle detection** problem!

### When to Use This Pattern

- Array elements represent indices
- Finding duplicates with space constraints
- Cycle detection in implicit data structures
- Problems with "exactly one duplicate" constraint

---

## Interview Tips

### What to Say

1. "I notice the array values can be treated as pointers to indices"
2. "Since there's a duplicate, this creates a cycle in an implicit linked list"
3. "I'll use Floyd's Cycle Detection algorithm to find where the cycle begins"
4. "This gives us O(n) time and O(1) space without modifying the array"

### Expected Follow-up Questions

**Q:** "Can you prove why this works mathematically?"  
**A:** [Explain the distance proof: F = nC - a]

**Q:** "What if we can modify the array?"  
**A:** "We could use the marking technique for a simpler solution"

**Q:** "What if there are multiple duplicates?"  
**A:** "This algorithm finds where the cycle begins, which is one of the duplicates"

**Q:** "What's the space complexity of sorting?"  
**A:** "O(1) for in-place sorts like heapsort, but O(n) for mergesort, and it modifies the array"

---

## Related Problems

- **Linked List Cycle** - Same algorithm
- **Linked List Cycle II** - Find cycle entry point
- **Happy Number** - Cycle detection in number sequences
- **Missing Number** - Similar array manipulation

---

## Summary

- **Pattern:** Floyd's Cycle Detection (Tortoise and Hare)
- **Time:** O(n)
- **Space:** O(1)
- **Key Insight:** Array values as pointers create an implicit linked list with a cycle
- **Two Phases:** Find intersection → Find cycle entry
- **Why Optimal:** No extra space, no array modification, linear time

