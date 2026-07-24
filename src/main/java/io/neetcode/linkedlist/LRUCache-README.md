# LRU Cache

## Problem Description

**Difficulty**: Medium

Implement the **Least Recently Used (LRU)** cache class `LRUCache`. The class should support the following operations:

- `LRUCache(int capacity)` Initialize the LRU cache of size `capacity`.
- `int get(int key)` Return the value corresponding to the key if the key exists, otherwise return `-1`.
- `void put(int key, int value)` Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the introduction of the new pair causes the cache to exceed its capacity, remove the **least recently used** key.

**A key is considered used if a `get` or a `put` operation is called on it.**

**Ensure that `get` and `put` each run in O(1) average time complexity.**

**Key Concepts:**
- **LRU Eviction**: Remove least recently used item when at capacity
- **Recency Tracking**: Most recent at one end, least recent at other
- **O(1) Operations**: Both get and put must be constant time
- **Fixed Capacity**: Maximum number of items determined at initialization

**Visual Example:**
```
Cache capacity = 3

After put(1,10), put(2,20), put(3,30):
  [1:10] ← [2:20] ← [3:30]
  Least               Most
  Recent            Recent

After put(4,40) (capacity exceeded):
  [2:20] ← [3:30] ← [4:40]
  Evicted 1 (LRU)
  
After get(2) (2 becomes MRU):
  [3:30] ← [4:40] ← [2:20]
  2 moved to most recent
```

---

## Examples

### Example 1 (Main Example):
```
Input:
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1,10], [2,20], [1], [3,30], [2], [4,40], [1], [3], [4]]

Output:
[null, null, null, 10, null, 20, null, -1, -1, 40]

Explanation:
LRUCache cache = new LRUCache(2);
cache.put(1, 10);  // cache: {1=10}
cache.get(1);      // return 10, cache: {1=10}
cache.put(2, 20);  // cache: {1=10, 2=20}
cache.put(3, 30);  // cache: {2=20, 3=30}, evicted key 1 (LRU)
cache.get(2);      // return 20, cache: {3=30, 2=20} (2 moved to MRU)
cache.put(4, 40);  // cache: {2=20, 4=40}, evicted key 3 (LRU)
cache.get(1);      // return -1 (not found)
cache.get(3);      // return -1 (not found)
cache.get(4);      // return 40, cache: {2=20, 4=40}
```

### Example 2 (Single Capacity):
```
Input:
["LRUCache", "put", "put", "get", "get"]
[[1], [1,100], [2,200], [1], [2]]

Output:
[null, null, null, -1, 200]

Explanation:
LRUCache cache = new LRUCache(1);
cache.put(1, 100);  // cache: {1=100}
cache.put(2, 200);  // cache: {2=200}, evicted 1
cache.get(1);       // return -1 (evicted)
cache.get(2);       // return 200
```

### Example 3 (Update Existing Key):
```
Input:
["LRUCache", "put", "put", "put", "get"]
[[2], [1,10], [2,20], [1,15], [1]]

Output:
[null, null, null, null, 15]

Explanation:
LRUCache cache = new LRUCache(2);
cache.put(1, 10);  // cache: {1=10}
cache.put(2, 20);  // cache: {1=10, 2=20}
cache.put(1, 15);  // cache: {2=20, 1=15}, updated value, 1 is now MRU
cache.get(1);      // return 15
```

### Example 4 (Get Makes Key MRU):
```
Input:
["LRUCache", "put", "put", "get", "put", "get"]
[[2], [1,10], [2,20], [1], [3,30], [2]]

Output:
[null, null, null, 10, null, 20]

Explanation:
LRUCache cache = new LRUCache(2);
cache.put(1, 10);  // cache: {1=10}
cache.put(2, 20);  // cache: {1=10, 2=20}
cache.get(1);      // return 10, cache: {2=20, 1=10} (1 is now MRU)
cache.put(3, 30);  // cache: {1=10, 3=30}, evicted 2 (LRU)
cache.get(2);      // return -1 (evicted)
```

### Example 5 (All Gets):
```
Input:
["LRUCache", "put", "get", "get", "get"]
[[2], [1,10], [1], [2], [1]]

Output:
[null, null, 10, -1, 10]

Explanation:
Only key 1 in cache
get(1) returns 10
get(2) returns -1 (not found)
get(1) returns 10
```

### Example 6 (Large Capacity):
```
Input:
["LRUCache", "put", "put", "put", "get", "get"]
[[100], [1,10], [2,20], [3,30], [1], [2]]

Output:
[null, null, null, null, 10, 20]

Explanation:
Large capacity, no evictions yet
All keys remain in cache
```

### Example 7 (Repeated Updates):
```
Input:
["LRUCache", "put", "put", "put", "put"]
[[2], [1,10], [1,20], [1,30], [1,40]]

Output:
[null, null, null, null, null]

Explanation:
Updating same key repeatedly
Only key 1 in cache with value 40
No evictions (only one unique key)
```

### Example 8 (Sequential Evictions):
```
Input:
["LRUCache", "put", "put", "put", "put", "put"]
[[2], [1,10], [2,20], [3,30], [4,40], [5,50]]

Output:
[null, null, null, null, null, null]

Explanation:
Capacity 2, adding 5 items
Final cache: {4=40, 5=50}
Keys 1,2,3 evicted in order
```

### Example 9 (Interleaved Operations):
```
Input:
["LRUCache", "put", "get", "put", "get", "put"]
[[2], [1,10], [1], [2,20], [1], [3,30]]

Output:
[null, null, 10, null, 10, null]

Explanation:
get operations update recency
Key 1 accessed twice, stays in cache longer
```

