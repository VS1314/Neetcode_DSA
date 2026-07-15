# Time Based Key-Value Store

## Problem Description

**Difficulty**: Medium

Design a time-based key-value data structure that can store **multiple values** for the same key at **different timestamps** and retrieve the key's value at a certain timestamp.

Implement the `TimeMap` class:

- **`TimeMap()`**: Initializes the object of the data structure.
- **`void set(String key, String value, int timestamp)`**: Stores the key `key` with the value `value` at the given time `timestamp`.
- **`String get(String key, int timestamp)`**: Returns a value such that `set` was called previously, with `timestamp_prev <= timestamp`. If there are multiple such values, it returns the value associated with the **largest** `timestamp_prev`. If there are no values, it returns `""`.

**Important**: All the timestamps of `set` are **strictly increasing** for each key.

## Examples

### Example 1:
```
Input:
["TimeMap", "set", "get", "get", "set", "get"]
[[], ["alice", "happy", 1], ["alice", 1], ["alice", 2], ["alice", "sad", 3], ["alice", 3]]

Output:
[null, null, "happy", "happy", null, "sad"]

Explanation:
TimeMap timeMap = new TimeMap();
timeMap.set("alice", "happy", 1);  // store key="alice", value="happy", timestamp=1
timeMap.get("alice", 1);           // return "happy" (exact match at timestamp 1)
timeMap.get("alice", 2);           // return "happy" (no value at 2, return value at timestamp 1)
timeMap.set("alice", "sad", 3);    // store key="alice", value="sad", timestamp=3
timeMap.get("alice", 3);           // return "sad" (exact match at timestamp 3)
```

### Example 2:
```
Input:
["TimeMap", "set", "set", "get"]
[[], ["key", "val1", 5], ["key", "val2", 10], ["key", 7]]

Output:
[null, null, null, "val1"]

Explanation:
TimeMap timeMap = new TimeMap();
timeMap.set("key", "val1", 5);
timeMap.set("key", "val2", 10);
timeMap.get("key", 7);  // return "val1" (timestamp 5 is largest <= 7)
```

### Example 3:
```
Input:
["TimeMap", "set", "get"]
[[], ["key", "val", 10], ["key", 5]]

Output:
[null, null, ""]

Explanation:
TimeMap timeMap = new TimeMap();
timeMap.set("key", "val", 10);
timeMap.get("key", 5);  // return "" (no timestamp <= 5)
```

### Example 4:
```
Input:
["TimeMap", "set", "set", "set", "get"]
[[], ["key", "a", 1], ["key", "b", 2], ["key", "c", 3], ["key", 2]]

Output:
[null, null, null, null, "b"]

Explanation:
Multiple values stored for same key
get(key, 2) returns exact match "b"
```

### Example 5:
```
Input:
["TimeMap", "get"]
[[], ["key", 1]]

Output:
[null, ""]

Explanation:
get() on non-existent key returns ""
```

### Example 6:
```
Input:
["TimeMap", "set", "set", "set", "get", "get", "get"]
[[], ["k", "v1", 10], ["k", "v2", 20], ["k", "v3", 30], ["k", 15], ["k", 25], ["k", 35]]

Output:
[null, null, null, null, "v1", "v2", "v3"]

Explanation:
get(k, 15): largest timestamp <= 15 is 10, return "v1"
get(k, 25): largest timestamp <= 25 is 20, return "v2"
get(k, 35): largest timestamp <= 35 is 30, return "v3"
```

### Example 7:
```
Input:
["TimeMap", "set", "set", "get", "get"]
[[], ["key1", "val1", 5], ["key2", "val2", 10], ["key1", 5], ["key2", 10]]

Output:
[null, null, null, "val1", "val2"]

Explanation:
Multiple keys with separate timelines
```

### Example 8:
```
Input:
["TimeMap", "set", "set", "set", "get"]
[[], ["key", "a", 1], ["key", "b", 5], ["key", "c", 10], ["key", 3]]

Output:
[null, null, null, null, "a"]

Explanation:
get(key, 3): timestamps [1, 5, 10], largest <= 3 is 1, return "a"
```

