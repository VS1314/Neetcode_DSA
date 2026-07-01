# Car Fleet

## Problem Description

**Difficulty**: Medium

There are `n` cars traveling to the same destination on a **one-lane highway**.

You are given two arrays of integers `position` and `speed`, both of length `n`:
- `position[i]` is the position of the `i`th car (in miles)
- `speed[i]` is the speed of the `i`th car (in miles per hour)

The **destination** is at position `target` miles.

**Rules:**
- A car **cannot pass** another car ahead of it
- It can only **catch up** to another car and then **drive at the same speed** as the car ahead
- A **car fleet** is a non-empty set of cars driving at the **same position and same speed**
- A **single car** is also considered a car fleet
- If a car catches up to a car fleet **the moment** the fleet reaches the destination, the car is considered part of the fleet

Return the number of **different car fleets** that will arrive at the destination.

## Examples

### Example 1:
```
Input: target = 10, position = [1, 4], speed = [3, 2]
Output: 1

Explanation:
  Car 0: position=1, speed=3 → time to target = (10-1)/3 = 3 hours
  Car 1: position=4, speed=2 → time to target = (10-4)/2 = 3 hours
  
  Both cars take 3 hours to reach target
  Car 0 (faster, behind) catches up to Car 1 (slower, ahead) exactly at target
  They form 1 fleet
```

### Example 2:
```
Input: target = 10, position = [4, 1, 0, 7], speed = [2, 2, 1, 1]
Output: 3

Explanation:
  Car at position 7, speed 1: time = (10-7)/1 = 3 hours
  Car at position 4, speed 2: time = (10-4)/2 = 3 hours
  Car at position 1, speed 2: time = (10-1)/2 = 4.5 hours
  Car at position 0, speed 1: time = (10-0)/1 = 10 hours
  
  Sort by position descending: [7, 4, 1, 0]
  Times: [3, 3, 4.5, 10]
  
  Car at 7 (time 3): forms fleet 1
  Car at 4 (time 3 <= 3): joins fleet 1 (catches up at target)
  Car at 1 (time 4.5 > 3): forms fleet 2 (slower, won't catch up)
  Car at 0 (time 10 > 4.5): forms fleet 3 (slowest, won't catch up)
  
  Total: 3 fleets
```

### Example 3:
```
Input: target = 12, position = [10, 8, 0, 5, 3], speed = [2, 4, 1, 1, 3]
Output: 3

Explanation:
  Sorted by position: [(10,2), (8,4), (5,1), (3,3), (0,1)]
  Times: [1, 1, 7, 3, 12]
  
  Position 10, time 1: fleet 1
  Position 8, time 1 <= 1: joins fleet 1
  Position 5, time 7 > 1: fleet 2
  Position 3, time 3 <= 7: joins fleet 2
  Position 0, time 12 > 7: fleet 3
  
  Total: 3 fleets
```

### Example 4:
```
Input: target = 100, position = [0, 2, 4], speed = [4, 2, 1]
Output: 1

Explanation:
  Times: [(0,25), (2,49), (4,96)]
  Sorted: [(4,96), (2,49), (0,25)]
  
  Position 4, time 96: fleet 1
  Position 2, time 49 <= 96: joins fleet 1 (slower, ahead)
  Position 0, time 25 <= 49: joins fleet 1 (fastest but catches slowest)
  
  All form 1 fleet
```

### Example 5:
```
Input: target = 10, position = [3], speed = [3]
Output: 1

Explanation:
  Single car forms 1 fleet
```

### Example 6:
```
Input: target = 10, position = [6, 8], speed = [3, 2]
Output: 2

Explanation:
  Car at 8, speed 2: time = 1
  Car at 6, speed 3: time = 1.33
  
  Car at 6 is slower, won't catch car at 8
  2 separate fleets
```

### Example 7:
```
Input: target = 10, position = [0, 4, 2], speed = [2, 1, 3]
Output: 1

Explanation:
  Sorted: [(4,6), (2,2.67), (0,5)]
  Times: [6, 2.67, 5]
  
  Position 4, time 6: fleet 1
  Position 2, time 2.67 <= 6: joins fleet 1
  Position 0, time 5 <= 6: joins fleet 1
  
  All form 1 fleet
```

