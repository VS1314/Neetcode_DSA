# Decode String

## Problem Description

**Difficulty**: Medium

You are given an **encoded string** `s`, return its **decoded string**.

The encoding rule is: `k[encoded_string]`, where the `encoded_string` inside the square brackets is being repeated exactly `k` times. Note that `k` is guaranteed to be a **positive integer**.

You may assume that the input string is always valid:
- No extra white spaces
- Square brackets are well-formed
- There will not be input like `3a`, `2[4]`, `a[a]` or `a[2]`

The test cases are generated so that the **length of the output will never exceed 100,000**.

## Examples

### Example 1:
```
Input: s = "2[a3[b]]c"
Output: "abbbabbbc"

Explanation:
  Process step by step:
    Start with "2[a3[b]]c"
    
    Innermost bracket first: 3[b] = "bbb"
    Substitute: "2[abbb]c"
    
    Next bracket: 2[abbb] = "abbbabbb"
    Substitute: "abbbabbbc"
    
  Final: "abbbabbbc"
```

### Example 2:
```
Input: s = "axb3[z]4[c]"
Output: "axbzzzcccc"

Explanation:
  "axb" remains as is
  3[z] = "zzz"
  4[c] = "cccc"
  Result: "axb" + "zzz" + "cccc" = "axbzzzcccc"
```

### Example 3:
```
Input: s = "ab2[c]3[d]1[x]"
Output: "abccdddx"

Explanation:
  "ab" remains
  2[c] = "cc"
  3[d] = "ddd"
  1[x] = "x"
  Result: "ab" + "cc" + "ddd" + "x" = "abccdddx"
```

### Example 4:
```
Input: s = "3[a]2[bc]"
Output: "aaabcbc"

Explanation:
  3[a] = "aaa"
  2[bc] = "bcbc"
  Result: "aaa" + "bcbc" = "aaabcbc"
```

### Example 5:
```
Input: s = "3[a2[c]]"
Output: "accaccacc"

Explanation:
  Innermost: 2[c] = "cc"
  Substitute: 3[acc]
  3[acc] = "accaccacc"
```

### Example 6:
```
Input: s = "2[abc]3[cd]ef"
Output: "abcabccdcdcdef"

Explanation:
  2[abc] = "abcabc"
  3[cd] = "cdcdcd"
  "ef" remains
  Result: "abcabc" + "cdcdcd" + "ef" = "abcabccdcdcdef"
```

### Example 7:
```
Input: s = "10[a]"
Output: "aaaaaaaaaa"

Explanation:
  Multi-digit number: 10
  10[a] = "aaaaaaaaaa" (10 a's)
```

### Example 8:
```
Input: s = "abc"
Output: "abc"

Explanation:
  No brackets, return as is
```

### Example 9:
```
Input: s = "100[leetcode]"
Output: "leetcode" repeated 100 times

Explanation:
  Multi-digit number handling
```

### Example 10:
```
Input: s = "2[2[y]pq4[z]]ef"
Output: "yypqzzzzyyppqzzzzef"

Explanation:
  Deep nesting:
    4[z] = "zzzz"
    2[y] = "yy"
    Inner content: "yypqzzzz"
    2[yypqzzzz] = "yypqzzzzyypqzzzz"
    Add "ef": "yypqzzzzyypqzzzzef"
```

## Constraints
- 1 <= s.length <= 30
- `s` consists of lowercase English letters, digits, and square brackets `'[]'`
- All the integers in `s` are in the range `[1, 300]`
- `s` is guaranteed to be a **valid input**

**Recommended Complexity**: O(n) time where n is the length of the decoded string

---

## Pattern Recognition

**Primary Pattern**: **Stack (Nested Structure / Bracket Matching with State)**

**Why This Pattern?**
- Nested brackets require LIFO processing
- Need to remember state (number and string) before each `[`
- When we see `]`, restore previous state and build result
- Stack perfectly models nested structure

