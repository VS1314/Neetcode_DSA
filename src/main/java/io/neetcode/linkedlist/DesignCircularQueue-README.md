# Design Circular Queue

## Problem Description

**Difficulty**: Medium

Design and implement a **circular queue**. The circular queue is a linear data structure in which the operations are performed based on **FIFO (First In First Out)** principle, and the **last position is connected back to the first position** to make a circle. It is also called a "**Ring Buffer**".

One of the benefits of the circular queue is that we can **make use of the spaces in front of the queue**. In a normal queue, once the queue becomes full, we cannot insert the next element even if there is a space in front of the queue. But using the circular queue, we can use the space to store new values.

**Implement the `MyCircularQueue` class:**

- `MyCircularQueue(k)` Initializes the object with the size of the queue to be `k`.
- `int Front()` Gets the front item from the queue. If the queue is empty, return `-1`.
- `int Rear()` Gets the last item from the queue. If the queue is empty, return `-1`.
- `boolean enQueue(int value)` Inserts an element into the circular queue. Return `true` if the operation is successful.
- `boolean deQueue()` Deletes an element from the circular queue. Return `true` if the operation is successful.
- `boolean isEmpty()` Checks whether the circular queue is empty or not.
- `boolean isFull()` Checks whether the circular queue is full or not.

**Important Constraint:**
You must solve the problem **without using the built-in queue data structure** in your programming language.

**Key Concepts:**
- **Circular/Ring Buffer**: Last position wraps to first
- **Fixed Capacity**: Size k determined at initialization
- **FIFO**: First In, First Out ordering
- **Space Reuse**: Can reuse dequeued positions at front

**Visual Representation:**
```
Normal Queue (wasteful):
  [_, _, 3, 4, 5] (front at index 2)
  Can't add more even though indices 0, 1 are free!

Circular Queue (efficient):
  [4, 5, 3, _, _] (rear wraps to front)
  Can reuse positions 3, 4 for new elements!
```

---

## Examples

### Example 1 (Main Example):
```
Input: 
["MyCircularQueue", "enQueue", "enQueue", "enQueue", "enQueue", "Rear", "isFull", "deQueue", "enQueue", "Rear"]
[[3], [1], [2], [3], [4], [], [], [], [4], []]

Output: 
[null, true, true, true, false, 3, true, true, true, 4]

Explanation:
MyCircularQueue queue = new MyCircularQueue(3);
queue.enQueue(1);  // [1] → true
queue.enQueue(2);  // [1, 2] → true
queue.enQueue(3);  // [1, 2, 3] → true (full)
queue.enQueue(4);  // [1, 2, 3] → false (queue full, cannot add)
queue.Rear();      // → 3 (last element)
queue.isFull();    // → true
queue.deQueue();   // [2, 3] → true (removed 1)
queue.enQueue(4);  // [2, 3, 4] → true (now space available)
queue.Rear();      // → 4 (last element)
```

### Example 2 (Empty Queue Operations):
```
Input:
["MyCircularQueue", "Front", "Rear", "deQueue", "isEmpty"]
[[3], [], [], [], []]

Output:
[null, -1, -1, false, true]

Explanation:
MyCircularQueue queue = new MyCircularQueue(3);
queue.Front();     // → -1 (empty queue)
queue.Rear();      // → -1 (empty queue)
queue.deQueue();   // → false (nothing to dequeue)
queue.isEmpty();   // → true
```

### Example 3 (Single Element):
```
Input:
["MyCircularQueue", "enQueue", "Front", "Rear", "deQueue", "isEmpty"]
[[1], [5], [], [], [], []]

Output:
[null, true, 5, 5, true, true]

Explanation:
MyCircularQueue queue = new MyCircularQueue(1);
queue.enQueue(5);  // [5] → true
queue.Front();     // → 5
queue.Rear();      // → 5 (same for single element)
queue.deQueue();   // [] → true
queue.isEmpty();   // → true
```

### Example 4 (Wrapping Around):
```
Input:
["MyCircularQueue", "enQueue", "enQueue", "deQueue", "enQueue", "deQueue", "enQueue"]
[[2], [1], [2], [], [3], [], [4]]

Output:
[null, true, true, true, true, true, true]

Explanation:
MyCircularQueue queue = new MyCircularQueue(2);
queue.enQueue(1);  // [1, _] front=0, rear=0
queue.enQueue(2);  // [1, 2] front=0, rear=1 (full)
queue.deQueue();   // [_, 2] front=1, rear=1
queue.enQueue(3);  // [3, 2] front=1, rear=0 (wrapped!)
queue.deQueue();   // [3, _] front=0, rear=0
queue.enQueue(4);  // [3, 4] front=0, rear=1
```

### Example 5 (Fill and Empty Repeatedly):
```
Input:
["MyCircularQueue", "enQueue", "enQueue", "deQueue", "deQueue", "isEmpty", "enQueue"]
[[2], [1], [2], [], [], [], [3]]

Output:
[null, true, true, true, true, true, true]

Explanation:
Fill queue, empty it, then fill again
Demonstrates space reuse
```

