<template>
  <div class="home">
    <HelloWorld msg="这是系统的主页面"/>
    <h2>负责人：王治远</h2>
    <div v-if="authenticated">
      <p>账号已登陆</p>
      <toLogout />
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'
import toLogout from '../components/toLogout.vue';

export default {
  name: 'HomeView',
  components: {
    HelloWorld,
    toLogout,
  },
  computed: {
    authenticated() {
      return localStorage.getItem('jwt');
    }
  }
}
</script>

<style scoped>
.home {
  display: flex;
  flex-direction: column;
  text-align: center;
}
</style>