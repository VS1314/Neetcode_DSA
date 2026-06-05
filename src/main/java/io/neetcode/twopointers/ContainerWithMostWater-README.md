# Container With Most Water

## Problem Description

**Difficulty**: Medium

You are given an integer array `height` where `height[i]` represents the height of the i-th bar.

You may choose any two bars to form a container. Return the maximum amount of water a container can store.

**Note**: The water is contained between two vertical bars, and the amount of water is determined by the shorter bar (height) and the distance between the bars (width).

## Examples

### Example 1:
```
Input: height = [1,7,2,5,4,7,3,6]
Output: 36
Explanation:
The bars at index 1 (height 7) and index 7 (height 6) form the largest container.
Area = (7 - 1) × min(7, 6) = 6 × 6 = 36
```

### Example 2:
```
Input: height = [2,2,2]
Output: 4
Explanation:
Any two bars form the same container since all heights are equal.
Area = (2 - 0) × min(2, 2) = 2 × 2 = 4
```

### Example 3:
```
Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation:
The bars at index 1 (height 8) and index 8 (height 7) form the largest container.
Area = (8 - 1) × min(8, 7) = 7 × 7 = 49
```

## Constraints
- 2 <= height.length <= 1000
- 0 <= height[i] <= 1000

**Recommended Complexity**: O(n) time, O(1) space

---

## Pattern Recognition

**Primary Pattern**: **Two Pointers (Opposite Direction with Greedy Choice)**

**Why This Pattern?**
- Need to find optimal pair of bars
- Brute force O(n²) checking all pairs is too slow
- Two pointers from both ends with greedy strategy achieves O(n)
- Container area limited by shorter bar — key insight for pointer movement

**Key Insight**: Always Move the Shorter Bar's Pointer
```
Container Area Formula:
  area = width × min(height[left], height[right])
  area = (right - left) × min(height[left], height[right])

Why move the shorter pointer?
  If height[left] < height[right]:
    - Current area limited by height[left]
    - Moving right-- decreases width, can't increase area
    - Moving left++ might find taller bar, potentially increasing area
    - Therefore: ALWAYS move left++
    
  If height[right] < height[left]:
    - Current area limited by height[right]
    - Moving left++ decreases width, can't increase area
    - Moving right-- might find taller bar, potentially increasing area
    - Therefore: ALWAYS move right--

Example visualization:
  height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
           ↑                       ↑
           L(1)                    R(7)
  
  area = 8 × min(1,7) = 8 × 1 = 8
  
  Move L (shorter):
           1  8  6  2  5  4  8  3  7
              ↑                    ↑
              L(8)                 R(7)
  
  area = 7 × min(8,7) = 7 × 7 = 49 ✓ (better!)
```

**Why This Greedy Strategy Works?**
```
Proof by Contradiction:

Assume height[left] < height[right]

Case 1: We move right-- (wrong choice)
  New width: smaller (right - left - 1)
  New height: at most min(height[left], height[right-1])
             ≤ height[left] (still limited by shorter bar)
  New area ≤ (right - left - 1) × height[left]
           < (right - left) × height[left]
           ≤ current area
  Result: Can't improve! ❌

Case 2: We move left++ (correct choice)
  New width: smaller (right - left - 1)
  New height: min(height[left+1], height[right])
             might be > height[left] if height[left+1] > height[left]
  New area: might improve if we find taller bar!
  Result: Potential to improve! ✓

Conclusion: Moving the shorter pointer is the only way to possibly improve!
```

**Related Patterns**:
1. **Two Pointers** — Core technique
2. **Greedy Algorithm** — Always move shorter pointer
3. **Trapping Rain Water** — Similar but calculates total trapped water
4. **Maximum Rectangle in Histogram** — Related area calculation problem

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: Try all pairs (i, j)
  → Two nested loops
  → O(n²) time
  → For n=1000: 1 million operations (acceptable but not optimal)

Two Pointers:
  → Start from both ends
  → Greedily move shorter pointer
  → O(n) time — one pass
  → Optimal! ✓
```

**The Greedy Choice**:
```
Container Area = width × height
  where height = min(height[left], height[right])

Key observation:
  - Width always decreases as pointers move inward
  - Only way to increase area: find taller bar
  - Moving taller pointer can't help (still limited by shorter)
  - Moving shorter pointer might find taller bar
  
