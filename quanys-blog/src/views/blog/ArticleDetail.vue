<template>
  <div class="article-detail-page">
    <el-page-header @back="goBack" content="文章详情" />
    <div v-loading="articleStore.loading" class="article-container">
      <template v-if="articleStore.currentArticle">
        <article class="article">
          <header class="article-header">
            <h1>{{ articleStore.currentArticle.title }}</h1>
            <div class="meta">
              <span><el-icon><User /></el-icon> {{ articleStore.currentArticle.authorUsername }}</span>
              <span class="separator">|</span>
              <span><el-icon><Clock /></el-icon> {{ articleStore.currentArticle.createdAt }}</span>
              <span class="separator">|</span>
              <span><el-icon><View /></el-icon> {{ articleStore.currentArticle.viewCount }}</span>
              <span class="separator">|</span>
              <span class="like-wrapper">
                <el-button :type="liked ? 'primary' : 'default'" text @click="handleLike" :loading="likeLoading">
                  <el-icon><Star /></el-icon>
                  {{ articleStore.currentArticle.likeCount }}
                </el-button>
              </span>
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
          <div class="comment-form">
            <el-input
              v-model="commentContent"
              type="textarea"
              :rows="3"
              placeholder="请输入评论..."
            />
            <el-button type="primary" @click="handleSubmitComment" :loading="submitting">
              发布评论
            </el-button>
          </div>
          <div v-loading="commentStore.loading" class="comment-list">
            <div v-if="commentStore.comments.length === 0" class="no-comments">
              暂无评论
            </div>
            <div v-else v-for="comment in commentStore.comments" :key="comment.id" class="comment-item">
              <div class="comment-header">
                <img v-if="comment.authorAvatar" :src="comment.authorAvatar" class="comment-avatar" />
                <img v-else src="@/assets/default-avatar.svg" class="comment-avatar" />
                <span class="comment-author">{{ comment.authorUsername }}</span>
                <span class="comment-time">{{ comment.createdAt }}</span>
              </div>
              <div class="comment-content">{{ comment.content }}</div>
              <div v-if="comment.children && comment.children.length > 0" class="comment-children">
                <div v-for="child in comment.children" :key="child.id" class="comment-item child">
                  <div class="comment-header">
                    <img v-if="child.authorAvatar" :src="child.authorAvatar" class="comment-avatar" />
                    <img v-else src="@/assets/default-avatar.svg" class="comment-avatar" />
                    <span class="comment-author">{{ child.authorUsername }}</span>
                    <span class="comment-time">{{ child.createdAt }}</span>
                  </div>
                  <div class="comment-content">{{ child.content }}</div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </template>
      <el-empty v-if="!articleStore.loading && !articleStore.currentArticle" description="文章不存在" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useArticleStore } from '@/stores/article'
import { useCommentStore } from '@/stores/comment'
import { useAuthStore } from '@/stores/auth'
import { marked } from 'marked'
import { ElMessage } from 'element-plus'
import { articleApi } from '@/api/article'
import { Star, User, Clock, View } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const articleStore = useArticleStore()
const commentStore = useCommentStore()
const authStore = useAuthStore()

const articleId = computed(() => Number(route.params.id))
const commentContent = ref('')
const liked = ref(false)
const likeLoading = ref(false)

// 浏览量限制：30分钟内同文章只计一次
const VIEW_COUNT_KEY = 'article_viewed'
const VIEW_COUNT_WINDOW = 30 * 60 * 1000 // 30分钟

const shouldIncrementViewCount = (id: number): boolean => {
  const key = `${VIEW_COUNT_KEY}_${id}`
  const lastViewed = localStorage.getItem(key)
  if (!lastViewed) return true
  const elapsed = Date.now() - parseInt(lastViewed, 10)
  return elapsed > VIEW_COUNT_WINDOW
}

const recordViewCount = (id: number) => {
  const key = `${VIEW_COUNT_KEY}_${id}`
  localStorage.setItem(key, Date.now().toString())
}
const submitting = ref(false)

const renderedContent = computed(() => {
  if (!articleStore.currentArticle?.content) return ''
  return marked(articleStore.currentArticle.content)
})

const goBack = () => {
  router.back()
}

const handleLike = async () => {
  if (!authStore.isLoggedIn) {
    ElMessage.warning('请先登录')
    router.push('/auth/login')
    return
  }

  likeLoading.value = true
  try {
    const api = liked.value ? articleApi.unlikeArticle : articleApi.likeArticle
    const res = await api(articleId.value)
    if (res.data.success && articleStore.currentArticle) {
      articleStore.currentArticle.likeCount = res.data.data.likeCount
      liked.value = res.data.data.liked
    }
  } catch (error: any) {
    ElMessage.error(error.message || '操作失败')
  } finally {
    likeLoading.value = false
  }
}

const loadLikeStatus = async () => {
  if (!authStore.isLoggedIn) return
  try {
    const res = await articleApi.getLikeStatus(articleId.value)
    if (res.data.success) {
      liked.value = res.data.data.liked
    }
  } catch {
    // ignore
  }
}

const handleSubmitComment = async () => {
  if (!commentContent.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }
  if (!authStore.isLoggedIn) {
    ElMessage.warning('请先登录')
    router.push('/auth/login')
    return
  }
  submitting.value = true
  try {
    const res = await commentStore.createComment({
      articleId: articleId.value,
      content: commentContent.value
    })
    if (res.data.success) {
      ElMessage.success('评论发布成功')
      commentContent.value = ''
    } else {
      ElMessage.error(res.data.message || '评论发布失败')
    }
  } finally {
    submitting.value = false
  }
}

onMounted(async () => {
  if (articleId.value) {
    // 获取文章内容
    articleStore.fetchArticleById(articleId.value)
    commentStore.fetchComments(articleId.value)

    // 检查是否需要增加浏览量（30分钟内同文章只计一次）
    if (shouldIncrementViewCount(articleId.value)) {
      await articleApi.incrementViewCount(articleId.value)
      recordViewCount(articleId.value)
    }

    // 获取点赞状态
    loadLikeStatus()
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
  align-items: center;
  gap: 8px;
  color: #999;
  font-size: 14px;
  margin-bottom: 15px;
}

.meta .separator {
  color: #ddd;
}

.meta .el-icon {
  margin-right: 2px;
}

.like-wrapper {
  display: flex;
  align-items: center;
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

.comment-form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 30px;
}

.comment-form .el-button {
  align-self: flex-end;
}

.comment-list {
  margin-top: 20px;
}

.no-comments {
  color: #999;
  text-align: center;
  padding: 20px;
}

.comment-item {
  padding: 15px 0;
  border-bottom: 1px solid #eee;
}

.comment-item:last-child {
  border-bottom: none;
}

.comment-item.child {
  margin-left: 30px;
  padding: 10px 0;
  border-left: 2px solid #eee;
  padding-left: 15px;
}

.comment-header {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 8px;
}

.comment-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
}

.comment-author {
  font-weight: 600;
  color: #333;
}

.comment-time {
  color: #999;
  font-size: 12px;
}

.comment-content {
  color: #666;
  line-height: 1.6;
}

.comment-children {
  margin-top: 10px;
}
</style>
