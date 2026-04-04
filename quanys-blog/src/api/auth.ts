import api from './index'
import type { ApiResponse, LoginRequest, RegisterRequest, AuthResponse } from './types'

/**
 * 用户登录
 * @param data 登录请求参数
 * @returns Promise
 */
export function login(data: LoginRequest) {
  return api.post<ApiResponse<AuthResponse>>('/auth/login', data)
}

/**
 * 用户注册
 * @param data 注册请求参数
 * @returns Promise
 */
export function register(data: RegisterRequest) {
  return api.post<ApiResponse<AuthResponse>>('/auth/register', data)
}
