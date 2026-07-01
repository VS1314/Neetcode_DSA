# Min Stack

## Problem Description

**Difficulty**: Medium

Design a stack class that supports the `push`, `pop`, `top`, and `getMin` operations.

- `MinStack()` - Initializes the stack object
- `void push(int val)` - Pushes the element `val` onto the stack
- `void pop()` - Removes the element on the top of the stack
- `int top()` - Gets the top element of the stack
- `int getMin()` - Retrieves the minimum element in the stack

Each function should run in **O(1)** time.

## Examples

### Example 1:
```
Input: 
["MinStack", "push", "push", "push", "getMin", "pop", "top", "getMin"]
[[], [1], [2], [0], [], [], [], []]

Output: 
[null, null, null, null, 0, null, 2, 1]

Explanation:
MinStack minStack = new MinStack();
minStack.push(1);    // stack: [1], min: 1
minStack.push(2);    // stack: [1, 2], min: 1
minStack.push(0);    // stack: [1, 2, 0], min: 0
minStack.getMin();   // return 0
minStack.pop();      // stack: [1, 2], min: 1
minStack.top();      // return 2
minStack.getMin();   // return 1
```

### Example 2:
```
Input:
["MinStack", "push", "push", "push", "getMin", "pop", "getMin"]
[[], [-2], [0], [-3], [], [], []]

Output:
[null, null, null, null, -3, null, -2]

Explanation:
push(-2) → stack: [-2], min: -2
push(0) → stack: [-2, 0], min: -2
push(-3) → stack: [-2, 0, -3], min: -3
getMin() → -3
pop() → stack: [-2, 0], min: -2
getMin() → -2
```

### Example 3:
```
Input:
["MinStack", "push", "push", "getMin", "pop", "getMin"]
[[], [2], [2], [], [], []]

Output:
[null, null, null, 2, null, 2]

Explanation:
push(2) → stack: [2], min: 2
push(2) → stack: [2, 2], min: 2 (duplicate)
getMin() → 2
pop() → stack: [2], min: 2
getMin() → 2 (still 2!)
```

## Constraints
- -2^31 <= val <= 2^31 - 1
- pop, top and getMin will always be called on **non-empty** stacks
- At most 30,000 calls will be made to push, pop, top, and getMin

**Recommended Complexity**: O(1) time for each operation, O(n) space

---

## Pattern Recognition

**Primary Pattern**: **Stack + Auxiliary Stack (Parallel Tracking)**

**Why This Pattern?**
- Need to track minimum efficiently
- Minimum changes as elements are pushed/popped
- Must maintain O(1) access to minimum
- Stack operations must remain O(1)

**Key Insight**: Track Minimum at Each Level
```
Problem: Track minimum element while supporting push/pop in O(1)

Naive approach: Scan entire stack for minimum
  getMin(): O(n) to scan all elements ❌
  Too slow!

Key insight: Track minimum AT EACH STACK STATE
  When element pushed, record what minimum was at that point
  When element popped, minimum reverts to previous state
  
Example: push(3), push(5), push(2)
  After push(3): stack = [3], min = 3
  After push(5): stack = [3, 5], min = 3 (still 3!)
  After push(2): stack = [3, 5, 2], min = 2 (new min!)
  
  pop(): Remove 2, min reverts to 3
  pop(): Remove 5, min stays 3
  pop(): Remove 3, stack empty
  
Solution: Use auxiliary stack to track minimum at each level!
```

**The Auxiliary Stack Strategy**:
```
Two stacks approach:
  - mainStack: stores all values
  - minStack: stores minimum at each level
  
Invariant: minStack.top() = minimum of all elements in mainStack

Operations:
  push(val):
    mainStack.push(val)
    if minStack.empty() or val <= minStack.top():
      minStack.push(val)
    else:
      minStack.push(minStack.top())  // Keep current min
  
  pop():
    mainStack.pop()
    minStack.pop()  // Both stacks stay synchronized
  
  top():
    return mainStack.top()
  
  getMin():
    return minStack.top()  // O(1)!
```

**Why This Works**:
```
Visualization: push(3), push(5), push(2), push(4)

mainStack:        minStack:
  4 ← top           2 ← top (min at this level)
  2                 2 (min at this level)
  5                 3 (min at this level)
  3                 3 (min at this level)
  
Each position in minStack stores: "What is minimum up to this point?"

When we pop:
  pop() → remove 4 and top of minStack (2)
    mainStack: [3, 5, 2]
    minStack: [3, 3, 2]
    getMin() → 2 ✓
  
  pop() → remove 2 and top of minStack (2)
    mainStack: [3, 5]
    minStack: [3, 3]
    getMin() → 3 ✓
```

