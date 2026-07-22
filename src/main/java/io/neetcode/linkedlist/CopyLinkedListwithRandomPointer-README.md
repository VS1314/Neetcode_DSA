# Copy Linked List with Random Pointer

## Problem Description

**Difficulty**: Medium

You are given the head of a linked list of length `n`. Unlike a singly linked list, each node contains an additional pointer `random`, which may point to any node in the list, or `null`.

Create a **deep copy** of the list.

The deep copy should consist of exactly `n` **new nodes**, each including:
- The original value `val` of the copied node
- A `next` pointer to the **new node** corresponding to the `next` pointer of the original node
- A `random` pointer to the **new node** corresponding to the `random` pointer of the original node

**Important:** None of the pointers in the new list should point to nodes in the original list.

Return the head of the copied linked list.

**Node Structure:**
```java
class Node {
    int val;
    Node next;
    Node random;
}
```

**Visual Example:**
```
Original List:
  Node 1 (val=7) → Node 2 (val=13) → Node 3 (val=11) → Node 4 (val=10) → null
    ↓                ↓                  ↓                  ↓
  null             Node 1            Node 4             Node 2

Deep Copy (completely separate nodes):
  Node 1' (val=7) → Node 2' (val=13) → Node 3' (val=11) → Node 4' (val=10) → null
    ↓                 ↓                   ↓                   ↓
  null              Node 1'             Node 4'             Node 2'
```

## Examples

### Example 1:
```
Input: head = [[3,null],[7,3],[4,0],[5,1]]
Output: [[3,null],[7,3],[4,0],[5,1]]

Explanation:
Representation: [val, random_index]
Node 0: val=3, random=null
Node 1: val=7, random=Node 3 (index 3)
Node 2: val=4, random=Node 0 (index 0)
Node 3: val=5, random=Node 1 (index 1)

Visual:
  3 → 7 → 4 → 5 → null
  ↓   ↓   ↓   ↓
null  5   3   7
```

### Example 2:
```
Input: head = [[1,null],[2,2],[3,2]]
Output: [[1,null],[2,2],[3,2]]

Explanation:
Node 0: val=1, random=null
Node 1: val=2, random=Node 2 (self)
Node 2: val=3, random=Node 2 (self)

Visual:
  1 → 2 → 3 → null
  ↓   ↓↺  ↓↺
null  2   3 (pointing to themselves)
```

### Example 3:
```
Input: head = null
Output: null

Empty list, return null
```

### Example 4:
```
Input: head = [[1,null]]
Output: [[1,null]]

Single node with no random pointer
```

### Example 5:
```
Input: head = [[1,1]]
Output: [[1,1]]

Single node pointing to itself
Visual:
  1 ↺ (random points to self)
```

### Example 6:
```
Input: head = [[1,null],[2,null]]
Output: [[1,null],[2,null]]

Two nodes, no random pointers
Visual:
  1 → 2 → null
  ↓   ↓
null null
```

### Example 7:
```
Input: head = [[1,1],[2,0]]
Output: [[1,1],[2,0]]

Two nodes with cross random pointers
Visual:
  1 → 2 → null
  ↓↺  ↓
  1   1 (Node 2 points to Node 1)
```

### Example 8:
```
Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]

Classic example with complex random connections
```

### Example 9:
```
Input: head = [[1,null],[2,null],[3,null],[4,null]]
Output: [[1,null],[2,null],[3,null],[4,null]]

Long chain with no random pointers
```

### Example 10:
```
Input: head = [[5,2],[10,0],[15,1]]
Output: [[5,2],[10,0],[15,1]]

Circular random references
Visual:
  5 → 10 → 15 → null
  ↓   ↓    ↓
 15   5   10 (all pointing to different nodes)
```

## Constraints
- `0 <= n <= 100`
- `-100 <= Node.val <= 100`
- Node values are **not guaranteed to be unique**
- `random` is `null` or is pointing to some node in the linked list

**Recommended Complexity**: 
- Time: O(n) where n is the length of the list
- Space: O(n) for HashMap approach, or O(1) for interleaving approach

---

## Pattern Recognition

**Primary Pattern**: **HashMap Mapping (Deep Copy with References)**

**Why This Pattern?**
- Need to create **completely new nodes** (deep copy)
- Must preserve **random pointer relationships**
- Random pointers can point to **any node** (forward or backward)
- Need to **map old nodes to new nodes**

