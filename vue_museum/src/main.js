import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'

createApp(App).use(router).mount('#app')

// 命令行相关指令
// npm run serve 启动项目
// Ctrl + C 结束运行