# Minimum Window Substring

## Problem Description

**Difficulty**: Hard

Given two strings `s` and `t`, return the **shortest substring** of `s` such that every character in `t` (including duplicates) is present in the substring. If such a substring does not exist, return an empty string `""`.

You may assume that the correct output is always unique.

## Examples

### Example 1:
```
Input: s = "OUZODYXAZV", t = "XYZ"
Output: "YXAZ"
Explanation: 
"YXAZ" is the shortest substring that includes "X", "Y", and "Z" from string t.
The substring contains exactly what we need.
```

### Example 2:
```
Input: s = "xyz", t = "xyz"
Output: "xyz"
Explanation: 
The entire string s is required. No shorter substring exists.
```

### Example 3:
```
Input: s = "x", t = "xy"
Output: ""
Explanation: 
String s does not contain 'y', so no valid substring exists.
```

### Example 4:
```
Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation:
Valid windows: "ADOBEC", "ODEBANC", "BANC"
Shortest: "BANC" (length 4)
```

### Example 5:
```
Input: s = "a", t = "aa"
Output: ""
Explanation:
Need two 'a's but s only has one. No valid substring exists.
```

## Constraints
- 1 <= s.length <= 1000
- 1 <= t.length <= 1000
- s and t consist of uppercase and lowercase English letters

**Recommended Complexity**: O(n) time, O(m) space where n is length of s, m is number of unique characters

---

## Pattern Recognition

**Primary Pattern**: **Variable Sliding Window + Frequency Map**

**Why This Pattern?**
- Need to find substring (contiguous characters)
- Unknown length (need to minimize)
- Character frequency matching required
- Can expand/shrink window dynamically

**Key Insight**: Valid Window Condition
```
Problem: Find minimum substring containing all characters of t

Critical observation:
  We need to track CHARACTER FREQUENCIES, not just presence!
  
Example: t = "AAB"
  Valid window must have: A appears ≥2 times, B appears ≥1 time
  Invalid: "AB" (only 1 A)
  Valid: "AAB", "AABX", "XAABY", etc.
  
Strategy:
  Use frequency maps to track:
    1. Required frequencies (from t)
    2. Current window frequencies (from s)
    3. How many unique chars are satisfied
```

**Why Sliding Window?**
```
Brute force: Check all substrings
  for i from 0 to n:
    for j from i to n:
      check if s[i...j] contains all chars from t
  → O(n²) windows × O(n+m) checking = O(n³) ❌
  
Sliding Window:
  Expand window until valid (contains all chars)
  When valid, try to shrink while keeping it valid
  Track minimum valid window
  → O(n) — each character visited at most twice
```

**The Two-Map Strategy**:
```
Map 1: Target frequencies (from t)
  Example: t = "AABC"
  target = {A:2, B:1, C:1}
  
Map 2: Current window frequencies
  Example: window = "AABXC"
  window = {A:2, B:1, X:1, C:1}
  
Validation:
  For each char in target:
    window.get(char) >= target.get(char)
  
Optimization:
  Track "formed" count:
    formed = number of unique chars whose frequency is satisfied
    required = number of unique chars in t
    
  Window is valid when: formed == required
```

**Critical Detail**: Frequency Matching
```
NOT just checking presence:
  ❌ Wrong: window contains 'A', 'B', 'C'
  ✅ Right: window has ≥2 A's, ≥1 B, ≥1 C
  
Example: t = "AAB"
  Window "ABC": contains A,B,C but only 1 A → INVALID
  Window "AABC": has 2 A's, 1 B, 1 C → VALID
  
This is why we need frequency maps!
```

**The Shrinking Strategy**:
```
When window becomes valid:
  1. Record current window if it's smaller
  2. Try to shrink from left:
     - Remove leftmost character
     - Update window frequency
     - Check if still valid
     - If valid, continue shrinking
     - If invalid, stop and expand again
     
This ensures we find the MINIMUM window!
```

