<template>
  <div class="article-create-page">
    <div class="page-header">
      <h2>写文章</h2>
      <el-button @click="$router.back()">取消</el-button>
      <el-button type="primary" @click="handlePublish" :loading="publishing">发布</el-button>
    </div>
    <el-form :model="form" label-width="80px" class="article-form" v-loading="loading">
      <el-form-item label="标题">
        <el-input v-model="form.title" placeholder="请输入文章标题" />
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="form.categoryId" placeholder="请选择分类">
          <el-option
            v-for="cat in categoryStore.categories"
            :key="cat.id"
            :label="cat.name"
            :value="cat.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="标签">
        <el-select v-model="form.tagIds" multiple placeholder="请选择标签">
          <el-option
            v-for="tag in tagStore.tags"
            :key="tag.id"
            :label="tag.name"
            :value="tag.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="内容">
        <el-input
          v-model="form.content"
          type="textarea"
          :rows="15"
          placeholder="请输入文章内容（支持 Markdown）"
        />
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useCategoryStore } from '@/stores/category'
import { useTagStore } from '@/stores/tag'
import { useArticleStore } from '@/stores/article'
import { articleApi } from '@/api/article'
import type { ArticleRequest } from '@/api/types'

const router = useRouter()
const categoryStore = useCategoryStore()
const tagStore = useTagStore()
const articleStore = useArticleStore()

const loading = ref(false)
const publishing = ref(false)

const form = ref<ArticleRequest>({
  title: '',
  content: '',
  categoryId: undefined,
  tagIds: [],
  isPublished: true
})

const handlePublish = async () => {
  if (!form.value.title || !form.value.content) {
    ElMessage.warning('请填写标题和内容')
    return
  }
  publishing.value = true
  try {
    const res = await articleApi.create(form.value)
    if (res.data.success) {
      ElMessage.success('文章发布成功')
      articleStore.fetchArticles({})
      router.push('/admin/articles')
    } else {
      ElMessage.error(res.data.message || '发布失败')
    }
  } finally {
    publishing.value = false
  }
}

onMounted(async () => {
  loading.value = true
  await Promise.all([
    categoryStore.fetchCategories(),
    tagStore.fetchTags()
  ])
  loading.value = false
})
</script>

<style scoped>
.article-create-page {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
}

.article-form {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
}
</style>
