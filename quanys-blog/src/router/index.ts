import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'

// 路由配置
const routes: RouteRecordRaw[] = [
  // 认证页面（不使用布局）
  {
    path: '/auth',
    redirect: '/auth/login'
  },
  {
    path: '/auth/login',
    name: 'Login',
    component: () => import('@/views/auth/Login.vue')
  },
  {
    path: '/auth/register',
    name: 'Register',
    component: () => import('@/views/auth/Register.vue')
  },

  // 博客前台（使用 MainLayout）
  {
    path: '/',
    component: () => import('@/layouts/MainLayout.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        component: () => import('@/views/blog/Home.vue')
      },
      {
        path: 'article/:id',
        name: 'ArticleDetail',
        component: () => import('@/views/blog/ArticleDetail.vue')
      },
      {
        path: 'category/:slug?',
        name: 'Category',
        component: () => import('@/views/blog/Category.vue')
      },
      {
        path: 'tag/:slug?',
        name: 'Tag',
        component: () => import('@/views/blog/Tag.vue')
      },
      {
        path: 'search',
        name: 'Search',
        component: () => import('@/views/blog/Search.vue')
      }
    ]
  },

  // 管理后台（使用 AdminLayout）
  {
    path: '/admin',
    component: () => import('@/layouts/AdminLayout.vue'),
    children: [
      {
        path: '',
        name: 'Dashboard',
        component: () => import('@/views/admin/Dashboard.vue')
      },
      {
        path: 'articles',
        name: 'ArticleList',
        component: () => import('@/views/admin/article/ArticleList.vue')
      },
      {
        path: 'articles/create',
        name: 'ArticleCreate',
        component: () => import('@/views/admin/article/ArticleCreate.vue')
      },
      {
        path: 'articles/edit/:id',
        name: 'ArticleEdit',
        component: () => import('@/views/admin/article/ArticleEdit.vue')
      },
      {
        path: 'categories',
        name: 'CategoryManage',
        component: () => import('@/views/admin/category/CategoryManage.vue')
      },
      {
        path: 'tags',
        name: 'TagManage',
        component: () => import('@/views/admin/tag/TagManage.vue')
      },
      {
        path: 'comments',
        name: 'CommentManage',
        component: () => import('@/views/admin/comment/CommentManage.vue')
      }
    ]
  },

  // 用户中心
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('@/views/user/Profile.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