**Related Patterns**:
1. **Variable Sliding Window** — Expand/shrink dynamically
2. **Frequency Map** — Track character counts
3. **Two Pointers** — Left and right boundaries
4. **Greedy** — Shrink window as much as possible

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Try all substrings
  for i from 0 to n:
    for j from i to n:
      if s[i...j] contains all chars from t:
        update minimum
  
Time: O(n²) substrings × O(n+m) validation = O(n³)
Space: O(m) for frequency maps
Too slow! ❌

Sliding Window Approach:
  Expand right until valid
  Shrink left while valid
  Each char visited at most twice (once by right, once by left)
  → O(n + m) time ✅
```

**The Optimal Strategy**:
```
Key observations:
  1. Window starts empty, expands right
  2. When valid (contains all t chars), try shrinking left
  3. Continue until right reaches end
  4. Track minimum valid window seen
  
State tracking:
  - target: frequency map of t
  - window: frequency map of current window
  - formed: count of unique chars with satisfied frequency
  - required: count of unique chars in t
  
Validation: formed == required (all char frequencies satisfied)
```

### Step-by-Step Algorithm

---

#### **Approach 1: Variable Sliding Window with Frequency Maps (OPTIMAL)**

**Core Idea**:
- Use two frequency maps: one for target (t), one for current window
- Use formed/required counters for efficient validation
- Expand window right, shrink window left when valid
- Track minimum valid window

**Algorithm**
```
minWindow(s, t):
    if t.length > s.length:
        return ""
    
    // Build target frequency map
    target = frequencyMap(t)
    required = target.size()
    
    // Initialize sliding window
    window = empty map
    formed = 0  // Count of unique chars with satisfied frequency
    
    left = 0, right = 0
    minLen = infinity
    minLeft = 0
    
    // Expand window
    while right < s.length:
        char = s[right]
        window[char]++
        
        // Check if this char's frequency is now satisfied
        if char in target and window[char] == target[char]:
            formed++
        
        // Try to shrink window while valid
        while formed == required:
            // Update result if smaller window found
            if right - left + 1 < minLen:
                minLen = right - left + 1
                minLeft = left
            
            // Shrink from left
            leftChar = s[left]
            window[leftChar]--
            if leftChar in target and window[leftChar] < target[leftChar]:
                formed--
            left++
        
        right++
    
    return minLen == infinity ? "" : s[minLeft...minLeft+minLen-1]
