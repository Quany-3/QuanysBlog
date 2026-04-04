package com.learnjava.quanysblog.module.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录请求DTO
 * 用于接收用户登录时的请求数据
 *
 * @author Quany
 */
@Data
public class LoginRequest {

    /**
     * 用户名，不能为空
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码，不能为空
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
