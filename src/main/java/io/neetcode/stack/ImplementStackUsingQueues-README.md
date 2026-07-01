# Implement Stack Using Queues

## Problem Description

**Difficulty**: Easy

Implement a last-in-first-out (LIFO) stack using **only two queues**. The implemented stack should support all the functions of a normal stack (`push`, `top`, `pop`, and `empty`).

Implement the `MyStack` class:

- `void push(int x)` - Pushes element x to the top of the stack
- `int pop()` - Removes the element on the top of the stack and returns it
- `int top()` - Returns the element on the top of the stack
- `boolean empty()` - Returns true if the stack is empty, false otherwise

**Notes**:
- You must use **only standard operations of a queue**: push to back, peek/pop from front, size, and is empty
- Depending on your language, the queue may not be supported natively. You may simulate a queue using a list or deque as long as you use only a queue's standard operations

## Examples

### Example 1:
```
Input: 
["MyStack", "push", "push", "top", "pop", "empty"]
[[], [1], [2], [], [], []]

Output: 
[null, null, null, 2, 2, false]

Explanation:
MyStack myStack = new MyStack();
myStack.push(1);    // stack: [1]
myStack.push(2);    // stack: [1, 2]
myStack.top();      // return 2 (top element)
myStack.pop();      // return 2, stack: [1]
myStack.empty();    // return false (stack has 1 element)
```

### Example 2:
```
Input:
["MyStack", "push", "push", "push", "pop", "pop", "pop", "empty"]
[[], [1], [2], [3], [], [], [], []]

Output:
[null, null, null, null, 3, 2, 1, true]

Explanation:
Push 1, 2, 3 → stack: [1, 2, 3]
Pop → 3, stack: [1, 2]
Pop → 2, stack: [1]
Pop → 1, stack: []
empty → true
```

### Example 3:
```
Input:
["MyStack", "push", "top", "top", "pop", "empty"]
[[], [5], [], [], [], []]

Output:
[null, null, 5, 5, 5, true]

Explanation:
Push 5 → stack: [5]
top → 5 (doesn't remove)
top → 5 (still there)
pop → 5, stack: []
empty → true
```

## Constraints
- 1 <= x <= 9
- At most 100 calls will be made to push, pop, top, and empty
- All the calls to pop and top are valid

**Follow-up**: Can you implement the stack using only one queue?

---

## Pattern Recognition

**Primary Pattern**: **Queue Manipulation / Data Structure Design**

**Why This Pattern?**
- Need to simulate LIFO (stack) using FIFO (queue)
- Queue removes from front, stack removes from back
- Must reverse order using queue operations
- Design problem: implement one data structure using another

**Key Insight**: Reversing FIFO to LIFO
```
Problem: Implement stack (LIFO) using queue (FIFO)

Stack behavior: Last In, First Out
  push(1), push(2), push(3)
  pop() → 3 (last added)
  
Queue behavior: First In, First Out
  add(1), add(2), add(3)
  remove() → 1 (first added)
  
Challenge: Queue gives us OPPOSITE order!

Key insight: Rotate queue to bring newest element to front
  After push(x), rotate queue so x is at front
  Then pop/top can use queue's front (which is stack's top)
```

**Why Two Queues (or One)?**
```
Approach 1: Two Queues (Push Heavy)
  - q1: main queue
  - q2: temporary queue
  - On push: Add to q2, move all from q1 to q2, swap
  - On pop/top: Simply use q1.remove()/q1.peek()
  
Approach 2: One Queue (Push Heavy)
  - Single queue
  - On push: Add element, rotate queue (size-1) times
  - On pop/top: Simply use queue.remove()/queue.peek()
  
Approach 3: Two Queues (Pop Heavy)
  - q1: main queue
  - q2: temporary queue
  - On push: Simply add to q1
  - On pop: Move all but last to q2, remove last, swap
  
Trade-off: Make push expensive or pop expensive?
```

**The Rotation Strategy (One Queue - OPTIMAL)**:
```
Core idea: After adding element, rotate it to front

Example: push(1), push(2), push(3)

push(1):
  queue.add(1) → [1]
  size = 1, rotate 0 times
  Result: [1]

push(2):
  queue.add(2) → [1, 2]
  size = 2, rotate 1 time:
    remove() → 1
    add(1) → [2, 1]
  Result: [2, 1]  (2 is now at front!)

push(3):
  queue.add(3) → [2, 1, 3]
  size = 3, rotate 2 times:
    remove() → 2, add(2) → [1, 3, 2]
    remove() → 1, add(1) → [3, 2, 1]
  Result: [3, 2, 1]  (3 is now at front!)

pop():
  remove() → 3  (front of queue = top of stack!) ✓
  Result: [2, 1]

Insight: Newest element always at front of queue!
```

