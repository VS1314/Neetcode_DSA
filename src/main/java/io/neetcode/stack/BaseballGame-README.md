# Baseball Game

## Problem Description

**Difficulty**: Easy

You are keeping the scores for a baseball game with strange rules. At the beginning of the game, you start with an empty record.

Given a list of strings `operations`, where `operations[i]` is the ith operation you must apply to the record and is one of the following:

- **An integer x**: Record a new score of `x`
- **'+'**: Record a new score that is the sum of the previous two scores
- **'D'**: Record a new score that is the double of the previous score
- **'C'**: Invalidate the previous score, removing it from the record

Return the **sum of all the scores** on the record after applying all the operations.

**Note**: The test cases are generated such that the answer and all intermediate calculations fit in a 32-bit integer and that all operations are valid.

## Examples

### Example 1:
```
Input: ops = ["1","2","+","C","5","D"]
Output: 18

Explanation:
"1" - Add 1 to the record, record = [1]
"2" - Add 2 to the record, record = [1, 2]
"+" - Add 1 + 2 = 3 to the record, record = [1, 2, 3]
"C" - Invalidate and remove the previous score, record = [1, 2]
"5" - Add 5 to the record, record = [1, 2, 5]
"D" - Add 2 * 5 = 10 to the record, record = [1, 2, 5, 10]
Total sum: 1 + 2 + 5 + 10 = 18
```

### Example 2:
```
Input: ops = ["5","D","+","C"]
Output: 15

Explanation:
"5" - Add 5 to the record, record = [5]
"D" - Add 2 * 5 = 10 to the record, record = [5, 10]
"+" - Add 5 + 10 = 15 to the record, record = [5, 10, 15]
"C" - Invalidate and remove the previous score, record = [5, 10]
Total sum: 5 + 10 = 15
```

### Example 3:
```
Input: ops = ["5","-2","4","C","D","9","+","+"]
Output: 27

Explanation:
"5" - record = [5]
"-2" - record = [5, -2]
"4" - record = [5, -2, 4]
"C" - record = [5, -2]
"D" - record = [5, -2, -4]
"9" - record = [5, -2, -4, 9]
"+" - record = [5, -2, -4, 9, 5]  (9 + (-4) = 5)
"+" - record = [5, -2, -4, 9, 5, 14]  (5 + 9 = 14)
Total sum: 5 + (-2) + (-4) + 9 + 5 + 14 = 27
```

### Example 4:
```
Input: ops = ["1"]
Output: 1

Explanation:
"1" - record = [1]
Total sum: 1
```

## Constraints
- 1 <= operations.length <= 1000
- operations[i] is "C", "D", "+", or a string representing an integer in the range [-30,000, 30,000]
- For operation "+", there will always be at least two previous scores on the record
- For operations "C" and "D", there will always be at least one previous score on the record

**Recommended Complexity**: O(n) time, O(n) space where n is the number of operations

---

## Pattern Recognition

**Primary Pattern**: **Stack (LIFO - Last In First Out)**

**Why This Pattern?**
- Need to access most recent scores
- Need to remove most recent score (invalidate)
- Need to reference previous scores for calculations
- LIFO behavior: last added score is first accessed

**Key Insight**: Stack for Recent Score Access
```
Problem: Track scores with operations on recent values

Critical observation:
  All operations depend on MOST RECENT scores:
    - 'C': Remove last score
    - 'D': Use last score
    - '+': Use last TWO scores
  
  This is classic LIFO (Last In, First Out) behavior!
  
Example: record = [1, 2, 5]
  'D' operation: Need 5 (last score)
  '+' operation: Need 5 and 2 (last two scores)
  'C' operation: Remove 5 (last score)
  
Solution: Use Stack!
  - Push: Add new score
  - Pop: Remove last score ('C' operation)
  - Peek: Access last score without removing
```

**Why Stack?**
```
Problem characteristics:
  ✓ Need access to most recent elements
  ✓ Need to remove most recent element
  ✓ Need to look at recent elements without removing
  ✓ Order matters (most recent first)
  
Stack operations match perfectly:
  - push(): Add new score
  - pop(): Remove score ('C' operation)
  - peek(): Look at last score without removing
  
Example trace:
  "1" → push(1) → stack = [1]
  "2" → push(2) → stack = [1, 2]
  "+" → peek twice, calculate, push → stack = [1, 2, 3]
  "C" → pop() → stack = [1, 2]
```