**Key Insight**: Two-Pass with HashMap
```
Problem: Can't copy random pointers in one pass
  
  While copying: A → B → C
  When at A, create A'
  A.random might point to C
  But C' doesn't exist yet! ❌

Solution: Two passes
  Pass 1: Create all new nodes, map old → new
    A → A' (store in HashMap)
    B → B' (store in HashMap)
    C → C' (store in HashMap)
  
  Pass 2: Set next and random pointers
    A'.next = map.get(A.next) = B'
    A'.random = map.get(A.random) = C'
    
  Now all new nodes exist! ✓
```

**Why HashMap?**
```
Need to map original nodes to copied nodes

HashMap<Node, Node>:
  Key: Original node
  Value: Copied node

When setting random pointer:
  original.random points to some original node X
  Find X's copy: copied = map.get(X)
  Set: newNode.random = copied
  
O(1) lookup time! ✓
```

**The Two-Pass Strategy**:
```
Example: 1 → 2 → 3
         ↓   ↓   ↓
         2   3   1

Pass 1: Create nodes and store mappings
  curr = 1
  copy = new Node(1)
  map.put(1, copy)
  
  curr = 2
  copy = new Node(2)
  map.put(2, copy)
  
  curr = 3
  copy = new Node(3)
  map.put(3, copy)
  
  Map: {1→1', 2→2', 3→3'}

Pass 2: Connect pointers
  curr = 1, copy = 1'
  copy.next = map.get(1.next) = map.get(2) = 2'
  copy.random = map.get(1.random) = map.get(2) = 2'
  
  curr = 2, copy = 2'
  copy.next = map.get(2.next) = map.get(3) = 3'
  copy.random = map.get(2.random) = map.get(3) = 3'
  
  curr = 3, copy = 3'
  copy.next = map.get(3.next) = map.get(null) = null
  copy.random = map.get(3.random) = map.get(1) = 1'

Result: 1' → 2' → 3' with correct random pointers ✓
```

**Why Not One Pass?**
```
Attempt one-pass approach:

curr = 1
  Create 1'
  1'.random should be copy of 1.random
  But 1.random might not be copied yet! ❌

Example: 1 → 2 → 3
         ↓   ↓   ↓
         3   3   1

At node 1:
  1.random = 3
  But 3' doesn't exist yet!
  Can't set 1'.random ❌

Need two passes!
```

**Alternative: Interleaving Approach (O(1) Space)**:
```
Instead of HashMap, interleave new nodes with old

Step 1: Interleave
  Original: 1 → 2 → 3 → null
  After:    1 → 1' → 2 → 2' → 3 → 3' → null
  
Step 2: Set random pointers
  For each original node:
    original.next.random = original.random.next
    (new node's random = old node's random's copy)
  
Step 3: Separate lists
  Restore original list
  Extract copied list
  
Space: O(1) (no HashMap) ✓
More complex but optimal space!
```

**Example: Interleaving**
```
Original: 1 → 2 → 3
          ↓   ↓   ↓
          2   3   1

Step 1: Interleave
  1 → 1' → 2 → 2' → 3 → 3' → null

Step 2: Set random pointers
  1.random = 2
  1'.random = 1.random.next = 2.next = 2' ✓
  
  2.random = 3
  2'.random = 2.random.next = 3.next = 3' ✓
  
  3.random = 1
  3'.random = 3.random.next = 1.next = 1' ✓

Step 3: Separate
  Original: 1 → 2 → 3
  Copy:     1' → 2' → 3'
  
Both with correct random pointers! ✓
```

**Shallow vs Deep Copy**:
```
Shallow Copy:
  Copy node values
  But pointers still reference original nodes
  Modifications affect original ❌

Deep Copy:
  Create completely new nodes
  New pointers reference new nodes
  Completely independent ✓
  
This problem requires deep copy!
```

**Handling null Random Pointers**:
```
If node.random is null:
  Copy's random should also be null
  
With HashMap:
  map.get(null) = null ✓
  
With Interleaving:
  if (original.random != null):
      copy.random = original.random.next
  else:
      copy.random = null ✓
```

**Related Patterns**:
1. **HashMap Mapping** — Map old to new
2. **Two-Pass Algorithm** — Create then connect
3. **Deep Copy** — Clone with references
4. **Interleaving** — O(1) space technique

---

## Algorithm & Approach

### Core Insight

