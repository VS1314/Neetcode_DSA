Excellent choice. This is the canonical Sliding Window problem.
I’ll explain it the same structured way you asked earlier:
👉 how to identify the pattern, constraints → DS → logic → code → traps.

🧠 Step 1: Pattern Identification
Keywords in the problem

Substring → contiguous

Without repeating characters

Longest

💡 This screams:

Sliding Window + Hashing

🧩 Why Sliding Window?

Brute force:

Fix i

Extend j until duplicate

Reset → O(n²) ❌

But notice:

Once a duplicate appears, we don’t restart

We only move the left pointer

👉 That’s the sliding window invariant.

🧠 Window Definition

We maintain a window [l … r] such that:

All characters inside the window are unique

🗂️ Data Structure Choice
Need	DS
Fast duplicate check	HashSet / HashMap
ASCII chars only	boolean[256] (optional)

We’ll start with HashSet (clean & intuitive).

🔁 Core Logic (Mental Model)

r expands the window

If s[r] is not in set

Add it

Update max length

If s[r] already exists

Shrink window from left

Remove chars until duplicate is gone

🧪 Dry Run (Example 1)
s = "z x y z x y z"
0 1 2 3 4 5 6

l	r	window	set	max
0	0	z	{z}	1
0	1	zx	{z,x}	2
0	2	zxy	{z,x,y}	3
0	3	zxyz ❌	duplicate z
1	3	xyz	{x,y,z}	3

➡️ Continue → max remains 3

✅ Java Solution (Sliding Window + HashSet)
class Solution {
public int lengthOfLongestSubstring(String s) {
Set<Character> set = new HashSet<>();
int l = 0;
int maxLen = 0;

        for (int r = 0; r < s.length(); r++) {
            while (set.contains(s.charAt(r))) {
                set.remove(s.charAt(l));
                l++;
            }
            set.add(s.charAt(r));
            maxLen = Math.max(maxLen, r - l + 1);
        }
        return maxLen;
    }
}

⏱ Complexity
Metric	Value
Time	O(n)
Space	O(m) (unique chars)

Each character enters & leaves the set once.

🚀 Optimized Version (HashMap – Jump Left Pointer)

Instead of moving l one-by-one, we jump it.

Key idea:

Store last index of each character.

class Solution {
public int lengthOfLongestSubstring(String s) {
Map<Character, Integer> map = new HashMap<>();
int l = 0, maxLen = 0;

        for (int r = 0; r < s.length(); r++) {
            char c = s.charAt(r);

            if (map.containsKey(c)) {
                l = Math.max(l, map.get(c) + 1);
            }

            map.put(c, r);
            maxLen = Math.max(maxLen, r - l + 1);
        }
        return maxLen;
    }
}


✔ Faster in practice
✔ Cleaner reasoning in interviews

❌ Common Mistakes (VERY IMPORTANT)

❌ Resetting l = r + 1
❌ Forgetting Math.max(l, lastIndex + 1)
❌ Using nested loops
❌ Counting frequency instead of uniqueness

🧠 Pattern Summary (Memorize This)
Feature	Meaning
Substring	Sliding window
No duplicates	HashSet / HashMap
Longest	Expand right, shrink left
O(n)	Each char processed once
🔥 Similar Problems (Same Pattern)

Longest Substring With At Most K Distinct Characters

Longest Repeating Character Replacement

Minimum Window Substring

Permutation in String

Fruit Into Baskets