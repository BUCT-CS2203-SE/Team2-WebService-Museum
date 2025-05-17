<!-- src/components/DateInput.vue -->
<template>
    <a-input
    class="datesty"
      v-model:value="innerValue"
      type="date"
      :placeholder="props.placeholder"
      allow-clear
      style="width: 100%;"
      @change="onChange"
    />
</template>

<script setup>
import { ref, watch, defineProps, defineEmits } from 'vue';

// 获取今天日期（YYYY-MM-DD 格式）
function getTodayDate() {
  const today = new Date();
  const yyyy = today.getFullYear();
  const mm = String(today.getMonth() + 1).padStart(2, '0');
  const dd = String(today.getDate()).padStart(2, '0');
  return `${yyyy}-${mm}-${dd}`;
}

const props = defineProps({
  modelValue: { type: String, default: '' },
  placeholder: { type: String, default: '' }
});

const emit = defineEmits(['update:modelValue', 'change']);

// 默认值为今天，如果外部没传入值
const innerValue = ref(props.modelValue || getTodayDate());

watch(
  () => props.modelValue,
  v => {
    if (v !== innerValue.value) {
      innerValue.value = v;
    }
  }
);

function onChange(e) {
  const v = e.target && e.target.value ? e.target.value : '';
  emit('update:modelValue', v);
  emit('change', v);
}
</script>

<style scoped>
.datesty{
    max-width: 160px;
    padding: 0px 20px;
}
</style>