**Why Two-Pass HashMap Works:**
```
Key observations:
  1. Can't copy random pointers without all nodes existing
  2. HashMap maps original to copy in O(1)
  3. Two passes: create all, then connect all
  4. Handle null pointers naturally
```

**The Optimal Strategy**:
```
Key steps:
  1. First pass: Create all new nodes, store in HashMap
  2. Second pass: Connect next and random pointers
  3. Return copy of original head
```

### Step-by-Step Algorithm

---

#### **Approach 1: HashMap (Two-Pass) - OPTIMAL FOR CLARITY**

**Core Idea**:
- Pass 1: Create all new nodes, map old → new
- Pass 2: Connect next and random pointers using map
- O(n) time, O(n) space

**Algorithm**
```
copyRandomList(head):
    if head == null:
        return null
    
    // Pass 1: Create all nodes
    map = new HashMap<Node, Node>()
    curr = head
    
    while curr != null:
        copy = new Node(curr.val)
        map.put(curr, copy)
        curr = curr.next
    
    // Pass 2: Connect pointers
    curr = head
    
    while curr != null:
        copy = map.get(curr)
        copy.next = map.get(curr.next)
        copy.random = map.get(curr.random)
        curr = curr.next
    
    return map.get(head)
```

**Code Implementation**
```java
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        
        // HashMap to store mapping: original -> copy
        Map<Node, Node> map = new HashMap<>();
        
        // Pass 1: Create all new nodes
        Node curr = head;
        while (curr != null) {
            Node copy = new Node(curr.val);
            map.put(curr, copy);
            curr = curr.next;
        }
        
        // Pass 2: Connect next and random pointers
        curr = head;
        while (curr != null) {
            Node copy = map.get(curr);
            copy.next = map.get(curr.next);  // map.get(null) returns null
            copy.random = map.get(curr.random);
            curr = curr.next;
        }
        
        // Return copy of head
        return map.get(head);
    }
}
```

**Example Walkthrough**

Input: `head = [[7,null],[13,0],[11,4],[10,2],[1,0]]`

```
List structure (0-indexed):
  0: val=7, random=null
  1: val=13, random=0
  2: val=11, random=4
  3: val=10, random=2
  4: val=1, random=0
```

**Pass 1: Create Nodes**
```
curr = Node 0 (val=7)
  copy = new Node(7)
  map.put(Node 0, Node 0')
  
curr = Node 1 (val=13)
  copy = new Node(13)
  map.put(Node 1, Node 1')
  
curr = Node 2 (val=11)
  copy = new Node(11)
  map.put(Node 2, Node 2')
  
curr = Node 3 (val=10)
  copy = new Node(10)
  map.put(Node 3, Node 3')
  
curr = Node 4 (val=1)
  copy = new Node(1)
  map.put(Node 4, Node 4')

Map: {0→0', 1→1', 2→2', 3→3', 4→4'}
```

**Pass 2: Connect Pointers**
```
curr = Node 0
  copy = Node 0'
  copy.next = map.get(Node 1) = Node 1'
  copy.random = map.get(null) = null
  
curr = Node 1
  copy = Node 1'
  copy.next = map.get(Node 2) = Node 2'
  copy.random = map.get(Node 0) = Node 0'
  
curr = Node 2
  copy = Node 2'
  copy.next = map.get(Node 3) = Node 3'
  copy.random = map.get(Node 4) = Node 4'
  
curr = Node 3
  copy = Node 3'
  copy.next = map.get(Node 4) = Node 4'
  copy.random = map.get(Node 2) = Node 2'
  
curr = Node 4
  copy = Node 4'
  copy.next = map.get(null) = null
  copy.random = map.get(Node 0) = Node 0'

Result: Complete deep copy with all pointers correct ✓
```

**Complexity Analysis**
- **Time**: O(n) — Two passes through list
- **Space**: O(n) — HashMap stores n mappings

---

#### **Approach 2: Interleaving Nodes (O(1) Space) - OPTIMAL SPACE**

**Core Idea**:
- Interleave new nodes between original nodes
- Use position to find corresponding copies
- Separate lists at the end
- O(n) time, O(1) space

**Algorithm**
```
copyRandomList(head):
    if head == null:
        return null
    
    // Step 1: Interleave new nodes
    curr = head
    while curr != null:
        copy = new Node(curr.val)
        copy.next = curr.next
        curr.next = copy
        curr = copy.next
    
    // Step 2: Set random pointers
    curr = head
    while curr != null:
        if curr.random != null:
            curr.next.random = curr.random.next
        curr = curr.next.next
    
    // Step 3: Separate lists
    curr = head
    copyHead = head.next
    copy = copyHead
    
    while curr != null:
        curr.next = curr.next.next
        if copy.next != null:
            copy.next = copy.next.next
        curr = curr.next
        copy = copy.next
    
    return copyHead
```

