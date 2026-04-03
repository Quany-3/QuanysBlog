# QuanysBlog

个人博客系统，基于 Spring Boot + Vue 3 构建的前后端分离项目。

## 技术栈

**后端**
- Spring Boot 4.0.1
- Spring Security + JWT 认证
- Spring Data JPA
- MySQL 8.0
- Flyway 数据库迁移

**前端**
- Vue 3 + TypeScript
- Vite
- Pinia 状态管理
- Axios

## 功能特性

- 用户注册/登录
- 文章管理（Markdown）
- 分类与标签
- 评论系统
- 搜索功能

## 项目结构

```
QuanysBlog/
├── src/main/java/     # 后端源码
├── src/main/resources/
│   ├── application.yml # 配置文件
│   └── db/migration/   # Flyway 迁移脚本
└── compose.yaml        # MySQL 服务配置
```

## 快速开始

### 1. 启动数据库

```bash
docker compose up -d
```

### 2. 配置环境变量

复制 `.env.example` 为 `.env` 并修改配置。

### 3. 启动后端

```bash
./mvnw spring-boot:run
```

## API 文档

- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录
- `GET /api/articles` - 文章列表
- `GET /api/articles/{id}` - 文章详情