**Key Insight**: Two Stacks for Count and String
```
Decode String problem:
  Encoding: k[encoded_string]
  Nested: 2[a3[b]] → need to decode inside-out
  
When we see '[':
  Must remember:
    1. How many times to repeat (k)
    2. What string came before this bracket
  
Solution: TWO STACKS
  countStack: stores repetition counts
  stringStack: stores previous strings
  
Example: "2[a3[b]]"
  
  See '2': number = 2
  See '[': push number to countStack, push currentString to stringStack
  See 'a': currentString = "a"
  See '3': number = 3
  See '[': push 3 to countStack, push "a" to stringStack
  See 'b': currentString = "b"
  See ']': 
    count = countStack.pop() = 3
    prevString = stringStack.pop() = "a"
    currentString = prevString + repeat("b", 3) = "a" + "bbb" = "abbb"
  See ']':
    count = countStack.pop() = 2
    prevString = stringStack.pop() = ""
    currentString = prevString + repeat("abbb", 2) = "" + "abbbabbb" = "abbbabbb"
  
Result: "abbbabbb"
```

**The Stack Strategy**:
```
Use two stacks to track state:

countStack: [2, 3]
stringStack: ["", "a"]
currentString: "b"
currentNum: 0

When we see ']':
  1. Pop count from countStack
  2. Pop prevString from stringStack
  3. Build: prevString + repeat(currentString, count)
  4. Update currentString to built result
  
This handles nesting automatically!
```

**Character Processing Rules**:
```
For each character c:

  1. If c is digit:
     - Accumulate into currentNum (handle multi-digit)
     
  2. If c is '[':
     - Push currentNum to countStack
     - Push currentString to stringStack
     - Reset currentNum = 0, currentString = ""
     
  3. If c is ']':
     - count = countStack.pop()
     - prevString = stringStack.pop()
     - currentString = prevString + repeat(currentString, count)
     
  4. If c is letter:
     - Append c to currentString
```

**Example Showing Stack Evolution**:
```
Input: "2[a3[b]]c"

Char '2':
  currentNum = 2
  countStack: []
  stringStack: []
  currentString: ""

Char '[':
  Push 2 to countStack
  Push "" to stringStack
  Reset: currentNum = 0, currentString = ""
  countStack: [2]
  stringStack: [""]

Char 'a':
  currentString += 'a'
  countStack: [2]
  stringStack: [""]
  currentString: "a"

Char '3':
  currentNum = 3
  countStack: [2]
  stringStack: [""]
  currentString: "a"

Char '[':
  Push 3 to countStack
  Push "a" to stringStack
  Reset: currentNum = 0, currentString = ""
  countStack: [2, 3]
  stringStack: ["", "a"]

Char 'b':
  currentString += 'b'
  countStack: [2, 3]
  stringStack: ["", "a"]
  currentString: "b"

Char ']':
  count = pop(countStack) = 3
  prevString = pop(stringStack) = "a"
  currentString = "a" + "b".repeat(3) = "abbb"
  countStack: [2]
  stringStack: [""]
  currentString: "abbb"

Char ']':
  count = pop(countStack) = 2
  prevString = pop(stringStack) = ""
  currentString = "" + "abbb".repeat(2) = "abbbabbb"
  countStack: []
  stringStack: []
  currentString: "abbbabbb"

Char 'c':
  currentString += 'c'
  countStack: []
  stringStack: []
  currentString: "abbbabbbc"

Final: "abbbabbbc" ✓
```

**Why Two Stacks?**
```
Need to remember TWO pieces of information:
  1. Repetition count (k)
  2. Previous string (before '[')

Example showing why both needed:
  "2[a3[b]]"
  
  At first '[':
    Must remember: count=2, prevString=""
  At second '[':
    Must remember: count=3, prevString="a"
  
  When we see ']' for 3[b]:
    Pop count=3, prevString="a"
    Build: "a" + "b"*3 = "abbb"
  
  When we see ']' for 2[...]:
    Pop count=2, prevString=""
    Build: "" + "abbb"*2 = "abbbabbb"

Two stacks keep count and string synchronized!
```

**Critical Detail**: Multi-Digit Numbers
```
Input can have multi-digit numbers: "10[a]", "100[b]"

Cannot just use: currentNum = c - '0'

Must accumulate: currentNum = currentNum * 10 + (c - '0')

Example: "123[a]"
  Char '1': currentNum = 0*10 + 1 = 1
  Char '2': currentNum = 1*10 + 2 = 12
  Char '3': currentNum = 12*10 + 3 = 123
  Char '[': push 123 to countStack ✓
```

**Related Patterns**:
1. **Stack** — Core technique for nested structures
2. **Bracket Matching** — Track opening/closing pairs
3. **String Building** — Construct result incrementally
4. **State Management** — Remember context for nesting

