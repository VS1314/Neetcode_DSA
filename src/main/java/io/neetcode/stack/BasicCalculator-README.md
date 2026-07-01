# Basic Calculator

## Problem Description

**Difficulty**: Hard

Given a string `s` representing a **valid expression**, implement a **basic calculator** to evaluate it, and return the result of the evaluation.

**Note:** You are **not** allowed to use any built-in function which evaluates strings as mathematical expressions, such as `eval()`.

**Operations Supported:**
- **Addition** (`+`)
- **Subtraction** (`-`)
- **Parentheses** (`(` and `)`)
- **Unary minus** (e.g., `-1`, `-(2 + 3)`)

**Important Rules:**
- `'+'` is **not** used as a unary operation (i.e., `"+1"` and `"+(2 + 3)"` is invalid)
- `'-'` **could** be used as a unary operation (i.e., `"-1"` and `"-(2 + 3)"` is valid)
- There will be **no two consecutive operators** in the input
- Every number and running calculation will fit in a **signed 32-bit integer**

## Examples

### Example 1:
```
Input: s = "1 + 1"
Output: 2

Explanation:
  1 + 1 = 2
```

### Example 2:
```
Input: s = " 2-1 + 2 "
Output: 3

Explanation:
  2 - 1 + 2 = 1 + 2 = 3
```

### Example 3:
```
Input: s = "(1+(4+5+2)-3)+(6+8)"
Output: 23

Explanation:
  Inner parentheses first:
    4+5+2 = 11
    (1+11-3) = 9
    6+8 = 14
    9+14 = 23
```

### Example 4:
```
Input: s = "2-(5-6)"
Output: 3

Explanation:
  5-6 = -1
  2-(-1) = 2+1 = 3
```

### Example 5:
```
Input: s = "1-(2+3)"
Output: -4

Explanation:
  2+3 = 5
  1-5 = -4
```

### Example 6:
```
Input: s = "-(2+3)"
Output: -5

Explanation:
  Unary minus at start
  2+3 = 5
  -5 = -5
```

### Example 7:
```
Input: s = "(1)"
Output: 1

Explanation:
  Parentheses don't change value
```

### Example 8:
```
Input: s = "1+2+3+4+5"
Output: 15

Explanation:
  No parentheses, simple addition
```

### Example 9:
```
Input: s = "10-2-3"
Output: 5

Explanation:
  Left to right: (10-2)-3 = 8-3 = 5
```

### Example 10:
```
Input: s = "1-(5)"
Output: -4

Explanation:
  1-5 = -4
```

### Example 11:
```
Input: s = "(7)-(0)+(4)"
Output: 11

Explanation:
  7-0+4 = 11
```

### Example 12:
```
Input: s = "-2+ 1"
Output: -1

Explanation:
  Unary minus: -2
  -2+1 = -1
```

## Constraints
- 1 <= s.length <= 3 * 10^5
- `s` consists of digits, `'+'`, `'-'`, `'('`, `')'`, and `' '` (space)
- `s` represents a **valid expression**
- `'+'` is **not** used as a unary operation
- `'-'` **could** be used as a unary operation
- There will be **no two consecutive operators** in the input
- Every number and running calculation will fit in a **signed 32-bit integer**

**Recommended Complexity**: O(n) time and O(n) space, where n is the length of the string

---

## Pattern Recognition

**Primary Pattern**: **Stack (Expression Evaluation with Parentheses)**

**Why This Pattern?**
- Parentheses create nested scopes that need LIFO processing
- When we see '(', we need to save current state
- When we see ')', we need to restore previous state
- Stack perfectly models scope nesting

**Key Insight**: Stack Saves State for Parentheses
```
Basic Calculator problem:
  Expression: "(1+(4+5+2)-3)+(6+8)"
  Operations: + and - only (no * or /)
  Challenge: Handle nested parentheses
  
When we see '(':
  Must save current result and sign
  Start fresh calculation inside parentheses
  
When we see ')':
  Complete calculation inside parentheses
  Restore previous result and sign
  Apply saved sign to parentheses result
  
Example: "2-(5-6)"
  
  Process '2': result = 2
  Process '-': sign = -1
  Process '(': 
    Save (result=2, sign=-1) to stack
    Reset: result=0, sign=+1
  Process '5': result = 5
  Process '-': sign = -1
  Process '6': result = 5 + (-1)*6 = -1
  Process ')':
    Pop (prevResult=2, prevSign=-1)
    result = prevResult + prevSign * result
           = 2 + (-1) * (-1)
           = 2 + 1
           = 3
```

