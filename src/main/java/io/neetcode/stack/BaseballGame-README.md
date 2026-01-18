Perfect problem to understand STACK pattern 👍
I’ll explain this very slowly, from why stack, to each operation, to line-by-line code, and then full dry run.

1️⃣ Why this problem screams STACK

Look at the rules:

"C" → remove last score

"D" → use last score

"+" → use last two scores

👉 All operations depend on the most recent scores
👉 This is LIFO (Last In First Out) behavior

📌 STACK is the correct data structure

2️⃣ What the stack represents

The stack stores valid scores so far

Example:

record = [1, 2, 5, 10]


Top of stack = latest score

3️⃣ Clean & correct Java solution
class Solution {
public int calPoints(String[] ops) {
Stack<Integer> stack = new Stack<>();

        for (String op : ops) {
            if (op.equals("C")) {
                stack.pop();
            } 
            else if (op.equals("D")) {
                stack.push(2 * stack.peek());
            } 
            else if (op.equals("+")) {
                int last = stack.pop();
                int secondLast = stack.peek();
                stack.push(last);
                stack.push(last + secondLast);
            } 
            else {
                stack.push(Integer.parseInt(op));
            }
        }

        int sum = 0;
        for (int score : stack) {
            sum += score;
        }
        return sum;
    }
}

4️⃣ LINE-BY-LINE EXPLANATION
🔹 Create stack
Stack<Integer> stack = new Stack<>();


Stores valid scores

🔹 Loop through operations
for (String op : ops)


Process each operation in order

🟥 Operation "C" → Cancel last score
stack.pop();


Removes last added score

🟦 Operation "D" → Double last score
stack.push(2 * stack.peek());


peek() → get last score

Multiply by 2

Push back

🟩 Operation "+" → Sum of last two scores
int last = stack.pop();
int secondLast = stack.peek();
stack.push(last);
stack.push(last + secondLast);


WHY POP?

Because:

Stack does NOT allow random access

We temporarily remove top to read second top

After reading:

Push back the popped value

Push sum

🟨 Number → Normal score
stack.push(Integer.parseInt(op));


Convert string → int → push

🔢 Final sum
for (int score : stack) {
sum += score;
}


Add all valid scores

5️⃣ FULL DRY RUN (Example 1)
Input:
["1","2","+","C","5","D"]

Step 1: "1"
stack = [1]

Step 2: "2"
stack = [1, 2]

Step 3: "+"
pop → 2
peek → 1
push 2 back
push 3

stack = [1, 2, 3]

Step 4: "C"
pop 3

stack = [1, 2]

Step 5: "5"
stack = [1, 2, 5]

Step 6: "D"
peek = 5
push 10

stack = [1, 2, 5, 10]

Final Sum:
1 + 2 + 5 + 10 = 18


✅ Correct

6️⃣ Key Interview Takeaways ⭐

✔ Stack because recent history matters
✔ "+" requires temporary pop
✔ Time complexity: O(n)
✔ Space complexity: O(n)

7️⃣ Pattern Recognition (IMPORTANT)

This problem belongs to:

🔥 "Stack for previous elements" pattern

Other similar problems:

Daily Temperatures

Next Greater Element

Remove K Digits

Simplify Path