### Example 9:
```
Input:
["TimeMap", "set", "get", "get"]
[[], ["key", "val", 0], ["key", 0], ["key", 100]]

Output:
[null, null, "val", "val"]

Explanation:
Timestamp can be 0
```

### Example 10:
```
Input:
["TimeMap", "set", "set", "set", "set", "get"]
[[], ["k", "v1", 1], ["k", "v2", 2], ["k", "v3", 3], ["k", "v4", 4], ["k", 2]]

Output:
[null, null, null, null, null, "v2"]

Explanation:
Many values, binary search finds exact match
```

## Constraints
- 1 <= key.length, value.length <= 100
- `key` and `value` consist of lowercase English letters and digits
- 0 <= timestamp <= 10^7
- All the timestamps of `set` are **strictly increasing** for each key
- At most 2 * 10^5 calls will be made to `set` and `get`

**Recommended Complexity**: 
- `set()`: O(1) time
- `get()`: O(log n) time, where n is number of values for a key
- Space: O(m * n), where m is number of keys, n is average values per key

---

## Pattern Recognition

**Primary Pattern**: **HashMap + Binary Search (Time-Series Data)**

**Why This Pattern?**
- Need **fast key lookup** (HashMap)
- Multiple values per key at **different timestamps**
- Need to find **largest timestamp <= target** (Binary Search)
- Timestamps are **strictly increasing** (sorted property)

**Key Insight**: HashMap of Lists with Binary Search
```
Data Structure:

HashMap<String, List<Pair<Integer, String>>>
         ↑key     ↑     ↑timestamp ↑value
         
For key "alice":
  Timestamp: [1,    3,    5,    8]
  Value:     ["a",  "b",  "c",  "d"]
  
get("alice", 6):
  Binary search on timestamps [1, 3, 5, 8]
  Find largest <= 6
  Answer: timestamp 5, value "c"
```

**Why HashMap?**
```
Need O(1) key lookup:

set("alice", "happy", 1)
  Look up "alice" → O(1)
  Append to list → O(1)
  Total: O(1) ✓

get("alice", 2)
  Look up "alice" → O(1)
  Binary search list → O(log n)
  Total: O(log n) ✓

HashMap is perfect for key-value storage!
```

**Why Binary Search?**
```
Timestamps are strictly increasing:

Timestamps for "alice": [1, 3, 5, 8, 10]
                         ↑  ↑  ↑  ↑  ↑
                       Already sorted!

get("alice", 6):
  Need largest timestamp <= 6
  Binary search: find 5 in O(log n)
  
  Linear search: O(n)
  Binary search: O(log n) ✓
  
Much faster for many timestamps!
```

**The Search Problem**:
```
Given sorted timestamps: [1, 3, 5, 8, 10]
Find largest timestamp <= target

Example: target = 6
  Answer: 5 (index 2)

Example: target = 3
  Answer: 3 (index 1, exact match)

Example: target = 0
  Answer: none (return "")

Example: target = 100
  Answer: 10 (index 4, largest in array)

This is "Search Insert Position" variant!
Find rightmost position where timestamp <= target.
```

**Binary Search Strategy**:
```
Find largest timestamp <= target:

Method 1: Modified binary search
  Track best answer seen so far
  When nums[mid] <= target:
    Update answer = mid
    Search right for potentially larger match
  When nums[mid] > target:
    Search left

Method 2: Find upper_bound then go left
  Find first timestamp > target
  Return previous position

We'll use Method 1 (cleaner)!
```

**Example Showing Binary Search**:
```
Timestamps: [1, 3, 5, 8, 10]
Values:     ["a", "b", "c", "d", "e"]

get(key, 6):

Step 1: left=0, right=4, mid=2
  timestamps[2] = 5 <= 6? Yes
  Found candidate! answer = 2
  Search right for larger: left = 3

Step 2: left=3, right=4, mid=3
  timestamps[3] = 8 <= 6? No
  Too large, search left: right = 2

Step 3: left=3 > right=2
  Done! Return values[answer] = values[2] = "c" ✓
```

