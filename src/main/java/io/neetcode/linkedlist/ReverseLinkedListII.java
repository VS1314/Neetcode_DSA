package io.neetcode.linkedlist;

public class ReverseLinkedListII {

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right)
            return head;
        ListNode dummy = new ListNode(0, head);
        ListNode curr = dummy;
        for (int i = 1; i < left; i++)
            curr = curr.next;
        ListNode pre = curr;
        curr = curr.next;
        pre.next = null;
        ListNode rev = curr;
        for (int i = left; i < right; i++) {
            curr = curr.next;
        }
        ListNode post = curr.next;
        curr.next = null;
        ListNode prev = null, next;
        curr = rev;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        pre.next = prev;
        rev.next = post;
        return dummy.next;
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right)
            return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        for (int i = 1; i < left; i++) {
            prev = prev.next;
        }
        ListNode curr = prev.next;
        for (int i = 0; i < right - left; i++) {
            ListNode next = curr.next;
            curr.next = next.next;
            next.next = prev.next;// 1,3,2,4,5
            prev.next = next; // p, ,c,n ->
        }
        return dummy.next;
    }
}
