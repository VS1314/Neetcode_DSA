# Daily Temperatures

## Problem Description

**Difficulty**: Medium

You are given an array of integers `temperatures` where `temperatures[i]` represents the daily temperatures on the `i`th day.

Return an array `result` where `result[i]` is the **number of days** after the `i`th day before a **warmer temperature** appears on a future day.

If there is **no day in the future** where a warmer temperature will appear for the `i`th day, set `result[i]` to `0` instead.

## Examples

### Example 1:
```
Input: temperatures = [73, 74, 75, 71, 69, 72, 76, 73]
Output: [1, 1, 4, 2, 1, 1, 0, 0]

Explanation:
  Day 0 (73): Day 1 has 74 (warmer) → wait 1 day
  Day 1 (74): Day 2 has 75 (warmer) → wait 1 day
  Day 2 (75): Day 6 has 76 (warmer) → wait 4 days
  Day 3 (71): Day 5 has 72 (warmer) → wait 2 days
  Day 4 (69): Day 5 has 72 (warmer) → wait 1 day
  Day 5 (72): Day 6 has 76 (warmer) → wait 1 day
  Day 6 (76): No warmer day → 0
  Day 7 (73): No warmer day → 0
```

### Example 2:
```
Input: temperatures = [30, 38, 30, 36, 35, 40, 28]
Output: [1, 4, 1, 2, 1, 0, 0]

Explanation:
  Day 0 (30): Day 1 has 38 (warmer) → wait 1 day
  Day 1 (38): Day 5 has 40 (warmer) → wait 4 days
  Day 2 (30): Day 3 has 36 (warmer) → wait 1 day
  Day 3 (36): Day 5 has 40 (warmer) → wait 2 days
  Day 4 (35): Day 5 has 40 (warmer) → wait 1 day
  Day 5 (40): No warmer day → 0
  Day 6 (28): No warmer day → 0
```

### Example 3:
```
Input: temperatures = [30, 60, 90]
Output: [1, 1, 0]

Explanation:
  Strictly increasing temperatures
  Each day gets answer on next day except last
```

### Example 4:
```
Input: temperatures = [90, 60, 30]
Output: [0, 0, 0]

Explanation:
  Strictly decreasing temperatures
  No warmer days ahead for any day
```

### Example 5:
```
Input: temperatures = [22, 21, 20]
Output: [0, 0, 0]

Explanation:
  Decreasing temperatures, no warmer days
```

### Example 6:
```
Input: temperatures = [75, 75, 75]
Output: [0, 0, 0]

Explanation:
  All same temperature
  No warmer (strictly greater) day exists
```

### Example 7:
```
Input: temperatures = [89, 62, 70, 58, 47, 99, 65]
Output: [5, 1, 3, 2, 1, 0, 0]

Explanation:
  Day 0 (89): Day 5 has 99 (warmer) → wait 5 days
  Day 1 (62): Day 2 has 70 (warmer) → wait 1 day
  Day 2 (70): Day 5 has 99 (warmer) → wait 3 days
  Day 3 (58): Day 5 has 99 (warmer) → wait 2 days
  Day 4 (47): Day 5 has 99 (warmer) → wait 1 day
  Day 5 (99): No warmer day → 0
  Day 6 (65): No warmer day → 0
```

## Constraints
- 1 <= temperatures.length <= 10^5
- 1 <= temperatures[i] <= 100
- All temperatures are positive integers

**Recommended Complexity**: O(n) time and O(n) space, where n is the number of days

---

## Pattern Recognition

**Primary Pattern**: **Monotonic Stack (Next Greater Element)**

**Why This Pattern?**
- Need to find next greater element for each position
- Looking forward in array (to the right)
- Brute force O(n²) too slow
- Stack can process in O(n) amortized

**Key Insight**: Process Right to Left, Stack Maintains Answer
```
Next Greater Element Problem:
  For each element, find the next element to its right that is greater
  
Naive approach: For each day, scan all future days
  for i in 0..n-1:
      for j in i+1..n-1:
          if temperatures[j] > temperatures[i]:
              result[i] = j - i
              break
  
Time: O(n²) — nested loops
Too slow for n = 10^5!

Monotonic Stack approach:
  Use stack to track unresolved days
  Process left to right
  When warmer day found, resolve all colder days
  → O(n) amortized ✓
```

