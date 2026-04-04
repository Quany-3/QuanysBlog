package com.learnjava.quanysblog.module.comment.service.impl;

import com.learnjava.quanysblog.common.exception.BusinessException;
import com.learnjava.quanysblog.common.result.ResultCode;
import com.learnjava.quanysblog.module.article.entity.Article;
import com.learnjava.quanysblog.module.article.repository.ArticleRepository;
import com.learnjava.quanysblog.module.comment.dto.request.CommentRequest;
import com.learnjava.quanysblog.module.comment.dto.response.CommentResponse;
import com.learnjava.quanysblog.module.comment.entity.Comment;
import com.learnjava.quanysblog.module.comment.repository.CommentRepository;
import com.learnjava.quanysblog.module.comment.service.CommentService;
import com.learnjava.quanysblog.module.user.entity.User;
import com.learnjava.quanysblog.module.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 评论服务实现类
 * 处理评论相关的业务逻辑
 *
 * @author Quany
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    /**
     * 评论数据访问层
     */
    private final CommentRepository commentRepository;

    /**
     * 文章数据访问层
     */
    private final ArticleRepository articleRepository;

    /**
     * 用户数据访问层
     */
    private final UserRepository userRepository;

    /**
     * 创建评论
     */
    @Override
    @Transactional
    public CommentResponse createComment(CommentRequest request, Long authorId) {
        // 获取评论者
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND, "用户不存在"));

        // 获取文章
        Article article = articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new BusinessException(ResultCode.ARTICLE_NOT_FOUND, "文章不存在"));

        // 获取父评论（如果有）
        Comment parent = null;
        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new BusinessException(ResultCode.COMMENT_NOT_FOUND, "父评论不存在"));
        }

        // 构建评论实体
        Comment comment = Comment.builder()
                .content(request.getContent())
                .author(author)
                .article(article)
                .parent(parent)
                .status(Comment.Status.APPROVED) // 默认直接通过审核
                .build();

        comment = commentRepository.save(comment);

        // 更新文章的评论计数
        article.setCommentCount(article.getCommentCount() + 1);
        articleRepository.save(article);

        log.info("创建评论成功：{}，文章：{}，作者：{}", comment.getId(), article.getTitle(), author.getUsername());

        return toCommentResponse(comment);
    }

    /**
     * 获取文章的评论列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByArticle(Long articleId) {
        // 获取所有评论
        List<Comment> allComments = commentRepository
                .findByArticleIdAndStatusOrderByCreatedAtDesc(articleId, Comment.Status.APPROVED, Pageable.unpaged())
                .getContent();

        // 构建ID到评论的映射
        Map<Long, CommentResponse> commentMap = new HashMap<>();
        allComments.forEach(comment -> commentMap.put(comment.getId(), toCommentResponse(comment)));

        // 构建评论树
        List<CommentResponse> rootComments = new ArrayList<>();
        allComments.forEach(comment -> {
            CommentResponse response = commentMap.get(comment.getId());
            if (comment.getParent() == null) {
                rootComments.add(response);
            } else {
                CommentResponse parentResponse = commentMap.get(comment.getParent().getId());
                if (parentResponse != null) {
                    if (parentResponse.getChildren() == null) {
                        parentResponse.setChildren(new ArrayList<>());
                    }
                    parentResponse.getChildren().add(response);
                }
            }
        });

        return rootComments;
    }

    /**
     * 删除评论
     */
    @Override
    @Transactional
    public void deleteComment(Long id, Long authorId) {
        // 获取评论
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.COMMENT_NOT_FOUND, "评论不存在"));

        // 校验权限：只有评论作者才能删除
        if (!comment.getAuthor().getId().equals(authorId)) {
            throw new BusinessException(ResultCode.FORBIDDEN, "没有权限删除此评论");
        }

        // 更新文章的评论计数
        Article article = comment.getArticle();
        if (article.getCommentCount() > 0) {
            article.setCommentCount(article.getCommentCount() - 1);
            articleRepository.save(article);
        }

        commentRepository.delete(comment);
        log.info("删除评论成功：{}", id);
    }

    /**
     * 获取所有评论（管理员用）
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CommentResponse> getAllComments(Pageable pageable) {
        Page<Comment> comments = commentRepository.findByStatusOrderByCreatedAtDesc(Comment.Status.APPROVED, pageable);
        return comments.map(this::toCommentResponse);
    }

    /**
     * 管理员删除评论
     */
    @Override
    @Transactional
    public void deleteCommentByAdmin(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.COMMENT_NOT_FOUND, "评论不存在"));

        // 更新文章的评论计数
        Article article = comment.getArticle();
        if (article.getCommentCount() > 0) {
            article.setCommentCount(article.getCommentCount() - 1);
            articleRepository.save(article);
        }

        commentRepository.delete(comment);
        log.info("管理员删除评论成功：{}", id);
    }

    /**
     * 将 Comment 实体转换为 CommentResponse DTO
     */
    private CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorUsername(comment.getAuthor().getUsername())
                .authorAvatar(comment.getAuthor().getAvatar())
                .articleId(comment.getArticle().getId())
                .articleTitle(comment.getArticle().getTitle())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .status(comment.getStatus().name())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .children(new ArrayList<>())
                .build();
    }
}
