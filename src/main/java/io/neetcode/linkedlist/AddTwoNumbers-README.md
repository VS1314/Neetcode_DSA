# Add Two Numbers

## Problem Description

**Difficulty**: Medium

You are given two **non-empty** linked lists, `l1` and `l2`, where each represents a non-negative integer.

The digits are stored in **reverse order**, e.g. the number `321` is represented as `1 -> 2 -> 3` in the linked list.

Each of the nodes contains a single digit. You may assume the two numbers do not contain any leading zero, except the number `0` itself.

Return the sum of the two numbers as a linked list.

**Key Insights:**
- Numbers stored in **reverse order** (least significant digit first)
- Each node contains **single digit** (0-9)
- Result should also be in **reverse order**
- Must handle **carry** when sum ≥ 10
- Lists can have **different lengths**

**Visual Example:**
```
Number 321 represented as: 1 -> 2 -> 3
Number 654 represented as: 4 -> 5 -> 6

Addition (reverse order):
  1 + 4 = 5 (carry = 0)
  2 + 5 = 7 (carry = 0)
  3 + 6 = 9 (carry = 0)

Result: 5 -> 7 -> 9 (represents 975)
```

## Examples

### Example 1:
```
Input: l1 = [1,2,3], l2 = [4,5,6]
Output: [5,7,9]

Explanation: 
  321 + 654 = 975
  Reverse representation:
    l1: 1 -> 2 -> 3 (321)
    l2: 4 -> 5 -> 6 (654)
    sum: 5 -> 7 -> 9 (975)

Step-by-step:
  Position 0: 1 + 4 = 5, carry = 0
  Position 1: 2 + 5 = 7, carry = 0
  Position 2: 3 + 6 = 9, carry = 0
  Result: [5, 7, 9]
```

### Example 2:
```
Input: l1 = [9], l2 = [9]
Output: [8,1]

Explanation:
  9 + 9 = 18
  
Step-by-step:
  Position 0: 9 + 9 = 18
    digit = 18 % 10 = 8
    carry = 18 / 10 = 1
  Position 1: 0 + 0 + carry(1) = 1
    digit = 1
    carry = 0
  
  Result: [8, 1] (represents 18)
```

### Example 3:
```
Input: l1 = [9,9,9], l2 = [1]
Output: [0,0,0,1]

Explanation:
  999 + 1 = 1000
  
Step-by-step:
  Position 0: 9 + 1 = 10, digit = 0, carry = 1
  Position 1: 9 + 0 + carry(1) = 10, digit = 0, carry = 1
  Position 2: 9 + 0 + carry(1) = 10, digit = 0, carry = 1
  Position 3: 0 + 0 + carry(1) = 1, digit = 1, carry = 0
  
  Result: [0, 0, 0, 1] (represents 1000)
```

### Example 4:
```
Input: l1 = [0], l2 = [0]
Output: [0]

Explanation:
  0 + 0 = 0
  Simple case with no carry
```

### Example 5:
```
Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]

Explanation:
  342 + 465 = 807
  
Step-by-step:
  Position 0: 2 + 5 = 7, carry = 0
  Position 1: 4 + 6 = 10, digit = 0, carry = 1
  Position 2: 3 + 4 + carry(1) = 8, carry = 0
  
  Result: [7, 0, 8]
```

### Example 6:
```
Input: l1 = [9,9], l2 = [1]
Output: [0,0,1]

Explanation:
  99 + 1 = 100
  Carry propagates through multiple digits
```

### Example 7:
```
Input: l1 = [1,8], l2 = [0]
Output: [1,8]

Explanation:
  81 + 0 = 81
  Adding zero, result is same as l1
```

### Example 8:
```
Input: l1 = [5], l2 = [5]
Output: [0,1]

Explanation:
  5 + 5 = 10
  Single digit addition with carry
```

### Example 9:
```
Input: l1 = [1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1]
       l2 = [5,6,4]
Output: [6,6,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1]

Explanation:
  Very long number + short number
  l1 is much longer than l2
```

### Example 10:
```
Input: l1 = [9,9,9,9], l2 = [9,9,9,9]
Output: [8,9,9,9,1]

Explanation:
  9999 + 9999 = 19998
  
Step-by-step:
  Position 0: 9 + 9 = 18, digit = 8, carry = 1
  Position 1: 9 + 9 + 1 = 19, digit = 9, carry = 1
  Position 2: 9 + 9 + 1 = 19, digit = 9, carry = 1
  Position 3: 9 + 9 + 1 = 19, digit = 9, carry = 1
  Position 4: 0 + 0 + 1 = 1, digit = 1, carry = 0
  
  Result: [8, 9, 9, 9, 1]
```

