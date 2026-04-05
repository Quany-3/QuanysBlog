import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { CommentResponse } from '@/api/types'
import { commentApi } from '@/api/comment'

export const useCommentStore = defineStore('comment', () => {
  const comments = ref<CommentResponse[]>([])
  const total = ref(0)
  const loading = ref(false)

  async function fetchComments(articleId: number) {
    loading.value = true
    try {
      const res = await commentApi.getByArticle(articleId)
      if (res.data.success) {
        comments.value = res.data.data
      }
    } finally {
      loading.value = false
    }
  }

  async function fetchAllComments(params?: { page?: number; size?: number }) {
    loading.value = true
    try {
      const res = await commentApi.getAll(params)
      if (res.data.success) {
        comments.value = res.data.data.content
        total.value = res.data.data.page.totalElements
      }
    } finally {
      loading.value = false
    }
  }

  async function createComment(data: { articleId: number; content: string; parentId?: number }) {
    const res = await commentApi.create(data)
    if (res.data.success) {
      comments.value.push(res.data.data)
    }
    return res
  }

  async function deleteComment(id: number) {
    const res = await commentApi.delete(id)
    if (res.data.success) {
      comments.value = comments.value.filter(c => c.id !== id)
    }
    return res
  }

  async function deleteCommentByAdmin(id: number) {
    const res = await commentApi.deleteByAdmin(id)
    if (res.data.success) {
      comments.value = comments.value.filter(c => c.id !== id)
      total.value--
    }
    return res
  }

  return {
    comments,
    total,
    loading,
    fetchComments,
    fetchAllComments,
    createComment,
    deleteComment,
    deleteCommentByAdmin
  }
})
