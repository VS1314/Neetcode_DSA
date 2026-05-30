# Valid Palindrome
## Problem Description
**Difficulty**: Easy
Given a string `s`, return `true` if it is a **palindrome**, otherwise return `false`.
A palindrome is a string that reads the same **forward and backward**. It is also **case-insensitive** and **ignores all non-alphanumeric characters**.
> **Note**: Alphanumeric characters consist of letters (A-Z, a-z) and numbers (0-9).
## Examples
### Example 1:
```
Input: s = "Was it a car or a cat I saw?"
Output: true
Explanation: After keeping only alphanumeric characters -> "wasitacaroracatisaw"
             This reads the same forward and backward -> palindrome
```
### Example 2:
```
Input: s = "tab a cat"
Output: false
Explanation: After keeping only alphanumeric characters -> "tabacat"
             "tabacat" reversed is "tacabat" -> not equal -> false
```
### Example 3:
```
Input: s = " "
Output: true
Explanation: After removing non-alphanumeric characters -> "" (empty string)
             An empty string is a valid palindrome.
```
## Constraints
- 1 <= s.length <= 1000
- s is made up of only printable ASCII characters
---
## Pattern Recognition
**Primary Pattern**: **Two Pointers - Opposite Direction (Skip & Compare)**
**Why This Pattern?**
- Need to check if the string reads the same from both ends
- Must **skip non-alphanumeric characters** dynamically
- Two pointers from both ends, skipping invalid chars, comparing valid ones - O(1) space
**Key Insight - Why Two Pointers?**
```
A palindrome means: character at position i from start == character at position i from end.
Two pointers let us verify this without reversing the string:
  l=0 -> move right, skipping non-alphanumeric
  r=n-1 -> move left, skipping non-alphanumeric
  Compare s[l] and s[r] (case-insensitive)
  If they differ -> not a palindrome
  If l >= r -> all pairs matched -> palindrome
```
**Key Insight - Skip Logic:**
- If s[l] is not alphanumeric -> l++ (skip it)
- If s[r] is not alphanumeric -> r-- (skip it)
- Only compare when both s[l] and s[r] are alphanumeric
**Related Patterns**:
1. **Reverse String** - Two pointers from both ends (swap instead of compare)
2. **Valid Palindrome II** - Can delete at most one character
3. **Two Sum II** - Two pointers converging on sorted array
4. **3Sum** - Two pointers inside a loop
---
## Algorithm & Approach
### Core Insight
**Why Brute Force Fails the Space Requirement:**
```
Brute force: clean string -> reverse -> compare
  -> O(n) time, O(n) space (creates new cleaned string)
  -> Fails the O(1) space target
Two Pointers: skip non-alphanumeric on-the-fly, compare in-place
  -> O(n) time, O(1) space
```
**The Skip-and-Compare Idea:**
```
Original: "Was it a car or a cat I saw?"
Instead of cleaning the string first, use two pointers:
  l starts at 0     -> 'W' is alphanumeric -> wait to compare
  r starts at '?'  -> '?' is NOT alphanumeric -> skip (r--)
  r is now 'w'     -> compare 'W' (lowercased 'w') == 'w'
  ... continue inward
```
### Visual Understanding
```
s = "Was it a car or a cat I saw?"
     0                           27
     l=0                         r=27
l=0:  'W' is alphanum
r=27: '?' is NOT alphanum -> r-- -> r=26
l=0:  'W' alphanum
r=26: 'w' alphanum -> compare 'w'=='w' -> l++, r--
l=1:  ' ' NOT alphanum -> l++
l=2:  'a' alphanum
r=25: 'a' alphanum -> compare 'a'=='a' -> l++, r--
... (spaces and punctuation skipped automatically)
Eventually l >= r -> return true
```
### Step-by-Step Algorithm
---
#### **Approach 1: Two Pointers In-Place - OPTIMAL**
**Core Idea**:
- l=0, r=s.length()-1
- If s[l] not alphanumeric -> l++, continue
- If s[r] not alphanumeric -> r--, continue
- Both alphanumeric -> compare lowercase versions
  - Not equal -> return false
  - Equal -> l++, r--
