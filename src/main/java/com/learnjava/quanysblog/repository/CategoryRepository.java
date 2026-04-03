package com.learnjava.quanysblog.repository;

import com.learnjava.quanysblog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 文章分类数据访问层
 * 提供分类相关的数据库操作方法
 *
 * @author Quany
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 根据分类别名查找分类
     *
     * @param slug 分类别名
     * @return 分类对象（如果存在）
     */
    Optional<Category> findBySlug(String slug);

    /**
     * 检查分类别名是否存在
     *
     * @param slug 分类别名
     * @return 是否存在
     */
    boolean existsBySlug(String slug);
}
