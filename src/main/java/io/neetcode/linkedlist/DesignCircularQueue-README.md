# Design Circular Queue

## Problem Statement

**Difficulty:** Medium

Design and implement a circular queue. The circular queue is a linear data structure in which the operations are performed based on FIFO (First In First Out) principle, and the **last position is connected back to the first position** to make a circle. It is also called "**Ring Buffer**".

### Key Benefit

One of the benefits of the circular queue is that we can **make use of the spaces in front of the queue**. In a normal queue, once the queue becomes full, we cannot insert the next element even if there is a space in front of the queue. But using the circular queue, we can use the space to store new values.

### Required Operations

Implement the `MyCircularQueue` class:

- `MyCircularQueue(k)` - Initializes the object with the size of the queue to be `k`
- `int Front()` - Gets the front item from the queue. If the queue is empty, return `-1`
- `int Rear()` - Gets the last item from the queue. If the queue is empty, return `-1`
- `boolean enQueue(int value)` - Inserts an element into the circular queue. Return `true` if successful
- `boolean deQueue()` - Deletes an element from the circular queue. Return `true` if successful
- `boolean isEmpty()` - Checks whether the circular queue is empty
- `boolean isFull()` - Checks whether the circular queue is full

**Constraint:** You must solve the problem **without using the built-in queue data structure**.

### Example

```
Input:
["MyCircularQueue", "enQueue", "enQueue", "enQueue", "enQueue", "Rear", "isFull", "deQueue", "enQueue", "Rear"]
[[3], [1], [2], [3], [4], [], [], [], [4], []]

Output:
[null, true, true, true, false, 3, true, true, true, 4]

Explanation:
MyCircularQueue myCircularQueue = new MyCircularQueue(3);
myCircularQueue.enQueue(1);  // return True, queue: [1]
myCircularQueue.enQueue(2);  // return True, queue: [1, 2]
myCircularQueue.enQueue(3);  // return True, queue: [1, 2, 3]
myCircularQueue.enQueue(4);  // return False (queue is full)
myCircularQueue.Rear();      // return 3
myCircularQueue.isFull();    // return True
myCircularQueue.deQueue();   // return True, queue: [2, 3]
myCircularQueue.enQueue(4);  // return True, queue: [2, 3, 4]
myCircularQueue.Rear();      // return 4
```

### Constraints

- 1 <= k <= 1000
- 0 <= value <= 1000
- At most 3000 calls will be made to enQueue, deQueue, Front, Rear, isEmpty, and isFull

---

## Pattern Identification

**Primary Pattern:** Circular Buffer / Ring Buffer  
**Secondary Pattern:** Array with Modular Arithmetic / Linked List Design

### Why this pattern?

- **FIFO structure** with fixed capacity
- **Wrap-around behavior** - when reaching the end, go back to start
- **Space reuse** - deleted elements free up space at the front
- **Modular arithmetic** enables circular indexing

### Pattern Recognition Clues

- "Circular" in the name
- Fixed size queue
- Reuse front space after dequeue
- Ring-like structure
- FIFO with wrap-around

---

## Problem Breakdown

### Key Observations

1. **Circular Nature:** Index wraps around using modulo operation: `(index + 1) % capacity`

2. **Two Pointers:**
   - `front` - points to the first element
   - `rear` - points to the last element

3. **Track Size vs Calculate:**
   - Option 1: Track `count` variable
   - Option 2: Sacrifice one space to differentiate full vs empty

4. **Empty vs Full:**
   - Empty: `count == 0` OR `front == rear` (with sacrifice approach)
   - Full: `count == capacity` OR `(rear + 1) % capacity == front`

### Visual Representation

```
Normal Queue (wasteful):
[1, 2, 3, _, _] → deQueue → [_, _, 3, _, _] → Can't use front spaces!

Circular Queue (efficient):
Index:  0  1  2  3  4
       [1, 2, 3, _, _]
        ↑        ↑
      front    rear

After deQueue (front moves):
       [_, 2, 3, _, _]
           ↑     ↑
         front  rear

After enQueue(4) (rear wraps around):
       [4, 2, 3, _, _]
        ↑  ↑
       rear front
```

---

## Solution Approaches

### Approach 1: Array-Based with Count ✅ (RECOMMENDED)

**Time:** O(1) for all operations | **Space:** O(k)

- Use array of size k
- Track `front`, `rear`, and `count`
- Easy to check empty/full using count

---

### Approach 2: Array-Based with Sacrifice ✅ (ALTERNATIVE)

**Time:** O(1) for all operations | **Space:** O(k+1)

- Use array of size k+1 (sacrifice one space)
- Only track `front` and `rear`
- Full when: `(rear + 1) % capacity == front`
- Empty when: `front == rear`

---

### Approach 3: Linked List Based 

**Time:** O(1) for all operations | **Space:** O(k)

