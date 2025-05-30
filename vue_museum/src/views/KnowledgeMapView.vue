<template>

  <div class="main-container">

    <div id="viz"></div>

    <div class="container">

      <div class="control-container">

        <div class="controls">
          <textarea v-model="cypher" rows="4" cols="50"></textarea><br>
          <button @click="reload">Query</button>
          <button @click="stabilize">Stabilize</button>
        </div>

      </div>

      <div class="selects-container">
        <p>节点类型</p>
        <select v-model="selected1" >
          <option v-for="option in firstOptions" :key="option" :value="option.value">
            {{ option.text }}
          </option>
        </select>

        <input v-model="selected4" placeholder="名称" />

        <p>关系类型</p>
        <select v-model="selected2">
          <option v-for="option in secondOptions" :key="option" :value="option.value">
            {{ option.text }}
          </option>
        </select>



        <p>节点类型</p>
        <select v-model="selected3">
          <option v-for="option in thirdOptions" :key="option" :value="option.value">
            {{ option.text }}
          </option>
        </select>

        <input v-model="selected5" placeholder="名称" />

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
  const name1 = selected4.value
      ? (selected1.value === "Artifact"
          ? `{Title:"${selected4.value}"}`
          : `{name:"${selected4.value}"}`)
      : '';
  // const name1 = selected4.value ? `{name:"${selected4.value}"}` : '';
  const node1 = (selected1.value || selected4.value || selected2.value) ? `(n${label1} ${name1})` : ''

  // 关系
  const relation = selected2.value ? `-[r:${selected2.value}]->` : '';

  const label2 = selected3.value ? `:${selected3.value}` : '';
  const name2 = selected5.value
      ? (selected3.value === "Artifact"
          ? `{Title:"${selected5.value}"}`
          : `{name:"${selected5.value}"}`)
      : '';
  // const name2 = selected5.value ? `{name:"${selected5.value}"}` : '';
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
    LIMIT 300
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
  {text: '种类', value: 'Classification'},
  {text: '尺寸', value: 'Dimension'},
  {text: '材质', value: 'Material'},
  {text: '博物馆', value: 'Museum'},
  {text: '时期', value: 'Dynasty'},
  {text: '来源地', value: 'PlaceOri'},
  {text: '媒介', value: 'Medium'}
])

const secondOptions = ref([
  {text: '', value: ''},
  {text: '现藏博物馆', value: '现藏博物馆是'},
  {text: '创作者', value: '创作者是'},
  {text: '创造年代', value: '创造年代是'},
  {text: '媒介', value: '媒介是'},
  {text: '尺寸', value: '尺寸是'},
  {text: '材质', value: '材质是'},
  {text: '来源地', value: '来源地是'},
  {text: '类型', value: '类型是'}
])

const thirdOptions = ref([
  {text: '', value: ''},
  {text: '文物', value: 'Artifact'},
  {text: '作者', value: 'Artist'},
  {text: '种类', value: 'Classification'},
  {text: '尺寸', value: 'Dimension'},
  {text: '材质', value: 'Material'},
  {text: '博物馆', value: 'Museum'},
  {text: '时期', value: 'Dynasty'},
  {text: '来源地', value: 'PlaceOri'},
  {text: '媒介', value: 'Medium'}
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
    physics: {
      enabled: true, // 启用物理效果
      barnesHut: {
        gravitationalConstant: -3000, // 引力常数，负值表示斥力
        centralGravity: 0.3,     // 向中心的引力
        springLength: 95,        // 弹簧自然长度
        // springConstant: 0.04,    // 弹簧常数
        springConstant: 0.01,
        // damping: 0.09           // 阻尼系数
        damping: 0.29           // 阻尼系数
      },
      stabilization: {
        iterations: 1000,        // 稳定化迭代次数
        updateInterval: 50       // 更新间隔
      }
    },
    // layout: {
    //   improvedLayout: true
    // },
  },
  labels: {
    Artifact: {
      label: 'Title',
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          title: (node) => NeoVis.objectToTitleHtml(node, ["id", "Title", "Artist","Dynasty","CreditLine", "Dimensions", "Materials","Inscribed","Museum","PlaceOri","Classifications","Medium","Description"])
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
    Classification: {
      label: 'name',
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          title: (node) => NeoVis.objectToTitleHtml(node, ["name"])
        },

      }
    },
    Dimension: {
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
    Dynasty: {
      label: 'name',
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          title: (node) => NeoVis.objectToTitleHtml(node, ["name"])
        },
      }
    },
    Medium: {
      label: 'name',
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          title: (node) => NeoVis.objectToTitleHtml(node, ["name"])
        },
      }
    },
    PlaceOri: {
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
    "现藏博物馆是": {
      // value: "weight",
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        function: {
          label: (edge) => edge.type
        },
      }
    },
    "创作者是": {
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        cypher: {},
        function: {
          label: (edge) => edge.type
        },
        static: {}
      }
    },
    "创造年代是": {
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
    "材质是": {
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        function: {
          label: (edge) => edge.type
        },
      }
    },
    "类型是": {
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        function: {
          label: (edge) => edge.type
        },
      }
    },
    "媒介是": {
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        function: {
          label: (edge) => edge.type
        },
      }
    },
    "来源地是": {
      [NeoVis.NEOVIS_ADVANCED_CONFIG]: {
        function: {
          label: (edge) => edge.type
        },
      }
    }
  },

  // initialCypher: "MATCH (n)-[r]->(m) RETURN n,r,m ",
  initialCypher: "MATCH (n)-[r]->(m) RETURN n,r,m limit 300",
  // initialCypher: "MATCH (n:Artifact) RETURN n LIMIT 20",
});


// const initialNodeIds = ref(new Set());
const expandedNodes = ref(new Set());

let vis = null;
onMounted(() => {
  vis = new NeoVis(config.value)
  vis.render();

  vis.registerOnEvent("clickNode", (properties) => {

    const nodeId = properties.nodeId;
    if (!expandedNodes.value.has(nodeId)) {

      // 如果节点未展开，则展开
      expandedNodes.value.add(nodeId);
      // const finalNodes = new Set([ ...expandedNodes.value]);
      const cypher3 = `
        MATCH (n)-[r]-(m)
        WHERE ID(n) IN [${properties.nodeId}]
        RETURN n,r,m LIMIT 20
      `;
      vis.updateWithCypher(cypher3);
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
  //height: 100%;
  height: 92vh;
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
  height: auto;
  bottom: 0;

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
  //flex-direction: row;
  flex-direction: column;
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

select {
  margin: 5px 5px;
  /* 左右间距改为水平方向的间距 */
  padding: 8px;
  //width: 30%;
  width: 100%;
  /* 设置宽度为30%，使三个select平均分布 */
  border-radius: 4px;
  border: 1px solid #ddd;
}

input {
  margin: 5px 5px;
  padding: 8px;
  width: 100%;
  border-radius: 4px;
  border: 1px solid #ddd;
}

p {
  margin: 5px 5px;
  display: flex;
  align-items: center; /* 垂直居中 */
  justify-content: flex-start; /* 水平靠左 */
  font-size: smaller; /* 文字变小 */
  color: #aaa; /* 文字颜色变淡 */
}

</style>
