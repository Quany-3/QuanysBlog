<template>
  <div class="article-detail-page">
    <el-page-header @back="goBack" content="文章详情" />
    <article class="article">
      <header class="article-header">
        <h1>文章标题</h1>
        <div class="meta">
          <span>作者：admin</span>
          <span>发布时间：2024-01-01</span>
          <span>浏览：100</span>
        </div>
        <div class="tags">
          <el-tag>标签1</el-tag>
          <el-tag>标签2</el-tag>
        </div>
      </header>
      <div class="article-content">
        <p>文章内容将在这里显示...</p>
      </div>
    </article>
    <section class="comments">
      <h3>评论</h3>
      <el-empty description="暂无评论" />
    </section>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const goBack = () => {
  router.back()
}
</script>

<style scoped>
.article-detail-page {
  padding: 20px 0;
}

.article {
  margin-top: 20px;
  background: #fff;
  padding: 30px;
  border-radius: 8px;
}

.article-header {
  border-bottom: 1px solid #eee;
  padding-bottom: 20px;
  margin-bottom: 20px;
}

.article-header h1 {
  font-size: 28px;
  margin-bottom: 15px;
}

.meta {
  display: flex;
  gap: 20px;
  color: #999;
  font-size: 14px;
  margin-bottom: 15px;
}

.tags {
  display: flex;
  gap: 10px;
}

.article-content {
  line-height: 1.8;
  color: #333;
}

.comments {
  margin-top: 30px;
  background: #fff;
  padding: 30px;
  border-radius: 8px;
}

.comments h3 {
  margin-bottom: 20px;
}
</style>
