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
