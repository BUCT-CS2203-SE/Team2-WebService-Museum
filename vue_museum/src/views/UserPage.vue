<!-- src/App.vue -->
<template>
  <a-layout class="full-height">
    <!-- 左侧 Sider -->
    <a-layout-sider
      :width="sidebarWidth"
      :collapsed-width="collapsedWidth"
      collapsible
      v-model:collapsed="collapsed"
      class="site-sider"
    >
      <a-menu
        theme="dark"
        mode="inline"
        style="margin-top: 40px;"
        :selected-keys="[selectedKey]"
        @click="onMenuClick"
      >
        <a-menu-item
          v-for="item in navItems"
          :key="item.key"
          :icon="item.icon"
          style="margin: 10px 0px; font-size: medium;"
        >
          {{ item.label }}
        </a-menu-item>
      </a-menu>
    </a-layout-sider>

    <!-- 右侧内容 -->
    <a-layout-content class="site-content">
      <keep-alive>
        <component :is="currentComponent" />
      </keep-alive>
    </a-layout-content>
  </a-layout>
</template>

<script  setup>
import { ref, computed,h } from 'vue';
import {
  UserOutlined,
  HeartOutlined,
  MessageOutlined,
  HistoryOutlined,
  LogoutOutlined
} from '@ant-design/icons-vue';

import UserInfoView from './user/UserInfoView.vue';
import UserFav from './user/UserFav.vue';
import UserComment from './user/UserComment.vue';
import UserBrowser from './user/UserBrowser.vue';

const navItems = [
  { key: 'info',    label: '修改信息', component: UserInfoView,  icon: () => h(UserOutlined) },
  { key: 'fav',     label: '我的收藏', component: UserFav,       icon: () => h(HeartOutlined) },
  { key: 'comment', label: '我的评论', component: UserComment,   icon: () => h(MessageOutlined) },
  { key: 'browser', label: '浏览记录', component: UserBrowser,   icon: () => h(HistoryOutlined) },
  { key: 'logout',  label: '注销',     icon: () => h(LogoutOutlined) },
];

const selectedKey = ref(navItems[0].key);
const collapsed = ref(false);

// 当前展示的组件
const currentComponent = computed(() => {
  switch (selectedKey.value) {
    case 'info':    return UserInfoView;
    case 'fav':     return UserFav;
    case 'comment': return UserComment;
    case 'browser': return UserBrowser;
    default:
      console.warn(`Unknown key: ${selectedKey.value}`);
      return UserInfoView;
  }
});


function onMenuClick({ key }) {
  if(key == 'logout'){
    handleLogout(); //注销
  }
  selectedKey.value = key;
}

// 可根据需要调整宽度
const sidebarWidth = 240;
const collapsedWidth = 64;

import { useRouter } from 'vue-router'
import service from '@/utils/request' // 你的 axios 实例
import Api from '@/api/Api';
import { message } from 'ant-design-vue';
const router = useRouter()
async function handleLogout() {
    await service.post(Api.url.logout,{});
    // 1. 清除本地存储中的 信息
    localStorage.removeItem('jwt');
    localStorage.removeItem('username');
    localStorage.removeItem('avatar');                  
    // 2. 取消 axios 请求头上的 Authorization
    delete service.defaults.headers.common['Authorization'];
    message.success("注销成功!");
    // 3. 跳转回首页
    router.push('/');
}
</script>

<style scoped>
.full-height {
    height: 100vh;
}

.site-sider {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.logo {
  height: 64px;
  margin: 0 16px;
  font-size: 1.25rem;
  color: #fff;
  line-height: 64px;
  text-align: center;
  font-weight: bold;
}

.site-content {
  padding: 24px;
  background: #f0f2f5;
}

/* 卡片化子组件容器 */
.site-content > * {
  background: #fff;
  padding: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.09);
}
</style>
