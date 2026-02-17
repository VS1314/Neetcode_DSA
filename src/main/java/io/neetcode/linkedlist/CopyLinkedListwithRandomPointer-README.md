# Copy Linked List with Random Pointer

**Difficulty:** Medium  
**Pattern:** Hash Map, Linked List Deep Copy  
**Recommended Time & Space Complexity:**  
- Time: O(n)  
- Space: O(n)  

---

## 1. Problem Understanding

Given a linked list where each node has:
- A `val` (integer value)
- A `next` pointer (to the next node)
- A `random` pointer (to any node in the list or null)

Create a **deep copy** of this list where:
- All nodes are newly created (different memory locations)
- The structure is identical to the original
- No pointers in the new list point to the original list

- **Example 1:**  
  Input: [[3,null],[7,3],[4,0],[5,1]]  
  Output: [[3,null],[7,3],[4,0],[5,1]]  
  (Node 0's random is null, Node 1's random points to Node 3, etc.)

- **Example 2:**  
  Input: [[1,null],[2,2],[3,2]]  
  Output: [[1,null],[2,2],[3,2]]

- **Key Challenge:** The random pointer can point to ANY node, including nodes not yet created during traversal.

---

## 2. Pattern to Use

This is a **HashMap-based Deep Copy** problem with a **Two-Pass approach**.

Key patterns:
1. **HashMap** to map original nodes to their copies
2. **Two-Pass Algorithm**: First create nodes, then assign pointers
3. **Deep Copy**: Create completely new nodes, not references

---

## 3. Algorithm & Approach

### Naive Approach (Why it fails)
- Try to copy nodes one by one during traversal
- When copying node A, try to set its random pointer
- **Problem:** The random pointer might point to a node not yet created!
- Can't assign random pointers immediately

### Optimal Approach: HashMap with Two Passes

**High-Level Strategy:**
1. **First Pass:** Create copies of all nodes, store mapping in HashMap
   - Key: Original node
   - Value: Copied node
2. **Second Pass:** Assign next and random pointers using the HashMap
   - For each original node, set copied node's pointers

**Why HashMap?**
- O(1) lookup time
- Maps original nodes to their copies
- Solves the "forward reference" problem

---

## 4. Why This Strategy?

### Why can't we do it in one pass?

**Problem Scenario:**
```
Node A → Node B → Node C
     ↓            ↑
     random points to C

When processing A:
- We create copy of A
- We try to set copy_A.random = copy_C
- But copy_C doesn't exist yet! ❌
```

**Solution with HashMap:**
```
Pass 1: Create all copies
- Original A → Copy A' (stored in map)
- Original B → Copy B' (stored in map)
- Original C → Copy C' (stored in map)

Pass 2: Assign pointers
- Copy A'.random = map[Original A.random] = map[C] = Copy C' ✓
```

**Advantages:**
- **Handles forward references:** All nodes exist before assigning pointers
- **O(1) lookup:** HashMap provides instant access to any copy
- **Clean logic:** Separates node creation from pointer assignment

---

## 5. Pseudocode

```pseudo
function copyRandomList(head):
    if head is null:
        return null
    
    // HashMap to store original -> copy mapping
    map = new HashMap()
    
    // Pass 1: Create all nodes and store in map
    current = head
    while current != null:
        copy = new Node(current.val)
        map[current] = copy
        current = current.next
    
    // Pass 2: Assign next and random pointers
    current = head
    while current != null:
        copiedNode = map[current]
        
        // Set next pointer
        if current.next != null:
            copiedNode.next = map[current.next]
        
        // Set random pointer
        if current.random != null:
            copiedNode.random = map[current.random]
        
        current = current.next
    
    return map[head]
```

---

## 6. Example Walkthrough

### Example: [[7,null],[13,0],[11,4],[10,2],[1,0]]

**Visual Representation:**
```
Original List:
7 → 13 → 11 → 10 → 1 → null
↓    ↓    ↓    ↓    ↓
null  7   1    11   7
```

