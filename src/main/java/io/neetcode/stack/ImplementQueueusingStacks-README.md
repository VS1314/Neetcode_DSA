# Implement Queue using Stacks

## Problem Description

**Difficulty**: Easy

Implement a first-in-first-out (FIFO) queue using **only two stacks**. The implemented queue should support all the functions of a normal queue (`push`, `peek`, `pop`, and `empty`).

Implement the `MyQueue` class:

- `void push(int x)` - Pushes element x to the back of the queue
- `int pop()` - Removes the element from the front of the queue and returns it
- `int peek()` - Returns the element at the front of the queue
- `boolean empty()` - Returns true if the queue is empty, false otherwise

**Notes**:
- You must use **only standard operations of a stack**: push to top, peek/pop from top, size, and is empty
- Depending on your language, the stack may not be supported natively. You may simulate a stack using a list or deque as long as you use only a stack's standard operations

## Examples

### Example 1:
```
Input: 
["MyQueue", "push", "push", "peek", "pop", "empty"]
[[], [1], [2], [], [], []]

Output: 
[null, null, null, 1, 1, false]

Explanation:
MyQueue myQueue = new MyQueue();
myQueue.push(1);  // queue: [1]
myQueue.push(2);  // queue: [1, 2] (leftmost is front)
myQueue.peek();   // return 1 (front element)
myQueue.pop();    // return 1, queue: [2]
myQueue.empty();  // return false
```

### Example 2:
```
Input:
["MyQueue", "push", "push", "push", "pop", "push", "pop", "pop", "peek", "empty"]
[[], [1], [2], [3], [], [4], [], [], [], []]

Output:
[null, null, null, null, 1, null, 2, 3, 4, false]

Explanation:
push(1), push(2), push(3) → queue: [1, 2, 3]
pop() → 1, queue: [2, 3]
push(4) → queue: [2, 3, 4]
pop() → 2, queue: [3, 4]
pop() → 3, queue: [4]
peek() → 4
empty() → false
```

### Example 3:
```
Input:
["MyQueue", "push", "pop", "push", "pop", "empty"]
[[], [1], [], [2], [], []]

Output:
[null, null, 1, null, 2, true]

Explanation:
push(1) → [1]
pop() → 1, []
push(2) → [2]
pop() → 2, []
empty() → true
```

## Constraints
- 1 <= x <= 9
- At most 100 calls will be made to push, pop, peek, and empty
- All the calls to pop and peek are valid

**Follow-up**: Can you implement the queue such that each operation is **amortized O(1)** time complexity?

---

## Pattern Recognition

**Primary Pattern**: **Stack Manipulation / Data Structure Design (Two-Stack Technique)**

**Why This Pattern?**
- Need to simulate FIFO (queue) using LIFO (stack)
- Stack removes from top, queue removes from front
- Must reverse order using stack operations
- Design problem: implement one data structure using another

**Key Insight**: Two-Stack Reversal
```
Problem: Implement queue (FIFO) using stacks (LIFO)

Queue behavior: First In, First Out
  push(1), push(2), push(3)
  pop() → 1 (first added)
  
Stack behavior: Last In, First Out
  push(1), push(2), push(3)
  pop() → 3 (last added)
  
Challenge: Stack gives us REVERSE order!

Key insight: Use TWO stacks to reverse twice
  Reverse once → gets us LIFO
  Reverse again → gets us FIFO! ✓
```

**The Two-Stack Strategy**:
```
Stack 1 (input): Receives new elements
Stack 2 (output): Provides elements for removal

Flow:
  push(x): Add to input stack
  
  pop()/peek(): 
    If output stack empty:
      Transfer ALL from input to output (reverses order!)
    Return from output stack
    
Example: push(1), push(2), push(3)
  input: [1, 2, 3] (3 on top)
  output: []
  
  pop():
    Transfer: pop from input, push to output
      pop(3) → push to output → output: [3]
      pop(2) → push to output → output: [3, 2]
      pop(1) → push to output → output: [3, 2, 1]
    
    Now output: [3, 2, 1] (1 on top!)
    pop from output → 1 ✓ (correct FIFO order!)
```

**Why This Works**:
```
Visualization:

input stack (LIFO):     output stack (LIFO):
  3 ← top                 1 ← top
  2                       2
  1 ← bottom              3 ← bottom
  
Transfer from input to output:
  pop(3), pop(2), pop(1) from input
  push(3), push(2), push(1) to output
  
Result: Order reversed! 
  Bottom of input → Top of output
  First pushed → First to be popped from output!
```

