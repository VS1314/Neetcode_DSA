# Simplify Path

## Problem Description

**Difficulty**: Medium

You are given an **absolute path** for a Unix-style file system, which always begins with a slash `'/'`.

Your task is to transform this absolute path into its **simplified canonical path**.

The rules of a Unix-style file system are as follows:
- A **single period** `'.'` represents the **current directory**
- A **double period** `'..'` represents the **previous/parent directory**
- **Multiple consecutive slashes** such as `'//'` and `'///'` are treated as a **single slash** `'/'`
- Any sequence of periods that does not match the rules above should be treated as a **valid directory or file name**. For example, `'...'` and `'....'` are valid directory or file names.

The **simplified canonical path** should follow these rules:
- The path must **start with a single slash** `'/'`
- Directories within the path must be **separated by exactly one slash** `'/'`
- The path must **not end with a slash** `'/'`, unless it is the **root directory**
- The path must **not have any single or double periods** (`'.'` and `'..'`) used to denote current or parent directories

Return the **simplified canonical path**.

## Examples

### Example 1:
```
Input: path = "/neetcode/practice//...///../courses"
Output: "/neetcode/practice/courses"

Explanation:
  Split by '/': ["", "neetcode", "practice", "", "...", "", "", "..", "courses"]
  
  Process each part:
    "" → skip (extra slash)
    "neetcode" → push to stack
    "practice" → push to stack
    "" → skip (extra slash)
    "..." → push to stack (valid directory name, 3+ periods)
    "" → skip (extra slash)
    "" → skip (extra slash)
    ".." → pop from stack (go to parent, removes "...")
    "courses" → push to stack
  
  Stack: ["neetcode", "practice", "courses"]
  Result: "/neetcode/practice/courses"
```

### Example 2:
```
Input: path = "/..//"
Output: "/"

Explanation:
  Split by '/': ["", "..", "", ""]
  
  Process each part:
    "" → skip
    ".." → pop from stack (but stack empty, so do nothing)
    "" → skip
    "" → skip
  
  Stack: []
  Result: "/" (root directory)
```

### Example 3:
```
Input: path = "/..//_home/a/b/..///"
Output: "/_home/a"

Explanation:
  Split by '/': ["", "..", "", "_home", "a", "b", "..", "", "", ""]
  
  Process each part:
    "" → skip
    ".." → pop (stack empty, do nothing)
    "" → skip
    "_home" → push (underscore is valid)
    "a" → push
    "b" → push
    ".." → pop (removes "b")
    "" → skip
    "" → skip
    "" → skip
  
  Stack: ["_home", "a"]
  Result: "/_home/a"
```

### Example 4:
```
Input: path = "/home/"
Output: "/home"

Explanation:
  Trailing slash removed
```

### Example 5:
```
Input: path = "/home//foo/"
Output: "/home/foo"

Explanation:
  Multiple slashes collapsed to single slash
  Trailing slash removed
```

### Example 6:
```
Input: path = "/a/./b/../../c/"
Output: "/c"

Explanation:
  Split: ["", "a", ".", "b", "..", "..", "c", ""]
  
  "" → skip
  "a" → push, stack: ["a"]
  "." → skip (current dir)
  "b" → push, stack: ["a", "b"]
  ".." → pop, stack: ["a"]
  ".." → pop, stack: []
  "c" → push, stack: ["c"]
  "" → skip
  
  Result: "/c"
```

### Example 7:
```
Input: path = "/a/../../b/../c//.//"
Output: "/c"

Explanation:
  Split: ["", "a", "..", "..", "b", "..", "c", "", ".", "", ""]
  
  "" → skip
  "a" → push, stack: ["a"]
  ".." → pop, stack: []
  ".." → pop (empty, do nothing), stack: []
  "b" → push, stack: ["b"]
  ".." → pop, stack: []
  "c" → push, stack: ["c"]
  "" → skip
  "." → skip
  "" → skip
  "" → skip
  
  Result: "/c"
```

### Example 8:
```
Input: path = "/.../a/../b/c/../d/./"
Output: "/.../a/../b/c/../d/."

Wait, this is WRONG interpretation!

Correct:
  Split: ["", "...", "a", "..", "b", "c", "..", "d", ".", ""]
  
  "" → skip
  "..." → push (valid name, 3 periods), stack: ["..."]
  "a" → push, stack: ["...", "a"]
  ".." → pop, stack: ["..."]
  "b" → push, stack: ["...", "b"]
  "c" → push, stack: ["...", "b", "c"]
  ".." → pop, stack: ["...", "b"]
  "d" → push, stack: ["...", "b", "d"]
  "." → skip (current dir)
  "" → skip
  
  Result: "/.../b/d"
```

