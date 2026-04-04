<template>
  <div class="category-page">
    <div class="page-header">
      <h1>分类</h1>
      <p>按分类浏览文章</p>
    </div>
    <div class="category-list">
      <el-card v-for="i in 6" :key="i" class="category-card" shadow="hover">
        <div class="category-info">
          <h3>分类 {{ i }}</h3>
          <p>该分类下的文章描述...</p>
          <span class="count">10 篇文章</span>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
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
  margin-bottom: 15px;
}

.count {
  color: #999;
  font-size: 12px;
}
</style>
