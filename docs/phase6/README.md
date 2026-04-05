# Phase 6: 点赞功能

## 概述

实现文章点赞功能，允许用户对文章进行点赞和取消点赞操作。

## 技术方案

### 数据库

新增 `article_like` 表记录用户点赞：

```sql
CREATE TABLE article_like (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    article_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_article (user_id, article_id),
    INDEX idx_article_id (article_id)
);
```

## 任务清单

| 任务 | 内容 | 状态 |
|------|------|------|
| Task 1 | 数据库迁移 - 创建 article_like 表 | ✅ |
| Task 2 | 后端 - ArticleLike 实体和 Repository | ✅ |
| Task 3 | 后端 - ArticleService 点赞方法 | ✅ |
| Task 4 | 后端 - ArticleController API | ✅ |
| Task 5 | 前端 - API 和类型定义 | ✅ |
| Task 6 | 前端 - 文章详情页点赞按钮 | ✅ |
| Task 7 | 前端 - 首页文章卡片点赞统计 | ✅ |
| Task 8 | 文档更新 | ✅ |

## 验证

- [ ] 点赞后 likeCount +1
- [ ] 取消点赞后 likeCount -1
- [ ] 点赞状态正确显示（已点赞/未点赞图标）
- [ ] 未登录用户点击点赞跳转登录页
- [ ] 重复点赞返回错误提示
