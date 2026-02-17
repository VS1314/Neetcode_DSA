# Add Two Numbers

**Difficulty:** Medium  
**Pattern:** Linked List Traversal, Math (Carry Addition)  
**Recommended Time & Space Complexity:**  
- Time: O(m + n)  
- Space: O(1)  

---

## 1. Problem Understanding

You are given two non-empty linked lists representing two non-negative integers. The digits are stored in **reverse order**, and each node contains a single digit. Add the two numbers and return the sum as a linked list.

- **Key Point:** Digits are in reverse order (least significant digit first)
  - Number 321 is represented as: 1 → 2 → 3
  - This is convenient because we add from right to left (least significant digit first)

- **Example 1:**  
  Input: l1 = [1,2,3], l2 = [4,5,6]  
  Output: [5,7,9]  
  Explanation: 321 + 654 = 975 (represented as 5 → 7 → 9)

- **Example 2:**  
  Input: l1 = [9], l2 = [9]  
  Output: [8,1]  
  Explanation: 9 + 9 = 18 (represented as 8 → 1)

- **Key Constraint:** No leading zeros except the number 0 itself

---

## 2. Pattern to Use

This is a **Digit-by-Digit Addition with Carry** problem using **Linked List Traversal**.

Key concepts:
1. **Elementary school addition:** Add digits from right to left
2. **Carry management:** Track when sum ≥ 10
3. **Simultaneous traversal:** Process both lists in parallel
4. **Handle different lengths:** One list may be longer than the other

---

## 3. Algorithm & Approach

### How Elementary Addition Works

```
    321
  + 654
  -----
    975

Step by step:
1 + 4 = 5, carry = 0
2 + 5 = 7, carry = 0
3 + 6 = 9, carry = 0
```

### With Carry Example

```
    199
  +   1
  -----
    200

Step by step:
9 + 1 = 10 → digit = 0, carry = 1
9 + 0 + carry(1) = 10 → digit = 0, carry = 1
1 + 0 + carry(1) = 2 → digit = 2, carry = 0
```

### Optimal Approach: Single Pass with Carry

**High-Level Strategy:**
1. Use a **dummy node** to simplify result list construction
2. Traverse both lists simultaneously
3. At each position:
   - Add values from both nodes (if they exist)
   - Add the carry from previous addition
   - Calculate new digit (sum % 10) and carry (sum / 10)
   - Create new node with the digit
4. After traversal, if carry exists, add one more node

**Edge Cases to Handle:**
- Lists of different lengths
- Final carry after all digits processed
- One or both lists are null at current position

---

## 4. Why This Strategy?

### Why reverse order is convenient

**If stored in normal order:**
```
321: 3 → 2 → 1
654: 6 → 5 → 4

We'd need to:
1. Reverse both lists
2. Add digits
3. Reverse result
Time: O(3n) with extra space
```

**With reverse order (given):**
```
321: 1 → 2 → 3
654: 4 → 5 → 6

We can:
1. Add directly from head to tail
Time: O(n), simpler logic ✓
```

### Why use a dummy node?

**Without dummy node:**
```java
// Complex: Need to track head separately
ListNode head = null;
ListNode current = null;
if (head == null) {
    head = new ListNode(digit);
    current = head;
} else {
    current.next = new ListNode(digit);
    current = current.next;
}
```

**With dummy node:**
```java
// Simple: Always append to current.next
ListNode dummy = new ListNode(0);
ListNode current = dummy;
current.next = new ListNode(digit);
current = current.next;
return dummy.next;  // Skip dummy
```

**Advantages:**
- **Cleaner code:** No special handling for first node
- **Easier to return:** Just return dummy.next
- **Less error-prone:** Uniform logic for all nodes

---

## 5. Pseudocode

```pseudo
function addTwoNumbers(l1, l2):
    dummy = new ListNode(0)
    current = dummy
    carry = 0
    
    while l1 != null OR l2 != null OR carry != 0:
        // Get values (0 if node is null)
        val1 = (l1 != null) ? l1.val : 0
        val2 = (l2 != null) ? l2.val : 0
        
        // Calculate sum and carry
        sum = val1 + val2 + carry
        carry = sum / 10
        digit = sum % 10
        
        // Create new node with digit
        current.next = new ListNode(digit)
        current = current.next
        
        // Move to next nodes
        if l1 != null: l1 = l1.next
        if l2 != null: l2 = l2.next
    
    return dummy.next
```

