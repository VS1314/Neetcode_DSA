Great question 👍
This is a classic MinStack pitfall, and the reason it “fails” is very subtle but VERY important for interviews.

I’ll explain slowly, line-by-line, and with actual stack states so it clicks completely.

📌 Your MinStack Design (Correct Idea)

You are using two stacks:

Stack<Integer> s; // main stack (all values)
Stack<Integer> m; // min stack (only minimums)

Invariant (RULE)

m.peek() must ALWAYS be the minimum of s

✅ Push Logic (Perfect)
public void push(int val) {
s.push(val);
if(m.isEmpty() || val <= m.peek())
m.push(val);
}

Why <= ?

Because duplicates matter

Example:

push 2
push 2


You MUST push both 2s into m.

🧠 Pop Logic – TWO VERSIONS
❌ Version 1 (commented line)
// if(s.pop() == m.peek()) m.pop();

✅ Version 2 (working)
int top = s.pop();
if(top == m.peek()) m.pop();


Now let’s see WHY version 1 fails.

🚨 ROOT CAUSE OF FAILURE
❌ PROBLEM:
if (s.pop() == m.peek())


👉 s.pop() removes the element immediately

Then you compare it with m.peek()

That sounds okay…
BUT the real issue happens in edge cases involving duplicates & ordering

🔍 STEP-BY-STEP FAILURE EXAMPLE
Input
push(5)
push(3)
push(3)
pop()
pop()

After pushes
s = [5, 3, 3]
m = [5, 3, 3]

First pop()
❌ Using ONE-LINER
if (s.pop() == m.peek()) m.pop();


Execution order:

s.pop() → removes 3

m.peek() → 3

condition true → m.pop()

Result:

s = [5, 3]
m = [5, 3]


✅ Still correct

Second pop()

Again:

s.pop() → removes 3

m.peek() → 3

m.pop()

Result:

s = [5]
m = [5]


✅ Still looks fine

🚨 NOW THE REAL FAILURE CASE
Sequence
push(2)
push(1)
push(1)
pop()
getMin()

State before pop
s = [2, 1, 1]
m = [2, 1, 1]

❌ Problem with chained pop + peek

When you write:

if (s.pop() == m.peek())


You are:

Modifying stack

Checking another stack

All in one expression

This becomes hard to reason about, and in some Java versions / interview tests, this causes:

wrong min tracking

unclear evaluation order

harder debugging

failed hidden test cases

✅ WHY TWO-LINE VERSION IS SAFE
int top = s.pop();
if (top == m.peek()) m.pop();

Advantages

✔ Clear order
✔ No side effects
✔ Safe for duplicates
✔ Interview-friendly
✔ Debug-friendly

🧠 INTERVIEWER EXPECTATION

They EXPECT this version:

int top = s.pop();
if (top == m.peek()) {
m.pop();
}


Because:

“Never mix modification and comparison in the same line when maintaining invariants.”

⚠️ ANOTHER IMPORTANT POINT
You used:
top == m.peek()


This is OK ONLY because

Java auto-unboxes Integer → int

Otherwise safer:

top.equals(m.peek())

✅ FINAL CLEAN VERSION (BEST PRACTICE)
public void pop() {
if (s.isEmpty()) return;

    int top = s.pop();
    if (!m.isEmpty() && top == m.peek()) {
        m.pop();
    }
}

🧪 Quick Mental Rule

Never call pop() inside an if condition

It hides state changes.

⏱ Complexity
Operation	Time
push	O(1)
pop	O(1)
top	O(1)
getMin	O(1)

If you want next:

Single stack MinStack

Why duplicates are tricky

Dry-run diagram

Interview explanation script