# Task 1: 管理后台 - 文章管理 CRUD

## 目标

完善管理后台的文章管理功能，让创建、编辑、发布、删除功能真正可用。

---

## 1.1 ArticleList.vue

### 当前状态
- 使用假数据 `articles = ref([])`
- 分页功能未对接 API

### 修改内容
| 字段 | 修改 |
|------|------|
| articles | 改为调用 `articleStore.fetchAdminArticles()` 获取数据 |
| total | 从 API 响应中获取 |
| currentPage | 支持切换页码调用 API |

### 验证标准
- 页面加载时调用 API 获取文章列表
- 分页切换时调用 API 获取对应页数据
- 点击编辑按钮跳转编辑页面
- 点击删除按钮调用 API 删除文章

---

## 1.2 ArticleCreate.vue

### 当前状态
- 使用静态假数据

### 修改内容
| 字段 | 修改 |
|------|------|
| 表单 | 使用 `v-model` 绑定 ArticleRequest 类型数据 |
| 分类选择 | 调用 `categoryStore.fetchCategories()` 获取真实分类列表 |
| 标签选择 | 调用 `tagStore.fetchTags()` 获取真实标签列表 |
| 提交 | 调用 `articleApi.create()` 创建文章 |
| 发布 | 设置 `isPublished: true` |

### 验证标准
- 页面加载时获取分类和标签列表
- 填写表单后点击发布，调用 API 创建文章
- 创建成功后跳转文章列表页

---

## 1.3 ArticleEdit.vue

### 当前状态
- 已有部分 API 对接代码（上一轮修复）

### 验证标准
- 页面加载时获取文章详情、分类列表、标签列表
- 修改表单后点击保存，调用 API 更新文章
- 更新成功后跳转文章列表页

---

## 关键文件

| 文件 | 操作 |
|------|------|
| `src/views/admin/article/ArticleList.vue` | 编辑 |
| `src/views/admin/article/ArticleCreate.vue` | 编辑 |
| `src/views/admin/article/ArticleEdit.vue` | 检查/编辑 |
| `src/stores/article.ts` | 编辑：添加 `fetchAdminArticles()` |

---

## 验证清单

- [x] ArticleList.vue 调用真实 API 获取文章列表
- [x] ArticleList.vue 支持分页
- [x] ArticleList.vue 删除功能调用 API
- [x] ArticleCreate.vue 表单提交调用 API
- [x] ArticleCreate.vue 获取真实分类和标签
- [x] ArticleEdit.vue 保存功能调用 API
- [x] 创建/编辑成功后刷新文章列表
