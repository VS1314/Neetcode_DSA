# Design HashSet
## Problem Description
**Difficulty**: Easy

Design a HashSet without using any built-in hash table libraries.
Implement the `MyHashSet` class:
- `MyHashSet()` - Initializes the object with an empty set.
- `void add(int key)` - Inserts the value key into the HashSet.
- `bool contains(int key)` - Returns whether the value key exists in the HashSet or not.
- `void remove(int key)` - Removes the value key in the HashSet. If key does not exist in the HashSet, do nothing.
## Examples
### Example 1:
```
Input:
["MyHashSet", "add", "add", "contains", "contains", "add", "contains", "remove", "contains"]
[[], [1], [2], [1], [3], [2], [2], [2], [2]]
Output:
[null, null, null, true, false, null, true, null, false]
Explanation:
MyHashSet myHashSet = new MyHashSet();
myHashSet.add(1);      // set = [1]
myHashSet.add(2);      // set = [1, 2]
myHashSet.contains(1); // return True
myHashSet.contains(3); // return False, (not found)
myHashSet.add(2);      // set = [1, 2]
myHashSet.contains(2); // return True
myHashSet.remove(2);   // set = [1]
myHashSet.contains(2); // return False, (already removed)
```
## Constraints
- 0 <= key <= 1,000,000
- At most 10,000 calls will be made to add, remove, and contains
---
## Pattern Recognition
**Primary Pattern**: **Array of Linked Lists (Separate Chaining) / Direct Addressing**
**Why This Pattern?**
- Need O(1) average time for add, remove, contains operations
- Hash collisions must be handled (unless using direct addressing)
- No built-in hash table allowed
- Key range is bounded [0, 1,000,000]
**Key Insight**: Two main approaches - (1) Direct addressing if space allows, or (2) Hashing with separate chaining for space efficiency.
**Related Patterns**:
1. **Design HashMap** - Similar but with key-value pairs
2. **Bit Manipulation** - Ultra space-efficient variant
3. **Hash Table Design** - General hash collision resolution
---
## Algorithm & Approach
### Core Insight
A HashSet fundamentally needs:
1. **Storage** - Way to store keys
2. **Fast Lookup** - O(1) contains operation
3. **Uniqueness** - No duplicate keys
**Design Choice Trade-offs:**
- **Direct Addressing**: O(1) time, O(max_key) space
- **Hashing**: O(1) average time, O(n) space
- **Bit Manipulation**: O(1) time, O(max_key/32) space
### Step-by-Step Algorithm
#### **Approach 1: Brute Force - ArrayList**
```
Data Structure: List<Integer>
add(key):
  1. If not contains(key)
     2. Add to list
contains(key):
  1. Scan entire list
  2. Return true if found
remove(key):
  1. Find and remove from list
```
**Code Implementation**
```java
class MyHashSet {
    private List<Integer> data;
    public MyHashSet() {
        data = new ArrayList<>();
    }
    public void add(int key) {
        if (!contains(key)) {
            data.add(key);
        }
    }
    public boolean contains(int key) {
        return data.contains(key);
    }
    public void remove(int key) {
        data.remove(Integer.valueOf(key));
    }
}
```
**Complexity Analysis**
- **Time Complexity**: O(n) for all operations
- **Space Complexity**: O(n)
**Why Not Optimal?** Too slow - defeats the purpose of a HashSet.
#### **Approach 2: Boolean Array - Direct Addressing (SIMPLE & OPTIMAL)**
```
Data Structure: boolean[1_000_001]
add(key):
  1. data[key] = true
contains(key):
  1. return data[key]
remove(key):
  1. data[key] = false
```
**Code Implementation**
```java
class MyHashSet {
    private boolean[] data;
    public MyHashSet() {
        data = new boolean[1_000_001];
    }
    public void add(int key) {
        data[key] = true;
    }
    public boolean contains(int key) {
        return data[key];
    }
    public void remove(int key) {
        data[key] = false;
    }
}
```
**Complexity Analysis**
- **Time Complexity**: O(1) for all operations - truly constant!
- **Space Complexity**: O(1,000,000) - fixed, independent of n
**Why This Works**: Key range is bounded, so we can use index = key directly.
#### **Approach 3: Linked List + Hashing - Separate Chaining (SPACE EFFICIENT)**
```
Data Structure: ListNode[BUCKET_SIZE]
Hash Function: key % BUCKET_SIZE
add(key):
  1. index = hash(key)
  2. If key not in bucket[index]:
     3. Add to linked list
contains(key):
  1. index = hash(key)
  2. Search linked list at bucket[index]
remove(key):
  1. index = hash(key)
  2. Remove from linked list at bucket[index]
```
**Code Implementation**
```java
class MyHashSet {
    class Node {
        int key;
        Node next;
        Node(int key) {
            this.key = key;
            this.next = null;
        }
    }
    private final int BUCKET_SIZE = 10000;
    private Node[] buckets;
    public MyHashSet() {
        buckets = new Node[BUCKET_SIZE];
    }
    private int hash(int key) {
        return key % BUCKET_SIZE;
    }
    public void add(int key) {
        int index = hash(key);
        if (buckets[index] == null) {
            buckets[index] = new Node(key);
            return;
        }
        Node current = buckets[index];
        // Check if key already exists
        while (current != null) {
            if (current.key == key) {
                return;  // Already exists
            }
            if (current.next == null) {
                break;
            }
            current = current.next;
        }
        // Add new node at end
        current.next = new Node(key);
    }
    public boolean contains(int key) {
        int index = hash(key);
        Node current = buckets[index];
        while (current != null) {
            if (current.key == key) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
    public void remove(int key) {
        int index = hash(key);
        if (buckets[index] == null) {
            return;
        }
        // Special case: head node
        if (buckets[index].key == key) {
            buckets[index] = buckets[index].next;
            return;
        }
        Node current = buckets[index];
        while (current.next != null) {
            if (current.next.key == key) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
    }
}
```
**Complexity Analysis**
- **Time Complexity**: O(1) average, O(n) worst case
- **Space Complexity**: O(n + BUCKET_SIZE)
#### **Approach 4: Bit Manipulation - BitSet (MOST SPACE EFFICIENT)**
```
Data Structure: int[31251]  // 1,000,000 / 32 + 1
Each int stores 32 keys using bits
add(key):
  1. index = key / 32
  2. bit = key % 32
  3. data[index] |= (1 << bit)
contains(key):
  1. index = key / 32
  2. bit = key % 32
  3. return (data[index] & (1 << bit)) != 0
remove(key):
  1. index = key / 32
  2. bit = key % 32
  3. data[index] &= ~(1 << bit)
```
**Code Implementation**
```java
class MyHashSet {
    private int[] data;
    public MyHashSet() {
        data = new int[31251];  // 1,000,000 / 32 + 1
    }
    public void add(int key) {
        int index = key / 32;
        int bit = key % 32;
        data[index] |= (1 << bit);
    }
    public boolean contains(int key) {
        int index = key / 32;
        int bit = key % 32;
        return (data[index] & (1 << bit)) != 0;
    }
    public void remove(int key) {
        int index = key / 32;
        int bit = key % 32;
        data[index] &= ~(1 << bit);
    }
}
```
**Complexity Analysis**
- **Time Complexity**: O(1) for all operations
- **Space Complexity**: O(1,000,000 / 32) ≈ O(31,251 integers) - 32x more space efficient!
---
## Why This Strategy?
### Problem Requirements Analysis
| Approach | Time | Space | Complexity | Best For |
|----------|------|-------|------------|----------|
| ArrayList | O(n) | O(n) | Low | ❌ Never |
| **Boolean Array** | **O(1)** | **O(10^6)** | **Low** | **✅ Interviews** |
| Separate Chaining | O(1) avg | O(n) | Medium | Production code |
| **BitSet** | **O(1)** | **O(10^6/32)** | **High** | **Space-critical** |
**Winner for Interviews**: Boolean Array - simplest code, true O(1), easy to explain!
**Winner for Production**: Separate Chaining - space efficient, scales well
### Why Boolean Array is Best for This Problem?
- Key range is bounded [0, 1,000,000]
- Direct addressing gives true O(1) time
- Simple implementation with no edge cases
- Space is acceptable for modern systems
### When to Use Each Approach?
**Boolean Array:**
- ✅ Key range is small/known
- ✅ Interview setting
- ✅ Simplicity matters
**Separate Chaining:**
- ✅ Key range is huge/unknown
- ✅ Space matters
- ✅ Real-world applications
**BitSet:**
- ✅ Ultra space-critical
- ✅ Advanced interview (bonus points)
- ⚠️ More error-prone
---
## Critical Edge Cases & Gotchas
### 1. **Add Duplicate Key**
```java
set.add(1);
set.add(1);  // Should not create duplicate
```
### 2. **Remove Non-Existent Key**
```java
set.remove(99);  // Should not crash, just do nothing
```
### 3. **Contains After Remove**
```java
set.add(1);
set.remove(1);
set.contains(1);  // Should return false
```
### 4. **Boundary Values**
```java
set.add(0);          // Minimum key
set.add(1_000_000);  // Maximum key
```
### 5. **Empty Set Operations**
```java
MyHashSet set = new MyHashSet();
set.contains(5);  // Should return false
set.remove(5);    // Should not crash
```
### 6. **Hash Collision (Separate Chaining)**
```java
// Keys 1 and 10001 hash to same bucket (1 % 10000 = 1)
set.add(1);
set.add(10001);
set.contains(1);      // Should return true
set.contains(10001);  // Should return true
```
---
## Major Areas Where We Might Go Wrong
### ❌ **MISTAKE 1: Adding Duplicates in Separate Chaining**
```java
// WRONG - Doesn't check for duplicates!
public void add(int key) {
    int index = hash(key);
    Node newNode = new Node(key);
    newNode.next = buckets[index];
    buckets[index] = newNode;  // Always adds, even if exists!
}
```
**Why wrong**: Creates multiple nodes with same key.
**Fix**: Check if key exists first
```java
// CORRECT
if (contains(key)) {
    return;
}
// Then add
```
### ❌ **MISTAKE 2: Array Out of Bounds (Boolean Array)**
```java
// WRONG - Array too small!
boolean[] data = new boolean[1_000_000];  // Should be 1_000_001!
data[1_000_000] = true;  // ArrayIndexOutOfBoundsException!
```
**Why wrong**: Keys range from 0 to 1,000,000 inclusive (needs 1,000,001 slots).
**Fix**: Size should be 1_000_001
### ❌ **MISTAKE 3: Bit Shift Overflow**
```java
// WRONG - Bit shift overflow!
public void add(int key) {
    int bit = key % 32;
    data[0] |= (1 << bit);  // What if key > 32?
}
```
**Why wrong**: Didn't calculate index, always uses data[0].
**Fix**: Calculate index = key / 32
### ❌ **MISTAKE 4: Not Handling Empty Bucket in Remove**
```java
// WRONG - NullPointerException!
public void remove(int key) {
    int index = hash(key);
    if (buckets[index].key == key) {  // buckets[index] might be null!
        buckets[index] = buckets[index].next;
    }
}
```
**Why wrong**: Bucket might be empty (null).
**Fix**: Check for null first
```java
// CORRECT
if (buckets[index] == null) {
    return;
}
```
### ❌ **MISTAKE 5: Wrong Boolean Array Removal**
```java
// WRONG - Doesn't remove!
public void remove(int key) {
    data[key] = null;  // Can't set boolean to null!
}
```
**Why wrong**: boolean can only be true or false, not null.
**Fix**: Set to false
```java
// CORRECT
data[key] = false;
```
---
## Complexity Analysis
### Time Complexity
| Approach | add() | contains() | remove() |
|----------|-------|------------|----------|
| ArrayList | O(n) | O(n) | O(n) |
| **Boolean Array** | **O(1)** | **O(1)** | **O(1)** |
| Separate Chaining | O(1) avg, O(n) worst | O(1) avg, O(n) worst | O(1) avg, O(n) worst |
| **BitSet** | **O(1)** | **O(1)** | **O(1)** |
### Space Complexity
| Approach | Space | Notes |
|----------|-------|-------|
| ArrayList | O(n) | n = number of keys added |
| Boolean Array | O(1,000,001) | Fixed size |
| Separate Chaining | O(n + buckets) | Scales with usage |
| BitSet | O(31,251) | 1,000,000 / 32, most efficient! |
---
## Visualization
### Example: Boolean Array Approach
```
Operations: add(1), add(2), contains(1), contains(3), remove(2), contains(2)
Initial: data = [false, false, false, false, ...]
add(1):
data[1] = true
data = [false, true, false, false, ...]
add(2):
data[2] = true
data = [false, true, true, false, ...]
contains(1):
return data[1] = true ✓
contains(3):
return data[3] = false ✗
remove(2):
data[2] = false
data = [false, true, false, false, ...]
contains(2):
return data[2] = false ✗
```
### Example: Separate Chaining with Collisions
```
BUCKET_SIZE = 10000
Operations: add(1), add(10001), contains(1)
add(1):
index = 1 % 10000 = 1
buckets[1] = [1] -> null
add(10001):
index = 10001 % 10000 = 1  (collision!)
buckets[1] = [1] -> [10001] -> null
contains(1):
index = 1
Search buckets[1]: [1] ✓ Found!
return true
```
---
## Comparison of Approaches
| Approach | Time | Space | Code Complexity | Interview Score |
|----------|------|-------|-----------------|-----------------|
| ArrayList | O(n) | O(n) | Low | ❌ Poor |
| **Boolean Array** | **O(1)** | **O(10^6)** | **Very Low** | **✅ Excellent** |
| Separate Chaining | O(1) avg | O(n) | Medium | ✅ Good |
| BitSet | O(1) | O(10^6/32) | High | 🔥 Advanced |
**Best for Interviews**: Boolean Array ✓
---
## Key Takeaways
1. **Bounded Keys**: When key range is known and small, direct addressing wins
2. **Boolean Array**: Simplest O(1) solution for this problem
3. **Separate Chaining**: Better when key range is huge or unknown
4. **BitSet**: 32x space savings with same O(1) time
5. **Trade-offs**: Time vs Space vs Code Complexity
6. **Interview Strategy**: Start with boolean array, mention alternatives
---
## Interview Tips
**What to say in an interview:**
> "Since the key range is bounded [0, 1,000,000], I'll use direct addressing with a boolean array. This gives true O(1) time for all operations with straightforward implementation. Each index represents a key - if data[key] is true, the key exists in the set. This uses O(1,000,000) space which is acceptable and gives the simplest, fastest solution."
**Key points to mention:**
1. **Pattern**: Direct addressing for bounded keys
2. **Why boolean array**: Key range is known, need O(1) operations
3. **Complexity**: O(1) time, O(1,000,000) space
4. **Alternative**: Can mention separate chaining for unbounded keys
**If asked about space optimization:**
> "If space is critical, I can use bit manipulation with an int array of size 31,251. Each integer stores 32 keys using bits, reducing space by 32x while keeping O(1) time. However, the boolean array is simpler and space is acceptable for this problem size."
**If asked about real-world implementation:**
> "In production with unbounded keys, I'd use separate chaining with linked lists or balanced trees in each bucket, like Java's actual HashSet implementation. But for this problem with bounded keys, direct addressing is optimal."
---
## Related Problems
| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Design HashSet** | Easy | **Direct Addressing / Hashing** | **No values, only keys** ← This problem |
| Design HashMap | Easy | Hashing | Key-value pairs |
| Design Linked HashSet | Medium | HashMap + DLL | Maintains insertion order |
| LFU Cache | Hard | Multiple data structures | Frequency tracking |
| Design Twitter | Medium | HashMap + Timeline | Complex relationships |
**Pattern Family**: Data Structure Design - Hash-based Storage
---
## Final Pattern Label
✅ **Direct Addressing – Boolean Array for Bounded Key Range**
**Remember:** When key range is bounded and known → think direct addressing (index = key) for simplest O(1) solution!