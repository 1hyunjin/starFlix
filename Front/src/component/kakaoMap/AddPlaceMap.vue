<script setup>
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';
const coordinate = {
  lat: 33.450701,
  lng: 126.570667,
};
</script>

<template>
  <!-- 검색결과 상세보기 -->
  <!-- <div style="color: white">erer</div>
  <div style="color: white">{{ idx }}</div> -->
  <KakaoMap
    :lat="coordinate.lat"
    :lng="coordinate.lng"
    :draggable="true"
    :level="5"
    :width="100 + '%'"
  >
    <KakaoMapMarker
      :lat="coordinate.lat"
      :lng="coordinate.lng"
    ></KakaoMapMarker>
  </KakaoMap>
</template>

<style scoped></style>
