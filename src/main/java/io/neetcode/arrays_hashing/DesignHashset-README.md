# Design HashSet

## Problem Description

**Difficulty**: Easy

Design a HashSet **without using any built-in hash table libraries**.

Implement the `MyHashSet` class:
- `MyHashSet()` — Initializes the object with an empty set
- `void add(int key)` — Inserts the value `key` into the HashSet
- `bool contains(int key)` — Returns whether `key` exists in the HashSet
- `void remove(int key)` — Removes `key` from the HashSet. If it does not exist, do nothing

## Examples

### Example 1:
```
Input:
["MyHashSet","add","add","contains","contains","add","contains","remove","contains"]
[[],[1],[2],[1],[3],[2],[2],[2],[2]]

Output:
[null,null,null,true,false,null,true,null,false]

Explanation:
MyHashSet myHashSet = new MyHashSet();
myHashSet.add(1);       // set = [1]
myHashSet.add(2);       // set = [1,2]
myHashSet.contains(1);  // return true
myHashSet.contains(3);  // return false (not found)
myHashSet.add(2);       // set = [1,2] (no duplicate)
myHashSet.contains(2);  // return true
myHashSet.remove(2);    // set = [1]
myHashSet.contains(2);  // return false (already removed)
```

## Constraints
- 0 <= key <= 1,000,000
- At most 10,000 calls will be made to `add`, `remove`, and `contains`

---

## Pattern Recognition

**Primary Pattern**: **Direct Addressing (Boolean Array)**

**Why This Pattern?**
- The key range is **bounded and known** — [0, 1,000,000]
- When the key range is bounded, we can use the key itself as the array index — no hash function needed
- This gives **true O(1)** time for all operations with the simplest possible code
- For unbounded keys, Separate Chaining (array of linked lists) would be needed

**Key Insight**:
```
Key range is bounded [0, 1,000,000]
→ Use index = key directly (direct addressing)
→ boolean[key] = true  means key is in the set
→ boolean[key] = false means key is not in the set
No hash function, no collision handling required.
```

**Pattern Elimination:**

| Pattern | Time | Space | Use When |
|---------|------|-------|----------|
| ArrayList (brute force) | O(n) | O(n) | ❌ Never |
| **Boolean Array** | ✅ O(1) | O(10^6) | ✅ Bounded keys (this problem) |
| Separate Chaining | O(1) avg | O(n) | Unbounded / large key range |
| BitSet | O(1) | O(10^6/32) | Ultra space-critical |

**Related Patterns**:
1. **Design HashMap** — Same concept but stores key-value pairs instead of just keys
2. **Contains Duplicate** — HashSet membership check
3. **Subarray Sum Equals K** — HashMap-based lookup

---

## Algorithm & Approach

### Core Insight

**Why Direct Addressing Works Here:**

```
Key range = [0, 1,000,000] → only 1,000,001 possible values

boolean[] data = new boolean[1_000_001]

add(key)      → data[key] = true
contains(key) → return data[key]
remove(key)   → data[key] = false

No hash function needed — key IS the index.
Each operation touches exactly one array cell → true O(1).
```

**Decision Flow:**
```
add(key):
    data[key] = true

contains(key):
    return data[key]

remove(key):
    data[key] = false
```

### Visual Understanding

```
Operations: add(1) → add(2) → contains(1) → contains(3) → remove(2) → contains(2)

Initial state:
  index: 0      1      2      3      ...  1000000
  data:  false  false  false  false  ...  false

add(1):       data[1] = true
  data:  false  TRUE   false  false  ...

add(2):       data[2] = true
  data:  false  true   TRUE   false  ...

contains(1):  return data[1] = true  ✓
contains(3):  return data[3] = false ✗

remove(2):    data[2] = false
  data:  false  true   FALSE  false  ...

contains(2):  return data[2] = false ✗
```

```
Separate Chaining collision example (BUCKET_SIZE = 10000):

add(1):     index = 1 % 10000 = 1   → buckets[1]: [1] → null
add(10001): index = 10001 % 10000 = 1 (collision!)
            → buckets[1]: [1] → [10001] → null

contains(1):
  index = 1, scan chain: found 1 → return true ✓

contains(10001):
  index = 1, scan chain: 1 → 10001 found → return true ✓
```

---

### Step-by-Step Algorithm

#### **Approach 1: Boolean Array — Direct Addressing (OPTIMAL)**

