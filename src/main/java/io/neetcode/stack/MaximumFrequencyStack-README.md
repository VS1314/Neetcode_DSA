# Maximum Frequency Stack

## Problem Description

**Difficulty**: Hard

Design a **stack-like data structure** to push elements to the stack and pop the **most frequent element** from the stack.

Implement the `FreqStack` class:
- `FreqStack()` constructs an empty frequency stack
- `void push(int val)` pushes an integer `val` onto the top of the stack
- `int pop()` removes and returns the **most frequent element** in the stack
  - If there is a **tie** for the most frequent element, the element **closest to the stack's top** is removed and returned

## Examples

### Example 1:
```
Input:
["FreqStack", "push", "push", "push", "push", "push", "push", "pop", "pop", "pop", "pop"]
[[], [5], [7], [5], [7], [4], [5], [], [], [], []]

Output:
[null, null, null, null, null, null, null, 5, 7, 5, 4]

Explanation:
FreqStack freqStack = new FreqStack();

freqStack.push(5);   // stack: [5]
freqStack.push(7);   // stack: [5, 7]
freqStack.push(5);   // stack: [5, 7, 5]
freqStack.push(7);   // stack: [5, 7, 5, 7]
freqStack.push(4);   // stack: [5, 7, 5, 7, 4]
freqStack.push(5);   // stack: [5, 7, 5, 7, 4, 5]

freqStack.pop();     // return 5
  // 5 appears 3 times (most frequent)
  // stack becomes [5, 7, 5, 7, 4]

freqStack.pop();     // return 7
  // 5 and 7 both appear 2 times (tie)
  // 7 is closer to top (at index 3, vs 5 at index 2)
  // stack becomes [5, 7, 5, 4]

freqStack.pop();     // return 5
  // 5 appears 2 times (most frequent)
  // stack becomes [5, 7, 4]

freqStack.pop();     // return 4
  // All appear 1 time (tie)
  // 4 is closest to top
  // stack becomes [5, 7]
```

### Example 2:
```
Input:
["FreqStack", "push", "push", "pop", "push", "pop", "push", "pop"]
[[], [1], [1], [], [2], [], [3], []]

Output:
[null, null, null, 1, null, 1, null, 2]

Explanation:
push(1): stack [1], freq: 1->1
push(1): stack [1, 1], freq: 1->2
pop(): return 1 (freq 2), stack [1], freq: 1->1
push(2): stack [1, 2], freq: 1->1, 2->1
pop(): return 2 (both freq 1, 2 is closer to top), stack [1], freq: 1->1
push(3): stack [1, 3], freq: 1->1, 3->1
pop(): return 3 (both freq 1, 3 is closer to top), stack [1], freq: 1->1
```

### Example 3:
```
Input:
["FreqStack", "push", "push", "push", "pop", "pop"]
[[], [1], [2], [3], [], []]

Output:
[null, null, null, null, 3, 2]

Explanation:
All elements have frequency 1
Pop returns most recent: 3, then 2
```

### Example 4:
```
Input:
["FreqStack", "push", "push", "push", "push", "pop", "pop"]
[[], [4], [0], [9], [3], [], []]

Output:
[null, null, null, null, null, 3, 9]

Explanation:
All have freq 1, pop most recent: 3, then 9
```

### Example 5:
```
Input:
["FreqStack", "push", "push", "push", "push", "pop"]
[[], [1], [1], [1], [2], []]

Output:
[null, null, null, null, null, 1]

Explanation:
1 appears 3 times, 2 appears 1 time
Pop returns 1 (most frequent)
```

## Constraints
- 0 <= val <= 10^9
- At most **20,000** calls will be made to `push` and `pop`
- It is guaranteed that there will be **at least one element** in the stack before calling `pop`

**Recommended Complexity**: O(1) for both push and pop operations

---

## Pattern Recognition

**Primary Pattern**: **Stack + HashMap (Frequency-Based Data Structure Design)**

**Why This Pattern?**
- Need to track frequency of each element
- Need to handle ties by recency (most recent wins)
- Need O(1) operations for push and pop
- Multiple data structures working together

**Key Insight**: Group Elements by Frequency Level
```
Maximum Frequency Stack problem:
  Return most frequent element
  Tie → return most recent
  
Naive approach:
  Track frequencies in map
  On pop: scan all frequencies to find max
  Problem: O(n) pop time ❌
  
Optimal insight:
  GROUP elements by their frequency!
  
  freq map: val -> current frequency
  group map: frequency -> stack of values at that frequency
  maxFreq: current maximum frequency
  
  When pushing val:
    1. Increment freq[val]
    2. Add val to group[freq[val]]
    3. Update maxFreq if needed
  
  When popping:
    1. Get value from group[maxFreq] (most recent at this frequency)
    2. Decrement freq[value]
    3. If group[maxFreq] is empty, decrement maxFreq
```