---

## 6. Example Walkthrough

### Example: l1 = [2,4,3], l2 = [5,6,4]

**Visual Representation:**
```
l1: 2 → 4 → 3  (represents 342)
l2: 5 → 6 → 4  (represents 465)
Sum: 342 + 465 = 807
Expected: 7 → 0 → 8
```

**Step-by-Step Execution:**

**Initial State:**
```
dummy → (empty)
current = dummy
carry = 0
```

**Iteration 1: (l1=2, l2=5)**
```
val1 = 2, val2 = 5
sum = 2 + 5 + 0 = 7
carry = 7 / 10 = 0
digit = 7 % 10 = 7

Result: dummy → 7
        current moves to 7
```

**Iteration 2: (l1=4, l2=6)**
```
val1 = 4, val2 = 6
sum = 4 + 6 + 0 = 10
carry = 10 / 10 = 1
digit = 10 % 10 = 0

Result: dummy → 7 → 0
                current moves to 0
```

**Iteration 3: (l1=3, l2=4)**
```
val1 = 3, val2 = 4
sum = 3 + 4 + 1 = 8  (include carry!)
carry = 8 / 10 = 0
digit = 8 % 10 = 8

Result: dummy → 7 → 0 → 8
                     current moves to 8
```

**Loop ends:** Both lists are null, carry = 0

**Return:** dummy.next = [7,0,8] ✓

---

## 7. Code (Java)

### Solution: Single Pass with Carry

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

public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    // Dummy node to simplify result list construction
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    int carry = 0;
    
    // Continue while there are nodes to process OR carry exists
    while (l1 != null || l2 != null || carry != 0) {
        // Get values from current nodes (0 if null)
        int val1 = (l1 != null) ? l1.val : 0;
        int val2 = (l2 != null) ? l2.val : 0;
        
        // Calculate sum and new carry
        int sum = val1 + val2 + carry;
        carry = sum / 10;
        int digit = sum % 10;
        
        // Create new node with the digit
        current.next = new ListNode(digit);
        current = current.next;
        
        // Move to next nodes (if they exist)
        if (l1 != null) l1 = l1.next;
        if (l2 != null) l2 = l2.next;
    }
    
    return dummy.next;
}
```

### Alternative: More Explicit Carry Handling

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    int carry = 0;
    
    while (l1 != null || l2 != null) {
        int val1 = (l1 != null) ? l1.val : 0;
        int val2 = (l2 != null) ? l2.val : 0;
        
        int sum = val1 + val2 + carry;
        carry = sum / 10;
        
        current.next = new ListNode(sum % 10);
        current = current.next;
        
        if (l1 != null) l1 = l1.next;
        if (l2 != null) l2 = l2.next;
    }
    
    // Handle final carry
    if (carry > 0) {
        current.next = new ListNode(carry);
    }
    
    return dummy.next;
}
```

---

## 8. Key Points

### Why check `carry != 0` in the loop condition?
```java
Example: [9,9,9] + [1]
9 + 1 = 10 → digit=0, carry=1
9 + 0 + 1 = 10 → digit=0, carry=1
9 + 0 + 1 = 10 → digit=0, carry=1
Both lists end, but carry=1 remains!
Need one more node: [0,0,0,1]
```

### Understanding Carry Mathematics:
- **sum / 10** gives the carry (tens digit)
  - 7 / 10 = 0 (no carry)
  - 15 / 10 = 1 (carry 1)
- **sum % 10** gives the digit (ones digit)
  - 7 % 10 = 7
  - 15 % 10 = 5

### Edge Cases:
- **Different lengths:** [9,9] + [1] → [0,0,1]
- **Final carry:** [5] + [5] → [0,1] (not [10])
- **All zeros:** [0] + [0] → [0]
- **Large carry chain:** [9,9,9,9] + [1] → [0,0,0,0,1]

