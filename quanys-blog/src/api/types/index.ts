/**
 * API 统一响应格式
 */
export interface ApiResponse<T = any> {
  success: boolean
  message: string
  data: T
  code: number
}

/**
 * 登录请求
 */
export interface LoginRequest {
  username: string
  password: string
}

/**
 * 注册请求
 */
export interface RegisterRequest {
  username: string
  password: string
  email: string
}

/**
 * 认证响应
 */
export interface AuthResponse {
  token: string
  username: string
  email: string
  role: string
}

/**
 * 用户信息
 */
export interface UserInfo {
  id: number
  username: string
  email: string
  avatar?: string
  role: string
}
