# Evaluate Reverse Polish Notation

## Problem Description

**Difficulty**: Medium

You are given an array of strings `tokens` that represents a valid arithmetic expression in **Reverse Polish Notation** (RPN).

Return the integer that represents the evaluation of the expression.

- The operands may be integers or the results of other operations.
- The operators include `'+'`, `'-'`, `'*'`, and `'/'`.
- Assume that division between integers always **truncates toward zero**.

## Examples

### Example 1:
```
Input: tokens = ["2", "1", "+", "3", "*"]
Output: 9

Explanation: 
(2 + 1) * 3 = 9

RPN evaluation:
  2 → push 2
  1 → push 1
  + → pop 1, pop 2, compute 2+1=3, push 3
  3 → push 3
  * → pop 3, pop 3, compute 3*3=9, push 9
  Result: 9
```

### Example 2:
```
Input: tokens = ["4", "13", "5", "/", "+"]
Output: 6

Explanation: 
4 + (13 / 5) = 4 + 2 = 6

RPN evaluation:
  4 → push 4
  13 → push 13
  5 → push 5
  / → pop 5, pop 13, compute 13/5=2, push 2
  + → pop 2, pop 4, compute 4+2=6, push 6
  Result: 6
```

### Example 3:
```
Input: tokens = ["10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"]
Output: 22

Explanation: 
((10 * (6 / ((9 + 3) * -11))) + 17) + 5
= ((10 * (6 / (12 * -11))) + 17) + 5
= ((10 * (6 / -132)) + 17) + 5
= ((10 * 0) + 17) + 5
= (0 + 17) + 5
= 22
```

### Example 4:
```
Input: tokens = ["1","2","+","3","*","4","-"]
Output: 5

Explanation: 
((1 + 2) * 3) - 4 = 5

RPN evaluation:
  1 → push 1
  2 → push 2
  + → pop 2, pop 1, compute 1+2=3, push 3
  3 → push 3
  * → pop 3, pop 3, compute 3*3=9, push 9
  4 → push 4
  - → pop 4, pop 9, compute 9-4=5, push 5
  Result: 5
```

## Constraints
- 1 <= tokens.length <= 1000
- tokens[i] is `"+"`, `"-"`, `"*"`, or `"/"`, or a string representing an integer in the range `[-200, 200]`
- The given RPN expression is always valid (no division by zero, proper operand/operator pairing)

**Recommended Complexity**: O(n) time and O(n) space, where n is the size of the input array

---

## Pattern Recognition

**Primary Pattern**: **Stack (Postfix Expression Evaluation)**

**Why This Pattern?**
- Reverse Polish Notation (RPN) is postfix notation
- Operators come after operands
- Most recent values are operands for next operator
- LIFO structure perfect for this

**Key Insight**: Stack Naturally Handles RPN
```
Reverse Polish Notation (Postfix):
  Infix: (2 + 3) * 4
  Postfix (RPN): 2 3 + 4 *
  
Why postfix?
  - No parentheses needed
  - Unambiguous evaluation order
  - Natural stack-based evaluation

Example: "2 3 + 4 *"
  Read 2: push(2) → stack: [2]
  Read 3: push(3) → stack: [2, 3]
  Read +: pop 3, pop 2, compute 2+3=5, push 5 → stack: [5]
  Read 4: push(4) → stack: [5, 4]
  Read *: pop 4, pop 5, compute 5*4=20, push 20 → stack: [20]
  Result: 20 ✓
```

**The Stack Strategy**:
```
Algorithm:
  1. Initialize empty stack
  2. For each token:
     - If number: push onto stack
     - If operator:
       a. Pop second operand
       b. Pop first operand
       c. Apply operator: first op second
       d. Push result
  3. Final result is single element in stack

Why this works?
  Stack maintains partial results
  Operators always have correct operands on top
  Order preserved by LIFO property
```

**Critical Detail**: Operand Order Matters!
```
For - and /:
  WRONG: first - second
  RIGHT: second - first (because second popped first!)

Example: ["5", "3", "-"]
  Stack after pushing: [5, 3]
  Pop operations:
    second = stack.pop() → 3
    first = stack.pop() → 5
  Computation: first - second = 5 - 3 = 2 ✓
  
  If we did second - first: 3 - 5 = -2 ❌

Order: The element popped FIRST was pushed SECOND (LIFO!)
```