---

## Algorithm & Approach

### Core Insight

**Why Naive Approach Fails:**
```
Naive: Recursively find innermost brackets
  while (has brackets):
      find innermost bracket pair
      decode that part
      replace in string
  
Problems:
  - O(n²) time — repeated string searching
  - String replacement is expensive
  - Hard to implement correctly
  
Optimal approach:
  Single pass with two stacks
  → O(n) time, clean logic ✓
```

**The Optimal Strategy**:
```
Key observations:
  1. Process left to right, single pass
  2. Use two stacks for state management
  3. Build result as we go
  4. Stack handles nesting automatically
  5. Each character processed once
  
Operations:
  Process each char: O(1) per char
  String building: amortized O(1) with StringBuilder
  
Total: O(n) where n is decoded length
```

### Step-by-Step Algorithm

---

#### **Approach 1: Two Stacks - OPTIMAL**

**Core Idea**:
- Maintain two stacks: countStack and stringStack
- Track currentNum and currentString
- Process each character according to type
- Build decoded string incrementally

**Algorithm**
```
decodeString(s):
    countStack = new Stack()
    stringStack = new Stack()
    currentString = ""
    currentNum = 0
    
    for each char c in s:
        if c is digit:
            // Accumulate multi-digit number
            currentNum = currentNum * 10 + (c - '0')
            
        else if c is '[':
            // Save state and reset
            countStack.push(currentNum)
            stringStack.push(currentString)
            currentNum = 0
            currentString = ""
            
        else if c is ']':
            // Decode current level
            count = countStack.pop()
            prevString = stringStack.pop()
            
            // Build repeated string
            temp = repeat(currentString, count)
            currentString = prevString + temp
            
        else:  // c is letter
            currentString += c
    
    return currentString
```

**Code Implementation**
```java
class Solution {
    public String decodeString(String s) {
        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();
        StringBuilder currentString = new StringBuilder();
        int currentNum = 0;
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                // Accumulate multi-digit number
                currentNum = currentNum * 10 + (c - '0');
                
            } else if (c == '[') {
                // Push current state and reset
                countStack.push(currentNum);
                stringStack.push(currentString);
                currentNum = 0;
                currentString = new StringBuilder();
                
            } else if (c == ']') {
                // Pop and decode
                int count = countStack.pop();
                StringBuilder prevString = stringStack.pop();
                
                // Repeat currentString count times
                StringBuilder temp = new StringBuilder(prevString);
                for (int i = 0; i < count; i++) {
                    temp.append(currentString);
                }
                currentString = temp;
                
            } else {
                // Letter
                currentString.append(c);
            }
        }
        
        return currentString.toString();
    }
}
```

**Example Walkthrough**

Input: `s = "2[a3[b]]c"`

| Char | Type | Action | currentNum | currentString | countStack | stringStack |
|------|------|--------|------------|---------------|------------|-------------|
| '2' | Digit | num = 0*10+2 | 2 | "" | [] | [] |
| '[' | Open | Push state, reset | 0 | "" | [2] | [""] |
| 'a' | Letter | Append | 0 | "a" | [2] | [""] |
| '3' | Digit | num = 0*10+3 | 3 | "a" | [2] | [""] |
| '[' | Open | Push state, reset | 0 | "" | [2,3] | ["","a"] |
| 'b' | Letter | Append | 0 | "b" | [2,3] | ["","a"] |
| ']' | Close | Decode: "a"+"b"*3 | 0 | "abbb" | [2] | [""] |
| ']' | Close | Decode: ""+"abbb"*2 | 0 | "abbbabbb" | [] | [] |
| 'c' | Letter | Append | 0 | "abbbabbbc" | [] | [] |

Final result: **"abbbabbbc"**

**Complexity Analysis**
- **Time**: O(n) — n is decoded string length, each char processed once
- **Space**: O(n) — Stacks + StringBuilder

---

#### **Approach 2: Single Stack with Pairs - ALTERNATIVE**

**Core Idea**: Use one stack storing (count, prevString) pairs.