## Constraints
- n == position.length == speed.length
- 1 <= n <= 1000
- 0 < target <= 1000
- 0 < speed[i] <= 100
- 0 <= position[i] < target
- All values of position are **unique**

**Recommended Complexity**: O(n log n) time and O(n) space, where n is the number of cars

---

## Pattern Recognition

**Primary Pattern**: **Monotonic Stack (Decreasing Time) with Sorting**

**Why This Pattern?**
- Need to determine which cars form fleets
- Cars can only interact with cars ahead (can't pass)
- Must process in order from target backward
- Stack tracks fleet arrival times

**Key Insight**: Time to Target Determines Fleet Formation
```
Car Fleet Problem:
  Cars can't pass, only catch up
  If faster car behind catches slower car ahead, they form fleet
  
Key observation: Only TIME to reach target matters!
  time = (target - position) / speed
  
Fleet formation rule:
  If car behind has time <= car ahead:
    → Catches up, forms fleet with car ahead
  Else:
    → Won't catch up, separate fleet

Example: target=10, position=[1,4], speed=[3,2]
  Car at 1: time = (10-1)/3 = 3 hours
  Car at 4: time = (10-4)/2 = 3 hours
  
  Car at 1 (behind) takes 3 hours
  Car at 4 (ahead) takes 3 hours
  Behind car catches up exactly at target → 1 fleet ✓
```

**The Sorting Strategy**:
```
Must process cars from closest to target to farthest

Why descending by position?
  Cars ahead determine if cars behind can catch up
  Must know fleet ahead before processing car behind
  
Sort by position descending:
  position: [7, 4, 1, 0] (closest to target first)
  
Then process in order:
  1. Car at 7: time=3, forms first fleet
  2. Car at 4: time=3, can catch car at 7? Yes → join fleet
  3. Car at 1: time=4.5, can catch fleet at 4? No → new fleet
  4. Car at 0: time=10, can catch fleet at 1? No → new fleet
  
Result: 3 fleets
```

**The Stack Strategy**:
```
Stack stores arrival times of fleet leaders

Invariant: Stack contains times in DECREASING order (bottom to top)
  stack[0] (bottom) = earliest fleet (largest time)
  stack[top] = latest fleet (smallest time so far)
  
Why decreasing times?
  Processing closest to target first
  These cars arrive earliest (smallest time)
  Cars farther back take longer (larger time)
  
Algorithm:
  1. Sort cars by position descending
  2. For each car (closest to farthest):
     a. Calculate time to target
     b. If time <= stack.top():
        - Joins fleet ahead (don't push)
     c. Else:
        - Forms new fleet (push time)
  3. Return stack size (number of fleets)
```

**Example Showing Stack Evolution**:
```
Input: target=10, position=[4,1,0,7], speed=[2,2,1,1]

Sort by position: [(7,1), (4,2), (1,2), (0,1)]
Calculate times: [3, 3, 4.5, 10]

Process position 7, time=3:
  Stack: []
  Push 3 (new fleet)
  Stack: [3]
  Fleets: 1

Process position 4, time=3:
  Stack: [3]
  3 <= 3? Yes, joins fleet ahead
  Don't push
  Stack: [3]
  Fleets: 1

Process position 1, time=4.5:
  Stack: [3]
  4.5 <= 3? No, new fleet
  Push 4.5
  Stack: [3, 4.5]
  Fleets: 2

Process position 0, time=10:
  Stack: [3, 4.5]
  10 <= 4.5? No, new fleet
  Push 10
  Stack: [3, 4.5, 10]
  Fleets: 3

Final: 3 fleets ✓
```

**Why Decreasing Time in Stack?**
```
Stack times decrease from bottom to top:
  Bottom (early arrivals, close to target, small time)
  Top (late arrivals, far from target, large time)

Example: Stack [3, 4.5, 10]
  3 (car at position 7, arrives earliest)
  4.5 (car at position 1, arrives later)
  10 (car at position 0, arrives latest)
  
Times increase as we process (farther cars take longer)

Property: If current time > stack.top(), forms new fleet
  Can't catch up to fleet ahead (takes longer to arrive)

If current time <= stack.top(), joins fleet
  Catches up to fleet ahead (arrives at same time or earlier)
```

**Critical Detail**: Why Sort Descending?
```
Must process closest to target first!

Example showing why:
  target=10, positions=[1, 7], speeds=[1, 1]
  
  Ascending order [1, 7]:
    Process position 1: time = 9
    Process position 7: time = 3
    But car at 7 is AHEAD of car at 1!
    Can't determine if car at 1 catches car at 7
    Need to know car at 7's time first!
  
  Descending order [7, 1]:
    Process position 7: time = 3, fleet 1
    Process position 1: time = 9, time > 3, fleet 2
    Clear! Car at 1 takes longer, won't catch car at 7 ✓

Descending = process cars in order they appear on road
```

**Related Patterns**:
1. **Monotonic Stack** — Core technique
2. **Sorting + Stack** — Preprocessing + data structure
3. **Time-Based Simulation** — Calculate arrival times
4. **Greedy** — Local decisions (join fleet or not)

---

## Algorithm & Approach

### Core Insight

**Why Naive Approach Fails:**
```
Naive: Simulate movement step by step
  for each time step:
      move each car forward
      check for collisions
      merge fleets
  
Time: O(n * max_time) where max_time could be very large
For target=1000, speed=1: 1000 time steps!
Too slow!

Optimal approach:
  Calculate final arrival times directly
  Sort by position
  Use stack to count fleets
  → O(n log n) ✓
```

**The Optimal Strategy**:
```
Key observations:
  1. Don't simulate movement, calculate arrival times
  2. Sort by position (closest to target first)
  3. Stack tracks fleet leaders
  4. Cars only join fleet ahead if time <= fleet time
  5. Each car processed once
  
All operations:
  Sort: O(n log n)
  Process each car: O(n)
  Stack operations: O(1) per car
  
Total: O(n log n) for sorting
```

### Step-by-Step Algorithm

---

#### **Approach 1: Stack with Sorting - OPTIMAL**

**Core Idea**:
- Sort cars by position descending
- Calculate time to target for each
- Use stack to track fleet times
- Join fleet if time <= stack top, else new fleet

**Algorithm**
```
carFleet(target, position, speed):
    n = position.length
    
    // Create pairs of (position, speed)
    cars = array of (position[i], speed[i]) for i in 0..n-1
    
    // Sort by position descending
    sort(cars, by position descending)
    
    // Stack to store fleet arrival times
    stack = new Stack()
    
    for each car in cars:
        // Calculate time to reach target
        time = (target - car.position) / car.speed
        
        // If stack empty or time > stack.top(), new fleet
        if stack.isEmpty() or time > stack.peek():
            stack.push(time)
        // Else: time <= stack.top(), joins fleet ahead (don't push)
    
    return stack.size()  // Number of fleets
```

**Code Implementation**
```java
class Solution {
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        
        // Create array of (position, speed) pairs
        double[][] cars = new double[n][2];
        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = speed[i];
        }
        
        // Sort by position descending
        Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0]));
        
        // Stack to store fleet arrival times
        Stack<Double> stack = new Stack<>();
        
        for (double[] car : cars) {
            double time = (target - car[0]) / car[1];
            
            // If this car takes longer than fleet ahead, it's a new fleet
            if (stack.isEmpty() || time > stack.peek()) {
                stack.push(time);
            }
            // Else: time <= stack.peek(), joins fleet ahead
        }
        
        return stack.size();
    }
}
```

**Example Walkthrough**

Input: `target = 10, position = [4, 1, 0, 7], speed = [2, 2, 1, 1]`

| Step | Position | Speed | Time | Stack Before | Action | Stack After |
|------|----------|-------|------|--------------|--------|-------------|
| Sort | - | - | - | [] | Sort by position desc | [(7,1), (4,2), (1,2), (0,1)] |
| 1 | 7 | 1 | 3.0 | [] | Push 3.0 | [3.0] |
| 2 | 4 | 2 | 3.0 | [3.0] | 3.0 <= 3.0, join fleet | [3.0] |
| 3 | 1 | 2 | 4.5 | [3.0] | 4.5 > 3.0, push 4.5 | [3.0, 4.5] |
| 4 | 0 | 1 | 10.0 | [3.0, 4.5] | 10.0 > 4.5, push 10.0 | [3.0, 4.5, 10.0] |

Final stack size: **3** fleets

**Complexity Analysis**
- **Time**: O(n log n) — Sorting dominates, stack operations O(n)
- **Space**: O(n) — Array for pairs, stack for times

---

#### **Approach 2: Without Explicit Stack - SPACE OPTIMIZED**

**Core Idea**: Track fleet count without stack.

**Code Implementation**
```java
class Solution {
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        
        // Create and sort pairs
        double[][] cars = new double[n][2];
        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = speed[i];
        }
        Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0]));
        
        int fleets = 0;
        double prevTime = 0;
        
        for (double[] car : cars) {
            double time = (target - car[0]) / car[1];
            
            // If this car takes longer than previous fleet, new fleet
            if (time > prevTime) {
                fleets++;
                prevTime = time;
            }
            // Else: joins previous fleet
        }
        
        return fleets;
    }
}
```

**Key Difference**: 
- No stack, just counter and previous time
- Same logic: if time > prevTime, new fleet
- Slightly less space (no stack object)

**Complexity Analysis**
- **Time**: O(n log n) — Sorting
- **Space**: O(n) — Cars array (slightly less than stack approach)

---

#### **Approach 3: Using TreeMap for Sorting - ALTERNATIVE**

**Core Idea**: Use TreeMap to sort while building pairs.

**Code Implementation**
```java
class Solution {
    public int carFleet(int target, int[] position, int[] speed) {
        // TreeMap automatically sorts by key (position)
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        
        for (int i = 0; i < position.length; i++) {
            map.put(position[i], speed[i]);
        }
        
        Stack<Double> stack = new Stack<>();
        
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            double time = (double)(target - entry.getKey()) / entry.getValue();
            
            if (stack.isEmpty() || time > stack.peek()) {
                stack.push(time);
            }
        }
        
        return stack.size();
    }
}
```

**Key Difference**: 
- TreeMap sorts automatically
- Cleaner code but same complexity
- Slightly slower due to TreeMap overhead

**Complexity Analysis**
- **Time**: O(n log n) — TreeMap insertion
- **Space**: O(n) — TreeMap + stack

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Code Complexity | Recommended |
|----------|------|-------|-----------------|-------------|
| **Stack with Sorting** | **O(n log n)** | **O(n)** | **Medium ✅** | **Yes ✅** |
| Without Stack | O(n log n) | O(n) | Medium | Space optimization |
| TreeMap | O(n log n) | O(n) | Medium | Alternative |

**Winner**: **Stack with Sorting** — clear, standard, optimal!

### Why Calculate Time to Target?

```
Key insight: Movement doesn't matter, only arrival time!

Example: target=10
  Car A: position=1, speed=3 → time = 9/3 = 3 hours
  Car B: position=4, speed=2 → time = 6/2 = 3 hours
  
  Both arrive at same time (3 hours)
  Car A (behind, faster) catches Car B (ahead, slower)
  → Form 1 fleet

Don't need to simulate:
  Hour 0: A at 1, B at 4
  Hour 1: A at 4, B at 6
  Hour 2: A at 7, B at 8
  Hour 3: A at 10, B at 10 (meet!)
  
Just compare times: 3 = 3 → fleet!

Time formula captures everything we need:
  time = (target - position) / speed
```

### Why Sort by Position Descending?

```
Must process cars from target backward!

Example showing correct order:
  target=10, positions=[0, 5, 8]
  
  Process 8 first (closest to target):
    Forms fleet 1
  Process 5 next:
    Can it catch fleet at 8? Check times
  Process 0 last:
    Can it catch fleet ahead? Check times
  
Each car only looks at fleet AHEAD (closer to target)

If we sorted ascending:
  Process 0: forms fleet
  Process 5: but car at 8 is ahead of 5!
    Can't determine if 5 catches 8
    Need to process 8 first!

Descending = process in road order (target → start)
```

### Why Use <= for Fleet Formation?

```
Critical: Use <= not < for time comparison!

Problem states: "If a car catches up... the moment the fleet reaches"

Example: target=10, position=[1,4], speed=[3,2]
  Car at 1: time = 3
  Car at 4: time = 3
  
  3 <= 3? Yes, they meet EXACTLY at destination
  Form 1 fleet ✓

If we used < (strictly less):
  3 < 3? No
  Don't join → 2 fleets ❌
  
  But problem says they meet at destination!

Equal time = catch up at destination = join fleet
Use <= in condition.
```

### Why Stack Times are Decreasing?

```
Stack maintains decreasing times (bottom to top)

Example: target=10, positions=[7,4,1,0]
  
  Process 7: time=3, stack=[3]
  Process 4: time=3, join fleet, stack=[3]
  Process 1: time=4.5, new fleet, stack=[3, 4.5]
  Process 0: time=10, new fleet, stack=[3, 4.5, 10]
  
  Times: 3 < 4.5 < 10 (increasing!)

Why increasing, not decreasing?
  Processing closest to target first (small positions)
  These arrive earliest (small times)
  Cars farther back (large positions) take longer (large times)
  
Stack bottom = earliest arrival
Stack top = latest arrival

If current time > stack.top():
  Takes longer than previous fleet
  Can't catch up → new fleet

If current time <= stack.top():
  Arrives same time or earlier
  Catches up → join fleet
```

---

## Critical Edge Cases & Gotchas

### 1. **Single Car**
```java
Input: target=10, position=[5], speed=[1]
Time: (10-5)/1 = 5
Output: 1 (single car = 1 fleet)
```

### 2. **All Cars Form One Fleet**
```java
Input: target=10, position=[0,2,4], speed=[4,2,1]
Times: [2.5, 4, 6] (descending after sort by position)
Sorted positions: [4,2,0]
Times in order: [6, 4, 2.5]
Each time <= previous, all join → 1 fleet
```

### 3. **No Cars Form Fleet**
```java
Input: target=10, position=[0,2,4], speed=[1,2,3]
Sorted: [4,2,0]
Times: [2, 4, 10]
Each time > previous, all separate → 3 fleets
```

### 4. **Equal Arrival Times**
```java
Input: target=10, position=[1,4], speed=[3,2]
Times: [3, 3]
3 <= 3 → join fleet → 1 fleet
```

### 5. **Cars Already at Target**
```java
No cars can start at target (position < target constraint)
```

### 6. **Very Close Positions**
```java
Input: target=10, position=[8,9], speed=[1,2]
Times: [2, 0.5]
Car at 9 arrives first (0.5)
Car at 8 arrives later (2)
2 > 0.5 → 2 fleets
```

### 7. **Large Speed Difference**
```java
Input: target=100, position=[0,50], speed=[100,1]
Times: [1, 50]
Car at 0 (behind but fast) time=1
Car at 50 (ahead but slow) time=50
1 <= 50 → joins fleet → 1 fleet
```

### 8. **Two Cars, No Fleet**
```java
Input: target=10, position=[5,3], speed=[1,2]
Sorted: [5,3]
Times: [5, 3.5]
3.5 <= 5 → join fleet → 1 fleet
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Sorting by Position Ascending**
```java
// WRONG - sorts ascending
Arrays.sort(cars, (a, b) -> Double.compare(a[0], b[0]));
```

**Why wrong**: Processes farthest cars first, can't determine fleet formation!

**Dry run failure for target=10, position=[1,7], speed=[1,1]:**
```
Sorted ascending: [(1,1), (7,1)]
Times: [9, 3]

Process position 1: time=9, push, stack=[9]
Process position 7: time=3, 3<=9? Yes, join fleet ❌

But car at 7 is AHEAD of car at 1!
Car at 1 is behind, should check if it catches car at 7!
Wrong order!

Correct (descending): [(7,1), (1,1)]
Process 7: time=3, push, stack=[3]
Process 1: time=9, 9>3? Yes, new fleet ✓
Car at 1 takes longer, won't catch up → 2 fleets ✓
```

**Fix**: Sort descending
```java
Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0]));
```

### ❌ **MISTAKE 2: Using < Instead of <=**
```java
// WRONG - uses < instead of <=
if (stack.isEmpty() || time > stack.peek()) {
    stack.push(time);
}
```

**Why wrong**: Equal times should join fleet (meet at destination)!

**Dry run failure for target=10, position=[1,4], speed=[3,2]:**
```
Times: [3, 3]
Sorted: [(4,2), (1,3)]
Times in order: [3, 3]

