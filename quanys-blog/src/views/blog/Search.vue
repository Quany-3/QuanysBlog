<template>
  <div class="search-page">
    <div class="search-box">
      <el-input
        v-model="keyword"
        placeholder="输入关键词搜索文章..."
        size="large"
        @keyup.enter="handleSearch"
      >
        <template #append>
          <el-button :icon="Search" @click="handleSearch" />
        </template>
      </el-input>
    </div>
    <div class="search-results">
      <p class="result-count" v-if="hasSearched">找到约 {{ articleStore.total }} 条结果</p>
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
        </el-card>
      </div>
      <el-empty v-if="hasSearched && !articleStore.loading && articleStore.articles.length === 0" description="未找到相关文章" />
      <el-empty v-if="!hasSearched" description="输入关键词搜索文章" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { Search } from '@element-plus/icons-vue'
import { useArticleStore } from '@/stores/article'

const router = useRouter()
const articleStore = useArticleStore()

const keyword = ref('')
const hasSearched = ref(false)

const handleSearch = () => {
  if (keyword.value.trim()) {
    hasSearched.value = true
    articleStore.searchArticles(keyword.value)
  }
}

const goToArticle = (id: number) => {
  router.push(`/article/${id}`)
}
</script>

<style scoped>
.search-page {
  padding: 20px 0;
}

.search-box {
  max-width: 600px;
  margin: 0 auto 40px;
}

.result-count {
  color: #999;
  margin-bottom: 20px;
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
</style>
