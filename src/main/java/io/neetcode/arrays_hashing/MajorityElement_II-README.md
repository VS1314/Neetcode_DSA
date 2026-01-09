This is a classic extension of Majority Element and uses a very important pattern.

🧠 PATTERN IDENTIFICATION

Pattern:
👉 Boyer–Moore Voting Algorithm (Extended version)

Majority Element (> n/2) → 1 candidate

Elements appearing > n/3 → at most 2 candidates

❗ This “at most 2” fact is the key.

🤔 WHY ONLY 2 ELEMENTS?

If an element appears more than ⌊n/3⌋ times:

You cannot have 3 such elements
Because:

3 × (n/3 + 1) > n


So:
👉 Maximum 2 elements can satisfy the condition

🚫 BRUTE FORCE (NOT ALLOWED)

Use HashMap → O(n) space ❌

Sort → O(n log n) ❌

Follow-up asks:

O(n) time + O(1) space

✅ OPTIMAL APPROACH (2-PHASE BOYER–MOORE)
Phase 1: Find potential candidates
Phase 2: Verify their counts
🧩 IDEA (INTUITION)

We maintain:

cand1, cand2

count1, count2

Rules:

If number == cand1 → count1++

Else if number == cand2 → count2++

Else if count1 == 0 → set cand1

Else if count2 == 0 → set cand2

Else → decrement both counts

👉 This cancels out groups of 3 different numbers

✅ JAVA SOLUTION (INTERVIEW READY)
class Solution {
public List<Integer> majorityElement(int[] nums) {
int cand1 = 0, cand2 = 0;
int count1 = 0, count2 = 0;

        // Phase 1: Find candidates
        for (int num : nums) {
            if (num == cand1) {
                count1++;
            } else if (num == cand2) {
                count2++;
            } else if (count1 == 0) {
                cand1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        // Phase 2: Verify
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == cand1) count1++;
            else if (num == cand2) count2++;
        }

        List<Integer> res = new ArrayList<>();
        if (count1 > nums.length / 3) res.add(cand1);
        if (count2 > nums.length / 3) res.add(cand2);

        return res;
    }
}

🧪 COMPLETE DRY RUN (Example 1)
Input
nums = [5,2,3,2,2,2,2,5,5,5]
n = 10 → n/3 = 3

Phase 1
num	cand1	c1	cand2	c2
5	5	1	-	0
2	5	1	2	1
3	5	0	2	0
2	2	1	-	0
2	2	2	-	0
2	2	3	-	0
2	2	4	-	0
5	2	4	5	1
5	2	4	5	2
5	2	4	5	3

Candidates:

cand1 = 2, cand2 = 5

Phase 2 (Verification)

Count actual occurrences:

2 → 5 times
5 → 4 times


Both > 3 → ✅

Output
[2, 5]

🧪 Example 3
nums = [1,2,3]
n/3 = 1


Each appears once → ❌

Output
[]

⏱ COMPLEXITY
Metric	Value
Time	O(n)
Space	O(1)
🧠 INTERVIEW SUMMARY (VERY IMPORTANT)

This is Boyer–Moore extended

Cancel elements in groups of 3

Always verify in second pass

Max 2 answers possible

If you want next:

Visualization with cancellation

Why decrementing works mathematically

Generalization to > n/k

Just say 👍

// Phase 2: Verify
count1 = 0;
count2 = 0;
for (int num : nums) {
if (num == cand1) count1++;
else if (num == cand2) count2++;
}

        List<Integer> res = new ArrayList<>();
        if (count1 > nums.length / 3) res.add(cand1);
        if (count2 > nums.length / 3) res.add(cand2);

        return res;
why this verify step

Excellent question 👍
This doubt means you’re very close to fully understanding Boyer–Moore, not just memorizing it.

Let’s clear this once and forever.

❓ WHY DO WE NEED THE VERIFY STEP?

Short answer:

Phase 1 only gives “possible candidates”, not guaranteed answers.

The verify step confirms whether those candidates actually appear more than ⌊n/3⌋ times.

🧠 KEY INSIGHT (VERY IMPORTANT)

The Boyer–Moore algorithm works by cancelling elements, not by counting exact frequencies.

So after Phase 1:

cand1 and cand2 are survivors of cancellation

They are not guaranteed to be majority elements

👉 Survivors ≠ Winners

🧩 WHAT PHASE 1 REALLY DOES

Phase 1 answers this question:

“Which elements could possibly appear more than n/3 times?”

It does NOT answer:

“Which elements actually do appear more than n/3 times?”

That’s why Phase 2 exists.

