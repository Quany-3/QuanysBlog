package com.learnjava.quanysblog.module.article.repository;

import com.learnjava.quanysblog.module.article.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 文章点赞数据访问层
 *
 * @author Quany
 */
@Repository
public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

    /**
     * 查询指定用户是否点赞了指定文章
     *
     * @param userId     用户ID
     * @param articleId  文章ID
     * @return 点赞记录
     */
    Optional<ArticleLike> findByUserIdAndArticleId(Long userId, Long articleId);

    /**
     * 检查指定用户是否点赞了指定文章
     *
     * @param userId    用户ID
     * @param articleId 文章ID
     * @return 是否点赞
     */
    boolean existsByUserIdAndArticleId(Long userId, Long articleId);

    /**
     * 删除指定用户对指定文章的点赞
     *
     * @param userId    用户ID
     * @param articleId 文章ID
     */
    void deleteByUserIdAndArticleId(Long userId, Long articleId);
}