### Example 10 (Zero and Negative Values):
```
Input:
["LRUCache", "put", "put", "get", "get"]
[[2], [0,0], [1,1000], [0], [1]]

Output:
[null, null, null, 0, 1000]

Explanation:
Keys and values can be 0
Valid key-value pairs
```

## Constraints
- `1 <= capacity <= 1000`
- `0 <= key <= 10000`
- `0 <= value <= 1000`
- At most `3000` calls to `get` and `put`
- Must implement with **O(1)** time for both get and put

**Recommended Complexity**: 
- Time: O(1) for both get and put operations
- Space: O(capacity) for storing cache entries

---

## Pattern Recognition

**Primary Pattern**: **HashMap + Doubly Linked List (Hybrid Data Structure)**

**Why This Pattern?**
- Need **O(1) lookup** → HashMap
- Need **O(1) insertion/deletion** → Doubly Linked List
- Need to track **order of usage** → Linked list ordering
- Need to **move elements** efficiently → Doubly linked list
- Combine both for **optimal solution**

**Key Insight**: Two Data Structures Working Together
```
Problem: Single data structure can't achieve all requirements

HashMap alone:
  ✓ O(1) lookup by key
  ✗ Can't track order efficiently
  ✗ Can't find LRU item in O(1)

Array/List alone:
  ✗ O(n) lookup by key
  ✗ O(n) to move elements
  
Doubly Linked List alone:
  ✗ O(n) lookup by key
  ✓ O(1) insertion/deletion (with node reference)
  ✓ Can track order

Solution: Combine HashMap + Doubly Linked List!
  HashMap: key → node reference (O(1) lookup)
  Doubly List: maintains order (O(1) move/delete)
  Together: All operations O(1)! ✓
```

**Why Doubly Linked List**:
```
Singly Linked List:
  Can only traverse forward
  To delete node, need previous node
  Can't go backward efficiently ❌

Doubly Linked List:
  Each node has prev and next pointers
  Can delete node with just node reference
  Can move to head/tail easily
  Perfect for LRU! ✓

Node structure:
  class Node {
    int key;
    int value;
    Node prev;
    Node next;
  }
```

**The Recency Order**:
```
Maintain doubly linked list from LRU to MRU:

  Head (Dummy) ← [LRU] ← ... ← [MRU] → Tail (Dummy)
  
  - Head.next = Least Recently Used
  - Tail.prev = Most Recently Used
  - Dummy nodes simplify edge cases

When accessed (get or put):
  - Remove node from current position
  - Move to tail (most recent)
  
When evicting:
  - Remove head.next (least recent)
```

**Why Dummy Head and Tail**:
```
Without dummies:
  - Empty list: head = null, tail = null
  - Single node: head = tail = node
  - Need special cases for boundary ❌

With dummies:
  - Always have head and tail
  - head.next = first real node (or tail if empty)
  - tail.prev = last real node (or head if empty)
  - No special cases! ✓
  
  Dummy Head ↔ Dummy Tail (empty)
  Dummy Head ↔ Node ↔ Dummy Tail (one node)
  Dummy Head ↔ Node1 ↔ Node2 ↔ Dummy Tail (two nodes)
  
  Consistent! ✓
```

**Core Operations**:

1. **get(key)**:
```
if key not in map:
  return -1

node = map.get(key)
remove node from list
add node to tail (make MRU)
return node.value

Time: O(1) all operations
```

2. **put(key, value)**:
```
if key in map:
  // Update existing
  node = map.get(key)
  node.value = value
  remove node from list
  add node to tail (make MRU)
else:
  // Add new
  if size == capacity:
    // Evict LRU
    lru = head.next
    remove lru from list
    map.remove(lru.key)
  
  newNode = new Node(key, value)
  add newNode to tail
  map.put(key, newNode)

Time: O(1) all operations
```

**Helper Methods**:

1. **removeNode(node)**:
```
// Remove node from doubly linked list
node.prev.next = node.next
node.next.prev = node.prev

Time: O(1) (have direct reference)
```

2. **addToTail(node)**:
```
// Add node before tail dummy (most recent position)
node.prev = tail.prev
node.next = tail
tail.prev.next = node
tail.prev = node

Time: O(1) (direct access to tail)
```

**Visual: List Operations**
```
Initial: Head ↔ [1] ↔ [2] ↔ [3] ↔ Tail

Access key 1 (make MRU):
  1. Remove [1]: Head ↔ [2] ↔ [3] ↔ Tail
  2. Add to tail: Head ↔ [2] ↔ [3] ↔ [1] ↔ Tail

Add key 4 (at capacity, evict LRU):
  1. Remove head.next (key 2): Head ↔ [3] ↔ [1] ↔ Tail
  2. Add [4] to tail: Head ↔ [3] ↔ [1] ↔ [4] ↔ Tail
```

**Why This Achieves O(1)**:
```
get(key):
  1. HashMap lookup: O(1)
  2. Remove node: O(1) (have reference)
  3. Add to tail: O(1) (have reference)
  Total: O(1) ✓

put(key, value):
  1. HashMap lookup: O(1)
  2. Remove node: O(1)
  3. Add to tail: O(1)
  4. Evict LRU: O(1) (head.next is LRU)
  5. HashMap insert/remove: O(1)
  Total: O(1) ✓
```