### Example 9:
```
Input: path = "/../"
Output: "/"

Explanation:
  Try to go up from root, stays at root
```

### Example 10:
```
Input: path = "/home/user/Documents/../Pictures"
Output: "/home/user/Pictures"

Explanation:
  Navigate up from Documents to user, then into Pictures
```

## Constraints
- 1 <= path.length <= 3000
- `path` consists of English letters, digits, period `'.'`, slash `'/'` or `'_'`
- `path` is a valid absolute Unix path

**Recommended Complexity**: O(n) time and O(n) space, where n is the length of the path

---

## Pattern Recognition

**Primary Pattern**: **Stack (Path Navigation / Directory Traversal)**

**Why This Pattern?**
- Unix path navigation is inherently LIFO (Last In, First Out)
- Going into a directory = push
- Going up (..) = pop
- Stack naturally models directory hierarchy

**Key Insight**: Path Navigation is Stack-Based
```
Unix path traversal:
  Start at root: /
  Go into dir: push directory name
  Go up (..): pop last directory
  Stay here (.): do nothing
  
Perfect for stack!

Example: /a/b/c/../d
  Stack: []
  /a: push "a" → ["a"]
  /b: push "b" → ["a", "b"]
  /c: push "c" → ["a", "b", "c"]
  ..: pop → ["a", "b"]
  /d: push "d" → ["a", "b", "d"]
  
Result: /a/b/d ✓
```

**The Splitting Strategy**:
```
Split path by '/' separator

"/home//user/./docs"
↓
["", "home", "", "user", ".", "docs"]

Empty strings = consecutive slashes
Just ignore them!

Categories after split:
  1. "" (empty) → ignore
  2. "." (current) → ignore
  3. ".." (parent) → pop from stack
  4. Anything else → push to stack
```

**The Stack Strategy**:
```
Stack stores valid directory names only

As we process each part:
  - Empty or "." → skip (no stack operation)
  - ".." → pop (go to parent)
  - Valid name → push (enter directory)
  
After processing all parts:
  Join stack elements with '/'
  Add leading '/'
  
Example: stack = ["home", "user", "docs"]
Result: "/" + "home/user/docs" = "/home/user/docs"

If stack empty: return "/" (root)
```

**Example Showing Stack Evolution**:
```
Input: "/a/./b/../../c/"

Split: ["", "a", ".", "b", "..", "..", "c", ""]

Process "":
  Skip
  Stack: []

Process "a":
  Push
  Stack: ["a"]

Process ".":
  Skip (current dir)
  Stack: ["a"]

Process "b":
  Push
  Stack: ["a", "b"]

Process "..":
  Pop (go to parent)
  Stack: ["a"]

Process "..":
  Pop (go to parent)
  Stack: []

Process "c":
  Push
  Stack: ["c"]

Process "":
  Skip
  Stack: ["c"]

Build result:
  "/" + join(["c"], "/") = "/c"
```

**Why Stack is Perfect**:
```
Directory hierarchy is naturally nested:
  /home
    /user
      /docs
        /file.txt

Going into directory = push onto stack
Going up (..) = pop from stack

Stack top = current directory
Stack bottom = directory closest to root

Perfect match for the problem!
```

**Critical Edge Cases**:
```
1. Multiple slashes: "///" → treat as single "/"
   Split creates empty strings → ignore them

2. Dots as directory names: "..." or "...." → valid names
   Only "." and ".." have special meaning
   3+ periods = regular directory name

3. Going up from root: "/.." → stays at root
   Stack empty, can't pop, do nothing

4. Trailing slash: "/home/" → remove it
   Result doesn't end with "/" unless root

5. Only dots: "/././." → "/"
   All ignored, stack empty, return root
```

**Related Patterns**:
1. **Stack** — Core technique
2. **String Processing** — Split and parse
3. **Simulation** — Model Unix navigation
4. **Path Canonicalization** — Standard form

---

## Algorithm & Approach

### Core Insight