### Example 6 (Large Capacity):
```
Input:
["MyCircularQueue", "enQueue", "enQueue", "enQueue", "isFull"]
[[100], [1], [2], [3], []]

Output:
[null, true, true, true, false]

Explanation:
Large capacity (100)
Only 3 elements added
Not full yet
```

### Example 7 (All Operations):
```
Input:
["MyCircularQueue", "enQueue", "Front", "Rear", "deQueue", "Front", "enQueue", "Rear"]
[[3], [5], [], [], [], [], [10], []]

Output:
[null, true, 5, 5, true, -1, true, 10]

Explanation:
Tests all operations in sequence
Front returns -1 after deQueue makes it empty
```

### Example 8 (Boundary Testing):
```
Input:
["MyCircularQueue", "enQueue", "enQueue", "enQueue", "deQueue", "deQueue", "deQueue", "isEmpty"]
[[3], [1], [2], [3], [], [], [], []]

Output:
[null, true, true, true, true, true, true, true]

Explanation:
Fill completely, then empty completely
Final state is empty
```

### Example 9 (Alternate Enqueue/Dequeue):
```
Input:
["MyCircularQueue", "enQueue", "deQueue", "enQueue", "deQueue", "enQueue"]
[[2], [1], [], [2], [], [3]]

Output:
[null, true, true, true, true, true]

Explanation:
Alternating operations
Never fills up completely
```

### Example 10 (Capacity 1):
```
Input:
["MyCircularQueue", "enQueue", "isFull", "deQueue", "enQueue", "Front", "Rear"]
[[1], [7], [], [], [9], [], []]

Output:
[null, true, true, true, true, 9, 9]

Explanation:
Minimum practical capacity
Front and Rear always return same value when not empty
```

## Constraints
- `1 <= k <= 1000` (capacity)
- `0 <= value <= 1000` (element values)
- At most `3000` calls will be made to `enQueue`, `deQueue`, `Front`, `Rear`, `isEmpty`, and `isFull`
- Must implement **without built-in queue data structure**

**Recommended Complexity**: 
- Time: O(1) for all operations
- Space: O(k) for storing queue elements

---

## Pattern Recognition

**Primary Pattern**: **Circular Buffer with Modulo Arithmetic**

**Why This Pattern?**
- Need **fixed-size** data structure
- **FIFO** ordering required
- Must **reuse space** at front after dequeuing
- **Circular wrapping** when reaching end of array
- All operations must be **O(1)** time

**Key Insight**: Array + Two Pointers + Modulo
```
Challenge: How to wrap around?

Linear Array (wasteful):
  [_, _, 3, 4, 5] (dequeued 1, 2)
  Indices 0, 1 wasted!
  Can't add more even though space exists ❌

Circular Array (efficient):
  View array as circular
  Use modulo to wrap indices
  rear = (rear + 1) % capacity
  
  [4, 5, 3, _, _]
   ↑     ↑
  rear  front
  
  Space reused! ✓
```

**Why Modulo Arithmetic Works**:
```
Array size = 5 (indices 0-4)

Moving forward:
  index = 4, next = (4 + 1) % 5 = 0 ✓ (wraps!)
  index = 3, next = (3 + 1) % 5 = 4
  index = 0, next = (0 + 1) % 5 = 1

Modulo automatically wraps around! ✓
```

**The Empty vs Full Challenge**:
```
Problem: How to distinguish empty from full?

Scenario:
  Empty: front = 0, rear = 0
  Full:  front = 0, rear = 0 (after wrapping)
  
  Same pointer values! ❌

Solution 1: Track count/size
  count = 0 → empty
  count = capacity → full
  Easy to implement! ✓

Solution 2: Sacrifice one slot
  Keep one slot always empty
  Full when (rear + 1) % capacity == front
  Wastes one slot ❌

Solution 3: Use flag
  boolean flag to track last operation
  Complex ❌

Best: Solution 1 (track count) ✓
```

**Core Operations**:

1. **enQueue(value)**:
```
Check if full:
  if count == capacity:
    return false

Add element:
  queue[rear] = value
  rear = (rear + 1) % capacity
  count++
  return true
```

2. **deQueue()**:
```
Check if empty:
  if count == 0:
    return false

Remove element:
  front = (front + 1) % capacity
  count--
  return true
```

3. **Front()**:
```
if isEmpty():
  return -1
return queue[front]
```

4. **Rear()**:
```
if isEmpty():
  return -1
return queue[(rear - 1 + capacity) % capacity]
```

5. **isEmpty()**:
```
return count == 0
```

6. **isFull()**:
```
return count == capacity
```

**Why Track Count/Size**:
```
Benefits:
  1. Easy to check empty (count == 0)
  2. Easy to check full (count == capacity)
  3. No ambiguity
  4. Simple logic
  
Alternative (sacrifice slot):
  - More complex
  - Wastes one slot
  - Harder to reason about
  
Tracking count is cleaner! ✓
```

