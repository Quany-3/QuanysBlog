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
 * 分页响应（Spring Data Page 格式）
 */
export interface PageResponse<T> {
  content: T[]
  page: {
    size: number
    number: number
    totalElements: number
    totalPages: number
  }
}

/**
 * 用户角色
 */
export const UserRole = {
  ADMIN: 'ADMIN',
  USER: 'USER'
} as const

export type UserRoleType = typeof UserRole[keyof typeof UserRole]

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
  avatar?: string
}

/**
 * 用户信息
 */
export interface UserInfo {
  id: number
  username: string
  email: string
  avatar?: string
  bio?: string
  role: string
}

// ============ Article Types ============

export interface TagSimple {
  id: number
  name: string
  slug: string
  color?: string
}

export interface ArticleRequest {
  title: string
  content: string
  summary?: string
  coverImage?: string
  categoryId?: number
  tagIds?: number[]
  isFeatured?: boolean
  isPublished?: boolean
}

export interface ArticleResponse {
  id: number
  title: string
  content: string
  summary: string
  coverImage: string
  authorUsername: string
  authorAvatar: string
  categoryId: number
  categoryName: string
  tags: TagSimple[]
  viewCount: number
  likeCount: number
  commentCount: number
  isFeatured: boolean
  isPublished: boolean
  publishedAt: string
  createdAt: string
  updatedAt: string
  liked?: boolean
}

// ============ Like Types ============

export interface LikeResponse {
  liked: boolean
  likeCount: number
}

// ============ Category Types ============

export interface CategoryRequest {
  name: string
  slug: string
  description?: string
  sort?: number
}

export interface CategoryResponse {
  id: number
  name: string
  slug: string
  description: string
  sort: number
  createdAt: string
  updatedAt: string
}

// ============ Tag Types ============

export interface TagRequest {
  name: string
  slug: string
  color?: string
}

export interface TagResponse {
  id: number
  name: string
  slug: string
  color: string
  createdAt: string
}

// ============ Comment Types ============

export interface CommentRequest {
  articleId: number
  content: string
  parentId?: number
}

export interface CommentResponse {
  id: number
  content: string
  authorUsername: string
  authorAvatar: string
  articleId: number
  articleTitle: string
  parentId: number
  children: CommentResponse[]
  status: string
  createdAt: string
  updatedAt: string
}
