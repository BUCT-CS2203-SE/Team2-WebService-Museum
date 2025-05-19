<template>
  <a-table
    :columns="computedColumns"
    :data-source="props.data"
    :row-key="record => record.id"
    bordered
    size="middle"
    :pagination="false"
    :scroll="{ y: 480, x: 'max-content' }"
    class="hover-action-table"
  >
    <template #bodyCell="{ column, record }">
      <template v-if="column.key === actionKey">
        <div class="hover-cell-wrapper">
          <span class="cell-text">{{ record[column.dataIndex] }}</span>
          <div class="floating-actions">
            <a-button type="link" @click.stop="emit('view', record)">查看</a-button>
            <a-button type="link" danger @click.stop="emit('delete', record)">删除</a-button>
          </div>
        </div>
      </template>
    </template>
  </a-table>
</template>

<script setup>
import { defineProps, defineEmits, computed } from 'vue';

const props = defineProps({
  data: {
    type: Array,
    default: () => []
  },
  /**
   * 字段解释：
   * title  列名
   * dataIndex  key   数据字段名
   * widthRatio   列宽比例默认为1
   */
  columns: {
    type: Array,
    default: () => [
      { title: '编号', dataIndex: 'id', key: 'id' ,widthRatio: 1},
      { title: '文物名称', dataIndex: 'name', key: 'name'  ,widthRatio: 1},
      { title: '收藏时间', dataIndex: 'time', key: 'time'  ,widthRatio: 1}
    ]
  },
  actionKey: {
    type: String,
    default: 'time' // 默认在哪列显示按钮，你也可以改成别的
  }
});

const emit = defineEmits(['view', 'delete']);

// 给传入的 columns 增加居中对齐属性
// const computedColumns = computed(() => {
//   return props.columns.map(col => ({
//     ...col,
//     align: 'center'
//   }));
// });
const computedColumns = computed(() => {
  // 计算所有列的比率总和
  const totalRatio = props.columns.reduce(
    (sum, col) => sum + (col.widthRatio || 1), 0
  );
  
  // 生成新的列配置，带有 width 属性（百分比）
  return props.columns.map(col => {
    const ratio = col.widthRatio || 1;
    const percent = ((ratio / totalRatio) * 100).toFixed(2) + '%';
    return {
      ...col,
      width: percent,
      align: 'center'   // 保留你已有的样式
    };
  });
});
</script>
<style scoped>
.hover-action-table {
  /* 限制最大宽度，防止撑开 */
  width: 100%;
  max-width: 100%;
  overflow-x: auto; /* 让容器出现滚动而不是表格撑宽 */
}

/* 单元格内容容器 */
.hover-cell-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  overflow: hidden; /* 超出隐藏 */
}

/* 内容文字溢出省略 */
.cell-text {
  width: 100%;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;       /* 允许换行 */
  word-break: break-word;    /* 长单词或 URL 换行 */
}

/* 操作按钮，绝对定位脱离文档流 */
.floating-actions {
  position: absolute;
  right: 4px;
  top: 50%;
  transform: translateY(-50%) translateX(20px);
  display: flex;
  gap: 8px;
  opacity: 0;
  transition: all 0.3s ease;
  z-index: 10;
  background: white;
  padding: 2px 6px;
  border-radius: 4px;
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.12);
  pointer-events: none;
  font-size: 14px;
}

/* 只有悬浮时显示按钮且滑入 */
:deep(.ant-table-row:hover) .floating-actions {
  opacity: 1;
  transform: translateY(-50%) translateX(0);
  pointer-events: auto;
}
</style>