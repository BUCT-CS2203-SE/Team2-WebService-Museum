<template>

  <div class="main-container">

    <div id="viz"></div>

    <div class="container">

      <div class="control-container">

        <div class="controls">
          <textarea v-model="cypher" rows="4" cols="50"></textarea><br>
          <button @click="reload">Submit</button>
          <button @click="stabilize">Stabilize</button>
        </div>

      </div>

      <div class="selects-container">

        <select v-model="selected1">
          <option v-for="option in firstOptions" :key="option" :value="option.value">
            {{ option.text }}
          </option>
        </select>

        <select v-model="selected2">
          <option v-for="option in secondOptions" :key="option" :value="option.value">
            {{ option.text }}
          </option>
        </select>

        <select v-model="selected3">
          <option v-for="option in thirdOptions" :key="option" :value="option.value">
            {{ option.text }}
          </option>
        </select>

      </div>
      <div class="selects-container">

        <select v-model="selected4">
          <option v-for="option in fourthOptions" :key="option" :value="option.value">
            {{ option.text }}
          </option>
        </select>

        <select v-model="selected5">
          <option v-for="option in fifthOptions" :key="option" :value="option.value">
            {{ option.text }}
          </option>
        </select>


      </div>
    </div>

  </div>

</template>

<script setup>

import {ref, onMounted, computed} from 'vue'

import NeoVis from 'neovis.js';


const cypher = computed(() => {
  // label是类别,name是名称
  const label1 = selected1.value ? `:${selected1.value}` : '';
  const name1 = selected4.value ? `{name:${selected4.value}}` : '';
  const node1 = (selected1.value || selected4.value || selected2.value) ? `(n${label1} ${name1})` : ''

  // 关系
  const relation = selected2.value ? `-[r:${selected2.value}]->` : '';

  const label2 = selected3.value ? `:${selected3.value}` : '';
  const name2 = selected5.value ? `{name:${selected5.value}}` : '';
  //const node2 = (selected3.value||selected5.value) ? `(m${label2} ${name2})` : '';
  const node2 = (selected3.value || selected5.value || selected2.value) ? `(m${label2} ${name2})` : ''

  //返回的 n,r,m
  const cyReturnN = selected1.value || selected2.value ? 'n' : '';
  const cyReturnR = selected2.value ? 'r' : '';
  const cyReturnM = selected2.value || selected3.value ? 'm' : '';
  // 逗号
  const cyReturnComma1 = selected2.value || (selected1.value && selected3.value) ? ',' : '';
  const cyReturnComma2 = selected2.value ? ',' : '';
  const cyReturn = `${cyReturnN} ${cyReturnComma1} ${cyReturnR} ${cyReturnComma2} ${cyReturnM}`;

  return `
    MATCH ${node1}${relation}${node2}
    RETURN ${cyReturn}
    LIMIT 60
  `
})

const selected1 = ref('')
const selected2 = ref('')
const selected3 = ref('')
const selected4 = ref('')
const selected5 = ref('')


const firstOptions = ref([
  {text: '', value: ''},
  {text: '文物', value: 'Artifact'},
  {text: '作者', value: 'Artist'},
  {text: '种类', value: 'Category'},
  {text: '尺寸', value: 'Dimensions'},
  {text: '材质', value: 'Material'},
  {text: '博物馆', value: 'Museum'},
  {text: '时期', value: 'Time'}
])

const secondOptions = ref([
  {text: '', value: ''},
  {text: '位于', value: '位于'},
  {text: '创作者', value: '创作者为'},
  {text: '处于的年代', value: '处于的年代'},
  {text: '尺寸', value: '尺寸为'},
  {text: '材质', value: '材质为'},
  {text: '类型', value: '类型为'}
])

const thirdOptions = ref([
  {text: '', value: ''},
  {text: '文物', value: 'Artifact'},
  {text: '作者', value: 'Artist'},
  {text: '种类', value: 'Category'},
  {text: '尺寸', value: 'Dimensions'},
  {text: '材质', value: 'Material'},
  {text: '博物馆', value: 'Museum'},
  {text: '时期', value: 'Time'}
])

const fourthOptions = ref([
  {text: '', value: ''},
  // { text: '中式鼻烟壶', value: '中式鼻烟壶' }
])

const fifthOptions = ref([
  {text: '', value: ''},
  // { text: '罗汉头像', value: '罗汉头像' }
])

