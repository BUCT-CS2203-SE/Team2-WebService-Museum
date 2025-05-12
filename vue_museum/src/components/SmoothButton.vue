<!-- 按钮 -->
<template>
  <button
    class="smooth-btn"
    :disabled="props.disabled"
    @click="onClick"
  >
    {{ label }}
  </button>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  label: { type: String, default: '提交' },
  disabled: { type: Boolean, default: false }
})

const emit = defineEmits(['click'])
/**通过这个函数，父组件能知道是否被点击使用@v-on */
function onClick(event) {
  if (!props.disabled) {
    emit('click', event)
  }
}
</script>

<style scoped>
.smooth-btn {
  width: 100px;
  display: inline-block;
  padding: 0.6em 1.5em;
  font-size: 1em;
  align-content: center;
  line-height: 1.2;
  border: 1px solid #409eff;
  border-radius: 6px;             /* 胶囊形圆角 */
  background-color: #409eff;        /* 默认蓝色背景 */
  color: white;
  cursor: pointer;
  transition:
    background-color 0.2s ease,
    box-shadow 0.2s ease,
    transform 0.1s ease;
}

/* 悬停和焦点效果 */
.smooth-btn:hover:not([disabled]),
.smooth-btn:focus-visible:not([disabled]) {
  background-color: #66b1ff;
  box-shadow: 0 0 0 3px rgba(102, 177, 255, 0.4);
  outline: none;
}

/* 按下时轻微缩放 */
.smooth-btn:active:not([disabled]) {
  transform: scale(0.97);
}

/* 禁用态 */
.smooth-btn[disabled] {
  background-color: #a0cfff;
  border-color: #a0cfff;
  cursor: not-allowed;
  color: #f0faff;
}
</style>