Strategy: Always move the pointer at shorter bar!
```

### Step-by-Step Algorithm

---

#### **Approach 1: Two Pointers (OPTIMAL)**

**Core Idea**:
- Initialize two pointers at both ends
- Calculate area with current pair
- Move the pointer with smaller height inward
- Track maximum area seen

**Algorithm**
```
maxArea(height):
    left = 0
    right = n - 1
    maxArea = 0
    
    while left < right:
        width = right - left
        currentHeight = min(height[left], height[right])
        currentArea = width × currentHeight
        
        maxArea = max(maxArea, currentArea)
        
        // Greedy choice: move shorter pointer
        if height[left] < height[right]:
            left++
        else:
            right--
    
    return maxArea
```

**Code Implementation**
```java
class Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        
        while (left < right) {
            // Calculate current area
            int width = right - left;
            int currentHeight = Math.min(height[left], height[right]);
            int currentArea = width * currentHeight;
            
            // Update maximum
            maxArea = Math.max(maxArea, currentArea);
            
            // Move pointer with smaller height (greedy choice)
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
    }
}
```

**Example Walkthrough**

Input: `height = [1,8,6,2,5,4,8,3,7]`

| Step | L | R | height[L] | height[R] | Width | Height | Area | Max | Action |
|------|---|---|-----------|-----------|-------|--------|------|-----|--------|
| 0 | 0 | 8 | 1 | 7 | 8 | min(1,7)=1 | 8×1=8 | 8 | L<R, L++ |
| 1 | 1 | 8 | 8 | 7 | 7 | min(8,7)=7 | 7×7=49 | 49 | R<L, R-- |
| 2 | 1 | 7 | 8 | 3 | 6 | min(8,3)=3 | 6×3=18 | 49 | R<L, R-- |
| 3 | 1 | 6 | 8 | 8 | 5 | min(8,8)=8 | 5×8=40 | 49 | Equal, R-- |
| 4 | 1 | 5 | 8 | 4 | 4 | min(8,4)=4 | 4×4=16 | 49 | R<L, R-- |
| 5 | 1 | 4 | 8 | 5 | 3 | min(8,5)=5 | 3×5=15 | 49 | R<L, R-- |
| 6 | 1 | 3 | 8 | 2 | 2 | min(8,2)=2 | 2×2=4 | 49 | R<L, R-- |
| 7 | 1 | 2 | 8 | 6 | 1 | min(8,6)=6 | 1×6=6 | 49 | R<L, R-- |
| — | — | — | — | — | — | — | — | — | L>=R, stop |

**Output:** `49`

**Complexity Analysis**
- **Time Complexity**: O(n) — Single pass through array, each element visited once
- **Space Complexity**: O(1) — Only a few variables

---

#### **Approach 2: Brute Force (NOT OPTIMAL)**

**Core Idea**: Try all possible pairs and find maximum area.

**Code Implementation**
```java
class Solution {
    public int maxArea(int[] height) {
        int maxArea = 0;
        int n = height.length;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int width = j - i;
                int currentHeight = Math.min(height[i], height[j]);
                int currentArea = width * currentHeight;
                maxArea = Math.max(maxArea, currentArea);
            }
        }
        
        return maxArea;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n²) — Two nested loops
- **Space Complexity**: O(1)
- **Why Not Optimal**: Too slow for large inputs

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | **Two Pointers** |
|-------------|-------------|------------------|
| Time complexity | O(n²) ❌ | **O(n) ✅** |
| Space complexity | O(1) ✓ | **O(1) ✅** |
| Code simplicity | Simple | **Clean ✅** |
| Optimal | ❌ | **✅** |

**Winner**: **Two Pointers** — optimal time with clean greedy strategy!

### Why Greedy Works?

**Mathematical Proof:**
```
Let's say we're at (i, j) with height[i] < height[j]

Option 1: Move j-- (exploring (i, j-1))
  area(i, j-1) = (j-1-i) × min(height[i], height[j-1])
               ≤ (j-1-i) × height[i]  (height[i] is limiting factor)
               < (j-i) × height[i]     (width decreased)
               ≤ area(i, j)
  
  Can't be better! Any pair with i is limited by height[i] and has smaller width.

Option 2: Move i++ (exploring (i+1, j))
  area(i+1, j) = (j-i-1) × min(height[i+1], height[j])
               might be > area(i, j) if height[i+1] > height[i]
  
  Potential to improve!

Conclusion: Must move pointer at shorter bar to have any chance of improving!
```

