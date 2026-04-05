<template>
  <div class="profile-page">
    <el-page-header @back="goBack" content="个人中心" />
    <el-card>
      <el-form :model="form" label-width="100px" class="profile-form">
        <el-form-item label="用户名">
          <el-input v-model="form.username" disabled />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="个人简介">
          <el-input v-model="form.bio" type="textarea" :rows="4" maxlength="500" show-word-limit />
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
            class="avatar-uploader"
            :show-file-list="false"
            :auto-upload="false"
            :on-change="handleAvatarChange"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="form.avatar" :src="form.avatar" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>
          <div class="avatar-tip">支持 JPEG、PNG、GIF、WebP，大小不超过 2MB</div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSave" :loading="saving">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onUnmounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { uploadAvatar } from '@/api/storage'
import { getProfile, updateProfile } from '@/api/user'

const router = useRouter()
const authStore = useAuthStore()

const form = ref({
  username: '',
  email: '',
  bio: '',
  avatar: ''
})

const saving = ref(false)
const pendingAvatarFile = ref<File | null>(null)
const currentAvatarBlobUrl = ref<string | null>(null)

const mapToForm = (user: { username: string; email?: string | null; bio?: string | null; avatar?: string | null }) => {
  form.value = {
    username: user.username,
    email: user.email || '',
    bio: user.bio || '',
    avatar: user.avatar || ''
  }
}

const loadUserInfo = async () => {
  try {
    const response = await getProfile()
    mapToForm(response.data.data)
  } catch (error) {
    console.error('获取用户信息失败:', error)
    if (authStore.userInfo) {
      mapToForm(authStore.userInfo)
    }
  }
}

loadUserInfo()

// 返回上一页
const goBack = () => {
  router.back()
}

// 头像选择后的回调
const handleAvatarChange = (file: any) => {
  // 释放之前的 blob URL
  if (currentAvatarBlobUrl.value) {
    URL.revokeObjectURL(currentAvatarBlobUrl.value)
  }
  pendingAvatarFile.value = file.raw
  currentAvatarBlobUrl.value = URL.createObjectURL(file.raw)
  form.value.avatar = currentAvatarBlobUrl.value
}

// 组件卸载时释放 blob URL
onUnmounted(() => {
  if (currentAvatarBlobUrl.value) {
    URL.revokeObjectURL(currentAvatarBlobUrl.value)
  }
})

// 上传前校验
const beforeAvatarUpload = (file: File) => {
  const isTypeAllowed = ['image/jpeg', 'image/png', 'image/gif', 'image/webp'].includes(file.type)
  const isSizeAllowed = file.size / 1024 / 1024 < 2

  if (!isTypeAllowed) {
    ElMessage.error('仅支持 JPEG、PNG、GIF、WebP 格式')
    return false
  }
  if (!isSizeAllowed) {
    ElMessage.error('头像大小不能超过 2MB')
    return false
  }
  return true
}

const handleSave = async () => {
  saving.value = true
  try {
    // 1. 如果有新头像需要先上传
    if (pendingAvatarFile.value) {
      const response = await uploadAvatar(pendingAvatarFile.value)
      form.value.avatar = response.data.data
      pendingAvatarFile.value = null
    }

    // 2. 更新用户资料
    await updateProfile({
      email: form.value.email,
      bio: form.value.bio,
      avatar: form.value.avatar
    })

    // 3. 更新本地存储的用户信息
    authStore.updateUserInfo({
      avatar: form.value.avatar,
      email: form.value.email
    })

    ElMessage.success('保存成功')
  } catch (error: any) {
    ElMessage.error(error.message || '保存失败')
  } finally {
    saving.value = false
  }
}
</script>

<style scoped>
.profile-page {
  padding: 20px 0;
}

.profile-form {
  max-width: 600px;
  margin-top: 20px;
}

.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 100px;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.avatar-uploader:hover {
  border-color: #409eff;
}

.avatar {
  width: 100px;
  height: 100px;
  object-fit: cover;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
}

.avatar-tip {
  margin-top: 8px;
  font-size: 12px;
  color: #999;
}
</style>