**The Stack Strategy**:
```
Stack stores pairs: (previousResult, previousSign)

Processing algorithm:
  1. Build multi-digit numbers digit by digit
  2. Apply sign when we complete a number
  3. On '(': push (result, sign), reset for inner expression
  4. On ')': pop (prevResult, prevSign), combine with current result
  5. Skip spaces
  
Variables needed:
  result: current running total
  sign: current sign (+1 or -1)
  num: current number being built
  stack: saves (result, sign) for parentheses
```

**Character Processing Rules**:
```
For each character c:

  1. If c is digit:
     - Build number: num = num * 10 + (c - '0')
     
  2. If c is '+':
     - Apply previous number: result += sign * num
     - Update sign: sign = +1
     - Reset num: num = 0
     
  3. If c is '-':
     - Apply previous number: result += sign * num
     - Update sign: sign = -1
     - Reset num: num = 0
     
  4. If c is '(':
     - Push (result, sign) to stack
     - Reset: result = 0, sign = +1
     - Keep num = 0
     
  5. If c is ')':
     - Apply current number: result += sign * num
     - Pop (prevResult, prevSign) from stack
     - Combine: result = prevResult + prevSign * result
     - Reset num: num = 0
     
  6. If c is space:
     - Skip
```

**Example Showing Stack Evolution**:
```
Input: "1-(2+3)"

char '1':
  num = 1
  result = 0, sign = +1, stack = []

char '-':
  result += sign * num = 0 + 1*1 = 1
  sign = -1, num = 0
  result = 1, sign = -1, stack = []

char '(':
  Push (result=1, sign=-1)
  Reset: result = 0, sign = +1
  result = 0, sign = +1, stack = [(1, -1)]

char '2':
  num = 2
  result = 0, sign = +1, stack = [(1, -1)]

char '+':
  result += sign * num = 0 + 1*2 = 2
  sign = +1, num = 0
  result = 2, sign = +1, stack = [(1, -1)]

char '3':
  num = 3
  result = 2, sign = +1, stack = [(1, -1)]

char ')':
  result += sign * num = 2 + 1*3 = 5
  Pop (prevResult=1, prevSign=-1)
  result = prevResult + prevSign * result
         = 1 + (-1) * 5
         = 1 - 5
         = -4
  num = 0
  result = -4, sign = -1, stack = []

Final: -4 ✓
```

**Why Stack is Essential**:
```
Nested parentheses create scopes within scopes

Example: "((1+2)+3)"

First '(':
  Save outer scope (result=0, sign=+1)
  
Second '(':
  Save intermediate scope (result=0, sign=+1)
  
Process 1+2:
  result = 3
  
First ')':
  Pop intermediate scope
  Apply to get 3
  
Process +3:
  result = 6
  
Second ')':
  Pop outer scope
  Apply to get 6

Stack handles arbitrary nesting depth!
```

**Critical Detail**: When to Apply Number
```
Number must be applied in 3 cases:
  1. When we see next operator (+/-)
  2. When we see closing parenthesis ')'
  3. At end of string
  
Example: "1+2"
  
  char '1': num = 1
  char '+': result += sign * num (apply 1)
  char '2': num = 2
  END: result += sign * num (apply 2 - IMPORTANT!)

Without final application:
  "1+2" would give 1 instead of 3 ❌
```

**Handling Unary Minus**:
```
Unary minus appears at start or after '('

Example 1: "-2+1"
  Initial: result=0, sign=+1
  char '-': sign=-1 (num is 0, so result stays 0)
  char '2': num=2
  char '+': result += sign*num = 0 + (-1)*2 = -2 ✓

Example 2: "-(2+3)"
  Initial: result=0, sign=+1
  char '-': sign=-1
  char '(': push (0, -1), reset
  Inside: 2+3 = 5
  char ')': pop (0, -1), result = 0 + (-1)*5 = -5 ✓

Unary minus is just a sign before a number or parentheses!
No special handling needed.
```

**Related Patterns**:
1. **Stack** — Core technique for nested structures
2. **Expression Evaluation** — Parse and compute
3. **State Management** — Save/restore for scopes
4. **String Processing** — Character-by-character parsing

---

## Algorithm & Approach

### Core Insight

