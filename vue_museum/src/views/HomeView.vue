<template>
    <div class="slidershow middle">
      <div class="slides">
        <input type="radio" name="r" id="r1" checked>
        <input type="radio" name="r" id="r2">
        <input type="radio" name="r" id="r3">
        <input type="radio" name="r" id="r4">
        <input type="radio" name="r" id="r5">
        <div class="slide s1">
          <img src="/img/image_HomeView/img5.jpg" alt="1">
        </div>
        <div class="slide">
          <img src="/img/image_HomeView/img6.jpg" alt="2">
        </div>
        <div class="slide">
          <img src="/img/image_HomeView/img7.jpg" alt="3">
        </div>
        <div class="slide">
          <img src="/img/image_HomeView/img4.jpg" alt="4">
        </div>
        <div class="slide">
          <img src="/img/image_HomeView/img3.jpg" alt="5">
        </div>
      </div>

      <div class="navigation">
        <label for="r1" class="bar"></label>
        <label for="r2" class="bar"></label>
        <label for="r3" class="bar"></label>
        <label for="r4" class="bar"></label>
        <label for="r5" class="bar"></label>
      </div>

    </div>
</template>


<!--<script>-->
<!--// @ is an alias to /src-->
<!--import HelloWorld from '@/components/HelloWorld.vue'-->
<!--import toLogout from '../components/toLogout.vue';-->

<!--export default {-->
<!--  name: 'HomeView',-->
<!--  components: {-->
<!--    HelloWorld,-->
<!--    toLogout,-->
<!--  },-->
<!--  computed: {-->
<!--    authenticated() {-->
<!--      return localStorage.getItem('jwt');-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->
<!--.home {-->
<!--  display: flex;-->
<!--  flex-direction: column;-->
<!--  text-align: center;-->
<!--}-->
<!--</style>-->

<!--<script setup>-->
<!--</script>-->


<style scoped>
body {
  margin: 0;
  padding: 0;
  background: #27ae60;
}


.slidershow {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.middle {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.navigation {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;

  z-index: 1;
}

.bar {
  width: 50px;
  height: 10px;
  border: 1px solid #fff;
  margin: 6px;
  cursor: pointer;
  transition: 0.4s;

}

.bar:hover {
  background: #fff;
}

input[name="r"] {
  visibility: hidden;
  position: absolute;
}

.slides {
  width: 500%;
  height: 100%;
  display: flex;
}

.slide {
  width: 20%;
  transition: 0.6s;
}

.slide img {
  width: 100%;
  height: 100%;

  object-fit: cover;
}

#r1:checked~.s1 {
  margin-left: 0;
}

#r2:checked~.s1 {
  margin-left: -20%;
}

#r3:checked~.s1 {
  margin-left: -40%;
}

#r4:checked~.s1 {
  margin-left: -60%;
}

#r5:checked~.s1 {
  margin-left: -80%;
}
</style>
