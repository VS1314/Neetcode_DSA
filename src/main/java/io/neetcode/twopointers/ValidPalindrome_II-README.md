# Valid Palindrome II
## Problem Description
**Difficulty**: Easy
Given a string `s`, return `true` if `s` can be a palindrome after deleting **at most one character** from it.
A palindrome is a string that reads the same forward and backward.
> **Note**: The string contains only lowercase English letters (no need to skip non-alphanumeric characters unlike Valid Palindrome I).
## Examples
### Example 1:
```
Input: s = "aca"
Output: true
Explanation: "aca" is already a palindrome - no deletion needed.
```
### Example 2:
```
Input: s = "abbadc"
Output: false
Explanation: "abbadc" is not a palindrome.
             Deleting 'd' -> "abbac" - not a palindrome.
             Deleting 'c' -> "abbad" - not a palindrome.
             No single deletion makes it a palindrome.
```
### Example 3:
```
Input: s = "abbda"
Output: true
Explanation: Delete 'd' -> "abba" which is a palindrome.
```
## Constraints
- 1 <= s.length <= 100,000
- s is made up of only lowercase English letters
---
## Pattern Recognition
**Primary Pattern**: **Two Pointers + Greedy Skip on Mismatch**
**Why This Pattern?**
- Start with same two-pointer approach as Valid Palindrome I
- When a mismatch is found, we have exactly one deletion to use
- Greedily try both options: skip left char OR skip right char
- Check if either resulting substring is a palindrome
**Key Insight - The One Deletion Decision:**
```
When s[l] != s[r], we MUST use our one allowed deletion here.
Two choices:
  1. Delete s[l] -> check if s[l+1..r] is a palindrome
  2. Delete s[r] -> check if s[l..r-1] is a palindrome
If EITHER is a palindrome -> return true
If NEITHER is -> return false (can't fix with one deletion)
We never need more than one mismatch check because:
  - If the string needs 2+ deletions -> false
  - If the string needs 0 or 1 deletion -> caught by this logic
```
**Key Insight - Why Greedy Works:**
```
At the first mismatch (l, r), one of s[l] or s[r] MUST be deleted.
There is no other option - we can't skip both.
So trying both and checking is exhaustive - it covers all cases.
```
**Related Patterns**:
1. **Valid Palindrome I** - Same two-pointer setup but no deletion allowed
2. **Reverse String** - Two pointers in-place swap
3. **Two Sum II** - Two pointers converging on sorted array
---
## Algorithm & Approach
### Core Insight
**Why Brute Force Fails:**
```
Brute force: try deleting each character one by one, check if palindrome
  -> O(n^2) time - too slow for n=100,000
Two Pointers + Greedy: find first mismatch, try both skips
  -> O(n) time, O(1) space
```
**The Greedy Decision Tree:**
```
Two pointers l=0, r=n-1 moving inward:
  s[l] == s[r]?
    YES -> advance both: l++, r--
    NO  -> use our one deletion:
           Option A: skip s[l] -> isPalindrome(s, l+1, r)?
           Option B: skip s[r] -> isPalindrome(s, l, r-1)?
           return A || B
If loop ends without mismatch -> already a palindrome -> return true
```
### Visual Understanding
```
s = "abbda"
     0 1 2 3 4
     l=0     r=4
Step 1: s[0]='a', s[4]='a' -> match -> l++, r--
        l=1, r=3
Step 2: s[1]='b', s[3]='d' -> MISMATCH!
        Use our one deletion:
        Option A: skip s[1] -> check "bda"[1..3] = s[2..3] = "da"... 
                  isPalindrome(s, 2, 3): 'b' vs 'd' -> false... 
                  Wait, let me redo:
        isPalindrome(s, l+1, r) = isPalindrome(s, 2, 3):
          s[2]='b', s[3]='d' -> mismatch -> false
        isPalindrome(s, l, r-1) = isPalindrome(s, 1, 2):
          s[1]='b', s[2]='b' -> match -> l=2, r=1 -> loop ends -> true!
        false || true -> return true
```
### Step-by-Step Algorithm
---
#### **Approach 1: Two Pointers + Greedy Skip - OPTIMAL**
**Core Idea**:
- Use two pointers l=0, r=s.length()-1
- If s[l]==s[r]: advance both (l++, r--)
- If s[l]!=s[r]: try both skips, return true if either works
- If loop completes: return true (already palindrome)
**Algorithm**
```
validPalindrome(String s):
    l = 0, r = s.length() - 1
    while l < r:
        if s[l] != s[r]:
            return isPalin(s, l+1, r) OR isPalin(s, l, r-1)
        l++, r--
    return true
isPalin(String s, int l, int r):
    while l < r:
        if s[l] != s[r]: return false
        l++, r--
    return true
```
**Code Implementation**
```java
class Solution {
    public boolean validPalindrome(String s) {
        int l = 0;
        int r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return isPalindrome(s, l + 1, r) || isPalindrome(s, l, r - 1);
            }
            l++;
            r--;
        }
        return true;
    }
    private boolean isPalindrome(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }
}
```
**Example Walkthrough - Example 3**

