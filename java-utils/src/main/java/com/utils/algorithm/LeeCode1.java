package com.utils.algorithm;

import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.List;

public class LeeCode1 {
    public static void main(String[] args) {

    }

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
