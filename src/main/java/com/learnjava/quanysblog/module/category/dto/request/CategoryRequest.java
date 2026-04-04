package com.learnjava.quanysblog.module.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 分类创建/更新请求DTO
 *
 * @author Quany
 */
@Data
public class CategoryRequest {

    /**
     * 分类名称，长度不超过50字符
     */
    @NotBlank(message = "分类名称不能为空")
    @Size(max = 50, message = "分类名称不能超过50字符")
    private String name;

    /**
     * 分类别名（URL友好），用于URL路径
     */
    @NotBlank(message = "分类别名不能为空")
    @Size(max = 50, message = "分类别名不能超过50字符")
    private String slug;

    /**
     * 分类描述
     */
    @Size(max = 200, message = "分类描述不能超过200字符")
    private String description;

    /**
     * 排序权重，数字越小排序越靠前
     */
    private Integer sort = 0;
}
