# Longest Common Prefix

## Problem Description

**Difficulty**: Easy

Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string `""`.

## Examples

### Example 1:
```
Input: strs = ["flower","flow","flight"]
Output: "fl"
Explanation: "fl" is the longest common prefix among all strings.
```

### Example 2:
```
Input: strs = ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
```

### Example 3:
```
Input: strs = ["interspecies","interstellar","interstate"]
Output: "inters"
```

## Constraints
- 1 <= strs.length <= 200
- 0 <= strs[i].length <= 200
- strs[i] consists of only lowercase English letters
---
## Pattern Recognition
**Primary Pattern**: **Vertical/Horizontal Scanning (Character Comparison)**
**Why This Pattern?**
- Need to find common prefix → compare character by character
- All strings must match at each position
- Stop at first mismatch or end of any string
- No hashing or sorting needed
**Key Insight**: The longest common prefix cannot be longer than the shortest string. Compare characters position by position across all strings.
**Related Patterns**:
1. **String Comparison** - Character-by-character matching
2. **Early Termination** - Stop when mismatch found
3. **Divide and Conquer** - Alternative approach
---
## Algorithm & Approach
### Core Insight
**Critical Observation:** Common prefix means ALL strings must have the same character at each position.
```
["flower", "flow", "flight"]
  f         f       f        ✓ Match at index 0
  l         l       l        ✓ Match at index 1
  o         o       i        ✗ Mismatch at index 2
Answer: "fl"
```
**Why it works:**
- Check character by character from left to right
- If any string differs at position i, prefix ends at i
- If any string ends, prefix cannot be longer
### What Approaches Exist?
1. **Horizontal Scanning** - Compare first string with all others
2. **Vertical Scanning** - Compare character at each position across all strings
3. **Divide and Conquer** - Recursively split and merge
4. **Binary Search** - Search for prefix length
### Step-by-Step Algorithm
#### **Approach 1: Horizontal Scanning**
```
1. Start with first string as prefix
2. For each remaining string:
   a. While string doesn't start with prefix:
      - Remove last character from prefix
   b. If prefix becomes empty, return ""
3. Return prefix
```
**Code Implementation**
```java
public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    String prefix = strs[0];
    for (int i = 1; i < strs.length; i++) {
        while (strs[i].indexOf(prefix) != 0) {
            prefix = prefix.substring(0, prefix.length() - 1);
            if (prefix.isEmpty()) return "";
        }
    }
    return prefix;
}
```
**Complexity Analysis**
- **Time Complexity**: O(S) where S = sum of all characters in all strings
- **Space Complexity**: O(1) - only using prefix variable
**Example:**
```
["flower", "flow", "flight"]
Start: prefix = "flower"
i=1: "flow".indexOf("flower") = -1 (not found)
     prefix = "flowe"
     "flow".indexOf("flowe") = -1
     prefix = "flow"
     "flow".indexOf("flow") = 0 ✓
i=2: "flight".indexOf("flow") = -1
     prefix = "flo"
     "flight".indexOf("flo") = -1
     prefix = "fl"
     "flight".indexOf("fl") = 0 ✓
Return: "fl"
```
#### **Approach 2: Vertical Scanning (OPTIMAL)**
```
1. For each character position i (0 to min length):
   a. Get character from first string at position i
   b. Check if all other strings have same character at i
   c. If mismatch or end of any string → return prefix so far
2. Return full shortest string (all match)
```
**Code Implementation**
```java
public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    // Check each character position
    for (int i = 0; i < strs[0].length(); i++) {
        char c = strs[0].charAt(i);
        // Compare with all other strings
        for (int j = 1; j < strs.length; j++) {
            // If reached end of string j OR character mismatch
            if (i == strs[j].length() || strs[j].charAt(i) != c) {
                return strs[0].substring(0, i);
            }
        }
    }
    // All strings matched completely with first string
    return strs[0];
}
```
**Complexity Analysis**
- **Time Complexity**: O(S) where S = sum of all characters
  - Best case: O(n * m) where n = number of strings, m = min string length
  - Worst case: O(S) when all strings are identical