**Why Naive Approach Fails:**
```
Naive: Find innermost parentheses, evaluate, repeat
  while (has parentheses):
      find innermost pair
      evaluate that part
      replace in string
  
Problems:
  - O(n²) time — repeated string searching
  - String manipulation is expensive
  - Complex to handle signs correctly
  
Optimal approach:
  Single pass with stack
  → O(n) time, clean logic ✓
```

**The Optimal Strategy**:
```
Key observations:
  1. Process left to right, single pass
  2. Use stack to save (result, sign) at '('
  3. Pop and combine at ')'
  4. Build multi-digit numbers incrementally
  5. Track current sign (+1 or -1)
  
Operations:
  Process each char: O(1) per char
  Stack push/pop: O(1)
  
Total: O(n) single pass
```

### Step-by-Step Algorithm

---

#### **Approach 1: Stack with State Pairs - OPTIMAL**

**Core Idea**:
- Use stack to save (result, sign) when entering parentheses
- Track current result, sign, and number being built
- Apply operations as we encounter operators
- Pop from stack when exiting parentheses

**Algorithm**
```
calculate(s):
    stack = new Stack()
    result = 0
    sign = 1  // +1 for positive, -1 for negative
    num = 0
    
    for each char c in s:
        if c is digit:
            // Build multi-digit number
            num = num * 10 + (c - '0')
            
        else if c is '+':
            // Apply previous number
            result += sign * num
            num = 0
            sign = 1
            
        else if c is '-':
            // Apply previous number
            result += sign * num
            num = 0
            sign = -1
            
        else if c is '(':
            // Save current state
            stack.push(result)
            stack.push(sign)
            // Reset for new scope
            result = 0
            sign = 1
            
        else if c is ')':
            // Apply current number
            result += sign * num
            num = 0
            // Pop sign and previous result
            prevSign = stack.pop()
            prevResult = stack.pop()
            // Combine: prevResult + prevSign * (result from parentheses)
            result = prevResult + prevSign * result
            
        // Skip spaces
    
    // Apply final number
    result += sign * num
    
    return result
```

**Code Implementation**
```java
class Solution {
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        int sign = 1;  // 1 for +, -1 for -
        int num = 0;
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                // Build multi-digit number
                num = num * 10 + (c - '0');
                
            } else if (c == '+') {
                // Apply previous number with its sign
                result += sign * num;
                num = 0;
                sign = 1;
                
            } else if (c == '-') {
                // Apply previous number with its sign
                result += sign * num;
                num = 0;
                sign = -1;
                
            } else if (c == '(') {
                // Push current result and sign to stack
                stack.push(result);
                stack.push(sign);
                // Reset for expression inside parentheses
                result = 0;
                sign = 1;
                
            } else if (c == ')') {
                // Apply current number
                result += sign * num;
                num = 0;
                // Pop sign for this parentheses
                int prevSign = stack.pop();
                // Pop result before parentheses
                int prevResult = stack.pop();
                // Combine: previous result + sign * (result from parentheses)
                result = prevResult + prevSign * result;
            }
            // Skip spaces automatically
        }
        
        // Apply final number
        result += sign * num;
        
        return result;
    }
}
```

**Example Walkthrough**

Input: `s = "1-(2+3)"`

| Char | Type | Action | num | sign | result | stack |
|------|------|--------|-----|------|--------|-------|
| '1' | Digit | num=1 | 1 | +1 | 0 | [] |
| '-' | Operator | result+=1\*1=1, sign=-1 | 0 | -1 | 1 | [] |
| '(' | Open | Push 1, push -1, reset | 0 | +1 | 0 | [1, -1] |
| '2' | Digit | num=2 | 2 | +1 | 0 | [1, -1] |
| '+' | Operator | result+=1\*2=2, sign=+1 | 0 | +1 | 2 | [1, -1] |
| '3' | Digit | num=3 | 3 | +1 | 2 | [1, -1] |
| ')' | Close | result+=1\*3=5, pop -1, pop 1 | 0 | +1 | -4 | [] |
| | | result=1+(-1)\*5=-4 | | | | |

Final: Apply num=0 (no effect), return **-4**

**Complexity Analysis**
- **Time**: O(n) — Single pass through string
- **Space**: O(n) — Stack for nested parentheses (max depth)

---

#### **Approach 2: Stack with Pairs Object - CLEANER**

**Core Idea**: Store (result, sign) as explicit pairs for clarity.

