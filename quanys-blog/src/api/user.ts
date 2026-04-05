import api from './index'
import type { ApiResponse } from './types'

export interface UserProfile {
  id: number
  username: string
  email: string | null
  bio: string | null
  avatar: string | null
  role: string
  status: string
  createdAt: string
  updatedAt: string
}

/**
 * 获取当前用户资料
 * @returns Promise<UserProfile>
 */
export function getProfile() {
  return api.get<ApiResponse<UserProfile>>('/user/profile')
}

/**
 * 更新用户资料
 * @param data 更新数据 { email?, bio?, avatar? }
 * @returns Promise<UserProfile>
 */
export function updateProfile(data: { email?: string; bio?: string; avatar?: string }) {
  return api.put<ApiResponse<UserProfile>>('/user/profile', data)
}
