<!-- 选择框组件 -->
<template>
  <div class="select-wrapper">
    <select v-model="selected">
      <option disabled value="">{{ props.name }}</option>
      <option v-for="opt in props.options" :key="opt.value" :value="opt.value">
        {{ opt.label }}
      </option>
    </select>
  </div>
</template>

<script setup>
import { ref,defineProps } from 'vue'

const props = defineProps({
    /**表面显示的字样 */
    name:{type: String, default:"请选择"},
    /**可选项数组 */
    options: {
        type: Array,
        default: () => [
            {value: '0', label:'全部'},
            { value: 'z', label: '选项 A' },
            { value: 'b', label: '选项 B' },
            { value: 'c', label: '选项 C' },
        ]
    }
})

const selected = ref('')
</script>

<style scoped>
.select-wrapper {
  width: 160px;
  position: relative;
  font-family: sans-serif;
}

/* 隐藏原生箭头 */
select {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;

  width: 100%;
  padding: 0.6em 1.2em;
  line-height: 1.2;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #fff;
  background-image: none;
  font-size: 1em;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
  cursor: pointer;
}

/* 自定义下拉箭头 */
.select-wrapper::after {
  content: '▾';
  position: absolute;
  right: 1em;
  top: 50%;
  transform: translateY(-50%);
  pointer-events: none;
  font-size: 0.8em;
  color: #666;
}

/* 获取焦点时高亮边框 */
select:focus {
  border-color: #66afe9;
  box-shadow: 0 0 0 3px rgba(102, 175, 233, 0.3);
  outline: none;
}

/* 鼠标悬停时微微改变背景 */
select:hover {
  background-color: #f9f9f9;
}
</style>
