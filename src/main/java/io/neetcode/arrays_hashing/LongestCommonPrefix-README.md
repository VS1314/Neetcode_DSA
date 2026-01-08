Step 1: Read the problem like a pattern-recognition exercise 🧠

Key phrases:

“find two numbers”

“sum equals target”

“return indices”

“exactly one solution”

array is NOT sorted

Step 2: Ask the killer questions 🔑
❓ Do we need order?

No → indices only

❓ Is array sorted?

No → cannot use two pointers safely

❓ Brute force?

Yes (O(n²)), but slow

❓ Can I reduce lookup time?

Yes → Hashing

Step 3: Pattern Identification ✅

This is a classic

🟢 HashMap – Complement Lookup Pattern

Also called:

“Two Sum Pattern”

“Seen + Complement Pattern”

Step 4: The mental trigger 🚦

Whenever you see:

“Find two elements such that A + B = X”

Think immediately:

If I know A, I need (X − A)

That’s the core insight.

Step 5: Walk through your code (it is CORRECT ✅)
HashMap<Integer,Integer> map = new HashMap<>();


Stores: number → index

Loop execution for [3,4,5,6], target = 7
i	nums[i]	complement	map	action
0	3	4	{}	put(3,0)
1	4	3	{3=0}	found → return

✅ Correct logic
✅ Smaller index first automatically

Step 6: Time & Space Complexity
Metric	Value
Time	O(n)
Space	O(n)

Optimal solution ✔

Step 7: Why NOT Two Pointers here? ❌

Two pointers only work when:

array is sorted OR

allowed to sort (but sorting loses indices)

Here:

indices matter

array unsorted

👉 HashMap wins

Step 8: Pattern family you should memorize 🧠
Problem	Pattern
Two Sum	HashMap – Complement
3 Sum	Sorting + Two Pointers
4 Sum	Hashing / Two Pointers
Subarray Sum = K	Prefix Sum + HashMap
Step 9: One micro improvement ✨

Your code is already clean, but this is slightly more readable:

if (map.containsKey(compliment)) {
return new int[]{map.get(compliment), i};
}
map.put(nums[i], i);


Same logic, clearer intent.

Final Pattern Label 🏷️

✅ Hashing – Complement Lookup Pattern