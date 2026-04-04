package com.learnjava.quanysblog.common.exception;

import com.learnjava.quanysblog.common.result.ResultCode;
import lombok.Getter;

/**
 * 业务异常类
 * 用于处理业务逻辑中的异常情况
 *
 * @author Quany
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误状态码
     */
    private final Integer code;

    /**
     * 构造器 - 使用 ResultCode 枚举
     *
     * @param resultCode 响应状态码枚举
     */
    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    /**
     * 构造器 - 使用 ResultCode 枚举和自定义消息
     *
     * @param resultCode 响应状态码枚举
     * @param message 自定义错误消息
     */
    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }

    /**
     * 构造器 - 使用自定义状态码和消息
     *
     * @param code 错误状态码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
