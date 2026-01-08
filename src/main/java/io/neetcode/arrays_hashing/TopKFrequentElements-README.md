This is a classic pattern-identification problem, so I’ll do this in 3 clear layers:

1️⃣ How to IDENTIFY the pattern (most important)
2️⃣ Best solution approach (Bucket Sort – O(n))
3️⃣ Complete DRY RUN on your example

No code from you. My way only.

1️⃣ HOW TO IDENTIFY THE PATTERN 🧠

Read the problem carefully and underline keywords:

Top K Frequent Elements
return the k most frequent elements
O(n) time, O(n) space

🚨 Key signals
Signal	Meaning
“Top K”	Ranking problem
“Most frequent”	Frequency counting
“O(n) required”	Sorting is NOT allowed
“Answer is unique”	No tie-handling complexity
❌ Patterns this is NOT

❌ Two pointers

❌ Sliding window

❌ Binary search

❌ Greedy

✅ Correct Pattern

👉 Frequency Map + Bucket Sort

This pattern appears when:

You need top / bottom k

Sorting is too slow

Frequency range is bounded by n

📌 This is why NeetCode gives Bucket Sort hint

2️⃣ BEST SOLUTION APPROACH (BUCKET SORT)
High-level idea (no code yet)

Count frequency of each number

Create buckets where

index = frequency

value = list of numbers with that frequency

Traverse buckets from highest frequency to lowest

Collect k elements

Why Bucket Sort works here?

Max frequency of any element = n

So we can create n + 1 buckets

This avoids sorting → O(n)

3️⃣ COMPLETE DRY RUN 🧪
Input
nums = [1,2,2,3,3,3]
k = 2

STEP 1: Frequency Count

Create a map:

Number	Frequency
1	1
2	2
3	3
STEP 2: Create Buckets

Bucket index = frequency
Each bucket stores numbers with that frequency

Index:   0    1      2      3      4  5  6
Bucket:  []  [1]   [2]    [3]     [] [] []


Explanation:

1 appears once → bucket[1] = [1]

2 appears twice → bucket[2] = [2]

3 appears three times → bucket[3] = [3]

STEP 3: Traverse buckets from RIGHT → LEFT

We want highest frequency first

Start from index = 6 → 0

i = 6 → empty
i = 5 → empty
i = 4 → empty
i = 3 → [3]

result = [3]

k = 1 remaining

i = 2 → [2]

result = [3, 2]

k = 0 ✅ STOP

✅ FINAL OUTPUT
[3, 2]


(Order doesn’t matter → [2,3] also valid)

⏱️ TIME & SPACE COMPLEXITY
Time

Frequency count → O(n)

Bucket creation → O(n)

Bucket traversal → O(n)

👉 Total = O(n) ✅

Space

HashMap → O(n)

Buckets → O(n)

👉 Total = O(n) ✅

🔑 INTERVIEW ONE-LINER (VERY IMPORTANT)

“Since we need the top k frequent elements in linear time, I used a frequency map followed by bucket sort where index represents frequency.”

🧠 HOW YOU SHOULD RECOGNIZE THIS NEXT TIME

Whenever you see:

✔ Top K
✔ Frequency
✔ Sorting too slow
✔ O(n) expected

👉 IMMEDIATELY think: Bucket Sort

If you want next:

I can convert this to code step-by-step

Or give you a decision chart to identify Top-K problems

Or compare Heap vs Bucket (when to use which)

Just tell me 👍

i tried something likw this
class Solution {
public int[] topKFrequent(int[] nums, int k) {
Set<Integer> set = new HashSet<>();
for(int i : nums){
set.add(i);
}
PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
for(int i : set){
max.add(i);
}
int[] ans = new int[k];
for(int i=0;i<k;i++){
ans[i] = max.poll();
}
return ans;
}
}

Good that you tried 👍 — this is exactly how learning happens.
Now let’s debug your thinking, not just the code.

I’ll explain in 4 parts:

1️⃣ What your code is actually doing
2️⃣ Why it is wrong for this problem
3️⃣ Which pattern mistake happened
4️⃣ How to correct your thinking, not just code

1️⃣ WHAT YOUR CODE IS DOING (VERY IMPORTANT)

Your code:

Set<Integer> set = new HashSet<>();
for(int i : nums){
set.add(i);
}


👉 This removes duplicates
Example:

