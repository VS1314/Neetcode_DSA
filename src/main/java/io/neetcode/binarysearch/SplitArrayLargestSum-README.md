🧠 Pattern to Remember (VERY IMPORTANT)

This problem belongs to the “Binary Search on Answer” family:

Also includes:

Koko Eating Bananas

Capacity to Ship Packages

Painter’s Partition

Split Array Largest Sum

If you master this template, you solve 10+ problems easily.

This is a classic Hard binary search on answer problem. Once you see the pattern, it becomes very systematic.

Let’s go step-by-step and lock the intuition 🔒

🔍 Problem Restated (key insight)

You are not asked how to split.
You are asked:

What is the minimum possible value of the largest subarray sum after splitting into k parts?

That means:

We are minimizing a maximum

The answer lies in a range
➡️ Binary Search on the answer

🧠 Key Observations
1️⃣ Lower Bound

What is the minimum possible largest sum?

➡️ At least the maximum element in the array
Because every subarray must contain at least one element.

low = max(nums)

2️⃣ Upper Bound

What is the maximum possible largest sum?

➡️ Sum of entire array (no split)

high = sum(nums)

3️⃣ Monotonic Property (MOST IMPORTANT)

If we fix a value mid = candidate largest sum:

If we can split the array into ≤ k subarrays such that each subarray sum ≤ mid
→ mid is valid

If not
→ mid is too small

This monotonic behavior allows binary search.

✅ Feasibility Check (Greedy)

Given maxAllowedSum = mid, can we split into ≤ k subarrays?

Greedy strategy:

Keep adding numbers to current subarray

If sum exceeds mid, start a new subarray

count subarrays needed


If:

count <= k → feasible

count > k → not feasible

🧪 Example Walkthrough
nums = [2,4,10,1,5], k = 2

low = 10

high = 22

Binary search will eventually land on 16 ✔️

Split:

[2,4,10] = 16
[1,5] = 6


Largest sum = 16 (minimum possible)

💻 Final Java Solution (Clean & Interview-Ready)
class Solution {
public int splitArray(int[] nums, int k) {
int low = 0, high = 0;

        for (int num : nums) {
            low = Math.max(low, num); // max element
            high += num;              // total sum
        }

        int ans = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (canSplit(nums, k, mid)) {
                ans = mid;
                high = mid - 1; // try smaller max sum
            } else {
                low = mid + 1;  // need bigger max sum
            }
        }

        return ans;
    }

    private boolean canSplit(int[] nums, int k, int maxSum) {
        int subarrays = 1;
        int currSum = 0;

        for (int num : nums) {
            currSum += num;
            if (currSum > maxSum) {
                subarrays++;
                currSum = num;
                if (subarrays > k) return false;
            }
        }
        return true;
    }
}

⏱ Complexity
Metric	Value
Time	O(n log(sum(nums)))
Space	O(1)