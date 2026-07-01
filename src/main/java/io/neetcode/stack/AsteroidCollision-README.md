# Asteroid Collision

## Problem Description

**Difficulty**: Medium

You are given an array `asteroids` of integers representing asteroids in a row. The indices of the asteroid in the array represent their relative position in space.

For each asteroid:
- The **absolute value** represents its **size**
- The **sign** represents its **direction**:
  - **Positive** (+) = moving **right** (→)
  - **Negative** (-) = moving **left** (←)
- Each asteroid moves at the **same speed**

Find out the state of the asteroids after all collisions.

**Collision Rules:**
- If two asteroids meet, the **smaller one explodes**
- If both are the **same size**, **both explode**
- Two asteroids moving in the **same direction** will **never meet**

## Examples

### Example 1:
```
Input: asteroids = [5, 10, -5]
Output: [5, 10]

Explanation:
  5 →     (moving right)
  10 →    (moving right)
  -5 ←    (moving left)
  
  Collision: 10 → meets ← -5
  Size: |10| = 10 vs |-5| = 5
  Result: 10 > 5, so -5 explodes
  
  Final: [5, 10]
```

### Example 2:
```
Input: asteroids = [8, -8]
Output: []

Explanation:
  8 →     (moving right)
  -8 ←    (moving left)
  
  Collision: 8 → meets ← -8
  Size: |8| = 8 vs |-8| = 8
  Result: Same size, both explode
  
  Final: []
```

### Example 3:
```
Input: asteroids = [10, 2, -5]
Output: [10]

Explanation:
  10 →    (moving right)
  2 →     (moving right)
  -5 ←    (moving left)
  
  Step 1: 2 → meets ← -5
    Size: 2 < 5, so 2 explodes
    Remaining: [10, -5]
  
  Step 2: 10 → meets ← -5 (cascading collision!)
    Size: 10 > 5, so -5 explodes
    Remaining: [10]
  
  Final: [10]
```

### Example 4:
```
Input: asteroids = [-2, -1, 1, 2]
Output: [-2, -1, 1, 2]

Explanation:
  -2 ←    (moving left)
  -1 ←    (moving left)
  1 →     (moving right)
  2 →     (moving right)
  
  No collisions! Left-moving never meet right-moving when ordered this way.
  
  Final: [-2, -1, 1, 2]
```

### Example 5:
```
Input: asteroids = [2, 4, -4, -1]
Output: [2]

Explanation:
  2 →     (moving right)
  4 →     (moving right)
  -4 ←    (moving left)
  -1 ←    (moving left)
  
  Step 1: 4 → meets ← -4
    Size: 4 = 4, both explode
    Remaining: [2, -1]
  
  Step 2: 2 → meets ← -1
    Size: 2 > 1, so -1 explodes
    Remaining: [2]
  
  Final: [2]
```

### Example 6:
```
Input: asteroids = [5, 5]
Output: [5, 5]

Explanation:
  5 →     (moving right)
  5 →     (moving right)
  
  Both moving same direction, no collision.
  
  Final: [5, 5]
```

### Example 7:
```
Input: asteroids = [7, -3, 9]
Output: [7, 9]

Explanation:
  7 →     (moving right)
  -3 ←    (moving left)
  9 →     (moving right)
  
  Collision: 7 → meets ← -3
  Size: 7 > 3, so -3 explodes
  
  Remaining: [7, 9] (no collision between 7 and 9, same direction)
  
  Final: [7, 9]
```

## Constraints
- 2 <= asteroids.length <= 10,000
- -1000 <= asteroids[i] <= 1000
- asteroids[i] != 0 (no zero-sized asteroids)

**Recommended Complexity**: O(n) time and O(n) space, where n is the number of asteroids

---

## Pattern Recognition

**Primary Pattern**: **Stack (Collision Simulation with Cascading Effects)**

**Why This Pattern?**
- Need to track "surviving" asteroids
- New asteroid can collide with multiple previous ones
- LIFO property perfect for checking most recent collision
- Cascading collisions require backtracking

**Key Insight**: When Does Collision Happen?
```
Collision condition: RIGHT-moving meets LEFT-moving
  Position: → (on stack) meets ← (incoming)
  Code: stack.top() > 0 && current < 0

Example showing collision:
  [5, 10]  ← stack (both moving right →)
  -7       ← incoming (moving left ←)
  
  10 → meets ← -7: COLLISION! ✓

All other combinations:
  → meets →: No collision (same direction)
  ← meets ←: No collision (same direction)
  ← meets →: No collision (moving apart)
  
Only → meets ← causes collision!
```