Input: `s = "abbda"`

| Step | l | r | s[l] | s[r] | Action |
|------|---|---|------|------|--------|
| 1    | 0 | 4 | 'a'  | 'a'  | match -> l++, r-- |
| 2    | 1 | 3 | 'b'  | 'd'  | MISMATCH -> try both skips |

**Option A:** `isPalindrome(s, 2, 3)` → s[2]='b', s[3]='d' → mismatch → **false**

**Option B:** `isPalindrome(s, 1, 2)` → s[1]='b', s[2]='b' → match → **true**

**Result:** false || true = **true**
**Example Walkthrough - Example 2**

Input: `s = "abbadc"`

| Step | l | r | s[l] | s[r] | Action |
|------|---|---|------|------|--------|
| 1    | 0 | 5 | 'a'  | 'c'  | MISMATCH -> try both skips |

**Option A:** `isPalindrome(s, 1, 5)` → "bbadc" → 'b'!='c' → **false**

**Option B:** `isPalindrome(s, 0, 4)` → "abbad" → 'a'!='d' → **false**

**Result:** false || false = **false**
**Complexity Analysis**
- **Time Complexity**: O(n) - main loop O(n) + at most one isPalindrome call O(n)
- **Space Complexity**: O(1) - only pointer variables, no extra space
---
#### **Approach 2: Brute Force - Try All Deletions**
**Core Idea**: Try deleting each character, check if result is a palindrome.
**Code Implementation**
```java
class Solution {
    public boolean validPalindrome(String s) {
        if (isPalin(s, 0, s.length() - 1)) return true;
        for (int i = 0; i < s.length(); i++) {
            String candidate = s.substring(0, i) + s.substring(i + 1);
            if (isPalin(candidate, 0, candidate.length() - 1)) return true;
        }
        return false;
    }
    private boolean isPalin(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) return false;
            l++; r--;
        }
        return true;
    }
}
```
**Complexity Analysis**
- **Time Complexity**: O(n^2) - n deletions each requiring O(n) palindrome check
- **Space Complexity**: O(n) - substring creation
  - Too slow for n=100,000
