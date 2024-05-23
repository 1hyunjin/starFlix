<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { storeToRefs } from 'pinia';
import { useMapStore } from '@/stores/mapStore';
const mapStore = useMapStore();
const { place, isSearch, isAround, isResult, isResultDetail } =
  storeToRefs(mapStore);
const { info } = mapStore;

const router = useRouter();
defineProps({
  params: Object,
});

const movePage = async (idx) => {
  console.log('페이지 이동 중');
  // 상세정보 넘어가기
  await info(idx);
  isSearch.value = true;
  isResult.value = false;
  isAround.value = false;
  isResultDetail.value = false;
  router.push({ name: 'placeInfo', query: { type: 'placeInfo', idx: idx } });
};
</script>

<template>
  <span v-for="p in params" :key="p.idx">
    <div
      class="Img"
      :style="{ backgroundImage: `url(${p.img})` }"
      @click="movePage(p.idx)"
    >
      <div class="title">{{ p.title }}</div>
    </div>
  </span>
</template>

<style scoped>
.Img {
  width: 250px;
  height: 200px;
  margin-right: 20px;
  position: relative;
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
