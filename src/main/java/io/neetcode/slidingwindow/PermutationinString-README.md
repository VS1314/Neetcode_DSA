# Permutation in String

## Problem Description

**Difficulty**: Medium

You are given two strings `s1` and `s2`.

Return `true` if `s2` contains a **permutation** of `s1`, or `false` otherwise. That means if a permutation of `s1` exists as a **substring** of `s2`, then return `true`.

Both strings only contain lowercase letters.

## Examples

### Example 1:
```
Input: s1 = "abc", s2 = "lecabee"
Output: true
Explanation: The substring "cab" is a permutation of "abc" and is present in "lecabee".
```

### Example 2:
```
Input: s1 = "abc", s2 = "lecaabee"
Output: false
Explanation: No permutation of "abc" exists as a substring in "lecaabee".
```

### Example 3:
```
Input: s1 = "ab", s2 = "eidbaooo"
Output: true
Explanation: s2 contains "ba" which is a permutation of "ab".
```

### Example 4:
```
Input: s1 = "ab", s2 = "eidboaoo"
Output: false
Explanation: No permutation of "ab" exists in "eidboaoo".
```

## Constraints
- 1 <= s1.length, s2.length <= 10,000
- s1 and s2 consist of lowercase English letters

**Recommended Complexity**: O(n) time, O(1) space
- n = max(s1.length, s2.length)

---

## Pattern Recognition

**Primary Pattern**: **Fixed-Size Sliding Window + Frequency Matching**

**Why This Pattern?**
- Need to find substring of s2 (contiguous)
- Substring must be a permutation of s1 (same characters, same frequencies)
- "Permutation" means order doesn't matter, only frequency counts
- Fixed window size = length of s1

**Key Insight**: Permutations Have Identical Frequency Counts
```
Problem: Check if s2 contains any permutation of s1

Observation:
  Two strings are permutations if and only if:
    They have the same character frequency counts
  
Example: s1 = "abc"
  Permutations: "abc", "acb", "bac", "bca", "cab", "cba"
  
  All have same frequency: {a:1, b:1, c:1}
  
For s2 = "lecabee":
  Window "lec": {l:1, e:1, c:1} ≠ {a:1, b:1, c:1} ✗
  Window "eca": {e:1, c:1, a:1} ≠ {a:1, b:1, c:1} ✗ (missing b)
  Window "cab": {c:1, a:1, b:1} = {a:1, b:1, c:1} ✓ MATCH!
  
Return true
```

**Why Fixed-Size Sliding Window?**
```
Brute force: Check all substrings
  for each substring of s2:
    if length == s1.length:
      compare frequencies
  → O(n²) with frequency comparison
  → Too slow!

Fixed Sliding Window:
  Window size = s1.length (fixed!)
  Slide window across s2 one character at a time
  Update frequencies: remove left, add right
  Compare frequencies at each position
  → O(n) with efficient frequency updates!
```

**The Window Strategy**:
```
1. Create frequency count for s1
2. Create frequency count for first window in s2
3. Compare: if match, return true
4. Slide window:
   - Remove leftmost character from count
   - Add new rightmost character to count
   - Compare: if match, return true
5. If no match found, return false

Example: s1 = "ab", s2 = "eidbaooo"
  s1_freq = {a:1, b:1}
  
  Window "ei": {e:1, i:1} ≠ s1_freq
  Window "id": {i:1, d:1} ≠ s1_freq
  Window "db": {d:1, b:1} ≠ s1_freq
  Window "ba": {b:1, a:1} = s1_freq ✓ FOUND!
  
Return true
```

**Critical Detail**: Efficient Frequency Comparison
```
Two approaches to compare frequencies:

1. Compare entire arrays (26 elements)
   - O(26) = O(1) per comparison
   - Simple but repeated work

2. Track "matches" count
   - Count how many character frequencies match
   - Update matches when frequencies change
   - O(1) comparison: just check if matches == 26
   - More efficient!

Most solutions use approach 2 (matches count)
```