**The Stack Strategy**:
```
Stack stores indices of days waiting for warmer temperature

Invariant: Stack contains indices in DECREASING temperature order
  temperatures[stack[0]] > temperatures[stack[1]] > temperatures[stack[2]] ...
  
Why decreasing?
  If day i is warmer than day j where i < j:
    Day i will get answered before day j
    So we can discard day j (it's blocked by day i)

Algorithm:
  1. Initialize result array with 0s
  2. Create empty stack
  3. For each day i:
     a. While stack not empty AND temperatures[i] > temperatures[stack.top()]:
        - Pop index from stack
        - This day found its answer! result[popped] = i - popped
     b. Push i onto stack
  4. Return result (remaining stack indices stay 0)
```

**Example Showing Monotonic Property**:
```
Input: [73, 74, 75, 71, 69, 72, 76, 73]

Process day 0 (73):
  Stack: [0]
  
Process day 1 (74):
  74 > 73, pop 0, result[0] = 1
  Stack: [1]
  
Process day 2 (75):
  75 > 74, pop 1, result[1] = 1
  Stack: [2]
  
Process day 3 (71):
  71 < 75, push 3
  Stack: [2, 3] (decreasing: 75, 71)
  
Process day 4 (69):
  69 < 71, push 4
  Stack: [2, 3, 4] (decreasing: 75, 71, 69)
  
Process day 5 (72):
  72 > 69, pop 4, result[4] = 1
  72 > 71, pop 3, result[3] = 2
  72 < 75, push 5
  Stack: [2, 5] (decreasing: 75, 72)
  
Process day 6 (76):
  76 > 72, pop 5, result[5] = 1
  76 > 75, pop 2, result[2] = 4
  Stack: [6]
  
Process day 7 (73):
  73 < 76, push 7
  Stack: [6, 7] (decreasing: 76, 73)
  
Final: result[6] = 0, result[7] = 0 (no warmer days)

Result: [1, 1, 4, 2, 1, 1, 0, 0] ✓
```

**Why Monotonic Decreasing?**
```
Key insight: Smaller temperatures "hide behind" larger ones

Example: [75, 70, 72]
  
  After 75, 70: Stack [0, 1] (temps: 75, 70)
  
  Process 72:
    72 > 70, pop 1 (72 is answer for 70)
    72 < 75, push 2
    Stack: [0, 2] (temps: 75, 72)
    
  Why keep 75 but remove 70?
    Any future temp > 72 will also be > 70
    But 70 already got its answer (72)
    No need to keep 70 in stack!
  
  Stack only keeps "unresolved" days
  In decreasing order: largest unresolved at bottom

This property ensures O(n) time:
  Each index pushed once
  Each index popped at most once
  Total operations: 2n = O(n)
```

**Critical Detail**: Store Indices, Not Temperatures
```
Why indices?
  Need to compute distance: current_index - previous_index
  
Stack: [0, 3, 4] (representing days)
  Access temps: temperatures[0], temperatures[3], temperatures[4]
  Compute distance: current - stack.top()

If we stored temps only:
  Stack: [75, 71, 69]
  Can't compute distance! Lost index information.
```

**Related Patterns**:
1. **Monotonic Stack** — This problem
2. **Next Greater Element** — Same concept
3. **Previous Greater Element** — Reverse direction
4. **Stock Span** — Similar counting

---

## Algorithm & Approach

### Core Insight

**Why Naive Approach Fails:**
```
Naive: For each day, scan all future days
  for i in range(n):
      for j in range(i+1, n):
          if temperatures[j] > temperatures[i]:
              result[i] = j - i
              break
  
Time: O(n²) — nested loops
For n = 10^5: 10^10 operations ❌
Too slow!

Monotonic Stack:
  Single pass through array
  Each element pushed/popped once
  Total: O(n) ✓
```