**Critical Detail**: Lazy Transfer
```
Don't transfer on every operation!
  Only transfer when output stack is EMPTY
  
Why?
  Once elements are in output stack, they're in correct order
  Keep popping from output until it's empty
  Only then transfer more from input
  
Example:
  push(1), push(2), push(3)
  input: [1, 2, 3]
  
  pop(): Transfer all to output
    output: [3, 2, 1]
    Return 1 (pop from output)
    
  pop(): Output still has [3, 2]
    Don't transfer! Just pop from output
    Return 2 ✓
    
  push(4), push(5)
  input: [4, 5]
  output: [3]
  
  pop(): Output not empty
    Return 3 (pop from output)
    
  pop(): Output now empty!
    Transfer from input: [4, 5] → [5, 4]
    Return 4 ✓
```

**Amortized O(1) Analysis**:
```
Key insight: Each element transferred at most ONCE
  
Element lifecycle:
  1. Pushed to input stack: O(1)
  2. Transferred to output stack: O(1)
  3. Popped from output stack: O(1)
  
Total: 3 operations per element

For n operations:
  n pushes + n pops = 2n operations
  Each element: 1 push to input + 1 transfer + 1 pop from output = 3 steps
  Total: 3n steps for n elements
  Average: 3n / 2n = 1.5 ≈ O(1) amortized! ✓
```

**Related Patterns**:
1. **Two Stacks** — Use two for reversal
2. **Lazy Evaluation** — Transfer only when needed
3. **Amortized Analysis** — Average over many operations
4. **Data Structure Design** — Implement one using another

---

## Algorithm & Approach

### Core Insight

**Why Naive Approach Fails:**
```
Naive: Use single stack
  push(1), push(2), push(3) → stack = [1, 2, 3] (3 on top)
  pop() should give 1, but stack.pop() gives 3 ❌
  
Can't get FIFO from single LIFO stack!
Need to reverse order somehow.
```

**The Optimal Strategy**:
```
Key observations:
  1. Pushing to stack reverses order
  2. Popping from stack reverses order again
  3. Two reversals = original order (FIFO)!
  
Use two stacks:
  - input: receives new elements
  - output: provides elements for removal
  
Transfer from input to output when needed
  → Reverses order
  → Output now has FIFO order!
```

### Step-by-Step Algorithm

---

#### **Approach 1: Two Stacks with Lazy Transfer (OPTIMAL)**

**Core Idea**:
- Use two stacks: input and output
- Push to input stack
- Transfer to output only when output is empty
- Pop/peek from output stack

**Algorithm**
```
class MyQueue:
    input = new Stack()
    output = new Stack()
    
    push(x):
        input.push(x)  // O(1)
    
    pop():
        transfer()  // Ensure output has elements
        return output.pop()  // O(1)
    
    peek():
        transfer()  // Ensure output has elements
        return output.peek()  // O(1)
    
    transfer():
        if output is empty:
            while input not empty:
                output.push(input.pop())
    
    empty():
        return input.isEmpty() and output.isEmpty()
```

**Code Implementation**
```java
class MyQueue {
    private Stack<Integer> input;
    private Stack<Integer> output;
    
    public MyQueue() {
        input = new Stack<>();
        output = new Stack<>();
    }
    
    // O(1) - just push to input stack
    public void push(int x) {
        input.push(x);
    }
    
    // Amortized O(1) - transfer if needed, then pop
    public int pop() {
        transfer();
        return output.pop();
    }
    
    // Amortized O(1) - transfer if needed, then peek
    public int peek() {
        transfer();
        return output.peek();
    }
    
    // O(1) - check both stacks
    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }
    
    // Transfer from input to output (only when output empty)
    private void transfer() {
        if (output.isEmpty()) {
            while (!input.isEmpty()) {
                output.push(input.pop());
            }
        }
    }
}
```

**Example Walkthrough**

Operations: `push(1), push(2), push(3), pop(), pop(), push(4), pop()`