Process position 4: time=3, push, stack=[3]
Process position 1: time=3
  3 > 3? No, don't push ✓ (correct, joins fleet)

But with <:
  if (time >= stack.peek()): push
  3 >= 3? Yes, push ❌
  Stack: [3, 3]
  2 fleets ❌ (should be 1!)
```

**Fix**: Use > (which means <= joins)
```java
if (stack.isEmpty() || time > stack.peek()) {
    stack.push(time);
}
```

### ❌ **MISTAKE 3: Not Handling Empty Stack**
```java
// WRONG - doesn't check empty
if (time > stack.peek()) {
    stack.push(time);
}
```

**Why wrong**: peek() on empty stack throws exception!

**Dry run failure for first car:**
```
First car: stack empty
Condition: time > stack.peek()
peek() on empty stack → EmptyStackException ❌
```

**Fix**: Check isEmpty first
```java
if (stack.isEmpty() || time > stack.peek())
```

### ❌ **MISTAKE 4: Integer Division**
```java
// WRONG - integer division
int time = (target - car.position) / car.speed;
```

**Why wrong**: Loses decimal precision!

**Dry run failure for target=10, position=[1], speed=[3]:**
```
time = (10-1) / 3 = 9/3 = 3 (integer)
Correct: 3.0

But for target=10, position=[1], speed=[4]:
  time = 9/4 = 2 (integer) ❌
  Correct: 2.25

