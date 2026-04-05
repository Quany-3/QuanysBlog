# Phase 5 Task 1: 后端基础设施

## 概述

完成后端基础配置：MinIO SDK 依赖、配置文件更新、SecurityConfig 放行用户 API。

## 实现内容

| 操作 | 文件 |
|------|------|
| 添加 MinIO SDK 依赖 | `pom.xml` |
| 添加 MinIO 配置 | `application.yml` |
| 放行 `/api/user/**` 认证 | `SecurityConfig.java` |
| AuthResponse 添加 avatar 字段 | `AuthResponse.java` |
| AuthServiceImpl 返回 avatar | `AuthServiceImpl.java` |
| 添加 dotenv-java 依赖 | `pom.xml` |
| 创建 DotenvInitializer | `DotenvInitializer.java` |
| 注册 EnvironmentPostProcessor | `META-INF/spring.factories` |

## 验证

- [x] `./mvnw compile` 编译成功
- [x] `.env` 变量可被 Spring 读取
