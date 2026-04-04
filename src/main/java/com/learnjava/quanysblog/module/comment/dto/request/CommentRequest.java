package com.learnjava.quanysblog.module.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 评论创建/更新请求DTO
 *
 * @author Quany
 */
@Data
public class CommentRequest {

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    private String content;

    /**
     * 文章ID
     */
    @NotNull(message = "文章ID不能为空")
    private Long articleId;

    /**
     * 父评论ID（用于回复），可为空
     */
    private Long parentId;
}
