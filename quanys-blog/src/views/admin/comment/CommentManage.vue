<template>
  <div class="comment-manage-page">
    <div class="page-header">
      <h2>评论管理</h2>
    </div>
    <el-table :data="commentStore.comments" stripe v-loading="commentStore.loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="content" label="内容" />
      <el-table-column prop="authorUsername" label="作者" width="120" />
      <el-table-column prop="articleTitle" label="文章" />
      <el-table-column prop="createdAt" label="时间" width="180" />
      <el-table-column label="操作" width="120">
        <template #default="{ row }">
          <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        :page-size="10"
        :total="commentStore.total"
        layout="total, prev, pager, next"
        @current-change="handlePageChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useCommentStore } from '@/stores/comment'

const commentStore = useCommentStore()
const currentPage = ref(1)

const loadComments = (page = 0) => {
  commentStore.fetchAllComments({ page, size: 10 })
}

const handlePageChange = (page: number) => {
  loadComments(page - 1)
}

const handleDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要删除这条评论吗？', '提示', { type: 'warning' })
    const res = await commentStore.deleteCommentByAdmin(row.id)
    if (res.data.success) {
      ElMessage.success('删除成功')
    } else {
      ElMessage.error(res.data.message || '删除失败')
    }
  } catch {
    // 用户取消
  }
}

onMounted(() => {
  loadComments()
})
</script>

<style scoped>
.comment-manage-page {
  padding: 20px;
}

.page-header {
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
