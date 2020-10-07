package com.utils.util.support;


import com.utils.util.PublicUtil;
import com.utils.util.ThreadLocalMap;
import com.utils.util.constant.GlobalConstant;
import com.utils.util.dto.AdminLoginAutoDto;
import com.utils.util.enums.ErrorCodeEnum;
import com.utils.util.exception.BizException;
import com.utils.util.dto.LoginAuthDto;
import com.utils.util.wrapper.WrapMapper;
import com.utils.util.wrapper.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected LoginAuthDto getLoginAuthDto() {
        // 获取TokenInterceptor解密完的对象
        LoginAuthDto loginAuthDto = (LoginAuthDto) ThreadLocalMap.get(GlobalConstant.Sys.TOKEN_AUTH_DTO);
        if (PublicUtil.isEmpty(loginAuthDto)) {
            throw new BizException(ErrorCodeEnum.UAC10011041);
        }
        return loginAuthDto;
    }

    protected AdminLoginAutoDto getAdminLoginAuthDto() {
        AdminLoginAutoDto loginAuthDto = (AdminLoginAutoDto) ThreadLocalMap.get(GlobalConstant.Sys.TOKEN_AUTH_DTO);
        if (PublicUtil.isEmpty(loginAuthDto)) {
            throw new BizException(ErrorCodeEnum.UAC10011041);
        }
        return loginAuthDto;
    }


    /**
     * Handle result wrapper.
     *
     * @param <T>    the type parameter
     * @param result the result
     * @return the wrapper
     */
    protected <T> Wrapper<T> handleResult(T result) {
        boolean flag = isFlag(result);

        if (flag) {
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", result);
        } else {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "操作失败", result);
        }
    }

    /**
     * Handle result wrapper.
     *
     * @param <E>      the type parameter
     * @param result   the result
     * @param errorMsg the error msg
     * @return the wrapper
     */
    protected <E> Wrapper<E> handleResult(E result, String errorMsg) {
        boolean flag = isFlag(result);

        if (flag) {
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", result);
        } else {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, errorMsg, result);
        }
    }

    private boolean isFlag(Object result) {
        boolean flag;
        if (result instanceof Integer) {
            flag = (Integer) result > 0;
        } else if (result instanceof Boolean) {
            flag = (Boolean) result;
        } else {
            flag = PublicUtil.isNotEmpty(result);
        }
        return flag;
    }
}
