package com.learnjava.quanysblog.repository;

import com.learnjava.quanysblog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByIsPublishedTrueOrderByPublishedAtDesc(Pageable pageable);

    Page<Article> findByAuthorIdAndIsPublishedTrueOrderByPublishedAtDesc(Long authorId, Pageable pageable);

    Page<Article> findByCategoryIdAndIsPublishedTrueOrderByPublishedAtDesc(Long categoryId, Pageable pageable);

    List<Article> findByIsFeaturedTrueAndIsPublishedTrueOrderByPublishedAtDesc(Pageable pageable);

    @Query("SELECT a FROM Article a JOIN a.tags t WHERE t.id = :tagId AND a.isPublished = true ORDER BY a.publishedAt DESC")
    Page<Article> findByTagIdAndIsPublishedTrue(@Param("tagId") Long tagId, Pageable pageable);

    @Query("SELECT a FROM Article a WHERE a.isPublished = true AND (a.title LIKE %:keyword% OR a.content LIKE %:keyword%) ORDER BY a.publishedAt DESC")
    Page<Article> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