- Loop ends when l >= r -> return true
**Algorithm**
```
isPalindrome(String s):
    l = 0
    r = s.length() - 1
    while l < r:
        if s[l] not alphanumeric: l++
        else if s[r] not alphanumeric: r--
        else if lowercase(s[l]) != lowercase(s[r]): return false
        else: l++, r--
    return true
```
**Code Implementation**
```java
class Solution {
    public boolean isPalindrome(String s) {
        int l = 0;
        int r = s.length() - 1;
        while (l < r) {
            if (!Character.isLetterOrDigit(s.charAt(l))) l++;
            else if (!Character.isLetterOrDigit(s.charAt(r))) r--;
            else if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r)))
                return false;
            else {
                l++;
                r--;
            }
        }
        return true;
    }
}
```
**Example Walkthrough**

Input: `s = "Was it a car or a cat I saw?"`

| Step | l | r | s[l] | s[r] | Action |
|------|---|---|------|------|--------|
| 1    | 0 | 27 | 'W' | '?' | '?' not alphanum -> r-- |
| 2    | 0 | 26 | 'W' | 'w' | compare 'w'=='w' -> l++, r-- |
| 3    | 1 | 25 | ' ' | 'a' | ' ' not alphanum -> l++ |
| 4    | 2 | 25 | 'a' | 'a' | compare 'a'=='a' -> l++, r-- |
| 5    | 3 | 24 | 's' | 's' | compare 's'=='s' -> l++, r-- |
| ...  | ... | ... | ... | ... | (spaces/punctuation skipped) |
| End  | l >= r | - | - | - | return true |
**Complexity Analysis**
- **Time Complexity**: O(n) - each character visited at most once
- **Space Complexity**: O(1) - only l, r pointer variables
---
#### **Approach 2: Clean String Then Compare - ALTERNATIVE**
**Core Idea**: Build a cleaned lowercase alphanumeric string, then compare it with its reverse.
**Code Implementation**
```java
class Solution {
    public boolean isPalindrome(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }
        String cleaned = sb.toString();
        String reversed = sb.reverse().toString();
        return cleaned.equals(reversed);
    }
}
```
**Complexity Analysis**
- **Time Complexity**: O(n) - one pass to clean + one pass to compare
- **Space Complexity**: O(n) - cleaned string stored in StringBuilder
  - Does NOT satisfy the O(1) space target