**Example Walkthrough**:
```
Input: ["2", "1", "+", "3", "*"]

Step-by-step:
  Token "2": Number → push 2
    Stack: [2]
  
  Token "1": Number → push 1
    Stack: [2, 1]
  
  Token "+": Operator
    Pop second=1, pop first=2
    Compute: 2 + 1 = 3
    Push 3
    Stack: [3]
  
  Token "3": Number → push 3
    Stack: [3, 3]
  
  Token "*": Operator
    Pop second=3, pop first=3
    Compute: 3 * 3 = 9
    Push 9
    Stack: [9]
  
  Return: 9 ✓
```

**Why Stack Is Perfect**:
```
RPN property: Operators apply to most recent operands
Stack property: Access most recent elements (LIFO)

Perfect match!

Example showing stack advantage:
  "1 2 + 3 4 + *"
  
  After "1 2 +": stack = [3]
  After "3 4 +": stack = [3, 7]
  After "*": stack = [21]
  
  Stack automatically maintains:
    - Partial results
    - Correct evaluation order
    - Operand availability

No need to track indices or search for operands!
```

**Related Patterns**:
1. **Postfix Evaluation** — This problem
2. **Infix to Postfix Conversion** — Related conversion
3. **Expression Tree** — Alternative representation
4. **Calculator** — Similar parsing problem

---

## Algorithm & Approach

### Core Insight

**Why Naive Approach Fails:**
```
Naive: Find operator, compute with neighbors, repeat
  ["2", "1", "+", "3", "*"]
  Find "+": compute 2+1=3, replace → ["3", "3", "*"]
  Find "*": compute 3*3=9 → ["9"]
  
Time: O(n²) — each operation requires array modification
Requirement: O(n)
Too slow for large inputs!

Stack Approach:
  Single pass through array
  Push/pop operations O(1)
  Total: O(n) ✓
```

**The Optimal Strategy**:
```
Key observations:
  1. Process tokens left to right (single pass)
  2. Numbers → push to stack
  3. Operators → pop operands, compute, push result
  4. Final stack has one element (the answer)
  
All operations O(1):
  Push: O(1)
  Pop: O(1)
  Arithmetic: O(1)
  
Total: O(n) for n tokens
```

### Step-by-Step Algorithm

---

#### **Approach 1: Stack with String Comparison - STANDARD**

**Core Idea**:
- Use stack to store intermediate results
- Check if token is operator using string comparison
- Handle operand order correctly for - and /

**Algorithm**
```
evalRPN(tokens):
    stack = new Stack()
    
    for token in tokens:
        if token is operator ("+", "-", "*", "/"):
            // Pop in reverse order
            second = stack.pop()
            first = stack.pop()
            
            // Apply operator
            if token == "+":
                result = first + second
            else if token == "-":
                result = first - second
            else if token == "*":
                result = first * second
            else if token == "/":
                result = first / second  // Truncates toward zero
            
            stack.push(result)
        else:
            // Token is number
            stack.push(Integer.parseInt(token))
    
    return stack.pop()
```

**Code Implementation**
```java
class Solution {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        
        for (String token : tokens) {
            // Check if token is an operator
            if (token.equals("+") || token.equals("-") || 
                token.equals("*") || token.equals("/")) {
                
                // Pop operands (ORDER MATTERS!)
                int second = stack.pop();
                int first = stack.pop();
                
                // Apply operator
                int result = 0;
                if (token.equals("+")) {
                    result = first + second;
                } else if (token.equals("-")) {
                    result = first - second;  // first - second, NOT second - first
                } else if (token.equals("*")) {
                    result = first * second;
                } else if (token.equals("/")) {
                    result = first / second;  // Integer division truncates
                }
                
                stack.push(result);
            } else {
                // Token is a number (possibly negative)
                stack.push(Integer.parseInt(token));
            }
        }
        
        // Final result
        return stack.pop();
    }
}
```

**Example Walkthrough**

Input: `["2", "1", "+", "3", "*"]`

