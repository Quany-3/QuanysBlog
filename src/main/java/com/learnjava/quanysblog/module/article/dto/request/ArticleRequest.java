package com.learnjava.quanysblog.module.article.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

/**
 * 文章创建/更新请求DTO
 * 用于接收文章创建和更新时的请求数据
 *
 * @author Quany
 */
@Data
public class ArticleRequest {

    /**
     * 文章标题，长度不超过200字符
     */
    @NotBlank(message = "文章标题不能为空")
    @Size(max = 200, message = "文章标题不能超过200字符")
    private String title;

    /**
     * 文章内容（Markdown格式）
     */
    @NotBlank(message = "文章内容不能为空")
    private String content;

    /**
     * 文章摘要，用于列表展示
     */
    @Size(max = 500, message = "文章摘要不能超过500字符")
    private String summary;

    /**
     * 封面图片URL
     */
    private String coverImage;

    /**
     * 分类ID，可为空
     */
    private Long categoryId;

    /**
     * 标签ID集合
     */
    private Set<Long> tagIds;

    /**
     * 是否为精选文章，默认false
     */
    private Boolean isFeatured = false;

    /**
     * 是否发布，默认true（发布）
     */
    private Boolean isPublished = true;
}