**Code Implementation**
```java
class Solution {
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        
        // Step 1: Interleave copied nodes
        Node curr = head;
        while (curr != null) {
            Node copy = new Node(curr.val);
            copy.next = curr.next;
            curr.next = copy;
            curr = copy.next;
        }
        
        // Step 2: Set random pointers for copied nodes
        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }
        
        // Step 3: Separate the lists
        curr = head;
        Node copyHead = head.next;
        Node copy = copyHead;
        
        while (curr != null) {
            curr.next = curr.next.next;
            if (copy.next != null) {
                copy.next = copy.next.next;
            }
            curr = curr.next;
            copy = copy.next;
        }
        
        return copyHead;
    }
}
```

**Example Walkthrough**

Input: `1 → 2 → 3` with randoms: `1→2, 2→3, 3→1`

**Step 1: Interleave**
```
Original: 1 → 2 → 3 → null

After interleaving:
1 → 1' → 2 → 2' → 3 → 3' → null

Process:
  At node 1:
    Create 1'
    1'.next = 1.next = 2
    1.next = 1'
    
  At node 2:
    Create 2'
    2'.next = 2.next = 3
    2.next = 2'
    
  At node 3:
    Create 3'
    3'.next = 3.next = null
    3.next = 3'
```

**Step 2: Set Random Pointers**
```
At node 1:
  1.random = 2
  1'.random = 1.random.next = 2.next = 2' ✓
  
At node 2:
  2.random = 3
  2'.random = 2.random.next = 3.next = 3' ✓
  
At node 3:
  3.random = 1
  3'.random = 3.random.next = 1.next = 1' ✓
```

**Step 3: Separate Lists**
```
Restore original: 1 → 2 → 3 → null
Extract copy:     1' → 2' → 3' → null

Process:
  1.next = 1'.next = 2
  1'.next = 2'.next = 3
  
  2.next = 2'.next = 3
  2'.next = 3'.next = null
  
  3.next = 3'.next = null
```

**Complexity Analysis**
- **Time**: O(n) — Three passes
- **Space**: O(1) — No extra data structure

---

## Why This Strategy?

### Problem Requirements Analysis

| Approach | Time | Space | Complexity | Recommended |
|----------|------|-------|------------|-------------|
| **HashMap (Two-Pass)** | **O(n)** | **O(n)** | **Simple ✅** | **Yes (clearest)** |
| Interleaving (O(1) space) | O(n) | O(1) | Complex | Yes (optimal space) |
| Recursion + HashMap | O(n) | O(n) | Medium | No (stack overhead) |

**Winner**: **HashMap approach** for clarity, **Interleaving** for space optimization!

### Why Two Passes with HashMap

```
Can't do it in one pass:

Example: 1 → 2 → 3
         ↓   ↓   ↓
         3   1   2

At node 1:
  Create 1'
  1'.next = ?  (need 2', but not created yet)
  1'.random = ?  (need 3', but not created yet)
  
Can't set pointers! ❌

Two passes solve this:
  Pass 1: Create all nodes
  Pass 2: Now all nodes exist, set pointers ✓
```

### Why HashMap Works

```
Need to find copy of any original node quickly

Array approach:
  Store copies in array by index
  Need to know index of each node
  Extra pass to assign indices ❌
  
HashMap approach:
  Key = original node (object reference)
  Value = copied node
  O(1) lookup ✓
  No index tracking needed ✓
```

### Why Interleaving Works

```
Clever observation:
  If new nodes interleaved with old
  Original → Copy is always .next!
  
  1 → 1' → 2 → 2' → 3 → 3'
  
  To find copy of node X:
    copy = X.next ✓
  
  To find copy of X.random:
    copy.random = X.random.next ✓
    
No HashMap needed!
```

### Why Separate Lists at End

```
After interleaving and setting pointers:
  1 → 1' → 2 → 2' → 3 → 3'
  
Need to return independent copies:
  Original: 1 → 2 → 3
  Copy:     1' → 2' → 3'
  
Separation required:
  1. Restore original list (1 → 2 → 3)
  2. Extract copy list (1' → 2' → 3')
  3. Return copy head (1')
```

