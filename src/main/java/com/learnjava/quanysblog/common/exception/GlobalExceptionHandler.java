package com.learnjava.quanysblog.common.exception;

import com.learnjava.quanysblog.common.result.ApiResponse;
import com.learnjava.quanysblog.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * 统一处理项目中所有控制器抛出的异常，返回统一的错误响应格式
 *
 * @author Quany
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数验证异常
     * 当请求参数不符合 @Valid 注解的约束时触发
     *
     * @param ex 参数验证异常
     * @return 400 错误响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        // 收集所有字段验证错误
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            // 获取字段名和错误消息
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        // 返回验证失败的响应
        log.warn("参数验证失败: {}", errors);
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(ResultCode.VALIDATION_ERROR, "参数验证失败"));
    }

    /**
     * 处理认证失败异常
     * 用户名或密码错误时触发
     *
     * @param ex 认证异常
     * @return 401 未授权响应
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        log.warn("认证失败: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(ResultCode.USERNAME_OR_PASSWORD_ERROR));
    }

    /**
     * 处理用户不存在异常
     * 当根据用户名查找用户但用户不存在时触发
     *
     * @param ex 用户不存在异常
     * @return 404 未找到响应
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUsernameNotFound(UsernameNotFoundException ex) {
        log.warn("用户不存在: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ResultCode.USER_NOT_FOUND, ex.getMessage()));
    }

    /**
     * 处理业务异常
     * 自定义业务逻辑异常
     *
     * @param ex 业务异常
     * @return 业务错误响应
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        log.warn("业务异常: {}", ex.getMessage());
        // 根据异常码判断 HTTP 状态码
        HttpStatus status = mapToHttpStatus(ex.getCode());
        return ResponseEntity.status(status)
                .body(ApiResponse.error(ResultCode.getByCode(ex.getCode()), ex.getMessage()));
    }

    /**
     * 处理非法参数异常
     * 当业务逻辑检测到非法参数时触发
     *
     * @param ex 非法参数异常
     * @return 400 错误请求响应
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("非法参数: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(ResultCode.BAD_REQUEST, ex.getMessage()));
    }

    /**
     * 处理运行时异常
     * 捕获所有未处理的运行时异常
     *
     * @param ex 运行时异常
     * @return 500 服务器内部错误响应
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
        log.error("运行时异常: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ResultCode.INTERNAL_SERVER_ERROR, "服务器内部错误"));
    }

    /**
     * 处理所有未捕获的异常
     * 最后的防线
     *
     * @param ex 异常
     * @return 500 服务器内部错误响应
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("未处理的异常: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(ResultCode.INTERNAL_SERVER_ERROR, "服务器内部错误"));
    }

    /**
     * 根据业务状态码映射到 HTTP 状态码
     *
     * @param code 业务状态码
     * @return HTTP 状态码
     */
    private HttpStatus mapToHttpStatus(Integer code) {
        if (code == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        // 认证相关错误 2xxx (但 2001/2002 是用户名/邮箱已存在，不算认证错误)
        if (code == ResultCode.USERNAME_OR_PASSWORD_ERROR.getCode()) {
            return HttpStatus.UNAUTHORIZED;
        }
        // 资源不存在 3xxx
        if (code >= 3000 && code < 4000) {
            return HttpStatus.NOT_FOUND;
        }
        // 参数验证失败
        if (code == ResultCode.VALIDATION_ERROR.getCode() || code == ResultCode.DUPLICATE_ERROR.getCode()) {
            return HttpStatus.BAD_REQUEST;
        }
        // 客户端错误
        if (code >= 1000 && code < 2000) {
            return HttpStatus.BAD_REQUEST;
        }
        if (code >= 5000) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