| Token | Type | Action | Stack After | Explanation |
|-------|------|--------|-------------|-------------|
| "2" | Number | push(2) | [2] | Push operand |
| "1" | Number | push(1) | [2, 1] | Push operand |
| "+" | Operator | pop 1, pop 2, push 3 | [3] | 2 + 1 = 3 |
| "3" | Number | push(3) | [3, 3] | Push operand |
| "*" | Operator | pop 3, pop 3, push 9 | [9] | 3 * 3 = 9 |
| Return | - | pop() | [] | Result: 9 |

**Complexity Analysis**
- **Time**: O(n) — Single pass through tokens, each operation O(1)
- **Space**: O(n) — Stack can hold up to n/2 numbers (worst case: all numbers first)

---

#### **Approach 2: Stack with Switch Statement - CLEANER**

**Core Idea**: Use switch for operator handling (cleaner code).

**Code Implementation**
```java
class Solution {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        
        for (String token : tokens) {
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(a - b);  // Order matters!
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    int divisor = stack.pop();
                    int dividend = stack.pop();
                    stack.push(dividend / divisor);  // Order matters!
                    break;
                default:
                    stack.push(Integer.parseInt(token));
                    break;
            }
        }
        
        return stack.pop();
    }
}
```

**Key Difference**: 
- Switch statement cleaner than if-else chain
- For + and *, order doesn't matter (commutative)
- For - and /, must pop into variables first

**Complexity Analysis**
- **Time**: O(n) — Single pass
- **Space**: O(n) — Stack storage

---

#### **Approach 3: Stack with Helper Method - MOST READABLE**

**Core Idea**: Extract operator check to helper method for clarity.

**Code Implementation**
```java
class Solution {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        
        for (String token : tokens) {
            if (isOperator(token)) {
                int second = stack.pop();
                int first = stack.pop();
                int result = applyOperator(token, first, second);
                stack.push(result);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        
        return stack.pop();
    }
    
    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || 
               token.equals("*") || token.equals("/");
    }
    
    private int applyOperator(String operator, int first, int second) {
        switch (operator) {
            case "+": return first + second;
            case "-": return first - second;
            case "*": return first * second;
            case "/": return first / second;
            default: throw new IllegalArgumentException("Invalid operator");
        }
    }
}
```

**Key Difference**: 
- Helper methods improve readability
- Single responsibility principle
- Easier to test and debug

**Complexity Analysis**
- **Time**: O(n) — Single pass
- **Space**: O(n) — Stack storage

---

#### **Approach 4: Array-Based Stack - SPACE OPTIMIZED**

**Core Idea**: Use array instead of Stack for slightly better performance.

**Code Implementation**
```java
class Solution {
    public int evalRPN(String[] tokens) {
        int[] stack = new int[tokens.length];
        int top = -1;  // Stack pointer
        
        for (String token : tokens) {
            if (token.equals("+")) {
                int second = stack[top--];
                int first = stack[top--];
                stack[++top] = first + second;
            } else if (token.equals("-")) {
                int second = stack[top--];
                int first = stack[top--];
                stack[++top] = first - second;
            } else if (token.equals("*")) {
                int second = stack[top--];
                int first = stack[top--];
                stack[++top] = first * second;
            } else if (token.equals("/")) {
                int second = stack[top--];
                int first = stack[top--];
                stack[++top] = first / second;
            } else {
                stack[++top] = Integer.parseInt(token);
            }
        }
        
        return stack[top];
    }
}
```

**Key Difference**: 
- Array instead of Stack object
- Slightly faster (no object overhead)
- More manual management

**Complexity Analysis**
- **Time**: O(n) — Single pass
- **Space**: O(n) — Array size = tokens.length

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Code Clarity | Recommended |
|----------|------|-------|--------------|-------------|
| **Stack (Standard)** | **O(n)** | **O(n)** | **Excellent ✅** | **Yes ✅** |
| Stack (Switch) | O(n) | O(n) | Good | Alternative |
| Stack (Helpers) | O(n) | O(n) | Best | For large codebases |
| Array Stack | O(n) | O(n) | Fair | Performance critical |

**Winner**: **Stack with Standard If-Else** — clear, straightforward, standard!

### Why Stack Works for RPN?

