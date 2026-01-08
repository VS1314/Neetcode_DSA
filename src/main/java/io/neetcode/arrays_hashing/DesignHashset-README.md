This problem is not about memorizing code, it’s about understanding design choices. I’ll explain each solution one by one, with:

• Idea / pattern
• How add / remove / contains work
• Why time & space are what they are
• When to use / not use in interviews

I’ll also tell you which one is best for interviews at the end.

🧠 First: What is this problem REALLY testing?

This is NOT a trick question.

It tests:

Do you understand what a HashSet is internally?

Can you trade space vs time?

Can you handle collisions?

1️⃣ Brute Force (List-based Set)
🔹 Idea

Use a simple list and manually enforce uniqueness.

List<Integer> data;

🔹 How operations work

add(key)

Check if key already exists → contains()

If not → add to list

remove(key)

Remove element from list

contains(key)

Scan entire list

🔹 Time Complexity
Operation	Time
add	O(n)
remove	O(n)
contains	O(n)

Because ArrayList scans linearly.

🔹 Space Complexity
O(n) → number of stored elements

🔹 Pros

✅ Very easy
✅ Good for understanding baseline

🔹 Cons

❌ No hashing
❌ Slow
❌ Not scalable

🎯 Interview verdict

❌ Only acceptable as a starting explanation, not final solution.

2️⃣ Boolean Array (Direct Addressing)
🔹 Idea

Keys are limited to [0, 1_000_000], so:

👉 Use index = key

boolean[] data = new boolean[1000001];

🔹 How operations work

add(key)

data[key] = true;


remove(key)

data[key] = false;


contains(key)

return data[key];

🔹 Time Complexity
O(1) for all operations

🔹 Space Complexity
O(1,000,000)


Uses memory even if only 1 element exists.

🔹 Pros

✅ Fastest possible
✅ Very simple

🔹 Cons

❌ Wastes memory
❌ Only works when key range is known & small

🎯 Interview verdict

✅ Excellent if constraints allow
Say:

“Since key range is bounded, direct addressing is optimal.”

3️⃣ Linked List + Hashing (Separate Chaining)
🔹 Idea

This is a REAL HashSet implementation.

Use an array of buckets

Each bucket = Linked List

Handle collisions via chaining

ListNode[] set = new ListNode[10000];
index = key % 10000;

🔹 Why modulo?

Maps large keys into limited buckets.

🔹 How operations work

add(key)

Go to bucket

Traverse linked list

Add only if not found

remove(key)

Traverse

Remove node

contains(key)

Traverse list and check

🔹 Time Complexity

Average:

O(n / k)


Worst:

O(n)


Where:

n = elements

k = buckets (10000)

🔹 Space Complexity
O(k + n)

🔹 Pros

✅ Real-world hashing
✅ Memory efficient
✅ Scales well

🔹 Cons

❌ Slightly complex
❌ Worst case still linear

🎯 Interview verdict

✅ BEST answer if interviewer wants internal HashSet design

4️⃣ Binary Search Tree in Buckets
🔹 Idea

Same hashing as solution 3, but:

Each bucket is a BST instead of LinkedList

Why?
👉 Faster search inside buckets.

🔹 How operations work

Hash key → bucket index

Insert/search/delete in BST

🔹 Time Complexity

Average:

O(log (n / k))


Worst (skewed BST):

O(n)

🔹 Space Complexity
O(k + n)

🔹 Pros

✅ Faster than linked list in collisions

🔹 Cons

❌ Overkill
❌ BST balancing not guaranteed
❌ Complex

🎯 Interview verdict

⚠️ Rarely needed
Mention only if asked about optimization.

5️⃣ Bit Manipulation (BitSet)
🔹 Idea

Store presence of numbers using bits instead of booleans.

Each int stores 32 values.

int[] set = new int[31251];

🔹 How it works

For key:

index = key / 32

bit position = key % 32

add

set[index] |= (1 << bit);


remove

set[index] &= ~(1 << bit);


contains

(set[index] & (1 << bit)) != 0

🔹 Time Complexity
O(1)

🔹 Space Complexity
~31251 integers ≈ very memory efficient

🔹 Pros

✅ Extremely memory efficient
✅ Very fast

🔹 Cons

❌ Hard to understand
❌ Error-prone
❌ Only works for bounded keys

🎯 Interview verdict

🔥 Advanced answer
Great if interviewer likes low-level optimization.

