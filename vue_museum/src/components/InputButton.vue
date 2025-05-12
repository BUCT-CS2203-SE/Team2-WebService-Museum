<!-- 输入框 -->
<template>
  <div class="input-wrapper">
    <input
      class="smooth-input"
      :value="props.modelValue"
      @input="onInput"
      :placeholder="props.placeholder"
    />
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  /**绑定的输入框内容 */
  modelValue: { type: String, default: '' },
  /**输入框原始填充 */
  placeholder: { type: String, default: '请输入内容' },
})

const emit = defineEmits(['update:modelValue'])

function onInput(event) {
  emit('update:modelValue', event.target.value)
}
</script>

<style scoped>
.input-wrapper {
  position: relative;
  width: 160px;
  font-family: sans-serif;
}

/* 隐藏原生样式 */
.smooth-input {
  width: 120px;
  padding: 0.6em 1.2em;
  font-size: 1em;
  border: 1px solid #ccc;
  border-radius: 8px;     /* 完全圆角 */
  background-color: #fff;
  transition: border-color 0.2s ease, box-shadow 0.2s ease, background 0.2s ease;
  outline: none;
  align-content: center;
}

/* 悬停时微微变色 */
.smooth-input:hover {
  background-color: #fafafa;
}

/* 聚焦时高亮 */
.smooth-input:focus {
  border-color: #409eff;
  box-shadow: 0 0 0 3px rgba(64, 158, 255, 0.3);
}

/* 禁用态样式（如果需要，可在父组件上加 disabled 属性并扩展此样式） */
.smooth-input:disabled {
  background-color: #f5f7fa;
  color: #c0c4cc;
  border-color: #ebeef5;
  cursor: not-allowed;
}
</style>
