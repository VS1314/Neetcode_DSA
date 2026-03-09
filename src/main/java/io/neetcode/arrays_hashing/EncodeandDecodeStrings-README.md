# Encode and Decode Strings
## Problem Description
**Difficulty**: Medium

Design an algorithm to encode a list of strings to a single string. The encoded string is then decoded back to the original list of strings.
Please implement `encode` and `decode`.
**Note:** The string may contain any possible characters out of 256 valid ASCII characters. Your algorithm should be generalized enough to work on any possible characters.
## Examples
### Example 1:
```
Input: ["Hello","World"]
Output: ["Hello","World"]
Explanation:
encode: ["Hello","World"] -> "5#Hello5#World"
decode: "5#Hello5#World" -> ["Hello","World"]
```
### Example 2:
```
Input: [""]
Output: [""]
Explanation:
encode: [""] -> "0#"
decode: "0#" -> [""]
```
### Example 3:
```
Input: ["#12@!$%","abc#123"]
Output: ["#12@!$%","abc#123"]
Explanation:
encode: ["#12@!$%","abc#123"] -> "7##12@!$%7#abc#123"
decode: "7##12@!$%7#abc#123" -> ["#12@!$%","abc#123"]
```
## Constraints
- 0 <= strs.length <= 200
- 0 <= strs[i].length <= 200
- strs[i] contains any possible characters out of 256 valid ASCII characters
---
## Pattern Recognition
**Primary Pattern**: **Length-Prefix Encoding / Self-Describing Serialization**
**Why This Pattern?**
- Strings can contain ANY ASCII character (0-255)
- No delimiter is safe - string content could contain it
- Need unambiguous encoding/decoding
- Common in network protocols and distributed systems
**Key Insight**: Since strings may contain any character, we cannot use delimiter-based splitting. Instead, we use length-prefix encoding: `length#string` where length tells us exactly how many characters to read.
**Related Patterns**:
1. **Serialization** - Converting data structures to transmittable format
2. **Protocol Design** - Network communication encoding
3. **String Parsing** - Custom format parsing
---
## Algorithm & Approach
### Core Insight
The fundamental problem: **No safe delimiter exists** because strings can contain ANY ASCII character.
**Why Delimiter-Based Solutions Fail:**
```
Input: ["Hi|Hello", "World"]
Using delimiter "|": "Hi|Hello|World"
Decode with split("|"): ["Hi", "Hello", "World"] ❌ WRONG!
```
**The Solution: Length-Prefix Encoding**
- Format: `length#string`
- Example: `"5#Hello5#World"`
- Read length → skip # → read exactly length characters
- Guarantees no ambiguity
### Why This Works for ANY Characters
**Critical Understanding:**
- The `#` is NOT a delimiter for the string content
- The `#` only separates the length from the string
- We read exactly `length` characters, so any character (including `#`) in the string is safe
**Example:**
```
Input: ["ab#cd"]
Encoded: "5#ab#cd"
Decoding: Read "5" → skip "#" → read next 5 chars → "ab#cd" ✓
```
### Step-by-Step Algorithm
#### **Encode Algorithm**
```
1. Create StringBuilder for result
2. For each string in list:
   a. Append string.length()
   b. Append '#' separator
   c. Append the string itself
3. Return encoded string
```
#### **Decode Algorithm**
```
1. Create result list
2. Initialize pointer i = 0
3. While i < encoded.length():
   a. Find next '#' (read the length number)
   b. Parse length from substring
   c. Skip the '#'
   d. Read exactly 'length' characters
   e. Add to result list
   f. Move pointer forward
4. Return result list
```
### Code Implementation
```java
public class Codec {
    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s.length()).append('#').append(s);
        }
        return sb.toString();
    }
    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            // Find the '#' separator
            int j = i;
            while (s.charAt(j) != '#') {
                j++;
            }
            // Parse the length
            int length = Integer.parseInt(s.substring(i, j));
            // Skip the '#'
            j++;
            // Extract string of specified length
            result.add(s.substring(j, j + length));
            // Move to next encoded string
            i = j + length;
        }
        return result;
    }
}
```
### Example Walkthrough
**Input:** `["Hello", "World"]`
**Encoding Process:**
| String | Length | Encoded Part |
|--------|--------|--------------|
| "Hello" | 5 | `5#Hello` |
| "World" | 5 | `5#World` |
**Final Encoded String:** `"5#Hello5#World"`
**Decoding Process:**
```
Encoded: "5#Hello5#World"
         ^
i = 0
Step 1:
- j scans until '#' at index 1
- length = parseInt("5") = 5
- j++ → j = 2
- Extract s.substring(2, 7) = "Hello"
- i = 7
Step 2:
- j scans from 7 until '#' at index 8
- length = parseInt("5") = 5
- j++ → j = 9
- Extract s.substring(9, 14) = "World"
- i = 14
Result: ["Hello", "World"]
```
### Complexity Analysis
- **Time Complexity**: O(m) where m = total characters in all strings
  - Encode: O(m) - iterate through all characters
  - Decode: O(m) - scan through encoded string once
