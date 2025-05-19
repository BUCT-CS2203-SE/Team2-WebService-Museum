<template>
  
  <a-page-header
    title="文物详情"
    :sub-title="relic.name"
    @back="goBack"
  />
  
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
        :disabled="currentIndex >= relic.images.length - 1"
      >›</button>
      <!-- 下方小圆点指示 -->
      <div class="dots">
        <span
          v-for="(img, idx) in relic.images"
          :key="idx"
          :class="{ 'dot': true, active: idx === currentIndex }"
          @click="goTo(idx)"
        />
      </div>
    </div>

    <!-- 右侧：文物信息 -->
    <div class="detailinfo-box">
      <div class="detail-info">
        <h2 class="info-title">{{ relic.name }}</h2>
        <p v-if="relic.era" class="info-era">年代：{{ relic.era }}</p>
        <p v-if="relic.type" class="info-era">类别： {{ relic.type }}</p>
        <p v-if="relic.atrist" class="info-era">作家： {{ relic.atrist }}</p>
        <p v-if="relic.credit" class="info-era">题词： {{ relic.credit }}</p>
        <p v-if="relic.material" class="info-era">材料： {{ relic.material }}</p>
        <p v-if="relic.scale" class="info-era">规模参数： {{ relic.scale }}</p>
        <div class="info-desc">
          <h3>详细介绍</h3>
          <p v-if="relic.description">{{ relic.description }}</p>
          <p v-else>暂无信息</p>
        </div>
      </div>

      <div class="icon-box">
        <div class="icon-item" @click="onFavorite">
          <StarFilled
            v-if="relic.favorited"
            class="icon"
            style="color: #409eff;"
          />
          <StarOutlined
            v-else
            class="icon"
          />
          <span class="label">收藏</span>
        </div>
        <div class="icon-item" @click="onComment">
          <message-outlined class="icon" />
          <span class="label">评论</span>
        </div>
      </div>
    </div>
  </div>

  <div class="associate">
    <span class="associate-title">相关推荐</span>
    <div v-if="relic.related.length" class="associate-list">
      <router-link
          v-for="(item, idx) in relic.related" :key="idx"
          class="associate-item"
          :to="{
            name: 'RelicDetail',
            query: { id: item.id }
          }"
        >
        {{ item.name }}
      </router-link>
    </div>
    <div v-else class="associate-list">暂无</div>
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
import { ref,watch, reactive, computed, defineProps,onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { StarOutlined, StarFilled, MessageOutlined } from '@ant-design/icons-vue'

const router = useRouter()
//返回上一页
function goBack() {
  router.back()      // 或者 router.go(-1)
}
/**路由获取的数据 */
const props = defineProps({
  id: { type: Number, required: true }
})

// 模拟获取多张图，真实项目从后端请求
const relic = ref({
  images: [
    require('@/assets/logo.png'),
    require('@/assets/logo.png'),
    require('@/assets/logo.png'),
    // require("C:/Users/lcx/Pictures/Screenshots/屏幕截图 2024-11-08 192429.png"),
    "https://www.njmuseum.com/files/nb/collection/modify/2021/09/28/5%EF%BC%9A3419-B-01.jpg"
  ],
  name: '文物名称',
  credit:'题词信息',
  type: '文物类别',
  era: '唐代',
  atrist: '齐白石',
  material: '材料',
  scale: '长3米宽2米',
  description:'文物描述具体信息',
  favorited: true,
  related: [{
    name:"四羊方尊",
    id:100
  }]  //相关文物信息
})

const currentIndex = ref(0)
const currentImage = computed(() => relic.value.images[currentIndex.value])

// 图片切换函数
function prevImage() {
  if (currentIndex.value > 0) currentIndex.value--
}
function nextImage() {
  if (currentIndex.value < relic.value.images.length - 1) currentIndex.value++
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

// 1： 处理点击，上报收藏状态
async function onFavorite() {
  console.log('点击收藏:- '+relic.value.favorited)
  try{
    const ans = await isFavorite(Api.url.relic.isFav,{
      id:props.id,
      username:localStorage.getItem('username'),
      fav:!relic.value.favorited,
    });
    if(ans) relic.value.favorited = !relic.value.favorited;
  }catch(error){
    message.error("出现异常 "+message);
  }
}
//跳转评论区
function onComment() {
  console.log('点击评论')
  router.push({
    path:'/comments',
    query:{id:props.id}
  })
}

import { getDetail,isFavorite } from '@/api/relicDetail'
import Api from '@/api/Api'
import { message } from 'ant-design-vue'
import { ReHistory } from '@/api/SearchRelic'

/**2： 暂时写到这，此处还需要赋值 */
async function FetchData(){
  try {
    relic.value = await getDetail(Api.url.relic.detail,
      {id:props.id,
        username:localStorage.getItem('username') });
  } catch (error) {
    message.error("获取文物具体信息失败!");
  }  
}
async function RefreshHis(){
  try{
    const res = await ReHistory(Api.url.relic.rehistory,{
      username: localStorage.getItem('username'),
      rid: props.id
    })
    if(res) message.info("刷新浏览信息成功！");
  }catch(error){
    message.error("刷新失败");
  }
}
watch(() => props.id, () => {
  FetchData(); // 监听 id 改变，重新请求数据，刷新依赖
  RefreshHis();
});
onMounted(async () => {
    FetchData();
    RefreshHis();
})
</script>

<style scoped>
.detail-container {
  display: grid;
  grid-template-columns: 1fr 2fr;
  /* align-items: center; */
  gap: 32px;
  padding: 16px;
  margin-left: 12%;
  /* margin-top: 3%; */
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
/**信息展示区样式 */
.detailinfo-box{
  max-width: fit-content;
  display: flex;
  flex-direction: row;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05); 
  font-family: "Helvetica Neue", Arial, sans-serif;
  color: #333;  
}
.detail-info {
  width: 560px;
  background-color: #ffffff;
  padding: 24px;
}

/* ID （可选，用于小标题或辅助信息） */
.detail-info h1 {
  margin: 0 0 12px;
  font-size: 0.9rem;
  font-weight: 500;
  color: #999;
  text-transform: uppercase;
  letter-spacing: 1px;
}

/* 主标题 */
.detail-info .info-title {
  display: inline-block;    /* 让 ::before 的百分比定位相对于这个元素 */
  position: relative;
  padding-left: 15px;       /* 留出空间给那条线 */
  line-height: 1.2;         /* 或者你自己的行高 */
  font-size: 2em;
}

.detail-info .info-title::before {
  content: "";
  position: absolute;
  left: 0;
  top: 50%;                 /* 垂直中心 */
  transform: translateY(-50%);
  width: 4px;
  height: 1.2em;            /* 和文字高度一致 */
  background-color: #409eff;
  border-radius: 2px;
}

/* 年代信息 */
.detail-info .info-era {
  margin: 0 0 8px;
  font-size: 1rem;
  font-style: italic;
  color: #666;
}

/* 详情介绍区域 */
.detail-info .info-desc {
  font-size: 1rem;
  line-height: 1.6;
  color: #444;
}

/* 详情小标题 */
.detail-info .info-desc h3 {
  margin: 0 0 8px;
  font-size: 1.2rem;
  font-weight: 500;
  border-bottom: 2px solid #409eff;
  padding-bottom: 4px;
  color: #222;
}

/* 详情文字段落 */
.detail-info .info-desc p {
  margin: 0 0 16px;
  line-height: 1.6;
  color: #555;

  /* 以下三行保证文本长单词/URL也会换行 */
  white-space: normal;       /* 默认的换行规则 */
  word-wrap: break-word;     /* 旧版浏览器兼容 */
  overflow-wrap: anywhere;   /* CSS 新属性，任何位置都可换行 */
}


/* 响应式：在小屏幕上让文字区全宽 */
@media (max-width: 768px) {
  .detail-info {
    padding: 16px;
    box-shadow: none;
  }
  .detail-info .info-title {
    font-size: 1.6rem;
  }
  .detail-info .info-desc h3 {
    font-size: 1rem;
  }
}

.icon-box {
  display: flex;
  flex-direction: column;    /* 竖直排列 */
  align-items: center;
  gap: 16px;                 /* 图标间距 */
  padding: 20px 20px;
}

.icon-item {
  display: flex;
  flex-direction: column;    /* 图标在上，文字在下 */
  align-items: center;
  cursor: pointer;
  color: #555;
  transition: color 0.2s;
}

.icon-item:hover {
  color: #1890ff;
}

.icon {
  font-size: 24px;           /* 图标大小 */
}

.label {
  margin-top: 4px;
  font-size: 14px;
}

.associate {
  margin: 0px 40px;
  display: flex;
  align-items: center;
  font-size: 16px;
  border-top: 2px solid #409eff;
  padding-top: 8px;   /* 边框下方留点距离 */
}

.associate-title {
  padding: 10px;
  margin-right: 8px;
  font-weight: 500;
  color: #333;
  font-size: 20px;
}

.associate-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}
.associate-item {
  display: inline-block;
  padding: 2px 8px;
  border: 1px solid #aaa;
  border-radius: 6px;
  text-decoration: none;
  color: #555;
  transition: background 0.2s, color 0.2s, border-color 0.2s;
  white-space: nowrap;
}

.associate-item:hover {
  background: #409eff20;    /* 20% 不透明度的主题色 */
  border-color: #409eff;
  color: #409eff;
}
</style>