**Code Implementation**
```java
class Solution {
    public String decodeString(String s) {
        Stack<Pair<Integer, StringBuilder>> stack = new Stack<>();
        StringBuilder currentString = new StringBuilder();
        int currentNum = 0;
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                currentNum = currentNum * 10 + (c - '0');
                
            } else if (c == '[') {
                // Push (count, prevString) as pair
                stack.push(new Pair<>(currentNum, currentString));
                currentNum = 0;
                currentString = new StringBuilder();
                
            } else if (c == ']') {
                Pair<Integer, StringBuilder> pair = stack.pop();
                int count = pair.getKey();
                StringBuilder prevString = pair.getValue();
                
                StringBuilder temp = new StringBuilder(prevString);
                for (int i = 0; i < count; i++) {
                    temp.append(currentString);
                }
                currentString = temp;
                
            } else {
                currentString.append(c);
            }
        }
        
        return currentString.toString();
    }
}
```

**Key Difference**: 
- Single stack instead of two
- Stores pairs (cleaner semantics)
- Same complexity

**Complexity Analysis**
- **Time**: O(n) — Same as two stacks
- **Space**: O(n) — Same overall space

---

#### **Approach 3: Recursion - ELEGANT**

**Core Idea**: Recursively decode when we see `[`.

**Code Implementation**
```java
class Solution {
    int index = 0;
    
    public String decodeString(String s) {
        StringBuilder result = new StringBuilder();
        int num = 0;
        
        while (index < s.length()) {
            char c = s.charAt(index);
            
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
                index++;
                
            } else if (c == '[') {
                index++;  // Skip '['
                String decoded = decodeString(s);  // Recurse
                
                // Repeat decoded string num times
                for (int i = 0; i < num; i++) {
                    result.append(decoded);
                }
                num = 0;
                
            } else if (c == ']') {
                index++;  // Skip ']'
                return result.toString();  // Return to caller
                
            } else {
                result.append(c);
                index++;
            }
        }
        
        return result.toString();
    }
}
```

**Key Difference**: 
- No explicit stack (uses call stack)
- More elegant for some
- Same time/space complexity

**Complexity Analysis**
- **Time**: O(n) — Each char processed once
- **Space**: O(n) — Call stack depth = nesting level

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Code Complexity | Recommended |
|----------|------|-------|-----------------|-------------|
| **Two Stacks** | **O(n)** | **O(n)** | **Simple ✅** | **Yes ✅** |
| Single Stack (Pairs) | O(n) | O(n) | Simple | Cleaner |
| Recursion | O(n) | O(n) | Medium | Elegant |

**Winner**: **Two Stacks** — clear, explicit state management!

### Why Use Two Stacks?

```
Nested brackets require remembering TWO things:
  1. How many times to repeat (count)
  2. What came before the bracket (prevString)

Example: "2[a3[b]]"

At first '[':
  Remember: count=2, prevString=""
  
At second '[':
  Remember: count=3, prevString="a"
  
When we see first ']' (closing 3[b]):
  Need BOTH: count=3, prevString="a"
  Build: "a" + "b"*3 = "abbb"
  
When we see second ']' (closing 2[...]):
  Need BOTH: count=2, prevString=""
  Build: "" + "abbb"*2 = "abbbabbb"

Two stacks keep them synchronized!
```

### Why Reset After '['?

```
When we see '[', we're entering a new nested level

Example: "2[ab]"
  
  Before '[': currentNum=2, currentString=""
  
  See '[':
    Push 2 to countStack
    Push "" to stringStack
    MUST RESET: currentNum=0, currentString=""
    
  Why reset?
    After '[', we start building NEW string for this level
    "ab" is INSIDE brackets, separate from outer level
    
  See 'a': currentString = "a"
  See 'b': currentString = "ab"
  See ']':
    count = 2 (from stack)
    prevString = "" (from stack)
    Result: "" + "ab"*2 = "abab"

Reset ensures we don't mix levels!
```

### Why Accumulate Multi-Digit Numbers?

```
Constraint: integers in range [1, 300]

Can have "100[a]", "25[bc]", etc.

Cannot just use: num = c - '0' (only works for single digit)

Must accumulate:
  num = num * 10 + (c - '0')

Example: "123[a]"
  
  Char '1':
    num = 0*10 + 1 = 1
  
  Char '2':
    num = 1*10 + 2 = 12
  
  Char '3':
    num = 12*10 + 3 = 123
  
  Char '[':
    Push 123 to countStack ✓

Accumulation handles any number of digits!
```

### Why StringBuilder Instead of String?

