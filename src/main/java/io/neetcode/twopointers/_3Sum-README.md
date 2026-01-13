Perfect 👍
This is a classic + VERY IMPORTANT interview problem. Let’s do it your way:

👉 Pattern → Why → Algorithm → Code → Full Dry Run → Mistakes

🧠 Pattern Identification
Keywords you should catch immediately:

Triplets

Sum = 0

No duplicates

Return combinations (not count)

💡 This tells us:

✅ Sorting + Two Pointers (Fixed + Moving pointers)

This is an extension of Two Sum.

🔁 How 3Sum Reduces to 2Sum

Fix one number → nums[i]

Find two numbers after it such that:

nums[j] + nums[k] = -nums[i]


So:

Outer loop → fixes i

Inner loop → two pointers (left, right)

🚦 Step 1: Sort the Array

Why sorting?

Enables two pointers

Makes duplicate removal easy

Example:

[-1,0,1,2,-1,-4]
↓
[-4,-1,-1,0,1,2]

🧠 Core Algorithm
For each index i:

Skip duplicates (nums[i] == nums[i-1])

Set:

left = i + 1
right = n - 1


While left < right:

Calculate sum

Move pointers

Skip duplicates

✅ Correct Java Code (Industry Standard)
class Solution {
public List<List<Integer>> threeSum(int[] nums) {
List<List<Integer>> res = new ArrayList<>();
Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {

            // Skip duplicate fixed elements
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // Skip duplicates for left & right
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    left++;
                    right--;
                }
                else if (sum < 0) {
                    left++;
                }
                else {
                    right--;
                }
            }
        }
        return res;
    }
}

🧪 FULL DRY RUN (Example 1)
Input
nums = [-1,0,1,2,-1,-4]

Sorted
[-4,-1,-1,0,1,2]

🔁 i = 0 → nums[i] = -4
left = 1 (-1), right = 5 (2)
sum = -4 + (-1) + 2 = -3 < 0 → left++


Continue…
❌ No valid triplet

🔁 i = 1 → nums[i] = -1
left = 2 (-1), right = 5 (2)
sum = -1 + (-1) + 2 = 0 ✅
→ add [-1,-1,2]


Skip duplicates
Move pointers

left = 3 (0), right = 4 (1)
sum = -1 + 0 + 1 = 0 ✅
→ add [-1,0,1]

🔁 i = 2 → nums[i] = -1

Duplicate → ❌ skip

🔁 i = 3 → nums[i] = 0
left = 4 (1), right = 5 (2)
sum = 3 > 0 → right--


❌ no more

✅ Final Output
[[-1,-1,2], [-1,0,1]]

🚫 Why Duplicate Skipping Is CRITICAL

Without this:

[-1,-1,2] appears multiple times


We skip:

Duplicate i

Duplicate left

Duplicate right

⏱ Complexity
Metric	Value
Time	O(n²)
Space	O(1) (excluding output)
❌ Common Mistakes (VERY COMMON)

❌ Forgetting to sort

❌ Not skipping duplicates

❌ Using HashSet unnecessarily

❌ Wrong pointer movement

❌ Thinking brute force (O(n³))

🧠 Pattern Summary (Memorize This)
Sort →
Fix one element →
Two pointers →
Skip duplicates →
Collect results

🔁 Similar Problems (Same Pattern)

Two Sum II (sorted array)

4Sum

3Sum Closest

Count Triplets with Sum < K

Pair Sum in Sorted Array