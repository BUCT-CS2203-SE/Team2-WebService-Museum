<template>
    <h1>This Search Page</h1>
    <ul>
      <li
        v-for="(item, index) in data"
        :key="index"
      >
        {{ item }}
      </li>
    </ul>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { getUserList } from '@/api/userinfo'
  
  const data = ref([])
  
  onMounted(async () => {
    // 假设 getUserList 返回的是一个简单的字符串数组
    data.value = await getUserList()
  })
  </script>  