# LFU Cache

## Problem Description

**Difficulty**: Hard

Design and implement a data structure for a **Least Frequently Used (LFU) cache**.

Implement the `LFUCache` class:

- `LFUCache(int capacity)` - Initializes the object with the capacity of the data structure.
- `int get(int key)` - Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
- `void put(int key, int value)` - Update the value of the key if present, or inserts the key if not already present. When the cache reaches its capacity, it should invalidate and remove the **least frequently used key** before inserting a new item. For this problem, when there is a **tie** (i.e., two or more keys with the same frequency), the **least recently used** key would be invalidated.

**Use Counter**: To determine the least frequently used key, a use counter is maintained for each key in the cache. The key with the smallest use counter is the least frequently used key. When a key is first inserted into the cache, its use counter is set to 1 (due to the put operation). The use counter for a key in the cache is incremented either a get or put operation is called on it.

**Time Complexity Goal**: O(1) for both `get` and `put` operations  
**Space Complexity Goal**: O(capacity)

## Examples

### Example 1:
```
Input: 
["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]

Output: 
[null, null, null, 1, null, -1, 3, null, -1, 3, 4]

Explanation:
// cnt(x) = the use counter for key x
// cache=[] will show the last used order for tiebreakers (leftmost element is most recent)
LFUCache lfu = new LFUCache(2);
lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
lfu.get(1);      // return 1
                 // cache=[1,2], cnt(2)=1, cnt(1)=2
lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
                 // cache=[3,1], cnt(3)=1, cnt(1)=2
lfu.get(2);      // return -1 (not found)
lfu.get(3);      // return 3
                 // cache=[3,1], cnt(3)=2, cnt(1)=2
lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
                 // cache=[4,3], cnt(4)=1, cnt(3)=2
lfu.get(1);      // return -1 (not found)
lfu.get(3);      // return 3
                 // cache=[3,4], cnt(4)=1, cnt(3)=3
lfu.get(4);      // return 4
                 // cache=[4,3], cnt(4)=2, cnt(3)=3
```

## Constraints
- 1 <= capacity <= 10,000
- 0 <= key <= 100,000
- 0 <= value <= 1,000,000,000
- At most 200,000 calls will be made to `get` and `put`

---

## Pattern Recognition

**Primary Pattern**: **Hash Map + Doubly Linked List (Hybrid Data Structure)**

**Why This Pattern?**
- We need O(1) access to cache values → Hash Map
- We need O(1) insertion/deletion → Doubly Linked List
- We need to track frequency counts → Multiple Doubly Linked Lists (one per frequency)
- We need to track LRU within same frequency → Order in Doubly Linked List

**Related Patterns**:
1. **LRU Cache** - Similar structure but simpler (only one doubly linked list)
2. **Design HashMap/HashSet** - Hash-based data structures
3. **Two Data Structures** - Combining complementary data structures

---

## Algorithm & Approach

### Core Insight
The LFU Cache is more complex than LRU because we need to:
1. Track access frequency for each key
2. Within the same frequency, maintain LRU order (least recently used should be evicted first)
3. Maintain a pointer to the minimum frequency for O(1) eviction

### Data Structures Used

1. **`map` - HashMap<Integer, Node>**
   - Maps key → Node (contains key, value, frequency, prev, next pointers)
   - Purpose: O(1) access to any cache entry

2. **`fmap` - HashMap<Integer, DoublyLL>**
   - Maps frequency → DoublyLinkedList of all nodes with that frequency
   - Purpose: O(1) access to all nodes with a specific frequency
   - Within each list: **head = most recently used**, **tail = least recently used**

3. **`minFreq` - Integer**
   - Tracks the minimum frequency currently in the cache
   - Purpose: O(1) eviction (always evict from the tail of minFreq list)

4. **`Node` - Custom Class**
   - Contains: key, value, freq, prev, next
   - Embedded in doubly linked lists

5. **`DoublyLL` - Custom Class**
   - Maintains doubly linked list with sentinel head and tail
   - Operations: addToHead(), remove(), removeTail()

### Step-by-Step Algorithm

#### **Constructor: `LFUCache(int capacity)`**
```
1. Initialize map (key → Node)
2. Initialize fmap (frequency → DoublyLL)
3. Store capacity
4. Set minFreq = 0
```