Lost precision affects fleet formation!
```

**Fix**: Use double
```java
double time = (target - car[0]) / car[1];
```

### ❌ **MISTAKE 5: Comparing Speeds Instead of Times**
```java
// WRONG - compares speeds
if (car.speed > prevCar.speed) {
    // new fleet?
}
```

**Why wrong**: Speed alone doesn't determine if cars meet!

**Dry run failure:**
```
target=10
Car A: position=1, speed=2 → time = 4.5
Car B: position=5, speed=1 → time = 5

Compare speeds: 2 > 1, but...
Car A (faster) takes LESS time (4.5 < 5)
Car A behind catches Car B ahead!

Must compare times, not speeds!
```

**Fix**: Calculate and compare times
```java
double time = (target - position) / speed;
if (time > stack.peek()) { ... }
```

### ❌ **MISTAKE 6: Not Sorting at All**
```java
// WRONG - processes in input order
for (int i = 0; i < n; i++) {
    double time = (target - position[i]) / speed[i];
    // ...
}
```

**Why wrong**: Input order doesn't match road order!

**Dry run failure for position=[1,7], speed=[1,1]:**
```
Process in input order:
  i=0: position=1, time=9
  i=1: position=7, time=3

Can't determine fleet formation!
Position 7 is ahead but processed after position 1
```

**Fix**: Sort by position first
```java
Arrays.sort(cars, (a, b) -> Double.compare(b[0], a[0]));
```

### ❌ **MISTAKE 7: Returning Wrong Value**
```java
// WRONG - returns stack top instead of size
return (int)stack.peek();
```

**Why wrong**: Need number of fleets, not a time!

**Fix**: Return stack size
```java
return stack.size();
```

---

## Complexity Analysis

### Time Complexity: **O(n log n)**

| Operation | Count | Time Each | Total |
|-----------|-------|-----------|-------|
| **Create pairs** | n | O(1) | O(n) |
| **Sort pairs** | n | O(log n) | **O(n log n)** |
| **Process each car** | n | O(1) | O(n) |
| **Stack push/peek** | ≤ n | O(1) | O(n) |
| **Total** | - | - | **O(n log n)** |

**Sorting dominates**: O(n log n)

**Time analysis**:
```
Sorting: O(n log n) — Arrays.sort()
Loop through cars: O(n)
Stack operations: O(1) per car, O(n) total