## Constraints
- `1 <= l1.length, l2.length <= 100`
- `0 <= Node.val <= 9`
- The two numbers do **not** contain leading zeros (except 0 itself)
- Each node contains a **single digit**

**Recommended Complexity**: 
- Time: O(m + n) where m = length of l1, n = length of l2
- Space: O(1) auxiliary space (output doesn't count)

---

## Pattern Recognition

**Primary Pattern**: **Elementary Addition with Carry (Digit-by-Digit)**

**Why This Pattern?**
- Need to add corresponding digits from both numbers
- Must track **carry** when sum ≥ 10
- Process from **least significant to most significant** digit
- Handle **different length** lists
- May need **extra node** for final carry

**Key Insight**: Simulate Elementary School Addition
```
Normal addition (right to left):
    342
  + 465
  -----
    807
    
  Start from rightmost (ones place)
  2 + 5 = 7
  4 + 6 = 10 (write 0, carry 1)
  3 + 4 + 1(carry) = 8

Linked list (already reversed):
  l1: 2 -> 4 -> 3
  l2: 5 -> 6 -> 4
  
  Start from left (already ones place!)
  2 + 5 = 7
  4 + 6 = 10 (write 0, carry 1)
  3 + 4 + 1 = 8
  
  Result: 7 -> 0 -> 8 ✓
```

**Why Reverse Order Helps**:
```
If stored normally (most significant first):
  342: 3 -> 4 -> 2
  465: 4 -> 6 -> 5
  
  Would need to:
    1. Traverse to end
    2. Add from back
    3. Handle carry going backward
    Complex! ❌

With reverse order:
  342: 2 -> 4 -> 3
  465: 5 -> 6 -> 4
  
  Add from front (already ones place)
  Carry goes forward naturally ✓
  Simple one-pass solution! ✓
```

**The Carry Logic**:
```
At each position:
  sum = digit1 + digit2 + carry
  
  If sum >= 10:
    digit = sum % 10 (last digit)
    carry = sum / 10 (1 for next position)
  Else:
    digit = sum
    carry = 0

Example: 9 + 8 + 1(carry) = 18
  digit = 18 % 10 = 8
  carry = 18 / 10 = 1
  
Next position uses carry = 1
```

**Handling Different Lengths**:
```
Example: 999 + 1

l1: 9 -> 9 -> 9 -> null
l2: 1 -> null

When l2 ends, treat as 0:
  Position 0: 9 + 1 = 10, digit = 0, carry = 1
  Position 1: 9 + 0 = 9 + carry(1) = 10, digit = 0, carry = 1
  Position 2: 9 + 0 = 9 + carry(1) = 10, digit = 0, carry = 1
  Position 3: 0 + 0 + carry(1) = 1
  
Result: 0 -> 0 -> 0 -> 1 ✓
```

**When to Stop**:
```
Continue while:
  1. l1 has nodes, OR
  2. l2 has nodes, OR
  3. carry != 0 (final carry)
  
Example: 9 + 9 = 18
  Position 0: 9 + 9 = 18, carry = 1
  Position 1: null + null + carry(1) = 1
  Must add final node for carry! ✓
```

**Why One Pass Works**:
```
Process left to right (reverse order):
  - Add digits as we go
  - Track carry for next iteration
  - Build result list simultaneously
  - No need to go back
  
One pass through both lists! O(max(m,n))
```

**Dummy Head Technique**:
```
Building result list:
  Use dummy head to avoid special case for first node
  
Without dummy:
  if (head == null):
      head = new Node(digit)  // Special case
  else:
      curr.next = new Node(digit)

With dummy:
  dummy = new Node(0)
  curr = dummy
  
  curr.next = new Node(digit)  // Always same!
  curr = curr.next
  
  return dummy.next  // Skip dummy
  
Cleaner code! ✓
```

**Example: Building Result**
```
Adding 2 + 5 = 7, 4 + 6 = 10, 3 + 4 = 7

Initialize:
  dummy -> null
  curr = dummy

Add 7:
  dummy -> 7 -> null
  curr = curr.next (now at 7)

Add 0:
  dummy -> 7 -> 0 -> null
  curr = curr.next (now at 0)

Add 8:
  dummy -> 7 -> 0 -> 8 -> null
  curr = curr.next (now at 8)

Return dummy.next = 7 -> 0 -> 8 ✓
```

**Alternative: Recursion**:
```
Could solve recursively:
  add(l1, l2, carry):
    if l1 == null and l2 == null and carry == 0:
        return null
    
    val1 = l1.val if l1 else 0
    val2 = l2.val if l2 else 0
    sum = val1 + val2 + carry
    
    node = new Node(sum % 10)
    node.next = add(l1.next, l2.next, sum / 10)
    return node

But iterative is simpler and avoids stack space!
```

**Related Patterns**:
1. **Digit-by-Digit Processing** — Process one digit at a time
2. **Carry Propagation** — Track and propagate carry
3. **Dummy Head** — Simplify list building
4. **Two-Pointer** — Traverse two lists simultaneously

---

## Algorithm & Approach

### Core Insight

**Why One-Pass with Carry Works:**
```
Key observations:
  1. Digits already in reverse order (LSB first)
  2. Add corresponding digits left to right
  3. Track carry for next position
  4. Handle different lengths by treating missing as 0
  5. Add final carry if exists
```

**The Optimal Strategy**:
```
Key steps:
  1. Initialize dummy head and carry = 0
  2. While either list has nodes OR carry exists:
     - Get values (or 0 if null)
     - Calculate sum + carry
     - Create new node with sum % 10
     - Update carry = sum / 10
  3. Return dummy.next
```

### Step-by-Step Algorithm

---

#### **Approach: One-Pass with Carry Tracking - OPTIMAL**

**Core Idea**:
- Traverse both lists simultaneously
- Add digits + carry at each position
- Build result list on the fly
- Handle different lengths naturally
- O(max(m,n)) time, O(1) auxiliary space

**Algorithm**
```
addTwoNumbers(l1, l2):
    dummy = new ListNode(0)
    curr = dummy
    carry = 0
    
    while l1 != null OR l2 != null OR carry != 0:
        // Get values (0 if null)
        val1 = l1.val if l1 != null else 0
        val2 = l2.val if l2 != null else 0
        
        // Calculate sum
        sum = val1 + val2 + carry
        carry = sum / 10
        digit = sum % 10
        
        // Create new node
        curr.next = new ListNode(digit)
        curr = curr.next
        
        // Move pointers
        if l1 != null: l1 = l1.next
        if l2 != null: l2 = l2.next
    
    return dummy.next
```

**Code Implementation**
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // Dummy head to simplify result list building
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;
        
        // Continue while either list has nodes or carry exists
        while (l1 != null || l2 != null || carry != 0) {
            // Get values from current nodes (0 if null)
            int val1 = (l1 != null) ? l1.val : 0;
            int val2 = (l2 != null) ? l2.val : 0;
            
            // Calculate sum and new carry
            int sum = val1 + val2 + carry;
            carry = sum / 10;
            int digit = sum % 10;
            
            // Create new node with digit
            curr.next = new ListNode(digit);
            curr = curr.next;
            
            // Move to next nodes if they exist
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        
        // Return head of result (skip dummy)
        return dummy.next;
    }
}
```

**Example Walkthrough**

Input: `l1 = [2,4,3]`, `l2 = [5,6,4]`
Expected: `[7,0,8]` (342 + 465 = 807)

```
Initial State:
  l1: 2 -> 4 -> 3 -> null
  l2: 5 -> 6 -> 4 -> null
  carry: 0
  dummy: 0 -> null
  curr: dummy