**The Optimal Strategy**:
```
Key observations:
  1. Process days left to right (chronological order)
  2. Stack tracks unresolved days (waiting for warmer)
  3. When warmer day arrives, resolve multiple past days
  4. Maintain decreasing temperature order in stack
  5. Each day processed once (amortized)
  
All operations O(1) amortized:
  Push: O(1)
  Pop: O(1) per day (each popped at most once)
  Comparison: O(1)
  
Total: O(n) for n days
```

### Step-by-Step Algorithm

---

#### **Approach 1: Monotonic Stack (Left to Right) - STANDARD**

**Core Idea**:
- Process days left to right
- Stack stores indices of days waiting for warmer temperature
- When warmer day found, pop and resolve all colder days

**Algorithm**
```
dailyTemperatures(temperatures):
    n = temperatures.length
    result = array of size n, initialized to 0
    stack = new Stack()
    
    for i from 0 to n-1:
        // Current day's temperature
        currentTemp = temperatures[i]
        
        // Pop all days colder than current
        while (stack not empty AND temperatures[stack.top()] < currentTemp):
            prevIndex = stack.pop()
            result[prevIndex] = i - prevIndex  // Days to wait
        
        // Push current day index
        stack.push(i)
    
    // Remaining indices in stack have no warmer day (already 0)
    return result
```

**Code Implementation**
```java
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];  // Initialized to 0 by default
        Stack<Integer> stack = new Stack<>();  // Stores indices
        
        for (int i = 0; i < n; i++) {
            // While current temperature is warmer than stack top
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int prevIndex = stack.pop();
                result[prevIndex] = i - prevIndex;  // Days until warmer
            }
            
            // Push current index
            stack.push(i);
        }
        
        // Remaining stack indices have no warmer day (result already 0)
        return result;
    }
}
```

**Example Walkthrough**

Input: `temperatures = [73, 74, 75, 71, 69]`

| i | Temp | Stack Before | Action | Stack After | Result Updates |
|---|------|--------------|--------|-------------|----------------|
| 0 | 73 | [] | Push 0 | [0] | - |
| 1 | 74 | [0] | 74>73, pop 0, result[0]=1, push 1 | [1] | result[0]=1 |
| 2 | 75 | [1] | 75>74, pop 1, result[1]=1, push 2 | [2] | result[1]=1 |
| 3 | 71 | [2] | 71<75, push 3 | [2, 3] | - |
| 4 | 69 | [2, 3] | 69<71, push 4 | [2, 3, 4] | - |

Final: Indices 2, 3, 4 remain in stack → result[2]=0, result[3]=0, result[4]=0

Output: `[1, 1, 0, 0, 0]`

**Complexity Analysis**
- **Time**: O(n) — Each index pushed and popped at most once (amortized)
- **Space**: O(n) — Stack can hold up to n indices in worst case

---

#### **Approach 2: Monotonic Stack (Right to Left) - ALTERNATIVE**

**Core Idea**: Process days right to left, find next greater to the right.

**Code Implementation**
```java
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        // Process from right to left
        for (int i = n - 1; i >= 0; i--) {
            // Pop all temperatures <= current (not useful)
            while (!stack.isEmpty() && temperatures[stack.peek()] <= temperatures[i]) {
                stack.pop();
            }
            
            // If stack not empty, top is next greater
            if (!stack.isEmpty()) {
                result[i] = stack.peek() - i;
            }
            // Else result[i] = 0 (already initialized)
            
            stack.push(i);
        }
        
        return result;
    }
}
```

**Key Difference**: 
- Process right to left instead of left to right
- Stack maintains next greater elements to the right
- Pop elements <= current (not just <)

**Complexity Analysis**
- **Time**: O(n) — Same amortized complexity
- **Space**: O(n) — Stack storage

---

#### **Approach 3: Array as Stack - SPACE OPTIMIZED**

**Core Idea**: Use array instead of Stack object for slight optimization.

**Code Implementation**
```java
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        int[] stack = new int[n];
        int top = -1;  // Stack pointer
        
        for (int i = 0; i < n; i++) {
            // Pop all colder days
            while (top >= 0 && temperatures[stack[top]] < temperatures[i]) {
                int prevIndex = stack[top--];
                result[prevIndex] = i - prevIndex;
            }
            
            // Push current index
            stack[++top] = i;
        }
        
        return result;
    }
}
```

