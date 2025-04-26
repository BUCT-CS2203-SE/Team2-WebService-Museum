<template>
    <form @submit.prevent="login">
        <input v-model="u" placeholder="用户名" required />
        <input v-model="p" type="password" placeholder="密码" required />
        <button>登录</button>
    </form>
</template>

<script setup>
import { ref } from 'vue';
import service from '@/utils/request';
import { useRouter } from 'vue-router';

const u = ref(''), p = ref('');
const router = useRouter();

async function login() {
    //注意函数的使用
    const data = await service.post('/login', { username: u.value, password: p.value });
    localStorage.setItem('jwt', data.token);
    console.log("保存成功："+data.token);
    //登陆成功跳转路径
    router.push('/');
}
</script>