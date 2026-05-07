# Valid Anagram

## Problem Description

**Difficulty**: Easy

Given two strings `s` and `t`, return `true` if the two strings are **anagrams** of each other, otherwise return `false`.

An **anagram** is a string that contains the **exact same characters** as another string, but the **order of the characters can be different**.

## Examples

### Example 1:
```
Input:  s = "racecar", t = "carrace"
Output: true
Explanation: Both strings contain: a×2, c×2, e×1, r×2 — same characters, same frequencies
```

### Example 2:
```
Input:  s = "jar", t = "jam"
Output: false
Explanation: s has 'r', t has 'm' — different characters
```

## Constraints
- `s` and `t` consist of **lowercase English letters** only
- Follow-up: What if inputs contain **Unicode characters**?

---

## Pattern Recognition

**Primary Pattern**: **Hashing — Frequency Count**

**Why This Pattern?**
- An anagram requires the **same characters at the same frequencies** — order doesn't matter
- We need to compare character **counts**, not positions
- A `HashMap` (or fixed-size array for lowercase letters) maps each character to its frequency in O(1) per lookup/insert

**Key Insight**:
```
Two strings are anagrams if and only if:
    length(s) == length(t)
    AND
    frequency(char) in s == frequency(char) in t for every char
```

**Pattern Elimination:**

| Pattern | Needed? | Why |
|---------|---------|-----|
| Sliding Window | ❌ | No subarray/range to scan |
| Two Pointers | ❌ | Order irrelevant |
| Binary Search | ❌ | Not a search problem |
| Sorting | ⚠️ | Works but O(n log n) — suboptimal |
| DP | ❌ | No optimal substructure |
| **Hashing** | ✅ | Frequency comparison in O(n) |

**Related Patterns**:
1. **Contains Duplicate** — Hashing for membership/existence check
2. **Group Anagrams** — Frequency map as a grouping key
3. **Top K Frequent Elements** — Frequency counting with HashMap

---

## Algorithm & Approach

### Core Insight

**What makes two strings anagrams?**
- Same length (if lengths differ → immediately `false`)
- Every character that appears in `s` appears in `t` **the same number of times**

**Decision Flow:**
```
isAnagram(s, t):
    ├─ If len(s) != len(t) → return false  (quick early exit)
    │
    ├─ Build frequency map for s:
    │   count[c]++ for each char c in s
    │
    ├─ Subtract frequency using t:
    │   count[c]-- for each char c in t
    │
    └─ If all counts == 0 → return true
       Else → return false
```

### Visual Understanding

```
s = "racecar",  t = "carrace"

Step 1: Build count from s
  r → 2
  a → 2
  c → 2
  e → 1

Step 2: Subtract count using t (c-a-r-r-a-c-e)
  c: 2-1 = 1  → 1-1 = 0
  a: 2-1 = 1  → 1-1 = 0
  r: 2-1 = 1  → 1-1 = 0
  e: 1-1 = 0

Step 3: All values are 0 → return true ✓
```

```
s = "jar",  t = "jam"

Step 1: Build count from s
  j → 1
  a → 1
  r → 1

Step 2: Subtract count using t (j-a-m)
  j: 1-1 = 0
  a: 1-1 = 0
  m: 0-1 = -1  ← 'm' not in s!

Step 3: Count has -1 → return false ✓
```

---

### Step-by-Step Algorithm

#### **Approach 1: Single HashMap (OPTIMAL for general case)**

**Core Idea**:
- Build a frequency map by incrementing for every char in `s` and decrementing for every char in `t`
- If all values in the map are `0`, the strings are anagrams

**Algorithm**:
```
isAnagram(s, t):
    if s.length != t.length → return false
    for each char c in s → count[c]++
    for each char t in t → count[c]--
    return all count values == 0
```

**Code Implementation**
```java
class Solution {
    public boolean isAnagram(String s, String t) {
        // Early exit: different lengths can never be anagrams
        if (s.length() != t.length()) {
            return false;
        }

        HashMap<Character, Integer> count = new HashMap<>();

        // Increment for s, decrement for t
        for (char c : s.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) + 1);
        }
        for (char c : t.toCharArray()) {
            count.put(c, count.getOrDefault(c, 0) - 1);
        }

        // All frequencies must cancel out to 0
        for (int val : count.values()) {
            if (val != 0) return false;
        }

        return true;
    }
}
```