```
RPN (Postfix) property:
  Operators come AFTER their operands
  Example: "2 3 +" means "2 + 3"

Evaluation order:
  Left to right, applying operators as encountered
  Operators use most recent operands

Stack property:
  LIFO — Last In, First Out
  Top of stack = most recent element

Perfect match!

Example: "5 1 2 + 4 * + 3 -"
  
  Token "5": push → [5]
  Token "1": push → [5, 1]
  Token "2": push → [5, 1, 2]
  Token "+": compute 1+2=3 → [5, 3]
  Token "4": push → [5, 3, 4]
  Token "*": compute 3*4=12 → [5, 12]
  Token "+": compute 5+12=17 → [17]
  Token "3": push → [17, 3]
  Token "-": compute 17-3=14 → [14]
  
  Result: 14
  
Stack maintains partial results at each step!
```

### Why Operand Order Matters?

```
Critical for - and /:

Stack: [5, 3]
Operation: "-"

Pop order:
  second = pop() → 3
  first = pop() → 5

Compute: first - second = 5 - 3 = 2 ✓

If we did second - first: 3 - 5 = -2 ❌

REMEMBER: First popped = Last pushed (LIFO!)

Mnemonic:
  "first = pop(), second = pop()"
  Compute "first op second"
  
  OR
  
  "b = pop(), a = pop()"
  Compute "a op b"
```

### Why Not Other Approaches?

```
1. Infix evaluation:
   - Need to convert RPN → infix first
   - More complex (precedence, parentheses)
   - Unnecessary work!

2. Recursion:
   - Possible but awkward
   - No natural base case
   - Stack is simpler

3. Two-pass algorithm:
   - First pass build tree
   - Second pass evaluate
   - Overkill! Single pass sufficient

Stack is the natural, optimal choice!
```

### Division Truncation Detail

```
Java integer division automatically truncates toward zero:
  
  13 / 5 = 2 (not 2.6)
  -13 / 5 = -2 (not -3, truncates toward zero!)
  
Python note:
  Python's // floors toward negative infinity
  -13 // 5 = -3 (different!)
  
For Java, just use / with ints — already correct!
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Number**
```java
Input: ["42"]
Stack after: [42]
Result: 42 ✓
```

### 2. **Negative Numbers in Input**
```java
Input: ["-3", "4", "+"]
Parse "-3" correctly with Integer.parseInt()
Stack: [-3] → [-3, 4] → [1]
Result: 1 ✓
```

### 3. **Division Truncation Toward Zero**
```java
Input: ["4", "13", "5", "/", "+"]
13 / 5 = 2 (truncates)
Stack: [4] → [4, 13] → [4, 13, 5] → [4, 2] → [6]
Result: 6 ✓
```

### 4. **Negative Division Result**
```java
Input: ["-10", "5", "/"]
-10 / 5 = -2
Stack: [-10] → [-10, 5] → [-2]
Result: -2 ✓
```

### 5. **Complex Expression**
```java
Input: ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
Multiple nested operations
Stack evolves through multiple stages
Final result: 22
```

### 6. **All Operations**
```java
Input: ["4","3","+","2","*","1","-"]
(4 + 3) * 2 - 1 = 7 * 2 - 1 = 14 - 1 = 13
Stack: [4] → [4,3] → [7] → [7,2] → [14] → [14,1] → [13]
Result: 13 ✓
```

### 7. **Consecutive Operations**
```java
Input: ["1","2","+","3","+","4","+"]
(((1 + 2) + 3) + 4) = 10
Stack grows and shrinks repeatedly
Result: 10 ✓
```

### 8. **Order-Sensitive Operations**
```java
Input: ["5", "3", "-"]
5 - 3 = 2 (NOT 3 - 5 = -2)
Operand order critical!
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Using == for String Comparison**
```java
// WRONG - uses == instead of .equals()
if (token == "+") {  // WRONG! Compares references
    // ...
}
```

**Why wrong**: `==` compares object references, not string content!

**Dry run failure for ["1","2","+"]:**
```
Token "1": "1" == "+" ? No (but would be unpredictable)
Token "2": "2" == "+" ? No
Token "+": "+" == "+" ? 
  Maybe yes, maybe no (depends on string interning!)
  If no: parsed as number → Integer.parseInt("+") → Exception! ❌
```

**Fix**: Use .equals()
```java
if (token.equals("+")) {
    // ...
}
```

