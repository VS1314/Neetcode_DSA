# Trapping Rain Water

## Problem Description

**Difficulty**: Hard

You are given an array of non-negative integers `height` which represent an elevation map. Each value `height[i]` represents the height of a bar, which has a width of 1.

Return the **maximum area of water** that can be trapped between the bars.

## Examples

### Example 1:
```
Input: height = [0,2,0,3,1,0,1,3,2,1]
Output: 9
Explanation:
Visual representation:
       █
   █   █ █
   █ █ █ █
   █ █ █ █ █
 █ █ █ █ █ █
━━━━━━━━━━━
0 1 2 3 4 5 6 7 8 9

Water trapped (~ = water):
       █
   █~~~█~█
   █~█~█~█
   █~█~█~█~█
 █~█~█~█~█~█
━━━━━━━━━━━
Total: 1+2+1+1+3+1 = 9
```

### Example 2:
```
Input: height = [4,2,0,3,2,5]
Output: 9
Explanation:
     █
 █   █
 █ █ █
 █ █ █
 █ █ █
━━━━━━
Water: 0+2+4+1+2+0 = 9
```

### Example 3:
```
Input: height = [4,2,3]
Output: 1
Explanation:
 █
 █ █
 █ █
 █ █
━━━━
Water at index 1: min(4,3) - 2 = 2 - 2 = 0... Actually: 1
Wait, let me recalculate:
Water at index 1: min(4,3) - 2 = 3 - 2 = 1
```

## Constraints
- 1 <= height.length <= 1000
- 0 <= height[i] <= 1000

**Recommended Complexity**: O(n) time, O(1) space

---

## Pattern Recognition

**Primary Pattern**: **Two Pointers (Opposite Direction with Max Tracking)**

**Why This Pattern?**
- Need to calculate water trapped at each position
- Water level depends on max heights on both left and right
- Two pointers can track max from both ends simultaneously
- Greedy choice: process side with smaller max height

**Key Insight**: Water at Position i Depends on Boundary Walls
```
Water trapped at index i:
  water[i] = min(maxLeft[i], maxRight[i]) - height[i]
  
  Where:
    maxLeft[i] = maximum height from 0 to i
    maxRight[i] = maximum height from i to n-1
  
Why?
  Water level is limited by the SHORTER wall on either side
  
Example:
  height = [3, 0, 2, 0, 4]
           
  At index 1 (height = 0):
    maxLeft = 3 (tallest bar on left)
    maxRight = 4 (tallest bar on right)
    water = min(3, 4) - 0 = 3 - 0 = 3 units
    
  Visual:
       █
   █~~~█
   █~~~█
   █~█~█
   █ █ █
  ━━━━━━
  
  Water fills up to height 3 (limited by left wall)
```

**Key Insight**: Don't Need Both Maxes Simultaneously!
```
Critical observation:
  If maxLeft < maxRight:
    Water at current position is LIMITED by maxLeft
    → Don't care about exact value of maxRight!
    → Only need to know maxRight >= maxLeft
  
  If maxRight < maxLeft:
    Water at current position is LIMITED by maxRight
    → Don't care about exact value of maxLeft!
    → Only need to know maxLeft >= maxRight

This enables two-pointer approach:
  - Track maxLeft and maxRight as we move pointers
  - Always process the side with SMALLER max
  - That side's max determines water level
  
Example:
  height = [3, 0, 2, 0, 4]
           ↑           ↑
           L           R
  
  maxLeft = 3, maxRight = 4
  
  Since maxLeft < maxRight:
    Process left side
    Water at L depends only on maxLeft (3)
    We know maxRight >= 3, so min(maxLeft, maxRight) = maxLeft
```

**Related Patterns**:
1. **Two Pointers** — Core technique
2. **Prefix/Suffix Max** — Alternative O(n) space approach
3. **Monotonic Stack** — Another approach
4. **Container With Most Water** — Similar but different calculation

---

## Algorithm & Approach

### Core Insight

**Why Brute Force Fails:**
```
Brute force: For each position, find max on left and right
  → Two scans per position: O(n) for maxLeft, O(n) for maxRight
  → O(n²) total time
  → Too slow!

Prefix/Suffix Arrays:
  → Precompute maxLeft and maxRight arrays
  → O(n) time, O(n) space
  → Good but not optimal space

Two Pointers:
  → Track maxLeft and maxRight dynamically
  → Process side with smaller max
  → O(n) time, O(1) space
  → Optimal! ✓
```

