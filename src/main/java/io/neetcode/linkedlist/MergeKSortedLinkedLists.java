package io.neetcode.linkedlist;

import java.util.ArrayList;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

public class MergeKSortedLinkedLists {

    public ListNode mergeKLists(ListNode[] lists) { // Divide & Conquer
        if (lists == null || lists.length == 0) return null;
        while (lists.length > 1) {
            List<ListNode> ll = new ArrayList<>();
            for (int i = 0; i < lists.length; i += 2) {
                ListNode l1 = lists[i];
                ListNode l2 = (i + 1 < lists.length) ? lists[i + 1] : null;
                ll.add(mergeTwoLists(l1, l2));
            }
            lists = ll.toArray(new ListNode[0]);
        }
        return lists[0];
    }

    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        curr.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }

    public ListNode mergeKLists(ListNode[] lists) { // Min Heap / Priority Queue
        if (lists == null || lists.length == 0) return null;
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);

        for (ListNode head : lists) {
            if (head != null) {
                pq.offer(head);
            }
        }
        ListNode dummy = new ListNode(0), curr = dummy;

        while (!pq.isEmpty()) {
            ListNode small = pq.poll();
            curr.next = small;
            curr = curr.next;
            if (small.next != null) pq.offer(small.next);
        }

        return dummy.next;
    }
}
