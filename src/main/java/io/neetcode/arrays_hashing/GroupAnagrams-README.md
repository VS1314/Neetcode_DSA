1️⃣ Pattern Identification (before code)
Keywords in the problem:

“group anagrams”

“same characters, different order”

“return sublists”

Mental trigger 🚦

Whenever you see:

group items by some derived property

👉 Think Hashing + Grouping pattern

2️⃣ Exact Pattern Name 🏷️
✅ HashMap Grouping with Canonical Key

Also known as:

Frequency Signature Pattern

Anagram Grouping Pattern

3️⃣ Why hashing is mandatory here

You need:

Fast lookup

One bucket per anagram group

So:

key → list of anagrams


That screams HashMap.

4️⃣ Key insight (MOST IMPORTANT)
Anagrams share the same:

character frequency

Example:

Word	Frequency Signature
act	a1 c1 t1
cat	a1 c1 t1
pots	p1 o1 t1 s1
tops	p1 o1 t1 s1

👉 Order does NOT matter
👉 Frequency DOES

5️⃣ Step-by-step explanation of YOUR code
Code you wrote:
HashMap<String, List<String>> res = new HashMap<>();


📌 Map:

Key → frequency signature

Value → list of anagrams

For each string:
for (String s : strs) {
int[] ch = new int[26];


You create a frequency array for lowercase letters.

Count characters:
for (char c : s.toCharArray()) {
ch[c - 'a']++;
}


Example "act" becomes:

[1,0,1,0,...,1,...]   // a=1, c=1, t=1

Convert frequency array to a key:
String key = Arrays.toString(ch);


This creates a unique, hashable representation.

Why?

Arrays can’t be map keys

Strings can

Group into map:
res.putIfAbsent(key, new ArrayList<>());
res.get(key).add(s);


💡 Meaning:

If group doesn’t exist → create it

Add current word to its group

6️⃣ Final return
return new ArrayList<>(res.values());


We only care about grouped values, not keys.

7️⃣ Dry run on example 🧪

Input:

["act","pots","tops","cat","stop","hat"]


Map becomes:

"a1c1t1" → ["act","cat"]
"p1o1t1s1" → ["pots","tops","stop"]
"h1a1t1" → ["hat"]


Output:

[["hat"],["act","cat"],["stop","pots","tops"]]


Order doesn’t matter ✅

8️⃣ Time & Space Complexity

Let:

n = number of strings

k = max string length (≤ 100)

⏱ Time:
O(n * k)


(Counting characters)

📦 Space:
O(n * k)


(HashMap + frequency keys)

✔ Optimal solution

9️⃣ Alternative approach (for awareness)
Sorting-based key
char[] arr = s.toCharArray();
Arrays.sort(arr);
String key = new String(arr);


⛔ Slower:

O(n * k log k)


Your solution is better.

🔟 Pattern recognition summary 🧠

When you see:

Problem	Pattern
Group Anagrams	HashMap + Frequency Signature
Group by property	HashMap Grouping
Same chars, different order	Frequency / Sorted Key
🎯 Interview-ready one-liner

“I grouped strings using a HashMap with a character frequency signature as the key, which uniquely identifies anagrams in linear time.”