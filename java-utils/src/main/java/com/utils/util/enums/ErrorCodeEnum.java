package com.utils.util.enums;

public enum ErrorCodeEnum {


    /**
     * Uac 10011041 error code enum.
     */
    GL99990100(9999100, "参数异常"),

    GL9999417(9999417, "操作失败"),

    GL99990101(9999101, "token is invalid"),

    GL99990401(9999401, "无访问权限"),

    GL500(500, "未知异常"),

    UAC10011041(10011041, "token已过期,请重新登录");

    private int code;
    private String msg;

    /**
     * Msg string.
     *
     * @return the string
     */
    public String msg() {
        return msg;
    }

    /**
     * Code int.
     *
     * @return the int
     */
    public int code() {
        return code;
    }

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Gets enum.
     *
     * @param code the code
     *
     * @return the enum
     */
    public static ErrorCodeEnum getEnum(int code) {
        for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }

}
