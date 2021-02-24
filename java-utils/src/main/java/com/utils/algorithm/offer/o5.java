package com.utils.algorithm.offer;

public class o5 {
    public static void main(String[] args) {

    }

    public String replaceSpace(String s) {
        int n = s.length();
        int size = 0;
        char[] array = new char[n * 3];
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                array[size++] = '%';
                array[size++] = '2';
                array[size++] = '0';
            } else {
                array[size++] = c;
            }
        }
        return new String(array, 0, size);
    }
}
