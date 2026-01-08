Step 1: Understand the core requirement 🔍

“Return true if any value appears more than once”

Key phrases:

any value

appears more than once

existence check, not count

➡️ We don’t care how many times, only whether it repeats.

Step 2: Constraints & signals 🚦

Array size can be large (usually up to 10⁵+ in real versions)

Values can be anything

Only a boolean result required

➡️ Strong hint:

“Can I remember what I’ve already seen?”

Step 3: Pattern elimination ❌
Pattern	Needed?	Why
Sliding Window	❌	No range / window
Two Pointers	❌	Order irrelevant
Binary Search	❌	Array not sorted
DP	❌	No optimal substructure
Greedy	❌	No choices
Prefix Sum	❌	No sums
Recursion	❌	Simple scan
Step 4: Pattern recognition ✅
🔑 Core thought:

“While scanning, I need to check if I’ve seen this element before”

That screams:

✅ Pattern: Hashing / Set-based Lookup

Specifically:

Membership checking

Seen-before pattern

This is one of the most important DSA patterns 🔥

Step 5: Best data structure 🧠

You used:

HashMap<Integer, Integer>


That works, but it’s more than needed.

Better choice:

HashSet → because we only care about existence, not frequency

Step 6: Correct & optimal solution
class Solution {
public boolean hasDuplicate(int[] nums) {
HashSet<Integer> seen = new HashSet<>();

        for (int n : nums) {
            if (seen.contains(n)) {
                return true;
            }
            seen.add(n);
        }
        return false;
    }
}

Time & Space Complexity

Time: O(n)

Space: O(n) (worst case all unique)

Step 7: Why your commented solution is risky ⚠️
for(int i = 1; i<nums.length;i++){
if(nums[i]==nums[i-1]) return true;
}


❌ This only works if the array is sorted

Example where it fails:

[3, 1, 3]


➡️ Adjacent check won’t catch duplicates unless you sort first.

Sorting approach:

Time: O(n log n)

Space: O(1) or O(n)

Worse than hashing for this problem

Mental pattern shortcut 🧠🔥

Whenever you see:

“Contains”

“Duplicate”

“Seen before”

“Exists / present”

👉 Immediately think:

HashSet / HashMap (Hashing Pattern)

Final Pattern Label for this problem

✅ Hashing – Membership Check (Seen Pattern)