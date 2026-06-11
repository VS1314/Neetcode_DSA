# Longest Substring Without Repeating Characters

## Problem Description

**Difficulty**: Medium

Given a string `s`, find the *length of the longest substring* without duplicate characters.

A **substring** is a contiguous sequence of characters within a string.

## Examples

### Example 1:
```
Input: s = "zxyzxyz"
Output: 3
Explanation: The string "xyz" is the longest without duplicate characters.
```

### Example 2:
```
Input: s = "xxxx"
Output: 1
Explanation: All characters are the same. 
The longest substring without repeats is any single character "x".
```

### Example 3:
```
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.
```

### Example 4:
```
Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Note that "pwke" is a subsequence, not a substring.
```

## Constraints
- 0 <= s.length <= 50,000
- s consists of English letters, digits, symbols, and spaces

**Recommended Complexity**: O(n) time, O(m) space
- n = length of string
- m = number of unique characters

---

## Pattern Recognition

**Primary Pattern**: **Sliding Window + HashSet (Dynamic Window)**

**Why This Pattern?**
- Need to find longest substring (contiguous)
- Constraint: no duplicate characters
- "Longest" suggests we need to maximize window size
- "Without duplicates" suggests we need to track characters in current window

**Key Insight**: Expand-Shrink Window Strategy
```
Brute force: Try all substrings
  For each start position i:
    Extend to j until duplicate found
    Track max length
  → O(n²) time with duplicate checking

Sliding Window: Maintain valid window without duplicates
  Expand: Add character at right pointer
  Shrink: Remove characters from left when duplicate found
  Track: Maximum window size seen
  → O(n) time, single pass!
```

**The Window Invariant**:
```
At all times, the window [left...right] contains:
  - All unique characters (no duplicates)
  - A valid substring candidate

When we encounter a duplicate:
  - Shrink window from left until duplicate removed
  - Then expand again
  
Example: s = "abcabcbb"
  
  abc → valid, len=3
  abca → 'a' duplicate! Shrink: bca → valid, len=3
  bcab → 'b' duplicate! Shrink: cab → valid, len=3
  cabb → 'b' duplicate! Shrink: abb → 'b' still duplicate! Shrink: bb → valid, len=1
  
  Maximum length = 3 (from "abc" or "bca" or "cab")
```

**Why HashSet?**
```
Need to:
  1. Check if character already in window → O(1)
  2. Add character to window → O(1)
  3. Remove character from window → O(1)
  
HashSet provides all operations in O(1)!

Alternative: HashMap to track indices (optimization)
  - HashMap<Character, Integer> for last seen index
  - Can jump left pointer directly instead of incrementing
```

**Critical Detail**: Window Must Remain Valid
```
When duplicate found at right:
  Must shrink from left until that duplicate removed
  
Example: "abcadefc"
         
  Window: "abca"
          ↑    ↑
          L    R
  
  'a' appears twice!
  Shrink: Remove 'a' from left
  Window becomes "bca"
          
  Now add 'd': "bcad" → valid!
```

**Related Patterns**:
1. **Sliding Window** — Dynamic window size
2. **HashSet/HashMap** — Track window contents
3. **Two Pointers** — Left and right boundaries
4. **Substring Problems** — Contiguous sequence

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Try all substrings
  for i = 0 to n:
    for j = i to n:
      if substring[i..j] has no duplicates:
        update max length
  
  Checking duplicates: O(n) with HashSet
  Total: O(n³) or O(n²) optimized
  → Too slow for n=50,000!

Sliding Window:
  Maintain window with unique characters
  Expand right: add new character
  Shrink left: remove duplicates
  Track: maximum window size
  → O(n) time, each character visited at most twice!
```

**The Sliding Window Strategy**:
```
Key observations:
  1. If [i...j] is valid (no duplicates), no need to check [i...j-1]
  2. If [i...j] has duplicate, [i...j+1] will also have it
  3. Only need to shrink from left to fix duplicates
  
Two pointers approach:
  left = 0, right = 0
  HashSet tracks characters in window
  
  For each right position:
    While s[right] in HashSet:
      Remove s[left] from HashSet
      left++
    Add s[right] to HashSet
    Update max = max(max, right - left + 1)
    right++
