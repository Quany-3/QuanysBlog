# Phase 5 Task 2: 存储服务模块

## 概述

创建 Storage Module，实现 MinIO 文件上传功能。

## 新建文件

| 文件 | 说明 |
|------|------|
| `config/MinioConfig.java` | MinIO 客户端配置 |
| `module/storage/service/FileStorageService.java` | 存储服务接口 |
| `module/storage/service/impl/FileStorageServiceImpl.java` | MinIO 上传实现 |

## 核心逻辑

1. 校验文件大小、类型
2. 生成唯一文件名：`avatars/{username}/{uuid}.{ext}`
3. 调用 MinIO SDK 上传
4. 返回公网访问 URL

## API

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/user/avatar` | 上传头像（需要认证） |

## 验证

- [x] MinIO 服务启动后，上传接口可正常调用
- [x] 上传成功返回公网可访问的 URL