### Important Observations:
- Never need to reverse the lists (already in convenient order)
- Dummy node eliminates edge case handling
- Single pass through both lists
- Space is O(1) if we don't count the output list

---

## 9. Time & Space Complexity Analysis

### Time Complexity: O(m + n)
- m = length of l1, n = length of l2
- Process each node exactly once
- Additional carry node: O(1)
- Total: O(max(m, n))

### Space Complexity: O(1)
- Only use constant extra space (dummy, current, carry)
- Output list doesn't count toward space complexity
- No recursion or additional data structures

---

## 10. Detailed Dry Run

### Input: l1 = [9,9], l2 = [1]

**Goal:** 99 + 1 = 100 → [0,0,1]

**Initial:**
```
l1: 9 → 9 → null
l2: 1 → null
dummy → (empty)
carry = 0
```

**Iteration 1:**
```
val1 = 9, val2 = 1
sum = 9 + 1 + 0 = 10
carry = 1, digit = 0
Result: dummy → 0
l1 = 9, l2 = null
```

**Iteration 2:**
```
val1 = 9, val2 = 0 (l2 is null)
sum = 9 + 0 + 1 = 10
carry = 1, digit = 0
Result: dummy → 0 → 0
l1 = null, l2 = null
```

**Iteration 3:**
```
val1 = 0, val2 = 0 (both null)
sum = 0 + 0 + 1 = 1
carry = 0, digit = 1
Result: dummy → 0 → 0 → 1
Both null, carry = 0, loop ends
```

**Return:** [0,0,1] ✓

---

## 11. Common Mistakes to Avoid

1. **Forgetting final carry:**
   ```java
   // WRONG: Missing carry check in loop
   while (l1 != null || l2 != null)  // ❌
   
   // RIGHT: Include carry check
   while (l1 != null || l2 != null || carry != 0)  // ✓
   ```

2. **Not handling null nodes:**
   ```java
   // WRONG: NullPointerException
   int val1 = l1.val;  // ❌ if l1 is null
   
   // RIGHT: Conditional check
   int val1 = (l1 != null) ? l1.val : 0;  // ✓
   ```

3. **Wrong carry calculation:**
   ```java
   // WRONG: Only works for single digit
   carry = (sum >= 10) ? 1 : 0;  // ❌
   
   // RIGHT: Works for any sum
   carry = sum / 10;  // ✓
   ```

4. **Returning wrong node:**
   ```java
   // WRONG: Returns dummy node
   return dummy;  // ❌
   
   // RIGHT: Skip dummy
   return dummy.next;  // ✓
   ```

5. **Not moving pointers:**
   ```java
   // WRONG: Infinite loop
   if (l1 != null) {
       val1 = l1.val;
       // Forgot: l1 = l1.next;  ❌
   }
   ```

---

## 12. Visual Example: Complete Flow

### Input: [9] + [9] = 18 → [8,1]

```
Step 0 (Initial):
l1: 9 → null
l2: 9 → null
Result: dummy → (empty)
carry = 0

Step 1:
val1 = 9, val2 = 9
sum = 9 + 9 + 0 = 18
carry = 18/10 = 1
digit = 18%10 = 8
Result: dummy → 8
l1 = null, l2 = null

Step 2:
val1 = 0, val2 = 0 (both null)
sum = 0 + 0 + 1 = 1
carry = 1/10 = 0
digit = 1%10 = 1
Result: dummy → 8 → 1

Loop ends (both null, carry = 0)
Return: [8, 1] ✓
```

---

## 13. Why This Problem is Important

This problem teaches fundamental concepts:
- **Carry propagation:** Used in many math algorithms
- **Simultaneous traversal:** Common linked list pattern
- **Dummy node technique:** Essential for linked list manipulation
- **Handling different lengths:** Real-world data is often asymmetric

---

## 14. Related Problems

Similar patterns appear in:
- **Add Binary Strings** (same carry logic)
- **Multiply Strings** (extended carry logic)
- **Plus One** (simpler version)
- **Add to Array-Form of Integer**

The carry management technique is universal across addition problems!

---

This is a fundamental medium problem that combines linked list manipulation with basic arithmetic. The dummy node technique and carry handling are essential skills for many linked list problems!

