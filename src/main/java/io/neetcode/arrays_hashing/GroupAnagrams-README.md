# Group Anagrams

## Problem Description

**Difficulty**: Medium

Given an array of strings `strs`, group the anagrams together. You can return the answer in any order.

An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

## Examples
### Example 1:
```
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
Explanation: 
- "eat", "tea", "ate" are anagrams (same characters, different order)
- "tan", "nat" are anagrams
- "bat" is alone
```

### Example 2:
```
Input: strs = [""]
Output: [[""]]
```

### Example 3:
```
Input: strs = ["a"]
Output: [["a"]]
```

## Constraints
- 1 <= strs.length <= 10^4
- 0 <= strs[i].length <= 100
- strs[i] consists of lowercase English letters

---

## Pattern Recognition

**Primary Pattern**: **HashMap Grouping with Canonical Key (Frequency Signature)**

**Why This Pattern?**
- Need to group items by derived property (character frequency)
- Fast lookup required → O(1) for HashMap
- One bucket per anagram group
- Key insight: Anagrams share same character frequency

**Key Insight**: Anagrams have identical character frequencies regardless of order. We can use this frequency signature as a unique key to group them.

**Related Patterns**:
1. **Group by Property** - General HashMap grouping pattern
2. **Frequency Map** - Character counting
3. **Canonical Form** - Converting to standard representation

---

## Algorithm & Approach

### Core Insight

**Critical Observation:** Anagrams share the same character frequency

| Word | Frequency Signature |
|------|---------------------|
| act | a:1, c:1, t:1 |
| cat | a:1, c:1, t:1 |
| pots | o:1, p:1, s:1, t:1 |
| tops | o:1, p:1, s:1, t:1 |

**Why it works:**
- Order does NOT matter → "act" and "cat" are anagrams
- Frequency DOES matter → same frequency = same anagram group
- Frequency signature uniquely identifies anagram groups

### What is a Canonical Key?

A **canonical key** is a standardized representation that's the same for all members of a group.

**For anagrams, we have two options:**

1. **Frequency Array** → `[1,0,1,0,...,1]` for "act"
2. **Sorted String** → `"act"` for both "act" and "cat"

### Step-by-Step Algorithm
#### **Approach 1: Sorted String as Key**
```
1. Create HashMap<String, List<String>>
2. For each string:
   a. Sort the string characters
   b. Use sorted string as key
   c. Add original string to the list at that key
3. Return all values (groups) from the map
```

**Code Implementation**
```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    
    for (String s : strs) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);
        
        map.putIfAbsent(key, new ArrayList<>());
        map.get(key).add(s);
    }
    
    return new ArrayList<>(map.values());
}
```

**Complexity Analysis**
- **Time Complexity**: O(n * k log k) where n = number of strings, k = max string length
  - Sorting each string: O(k log k)
  - Done for n strings: O(n * k log k)
- **Space Complexity**: O(n * k) for the HashMap

**Why Not Optimal?** Sorting is O(k log k) - we can do better!

#### **Approach 2: Frequency Array as Key (OPTIMAL)**
```
1. Create HashMap<String, List<String>>
2. For each string:
   a. Count frequency of each character (26 letters)
   b. Convert frequency array to String key
   c. Use this key to group anagrams
3. Return all values from the map
```

**Example:**
```
"act" → [1,0,1,0,0,...,0,0,1,0,...,0] → "[1,0,1,0,...,1,...]" (key)
"cat" → [1,0,1,0,0,...,0,0,1,0,...,0] → "[1,0,1,0,...,1,...]" (same key!)
```

**Code Implementation**
```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> res = new HashMap<>();
    
    for (String s : strs) {
        // Create frequency array for 26 lowercase letters
        int[] count = new int[26];
        
        // Count each character
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        
        // Convert frequency array to String key
        String key = Arrays.toString(count);
        
        // Group strings by key
        res.putIfAbsent(key, new ArrayList<>());
        res.get(key).add(s);
    }
    
    // Return all groups
    return new ArrayList<>(res.values());
}
```

### Understanding the Code Step-by-Step

