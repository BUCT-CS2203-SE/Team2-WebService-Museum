<template>
  <div class="about">
    <h1>This is an about page</h1>
    <p>以下是获取到的用户名:</p>
    <ul v-if="users.length">
      <li v-for="(user, index) in users" :key="index">{{ user }}</li>
    </ul>
  </div>
</template>

<script>
import { getUserList } from '@/api/userinfo';

export default {
  name: 'AboutView',
  data() {
    return {
      users: []
    };
  },
  created() {
    getUserList()
      .then(users => {
        this.users = users;
      })
      .catch(error => {
        console.error("请求用户列表失败：", error.message);
      });
  }
};
</script>

<style scoped>
h1 {
  font-size: 60px;
  color: brown;
  display: flex;
  flex-direction: column;
}
</style>