Total: O(n log n) + O(n) = O(n log n)

For n=1000: ~10,000 operations (manageable)
```

### Space Complexity: **O(n)**

| Component | Space | Reason |
|-----------|-------|--------|
| Cars array | O(n) | Store n (position, speed) pairs |
| Stack | O(n) | Worst case: n fleets (all separate) |
| Variables | O(1) | time, position, speed (constant) |
| **Total** | **O(n)** | Arrays + stack |

**Space analysis**:
```
Worst case space:
  Cars array: n pairs
  Stack: n elements (all cars separate fleets)
  Total: 2n = O(n)

Best case:
  Cars array: n pairs
  Stack: 1 element (all cars one fleet)
  Total: n + 1 ≈ O(n)

Space complexity: O(n)
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `target = 10, position = [4, 1, 0, 7], speed = [2, 2, 1, 1]`

**Expected Output:** `3`

---

**Step 1: Create Pairs**
```
Cars: [(4,2), (1,2), (0,1), (7,1)]
```

---

**Step 2: Sort by Position Descending**
```
Sorted: [(7,1), (4,2), (1,2), (0,1)]

Road visualization:
Position: 0───1───4──────7──→10 (target)
          ↑   ↑   ↑      ↑
         Car3 Car2 Car1  Car0
         s=1  s=2  s=2   s=1
```