#### **Get Operation: `get(int key)`**
```
1. If key doesn't exist in map → return -1
2. Get the node from map
3. Update frequency of the node (calls updateFreq)
4. Return node.value
```

#### **Put Operation: `put(int key, int value)`**
```
1. If capacity == 0 → return (edge case)

2. If key already exists:
   a. Get the node
   b. Update node.value = value
   c. Update frequency (calls updateFreq)

3. If key is new:
   a. If cache is full (map.size >= capacity):
      - Get the DoublyLL at minFreq
      - Remove tail (least recently used with minimum frequency)
      - Remove that key from map
   
   b. Create new Node with key, value (freq = 1)
   c. Add to map
   d. Add to fmap at frequency 1 (create list if needed)
   e. Set minFreq = 1
```

#### **Update Frequency: `updateFreq(Node node)`**
```
1. Get oldfreq = node.freq
2. Get the DoublyLL at oldfreq from fmap
3. Remove node from that list

4. Critical Check:
   If oldfreq == minFreq AND the old list is now empty:
      - Increment minFreq++
   
   (This is THE KEY LOGIC - only increment minFreq when the minimum frequency list becomes empty)

5. If the old list is empty:
   - Remove oldfreq from fmap (cleanup)

6. Increment node.freq
7. Add node to the DoublyLL at new frequency (create if needed)
8. Add node to head (most recently used position)
```

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Solution |
|-------------|----------|
| O(1) get() | HashMap for direct key lookup |
| O(1) put() | HashMap for direct key access |
| O(1) eviction | Track minFreq + DoublyLL.removeTail() |
| LFU eviction | Maintain frequency lists in fmap |
| LRU tie-breaking | DoublyLL maintains insertion order (tail = LRU) |
| Frequency updates | Move nodes between frequency lists |

### Why HashMap?
- **Fast Access**: O(1) to check if key exists
- **Fast Retrieval**: O(1) to get node by key
- **Fast Deletion**: O(1) to remove key

### Why Doubly Linked List?
- **Fast Removal**: O(1) when you have node reference (unlike arrays)
- **Fast Insertion**: O(1) to add to head
- **Order Maintenance**: Tail = oldest, Head = newest
- **No Shifting**: Unlike arrays, no element shifting needed

### Why Multiple Frequency Lists?
- **Frequency Segregation**: All nodes with same frequency are in same list
- **Fast Frequency Lookup**: fmap[freq] gives all nodes with that frequency
- **Fast Eviction**: Always evict from minFreq list's tail

### Why Track minFreq?
- **Fast Eviction**: Don't need to search for minimum frequency
- **O(1) Guarantee**: Direct access to the frequency list we need to evict from

---

## Critical Edge Cases & Gotchas

### 1. **minFreq Update Logic** ⚠️ **MOST COMMON BUG**
**Wrong Approach**:
```java
if(oldLL.size == 0) fmap.remove(oldfreq);
if(oldfreq == minFreq) minFreq++;  // BUG!
```
**Why it's wrong**: This increments minFreq even if the old list still has nodes!

**Correct Approach**:
```java
if(oldfreq == minFreq && oldLL.size == 0) {
    minFreq++;  // Only increment when the min frequency list becomes empty
}
```

**Why it works**: minFreq should only change when:
- The current minimum frequency list becomes empty, AND
- We're updating a node from that minimum frequency

**Example**:
```
Cache: {key1: freq=1, key2: freq=1}
minFreq = 1

When we access key1:
1. Remove key1 from freq=1 list
2. freq=1 list still has key2, so size > 0
3. DON'T increment minFreq (it's still 1!)
4. Add key1 to freq=2 list

If we incorrectly incremented minFreq to 2, and we need to evict, we'd try to evict from freq=2 list, but key2 (freq=1) should be evicted!
```

### 2. **Capacity = 0 Edge Case**
```java
if(capacity == 0) return;  // Must handle this!
```
- Prevents division by zero or null pointer issues
- Cache with 0 capacity is valid but stores nothing

### 3. **Order of Operations in updateFreq**
Must do in this order:
1. Remove from old frequency list
2. Check if minFreq needs updating (based on old list being empty)
3. Clean up empty frequency list
4. Increment node frequency
5. Add to new frequency list