**Why Naive Approach Fails:**
```
Naive: Process string character by character
  for each character:
      if '/', check what comes next
      if '.', check if followed by '.' or '/'
      build result string directly
  
Problems:
  - Complex state management
  - Hard to handle ".."
  - Error-prone string manipulation
  - O(n²) if removing from string
  
Optimal approach:
  Split by '/', process each part with stack
  → O(n) time, clean logic ✓
```

**The Optimal Strategy**:
```
Key observations:
  1. Split simplifies parsing
  2. Stack handles ".." naturally (pop)
  3. Ignore empty and "."
  4. Join stack at end with "/"
  5. Linear time, single pass
  
Operations:
  Split: O(n)
  Process each part: O(n)
  Join: O(n)
  
Total: O(n)
```

### Step-by-Step Algorithm

---

#### **Approach 1: Stack with Split - OPTIMAL**

**Core Idea**:
- Split path by '/' to get parts
- Use stack to store valid directory names
- Process each part according to rules
- Join stack with '/' and add leading '/'

**Algorithm**
```
simplifyPath(path):
    // Split by '/'
    parts = path.split("/")
    
    // Stack to store directory names
    stack = new Stack()
    
    for each part in parts:
        if part is empty or part equals ".":
            // Skip empty strings and current directory
            continue
        else if part equals "..":
            // Go to parent directory
            if stack is not empty:
                stack.pop()
        else:
            // Valid directory name, enter it
            stack.push(part)
    
    // Build result
    if stack is empty:
        return "/"
    
    result = ""
    for each dir in stack (bottom to top):
        result = result + "/" + dir
    
    return result
```

**Code Implementation**
```java
class Solution {
    public String simplifyPath(String path) {
        // Split by '/'
        String[] parts = path.split("/");
        
        // Stack to store valid directory names
        Stack<String> stack = new Stack<>();
        
        // Process each part
        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                // Skip empty strings and current directory
                continue;
            } else if (part.equals("..")) {
                // Go to parent directory
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                // Valid directory name
                stack.push(part);
            }
        }
        
        // Build result
        if (stack.isEmpty()) {
            return "/";
        }
        
        StringBuilder result = new StringBuilder();
        for (String dir : stack) {
            result.append("/").append(dir);
        }
        
        return result.toString();
    }
}
```

**Example Walkthrough**

Input: `path = "/neetcode/practice//...///../courses"`

| Step | Part | Action | Stack After |
|------|------|--------|-------------|
| Split | - | Split by '/' | ["", "neetcode", "practice", "", "...", "", "", "..", "courses"] |
| 1 | "" | Skip (empty) | [] |
| 2 | "neetcode" | Push | ["neetcode"] |
| 3 | "practice" | Push | ["neetcode", "practice"] |
| 4 | "" | Skip (empty) | ["neetcode", "practice"] |
| 5 | "..." | Push (valid name) | ["neetcode", "practice", "..."] |
| 6 | "" | Skip (empty) | ["neetcode", "practice", "..."] |
| 7 | "" | Skip (empty) | ["neetcode", "practice", "..."] |
| 8 | ".." | Pop | ["neetcode", "practice"] |
| 9 | "courses" | Push | ["neetcode", "practice", "courses"] |

Build result: "/" + "neetcode" + "/" + "practice" + "/" + "courses" = **"/neetcode/practice/courses"**

**Complexity Analysis**
- **Time**: O(n) — Split O(n), process O(n), join O(n)
- **Space**: O(n) — Stack and result string

---

#### **Approach 2: Deque Instead of Stack - ALTERNATIVE**

**Core Idea**: Use Deque for more flexibility (can iterate without converting).

**Code Implementation**
```java
class Solution {
    public String simplifyPath(String path) {
        String[] parts = path.split("/");
        Deque<String> deque = new LinkedList<>();
        
        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            } else if (part.equals("..")) {
                if (!deque.isEmpty()) {
                    deque.pollLast();  // Remove last element
                }
            } else {
                deque.offerLast(part);  // Add to end
            }
        }
        
        // Build result
        if (deque.isEmpty()) {
            return "/";
        }
        
        StringBuilder result = new StringBuilder();
        for (String dir : deque) {
            result.append("/").append(dir);
        }
        
        return result.toString();
    }
}
```

**Key Difference**: 
- Deque has more methods (pollLast, offerLast)
- Can iterate directly without intermediate list
- Semantically clearer (pollLast vs pop)

**Complexity Analysis**
- **Time**: O(n) — Same as stack approach
- **Space**: O(n) — Deque + result

---

