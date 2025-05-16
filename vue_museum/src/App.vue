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
.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #ffffff;
}
</style>