**The Stack Strategy**:
```
Stack represents "surviving asteroids so far"

Processing each asteroid:
  1. If moving left (negative):
     - Check for collisions with right-moving asteroids on stack
     - While collision exists:
       a. Compare sizes
       b. Smaller explodes (or both if equal)
       c. Continue checking (cascading!)
     - If survives all collisions, add to stack
  
  2. If moving right (positive):
     - No immediate collision (can only collide with future left-moving)
     - Add to stack
  
  3. If moving left (negative) and stack empty or stack.top() < 0:
     - No collision possible (no right-moving asteroid to collide with)
     - Add to stack

Final stack = surviving asteroids
```

**Critical Detail**: Cascading Collisions
```
Example: [3, 5, 2, -10]

Process -10:
  Stack before: [3, 5, 2] (all moving right →)
  Incoming: -10 ← (moving left)
  
  Collision 1: 2 → vs ← -10
    |2| < |-10| → 2 explodes
    Stack: [3, 5]
    Continue checking!
  
  Collision 2: 5 → vs ← -10
    |5| < |-10| → 5 explodes
    Stack: [3]
    Continue checking!
  
  Collision 3: 3 → vs ← -10
    |3| < |-10| → 3 explodes
    Stack: []
    Continue checking!
  
  No more right-moving asteroids
  -10 survives, add to stack
  
  Final: [-10]

MUST use while loop, not just if!
```

**Why Stack Is Perfect**:
```
Stack properties:
  - LIFO: Check most recent asteroid first
  - Easy to remove destroyed asteroids (pop)
  - Natural for cascading collisions
  - O(1) push/pop operations

Example showing stack advantage:
  Input: [5, 10, -5]
  
  Step 1: Push 5
    Stack: [5]
  
  Step 2: Push 10
    Stack: [5, 10]
  
  Step 3: Process -5
    Top = 10, 10 > 0 and -5 < 0 → collision!
    |10| > |-5| → -5 explodes
    Stack: [5, 10] (unchanged)
  
  Result: [5, 10] ✓

No need to track indices or modify input array!
```

**The While Loop Insight**:
```
Why while, not if?

One left-moving asteroid can destroy MULTIPLE right-moving ones!

Example: [1, 2, 3, -10]
  -10 destroys 3, then 2, then 1 → all explode!
  
Must keep checking until:
  1. Left-moving asteroid explodes, OR
  2. Both explode (equal size), OR
  3. No more right-moving asteroids on stack

while (stack not empty AND collision condition AND left-moving survives):
    resolve collision
```

**Related Patterns**:
1. **Stack Simulation** — Process events with backtracking
2. **Monotonic Stack** — Related but different
3. **State Machine** — Track asteroid states
4. **Collision Detection** — Physics simulation

---

## Algorithm & Approach

### Core Insight

**Why Naive Approach Fails:**
```
Naive: Modify array in place, scan for collisions repeatedly
  While collisions exist:
    Find collision
    Remove destroyed asteroids
    Repeat
  
Time: O(n²) — multiple passes, array modifications expensive
Requirement: O(n)
Too slow for n = 10,000!

Stack Approach:
  Single pass through array
  Stack operations O(1)
  Each asteroid pushed/popped at most once
  Total: O(n) ✓
```

**The Optimal Strategy**:
```
Key observations:
  1. Process asteroids left to right (order matters!)
  2. Stack tracks survivors moving right
  3. Left-moving asteroid triggers collision checks
  4. Cascading collisions handled by while loop
  5. Each asteroid processed once (amortized)
  
All operations O(1) amortized:
  Push: O(1)
  Pop: O(1) per asteroid (each popped at most once)
  Comparison: O(1)
  
Total: O(n) for n asteroids
```

### Step-by-Step Algorithm

---

#### **Approach 1: Stack with While Loop - STANDARD**

**Core Idea**:
- Use stack to track surviving asteroids
- When left-moving asteroid arrives, resolve collisions with while loop
- Handle cascading collisions until stable

**Algorithm**
```
asteroidCollision(asteroids):
    stack = new Stack()
    
    for asteroid in asteroids:
        alive = true  // Track if current asteroid survives
        
        // Handle collision: current is left-moving (negative)
        while (stack not empty AND stack.top() > 0 AND asteroid < 0):
            // Collision between stack.top() (right) and asteroid (left)
            
            if abs(stack.top()) < abs(asteroid):
                // Right-moving asteroid smaller, it explodes
                stack.pop()
                // Continue checking (cascading collision)
            
            else if abs(stack.top()) == abs(asteroid):
                // Same size, both explode
                stack.pop()
                alive = false
                break  // Current asteroid destroyed
            
            else:
                // Right-moving asteroid larger, current explodes
                alive = false
                break
        
        // Add current asteroid if it survived
        if alive:
            stack.push(asteroid)
    
    return stack as array
```

