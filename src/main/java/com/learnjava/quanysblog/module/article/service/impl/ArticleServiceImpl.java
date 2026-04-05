package com.learnjava.quanysblog.module.article.service.impl;

import com.learnjava.quanysblog.common.exception.BusinessException;
import com.learnjava.quanysblog.common.result.ResultCode;
import com.learnjava.quanysblog.module.article.dto.request.ArticleRequest;
import com.learnjava.quanysblog.module.article.dto.response.ArticleResponse;
import com.learnjava.quanysblog.module.article.dto.response.LikeResponse;
import com.learnjava.quanysblog.module.article.entity.Article;
import com.learnjava.quanysblog.module.article.entity.ArticleLike;
import com.learnjava.quanysblog.module.article.entity.Category;
import com.learnjava.quanysblog.module.article.entity.Tag;
import com.learnjava.quanysblog.module.article.repository.ArticleLikeRepository;
import com.learnjava.quanysblog.module.article.repository.ArticleRepository;
import com.learnjava.quanysblog.module.article.repository.CategoryRepository;
import com.learnjava.quanysblog.module.article.repository.TagRepository;
import com.learnjava.quanysblog.module.article.service.ArticleService;
import com.learnjava.quanysblog.module.user.entity.User;
import com.learnjava.quanysblog.module.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文章服务实现类
 * 处理文章相关的业务逻辑
 *
 * @author Quany
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    /**
     * 文章数据访问层
     */
    private final ArticleRepository articleRepository;

    /**
     * 点赞数据访问层
     */
    private final ArticleLikeRepository articleLikeRepository;

    /**
     * 用户数据访问层
     */
    private final UserRepository userRepository;

    /**
     * 分类数据访问层
     */
    private final CategoryRepository categoryRepository;

    /**
     * 标签数据访问层
     */
    private final TagRepository tagRepository;

    /**
* 创建文章
     */
    @Override
    @Transactional
    public ArticleResponse createArticle(ArticleRequest request, Long authorId) {
        // 获取作者
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new BusinessException(ResultCode.USER_NOT_FOUND, "用户不存在"));

        // 获取分类（如果指定）
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new BusinessException(ResultCode.CATEGORY_NOT_FOUND, "分类不存在"));
        }

        // 获取标签
        Set<Tag> tags = new HashSet<>();
        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            tags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));
        }

        // 构建文章实体
        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .summary(request.getSummary())
                .coverImage(request.getCoverImage())
                .author(author)
                .category(category)
                .tags(tags)
                .isFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false)
                .isPublished(request.getIsPublished() != null ? request.getIsPublished() : true)
                .publishedAt(request.getIsPublished() != null && request.getIsPublished() ? LocalDateTime.now() : null)
                .viewCount(0)
                .likeCount(0)
                .commentCount(0)
                .build();

        article = articleRepository.save(article);
        log.info("创建文章成功：{}，作者：{}", article.getId(), author.getUsername());

        return toArticleResponse(article);
    }

    /**
     * 更新文章
     */
    @Override
    @Transactional
    public ArticleResponse updateArticle(Long id, ArticleRequest request, Long authorId) {
        // 获取文章
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.ARTICLE_NOT_FOUND, "文章不存在"));

        // 校验权限：只有作者才能修改
        if (!article.getAuthor().getId().equals(authorId)) {
            throw new BusinessException(ResultCode.FORBIDDEN, "没有权限修改此文章");
        }

        // 更新分类（如果指定）
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new BusinessException(ResultCode.CATEGORY_NOT_FOUND, "分类不存在"));
            article.setCategory(category);
        }

        // 更新标签
        if (request.getTagIds() != null) {
            Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.getTagIds()));
            article.setTags(tags);
        }

        // 更新其他字段
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setSummary(request.getSummary());
        article.setCoverImage(request.getCoverImage());

        if (request.getIsFeatured() != null) {
            article.setIsFeatured(request.getIsFeatured());
        }

        // 处理发布状态变更
        if (request.getIsPublished() != null && request.getIsPublished()) {
            if (article.getPublishedAt() == null) {
                article.setPublishedAt(LocalDateTime.now());
            }
            article.setIsPublished(true);
        } else if (request.getIsPublished() != null) {
            article.setIsPublished(false);
        }

        article = articleRepository.save(article);
        log.info("更新文章成功：{}", article.getId());

        return toArticleResponse(article);
    }

    /**
     * 删除文章
     */
    @Override
    @Transactional
    public void deleteArticle(Long id, Long authorId) {
        // 获取文章
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.ARTICLE_NOT_FOUND, "文章不存在"));

        // 校验权限：只有作者才能删除
        if (!article.getAuthor().getId().equals(authorId)) {
            throw new BusinessException(ResultCode.FORBIDDEN, "没有权限删除此文章");
        }

        articleRepository.delete(article);
        log.info("删除文章成功：{}", id);
    }

    /**
     * 获取文章详情
     */
    @Override
    @Transactional(readOnly = true)
    public ArticleResponse getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.ARTICLE_NOT_FOUND, "文章不存在"));
        return toArticleResponse(article);
    }

    /**
     * 增加文章浏览量
     */
    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.ARTICLE_NOT_FOUND, "文章不存在"));
        article.setViewCount(article.getViewCount() + 1);
        articleRepository.save(article);
    }

    /**
     * 点赞文章
     */
    @Override
    @Transactional
    public LikeResponse likeArticle(Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new BusinessException(ResultCode.ARTICLE_NOT_FOUND, "文章不存在"));

        // 检查是否已点赞
        if (articleLikeRepository.existsByUserIdAndArticleId(userId, articleId)) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "已经点赞过了");
        }

        // 使用代理对象，避免查询用户表
        User userRef = User.builder().id(userId).build();

        // 创建点赞记录
        ArticleLike like = ArticleLike.builder()
                .user(userRef)
                .article(article)
                .build();
        articleLikeRepository.save(like);

        // 更新点赞数
        article.setLikeCount(article.getLikeCount() + 1);
        articleRepository.save(article);

        log.info("用户 {} 点赞了文章 {}", userId, articleId);

        return LikeResponse.builder()
                .liked(true)
                .likeCount(article.getLikeCount())
                .build();
    }

    /**
     * 取消点赞文章
     */
    @Override
    @Transactional
    public LikeResponse unlikeArticle(Long articleId, Long userId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new BusinessException(ResultCode.ARTICLE_NOT_FOUND, "文章不存在"));

        // 检查是否已点赞
        if (!articleLikeRepository.existsByUserIdAndArticleId(userId, articleId)) {
            throw new BusinessException(ResultCode.BAD_REQUEST, "还没有点赞");
        }

        // 删除点赞记录
        articleLikeRepository.deleteByUserIdAndArticleId(userId, articleId);

        // 更新点赞数
        article.setLikeCount(Math.max(0, article.getLikeCount() - 1));
        articleRepository.save(article);

        log.info("用户取消点赞了文章 {}", articleId);

        return LikeResponse.builder()
                .liked(false)
                .likeCount(article.getLikeCount())
                .build();
    }

    /**
     * 检查用户是否点赞了文章
     */
    @Override
    @Transactional(readOnly = true)
    public boolean hasLiked(Long articleId, Long userId) {
        return articleLikeRepository.existsByUserIdAndArticleId(userId, articleId);
    }

    /**
     * 获取点赞状态
     */
    @Override
    @Transactional(readOnly = true)
    public LikeResponse getLikeStatus(Long articleId, Long userId) {
        Integer likeCount = articleRepository.getLikeCountById(articleId);
        boolean liked = articleLikeRepository.existsByUserIdAndArticleId(userId, articleId);
        return LikeResponse.builder()
                .liked(liked)
                .likeCount(likeCount != null ? likeCount : 0)
                .build();
    }

    /**
     * 分页查询文章列表
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResponse> getArticlesByPage(Pageable pageable) {
        Page<Article> articles = articleRepository.findByIsPublishedTrueOrderByPublishedAtDesc(pageable);
        return articles.map(this::toArticleResponse);
    }

    /**
     * 按分类查询文章
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResponse> getArticlesByCategory(Long categoryId, Pageable pageable) {
        // 校验分类是否存在
        if (!categoryRepository.existsById(categoryId)) {
            throw new BusinessException(ResultCode.CATEGORY_NOT_FOUND, "分类不存在");
        }
        Page<Article> articles = articleRepository.findByCategoryIdAndIsPublishedTrueOrderByPublishedAtDesc(categoryId, pageable);
        return articles.map(this::toArticleResponse);
    }

    /**
     * 按标签查询文章
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResponse> getArticlesByTag(Long tagId, Pageable pageable) {
        // 校验标签是否存在
        if (!tagRepository.existsById(tagId)) {
            throw new BusinessException(ResultCode.TAG_NOT_FOUND, "标签不存在");
        }
        Page<Article> articles = articleRepository.findByTagIdAndIsPublishedTrue(tagId, pageable);
        return articles.map(this::toArticleResponse);
    }

    /**
     * 搜索文章
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResponse> searchArticles(String keyword, Pageable pageable) {
        Page<Article> articles = articleRepository.searchByKeyword(keyword, pageable);
        return articles.map(this::toArticleResponse);
    }

    /**
     * 将 Article 实体转换为 ArticleResponse DTO
     */
    private ArticleResponse toArticleResponse(Article article) {
        // 构建标签响应列表
        Set<ArticleResponse.TagResponse> tagResponses = null;
        if (article.getTags() != null && !article.getTags().isEmpty()) {
            tagResponses = article.getTags().stream()
                    .map(tag -> ArticleResponse.TagResponse.builder()
                            .id(tag.getId())
                            .name(tag.getName())
                            .slug(tag.getSlug())
                            .color(tag.getColor())
                            .build())
                    .collect(Collectors.toSet());
        }

        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .summary(article.getSummary())
                .coverImage(article.getCoverImage())
                .authorUsername(article.getAuthor().getUsername())
                .authorAvatar(article.getAuthor().getAvatar())
                .categoryId(article.getCategory() != null ? article.getCategory().getId() : null)
                .categoryName(article.getCategory() != null ? article.getCategory().getName() : null)
                .tags(tagResponses)
                .viewCount(article.getViewCount())
                .likeCount(article.getLikeCount())
                .commentCount(article.getCommentCount())
                .isFeatured(article.getIsFeatured())
                .isPublished(article.getIsPublished())
                .publishedAt(article.getPublishedAt())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }
}