---
## Why This Strategy?
### Problem Requirements Analysis
| Requirement | Brute Force (Reverse) | Clean + Compare | Two Pointers |
|-------------|----------------------|-----------------|--------------|
| Time complexity | O(n) | O(n) | O(n) |
| Space complexity | O(n) | O(n) | O(1) |
| Handles skip in-place | No | No | Yes |
| Follow-up satisfied | No | No | Yes |
**Winner**: **Two Pointers** - only approach meeting O(n) time and O(1) space simultaneously.
### Why else-if order in the two-pointer loop matters:
```
Priority order:
  1. Skip invalid s[l]  (check left first)
  2. Skip invalid s[r]  (check right second)
  3. Both valid -> compare
If we used separate if blocks instead of else-if:
  Both pointers could advance in the same iteration,
  then the comparison runs on unvalidated characters.
The else-if chain ensures exactly one action per iteration.
```
---
## Critical Edge Cases & Gotchas
### 1. **Empty string after cleaning**
```java
Input: s = " "
Cleaned: ""  (only a space - not alphanumeric)
l=0, r=0 -> l >= r immediately -> return true
An empty string is a valid palindrome.
```
### 2. **Single alphanumeric character**
```java
Input: s = "a"
l=0, r=0 -> l >= r immediately -> return true
```
### 3. **All non-alphanumeric**
```java
Input: s = "!@#$%"
All characters skipped, l and r converge without comparing -> return true
```
### 4. **Mixed case**
```java
Input: s = "RaceCar"
Cleaned: "racecar" -> palindrome
Must compare lowercase versions: 'R'->'r' == 'r'
```
### 5. **Numbers in string**
```java
Input: s = "A1B2B1A"
Cleaned: "a1b2b1a" -> palindrome
Numbers are alphanumeric and must be compared too.
```
---
## Major Areas Where We Might Go Wrong
### MISTAKE 1: Not skipping non-alphanumeric characters
```java
// WRONG - compares spaces and punctuation directly
while (l < r) {
    if (s.charAt(l) != s.charAt(r)) return false;
    l++; r--;
}
```
**Why wrong**: Spaces and punctuation would be compared directly.
**Dry run failure for "a,a":**
```
l=0: 'a', r=2: 'a' -> match -> l++, r--
l=1: ',', r=1: ',' -> ',' compared with ',' -> match (accident!)
But this returns true when "a,a" should also be true...
For "a,b":
l=0: 'a', r=2: 'b' -> 'a' != 'b' -> return false
But "ab" is not a palindrome so this accidentally works...
For "ab,a":
l=0: 'a', r=3: 'a' -> match
l=1: 'b', r=2: ',' -> 'b' != ',' -> return false  (WRONG!)
But cleaned = "aba" which IS a palindrome -> should return true
```
**Fix**: Always check isLetterOrDigit before comparing.
### MISTAKE 2: Forgetting case-insensitive comparison
```java
// WRONG - case-sensitive comparison
else if (s.charAt(l) != s.charAt(r)) return false;
```
**Why wrong**: 'W' and 'w' are equal letters but 'W' != 'w' in direct char comparison.
**Dry run failure for "Was it a car or a cat I saw?":**
```
l=0: 'W', r=26: 'w'
'W' != 'w' -> return false   (but string IS a palindrome!)
```
**Fix**:
```java
Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))
```
### MISTAKE 3: Using separate if blocks instead of else-if
```java
// WRONG - using separate if blocks
if (!Character.isLetterOrDigit(s.charAt(l))) l++;
if (!Character.isLetterOrDigit(s.charAt(r))) r--;
if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) return false;
else { l++; r--; }
```
**Why wrong**: Both pointers can advance in the same iteration when both are non-alphanumeric, then the third if immediately compares new characters without verifying they are alphanumeric.
**Fix**: Strict else-if chain - only one action per iteration:
```java
if (!isLetterOrDigit(s[l])) l++;
else if (!isLetterOrDigit(s[r])) r--;
else if (lower(s[l]) != lower(s[r])) return false;
else { l++; r--; }
```
### MISTAKE 4: Wrong loop bound - using <= instead of <
```java
// WRONG
while (l <= r) { ... }
```
**Why wrong**: When l == r (middle character of odd-length string), it compares a character against itself. This is harmless but when combined with the skip logic, l can advance past r and create an invalid comparison in the next iteration.
**Fix**:
```java
while (l < r) { ... }  // middle character needs no comparison
```
### MISTAKE 5: Not returning true at the end
```java
// WRONG - missing return true
public boolean isPalindrome(String s) {
    int l = 0, r = s.length() - 1;
    while (l < r) {
        ...
        else if (lower(s[l]) != lower(s[r])) return false;
        else { l++; r--; }
    }
    // missing: return true  <- compile error or wrong result
}
```
**Why wrong**: If the loop completes without returning false, the string IS a palindrome. Without return true, the method has no return value.
**Fix**: Always end with return true after the loop.
---
## Complexity Analysis
### Two Pointers Approach
**Time Complexity: O(n)**
| Operation | Time | Reason |
|-----------|------|--------|
| Each character visited | O(1) | At most once by l or r |
| Total iterations | O(n) | Both pointers together cover all n characters |
| **Total** | **O(n)** | Single pass through string |
**Space Complexity: O(1)**
| Component | Space | Reason |
|-----------|-------|--------|
| l, r | O(1) | Two integer pointer variables |
| No cleaned string | O(1) | Skip characters in-place |
| **Total** | **O(1)** | No extra data structure |
---
## Visualization
### Skipping Non-Alphanumeric Characters
**Input:** `s = "A man, a plan, a canal: Panama"`
```
Positions:
A   ' ' m  a  n  ,  ' ' a  ' ' p  l  a  n  ,  ' ' a  ' ' c  a  n  a  l  :  ' ' P  a  n  a  m  a
0    1  2  3  4  5   6  7   8  9  10 11 12 13  14 15  16 17 18 19 20 21 22  23 24 25 26 27 28 29
l=0:  'A' alphanum
r=29: 'a' alphanum -> compare 'a'=='a' -> l++, r--
l=1:  ' ' NOT alphanum -> l++
l=2:  'm' alphanum
r=28: 'm' alphanum -> compare 'm'=='m' -> l++, r--
l=3:  'a' alphanum
r=27: 'a' alphanum -> compare 'a'=='a' -> l++, r--
l=4:  'n' alphanum
r=26: 'n' alphanum -> compare 'n'=='n' -> l++, r--
l=5:  ',' NOT alphanum -> l++
l=6:  ' ' NOT alphanum -> l++
l=7:  'a' alphanum
r=25: 'a' alphanum -> compare 'a'=='a' -> l++, r--
... (commas, spaces, colons all skipped automatically)
Eventually l >= r -> return true
```
### Why In-Place Skipping is Correct
```
Key property:
  Non-alphanumeric characters are "invisible" to the palindrome check.
  Advancing l or r past them doesn't skip any valid comparison -
  it just moves the pointer to the next relevant character.
Advancing l when s[l] is invalid:
  -> finds next valid character from left
Advancing r when s[r] is invalid:
  -> finds next valid character from right
Only then: compare the two valid characters.
```
---
## Comparison of Approaches
| Approach | Time | Space | In-Place | Follow-up | When to Use |
|----------|------|-------|----------|-----------|-------------|
| Brute Force (Reverse) | O(n) | O(n) | No | No | Never |
| Clean + Compare | O(n) | O(n) | No | No | Only for readability |
| **Two Pointers** | **O(n)** | **O(1)** | **Yes** | **Yes** | **Always - optimal** |
**Recommendation**: Use **Two Pointers** - skip non-alphanumeric in-place, compare case-insensitively.
---
## Key Takeaways
1. **Skip non-alphanumeric in-place** - use isLetterOrDigit to skip invalid chars without building a new string
2. **Case-insensitive comparison** - always convert to lowercase before comparing
3. **Strict else-if chain** - only one action per iteration: skip-l, skip-r, mismatch-return, or advance-both
4. **Loop condition is l < r** - middle character needs no comparison with itself
5. **Empty string is a palindrome** - when all chars are non-alphanumeric, loop never compares -> return true
6. **Numbers are alphanumeric** - isLetterOrDigit handles both letters AND digits correctly
7. **Always return true after loop** - loop exits only when all pairs matched
---
## Interview Tips
**What to say in an interview:**
> "I use two pointers from opposite ends. At each step, I skip non-alphanumeric characters by advancing the respective pointer. When both pointers point to alphanumeric characters, I compare them case-insensitively. If they differ, I return false. When the pointers meet or cross, all pairs matched and I return true. This runs in O(n) time and O(1) space."
**Key points to mention:**
1. **Why two pointers** - O(1) space; no need to clean the string first
2. **Skip logic** - check left pointer first, then right, then compare - strict else-if order
3. **Case-insensitive** - Character.toLowerCase() before comparing
4. **isLetterOrDigit** - handles both letters (a-z, A-Z) and numbers (0-9)
5. **Empty cleaned string** - still returns true (valid palindrome)
**If asked about the brute force:**
> "The brute force builds a cleaned string O(n) space and reverses it, then compares. It is simpler to implement but uses O(n) extra memory. The two-pointer approach avoids creating any extra string by skipping invalid characters in-place."
**Common Follow-ups:**
- "What if you could delete one character?" -> Valid Palindrome II - try skipping l or r when mismatch, check both substrings
- "What counts as alphanumeric?" -> Letters A-Z, a-z and digits 0-9 - Character.isLetterOrDigit() handles this
- "What about Unicode characters?" -> isLetterOrDigit in Java handles Unicode letters too
---
## Related Problems
| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Valid Palindrome** | Easy | **Two Pointers (skip & compare)** | **This problem** |
| Valid Palindrome II | Medium | Two Pointers + greedy skip | Can delete at most one character |
| Reverse String | Easy | Two Pointers (swap, not compare) | Modify in-place instead of comparing |
| Palindromic Substrings | Medium | Expand around center | Count all palindromic substrings |
| Longest Palindromic Substring | Medium | Expand around center / DP | Find longest palindrome |
**Pattern Progression**:
1. **Valid Palindrome** (this problem) - skip & compare from both ends, O(1) space
2. **Valid Palindrome II** - same setup but handle one allowed deletion
3. **Palindromic Substrings** - expand from center for all substrings
4. **Longest Palindromic Substring** - expand from center, track longest
---
## Final Pattern Label
**Two Pointers - Opposite Direction (Skip Non-Alphanumeric & Compare Case-Insensitive)**
**Remember:** Skip invalid chars with isLetterOrDigit, compare with toLowerCase, use strict else-if chain, loop while l < r. Return true after loop - loop exits only when all pairs matched!