```

**Code Implementation**
```java
class Solution {
    public String minWindow(String s, String t) {
        if (t.length() > s.length()) {
            return "";
        }
        
        // Build target frequency map
        Map<Character, Integer> target = new HashMap<>();
        for (char c : t.toCharArray()) {
            target.put(c, target.getOrDefault(c, 0) + 1);
        }
        
        int required = target.size();  // Number of unique chars in t
        int formed = 0;  // Number of unique chars with satisfied frequency
        
        // Current window frequency map
        Map<Character, Integer> window = new HashMap<>();
        
        // Sliding window pointers
        int left = 0, right = 0;
        
        // Result tracking
        int minLen = Integer.MAX_VALUE;
        int minLeft = 0;
        
        // Expand window with right pointer
        while (right < s.length()) {
            char c = s.charAt(right);
            window.put(c, window.getOrDefault(c, 0) + 1);
            
            // Check if current char's frequency is satisfied
            if (target.containsKey(c) && 
                window.get(c).intValue() == target.get(c).intValue()) {
                formed++;
            }
            
            // Try to shrink window while it's valid
            while (left <= right && formed == required) {
                // Update result if this window is smaller
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minLeft = left;
                }
                
                // Shrink from left
                char leftChar = s.charAt(left);
                window.put(leftChar, window.get(leftChar) - 1);
                
                // Check if removing this char breaks validity
                if (target.containsKey(leftChar) && 
                    window.get(leftChar).intValue() < target.get(leftChar).intValue()) {
                    formed--;
                }
                
                left++;
            }
            
            right++;
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }
}
```

**Example Walkthrough**

Input: `s = "ADOBECODEBANC", t = "ABC"`

Target: `{A:1, B:1, C:1}`, Required: 3

| Step | left | right | char | window | formed | Action | Result |
|------|------|-------|------|--------|--------|--------|--------|
| Init | 0 | 0 | - | {} | 0 | - | - |
| 1 | 0 | 0 | A | {A:1} | 1 | A satisfied | - |
| 2 | 0 | 1 | D | {A:1,D:1} | 1 | D not needed | - |
| 3 | 0 | 2 | O | {A:1,D:1,O:1} | 1 | O not needed | - |
| 4 | 0 | 3 | B | {A:1,D:1,O:1,B:1} | 2 | B satisfied | - |
| 5 | 0 | 4 | E | {A:1,D:1,O:1,B:1,E:1} | 2 | E not needed | - |
| 6 | 0 | 5 | C | {A:1,D:1,O:1,B:1,E:1,C:1} | 3 | **Valid!** | "ADOBEC" (len=6) |
| 7 | 1 | 5 | - | {D:1,O:1,B:1,E:1,C:1} | 2 | Remove A, invalid | - |
| 8 | 1 | 6 | O | {D:1,O:2,B:1,E:1,C:1} | 2 | - | - |
| 9 | 1 | 7 | D | {D:2,O:2,B:1,E:1,C:1} | 2 | - | - |
| 10 | 1 | 8 | E | {D:2,O:2,B:1,E:2,C:1} | 2 | - | - |
| 11 | 1 | 9 | B | {D:2,O:2,B:2,E:2,C:1} | 2 | - | - |
| 12 | 1 | 10 | A | {D:2,O:2,B:2,E:2,C:1,A:1} | 3 | **Valid!** | "DOBE CODEBANC"→shrink |
| 13 | 6 | 10 | - | {B:2,A:1,C:1} | 3 | After shrinking | "EBANC" (len=5) |
| 14 | 7 | 10 | - | {B:1,A:1,C:1} | 3 | Shrink more | "BANC" (len=4) ✓ |
| 15 | 8 | 10 | - | {A:1,C:1} | 2 | Remove B, invalid | - |
| 16 | 8 | 11 | N | {A:1,C:1,N:1} | 2 | - | - |
| 17 | 8 | 12 | C | {A:1,C:2,N:1} | 2 | Extra C | - |
| Exit | 8 | 13 | - | - | - | right >= n | - |

**Output:** `"BANC"` (minimum length = 4)

**Complexity Analysis**
- **Time Complexity**: O(n + m) — Each character in s visited at most twice (right and left pointers)
- **Space Complexity**: O(m) — Frequency maps for unique characters in s and t

---

#### **Approach 2: Optimized with Character Array (SPACE OPTIMIZATION)**

**Core Idea**: Use fixed-size array instead of HashMap (for ASCII characters).

**Code Implementation**
```java
class Solution {
    public String minWindow(String s, String t) {
        if (t.length() > s.length()) {
            return "";
        }
        
        // Frequency arrays (assuming ASCII)
        int[] target = new int[128];
        int[] window = new int[128];
        
        // Build target frequencies
        for (char c : t.toCharArray()) {
            target[c]++;
        }
        
        // Count required unique characters
        int required = 0;
        for (int count : target) {
            if (count > 0) required++;
        }
        
        int formed = 0;
        int left = 0, right = 0;
        int minLen = Integer.MAX_VALUE;
        int minLeft = 0;
        
        while (right < s.length()) {
            char c = s.charAt(right);
            window[c]++;
            
            // Check if this char is now satisfied
            if (target[c] > 0 && window[c] == target[c]) {
                formed++;
            }
            
            // Shrink window while valid
            while (left <= right && formed == required) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minLeft = left;
                }
                
                char leftChar = s.charAt(left);
                window[leftChar]--;
                
                if (target[leftChar] > 0 && window[leftChar] < target[leftChar]) {
                    formed--;
                }
                
                left++;
            }
            