| Operation | Input Stack | Output Stack | Action | Result |
|-----------|-------------|--------------|--------|--------|
| Init | [] | [] | - | - |
| push(1) | [1] | [] | Push to input | - |
| push(2) | [1, 2] | [] | Push to input | - |
| push(3) | [1, 2, 3] | [] | Push to input | - |
| pop() | [] | [3, 2, 1] | Transfer all, pop 1 | 1 |
| pop() | [] | [3, 2] | Pop 2 (no transfer) | 2 |
| push(4) | [4] | [3, 2] | Push to input | - |
| pop() | [4] | [3] | Pop 3 (no transfer) | 3 |

**Note**: Next pop() would transfer [4] to output

**Complexity Analysis**
- **push(x)**: O(1) — Single push operation
- **pop()**: Amortized O(1) — Each element transferred once
- **peek()**: Amortized O(1) — Each element transferred once
- **empty()**: O(1) — Check two stacks
- **Space**: O(n) — Store n elements across two stacks

---

#### **Approach 2: Two Stacks with Eager Transfer**

**Core Idea**: Transfer on every pop, even if output has elements.

**Code Implementation**
```java
class MyQueue {
    private Stack<Integer> input;
    private Stack<Integer> output;
    
    public MyQueue() {
        input = new Stack<>();
        output = new Stack<>();
    }
    
    public void push(int x) {
        input.push(x);
    }
    
    // O(n) every time - transfer back and forth
    public int pop() {
        // Transfer all from input to output
        while (!input.isEmpty()) {
            output.push(input.pop());
        }
        
        // Pop from output
        int result = output.pop();
        
        // Transfer back to input (WRONG! Inefficient!)
        while (!output.isEmpty()) {
            input.push(output.pop());
        }
        
        return result;
    }
    
    public int peek() {
        // Similar inefficiency
        while (!input.isEmpty()) {
            output.push(input.pop());
        }
        
        int result = output.peek();
        
        while (!output.isEmpty()) {
            input.push(output.pop());
        }
        
        return result;
    }
    
    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }
}
```

**Key Difference**: 
- Transfers on every operation
- Transfers back after each pop/peek
- O(n) for pop/peek instead of amortized O(1)

**Complexity Analysis**
- **push(x)**: O(1) — Single push
- **pop()**: O(n) — Transfer all elements twice
- **peek()**: O(n) — Transfer all elements twice
- **empty()**: O(1) — Check stacks
- **Space**: O(n) — Two stacks
- **Why Not Optimal**: Too many transfers!

---

#### **Approach 3: Single Stack with Recursion (CLEVER)**

**Core Idea**: Use recursion call stack as second stack.

**Code Implementation**
```java
class MyQueue {
    private Stack<Integer> stack;
    
    public MyQueue() {
        stack = new Stack<>();
    }
    
    // O(n) - recursive insert at bottom
    public void push(int x) {
        if (stack.isEmpty()) {
            stack.push(x);
        } else {
            int top = stack.pop();
            push(x);  // Recursive call
            stack.push(top);
        }
    }
    
    // O(1) - just pop from top
    public int pop() {
        return stack.pop();
    }
    
    // O(1) - just peek at top
    public int peek() {
        return stack.peek();
    }
    
    // O(1) - check empty
    public boolean empty() {
        return stack.isEmpty();
    }
}
```

**Key Difference**: 
- Uses recursion to maintain queue order
- Push is O(n), pop/peek are O(1)
- Single stack but uses call stack

**Complexity Analysis**
- **push(x)**: O(n) — Recursive insert at bottom
- **pop()**: O(1) — Top is front of queue
- **peek()**: O(1) — Top is front of queue
- **empty()**: O(1) — Check stack
- **Space**: O(n) — Stack + recursion depth

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | push | pop | peek | Space | Amortized O(1)? |
|----------|------|-----|------|-------|-----------------|
| **Two Stacks (Lazy)** | **O(1)** | **O(1)*** | **O(1)*** | **O(n) ✅** | **Yes ✅** |
| Two Stacks (Eager) | O(1) | O(n) | O(n) | O(n) | No ❌ |
| Single Stack (Recursive) | O(n) | O(1) | O(1) | O(n) | No ❌ |

**Winner**: **Two Stacks with Lazy Transfer** — amortized O(1) for all operations!

### Why Lazy Transfer is Optimal?

