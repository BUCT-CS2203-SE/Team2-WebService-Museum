<template>
  <!-- 修改1: 添加页面加载状态 -->
  <div v-if="loading" class="loading-container">
    <a-spin size="large" tip="加载中..." />
  </div>
  
  <div v-else>
    <a-page-header
        title="节点详情"
        :sub-title="time_span"
        @back="goBack"
    />
    <!-- 修改2: 添加文物卡片列表容器 -->
    <div class="relic-list-container">
      <!-- 下面展示这段时间内的所有文物卡片 -->
      <div v-if="relics.length > 0" class="relic-list">
        <SingleRelic
          v-for="(relic, idx) in relics"
          :key="idx"
          :id="relic.id"
          :src="relic.src"
          :title="relic.title"
        />
      </div>
      <!-- 修改3: 添加无数据提示 -->
      <div v-else class="no-data">
        <a-empty description="该时间段内暂无文物记录" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import SingleRelic from '@/components/SingleRelic.vue';
import { message } from 'ant-design-vue';
//import axios from '@/utils/request';
//import api from '../api/Api.js'; 

const router = useRouter();
function goBack() { router.back(); }

const props = defineProps({
  sta: { type: Number, required: true },
  end: { type: Number, required: true },
});

// 时间范围显示
const time_span = `${props.sta}年 - ${props.end}年`;

// 文物数据
const relics = ref([]);

// 加载状态
const loading = ref(true);

// 模拟获取该时间段内的文物数据，以下是mork数据测试，实际使用时注释
const fetchRelicsInTimeSpan = async () => {
  try {
    // 模拟API请求延迟
    await new Promise(resolve => setTimeout(resolve, 800));
    
    // 模拟根据时间范围获取文物数据
    // 实际项目中应替换为真实的API调用
    const mockData = [
      { id: 101, src: require('@/assets/logo.png'), title: '司母戊鼎 (时间范围: 1000-1200年)' },
      { id: 102, src: require('@/assets/logo.png'), title: '文物标题 B (时间范围: 1000-1200年)' },
      { id: 103, src: require('@/assets/logo.png'), title: '文物标题 C (时间范围: 1200-1400年)' },
      { id: 104, src: require('@/assets/logo.png'), title: '文物标题 D (时间范围: 1300-1500年)' },
      { id: 105, src: require('@/assets/logo.png'), title: '文物标题 E (时间范围: 1400-1600年)' },
    ];
    
    // 筛选符合时间范围的文物
    const filteredRelics = mockData.filter(relic => {
      // 这里应根据实际文物时间数据进行筛选
      // 简化逻辑：假设文物标题中包含时间范围信息且格式为 "时间范围: XXXX-XXXX年"
      const match = relic.title.match(/时间范围: (\d+)-(\d+)年/);
      if (match) {
        const relicStart = parseInt(match[1]);
        const relicEnd = parseInt(match[2]);
        return relicStart <= props.end && relicEnd >= props.sta;
      }
      return false;
    });
    
    relics.value = filteredRelics;
  } catch (error) {
    message.error('获取文物数据失败');
    console.error(error);
  } finally {
    loading.value = false;
  }
};
//以上是mork数据测试，实际使用时注释


// 从后端接口获取该时间段内的文物数据,以下是实际使用api接口的，测试时注释
// const fetchRelicsInTimeSpan = async () => {
//   try {
//     loading.value = true; //开始加载时设置加载状态
//     // 修改处4：使用axios从后端接口获取文物数据
//      const response = await axios.get(api.url.relic.timeline.data);
//      // 筛选符合时间范围的文物
//      const filteredRelics = response.data.filter(relic => {
//      const match = relic.dateRange?.match(/(\d+)-(\d+)/);
//      if (match) {
//      const relicStart = parseInt(match[1]);
//      const relicEnd = parseInt(match[2]);
//      return relicStart <= props.end && relicEnd >= props.sta;
 // }
      // return false;
//     });    
//     relics.value = filteredRelics;
//   } catch (error) {
//     message.error('获取文物数据失败');
//     console.error(error);
//   } finally {
//     loading.value = false; // 修改处5：加载完成后设置加载状态
//   }
// };
//以上是实际使用api接口的，测试时注释
onMounted(() => {
  fetchRelicsInTimeSpan();
});
</script>

<style scoped>
/* 修改4: 添加列表容器样式 */
.relic-list-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

/* 修改5: 添加文物列表样式 */
.relic-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  gap: 24px;
  margin-top: 20px;
}

/* 修改6: 添加无数据提示样式 */
.no-data {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
  margin: 20px 0;
}

/* 修改7: 加载中样式 */
.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
  margin: 20px 0;
}
</style>