**Why Strictly Increasing Matters**:
```
Strictly increasing timestamps:
  set("key", "a", 1)
  set("key", "b", 3)
  set("key", "c", 5)
  
  Timestamps: [1, 3, 5] (sorted, no duplicates)
  Can use binary search efficiently!

If timestamps could decrease:
  set("key", "a", 5)
  set("key", "b", 3)
  set("key", "c", 8)
  
  Timestamps: [5, 3, 8] (unsorted!)
  Would need to sort or use different approach
  
Strictly increasing is a gift!
Makes problem easier and efficient.
```

**Data Structure Choice**:
```
Option 1: HashMap<String, List<Pair>>
  ✓ O(1) set
  ✓ O(log n) get
  ✓ Simple
  ✓ Our choice!

Option 2: TreeMap for each key
  O(log n) set (tree insertion)
  O(log n) get
  More complex

Option 3: Sorted array per key
  O(n) set (array insertion)
  O(log n) get
  Too slow for set

HashMap + List is optimal!
```

**Why This is Optimal**:
```
set() requirements:
  Must store key-value-timestamp
  O(1) is best possible
  HashMap + append achieves this ✓

get() requirements:
  Search sorted timestamps
  O(log n) with binary search
  Cannot do better (Ω(log n) lower bound)
  
Space:
  Must store all values
  O(m * n) where m keys, n values per key
  Cannot do better
  
This solution is optimal!
```

**Related Patterns**:
1. **HashMap** — Fast key lookup
2. **Binary Search** — Search sorted list
3. **Time-Series Data** — Multiple timestamped values
4. **Design Problem** — Implement data structure

---

## Algorithm & Approach

### Core Insight

**Why HashMap + Binary Search Works:**
```
Key properties:
  1. Need fast key lookup → HashMap
  2. Multiple values per key → List
  3. Timestamps sorted → Binary search
  4. Find largest <= target → Modified binary search
```

**The Optimal Strategy**:
```
Data Structure:
  HashMap<String, List<Pair<Integer, String>>>
  
set(key, value, timestamp):
  1. Get or create list for key
  2. Append (timestamp, value) pair
  3. O(1) time
  
get(key, timestamp):
  1. Get list for key
  2. Binary search for largest timestamp <= target
  3. Return corresponding value or ""
  4. O(log n) time
```

### Step-by-Step Algorithm

---

#### **Approach 1: HashMap + Binary Search - OPTIMAL**

**Core Idea**:
- HashMap maps key → list of (timestamp, value) pairs
- `set()`: append to list (O(1))
- `get()`: binary search on timestamps (O(log n))

**Algorithm**
```
class TimeMap:
    Initialize:
        map = new HashMap<String, List<Pair<Integer, String>>>()
    
    set(key, value, timestamp):
        if key not in map:
            map[key] = new ArrayList()
        
        map[key].add(new Pair(timestamp, value))
        
        Time: O(1)
    
    get(key, timestamp):
        if key not in map:
            return ""
        
        list = map[key]
        
        // Binary search for largest timestamp <= target
        left = 0
        right = list.size() - 1
        result = ""
        
        while left <= right:
            mid = left + (right - left) / 2
            
            if list[mid].timestamp <= timestamp:
                // Found candidate
                result = list[mid].value
                left = mid + 1  // Search right for larger
            else:
                // Too large
                right = mid - 1
        
        return result
        
        Time: O(log n)
```

**Code Implementation**
```java
class TimeMap {
    // Pair class to store timestamp and value
    private static class Pair {
        int timestamp;
        String value;
        
        Pair(int timestamp, String value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }
    
    private Map<String, List<Pair>> map;
    
    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        // Get or create list for this key
        if (!map.containsKey(key)) {
            map.put(key, new ArrayList<>());
        }
        
        // Append new timestamp-value pair
        map.get(key).add(new Pair(timestamp, value));
    }
    
    public String get(String key, int timestamp) {
        // Key doesn't exist
        if (!map.containsKey(key)) {
            return "";
        }
        
        List<Pair> list = map.get(key);
        
        // Binary search for largest timestamp <= target
        int left = 0;
        int right = list.size() - 1;
        String result = "";
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (list.get(mid).timestamp <= timestamp) {
                // Found a candidate, save it
                result = list.get(mid).value;
                // Search right for potentially larger timestamp
                left = mid + 1;
            } else {
                // Current timestamp too large, search left
                right = mid - 1;
            }
        }
        
        return result;
    }
}
```