### 4. **Sentinel Nodes in DoublyLL**
- head and tail are dummy nodes (key=0, value=0)
- Actual nodes are between head and tail
- Simplifies edge cases (no null checks for prev/next)

### 5. **AddToHead vs AddToTail**
- **Always add to head** = most recently used
- **Always remove from tail** = least recently used
- This ensures LRU ordering within same frequency

---

## Complexity Analysis

### Time Complexity: **O(1)** for all operations

| Operation | Time | Reason |
|-----------|------|--------|
| get() | O(1) | HashMap lookup + updateFreq |
| put() | O(1) | HashMap operations + updateFreq |
| updateFreq() | O(1) | All operations are O(1): list removal, list insertion, freq map access |
| Eviction | O(1) | Direct access via minFreq, removeTail is O(1) |

**Why DoublyLL operations are O(1)**:
- `addToHead()`: Just pointer manipulation
- `remove(node)`: Have direct reference to node, just update prev/next pointers
- `removeTail()`: Direct access to tail.prev

### Space Complexity: **O(capacity)**

| Component | Space |
|-----------|-------|
| map | O(capacity) - stores at most capacity nodes |
| fmap | O(capacity) - all nodes distributed across frequency lists |
| Total nodes | O(capacity) - each node stored once |
| Overhead | O(capacity) - doubly linked list pointers |

**Note**: Although we have multiple frequency lists, the total number of nodes across all lists is always ≤ capacity.

---

## Visualization

### Example Walkthrough
```
Capacity = 2
Operations: put(1,1), put(2,2), get(1), put(3,3)
```

**Step 1: put(1, 1)**
```
map: {1 → Node(1,1,freq=1)}
fmap: {1 → [Node(1)]}
minFreq: 1

Frequency 1: Head ← Node(1) → Tail
```

**Step 2: put(2, 2)**
```
map: {1 → Node(1,1,freq=1), 2 → Node(2,2,freq=1)}
fmap: {1 → [Node(2), Node(1)]}  // Node(2) added to head
minFreq: 1

Frequency 1: Head ← Node(2) ← Node(1) → Tail
                    (MRU)      (LRU)
```

**Step 3: get(1)**
```
1. Remove Node(1) from freq=1 list
2. freq=1 list still has Node(2), so minFreq stays 1
3. Increment Node(1).freq to 2
4. Add Node(1) to freq=2 list

map: {1 → Node(1,1,freq=2), 2 → Node(2,2,freq=1)}
fmap: {
    1 → [Node(2)],
    2 → [Node(1)]
}
minFreq: 1

Frequency 1: Head ← Node(2) → Tail
Frequency 2: Head ← Node(1) → Tail
```

**Step 4: put(3, 3)** - Cache is full!
```
1. Need to evict: get freq=minFreq=1 list → [Node(2)]
2. removeTail from freq=1 list → evict Node(2)
3. Remove key=2 from map
4. Add new Node(3, freq=1)

map: {1 → Node(1,1,freq=2), 3 → Node(3,3,freq=1)}
fmap: {
    1 → [Node(3)],
    2 → [Node(1)]
}
minFreq: 1

Frequency 1: Head ← Node(3) → Tail
Frequency 2: Head ← Node(1) → Tail
```

---

## Common Mistakes & How to Avoid Them

### ❌ Mistake 1: Incorrect minFreq Update
```java
// WRONG
if(oldLL.size == 0) fmap.remove(oldfreq);
if(oldfreq == minFreq) minFreq++;
```
**Fix**: Combine the conditions
```java
// CORRECT
if(oldfreq == minFreq && oldLL.size == 0) {
    minFreq++;
}
```

### ❌ Mistake 2: Forgetting to Update minFreq on New Insert
```java
// In put() when adding new key
minFreq = 1;  // MUST set this!
```

### ❌ Mistake 3: Wrong Order in DoublyLL
```java
// WRONG: Adding to tail instead of head
void addToTail(Node node) { ... }  // Makes oldest the newest!
```
**Fix**: Always add to head (most recently used)