- **Space Complexity**: O(m) for the encoded string (excluding output)
---
## Why This Strategy?
### Problem Requirements Analysis
| Approach | Can Handle Any Char? | Encoding Ambiguity | Time | Space |
|----------|---------------------|-----------------------|------|-------|
| Delimiter (e.g., `,`) | ❌ Fails if string contains delimiter | High | O(m) | O(m) |
| Escape Characters | ⚠️ Complex, error-prone | Medium | O(m) | O(m) |
| **Length-Prefix** | **✅ Handles all ASCII** | **None** ✅ | **O(m)** | **O(m)** |
| JSON | ✅ Yes | None | O(m) | O(m) |
**Winner**: Length-Prefix Encoding - robust, simple, no ambiguity!
### Why Not JSON?
JSON works but has drawbacks:
- ❌ More overhead (quotes, brackets, escaping)
- ❌ Slower to parse
- ❌ More space
- ✅ Length-prefix is minimal and efficient
### Why Separator Character Doesn't Matter?
**Important Insight:** The separator (`#`) can be ANY character!
Could use:
- `5|Hello`
- `5:Hello`
- `5@Hello`
**What matters:** 
- The **length** tells us exactly how many characters to read
- The separator just marks where the number ends
- The actual string content can contain the separator character safely
---
## Critical Edge Cases & Gotchas
### 1. **Empty String**
```java
Input: [""]
Encoded: "0#"
Decoded: [""] ✓
```
### 2. **String Containing Separator**
```java
Input: ["ab#cd"]
Encoded: "5#ab#cd"
Decoded: "ab#cd" ✓
Explanation: Read 5 chars, so the # inside is part of string
```
### 3. **Multiple Separators in String**
```java
Input: ["###"]
Encoded: "3####"
Decoded: "###" ✓
```
### 4. **Empty List**
```java
Input: []
Encoded: ""
Decoded: [] ✓
```
### 5. **Single Character String**
```java
Input: ["a"]
Encoded: "1#a"
Decoded: ["a"] ✓
```
### 6. **All Special Characters**
```java
Input: ["!@#$%^&*()"]
Encoded: "10#!@#$%^&*()"
Decoded: ["!@#$%^&*()"] ✓
```
### 7. **Strings with Numbers**
```java
Input: ["123", "456"]
Encoded: "3#1233#456"
Decoded: ["123", "456"] ✓
```
---
## Major Areas Where We Might Go Wrong
### ❌ **MISTAKE 1: Using Simple Delimiter**
```java
// WRONG - Fails when string contains delimiter!
public String encode(List<String> strs) {
    return String.join("|", strs);
}
public List<String> decode(String s) {
    return Arrays.asList(s.split("\\|"));
}
```
**Why wrong**: Strings can contain `|` character.
**Example failure:**
```
Input: ["a|b", "c"]
Encoded: "a|b|c"
Decoded: ["a", "b", "c"] ❌ Should be ["a|b", "c"]
```
**Fix**: Use length-prefix encoding
### ❌ **MISTAKE 2: Not Handling Empty Strings**
```java
// WRONG - Doesn't handle empty strings!
public String encode(List<String> strs) {
    StringBuilder sb = new StringBuilder();
    for (String s : strs) {
        if (s.length() > 0) {  // Skips empty strings!
            sb.append(s.length()).append('#').append(s);
        }
    }
    return sb.toString();
}
```
**Why wrong**: Empty strings are valid input.
**Fix**: Don't skip empty strings - `0#` is valid encoding
### ❌ **MISTAKE 3: Forgetting to Skip Separator in Decode**
```java
// WRONG - Doesn't skip '#'!
public List<String> decode(String s) {
    List<String> result = new ArrayList<>();
    int i = 0;
    while (i < s.length()) {
        int j = i;
        while (s.charAt(j) != '#') j++;
        int length = Integer.parseInt(s.substring(i, j));
        // Missing: j++; to skip '#'
        result.add(s.substring(j, j + length));  // Includes '#' in string!
        i = j + length;
    }
    return result;
}
```
**Why wrong**: Includes `#` separator in the decoded string.
**Fix**: Add `j++;` after parsing length
### ❌ **MISTAKE 4: Wrong Pointer Arithmetic**
```java
// WRONG - Wrong pointer update!
result.add(s.substring(j, j + length));
i = j;  // Should be j + length!
```
**Why wrong**: Pointer doesn't advance correctly, causes infinite loop or wrong parsing.
**Fix**: `i = j + length;`
### ❌ **MISTAKE 5: Using charAt Without Bounds Check**
```java
// WRONG - Potential IndexOutOfBounds!
while (s.charAt(j) != '#') {  // What if no '#' found?
    j++;
}
```
**Why wrong**: If encoded string is malformed, `j` goes out of bounds.
**Fix**: Add bounds check or trust that encoding is correct (interview assumption)
---
## Complexity Analysis
### Time Complexity: **O(m)**
| Operation | Time | Explanation |
|-----------|------|-------------|
| Encode | O(m) | Iterate through all characters once |
| Length calculation | O(n) | n strings, constant time each |
| Append operations | O(m) | StringBuilder amortized O(1) per char |
| Decode | O(m) | Scan encoded string once |
| Integer.parseInt | O(log k) | k = max string length, negligible |
**Where:** m = total characters in all strings, n = number of strings
### Space Complexity: **O(m)**
| Component | Space |
|-----------|-------|
| Encoded string | O(m) |
| StringBuilder | O(m) |
| Result list | O(n) pointers + O(m) characters |
| Total | O(m + n) ≈ O(m) |
---
## Visualization
### Example Walkthrough
```
Input: ["ab#c", "12|34"]
Encoding:
- "ab#c": length = 4 → "4#ab#c"
- "12|34": length = 5 → "5#12|34"
- Concatenate: "4#ab#c5#12|34"
Decoding "4#ab#c5#12|34":
i=0: "4#ab#c5#12|34"
     ^
     j scans for '#'
i=0, j=0: "4#ab#c5#12|34"
          ^
i=0, j=1: "4#ab#c5#12|34"
           ^
          Found '#'!
     length = parseInt("4") = 4
     j++ → j = 2
     Extract: s.substring(2, 6) = "ab#c"
     i = 6
i=6: "4#ab#c5#12|34"
            ^
            j scans for '#'
i=6, j=7: "4#ab#c5#12|34"
                ^
               Found '#'!
     length = parseInt("5") = 5
     j++ → j = 8
     Extract: s.substring(8, 13) = "12|34"
     i = 13
Result: ["ab#c", "12|34"] ✓
```
---
## Comparison of Approaches
| Approach | Pros | Cons | Use Case |
|----------|------|------|----------|
| Simple Delimiter | Easy to understand | ❌ Fails with special chars | Simple data only |
| Escape Characters | Works for any char | Complex, error-prone | When needed |
| **Length-Prefix** | **Robust, simple** ✅ | **Slightly more code** | **This problem** ✅ |
| JSON | Standard, robust | Overhead, slower | Complex objects |
**Best Choice**: Length-Prefix Encoding ✓
---
## Key Takeaways
1. **No Safe Delimiter**: When strings can contain any character, delimiter-based encoding fails
2. **Length-Prefix Pattern**: `length#string` format is unambiguous
3. **Separator is Just Marker**: The `#` only separates length from string, not string content
4. **Read Exact Count**: Length tells us exactly how many characters to read
5. **Common in Systems**: Used in network protocols, message queues, file formats
6. **Interview Pattern**: Classic serialization/deserialization problem
---
## Interview Tips
**What to say in an interview:**
> "Since strings can contain any ASCII character, we cannot use a simple delimiter like comma or pipe because the string itself might contain that character. Instead, I'll use length-prefix encoding where each string is encoded as `length#string`. When decoding, I read the length, skip the separator, then read exactly that many characters. This guarantees no ambiguity regardless of string content. The time complexity is O(m) where m is total characters, and space is O(m) for the encoded string."
**Key points to mention:**
1. **Why delimiter fails**: Strings can contain any character
2. **Pattern**: Length-prefix encoding (self-describing format)
3. **How it works**: Length tells us exactly how many chars to read
4. **Separator role**: Only marks where number ends, not a string delimiter
5. **Complexity**: O(m) time and space
**If asked about alternatives:**
> "We could use escape characters (like backslash escaping), but that's more complex and error-prone. JSON works but has more overhead with quotes and brackets. Length-prefix is the simplest robust solution. In real systems, protocols like Protocol Buffers use similar length-delimited formats."
**If asked about the separator:**
> "The separator can be any character - I used `#` but could use `|`, `:`, or anything. What matters is the length prefix, not the separator. The separator just marks where the length number ends so we know where the string begins."
---
## Related Problems
| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Encode and Decode Strings** | Medium | **Length-Prefix** | **Serialization of strings** ← This problem |
| Serialize and Deserialize Binary Tree | Hard | DFS/BFS + Encoding | Tree structure encoding |
| Design Compressed String Iterator | Easy | Run-length encoding | Compression with counts |
| String Compression | Easy | Run-length encoding | Character repetition |
| Decode String | Medium | Stack | Nested encoding pattern |
**Pattern Family**: Serialization / Encoding
---
## Final Pattern Label
✅ **Length-Prefix Encoding – Self-Describing Serialization Format**
**Remember:** When encoding data that can contain ANY character → use length-prefix format to avoid delimiter ambiguity. Common in network protocols, distributed systems, and serialization!