---

**Step 3: Process Car at Position 7**
```
Position: 7, Speed: 1
Time: (10-7)/1 = 3.0 hours

Stack: []
Action: Stack empty, push 3.0

Stack:
   ┌─────┐
   │ 3.0 │ ← top (fleet 1)
   └─────┘

Fleets: 1
```

---

**Step 4: Process Car at Position 4**
```
Position: 4, Speed: 2
Time: (10-4)/2 = 3.0 hours

Stack: [3.0]
Comparison: 3.0 <= 3.0? Yes!
Action: Joins fleet 1 (don't push)

Stack:
   ┌─────┐
   │ 3.0 │ ← top (fleet 1 with 2 cars)
   └─────┘

Fleets: 1

Why joins?
  Car at 4 takes 3 hours
  Fleet at 7 takes 3 hours
  They meet exactly at target → same fleet!
```

---

**Step 5: Process Car at Position 1**
```
Position: 1, Speed: 2
Time: (10-1)/2 = 4.5 hours

Stack: [3.0]
Comparison: 4.5 > 3.0? Yes!
Action: Forms new fleet (push 4.5)

Stack:
   ┌─────┐
   │ 4.5 │ ← top (fleet 2)
   ├─────┤
   │ 3.0 │ (fleet 1)
   └─────┘

Fleets: 2

Why new fleet?
  Car at 1 takes 4.5 hours
  Fleet ahead takes 3 hours
  Fleet arrives before car at 1 catches up
  → Separate fleet!
```

