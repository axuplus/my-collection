package com.utils.algorithm;



public class LeeCode1 {
    public static void main(String[] args) {

    }

    /**
     *  计算两数之和
     * @param nums
     * @param target
     * @return
     */
    static int[] twoSums(int[] nums, int target) {
        for (int i = 0; i < nums.length; ++i) {
            for (int j = i + 1; i < nums.length; ++j) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }
}