            right++;
        }
        
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n + m) — Same as approach 1
- **Space Complexity**: O(1) — Fixed 128-size arrays (constant space)
- **Advantage**: Faster constant factor (array access vs HashMap)

---

#### **Approach 3: Brute Force (NOT RECOMMENDED)**

**Core Idea**: Check all substrings.

**Code Implementation**
```java
class Solution {
    public String minWindow(String s, String t) {
        String result = "";
        int minLen = Integer.MAX_VALUE;
        
        // Try all substrings
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                String substring = s.substring(i, j + 1);
                
                if (containsAll(substring, t) && substring.length() < minLen) {
                    minLen = substring.length();
                    result = substring;
                }
            }
        }
        
        return result;
    }
    
    private boolean containsAll(String s, String t) {
        Map<Character, Integer> target = new HashMap<>();
        for (char c : t.toCharArray()) {
            target.put(c, target.getOrDefault(c, 0) + 1);
        }
        
        for (char c : s.toCharArray()) {
            if (target.containsKey(c)) {
                target.put(c, target.get(c) - 1);
                if (target.get(c) == 0) {
                    target.remove(c);
                }
            }
        }
        
        return target.isEmpty();
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n² × (n + m)) — Very slow!
- **Space Complexity**: O(m) — Frequency map
- **Why Not Optimal**: Too many redundant checks

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | **Sliding Window + Freq Map** |
|-------------|-------------|-------------------------------|
| Time complexity | O(n² × m) ❌ | **O(n + m) ✅** |
| Space complexity | O(m) ✓ | **O(m) ✅** |
| Handles duplicates | ✓ | **✅** |
| Code simplicity | Simple | **Medium ✅** |
| Optimal | ❌ | **✅** |

**Winner**: **Variable Sliding Window with Frequency Maps** — optimal and handles all cases!

### Why Frequency Maps Are Essential?

```
Example: t = "AAB"
  Need: 2 A's, 1 B
  
Without frequency tracking:
  ❌ Window "AB": Has A and B but only 1 A (invalid)
  Would incorrectly report as valid!
  
With frequency maps:
  target = {A:2, B:1}
  window = {A:1, B:1}
  Check: window[A] >= target[A]? → 1 >= 2? → No! (correctly invalid)
  
  window = {A:2, B:1}
  Check: window[A] >= target[A]? → 2 >= 2? → Yes! (correctly valid)
```

### Why "Formed" Counter Optimization?

```
Without optimization:
  Every time window changes, iterate through all chars in target
  for each char in target:
    if window[char] >= target[char]: valid++
  if valid == required: window is valid
  → O(m) check per iteration → O(n × m) total ❌
  
With "formed" counter:
  Increment formed when: window[char] goes from (target[char]-1) to target[char]
  Decrement formed when: window[char] goes from target[char] to (target[char]-1)
  → O(1) check per iteration → O(n) total ✅
  
Example: target = {A:2, B:1}, required = 2
  window[A] = 1 → formed = 0
  window[A] = 2 → formed = 1 (A now satisfied!)
  window[B] = 1 → formed = 2 (B now satisfied! Valid!)
  window[A] = 1 → formed = 1 (A no longer satisfied)
```

### Why Shrink From Left?

```
When window becomes valid:
  We found A valid window, but is it minimum?
  
  Try removing leftmost character:
    If still valid → we found a smaller window!
    If invalid → stop shrinking, expand right again
    
Example: s = "ADOBECODEBA", t = "ABC"
  Window "ADOBEC" is valid (length 6)
  Remove A → "DOBEC" invalid (missing A)
  Keep "ADOBEC" and continue
  
  Later find "CODEBA" valid (length 6)
  Remove C → "ODEBA" invalid
  Remove O → "DEBA" invalid
  But later find "BANC" valid (length 4) — minimum!
