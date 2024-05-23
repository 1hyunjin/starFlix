<script setup>
import Header from '@/component/common/Header.vue';
import MainList from '@/component/common/MainList.vue';
import Meteo from '@/component/common/Meteo.vue';
import Weather from '@/component/common/Weather.vue';
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { useMapStore } from '@/stores/mapStore';
import { storeToRefs } from 'pinia';
const memberStore = useUserStore();
const mapStore = useMapStore();
const { userInfo } = memberStore;
const { info } = mapStore;
const {
  myAddress,
  bestList,
  placeList,
  isSearch,
  isAround,
  isResult,
  isResultDetail,
} = storeToRefs(mapStore);

const sub1 = 'BEST 별자리 명소';
const sub2 = ref(userInfo.userName + '님의 지역명소');

const router = useRouter();

const goInfo = async () => {
  await info(10);
  isSearch.value = true;
  isResult.value = false;
  isAround.value = false;
  isResultDetail.value = false;
  router.push({ name: 'placeInfo', query: { type: 'placeInfo', idx: 10 } });
};
</script>

<template>
  <div class="content">
    <Header />
    <section class="PageTitle">
      <div class="modalDiv">
        <Meteo />
        <Weather />
      </div>
      <div class="titleBox">
        <h1 class="ImgTitle">화악산 별빛공원</h1>
        <button class="titleInfo" @click="goInfo">상세정보</button>
      </div>
    </section>
    <section class="list container-fluid">
      <MainList :title="sub1" :data="bestList" />
    </section>
    <section class="list container-fluid">
      <h5 v-if="placeList.length == 0">
        접속하신 {{ myAddress.substring(0, 2) }} 지역의 명소가 존재하지
        않습니다.
      </h5>
      <MainList v-else :title="sub2" :data="placeList" />
    </section>
  </div>
</template>

<style scoped>
@media (max-width: 790px) {
  .content {
    background-size: 300% !important;
  }
  .PageTitle {
    height: 250px !important;
  }
  .titleBox {
    top: 120px !important;
    left: 0px !important;
  }
}
.content {
  min-height: 100vh;
  background-image: url('@/assets/img/homeImg.png');
  background-repeat: no-repeat;
  background-size: 100%;
  background-position: top;
  overflow: auto;
}
.PageTitle {
  height: 350px;
}
.titleBox {
  width: 300px;
  text-align: center;
  position: relative;
  top: 230px;
  left: 60px;
}
.ImgTitle {
  color: white;
  font-weight: bold;
}
.titleInfo {
  width: 170px;
  height: 50px;
  border-radius: 10px;
  background-color: #5a5e5f;
  border: none;
  color: white;
  margin: 10px;
}
section {
  width: 100%;
  margin-bottom: 50px;
}
.modalDiv {
  position: absolute;
  width: 500px;
  right: 0;
  height: 50px;
  display: flex;
  justify-content: space-around;
}
h5 {
  font-weight: bold;
  color: white;
}
</style>
