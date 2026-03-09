# Contains Duplicate
## Problem Description
**Difficulty**: Easy

Given an integer array `nums`, return `true` if any value appears at least twice in the array, and return `false` if every element is distinct.
## Examples
### Example 1:
```
Input: nums = [1,2,3,1]
Output: true
Explanation: The element 1 occurs at indices 0 and 3.
```
### Example 2:
```
Input: nums = [1,2,3,4]
Output: false
Explanation: All elements are distinct.
```
### Example 3:
```
Input: nums = [1,1,1,3,3,4,3,2,4,2]
Output: true
```
## Constraints
- 1 <= nums.length <= 10^5
- -10^9 <= nums[i] <= 10^9
---
## Pattern Recognition
**Primary Pattern**: **HashSet - Membership Check (Seen Pattern)**
**Why This Pattern?**
- Need to check if element was seen before
- Only care about existence, not frequency
- O(1) lookup time required
- "Contains duplicate" keyword signals hashing
**Key Insight**: While scanning the array, check if current element exists in a set. If yes, duplicate found. If no, add to set and continue.
**Related Patterns**:
1. **HashMap** - If we need to count frequencies
2. **Sorting** - Alternative O(n log n) approach
3. **Two Pointers** - Only works on sorted arrays
---
## Algorithm & Approach
### Core Insight
The problem asks for existence check, not counting. We need to remember what we've seen so far and check if current element was already seen.
**Why HashSet works:**
- O(1) average time for `contains()` and `add()`
- Space efficient - only stores unique elements
- Perfect for "seen before" pattern
### Step-by-Step Algorithm
#### **Approach 1: Brute Force - Nested Loops**
```
1. For each element at index i:
   2. Check all elements before index i
   3. If any match, return true
4. Return false if no duplicates found
```
**Code Implementation**
```java
public boolean containsDuplicate(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[i] == nums[j]) {
                return true;
            }
        }
    }
    return false;
}
```
**Complexity Analysis**
- **Time Complexity**: O(n²) - For each element, check all previous
- **Space Complexity**: O(1) - No extra space
**Why Not Optimal?** Too slow for large arrays (up to 10^5 elements).
#### **Approach 2: Sorting**
```
1. Sort the array
2. Check adjacent elements
3. If any adjacent pair is equal, return true
4. Return false
```
**Code Implementation**
```java
public boolean containsDuplicate(int[] nums) {
    Arrays.sort(nums);
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] == nums[i - 1]) {
            return true;
        }
    }
    return false;
}
```
**Complexity Analysis**
- **Time Complexity**: O(n log n) - Sorting dominates
- **Space Complexity**: O(1) or O(n) - Depends on sorting algorithm
**Why Not Optimal?** O(n log n) is slower than O(n), and modifies array.
#### **Approach 3: HashSet (OPTIMAL)**
```
1. Create empty HashSet
2. For each element in array:
   - If element exists in set → return true
   - Otherwise, add element to set
3. Return false (no duplicates found)
```
**Example Walkthrough**
Input: nums = [1,2,3,1]
| i | nums[i] | seen.contains? | Action | seen set |
|---|---------|----------------|--------|----------|
| 0 | 1 | No | Add 1 | {1} |
| 1 | 2 | No | Add 2 | {1,2} |
| 2 | 3 | No | Add 3 | {1,2,3} |
| 3 | 1 | **Yes** | **Return true** | {1,2,3} |
Output: true
**Code Implementation**
```java
public boolean containsDuplicate(int[] nums) {
    HashSet<Integer> seen = new HashSet<>();
    for (int num : nums) {
        if (seen.contains(num)) {
            return true;
        }
        seen.add(num);
    }
    return false;
}
```
**Alternative - More Concise**
```java
public boolean containsDuplicate(int[] nums) {
    HashSet<Integer> seen = new HashSet<>();
    for (int num : nums) {
        if (!seen.add(num)) {  // add() returns false if element already exists
            return true;
        }
    }
    return false;
}
```
**Complexity Analysis**
- **Time Complexity**: O(n) - Single pass through array
- **Space Complexity**: O(n) - HashSet can store up to n elements (worst case: all unique)
---
## Why This Strategy?
### Problem Requirements Analysis
| Requirement | Brute Force | Sorting | HashSet |
|-------------|-------------|---------|---------|
| Time complexity | O(n²) ❌ | O(n log n) | **O(n)** ✅ |
| Space complexity | O(1) ✅ | O(1)-O(n) | O(n) |
| Modifies input | No ✅ | Yes ❌ | No ✅ |
| Early exit | Yes ✅ | Yes ✅ | Yes ✅ |
| Best for | Small arrays | Memory constrained | **General case** ✅ |
**Winner**: HashSet approach - optimal time complexity, doesn't modify input!
### Why HashSet Over HashMap?
- **HashMap**: Stores key-value pairs → `HashMap<Integer, Integer>`
- **HashSet**: Only stores keys → `HashSet<Integer>`
- Since we only care about existence (not frequency), HashSet is more appropriate
- HashSet is slightly more memory efficient
### Why O(n) Time is Optimal?
- Must examine each element at least once
- Can't determine if array has duplicates without checking all elements (worst case)
- O(n) is the best we can achieve
---
## Critical Edge Cases & Gotchas
### 1. **All Elements Same**
```java
Input: nums = [1,1,1,1]
Output: true
Explanation: First duplicate found at index 1
```
### 2. **All Elements Unique**
```java
Input: nums = [1,2,3,4,5]
Output: false
Explanation: Must check entire array, set will have 5 elements
```
### 3. **Single Element**
```java
Input: nums = [1]
Output: false
Explanation: Cannot have duplicate with only one element
```
### 4. **Two Elements - Duplicate**
```java
Input: nums = [1,1]
Output: true
```
### 5. **Two Elements - Unique**
```java
Input: nums = [1,2]
Output: false
```
### 6. **Duplicate at End**
```java
Input: nums = [1,2,3,4,1]
Output: true
Explanation: Must scan entire array before finding duplicate
```
---
## Major Areas Where We Might Go Wrong
### ❌ **MISTAKE 1: Using Adjacent Check Without Sorting**
```java
// WRONG - Only works on sorted arrays!
for (int i = 1; i < nums.length; i++) {
    if (nums[i] == nums[i - 1]) {
        return true;
    }
}
```
**Why wrong**: This only checks adjacent elements. Duplicates might not be adjacent.
**Example where it fails**: [3, 1, 3] - Adjacent check won't find the duplicate
**Fix**: Either sort first, or use HashSet
### ❌ **MISTAKE 2: Using HashMap Instead of HashSet**
```java
// WRONG - Unnecessarily complex!
HashMap<Integer, Integer> map = new HashMap<>();
for (int num : nums) {
    if (map.containsKey(num)) {
        return true;
    }
    map.put(num, 1);  // Don't need to store value
}
```
**Why wrong**: We don't need key-value pairs, just keys.
**Fix**: Use HashSet
```java
// CORRECT
HashSet<Integer> seen = new HashSet<>();
for (int num : nums) {
    if (seen.contains(num)) {
        return true;
    }
    seen.add(num);
}
```
### ❌ **MISTAKE 3: Not Returning Early**
```java
// WRONG - Checks entire array even after finding duplicate
HashSet<Integer> seen = new HashSet<>();
boolean hasDup = false;
for (int num : nums) {
    if (seen.contains(num)) {
        hasDup = true;  // Should return immediately!
    }
    seen.add(num);
}
return hasDup;
```
**Why wrong**: Wastes time and space checking remaining elements.
**Fix**: Return immediately when duplicate found
### ❌ **MISTAKE 4: Checking After Adding**
```java
// WRONG - Logic error!
HashSet<Integer> seen = new HashSet<>();
for (int num : nums) {
    seen.add(num);
    if (seen.contains(num)) {  // Always true after adding!
        return true;
    }
}
```
**Why wrong**: Element is always in set after adding it.
**Fix**: Check BEFORE adding
```java
// CORRECT
if (seen.contains(num)) {
    return true;
}
seen.add(num);
```
---
## Complexity Analysis
### Time Complexity: **O(n)**
| Operation | Time | Reason |
|-----------|------|--------|
| Loop through array | O(n) | Visit each element once |
| HashSet.contains() | O(1) avg | Hash lookup |
| HashSet.add() | O(1) avg | Hash insertion |
| Total | O(n) | Linear time |
**Best Case**: O(1) - Duplicate found at index 1  
**Average Case**: O(n) - Duplicate somewhere in middle  
**Worst Case**: O(n) - No duplicates, check entire array
### Space Complexity: **O(n)**
| Component | Space |
|-----------|-------|
| HashSet | O(n) worst case |
| Other variables | O(1) |
| Total | O(n) |
**Best Case**: O(1) - Duplicate found immediately  
**Worst Case**: O(n) - All elements unique, set stores all n elements
---
## Visualization
### Example Walkthrough
```
Input: nums = [1, 2, 3, 1]
Step-by-step:
i=0: num=1
  seen.contains(1)? No
  seen.add(1)
  seen = {1}
i=1: num=2
  seen.contains(2)? No
  seen.add(2)
  seen = {1, 2}
i=2: num=3
  seen.contains(3)? No
  seen.add(3)
  seen = {1, 2, 3}
i=3: num=1
  seen.contains(1)? YES! ✓
  return true
Output: true
```
---
## Comparison of Approaches
| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Brute Force | O(n²) | O(1) | No extra space | Too slow |
| Sorting | O(n log n) | O(1)-O(n) | Works without extra structure | Slower, modifies array |
| **HashSet** | **O(n)** | **O(n)** | **Optimal time, clean code** ✅ | Uses extra space |
**Best Choice**: HashSet ✓
---
## Key Takeaways
1. **Pattern Recognition**: "Contains/duplicate/exists" → Think HashSet
2. **HashSet vs HashMap**: Use HashSet when only checking existence
3. **Check Before Adding**: Verify element not in set before insertion
4. **Early Return**: Return true immediately when duplicate found
5. **Optimal Solution**: O(n) time, O(n) space
6. **Space-Time Tradeoff**: HashSet uses more space but much faster than O(n²)
---
## Interview Tips
**What to say in an interview:**
> "This is a classic duplicate detection problem. The key insight is that we need to remember what elements we've seen as we iterate through the array. I'll use a HashSet because it provides O(1) average-time lookup and insertion. For each element, I check if it's already in the set - if yes, we found a duplicate and return true. If not, I add it to the set. This gives us O(n) time complexity with O(n) space, which is optimal for this problem."
**Key points to mention:**
1. **Pattern**: HashSet for "seen before" pattern
2. **Why HashSet over HashMap**: Only need to track existence, not frequency
3. **Complexity**: O(n) time, O(n) space - optimal
4. **Early exit**: Return immediately when duplicate found
**If asked about space optimization:**
> "If space is a concern, we could sort the array first (O(n log n) time) and then check adjacent elements (O(n) time) for O(n log n) total with O(1) extra space. However, this is slower and modifies the input array, so the HashSet approach is generally preferred unless there are strict space constraints."
---
## Related Problems
| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Contains Duplicate** | Easy | **HashSet** | **Basic duplicate detection** ← This problem |
| Contains Duplicate II | Easy | HashMap | Check duplicates within k distance |
| Contains Duplicate III | Hard | TreeSet/Buckets | Value difference constraint |
| Single Number | Easy | XOR/HashSet | Find unique element |
| Find All Duplicates | Medium | Index marking | All duplicates, in-place |
**Pattern Family**: Hashing - Duplicate Detection
---
## Final Pattern Label
✅ **HashSet – Membership Check (Seen Before Pattern)**
**Remember:** When you see "contains", "duplicate", "seen before", "exists" → immediately think HashSet for O(1) lookup!