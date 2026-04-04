import api from './index'
import type { ApiResponse, TagRequest, TagResponse } from './types'

export const tagApi = {
  /**
   * 获取所有标签
   */
  getAll() {
    return api.get<ApiResponse<TagResponse[]>>('/tags')
  },

  /**
   * 获取标签详情
   */
  getById(id: number) {
    return api.get<ApiResponse<TagResponse>>(`/tags/${id}`)
  },

  /**
   * 创建标签
   */
  create(data: TagRequest) {
    return api.post<ApiResponse<TagResponse>>('/tags', data)
  },

  /**
   * 更新标签
   */
  update(id: number, data: TagRequest) {
    return api.put<ApiResponse<TagResponse>>(`/tags/${id}`, data)
  },

  /**
   * 删除标签
   */
  delete(id: number) {
    return api.delete<ApiResponse<void>>(`/tags/${id}`)
  }
}