### Why This is Optimal

```
HashMap approach:
  Time: O(n) - two passes, optimal
  Space: O(n) - store all mappings
  Clarity: High ✓
  
Interleaving approach:
  Time: O(n) - three passes, still optimal
  Space: O(1) - no extra structure ✓
  Clarity: Medium
  
Both are optimal for their criteria!
Choose based on priority: clarity vs space.
```

---

## Critical Edge Cases & Gotchas

### 1. **Empty List**
```java
Input: head = null
Output: null

No nodes to copy
Return null immediately
```

### 2. **Single Node, No Random**
```java
Input: head = [[1,null]]
Output: [[1,null]]

Single node with random = null
Simple case
```

### 3. **Single Node, Self-Pointing**
```java
Input: head = [[1,0]]
Output: [[1,0]]

Node's random points to itself
Copy should also point to itself (copy)
```

### 4. **All Randoms Null**
```java
Input: head = [[1,null],[2,null],[3,null]]
Output: [[1,null],[2,null],[3,null]]

No random connections
Like copying regular linked list
```

### 5. **All Randoms Point to Same Node**
```java
Input: head = [[1,0],[2,0],[3,0]]
Output: [[1,0],[2,0],[3,0]]

All random pointers point to first node
All copies should point to first copy
```

### 6. **Circular Random References**
```java
Input: head = [[1,1],[2,0],[3,2]]
Output: [[1,1],[2,0],[3,2]]

Node 0 → Node 1 → Node 0 (circular)
Must preserve in copy
```

### 7. **Random Points Forward**
```java
Input: head = [[1,1],[2,2]]
Output: [[1,1],[2,2]]

Random pointers point forward
Common case
```

### 8. **Random Points Backward**
```java
Input: head = [[1,null],[2,0]]
Output: [[1,null],[2,0]]

Random pointers point backward
HashMap handles naturally
```

### 9. **Duplicate Values**
```java
Input: head = [[1,1],[1,0],[1,1]]
Output: [[1,1],[1,0],[1,1]]

Multiple nodes with same value
Must copy all, preserve connections
```

### 10. **Maximum Length**
```java
Input: head = list of 100 nodes
Output: Copy of 100 nodes

Handles maximum constraint
Still O(n) time
```

---

## Major Areas Where We Might Go Wrong

### ❌ **MISTAKE 1: Shallow Copy Instead of Deep Copy**
```java
// WRONG - shallow copy
public Node copyRandomList(Node head) {
    if (head == null) return null;
    
    Node newHead = head;  // Just copying reference! ❌
    return newHead;
}
```

**Why wrong**: Not creating new nodes!

**Dry run failure:**
```
Original: 1 → 2 → 3

"Copy": newHead = head
  newHead points to same node 1
  Not a deep copy! ❌
  
Modifications to "copy" affect original!
```

**Fix**: Create new nodes
```java
Node copy = new Node(curr.val);
```

### ❌ **MISTAKE 2: Forgetting to Handle null Random Pointers**
```java
// WRONG - doesn't check for null
while (curr != null) {
    Node copy = map.get(curr);
    copy.next = map.get(curr.next);
    copy.random = curr.random.next;  // What if curr.random is null? ❌
    curr = curr.next;
}
```

**Why wrong**: NullPointerException!

**Fix**: Check for null
```java
copy.random = map.get(curr.random);  // HashMap handles null
// Or explicitly:
copy.random = (curr.random != null) ? map.get(curr.random) : null;
```

### ❌ **MISTAKE 3: Not Separating Lists in Interleaving Approach**
```java
// WRONG - returning interleaved list
// After interleaving and setting pointers
return head.next;  // Returns interleaved structure ❌
```

**Why wrong**: Lists still connected!

**Dry run failure:**
```
After steps 1 & 2:
  1 → 1' → 2 → 2' → 3 → 3'
  
Return head.next = 1'
  
But 1'.next = 2 (original!) ❌
  
Need to separate first!
```

**Fix**: Separate before returning
```java
// Step 3: Separate lists
curr = head;
Node copyHead = head.next;
Node copy = copyHead;

while (curr != null) {
    curr.next = curr.next.next;
    if (copy.next != null) {
        copy.next = copy.next.next;
    }
    curr = curr.next;
    copy = copy.next;
}

return copyHead;  ✓
```

