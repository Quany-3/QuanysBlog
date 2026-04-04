# 第三阶段：前端业务页面开发

## 目标
1. 完善前端基础设施
2. 开发博客前台页面（首页、文章详情、分类、标签、搜索）
3. 开发管理后台页面
4. 完成前后端API对接

---

## 前端路由设计

### 博客前台路由
| 路径 | 组件 | 说明 |
|------|------|------|
| `/` | `Home.vue` | 首页 |
| `/article/:id` | `ArticleDetail.vue` | 文章详情 |
| `/category/:slug` | `Category.vue` | 分类页 |
| `/tag/:slug` | `Tag.vue` | 标签页 |
| `/search` | `Search.vue` | 搜索页 |

### 管理后台路由
| 路径 | 组件 | 说明 |
|------|------|------|
| `/admin` | `Dashboard.vue` | 仪表盘 |
| `/admin/articles` | `ArticleList.vue` | 文章列表 |
| `/admin/articles/create` | `ArticleCreate.vue` | 创建文章 |
| `/admin/articles/edit/:id` | `ArticleEdit.vue` | 编辑文章 |
| `/admin/categories` | `CategoryManage.vue` | 分类管理 |
| `/admin/tags` | `TagManage.vue` | 标签管理 |
| `/admin/comments` | `CommentManage.vue` | 评论管理 |

### 用户路由
| 路径 | 组件 | 说明 |
|------|------|------|
| `/profile` | `Profile.vue` | 个人资料 |

---

## 目录结构

```
quanys-blog/src/
├── api/
│   ├── article.ts        # 文章 API
│   ├── category.ts       # 分类 API
│   ├── tag.ts            # 标签 API
│   └── comment.ts        # 评论 API
│
├── views/
│   ├── blog/             # 博客前台
│   │   ├── Home.vue
│   │   ├── ArticleDetail.vue
│   │   ├── Category.vue
│   │   ├── Tag.vue
│   │   └── Search.vue
│   ├── admin/             # 管理后台
│   │   ├── Dashboard.vue
│   │   ├── article/
│   │   ├── category/
│   │   ├── tag/
│   │   └── comment/
│   ├── user/             # 用户中心
│   │   └── Profile.vue
│   └── auth/             # 认证（已存在）
│
├── layouts/
│   ├── MainLayout.vue     # 博客前台布局
│   └── AdminLayout.vue    # 管理后台布局
│
├── stores/
│   ├── article.ts        # 文章状态
│   ├── category.ts       # 分类状态
│   ├── tag.ts            # 标签状态
│   └── comment.ts        # 评论状态
│
└── components/          # 公共组件
    ├── ArticleCard.vue   # 文章卡片
    ├── Sidebar.vue       # 侧边栏
    └── MarkdownEditor.vue # Markdown 编辑器
```

---

## 任务清单

### Task 1: 基础设施完善
- [ ] 安装 Element Plus
- [ ] 安装 Markdown 渲染库（marked + highlight.js）
- [ ] 创建 MainLayout 布局组件
- [ ] 创建 AdminLayout 布局组件
- [ ] 配置嵌套路由

### Task 2: 博客前台页面
- [ ] Home.vue - 首页（文章列表 + 侧边栏）
- [ ] ArticleDetail.vue - 文章详情页
- [ ] Category.vue - 分类页
- [ ] Tag.vue - 标签页
- [ ] Search.vue - 搜索页

### Task 3: 管理后台页面
- [ ] Dashboard.vue - 仪表盘
- [ ] ArticleList.vue - 文章列表
- [ ] ArticleCreate.vue - 创建文章
- [ ] ArticleEdit.vue - 编辑文章
- [ ] CategoryManage.vue - 分类管理
- [ ] TagManage.vue - 标签管理
- [ ] CommentManage.vue - 评论管理

### Task 4: 用户中心
- [ ] Profile.vue - 个人资料页

### Task 5: 前后端 API 对接
- [ ] 创建 article.ts API 模块
- [ ] 创建 category.ts API 模块
- [ ] 创建 tag.ts API 模块
- [ ] 创建 comment.ts API 模块
- [ ] 创建对应 Pinia Store
- [ ] 实现路由守卫（权限控制）

---

## 技术选型

| 功能 | 库 | 说明 |
|------|-----|------|
| UI 组件 | Element Plus | Vue 3 组件库 |
| Markdown 渲染 | marked + highlight.js | 文章内容渲染 |
| Markdown 编辑 | @kangc/v-md-editor | 管理后台文章编辑 |
| 图标 | @element-plus/icons-vue | Element Plus 图标 |

---

## 验证方式

1. 前端编译通过: `npm run build`
2. 后端编译通过: `./mvnw compile`
3. 启动 MySQL: `docker compose up -d`
4. 启动后端: `./mvnw spring-boot:run`
5. 启动前端: `npm run dev`
6. 访问 `http://localhost:5173` 测试各页面

---

## 进度

- [ ] Task 1: 基础设施完善
- [ ] Task 2: 博客前台页面
- [ ] Task 3: 管理后台页面
- [ ] Task 4: 用户中心
- [ ] Task 5: 前后端 API 对接
