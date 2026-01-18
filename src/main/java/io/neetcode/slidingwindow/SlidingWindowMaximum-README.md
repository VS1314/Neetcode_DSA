This is a classic sliding window + data structure problem.
I’ll explain it slowly, pattern-first, then show why heap works, and finally the best solution (deque) which interviews expect.

1️⃣ How to recognize the pattern
Keywords that matter

“sliding window of size k”

“maximum at each step”

Window moves one step right

👉 This screams: Sliding Window + Fast Max Query

2️⃣ Brute force (why it fails)

For every window of size k:

Scan k elements

Find max

⏱️ Time: O(n * k) → too slow when n is big

3️⃣ Heap approach (O(n log n)) – hinted in the problem
Idea

Use a max heap that stores:

(value, index)


Why index?
👉 To know whether the element is still inside the window

Heap Logic (step-by-step)
Window range at index i
valid indices = [i - k + 1, i]

Steps

Add (nums[i], i) to heap

Remove heap top if its index is outside the window

Heap top is the max for current window

Heap Code (VALID but not optimal)
class Solution {
public int[] maxSlidingWindow(int[] nums, int k) {
int n = nums.length;
int[] res = new int[n - k + 1];

        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> b[0] - a[0]   // max heap
        );

        int idx = 0;

        for (int i = 0; i < n; i++) {
            pq.offer(new int[]{nums[i], i});

            // remove elements outside window
            while (pq.peek()[1] <= i - k) {
                pq.poll();
            }

            if (i >= k - 1) {
                res[idx++] = pq.peek()[0];
            }
        }
        return res;
    }
}

Complexity

Time: O(n log n)

Space: O(n)

✔️ Passes
❌ Not optimal

4️⃣ Why Heap is NOT optimal

Because:

We only care about monotonic max

Heap keeps useless elements

Each insert/remove costs log n

👉 There is a way to do this in O(n)

5️⃣ Optimal Solution: Monotonic Deque (O(n))

🔥 THIS IS THE INTERVIEW GOLD SOLUTION

Core Idea (VERY IMPORTANT)

Use a Deque of indices such that:

Elements are in decreasing order

Front always contains the maximum

Why indices?

To:

Check window validity

Access values via nums[index]

Rules of the Deque
While adding new element nums[i]

Remove all elements from back smaller than nums[i]

They can never be max again

Add i to the back

Before taking max

Remove front if it is outside the window

Max = nums[deque.front()]
Visual Example
nums = [1,2,1,0,4,2,6], k = 3


Deque holds indices (values decreasing):

Window [1,2,1] → deque = [2]
Window [2,1,0] → deque = [2,1,0]
Window [1,0,4] → deque = [4]
...

✅ Optimal O(n) Code
class Solution {
public int[] maxSlidingWindow(int[] nums, int k) {
int n = nums.length;
int[] res = new int[n - k + 1];
Deque<Integer> dq = new ArrayDeque<>();
int idx = 0;

        for (int i = 0; i < n; i++) {

            // Remove indices out of window
            if (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            // Remove smaller elements from back
            while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) {
                dq.pollLast();
            }

            dq.offerLast(i);

            // Start recording answers after first window
            if (i >= k - 1) {
                res[idx++] = nums[dq.peekFirst()];
            }
        }
        return res;
    }
}

6️⃣ Why Deque is O(n)

Each index:

Added once

Removed once

👉 Total operations = 2n

7️⃣ Pattern you must remember
This problem uses:
Sliding Window + Monotonic Deque (Decreasing)

8️⃣ Similar Problems (Same Pattern)

Sliding Window Maximum

Minimum Window Maximum

Max of subarrays

Largest Rectangle in Histogram

Daily Temperatures

9️⃣ Interview one-liner

We maintain a monotonic decreasing deque of indices so that the front always represents the maximum element of the current window.

If you want next:

🔍 Full dry run with deque states

❌ Common mistakes

🧠 How to recognize deque problems instantly

Just say 👍

i didnot understand explain line by line

Perfect 👍
Let’s go VERY slowly, line-by-line, like we’re executing the code with our brain.

We’ll use your example so it becomes intuitive.

Problem Recap (1 line)

We need the maximum of every window of size k while sliding from left → right.

