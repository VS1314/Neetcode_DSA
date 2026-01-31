Perfect — this problem is binary search in disguise 😄
Let’s go slow, explain every line, and also do full iterations so it’s crystal clear.

🧠 Key Insight

The number is between 1 and n

After every guess, you’re told:

Too high → move left

Too low → move right

That’s exactly how binary search works

✅ Correct & Safe Code (No Overflow)
public class Solution extends GuessGame {
public int guessNumber(int n) {

        int l = 1;
        int r = n;

        while (l <= r) {

            int mid = l + (r - l) / 2;

            int res = guess(mid);

            if (res == 0) {
                return mid;          // correct guess
            }
            else if (res == -1) {
                r = mid - 1;         // guessed too high
            }
            else {
                l = mid + 1;         // guessed too low
            }
        }

        return -1; // will never happen as per constraints
    }
}

❗ Why This Mid Calculation?
int mid = l + (r - l) / 2;


🚫 Avoid this:

(l + r) / 2


Because n can be as large as 2³¹ − 1, which can cause integer overflow.

🔁 Full Dry Run — Example 1
Input
n = 5
pick = 3

🔁 Iteration 1
l = 1, r = 5
mid = 1 + (5 - 1)/2 = 3
guess(3) → 0


🎯 Correct guess
Return 3

🔁 Full Dry Run — Example 2
Input
n = 15
pick = 10

🔁 Iteration 1
l = 1, r = 15
mid = 8
guess(8) → 1   (too low)


➡ Move right:

l = 9

🔁 Iteration 2
l = 9, r = 15
mid = 12
guess(12) → -1  (too high)


⬅ Move left:

r = 11

🔁 Iteration 3
l = 9, r = 11
mid = 10
guess(10) → 0


🎯 Return 10

🔁 Example 3 (Edge Case)
Input
n = 1
pick = 1

l = 1, r = 1
mid = 1
guess(1) → 0


✅ Works perfectly

⏱ Complexity
Metric	Value
Time	O(log n)
Space	O(1)
🎯 Interview One-Liner

“Since the feedback tells me whether my guess is higher or lower, I apply binary search between 1 and n.”

🚨 Common Mistakes

❌ Using (l + r) / 2
❌ Forgetting to move l or r
❌ Infinite loop due to while(l < r)