<template>
  <div>
    <div id="viz" ref="viz" class="viz-container"></div>
    <div class="controls">
      <label>Cypher query:</label>
      <textarea rows="4" cols="50" v-model="cypherQuery"></textarea><br>
      <button @click="handleReload">Submit</button>
      <button @click="handleStabilize">Stabilize</button>
    </div>
  </div>
</template>

<script>
import NeoVis from 'neovis.js';

export default {
  name: 'KGraph',
  data() {
    return {
      cypherQuery: 'MATCH (n)-[r:INTERACTS]->(m) RETURN n,r,m',
      viz: null
    };
  },
  mounted() {
    this.initGraph();
  },
  methods: {
    initGraph() {
      const config = {
        containerId: this.$refs.viz,
        neo4j: {
          serverUrl: 'bolt://localhost:7687',
          serverUser: 'neo4j',
          serverPassword: '123456789'
        },
        labels: {
          Character: {
            label: 'name',
            value: 'pagerank',
            group: 'community'
          }
        },
        relationships: {
          INTERACTS: {
            value: 'weight'
          }
        },
        initialCypher: this.cypherQuery
      };

      this.viz = new NeoVis(config);
      this.viz.render();
    },
    handleReload() {
      if (this.cypherQuery.length > 3) {
        this.viz.renderWithCypher(this.cypherQuery);
      } else {
        this.viz.reload();
      }
    },
    handleStabilize() {
      this.viz.stabilize();
    }
  }
};
</script>

<style scoped>
.viz-container {
  width: 900px;
  height: 700px;
  border: 1px solid lightgray;
  font: 22pt arial;
  margin-bottom: 20px;
}
.controls {
  font: 16pt arial;
  margin-bottom: 10px;
}
</style>
