import api from './index'
import type { ApiResponse, CommentRequest, CommentResponse } from './types'

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
   * 删除评论
   */
  delete(id: number) {
    return api.delete<ApiResponse<void>>(`/comments/${id}`)
  }
}
