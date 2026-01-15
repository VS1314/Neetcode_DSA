Perfect problem to practice pattern recognition 👍
Let’s break this exactly the way you want: pattern → reasoning → greedy proof → dry run → constraints → final solution idea.

🧠 Step 1: Identify the PATTERN
Keywords in the problem

each boat can carry at most two people

sum of weights ≤ limit

minimum number of boats

👉 This screams GREEDY + TWO POINTERS (PAIRING)

This is the “pair lightest with heaviest” pattern.

🧩 Why Greedy Works Here (Key Insight)

To minimize boats:

Heavy people are hardest to place

If the heaviest person cannot pair with the lightest person,
→ they cannot pair with anyone

So the optimal strategy:

Always try to pair the lightest person with the heaviest person.

🧠 Step 2: Strategy (High Level)

Sort the array

Use two pointers

l → lightest person

r → heaviest person

If people[l] + people[r] <= limit

Put them in one boat

Move both pointers

Else

Heaviest person goes alone

Move only r

Each step uses exactly one boat

📌 Step 3: Why This Is Optimal (Important Interview Explanation)

If the heaviest person can pair with anyone, it must be the lightest

If even the lightest + heaviest exceed the limit:

The heaviest must go alone

No better pairing exists → greedy choice is safe

This is a classic greedy proof.

🧪 Step 4: Dry Run (Example 1)
Input
people = [5,1,4,2], limit = 6

Step 1: Sort
[1,2,4,5]

Pointers
l = 0 (1), r = 3 (5)
boats = 0

Iteration 1
1 + 5 = 6 <= limit
→ pair them
boats = 1
l++, r--

Iteration 2
2 + 4 = 6 <= limit
→ pair them
boats = 2
l++, r--

Done ✅
Answer = 2

🧪 Dry Run (Example 2)
people = [1,3,2,3,2], limit = 3
Sorted → [1,2,2,3,3]

l	r	pair?	boat
1	3	❌	3 alone
1	3	❌	3 alone
1	2	✅	[1,2]
2	—	alone	[2]

Answer = 4

⚙️ Step 5: Constraints → Why This Approach
Constraint	Why it matters
n ≤ 50,000	O(n log n) sort is OK
max 2 per boat	Enables pairing logic
weight ≤ limit	No invalid people
🧠 Pattern Name (REMEMBER THIS)

Greedy Pairing using Two Pointers

🧪 Final Code (Clean & Optimal)
class Solution {
public int numRescueBoats(int[] people, int limit) {
Arrays.sort(people);

        int l = 0;
        int r = people.length - 1;
        int boats = 0;

        while (l <= r) {
            if (people[l] + people[r] <= limit) {
                l++; // pair lightest
            }
            r--;    // heaviest always boards
            boats++;
        }
        return boats;
    }
}

⏱ Complexity
Metric	Value
Time	O(n log n)
Space	O(1) extra
🔁 Similar Problems (Same Pattern)

Two Sum (sorted version)

Container With Most Water

Minimize Maximum Pair Sum

Assign Cookies

Boats to Save People ← this one 😄

🎯 Mental Checklist for Future Problems

Ask yourself:

Is pairing involved?

Is there a “maximum of 2” constraint?

Is minimizing something?

👉 Think: sort + two pointers + greedy