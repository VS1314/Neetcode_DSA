# Valid Parentheses

## Problem Description

**Difficulty**: Easy

You are given a string `s` consisting of the following characters: `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`.

The input string `s` is valid if and only if:

1. Every open bracket is closed by the same type of close bracket.
2. Open brackets are closed in the correct order.
3. Every close bracket has a corresponding open bracket of the same type.

Return `true` if `s` is a valid string, and `false` otherwise.

## Examples

### Example 1:
```
Input: s = "[]"
Output: true
Explanation: Opening '[' is closed by matching ']'.
```

### Example 2:
```
Input: s = "([{}])"
Output: true
Explanation: 
Each opening bracket has matching closing bracket in correct order.
'(' matches with ')'
'[' matches with ']'
'{' matches with '}'
```

### Example 3:
```
Input: s = "[(])"
Output: false
Explanation: 
The brackets are not closed in the correct order.
'[' is opened, then '(' is opened, but ')' closes before '[' closes.
Correct order would require ']' before ')'.
```

### Example 4:
```
Input: s = "(){}[]"
Output: true
Explanation: Three separate valid pairs in sequence.
```

### Example 5:
```
Input: s = "((("
Output: false
Explanation: Opening brackets without closing ones.
```

### Example 6:
```
Input: s = ")))"
Output: false
Explanation: Closing brackets without opening ones.
```

## Constraints
- 1 <= s.length <= 10,000
- s consists of parentheses only: '(', ')', '{', '}', '[', ']'

**Recommended Complexity**: O(n) time, O(n) space where n is the length of string

---

## Pattern Recognition

**Primary Pattern**: **Stack (Bracket Matching / Balanced Parentheses)**

**Why This Pattern?**
- Need to match opening and closing brackets
- Closing bracket must match most recent unmatched opening bracket
- LIFO (Last In, First Out) behavior
- Order matters: must close in reverse order of opening

**Key Insight**: Most Recent Unmatched Opening
```
Problem: Validate bracket matching and order

Critical observation:
  A closing bracket MUST match the MOST RECENT unmatched opening bracket
  
Example: "([{}])"
  '(' opens
  '[' opens (most recent)
  '{' opens (most recent)
  '}' must match '{' (most recent) ✓
  ']' must match '[' (now most recent) ✓
  ')' must match '(' (now most recent) ✓
  
Key insight: "Most recent unmatched" = LIFO = Stack!
```

**Why Stack?**
```
Problem characteristics:
  ✓ Need to track opening brackets
  ✓ Need to access most recent opening
  ✓ Remove opening when matched
  ✓ Order matters (nested structure)
  
Stack operations match perfectly:
  - Opening bracket: push() → O(1)
  - Closing bracket: peek() to check, pop() to remove → O(1)
  - Final check: isEmpty() → all matched
  
Example trace:
  s = "([{}])"
  
  '(' → push '(' → stack = ['(']
  '[' → push '[' → stack = ['(', '[']
  '{' → push '{' → stack = ['(', '[', '{']
  '}' → peek '{', matches! pop → stack = ['(', '[']
  ']' → peek '[', matches! pop → stack = ['(']
  ')' → peek '(', matches! pop → stack = []
  
  Stack empty → all brackets matched → valid!
```

**The Matching Strategy**:
```
For each character in string:
  
  Case 1: Opening bracket ('(', '[', '{')
    → Push to stack
    → Wait for corresponding closing bracket
  
  Case 2: Closing bracket (')', ']', '}')
    → Check if stack empty (no opening to match) → invalid
    → Pop top of stack (most recent opening)
    → Check if types match:
        ')' must match '('
        ']' must match '['
        '}' must match '{'
    → If mismatch → invalid
  
Final check: Stack must be empty
  - Not empty → extra opening brackets → invalid
  - Empty → all matched → valid
```

