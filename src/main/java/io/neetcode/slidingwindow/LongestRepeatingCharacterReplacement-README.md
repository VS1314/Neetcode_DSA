# Longest Repeating Character Replacement

## Problem Description

**Difficulty**: Medium

You are given a string `s` consisting of only uppercase English characters and an integer `k`. You can choose up to `k` characters of the string and replace them with any other uppercase English character.

After performing at most `k` replacements, return the **length of the longest substring** which contains only one distinct character.

## Examples

### Example 1:
```
Input: s = "XYYX", k = 2
Output: 4
Explanation: Either replace the 'X's with 'Y's (making "YYYY"), 
or replace the 'Y's with 'X's (making "XXXX").
Both give a substring of length 4.
```

### Example 2:
```
Input: s = "AAABABB", k = 1
Output: 5
Explanation: Replace one 'B' with 'A' to get "AAAAABB".
The substring "AAAAA" has length 5.
```

### Example 3:
```
Input: s = "AABABBA", k = 1
Output: 4
Explanation: Replace one 'B' with 'A' to get "AAAAABA".
The substring "AAAA" has length 4.
```

### Example 4:
```
Input: s = "ABAB", k = 2
Output: 4
Explanation: Replace both 'B's with 'A's to get "AAAA".
Length = 4.
```

## Constraints
- 1 <= s.length <= 10,000
- 0 <= k <= s.length
- s consists of only uppercase English letters

**Recommended Complexity**: O(n) time, O(m) space
- n = length of string
- m = number of unique characters (26 for uppercase English)

---

## Pattern Recognition

**Primary Pattern**: **Sliding Window + Frequency Tracking (Variable Window with Replacement Constraint)**

**Why This Pattern?**
- Need to find longest substring (contiguous)
- Can replace up to k characters
- "Longest" suggests maximizing window size
- Replacement constraint suggests tracking character frequencies

**Key Insight**: Replace Least Frequent, Keep Most Frequent
```
Problem: Find longest substring after ≤ k replacements

Critical observation:
  To make all characters the same in a window:
    - Keep the MOST frequent character
    - Replace all OTHER characters with it
  
  Number of replacements needed = windowLength - maxFrequency
  
Valid window condition:
  (windowLength - maxFrequency) ≤ k
  
Example: Window "AAAB"
  Length = 4
  Most frequent = 'A' (appears 3 times)
  Replacements needed = 4 - 3 = 1
  If k ≥ 1, window is valid!
  Replace 'B' with 'A' → "AAAA"
```

**Why This Works?**
```
Mathematical proof:
  For a window of length L with max frequency F:
    - We have F characters of the most frequent type
    - We have (L - F) characters of other types
    - To make all characters same, replace (L - F) characters
  
  Window is valid if: L - F ≤ k
  
Example: s = "AABBA", k = 1
  
  Window "AAB": L=3, F=2 (A), replacements=1 ≤ 1 ✓
  Window "AABB": L=4, F=2 (A or B), replacements=2 > 1 ✗ (invalid!)
  
  Must shrink window when replacements exceed k
```

**The Sliding Window Strategy**:
```
Maintain a window [left...right] where:
  (right - left + 1) - maxFrequency ≤ k

Two pointers:
  right: expands window (add characters)
  left: shrinks window (remove characters when invalid)

Frequency tracking:
  HashMap or array to count characters in window
  Track maxFrequency seen in current window

When to shrink:
  If replacements > k, move left pointer
  Update frequency count and maxFrequency
```

**Critical Detail**: MaxFrequency Optimization
```
Do we need to decrease maxFrequency when left moves?

Two approaches:

1. STRICT: Always track exact maxFrequency
   - Recalculate maxFreq after removing character
   - More accurate but slightly more work

2. LAZY: Keep maxFrequency as maximum ever seen
   - Never decrease maxFrequency
   - Why it works: We only care about finding max window size
   - If maxFreq decreases, window won't grow (which is fine!)
   
Most implementations use LAZY approach (simpler, same result)
```