**The Operations Breakdown**:
```
Operation 1: Integer (e.g., "5", "-2")
  Action: Parse to int, push to stack
  Example: "5" → push(5)
  
Operation 2: "+"
  Action: 
    1. Get last score (last)
    2. Get second-to-last score (secondLast)
    3. Push (last + secondLast)
  
  Important: Don't pop! Need to keep original scores
  
  Example: stack = [1, 2]
    last = peek() = 2
    pop() to access second
    secondLast = peek() = 1
    push(2) back (restore)
    push(2 + 1 = 3)
    Result: [1, 2, 3]

Operation 3: "D"
  Action:
    1. Get last score
    2. Push (2 × last)
  
  Example: stack = [5]
    last = peek() = 5
    push(2 × 5 = 10)
    Result: [5, 10]

Operation 4: "C"
  Action:
    1. Remove last score (pop)
  
  Example: stack = [1, 2, 3]
    pop() removes 3
    Result: [1, 2]
```

**Critical Detail**: Don't Destructively Access for '+'
```
Common mistake: '+' operation
  WRONG:
    last = pop()
    secondLast = pop()
    push(last + secondLast)
    → Loses original values!
  
  CORRECT:
    last = pop()
    secondLast = peek()  // Don't pop again!
    push(last)  // Restore last
    push(last + secondLast)  // Add sum
    
Example: stack = [5, 10]
  WRONG approach:
    last = pop() = 10
    secondLast = pop() = 5
    push(15)
    Result: [15] ❌ (lost 5 and 10!)
  
  CORRECT approach:
    last = pop() = 10
    secondLast = peek() = 5
    push(10) back
    push(15)
    Result: [5, 10, 15] ✓
```

**Related Patterns**:
1. **Stack** — LIFO data structure
2. **Simulation** — Execute operations step by step
3. **String Parsing** — Convert string to operations
4. **State Tracking** — Maintain running record

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Use array, shift elements for operations
  For 'C': Remove last, shift all elements
  For '+': Access last two, add new, possibly resize
  For 'D': Access last, add new, possibly resize
  
Time: O(n) per operation for shifting = O(n²) total ❌
Space: O(n) for array
Too slow!

Stack Approach:
  All operations in O(1):
    - Push: O(1)
    - Pop: O(1)
    - Peek: O(1)
  → O(n) total time ✅
  → O(n) space
```

**The Optimal Strategy**:
```
Key observations:
  1. Only need access to most recent scores
  2. Stack provides O(1) access to top
  3. All operations can be done with stack operations
  
Algorithm:
  1. Initialize empty stack
  2. For each operation:
     - Integer: parse and push
     - '+': peek twice, calculate sum, push
     - 'D': peek once, calculate double, push
     - 'C': pop (remove)
  3. Sum all elements in stack
  
Why it works:
  - Stack maintains valid scores in order
  - Recent scores at top (easy access)
  - Can add/remove in O(1)
```

### Step-by-Step Algorithm

---

#### **Approach 1: Stack with Proper '+' Handling (OPTIMAL)**

**Core Idea**:
- Use stack to store valid scores
- Process each operation with appropriate stack operations
- For '+', carefully preserve both previous scores
- Sum all remaining scores at end

**Algorithm**
```
calPoints(operations):
    stack = empty stack
    
    for each op in operations:
        if op is "C":
            stack.pop()  // Remove last score
        
        else if op is "D":
            stack.push(2 × stack.peek())  // Double last score
        
        else if op is "+":
            last = stack.pop()
            secondLast = stack.peek()
            stack.push(last)  // Restore
            stack.push(last + secondLast)  // Add sum
        
        else:  // Integer
            stack.push(parseInt(op))
    
    // Sum all scores in stack
    sum = 0
    while stack not empty:
        sum += stack.pop()
    
    return sum