**The Data Structure Strategy**:
```
Use THREE data structures:

1. freq: HashMap<Integer, Integer>
   Maps: value -> its current frequency
   Example: {5: 3, 7: 2, 4: 1}
   
2. group: HashMap<Integer, Stack<Integer>>
   Maps: frequency -> stack of values at that frequency
   Example: {
     1: [4],
     2: [7],
     3: [5]
   }
   
3. maxFreq: int
   Current maximum frequency in the stack
   Example: 3

Why this works:
  - freq tells us how often each value appears
  - group organizes values by frequency level
  - maxFreq lets us quickly access highest frequency
  - Stack in each group maintains recency order
```

**Example Showing Data Structure Evolution**:
```
Operations: push(5), push(7), push(5), push(7), push(4), push(5)

After push(5):
  freq: {5: 1}
  group: {1: [5]}
  maxFreq: 1

After push(7):
  freq: {5: 1, 7: 1}
  group: {1: [5, 7]}
  maxFreq: 1

After push(5) [second time]:
  freq: {5: 2, 7: 1}
  group: {1: [5, 7], 2: [5]}
  maxFreq: 2
  
  Note: 5 appears in BOTH group[1] and group[2]!

After push(7) [second time]:
  freq: {5: 2, 7: 2}
  group: {1: [5, 7], 2: [5, 7]}
  maxFreq: 2

After push(4):
  freq: {5: 2, 7: 2, 4: 1}
  group: {1: [5, 7, 4], 2: [5, 7]}
  maxFreq: 2

After push(5) [third time]:
  freq: {5: 3, 7: 2, 4: 1}
  group: {1: [5, 7, 4], 2: [5, 7], 3: [5]}
  maxFreq: 3
  
  Now 5 appears in group[1], group[2], AND group[3]!
```

**Pop Operation Walkthrough**:
```
Starting state:
  freq: {5: 3, 7: 2, 4: 1}
  group: {1: [5, 7, 4], 2: [5, 7], 3: [5]}
  maxFreq: 3

pop():
  1. Get most recent from group[maxFreq=3]
     val = group[3].pop() = 5
  2. Decrement freq[5]: 3 -> 2
  3. Check if group[3] is empty: yes!
     Decrement maxFreq: 3 -> 2
  4. Return 5
  
  After pop:
    freq: {5: 2, 7: 2, 4: 1}
    group: {1: [5, 7, 4], 2: [5, 7], 3: []}
    maxFreq: 2

pop() again:
  1. Get from group[maxFreq=2]
     val = group[2].pop() = 7 (most recent at freq 2)
  2. Decrement freq[7]: 2 -> 1
  3. Group[2] still has [5], so maxFreq stays 2
  4. Return 7
  
  After pop:
    freq: {5: 2, 7: 1, 4: 1}
    group: {1: [5, 7, 4], 2: [5], 3: []}
    maxFreq: 2
```

**Why This is O(1)**:
```
Push operation:
  1. freq.get(val) and freq.put(val, newFreq): O(1)
  2. group.get(newFreq): O(1)
  3. stack.push(val): O(1)
  4. Update maxFreq: O(1)
  Total: O(1) ✓

Pop operation:
  1. group.get(maxFreq): O(1)
  2. stack.pop(): O(1)
  3. freq.get(val) and freq.put(val, newFreq): O(1)
  4. Check if stack empty and update maxFreq: O(1)
  Total: O(1) ✓

All operations are direct map/stack access!
```

**Critical Detail**: Value Appears in Multiple Groups
```
KEY INSIGHT: A value can appear in multiple frequency groups!

Example: push(5) three times

After 1st push(5):
  group[1]: [5]

After 2nd push(5):
  group[1]: [5, ...]  (5 still here from 1st push)
  group[2]: [5]       (5 added here for 2nd push)

After 3rd push(5):
  group[1]: [5, ...]  (5 still here)
  group[2]: [5, ...]  (5 still here)
  group[3]: [5]       (5 added here for 3rd push)

Each push adds the value to a NEW frequency level!
We don't remove from lower levels!

Why this works:
  When we pop, we only access maxFreq group
  Lower frequency groups are never touched during pop
  They just sit there (no need to remove)
```

