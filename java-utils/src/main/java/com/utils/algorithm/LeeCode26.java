package com.utils.algorithm;

import org.checkerframework.checker.units.qual.K;

public class LeeCode26 {
    public static void main(String[] args) {

    }

    static int removeDuplicates(int[] nums) {
        if (nums.length <= 1) {
            return 0;
        }

        if (nums.length == 0) return 0;
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[j] != nums[i]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }
}
