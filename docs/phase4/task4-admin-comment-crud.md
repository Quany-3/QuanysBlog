# Task 4: 管理后台 - 评论管理 CRUD

## 目标

完善管理后台的评论管理功能，让查看、删除功能真正可用。

---

## 4.1 CommentManage.vue

### 当前状态
- 使用 Element Plus Table 组件
- 使用假数据

### 修改内容
| 字段 | 修改 |
|------|------|
| 表格数据 | 调用 `commentStore.fetchAllComments()` 获取数据 |
| 查看 | 显示评论列表，包括评论内容、作者、文章标题、创建时间 |
| 删除 | 调用 `commentStore.deleteComment()` |

### 验证标准
- 页面加载时获取所有评论
- 点击删除评论，确认后调用 API

---

## 关键文件

| 文件 | 操作 |
|------|------|
| `src/views/admin/comment/CommentManage.vue` | 编辑 |
| `src/stores/comment.ts` | 编辑：添加 `fetchAllComments()` |
| `src/api/comment.ts` | 检查 |

---

## 验证清单

- [ ] 页面加载时获取评论列表
- [ ] 评论列表显示评论内容、作者、文章标题、创建时间
- [ ] 删除评论功能调用 API
