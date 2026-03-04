# LRU Cache

## Problem Description

**Difficulty**: Medium

Implement the Least Recently Used (LRU) cache class `LRUCache`. The class should support the following operations:

- `LRUCache(int capacity)` - Initialize the LRU cache of size capacity.
- `int get(int key)` - Return the value corresponding to the key if the key exists, otherwise return -1.
- `void put(int key, int value)` - Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the introduction of the new pair causes the cache to exceed its capacity, remove the least recently used key.

A key is considered used if a get or a put operation is called on it.

**Time Complexity Goal**: O(1) for both `get` and `put` operations  
**Space Complexity Goal**: O(capacity)

## Examples

### Example 1:
```
Input:
["LRUCache", [2], "put", [1, 10], "get", [1], "put", [2, 20], "put", [3, 30], "get", [2], "get", [1]]

Output:
[null, null, 10, null, null, 20, -1]

Explanation:
LRUCache lRUCache = new LRUCache(2);
lRUCache.put(1, 10);  // cache: {1=10}
lRUCache.get(1);      // return 10
lRUCache.put(2, 20);  // cache: {1=10, 2=20}
lRUCache.put(3, 30);  // cache: {2=20, 3=30}, key=1 was evicted
lRUCache.get(2);      // returns 20 
lRUCache.get(1);      // return -1 (not found)
```

## Constraints

- 1 <= capacity <= 100
- 0 <= key <= 1000
- 0 <= value <= 1000

---

## Pattern to Use

**Pattern**: **HashMap + Doubly Linked List (Custom Data Structure Design)**

This problem requires combining two data structures to achieve O(1) operations:
1. **HashMap** - For O(1) key lookup
2. **Doubly Linked List** - For O(1) insertion/deletion and maintaining order

---

## Why This Pattern?

### Understanding LRU Cache:
- **LRU (Least Recently Used)**: When the cache is full, we evict the item that hasn't been accessed for the longest time.
- Every `get` or `put` operation makes that key "recently used."
- We need to track the order of usage efficiently.

### Why HashMap?
- We need O(1) access to check if a key exists and retrieve its value.
- HashMap provides this directly with `get(key)` and `put(key, value)`.

### Why Doubly Linked List?
- We need to maintain the order of usage: LRU at one end, MRU (Most Recently Used) at the other.
- When a key is accessed, we need to move it to the MRU position in O(1) time.
- When capacity is exceeded, we need to remove the LRU element in O(1) time.
- **Singly linked list won't work** because we can't delete a node in O(1) without access to its previous node.
- **Array/ArrayList won't work** because insertion/deletion in the middle takes O(n) time.

### Why Both Together?
- **HashMap alone**: Can't maintain order of usage.
- **Doubly Linked List alone**: Can't provide O(1) key lookup (would need O(n) to find a key).
- **Combined**: HashMap stores `key -> Node` mapping, where each Node is part of the doubly linked list.

---

## Algorithm / Approach

### Data Structure Design:

```
1. Create a Node class:
   - int key
   - int value
   - Node prev
   - Node next

2. LRUCache class has:
   - HashMap<Integer, Node> cache (maps key to node)
   - Node head (dummy node - LRU end)
   - Node tail (dummy node - MRU end)
   - int capacity
```

### Visual Representation:

```
HashMap:              Doubly Linked List:
key -> Node           
                      head <-> [key:1, val:10] <-> [key:2, val:20] <-> tail
1 -> Node@123         ^                                               ^
2 -> Node@456         LRU (Least Recently Used)          MRU (Most Recently Used)
```

### Core Operations:

#### 1. **get(key)**
```
Step 1: Check if key exists in HashMap
   - If NOT exists: return -1
   
Step 2: If exists:
   - Get the node from HashMap
   - Remove this node from its current position in the list
   - Insert it at the tail (mark as most recently used)
   - Return node.value
   
Time: O(1)
```

#### 2. **put(key, value)**
```
Step 1: Check if key already exists
   - If YES:
     * Update the node's value
     * Remove node from current position
     * Insert at tail (mark as most recently used)
     * Return
   
Step 2: If key doesn't exist:
   - Create new node
   - Add to HashMap
   - Insert at tail (mark as most recently used)
   
Step 3: Check if size exceeds capacity
   - If YES:
     * Remove the node right after head (LRU element)
     * Remove it from HashMap as well
     
Time: O(1)
```

### Helper Functions:

#### **remove(Node node)** - Remove node from list
```
Step 1: Get previous and next nodes
Step 2: Link prev.next to next
Step 3: Link next.prev to prev

Example: Remove node B
A <-> B <-> C
becomes
A <-> C

Time: O(1)
```

