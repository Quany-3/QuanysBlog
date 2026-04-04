<template>
  <div class="tag-manage-page">
    <div class="page-header">
      <h2>标签管理</h2>
      <el-button type="primary" @click="handleCreate">新建标签</el-button>
    </div>
    <el-table :data="tags" stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="slug" label="Slug" />
      <el-table-column label="颜色" width="100">
        <template #default="{ row }">
          <el-tag :color="row.color">{{ row.name }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称">
          <el-input v-model="form.name" placeholder="请输入标签名称" />
        </el-form-item>
        <el-form-item label="Slug">
          <el-input v-model="form.slug" placeholder="请输入 slug" />
        </el-form-item>
        <el-form-item label="颜色">
          <el-color-picker v-model="form.color" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const tags = ref([
  { id: 1, name: 'JavaScript', slug: 'javascript', color: '#f7df1e' },
  { id: 2, name: 'Vue', slug: 'vue', color: '#42b883' },
  { id: 3, name: 'TypeScript', slug: 'typescript', color: '#3178c6' }
])

const dialogVisible = ref(false)
const dialogTitle = ref('新建标签')
const form = ref({
  id: null,
  name: '',
  slug: '',
  color: '#409eff'
})

const handleCreate = () => {
  dialogTitle.value = '新建标签'
  form.value = { id: null, name: '', slug: '', color: '#409eff' }
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  dialogTitle.value = '编辑标签'
  form.value = { ...row }
  dialogVisible.value = true
}

const handleDelete = (row: any) => {
  console.log('delete', row)
}

const handleSave = () => {
  console.log('save', form.value)
  dialogVisible.value = false
}
</script>

<style scoped>
.tag-manage-page {
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
</style>
