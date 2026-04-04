package com.learnjava.quanysblog.module.comment.repository;

import com.learnjava.quanysblog.module.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论数据访问层
 * 提供评论相关的数据库操作方法
 *
 * @author Quany
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 获取指定文章的评论列表（分页），按创建时间倒序
     *
     * @param articleId 文章ID
     * @param status 评论状态
     * @param pageable 分页参数
     * @return 评论分页结果
     */
    Page<Comment> findByArticleIdAndStatusOrderByCreatedAtDesc(Long articleId, Comment.Status status, Pageable pageable);

    /**
     * 获取指定文章的顶级评论（无父评论），按创建时间倒序
     * 用于展示文章的一级评论及其回复
     *
     * @param articleId 文章ID
     * @param status 评论状态
     * @return 顶级评论列表
     */
    List<Comment> findByArticleIdAndParentIsNullAndStatusOrderByCreatedAtDesc(Long articleId, Comment.Status status);

    /**
     * 获取指定用户的评论列表（分页），按创建时间倒序
     *
     * @param authorId 用户ID
     * @param pageable 分页参数
     * @return 评论分页结果
     */
    Page<Comment> findByAuthorIdOrderByCreatedAtDesc(Long authorId, Pageable pageable);

    /**
     * 获取指定状态的所有评论（分页），按创建时间倒序
     * 用于后台管理审核评论
     *
     * @param status 评论状态
     * @param pageable 分页参数
     * @return 评论分页结果
     */
    Page<Comment> findByStatusOrderByCreatedAtDesc(Comment.Status status, Pageable pageable);
}
