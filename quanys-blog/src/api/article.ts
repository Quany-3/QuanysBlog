import api from './index'
import type { ApiResponse, ArticleRequest, ArticleResponse, LikeResponse, PageResponse } from './types'

export const articleApi = {
  /**
   * 分页获取文章列表
   */
  getList(params?: { page?: number; size?: number }) {
    return api.get<ApiResponse<PageResponse<ArticleResponse>>>('/articles', { params })
  },

  /**
   * 获取文章详情
   */
  getById(id: number) {
    return api.get<ApiResponse<ArticleResponse>>(`/articles/${id}`)
  },

  /**
   * 增加文章浏览量
   */
  incrementViewCount(id: number) {
    return api.post<ApiResponse<void>>(`/articles/${id}/view`)
  },

  /**
   * 点赞文章
   */
  likeArticle(id: number) {
    return api.post<ApiResponse<LikeResponse>>(`/articles/${id}/like`)
  },

  /**
   * 取消点赞文章
   */
  unlikeArticle(id: number) {
    return api.delete<ApiResponse<LikeResponse>>(`/articles/${id}/like`)
  },

  /**
   * 获取点赞状态
   */
  getLikeStatus(id: number) {
    return api.get<ApiResponse<LikeResponse>>(`/articles/${id}/like/status`)
  },

  /**
   * 创建文章
   */
  create(data: ArticleRequest) {
    return api.post<ApiResponse<ArticleResponse>>('/articles', data)
  },

  /**
   * 更新文章
   */
  update(id: number, data: ArticleRequest) {
    return api.put<ApiResponse<ArticleResponse>>(`/articles/${id}`, data)
  },

  /**
   * 删除文章
   */
  delete(id: number) {
    return api.delete<ApiResponse<void>>(`/articles/${id}`)
  },

  /**
   * 按分类查询文章
   */
  getByCategory(categoryId: number, params?: { page?: number; size?: number }) {
    return api.get<ApiResponse<PageResponse<ArticleResponse>>>(`/articles/category/${categoryId}`, { params })
  },

  /**
   * 按标签查询文章
   */
  getByTag(tagId: number, params?: { page?: number; size?: number }) {
    return api.get<ApiResponse<PageResponse<ArticleResponse>>>(`/articles/tag/${tagId}`, { params })
  },

  /**
   * 搜索文章
   */
  search(keyword: string, params?: { page?: number; size?: number }) {
    return api.get<ApiResponse<PageResponse<ArticleResponse>>>('/articles/search', { params: { q: keyword, ...params } })
  }
}
