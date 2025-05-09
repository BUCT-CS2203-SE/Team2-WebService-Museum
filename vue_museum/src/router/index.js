// import { meta } from '@babel/eslint-parser';
import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    // 根目录
    path: '/',
    name: 'home',
    component: () => import('../views/HomeView.vue'),
    meta: { public: true , hasNav: true }
  },{
    //登录页
    path: '/login',
    component: () => import('@/views/toLogin.vue'),
    meta: { public: true, hasNav: false }
  },{
    path: '/search',
    name: 'search',
    component: () => import('../views/SearchView.vue'),
    meta: {public: true, hasNav: true}
  },{
    path: '/timebar',
    name: 'timebar',
    component: () => import('../views/TimeBarView.vue'),
    meta: {public:true, hasNav: true}
  },{
    path: '/knowledgemap',
    name: 'knowledgemap',
    component: () => import('../views/KnowledgeMapView.vue'),
    meta: {public:true, hasNav: true}
  },{
    path: '/userinfo',
    name: 'userinfo',
    component: () => import('../views/UserInfoView.vue'),
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
    console.log("路由重定向至登陆界面");
    return next('/login');
  }
  // 其余情况放行
  next();
});


export default router