- Use doubly linked list with sentinel nodes
- More complex but flexible
- Rarely used for circular queue

---

## Algorithm Explanation

### Approach 1: Array with Count (Most Intuitive)

#### Data Structure

```java
class MyCircularQueue {
    private int[] data;      // Store elements
    private int front;       // Index of front element
    private int rear;        // Index of rear element
    private int count;       // Current number of elements
    private int capacity;    // Maximum capacity
}
```

#### Operations Logic

**1. enQueue(value):**
```
1. Check if full → return false
2. If empty: set both front and rear to 0
3. Else: rear = (rear + 1) % capacity
4. data[rear] = value
5. count++
6. return true
```

**2. deQueue():**
```
1. Check if empty → return false
2. If only one element: reset front and rear to -1
3. Else: front = (front + 1) % capacity
4. count--
5. return true
```

**3. Front():**
```
1. If empty → return -1
2. return data[front]
```

**4. Rear():**
```
1. If empty → return -1
2. return data[rear]
```

**5. isEmpty():**
```
return count == 0
```

**6. isFull():**
```
return count == capacity
```

### Why Modular Arithmetic?

```
Array size: 5 (indices: 0, 1, 2, 3, 4)

Without modulo (WRONG):
rear = 4, rear++ = 5 → OUT OF BOUNDS!

With modulo (CORRECT):
rear = 4, rear = (4 + 1) % 5 = 0 → Wraps to beginning!
```

**Formula:** `next_index = (current_index + 1) % capacity`

---

## Code Implementation

### Solution 1: Array with Count (RECOMMENDED)

```java
class MyCircularQueue {
    private int[] data;
    private int front;
    private int rear;
    private int count;
    private int capacity;

    public MyCircularQueue(int k) {
        this.capacity = k;
        this.data = new int[k];
        this.front = 0;
        this.rear = -1;  // -1 indicates empty queue
        this.count = 0;
    }
    
    public boolean enQueue(int value) {
        // Check if queue is full
        if (isFull()) {
            return false;
        }
        
        // Move rear pointer circularly
        rear = (rear + 1) % capacity;
        data[rear] = value;
        count++;
        
        return true;
    }
    
    public boolean deQueue() {
        // Check if queue is empty
        if (isEmpty()) {
            return false;
        }
        
        // Move front pointer circularly
        front = (front + 1) % capacity;
        count--;
        
        return true;
    }
    
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return data[front];
    }
    
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        return data[rear];
    }
    
    public boolean isEmpty() {
        return count == 0;
    }
    
    public boolean isFull() {
        return count == capacity;
    }
}
```

**Time Complexity:** O(1) for all operations  
**Space Complexity:** O(k) where k is the capacity

---

### Solution 2: Array with Sacrifice Space

```java
class MyCircularQueue {
    private int[] data;
    private int front;
    private int rear;
    private int capacity;

    public MyCircularQueue(int k) {
        this.capacity = k + 1;  // Extra space for distinction
        this.data = new int[capacity];
        this.front = 0;
        this.rear = 0;
    }
    
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        
        data[rear] = value;
        rear = (rear + 1) % capacity;
        
        return true;
    }
    
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        
        front = (front + 1) % capacity;
        
        return true;
    }
    
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return data[front];
    }
    
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        // rear points to next empty spot, so get previous
        int rearIndex = (rear - 1 + capacity) % capacity;
        return data[rearIndex];
    }
    
    public boolean isEmpty() {
        return front == rear;
    }
    
    public boolean isFull() {
        return (rear + 1) % capacity == front;
    }
}
```

**Key Difference:** 
- Allocates k+1 space but uses k
- No need for `count` variable
- Full/Empty detected by pointer positions

---

### Solution 3: Linked List Based

```java
class MyCircularQueue {
    class Node {
        int val;
        Node next;
        Node(int val) {
            this.val = val;
        }
    }
    
    private Node head;
    private Node tail;
    private int count;
    private int capacity;

    public MyCircularQueue(int k) {
        this.capacity = k;
        this.count = 0;
        this.head = null;
        this.tail = null;
    }
    
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        
        Node newNode = new Node(value);
        
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        
        count++;
        return true;
    }
    
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        
        head = head.next;
        count--;
        
        if (isEmpty()) {
            tail = null;
        }
        
        return true;
    }
    
    public int Front() {
        return isEmpty() ? -1 : head.val;
    }
    
    public int Rear() {
        return isEmpty() ? -1 : tail.val;
    }
    
    public boolean isEmpty() {
        return count == 0;
    }
    
    public boolean isFull() {
        return count == capacity;
    }
}
```

---

## Complexity Analysis

### All Approaches

| Operation | Time | Space |
|-----------|------|-------|
| Constructor | O(1) | O(k) |
| enQueue | O(1) | - |
| deQueue | O(1) | - |
| Front | O(1) | - |
| Rear | O(1) | - |
| isEmpty | O(1) | - |
| isFull | O(1) | - |

