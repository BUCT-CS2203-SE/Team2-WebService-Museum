<template>
  <div class="timeline-container">
    <a-spin v-if="loading" size="large" />
    <a-alert
      v-else-if="error"
      message="加载失败"
      :description="error"
      type="error"
      show-icon
    />
    
    <!-- 修改1: 添加外层滚动容器 -->
     <div v-else class="timeline-scroll-wrapper" ref="scrollWrapper">
      <div class="timeline-inner" :style="{ width: timelineWidth }"></div>
      <TimeLine 
        :items="itemList" 
        
      />
    </div>
    
    <!-- 修改2: 添加导航按钮 -->
    <div class="nav-buttons">
      <a-button 
        @click="scrollTo('left')" 
        :disabled="isScrollLeft"
        class="nav-button"
      >
        <LeftOutlined />
      </a-button>
      <a-button 
        @click="scrollTo('right')" 
        :disabled="isScrollRight"
        class="nav-button"
      >
        <RightOutlined />
      </a-button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { message } from "ant-design-vue";
import { LeftOutlined, RightOutlined } from '@ant-design/icons-vue'; // 修改3: 引入图标
import TimeLine from "@/components/TimeLine.vue";
//import axios from "@/utils/request";//测试时要注释掉，正式使用时取消注释

const itemList = ref([]);
const loading = ref(false);
const error = ref(null);
const scrollWrapper = ref(null); // 修改4: 获取滚动容器DOM

//以下是测试，正式使用时注释掉
const generateMockData = () => {
  const dynasties = ['夏朝', '商朝', '周朝', '秦朝', '汉朝', '唐朝', '宋朝', '元朝', '明朝', '清朝'];
  const relics = ['青铜器', '瓷器', '玉器', '书画', '陶器', '金银器', '漆器', '石刻', '织绣', '钱币'];
  
  return Array.from({ length: 10 }, (_, i) => ({
    id: i + 1,
    imageUrl: `https://picsum.photos/300/200?random=${i}`, // 使用随机图片
    name: ` ${relics[i % relics.length]}`,
    dynasty: dynasties[i % dynasties.length],
    dateRange: `约${1000 + i * 100}-${900 + i * 100}年`

  }));
};
//以上是测试，正式使用时删除

// 修改5: 计算时间轴总宽度（根据项目数量）
const timelineWidth = computed(() => {
  const itemWidth = 280; // 每个时间轴项目预估宽度
  return `${itemList.value.length * itemWidth}px`;
});

// 修改6: 判断是否滚动到最左/最右
const isScrollLeft = ref(true);
const isScrollRight = ref(false);

// 修改7: 滚动控制函数
const scrollTo = (direction) => {
  const container = scrollWrapper.value;
  if (!container) return;
  
  const scrollAmount = container.offsetWidth * 0.8; // 每次滚动距离
  const newScrollLeft = direction === 'left' 
    ? Math.max(0, container.scrollLeft - scrollAmount) // 确保不小于0
    : Math.min(container.scrollWidth - container.clientWidth, container.scrollLeft + scrollAmount); // 确保不超过最大值
  
  container.scrollTo({
    left: newScrollLeft,
    behavior: 'smooth'
  });
};

// 修改8: 添加滚动事件监听
const checkScrollPosition = () => {
  const container = scrollWrapper.value;
  if (!container) return;
  
  isScrollLeft.value = container.scrollLeft <= 10;
  isScrollRight.value = container.scrollLeft + container.clientWidth >= container.scrollWidth - 10;
};
 //以下是测试，正式使用时删除
 const fetchTimelineData = async () => {
   try {
     loading.value = true;
     error.value = null;
    
     // 修改3: 直接使用Mock数据而不是axios请求
     const mockData = generateMockData();
     //console.log(mockData);
     itemList.value = mockData.map(item => ({
       id: item.id,
       imgUrl: item.imageUrl,
       title: item.name,
       date: `(${item.dateRange})`,
       dynasty: item.dynasty,
     }));
    
     // 修改4: 添加延迟模拟网络请求
     await new Promise(resolve => setTimeout(resolve, 500));
    
   } catch (err) {
     error.value = '加载数据时发生错误';
     message.error('获取文物时间轴失败');
   } finally {
     loading.value = false;
   }
 };
//以上是测试，正式使用时删除

// //获取时间轴数据，正式使用时取消注释
// const fetchTimelineData = async () => {
//   try {
//     loading.value = true;
//     error.value = null;
//     const response = await axios.get('/relics/timeline');     
//     itemList.value = response.data.map(item => ({
//       id: item.id,
//       imgUrl: item.imageUrl,
//       title: item.name,
//       date: `(${item.dateRange})`,
//       dynasty: item.dynasty,
//     }));
//   } catch (err) {
    
//     error.value = err.response?.data?.message || err.message;
//     message.error('获取文物时间轴失败');
    
//   } finally {
//     loading.value = false;
//   }
// };

onMounted(() => {
  fetchTimelineData();
  if (scrollWrapper.value) {
    scrollWrapper.value.addEventListener('scroll', checkScrollPosition);
    // 修改5：初始化时确保滚动位置正确
    setTimeout(() => {
      scrollTo('right');
      scrollTo('left');
    }, 500);
  }
});
</script>

<style scoped>
.timeline-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  position: relative; /* 修改10: 为导航按钮定位 */
}

/* 修改11: 新增滚动容器样式 */
.timeline-scroll-wrapper {
  width: 100%;
  overflow-x: auto;
  scrollbar-width: none;
  -ms-overflow-style: none;
  scroll-behavior: smooth;
  -webkit-overflow-scrolling: touch;
}

.timeline-inner {
  display: inline-block; /* 确保宽度生效 */
  padding: 0 50px; /* 添加内边距确保边缘项目可见 */
}

.timeline-scroll-wrapper::-webkit-scrollbar {
  display: none; /* 隐藏滚动条（Chrome/Safari） */
}

/* 修改12: 导航按钮样式 */
.nav-buttons {
  position: absolute;
  width: 1px; 
  height: 1px;
  top: 50%;
  left: 0;
  right: 0;
  display: none;
  justify-content: space-between;
  transform: translateY(-50%);
  pointer-events: none; /* 允许点击穿透 */
}

.nav-button {
  pointer-events: auto;
  width: 1px; 
  height: 1px; /* 恢复按钮点击 */
  z-index: 10;
  display: none;
  background: rgba(255,255,255,0.8);
  box-shadow: 0 2px 8px rgba(0,0,0,0.15);
}

/* 修改13: 响应式调整 */
@media (max-width: 768px) {
  .nav-buttons {
    display: none; /* 小屏隐藏导航按钮，使用触摸滑动 */
  }
  
  .timeline-scroll-wrapper {
    -webkit-overflow-scrolling: touch; /* iOS平滑滚动 */
  }

  .timeline-inner {
    padding: 0 20px;
  }
}
</style>

