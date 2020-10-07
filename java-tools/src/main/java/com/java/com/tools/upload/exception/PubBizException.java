package com.java.com.tools.upload.exception;

import com.java.com.tools.upload.enums.PubErrorCodeEnum;
import com.utils.util.exception.BizException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PubBizException extends BizException {

    public PubBizException() {

    }

    public PubBizException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
        log.info("<== PubBizException, code:{}, message:{}", this.code, super.getMessage());
    }


    public PubBizException(int code, String msg) {
        super(code, msg);
        log.info("<== PubBizException, code:{}, message:{}", this.code, super.getMessage());
    }

    public PubBizException(PubErrorCodeEnum codeEnum) {
        super(codeEnum.code(), codeEnum.msg());
        log.info("<== PubBizException, code:{}, message:{}", this.code, super.getMessage());
    }
}