**The Two-Pointer Strategy**:
```
Intuition: Process from both ends

Start with pointers at both ends:
  left = 0, right = n-1
  maxLeft = 0, maxRight = 0

At each step:
  Compare height[left] vs height[right]
  Move pointer with SMALLER height
  
  Why?
    Smaller side determines water level
    Process that side, update max, add water
    
Example visualization:
  height = [3, 0, 2, 0, 4]
           ↑           ↑
           L           R
  
  height[L] = 3, height[R] = 4
  L is smaller, process left side
  maxLeft = max(0, 3) = 3
  water = 0 (no water above height 3)
  L++
  
  Continue until L meets R...
```

### Step-by-Step Algorithm

---

#### **Approach 1: Two Pointers (OPTIMAL)**

**Core Idea**:
- Use two pointers from both ends
- Track max heights from both sides
- Process side with smaller height
- Calculate water based on that side's max

**Algorithm**
```
trap(height):
    left = 0
    right = n - 1
    maxLeft = 0
    maxRight = 0
    water = 0
    
    while left < right:
        if height[left] < height[right]:
            // Process left side (left side is limiting)
            if height[left] >= maxLeft:
                maxLeft = height[left]
            else:
                water += maxLeft - height[left]
            left++
        else:
            // Process right side (right side is limiting)
            if height[right] >= maxRight:
                maxRight = height[right]
            else:
                water += maxRight - height[right]
            right--
    
    return water
```

**Code Implementation**
```java
class Solution {
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        
        int left = 0;
        int right = height.length - 1;
        int maxLeft = 0;
        int maxRight = 0;
        int water = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                // Process left side (left is limiting factor)
                if (height[left] >= maxLeft) {
                    // New max on left, no water trapped here
                    maxLeft = height[left];
                } else {
                    // Can trap water here
                    water += maxLeft - height[left];
                }
                left++;
            } else {
                // Process right side (right is limiting factor)
                if (height[right] >= maxRight) {
                    // New max on right, no water trapped here
                    maxRight = height[right];
                } else {
                    // Can trap water here
                    water += maxRight - height[right];
                }
                right--;
            }
        }
        
        return water;
    }
}
```

**Example Walkthrough**

Input: `height = [0,2,0,3,1,0,1,3,2,1]`

| Step | L | R | h[L] | h[R] | maxL | maxR | Smaller? | Action | Water Added | Total |
|------|---|---|------|------|------|------|----------|--------|-------------|-------|
| 0 | 0 | 9 | 0 | 1 | 0 | 0 | L | maxL=0, L++ | 0 | 0 |
| 1 | 1 | 9 | 2 | 1 | 0 | 0 | R | maxR=1, R-- | 0 | 0 |
| 2 | 1 | 8 | 2 | 2 | 0 | 1 | R | maxR=2, R-- | 0 | 0 |
| 3 | 1 | 7 | 2 | 3 | 0 | 2 | L | maxL=2, L++ | 0 | 0 |
| 4 | 2 | 7 | 0 | 3 | 2 | 2 | L | 2-0=2 | 2 | 2 |
| 5 | 3 | 7 | 3 | 3 | 2 | 2 | L | maxL=3, L++ | 0 | 2 |
| 6 | 4 | 7 | 1 | 3 | 3 | 2 | L | 3-1=2 | 2 | 4 |
| 7 | 5 | 7 | 0 | 3 | 3 | 2 | L | 3-0=3 | 3 | 7 |
| 8 | 6 | 7 | 1 | 3 | 3 | 2 | L | 3-1=2 | 2 | 9 |
| 9 | 7 | 7 | — | — | — | — | — | L>=R, stop | — | 9 |

**Output:** `9`

**Complexity Analysis**
- **Time Complexity**: O(n) — Single pass through array
- **Space Complexity**: O(1) — Only a few variables

---

#### **Approach 2: Prefix and Suffix Arrays**

**Core Idea**: Precompute max heights from left and right for each position.