**Core Idea**:
- Allocate a `boolean[]` of size 1,000,001 (one slot per possible key)
- `add` sets the slot to `true`, `remove` sets it to `false`, `contains` reads the slot
- No hashing, no collisions, no linked lists

**Code Implementation**
```java
class MyHashSet {
    private boolean[] data;

    public MyHashSet() {
        data = new boolean[1_000_001];  // index 0 to 1,000,000 inclusive
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

**Step-by-Step Trace:**

Input: `["add","add","contains","contains","add","contains","remove","contains"]`
       `[[1],[2],[1],[3],[2],[2],[2],[2]]`

| Operation | key | Action | data[key] | Return |
|-----------|-----|--------|-----------|--------|
| add | 1 | data[1] = true | true | null |
| add | 2 | data[2] = true | true | null |
| contains | 1 | read data[1] | true | **true** |
| contains | 3 | read data[3] | false | **false** |
| add | 2 | data[2] = true (no-op, already true) | true | null |
| contains | 2 | read data[2] | true | **true** |
| remove | 2 | data[2] = false | false | null |
| contains | 2 | read data[2] | false | **false** |

**Complexity Analysis**
- **Time Complexity**: O(1) for all operations — single array access
- **Space Complexity**: O(1,000,001) — fixed, independent of number of operations

---

#### **Approach 2: Separate Chaining — Array of Linked Lists**

**Core Idea**:
- Use a fixed-size array of linked lists (buckets)
- Hash function: `key % BUCKET_SIZE` maps key to a bucket index
- Each bucket holds a linked list of keys that hash to that index
- Handles collisions naturally — multiple keys can share the same bucket

**When to prefer this?**
- Key range is large or unknown (can't use direct addressing)
- Space is constrained — only stores keys that are actually added

**Code Implementation**
```java
class MyHashSet {
    private static final int BUCKET_SIZE = 10000;
    private LinkedList<Integer>[] buckets;

