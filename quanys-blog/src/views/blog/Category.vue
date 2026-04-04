<template>
  <div class="category-page">
    <!-- 分类列表视图 -->
    <template v-if="!slug">
      <div class="page-header">
        <h1>分类</h1>
        <p>按分类浏览文章</p>
      </div>
      <div v-loading="categoryStore.loading" class="category-list">
        <el-card
          v-for="cat in categoryStore.categories"
          :key="cat.id"
          class="category-card"
          shadow="hover"
          @click="goToCategory(cat.slug)"
        >
          <div class="category-info">
            <h3>{{ cat.name }}</h3>
            <p>{{ cat.description || '暂无描述' }}</p>
          </div>
        </el-card>
        <el-empty v-if="!categoryStore.loading && categoryStore.categories.length === 0" description="暂无分类" />
      </div>
    </template>

    <!-- 分类文章列表视图 -->
    <template v-else>
      <div class="page-header">
        <h1>{{ categoryName }}</h1>
        <p>分类文章</p>
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
        <el-empty v-if="!articleStore.loading && articleStore.articles.length === 0" description="该分类下暂无文章" />
      </div>
    </template>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useCategoryStore } from '@/stores/category'
import { useArticleStore } from '@/stores/article'

const route = useRoute()
const router = useRouter()
const categoryStore = useCategoryStore()
const articleStore = useArticleStore()

const slug = computed(() => route.params.slug as string | undefined)
const categoryName = computed(() => {
  const cat = categoryStore.categories.find(c => c.slug === slug.value)
  return cat?.name || slug.value || ''
})

const goToCategory = (catSlug: string) => {
  router.push(`/category/${catSlug}`)
}

const goToArticle = (id: number) => {
  router.push(`/article/${id}`)
}

const loadCategoryArticles = () => {
  if (slug.value && categoryStore.categories.length > 0) {
    const cat = categoryStore.categories.find(c => c.slug === slug.value)
    if (cat) {
      articleStore.fetchByCategory(cat.id)
    }
  }
}

watch([slug, () => categoryStore.categories.length], () => {
  if (slug.value) {
    loadCategoryArticles()
  }
})

onMounted(async () => {
  await categoryStore.fetchCategories()
  if (slug.value) {
    loadCategoryArticles()
  }
})
</script>

<style scoped>
.category-page {
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

.category-list {
  min-height: 200px;
}

.category-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.category-card {
  cursor: pointer;
}

.category-info h3 {
  margin-bottom: 10px;
}

.category-info p {
  color: #666;
  font-size: 14px;
}
</style>