```

### Step-by-Step Algorithm

---

#### **Approach 1: Sliding Window + HashSet (OPTIMAL)**

**Core Idea**:
- Use two pointers (left, right) to represent window
- HashSet tracks characters in current window
- Expand right, shrink left when duplicate found

**Algorithm**
```
lengthOfLongestSubstring(s):
    left = 0
    maxLength = 0
    seen = HashSet()
    
    for right from 0 to s.length - 1:
        // Shrink window until no duplicate
        while s[right] in seen:
            seen.remove(s[left])
            left++
        
        // Add current character
        seen.add(s[right])
        
        // Update maximum length
        maxLength = max(maxLength, right - left + 1)
    
    return maxLength
```

**Code Implementation**
```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> seen = new HashSet<>();
        int left = 0;
        int maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // Shrink window from left until no duplicate
            while (seen.contains(currentChar)) {
                seen.remove(s.charAt(left));
                left++;
            }
            
            // Add current character to window
            seen.add(currentChar);
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}
```

**Example Walkthrough**

Input: `s = "abcabcbb"`

| Step | right | char | left | seen | Window | Action | maxLength |
|------|-------|------|------|------|--------|--------|-----------|
| Init | - | - | 0 | {} | "" | - | 0 |
| 1 | 0 | 'a' | 0 | {a} | "a" | Add 'a' | 1 |
| 2 | 1 | 'b' | 0 | {a,b} | "ab" | Add 'b' | 2 |
| 3 | 2 | 'c' | 0 | {a,b,c} | "abc" | Add 'c' | 3 |
| 4 | 3 | 'a' | 0→1 | {b,c} then {a,b,c} | "bca" | Remove 'a', add 'a' | 3 |
| 5 | 4 | 'b' | 1→2 | {c,a} then {a,b,c} | "cab" | Remove 'b', add 'b' | 3 |
| 6 | 5 | 'c' | 2→3 | {a,b} then {a,b,c} | "abc" | Remove 'c', add 'c' | 3 |
| 7 | 6 | 'b' | 3→4→5 | {c} then {b,c} | "cb" | Remove 'a','b', add 'b' | 3 |
| 8 | 7 | 'b' | 5→6→7 | {} then {b} | "b" | Remove 'c','b', add 'b' | 3 |

**Output:** `3`

**Complexity Analysis**
- **Time Complexity**: O(n) — Each character added once, removed at most once
- **Space Complexity**: O(m) — HashSet stores unique characters, m ≤ min(n, charset_size)

---

#### **Approach 2: Sliding Window + HashMap (OPTIMIZED)**

**Core Idea**: Use HashMap to store last seen index, jump left pointer directly.

**Code Implementation**
```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int left = 0;
        int maxLength = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // If character seen before and within current window
            if (lastSeen.containsKey(currentChar)) {
                // Move left to position after last occurrence
                left = Math.max(left, lastSeen.get(currentChar) + 1);
            }
            
            // Update last seen index
            lastSeen.put(currentChar, right);
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}
```

**Key Difference**: 
- Instead of while loop to shrink, jump left directly
- Stores indices instead of just presence
- Slightly faster in practice (fewer operations)

**Example**: `s = "abba"`
```
Step 1: right=0, 'a'
  lastSeen = {a:0}
  left = 0
  window = "a", length = 1

Step 2: right=1, 'b'
  lastSeen = {a:0, b:1}
  left = 0
  window = "ab", length = 2

Step 3: right=2, 'b'
  'b' seen at index 1 (in window!)
  left = max(0, 1+1) = 2
  lastSeen = {a:0, b:2}
  window = "b", length = 1

Step 4: right=3, 'a'
  'a' seen at index 0 (NOT in current window! left=2 > 0)
  left = max(2, 0+1) = 2 (no change)
  lastSeen = {a:3, b:2}
  window = "ba", length = 2

