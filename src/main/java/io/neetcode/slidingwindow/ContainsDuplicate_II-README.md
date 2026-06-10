# Contains Duplicate II

## Problem Description

**Difficulty**: Easy

You are given an integer array `nums` and an integer `k`, return `true` if there are two **distinct indices** `i` and `j` in the array such that `nums[i] == nums[j]` and `abs(i - j) <= k`, otherwise return `false`.

## Examples

### Example 1:
```
Input: nums = [1,2,3,1], k = 3
Output: true
Explanation: 
nums[0] = 1 and nums[3] = 1
abs(0 - 3) = 3 <= 3 ✓
```

### Example 2:
```
Input: nums = [2,1,2], k = 1
Output: false
Explanation:
nums[0] = 2 and nums[2] = 2
abs(0 - 2) = 2 > 1 ✗
(The two 2's are too far apart)
```

### Example 3:
```
Input: nums = [1,0,1,1], k = 1
Output: true
Explanation:
nums[2] = 1 and nums[3] = 1
abs(2 - 3) = 1 <= 1 ✓
```

## Constraints
- 1 <= nums.length <= 100,000
- -10^9 <= nums[i] <= 10^9
- 0 <= k <= 100,000

**Recommended Complexity**: O(n) time, O(k) space

---

## Pattern Recognition

**Primary Pattern**: **Sliding Window + HashSet (Fixed Window Size k)**

**Why This Pattern?**
- Need to check for duplicates within a distance constraint
- Distance constraint = sliding window of size k
- Only care about existence (not frequency) = HashSet
- Window slides as we iterate through array

**Key Insight**: Maintain a Window of Last k Elements
```
Problem: Find if duplicate exists within distance k

Reframe: At position i, check if nums[i] exists in previous k positions

Window approach:
  Maintain a HashSet of elements in current window
  Window size = k (contains elements from [i-k, i-1])
  
  For each position i:
    1. Check if nums[i] already in window → duplicate found!
    2. Add nums[i] to window
    3. If window size > k, remove oldest element (nums[i-k])

Example:
  nums = [1, 2, 3, 1, 2, 3], k = 2
  
  i=0: window={}, add 1 → window={1}
  i=1: window={1}, add 2 → window={1,2}
  i=2: window={1,2}, add 3 → window={1,2,3} (size > 2, remove nums[0]=1)
       Final window={2,3}
  i=3: window={2,3}, check 1 → not in window, add 1 → window={2,3,1}
       (size > 2, remove nums[1]=2) → window={3,1}
  i=4: window={3,1}, check 2 → not in window, add 2 → window={3,1,2}
       (size > 2, remove nums[2]=3) → window={1,2}
  i=5: window={1,2}, check 3 → not in window, add 3 → window={1,2,3}
  
  No duplicate within distance 2 → return false
```

**Why HashSet Over HashMap?**
```
HashSet approach:
  - Only stores values in current window
  - Check existence: O(1)
  - Space: O(k)
  - Simpler logic

HashMap approach (alternative):
  - Stores value → last seen index
  - Check: value exists AND (i - lastIndex) <= k
  - Space: O(n) in worst case (all unique values)
  - More memory but doesn't need window management
  
HashSet is cleaner for this problem!
```

**Critical Detail**: Window Size is k, Not k+1
```
Window contains elements from indices [i-k, i-1]
  - That's k positions before current position i
  - Distance between i and (i-k) = k
  
Example: i=5, k=2
  Window contains indices [3, 4]
  Distance from 5 to 3 = 2 (exactly k)
  
Window management:
  When window.size() > k:
    Remove element at index (i - k)
```

**Related Patterns**:
1. **Sliding Window** — Core technique
2. **HashSet** — Fast duplicate detection
3. **Contains Duplicate** — Without distance constraint
4. **Fixed Window** — Window size is constant (k)

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Check all pairs (i, j) with i < j
  → For each i, check all j where j - i <= k
  → O(n × k) time in worst case
  → Too slow when k is large!

Sliding Window + HashSet:
  → Maintain window of last k elements
  → Check if current element in window: O(1)
  → O(n) time total
  → Optimal! ✓
