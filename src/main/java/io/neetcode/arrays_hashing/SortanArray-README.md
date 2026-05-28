# Sort an Array

## Problem Description

**Difficulty**: Medium

You are given an array of integers `nums`, sort the array in ascending order and return it.

You must solve the problem **without using any built-in functions** in **O(n log n)** time complexity and with the **smallest space complexity** possible.

## Examples

### Example 1:
```
Input: nums = [10,9,1,1,1,2,3,1]

Output: [1,1,1,1,2,3,9,10]
```

### Example 2:
```
Input: nums = [5,10,2,1,3]

Output: [1,2,3,5,10]
```

## Constraints
- 1 <= nums.length <= 50,000
- -50,000 <= nums[i] <= 50,000

---

## Pattern Recognition

**Primary Pattern**: **Sorting – In-Place – O(n log n)**

**Why This Pattern?**
- Problem explicitly requires O(n log n) time
- "Smallest space possible" → rules out O(n) space approaches
- No built-in sort allowed → need to implement manually

**Key Insight**: Among all comparison-based sorting algorithms, **Heap Sort** is the only one that guarantees both O(n log n) time **and** O(1) extra space.

**Elimination of Alternatives**:

| Algorithm | Time | Space | Verdict |
|-----------|------|-------|---------|
| Bubble / Selection Sort | O(n²) | O(1) | ❌ Too slow |
| Counting Sort | O(n) | O(range) | ❌ Too much space |
| Merge Sort | O(n log n) | O(n) | ❌ Extra space |
| Quick Sort | O(n log n) avg | O(log n) | ⚠️ Worst case O(n²) |
| **Heap Sort** | **O(n log n)** | **O(1)** | ✅ **Best** |

---

## Algorithm & Approach

### Core Insight

Heap Sort works in two phases:
1. **Build a Max Heap** from the array
2. **Repeatedly extract the maximum** element (root) and place it at the end

This sorts the array in-place without needing any extra memory.

### Heap Rules

For a node at index `i`:
- **Left child** → `2*i + 1`
- **Right child** → `2*i + 2`
- **Parent** ≥ both children (Max Heap property)

---

### Approach 1: Recursive Heapify (Clean & Interview-Ready)

**Algorithm**
```
1. Build Max Heap from the array (start from last non-leaf node)
2. For i = n-1 down to 1:
   a. Swap root (largest) with element at index i
   b. Reduce heap size by 1
   c. Heapify from root to restore heap property
3. Return sorted array
```

**Code Implementation**
```java
class Solution {
    public int[] sortArray(int[] nums) {
        int n = nums.length;

        // Phase 1: Build Max Heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }

        // Phase 2: Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            // Swap root (max) with last element
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            // Heapify the reduced heap
            heapify(nums, i, 0);
        }

        return nums;
    }

    private void heapify(int[] nums, int size, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && nums[left] > nums[largest])
            largest = left;

        if (right < size && nums[right] > nums[largest])
            largest = right;

        if (largest != i) {
            int temp = nums[i];
            nums[i] = nums[largest];
            nums[largest] = temp;

            heapify(nums, size, largest);  // Recursively fix affected subtree
        }
    }
}
```

**Example Walkthrough**

Input: `nums = [10, 9, 1, 1, 1, 2, 3, 1]`

```
Index:  0   1   2   3   4   5   6   7
Value: [10,  9,  1,  1,  1,  2,  3,  1]
```

**Phase 1: Build Max Heap**

Start from last non-leaf: `n/2 - 1 = 3`

Heapify index 3 (value=1): left child at index 7 (value=1) → no swap
```
[10, 9, 1, 1, 1, 2, 3, 1]
```

Heapify index 2 (value=1): left=5(val=2), right=6(val=3) → largest=3 at index 6 → swap
```
[10, 9, 3, 1, 1, 2, 1, 1]
```

Heapify index 1 (value=9): left=3(val=1), right=4(val=1) → 9 is largest → no swap
```
[10, 9, 3, 1, 1, 2, 1, 1]
```

Heapify index 0 (value=10): left=1(val=9), right=2(val=3) → 10 is largest → no swap