### ❌ **MISTAKE 4: Wrong Interleaving Logic**
```java
// WRONG - incorrect interleaving
Node curr = head;
while (curr != null) {
    Node copy = new Node(curr.val);
    copy.next = curr;  // WRONG! Should be curr.next
    curr.next = copy;
    curr = copy.next;  // WRONG! Will be original node
}
```

**Why wrong**: Creates wrong structure!

**Dry run failure:**
```
Original: 1 → 2 → 3

Step at node 1:
  Create 1'
  1'.next = 1 ❌ (should be 2)
  1.next = 1'
  
Creates: 1 → 1' → 1 (cycle!) ❌
```

**Fix**: Correct order
```java
Node copy = new Node(curr.val);
copy.next = curr.next;  // Point to next original
curr.next = copy;  // Insert copy after current
curr = copy.next;  // Move to next original
```

### ❌ **MISTAKE 5: Forgetting Pass 1 in HashMap Approach**
```java
// WRONG - trying to do everything in one pass
Map<Node, Node> map = new HashMap<>();
Node curr = head;

while (curr != null) {
    Node copy = new Node(curr.val);
    map.put(curr, copy);
    
    // Trying to set pointers immediately
    copy.next = map.get(curr.next);  // Might not exist yet! ❌
    copy.random = map.get(curr.random);  // Might not exist yet! ❌
    
    curr = curr.next;
}
```

**Why wrong**: Next/random nodes might not be created!

**Fix**: Two separate passes
```java
// Pass 1: Create all nodes
while (curr != null) {
    map.put(curr, new Node(curr.val));
    curr = curr.next;
}

// Pass 2: Connect pointers
curr = head;
while (curr != null) {
    Node copy = map.get(curr);
    copy.next = map.get(curr.next);
    copy.random = map.get(curr.random);
    curr = curr.next;
}
```

### ❌ **MISTAKE 6: Not Restoring Original List in Interleaving**
```java
// WRONG - modifying original list permanently
// After step 3:
return copyHead;  // But original list is still modified! ❌
```

**Why wrong**: Side effect on input!

**Issue:**
```
Original list structure changed:
  Before: 1 → 2 → 3
  After:  1 → 2 → 3 (if separated correctly)
  
Must restore original structure!
```

**Fix**: Properly separate in step 3
```java
while (curr != null) {
    curr.next = curr.next.next;  // Restore original
    if (copy.next != null) {
        copy.next = copy.next.next;  // Build copy
    }
    curr = curr.next;
    copy = copy.next;
}
```

### ❌ **MISTAKE 7: Wrong Random Pointer Logic in Interleaving**
```java
// WRONG - incorrect random pointer assignment
while (curr != null) {
    if (curr.random != null) {
        curr.next.random = curr.random;  // WRONG! Points to original ❌
    }
    curr = curr.next.next;
}
```

**Why wrong**: Copy's random points to original node!

**Dry run failure:**
```
Original: 1.random = 2
  
Setting: 1'.random = 1.random = 2 ❌
  
Should be: 1'.random = 2' (the copy)
```

**Fix**: Use .next to get copy
```java
curr.next.random = curr.random.next;  // Get copy of random
```

### ❌ **MISTAKE 8: Using Node Values as HashMap Keys**
```java
// WRONG - using values instead of node references
Map<Integer, Node> map = new HashMap<>();  // WRONG!

while (curr != null) {
    map.put(curr.val, new Node(curr.val));  // What if duplicate values? ❌
    curr = curr.next;
}
```

**Why wrong**: Values might not be unique!

**Dry run failure:**
```
List: 1 → 1 → 2 (two nodes with value 1)

map.put(1, copy1)
map.put(1, copy2)  // Overwrites copy1! ❌

Can't distinguish between original nodes with same value!
```

**Fix**: Use node references as keys
```java
Map<Node, Node> map = new HashMap<>();  // Node objects as keys
map.put(curr, new Node(curr.val));  ✓
```

---

## Complexity Analysis

### Time Complexity: **O(n)**

| Approach | Pass 1 | Pass 2 | Pass 3 | Total |
|----------|--------|--------|--------|-------|
| **HashMap** | O(n) create | O(n) connect | — | **O(n)** |
| **Interleaving** | O(n) interleave | O(n) randoms | O(n) separate | **O(n)** |