**Example Walkthrough**

Input: 
```
set("alice", "happy", 1)
set("alice", "sad", 3)
get("alice", 2)
```

**After set("alice", "happy", 1):**
```
map = {
  "alice": [(1, "happy")]
}
```

**After set("alice", "sad", 3):**
```
map = {
  "alice": [(1, "happy"), (3, "sad")]
}
```

**get("alice", 2):**
```
list = [(1, "happy"), (3, "sad")]
Timestamps: [1, 3]

Binary search for largest timestamp <= 2:

Iteration 1:
  left=0, right=1, mid=0
  timestamps[0] = 1 <= 2? Yes
  result = "happy"
  left = 1

Iteration 2:
  left=1, right=1, mid=1
  timestamps[1] = 3 <= 2? No
  right = 0

left=1 > right=0, done
Return result = "happy" ✓
```

**Complexity Analysis**
- **set()**: O(1) — HashMap lookup + append to list
- **get()**: O(log n) — HashMap lookup + binary search, n = values for key
- **Space**: O(m * n) — m keys, n average values per key

---

#### **Approach 2: TreeMap for Each Key - ALTERNATIVE**

**Core Idea**: Use TreeMap (Red-Black tree) for each key's timeline.

**Code Implementation**
```java
class TimeMap {
    private Map<String, TreeMap<Integer, String>> map;
    
    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        if (!map.containsKey(key)) {
            map.put(key, new TreeMap<>());
        }
        map.get(key).put(timestamp, value);
    }
    
    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) {
            return "";
        }
        
        TreeMap<Integer, String> treeMap = map.get(key);
        // floorEntry returns largest key <= timestamp
        Map.Entry<Integer, String> entry = treeMap.floorEntry(timestamp);
        
        return entry == null ? "" : entry.getValue();
    }
}
```

**Key Difference**: 
- Uses built-in TreeMap with `floorEntry()`
- `set()`: O(log n) (tree insertion)
- `get()`: O(log n) (tree search)
- Simpler code but slower set()

**Complexity Analysis**
- **set()**: O(log n) ⚠️ (slower than O(1))
- **get()**: O(log n)
- **Space**: O(m * n)

---

#### **Approach 3: HashMap + Linear Search - TOO SLOW**

**Core Idea**: Linear search instead of binary search.

**Code Implementation**
```java
public String get(String key, int timestamp) {
    if (!map.containsKey(key)) {
        return "";
    }
    
    List<Pair> list = map.get(key);
    String result = "";
    
    // Linear search from start
    for (Pair pair : list) {
        if (pair.timestamp <= timestamp) {
            result = pair.value;
        } else {
            break;  // Rest are larger
        }
    }
    
    return result;
}
```

**Key Difference**: 
- O(n) get instead of O(log n)
- Too slow for large n

**Complexity Analysis**
- **set()**: O(1)
- **get()**: O(n) ❌
- **Space**: O(m * n)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | set() | get() | Space | Uses Sorted Property | Recommended |
|----------|-------|-------|-------|----------------------|-------------|
| **HashMap + Binary Search** | **O(1)** | **O(log n)** | **O(m*n)** | **Yes ✅** | **Yes ✅** |
| TreeMap | O(log n) | O(log n) | O(m*n) | Built-in | Slower set |
| Linear Search | O(1) | O(n) | O(m*n) | No | Too slow ❌ |

**Winner**: **HashMap + Binary Search** — optimal time complexity!

### Why HashMap Over Other Structures

```
Need O(1) key lookup:

HashMap:
  Average O(1) lookup ✓
  O(1) insertion ✓
  Perfect!

TreeMap:
  O(log m) lookup (m = number of keys)
  O(log m) insertion
  Slower

Array:
  O(m) lookup
  Too slow

HashMap is optimal for key storage!
```

### Why List Over TreeMap for Values

```
For each key's values:

List + Binary Search:
  set(): O(1) append ✓
  get(): O(log n) binary search
  Simple
  
TreeMap:
  set(): O(log n) tree insertion
  get(): O(log n) tree search
  More complex

List is faster for set()!
Timestamps strictly increasing → append is enough.
```