✅ **Max Heap built:** `[10, 9, 3, 1, 1, 2, 1, 1]`

**Phase 2: Extract Max & Sort**

| Iteration | Swap | Array After Swap | After Heapify |
|-----------|------|-----------------|---------------|
| i=7 | 10 ↔ index 7 | [1, 9, 3, 1, 1, 2, 1, **10**] | [9, 1, 3, 1, 1, 2, 1, **10**] |
| i=6 | 9 ↔ index 6 | [1, 1, 3, 1, 1, 2, **9**, 10] | [3, 1, 2, 1, 1, 1, **9**, 10] |
| i=5 | 3 ↔ index 5 | [1, 1, 2, 1, 1, **3**, 9, 10] | [2, 1, 1, 1, 1, **3**, 9, 10] |
| i=4 | 2 ↔ index 4 | [1, 1, 1, 1, **2**, 3, 9, 10] | no change |
| i=3 | 1 ↔ index 3 | no change | no change |
| i=2 | 1 ↔ index 2 | no change | no change |
| i=1 | done | — | — |

🎉 **Final Sorted Array:** `[1, 1, 1, 1, 2, 3, 9, 10]`

**Complexity Analysis**
- **Time Complexity**: O(n log n) — Build heap: O(n), Extract all: O(n log n)
- **Space Complexity**: O(log n) — Recursion stack for heapify (tree height)

---

### Approach 2: Iterative Heapify (TRUE O(1) SPACE)

**Core Idea**: Replace recursive `heapify` with an iterative `while` loop to completely eliminate the recursion stack.

**Why Iterative?**
- True **O(1) extra space** — no recursion overhead
- Perfectly matches "smallest space possible" constraint
- Avoids stack overflow risk on very deep trees

**Code Implementation**
```java
class Solution {
    public int[] sortArray(int[] nums) {
        int n = nums.length;

        // Phase 1: Build Max Heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }

        // Phase 2: Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            heapify(nums, i, 0);
        }

        return nums;
    }

    private void heapify(int[] nums, int size, int i) {
        while (true) {
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size && nums[left] > nums[largest])
                largest = left;

            if (right < size && nums[right] > nums[largest])
                largest = right;

            if (largest == i) break;  // Heap property satisfied, stop

            int temp = nums[i];
            nums[i] = nums[largest];
            nums[largest] = temp;

            i = largest;  // Move down and continue fixing
        }
    }
}
```

**Complexity Analysis**
- **Time Complexity**: O(n log n) — Same as recursive
- **Space Complexity**: O(1) — No recursion stack, true in-place ✅

---

## Why This Strategy?

### Problem Requirements Analysis

| Requirement | Heap Sort (Recursive) | Heap Sort (Iterative) |
|-------------|----------------------|----------------------|
| No built-in sort | ✅ | ✅ |
| O(n log n) time | ✅ | ✅ |
| Smallest space | O(log n) ⚠️ | **O(1) ✅ Best** |
| Code simplicity | ✅ | ✅ |

**Winner**: Iterative Heap Sort — satisfies all constraints.

### Why Not Others?
- **Bubble/Selection Sort** → O(n²) time, violates constraint directly
- **Counting Sort** → O(range) = O(100,001) space, too much
- **Merge Sort** → O(n) auxiliary array required
- **Quick Sort** → O(n²) worst case, unsafe for interviews

---

## Critical Edge Cases & Gotchas

### 1. **Already Sorted Array**
```java
Input: nums = [1, 2, 3, 4, 5]
Output: [1, 2, 3, 4, 5]
// Heap sort still runs O(n log n) — no short-circuit
```

### 2. **Reverse Sorted Array**
```java
Input: nums = [5, 4, 3, 2, 1]
Output: [1, 2, 3, 4, 5]
// Works correctly, no worst-case degradation unlike Quick Sort
```

### 3. **All Elements Same**
```java
Input: nums = [3, 3, 3, 3]
Output: [3, 3, 3, 3]
// No swaps occur (no element is strictly greater)
```

### 4. **Single Element**
```java
Input: nums = [42]
Output: [42]
// Loop doesn't execute (n-1 = 0 iterations)
```

