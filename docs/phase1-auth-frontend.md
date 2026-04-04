# 阶段1: Auth 模块 - 前端开发

## 目标
完成 Auth 模块的前端页面，与后端联调验证。

---

## 前端技术栈

- Vue 3 + TypeScript
- Vite (构建工具)
- Vue Router (路由)
- Pinia (状态管理)
- Axios (HTTP 请求)
- Element Plus (UI 组件库，可选)

---
## 需要创建的文件

```
quanys-blog/                          # 前端项目根目录（与后端平级）
├── src/
│   ├── api/                        # API 请求
│   │   ├── index.ts              # axios 实例配置
│   │   ├── auth.ts               # 认证相关 API
│   │   └── types/                 # TypeScript 类型定义
│   │       └── index.ts
│   │
│   ├── views/                     # 页面组件
│   │   └── auth/
│   │       ├── Login.vue          # 登录页
│   │       └── Register.vue       # 注册页
│   │
│   ├── router/
│   │   └── index.ts               # 路由配置
│   │
│   ├── stores/                    # 状态管理
│   │   └── auth.ts               # 认证状态
│   │
│   ├── App.vue
│   └── main.ts
│
├── index.html
├── package.json
├── tsconfig.json
└── vite.config.ts
```

---

## 任务清单

### Task 1: 初始化前端项目
- [ ] 创建项目目录结构
- [ ] 创建 package.json
- [ ] 创建 vite.config.ts
- [ ] 创建 tsconfig.json
- [ ] 安装依赖

### Task 2: 创建基础配置
- [ ] 创建 axios 实例配置
- [ ] 创建 TypeScript 类型定义
- [ ] 创建路由配置
- [ ] 创建 Pinia store

### Task 3: 创建 Auth 页面
- [ ] 创建 Login.vue 登录页
- [ ] 创建 Register.vue 注册页

### Task 4: 前后端联调
- [ ] 启动后端 (MySQL + Spring Boot)
- [ ] 启动前端 (npm run dev)
- [ ] 测试登录功能
- [ ] 测试注册功能
- [ ] 验证 JWT token 存储和使用

---

## 验证方式

1. 启动 MySQL: `docker compose up -d`
2. 启动后端: `./mvnw spring-boot:run` (端口 8080)
3. 启动前端: `npm run dev` (端口 5173)
4. 访问 `http://localhost:5173`
5. 测试注册 → 登录 → 登出流程

---

## 进度

- [x] Task 1: 初始化前端项目
- [x] Task 2: 创建基础配置
- [x] Task 3: 创建 Auth 页面
- [x] Task 4: 前后端联调
