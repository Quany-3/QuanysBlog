<template>
  <div class="category-manage-page">
    <div class="page-header">
      <h2>分类管理</h2>
      <el-button type="primary" @click="handleCreate">新建分类</el-button>
    </div>
    <el-table :data="categoryStore.categories" stripe v-loading="categoryStore.loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="slug" label="Slug" />
      <el-table-column prop="description" label="描述" />
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
          <el-input v-model="form.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="Slug">
          <el-input v-model="form.slug" placeholder="请输入 slug" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" />
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
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useCategoryStore } from '@/stores/category'
import type { CategoryRequest } from '@/api/types'

const categoryStore = useCategoryStore()

const dialogVisible = ref(false)
const dialogTitle = ref('新建分类')
const form = ref<CategoryRequest>({
  name: '',
  slug: '',
  description: ''
})
const editingId = ref<number | null>(null)

const handleCreate = () => {
  dialogTitle.value = '新建分类'
  form.value = { name: '', slug: '', description: '' }
  editingId.value = null
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  dialogTitle.value = '编辑分类'
  form.value = { name: row.name, slug: row.slug, description: row.description }
  editingId.value = row.id
  dialogVisible.value = true
}

const handleDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要删除这个分类吗？', '提示', { type: 'warning' })
    const res = await categoryStore.deleteCategory(row.id)
    if (res.data.success) {
      ElMessage.success('删除成功')
    } else {
      ElMessage.error(res.data.message || '删除失败')
    }
  } catch {
    // 用户取消
  }
}

const handleSave = async () => {
  if (!form.value.name || !form.value.slug) {
    ElMessage.warning('请填写名称和 slug')
    return
  }
  try {
    let res
    if (editingId.value) {
      res = await categoryStore.updateCategory(editingId.value, form.value)
    } else {
      res = await categoryStore.createCategory(form.value)
    }
    if (res.data.success) {
      ElMessage.success(editingId.value ? '更新成功' : '创建成功')
      dialogVisible.value = false
      categoryStore.fetchCategories()
    } else {
      ElMessage.error(res.data.message || '操作失败')
    }
  } catch {
    ElMessage.error('操作失败')
  }
}

onMounted(() => {
  categoryStore.fetchCategories()
})
</script>

<style scoped>
.category-manage-page {
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
