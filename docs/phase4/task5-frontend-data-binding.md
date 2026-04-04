# Task 5: 前台数据绑定完善

## 目标

完善博客前台页面的数据绑定，让文章列表、详情、分类、标签、搜索等功能显示真实数据。

---

## 5.1 Home.vue 首页

### 当前状态
- 已调用 `articleStore.fetchArticles()` 获取文章列表

### 验证标准
- 首页显示真实的文章列表
- 点击文章卡片跳转到文章详情页

---

## 5.2 ArticleDetail.vue 文章详情

### 当前状态
- 已调用 `articleStore.fetchArticleById()` 获取文章详情
- 使用 `marked` 渲染 Markdown 内容

### 验证标准
- 文章详情页显示真实文章内容
- Markdown 内容正确渲染

---

## 5.3 Category.vue 分类页

### 当前状态
- 占位组件

### 修改内容
| 字段 | 修改 |
|------|------|
| 文章列表 | 调用 `articleApi.getByCategory()` 获取分类下的文章 |

### 验证标准
- 显示该分类下的所有文章
- 点击文章跳转到文章详情页

---

## 5.4 Tag.vue 标签页

### 当前状态
- 占位组件

### 修改内容
| 字段 | 修改 |
|------|------|
| 文章列表 | 调用 `articleApi.getByTag()` 获取标签下的文章 |

### 验证标准
- 显示该标签下的所有文章
- 点击文章跳转到文章详情页

---

## 5.5 Search.vue 搜索页

### 当前状态
- 占位组件

### 修改内容
| 字段 | 修改 |
|------|------|
| 搜索 | 调用 `articleApi.search()` 获取搜索结果 |

### 验证标准
- 输入关键词搜索，显示相关文章列表
- 点击文章跳转到文章详情页

---

## 关键文件

| 文件 | 操作 |
|------|------|
| `src/views/blog/Home.vue` | 检查 |
| `src/views/blog/ArticleDetail.vue` | 检查 |
| `src/views/blog/Category.vue` | 编辑 |
| `src/views/blog/Tag.vue` | 编辑 |
| `src/views/blog/Search.vue` | 编辑 |

---

## 验证清单

- [ ] 首页文章列表显示真实数据
- [ ] 文章详情页显示真实内容并渲染 Markdown
- [ ] 分类页显示该分类下的文章列表
- [ ] 标签页显示该标签下的文章列表
- [ ] 搜索页支持关键词搜索并显示结果