**Alternative Approaches (Why They Don't Work)**:

1. **HashMap + ArrayList**:
```
Store keys in order in ArrayList
  get: Find in list O(n), move to end O(n) ❌
  put: Find in list O(n), insert O(n) ❌
  
Too slow!
```

2. **HashMap + Priority Queue**:
```
Use timestamp as priority
  get: O(log n) to update priority ❌
  put: O(log n) to add/remove ❌
  
Not O(1)!
```

3. **Ordered HashMap (LinkedHashMap)**:
```
Built-in but:
  - Need custom implementation for interview
  - Want to show understanding of data structures
  - Problem likely wants custom solution
```

4. **Single HashMap with Timestamps**:
```
Store (value, timestamp) in map
Find LRU: O(n) scan for min timestamp ❌
  
Can't evict in O(1)!
```

**Related Patterns**:
1. **LFU Cache** — Least Frequently Used eviction
2. **LRU-K** — Track k most recent accesses
3. **MRU Cache** — Most Recently Used eviction (opposite)

---

## Algorithm & Approach

### Core Insight

**Why HashMap + Doubly Linked List Works:**
```
Key observations:
  1. HashMap provides O(1) key lookup
  2. Doubly linked list provides O(1) move/delete with node reference
  3. List order represents recency (head=LRU, tail=MRU)
  4. Dummy nodes eliminate edge cases
  5. Both structures stay synchronized
```

**The Optimal Strategy**:
```
Data structures:
  - HashMap<Integer, Node> map
  - Doubly linked list (head ↔ nodes ↔ tail)
  - Dummy head and tail nodes
  - Track current size

Operations:
  - get: lookup, move to tail
  - put: lookup, update/add, evict if needed
  - All O(1) with proper structure
```

### Step-by-Step Algorithm

---

#### **Approach: HashMap + Doubly Linked List - OPTIMAL**

**Core Idea**:
- HashMap for O(1) key → node lookup
- Doubly linked list for O(1) order maintenance
- Dummy nodes simplify boundary cases
- Move accessed nodes to tail (MRU)
- Evict head.next (LRU) when at capacity

**Data Structures**
```java
class Node {
    int key;
    int value;
    Node prev;
    Node next;
    
    Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

class LRUCache {
    private Map<Integer, Node> map;
    private Node head;  // Dummy head
    private Node tail;  // Dummy tail
    private int capacity;
    private int size;
}
```

**Constructor**
```java
LRUCache(int capacity):
    map = new HashMap<>()
    capacity = capacity
    size = 0
    
    // Initialize dummy nodes
    head = new Node(0, 0)
    tail = new Node(0, 0)
    head.next = tail
    tail.prev = head
```

**get(key)**
```java
get(int key):
    if key not in map:
        return -1
    
    node = map.get(key)
    
    // Move to tail (make MRU)
    removeNode(node)
    addToTail(node)
    
    return node.value
```

**put(key, value)**
```java
put(int key, int value):
    if key in map:
        // Update existing
        node = map.get(key)
        node.value = value
        removeNode(node)
        addToTail(node)
    else:
        // Add new
        if size == capacity:
            // Evict LRU
            lru = head.next
            removeNode(lru)
            map.remove(lru.key)
            size--
        
        newNode = new Node(key, value)
        map.put(key, newNode)
        addToTail(newNode)
        size++
```

**removeNode(node)**
```java
removeNode(Node node):
    node.prev.next = node.next
    node.next.prev = node.prev
```

**addToTail(node)**
```java
addToTail(Node node):
    node.prev = tail.prev
    node.next = tail
    tail.prev.next = node
    tail.prev = node
```

**Complete Code Implementation**
```java
class LRUCache {
    
    // Node class for doubly linked list
    private class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private Map<Integer, Node> map;
    private Node head;  // Dummy head (LRU side)
    private Node tail;  // Dummy tail (MRU side)
    private int capacity;
    private int size;

    public LRUCache(int capacity) {
        this.map = new HashMap<>();
        this.capacity = capacity;
        this.size = 0;
        
        // Initialize dummy nodes
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        
        Node node = map.get(key);
        
        // Move to tail (most recently used)
        removeNode(node);
        addToTail(node);
        
        return node.value;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            // Update existing key
            Node node = map.get(key);
            node.value = value;
            
            // Move to tail (most recently used)
            removeNode(node);
            addToTail(node);
        } else {
            // Add new key
            if (size == capacity) {
                // Evict LRU (head.next)
                Node lru = head.next;
                removeNode(lru);
                map.remove(lru.key);
                size--;
            }
            
            // Add new node
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addToTail(newNode);
            size++;
        }
    }
    
    // Remove node from its current position in the list
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    // Add node to tail (most recently used position)
    private void addToTail(Node node) {
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
    }
}
```

**Example Walkthrough**

Input: `capacity = 2`, operations: put(1,10), get(1), put(2,20), put(3,30), get(2)

```
Initialize:
  capacity = 2, size = 0
  map = {}
  List: Head ↔ Tail (empty)
```

**Operation 1: put(1, 10)**
```
Check: key 1 in map? No → add new

Check capacity: size (0) == capacity (2)? No

Create node: Node(1, 10)
map.put(1, node)
addToTail(node)
size = 1

State:
  map = {1 → Node(1,10)}
  List: Head ↔ [1:10] ↔ Tail
  size = 1
```

**Operation 2: get(1)**
```
Check: key 1 in map? Yes

node = map.get(1) = Node(1,10)

removeNode(node):
  node already at position (between head and tail)
  Remove: Head ↔ Tail
  
addToTail(node):
  Add back: Head ↔ [1:10] ↔ Tail
  (same position since only one node)

Return: 10 ✓

State unchanged (single node, already MRU)
```

**Operation 3: put(2, 20)**
```
Check: key 2 in map? No → add new

Check capacity: size (1) == capacity (2)? No

Create node: Node(2, 20)
map.put(2, node)
addToTail(node)
size = 2

State:
  map = {1 → Node(1,10), 2 → Node(2,20)}
  List: Head ↔ [1:10] ↔ [2:20] ↔ Tail
        LRU             MRU
  size = 2 (at capacity!)
```

**Operation 4: put(3, 30)**
```
Check: key 3 in map? No → add new

Check capacity: size (2) == capacity (2)? Yes → EVICT LRU

Evict:
  lru = head.next = Node(1,10)
  removeNode(lru):
    head.next = lru.next = Node(2,20)
    Node(2,20).prev = head
    List: Head ↔ [2:20] ↔ Tail
  
  map.remove(1)
  size = 1

Add new:
  newNode = Node(3, 30)
  map.put(3, newNode)
  addToTail(newNode)
  size = 2

State:
  map = {2 → Node(2,20), 3 → Node(3,30)}
  List: Head ↔ [2:20] ↔ [3:30] ↔ Tail
        LRU             MRU
  size = 2
  Key 1 evicted! ✓
```

**Operation 5: get(2)**
```
Check: key 2 in map? Yes

node = map.get(2) = Node(2,20)

removeNode(node):
  [2:20].prev.next = [2:20].next
  → head.next = [3:30]
  [2:20].next.prev = [2:20].prev
  → [3:30].prev = head
  List: Head ↔ [3:30] ↔ Tail

addToTail(node):
  [2:20].prev = tail.prev = [3:30]
  [2:20].next = tail
  [3:30].next = [2:20]
  tail.prev = [2:20]
  List: Head ↔ [3:30] ↔ [2:20] ↔ Tail

Return: 20 ✓

State:
  map = {2 → Node(2,20), 3 → Node(3,30)}
  List: Head ↔ [3:30] ↔ [2:20] ↔ Tail
        LRU             MRU
  Key 2 is now MRU! ✓
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
| **HashMap + Doubly LL** | **O(1)** | **O(1)** | **O(n)** | **Medium** | **Yes ✅** |
| HashMap + ArrayList | O(n) | O(n) | O(n) | Low | No |
| HashMap + Timestamps | O(1) get | O(n) evict | O(n) | Low | No |
| OrderedDict/LinkedHashMap | O(1) | O(1) | O(n) | Low | Maybe (built-in) |
| HashMap + PriorityQueue | O(log n) | O(log n) | O(n) | Medium | No |

**Winner**: **HashMap + Doubly Linked List** — only custom solution with O(1) operations!

### Why HashMap is Essential

```
Need O(1) lookup by key

Without HashMap:
  Must search list: O(n) ❌
  Can't meet requirement
  
With HashMap:
  Direct access to node: O(1) ✓
  Store key → node reference
  Instant lookup
```

### Why Doubly Linked List

```
Need O(1) move/delete with node reference

Singly Linked:
  To delete, need previous node
  Must traverse: O(n) ❌
  
Doubly Linked:
  node.prev and node.next available
  Delete directly: O(1) ✓
  
  node.prev.next = node.next
  node.next.prev = node.prev
  Done! No traversal needed
```

### Why Track Order in List

```
Need to identify LRU for eviction

Array/List order:
  Find minimum: O(n) scan ❌
  
Linked list with order:
  LRU always at head.next: O(1) ✓
  Just remove head.next
  
Order = recency, naturally maintained!
```

### Why Dummy Nodes

```
Without dummies (empty list):
  if head == null:
    // Special case
  if single node:
    // Special case
  Lots of edge cases ❌

With dummies:
  head and tail always exist
  head.next and tail.prev always valid
  Empty: head ↔ tail
  One: head ↔ node ↔ tail
  Consistent code! ✓
```

### Why Store Key in Node

```
During eviction:
  lru = head.next
  Need to remove from HashMap too
  map.remove(lru.key)
  
Without key in node:
  How to find key for this node? ❌
  Would need reverse lookup: O(n)
  
With key in node:
  Direct access: O(1) ✓
```

### Why This is Optimal

```
Time complexity:
  get: O(1) - HashMap + list ops
  put: O(1) - HashMap + list ops
  All operations constant! ✓

Space complexity:
  HashMap: O(capacity)
  List nodes: O(capacity)
  Total: O(capacity) = O(n)
  Minimal for storing n items! ✓

Meets all requirements! ✓
```

---

## Critical Edge Cases & Gotchas

### 1. **Capacity 1**
```java
LRUCache cache = new LRUCache(1);
cache.put(1, 10);  // {1=10}
cache.put(2, 20);  // {2=20}, evicted 1
cache.get(1);      // -1 (evicted)
// Every put evicts previous (if different key)
```

### 2. **Update Existing Key**
```java
cache.put(1, 10);  // {1=10}
cache.put(1, 20);  // {1=20}, updated value
// Should NOT evict, just update value and move to MRU
```

### 3. **Get Non-Existent Key**
```java
cache.get(999);    // -1 (not in cache)
// Should return -1, not throw exception
```

### 4. **Get Makes Key MRU**
```java
cache.put(1, 10);  // {1=10}
cache.put(2, 20);  // {1=10, 2=20}
cache.get(1);      // {2=20, 1=10}, 1 is now MRU
cache.put(3, 30);  // {1=10, 3=30}, evicts 2 (not 1!)
```

### 5. **Eviction Order**
```java
LRUCache cache = new LRUCache(2);
cache.put(1, 10);  // {1=10}
cache.put(2, 20);  // {1=10, 2=20}
cache.put(3, 30);  // {2=20, 3=30}, evicted 1 (oldest)
cache.put(4, 40);  // {3=30, 4=40}, evicted 2 (now oldest)
```

### 6. **All Operations on Same Key**
```java
cache.put(1, 10);  // {1=10}
cache.get(1);      // 10
cache.put(1, 20);  // {1=20}
cache.get(1);      // 20
// No evictions, all operations on single key
```

### 7. **Empty Cache Get**
```java
LRUCache cache = new LRUCache(2);
cache.get(1);      // -1 (cache empty)
```

### 8. **Fill to Capacity**
```java
LRUCache cache = new LRUCache(3);
cache.put(1, 10);  // {1=10}
cache.put(2, 20);  // {1=10, 2=20}
cache.put(3, 30);  // {1=10, 2=20, 3=30}, full but no eviction yet
cache.put(4, 40);  // {2=20, 3=30, 4=40}, NOW eviction happens
```

### 9. **Zero Values**
```java
cache.put(0, 0);   // Valid key-value pair
cache.get(0);      // Should return 0, not -1
// 0 is valid value, -1 is "not found" sentinel
```

### 10. **Rapid Get/Put Alternation**
```java
cache.put(1, 10);
cache.get(1);
cache.put(1, 20);
cache.get(1);
// Each operation updates recency
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Storing Key in Node**
```java
// WRONG - no key in node
private class Node {
    int value;  // Missing: int key; ❌
    Node prev;
    Node next;
}

// During eviction:
Node lru = head.next;
removeNode(lru);
map.remove(?);  // Don't know the key! ❌
```

**Why wrong**: Can't remove from HashMap!

**Dry run failure:**
```
Need to remove from HashMap during eviction
But node only has value, not key
Would need to scan entire HashMap: O(n) ❌

Must store key in node for O(1) removal! ✓
```

**Fix**: Store key in node
```java
private class Node {
    int key;   ✓
    int value;
    Node prev;
    Node next;
}
```

### ❌ **MISTAKE 2: Using Singly Linked List**
```java
// WRONG - singly linked list
private class Node {
    int key;
    int value;
    Node next;  // No prev pointer! ❌
}
```

**Why wrong**: Can't remove node in O(1)!

**Dry run failure:**
```
To remove node from singly linked list:
  Need previous node
  Must traverse from head: O(n) ❌
  
Can't achieve O(1) requirement!
```

**Fix**: Use doubly linked list
```java
private class Node {
    int key;
    int value;
    Node prev;  ✓
    Node next;  ✓
}
```

### ❌ **MISTAKE 3: Not Using Dummy Nodes**
```java
// WRONG - no dummy nodes
private Node head;  // Points to real first node ❌
private Node tail;  // Points to real last node ❌

public void addToTail(Node node) {
    if (tail == null) {  // Special case! ❌
        head = tail = node;
    } else {
        tail.next = node;
        node.prev = tail;
        tail = node;
    }
}
```

**Why wrong**: Too many edge cases!

**Issue:**
```
Need to handle:
  - Empty list
  - Single node
  - Multiple nodes
  
Lots of if-else logic ❌
Error-prone
```

**Fix**: Use dummy nodes
```java
private Node head;  // Dummy ✓
private Node tail;  // Dummy ✓

// Constructor:
head = new Node(0, 0);
tail = new Node(0, 0);
head.next = tail;
tail.prev = head;

// addToTail always works the same way:
private void addToTail(Node node) {
    node.prev = tail.prev;
    node.next = tail;
    tail.prev.next = node;
    tail.prev = node;
}
// No special cases! ✓
```

### ❌ **MISTAKE 4: Forgetting to Update List on get()**
```java
// WRONG - get doesn't update recency
public int get(int key) {
    if (!map.containsKey(key)) {
        return -1;
    }
    
    Node node = map.get(key);
    return node.value;  // WRONG! Didn't move to tail ❌
}
```

**Why wrong**: Violates LRU semantics!

**Dry run failure:**
```
cache.put(1, 10);  // {1=10}
cache.put(2, 20);  // {1=10, 2=20}
cache.get(1);      // Returns 10 but doesn't update position ❌
cache.put(3, 30);  // Should evict 2, but evicts 1 instead! ❌

Key 1 was accessed, should be MRU!
```

**Fix**: Move to tail on get
```java
public int get(int key) {
    if (!map.containsKey(key)) {
        return -1;
    }
    
    Node node = map.get(key);
    removeNode(node);  ✓
    addToTail(node);   ✓
    return node.value;
}
```

### ❌ **MISTAKE 5: Not Handling Update vs Insert in put()**
```java
// WRONG - treating update same as insert
public void put(int key, int value) {
    if (size == capacity) {  // Check before checking if key exists! ❌
        // Evict LRU
        Node lru = head.next;
        removeNode(lru);
        map.remove(lru.key);
        size--;
    }
    
    Node newNode = new Node(key, value);
    map.put(key, newNode);
    addToTail(newNode);
    size++;
}
```

**Why wrong**: Updates shouldn't evict or increase size!

**Dry run failure:**
```
cache.put(1, 10);  // size = 1
cache.put(1, 20);  // Update, but code adds new node!
                   // Now two nodes for key 1 ❌
                   // size = 2 ❌ (should still be 1)
```

**Fix**: Check if key exists first
```java
public void put(int key, int value) {
    if (map.containsKey(key)) {  ✓
        // Update existing
        Node node = map.get(key);
        node.value = value;
        removeNode(node);
        addToTail(node);
    } else {
        // Insert new
        if (size == capacity) {
            // Evict LRU
            Node lru = head.next;
            removeNode(lru);
            map.remove(lru.key);
            size--;
        }
        
        Node newNode = new Node(key, value);
        map.put(key, newNode);
        addToTail(newNode);
        size++;
    }
}
```

### ❌ **MISTAKE 6: Wrong removeNode Implementation**
```java
// WRONG - incomplete removal
private void removeNode(Node node) {
    node.prev.next = node.next;
    // Missing: node.next.prev = node.prev; ❌
}
```

**Why wrong**: Breaks doubly linked list!

**Dry run failure:**
```
List: Head ↔ [1] ↔ [2] ↔ Tail

Remove [1]:
  head.next = [2] ✓
  But [2].prev still points to [1]! ❌
  
List corrupted!
```

**Fix**: Update both directions
```java
private void removeNode(Node node) {
    node.prev.next = node.next;  ✓
    node.next.prev = node.prev;  ✓
}
```

### ❌ **MISTAKE 7: Wrong addToTail Implementation**
```java
// WRONG - incorrect pointer updates
private void addToTail(Node node) {
    node.prev = tail.prev;
    node.next = tail;
    tail.prev = node;  // Missing: tail.prev.next = node; ❌
}
```

**Why wrong**: Doesn't connect previous node!

**Dry run failure:**
```
List: Head ↔ [1] ↔ Tail

Add [2]:
  [2].prev = [1] ✓
  [2].next = Tail ✓
  Tail.prev = [2] ✓
  But [1].next still points to Tail! ❌
  
[1] not connected to [2]!
```

**Fix**: Update all four pointers
```java
private void addToTail(Node node) {
    node.prev = tail.prev;
    node.next = tail;
    tail.prev.next = node;  ✓
    tail.prev = node;
}
```

### ❌ **MISTAKE 8: Not Decrementing Size on Eviction**
```java
// WRONG - size not updated
if (size == capacity) {
    Node lru = head.next;
    removeNode(lru);
    map.remove(lru.key);
    // Missing: size--; ❌
}

Node newNode = new Node(key, value);
map.put(key, newNode);
addToTail(newNode);
size++;
```

**Why wrong**: Size exceeds capacity!

**Dry run failure:**
```
capacity = 2, size = 2

Add new key (should evict):
  Remove LRU
  size still 2 ❌
  Add new node
  size = 3 ❌ (exceeds capacity!)
```

**Fix**: Decrement on eviction
```java
if (size == capacity) {
    Node lru = head.next;
    removeNode(lru);
    map.remove(lru.key);
    size--;  ✓
}
```

### ❌ **MISTAKE 9: Returning 0 Instead of -1 for Not Found**
```java
// WRONG - wrong sentinel value
public int get(int key) {
    if (!map.containsKey(key)) {
        return 0;  // WRONG! Should be -1 ❌
    }
    // ...
}
```

**Why wrong**: 0 is a valid value!

**Issue:**
```
cache.put(0, 0);  // Valid: key 0, value 0
cache.get(0);     // Should return 0

cache.get(999);   // Should return -1 (not found)

If we return 0 for not found:
  Can't distinguish between stored 0 and not found! ❌
```

**Fix**: Return -1 for not found
```java
if (!map.containsKey(key)) {
    return -1;  ✓
}
```

### ❌ **MISTAKE 10: Using LinkedHashMap Without Understanding**
```java
// WRONG - using built-in without showing understanding
class LRUCache extends LinkedHashMap<Integer, Integer> {
    // Just using built-in ❌
    // Interview wants to see implementation!
}
```

**Why wrong**: Interview tests understanding!

**Issue:**
```
While LinkedHashMap can solve it:
  - Interview wants custom implementation
  - Want to see understanding of data structures
  - Need to explain HashMap + Doubly LL
  
Using built-in shows less understanding ❌
```

**Fix**: Implement from scratch
```java
class LRUCache {
    private Map<Integer, Node> map;
    private Node head, tail;
    // ... custom implementation ✓
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
1. map.containsKey(key): O(1) - HashMap lookup
2. map.get(key): O(1) - HashMap lookup
3. removeNode(node): O(1) - 4 pointer updates
4. addToTail(node): O(1) - 4 pointer updates
Total: O(1) ✓
```

**put(key, value)** (update existing):
```
1. map.containsKey(key): O(1)
2. map.get(key): O(1)
3. node.value = value: O(1)
4. removeNode(node): O(1)
5. addToTail(node): O(1)
Total: O(1) ✓
```

**put(key, value)** (insert new):
```
1. map.containsKey(key): O(1)
2. Check capacity: O(1)
3. Evict (if needed):
   - Get head.next: O(1)
   - removeNode: O(1)
   - map.remove: O(1)
4. Create node: O(1)
5. map.put: O(1)
6. addToTail: O(1)
Total: O(1) ✓
```

### Space Complexity: **O(capacity)**

```
Data structure components:
  - HashMap: O(capacity) - stores up to capacity entries
  - Doubly linked list: O(capacity) - one node per entry
  - Dummy nodes: O(1) - two dummy nodes
  - Other variables: O(1)

Total: O(capacity) = O(n) where n is capacity
```

**Space is optimal for storing n entries!**

**No Hidden Space:**
```
Each entry stored once:
  - Once in HashMap (key → node reference)
  - Once in list (actual node)
  
No duplication ✓
No extra arrays or structures ✓
Minimal overhead (prev/next pointers) ✓
```

### Optimal Complexity

```
Time: O(1) for all operations
  HashMap provides O(1) lookup
  Doubly list provides O(1) move/delete
  Cannot do better than constant time
  Optimal! ✓

Space: O(n) to store n entries
  Must store all entries
  Minimal overhead for tracking order
  Optimal! ✓

Solution meets all requirements! ✓
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `capacity = 3`, operations shown step-by-step

---

**Initialization:**
```
LRUCache cache = new LRUCache(3);

State:
  capacity = 3
  size = 0
  map = {}
  List: Head ↔ Tail (empty)

Visual:
  Head ↔ Tail
  (no real nodes)
```

---

**Operation 1: put(1, 10)**
```
Check: key 1 in map? No → insert new

Check capacity: size (0) < capacity (3) ✓

Create Node(1, 10)
map.put(1, node)
addToTail(node)
size = 1

State:
  map = {1 → Node(1,10)}
  List: Head ↔ [1:10] ↔ Tail
  size = 1

Visual:
  Head ↔ [1:10] ↔ Tail
  LRU          MRU
```

---

**Operation 2: put(2, 20)**
```
Insert new Node(2, 20)
size = 2

State:
  map = {1 → Node(1,10), 2 → Node(2,20)}
  List: Head ↔ [1:10] ↔ [2:20] ↔ Tail
  size = 2

Visual:
  Head ↔ [1:10] ↔ [2:20] ↔ Tail
  LRU                   MRU
```

---

**Operation 3: put(3, 30)**
```
Insert new Node(3, 30)
size = 3 (at capacity!)

State:
  map = {1 → Node(1,10), 2 → Node(2,20), 3 → Node(3,30)}
  List: Head ↔ [1:10] ↔ [2:20] ↔ [3:30] ↔ Tail
  size = 3

Visual:
  Head ↔ [1:10] ↔ [2:20] ↔ [3:30] ↔ Tail
  LRU                           MRU
  
Cache is full!
```

---

**Operation 4: get(2)**
```
Check: key 2 in map? Yes

node = Node(2,20)

removeNode(node):
  Before: Head ↔ [1:10] ↔ [2:20] ↔ [3:30] ↔ Tail
  
  [1:10].next = [3:30]
  [3:30].prev = [1:10]
  
  After: Head ↔ [1:10] ↔ [3:30] ↔ Tail

addToTail(node):
  [2:20].prev = [3:30]
  [2:20].next = Tail
  [3:30].next = [2:20]
  Tail.prev = [2:20]
  
  After: Head ↔ [1:10] ↔ [3:30] ↔ [2:20] ↔ Tail

Return: 20 ✓

State:
  map unchanged (same keys)
  List: Head ↔ [1:10] ↔ [3:30] ↔ [2:20] ↔ Tail
  size = 3

Visual:
  Head ↔ [1:10] ↔ [3:30] ↔ [2:20] ↔ Tail
  LRU                           MRU
  
Key 2 moved to MRU position! ✓
```

---

**Operation 5: put(4, 40)**
```
Check: key 4 in map? No → insert new

Check capacity: size (3) == capacity (3) → EVICT!

Evict LRU:
  lru = head.next = Node(1,10)
  
  removeNode(lru):
    head.next = [3:30]
    [3:30].prev = head
    List: Head ↔ [3:30] ↔ [2:20] ↔ Tail
  
  map.remove(1)
  size = 2

Insert new:
  newNode = Node(4, 40)
  map.put(4, newNode)
  addToTail(newNode)
  size = 3

State:
  map = {2 → Node(2,20), 3 → Node(3,30), 4 → Node(4,40)}
  List: Head ↔ [3:30] ↔ [2:20] ↔ [4:40] ↔ Tail
  size = 3

Visual:
  Head ↔ [3:30] ↔ [2:20] ↔ [4:40] ↔ Tail
  LRU                           MRU
  
Key 1 evicted! ✓
Key 4 added as MRU! ✓
```

---

**Operation 6: get(1)**
```
Check: key 1 in map? No

Return: -1 ✓

(Key 1 was evicted, not found)
```

---

**Operation 7: put(3, 35)**
```
Check: key 3 in map? Yes → UPDATE

node = Node(3,30)
node.value = 35

removeNode(node):
  Head ↔ [2:20] ↔ [4:40] ↔ Tail

addToTail(node):
  Head ↔ [2:20] ↔ [4:40] ↔ [3:35] ↔ Tail

State:
  map = {2 → Node(2,20), 3 → Node(3,35), 4 → Node(4,40)}
  List: Head ↔ [2:20] ↔ [4:40] ↔ [3:35] ↔ Tail
  size = 3 (unchanged)

Visual:
  Head ↔ [2:20] ↔ [4:40] ↔ [3:35] ↔ Tail
  LRU                           MRU
  
Key 3 updated and moved to MRU! ✓
No eviction (update, not insert)! ✓
```

---

### Visual: List Operations Detail

**removeNode(node) Example:**
```
Before: A ↔ B ↔ C ↔ D

Remove B:
  Step 1: A.next = B.next = C
  Step 2: C.prev = B.prev = A
  
After: A ↔ C ↔ D

B is disconnected (can be GC'd)
```

**addToTail(node) Example:**
```
Before: A ↔ B ↔ Tail

Add C:
  Step 1: C.prev = Tail.prev = B
  Step 2: C.next = Tail
  Step 3: B.next = C (was Tail)
  Step 4: Tail.prev = C (was B)
  
After: A ↔ B ↔ C ↔ Tail

C inserted before Tail (MRU position)
```

---

## Comparison of Approaches

| Approach | get Time | put Time | Space | Complexity | Recommended |
|----------|----------|----------|-------|------------|-------------|
| **HashMap + Doubly LL** | **O(1)** | **O(1)** | **O(n)** | **Medium** | **Yes ✅** |
| HashMap + ArrayList | O(n) | O(n) | O(n) | Low | No |
| HashMap + Timestamps | O(1) | O(n) evict | O(n) | Low | No |
| OrderedDict (Python) | O(1) | O(1) | O(n) | Low | Maybe |
| LinkedHashMap (Java) | O(1) | O(1) | O(n) | Low | Maybe |
| HashMap + PriorityQueue | O(log n) | O(log n) | O(n) | Medium | No |

**Winner**: **HashMap + Doubly Linked List** — optimal custom solution!

**When to Use Built-ins:**
- Python: `collections.OrderedDict` with `move_to_end()`
- Java: `LinkedHashMap` with `accessOrder=true`
- But interview typically wants custom implementation to show understanding

---

## Key Takeaways

1. **HashMap + Doubly LL** — hybrid data structure
2. **HashMap**: O(1) key → node lookup
3. **Doubly LL**: O(1) move/delete operations
4. **Dummy nodes** — eliminate edge cases
5. **List order** — LRU at head, MRU at tail
6. **Store key in node** — for HashMap removal during eviction
7. **get updates recency** — move to tail (MRU)
8. **put checks existence** — update vs insert logic
9. **Evict head.next** — least recently used
10. **All O(1) operations** — meets requirements

---

## Interview Tips

**What to say in an interview:**

> "To implement an LRU cache with O(1) get and put operations, I'll use a hybrid data structure combining a HashMap and a doubly linked list. The HashMap provides O(1) lookup from key to node, while the doubly linked list maintains the order of usage with the least recently used at the head and most recently used at the tail. I'll use dummy head and tail nodes to simplify edge cases. When get is called, I look up the node in the HashMap, then remove it from its current position in the list and add it to the tail to mark it as most recently used. When put is called, I first check if the key exists—if so, I update the value and move it to the tail. If it's a new key and we're at capacity, I remove the head.next node (least recently used) from both the list and HashMap before adding the new node at the tail. Each node stores both key and value so I can remove the key from the HashMap during eviction. All operations run in O(1) time because the HashMap gives direct node access and the doubly linked list allows constant-time removal and insertion with node references."

**Key points to mention:**
1. **Hybrid structure**: HashMap + Doubly Linked List
2. **HashMap**: key → node lookup in O(1)
3. **Doubly LL**: order maintenance, O(1) move/delete
4. **Dummy nodes**: simplify boundary cases
5. **Order**: LRU at head, MRU at tail
6. **get updates recency**: move to tail
7. **put handles update vs insert**: different logic
8. **Eviction**: remove head.next when at capacity
9. **Store key in node**: for HashMap removal

**Common Follow-ups:**
- "Why doubly linked list instead of singly?" → Need O(1) delete with node reference (need prev pointer)
- "Why store key in node?" → To remove from HashMap during eviction
- "Can you use other structures?" → Not for O(1) (ArrayList is O(n), PriorityQueue is O(log n))
- "What about LinkedHashMap?" → Yes, but interview wants custom implementation
- "How to handle updates?" → Check if key exists first, update value and move to tail without evicting

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **LRU Cache** | Medium | **HashMap + Doubly LL** | **This problem** |
| LFU Cache | Hard | HashMap + Doubly LL + Freq tracking | Track access frequency instead of recency |
| Design Twitter | Medium | HashMap + Lists | Follow/unfollow, merge timelines |
| All O\`one Data Structure | Hard | HashMap + Doubly LL | Track min/max frequency |
| Insert Delete GetRandom O(1) | Medium | HashMap + ArrayList | Different operations |
| Snapshot Array | Medium | HashMap + TreeMap | Time-based versioning |

**Pattern Progression**:
1. **LRU Cache** (this) — Recency-based eviction
2. **LFU Cache** — Frequency-based eviction (more complex)
3. **All O'one** — Track both min and max counts
4. **Hybrid caching** — Combine multiple policies

---

## Final Pattern Label

✅ **HashMap + Doubly Linked List Hybrid (LRU Cache)**

**Remember:** This is a **least recently used cache** requiring **O(1) operations**. Use **HashMap<Integer, Node>** for O(1) key lookup and **doubly linked list** for O(1) order maintenance. **Dummy head and tail nodes** eliminate edge cases. List order represents recency: **head.next is LRU** (least recently used), **tail.prev is MRU** (most recently used). **Node must store both key and value** (key needed for HashMap removal during eviction). **get(key)**: lookup in HashMap, remove node from list, add to tail (make MRU), return value. **put(key, value)**: if key exists, update value and move to tail; if new key, check capacity—evict head.next and remove from HashMap if at capacity—then add new node to tail and HashMap. **Critical operations**: `removeNode(node)` updates 4 pointers (prev.next, next.prev), `addToTail(node)` updates 4 pointers (before tail). **Common mistakes**: forgetting to store key in node, using singly linked list, not updating recency on get(), not distinguishing update vs insert in put(), wrong removeNode/addToTail implementation, not decrementing size on eviction. All operations **O(1) time**, **O(capacity) space**. Pattern: combine two data structures for complementary O(1) capabilities!