```

**The Sliding Window Strategy**:
```
Window represents: elements we've seen in last k positions

At each position i:
  1. Check if nums[i] exists in window
     → If yes: found duplicate within distance k!
     → Return true
  
  2. Add nums[i] to window
  
  3. If window size > k:
     → Remove oldest element (at index i-k)
     → Keeps window size ≤ k

Example trace:
  nums = [1, 2, 3, 1], k = 3
  
  i=0: window={} → check 1 (not found) → add 1 → window={1}
  i=1: window={1} → check 2 (not found) → add 2 → window={1,2}
  i=2: window={1,2} → check 3 (not found) → add 3 → window={1,2,3}
  i=3: window={1,2,3} → check 1 (FOUND!) → return true ✓
```

### Step-by-Step Algorithm

---

#### **Approach 1: Sliding Window + HashSet (OPTIMAL)**

**Core Idea**:
- Maintain HashSet of elements in current window
- Window size ≤ k
- Check if current element exists before adding

**Algorithm**
```
containsNearbyDuplicate(nums, k):
    window = new HashSet()
    
    for i = 0 to n-1:
        // Check if nums[i] exists in window
        if window.contains(nums[i]):
            return true
        
        // Add current element to window
        window.add(nums[i])
        
        // Maintain window size ≤ k
        if window.size() > k:
            window.remove(nums[i - k])
    
    return false
```

**Code Implementation**
```java
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> window = new HashSet<>();
        
        for (int i = 0; i < nums.length; i++) {
            // Check if current element already in window
            if (window.contains(nums[i])) {
                return true;  // Found duplicate within distance k
            }
            
            // Add current element to window
            window.add(nums[i]);
            
            // Maintain window size ≤ k
            if (window.size() > k) {
                window.remove(nums[i - k]);
            }
        }
        
        return false;  // No duplicate found
    }
}
```

**Example Walkthrough**

Input: `nums = [1,2,3,1]`, `k = 3`

| i | nums[i] | Window Before | Contains? | Action | Window After |
|---|---------|---------------|-----------|--------|--------------|
| 0 | 1 | {} | No | Add 1 | {1} |
| 1 | 2 | {1} | No | Add 2 | {1,2} |
| 2 | 3 | {1,2} | No | Add 3 | {1,2,3} |
| 3 | 1 | {1,2,3} | **Yes** | **Return true** | — |

**Output:** `true`

**Complexity Analysis**
- **Time Complexity**: O(n) — Single pass, O(1) per element
- **Space Complexity**: O(min(n, k)) — Window size at most k (or n if k > n)

---

#### **Approach 2: HashMap (Index Tracking)**

**Core Idea**: Store last seen index for each value, check distance when seen again.

**Code Implementation**
```java
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> lastIndex = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            if (lastIndex.containsKey(nums[i])) {
                // Check if distance is within k
                if (i - lastIndex.get(nums[i]) <= k) {
                    return true;
                }
            }
            // Update last seen index
            lastIndex.put(nums[i], i);
        }
        
        return false;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) — Single pass
- **Space Complexity**: O(n) — Can store all unique elements
- **Why Not Optimal**: Uses more space than needed

---

#### **Approach 3: Brute Force (NOT OPTIMAL)**

**Core Idea**: Check all pairs within distance k.

**Code Implementation**
```java
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length && j <= i + k; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n × k) — Nested loops
- **Space Complexity**: O(1)
- **Why Not Optimal**: Too slow when k is large

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | HashMap | **HashSet Window** |
|-------------|-------------|---------|-------------------|
| Time complexity | O(n×k) ❌ | O(n) ✓ | **O(n) ✅** |
| Space complexity | O(1) ✓ | O(n) ❌ | **O(k) ✅** |
| Code simplicity | Simple | Medium | **Clean ✅** |
| Optimal | ❌ | Partial | **✅** |

**Winner**: **Sliding Window + HashSet** — optimal time and minimal space!

### Why Sliding Window?

```
Problem constraint: abs(i - j) <= k