```

---

## Critical Edge Cases & Gotchas

### 1. **Target Longer Than Source**
```java
Input: s = "a", t = "aa"
Output: ""
Explanation: Impossible to find two 'a's in s.
```

### 2. **Entire String is Answer**
```java
Input: s = "abc", t = "abc"
Output: "abc"
Explanation: Need entire string.
```

### 3. **No Valid Substring**
```java
Input: s = "abc", t = "d"
Output: ""
Explanation: 'd' not in s.
```

### 4. **Empty String**
```java
Input: s = "", t = "a"
Output: ""
Explanation: Empty source, no valid substring.
```

### 5. **Duplicate Characters in Target**
```java
Input: s = "ADOBECODEBANC", t = "AABC"
Output: "ADOBECODEBA"
Explanation: Need 2 A's, 1 B, 1 C. More restrictive.
```

### 6. **All Same Character**
```java
Input: s = "aaaaaaa", t = "aaa"
Output: "aaa"
Explanation: Need 3 consecutive 'a's, return first occurrence.
```

### 7. **Case Sensitive**
```java
Input: s = "Abc", t = "abc"
Output: ""
Explanation: 'A' != 'a'. Case matters.
```

### 8. **Extra Characters in Window**
```java
Input: s = "AXXXBXXXC", t = "ABC"
Output: "AXXXBXXXC"
Explanation: Must include all X's between required chars.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Using Set Instead of Frequency Map**
```java
// WRONG - doesn't track frequencies
Set<Character> target = new HashSet<>();
for (char c : t.toCharArray()) {
    target.add(c);  // WRONG! Lost frequency information
}
```

**Why wrong**: Can't handle duplicates!

**Dry run failure for t="AAB":**
```
target = {A, B}  // Lost the fact that we need 2 A's!
Window "AB": contains A and B → incorrectly reports valid
But we need 2 A's! Should be invalid.
```

**Fix**: Use frequency map
```java
Map<Character, Integer> target = new HashMap<>();
for (char c : t.toCharArray()) {
    target.put(c, target.getOrDefault(c, 0) + 1);
}
```

### ❌ **MISTAKE 2: Wrong "Formed" Increment Condition**
```java
// WRONG - increments formed multiple times
if (target.containsKey(c) && window.get(c) >= target.get(c)) {
    formed++;  // WRONG! Increments every time, not just when threshold crossed
}
```

**Why wrong**: Over-counts when frequency exceeds target!

**Dry run failure for window="AAB", target={A:1}:**
```
Add A → window[A]=1 → 1 >= 1 → formed++ (formed=1) ✓
Add A → window[A]=2 → 2 >= 1 → formed++ (formed=2) ❌ WRONG!
  formed should still be 1, not 2!
  
Result: formed=2 but required=1 → formed > required (impossible!)
```

**Fix**: Only increment when exactly reaching target
```java
if (target.containsKey(c) && window.get(c).intValue() == target.get(c).intValue()) {
    formed++;
}
```

### ❌ **MISTAKE 3: Wrong Shrinking Condition**
```java
// WRONG - checks individual char instead of entire window
while (window.containsKey(s.charAt(left)) && 
       window.get(s.charAt(left)) > target.getOrDefault(s.charAt(left), 0)) {
    // WRONG! Doesn't check if entire window is valid
    left++;
}
```

**Why wrong**: Doesn't ensure all required chars are present!

**Dry run failure for s="ADOBEC", t="ABC":**
```
At position with window="ADOBEC":
  A:1, D:1, O:1, B:1, E:1, C:1
  All formed, window is valid
  
Using wrong condition:
  leftChar = A, window[A]=1, target[A]=1
  1 > 1? No, stop
  Doesn't shrink even though D,O,E are unnecessary!
  
Correct: Check formed == required
  All chars satisfied → try shrinking
```