nums = [1,2,2,3,3,3]
set = {1,2,3}


So far → you LOST frequency information ❌

PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
for(int i : set){
max.add(i);
}


This creates a max-heap of VALUES, not frequencies.

Heap content:

3, 2, 1

ans[0] = max.poll(); // 3
ans[1] = max.poll(); // 2


👉 Your output is:

[3, 2]


⚠️ This is top K largest numbers, NOT top K frequent numbers.

2️⃣ WHY THIS IS WRONG ❌
The problem asks:

Top K Frequent Elements

But your logic finds:

Top K Largest Unique Elements

Counterexample (IMPORTANT)
nums = [1,1,1,2,2,3]
k = 2

Correct answer:
[1,2]

Your code result:
[3,2] ❌


Why?

3 appears once

1 appears three times
But your code does not care about frequency

3️⃣ THE CORE PATTERN MISTAKE 🧠
❌ Your mental model was:

“Top K → use PriorityQueue”

That’s only half correct.

✅ Correct mental model:

Top K BY WHAT?

If Top K by	Use
Value	Heap directly
Frequency	Frequency map + Heap/Bucket

📌 You skipped the frequency dimension

4️⃣ HOW TO FIX YOUR THINKING (NOT JUST CODE)
Step-by-step correct reasoning
Step 1: Ask this question first

“What am I ranking on?”

Answer:

Frequency, not value

Step 2: What data structure stores frequency?

✔ HashMap <number, count>

Step 3: How do I get top K by frequency?

Two valid approaches:

Option A: Heap (O(n log k))

Heap stores (frequency, number)

Heap compares by frequency

Option B: Bucket Sort (O(n)) ✅ BEST

Index = frequency

Value = list of numbers

WHY YOUR APPROACH FAILED (ONE LINE INTERVIEW EXPLANATION)

“I initially used a max heap directly on values, but that ranks numbers by magnitude, not frequency. Since the problem asks for top K frequent elements, frequency must be explicitly tracked.”

QUICK FIX (LOGIC ONLY, NO FULL CODE)

Instead of:

PriorityQueue<Integer>


You would need something like:

PriorityQueue<Map.Entry<Integer, Integer>>


And compare by:

entry.getValue() // frequency

FINAL TAKEAWAY (VERY IMPORTANT 🔥)
🔴 Rule to remember forever:

If the problem says "frequent", you MUST count first

No exceptions.

Pattern summary for this problem:
Frequency Map
↓
Bucket Sort (O(n))  ← BEST
or
Heap by Frequency (O(n log k))


If you want, next we can:

Convert your code into a correct heap-based solution

Or do bucket sort code slowly

Or I’ll give you a Top-K decision chart you can memorize

Just tell me 👍

how to iterate map

Good question 👍 — this is core Java + DSA basics.
Let’s go step by step, from most common → interview-friendly → when to use which.

1️⃣ Iterate over a Map (MOST COMMON & BEST)
Using entrySet() ✅ (Recommended)
Map<Integer, Integer> map = new HashMap<>();

for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
int key = entry.getKey();
int value = entry.getValue();
}

✔ Why this is best:

Fast

Clean

Access key and value together

Most used in interviews

2️⃣ Iterate over Keys only
for (Integer key : map.keySet()) {
int value = map.get(key);
}


⚠ Less efficient (extra get() lookup)

3️⃣ Iterate over Values only
for (Integer value : map.values()) {
System.out.println(value);
}


✔ Useful when you don’t care about keys

4️⃣ Using Java 8 forEach (MODERN)
map.forEach((key, value) -> {
System.out.println(key + " -> " + value);
});


✔ Clean
⚠ Some interviewers prefer entrySet() for clarity

5️⃣ Using Iterator (RARE but useful)
Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();

while (it.hasNext()) {
Map.Entry<Integer, Integer> entry = it.next();
int key = entry.getKey();
int value = entry.getValue();
}


✔ Needed when you want to remove elements while iterating

QUICK COMPARISON TABLE 🧠
Use Case	Best Way
Key + Value	entrySet() ✅
Keys only	keySet()
Values only	values()
Java 8 style	forEach()
Remove while iterating	Iterator
INTERVIEW TIP 🔥

If interviewer asks:

“How do you iterate over a HashMap?”

👉 Answer:

“Using entrySet() because it allows efficient access to both keys and values.”