**Code Implementation**
```java
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        
        for (int asteroid : asteroids) {
            boolean alive = true;
            
            // Check for collision: current is left-moving, stack top is right-moving
            while (!stack.isEmpty() && stack.peek() > 0 && asteroid < 0) {
                int top = stack.peek();
                
                // Compare sizes (use absolute values)
                if (Math.abs(top) < Math.abs(asteroid)) {
                    // Top asteroid smaller, it explodes
                    stack.pop();
                    // Continue loop to check next collision
                } else if (Math.abs(top) == Math.abs(asteroid)) {
                    // Same size, both explode
                    stack.pop();
                    alive = false;
                    break;  // Current asteroid destroyed, stop checking
                } else {
                    // Top asteroid larger, current explodes
                    alive = false;
                    break;  // Current asteroid destroyed, stop checking
                }
            }
            
            // Add current asteroid to stack if it survived
            if (alive) {
                stack.push(asteroid);
            }
        }
        
        // Convert stack to array
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        
        return result;
    }
}
```

**Example Walkthrough**

Input: `asteroids = [10, 2, -5]`

| Asteroid | Collision? | Action | Stack After | alive |
|----------|-----------|--------|-------------|-------|
| 10 | No | Push 10 | [10] | true |
| 2 | No | Push 2 | [10, 2] | true |
| -5 | Yes | Check collisions | - | - |
| | Collision 1: 2 vs -5 | \|2\| < \|-5\|, pop 2 | [10] | true (continue) |
| | Collision 2: 10 vs -5 | \|10\| > \|-5\|, -5 explodes | [10] | false (stop) |
| | | Don't push -5 | [10] | - |
| **Return** | - | Convert to array | - | [10] |

**Complexity Analysis**
- **Time**: O(n) — Each asteroid pushed/popped at most once (amortized)
- **Space**: O(n) — Stack can hold up to n asteroids

---

#### **Approach 2: Stack Without Boolean Flag - CLEANER**

**Core Idea**: Use break/continue more explicitly, avoid alive flag.

**Code Implementation**
```java
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        
        for (int asteroid : asteroids) {
            // Process collision for left-moving asteroid
            if (asteroid < 0) {
                // Resolve collisions with right-moving asteroids
                while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < -asteroid) {
                    stack.pop();  // Right-moving asteroid explodes
                }
                
                // Check if current asteroid survived
                if (!stack.isEmpty() && stack.peek() == -asteroid) {
                    // Same size, both explode
                    stack.pop();
                } else if (stack.isEmpty() || stack.peek() < 0) {
                    // No collision, add to stack
                    stack.push(asteroid);
                }
                // Else: current asteroid exploded (stack.peek() > -asteroid)
            } else {
                // Right-moving asteroid, no immediate collision
                stack.push(asteroid);
            }
        }
        
        // Convert stack to array
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        
        return result;
    }
}
```

**Key Difference**: 
- No boolean flag, clearer logic flow
- While loop only for smaller asteroids
- Check equal and survived cases after loop

**Complexity Analysis**
- **Time**: O(n) — Amortized, each asteroid processed once
- **Space**: O(n) — Stack storage

---

#### **Approach 3: In-Place with Two Pointers - SPACE OPTIMIZED**

**Core Idea**: Use input array as stack (write pointer tracks stack top).

**Code Implementation**
```java
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        int writeIdx = 0;  // Stack pointer (top of stack)
        
        for (int asteroid : asteroids) {
            boolean alive = true;
            
            // Check collision: asteroid moving left, stack top moving right
            while (alive && writeIdx > 0 && asteroids[writeIdx - 1] > 0 && asteroid < 0) {
                int top = asteroids[writeIdx - 1];
                
                if (Math.abs(top) < Math.abs(asteroid)) {
                    // Top explodes, pop it
                    writeIdx--;
                    // Continue checking
                } else if (Math.abs(top) == Math.abs(asteroid)) {
                    // Both explode
                    writeIdx--;
                    alive = false;
                } else {
                    // Current explodes
                    alive = false;
                }
            }
            
            // Add current asteroid if survived
            if (alive) {
                asteroids[writeIdx++] = asteroid;
            }
        }
        
        // Copy result to new array of correct size
        return Arrays.copyOf(asteroids, writeIdx);
    }
}
```