🔥 SIMPLE COUNTER-EXAMPLE (VERY IMPORTANT)
Input:
nums = [1, 2, 3, 4]
n = 4 → n/3 = 1

Phase 1 (candidate selection):

You might end with:

cand1 = 3
cand2 = 4


But actual frequencies:

1 → 1
2 → 1
3 → 1
4 → 1


❌ None appears more than 1 time.

❓ Without verification:

You would incorrectly return [3,4]

✅ With verification:

You correctly return []

🧪 YOUR CODE’S VERIFY STEP (LINE BY LINE)
count1 = 0;
count2 = 0;


🔹 Reset counts
Why?
Because Phase 1 counts are cancellation counts, not real frequencies.

for (int num : nums) {
if (num == cand1) count1++;
else if (num == cand2) count2++;
}


🔹 Now we actually count occurrences

True frequency of cand1

True frequency of cand2

if (count1 > nums.length / 3) res.add(cand1);
if (count2 > nums.length / 3) res.add(cand2);


🔹 Only add if it truly satisfies the problem condition

🧠 THINK OF IT LIKE THIS (ANALOGY)
Phase 1:

🗳️ Election nomination

Many candidates

Only strongest 2 survive

Phase 2:

📊 Vote counting

Verify who actually crossed the required threshold

You must count votes again.

⚠️ VERY IMPORTANT INTERVIEW RULE

Any Boyer–Moore based solution ALWAYS needs a verification pass
unless the problem explicitly guarantees existence.

Problem	Verification Needed?
Majority Element (> n/2)	❌ (guaranteed exists)
Majority Element II (> n/3)	✅
General > n/k	✅
🧠 ONE-LINE SUMMARY (MEMORIZE THIS)

Phase 1 finds candidates by cancellation.
Phase 2 confirms them by counting.

class Solution {
public List<Integer> majorityElement(int[] nums) {
int cand1=0;
int cand2 = 0;
int count1 =0;
int count2=0;
for(int i : nums){
if(count1==0) cand1=i;
if(count2==0) cand2=i;
if(cand1==i) count1++;
if(cand2==i) count2++;
else {
count1--;
count2--;
}
}
count1=0;
count2=0;
List<Integer> ans = new ArrayList<>();
for(int i : nums){
if(i==cand1) count1++;
if(i==cand2) count2++;
}
if(count1>nums.length/3) ans.add(cand1);
if(count2>nums.length/3) ans.add(cand2);

        return ans;
    }
}
this is my solution

Good attempt 👍 You’re very close, but there are logical bugs in your Phase-1 (candidate selection).
Let’s debug your code line by line, explain what’s wrong, and then I’ll give the correct, clean version and the reasoning.

🚨 MAIN PROBLEM IN YOUR CODE (IMPORTANT)

Your candidate selection logic is incorrect.

Your Phase-1 code
for(int i : nums){
if(count1==0) cand1=i;
if(count2==0) cand2=i;
if(cand1==i) count1++;
if(cand2==i) count2++;
else {
count1--;
count2--;
}
}

❌ What’s wrong here?

There are 3 critical issues:

❌ ISSUE 1: cand1 and cand2 can become the SAME
if(count1==0) cand1=i;
if(count2==0) cand2=i;


Both can be assigned the same number in the same iteration.

👉 But Boyer–Moore requires two DISTINCT candidates.

❌ ISSUE 2: else is attached ONLY to second if

This part:

if(cand1==i) count1++;
if(cand2==i) count2++;
else {
count1--;
count2--;
}


Java parses it as:

if (cand1 == i) count1++;
if (cand2 == i) count2++;
else {   // only belongs to second if
count1--;
count2--;
}

Result:

If cand1 == i but cand2 != i

You increment count1

Then you decrement both counts

💥 Completely breaks the algorithm.

❌ ISSUE 3: Missing else if chain

Boyer–Moore relies on a very strict priority order:

If matches cand1 → count1++

Else if matches cand2 → count2++

Else if count1 == 0 → assign cand1

Else if count2 == 0 → assign cand2

Else → decrement both

Your code does not enforce this order.

✅ CORRECT PHASE-1 LOGIC (FIXED)

Here is the correct and standard Boyer–Moore (> n/3) implementation:

class Solution {
public List<Integer> majorityElement(int[] nums) {
int cand1 = 0, cand2 = 0;
int count1 = 0, count2 = 0;

        // Phase 1: Candidate selection
        for (int num : nums) {
            if (num == cand1) {
                count1++;
            } else if (num == cand2) {
                count2++;
            } else if (count1 == 0) {
                cand1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        // Phase 2: Verification
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == cand1) count1++;
            else if (num == cand2) count2++;
        }

        List<Integer> ans = new ArrayList<>();
        if (count1 > nums.length / 3) ans.add(cand1);
        if (count2 > nums.length / 3) ans.add(cand2);

        return ans;
    }
}

