<template>
  <div class="detail-container">
    <!-- 左侧：轮播缩略图 -->
    <div class="detail-image">
      <!-- 左箭头 -->
      <button 
        class="carousel-btn left" 
        @click="prevImage" 
        :disabled="currentIndex <= 0"
      >‹</button>

      <img
        :key="currentIndex"       
        :src="currentImage"
        alt="文物缩略"
        @click="openZoom"
      />

      <!-- 右箭头 -->
      <button 
        class="carousel-btn right" 
        @click="nextImage" 
        :disabled="currentIndex >= images.length - 1"
      >›</button>

      <!-- 下方小圆点指示 -->
      <div class="dots">
        <span
          v-for="(img, idx) in images"
          :key="idx"
          :class="{ 'dot': true, active: idx === currentIndex }"
          @click="goTo(idx)"
        />
      </div>
    </div>

    <!-- 右侧：文物信息 -->
    <div class="detail-info">
      <h1>{{ props.id }}</h1>
      <h2 class="info-title">{{ relic.name }}</h2>
      <p class="info-era">年代：{{ relic.era }}</p>
      <div class="info-desc">
        <h3>详细介绍</h3>
        <p>{{ relic.description }}</p>
      </div>
    </div>
  </div>

  <!-- 放大镜模态层 -->
  <div 
    v-if="zoomVisible" 
    class="zoom-overlay" 
    @click.self="closeZoom"
  >
    <img
      class="zoom-img"
      :src="currentImage"
      :style="zoomStyle"
      @wheel.prevent="onWheel"
      @mousedown.prevent="startDrag"
    />
    <button class="zoom-close" @click="closeZoom">✕</button>
  </div>
</template>

<script setup>
import { ref, reactive, computed, defineProps } from 'vue'

const props = defineProps({
  id: { type: Number, required: true }
})

// 模拟获取多张图，真实项目从后端请求
const relic = {
  images: [
    require('@/assets/logo.png'),
    require('@/assets/logo.png'),
    require('@/assets/logo.png'),
    // require("C:/Users/lcx/Pictures/Screenshots/屏幕截图 2024-11-08 192429.png"),
    "https://www.njmuseum.com/files/nb/collection/modify/2021/09/28/5%EF%BC%9A3419-B-01.jpg"
  ],
  name: '文物示例',
  era: '唐代',
  description: '这是文物详细介绍……'
}

const images = relic.images
const currentIndex = ref(0)
const currentImage = computed(() => images[currentIndex.value])

// 切换函数
function prevImage() {
  if (currentIndex.value > 0) currentIndex.value--
}
function nextImage() {
  if (currentIndex.value < images.length - 1) currentIndex.value++
}
function goTo(idx) {
  currentIndex.value = idx
}

// 放大镜状态
const zoomVisible = ref(false)
const zoomState = reactive({
  scale: 1,
  offsetX: 0,
  offsetY: 0,
  dragging: false,
  dragStartX: 0,
  dragStartY: 0
})

function openZoom() {
  zoomState.scale = 1
  zoomState.offsetX = 0
  zoomState.offsetY = 0
  zoomVisible.value = true
}
function closeZoom() {
  zoomVisible.value = false
}
function onWheel(e) {
  const d = e.deltaY > 0 ? -0.1 : 0.1
  let s = zoomState.scale + d
  zoomState.scale = Math.min(Math.max(s, 0.5), 5)
}
function startDrag(e) {
  zoomState.dragging = true
  zoomState.dragStartX = e.clientX - zoomState.offsetX
  zoomState.dragStartY = e.clientY - zoomState.offsetY
  const onMove = evt => {
    zoomState.offsetX = evt.clientX - zoomState.dragStartX
    zoomState.offsetY = evt.clientY - zoomState.dragStartY
  }
  const onUp = () => {
    zoomState.dragging = false
    window.removeEventListener('mousemove', onMove)
    window.removeEventListener('mouseup', onUp)
  }
  window.addEventListener('mousemove', onMove)
  window.addEventListener('mouseup', onUp)
}
const zoomStyle = computed(() => ({
  transform: `scale(${zoomState.scale}) translate(${zoomState.offsetX / zoomState.scale}px, ${zoomState.offsetY / zoomState.scale}px)`,
  cursor: zoomState.dragging ? 'grabbing' : 'grab'
}))
</script>

<style scoped>
.detail-container {
  display: flex;
  align-items: flex-start;
  gap: 32px;
  padding: 24px;
}
.detail-image {
  position: relative;
  width: 500px;
  height: 500px;
  background: #f0f8ff;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  border-radius: 8px;
}
.detail-image img {
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  cursor: zoom-in;
  transition: box-shadow .3s;
}
.detail-image img:hover {
  box-shadow: 0 2px 12px rgba(0,0,0,0.2);
}
/* 箭头按钮 */
.carousel-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  font-size: 4em;
  padding: 2px;
  cursor: pointer;
  z-index: 2;
  color: #409eff;
}
.carousel-btn.left { left: 8px; }
.carousel-btn.right { right: 8px; }
.carousel-btn:disabled { opacity: 0.3; cursor: not-allowed; }

/* 分页小点 */
.dots {
  position: absolute;
  bottom: 8px;
  display: flex;
  gap: 8px;
}
.dot {
  width: 8px;
  height: 8px;
  background: #ccc;
  border-radius: 50%;
  cursor: pointer;
}
.dot.active { background: #409eff; }

/* 放大镜模态层 */
.zoom-overlay {
  position: fixed; top: 0; left: 0; right: 0; bottom: 0;
  background: rgba(0,0,0,0.85);
  display: flex; justify-content: center; align-items: center;
  z-index: 9999;
}
.zoom-img {
  max-width: 90%; max-height: 90%;
  transition: transform .1s;
}
.zoom-close {
  position: absolute; top: 24px; right: 24px;
  background: transparent; border: none;
  color: #fff; font-size: 2em; cursor: pointer;
}
</style>