### ❌ Mistake 4: Not Cleaning Up Empty Frequency Lists
```java
// Should remove empty lists to avoid memory leaks
if(oldLL.size == 0) {
    fmap.remove(oldfreq);
}
```

### ❌ Mistake 5: Updating Value Without Updating Frequency
```java
// In put() when key exists
node.value = value;  // Good
// But MUST also call:
updateFreq(node);  // Don't forget!
```

---

## Alternative Approaches

### Approach 1: Using TreeMap (Not O(1))
- Store (frequency, timestamp) → key mapping
- TreeMap keeps entries sorted
- **Time**: O(log n) for get/put
- **Verdict**: Doesn't meet O(1) requirement ❌

### Approach 2: Min Heap for Frequencies (Not O(1))
- Heap to track minimum frequency
- **Time**: O(log n) for heap operations
- **Verdict**: Doesn't meet O(1) requirement ❌

### Approach 3: Array of Frequency Lists (Possible but Wasteful)
- Array of size [0...maxFrequency]
- Track minFreq by scanning array
- **Time**: O(1) if bounded frequencies
- **Space**: O(maxFrequency) - wasteful if frequencies get high ❌

**Conclusion**: HashMap + DoublyLL approach is the **optimal and standard solution**

---

## Key Takeaways

1. **LFU is harder than LRU** because we need to track both frequency AND recency
2. **minFreq tracking is critical** - must only update when minimum frequency list becomes empty
3. **Two-level structure**: frequency → list of nodes with that frequency
4. **DoublyLL enables O(1)** removal when we have node reference
5. **HashMap enables O(1)** access to nodes and frequency lists
6. **Order matters**: Always add to head (MRU), remove from tail (LRU)
7. **Edge cases**: capacity=0, single element, frequency ties

---

## Comparison: LRU vs LFU

| Aspect | LRU Cache | LFU Cache |
|--------|-----------|-----------|
| **Eviction Policy** | Least Recently Used | Least Frequently Used |
| **Tie-Breaking** | N/A | LRU within same frequency |
| **Data Structures** | 1 HashMap + 1 DoublyLL | 2 HashMaps + Multiple DoublyLLs |
| **Complexity** | Simpler | More Complex |
| **minFreq Tracking** | Not needed | Critical for O(1) |
| **Use Case** | Temporal locality | Access pattern based |

---

## Practice Tips

1. **Understand LRU first** - LFU builds on similar concepts
2. **Draw diagrams** - Visualize the frequency lists
3. **Trace examples** - Walk through operations step by step
4. **Test minFreq logic** - This is where most bugs occur
5. **Handle edge cases** - capacity=0, single element, all same frequency
6. **Implement DoublyLL correctly** - Sentinel nodes simplify logic

---

## Related Problems
- LRU Cache (Medium) - Prerequisite
- Design HashMap (Easy) - Hash structure understanding
- Design HashSet (Easy) - Hash structure understanding
- LRU Cache with TTL (Hard) - Extension with time-to-live
- All O(1) Data Structure (Hard) - Similar multi-structure design```

## Constraints

- 1 <= capacity <= 10,000
- 0 <= key <= 100,000
- 0 <= value <= 1,000,000,000
- At most 200,000 calls will be made to get and put

---

## Pattern to Use

**Pattern**: **Two-Level HashMap + Doubly Linked Lists (Advanced Data Structure Design)**

This is an extension of the LRU Cache problem but significantly more complex because we need to track **both frequency AND recency**.

Key components:
1. **HashMap for key lookup** - For O(1) key access
2. **HashMap for frequency buckets** - For O(1) frequency access
3. **Doubly Linked List per frequency** - For O(1) insertion/deletion and LRU ordering
4. **minFreq tracker** - For O(1) eviction

---

## Why This Pattern?

### Understanding LFU Cache:
- **LFU (Least Frequently Used)**: When the cache is full, we evict the item that has been accessed the **fewest times**.
- **Tie-breaker**: If multiple keys have the same frequency, evict the **least recently used** among them (LRU logic).
- Every `get` or `put` operation increments that key's frequency counter.

### Why Two Hash Maps?

**HashMap #1 - keyMap (key → Node)**:
- We need O(1) access to check if a key exists and get its value.
- Each node stores: key, value, and **frequency**.

**HashMap #2 - freqMap (frequency → DoublyLinkedList)**:
- We need to group all nodes with the same frequency together.
- When a node's frequency changes, we move it to a different frequency bucket.
- Each frequency has its own doubly linked list of nodes.

### Why Doubly Linked List Per Frequency?

- Within each frequency bucket, we need to maintain **LRU order** (for tie-breaking).
- **Head of list** = Most Recently Used (MRU) for that frequency
- **Tail of list** = Least Recently Used (LRU) for that frequency
- Need O(1) removal when a node's frequency increases (move to different bucket).
- Need O(1) insertion when adding to a frequency bucket.
- **Singly linked list won't work** because we can't delete a node in O(1) without access to its previous node.

### Why Track minFreq?

- When cache is full, we need to evict from the **minimum frequency bucket**.
- Without tracking minFreq, we'd need O(n) to scan all frequencies.
- With minFreq, eviction is O(1): just remove tail from freqMap[minFreq].

### Visual Representation:

```
keyMap:                    freqMap:
key → Node                 frequency → DoublyLinkedList
                      
