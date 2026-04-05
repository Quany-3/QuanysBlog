package com.learnjava.quanysblog.module.article.service;

import com.learnjava.quanysblog.module.article.dto.request.ArticleRequest;
import com.learnjava.quanysblog.module.article.dto.response.ArticleResponse;
import com.learnjava.quanysblog.module.article.dto.response.LikeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 文章服务接口
 * 定义文章相关的业务逻辑
 *
 * @author Quany
 */
public interface ArticleService {

    /**
     * 创建文章
     *
     * @param request 文章创建请求
     * @param authorId 作者ID
     * @return 创建的文章响应
     */
    ArticleResponse createArticle(ArticleRequest request, Long authorId);

    /**
     * 更新文章
     *
     * @param id 文章ID
     * @param request 文章更新请求
     * @param authorId 作者ID（用于权限校验）
     * @return 更新后的文章响应
     */
    ArticleResponse updateArticle(Long id, ArticleRequest request, Long authorId);

    /**
     * 删除文章
     *
     * @param id 文章ID
     * @param authorId 作者ID（用于权限校验）
     */
    void deleteArticle(Long id, Long authorId);

    /**
     * 获取文章详情
     *
     * @param id 文章ID
     * @return 文章响应
     */
    ArticleResponse getArticleById(Long id);

    /**
     * 分页查询文章列表
     *
     * @param pageable 分页参数
     * @return 文章分页结果
     */
    Page<ArticleResponse> getArticlesByPage(Pageable pageable);

    /**
     * 按分类查询文章
     *
     * @param categoryId 分类ID
     * @param pageable 分页参数
     * @return 文章分页结果
     */
    Page<ArticleResponse> getArticlesByCategory(Long categoryId, Pageable pageable);

    /**
     * 按标签查询文章
     *
     * @param tagId 标签ID
     * @param pageable 分页参数
     * @return 文章分页结果
     */
    Page<ArticleResponse> getArticlesByTag(Long tagId, Pageable pageable);

    /**
     * 搜索文章
     *
     * @param keyword 搜索关键词
     * @param pageable 分页参数
     * @return 文章分页结果
     */
    Page<ArticleResponse> searchArticles(String keyword, Pageable pageable);

    /**
     * 增加文章浏览量
     *
     * @param id 文章ID
     */
    void incrementViewCount(Long id);

    /**
     * 点赞文章
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 点赞结果
     */
    LikeResponse likeArticle(Long articleId, Long userId);

    /**
     * 取消点赞文章
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 取消点赞结果
     */
    LikeResponse unlikeArticle(Long articleId, Long userId);

    /**
     * 检查用户是否点赞了文章
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 是否点赞
     */
    boolean hasLiked(Long articleId, Long userId);

    /**
     * 获取点赞状态
     *
     * @param articleId 文章ID
     * @param userId 用户ID
     * @return 点赞状态和点赞数
     */
    LikeResponse getLikeStatus(Long articleId, Long userId);
}