```

**Code Implementation**
```java
class Solution {
    public int calPoints(String[] operations) {
        Stack<Integer> stack = new Stack<>();
        
        for (String op : operations) {
            if (op.equals("C")) {
                // Remove the last score
                stack.pop();
                
            } else if (op.equals("D")) {
                // Double the last score
                stack.push(2 * stack.peek());
                
            } else if (op.equals("+")) {
                // Sum of last two scores
                int last = stack.pop();
                int secondLast = stack.peek();
                stack.push(last);  // Restore last
                stack.push(last + secondLast);  // Add sum
                
            } else {
                // Integer score
                stack.push(Integer.parseInt(op));
            }
        }
        
        // Calculate total sum
        int sum = 0;
        while (!stack.isEmpty()) {
            sum += stack.pop();
        }
        
        return sum;
    }
}
```

**Example Walkthrough**

Input: `ops = ["5","2","C","D","+"]`

| Step | Operation | Action | Stack | Explanation |
|------|-----------|--------|-------|-------------|
| Init | - | - | [] | Empty stack |
| 1 | "5" | push(5) | [5] | Add score 5 |
| 2 | "2" | push(2) | [5, 2] | Add score 2 |
| 3 | "C" | pop() | [5] | Remove last score (2) |
| 4 | "D" | push(2×5) | [5, 10] | Double last score (5) |
| 5 | "+" | push(5+10) | [5, 10, 15] | Sum last two (5+10=15) |
| Sum | - | - | - | 5 + 10 + 15 = 30 |

**Output:** `30`

**Complexity Analysis**
- **Time Complexity**: O(n) — Process each operation once, then sum stack
- **Space Complexity**: O(n) — Stack stores all valid scores (at most n)

---

#### **Approach 2: Stack with Alternative '+' Implementation**

**Core Idea**: Store values in list, access by index for '+' operation.

**Code Implementation**
```java
class Solution {
    public int calPoints(String[] operations) {
        List<Integer> record = new ArrayList<>();
        
        for (String op : operations) {
            int n = record.size();
            
            if (op.equals("C")) {
                record.remove(n - 1);
                
            } else if (op.equals("D")) {
                record.add(2 * record.get(n - 1));
                
            } else if (op.equals("+")) {
                record.add(record.get(n - 1) + record.get(n - 2));
                
            } else {
                record.add(Integer.parseInt(op));
            }
        }
        
        // Calculate sum
        int sum = 0;
        for (int score : record) {
            sum += score;
        }
        
        return sum;
    }
}
```

**Key Difference**: 
- Uses ArrayList instead of Stack
- Direct index access for '+' operation (no pop/push)
- More straightforward but conceptually less "stack-like"

**Complexity Analysis**
- **Time Complexity**: O(n) — Same as stack approach
- **Space Complexity**: O(n) — ArrayList stores all scores

---

#### **Approach 3: Stack with Running Sum**

**Core Idea**: Maintain running sum instead of calculating at end.

**Code Implementation**
```java
class Solution {
    public int calPoints(String[] operations) {
        Stack<Integer> stack = new Stack<>();
        int sum = 0;
        
        for (String op : operations) {
            if (op.equals("C")) {
                sum -= stack.pop();
                
            } else if (op.equals("D")) {
                int doubled = 2 * stack.peek();
                stack.push(doubled);
                sum += doubled;
                
            } else if (op.equals("+")) {
                int last = stack.pop();
                int secondLast = stack.peek();
                stack.push(last);
                int newScore = last + secondLast;
                stack.push(newScore);
                sum += newScore;
                
            } else {
                int score = Integer.parseInt(op);
                stack.push(score);
                sum += score;
            }
        }
        
        return sum;
    }
}
```

**Key Difference**: 
- Maintains sum during processing
- No need to iterate stack at end
- Slightly more efficient (one pass instead of two)

**Complexity Analysis**
- **Time Complexity**: O(n) — Single pass through operations
- **Space Complexity**: O(n) — Stack stores all scores

---

#### **Approach 4: Brute Force with Array**

**Core Idea**: Use dynamic array, manually manage indices.

**Code Implementation**
```java
class Solution {
    public int calPoints(String[] operations) {
        int[] record = new int[operations.length];
        int index = 0;
        
        for (String op : operations) {
            if (op.equals("C")) {
                index--;
                
            } else if (op.equals("D")) {
                record[index] = 2 * record[index - 1];
                index++;
                
            } else if (op.equals("+")) {
                record[index] = record[index - 1] + record[index - 2];
                index++;
                
            } else {
                record[index] = Integer.parseInt(op);
                index++;
            }
        }
        
        int sum = 0;
        for (int i = 0; i < index; i++) {
            sum += record[i];
        }
        
        return sum;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) — Same as stack
- **Space Complexity**: O(n) — Fixed array
- **Note**: Works but less intuitive than stack

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Array | ArrayList | **Stack** |
|-------------|-------|-----------|-----------|
| Time complexity | O(n) ✓ | O(n) ✓ | **O(n) ✅** |
| Space complexity | O(n) ✓ | O(n) ✓ | **O(n) ✅** |
| Code simplicity | Medium | ✓ | **✅** |
| Conceptual fit | ⚠️ | ✓ | **✅** |
| LIFO operations | ⚠️ | ⚠️ | **✅** |

**Winner**: **Stack** — perfect conceptual match and clean code!

### Why Stack is Perfect?

```
Problem operations map directly to stack operations:

'C' → Remove last score
  Stack: pop() → O(1) ✓
  Array: Need to track index, manually decrement
  
'D' → Use last score
  Stack: peek() → O(1) ✓
  Array: Access record[index-1]
  
'+' → Use last two scores
  Stack: pop(), peek(), push(), push() → O(1) ✓
  Array: Access record[index-1] and record[index-2]
  
Integer → Add score
  Stack: push() → O(1) ✓
  Array: record[index++]

Natural fit: Stack was designed for LIFO access patterns!
```

### Why Not Just ArrayList?

```
ArrayList works but:
  1. Less semantic clarity
     stack.pop() vs record.remove(record.size() - 1)
  
  2. Stack operations built-in
     peek() is clearer than get(size() - 1)
  
  3. Type safety
     Stack<Integer> enforces integer type
  
  4. Interview expectations
     Stack shows pattern recognition

Example comparison:
  Stack: if (op.equals("D")) stack.push(2 * stack.peek());
  ArrayList: if (op.equals("D")) record.add(2 * record.get(record.size()-1));
  
  Stack version is cleaner and more readable!
```

### The '+' Operation Pitfall

```
Why the careful implementation?

WRONG (destroys values):
  int last = stack.pop();
  int secondLast = stack.pop();
  stack.push(last + secondLast);
  
  Before: [5, 10]
  After: [15]
  Lost 5 and 10! ❌

CORRECT (preserves values):
  int last = stack.pop();
  int secondLast = stack.peek();  // Don't pop!
  stack.push(last);  // Restore
  stack.push(last + secondLast);
  
  Before: [5, 10]
  After: [5, 10, 15] ✓

Why it matters:
  Later '+' operation might need these values!
  
  Example: ["5", "10", "+", "+"]
    After first '+': [5, 10, 15]
    Second '+' needs 15 and 10:
      If we lost them, second '+' fails!
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Operation**
```java
Input: ops = ["100"]
Output: 100
Explanation: Single score, return it directly.
```

### 2. **All Removals at End**
```java
Input: ops = ["1", "2", "3", "C", "C", "C"]
Output: 0
Explanation: All scores removed, empty record, sum = 0.
```

### 3. **Negative Numbers**
```java
Input: ops = ["-5", "D", "+"]
Output: -20
Explanation:
"-5" → [-5]
"D" → [-5, -10]
"+" → [-5, -10, -15]
Sum: -5 + (-10) + (-15) = -20
```

### 4. **Multiple '+' Operations**
```java
Input: ops = ["5", "2", "+", "+"]
Output: 21
Explanation:
"5" → [5]
"2" → [5, 2]
"+" → [5, 2, 7]  (5+2)
"+" → [5, 2, 7, 9]  (2+7)
Sum: 5 + 2 + 7 + 9 = 23
```

### 5. **Alternating Operations**
```java
Input: ops = ["1", "C", "2", "C", "3"]
Output: 3
Explanation:
"1" → [1]
"C" → []
"2" → [2]
"C" → []
"3" → [3]
Sum: 3
```

### 6. **Large Numbers**
```java
Input: ops = ["30000", "D"]
Output: 90000
Explanation:
"30000" → [30000]
"D" → [30000, 60000]
Sum: 30000 + 60000 = 90000
```

### 7. **Zero Values**
```java
Input: ops = ["0", "D", "+"]
Output: 0
Explanation:
"0" → [0]
"D" → [0, 0]
"+" → [0, 0, 0]
Sum: 0
```

### 8. **Complex Sequence**
```java
Input: ops = ["5", "-2", "4", "C", "D", "9", "+", "+"]
Output: 27
Explanation:
"5" → [5]
"-2" → [5, -2]
"4" → [5, -2, 4]
"C" → [5, -2]
"D" → [5, -2, -4]
"9" → [5, -2, -4, 9]
"+" → [5, -2, -4, 9, 5]
"+" → [5, -2, -4, 9, 5, 14]
Sum: 5 + (-2) + (-4) + 9 + 5 + 14 = 27
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Destructive '+' Operation**
```java
// WRONG - loses previous scores
if (op.equals("+")) {
    int last = stack.pop();
    int secondLast = stack.pop();  // WRONG! Pops both
    stack.push(last + secondLast);
}
```

**Why wrong**: Destroys values needed for future operations!

**Dry run failure for ops=["5","2","+","+"]:**
```
"5" → stack = [5]
"2" → stack = [5, 2]
"+" → pop(2), pop(5), push(7) → stack = [7]
"+" → Need last two scores but only have [7]!
  pop(7) → stack = []
  pop() → ERROR! Stack empty!
  
Should be: [5, 2, 7, 9]
```

**Fix**: Preserve original scores
```java
int last = stack.pop();
int secondLast = stack.peek();  // Peek, don't pop!
stack.push(last);  // Restore
stack.push(last + secondLast);
```

### ❌ **MISTAKE 2: Wrong Order for '+'**
```java
// WRONG - adds in wrong order
if (op.equals("+")) {
    int first = stack.pop();
    int second = stack.pop();
    stack.push(second);
    stack.push(first);  // WRONG! Should push sum, not first
    stack.push(first + second);
}
```

**Why wrong**: Pushes values back in wrong order!

**Dry run failure:**
```
stack = [5, 10]
pop() → first = 10
pop() → second = 5
push(5) → stack = [5]
push(10) → stack = [5, 10]  (wrong order!)
push(15) → stack = [5, 10, 15]  (looks right but...)

Next operation might depend on correct order!
```

**Fix**: Correct restoration order
```java
int last = stack.pop();
int secondLast = stack.peek();
stack.push(last);  // Restore in original order
stack.push(last + secondLast);
```

### ❌ **MISTAKE 3: Using parseInt Without Try-Catch**
```java
// WRONG - no error handling
else {
    stack.push(Integer.parseInt(op));  // What if invalid?
}
```

**Why wrong**: Problem guarantees valid input, but defensive programming is good!

**Better**: Check if it's a number first
```java
else {
    // All other cases are numbers (per problem constraints)
    stack.push(Integer.parseInt(op));
}
```

### ❌ **MISTAKE 4: Not Handling Empty Stack**
```java
// WRONG - doesn't check if stack is empty
if (op.equals("C")) {
    stack.pop();  // What if stack is empty?
}
```

**Why wrong**: Problem guarantees valid operations, but good to verify!

**Fix**: Add safety check (though not needed per constraints)
```java
if (op.equals("C")) {
    if (!stack.isEmpty()) {  // Safety check
        stack.pop();
    }
}
```

### ❌ **MISTAKE 5: Summing Stack Destructively Without Saving**
```java
// WRONG - destroys stack while summing
int sum = 0;
while (!stack.isEmpty()) {
    sum += stack.pop();  // Stack becomes empty!
}
// If we need stack later, it's gone!
```

**Why wrong**: If we need to access scores again, they're lost!

**Fix**: Either don't care (if single use) or iterate without popping
```java
// Option 1: Destructive (if done processing)
int sum = 0;
while (!stack.isEmpty()) {
    sum += stack.pop();
}

// Option 2: Non-destructive (preserve stack)
int sum = 0;
for (int score : stack) {
    sum += score;
}
```

### ❌ **MISTAKE 6: Wrong Comparison for String Operations**
```java
// WRONG - uses == instead of .equals()
if (op == "C") {  // WRONG! String comparison with ==
    stack.pop();
}
```

**Why wrong**: `==` compares references, not values!

**Dry run failure:**
```
op = new String("C");
op == "C" → false (different objects!)
Operation not recognized, treated as integer
parseInt("C") → NumberFormatException!
```

**Fix**: Use .equals()
```java
if (op.equals("C")) {
    stack.pop();
}
```

### ❌ **MISTAKE 7: Forgetting to Handle Negative Numbers**
```java
// WRONG - assumes all numbers are positive
else {
    int num = Integer.parseInt(op);
    if (num >= 0) {  // WRONG! Negative numbers allowed
        stack.push(num);
    }
}
```

**Why wrong**: Problem allows negative numbers!

**Dry run failure for ops=["-5"]:**
```
op = "-5"
parseInt("-5") = -5
-5 >= 0? No
Don't push anything
Result: Empty stack, sum = 0 (should be -5!)
```

**Fix**: Don't filter, push all parsed integers
```java
else {
    stack.push(Integer.parseInt(op));
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Process each operation | O(n) | Iterate through n operations |
| Each operation | O(1) | Stack push/pop/peek all O(1) |
| Sum stack | O(n) | Iterate through remaining scores |
| **Total** | **O(n)** | Linear in input size |

**Detailed breakdown:**
```
For n operations:
  Each operation: O(1)
    - Integer: parseInt + push = O(1)
    - 'C': pop = O(1)
    - 'D': peek + push = O(1)
    - '+': pop + peek + push + push = O(1)
  