Maximum = 2
```

**Complexity Analysis**
- **Time Complexity**: O(n) — Single pass
- **Space Complexity**: O(m) — HashMap stores unique characters

---

#### **Approach 3: Brute Force (NOT OPTIMAL)**

**Core Idea**: Try all substrings, check for duplicates.

**Code Implementation**
```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        
        for (int i = 0; i < s.length(); i++) {
            Set<Character> seen = new HashSet<>();
            int j = i;
            
            // Extend substring from i
            while (j < s.length() && !seen.contains(s.charAt(j))) {
                seen.add(s.charAt(j));
                j++;
            }
            
            maxLength = Math.max(maxLength, j - i);
        }
        
        return maxLength;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n²) — Nested loops
- **Space Complexity**: O(m) — HashSet for checking
- **Why Not Optimal**: Too slow for large inputs

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | Sliding Window + HashSet | **Sliding Window + HashMap** |
|-------------|-------------|--------------------------|------------------------------|
| Time complexity | O(n²) ❌ | O(n) ✓ | **O(n) ✅** |
| Space complexity | O(m) ✓ | O(m) ✓ | **O(m) ✅** |
| Code simplicity | Simple | **Clean ✅** | Medium |
| Optimal | ❌ | ✅ | **✅ (slightly faster)** |

**Winner**: **Sliding Window + HashSet** for clarity, **HashMap** for performance

### Why Sliding Window Works?

```
Key observation:
  If [i...j] has duplicate, extending to [i...j+1] won't help
  Need to shrink from left to remove duplicate
  
But we don't need to check all [i+1...j], [i+2...j], etc.
  Once we remove duplicate from left, window is valid again!
  
This is why sliding window is optimal:
  Each element enters window once (right pointer)
  Each element leaves window at most once (left pointer)
  Total operations: 2n → O(n)
```

### Why HashSet/HashMap?

```
Alternative: Array-based tracking
  boolean[] seen = new boolean[256]; // ASCII
  
  Pros: Slightly faster (no hashing)
  Cons: Limited to ASCII, more space if charset small
  
HashSet/HashMap:
  Pros: Works for any character set (Unicode)
  Cons: Slight overhead from hashing
  
For interviews: HashSet is cleaner and more general!
```

### HashSet vs HashMap Trade-off

```
HashSet approach:
  while (seen.contains(s[right])):
    seen.remove(s[left])
    left++
  → May iterate multiple times to remove duplicate

HashMap approach:
  left = max(left, lastSeen[s[right]] + 1)
  → Jump directly to correct position
  → Faster but slightly more complex

Both are O(n), HashMap has better constant factor
```

---

## Critical Edge Cases & Gotchas

### 1. **Empty String**
```java
Input: s = ""
Output: 0
Explanation: No characters, length = 0.
```

### 2. **Single Character**
```java
Input: s = "a"
Output: 1
Explanation: One character, length = 1.
```

### 3. **All Same Characters**
```java
Input: s = "aaaaa"
Output: 1
Explanation: All duplicates, max length = 1.
```

### 4. **All Unique Characters**
```java
Input: s = "abcdefg"
Output: 7
Explanation: No duplicates, entire string is answer.
```

### 5. **Duplicates at Start**
```java
Input: s = "aabcdef"
Output: 6
Explanation: "abcdef" is the longest.
```

### 6. **Duplicates at End**
```java
Input: s = "abcdeff"
Output: 6
Explanation: "abcdef" is the longest.
```

### 7. **Multiple Valid Windows**
```java
Input: s = "abcxyzabc"
Output: 6
Explanation: "abcxyz" or "xyzabc", both length 6.
```

### 8. **Special Characters**
```java
Input: s = "a b!c@a"
Output: 5
Explanation: " b!c@" has 5 unique characters (space counts!).
```

### 9. **Alternating Pattern**
```java
Input: s = "abababab"
Output: 2
Explanation: "ab" repeats, max length = 2.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Handling Empty String**
```java
// WRONG - doesn't handle empty string
public int lengthOfLongestSubstring(String s) {
    char firstChar = s.charAt(0);  // Crashes if s is empty!
    // ...
}
```

**Why wrong**: `s.charAt(0)` throws IndexOutOfBoundsException if string is empty!

**Fix**: Loop handles empty string naturally (0 iterations)
```java
for (int right = 0; right < s.length(); right++) {
    // If s.length() == 0, loop doesn't execute
}
```

### ❌ **MISTAKE 2: Not Removing from HashSet**
```java
// WRONG - only adds, never removes
Set<Character> seen = new HashSet<>();
int left = 0, maxLength = 0;

