package com.utils.algorithm;

public class LeeCode21 {
    public static void main(String[] args) {

    }

    static ListNode mergeList(ListNode k1, ListNode k2) {
        if (k1 == null) return k2;
        if (k2 == null) return k1;

        ListNode head;
        // 判断两个k1,k2哪个小
        if (k1.val <= k2.val) {
            head = k1;
            k1 = k1.next;
        } else {
            head = k2;
            k2 = k2.next;
        }
        ListNode curr = head;
        while (k1 != null && k2 != null) {
            if (k1.val <= k2.val) {
                curr = curr.next = k1;
                k1 = k1.next;
            } else {
                curr = curr.next = k2;
                k2 = k2.next;
            }
        }
        // k1跟k2长度不一样，其中一个提前空了
        if (k1 == null) curr.next = k2;
        if (k2 == null) curr.next = k1;

        return head;
    }
}
