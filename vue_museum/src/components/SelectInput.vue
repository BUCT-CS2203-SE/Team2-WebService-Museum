<template>
  <div class="select-wrapper">
    <select
      :value="modelValue" 
      @change="onChange"
    >
      <!-- 占位 option -->
      <option disabled value="">{{ props.name }}</option>

      <!-- 真正的选项 -->
      <option
        v-for="opt in options"
        :key="opt.value"
        :value="opt.value"
      >{{ opt.label }}</option>
    </select>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  name:     { type: String, default: '请选择' },
  options:  {
    type: Array,
    default: () => [
      { value: '0', label: '全部' }
    ]
  },
  // 3. v-model 绑定的值就是 modelValue
  modelValue: {
    type: [String, Number, null],
    default: ''
  }
})

const emit = defineEmits(['update:modelValue'])

// 当 select 改变时，向父组件发出 update:modelValue
function onChange(event) {
  emit('update:modelValue', event.target.value)
}
</script>

<style scoped>
.select-wrapper {
  width: 160px;
  position: relative;
  font-family: sans-serif;
}
/* 隐藏原生箭头 */
select {
  appearance: none;
  width: 100%;
  padding: 0.6em 1.2em;
  line-height: 1.2;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #fff;
  font-size: 1em;
  cursor: pointer;
}
/* 自定义箭头 */
.select-wrapper::after {
  content: '▾';
  position: absolute;
  right: 1em;
  top: 50%;
  transform: translateY(-50%);
  pointer-events: none;
  color: #666;
}
/* 焦点和悬停效果 */
select:focus {
  border-color: #66afe9;
  box-shadow: 0 0 0 3px rgba(102,175,233,0.3);
  outline: none;
}
select:hover {
  background-color: #f9f9f9;
}
</style>