1 → Node@123               1 → head <-> [key:3, val:30, freq:1] <-> tail
3 → Node@456               2 → head <-> [key:1, val:10, freq:2] <-> tail
4 → Node@789               
                           minFreq = 1
```

---

## Algorithm / Approach

### Data Structure Design:

```java
1. Node class:
   - int key
   - int value
   - int freq (frequency counter)
   - Node prev, next

2. DLinkedList class (for each frequency bucket):
   - Node head (dummy)
   - Node tail (dummy)
   - int size
   - Methods: addToHead(), remove(), removeTail()

3. LFUCache class:
   - HashMap<Integer, Node> keyMap (key → node)
   - HashMap<Integer, DLinkedList> freqMap (frequency → list)
   - int capacity
   - int minFreq (track minimum frequency for eviction)
```

### Core Operations:

#### 1. **get(key)**
```
Step 1: Check if key exists in keyMap
   - If NOT exists: return -1
   
Step 2: If exists:
   - Get the node from keyMap
   - Update frequency (move to higher frequency bucket)
   - Return node.value
   
Time: O(1)
```

#### 2. **put(key, value)**
```
Step 1: If capacity == 0, return (edge case)

Step 2: If key already exists:
   - Get node from keyMap
   - Update node.value
   - Update frequency (same as get operation)
   
Step 3: If key doesn't exist:
   - Check if cache is full:
     * If YES: evict LRU node from minFreq bucket
     * Remove from both keyMap and freqMap
   - Create new node with freq = 1
   - Add to keyMap
   - Add to freqMap[1] (at head)
   - Set minFreq = 1
   
Time: O(1)
```

#### 3. **updateFreq(Node node)** - Helper function
```
Step 1: Get current frequency (oldFreq)
Step 2: Remove node from freqMap[oldFreq]
Step 3: If freqMap[oldFreq] is now empty:
   - Remove oldFreq from freqMap
   - If oldFreq == minFreq: minFreq++ (important!)
   
Step 4: Increment node.freq
Step 5: Add node to freqMap[newFreq] at head (most recent)
Step 6: If freqMap[newFreq] doesn't exist, create new DLinkedList

Time: O(1)
```

---

## Step-by-Step Execution Example

Let's trace through the example:

```
Initial: LFUCache(2) - capacity = 2
keyMap = {}
freqMap = {}
minFreq = 0
```

### Step 1: put(1, 1)
```
- Key doesn't exist, cache not full
- Create node: [key=1, val=1, freq=1]
- Add to keyMap: {1 → Node[1,1,1]}
- Add to freqMap[1]: head <-> [1,1,1] <-> tail
- minFreq = 1

State:
keyMap: {1 → Node[1,1,1]}
freqMap: {1 → [1,1,1]}
minFreq: 1
```

### Step 2: put(2, 2)
```
- Key doesn't exist, cache not full
- Create node: [key=2, val=2, freq=1]
- Add to keyMap: {1 → Node[1,1,1], 2 → Node[2,2,1]}
- Add to freqMap[1] at head: head <-> [2,2,1] <-> [1,1,1] <-> tail
- minFreq = 1