**Code Implementation**
```java
class Solution {
    public int trap(int[] height) {
        int n = height.length;
        if (n == 0) return 0;
        
        // Precompute max from left
        int[] maxLeft = new int[n];
        maxLeft[0] = height[0];
        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i]);
        }
        
        // Precompute max from right
        int[] maxRight = new int[n];
        maxRight[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i]);
        }
        
        // Calculate water
        int water = 0;
        for (int i = 0; i < n; i++) {
            int waterLevel = Math.min(maxLeft[i], maxRight[i]);
            water += waterLevel - height[i];
        }
        
        return water;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n) — Three passes
- **Space Complexity**: O(n) — Two arrays
- **Why Not Optimal**: Uses extra space

---

#### **Approach 3: Brute Force (NOT OPTIMAL)**

**Core Idea**: For each position, scan left and right to find maxes.

**Code Implementation**
```java
class Solution {
    public int trap(int[] height) {
        int n = height.length;
        int water = 0;
        
        for (int i = 0; i < n; i++) {
            // Find max on left
            int maxLeft = 0;
            for (int j = 0; j <= i; j++) {
                maxLeft = Math.max(maxLeft, height[j]);
            }
            
            // Find max on right
            int maxRight = 0;
            for (int j = i; j < n; j++) {
                maxRight = Math.max(maxRight, height[j]);
            }
            
            // Calculate water at this position
            water += Math.min(maxLeft, maxRight) - height[i];
        }
        
        return water;
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n²) — Nested loops
- **Space Complexity**: O(1)
- **Why Not Optimal**: Too slow

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Brute Force | Prefix/Suffix | **Two Pointers** |
|-------------|-------------|---------------|------------------|
| Time complexity | O(n²) ❌ | O(n) ✓ | **O(n) ✅** |
| Space complexity | O(1) ✓ | O(n) ❌ | **O(1) ✅** |
| Code simplicity | Simple | Medium | **Clean ✅** |
| Optimal | ❌ | Partial | **✅** |

**Winner**: **Two Pointers** — optimal time and space!

### Why Two Pointers Works?

**The Key Realization:**
```
Water at position i = min(maxLeft[i], maxRight[i]) - height[i]

We don't need EXACT values of both maxes!
We only need to know which one is SMALLER!

If maxLeft < maxRight:
  min(maxLeft, maxRight) = maxLeft
  Water depends only on maxLeft
  → Process left side

If maxRight < maxLeft:
  min(maxLeft, maxRight) = maxRight
  Water depends only on maxRight
  → Process right side

Two pointers tracks maxLeft and maxRight as we go:
  - Always process the side with smaller max
  - That side's max determines water level
  - No need to know exact value of other side!
```

**Visual Proof:**
```
height = [3, 0, 2, 0, 4]
          ↑           ↑
          L           R

maxLeft = 0, maxRight = 0

Step 1: h[L]=3 < h[R]=4? No, equal, but let's process left
  maxLeft = max(0, 3) = 3
  water = 0 (3 >= 3, no water above this bar)
  L++

Step 2: h[L]=0 < h[R]=4? Yes
  height[L] < maxLeft? Yes (0 < 3)
  water += 3 - 0 = 3
  L++
  
Step 3: h[L]=2 < h[R]=4? Yes
  height[L] < maxLeft? Yes (2 < 3)
  water += 3 - 2 = 1
  L++
  
Step 4: h[L]=0 < h[R]=4? Yes
  height[L] < maxLeft? Yes (0 < 3)
  water += 3 - 0 = 3
  L++
  
Step 5: L >= R, stop

Total water = 3 + 1 + 3 = 7

Verification:
       █
   █~~~█
   █~█~█
   █ █ █
  ━━━━━━
  Water: 3 (index 1) + 1 (index 2) + 3 (index 3) = 7 ✓
```

---

## Critical Edge Cases & Gotchas

### 1. **Flat Surface (No Water)**
```java
Input: height = [3,3,3,3]
Output: 0
Explanation: No valleys, can't trap water.
```

### 2. **Single Peak**
```java
Input: height = [0,1,0]
Output: 0
Explanation: Too small to trap water.
```

### 3. **Increasing Heights**
```java
Input: height = [1,2,3,4,5]
Output: 0
Explanation: No valleys, water flows off.
```

### 4. **Decreasing Heights**
```java
Input: height = [5,4,3,2,1]
Output: 0
Explanation: No valleys, water flows off.
```

### 5. **Single Bar**
```java
Input: height = [5]
Output: 0
Explanation: Need at least 2 bars to trap water.
```

### 6. **Two Bars**
```java
Input: height = [3,0,3]
Output: 3
Explanation: Can trap 3 units between two bars.
```

### 7. **All Zeros**
```java
Input: height = [0,0,0,0]
Output: 0
Explanation: No elevation, no water.
```

### 8. **Large Valley**
```java
Input: height = [5,0,0,0,5]
Output: 15
Explanation: 5 + 5 + 5 = 15 units in valley.
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Using >= Instead of > When Comparing Pointers**
```java
// WRONG - uses <= instead of <
while (left <= right) {  // WRONG!
    // ...
}
```

**Why wrong**: When left == right, we're at the same position — shouldn't process it!

**Dry run failure for height=[2,0,2]:**
```
L=0, R=2: process
L=1, R=1: left == right, should stop
  But <= continues, tries to process same position
  Can cause incorrect calculations
```

**Fix**: Use strict inequality
```java
while (left < right) { ... }
```

### ❌ **MISTAKE 2: Not Updating Max Before Comparing**
```java
// WRONG - calculates water before updating max
if (height[left] < height[right]) {
    water += maxLeft - height[left];  // WRONG! maxLeft not updated yet
    maxLeft = Math.max(maxLeft, height[left]);
    left++;
}
```

**Why wrong**: Must check if current height is new max BEFORE calculating water!

**Dry run failure:**
```
height = [3, 0, 2], maxLeft = 0
L=0: height[0] = 3
  Wrong code: water += 0 - 3 = -3 (NEGATIVE!)
  Then updates: maxLeft = 3
  
Correct: First check 3 >= 0, update maxLeft = 3, then no water
```

**Fix**: Check and update max first
```java
if (height[left] >= maxLeft) {
    maxLeft = height[left];
} else {
    water += maxLeft - height[left];
}
```

### ❌ **MISTAKE 3: Moving Wrong Pointer**
```java
// WRONG - always moves left
if (height[left] < height[right]) {
    // ... process left
    left++;
} else {
    // ... process right
    left++;  // WRONG! Should move right
}
```

**Why wrong**: When processing right side, must move right pointer!

**Fix**: Move correct pointer
```java
} else {
    // ... process right
    right--;  // Correct
}
```

### ❌ **MISTAKE 4: Calculating Water When Height Equals Max**
```java
// WRONG - calculates water even when height == max
if (height[left] > maxLeft) {
    maxLeft = height[left];
}
water += maxLeft - height[left];  // WRONG! Should be in else
```

**Why wrong**: When current height is new max, no water can be trapped there!

**Dry run failure:**
```
height[left] = 5, maxLeft = 3
  height[left] > maxLeft, update maxLeft = 5
  water += 5 - 5 = 0 (wasted calculation, but not wrong)
  
