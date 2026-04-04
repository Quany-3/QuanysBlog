# Task 2: 管理后台 - 分类管理 CRUD

## 目标

完善管理后台的分类管理功能，让创建、编辑、删除功能真正可用。

---

## 2.1 CategoryManage.vue

### 当前状态
- 使用 Element Plus Table 组件
- 使用假数据

### 修改内容
| 字段 | 修改 |
|------|------|
| 表格数据 | 调用 `categoryStore.fetchCategories()` 获取数据 |
| 新建 | 弹窗表单调用 `categoryStore.createCategory()` |
| 编辑 | 弹窗表单调用 `categoryStore.updateCategory()` |
| 删除 | 调用 `categoryStore.deleteCategory()` |

### 验证标准
- 页面加载时获取所有分类
- 点击新建分类，填写表单后调用 API
- 点击编辑分类，修改表单后调用 API
- 点击删除分类，确认后调用 API

---

## 关键文件

| 文件 | 操作 |
|------|------|
| `src/views/admin/category/CategoryManage.vue` | 编辑 |
| `src/stores/category.ts` | 检查 |

---

## 验证清单

- [ ] 页面加载时获取分类列表
- [ ] 新建分类功能调用 API
- [ ] 编辑分类功能调用 API
- [ ] 删除分类功能调用 API