**Overall Space:** O(k) where k is the queue capacity

---

## Dry Run (Step-by-Step)

**Using Array with Count approach, k = 3**

### Initial State

```
capacity = 3
data = [_, _, _]
front = 0, rear = -1, count = 0
```

---

### Operation 1: `enQueue(1)`

```
Before: [_, _, _], front=0, rear=-1, count=0
Check: isFull? No (count=0)
Action: rear = (-1+1)%3 = 0, data[0]=1, count=1
After:  [1, _, _], front=0, rear=0, count=1
         ↑
      front/rear
Return: true
```

---

### Operation 2: `enQueue(2)`

```
Before: [1, _, _], front=0, rear=0, count=1
Check: isFull? No (count=1)
Action: rear = (0+1)%3 = 1, data[1]=2, count=2
After:  [1, 2, _], front=0, rear=1, count=2
         ↑  ↑
      front rear
Return: true
```

---

### Operation 3: `enQueue(3)`

```
Before: [1, 2, _], front=0, rear=1, count=2
Check: isFull? No (count=2)
Action: rear = (1+1)%3 = 2, data[2]=3, count=3
After:  [1, 2, 3], front=0, rear=2, count=3
         ↑     ↑
      front  rear
Return: true
```

---

### Operation 4: `enQueue(4)`

```
Before: [1, 2, 3], front=0, rear=2, count=3
Check: isFull? Yes (count=3 == capacity=3)
Return: false (queue is full)
```

---

### Operation 5: `Rear()`

```
Check: isEmpty? No
Return: data[rear] = data[2] = 3
```

---

### Operation 6: `isFull()`

```
Check: count == capacity? 3 == 3? Yes
Return: true
```

---

### Operation 7: `deQueue()`

```
Before: [1, 2, 3], front=0, rear=2, count=3
Check: isEmpty? No
Action: front = (0+1)%3 = 1, count=2
After:  [X, 2, 3], front=1, rear=2, count=2
            ↑  ↑
         front rear
Return: true
```

---

### Operation 8: `enQueue(4)`

```
Before: [X, 2, 3], front=1, rear=2, count=2
Check: isFull? No (count=2)
Action: rear = (2+1)%3 = 0, data[0]=4, count=3
After:  [4, 2, 3], front=1, rear=0, count=3
         ↑  ↑
       rear front
Return: true ← Notice wrap-around!
```

---

### Operation 9: `Rear()`

```
Check: isEmpty? No
Return: data[rear] = data[0] = 4
```

---

## Edge Cases

### 1. Capacity of 1

```java
MyCircularQueue queue = new MyCircularQueue(1);
queue.enQueue(1);  // true, [1]
queue.enQueue(2);  // false (full)
queue.deQueue();   // true, []
queue.enQueue(2);  // true, [2]
```

---

### 2. Empty Queue Operations

```java
MyCircularQueue queue = new MyCircularQueue(3);
queue.Front();     // -1
queue.Rear();      // -1
queue.deQueue();   // false
queue.isEmpty();   // true
queue.isFull();    // false
```

---

### 3. Fill, Empty, Refill

```java
MyCircularQueue queue = new MyCircularQueue(2);
queue.enQueue(1);  // [1]
queue.enQueue(2);  // [1, 2] FULL
queue.deQueue();   // [2]
queue.deQueue();   // [] EMPTY
queue.enQueue(3);  // [3]
queue.enQueue(4);  // [3, 4] FULL again
```

---

### 4. Multiple Wrap-arounds

```java
MyCircularQueue queue = new MyCircularQueue(3);
// [1, 2, 3] → deQueue → [2, 3]
// [2, 3, 4] → deQueue → [3, 4]
// [3, 4, 5] → deQueue → [4, 5]
// Rear wraps multiple times
```

---

## Common Mistakes

### ❌ Mistake 1: Wrong Modulo for Rear Calculation

```java
// WRONG - doesn't handle rear = -1
rear = rear + 1 % capacity;  // Operator precedence issue!

// CORRECT
rear = (rear + 1) % capacity;
```

---

### ❌ Mistake 2: Not Handling Empty Queue in Rear()

```java
// WRONG - doesn't check if empty
public int Rear() {
    return data[rear];  // Crashes or wrong value!
}

// CORRECT
public int Rear() {
    if (isEmpty()) return -1;
    return data[rear];
}
```

---

### ❌ Mistake 3: Forgetting to Update Count

```java
// WRONG
public boolean enQueue(int value) {
    if (isFull()) return false;
    rear = (rear + 1) % capacity;
    data[rear] = value;
    // Forgot: count++
    return true;
}
```

---

### ❌ Mistake 4: Wrong Full Condition with Sacrifice Approach