  Total: n × O(1) = O(n)

Sum stack: O(k) where k = final stack size ≤ n
  → O(n) worst case

Total: O(n) + O(n) = O(n)
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Stack | O(n) | Worst case: all operations are integers |
| Variables | O(1) | Temp variables for calculations |
| **Total** | **O(n)** | Dominated by stack size |

**Why O(n)?**
```
Worst case: All operations are integers
  ops = ["1", "2", "3", ..., "n"]
  Each pushes to stack
  Final stack size: n
  → O(n) space

Best case: Many 'C' operations
  ops = ["1", "C", "2", "C", ...]
  Stack size stays small
  → O(1) space
  
Average/worst: O(n)
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `ops = ["5", "2", "C", "D", "+"]`

**Goal:** Process operations and calculate sum.

---

**Step 1: Initialize**
```
ops = ["5", "2", "C", "D", "+"]
stack = []
```

---

**Step 2: Process "5"**
```
Operation: "5" (integer)
Action: Parse and push

Before: stack = []
Push: 5
After: stack = [5]

Explanation: Add score 5 to record
```

---

**Step 3: Process "2"**
```
Operation: "2" (integer)
Action: Parse and push

Before: stack = [5]
Push: 2
After: stack = [5, 2]

Explanation: Add score 2 to record
```

---

**Step 4: Process "C"**
```
Operation: "C" (cancel)
Action: Remove last score

Before: stack = [5, 2]
Pop: Remove 2
After: stack = [5]

Explanation: Invalidate last score (2)
```

---

**Step 5: Process "D"**
```
Operation: "D" (double)
Action: Double last score

Before: stack = [5]
Peek: Last score = 5
Calculate: 2 × 5 = 10
Push: 10
After: stack = [5, 10]

Explanation: Record double of last score
```

---

**Step 6: Process "+"**
```
Operation: "+" (sum)
Action: Sum last two scores

Before: stack = [5, 10]

Detailed steps:
  1. Pop: last = 10
     stack = [5]
  
