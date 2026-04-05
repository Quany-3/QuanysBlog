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
        <div v-if="article.tags && article.tags.length" class="article-tags">
          <el-tag v-for="tag in article.tags.slice(0, 3)" :key="tag.id" size="small">
            {{ tag.name }}
          </el-tag>
        </div>
        <template #footer>
          <div class="card-footer">
            <span><el-icon><User /></el-icon> {{ article.authorUsername }}</span>
            <span class="separator">|</span>
            <span><el-icon><Clock /></el-icon> {{ article.createdAt }}</span>
            <div class="stats">
              <span><el-icon><View /></el-icon> {{ article.viewCount }}</span>
              <span><el-icon><Star /></el-icon> {{ article.likeCount }}</span>
            </div>
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
import { User, Clock, View, Star } from '@element-plus/icons-vue'

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
  align-items: center;
  gap: 8px;
  color: #999;
  font-size: 14px;
}

.card-footer .separator {
  color: #ddd;
}

.card-footer .el-icon {
  margin-right: 2px;
}

.stats {
  margin-left: auto;
  display: flex;
  gap: 15px;
}

.article-tags {
  margin-top: 10px;
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}
</style>
