<template>
  <div class="tag-manage-page">
    <div class="page-header">
      <h2>标签管理</h2>
      <el-button type="primary" @click="handleCreate">新建标签</el-button>
    </div>
    <el-table :data="tagStore.tags" stripe v-loading="tagStore.loading">
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
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useTagStore } from '@/stores/tag'
import type { TagRequest } from '@/api/types'

const tagStore = useTagStore()

const dialogVisible = ref(false)
const dialogTitle = ref('新建标签')
const form = ref<TagRequest>({
  name: '',
  slug: '',
  color: '#409eff'
})
const editingId = ref<number | null>(null)

const handleCreate = () => {
  dialogTitle.value = '新建标签'
  form.value = { name: '', slug: '', color: '#409eff' }
  editingId.value = null
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  dialogTitle.value = '编辑标签'
  form.value = { name: row.name, slug: row.slug, color: row.color }
  editingId.value = row.id
  dialogVisible.value = true
}

const handleDelete = async (row: any) => {
  try {
    await ElMessageBox.confirm('确定要删除这个标签吗？', '提示', { type: 'warning' })
    const res = await tagStore.deleteTag(row.id)
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
      res = await tagStore.updateTag(editingId.value, form.value)
    } else {
      res = await tagStore.createTag(form.value)
    }
    if (res.data.success) {
      ElMessage.success(editingId.value ? '更新成功' : '创建成功')
      dialogVisible.value = false
      tagStore.fetchTags()
    } else {
      ElMessage.error(res.data.message || '操作失败')
    }
  } catch {
    ElMessage.error('操作失败')
  }
}

onMounted(() => {
  tagStore.fetchTags()
})
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