**Critical Detail**: Rotation Count
```
After adding element to queue of size n:
  Need to rotate (n - 1) times
  
Why (n - 1)?
  - Element just added is at position n (back)
  - Need to move it to position 1 (front)
  - Move (n - 1) elements from front to back
  
Example: [1, 2, 3, NEW]
  NEW at position 4
  Rotate 3 times:
    [2, 3, NEW, 1]
    [3, NEW, 1, 2]
    [NEW, 1, 2, 3]  ✓ NEW at front!
```

**Related Patterns**:
1. **Queue** — FIFO data structure
2. **Stack** — LIFO data structure
3. **Data Structure Design** — Implement one using another
4. **Rotation/Cyclic** — Move elements in circular fashion

---

## Algorithm & Approach

### Core Insight

**Why Naive Approach Fails:**
```
Naive: Just use queue as-is
  push(1), push(2), push(3) → queue = [1, 2, 3]
  pop() → remove() gives 1 ❌ (should give 3!)
  
Queue gives FIFO order, we need LIFO!
Must transform the order somehow.
```

**The Optimal Strategy**:
```
Key observations:
  1. Queue can only remove from front
  2. Stack must remove from back (most recent)
  3. Solution: Keep most recent element at front of queue!
  
How? After each push, rotate queue:
  - Add new element to back
  - Move all other elements from front to back
  - Result: New element now at front
  
Then pop/top just use queue's front!
```

### Step-by-Step Algorithm

---

#### **Approach 1: Single Queue with Rotation on Push (OPTIMAL)**

**Core Idea**:
- Use one queue
- After push, rotate queue to bring new element to front
- Pop/top simply use queue front (O(1))

**Algorithm**
```
class MyStack:
    queue = new Queue()
    
    push(x):
        queue.add(x)
        // Rotate queue (size - 1) times
        for i from 0 to queue.size() - 2:
            queue.add(queue.remove())
    
    pop():
        return queue.remove()  // Front = stack top
    
    top():
        return queue.peek()  // Front = stack top
    
    empty():
        return queue.isEmpty()
```

**Code Implementation**
```java
class MyStack {
    private Queue<Integer> queue;
    
    public MyStack() {
        queue = new LinkedList<>();
    }
    
    // O(n) - rotate queue after adding element
    public void push(int x) {
        queue.offer(x);
        
        // Rotate queue to bring new element to front
        int size = queue.size();
        for (int i = 0; i < size - 1; i++) {
            queue.offer(queue.poll());
        }
    }
    
    // O(1) - remove from front
    public int pop() {
        return queue.poll();
    }
    
    // O(1) - peek at front
    public int top() {
        return queue.peek();
    }
    
    // O(1) - check if empty
    public boolean empty() {
        return queue.isEmpty();
    }
}
```

**Example Walkthrough**

Operations: `push(1), push(2), push(3), pop(), top()`

| Operation | Action | Queue State | Explanation |
|-----------|--------|-------------|-------------|
| Init | - | [] | Empty queue |
| push(1) | add(1), rotate 0 times | [1] | First element, no rotation needed |
| push(2) | add(2), rotate 1 time | [2, 1] | Moved 1 to back, 2 at front |
| push(3) | add(3), rotate 2 times | [3, 2, 1] | Moved 2,1 to back, 3 at front |
| pop() | poll() | [2, 1] | Remove front (3) |
| top() | peek() | [2, 1] | Return front (2) without removing |

**Complexity Analysis**
- **push(x)**: O(n) — Rotate queue n-1 times
- **pop()**: O(1) — Remove from front
- **top()**: O(1) — Peek at front
- **empty()**: O(1) — Check size
- **Space**: O(n) — Store n elements

---

#### **Approach 2: Two Queues with Swap on Push**

**Core Idea**: Use two queues, keep newest at front by swapping.