**Key Difference**: 
- Uses input array as stack
- writeIdx tracks stack size
- Saves extra O(n) space for stack

**Complexity Analysis**
- **Time**: O(n) — Same logic, amortized
- **Space**: O(1) — In-place (excluding output array)

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Code Clarity | Recommended |
|----------|------|-------|--------------|-------------|
| **Stack (Boolean Flag)** | **O(n)** | **O(n)** | **Excellent ✅** | **Yes ✅** |
| Stack (No Flag) | O(n) | O(n) | Very Good | Alternative |
| In-Place Two Pointers | O(n) | O(1) | Good | Space critical |

**Winner**: **Stack with Boolean Flag** — clearest logic, easy to understand!

### Why Stack Works for Collisions?

```
Stack represents "active asteroids"

Key insight: Only need to check MOST RECENT right-moving asteroid

Example: [5, 10, -3]
  
  Stack: [5, 10]
  Incoming: -3
  
  -3 collides with 10 (top), not 5
  If -3 destroys 10, then check 5 (cascading)
  
  LIFO property: Check from right to left naturally

Why not array scan?
  [5, 10, ?, ?] with -3
  Would need to find rightmost right-moving asteroid
  Then check collisions left to right
  Complex index management!
  
Stack: Just peek/pop from top — simple!
```

### The While Loop Necessity

```
Critical: One asteroid can destroy MULTIPLE others

Example: [1, 2, 3, -10]
  
  Process -10:
    Collision 1: 3 vs -10 → 3 explodes
    Collision 2: 2 vs -10 → 2 explodes
    Collision 3: 1 vs -10 → 1 explodes
    -10 survives
  
MUST use while loop:
  while (collision exists AND current survives):
      resolve collision
  
If we used if:
  Only check once, -10 would destroy only 3
  Stack: [1, 2, -10] ❌ Wrong!
  
While loop handles cascading correctly!
```

### Collision Detection Logic

```
When does collision happen?
  stack.top() > 0  AND  asteroid < 0
  (right-moving)       (left-moving)
  
All other cases:
  1. stack.top() > 0 AND asteroid > 0 → both right, no collision
  2. stack.top() < 0 AND asteroid < 0 → both left, no collision
  3. stack.top() < 0 AND asteroid > 0 → moving apart, no collision
  
Only case 4 causes collision!

Visual:
  Case 1:  → →  (parallel, never meet)
  Case 2:  ← ←  (parallel, never meet)
  Case 3:  ← →  (diverging, moving apart)
  Case 4:  → ←  (converging, COLLISION!)
```

### Size Comparison Detail

```
Use absolute values for size comparison!

Example: 10 vs -5
  Direct: 10 > -5 ✓ (but wrong interpretation!)
  Absolute: |10| = 10 vs |-5| = 5
  Correct: 10 > 5, so 10 wins
  
Three outcomes:
  1. |top| < |asteroid| → top explodes, continue checking
  2. |top| = |asteroid| → both explode, stop checking
  3. |top| > |asteroid| → asteroid explodes, stop checking

Important: In cases 2 and 3, current asteroid destroyed, break!
```

### Amortized O(n) Analysis

```
Question: While loop inside for loop = O(n²)?
Answer: No! Amortized O(n)

Proof:
  Each asteroid pushed at most once: O(n) pushes
  Each asteroid popped at most once: O(n) pops
  Total operations: O(n) + O(n) = O(n)
  
Example: [1, 2, 3, 4, 5, -10]
  Push 1, 2, 3, 4, 5: 5 pushes
  Process -10:
    Pop 5: 1 pop
    Pop 4: 1 pop
    Pop 3: 1 pop
    Pop 2: 1 pop
    Pop 1: 1 pop
  Push -10: 1 push
  
  Total: 6 pushes + 5 pops = 11 operations for 6 asteroids
  = O(n) ✓

While loop iterations limited by stack size!
```

---

## Critical Edge Cases & Gotchas

### 1. **All Right-Moving**
```java
Input: [5, 10, 15]
All positive (moving right →)
No collisions
Output: [5, 10, 15]
```

### 2. **All Left-Moving**
```java
Input: [-15, -10, -5]
All negative (moving left ←)
No collisions
Output: [-15, -10, -5]
```

### 3. **Left Then Right (No Collision)**
```java
Input: [-5, -10, 5, 10]
← ← → →
Moving apart, no collision
Output: [-5, -10, 5, 10]
```

