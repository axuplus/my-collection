package com.utils.algorithm;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] array = {10, 6, 7, 1, 3, 9, 5, 4, 2, 8};
        System.out.println(Arrays.toString(mergeSort(array, 0, array.length - 1)));
    }


    /**
     * 归并排序，每次 /2即可
     *
     * @param array
     * @param l
     * @param r
     * @return
     */
    static int[] mergeSort(int[] array, int l, int r) {
        if (l >= r) {
            return array;
        }

        int m = (l + r) >>> 1;
        // 递归left 长度既是l到m的距离
        mergeSort(array, l, m);
        // 递归right 长度既是m+1到m的距离
        mergeSort(array, m + 1, r);

        // 合并
        merge(array, l, m, r);
        return array;
    }

    static void merge(int[] array, int l, int m, int r) {
        int i = l;
        int j = m + 1;
        // 临时数组
        int[] temp = new int[r - l + 1];
        // 临时索引
        int index = 0;
        while (i <= m && j <= r) {
            if (array[i] <= array[j]) {
                temp[index++] = array[i++];
            } else {
                temp[index++] = array[j++];
            }
        }
        // 将左边剩余元素填充进temp中￿
        while (i <= m) {
            temp[index++] = array[i++];
        }
        // 将右序列剩余元素填充进temp中
        while (j <= r) {
            temp[index++] = array[j++];
        }

        // 将temp中的元素全部拷贝到原数组中
        index = 0;
        while (l <= r) {
            array[l++] = temp[index++];
        }
    }
}
