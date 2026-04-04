import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { CategoryRequest, CategoryResponse } from '@/api/types'
import { categoryApi } from '@/api/category'

export const useCategoryStore = defineStore('category', () => {
  const categories = ref<CategoryResponse[]>([])
  const loading = ref(false)

  async function fetchCategories() {
    loading.value = true
    try {
      const res = await categoryApi.getAll()
      if (res.data.success) {
        categories.value = res.data.data
      }
    } finally {
      loading.value = false
    }
  }

  async function createCategory(data: CategoryRequest) {
    const res = await categoryApi.create(data)
    if (res.data.success) {
      categories.value.push(res.data.data)
    }
    return res
  }

  async function updateCategory(id: number, data: CategoryRequest) {
    const res = await categoryApi.update(id, data)
    if (res.data.success) {
      const index = categories.value.findIndex(c => c.id === id)
      if (index !== -1) {
        categories.value[index] = res.data.data
      }
    }
    return res
  }

  async function deleteCategory(id: number) {
    const res = await categoryApi.delete(id)
    if (res.data.success) {
      categories.value = categories.value.filter(c => c.id !== id)
    }
    return res
  }

  return {
    categories,
    loading,
    fetchCategories,
    createCategory,
    updateCategory,
    deleteCategory
  }
})
