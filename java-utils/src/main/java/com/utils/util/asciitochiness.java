package com.utils.util;

import java.io.UnsupportedEncodingException;

public class asciitochiness {

    public static void main(String[] args) throws UnsupportedEncodingException {

        String str = "\\xF0\\xA4\\x8B\\xAE";
        //注意：在16进制转字符串时我们要先将\x去掉再进行转码
        String stringss = hexStringToString(str.replaceAll("\\\\x", ""));
        System.out.println(stringss);

    }

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }
}
