# LFU Cache

## Problem Description

**Difficulty**: Hard

Design and implement a data structure for a **Least Frequently Used (LFU)** cache.

Implement the `LFUCache` class:

- `LFUCache(int capacity)` Initializes the object with the capacity of the data structure.
- `int get(int key)` Gets the value of the key if the key exists in the cache. Otherwise, returns `-1`.
- `void put(int key, int value)` Updates the value of the key if present, or inserts the key if not already present. When the cache reaches its capacity, it should invalidate and remove the **least frequently used** key before inserting a new item.

**For this problem, when there is a tie (i.e., two or more keys with the same frequency), the least recently used key would be invalidated.**

To determine the least frequently used key, a **use counter** is maintained for each key in the cache. The key with the smallest use counter is the least frequently used key.

When a key is first inserted into the cache, its use counter is set to `1` (due to the `put` operation). The use counter for a key in the cache is incremented when either a `get` or `put` operation is called on it.

**The functions `get` and `put` must each run in O(1) average time complexity.**

**Key Concepts:**
- **Frequency-Based Eviction**: Remove least frequently used item when at capacity
- **Tie-Breaker**: LRU among keys with same frequency
- **Use Counter**: Track access frequency for each key
- **O(1) Operations**: Both get and put must be constant time
- **Frequency Increment**: Every access increases frequency

**Visual Example:**
```
Cache capacity = 2

put(1,10): cache=[1], freq(1)=1
put(2,20): cache=[2,1], freq(2)=1, freq(1)=1

get(1): cache=[1,2], freq(1)=2, freq(2)=1
        (1 has higher frequency now)

put(3,30): Evict key 2 (freq=1, lowest)
          cache=[3,1], freq(3)=1, freq(1)=2

get(3): cache=[3,1], freq(3)=2, freq(1)=2

put(4,40): Tie! Both have freq=2
          Evict key 1 (LRU among freq=2)
          cache=[4,3], freq(4)=1, freq(3)=2
```

---

## Examples

### Example 1 (Main Example):
```
Input:
["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
[[2], [1,1], [2,2], [1], [3,3], [2], [3], [4,4], [1], [3], [4]]

Output:
[null, null, null, 1, null, -1, 3, null, -1, 3, 4]

Explanation:
// cnt(x) = the use counter for key x
// cache=[] shows last used order for tiebreakers (leftmost = most recent)

LFUCache lfu = new LFUCache(2);
lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
lfu.get(1);      // return 1
                 // cache=[1,2], cnt(2)=1, cnt(1)=2
lfu.put(3, 3);   // 2 is the LFU key (cnt(2)=1 is smallest), invalidate 2
                 // cache=[3,1], cnt(3)=1, cnt(1)=2
lfu.get(2);      // return -1 (not found, was evicted)
lfu.get(3);      // return 3
                 // cache=[3,1], cnt(3)=2, cnt(1)=2
lfu.put(4, 4);   // Both 1 and 3 have cnt=2, but 1 is LRU, invalidate 1
                 // cache=[4,3], cnt(4)=1, cnt(3)=2
lfu.get(1);      // return -1 (not found, was evicted)
lfu.get(3);      // return 3
                 // cache=[3,4], cnt(4)=1, cnt(3)=3
lfu.get(4);      // return 4
                 // cache=[4,3], cnt(4)=2, cnt(3)=3
```

### Example 2 (Single Capacity):
```
Input:
["LFUCache", "put", "put", "get", "get"]
[[1], [1,100], [2,200], [1], [2]]

Output:
[null, null, null, -1, 200]

Explanation:
LFUCache lfu = new LFUCache(1);
lfu.put(1, 100);  // cache=[1], cnt(1)=1
lfu.put(2, 200);  // cache=[2], cnt(2)=1, evicted 1 (both cnt=1, 1 is LRU)
lfu.get(1);       // return -1 (evicted)
lfu.get(2);       // return 200, cnt(2)=2
```

### Example 3 (Frequency Builds Up):
```
Input:
["LFUCache", "put", "get", "get", "get"]
[[2], [1,10], [1], [1], [1]]

Output:
[null, null, 10, 10, 10]

Explanation:
LFUCache lfu = new LFUCache(2);
lfu.put(1, 10);   // cache=[1], cnt(1)=1
lfu.get(1);       // return 10, cnt(1)=2
lfu.get(1);       // return 10, cnt(1)=3
lfu.get(1);       // return 10, cnt(1)=4
// Frequency keeps increasing with each access
```

### Example 4 (Update Increments Frequency):
```
Input:
["LFUCache", "put", "put", "put", "get"]
[[2], [1,10], [2,20], [1,15], [1]]

Output:
[null, null, null, null, 15]

Explanation:
LFUCache lfu = new LFUCache(2);
lfu.put(1, 10);   // cache=[1], cnt(1)=1
lfu.put(2, 20);   // cache=[2,1], cnt(2)=1, cnt(1)=1
lfu.put(1, 15);   // cache=[1,2], cnt(1)=2 (updated value AND frequency)
lfu.get(1);       // return 15
```

### Example 5 (Multiple Frequency Levels):
```
Input:
["LFUCache", "put", "put", "get", "get", "put"]
[[2], [1,10], [2,20], [1], [1], [3,30]]

Output:
[null, null, null, 10, 10, null]

Explanation:
lfu.put(1, 10);   // cnt(1)=1
lfu.put(2, 20);   // cnt(2)=1, cnt(1)=1
lfu.get(1);       // cnt(1)=2, cnt(2)=1
lfu.get(1);       // cnt(1)=3, cnt(2)=1
lfu.put(3, 30);   // Evict 2 (cnt(2)=1 is minimum)
                  // cache=[3,1], cnt(3)=1, cnt(1)=3
```

### Example 6 (LRU Tie-Breaker):
```
Input:
["LFUCache", "put", "put", "put", "get", "get", "put"]
[[3], [1,10], [2,20], [3,30], [2], [3], [4,40]]

Output:
[null, null, null, null, 20, 30, null]

Explanation:
All start with cnt=1
After gets: cnt(2)=2, cnt(3)=2, cnt(1)=1
put(4,40) at capacity: evict 1 (cnt=1 is minimum)
```

### Example 7 (All Same Frequency):
```
Input:
["LFUCache", "put", "put", "put", "put"]
[[2], [1,10], [2,20], [3,30], [4,40]]

Output:
[null, null, null, null, null]

Explanation:
All keys have cnt=1
Evictions follow LRU order:
- put(3,30): evict 1 (oldest with cnt=1)
- put(4,40): evict 2 (oldest with cnt=1)
```

### Example 8 (Zero Values):
```
Input:
["LFUCache", "put", "put", "get"]
[[2], [0,0], [1,1000], [0]]

Output:
[null, null, null, 0]

Explanation:
Keys and values can be 0
0 is valid value (not "not found")
-1 is the "not found" sentinel
```