**Why Order Matters**:
```
Valid: Nested or sequential matching
  "([])" → valid (properly nested)
  "()[]" → valid (sequential)
  "([{}])" → valid (deeply nested)
  
Invalid: Improper interleaving
  "[(])" → invalid!
    '[' opens
    '(' opens
    ')' closes '(' ✓ BUT '[' still open!
    ']' closes '[' ✓
    
  Problem: '(' and ')' are INSIDE '[' and ']'
           But they close BEFORE the outer pair completes!
  
  Visualization:
    [ ( ] )
    └─┘ └─┘  ❌ Overlap, not properly nested!
    
  Correct:
    [ ( ) ]
    └───┘ └─┘  ✓ Properly nested!
```

**Critical Detail**: Type Matching
```
Not enough to just count brackets:
  "([)]" has 2 opening, 2 closing → counts match ✓
  But types don't align properly → invalid ❌
  
  '[' opens
  '(' opens
  ')' closes... should match '(' but '[' is still outer!
  ']' closes... should match '[' but '(' was between!
  
  This is improper interleaving!

Must check: Closing type matches opening type
  ')' ↔ '('
  ']' ↔ '['
  '}' ↔ '{'
```

**Related Patterns**:
1. **Stack** — LIFO data structure
2. **Matching Pairs** — Find corresponding elements
3. **Bracket Problems** — Parentheses, expressions
4. **Balanced Structure** — Nested validation

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Repeatedly remove valid pairs
  while string contains "()" or "[]" or "{}":
    remove first occurrence
  if string empty: return true
  
Time: O(n²) — Each removal takes O(n), up to n removals
Space: O(n) — String manipulation
Too slow! ❌

Stack Approach:
  Single pass through string: O(n)
  Each character processed once
  → O(n) time ✅
  → O(n) space for stack
```

**The Optimal Strategy**:
```
Key observations:
  1. Closing bracket must match most recent opening
  2. Stack top always contains most recent opening
  3. If stack empty when closing bracket arrives → no match
  4. If stack not empty at end → unmatched openings
  
Algorithm:
  1. Iterate through string
  2. Opening bracket → push to stack
  3. Closing bracket → check match with stack top, pop
  4. End: stack must be empty
  
Why it works:
  Stack maintains "waiting to be closed" brackets
  When closing arrives, it must match top (most recent waiting)
  If all valid, all openings will be matched and popped
```

### Step-by-Step Algorithm

---

#### **Approach 1: Stack with Character Matching (OPTIMAL)**

**Core Idea**:
- Use stack to store opening brackets
- For closing brackets, check if they match stack top
- Stack must be empty at end (all brackets matched)

**Algorithm**
```
isValid(s):
    stack = empty stack
    
    for each char in s:
        if char is opening bracket:
            push char to stack
        
        else:  // char is closing bracket
            if stack is empty:
                return false  // No opening to match
            
            opening = pop from stack
            
            if not matches(opening, char):
                return false  // Type mismatch
    
    return stack is empty  // True if all matched