**Related Patterns**:
1. **Sliding Window** — Variable window size
2. **Frequency Tracking** — HashMap or array
3. **Two Pointers** — Left and right boundaries
4. **Greedy Choice** — Keep most frequent character

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Try all substrings
  for i = 0 to n:
    for j = i to n:
      Count frequencies in substring[i..j]
      Find maxFrequency
      If (j - i + 1) - maxFreq ≤ k:
        Update max length
  
  → O(n²) time with O(n) frequency counting
  → O(n³) total (or O(n²) optimized)
  → Too slow for n=10,000!

Sliding Window:
  Maintain window with frequency counts
  Expand right: add character, update frequency
  Shrink left: when replacements > k
  Track: maximum valid window size
  → O(n) time, single pass!
```

**The Sliding Window Strategy**:
```
Key observation:
  We only care about the LONGEST valid window
  Don't need to check all possible windows
  
Algorithm:
  1. Expand window by moving right
  2. Update frequency of new character
  3. Update maxFrequency in window
  4. Check if window is valid:
     replacements = windowLength - maxFrequency
     if replacements > k: shrink from left
  5. Track maximum window size
  
Valid window invariant:
  (right - left + 1) - maxFreq ≤ k
```

### Step-by-Step Algorithm

---

#### **Approach 1: Sliding Window + HashMap (OPTIMAL)**

**Core Idea**:
- Use two pointers (left, right) for window
- HashMap tracks frequency of characters in window
- Track maxFrequency in current window
- Expand right, shrink left when replacements > k

**Algorithm**
```
characterReplacement(s, k):
    left = 0
    maxLength = 0
    maxFreq = 0
    freq = HashMap()
    
    for right from 0 to s.length - 1:
        // Add character to window
        char = s[right]
        freq[char]++
        maxFreq = max(maxFreq, freq[char])
        
        // Check if window is valid
        windowLength = right - left + 1
        replacements = windowLength - maxFreq
        
        if replacements > k:
            // Shrink window from left
            freq[s[left]]--
            left++
        
        // Update maximum length
        maxLength = max(maxLength, right - left + 1)
    
    return maxLength