### Example 9 (Rapid Frequency Changes):
```
Input:
["LFUCache", "put", "put", "get", "put", "get", "put"]
[[2], [1,10], [2,20], [1], [2], [2], [3,30]]

Output:
[null, null, null, 10, null, 20, null]

Explanation:
put(1,10): cnt(1)=1
put(2,20): cnt(2)=1
get(1):    cnt(1)=2
put(2,20): cnt(2)=2 (update increments freq)
get(2):    cnt(2)=3
put(3,30): No keys with cnt=1, no cnt=2, evict based on remaining
```

### Example 10 (Large Capacity):
```
Input:
["LFUCache", "put", "put", "put", "get", "get", "get"]
[[100], [1,10], [2,20], [3,30], [1], [2], [3]]

Output:
[null, null, null, null, 10, 20, 30]

Explanation:
Large capacity, no evictions
All operations succeed
Frequencies: cnt(1)=2, cnt(2)=2, cnt(3)=2
```

---

## Constraints
- `1 <= capacity <= 10000`
- `0 <= key <= 100000`
- `0 <= value <= 1000000000`
- At most `200000` calls to `get` and `put`
- Must implement with **O(1)** time for both get and put

**Recommended Complexity**: 
- Time: O(1) for both get and put operations
- Space: O(capacity) for storing cache entries

---

## Pattern Recognition

**Primary Pattern**: **HashMap + HashMap of Doubly Linked Lists + Min Frequency Tracking**

**Why This Pattern?**
- Need **O(1) lookup by key** → HashMap (key → node)
- Need **O(1) find LFU key** → Track min frequency
- Need **O(1) LRU within frequency** → Doubly linked list per frequency
- Need **O(1) update frequency** → Move between frequency lists
- **Three-level structure** for complete solution

**Key Insight**: LFU is More Complex Than LRU
```
LRU Cache:
  - Single dimension: recency
  - One doubly linked list
  - HashMap + Doubly LL

LFU Cache:
  - Two dimensions: frequency AND recency
  - Multiple doubly linked lists (one per frequency)
  - HashMap + HashMap of Doubly LLs + minFreq tracking
  
More complex! But still O(1) with proper structure! ✓
```

**The Three Key Data Structures**:

```
1. HashMap<Integer, Node> cache
   - Key → Node mapping
   - O(1) lookup by key
   
   Node contains:
     - key (for eviction)
     - value (data)
     - frequency (current freq)

2. HashMap<Integer, DoublyLinkedList> freqMap
   - Frequency → List of nodes with that frequency
   - Each list maintains LRU order
   - O(1) access to any frequency list
   
   DoublyLinkedList:
     - Dummy head and tail
     - Nodes in LRU order (head.next = LRU, tail.prev = MRU)

3. int minFreq
   - Track minimum frequency in cache
   - O(1) access to eviction candidate
   - Update when needed
```

**Visual: Frequency-Based Organization**
```
minFreq = 1

cache (HashMap):
  1 → Node(1,10,freq=2)
  2 → Node(2,20,freq=1)
  3 → Node(3,30,freq=1)

freqMap (HashMap of Doubly Lists):
  1 → Head ↔ [2:20] ↔ [3:30] ↔ Tail
      LRU               MRU
  
  2 → Head ↔ [1:10] ↔ Tail
      (only one node)

When evicting:
  - Use minFreq (=1) to find frequency list
  - Evict head.next from freq=1 list (LRU)
  - That's key 2!

O(1) eviction! ✓
```

**Why HashMap of Lists (Not Just Lists)**:
```
Need O(1) access to specific frequency list

Array approach:
  DoublyLinkedList[] freqLists = new DoublyLinkedList[maxFreq];
  Problem: Don't know maxFreq in advance! ❌
  Problem: Sparse array (frequencies not continuous) ❌
  
HashMap approach:
  Map<Integer, DoublyLinkedList> freqMap
  Dynamic: create lists as needed ✓
  Sparse: only store existing frequencies ✓
  O(1) access: freqMap.get(freq) ✓
```

**Core Operations**:

1. **get(key)**:
```
if key not in cache:
  return -1

node = cache.get(key)
updateFrequency(node)  // Move to freq+1 list
return node.value

Time: O(1)
```

2. **put(key, value)**:
```
if capacity == 0:
  return

if key in cache:
  // Update existing
  node = cache.get(key)
  node.value = value
  updateFrequency(node)
else:
  // Insert new
  if cache.size == capacity:
    // Evict LFU (with LRU tie-breaker)
    evict()
  
  newNode = new Node(key, value, freq=1)
  cache.put(key, newNode)
  add newNode to freqMap[1]
  minFreq = 1

Time: O(1)
```

3. **updateFrequency(node)**:
```
oldFreq = node.freq
remove node from freqMap[oldFreq]

if freqMap[oldFreq] is empty:
  remove freqMap[oldFreq]
  if minFreq == oldFreq:
    minFreq++  // Only increment if oldFreq was min

node.freq++
add node to freqMap[node.freq] (at tail = MRU)

Time: O(1)
```

4. **evict()**:
```
// Evict LFU, with LRU tie-breaker
list = freqMap[minFreq]
lfu = list.head.next  // LRU node in minFreq list

remove lfu from list
if list is empty:
  remove freqMap[minFreq]

cache.remove(lfu.key)

Time: O(1)
```

**Why MinFreq Tracking Works**:
```
Key insight: minFreq can only increase by 1 or reset to 1

When does minFreq change?

1. New insertion: minFreq = 1 ✓

2. Update frequency of existing node:
   if oldFreq == minFreq AND freqMap[oldFreq] becomes empty:
     minFreq++  ✓
   
   Why only increment by 1?
     - We just moved a node from freq=k to freq=k+1
     - If freq=k becomes empty, next minimum is k+1
     - Can't skip frequencies! ✓

3. Eviction: minFreq stays same or will be reset on next insert

Always O(1) to maintain! ✓
```

**Visual: Frequency Update Example**
```
Initial state:
  minFreq = 1
  freqMap:
    1 → Head ↔ [2:20] ↔ Tail
    2 → Head ↔ [1:10] ↔ [3:30] ↔ Tail

get(2):
  Remove [2:20] from freq=1 list:
    1 → Head ↔ Tail (empty!)
  
  freqMap[1] is empty, remove it
  minFreq was 1, increment to 2 ✓
  
  Add [2:20] to freq=2 list (at tail):
    2 → Head ↔ [1:10] ↔ [3:30] ↔ [2:20] ↔ Tail
  
  node(2).freq = 2

Final state:
  minFreq = 2
  freqMap:
    2 → Head ↔ [1:10] ↔ [3:30] ↔ [2:20] ↔ Tail
        LRU                            MRU
```

