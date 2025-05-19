// import { meta } from '@babel/eslint-parser';
import { message } from 'ant-design-vue';
import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    // 根目录 公开
    path: '/',
    name: 'home',
    component: () => import('../views/HomeView.vue'),
    meta: { public: true , hasNav: true }
  },{
    //登录页 公开
    path: '/login',
    component: () => import('@/views/toLogin.vue'),
    meta: { public: true, hasNav: false }
  },{
    //搜索页 公开
    path: '/search',
    name: 'search',
    component: () => import('../views/SearchView.vue'),
    meta: {public: true, hasNav: true}
  },{
    //时间轴页 公开
    path: '/timebar',
    name: 'timebar',
    component: () => import('../views/TimeBarView.vue'),
    meta: {public:true, hasNav: true},
  },{
    //时间轴节点页 公开
    path: '/timebar-time',
    name: 'timebar-time',
    component: () => import('../views/TimeQueryView.vue'),
    props: (route) => ({
      sta: Number(route.query.sta),
      end: Number(route.query.end)
    }),
    meta: { public: true, hasNav: true }
  },{
    //知识图谱页 不公开
    path: '/knowledgemap',
    name: 'knowledgemap',
    component: () => import('../views/KnowledgeMapView.vue'),
    meta: {public:false, hasNav: true}
  },{
    //文物详情 不公开
    path: '/relic',
    name: 'RelicDetail',
    component: () => import('../views/RelicDetail.vue'),
    // 将 query.id 转成数字后，作为组件的 props.id
    props: (route) => ({
      id: Number(route.query.id)
    }),
    meta: {public:false, hasNav: true}
  },{
    //文物评论页 不公开
    path:'/comments',
    name: 'Comments',
    component: () => import('../views/CommentsView.vue'),
    props: (route) => ({
      id: Number(route.query.id)
    }),
    meta: {public:false, hasNav: true}
  },  { 
    //用户信息管理页 不公开
    path:'/userpage',
    name: 'userpage',
    component: () => import('../views/UserPage.vue'),
    meta: {public:false, hasNav: true}
  }

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})
router.beforeEach((to, from, next) => {
  // 公开页面直接放行
  if (to.meta.public) {
    return next();
  }
  // 需要认证但无 token，跳登录
  const token = localStorage.getItem('jwt');
  if (!token) {
    message.warn("请登录后访问!");
    console.log("路由重定向至登陆界面");
    return next('/login');
  }
  // 其余情况放行
  next();
});


export default router
