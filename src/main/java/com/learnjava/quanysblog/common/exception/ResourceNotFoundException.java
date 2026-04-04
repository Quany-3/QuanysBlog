package com.learnjava.quanysblog.common.exception;

import com.learnjava.quanysblog.common.result.ResultCode;
import lombok.Getter;

/**
 * 资源不存在异常类
 * 当请求的资源（用户、文章、分类等）不存在时抛出
 *
 * @author Quany
 */
@Getter
public class ResourceNotFoundException extends BusinessException {

    /**
     * 资源名称
     */
    private final String resourceName;

    /**
     * 资源ID
     */
    private final Object resourceId;

    /**
     * 构造器 - 使用 ResultCode 和资源信息
     *
     * @param resultCode 响应状态码枚举
     * @param resourceName 资源名称（如：User, Article）
     * @param resourceId 资源ID
     */
    public ResourceNotFoundException(ResultCode resultCode, String resourceName, Object resourceId) {
        super(resultCode, String.format("%s不存在，ID: %s", resourceName, resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    /**
     * 构造器 - 使用资源名称和ID（默认使用 NOT_FOUND 状态码）
     *
     * @param resourceName 资源名称
     * @param resourceId 资源ID
     */
    public ResourceNotFoundException(String resourceName, Object resourceId) {
        super(ResultCode.NOT_FOUND, String.format("%s不存在，ID: %s", resourceName, resourceId));
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }
}
