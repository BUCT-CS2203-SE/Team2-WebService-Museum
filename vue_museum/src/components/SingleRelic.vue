<!-- 此组件用于展示文物图片 -->

<template>
  <div class="relic_pos">
    <div class="lay" @click="goDetail">
      <img
        class="tupian"
        :src="imgSrc"
        :alt="props.id"
        @error="onImgError"
      />
      <label class="tlt">{{ title }}</label>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, defineProps } from 'vue'
import logo from '@/assets/logo.png'
import { useRouter } from 'vue-router'

const props = defineProps({
  id:  { type: Number, required: true },
  src: { type: String, required: true },
  title: { type: String, required: true }
})

// 响应式图片地址
const imgSrc = ref(props.src)

// 当 props.src 改变时更新图片
watch(() => props.src, (newSrc) => {
  imgSrc.value = newSrc
})

// 图片加载失败时，使用默认失败图
const onImgError = () => {
  console.log("加载图片失败!");
  imgSrc.value = logo; // 替换为你项目中存在的默认图路径
}
const router = useRouter();
// 点击当前项，跳转并传递数字型 id
function goDetail() {
  router.push({
    path:  '/relic',
    query: { id: props.id }  // 这里传的就是数字
  })
}
</script>

<style scoped>
.relic_pos {
  height: 240px;
  width: 240px;
  background-color: gainsboro;
  border-radius: 8px;
  align-content: center;
  align-items: center;
  justify-content: center;
  padding: 6px 0px;
}
.tupian {
  width: 200px;
  height: 200px;
  object-fit: cover;
  border-radius: 4px;
  transition: transform 0.3s ease;
  cursor: pointer;  
}
.tupian:hover {
  transform: scale(1.15);
}
.relic_pos .lay {
  display: grid;
  grid-template-rows: 5fr 1fr;
  align-items: center;
  justify-content: center;
  gap: 8px;
  overflow: hidden;
}
.relic_pos .tlt {
  font-size: large;
  text-align: center;
  cursor: pointer;
}
</style>