    public MyHashSet() {
        buckets = new LinkedList[BUCKET_SIZE];
        for (int i = 0; i < BUCKET_SIZE; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    private int hash(int key) {
        return key % BUCKET_SIZE;
    }

    public void add(int key) {
        int index = hash(key);
        if (!buckets[index].contains(key)) {
            buckets[index].add(key);   // only add if not already present
        }
    }

    public boolean contains(int key) {
        int index = hash(key);
        return buckets[index].contains(key);
    }

    public void remove(int key) {
        int index = hash(key);
        buckets[index].remove(Integer.valueOf(key));
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(1) average — O(n/BUCKET_SIZE) per operation; worst case O(n) if all keys collide
- **Space Complexity**: O(n + BUCKET_SIZE) — n for stored keys, BUCKET_SIZE for the array

---

#### **Approach 3: BitSet — Ultra Space Efficient**

**Core Idea**:
- Pack 32 boolean values into a single `int` using bit positions
- `key / 32` gives the array index, `key % 32` gives the bit position within that int
- 32× more space-efficient than the boolean array approach

**Code Implementation**
```java
class MyHashSet {
    private int[] data;  // each int stores 32 keys as bits

    public MyHashSet() {
        data = new int[31251];  // ceil(1_000_001 / 32)
    }

    public void add(int key) {
        data[key / 32] |= (1 << (key % 32));
    }

    public boolean contains(int key) {
        return (data[key / 32] & (1 << (key % 32))) != 0;
    }

    public void remove(int key) {
        data[key / 32] &= ~(1 << (key % 32));
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(1) — single array access + bit operation
- **Space Complexity**: O(31,251) — 32× smaller than boolean array

---

## Comparison of Approaches

| Aspect | Boolean Array | Separate Chaining | BitSet |
|--------|--------------|-------------------|--------|
| **Time Complexity** | ✅ O(1) | O(1) avg | ✅ O(1) |
| **Space Complexity** | O(10^6) | O(n) | ✅ O(10^6/32) |
| **Code Simplicity** | ✅ Trivial | Moderate | Complex |
| **Handles Unbounded Keys** | ❌ No | ✅ Yes | ❌ No |
| **Preferred?** | ✅ Interviews | Production / unbounded | Space-critical |

**Recommendation**: Use **Boolean Array** in interviews — three one-liners, true O(1), zero edge cases. Mention Separate Chaining as the production alternative for unbounded keys.

---

## Key Takeaways

1. **Bounded Key Range → Direct Addressing**
   - When the key range is small and known, use `index = key` directly
   - No hash function, no collision, no linked list — just one array

2. **Array Size Must Be 1,000,001 Not 1,000,000**
   - Keys go from 0 to 1,000,000 **inclusive** → need 1,000,001 slots
   - Off-by-one here causes `ArrayIndexOutOfBoundsException` on `key = 1,000,000`

3. **Remove Means Set to false, Not null**
   - `boolean` can only be `true` or `false` — cannot be set to `null`
   - `data[key] = false` is the correct removal

4. **Separate Chaining Needs Duplicate Check on add**
   - A set must not contain duplicates
   - Always check `contains(key)` before adding in the chaining approach

5. **BitSet Trades Readability for Space**
   - Bit manipulation gives 32× space saving but harder to read and error-prone
   - Bring it up as a bonus in interviews, not as the primary solution

---

## Common Pitfalls

❌ **Mistake 1**: Array size 1,000,000 instead of 1,000,001
```java
// WRONG: key=1,000,000 causes ArrayIndexOutOfBoundsException
boolean[] data = new boolean[1_000_000];
data[1_000_000] = true;  // ← crashes!
```
✅ **Correct**: Include the upper bound
```java
boolean[] data = new boolean[1_000_001];
```

❌ **Mistake 2**: Setting `data[key] = null` in remove
```java
// WRONG: boolean cannot be null
public void remove(int key) {
    data[key] = null;  // compile error!
}
```
✅ **Correct**: Set to false
```java
data[key] = false;
```

❌ **Mistake 3**: Not checking for duplicates in Separate Chaining add
```java
// WRONG: adds the same key multiple times
public void add(int key) {
    int index = hash(key);
    buckets[index].add(key);  // no duplicate check!
}
```
✅ **Correct**: Check before adding
```java
if (!buckets[index].contains(key)) {
    buckets[index].add(key);
}
```

❌ **Mistake 4**: Using `remove(key)` instead of `remove(Integer.valueOf(key))` in Separate Chaining
```java
// WRONG: calls List.remove(int index) not List.remove(Object)
buckets[index].remove(key);  // removes element at index 'key', not value 'key'!
```
✅ **Correct**: Box to Integer to call remove by value
```java
buckets[index].remove(Integer.valueOf(key));
```

---

## Related Problems

1. **Design HashMap** (Easy) — Same design but stores key-value pairs instead of just keys
2. **Contains Duplicate** (Easy) — HashSet membership check (uses this data structure)
3. **Two Sum** (Easy) — HashMap complement lookup
4. **LRU Cache** (Medium) — Advanced data structure design with HashMap + Doubly Linked List
5. **Design Twitter** (Medium) — Complex data structure design with multiple HashMaps

---

## Edge Cases to Consider

1. **Add Duplicate Key**
   ```
   add(1); add(1);
   Boolean Array: data[1]=true (idempotent — no issue)
   Chaining: must check contains before adding ✓
   ```

2. **Remove Non-Existent Key**
   ```
   remove(99)  → data[99] already false → data[99]=false (no-op, no crash) ✓
   ```

3. **Contains on Empty Set**
   ```
   new MyHashSet(); contains(5) → data[5]=false → return false ✓
   ```

4. **Boundary Keys**
   ```
   add(0);         → data[0] = true  ✓
   add(1_000_000); → data[1_000_000] = true  ✓  (needs size 1_000_001)
   ```

5. **Add Then Remove Then Contains**
   ```
   add(1) → data[1]=true
   remove(1) → data[1]=false
   contains(1) → return false ✓
   ```

6. **Hash Collision in Separate Chaining**
   ```
   add(1); add(10001);  // both hash to bucket index 1
   contains(1)     → scan chain → true ✓
   contains(10001) → scan chain → true ✓
   remove(1)       → removes only key=1, key=10001 stays ✓
   ```

---

## Summary

**Problem**: Design a HashSet supporting `add`, `contains`, and `remove` in O(1) without built-in libraries.

**Solution**:
- Use a `boolean[]` of size 1,000,001 (direct addressing)
- `add(key)` → `data[key] = true`
- `contains(key)` → `return data[key]`
- `remove(key)` → `data[key] = false`

**Time**: O(1) for all operations | **Space**: O(1,000,001)

**Pattern**: Direct Addressing. When the key range is bounded and known, the key itself is the index — no hashing or collision handling needed. This is the simplest, fastest solution for this problem.