**Step 1: Create frequency array**
```java
int[] count = new int[26];
```
- 26 positions for 'a' to 'z'
- All initialized to 0

**Step 2: Count characters**
```java
for (char c : s.toCharArray()) {
    count[c - 'a']++;
}
```
- `c - 'a'` converts 'a'→0, 'b'→1, ..., 'z'→25
- Example: "act" → count = [1,0,1,0,...,1,...]

**Step 3: Convert to String key**
```java
String key = Arrays.toString(count);
```
- **Why?** Arrays can't be HashMap keys (reference comparison)
- Strings can be keys (value comparison)
- Example: `"[1, 0, 1, 0, ..., 1, ...]"`

**Step 4: Group by key**
```java
res.putIfAbsent(key, new ArrayList<>());
res.get(key).add(s);
```
- `putIfAbsent`: Create new list if key doesn't exist
- `get(key).add(s)`: Add string to its anagram group

### Example Walkthrough

**Input:** strs = ["act", "pots", "tops", "cat", "stop", "hat"]
| String | Frequency Array | Key (simplified) | Group |
|--------|----------------|------------------|-------|
| "act" | a:1,c:1,t:1 | "[1,0,1,0...1...]" | 1 |
| "pots" | o:1,p:1,s:1,t:1 | "[0,0,0,0...1...]" | 2 |
| "tops" | o:1,p:1,s:1,t:1 | "[0,0,0,0...1...]" | 2 (same!) |
| "cat" | a:1,c:1,t:1 | "[1,0,1,0...1...]" | 1 (same!) |
| "stop" | o:1,p:1,s:1,t:1 | "[0,0,0,0...1...]" | 2 (same!) |
| "hat" | a:1,h:1,t:1 | "[1,0,0,0...1...]" | 3 |

**HashMap State:**
```
"[1,0,1,0...1...]" → ["act", "cat"]
"[0,0,0,0...1...]" → ["pots", "tops", "stop"]
"[1,0,0,0...1...]" → ["hat"]
```

**Output:** `[["hat"], ["act", "cat"], ["stop", "pots", "tops"]]`

**Note:** Order of groups doesn't matter!

### Complexity Analysis
- **Time Complexity**: O(n * k) - OPTIMAL!
  - n strings, each of length k
  - Counting characters: O(k) per string
  - Total: O(n * k)
- **Space Complexity**: O(n * k)
  - HashMap stores all strings
  - Frequency keys: O(n) keys of O(k) size

---

## Why This Strategy?

### Problem Requirements Analysis
| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Brute Force | O(n² * k) | O(n * k) | Simple comparison | Too slow |
| **Frequency Array** | **O(n * k)** ✅ | **O(n * k)** | **Fastest** | Slightly more code |
| Sorted String | O(n * k log k) | O(n * k) | Easy to understand | Slower due to sorting |

**Winner**: Frequency Array - optimal time complexity!

### Why Frequency Array Over Sorted String?

**Frequency Array:**
- ✅ O(k) - linear in string length
- ✅ Single pass through string
- ✅ No sorting overhead

**Sorted String:**
- ❌ O(k log k) - sorting overhead
- ❌ More operations per string
- ✅ Simpler to code

**For interviews:** Use frequency array and mention sorted string as alternative!

### Why Arrays.toString() for Key?

**Problem:** Arrays use reference equality
```java
int[] a = {1, 2, 3};
int[] b = {1, 2, 3};
a.equals(b); // false! Different objects
```

**Solution:** Convert to String for value equality
```java
String keyA = Arrays.toString(a); // "[1, 2, 3]"
String keyB = Arrays.toString(b); // "[1, 2, 3]"
keyA.equals(keyB); // true! Same content
```

---

## Critical Edge Cases & Gotchas

### 1. **Empty String**
```java
Input: strs = [""]
Output: [[""]]
Explanation: Empty string is valid, groups with itself
```

### 2. **Single String**
```java
Input: strs = ["a"]
Output: [["a"]]
```