🧠 WHY THIS ORDER MATTERS (INTUITION)

Think in priority:

First try to support existing candidates

Only replace a candidate if its count is zero

Otherwise → cancel both

This ensures:

No duplicates

At most 2 survivors

Correct cancellation behavior

🧪 QUICK DRY RUN (Example)

Input:

[5,2,3,2,2,2,2,5,5,5]
n = 10 → n/3 = 3


After Phase 1:

cand1 = 2
cand2 = 5


Phase 2 counts:

2 → 5 times
5 → 4 times


✅ Output: [2,5]

🧠 FINAL TAKEAWAY (VERY IMPORTANT)

❌ Your logic idea is right
❌ Your if structure breaks Boyer–Moore
✅ Correct solution must use an else-if chain

If you want, next I can:

Rewrite this in your own style

Dry run your broken version to show exactly where it fails

Generalize this to n/k majority

Just tell me 👍

even this is failing
class Solution {
public List<Integer> majorityElement(int[] nums) {
int cand1=0;
int cand2 = 0;
int count1 =0;
int count2=0;
for(int i : nums){
if(count1==0) cand1=i;
else if(count2==0) cand2=i;
else if(cand1==i) count1++;
else if(cand2==i) count2++;
else {
count1--;
count2--;
}
}
count1=0;
count2=0;
List<Integer> ans = new ArrayList<>();
for(int i : nums){
if(i==cand1) count1++;
if(i==cand2) count2++;
}
if(count1>nums.length/3) ans.add(cand1);
if(count2>nums.length/3) ans.add(cand2);

        return ans;
    }
}

You’re very close 👍
The reason it’s still failing is due to one subtle but critical mistake in the candidate selection phase of Boyer–Moore (n/3 version).

Let’s break it down clearly.

Correct logic (very important order)

For ⌊n/3⌋ majority, there can be at most 2 candidates.

Phase 1: Candidate selection rules (MUST follow this exact order)

For each number num:

If num == cand1 → count1++

Else if num == cand2 → count2++

Else if count1 == 0 → cand1 = num, count1 = 1

Else if count2 == 0 → cand2 = num, count2 = 1

Else → count1--, count2--

👉 The key idea:

Matching existing candidates has higher priority

Only assign a new candidate when its count is zero

❌ What’s wrong in your code
Your Phase 1:
for(int i : nums){
if(count1==0) cand1=i;
else if(count2==0) cand2=i;
else if(cand1==i) count1++;
else if(cand2==i) count2++;
else {
count1--;
count2--;
}
}

Problems:

You assign cand1 or cand2 before checking if i matches an existing candidate

You never set count1 = 1 or count2 = 1 when assigning candidates

This causes:

Wrong candidates

Counts going negative

Valid majority elements being lost

✅ Corrected Working Solution
class Solution {
public List<Integer> majorityElement(int[] nums) {
int cand1 = 0, cand2 = 0;
int count1 = 0, count2 = 0;

        // Phase 1: Find potential candidates
        for (int num : nums) {
            if (num == cand1) {
                count1++;
            } else if (num == cand2) {
                count2++;
            } else if (count1 == 0) {
                cand1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        // Phase 2: Verify counts
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == cand1) count1++;
            else if (num == cand2) count2++;
        }

        List<Integer> ans = new ArrayList<>();
        if (count1 > nums.length / 3) ans.add(cand1);
        if (count2 > nums.length / 3) ans.add(cand2);

        return ans;
    }
}

🧠 Why verification is REQUIRED (quick intuition)

The first phase only guarantees potential candidates, not certainty.

Example:

[1,2,3,4,5,6]


You’ll still end up with 2 candidates — but none appear > n/3 times.

So we must re-count in Phase 2.

✅ Final Takeaways (Interview Gold)

Order of conditions matters

Always increment count when assigning new candidate

Phase 1 → possible candidates

Phase 2 → confirmation

If you want, I can:

Dry run this with your example

Show a visualization

Compare with hashmap approach

Give interview explanation in 30 seconds

Just tell me 👍

what if num is zero

Great question 👍 — this is a very common confusion.

Short answer

👉 It works perfectly even if num is 0.
There is no special case needed.

Why 0 is NOT a problem

In Boyer–Moore, cand1 = 0 or cand2 = 0 is just an initial placeholder, not a real value yet.

