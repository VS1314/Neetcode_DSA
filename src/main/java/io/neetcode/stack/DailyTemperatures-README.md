Perfect problem to cement the monotonic stack pattern.
I’ll explain this from zero, then show code, then do a full dry-run step by step so nothing is left confusing.

🧠 Core Idea (in simple words)

For each day, you want to know:

How many days until a warmer temperature appears?

Instead of checking every future day (O(n²)), we do this:

👉 When a warmer day arrives, it answers the question for all colder days before it.

That’s why we use a stack.

📦 What do we store in the stack?

We store indices, not temperatures.

Why?

We need distance → currentIndex - previousIndex

Temperature is accessed via temperatures[index]

📉 Stack Property (VERY IMPORTANT)

The stack stores indices such that:

temperatures[stack[0]] > temperatures[stack[1]] > temperatures[stack[2]] ...


👉 Monotonically decreasing temperatures

This means:

Stack top = most recent unresolved colder day

✅ Algorithm (High Level)

Create result[] initialized with 0

Create an empty stack

Traverse days from left → right

While current temperature is warmer than stack top

Pop index

Fill result for that index

Push current index

Unresolved days stay 0

✅ Java Code (Clean & Correct)
class Solution {
public int[] dailyTemperatures(int[] temperatures) {
int n = temperatures.length;
int[] result = new int[n];
Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() &&
                   temperatures[i] > temperatures[stack.peek()]) {

                int prevDay = stack.pop();
                result[prevDay] = i - prevDay;
            }
            stack.push(i);
        }
        return result;
    }
}

🔍 LINE-BY-LINE EXPLANATION
int[] result = new int[n];


✔ Default initialized to 0
✔ If no warmer day exists → result remains 0

Stack<Integer> stack = new Stack<>();


✔ Stores indices of days
✔ Not temperatures!

for (int i = 0; i < n; i++) {


✔ Traverse days left → right

while (!stack.isEmpty() &&
temperatures[i] > temperatures[stack.peek()]) {


This means:

Today is warmer

Stack top day finally gets its answer

int prevDay = stack.pop();
result[prevDay] = i - prevDay;


✔ We now know:

Days waited = currentDay - previousDay

stack.push(i);


✔ Current day is unresolved → wait for a warmer future day

🧪 FULL DRY RUN (VERY IMPORTANT)
Input
temperatures = [30,38,30,36,35,40,28]

Day 0 → 30

Stack: []

Push 0
👉 stack = [0]

Day 1 → 38

38 > 30 → pop 0

result[0] = 1 - 0 = 1

Push 1
👉 stack = [1]

Day 2 → 30

30 < 38 → no pop

Push 2
👉 stack = [1, 2]

Day 3 → 36

36 > 30 → pop 2 → result[2] = 1

36 < 38 → stop

Push 3
👉 stack = [1, 3]

Day 4 → 35

35 < 36 → push
👉 stack = [1, 3, 4]

Day 5 → 40

40 > 35 → pop 4 → result[4] = 1

40 > 36 → pop 3 → result[3] = 2

40 > 38 → pop 1 → result[1] = 4

Push 5
👉 stack = [5]

Day 6 → 28

28 < 40 → push
👉 stack = [5, 6]

End

Remaining indices → no warmer day → result stays 0

✅ Final Output
[1,4,1,2,1,0,0]

🧩 Pattern Recognition (VERY IMPORTANT)

This problem is:

Next Greater Element to the Right

Same pattern used in:

Daily Temperatures

Next Greater Element I & II

Stock Span

Asteroid Collision (partially)

Largest Rectangle in Histogram

🧠 One-Line Memory Trick

“Stack holds unresolved days.
A warmer day resolves all colder days before it.”