**Time analysis**:
```
HashMap approach:
  Pass 1: Visit each node, create copy: O(n)
  Pass 2: Visit each node, set pointers: O(n)
  Total: 2n = O(n) ✓

Interleaving approach:
  Step 1: Visit each node, interleave: O(n)
  Step 2: Visit each node, set randoms: O(n)
  Step 3: Visit each node, separate: O(n)
  Total: 3n = O(n) ✓

Both are O(n), optimal!
Cannot do better (must visit all nodes)
```

### Space Complexity

| Approach | HashMap | Recursion Stack | New Nodes | Total Space |
|----------|---------|----------------|-----------|-------------|
| **HashMap** | **O(n)** | — | O(n) output | **O(n)** |
| **Interleaving** | — | — | O(n) output | **O(1) auxiliary** |
| **Recursive** | O(n) | O(n) | O(n) output | **O(n)** |

**Space analysis**:
```
HashMap approach:
  HashMap: n entries = O(n)
  Output: n new nodes (not counted)
  Space: O(n) ✓

Interleaving approach:
  No extra data structure
  Temporary interleaving (restored)
  Space: O(1) ✓ (auxiliary space)
  
Interleaving is optimal for space!
```

---

## Visualization

### Complete Example Walkthrough (HashMap)

**Input:** `head = [[7,null],[13,0],[11,4],[10,2],[1,0]]`

**Expected Output:** Deep copy with same structure

---

**Original List:**
```
Index: 0    1     2     3     4
Val:   7 → 13 → 11 → 10 → 1 → null
       ↓    ↓     ↓     ↓    ↓
      null  0     4     2    0

Visual:
  7 → 13 → 11 → 10 → 1
  ↓    ↓     ↓     ↓   ↓
null   7    1    11   7
```

---

**Pass 1: Create All Nodes**

```
curr = Node 0 (7)
  copy = new Node(7)
  map: {0 → 0'}

curr = Node 1 (13)
  copy = new Node(13)
  map: {0 → 0', 1 → 1'}

curr = Node 2 (11)
  copy = new Node(11)
  map: {0 → 0', 1 → 1', 2 → 2'}

curr = Node 3 (10)
  copy = new Node(10)
  map: {0 → 0', 1 → 1', 2 → 2', 3 → 3'}

curr = Node 4 (1)
  copy = new Node(1)
  map: {0 → 0', 1 → 1', 2 → 2', 3 → 3', 4 → 4'}

All nodes created! ✓
```

---

**Pass 2: Connect Pointers**

```
curr = Node 0 (7)
  copy = map.get(Node 0) = Node 0'
  copy.next = map.get(Node 1) = Node 1'
  copy.random = map.get(null) = null

curr = Node 1 (13)
  copy = map.get(Node 1) = Node 1'
  copy.next = map.get(Node 2) = Node 2'
  copy.random = map.get(Node 0) = Node 0'

curr = Node 2 (11)
  copy = map.get(Node 2) = Node 2'
  copy.next = map.get(Node 3) = Node 3'
  copy.random = map.get(Node 4) = Node 4'

curr = Node 3 (10)
  copy = map.get(Node 3) = Node 3'
  copy.next = map.get(Node 4) = Node 4'
  copy.random = map.get(Node 2) = Node 2'

curr = Node 4 (1)
  copy = map.get(Node 4) = Node 4'
  copy.next = map.get(null) = null
  copy.random = map.get(Node 0) = Node 0'

All pointers connected! ✓
```

---

**Result:**
```
New List:
  7' → 13' → 11' → 10' → 1' → null
  ↓     ↓      ↓      ↓     ↓
null   7'    1'    11'   7'

Completely independent deep copy! ✓
```

---

### Interleaving Approach Visualization

**Input:** `1 → 2 → 3` with randoms: `1→2, 2→3, 3→1`

---

**Step 1: Interleave**
```
Original:
  1 → 2 → 3 → null

After:
  1 → 1' → 2 → 2' → 3 → 3' → null

Process:
  Insert 1' after 1
  Insert 2' after 2
  Insert 3' after 3
```

---

**Step 2: Set Random Pointers**
```
1.random = 2
  1'.random = 1.random.next = 2.next = 2' ✓

2.random = 3
  2'.random = 2.random.next = 3.next = 3' ✓

3.random = 1
  3'.random = 3.random.next = 1.next = 1' ✓

All randoms set correctly!
```

---

**Step 3: Separate**
```
Restore original:
  1.next = 1'.next = 2
  2.next = 2'.next = 3
  3.next = 3'.next = null
  
  Result: 1 → 2 → 3 → null

Extract copy:
  1'.next = 2'.next = 3
  2'.next = 3'.next = null
  
  Result: 1' → 2' → 3' → null

Both lists independent! ✓
```