🏆 FINAL COMPARISON
Solution	Interview Use
Brute Force	❌ Only baseline
Boolean Array	✅ Best if key range known
Linked List Hashing	⭐ BEST GENERAL ANSWER
BST Buckets	⚠️ Optional
Bit Manipulation	🔥 Advanced / bonus
🎤 What YOU should say in interviews
Safe & impressive answer:

“I’ll use hashing with separate chaining using linked lists to handle collisions. This gives average O(1) operations and models how real hash sets work.”

If constraints allow:

“Since the key range is bounded, direct addressing using a boolean array gives O(1) time.”

Perfect 👍
Now we’ll do this slow, visual, and practical.
I’ll explain EACH solution with a DRY RUN using the same example so your brain can see what’s happening.

We’ll use this input everywhere 👇

Operations:
add(1), add(2), contains(1), contains(3), remove(2), contains(2)

1️⃣ Brute Force (ArrayList)
Data Structure
List<Integer> data = []

▶ add(1)

data.contains(1)? ❌

add 1

data = [1]

▶ add(2)

data.contains(2)? ❌

add 2

data = [1, 2]

▶ contains(1)

scan list: [1, 2]

found → ✅ true

▶ contains(3)

scan list: [1, 2]

not found → ❌ false

▶ remove(2)

scan list

remove 2

data = [1]

▶ contains(2)

scan list: [1]

not found → ❌ false

⏱ Time intuition

Every operation scans the list → slow when list grows.

2️⃣ Boolean Array (Direct Addressing)
Data Structure
boolean[] data = new boolean[1000001];
(all false initially)

▶ add(1)
data[1] = true

▶ add(2)
data[2] = true

▶ contains(1)
data[1] → true

▶ contains(3)
data[3] → false

▶ remove(2)
data[2] = false

▶ contains(2)
data[2] → false

🧠 Key realization

No searching.
Index = key → fastest possible.

3️⃣ Linked List + Hashing (Separate Chaining)
Data Structure
ListNode[] set = new ListNode[10000]


Each index has a dummy head node.

Hash function:

index = key % 10000

▶ add(1)
index = 1 % 10000 = 1


Bucket 1 before:

[HEAD] -> null


Add node:

[HEAD] -> 1 -> null

▶ add(2)
index = 2


Bucket 2:

[HEAD] -> 2 -> null

▶ contains(1)
index = 1
Traverse bucket:
HEAD → 1 → FOUND


✅ true

▶ contains(3)
index = 3
Bucket empty


❌ false

▶ remove(2)
index = 2
Traverse:
HEAD → 2 → remove


Bucket 2 becomes:

[HEAD] -> null

▶ contains(2)
index = 2
Bucket empty


❌ false

🧠 Why this works

Hashing spreads values

Linked list handles collisions

Average case = short list = fast

4️⃣ Binary Search Tree in Buckets
Data Structure
BST[] buckets = new BST[10000]


Each bucket is a BST, not a list.

▶ add(1)
index = 1
BST at bucket[1]:
1

▶ add(2)
index = 2
BST at bucket[2]:
2

▶ contains(1)
Search BST at bucket[1]
Found 1


✅ true

▶ contains(3)
Search BST at bucket[3]
BST empty


❌ false

▶ remove(2)
Delete node 2 from BST at bucket[2]

▶ contains(2)
BST empty


❌ false

🧠 Why use BST?

If many values collide into one bucket:

Linked list → O(n)

BST → O(log n)

5️⃣ Bit Manipulation (BitSet)
Data Structure
int[] set = new int[31251]
Each int = 32 bits

▶ add(1)
index = 1 / 32 = 0
bit = 1 % 32 = 1


Before:

set[0] = 00000000


After:

set[0] = 00000010

▶ add(2)
index = 0
bit = 2

set[0] = 00000110

▶ contains(1)
set[0] & (1 << 1) ≠ 0


✅ true

▶ contains(3)
set[0] & (1 << 3) == 0


❌ false

▶ remove(2)
toggle bit 2

set[0] = 00000010

▶ contains(2)
bit not set


❌ false

🧠 Why this is powerful

32 numbers in 1 integer

Ultra memory-efficient

O(1) always

🧠 BIG PICTURE (How to recognize pattern)

When you see:

“Design HashSet without built-in libraries”

Ask yourself:

1️⃣ Is key range bounded?
→ Boolean / Bitset

2️⃣ Need real hash behavior?
→ Buckets + Linked List

3️⃣ Heavy collisions expected?
→ BST buckets

4️⃣ Simple baseline?
→ List

🔑 One-liner interview summary

“I’ll use hashing with separate chaining using linked lists to handle collisions, achieving O(1) average time for add, remove, and contains.”