package com.learnjava.quanysblog.module.article.repository;

import com.learnjava.quanysblog.module.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章数据访问层
 * 提供文章相关的数据库操作方法
 *
 * @author Quany
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    /**
     * 获取已发布的文章列表，按发布时间倒序分页
     *
     * @param pageable 分页参数
     * @return 文章分页结果
     */
    Page<Article> findByIsPublishedTrueOrderByPublishedAtDesc(Pageable pageable);

    /**
     * 获取指定作者已发布的文章列表，按发布时间倒序分页
     *
     * @param authorId 作者ID
     * @param pageable 分页参数
     * @return 文章分页结果
     */
    Page<Article> findByAuthorIdAndIsPublishedTrueOrderByPublishedAtDesc(Long authorId, Pageable pageable);

    /**
     * 获取指定分类已发布的文章列表，按发布时间倒序分页
     *
     * @param categoryId 分类ID
     * @param pageable 分页参数
     * @return 文章分页结果
     */
    Page<Article> findByCategoryIdAndIsPublishedTrueOrderByPublishedAtDesc(Long categoryId, Pageable pageable);

    /**
     * 获取精选文章列表，按发布时间倒序
     *
     * @param pageable 分页参数
     * @return 精选文章列表
     */
    List<Article> findByIsFeaturedTrueAndIsPublishedTrueOrderByPublishedAtDesc(Pageable pageable);

    /**
     * 根据标签ID获取已发布的文章列表，按发布时间倒序
     * 使用JPQL自定义查询，通过标签ID关联查询文章
     *
     * @param tagId 标签ID
     * @param pageable 分页参数
     * @return 文章分页结果
     */
    @Query("SELECT a FROM Article a JOIN a.tags t WHERE t.id = :tagId AND a.isPublished = true ORDER BY a.publishedAt DESC")
    Page<Article> findByTagIdAndIsPublishedTrue(@Param("tagId") Long tagId, Pageable pageable);

    /**
     * 根据关键词搜索已发布的文章
     * 搜索范围：标题和内容，按发布时间倒序
     *
     * @param keyword 搜索关键词
     * @param pageable 分页参数
     * @return 文章分页结果
     */
    @Query("SELECT a FROM Article a WHERE a.isPublished = true AND (a.title LIKE %:keyword% OR a.content LIKE %:keyword%) ORDER BY a.publishedAt DESC")
    Page<Article> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