**Visual: Circular Queue in Action**
```
Capacity = 5, using indices 0-4

Initial: empty
  [_, _, _, _, _]
  front = 0, rear = 0, count = 0

enQueue(1):
  [1, _, _, _, _]
   ↑
  front/rear = 0, count = 1

enQueue(2):
  [1, 2, _, _, _]
   ↑  ↑
  front rear, count = 2

enQueue(3), enQueue(4), enQueue(5):
  [1, 2, 3, 4, 5]
   ↑           ↑
  front       rear (wraps to 0)
  count = 5 (full!)

deQueue():
  [_, 2, 3, 4, 5]
      ↑        ↑
    front     rear
  count = 4

enQueue(6):
  [6, 2, 3, 4, 5]
   ↑  ↑
  rear front
  count = 5 (full again!)
  
Circular wrapping! ✓
```

**Rear Index Calculation**:
```
Problem: Rear points to NEXT available slot

To get last element:
  Last element is at (rear - 1)
  But rear might be 0!
  
  (rear - 1) when rear = 0 gives -1 ❌
  
Solution: Add capacity before modulo
  (rear - 1 + capacity) % capacity
  
  If rear = 0:
    (0 - 1 + 5) % 5 = 4 ✓
    
  If rear = 3:
    (3 - 1 + 5) % 5 = 2 ✓
    
Always works! ✓
```

**Alternative Implementations**:

1. **Linked List**:
```
Could use circular linked list
  Tail.next = head
  
Pros:
  - Dynamic size (but problem requires fixed k)
  - No modulo needed
  
Cons:
  - More complex
  - Extra space for pointers
  - Not required here
  
Array is simpler for fixed size! ✓
```

2. **Sacrifice One Slot**:
```
Keep one slot always empty
  Empty: front == rear
  Full: (rear + 1) % capacity == front
  
Pros:
  - No count variable needed
  
Cons:
  - Wastes one slot
  - Can only store (k - 1) elements ❌
  - More complex logic
  
Not recommended!
```

**Related Patterns**:
1. **Ring Buffer** — Same as circular queue
2. **Circular Array** — General circular indexing
3. **Modulo Arithmetic** — Wrapping around

---

## Algorithm & Approach

### Core Insight

**Why Array + Count Works:**
```
Key observations:
  1. Fixed capacity k → use array of size k
  2. FIFO → track front and rear pointers
  3. Circular → use modulo for wrapping
  4. Empty vs Full → track count/size
  5. All operations O(1) with direct array access
```

**The Optimal Strategy**:
```
Data structure:
  - int[] queue of size k
  - int front (points to first element)
  - int rear (points to next available slot)
  - int count (current number of elements)
  - int capacity (maximum size k)

Operations all O(1):
  - Direct array access
  - Simple arithmetic
  - No loops needed
```

### Step-by-Step Algorithm

---

#### **Approach: Array with Count Tracking - OPTIMAL**

**Core Idea**:
- Use fixed-size array
- Track front, rear, and count
- Use modulo for circular wrapping
- Count distinguishes empty from full
- All operations O(1)

**Data Structure**
```java
class MyCircularQueue {
    private int[] queue;
    private int front;
    private int rear;
    private int count;
    private int capacity;
}
```

**Constructor**
```java
MyCircularQueue(int k):
    queue = new int[k]
    front = 0
    rear = 0
    count = 0
    capacity = k
```

**enQueue(value)**
```java
enQueue(int value):
    if count == capacity:
        return false  // Full
    
    queue[rear] = value
    rear = (rear + 1) % capacity
    count++
    return true
```

**deQueue()**
```java
deQueue():
    if count == 0:
        return false  // Empty
    
    front = (front + 1) % capacity
    count--
    return true
```

**Front()**
```java
Front():
    if count == 0:
        return -1  // Empty
    return queue[front]
```

**Rear()**
```java
Rear():
    if count == 0:
        return -1  // Empty
    return queue[(rear - 1 + capacity) % capacity]
```

**isEmpty()**
```java
isEmpty():
    return count == 0
```

**isFull()**
```java
isFull():
    return count == capacity
```

**Complete Code Implementation**
```java
class MyCircularQueue {
    private int[] queue;
    private int front;
    private int rear;
    private int count;
    private int capacity;

    public MyCircularQueue(int k) {
        queue = new int[k];
        front = 0;
        rear = 0;
        count = 0;
        capacity = k;
    }
    
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        queue[rear] = value;
        rear = (rear + 1) % capacity;
        count++;
        return true;
    }
    
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        front = (front + 1) % capacity;
        count--;
        return true;
    }
    
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return queue[front];
    }
    
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        // Rear points to next available, so last element is at rear - 1
        return queue[(rear - 1 + capacity) % capacity];
    }
    
    public boolean isEmpty() {
        return count == 0;
    }
    
    public boolean isFull() {
        return count == capacity;
    }
}
```

**Example Walkthrough**

Input: `k = 3`, operations: enQueue(1), enQueue(2), enQueue(3), enQueue(4), Rear(), isFull()

```
Initialize:
  queue = [0, 0, 0] (size 3)
  front = 0, rear = 0, count = 0, capacity = 3
```