```

**Code Implementation**
```java
class Solution {
    public int characterReplacement(String s, int k) {
        Map<Character, Integer> freq = new HashMap<>();
        int left = 0;
        int maxLength = 0;
        int maxFreq = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            
            // Add character to window and update frequency
            freq.put(rightChar, freq.getOrDefault(rightChar, 0) + 1);
            
            // Update max frequency in current window
            maxFreq = Math.max(maxFreq, freq.get(rightChar));
            
            // Check if window is valid
            int windowLength = right - left + 1;
            int replacements = windowLength - maxFreq;
            
            if (replacements > k) {
                // Shrink window from left
                char leftChar = s.charAt(left);
                freq.put(leftChar, freq.get(leftChar) - 1);
                left++;
            }
            
            // Update maximum length (window is now valid)
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}
```

**Example Walkthrough**

Input: `s = "AABABBA", k = 1`

| Step | right | char | left | freq | maxFreq | windowLen | replacements | Valid? | maxLength |
|------|-------|------|------|------|---------|-----------|--------------|--------|-----------|
| Init | - | - | 0 | {} | 0 | 0 | 0 | - | 0 |
| 1 | 0 | 'A' | 0 | {A:1} | 1 | 1 | 0 | ✓ | 1 |
| 2 | 1 | 'A' | 0 | {A:2} | 2 | 2 | 0 | ✓ | 2 |
| 3 | 2 | 'B' | 0 | {A:2,B:1} | 2 | 3 | 1 | ✓ | 3 |
| 4 | 3 | 'A' | 0 | {A:3,B:1} | 3 | 4 | 1 | ✓ | 4 |
| 5 | 4 | 'B' | 0 | {A:3,B:2} | 3 | 5 | 2 | ✗ (>k) | 4 |
| 5' | 4 | - | 0→1 | {A:2,B:2} | 3 | 4 | 1 | ✓ | 4 |
| 6 | 5 | 'B' | 1 | {A:2,B:3} | 3 | 5 | 2 | ✗ (>k) | 4 |
| 6' | 5 | - | 1→2 | {A:1,B:3} | 3 | 4 | 1 | ✓ | 4 |
| 7 | 6 | 'A' | 2 | {A:2,B:3} | 3 | 5 | 2 | ✗ (>k) | 4 |
| 7' | 6 | - | 2→3 | {A:2,B:2} | 3 | 4 | 1 | ✓ | 4 |

**Output:** `4`

**Complexity Analysis**
- **Time Complexity**: O(n) — Each character visited at most twice
- **Space Complexity**: O(m) — HashMap for character frequencies, m = 26 for uppercase

---

#### **Approach 2: Sliding Window + Array (OPTIMIZED)**

**Core Idea**: Use array instead of HashMap for faster access.

**Code Implementation**
```java
class Solution {
    public int characterReplacement(String s, int k) {
        int[] freq = new int[26]; // For uppercase English letters
        int left = 0;
        int maxLength = 0;
        int maxFreq = 0;
        
        for (int right = 0; right < s.length(); right++) {
            // Add character to window
            int rightIndex = s.charAt(right) - 'A';
            freq[rightIndex]++;
            
            // Update max frequency
            maxFreq = Math.max(maxFreq, freq[rightIndex]);
            
            // Check if window is valid
            int windowLength = right - left + 1;
            
            if (windowLength - maxFreq > k) {
                // Shrink window
                int leftIndex = s.charAt(left) - 'A';
                freq[leftIndex]--;
                left++;
            }
            
            // Update maximum length
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}
```

**Key Difference**: 
- Array access O(1) vs HashMap O(1) but with overhead
- Slightly faster in practice
- Works only for fixed character set (26 uppercase letters)

**Complexity Analysis**
- **Time Complexity**: O(n) — Single pass
- **Space Complexity**: O(1) — Fixed array of size 26

---

#### **Approach 3: Sliding Window with While Loop (ALTERNATIVE)**

**Core Idea**: Use while loop to shrink window until valid.

**Code Implementation**
```java
class Solution {
    public int characterReplacement(String s, int k) {
        int[] freq = new int[26];
        int left = 0;
        int maxLength = 0;
        int maxFreq = 0;
        
        for (int right = 0; right < s.length(); right++) {
            int rightIndex = s.charAt(right) - 'A';
            freq[rightIndex]++;
            maxFreq = Math.max(maxFreq, freq[rightIndex]);
            
            // Shrink window while invalid
            while (right - left + 1 - maxFreq > k) {
                int leftIndex = s.charAt(left) - 'A';
                freq[leftIndex]--;
                left++;
                // Optionally recalculate maxFreq (strict approach)
                // maxFreq = Arrays.stream(freq).max().getAsInt();
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
}
```

**Note**: Using while loop allows multiple shrinks in one iteration.

**Complexity Analysis**
- **Time Complexity**: O(n) — Each character visited at most twice
- **Space Complexity**: O(1) — Fixed array

---

#### **Approach 4: Brute Force (NOT OPTIMAL)**

**Core Idea**: Try all substrings and check validity.

**Code Implementation**
```java
class Solution {
    public int characterReplacement(String s, int k) {
        int maxLength = 0;
        
        for (int i = 0; i < s.length(); i++) {
            int[] freq = new int[26];
            int maxFreq = 0;
            
            for (int j = i; j < s.length(); j++) {
                int index = s.charAt(j) - 'A';
                freq[index]++;
                maxFreq = Math.max(maxFreq, freq[index]);
                
                int windowLength = j - i + 1;
                int replacements = windowLength - maxFreq;
                
                if (replacements <= k) {
                    maxLength = Math.max(maxLength, windowLength);
                }
            }
        }
        
        return maxLength;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n²) — Nested loops
- **Space Complexity**: O(1) — Fixed array
- **Why Not Optimal**: Too slow for large inputs

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | Sliding Window (HashMap) | **Sliding Window (Array)** |
|-------------|-------------|--------------------------|----------------------------|
| Time complexity | O(n²) ❌ | O(n) ✓ | **O(n) ✅** |
| Space complexity | O(1) ✓ | O(26) ✓ | **O(1) ✅** |
| Code simplicity | Simple | **Clean ✅** | Clean |
| Optimal | ❌ | ✅ | **✅ (fastest)** |

**Winner**: **Sliding Window + Array** for speed, **HashMap** for flexibility

### Why (WindowLength - MaxFreq) ≤ k Works?

```
Mathematical reasoning:

Window contains:
  - F characters of most frequent type (maxFreq)
  - (WindowLength - F) characters of other types
  
To make all characters same:
  Option 1: Convert all to most frequent type
    Replacements needed = WindowLength - F
    
  Option 2: Convert all to some other type
    Replacements needed > WindowLength - F
    (because we'd replace the F frequent chars too)
  
Optimal strategy: Always keep most frequent, replace rest
  → Minimizes replacements
  → Window valid if replacements ≤ k
```

### Why Lazy MaxFreq Works?

```
Question: When we shrink window (move left), should we recalculate maxFreq?

Strict approach:
  After removing character from left:
    maxFreq = max frequency in current window
    Requires scanning all frequencies: O(26) = O(1)
  
Lazy approach:
  Never decrease maxFreq
  Only update when we see higher frequency
  
Why lazy works:
  We only care about finding MAXIMUM window size
  
  If actual maxFreq decreases after shrinking:
    - Window won't grow larger than previous best
    - We're still tracking the maximum correctly
    - Future windows can only grow if they beat current max
  
  If actual maxFreq stays same or increases:
    - Everything works normally
  
Result: Lazy approach gives same answer with simpler code!

Example: s = "AABAA", k = 0
  Window "AAB": maxFreq=2
  Shrink to "AB": actual maxFreq=1, but we keep 2
  This is fine! Window size = 2, and we correctly won't grow it
  because (2 - 2) = 0 ≤ k, but next char 'A' makes window "ABA"
  which needs (3 - 2) = 1 > 0, so we shrink again
```

---

## Critical Edge Cases & Gotchas

### 1. **k = 0 (No Replacements)**
```java
Input: s = "AABCCBB", k = 0
Output: 2
Explanation: Can't replace anything. Longest substring is "AA" or "CC" or "BB".
```

### 2. **k ≥ s.length (Replace Everything)**
```java
Input: s = "ABCD", k = 4
Output: 4
Explanation: Can replace all characters. Entire string becomes one character.
```

### 3. **All Same Characters**
```java
Input: s = "AAAA", k = 2
Output: 4
Explanation: Already all same. No replacements needed.
```

### 4. **Single Character**
```java
Input: s = "A", k = 0
Output: 1
Explanation: One character, length = 1.
```

### 5. **Alternating Characters**
```java
Input: s = "ABABAB", k = 2
Output: 5
Explanation: Replace 2 'B's to get "AAAABA". Substring "AAAAA" has length 5.
```

### 6. **k = s.length (Edge Case)**
```java
Input: s = "ABCDEFG", k = 7
Output: 7
Explanation: Can replace everything to make all characters same.
```

### 7. **Consecutive Same Characters**
```java
Input: s = "AAABBBCCC", k = 2
Output: 5
Explanation: "AAABB" with 2 replacements (BB→AA) gives "AAAAA".
```

### 8. **All Different Characters**
```java
Input: s = "ABCDE", k = 1
Output: 2
Explanation: Replace one character to get 2 consecutive same characters.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Updating MaxFrequency**
```java
// WRONG - never updates maxFreq
int maxFreq = 0;  // Initialized once

for (int right = 0; right < s.length(); right++) {
    freq[s.charAt(right) - 'A']++;
    // Missing: maxFreq = Math.max(maxFreq, freq[s.charAt(right) - 'A']);
    
    if (right - left + 1 - maxFreq > k) {
        // Using stale maxFreq!
    }
}
```

**Why wrong**: maxFreq stays 0, so condition always evaluates incorrectly!

**Dry run failure for s="AABB", k=1:**
```
Step 1: right=0, 'A' → freq[A]=1, maxFreq=0 (WRONG! Should be 1)
  windowLen=1, replacements=1-0=1 ≤ 1 ✓
  
Step 2: right=1, 'A' → freq[A]=2, maxFreq=0 (WRONG! Should be 2)
  windowLen=2, replacements=2-0=2 > 1 ✗ (should be valid!)
  Shrinks incorrectly!
```

**Fix**: Update maxFreq
```java
maxFreq = Math.max(maxFreq, freq[rightChar]);
```

### ❌ **MISTAKE 2: Wrong Replacement Calculation**
```java
// WRONG - calculates replacements incorrectly
int replacements = maxFreq - windowLength;  // WRONG! Backwards!
```

**Why wrong**: Formula is inverted!

**Correct formula**:
```java
int replacements = windowLength - maxFreq;
```

### ❌ **MISTAKE 3: Using While Loop Without Recalculating MaxFreq**
```java
// POTENTIALLY WRONG - may shrink too much
while (right - left + 1 - maxFreq > k) {
    freq[s.charAt(left) - 'A']--;
    left++;
    // Should we recalculate maxFreq here?
}
```

**Why potentially wrong**: If the character being removed was the most frequent, maxFreq becomes stale.

**Two solutions**:
1. **Lazy**: Don't recalculate (works for finding max length)
2. **Strict**: Recalculate maxFreq after each removal

**For this problem, lazy works!**

### ❌ **MISTAKE 4: Checking Condition Before Adding Character**
```java
// WRONG - checks before adding new character
for (int right = 0; right < s.length(); right++) {
    // Check condition first (WRONG!)
    if (right - left + 1 - maxFreq > k) {
        freq[s.charAt(left) - 'A']--;
        left++;
    }
    
    // Add character after checking (WRONG!)
    freq[s.charAt(right) - 'A']++;
    maxFreq = Math.max(maxFreq, freq[s.charAt(right) - 'A']);
}
```

**Why wrong**: Checking condition with old state, not including new character!

**Fix**: Add character first, then check
```java
// Add new character first
freq[s.charAt(right) - 'A']++;
maxFreq = Math.max(maxFreq, freq[s.charAt(right) - 'A']);

// Then check condition
if (right - left + 1 - maxFreq > k) {
    // Shrink
}
```

### ❌ **MISTAKE 5: Not Handling k = 0**
```java
// Code might work, but edge case worth testing
Input: s = "AABAA", k = 0
Expected: 2 (substring "AA")

// Algorithm should handle this naturally
// but worth verifying in tests
```

**Why important**: k=0 means no replacements allowed, so window must have all same characters.

### ❌ **MISTAKE 6: Off-by-One in Window Length**
```java
// WRONG - incorrect window length calculation
int windowLength = right - left;  // WRONG! Should be +1
```

**Why wrong**: Window from left to right (inclusive) has length `right - left + 1`!

**Dry run failure for left=0, right=0:**
```
windowLength = 0 - 0 = 0 (WRONG! Should be 1)
```

**Fix**: Add 1
```java
int windowLength = right - left + 1;
```

### ❌ **MISTAKE 7: Decrementing Frequency Incorrectly**
```java
// WRONG - doesn't decrement when shrinking
if (replacements > k) {
    left++;  // WRONG! Forgot to update freq array
}
```

**Why wrong**: Frequency array still has old character count!

**Dry run failure:**
```
Window "AAB" → shrink left
left++ → Window now "AB"
But freq still shows {A:2, B:1} instead of {A:1, B:1}
maxFreq still 2 (incorrect!)
```

**Fix**: Decrement frequency
```java
if (replacements > k) {
    freq[s.charAt(left) - 'A']--;
    left++;
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Iterate through string | O(n) | Right pointer moves n times |
| Update frequency | O(1) | Array/HashMap update |
| Update maxFreq | O(1) | Comparison |
| Shrink window | O(1) amortized | Left pointer moves at most n times total |
| Update maxLength | O(1) | Comparison |
| **Total** | **O(n)** | Linear time |

**Why O(n)?**
- Right pointer: moves from 0 to n-1 → n moves
- Left pointer: moves from 0 to n-1 → at most n moves
- Total moves: 2n → O(n)

**Note**: Even with while loop for shrinking, total shrinks across all iterations ≤ n

### Space Complexity: **O(m)** where m = 26

| Component | Space | Reason |
|-----------|-------|--------|
| Frequency array/HashMap | O(26) = O(1) | Fixed size for uppercase letters |
| Other variables | O(1) | left, right, maxLength, maxFreq |
| **Total** | **O(1)** | Constant space |

**For uppercase English letters**: m = 26 → O(1) space

---

## Visualization

### Complete Example Walkthrough

**Input:** `s = "AABABBA", k = 1`

**Goal:** Find longest substring with at most 1 replacement.

---

**Initial State:**
```
left = 0, right = 0
freq = {}
maxFreq = 0
maxLength = 0
```

---

**Step 1: right=0, char='A'**
```
s = "AABABBA"
     ↑
     L,R

freq[A]++ → freq = {A:1}
maxFreq = max(0, 1) = 1
windowLength = 0-0+1 = 1
replacements = 1 - 1 = 0 ≤ 1 ✓
maxLength = 1

State: left=0, window="A"
```

---

**Step 2: right=1, char='A'**
```
s = "AABABBA"
     ↑ ↑
     L R

freq[A]++ → freq = {A:2}
maxFreq = max(1, 2) = 2
windowLength = 1-0+1 = 2
replacements = 2 - 2 = 0 ≤ 1 ✓
maxLength = 2

State: left=0, window="AA"
```

---

**Step 3: right=2, char='B'**
```
s = "AABABBA"
     ↑   ↑
     L   R

freq[B]++ → freq = {A:2, B:1}
maxFreq = max(2, 1) = 2
windowLength = 2-0+1 = 3
replacements = 3 - 2 = 1 ≤ 1 ✓
maxLength = 3

State: left=0, window="AAB"
Interpretation: Replace 'B' with 'A' → "AAA"
```

---

**Step 4: right=3, char='A'**
```
s = "AABABBA"
     ↑     ↑
     L     R

freq[A]++ → freq = {A:3, B:1}
maxFreq = max(2, 3) = 3
windowLength = 3-0+1 = 4
replacements = 4 - 3 = 1 ≤ 1 ✓
maxLength = 4 ✓

State: left=0, window="AABA"
Interpretation: Replace 'B' with 'A' → "AAAA"
```

---

**Step 5: right=4, char='B'**
```
s = "AABABBA"
     ↑       ↑
     L       R

freq[B]++ → freq = {A:3, B:2}
maxFreq = max(3, 2) = 3
windowLength = 4-0+1 = 5
replacements = 5 - 3 = 2 > 1 ✗ (INVALID!)

Shrink window:
  freq[A]-- → freq = {A:2, B:2}
  left++ → left = 1
  
After shrink:
  windowLength = 4-1+1 = 4
  replacements = 4 - 3 = 1 ≤ 1 ✓
  maxLength = 4

State: left=1, window="ABAB"
```

---

**Step 6: right=5, char='B'**
```
s = "AABABBA"
       ↑     ↑
       L     R

freq[B]++ → freq = {A:2, B:3}
maxFreq = max(3, 3) = 3
windowLength = 5-1+1 = 5
replacements = 5 - 3 = 2 > 1 ✗ (INVALID!)

Shrink window:
  freq[A]-- → freq = {A:1, B:3}
  left++ → left = 2
  
After shrink:
  windowLength = 5-2+1 = 4
  replacements = 4 - 3 = 1 ≤ 1 ✓
  maxLength = 4

State: left=2, window="BABB"
```

---

**Step 7: right=6, char='A'**
```
s = "AABABBA"
         ↑   ↑
         L   R

freq[A]++ → freq = {A:2, B:3}
maxFreq = max(3, 2) = 3
windowLength = 6-2+1 = 5
replacements = 5 - 3 = 2 > 1 ✗ (INVALID!)

Shrink window:
  freq[B]-- → freq = {A:2, B:2}
  left++ → left = 3
  
After shrink:
  windowLength = 6-3+1 = 4
  replacements = 4 - 3 = 1 ≤ 1 ✓
  maxLength = 4

State: left=3, window="ABBA"
```

---

**Final Result:** `maxLength = 4`

**Optimal Substring:** "AABA" → replace 'B' → "AAAA"

### Visual Summary

```
s = "AABABBA", k = 1

Valid windows explored:
  "A"    → len=1, maxFreq=1, replace=0
  "AA"   → len=2, maxFreq=2, replace=0
  "AAB"  → len=3, maxFreq=2, replace=1 ✓
  "AABA" → len=4, maxFreq=3, replace=1 ✓✓✓ (maximum!)
  
Maximum length = 4

Strategy: Replace the 'B' in "AABA" with 'A' to get "AAAA"
```

---

## Comparison of Approaches

| Approach | Time | Space | Optimal | Notes |
|----------|------|-------|---------|-------|
| Brute Force | O(n²) | O(1) | ❌ | Try all substrings |
| Sliding Window + HashMap | O(n) | O(26) | ✅ | Clean, flexible |
| **Sliding Window + Array** | **O(n)** | **O(1)** | **✅** | **Fastest** |
| Sliding Window + While | O(n) | O(1) | ✅ | Multiple shrinks |

**Recommendation**: Use **Sliding Window + Array** for best performance

---

## Key Takeaways

1. **Window valid when (windowLength - maxFreq) ≤ k** — core condition
2. **Replace minority, keep majority** — optimal strategy
3. **Track max frequency in window** — determines replacements needed
4. **Lazy maxFreq works** — never need to decrease it
5. **Expand right, shrink left when invalid** — standard sliding window
6. **Each character visited at most twice** — O(n) time
7. **Array faster than HashMap** — for fixed character set

---

## Interview Tips

**What to say in an interview:**

> "This is a sliding window problem with a frequency constraint. The key insight is that for any window, to make all characters the same, we should keep the most frequent character and replace all others. So the number of replacements needed is windowLength minus maxFrequency. I'll use two pointers to maintain a window where this replacement count doesn't exceed k. As I expand the window with the right pointer, I'll track character frequencies and the maximum frequency seen. When replacements exceed k, I'll shrink from the left. I'll track the maximum valid window size throughout. This gives O(n) time with a single pass and O(1) space using an array for the 26 uppercase letters."

**Key points to mention:**
1. **Core formula** — replacements = windowLength - maxFreq
2. **Window validity** — replacements ≤ k
3. **Frequency tracking** — array or HashMap
4. **Expand and shrink** — right expands, left shrinks when invalid
5. **Complexity** — O(n) time, O(1) space

**If asked about optimizations:**
> "I'm using a lazy approach for maxFrequency where I never decrease it. This works because we only care about finding the maximum window size, not all valid windows. If the actual max frequency decreases after shrinking, the window won't grow larger than our current best anyway, so we still find the correct answer."

**Common Follow-ups:**
- "Why don't you decrease maxFreq when shrinking?" → Lazy approach explained above
- "What if characters are lowercase too?" → Increase array size to 52 or use HashMap
- "What if we want at most k distinct characters?" → Different problem (similar sliding window)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Longest Repeating Character Replacement** | Medium | **Sliding Window + Frequency** | **This problem** |
| Longest Substring Without Repeating Characters | Medium | Sliding Window + HashSet | No replacements, all unique |
| Longest Substring with At Most K Distinct Characters | Medium | Sliding Window + HashMap | Track distinct chars, not replacements |
| Max Consecutive Ones III | Medium | Sliding Window | Replace 0s with 1s (binary version) |
| Minimum Window Substring | Hard | Sliding Window + HashMap | Find smallest window |
| Permutation in String | Medium | Sliding Window + Frequency | Fixed window size |

**Pattern Progression**:
1. **No replacements** — Basic sliding window with unique constraint
2. **With replacements** (this problem) — Track frequency, replace minority
3. **K distinct characters** — Track number of distinct types
4. **Binary version** — Simpler with only 0s and 1s

---

## Final Pattern Label

✅ **Sliding Window + Frequency Tracking (Variable Window with Replacement Budget)**

**Remember:** The window is valid when (windowLength - maxFrequency) ≤ k. This represents the minimum replacements needed to make all characters in the window identical. Expand the window by moving right pointer and updating frequencies. When invalid, shrink from left. Track maximum frequency lazily (never decrease it). This gives O(n) time and O(1) space for finding the longest valid window!
