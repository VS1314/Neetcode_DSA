This is a classic “identify-the-sorting-pattern” problem, so let’s do this in the exact way you asked earlier:

👉 Pattern identification FIRST
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