**Critical Detail**: Duplicates and <= vs <
```
Must use <= not < when comparing with minimum!

Why?
  Consider: push(2), push(2)
  
  Using < (WRONG):
    push(2): minStack.push(2)
    push(2): 2 < 2? No, don't push
    minStack: [2] (only one element!)
    
    pop(): Remove 2 from mainStack, pop from minStack
    mainStack: [2], minStack: []
    getMin() → minStack empty! ❌
  
  Using <= (CORRECT):
    push(2): minStack.push(2)
    push(2): 2 <= 2? Yes, push
    minStack: [2, 2]
    
    pop(): mainStack: [2], minStack: [2]
    getMin() → 2 ✓

Key: Push to minStack for duplicates too!
```

**Space Optimization Alternative**:
```
Can we use less space than O(n)?

Approach: Only push to minStack when new minimum
  push(val):
    mainStack.push(val)
    if minStack.empty() or val <= minStack.top():
      minStack.push(val)
  
  pop():
    val = mainStack.pop()
    if val == minStack.top():
      minStack.pop()
  
Space: O(k) where k = number of times minimum changes
  k << n in many cases
  
BUT: Must compare values, risk of == issues with objects
```

**Related Patterns**:
1. **Auxiliary Stack** — Track extra information
2. **Parallel Data Structures** — Maintain synchronized state
3. **Stack Design** — Custom stack implementation
4. **Monotonic Stack** — Related but different

---

## Algorithm & Approach

### Core Insight

**Why Naive Approach Fails:**
```
Naive: Scan for minimum each time
  getMin(): 
    min = Integer.MAX_VALUE
    for each element in stack:
      min = Math.min(min, element)
    return min
  
Time: O(n) per getMin() call ❌
Requirement: O(1) per call
Too slow!

Auxiliary Stack:
  Precompute minimum at each level
  getMin() just returns top of minStack
  → O(1) ✓
```

**The Optimal Strategy**:
```
Key observations:
  1. Minimum only changes when pushing or popping
  2. Can track minimum incrementally
  3. Use second stack to store minimums
  4. Both stacks synchronized (same size)
  
All operations O(1):
  push: Two push operations
  pop: Two pop operations
  top: One peek operation
  getMin: One peek operation
```

### Step-by-Step Algorithm

---

#### **Approach 1: Two Stacks (Full Synchronization) - SAFE**

**Core Idea**:
- Two stacks always same size
- minStack[i] = minimum of mainStack[0...i]
- Always push to both stacks

**Algorithm**
```
class MinStack:
    mainStack = new Stack()
    minStack = new Stack()
    
    push(val):
        mainStack.push(val)
        if minStack.empty():
            minStack.push(val)
        else:
            minStack.push(min(val, minStack.top()))
    
    pop():
        mainStack.pop()
        minStack.pop()
    
    top():
        return mainStack.top()
    
    getMin():
        return minStack.top()
```

**Code Implementation**
```java
class MinStack {
    private Stack<Integer> mainStack;
    private Stack<Integer> minStack;
    
    public MinStack() {
        mainStack = new Stack<>();
        minStack = new Stack<>();
    }
    
    // O(1) - push to both stacks
    public void push(int val) {
        mainStack.push(val);
        
        // Always push to minStack
        if (minStack.isEmpty()) {
            minStack.push(val);
        } else {
            // Push minimum of current val and current min
            minStack.push(Math.min(val, minStack.peek()));
        }
    }
    
    // O(1) - pop from both stacks
    public void pop() {
        mainStack.pop();
        minStack.pop();
    }
    
    // O(1) - peek at main stack
    public int top() {
        return mainStack.peek();
    }
    
    // O(1) - peek at min stack
    public int getMin() {
        return minStack.peek();
    }
}
```

**Example Walkthrough**

Operations: `push(3), push(5), push(2), push(4), getMin(), pop(), getMin()`