#### **Approach 3: String Array as Stack - SPACE OPTIMIZED**

**Core Idea**: Use array and pointer instead of Stack object.

**Code Implementation**
```java
class Solution {
    public String simplifyPath(String path) {
        String[] parts = path.split("/");
        String[] stack = new String[parts.length];
        int top = -1;  // Stack pointer
        
        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            } else if (part.equals("..")) {
                if (top >= 0) {
                    top--;  // Pop
                }
            } else {
                stack[++top] = part;  // Push
            }
        }
        
        // Build result
        if (top < 0) {
            return "/";
        }
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= top; i++) {
            result.append("/").append(stack[i]);
        }
        
        return result.toString();
    }
}
```

**Key Difference**: 
- No Stack object, just array + pointer
- Slightly less memory overhead
- Manual pointer management

**Complexity Analysis**
- **Time**: O(n) — Same logic
- **Space**: O(n) — Array same size as parts

---

#### **Approach 4: List Instead of Stack - CLEANER**

**Core Idea**: ArrayList for simpler code.

**Code Implementation**
```java
class Solution {
    public String simplifyPath(String path) {
        String[] parts = path.split("/");
        List<String> stack = new ArrayList<>();
        
        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            } else if (part.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.remove(stack.size() - 1);  // Remove last
                }
            } else {
                stack.add(part);
            }
        }
        
        // Build result
        if (stack.isEmpty()) {
            return "/";
        }
        
        return "/" + String.join("/", stack);
    }
}
```

**Key Difference**: 
- String.join() for cleaner result building
- More readable code
- List is more standard than Stack in modern Java

**Complexity Analysis**
- **Time**: O(n) — Same
- **Space**: O(n) — Same

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Code Complexity | Recommended |
|----------|------|-------|-----------------|-------------|
| **Stack with Split** | **O(n)** | **O(n)** | **Simple ✅** | **Yes ✅** |
| Deque | O(n) | O(n) | Simple | Alternative |
| Array Stack | O(n) | O(n) | Medium | Manual control |
| List + String.join() | O(n) | O(n) | Simple | Modern style |

**Winner**: **Stack with Split** or **List + String.join()** — both excellent!

### Why Split by '/'?

```
Splitting simplifies parsing dramatically!

Without split:
  "/home//user"
  ↓
  Process char by char:
    '/' → start
    'h','o','m','e' → collect as word
    '/' → delimiter
    '/' → duplicate, ignore
    'u','s','e','r' → collect as word
  
  Complex state machine!

With split:
  "/home//user".split("/")
  ↓
  ["", "home", "", "user"]
  
  Process each part:
    "" → skip
    "home" → push
    "" → skip
    "user" → push
  
  Simple loop! ✓

Split does the hard work for us.
```

### Why Use Stack?

```
Directory navigation is LIFO!

Example: /a/b/c/../../d
  
  Path: /a
  Stack: [a]
  
  Path: /a/b
  Stack: [a, b]
  
  Path: /a/b/c
  Stack: [a, b, c]
  
  Path: /a/b/c/..  (go up)
  Stack: [a, b]     (pop c)
  
  Path: /a/b/..     (go up again)
  Stack: [a]        (pop b)
  
  Path: /a/d
  Stack: [a, d]     (push d)
  
Result: /a/d

Last directory entered is first to leave (..)
Perfect for stack!
```

### Why Ignore Empty Strings?

```
Empty strings come from consecutive slashes

"///" split by '/' → ["", "", "", ""]

Example:
  "/home//user" → ["", "home", "", "user"]
  
  "" → from leading '/'
  "home" → directory
  "" → from '//'
  "user" → directory

Empty strings have no meaning, ignore them.

Same for "." (current directory):
  "/home/./user" → ["", "home", ".", "user"]
  
  "." means "stay here", no stack change
```

### Why Check isEmpty() Before Pop?

```
Critical: Can't pop from empty stack!

Example: "/.."
  Split: ["", ".."]
  
  Process "":
    Skip
    Stack: []
  
  Process "..":
    Try to go up from root
    But stack empty!
    Can't pop → do nothing
    Stack: [] (stays at root)
  
Result: "/" ✓

Without check:
  stack.pop() → EmptyStackException ❌

Always check: if (!stack.isEmpty()) { stack.pop(); }
```

### Why "..." is Valid Directory Name?