But cleaner to only calculate when height < max
```

**Fix**: Only calculate in else branch
```java
if (height[left] >= maxLeft) {
    maxLeft = height[left];
} else {
    water += maxLeft - height[left];
}
```

### ❌ **MISTAKE 5: Forgetting to Handle Empty Array**
```java
// WRONG - doesn't check for empty array
public int trap(int[] height) {
    int left = 0;
    int right = height.length - 1;  // Crash if height is empty!
    // ...
}
```

**Why wrong**: Empty array causes ArrayIndexOutOfBounds!

**Fix**: Check at start
```java
if (height == null || height.length == 0) {
    return 0;
}
```

### ❌ **MISTAKE 6: Using Wrong Comparison for Side Selection**
```java
// WRONG - uses maxLeft/maxRight instead of height
if (maxLeft < maxRight) {  // WRONG!
    // ...
}
```

**Why wrong**: Should compare actual heights at pointers, not maxes!

**Dry run failure:**
```
height = [3, 0, 2], maxLeft = 3, maxRight = 0
  Wrong: maxLeft < maxRight? No (3 > 0)
  Wrong code processes right side (WRONG!)
  
Correct: height[left] < height[right]? Yes (3 > 2)
  Should process right side based on pointer heights
```

**Fix**: Compare heights at pointers
```java
if (height[left] < height[right]) { ... }
```

### ❌ **MISTAKE 7: Integer Overflow with Negative Water**
```java
// WRONG - doesn't ensure maxLeft >= height[left]
water += maxLeft - height[left];  // Could be negative!
```

**Why wrong**: If maxLeft not properly updated, could get negative water!

**Fix**: Only calculate when height < max
```java
if (height[left] < maxLeft) {
    water += maxLeft - height[left];
}
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Operation | Time | Reason |
|-----------|------|--------|
| Initialize pointers | O(1) | Few variables |
| While loop | O(n) | Each pointer moves at most n times |
| Per iteration | O(1) | Simple comparisons and arithmetic |
| **Total** | **O(n)** | Single pass through array |