**Fix**: Check if entire window is valid
```java
while (formed == required) {
    // Try to shrink
    left++;
}
```

### ❌ **MISTAKE 4: Not Checking Window Before Updating Result**
```java
// WRONG - doesn't verify window is valid before updating
while (left <= right) {
    if (right - left + 1 < minLen) {
        minLen = right - left + 1;  // WRONG! Might be invalid window
        minLeft = left;
    }
    left++;
}
```

**Why wrong**: Updates result with invalid windows!

**Fix**: Only update when window is valid
```java
while (formed == required) {
    if (right - left + 1 < minLen) {
        minLen = right - left + 1;
        minLeft = left;
    }
    left++;
}
```

### ❌ **MISTAKE 5: Not Decrementing "Formed" When Shrinking**
```java
// WRONG - doesn't update formed when removing chars
char leftChar = s.charAt(left);
window.put(leftChar, window.get(leftChar) - 1);
left++;
// Missing: check if formed should decrease!
```

**Why wrong**: "formed" becomes incorrect!

**Dry run failure:**
```
Window "ABC" valid, formed=3, required=3
Remove A:
  window[A] goes from 1 to 0
  But formed still = 3 (should be 2!)
  Window now invalid but formed == required (false positive)
```

**Fix**: Update formed when char frequency drops below target
```java
char leftChar = s.charAt(left);
window.put(leftChar, window.get(leftChar) - 1);
if (target.containsKey(leftChar) && 
    window.get(leftChar) < target.get(leftChar)) {
    formed--;
}
left++;
```

### ❌ **MISTAKE 6: Off-by-One in Substring Extraction**
```java
// WRONG - incorrect substring indices
return s.substring(minLeft, minLeft + minLen - 1);  // WRONG! Missing last char
```

**Why wrong**: Second parameter is exclusive end index!

**Dry run failure for minLeft=9, minLen=4:**
```
Want: s[9], s[10], s[11], s[12] (4 chars)
Wrong: s.substring(9, 12) → s[9], s[10], s[11] (3 chars)
Correct: s.substring(9, 13) → s[9], s[10], s[11], s[12] (4 chars)
```

**Fix**: Use minLeft + minLen
```java
return s.substring(minLeft, minLeft + minLen);
```

### ❌ **MISTAKE 7: Not Handling Empty Result**
```java
// WRONG - doesn't check if valid window was found
return s.substring(minLeft, minLeft + minLen);  // Crashes if minLen = MAX_VALUE
```

**Why wrong**: If no valid window, minLen stays at MAX_VALUE!

**Fix**: Check if valid window was found
```java
if (minLen == Integer.MAX_VALUE) {
    return "";
}
return s.substring(minLeft, minLeft + minLen);
```

---

## Complexity Analysis

### Time Complexity: **O(n + m)**

| Operation | Time | Reason |
|-----------|------|--------|
| Build target map | O(m) | Iterate through t |
| Sliding window | O(n) | Each char in s visited at most twice (right, then left) |
| **Total** | **O(n + m)** | Linear in input sizes |

**Why O(n) for sliding window?**
```
Right pointer: moves from 0 to n-1
  → Each character added to window once
  → O(n) operations

Left pointer: moves from 0 to n-1 (across all iterations)
  → Each character removed from window at most once
  → O(n) operations total (amortized)
  
Total: O(n) + O(n) = O(n)

Example trace for left pointer:
  left starts at 0
  Across all iterations, left can move at most n times total
  (Can't move beyond right pointer)
```

### Space Complexity: **O(m)**

| Component | Space | Reason |
|-----------|-------|--------|
| target map | O(m) | Unique chars in t |
| window map | O(m) | Unique chars in s (at most 128 ASCII) |
| Variables | O(1) | Counters and pointers |
| **Total** | **O(m)** | Where m is number of unique characters |

