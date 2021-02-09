package com.utils.algorithm;


import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        int[] array = {10, 9, 0, 57, 6, 3};
        bubbleSort(array);
    }

    /**
     * 冒泡排序的时间复杂度是O(n^2)
     */
    public static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(array));
    }
}