### 3. **All Same Anagrams**
```java
Input: strs = ["abc", "bca", "cab"]
Output: [["abc", "bca", "cab"]]
Explanation: All in one group
```

### 4. **No Anagrams**
```java
Input: strs = ["a", "b", "c"]
Output: [["a"], ["b"], ["c"]]
Explanation: Each string in its own group
```

### 5. **Single Character Repeated**
```java
Input: strs = ["aaa", "aa", "a"]
Output: [["a"], ["aa"], ["aaa"]]
Explanation: Different frequencies, different groups
```

### 6. **Mixed Case (Not in constraints)**
```java
// Problem guarantees lowercase only
// But if it didn't, "Act" and "cat" would NOT be anagrams
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Using Array as HashMap Key Directly**
```java
// WRONG - Arrays use reference equality!
Map<int[], List<String>> map = new HashMap<>();
int[] count = new int[26];
// ...count characters...
map.put(count, list); // Different array objects won't match!
```

**Why wrong**: Each `new int[26]` creates a different object reference.

**Fix**: Convert to String with `Arrays.toString(count)`

### ❌ **MISTAKE 2: Forgetting putIfAbsent**
```java
// WRONG - NullPointerException!
res.get(key).add(s); // What if key doesn't exist yet?
```

**Why wrong**: First time we see a key, there's no list yet.

**Fix**: Use `putIfAbsent` or check before adding
```java
// CORRECT
res.putIfAbsent(key, new ArrayList<>());
res.get(key).add(s);

// OR
if (!res.containsKey(key)) {
    res.put(key, new ArrayList<>());
}
res.get(key).add(s);
```

### ❌ **MISTAKE 3: Wrong Character Offset**
```java
// WRONG - Assumes ASCII starting from 'A'
count[c - 'A']++; // Wrong for lowercase!
```

**Why wrong**: 'a' is ASCII 97, not 65. We need offset from 'a' (97), not 'A' (65).

**Fix**: Use `c - 'a'` for lowercase letters

### ❌ **MISTAKE 4: Returning Map Instead of Values**
```java
// WRONG - Wrong return type!
return res; // Returns Map, not List<List<String>>
```

**Why wrong**: We need the grouped lists, not the keys.

**Fix**: Return `new ArrayList<>(res.values())`

### ❌ **MISTAKE 5: Reusing Frequency Array**
```java
// WRONG - Don't reuse!
int[] count = new int[26]; // Outside loop

for (String s : strs) {
    // ...count characters...
    // Array still has previous counts!
}
```

**Why wrong**: Frequency array accumulates counts from previous strings.

**Fix**: Create new array for each string
```java
// CORRECT
for (String s : strs) {
    int[] count = new int[26]; // Fresh array each time
}
```

---

## Complexity Analysis

### Time Complexity: **O(n * k)**

| Operation | Time | Explanation |
|-----------|------|-------------|
| Iterate through n strings | O(n) | Outer loop |
| Count characters in string | O(k) | Inner loop for each string |
| Arrays.toString() | O(26) = O(1) | Fixed size array |
| HashMap operations | O(1) average | putIfAbsent, get, add |
| Total | O(n * k) | Optimal for this problem |

**Where:** n = number of strings, k = maximum string length

### Space Complexity: **O(n * k)**

| Component | Space |
|-----------|-------|
| HashMap keys | O(n) keys (worst case: all unique) |
| HashMap values | O(n * k) total characters stored |
| Frequency arrays | O(26 * n) = O(n) temporary |
| Total | O(n * k) |

**Note:** Output itself requires O(n * k) space, so this is optimal.

---

## Visualization

### Example Walkthrough
```
Input: ["eat", "tea", "tan", "ate", "nat", "bat"]

Processing:

"eat" → count: [1,0,0,0,1,0,...,1,...] → key: "[1,0,0,0,1,0...1...]"
        map["[1,0,0,0,1,0...1...]"] = ["eat"]

"tea" → count: [1,0,0,0,1,0,...,1,...] → key: "[1,0,0,0,1,0...1...]" (SAME!)
        map["[1,0,0,0,1,0...1...]"] = ["eat", "tea"]

