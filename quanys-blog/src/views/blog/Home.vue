<template>
  <div class="home-page">
    <div class="page-header">
      <h1>欢迎来到 QuanysBlog</h1>
      <p>分享技术与生活</p>
    </div>
    <div class="article-list">
      <el-card v-for="i in 5" :key="i" class="article-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <span>文章标题 {{ i }}</span>
          </div>
        </template>
        <p>这里是文章摘要内容...</p>
        <template #footer>
          <div class="card-footer">
            <span>2024-01-01</span>
            <span>作者</span>
          </div>
        </template>
      </el-card>
    </div>
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        :page-size="10"
        :total="100"
        layout="prev, pager, next"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const currentPage = ref(1)
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

.article-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.article-card {
  cursor: pointer;
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