**Operation 1: enQueue(1)**
```
Check: count (0) == capacity (3)? No → not full

queue[rear] = queue[0] = 1
  queue = [1, 0, 0]

rear = (0 + 1) % 3 = 1
count = 0 + 1 = 1

State:
  queue = [1, _, _]
  front = 0, rear = 1, count = 1
  Return: true ✓
```

**Operation 2: enQueue(2)**
```
Check: count (1) == capacity (3)? No → not full

queue[rear] = queue[1] = 2
  queue = [1, 2, 0]

rear = (1 + 1) % 3 = 2
count = 1 + 1 = 2

State:
  queue = [1, 2, _]
  front = 0, rear = 2, count = 2
  Return: true ✓
```

**Operation 3: enQueue(3)**
```
Check: count (2) == capacity (3)? No → not full

queue[rear] = queue[2] = 3
  queue = [1, 2, 3]

rear = (2 + 1) % 3 = 0 (wrapped!)
count = 2 + 1 = 3

State:
  queue = [1, 2, 3]
  front = 0, rear = 0, count = 3 (full!)
  Return: true ✓
```

**Operation 4: enQueue(4)**
```
Check: count (3) == capacity (3)? Yes → FULL!

Return: false ✓
(queue unchanged)
```

**Operation 5: Rear()**
```
Check: count (3) == 0? No → not empty

Calculate rear index:
  (rear - 1 + capacity) % capacity
  = (0 - 1 + 3) % 3
  = 2 % 3
  = 2

Return: queue[2] = 3 ✓
```

**Operation 6: isFull()**
```
Check: count (3) == capacity (3)? Yes

Return: true ✓
```

**Complexity Analysis**
- **Constructor**: O(k) — Initialize array
- **All Operations**: O(1) — Direct array access, simple arithmetic

---

**Example with Wrapping**

Continue from above: deQueue(), enQueue(4), Rear()

**Operation 7: deQueue()**
```
Check: count (3) == 0? No → not empty

front = (0 + 1) % 3 = 1
count = 3 - 1 = 2

State:
  queue = [1, 2, 3] (1 is "removed" but still in array)
  front = 1, rear = 0, count = 2
  Logical: [2, 3]
  Return: true ✓
```

**Operation 8: enQueue(4)**
```
Check: count (2) == capacity (3)? No → not full

queue[rear] = queue[0] = 4
  queue = [4, 2, 3]

rear = (0 + 1) % 3 = 1
count = 2 + 1 = 3

State:
  queue = [4, 2, 3]
  front = 1, rear = 1, count = 3 (full again!)
  Logical: [2, 3, 4] (circular!)
  Return: true ✓
```

**Operation 9: Rear()**
```
Check: count (3) == 0? No → not empty

Calculate:
  (rear - 1 + capacity) % capacity
  = (1 - 1 + 3) % 3
  = 3 % 3
  = 0

Return: queue[0] = 4 ✓
```

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time (All Ops) | Space | Empty/Full Detection | Complexity | Recommended |
|----------|----------------|-------|---------------------|------------|-------------|
| **Array + Count** | **O(1)** | **O(k)** | **Simple ✅** | **Low** | **Yes ✅** |
| Array + Sacrifice Slot | O(1) | O(k) | Complex | Medium | No |
| Array + Flag | O(1) | O(k) | Complex | Medium | No |
| Linked List | O(1) | O(k) | Simple | High | No (overkill) |

**Winner**: **Array with count tracking** — simplest and most efficient!

### Why Track Count

```
Without count:
  front = 0, rear = 0
  Is this empty or full? Can't tell! ❌
  
  Need complex logic:
    - Sacrifice one slot
    - Use flag
    - More code
    
With count:
  count == 0 → empty ✓
  count == capacity → full ✓
  
  Clear and simple! ✓
```

### Why Modulo for Wrapping

```
Alternative: Manual wrap checking
  rear++;
  if (rear >= capacity) {
    rear = 0;  // Manual wrap
  }

Better: Modulo
  rear = (rear + 1) % capacity;
  // Automatic wrap! ✓
  
  More concise
  Less error-prone
  Standard practice
```

### Why Rear Points to Next Available

```
Option 1: Rear points to last element
  enQueue: rear = (rear + 1) % capacity, then insert
  Rear(): return queue[rear]
  
  But initial rear? Where to start? Complex ❌

Option 2: Rear points to next available
  enQueue: insert at rear, then rear++
  Rear(): return queue[rear - 1]
  
  Clean initialization: rear = 0 ✓
  Consistent logic ✓
  
Standard practice to point to next available!
```

### Why This is Optimal

```
Time complexity:
  All operations O(1)
  - Direct array access: O(1)
  - Arithmetic operations: O(1)
  - No loops
  Optimal! ✓

Space complexity:
  Array of size k: O(k)
  Few integer variables: O(1)
  Total: O(k)
  Minimal for storing k elements! ✓

No built-in queue used ✓
```

---

## Critical Edge Cases & Gotchas

### 1. **Empty Queue Operations**
```java
MyCircularQueue queue = new MyCircularQueue(3);
queue.Front();    // → -1 (empty)
queue.Rear();     // → -1 (empty)
queue.deQueue();  // → false (nothing to remove)
queue.isEmpty();  // → true
```