**Visual Intuition:**
```
height = [3, 9, 3, 4, 7, 2, 12, 6]
          ↑                    ↑
          L=3                  R=6

Current area = 7 × min(3,6) = 7 × 3 = 21

Why not move R?
  Any future pair with L=0 has:
    - Smaller width (< 7)
    - Height still ≤ 3 (limited by height[0])
    - Can't exceed 21!
  
Why move L?
  Future pairs without L=0:
    - Smaller width (< 7)
    - But might have height > 3
    - Could exceed 21!
  
  Example: L=1, R=7
    area = 6 × min(9,6) = 6 × 6 = 36 > 21 ✓
```

---

## Critical Edge Cases & Gotchas

### 1. **Minimum Size (n=2)**
```java
Input: height = [1, 1]
Output: 1
Explanation: width = 1, height = min(1,1) = 1, area = 1 × 1 = 1
```

### 2. **All Same Heights**
```java
Input: height = [5, 5, 5, 5]
Output: 15
Explanation: Maximum width pair (0,3): 3 × 5 = 15
```

### 3. **Increasing Heights**
```java
Input: height = [1, 2, 3, 4, 5]
Output: 6
Explanation: Pairs (0,4) or (1,4): 4 × min(1,5) = 4 or 3 × min(2,5) = 6
```

### 4. **Decreasing Heights**
```java
Input: height = [5, 4, 3, 2, 1]
Output: 6
Explanation: Pairs (0,4) or (0,3): 4 × min(5,1) = 4 or 3 × min(5,2) = 6
```

### 5. **Tall Bars at Ends**
```java
Input: height = [10, 1, 1, 1, 10]
Output: 40
Explanation: Bars at ends: 4 × min(10,10) = 40
```

### 6. **Tall Bar in Middle**
```java
Input: height = [1, 100, 1]
Output: 2
Explanation: Can't use middle bar effectively: max = 2 × min(1,1) = 2
```

### 7. **Zero Heights**
```java
Input: height = [0, 2, 0]
Output: 0
Explanation: Any pair with 0 gives area 0
```

### 8. **Large Array with Pattern**
```java
Input: height = [1, 3, 2, 5, 25, 24, 5]
Output: 24
Explanation: Pairs (3,5): 2 × min(5,24) = 10 or (1,6): 5 × min(3,5) = 15
              Best is (4,5): 1 × min(25,24) = 24
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Moving the Taller Pointer**
```java
// WRONG - moves pointer with larger height
public int maxArea(int[] height) {
    int left = 0, right = height.length - 1;
    int maxArea = 0;
    
    while (left < right) {
        int area = (right - left) * Math.min(height[left], height[right]);
        maxArea = Math.max(maxArea, area);
        
        // WRONG! Should move shorter pointer
        if (height[left] > height[right]) {
            left++;
        } else {
            right--;
        }
    }
    
    return maxArea;
}
```

**Why wrong**: Moving the taller pointer can never improve the area!

**Dry run failure for height=[1,8,6,2,5,4,8,3,7]:**
```
L=0, R=8: height[L]=1, height[R]=7
  area = 8 × 1 = 8
  height[L] < height[R], but code moves L (correct accidentally)

L=1, R=8: height[L]=8, height[R]=7
  area = 7 × 7 = 49
  height[L] > height[R], code moves L (WRONG!)
  Should move R to potentially find better!

Misses optimal strategy, gets suboptimal result!
```

**Fix**: Move the shorter pointer
```java
if (height[left] < height[right]) {
    left++;
} else {
    right--;
}
```

### ❌ **MISTAKE 2: Using Maximum Height Instead of Minimum**
```java
// WRONG - uses max instead of min for height
int currentHeight = Math.max(height[left], height[right]);  // WRONG!
int currentArea = width * currentHeight;
```

**Why wrong**: Water level is limited by the SHORTER bar, not taller!

**Dry run failure:**
```
height = [2, 1]
width = 1
Wrong: height = max(2,1) = 2 → area = 1 × 2 = 2
Correct: height = min(2,1) = 1 → area = 1 × 1 = 1

The 2 is wrong because water would overflow at height[1]=1!
```

**Fix**: Always use minimum
```java
int currentHeight = Math.min(height[left], height[right]);
```

### ❌ **MISTAKE 3: Calculating Width Incorrectly**
```java
// WRONG - incorrect width calculation
int width = right - left + 1;  // WRONG! Off by one
```

**Why wrong**: Width between bars at positions i and j is j - i, not j - i + 1!

**Dry run failure:**
```
L=0, R=2: positions 0 and 2
Correct width = 2 - 0 = 2 (distance between bars)
Wrong width = 2 - 0 + 1 = 3 (counts an extra unit)