```

**Iteration 1:**
```
l1 = 2, l2 = 5, carry = 0

val1 = 2
val2 = 5
sum = 2 + 5 + 0 = 7
carry = 7 / 10 = 0
digit = 7 % 10 = 7

Create node(7):
  dummy -> 7 -> null
  curr = node(7)

Move pointers:
  l1 = l1.next (now 4)
  l2 = l2.next (now 6)
```

**Iteration 2:**
```
l1 = 4, l2 = 6, carry = 0

val1 = 4
val2 = 6
sum = 4 + 6 + 0 = 10
carry = 10 / 10 = 1
digit = 10 % 10 = 0

Create node(0):
  dummy -> 7 -> 0 -> null
  curr = node(0)

Move pointers:
  l1 = l1.next (now 3)
  l2 = l2.next (now 4)
```

**Iteration 3:**
```
l1 = 3, l2 = 4, carry = 1

val1 = 3
val2 = 4
sum = 3 + 4 + 1 = 8
carry = 8 / 10 = 0
digit = 8 % 10 = 8

Create node(8):
  dummy -> 7 -> 0 -> 8 -> null
  curr = node(8)

Move pointers:
  l1 = l1.next (now null)
  l2 = l2.next (now null)
```

**Loop Check:**
```
l1 == null, l2 == null, carry == 0
Exit loop