**Code Implementation**
```java
class MyStack {
    private Queue<Integer> q1;
    private Queue<Integer> q2;
    
    public MyStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }
    
    // O(n) - transfer all elements to q2
    public void push(int x) {
        // Add new element to empty q2
        q2.offer(x);
        
        // Transfer all elements from q1 to q2
        while (!q1.isEmpty()) {
            q2.offer(q1.poll());
        }
        
        // Swap q1 and q2
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
    }
    
    // O(1) - remove from q1 front
    public int pop() {
        return q1.poll();
    }
    
    // O(1) - peek at q1 front
    public int top() {
        return q1.peek();
    }
    
    // O(1) - check q1 empty
    public boolean empty() {
        return q1.isEmpty();
    }
}
```

**Key Difference**: 
- Uses two queues instead of one
- Swap queues after each push
- Conceptually clearer but uses more space

**Complexity Analysis**
- **push(x)**: O(n) — Transfer all elements
- **pop()**: O(1) — Remove from front
- **top()**: O(1) — Peek at front
- **empty()**: O(1) — Check size
- **Space**: O(n) — Two queues

---

#### **Approach 3: Two Queues with Pop Heavy**

**Core Idea**: Make push O(1), but pop O(n).

**Code Implementation**
```java
class MyStack {
    private Queue<Integer> q1;
    private Queue<Integer> q2;
    private int topElement;
    
    public MyStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }
    
    // O(1) - just add to q1
    public void push(int x) {
        q1.offer(x);
        topElement = x;
    }
    
    // O(n) - move all but last to q2
    public int pop() {
        // Move all but last element to q2
        while (q1.size() > 1) {
            topElement = q1.poll();
            q2.offer(topElement);
        }
        
        // Remove and return last element
        int result = q1.poll();
        
        // Swap q1 and q2
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
        
        return result;
    }
    
    // O(1) - return cached top
    public int top() {
        return topElement;
    }
    
    // O(1) - check q1 empty
    public boolean empty() {
        return q1.isEmpty();
    }
}
```

**Key Difference**: 
- Push is O(1) (fast)
- Pop is O(n) (slow)
- Good if push is more frequent than pop

**Complexity Analysis**
- **push(x)**: O(1) — Just add to queue
- **pop()**: O(n) — Transfer n-1 elements
- **top()**: O(1) — Return cached value
- **empty()**: O(1) — Check size
- **Space**: O(n) — Two queues

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | push | pop | top | Space | Best For |
|----------|------|-----|-----|-------|----------|
| **One Queue (Rotate)** | **O(n)** | **O(1)** | **O(1)** | **O(n) ✅** | **Frequent pop/top** |
| Two Queues (Swap) | O(n) | O(1) | O(1) | O(2n) | Same as above |
| Two Queues (Pop Heavy) | O(1) | O(n) | O(1) | O(2n) | Frequent push |

**Winner**: **Single Queue with Rotation** — optimal space, clean code!

### Why Rotation Works?

```
Goal: Keep most recent element at front

Without rotation:
  push(1), push(2), push(3)
  queue = [1, 2, 3]
  poll() gives 1 ❌ (want 3!)

With rotation after each push:
  push(1): [1] (no rotation needed)
  push(2): [1, 2] → rotate 1 time → [2, 1]
  push(3): [2, 1, 3] → rotate 2 times → [3, 2, 1]
  poll() gives 3 ✓ (correct!)

Key: Newest always at front of queue = top of stack!
```

### Why (size - 1) Rotations?

```
After adding element:
  queue = [old elements..., NEW]
  NEW is at position 'size' (back)
  Want NEW at position 1 (front)
  
Rotate once: Move front to back
  [1, 2, 3, NEW] → [2, 3, NEW, 1]
  
Rotate twice:
  [2, 3, NEW, 1] → [3, NEW, 1, 2]
  
Rotate (size-1) times:
  [3, NEW, 1, 2] → [NEW, 1, 2, 3] ✓

Need exactly (size - 1) rotations to move NEW to front!
```

### Trade-off: Push vs Pop Complexity

