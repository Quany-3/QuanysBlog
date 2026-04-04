<template>
  <div class="article-create-page">
    <div class="page-header">
      <h2>写文章</h2>
      <el-button @click="$router.back()">取消</el-button>
      <el-button type="primary" @click="handleSave">发布</el-button>
    </div>
    <el-form :model="form" label-width="80px" class="article-form">
      <el-form-item label="标题">
        <el-input v-model="form.title" placeholder="请输入文章标题" />
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="form.categoryId" placeholder="请选择分类">
          <el-option label="技术" :value="1" />
          <el-option label="生活" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="标签">
        <el-select v-model="form.tagIds" multiple placeholder="请选择标签">
          <el-option label="JavaScript" :value="1" />
          <el-option label="Vue" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="内容">
        <el-input
          v-model="form.content"
          type="textarea"
          :rows="15"
          placeholder="请输入文章内容（支持 Markdown）"
        />
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const form = ref({
  title: '',
  categoryId: '',
  tagIds: [],
  content: ''
})

const handleSave = () => {
  console.log('save', form.value)
  ElMessage.success('文章发布成功')
  router.push('/admin/articles')
}
</script>

<style scoped>
.article-create-page {
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

.article-form {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
}
</style>