**Related Patterns**:
1. **Fixed Sliding Window** — Window size = s1.length
2. **Frequency Matching** — Compare character counts
3. **Anagram Detection** — Permutation = anagram
4. **Array Comparison** — Efficient frequency matching

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Try all substrings of length s1.length
  for i = 0 to s2.length - s1.length:
    substring = s2[i...i+s1.length]
    if isPermutation(substring, s1):
      return true
  
  Checking permutation: O(26) frequency comparison
  Total: O(n * 26) = O(n) but with high constant
  
  Worse: If we sort to check permutation: O(n * m log m)
  where m = s1.length
  → Too slow!

Fixed Sliding Window:
  Build frequency for s1: O(s1.length)
  Build frequency for first window: O(s1.length)
  Slide window: O(s2.length) with O(1) updates
  → O(s1.length + s2.length) = O(n) optimal!
```

**The Sliding Window Strategy**:
```
Key observations:
  1. Window size is fixed (s1.length)
  2. When sliding: only 1 character leaves, 1 enters
  3. Can update frequencies incrementally
  4. Compare frequencies in O(1) with matches count
  
Algorithm:
  1. Build s1 frequency array
  2. Build initial window frequency array
  3. Check if frequencies match → return true
  4. Slide window from left to right:
     - Decrement count of outgoing character (left)
     - Increment count of incoming character (right)
     - Update matches count
     - If all 26 frequencies match → return true
  5. Return false if no match found
```

### Step-by-Step Algorithm

---

#### **Approach 1: Fixed Sliding Window + Matches Count (OPTIMAL)**

**Core Idea**:
- Track frequency arrays for s1 and current window
- Track number of character frequencies that match
- Slide window, update matches count incrementally
- Return true when all 26 frequencies match

**Algorithm**
```
checkInclusion(s1, s2):
    if s1.length > s2.length:
        return false  // Can't contain permutation
    
    s1Freq = array[26]  // Frequency of s1
    s2Freq = array[26]  // Frequency of current window
    matches = 0         // Count of matching frequencies
    
    // Build frequency for s1 and first window
    for i = 0 to s1.length - 1:
        s1Freq[s1[i] - 'a']++
        s2Freq[s2[i] - 'a']++
    
    // Count initial matches
    for i = 0 to 25:
        if s1Freq[i] == s2Freq[i]:
            matches++
    
    // Check if first window matches
    if matches == 26:
        return true
    
    // Slide window across s2
    for i = s1.length to s2.length - 1:
        // Add incoming character (right)
        rightChar = s2[i] - 'a'
        s2Freq[rightChar]++
        if s2Freq[rightChar] == s1Freq[rightChar]:
            matches++
        else if s2Freq[rightChar] == s1Freq[rightChar] + 1:
            matches--
        
        // Remove outgoing character (left)
        leftChar = s2[i - s1.length] - 'a'
        s2Freq[leftChar]--
        if s2Freq[leftChar] == s1Freq[leftChar]:
            matches++
        else if s2Freq[leftChar] == s1Freq[leftChar] - 1:
            matches--
        
        // Check if all frequencies match
        if matches == 26:
            return true
    
    return false
