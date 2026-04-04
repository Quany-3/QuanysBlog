import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { TagRequest, TagResponse } from '@/api/types'
import { tagApi } from '@/api/tag'

export const useTagStore = defineStore('tag', () => {
  const tags = ref<TagResponse[]>([])
  const loading = ref(false)

  async function fetchTags() {
    loading.value = true
    try {
      const res = await tagApi.getAll()
      if (res.data.success) {
        tags.value = res.data.data
      }
    } finally {
      loading.value = false
    }
  }

  async function createTag(data: TagRequest) {
    const res = await tagApi.create(data)
    if (res.data.success) {
      tags.value.push(res.data.data)
    }
    return res
  }

  async function updateTag(id: number, data: TagRequest) {
    const res = await tagApi.update(id, data)
    if (res.data.success) {
      const index = tags.value.findIndex(t => t.id === id)
      if (index !== -1) {
        tags.value[index] = res.data.data
      }
    }
    return res
  }

  async function deleteTag(id: number) {
    const res = await tagApi.delete(id)
    if (res.data.success) {
      tags.value = tags.value.filter(t => t.id !== id)
    }
    return res
  }

  return {
    tags,
    loading,
    fetchTags,
    createTag,
    updateTag,
    deleteTag
  }
})