### 4. **Cascading Collision**
```java
Input: [1, 2, 3, -10]
-10 destroys all three
Stack evolution:
  [1] → [1,2] → [1,2,3] → [1,2] → [1] → [] → [-10]
Output: [-10]
```

### 5. **Equal Size Collision**
```java
Input: [8, -8]
Same size, both explode
Output: []
```

### 6. **Multiple Left-Moving**
```java
Input: [10, -5, -3]
10 destroys -5
Stack: [10]
-3 then collides with 10
10 > 3, so -3 explodes
Output: [10]
```

### 7. **Alternating Directions**
```java
Input: [5, -5, 10, -10]
5 vs -5: both explode → []
10 vs -10: both explode → []
Output: []
```

### 8. **Left-Moving Survives**
```java
Input: [5, -10]
|5| < |-10|, so 5 explodes
-10 survives
Output: [-10]
```

### 9. **Two Element Array**
```java
Input: [1, -1]
Equal size, both explode
Output: []
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Using If Instead of While for Collisions**
```java
// WRONG - only checks one collision
if (!stack.isEmpty() && stack.peek() > 0 && asteroid < 0) {
    // ... handle collision
}
```

**Why wrong**: One asteroid can destroy multiple others!

**Dry run failure for [1, 2, -5]:**
```
Stack after pushes: [1, 2]
Process -5:
  if condition true, check collision with 2
  |2| < |-5|, pop 2
  Stack: [1]
  
  BUT: -5 should also collide with 1!
  With if: only one collision checked
  Result: [1, -5] ❌ (impossible! → meets ←)
  
Expected: pop 1 too, result [-5] ✓
```

**Fix**: Use while loop
```java
while (!stack.isEmpty() && stack.peek() > 0 && asteroid < 0) {
    // ... handle collision
}
```

### ❌ **MISTAKE 2: Wrong Collision Condition**
```java
// WRONG - checks asteroid > 0 instead of < 0
while (!stack.isEmpty() && stack.peek() > 0 && asteroid > 0) {
    // ...
}
```

**Why wrong**: Collision happens when right meets left, not right meets right!

**Dry run failure for [5, -3]:**
```
Process -3:
  Condition: stack.peek() > 0 (5 > 0 ✓) AND asteroid > 0 (-3 > 0 ❌)
  Condition false, no collision detected
  Push -3
  Result: [5, -3] ❌ (impossible state!)
  
Expected: 5 vs -3, 5 wins, result [5] ✓
```

**Fix**: Check asteroid < 0
```java
while (!stack.isEmpty() && stack.peek() > 0 && asteroid < 0)
```

### ❌ **MISTAKE 3: Direct Value Comparison Instead of Absolute**
```java
// WRONG - compares values directly
if (stack.peek() < asteroid) {  // WRONG!
    stack.pop();
}
```

**Why wrong**: Sign affects comparison, need sizes only!

**Dry run failure for [5, -10]:**
```
Process -10:
  Collision detected ✓
  Compare: 5 < -10? No (5 > -10 numerically)
  Don't pop 5
  -10 explodes? No
  Stuck!
  
Expected: |5| < |-10|, so 5 should explode
```

**Fix**: Use Math.abs()
```java
if (Math.abs(stack.peek()) < Math.abs(asteroid))
```

### ❌ **MISTAKE 4: Forgetting to Add Survivor**
```java
// WRONG - doesn't push surviving asteroid
while (!stack.isEmpty() && stack.peek() > 0 && asteroid < 0) {
    if (Math.abs(stack.peek()) < Math.abs(asteroid)) {
        stack.pop();
    } else {
        alive = false;
        break;
    }
}
// WRONG! Forgot: if (alive) stack.push(asteroid);
```

**Why wrong**: Surviving asteroid disappears!

**Dry run failure for [5, -10]:**
```
Process -10:
  Collision with 5
  |5| < |-10|, pop 5
  Stack: []
  Loop exits (empty stack)
  
  -10 survived but not added!
  Result: [] ❌
  
Expected: [-10] ✓
```

**Fix**: Add survivor after loop
```java
if (alive) {
    stack.push(asteroid);
}
```

### ❌ **MISTAKE 5: Not Breaking After Equal Size**
```java
// WRONG - continues after both explode
if (Math.abs(stack.peek()) == Math.abs(asteroid)) {
    stack.pop();
    alive = false;
    // WRONG! Forgot to break
}
```

**Why wrong**: Loop continues checking destroyed asteroid!

**Dry run failure for [5, -5, 3]:**
```
Process -5:
  Collision with 5
  |5| = |-5|, both explode
  Pop 5, set alive = false
  
  No break! Loop continues:
  Stack empty? Yes, loop exits
  
  Fortunately works here, but wrong if more asteroids in stack!