```
Java strings are immutable!

Using String with +=:
  result += c;  // Creates NEW string object each time
  
For n characters: O(n²) time (each concat copies string)

Using StringBuilder:
  result.append(c);  // Modifies in place
  
For n characters: O(n) time (amortized)

StringBuilder is essential for efficiency!
```

### Why Check Character Type?

```
Input has 4 types of characters:
  1. Digits: '0'-'9'
  2. Opening bracket: '['
  3. Closing bracket: ']'
  4. Letters: 'a'-'z'

Each type has different action:
  Digit → accumulate number
  '[' → push state, reset
  ']' → pop and decode
  Letter → append to string

Must distinguish to process correctly!

Implementation:
  if (Character.isDigit(c)) { ... }
  else if (c == '[') { ... }
  else if (c == ']') { ... }
  else { ... }  // Letter
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Letter**
```java
Input: s = "abc"
No brackets, just append letters
Output: "abc"
```

### 2. **Single Bracket Pair**
```java
Input: s = "3[a]"
Simple case: "a" repeated 3 times
Output: "aaa"
```

### 3. **Nested Brackets**
```java
Input: s = "2[a3[b]]"
Inner first: 3[b]="bbb", then 2[abbb]="abbbabbb"
Output: "abbbabbb"
```

### 4. **Multiple Bracket Pairs**
```java
Input: s = "3[a]2[bc]"
Separate: 3[a]="aaa", 2[bc]="bcbc"
Output: "aaabcbc"
```

### 5. **Multi-Digit Number**
```java
Input: s = "10[a]"
Number is 10, not 1
Output: "aaaaaaaaaa" (10 a's)
```

### 6. **Letters Before Bracket**
```java
Input: s = "xy2[a]"
"xy" + "aa" = "xyaa"
Output: "xyaa"
```

### 7. **Letters After Bracket**
```java
Input: s = "2[a]bc"
"aa" + "bc" = "aabc"
Output: "aabc"
```

### 8. **Deep Nesting**
```java
Input: s = "2[2[2[a]]]"
Innermost: 2[a]="aa"
Middle: 2[aa]="aaaa"
Outer: 2[aaaa]="aaaaaaaa"
Output: "aaaaaaaa" (8 a's)
```

### 9. **Complex Pattern**
```java
Input: s = "3[z]2[2[y]pq4[k]]"
3[z]="zzz"
4[k]="kkkk"
2[y]="yy"
2[yypqkkkk]="yypqkkkkyypqkkkk"
Output: "zzzyypqkkkkyypqkkkk"
```

### 10. **Number Followed by Letter**
```java
Input: s = "abc3[cd]xyz"
"abc" + "cdcdcd" + "xyz" = "abccdcdcdxyz"
Output: "abccdcdcdxyz"
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Handling Multi-Digit Numbers**
```java
// WRONG - only handles single digit
if (Character.isDigit(c)) {
    currentNum = c - '0';  // Overwrites instead of accumulating!
}
```

**Why wrong**: "10[a]" would be interpreted as "0[a]"!

**Dry run failure for s="12[a]":**
```
Char '1':
  currentNum = '1' - '0' = 1 ✓

Char '2':
  currentNum = '2' - '0' = 2 ❌
  
  Lost the '1'! Should be 12, not 2!

Char '[':
  Push 2 to stack ❌
  
Result: "aa" ❌ (should be "aaaaaaaaaaaa" - 12 a's)
```

**Fix**: Accumulate digits
```java
if (Character.isDigit(c)) {
    currentNum = currentNum * 10 + (c - '0');
}
```

### ❌ **MISTAKE 2: Not Resetting After '['**
```java
// WRONG - doesn't reset
if (c == '[') {
    countStack.push(currentNum);
    stringStack.push(currentString);
    // Missing: currentNum = 0; currentString = new StringBuilder();
}
```

**Why wrong**: Mixes levels, wrong results!

**Dry run failure for s="2[a]":**
```
Char '2':
  currentNum = 2
  currentString = ""

Char '[':
  Push 2, push ""
  Don't reset ❌
  currentNum = 2 (still!)
  currentString = ""

Char 'a':
  currentString = "a"

Char ']':
  count = 2
  prevString = ""
  currentString = "" + "a"*2 = "aa" ✓
  
Lucky! But for "2[a]3[b]":

After ']':
  currentNum = 2 (not reset!)
  currentString = "aa"

Char '3':
  currentNum = 2*10 + 3 = 23 ❌
  
Wrong number for next bracket!
```

**Fix**: Always reset after '['
```java
if (c == '[') {
    countStack.push(currentNum);
    stringStack.push(currentString);
    currentNum = 0;
    currentString = new StringBuilder();
}
```

### ❌ **MISTAKE 3: Wrong String Building Order**
```java
// WRONG - wrong concatenation order
if (c == ']') {
    int count = countStack.pop();
    StringBuilder prevString = stringStack.pop();
    
    // WRONG order
    currentString = repeat(currentString, count) + prevString;  ❌
}
```

**Why wrong**: Repeated part should come AFTER prevString!

**Dry run failure for s="a2[b]":**
```
After 'a':
  currentString = "a"

After '[':
  stringStack = ["a"]
  currentString = ""

After 'b':
  currentString = "b"

After ']':
  count = 2
  prevString = "a"
  
  Wrong: "b"*2 + "a" = "bba" ❌
  Correct: "a" + "b"*2 = "abb" ✓
```

**Fix**: Correct order
```java
StringBuilder temp = new StringBuilder(prevString);
for (int i = 0; i < count; i++) {
    temp.append(currentString);
}
currentString = temp;
```

### ❌ **MISTAKE 4: Using String Instead of StringBuilder**
```java
// WRONG - using String (inefficient)
String currentString = "";
for (char c : s.toCharArray()) {
    // ...
    if (letter) {
        currentString += c;  // Creates new string each time!
    }
}
```

**Why wrong**: O(n²) time complexity!

**Performance issue:**
```
String concatenation creates new object each time

For n characters:
  Iteration 1: copy 0 chars
  Iteration 2: copy 1 char
  Iteration 3: copy 2 chars
  ...
  Iteration n: copy n-1 chars
  
Total: 0+1+2+...+(n-1) = O(n²)

StringBuilder: O(n) amortized
```

**Fix**: Use StringBuilder
```java
StringBuilder currentString = new StringBuilder();
currentString.append(c);
```

### ❌ **MISTAKE 5: Not Checking Character Type Correctly**
```java
// WRONG - assumes order
for (char c : s.toCharArray()) {
    if (c >= '0' && c <= '9') {
        // ...
    }
    // Missing else if checks!
    if (c == '[') {  // Should be else if
        // ...
    }
}
```

**Why wrong**: Multiple conditions could execute!

**Fix**: Use else if chain
```java
if (Character.isDigit(c)) {
    // ...
} else if (c == '[') {
    // ...
} else if (c == ']') {
    // ...
} else {
    // Letter
}
```

### ❌ **MISTAKE 6: Forgetting to Return currentString**
```java
// WRONG - returns empty string
public String decodeString(String s) {
    // ... process s
    return "";  // Should return currentString!
}
```

**Fix**: Return currentString
```java
return currentString.toString();
```

### ❌ **MISTAKE 7: Wrong Stack Pop Order**
```java
// WRONG - pops in wrong order
if (c == ']') {
    StringBuilder prevString = stringStack.pop();
    int count = countStack.pop();  // Order doesn't matter here
    
    // But conceptually should match push order
}
```

**Actually OK**: Order doesn't matter for independent stacks, but should match push for clarity.

**Better**: Pop in reverse push order
```java
int count = countStack.pop();  // Last thing pushed
StringBuilder prevString = stringStack.pop();
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **Process each char** | m (input length) | O(1) | O(m) |
| **String repetition** | varies | varies | O(n-m) |
| **Stack operations** | O(m) | O(1) | O(m) |
| **Total** | - | - | **O(n)** |

**Where n = decoded string length, m = encoded string length**

**Time analysis**:
```
Each character in input processed once: O(m)
String repetition work proportional to output: O(n-m)
Total: O(m) + O(n-m) = O(n)

Example: "100[a]"
  Input length m = 6
  Output length n = 100
  Work: process 6 chars + build 100 chars = O(100) = O(n)

Worst case: n >> m (high repetition)
Best case: n = m (no brackets)
Both: O(n)
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Count stack | O(d) | d = max nesting depth |
| String stack | O(n) | Can store partial strings totaling O(n) |
| Current string | O(n) | Result building |
| **Total** | **O(n)** | Linear in output size |

**Space analysis**:
```
Count stack: max depth d (d <= m)
String stack: can accumulate O(n) across all frames
Current string: builds to O(n)

Example: "2[2[2[a]]]"
  Depth d = 3
  countStack: [2, 2, 2] → O(3)
  stringStack: ["", "", ""] → O(3)
  currentString: up to O(8) for result
  Total: O(8) = O(n)

Worst case: deep nesting with large output
Space: O(n)
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `s = "2[a3[b]]c"`

**Expected Output:** `"abbbabbbc"`

---

**Initial State:**
```
countStack: []
stringStack: []
currentString: ""
currentNum: 0
```

---

**Step 1: Process '2'**
```
Character: '2' (digit)
Action: Accumulate number
  currentNum = 0 * 10 + 2 = 2

State:
  currentNum: 2
  currentString: ""
  countStack: []
  stringStack: []
```

---

**Step 2: Process '['**
```
Character: '[' (opening bracket)
Action: Push state, reset
  countStack.push(2)
  stringStack.push("")
  currentNum = 0
  currentString = ""

State:
  currentNum: 0
  currentString: ""
  countStack: [2]
  stringStack: [""]
  
Entered nesting level 1
```

---

**Step 3: Process 'a'**
```
Character: 'a' (letter)
Action: Append to currentString
  currentString = "a"

State:
  currentNum: 0
  currentString: "a"
  countStack: [2]
  stringStack: [""]
```

---

**Step 4: Process '3'**
```
Character: '3' (digit)
Action: Accumulate number
  currentNum = 0 * 10 + 3 = 3

State:
  currentNum: 3
  currentString: "a"
  countStack: [2]
  stringStack: [""]
```

---

**Step 5: Process '['**
```
Character: '[' (opening bracket)
Action: Push state, reset
  countStack.push(3)
  stringStack.push("a")
  currentNum = 0
  currentString = ""

State:
  currentNum: 0
  currentString: ""
  countStack: [2, 3]
  stringStack: ["", "a"]
  
Entered nesting level 2
```

---

**Step 6: Process 'b'**
```
Character: 'b' (letter)
Action: Append to currentString
  currentString = "b"

State:
  currentNum: 0
  currentString: "b"
  countStack: [2, 3]
  stringStack: ["", "a"]
```

---

**Step 7: Process ']' (First Closing)**
```
Character: ']' (closing bracket)
Action: Pop and decode
  count = countStack.pop() = 3
  prevString = stringStack.pop() = "a"
  
  Build: "a" + "b" * 3
       = "a" + "bbb"
       = "abbb"
  
  currentString = "abbb"

State:
  currentNum: 0
  currentString: "abbb"
  countStack: [2]
  stringStack: [""]
  
Exited nesting level 2
Decoded 3[b] → "bbb", combined with "a" → "abbb"
```

---

**Step 8: Process ']' (Second Closing)**
```
Character: ']' (closing bracket)
Action: Pop and decode
  count = countStack.pop() = 2
  prevString = stringStack.pop() = ""
  
  Build: "" + "abbb" * 2
       = "" + "abbbabbb"
       = "abbbabbb"
  
  currentString = "abbbabbb"

State:
  currentNum: 0
  currentString: "abbbabbb"
  countStack: []
  stringStack: []
  
Exited nesting level 1
Decoded 2[abbb] → "abbbabbb"
```

---

**Step 9: Process 'c'**
```
Character: 'c' (letter)
Action: Append to currentString
  currentString = "abbbabbb" + "c" = "abbbabbbc"

State:
  currentNum: 0
  currentString: "abbbabbbc"
  countStack: []
  stringStack: []
```

---

**Final Result:**
```
currentString: "abbbabbbc"
Return: "abbbabbbc" ✓
```

---

### Nesting Visualization

```
Input: "2[a3[b]]c"

Nesting structure:
┌─────────────────┐
│ 2[           ]  │ Level 1: repeat 2 times
│   ┌─────────┐   │
│   │ a3[b]   │   │ Content: "a" + 3[b]
│   │  └──┘   │   │
│   │  "bbb"  │   │ Level 2: repeat 3 times → "bbb"
│   │         │   │
│   │ "abbb"  │   │ Combined: "a" + "bbb" = "abbb"
│   └─────────┘   │
│                 │
│ "abbbabbb"      │ Repeated: "abbb" * 2 = "abbbabbb"
└─────────────────┘

Add final 'c': "abbbabbbc"
```

---

### Another Example: "3[a2[c]]"

```
Step-by-step decoding:

Input: 3[a2[c]]

Innermost first:
  2[c] = "cc"
  
Substitute:
  3[acc] (because we have "a" before 2[c])
  
Outer:
  3[acc] = "accaccacc"

Result: "accaccacc"

Stack trace:
  '3': num=3
  '[': push 3, push ""
  'a': str="a"
  '2': num=2
  '[': push 2, push "a"
  'c': str="c"
  ']': pop→2,"a" → "a"+"cc"="acc"
  ']': pop→3,"" → ""+"acc"*3="accaccacc"
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Lines | Clarity | Recommended |
|----------|------|-------|------------|---------|-------------|
| **Two Stacks** | **O(n)** | **O(n)** | **~30** | **Excellent ✅** | **Yes ✅** |
| Single Stack (Pairs) | O(n) | O(n) | ~28 | Excellent | Cleaner |
| Recursion | O(n) | O(n) | ~25 | Good | Elegant |

**All optimal approaches have O(n) time and space**

**Recommendation**: **Two Stacks** — explicit state management, easiest to understand!

---

## Key Takeaways

1. **Use two stacks** — one for counts, one for previous strings
2. **Process character by character** — digit, '[', ']', or letter
3. **Accumulate multi-digit numbers** — num = num*10 + digit
4. **Push state on '['** — save count and prevString, then reset
5. **Pop and decode on ']'** — build prevString + repeat(currentString, count)
6. **Use StringBuilder** — avoid O(n²) string concatenation
7. **Reset after '['** — start fresh for new nesting level
8. **Return currentString** — final decoded result
9. **O(n) time** — n is decoded string length

---

## Interview Tips

**What to say in an interview:**

> "This problem requires decoding a string with the pattern k[encoded_string] where the string inside brackets is repeated k times. The challenge is handling nested brackets. I'll use two stacks: one for repetition counts and one for previous strings. As I process each character, if it's a digit I'll accumulate it into the current number (handling multi-digit numbers). When I see '[', I'll push the current count and string onto the stacks and reset. When I see ']', I'll pop from both stacks to get the count and previous string, then build the result by appending the current string repeated count times to the previous string. Letters just get appended to the current string. This handles arbitrary nesting levels automatically. Time complexity is O(n) where n is the decoded string length, space is O(n) for the stacks and result."

**Key points to mention:**
1. **Two stacks** — count and prevString
2. **Four character types** — digit, '[', ']', letter
3. **Multi-digit numbers** — accumulate with num*10+digit
4. **State management** — push/reset on '[', pop/decode on ']'
5. **StringBuilder** — efficient string building
6. **Nesting** — stacks handle automatically
7. **O(n) complexity** — linear in output size

**Common Follow-ups:**
- "How do you handle multi-digit numbers?" → Accumulate: num = num*10 + digit
- "What if brackets are invalid?" → Problem guarantees valid input
- "Can you do it without stacks?" → Recursion possible, uses call stack
- "What's the space complexity?" → O(n) for stacks and result

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Decode String** | Medium | **Stack + Nested Structure** | **This problem** |
| Basic Calculator II | Medium | Stack + Expression Parsing | Operators and precedence |
| Simplify Path | Medium | Stack + String Processing | Unix path navigation |
| Remove K Digits | Medium | Monotonic Stack | Digit removal |
| Valid Parentheses | Easy | Stack + Matching | Bracket validation |
| Asteroid Collision | Medium | Stack + Simulation | Collision detection |

**Pattern Progression**:
1. **String decoding** (this problem) — Nested repetition
2. **Expression evaluation** (Calculator) — Operator precedence
3. **Path simplification** (Simplify Path) — Directory navigation
4. **Bracket matching** (Valid Parentheses) — Structure validation

---

## Final Pattern Label

✅ **Stack (Nested Structure / Bracket Matching with State Management)**

**Remember:** Use **two stacks** — one for counts, one for previous strings. Process each character: **digit** → accumulate (num*10+digit), **'['** → push state and reset, **']'** → pop and decode (prevString + repeat), **letter** → append. Must **reset** currentNum and currentString after '[' to start fresh for nested level. Use **StringBuilder** for efficiency. Build result: prevString + currentString.repeat(count). **O(n) time** where n is decoded length, **O(n) space** for stacks. Handles arbitrary nesting automatically!