for (int right = 0; right < s.length(); right++) {
    if (seen.contains(s.charAt(right))) {
        left++;  // WRONG! Moves left but doesn't update HashSet
    }
    seen.add(s.charAt(right));
    maxLength = Math.max(maxLength, right - left + 1);
}
```

**Why wrong**: HashSet still contains characters outside window!

**Dry run failure for s="abca":**
```
Step 1: right=0, 'a'
  seen={a}, left=0, window="a", len=1

Step 2: right=1, 'b'
  seen={a,b}, left=0, window="ab", len=2

Step 3: right=2, 'c'
  seen={a,b,c}, left=0, window="abc", len=3

Step 4: right=3, 'a'
  'a' in seen! (WRONG: HashSet still has old 'a')
  left++ → left=1
  seen={a,b,c,a} (can't have duplicate 'a'!)
  window should be "bca" but seen still has all chars
  len = 3-1+1 = 3 (WRONG! Calculation is off)

HashSet doesn't match actual window!
```

**Fix**: Remove characters when shrinking
```java
while (seen.contains(s.charAt(right))) {
    seen.remove(s.charAt(left));
    left++;
}
```

### ❌ **MISTAKE 3: Off-by-One in Window Length**
```java
// WRONG - incorrect length calculation
maxLength = Math.max(maxLength, right - left);  // WRONG! Should be +1
```

**Why wrong**: Window length from left to right (inclusive) is `right - left + 1`!

**Dry run failure for s="ab":**
```
right=0, left=0: length = 0-0 = 0 (WRONG! Should be 1)
right=1, left=0: length = 1-0 = 1 (WRONG! Should be 2)
```

**Fix**: Add 1
```java
maxLength = Math.max(maxLength, right - left + 1);
```

### ❌ **MISTAKE 4: Initializing left to 1 Instead of 0**
```java
// WRONG - starts left at 1
int left = 1;  // WRONG! Should be 0
```

**Why wrong**: Skips first character!

**Dry run failure for s="a":**
```
right=0, left=1
window is empty (left > right)
maxLength = 0-1+1 = 0 (WRONG! Should be 1)
```

**Fix**: Start at 0
```java
int left = 0;
```

### ❌ **MISTAKE 5: Using HashMap Without Max() Check**
```java
// WRONG - doesn't use max() when updating left
if (lastSeen.containsKey(currentChar)) {
    left = lastSeen.get(currentChar) + 1;  // WRONG! Can move left backward!
}
```

**Why wrong**: If duplicate is before current window, left moves backward!

**Dry run failure for s="abba":**
```
Step 1: right=0, 'a' → lastSeen={a:0}, left=0, window="a"
Step 2: right=1, 'b' → lastSeen={a:0,b:1}, left=0, window="ab"
Step 3: right=2, 'b' → 'b' seen at 1
  left = 1+1 = 2 ✓
  lastSeen={a:0,b:2}, window="b"
Step 4: right=3, 'a' → 'a' seen at 0
  left = 0+1 = 1 (WRONG! Moves backward from 2 to 1!)
  window="bba" (has duplicate 'b'!)

Left pointer should only move forward!
```

**Fix**: Use max()
```java
left = Math.max(left, lastSeen.get(currentChar) + 1);
```

### ❌ **MISTAKE 6: Adding Character Before Shrinking**
```java
// WRONG - adds before removing duplicate
for (int right = 0; right < s.length(); right++) {
    seen.add(s.charAt(right));  // WRONG! Add first
    
    while (seen.contains(s.charAt(right))) {  // Will always be true!
        seen.remove(s.charAt(left));
        left++;
    }
}
```

**Why wrong**: Character added to set, then checking if it exists (always true if it's a duplicate)!

**Fix**: Check before adding
```java
while (seen.contains(s.charAt(right))) {  // Check first
    seen.remove(s.charAt(left));
    left++;
}
seen.add(s.charAt(right));  // Add after
```

### ❌ **MISTAKE 7: Not Updating maxLength**
```java
// WRONG - only returns final window size
return right - left + 1;  // WRONG! Returns last window, not maximum!
```

**Why wrong**: Need to track maximum across all windows!

**Dry run failure for s="abcb":**
```
Window 1: "abc" → length 3
Window 2: "cb" → length 2
Returns 2 (WRONG! Should return 3)
```

**Fix**: Update max in loop
```java
maxLength = Math.max(maxLength, right - left + 1);
return maxLength;  // Return maximum seen
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Iterate through string | O(n) | Right pointer moves n times |
| Add to HashSet | O(1) | Each character added once |
| Remove from HashSet | O(1) | Each character removed at most once |
| Contains check | O(1) | Hash lookup |
| Update max | O(1) | Comparison |
| **Total** | **O(n)** | Linear time, each char visited at most twice |

**Why O(n)?**
- Right pointer: moves from 0 to n-1 → n moves
- Left pointer: moves from 0 to n-1 → at most n moves
- Total moves: 2n → O(n)

### Space Complexity: **O(m)**

| Component | Space | Reason |
|-----------|-------|--------|
| HashSet/HashMap | O(m) | Stores unique characters in window |
| Other variables | O(1) | left, right, maxLength |
| **Total** | **O(m)** | m = min(n, charset_size) |

**What is m?**
- m = number of unique characters in string
- m ≤ n (can't have more unique chars than string length)
- m ≤ charset size (e.g., 128 for ASCII, 256 for extended ASCII)
- For English letters: m ≤ 26 or 52 → O(1) in practice!

---

## Visualization

### Complete Example Walkthrough

**Input:** `s = "pwwkew"`

**Goal:** Find length of longest substring without repeating characters.

---

**Initial State:**
```
left = 0, right = 0
seen = {}
maxLength = 0
```

---

**Step 1: right=0, char='p'**
```
s = "pwwkew"
     ↑
     L,R

seen.contains('p')? No
seen.add('p') → seen = {p}
window = "p"
maxLength = max(0, 0-0+1) = 1

State: left=0, right=0, maxLength=1
```

---

**Step 2: right=1, char='w'**
```
s = "pwwkew"
     ↑ ↑
     L R

seen.contains('w')? No
seen.add('w') → seen = {p, w}
window = "pw"
maxLength = max(1, 1-0+1) = 2

State: left=0, right=1, maxLength=2
```

---

**Step 3: right=2, char='w'**
```
s = "pwwkew"
     ↑   ↑
     L   R

seen.contains('w')? Yes! (duplicate!)
  Shrink window:
    Loop iteration 1:
      seen.remove('p') → seen = {w}
      left++ → left = 1
    seen.contains('w')? Still Yes!
    Loop iteration 2:
      seen.remove('w') → seen = {}
      left++ → left = 2
    seen.contains('w')? No, exit loop

seen.add('w') → seen = {w}
window = "w"
maxLength = max(2, 2-2+1) = 2

State: left=2, right=2, maxLength=2
```

---

**Step 4: right=3, char='k'**
```
s = "pwwkew"
       ↑ ↑
       L R

seen.contains('k')? No
seen.add('k') → seen = {w, k}
window = "wk"
maxLength = max(2, 3-2+1) = 2

State: left=2, right=3, maxLength=2
```

---

**Step 5: right=4, char='e'**
```
s = "pwwkew"
       ↑   ↑
       L   R

seen.contains('e')? No
seen.add('e') → seen = {w, k, e}
window = "wke"
maxLength = max(2, 4-2+1) = 3 ✓ (new max!)

State: left=2, right=4, maxLength=3
```

---

**Step 6: right=5, char='w'**
```
s = "pwwkew"
       ↑     ↑
       L     R

seen.contains('w')? Yes! (duplicate!)
  Shrink window:
    Loop iteration 1:
      seen.remove('w') → seen = {k, e}
      left++ → left = 3
    seen.contains('w')? No, exit loop

seen.add('w') → seen = {k, e, w}
window = "kew"
maxLength = max(3, 5-3+1) = 3

State: left=3, right=5, maxLength=3
```

---

**Final Result:** `maxLength = 3`

**Longest Substring:** "wke" (or "kew")

### Visual Summary

```
s = "pwwkew"

Windows explored:
  "p"     → length 1
  "pw"    → length 2 ✓
  "w"     → length 1 (shrunk after duplicate 'w')
  "wk"    → length 2
  "wke"   → length 3 ✓✓✓ (maximum!)
  "kew"   → length 3

Maximum length = 3
```

---

## Comparison of Approaches

| Approach | Time | Space | Optimal | Notes |
|----------|------|-------|---------|-------|
| Brute Force | O(n²) | O(m) | ❌ | Try all substrings |
| Sliding Window + HashSet | O(n) | O(m) | **✅** | **Clean & intuitive** |
| Sliding Window + HashMap | O(n) | O(m) | **✅** | **Slightly faster** |
| Array (ASCII only) | O(n) | O(1) | ✅ | Limited to ASCII chars |

**Recommendation**: Use **Sliding Window + HashSet** for interviews (clear logic)

---

## Key Takeaways

1. **Sliding window for substring problems** — especially with constraints
2. **HashSet tracks window contents** — O(1) duplicate detection
3. **Expand right, shrink left** — maintain window invariant
4. **Two pointers move forward only** — each element visited at most twice
5. **Window length = right - left + 1** — inclusive range
6. **Track maximum across all windows** — not just final window
7. **O(n) time, O(m) space** — optimal solution

---

## Interview Tips

**What to say in an interview:**

> "This is a classic sliding window problem. I need to find the longest substring without duplicate characters, which means I need to maintain a window where all characters are unique. I'll use two pointers, left and right, to represent the window boundaries, and a HashSet to track which characters are currently in the window. As I move the right pointer to expand the window, if I encounter a character that's already in the set, I'll shrink the window from the left by removing characters until the duplicate is gone. At each step, I'll update the maximum length. This gives O(n) time since each character is added and removed at most once, and O(m) space for the HashSet where m is the number of unique characters."

**Key points to mention:**
1. **Sliding window pattern** — dynamic window size
2. **HashSet for tracking** — O(1) duplicate detection
3. **Expand and shrink** — right expands, left shrinks on duplicate
4. **Maximum tracking** — track max length across all windows
5. **Complexity** — O(n) time, O(m) space

**If asked about optimizations:**
> "I could optimize using a HashMap to store the last seen index of each character. Instead of shrinking one step at a time with a while loop, I can jump the left pointer directly to the position after the last occurrence of the duplicate character. This reduces the number of operations but doesn't change the O(n) time complexity. Both approaches are optimal."

**Common Follow-ups:**
- "What if you need to return the actual substring?" → Track start index when updating maxLength
- "What about exactly k distinct characters?" → Different problem, still sliding window
- "What if characters are Unicode?" → HashSet handles it automatically

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Longest Substring Without Repeating Characters** | Medium | **Sliding Window + HashSet** | **This problem** |
| Longest Substring with At Most K Distinct Characters | Medium | Sliding Window + HashMap | Fixed limit k |
| Longest Repeating Character Replacement | Medium | Sliding Window + Count | Can replace k chars |
| Minimum Window Substring | Hard | Sliding Window + HashMap | Must contain all chars of pattern |
| Substring with Concatenation of All Words | Hard | Sliding Window + HashMap | Multiple word patterns |
| Longest Substring with At Most Two Distinct Characters | Medium | Sliding Window + HashMap | Limit of 2 distinct |

**Pattern Progression**:
1. **No duplicates** (this problem) — Basic sliding window
2. **At most k distinct** — Track count of distinct characters
3. **With replacements** — Allow changing characters
4. **Minimum window** — Find smallest instead of largest

---

## Final Pattern Label

✅ **Sliding Window + HashSet (Dynamic Window with Unique Constraint)**

**Remember:** Use two pointers (left, right) to maintain a window of unique characters. Expand by moving right pointer and adding character to HashSet. When duplicate found, shrink from left by removing characters from HashSet until duplicate removed. Track maximum window size at each step. Each character enters and leaves window at most once, giving O(n) time complexity!