### ❌ **MISTAKE 2: Wrong Operand Order**
```java
// WRONG - operands in wrong order
int first = stack.pop();
int second = stack.pop();
stack.push(first - second);  // WRONG! Should be second - first
```

**Why wrong**: Pop order reversed from push order!

**Dry run failure for ["5","3","-"]:**
```
Stack after pushes: [5, 3]
Pop first: first = 3 (top)
Pop second: second = 5
Compute: first - second = 3 - 5 = -2 ❌
Expected: 5 - 3 = 2
```

**Fix**: Correct naming or order
```java
int second = stack.pop();  // Last pushed
int first = stack.pop();   // First pushed
stack.push(first - second);  // Correct order!
```

### ❌ **MISTAKE 3: Using Integer.valueOf() Instead of parseInt()**
```java
// POTENTIALLY WRONG - returns Integer object
stack.push(Integer.valueOf(token));
```

**Why wrong**: Works but less efficient (object creation).

**Better**: Use parseInt()
```java
stack.push(Integer.parseInt(token));  // Returns primitive int
```

### ❌ **MISTAKE 4: Not Handling Negative Numbers**
```java
// WRONG - assumes all non-operators are positive
if (token.charAt(0) >= '0' && token.charAt(0) <= '9') {
    // WRONG! Fails for "-3"
}
```

**Why wrong**: Negative numbers start with '-', same as subtract operator!

**Dry run failure for ["-3"]:**
```
token = "-3"
token.charAt(0) = '-'
'-' >= '0'? No
Treated as operator, try to pop → stack empty → Exception! ❌
```

**Fix**: Check if operator explicitly
```java
if (token.equals("+") || token.equals("-") || 
    token.equals("*") || token.equals("/")) {
    // Operator
} else {
    // Number (possibly negative)
    stack.push(Integer.parseInt(token));
}
```

### ❌ **MISTAKE 5: Forgetting to Push Result**
```java
// WRONG - computes but doesn't push
int second = stack.pop();
int first = stack.pop();
int result = first + second;
// WRONG! Forgot to push result back
```

**Why wrong**: Result disappears, stack gets smaller!

**Dry run failure for ["1","2","+","3","+"]:**
```
After "1","2","+": compute 3 but don't push
Stack: [] (empty!)
Next "+": need to pop → empty stack → Exception! ❌
```

**Fix**: Always push result
```java
int result = first + second;
stack.push(result);  // Push back to stack!
```

### ❌ **MISTAKE 6: Using else-if Instead of if for All Operators**
```java
// WRONG - uses if without else, always executes last else
if (token.equals("+")) {
    // ...
} 
if (token.equals("-")) {
    // ...
}
// ... more ifs
else {
    stack.push(Integer.parseInt(token));  // ALWAYS executes!
}
```

**Why wrong**: Need else-if chain or different structure!

**Fix**: Use else-if or proper structure
```java
if (token.equals("+")) {
    // ...
} else if (token.equals("-")) {
    // ...
} else {
    stack.push(Integer.parseInt(token));
}
```

### ❌ **MISTAKE 7: Inline Pop in Arithmetic**
```java
// WRONG - evaluation order undefined for - and /
stack.push(stack.pop() - stack.pop());  // WRONG! Order undefined
```

**Why wrong**: Java doesn't guarantee evaluation order!

**Dry run failure for ["5","3","-"]:**
```
stack.pop() - stack.pop()
Could evaluate left pop first: 3 - 5 = -2 ❌
Or right pop first: 5 - 3 = 2 ✓
Undefined behavior!
```

**Fix**: Pop into variables first
```java
int second = stack.pop();
int first = stack.pop();
stack.push(first - second);  // Explicit order!
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **Iterate tokens** | n | - | - |
| **Push to stack** | ≤ n | O(1) | O(n) |
| **Pop from stack** | ≤ n | O(1) | O(n) |
| **Arithmetic ops** | ≤ n | O(1) | O(n) |
| **Parse integers** | ≤ n | O(k)* | O(nk) |
| **Total** | - | - | **O(n)** |

*k = max digits per number (constant for this problem: max 3 digits for range [-200, 200])

**Time analysis**:
```
Single pass through tokens: O(n)
Each token processed once: O(1) per token
Total: O(n)