```

**Code Implementation**
```java
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            // Opening brackets: push to stack
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }
            // Closing brackets: check matching
            else {
                // No opening bracket to match
                if (stack.isEmpty()) {
                    return false;
                }
                
                char top = stack.pop();
                
                // Check if types match
                if (c == ')' && top != '(') return false;
                if (c == ']' && top != '[') return false;
                if (c == '}' && top != '{') return false;
            }
        }
        
        // All brackets must be matched (stack empty)
        return stack.isEmpty();
    }
}
```

**Example Walkthrough**

Input: `s = "([{}])"`

| Step | Char | Action | Stack | Explanation |
|------|------|--------|-------|-------------|
| Init | - | - | [] | Empty stack |
| 1 | '(' | Push | ['('] | Opening bracket |
| 2 | '[' | Push | ['(', '['] | Opening bracket |
| 3 | '{' | Push | ['(', '[', '{'] | Opening bracket |
| 4 | '}' | Pop & Match | ['(', '['] | '}' matches '{' ✓ |
| 5 | ']' | Pop & Match | ['('] | ']' matches '[' ✓ |
| 6 | ')' | Pop & Match | [] | ')' matches '(' ✓ |
| End | - | Check empty | [] | Stack empty ✓ |

**Output:** `true`

**Complexity Analysis**
- **Time Complexity**: O(n) — Single pass through string, each operation O(1)
- **Space Complexity**: O(n) — Stack stores at most n/2 opening brackets

---

#### **Approach 2: Stack with HashMap for Matching**

**Core Idea**: Use HashMap to map closing to opening brackets.

**Code Implementation**
```java
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        
        // Map closing brackets to opening brackets
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put(']', '[');
        pairs.put('}', '{');
        
        for (char c : s.toCharArray()) {
            // If closing bracket
            if (pairs.containsKey(c)) {
                // Check if stack has matching opening
                if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                    return false;
                }
            }
            // Opening bracket
            else {
                stack.push(c);
            }
        }
        
        return stack.isEmpty();
    }
}
```

**Key Difference**: 
- Uses HashMap for cleaner matching logic
- Slightly more scalable (easy to add new bracket types)
- Conceptually cleaner separation

**Complexity Analysis**
- **Time Complexity**: O(n) — Same as approach 1
- **Space Complexity**: O(n) — Stack + constant HashMap

---

#### **Approach 3: Stack with Switch Statement**

**Core Idea**: Use switch for explicit matching.

**Code Implementation**
```java
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            switch (c) {
                case '(':
                case '[':
                case '{':
                    stack.push(c);
                    break;
                    
                case ')':
                    if (stack.isEmpty() || stack.pop() != '(') return false;
                    break;
                    
                case ']':
                    if (stack.isEmpty() || stack.pop() != '[') return false;
                    break;
                    
                case '}':
                    if (stack.isEmpty() || stack.pop() != '{') return false;
                    break;
            }
        }
        
        return stack.isEmpty();
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) — Same performance
- **Space Complexity**: O(n) — Stack only
- **Note**: Most explicit, good for interviews

---

#### **Approach 4: Brute Force with String Replacement**

**Core Idea**: Repeatedly remove valid pairs until none remain.

**Code Implementation**
```java
class Solution {
    public boolean isValid(String s) {
        while (s.contains("()") || s.contains("[]") || s.contains("{}")) {
            s = s.replace("()", "");
            s = s.replace("[]", "");
            s = s.replace("{}", "");
        }
        
        return s.isEmpty();
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n²) — Up to n removals, each O(n)
- **Space Complexity**: O(n) — String manipulation
- **Why Not Optimal**: Too slow for large inputs

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | HashMap | Switch | **If-Else** |
|-------------|-------------|---------|--------|-------------|
| Time complexity | O(n²) ❌ | O(n) ✓ | O(n) ✓ | **O(n) ✅** |
| Space complexity | O(n) ✓ | O(n) ✓ | O(n) ✓ | **O(n) ✅** |
| Code simplicity | ✓ | ✓ | Medium | **✅** |
| Readability | ✓ | ✅ | ✅ | **✅** |
| Interview friendly | ❌ | ✅ | ✅ | **✅** |

**Winner**: **Stack with if-else or HashMap** — optimal, clean, and interview-friendly!

### Why Stack is Essential?

```
Stack property: LIFO (Last In, First Out)
  Matches bracket nesting perfectly!
  
Example: "((()))"
  Push '(' → stack = ['(']
  Push '(' → stack = ['(', '(']
  Push '(' → stack = ['(', '(', '(']
  Pop ')' matches '(' → stack = ['(', '(']
  Pop ')' matches '(' → stack = ['(']
  Pop ')' matches '(' → stack = []
  
  Each closing matches the MOST RECENT opening
  That's exactly what pop() gives us!

