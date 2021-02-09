package com.utils.algorithm;


import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        int[] array = {10, 9, 0, 57, 6, 3,89,324,2,5};
        int[] ints = quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(ints));

    }

    /**
     * 时间复杂度最坏是O(n^2),一般都是O(nlogn) 也就是 2 * T(n/2) + O(n)
     */
    public static int[] quickSort(int[] array, int begin, int end) {
        if (begin < end) {
            // 首先确定轴点index
            int index = partition(array, begin, end);
            // 对左边的子序列进行排序
            quickSort(array, begin, index - 1);
            // 对右边的子序列进行排序
            quickSort(array, index + 1, end);
        }
        return array;
    }

    public static int partition(int[] array, int begin, int end) {
        int pivot = begin;
        int index = pivot + 1;
        for (int i = index; i <= end; i++) {
            if (array[i] < array[pivot]) {
                swap(array, i, index);
                index++;
            }
        }
        swap(array, pivot, index - 1);
        return index - 1;
    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