**Key Difference**: 
- Array instead of Stack object
- Manual top pointer management
- Slightly faster (no object overhead)

**Complexity Analysis**
- **Time**: O(n) — Same logic
- **Space**: O(n) — Array for stack

---

#### **Approach 4: Brute Force - FOR COMPARISON**

**Core Idea**: For each day, scan all future days until warmer found.

**Code Implementation**
```java
class Solution {
    // This is the brute force approach - O(n²)
    // Included for comparison only
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i;
                    break;  // Found, move to next i
                }
            }
            // If no warmer day found, result[i] stays 0
        }
        
        return result;
    }
}
```

**Key Difference**: 
- No stack, simple nested loops
- Easy to understand but slow
- O(n²) time complexity

**Complexity Analysis**
- **Time**: O(n²) — Nested loops
- **Space**: O(1) — No extra space (excluding output)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Code Complexity | Recommended |
|----------|------|-------|-----------------|-------------|
| **Monotonic Stack (L→R)** | **O(n)** | **O(n)** | **Medium ✅** | **Yes ✅** |
| Monotonic Stack (R→L) | O(n) | O(n) | Medium | Alternative |
| Array as Stack | O(n) | O(n) | Medium | Slight optimization |
| Brute Force | O(n²) | O(1) | Easy | Learning only |

**Winner**: **Monotonic Stack (Left to Right)** — optimal time, intuitive flow!

### Why Monotonic Stack Works?

```
Core insight: When warmer day arrives, it answers MULTIPLE past days

Example: [70, 65, 60, 75]
  
  After processing 60:
    Stack: [0, 1, 2] (indices for 70, 65, 60)
    All waiting for warmer temperature
  
  Process 75:
    75 > 60, pop 2, result[2] = 3-2 = 1
    75 > 65, pop 1, result[1] = 3-1 = 2
    75 > 70, pop 0, result[0] = 3-0 = 3
    Push 3
    
    One day (75) resolved THREE days! ✓

This is why it's O(n):
  Each day pushed once: n operations
  Each day popped at most once: n operations
  Total: 2n = O(n) ✓
```

### Why Decreasing Order in Stack?

```
Maintain decreasing temperatures in stack:
  Stack bottom = highest unresolved temperature
  Stack top = lowest unresolved temperature

Example showing why:
  [80, 75, 70, 76]
  
  After 70: Stack [0, 1, 2] (temps: 80, 75, 70) — decreasing ✓
  
  Process 76:
    76 > 70, pop 2 → 70 resolved
    76 > 75, pop 1 → 75 resolved
    76 < 80, stop
    Push 3
    Stack: [0, 3] (temps: 80, 76) — still decreasing ✓

Property preserved!

Why this works:
  If temp[i] > temp[j] where i < j:
    Any future temp > temp[i] will also be > temp[j]
    But temp[j] gets resolved when temp[i] resolves
    So we don't need to keep temp[j] after temp[i] resolves it

Stack naturally filters to keep only "relevant" unresolved days
```

### Why Store Indices, Not Temperatures?

```
MUST store indices to compute distance!

Example: [73, 74]
  
  If we stored temperatures:
    Stack: [73]
    Process 74: 74 > 73, pop 73
    Need distance... but we don't have indices! ❌
  
  Storing indices:
    Stack: [0]
    Process 74 at index 1: 74 > 73, pop 0
    Distance: 1 - 0 = 1 ✓
    result[0] = 1

Always store indices, access temperatures via temperatures[index]
```

### Amortized O(n) Proof

```
Claim: While loop inside for loop is still O(n) total

Proof by counting operations:
  For loop: n iterations (i from 0 to n-1)
  Each i pushed exactly once: n pushes
  Each i popped at most once: ≤ n pops
  
  Total push + pop: n + n = 2n operations
  Amortized per iteration: 2n / n = 2 = O(1)
  Total: O(n) ✓

Example: [90, 80, 70, 60, 50, 100]
  Push 90, 80, 70, 60, 50: 5 pushes
  Process 100:
    Pop 50, 60, 70, 80, 90: 5 pops
  Push 100: 1 push
  
  Total: 6 pushes + 5 pops = 11 operations for 6 elements
  = O(n) ✓

Even though while loop can iterate 5 times,
it only happens once across entire algorithm!
```

