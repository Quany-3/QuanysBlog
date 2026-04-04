import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { CommentResponse } from '@/api/types'
import { commentApi } from '@/api/comment'

export const useCommentStore = defineStore('comment', () => {
  const comments = ref<CommentResponse[]>([])
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

  return {
    comments,
    loading,
    fetchComments,
    createComment,
    deleteComment
  }
})
