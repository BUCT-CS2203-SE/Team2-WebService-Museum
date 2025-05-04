import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    // 根目录
    path: '/',
    name: 'home',
    component: () => import('../views/HomeView.vue'),
    meta: { public: true }
  },{
    //登录页
    path: '/login',
    component: () => import('@/views/toLogin.vue'),
    meta: { public: true }
  },{
    //信息页
    path: '/about',
    name: 'about',
    // 懒加载（推荐使用）
    component: () => import('../views/AboutView.vue'),
    meta: { auth: true } 
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
  if (to.meta.auth && !token) {
    console.log("路由重定向至登陆界面");
    return next('/login');
  }
  // 其余情况放行
  next();
});


export default router
