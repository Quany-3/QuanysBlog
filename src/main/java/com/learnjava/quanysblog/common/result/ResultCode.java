package com.learnjava.quanysblog.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码枚举
 * 定义系统常用的响应状态
 *
 * @author Quany
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // 成功状态码
    SUCCESS(200, "操作成功"),

    // 客户端错误状态码 4xx
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未登录或登录已过期"),
    FORBIDDEN(403, "没有权限访问该资源"),
    NOT_FOUND(404, "请求的资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),

    // 服务器错误状态码 5xx
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务暂不可用"),

    // 业务错误状态码 1xxx
    ERROR(1000, "操作失败"),
    VALIDATION_ERROR(1001, "数据校验失败"),
    DUPLICATE_ERROR(1002, "数据已存在"),

    // 认证错误状态码 2xxx
    USERNAME_EXISTS(2001, "用户名已存在"),
    EMAIL_EXISTS(2002, "邮箱已被注册"),
    USERNAME_OR_PASSWORD_ERROR(2003, "用户名或密码错误"),

    // 资源错误状态码 3xxx
    USER_NOT_FOUND(3001, "用户不存在"),
    ARTICLE_NOT_FOUND(3002, "文章不存在"),
    CATEGORY_NOT_FOUND(3003, "分类不存在"),
    TAG_NOT_FOUND(3004, "标签不存在"),
    COMMENT_NOT_FOUND(3005, "评论不存在");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态消息
     */
    private final String message;

    /**
     * 根据状态码获取 ResultCode 枚举
     *
     * @param code 状态码
     * @return 对应的 ResultCode 枚举，如果未找到返回 ERROR
     */
    public static ResultCode getByCode(Integer code) {
        if (code == null) {
            return ERROR;
        }
        for (ResultCode resultCode : values()) {
            if (resultCode.code.equals(code)) {
                return resultCode;
            }
        }
        return ERROR;
    }
}
