<template>
    <p>This is Logout</p>
    <form @submit.prevent="handleLogout">
       <button type="submit" class="logout-button">注销</button> 
    </form>
</template>

<script setup>
import { useRouter } from 'vue-router'
import service from '@/utils/request' // 你的 axios 实例
import Api from '@/api/Api';

const router = useRouter()

async function handleLogout() {
    await service.post(Api.url.logout,{});
    // 1. 清除本地存储中的 Token
    localStorage.removeItem('jwt');
    localStorage.removeItem('username');
    localStorage.removeItem('avatar');                  
    // 2. 取消 axios 请求头上的 Authorization
    delete service.defaults.headers.common['Authorization'];            
    // 3. 跳转回登录页
    router.push('/login');                                        
}
</script>

<style scoped>
.logout-button {
    padding: 8px 16px;
    background: transparent;
    border: 1px solid #333;
    border-radius: 4px;
    color: #333;
    cursor: pointer;
    transition: all 0.3s ease;
}

.logout-button:hover {
    background: rgba(255, 255, 255, 0.1);
}
</style>