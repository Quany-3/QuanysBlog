package com.learnjava.quanysblog.module.article.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 点赞响应DTO
 *
 * @author Quany
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {

    /**
     * 当前点赞状态
     */
    private boolean liked;

    /**
     * 文章当前点赞数
     */
    private Integer likeCount;
}
