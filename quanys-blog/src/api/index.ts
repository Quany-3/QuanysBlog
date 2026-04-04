import axios from 'axios'
import type { AxiosInstance, AxiosError, InternalAxiosRequestConfig, AxiosResponse } from 'axios'
import type { ApiResponse } from './types'

// 创建 axios 实例
const instance = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
}) as AxiosInstance

// 请求拦截器：添加 JWT Token
instance.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem('token')
    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error: AxiosError) => {
    return Promise.reject(error)
  }
)

// 响应拦截器：统一处理错误
instance.interceptors.response.use(
  (response: AxiosResponse<ApiResponse>) => {
    return response
  },
  (error: AxiosError<ApiResponse>) => {
    if (error.response) {
      // 服务器返回错误状态码
      const httpStatus = error.response.status
      const { message } = error.response.data || {}

      // HTTP 401 表示认证失败（token 无效或过期）
      if (httpStatus === 401) {
        localStorage.removeItem('token')
        window.location.href = '/login'
      }

      // 其他错误，使用后端返回的 message
      return Promise.reject(new Error(message || '请求失败'))
    } else if (error.request) {
      // 请求已发送但没有收到响应
      return Promise.reject(new Error('网络连接失败，请检查网络'))
    } else {
      // 请求配置出错
      return Promise.reject(new Error('请求配置错误'))
    }
  }
)

export default instance