This inflates all areas by (width+1)/width!
```

**Fix**: Correct formula
```java
int width = right - left;
```

### ❌ **MISTAKE 4: Not Updating Maximum**
```java
// WRONG - forgets to track maximum
public int maxArea(int[] height) {
    int left = 0, right = height.length - 1;
    int currentArea = 0;  // Only tracks current, not max!
    
    while (left < right) {
        currentArea = (right - left) * Math.min(height[left], height[right]);
        
        if (height[left] < height[right]) {
            left++;
        } else {
            right--;
        }
    }
    
    return currentArea;  // Returns last area, not maximum!
}
```

**Why wrong**: Returns the final area calculated, not the maximum across all pairs!

**Fix**: Track maximum
```java
int maxArea = 0;
// ... in loop:
maxArea = Math.max(maxArea, currentArea);
```

### ❌ **MISTAKE 5: Moving Both Pointers**
```java
// WRONG - moves both pointers
if (height[left] < height[right]) {
    left++;
} else {
    right--;
}
left++;  // WRONG! Moving both pointers
```

**Why wrong**: Skips potential optimal pairs by moving both pointers!

**Dry run failure:**
```
height = [3, 9, 3]
L=0, R=2: area = 2 × 3 = 6
Move both: L=1, R=1 (skipped checking (0,1) and (1,2)!)
```

**Fix**: Move only one pointer based on condition

### ❌ **MISTAKE 6: Wrong Loop Condition**
```java
// WRONG - uses <= instead of <
while (left <= right) {  // WRONG!
    // ...
}
```

**Why wrong**: When left == right, there's only one bar, can't form container!

**Dry run failure:**
```
height = [1, 2]
L=0, R=0: Invalid! Can't form container with single bar
Width = 0 - 0 = 0, area = 0
Wastes an iteration!
```

**Fix**: Use strict inequality
```java
while (left < right) { ... }
```

### ❌ **MISTAKE 7: Integer Overflow**
```java
// WRONG - potential overflow with large values
int area = width * currentHeight;  // Might overflow if values are large
```

**Why wrong**: With height ≤ 1000 and width ≤ 1000, max area = 1,000,000 fits in int, but good practice to be aware!

**For this problem**: int is sufficient (max area = 1000 × 1000 = 1,000,000 < 2^31-1)

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Initialize pointers | O(1) | Two variables |
| While loop | O(n) | Each pointer moves inward at most n/2 times |
| Area calculation | O(1) | Simple arithmetic |
| **Total** | **O(n)** | Single pass through array |

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| Pointers (left, right) | O(1) | Two integers |
| Variables (maxArea, width, etc.) | O(1) | Few integers |
| **Total** | **O(1)** | Constant extra space |

**Why O(n) Time is Optimal:**
- Must examine elements to find maximum
- Two pointers visit each element at most once
- Can't do better than O(n)

---

## Visualization

### Complete Example Walkthrough

**Input:** `height = [1, 8, 6, 2, 5, 4, 8, 3, 7]`

**Visual representation:**
```
Height visualization:
      8           8     
      █           █     
      █       6   █     
      █       █   █   7 
      █     5 █   █   █ 
      █   4 █ █   █   █ 
    2 █   █ █ █   █   █ 
  1 █ █ 3 █ █ █   █ 3 █ 
  ▔ ▔ ▔ ▔ ▔ ▔ ▔ ▔ ▔ ▔ ▔
  0 1 2 3 4 5 6 7 8
```

---

**Step 0: Initialize**
```
height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
          ↑                       ↑
          L                       R
          
L=0, R=8
maxArea = 0
```

---

**Step 1: Calculate area(0, 8)**
```
          1                       7
          ↑                       ↑
          L                       R
          
width = 8 - 0 = 8
height = min(1, 7) = 1
area = 8 × 1 = 8
maxArea = 8

height[L]=1 < height[R]=7 → move L++
```

---

**Step 2: Calculate area(1, 8)**
```
             8                    7
             ↑                    ↑
             L                    R
             
width = 8 - 1 = 7
height = min(8, 7) = 7
area = 7 × 7 = 49 ✓ (NEW MAX!)
maxArea = 49

height[L]=8 > height[R]=7 → move R--
```

---

**Step 3: Calculate area(1, 7)**
```
             8                 3
             ↑                 ↑
             L                 R
             
