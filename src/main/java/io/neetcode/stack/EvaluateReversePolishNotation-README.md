Good you shared your exact code 👍
This solution is very close, but there are 3 fundamental Java mistakes happening together. I’ll explain why it fails, line by line, and then show the correct version with a full dry run.

❌ YOUR CODE (for reference)
class Solution {
public int evalRPN(String[] tokens) {
Stack<Integer> s = new Stack<>();
for(String c : tokens){
if(c=="+"){
int se = s.pop();
int fi = s.pop();
s.push(se+fi);
} if(c=="-"){
int se = s.pop();
int fi = s.pop();
s.push(se-fi);
} if(c=="*"){
int se = s.pop();
int fi = s.pop();
s.push(se*fi);
} if(c=="/"){
int se = s.pop();
int fi = s.pop();
s.push(se/fi);
}
else {
s.push(String.valueOf(c));
}
}
return s.pop();
}
}

🚨 PROBLEM 1: == with Strings (BIGGEST ISSUE)
if (c == "+")

❌ Why wrong?

In Java:

== compares memory references

NOT string content

So "+", "-", "*", "/" comparisons FAIL

✅ Correct
c.equals("+")

🚨 PROBLEM 2: Wrong operand order for - and /

Stack pop order:

int second = s.pop();
int first = s.pop();

❌ Your code
s.push(se - fi);   // WRONG
s.push(se / fi);   // WRONG

✅ Correct
s.push(first - second);
s.push(first / second);


RPN rule:
a b - → a - b

🚨 PROBLEM 3: Pushing STRING into Stack<Integer>
s.push(String.valueOf(c)); // ❌

❌ Why wrong?

Your stack is:

Stack<Integer> s


You must push integers, not strings.

✅ Correct
s.push(Integer.parseInt(c));

🚨 PROBLEM 4: Multiple if instead of else if

Your code:

if(c=="+"){...}
if(c=="-"){...}
if(c=="*"){...}
if(c=="/"){...}
else {...}

❌ Why wrong?

else only belongs to last if

Other ifs still execute

✅ Correct

Use else if OR switch

✅ FULLY CORRECT VERSION (CLEAN & INTERVIEW READY)
class Solution {
public int evalRPN(String[] tokens) {
Stack<Integer> s = new Stack<>();

        for (String c : tokens) {
            if (c.equals("+")) {
                int b = s.pop();
                int a = s.pop();
                s.push(a + b);
            }
            else if (c.equals("-")) {
                int b = s.pop();
                int a = s.pop();
                s.push(a - b);
            }
            else if (c.equals("*")) {
                int b = s.pop();
                int a = s.pop();
                s.push(a * b);
            }
            else if (c.equals("/")) {
                int b = s.pop();
                int a = s.pop();
                s.push(a / b);
            }
            else {
                s.push(Integer.parseInt(c));
            }
        }
        return s.pop();
    }
}

🧪 FULL DRY RUN (Example)
Input
tokens = ["2","1","+","3","*"]

Step-by-step
Token	Stack
"2"	[2]
"1"	[2,1]
"+"	pop 1,2 → push 3 → [3]
"3"	[3,3]
"*"	pop 3,3 → push 9 → [9]
Output
9

⏱ Time & Space Complexity
Metric	Value
Time	O(n)
Space	O(n)
🧠 INTERVIEW TAKEAWAYS (VERY IMPORTANT)

❌ Never use == for strings

✔ Always parse numbers explicitly

✔ Operand order matters in RPN

✔ Stack problems demand careful pop order

If you want next:

Why division truncates toward zero

Switch-based version

Edge cases

Interview explanation script

Just say 👍