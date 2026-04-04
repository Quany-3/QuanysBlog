<template>
  <el-container class="main-layout">
    <el-header class="header">
      <div class="header-content">
        <router-link to="/" class="logo">QuanysBlog</router-link>
        <el-menu mode="horizontal" :router="true" :default-active="activeIndex" class="nav-menu">
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/category">分类</el-menu-item>
          <el-menu-item index="/tag">标签</el-menu-item>
          <el-menu-item index="/search">搜索</el-menu-item>
        </el-menu>
        <div class="user-area">
          <template v-if="authStore.isLoggedIn">
            <el-dropdown @command="handleUserCommand">
              <span class="user-dropdown">
                <el-avatar :size="32" :src="authStore.userInfo?.avatar">
                  {{ authStore.userInfo?.username?.charAt(0) }}
                </el-avatar>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item :command="UserCommand.PROFILE">个人中心</el-dropdown-item>
                  <el-dropdown-item :command="UserCommand.ADMIN" v-if="authStore.isAdmin">管理后台</el-dropdown-item>
                  <el-dropdown-item :command="UserCommand.LOGOUT" divided>退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <el-button-group>
              <el-button @click="$router.push('/auth/login')">登录</el-button>
              <el-button @click="$router.push('/auth/register')">注册</el-button>
            </el-button-group>
          </template>
        </div>
      </div>
    </el-header>
    <el-main class="main-content">
      <router-view />
    </el-main>
    <el-footer class="footer">
      <p>&copy; {{ currentYear }} QuanysBlog. All rights reserved.</p>
    </el-footer>
  </el-container>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { ElMessage } from 'element-plus'

const UserCommand = {
  PROFILE: 'profile',
  ADMIN: 'admin',
  LOGOUT: 'logout'
} as const

const currentYear = new Date().getFullYear()

const route = useRoute()
const router = useRouter()
const authStore = useAuthStore()

const activeIndex = computed(() => {
  const path = route.path
  if (path.startsWith('/category')) return '/category'
  if (path.startsWith('/tag')) return '/tag'
  if (path.startsWith('/search')) return '/search'
  return '/'
})

const handleUserCommand = (command: string) => {
  switch (command) {
    case UserCommand.PROFILE:
      router.push('/profile')
      break
    case UserCommand.ADMIN:
      router.push('/admin')
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
.main-layout {
  min-height: 100vh;
}

.header {
  background: #fff;
  border-bottom: 1px solid #e6e6e6;
  padding: 0;
}

.header-content {
  display: flex;
  align-items: center;
  height: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.logo {
  font-size: 20px;
  font-weight: 600;
  color: #333;
  text-decoration: none;
  margin-right: 40px;
}

.nav-menu {
  flex: 1;
  border-bottom: none;
}

.user-area {
  display: flex;
  align-items: center;
}

.user-dropdown {
  cursor: pointer;
  display: flex;
  align-items: center;
}

.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.footer {
  background: #f5f5f5;
  text-align: center;
  padding: 20px;
  color: #666;
}
</style>