### Why Binary Search on Timestamps

```
Finding largest timestamp <= target:

Linear search:
  Check each timestamp: O(n)
  For n=1000: 1000 checks ❌

Binary search:
  Halve space each iteration: O(log n)
  For n=1000: ~10 checks ✓
  
100× faster!

Timestamps sorted → binary search works!
```

### Why Track Result Variable

```
Binary search for largest <= target:

Method 1: Track result (our approach)
  When found candidate (timestamp <= target):
    Save result
    Continue searching right
  Cleaner logic ✓

Method 2: Calculate after loop
  Find position then check boundaries
  More complex

Tracking result is cleaner!
```

### Why left = mid + 1 When Found

```
When timestamps[mid] <= target:
  mid is a valid answer
  But might be larger one to the right!
  
Example: [1, 3, 5, 8], target = 10
  mid=2, timestamps[2]=5 <= 10? Yes
  Save result = 5
  But 8 is also <= 10 and larger!
  Search right: left = mid + 1
  
Keep searching right for larger matches!
```

### Why Strictly Increasing Helps

```
With strictly increasing timestamps:
  Just append to list: O(1)
  No need to sort
  No duplicates to handle
  
Without this guarantee:
  Would need to insert sorted: O(n)
  Or sort after: O(n log n)
  Or handle duplicates
  
Strictly increasing makes problem easier!
```

---

## Critical Edge Cases & Gotchas

### 1. **Key Doesn't Exist**
```java
get("nonexistent", 5)
Return: ""
Check if key in map before searching
```

### 2. **Timestamp Smaller Than All**
```java
set("key", "val", 10)
get("key", 5)
Return: "" (no timestamp <= 5)
Binary search returns "" when no match
```

### 3. **Timestamp Larger Than All**
```java
set("key", "val1", 1)
set("key", "val2", 3)
get("key", 100)
Return: "val2" (largest available)
```

### 4. **Exact Timestamp Match**
```java
set("key", "val", 5)
get("key", 5)
Return: "val" (exact match)
```

### 5. **Single Value for Key**
```java
set("key", "val", 1)
get("key", 1)
Return: "val"
Binary search works with single element
```

### 6. **Multiple Keys**
```java
set("key1", "a", 1)
set("key2", "b", 1)
get("key1", 1) → "a"
get("key2", 1) → "b"
Each key has separate timeline
```

### 7. **Timestamp 0**
```java
set("key", "val", 0)
get("key", 0)
Return: "val"
0 is valid timestamp
```

### 8. **Many Values for Same Key**
```java
set("key", "v1", 1)
set("key", "v2", 2)
...
set("key", "v1000", 1000)
get("key", 500) → binary search efficient
```

### 9. **Get Before Any Set**
```java
TimeMap map = new TimeMap()
get("key", 5)
Return: "" (no values yet)
```

### 10. **Sequential Sets and Gets**
```java
set("key", "a", 1)
get("key", 1) → "a"
set("key", "b", 2)
get("key", 2) → "b"
get("key", 1) → "a" (still valid)
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Initializing List for New Key**
```java
// WRONG - assumes list exists
public void set(String key, String value, int timestamp) {
    map.get(key).add(new Pair(timestamp, value));
    // NullPointerException if key doesn't exist!
}
```

**Why wrong**: First set for key will fail!

**Fix**: Check and initialize
```java
if (!map.containsKey(key)) {
    map.put(key, new ArrayList<>());
}
map.get(key).add(new Pair(timestamp, value));
```

### ❌ **MISTAKE 2: Not Handling Missing Key in get()**
```java
// WRONG - assumes key exists
public String get(String key, int timestamp) {
    List<Pair> list = map.get(key);
    // NullPointerException if key doesn't exist!
    // ... binary search
}
```

**Why wrong**: get() on non-existent key crashes!

**Fix**: Check first
```java
if (!map.containsKey(key)) {
    return "";
}
List<Pair> list = map.get(key);
```

### ❌ **MISTAKE 3: Wrong Binary Search Condition**
```java
// WRONG - uses >= instead of <=
if (list.get(mid).timestamp >= timestamp) {
    result = list.get(mid).value;
    left = mid + 1;
}
```

**Why wrong**: Looking for largest <= not >=!

**Dry run failure for timestamps=[1,3,5], target=2:**
```
mid=1, timestamps[1]=3
3 >= 2? Yes
result = value at timestamp 3

