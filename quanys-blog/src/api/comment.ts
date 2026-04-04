import api from './index'
import type { ApiResponse, CommentRequest, CommentResponse, PageResponse } from './types'

export const commentApi = {
  /**
   * 获取文章的评论列表
   */
  getByArticle(articleId: number) {
    return api.get<ApiResponse<CommentResponse[]>>(`/comments/article/${articleId}`)
  },

  /**
   * 创建评论
   */
  create(data: CommentRequest) {
    return api.post<ApiResponse<CommentResponse>>('/comments', data)
  },

  /**
   * 删除评论（作者删除自己的）
   */
  delete(id: number) {
    return api.delete<ApiResponse<void>>(`/comments/${id}`)
  },

  /**
   * 获取所有评论（管理员）
   */
  getAll(params?: { page?: number; size?: number }) {
    return api.get<ApiResponse<PageResponse<CommentResponse>>>(`/comments/admin/all`, { params })
  },

  /**
   * 管理员删除评论
   */
  deleteByAdmin(id: number) {
    return api.delete<ApiResponse<void>>(`/comments/admin/${id}`)
  }
}
