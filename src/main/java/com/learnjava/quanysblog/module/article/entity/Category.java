package com.learnjava.quanysblog.module.article.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 文章分类实体类
 * 对应数据库中的 category 表
 *
 * @author Quany
 */
@Entity
@Table(name = "category")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    /**
     * 分类ID，主键自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分类名称，长度不超过50字符
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * 分类别名（URL友好），唯一，用于URL路径
     */
    @Column(nullable = false, unique = true, length = 50)
    private String slug;

    /**
     * 分类描述
     */
    @Column(length = 200)
    private String description;

    /**
     * 排序权重，数字越小排序越靠前，默认0
     */
    @Column(nullable = false)
    @Builder.Default
    private Integer sort = 0;

    /**
     * 创建时间，自动填充
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * 更新时间，自动填充
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