width = 7 - 1 = 6
height = min(8, 3) = 3
area = 6 × 3 = 18
maxArea = 49 (no change)

height[L]=8 > height[R]=3 → move R--
```

---

**Step 4: Calculate area(1, 6)**
```
             8              8
             ↑              ↑
             L              R
             
width = 6 - 1 = 5
height = min(8, 8) = 8
area = 5 × 8 = 40
maxArea = 49 (no change)

height[L]=8 = height[R]=8 → move R-- (equal case)
```

---

**Continue until L >= R...**

**Final Result:** `maxArea = 49`

### Why This Works - Visual Proof

```
Original problem:
  Start: widest possible container
         [1, 8, 6, 2, 5, 4, 8, 3, 7]
          ↑                       ↑
          
  Width is maximum (8), but height limited by min(1,7)=1
  
Move L (shorter):
  Width decreases, but might find taller bar
         [1, 8, 6, 2, 5, 4, 8, 3, 7]
             ↑                    ↑
  
  Found taller bar (8)! Even with smaller width,
  area improved: 7 × 7 = 49 > 8 × 1 = 8

Key insight:
  - If we moved R instead, height still ≤ 1 (limited by L)
  - Smaller width + same/worse height = can't improve
  - Moving shorter pointer is the ONLY way to improve!
```

---

## Comparison of Approaches

| Approach | Time | Space | Optimal | Notes |
|----------|------|-------|---------|-------|
| Brute Force | O(n²) | O(1) | ❌ | Try all pairs |
| **Two Pointers** | **O(n)** | **O(1)** | **✅** | **Greedy, one pass** |

**Recommendation**: Always use **Two Pointers** — it's the optimal solution!

---

## Key Takeaways

1. **Greedy choice** — always move the pointer at the shorter bar
2. **Area formula** — width × min(height[left], height[right])
3. **Two pointers from ends** — start with maximum width
4. **Width always decreases** — moving inward reduces width
5. **Height might increase** — finding taller bar compensates for width loss
6. **O(n) time optimal** — can't do better, must examine all bars
7. **Proof by contradiction** — moving taller pointer provably can't improve

---

## Interview Tips

**What to say in an interview:**

> "This is a classic two-pointer problem. The key insight is that the container area is determined by the width (distance between bars) and the height (limited by the shorter bar). I'll start with pointers at both ends to get maximum width, then greedily move the pointer at the shorter bar inward. Why? Because moving the taller bar's pointer can't possibly increase the area — the height is still limited by the shorter bar, and we're decreasing the width. Only by moving the shorter bar's pointer do we have a chance of finding a taller bar that might compensate for the reduced width. This gives us O(n) time with a single pass and O(1) space."

**Key points to mention:**
1. **Two pointers from ends** — maximize initial width
2. **Area formula** — width × min(height)
3. **Greedy strategy** — always move shorter pointer
4. **Why it works** — moving taller pointer provably can't improve
5. **Complexity** — O(n) time (optimal), O(1) space

**If asked about alternatives:**
> "I could try all pairs with nested loops in O(n²) time, but that's not optimal. The two-pointer greedy approach achieves O(n) time, which is optimal since we need to examine all bars."

**Common Follow-ups:**
- "Prove the greedy choice is correct" → Moving taller pointer keeps height same/worse and reduces width
- "What if heights are equal?" → Either direction works, convention is to move one (usually right--)
- "Can you optimize space further?" → Already O(1), can't improve

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Container With Most Water** | Medium | **Two Pointers** | **This problem** ← **Greedy move shorter** |
| Trapping Rain Water | Hard | Two Pointers / Stack | Calculate total trapped water, not max container |
| Maximum Rectangle in Histogram | Hard | Stack / Divide & Conquer | Histogram bars, different constraints |
| Largest Rectangle in Histogram | Hard | Stack | Consecutive bars, different calculation |
| Max Area of Island | Medium | DFS/BFS | 2D grid, different problem domain |

**Pattern Connection**:
- **Two Pointers** — Core technique
- **Greedy Choice** — Move based on local information
- **Optimization** — Reduce O(n²) to O(n)

---

## Final Pattern Label

✅ **Two Pointers (Opposite Direction with Greedy Choice)**

**Remember:** Start pointers at both ends. Calculate area = (right - left) × min(height[left], height[right]). Always move the pointer at the shorter bar (greedy choice). Why? Moving the taller pointer can't improve area (height stays same/worse, width decreases). Only moving shorter pointer gives a chance to find taller bar. O(n) time, O(1) space. This is the optimal solution!