**Pass 1: Create Copies**
```
HashMap after Pass 1:
Original 7  → Copy 7'
Original 13 → Copy 13'
Original 11 → Copy 11'
Original 10 → Copy 10'
Original 1  → Copy 1'
```

**Pass 2: Assign Pointers**

Processing Original 7:
```
Copy 7'.next = map[Original 13] = Copy 13'
Copy 7'.random = null (Original 7.random is null)
```

Processing Original 13:
```
Copy 13'.next = map[Original 11] = Copy 11'
Copy 13'.random = map[Original 7] = Copy 7'
```

Processing Original 11:
```
Copy 11'.next = map[Original 10] = Copy 10'
Copy 11'.random = map[Original 1] = Copy 1'
```

Processing Original 10:
```
Copy 10'.next = map[Original 1] = Copy 1'
Copy 10'.random = map[Original 11] = Copy 11'
```

Processing Original 1:
```
Copy 1'.next = null (Original 1.next is null)
Copy 1'.random = map[Original 7] = Copy 7'
```

**Result:**
```
Copied List:
7' → 13' → 11' → 10' → 1' → null
↓     ↓     ↓     ↓     ↓
null  7'    1'    11'   7'
```

---

## 7. Code (Java)

### Solution 1: HashMap with Two Passes (Recommended)

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

public Node copyRandomList(Node head) {
    if (head == null) {
        return null;
    }
    
    // HashMap to store mapping: original node -> copied node
    Map<Node, Node> map = new HashMap<>();
    
    // Pass 1: Create all nodes and store in map
    Node current = head;
    while (current != null) {
        map.put(current, new Node(current.val));
        current = current.next;
    }
    
    // Pass 2: Assign next and random pointers
    current = head;
    while (current != null) {
        Node copiedNode = map.get(current);
        
        // Set next pointer
        copiedNode.next = map.get(current.next);
        
        // Set random pointer
        copiedNode.random = map.get(current.random);
        
        current = current.next;
    }
    
    return map.get(head);
}
```

### Solution 2: Optimized O(1) Space (Advanced - Interweaving)

```java
public Node copyRandomList(Node head) {
    if (head == null) return null;
    
    // Step 1: Create copied nodes and interweave with original
    // Original: A → B → C
    // After:    A → A' → B → B' → C → C'
    Node current = head;
    while (current != null) {
        Node copy = new Node(current.val);
        copy.next = current.next;
        current.next = copy;
        current = copy.next;
    }
    
    // Step 2: Assign random pointers
    current = head;
    while (current != null) {
        if (current.random != null) {
            current.next.random = current.random.next;
        }
        current = current.next.next;
    }
    
    // Step 3: Separate the two lists
    current = head;
    Node copyHead = head.next;
    Node copyCurrent = copyHead;
    
    while (current != null) {
        current.next = current.next.next;
        if (copyCurrent.next != null) {
            copyCurrent.next = copyCurrent.next.next;
        }
        current = current.next;
        copyCurrent = copyCurrent.next;
    }
    
    return copyHead;
}
```

---

## 8. Key Points

### Why HashMap is Perfect Here:
- **O(1) lookup:** Instant access to any copied node
- **Handles any order:** Random pointers can point forward or backward
- **Clean separation:** Node creation vs pointer assignment

### Edge Cases:
- **Empty list:** head = null → return null
- **Single node:** [[1,null]] → [[1,null]]
- **Self-referencing:** [[1,0]] (random points to itself)
- **Circular random:** Node A → Node B, both random point to each other
- **All random null:** [[1,null],[2,null],[3,null]]

### Important Observations:
- HashMap automatically handles null (map.get(null) returns null)
- Both next and random are set using the same HashMap lookup
- The original list remains unchanged
- Deep copy means **no shared references** between old and new lists

---

## 9. Time & Space Complexity Analysis

### Solution 1: HashMap (Recommended)

**Time Complexity: O(n)**
- Pass 1: Create n nodes → O(n)
- Pass 2: Assign pointers for n nodes → O(n)
- HashMap operations (put/get) → O(1) each
- Total: O(n)

**Space Complexity: O(n)**
- HashMap stores n key-value pairs → O(n)
- New list with n nodes → O(n) (required for output)
- Total: O(n)

### Solution 2: Interweaving (Space Optimized)

**Time Complexity: O(n)**
- Three passes through the list → 3 * O(n) = O(n)

**Space Complexity: O(1)**
- No extra data structures (HashMap not needed)
- Only new nodes for the copy (required output)

---

## 10. Detailed Dry Run

### Input: [[1,null],[2,2],[3,2]]

**Original List Structure:**
```
Index: 0      1      2
Node:  1  →   2  →   3  → null
       ↓      ↓      ↓