Return dummy.next = 7 -> 0 -> 8 ✓
```

**Complexity Analysis**
- **Time**: O(max(m, n)) — Process all digits once
- **Space**: O(1) auxiliary — Only carry and pointers (result doesn't count)

---

**Example with Different Lengths**

Input: `l1 = [9,9,9]`, `l2 = [1]`
Expected: `[0,0,0,1]` (999 + 1 = 1000)

```
Initial: carry = 0
```

**Iteration 1:**
```
l1 = 9, l2 = 1, carry = 0
sum = 9 + 1 + 0 = 10
digit = 0, carry = 1
Result: [0]
```

**Iteration 2:**
```
l1 = 9, l2 = null, carry = 1
val2 = 0 (l2 is null)
sum = 9 + 0 + 1 = 10
digit = 0, carry = 1
Result: [0, 0]
```

**Iteration 3:**
```
l1 = 9, l2 = null, carry = 1
val2 = 0
sum = 9 + 0 + 1 = 10
digit = 0, carry = 1
Result: [0, 0, 0]
```

**Iteration 4:**
```
l1 = null, l2 = null, carry = 1
val1 = 0, val2 = 0
sum = 0 + 0 + 1 = 1
digit = 1, carry = 0
Result: [0, 0, 0, 1]
```

**Loop Check:**
```
All null and carry = 0
Exit
Return [0, 0, 0, 1] ✓
```

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Complexity | Recommended |
|----------|------|-------|------------|-------------|
| **One-Pass with Carry** | **O(max(m,n))** | **O(1)** | **Simple ✅** | **Yes ✅** |
| Recursion | O(max(m,n)) | O(max(m,n)) stack | Medium | No (extra space) |
| Convert to Int | O(m+n) | O(1) | Simple | No (overflow for large numbers) |
| Reverse First | O(m+n) | O(1) | Complex | No (already reversed!) |

**Winner**: **One-pass with carry tracking** — optimal and simple!

### Why One Pass Works

```
Since digits already in reverse order:
  Can process left to right
  Naturally handles carry propagation
  No need to reverse or go back
  
Example: 342 + 465

Normal: 3->4->2 needs to start from 2 ❌
Reversed: 2->4->3 starts from 2 naturally ✓

One pass is sufficient!
```

### Why Not Convert to Integer

```
Naive approach:
  1. Convert l1 to integer: 321
  2. Convert l2 to integer: 654
  3. Add: 321 + 654 = 975
  4. Convert back to list: [5,7,9]

Problems:
  - Lists can be up to 100 digits long
  - Integer overflow! (Java int max = ~10 digits)
  - Even long overflow (max = ~19 digits)
  - Need BigInteger (inefficient) ❌
  
Direct addition avoids overflow! ✓
```

### Why Dummy Head Simplifies

```
Without dummy head:
  ListNode head = null;
  ListNode curr = null;
  
  if (head == null) {
      head = new ListNode(digit);
      curr = head;
  } else {
      curr.next = new ListNode(digit);
      curr = curr.next;
  }
  
  Special case for first node! ❌

With dummy head:
  ListNode dummy = new ListNode(0);
  ListNode curr = dummy;
  
  curr.next = new ListNode(digit);
  curr = curr.next;
  
  // Always same code!
  return dummy.next;
  
  No special cases! ✓
```

### Why Check Carry After Lists End

```
Example: 9 + 9 = 18

After processing both 9s:
  l1 = null, l2 = null
  BUT carry = 1!
  
Without carry check:
  Would return [8] ❌ (wrong!)
  
With carry check:
  Continue loop because carry != 0
  Add node(1)
  Return [8, 1] ✓
  
Must handle final carry!
```

### Why This is Optimal

```
Time complexity:
  Must visit all digits once
  O(max(m, n)) is optimal
  Cannot do better! ✓

Space complexity:
  Only store carry and pointers
  O(1) auxiliary space
  Optimal! ✓
  
Result list O(max(m,n)) doesn't count as auxiliary
```

---

## Critical Edge Cases & Gotchas

### 1. **Both Single Digit with Carry**
```java
Input: l1 = [9], l2 = [9]
Output: [8,1]

9 + 9 = 18
Must create two nodes
Don't forget final carry!
```

### 2. **Different Lengths**
```java
Input: l1 = [9,9,9], l2 = [1]
Output: [0,0,0,1]

Treat missing digits as 0
Carry propagates through all digits
```

### 3. **One List Much Longer**
```java
Input: l1 = [1,0,0,0,0], l2 = [5]
Output: [6,0,0,0,0]

Only first digit affected
Rest copied unchanged
```

### 4. **All Nines**
```java
Input: l1 = [9,9,9,9], l2 = [9,9,9,9]
Output: [8,9,9,9,1]