**Why Not Remove from Lower Levels?**
```
Consider: push(5) twice, pop(), push(5) again

If we removed from lower levels:
  push(5): group[1]=[5], freq[5]=1
  push(5): group[2]=[5], remove from group[1]?, freq[5]=2
  pop(): return 5 from group[2], freq[5]=1
  push(5): group[2]=[5] again, freq[5]=2
  
  Extra work removing and re-adding!

Without removing:
  push(5): group[1]=[5], freq[5]=1
  push(5): group[1]=[5], group[2]=[5], freq[5]=2
  pop(): return 5 from group[2], freq[5]=1
  push(5): group[1]=[5,5], group[2]=[5,5], freq[5]=2
  
  Never need to remove from lower levels!
  Old entries harmless (never accessed)

Simpler and faster to just leave them!
```

**Related Patterns**:
1. **HashMap + Stack** — Core combination
2. **Frequency Tracking** — Count occurrences
3. **Multi-Level Organization** — Group by property
4. **Design Problem** — Build custom data structure

---

## Algorithm & Approach

### Core Insight

**Why Naive Approach Fails:**
```
Naive: Track frequencies, scan for max on pop
  freq: Map<Integer, Integer>
  
  push(val):
    freq[val]++
    
  pop():
    maxFreq = max(freq.values())  // O(n)
    find value with maxFreq and most recent
    return value
  
Problems:
  - O(n) to find max frequency
  - O(n) to find most recent among tied values
  - Too slow for 20,000 operations!
  
Optimal approach:
  Group elements by frequency
  Track maxFreq
  → O(1) push and pop ✓
```

**The Optimal Strategy**:
```
Key observations:
  1. Group values by their frequency level
  2. Use stack for each frequency (maintains recency)
  3. Track current max frequency
  4. On push: add to appropriate frequency group
  5. On pop: take from max frequency group
  
Operations:
  push: O(1) — direct map/stack operations
  pop: O(1) — direct access to maxFreq group
  
Total: O(1) for all operations
```

### Step-by-Step Algorithm

---

#### **Approach 1: HashMap + Stack Groups - OPTIMAL**

**Core Idea**:
- Use `freq` map to track current frequency of each value
- Use `group` map where each frequency level has a stack
- Track `maxFreq` for quick access to highest frequency
- Push adds value to its frequency group
- Pop takes from maxFreq group

**Algorithm**
```
class FreqStack:
    freq: Map<Integer, Integer>           // value -> frequency
    group: Map<Integer, Stack<Integer>>   // frequency -> stack of values
    maxFreq: int                          // current max frequency
    
    constructor():
        freq = new HashMap()
        group = new HashMap()
        maxFreq = 0
    
    push(val):
        // Increment frequency
        freq[val] = freq.getOrDefault(val, 0) + 1
        f = freq[val]
        
        // Add to frequency group
        if group[f] not exists:
            group[f] = new Stack()
        group[f].push(val)
        
        // Update max frequency
        maxFreq = max(maxFreq, f)
    
    pop():
        // Get most recent value from max frequency group
        val = group[maxFreq].pop()
        
        // Decrement frequency
        freq[val]--
        
        // If max frequency group is empty, decrement maxFreq
        if group[maxFreq].isEmpty():
            maxFreq--
        
        return val
```

**Code Implementation**
```java
class FreqStack {
    private Map<Integer, Integer> freq;           // value -> frequency
    private Map<Integer, Stack<Integer>> group;   // frequency -> stack
    private int maxFreq;
    
    public FreqStack() {
        freq = new HashMap<>();
        group = new HashMap<>();
        maxFreq = 0;
    }
    
    public void push(int val) {
        // Increment frequency of val
        int f = freq.getOrDefault(val, 0) + 1;
        freq.put(val, f);
        
        // Add val to the stack for this frequency
        group.computeIfAbsent(f, k -> new Stack<>()).push(val);
        
        // Update max frequency
        maxFreq = Math.max(maxFreq, f);
    }
    
    public int pop() {
        // Get most recent value from max frequency group
        int val = group.get(maxFreq).pop();
        
        // Decrement its frequency
        freq.put(val, freq.get(val) - 1);
        
        // If max frequency group is now empty, decrement maxFreq
        if (group.get(maxFreq).isEmpty()) {
            maxFreq--;
        }
        
        return val;
    }
}
```

**Example Walkthrough**

Operations: `push(5), push(7), push(5), push(7), push(4), push(5), pop(), pop(), pop(), pop()`