Then write this:

for (Map.Entry<K, V> entry : map.entrySet()) {
...
}

WANT PRACTICE?

I can:

Show iteration inside Top K Frequent

Explain how map iteration works in memory

Give you common mistakes

Just say the word 👍

class Solution {
public int[] topKFrequent(int[] nums, int k) {
HashMap<Integer,Integer> map = new HashMap<>();
int[] freq = new int[nums.length];
for(int i : nums){
map.put(i,map.getOrDefault(i,0)+1);
}
for(Map.Entry<Integer,Integer> entry : map.entrySet()){
int key = entry.getKey();
int value = entry.getValue();
freq[value] = key;
}
int[] ans = new int[k];
for(int i=nums.length-1;i>=0;i--){
if(freq[i]!=0&&k>0){
ans[k-1] = freq[i];
k--;
}
}
return ans;
}
}
is this right? i wrote

Great attempt 👏
You are very close, but ❌ this solution is NOT fully correct.
Let me explain clearly, slowly, with a dry run, so you’ll never repeat this mistake.

❌ WHAT IS WRONG IN YOUR CODE?
❌ Problem 1: freq[value] = key;

You are using:

int[] freq = new int[nums.length];
freq[value] = key;

Why this is wrong ❌

Multiple numbers can have the same frequency

Your array stores only ONE number per frequency

Earlier values get overwritten

🔥 DRY RUN (VERY IMPORTANT)
Input:
nums = [1,1,2,2,3]
k = 2

Step 1: Frequency Map
map = {
1 -> 2,
2 -> 2,
3 -> 1
}

Step 2: Your freq array updates
freq[2] = 1   // from key=1
freq[2] = 2   // overwritten by key=2 ❌
freq[1] = 3

Final freq array:
index: 0 1 2 3 4
value: 0 3 2 0 0


❌ We lost number 1 completely

Step 3: Collect top K

You get:

[2, 3] ❌


Correct answer should be:

[1, 2] or [2, 1]

❌ ROOT CAUSE (PATTERN MISTAKE)
Frequency Bucket Pattern Rule:

One frequency can have MULTIPLE numbers

So the bucket must store a list, not a single number.

✅ CORRECT APPROACH (BUCKET SORT PATTERN)
✔ Fix: Use List inside bucket
class Solution {
public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        // Bucket: index = frequency, value = list of numbers
        List<Integer>[] bucket = new List[nums.length + 1];

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey();
            int freq = entry.getValue();

            if (bucket[freq] == null) {
                bucket[freq] = new ArrayList<>();
            }
            bucket[freq].add(num);
        }

        int[] ans = new int[k];
        int index = 0;

        for (int i = nums.length; i >= 0 && index < k; i--) {
            if (bucket[i] != null) {
                for (int num : bucket[i]) {
                    ans[index++] = num;
                    if (index == k) break;
                }
            }
        }
        return ans;
    }
}

🧠 COMPLETE DRY RUN (SLOW)
Input
nums = [1,1,2,2,3]
k = 2

Frequency Map
1 -> 2
2 -> 2
3 -> 1

Bucket Array
bucket[1] = [3]
bucket[2] = [1, 2]

Traverse from highest frequency:
freq 2 → take 1, 2

Output:
[1, 2] ✅

📌 PATTERN IDENTIFICATION
Problem	Pattern
Top K Frequent	Bucket Sort
Need frequency	HashMap
Need Top K	Heap or Bucket
🧪 WHEN IS YOUR CODE VALID?

Your code works ONLY IF:

All frequencies are unique ❌ (rare)

🔥 INTERVIEW TIP

If interviewer asks:

“Why array of list?”

Answer:

“Because multiple elements can have the same frequency.”

NEXT STEP?

Want me to:

Explain Heap solution

Compare Bucket vs Heap

Do line-by-line dry run again

Just say 👍

        List<List<Integer>> freq = new ArrayList<>(); will this work? 

Yes 👍 it WILL work — but only if you initialize it correctly.
Most people get this wrong, so let’s go slow and visual.

✅ SHORT ANSWER
List<List<Integer>> freq = new ArrayList<>();


✔️ Valid
❌ NOT usable yet (lists inside are missing)

You must pre-fill it before accessing by index.

