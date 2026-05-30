# Reverse String

## Problem Description

**Difficulty**: Easy

Write a function that reverses a character array `s` **in-place**.

You must do this by **modifying the input array in-place** with **O(1) extra memory**.

**Follow-up**: Could you solve it without using any built-in reverse functions?

## Examples

### Example 1:
```
Input: s = ["h","e","l","l","o"]

Output: ["o","l","l","e","h"]

Explanation: Characters are reversed in-place.
```

### Example 2:
```
Input: s = ["H","a","n","n","a","h"]

Output: ["h","a","n","n","a","H"]

Explanation: Characters at both ends are swapped progressively toward the middle.
```

### Example 3:
```
Input: s = ["a"]

Output: ["a"]

Explanation: Single character — nothing to reverse.
```

## Constraints
- 1 <= s.length <= 100,000
- s[i] is a printable ASCII character

---

## Pattern Recognition

**Primary Pattern**: **Two Pointers – Opposite Direction (In-Place Swap)**

**Why This Pattern?**
- Need to **reverse** array/string in-place
- Must use **O(1) extra space** — no extra array allowed
- Perfect use case: **converging pointers from both ends**, swap and move inward until they meet

**Key Insight — Why Two Pointers?**
```
To reverse an array, element at index 0 goes to index n-1,
element at index 1 goes to index n-2, and so on.

Two pointers let us do this in a single pass without any extra array:
  l=0 ↔ r=n-1  →  l=1 ↔ r=n-2  →  ... →  l >= r (stop)

Only n/2 swaps needed — each swap fixes two positions at once.
```

**Key Insight — In-Place Swap:**
- Place `l=0`, `r=s.length-1`
- Swap `s[l]` and `s[r]`
- Move `l++`, `r--`
- Stop when `l >= r`

**Related Patterns**:
1. **Valid Palindrome** — Two pointers checking from both ends (compare instead of swap)
2. **Reverse Words in a String** — Multiple two-pointer reversal steps
3. **Two Sum II** — Two pointers on sorted array converging inward
4. **Container With Most Water** — Two pointers shrinking inward based on height

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**

```
New array approach: O(n) time, O(n) space
  → Works but fails the O(1) space requirement

Recursion approach: O(n) time, O(n) stack space
  → Also fails O(1) space (call stack counts as space)

Two Pointers Iterative: O(n) time, O(1) space  ✓
```

**The Swap Idea:**

```
Think of the array as symmetric around the center.
Position 0 and position n-1 are mirrors.
Position 1 and position n-2 are mirrors.
...

Swap each mirror pair from outside inward.
When l meets or crosses r, every pair has been swapped — done.
```

### Visual Understanding

```
s = ["h","e","l","l","o"]
     0    1    2    3    4

Step 1: l=0 ↔ r=4  →  swap 'h' and 'o'
["o","e","l","l","h"]
  l=0              r=4

Step 2: l=1 ↔ r=3  →  swap 'e' and 'l'
["o","l","l","e","h"]
       l=1    r=3

Step 3: l=2, r=2  →  l >= r → STOP (middle element stays)

Result: ["o","l","l","e","h"] ✓
```

### Step-by-Step Algorithm

---

#### **Approach 1: Two Pointers Iterative — OPTIMAL**

**Core Idea**:
- Maintain two pointers `l=0` and `r=s.length-1`
- Swap characters at `l` and `r`
- Move `l++` and `r--`
- Stop when `l >= r`

**Algorithm**
```
reverseString(char[] s):
    l = 0
    r = s.length - 1
    while l < r:
        swap s[l] and s[r]
        l++
        r--
```

**Code Implementation**
```java
class Solution {
    public void reverseString(char[] s) {
        int l = 0;
        int r = s.length - 1;
        while (l < r) {
            char temp = s[r];
            s[r] = s[l];
            s[l] = temp;
            l++;
            r--;
        }
    }
}
```

**Example Walkthrough**

Input: `s = ["h","e","l","l","o"]`