This defines a WINDOW of size k:
  - At position i, only care about elements in [i-k, i-1]
  - Elements outside this range are irrelevant
  - Perfect use case for sliding window!

Window benefits:
  - Maintains only relevant elements
  - Space: O(k) instead of O(n)
  - Automatically removes old elements
  - Clean, intuitive logic
```

### Why HashSet Over Array?

```
Array approach:
  - Store last k elements in array
  - Check if current element in array: O(k)
  - Total time: O(n × k) ✗

HashSet approach:
  - Store last k elements in HashSet
  - Check if current element in set: O(1)
  - Total time: O(n) ✓
  
HashSet wins with O(1) lookup!
```

---

## Critical Edge Cases & Gotchas

### 1. **k = 0**
```java
Input: nums = [1,2,1], k = 0
Output: false
Explanation: abs(i - j) <= 0 means i == j (same index), but need distinct indices.
```

### 2. **All Unique Elements**
```java
Input: nums = [1,2,3,4,5], k = 2
Output: false
Explanation: No duplicates at all.
```

### 3. **Duplicate at Exact Distance k**
```java
Input: nums = [1,2,3,1], k = 3
Output: true
Explanation: Distance = 3, exactly k. 3 <= 3 is true.
```

### 4. **Duplicate Beyond Distance k**
```java
Input: nums = [1,2,3,1], k = 2
Output: false
Explanation: Distance = 3 > 2.
```

### 5. **Adjacent Duplicates**
```java
Input: nums = [1,1], k = 1
Output: true
Explanation: Distance = 1 <= 1.
```

### 6. **All Same Elements**
```java
Input: nums = [1,1,1,1], k = 1
Output: true
Explanation: First two 1's are adjacent.
```

### 7. **k Larger Than Array**
```java
Input: nums = [1,2,1], k = 100
Output: true
Explanation: k > n, so entire array is within window.
```

### 8. **Single Element**
```java
Input: nums = [1], k = 1
Output: false
Explanation: Need at least two distinct indices.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Checking After Adding to Window**
```java
// WRONG - adds before checking
window.add(nums[i]);  // WRONG! Add first
if (window.contains(nums[i])) {
    return true;  // Will always be true!
}
```

**Why wrong**: After adding, element will always be in window!

**Dry run failure:**
```
nums = [1, 2, 3], k = 2
i=0: add 1 to window → window={1}
     check if 1 in window → YES (WRONG! Always true)
     Returns true immediately (WRONG!)
```

**Fix**: Check before adding
```java
if (window.contains(nums[i])) {
    return true;
}
window.add(nums[i]);
```

### ❌ **MISTAKE 2: Wrong Window Size Check**
```java
// WRONG - uses >= instead of >
if (window.size() >= k) {  // WRONG!
    window.remove(nums[i - k]);
}
```

**Why wrong**: Window should be at most k, not k-1!

**Dry run failure for nums=[1,2,3,1], k=3:**
```
i=0: add 1 → window={1}, size=1
     size >= 3? No
i=1: add 2 → window={1,2}, size=2
     size >= 3? No
i=2: add 3 → window={1,2,3}, size=3
     size >= 3? YES (WRONG! Should be size > 3)
     Remove nums[2-3]= nums[-1] (INDEX OUT OF BOUNDS!)
```

**Fix**: Use > not >=
```java
if (window.size() > k) { ... }
```

### ❌ **MISTAKE 3: Wrong Index for Removal**
```java
// WRONG - removes wrong element
if (window.size() > k) {
    window.remove(nums[i - k - 1]);  // WRONG! Off by one
}
```

**Why wrong**: Should remove element at index (i - k), not (i - k - 1)!

**Dry run failure:**
```
Window should contain elements from [i-k, i-1]
When window size > k, oldest element is at index (i - k)

Example: i=4, k=2
  Window should have [2, 3] (indices)
  Remove element at index 4-2=2 (correct)
  Wrong code: 4-2-1=1 (removes wrong element!)
```

**Fix**: Remove at correct index
```java
window.remove(nums[i - k]);
```

