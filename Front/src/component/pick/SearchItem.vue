<script setup>
import { useRoute, useRouter } from 'vue-router';

import { storeToRefs } from 'pinia';
import { useMapStore } from '@/stores/mapStore';

const mapStore = useMapStore();
const { place, mapInfo, isSearch, isAround, isResult, isResultDetail } =
  storeToRefs(mapStore);
const { info } = mapStore;

defineProps({
  data: Object,
});

const route = useRoute();
const router = useRouter();
const movePage = async (val, lat, lon) => {
  console.log(val + ' 이 넘어갑니다. search Item');
  await info(val);
  isSearch.value = false;
  isResult.value = false;
  isAround.value = false;
  isResultDetail.value = true;
  mapInfo.value.latitude = lat;
  mapInfo.value.longitude = lon;
  router.push({ name: 'placeInfo', query: { type: 'placeInfo', idx: val } });
};
</script>

<template>
  <div class="Item" @click="movePage(data.idx, data.lati, data.longj)">
    <div class="Img" :style="{ backgroundImage: `url(${data.img})` }">
      <div class="title">{{ data.title }}</div>
    </div>
  </div>
</template>

<style scoped>
.Item {
  margin-top: 20px;
}
.Img {
  position: relative;
  height: 300px;
  margin-bottom: 20px;
  cursor: pointer;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
}
.Img:hover .title {
  opacity: 1; /* 마우스를 올렸을 때 보이도록 변경 */
}
.title {
  height: 50px;
  width: 100%;
  position: absolute;
  bottom: 0;
  background-color: #d30000;
  text-align: center;
  color: white;
  font-size: 20px;
  font-weight: bold;
  line-height: 50px;
  transition: opacity 0.3s ease; /* 투명도 변경 시 애니메이션 효과 추가 */
  opacity: 0; /* 초기에는 숨김 */
}
</style>