---

**Step 6: Process Car at Position 0**
```
Position: 0, Speed: 1
Time: (10-0)/1 = 10.0 hours

Stack: [3.0, 4.5]
Comparison: 10.0 > 4.5? Yes!
Action: Forms new fleet (push 10.0)

Stack:
   ┌─────┐
   │10.0 │ ← top (fleet 3)
   ├─────┤
   │ 4.5 │ (fleet 2)
   ├─────┤
   │ 3.0 │ (fleet 1)
   └─────┘

Fleets: 3

Why new fleet?
  Car at 0 takes 10 hours
  Fleet ahead takes 4.5 hours
  Much slower, can't catch up
  → Separate fleet!
```

---

**Final Result:**
```
Stack size: 3
Return: 3 fleets ✓

Fleet 1: Cars at positions 7 and 4 (arrive at 3 hours)
Fleet 2: Car at position 1 (arrives at 4.5 hours)
Fleet 3: Car at position 0 (arrives at 10 hours)
```

---

### Timeline Visualization

```
Time-based view:

At target (t=3 hours):
  Fleet 1 arrives: [Car7, Car4]

At target (t=4.5 hours):
  Fleet 2 arrives: [Car1]

At target (t=10 hours):
  Fleet 3 arrives: [Car0]

Position over time:
  t=0:   0─1─4──7→10
  t=1:   ─0─2─5─8→10
  t=2:   ──3─5─9→10
  t=3:   ──4─10→10 (Fleet 1 arrives!)
  t=4:   ──8──10 (Car1 still going)
  t=4.5: ──10 (Fleet 2 arrives!)
  t=10:  →10 (Fleet 3 arrives!)
```