  2. Peek: secondLast = 5
     stack = [5]
  
  3. Push: Restore 10
     stack = [5, 10]
  
  4. Calculate: 5 + 10 = 15
  
  5. Push: 15
     stack = [5, 10, 15]

After: stack = [5, 10, 15]

Explanation: Add sum of last two scores (5+10=15)
```

---

**Step 7: Calculate Sum**
```
Final stack: [5, 10, 15]

Sum calculation:
  5 + 10 + 15 = 30

Result: 30
```

---

**Final Result:** `30`

### Visual State Diagram

```
Operation Sequence:
  "5"  →  [5]
  "2"  →  [5, 2]
  "C"  →  [5]
  "D"  →  [5, 10]
  "+"  →  [5, 10, 15]
  
Sum: 5 + 10 + 15 = 30
```

### Another Example with Complex Operations

**Input:** `ops = ["5", "-2", "4", "C", "D", "9", "+", "+"]`

```
Step-by-step:

"5"   → [5]
"-2"  → [5, -2]
"4"   → [5, -2, 4]
"C"   → [5, -2]           (remove 4)
"D"   → [5, -2, -4]       (double -2 = -4)
"9"   → [5, -2, -4, 9]
"+"   → [5, -2, -4, 9, 5] (9 + (-4) = 5)
"+"   → [5, -2, -4, 9, 5, 14] (5 + 9 = 14)

Sum: 5 + (-2) + (-4) + 9 + 5 + 14 = 27
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Simplicity | Pattern Clarity |
|----------|------|-------|-----------------|-----------------|
| **Stack (Standard)** | **O(n)** | **O(n)** | **✅** | **✅** |
| ArrayList | O(n) | O(n) | ✓ | ⚠️ |
| Stack + Running Sum | O(n) | O(n) | ⚠️ | ✓ |
| Array with Manual Index | O(n) | O(n) | ⚠️ | ⚠️ |

**Recommendation**: Use **Standard Stack** — clear pattern, clean code, optimal complexity!

---

## Key Takeaways

1. **Stack for LIFO** — perfect for accessing recent elements
2. **Preserve values for '+'** — pop/peek/restore pattern
3. **String comparison with .equals()** — not ==
4. **All operations O(1)** — stack operations are constant time
5. **Handle negatives** — problem allows negative scores
6. **Parse integers carefully** — use Integer.parseInt()
7. **Sum at end** — iterate through final stack

---

## Interview Tips

**What to say in an interview:**

> "This is a classic stack problem because all operations depend on the most recent scores, which is LIFO behavior. I'll use a stack to maintain the record of valid scores. For integer operations, I parse and push. For 'C', I pop to remove the last score. For 'D', I peek at the last score, double it, and push. For '+', I need to be careful: I pop to get the last score, peek to get the second-to-last, restore the last score by pushing it back, then push their sum. This preserves both original values for potential future operations. Finally, I sum all remaining scores in the stack. This gives O(n) time since each operation is O(1) and we process n operations, with O(n) space for the stack."

**Key points to mention:**
1. **Stack for LIFO access** — recent scores at top
2. **Careful '+' implementation** — preserve original values
3. **All operations O(1)** — push, pop, peek
4. **String operations** — use .equals() not ==
5. **Complexity** — O(n) time, O(n) space

**If asked about alternatives:**
> "I could use an ArrayList and access by index, which would also be O(n) time and space. However, stack is more semantically correct since it explicitly supports LIFO operations. The stack operations (push, pop, peek) make the code cleaner and more readable than ArrayList's add, remove, and get methods."

**Common Follow-ups:**
- "What if you need to support undo?" → Keep history stack of operations
- "What if 'C' removes all previous scores?" → Check isEmpty() before operations
- "Can you optimize space?" → Already optimal, need to store all valid scores
- "What about invalid operations?" → Problem guarantees valid, but could add validation

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Baseball Game** | Easy | **Stack (Basic)** | **This problem** |
| Valid Parentheses | Easy | Stack (Matching) | Character matching instead of calculations |
| Min Stack | Medium | Stack (Auxiliary) | Track minimum with extra stack |
| Evaluate Reverse Polish Notation | Medium | Stack (Expression) | More complex calculations |
| Simplify Path | Medium | Stack (String Processing) | Unix path simplification |
| Backspace String Compare | Easy | Stack (Character Removal) | Similar removal pattern |

**Pattern Progression**:
1. **Basic stack operations** (this problem) — Baseball Game
2. **Stack with matching** (easier) — Valid Parentheses
3. **Stack with calculations** (harder) — Reverse Polish Notation

---

## Final Pattern Label

✅ **Stack (LIFO) - Basic Operations with State Tracking**

**Remember:** Use a stack to maintain valid scores with LIFO access. For the '+' operation, be careful to preserve both previous scores: pop to get the last, peek to get second-to-last, restore the last, then push the sum. This ensures future operations have access to all necessary values. All operations are O(1), giving O(n) total time with O(n) space for the stack!
