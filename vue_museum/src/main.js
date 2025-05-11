import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css' // 引入样式文件

const app = createApp(App)
app.use(router)
app.use(Antd)
app.mount('#app')
// createApp(App).use(router).mount('#app')

// 命令行相关指令
// npm run serve 启动项目
// Ctrl + C 结束运行