Great problem 👍 — this is a classic HashSet pattern question.
I’ll explain it slowly, step-by-step, with a full dry run, and also tell you what pattern this belongs to.

🧠 PATTERN IDENTIFICATION

Pattern:
👉 HashSet + Sequence Start Detection

This is NOT sorting, NOT sliding window.

Key idea:

Only start counting when the number is the start of a sequence

❌ Why brute force fails

For every number:

Check num+1, num+2, num+3...

Worst case:

[1,2,3,4,5,6,7]


Every element does full scan → O(n²) ❌

✅ Optimal Idea (Core Insight)

A number num is the start of a sequence ONLY IF:

(num - 1) does NOT exist


Why?

Example:

[2, 3, 4, 5]

num	num-1 exists?	start?
2	❌	✅
3	✅	❌
4	✅	❌
5	✅	❌

👉 Start only at 2

✅ Algorithm (Plain English)

Put all numbers into a HashSet

For each number:

If num - 1 exists → skip

Else:

Start counting num, num+1, num+2...

Track maximum length

✅ Java Code (Correct & Clean)
class Solution {
public int longestConsecutive(int[] nums) {
HashSet<Integer> set = new HashSet<>();
for (int n : nums) {
set.add(n);
}

        int longest = 0;

        for (int num : set) {
            // start of sequence
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int count = 1;

                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    count++;
                }

                longest = Math.max(longest, count);
            }
        }
        return longest;
    }
}

🧪 FULL DRY RUN — Example 1
Input
nums = [2,20,4,10,3,4,5]

Step 1: Build HashSet
set = {2,3,4,5,10,20}

Step 2: Iterate through set
num = 2

2 - 1 = 1 ❌ not in set → START

2 → 3 → 4 → 5
count = 4


longest = 4

num = 3

3 - 1 = 2 ✅ → SKIP

num = 4

4 - 1 = 3 ✅ → SKIP

num = 5

5 - 1 = 4 ✅ → SKIP

num = 10

10 - 1 = 9 ❌ → START

10 → stop
count = 1

num = 20

20 - 1 = 19 ❌ → START

20 → stop
count = 1

✅ Final Answer
longest = 4

🧪 FULL DRY RUN — Example 2
Input
nums = [0,3,2,5,4,6,1,1]

HashSet
{0,1,2,3,4,5,6}

Only start at 0
0 → 1 → 2 → 3 → 4 → 5 → 6
count = 7


✅ Answer = 7

⏱ Time & Space Complexity
Metric	Complexity
Time	O(n)
Space	O(n)

Each number:

Added once

Checked once

Counted once

❗ Interview Tips (IMPORTANT)

❌ Do NOT sort → O(n log n)

❌ Do NOT nested loops

✅ Explain “start of sequence” logic clearly

If interviewer asks:

Why is it O(n)?

Say:

Each number is visited at most twice — once in outer loop and once in while loop.

🧩 Pattern Summary (Remember this)
Problem	Pattern
Longest Consecutive Sequence	HashSet + Sequence Start
Top K Frequent	Bucket Sort / Heap
Valid Sudoku	HashSet Validation
Range Sum Query	Prefix Sum