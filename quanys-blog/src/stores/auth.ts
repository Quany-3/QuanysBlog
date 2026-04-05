import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { UserRole, type UserInfo } from '@/api/types'

const USER_INFO_KEY = 'userInfo'

export const useAuthStore = defineStore('auth', () => {
  // State - 从 localStorage 恢复
  const token = ref<string | null>(localStorage.getItem('token'))
  const userInfo = ref<UserInfo | null>(JSON.parse(localStorage.getItem(USER_INFO_KEY) || 'null'))

  // Getters
  const isLoggedIn = computed(() => !!token.value)
  const isAdmin = computed(() => userInfo.value?.role === UserRole.ADMIN)

  // Actions
  function setToken(newToken: string) {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  function setUserInfo(info: UserInfo) {
    userInfo.value = info
    localStorage.setItem(USER_INFO_KEY, JSON.stringify(info))
  }

  function logout() {
    token.value = null
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem(USER_INFO_KEY)
  }

  function updateUserInfo(partial: Partial<UserInfo>) {
    if (userInfo.value) {
      userInfo.value = { ...userInfo.value, ...partial }
      localStorage.setItem(USER_INFO_KEY, JSON.stringify(userInfo.value))
    }
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    isAdmin,
    setToken,
    setUserInfo,
    updateUserInfo,
    logout
  }
})
