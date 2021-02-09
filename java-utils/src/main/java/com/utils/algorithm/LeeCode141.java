package com.utils.algorithm;

import java.util.HashSet;
import java.util.Set;

public class LeeCode141 {
    public static void main(String[] args) {

    }
    /**
     * 判断链表是否有回环
     */
    public boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }
        return false;
    }
}