```
Problem states: "Any sequence of periods that does not match 
the rules above should be treated as a valid directory name."

Rules:
  "." → current directory (special)
  ".." → parent directory (special)
  
Everything else is valid, including:
  "..." → directory named "..."
  "...." → directory named "...."
  ".hidden" → directory named ".hidden"
  "..file" → directory named "..file"

Only exact "." and ".." are special!

Implementation:
  if (part.equals(".")) → skip
  else if (part.equals("..")) → pop
  else → push (includes "...", ".hidden", etc.)
```

---

## Critical Edge Cases & Gotchas

### 1. **Root Directory Only**
```java
Input: path = "/"
Split: ["", ""]
All parts empty or skipped
Stack: []
Output: "/" (root)
```

### 2. **Multiple Slashes**
```java
Input: path = "/home//foo/"
Split: ["", "home", "", "foo", ""]
Stack: ["home", "foo"]
Output: "/home/foo"
```

### 3. **All Dots**
```java
Input: path = "/./././."
Split: ["", ".", ".", ".", "."]
All "." skipped
Stack: []
Output: "/"
```

### 4. **Go Up from Root**
```java
Input: path = "/../"
Split: ["", "..", ""]
Process "..": stack empty, can't pop
Stack: []
Output: "/"
```

### 5. **Three or More Periods**
```java
Input: path = "/.../a/..."
Split: ["", "...", "a", "..."]
"..." is valid directory name
Stack: ["...", "a", "..."]
Output: "/.../a/..."
```

### 6. **Underscore in Name**
```java
Input: path = "/_home/_user"
Split: ["", "_home", "_user"]
Underscore is valid
Stack: ["_home", "_user"]
Output: "/_home/_user"
```

### 7. **Complex Navigation**
```java
Input: path = "/a/./b/../../c/"
Split: ["", "a", ".", "b", "..", "..", "c", ""]
Stack evolution:
  "a" → ["a"]
  "." → ["a"] (skip)
  "b" → ["a", "b"]
  ".." → ["a"] (pop b)
  ".." → [] (pop a)
  "c" → ["c"]
Output: "/c"
```

### 8. **Empty String Input** (Constraint ensures not empty)
```java
Not possible (1 <= path.length)
```

### 9. **No Valid Directories**
```java
Input: path = "/../../../"
All ".." from root
Stack: []
Output: "/"
```

### 10. **Trailing Slash**
```java
Input: path = "/home/user/"
Split: ["", "home", "user", ""]
Last "" is from trailing '/'
Stack: ["home", "user"]
Output: "/home/user" (no trailing '/')
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Not Checking Stack Empty Before Pop**
```java
// WRONG - no empty check
if (part.equals("..")) {
    stack.pop();  // May throw exception!
}
```

**Why wrong**: Popping from empty stack throws exception!

**Dry run failure for path="/..":**
```
Split: ["", ".."]

Process "":
  Skip
  Stack: []

Process "..":
  Condition: part.equals("..")? Yes
  stack.pop() → EmptyStackException ❌
  
Program crashes!

Correct:
  if (part.equals("..")) {
      if (!stack.isEmpty()) {
          stack.pop();  ✓
      }
  }
  
  Stack stays empty, result="/" ✓
```

**Fix**: Always check isEmpty()
```java
if (part.equals("..")) {
    if (!stack.isEmpty()) {
        stack.pop();
    }
}
```

### ❌ **MISTAKE 2: Treating "..." as ".."**
```java
// WRONG - uses startsWith instead of equals
if (part.startsWith("..")) {
    stack.pop();
}
```

**Why wrong**: "..." is a valid directory name, not parent!

**Dry run failure for path="/...///../x":**
```
Split: ["", "...", "", "", "..", "x"]

Process "...":
  Condition: "...".startsWith("..")? Yes ❌
  Pop from stack (empty) → error
  
But "..." should be treated as directory name!
Push to stack!

Correct: use equals() not startsWith()
  "...".equals("..")? No
  Push "..." ✓
```

**Fix**: Use equals() for exact match
```java
if (part.equals("..")) {
    // ...
}
```

### ❌ **MISTAKE 3: Not Handling Empty Parts**
```java
// WRONG - no check for empty
for (String part : parts) {
    if (part.equals(".")) {
        continue;
    } else if (part.equals("..")) {
        // ...
    } else {
        stack.push(part);  // Pushes empty strings!
    }
}
```

**Why wrong**: Empty strings from "//" get pushed!

**Dry run failure for path="//home":**
```
Split: ["", "", "home"]