**Code Implementation**
```java
class Solution {
    public int calculate(String s) {
        Stack<int[]> stack = new Stack<>();
        int result = 0;
        int sign = 1;
        int num = 0;
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
                
            } else if (c == '+') {
                result += sign * num;
                num = 0;
                sign = 1;
                
            } else if (c == '-') {
                result += sign * num;
                num = 0;
                sign = -1;
                
            } else if (c == '(') {
                // Push both as a pair
                stack.push(new int[]{result, sign});
                result = 0;
                sign = 1;
                
            } else if (c == ')') {
                result += sign * num;
                num = 0;
                int[] prev = stack.pop();
                result = prev[0] + prev[1] * result;
            }
        }
        
        result += sign * num;
        return result;
    }
}
```

**Key Difference**: 
- More explicit about pairs
- Single push/pop instead of two
- Same complexity

**Complexity Analysis**
- **Time**: O(n) — Same as approach 1
- **Space**: O(n) — Same overall space

---

#### **Approach 3: No Explicit Sign Variable - ALTERNATIVE**

**Core Idea**: Use +1/-1 directly in result calculation.

**Code Implementation**
```java
class Solution {
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        int num = 0;
        int sign = 1;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
                
            } else if (c == '+' || c == '-') {
                result += sign * num;
                sign = (c == '+') ? 1 : -1;
                num = 0;
                
            } else if (c == '(') {
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
                
            } else if (c == ')') {
                result += sign * num;
                num = 0;
                result = stack.pop() * result + stack.pop();
            }
        }
        
        if (num != 0) {
            result += sign * num;
        }
        
        return result;
    }
}
```

**Key Difference**: 
- Combines +/- handling
- Different pop order (careful!)
- Same complexity

**Complexity Analysis**
- **Time**: O(n)
- **Space**: O(n)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Code Complexity | Recommended |
|----------|------|-------|-----------------|-------------|
| **Stack with State** | **O(n)** | **O(n)** | **Simple ✅** | **Yes ✅** |
| Stack with Pairs | O(n) | O(n) | Simple | Cleaner |
| Combined Operator | O(n) | O(n) | Medium | Alternative |

**Winner**: **Stack with State** — clear, explicit, easy to understand!

### Why Use Stack?

```
Parentheses create nested scopes!

Example: "1-(2+(3-4))"

Need to remember:
  At first '(': result=1, sign=-1
  At second '(': result=2, sign=+1
  
When closing second ')':
  Complete 3-4 = -1
  Apply to outer: 2 + 1*(-1) = 1
  
When closing first ')':
  Complete result = 1
  Apply to outermost: 1 + (-1)*1 = 0

Stack stores context for each nesting level!

Without stack:
  Can't remember outer scope when processing inner scope ❌
```

### Why Push Both Result and Sign?

```
Both are needed to correctly apply parentheses result!

Example: "2-(5-6)"

At '(':
  result = 2
  sign = -1
  
  Must save BOTH!
  
Inside parentheses:
  5-6 = -1
  
At ')':
  Need sign=-1 to compute: 2 + (-1)*(-1) = 2+1 = 3 ✓
  
If we only saved result=2:
  How do we know to negate? ❌
  
If we only saved sign=-1:
  What do we add the result to? ❌

Both pieces of state are essential!
```

### Why Reset After '('?

```
Parentheses start a fresh calculation!

Example: "5+(3-1)"

At '(':
  Save result=5, sign=+1
  MUST RESET: result=0, sign=+1
  
Why reset result?
  Inside parentheses is independent calculation
  Starts from 0, not from 5
  
Why reset sign?
  Inside expression starts with implicit +
  First number has sign +1
  
Process inside:
  result = 0
  3: result = 0 + 1*3 = 3
  -: sign = -1
  1: result = 3 + (-1)*1 = 2
  
At ')':
  Pop result=5, sign=+1
  Combine: 5 + 1*2 = 7 ✓

Reset ensures inner calculation is independent!
```

### Why Apply Number at Multiple Points?

```
Number needs to be applied when:
  1. We see next operator
  2. We see closing parenthesis
  3. At end of string

Example 1: "1+2"
  char '1': num=1
  char '+': result += sign*num (apply here!)
  char '2': num=2
  END: result += sign*num (must apply here too!)
  
  Without final: result=1 ❌
  With final: result=3 ✓

Example 2: "(1+2)"
  char ')': must apply current num before popping!
  
Example 3: "1+2-3"
  At '+': apply 1
  At '-': apply 2
  At END: apply 3
  
Apply at every transition point!
```

### Why Use +1 and -1 for Sign?

