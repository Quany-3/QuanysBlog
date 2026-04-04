import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { ArticleResponse } from '@/api/types'
import { articleApi } from '@/api/article'

export const useArticleStore = defineStore('article', () => {
  const articles = ref<ArticleResponse[]>([])
  const currentArticle = ref<ArticleResponse | null>(null)
  const total = ref(0)
  const page = ref(0)
  const size = ref(10)
  const loading = ref(false)

  async function fetchArticles(params?: { page?: number; size?: number }) {
    loading.value = true
    try {
      const res = await articleApi.getList(params)
      if (res.data.success) {
        articles.value = res.data.data.content
        total.value = res.data.data.totalElements
        page.value = res.data.data.number
        size.value = res.data.data.size
      }
    } finally {
      loading.value = false
    }
  }

  async function fetchArticleById(id: number) {
    loading.value = true
    try {
      const res = await articleApi.getById(id)
      if (res.data.success) {
        currentArticle.value = res.data.data
      }
    } finally {
      loading.value = false
    }
  }

  async function fetchByCategory(categoryId: number, params?: { page?: number; size?: number }) {
    loading.value = true
    try {
      const res = await articleApi.getByCategory(categoryId, params)
      if (res.data.success) {
        articles.value = res.data.data.content
        total.value = res.data.data.totalElements
        page.value = res.data.data.number
        size.value = res.data.data.size
      }
    } finally {
      loading.value = false
    }
  }

  async function fetchByTag(tagId: number, params?: { page?: number; size?: number }) {
    loading.value = true
    try {
      const res = await articleApi.getByTag(tagId, params)
      if (res.data.success) {
        articles.value = res.data.data.content
        total.value = res.data.data.totalElements
        page.value = res.data.data.number
        size.value = res.data.data.size
      }
    } finally {
      loading.value = false
    }
  }

  async function searchArticles(keyword: string, params?: { page?: number; size?: number }) {
    loading.value = true
    try {
      const res = await articleApi.search(keyword, params)
      if (res.data.success) {
        articles.value = res.data.data.content
        total.value = res.data.data.totalElements
        page.value = res.data.data.number
        size.value = res.data.data.size
      }
    } finally {
      loading.value = false
    }
  }

  return {
    articles,
    currentArticle,
    total,
    page,
    size,
    loading,
    fetchArticles,
    fetchArticleById,
    fetchByCategory,
    fetchByTag,
    searchArticles
  }
})
