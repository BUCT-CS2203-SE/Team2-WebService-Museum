<!-- 顶部导航栏组件 -->
<template>
  <div class="topnav">
    <div class="box1">
      海外文物知识服务子系统
    </div>
    <div class="box2">
      <div class="box3">
        <ul><router-link class="nav-link" active-class="active" to="/">首页</router-link> </ul>
        <span class="aline"></span>
      </div>
      <div class="box3">
        <ul><router-link class="nav-link" active-class="active" to="/search">文物搜索</router-link></ul>
        <span class="aline"></span>
      </div>
      <div class="box3">
        <ul><router-link class="nav-link" active-class="active" to="/timebar">时间轴</router-link></ul>
        <span class="aline"></span>
      </div>
      <div class="box3">
        <ul><router-link class="nav-link" active-class="active" to="/knowledgemap">知识图谱</router-link></ul>
        <span class="aline"></span>
      </div>
      <div class="box3">
        <ul><router-link class="nav-link" active-class="active" to="/userinfo">用户信息</router-link></ul>
        <span class="aline"></span>
      </div>
      <div class="box3">
        <ul><router-link class="nav-link" active-class="active" to="/tologout">注销</router-link></ul>
        <span class="aline"></span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.topnav {
  padding: 0px 10px;
  background-color: rgb(17, 17, 17);
  position: relative;
  height: 65px;
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 2fr;
  align-items: center;
  font-size: large;
  gap: 60px;
  top: 0;          /* 固定在页面顶部 */
  z-index: 9999;   /* 确保在最上层 */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3); /* 添加阴影 */
}
.topnav .box1{
  font-size: larger;
  position: relative;
  text-align: center;
  letter-spacing: 10px;
  color: #ffffff;
}
.topnav .box2{
  display: grid;
  height: inherit;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr 1fr;
  padding: 0px 20px;
  gap: 8px;
}
.box3 {
  position: relative;
  display: inline-block;
  padding: 0.5em 1em;
  align-content: center;
}
.box3 ul{
  margin: 0px;
  padding: 4px;
  align-content: center;
  text-align: center;
}
.box3 a{
  text-decoration: none;
  color: #FFFFFF;
  padding: 0px;
}
.box3 .aline{
  position: absolute;
  bottom: 0;
  left: 0;
  height: 4px;              /* 横线高度 */
  width: 100%;                 /* 初始不可见 */
  background-color: #0087ca;/* 蓝色横线 */
  transform: scaleX(0);/* 从中心收缩至 0 */
  transform-origin: center;   /* 缩放基点在中心 */
  transition: transform 0.3s ease;
}
.box3:hover .aline{
  transform: scaleX(80%);
}

.nav-link.active {
  color: #0087ca;
}

</style>
<script setup>
</script>