```

**Code Implementation**
```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        
        int[] s1Freq = new int[26];
        int[] s2Freq = new int[26];
        
        // Build frequency arrays for s1 and first window of s2
        for (int i = 0; i < s1.length(); i++) {
            s1Freq[s1.charAt(i) - 'a']++;
            s2Freq[s2.charAt(i) - 'a']++;
        }
        
        // Count how many character frequencies match
        int matches = 0;
        for (int i = 0; i < 26; i++) {
            if (s1Freq[i] == s2Freq[i]) {
                matches++;
            }
        }
        
        // Check if first window is a match
        if (matches == 26) {
            return true;
        }
        
        // Slide the window across s2
        for (int i = s1.length(); i < s2.length(); i++) {
            // Add incoming character (right side of window)
            int rightIndex = s2.charAt(i) - 'a';
            s2Freq[rightIndex]++;
            
            if (s2Freq[rightIndex] == s1Freq[rightIndex]) {
                matches++;
            } else if (s2Freq[rightIndex] == s1Freq[rightIndex] + 1) {
                matches--;
            }
            
            // Remove outgoing character (left side of window)
            int leftIndex = s2.charAt(i - s1.length()) - 'a';
            s2Freq[leftIndex]--;
            
            if (s2Freq[leftIndex] == s1Freq[leftIndex]) {
                matches++;
            } else if (s2Freq[leftIndex] == s1Freq[leftIndex] - 1) {
                matches--;
            }
            
            // Check if all frequencies match
            if (matches == 26) {
                return true;
            }
        }
        
        return false;
    }
}
```

**Example Walkthrough**

Input: `s1 = "ab", s2 = "eidbaooo"`

| Step | Window | s2Freq | Comparison | matches | Found? |
|------|--------|--------|------------|---------|--------|
| Init | - | {} | - | - | - |
| Build | "ei" | {e:1,i:1} | vs {a:1,b:1} | 24 | No (only 24 chars match with 0) |
| 1 | "id" | {i:1,d:1} | vs {a:1,b:1} | 24 | No |
| 2 | "db" | {d:1,b:1} | vs {a:1,b:1} | 25 | No ('b' matches, not 'a') |
| 3 | "ba" | {b:1,a:1} | vs {a:1,b:1} | 26 | **Yes!** ✓ |

**Output:** `true`

**Complexity Analysis**
- **Time Complexity**: O(s1.length + s2.length) = O(n) — Linear pass
- **Space Complexity**: O(1) — Two fixed arrays of size 26

---

#### **Approach 2: Fixed Sliding Window + Array Comparison (SIMPLER)**

**Core Idea**: Compare frequency arrays directly instead of tracking matches.

**Code Implementation**
```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        
        int[] s1Freq = new int[26];
        int[] s2Freq = new int[26];
        
        // Build frequency for s1
        for (char c : s1.toCharArray()) {
            s1Freq[c - 'a']++;
        }
        
        // Slide window across s2
        for (int i = 0; i < s2.length(); i++) {
            // Add character to window
            s2Freq[s2.charAt(i) - 'a']++;
            
            // Remove character if window too large
            if (i >= s1.length()) {
                s2Freq[s2.charAt(i - s1.length()) - 'a']--;
            }
            
            // Check if frequencies match
            if (Arrays.equals(s1Freq, s2Freq)) {
                return true;
            }
        }
        
        return false;
    }
}
```

**Key Difference**: 
- Simpler code (no matches tracking)
- O(26) comparison per window position
- Still O(n) overall but higher constant factor

**Complexity Analysis**
- **Time Complexity**: O(n * 26) = O(n) — 26 is constant
- **Space Complexity**: O(1) — Two fixed arrays

---

#### **Approach 3: HashMap Approach (ALTERNATIVE)**

**Core Idea**: Use HashMap for frequency tracking.

**Code Implementation**
```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        
        Map<Character, Integer> s1Map = new HashMap<>();
        Map<Character, Integer> windowMap = new HashMap<>();
        
        // Build frequency map for s1
        for (char c : s1.toCharArray()) {
            s1Map.put(c, s1Map.getOrDefault(c, 0) + 1);
        }
        
        // Slide window
        for (int i = 0; i < s2.length(); i++) {
            char rightChar = s2.charAt(i);
            windowMap.put(rightChar, windowMap.getOrDefault(rightChar, 0) + 1);
            
            // Shrink window if too large
            if (i >= s1.length()) {
                char leftChar = s2.charAt(i - s1.length());
                windowMap.put(leftChar, windowMap.get(leftChar) - 1);
                if (windowMap.get(leftChar) == 0) {
                    windowMap.remove(leftChar);
                }
            }
            
            // Check if maps match
            if (windowMap.equals(s1Map)) {
                return true;
            }
        }
        
        return false;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n * m) where m = size of s1Map (up to 26)
- **Space Complexity**: O(1) — At most 26 entries in maps

---

#### **Approach 4: Brute Force (NOT OPTIMAL)**

**Core Idea**: Check every substring by sorting.

