<template>
  <div class="select-box">
    <SelectInput v-model="sendInfo.type" name="文物类别" :options="catas"/>
    <SelectInput v-model="sendInfo.time" name="文物年代" :options="dynasty"/>
    <SelectInput v-model="sendInfo.museum" name="所在博物馆" :options="museum"/>
    <SelectInput v-model="sendInfo.sortby" name="排序方式" :options="sortBy"/>
    <InputButton placeholder="文物名称：" v-model="sendInfo.name" />
    <InputButton placeholder="作家名称：" v-model="sendInfo.artist" />
    <SmoothButton label="查询" @click="Onclick(1)" />
  </div>

  <div class="gallery">
    <SingleRelic
      v-for="(relic, idx) in relics"
      :key="idx"
      :id="relic.id"      
      :src="relic.src"
      :title="relic.title"
    />
  </div>

  <div class="page-control">
    <PageController v-model="nowpage" :total=total :page-size="8"/>
  </div>
  
</template>

<script setup>
import { onMounted, ref,watch,reactive } from 'vue'
import SingleRelic from '@/components/SingleRelic.vue';
import SelectInput from '@/components/SelectInput.vue';
import InputButton from '@/components/InputButton.vue';
import SmoothButton from '@/components/SmoothButton.vue';
import PageController from '@/components/PageController.vue';
import { getcatasETcList,SearchRelic } from '@/api/SearchRelic';
import Api from '@/api/Api';
import { message } from 'ant-design-vue';

// 卡片数据，之后可被直接代替，这里的数组元素个数为8，本地得使用require
const relics = ref([
  {id: 1, src: require("../assets/logo.png"),  title: '文物标题 A' },
]);

/**需要发送的结构体 */
const sendInfo = reactive({
  type:null,      //文物类型
  time:null,      //文物时代
  museum: null,   //博物馆
  sortby:null,    //排序方式,null按ID，1按名称，2按年代
  name: null,     //按文物名称模糊查询
  artist: null,    //按作家查询
  pageindex: 1,   //页码号
  pagesize:8      //页大小
})
const response = ref({
  total:2,
  data:[  {id: 7, src: require("../assets/logo.png"),  title: '文物标题 A' },
        {id: 8, src: "../assets/logo.png",  title: '文物标题 A' }],
})
/**请求函数 */
async function Onclick(val){
  console.log("Button was Clicked   "+sendInfo.sortby);
  try{
    sendInfo.pageindex = val; //每次点击查询回到第一页
    response.value = await SearchRelic(Api.url.relic.search,sendInfo);
    relics.value = response.value.data;
    total.value = response.value.total;
    nowpage.value = sendInfo.pageindex; 
  }catch(error){
    message.error("请求列表失败!");
  }
}
const total = ref('100'); //总项目数
const nowpage = ref(2);
//页面切换逻辑
watch(nowpage, (newval, oldval) => {
  console.log('旧值：', oldval, '新值：', newval);
  if(sendInfo.pageindex !== newval){
    sendInfo.pageindex = newval;
    Onclick(newval);
  }
})

const catas = ref([{ value: 'null', label: '全部' }]);//种类
const dynasty = ref([{ value: 'null', label: '全部' }]);//朝代信息
const museum = ref([{ value: 'null', label: '全部' }]);//博物馆名
const sortBy = ref([
  { value: 'null', label: '文物ID' },
  { value: 1, label: '文物名称' },
  { value: 2, label: '文物年代' },
  // { value: '3', label: '博物馆名称' },
]);//排序规则

onMounted(async () => {
  try {
    /**获取选择框内容,均包含全部选项对应值null */
    catas.value = await getcatasETcList(Api.url.relic.types);
    dynasty.value = await getcatasETcList(Api.url.relic.times);
    museum.value = await getcatasETcList(Api.url.relic.museums);
    Onclick(1);
  } catch (error) {
    message.error(error);
  }
})
</script>

<style scoped>
.gallery {
  display: grid;
  grid-template-columns: repeat(4, 240px);  /* 四列，每列固定 240px */
  grid-auto-rows: auto;                     /* 行高随内容自适应 */
  gap: 16px 24px;                           /* row-gap 16px, column-gap 24px */
  justify-content: center;                  /* 整体网格水平居中 */
  justify-items: center;                    /* 每个格子里内容居中 */
  padding-bottom: 40px;
  /* 限制显示两行，超出折行后隐藏 */
  max-height: calc( (270px + 16px) * 2 - 16px );
  /* 两行高度 = 240px*2 + row-gap(16px)；减去最后一行下方的额外 gap */
  overflow: hidden;
}
.select-box{
  margin: 16px 0px;
  padding: 8px;
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  gap: 30px;
}
.page-control{
  margin: 8px 0px;
  padding: 6px 0px;
  display: flex;
  flex-direction: column;
  /* 垂直居中 */
  justify-content: center;
  /* 水平居中 */
  align-items: center;
}
</style>