#### **insertAtTail(Node node)** - Insert node before tail
```
Step 1: Get the node before tail (tail.prev)
Step 2: Link tail.prev.next to new node
Step 3: Link new node.prev to tail.prev
Step 4: Link new node.next to tail
Step 5: Link tail.prev to new node

Example: Insert X before tail
A <-> tail
becomes
A <-> X <-> tail

Time: O(1)
```

---

## Step-by-Step Execution Example

Let's trace through Example 1:

```
Initial: LRUCache(2) - capacity = 2
head <-> tail
cache = {}
```

### Step 1: put(1, 10)
```
- Create node [1, 10]
- Insert at tail
- Add to HashMap

head <-> [1:10] <-> tail
cache = {1 -> Node[1:10]}
```

### Step 2: get(1)
```
- Key 1 exists in cache
- Node already at MRU position
- Return 10

head <-> [1:10] <-> tail
cache = {1 -> Node[1:10]}
```

### Step 3: put(2, 20)
```
- Create node [2, 20]
- Insert at tail
- Add to HashMap

head <-> [1:10] <-> [2:20] <-> tail
cache = {1 -> Node[1:10], 2 -> Node[2:20]}
```

### Step 4: put(3, 30)
```
- Create node [3, 30]
- Insert at tail
- Size = 3, exceeds capacity (2)
- Remove LRU node (head.next = [1:10])
- Remove key 1 from HashMap

head <-> [2:20] <-> [3:30] <-> tail
cache = {2 -> Node[2:20], 3 -> Node[3:30]}
```

### Step 5: get(2)
```
- Key 2 exists in cache
- Remove [2:20] from current position
- Insert at tail

head <-> [3:30] <-> [2:20] <-> tail
cache = {2 -> Node[2:20], 3 -> Node[3:30]}
Return: 20
```

### Step 6: get(1)
```
- Key 1 NOT in cache
Return: -1
```

---

## Code Implementation

```java
class LRUCache {
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
    
    private final int capacity;
    private final Map<Integer, Node> cache;
    private final Node head;
    private final Node tail;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        
        // Initialize dummy nodes
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        
        Node node = cache.get(key);
        remove(node);           // Remove from current position
        insertAtTail(node);     // Mark as most recently used
        return node.value;
    }
    
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            // Update existing key
            Node node = cache.get(key);
            node.value = value;
            remove(node);
            insertAtTail(node);
        } else {
            // Add new key
            Node newNode = new Node(key, value);
            cache.put(key, newNode);
            insertAtTail(newNode);
            
            // Check capacity
            if (cache.size() > capacity) {
                Node lruNode = head.next;
                remove(lruNode);
                cache.remove(lruNode.key);
            }
        }
    }
    
    private void remove(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
    }
    
    private void insertAtTail(Node node) {
        Node prevNode = tail.prev;
        prevNode.next = node;
        node.prev = prevNode;
        node.next = tail;
        tail.prev = node;
    }
}
```

---

## Complexity Analysis

### Time Complexity:
- **get(key)**: O(1)
  - HashMap lookup: O(1)
  - Remove from list: O(1)
  - Insert at tail: O(1)
  
- **put(key, value)**: O(1)
  - HashMap lookup: O(1)
  - Remove from list: O(1)
  - Insert at tail: O(1)
  - Remove LRU: O(1)

### Space Complexity:
- **O(capacity)**: We store at most `capacity` nodes in both HashMap and the doubly linked list.

---

## Key Takeaways

1. **Why Dummy Nodes?**
   - Simplify edge cases (empty list, single element)
   - No need to check for null when inserting/removing
   - Head always has a next, tail always has a prev

2. **Why Store Key in Node?**
   - When we evict LRU node, we need to remove it from HashMap
   - Without the key in the node, we'd need to iterate through HashMap (O(n))

3. **Common Mistakes:**
   - Forgetting to update HashMap when removing nodes
   - Not handling the case when updating existing key
   - Using singly linked list (can't remove in O(1))
   - Not using dummy nodes (leads to complex edge case handling)

4. **LRU vs LFU:**
   - **LRU**: Evicts least recently used (based on time of last access)
   - **LFU**: Evicts least frequently used (based on access count)

5. **Real-World Usage:**
   - Browser cache
   - CPU cache
   - Database query cache
   - CDN caching strategies

---

## Related Problems

- LFU Cache (Harder)
- Design Browser History
- Time-based Key-Value Store
- Design In-Memory File System

---

## Summary

**Pattern**: HashMap + Doubly Linked List

**Why This Approach:**
- HashMap provides O(1) key lookup
- Doubly Linked List maintains order and allows O(1) insertion/deletion
- Dummy nodes simplify edge cases
- Storing key in node allows O(1) eviction

**Key Insight**: You need TWO data structures working together to achieve O(1) for both operations. Neither HashMap nor Linked List alone can satisfy both requirements.

