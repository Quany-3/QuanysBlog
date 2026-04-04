<template>
  <div class="home-page">
    <div class="page-header">
      <h1>欢迎来到 QuanysBlog</h1>
      <p>分享技术与生活</p>
      <div v-if="authStore.isAdmin" class="admin-entry">
        <el-button type="primary" @click="router.push('/admin')">进入管理后台</el-button>
      </div>
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
          <div class="card-header">
            <span>{{ article.title }}</span>
          </div>
        </template>
        <p>{{ article.summary || article.content.slice(0, 100) + '...' }}</p>
        <template #footer>
          <div class="card-footer">
            <span>{{ article.authorUsername }}</span>
            <span>{{ article.createdAt }}</span>
            <span>{{ article.viewCount }} 浏览</span>
          </div>
        </template>
      </el-card>
      <el-empty v-if="!articleStore.loading && articleStore.articles.length === 0" description="暂无文章" />
    </div>
    <div class="pagination" v-if="articleStore.total > 0">
      <el-pagination
        v-model:current-page="currentPage"
        :page-size="articleStore.size"
        :total="articleStore.total"
        layout="prev, pager, next"
        @current-change="handlePageChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useArticleStore } from '@/stores/article'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const articleStore = useArticleStore()
const authStore = useAuthStore()
const currentPage = ref(1)

const loadArticles = (page = 0) => {
  articleStore.fetchArticles({ page, size: 10 })
}

const handlePageChange = (page: number) => {
  loadArticles(page - 1)
}

const goToArticle = (id: number) => {
  router.push(`/article/${id}`)
}

onMounted(() => {
  loadArticles()
})
</script>

<style scoped>
.home-page {
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

.admin-entry {
  margin-top: 20px;
}

.article-list {
  min-height: 200px;
}

.article-card {
  cursor: pointer;
  margin-bottom: 20px;
}

.card-header {
  font-weight: 600;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  color: #999;
  font-size: 14px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}
</style>