**Alternative Approaches (Why They Don't Work)**:

1. **Single Priority Queue (Heap)**:
```
Store (frequency, timestamp, key, value)
  get: O(log n) to update priority ❌
  put: O(log n) to add/update ❌
  
Not O(1)!
```

2. **HashMap + Single Doubly List (like LRU)**:
```
Can't track frequency efficiently
Finding LFU: O(n) scan ❌
  
Doesn't meet requirements!
```

3. **Sorted Map (TreeMap)**:
```
Store by frequency
  get: O(log n) to update ❌
  put: O(log n) to add ❌
  
Not O(1)!
```

4. **Array of Lists by Frequency**:
```
DoublyLinkedList[] freqLists
  Need max frequency in advance ❌
  Sparse: waste space ❌
  
HashMap of lists is better!
```

**Related Patterns**:
1. **LRU Cache** — Least Recently Used eviction (simpler)
2. **LFU Cache** — This problem (more complex)
3. **LRU-K Cache** — Track k most recent accesses
4. **Hybrid Caching** — Combine multiple policies

---

## Algorithm & Approach

### Core Insight

**Why Triple Structure Works:**
```
Key observations:
  1. cache (HashMap) provides O(1) key lookup
  2. freqMap (HashMap of Lists) provides O(1) frequency list access
  3. Each list (Doubly LL) provides O(1) LRU order maintenance
  4. minFreq provides O(1) eviction target
  5. All structures stay synchronized
  6. All operations O(1)! ✓
```

**The Optimal Strategy**:
```
Data structures:
  - HashMap<Integer, Node> cache: key → node
  - HashMap<Integer, DoublyLinkedList> freqMap: freq → list
  - int minFreq: track minimum frequency
  - Node: key, value, freq
  - DoublyLinkedList: dummy head/tail, nodes in LRU order

Operations:
  - get: lookup, update frequency
  - put: lookup, update/insert, evict if needed
  - updateFreq: move node between frequency lists
  - evict: remove LRU from minFreq list
  - All O(1) with proper synchronization
```

### Step-by-Step Algorithm

---

#### **Approach: Triple HashMap Structure - OPTIMAL**

**Core Idea**:
- cache HashMap for O(1) key → node lookup
- freqMap HashMap for O(1) frequency → list access
- Doubly linked lists for O(1) LRU order per frequency
- minFreq for O(1) eviction target identification
- Synchronize all structures on every operation

**Data Structures**
```java
class Node {
    int key;
    int value;
    int freq;
    Node prev;
    Node next;
    
    Node(int key, int value) {
        this.key = key;
        this.value = value;
        this.freq = 1;  // Start at frequency 1
    }
}

class DoublyLinkedList {
    Node head;  // Dummy
    Node tail;  // Dummy
    int size;
    
    DoublyLinkedList() {
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
        size = 0;
    }
    
    void addToTail(Node node) { /* ... */ }
    void remove(Node node) { /* ... */ }
    Node removeHead() { /* return head.next and remove */ }
    boolean isEmpty() { return size == 0; }
}

class LFUCache {
    private Map<Integer, Node> cache;
    private Map<Integer, DoublyLinkedList> freqMap;
    private int capacity;
    private int minFreq;
}
```

**Constructor**
```java
LFUCache(int capacity):
    this.capacity = capacity
    this.minFreq = 0
    cache = new HashMap<>()
    freqMap = new HashMap<>()
```

**get(key)**
```java
get(int key):
    if key not in cache:
        return -1
    
    node = cache.get(key)
    updateFrequency(node)
    return node.value
```

**put(key, value)**
```java
put(int key, int value):
    if capacity == 0:
        return
    
    if key in cache:
        // Update existing
        node = cache.get(key)
        node.value = value
        updateFrequency(node)
    else:
        // Insert new
        if cache.size() == capacity:
            evict()
        
        newNode = new Node(key, value)
        cache.put(key, newNode)
        
        // Add to frequency 1 list
        freqMap.putIfAbsent(1, new DoublyLinkedList())
        freqMap.get(1).addToTail(newNode)
        
        minFreq = 1
```

**updateFrequency(node)**
```java
updateFrequency(Node node):
    oldFreq = node.freq
    
    // Remove from old frequency list
    list = freqMap.get(oldFreq)
    list.remove(node)
    
    // If old frequency list is empty, clean up
    if list.isEmpty():
        freqMap.remove(oldFreq)
        
        // Update minFreq if needed
        if minFreq == oldFreq:
            minFreq++
    
    // Increment frequency
    node.freq++
    
    // Add to new frequency list
    freqMap.putIfAbsent(node.freq, new DoublyLinkedList())
    freqMap.get(node.freq).addToTail(node)
```

**evict()**
```java
evict():
    // Get list with minimum frequency
    list = freqMap.get(minFreq)
    
    // Remove LRU node (head.next)
    nodeToRemove = list.removeHead()
    
    // If list becomes empty, remove from freqMap
    if list.isEmpty():
        freqMap.remove(minFreq)
    
    // Remove from cache
    cache.remove(nodeToRemove.key)
```

**Complete Code Implementation**
```java
class LFUCache {
    
    // Node class
    private class Node {
        int key;
        int value;
        int freq;
        Node prev;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }
    
    // Doubly linked list class
    private class DoublyLinkedList {
        Node head;
        Node tail;
        int size;
        
        DoublyLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }
        
        void addToTail(Node node) {
            node.prev = tail.prev;
            node.next = tail;
            tail.prev.next = node;
            tail.prev = node;
            size++;
        }
        
        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }
        
        Node removeHead() {
            if (size == 0) return null;
            Node node = head.next;
            remove(node);
            return node;
        }
        
        boolean isEmpty() {
            return size == 0;
        }
    }
    
    private Map<Integer, Node> cache;
    private Map<Integer, DoublyLinkedList> freqMap;
    private int capacity;
    private int minFreq;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
        this.cache = new HashMap<>();
        this.freqMap = new HashMap<>();
    }
    
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        
        Node node = cache.get(key);
        updateFrequency(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        
        if (cache.containsKey(key)) {
            // Update existing key
            Node node = cache.get(key);
            node.value = value;
            updateFrequency(node);
        } else {
            // Insert new key
            if (cache.size() == capacity) {
                evict();
            }
            
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            
            // Add to frequency 1 list
            freqMap.putIfAbsent(1, new DoublyLinkedList());
            freqMap.get(1).addToTail(newNode);
            
            minFreq = 1;
        }
    }
    
    private void updateFrequency(Node node) {
        int oldFreq = node.freq;
        
        // Remove from old frequency list
        DoublyLinkedList list = freqMap.get(oldFreq);
        list.remove(node);
        
        // If old frequency list is empty, clean up
        if (list.isEmpty()) {
            freqMap.remove(oldFreq);
            
            // Update minFreq if needed
            if (minFreq == oldFreq) {
                minFreq++;
            }
        }
        
        // Increment frequency
        node.freq++;
        
        // Add to new frequency list
        freqMap.putIfAbsent(node.freq, new DoublyLinkedList());
        freqMap.get(node.freq).addToTail(node);
    }
    
    private void evict() {
        // Get list with minimum frequency
        DoublyLinkedList list = freqMap.get(minFreq);
        
        // Remove LRU node (head.next)
        Node nodeToRemove = list.removeHead();
        
        // If list becomes empty, remove from freqMap
        if (list.isEmpty()) {
            freqMap.remove(minFreq);
        }
        
        // Remove from cache
        cache.remove(nodeToRemove.key);
    }
}
```

**Example Walkthrough**

Input: `capacity = 2`, operations: put(1,1), put(2,2), get(1), put(3,3), get(2), get(3)

```
Initialize:
  capacity = 2, minFreq = 0
  cache = {}
  freqMap = {}
```

**Operation 1: put(1, 1)**
```
Check: key 1 in cache? No → insert new

Check capacity: size (0) < capacity (2) ✓

Create Node(1, 1, freq=1)
cache.put(1, node)

Add to freqMap[1]:
  Create new DoublyLinkedList
  freqMap[1] = Head ↔ [1:1,f=1] ↔ Tail

minFreq = 1

State:
  cache = {1 → Node(1,1,f=1)}
  freqMap = {1 → [Head ↔ [1:1,f=1] ↔ Tail]}
  minFreq = 1
```

**Operation 2: put(2, 2)**
```
Insert Node(2, 2, freq=1)

Add to freqMap[1]:
  freqMap[1] = Head ↔ [1:1,f=1] ↔ [2:2,f=1] ↔ Tail
               LRU                        MRU

State:
  cache = {1 → Node(1,1,f=1), 2 → Node(2,2,f=1)}
  freqMap = {1 → [Head ↔ [1:1,f=1] ↔ [2:2,f=1] ↔ Tail]}
  minFreq = 1
  size = 2 (at capacity!)
```

**Operation 3: get(1)**
```
Check: key 1 in cache? Yes

node = Node(1,1,f=1)

updateFrequency(node):
  oldFreq = 1
  
  Remove from freqMap[1]:
    freqMap[1] = Head ↔ [2:2,f=1] ↔ Tail
  
  freqMap[1] not empty, don't remove
  minFreq still 1
  
  node.freq = 2
  
  Add to freqMap[2]:
    Create new list
    freqMap[2] = Head ↔ [1:1,f=2] ↔ Tail

Return: 1 ✓

State:
  cache = {1 → Node(1,1,f=2), 2 → Node(2,2,f=1)}
  freqMap = {
    1 → [Head ↔ [2:2,f=1] ↔ Tail]
    2 → [Head ↔ [1:1,f=2] ↔ Tail]
  }
  minFreq = 1
```

**Operation 4: put(3, 3)**
```
Check: key 3 in cache? No → insert new

Check capacity: size (2) == capacity (2) → EVICT!

evict():
  list = freqMap[minFreq=1]
  list = Head ↔ [2:2,f=1] ↔ Tail
  
  nodeToRemove = list.removeHead() = Node(2,2,f=1)
  
  list becomes empty:
    freqMap.remove(1)
  
  cache.remove(2)

Insert new:
  newNode = Node(3, 3, freq=1)
  cache.put(3, newNode)
  
  Add to freqMap[1]:
    Create new list
    freqMap[1] = Head ↔ [3:3,f=1] ↔ Tail
  
  minFreq = 1

State:
  cache = {1 → Node(1,1,f=2), 3 → Node(3,3,f=1)}
  freqMap = {
    1 → [Head ↔ [3:3,f=1] ↔ Tail]
    2 → [Head ↔ [1:1,f=2] ↔ Tail]
  }
  minFreq = 1
  Key 2 evicted! ✓
```

**Operation 5: get(2)**
```
Check: key 2 in cache? No

Return: -1 ✓
```

**Operation 6: get(3)**
```
Check: key 3 in cache? Yes

node = Node(3,3,f=1)

updateFrequency(node):
  oldFreq = 1
  
  Remove from freqMap[1]:
    freqMap[1] becomes empty
  
  freqMap[1] is empty:
    freqMap.remove(1)
    minFreq was 1, increment to 2 ✓
  
  node.freq = 2
  
  Add to freqMap[2]:
    freqMap[2] = Head ↔ [1:1,f=2] ↔ [3:3,f=2] ↔ Tail
                 LRU                        MRU

Return: 3 ✓

State:
  cache = {1 → Node(1,1,f=2), 3 → Node(3,3,f=2)}
  freqMap = {
    2 → [Head ↔ [1:1,f=2] ↔ [3:3,f=2] ↔ Tail]
  }
  minFreq = 2
```

**Complexity Analysis**
- **Constructor**: O(1) — Initialize structures
- **get**: O(1) — HashMap lookup + list operations
- **put**: O(1) — HashMap operations + list operations

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | get Time | put Time | Space | Complexity | Recommended |
|----------|----------|----------|-------|------------|-------------|
| **HashMap + Freq Lists** | **O(1)** | **O(1)** | **O(n)** | **High** | **Yes ✅** |
| HashMap + Single PQ | O(log n) | O(log n) | O(n) | Medium | No |
| HashMap + TreeMap | O(log n) | O(log n) | O(n) | Medium | No |
| HashMap + Array of Lists | O(1) | O(1) | O(n·maxF) | High | No (space) |
| Scan for Min | O(n) | O(n) | O(n) | Low | No |

**Winner**: **HashMap + HashMap of Doubly Linked Lists** — only solution with O(1) operations!

### Why Cache HashMap is Essential

```
Need O(1) lookup by key

Without HashMap:
  Must search through frequency lists: O(n) ❌
  Can't meet requirement
  
With HashMap:
  Direct access to node: O(1) ✓
  Store key → node reference
  Instant lookup
```

### Why FreqMap (HashMap of Lists)

```
Need O(1) access to specific frequency list

Without freqMap:
  To find freq=k list: search all lists O(n) ❌
  
With freqMap:
  freqMap.get(k) gives list directly: O(1) ✓
  
Dynamic:
  Create lists as needed
  Remove when empty
  No wasted space ✓
```

### Why Doubly Linked List Per Frequency

```
Within each frequency, need LRU order

Array/ArrayList:
  Move to end: O(n) (shift elements) ❌
  Remove: O(n) (shift elements) ❌
  
Doubly Linked List:
  Remove node: O(1) (have reference) ✓
  Add to tail: O(1) ✓
  LRU at head, MRU at tail ✓
  
Perfect for order maintenance!
```

### Why Track MinFreq

```
Need O(1) eviction

Without minFreq:
  Find minimum frequency: O(n) scan ❌
  
With minFreq:
  Direct access: O(1) ✓
  
Maintenance is easy:
  - New insert: minFreq = 1
  - Update: increment only if current minFreq list becomes empty
  - Always O(1) to maintain! ✓
```

### Why Store Frequency in Node

```
During updateFrequency:
  Need to know current frequency
  To remove from correct list
  
Without freq in node:
  How to find which list? ❌
  Would need reverse lookup: O(n)
  
With freq in node:
  node.freq tells us directly: O(1) ✓
```

### Why Dummy Nodes in Lists

```
Without dummies:
  Empty list: head = tail = null
  Single node: head = tail = node
  Edge cases everywhere ❌

With dummies:
  head and tail always exist
  head.next and tail.prev always valid
  Empty: head ↔ tail
  Consistent code! ✓
```

### Why This is Optimal

```
Time complexity:
  get: O(1) - cache lookup + list ops
  put: O(1) - cache lookup + list ops + evict
  updateFreq: O(1) - remove + add
  evict: O(1) - minFreq gives target directly
  All operations constant! ✓

Space complexity:
  cache: O(capacity)
  freqMap: O(capacity) total nodes across all lists
  Lists: O(number of distinct frequencies)
  Total: O(capacity) = O(n)
  Minimal! ✓

Meets all requirements! ✓
```

---

## Critical Edge Cases & Gotchas

### 1. **Capacity 0**
```java
LFUCache cache = new LFUCache(0);
cache.put(1, 10);  // Do nothing (no capacity)
cache.get(1);      // -1 (nothing stored)
// Must handle capacity 0 gracefully
```

### 2. **Update Increments Frequency**
```java
cache.put(1, 10);  // freq(1) = 1
cache.put(1, 20);  // freq(1) = 2 (update still increments!)
// Update is an access, so frequency increases
```

### 3. **Get Increments Frequency**
```java
cache.put(1, 10);  // freq(1) = 1
cache.get(1);      // freq(1) = 2
cache.get(1);      // freq(1) = 3
// Every get increases frequency
```

### 4. **LRU Tie-Breaker**
```java
cache.put(1, 10);  // freq(1) = 1
cache.put(2, 20);  // freq(2) = 1
get(1);            // freq(1) = 2
get(2);            // freq(2) = 2
// Both have freq=2
put(3, 30);        // Evict 1 (LRU among freq=2)
```

### 5. **MinFreq Update on Empty List**
```java
// Only node with freq=1
cache.put(1, 10);  // freq(1) = 1, minFreq = 1
get(1);            // freq(1) = 2
// freq=1 list is empty, minFreq = 2! ✓
```

### 6. **MinFreq Reset on New Insert**
```java
// All nodes have high frequency
cache with freq=5 nodes
put(new key);      // freq=1, minFreq = 1! ✓
```

### 7. **Single Capacity**
```java
LFUCache cache = new LFUCache(1);
cache.put(1, 10);  // {1=10}
cache.put(2, 20);  // {2=20}, evicted 1
// Every new key evicts previous
```

### 8. **All Same Frequency**
```java
cache.put(1, 10);  // freq=1
cache.put(2, 20);  // freq=1
cache.put(3, 30);  // freq=1
// All have freq=1, evictions follow LRU order
```

### 9. **Rapid Frequency Buildup**
```java
for (int i = 0; i < 100; i++) {
    cache.get(1);  // freq(1) keeps increasing
}
// Frequency can grow very large
```

### 10. **Zero Key and Value**
```java
cache.put(0, 0);   // Valid
cache.get(0);      // Should return 0, not -1
// 0 is valid, -1 is "not found"
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Incrementing Frequency on put() Update**
```java
// WRONG - update doesn't increment frequency
public void put(int key, int value) {
    if (cache.containsKey(key)) {
        Node node = cache.get(key);
        node.value = value;
        // Missing: updateFrequency(node); ❌
    }
    // ...
}
```

**Why wrong**: Update is an access, must increment frequency!

**Dry run failure:**
```
put(1, 10): freq(1) = 1
put(1, 20): freq(1) should be 2, but stays 1 ❌

Later:
put(2, 20): freq(2) = 1
put(3, 30): Both freq=1, should evict 2 (LRU)
            But 1 also has freq=1 when it shouldn't! ❌
```

**Fix**: Always update frequency on access
```java
if (cache.containsKey(key)) {
    Node node = cache.get(key);
    node.value = value;
    updateFrequency(node);  ✓
}
```

### ❌ **MISTAKE 2: Not Updating MinFreq When Old List Becomes Empty**
```java
// WRONG - minFreq not updated
private void updateFrequency(Node node) {
    int oldFreq = node.freq;
    DoublyLinkedList list = freqMap.get(oldFreq);
    list.remove(node);
    
    if (list.isEmpty()) {
        freqMap.remove(oldFreq);
        // Missing minFreq update! ❌
    }
    
    node.freq++;
    // ...
}
```

**Why wrong**: MinFreq becomes stale!

**Dry run failure:**
```
minFreq = 1
freqMap[1] = [Node(1)]
freqMap[2] = [Node(2)]

get(1):
  Remove from freq=1 list
  List becomes empty
  minFreq still 1 ❌ (should be 2!)

Later eviction:
  freqMap.get(minFreq=1) returns null ❌
  NullPointerException!
```

**Fix**: Update minFreq when needed
```java
if (list.isEmpty()) {
    freqMap.remove(oldFreq);
    if (minFreq == oldFreq) {
        minFreq++;  ✓
    }
}
```

### ❌ **MISTAKE 3: Always Incrementing MinFreq on Update**
```java
// WRONG - always increment minFreq
private void updateFrequency(Node node) {
    int oldFreq = node.freq;
    // ... remove from old list ...
    
    if (list.isEmpty()) {
        freqMap.remove(oldFreq);
        minFreq++;  // WRONG! Always increment ❌
    }
    // ...
}
```

**Why wrong**: Should only increment if oldFreq was the minimum!

**Dry run failure:**
```
minFreq = 1
freqMap[1] = [Node(1)]
freqMap[3] = [Node(2)]

get(2): // freq=3 node
  Remove from freq=3 list
  List becomes empty
  minFreq = 1 + 1 = 2 ❌ (should stay 1!)
  
Now minFreq = 2 but freqMap[1] still has nodes!
Eviction will evict from wrong frequency! ❌
```

**Fix**: Only increment if oldFreq == minFreq
```java
if (list.isEmpty()) {
    freqMap.remove(oldFreq);
    if (minFreq == oldFreq) {  ✓
        minFreq++;
    }
}
```

### ❌ **MISTAKE 4: Not Resetting MinFreq on New Insert**
```java
// WRONG - minFreq not reset
public void put(int key, int value) {
    // ...
    else {
        // Insert new
        if (cache.size() == capacity) {
            evict();
        }
        
        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        freqMap.get(1).addToTail(newNode);
        // Missing: minFreq = 1; ❌
    }
}
```

**Why wrong**: New nodes always have freq=1!

**Dry run failure:**
```
All nodes have freq=5
minFreq = 5

put(new key):
  Node has freq=1
  But minFreq still 5 ❌
  
Next eviction:
  Tries to evict from freq=5 instead of freq=1 ❌
```

**Fix**: Always reset minFreq to 1 on new insert
```java
Node newNode = new Node(key, value);
cache.put(key, newNode);
freqMap.get(1).addToTail(newNode);
minFreq = 1;  ✓
```

### ❌ **MISTAKE 5: Not Removing Empty Lists from FreqMap**
```java
// WRONG - keeping empty lists
private void updateFrequency(Node node) {
    int oldFreq = node.freq;
    DoublyLinkedList list = freqMap.get(oldFreq);
    list.remove(node);
    
    // Missing: if (list.isEmpty()) freqMap.remove(oldFreq); ❌
    
    node.freq++;
    // ...
}
```

**Why wrong**: Memory leak, stale entries!

**Issue:**
```
Empty lists accumulate in freqMap
Waste memory ❌
freqMap.get(oldFreq) might return empty list ❌
Confusing state
```

**Fix**: Remove empty lists
```java
if (list.isEmpty()) {
    freqMap.remove(oldFreq);  ✓
    if (minFreq == oldFreq) {
        minFreq++;
    }
}
```

### ❌ **MISTAKE 6: Wrong Node Structure (No Frequency)**
```java
// WRONG - node doesn't store frequency
private class Node {
    int key;
    int value;
    // Missing: int freq; ❌
    Node prev;
    Node next;
}
```

**Why wrong**: Can't determine which list to remove from!

**Dry run failure:**
```
updateFrequency(node):
  Need to remove from freqMap[node.freq]
  But node.freq doesn't exist! ❌
  
Would need to search all freqMap entries: O(n) ❌
Can't achieve O(1)!
```

**Fix**: Store frequency in node
```java
private class Node {
    int key;
    int value;
    int freq;  ✓
    Node prev;
    Node next;
}
```

### ❌ **MISTAKE 7: Evicting from Wrong End of List**
```java
// WRONG - evicting MRU instead of LRU
private void evict() {
    DoublyLinkedList list = freqMap.get(minFreq);
    Node nodeToRemove = list.tail.prev;  // WRONG! This is MRU ❌
    // ...
}
```

**Why wrong**: Should evict LRU (least recently used)!

**Issue:**
```
List: Head ↔ [1] ↔ [2] ↔ [3] ↔ Tail
      LRU                  MRU

Should evict [1] (LRU) ✓
Code evicts [3] (MRU) ❌

Wrong key evicted!
```

**Fix**: Evict head.next (LRU)
```java
Node nodeToRemove = list.removeHead();  ✓
// or
Node nodeToRemove = list.head.next;
list.remove(nodeToRemove);
```

### ❌ **MISTAKE 8: Not Handling Capacity 0**
```java
// WRONG - no capacity check
public void put(int key, int value) {
    if (cache.containsKey(key)) {
        // ...
    } else {
        // Insert new
        // Missing: if (capacity == 0) return; ❌
        
        if (cache.size() == capacity) {
            evict();
        }
        // ...
    }
}
```

**Why wrong**: Capacity 0 is valid, nothing should be stored!

**Dry run failure:**
```
LFUCache cache = new LFUCache(0);
cache.put(1, 10);

Without check:
  capacity == 0
  cache.size() (0) == capacity (0) ✓
  evict() called, but freqMap is empty ❌
  NullPointerException!
```

**Fix**: Check capacity at start
```java
public void put(int key, int value) {
    if (capacity == 0) {  ✓
        return;
    }
    // ...
}
```

### ❌ **MISTAKE 9: Adding to Head Instead of Tail**
```java
// WRONG - adding to head (LRU position)
freqMap.get(node.freq).addToHead(node);  // ❌
```

**Why wrong**: New/updated nodes should be MRU!

**Issue:**
```
List: Head ↔ [1] ↔ [2] ↔ Tail
      LRU           MRU

Access [2] (should be MRU):
  Add to head: Head ↔ [2] ↔ [1] ↔ Tail ❌
  [2] is now LRU, will be evicted first! ❌

Should add to tail (MRU position) ✓
```

**Fix**: Add to tail
```java
freqMap.get(node.freq).addToTail(node);  ✓
```

### ❌ **MISTAKE 10: Using Single HashMap (No FreqMap)**
```java
// WRONG - only cache, no freqMap
private Map<Integer, Node> cache;
// Missing: Map<Integer, DoublyLinkedList> freqMap; ❌

private void evict() {
    // Need to find node with minFreq
    // Have to scan all nodes: O(n) ❌
    for (Node node : cache.values()) {
        if (node.freq == minFreq) {
            // ...
        }
    }
}
```

**Why wrong**: Can't find frequency list in O(1)!

**Issue:**
```
Without freqMap:
  To find nodes with specific frequency: O(n) scan ❌
  To find LRU among them: more work ❌
  
Can't achieve O(1) eviction!
```

**Fix**: Use freqMap
```java
private Map<Integer, DoublyLinkedList> freqMap;  ✓

private void evict() {
    DoublyLinkedList list = freqMap.get(minFreq);  // O(1) ✓
    Node nodeToRemove = list.removeHead();  // O(1) ✓
    // ...
}
```

---

## Complexity Analysis

### Time Complexity

| Operation | Time | Explanation |
|-----------|------|-------------|
| **Constructor** | **O(1)** | Initialize structures |
| **get(key)** | **O(1)** | HashMap lookup + list operations |
| **put(key, value)** | **O(1)** | HashMap operations + list operations |

**All operations are O(1) amortized constant time! ✓**

**Detailed Analysis:**

**get(key)**:
```
1. cache.containsKey(key): O(1) - HashMap lookup
2. cache.get(key): O(1) - HashMap lookup
3. updateFrequency(node):
   - freqMap.get(oldFreq): O(1)
   - list.remove(node): O(1) - doubly linked list removal
   - list.isEmpty(): O(1)
   - freqMap.remove(): O(1)
   - node.freq++: O(1)
   - freqMap.putIfAbsent(): O(1)
   - freqMap.get(): O(1)
   - list.addToTail(node): O(1)
Total: O(1) ✓
```

**put(key, value)** (update existing):
```
1. cache.containsKey(key): O(1)
2. cache.get(key): O(1)
3. node.value = value: O(1)
4. updateFrequency(node): O(1)
Total: O(1) ✓
```

**put(key, value)** (insert new):
```
1. cache.containsKey(key): O(1)
2. Check capacity: O(1)
3. Evict (if needed):
   - freqMap.get(minFreq): O(1)
   - list.removeHead(): O(1)
   - list.isEmpty(): O(1)
   - freqMap.remove(): O(1)
   - cache.remove(): O(1)
4. Create node: O(1)
5. cache.put(): O(1)
6. freqMap.putIfAbsent(): O(1)
7. freqMap.get(): O(1)
8. list.addToTail(): O(1)
9. minFreq = 1: O(1)
Total: O(1) ✓
```

**Why MinFreq Maintenance is O(1)**:
```
MinFreq only changes in two cases:

1. New insert: minFreq = 1
   - Direct assignment: O(1) ✓

2. Old frequency list becomes empty:
   - Check if minFreq == oldFreq: O(1)
   - Increment minFreq: O(1)
   - Can only increment by 1 (node moved from freq to freq+1)
   - Can't skip frequencies ✓

No scanning needed! ✓
```

### Space Complexity: **O(capacity)**

```
Data structure components:
  - cache HashMap: O(capacity) - one entry per key
  - freqMap HashMap: O(number of distinct frequencies)
  - Doubly linked lists: O(capacity) total nodes across all lists
  - Each node appears once across all frequency lists
  - minFreq: O(1)
  - Other variables: O(1)

Total nodes: capacity (each key stored once)
Total space: O(capacity) = O(n) where n is capacity
```

**Space is optimal!**

```
Must store n entries: O(n) required
Overhead per entry:
  - Node: key, value, freq, prev, next
  - HashMap entries
  - List structure
  
All constant overhead per entry ✓
No duplication (each node in one list only) ✓
Minimal extra space! ✓
```

### Optimal Complexity

```
Time: O(1) for all operations
  Three HashMaps provide O(1) access
  Doubly lists provide O(1) move/delete
  MinFreq provides O(1) eviction target
  Cannot do better than constant time
  Optimal! ✓

Space: O(n) to store n entries
  Must store all entries
  Minimal overhead for tracking frequency and order
  Optimal! ✓

Solution meets all requirements! ✓
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `capacity = 2`, operations: put(1,1), put(2,2), get(1), put(3,3), get(2), get(3), put(4,4)

---

**Initialization:**
```
LFUCache cache = new LFUCache(2);

State:
  capacity = 2
  minFreq = 0
  cache = {}
  freqMap = {}
```

---

**Operation 1: put(1, 1)**
```
Key 1 not in cache → insert new

Not at capacity (size=0 < 2)

Create Node(1, 1, freq=1)
cache.put(1, node)

Add to freqMap[1]:
  Create new DoublyLinkedList
  freqMap[1] = Head ↔ [1:1,f=1] ↔ Tail

minFreq = 1

State:
  cache = {1 → Node(1,1,f=1)}
  freqMap = {
    1 → [Head ↔ [1:1,f=1] ↔ Tail]
  }
  minFreq = 1

Visual:
  Freq 1: [1:1] (LRU/MRU)
```

---

**Operation 2: put(2, 2)**
```
Key 2 not in cache → insert new

Not at capacity (size=1 < 2)

Create Node(2, 2, freq=1)

Add to freqMap[1] at tail:
  freqMap[1] = Head ↔ [1:1,f=1] ↔ [2:2,f=1] ↔ Tail

minFreq = 1

State:
  cache = {1 → Node(1,1,f=1), 2 → Node(2,2,f=1)}
  freqMap = {
    1 → [Head ↔ [1:1,f=1] ↔ [2:2,f=1] ↔ Tail]
        LRU                        MRU
  }
  minFreq = 1
  At capacity!

Visual:
  Freq 1: [1:1] ↔ [2:2]
          LRU    MRU
```

---

**Operation 3: get(1)**
```
Key 1 in cache → updateFrequency

node = Node(1,1,f=1)

updateFrequency(node):
  oldFreq = 1
  
  Remove from freqMap[1]:
    freqMap[1] = Head ↔ [2:2,f=1] ↔ Tail
  
  freqMap[1] not empty, keep it
  minFreq still 1
  
  node.freq = 2
  
  Add to freqMap[2] (create new):
    freqMap[2] = Head ↔ [1:1,f=2] ↔ Tail

Return: 1 ✓

State:
  cache = {1 → Node(1,1,f=2), 2 → Node(2,2,f=1)}
  freqMap = {
    1 → [Head ↔ [2:2,f=1] ↔ Tail]
    2 → [Head ↔ [1:1,f=2] ↔ Tail]
  }
  minFreq = 1

Visual:
  Freq 1: [2:2]
  Freq 2: [1:1]
  
  Key 1 promoted to freq 2! ✓
```

---

**Operation 4: put(3, 3)**
```
Key 3 not in cache → insert new

At capacity (size=2 == 2) → EVICT!

evict():
  list = freqMap[minFreq=1]
  list = Head ↔ [2:2,f=1] ↔ Tail
  
  nodeToRemove = list.removeHead()
  nodeToRemove = Node(2,2,f=1)
  
  freqMap[1] becomes empty:
    freqMap.remove(1)
  
  cache.remove(2)

After eviction:
  cache = {1 → Node(1,1,f=2)}
  freqMap = {
    2 → [Head ↔ [1:1,f=2] ↔ Tail]
  }

Insert new:
  newNode = Node(3, 3, freq=1)
  cache.put(3, newNode)
  
  Create freqMap[1]:
    freqMap[1] = Head ↔ [3:3,f=1] ↔ Tail
  
  minFreq = 1

State:
  cache = {1 → Node(1,1,f=2), 3 → Node(3,3,f=1)}
  freqMap = {
    1 → [Head ↔ [3:3,f=1] ↔ Tail]
    2 → [Head ↔ [1:1,f=2] ↔ Tail]
  }
  minFreq = 1

Visual:
  Freq 1: [3:3]
  Freq 2: [1:1]
  
  Key 2 evicted (LFU)! ✓
```

---

**Operation 5: get(2)**
```
Key 2 not in cache

Return: -1 ✓
```

---

**Operation 6: get(3)**
```
Key 3 in cache → updateFrequency

node = Node(3,3,f=1)

updateFrequency(node):
  oldFreq = 1
  
  Remove from freqMap[1]:
    freqMap[1] becomes empty
  
  freqMap[1] is empty:
    freqMap.remove(1)
    minFreq was 1, increment to 2 ✓
  
  node.freq = 2
  
  Add to freqMap[2] at tail:
    freqMap[2] = Head ↔ [1:1,f=2] ↔ [3:3,f=2] ↔ Tail

Return: 3 ✓

State:
  cache = {1 → Node(1,1,f=2), 3 → Node(3,3,f=2)}
  freqMap = {
    2 → [Head ↔ [1:1,f=2] ↔ [3:3,f=2] ↔ Tail]
        LRU                        MRU
  }
  minFreq = 2

Visual:
  Freq 2: [1:1] ↔ [3:3]
          LRU    MRU
  
  Both keys now have freq 2!
  MinFreq updated to 2! ✓
```

---

**Operation 7: put(4, 4)**
```
Key 4 not in cache → insert new

At capacity (size=2 == 2) → EVICT!

evict():
  list = freqMap[minFreq=2]
  list = Head ↔ [1:1,f=2] ↔ [3:3,f=2] ↔ Tail
  
  nodeToRemove = list.removeHead()
  nodeToRemove = Node(1,1,f=2)  // LRU among freq=2 ✓
  
  list = Head ↔ [3:3,f=2] ↔ Tail
  Not empty, keep freqMap[2]
  
  cache.remove(1)

After eviction:
  cache = {3 → Node(3,3,f=2)}
  freqMap = {
    2 → [Head ↔ [3:3,f=2] ↔ Tail]
  }

Insert new:
  newNode = Node(4, 4, freq=1)
  cache.put(4, newNode)
  
  Create freqMap[1]:
    freqMap[1] = Head ↔ [4:4,f=1] ↔ Tail
  
  minFreq = 1

State:
  cache = {3 → Node(3,3,f=2), 4 → Node(4,4,f=1)}
  freqMap = {
    1 → [Head ↔ [4:4,f=1] ↔ Tail]
    2 → [Head ↔ [3:3,f=2] ↔ Tail]
  }
  minFreq = 1

Visual:
  Freq 1: [4:4]
  Freq 2: [3:3]
  
  Key 1 evicted (LRU among freq=2 tie)! ✓
```

---

### Visual: UpdateFrequency Detail

**Before updateFrequency(node with freq=1):**
```
freqMap:
  1 → Head ↔ [A] ↔ [B] ↔ [C] ↔ Tail
  2 → Head ↔ [D] ↔ [E] ↔ Tail

Updating node B:
```

**Step 1: Remove from old list**
```
freqMap[1].remove(B):
  1 → Head ↔ [A] ↔ [C] ↔ Tail
```

**Step 2: Check if empty and update minFreq**
```
freqMap[1] not empty
minFreq stays same
```

**Step 3: Increment frequency**
```
B.freq = 2
```

**Step 4: Add to new list**
```
freqMap[2].addToTail(B):
  2 → Head ↔ [D] ↔ [E] ↔ [B] ↔ Tail
      LRU                  MRU
```

**After:**
```
freqMap:
  1 → Head ↔ [A] ↔ [C] ↔ Tail
  2 → Head ↔ [D] ↔ [E] ↔ [B] ↔ Tail

B promoted from freq 1 to freq 2! ✓
B is now MRU in freq 2 list! ✓
```

---

## Comparison of Approaches

| Approach | get Time | put Time | Space | Complexity | Recommended |
|----------|----------|----------|-------|------------|-------------|
| **HashMap + Freq Lists** | **O(1)** | **O(1)** | **O(n)** | **High** | **Yes ✅** |
| HashMap + Single PQ | O(log n) | O(log n) | O(n) | Medium | No |
| HashMap + TreeMap | O(log n) | O(log n) | O(n) | Medium | No |
| HashMap + Array[maxFreq] | O(1) | O(1) | O(n·maxF) | High | No (space) |
| Scan All Nodes | O(n) | O(n) | O(n) | Low | No |
| Two Priority Queues | O(log n) | O(log n) | O(n) | Medium | No |

**Winner**: **HashMap + HashMap of Doubly Linked Lists** — only custom solution with O(1) all operations!

**Why Others Don't Work:**

**Priority Queue**: O(log n) updates, can't achieve O(1)
**TreeMap**: O(log n) operations, not constant time
**Array of Lists**: Can't know max frequency, space inefficient
**Scan**: O(n) to find minimum, too slow

**Only HashMap + FreqMap + Lists achieves O(1)! ✓**

---

## Key Takeaways

1. **Three-level structure** — cache + freqMap + doubly lists
2. **cache HashMap**: O(1) key → node lookup
3. **freqMap HashMap**: O(1) frequency → list access
4. **Doubly lists**: O(1) LRU order per frequency
5. **minFreq tracking** — O(1) eviction target
6. **Store freq in node** — know which list to remove from
7. **Update increments freq** — put on existing key is access
8. **MinFreq maintenance** — only increment if old list empties
9. **Reset minFreq** — new inserts always freq=1
10. **LRU tie-breaker** — evict head.next from minFreq list

---

## Interview Tips

**What to say in an interview:**

> "To implement an LFU cache with O(1) get and put, I need to track both frequency and recency. I'll use three key data structures: a cache HashMap for O(1) key-to-node lookup, a freqMap HashMap that maps each frequency to a doubly linked list of nodes with that frequency, and a minFreq variable to track the minimum frequency for O(1) eviction. Each node stores key, value, and current frequency. Within each frequency's doubly linked list, nodes are ordered by recency with LRU at the head and MRU at the tail.
>
> When get is called, I look up the node in cache and call updateFrequency which removes the node from its current frequency list, increments the frequency, and adds it to the new frequency list at the tail (MRU position). If the old frequency list becomes empty and it was the minimum frequency, I increment minFreq by 1.
>
> When put is called for a new key, if we're at capacity, I evict the LRU node from the minFreq list—that's head.next—then remove it from both the frequency list and cache. I then create a new node with frequency 1, add it to the freqMap[1] list, and reset minFreq to 1. For existing keys, I update the value and call updateFrequency just like in get.
>
> The key insight is that minFreq can only increase by 1 or reset to 1, never skip values, so maintaining it is O(1). All operations—cache lookups, frequency list access, and doubly linked list operations—are O(1), meeting the requirements."

**Key points to mention:**
1. **Three structures**: cache HashMap, freqMap HashMap, doubly lists
2. **Node stores**: key, value, frequency
3. **FreqMap**: frequency → list of nodes with that frequency
4. **Each list**: LRU order (head=LRU, tail=MRU)
5. **minFreq tracking**: O(1) eviction target identification
6. **updateFrequency**: remove from old list, increment freq, add to new list
7. **MinFreq maintenance**: only increment if old list empties and was min
8. **New insert**: always freq=1, minFreq=1
9. **Eviction**: head.next from minFreq list (LFU with LRU tie-breaker)
10. **All O(1)**: HashMap access + doubly list ops

**Common Follow-ups:**
- "Why not use a single priority queue?" → O(log n) updates, can't achieve O(1)
- "How do you handle frequency ties?" → LRU within same frequency (doubly list order)
- "Why HashMap for freqMap, not array?" → Don't know max frequency, sparse frequencies
- "How is minFreq maintained in O(1)?" → Can only increase by 1 (when old freq empties) or reset to 1 (new insert)
- "What if all keys have same frequency?" → Falls back to LRU (evict head.next)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| LRU Cache | Medium | HashMap + Doubly LL | Only recency (simpler) |
| **LFU Cache** | Hard | **HashMap + Freq Lists** | **This problem** |
| All O'one Data Structure | Hard | HashMap + Freq Lists | Track min/max counts |
| Design Twitter | Medium | HashMap + Lists | Follow/unfollow, merge feeds |
| Time Based Key-Value Store | Medium | HashMap + TreeMap | Time-based versioning |
| Design In-Memory File System | Hard | Trie + HashMap | File system operations |

**Pattern Progression**:
1. **LRU Cache** — Single dimension (recency)
2. **LFU Cache** (this) — Two dimensions (frequency + recency)
3. **All O'one** — Track both min and max frequency
4. **Hybrid policies** — Combine multiple eviction strategies

---

## Final Pattern Label

✅ **HashMap + HashMap of Doubly Linked Lists (LFU Cache)**

**Remember:** This is a **least frequently used cache** requiring **O(1) operations**. Use **three key structures**: (1) **cache HashMap<Integer, Node>** for O(1) key lookup, (2) **freqMap HashMap<Integer, DoublyLinkedList>** for O(1) frequency → list access, (3) **doubly linked lists** for O(1) LRU order within each frequency. Track **minFreq** for O(1) eviction target. **Node stores key, value, and frequency**. Within each frequency list: **head.next = LRU**, **tail.prev = MRU**. **get(key)**: lookup in cache, updateFrequency (move to freq+1 list), return value. **put(key, value)**: if exists, update value and updateFrequency; if new and at capacity, evict head.next from freqMap[minFreq] list (LFU with LRU tie-breaker), then insert new node with freq=1, reset minFreq=1. **updateFrequency(node)**: remove from freqMap[oldFreq] list, if list becomes empty remove it and if oldFreq==minFreq then minFreq++, increment node.freq, add to freqMap[newFreq] list at tail. **Critical**: update increments frequency, minFreq only increments when old freq list empties and was minimum, new inserts always reset minFreq to 1, evict from head (LRU) of minFreq list. **Common mistakes**: not incrementing freq on put update, not updating minFreq correctly, always incrementing minFreq, not resetting minFreq on insert, not storing freq in node, evicting from wrong end. All operations **O(1) time**, **O(capacity) space**. Pattern: three-level HashMap + HashMap of Lists structure for dual-dimension (frequency + recency) tracking with O(1) everything!
