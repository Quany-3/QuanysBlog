package com.learnjava.quanysblog.module.article.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 文章标签实体类
 * 对应数据库中的 tag 表
 *
 * @author Quany
 */
@Entity
@Table(name = "tag")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    /**
     * 标签ID，主键自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标签名称，唯一
     */
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    /**
     * 标签别名（URL友好），唯一，用于URL路径
     */
    @Column(nullable = false, unique = true, length = 50)
    private String slug;

    /**
     * 标签颜色，默认蓝色 #1890ff
     */
    @Column(length = 20)
    @Builder.Default
    private String color = "#1890ff";

    /**
     * 创建时间，自动填充
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
