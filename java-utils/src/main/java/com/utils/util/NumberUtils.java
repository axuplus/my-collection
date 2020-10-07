package com.utils.util;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NumberUtils {

    /**
     * Check if the number is Negative:-0.1,-1,-10,...
     *
     * @param number
     * @return
     */
    public static boolean isNegative(Number number) {
        if (number.doubleValue() < 0) {
            return true;
        }
        return false;
    }

    /**
     * Check if the number is Negative:0.1,1,10,...
     *
     * @param number
     * @return
     */
    public static boolean isPositive(Number number) {
        if (number.doubleValue() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Check if the number is positive
     *
     * @param numberArray
     * @return
     */
    public static boolean isNegative(Number... numberArray) {

        if (null == numberArray || 0 == numberArray.length)
            return false;

        for (Number number : numberArray) {
            if (number.doubleValue() < 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if in interval
     *
     * @param interval such as: "[20,80]","(34,41521]"
     * @return
     */
    public static boolean isInInterval(String interval, String numberStr) {

        double number = 0;
        try {
            number = Double.parseDouble(numberStr);
        } catch (Exception e) {
            return false;
        }

        interval = StringUtils.trimToEmpty(interval);

        if (StringUtils.isBlank(interval))
            return false;

        /** (1,3) */
        String regex0 = "(\\()[0-9]+(.[0-9]+)?,[0-9]+(.[0-9]+)?(\\))";
        /** (1,3] */
        String regex1 = "(\\()[0-9]+(.[0-9]+)?,[0-9]+(.[0-9]+)?(\\])";
        /** [1,3) */
        String regex2 = "(\\[)[0-9]+(.[0-9]+)?,[0-9]+(.[0-9]+)?(\\))";
        /** [1,3] */
        String regex3 = "(\\[)[0-9]+(.[0-9]+)?,[0-9]+(.[0-9]+)?(\\])";

        Pattern pattern0 = Pattern.compile(regex0);
        Pattern pattern1 = Pattern.compile(regex1);
        Pattern pattern2 = Pattern.compile(regex2);
        Pattern pattern3 = Pattern.compile(regex3);

        Matcher matcher0 = pattern0.matcher(interval);
        Matcher matcher1 = pattern1.matcher(interval);
        Matcher matcher2 = pattern2.matcher(interval);
        Matcher matcher3 = pattern3.matcher(interval);

        interval = StringUtils.replaceAll(interval, "", SymbolConstant.PARENTHESES_BRACKETS_LEFT, SymbolConstant.PARENTHESES_BRACKETS_RIGHT, SymbolConstant.SQUARE_BRACKETS_LEFT,
                SymbolConstant.SQUARE_BRACKETS_RIGHT);

        String[] intervalArray = null;
        try {
            intervalArray = interval.split(SymbolConstant.COMMA);
        } catch (Exception e) {
            return false;
        }

        double intervalNumber0 = 0;
        double intervalNumber1 = 0;
        try {
            intervalNumber0 = Double.parseDouble(intervalArray[0]);
            intervalNumber1 = Double.parseDouble(intervalArray[1]);
        } catch (Exception e) {
            return false;
        }

        if (intervalNumber0 >= intervalNumber1)
            return false;

        /** (1,3) */
        if (matcher0.matches()) {
            return (number > intervalNumber0 && number < intervalNumber1);
        } else if (matcher1.matches()) {
            return (number > intervalNumber0 && number <= intervalNumber1);
        } else if (matcher2.matches()) {
            return (number >= intervalNumber0 && number < intervalNumber1);
        } else if (matcher3.matches()) {
            return (number > intervalNumber0 && number <= intervalNumber1);
        }
        return false;
    }

    /**
     * <p>
     * 判断字符串是否是数字
     * </p>
     *
     * @param str
     * @return
     */
    public static boolean isDigits(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * 判断字符串是否可以表示成一个Java Number类型 例如：123L、3.14f、2.0d、0x123
     * </p>
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        char[] chars = str.toCharArray();
        int sz = chars.length;
        boolean hasExp = false;
        boolean hasDecPoint = false;
        boolean allowSigns = false;
        boolean foundDigit = false;
        // deal with any possible sign up front
        int start = (chars[0] == '-') ? 1 : 0;
        if (sz > start + 1 && chars[start] == '0' && chars[start + 1] == 'x') {
            int i = start + 2;
            if (i == sz) {
                return false; // str == "0x"
            }
            // checking hex (it can't be anything else)
            for (; i < chars.length; i++) {
                if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f') && (chars[i] < 'A' || chars[i] > 'F')) {
                    return false;
                }
            }
            return true;
        }
        sz--; // don't want to loop to the last char, check it afterwords
        // for type qualifiers
        int i = start;
        // loop to the next to last char or to the last char if we need another digit to
        // make a valid number (e.g. chars[0..5] = "1234E")
        while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                foundDigit = true;
                allowSigns = false;

            } else if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                hasDecPoint = true;
            } else if (chars[i] == 'e' || chars[i] == 'E') {
                // we've already taken care of hex.
                if (hasExp) {
                    // two E's
                    return false;
                }
                if (!foundDigit) {
                    return false;
                }
                hasExp = true;
                allowSigns = true;
            } else if (chars[i] == '+' || chars[i] == '-') {
                if (!allowSigns) {
                    return false;
                }
                allowSigns = false;
                foundDigit = false; // we need a digit after the E
            } else {
                return false;
            }
            i++;
        }
        if (i < chars.length) {
            if (chars[i] >= '0' && chars[i] <= '9') {
                // no type qualifier, OK
                return true;
            }
            if (chars[i] == 'e' || chars[i] == 'E') {
                // can't have an E at the last byte
                return false;
            }
            if (chars[i] == '.') {
                if (hasDecPoint || hasExp) {
                    // two decimal points or dec in exponent
                    return false;
                }
                // single trailing decimal point after non-exponent is ok
                return foundDigit;
            }
            if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
                return foundDigit;
            }
            if (chars[i] == 'l' || chars[i] == 'L') {
                // not allowing L with an exponent or decimal point
                return foundDigit && !hasExp && !hasDecPoint;
            }
            // last character is illegal
            return false;
        }
        // allowSigns is true iff the val ends in 'E'
        // found digit it to make sure weird stuff like '.' and '1E-' doesn't pass
        return !allowSigns && foundDigit;
    }

    /**
     * <p>
     * 将抽象的Number转换为实际的某个特定类型<Byte,Short,Integer,Long,Float,Double>
     * </p>
     *
     * @param number
     * @param targetClass
     * @return
     * @throws IllegalArgumentException
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T convertNumber(Number number, Class<T> targetClass) {
        if (targetClass.isInstance(number)) {
            return (T) number;
        } else if (targetClass.equals(Byte.class)) {
            long value = number.longValue();
            if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) new Byte(number.byteValue());
        } else if (targetClass.equals(Short.class)) {
            long value = number.longValue();
            if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) new Short(number.shortValue());
        } else if (targetClass.equals(Integer.class)) {
            long value = number.longValue();
            if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                raiseOverflowException(number, targetClass);
            }
            return (T) new Integer(number.intValue());
        } else if (targetClass.equals(Long.class)) {
            return (T) new Long(number.longValue());
        } else if (targetClass.equals(BigInteger.class)) {
            if (number instanceof BigDecimal) {
                return (T) ((BigDecimal) number).toBigInteger();
            } else {
                return (T) BigInteger.valueOf(number.longValue());
            }
        } else if (targetClass.equals(Float.class)) {
            return (T) new Float(number.floatValue());
        } else if (targetClass.equals(Double.class)) {
            return (T) new Double(number.doubleValue());
        } else if (targetClass.equals(BigDecimal.class)) {
            return (T) new BigDecimal(number.toString());
        } else {
            throw new IllegalArgumentException(
                    "Could not convert number [" + number + "] of type [" + number.getClass().getName() + "] to unknown target class [" + targetClass.getName() + "]");
        }
    }

    private static void raiseOverflowException(Number number, Class<?> targetClass) {
        throw new IllegalArgumentException(
                "Could not convert number [" + number + "] of type [" + number.getClass().getName() + "] to target class [" + targetClass.getName() + "]: overflow");
    }

    /**
     * <p>
     * 将字符串类型的数字转换为实际的目标类型<Byte,Short,Integer,Long,Float,Double>
     * </p>
     *
     * @param text
     * @param targetClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T parseNumber(String text, Class<T> targetClass) {
        // Assert.notNull(text, "Parameter 'text' must be not null!");
        // Assert.notNull(targetClass, "Parameter 'targetClass' must be not null!");
        String trimmed = text.replaceAll(" ", "");

        if (targetClass.equals(Byte.class)) {
            return (T) (isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
        } else if (targetClass.equals(Short.class)) {
            return (T) (isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
        } else if (targetClass.equals(Integer.class)) {
            return (T) (isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
        } else if (targetClass.equals(Long.class)) {
            return (T) (isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed));
        } else if (targetClass.equals(BigInteger.class)) {
            return (T) (isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed));
        } else if (targetClass.equals(Float.class)) {
            return (T) Float.valueOf(trimmed);
        } else if (targetClass.equals(Double.class)) {
            return (T) Double.valueOf(trimmed);
        } else if (targetClass.equals(BigDecimal.class) || targetClass.equals(Number.class)) {
            return (T) new BigDecimal(trimmed);
        } else {
            throw new IllegalArgumentException("Cannot convert String [" + text + "] to target class [" + targetClass.getName() + "]");
        }
    }

    /**
     * <p>
     * 将字符串类型的数字转换为实际的带有格式的目标类型<Byte,Short,Integer,Long,Float,Double>
     * </p>
     *
     * @param text
     * @param targetClass
     * @param numberFormat
     * @return
     */
    public static <T extends Number> T parseNumber(String text, Class<T> targetClass, NumberFormat numberFormat) {
        if (numberFormat != null) {
            // Assert.notNull(text, "Parameter 'text' must be not null!");
            // Assert.notNull(targetClass, "Parameter 'targetClass' must be not null!");
            DecimalFormat decimalFormat = null;
            boolean resetBigDecimal = false;
            if (numberFormat instanceof DecimalFormat) {
                decimalFormat = (DecimalFormat) numberFormat;
                if (BigDecimal.class.equals(targetClass) && !decimalFormat.isParseBigDecimal()) {
                    decimalFormat.setParseBigDecimal(true);
                    resetBigDecimal = true;
                }
            }
            try {
                Number number = numberFormat.parse(text.replaceAll(" ", ""));
                return convertNumber(number, targetClass);
            } catch (ParseException ex) {
                throw new IllegalArgumentException("Could not parse number: " + ex.getMessage());
            } finally {
                if (resetBigDecimal) {
                    decimalFormat.setParseBigDecimal(false);
                }
            }
        } else {
            return parseNumber(text, targetClass);
        }
    }

    private static boolean isHexNumber(String value) {
        int index = (value.startsWith("-") ? 1 : 0);
        return (value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index));
    }

    /**
     * 将字符串类型的数字转换成BigInteger,支持十进制、十六进制、八进制写法
     *
     * @param value
     * @return
     */
    private static BigInteger decodeBigInteger(String value) {
        int radix = 10;
        int index = 0;
        boolean negative = false;

        if (value.startsWith("-")) {
            negative = true;
            index++;
        }

        if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        } else if (value.startsWith("#", index)) {
            index++;
            radix = 16;
        } else if (value.startsWith("0", index) && value.length() > 1 + index) {
            index++;
            radix = 8;
        }

        BigInteger result = new BigInteger(value.substring(index), radix);
        return (negative ? result.negate() : result);
    }

    /**
     * 生成随机的数字
     */
    public static String getRandomNumber(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        if (length < 1) {
            length = 1;
        }
        String base = "0123456789";
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
