package com.learnjava.quanysblog.module.tag.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 标签创建/更新请求DTO
 *
 * @author Quany
 */
@Data
public class TagRequest {

    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空")
    @Size(max = 50, message = "标签名称不能超过50字符")
    private String name;

    /**
     * 标签别名（URL友好）
     */
    @NotBlank(message = "标签别名不能为空")
    @Size(max = 50, message = "标签别名不能超过50字符")
    private String slug;

    /**
     * 标签颜色
     */
    @Size(max = 20, message = "标签颜色不能超过20字符")
    private String color = "#1890ff";
}