---
## Why This Strategy?
### Problem Requirements Analysis
| Requirement | Brute Force | Two Pointers + Greedy |
|-------------|-------------|----------------------|
| Time complexity | O(n^2) | O(n) |
| Space complexity | O(n) | O(1) |
| Handles n=100,000 | Too slow | Yes |
| Correct for all cases | Yes | Yes |
**Winner**: **Two Pointers + Greedy** - O(n) time, O(1) space, handles all cases.
### Why We Only Need to Check Two Options at the First Mismatch:
```
At mismatch (l, r):
  - s[l] and s[r] are different
  - To make the string a palindrome, one of them MUST be removed
  - We cannot remove a character from somewhere else and fix this pair
    (that would still leave s[l] != s[r] at the same positions)
  - So: remove s[l] OR remove s[r] - these are the only two choices
  - Check both - if either works -> true, else -> false
This is exhaustive. No other deletion can fix the first mismatch.
```
### Why the helper isPalindrome has no deletion:
```
After using our one allowed deletion (by skipping l or r),
we have ZERO deletions left.
So the helper checks if the remaining substring is EXACTLY a palindrome
with no more deletions allowed.
```
---
## Critical Edge Cases & Gotchas
### 1. **Already a palindrome - no deletion needed**
```java
Input: s = "aca"
l=0: 'a', r=2: 'a' -> match
l=1, r=1: l >= r -> loop ends -> return true
No mismatch encountered -> already a palindrome
```
### 2. **Single character**
```java
Input: s = "a"
l=0, r=0 -> l >= r immediately -> return true
```
### 3. **Two different characters**
```java
Input: s = "ab"
l=0: 'a', r=1: 'b' -> MISMATCH
isPalindrome(s, 1, 1) -> l>=r -> true (delete 'a', left with "b")
isPalindrome(s, 0, 0) -> l>=r -> true (delete 'b', left with "a")
true || true -> return true
```
### 4. **Two same characters**
```java
Input: s = "aa"
l=0: 'a', r=1: 'a' -> match -> l=1, r=0 -> loop ends -> return true
```
### 5. **Mismatch deep inside**
```java
Input: s = "abcbxa"
l=0: 'a', r=5: 'a' -> match
l=1: 'b', r=4: 'x' -> MISMATCH
isPalindrome(s, 2, 4): "cbx" -> 'c'!='x' -> false
isPalindrome(s, 1, 3): "bcb" -> 'b'=='b', 'c'=='c' -> true
false || true -> return true  (deleted 'x')
```
### 6. **Needs two deletions - impossible**
```java
Input: s = "abcde"
l=0: 'a', r=4: 'e' -> MISMATCH
isPalindrome(s, 1, 4): "bcde" -> 'b'!='e' -> false
isPalindrome(s, 0, 3): "abcd" -> 'a'!='d' -> false
false || false -> return false
```
---
## Major Areas Where We Might Go Wrong
### MISTAKE 1: Allowing a second deletion in the helper
```java
// WRONG - recursively calling validPalindrome instead of isPalindrome
if (s.charAt(l) != s.charAt(r)) {
    return validPalindrome(s.substring(l+1, r+1)) ||
           validPalindrome(s.substring(l, r));
}
```
**Why wrong**: `validPalindrome` allows another deletion. This would allow 2+ deletions, giving wrong answers.
**Dry run failure for "abcde":**
```
validPalindrome("abcde"):
  'a'!='e' -> try validPalindrome("bcde") || validPalindrome("abcd")
  validPalindrome("bcde"):
    'b'!='e' -> try validPalindrome("cde") || validPalindrome("bcd")
    ... (allows 2nd deletion)
  Could incorrectly return true by using 2 deletions
```
**Fix**: Use a separate `isPalindrome` helper that does NOT allow any deletion.
### MISTAKE 2: Not trying both skips - only trying one
```java
// WRONG - only tries skipping left
if (s.charAt(l) != s.charAt(r)) {
    return isPalindrome(s, l + 1, r);  // forgets to try r-1
}
```
**Why wrong**: The mismatch could be fixed by deleting the right character, not the left.
**Dry run failure for "abbda":**
```
l=1: 'b', r=3: 'd' -> MISMATCH
Only tries: isPalindrome(s, 2, 3) = "bd" -> false
Returns false  (WRONG - should be true by deleting 'd')
```
**Fix**: Always return `isPalindrome(s, l+1, r) || isPalindrome(s, l, r-1)`.
### MISTAKE 3: Not returning true when loop ends cleanly
```java
// WRONG - missing return true
public boolean validPalindrome(String s) {
    int l = 0, r = s.length() - 1;
    while (l < r) {
        if (s.charAt(l) != s.charAt(r)) {
            return isPalindrome(s, l+1, r) || isPalindrome(s, l, r-1);
        }
        l++; r--;
    }
    // missing: return true
}
```
**Why wrong**: If loop exits without mismatch, string is already a palindrome. Without `return true`, method has no return value -> compile error.
**Fix**: `return true` after the loop.
### MISTAKE 4: Using substring which creates O(n) space
```java
// WRONG - creates new strings
return isPalindrome(s.substring(l+1, r+1)) ||
       isPalindrome(s.substring(l, r));
```
**Why wrong**: `substring` creates new String objects -> O(n) extra space per call.
**Fix**: Pass index boundaries `l` and `r` to `isPalindrome` instead:
```java
return isPalindrome(s, l+1, r) || isPalindrome(s, l, r-1);
```
### MISTAKE 5: Off-by-one in isPalindrome range when skipping
```java
// WRONG - wrong end index when skipping left char
return isPalindrome(s, l + 1, r + 1);  // r+1 is out of bounds!
```
**Why wrong**: When we skip s[l] (the left char), we check s[l+1..r] - the right boundary stays at r, not r+1.
**Fix**:
```java
isPalindrome(s, l + 1, r)   // skip left: start moves right, end stays
isPalindrome(s, l, r - 1)   // skip right: start stays, end moves left
```
---
## Complexity Analysis
### Two Pointers + Greedy Approach
**Time Complexity: O(n)**
| Operation | Time | Reason |
|-----------|------|--------|
| Main two-pointer loop | O(n) | Each char visited at most once |
| isPalindrome helper (at most once) | O(n) | Another linear scan of substring |
| **Total** | **O(n)** | Two linear passes at most |
**Space Complexity: O(1)**
| Component | Space | Reason |
|-----------|-------|--------|
| l, r pointers | O(1) | Two integer variables |
| isPalindrome helper l, r | O(1) | Local variables, no recursion stack |
| No substring created | O(1) | Pass indices, not new strings |
| **Total** | **O(1)** | No extra data structure |
---
## Visualization
### How the Two Skips Cover All Cases
```
s = "a b b d a"
     0 1 2 3 4
Main loop:
  l=0, r=4: 'a'=='a' -> match, advance
  l=1, r=3: 'b'!='d' -> MISMATCH at positions (1, 3)
Two options:
Option A: Delete s[1]='b' -> check s[2..3] = "bd"
  isPalindrome(s, 2, 3):
    s[2]='b', s[3]='d' -> mismatch -> FALSE
Option B: Delete s[3]='d' -> check s[1..2] = "bb"
  isPalindrome(s, 1, 2):
    s[1]='b', s[2]='b' -> match -> l=2, r=1 -> loop ends -> TRUE
Result: FALSE || TRUE = TRUE
```
### Why We Stop at First Mismatch
```
The first mismatch is the only place we need to make the deletion decision.
Once s[l] != s[r], exactly ONE of them must go.
There is no benefit to looking further - any other deletion would still
leave this pair mismatched.
So: try skip-left, try skip-right, return OR of both results.
This is O(n) total - no backtracking, no trying all positions.
```
---
## Comparison of Approaches
| Approach | Time | Space | Handles n=100K | When to Use |
|----------|------|-------|----------------|-------------|
| Brute Force | O(n^2) | O(n) | Too slow | Never |
| **Two Pointers + Greedy** | **O(n)** | **O(1)** | **Yes** | **Always** |
---
## Key Takeaways
1. **First mismatch is the decision point** - when s[l]!=s[r], we must delete one of them
2. **Try both skips** - skip s[l] (check l+1..r) OR skip s[r] (check l..r-1)
3. **Helper has zero deletions** - after using our one deletion, the helper checks exact palindrome
4. **Return OR of both** - if either substring is a palindrome -> true
5. **Pass indices not substrings** - avoids O(n) space from substring creation
6. **Return true after loop** - loop exits cleanly means string is already a palindrome
7. **O(n) total** - main loop + at most one helper call = two linear passes
---
## Interview Tips
**What to say in an interview:**
> "I use two pointers from both ends. When the characters match, I advance both pointers. When they mismatch, I must use my one allowed deletion here - I try skipping the left character and check if the remaining substring is a palindrome, and I try skipping the right character and check the same. I return true if either check passes. This runs in O(n) time and O(1) space."
**Key points to mention:**
1. **Greedy decision** - first mismatch is where the deletion must happen
2. **Two options** - skip left (l+1..r) OR skip right (l..r-1)
3. **Separate helper** - helper checks exact palindrome with zero deletions remaining
4. **Return OR** - either skip working is sufficient
5. **Pass indices** - not substrings, to keep O(1) space
**Differences from Valid Palindrome I:**
> "Valid Palindrome I has no deletion - just skip non-alphanumeric and compare. Valid Palindrome II allows one deletion on a clean lowercase string - when we hit a mismatch, we try both possible single-character deletions and check if either produces a palindrome."
**Common Follow-ups:**
- "What if you could delete k characters?" -> Use a recursive/DP approach; greedy breaks for k>1
- "What is the time complexity?" -> O(n) - two linear passes at most
- "Why don't you need to try all possible deletions?" -> The first mismatch forces the deletion; only two choices exist there
---
## Related Problems
| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Valid Palindrome II** | Easy | **Two Pointers + Greedy Skip** | **This problem** |
| Valid Palindrome I | Easy | Two Pointers (skip non-alphanum) | No deletion allowed |
| Longest Palindromic Subsequence | Medium | DP | Find longest palindrome by removing chars |
| Palindrome Partitioning | Medium | Backtracking / DP | Split string into palindrome parts |
**Pattern Progression**:
1. **Valid Palindrome I** - check palindrome, skip non-alphanumeric, O(1) space
2. **Valid Palindrome II** (this problem) - allow one deletion, greedy at first mismatch
3. **Longest Palindromic Subsequence** - allow unlimited deletions, find longest palindrome
---
## Final Pattern Label
**Two Pointers + Greedy Skip on First Mismatch**
**Remember:** Same two-pointer loop as Valid Palindrome I. On first mismatch at (l, r): return isPalindrome(s, l+1, r) || isPalindrome(s, l, r-1). The helper checks exact palindrome - no more deletions allowed!