### ❌ **MISTAKE 4: Not Handling k = 0**
```java
// WRONG - doesn't handle k=0
public boolean containsNearbyDuplicate(int[] nums, int k) {
    Set<Integer> window = new HashSet<>();
    // ... rest of code
}
```

**Why wrong**: When k=0, distance must be 0 (same index), but need distinct indices!

**Fix**: Early return for k=0
```java
if (k == 0) return false;
```

### ❌ **MISTAKE 5: Using Array Instead of HashSet**
```java
// WRONG - uses array for window (slow lookups)
int[] window = new int[k];
// Need to search array for duplicate: O(k) per check
for (int j = 0; j < windowSize; j++) {
    if (window[j] == nums[i]) {
        return true;
    }
}
```

**Why wrong**: Array lookup is O(k), making total time O(n×k)!

**Fix**: Use HashSet for O(1) lookup
```java
Set<Integer> window = new HashSet<>();
```

### ❌ **MISTAKE 6: Removing Wrong Element from HashSet**
```java
// WRONG - removes current element instead of oldest
if (window.size() > k) {
    window.remove(nums[i]);  // WRONG! Just added this
}
```

**Why wrong**: Removes the element we just added, not the oldest!

**Dry run failure:**
```
nums = [1,2,3,4], k=2
i=2: add 3 → window={1,2,3}, size=3 > 2
     Wrong: remove nums[2]=3 → window={1,2}
     Lost the element we just added!
```

**Fix**: Remove oldest element
```java
window.remove(nums[i - k]);
```

### ❌ **MISTAKE 7: Not Considering Negative Numbers**
```java
// This is actually fine - HashSet handles negative numbers correctly
// But be aware that nums[i] can be negative!
```

**Why this matters**: Some solutions might incorrectly use array indexing with nums[i] as index, which fails for negative numbers.

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Loop through array | O(n) | Visit each element once |
| HashSet contains | O(1) | Average case constant time |
| HashSet add | O(1) | Average case constant time |
| HashSet remove | O(1) | Average case constant time |
| **Total** | **O(n)** | Linear time |

### Space Complexity: **O(min(n, k))**

| Component | Space | Reason |
|-----------|-------|--------|
| HashSet window | O(min(n, k)) | At most k elements (or n if array smaller) |
| Other variables | O(1) | Few integers |
| **Total** | **O(min(n, k))** | Typically O(k) |

**Why O(n) Time is Optimal:**
- Must examine each element at least once
- Can't determine duplicates without looking
- O(n) is optimal

---

## Visualization

### Complete Example Walkthrough

**Input:** `nums = [1, 2, 3, 1, 2, 3]`, `k = 2`

---

**Step 0: Initialize**
```
nums = [1, 2, 3, 1, 2, 3]
        ↑
        i=0

window = {}
k = 2 (window can hold at most 2 elements)
```

---

**Step 1: i=0, nums[0]=1**
```
Check: Is 1 in window? No
Add: 1 to window
Window: {1}
Size: 1 <= 2, no removal needed
```

---

**Step 2: i=1, nums[1]=2**
```
Check: Is 2 in window? No
Add: 2 to window
Window: {1, 2}
Size: 2 <= 2, no removal needed
```

---

**Step 3: i=2, nums[2]=3**
```
Check: Is 3 in window? No
Add: 3 to window
Window: {1, 2, 3}
Size: 3 > 2, remove oldest!
  Remove nums[i-k] = nums[2-2] = nums[0] = 1
Window: {2, 3}
```

---

**Step 4: i=3, nums[3]=1**
```
Check: Is 1 in window? No (we removed it!)
Add: 1 to window
Window: {2, 3, 1}
Size: 3 > 2, remove oldest!
  Remove nums[i-k] = nums[3-2] = nums[1] = 2
Window: {3, 1}
```

---

**Step 5: i=4, nums[4]=2**
```
Check: Is 2 in window? No (we removed it earlier!)
Add: 2 to window
Window: {3, 1, 2}
Size: 3 > 2, remove oldest!
  Remove nums[i-k] = nums[4-2] = nums[2] = 3
Window: {1, 2}
```