### 2. **Full Queue Operations**
```java
MyCircularQueue queue = new MyCircularQueue(2);
queue.enQueue(1);  // true
queue.enQueue(2);  // true (full)
queue.enQueue(3);  // false (cannot add)
queue.isFull();    // true
```

### 3. **Single Element Queue**
```java
MyCircularQueue queue = new MyCircularQueue(1);
queue.enQueue(5);  // true
queue.Front();     // 5
queue.Rear();      // 5 (same!)
queue.isFull();    // true
queue.deQueue();   // true
queue.isEmpty();   // true
```

### 4. **Wrapping Around**
```java
MyCircularQueue queue = new MyCircularQueue(3);
// Fill: [1, 2, 3], front=0, rear=0
queue.enQueue(1); queue.enQueue(2); queue.enQueue(3);
// Dequeue: [_, 2, 3], front=1, rear=0
queue.deQueue();
// Enqueue wraps: [4, 2, 3], front=1, rear=1
queue.enQueue(4);
```

### 5. **Capacity of 1**
```java
MyCircularQueue queue = new MyCircularQueue(1);
// Only one slot
// Always full or empty, never partial
```

### 6. **Repeated Fill and Empty**
```java
// Stress test: fill, empty, fill, empty...
for (int i = 0; i < 100; i++) {
    queue.enQueue(i);
    queue.deQueue();
}
// Should work correctly
```

### 7. **Rear Index Calculation**
```java
// When rear = 0, last element at capacity - 1
// Must use (rear - 1 + capacity) % capacity
// NOT just (rear - 1) % capacity (gives negative!)
```

### 8. **Front and Rear Same**
```java
// When count = 1:
//   front = 0, rear = 1
//   Only one element
// When count = capacity:
//   front = 0, rear = 0 (after wrapping)
//   Multiple elements
// Count distinguishes these!
```

### 9. **Maximum Capacity**
```java
MyCircularQueue queue = new MyCircularQueue(1000);
// Should handle efficiently
// No performance degradation
```

### 10. **All Same Values**
```java
MyCircularQueue queue = new MyCircularQueue(5);
queue.enQueue(7); // [7]
queue.enQueue(7); // [7, 7]
queue.enQueue(7); // [7, 7, 7]
// Should distinguish positions even with same values
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Tracking Count**
```java
// WRONG - only tracking front and rear
class MyCircularQueue {
    private int[] queue;
    private int front, rear, capacity;
    // Missing: int count; ❌
    
    public boolean isEmpty() {
        return front == rear;  // WRONG! Could be full too! ❌
    }
    
    public boolean isFull() {
        return (rear + 1) % capacity == front;  // Requires sacrificing slot ❌
    }
}
```

**Why wrong**: Can't distinguish empty from full!

**Dry run failure:**
```
Initial: front = 0, rear = 0 (empty)

After filling to capacity and wrapping:
  front = 0, rear = 0 (full!)
  
front == rear returns true for both! ❌

With count:
  count == 0 → empty ✓
  count == capacity → full ✓
  Clear distinction!
```

**Fix**: Track count
```java
private int count;
public boolean isEmpty() { return count == 0; } ✓
public boolean isFull() { return count == capacity; } ✓
```

### ❌ **MISTAKE 2: Wrong Rear Calculation**
```java
// WRONG - incorrect rear index calculation
public int Rear() {
    if (isEmpty()) return -1;
    return queue[(rear - 1) % capacity];  // WRONG! ❌
}
```

**Why wrong**: Modulo of negative number is negative!

**Dry run failure:**
```
rear = 0, capacity = 5

(rear - 1) % capacity
= (0 - 1) % 5
= -1 % 5
= -1 ❌ (negative index!)

Crashes or wrong result!
```

**Fix**: Add capacity before modulo
```java
return queue[(rear - 1 + capacity) % capacity]; ✓
// (0 - 1 + 5) % 5 = 4 ✓
```

### ❌ **MISTAKE 3: Not Using Modulo**
```java
// WRONG - manual wrapping
public boolean enQueue(int value) {
    if (isFull()) return false;
    
    queue[rear] = value;
    rear++;
    if (rear >= capacity) {  // Manual check ❌
        rear = 0;
    }
    count++;
    return true;
}
```

**Why suboptimal**: Verbose and error-prone!

**Fix**: Use modulo
```java
rear = (rear + 1) % capacity; ✓
// Automatic wrapping, cleaner code
```

### ❌ **MISTAKE 4: Forgetting to Check Empty/Full**
```java
// WRONG - no checks
public boolean enQueue(int value) {
    // Missing: if (isFull()) return false; ❌
    
    queue[rear] = value;
    rear = (rear + 1) % capacity;
    count++;
    return true;  // Always returns true! ❌
}

public boolean deQueue() {
    // Missing: if (isEmpty()) return false; ❌
    
    front = (front + 1) % capacity;
    count--;
    return true;  // Always returns true! ❌
}
```

**Why wrong**: Violates preconditions!

**Dry run failure:**
```
enQueue when full:
  Overwrites data! ❌
  count exceeds capacity! ❌
  