| Step | l | r | s[l] | s[r] | Action | Array State |
|------|---|---|------|------|--------|-------------|
| Init | 0 | 4 | 'h'  | 'o'  | —      | ["h","e","l","l","o"] |
| 1    | 0 | 4 | 'h'  | 'o'  | Swap   | ["o","e","l","l","h"] |
| 2    | 1 | 3 | 'e'  | 'l'  | Swap   | ["o","l","l","e","h"] |
| 3    | 2 | 2 | 'l'  | 'l'  | l>=r → STOP | **["o","l","l","e","h"]** ✓ |

**Complexity Analysis**
- **Time Complexity**: O(n) — n/2 swaps, each O(1)
- **Space Complexity**: O(1) — only `l`, `r`, `temp` variables

---

#### **Approach 2: Recursion — ALTERNATIVE (violates O(1) space)**

**Core Idea**: Recursively swap the outermost pair and shrink the range.

**Algorithm**
```
helper(char[] s, int l, int r):
    if l >= r: return
    swap s[l] and s[r]
    helper(s, l+1, r-1)
```

**Code Implementation**
```java
class Solution {
    public void reverseString(char[] s) {
        helper(s, 0, s.length - 1);
    }

    private void helper(char[] s, int l, int r) {
        if (l >= r) return;
        char temp = s[l];
        s[l] = s[r];
        s[r] = temp;
        helper(s, l + 1, r - 1);
    }
}
```

**Example Walkthrough**

Input: `s = ["a","b","c","d"]`

```
helper(s, 0, 3): swap s[0]='a' ↔ s[3]='d'  →  ["d","b","c","a"]
  helper(s, 1, 2): swap s[1]='b' ↔ s[2]='c'  →  ["d","c","b","a"]
    helper(s, 2, 1): l >= r  →  return

Output: ["d","c","b","a"] ✓
```

**Complexity Analysis**
- **Time Complexity**: O(n) — n/2 recursive calls
- **Space Complexity**: O(n) — recursion call stack n/2 frames deep
  - ❌ Violates the O(1) extra memory requirement

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | New Array | Recursion | Two Pointers |
|-------------|-----------|-----------|--------------|
| Time complexity | O(n) ✓ | O(n) ✓ | O(n) ✓ |
| Space complexity | O(n) ❌ | O(n) ❌ | O(1) ✅ |
| Modifies in-place | ❌ | ✓ | ✅ |
| Follow-up satisfied | ❌ | ❌ | ✅ |

**Winner**: **Two Pointers Iterative** — only approach meeting both in-place and O(1) space constraints.

### Why swap order doesn't matter:
```
Array: [A, B, C, D]

Outside-in:  Swap(A,D) → Swap(B,C) → [D,C,B,A] ✓
Inside-out:  Swap(B,C) → Swap(A,D) → [D,C,B,A] ✓

Both produce the same result — any order of mirror-pair swaps works.
```

### Optimality Proof:
- Must visit each of n elements → O(n) time is minimum
- In-place + iterative = only 3 variables (l, r, temp) → O(1) space

---

## Critical Edge Cases & Gotchas

### 1. **Single character**
```java
Input: s = ["a"]
l=0, r=0  →  l >= r → loop never executes
Output: ["a"] ✓  (no swap needed)
```

### 2. **Two characters**
```java
Input: s = ["a","b"]
l=0, r=1  →  swap → ["b","a"]
l=1, r=0  →  l >= r → stop
Output: ["b","a"] ✓
```

### 3. **Palindrome string**
```java
Input: s = ["r","a","c","e","c","a","r"]
Swapping mirror pairs of a palindrome gives back the same array.
Output: ["r","a","c","e","c","a","r"] ✓  (unchanged — correct behaviour)
```

### 4. **Even length**
```java
Input: s = ["a","b","c","d"]
All pairs get swapped (no middle element).
Output: ["d","c","b","a"] ✓
```