---

**Step 6: i=5, nums[5]=3**
```
Check: Is 3 in window? No
Add: 3 to window
Window: {1, 2, 3}
Size: 3 > 2, remove oldest!
  Remove nums[i-k] = nums[5-2] = nums[3] = 1
Window: {2, 3}

Loop ends
```

**Result:** `false` (no duplicate found within distance 2)

### Why This Works

```
Window concept:
       [1, 2, 3, 1, 2, 3]
        ↑     ↑
        |--k--|

At i=3, window contains indices [1, 2]
  Elements: [2, 3]
  Distance from 3 to 1 = 2 (exactly k)
  
The 1 at index 0 is outside window (distance 3 > k)
So we correctly don't detect it as duplicate!

Visual:
i=3:  [1, 2, 3, 1, 2, 3]
       ✗  ✓  ✓  ↑
      out |--k--| current
      
Window only tracks elements within distance k.
```

---

## Comparison of Approaches

| Approach | Time | Space | Optimal | Notes |
|----------|------|-------|---------|-------|
| Brute Force | O(n×k) | O(1) | ❌ | Check all pairs |
| HashMap (index) | O(n) | O(n) | Partial | Stores all unique values |
| **HashSet (window)** | **O(n)** | **O(k)** | **✅** | **Optimal space** |

**Recommendation**: Always use **Sliding Window + HashSet** — optimal time and minimal space!

---

## Key Takeaways

1. **Sliding window** — distance constraint defines window size k
2. **HashSet for window** — O(1) lookup, cleaner than HashMap
3. **Check before adding** — critical order of operations
4. **Window size > k** — then remove oldest element
5. **Remove at index i-k** — oldest element in window
6. **Space O(k)** — only stores relevant elements
7. **Handle k=0** — special case, always return false

---

## Interview Tips

**What to say in an interview:**

> "This is a sliding window problem with a distance constraint. The key insight is that I only need to check if the current element exists in the previous k elements. I'll maintain a sliding window using a HashSet. For each element, I first check if it's already in the window — if yes, I've found a duplicate within distance k. Then I add the current element to the window. If the window size exceeds k, I remove the oldest element (at index i-k) to maintain the window size. This gives O(n) time with O(k) space, which is optimal."

**Key points to mention:**
1. **Sliding window** — distance constraint = window of size k
2. **HashSet choice** — O(1) lookup for duplicates
3. **Window maintenance** — add current, remove oldest if size > k
4. **Check before add** — order matters!
5. **Complexity** — O(n) time, O(k) space

**If asked about alternatives:**
> "I could use a HashMap to store each value's last seen index and check if the distance is within k when I see it again. That's also O(n) time but uses O(n) space in the worst case. The sliding window with HashSet is more space-efficient at O(k)."

**Common Follow-ups:**
- "What if we need to count duplicates?" → Use HashMap with frequency count
- "What if k is very large?" → Window approach still optimal, O(min(n,k)) space
- "How would you handle multiple duplicates?" → Same approach, returns on first found

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| Contains Duplicate | Easy | HashSet | No distance constraint |
| **Contains Duplicate II** | Easy | **Sliding Window** | **This problem** ← **Distance ≤ k** |
| Contains Duplicate III | Hard | Sliding Window + TreeSet | Value difference constraint |
| Longest Substring Without Repeating Characters | Medium | Sliding Window | Variable window, no duplicates |
| Max Consecutive Ones III | Medium | Sliding Window | At most k zeros |

**Pattern Connection**:
- **Sliding Window** — Fixed or variable size
- **HashSet** — Fast duplicate detection
- **Distance Constraints** — Define window boundaries

---

## Final Pattern Label

✅ **Sliding Window + HashSet (Fixed Window Size k)**

**Remember:** Maintain a HashSet window of at most k elements. For each element: (1) Check if it's in the window (duplicate found!), (2) Add it to window, (3) If window size > k, remove the oldest element at index (i-k). Check BEFORE adding is critical! This gives O(n) time with O(k) space, which is optimal!
