package com.utils.algorithm;

public class BinarySearch {
    public static void main(String[] args) {
        int[] array = {2, 3, 25, 96};
        int searchA = binarySearchA(array, 0, array.length - 1, 2);
        System.out.println(searchA);
        int searchB = binarySearchB(array, 3);
        System.out.println(searchB);
        int search = halfSearch(array, 0, array.length - 1, 2);
        System.out.println("search = " + search);

    }

    public static int binarySearchA(int array[], int low, int high, int target) {
        if (low > high) return -1;
        int mid = low + (high - low) / 2;
        if (array[mid] > target)
            return binarySearchA(array, low, mid - 1, target);
        if (array[mid] < target)
            return binarySearchA(array, mid + 1, high, target);
        return mid;
    }

    public static int binarySearchB(int[] array, int key) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (array[mid] > key) {
                high = mid - 1;
            } else if (array[mid] < key) {
                low = mid + 1;
            }
            return mid;
        }
        return -1;
    }


    public static int halfSearch(int[] a, int left, int right, int target) {
        int mid = (left + right) / 2;
        int midValue = a[mid];
        if (left <= right) {
            if (midValue > target) {
                return halfSearch(a, left, mid - 1, target);
            } else if (midValue < target) {
                return halfSearch(a, mid + 1, right, target);
            } else {
                return mid;
            }
        }
        return -1;
    }
}