```
Lazy transfer: Only move when output empty
  push(1), push(2), push(3)
    input: [1, 2, 3]
    output: []
  
  pop(): Transfer once
    output: [3, 2, 1]
    Return 1
  
  pop(): No transfer!
    output: [3, 2]
    Return 2 ✓
  
  Total: 1 transfer for 2 pops

Eager transfer: Move on every pop
  push(1), push(2), push(3)
  
  pop(): Transfer input→output, pop, transfer output→input
    3 transfers + 1 pop
  
  pop(): Transfer again!
    3 transfers + 1 pop
  
  Total: 6 transfers for 2 pops ❌

Lazy is much more efficient!
```

### Why Amortized O(1)?

```
Amortized analysis: Average cost over sequence of operations

Consider n push + n pop operations:
  
  Each element's journey:
    1. Pushed to input: 1 operation
    2. Transferred to output: 1 operation (once!)
    3. Popped from output: 1 operation
  
  Total per element: 3 operations
  Total for n elements: 3n operations
  
  n pushes + n pops = 2n operations
  Total work: 3n operations
  Average: 3n / 2n = 1.5 ≈ O(1)
  
Key: Each element transferred AT MOST ONCE!
  Not transferred on every pop
  Only when output becomes empty
```

### Why Two Stacks?

```
One stack: Can't reverse order
  push(1), push(2) → [1, 2] (2 on top)
  Need 1, but get 2 ❌

Two stacks: Double reversal = original order
  input: [1, 2] (2 on top)
  
  Transfer to output:
    pop(2), push(2) → output: [2]
    pop(1), push(1) → output: [2, 1] (1 on top!)
  
  Now pop from output gives 1 ✓
  
First stack reverses: [1, 2] → [2, 1]
Second stack reverses again: [2, 1] → [1, 2]
Result: FIFO order!
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Element**
```java
push(5)
input: [5], output: []

pop() → 5
Transfer: output: [5]
Return 5
input: [], output: []
```

### 2. **Alternating Push/Pop**
```java
push(1) → input: [1]
pop() → 1, input: [], output: []
push(2) → input: [2]
pop() → 2, input: [], output: []

Each pop triggers transfer (output empty each time)
```

### 3. **Multiple Pops Without Push**
```java
push(1), push(2), push(3)
input: [1, 2, 3]

pop() → 1 (transfer happens)
output: [3, 2]

pop() → 2 (no transfer!)
output: [3]

pop() → 3 (no transfer!)
output: []

Efficient: Transfer only once!
```

### 4. **Push After Partial Pops**
```java
push(1), push(2), push(3)
input: [1, 2, 3]

pop() → 1
input: [], output: [3, 2]

push(4), push(5)
input: [4, 5], output: [3, 2]

pop() → 2 (from output, no transfer)
pop() → 3 (from output, no transfer)
pop() → 4 (transfer [4,5] to output)
output: [5, 4]
```

### 5. **Empty Queue Operations**
```java
MyQueue q = new MyQueue();
empty() → true
// pop() and peek() not called per constraints
```

### 6. **All Pushes Then All Pops**
```java
push(1), push(2), ..., push(n)
input: [1, 2, ..., n]

pop() → 1 (transfer all n elements)
pop() → 2 (no transfer)
...
pop() → n (no transfer)

Single transfer for all n pops!
Amortized O(1) ✓
```

### 7. **Peek Without Removing**
```java
push(1), push(2)
input: [1, 2]

peek() → 1 (transfer to output)
output: [2, 1]

peek() → 1 (no transfer, just peek)
pop() → 1 (no transfer, just pop)
output: [2]
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Transferring on Every Operation**
```java
// WRONG - transfers every time, not lazy
public int pop() {
    // Always transfer, even if output not empty!
    while (!input.isEmpty()) {
        output.push(input.pop());
    }
    return output.pop();
}
```

**Why wrong**: Wastes time transferring when output already has elements!

**Dry run failure:**
```
push(1), push(2), push(3)
input: [1, 2, 3]

pop():
  Transfer: output: [3, 2, 1]
  Return 1
  input: [], output: [3, 2]

pop():
  Transfer again: But input empty! Nothing to transfer.
  Return 2 ✓ (accidentally works)

But if we had:
push(1), push(2), pop(), push(3), pop()
  After first pop: output: [2]
  push(3): input: [3]
  
  Second pop() with wrong code:
    Transfer input to output: output: [2, 3] ❌
    Should have [3, 2] for correct order!
```

**Fix**: Only transfer when output empty
```java
if (output.isEmpty()) {
    while (!input.isEmpty()) {
        output.push(input.pop());
    }
}
```