| Operation | Action | freq | group | maxFreq | Return |
|-----------|--------|------|-------|---------|--------|
| push(5) | f=1, add to group[1] | {5:1} | {1:[5]} | 1 | - |
| push(7) | f=1, add to group[1] | {5:1, 7:1} | {1:[5,7]} | 1 | - |
| push(5) | f=2, add to group[2] | {5:2, 7:1} | {1:[5,7], 2:[5]} | 2 | - |
| push(7) | f=2, add to group[2] | {5:2, 7:2} | {1:[5,7], 2:[5,7]} | 2 | - |
| push(4) | f=1, add to group[1] | {5:2, 7:2, 4:1} | {1:[5,7,4], 2:[5,7]} | 2 | - |
| push(5) | f=3, add to group[3] | {5:3, 7:2, 4:1} | {1:[5,7,4], 2:[5,7], 3:[5]} | 3 | - |
| pop() | Pop from group[3] | {5:2, 7:2, 4:1} | {1:[5,7,4], 2:[5,7], 3:[]} | 2 | 5 |
| pop() | Pop from group[2] (7 is top) | {5:2, 7:1, 4:1} | {1:[5,7,4], 2:[5], 3:[]} | 2 | 7 |
| pop() | Pop from group[2] (5 is top) | {5:1, 7:1, 4:1} | {1:[5,7,4], 2:[], 3:[]} | 1 | 5 |
| pop() | Pop from group[1] (4 is top) | {5:1, 7:1, 4:0} | {1:[5,7], 2:[], 3:[]} | 1 | 4 |

**Complexity Analysis**
- **Time**: O(1) for both push and pop
- **Space**: O(n) where n is number of elements pushed

---

#### **Approach 2: Using ArrayList Instead of Stack - ALTERNATIVE**

**Core Idea**: Same logic but use ArrayList instead of Stack.

**Code Implementation**
```java
class FreqStack {
    private Map<Integer, Integer> freq;
    private Map<Integer, List<Integer>> group;
    private int maxFreq;
    
    public FreqStack() {
        freq = new HashMap<>();
        group = new HashMap<>();
        maxFreq = 0;
    }
    
    public void push(int val) {
        int f = freq.getOrDefault(val, 0) + 1;
        freq.put(val, f);
        
        group.computeIfAbsent(f, k -> new ArrayList<>()).add(val);
        
        maxFreq = Math.max(maxFreq, f);
    }
    
    public int pop() {
        List<Integer> list = group.get(maxFreq);
        int val = list.remove(list.size() - 1);  // Remove last (top)
        
        freq.put(val, freq.get(val) - 1);
        
        if (list.isEmpty()) {
            maxFreq--;
        }
        
        return val;
    }
}
```

**Key Difference**: 
- ArrayList instead of Stack
- Same time complexity
- Slightly different API (remove vs pop)

**Complexity Analysis**
- **Time**: O(1) for both operations
- **Space**: O(n)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Push Time | Pop Time | Space | Recommended |
|----------|-----------|----------|-------|-------------|
| **HashMap + Stack Groups** | **O(1)** | **O(1)** | **O(n)** | **Yes ✅** |
| ArrayList Groups | O(1) | O(1) | O(n) | Alternative |
| Naive (scan on pop) | O(1) | O(n) | O(n) | Too slow ❌ |

**Winner**: **HashMap + Stack Groups** — optimal O(1) operations!

### Why Group by Frequency?

```
Grouping by frequency enables O(1) access!

Without grouping:
  freq: {5: 3, 7: 2, 4: 1}
  
  pop():
    Scan all frequencies to find max: O(n)
    Find value with that frequency: O(n)
    Total: O(n) ❌

With grouping:
  freq: {5: 3, 7: 2, 4: 1}
  group: {1: [5,7,4], 2: [5,7], 3: [5]}
  maxFreq: 3
  
  pop():
    Access group[maxFreq]: O(1)
    Pop from stack: O(1)
    Total: O(1) ✓

Grouping is the key optimization!
```

### Why Use Stack for Each Group?

```
Stack maintains recency order within each frequency level!

Example: push(5), push(7), push(5), push(7)
  Both 5 and 7 have frequency 2
  
  group[2]: [5, 7]
            ↑   ↑
          first second
  
When we pop():
  Need the most RECENT at frequency 2
  Stack.pop() gives us 7 (last pushed) ✓
  
If we used set instead of stack:
  group[2]: {5, 7}
  No ordering! Can't tell which is more recent ❌

Stack maintains FIFO within each frequency!
```

### Why Track maxFreq?

