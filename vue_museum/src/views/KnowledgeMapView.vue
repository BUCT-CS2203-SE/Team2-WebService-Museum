<template>
  <div class="container">
    <div id="viz"></div>
    <div class="controls">
      <textarea v-model="cypher" rows="4" cols="50"></textarea><br>
      <button @click="reload">Submit</button>
      <button @click="stabilize">Stabilize</button>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import NeoVis from 'neovis.js';

const cypher = ref("");

const config = ref({
  containerId: "viz",
  neo4j: {
    serverUrl: "bolt://localhost:7687",
    serverUser: "neo4j",
    serverPassword: "Cs22032025",
  },
  labels: {
    Artifact: {
      label: "name"
    },
    Artist: {
      label: "name"
    },
    Category: {
      label: "name"
    },
    Dimensions: {
      label: "name"
    },
    Material: {
      label: "name"
    },
    Museum: {
      label: "name"
    },
    Time: {
      label: "name"
    }
  },
  relationships: {
    "位于": {
      label: "name"
    },
    "创作者为": {
      label: "name"
    },
    "处于的年代": {
      label: "name"
    },
    "尺寸为": {
      label: "name"
    },
    "材质为": {
      label: "name"
    },
    "类型为": {
      label: "name"
    }
  },
  initialCypher: "MATCH (n)-[r]->(m) RETURN n,r,m LIMIT 150",
});

let vis = null;
onMounted(() => {
  vis = new NeoVis(config.value)
  // const vis = new NeoVis(config.value);
  vis.render();
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
.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.controls {
  margin-top: 20px;
}

/* //.myDiv {
//  width: 100%;
//  //height: 800px;
//  position: relative;
//} */

textarea {
  width: 100%;
  border: 1px solid lightgray;
  margin: 10px 0;
  border-radius: 5px;
}

#viz {
  width: 100%;
  height: 590px;
  border: 1px solid #f1f3f4;
  font: 16px Arial, sans-serif;
}

button {
  margin-right: 10px;
  padding: 8px 16px;
  border: none;
  background-color: #007bff;
  color: white;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>

