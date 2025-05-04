/**
这个组件，包含了一个倒计时按钮，和两个参数分别是是否启用disabledInitially、邮件地址数据emailInfo
传入参数时请求对应网址，返回倒计时时间。
 */
<template>
    <button class="countdown-button" :disabled="disabled" @click="startCountdown">
        {{ disabled ? formatTime(remaining) : '发送验证码' }}
    </button>
</template>

<script setup>
import service from '@/utils/request'
import { ref,defineProps, onBeforeUnmount } from 'vue'

const props = defineProps({
  // 运行时声明模式：与 options.props 完全等价
  /**是否禁用，默认true */
  disabledInitially: {
    type: Boolean,
    default: true 
  },
  emailInfo:{
    type: String,
    default:""
  }
})

// 响应式状态
const remaining = ref(0)
const disabled = ref(false)
let timer = null

// 格式化秒数为 MM:SS
function formatTime(sec) {
    const m = String(Math.floor(sec / 60)).padStart(2, '0')
    const s = String(sec % 60).padStart(2, '0')
    return `${m}:${s}`
}

// 启动倒计时
async function startCountdown() {
    if (disabled.value || props.disabledInitially) return
    console.log("Get Info: " + props.disabledInitially + " and " + props.emailInfo);
    const data = await service.post('/getCode', { time: new Date().getTime(), email: props.emailInfo });
    alert(data.data.message);
    if(data.data.status == 400) return ;
    remaining.value = data.data.info;        // 获取剩余时间
    disabled.value = true
    timer = setInterval(() => {
        if (remaining.value > 0) {
            remaining.value--
        } else {
            clearInterval(timer)         // 倒计时结束清理定时器
            disabled.value = false
            remaining.value = 0
        }
    }, 1000)                // 每秒执行一次 
}
// 组件卸载时清理定时器
onBeforeUnmount(() => {
    if (timer) clearInterval(timer)  // 防止内存泄露 
})
</script>

<style scoped>
.countdown-button {
    margin: 26px 1px;
    border: none;
    border-radius: 6px;
    /* 完全圆角按钮 :contentReference[oaicite:10]{index=10} */
    background-color: #409eff;
    color: white;
    font-size: 1rem;
    transition: background-color 0.3s ease;
    /* 平滑过渡 :contentReference[oaicite:11]{index=11} */
    cursor: pointer;
}

.countdown-button:disabled {
    background-color: #a0cfff;
    /* 禁用时更淡的背景 :contentReference[oaicite:12]{index=12} */
    cursor: not-allowed;
}
</style>