Without stack (using queue/array)?
  Queue (FIFO): Would match in wrong order
    Push '(', '(', '('
    Pop would give first '(' not last!
  
  Array: Need to track index, manual management
    More error-prone, less elegant
```

### Why HashMap is Elegant?

```
Without HashMap:
  if (c == ')' && top != '(') return false;
  if (c == ']' && top != '[') return false;
  if (c == '}' && top != '{') return false;
  → Repetitive, error-prone

With HashMap:
  if (stack.pop() != pairs.get(c)) return false;
  → Single check, DRY principle
  
Scalability:
  Add new bracket type: pairs.put('>', '<');
  No need to modify logic!
```

### Why Check isEmpty() at End?

```
Case: Extra opening brackets
  s = "((("
  All opening, nothing to pop for
  Stack at end: ['(', '(', '('] (not empty)
  → Invalid! ✓

Case: All matched
  s = "()"
  Push '(', pop for ')'
  Stack at end: [] (empty)
  → Valid! ✓

Empty stack = All brackets matched!
```

---

## Critical Edge Cases & Gotchas

### 1. **Empty String**
```java
Input: s = ""
Output: true
Explanation: Empty string is valid (no brackets to match).
Note: Constraints say 1 <= s.length, so might not occur.
```

### 2. **Single Opening Bracket**
```java
Input: s = "("
Output: false
Explanation: Opening without closing.
Stack at end: ['('] (not empty).
```

### 3. **Single Closing Bracket**
```java
Input: s = ")"
Output: false
Explanation: Closing without opening.
Stack empty when ')' arrives → return false immediately.
```

### 4. **Wrong Order**
```java
Input: s = "[(])"
Output: false
Explanation:
'[' → push → ['[']
'(' → push → ['[', '(']
')' → pop '(' matches ✓ → ['[']
']' → pop '[' matches ✓ → []
But structure is wrong! '[' and ']' are not properly nested around '(' and ')'.

Actually, this SHOULD be valid by the algorithm!
Let me reconsider...

Actually wait, let me trace again:
'[' → push → ['[']
'(' → push → ['[', '(']
']' → pop '(' → '(' != '[' → return false ✓

Ah, the ')' comes after '[', so we read it wrong.
s = "[(])"
Index: 0=[ 1=( 2=] 3=)

'[' → push → ['[']
'(' → push → ['[', '(']
']' → pop '(' but ']' expects '[' → mismatch → false ✓
```

### 5. **All Same Type**
```java
Input: s = "()()()"
Output: true
Explanation: Sequential matching, all valid.
```

### 6. **Deeply Nested**
```java
Input: s = "(((())))""
Output: true
Explanation: Properly nested, all match.
```

### 7. **Mixed Types**
```java
Input: s = "()[]{}"
Output: true
Explanation: Three separate valid pairs.
```

### 8. **Extra Closing**
```java
Input: s = "())"
Output: false
Explanation:
'(' → push → ['(']
')' → pop matches ✓ → []
')' → stack empty → return false ✓
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Checking Stack Empty for Closing Bracket**
```java
// WRONG - doesn't check if stack is empty
else {
    char top = stack.pop();  // WRONG! What if stack empty?
    if (c == ')' && top != '(') return false;
}
```

**Why wrong**: If stack empty, pop() throws exception!

**Dry run failure for s=")":**
```
')' arrives
Stack is empty
pop() → EmptyStackException! ❌

Should check first:
if (stack.isEmpty()) return false;
Then pop safely.
```

**Fix**: Check isEmpty() first
```java
if (stack.isEmpty()) {
    return false;
}
char top = stack.pop();
```

### ❌ **MISTAKE 2: Not Checking Stack Empty at End**
```java
// WRONG - returns true without checking
for (char c : s.toCharArray()) {
    // ... process ...
}
return true;  // WRONG! What if stack not empty?
```

**Why wrong**: Extra opening brackets not detected!

