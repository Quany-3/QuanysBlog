# 第二阶段：核心业务 - 模块化重构与功能实现

## 目标
1. 重构项目架构为模块化结构
2. 实现各模块的 Service 层 + Controller 层

---

## 目录结构（重构后）

```
src/main/java/com/learnjava/quanysblog/
├── QuanysBlogApplication.java
│
├── config/                          # 全局配置
│   ├── SecurityConfig.java
│   └── CorsConfig.java
│
├── common/                          # 通用组件
│   ├── result/
│   │   ├── ApiResponse.java
│   │   └── ResultCode.java
│   └── exception/
│       ├── GlobalExceptionHandler.java
│       ├── BusinessException.java
│       └── ResourceNotFoundException.java
│
├── module/                          # 业务模块
│   ├── auth/                        # 认证模块
│   │   ├── controller/AuthController.java
│   │   ├── service/AuthService.java
│   │   ├── service/impl/AuthServiceImpl.java
│   │   ├── repository/UserRepository.java
│   │   └── dto/
│   │
│   ├── article/                     # 文章模块
│   │   ├── controller/ArticleController.java
│   │   ├── service/ArticleService.java
│   │   ├── service/impl/ArticleServiceImpl.java
│   │   ├── repository/ArticleRepository.java
│   │   ├── entity/Article.java
│   │   └── dto/
│   │
│   ├── category/                    # 分类模块
│   ├── tag/                         # 标签模块
│   └── comment/                     # 评论模块
│
└── security/                        # 安全相关
```

---

## 重构任务清单

### Task 0: 架构重构（前置任务）
- [x] 创建 common 模块（ApiResponse, ResultCode, 自定义异常）
- [x] 按模块移动 entity, repository, dto
- [x] 创建 Service 接口 + Impl 实现类
- [x] 重构 Controller 使用 Service
- [x] 清理旧文件夹

### Task 1: ArticleService + ArticleController
**API 端点**:
| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/articles` | 创建文章 |
| GET | `/api/articles` | 分页查询列表 |
| GET | `/api/articles/{id}` | 获取文章详情 |
| PUT | `/api/articles/{id}` | 更新文章 |
| DELETE | `/api/articles/{id}` | 删除文章 |
| GET | `/api/articles/category/{categoryId}` | 按分类查询 |
| GET | `/api/articles/tag/{tagId}` | 按标签查询 |
| GET | `/api/articles/search?q=` | 搜索文章 |

### Task 2: CategoryService + CategoryController
**API 端点**:
| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/categories` | 创建分类 |
| GET | `/api/categories` | 获取所有分类 |
| GET | `/api/categories/{id}` | 获取分类详情 |
| PUT | `/api/categories/{id}` | 更新分类 |
| DELETE | `/api/categories/{id}` | 删除分类 |

### Task 3: TagService + TagController
**API 端点**:
| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/tags` | 创建标签 |
| GET | `/api/tags` | 获取所有标签 |
| GET | `/api/tags/{id}` | 获取标签详情 |
| PUT | `/api/tags/{id}` | 更新标签 |
| DELETE | `/api/tags/{id}` | 删除标签 |

### Task 4: CommentService + CommentController
**API 端点**:
| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/api/comments` | 创建评论 |
| GET | `/api/comments/article/{articleId}` | 获取文章评论列表 |
| DELETE | `/api/comments/{id}` | 删除评论 |

---

## 验证方式

1. `./mvnw compile` - 编译通过
2. `./mvnw test` - 测试通过
3. 启动 MySQL: `docker compose up -d`
4. 启动后端: `./mvnw spring-boot:run`
5. API 测试

---

## 进度

- [x] Task 0: 架构重构
- [x] Task 1: ArticleService + ArticleController
- [x] Task 2: CategoryService + CategoryController
- [x] Task 3: TagService + TagController
- [x] Task 4: CommentService + CommentController

---

## 验证结果

- [x] `./mvnw compile` - 编译通过
- [x] `./mvnw test` - 测试通过（1 passed）
- [x] 启动 MySQL: `docker compose up -d`
- [x] 启动后端: `./mvnw spring-boot:run`
- [x] API 测试
