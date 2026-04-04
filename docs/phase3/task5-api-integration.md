# Task 5: 前后端 API 对接

## 目标
创建前端 API 模块和状态管理，完成前后端数据对接。

---

## 5.1 API 模块

### 方案
基于 Axios 封装各模块 API，统一管理请求配置。

### 拆分
| API 模块 | 路径 | 说明 |
|----------|------|------|
| article | `src/api/article.ts` | 文章相关 API |
| category | `src/api/category.ts` | 分类相关 API |
| tag | `src/api/tag.ts` | 标签相关 API |
| comment | `src/api/comment.ts` | 评论相关 API |

### API 设计标准
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/articles` | 分页获取文章列表 |
| GET | `/articles/{id}` | 获取文章详情 |
| POST | `/articles` | 创建文章 |
| PUT | `/articles/{id}` | 更新文章 |
| DELETE | `/articles/{id}` | 删除文章 |
| GET | `/categories` | 获取分类列表 |
| POST | `/categories` | 创建分类 |
| PUT | `/categories/{id}` | 更新分类 |
| DELETE | `/categories/{id}` | 删除分类 |
| GET | `/tags` | 获取标签列表 |
| POST | `/tags` | 创建标签 |
| PUT | `/tags/{id}` | 更新标签 |
| DELETE | `/tags/{id}` | 删除标签 |
| GET | `/comments/article/{articleId}` | 获取文章评论 |
| POST | `/comments` | 创建评论 |
| DELETE | `/comments/{id}` | 删除评论 |

### 验证标准
- API 方法与后端接口对应
- 请求拦截器携带 JWT Token
- 响应拦截器处理 401 错误

---

## 5.2 Pinia Store

### 方案
按模块创建 Store，管理前端状态。

### 拆分
| Store | 路径 | 说明 |
|-------|------|------|
| article | `src/stores/article.ts` | 文章列表和详情状态 |
| category | `src/stores/category.ts` | 分类列表状态 |
| tag | `src/stores/tag.ts` | 标签列表状态 |
| comment | `src/stores/comment.ts` | 评论列表状态 |

### 验证标准
- Store 包含对应 API 的状态管理
- 组件可调用 Store 方法获取数据

---

## 5.3 路由守卫

### 方案
使用 Vue Router 导航守卫，实现权限控制。

### 执行流程
1. 创建路由守卫逻辑
2. 未登录用户访问需要权限的页面时跳转登录
3. 非管理员访问后台时跳转首页

### 拆分
| 守卫 | 触发场景 |
|------|----------|
| 前置守卫 | 路由切换前检查登录状态 |
| 权限守卫 | 访问 `/admin` 时检查管理员身份 |

### 验证标准
- 未登录访问 `/admin` 跳转登录页
- 登录后跳转原请求页面
- 非管理员无法访问管理后台

---

## 验证清单

- [x] `src/api/article.ts` API 方法完整
- [x] `src/api/category.ts` API 方法完整
- [x] `src/api/tag.ts` API 方法完整
- [x] `src/api/comment.ts` API 方法完整
- [x] Pinia Store 状态管理正常
- [x] 路由守卫权限控制正常/co
- [x] 前后端联调测试通过

---

## 关键文件

| 文件 | 操作 |
|------|------|
| `src/api/article.ts` | 新建 |
| `src/api/category.ts` | 新建 |
| `src/api/tag.ts` | 新建 |
| `src/api/comment.ts` | 新建 |
| `src/stores/article.ts` | 新建 |
| `src/stores/category.ts` | 新建 |
| `src/stores/tag.ts` | 新建 |
| `src/stores/comment.ts` | 新建 |
| `src/router/index.ts` | 编辑：添加路由守卫 |