Process "":
  Not ".", not ".."
  Push "" ❌
  Stack: [""]

Process "":
  Push "" ❌
  Stack: ["", ""]

Process "home":
  Push "home"
  Stack: ["", "", "home"]

Result: "//home" ❌ (should be "/home")
```

**Fix**: Check for empty
```java
if (part.isEmpty() || part.equals(".")) {
    continue;
}
```

### ❌ **MISTAKE 4: Adding Trailing Slash**
```java
// WRONG - adds trailing slash
StringBuilder result = new StringBuilder();
for (String dir : stack) {
    result.append("/").append(dir).append("/");  // Extra '/'!
}
```

**Why wrong**: Result shouldn't end with '/' unless root!

**Dry run failure:**
```
Stack: ["home", "user"]

Build result:
  "/" + "home" + "/"  → "/home/"
  "/" + "user" + "/"  → "/home/user/"
  
Result: "/home/user/" ❌ (should be "/home/user")
```

**Fix**: Don't add trailing slash
```java
for (String dir : stack) {
    result.append("/").append(dir);  // No trailing '/'
}
```

### ❌ **MISTAKE 5: Forgetting Root Directory Case**
```java
// WRONG - no check for empty stack
StringBuilder result = new StringBuilder();
for (String dir : stack) {
    result.append("/").append(dir);
}
return result.toString();  // Returns "" if stack empty!
```

**Why wrong**: Empty stack should return "/" not ""!

**Dry run failure for path="/":**
```
Split: ["", ""]
All empty, skip all
Stack: []

Build result:
  Loop doesn't execute (stack empty)
  result = ""
  
Return: "" ❌ (should be "/")
```

**Fix**: Check for empty stack
```java
if (stack.isEmpty()) {
    return "/";
}
```

### ❌ **MISTAKE 6: Using Wrong Split Delimiter**
```java
// WRONG - splits by any whitespace
String[] parts = path.split("\\s+");
```

**Why wrong**: Should split by '/' only!

**Fix**: Split by "/"
```java
String[] parts = path.split("/");
```

### ❌ **MISTAKE 7: Not Building Path from Stack Bottom**
```java
// WRONG - pops from stack to build result
StringBuilder result = new StringBuilder("/");
while (!stack.isEmpty()) {
    result.append(stack.pop());  // Reverse order!
    if (!stack.isEmpty()) {
        result.append("/");
    }
}
```

**Why wrong**: Builds path in reverse!

**Dry run failure:**
```
Stack: ["home", "user", "docs"]

Pop and build:
  Pop "docs" → "/docs"
  Pop "user" → "/docs/user"
  Pop "home" → "/docs/user/home"
  
Result: "/docs/user/home" ❌ (should be "/home/user/docs")
```

**Fix**: Iterate from bottom to top
```java
for (String dir : stack) {
    result.append("/").append(dir);
}
```

### ❌ **MISTAKE 8: Modifying Original String**
```java
// WRONG - tries to modify path in place
// (Not applicable in Java, but common error in other languages)
```

**In Java**: Strings are immutable, but this is an error in languages like Python where you might try to modify the original path.

**Fix**: Always create new result string.

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **Split path** | 1 | O(n) | O(n) |
| **Process each part** | m parts | O(1) | O(m) ≤ O(n) |
| **Stack operations** | ≤ m | O(1) | O(m) ≤ O(n) |
| **Join stack** | ≤ m | O(1) | O(m) ≤ O(n) |
| **Total** | - | - | **O(n)** |

**Where n = path.length, m = number of parts**

**Time analysis**:
```
Split: O(n) — scan entire path once
Loop through parts: O(m) where m ≤ n
Stack push/pop: O(1) per operation, O(m) total
Build result: O(m) where m ≤ n

Total: O(n) + O(m) + O(m) + O(m) = O(n)

For path.length = 3000: ~3000 operations (very fast)
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Parts array | O(n) | Split creates array of substrings |
| Stack | O(n) | Worst case: all valid directories |
| Result string | O(n) | Final path can be O(n) |
| **Total** | **O(n)** | Linear space |

