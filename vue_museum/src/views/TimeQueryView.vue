<template>
    <a-page-header
        title="节点详情"
        :sub-title=time_span
        @back="goBack"
    />
    <!-- 下面展示这段时间内的所有文物卡片，建议调用SingleRelic组件，可以直接跳转到详情页 -->
    <SingleRelic
      v-for="(relic, idx) in relics"
      :key="idx"
      :id="relic.id"      
      :src="relic.src"
      :title="relic.title"
    />

</template>

<script setup>
import { defineProps, ref } from 'vue';
import { useRouter } from 'vue-router'
import SingleRelic from '@/components/SingleRelic.vue';

const router = useRouter();
function goBack() { router.back(); }
const props = defineProps({
  sta: { type: Number, required: true },
  end: { type: Number, required: true },
})
const time_span = props.sta+"年 - "+props.end+"年";

/**这个数组用作示例，实际需要写接口后端传数据 */
const relics = ref([
  {id: 1, src: require("../assets/logo.png"),  title: '文物标题 A' },
]);
</script>