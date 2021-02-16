package com.utils.algorithm;

import java.util.Arrays;

public class MergeList {
    public static void main(String[] args) {
        int[] A = {1, 2, 3, 0, 0, 0};
        int[] B = {2, 5, 6};
        int m = 3;
        int n = 3;
        merge(A, m, B, n);
        System.out.println(Arrays.toString(A));
    }


    static void merge(int[] A, int m, int[] B, int n) {
        int i = m - 1, j = n - 1;
        while (i >= 0 && j >= 0) {
            if (B[j] >= A[i]) {
                A[i + j + 1] = B[j--];
            } else {
                A[i + j + 1] = A[i--];
            }
        }
        while (j >= 0) {
            A[j] = B[j--];
        }
    }
}
