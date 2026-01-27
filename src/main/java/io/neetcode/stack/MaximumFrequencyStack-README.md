Perfect question 👍
This problem looks scary, but once the idea clicks, it becomes very mechanical.

I’ll explain in layers:

❌ Why normal stack / heap fails

🧠 Core intuition (the “aha” moment)

🧱 Data structures used (VERY important)

🔁 Step-by-step dry run (your example)

✅ Final Java code

⏱️ Complexity

🧠 One-line memory hook

1️⃣ Why a normal stack is NOT enough

A normal stack gives:

Last pushed element

But we need:

Most frequent element

If tie → most recently pushed among them

So we need two priorities:

Frequency (max first)

Recency (stack order)

➡️ One structure can’t handle both cleanly.

2️⃣ 🧠 Key Insight (THIS is the trick)

Instead of:

“Find the most frequent element every time”

We flip the thinking:

Group elements by their frequency

That’s the magic.

3️⃣ 🧱 Data Structures Used

We use THREE things:

1️⃣ freqMap
Map<Integer, Integer> freq;


Tracks how many times each value appears

Example: {5=3, 7=2, 4=1}

2️⃣ group
Map<Integer, Stack<Integer>> group;


Key = frequency

Value = stack of elements that reached that frequency

WHY stack?
→ To resolve tie by most recent

Example:

freq = 3 → [5]
freq = 2 → [7, 5]
freq = 1 → [5, 7, 4]

3️⃣ maxFreq
int maxFreq;


Keeps track of current highest frequency

So pop() is O(1)

4️⃣ 🔁 FULL DRY RUN (YOUR EXAMPLE)
Input
push: 5, 7, 5, 7, 4, 5

After pushes
Push 5
freq[5] = 1
group[1] = [5]
maxFreq = 1

Push 7
freq[7] = 1
group[1] = [5, 7]

Push 5
freq[5] = 2
group[2] = [5]
maxFreq = 2

Push 7
freq[7] = 2
group[2] = [5, 7]

Push 4
freq[4] = 1
group[1] = [5, 7, 4]

Push 5
freq[5] = 3
group[3] = [5]
maxFreq = 3

Now POP operations
Pop #1
maxFreq = 3
group[3].pop() → 5
freq[5] becomes 2
group[3] empty → maxFreq--


Returned: 5

Pop #2
maxFreq = 2
group[2] = [5, 7]
pop → 7 (most recent)
freq[7] becomes 1


Returned: 7

Pop #3
maxFreq = 2
group[2] = [5]
pop → 5
freq[5] becomes 1
group[2] empty → maxFreq--


Returned: 5

Pop #4
maxFreq = 1
group[1] = [5, 7, 4]
pop → 4


Returned: 4

✅ Matches expected output.

5️⃣ ✅ CLEAN JAVA IMPLEMENTATION
class FreqStack {

    private Map<Integer, Integer> freq;
    private Map<Integer, Stack<Integer>> group;
    private int maxFreq;

    public FreqStack() {
        freq = new HashMap<>();
        group = new HashMap<>();
        maxFreq = 0;
    }

    public void push(int val) {
        int f = freq.getOrDefault(val, 0) + 1;
        freq.put(val, f);

        if (!group.containsKey(f)) {
            group.put(f, new Stack<>());
        }
        group.get(f).push(val);

        maxFreq = Math.max(maxFreq, f);
    }

    public int pop() {
        int val = group.get(maxFreq).pop();
        freq.put(val, freq.get(val) - 1);

        if (group.get(maxFreq).isEmpty()) {
            maxFreq--;
        }
        return val;
    }
}

6️⃣ ⏱️ Time & Space Complexity
Operation	Complexity
push	O(1)
pop	O(1)
Space	O(n)

This is optimal.

7️⃣ 🧠 One-Line Memory Hook

“Group values by frequency, stack handles recency.”

If you remember just this, you’ll reconstruct the solution in interviews.

🎯 Why Interviewers Love This Problem

Because it tests:

HashMap

Stack

Priority logic

Data structure design

Clean O(1) optimization