```
maxFreq enables O(1) access to highest frequency!

Without maxFreq:
  pop():
    Scan all keys in group map: O(maxFreq)
    Find maximum: O(maxFreq)
    Not O(1)! ❌

With maxFreq:
  pop():
    Access group[maxFreq]: O(1) ✓
    
maxFreq update:
  On push: maxFreq = max(maxFreq, newFreq) - O(1)
  On pop: if group[maxFreq] empty, maxFreq-- - O(1)
  
Simple decrement works because:
  We can only empty maxFreq level
  Next level down is maxFreq-1
  No need to scan!
```

### Why Not Remove from Lower Frequency Groups?

```
Leaving old entries is harmless and efficient!

Example: push(5) twice, then pop(5)

Approach 1: Remove from lower levels
  push(5): group[1]=[5], freq[5]=1
  push(5): group[2]=[5], remove from group[1], freq[5]=2
  pop(): from group[2], freq[5]=1
  
  Extra work removing! Complex!

Approach 2: Leave old entries
  push(5): group[1]=[5], freq[5]=1
  push(5): group[2]=[5], group[1]=[5], freq[5]=2
  pop(): from group[2], freq[5]=1
  
  Old entry in group[1] stays
  Never accessed (we check freq first)
  Simpler!

Why old entries don't matter:
  We always pop from maxFreq
  Lower levels never accessed during pop
  They're just memory (worth it for O(1))
```

### Why Use computeIfAbsent?

```
computeIfAbsent creates stack only when needed

Without:
  if (!group.containsKey(f)) {
      group.put(f, new Stack<>());
  }
  group.get(f).push(val);
  
With:
  group.computeIfAbsent(f, k -> new Stack<>()).push(val);
  
Cleaner, more concise!
Same functionality, better code style.
```

---

## Critical Edge Cases & Gotchas

### 1. **All Elements Have Same Frequency**
```java
push(1), push(2), push(3), pop()
All have freq 1
Pop returns most recent: 3
```

### 2. **Same Element Pushed Multiple Times**
```java
push(5), push(5), push(5), pop(), pop(), pop()
Each pop returns 5
Frequencies: 3, 2, 1
```

### 3. **Alternating Elements**
```java
push(1), push(2), push(1), push(2), pop()
Both have freq 2
Pop returns most recent at freq 2: 2
```

### 4. **Single Element**
```java
push(5), pop()
Returns 5
```

### 5. **Large Values**
```java
push(1000000000), pop()
Handles values up to 10^9
```

### 6. **Many Operations**
```java
20,000 push and pop operations
All must be O(1)
```

### 7. **After Multiple Pops**
```java
push(1), push(2), push(3)
pop(), pop()
Stack: [1]
pop() returns 1
```

### 8. **Rebuilding Frequency**
```java
push(5), push(5), pop(), push(5)
freq[5]: 0 -> 1 -> 2 -> 1 -> 2
Each operation updates correctly
```

### 9. **Empty and Rebuild**
```java
push(1), pop(), push(2), pop()
Stack empties and refills
maxFreq: 0 -> 1 -> 0 -> 1 -> 0
```

### 10. **Tie Breaking**
```java
push(1), push(2), push(1), push(2), push(3), pop()
1 and 2 have freq 2, 3 has freq 1
3 is most recent at freq 1
But maxFreq is 2!
Pop returns 2 (most recent at freq 2)
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Updating maxFreq on Push**
```java
// WRONG - doesn't update maxFreq
public void push(int val) {
    int f = freq.getOrDefault(val, 0) + 1;
    freq.put(val, f);
    group.computeIfAbsent(f, k -> new Stack<>()).push(val);
    // Missing: maxFreq = Math.max(maxFreq, f);
}
```

**Why wrong**: maxFreq becomes stale!

**Dry run failure:**
```
push(5): freq[5]=1, group[1]=[5], maxFreq=0 ❌
push(5): freq[5]=2, group[2]=[5], maxFreq=0 ❌

pop(): Try to access group[0] → null ❌
```

**Fix**: Always update maxFreq
```java
maxFreq = Math.max(maxFreq, f);
```

### ❌ **MISTAKE 2: Not Checking if Group is Empty on Pop**
```java
// WRONG - doesn't check if group becomes empty
public int pop() {
    int val = group.get(maxFreq).pop();
    freq.put(val, freq.get(val) - 1);
    // Missing: if (group.get(maxFreq).isEmpty()) maxFreq--;
    return val;
}
```

**Why wrong**: maxFreq stays at empty level!

**Dry run failure:**
```
State: group[2]=[5], maxFreq=2

pop():
  val = group[2].pop() = 5
  group[2] is now empty
  But maxFreq still = 2 ❌

Next pop():
  Try group[2].pop() → EmptyStackException ❌