Example: [10, 5, -5]
  After 10, 5 pushed: [10, 5]
  Process -5:
    Collision with 5: equal, pop 5, alive = false
    No break! Continue loop
    Next iteration: collision with 10!
    But -5 already exploded!
    Would check |10| vs |-5| incorrectly
```

**Fix**: Break after both explode
```java
if (Math.abs(stack.peek()) == Math.abs(asteroid)) {
    stack.pop();
    alive = false;
    break;  // Current destroyed, stop checking
}
```

### ❌ **MISTAKE 6: Wrong Loop Condition Order**
```java
// WRONG - doesn't check empty first
while (stack.peek() > 0 && !stack.isEmpty() && asteroid < 0) {
    // WRONG! peek() before isEmpty() check
}
```

**Why wrong**: peek() on empty stack throws exception!

**Dry run failure for [-5]:**
```
Process -5:
  Condition: stack.peek() > 0
  Stack empty! peek() throws EmptyStackException ❌
```

**Fix**: Check empty first
```java
while (!stack.isEmpty() && stack.peek() > 0 && asteroid < 0)
```

### ❌ **MISTAKE 7: Not Handling Same Direction**
```java
// WRONG - tries to handle collision for right-moving
if (asteroid > 0) {
    while (!stack.isEmpty() && stack.peek() < 0) {
        // WRONG! Right-moving can't collide with left-moving already past
    }
}
```

**Why wrong**: Right-moving asteroid can't catch left-moving one that's already past!

**Fix**: Only right-moving asteroids go directly to stack
```java
if (asteroid > 0) {
    stack.push(asteroid);  // No collision check needed
}
```

### ❌ **MISTAKE 8: Converting Stack to Array Incorrectly**
```java
// WRONG - converts in wrong order
int[] result = new int[stack.size()];
for (int i = 0; i < result.length; i++) {
    result[i] = stack.pop();  // WRONG! Pops in reverse
}
```

**Why wrong**: Stack is LIFO, need to reverse!

**Dry run failure:**
```
Stack: [1, 2, 3] (3 on top)
Loop i=0: result[0] = pop() → 3
Loop i=1: result[1] = pop() → 2
Loop i=2: result[2] = pop() → 1
Result: [3, 2, 1] ❌

