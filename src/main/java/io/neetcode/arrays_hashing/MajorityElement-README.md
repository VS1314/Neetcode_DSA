1️⃣ Pattern Identification (most important step)
Keywords in the problem:

“remove element in-place”

“order does not matter”

“return k (count of remaining elements)”

“do not use extra space”

🚦 These keywords immediately point to:

✅ Two Pointers – Slow & Fast Pointer Pattern
2️⃣ Why Two Pointers?

You need to:

Scan the array once

Overwrite unwanted elements

Keep valid elements at the front

This is classic:

read pointer  → scans array
write pointer → writes valid values

3️⃣ Pattern Name 🏷️

This problem uses:

🔹 Two Pointer (Overwrite / Filtering Pattern)

Also known as:

In-place array modification

Slow–Fast pointer

Read–Write pointer technique

4️⃣ How your code fits the pattern

Your code:

int k = 0;
for (int i = 0; i < nums.length; i++) {
if (nums[i] != val) {
nums[k] = nums[i];
k++;
}
}
return k;

Pointer roles:
Pointer	Purpose
i	Fast pointer (reads every element)
k	Slow pointer (writes only valid elements)
5️⃣ Step-by-step dry run 🧪
Input:
nums = [1,1,2,3,4]
val = 1

i	nums[i]	nums[i] != val?	Action	nums	k
0	1	❌	skip	[1,1,2,3,4]	0
1	1	❌	skip	[1,1,2,3,4]	0
2	2	✅	nums[0]=2	[2,1,2,3,4]	1
3	3	✅	nums[1]=3	[2,3,2,3,4]	2
4	4	✅	nums[2]=4	[2,3,4,3,4]	3

👉 First k=3 elements are valid:

[2,3,4]


✔ Order beyond k doesn’t matter

6️⃣ Why this is optimal
⏱ Time Complexity
O(n)


(one pass)

📦 Space Complexity
O(1)


(in-place)

✔ Best possible solution

7️⃣ When should THIS pattern trigger in your brain?

Whenever you see:

“remove in-place”

“filter elements”

“don’t care about order”

“return length”

👉 Two Pointer (Overwrite) Pattern

8️⃣ Pattern cheat-sheet mapping 🧠
Problem Type	Pattern
Remove element	Two Pointer
Move zeros	Two Pointer
Remove duplicates	Two Pointer
Filter array in-place	Two Pointer
🎯 Interview-ready explanation

“I used a two-pointer approach where one pointer scans the array and the other overwrites valid elements, allowing in-place removal in linear time and constant space.”