**Optimization**: Use array instead of HashMap
```
If characters are ASCII:
  int[] target = new int[128];
  int[] window = new int[128];
  → O(1) space (fixed size)
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `s = "ADOBECODEBANC", t = "ABC"`

**Goal:** Find minimum substring containing A, B, C.

---

**Step 1: Setup**
```
s = "ADOBECODEBANC"
t = "ABC"

target = {A:1, B:1, C:1}
required = 3
window = {}
formed = 0

left = 0, right = 0
minLen = ∞, minLeft = 0
```

---

**Step 2: Expand Window (right=0 to 5)**
```
Iteration 1: right=0, char='A'
  window = {A:1}
  A satisfied! formed = 1
  formed < required → continue expanding

Iteration 2: right=1, char='D'
  window = {A:1, D:1}
  D not needed, formed = 1
  Continue expanding

Iteration 3: right=2, char='O'
  window = {A:1, D:1, O:1}
  O not needed, formed = 1
  Continue expanding

Iteration 4: right=3, char='B'
  window = {A:1, D:1, O:1, B:1}
  B satisfied! formed = 2
  formed < required → continue

Iteration 5: right=4, char='E'
  window = {A:1, D:1, O:1, B:1, E:1}
  E not needed, formed = 2
  Continue expanding

Iteration 6: right=5, char='C'
  window = {A:1, D:1, O:1, B:1, E:1, C:1}
  C satisfied! formed = 3
  formed == required → VALID! ✓
```

---

**Step 3: Shrink Window (First Valid Window)**
```
Window "ADOBEC" (length 6)
  Update result: minLen=6, minLeft=0

Try shrinking:
  Remove s[0]='A':
    window = {D:1, O:1, B:1, E:1, C:1}
    A no longer satisfied, formed = 2
    formed < required → invalid, stop shrinking
    left = 1
```

---

**Step 4: Continue Expanding (right=6 to 10)**
```
Iteration 7: right=6, char='O'
  window = {D:1, O:2, B:1, E:1, C:1}
  formed = 2, continue

Iteration 8: right=7, char='D'
  window = {D:2, O:2, B:1, E:1, C:1}
  formed = 2, continue

Iteration 9: right=8, char='E'
  window = {D:2, O:2, B:1, E:2, C:1}
  formed = 2, continue

Iteration 10: right=9, char='B'
  window = {D:2, O:2, B:2, E:2, C:1}
  formed = 2, continue

Iteration 11: right=10, char='A'
  window = {D:2, O:2, B:2, E:2, C:1, A:1}
  A satisfied! formed = 3
  formed == required → VALID! ✓
```

---

**Step 5: Shrink Window (Second Valid Window)**
```
Window "DOBECODEBANC" (left=1, right=10, length=10)
  10 >= 6, don't update result

Shrink multiple times:
  Remove D → window={O:2, B:2, E:2, C:1, A:1, D:1}, formed=3 (still valid)
  Remove O → window={B:2, E:2, C:1, A:1, O:1, D:1}, formed=3 (still valid)
  Remove B → window={B:1, E:2, C:1, A:1, O:1, D:1}, formed=3 (still valid)
  Remove E → window={B:1, E:1, C:1, A:1, O:1, D:1}, formed=3 (still valid)
  Remove C → window={B:1, E:1, A:1, O:1, D:1}, formed=2 (invalid after this)
  
  But wait! After removing C, before that iteration:
  Window "EBANC" (left=5, right=10, length=6)
    6 >= 6, don't update
    
  Actually shrink properly:
  After removing unwanted chars, we get:
  Window "BANC" (left=9, right=12, length=4)
    4 < 6! Update: minLen=4, minLeft=9 ✓
```

---

**Final Result:** `"BANC"` (substring from index 9, length 4)

### Visual State Diagram

```
s = A D O B E C O D E B A N C
    0 1 2 3 4 5 6 7 8 9 10 11 12

