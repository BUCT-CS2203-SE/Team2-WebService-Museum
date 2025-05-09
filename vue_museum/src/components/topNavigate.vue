<!-- 顶部导航栏组件 -->
<template>

  <div class="topnav">
    <div class="box1">
      海外文物知识服务子系统
    </div>
    <div class="box2">
      <div class="box3">
        <ul><router-link to="/">首页</router-link> </ul>
        <span class="aline"></span>
      </div>
      <div class="box3">
        <ul><router-link to="/search">文物搜索</router-link></ul>
        <span class="aline"></span>
      </div>
      <div class="box3">
        <ul><router-link to="/timebar">时间轴</router-link></ul>
        <span class="aline"></span>
      </div>
      <div class="box3">
        <ul><router-link to="/knowledgemap">知识图谱</router-link></ul>
        <span class="aline"></span>
      </div>
      <div class="box3">
        <ul><router-link to="/userinfo">用户信息</router-link></ul>
        <span class="aline"></span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.topnav {
  padding: 0px 10px;
  background-color: #a4fcd4;
  position: relative;
  height: 60px;
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 2fr;
  align-items: center;
  font-size: large;
  gap: 80px;
}
.topnav .box1{
  font-size: larger;
  position: relative;
  text-align: center;
  letter-spacing: 10px;
}
.topnav .box2{
  display: grid;
  height: inherit;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  padding: 0px 48px;
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
  padding: 6px;
  align-content: center;
  text-align: center;
}
.box3 a{
  text-decoration: none;
  font-style: none;
  color: black;
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


</style>