```java
// WRONG
public boolean isFull() {
    return rear % capacity == front;  // Missing the +1!
}

// CORRECT
public boolean isFull() {
    return (rear + 1) % capacity == front;
}
```

---

### ❌ Mistake 5: Negative Modulo Issue

```java
// WRONG - in Rear() for sacrifice approach
int rearIndex = (rear - 1) % capacity;  // Can be negative!

// CORRECT
int rearIndex = (rear - 1 + capacity) % capacity;
```

**Example:** `rear = 0`, `(0 - 1) % 5 = -1` ❌  
**Correct:** `(0 - 1 + 5) % 5 = 4` ✅

---

## Why This Strategy?

### Advantages of Circular Queue

1. ✅ **Space Efficiency** - Reuses freed space at front
2. ✅ **O(1) Operations** - All operations are constant time
3. ✅ **Cache Friendly** - Array-based implementation
4. ✅ **Bounded Size** - Prevents unlimited growth
5. ✅ **Predictable Performance** - No dynamic allocation

### Array vs Linked List

| Aspect | Array | Linked List |
|--------|-------|-------------|
| Space | O(k) fixed | O(k) but per-node overhead |
| Cache | ✅ Better | ❌ Worse (pointer chasing) |
| Complexity | Simpler | More complex |
| Flexibility | Fixed size | Could be circular LL |
| **Preferred** | **✅ Yes** | Rarely used |

---

## Real-World Applications

### Where Circular Queues Are Used

1. **Producer-Consumer Problems**
   - Audio/video buffering
   - Print job scheduling

2. **CPU Scheduling**
   - Round-robin scheduling
   - Process queues

3. **Network Buffers**
   - Packet queuing in routers
   - Circular buffer in NIC

4. **Memory Management**
   - Circular log buffers
   - Ring buffers in device drivers

5. **Gaming**
   - Event queues
   - Animation frame buffers

---

## Interview Tips

### What to Say in Interview

1. **Clarify Requirements:**
   - "Should I use array or linked list?"
   - "Can I sacrifice one space for easier full/empty detection?"

2. **Explain Trade-offs:**
   - "Array approach is more cache-friendly"
   - "I'll use count variable for clarity over space sacrifice"

3. **Mention Modulo:**
   - "Key insight is using modular arithmetic for wrap-around"

4. **Time Complexity:**
   - "All operations are O(1) which is optimal for a queue"

### Expected Follow-up Questions

**Q:** "What if we want to resize the queue dynamically?"  
**A:** "We'd need to create new array, copy elements maintaining order, update pointers. O(k) operation."

**Q:** "How would you implement with array of size k without sacrificing space?"  
**A:** "Use a count variable to differentiate full vs empty, which I've done."

**Q:** "Can you implement thread-safe circular queue?"  
**A:** "Add synchronization or use atomic operations for enQueue/deQueue, or use lock-free ring buffer."

**Q:** "What's the difference from normal queue?"  
**A:** "Normal queue wastes front space. Circular queue reuses it via wrap-around."

---

## Visual Summary

### State Transitions

```
Empty Queue:
[_, _, _] front=0, rear=-1, count=0

After enQueue(1):
[1, _, _] front=0, rear=0, count=1
 ↑
F/R

After enQueue(2):
[1, 2, _] front=0, rear=1, count=2
 ↑  ↑
 F  R

After enQueue(3):
[1, 2, 3] front=0, rear=2, count=3 FULL
 ↑     ↑
 F     R

After deQueue():
[X, 2, 3] front=1, rear=2, count=2
    ↑  ↑
    F  R

After enQueue(4) - WRAP AROUND:
[4, 2, 3] front=1, rear=0, count=3
 ↑  ↑
 R  F
```

---

## Related Problems

- **Design Queue using Stacks** - Different implementation approach
- **Design Deque** - Double-ended queue
- **LRU Cache** - Uses similar circular/bounded structure
- **Sliding Window Maximum** - Can use circular buffer optimization

---

## Pattern Recognition

**When you see:**
- "Circular" data structure
- "Ring buffer"
- Fixed-size FIFO
- "Reuse front space"
- Bounded queue

**Think:**
- Array with modular arithmetic
- Track front and rear pointers
- Use count or sacrifice space
- All O(1) operations possible

---

## Summary

- **Pattern:** Circular Buffer with Modular Arithmetic
- **Implementation:** Array-based (preferred) or Linked List
- **Key Technique:** `(index + 1) % capacity` for wrap-around
- **Time:** O(1) for all operations
- **Space:** O(k) for queue capacity k
- **Critical Insight:** Modulo enables circular indexing
- **Best Practice:** Use count variable for clarity
- **Common Pitfall:** Negative modulo when calculating previous index
- **Interview Gold:** Demonstrates understanding of array manipulation and FIFO structures