| Operation | mainStack | minStack | Action | Result |
|-----------|-----------|----------|--------|--------|
| Init | [] | [] | - | - |
| push(3) | [3] | [3] | Push 3 to both | - |
| push(5) | [3, 5] | [3, 3] | Push 5, push min(5,3)=3 | - |
| push(2) | [3, 5, 2] | [3, 3, 2] | Push 2, push min(2,3)=2 | - |
| push(4) | [3, 5, 2, 4] | [3, 3, 2, 2] | Push 4, push min(4,2)=2 | - |
| getMin() | [3, 5, 2, 4] | [3, 3, 2, 2] | Peek minStack | 2 |
| pop() | [3, 5, 2] | [3, 3, 2] | Pop both | - |
| getMin() | [3, 5, 2] | [3, 3, 2] | Peek minStack | 2 |

**Complexity Analysis**
- **push(val)**: O(1) — Two push operations
- **pop()**: O(1) — Two pop operations
- **top()**: O(1) — One peek operation
- **getMin()**: O(1) — One peek operation
- **Space**: O(n) — Two stacks, each size n

---

#### **Approach 2: Two Stacks (Space Optimized) - TRICKY**

**Core Idea**: Only push to minStack when new minimum found.

**Code Implementation**
```java
class MinStack {
    private Stack<Integer> mainStack;
    private Stack<Integer> minStack;
    
    public MinStack() {
        mainStack = new Stack<>();
        minStack = new Stack<>();
    }
    
    // O(1) - push to main, conditionally to min
    public void push(int val) {
        mainStack.push(val);
        
        // Only push if new minimum (or equal)
        if (minStack.isEmpty() || val <= minStack.peek()) {
            minStack.push(val);
        }
    }
    
    // O(1) - pop from main, conditionally from min
    public void pop() {
        int val = mainStack.pop();
        
        // Only pop from minStack if removing current minimum
        // IMPORTANT: Use .equals() for Integer objects!
        if (!minStack.isEmpty() && val == minStack.peek()) {
            minStack.pop();
        }
    }
    
    // O(1) - peek at main stack
    public int top() {
        return mainStack.peek();
    }
    
    // O(1) - peek at min stack
    public int getMin() {
        return minStack.peek();
    }
}
```

**Key Difference**: 
- minStack smaller (only unique minimums)
- Must compare on pop to know when to remove from minStack
- **Warning**: Be careful with Integer vs int comparison!

**Complexity Analysis**
- **push(val)**: O(1) — One or two push operations
- **pop()**: O(1) — One or two pop operations
- **top()**: O(1) — One peek operation
- **getMin()**: O(1) — One peek operation
- **Space**: O(k) where k ≤ n (number of minimum changes)

---

#### **Approach 3: Single Stack with Pairs**

**Core Idea**: Store (value, min) pairs in single stack.

**Code Implementation**
```java
class MinStack {
    private Stack<int[]> stack;  // [value, min at this level]
    
    public MinStack() {
        stack = new Stack<>();
    }
    
    // O(1) - push pair
    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(new int[]{val, val});
        } else {
            int currentMin = stack.peek()[1];
            stack.push(new int[]{val, Math.min(val, currentMin)});
        }
    }
    
    // O(1) - pop pair
    public void pop() {
        stack.pop();
    }
    
    // O(1) - get value from pair
    public int top() {
        return stack.peek()[0];
    }
    
    // O(1) - get min from pair
    public int getMin() {
        return stack.peek()[1];
    }
}
```

**Key Difference**: 
- Single stack but stores pairs
- Each element carries its own minimum
- Clean and self-contained

**Complexity Analysis**
- **All operations**: O(1) — Single stack access
- **Space**: O(n) — Store pairs (2n integers)

---

#### **Approach 4: Single Stack with Encoding (CLEVER)**

**Core Idea**: Encode minimum into values using differences.

**Code Implementation**
```java
class MinStack {
    private Stack<Long> stack;
    private long min;
    
    public MinStack() {
        stack = new Stack<>();
    }
    
    // O(1) - encode difference
    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(0L);
            min = val;
        } else {
            stack.push((long)val - min);
            if (val < min) {
                min = val;
            }
        }
    }
    
    // O(1) - decode difference
    public void pop() {
        long diff = stack.pop();
        if (diff < 0) {
            min = min - diff;  // Restore previous min
        }
    }
    
    // O(1) - decode value
    public int top() {
        long diff = stack.peek();
        if (diff < 0) {
            return (int)min;
        }
        return (int)(min + diff);
    }
    
    // O(1) - return current min
    public int getMin() {
        return (int)min;
    }
}
```

