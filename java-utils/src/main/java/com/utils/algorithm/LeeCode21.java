package com.utils.algorithm;

public class LeeCode21 {
    public static void main(String[] args) {

    }

    /**
     *  合并两个有序链表
     * @param l1
     * @param l2
     * @return
     */
    static ListNode mergeList(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode head;

        if (l1.val <= l2.val) {
            head = l1;
            l1 = l1.next;
        } else {
            head = l2;
            l2 = l2.next;
        }
        // 临时指针用来记录head当前的情况
        ListNode curr = head;
        // 指向另外一个链表的头节点
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                curr = curr.next = l1;
                l1 = l1.next;
            } else {
                curr = curr.next = l2;
                l2 = l2.next;
            }
        }
        // 其中一条链表提前空了则直接指向另外一条链表，剩余部分都是有序的
        if (l1 == null) {
            curr.next = l2;
        }
        if (l2 == null) {
            curr.next = l1;
        }
        return head;
    }
}
