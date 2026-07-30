package io.neetcode.linkedlist;

public class ReverseNodesinK_Group {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head);
        ListNode pregrp = dummy;
        while (true) {
            ListNode kth = getKth(pregrp.next, k);
            if (kth == null)
                break;
            ListNode postgrp = kth.next;
            kth.next = null;
            ListNode rev = pregrp.next;
            reverse(rev);
            rev.next = postgrp;
            pregrp.next = kth;
            pregrp = rev;
        }
        return dummy.next;
    }

    private ListNode getKth(ListNode curr, int k) {
        while (curr != null && k > 1) {
            curr = curr.next;
            k--;
        }
        return curr;
    }

    private void reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
    }
}