What actually matters is the count, not the initial value.

int cand1 = 0, cand2 = 0;
int count1 = 0, count2 = 0;


At this point:

cand1 and cand2 are invalid

Because count1 == 0 and count2 == 0

What happens if num = 0 appears?

Let’s dry run.

Example:
nums = [0, 0, 1, 2, 0, 0]

Step-by-step:
num	cand1	count1	cand2	count2	Action
0	0	1	0	0	cand1 assigned
0	0	2	0	0	matches cand1
1	0	2	1	1	cand2 assigned
2	0	1	1	0	both decremented
0	0	2	1	0	matches cand1
0	0	3	1	0	matches cand1

✅ 0 is correctly tracked as a candidate.

Key Rule (this removes all confusion)

A candidate is valid ONLY when its count > 0

So:

Initial cand1 = 0 doesn’t mean 0 is selected

0 becomes a real candidate only when count1 becomes 1

What if array has only zeros?
nums = [0,0,0,0]


Phase 1 → cand1 = 0

Phase 2 → count = 4

4 > n/3 → return [0]

✅ Correct

Why people think it breaks

Because they confuse:

default value of int (0)

with valid candidate selection

But the algorithm never trusts candX without its count.

Interview-safe explanation (1 line)

“Initial candidate values don’t matter because a candidate is only considered valid when its count is positive.”

If you want, I can:

Rewrite this using Integer instead of int

Show how this fails if order is wrong

Give a memory trick to remember the condition order

Just say 👍

Wrong Answer
4 / 24 test cases
Memory: 63.6 MB
•
Time: 0.434s
•
Submitted at: 01/09/2026 19:57
Failed Test Case
Input

nums=[1,2]
Your Output

[2]
Expected Output

[1,2]
this test case is failing
class Solution {
public List<Integer> majorityElement(int[] nums) {
int cand1=0;
int cand2 = 0;
int count1 =0;
int count2=0;
for(int i : nums){
if(cand1==i) count1++;
else if(cand2==i) count2++;
else if(count1==0) cand1=i;
else if(count2==0) cand2=i;
else {
count1--;
count2--;
}
}
count1=0;
count2=0;
List<Integer> ans = new ArrayList<>();
for(int i : nums){
if(i==cand1) count1++;
if(i==cand2) count2++;
}
if(count1>nums.length/3) ans.add(cand1);
if(count2>nums.length/3) ans.add(cand2);

        return ans;
    }
}

Perfect test case to expose the bug 👌
This failure is not about 0 — it’s about missing count updates when a new candidate is chosen.

Why your code fails for nums = [1, 2]

Let’s dry-run your exact code.

Initial
cand1 = 0, count1 = 0
cand2 = 0, count2 = 0

i = 1
if (cand1 == i) ❌
else if (cand2 == i) ❌
else if (count1 == 0) cand1 = 1;   // ❗ count1 NOT incremented


State:

cand1 = 1, count1 = 0
cand2 = 0, count2 = 0

i = 2
if (cand1 == i) ❌
else if (cand2 == i) ❌
else if (count1 == 0) cand1 = 2;   // overwrites cand1


Final candidates:

cand1 = 2
cand2 = 0

Verification phase
count1 = 1 (for 2)
count2 = 0
n/3 = 0


✅ Only 2 added → WRONG

🔴 Root cause (important)

When you assign a new candidate, you MUST also set its count to 1.

You currently do:

else if (count1 == 0) cand1 = i;


But it must be:

else if (count1 == 0) {
cand1 = i;
count1 = 1;
}


Same for cand2.

✅ Correct & accepted solution (minimal fix)
class Solution {
public List<Integer> majorityElement(int[] nums) {
int cand1 = 0, cand2 = 0;
int count1 = 0, count2 = 0;

        // Phase 1: Find candidates
        for (int num : nums) {
            if (cand1 == num) {
                count1++;
            } else if (cand2 == num) {
                count2++;
            } else if (count1 == 0) {
                cand1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                cand2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }

        // Phase 2: Verify
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == cand1) count1++;
            else if (num == cand2) count2++;
        }

        List<Integer> ans = new ArrayList<>();
        if (count1 > nums.length / 3) ans.add(cand1);
        if (count2 > nums.length / 3) ans.add(cand2);

        return ans;
    }
}

Why this now works for [1,2]

cand1 = 1, count1 = 1

cand2 = 2, count2 = 1

n/3 = 0

both counts > 0 → [1,2] ✅

Interview one-liner (very important)

“When a count becomes zero and we assign a new candidate, we must immediately set its count to 1, otherwise it can be overwritten incorrectly.”