```
Makes calculation simple and uniform!

With +1/-1:
  result += sign * num
  
  If sign=+1: result += num (addition)
  If sign=-1: result -= num (subtraction)
  
Single expression handles both!

With true/false or '+'/'-':
  if (sign is positive):
      result += num
  else:
      result -= num
  
More verbose, same logic.

+1/-1 is cleaner and matches mathematical convention!
```

### Why Skip Spaces?

```
Problem states: s contains spaces

Spaces don't affect calculation
  "1+2" same as " 1 + 2 " same as "1  +   2"

Implementation:
  Don't explicitly check for space
  Just don't handle it in if/else chain
  Automatically skipped ✓

Alternative:
  else if (c == ' ') continue;
  
Both work, implicit skip is cleaner!
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Number**
```java
Input: s = "42"
No operators or parentheses
result = 0, after loop: result += 1*42 = 42
Output: 42
```

### 2. **Unary Minus at Start**
```java
Input: s = "-2"
Initial sign = +1
char '-': result += 1*0 = 0, sign = -1
char '2': num = 2
End: result += (-1)*2 = -2
Output: -2
```

### 3. **Expression in Parentheses at Start**
```java
Input: s = "(1+2)"
char '(': push 0, push 1, reset
Inside: 1+2 = 3
char ')': pop 1, pop 0, result = 0+1*3 = 3
Output: 3
```

### 4. **Nested Parentheses**
```java
Input: s = "((1+2)+3)"
First '(': push [0, 1]
Second '(': push [0, 1]
1+2 = 3
First ')': pop [0, 1], 0+1*3 = 3
+3: 3+3 = 6
Second ')': pop [0, 1], 0+1*6 = 6
Output: 6
```

### 5. **Subtraction with Parentheses**
```java
Input: s = "5-(3-1)"
'5': result=5
'-': sign=-1
'(': push [5, -1], reset
Inside: 3-1 = 2
')': pop [-1, 5], result = 5+(-1)*2 = 3
Output: 3
```

### 6. **Multiple Operations**
```java
Input: s = "1+2+3+4"
No parentheses, straightforward
result = 1+2+3+4 = 10
Output: 10
```

### 7. **Spaces Everywhere**
```java
Input: s = " 1 + 2 - 3 "
Spaces ignored
result = 1+2-3 = 0
Output: 0
```

### 8. **Unary Minus with Parentheses**
```java
Input: s = "-(1+2)"
'-': sign=-1
'(': push [0, -1], reset
Inside: 1+2 = 3
')': pop [-1, 0], result = 0+(-1)*3 = -3
Output: -3
```

### 9. **Multiple Parentheses**
```java
Input: s = "(1)+(2)+(3)"
Each parentheses evaluated independently
result = 1+2+3 = 6
Output: 6
```

### 10. **Zero Values**
```java
Input: s = "0-0"
result = 0-0 = 0
Output: 0
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Applying Final Number**
```java
// WRONG - missing final application
for (char c : s.toCharArray()) {
    // ... process characters
}
return result;  // Forgot to apply last number!
```

**Why wrong**: Last number not applied to result!

**Dry run failure for s="1+2":**
```
char '1': num=1
char '+': result += 1*1 = 1, sign=1, num=0
char '2': num=2
END: return result ❌

result = 1 ❌ (should be 3)

Missing: result += sign * num = 1 + 1*2 = 3 ✓
```

**Fix**: Apply final number
```java
result += sign * num;
return result;
```

### ❌ **MISTAKE 2: Wrong Stack Pop Order**
```java
// WRONG - pop order reversed
if (c == ')') {
    result += sign * num;
    num = 0;
    int prevResult = stack.pop();  // Wrong order!
    int prevSign = stack.pop();
    result = prevResult + prevSign * result;
}
```

**Why wrong**: We pushed result first, then sign. Must pop in reverse!

**Dry run failure for s="1-(2)":**
```
'(': push result=1, push sign=-1
     stack = [1, -1] (bottom to top)

')': pop prevResult
     prevResult = stack.pop() = -1 ❌ (this was sign!)
     prevSign = stack.pop() = 1 ❌ (this was result!)
     
     result = (-1) + 1*2 = 1 ❌
     
Wrong! Got sign as result and result as sign!
```

**Fix**: Pop in reverse order of push
```java
int prevSign = stack.pop();    // Last pushed
int prevResult = stack.pop();  // First pushed
```