**Space analysis**:
```
Worst case:
  path = "/a/b/c/d/e/f/g/h" (no ".." or ".")
  Split: n characters → O(n)
  Stack: all directories → O(n)
  Result: full path → O(n)
  Total: 3n = O(n)

Best case:
  path = "/../../../" (all "..")
  Split: O(n)
  Stack: empty → O(1)
  Result: "/" → O(1)
  Total: n + 1 ≈ O(n)

Space complexity: O(n)
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `path = "/neetcode/practice//...///../courses"`

**Expected Output:** `"/neetcode/practice/courses"`

---

**Step 1: Split by '/'**
```
path.split("/")
↓
["", "neetcode", "practice", "", "...", "", "", "..", "courses"]

Index 0: "" (from leading '/')
Index 1: "neetcode"
Index 2: "practice"
Index 3: "" (from '//')
Index 4: "..."
Index 5: "" (from '//')
Index 6: "" (from '//')
Index 7: ".."
Index 8: "courses"
```

---

**Step 2: Process Part "" (Index 0)**
```
Part: ""
Condition: isEmpty()? Yes
Action: Skip

Stack: []
```

---

**Step 3: Process Part "neetcode"**
```
Part: "neetcode"
Conditions:
  isEmpty()? No
  equals(".")? No
  equals("..")? No
Action: Push (valid directory name)

Stack:
   ┌───────────┐
   │ neetcode  │ ← top
   └───────────┘
```

---

**Step 4: Process Part "practice"**
```
Part: "practice"
Action: Push (valid directory)

Stack:
   ┌───────────┐
   │ practice  │ ← top
   ├───────────┤
   │ neetcode  │
   └───────────┘
```

---

**Step 5: Process Part "" (Index 3)**
```
Part: ""
Action: Skip (empty from '//')

Stack:
   ┌───────────┐
   │ practice  │ ← top
   ├───────────┤
   │ neetcode  │
   └───────────┘
```

---

**Step 6: Process Part "..."**
```
Part: "..."
Conditions:
  isEmpty()? No
  equals(".")? No ("..." ≠ ".")
  equals("..")? No ("..." ≠ "..")
Action: Push (valid directory name with 3 periods)

Stack:
   ┌───────────┐
   │    ...    │ ← top
   ├───────────┤
   │ practice  │
   ├───────────┤
   │ neetcode  │
   └───────────┘

Note: "..." is a valid directory name!
Only "." and ".." are special.
```

---

**Step 7: Process Parts "" (Index 5, 6)**
```
Part: "" (twice)
Action: Skip both (empty from '///')

Stack:
   ┌───────────┐
   │    ...    │ ← top
   ├───────────┤
   │ practice  │
   ├───────────┤
   │ neetcode  │
   └───────────┘
```

---

**Step 8: Process Part ".."**
```
Part: ".."
Condition: equals("..")? Yes
Action: Pop (go to parent directory)

Stack before pop:
   ┌───────────┐
   │    ...    │ ← top (will be removed)
   ├───────────┤
   │ practice  │
   ├───────────┤
   │ neetcode  │
   └───────────┘

Stack after pop:
   ┌───────────┐
   │ practice  │ ← top
   ├───────────┤
   │ neetcode  │
   └───────────┘

Removed "..." because we went up one level!
```

---

**Step 9: Process Part "courses"**
```
Part: "courses"
Action: Push (valid directory)

Stack:
   ┌───────────┐
   │  courses  │ ← top
   ├───────────┤
   │ practice  │
   ├───────────┤
   │ neetcode  │
   └───────────┘
```

---

**Step 10: Build Result**
```
Stack: ["neetcode", "practice", "courses"]

Build path:
  Start with "/"
  Iterate through stack from bottom to top:
    "/" + "neetcode" → "/neetcode"
    + "/" + "practice" → "/neetcode/practice"
    + "/" + "courses" → "/neetcode/practice/courses"

Final Result: "/neetcode/practice/courses" ✓
```

---

### Directory Navigation Visualization

```
Input path: /neetcode/practice//...///../courses

Directory tree navigation:

Start: / (root)
       │
       ├─ neetcode (go into)
       │  │
       │  ├─ practice (go into)
       │  │  │
       │  │  ├─ ... (go into)
       │  │  │  │
       │  │  │  └─ .. (go back UP to practice)
       │  │  │
       │  │  └─ courses (go into)
       │  │
       
Final location: /neetcode/practice/courses

Stack represents current path:
  ["neetcode", "practice", "courses"]
```

---

### Another Example: Complex Navigation

**Input:** `path = "/a/./b/../../c/"`

```
Split: ["", "a", ".", "b", "..", "..", "c", ""]

