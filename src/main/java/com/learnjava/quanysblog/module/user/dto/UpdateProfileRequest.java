package com.learnjava.quanysblog.module.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户资料更新请求 DTO
 *
 * @author Quany
 */
@Data
public class UpdateProfileRequest {

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 个人简介
     */
    @Size(max = 500, message = "个人简介不能超过 500 字符")
    private String bio;

    /**
     * 头像 URL
     */
    @Size(max = 500, message = "头像 URL 长度不能超过 500")
    private String avatar;
}