**Dry run failure for s="(((":**
```
'(' → push → ['(']
'(' → push → ['(', '(']
'(' → push → ['(', '(', '(']
End of loop
Return true ❌

But stack not empty! Should return false.
```

**Fix**: Return stack.isEmpty()
```java
return stack.isEmpty();
```

### ❌ **MISTAKE 3: Wrong Matching Logic**
```java
// WRONG - checks if closing matches closing
if (c == ')' && top != ')') return false;  // WRONG! Both closing
```

**Why wrong**: Compares closing with closing instead of closing with opening!

**Dry run failure:**
```
s = "()"
'(' → push → ['(']
')' → pop '(' → ')' != ')'? False, so doesn't return
Result: true (accidentally correct!)

But logic is wrong! Should be:
if (c == ')' && top != '(') return false;
```

**Fix**: Match closing with corresponding opening
```java
if (c == ')' && top != '(') return false;
if (c == ']' && top != '[') return false;
if (c == '}' && top != '{') return false;
```

### ❌ **MISTAKE 4: Using Queue Instead of Stack**
```java
// WRONG - uses queue (FIFO instead of LIFO)
Queue<Character> queue = new LinkedList<>();
for (char c : s.toCharArray()) {
    if (c == '(') queue.offer(c);
    else if (c == ')') {
        if (queue.isEmpty()) return false;
        queue.poll();  // WRONG! Removes first, not last
    }
}
```

**Why wrong**: Queue removes in FIFO order, not LIFO!

**Dry run failure for s="(())":**
```
'(' → offer → ['(']
'(' → offer → ['(', '(']
')' → poll removes first '(' → ['(']
  But should remove most recent '('!
')' → poll removes remaining '(' → []

Result: true (accidentally correct for this case!)

But fails for: s="([)]"
'(' → ['(']
'[' → ['(', '[']
')' → poll '(' ✓
']' → poll '[' ✓
Result: true ❌ (should be false!)

Wrong because:
  ')' should match most recent '[', not '('
  Queue gives us '(' (oldest), not '[' (newest)
```

**Fix**: Use Stack, not Queue
```java
Stack<Character> stack = new Stack<>();
```

### ❌ **MISTAKE 5: Forgetting to Pop**
```java
// WRONG - checks match but doesn't pop
else {
    if (stack.isEmpty()) return false;
    char top = stack.peek();  // WRONG! Should pop, not just peek
    if (c == ')' && top != '(') return false;
}
```

**Why wrong**: Stack never shrinks, remains non-empty!

**Dry run failure for s="()":**
```
'(' → push → ['(']
')' → peek '(', matches ✓
  But don't pop! Stack still ['(']
End: stack not empty → return false ❌

Should be true!
```

**Fix**: Use pop() not peek()
```java
char top = stack.pop();
```

### ❌ **MISTAKE 6: Returning True on Match Instead of Continue**
```java
// WRONG - returns true immediately on match
else {
    if (stack.isEmpty()) return false;
    char top = stack.pop();
    if (c == ')' && top == '(') return true;  // WRONG! Premature return
}
```

**Why wrong**: Doesn't check rest of string!

**Dry run failure for s="()(":**
```
'(' → push → ['(']
')' → pop, matches → return true ✓
  But didn't check rest of string!
  Still have '(' unmatched!

Should continue and check all characters.
```

**Fix**: Don't return true on match, continue loop
```java
if (c == ')' && top != '(') return false;
// If match, continue (don't return true)
```

### ❌ **MISTAKE 7: Not Handling All Three Bracket Types**
```java
// WRONG - only handles one type
for (char c : s.toCharArray()) {
    if (c == '(') stack.push(c);
    else if (c == ')') {
        if (stack.isEmpty() || stack.pop() != '(') return false;
    }
    // WRONG! Missing '[', ']', '{', '}'
}
```

**Why wrong**: Ignores other bracket types!

