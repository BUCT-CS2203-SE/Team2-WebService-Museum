<template>
  <TopNavigate />
  <div class="home">
    <HelloWorld msg="这是系统的主页面"/>
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
import TopNavigate from '@/components/topNavigate.vue';

export default {
  name: 'HomeView',
  components: {
    HelloWorld,
    toLogout,
    TopNavigate
  },
  computed: {
    authenticated() {
      return localStorage.getItem('jwt');
    }
  }
}
</script>