State:
keyMap: {1 → Node[1,1,1], 2 → Node[2,2,1]}
freqMap: {1 → [[2,2,1], [1,1,1]]}  (2 is more recent)
minFreq: 1
```

### Step 3: get(1) → returns 1
```
- Key 1 exists in keyMap
- Current freq = 1
- Remove from freqMap[1]: head <-> [2,2,1] <-> tail
- Increment freq: 1 → 2
- Add to freqMap[2]: head <-> [1,1,2] <-> tail
- freqMap[1] still has [2,2,1], so minFreq stays 1
- Return 1

State:
keyMap: {1 → Node[1,1,2], 2 → Node[2,2,1]}
freqMap: {1 → [[2,2,1]], 2 → [[1,1,2]]}
minFreq: 1
```

### Step 4: put(3, 3)
```
- Key doesn't exist, cache is FULL (2/2)
- Need to evict: minFreq = 1, tail of freqMap[1] = [2,2,1]
- Remove [2,2,1] from keyMap and freqMap
- freqMap[1] is now empty, remove from freqMap
- Create node: [key=3, val=3, freq=1]
- Add to keyMap: {1 → Node[1,1,2], 3 → Node[3,3,1]}
- Add to freqMap[1]: head <-> [3,3,1] <-> tail
- minFreq = 1

State:
keyMap: {1 → Node[1,1,2], 3 → Node[3,3,1]}
freqMap: {1 → [[3,3,1]], 2 → [[1,1,2]]}
minFreq: 1
```

### Step 5: get(2) → returns -1
```
- Key 2 was evicted (not in keyMap)
- Return -1
```

### Step 6: get(3) → returns 3
```
- Key 3 exists in keyMap
- Current freq = 1
- Remove from freqMap[1]: becomes empty
- Increment freq: 1 → 2
- Add to freqMap[2] at head: head <-> [3,3,2] <-> [1,1,2] <-> tail
- freqMap[1] is empty, remove it
- minFreq was 1, list empty, so minFreq = 2
- Return 3

State:
keyMap: {1 → Node[1,1,2], 3 → Node[3,3,2]}
freqMap: {2 → [[3,3,2], [1,1,2]]}  (3 is more recent)
minFreq: 2
```

### Step 7: put(4, 4)
```
- Key doesn't exist, cache is FULL (2/2)
- Need to evict: minFreq = 2, tail of freqMap[2] = [1,1,2]
- Remove [1,1,2] from keyMap and freqMap
- freqMap[2]: head <-> [3,3,2] <-> tail
- Create node: [key=4, val=4, freq=1]
- Add to keyMap: {3 → Node[3,3,2], 4 → Node[4,4,1]}
- Add to freqMap[1]: head <-> [4,4,1] <-> tail
- minFreq = 1