❌ COMMON MISTAKE (THIS WILL CRASH)
List<List<Integer>> freq = new ArrayList<>();
freq.get(2).add(10);   // ❌ IndexOutOfBoundsException


Why?

freq is empty

No index 2 exists

✅ CORRECT WAY (BUCKET SORT WITH LIST OF LISTS)
Step 1: Initialize buckets
List<List<Integer>> freq = new ArrayList<>();

for (int i = 0; i <= nums.length; i++) {
freq.add(new ArrayList<>());
}


Now:

freq.size() = nums.length + 1
freq[0], freq[1], freq[2] ... all exist

✅ FULL WORKING SOLUTION (CLEAN + INTERVIEW READY)
class Solution {
public int[] topKFrequent(int[] nums, int k) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        List<List<Integer>> freq = new ArrayList<>();
        for (int i = 0; i <= nums.length; i++) {
            freq.add(new ArrayList<>());
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey();
            int count = entry.getValue();
            freq.get(count).add(num);
        }

        int[] ans = new int[k];
        int idx = 0;

        for (int i = nums.length; i >= 0 && idx < k; i--) {
            for (int num : freq.get(i)) {
                ans[idx++] = num;
                if (idx == k) break;
            }
        }

        return ans;
    }
}

🔍 COMPLETE DRY RUN
Input
nums = [1,1,2,2,3]
k = 2

Frequency Map
1 → 2
2 → 2
3 → 1

freq buckets
freq[1] = [3]
freq[2] = [1, 2]

Collect from highest
freq[2] → take 1, 2

Output
[1, 2] ✅

🧠 WHY LIST<LIST<Integer>> IS BETTER THAN ARRAY
Reason	List<List>	Array<List>
Cleaner Java	✅	❌
Dynamic	✅	❌
Interview friendly	✅	⚠️
No warnings	✅	❌
🏆 PATTERN CONFIRMED

This is BUCKET SORT pattern

Used in:

Top K Frequent Elements

Sort Characters by Frequency

Frequency based grouping problems

NEXT?

If you want:

🚀 Heap vs Bucket (when to use which)

🧠 How to identify this pattern instantly

🔥 Common interview traps

Just tell me 👍

        List<List<Integer>> freq = new ArrayList<>(nums.length); will this not work how to give size for an arraylist 

This is an excellent question — you’re touching a very common Java confusion 👌
Let’s clear it once and for all.

❓ Will this work?
List<List<Integer>> freq = new ArrayList<>(nums.length);

✅ Compiles
❌ Does NOT give size
❌ You still cannot do freq.get(i)
🧠 KEY CONCEPT (VERY IMPORTANT)
ArrayList(capacity) ≠ ArrayList(size)
Term	Meaning
Capacity	How much space is reserved internally
Size	How many elements actually exist

👉 ArrayList<>(nums.length)
✔️ reserves memory
❌ adds ZERO elements

🔥 PROOF (Dry Run)
List<Integer> list = new ArrayList<>(5);

System.out.println(list.size());  // 0
list.get(0);                       // ❌ IndexOutOfBoundsException


Why?

Capacity = 5

Size = 0

No elements added yet

✅ CORRECT WAY TO “GIVE SIZE” TO ARRAYLIST
🔹 Method 1: Add elements in loop (MOST USED)
List<List<Integer>> freq = new ArrayList<>();

for (int i = 0; i <= nums.length; i++) {
freq.add(new ArrayList<>());
}


✔️ Now freq.size() == nums.length + 1
✔️ freq.get(i) is SAFE

🔹 Method 2: Using Collections.nCopies (ADVANCED)
List<List<Integer>> freq =
new ArrayList<>(Collections.nCopies(nums.length + 1, new ArrayList<>()));


⚠️ WARNING:
All indices point to SAME list → ❌ bug

freq.get(1).add(10);
freq.get(2);  // also contains 10 ❌


❌ Avoid for this problem

🔹 Method 3: Array (if interviewer allows)
List<Integer>[] freq = new ArrayList[nums.length + 1];

for (int i = 0; i <= nums.length; i++) {
freq[i] = new ArrayList<>();
}


✔️ Works
⚠️ Slightly less clean Java

✅ WHAT YOU SHOULD REMEMBER (INTERVIEW GOLD)

ArrayList constructor sets CAPACITY, not SIZE

❌ This is wrong thinking
new ArrayList<>(5) → size = 5 ❌