---

## Comparison of Approaches

| Approach | Time | Space (Auxiliary) | Complexity | Recommended |
|----------|------|-------------------|------------|-------------|
| **HashMap (Two-Pass)** | **O(n)** | **O(n)** | **Simple ✅** | **Yes ✅** |
| Interleaving (Three-Step) | O(n) | O(1) | Complex | Yes (space optimal) |
| Recursion + HashMap | O(n) | O(n) + O(n) stack | Medium | No (stack overhead) |

**Winner**: **HashMap** for interviews (clarity), **Interleaving** for space optimization!

---

## Key Takeaways

1. **Deep copy** — create completely new nodes
2. **Two passes** — create all, then connect
3. **HashMap mapping** — old node → new node
4. **Handle null** — random can be null
5. **Return map.get(head)** — copy of original head
6. **Interleaving alternative** — O(1) space
7. **Three steps** — interleave, randoms, separate
8. **O(n) time** — optimal for both approaches
9. **Node references as keys** — not values (duplicates!)
10. **Verify independence** — no pointers to original

---

## Interview Tips

**What to say in an interview:**

> "To create a deep copy of a linked list with random pointers, I'll use a two-pass approach with a HashMap. The challenge is that when copying a node, its random pointer might point to a node that hasn't been created yet, so we can't set all pointers in one pass. In the first pass, I'll create all new nodes and store the mapping from original nodes to copied nodes in a HashMap. In the second pass, I'll traverse the original list again and use the HashMap to set the next and random pointers for each copied node. The HashMap allows O(1) lookup to find the copy of any original node. This solution runs in O(n) time with two passes through the list and uses O(n) space for the HashMap. There's also an O(1) space solution using interleaving, where we insert copied nodes between original nodes, but it's more complex."

**Key points to mention:**
1. **Deep copy** — completely new nodes
2. **Two passes** — can't do it in one
3. **HashMap** — maps original to copy
4. **Pass 1** — create all nodes
5. **Pass 2** — connect pointers using map
6. **Handle null** — random can be null
7. **O(n) time** — two passes
8. **O(n) space** — HashMap storage
9. **Alternative** — interleaving for O(1) space

**Common Follow-ups:**
- "Can you do it in O(1) space?" → Yes, use interleaving approach
- "Why not one pass?" → Can't set pointers to nodes not yet created
- "What if values aren't unique?" → Use node references as keys, not values
- "How to verify deep copy?" → No pointer in new list points to original list
- "Can you do it recursively?" → Yes, but adds stack space overhead

---

## Related Problems

| Problem | Difficulty | Pattern | Key Difference |
|---------|-----------|---------|----------------|
| **Copy List with Random Pointer** | Medium | **HashMap Deep Copy** | **This problem** |
| Clone Graph | Medium | HashMap DFS/BFS | Graph instead of list |
| Clone Binary Tree with Random Pointer | Hard | HashMap + Tree | Tree structure |
| Serialize and Deserialize Binary Tree | Hard | Encoding/Decoding | Different problem type |
| Deep Copy Nested List | Medium | Recursion + HashMap | Nested structures |

**Pattern Progression**:
1. **Clone List** (this) — Linear structure with extra pointer
2. **Clone Graph** — Arbitrary connections
3. **Clone Tree with Random** — Tree + random pointers
4. **Advanced cloning** — Complex data structures

---

## Final Pattern Label

✅ **HashMap Deep Copy with Two-Pass (or Interleaving for O(1) Space)**

**Remember:** This is a **deep copy problem with random pointers**. Use **HashMap** to map original nodes to copies. **Pass 1**: Create all new nodes and store in HashMap (`map.put(original, new Node(original.val))`). **Pass 2**: Connect pointers using HashMap (`copy.next = map.get(curr.next)`, `copy.random = map.get(curr.random)`). HashMap naturally handles null (returns null). Return `map.get(head)` (copy of original head). Achieves **O(n) time** (two passes) and **O(n) space** (HashMap). **Alternative: Interleaving** for **O(1) space**: Step 1 interleave copies between originals, Step 2 set random pointers (`curr.next.random = curr.random.next`), Step 3 separate lists. Critical: **two passes needed** (can't set pointers in one pass), use **node references as keys** (not values, which may duplicate), verify **complete independence** (no pointers to original). HashMap approach is clearer for interviews!