Why O(1) per token?
  - Stack push/pop: O(1)
  - Arithmetic: O(1)
  - String comparison: O(1) for fixed operators
  - parseInt: O(k) where k = digits (constant here)
  
No nested loops, no backtracking
Truly O(n)!
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Stack | O(n) | Worst case: all numbers pushed |
| Variables | O(1) | Temporary variables (first, second, result) |
| **Total** | **O(n)** | Stack dominates |

**Space analysis**:
```
Worst case: All numbers, then operators
  Example: ["1","2","3","4","+","+","+"]
  After pushing 1,2,3,4: stack size = 4
  Then operators reduce it
  
Maximum stack size: ≤ n/2 + 1
  (RPN property: valid expressions have this bound)
  
Asymptotically: O(n)
```

**Can we do better?**
```
Space: No, must store intermediate results
Time: No, must process all tokens

O(n) time and space is optimal!
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `["2", "1", "+", "3", "*"]`

**Infix equivalent:** `(2 + 1) * 3 = 9`

---

**Initial State:**
```
Tokens: ["2", "1", "+", "3", "*"]
Stack: []
Index: 0
```

---

**Step 1: Process "2"**
```
Token: "2"
Type: Number
Action: push(2)

Stack visualization:
   ┌───┐
   │ 2 │ ← top
   └───┘

Stack: [2]
```

---

**Step 2: Process "1"**
```
Token: "1"
Type: Number
Action: push(1)

Stack visualization:
   ┌───┐
   │ 1 │ ← top
   ├───┤
   │ 2 │
   └───┘

Stack: [2, 1]
```

---

**Step 3: Process "+"**
```
Token: "+"
Type: Operator
Action:
  1. Pop second = 1
  2. Pop first = 2
  3. Compute: 2 + 1 = 3
  4. Push 3

Before pop:
   ┌───┐
   │ 1 │ ← pop (second)
   ├───┤
   │ 2 │ ← pop (first)
   └───┘

After push:
   ┌───┐
   │ 3 │ ← top (result)
   └───┘

Stack: [3]
```

---

**Step 4: Process "3"**
```
Token: "3"
Type: Number
Action: push(3)

Stack visualization:
   ┌───┐
   │ 3 │ ← top
   ├───┤
   │ 3 │
   └───┘

Stack: [3, 3]
```

---

**Step 5: Process "*"**
```
Token: "*"
Type: Operator
Action:
  1. Pop second = 3
  2. Pop first = 3
  3. Compute: 3 * 3 = 9
  4. Push 9

Before pop:
   ┌───┐
   │ 3 │ ← pop (second)
   ├───┤
   │ 3 │ ← pop (first)
   └───┘

After push:
   ┌───┐
   │ 9 │ ← top (result)
   └───┘

Stack: [9]
```

---

**Final Step: Return Result**
```
Action: pop() → 9

Stack after pop:
   (empty)

Result: 9 ✓
```

---

### Complex Example: Division and Order

**Input:** `["5", "3", "-"]`

**Infix equivalent:** `5 - 3 = 2`

---

**Step 1: Push 5**
```
Stack:
   ┌───┐
   │ 5 │ ← top
   └───┘
```

**Step 2: Push 3**
```
Stack:
   ┌───┐
   │ 3 │ ← top (second operand)
   ├───┤
   │ 5 │ (first operand)
   └───┘
```

**Step 3: Subtract**
```
Pop order:
  second = pop() = 3 (last pushed)
  first = pop() = 5 (first pushed)

Computation:
  first - second = 5 - 3 = 2 ✓

IMPORTANT: NOT second - first (would be 3 - 5 = -2 ❌)

Result Stack:
   ┌───┐
   │ 2 │ ← top
   └───┘
```

**Key Insight:**
```
Stack reverses order:
  Push order: 5, then 3
  Pop order: 3, then 5
  
For non-commutative ops (- and /):
  Must compute: (second pop) op (first pop)
  Which is: first op second (original order)
```

---

### State Transitions Diagram

```
Input: ["1","2","+","3","*","4","-"]

State 0 (start):
  Stack: []

↓ push(1)

State 1:
  Stack: [1]

↓ push(2)

State 2:
  Stack: [1, 2]

