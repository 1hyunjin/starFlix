<script setup>
import { onMounted, ref } from 'vue';
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';
import { isKakaoMapApiLoaded } from 'vue3-kakao-maps/@utils';
import { storeToRefs } from 'pinia';
import { useMapStore } from '@/stores/mapStore';
import { useRoute } from 'vue-router';
import '../../assets/img/marker/12.png';
const route = useRoute();
const idx = route.query.idx;

function getImageUrl(name) {
  return new URL(`../../assets/img/marker/${name}.png`, import.meta.url).href;
}
const mapStore = useMapStore();
const {
  myLocation,
  place,
  placeList,
  bestList,
  searchList,
  aroundList,
  mapInfo,
  isSearch,
  isResult,
  isAround,
  isResultDetail,
} = storeToRefs(mapStore);
const coordinate = {
  lat: myLocation.value.latitude,
  lng: myLocation.value.longitude,
};

// 관광지 마커 클릭시 이벤트
/////////////////////////////////
//마커 클릭 시 인포윈도우의 visible 값을 반전시킵니다
const onClickMapMarker = (markerItem) => {
  if (
    markerItem.infoWindow?.visible !== null &&
    markerItem.infoWindow?.visible !== undefined
  ) {
    markerItem.infoWindow.visible = !markerItem.infoWindow.visible;
  } else {
    markerItem.value.infoWindow.visible = true;
  }
};
// 여기까지
</script>

<template>
  <!-- 찜 및 메인화면의 정보 결과 상세보기 -->
  <!-- <div style="color: white">erer</div>
  <div style="color: white">{{ idx }}</div> -->
  <KakaoMap
    v-if="isSearch"
    :lat="place.placeInfo.lati"
    :lng="place.placeInfo.longj"
    :draggable="true"
    :level="10"
    :width="100 + '%'"
  >
    <KakaoMapMarker
      :lat="place.placeInfo.lati"
      :lng="place.placeInfo.longj"
    ></KakaoMapMarker>
    <template v-if="isAround">
      <KakaoMapMarker
        v-for="a in aroundList"
        :lat="a.lati"
        :lng="a.longj"
        :image="{
          imageSrc: getImageUrl(a.type),
          imageWidth: 32,
          imageHeight: 32,
          imageOption: {},
        }"
        :infoWindow="a.infoWindow"
        :clickable="true"
        @onClickKakaoMapMarker="onClickMapMarker(a)"
      ></KakaoMapMarker>
    </template>
  </KakaoMap>
  <!-- 검색결과 리스트 -->
  <KakaoMap
    v-if="isResult"
    :lat="mapInfo.latitude"
    :lng="mapInfo.longitude"
    :draggable="true"
    :level="5"
    :width="100 + '%'"
  >
    <KakaoMapMarker
      v-for="s in searchList"
      :lat="s.lati"
      :lng="s.longj"
    ></KakaoMapMarker>
    <template v-if="isAround">
      <KakaoMapMarker
        v-for="a in aroundList"
        :lat="a.lati"
        :lng="a.longj"
        :image="{
          imageSrc: getImageUrl(a.type),
          imageWidth: 32,
          imageHeight: 32,
          imageOption: {},
        }"
        :infoWindow="a.infoWindow"
        :clickable="true"
        @onClickKakaoMapMarker="onClickMapMarker(a)"
      ></KakaoMapMarker>
    </template>
  </KakaoMap>
  <!-- 지도 검색 후 결과 -->
  <KakaoMap
    v-if="isResultDetail"
    :lat="mapInfo.latitude"
    :lng="mapInfo.longitude"
    :draggable="true"
    :level="5"
    :width="100 + '%'"
  >
    <KakaoMapMarker
      :lat="mapInfo.latitude"
      :lng="mapInfo.longitude"
    ></KakaoMapMarker>
    <template v-if="isAround">
      <KakaoMapMarker
        v-for="a in aroundList"
        :lat="a.lati"
        :lng="a.longj"
        :image="{
          imageSrc: getImageUrl(a.type),
          imageWidth: 32,
          imageHeight: 32,
          imageOption: {},
        }"
        :infoWindow="a.infoWindow"
        :clickable="true"
        @onClickKakaoMapMarker="onClickMapMarker(a)"
      ></KakaoMapMarker>
    </template>
  </KakaoMap>
</template>

<style scoped></style>
