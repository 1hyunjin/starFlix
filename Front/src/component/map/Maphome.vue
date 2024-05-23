<script setup>
import SearchItem from '../pick/SearchItem.vue';
import { useRoute, useRouter } from 'vue-router';
import { onMounted, ref } from 'vue';

import { storeToRefs } from 'pinia';
import { useMapStore } from '@/stores/mapStore';
const mapStore = useMapStore();
const {
  searchList,
  mapInfo,
  myLocation,
  isSearch,
  isAround,
  isResult,
  isResultDetail,
} = storeToRefs(mapStore);

const route = useRoute();
const router = useRouter();

const type = route.params.type;
const activeMenu = ref(type); // 메뉴 클릭시 효과 변수

onMounted(() => {
  isResultDetail.value = false;
});
</script>

<template>
  <section class="mapContent">
    <div class="textArea">
      <h2 class="title">별자리를 확인하고 공유해보세요.</h2>
      <p class="text">명소 주변정보와 리뷰도 한눈에 확인할 수 있습니다.</p>
    </div>
    <!-- <h2 class="title">원하는 정보를 검색해보세요!</h2> -->
    <div class="aroundDiv container-fluid">
      <h2 class="title">Search Result</h2>
      <div class="wrap row">
        <div v-if="searchList == null" class="text">
          지도 상단에 위치한 검색창으로 명소를 찾아보세요!
        </div>
        <template v-else>
          <div v-if="searchList.length == 0" class="text">
            검색 결과가 없습니다.
          </div>
          <template v-else>
            <SearchItem
              v-for="s in searchList"
              class="col-12 col-sm-6 col-md-4"
              :data="s"
            />
          </template>
        </template>
      </div>
    </div>
  </section>
</template>

<style scoped>
@media (max-width: 580px) {
  .aroundMenu {
    font-size: 10px !important;
  }
}

.mapContent {
  width: 90%;
  height: 100%;
  margin: 20px auto 0;
  padding: 20px;
  border-radius: 15px;
  background-color: rgba(0, 0, 0, 0.4);
}
/* 아래는 홈 */

/* 주변 정보 */
.aroundDiv {
  height: 470px;
  overflow: auto;
  border: 1px solid white;
  border-radius: 5px;
}
.aroundDiv::-webkit-scrollbar {
  display: none;
}
.title,
.text {
  color: white;
}
.title {
  font-weight: bold;
}
</style>
