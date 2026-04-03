package com.learnjava.quanysblog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库中的 user 表
 *
 * @author Quany
 */
@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 用户ID，主键自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名，唯一，长度不超过50字符
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * 密码（加密存储）
     */
    @Column(nullable = false)
    private String password;

    /**
     * 邮箱，唯一，长度不超过100字符
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * 用户头像URL
     */
    @Column(length = 500)
    private String avatar;

    /**
     * 用户个人简介
     */
    @Column(columnDefinition = "TEXT")
    private String bio;

    /**
     * 用户角色：ADMIN-管理员，USER-普通用户
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.USER;

    /**
     * 用户状态：ACTIVE-正常，INACTIVE-未激活，BANNED-已封禁
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.ACTIVE;

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

    /**
     * 用户角色枚举
     */
    public enum Role {
        /** 管理员 */
        ADMIN,
        /** 普通用户 */
        USER
    }

    /**
     * 用户状态枚举
     */
    public enum Status {
        /** 正常 */
        ACTIVE,
        /** 未激活 */
        INACTIVE,
        /** 已封禁 */
        BANNED
    }
}
