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
import Api from '@/api/Api';
import axios from "@/utils/request";//测试时要注释掉，正式使用时取消注释

const router = useRouter();
function goBack() { router.back(); }

const props = defineProps({
  sta: { type: Number, required: true },
  end: { type: Number, required: true },
});

// 时间范围显示
const time_span = `${props.sta}年 - ${props.end}年`;

// 文物数据
const relics = ref([{
  id: 1,
  src: require('@/assets/logo.png'),
  title: '文物标题 A'
}]);

// 加载状态
const loading = ref(true);



// 从后端接口获取该时间段内的文物数据,以下是实际使用api接口的，测试时注释
const fetchRelicsInTimeSpan = async () => {
  try {
    loading.value = true; //开始加载时设置加载状态
    // 修改处4：使用axios从后端接口获取文物数据
    const response = await axios.post(Api.url.relic.timeline.info, {
      sta:props.sta,
      end:props.end
    });
    if(response.status===200) relics.value = response.data;
  } catch (error) {
    message.error('获取文物数据失败');
    console.error(error);
  } finally {
    loading.value = false; // 修改处5：加载完成后设置加载状态
  }
};
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