But we want timestamp 1 (largest <= 2)!
Wrong answer! ❌
```

**Fix**: Use <=
```java
if (list.get(mid).timestamp <= timestamp) {
    result = list.get(mid).value;
    left = mid + 1;
}
```

### ❌ **MISTAKE 4: Not Saving Result Before Continuing Search**
```java
// WRONG - doesn't save result
while (left <= right) {
    int mid = left + (right - left) / 2;
    
    if (list.get(mid).timestamp <= timestamp) {
        // Missing: result = list.get(mid).value
        left = mid + 1;
    } else {
        right = mid - 1;
    }
}
return result;  // Returns "" always!
```

**Why wrong**: Never saves the found value!

**Fix**: Save result
```java
if (list.get(mid).timestamp <= timestamp) {
    result = list.get(mid).value;  // Save it!
    left = mid + 1;
}
```

### ❌ **MISTAKE 5: Searching Left When Found Match**
```java
// WRONG - searches left
if (list.get(mid).timestamp <= timestamp) {
    result = list.get(mid).value;
    right = mid - 1;  // Should be left = mid + 1
}
```

**Why wrong**: Want largest match, should search right!

**Dry run failure for timestamps=[1,3,5,8], target=10:**
```
mid=1, timestamps[1]=3 <= 10? Yes
result = "value at 3"
right = 0

Search [0, 0]:
  mid=0, timestamps[0]=1 <= 10? Yes
  result = "value at 1"
  right = -1

Return "value at 1"

But largest <= 10 is 8! ❌
```

**Fix**: Search right for larger
```java
left = mid + 1;
```

### ❌ **MISTAKE 6: Using < Instead of <= in Loop**
```java
// WRONG - uses left < right
while (left < right) {
    // ...
}
```

**Why wrong**: Doesn't check last element when left == right!

**Dry run failure for timestamps=[5], target=5:**
```
left=0, right=0
Condition: 0 < 0? No
Loop doesn't execute
Return "" ❌

Should return value at timestamp 5!
```

**Fix**: Use <=
```java
while (left <= right) {
    // ...
}
```

### ❌ **MISTAKE 7: Not Initializing result**
```java
// WRONG - result not initialized
String result;  // null!

while (left <= right) {
    // ...
    result = list.get(mid).value;
}

return result;  // Might be null!
```

**Why wrong**: If no match found, returns null instead of ""!

**Fix**: Initialize to ""
```java
String result = "";
```

### ❌ **MISTAKE 8: Using Pair Without Proper Access**
```java
// WRONG - tries to use non-existent Pair class
List<Pair<Integer, String>> list = new ArrayList<>();
// Pair is not a built-in class!
```

**Why wrong**: Java doesn't have built-in Pair class!

**Fix**: Create custom Pair class or use other approach
```java
private static class Pair {
    int timestamp;
    String value;
    
    Pair(int timestamp, String value) {
        this.timestamp = timestamp;
        this.value = value;
    }
}
```

Or use two parallel lists (less clean).

---

## Complexity Analysis

### Time Complexity

**set() Operation: O(1)**

| Operation | Time | Reason |
|-----------|------|--------|
| HashMap lookup | O(1) | Average case |
| Create list if needed | O(1) | First time only |
| Append to list | O(1) | ArrayList append |
| **Total** | **O(1)** | Constant time |

**get() Operation: O(log n)**

| Operation | Time | Reason |
|-----------|------|--------|
| HashMap lookup | O(1) | Average case |
| Binary search | O(log n) | Halve space each iteration |
| **Total** | **O(log n)** | n = values for key |

**Time analysis for get():**
```
Binary search on sorted list:
  Search space: n
  After k iterations: n / 2^k
  
  Converges when: n / 2^k = 1
  Solving: k = log₂(n)
  
  Maximum iterations: ⌈log₂(n)⌉

