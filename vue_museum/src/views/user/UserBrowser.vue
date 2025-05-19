<template>
    <div class="container">
        <div class="hed">浏览记录</div>
        <div class="but-box">
           <InputButton v-model="name" placeholder="文物名称："/> 
           <DateInput v-model="ti" label="浏览时间"/>
           <SmoothButton label="查询" @click="OnCilck"/>
        </div>
        <MyTable :data="myFav" :columns="colName" action-key="time" @view="handleView" @delete="handleDelete" />
        
    </div>
</template>

<script setup>
import InputButton from '@/components/InputButton.vue';
import SmoothButton from '@/components/SmoothButton.vue';
import { onActivated , ref } from 'vue';
import DateInput from '@/components/DateInput.vue';
import MyTable from '@/components/MyTable.vue';
import { message } from 'ant-design-vue';
import { useRouter } from 'vue-router'
import { delBrowser, getBroswerDetail } from '@/api/UserBrowser';
import Api from '@/api/Api';
const router = useRouter()

const name = ref(null)
const ti = ref(null)

// 表格字段二者得绑定在一起
const myFav = ref([
    {seq:1,id: 1, name: "四羊方尊", time: new Date().toISOString().slice(0, 19)},
    {seq:2,id: 3, name: "四羊方尊", time: new Date().toISOString().slice(0, 19)},
])
const colName = ref([
    { title: '序号', dataIndex: 'seq', key: 'seq' },
      { title: '文物编号', dataIndex: 'id', key: 'id' },
      { title: '文物名称', dataIndex: 'name', key: 'name' ,widthRatio:2},
      { title: '浏览时间', dataIndex: 'time', key: 'time' ,widthRatio:2},
])

function handleView(item) {
  message.info(`查看：${item.name}`);
  console.log('查看收藏')
  router.push({
    path:'/relic',
    query:{id:item.id}
  })
}
async function handleDelete(item) {
  myFav.value = myFav.value.filter(val => val.id !== item.id);
  try{
    const res = await delBrowser(Api.url.user.delMyBrowser,{
        username: localStorage.getItem('username'),
        rid: item.id
    })
    if(res) message.success('已删除');
  }catch(error){
    message.error("出错了");
  }
}
async function OnCilck(){
    console.log(name.value+"as  "+ti.value);
    try{
        myFav.value = await getBroswerDetail(Api.url.user.getMyBrowser,{
            username: localStorage.getItem('username'),
            relicname: name.value,
            time: ti.value
        })
        message.success("请求成功")
    }catch(error){
        message.error("请求收藏失败")
    }
}
// 激活时执行
onActivated(() =>{
    OnCilck();
})
</script>

<style scoped>
.container{
    display: flex;
    flex-direction: column;
    gap: 20px;
    margin-top: -10px;
}
.hed{
    font-size: 20px;
    font-weight: bold;
}
.but-box{
    display: flex;
    flex-direction: row;
    gap: 30px;
}
</style>