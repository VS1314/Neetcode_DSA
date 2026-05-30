# Merge Strings Alternately

## Problem Description

**Difficulty**: Easy

You are given two strings, `word1` and `word2`. Construct a new string by merging them in alternating order, starting with `word1` — take one character from `word1`, then one from `word2`, and repeat this process.

If one string is longer than the other, append the remaining characters from the longer string to the end of the merged result.

Return the final merged string.

## Examples

### Example 1:
```
Input: word1 = "abc", word2 = "xyz"
Output: "axbycz"
Explanation: Merge alternately: 'a' from word1, 'x' from word2, 'b' from word1, 'y' from word2, 'c' from word1, 'z' from word2.
```

### Example 2:
```
Input: word1 = "ab", word2 = "abbxxc"
Output: "aabbbxxc"
Explanation: 
- 'a' from word1, 'a' from word2 → "aa"
- 'b' from word1, 'b' from word2 → "aabb"
- word1 is exhausted, append remaining "bxxc" from word2 → "aabbbxxc"
```

### Example 3:
```
Input: word1 = "hello", word2 = "wo"
Output: "hweolrllo"
Explanation:
- 'h' from word1, 'w' from word2 → "hw"
- 'e' from word1, 'o' from word2 → "hweo"
- word2 is exhausted, append remaining "llo" from word1 → "hweolllo"
```

## Constraints
- 1 <= word1.length, word2.length <= 100
- word1 and word2 consist of lowercase English letters

---

## Pattern Recognition

**Primary Pattern**: **Two Pointers - Parallel Traversal (Index-Based Merge)**

**Why This Pattern?**
- Need to process two strings simultaneously
- Alternating access pattern requires tracking position in both strings
- Two pointers (indices) allow independent progression through each string
- Natural fit for merging/interleaving elements from two sequences

**Key Insight**: Why Two Pointers?
```
To merge alternately, we need to:
  1. Track position in word1 (pointer i)
  2. Track position in word2 (pointer j)
  3. Alternate between taking from word1[i] and word2[j]
  4. Handle remaining characters when one string ends first

Two independent pointers let us control progression through each string separately.
```

**Related Patterns**:
1. **Merge Two Sorted Lists** — Two pointers merging based on comparison
2. **Merge Sorted Array** — Two pointers merging from both ends
3. **Interleaving String** — Two pointers with backtracking
4. **Zip Iterator** — Alternate between multiple sequences

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Works Here:**
```
Unlike optimization problems, this is a straightforward simulation:
  → Just follow the rules: alternate characters, append remainder
  → No need for complex algorithms
  → Single pass through both strings is optimal
```

**The Alternating Pattern:**
```
word1 = "abc"   (length 3)
word2 = "xyz"   (length 3)

Step-by-step merge:
  i=0, j=0 → take word1[0]='a' → result = "a"
  i=0, j=0 → take word2[0]='x' → result = "ax"
  i=1, j=1 → take word1[1]='b' → result = "axb"
  i=1, j=1 → take word2[1]='y' → result = "axby"
  i=2, j=2 → take word1[2]='c' → result = "axbyc"
  i=2, j=2 → take word2[2]='z' → result = "axbycz"
  Both exhausted → done!
```

### Step-by-Step Algorithm

---

#### **Approach 1: Two Pointers with StringBuilder (OPTIMAL)**

**Core Idea**:
- Use two pointers `i` and `j` for word1 and word2
- Build result using StringBuilder for efficiency
- Alternate adding characters while both strings have remaining characters
- Append leftover from the longer string at the end

**Algorithm**
```
mergeAlternately(word1, word2):
    result = new StringBuilder()
    i = 0, j = 0
    
    // Alternate while both have characters
    while i < word1.length AND j < word2.length:
        result.append(word1[i++])
        result.append(word2[j++])
    
    // Append remaining from word1 (if any)
    while i < word1.length:
        result.append(word1[i++])
    
    // Append remaining from word2 (if any)
    while j < word2.length:
        result.append(word2[j++])
    
    return result.toString()
```

**Code Implementation**
```java
class Solution {
    public String mergeAlternately(String word1, String word2) {
        StringBuilder result = new StringBuilder();
        int i = 0, j = 0;
        
        // Alternate characters while both strings have remaining characters
        while (i < word1.length() && j < word2.length()) {
            result.append(word1.charAt(i++));
            result.append(word2.charAt(j++));
        }
        
        // Append remaining characters from word1
        while (i < word1.length()) {
            result.append(word1.charAt(i++));
        }
        
        // Append remaining characters from word2
        while (j < word2.length()) {
            result.append(word2.charAt(j++));
        }
        
        return result.toString();
    }
}
```