### ❌ **MISTAKE 2: Transferring Back After Pop**
```java
// WRONG - transfers back to input after pop
public int pop() {
    while (!input.isEmpty()) {
        output.push(input.pop());
    }
    int result = output.pop();
    
    // WRONG! Transfers back
    while (!output.isEmpty()) {
        input.push(output.pop());
    }
    return result;
}
```

**Why wrong**: Destroys the correct order in output stack!

**Dry run failure:**
```
push(1), push(2), push(3)
input: [1, 2, 3]

pop():
  Transfer to output: [3, 2, 1]
  Pop: result = 1, output: [3, 2]
  Transfer back: input: [2, 3] ❌
  
Now input has [2, 3] (3 on top)

pop():
  Transfer to output: [3, 2]
  Pop: result = 3 ❌ (should be 2!)
  
Order destroyed by transferring back!
```

**Fix**: Don't transfer back, keep in output
```java
// Just pop, don't transfer back
int result = output.pop();
return result;
```

### ❌ **MISTAKE 3: Not Checking Output Empty Before Transfer**
```java
// WRONG - always transfers
public int pop() {
    while (!input.isEmpty()) {
        output.push(input.pop());
    }
    return output.pop();
}
```

**Why wrong**: Explained in Mistake 1, but specifically: mixes old and new elements!

**Fix**: Check output.isEmpty() first
```java
if (output.isEmpty()) {
    while (!input.isEmpty()) {
        output.push(input.pop());
    }
}
return output.pop();
```

### ❌ **MISTAKE 4: Wrong Empty Check**
```java
// WRONG - only checks one stack
public boolean empty() {
    return input.isEmpty();  // WRONG! Output might have elements
}
```

**Why wrong**: Queue not empty if output has elements!

**Dry run failure:**
```
push(1), push(2)
input: [1, 2]

pop() → 1
input: [], output: [2]

empty() using wrong code:
  input.isEmpty() → true ❌
  But output has [2]!
  
Should return false!
```

**Fix**: Check both stacks
```java
return input.isEmpty() && output.isEmpty();
```

### ❌ **MISTAKE 5: Popping From Input Instead of Output**
```java
// WRONG - pops from input
public int pop() {
    transfer();
    return input.pop();  // WRONG! Should pop from output
}
```

**Why wrong**: Input might be empty after transfer!

**Dry run failure:**
```
push(1), push(2)
input: [1, 2]

pop():
  transfer(): output: [2, 1], input: []
  input.pop() → EmptyStackException! ❌
  
Should pop from output!
```

**Fix**: Pop from output
```java
return output.pop();
```

### ❌ **MISTAKE 6: Not Creating Transfer Helper**
```java
// WRONG - duplicates transfer logic
public int pop() {
    if (output.isEmpty()) {
        while (!input.isEmpty()) {
            output.push(input.pop());
        }
    }
    return output.pop();
}

public int peek() {
    if (output.isEmpty()) {
        while (!input.isEmpty()) {
            output.push(input.pop());
        }
    }
    return output.peek();  // Duplicated logic!
}
```

**Why wrong**: Code duplication, violates DRY principle!

**Fix**: Extract to helper method
```java
private void transfer() {
    if (output.isEmpty()) {
        while (!input.isEmpty()) {
            output.push(input.pop());
        }
    }
}

public int pop() {
    transfer();
    return output.pop();
}

public int peek() {
    transfer();
    return output.peek();
}
```

### ❌ **MISTAKE 7: Forgetting to Initialize Stacks**
```java
// WRONG - stacks not initialized
class MyQueue {
    private Stack<Integer> input;
    private Stack<Integer> output;
    
    public MyQueue() {
        // WRONG! Forgot to initialize
    }
    
    public void push(int x) {
        input.push(x);  // NullPointerException!
    }
}
```

**Fix**: Initialize in constructor
```java
public MyQueue() {
    input = new Stack<>();
    output = new Stack<>();
}
```

---

## Complexity Analysis

### Time Complexity

| Operation | Worst Case | Amortized | Explanation |
|-----------|------------|-----------|-------------|
| **push(x)** | **O(1)** | **O(1)** | Single push to input |
| **pop()** | O(n) | **O(1)** | Transfer n elements once, then O(1) |
| **peek()** | O(n) | **O(1)** | Transfer n elements once, then O(1) |
| **empty()** | **O(1)** | **O(1)** | Check two stacks |

