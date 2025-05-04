<template>
  <div class="hello">
    <h3> {{ msg }}</h3>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  font-display: flex;
  font-size: 32px;
  flex-direction: column;
  align-content: center;
}
</style>