Maximum carry propagation
Result has one extra digit
```

### 5. **Adding Zero**
```java
Input: l1 = [0], l2 = [0]
Output: [0]

0 + 0 = 0
No carry, single node
```

### 6. **One List is Zero**
```java
Input: l1 = [1,2,3], l2 = [0]
Output: [1,2,3]

Adding zero doesn't change result
Like copying l1
```

### 7. **Carry Propagates to New Digit**
```java
Input: l1 = [5,5], l2 = [5,5]
Output: [0,1,1]

55 + 55 = 110
Carry creates new digit
Result length = max(m,n) + 1
```

### 8. **No Carry Throughout**
```java
Input: l1 = [1,2,3], l2 = [4,5,6]
Output: [5,7,9]

All sums < 10
No carry ever generated
Simple case
```

### 9. **Maximum Length Lists**
```java
Input: Both lists have 100 nodes
Output: Result may have 101 nodes

Must handle maximum constraint
Efficient O(n) solution required
```

### 10. **Single Node Each, No Carry**
```java
Input: l1 = [2], l2 = [3]
Output: [5]

2 + 3 = 5
Simplest case
Single digit result
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Forgetting to Handle Final Carry**
```java
// WRONG - stops when both lists end
while (l1 != null || l2 != null) {  // Missing carry check! ❌
    int val1 = (l1 != null) ? l1.val : 0;
    int val2 = (l2 != null) ? l2.val : 0;
    int sum = val1 + val2 + carry;
    // ... create node
}
return dummy.next;  // Missing final carry!
```

**Why wrong**: Final carry not added!

**Dry run failure:**
```
Input: l1 = [9], l2 = [9]

Iteration 1:
  sum = 9 + 9 + 0 = 18
  digit = 8, carry = 1
  Result: [8]

Loop check:
  l1 = null, l2 = null
  Exit (but carry = 1!) ❌

Return [8] — WRONG! Should be [8,1]
```

**Fix**: Include carry in loop condition
```java
while (l1 != null || l2 != null || carry != 0) {  ✓
```

### ❌ **MISTAKE 2: Not Using Dummy Head**
```java
// WRONG - complex logic for first node
ListNode head = null;
ListNode curr = null;
int carry = 0;

while (l1 != null || l2 != null || carry != 0) {
    int val1 = (l1 != null) ? l1.val : 0;
    int val2 = (l2 != null) ? l2.val : 0;
    int sum = val1 + val2 + carry;
    
    if (head == null) {  // Special case! ❌
        head = new ListNode(sum % 10);
        curr = head;
    } else {
        curr.next = new ListNode(sum % 10);
        curr = curr.next;
    }
    // ...
}
```

**Why wrong**: Unnecessary complexity!

**Fix**: Use dummy head
```java
ListNode dummy = new ListNode(0);
ListNode curr = dummy;
// ... always same: curr.next = new ListNode(digit)
return dummy.next;  ✓
```

### ❌ **MISTAKE 3: Wrong Carry Calculation**
```java
// WRONG - incorrect carry logic
int sum = val1 + val2 + carry;
int digit = sum - 10;  // WRONG! ❌
carry = (sum >= 10) ? 1 : 0;  // Works but verbose
```

**Why wrong**: digit calculation is wrong!

**Dry run failure:**
```
sum = 15
digit = 15 - 10 = 5 ✓ (happens to work)

sum = 7
digit = 7 - 10 = -3 ❌ (wrong!)
```

**Fix**: Use modulo and division
```java
int digit = sum % 10;  ✓ (always correct)
carry = sum / 10;      ✓ (works for any sum)
```

### ❌ **MISTAKE 4: Not Moving Pointers**
```java
// WRONG - infinite loop!
while (l1 != null || l2 != null || carry != 0) {
    int val1 = (l1 != null) ? l1.val : 0;
    int val2 = (l2 != null) ? l2.val : 0;
    int sum = val1 + val2 + carry;
    // ... create node
    
    // MISSING: l1 = l1.next and l2 = l2.next ❌
}
```

**Why wrong**: Infinite loop!

**Dry run failure:**
```
l1 = [1,2,3]
l2 = [4,5,6]

Iteration 1:
  Process l1.val = 1, l2.val = 4
  But l1 and l2 not moved!
  
Iteration 2:
  Still l1.val = 1, l2.val = 4
  Infinite loop! ❌
```

**Fix**: Move pointers
```java
if (l1 != null) l1 = l1.next;
if (l2 != null) l2 = l2.next;
```