**Amortized Analysis for pop()**:
```
Consider n operations: push, push, ..., push, pop, pop, ..., pop

Each element:
  1. Pushed to input: 1 operation
  2. Transferred to output: 1 operation (once!)
  3. Popped from output: 1 operation
  
Total per element: 3 operations

For n elements:
  n pushes + n pops = 2n operations by user
  n pushes to input + n transfers + n pops from output = 3n total operations
  
  Average: 3n / 2n = 1.5 ≈ O(1) amortized ✓

Key: Each element enters and leaves each stack at most once!
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Input stack | O(n) | Store up to n elements |
| Output stack | O(n) | Store up to n elements |
| **Total** | **O(n)** | At most n elements total across both stacks |

**Note**: Elements never duplicated between stacks
```
At any time: 
  Total elements = input.size() + output.size() ≤ n
  
Not O(2n), just O(n)!
```

---

## Visualization

### Complete Example Walkthrough

**Operations:** `push(1), push(2), push(3), pop(), push(4), pop(), pop()`

---

**Step 1: Initialize**
```
MyQueue queue = new MyQueue();
input: []
output: []
```

---

**Step 2: push(1)**
```
Action: input.push(1)

State:
  input: [1]
  output: []

Explanation: Add to input stack
```

---

**Step 3: push(2)**
```
Action: input.push(2)

State:
  input: [1, 2] (2 on top)
  output: []

Explanation: Add to input stack
```

---

**Step 4: push(3)**
```
Action: input.push(3)

State:
  input: [1, 2, 3] (3 on top)
  output: []

Explanation: Add to input stack
```

---

**Step 5: pop()**
```
Action: 
  1. Check output empty: Yes
  2. Transfer from input to output:
     - input.pop() → 3, output.push(3) → output: [3]
     - input.pop() → 2, output.push(2) → output: [3, 2]
     - input.pop() → 1, output.push(1) → output: [3, 2, 1]
  3. output.pop() → 1

State:
  input: []
  output: [3, 2] (2 on top)
  Return: 1

Explanation: Transfer reverses order! 1 is now on top of output.
```

---

**Step 6: push(4)**
```
Action: input.push(4)

State:
  input: [4]
  output: [3, 2] (2 on top)

Explanation: Add to input, output still has elements
```

---

**Step 7: pop()**
```
Action:
  1. Check output empty: No (has [3, 2])
  2. No transfer needed!
  3. output.pop() → 2

State:
  input: [4]
  output: [3]
  Return: 2

Explanation: Output not empty, so just pop from it. No transfer!
```

---

**Step 8: pop()**
```
Action:
  1. Check output empty: No (has [3])
  2. No transfer needed!
  3. output.pop() → 3

State:
  input: [4]
  output: []
  Return: 3

Explanation: Output not empty, so just pop. Now output is empty.
```

---

**Next pop() would trigger transfer of [4] from input to output**

---

### Visual State Diagram

```
Push phase:
  push(1): input: [1], output: []
  push(2): input: [1,2], output: []
  push(3): input: [1,2,3], output: []

Transfer on first pop():
  input: [1, 2, 3]    →    output: [3, 2, 1]
         ↓ ↓ ↓              ↑ ↑ ↑
       pop all           push all
       
  Bottom → Top transfer!
  Result: FIFO order in output!

Subsequent pops (no transfer):
  pop() → 1 from output: [3, 2]
  pop() → 2 from output: [3]
  pop() → 3 from output: []

Mixed operations:
  push(4): input: [4], output: [3]
  pop(): from output: [3] → no transfer
  pop(): output empty → transfer [4]
```

### Transfer Visualization

```
Before transfer:
  input (LIFO):          output (LIFO):
  ┌───┐                 ┌───┐
  │ 3 │ ← top           │   │
  ├───┤                 └───┘
  │ 2 │                   empty
  ├───┤
  │ 1 │ ← bottom
  └───┘

Transfer process:
  Step 1: pop(3) from input, push(3) to output
    input: [1,2]  output: [3]
  
  Step 2: pop(2) from input, push(2) to output
    input: [1]  output: [3,2]
  
  Step 3: pop(1) from input, push(1) to output
    input: []  output: [3,2,1]