---

## Critical Edge Cases & Gotchas

### 1. **All Increasing Temperatures**
```java
Input: [30, 40, 50, 60, 70]
Each day has warmer next day
Output: [1, 1, 1, 1, 0]
```

### 2. **All Decreasing Temperatures**
```java
Input: [70, 60, 50, 40, 30]
No warmer days ahead
Output: [0, 0, 0, 0, 0]
```

### 3. **All Same Temperature**
```java
Input: [50, 50, 50, 50]
No strictly warmer day (50 not > 50)
Output: [0, 0, 0, 0]
```

### 4. **Single Element**
```java
Input: [75]
No future days
Output: [0]
```

### 5. **Two Elements - Warmer**
```java
Input: [30, 40]
Output: [1, 0]
```

### 6. **Two Elements - Colder**
```java
Input: [40, 30]
Output: [0, 0]
```

### 7. **Peak in Middle**
```java
Input: [60, 80, 60]
80 is peak, no warmer day after it
Output: [1, 0, 0]
```

### 8. **Large Gap**
```java
Input: [50, 40, 30, 20, 10, 100]
Last day resolves all previous days
Output: [5, 4, 3, 2, 1, 0]
```

### 9. **Multiple Equal Temperatures**
```java
Input: [70, 70, 70, 80]
First three resolved by 80
Output: [3, 2, 1, 0]
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Storing Temperatures Instead of Indices**
```java
// WRONG - stores temperatures
Stack<Integer> stack = new Stack<>();
for (int temp : temperatures) {
    while (!stack.isEmpty() && stack.peek() < temp) {
        int prevTemp = stack.pop();
        // WRONG! Can't compute distance without index
        result[???] = ???;  // Lost index information!
    }
    stack.push(temp);
}
```

**Why wrong**: Can't compute distance without indices!

**Dry run failure for [73, 74]:**
```
Process 73: push 73, stack = [73]
Process 74:
  74 > 73, pop 73
  Need to set result[?] = ?
  Have: prevTemp = 73, currentTemp = 74
  Missing: both indices!
  Can't compute distance or know which result index to update ❌
```

**Fix**: Store indices
```java
Stack<Integer> stack = new Stack<>();
for (int i = 0; i < temperatures.length; i++) {
    while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
        int prevIndex = stack.pop();
        result[prevIndex] = i - prevIndex;  // Correct!
    }
    stack.push(i);
}
```

### ❌ **MISTAKE 2: Using <= Instead of < in Comparison**
```java
// WRONG - uses <= instead of <
while (!stack.isEmpty() && temperatures[stack.peek()] <= temperatures[i]) {
    int prevIndex = stack.pop();
    result[prevIndex] = i - prevIndex;
}
```

**Why wrong**: Pops equal temperatures, but equal is not warmer!

**Dry run failure for [70, 70, 80]:**
```
Process 70 (i=0): push 0, stack = [0]
Process 70 (i=1):
  70 <= 70? Yes, pop 0
  result[0] = 1 - 0 = 1 ❌
  Should be 2, not 1! (70 is not warmer than 70)
  
  Push 1, stack = [1]
Process 80 (i=2):
  80 <= 70? No, no pop
  Push 2, stack = [1, 2]
  
Result: [1, 0, 0] ❌
Expected: [2, 1, 0] ✓

Equal temperature is NOT warmer!
```

**Fix**: Use < (strictly less than)
```java
while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i])
```

### ❌ **MISTAKE 3: Not Checking Stack Empty Before Peek**
```java
// WRONG - doesn't check empty first
while (temperatures[stack.peek()] < temperatures[i]) {
    // WRONG! peek() on empty stack throws exception
    stack.pop();
}
```

**Why wrong**: peek() on empty stack throws EmptyStackException!

**Dry run failure for first element:**
```
Process i=0:
  Condition: temperatures[stack.peek()] < temperatures[0]
  Stack empty! peek() throws EmptyStackException ❌
