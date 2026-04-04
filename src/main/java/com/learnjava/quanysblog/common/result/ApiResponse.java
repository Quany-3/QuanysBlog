package com.learnjava.quanysblog.common.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用API响应封装类
 * 统一所有REST API的响应格式
 *
 * @param <T> 响应数据的类型
 * @author Quany
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    /**
     * 请求是否成功
     */
    private boolean success;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 创建成功响应（仅数据）
     *
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功的响应对象
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(ResultCode.SUCCESS.getCode())
                .data(data)
                .build();
    }

    /**
     * 创建成功响应（带消息）
     *
     * @param message 成功消息
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功的响应对象
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(ResultCode.SUCCESS.getCode())
                .message(message)
                .data(data)
                .build();
    }

    /**
     * 创建成功响应（仅消息）
     *
     * @param message 成功消息
     * @param <T> 数据类型
     * @return 成功的响应对象
     */
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(ResultCode.SUCCESS.getCode())
                .message(message)
                .build();
    }

    /**
     * 创建错误响应
     *
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 失败的响应对象
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .code(ResultCode.ERROR.getCode())
                .message(message)
                .build();
    }

    /**
     * 创建错误响应（带状态码）
     *
     * @param resultCode 响应状态码枚举
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 失败的响应对象
     */
    public static <T> ApiResponse<T> error(ResultCode resultCode, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .code(resultCode.getCode())
                .message(message)
                .build();
    }

    /**
     * 创建错误响应（使用 ResultCode 枚举）
     *
     * @param resultCode 响应状态码枚举
     * @param <T> 数据类型
     * @return 失败的响应对象
     */
    public static <T> ApiResponse<T> error(ResultCode resultCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .code(resultCode.getCode())
                .message(resultCode.getMessage())
                .build();
    }
}
