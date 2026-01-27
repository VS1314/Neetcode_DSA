Nice, this is a classic stack + nested structure problem.
I’ll explain it from zero, then walk character by character, then give clean code.

🧠 Decode String — Intuition First

Encoding rule:

k[encoded_string]


means:

repeat encoded_string exactly k times


Example:

2[a3[b]]c


You cannot decode left-to-right in one pass because:

numbers apply after you see ]

nested brackets exist

➡️ This screams STACK.

🧩 What do we need to remember?

When we see:

2[a3[b]]


At [ we need to remember:

how many times (k)

what string existed before this bracket

So we use two stacks:

Stack	Stores
countStack	repetition counts
stringStack	previous strings
🔑 Core Algorithm (Plain English)

Traverse characters one by one:

If digit:

build number (can be multi-digit)

If [

push current string into stringStack

push current number into countStack

reset current string & number

If letter:

append to current string

If ]

pop repeat count

pop previous string

repeat current string

attach to previous string

🧪 FULL DRY RUN (VERY IMPORTANT)
Input
"2[a3[b]]c"

Variables
countStack = []
stringStack = []
currentString = ""
currentNum = 0

Step-by-step execution
2
digit → currentNum = 2

[
push "" into stringStack
push 2 into countStack
reset currentString = ""
reset currentNum = 0


Stacks:

stringStack = [""]
countStack  = [2]

a
currentString = "a"

3
currentNum = 3

[
push "a" into stringStack
push 3 into countStack
reset currentString = ""
reset currentNum = 0


Stacks:

stringStack = ["", "a"]
countStack  = [2, 3]

b
currentString = "b"

]

Pop and build:

repeat = 3
prevString = "a"
currentString = "a" + "b"*3 = "abbb"


Stacks:

stringStack = [""]
countStack  = [2]

]

Pop again:

repeat = 2
prevString = ""
currentString = "" + "abbb"*2 = "abbbabbb"


Stacks empty now.

c
currentString = "abbbabbbc"

✅ Final Answer
"abbbabbbc"

🧠 Why STACK Works Here

Nested brackets = Last opened must close first

That’s LIFO → Stack

Same reason:

Valid Parentheses

Simplify Path

Decode String

✅ CLEAN & CORRECT JAVA CODE
class Solution {
public String decodeString(String s) {
Stack<Integer> countStack = new Stack<>();
Stack<String> stringStack = new Stack<>();

        StringBuilder curr = new StringBuilder();
        int num = 0;

        for (char c : s.toCharArray()) {

            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } 
            else if (c == '[') {
                countStack.push(num);
                stringStack.push(curr.toString());
                curr = new StringBuilder();
                num = 0;
            } 
            else if (c == ']') {
                int repeat = countStack.pop();
                String prev = stringStack.pop();

                StringBuilder temp = new StringBuilder(prev);
                for (int i = 0; i < repeat; i++) {
                    temp.append(curr);
                }
                curr = temp;
            } 
            else {
                curr.append(c);
            }
        }
        return curr.toString();
    }
}

⏱ Complexity
Metric	Value
Time	O(n)
Space	O(n)
🔁 Pattern Recognition (IMPORTANT)

Decode String is the same pattern as:

Nested parentheses

Expression evaluation

JSON/XML parsing

HTML parsing

🧠 One-line takeaway (memorize)

Whenever decoding nested patterns → store previous state using a stack.