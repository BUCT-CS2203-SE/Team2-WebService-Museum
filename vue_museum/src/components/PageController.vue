<!-- src/components/Pagination.vue -->
<template>
  <div class="pagination" :class="{ 'pagination--background': background }">
    <!-- 上一页 -->
    <button
      class="pagination__btn"
      :disabled="currentPage <= 1"
      @click="go(currentPage - 1)"
    >‹</button>

    <!-- 页码 -->
    <button
      v-for="page in pages"
      :key="page + '-' + Math.random()"
      class="pagination__item"
      :class="{ 'is-active': page === currentPage, 'is-ellipsis': page === '...' }"
      :disabled="page === '...'"
      @click="page !== '...' && go(page)"
    >
      {{ page }}
    </button>

    <!-- 下一页 -->
    <button
      class="pagination__btn"
      :disabled="currentPage >= pageCount"
      @click="go(currentPage + 1)"
    >›</button>

    <!-- 输入页码跳转 -->
    <div class="pagination__jump">
      跳至&nbsp;
      <input
        type="number"
        v-model.number="inputPage"
        @keydown.enter="go(inputPage)"
        :min="1"
        :max="pageCount"
      />
      &nbsp;页
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits, computed, toRefs, ref, watch } from 'vue'

const props = defineProps({
  total: { type: Number, required: true },
  pageSize: { type: Number, default: 10 },
  modelValue: { type: Number, default: 1 },
  background: { type: Boolean, default: false }
})
const emit = defineEmits(['update:modelValue'])

const { total, pageSize, modelValue } = toRefs(props)

// 计算总页数
const pageCount = computed(() => Math.ceil(total.value / pageSize.value))

// 当前页（v-model 绑定）
const currentPage = computed({
  get: () => {
    const v = modelValue.value
    return v < 1 ? 1 : v > pageCount.value ? pageCount.value : v
  },
  set: v => emit('update:modelValue', v)
})

// 输入框绑定的页码
const inputPage = ref(currentPage.value)
watch(currentPage, val => { inputPage.value = val })

// 生成页码数组，保证大于 7 时只包含 7 个显示项
const pages = computed(() => {
  const total = pageCount.value
  const cur = currentPage.value
  const max = 7

  if (total <= max) {
    // 总页数 <=7，显示全部
    return Array.from({ length: total }, (_, i) => i + 1)
  }

  // 总页数 >7 时
  const result = []

  // 第一页
  result.push(1)

  // 左侧省略
  if (cur > 4) {
    result.push('...')
  }

  // 中间页码
  if (cur <= 4) {
    // 当前页靠左，显示 2,3,4,5
    for (let i = 2; i <= 5; i++) result.push(i)
  } else if (cur >= total - 3) {
    // 当前页靠右，显示 total-4..total-1
    for (let i = total - 4; i < total; i++) result.push(i)
  } else {
    // 当前页居中，显示 cur-1,cur,cur+1
    result.push(cur - 1, cur, cur + 1)
  }

  // 右侧省略
  if (cur < total - 3) {
    result.push('...')
  }

  // 最后一页
  result.push(total)

  return result
})

// 跳转函数
function go(page) {
  const p = Number(page)
  if (Number.isInteger(p) && p >= 1 && p <= pageCount.value) {
    currentPage.value = p
  }
}
</script>

<style scoped>
.pagination {
  display: inline-flex;
  align-items: center;
  font-size: 16px;
  gap: 6px;
  flex-wrap: wrap;
}
.pagination--background {
  background: #f5f7fa;
  padding: 4px 8px;
  border-radius: 4px;
}
.pagination__btn,
.pagination__item {
  /* width: 36px;
  height: 32px; */
  border: 1px solid #dcdfe6;
  background: #fff;
  color: #606266;
  padding: 6px 8px;
  margin: 0 2px;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s;
}
.pagination__btn[disabled],
.pagination__item[disabled] {
  cursor: not-allowed;
  color: #c0c4cc;
  border-color: #ebeef5;
}
.pagination__btn:not([disabled]):hover,
.pagination__item:not([disabled]):hover {
  border-color: #409eff;
  color: #409eff;
}
.pagination__item.is-active {
  background: #409eff;
  border-color: #409eff;
  color: #fff;
}
.pagination__item.is-ellipsis {
  cursor: default;
  color: #909399;
  border: none;
  background: transparent;
}
.pagination__jump {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  margin-left: 10px;
}
.pagination__jump input {
  width: 50px;
  height: 20px;
  padding: 2px 4px;
  border: 1px solid #dcdfe6;
  border-radius: 2px;
  text-align: center;
}
</style>