**Example Walkthrough**

Input: `word1 = "ab"`, `word2 = "abbxxc"`

| Step | i | j | Action | result |
|------|---|---|--------|--------|
| Init | 0 | 0 | — | "" |
| 1 | 0 | 0 | Append word1[0]='a' | "a" |
| 2 | 1 | 0 | Append word2[0]='a' | "aa" |
| 3 | 1 | 1 | Append word1[1]='b' | "aab" |
| 4 | 2 | 1 | Append word2[1]='b' | "aabb" |
| 5 | 2 | 2 | i >= word1.length, exit first loop | "aabb" |
| 6 | 2 | 2 | Append word2[2]='b' | "aabbb" |
| 7 | 2 | 3 | Append word2[3]='x' | "aabbbx" |
| 8 | 2 | 4 | Append word2[4]='x' | "aabbbxx" |
| 9 | 2 | 5 | Append word2[5]='c' | "aabbbxxc" |
| End | 2 | 6 | j >= word2.length | **"aabbbxxc"** ✓ |

**Complexity Analysis**
- **Time Complexity**: O(m + n) where m = word1.length, n = word2.length
- **Space Complexity**: O(m + n) for the result string (output space, unavoidable)

---

#### **Approach 2: Single Pointer with Math.min (ALTERNATIVE)**

**Core Idea**: Use a single loop up to the minimum length, then append remainders.