Examples:
  n = 10 values: log₂(10) ≈ 3-4 iterations
  n = 100 values: log₂(100) ≈ 6-7 iterations
  n = 1,000 values: log₂(1,000) ≈ 10 iterations

Very efficient even with many values!
```

### Space Complexity: **O(m * n)**

| Component | Space | Reason |
|-----------|-------|--------|
| HashMap | O(m) | m = number of keys |
| Lists | O(m * n) | n = average values per key |
| **Total** | **O(m * n)** | All stored values |

**Space analysis**:
```
Must store all key-value-timestamp tuples:
  - HashMap overhead: O(m) for m keys
  - Each key has list of values: O(n) per key
  - Total: O(m * n)
  
Examples:
  10 keys, 100 values each: 10 * 100 = 1,000 pairs
  1,000 keys, 10 values each: 1,000 * 10 = 10,000 pairs
  
Cannot do better - must store all data!
Space complexity is optimal.
```

---

## Visualization

### Complete Example Walkthrough

**Input:** 
```
set("alice", "happy", 1)
set("alice", "sad", 3)
set("alice", "ok", 5)
get("alice", 4)
```

---

**After set("alice", "happy", 1):**
```
HashMap:
{
  "alice": [(1, "happy")]
}

Timeline for "alice":
  timestamp: [1]
  value:     ["happy"]
```

---

**After set("alice", "sad", 3):**
```
HashMap:
{
  "alice": [(1, "happy"), (3, "sad")]
}

Timeline for "alice":
  timestamp: [1,    3]
  value:     ["happy", "sad"]
```

---

**After set("alice", "ok", 5):**
```
HashMap:
{
  "alice": [(1, "happy"), (3, "sad"), (5, "ok")]
}

Timeline for "alice":
  timestamp: [1,    3,    5]
  value:     ["happy", "sad", "ok"]
```

---

**get("alice", 4):**
```
List for "alice": [(1, "happy"), (3, "sad"), (5, "ok")]
Timestamps: [1, 3, 5]

Binary search for largest timestamp <= 4:

Iteration 1:
  left=0, right=2, mid=1
  timestamps[1] = 3 <= 4? Yes ✓
  
  Found candidate!
  result = "sad" (value at timestamp 3)
  
  Search right for larger: left = 2

Iteration 2:
  left=2, right=2, mid=2
  timestamps[2] = 5 <= 4? No ✗
  
  Too large!
  Search left: right = 1

Loop ends: left=2 > right=1

Return result = "sad" ✓
```

---

**Visual Timeline:**
```
Timestamps:  0---1---2---3---4---5---6-->
Values:          ↑       ↑       ↑
               happy   sad     ok

get(alice, 4) asks for value at time 4:
  No exact match at 4
  Largest timestamp <= 4 is 3
  Return "sad" ✓
```

---

### Another Example: Multiple Keys

**Input:**
```
set("alice", "happy", 1)
set("bob", "cool", 2)
set("alice", "sad", 3)
get("alice", 2)
get("bob", 2)
```

**HashMap after all sets:**
```
{
  "alice": [(1, "happy"), (3, "sad")],
  "bob":   [(2, "cool")]
}
```

**get("alice", 2):**
```
Timeline: [1, 3]
Search for <= 2
Find: 1
Return: "happy"
```

**get("bob", 2):**
```
Timeline: [2]
Search for <= 2
Find: 2 (exact match)
Return: "cool"
```

Each key has independent timeline!

---

### Binary Search Decision Tree

**Timestamps: [1, 3, 5, 8, 10], target = 6**

```
                 mid=2 (ts=5)
                 5 <= 6? Yes
                 Save "value5"
                /            \
         search right      (not taken)
           [3,4]
           
         mid=3 (ts=8)
         8 <= 6? No
        /            \
   search left    (not taken)
      [3,2]
      
    left > right
    DONE!
    