- **Space Complexity**: O(1) - constant extra space
**Why This is Better:**
- Early termination on first mismatch
- No string manipulation (substring)
- Cleaner logic
### Example Walkthrough
**Input:** strs = ["flower", "flow", "flight"]
**Vertical Scanning Process:**
| i | strs[0][i] | Check All | Result |
|---|------------|-----------|--------|
| 0 | 'f' | flower[0]='f', flow[0]='f', flight[0]='f' | ✓ All match |
| 1 | 'l' | flower[1]='l', flow[1]='l', flight[1]='l' | ✓ All match |
| 2 | 'o' | flower[2]='o', flow[2]='o', flight[2]='i' | ✗ Mismatch! |
**Return:** `strs[0].substring(0, 2)` = `"fl"`
**Visual:**
```
Position:  0  1  2  3  4  5
flower:    f  l  o  w  e  r
flow:      f  l  o  w
flight:    f  l  i  g  h  t
           ✓  ✓  ✗
Common prefix: "fl"
```
#### **Approach 3: Divide and Conquer**
```
1. Divide array into two halves
2. Recursively find LCP of each half
3. Merge by finding LCP of two results
```
**Code Implementation**
```java
public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) return "";
    return longestCommonPrefix(strs, 0, strs.length - 1);
}
private String longestCommonPrefix(String[] strs, int left, int right) {
    if (left == right) {
        return strs[left];
    }
    int mid = (left + right) / 2;
    String lcpLeft = longestCommonPrefix(strs, left, mid);
    String lcpRight = longestCommonPrefix(strs, mid + 1, right);
    return commonPrefix(lcpLeft, lcpRight);
}
private String commonPrefix(String left, String right) {
    int min = Math.min(left.length(), right.length());
    for (int i = 0; i < min; i++) {
        if (left.charAt(i) != right.charAt(i)) {
            return left.substring(0, i);
        }
    }
    return left.substring(0, min);
}
```
**Complexity Analysis**
- **Time Complexity**: O(S) where S = sum of all characters
- **Space Complexity**: O(m * log n) for recursion stack
---
## Why This Strategy?
### Problem Requirements Analysis
| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Horizontal Scanning | O(S) | O(1) | Simple | String manipulation overhead |
| **Vertical Scanning** | **O(S)** | **O(1)** | **Early termination, clean** ✅ | None |
| Divide & Conquer | O(S) | O(m log n) | Parallelizable | Extra space, complex |
| Binary Search | O(S log m) | O(1) | Works for sorted | Slower for this problem |
**Winner**: Vertical Scanning - optimal time, constant space, early termination!
### Why Vertical Over Horizontal?
**Vertical Scanning:**
- ✅ Early termination on first mismatch
- ✅ No string manipulation (substring repeatedly)
- ✅ Clearer logic - check position by position
**Horizontal Scanning:**
- ❌ Multiple substring operations
- ❌ indexOf() called repeatedly
- ✅ Easy to understand
---
## Critical Edge Cases & Gotchas
### 1. **Empty Array**
```java
Input: strs = []
Output: ""
Explanation: No strings to compare
```
### 2. **Single String**
```java
Input: strs = ["alone"]
Output: "alone"
Explanation: Only string is the common prefix
```
### 3. **Empty String in Array**
```java
Input: strs = ["flower", "", "flow"]
Output: ""
Explanation: Empty string has no characters to match
```
### 4. **No Common Prefix**
```java
Input: strs = ["dog", "racecar", "car"]
Output: ""
Explanation: First characters don't match
```
### 5. **One String is Prefix of Others**
```java
Input: strs = ["flow", "flower", "flowing"]
Output: "flow"
Explanation: Shortest string is the prefix
```
### 6. **All Strings Identical**
```java
Input: strs = ["test", "test", "test"]
Output: "test"
Explanation: Entire string is common
```
---
## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Checking for Null or Empty Array**
```java
// WRONG - NullPointerException!
public String longestCommonPrefix(String[] strs) {
    for (int i = 0; i < strs[0].length(); i++) {
        // What if strs is null or empty?
    }
}
```

**Why wrong**: Accessing `strs[0]` when array is null or empty crashes.

**Fix**: Check at start
```java
// CORRECT
if (strs == null || strs.length == 0) return "";
```

### ❌ **MISTAKE 2: Not Checking String Length Before charAt()**
```java
// WRONG - StringIndexOutOfBoundsException!
for (int i = 0; i < strs[0].length(); i++) {
    for (int j = 1; j < strs.length; j++) {
        if (strs[j].charAt(i) != c) {
            // What if strs[j] is shorter than strs[0]?
        }
    }
}
```

**Why wrong**: Shorter strings will throw exception.

**Fix**: Check length first
```java
// CORRECT
if (i == strs[j].length() || strs[j].charAt(i) != c) {
    return strs[0].substring(0, i);
}
```

### ❌ **MISTAKE 3: Wrong Substring Bounds**
```java
// WRONG - Off by one!
return strs[0].substring(0, i + 1); // Should be i, not i+1
```

**Why wrong**: When we find mismatch at position i, prefix ends BEFORE i.

**Fix**: Use `substring(0, i)` - excludes position i

### ❌ **MISTAKE 4: Forgetting Empty String Case**
```java
// WRONG - Returns wrong result!
if (strs[i].indexOf(prefix) != 0) {
    prefix = prefix.substring(0, prefix.length() - 1);
    // What if prefix becomes empty? Need to check!
}
```