deQueue when empty:
  count becomes negative! ❌
  Returns true when should return false! ❌
```

**Fix**: Check conditions
```java
public boolean enQueue(int value) {
    if (isFull()) return false; ✓
    // ... rest of logic
}

public boolean deQueue() {
    if (isEmpty()) return false; ✓
    // ... rest of logic
}
```

### ❌ **MISTAKE 5: Updating Count in Wrong Direction**
```java
// WRONG - incorrect count updates
public boolean enQueue(int value) {
    if (isFull()) return false;
    queue[rear] = value;
    rear = (rear + 1) % capacity;
    count--;  // WRONG! Should increment ❌
    return true;
}

public boolean deQueue() {
    if (isEmpty()) return false;
    front = (front + 1) % capacity;
    count++;  // WRONG! Should decrement ❌
    return true;
}
```

**Why wrong**: Count goes in wrong direction!

**Dry run failure:**
```
Start: count = 0
enQueue: count becomes -1 ❌
enQueue: count becomes -2 ❌

Queue reports as "empty" when it's full!
```

**Fix**: Correct direction
```java
enQueue: count++ ✓
deQueue: count-- ✓
```

### ❌ **MISTAKE 6: Not Initializing Array**
```java
// WRONG - null array
class MyCircularQueue {
    private int[] queue;  // null! ❌
    
    public MyCircularQueue(int k) {
        // Missing: queue = new int[k]; ❌
        capacity = k;
        front = 0;
        rear = 0;
        count = 0;
    }
}
```

**Why wrong**: NullPointerException!

**Dry run failure:**
```
enQueue(5):
  queue[rear] = value;  // NullPointerException! ❌
```

**Fix**: Initialize array
```java
public MyCircularQueue(int k) {
    queue = new int[k]; ✓
    // ... rest
}
```

### ❌ **MISTAKE 7: Modifying Array in Front/Rear Methods**
```java
// WRONG - side effects in getter methods
public int Front() {
    if (isEmpty()) return -1;
    int value = queue[front];
    front = (front + 1) % capacity;  // WRONG! Modifying state ❌
    count--;  // WRONG! ❌
    return value;
}
```

**Why wrong**: Front() should be read-only!

**Issue:**
```
Calling Front() multiple times:
  First call: returns first element
  Second call: returns second element ❌
  
Front() should be idempotent (same result)!
```

**Fix**: Don't modify state
```java
public int Front() {
    if (isEmpty()) return -1;
    return queue[front]; ✓
    // No modifications!
}
```

### ❌ **MISTAKE 8: Wrong Initial Values**
```java
// WRONG - incorrect initialization
public MyCircularQueue(int k) {
    queue = new int[k];
    front = 1;  // WRONG! Should be 0 ❌
    rear = 1;   // WRONG! Should be 0 ❌
    count = 0;
    capacity = k;
}
```

**Why wrong**: Inconsistent state!

**Dry run failure:**
```
First enQueue(5):
  queue[rear] = queue[1] = 5
  But logically should be at queue[0]!
  
Wastes first slot
Complicates logic
```

**Fix**: Start at 0
```java
front = 0; ✓
rear = 0;  ✓
```

### ❌ **MISTAKE 9: Using Built-in Queue**
```java
// WRONG - problem says don't use built-in queue!
class MyCircularQueue {
    private Queue<Integer> queue;  // WRONG! ❌
    
    public MyCircularQueue(int k) {
        queue = new LinkedList<>();  // Violates constraint! ❌
    }
}
```

**Why wrong**: Violates problem constraint!

**Fix**: Implement from scratch with array
```java
private int[] queue; ✓
```

### ❌ **MISTAKE 10: Not Handling Capacity 1**
```java
// Potential issue: special case for k = 1

// With count tracking, works naturally:
MyCircularQueue queue = new MyCircularQueue(1);
queue.enQueue(5);  // count = 1
queue.isFull();    // count == 1 == capacity ✓

// But without count, more complex ❌
```

**Why count helps**: Handles all capacities uniformly!

---

## Complexity Analysis

### Time Complexity

| Operation | Time | Explanation |
|-----------|------|-------------|
| **Constructor** | **O(k)** | Allocate array of size k |
| **enQueue** | **O(1)** | Direct array access, arithmetic |
| **deQueue** | **O(1)** | Update pointer, arithmetic |
| **Front** | **O(1)** | Direct array access |
| **Rear** | **O(1)** | Direct array access with calculation |
| **isEmpty** | **O(1)** | Simple comparison |
| **isFull** | **O(1)** | Simple comparison |

**All operations are O(1) constant time! ✓**

**Detailed Analysis:**
```
enQueue(value):
  1. Check count == capacity: O(1)
  2. Array assignment queue[rear] = value: O(1)
  3. Arithmetic rear = (rear + 1) % capacity: O(1)
  4. Increment count: O(1)
  Total: O(1) ✓

deQueue():
  1. Check count == 0: O(1)
  2. Arithmetic front = (front + 1) % capacity: O(1)
  3. Decrement count: O(1)
  Total: O(1) ✓