Code we are explaining (deque solution)
class Solution {
public int[] maxSlidingWindow(int[] nums, int k) {
int n = nums.length;
int[] res = new int[n - k + 1];
Deque<Integer> dq = new ArrayDeque<>();
int idx = 0;

        for (int i = 0; i < n; i++) {

            if (!dq.isEmpty() && dq.peekFirst() <= i - k) {
                dq.pollFirst();
            }

            while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) {
                dq.pollLast();
            }

            dq.offerLast(i);

            if (i >= k - 1) {
                res[idx++] = nums[dq.peekFirst()];
            }
        }
        return res;
    }
}

🔑 VERY IMPORTANT RULE (memorize this)

👉 Deque stores INDICES, not values

Why?

To check if index is outside window

To get value using nums[index]

Step 0️⃣ Initial Setup
int n = nums.length;
int[] res = new int[n - k + 1];
Deque<Integer> dq = new ArrayDeque<>();
int idx = 0;

What each variable does
Variable	Meaning
res	answer array
dq	keeps indices of useful elements
idx	index for result array
Now let’s DRY RUN with example
nums = [1,2,1,0,4,2,6]
k = 3

🔁 LOOP STARTS
🔹 i = 0 (value = 1)
❓ First if
if (!dq.isEmpty() && dq.peekFirst() <= i - k)


dq empty ❌

skip

❓ while loop
while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i])


dq empty ❌

skip

Add index
dq.offerLast(i);


Deque = [0] → (value = 1)

Result condition
if (i >= k - 1)


0 >= 2 ❌ → no result yet

🔹 i = 1 (value = 2)
Remove out-of-window?
dq.peekFirst() <= 1 - 3 → 0 <= -2 ❌

Remove smaller from back
nums[dq.peekLast()] < nums[i]
nums[0] < 2 → 1 < 2 ✅


👉 Remove index 0

Deque = []

Add index
dq.offerLast(1);


Deque = [1] (value = 2)

Result?

1 >= 2 ❌

🔹 i = 2 (value = 1)
Remove out-of-window?
1 <= 2 - 3 → 1 <= -1 ❌

Remove smaller?
nums[1] < 1 → 2 < 1 ❌

Add index

Deque = [1, 2]
(values = [2,1])

Save result
res[0] = nums[dq.peekFirst()] = nums[1] = 2


✔️ Window [1,2,1] → max = 2

🔹 i = 3 (value = 0)
Remove out-of-window?
dq.peekFirst() = 1
1 <= 3 - 3 → 1 <= 0 ❌

Remove smaller?
nums[2] < 0 → 1 < 0 ❌

Add index

Deque = [1,2,3]
(values = [2,1,0])

Save result
res[1] = nums[1] = 2


✔️ Window [2,1,0] → max = 2

🔹 i = 4 (value = 4)
Remove out-of-window
dq.peekFirst() = 1
1 <= 4 - 3 → 1 <= 1 ✅


👉 Remove index 1

Deque = [2,3]

Remove smaller from back
nums[3] < 4 → 0 < 4 ✅ remove
nums[2] < 4 → 1 < 4 ✅ remove


Deque = []

Add index

Deque = [4]

Save result
res[2] = nums[4] = 4


✔️ Window [1,0,4] → max = 4

🔹 i = 5 (value = 2)
Out-of-window?
4 <= 5 - 3 → 4 <= 2 ❌

Remove smaller?
nums[4] < 2 → 4 < 2 ❌

Add index

Deque = [4,5]

Save result
res[3] = nums[4] = 4


✔️ Window [0,4,2] → max = 4

🔹 i = 6 (value = 6)
Remove out-of-window?
4 <= 6 - 3 → 4 <= 3 ❌

Remove smaller
nums[5] < 6 → 2 < 6 ✅
nums[4] < 6 → 4 < 6 ✅


Deque = []

Add index

Deque = [6]

Save result
res[4] = nums[6] = 6


✔️ Window [4,2,6] → max = 6

✅ Final Output
[2, 2, 4, 4, 6]

🧠 ONE SENTENCE SUMMARY

We keep a deque of indices in decreasing order so the front always holds the maximum of the current window.

If you want next:

❌ Why removing smaller elements is SAFE

🧠 How to identify deque problems instantly

🔁 Dry run again with another example

Just tell me 😊

still not clear explain every line with code as well as all the iteration so every thing is clear

Got it 👍
Let’s reset completely and do this from absolute zero, with:

WHY this problem needs a special approach

WHY we use Deque

WHAT each line means

WHAT happens in EVERY iteration

WHY each removal is correct

I’ll be very slow and repetitive on purpose.