### ❌ **MISTAKE 5: Moving Null Pointers**
```java
// WRONG - NullPointerException
while (l1 != null || l2 != null || carry != 0) {
    int val1 = (l1 != null) ? l1.val : 0;
    int val2 = (l2 != null) ? l2.val : 0;
    int sum = val1 + val2 + carry;
    // ... create node
    
    l1 = l1.next;  // What if l1 is null? ❌
    l2 = l2.next;  // What if l2 is null? ❌
}
```

**Why wrong**: Can't call .next on null!

**Fix**: Check before moving
```java
if (l1 != null) l1 = l1.next;
if (l2 != null) l2 = l2.next;
```

### ❌ **MISTAKE 6: Trying to Convert to Integer**
```java
// WRONG - overflow for large numbers
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    int num1 = 0, multiplier = 1;
    while (l1 != null) {
        num1 += l1.val * multiplier;  // Overflow for 100 digits! ❌
        multiplier *= 10;
        l1 = l1.next;
    }
    // ... similar for l2
    int sum = num1 + num2;  // Overflow! ❌
    // ... convert back
}
```

**Why wrong**: Integer overflow!

**Issue:**
```
Lists can be up to 100 digits
int max = 2,147,483,647 (~10 digits)
long max = 9,223,372,036,854,775,807 (~19 digits)

100-digit number doesn't fit! ❌
```

**Fix**: Add digit by digit (original approach)

### ❌ **MISTAKE 7: Not Handling Null Initially**
```java
// WRONG - NullPointerException
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    if (l1 == null && l2 == null) {  // Problem says non-empty!
        return null;
    }
    
    int val1 = l1.val;  // But could be null elsewhere! ❌
    // ...
}
```

**Why wrong**: Problem states lists are non-empty!

**Fix**: No need to check initially
```java
// Problem guarantees: 1 <= length
// Don't waste time checking null initially
```

### ❌ **MISTAKE 8: Creating Node Before Calculation**
```java
// WRONG - creates node too early
while (l1 != null || l2 != null || carry != 0) {
    ListNode newNode = new ListNode(0);  // Created too early! ❌
    curr.next = newNode;
    curr = newNode;
    
    int val1 = (l1 != null) ? l1.val : 0;
    int val2 = (l2 != null) ? l2.val : 0;
    int sum = val1 + val2 + carry;
    curr.val = sum % 10;  // Setting value later
    carry = sum / 10;
    // ...
}
```

**Why wrong**: Less clear, error-prone!

**Fix**: Create node with correct value immediately
```java
int sum = val1 + val2 + carry;
int digit = sum % 10;
curr.next = new ListNode(digit);  ✓
```

### ❌ **MISTAKE 9: Wrong Loop Condition (AND instead of OR)**
```java
// WRONG - stops too early
while (l1 != null && l2 != null) {  // AND! ❌
    // ...
}
```

**Why wrong**: Stops when either list ends!

**Dry run failure:**
```
l1 = [9,9,9], l2 = [1]

Iteration 1:
  l1 = 9, l2 = 1
  Process normally

Check:
  l1 = [9,9], l2 = null
  l1 != null: true
  l2 != null: false
  l1 != null AND l2 != null: false
  Exit! ❌

Missing [9,9] from l1!
```

**Fix**: Use OR
```java
while (l1 != null || l2 != null || carry != 0) {  ✓
```

---

## Complexity Analysis

### Time Complexity: **O(max(m, n))**

Where m = length of l1, n = length of l2

```
Must visit all digits from both lists:
  If m > n: process all m digits
  If n > m: process all n digits
  If m = n: process all m (or n) digits
  
Time = O(max(m, n)) ✓

Single pass through lists
Each operation O(1):
  - Get values: O(1)
  - Addition: O(1)
  - Create node: O(1)
  - Move pointers: O(1)

Total: O(max(m, n))
```

**Example Analysis:**
```
l1 = [2,4,3] (m = 3)
l2 = [5,6,4] (n = 3)

Iterations: max(3, 3) = 3
Time: O(3) = O(n) ✓

l1 = [9,9,9,9,9] (m = 5)
l2 = [1] (n = 1)

Iterations: max(5, 1) = 5
Time: O(5) = O(m) ✓

Plus one extra iteration for final carry
Total: O(max(m, n)) ✓
```

### Space Complexity: **O(1)** auxiliary space

```
Variables used:
  - dummy: O(1)
  - curr: O(1)
  - carry: O(1)
  - val1, val2, sum, digit: O(1)

Total auxiliary: O(1) ✓

Result list: O(max(m, n))
  But this is output, not counted as auxiliary space!
  
Space complexity: O(1) ✓
```