Process "": skip
Stack: []

Process "a": push
Stack: [a]
Path: /a

Process ".": skip (current directory)
Stack: [a]
Path: /a (still here)

Process "b": push
Stack: [a, b]
Path: /a/b

Process "..": pop
Stack: [a]
Path: /a (went back up)

Process "..": pop
Stack: []
Path: / (went back to root)

Process "c": push
Stack: [c]
Path: /c

Process "": skip
Stack: [c]

Result: "/c" ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Lines | Clarity | Recommended |
|----------|------|-------|------------|---------|-------------|
| **Stack with Split** | **O(n)** | **O(n)** | **~25** | **Excellent ✅** | **Yes ✅** |
| Deque | O(n) | O(n) | ~25 | Excellent | Alternative |
| Array Stack | O(n) | O(n) | ~28 | Good | Manual control |
| List + String.join() | O(n) | O(n) | ~22 | Excellent ✅ | Modern style |

**All optimal approaches have O(n) time and space**

**Recommendation**: Use **Stack with Split** or **List + String.join()** — both are clean and optimal!

---

## Key Takeaways

1. **Split path by '/'** — simplifies parsing dramatically
2. **Use stack for directory navigation** — LIFO matches Unix traversal
3. **Three rules for parts:**
   - Empty or "." → skip
   - ".." → pop from stack (if not empty)
   - Anything else → push to stack
4. **"..." is valid directory** — only "." and ".." are special
5. **Always check isEmpty() before pop** — prevent exception
6. **Build result by joining stack with '/'** — add leading '/'
7. **Empty stack returns "/"** — root directory
8. **No trailing slash** — unless result is root
9. **O(n) time, O(n) space** — optimal and efficient

---

## Interview Tips

**What to say in an interview:**

> "This problem asks to simplify a Unix path to its canonical form. The key insight is that Unix path navigation is naturally stack-based: entering a directory is like pushing onto a stack, and going up with '..' is like popping. I'll split the path by '/' to get individual parts, then process each part according to the rules: skip empty strings and '.', pop for '..', and push for valid directory names. Note that '...' with three or more periods is a valid directory name, not a special symbol—only '.' and '..' are special. After processing, I'll join the stack elements with '/' and add a leading '/'. If the stack is empty, I return '/' for the root directory. Time complexity is O(n) for splitting and processing, space is O(n) for the stack and result."

**Key points to mention:**
1. **Stack models directory navigation** — push to enter, pop to go up
2. **Split by '/' first** — easier than character-by-character parsing
3. **Three cases:** empty/'.', '..', valid name
4. **Check isEmpty() before pop** — prevent exception when at root
5. **"..." is valid directory** — only exactly "." and ".." are special
6. **Join with '/' at end** — build canonical path
7. **O(n) optimal** — single pass through input

**Common Follow-ups:**
- "What if '..' goes above root?" → Stay at root, don't pop from empty stack
- "How to handle '...'?" → It's a valid directory name, treat like any other name
- "Can you do it without split?" → Yes but more complex, character-by-character parsing
- "What's the space complexity?" → O(n) for stack and parts array

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Simplify Path** | Medium | **Stack + String Processing** | **This problem** |
| Basic Calculator | Hard | Stack + Expression Parsing | Numbers and operators |
| Decode String | Medium | Stack + String Manipulation | Nested brackets |
| Remove K Digits | Medium | Monotonic Stack + String | Digit removal |
| Valid Parentheses | Easy | Stack + Matching | Bracket matching |
| Evaluate Reverse Polish Notation | Medium | Stack + Expression Eval | Postfix notation |

**Pattern Progression**:
1. **Path simplification** (this problem) — Directory navigation
2. **Expression parsing** (Calculator, RPN) — Operator precedence
3. **String manipulation** (Decode String) — Nested structures
4. **Monotonic properties** (Remove K Digits) — Ordering constraints

---

## Final Pattern Label

✅ **Stack (Path Navigation / Directory Traversal) + String Processing**

**Remember:** Split path by **'/'** to get parts. Use **stack** to store valid directory names. Process each part: skip empty and **'.'**, pop for **'..'** (check isEmpty first!), push for valid names (including **'...'** which is NOT special). Build result by joining stack with **'/'** and adding leading **'/'**. Empty stack returns **'/'** (root). No trailing slash unless root. **O(n) time**, **O(n) space**. Unix navigation is naturally LIFO—perfect for stack!
