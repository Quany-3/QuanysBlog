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
      <p class="result-count" v-if="hasSearched">找到约 {{ total }} 条结果</p>
      <div class="article-list" v-if="hasSearched">
        <el-card v-for="i in 5" :key="i" class="article-card" shadow="hover">
          <template #header>
            <span>搜索结果文章 {{ i }}</span>
          </template>
          <p>匹配的文章摘要内容...</p>
        </el-card>
      </div>
      <el-empty v-if="!hasSearched" description="输入关键词搜索文章" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'

const keyword = ref('')
const hasSearched = ref(false)
const total = ref(0)

const handleSearch = () => {
  if (keyword.value.trim()) {
    hasSearched.value = true
    total.value = Math.floor(Math.random() * 100)
  }
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
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.article-card {
  cursor: pointer;
}
</style>
