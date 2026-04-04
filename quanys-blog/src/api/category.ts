import api from './index'
import type { ApiResponse, CategoryRequest, CategoryResponse } from './types'

export const categoryApi = {
  /**
   * 获取所有分类
   */
  getAll() {
    return api.get<ApiResponse<CategoryResponse[]>>('/categories')
  },

  /**
   * 获取分类详情
   */
  getById(id: number) {
    return api.get<ApiResponse<CategoryResponse>>(`/categories/${id}`)
  },

  /**
   * 创建分类
   */
  create(data: CategoryRequest) {
    return api.post<ApiResponse<CategoryResponse>>('/categories', data)
  },

  /**
   * 更新分类
   */
  update(id: number, data: CategoryRequest) {
    return api.put<ApiResponse<CategoryResponse>>(`/categories/${id}`, data)
  },

  /**
   * 删除分类
   */
  delete(id: number) {
    return api.delete<ApiResponse<void>>(`/categories/${id}`)
  }
}
