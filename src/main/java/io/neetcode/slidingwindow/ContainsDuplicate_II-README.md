Great — this is a classic sliding window + hash set / hash map problem.
I’ll break it down cleanly and connect it to patterns you’re already using (like two pointers).

🧠 Step 1: Recognize the PATTERN
Keywords

abs(i - j) <= k

same value (nums[i] == nums[j])

indices distance constraint

👉 This screams Sliding Window of size k

🧩 Core Insight

You don’t need to compare every pair.

You only need to ask:

Does the current number already exist in the last k indices?

If yes → duplicate within distance k → return true

🪟 Step 2: Sliding Window Idea

Maintain a window of at most k elements

Use a HashSet to check duplicates in O(1)

Slide the window as you move forward

🧠 Why HashSet Works

We only care if a value exists inside the window

Order doesn’t matter

Fast lookup → O(1)

🧪 Dry Run (Example 1)
nums = [1,2,3,1], k = 3

i	nums[i]	Window (Set)	Action
0	1	{}	add 1
1	2	{1}	add 2
2	3	{1,2}	add 3
3	1	{1,2,3}	❗ 1 exists → true
🧪 Dry Run (Example 2)
nums = [2,1,2], k = 1

i	nums[i]	Window	Action
0	2	{}	add 2
1	1	{2}	add 1, remove 2
2	2	{1}	add 2 (no duplicate)

➡️ Return false

🧠 Sliding Window Logic (Important)

At index i:

Window contains elements from indices [i - k, i - 1]

If size exceeds k, remove nums[i - k]

✅ Optimal Solution (HashSet + Sliding Window)
class Solution {
public boolean containsNearbyDuplicate(int[] nums, int k) {
Set<Integer> window = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            if (window.contains(nums[i])) {
                return true;
            }

            window.add(nums[i]);

            if (window.size() > k) {
                window.remove(nums[i - k]);
            }
        }
        return false;
    }
}

⏱ Complexity
Metric	Value
Time	O(n)
Space	O(k)
🧠 Alternate Approach (HashMap – Index Tracking)

Store last seen index instead of sliding window.

class Solution {
public boolean containsNearbyDuplicate(int[] nums, int k) {
Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && i - map.get(nums[i]) <= k) {
                return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }
}

When to use this?

When distance logic is explicit

When sliding window feels less intuitive

🔥 Pattern Summary (Memorize This)
Clue	Pattern
abs(i - j) <= k	Sliding Window
Duplicate check	HashSet
Index tracking	HashMap
🚀 Similar Problems (Same Pattern)

Longest Substring Without Repeating Characters

Contains Duplicate III

Maximum Sum Subarray of Size K

Permutation in String

Find All Anagrams in a String