✅ Correct thinking
new ArrayList<>(5) → size = 0 ✅

🎯 FINAL RECOMMENDATION (FOR TOP K FREQUENT)

✔️ Use this every time:

List<List<Integer>> freq = new ArrayList<>();
for (int i = 0; i <= nums.length; i++) {
freq.add(new ArrayList<>());
}

🔥 QUICK MEMORY TRICK

Capacity is invisible, size is real

If you want, next I can:

Draw memory diagram

Compare Array vs ArrayList

Show why LeetCode prefers this pattern

Just say 👍

ok y this i <= nums.length not < y <=

🔥 Very sharp observation — this is exactly where most people get confused
Let’s break it slowly, visually, and logically.

❓ Question

Why do we write:

for (int i = 0; i <= nums.length; i++)


❌ not

i < nums.length

🧠 ROOT REASON (MOST IMPORTANT)

👉 Frequency = how many times a number appears

For an array of length n:

Minimum frequency = 1

Maximum frequency = n

So we need buckets for:

index = 0, 1, 2, 3, ..., n


That is n + 1 buckets

🔢 DRY RUN EXAMPLE
Input
nums = [1,2,2,3,3,3]


Length:

nums.length = 6

Frequencies
Number	Frequency
1	1
2	2
3	3

👉 Maximum possible frequency = 6

So buckets needed:

freq[0]  → unused
freq[1]  → numbers appearing once
freq[2]  → numbers appearing twice
freq[3]  → numbers appearing thrice
freq[4]
freq[5]
freq[6]


That’s 7 buckets → indices 0 to 6

❌ WHAT IF YOU USE < nums.length?
for (int i = 0; i < nums.length; i++)


Creates buckets:

0,1,2,3,4,5


❌ Missing:

freq[6]   ❌


Now imagine this input:

nums = [5,5,5,5,5,5]


Frequency of 5 = 6

freq[6].add(5);   // 💥 IndexOutOfBoundsException

✅ WHY <= nums.length IS CORRECT
for (int i = 0; i <= nums.length; i++)


Creates:

0,1,2,3,4,5,6


✔️ Covers maximum possible frequency
✔️ Safe for all cases

📌 WHY INDEX 0 EXISTS AT ALL?

Good question!

No number appears 0 times

But using index = frequency

So index i directly represents frequency i

Index 0 is unused → that's okay

This makes code clean and direct

🧠 ONE-LINE INTERVIEW ANSWER

We use <= nums.length because an element can appear nums.length times, so we need nums.length + 1 buckets to avoid index out of bounds.

🧪 VISUAL SUMMARY
Loop Condition	Buckets Created	Safe?
< nums.length	0 → n-1	❌
<= nums.length	0 → n	✅
🔥 REMEMBER THIS FOREVER

When index = frequency → always size = n + 1

If you want, next I can:

Do full dry run of Top K Frequent

Explain why bucket sort is O(n)

Compare heap vs bucket

Just tell me 🚀

List<List<Integer>> freq = new ArrayList<>();
for(int i = 0; i<nums.length+1;i++){
freq[i] = new ArrayList<>();
} what abt this

Excellent question 👍
This is a very common Java mistake, so let’s clear it once and for all.

❌ YOUR CODE (WHY IT DOES NOT WORK)
List<List<Integer>> freq = new ArrayList<>();
for (int i = 0; i < nums.length + 1; i++) {
freq[i] = new ArrayList<>();   // ❌ WRONG
}

❌ Problem #1: ArrayList is NOT an array
freq[i]


👉 This works only for arrays, NOT for ArrayList.

ArrayList does not support index assignment like this.

❌ Problem #2: ArrayList has size = 0
new ArrayList<>();


Capacity = 10 (internal)

Size = 0

You cannot access index 0 until you add elements.

So freq[i] causes:

💥 IndexOutOfBoundsException

✅ CORRECT WAY #1 (MOST COMMON & BEST)
✔️ Use add() to grow the list
List<List<Integer>> freq = new ArrayList<>();

for (int i = 0; i <= nums.length; i++) {
freq.add(new ArrayList<>());
}

✔️ Now this works safely:
freq.get(3).add(10);

🔍 DRY RUN
nums.length = 6

Loop runs from:

i = 0 → 6


After loop:

freq.size() = 7


Indexes available:

freq.get(0) ... freq.get(6)


Perfect for frequency buckets ✔️

✅ CORRECT WAY #2 (WITH INITIAL CAPACITY)

⚠️ Important: capacity ≠ size

List<List<Integer>> freq = new ArrayList<>(nums.length + 1);

for (int i = 0; i <= nums.length; i++) {
freq.add(new ArrayList<>());
}


✔️ Slightly more efficient
✔️ Still MUST use add()

❌ THIS WILL STILL FAIL
List<List<Integer>> freq = new ArrayList<>(nums.length + 1);
freq.get(0);   // 💥 ERROR


Because size is still 0

🧠 ARRAY vs ARRAYLIST (VERY IMPORTANT)
Feature	Array	ArrayList
Index assignment	arr[i] = x	❌ Not allowed
Add element	Fixed size	add()
Size known	Yes	Dynamic
Access	arr[i]	get(i)
🔥 INTERVIEW GOLDEN RULE

ArrayList grows only with add() — capacity alone does nothing

✅ FINAL CORRECT BUCKET CODE (CLEAN)
List<List<Integer>> freq = new ArrayList<>();

for (int i = 0; i <= nums.length; i++) {
freq.add(new ArrayList<>());
}


If you want next, I can:

Do full Top K Frequent dry run

Fix your earlier solution step-by-step

Explain why bucket sort beats heap here

Just say 👍

class Solution {
public int[] topKFrequent(int[] nums, int k) {
HashMap<Integer,Integer> map = new HashMap<>();
for(int i : nums){
map.put(i,map.getOrDefault(i,0)+1);
}
List<List<Integer>> freq = new ArrayList<>();
for(int i = 0; i<=nums.length;i++){
freq.add(new ArrayList<>());
}
for(Map.Entry<Integer,Integer> entry : map.entrySet()){
int key = entry.getKey();
int value = entry.getValue();
freq[value].add(key);
}
int[] ans = new int[k];
int index=0;
for(int i=freq.length-1;i>0&&index<k;i--){
for(int n : freq[i]){
ans[index++]=n;
if(index==k) return ans;
}
}
return ans;
}
}
is this right?

You are very close 👍
The idea is 100% correct (bucket sort), but there are 2 Java mistakes that will cause compile-time errors.

Let’s fix them precisely and then do a quick dry run so it clicks.

❌ MISTAKE 1: freq[value].add(key)
freq[value].add(key);   // ❌ WRONG

Why?

freq is an ArrayList, not an array.

👉 You must use .get(index) to access elements.

✅ Fix
freq.get(value).add(key);

❌ MISTAKE 2: freq.length
for (int i = freq.length - 1; i > 0 && index < k; i--) {

Why?

ArrayList does not have length.

Arrays → length

ArrayList → size()

✅ Fix
for (int i = freq.size() - 1; i > 0 && index < k; i--) {

✅ FINAL CORRECT CODE (INTERVIEW-READY)
class Solution {
public int[] topKFrequent(int[] nums, int k) {

        // Step 1: Frequency map
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        // Step 2: Bucket list
        List<List<Integer>> freq = new ArrayList<>();
        for (int i = 0; i <= nums.length; i++) {
            freq.add(new ArrayList<>());
        }

        // Step 3: Put numbers into buckets
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            freq.get(value).add(key);
        }

        // Step 4: Collect top k frequent
        int[] ans = new int[k];
        int index = 0;

        for (int i = freq.size() - 1; i > 0 && index < k; i--) {
            for (int n : freq.get(i)) {
                ans[index++] = n;
                if (index == k) return ans;
            }
        }
        return ans;
    }
}

🧠 QUICK DRY RUN
Input
nums = [1,2,2,3,3,3]
k = 2

Frequency map
1 → 1
2 → 2
3 → 3

Buckets (freq)
index: elements
0 → []
1 → [1]
2 → [2]
3 → [3]

Traverse from back
i = 3 → pick 3
i = 2 → pick 2

Output
[3, 2]


✔ Correct

⏱️ COMPLEXITY (WHAT INTERVIEWERS LOVE)
Metric	Value
Time	O(n)
Space	O(n)
Technique	Bucket Sort
Beats Heap?	✅ Yes
🔥 FINAL VERDICT

✅ Logic: Correct
❌ Java syntax: 2 small fixes
🎯 Interview-level solution: YES