First valid window:
    A D O B E C
    [-------]
    Length: 6

Second valid window (best):
                  B A N C
                  [-----]
                  Length: 4 ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Optimal | Notes |
|----------|------|-------|---------|-------|
| Brute Force | O(n² × m) | O(m) | ❌ | Too slow |
| **Sliding Window + HashMap** | **O(n + m)** | **O(m)** | **✅** | **Best balance** |
| Sliding Window + Array | O(n + m) | O(1) | ✅ | Fastest (constant factor) |

**Recommendation**: Use **Sliding Window with Frequency Maps** — optimal and handles all cases!

---

## Key Takeaways

1. **Use frequency maps, not sets** — handles duplicate characters
2. **"Formed" counter optimization** — O(1) validation instead of O(m)
3. **Expand right, shrink left** — two-pointer technique
4. **Only update result when valid** — formed == required
5. **Update formed when crossing threshold** — only when window[c] == target[c]
6. **Decrement formed when shrinking** — when window[c] < target[c]
7. **Handle edge case** — return "" if minLen stays at MAX_VALUE

---

## Interview Tips

**What to say in an interview:**

> "This is a classic variable sliding window problem with frequency matching. I need to find the minimum substring of s that contains all characters from t, including their frequencies. I'll use two frequency maps: one for the target (t) and one for the current window. To efficiently check validity, I'll maintain a 'formed' counter that tracks how many unique characters have satisfied their required frequencies. I'll expand the window by moving the right pointer, and when the window becomes valid (formed equals required), I'll try to shrink it from the left while it remains valid. This gives O(n+m) time since each character is visited at most twice — once when adding to the window and once when removing."

**Key points to mention:**
1. **Variable sliding window** — expand right, shrink left when valid
2. **Frequency maps** — track character counts, not just presence
3. **Formed/required optimization** — O(1) validation
4. **Shrink while valid** — find minimum window
5. **Complexity** — O(n+m) time, O(m) space

**If asked about alternatives:**
> "I could check all O(n²) substrings, but that would be O(n² × m) time, which is too slow. The sliding window approach is optimal because it processes each character at most twice. I could also optimize space to O(1) by using fixed-size character arrays instead of HashMaps for ASCII characters."

**Common Follow-ups:**
- "What if t is longer than s?" → Return "" immediately
- "What if multiple minimum windows exist?" → Return any one (problem guarantees uniqueness)
- "Can you optimize space?" → Use int[128] arrays for ASCII
- "What if characters can be rearranged?" → Different problem (anagram)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Minimum Window Substring** | Hard | **Variable Sliding Window + Freq** | **This problem** |
| Longest Substring Without Repeating Characters | Medium | Variable Sliding Window + Set | No duplicates allowed |
| Longest Repeating Character Replacement | Medium | Variable Sliding Window + Freq | Fixed k replacements |
| Permutation in String | Medium | Fixed Sliding Window + Freq | Exact match, fixed size |
| Find All Anagrams in a String | Medium | Fixed Sliding Window + Freq | Multiple results |
| Substring with Concatenation of All Words | Hard | Sliding Window + HashMap | Word-level matching |

**Pattern Progression**:
1. **Fixed window + frequency** (easier) — Permutation in String
2. **Variable window + frequency** (this problem) — Minimum Window Substring
3. **Variable window + constraint** (medium) — Longest Repeating Character Replacement

---

## Final Pattern Label

✅ **Variable Sliding Window + Frequency Map (Character Frequency Matching)**

**Remember:** Use two frequency maps (target and window) with a "formed" counter for efficient O(1) validation. Expand the window right until valid, then shrink left while maintaining validity. Track the minimum valid window seen. The key optimization is incrementing "formed" only when window[c] equals target[c] (not when it exceeds), and decrementing only when it drops below. This gives O(n+m) time with O(m) space!