### 5. **Negative Numbers**
```java
Input: nums = [-5, 2, -1, 0]
Output: [-5, -1, 0, 2]
// Comparison operators handle negatives correctly
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Wrong Starting Index for Build Heap**
```java
// WRONG - Starting from 0 wastes time (leaves have no children to heapify)
for (int i = 0; i < n; i++) {
    heapify(nums, n, i);
}
```
**Fix**: Start from last non-leaf node: `n/2 - 1`
```java
// CORRECT
for (int i = n / 2 - 1; i >= 0; i--) {
    heapify(nums, n, i);
}
```

### ❌ **MISTAKE 2: Not Reducing Heap Size After Extraction**
```java
// WRONG - Sorted elements get mixed back into the heap
heapify(nums, n, 0);  // n stays constant — wrong!
```
**Fix**: Pass current boundary `i` as heap size
```java
// CORRECT
heapify(nums, i, 0);  // i shrinks each iteration
```

### ❌ **MISTAKE 3: Missing Bounds Check on Right Child**
```java
// WRONG - ArrayIndexOutOfBoundsException when node has no right child
if (nums[right] > nums[largest])
    largest = right;
```
**Fix**: Always check bounds before accessing
```java
// CORRECT
if (right < size && nums[right] > nums[largest])
    largest = right;