```
Option 1: O(n) push, O(1) pop
  Good when: pop/top called frequently
  Bad when: push called frequently
  
Option 2: O(1) push, O(n) pop
  Good when: push called frequently
  Bad when: pop/top called frequently

Most real-world scenarios:
  - Pop/top called more often than push
  - Or at least same frequency
  → Make pop/top fast (O(1))
  → Accept O(n) push
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Element**
```java
push(5)
Queue: [5]
pop() → 5
Queue: []
empty() → true
```

### 2. **Multiple Top Calls**
```java
push(1), push(2)
Queue: [2, 1]
top() → 2 (doesn't remove)
top() → 2 (still there)
top() → 2 (still there)
pop() → 2
Queue: [1]
```

### 3. **Alternating Push/Pop**
```java
push(1) → [1]
push(2) → [2, 1]
pop() → 2, [1]
push(3) → [3, 1]
pop() → 3, [1]
pop() → 1, []
```

### 4. **Push Same Values**
```java
push(5), push(5), push(5)
Queue: [5, 5, 5]
pop() → 5
pop() → 5
pop() → 5
```

### 5. **Rotation with Size 1**
```java
push(1):
  add(1) → [1]
  size = 1
  rotate (1-1) = 0 times
  Result: [1] (no rotation needed) ✓
```

### 6. **Empty Queue Operations**
```java
MyStack stack = new MyStack();
empty() → true
// pop() and top() should not be called (per constraints)
```

### 7. **Maximum Rotations**
```java
push(1), push(2), ..., push(100)
Last push(100):
  add(100) → [...99 elements..., 100]
  rotate 99 times to bring 100 to front
  O(n) operation but acceptable
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Wrong Number of Rotations**
```java
// WRONG - rotates 'size' times instead of 'size - 1'
public void push(int x) {
    queue.offer(x);
    int size = queue.size();
    for (int i = 0; i < size; i++) {  // WRONG! Should be size - 1
        queue.offer(queue.poll());
    }
}
```

**Why wrong**: Rotates one extra time, element ends up at back again!

**Dry run failure for push(1), push(2):**
```
push(1):
  add(1) → [1]
  rotate 1 time: [1] → [1] (back to original, OK)

push(2):
  add(2) → [1, 2]
  rotate 2 times:
    [1, 2] → [2, 1]
    [2, 1] → [1, 2] (back to original! ❌)
  
  Result: [1, 2]
  pop() gives 1 (should give 2!)
```

**Fix**: Rotate (size - 1) times
```java
for (int i = 0; i < size - 1; i++) {
    queue.offer(queue.poll());
}
```

### ❌ **MISTAKE 2: Rotating Before Adding**
```java
// WRONG - rotates before adding element
public void push(int x) {
    int size = queue.size();
    for (int i = 0; i < size - 1; i++) {  // WRONG! Element not added yet
        queue.offer(queue.poll());
    }
    queue.offer(x);
}
```

**Why wrong**: Rotates wrong number of elements (size is old size)!

**Dry run failure:**
```
push(1): size=0, rotate 0 times, add(1) → [1] ✓ (accidentally OK)
push(2): size=1, rotate 0 times, add(2) → [1, 2] ❌
  Should rotate 1 time to get [2, 1]!
```

**Fix**: Add first, then rotate
```java
queue.offer(x);
int size = queue.size();  // Now includes new element
for (int i = 0; i < size - 1; i++) {
    queue.offer(queue.poll());
}
```

### ❌ **MISTAKE 3: Using add() Instead of offer()**
```java
// WRONG - uses add() which may throw exception
public void push(int x) {
    queue.add(x);  // Could throw exception if queue full
}
```

**Why wrong**: `add()` throws exception if queue capacity limit reached!

**Fix**: Use `offer()` (returns false instead of throwing)
```java
queue.offer(x);  // Safe, returns false if can't add
```

### ❌ **MISTAKE 4: Not Handling Empty in pop()**
```java
// WRONG - doesn't check if queue empty
public int pop() {
    return queue.poll();  // Returns null if empty!
}
```

**Why wrong**: Returns null (boxed Integer) instead of primitive int!

**Fix**: Problem guarantees valid calls, but could add check
```java
public int pop() {
    if (queue.isEmpty()) {
        throw new IllegalStateException("Stack is empty");
    }
    return queue.poll();
}
```

### ❌ **MISTAKE 5: Using remove() Without poll()**
```java
// WRONG - remove() throws exception if empty
public int pop() {
    return queue.remove();  // Throws NoSuchElementException if empty
}
```

**Why wrong**: Less safe than poll() which returns null!

**Fix**: Use poll() (safer)
```java
public int pop() {
    return queue.poll();  // Returns null if empty (or check first)
}
```

### ❌ **MISTAKE 6: Forgetting to Initialize Queue**
```java
// WRONG - queue not initialized
class MyStack {
    private Queue<Integer> queue;  // null!
    
    public MyStack() {
        // WRONG! Forgot to initialize
    }
    
    public void push(int x) {
        queue.offer(x);  // NullPointerException!
    }
}
```

**Fix**: Initialize in constructor
```java
public MyStack() {
    queue = new LinkedList<>();
}
```

### ❌ **MISTAKE 7: Using Deque Methods Instead of Queue**
```java
// WRONG - uses addFirst() which is not standard queue operation
public void push(int x) {
    queue.addFirst(x);  // WRONG! Not a queue operation
}
```

**Why wrong**: Problem requires ONLY standard queue operations!

**Allowed operations**:
- add to back: offer()
- remove from front: poll()
- peek front: peek()
- size: size()
- check empty: isEmpty()

**Fix**: Use only standard queue operations
```java
queue.offer(x);  // Add to back (standard)
```

---

## Complexity Analysis

### Time Complexity

| Operation | Single Queue | Two Queues (Push Heavy) | Two Queues (Pop Heavy) |
|-----------|--------------|-------------------------|------------------------|
| **push(x)** | **O(n)** | O(n) | O(1) |
| **pop()** | **O(1)** | O(1) | O(n) |
| **top()** | **O(1)** | O(1) | O(1) |
| **empty()** | **O(1)** | O(1) | O(1) |

**Single Queue Analysis**:
```
push(x): O(n)
  - offer(): O(1)
  - Rotate (n-1) times: O(n-1) ≈ O(n)
  - Each rotation: poll() + offer() = O(1) + O(1) = O(1)
  - Total: O(1) + O(n) = O(n)

pop(): O(1)
  - poll(): O(1)

top(): O(1)
  - peek(): O(1)

empty(): O(1)
  - isEmpty(): O(1)
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Queue | O(n) | Store n elements |
| Variables | O(1) | size, temp |
| **Total** | **O(n)** | Linear in number of elements |

**Comparison**:
```
Single Queue: O(n) space
  One queue storing n elements

Two Queues: O(n) space
  At most n elements total across both queues
  (Elements not duplicated, just moved between queues)
```

---

## Visualization

### Complete Example Walkthrough

**Operations:** `push(1), push(2), push(3), pop(), top(), empty()`

---

**Step 1: Initialize**
```
MyStack stack = new MyStack();
queue = []
```

---

**Step 2: push(1)**
```
Action:
  1. offer(1) → queue = [1]
  2. size = 1
  3. Rotate (1-1) = 0 times
  
Result: queue = [1]

Explanation: First element, no rotation needed
```

---

**Step 3: push(2)**
```
Action:
  1. offer(2) → queue = [1, 2]
  2. size = 2
  3. Rotate (2-1) = 1 time:
     - poll() → 1, offer(1) → queue = [2, 1]
  
Result: queue = [2, 1]

Explanation: 
  - 2 is now at front (stack top)
  - 1 moved to back
```

---

**Step 4: push(3)**
```
Action:
  1. offer(3) → queue = [2, 1, 3]
  2. size = 3
  3. Rotate (3-1) = 2 times:
     - poll() → 2, offer(2) → queue = [1, 3, 2]
     - poll() → 1, offer(1) → queue = [3, 2, 1]
  
Result: queue = [3, 2, 1]

Explanation:
  - 3 is now at front (stack top)
  - 2, 1 moved to back in order
```

---

**Step 5: pop()**
```
Action:
  poll() → 3

Result: 
  - Return: 3
  - queue = [2, 1]

Explanation: Remove front (stack top)
```

---

**Step 6: top()**
```
Action:
  peek() → 2

Result:
  - Return: 2
  - queue = [2, 1] (unchanged)

Explanation: View front without removing
```

---

**Step 7: empty()**
```
Action:
  isEmpty() → false

Result: false

Explanation: Queue has 2 elements, not empty
```

---

### Visual State Diagram

```
Operation Flow:

push(1):
  [] → offer(1) → [1]
  
push(2):
  [1] → offer(2) → [1, 2]
      → rotate → [2, 1]
  
push(3):
  [2, 1] → offer(3) → [2, 1, 3]
         → rotate → [1, 3, 2]
         → rotate → [3, 2, 1]

pop():
  [3, 2, 1] → poll() → [2, 1]
  Return: 3

top():
  [2, 1] → peek() → [2, 1]
  Return: 2

Stack Equivalence:
  Queue [3, 2, 1] ↔ Stack (bottom) 1, 2, 3 (top)
  Front of queue = Top of stack
```

### Rotation Visualization

```
push(3) into [2, 1]:

Step 1: Add to back
  [2, 1] + 3 = [2, 1, 3]
            ↑
          front

Step 2: Rotate once
  poll(2) + offer(2)
  [2, 1, 3] → [1, 3, 2]
   ↑           ↑
  remove     front

Step 3: Rotate twice
  poll(1) + offer(1)
  [1, 3, 2] → [3, 2, 1]
   ↑           ↑
  remove     front

Result: [3, 2, 1]
         ↑
        3 is now at front (stack top!)
```

---

## Comparison of Approaches

| Approach | push | pop | top | Space | Code Complexity |
|----------|------|-----|-----|-------|-----------------|
| **Single Queue** | **O(n)** | **O(1)** | **O(1)** | **O(n) ✅** | **Simple ✅** |
| Two Queues (Swap) | O(n) | O(1) | O(1) | O(n) | Medium |
| Two Queues (Pop Heavy) | O(1) | O(n) | O(1) | O(n) | Complex |

**Recommendation**: Use **Single Queue with Rotation** — optimal space, simplest code!

---

## Key Takeaways

1. **Rotate after push** — bring newest element to front
2. **Rotate (n-1) times** — not n times
3. **Add before rotating** — get correct size
4. **Front = stack top** — queue front is most recent element
5. **Use offer/poll** — safer than add/remove
6. **O(n) push, O(1) pop** — trade-off for frequent access
7. **Single queue sufficient** — don't need two queues

---

## Interview Tips

**What to say in an interview:**

> "The challenge is implementing LIFO (stack) using FIFO (queue). Queues remove from the front, but stacks remove from the back. My approach is to maintain an invariant: the most recent element is always at the front of the queue. After pushing a new element, I rotate the queue by removing elements from the front and adding them to the back, doing this (size - 1) times. This brings the newly added element to the front. Now pop and top operations simply use the queue's front, which represents the stack's top. Push becomes O(n) due to rotation, but pop and top are O(1). This is optimal for scenarios where access operations are more frequent than modifications. I can implement this with a single queue, making it space-efficient at O(n)."

**Key points to mention:**
1. **LIFO vs FIFO** — opposite ordering requirements
2. **Rotation strategy** — move newest to front
3. **Invariant** — queue front = stack top
4. **Complexity trade-off** — O(n) push for O(1) pop/top
5. **Single queue** — no need for two queues

**If asked about alternatives:**
> "I could use two queues where I transfer all elements to a temporary queue on each push, which has the same O(n) complexity but uses slightly more space due to two queue references. Alternatively, I could make push O(1) and pop O(n) by keeping elements in FIFO order and transferring all but the last element during pop. The choice depends on the access pattern — if pop/top are called more frequently than push, the rotation-on-push approach is better."

**Common Follow-ups:**
- "Can you do it with one queue?" → Yes, that's the optimal approach
- "What if push is more frequent?" → Consider pop-heavy approach (O(1) push, O(n) pop)
- "Can you do better than O(n) push?" → No, must reorder n-1 elements
- "Implement queue using stacks?" → Related problem, opposite direction

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Implement Stack Using Queues** | Easy | **Queue Manipulation** | **This problem** |
| Implement Queue Using Stacks | Easy | Stack Manipulation | Opposite: FIFO using LIFO |
| Min Stack | Medium | Stack + Auxiliary | Track minimum efficiently |
| Max Stack | Medium | Stack + TreeMap | Track maximum with remove |
| Design Circular Queue | Medium | Array + Pointers | Fixed-size circular buffer |

**Pattern Progression**:
1. **Basic transformation** (this problem) — Stack using queues
2. **Reverse transformation** (easy) — Queue using stacks
3. **Enhanced structures** (medium) — Min/Max stack with extra features

---

## Final Pattern Label

✅ **Queue Manipulation / Data Structure Design (LIFO using FIFO)**

**Remember:** Keep the most recent element at the front of the queue by rotating after each push. After adding an element, perform (size - 1) rotations: remove from front and add to back. This ensures the queue's front represents the stack's top. Push is O(n) due to rotation, but pop and top are O(1). A single queue is sufficient — no need for two queues!
