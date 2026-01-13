Great problem 👍 — this is a classic two-pointer + reverse traversal pattern.
Let’s break it down step by step, exactly how interviewers expect you to think.

🧠 Pattern Recognition
Keywords in the problem

Two sorted arrays

Merge

Modify nums1 in-place

Extra space NOT allowed

➡️ This immediately tells us:

Merge from the end using two pointers

❌ Why naive approach fails
❌ Idea:

Start merging from the front

❌ Problem:

nums1 already has valid elements

Shifting elements right costs O(n) per operation

Total becomes O(n²)

👉 In-place + sorted → always think merge from back

✅ Correct Strategy: Merge From the End
🧠 Why from the end?

nums1 has extra space at the end

Largest elements should go to the back

No overwriting issues

🎯 Pointer Setup

Let:

i = m - 1   // last valid element in nums1
j = n - 1   // last element in nums2
k = m + n - 1  // last index of nums1

🔄 Core Logic

While both arrays have elements:

Compare nums1[i] and nums2[j]

Put the larger one at nums1[k]

Move pointers accordingly

After that:

If nums2 still has elements → copy them

No need to copy nums1 leftovers (already in place)

✅ Correct Java Code
class Solution {
public void merge(int[] nums1, int m, int[] nums2, int n) {
int i = m - 1;
int j = n - 1;
int k = m + n - 1;

        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }

        // Copy remaining nums2 elements if any
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }
}

🧪 Walkthrough (Example 1)
nums1 = [10,20,20,40,0,0]
nums2 = [1,2]

i = 3 (40), j = 1 (2), k = 5
→ place 40 → nums1[5]

i = 2 (20), j = 1 (2), k = 4
→ place 20

i = 1 (20), j = 1 (2), k = 3
→ place 20

i = 0 (10), j = 1 (2), k = 2
→ place 10

nums2 leftovers → copy 2, then 1


Final:

[1,2,10,20,20,40]

🧪 Edge Case Explained (Example 2)
nums1 = [0,0], m = 0
nums2 = [1,2]


First loop skipped (i < 0)

Copy nums2 directly
✔ Works perfectly

⏱ Complexity Analysis
Metric	Value
Time	O(m + n)
Space	O(1)
In-place	✅ YES
🧠 Pattern Summary (MEMORIZE THIS)
Sorted + Merge + In-place
→ Start from the END
→ Use 3 pointers

🔁 Similar Problems (Same Pattern)

Merge Intervals (after sorting)

Squares of a Sorted Array

Sort Colors

Two Sum II (sorted array)

Valid Palindrome (two pointers)