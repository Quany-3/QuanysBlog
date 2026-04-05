import api from './index'
import type { ApiResponse } from './types'

/**
 * 上传头像
 * @param file 头像文件 (MultipartFile)
 * @returns Promise<avatarUrl>
 */
export function uploadAvatar(file: File) {
  const formData = new FormData()
  formData.append('file', file)
  return api.post<ApiResponse<string>>('/user/avatar', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
