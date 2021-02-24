package com.utils.algorithm.offer;

public class o4 {
    public static void main(String[] args) {

    }

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || (matrix.length == 1 && matrix[0].length == 0)) {
            return false;
        }
        int i = 0;
        // i j对应右上角
        int j = matrix[0].length - 1;
        while (i <= matrix.length - 1 && j >= 0) {
            // 先判断是否相等，否则会出现i j越界
            if (target == matrix[i][j]) {
                return true;
            } else if (target < matrix[i][j]) {
                j--;
            } else {
                i++;
            }
        }
        return false;
    }
}