**Why wrong**: Empty prefix means no common prefix exists.

**Fix**: Check if prefix is empty
```java
// CORRECT
if (prefix.isEmpty()) return "";
```

### ❌ **MISTAKE 5: Comparing Wrong Strings**
```java
// WRONG - Only compares adjacent strings!
for (int i = 1; i < strs.length; i++) {
    if (strs[i].charAt(j) != strs[i-1].charAt(j)) {
        // This misses comparing with first string!
    }
}
```

**Why wrong**: Must compare ALL strings with a reference (usually first).

**Fix**: Compare with `strs[0]` or previous result

---

## Complexity Analysis

### Time Complexity: **O(S)**

| Operation | Time | Explanation |
|-----------|------|-------------|
| Outer loop | O(m) | m = length of shortest string |
| Inner loop | O(n) | n = number of strings |
| Total | O(m * n) = O(S) | S = sum of all characters |

**Best Case**: O(n * 1) = O(n) - Mismatch at first character  
**Worst Case**: O(S) - All strings identical, check every character

### Space Complexity: **O(1)**

| Component | Space |
|-----------|-------|
| Loop variables | O(1) |
| Substring result | O(m) - part of output, not counted |
| Total | O(1) |

---

## Visualization

### Example Walkthrough
```
Input: ["flower", "flow", "flight"]

Visual character grid:
Position: 0   1   2   3   4   5
---------------------------------
flower:   f   l   o   w   e   r
flow:     f   l   o   w   -   -
flight:   f   l   i   g   h   t
---------------------------------
Match:    ✓   ✓   ✗

Step-by-step:

i=0: Check column 0
  flower[0] = 'f'
  flow[0] = 'f' ✓
  flight[0] = 'f' ✓
  All match!

i=1: Check column 1
  flower[1] = 'l'
  flow[1] = 'l' ✓
  flight[1] = 'l' ✓
  All match!

i=2: Check column 2
  flower[2] = 'o'
  flow[2] = 'o' ✓
  flight[2] = 'i' ✗ MISMATCH!
  
Return: strs[0].substring(0, 2) = "fl"
```

---

## Comparison of Approaches

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Horizontal | O(S) | O(1) | Simple | String operations overhead |
| **Vertical** | **O(S)** | **O(1)** | **Early exit, clean** ✅ | None |
| Divide & Conquer | O(S) | O(m log n) | Can parallelize | Complex, extra space |
| Binary Search | O(S log m) | O(1) | Interesting approach | Slower |

**Best Choice**: Vertical Scanning ✓

---

## Key Takeaways

1. **Pattern Recognition**: "Common prefix" → vertical character comparison
2. **Early Termination**: Stop at first mismatch or shortest string end
3. **Character-by-Character**: Compare position by position across all strings
4. **Edge Cases**: Empty array, empty strings, single string
5. **Optimal**: O(S) time, O(1) space
6. **No Need for**: Sorting, hashing, or complex data structures

---

## Interview Tips

**What to say in an interview:**

> "I need to find the longest common prefix across all strings. The key insight is that the prefix cannot be longer than the shortest string. I'll use vertical scanning - for each character position, I check if all strings have the same character. The first mismatch or end of any string determines where the prefix ends. This is O(S) time where S is the sum of all characters, with O(1) space, and it terminates early on the first mismatch."

**Key points to mention:**
1. **Pattern**: Vertical character comparison
2. **Why vertical**: Early termination, no string manipulation
3. **Edge cases**: Empty array, empty strings, no common prefix
4. **Complexity**: O(S) time, O(1) space - optimal
5. **Alternative**: Horizontal scanning (simpler but slower)

**If asked about optimization:**
> "This is already optimal at O(S) since we must examine characters to find the prefix. We could use divide and conquer for parallelization in distributed systems, but for a single machine, vertical scanning is best due to early termination and cache locality."

**If asked about edge cases:**
> "I handle empty arrays and check string lengths before accessing characters. If any string is shorter or has a mismatch at position i, I return the substring up to (but not including) position i."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Longest Common Prefix** | Easy | **Vertical Scanning** | **Character-by-character comparison** ← This problem |
| Longest Common Subsequence | Medium | DP | Subsequence, not continuous |
| Group Anagrams | Medium | HashMap | Grouping, not prefix |
| Implement Trie | Medium | Trie | Prefix tree data structure |
| Word Search II | Hard | Trie | Board search with prefix |

**Pattern Family**: String Comparison / Prefix Matching

---

## Final Pattern Label

✅ **Vertical Scanning – Position-by-Position String Comparison**

**Remember:** When you see "common prefix" or "matching characters from start" → think vertical scanning with early termination!