const config = ref({

  containerId: "viz",

  neo4j: {
    serverUrl: "bolt://localhost:7687",
    serverUser: "neo4j",
    serverPassword: "Cs22032025",
  },

  visConfig: {
    nodes: {
      shape: "ellipse",
      //shape: "dot"
    },
    edges: {
      // arrows: {
      //   // 箭头
      //   to: { enabled: true }
      // }
    },
    layout: {
      improvedLayout: true
    },
    //树状图
    // layout: {
    //   hierarchical: {
    //     enabled: true,
    //     // 严格按照父子关系划分
    //     // sortMethod: 'directed'
    //   }
    // }


  },
  labels: {
    Artifact: {
      label: 'name',
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          title: (node) => NeoVis.objectToTitleHtml(node, ["id", "name", "type", "Classifications", "Artist", "Description", "Credit", "Materials", "Dimensions", "Dynasty", "Title",])
        },
        static: {
          // shape: "image",
        }
      }
    },
    Artist: {
      label: 'name',
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          title: (node) => NeoVis.objectToTitleHtml(node, ["name"])
        },

      }
    },
    Category: {
      label: 'name',
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          title: (node) => NeoVis.objectToTitleHtml(node, ["name"])
        },

      }
    },
    Dimensions: {
      label: 'name',
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          title: (node) => NeoVis.objectToTitleHtml(node, ["name"])
        },

      }
    },
    Material: {
      label: 'name',
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          title: (node) => NeoVis.objectToTitleHtml(node, ["name"])
        },
      }
    },
    Museum: {
      label: 'name',
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          title: (node) => NeoVis.objectToTitleHtml(node, ["name"])
        },
      }
    },
    Time: {
      label: 'name',
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          title: (node) => NeoVis.objectToTitleHtml(node, ["name"])
        },
      }
    }
  },


  relationships: {
    "位于": {
      // value: "weight",
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        function: {
          label: (edge) => edge.type
        },
      }
    },
    "创作者为": {
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          label: (edge) => edge.type
        },
        static: {}
      }
    },
    "处于的年代": {
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        function: {
          label: (edge) => edge.type
        },
      }
    },
    "尺寸为": {
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        function: {
          label: (edge) => edge.type
        },
      }
    },
    "材质为": {
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        function: {
          label: (edge) => edge.type
        },
      }
    },
    "类型为": {
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        function: {
          label: (edge) => edge.type
        },
      }
    }
  },


  initialCypher: "MATCH (n)-[r]->(m) RETURN n,r,m LIMIT 30",
});


const initialNodeIds = ref(new Set());
const expandedNodes = ref(new Set());

let vis = null;
onMounted(() => {
  vis = new NeoVis(config.value)
  vis.render();


  // // 监听初始渲染完成事件，记录初始节点
  // vis.registerOnEvent("completed", (e) => {
  //   const network = vis.network;
  //   // 使用 vis.js 网络的原生事件监听稳定状态
  //   network.on("stabilizationIterationsDone", () => {
  //     const nodes = network.body.data.nodes;
  //     // 更新展开节点集合，只保留当前存在的节点
  //     expandedNodes.value = new Set(
  //         Array.from(expandedNodes.value).filter(id => nodes.get(id) !== undefined)
  //     );
  //   });
  // });


  // 注册节点点击展开事件

  vis.registerOnEvent("clickNode", (properties) => {
    const nodeId = properties.nodeId;
    if (expandedNodes.value.has(nodeId)) {
      // 如果节点已展开，则收起
      expandedNodes.value.delete(nodeId);
      // const expandedNodesArray = Array.from(expandedNodes.value);

      // 确保保留初始节点
      const finalNodes = new Set([...initialNodeIds.value, ...expandedNodes.value]);
      const cypher = `
        MATCH (n)-[r]-(m)
        WHERE ID(n) IN [${Array.from(finalNodes).join(',')}]
        RETURN n,r,m LIMIT 60
      `;
      vis.updateWithCypher(cypher);
    } else {

      // 如果节点未展开，则展开
      expandedNodes.value.add(nodeId);
      const finalNodes = new Set([...initialNodeIds.value, ...expandedNodes.value]);
      const cypher = `
        MATCH (n)-[r]-(m)
        WHERE ID(n) IN [${Array.from(finalNodes).join(',')}]
        RETURN n,r,m LIMIT 60
      `;
      vis.updateWithCypher(cypher);
    }
  });


});

// 重新加载图谱
const reload = () => {
  if (cypher.value.length > 3) {
    vis.renderWithCypher(cypher.value);  // 使用用户输入的 Cypher 查询
  } else {
    console.log("reload");
    vis.reload();  // 没有自定义查询时，重新加载默认图谱
  }
};

// 稳定视图
const stabilize = () => {
  vis.stabilize();
};

</script>


<style scoped>
#viz {
  width: 100%;
  height: 95vh;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.main-container {
  display: flex;
  /* 启用弹性布局 */
  flex-direction: row;
  /* 设置主轴方向为水平 */
  gap: 20px;
  /* 设置子元素之间的间距 */
  min-height: 95vh;
  height: auto;
  /* 设置容器高度为视口高度 */
  padding: 10px;
  /* 设置内边距 */
}

.container {
  background-color: #ebe9e9;
  box-shadow: 0 0 2px;
  border-radius: 20px;
  padding: 15px;
  width: 25%;
}

.control-container {
  width: 90%;
  margin: 5px 0;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
  align-items: center;
}

.selects-container {
  width: 90%;
  margin: 10px 0;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);

  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.controls {
  width: 100%;
  text-align: center;
}

textarea {
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  resize: vertical;
}

button {
  padding: 8px 16px;
  margin: 0 5px;
  background-color: #f0f0f0;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

button:hover {
  background-color: #e0e0e0;
}

//.graph {
//  /* 设置元素底部的外边距为10像素 */
//  margin-bottom: 10px;
//  align-self: flex-end;
//  /* 将 div 放置在右侧 */
//  margin-right: 10px;
//  display: inline-block;
//}

select {
  margin: 0 5px;
  /* 左右间距改为水平方向的间距 */
  padding: 8px;
  width: 30%;
  /* 设置宽度为30%，使三个select平均分布 */
  border-radius: 4px;
  border: 1px solid #ddd;
}
</style>
