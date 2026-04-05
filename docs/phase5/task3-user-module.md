# Phase 5 Task 3: 用户服务模块

## 概述

创建 User Module，实现用户资料管理和更新功能。

## 新建文件

| 文件 | 说明 |
|------|------|
| `module/user/dto/UpdateProfileRequest.java` | 资料更新请求 DTO |
| `module/user/service/UserService.java` | 用户服务接口 |
| `module/user/service/impl/UserServiceImpl.java` | 用户服务实现 |
| `module/user/controller/UserController.java` | REST API |

## 核心逻辑

- `getCurrentUser`: 根据用户名获取用户
- `updateProfile`: 更新邮箱/简介/头像，校验邮箱唯一性

## API

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/user/profile` | 获取当前用户资料 |
| PUT | `/api/user/profile` | 更新用户资料 |
| POST | `/api/user/avatar` | 上传头像 |

## 验证

- [x] `GET /api/user/profile` 返回当前登录用户信息
- [x] `PUT /api/user/profile` 可更新邮箱/简介/头像
- [x] 更新邮箱时校验唯一性