Front(), Rear(), isEmpty(), isFull():
  All just simple checks and array access
  All O(1) ✓
```

### Space Complexity: **O(k)**

```
Data structure components:
  - int[] queue of size k: O(k)
  - int front: O(1)
  - int rear: O(1)
  - int count: O(1)
  - int capacity: O(1)

Total: O(k) + O(1) = O(k)
```

**Space is optimal for storing k elements!**

**Comparison with Alternatives:**
```
Array-based (this solution):
  Space: O(k) ✓
  
Linked list-based:
  Each node: value + next pointer
  Space: O(k) but with overhead of pointers ❌
  More memory per element
  
Array is more space-efficient! ✓
```

### Optimal Complexity

```
Time: O(1) for all operations
  Can't do better than constant time
  Optimal! ✓

Space: O(k) to store k elements
  Minimum space needed
  Optimal! ✓

This solution achieves optimal complexity! ✓
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `k = 5`, operations shown step-by-step

---

**Initialization:**
```
MyCircularQueue queue = new MyCircularQueue(5);

State:
  queue = [_, _, _, _, _] (size 5)
  front = 0
  rear = 0
  count = 0
  capacity = 5

Visual:
  Index: 0  1  2  3  4
  Value: _  _  _  _  _
         ↑
      front/rear
```

---

**Operation 1: enQueue(10)**
```
Check: isFull()? count (0) == capacity (5)? No ✓

queue[rear] = queue[0] = 10
rear = (0 + 1) % 5 = 1
count = 1

State:
  queue = [10, _, _, _, _]
  front = 0, rear = 1, count = 1

Visual:
  Index: 0  1  2  3  4
  Value: 10 _  _  _  _
         ↑  ↑
      front rear
  
Return: true ✓
```

---

**Operation 2: enQueue(20)**
```
queue[1] = 20
rear = (1 + 1) % 5 = 2
count = 2

Visual:
  Index: 0  1  2  3  4
  Value: 10 20 _  _  _
         ↑     ↑
      front   rear
```

---

**Operation 3: enQueue(30)**
```
queue[2] = 30
rear = (2 + 1) % 5 = 3
count = 3

Visual:
  Index: 0  1  2  3  4
  Value: 10 20 30 _  _
         ↑        ↑
      front      rear
```

---

**Operation 4: Front()**
```
Check: isEmpty()? count (3) == 0? No ✓

Return: queue[front] = queue[0] = 10 ✓

(No state change, read-only operation)
```

---

**Operation 5: deQueue()**
```
Check: isEmpty()? No ✓

front = (0 + 1) % 5 = 1
count = 2

Visual:
  Index: 0  1  2  3  4
  Value: 10 20 30 _  _  (10 still there but "removed")
            ↑     ↑
         front   rear
  
Logical view: [20, 30]
Return: true ✓
```

---

**Operation 6: enQueue(40)**
```
queue[3] = 40
rear = (3 + 1) % 5 = 4
count = 3

Visual:
  Index: 0  1  2  3  4
  Value: 10 20 30 40 _
            ↑        ↑
         front      rear
  
Logical: [20, 30, 40]
```

---

**Operation 7: enQueue(50)**
```
queue[4] = 50
rear = (4 + 1) % 5 = 0 (wraps!)
count = 4

Visual:
  Index: 0  1  2  3  4
  Value: 10 20 30 40 50
            ↑
         front/rear (both at different conceptual positions)
  
Logical: [20, 30, 40, 50]
```

---

**Operation 8: enQueue(60)**
```
queue[0] = 60 (overwrites old 10)
rear = (0 + 1) % 5 = 1
count = 5 (FULL!)

Visual:
  Index: 0  1  2  3  4
  Value: 60 20 30 40 50
            ↑  ↑
         rear  front
  
Circular! Rear wrapped to front
Logical: [20, 30, 40, 50, 60]
```

---

**Operation 9: enQueue(70)**
```
Check: isFull()? count (5) == capacity (5)? Yes! ❌

Return: false ✓
(Cannot add, queue is full)
```

---

**Operation 10: Rear()**
```
Check: isEmpty()? No ✓

Calculate rear index:
  (rear - 1 + capacity) % capacity
  = (1 - 1 + 5) % 5
  = 0

Return: queue[0] = 60 ✓
```

---

**Operation 11: deQueue()**
```
front = (1 + 1) % 5 = 2
count = 4

Visual:
  Index: 0  1  2  3  4
  Value: 60 20 30 40 50
            ↑     ↑
         rear   front
  
Logical: [30, 40, 50, 60]
Return: true ✓
```

---

**Operation 12: enQueue(70)**
```
Now space available!

queue[1] = 70
rear = (1 + 1) % 5 = 2
count = 5 (full again!)

Visual:
  Index: 0  1  2  3  4
  Value: 60 70 30 40 50
               ↑  ↑
            rear  front
  
Circular layout:
  [front]→ 30→40→50→60→70 [rear]
  
Logical: [30, 40, 50, 60, 70]
Return: true ✓
```

---

### Visual: Circular Nature