State:
keyMap: {3 → Node[3,3,2], 4 → Node[4,4,1]}
freqMap: {1 → [[4,4,1]], 2 → [[3,3,2]]}
minFreq: 1
```

---

## Code Implementation

```java
class LFUCache {
    class Node {
        int key;
        int value;
        int freq;
        Node prev;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;  // New nodes start with frequency 1
        }
    }
    
    class DLinkedList {
        Node head;
        Node tail;
        int size;
        
        DLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }
        
        void addToHead(Node node) {
            Node nextNode = head.next;
            head.next = node;
            node.prev = head;
            node.next = nextNode;
            nextNode.prev = node;
            size++;
        }
        
        void remove(Node node) {
            Node prevNode = node.prev;
            Node nextNode = node.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            size--;
        }
        
        Node removeTail() {
            if (size == 0) return null;
            Node tailNode = tail.prev;
            remove(tailNode);
            return tailNode;
        }
    }
    
    private final int capacity;
    private final Map<Integer, Node> keyMap;           // key → node
    private final Map<Integer, DLinkedList> freqMap;   // frequency → list
    private int minFreq;
    
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.keyMap = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.minFreq = 0;
    }
    
    public int get(int key) {
        if (!keyMap.containsKey(key)) {
            return -1;
        }
        
        Node node = keyMap.get(key);
        updateFreq(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        if (capacity == 0) return;
        
        if (keyMap.containsKey(key)) {
            // Update existing key
            Node node = keyMap.get(key);
            node.value = value;
            updateFreq(node);
        } else {
            // Add new key
            if (keyMap.size() >= capacity) {
                // Evict LFU (and LRU if tie)
                DLinkedList minFreqList = freqMap.get(minFreq);
                Node evictNode = minFreqList.removeTail();
                keyMap.remove(evictNode.key);
            }
            
            // Create and insert new node
            Node newNode = new Node(key, value);
            keyMap.put(key, newNode);
            
            // Add to frequency 1 list
            freqMap.putIfAbsent(1, new DLinkedList());
            freqMap.get(1).addToHead(newNode);
            
            minFreq = 1;  // New node always has frequency 1
        }
    }
    
    private void updateFreq(Node node) {
        int oldFreq = node.freq;
        
        // Remove from old frequency list
        DLinkedList oldList = freqMap.get(oldFreq);
        oldList.remove(node);
        
        // If old frequency list is empty, clean up
        if (oldList.size == 0) {
            freqMap.remove(oldFreq);
            // If we removed the min frequency, increment it
            if (oldFreq == minFreq) {
                minFreq++;
            }
        }
        
        // Increment frequency
        node.freq++;
        
        // Add to new frequency list (at head - most recent)
        freqMap.putIfAbsent(node.freq, new DLinkedList());
        freqMap.get(node.freq).addToHead(node);
    }
}
```

---

## Complexity Analysis

### Time Complexity:
- **get(key)**: O(1)
  - HashMap lookup: O(1)
  - Remove from old freq list: O(1)
  - Insert to new freq list: O(1)
  - Update minFreq: O(1)
  
- **put(key, value)**: O(1)
  - HashMap operations: O(1)
  - Eviction (remove tail): O(1)
  - Update frequency: O(1)

### Space Complexity:
- **O(capacity)**: 
  - keyMap stores at most `capacity` nodes
  - All nodes across all frequency lists = capacity
  - freqMap can have at most `capacity` different frequencies
- Overall: **O(capacity)**

---

## Key Takeaways

1. **LFU = LRU with Frequency Tracking**
   - LFU evicts based on **access count** (frequency)
   - Tie-breaker uses **LRU logic** (recency)

2. **Two-Level Data Structure:**
   - **First level**: Frequency buckets (freqMap)
   - **Second level**: LRU ordering within each frequency (DLinkedList)

3. **Why Store Key in Node?**
   - When evicting, we need to remove from keyMap
   - Without the key in the node, we'd need O(n) HashMap iteration

4. **minFreq Optimization:**
   - Critical for O(1) eviction
   - Must update when:
     * New node added → minFreq = 1
     * Frequency list becomes empty → minFreq++

5. **Common Mistakes:**
   - Forgetting to update minFreq when frequency list empties
   - Not maintaining LRU order within frequency buckets (add to head, remove from tail)
   - Not removing empty frequency lists from freqMap
   - Using singly linked list (can't remove in O(1))

6. **LRU vs LFU Comparison:**
   - **LRU**: Single doubly linked list (simpler)
   - **LFU**: Multiple doubly linked lists grouped by frequency (complex)
   - **LRU**: Evicts based on recency only
   - **LFU**: Evicts based on frequency, then recency

7. **Real-World Usage:**
   - Database query caching (frequently accessed queries stay longer)
   - CDN content caching
   - CPU cache replacement policies
   - Memory management in operating systems

---

## Related Problems

- LRU Cache (Medium) - Simpler version without frequency tracking
- Design Browser History (Medium)
- Time-based Key-Value Store (Medium)
- All O(1) Data Structure (Hard)

---

## Summary

**Pattern**: Two-Level HashMap + Doubly Linked Lists

**Why This Approach:**
- keyMap provides O(1) key lookup
- freqMap groups nodes by frequency
- Doubly Linked Lists maintain LRU order within each frequency
- minFreq enables O(1) eviction

**Key Insight**: You need **THREE data structures** working together:
1. keyMap (key → node mapping)
2. freqMap (frequency → list mapping)
3. DLinkedList (LRU ordering per frequency)

This is one of the **hardest cache design problems** because it combines frequency tracking with LRU tie-breaking, requiring careful state management to achieve O(1) operations!

