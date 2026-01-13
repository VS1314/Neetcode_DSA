Perfect 👍
Let’s do this the same structured way you asked earlier:
👉 Pattern → Why → Best solution → Dry run

🔍 Step 1: Identify the PATTERN
Key words in the problem

“reverse”

“in-place”

“O(1) extra memory”

🚨 These immediately scream:

🔑 Two Pointers Pattern

🧠 Why Two Pointers?

You want to:

Swap first and last

Move inward

No extra array allowed

This means:

left pointer  → start
right pointer → end


Swap until they cross.

🧩 Pattern Name
✅ Two Pointers – Opposite Direction

Used when:

Reversing

Palindrome checks

Array/string modifications in-place

✅ Best Solution (Optimal)
class Solution {
public void reverseString(char[] s) {
int left = 0;
int right = s.length - 1;

        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;

            left++;
            right--;
        }
    }
}

⏱ Time Complexity

O(n)

🧠 Space Complexity

O(1) (only temp variable)

🔬 Complete DRY RUN
Input
s = ["n","e","e","t"]

Initial
left = 0 → 'n'
right = 3 → 't'

🔁 Iteration 1

Swap:

"n" ↔ "t"


Array becomes:

["t","e","e","n"]


Move pointers:

left = 1
right = 2

🔁 Iteration 2
left < right → 1 < 2 ✔


Swap:

"e" ↔ "e"  (no visible change)


Array:

["t","e","e","n"]


Move pointers:

left = 2
right = 1

❌ Stop Condition
left >= right → STOP


✅ Final Output:

["t","e","e","n"]

🔁 Example 2 (Palindrome Case)

Input:

["r","a","c","e","c","a","r"]


Swaps cancel out symmetrically → array remains same
✔ Still correct

🧠 How to INSTANTLY recognize this pattern in future

Ask yourself:

1️⃣ Is it array/string?
2️⃣ Is it reverse / compare from ends / palindrome?
3️⃣ Is O(1) space required?

✔ If YES → Two Pointers

🔥 Similar Problems (Same Pattern)

Valid Palindrome

Reverse Words in a String

Move Zeroes

Remove Duplicates from Sorted Array

Container With Most Water

🧩 Interview One-Line Explanation

“Since we must reverse the array in-place with O(1) space, we use two pointers from both ends and swap until they meet.”