### ❌ **MISTAKE 3: Not Resetting After '('**
```java
// WRONG - doesn't reset
if (c == '(') {
    stack.push(result);
    stack.push(sign);
    // Missing: result = 0; sign = 1;
}
```

**Why wrong**: Inner calculation uses outer values!

**Dry run failure for s="2+(3)":**
```
'2': result=2
'+': result=2, sign=1
'(': push 2, push 1
     Don't reset ❌
     result=2 (still!), sign=1

'3': num=3
')': result += 1*3 = 2+3 = 5
     pop sign=1, pop result=2
     result = 2 + 1*5 = 7 ❌
     
Wrong! Should be 2+3 = 5, not 7!

The '3' should start from 0, not from 2!
```

**Fix**: Always reset
```java
if (c == '(') {
    stack.push(result);
    stack.push(sign);
    result = 0;
    sign = 1;
}
```

### ❌ **MISTAKE 4: Wrong Final Calculation at ')'**
```java
// WRONG - wrong formula
if (c == ')') {
    result += sign * num;
    num = 0;
    int prevSign = stack.pop();
    int prevResult = stack.pop();
    result = result + prevSign * prevResult;  // Wrong!
}
```

**Why wrong**: Should multiply parentheses result by prevSign, not prevResult!

**Dry run failure for s="2-(3)":**
```
'(': push result=2, sign=-1
Inside: result=3
')': pop prevSign=-1, prevResult=2
     
     Wrong: result = 3 + (-1)*2 = 3-2 = 1 ❌
     Correct: result = 2 + (-1)*3 = 2-3 = -1 ✓

Formula should be:
  prevResult + prevSign * (parentheses result)
Not:
  (parentheses result) + prevSign * prevResult
```

**Fix**: Correct formula
```java
result = prevResult + prevSign * result;
```

### ❌ **MISTAKE 5: Not Building Multi-Digit Numbers**
```java
// WRONG - only handles single digits
if (Character.isDigit(c)) {
    num = c - '0';  // Overwrites!
}
```

**Why wrong**: "123" would be interpreted as just "3"!

**Dry run failure for s="12+3":**
```
'1': num = 1
'2': num = 2 ❌ (overwrote 1!)
'+': result += 1*2 = 2
'3': num = 3
END: result += 1*3 = 2+3 = 5 ❌

Should be 12+3 = 15!
```

**Fix**: Accumulate digits
```java
num = num * 10 + (c - '0');
```

### ❌ **MISTAKE 6: Forgetting to Reset num**
```java
// WRONG - doesn't reset num after operator
if (c == '+') {
    result += sign * num;
    sign = 1;
    // Missing: num = 0;
}
```

**Why wrong**: num carries over to next number!

**Dry run failure for s="1+2":**
```
'1': num=1
'+': result += 1*1 = 1, sign=1
     num=1 (still!) ❌

'2': num = 1*10+2 = 12 ❌
     
Wrong! '2' became '12' because we didn't reset!
```

**Fix**: Always reset num
```java
if (c == '+') {
    result += sign * num;
    sign = 1;
    num = 0;
}
```

### ❌ **MISTAKE 7: Treating '(' as Operator**
```java
// WRONG - applies number at '('
if (c == '(') {
    result += sign * num;  // NO! Don't apply here
    num = 0;
    stack.push(result);
    stack.push(sign);
    result = 0;
    sign = 1;
}
```

**Why wrong**: Number before '(' should be applied earlier!

**Example issue: "5(3)"** (but this is invalid input per constraints)

In valid input like "5+(3)", the '+' already applied '5', so num=0 at '('.

