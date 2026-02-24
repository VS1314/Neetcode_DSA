package io.neetcode.linkedlist;

public class ReverseLinkedListII {

    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null || left == right) return head;
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
            prev.next = next;     // p, ,c,n ->
        }
        return dummy.next;
    }
}
