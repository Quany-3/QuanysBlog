<template>
  <div class="dashboard">
    <h2>仪表盘</h2>
    <el-row :gutter="20" class="stats-row">
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <div class="stat-icon"><Document /></div>
            <div class="stat-info">
              <p class="stat-value">{{ stats.articles }}</p>
              <p class="stat-label">文章数</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <div class="stat-icon"><Folder /></div>
            <div class="stat-info">
              <p class="stat-value">{{ stats.categories }}</p>
              <p class="stat-label">分类数</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <div class="stat-icon"><PriceTag /></div>
            <div class="stat-info">
              <p class="stat-value">{{ stats.tags }}</p>
              <p class="stat-label">标签数</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="stat-card">
            <div class="stat-icon"><ChatDotRound /></div>
            <div class="stat-info">
              <p class="stat-value">{{ stats.comments }}</p>
              <p class="stat-label">评论数</p>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="quick-actions">
      <el-col :span="24">
        <h3>快捷操作</h3>
      </el-col>
      <el-col :span="6">
        <el-button type="primary" @click="$router.push('/admin/articles/create')">写文章</el-button>
      </el-col>
      <el-col :span="6">
        <el-button @click="$router.push('/admin/categories')">管理分类</el-button>
      </el-col>
      <el-col :span="6">
        <el-button @click="$router.push('/admin/tags')">管理标签</el-button>
      </el-col>
      <el-col :span="6">
        <el-button @click="$router.push('/admin/comments')">管理评论</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Document, Folder, PriceTag, ChatDotRound } from '@element-plus/icons-vue'

const stats = ref({
  articles: 0,
  categories: 0,
  tags: 0,
  comments: 0
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.dashboard h2 {
  margin-bottom: 20px;
}

.stats-row {
  margin-bottom: 30px;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 15px;
}

.stat-icon {
  font-size: 48px;
  color: #409eff;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: 600;
  margin: 0;
}

.stat-label {
  color: #999;
  margin: 5px 0 0;
}

.quick-actions {
  margin-top: 30px;
}

.quick-actions h3 {
  margin-bottom: 15px;
}
</style>