↓ + (pop 2, pop 1, push 3)

State 3:
  Stack: [3]

↓ push(3)

State 4:
  Stack: [3, 3]

↓ * (pop 3, pop 3, push 9)

State 5:
  Stack: [9]

↓ push(4)

State 6:
  Stack: [9, 4]

↓ - (pop 4, pop 9, push 5)

State 7:
  Stack: [5]

↓ return stack.pop()

Result: 5 ✓
```

---

## Comparison of Approaches

| Approach | Code Lines | Clarity | Performance | Recommended |
|----------|-----------|---------|-------------|-------------|
| **Stack (If-Else)** | **~30** | **Excellent ✅** | **Good** | **Yes ✅** |
| Stack (Switch) | ~25 | Very Good | Good | Alternative |
| Stack (Helpers) | ~40 | Best | Good | Large codebases |
| Array Stack | ~35 | Fair | Slightly faster | Performance critical |

**All have same time/space complexity: O(n) / O(n)**

**Recommendation**: Use **Stack with If-Else** — clear, standard, easy to understand!

---

## Key Takeaways

1. **RPN = Stack** — Postfix notation perfect for stack evaluation
2. **Operand order matters** — For - and /: first op second (not second op first)
3. **Use .equals() for strings** — Never use == for string comparison
4. **Push results back** — After computing, push result to stack
5. **Handle negative numbers** — Integer.parseInt() handles "-3" correctly
6. **Single pass O(n)** — Process each token exactly once
7. **Division truncates** — Java int division already truncates toward zero

---

## Interview Tips

**What to say in an interview:**

> "This problem uses Reverse Polish Notation, which is a postfix representation where operators come after their operands. The key insight is that RPN naturally maps to stack-based evaluation. I'll process tokens left to right: when I see a number, I push it onto the stack; when I see an operator, I pop two operands, apply the operation, and push the result back. The critical detail is operand order—since the stack is LIFO, I need to pop the second operand first, then the first operand, and compute first op second. For example, with stack [5, 3] and operator '-', I pop 3, then 5, and compute 5 - 3 = 2. After processing all tokens, the stack contains a single element which is the final result. This runs in O(n) time with O(n) space for the stack."

**Key points to mention:**
1. **RPN/Postfix notation** — Operators after operands
2. **Stack-based evaluation** — LIFO perfect for this
3. **Operand order** — Pop order matters for - and /
4. **Single pass** — Process each token once
5. **O(n) time and space** — Optimal complexity

**If asked about implementation details:**
> "I'll use .equals() to compare strings for operators, not ==. For operand order, I'll pop into two variables: second = pop(), first = pop(), then compute first op second. For parsing numbers, Integer.parseInt() handles negative numbers like '-3' correctly. Java's integer division already truncates toward zero as required."

**Common Follow-ups:**
- "What if operators had different precedence?" → RPN eliminates precedence concerns
- "How to convert infix to postfix?" → Use stack with precedence rules (different problem)
- "What about invalid input?" → Problem states input always valid
- "Can you optimize space?" → No, must store intermediate results

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Evaluate Reverse Polish Notation** | Medium | **Stack (Postfix)** | **This problem** |
| Basic Calculator | Hard | Stack (Infix) | Handles parentheses |
| Basic Calculator II | Medium | Stack | Handles precedence |
| Basic Calculator III | Hard | Stack + Recursion | Parentheses + precedence |
| Expression Add Operators | Hard | Backtracking | Generate expressions |

**Pattern Progression**:
1. **Postfix evaluation** (this problem) — Simplest, no precedence
2. **Infix with precedence** (Calculator II) — Handle * / before + -
3. **Infix with parentheses** (Calculator I) — Handle nested expressions
4. **Full calculator** (Calculator III) — Precedence + parentheses

---

## Final Pattern Label

✅ **Stack (Postfix/RPN Expression Evaluation)**

**Remember:** RPN (Reverse Polish Notation) uses postfix notation where operators come after operands. Use a stack: push numbers, pop two operands for operators (order matters: first op second), compute, push result. Process tokens left to right in single pass. Use .equals() for string comparison, not ==. Division truncates toward zero automatically in Java. Final result is single element remaining in stack. O(n) time, O(n) space!