0️⃣ Problem in the simplest words

You are given:

nums = [1,2,1,0,4,2,6]
k = 3


You slide a window of size 3 and at each position, you must output the maximum number inside that window.

Windows:
[1,2,1] → 2
[2,1,0] → 2
[1,0,4] → 4
[0,4,2] → 4
[4,2,6] → 6

1️⃣ Why brute force is BAD

Brute force:

For each window → scan k elements → find max

Time = O(n * k) ❌

We want:

O(n)


So we need:

A way to know the max instantly when window moves

2️⃣ Why Deque (double-ended queue)?

Deque allows:

Remove from front

Remove from back

Add to back

All in O(1) time.

3️⃣ What does the Deque store?

⚠️ THIS IS CRITICAL

Deque stores INDICES, NOT VALUES

Why indices?

To check if an element is out of window

To access value using nums[index]

4️⃣ Core idea (MOST IMPORTANT)

We maintain a deque such that:

👉 Values in deque are ALWAYS in DECREASING order

Example:

Indices: [4,5]
Values:  [4,2]


So:

Front = maximum

Smaller useless elements are removed

5️⃣ Code (we explain line-by-line)
class Solution {
public int[] maxSlidingWindow(int[] nums, int k) {


👉 Method definition

int n = nums.length;


👉 Length of array

int[] res = new int[n - k + 1];


👉 Number of windows = n - k + 1
We store answers here

Deque<Integer> dq = new ArrayDeque<>();


👉 Deque stores indices of useful elements

int idx = 0;


👉 Pointer for result array

6️⃣ Main loop (heart of solution)
for (int i = 0; i < n; i++) {


i = current index entering the window

🔥 LINE 1 — Remove out-of-window elements
if (!dq.isEmpty() && dq.peekFirst() <= i - k) {
dq.pollFirst();
}

Meaning:

i - k = last index that is OUTSIDE window

If front index is outside → remove it

WHY front?

Because front stores the oldest & largest candidate

🔥 LINE 2 — Remove smaller elements from back
while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) {
dq.pollLast();
}

Meaning:

If current element nums[i] is bigger than elements at back,
then those elements are useless forever.

WHY useless?

Because:

They are smaller

They are older

They can NEVER be max again

🔥 LINE 3 — Add current index
dq.offerLast(i);


Now deque remains:

Decreasing order

Only useful candidates

🔥 LINE 4 — Save result when window is ready
if (i >= k - 1) {
res[idx++] = nums[dq.peekFirst()];
}


Window becomes valid at i = k - 1

Front of deque = maximum

Save it

7️⃣ FULL DRY RUN (EVERY ITERATION)
Initial:
nums = [1,2,1,0,4,2,6]
k = 3
dq = []
res = []

🟢 i = 0 → value = 1

Remove out-of-window ❌

Remove smaller ❌

Add index 0

dq = [0] → values [1]


i < 2 → no result

🟢 i = 1 → value = 2

Out-of-window ❌

Remove smaller:

nums[0] < 2 → remove 0

dq = []


Add index 1

dq = [1] → values [2]


No result yet

🟢 i = 2 → value = 1

Out-of-window ❌

Remove smaller ❌

Add index 2

dq = [1,2] → values [2,1]


Window ready ✅

res[0] = nums[1] = 2

🟢 i = 3 → value = 0

Out-of-window ❌

Remove smaller ❌

Add index 3

dq = [1,2,3] → values [2,1,0]

res[1] = nums[1] = 2

🟢 i = 4 → value = 4

Out-of-window:

dq.peekFirst() = 1

1 <= 4 - 3 → remove 1

dq = [2,3]


Remove smaller:

nums[3] < 4 → remove

nums[2] < 4 → remove

dq = []


Add index 4

dq = [4] → values [4]

res[2] = 4

🟢 i = 5 → value = 2

Out-of-window ❌

Remove smaller ❌

Add index 5

dq = [4,5] → values [4,2]

res[3] = 4

🟢 i = 6 → value = 6

Out-of-window ❌

Remove smaller:

nums[5] < 6 → remove

nums[4] < 6 → remove

dq = []


Add index 6

dq = [6] → values [6]

res[4] = 6

8️⃣ Final Answer
[2,2,4,4,6]

9️⃣ ONE-LINE LOGIC (INTERVIEW GOLD)

We maintain a deque of indices in decreasing order so the front always contains the maximum of the current sliding window.