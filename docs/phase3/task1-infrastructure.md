# Task 1: 基础设施完善

## 目标
完善前端基础设施，为后续页面开发做好准备。

---

## 1.1 安装依赖

### 方案
使用 Element Plus 作为 Vue 3 UI 组件库，支持自动导入减少包体积。

### 执行流程
1. 进入前端目录 `cd quanys-blog`
2. 安装 Element Plus 及图标库
3. 安装自动导入插件

### 拆分
| 步骤 | 操作 |
|------|------|
| 1 | 安装 `element-plus` |
| 2 | 安装 `@element-plus/icons-vue` |
| 3 | 安装 `unplugin-auto-import` |
| 4 | 安装 `unplugin-vue-components` |

### 验证标准
- `package.json` 包含 element-plus 相关依赖
- `npm install` 无报错

---

## 1.2 配置自动导入

### 方案
通过 Vite 插件配置 Element Plus 自动导入，实现按需加载。

### 执行流程
1. 修改 `vite.config.ts`
2. 配置 `AutoImport` 插件并添加 Element Plus 解析器
3. 配置 `Components` 插件并添加 Element Plus 解析器

### 拆分
| 文件 | 修改内容 |
|------|----------|
| `vite.config.ts` | 添加 AutoImport 和 Components 插件配置 |

### 验证标准
- `vite.config.ts` 包含 Element Plus 自动导入配置
- `npm run dev` 启动无控制台警告

---

## 1.3 创建布局组件

### 方案
使用 Element Plus 的 `el-container/el-header/el-aside/el-main/el-footer` 构建响应式布局。

#### MainLayout（博客前台）
- 水平导航模式
- 顶部固定 Header
- 主内容区 + 侧边栏
- 底部 Footer

#### AdminLayout（管理后台）
- 左侧固定侧边栏
- 顶部工具栏
- 主内容区

### 执行流程
1. 创建 `src/layouts/` 目录
2. 创建 `MainLayout.vue`
3. 创建 `AdminLayout.vue`

### 拆分
| 组件 | 路径 | 说明 |
|------|------|------|
| MainLayout | `src/layouts/MainLayout.vue` | 博客前台布局 |
| AdminLayout | `src/layouts/AdminLayout.vue` | 管理后台布局 |

### 验证标准
- 布局组件使用 `<el-container>` 结构
- 路由跳转后布局正确显示
- `/` 和 `/admin` 路径分别对应不同布局

---

## 1.4 配置嵌套路由

### 方案
使用 Vue Router 嵌套路由，前端路由按布局分组。

### 执行流程
1. 修改 `src/router/index.ts`
2. 配置博客前台路由（使用 MainLayout）
3. 配置管理后台路由（使用 AdminLayout）
4. 配置用户中心和认证路由

### 拆分
| 路由组 | 父路由 | 布局组件 |
|--------|--------|----------|
| 博客前台 | `/` | MainLayout |
| 管理后台 | `/admin` | AdminLayout |
| 用户中心 | `/profile` | 独立页面 |
| 认证 | `/auth` | 独立页面 |

### 验证标准
- `/` 路径渲染 MainLayout
- `/admin` 路径渲染 AdminLayout
- 子路由路径正确嵌套

---

## 验证清单

- [x] Element Plus 及图标库安装成功
- [x] `vite.config.ts` 配置自动导入
- [x] `MainLayout.vue` 创建完成
- [x] `AdminLayout.vue` 创建完成
- [x] 嵌套路由配置正确
- [x] `npm run build` 编译通过

---

## 关键文件

| 文件 | 操作 |
|------|------|
| `quanys-blog/vite.config.ts` | 编辑 |
| `quanys-blog/src/layouts/MainLayout.vue` | 新建 |
| `quanys-blog/src/layouts/AdminLayout.vue` | 新建 |
| `quanys-blog/src/router/index.ts` | 编辑 |
