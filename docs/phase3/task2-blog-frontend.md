# Task 2: 博客前台页面

## 目标
开发博客前台页面，提供博客内容展示功能。

---

## 2.1 公共组件

### 方案
抽取可复用的展示组件，统一风格。

### 拆分
| 组件 | 路径 | 说明 |
|------|------|------|
| ArticleCard | `src/components/ArticleCard.vue` | 文章卡片，封面+标题+摘要+标签 |
| CategoryList | `src/components/CategoryList.vue` | 侧边栏分类列表 |
| TagList | `src/components/TagList.vue` | 侧边栏标签列表 |
| PopularArticles | `src/components/PopularArticles.vue` | 侧边栏热门文章 |

### 验证标准
- 组件可在多个页面复用
- 样式风格一致

---

## 2.2 首页 Home.vue

### 方案
左侧主内容区（文章列表+分页），右侧侧边栏（分类/标签/热门）。

### 执行流程
1. 调用文章列表 API
2. 循环渲染 ArticleCard
3. 调用侧边栏 API
4. 配置分页组件

### 拆分
| 区块 | 内容 |
|------|------|
| 主内容 | 文章列表（分页） |
| 侧边栏 | 分类、标签、热门文章 |

### 验证标准
- 首页加载时获取文章列表
- 分页切换正确加载对应页
- 点击文章卡片跳转详情页

---

## 2.3 文章详情页 ArticleDetail.vue

### 方案
文章主体 + Markdown 渲染 + 评论区。

### 执行流程
1. 根据路由 ID 加载文章详情
2. 使用 marked 渲染 Markdown 内容
3. 使用 highlight.js 处理代码高亮
4. 加载并展示评论列表

### 拆分
| 区块 | 内容 |
|------|------|
| 文章头部 | 标题、作者、时间、浏览数、标签 |
| 文章主体 | Markdown 渲染内容 |
| 评论区 | 评论列表 + 评论表单 |

### 验证标准
- Markdown 内容正确渲染
- 代码块有语法高亮
- 评论可提交和展示

---

## 2.4 分类页 Category.vue

### 方案
展示分类信息 + 该分类下的文章列表。

### 执行流程
1. 根据路由 slug 加载分类信息
2. 调用分类下文章列表 API
3. 渲染文章列表

### 验证标准
- 分类页标题和描述正确显示
- 文章列表为该分类下的文章

---

## 2.5 标签页 Tag.vue

### 方案
展示标签信息 + 该标签下的文章列表。

### 执行流程
1. 根据路由 slug 加载标签信息
2. 调用标签下文章列表 API
3. 渲染文章列表

### 验证标准
- 标签页标题正确显示
- 文章列表为该标签下的文章

---

## 2.6 搜索页 Search.vue

### 方案
搜索框 + 结果列表。

### 执行流程
1. 输入关键词并回车或点击搜索
2. 调用搜索 API
3. 渲染搜索结果

### 验证标准
- 输入关键词可搜索
- 无结果时显示空状态提示

---

## 验证清单

- [ ] `/` 路径显示首页（文章列表+侧边栏）
- [ ] `/article/:id` 路径显示文章详情
- [ ] `/category/:slug` 路径显示分类页
- [ ] `/tag/:slug` 路径显示标签页
- [ ] `/search` 路径可搜索
- [ ] 分页功能正常
- [ ] 页面跳转正常

---

## 关键文件

| 文件 | 操作 |
|------|------|
| `src/views/blog/Home.vue` | 新建 |
| `src/views/blog/ArticleDetail.vue` | 新建 |
| `src/views/blog/Category.vue` | 新建 |
| `src/views/blog/Tag.vue` | 新建 |
| `src/views/blog/Search.vue` | 新建 |
| `src/components/ArticleCard.vue` | 新建 |
| `src/components/CategoryList.vue` | 新建 |
| `src/components/TagList.vue` | 新建 |
| `src/components/PopularArticles.vue` | 新建 |