**Code Implementation**
```java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        
        String sorted1 = sortString(s1);
        
        for (int i = 0; i <= s2.length() - s1.length(); i++) {
            String substring = s2.substring(i, i + s1.length());
            if (sortString(substring).equals(sorted1)) {
                return true;
            }
        }
        
        return false;
    }
    
    private String sortString(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n * m log m) where m = s1.length
- **Space Complexity**: O(m) — For sorting
- **Why Not Optimal**: Too slow

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | Array Comparison | **Matches Count** |
|-------------|-------------|------------------|-------------------|
| Time complexity | O(n*m log m) ❌ | O(n*26) ✓ | **O(n) ✅** |
| Space complexity | O(m) ❌ | O(1) ✓ | **O(1) ✅** |
| Code simplicity | Simple | **Clean ✅** | Medium |
| Optimal | ❌ | Partial | **✅** |

**Winner**: **Matches Count** for optimal performance, **Array Comparison** for simplicity

### Why Fixed Window Size Works?

```
Key observation:
  Permutation of s1 has EXACTLY s1.length characters
  
  Any substring of s2 that's a permutation of s1:
    - Must have same length as s1
    - Must have same character frequencies
  
Therefore:
  Only need to check substrings of length s1.length
  → Fixed window size!
  
This reduces search space from O(n²) to O(n)
```

### Why Matches Count is Efficient?

```
Without matches count:
  At each window position:
    Compare all 26 frequencies
    → O(26) per position
    → O(n * 26) = O(n) but high constant

With matches count:
  Track how many of 26 frequencies currently match
  When character added/removed:
    - Update at most 1 frequency
    - Update matches count in O(1)
    - Check if matches == 26 in O(1)
  → O(1) per position
  → O(n) with low constant

Example update logic:
  Adding character 'a':
    s2Freq[a]++ → was 2, now 3
    If s1Freq[a] == 3: matches++ (now matching!)
    If s1Freq[a] == 2: matches-- (was matching, now not!)
```

---

## Critical Edge Cases & Gotchas

### 1. **s1 Longer Than s2**
```java
Input: s1 = "abcd", s2 = "ab"
Output: false
Explanation: s2 is too short to contain any permutation of s1.
```

### 2. **s1 Equals s2**
```java
Input: s1 = "abc", s2 = "abc"
Output: true
Explanation: s2 itself is a permutation of s1.
```

### 3. **Permutation at Start**
```java
Input: s1 = "ab", s2 = "baxy"
Output: true
Explanation: First window "ba" is a permutation of "ab".
```

### 4. **Permutation at End**
```java
Input: s1 = "ab", s2 = "xyba"
Output: true
Explanation: Last window "ba" is a permutation of "ab".
```

### 5. **No Permutation**
```java
Input: s1 = "abc", s2 = "defgh"
Output: false
Explanation: No common characters.
```

### 6. **Repeated Characters**
```java
Input: s1 = "aab", s2 = "cbaabc"
Output: true
Explanation: "baa" is a permutation of "aab".
```

### 7. **Single Character**
```java
Input: s1 = "a", s2 = "ab"
Output: true
Explanation: "a" appears in s2.
```

### 8. **All Same Characters**
```java
Input: s1 = "aaa", s2 = "aaaa"
Output: true
Explanation: Any window of length 3 in s2 contains "aaa".
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Handling s1.length > s2.length**
```java
// WRONG - doesn't check if s1 is longer
public boolean checkInclusion(String s1, String s2) {
    // Missing length check!
    for (int i = 0; i <= s2.length() - s1.length(); i++) {
        // This loop condition fails if s2.length() < s1.length()
        // Because s2.length() - s1.length() becomes negative (wrapped to large positive)
    }
}
```

**Why wrong**: When s1 longer than s2, can't possibly contain permutation!

**Fix**: Check at start
```java
if (s1.length() > s2.length()) {
    return false;
}
```

### ❌ **MISTAKE 2: Wrong Window Boundaries**
```java
// WRONG - incorrect loop boundaries
for (int i = s1.length(); i <= s2.length(); i++) {  // WRONG! Should be < not <=
    int leftIndex = s2.charAt(i - s1.length()) - 'a';
    // ...
}
```

**Why wrong**: When `i = s2.length()`, accessing `s2.charAt(i)` throws IndexOutOfBoundsException!

**Fix**: Use `i < s2.length()`
```java
for (int i = s1.length(); i < s2.length(); i++) {
```

### ❌ **MISTAKE 3: Not Updating Matches Correctly**
```java
// WRONG - doesn't update matches when removing character
s2Freq[leftIndex]--;
// Missing: Update matches count!
// Should check if this made frequencies match or unmatch
```