**Actually**: This might work for valid input, but it's semantically wrong. The '+' or '-' before '(' should apply the number, not the '(' itself.

**Fix**: Don't apply at '('
```java
if (c == '(') {
    stack.push(result);
    stack.push(sign);
    result = 0;
    sign = 1;
    // Don't apply num here
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **Process each char** | n | O(1) | O(n) |
| **Stack push** | ≤ n/2 | O(1) | O(n) |
| **Stack pop** | ≤ n/2 | O(1) | O(n) |
| **Total** | - | - | **O(n)** |

**Time analysis**:
```
Single pass through string: O(n)
Each character processed once: O(1) per char
Stack operations: O(1) per operation
Total pushes/pops: O(number of parentheses pairs) ≤ O(n)

Total: O(n)

For s.length = 3*10^5: ~300,000 operations (very fast)
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Stack | O(d) | d = max nesting depth ≤ n/2 |
| Variables | O(1) | result, sign, num (constant) |
| **Total** | **O(n)** | Stack dominates |

**Space analysis**:
```
Worst case: maximum nesting depth
  Input: "(((((((...)))))))"
  Each '(' pushes 2 values
  Max depth: n/2
  Stack: O(n)

Average case:
  Few levels of nesting
  Stack: O(depth) where depth << n
  
Best case: no parentheses
  Stack empty: O(1)
  
Worst case space: O(n)
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `s = "1-(2+3)"`

**Expected Output:** `-4`

---

**Initial State:**
```
result: 0
sign: 1
num: 0
stack: []
```

---

**Step 1: Process '1'**
```
Character: '1' (digit)
Action: Build number
  num = 0*10 + 1 = 1

State:
  result: 0
  sign: 1
  num: 1
  stack: []
```

---

**Step 2: Process '-'**
```
Character: '-' (operator)
Action: Apply previous number, update sign
  result += sign * num = 0 + 1*1 = 1
  sign = -1
  num = 0

State:
  result: 1
  sign: -1
  num: 0
  stack: []
```

---

**Step 3: Process '('**
```
Character: '(' (opening parenthesis)
Action: Save state, reset
  stack.push(result) → push 1
  stack.push(sign) → push -1
  result = 0
  sign = 1

State:
  result: 0
  sign: 1
  num: 0
  stack: [1, -1]
  
Entered parentheses scope
Saved outer: result=1, sign=-1
```

---

**Step 4: Process '2'**
```
Character: '2' (digit)
Action: Build number
  num = 0*10 + 2 = 2

State:
  result: 0
  sign: 1
  num: 2
  stack: [1, -1]
```

---

**Step 5: Process '+'**
```
Character: '+' (operator)
Action: Apply previous number, update sign
  result += sign * num = 0 + 1*2 = 2
  sign = 1
  num = 0

State:
  result: 2
  sign: 1
  num: 0
  stack: [1, -1]
```

---

**Step 6: Process '3'**
```
Character: '3' (digit)
Action: Build number
  num = 0*10 + 3 = 3

State:
  result: 2
  sign: 1
  num: 3
  stack: [1, -1]
```

---

**Step 7: Process ')'**
```
Character: ')' (closing parenthesis)
Action: Apply current number, pop state, combine
  1. Apply current number:
     result += sign * num = 2 + 1*3 = 5
     num = 0
  
  2. Pop sign:
     prevSign = stack.pop() = -1
  
  3. Pop result:
     prevResult = stack.pop() = 1
  
  4. Combine:
     result = prevResult + prevSign * result
            = 1 + (-1) * 5
            = 1 - 5
            = -4

State:
  result: -4
  sign: -1 (unchanged from pop)
  num: 0
  stack: []
  
Exited parentheses scope
(2+3) evaluated to 5
Applied with sign -1: 1 - 5 = -4
```

---

**Step 8: End of String**
```
Action: Apply final number (if any)
  result += sign * num
  result += (-1) * 0 = -4 + 0 = -4
  (no effect since num=0)

Final Result: -4 ✓
```

---

### Step-by-Step Calculation View

```
Expression: 1-(2+3)

Step 1: Process '1'
  Current: 1
  
Step 2: Process '-'
  Current: 1-
  (waiting for next value)
  
Step 3: Process '('
  Save: result=1, sign=-
  Enter: fresh calculation
  
Step 4-6: Process '2+3'
  Inside parentheses: 2+3 = 5
  
Step 7: Process ')'
  Exit: got 5 from parentheses
  Apply: 1 - 5 = -4
  
Result: -4
```

---

### Another Example: "2-(5-6)"

```
Input: "2-(5-6)"

char '2':
  num=2

char '-':
  result += 1*2 = 2
  sign=-1

char '(':
  Push [2, -1]
  Reset: result=0, sign=1

char '5':
  num=5

char '-':
  result += 1*5 = 5
  sign=-1

char '6':
  num=6

char ')':
  result += (-1)*6 = 5-6 = -1
  Pop prevSign=-1, prevResult=2
  result = 2 + (-1)*(-1) = 2+1 = 3

Result: 3 ✓

Calculation: 2-(5-6) = 2-(-1) = 2+1 = 3
```

---

### Stack Visualization for Nested Example

```
Input: "((1+2)+3)"

Step 1: First '('
  stack: [0, 1]
  Inside level 1

Step 2: Second '('
  stack: [0, 1, 0, 1]
  Inside level 2

Step 3: Process 1+2
  result = 3
  
Step 4: First ')'
  Pop [0, 1]
  result = 0 + 1*3 = 3
  stack: [0, 1]
  Back to level 1

Step 5: Process +3
  result = 3+3 = 6
  
Step 6: Second ')'
  Pop [0, 1]
  result = 0 + 1*6 = 6
  stack: []
  Back to level 0

Result: 6
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Lines | Clarity | Recommended |
|----------|------|-------|------------|---------|-------------|
| **Stack with State** | **O(n)** | **O(n)** | **~35** | **Excellent ✅** | **Yes ✅** |
| Stack with Pairs | O(n) | O(n) | ~33 | Excellent | Cleaner |
| Combined Operators | O(n) | O(n) | ~32 | Good | Alternative |

**All optimal approaches have O(n) time and space**

**Recommendation**: **Stack with State** — explicit, clear, easy to debug!

---

## Key Takeaways

1. **Use stack for parentheses** — save (result, sign) at '(', restore at ')'
2. **Track three variables** — result (running total), sign (+1/-1), num (current number)
3. **Apply number at transitions** — operator, ')', and end of string
4. **Build multi-digit numbers** — num = num*10 + digit
5. **Reset after '('** — start fresh calculation for inner expression
6. **Combine at ')'** — prevResult + prevSign * (inner result)
7. **Handle unary minus** — automatically handled by sign tracking
8. **Skip spaces** — no explicit handling needed
9. **O(n) time, O(n) space** — single pass, stack for nesting

---

## Interview Tips

**What to say in an interview:**

> "This problem requires evaluating an arithmetic expression with addition, subtraction, and parentheses. The key challenge is handling nested parentheses correctly. I'll use a stack to save the current result and sign whenever I enter parentheses with '(', then restore them when I exit with ')'. I'll maintain three variables: result for the running total, sign for the current operator (+1 or -1), and num for building multi-digit numbers. As I process each character, if it's a digit I'll accumulate it into num, if it's an operator I'll apply the previous number and update the sign, if it's '(' I'll push state and reset, and if it's ')' I'll apply the current number and combine with the saved state. At the end, I'll apply any remaining number. Time complexity is O(n) for a single pass, space is O(n) for the stack which stores at most n/2 pairs."

**Key points to mention:**
1. **Stack saves state** — (result, sign) at each '('
2. **Three variables** — result, sign, num
3. **Apply number at transitions** — operators, ')', end
4. **Multi-digit handling** — num = num*10 + digit
5. **Reset at '('** — fresh calculation inside
6. **Combine at ')'** — prevResult + prevSign * result
7. **Unary minus** — handled automatically
8. **O(n) single pass** — optimal complexity

**Common Follow-ups:**
- "How do you handle multiplication?" → Different problem (needs precedence)
- "What if parentheses are unbalanced?" → Problem guarantees valid input
- "Can you do it without stack?" → Not easily for arbitrary nesting
- "How about spaces?" → Automatically skipped in character processing

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Basic Calculator** | Hard | **Stack + Expression Eval** | **This problem** |
| Basic Calculator II | Medium | Stack + Precedence | Has * and / (no parentheses) |
| Basic Calculator III | Hard | Stack + Full Precedence | All operators + parentheses |
| Evaluate Reverse Polish Notation | Medium | Stack | Postfix notation (simpler) |
| Different Ways to Add Parentheses | Medium | Recursion + Memoization | Generate all possibilities |
| Expression Add Operators | Hard | Backtracking | Insert operators between digits |

**Pattern Progression**:
1. **+/- with parentheses** (this problem) — Scope management
2. **+/-/\*/÷ no parentheses** (Calculator II) — Operator precedence
3. **All operators with parentheses** (Calculator III) — Both challenges
4. **Postfix evaluation** (RPN) — No precedence issues

---

## Final Pattern Label

✅ **Stack (Expression Evaluation with Nested Parentheses)**

**Remember:** Use **stack** to save **(result, sign)** when entering parentheses '(', restore when exiting ')'. Track **three variables**: result (running total), sign (+1 or -1), num (current number building). **Apply number** at operators (+/-), at ')', and at end. Build multi-digit: **num = num\*10 + digit**. **Reset** result=0, sign=1 after '(' to start fresh. **Combine** at ')': prevResult + prevSign \* result. Unary minus handled automatically. **O(n) time**, **O(n) space** for stack. Single pass with state management!