**Code Implementation**
```java
class Solution {
    public String mergeAlternately(String word1, String word2) {
        StringBuilder result = new StringBuilder();
        int n1 = word1.length(), n2 = word2.length();
        int minLen = Math.min(n1, n2);
        
        // Alternate up to minimum length
        for (int i = 0; i < minLen; i++) {
            result.append(word1.charAt(i));
            result.append(word2.charAt(i));
        }
        
        // Append remaining from word1 or word2
        if (n1 > minLen) {
            result.append(word1.substring(minLen));
        } else if (n2 > minLen) {
            result.append(word2.substring(minLen));
        }
        
        return result.toString();
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(m + n)
- **Space Complexity**: O(m + n)

---

#### **Approach 3: Single Pointer Up to Max Length (ELEGANT)**

**Core Idea**: Use a single loop up to the maximum length, check bounds before accessing.

**Code Implementation**
```java
class Solution {
    public String mergeAlternately(String word1, String word2) {
        StringBuilder result = new StringBuilder();
        int n1 = word1.length(), n2 = word2.length();
        int maxLen = Math.max(n1, n2);
        
        for (int i = 0; i < maxLen; i++) {
            if (i < n1) {
                result.append(word1.charAt(i));
            }
            if (i < n2) {
                result.append(word2.charAt(i));
            }
        }
        
        return result.toString();
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(max(m, n))
- **Space Complexity**: O(m + n)

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Approach 1 (Two Pointers) | Approach 2 (Min Length) | Approach 3 (Max Length) |
|-------------|---------------------------|-------------------------|-------------------------|
| Time complexity | O(m + n) ✓ | O(m + n) ✓ | O(max(m, n)) ✓ |
| Space complexity | O(m + n) ✓ | O(m + n) ✓ | O(m + n) ✓ |
| Code clarity | Good | Better | ✅ **Best** |
| Edge case handling | Explicit | Mixed | Clean |

**Winner**: All approaches are optimal in complexity. Approach 3 is most elegant!

### Why O(m + n) Time is Optimal?
- Must read every character from both strings at least once
- Can't skip any characters
- Single pass achieves minimum possible time

### Why O(m + n) Space is Required?
- Output string must contain all characters from both inputs
- Result size = m + n characters
- This is output space, not auxiliary space (unavoidable)

---

## Critical Edge Cases & Gotchas

### 1. **Equal Length Strings**
```java
Input: word1 = "abc", word2 = "xyz"
Output: "axbycz"
Explanation: Perfect alternation with no remainder.
```

### 2. **word1 Longer**
```java
Input: word1 = "abcdef", word2 = "xy"
Output: "axybcdef"
Explanation: Alternate a,x,b,y then append "cdef".
```

### 3. **word2 Longer**
```java
Input: word1 = "ab", word2 = "xyzwpq"
Output: "axbyzwpq"
Explanation: Alternate a,x,b,y then append "zwpq".
```

### 4. **Single Character Each**
```java
Input: word1 = "a", word2 = "b"
Output: "ab"
```

### 5. **One String Empty (Not Possible per Constraints)**
```java
Constraints guarantee: 1 <= word1.length, word2.length
So empty string case doesn't exist.
```

### 6. **Minimum Length (Both Length 1)**
```java
Input: word1 = "x", word2 = "y"
Output: "xy"
```

### 7. **Maximum Length (Both Length 100)**
```java
Both strings at max length = 100 characters each
Output will be 200 characters long.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Using String Concatenation Instead of StringBuilder**
```java
// WRONG - O(n^2) time due to string immutability
String result = "";
for (int i = 0; i < word1.length(); i++) {
    result += word1.charAt(i);  // Creates new string every time!
}
```

**Why wrong**: Each `+=` creates a new String object, copying all previous characters. This leads to O(n²) time.

**Fix**: Use StringBuilder
```java
// CORRECT
StringBuilder result = new StringBuilder();
result.append(word1.charAt(i));  // O(1) amortized
```

### ❌ **MISTAKE 2: Forgetting to Append Remaining Characters**
```java
// WRONG - only alternates, loses remainder
while (i < word1.length() && j < word2.length()) {
    result.append(word1.charAt(i++));
    result.append(word2.charAt(j++));
}
// Missing: append remaining from longer string!
return result.toString();
```

**Why wrong**: When one string is longer, remaining characters are lost.

**Dry run failure for word1="ab", word2="xyz":**
```
Loop alternates: "axby"
word1 exhausted, but 'z' from word2 is never added
Output: "axby" (WRONG - should be "axbyz")
```

**Fix**: Add loops to append remainders
```java
while (i < word1.length()) result.append(word1.charAt(i++));
while (j < word2.length()) result.append(word2.charAt(j++));
```

### ❌ **MISTAKE 3: Off-by-One in Loop Condition**
```java
// WRONG - <= instead of <
while (i <= word1.length() && j <= word2.length()) {
    result.append(word1.charAt(i++));  // StringIndexOutOfBounds!
    result.append(word2.charAt(j++));
}
```

**Why wrong**: When i = word1.length(), charAt(i) is out of bounds!

**Fix**: Use `<` not `<=`
```java
while (i < word1.length() && j < word2.length()) { ... }
```

### ❌ **MISTAKE 4: Not Incrementing Pointers**
```java
// WRONG - infinite loop
while (i < word1.length() && j < word2.length()) {
    result.append(word1.charAt(i));  // Missing i++
    result.append(word2.charAt(j));  // Missing j++
}
```

**Why wrong**: i and j never change, condition is always true → infinite loop.

**Fix**: Increment after each use
```java
result.append(word1.charAt(i++));
result.append(word2.charAt(j++));
```

### ❌ **MISTAKE 5: Alternating in Wrong Order**
```java
// WRONG - starts with word2
while (i < word1.length() && j < word2.length()) {
    result.append(word2.charAt(j++));  // word2 first!
    result.append(word1.charAt(i++));
}
```

**Why wrong**: Problem says "starting with word1".

**Dry run failure for word1="ab", word2="xy":**
```
Output: "xayb" (WRONG - should be "axby")
```

**Fix**: Always append word1 character first
```java
result.append(word1.charAt(i++));  // word1 first
result.append(word2.charAt(j++));  // word2 second
```

---

## Complexity Analysis

### Time Complexity: **O(m + n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Alternating loop | O(min(m, n)) | Process shorter string completely |
| Append remainder | O(max(m, n) - min(m, n)) | Process remaining from longer string |
| StringBuilder operations | O(1) amortized | Each append is constant time amortized |
| **Total** | **O(m + n)** | Every character accessed exactly once |

### Space Complexity: **O(m + n)**

| Component | Space | Reason |
|-----------|-------|--------|
| StringBuilder/Result | O(m + n) | Stores all characters from both strings |
| Pointer variables i, j | O(1) | Two integer variables |
| **Total** | **O(m + n)** | Output space dominates |

**Note**: O(m + n) is output space (required), not auxiliary space. If we don't count output, space is O(1).

---

## Visualization

### Example Walkthrough

**Input:** `word1 = "abc"`, `word2 = "xyz"`

```
Step-by-step merge visualization:

word1: a  b  c
       ↓  ↓  ↓
       i=0 i=1 i=2

word2: x  y  z
       ↓  ↓  ↓
       j=0 j=1 j=2

Alternation Process:
  Step 1: Take word1[0]='a' → result = "a"      (i=0→1, j=0)
  Step 2: Take word2[0]='x' → result = "ax"     (i=1, j=0→1)
  Step 3: Take word1[1]='b' → result = "axb"    (i=1→2, j=1)
  Step 4: Take word2[1]='y' → result = "axby"   (i=2, j=1→2)
  Step 5: Take word1[2]='c' → result = "axbyc"  (i=2→3, j=2)
  Step 6: Take word2[2]='z' → result = "axbycz" (i=3, j=2→3)
  
  Both exhausted → DONE!
  
Output: "axbycz" ✓
```

### Unequal Length Example

**Input:** `word1 = "ab"`, `word2 = "pqrs"`

```
word1: a  b  (length 2)
       ↓  ↓
       i  i

word2: p  q  r  s  (length 4)
       ↓  ↓  ↓  ↓
       j  j  j  j

Alternation:
  Take 'a' → "a"
  Take 'p' → "ap"
  Take 'b' → "apb"
  Take 'q' → "apbq"
  word1 exhausted! Append remaining "rs" from word2
  
Output: "apbqrs" ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Lines | Clarity | When to Use |
|----------|------|-------|------------|---------|-------------|
| Two Pointers | O(m+n) | O(m+n) | ~15 | Good | Explicit control |
| Min Length Loop | O(m+n) | O(m+n) | ~12 | Better | Slightly cleaner |
| **Max Length Loop** | **O(m+n)** | **O(m+n)** | **~10** | ✅ **Best** | **Always - most elegant** |

**Recommendation**: Use **Max Length Loop** (Approach 3) for cleanest code!

---

## Key Takeaways

1. **Two pointers for parallel traversal** — i for word1, j for word2
2. **StringBuilder for efficiency** — avoid O(n²) string concatenation
3. **Alternate: word1 first, then word2** — follow problem specification
4. **Handle remainder** — append leftover characters from longer string
5. **O(m + n) is optimal** — must visit every character at least once
6. **Output space dominates** — O(m + n) space is required for result
7. **Three loop approach** — alternate, append word1 remainder, append word2 remainder

---

## Interview Tips

**What to say in an interview:**

> "I'll use two pointers to traverse both strings simultaneously. While both strings have characters remaining, I alternate adding one from word1 and one from word2 using a StringBuilder for efficiency. After the alternating phase, I append any remaining characters from whichever string is longer. This gives O(m + n) time and O(m + n) space for the output."

**Key points to mention:**
1. **Why StringBuilder** — String concatenation with `+=` is O(n²), StringBuilder is O(n)
2. **Two phases** — alternating phase + remainder phase
3. **Starting with word1** — problem specifies to start with word1
4. **Pointer management** — increment i and j independently as we consume characters
5. **Complexity** — O(m + n) time (optimal), O(m + n) output space (required)

**If asked about alternatives:**
> "I could use a single loop with Math.max and check bounds before each character access. This is more elegant but conceptually the same. Or I could use streams/iterators, but that would be less efficient and harder to read."

**Common Follow-ups:**
- "What if you need to merge k strings alternately?" → Use round-robin with k pointers
- "Can you do it without StringBuilder?" → Yes, but String concatenation is O(n²)
- "What if strings contain Unicode?" → charAt() handles Unicode correctly in Java

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Merge Strings Alternately** | Easy | **Two Pointers Parallel** | **This problem** ← |
| Merge Two Sorted Lists | Easy | Two Pointers (comparison-based) | Merge based on value comparison |
| Merge Sorted Array | Easy | Two Pointers (backward) | In-place merge from end |
| Interleaving String | Medium | Two Pointers + DP/BFS | Check if s3 is interleaving of s1, s2 |
| Shortest Word Distance | Easy | Single pass | Find minimum distance between words |

**Pattern Progression**:
1. **Merge Strings Alternately** (this problem) — Simple alternating merge
2. **Merge Two Sorted Lists** — Merge based on comparison
3. **Interleaving String** — Complex interleaving with validation
4. **Merge K Sorted Lists** — Merge multiple sequences with heap

---

## Final Pattern Label

✅ **Two Pointers – Parallel Traversal (Alternating Merge)**

**Remember:** Use two independent pointers (i, j) to traverse both strings. Alternate taking characters (word1 first), then append remainder from the longer string. StringBuilder is crucial for O(n) performance!