Expected: [1, 2, 3] ✓
```

**Fix**: Pop in reverse order
```java
for (int i = result.length - 1; i >= 0; i--) {
    result[i] = stack.pop();
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **Iterate asteroids** | n | - | - |
| **Push to stack** | ≤ n | O(1) | O(n) |
| **Pop from stack** | ≤ n | O(1) | O(n) |
| **Comparisons** | ≤ n | O(1) | O(n) |
| **Convert to array** | ≤ n | O(1) | O(n) |
| **Total** | - | - | **O(n)** |

**Amortized Analysis**:
```
While loop inside for loop looks like O(n²), but it's O(n)!

Proof:
  Each asteroid pushed at most once: n pushes
  Each asteroid popped at most once: n pops
  Total push/pop: 2n operations
  
For loop: n iterations
While loop: Total iterations across all asteroids ≤ n
  (because each pop removes one asteroid forever)
  
Total: O(n) + O(n) = O(n) ✓

Example: [1, 2, 3, 4, 5, -100]
  Pushes: 1, 2, 3, 4, 5 → 5 operations
  Process -100:
    Pop 5, pop 4, pop 3, pop 2, pop 1 → 5 operations
  Push -100 → 1 operation
  
  Total: 11 operations for 6 elements
  Average: ~2 operations per element = O(n)
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Stack | O(n) | Worst case: all asteroids survive |
| Variables | O(1) | alive, asteroid (constant) |
| Output array | O(n) | Required for result |
| **Total** | **O(n)** | Stack + output |

**Space analysis**:
```
Worst case: No collisions
  Example: [1, 2, 3, 4, 5]
  All pushed to stack: O(n) space
  
Best case: All explode
  Example: [5, -5]
  Empty stack, but O(n) space for output array
  
Average case: Some survive
  Stack size ≤ n
  
Space complexity: O(n) for stack
```

**Can we optimize space?**
```
In-place approach: O(1) extra space
  Use input array as stack
  writeIdx tracks stack top
  Saves extra O(n) for stack object
  
But still need O(n) for output (problem requirement)
  
Practical savings: ~n integers of memory
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `asteroids = [10, 2, -5]`

**Expected Output:** `[10]`

---

**Initial State:**
```
Asteroids: [10, 2, -5]
Stack: []
Index: 0
```

---

**Step 1: Process 10**
```
Asteroid: 10 (moving right →)
Collision check: 10 > 0, no left-moving collision
Action: Push 10

Stack visualization:
   ┌────┐
   │ 10 │ ← top (→)
   └────┘

Stack: [10]
```

---

**Step 2: Process 2**
```
Asteroid: 2 (moving right →)
Collision check: 2 > 0, no left-moving collision
Action: Push 2

Stack visualization:
   ┌────┐
   │ 2  │ ← top (→)
   ├────┤
   │ 10 │ (→)
   └────┘

Stack: [10, 2]
```

---

**Step 3: Process -5 (Collision!)**
```
Asteroid: -5 (moving left ←)
Collision check: stack.peek() = 2 > 0 AND -5 < 0 ✓
Action: Enter while loop

Visual collision:
   ┌────┐
   │ 2→ │ ← vs → -5←
   ├────┤
   │10→ │
   └────┘

Collision 1: 2 vs -5
  |2| = 2
  |-5| = 5
  2 < 5, so 2 explodes
  Pop 2

Stack after pop:
   ┌────┐
   │ 10 │ ← top (→)
   └────┘

Continue while loop (alive still true)

Collision 2: 10 vs -5
  |10| = 10
  |-5| = 5
  10 > 5, so -5 explodes
  Set alive = false
  Break

Stack unchanged:
   ┌────┐
   │ 10 │ ← top
   └────┘

Don't push -5 (alive = false)

Stack: [10]
```

---

**Final: Convert to Array**
```
Stack: [10]
Result array: [10]

Return: [10] ✓
```

---

### Cascading Collision Example

**Input:** `asteroids = [5, 10, 15, -20]`

---

**After pushes:**
```
Stack:
   ┌────┐
   │ 15 │ ← top
   ├────┤
   │ 10 │
   ├────┤
   │ 5  │
   └────┘
```

**Process -20:**
```
Iteration 1:
  Compare: 15 vs -20
  |15| < |-20|, pop 15
  
  Stack:
   ┌────┐
   │ 10 │ ← top
   ├────┤
   │ 5  │
   └────┘
  
  Continue (alive = true)

Iteration 2:
  Compare: 10 vs -20
  |10| < |-20|, pop 10
  
  Stack:
   ┌────┐
   │ 5  │ ← top
   └────┘
  
  Continue (alive = true)

Iteration 3:
  Compare: 5 vs -20
  |5| < |-20|, pop 5
  
  Stack:
   (empty)
  
  Continue (alive = true)

Loop exits (stack empty)

alive = true, push -20

Final stack:
   ┌────┐
   │-20 │ ← top
   └────┘

Result: [-20]
```

---

### Equal Size Collision Example

**Input:** `asteroids = [8, -8]`

---

**Step 1: Push 8**
```
Stack:
   ┌───┐
   │ 8 │ ← top (→)
   └───┘
```

**Step 2: Process -8**
```
Collision: 8 vs -8
  |8| = 8
  |-8| = 8
  Equal size! Both explode
  
Action:
  Pop 8
  Set alive = false
  Break

Stack:
   (empty)

Don't push -8

Result: []
```

---

### No Collision Example

**Input:** `asteroids = [-5, -3, 10, 5]`

---

**Visual:**
```
-5 ←  -3 ←  10 →  5 →

No collisions:
  -5 and -3: same direction ←
  -3 and 10: moving apart (← and →)
  10 and 5: same direction →
```

**Stack evolution:**
```
Push -5: [-5]
Push -3: [-5, -3]
Push 10: [-5, -3, 10] (10 > 0, no collision with negatives on left)
Push 5:  [-5, -3, 10, 5]

Result: [-5, -3, 10, 5]
```

---

### State Machine Diagram

```
For each asteroid:

         asteroid < 0
         (left-moving)
              ↓
      ┌──────────────┐
      │ Check Stack  │
      └──────┬───────┘
             ↓
   ┌─────────────────────┐
   │ Stack empty OR      │
   │ stack.top() < 0?    │
   └──┬──────────────┬───┘
      │Yes           │No
      ↓              ↓
   Push        ┌──────────┐
   Done        │Collision!│
               └────┬─────┘
                    ↓
         ┌──────────────────┐
         │ Compare sizes    │
         └──────┬───────────┘
                ↓
   ┌────────────┼────────────┐
   │            │            │
   │<           │=           │>
   ↓            ↓            ↓
Pop top    Pop both      Current
Continue   Stop          explodes
                         Stop

asteroid > 0
(right-moving)
    ↓
  Push
  Done
```

---

## Comparison of Approaches

| Approach | Space | Code Lines | Clarity | Edge Cases | Recommended |
|----------|-------|------------|---------|------------|-------------|
| **Stack (Flag)** | **O(n)** | **~35** | **Excellent ✅** | **Easy ✅** | **Yes ✅** |
| Stack (No Flag) | O(n) | ~30 | Very Good | Medium | Alternative |
| In-Place | O(1) | ~35 | Good | Medium | Space critical |

**All have same time complexity: O(n)**

**Recommendation**: Use **Stack with Boolean Flag** — clearest logic, easiest to understand and debug!

---

## Key Takeaways

1. **Collision only when → meets ←** — right-moving meets left-moving
2. **Use while loop, not if** — cascading collisions possible
3. **Boolean flag tracks survival** — clear state management
4. **Compare absolute values** — sizes, not signed values
5. **Break after current explodes** — don't continue checking destroyed asteroid
6. **Check stack.isEmpty() first** — avoid peek() on empty stack
7. **Convert stack carefully** — reverse order for final array
8. **Amortized O(n) time** — each asteroid pushed/popped once

---

## Interview Tips

**What to say in an interview:**

> "This problem simulates asteroid collisions where positive values move right and negative move left. The key insight is that collisions only occur when a right-moving asteroid (positive) meets a left-moving asteroid (negative). I'll use a stack to track surviving asteroids. For each asteroid, if it's moving right, I push it directly. If it's moving left, I check for collisions with right-moving asteroids on the stack using a while loop—this is critical because one left-moving asteroid can destroy multiple right-moving ones in a cascade. I compare absolute values to determine sizes, with three outcomes: if the stack asteroid is smaller, it explodes and I continue checking; if they're equal, both explode; if the current asteroid is smaller, it explodes. Finally, I convert the stack to an array. This runs in O(n) time because each asteroid is pushed and popped at most once, giving amortized O(n) complexity."

**Key points to mention:**
1. **Collision condition** — Only → meets ← (positive on stack, negative incoming)
2. **Stack for survivors** — LIFO perfect for checking most recent
3. **While loop for cascading** — One asteroid can destroy multiple
4. **Absolute value comparison** — Compare sizes, not signed values
5. **Three outcomes** — Smaller, equal, larger
6. **Amortized O(n)** — Each pushed/popped once

**If asked about implementation details:**
> "I use a boolean flag 'alive' to track whether the current asteroid survives. The while loop continues as long as there's a collision possibility—stack not empty, top is positive, and current is negative. I always check isEmpty() before peek() to avoid exceptions. When converting the stack to array, I pop in reverse order to maintain the correct left-to-right sequence."

**Common Follow-ups:**
- "Why while loop, not if?" → One asteroid can destroy multiple (cascading)
- "What's the time complexity?" → O(n) amortized, each pushed/popped once
- "Can you optimize space?" → Yes, in-place with two pointers for O(1)
- "What if asteroids have different speeds?" → More complex, need time simulation

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Asteroid Collision** | Medium | **Stack (Collision)** | **This problem** |
| Daily Temperatures | Medium | Stack (Monotonic) | Find next greater |
| Remove K Digits | Medium | Stack (Greedy) | Build optimal result |
| Car Fleet | Medium | Stack (Events) | Merging based on arrival |
| Valid Parentheses | Easy | Stack (Matching) | Bracket matching |

**Pattern Progression**:
1. **Collision simulation** (this problem) — Resolve conflicts with cascading
2. **Monotonic stack** (Daily Temps) — Maintain order property
3. **Greedy stack** (Remove K) — Build optimal by removing elements
4. **Event processing** (Car Fleet) — Merge/collapse based on conditions

---

## Final Pattern Label

✅ **Stack (Collision Simulation with Cascading Effects)**

**Remember:** Collision occurs only when right-moving (positive) meets left-moving (negative): `stack.peek() > 0 && asteroid < 0`. Use stack to track survivors, checking most recent first (LIFO). MUST use while loop for cascading collisions—one asteroid can destroy multiple. Compare absolute values for sizes. Boolean flag tracks survival. Break when current asteroid explodes (equal or larger stack top). Each asteroid pushed/popped at most once, giving amortized O(n) time with O(n) space!