**Key Difference**: 
- Uses math trick to avoid second stack
- Stores differences, not absolute values
- Clever but harder to understand

**Complexity Analysis**
- **All operations**: O(1) — Single stack, arithmetic
- **Space**: O(n) — Single stack

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Space | Code Complexity | Robust | Recommended |
|----------|-------|-----------------|--------|-------------|
| **Two Stacks (Full Sync)** | **O(2n)** | **Simple ✅** | **Yes ✅** | **Yes ✅** |
| Two Stacks (Optimized) | O(n+k) | Medium | Needs care | For space |
| Single Stack (Pairs) | O(2n) | Simple | Yes | Alternative |
| Single Stack (Encoding) | O(n) | Complex | Tricky | Interviews only |

**Winner**: **Two Stacks (Full Synchronization)** — simple, robust, O(1) operations!

### Why Auxiliary Stack Works?

```
Key insight: Precompute minimums!

Instead of scanning for min every time:
  getMin() scans all elements → O(n) ❌

Precompute at each level:
  minStack[i] = min of elements [0...i]
  getMin() = minStack.top() → O(1) ✓

Example: [3, 5, 2, 7, 1]
  Index 0: min([3]) = 3
  Index 1: min([3,5]) = 3
  Index 2: min([3,5,2]) = 2
  Index 3: min([3,5,2,7]) = 2
  Index 4: min([3,5,2,7,1]) = 1
  
minStack: [3, 3, 2, 2, 1]
At any point, top of minStack = current minimum!
```

### Why Synchronize Both Stacks?

```
Option 1: Always synchronized (same size)
  push: Always push to both
  pop: Always pop from both
  
  Advantage: Simple, no special cases
  Disadvantage: Uses 2n space always

Option 2: Conditional push to minStack
  push: Only push to minStack if new min
  pop: Only pop from minStack if removing min
  
  Advantage: Uses less space (n + k)
  Disadvantage: Must compare on pop (tricky with objects!)

For interviews: Option 1 is safer!
```

### The <= vs < Gotcha

```
Critical: Must use <= when comparing!

Example showing why:
  push(5), push(5)
  
  Using < (WRONG):
    push(5): minStack = [5]
    push(5): 5 < 5? No → minStack = [5] (don't push)
    
    pop(): mainStack = [5], minStack = []
    pop(): minStack empty! getMin() fails! ❌
  
  Using <= (CORRECT):
    push(5): minStack = [5]
    push(5): 5 <= 5? Yes → minStack = [5, 5]
    
    pop(): mainStack = [5], minStack = [5]
    pop(): mainStack = [], minStack = []
    Both empty, consistent! ✓

Rule: Always use <= to handle duplicates!
```

---

## Critical Edge Cases & Gotchas

### 1. **Duplicate Minimums**
```java
push(2), push(2), push(2)
mainStack: [2, 2, 2]
minStack: [2, 2, 2]

pop() → mainStack: [2, 2], minStack: [2, 2]
getMin() → 2 ✓ (still correct)
```

### 2. **Minimum at Beginning**
```java
push(1), push(5), push(3)
mainStack: [1, 5, 3]
minStack: [1, 1, 1]

All getMin() calls return 1
```

### 3. **Minimum at End**
```java
push(5), push(3), push(1)
mainStack: [5, 3, 1]
minStack: [5, 3, 1]

getMin() evolves: 5 → 3 → 1
```

### 4. **Single Element**
```java
push(7)
mainStack: [7]
minStack: [7]

getMin() → 7
top() → 7
pop() → both empty
```

### 5. **Negative Numbers**
```java
push(-1), push(-2), push(0)
mainStack: [-1, -2, 0]
minStack: [-1, -2, -2]

getMin() → -2
```

### 6. **All Same Values**
```java
push(5), push(5), push(5)
mainStack: [5, 5, 5]
minStack: [5, 5, 5]

getMin() always returns 5
```

### 7. **Alternating Min Changes**
```java
push(1), push(2), push(0), push(3), push(-1)
minStack: [1, 1, 0, 0, -1]

Minimum changes: 1 → 0 → -1
```