```

**Fix**: Check and decrement
```java
if (group.get(maxFreq).isEmpty()) {
    maxFreq--;
}
```

### ❌ **MISTAKE 3: Removing Value from All Groups**
```java
// WRONG - tries to remove from all frequency levels
public void push(int val) {
    int oldFreq = freq.getOrDefault(val, 0);
    if (oldFreq > 0) {
        group.get(oldFreq).remove((Integer)val);  // Expensive!
    }
    int f = oldFreq + 1;
    freq.put(val, f);
    group.computeIfAbsent(f, k -> new Stack<>()).push(val);
    maxFreq = Math.max(maxFreq, f);
}
```

**Why wrong**: Unnecessary work, and remove() is O(n) on stack!

**Fix**: Don't remove, just leave old entries
```java
// No need to remove from lower levels
```

### ❌ **MISTAKE 4: Not Decrementing freq on Pop**
```java
// WRONG - doesn't update freq
public int pop() {
    int val = group.get(maxFreq).pop();
    // Missing: freq.put(val, freq.get(val) - 1);
    if (group.get(maxFreq).isEmpty()) {
        maxFreq--;
    }
    return val;
}
```

**Why wrong**: freq map becomes incorrect!

**Dry run failure:**
```
push(5), push(5), push(5)
freq[5] = 3

pop():
  Return 5
  freq[5] still = 3 ❌

push(5) again:
  freq[5] = 3 + 1 = 4 ❌
  
Should be 3!
```

**Fix**: Always decrement freq
```java
freq.put(val, freq.get(val) - 1);
```

### ❌ **MISTAKE 5: Using Wrong Data Structure**
```java
// WRONG - using PriorityQueue (slow!)
private Map<Integer, PriorityQueue<int[]>> group;  // [value, timestamp]

public void push(int val) {
    // ... add to priority queue with timestamp
    // O(log n) ❌
}

public int pop() {
    // ... poll from priority queue
    // O(log n) ❌
}
```

**Why wrong**: Not O(1)!

**Fix**: Use Stack, not PriorityQueue
```java
private Map<Integer, Stack<Integer>> group;
```

### ❌ **MISTAKE 6: Incrementing freq Before Adding to Group**
```java
// WRONG - increments freq, then adds to OLD frequency group
public void push(int val) {
    freq.put(val, freq.getOrDefault(val, 0) + 1);
    int f = freq.get(val);
    // This is correct, but if you did:
    int f = freq.getOrDefault(val, 0);  // Wrong! Old frequency
    freq.put(val, f + 1);
    group.computeIfAbsent(f, k -> new Stack<>()).push(val);
    // Added to wrong group!
}
```

**Fix**: Increment first, then use new frequency
```java
int f = freq.getOrDefault(val, 0) + 1;
freq.put(val, f);
group.computeIfAbsent(f, k -> new Stack<>()).push(val);
```

### ❌ **MISTAKE 7: Initializing maxFreq to -1 or Not 0**
```java
// WRONG - starts at -1
public FreqStack() {
    freq = new HashMap<>();
    group = new HashMap<>();
    maxFreq = -1;  // Wrong!
}
```

**Why wrong**: First push won't update maxFreq correctly

**Fix**: Initialize to 0
```java
maxFreq = 0;
```

---

## Complexity Analysis

### Time Complexity

**Push: O(1)**
| Operation | Time |
|-----------|------|
| freq.getOrDefault() | O(1) |
| freq.put() | O(1) |
| group.computeIfAbsent() | O(1) amortized |
| stack.push() | O(1) |
| Math.max() | O(1) |
| **Total** | **O(1)** |

**Pop: O(1)**
| Operation | Time |
|-----------|------|
| group.get() | O(1) |
| stack.pop() | O(1) |
| freq.get() | O(1) |
| freq.put() | O(1) |
| stack.isEmpty() | O(1) |
| **Total** | **O(1)** |

**Time analysis**:
```
All operations use:
  - HashMap get/put: O(1) average
  - Stack push/pop: O(1)
  - Integer comparison: O(1)

No loops, no scanning
Pure O(1) operations!

For 20,000 operations: ~20,000 atomic operations
Very fast!
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| freq map | O(k) | k = number of distinct values |
| group map | O(n) | Total n elements across all stacks |
| maxFreq | O(1) | Single integer |
| **Total** | **O(n)** | Linear in pushed elements |

**Space analysis**:
```
freq map: one entry per distinct value
  Worst case: all values distinct → O(n)
  Best case: all values same → O(1)

group map: each value appears in multiple groups
  Each push adds one entry to a stack
  Total entries = number of pushes = n
  
Example: push(5) three times
  group[1]: [5]
  group[2]: [5]
  group[3]: [5]
  Total: 3 entries for 3 pushes

Space: O(n)
```

