<template>
    <div class="museum-timeline">
      <!-- 加载状态 -->
      <div v-if="loading" class="loading">加载中...</div>
      
      <!-- 错误提示 -->
      <div v-if="error" class="error">{{ error }}</div>
  
      <!-- 主内容 -->
      <div v-else>
        <div class="header">
          <h1>{{ museumName }}数字时间轴</h1>
          <div class="filters">
            <!-- 朝代筛选 -->
            <select v-model="selectedDynasty" @change="fetchRelics">
              <option value="">全部朝代</option>
              <option v-for="dynasty in dynasties" :key="dynasty">{{ dynasty }}</option>
            </select>
  
            <!-- 分类筛选 -->
            <select v-model="selectedCategory" @change="fetchRelics">
              <option value="">全部分类</option>
              <option v-for="category in categories" :key="category">{{ category }}</option>
            </select>
          </div>
        </div>
  
        <!-- 时间轴展示 -->
        <div class="timeline">
          <div 
            v-for="item in relics" 
            :key="item.id"
            class="timeline-item"
            :style="{ '--timeline-color': getDynastyColor(item.dynasty) }"
            @click="showDetail(item)"
          >
            <!-- 时间轴项内容 -->
          </div>
        </div>
  
        <!-- 详情弹窗 -->
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  
  // API基础配置
  const API_BASE = import.meta.env.VITE_API_BASE || 'http://api.example.com'
  const museumId = 'museum_001' // 当前博物馆ID
  
  /**
   * 接口说明：
   * 1. 获取时间轴数据
   * 路径：GET /api/timeline
   * 参数：
   *   - museumId: 博物馆ID (必需)
   *   - dynasty: 朝代筛选
   *   - category: 分类筛选
   * 返回数据结构：
   * {
   *   code: number,
   *   data: {
   *     relics: Array<{
   *       id: string,
   *       name: string,
   *       dynasty: string,
   *       dateRange: string,
   *       category: string,
   *       image: string,
   *       description: string,
   *       dimensions: string,
   *       material: string,
   *       excavationDate: string
   *     }>,
   *     dynasties: string[],
   *     categories: string[]
   *   },
   *   message: string
   * }
   */
  
  // 响应式数据
  const museumName = ref('中国国家博物馆')
  const relics = ref([])
  const dynasties = ref([])
  const categories = ref([])
  const selectedDynasty = ref('')
  const selectedCategory = ref('')
  const selectedRelic = ref(null)
  const loading = ref(false)
  const error = ref('')
  
  // 初始化获取数据
  onMounted(() => {
    fetchRelics()
  })
  
  // 获取文物数据
  const fetchRelics = async () => {
    try {
      loading.value = true
      error.value = ''
      
      const response = await axios.get(`${API_BASE}/api/timeline`, {
        params: {
          museumId,
          dynasty: selectedDynasty.value,
          category: selectedCategory.value
        }
      })
  
      if (response.data.code === 200) {
        relics.value = response.data.data.relics
        dynasties.value = response.data.data.dynasties
        categories.value = response.data.data.categories
      } else {
        error.value = '数据加载失败: ' + response.data.message
      }
    } catch (err) {
      error.value = '网络请求异常: ' + err.message
    } finally {
      loading.value = false
    }
  }
  
  /**
   * 接口说明：
   * 2. 获取文物详情
   * 路径：GET /api/relic/{id}
   * 参数：无
   * 返回数据结构：
   * {
   *   code: number,
   *   data: {
   *     ...文物详情字段,
   *     3dModelUrl: string, // 3D模型地址
   *     relatedRelics: Array<string> // 相关文物ID列表
   *   },
   *   message: string
   * }
   */
  const showDetail = async (item) => {
    try {
      const response = await axios.get(`${API_BASE}/api/relic/${item.id}`)
      if (response.data.code === 200) {
        selectedRelic.value = response.data.data
      }
    } catch (err) {
      error.value = '获取详情失败: ' + err.message
    }
  }
  
  // 朝代颜色映射
  const dynastyColors = {
    '商朝': '#cd6133',
    '周朝': '#2c3e50',
    '秦朝': '#c44569',
    '汉朝': '#e67e22'
  }
  
  const getDynastyColor = (dynasty) => {
    return dynastyColors[dynasty] || '#3498db'
  }
  </script>
  
  <style>
  /* 样式部分与之前版本保持一致 */
  </style>