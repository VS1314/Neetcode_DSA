This is a classic prefix-sum + HashMap problem.
I’ll explain it slowly, intuitively, and with a full dry run, because this one usually “clicks” only after seeing why it works.

🔹 Problem restated (in simple words)

Count how many contiguous subarrays have sum = k.

Subarray = continuous part of the array.

❌ Brute force (why not)

Check all subarrays → O(n²) → too slow for n = 20,000.

We need O(n).

✅ Key Idea: Prefix Sum
Prefix sum definition
prefixSum[i] = sum of elements from index 0 to i


Example:

nums = [2, -1, 1, 2]
prefixSum = [2, 1, 2, 4]

💡 Core insight (VERY IMPORTANT)

A subarray from index j+1 to i has sum = k if and only if:

prefixSum[i] - prefixSum[j] = k


Rearranging:

prefixSum[j] = prefixSum[i] - k


👉 So while scanning, if we know how many times
(currentPrefixSum - k) appeared before,
we know how many subarrays end here with sum = k.

🧠 Data Structure

We use a HashMap:

map[prefixSum] = how many times this prefixSum occurred


Why count?
Because the same prefix sum can appear multiple times, creating multiple valid subarrays.

✅ Algorithm (step-by-step)

Initialize:

map.put(0, 1);


Why?
→ To count subarrays starting at index 0

Maintain:

sum → running prefix sum

count → total subarrays found

For each number:

Add it to sum

Check if (sum - k) exists in map

Add its frequency to count

Store sum in map

✅ Java Code (Correct & Optimal)
class Solution {
public int subarraySum(int[] nums, int k) {
Map<Integer, Integer> map = new HashMap<>();
map.put(0, 1); // base case

        int sum = 0;
        int count = 0;

        for (int num : nums) {
            sum += num;

            if (map.containsKey(sum - k)) {
                count += map.get(sum - k);
            }

            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }

        return count;
    }
}

🔍 FULL DRY RUN (Example 1)
Input
nums = [2, -1, 1, 2]
k = 2

Initial state
map = {0=1}
sum = 0
count = 0

Step 1: num = 2
sum = 2
sum - k = 0
map contains 0 → YES (1 time)
count = 1
map = {0=1, 2=1}


✔ Subarray: [2]

Step 2: num = -1
sum = 1
sum - k = -1
map contains -1 → NO
map = {0=1, 2=1, 1=1}

Step 3: num = 1
sum = 2
sum - k = 0
map contains 0 → YES (1 time)
count = 2
map = {0=1, 2=2, 1=1}


✔ Subarray: [2, -1, 1]

Step 4: num = 2
sum = 4
sum - k = 2
map contains 2 → YES (2 times)
count = 4
map = {0=1, 2=2, 1=1, 4=1}


✔ Subarrays:

[-1,1,2]

[2]

✅ Final Answer
count = 4

🔍 Example 2 Quick Intuition
nums = [4,4,4,4,4,4], k = 4


Every single 4 alone is valid.

Prefix sums:

4, 8, 12, 16, 20, 24


Each time:

sum - k exists


👉 Answer = 6

🚨 Common Mistakes

❌ Forgetting:

map.put(0, 1);


❌ Using sliding window (fails with negative numbers)

❌ Using Set instead of Map (frequency matters!)

🧠 Interview One-Liner

“We use prefix sums and a HashMap to count how many previous prefix sums equal currentSum - k, giving us O(n) time.”