**Why wrong**: Matches count becomes stale, giving wrong results!

**Dry run failure for s1="ab", s2="ba":**
```
Initial window "ba": s2Freq={b:1,a:1}, matches=26 ✓
Should return true immediately!

But if we had a third character:
s1="ab", s2="bac"
Window "ba": matches=26 ✓ → return true (correct!)
```

**Fix**: Update matches when removing
```java
s2Freq[leftIndex]--;
if (s2Freq[leftIndex] == s1Freq[leftIndex]) {
    matches++;
} else if (s2Freq[leftIndex] == s1Freq[leftIndex] - 1) {
    matches--;
}
```

### ❌ **MISTAKE 4: Comparing Frequencies Before First Window Complete**
```java
// WRONG - checks match before building first window
for (int i = 0; i < s2.length(); i++) {
    s2Freq[s2.charAt(i) - 'a']++;
    
    // WRONG! Compares even when window not yet full
    if (Arrays.equals(s1Freq, s2Freq)) {
        return true;
    }
}
```

**Why wrong**: Compares partial window against complete s1!

**Dry run failure for s1="abc", s2="cab":**
```
i=0: Add 'c', s2Freq={c:1} vs s1Freq={a:1,b:1,c:1} → false (correct by accident)
i=1: Add 'a', s2Freq={c:1,a:1} vs s1Freq={a:1,b:1,c:1} → false (correct by accident)
i=2: Add 'b', s2Freq={c:1,a:1,b:1} vs s1Freq={a:1,b:1,c:1} → true ✓

Works in this case but logic is wrong!
```

**Fix**: Check after building first complete window
```java
for (int i = 0; i < s1.length(); i++) {
    s2Freq[s2.charAt(i) - 'a']++;
}
// First window complete, now check
if (matches == 26) return true;

// Then slide window
for (int i = s1.length(); i < s2.length(); i++) {
    // ...
}
```

### ❌ **MISTAKE 5: Off-by-One in Removing Character**
```java
// WRONG - removes wrong character
for (int i = s1.length(); i < s2.length(); i++) {
    // Add right character
    s2Freq[s2.charAt(i) - 'a']++;
    
    // WRONG! Should be i - s1.length(), not i - s1.length() - 1
    int leftIndex = s2.charAt(i - s1.length() - 1) - 'a';
    s2Freq[leftIndex]--;
}
```

**Why wrong**: Removes wrong character, window size becomes incorrect!

**Fix**: Use `i - s1.length()`
```java
int leftIndex = s2.charAt(i - s1.length()) - 'a';
```

### ❌ **MISTAKE 6: Not Initializing Matches Count**
```java
// WRONG - forgets to count initial matches
int matches = 0;

// Build frequencies
for (int i = 0; i < s1.length(); i++) {
    s1Freq[s1.charAt(i) - 'a']++;
    s2Freq[s2.charAt(i) - 'a']++;
}

// Missing: Count how many frequencies match!
// Should iterate through all 26 characters and count matches

if (matches == 26) {  // matches is still 0!
    return true;
}
```

**Why wrong**: Matches stays 0, never returns true even when frequencies match!

**Fix**: Initialize matches after building frequencies
```java
for (int i = 0; i < 26; i++) {
    if (s1Freq[i] == s2Freq[i]) {
        matches++;
    }
}
```

### ❌ **MISTAKE 7: Wrong Update Logic for Matches**
```java
// WRONG - incorrect match update logic
s2Freq[rightIndex]++;
if (s2Freq[rightIndex] == s1Freq[rightIndex]) {
    matches++;
}
// Missing else if to decrement when it goes over!
```

**Why wrong**: When frequency goes from matching to non-matching, doesn't decrement matches!

**Dry run failure:**
```
s1Freq[a] = 1, s2Freq[a] = 1 → matching, matches=26
Add another 'a': s2Freq[a] = 2
Now s2Freq[a] != s1Freq[a], but matches still 26! (WRONG!)
```

**Fix**: Handle both cases
```java
s2Freq[rightIndex]++;
if (s2Freq[rightIndex] == s1Freq[rightIndex]) {
    matches++;  // Now matching
} else if (s2Freq[rightIndex] == s1Freq[rightIndex] + 1) {
    matches--;  // Was matching, now not
}
```