**Dry run failure for s="[]":**
```
'[' → Not '(', not ')', skip (WRONG!)
']' → Not '(', not ')', skip (WRONG!)
End: stack empty → return true ❌

But we never checked '[' and ']'!
Accidentally correct but wrong logic!
```

**Fix**: Handle all six characters
```java
if (c == '(' || c == '[' || c == '{') {
    stack.push(c);
} else {
    // Handle ')', ']', '}'
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Iterate string | O(n) | Visit each character once |
| Push to stack | O(1) | Per operation |
| Pop from stack | O(1) | Per operation |
| **Total** | **O(n)** | Each of n characters processed once |

**Why O(n) not O(n²)?**
```
Each character processed exactly once:
  - Opening: push() → O(1)
  - Closing: isEmpty() + pop() + compare → O(1)
  
Total: n characters × O(1) = O(n)

Contrast with brute force:
  Each removal: O(n) string replacement
  Up to n removals
  Total: O(n²) ❌
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Stack | O(n) | Worst case: all opening brackets |
| Variables | O(1) | Char, boolean |
| **Total** | **O(n)** | Dominated by stack |

**Why O(n)?**
```
Worst case: All opening brackets
  s = "((((("
  Each character pushed to stack
  Stack size: n
  → O(n) space

Best case: Alternating matched pairs
  s = "()()()"
  Stack size never exceeds 1
  → O(1) space
  
Average: O(n/2) ≈ O(n)
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `s = "([{}])"`

**Goal:** Validate bracket matching.

---

**Step 1: Initialize**
```
s = "([{}])"
stack = []
```

---

**Step 2: Process '('**
```
Character: '('
Type: Opening bracket

Action: Push to stack
Stack: ['(']

Explanation: Wait for matching ')'
```

---

**Step 3: Process '['**
```
Character: '['
Type: Opening bracket

Action: Push to stack
Stack: ['(', '[']

Explanation: Wait for matching ']'
```

---

**Step 4: Process '{'**
```
Character: '{'
Type: Opening bracket

Action: Push to stack
Stack: ['(', '[', '{']

Explanation: Wait for matching '}'
```

---

**Step 5: Process '}'**
```
Character: '}'
Type: Closing bracket

Action:
  1. Check stack not empty: ✓
  2. Pop top: '{' 
  3. Check match: '}' matches '{' ✓

Stack: ['(', '[']

Explanation: Successfully matched '{' and '}'
```

---

**Step 6: Process ']'**
```
Character: ']'
Type: Closing bracket

Action:
  1. Check stack not empty: ✓
  2. Pop top: '['
  3. Check match: ']' matches '[' ✓

Stack: ['(']

Explanation: Successfully matched '[' and ']'
```

---

**Step 7: Process ')'**
```
Character: ')'
Type: Closing bracket

Action:
  1. Check stack not empty: ✓
  2. Pop top: '('
  3. Check match: ')' matches '(' ✓

Stack: []

Explanation: Successfully matched '(' and ')'
```

---

**Step 8: Final Check**
```
End of string reached
Stack: [] (empty)

Check: stack.isEmpty() → true ✓

Result: true (all brackets matched)
```

---

**Final Result:** `true`

### Invalid Example Walkthrough

**Input:** `s = "[(])"`

```
Step-by-step:

'[' → Opening → push → ['[']
'(' → Opening → push → ['[', '(']
']' → Closing → pop '(' 
     Check: ']' matches '('? NO! ❌
     Expected '[' but got '('
     
Return false immediately

Explanation:
  ']' arrives but most recent opening is '('
  Brackets are interleaved improperly:
    [ ( ] )
    └─┘ └─┘  → Overlapping, not nested!
```

### Visual Bracket Structure

```
Valid (properly nested):
  ( [ { } ] )
  └─────────┘
    └───────┘
      └───┘