---

## Visualization

### Complete Example Walkthrough

**Operations:** `push(5), push(7), push(5), push(7), push(4), push(5), pop(), pop(), pop(), pop()`

---

**Initial State:**
```
freq: {}
group: {}
maxFreq: 0
```

---

**Operation 1: push(5)**
```
Action: First time pushing 5
  1. f = freq.getOrDefault(5, 0) + 1 = 1
  2. freq.put(5, 1)
  3. group[1].push(5)
  4. maxFreq = max(0, 1) = 1

State:
  freq: {5: 1}
  group: {1: [5]}
  maxFreq: 1
```

---

**Operation 2: push(7)**
```
Action: First time pushing 7
  1. f = 1
  2. freq: {5: 1, 7: 1}
  3. group[1].push(7)
  4. maxFreq = max(1, 1) = 1

State:
  freq: {5: 1, 7: 1}
  group: {1: [5, 7]}
  maxFreq: 1
```

---

**Operation 3: push(5)**
```
Action: Second time pushing 5
  1. f = freq[5] + 1 = 2
  2. freq: {5: 2, 7: 1}
  3. group[2].push(5)
  4. maxFreq = max(1, 2) = 2

State:
  freq: {5: 2, 7: 1}
  group: {1: [5, 7], 2: [5]}
  maxFreq: 2
  
Note: 5 now in BOTH group[1] and group[2]!
```

---

**Operation 4: push(7)**
```
Action: Second time pushing 7
  1. f = 2
  2. freq: {5: 2, 7: 2}
  3. group[2].push(7)
  4. maxFreq = max(2, 2) = 2

State:
  freq: {5: 2, 7: 2}
  group: {1: [5, 7], 2: [5, 7]}
  maxFreq: 2
```

---

**Operation 5: push(4)**
```
Action: First time pushing 4
  1. f = 1
  2. freq: {5: 2, 7: 2, 4: 1}
  3. group[1].push(4)
  4. maxFreq = max(2, 1) = 2

State:
  freq: {5: 2, 7: 2, 4: 1}
  group: {1: [5, 7, 4], 2: [5, 7]}
  maxFreq: 2
```

---

**Operation 6: push(5)**
```
Action: Third time pushing 5
  1. f = 3
  2. freq: {5: 3, 7: 2, 4: 1}
  3. group[3].push(5)
  4. maxFreq = max(2, 3) = 3

State:
  freq: {5: 3, 7: 2, 4: 1}
  group: {1: [5, 7, 4], 2: [5, 7], 3: [5]}
  maxFreq: 3
  
Now 5 appears in group[1], group[2], AND group[3]!
```

---

**Operation 7: pop()**
```
Action: Pop most frequent (freq 3)
  1. val = group[3].pop() = 5
  2. freq[5]-- = 2
  3. group[3].isEmpty()? Yes! maxFreq-- = 2
  4. Return 5

State:
  freq: {5: 2, 7: 2, 4: 1}
  group: {1: [5, 7, 4], 2: [5, 7], 3: []}
  maxFreq: 2
  
Return: 5
```

---

**Operation 8: pop()**
```
Action: Pop most frequent (freq 2, tie between 5 and 7)
  1. val = group[2].pop() = 7 (most recent at freq 2)
  2. freq[7]-- = 1
  3. group[2].isEmpty()? No (still has [5])
  4. Return 7

State:
  freq: {5: 2, 7: 1, 4: 1}
  group: {1: [5, 7, 4], 2: [5], 3: []}
  maxFreq: 2
  
Return: 7
```

---

**Operation 9: pop()**
```
Action: Pop most frequent (freq 2, only 5)
  1. val = group[2].pop() = 5
  2. freq[5]-- = 1
  3. group[2].isEmpty()? Yes! maxFreq-- = 1
  4. Return 5

State:
  freq: {5: 1, 7: 1, 4: 1}
  group: {1: [5, 7, 4], 2: [], 3: []}
  maxFreq: 1
  
Return: 5
```

---

**Operation 10: pop()**
```
Action: Pop most frequent (freq 1, all tied)
  1. val = group[1].pop() = 4 (most recent at freq 1)
  2. freq[4]-- = 0
  3. group[1].isEmpty()? No (still has [5, 7])
  4. Return 4

State:
  freq: {5: 1, 7: 1, 4: 0}
  group: {1: [5, 7], 2: [], 3: []}
  maxFreq: 1
  
Return: 4
```

---

### Frequency Level Visualization

