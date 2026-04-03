package com.learnjava.quanysblog.repository;

import com.learnjava.quanysblog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByArticleIdAndStatusOrderByCreatedAtDesc(Long articleId, Comment.Status status, Pageable pageable);

    List<Comment> findByArticleIdAndParentIsNullAndStatusOrderByCreatedAtDesc(Long articleId, Comment.Status status);

    Page<Comment> findByAuthorIdOrderByCreatedAtDesc(Long authorId, Pageable pageable);

    Page<Comment> findByStatusOrderByCreatedAtDesc(Comment.Status status, Pageable pageable);
}
