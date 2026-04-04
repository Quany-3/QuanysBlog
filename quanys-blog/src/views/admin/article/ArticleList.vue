<template>
  <div class="article-list-page">
    <div class="page-header">
      <h2>文章管理</h2>
      <el-button type="primary" @click="$router.push('/admin/articles/create')">写文章</el-button>
    </div>
    <el-table :data="articleStore.articles" stripe v-loading="articleStore.loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="categoryName" label="分类" width="120" />
      <el-table-column prop="authorUsername" label="作者" width="120" />
      <el-table-column prop="publishedAt" label="发布时间" width="180" />
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        :page-size="articleStore.size"
        :total="articleStore.total"
        layout="total, prev, pager, next"
        @current-change="handlePageChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useArticleStore } from '@/stores/article'
import { articleApi } from '@/api/article'

const router = useRouter()
const articleStore = useArticleStore()
const currentPage = ref(1)

const loadArticles = (page = 0) => {
  articleStore.fetchArticles({ page, size: 10 })
}

const handlePageChange = (page: number) => {
  loadArticles(page - 1)
}

const handleEdit = (row: any) => {
  router.push(`/admin/articles/edit/${row.id}`)
}

const handleDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要删除这篇文章吗？', '提示', {
      type: 'warning'
    })
    const res = await articleApi.delete(row.id)
    if (res.data.success) {
      ElMessage.success('删除成功')
      loadArticles(articleStore.page)
    } else {
      ElMessage.error(res.data.message || '删除失败')
    }
  } catch {
    // 用户取消
  }
}

onMounted(() => {
  loadArticles()
})
</script>

<style scoped>
.article-list-page {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