```
After all pushes: [5, 7, 5, 7, 4, 5]

Frequency levels:
┌──────────────────────────┐
│ freq = 3:  [5]          │ ← maxFreq (pop this first)
├──────────────────────────┤
│ freq = 2:  [5, 7]       │ ← second highest
├──────────────────────────┤
│ freq = 1:  [5, 7, 4]    │ ← lowest
└──────────────────────────┘

Each level is a stack (right = top/most recent)

Pop order:
  1. Pop from freq=3: get 5
  2. maxFreq becomes 2
  3. Pop from freq=2: get 7 (top of that stack)
  4. Still at freq=2: pop 5
  5. maxFreq becomes 1
  6. Pop from freq=1: get 4 (top of that stack)
```

---

## Comparison of Approaches

| Approach | Push | Pop | Space | Clarity | Recommended |
|----------|------|-----|-------|---------|-------------|
| **HashMap + Stack** | **O(1)** | **O(1)** | **O(n)** | **Excellent ✅** | **Yes ✅** |
| HashMap + ArrayList | O(1) | O(1) | O(n) | Excellent | Alternative |
| Naive (scan on pop) | O(1) | O(n) | O(n) | Simple but slow | No ❌ |
| Priority Queue | O(log n) | O(log n) | O(n) | Complex | No ❌ |

**Winner**: **HashMap + Stack Groups** — optimal O(1) for both operations!

---

## Key Takeaways

1. **Group elements by frequency** — key optimization for O(1)
2. **Use stack for each frequency level** — maintains recency order
3. **Track maxFreq** — quick access to highest frequency
4. **Three data structures** — freq map, group map, maxFreq counter
5. **Value can appear in multiple groups** — each push adds to new level
6. **Don't remove from lower levels** — leave old entries (harmless)
7. **Update maxFreq on push** — Math.max(maxFreq, newFreq)
8. **Decrement maxFreq when group empties** — move to next level down
9. **O(1) push and pop** — all operations are direct map/stack access
10. **O(n) space** — linear in number of pushed elements

---

## Interview Tips

**What to say in an interview:**

> "This problem asks me to design a stack that pops the most frequent element, with ties broken by recency. The key insight is to group elements by their frequency level. I'll use three data structures: a freq map to track current frequency of each value, a group map where each frequency level has a stack of values, and a maxFreq counter. When pushing, I increment the frequency, add the value to its new frequency group, and update maxFreq. When popping, I take the most recent value from the maxFreq group, decrement its frequency, and if that group becomes empty I decrement maxFreq. This gives O(1) for both push and pop because all operations are direct HashMap and Stack access. Space complexity is O(n) where n is the number of pushed elements."

**Key points to mention:**
1. **Group by frequency** — core optimization
2. **Three data structures** — freq, group, maxFreq
3. **Stack per frequency** — maintains recency
4. **Value in multiple groups** — each push adds to new level
5. **O(1) operations** — no scanning or sorting
6. **maxFreq tracking** — quick access to max
7. **Decrement when empty** — update maxFreq
8. **O(n) space** — linear in elements

**Common Follow-ups:**
- "Why not use PriorityQueue?" → O(log n), not O(1)
- "Do you remove from lower levels?" → No, leave them (harmless)
- "How do you handle ties?" → Stack in each group gives recency
- "What's the space complexity?" → O(n) for all pushed elements

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Maximum Frequency Stack** | Hard | **HashMap + Stack Groups** | **This problem** |
| LFU Cache | Hard | Frequency-based eviction | Cache with capacity limit |
| Design Twitter | Medium | HashMap + Timeline | Follow/unfollow mechanics |
| Top K Frequent Elements | Medium | Heap/Bucket Sort | Find k elements, not pop |
| First Unique Number | Medium | Queue + HashMap | First unique, not most frequent |

**Pattern Progression**:
1. **Frequency stack** (this problem) — Pop by frequency + recency
2. **LFU cache** — Evict least frequent, capacity constraint
3. **Top K frequent** — Find k elements, static analysis
4. **Design problems** — Custom data structure requirements

---

## Final Pattern Label

✅ **Stack + HashMap (Frequency-Based Multi-Level Organization)**

**Remember:** Use **THREE data structures**: freq map (value→frequency), group map (frequency→stack of values), maxFreq counter. On **push**: increment freq, add to group[newFreq], update maxFreq. On **pop**: get from group[maxFreq], decrement freq, decrement maxFreq if group empty. Values appear in **multiple frequency groups** (each push adds to new level). **Don't remove** from lower levels (harmless). Stack in each group maintains **recency** for tie-breaking. **O(1) push and pop**, **O(n) space**. Group by frequency is the key insight!