### Space Complexity: **O(1)**

| Component | Space | Reason |
|-----------|-------|--------|
| Pointers (left, right) | O(1) | Two integers |
| Max trackers (maxLeft, maxRight) | O(1) | Two integers |
| Water accumulator | O(1) | One integer |
| **Total** | **O(1)** | Constant extra space |

**Why O(n) Time is Optimal:**
- Must examine each position to calculate water
- Two pointers visit each element at most once
- Can't do better than O(n)

---

## Visualization

### Complete Example Walkthrough

**Input:** `height = [0,2,0,3,1,0,1,3,2,1]`

**Visual representation:**
```
       █
   █   █ █
   █ █ █ █
   █ █ █ █ █
 █ █ █ █ █ █
━━━━━━━━━━━
0 1 2 3 4 5 6 7 8 9
```

---

**Step 0: Initialize**
```
[0, 2, 0, 3, 1, 0, 1, 3, 2, 1]
 ↑                          ↑
 L                          R

maxLeft = 0, maxRight = 0
water = 0
```

---

**Step 1: h[L]=0 < h[R]=1? Yes, process left**
```
[0, 2, 0, 3, 1, 0, 1, 3, 2, 1]
 ↑                          ↑
 L                          R

height[L] = 0 >= maxLeft (0)? Yes
  Update maxLeft = 0
  No water trapped
L++ → L=1
water = 0
```

---

**Step 2: h[L]=2 < h[R]=1? No, process right**
```
[0, 2, 0, 3, 1, 0, 1, 3, 2, 1]
    ↑                       ↑
    L                       R

height[R] = 1 >= maxRight (0)? Yes
  Update maxRight = 1
  No water trapped
R-- → R=8
water = 0
```

---

**Step 3: h[L]=2 < h[R]=2? No (equal), process right**
```
[0, 2, 0, 3, 1, 0, 1, 3, 2, 1]
    ↑                    ↑
    L                    R

height[R] = 2 >= maxRight (1)? Yes
  Update maxRight = 2
  No water trapped
R-- → R=7
water = 0
```

---

**Step 4: h[L]=2 < h[R]=3? Yes, process left**
```
[0, 2, 0, 3, 1, 0, 1, 3, 2, 1]
    ↑                 ↑
    L                 R

height[L] = 2 >= maxLeft (0)? Yes
  Update maxLeft = 2
  No water trapped
L++ → L=2
water = 0
```

---

**Step 5: h[L]=0 < h[R]=3? Yes, process left**
```
[0, 2, 0, 3, 1, 0, 1, 3, 2, 1]
       ↑              ↑
       L              R

height[L] = 0 < maxLeft (2)? Yes
  Water trapped = 2 - 0 = 2
L++ → L=3
water = 2
```

---

**Step 6: h[L]=3 < h[R]=3? No (equal), process right**
```
[0, 2, 0, 3, 1, 0, 1, 3, 2, 1]
          ↑           ↑
          L           R

height[R] = 3 >= maxRight (2)? Yes
  Update maxRight = 3
  No water trapped
R-- → R=6
water = 2
```

---

**Step 7: h[L]=3 < h[R]=1? No, process right**
```
[0, 2, 0, 3, 1, 0, 1, 3, 2, 1]
          ↑        ↑
          L        R

height[R] = 1 < maxRight (3)? Yes
  Water trapped = 3 - 1 = 2
R-- → R=5
water = 4
```

---

**Step 8: h[L]=3 < h[R]=0? No, process right**
```
[0, 2, 0, 3, 1, 0, 1, 3, 2, 1]
          ↑     ↑
          L     R

height[R] = 0 < maxRight (3)? Yes
  Water trapped = 3 - 0 = 3
R-- → R=4
water = 7
```

---

**Step 9: h[L]=3 < h[R]=1? No, process right**
```
[0, 2, 0, 3, 1, 0, 1, 3, 2, 1]
          ↑  ↑
          L  R

height[R] = 1 < maxRight (3)? Yes
  Water trapped = 3 - 1 = 2
R-- → R=3
water = 9
```

---

**Step 10: L >= R, stop**
```
L = 3, R = 3
L >= R, exit loop
```

