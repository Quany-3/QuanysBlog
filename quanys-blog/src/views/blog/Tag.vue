<template>
  <div class="tag-page">
    <div class="page-header">
      <h1>标签</h1>
      <p>按标签浏览文章</p>
    </div>
    <div class="tag-list">
      <el-tag
        v-for="i in 12"
        :key="i"
        type="info"
        size="large"
        class="tag-item"
      >
        标签 {{ i }}
      </el-tag>
    </div>
  </div>
</template>

<script setup lang="ts">
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
</style>
