package com.learnjava.quanysblog.module.comment.service;

import com.learnjava.quanysblog.module.comment.dto.request.CommentRequest;
import com.learnjava.quanysblog.module.comment.dto.response.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 评论服务接口
 * 定义评论相关的业务逻辑
 *
 * @author Quany
 */
public interface CommentService {

    /**
     * 创建评论
     *
     * @param request 评论创建请求
     * @param authorId 评论作者ID
     * @return 创建的评论响应
     */
    CommentResponse createComment(CommentRequest request, Long authorId);

    /**
     * 获取文章的评论列表
     *
     * @param articleId 文章ID
     * @return 评论列表（包含嵌套回复）
     */
    List<CommentResponse> getCommentsByArticle(Long articleId);

    /**
     * 删除评论
     *
     * @param id 评论ID
     * @param authorId 当前用户ID（用于权限校验）
     */
    void deleteComment(Long id, Long authorId);

    /**
     * 获取所有评论（管理员用）
     *
     * @param pageable 分页参数
     * @return 评论分页结果
     */
    Page<CommentResponse> getAllComments(Pageable pageable);

    /**
     * 管理员删除评论
     *
     * @param id 评论ID
     */
    void deleteCommentByAdmin(Long id);
}
