package com.utils.algorithm.offer;

import java.util.HashSet;
import java.util.Set;

public class o3 {
    public static void main(String[] args) {

    }

    /**
     * 利用哈希表
     *
     * @param nums
     * @return
     */
    static int findRepeatNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int res = -1;
        for (int i = 0; i < nums.length; i++) {
            if (!set.contains(nums[i])) {
                set.add(nums[i]);
            } else {
                res = nums[i];
            }
        }
        return res;
    }
}
