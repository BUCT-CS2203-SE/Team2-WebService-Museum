import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Login from '@/views/toLogin.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: { public: true }
  },{
    path: '/login',
    component: Login,
    meta: { public: true }
  },{
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue'),
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
    console.log("重定向");
    return next('/login');
  }
  // 其余情况放行
  next();
});


export default router
