# Task 3: 管理后台页面

## 目标
开发管理后台页面，提供内容管理功能。

---

## 3.1 仪表盘 Dashboard.vue

### 方案
统计概览卡片 + 快捷操作入口。

### 拆分
| 区块 | 内容 |
|------|------|
| 统计卡片 | 文章数、分类数、标签数、评论数 |
| 快捷入口 | 文章管理、分类管理、标签管理、评论管理 |

### 验证标准
- 统计数字正确显示
- 快捷入口可跳转对应页面

---

## 3.2 文章管理

### 方案
列表页 + 创建/编辑页面。

#### ArticleList.vue（列表）
- 表格展示文章列表
- 支持分页
- 操作列（编辑/删除）

#### ArticleCreate.vue（创建）
- Markdown 编辑器
- 分类/标签选择
- 封面图片上传

#### ArticleEdit.vue（编辑）
- 加载已有文章数据
- Markdown 编辑器
- 分类/标签修改

### 执行流程
1. 实现文章列表页面
2. 实现文章创建页面（含 Markdown 编辑器）
3. 实现文章编辑页面
4. 对接后端 CRUD API

### 验证标准
- 可查看文章列表
- 可创建新文章
- 可编辑已有文章
- 可删除文章

---

## 3.3 分类管理 CategoryManage.vue

### 方案
表格 + 新建/编辑弹窗。

### 执行流程
1. 加载分类列表
2. 实现新建分类弹窗
3. 实现编辑分类弹窗
4. 对接后端 CRUD API

### 验证标准
- 可查看分类列表
- 可创建新分类
- 可编辑分类
- 可删除分类

---

## 3.4 标签管理 TagManage.vue

### 方案
表格 + 新建/编辑弹窗。

### 执行流程
1. 加载标签列表
2. 实现新建标签弹窗
3. 实现编辑标签弹窗
4. 对接后端 CRUD API

### 验证标准
- 可查看标签列表
- 可创建新标签
- 可编辑标签
- 可删除标签

---

## 3.5 评论管理 CommentManage.vue

### 方案
表格展示评论列表 + 审核/删除操作。

### 执行流程
1. 加载评论列表
2. 实现审核功能（通过/拒绝）
3. 实现删除功能
4. 对接后端 API

### 验证标准
- 可查看评论列表
- 可审核评论
- 可删除评论

---

## 验证清单

- [ ] `/admin` 仪表盘显示统计数据
- [ ] `/admin/articles` 文章列表正常
- [ ] `/admin/articles/create` 可创建文章
- [ ] `/admin/articles/edit/:id` 可编辑文章
- [ ] `/admin/categories` 分类管理正常
- [ ] `/admin/tags` 标签管理正常
- [ ] `/admin/comments` 评论管理正常

---

## 关键文件

| 文件 | 操作 |
|------|------|
| `src/views/admin/Dashboard.vue` | 新建 |
| `src/views/admin/article/ArticleList.vue` | 新建 |
| `src/views/admin/article/ArticleCreate.vue` | 新建 |
| `src/views/admin/article/ArticleEdit.vue` | 新建 |
| `src/views/admin/category/CategoryManage.vue` | 新建 |
| `src/views/admin/tag/TagManage.vue` | 新建 |
| `src/views/admin/comment/CommentManage.vue` | 新建 |