---

## Complexity Analysis

### Time Complexity: **O(s1.length + s2.length)**

| Operation | Time | Reason |
|-----------|------|--------|
| Build s1 frequency | O(s1.length) | Iterate through s1 |
| Build first window | O(s1.length) | First s1.length characters of s2 |
| Count initial matches | O(26) = O(1) | Fixed array size |
| Slide window | O(s2.length) | Iterate through remaining s2 |
| Update per position | O(1) | Fixed operations per character |
| **Total** | **O(s1.length + s2.length)** | Linear time |

**Simplified**: O(n) where n = max(s1.length, s2.length)

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| s1Freq array | O(26) = O(1) | Fixed size for lowercase letters |
| s2Freq array | O(26) = O(1) | Fixed size for lowercase letters |
| Other variables | O(1) | matches, indices |
| **Total** | **O(1)** | Constant space |

---

## Visualization

### Complete Example Walkthrough

**Input:** `s1 = "ab", s2 = "eidbaooo"`

**Goal:** Check if s2 contains any permutation of s1.

---

**Step 1: Build Frequencies**
```
s1 = "ab"
s1Freq = {a:1, b:1, others:0}

First window of s2 = "ei"
s2Freq = {e:1, i:1, others:0}

Count matches:
  a: 0 vs 1 → no match
  b: 0 vs 1 → no match
  e: 1 vs 0 → no match
  i: 1 vs 0 → no match
  others (22): 0 vs 0 → match!
  
matches = 24 (not 26, so no match yet)
```

---

**Step 2: Slide Window - Position 2**
```
s2 = "eidbaooo"
      ↑↑
     (ei) → slide to (id)

Remove 'e': s2Freq[e] = 0
  Was: 1 vs 0 → no match
  Now: 0 vs 0 → match!
  matches++ → matches = 25

Add 'd': s2Freq[d] = 1
  Was: 0 vs 0 → match
  Now: 1 vs 0 → no match
  matches-- → matches = 24

Window "id": matches = 24 ≠ 26
```

---

**Step 3: Slide Window - Position 3**
```
s2 = "eidbaooo"
       ↑↑
      (id) → slide to (db)

Remove 'i': s2Freq[i] = 0
  Was: 1 vs 0 → no match
  Now: 0 vs 0 → match!
  matches++ → matches = 25

Add 'b': s2Freq[b] = 1
  Was: 0 vs 1 → no match
  Now: 1 vs 1 → match!
  matches++ → matches = 26... wait, matches was 25
  
Let me recalculate:
  After removing 'i': matches = 25
  After adding 'b': matches = 26 but we have {d:1, b:1}
  
Actually wait, let me trace more carefully...
```

Let me redo this more carefully:

---

**Initial State:**
```
s1 = "ab" → s1Freq = [1 at index 0 (a), 1 at index 1 (b), 0 for rest]
First window "ei" → s2Freq = [0,0,0,0,1,0,...,0,1,0] (1 at e, 1 at i)

Matching positions:
  Position 0 (a): 0 vs 1 ✗
  Position 1 (b): 0 vs 1 ✗
  Position 4 (e): 1 vs 0 ✗
  Position 8 (i): 1 vs 0 ✗
  Positions 2,3,5,6,7,9-25: all 0 vs 0 ✓ (22 matches)

matches = 22
```

---

**Slide to "id":**
```
Remove 'e' (index 4):
  s2Freq[4]-- → was 1, now 0
  Check: 0 vs 0 → match! matches++ → 23

Add 'd' (index 3):
  s2Freq[3]++ → was 0, now 1
  Check: 1 vs 0 → no match, was match, matches-- → 22

Window "id", matches = 22
```

---

**Slide to "db":**
```
Remove 'i' (index 8):
  s2Freq[8]-- → was 1, now 0
  Check: 0 vs 0 → match! matches++ → 23

Add 'b' (index 1):
  s2Freq[1]++ → was 0, now 1
  Check: 1 vs 1 → match! matches++ → 24

Window "db", matches = 24
```

---