Return saved: "value5" ✓
```

---

## Comparison of Approaches

| Approach | set() | get() | Space | Code Lines | Recommended |
|----------|-------|-------|-------|------------|-------------|
| **HashMap + Binary Search** | **O(1)** | **O(log n)** | **O(m*n)** | **~40** | **Yes ✅** |
| TreeMap | O(log n) | O(log n) | O(m*n) | ~20 | Slower set |
| Linear Search | O(1) | O(n) | O(m*n) | ~30 | Too slow ❌ |

**Winner**: **HashMap + Binary Search** — optimal time!

---

## Key Takeaways

1. **HashMap for keys** — O(1) key lookup
2. **List for each key's values** — store (timestamp, value) pairs
3. **Timestamps strictly increasing** — can append without sorting
4. **Binary search for get()** — find largest timestamp <= target
5. **Track result variable** — save best match while searching
6. **Search right when found** — look for larger valid timestamp
7. **Initialize list for new keys** — avoid NullPointerException
8. **Return "" when not found** — for missing key or no valid timestamp
9. **set() is O(1)** — just append to list
10. **get() is O(log n)** — binary search on sorted timestamps

---

## Interview Tips

**What to say in an interview:**

> "This is a time-series data problem where we need to store multiple values for each key at different timestamps and retrieve values based on timestamps. I'll use a HashMap where each key maps to a list of (timestamp, value) pairs. Since timestamps are strictly increasing, I can simply append new values to each list, making set() O(1). For get(), I need to find the value with the largest timestamp less than or equal to the query timestamp. Since the timestamps are sorted, I'll use binary search. I'll maintain a result variable to track the best match found so far, and when I find a timestamp that's less than or equal to my target, I'll save it and continue searching to the right for potentially larger matches. This gives O(log n) time for get(), where n is the number of values for that key. The space complexity is O(m*n) where m is the number of keys and n is the average number of values per key, which is optimal since we must store all the data."

**Key points to mention:**
1. **HashMap for O(1) key lookup** — essential for efficiency
2. **List of pairs** — store (timestamp, value) together
3. **Timestamps strictly increasing** — allows O(1) append
4. **Binary search** — find largest timestamp <= target
5. **Track result variable** — save best match while searching
6. **Search right when found** — look for larger valid match
7. **Handle missing key** — return "" when key doesn't exist
8. **set() O(1), get() O(log n)** — optimal time complexity

**Common Follow-ups:**
- "What if timestamps aren't strictly increasing?" → Would need to sort or use TreeMap
- "Can you use TreeMap instead?" → Yes, but set() becomes O(log n)
- "How do you handle duplicate timestamps?" → Problem states strictly increasing, but could keep latest
- "What if we need to delete values?" → Would need additional logic, maybe mark as deleted
- "Space optimization possible?" → No, must store all data

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Time Based Key-Value Store** | Medium | **HashMap + Binary Search** | **This problem** |
| LRU Cache | Medium | HashMap + Doubly Linked List | Time-based eviction, not timestamp queries |
| LFU Cache | Hard | HashMap + Min Heap | Frequency-based, not time-based |
| Design Search Autocomplete System | Hard | Trie + Heap | Different data structure |
| Binary Search | Easy | Binary Search | Basic search algorithm |
| Search Insert Position | Easy | Binary Search | Similar search logic |
| Design HashMap | Easy | Hash Table | Basic hash map design |
| Logger Rate Limiter | Easy | HashMap + Timestamps | Simpler timestamp logic |

**Pattern Progression**:
1. **Basic HashMap** — Key-value storage
2. **Time-Series Data** (this problem) — Multiple timestamped values
3. **Binary Search on Time** — Efficient temporal queries
4. **Design Problems** — Implement complex data structures

---

## Final Pattern Label

✅ **HashMap + Binary Search (Time-Series Key-Value Store)**

**Remember:** This is **HashMap + Binary Search for time-series data**. Use **HashMap** to map keys to **lists of (timestamp, value) pairs**. Since **timestamps strictly increasing**, just **append** to list for O(1) `set()`. For `get()`, perform **binary search** to find **largest timestamp <= target**. **Track result variable** to save best match, and **search right** when found valid timestamp to look for larger matches. **Handle missing keys** by returning "". **Check and initialize list** for new keys in `set()`. The **set() is O(1)** (append), **get() is O(log n)** (binary search), and **space is O(m*n)** (store all data). Key insight: **strictly increasing timestamps** make problem efficient — no sorting needed, just append and binary search! Perfect combination of hash table for fast key lookup and binary search for efficient temporal queries!
