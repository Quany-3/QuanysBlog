# Task 3: 管理后台 - 标签管理 CRUD

## 目标

完善管理后台的标签管理功能，让创建、编辑、删除功能真正可用。

---

## 3.1 TagManage.vue

### 当前状态
- 使用 Element Plus Table 组件
- 使用假数据

### 修改内容
| 字段 | 修改 |
|------|------|
| 表格数据 | 调用 `tagStore.fetchTags()` 获取数据 |
| 新建 | 弹窗表单调用 `tagStore.createTag()` |
| 编辑 | 弹窗表单调用 `tagStore.updateTag()` |
| 删除 | 调用 `tagStore.deleteTag()` |

### 验证标准
- 页面加载时获取所有标签
- 点击新建标签，填写表单后调用 API
- 点击编辑标签，修改表单后调用 API
- 点击删除标签，确认后调用 API

---

## 关键文件

| 文件 | 操作 |
|------|------|
| `src/views/admin/tag/TagManage.vue` | 编辑 |
| `src/stores/tag.ts` | 检查 |

---

## 验证清单

- [x] 页面加载时获取标签列表
- [x] 新建标签功能调用 API
- [x] 编辑标签功能调用 API
- [x] 删除标签功能调用 API