### 8. **Pop Until Empty**
```java
push(1), push(2)
pop(), pop()
mainStack: []
minStack: []

Both empty, consistent!
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Using < Instead of <=**
```java
// WRONG - doesn't handle duplicates correctly
public void push(int val) {
    mainStack.push(val);
    if (minStack.isEmpty() || val < minStack.peek()) {  // WRONG! Should be <=
        minStack.push(val);
    }
}
```

**Why wrong**: Explained earlier with duplicate minimum example!

**Dry run failure for push(5), push(5), pop(), getMin():**
```
push(5): minStack = [5]
push(5): 5 < 5? No → minStack = [5] (wrong!)
pop(): mainStack = [5], need to pop from minStack?
  Can't tell! minStack has only one element
getMin() might return wrong value or fail
```

**Fix**: Use <=
```java
if (minStack.isEmpty() || val <= minStack.peek()) {
    minStack.push(val);
}
```

### ❌ **MISTAKE 2: Not Synchronizing Stacks**
```java
// WRONG - stacks get out of sync
public void pop() {
    mainStack.pop();
    // WRONG! Forgot to pop from minStack
}
```

**Why wrong**: Stacks have different sizes!

**Dry run failure:**
```
push(1), push(2), push(3)
mainStack: [1, 2, 3]
minStack: [1, 1, 1]

pop() without popping minStack:
mainStack: [1, 2]
minStack: [1, 1, 1] (still 3 elements!)

getMin() → 1 (correct by accident)
pop() again:
mainStack: [1]
minStack: [1, 1, 1]

Sizes don't match! Will cause issues!
```

**Fix**: Always pop from both
```java
public void pop() {
    mainStack.pop();
    minStack.pop();
}
```

### ❌ **MISTAKE 3: Wrong Comparison in Space-Optimized Version**
```java
// WRONG - uses == on Integer objects without consideration
public void pop() {
    Integer val = mainStack.pop();
    if (val == minStack.peek()) {  // WRONG! Object comparison
        minStack.pop();
    }
}
```

**Why wrong**: Integer caching only works for -128 to 127!

**Dry run failure for push(1000), push(1000), pop():**
```
push(1000): Integer object A
push(1000): Integer object B
Both push to minStack

pop(): val = object B
  val == minStack.peek() (object B)?
  Could be false if not cached! (1000 > 127)
  Don't pop from minStack ❌
  
minStack grows indefinitely!
```

**Fix**: Use .equals() or int primitive
```java
int val = mainStack.pop();  // Unbox to int
if (val == minStack.peek()) {  // Primitive comparison
    minStack.pop();
}
```

### ❌ **MISTAKE 4: Not Handling Empty Stack**
```java
// WRONG - doesn't check if stack empty
public int getMin() {
    return minStack.peek();  // What if empty?
}
```

**Why wrong**: Problem states operations called on non-empty stacks, but good practice to check!

**Fix**: Add check (defensive programming)
```java
public int getMin() {
    if (minStack.isEmpty()) {
        throw new IllegalStateException("Stack is empty");
    }
    return minStack.peek();
}
```

### ❌ **MISTAKE 5: Pushing Current Min Instead of Comparison**
```java
// WRONG - always pushes current minimum
public void push(int val) {
    mainStack.push(val);
    if (!minStack.isEmpty()) {
        minStack.push(minStack.peek());  // WRONG! Ignores val
    } else {
        minStack.push(val);
    }
}
```

**Why wrong**: Doesn't update minimum when new smaller value arrives!

**Dry run failure for push(5), push(3):**
```
push(5): minStack = [5]
push(3): minStack = [5, 5] ❌ (should be [5, 3]!)

getMin() → 5 ❌ (should be 3!)
```

**Fix**: Use Math.min()
```java
minStack.push(Math.min(val, minStack.peek()));
```

### ❌ **MISTAKE 6: Returning Wrong Stack for top()**
```java
// WRONG - returns from minStack
public int top() {
    return minStack.peek();  // WRONG! Should be mainStack
}
```

**Why wrong**: minStack stores minimums, not actual top!

**Dry run failure:**
```
push(5), push(3), push(7)
mainStack: [5, 3, 7]
minStack: [5, 3, 3]

top() using wrong code: 3 ❌
Should return: 7 ✓
```

**Fix**: Return from mainStack
```java
public int top() {
    return mainStack.peek();
}
```

### ❌ **MISTAKE 7: Not Initializing Stacks**
```java
// WRONG - stacks not initialized
class MinStack {
    private Stack<Integer> mainStack;
    private Stack<Integer> minStack;
    