**Slide to "ba":**
```
Remove 'd' (index 3):
  s2Freq[3]-- → was 1, now 0
  Check: 0 vs 0 → match! matches++ → 25

Add 'a' (index 0):
  s2Freq[0]++ → was 0, now 1
  Check: 1 vs 1 → match! matches++ → 26

Window "ba": s2Freq = {a:1, b:1, others:0}
matches = 26 ✓ ALL MATCH!

Return true
```

---

**Final Result:** `true`

**Explanation:** Window "ba" is a permutation of "ab"

---

## Comparison of Approaches

| Approach | Time | Space | Optimal | Notes |
|----------|------|-------|---------|-------|
| Brute Force (sorting) | O(n*m log m) | O(m) | ❌ | Sort each substring |
| Array Comparison | O(n*26) | O(1) | ✓ | Compare arrays each time |
| **Matches Count** | **O(n)** | **O(1)** | **✅** | **Optimal, track matches** |
| HashMap | O(n*26) | O(1) | ✓ | Similar to array comparison |

**Recommendation**: Use **Matches Count** for optimal performance

---

## Key Takeaways

1. **Fixed window size = s1.length** — permutations have same length
2. **Frequency matching determines permutation** — not order
3. **Track matches count for O(1) comparison** — more efficient than full array comparison
4. **Update matches incrementally** — when adding/removing characters
5. **Initialize first window separately** — then slide
6. **Check all 26 characters for matches** — even those not in s1
7. **O(n) time, O(1) space** — optimal solution

---

## Interview Tips

**What to say in an interview:**

> "This is a fixed-size sliding window problem. Since a permutation of s1 must have the same length as s1, I only need to check substrings of s2 that are exactly s1.length characters long. Two strings are permutations if they have identical character frequency counts. I'll maintain two frequency arrays: one for s1 and one for the current window in s2. To avoid comparing all 26 frequencies at each position, I'll track a 'matches' count that represents how many character frequencies currently match between the two arrays. When I slide the window, I update the window's frequency array by removing the outgoing character and adding the incoming character, then update the matches count accordingly. If matches ever equals 26, all frequencies match and I've found a permutation. This gives O(n) time and O(1) space."

**Key points to mention:**
1. **Fixed window size** — equals s1.length
2. **Frequency matching** — permutation = same character counts
3. **Matches optimization** — track count instead of full comparison
4. **Incremental updates** — O(1) per position
5. **Complexity** — O(n) time, O(1) space

**If asked about optimizations:**
> "The key optimization is tracking the matches count. Without it, I'd need to compare all 26 frequencies at each window position, giving O(n * 26) time. By tracking matches and updating it incrementally when characters are added or removed, I reduce the comparison to O(1) per position, achieving true O(n) time complexity."

**Common Follow-ups:**
- "What if strings contain Unicode?" → Use HashMap instead of array
- "What if you need to find all starting indices?" → Track indices when matches == 26
- "What about finding longest permutation substring?" → Different problem, use different approach

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Permutation in String** | Medium | **Fixed Sliding Window + Frequency** | **This problem** |
| Find All Anagrams in a String | Medium | Fixed Sliding Window + Frequency | Return all starting indices |
| Valid Anagram | Easy | Frequency Count | No sliding window, just compare |
| Minimum Window Substring | Hard | Variable Sliding Window | Find smallest window |
| Longest Substring Without Repeating Characters | Medium | Variable Sliding Window | No frequency matching, all unique |
| Substring with Concatenation of All Words | Hard | Fixed Sliding Window | Multiple word patterns |

**Pattern Progression**:
1. **Simple anagram** — Just compare two strings
2. **Permutation in string** (this problem) — Find any occurrence
3. **Find all anagrams** — Find all occurrences
4. **Minimum window** — Find smallest valid window

---

## Final Pattern Label

✅ **Fixed-Size Sliding Window + Frequency Matching (with Matches Optimization)**

**Remember:** Maintain a fixed window of size s1.length sliding across s2. Use two frequency arrays (or HashMaps) to compare character counts. Optimize by tracking a "matches" count representing how many of the 26 character frequencies currently match. Update matches incrementally in O(1) when sliding the window. When matches equals 26, all frequencies match and you've found a permutation. This gives O(n) time and O(1) space!
