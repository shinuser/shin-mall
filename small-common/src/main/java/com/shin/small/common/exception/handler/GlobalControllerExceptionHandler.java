package com.shin.small.common.exception.handler;

import com.shin.small.common.exception.SmallServiceException;
import com.shin.small.common.web.JsonResult;
import com.shin.small.common.web.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {
    /**
     * 处理业务异常
     */
    @ExceptionHandler({SmallServiceException.class})
    public JsonResult<Void> handleCoolSharkServiceException(SmallServiceException e) {
        log.debug("出现业务异常，业务错误码={}，描述文本={}", e.getState().getValue(), e.getMessage());
        e.printStackTrace();
        JsonResult<Void> result = JsonResult.failed(e);
        log.debug("即将返回：{}", result);
        return result;
    }

    /**
     * 处理绑定异常（通过Validation框架验证请求参数时的异常）
     */
    @ExceptionHandler(BindException.class)
    public JsonResult<Void> handleBindException(BindException e) {
        log.debug("验证请求数据时出现异常：{}", e.getClass().getName());
        e.printStackTrace();
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        JsonResult<Void> result = JsonResult.failed(State.ERR_BAD_REQUEST, message);
        log.debug("即将返回：{}", result);
        return result;

//        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//        StringBuilder stringBuilder = new StringBuilder();
//        for (FieldError fieldError : fieldErrors) {
//            stringBuilder.append("; ");
//            stringBuilder.append(fieldError.getDefaultMessage());
//        }
//        String message = stringBuilder.substring(1);
//        return JsonResult.failed(State.ERR_BAD_REQUEST, message);

//        String defaultMessage = e.getBindingResult().getFieldError().getDefaultMessage();
//        return JsonResult.failed(State.ERR_BAD_REQUEST, defaultMessage);
    }

    /**
     * 处理系统（其它）异常
     */
    @ExceptionHandler({Throwable.class})
    public JsonResult<Void> handleSystemError(Throwable e) {
        log.debug("出现系统异常，异常类型={}，描述文本={}", e.getClass().getName(), e.getMessage());
        e.printStackTrace();
        JsonResult<Void> result = JsonResult.failed(State.ERR_INTERNAL_SERVER_ERROR, e);
        log.debug("即将返回：{}", result);
        return result;
    }
}