"tan" → count: [1,0,0,0,0,0,...,1,0,...,1] → key: "[1,0,0,0,0,0...1...]"
        map["[1,0,0,0,0,0...1...]"] = ["tan"]

"ate" → count: [1,0,0,0,1,0,...,1,...] → key: "[1,0,0,0,1,0...1...]" (SAME!)
        map["[1,0,0,0,1,0...1...]"] = ["eat", "tea", "ate"]

"nat" → count: [1,0,0,0,0,0,...,1,0,...,1] → key: "[1,0,0,0,0,0...1...]" (SAME!)
        map["[1,0,0,0,0,0...1...]"] = ["tan", "nat"]

"bat" → count: [1,1,0,0,0,0,...,1,...] → key: "[1,1,0,0,0,0...1...]"
        map["[1,1,0,0,0,0...1...]"] = ["bat"]

Final HashMap:
{
  "[1,0,0,0,1,0...1...]": ["eat", "tea", "ate"],
  "[1,0,0,0,0,0...1...]": ["tan", "nat"],
  "[1,1,0,0,0,0...1...]": ["bat"]
}

Output: [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]
```

---

## Comparison of Approaches

| Approach | Time | Space | Pros | Cons |
|----------|------|-------|------|------|
| Brute Force | O(n² * k) | O(n * k) | No extra concepts | Too slow |
| **Frequency Array** | **O(n * k)** ✅ | **O(n * k)** | **Optimal time** | Slightly complex |
| Sorted String | O(n * k log k) | O(n * k) | Simpler code | Slower |
| Prime Product | O(n * k) | O(n * k) | Clever | Risk of overflow |

**Best Choice**: Frequency Array ✓

---

## Key Takeaways

1. **Pattern Recognition**: "Group by property" → HashMap with canonical key
2. **Anagram Definition**: Same characters, different order → same frequency
3. **Canonical Key**: Standardized representation for all group members
4. **Frequency vs Sorted**: Frequency is O(k), sorting is O(k log k)
5. **Arrays as Keys**: Must convert to String or use custom equals/hashCode
6. **putIfAbsent**: Essential for safe HashMap grouping

---

## Interview Tips

**What to say in an interview:**

> "This is a grouping problem where I need to identify anagrams. Anagrams have the same character frequencies, so I'll use a HashMap where the key is a frequency signature and the value is a list of anagrams. For each string, I count character frequencies using an array of size 26, convert it to a String key, and group the string under that key. This gives O(n*k) time which is optimal since I must look at every character."

**Key points to mention:**
1. **Pattern**: HashMap grouping with canonical key
2. **Why frequency**: Anagrams have identical character frequencies
3. **Why String key**: Arrays can't be HashMap keys (reference equality)
4. **Complexity**: O(n*k) time - optimal, must examine all characters
5. **Alternative**: Sorted string as key (O(n*k log k) but simpler)

**If asked about optimization:**
> "The frequency array approach is already optimal at O(n*k). An alternative is using sorted strings as keys, which is O(n*k log k) - slightly slower but easier to code. Some people use prime number products, but that risks integer overflow with long strings."

**If asked why not use array as key directly:**
> "Arrays in Java use reference equality, not value equality. Two arrays with the same content are considered different objects. Converting to a String ensures we get value-based equality for the HashMap key."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Group Anagrams** | Medium | **HashMap + Frequency** | **Group by character frequency** ← This problem |
| Valid Anagram | Easy | Frequency Array | Check if two strings are anagrams |
| Find All Anagrams | Medium | Sliding Window + Frequency | Find anagram substrings |
| Isomorphic Strings | Easy | Character Mapping | Pattern matching, not anagrams |
| Group Shifted Strings | Medium | HashMap + Pattern | Group by shift pattern |

**Pattern Family**: HashMap Grouping / Frequency Counting

---

## Final Pattern Label

✅ **HashMap Grouping with Canonical Key (Frequency Signature Pattern)**

**Remember:** When you see "group by some property" + "same characters different order" → think HashMap with frequency array as canonical key! This is the **Anagram Grouping Pattern**.