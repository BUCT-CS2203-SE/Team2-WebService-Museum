<template>
  <div class="app-container">
    <top-navigate v-if="$route.meta.hasNav" />
    <router-view />
  </div>

</template>

<script setup>
import topNavigate from './components/topNavigate.vue';
</script>


<style scoped>
.app-container{
  display: flex;
  flex-direction: column;
  height: 100vh;  
}
</style>
