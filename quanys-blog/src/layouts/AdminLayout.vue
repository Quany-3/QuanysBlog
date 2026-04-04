<template>
  <el-container class="admin-layout">
    <el-aside width="200px" class="aside">
      <div class="logo">管理后台</div>
      <el-menu :router="true" :default-active="activeIndex" class="menu">
        <el-menu-item index="/admin">
          <el-icon><Odometer /></el-icon>
          <span>仪表盘</span>
        </el-menu-item>
        <el-menu-item index="/admin/articles">
          <el-icon><Document /></el-icon>
          <span>文章管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/categories">
          <el-icon><Folder /></el-icon>
          <span>分类管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/tags">
          <el-icon><PriceTag /></el-icon>
          <span>标签管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/comments">
          <el-icon><ChatDotRound /></el-icon>
          <span>评论管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="currentRoute">{{ currentRoute }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-button @click="goToHome">返回前台</el-button>
          <el-dropdown @command="handleUserCommand">
            <span class="user-dropdown">
              <el-avatar :size="32" :src="authStore.userInfo?.avatar">
                {{ authStore.userInfo?.username?.charAt(0) }}
              </el-avatar>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item :command="UserCommand.PROFILE">个人中心</el-dropdown-item>
                <el-dropdown-item :command="UserCommand.LOGOUT" divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { ElMessage } from 'element-plus'
import {
  Odometer,
  Document,
  Folder,
  PriceTag,
  ChatDotRound
} from '@element-plus/icons-vue'

const UserCommand = {
  PROFILE: 'profile',
  LOGOUT: 'logout'
} as const

const routeMap: Record<string, string> = {
  '/admin': '',
  '/admin/articles': '文章管理',
  '/admin/categories': '分类管理',
  '/admin/tags': '标签管理',
  '/admin/comments': '评论管理'
}

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const activeIndex = computed(() => route.path)

const currentRoute = computed(() => routeMap[route.path] || null)

const goToHome = () => {
  router.push('/')
}

const handleUserCommand = (command: string) => {
  switch (command) {
    case UserCommand.PROFILE:
      router.push('/profile')
      break
    case UserCommand.LOGOUT:
      authStore.logout()
      ElMessage.success('已退出登录')
      router.push('/')
      break
  }
}
</script>

<style scoped>
.admin-layout {
  height: 100vh;
}

.aside {
  background: #304156;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 18px;
  font-weight: 600;
  background: #263445;
}

.menu {
  border-right: none;
  background: #304156;
}

.menu .el-menu-item {
  color: #bfcbd9;
}

.menu .el-menu-item:hover,
.menu .el-menu-item.is-active {
  background: #263445;
  color: #409eff;
}

.header {
  background: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
}

.header-left {
  display: flex;
  align-items: center;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-dropdown {
  cursor: pointer;
  display: flex;
  align-items: center;
}

.main-content {
  background: #f0f2f5;
}
</style>
