package com.utils.util.support;

import com.utils.util.enums.ErrorCodeEnum;
import com.utils.util.exception.BizException;
import com.utils.util.wrapper.WrapMapper;
import com.utils.util.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private TaskExecutor taskExecutor;
    @Value("${spring.profiles.active}")
    String profile;
    @Value("${spring.application.name}")
    String applicationName;


    /**
     * 参数非法异常.
     *
     * @param e the e
     * @return the wrapper
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper illegalArgumentException(IllegalArgumentException e) {
        log.error("参数非法异常={}", e.getMessage(), e);
        return WrapMapper.wrap(ErrorCodeEnum.GL99990100.code(), e.getMessage());
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Wrapper NoHandlerFoundExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("访问路径不存在={}", e.getMessage(), e);
        return WrapMapper.wrap(ErrorCodeEnum.GL99990401.code(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper MethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.error("请求：{}发生异常：{}", req.getRequestURI(), e);

        // 错误信息
//        StringBuilder sb = new StringBuilder("参数校验失败：");
        // 错误信息map
//        Map<String, String> error = new HashMap<>();

        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
//        List<ObjectError> allErrors = null;
//        allErrors = e.getBindingResult().getAllErrors().get(0);
        // 拼接错误信息
//        for (ObjectError oe : allErrors) {
//            msg = oe.getDefaultMessage();
//            sb.append(msg).append("；");
//            if (oe instanceof FieldError) {
//                error.put(((FieldError)oe).getField(), msg);
//            } else {
//                error.put(oe.getObjectName(), msg);
//            }
        return WrapMapper.wrap(ErrorCodeEnum.GL99990100.code(), msg);
//        }
//        log.error("参数校验异常={}", e.getMessage(), e);
//        return WrapMapper.wrap(ErrorCodeEnum.GL99990100.code(), ErrorCodeEnum.GL99990100.msg());
    }

    /**
     * 业务异常.
     *
     * @param e the e
     * @return the wrapper
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Wrapper businessException(BizException e) {
        log.error("业务异常={}", e.getMessage(), e);
        return WrapMapper.wrap(e.getCode() == 0 ? Wrapper.ERROR_CODE : e.getCode(), e.getMessage());
    }


    /**
     * 全局异常.
     *
     * @param e the e
     * @return the wrapper
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Wrapper exception(Exception e) {
        log.info("保存全局异常信息 ex={}", e.getMessage(), e);
        taskExecutor.execute(() -> {
            log.info("---------->>>>>>>>>>>");
        });
        return WrapMapper.error();
    }
}
