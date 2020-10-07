package com.utils.util;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @author: gzy
 * @date: 2018/12/25
 */
public class ValidateUtil {
    /**
     * 检验字符串是否包含大写
     */
    public static boolean isUppercase(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检验字符串是否包含小写
     */
    public static boolean islowercase(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检验字符串是否包涵数字
     */
    public static boolean isDigit(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检验字符串是否包含字母
     */
    public static boolean isLetter(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLetterOrDigit(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验参数是否为空
     *
     * @param object
     */
    public static boolean isNull(Object object) {
        if ("" == object || object == null) {
            return true;
        }
        return false;
    }

    /**
     * 校验参数是否为空
     */
    public static boolean isNotBlank(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        return true;
    }


    /**
     * 校验整形参数取值大小
     *
     * @param src
     * @param target
     */
    public static boolean isLargerThan(int src, int target) {
        if (src <= target) {
            return false;
        }
        return true;
    }

    /**
     * 校验长整型参数取值大小
     *
     * @param src
     * @param target
     */
    public static boolean isLargerThan(long src, long target) {
        if (src <= target) {
            return false;
        }
        return true;
    }

    /**
     * 校验浮点参数取值大小
     *
     * @param src
     * @param target
     */
    public static boolean largerThan(float src, float target) {
        if (src <= target) {
            return false;
        }
        return true;
    }

    /**
     * 校验双精度参数取值大小
     *
     * @param src
     * @param target
     */
    public static boolean largerThan(double src, double target) {
        if (src <= target) {
            return false;
        }
        return true;
    }

    /**
     * 校验整形参数取值大小
     *
     * @param src
     * @param target
     */
    public static boolean notLitterThan(int src, int target) {
        if (src <= target) {
            return false;
        }
        return true;
    }

    /**
     * 校验长整型参数取值大小
     *
     * @param src
     * @param target
     */
    public static boolean notLitterThan(long src, long target) {
        if (src <= target) {
            return false;
        }
        return true;
    }

    /**
     * 校验浮点参数取值大小
     *
     * @param src
     * @param target
     */
    public static boolean notLitterThan(float src, float target) {
        if (src <= target) {
            return false;
        }
        return true;
    }

    /**
     * 校验双精度参数取值大小
     *
     * @param src
     * @param target
     */
    public static boolean notLitterThan(double src, double target) {
        if (src <= target) {
            return false;
        }
        return true;
    }

    /**
     * 校验手机号码是否正确
     *
     * @param mobile
     */
    public static boolean isMobile(String mobile) {
        // "^[1][3,4,5,8][0-9]{9}$"
        //Pattern pattern = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$");
        //Pattern pattern = Pattern.compile("^[1][0-9]{10}");
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{4,8}$");
        //Pattern pattern = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$");
        return pattern.matcher(mobile).matches();
    }

    /**
     * 校验QQ号码是否正确
     *
     * @param qq
     */
    public static boolean isQQ(String qq) {
        Pattern pattern = Pattern.compile("^[1-9][0-9]{4,9}$");
        return pattern.matcher(qq).matches();
    }

    /**
     * 校验email格式是否正确
     *
     * @param email
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
        return pattern.matcher(email).matches();
    }

    /**
     * 校验IP格式是否正确
     *
     * @param ip
     * @return
     */
    public static boolean isIP(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        return pattern.matcher(ip).matches();
    }

}