**Note on Result Space:**
```
Result length:
  Usually: max(m, n)
  With final carry: max(m, n) + 1
  
Example: 99 + 1 = 100
  l1: [9,9] (length 2)
  l2: [1] (length 1)
  Result: [0,0,1] (length 3)
  
  length = max(2,1) + 1 = 3 ✓

But result doesn't count as auxiliary space!
```

### Optimal Complexity

```
Time: O(max(m, n))
  Cannot do better — must visit all digits
  Optimal! ✓

Space: O(1) auxiliary
  Only need constant extra variables
  Optimal! ✓

This solution is optimal in both time and space!
```

---

## Visualization

### Complete Example Walkthrough

**Input:** `l1 = [2,4,3]`, `l2 = [5,6,4]`
**Represents:** 342 + 465
**Expected:** [7,0,8] (represents 807)

---

**Initial State:**
```
l1: 2 -> 4 -> 3 -> null
l2: 5 -> 6 -> 4 -> null

dummy: 0 -> null
curr: dummy (pointing to dummy)
carry: 0
```

---

**Iteration 1: Process First Digits (Ones Place)**
```
l1 points to: 2
l2 points to: 5
carry: 0

Step 1: Get values
  val1 = 2
  val2 = 5

Step 2: Calculate sum
  sum = 2 + 5 + 0 = 7
  digit = 7 % 10 = 7
  carry = 7 / 10 = 0

Step 3: Create node
  curr.next = new ListNode(7)
  dummy: 0 -> 7 -> null
  
Step 4: Move pointers
  curr = curr.next (now points to 7)
  l1 = l1.next (now points to 4)
  l2 = l2.next (now points to 6)

State after iteration 1:
  Result: [7]
  carry: 0
  l1 at: 4
  l2 at: 6
```

---

**Iteration 2: Process Second Digits (Tens Place)**
```
l1 points to: 4
l2 points to: 6
carry: 0

Step 1: Get values
  val1 = 4
  val2 = 6

Step 2: Calculate sum
  sum = 4 + 6 + 0 = 10
  digit = 10 % 10 = 0
  carry = 10 / 10 = 1  ← Carry generated!

Step 3: Create node
  curr.next = new ListNode(0)
  dummy: 0 -> 7 -> 0 -> null
  
Step 4: Move pointers
  curr = curr.next (now points to 0)
  l1 = l1.next (now points to 3)
  l2 = l2.next (now points to 4)

State after iteration 2:
  Result: [7, 0]
  carry: 1
  l1 at: 3
  l2 at: 4
```

---

**Iteration 3: Process Third Digits (Hundreds Place)**
```
l1 points to: 3
l2 points to: 4
carry: 1  ← Using carry from previous

Step 1: Get values
  val1 = 3
  val2 = 4

Step 2: Calculate sum
  sum = 3 + 4 + 1 = 8
  digit = 8 % 10 = 8
  carry = 8 / 10 = 0

Step 3: Create node
  curr.next = new ListNode(8)
  dummy: 0 -> 7 -> 0 -> 8 -> null
  
Step 4: Move pointers
  curr = curr.next (now points to 8)
  l1 = l1.next (now null)
  l2 = l2.next (now null)

State after iteration 3:
  Result: [7, 0, 8]
  carry: 0
  l1: null
  l2: null
```

---

**Loop Check:**
```
Condition: l1 != null || l2 != null || carry != 0
  l1 = null: false
  l2 = null: false
  carry = 0: false
  
Overall: false || false || false = false
Exit loop!
```

---

**Return:**
```
return dummy.next

dummy: 0 -> 7 -> 0 -> 8 -> null
dummy.next: 7 -> 0 -> 8 -> null

Result: [7, 0, 8] ✓
```

---

### Example with Carry to New Digit

**Input:** `l1 = [9,9]`, `l2 = [1]`
**Represents:** 99 + 1 = 100
**Expected:** [0,0,1]

---

**Iteration 1:**
```
l1 = 9, l2 = 1, carry = 0
sum = 9 + 1 + 0 = 10
digit = 0, carry = 1
Result: [0]

l1 moves to 9
l2 moves to null
```

---

**Iteration 2:**
```
l1 = 9, l2 = null, carry = 1
val1 = 9, val2 = 0 (l2 is null)
sum = 9 + 0 + 1 = 10
digit = 0, carry = 1
Result: [0, 0]

l1 moves to null
l2 still null
```

---

**Iteration 3:**
```
l1 = null, l2 = null, carry = 1

Loop continues because carry != 0! ✓

val1 = 0, val2 = 0
sum = 0 + 0 + 1 = 1
digit = 1, carry = 0
Result: [0, 0, 1]

l1 = null, l2 = null
```

---