```
Array as Circle:

     0
   4   1
     
   3   2

Operations:
  front = 3, rear = 0
  Elements: 30 (idx 3), 40 (idx 4), 50 (idx 0)
  
  Going from front:
    3 → 4 → 0 (wraps!)
    
  Going to rear:
    rear is at 1 (next available)
    Last element at 0
```

---

## Comparison of Approaches

| Approach | Time (All Ops) | Space | Empty/Full Check | Code Complexity | Recommended |
|----------|----------------|-------|------------------|-----------------|-------------|
| **Array + Count** | **O(1)** | **O(k)** | **Simple** | **Low** | **Yes ✅** |
| Array + Sacrifice Slot | O(1) | O(k+1) | Medium | Medium | No |
| Array + Flag | O(1) | O(k) | Medium | Medium | No |
| Doubly Linked List | O(1) | O(k) + pointers | Simple | High | No (overkill) |
| Two Stacks | O(n) worst | O(k) | Simple | Low | No (not O(1)) |

**Winner**: **Array with count tracking** — simplest, most efficient!

---

## Key Takeaways

1. **Fixed array** — allocate size k at construction
2. **Two pointers** — front (first element) and rear (next available)
3. **Count variable** — distinguishes empty from full
4. **Modulo arithmetic** — automatic circular wrapping
5. **rear - 1 calculation** — use `(rear - 1 + capacity) % capacity`
6. **All O(1) operations** — direct array access
7. **Check empty/full** — before enQueue/deQueue
8. **No built-in queue** — implement from scratch
9. **Front/Rear read-only** — don't modify state
10. **Initialize at 0** — front = 0, rear = 0, count = 0

---

## Interview Tips

**What to say in an interview:**

> "To implement a circular queue, I'll use a fixed-size array with two pointers: front pointing to the first element and rear pointing to the next available slot. The key challenge is distinguishing between empty and full states since both can have the same front and rear values after wrapping. I'll solve this by tracking a count variable. For circular wrapping, I'll use modulo arithmetic: when moving forward, I use (index + 1) % capacity, which automatically wraps from the last index back to zero. The Rear() method needs special care—since rear points to the next available slot, the last element is at (rear - 1), but I must add capacity before taking modulo to handle negative indices correctly: (rear - 1 + capacity) % capacity. All operations run in O(1) time with direct array access and simple arithmetic. The space complexity is O(k) for storing the k elements."

**Key points to mention:**
1. **Fixed array of size k** — allocated at construction
2. **Two pointers**: front and rear
3. **Count variable** — solves empty vs full problem
4. **Modulo arithmetic** — automatic circular wrapping
5. **Rear calculation** — `(rear - 1 + capacity) % capacity`
6. **All O(1) operations** — direct access
7. **Check preconditions** — empty before deQueue, full before enQueue
8. **No built-in queue** — custom implementation

**Common Follow-ups:**
- "How do you distinguish empty from full?" → Track count variable (clean solution)
- "Why use modulo?" → Automatic wrapping, cleaner than manual if-checks
- "Can you use a linked list?" → Yes, but array is simpler for fixed size
- "What about dynamic resizing?" → Not needed here (fixed capacity k)
- "Why (rear - 1 + capacity) % capacity?" → Handles negative index when rear = 0

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Design Circular Queue** | Medium | **Circular Buffer** | **This problem** |
| Design Circular Deque | Medium | Circular Buffer | Double-ended (add/remove both ends) |
| Implement Queue using Stacks | Easy | Two Stacks | Different implementation |
| Implement Stack using Queues | Easy | Two Queues | Inverse problem |
| Design Hit Counter | Medium | Sliding Window | Time-based circular buffer |
| Moving Average from Data Stream | Easy | Queue/Window | Running average with queue |

**Pattern Progression**:
1. **Design Circular Queue** (this) — Basic circular buffer
2. **Design Circular Deque** — Extends to double-ended
3. **Design Hit Counter** — Time-based circular application
4. **Moving Average** — Queue-based stream processing

---

## Final Pattern Label

✅ **Circular Buffer with Array + Count Tracking (Modulo Arithmetic)**

**Remember:** This is a **fixed-size circular queue** implementation. Use **fixed array of size k** with **two pointers** (front and rear) and **count variable**. **Front** points to first element, **rear** points to next available slot. **Count solves empty vs full** ambiguity (count == 0 is empty, count == capacity is full). Use **modulo arithmetic** for circular wrapping: `(index + 1) % capacity` automatically wraps. **Critical: Rear() calculation** must be `(rear - 1 + capacity) % capacity` (NOT just `(rear - 1) % capacity` which gives negative!). **All operations O(1)**: enQueue/deQueue use direct array access, Front/Rear are simple lookups, isEmpty/isFull are count checks. **Always check preconditions**: check isFull() before enQueue, isEmpty() before deQueue. **Initialize**: front = 0, rear = 0, count = 0. **Common mistakes**: forgetting count variable, wrong rear calculation (negative modulo), not checking empty/full, modifying state in Front/Rear methods. Space: O(k) optimal. Pattern applicable to any circular/ring buffer problem!