**Step-by-Step Trace:**

Input: s = "racecar", t = "carrace"

| Step | Char | Action | Map State |
|------|------|--------|-----------|
| 1 | r | +1 | {r:1} |
| 2 | a | +1 | {r:1, a:1} |
| 3 | c | +1 | {r:1, a:1, c:1} |
| 4 | e | +1 | {r:1, a:1, c:1, e:1} |
| 5 | c | +1 | {r:1, a:1, c:2, e:1} |
| 6 | a | +1 | {r:1, a:2, c:2, e:1} |
| 7 | r | +1 | {r:2, a:2, c:2, e:1} |
| 8 | c | -1 | {r:2, a:2, c:1, e:1} |
| 9 | a | -1 | {r:2, a:1, c:1, e:1} |
| 10 | r | -1 | {r:1, a:1, c:1, e:1} |
| 11 | r | -1 | {r:0, a:1, c:1, e:1} |
| 12 | a | -1 | {r:0, a:0, c:1, e:1} |
| 13 | c | -1 | {r:0, a:0, c:0, e:1} |
| 14 | e | -1 | {r:0, a:0, c:0, e:0} |

All values are 0 → **return true** ✓

**Complexity Analysis**
- **Time Complexity**: O(n + m)
  - n = length of s, m = length of t
  - One pass through each string
- **Space Complexity**: O(1)
  - At most 26 entries in the map (lowercase English letters only)
  - For Unicode: O(k) where k = number of unique characters

---

#### **Approach 2: Fixed-Size Array (OPTIMAL for lowercase letters)**

**Core Idea**:
- Since the constraint guarantees **lowercase English letters only**, we can use a `int[26]` array instead of a HashMap
- `char - 'a'` maps each letter to an index 0–25
- Much faster in practice due to direct array indexing vs. hash computation

**Why Array over HashMap?**
- No hash computation overhead
- Fixed O(1) space regardless of input
- Cache-friendly (contiguous memory)

