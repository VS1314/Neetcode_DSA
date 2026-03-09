# Design HashMap
## Problem Description
**Difficulty**: Easy

Design a HashMap without using any built-in hash table libraries.
Implement the `MyHashMap` class:
- `MyHashMap()` - Initializes the object with an empty map.
- `void put(int key, int value)` - Inserts a (key, value) pair into the HashMap. If the key already exists in the map, update the corresponding value.
- `int get(int key)` - Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key.
- `void remove(int key)` - Removes the key and its corresponding value if the map contains the mapping for the key.
## Examples
### Example 1:
```
Input:
["MyHashMap", "put", "put", "get", "get", "put", "get", "remove", "get"]
[[], [1, 1], [2, 2], [1], [3], [2, 1], [2], [2], [2]]
Output:
[null, null, null, 1, -1, null, 1, null, -1]
Explanation:
MyHashMap myHashMap = new MyHashMap();
myHashMap.put(1, 1);    // map is now [[1,1]]
myHashMap.put(2, 2);    // map is now [[1,1], [2,2]]
myHashMap.get(1);       // return 1, map is now [[1,1], [2,2]]
myHashMap.get(3);       // return -1 (not found)
myHashMap.put(2, 1);    // map is now [[1,1], [2,1]] (update existing value)
myHashMap.get(2);       // return 1
myHashMap.remove(2);    // remove mapping for 2, map is now [[1,1]]
myHashMap.get(2);       // return -1 (not found)
```
## Constraints
- 0 <= key, value <= 1,000,000
- At most 10,000 calls will be made to put, get, and remove
---
## Pattern Recognition
**Primary Pattern**: **Array of Linked Lists (Separate Chaining)**
**Why This Pattern?**
- Need O(1) average time for get, put, remove operations
- Hash collisions must be handled
- No built-in hash table allowed
- Separate chaining with linked lists is classic approach
**Key Insight**: Use an array of buckets where each bucket is a linked list. Hash function maps keys to bucket indices. Collisions handled by storing multiple key-value pairs in same bucket's linked list.
**Related Patterns**:
1. **Direct Addressing** - If key range is small
2. **Open Addressing** - Alternative collision resolution
3. **Design HashSet** - Similar but without values
---
## Algorithm & Approach
### Core Insight
A HashMap fundamentally needs:
1. **Hash Function** - Convert key to bucket index
2. **Collision Resolution** - Handle multiple keys mapping to same index
3. **Dynamic Storage** - Efficiently store key-value pairs
**Why Separate Chaining:**
- Simple to implement
- Performance degrades gracefully with collisions
- No need for rehashing (for this problem)
- Each bucket independently manages its entries
### Data Structures Used
1. **Bucket Array** - Fixed-size array of linked lists
   - Size: Usually prime number or power of 2 (we'll use 1000)
   - Each index represents a bucket
2. **Node Class** - Stores key-value pairs
   - Contains: key, value, next pointer
   - Forms linked list within each bucket
3. **Hash Function** - `key % bucketSize`
   - Maps any key to valid bucket index [0, bucketSize-1]
### Step-by-Step Algorithm
#### **Hash Function Design**
```
hash(key) = key % bucketSize
Example with bucketSize = 1000:
- key = 1 → hash = 1 % 1000 = 1
- key = 1001 → hash = 1001 % 1000 = 1 (collision!)
- key = 2500 → hash = 2500 % 1000 = 500
```
#### **Approach: Array of Linked Lists (Separate Chaining)**
**Constructor: `MyHashMap()`**
```
1. Create array of size BUCKET_SIZE (e.g., 1000)
2. Initialize each bucket to null (empty linked list)
```
**Put Operation: `put(int key, int value)`**
```
1. Calculate bucket index: index = key % BUCKET_SIZE
2. Get the linked list at buckets[index]
3. Search through linked list for node with matching key:
   - If found: update node.value = value
   - If not found: create new node and add to front of list
```
**Get Operation: `get(int key)`**
```
1. Calculate bucket index: index = key % BUCKET_SIZE
2. Get the linked list at buckets[index]
3. Search through linked list for node with matching key:
   - If found: return node.value
   - If not found: return -1
```
**Remove Operation: `remove(int key)`**
```
1. Calculate bucket index: index = key % BUCKET_SIZE
2. Get the linked list at buckets[index]
3. Search through linked list for node with matching key:
   - If found: remove node from linked list
   - If not found: do nothing
```
### Code Implementation
```java
class MyHashMap {
    // Node class to store key-value pairs
    class Node {
        int key;
        int value;
        Node next;
        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
    private final int BUCKET_SIZE = 1000;
    private Node[] buckets;
    public MyHashMap() {
        buckets = new Node[BUCKET_SIZE];
    }
    // Hash function
    private int hash(int key) {
        return key % BUCKET_SIZE;
    }
    public void put(int key, int value) {
        int index = hash(key);
        // If bucket is empty, create new node
        if (buckets[index] == null) {
            buckets[index] = new Node(key, value);
            return;
        }
        // Search for existing key or find end of list
        Node current = buckets[index];
        Node prev = null;
        while (current != null) {
            if (current.key == key) {
                // Key exists, update value
                current.value = value;
                return;
            }
            prev = current;
            current = current.next;
        }
        // Key doesn't exist, add new node at end
        prev.next = new Node(key, value);
    }
    public int get(int key) {
        int index = hash(key);
        Node current = buckets[index];
        while (current != null) {
            if (current.key == key) {
                return current.value;
            }
            current = current.next;
        }
        return -1;  // Key not found
    }
    public void remove(int key) {
        int index = hash(key);
        if (buckets[index] == null) {
            return;  // Bucket is empty
        }
        // Special case: head node has the key
        if (buckets[index].key == key) {
            buckets[index] = buckets[index].next;
            return;
        }
        // Search for key in rest of list
        Node current = buckets[index];
        Node prev = null;
        while (current != null && current.key != key) {
            prev = current;
            current = current.next;
        }
        // If key found, remove node
        if (current != null) {
            prev.next = current.next;
        }
    }
}
```
### Alternative - Add to Front (Slightly Faster Put)
```java
public void put(int key, int value) {
    int index = hash(key);
    Node current = buckets[index];
    // Search for existing key
    while (current != null) {
        if (current.key == key) {
            current.value = value;
            return;
        }
        current = current.next;
    }
    // Key doesn't exist, add new node at front
    Node newNode = new Node(key, value);
    newNode.next = buckets[index];
    buckets[index] = newNode;
}
```
### Complexity Analysis
- **Time Complexity**: 
  - Average: O(1) for all operations (assuming uniform distribution)
  - Worst: O(n) if all keys hash to same bucket
- **Space Complexity**: O(n + BUCKET_SIZE) where n is number of key-value pairs
---
## Why This Strategy?
### Problem Requirements Analysis
| Requirement | Direct Array | Linked List Only | Separate Chaining |
|-------------|--------------|------------------|-------------------|
| Time (average) | O(1) ✅ | O(n) ❌ | O(1) ✅ |
| Space efficiency | O(10^6) ❌ | O(n) ✅ | O(n + buckets) ✅ |
| Handles collisions | No ❌ | N/A | Yes ✅ |
| Implementation | Simple | Simple | Medium |
| **Best choice** | No | No | **Yes** ✅ |
**Winner**: Separate Chaining - optimal time complexity with reasonable space usage!
### Why Array Size = 1000?
- **Trade-off**: Larger array → fewer collisions but more space
- 1000 buckets for up to 10,000 entries → average ~10 entries per bucket
- Good balance between time and space
- Could use 10007 (prime) for better distribution
### Why Linked List Over Other Structures?
- **vs Array**: No size limit, dynamic growth
- **vs ArrayList**: Simpler, no resizing needed
- **vs BST**: Overkill for small bucket sizes
---
## Critical Edge Cases & Gotchas
### 1. **Empty HashMap - Get**
```java
MyHashMap map = new MyHashMap();
map.get(1);  // Should return -1
```
### 2. **Update Existing Key**
```java
map.put(1, 10);
map.put(1, 20);  // Should update to 20, not create duplicate
map.get(1);      // Should return 20
```
### 3. **Remove Non-Existent Key**
```java
map.remove(99);  // Should not crash, just do nothing
```
### 4. **Remove Then Get**
```java
map.put(1, 10);
map.remove(1);
map.get(1);      // Should return -1
```
### 5. **Same Hash Different Keys (Collision)**
```java
map.put(1, 100);
map.put(1001, 200);  // Same hash: 1 % 1000 = 1
map.get(1);          // Should return 100, not 200
map.get(1001);       // Should return 200
```
### 6. **Remove Head of Bucket**
```java
map.put(1, 10);
map.put(1001, 20);  // Both hash to bucket 1
map.remove(1);       // Remove head node
map.get(1001);       // Should still return 20
```
---
## Major Areas Where We Might Go Wrong
### ❌ **MISTAKE 1: Not Checking for Duplicate Keys in Put**
```java
// WRONG - Creates duplicate keys!
public void put(int key, int value) {
    int index = hash(key);
    Node newNode = new Node(key, value);
    newNode.next = buckets[index];
    buckets[index] = newNode;  // Always adds new node, even if key exists!
}
```
**Why wrong**: Creates multiple nodes with same key in bucket.
**Fix**: Search for existing key first, update if found
```java
// CORRECT
Node current = buckets[index];
while (current != null) {
    if (current.key == key) {
        current.value = value;  // Update existing
        return;
    }
    current = current.next;
}
// Only add new node if key not found
```
### ❌ **MISTAKE 2: Forgetting to Handle Empty Bucket in Remove**
```java
// WRONG - NullPointerException if bucket is empty!
public void remove(int key) {
    int index = hash(key);
    Node current = buckets[index];
    if (current.key == key) {  // current might be null!
        buckets[index] = current.next;
    }
}
```
**Why wrong**: If bucket is empty, `current` is null, accessing `current.key` crashes.
**Fix**: Check for null first
```java
// CORRECT
if (buckets[index] == null) {
    return;
}
```
### ❌ **MISTAKE 3: Not Handling Head Node Removal Separately**
```java
// WRONG - Doesn't remove head node correctly!
public void remove(int key) {
    int index = hash(key);
    Node current = buckets[index];
    Node prev = null;
    while (current != null && current.key != key) {
        prev = current;
        current = current.next;
    }
    if (current != null) {
        prev.next = current.next;  // prev is null if removing head!
    }
}
```
**Why wrong**: If removing head node, `prev` is null, causes NullPointerException.
**Fix**: Special case for head node
```java
// CORRECT
if (buckets[index].key == key) {
    buckets[index] = buckets[index].next;
    return;
}
```
### ❌ **MISTAKE 4: Poor Hash Function**
```java
// WRONG - Always returns same index!
private int hash(int key) {
    return 0;  // All keys go to bucket 0, O(n) time!
}
```
**Why wrong**: All entries in one bucket, degenerates to O(n) linked list.
**Fix**: Use proper modulo hash
```java
// CORRECT
private int hash(int key) {
    return key % BUCKET_SIZE;
}
```
### ❌ **MISTAKE 5: Using Key Comparison with == Instead of Checking Values**
```java
// WRONG - Comparing Node references, not keys!
if (current == key) {  // Comparing Node with int!
    // ...
}
```
**Why wrong**: Type mismatch and wrong comparison.
**Fix**: Compare key values
```java
// CORRECT
if (current.key == key) {
    // ...
}
```
---
## Complexity Analysis
### Time Complexity
| Operation | Average Case | Worst Case | Explanation |
|-----------|--------------|------------|-------------|
| put() | O(1) | O(n) | Average: Constant bucket access + small chain. Worst: All in one bucket |
| get() | O(1) | O(n) | Average: Constant bucket access + small chain. Worst: All in one bucket |
| remove() | O(1) | O(n) | Average: Constant bucket access + small chain. Worst: All in one bucket |
**Average Case Analysis:**
- Number of entries: n = 10,000 (max from constraints)
- Number of buckets: BUCKET_SIZE = 1000
- Load factor: α = n / BUCKET_SIZE = 10,000 / 1000 = 10
- Chain length: ~10 nodes per bucket on average
- Time per operation: O(1 + α) = O(1 + 10) = O(1)
**Worst Case:**
- All 10,000 entries hash to same bucket
- Linked list of 10,000 nodes
- Time: O(10,000) = O(n)
### Space Complexity: **O(n + BUCKET_SIZE)**
| Component | Space |
|-----------|-------|
| Bucket array | O(BUCKET_SIZE) = O(1000) = O(1) |
| Stored entries | O(n) where n = number of put operations |
| Total | O(n + 1000) = O(n) |
---
## Visualization
### Example Walkthrough
```
BUCKET_SIZE = 1000
Operations:
1. put(1, 100)
2. put(1001, 200)  // Collision: 1001 % 1000 = 1
3. put(2, 300)
4. get(1001)
Bucket Array Structure:
Index 0:  null
Index 1:  [1:100] -> [1001:200] -> null
Index 2:  [2:300] -> null
Index 3:  null
...
Index 999: null
Step 4: get(1001)
- hash(1001) = 1
- Go to buckets[1]
- Search: [1:100] (key=1, not match) -> [1001:200] (key=1001, MATCH!)
- Return 200
```
---
## Comparison of Approaches
| Approach | Time (avg) | Space | Pros | Cons |
|----------|------------|-------|------|------|
| Direct Array (size 10^6) | O(1) | O(10^6) | Simple, true O(1) | Massive space waste |
| Single Linked List | O(n) | O(n) | Space efficient | Too slow |
| **Separate Chaining** | **O(1)** | **O(n)** | **Balanced** ✅ | Slightly complex |
| Open Addressing | O(1) | O(n) | Cache friendly | Complex removal |
**Best Choice**: Separate Chaining ✓
---
## Key Takeaways
1. **Hash Function**: Simple modulo works well for this problem
2. **Collision Handling**: Separate chaining with linked lists
3. **Bucket Size**: Balance between time and space (1000 is good)
4. **Update Check**: Always check if key exists before adding new node
5. **Edge Cases**: Empty bucket, removing head, collisions
6. **Complexity**: O(1) average with good hash distribution
---
## Interview Tips
**What to say in an interview:**
> "I'll design a HashMap using separate chaining with an array of linked lists. I'll use a bucket array of size 1000, where each bucket is the head of a linked list. The hash function will be `key % 1000` to map keys to bucket indices. For collisions, multiple key-value pairs can exist in the same bucket's linked list. For put(), I'll hash the key, search the bucket's list for the key - if found update the value, otherwise add a new node. For get() and remove(), similar hash-and-search approach. This gives O(1) average time for all operations."
**Key points to mention:**
1. **Pattern**: Array of linked lists (separate chaining)
2. **Hash function**: Modulo operation for uniform distribution
3. **Collision resolution**: Linked list in each bucket
4. **Complexity**: O(1) average, O(n) worst case
5. **Update vs Insert**: Check for existing key before adding
**If asked about improvements:**
> "For better performance, I could use a prime number for bucket size (like 10007) to reduce collisions. I could also implement dynamic resizing - when load factor exceeds a threshold, double the bucket array size and rehash all entries. For even better performance with larger datasets, I could use balanced BSTs instead of linked lists in each bucket (like Java's HashMap does)."
---
## Related Problems
| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Design HashMap** | Easy | **Separate Chaining** | **Hash table with collisions** ← This problem |
| Design HashSet | Easy | Separate Chaining | No values, only keys |
| Design Linked HashMap | Medium | HashMap + DLL | Maintains insertion order |
| LRU Cache | Medium | HashMap + DLL | Eviction policy |
| Design Twitter | Medium | HashMap + Heap/List | Complex data relationships |
**Pattern Family**: Data Structure Design - Hashing
---
## Final Pattern Label
✅ **Separate Chaining – Array of Linked Lists for Hash Collision Resolution**
**Remember:** When designing hash-based data structures → think array for O(1) access + linked list for collision handling!