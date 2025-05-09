<template>
    <top-navigate v-if="$route.meta.hasNav" />
    <router-view />
</template>

<script setup>
import topNavigate from './components/topNavigate.vue';
</script>