Invalid (improper interleaving):
  ( [ ) ]
  └─┘ └─┘  → Overlap!
  
  '[' opens, '(' opens, ')' closes
  But '(' closes before '[' closes!
  Improper nesting!
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Simplicity | Interview Friendly |
|----------|------|-------|-----------------|-------------------|
| **Stack + If-Else** | **O(n)** | **O(n)** | **✅** | **✅** |
| Stack + HashMap | O(n) | O(n) | ✅ | ✅ |
| Stack + Switch | O(n) | O(n) | ✓ | ✅ |
| Brute Force | O(n²) | O(n) | ✅ | ❌ |

**Recommendation**: Use **Stack with If-Else or HashMap** — optimal and clean!

---

## Key Takeaways

1. **Stack for LIFO matching** — closing must match most recent opening
2. **Check isEmpty() before pop** — avoid exception
3. **Check isEmpty() at end** — detect unmatched openings
4. **Match closing to opening** — ')' ↔ '(', not ')' ↔ ')'
5. **Handle all three types** — '()', '[]', '{}'
6. **Continue on match** — don't return true prematurely
7. **O(n) time, O(n) space** — single pass, stack size bounded by n

---

## Interview Tips

**What to say in an interview:**

> "This is a classic stack problem for bracket matching. The key insight is that a closing bracket must match the most recent unmatched opening bracket, which is exactly what a stack's LIFO property provides. I'll iterate through the string once. For opening brackets, I push them onto the stack. For closing brackets, I first check if the stack is empty — if so, there's no opening to match and I return false. Otherwise, I pop the stack and verify the types match: ')' must match '(', ']' must match '[', and '}' must match '{'. If there's a type mismatch, I return false. After processing all characters, I check if the stack is empty — if it is, all brackets were properly matched and I return true; otherwise, there are unmatched opening brackets and I return false. This gives O(n) time with a single pass and O(n) space for the stack."

**Key points to mention:**
1. **Stack for LIFO** — matches nesting structure
2. **Most recent opening** — closing must match stack top
3. **Three checks** — empty before pop, type match, empty at end
4. **Type matching** — each closing has corresponding opening
5. **Complexity** — O(n) time, O(n) space

**If asked about alternatives:**
> "I could use a HashMap to map closing brackets to their corresponding opening brackets, which makes the matching logic cleaner and more scalable. The time and space complexity remain the same. A brute force approach would repeatedly remove valid pairs like '()' from the string, but that would be O(n²) time due to repeated string operations. The stack approach is optimal."

**Common Follow-ups:**
- "What if there are other characters?" → Ignore non-bracket characters
- "What about nested validity?" → Stack handles arbitrary nesting depth
- "Can you use less space?" → No, worst case requires O(n) stack (all opening brackets)
- "What if we add more bracket types?" → Just add more cases, HashMap makes this easy

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Valid Parentheses** | Easy | **Stack (Matching)** | **This problem** |
| Generate Parentheses | Medium | Backtracking + Stack | Generate valid combinations |
| Longest Valid Parentheses | Hard | Stack + DP | Find longest valid substring |
| Minimum Add to Make Parentheses Valid | Medium | Stack / Counter | Count insertions needed |
| Remove Invalid Parentheses | Hard | BFS / DFS | Remove minimum to make valid |
| Score of Parentheses | Medium | Stack | Calculate score from structure |
| Valid Parenthesis String | Medium | Stack + Greedy | Handles '*' wildcard |

**Pattern Progression**:
1. **Basic matching** (this problem) — Valid Parentheses
2. **Generation** (medium) — Generate valid combinations
3. **Optimization** (hard) — Longest valid, minimum changes

---

## Final Pattern Label

✅ **Stack (Bracket Matching / Balanced Parentheses)**

**Remember:** Use a stack to track opening brackets. When a closing bracket arrives, it must match the most recent unmatched opening (stack top). Check three things: (1) stack not empty before popping, (2) types match (closing ↔ opening), and (3) stack empty at end (all matched). This handles arbitrary nesting depth with O(n) time and O(n) space!