### 5. **Odd length**
```java
Input: s = ["a","b","c"]
l=0 ↔ r=2: swap 'a' and 'c'
l=1, r=1: l >= r → stop (middle 'b' untouched)
Output: ["c","b","a"] ✓
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Wrong loop condition — using `<=` instead of `<`**
```java
// WRONG
while (l <= r) { ... }
```
**Why wrong**: When `l == r` (odd-length, middle element), the swap is harmless alone, but when pointers cross, swaps undo previous work.

**Dry run failure for `["a","b"]`:**
```
l=0, r=1: swap → ["b","a"]  ✓
l=1, r=0: 1 <= 0 is TRUE → swap again → ["a","b"]  ✗  (undone!)
```

**Fix**:
```java
while (l < r) { ... }   // ✓ stops exactly when pointers meet
```

### ❌ **MISTAKE 2: Off-by-one on right pointer initialization**
```java
// WRONG
int r = s.length;    // ArrayIndexOutOfBoundsException!
```
**Why wrong**: Array has valid indices `0` to `s.length-1`. Index `s.length` does not exist.

**Fix**:
```java
int r = s.length - 1;   // ✓
```

### ❌ **MISTAKE 3: Forgetting to move pointers — Infinite Loop**
```java
// WRONG — pointers never move
while (l < r) {
    char temp = s[l];
    s[l] = s[r];
    s[r] = temp;
    // missing: l++; r--;
}
```
**Why wrong**: `l` and `r` stay fixed, condition `l < r` is always true → infinite loop.

**Fix**:
```java
while (l < r) {
    char temp = s[l];
    s[l] = s[r];
    s[r] = temp;
    l++;   // ✓
    r--;   // ✓
}
```

### ❌ **MISTAKE 4: Overwriting without temp variable**
```java
// WRONG — loses original s[l] value
s[l] = s[r];
s[r] = s[l];   // ❌ s[l] already overwritten — both become s[r]
```
**Why wrong**: After `s[l] = s[r]`, `s[l]` no longer holds the original value.

**Dry run failure for `["a","b"]`:**
```
s[l] = s[r] → ["b","b"]
s[r] = s[l] → ["b","b"]   ← 'a' is lost ❌
```

**Fix**: Always use a temp variable:
```java
char temp = s[l];   // ✓ save s[l] first
s[l] = s[r];
s[r] = temp;
```

### ❌ **MISTAKE 5: Trying to use String instead of char[]**
```java
// WRONG — String is immutable in Java
public void reverseString(String s) {
    // cannot modify characters directly
}
```
**Why wrong**: `String` in Java is immutable. The problem explicitly gives `char[]` which is mutable.

**Fix**: Use `char[]` as the parameter type, swap directly using array index access.

---

## Complexity Analysis

### Two Pointers Iterative Approach

**Time Complexity: O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Traverse with two pointers | O(n/2) | Each element visited exactly once |
| Each swap | O(1) | Constant-time 3-step swap |
| **Total** | **O(n)** | n/2 swaps = O(n) |

**Space Complexity: O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| l, r | O(1) | Two integer pointer variables |
| temp | O(1) | One char variable for swap |
| **Total** | **O(1)** | No extra data structure used |

---

## Visualization

### Odd-Length Array — Middle Element Untouched

**Input:** `s = ["r","a","c","e","c","a","r"]`

```
Initial:
["r","a","c","e","c","a","r"]
  l=0                   r=6

Step 1: l=0 ↔ r=6  swap 'r' ↔ 'r'  (same, no change)
["r","a","c","e","c","a","r"]
       l=1            r=5

Step 2: l=1 ↔ r=5  swap 'a' ↔ 'a'  (same, no change)
["r","a","c","e","c","a","r"]
            l=2      r=4

Step 3: l=2 ↔ r=4  swap 'c' ↔ 'c'  (same, no change)
["r","a","c","e","c","a","r"]
                 l=3 r=3

Step 4: l=3, r=3  →  l >= r  →  STOP  (middle 'e' untouched)

