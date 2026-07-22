package io.neetcode.linkedlist;

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

public class CopyLinkedListwithRandomPointer {

    public Node copyRandomList(Node head) {
        Node curr = head;
        while (curr != null) {
            Node clone = new Node(curr.val);
            clone.next = curr.next;
            curr.next = clone;
            curr = clone.next;
        }
        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }

        curr = head;
        Node dummy = new Node(0);
        Node live = dummy;
        while (curr != null) {
            live.next = curr.next;
            live = live.next;
            curr.next = curr.next.next;
            curr = curr.next;
        }
        return dummy.next;
    }

    public Node copyRandomList(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        Node current = head;
        while (current != null) {
            map.put(current, new Node(current.val));
            current = current.next;
        }
        current = head;
        while (current != null) {
            Node mn = map.get(current);
            mn.next = map.get(current.next);
            mn.random = map.get(current.random);
            current = current.next;
        }
        return map.get(head);
    }
}
