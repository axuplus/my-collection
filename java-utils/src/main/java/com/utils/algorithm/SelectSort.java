package com.utils.algorithm;

import java.util.Arrays;

public class SelectSort {
    public static void main(String[] args) {
        int[] array = {10, 9, 0, 57, 6, 3};
        selectSort(array);
    }

    /**
     * 冒泡排序的时间复杂度是O(n^2)
     */
    public static void selectSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }
}