Random:null   3      3
```

**Pass 1: Create Nodes**
```
Iteration 1: current = Node(1)
  Create: copy1 = Node(1)
  Map: {Node(1) → copy1}

Iteration 2: current = Node(2)
  Create: copy2 = Node(2)
  Map: {Node(1) → copy1, Node(2) → copy2}

Iteration 3: current = Node(3)
  Create: copy3 = Node(3)
  Map: {Node(1) → copy1, Node(2) → copy2, Node(3) → copy3}
```

**Pass 2: Assign Pointers**
```
Iteration 1: current = Node(1)
  copy1.next = map[Node(2)] = copy2
  copy1.random = map[null] = null

Iteration 2: current = Node(2)
  copy2.next = map[Node(3)] = copy3
  copy2.random = map[Node(3)] = copy3

Iteration 3: current = Node(3)
  copy3.next = map[null] = null
  copy3.random = map[Node(3)] = copy3 (self-reference)
```

**Result:**
```
Copied List:
copy1 → copy2 → copy3 → null
  ↓       ↓       ↓
null    copy3   copy3
```

---

## 11. Common Mistakes to Avoid

1. **Shallow copy:** Copying references instead of creating new nodes
   ```java
   // WRONG: Shallow copy
   Node copy = head;  // ❌ Same object!
   
   // RIGHT: Deep copy
   Node copy = new Node(head.val);  // ✓ New object
   ```

2. **Missing null checks:** Not handling null random pointers
   ```java
   // WRONG: NullPointerException
   copiedNode.random = map.get(current.random);  // If current.random is null
   
   // RIGHT: HashMap handles null automatically
   copiedNode.random = map.get(current.random);  // Returns null if key is null ✓
   ```

3. **One-pass attempt:** Trying to assign pointers while creating nodes
   - Fails when random points to a node not yet created

4. **Forgetting to return map.get(head):** Returning head instead of the copy

5. **Modifying original list:** In O(1) space solution, ensure you restore original structure

---

## 12. Visual Comparison: Shallow vs Deep Copy

### Shallow Copy (WRONG):
```
Original: A → B → C
          ↓
Copy:     Same A, Same B, Same C (just references)
```

### Deep Copy (CORRECT):
```
Original: A → B → C
          ↓
Copy:     A' → B' → C' (completely new nodes)
```

---

## 13. When to Use Each Approach

### Use HashMap (Solution 1) when:
- ✅ Clarity and maintainability are important
- ✅ Space is not a critical constraint
- ✅ You want simpler, more readable code
- ✅ **Most interview scenarios** (recommended)

### Use Interweaving (Solution 2) when:
- ✅ Interviewer asks for O(1) space optimization
- ✅ You need to demonstrate advanced techniques
- ✅ Memory is extremely limited
- ⚠️ More complex and error-prone

---

## 14. Related Problems

This pattern appears in:
- **Clone Graph** (similar deep copy with multiple pointers)
- **Serialize and Deserialize Binary Tree**
- **Copy List with Arbitrary Pointer**

The HashMap approach is a fundamental technique for handling complex pointer relationships!

---

This is a classic medium problem that tests your understanding of deep copying and HashMap usage. The two-pass HashMap solution is elegant and industry-standard!