**Code Implementation**
```java
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] count = new int[26];  // Index 0='a', 1='b', ..., 25='z'

        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;  // Increment for s
            count[t.charAt(i) - 'a']--;  // Decrement for t (same index, same length)
        }

        // All counts must be 0
        for (int c : count) {
            if (c != 0) return false;
        }

        return true;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) — single pass through both strings simultaneously
- **Space Complexity**: O(1) — fixed 26-element array, independent of input size

---

#### **Approach 3: Sorting (SIMPLE but suboptimal)**

**Core Idea**:
- Sort both strings and compare — if they're equal, they're anagrams
- Two strings with the same characters will produce identical sorted strings

**Code Implementation**
```java
class Solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;

        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        Arrays.sort(sArr);
        Arrays.sort(tArr);

        return Arrays.equals(sArr, tArr);
    }
}
```

**Why This is Suboptimal**:
- Sorting takes O(n log n) — unnecessary when O(n) hashing exists
- But acceptable in interviews as a quick brute-force solution to state first

**Complexity Analysis**
- **Time Complexity**: O(n log n + m log m)
- **Space Complexity**: O(n + m) — for the char arrays

---

## Follow-Up: Unicode Characters

**Problem**: If inputs can contain **Unicode characters** (emojis, Chinese/Arabic/etc.), a `int[26]` array won't work.

**Solution**: Use `HashMap<Character, Integer>` — Approach 1 handles this naturally.

```java
// Works for Unicode — no code change needed from Approach 1!
HashMap<Character, Integer> count = new HashMap<>();
```

**Why it works**: `HashMap` can store any `Character` as a key, including Unicode code points beyond the standard ASCII range.

---

## Comparison of Approaches

| Aspect | HashMap | int[26] Array | Sorting |
|--------|---------|---------------|---------|
| **Time Complexity** | O(n + m) | ✅ O(n) | O(n log n) |
| **Space Complexity** | O(1)* | ✅ O(1) | O(n + m) |
| **Handles Unicode** | ✅ Yes | ❌ No | ✅ Yes |
| **Code Simplicity** | Moderate | ✅ Cleanest | ✅ Very simple |
| **Preferred?** | ✅ General use | ✅ Lowercase only | Interview brute-force |

*O(1) space since at most 26 unique lowercase letters

**Recommendation**: Use **`int[26]` array** when constraints guarantee lowercase letters (as in this problem) — it's the fastest. Use **HashMap** if Unicode support is needed (follow-up).

---

## Key Takeaways

1. **Length Check is a Free Early Exit**
   - If `s.length() != t.length()`, immediately return `false` — no need to count anything

2. **Increment-Decrement on One Map is Cleaner Than Two Maps**
   - Using one map (+1 for s, -1 for t) is cleaner and more efficient than building two separate maps and comparing them

3. **Frequency Count = Hashing Pattern**
   - Whenever a problem asks about character/element counts, equality of multisets, or rearrangements → **HashMap or frequency array**

4. **Fixed Array vs HashMap**
   - Known, small character set (26 letters) → use `int[26]` (faster, simpler)
   - Unknown or large character set (Unicode) → use `HashMap<Character, Integer>`

5. **Anagram = Same Multiset of Characters**
   - An anagram is just a permutation — same elements, same counts, any order
   - This is the exact definition of two multisets being equal

---

## Common Pitfalls

❌ **Mistake 1**: Not checking lengths first
```java
// WRONG: wastes time counting when lengths differ
for (char c : s.toCharArray()) count[c - 'a']++;
for (char c : t.toCharArray()) count[c - 'a']--;
// "race" vs "car" — lengths differ but we still processed everything
```
✅ **Correct**: Early length check
```java
if (s.length() != t.length()) return false;
```

❌ **Mistake 2**: Using sorting as your only approach without knowing it's suboptimal
```java
// WORKS but O(n log n) — mention this first, then improve
Arrays.sort(sArr); Arrays.sort(tArr);
return Arrays.equals(sArr, tArr);
```
✅ **Better**: Frequency array/map for O(n)

❌ **Mistake 3**: Using `int[26]` for Unicode inputs
```java
// WRONG: 'こ' - 'a' causes negative/overflow index for non-ASCII characters
count[c - 'a']++;
```
✅ **Correct for Unicode**: Use HashMap
```java
count.put(c, count.getOrDefault(c, 0) + 1);
```

❌ **Mistake 4**: Comparing two separate maps inefficiently
```java
// WORKS but verbose — building two maps and comparing all entries
HashMap<Character, Integer> countS = new HashMap<>();
HashMap<Character, Integer> countT = new HashMap<>();
// ... fill both, then compare
return countS.equals(countT);
```
✅ **Cleaner**: One map, increment for s, decrement for t, check all zeros

---

## Related Problems

1. **Contains Duplicate** (Easy) — Hashing for existence check (simpler variant)
2. **Group Anagrams** (Medium) — Use frequency map as a HashMap key to group strings
3. **Find All Anagrams in a String** (Medium) — Sliding window + frequency comparison
4. **Minimum Number of Steps to Make Two Strings Anagram** (Medium) — Count character differences
5. **Two Sum** (Easy) — Same hashing pattern for value lookup
6. **Top K Frequent Elements** (Medium) — Frequency counting extended with ranking

---

## Edge Cases to Consider

1. **Different Lengths**
   ```
   s = "ab", t = "abc"
   len(s)=2 ≠ len(t)=3 → return false immediately
   ```

2. **Same Characters, Same Length, Different Frequencies**
   ```
   s = "aab", t = "bba"
   count after s: {a:2, b:1}
   count after t: {a:2-1=1, b:1-2=-1}  → -1 ≠ 0 → false
   ```

3. **Single Character Strings**
   ```
   s = "a", t = "a" → count[a]=0 → true
   s = "a", t = "b" → count[a]=1, count[b]=-1 → false
   ```

4. **All Same Characters**
   ```
   s = "aaaa", t = "aaaa" → all counts cancel → true
   s = "aaaa", t = "aaab" → count[a]=1, count[b]=-1 → false
   ```

5. **Unicode Follow-Up**
   ```
   s = "こんにちは", t = "はちにんこ"
   int[26] fails — use HashMap<Character, Integer> ✓
   ```

---

## Summary

**Problem**: Check if two strings contain the exact same characters with the same frequencies (order irrelevant).

**Solution**:
- Early exit if lengths differ
- Use `int[26]` frequency array: increment for each char in `s`, decrement for each char in `t`
- If all entries are `0`, return `true`; otherwise `false`

**Time**: O(n + m) | **Space**: O(1)

**Pattern**: Hashing — Frequency Count. Anagram = same multiset of characters. Any time a problem involves character frequencies, rearrangements, or "same elements different order" → reach for a frequency array or HashMap.