```

**Fix**: Check isEmpty() first
```java
while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i])
```

### ❌ **MISTAKE 4: Forgetting to Push Current Index**
```java
// WRONG - forgets to push current index
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
        int prevIndex = stack.pop();
        result[prevIndex] = i - prevIndex;
    }
    // WRONG! Forgot: stack.push(i);
}
```

**Why wrong**: Current day never added to stack, can't be resolved later!

**Dry run failure for [70, 80, 90]:**
```
Process 70 (i=0): stack = [] (empty, forgot push) ❌
Process 80 (i=1):
  Stack empty, nothing to pop
  Forgot push, stack = [] ❌
Process 90 (i=2):
  Stack empty, nothing to pop
  
All days missed! Result: [0, 0, 0] ❌
Expected: [1, 1, 0] ✓
```

**Fix**: Always push current index
```java
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
        int prevIndex = stack.pop();
        result[prevIndex] = i - prevIndex;
    }
    stack.push(i);  // Don't forget!
}
```

### ❌ **MISTAKE 5: Using i Instead of prevIndex for Result**
```java
// WRONG - uses wrong index for result
while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
    int prevIndex = stack.pop();
    result[i] = i - prevIndex;  // WRONG! Should be result[prevIndex]
}
```

**Why wrong**: Updates wrong result index!

**Dry run failure for [70, 80]:**
```
Process 70 (i=0): push 0, stack = [0]
Process 80 (i=1):
  80 > 70, pop 0 (prevIndex = 0)
  result[1] = 1 - 0 = 1 ❌ (should update result[0]!)
  
Result: [0, 1] ❌
Expected: [1, 0] ✓

We resolved day 0 but updated day 1's result!
```

**Fix**: Use prevIndex for result
```java
result[prevIndex] = i - prevIndex;  // Correct!
```

### ❌ **MISTAKE 6: Computing Distance Incorrectly**
```java
// WRONG - computes distance in wrong order
while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
    int prevIndex = stack.pop();
    result[prevIndex] = prevIndex - i;  // WRONG! Negative distance
}
```

**Why wrong**: Distance is negative!

**Dry run failure for [70, 80]:**
```
Process 80 (i=1):
  Pop 0 (prevIndex = 0)
  result[0] = 0 - 1 = -1 ❌
  
Negative days! Impossible!
```

**Fix**: Current minus previous
```java
result[prevIndex] = i - prevIndex;  // Future - Past = positive
```

### ❌ **MISTAKE 7: Not Initializing Result Array**
```java
// WRONG - doesn't initialize result
int[] result;  // WRONG! Not initialized
Stack<Integer> stack = new Stack<>();
for (int i = 0; i < n; i++) {
    // ...
}
return result;  // NullPointerException or garbage values
```

**Why wrong**: Uninitialized array or null reference!

**Fix**: Initialize with size n
```java
int[] result = new int[n];  // Initialized to 0s automatically
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **Iterate days** | n | - | - |
| **Push to stack** | n | O(1) | O(n) |
| **Pop from stack** | ≤ n | O(1) | O(n) |
| **Comparisons** | ≤ 2n | O(1) | O(n) |
| **Total** | - | - | **O(n)** |

**Amortized Analysis**:
```
While loop inside for loop looks like O(n²), but it's O(n)!

Proof:
  Each index pushed exactly once: n pushes
  Each index popped at most once: n pops
  Total stack operations: 2n
  
For loop: n iterations
While loop total: ≤ n pops across all iterations
  (each index popped at most once)
  
Total comparisons: O(n)
Total: O(n) ✓

Example: [80, 70, 60, 50, 90]
  Pushes: 0, 1, 2, 3 → 4 operations
  Process 90:
    Pops: 3, 2, 1, 0 → 4 operations
  Push 4 → 1 operation
  
  Total: 9 operations for 5 elements
  = ~2 operations per element = O(n) ✓
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Stack | O(n) | Worst case: all indices (decreasing temps) |
| Result array | O(n) | Required output |
| Variables | O(1) | i, prevIndex (constant) |
| **Total** | **O(n)** | Stack + output |

**Space analysis**:
```
Worst case: Strictly decreasing temperatures
  Example: [90, 80, 70, 60, 50]
  All pushed, none popped
  Stack size: n
  
