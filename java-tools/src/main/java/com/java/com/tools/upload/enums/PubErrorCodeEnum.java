package com.java.com.tools.upload.enums;


public enum PubErrorCodeEnum {

    PUB10000001(10000001, "文件上传失败"),
    PUB10000002(10000002, "插入失败"),
    PUB10000003(10000003, "删除失败"),
    PUB10000004(10000004, "文件不能为空"),
    PUB10000005(10000005, "文件类型不支持");

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

    PubErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Gets enum.
     *
     * @param code the code
     * @return the enum
     */

}
