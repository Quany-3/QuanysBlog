package com.learnjava.quanysblog.module.article.repository;

import com.learnjava.quanysblog.module.article.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 文章标签数据访问层
 * 提供标签相关的数据库操作方法
 *
 * @author Quany
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 根据标签别名查找标签
     *
     * @param slug 标签别名
     * @return 标签对象（如果存在）
     */
    Optional<Tag> findBySlug(String slug);

    /**
     * 根据标签名称查找标签
     *
     * @param name 标签名称
     * @return 标签对象（如果存在）
     */
    Optional<Tag> findByName(String name);

    /**
     * 检查标签别名是否存在
     *
     * @param slug 标签别名
     * @return 是否存在
     */
    boolean existsBySlug(String slug);

    /**
     * 检查标签名称是否存在
     *
     * @param name 标签名称
     * @return 是否存在
     */
    boolean existsByName(String name);
}