**Final Result:** `water = 9`

### Why This Works - Visual Proof

```
Original:
       █
   █   █ █
   █ █ █ █
   █ █ █ █ █
 █ █ █ █ █ █
━━━━━━━━━━━

Water fills valleys:
       █
   █~~~█~█
   █~█~█~█
   █~█~█~█~█
 █~█~█~█~█~█
━━━━━━━━━━━

Counting:
Index 0: 0 (at edge)
Index 1: 0 (peak)
Index 2: 2 (min(2,3) - 0 = 2)
Index 3: 0 (peak)
Index 4: 2 (min(3,3) - 1 = 2)
Index 5: 3 (min(3,3) - 0 = 3)
Index 6: 2 (min(3,3) - 1 = 2)
Index 7: 0 (peak)
Index 8: 0 (near edge)
Index 9: 0 (at edge)

Total: 0+0+2+0+2+3+2+0+0+0 = 9 ✓
```

---

## Comparison of Approaches

| Approach | Time | Space | Optimal | Notes |
|----------|------|-------|---------|-------|
| Brute Force | O(n²) | O(1) | ❌ | Scan left/right each position |
| Prefix/Suffix Arrays | O(n) | O(n) | Partial | Precompute maxes |
| **Two Pointers** | **O(n)** | **O(1)** | **✅** | **Greedy processing** |
| Monotonic Stack | O(n) | O(n) | Partial | Different approach |

**Recommendation**: Always use **Two Pointers** — optimal time and space!

---

## Key Takeaways

1. **Water level determined by shorter wall** — min(maxLeft, maxRight)
2. **Process side with smaller height** — that side's max is limiting factor
3. **Track maxLeft and maxRight dynamically** — no need for arrays
4. **Two pointers from both ends** — converge to middle
5. **Update max before calculating water** — check if current bar is new max
6. **O(n) time, O(1) space optimal** — can't do better
7. **Greedy choice works** — always process limiting side

---

## Interview Tips

**What to say in an interview:**

> "This is a classic two-pointer problem. The key insight is that water trapped at any position depends on the maximum heights on both left and right sides — specifically, the minimum of those two maxes minus the current height. I can use two pointers starting from both ends, tracking the maximum height seen from each side. The critical observation is that I don't need both exact maxes simultaneously. If the left max is smaller than the right max, I know the left side is the limiting factor, so I can calculate water on the left side and move the left pointer inward. Similarly for the right side. This gives O(n) time with a single pass and O(1) space since I only need four variables."

**Key points to mention:**
1. **Water formula** — min(maxLeft, maxRight) - height[i]
2. **Two pointers from ends** — converge to middle
3. **Process smaller side** — that side's max is limiting factor
4. **Track maxes dynamically** — don't need arrays
5. **Complexity** — O(n) time (single pass), O(1) space

**If asked about alternatives:**
> "I could precompute prefix and suffix max arrays in O(n) time and O(n) space, which is also linear time but uses extra space. Or brute force scanning left and right for each position is O(n²). The two-pointer approach is optimal at O(n) time and O(1) space."

**Common Follow-ups:**
- "Prove two pointers is correct" → Show that processing smaller side with its max is sufficient
- "What if bars have varying widths?" → Different problem, needs different approach
- "How would you handle 2D rain water?" → Requires priority queue or similar structure

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Trapping Rain Water** | Hard | **Two Pointers** | **This problem** ← **1D water trapping** |
| Container With Most Water | Medium | Two Pointers | Max single container, not total water |
| Trapping Rain Water II | Hard | Priority Queue | 2D version, more complex |
| Pour Water | Medium | Simulation | Water flows down, different rules |
| Rain Water Trapper | Hard | Similar | Variation with obstacles |

**Pattern Connection**:
- **Two Pointers** — Core technique
- **Greedy** — Process limiting side
- **Max Tracking** — Dynamic max from both ends

---

## Final Pattern Label

✅ **Two Pointers (Opposite Direction with Max Tracking)**

**Remember:** Water at position i = min(maxLeft[i], maxRight[i]) - height[i]. Use two pointers from both ends. Track maxLeft and maxRight dynamically. Always process the side with smaller height (that side's max is the limiting factor). If current height >= max, update max. Otherwise, add (max - height) to water. Move the processed pointer inward. Loop while left < right. O(n) time, O(1) space. This is the optimal solution!
