<template>
    <div class="container">
        <div class="hed">我的评论</div>
        <div class="but-box">
           <InputButton v-model="name" placeholder="文物名称："/> 
           <InputButton v-model="conten" placeholder="评论内容："/> 
           <DateInput v-model="ti" label="评论时间"/>
           <SmoothButton label="查询" @click="OnCilck"/>
        </div>
        <MyTable :data="myFav" :columns="colName" action-key="time" @view="handleView" @delete="handleDelete" />
        
    </div>
</template>

<script setup>
import InputButton from '@/components/InputButton.vue';
import SmoothButton from '@/components/SmoothButton.vue';
import { ref,onActivated } from 'vue';
import DateInput from '@/components/DateInput.vue';
import MyTable from '@/components/MyTable.vue';
import { message } from 'ant-design-vue';
import { useRouter } from 'vue-router'
import { delComts, getComtsDetail } from '@/api/UserComts';
import Api from '@/api/Api';
const router = useRouter()

const name = ref(null) //输入的文物名
const conten = ref(null) //输入的评论内容
const ti = ref(null) //输入的截止时间

// 表格字段
const myFav = ref([
    {id: 1,rid:1, name: "四羊方尊",content:"真不错", time: new Date().toISOString().slice(0, 19)},
    {id: 3,rid:2, name: "四羊方尊",content:"Nice!", time: new Date().toISOString().slice(0, 19)},
])
const colName = ref([
      { title: '评论编号', dataIndex: 'id', key: 'id' },
      { title: '文物编号', dataIndex: 'rid', key: 'rid' },
      { title: '文物名称', dataIndex: 'name', key: 'name' ,widthRatio:2 },
      { title: '评论内容', dataIndex: 'content', key: 'content' ,widthRatio:5 },
      { title: '评论时间', dataIndex: 'time', key: 'time' ,widthRatio:1.5 },
])

function handleView(item) {
  message.info(`查看：${item.name}`);
  console.log('查看评论')
  router.push({
    path:'/comments',
    query:{id:item.rid}
  })
}
async function handleDelete(item) {
  myFav.value = myFav.value.filter(val => val.id !== item.id);
  try{
    const res = await delComts(Api.url.user.delMyComts,{
        id: item.id //评论id
    })
    if(res) message.success('已删除评论');
  }catch(error){
    message.error("出错了");
  }
}
async function OnCilck(){
    console.log(name.value+"as  "+ti.value);
    try{
        myFav.value = await getComtsDetail(Api.url.user.getMyComts,{
            username: localStorage.getItem('username'),
            relicname: name.value,
            content: conten.value,
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