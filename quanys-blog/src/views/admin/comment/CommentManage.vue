<template>
  <div class="comment-manage-page">
    <div class="page-header">
      <h2>评论管理</h2>
    </div>
    <el-table :data="comments" stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="content" label="内容" />
      <el-table-column prop="author" label="作者" width="120" />
      <el-table-column prop="articleTitle" label="文章" />
      <el-table-column prop="createdAt" label="时间" width="180" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 'APPROVED' ? 'success' : 'warning'">
            {{ row.status === 'APPROVED' ? '已通过' : '待审核' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="success" link @click="handleApprove(row)">通过</el-button>
          <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const comments = ref([
  {
    id: 1,
    content: '这是一条评论内容',
    author: '用户1',
    articleTitle: '文章标题1',
    createdAt: '2024-01-01 12:00:00',
    status: 'APPROVED'
  },
  {
    id: 2,
    content: '这是另一条评论',
    author: '用户2',
    articleTitle: '文章标题2',
    createdAt: '2024-01-02 12:00:00',
    status: 'PENDING'
  }
])

const handleApprove = (row: any) => {
  console.log('approve', row)
}

const handleDelete = (row: any) => {
  console.log('delete', row)
}
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
