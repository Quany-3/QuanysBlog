<template>
  <div class="article-detail-page">
    <el-page-header @back="goBack" content="文章详情" />
    <div v-loading="articleStore.loading" class="article-container">
      <template v-if="articleStore.currentArticle">
        <article class="article">
          <header class="article-header">
            <h1>{{ articleStore.currentArticle.title }}</h1>
            <div class="meta">
              <span>作者：{{ articleStore.currentArticle.authorUsername }}</span>
              <span>发布时间：{{ articleStore.currentArticle.createdAt }}</span>
              <span>浏览：{{ articleStore.currentArticle.viewCount }}</span>
            </div>
            <div class="tags">
              <el-tag v-for="tag in articleStore.currentArticle.tags" :key="tag.id">
                {{ tag.name }}
              </el-tag>
            </div>
          </header>
          <div class="article-content" v-html="renderedContent"></div>
        </article>
        <section class="comments">
          <h3>评论</h3>
          <el-empty description="暂无评论" />
        </section>
      </template>
      <el-empty v-if="!articleStore.loading && !articleStore.currentArticle" description="文章不存在" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useArticleStore } from '@/stores/article'
import { marked } from 'marked'

const route = useRoute()
const router = useRouter()
const articleStore = useArticleStore()

const articleId = computed(() => Number(route.params.id))

const renderedContent = computed(() => {
  if (!articleStore.currentArticle?.content) return ''
  return marked(articleStore.currentArticle.content)
})

const goBack = () => {
  router.back()
}

onMounted(() => {
  if (articleId.value) {
    articleStore.fetchArticleById(articleId.value)
  }
})
</script>

<style scoped>
.article-detail-page {
  padding: 20px 0;
}

.article-container {
  min-height: 400px;
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
