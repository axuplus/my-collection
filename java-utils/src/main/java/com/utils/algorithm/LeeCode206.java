package com.utils.algorithm;

public class LeeCode206 {
    public static void main(String[] args) {

    }

    /**
     * 翻转单个链表
     *
     * @param head
     * @return
     */
    static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 1:初始化两个指针 prev跟curr指针，prev指向头节点，curr指向头节点的下一位
        ListNode prev = head;
        ListNode curr = head.next;
        // 2:使用三指针法，反转链表的主体部分
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        // 3:修改头节点，将头节点的next指针指向空(防止出现链表回环)
        head.next = null;
        return prev;
    }
}


