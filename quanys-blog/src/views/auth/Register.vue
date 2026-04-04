<template>
  <div class="register-container">
    <div class="register-box">
      <h1 class="title">QuanysBlog</h1>
      <p class="subtitle">创建新账号</p>

      <form @submit.prevent="handleRegister" class="register-form">
        <div class="form-item">
          <label for="username">用户名</label>
          <input
            id="username"
            v-model="formData.username"
            type="text"
            placeholder="请输入用户名（3-50字符）"
            required
            minlength="3"
            maxlength="50"
          />
        </div>

        <div class="form-item">
          <label for="email">邮箱</label>
          <input
            id="email"
            v-model="formData.email"
            type="email"
            placeholder="请输入邮箱"
            required
          />
        </div>

        <div class="form-item">
          <label for="password">密码</label>
          <input
            id="password"
            v-model="formData.password"
            type="password"
            placeholder="请输入密码（6-100字符）"
            required
            minlength="6"
            maxlength="100"
          />
        </div>

        <div class="form-item">
          <label for="confirmPassword">确认密码</label>
          <input
            id="confirmPassword"
            v-model="confirmPassword"
            type="password"
            placeholder="请再次输入密码"
            required
          />
        </div>

        <div v-if="errorMessage" class="error-message">
          {{ errorMessage }}
        </div>

        <div v-if="successMessage" class="success-message">
          {{ successMessage }}
        </div>

        <button type="submit" class="submit-btn" :disabled="loading">
          {{ loading ? '注册中...' : '注册' }}
        </button>
      </form>

      <div class="footer">
        已有账号？<router-link to="/auth/login">立即登录</router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/api/auth'
import { useAuthStore } from '@/stores/auth'
import type { RegisterRequest } from '@/api/types'

const router = useRouter()
const authStore = useAuthStore()

const formData = ref<RegisterRequest>({
  username: '',
  password: '',
  email: ''
})

const confirmPassword = ref('')
const loading = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

async function handleRegister() {
  // 验证密码确认
  if (formData.value.password !== confirmPassword.value) {
    errorMessage.value = '两次输入的密码不一致'
    return
  }

  loading.value = true
  errorMessage.value = ''
  successMessage.value = ''

  try {
    const response = await register(formData.value)
    if (response.data.success) {
      successMessage.value = '注册成功！正在跳转...'
      // 保存 token
      authStore.setToken(response.data.data.token)
      // 延迟跳转
      setTimeout(() => {
        router.push('/')
      }, 1500)
    } else {
      errorMessage.value = response.data.message || '注册失败'
    }
  } catch (error: any) {
    errorMessage.value = error.message || '注册失败，请稍后重试'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-box {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
}

.title {
  font-size: 28px;
  font-weight: 600;
  text-align: center;
  color: #333;
  margin-bottom: 8px;
}

.subtitle {
  font-size: 14px;
  color: #666;
  text-align: center;
  margin-bottom: 30px;
}

.register-form {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.form-item {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-item label {
  font-size: 14px;
  color: #333;
  font-weight: 500;
}

.form-item input {
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
  transition: border-color 0.3s;
}

.form-item input:focus {
  border-color: #667eea;
  outline: none;
}

.error-message {
  padding: 10px;
  background: #fee;
  border: 1px solid #fcc;
  border-radius: 6px;
  color: #c00;
  font-size: 14px;
  text-align: center;
}

.success-message {
  padding: 10px;
  background: #efe;
  border: 1px solid #cfc;
  border-radius: 6px;
  color: #060;
  font-size: 14px;
  text-align: center;
}

.submit-btn {
  padding: 12px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 500;
  transition: opacity 0.3s;
  margin-top: 8px;
}

.submit-btn:hover:not(:disabled) {
  opacity: 0.9;
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.footer {
  margin-top: 20px;
  text-align: center;
  font-size: 14px;
  color: #666;
}

.footer a {
  color: #667eea;
  font-weight: 500;
}

.footer a:hover {
  text-decoration: underline;
}
</style>