**Loop Check:**
```
l1 = null, l2 = null, carry = 0
All false → exit
Return [0, 0, 1] ✓
```

---

## Comparison of Approaches

| Approach | Time | Space (Aux) | Handles Overflow | Complexity | Recommended |
|----------|------|-------------|------------------|------------|-------------|
| **One-Pass Carry** | **O(max(m,n))** | **O(1)** | **Yes ✅** | **Simple ✅** | **Yes ✅** |
| Recursion | O(max(m,n)) | O(max(m,n)) | Yes | Medium | No (stack space) |
| Convert to Integer | O(m+n) | O(1) | No ❌ | Simple | No (overflow) |
| BigInteger | O(m+n) | O(m+n) | Yes | Simple | No (inefficient) |

**Winner**: **One-pass with carry** — optimal, simple, handles all cases!

---

## Key Takeaways

1. **Reverse order is a gift** — allows left-to-right processing
2. **Track carry** — sum / 10 for next position
3. **Handle different lengths** — treat missing as 0
4. **Continue while carry exists** — don't forget final carry
5. **Dummy head simplifies** — no special case for first node
6. **One pass is enough** — O(max(m,n)) optimal
7. **Don't convert to integer** — overflow for large numbers
8. **Space O(1)** — only carry and pointers needed
9. **Check carry != 0** — crucial for loop condition
10. **Move pointers safely** — check null before .next

---

## Interview Tips

**What to say in an interview:**

> "To add two numbers represented as linked lists in reverse order, I'll use a one-pass approach with carry tracking. Since the digits are already in reverse order (least significant digit first), I can process them left to right, just like elementary school addition. I'll use a dummy head to simplify building the result list. At each position, I'll add the corresponding digits from both lists plus any carry from the previous position. If a digit is missing (one list is shorter), I'll treat it as 0. I'll continue until both lists are exhausted and there's no carry left. It's important to check for a final carry — for example, 9 + 9 = 18 requires adding an extra node for the carry. This solution runs in O(max(m,n)) time with a single pass through both lists, and uses O(1) auxiliary space."

**Key points to mention:**
1. **Reverse order helps** — can process left to right
2. **Track carry** — sum / 10, propagates to next
3. **Dummy head** — simplifies list building
4. **Handle different lengths** — treat missing as 0
5. **Check carry != 0** — don't miss final carry
6. **One pass** — O(max(m,n)) time
7. **O(1) space** — only carry and pointers
8. **Modulo for digit** — sum % 10
9. **Division for carry** — sum / 10

**Common Follow-ups:**
- "What if lists are in normal order (most significant first)?" → Reverse both lists first, or use stack
- "Can you do it recursively?" → Yes, but uses O(n) stack space
- "What about very large numbers?" → This approach handles any length (no overflow)
- "How to handle negative numbers?" → Need sign tracking (not in this problem)
- "What's the space complexity?" → O(1) auxiliary (result doesn't count)

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Add Two Numbers** | Medium | **Digit-by-Digit + Carry** | **This problem** |
| Add Two Numbers II | Medium | Digit-by-Digit | Stored in normal order (MSB first) |
| Multiply Strings | Medium | Digit-by-Digit | Multiplication instead of addition |
| Plus One | Easy | Digit-by-Digit | Add 1 to array representation |
| Add Binary | Easy | Bit-by-Bit | Binary instead of decimal |
| Add Strings | Easy | Digit-by-Digit | String representation |
| Add to Array-Form of Integer | Easy | Digit-by-Digit | Array + integer |

**Pattern Progression**:
1. **Add Two Numbers** (this) — Linked lists, reverse order
2. **Add Two Numbers II** — Linked lists, normal order (need stack/reverse)
3. **Plus One** — Array form, simpler (only +1)
4. **Multiply Strings** — More complex carry logic

---

## Final Pattern Label

✅ **Elementary Addition with Carry Tracking (One-Pass)**

**Remember:** This is a **digit-by-digit addition problem** with reverse order. Use **one-pass approach** with **carry tracking**. Process both lists simultaneously from left to right (already in reverse = ones digit first). At each step: get values (treat null as 0), calculate sum + carry, create node with `sum % 10`, update carry = `sum / 10`. Use **dummy head** to simplify list building. Critical: **continue while carry != 0** to handle final carry (e.g., 9+9=18 needs extra node). Achieves **O(max(m,n)) time** (single pass) and **O(1) auxiliary space** (only carry and pointers). Don't convert to integer (overflow for 100 digits). Move pointers safely (`if (l1 != null) l1 = l1.next`). Return `dummy.next` to skip dummy. This is optimal for both time and space!