    public MinStack() {
        // WRONG! Forgot to initialize
    }
}
```

**Fix**: Initialize in constructor
```java
public MinStack() {
    mainStack = new Stack<>();
    minStack = new Stack<>();
}
```

---

## Complexity Analysis

### Time Complexity: **O(1)** for all operations

| Operation | Time | Reason |
|-----------|------|--------|
| **push(val)** | **O(1)** | Two push operations (constant) |
| **pop()** | **O(1)** | Two pop operations (constant) |
| **top()** | **O(1)** | One peek operation |
| **getMin()** | **O(1)** | One peek operation |

**All operations truly O(1)**:
```
No loops, no recursion, no variable-time operations
Every operation: fixed number of stack operations
Each stack operation: O(1)
Total: O(1) ✓
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| mainStack | O(n) | Store n elements |
| minStack | O(n) | Store n minimums |
| Variables | O(1) | Temporary variables |
| **Total** | **O(n)** | Linear in number of elements |

**Space analysis**:
```
Full synchronization: 2n space
  mainStack: n elements
  minStack: n elements (one per main element)
  
Space-optimized: n + k space
  mainStack: n elements
  minStack: k elements (k = times minimum changes)
  
Worst case: k = n (every element is new minimum)
Average case: k << n (minimum changes rarely)
```

---

## Visualization

### Complete Example Walkthrough

**Operations:** `push(3), push(5), push(2), push(7), getMin(), pop(), pop(), getMin()`

---

**Step 1: push(3)**
```
Action:
  mainStack.push(3)
  minStack.isEmpty()? Yes
  minStack.push(3)

State:
  mainStack: [3]
  minStack: [3]

Explanation: First element, it's the minimum
```

---

**Step 2: push(5)**
```
Action:
  mainStack.push(5)
  min(5, 3) = 3
  minStack.push(3)

State:
  mainStack: [3, 5]
  minStack: [3, 3]

Explanation: 5 > 3, so minimum stays 3
```

---

**Step 3: push(2)**
```
Action:
  mainStack.push(2)
  min(2, 3) = 2
  minStack.push(2)

State:
  mainStack: [3, 5, 2]
  minStack: [3, 3, 2]

Explanation: New minimum! 2 < 3
```

---

**Step 4: push(7)**
```
Action:
  mainStack.push(7)
  min(7, 2) = 2
  minStack.push(2)

State:
  mainStack: [3, 5, 2, 7]
  minStack: [3, 3, 2, 2]

Explanation: 7 > 2, minimum stays 2
```

---

**Step 5: getMin()**
```
Action:
  minStack.peek() → 2

State:
  mainStack: [3, 5, 2, 7]
  minStack: [3, 3, 2, 2]
  Return: 2

Explanation: Current minimum is 2
```

---

**Step 6: pop()**
```
Action:
  mainStack.pop() → 7
  minStack.pop() → 2

State:
  mainStack: [3, 5, 2]
  minStack: [3, 3, 2]

Explanation: Remove 7 and its corresponding min (2)
```

---

**Step 7: pop()**
```
Action:
  mainStack.pop() → 2
  minStack.pop() → 2

State:
  mainStack: [3, 5]
  minStack: [3, 3]

Explanation: Remove 2 and its corresponding min (2)
```

---

**Step 8: getMin()**
```
Action:
  minStack.peek() → 3

State:
  mainStack: [3, 5]
  minStack: [3, 3]
  Return: 3

Explanation: After removing 2, minimum reverts to 3
```

---

### Visual State Diagram

```
Operation Sequence:

push(3):
  main: [3]    min: [3]    getMin() → 3

push(5):
  main: [3,5]  min: [3,3]  getMin() → 3 (stays 3)

push(2):
  main: [3,5,2]  min: [3,3,2]  getMin() → 2 (new min!)

push(7):
  main: [3,5,2,7]  min: [3,3,2,2]  getMin() → 2

pop():
  main: [3,5,2]  min: [3,3,2]  getMin() → 2

pop():
  main: [3,5]  min: [3,3]  getMin() → 3 (reverted!)

Synchronized stacks maintain minimum at each level!
```

### Minimum Tracking Visualization

