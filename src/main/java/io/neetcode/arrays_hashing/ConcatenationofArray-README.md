Step 1: Read the problem → extract signals 🔍
What is asked?

Create a new array ans of size 2n

First half = original array

Second half = original array again

➡️ No condition, no comparison, no optimization trick

Step 2: Look at constraints 🚦

1 <= nums.length <= 1000

Values are small

No time/space pressure mentioned

Output array must be size 2n

➡️ This already tells you:

No need for clever algorithms

Extra space is allowed (required)

Step 3: Identify what is not needed ❌

Ask yourself these questions:

Question	Answer	Why
Sorting needed?	❌	Order must remain same
Two pointers?	❌	No shrinking/expanding window
Sliding window?	❌	No subarray / range
HashMap?	❌	No counting / lookup
Stack?	❌	No LIFO behavior
Greedy?	❌	No decision-making
Binary search?	❌	No sorted property

➡️ This eliminates 90% of patterns

Step 4: What pattern fits naturally? ✅
🔑 Core observation

“Take input as-is and place it into output in a fixed position”

This is a direct mapping / construction problem.

✅ Correct Pattern Identification
Pattern: Array Construction / Simulation

Sometimes also called:

Direct Construction

Index Mapping

Simulation

👉 These problems:

Don’t need optimization

Just require correct indexing

Often appear as easy problems

Step 5: How your brain should classify this in future 🧠

When you see:

“Create a new array”

“ans[i] = …”

“for all i”

No conditions / decisions

➡️ Immediately think:

Construction / Simulation

Step 6: Correct logic (your loop issue fixed)

Your commented loop had a mistake.
Here is the correct and clean solution:

class Solution {
public int[] getConcatenation(int[] nums) {
int n = nums.length;
int[] ans = new int[2 * n];

        for (int i = 0; i < n; i++) {
            ans[i] = nums[i];
            ans[i + n] = nums[i];
        }
        return ans;
    }
}

Time & Space Complexity

Time: O(n)

Space: O(2n) → O(n)

Mental shortcut to remember 🔥

If the problem says “Create / Build / Return a new array”
and no optimization hints are given
→ Construction pattern