# Phase 5: 头像上传与个人资料

## 概述

Phase 4 完成了前后端 API 对接，但头像上传功能未实现。本阶段使用 MinIO（自建 S3 兼容存储）实现头像上传与个人资料管理功能。

## 技术方案

### 存储方案

- **MinIO**：自建 S3 兼容对象存储服务
- **依赖**：MinIO Java SDK 8.5.9
- **架构**：前端直传 MinIO，后端只负责生成访问 URL 并存储到数据库

### 架构流程

```
前端 (Vue3 el-upload)
       │
       ▼ POST /api/user/avatar (multipart/form-data)
后端 UserController
       │
       ▼
FileStorageService (MinIO SDK)
       │
       ▼
MinIO Server (localhost:9000)
       │
       ▼ 返回 avatar URL
       │
       ▼ PUT /api/user/profile (JSON: { avatar: "url" })
更新 User.avatar 字段
```

## 任务清单

| 任务 | 内容 | 状态 |
|------|------|------|
| Task 1 | 后端基础设施（依赖、配置） | ✅ |
| Task 2 | 存储服务模块（Storage Module） | ✅ |
| Task 3 | 用户服务模块（User Module） | ✅ |
| Task 4 | 前端 API 对接 | ✅ |
| Task 5 | 前端 Profile 页面完善 | ✅ |

## 验证标准

- [x] 头像上传成功，刷新页面后仍显示
- [x] 资料更新（邮箱、简介）保存成功
- [x] 登录响应包含 avatar 字段
- [x] 评论列表显示用户真实头像