```
Elements:     3    5    2    7
             ───  ───  ───  ───
mainStack:   │3│  │5│  │2│  │7│
             └─┘  └─┘  └─┘  └─┘

minStack:    │3│  │3│  │2│  │2│
             └─┘  └─┘  └─┘  └─┘
              ↑    ↑    ↑    ↑
             min  min  min  min
             at   at   at   at
             0    1    2    3

At each position i:
  minStack[i] = minimum of elements [0...i]
  
  minStack[0] = min(3) = 3
  minStack[1] = min(3,5) = 3
  minStack[2] = min(3,5,2) = 2
  minStack[3] = min(3,5,2,7) = 2

When popping:
  Remove from both stacks
  minStack.top() automatically gives previous minimum!
```

---

## Comparison of Approaches

| Approach | Space | Code Lines | Clarity | Edge Cases | Recommended |
|----------|-------|------------|---------|------------|-------------|
| **Two Stacks (Full)** | **O(2n)** | **~20** | **Excellent ✅** | **None ✅** | **Yes ✅** |
| Two Stacks (Optimized) | O(n+k) | ~25 | Good | Integer comparison | Space constrained |
| Single Stack (Pairs) | O(2n) | ~20 | Good | None | Alternative |
| Single Stack (Encoding) | O(n) | ~30 | Poor | Overflow, complex | Interviews only |

**Recommendation**: Use **Two Stacks (Full Synchronization)** — simple, robust, performant!

---

## Key Takeaways

1. **Auxiliary stack for O(1) minimum** — precompute at each level
2. **Full synchronization is simplest** — always push/pop both stacks
3. **Use <= not <** — handles duplicate minimums correctly
4. **minStack stores minimum UP TO that point** — prefix minimum
5. **Both stacks same size** — always synchronized
6. **O(1) all operations** — no scanning required
7. **O(n) space** — acceptable trade-off for O(1) queries

---

## Interview Tips

**What to say in an interview:**

> "The challenge is tracking the minimum element while maintaining O(1) for all operations including push and pop. If I scan the entire stack for the minimum each time, getMin() would be O(n). Instead, I'll use an auxiliary stack to track the minimum at each level of the main stack. When pushing a value, I also push the minimum seen so far onto the min stack — either the new value or the current minimum, whichever is smaller. When popping, I pop from both stacks to keep them synchronized. This way, the top of the min stack always contains the current minimum, making getMin() O(1). All operations remain O(1) because each involves a constant number of stack operations. The space complexity is O(n) for the two stacks, which is acceptable."

**Key points to mention:**
1. **Auxiliary stack pattern** — track extra information
2. **Precompute minimums** — at each stack level
3. **Synchronization** — both stacks same size
4. **<= for duplicates** — handle equal minimums correctly
5. **All O(1) operations** — no scanning needed

**If asked about alternatives:**
> "I could optimize space by only pushing to the min stack when the minimum changes, but this requires careful comparison during pop and can be tricky with Integer object comparison. I could also store pairs (value, min) in a single stack, which is cleaner but uses similar space. For a coding interview, the two fully-synchronized stacks approach is safest and clearest."

**Common Follow-ups:**
- "Can you optimize space?" → Yes, conditional push to minStack (saves space but trickier)
- "What about getMax()?" → Same approach with maxStack instead of minStack
- "Why not store minimum in variable?" → Doesn't work after pop (can't restore previous min)
- "Handle duplicates?" → Use <= not <, push duplicates to minStack

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Min Stack** | Medium | **Auxiliary Stack** | **This problem** |
| Max Stack | Hard | Auxiliary Stack + TreeMap | Support removing arbitrary elements |
| Stack With Increment Operation | Medium | Stack + Array | Lazy increment propagation |
| Design a Stack With Increment Operation | Medium | Stack Design | Bulk operations |
| Implement Queue using Stacks | Easy | Two Stacks | FIFO using LIFO |

**Pattern Progression**:
1. **Basic auxiliary tracking** (this problem) — Min Stack
2. **Advanced operations** (hard) — Max Stack with remove
3. **Bulk operations** (medium) — Increment operation
4. **Multiple properties** (harder) — Track multiple metrics

---

## Final Pattern Label

✅ **Auxiliary Stack / Parallel Data Structure (O(1) Min Tracking)**

**Remember:** Use two stacks — main stack stores all values, min stack stores minimum at each level. When pushing, push to main and push min(val, currentMin) to min stack. When popping, pop from both stacks to stay synchronized. Use <= not < to handle duplicate minimums correctly. The top of min stack always contains the current minimum, giving O(1) getMin() with O(n) space!
