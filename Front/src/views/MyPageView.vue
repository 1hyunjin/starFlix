<script setup>
import Header from '@/component/common/Header.vue';
import Meteo from '@/component/common/Meteo.vue';
import Weather from '@/component/common/Weather.vue';
import { useRoute, useRouter } from 'vue-router';
import { RouterLink, RouterView } from 'vue-router';
import { ref, watch } from 'vue';
const test = 10;
const route = useRoute();
const router = useRouter();

const type = route.query.type;
const activeMenu = ref(type);

function setActiveMenu(menu) {
  router.push({ name: menu, query: { type: menu } });
  activeMenu.value = menu;
}
console.log(type);
watch(
  //동적라우팅 화면전환 안되는 부분 watch로 해결
  () => route.query.type,
  (nv, ov) => {
    console.log('??' + ov, nv);
    activeMenu.value = nv;
  },
  {
    immediate: true,
  }
);
</script>

<template>
  <div class="content">
    <Header />
    <section class="PageTitle container-fruid">
      <div class="modalDiv">
        <Meteo />
        <Weather />
      </div>
      <div class="titleBox row">
        <div class="col-12 col-sm-7">
          <h1 class="title">마이 페이지</h1>
          <p class="text">
            회정원보 관리 및 사용자가 작성한 콘텐츠를 확인합니다.
          </p>
        </div>
      </div>
    </section>
    <section class="container-fluid">
      <ul class="myMenu">
        <li
          :class="{ active: activeMenu === 'userInfo' }"
          @click="setActiveMenu('userInfo')"
        >
          회원 정보 관리
        </li>
        <li
          :class="{ active: activeMenu === 'myPlaces' }"
          @click="setActiveMenu('myPlaces')"
        >
          등록한 별 명소
        </li>
        <li
          :class="{ active: activeMenu === 'myReviews' }"
          @click="setActiveMenu('myReviews')"
        >
          등록한 명소 리뷰
        </li>
      </ul>
      <div class="hr"></div>
      <RouterView />
    </section>
  </div>
</template>

<style scoped>
@media (max-width: 550px) {
  .myMenu {
    flex-direction: column;
    text-align: center;
  }
}
@media (max-width: 750px) {
  .myMenu > li {
    font-size: 20px !important;
  }
}
@media (max-width: 790px) {
  .content {
    background-size: 300% !important;
  }
  .PageTitle {
    height: 250px !important;
  }
  .titleBox {
    width: 100% !important;
    top: 120px !important;
    left: 0px !important;
  }
  table {
    font-size: 10px !important;
  }
  .btnArea {
    flex-direction: column;
  }
}
.content {
  min-height: 100vh;
  background-image: url('@/assets/img/mypageImg.png');
  background-repeat: no-repeat;
  background-size: 100%;
  background-position: top;
  overflow: auto;
}
.PageTitle {
  height: 350px;
}
.titleBox {
  width: 90%;
  padding-left: 20px;
  position: relative;
  display: flex;
  justify-content: space-between;
  top: 230px;
  left: 60px;
}
.title {
  color: white;
  font-weight: bold;
}
.text {
  color: white;
}
section {
  width: 100%;
  margin-bottom: 20px;
}
.modalDiv {
  position: absolute;
  width: 500px;
  right: 0;
  height: 50px;
  display: flex;
  justify-content: space-around;
}

/*  */

.myMenu {
  width: 95%;
  margin: 0 auto;
  list-style: none;
  color: white;
  font-size: 30px;
  font-weight: bold;
  display: flex;
}
.myMenu > li {
  margin-right: 30px;
  cursor: pointer;
  color: #757575;
}
.active {
  color: white !important;
}
.hr {
  width: 95%;
  margin: 10px auto;
}
</style>
