package com.utils.util;

import com.utils.util.collection.CollectionUtils;
import com.utils.util.collection.StringCache;
import org.apache.commons.lang3.text.StrBuilder;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
@SuppressWarnings({"all"})
public class StringUtils {




	private static ThreadLocal<StringCache> cacheLocal = new ThreadLocal<StringCache>() {
		@Override
		protected StringCache initialValue() {
			return new StringCache();
		}
	};

	public static final String DEFAULT_EMPTY_STRING = "";

    public static final String DEFAULT_NULL_STRING = "null";

    public static final String DOT = ".";

	public static final String SLASH = "/";

	public static final String EMPTY = "";

	public static final String UNDERLINE = "_";

	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static long strOffset;


	/**
	 * 字符串是否为空 空的定义如下： <br/>
	 * 1、为null <br/>
	 * 2、为不可见字符（如空格）<br/>
	 * 3、""<br/>
	 * @param str 被检测的字符串
	 * @return 是否为空
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 是否包含空字符串
	 * @param strs 字符串列表
	 * @return 是否包含空字符串
	 */
	public static boolean hasBlank(String... strs) {
		if (CollectionUtils.isEmpty(strs)) {
			return true;
		}
		for (String str : strs) {
			if (isBlank(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检测指定的src是否不为空。不为空返回true
	 * @param src
	 * @return
	 */
	public static boolean isNotBlank(String src) {
		if (src != null && src.trim().equals("") != true) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 等于null，或者等于“”
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return (str == null || "".equals(str) || str.length() == 0);
	}

	/**
	 * 字符串是否为非空白 空白的定义如下： <br>
	 * 1、不为null <br>
	 * 2、不为""<br>
	 *
	 * @param str 被检测的字符串
	 * @return 是否为非空
	 */
	public static boolean isNotEmpty(String str) {
		return false == isEmpty(str);
	}

	/**
	 * 将byte数组以16进制的形式输出
	 * @param src
	 * @return
	 */
	public static String toHexString(byte[] src) {
		StringCache cache = new StringCache();
		for (byte b : src) {
			cache.append(DIGITS_LOWER[(b & 0xf0) >>> 4]);
			cache.append(DIGITS_LOWER[b & 0x0f]);
		}
		return cache.toString();
	}

	/**
	 *
	 * @param str
	 * @return
	 */
	public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StrBuilder(strLen)
            .append(Character.toTitleCase(str.charAt(0)))
            .append(str.substring(1))
            .toString();
    }

	/**
	 * 将hex字符的字符串转变byte数组
	 * @param hexStr
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexStr) {
		return hexCharsToBytes(hexStr.toLowerCase().toCharArray());
	}

	/**
	 * 将hex字符的char数组转变byte数组
	 * @param hexChars
	 * @return
	 */
	public static byte[] hexCharsToBytes(char[] hexChars) {
		if ((hexChars.length & 0x01) == 1) {
			throw new RuntimeException("用于解析的十六进制字符数组的长度不对，不是2的整数倍");
		}
		int length = hexChars.length / 2;
		byte[] result = new byte[length];
		for (int i = 0; i < hexChars.length; i += 2) {
			int f = toDigit(hexChars[i]) << 4;
			f = f | toDigit(hexChars[i + 1]);
			result[i >> 1] = (byte) f;
		}
		return result;
	}

	/**
	 *
	 * @param c
	 * @return
	 */
	private static int toDigit(char c) {
		int index = 0;
		for (; index < 16; index++) {
			if (DIGITS_LOWER[index] == c) {
				return index;
			}
		}
		throw new RuntimeException("字符" + c + "不是小写十六进制的字符");
	}

	/**
	 * 使用匹配规则检测字符串，如果匹配返回true 匹配规则为，如果有*则认为可以是任意字符，从前到后匹配
	 * @param src 需要检测的字符串
	 * @param rule  匹配规则
	 * @return
	 */
	public static boolean match(String src, String rule) {
		if (rule.contains("*")) {
			String[] tmps = rule.split("\\*");
			int index = 0;
			for (int i = 0; i < tmps.length; i++) {
				// 从前往后匹配，每一次匹配成功，将index增加，这样就可以去匹配的字符串
				index = src.indexOf(tmps[i], index);
				if (index >= 0) {
					index += tmps[i].length();
				} else {
					break;
				}
			}
			// 不匹配，返回false
			if (index == -1) {
				return false;
			}
			// 如果结尾不是*号，则匹配完毕必然是index==src.length。如果只有*，则index=0.
			else if (index == src.length() || index == 0) {
				return true;
			}
			// 如果index比src的长度小，又不是0.那么如果rule中*是最后一个字母，则表示此时匹配，返回true
			else if (rule.charAt(rule.length() - 1) == '*') {
				return true;
			} else {
				return false;
			}
		} else {
			return src.equals(rule);
		}
	}


	/**
	 * 从char数组中确定大括号的位置，如果不存在返回-1
	 * @param array
	 * @param off
	 * @return
	 */
	private static int indexOfBrace(char[] array, int off) {
		int length = array.length - 1;
		for (int i = off; i < length; i++) {
			if (array[i] == '{' && array[i + 1] == '}') {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 得到字符串的内存字节表示
	 * @param str
	 * @return
	 */
	public static String getHexBytes(String str) {
		char[] array = str.toLowerCase().toCharArray();
		int length = array.length;
		byte[] tmp = new byte[length * 2];
		char c;
		int index = 0;
		for (int i = 0; i < length; i++) {
			c = array[i];
			tmp[index++] = (byte) (c >>> 8);
			tmp[index++] = (byte) c;
		}
		return toHexString(tmp);
	}

	 /**
     * <p>判断字符串是否有长度(不为null或"")</p>
     * <pre>
     * StringUtilss.hasLength(null)          = false
     * StringUtilss.hasLength("")            = false
     * StringUtilss.hasLength("  ")       	= true
     * StringUtilss.hasLength(" abc ")       = true
     * </pre>
     * @param str
     * @return
     */
    public static boolean hasLength(String str) {
        return str == null ? false : str.length() > 0;
    }

    /**
     * <p>Null-safe 调用String.trim()方法</p>
     * <pre>
     * StringUtilss.trim(null)          		= null
     * StringUtilss.trim("")            		= ""
     * StringUtilss.trim("     ")       		= ""
     * StringUtilss.trim("abc")         		= "abc"
     * StringUtilss.trim("  abc  ") 			= "abc"
     * </pre>
     * @param str
     * @return
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * <p>Null-safe 调用String.trim()方法</p>
     * <pre>
     * StringUtilss.trim(null)          		= null
     * StringUtilss.trim("")            		= ""
     * StringUtilss.trim("     ")       		= ""
     * StringUtilss.trim("  abc  ")         	= "abc"
     * StringUtilss.trim(" a b c ") 			= "abc"
     * </pre>
     * @param str
     * @return
     */
    public static String trimAll(String str) {
        return str == null ? null : str.trim().replace(" ", "");
    }

    /**
     * <p>对字符串trim操作,当字符串为空值(null、""、" "、"null")时转换为null</p>
     * <pre>
     * StringUtilss.trimToNull(null)          = null
     * StringUtilss.trimToNull("")            = null
     * StringUtilss.trimToNull("null")        = null
     * StringUtilss.trimToNull("     ")       = null
     * StringUtilss.trimToNull("abc")         = "abc"
     * StringUtilss.trimToNull("  abc  ") 	 = "abc"
     * </pre>
     * @param str
     * @return
     */
    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * <p>对字符串trim操作,当字符串为空值(null、""、" "、"null")时转换为""</p>
     * <pre>
     * StringUtilss.trimToEmpty(null)          = ""
     * StringUtilss.trimToEmpty("")            = ""
     * StringUtilss.trimToEmpty("null")        = ""
     * StringUtilss.trimToEmpty("     ")       = ""
     * StringUtilss.trimToEmpty("abc")         = "abc"
     * StringUtilss.trimToEmpty("  abc  ")     = "abc"
     * </pre>
     * @param str
     * @return
     */
    public static String trimToEmpty(String str) {
        return str == null ? DEFAULT_EMPTY_STRING : str.trim();
    }

    /**
     * <p>如果字符串为null则将其转换为""</p>
     * <pre>
     * StringUtilss.defaultIfNull(null)   		= ""
     * StringUtilss.defaultIfNull("")  			= ""
     * StringUtilss.defaultIfNull("  ")  		= "  "
     * StringUtilss.defaultIfNull("abc") 		= "abc"
     * </pre>
     * @param str
     * @return
     */
    public static String defaultIfNull(String str) {
        return defaultIfNull(str, DEFAULT_EMPTY_STRING);
    }

    /**
     * <p>如果字符串为null则将其转换为defaultValue</p>
     * <pre>
     * StringUtilss.defaultIfNull(null, "abc")   	= "abc"
     * StringUtilss.defaultIfNull("", "abc")  		= ""
     * StringUtilss.defaultIfNull("  ", "abc")  		= "  "
     * StringUtilss.defaultIfNull("abc", "abc") 		= "abc"
     * </pre>
     * @param str
     * @param defaultValue
     * @return
     */
    public static String defaultIfNull(String str, String defaultValue) {
        return str == null ? defaultValue : str;
    }

    /**
     * <p>如果字符串为null则将其转换为""</p>
     * <pre>
     * StringUtilss.defaultIfEmpty(null)   		= ""
     * StringUtilss.defaultIfEmpty("")  			= ""
     * StringUtilss.defaultIfEmpty("  ")  		= "  "
     * StringUtilss.defaultIfEmpty("abc") 		= "abc"
     * </pre>
     * @param str
     * @return
     */
    public static String defaultIfEmpty(String str) {
        return defaultIfEmpty(str, DEFAULT_EMPTY_STRING);
    }

    /**
     * <p>如果字符串为空值(null、""、" ")则将其转换为defaultValue</p>
     * <pre>
     * StringUtilss.defaultIfEmpty(null, "abc")   	= "abc"
     * StringUtilss.defaultIfEmpty("", "abc")  		= "abc"
     * StringUtilss.defaultIfEmpty("null", "abc")  	= "abc"
     * StringUtilss.defaultIfEmpty("  ", "abc")  	= "abc"
     * StringUtilss.defaultIfEmpty("abc", "abc") 	= "abc"
     * </pre>
     * @param str
     * @param defaultValue
     * @return
     */
    public static String defaultIfEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    /**
     * <p>判断两个字符串是否相同</p>
     * <pre>
     * StringUtilss.equals(null, null)   	= true
     * StringUtilss.equals(null, "abc")  	= false
     * StringUtilss.equals("abc", null)  	= false
     * StringUtilss.equals("abc", "abc") 	= true
     * StringUtilss.equals("abc", "ABC") 	= false
     * </pre>
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    /**
     * <p>判断两个字符串不区分大小写比较是否相同</p>
     * <pre>
     * StringUtilss.equals(null, null)   	= true
     * StringUtilss.equals(null, "abc")  	= false
     * StringUtilss.equals("abc", null)  	= false
     * StringUtilss.equals("abc", "abc") 	= true
     * StringUtilss.equals("abc", "ABC") 	= true
     * </pre>
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    /**
     * <p>判断两个字符串trim之后比较是否相同</p>
     * <pre>
     * StringUtilss.trimEquals(null, null)   	= true
     * StringUtilss.trimEquals("", "  ")   		= true
     * StringUtilss.trimEquals(null, "abc")  	= false
     * StringUtilss.trimEquals("abc", null)  	= false
     * StringUtilss.trimEquals(" abc ", "abc") 	= true
     * StringUtilss.trimEquals("abc", "ABC") 	= false
     * </pre>
     * @param str1
     * @param str2
     * @return
     */
    public static boolean trimEquals(String str1, String str2) {
    	if(str1 == null || str2 == null){
    		return str1 == str2;
    	}
        return str1.trim().equals(str2.trim());
    }

    /**
     * <p>判断两个字符串trim之后不区分大小写比较是否相同</p>
     * <pre>
     * StringUtilss.trimEquals(null, null)   	= true
     * StringUtilss.trimEquals("", "  ")   		= true
     * StringUtilss.trimEquals(null, "abc")  	= false
     * StringUtilss.trimEquals("abc", null)  	= false
     * StringUtilss.trimEquals(" abc ", "abc") 	= true
     * StringUtilss.trimEquals("abc", "ABC") 	= true
     * </pre>
     * @param str1
     * @param str2
     * @return
     */
    public static boolean trimEqualsIgnoreCase(String str1, String str2) {
    	if(str1 == null || str2 == null){
    		return false;
    	}
        return str1.trim().equalsIgnoreCase(str2.trim());
    }

    /**
     * <p>分别从目标字符串中的两端剔除需要剔除的字符串stripChars</p>
     * <pre>
     * StringUtilss.strip(null, *)          = null
     * StringUtilss.strip("", *)            = ""
     * StringUtilss.strip("abc", null)      = "abc"
     * StringUtilss.strip("  abc", null)    = "abc"
     * StringUtilss.strip("abc  ", null)    = "abc"
     * StringUtilss.strip(" abc ", null)    = "abc"
     * StringUtilss.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     * @param str
     * @param stripChars
     * @return
     */
    public static String strip(String str, String stripChars) {
        str = stripStart(str, stripChars);
        return stripEnd(str, stripChars);
    }

    /**
     * <p>从目标字符串中的起始端开始剔除需要剔除的字符串stripChars</p>
     * <pre>
     * StringUtilss.stripStart(null, *)          = null
     * StringUtilss.stripStart("", *)            = ""
     * StringUtilss.stripStart("abc", "")        = "abc"
     * StringUtilss.stripStart("abc", null)      = "abc"
     * StringUtilss.stripStart("  abc", null)    = "abc"
     * StringUtilss.stripStart("abc  ", null)    = "abc  "
     * StringUtilss.stripStart(" abc ", null)    = "abc "
     * StringUtilss.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripStart(String str, String stripChars) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while (start != strLen && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while (start != strLen && stripChars.indexOf(str.charAt(start)) != -1) {
                start++;
            }
        }
        return str.substring(start);
    }

    /**
     * <p>从目标字符串中的末端开始剔除需要剔除的字符串stripChars</p>
     * <pre>
     * StringUtilss.stripEnd(null, *)          = null
     * StringUtilss.stripEnd("", *)            = ""
     * StringUtilss.stripEnd("abc", "")        = "abc"
     * StringUtilss.stripEnd("abc", null)      = "abc"
     * StringUtilss.stripEnd("  abc", null)    = "  abc"
     * StringUtilss.stripEnd("abc  ", null)    = "abc"
     * StringUtilss.stripEnd(" abc ", null)    = " abc"
     * StringUtilss.stripEnd("  abcyx", "xyz") = "  abc"
     * StringUtilss.stripEnd("120.00", ".0")   = "12"
     * </pre>
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripEnd(String str, String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    /**
     * <p>在目标字符串targetStr左边补充字符appendChar,使得目标字符串的总长度达到length
     * (注：targetStr为null时返回null)</p>
     * @param targetStr
     * @param appendChar
     * @param length
     * @return
     */
    public static String leftPad(String targetStr, char appendChar, int length) {
        if (targetStr == null) {
            return null;
        }
        int len = targetStr.length();
        while (len++ < length) {
            targetStr = appendChar + targetStr;
        }
        return targetStr;
    }

    /**
     * <p>在目标字符串targetStr右边补充字符appendChar,使得目标字符串的总长度达到length
     * (注：targetStr为null时返回null)</p>
     * @param targetStr
     * @param appendChar
     * @param length
     * @return
     */
    public static String rightPad(String targetStr, char appendChar, int length) {
        if (targetStr == null) {
            return null;
        }
        int len = targetStr.length();
        while (len++ < length) {
            targetStr += appendChar;
        }
        return targetStr;
    }

    /**
     * <p>判断字符串是否全是由字母组成</p>
     * <pre>
     * StringUtilss.isAlpha(null)   		= false
     * StringUtilss.isAlpha("")     		= false
     * StringUtilss.isAlpha("  ")  		= false
     * StringUtilss.isAlpha("abc")  		= true
     * StringUtilss.isAlpha("ab2c") 		= false
     * StringUtilss.isAlpha("ab-c") 		= false
     * </pre>
     * @param str
     * @return
     */
    public static boolean isAlpha(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLetter(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>判断字符是否由字母或者数字[a-zA-Z0-9]组成</p>
     * @param str
     * @return
     */
    public static boolean isAlphanumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLetterOrDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>判断字符串是否全由数字组成[0-9]</p>
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>反序返回字符串</p>
     * <pre>
     * StringUtilss.reverse(null)  		= null
     * StringUtilss.reverse("")    		= ""
     * StringUtilss.reverse("bat") 		= "tab"
     * </pre>
     * @param str
     * @return
     */
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
	 * 字符串是否为空 空的定义如下： <br/>
	 * 1、为null <br/>
	 * 2、为不可见字符（如空格）<br/>
	 * 3、""<br/>
	 * @param str 被检测的字符串
	 * @return 是否为空
	 */
	public static boolean isEmptyOrBlank(String str) {
		return (str == null || str.trim().length() == 0) ? true : false;
	}

	/**
	 * Description: Replaces last substring of this string that matches the
	 * given regular expression with the given replacement.<br>
	 * Do not worry about null pointer
	 * @param @param regex
	 * @param @param replacement
	 * @return String
	 * @throws
	 */
	public static String replaceAll( String originalStr, String replacement, String regex ) {
		return StringUtils.trimToEmpty( originalStr ).replaceAll( regex, replacement );
	}

	public static String replaceAll( String originalStr, String replacement, String... regexArray ) {
		if ( 0 == regexArray.length )
			return originalStr;
		for ( String regex : regexArray ) {
			originalStr = StringUtils.replaceAll( originalStr, replacement, regex );
		}
		return originalStr;
	}

	/**
	 * Description: Replaces last substring of this string that matches the
	 * given regular expression with the given replacement.
	 * @param @param regex
	 * @param @param replacement
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String replaceLast( String originalStr, String regex, String replacement ) {

		if (StringUtils.isBlank( originalStr ) )
			return "";

		int index = originalStr.lastIndexOf( regex );
		if ( -1 == index )
			return originalStr;

		String temp = originalStr.substring( 0, index );
		String temp2 = originalStr.substring( index, originalStr.length() );

		temp2 = temp2.replaceFirst( regex, replacement );

		originalStr = temp + temp2;

		return originalStr;
	}


	/**
	 * 获得一个随机的字符串
	 * @param length 字符串的长度
	 * @return 随机字符串
	 */
	public static String getRandomString(int length) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();

		if (length < 1) {
			length = 1;
		}
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}



	/**
	 * 将byte数组转为字符串
	 *
	 * @param bytes byte数组
	 * @param charset 字符集
	 * @return 字符串
	 */
	public static String str(byte[] bytes, String charset) {
		return str(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
	}

	/**
	 * 解码字节码
	 *
	 * @param data 字符串
	 * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
	 * @return 解码后的字符串
	 */
	public static String str(byte[] data, Charset charset) {
		if (data == null) {
			return null;
		}

		if (null == charset) {
			return new String(data);
		}
		return new String(data, charset);
	}

	/**
	 * 将编码的byteBuffer数据转换为字符串
	 * @param data 数据
	 * @param charset 字符集，如果为空使用当前系统字符集
	 * @return 字符串
	 */
	public static String str(ByteBuffer data, String charset){
		if(data == null) {
			return null;
		}

		return str(data, Charset.forName(charset));
	}

	/**
	 * 将编码的byteBuffer数据转换为字符串
	 * @param data 数据
	 * @param charset 字符集，如果为空使用当前系统字符集
	 * @return 字符串
	 */
	public static String str(ByteBuffer data, Charset charset){
		if(null == charset) {
			charset = Charset.defaultCharset();
		}
		return charset.decode(data).toString();
	}

	/**
	 * 改进JDK subString<br>
	 * index从0开始计算，最后一个字符为-1<br>
	 * 如果from和to位置一样，返回 "" <br>
	 * 如果from或to为负数，则按照length从后向前数位置，如果绝对值大于字符串长度，则from归到0，to归到length<br>
	 * 如果经过修正的index中from大于to，则互换from和to
	 * example: <br>
	 * 	abcdefgh 2 3 -> c <br>
	 * 	abcdefgh 2 -3 -> cde <br>
	 *
	 * @param string String
	 * @param fromIndex 开始的index（包括）
	 * @param toIndex 结束的index（不包括）
	 * @return 字串
	 */
	public static String sub(String string, int fromIndex, int toIndex) {
		int len = string.length();

		if (fromIndex < 0) {
			fromIndex = len + fromIndex;
			if(fromIndex < 0 ){
				fromIndex = 0;
			}
		}else if(fromIndex > len){
			fromIndex = len;
		}

		if (toIndex < 0) {
			toIndex = len + toIndex;
			if(toIndex < 0){
				toIndex = len;
			}
		}else if(toIndex > len){
			toIndex = len;
		}

		if (toIndex < fromIndex) {
			int tmp = fromIndex;
			fromIndex = toIndex;
			toIndex = tmp;
		}

		if (fromIndex == toIndex) {
			return EMPTY;
		}

		char[] strArray = string.toCharArray();
		char[] newStrArray = Arrays.copyOfRange(strArray, fromIndex, toIndex);
		return new String(newStrArray);
	}

	/**
	 * 切割前部分
	 *
	 * @param string 字符串
	 * @param toIndex 切割到的位置（不包括）
	 * @return 切割后的字符串
	 */
	public static String subPre(String string, int toIndex) {
		return sub(string, 0, toIndex);
	}

	/**
	 * 切割后部分
	 *
	 * @param string 字符串
	 * @param fromIndex 切割开始的位置（包括）
	 * @return 切割后的字符串
	 */
	public static String subSuf(String string, int fromIndex) {
		if (isEmpty(string)) {
			return null;
		}
		return sub(string, fromIndex, string.length());
	}


	/**
	 * 转换基本类型
	 * @param clazz
	 * @return
	 */
	public static Class<?> castToPrimitive(Class<?> clazz) {
		BASIC_TYPE basicType;
		try {
			basicType = BASIC_TYPE.valueOf(clazz.getSimpleName().toUpperCase());
		}catch(Exception e) {
			return clazz;
		}
		//基本类型
		switch (basicType) {
			case BYTE:
				return byte.class;
			case SHORT:
				return short.class;
			case INT:
				return int.class;
			case LONG:
				return long.class;
			case DOUBLE:
				return double.class;
			case FLOAT:
				return float.class;
			case BOOLEAN:
				return boolean.class;
			case CHAR:
				return char.class;
			default:
				return clazz;
		}
	}

	/**
	 * 获得set或get方法对应的标准属性名<br/>
	 * 例如：setName 返回 name
	 * @param methodNameWithGet
	 * @return 如果是set或get方法名，返回field， 否则null
	 */
	public static String getGeneralField(String methodNameWithGet){
		if(methodNameWithGet.startsWith("get") || methodNameWithGet.startsWith("set")) {
			return cutPreAndLowerFirst(methodNameWithGet, 3);
		}
		return null;
	}

	/**
	 * 生成set方法名<br/>
	 * 例如：name 返回 setName
	 * @param fieldName 属性名
	 * @return setXxx
	 */
	public static String genSetter(String fieldName){
		return upperFirstAndAddPre(fieldName, "set");
	}

	/**
	 * 生成get方法名
	 * @param fieldName 属性名
	 * @return getXxx
	 */
	public static String genGetter(String fieldName){
		return upperFirstAndAddPre(fieldName, "get");
	}

	/**
	 * 去掉首部指定长度的字符串并将剩余字符串首字母小写<br/>
	 * 例如：str=setName, preLength=3 -> return name
	 * @param str 被处理的字符串
	 * @param preLength 去掉的长度
	 * @return 处理后的字符串，不符合规范返回null
	 */
	public static String cutPreAndLowerFirst(String str, int preLength) {
		if(str == null) {
			return null;
		}
		if(str.length() > preLength) {
			char first = Character.toLowerCase(str.charAt(preLength));
			if(str.length() > preLength +1) {
				return first +  str.substring(preLength +1);
			}
			return String.valueOf(first);
		}
		return null;
	}

	/**
	 * 原字符串首字母大写并在其首部添加指定字符串
	 * 例如：str=name, preString=get -> return getName
	 * @param str 被处理的字符串
	 * @param preString 添加的首部
	 * @return 处理后的字符串
	 */
	public static String upperFirstAndAddPre(String str, String preString) {
		if(str == null || preString == null) {
			return null;
		}
		return preString + Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	/**
	 * 大写首字母<br>
	 * 例如：str = name, return Name
	 * @param str 字符串
	 * @return
	 */
	public static String upperFirst(String str) {
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	/**
	 * 小写首字母<br>
	 * 例如：str = Name, return name
	 * @param str 字符串
	 * @return
	 */
	public static String lowerFirst(String str) {
		return Character.toLowerCase(str.charAt(0)) + str.substring(1);
	}

	/**
	 * 去掉指定前缀
	 * @param str 字符串
	 * @param prefix 前缀
	 * @return 切掉后的字符串，若前缀不是 preffix， 返回原字符串
	 */
	public static String removePrefix(String str, String prefix) {
		if(str != null && str.startsWith(prefix)) {
			return str.substring(prefix.length());
		}
		return str;
	}

	/**
	 * 忽略大小写去掉指定前缀
	 * @param str 字符串
	 * @param prefix 前缀
	 * @return 切掉后的字符串，若前缀不是 prefix， 返回原字符串
	 */
	public static String removePrefixIgnoreCase(String str, String prefix) {
		if (str != null && str.toLowerCase().startsWith(prefix.toLowerCase())) {
			return str.substring(prefix.length());
		}
		return str;
	}

	/**
	 * 去掉指定后缀
	 * @param str 字符串
	 * @param suffix 后缀
	 * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
	 */
	public static String removeSuffix(String str, String suffix) {
		if (str != null && str.endsWith(suffix)) {
			return str.substring(0, str.length() - suffix.length());
		}
		return str;
	}

	/**
	 * 忽略大小写去掉指定后缀
	 * @param str 字符串
	 * @param suffix 后缀
	 * @return 切掉后的字符串，若后缀不是 suffix， 返回原字符串
	 */
	public static String removeSuffixIgnoreCase(String str, String suffix) {
		if (str != null && str.toLowerCase().endsWith(suffix.toLowerCase())) {
			return str.substring(0, str.length() - suffix.length());
		}
		return str;
	}

	/**
	 * 切分字符串<br/>
	 * a#b#c -> [a,b,c]
	 * a##b#c -> [a,"",b,c]
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @return 切分后的集合
	 */
	public static List<String> split(String str, char separator) {
		return split(str, separator, 0);
	}

	/**
	 * 切分字符串
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数
	 * @return 切分后的集合
	 */
	public static List<String> split(String str, char separator, int limit){
		if(str == null) {
			return null;
		}
		List<String> list = new ArrayList<String>(limit == 0 ? 16 : limit);
		if(limit == 1) {
			list.add(str);
			return list;
		}

		boolean isNotEnd = true;	//未结束切分的标志
		int strLen = str.length();
		StringBuilder sb = new StringBuilder(strLen);
		for(int i=0; i < strLen; i++) {
			char c = str.charAt(i);
			if(isNotEnd && c == separator) {
				list.add(sb.toString());
				//清空StringBuilder
				sb.delete(0, sb.length());

				//当达到切分上限-1的量时，将所剩字符全部作为最后一个串
				if(limit !=0 && list.size() == limit-1) {
					isNotEnd = false;
				}
			}else {
				sb.append(c);
			}
		}
		list.add(sb.toString());
		return list;
	}

	/**
	 * 切分字符串<br>
	 * from jodd
	 * @param str 被切分的字符串
	 * @param delimiter 分隔符
	 * @return
	 */
	public static String[] split(String str, String delimiter) {
		if(str == null) {
			return null;
		}
		if(str.trim().length() == 0) {
			return new String[]{str};
		}

		int dellen = delimiter.length();	//del length
		int maxparts = (str.length() / dellen) + 2;		// one more for the last
		int[] positions = new int[maxparts];

		int i, j = 0;
		int count = 0;
		positions[0] = - dellen;
		while ((i = str.indexOf(delimiter, j)) != -1) {
			count++;
			positions[count] = i;
			j = i + dellen;
		}
		count++;
		positions[count] = str.length();

		String[] result = new String[count];

		for (i = 0; i < count; i++) {
			result[i] = str.substring(positions[i] + dellen, positions[i + 1]);
		}
		return result;
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->hello_world
	 *
	 * @param camelCaseStr 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String toUnderlineCase(String camelCaseStr) {
		if (camelCaseStr == null) {
			return null;
		}

		final int length = camelCaseStr.length();
		StringBuilder sb = new StringBuilder();
		char c;
		boolean isPreUpperCase = false;
		for (int i = 0; i < length; i++) {
			c = camelCaseStr.charAt(i);
			boolean isNextUpperCase = true;
			if (i < (length - 1)) {
				isNextUpperCase = Character.isUpperCase(camelCaseStr.charAt(i + 1));
			}
			if (Character.isUpperCase(c)) {
				if (!isPreUpperCase || !isNextUpperCase) {
					if (i > 0) sb.append(UNDERLINE);
				}
				isPreUpperCase = true;
			} else {
				isPreUpperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：hello_world->HelloWorld
	 *
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String toCamelCase(String name) {
		if (name == null) {
			return null;
		}
		if (name.contains(UNDERLINE)) {
			name = name.toLowerCase();

			StringBuilder sb = new StringBuilder(name.length());
			boolean upperCase = false;
			for (int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);

				if (c == '_') {
					upperCase = true;
				} else if (upperCase) {
					sb.append(Character.toUpperCase(c));
					upperCase = false;
				} else {
					sb.append(c);
				}
			}
			return sb.toString();
		} else
			return name;
	}
	/**
	 * 重复某个字符
	 * @param c 被重复的字符
	 * @param count 重复的数目
	 * @return 重复字符字符串
	 */
	public static String repeat(char c, int count) {
		char[] result = new char[count];
		for (int i = 0; i < count; i++) {
			result[i] = c;
		}
		return new String(result);
	}

	/**
	 * 给定字符串转换字符编码<br/>
	 * 如果参数为空，则返回原字符串，不报错。
	 * @param str 被转码的字符串
	 * @param sourceCharset 原字符集
	 * @param destCharset 目标字符集
	 * @return 转换后的字符串
	 */
	public static String transCharset(String str, String sourceCharset, String destCharset) {
		if(isEmpty(str) || isEmpty(sourceCharset) || isEmpty(destCharset)) {
			return str;
		}
		try {
			return new String(str.getBytes(sourceCharset), destCharset);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	/**
	 * 基本变量类型的枚举
	 */
	private static enum BASIC_TYPE {
		BYTE("byte"),
		SHORT("short"),
		INT("int"),
		LONG("long"),
		DOUBLE("double"),
		FLOAT("float"),
		BOOLEAN("boolean"),
		CHAR("char"),
		CHARACTER("character"),
		STRING("string");

		private Object value;

		private BASIC_TYPE(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.name() + " : " + value;
		}
	}
}