Best case: Strictly increasing temperatures
  Example: [50, 60, 70, 80, 90]
  Each pushed then immediately popped
  Max stack size: 1
  
Average case: Mixed
  Stack size: O(n) in worst case
  
Space complexity: O(n) for stack
  (Result array required, doesn't count toward auxiliary space)
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `temperatures = [73, 74, 75, 71, 69, 72, 76, 73]`

**Expected Output:** `[1, 1, 4, 2, 1, 1, 0, 0]`

---

**Initial State:**
```
Temperatures: [73, 74, 75, 71, 69, 72, 76, 73]
Result: [0, 0, 0, 0, 0, 0, 0, 0]
Stack: []
```

---

**Step 1: Process i=0, temp=73**
```
Action: Stack empty, push 0

Stack:
   ┌───┐
   │ 0 │ ← top (temp: 73)
   └───┘

Stack: [0]
Result: [0, 0, 0, 0, 0, 0, 0, 0]
```

---

**Step 2: Process i=1, temp=74**
```
Comparison: 74 > 73 (temperatures[0])
Action:
  Pop 0, result[0] = 1 - 0 = 1
  Push 1

Stack:
   ┌───┐
   │ 1 │ ← top (temp: 74)
   └───┘

Stack: [1]
Result: [1, 0, 0, 0, 0, 0, 0, 0]
       ↑ (day 0 resolved!)
```

---

**Step 3: Process i=2, temp=75**
```
Comparison: 75 > 74 (temperatures[1])
Action:
  Pop 1, result[1] = 2 - 1 = 1
  Push 2

Stack:
   ┌───┐
   │ 2 │ ← top (temp: 75)
   └───┘

Stack: [2]
Result: [1, 1, 0, 0, 0, 0, 0, 0]
          ↑ (day 1 resolved!)
```

---

**Step 4: Process i=3, temp=71**
```
Comparison: 71 < 75 (temperatures[2])
Action: Push 3 (no pop)

Stack:
   ┌───┐
   │ 3 │ ← top (temp: 71)
   ├───┤
   │ 2 │ (temp: 75)
   └───┘

Stack: [2, 3] (decreasing: 75, 71) ✓
Result: [1, 1, 0, 0, 0, 0, 0, 0]
```

---

**Step 5: Process i=4, temp=69**
```
Comparison: 69 < 71 (temperatures[3])
Action: Push 4 (no pop)

Stack:
   ┌───┐
   │ 4 │ ← top (temp: 69)
   ├───┤
   │ 3 │ (temp: 71)
   ├───┤
   │ 2 │ (temp: 75)
   └───┘

Stack: [2, 3, 4] (decreasing: 75, 71, 69) ✓
Result: [1, 1, 0, 0, 0, 0, 0, 0]
```

---

**Step 6: Process i=5, temp=72**
```
Comparison 1: 72 > 69 (temperatures[4])
  Pop 4, result[4] = 5 - 4 = 1

Comparison 2: 72 > 71 (temperatures[3])
  Pop 3, result[3] = 5 - 3 = 2

Comparison 3: 72 < 75 (temperatures[2])
  Stop, push 5

Stack:
   ┌───┐
   │ 5 │ ← top (temp: 72)
   ├───┤
   │ 2 │ (temp: 75)
   └───┘

Stack: [2, 5] (decreasing: 75, 72) ✓
Result: [1, 1, 0, 2, 1, 0, 0, 0]
                ↑  ↑ (days 3, 4 resolved!)
```

---

**Step 7: Process i=6, temp=76**
```
Comparison 1: 76 > 72 (temperatures[5])
  Pop 5, result[5] = 6 - 5 = 1

Comparison 2: 76 > 75 (temperatures[2])
  Pop 2, result[2] = 6 - 2 = 4

Stack empty, push 6

Stack:
   ┌───┐
   │ 6 │ ← top (temp: 76)
   └───┘

Stack: [6]
Result: [1, 1, 4, 2, 1, 1, 0, 0]
             ↑        ↑ (days 2, 5 resolved!)
```

---

**Step 8: Process i=7, temp=73**
```
Comparison: 73 < 76 (temperatures[6])
Action: Push 7 (no pop)

Stack:
   ┌───┐
   │ 7 │ ← top (temp: 73)
   ├───┤
   │ 6 │ (temp: 76)
   └───┘

Stack: [6, 7] (decreasing: 76, 73) ✓
Result: [1, 1, 4, 2, 1, 1, 0, 0]
```

---

**Final: Loop Complete**
```
Stack: [6, 7] (remaining indices have no warmer day)
result[6] = 0 (already initialized)
result[7] = 0 (already initialized)

Final Result: [1, 1, 4, 2, 1, 1, 0, 0] ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Lines | Clarity | Recommended |
|----------|------|-------|------------|---------|-------------|
| **Monotonic Stack (L→R)** | **O(n)** | **O(n)** | **~15** | **Excellent ✅** | **Yes ✅** |
| Monotonic Stack (R→L) | O(n) | O(n) | ~15 | Good | Alternative |
| Array as Stack | O(n) | O(n) | ~18 | Good | Slight optimization |
| Brute Force | O(n²) | O(1) | ~10 | Easy | Too slow |

**Recommendation**: Use **Monotonic Stack (Left to Right)** — optimal time, intuitive, standard!

---

## Key Takeaways

1. **Monotonic stack for next greater** — O(n) instead of O(n²)
2. **Store indices, not values** — need distance computation
3. **Decreasing order in stack** — temperatures decrease from bottom to top
4. **Use < not <=** — equal temperature is not warmer
5. **Check isEmpty() before peek()** — avoid exceptions
6. **Always push current index** — needed for future resolutions
7. **Amortized O(n) time** — each index pushed/popped once
8. **Process left to right** — chronological, natural flow

---

## Interview Tips

**What to say in an interview:**

> "This is a classic 'next greater element' problem that I'll solve using a monotonic stack. The naive approach of checking all future days for each day would be O(n²), which is too slow. Instead, I'll use a stack to track indices of days waiting for a warmer temperature, maintaining a decreasing order of temperatures. As I process each day from left to right, if the current temperature is warmer than the stack top, I pop that index and record the distance. This can cascade—one warm day can resolve multiple previous cold days. I store indices rather than temperatures because I need to compute the number of days between them. Each index is pushed and popped at most once, giving O(n) amortized time complexity with O(n) space for the stack."

**Key points to mention:**
1. **Next greater element pattern** — Monotonic stack
2. **Store indices** — Need distance computation
3. **Decreasing order** — Stack invariant
4. **Cascading resolution** — While loop pops multiple
5. **Amortized O(n)** — Each pushed/popped once
6. **Check < not <=** — Strictly greater required

**Common Follow-ups:**
- "Why monotonic stack?" → Maintains decreasing order, finds next greater in O(n)
- "Why store indices?" → Need distance calculation (current - previous)
- "Time complexity?" → O(n) amortized, each pushed/popped once
- "Can you optimize space?" → Not for this approach, stack needed for O(n) time

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Daily Temperatures** | Medium | **Monotonic Stack (Next Greater)** | **This problem** |
| Next Greater Element I | Easy | Monotonic Stack | Find next greater in array |
| Next Greater Element II | Medium | Monotonic Stack + Circular | Circular array |
| Largest Rectangle in Histogram | Hard | Monotonic Stack | Next/previous smaller |
| Trapping Rain Water | Hard | Monotonic Stack | Water volume calculation |
| Online Stock Span | Medium | Monotonic Stack | Running span calculation |

**Pattern Progression**:
1. **Next greater (linear)** (this problem) — Basic monotonic stack
2. **Next greater (circular)** — Handle wrap-around
3. **Next smaller** — Reverse comparison
4. **Two-sided** — Both next greater and previous greater

---

## Final Pattern Label

✅ **Monotonic Stack (Next Greater Element) — Decreasing Order**

**Remember:** Use monotonic stack to find next greater element in O(n) time. Store indices (not temperatures) in stack to compute distances. Maintain decreasing temperature order from bottom to top. When warmer day found, pop and resolve all colder days (cascading). Use strictly less than (<) for comparison since equal is not warmer. Always check isEmpty() before peek(). Each index pushed and popped at most once, giving amortized O(n) time with O(n) space!
