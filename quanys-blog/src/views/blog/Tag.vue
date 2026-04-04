<template>
  <div class="tag-page">
    <!-- 标签列表视图 -->
    <template v-if="!slug">
      <div class="page-header">
        <h1>标签</h1>
        <p>按标签浏览文章</p>
      </div>
      <div v-loading="tagStore.loading" class="tag-list">
        <el-tag
          v-for="tagItem in tagStore.tags"
          :key="tagItem.id"
          :color="tagItem.color"
          size="large"
          class="tag-item"
          @click="goToTag(tagItem.slug)"
        >
          {{ tagItem.name }}
        </el-tag>
        <el-empty v-if="!tagStore.loading && tagStore.tags.length === 0" description="暂无标签" />
      </div>
    </template>

    <!-- 标签文章列表视图 -->
    <template v-else>
      <div class="page-header">
        <h1>{{ tagName }}</h1>
        <p>标签文章</p>
      </div>
      <div v-loading="articleStore.loading" class="article-list">
        <el-card
          v-for="article in articleStore.articles"
          :key="article.id"
          class="article-card"
          shadow="hover"
          @click="goToArticle(article.id)"
        >
          <template #header>
            <span>{{ article.title }}</span>
          </template>
          <p>{{ article.summary || article.content.slice(0, 100) + '...' }}</p>
          <template #footer>
            <div class="card-footer">
              <span>{{ article.authorUsername }}</span>
              <span>{{ article.publishedAt }}</span>
            </div>
          </template>
        </el-card>
        <el-empty v-if="!articleStore.loading && articleStore.articles.length === 0" description="该标签下暂无文章" />
      </div>
    </template>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useTagStore } from '@/stores/tag'
import { useArticleStore } from '@/stores/article'

const route = useRoute()
const router = useRouter()
const tagStore = useTagStore()
const articleStore = useArticleStore()

const slug = computed(() => route.params.slug as string | undefined)
const tagName = computed(() => {
  const tagItem = tagStore.tags.find(t => t.slug === slug.value)
  return tagItem?.name || slug.value || ''
})

const goToTag = (tagSlug: string) => {
  router.push(`/tag/${tagSlug}`)
}

const goToArticle = (id: number) => {
  router.push(`/article/${id}`)
}

const loadTagArticles = () => {
  if (slug.value && tagStore.tags.length > 0) {
    const tagItem = tagStore.tags.find(t => t.slug === slug.value)
    if (tagItem) {
      articleStore.fetchByTag(tagItem.id)
    }
  }
}

watch([slug, () => tagStore.tags.length], () => {
  if (slug.value) {
    loadTagArticles()
  }
})

onMounted(async () => {
  await tagStore.fetchTags()
  if (slug.value) {
    loadTagArticles()
  }
})
</script>

<style scoped>
.tag-page {
  padding: 20px 0;
}

.page-header {
  text-align: center;
  margin-bottom: 40px;
}

.page-header h1 {
  font-size: 32px;
  margin-bottom: 10px;
}

.page-header p {
  color: #666;
}

.tag-list {
  min-height: 200px;
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  justify-content: center;
}

.tag-item {
  cursor: pointer;
  padding: 10px 20px;
  font-size: 16px;
}

.article-list {
  min-height: 200px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.article-card {
  cursor: pointer;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  color: #999;
  font-size: 14px;
}
</style>