Output: ["r","a","c","e","c","a","r"] ✓  (palindrome stays same)
```

### Why the Middle Element is Always Correct

```
For odd-length array of size n:
  Middle index = n/2 (integer division)
  l and r both reach n/2 at the same time → l >= r → loop stops

Middle element never needs swapping — its mirror is itself.
No special case needed; the condition l < r handles it automatically.
```

---

## Comparison of Approaches

| Approach | Time | Space | In-Place | Follow-up | When to Use |
|----------|------|-------|----------|-----------|-------------|
| New Array | O(n) | O(n) ❌ | ❌ | ❌ | Never — wastes space |
| Recursion | O(n) | O(n) ❌ | ✓ | ❌ | Only for teaching recursion |
| **Two Pointers** | **O(n)** | **O(1) ✅** | **✅** | **✅** | **Always — optimal** |

**Recommendation**: Use **Two Pointers Iterative** — it's the only approach that satisfies all constraints.

---

## Key Takeaways

1. **Two pointers from opposite ends** — l=0 and r=n-1 converge inward with each swap
2. **Loop condition is `l < r` not `l <= r`** — `<=` causes double-swap undoing the reversal
3. **right init is `s.length - 1` not `s.length`** — off-by-one causes ArrayIndexOutOfBounds
4. **Always use temp variable** — direct overwrite without temp loses the original value
5. **Move both pointers every iteration** — forgetting causes infinite loop
6. **Middle element is auto-handled** — `l < r` naturally skips the middle in odd-length arrays
7. **Recursion is O(n) space** — each call frame on the stack counts; violates the follow-up

---

## Interview Tips

**What to say in an interview:**

> "I use two pointers starting from opposite ends — l=0 and r=n-1. Each iteration I swap s[l] and s[r] using a temp variable, then move l inward and r inward. I stop when l meets or crosses r. This runs in O(n) time and O(1) space since I only use two pointer variables and one temp."

**Key points to mention:**
1. **Why two pointers** — only way to achieve O(1) space in-place reversal
2. **Loop condition `l < r`** — not `<=`, prevents double-swap undoing the result
3. **Temp variable necessity** — prevents value loss during swap
4. **Middle element** — naturally untouched in odd-length arrays, no special case needed
5. **Why not recursion** — O(n) call stack violates the O(1) space follow-up

**If asked about Recursion vs Iterative:**
> "Recursion produces the same result but uses O(n) stack space due to n/2 recursive calls. The iterative two-pointer approach uses only 3 variables — O(1) space — which is what the problem requires."

**Common Follow-ups:**
- "Can you do it without a temp variable?" → Yes, XOR swap: `s[l]^=s[r]; s[r]^=s[l]; s[l]^=s[r];` — but less readable
- "What if it was a String not a char[]?" → String is immutable in Java; must convert to char[] first
- "What is the space complexity of the recursive approach?" → O(n) due to call stack depth

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Reverse String** | Easy | **Two Pointers In-Place Swap** | **This problem** ← |
| Valid Palindrome | Easy | Two Pointers (compare, not swap) | Check equality instead of swapping |
| Reverse Words in a String | Medium | Two Pointers (multi-step reversal) | Reverse whole string, then each word |
| Reverse Linked List | Easy | Two Pointers (node re-linking) | Pointer manipulation instead of index swap |
| Reverse String II | Easy | Two Pointers (partial reverse) | Reverse every 2k characters |

**Pattern Progression**:
1. **Reverse String** (this problem) — simplest in-place swap with two pointers
2. **Valid Palindrome** — same two-pointer setup but compare instead of swap
3. **Reverse Words in a String** — combine full reverse + word-level reverse
4. **Reverse Linked List** — same concept applied to linked list nodes

---

## Final Pattern Label

✅ **Two Pointers – Opposite Direction (In-Place Swap)**

**Remember:** `l=0`, `r=s.length-1`, loop while `l < r`, swap with temp, move both pointers. Middle element in odd-length array is automatically skipped — no special case needed!