```

### ❌ **MISTAKE 4: Not Continuing Heapify Downward**
```java
// WRONG - One swap may still violate heap property deeper in tree
if (largest != i) {
    swap(nums, i, largest);
    // Missing: didn't fix the subtree at 'largest'
}
```
**Fix**: Recursively (or iteratively) fix the affected subtree
```java
// CORRECT
if (largest != i) {
    swap(nums, i, largest);
    heapify(nums, size, largest);  // Continue fixing downward
}
```

---

## Complexity Analysis

**Time Complexity: O(n log n)**

| Phase | Time | Reason |
|-------|------|--------|
| Build Max Heap | O(n) | Bottom-up heapify is O(n) by summation proof |
| Extract all maxima | O(n log n) | n extractions × O(log n) each |
| **Total** | **O(n log n)** | Dominated by extraction phase |

**Space Complexity**

| Variant | Space | Reason |
|---------|-------|--------|
| Recursive heapify | O(log n) | Recursion stack = tree height |
| **Iterative heapify** | **O(1)** | No extra memory used ✅ |

---

## Comparison of Approaches

| Approach | Time | Space | Worst Case | When to Use |
|----------|------|-------|------------|-------------|
| **Heap Sort (Iterative)** | O(n log n) | **O(1) ✅** | O(n log n) ✅ | **Default ✅** |
| Heap Sort (Recursive) | O(n log n) | O(log n) | O(n log n) | Fine for most cases |
| Merge Sort | O(n log n) | O(n) | O(n log n) | When stable sort needed |
| Quick Sort | O(n log n) avg | O(log n) | O(n²) ❌ | Not safe here |
| Counting Sort | O(n) | O(range) ❌ | O(n) | When space is not a concern |

---

## Key Takeaways

1. **O(n log n) + O(1) space → Heap Sort** is the only matching algorithm
2. **Build heap from `n/2-1` down to 0** — leaves are already valid heaps
3. **Extraction phase**: swap root → shrink size → heapify
4. **Iterative heapify** removes O(log n) stack factor → true O(1) space
5. **Max Heap property**: parent ≥ both children at all times
6. **Always reduce heap size** after each extraction step

---

## Interview Tips

**What to say in an interview:**

> "The constraints require O(n log n) time and minimum space. This immediately points to Heap Sort — the only comparison-based algorithm guaranteeing O(n log n) with O(1) extra space. I'll build a max heap first, then repeatedly extract the maximum to sort in-place."

**Key points to mention:**
1. **Why Heap Sort** — eliminate alternatives by constraints
2. **Two phases** — build heap, then extract
3. **In-place** — no auxiliary array needed
4. **Guaranteed worst case** — unlike Quick Sort

**If asked about iterative:**
> "I can make heapify iterative using a while loop, which eliminates the O(log n) recursion stack and achieves true O(1) extra space."

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Sort an Array** | Medium | **Heap Sort** | **This problem** ← |
| Kth Largest Element in Array | Medium | Heap / Quick Select | Partial sort |
| Top K Frequent Elements | Medium | Heap | Frequency + sort |
| Sort Colors | Medium | Three-way partition | Only 3 distinct values |
| Merge K Sorted Lists | Hard | Heap / Merge | External merge |

**Pattern Progression:**
1. **Sorting fundamentals** (this problem) — Foundation
2. **Heap operations** — Priority queue problems
3. **Top K elements** — Partial heap extraction
4. **External sorting** — Large data with limited memory

---

## Final Pattern Label

✅ **Sorting – Heap Sort (In-Place) – O(n log n) Time, O(1) Space**

**Remember:** When you see "O(n log n) + smallest space" → **Heap Sort** is the only answer!
👉 Why other approaches are rejected
👉 Best solution
👉 Dry run

🔍 Step 1: Read the constraints (MOST IMPORTANT)

You must solve without built-in sort
Time: O(n log n)
Smallest space possible
n ≤ 50,000

This line already eliminates many options.

🧠 Step 2: Pattern recognition

This is a SORTING problem.
Now ask these 3 key interview questions:

Q1️⃣ Can I use brute force (bubble / selection)?

Bubble sort → O(n²) ❌

Selection sort → O(n²) ❌
➡️ Rejected

Q2️⃣ Can I use Counting Sort?

Values range: -50,000 to 50,000

Range size = 100,001

Space = O(range) → large but allowed

Time = O(n)

✅ Possible
❌ But interviewer says smallest space possible → not ideal

Q3️⃣ Comparison-based optimal sorting?

Only 3 algorithms give guaranteed O(n log n):

Algorithm	Time	Space
Merge Sort	O(n log n)	O(n)
Heap Sort	O(n log n)	O(1)
Quick Sort	O(n log n) avg	O(n) worst

👉 Heap Sort wins (smallest space)

✅ FINAL PATTERN

Pattern: Sorting → In-place → O(n log n)
Algorithm: Heap Sort

This is EXACTLY what NeetCode expects.

🧩 Step 3: Best Solution – Heap Sort
Why Heap Sort?

Guaranteed O(n log n)

In-place (O(1) extra space)

No recursion stack (unlike merge / quick)

✅ Code (Clean & Interview-Ready)
class Solution {
public int[] sortArray(int[] nums) {
int n = nums.length;

        // 1. Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(nums, n, i);
        }

        // 2. Extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            // swap root with last element
            int temp = nums[0];
            nums[0] = nums[i];
            nums[i] = temp;

            // heapify reduced heap
            heapify(nums, i, 0);
        }

        return nums;
    }

    private void heapify(int[] nums, int size, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && nums[left] > nums[largest])
            largest = left;

        if (right < size && nums[right] > nums[largest])
            largest = right;

        if (largest != i) {
            int temp = nums[i];
            nums[i] = nums[largest];
            nums[largest] = temp;

            heapify(nums, size, largest);
        }
    }
}

🔁 Step 4: Dry Run (VERY IMPORTANT)
Input
[10, 9, 1, 1, 1, 2, 3, 1]

🔨 Step 1: Build Max Heap

Heap becomes:

[10, 9, 3, 1, 1, 2, 1, 1]


(10 is max at root)

🔄 Step 2: Extract max one by one
Swap 10 with last:
[1, 9, 3, 1, 1, 2, 1, 10]


Heapify →

[9, 2, 3, 1, 1, 1, 1, 10]

Swap 9:
[1, 2, 3, 1, 1, 1, 9, 10]


Heapify →

[3, 2, 1, 1, 1, 1, 9, 10]


Continue…

✅ Final Sorted Array
[1,1,1,1,2,3,9,10]

🧠 Interview Thought Process (THIS IS GOLD)

When you see:

“Sort array without built-in”
“O(n log n)”
“Smallest space possible”

Immediately think:

SORTING
↓
O(n log n)
↓
IN-PLACE
↓
HEAP SORT

📌 Summary Table
Approach	Time	Space	Verdict
Bubble / Selection	O(n²)	O(1)	❌
Counting Sort	O(n)	O(range)	⚠️
Merge Sort	O(n log n)	O(n)	❌
Quick Sort	O(n log n) avg	O(n)	⚠️
Heap Sort	O(n log n)	O(1)	✅ BEST

If you want, next I can:

🔥 Teach Counting Sort version (very easy)

🧠 Train you to recognize sorting algorithm in 10 seconds

🎯 Move to Top-K / Kth largest → Heap pattern

Just say the word 👍

do a complete dry run i cant understand

Perfect 👍
Let’s slow everything down and do a true line-by-line dry run of Heap Sort.
No theory fluff. Just what changes in the array at every step.

We’ll use your exact input:

nums = [10, 9, 1, 1, 1, 2, 3, 1]


Index positions:

index:  0   1   2   3   4   5   6   7
value: [10,  9,  1,  1,  1,  2,  3,  1]

🧠 RULES OF MAX HEAP (VERY IMPORTANT)

For index i:

Left child → 2*i + 1

Right child → 2*i + 2

Parent ≥ children

STEP 1️⃣ BUILD MAX HEAP

We start heapifying from last non-leaf node:

n = 8
last non-leaf = n/2 - 1 = 3


So we heapify indices: 3 → 2 → 1 → 0

🔹 Heapify index = 3
index 3 = 1
left = 7 → 1
right = 8 → out of bounds


Compare:

1 vs 1 → OK


No swap.

[10, 9, 1, 1, 1, 2, 3, 1]

🔹 Heapify index = 2
index 2 = 1
left = 5 → 2
right = 6 → 3


Largest = 3 (index 6)

Swap index 2 and 6
[10, 9, 3, 1, 1, 2, 1, 1]


Heapify again at index 6 → leaf → stop.

🔹 Heapify index = 1
index 1 = 9
left = 3 → 1
right = 4 → 1


9 is already largest → no swap.

[10, 9, 3, 1, 1, 2, 1, 1]

🔹 Heapify index = 0
index 0 = 10
left = 1 → 9
right = 2 → 3


10 is largest → no swap.

✅ MAX HEAP BUILT
[10, 9, 3, 1, 1, 2, 1, 1]

STEP 2️⃣ EXTRACT MAX & SORT

Now we repeatedly:

Swap root with last

Reduce heap size

Heapify again

🔁 Iteration 1 (i = 7)
Swap index 0 & 7
[1, 9, 3, 1, 1, 2, 1, 10]


Heap size = 7 (ignore last element)

Heapify index 0
value = 1
left = 9
right = 3


Largest = 9 (index 1)

Swap:

[9, 1, 3, 1, 1, 2, 1, 10]


Heapify index 1:

left = 1
right = 1 → no swap

🔁 Iteration 2 (i = 6)
Swap index 0 & 6
[1, 1, 3, 1, 1, 2, 9, 10]


Heap size = 6

Heapify index 0

Largest = 3 (index 2)

Swap:

[3, 1, 1, 1, 1, 2, 9, 10]


Heapify index 2:

left = 2 → swap

[3, 1, 2, 1, 1, 1, 9, 10]

🔁 Iteration 3 (i = 5)
Swap index 0 & 5
[1, 1, 2, 1, 1, 3, 9, 10]


Heap size = 5

Heapify index 0

Largest = 2

Swap:

[2, 1, 1, 1, 1, 3, 9, 10]

🔁 Iteration 4 (i = 4)
Swap index 0 & 4
[1, 1, 1, 1, 2, 3, 9, 10]


Heap size = 4
Heap already valid → no swap

🔁 Iteration 5 (i = 3)

Swap:

[1, 1, 1, 1, 2, 3, 9, 10]


(no change)

🔁 Iteration 6 (i = 2)

Swap:

[1, 1, 1, 1, 2, 3, 9, 10]

🔁 Iteration 7 (i = 1)

Done.

🎉 FINAL SORTED ARRAY
[1, 1, 1, 1, 2, 3, 9, 10]

🧠 ONE-LINE INTUITION (VERY IMPORTANT)

Heap Sort = Put biggest number at the end again and again

🔑 MEMORY TRICK

Think of heap as:

MAX at top → move to correct position → fix remaining