After transfer:
  input (LIFO):          output (LIFO):
  ┌───┐                 ┌───┐
  │   │                 │ 1 │ ← top (front of queue!)
  └───┘                 ├───┤
   empty                │ 2 │
                        ├───┤
                        │ 3 │ ← bottom (back of queue!)
                        └───┘

Key: Bottom of input → Top of output!
     First in input → First out from output! (FIFO ✓)
```

---

## Comparison of Approaches

| Approach | push | pop/peek | Space | Amortized O(1)? | Best For |
|----------|------|----------|-------|-----------------|----------|
| **Two Stacks (Lazy)** | **O(1)** | **O(1)*** | **O(n) ✅** | **Yes ✅** | **General use ✅** |
| Two Stacks (Eager) | O(1) | O(n) | O(n) | No ❌ | Never use |
| Single Stack (Recursive) | O(n) | O(1) | O(n) | No ❌ | Rare interview variant |

**Recommendation**: Use **Two Stacks with Lazy Transfer** — amortized O(1) and optimal!

---

## Key Takeaways

1. **Two stacks reverse twice** — gets us FIFO from LIFO
2. **Lazy transfer** — only when output stack empty
3. **Each element transferred once** — amortized O(1)
4. **Don't transfer back** — keep elements in output
5. **Check both stacks for empty** — elements can be in either
6. **Helper method for transfer** — avoid code duplication
7. **Amortized analysis** — average over many operations

---

## Interview Tips

**What to say in an interview:**

> "The challenge is implementing FIFO (queue) using LIFO (stack). A single stack gives us the opposite order, but two stacks can reverse twice to restore FIFO order. I'll use an input stack for new elements and an output stack for removal. When pushing, I simply add to the input stack in O(1). For pop or peek, I check if the output stack is empty. If it is, I transfer all elements from input to output by repeatedly popping from input and pushing to output. This reversal puts elements in FIFO order in the output stack. Then I pop or peek from output. The key optimization is lazy transfer — only moving elements when output is empty. This gives amortized O(1) for all operations because each element is transferred at most once across its lifetime. With n pushes and n pops, we do 3n total operations (push to input, transfer, pop from output), giving us an average of O(1) per operation."

**Key points to mention:**
1. **Two stacks for double reversal** — LIFO → LIFO = FIFO
2. **Lazy transfer** — only when output empty
3. **Amortized O(1)** — each element transferred once
4. **Input for push, output for pop** — clear separation
5. **Transfer reverses order** — bottom of input becomes top of output

**If asked about alternatives:**
> "I could transfer on every operation, but that would make pop O(n) instead of amortized O(1). Or I could use recursion with a single stack to insert at the bottom, but push would be O(n). The lazy two-stack approach is optimal because it achieves amortized O(1) for all operations by transferring each element exactly once."

**Common Follow-ups:**
- "What's the worst-case time for pop?" → O(n) when output empty and input has n elements, but amortized O(1)
- "Can you do it with one stack?" → Yes with recursion, but push becomes O(n)
- "Prove amortized O(1)" → Each element: 1 push + 1 transfer + 1 pop = 3 ops total, 3n/2n = O(1) average
- "What if push is more frequent?" → Still optimal, elements accumulate in input, transferred in batch

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Implement Queue using Stacks** | Easy | **Two Stacks** | **This problem** |
| Implement Stack using Queues | Easy | Two Queues / Rotation | Opposite: LIFO using FIFO |
| Min Stack | Medium | Stack + Auxiliary | Track minimum efficiently |
| Design Circular Queue | Medium | Array + Pointers | Fixed-size circular buffer |
| Design Circular Deque | Medium | Array + Two Pointers | Double-ended queue |

**Pattern Progression**:
1. **Basic transformation** (this problem) — Queue using stacks (FIFO from LIFO)
2. **Reverse transformation** (easy) — Stack using queues (LIFO from FIFO)
3. **Enhanced structures** (medium) — Circular, deque with additional constraints

---

## Final Pattern Label

✅ **Two-Stack Technique / Data Structure Design (FIFO using LIFO with Amortized O(1))**

**Remember:** Use two stacks — input for push, output for pop/peek. Only transfer from input to output when output is empty, which reverses the order to give us FIFO. Each element is transferred exactly once, giving amortized O(1) for all operations. Don't transfer back after popping — keep elements in output until it's empty. Check both stacks for the empty operation!