---

## Comparison of Approaches

| Approach | Time | Space | Code Lines | Clarity | Recommended |
|----------|------|-------|------------|---------|-------------|
| **Stack with Sorting** | **O(n log n)** | **O(n)** | **~20** | **Excellent ✅** | **Yes ✅** |
| Without Stack | O(n log n) | O(n) | ~18 | Good | Space optimization |
| TreeMap | O(n log n) | O(n) | ~18 | Good | Alternative |

**All optimal approaches have same time complexity**

**Recommendation**: Use **Stack with Sorting** — clear, standard, easy to understand!

---

## Key Takeaways

1. **Calculate time to target** — time = (target - position) / speed
2. **Sort by position descending** — closest to target first
3. **Use stack to track fleet times** — decreasing order (bottom to top)
4. **Use <= for fleet formation** — equal time = meet at destination
5. **If time > stack.top(), new fleet** — can't catch up
6. **If time <= stack.top(), join fleet** — catches up
7. **Return stack.size()** — number of fleets
8. **O(n log n) from sorting** — optimal complexity

---

## Interview Tips

**What to say in an interview:**

> "This problem asks how many car fleets arrive at the destination. Cars can't pass each other, so faster cars behind slower cars ahead will catch up and form fleets. The key insight is that we don't need to simulate movement—we can calculate the time each car takes to reach the target using the formula time = (target - position) / speed. I'll sort the cars by position in descending order, processing from closest to target to farthest. This ensures we know about fleets ahead before processing cars behind. I'll use a stack to track fleet arrival times. For each car, if its time is greater than the stack top, it forms a new fleet because it can't catch the fleet ahead. Otherwise, it joins the existing fleet. The stack size at the end gives us the number of fleets. Time complexity is O(n log n) for sorting, space is O(n) for the cars array and stack."

**Key points to mention:**
1. **Time calculation** — (target - position) / speed
2. **Sort descending** — closest to target first
3. **Stack tracks fleet times** — increasing from bottom to top
4. **Fleet formation rule** — time <= stack.top() joins, else new fleet
5. **No simulation needed** — direct calculation
6. **O(n log n) time** — dominated by sorting

**Common Follow-ups:**
- "Why sort descending?" → Process cars in road order, need fleet ahead info first
- "Why use stack?" → Track fleet times, check if current car catches fleet ahead
- "What if cars can pass?" → Different problem, no fleet formation
- "Can you optimize?" → Without stack using counter, same O(n log n) time

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Car Fleet** | Medium | **Stack + Sorting** | **This problem** |
| Car Fleet II | Hard | Monotonic Stack | Calculate collision times, complex |
| Meeting Rooms II | Medium | Sorting + Priority Queue | Room allocation |
| Merge Intervals | Medium | Sorting | Interval merging |
| Non-overlapping Intervals | Medium | Sorting + Greedy | Minimum removals |

**Pattern Progression**:
1. **Basic fleet formation** (this problem) — Time-based merging
2. **Complex fleet dynamics** (Car Fleet II) — Pairwise collision times
3. **Resource allocation** (Meeting Rooms) — Time-based scheduling
4. **Interval problems** — Similar sorting + merge pattern

---

## Final Pattern Label

✅ **Monotonic Stack (Decreasing Time) + Sorting by Position**

**Remember:** Calculate time to target for each car: time = (target - position) / speed. Sort by position **descending** (closest to target first). Use stack to track fleet arrival times. If current time > stack.top(), forms new fleet (push). If time <= stack.top(), joins existing fleet (don't push). Stack times increase from bottom to top as we process farther cars. Return stack.size() for number of fleets. O(n log n) time from sorting, O(n) space. Use **<= not <** for catching up at destination!
