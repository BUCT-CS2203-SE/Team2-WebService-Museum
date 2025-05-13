<!-- src/views/CommentsView.vue -->
<template>
  <a-page-header
    title="评论详情"
    :sub-title="relic.name"
    @back="goBack"
  />

  <div class="comments-wrapper">
    <!-- 新评论输入区（保持不变） -->
    <div class="new-comment-card">
      <a-avatar
        :src="user.avatar"
        icon="user-outlined"
        class="new-avatar"
      />
      <a-textarea
        v-model:value="newComment"
        rows="3"
        placeholder="写下你的评论..."
        class="new-textarea"
      />
      <a-button
        type="primary"
        @click="addComment"
        :disabled="!newComment.trim()"
        class="new-button"
      >
        发布
      </a-button>
    </div>

    <!-- 加了滚动的评论列表 -->
    <div class="comment-list-wrapper">
      <a-list
        :data-source="comments"
        item-layout="vertical"
        class="comment-list"
      >
        <template #renderItem="{ item }">
          <a-list-item class="comment-item">
            <a-comment :author="item.author" class="ant-comment-custom">
              <template #avatar>
                <a-avatar
                  :src="item.avatar"
                  icon="user-outlined"
                />
              </template>
              <template #content>
                <p>{{ item.content }}</p>
              </template>
              <template #datetime>
                <span class="comment-time">{{ item.datetime }}</span>
              </template>
            </a-comment>
          </a-list-item>
        </template>
      </a-list>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted,defineProps } from 'vue'
import { useRouter } from 'vue-router'
import {  Comment as AComment, message} from 'ant-design-vue'
import { getComments,AddComments } from '@/api/relicComments'
import Api from '@/api/Api'

const router = useRouter()
function goBack() { router.back() }

//已知文物ID
const props = defineProps({
  id: { type: Number, required: true }
})

const relic = reactive({ id: props.id, name: '千年青铜镜' })  //文物信息
const user = reactive({ avatar: require("../assets/user_default500x500.png") }) //用户头像url,考虑在前端保存
const comments = ref([
  {  author: '张三', avatar: '', content: '非常精美！', datetime: '2025-05-10 14:32' },
  {  author: '李四', avatar: '', content: '请问出土地点？', datetime: '2025-05-11 09:15' },
  /* …更多评论… */
])

/**1：  添加新评论 */
const newComment = ref('')
async function addComment() {
  const txt = newComment.value.trim()
  if (!txt) return;
  try{
    const apply = await AddComments(Api.url.comments.add_comments,{
      username: localStorage.getItem('username'),
      id: props.id,
      contents: txt,
      datetime: new Date().toLocaleString()
    })
    if(!apply) return;
  }catch(error) {message.error(error);}
  comments.value.unshift({
    // id: Date.now(),
    author: '游客',
    avatar: '',
    content: txt,
    datetime: new Date().toLocaleString()
  })
  newComment.value = ''
}

const response = ref({
  name: '文物名称',
  comments: [{author:'用户名',avatar: '头像url',content:'评论详情',datetime:'评论时间'}]
})

/**2：  加载评论区数据 */
onMounted(async () =>{
  try{
    response.value = await getComments(Api.url.comments.comments,{
      id:props.id
    });
  }catch(error){
    message.error(error);
  }
})
</script>

<style scoped>
.comments-wrapper {
  max-width: 800px;
  margin: 24px auto;
  padding: 0 16px;
}

/* 新评论卡片（保持原来样式） */
.new-comment-card {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  background: #fff;
  padding: 16px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
  margin-bottom: 16px;
}
.new-avatar {
  font-size: 24px;
  color: #999;
}
.new-textarea {
  flex: 1;
}
.new-button {
  align-self: flex-end;
  border-radius: 4px;
}

/* 评论列表外层，限制高度并滚动 */
.comment-list-wrapper {
  max-height: 400px;    /* 超过 400px 时出现滚动条 */
  overflow-y: auto;
  padding-right: 8px;   /* 给滚动条留出空间 */
}

/* 评论列表本身无需改动 */
.comment-list {
  background: none;
}

/* 卡片式评论项（保持原来样式） */
.comment-item {
  background: #fff;
  padding: 16px;
  border-radius: 8px;
  margin-bottom: 16px;
  transition: box-shadow 0.2s;
}
.comment-item:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
}

/* 去掉 a-comment 默认缩进 */
.ant-comment-custom {
  padding: 0;
  border: none;
}

/* 评论时间 */
.comment-time {
  font-size: 0.85rem;
  color: #999;
  margin-left: 8px;
}
</style>
