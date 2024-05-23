<script setup>
import AroundItem from '@/component/map/AroundItem.vue';
import { useRoute, useRouter, onBeforeRouteLeave } from 'vue-router';
import { onMounted, ref, watch } from 'vue';
import { storeToRefs } from 'pinia';
import { useMapStore } from '@/stores/mapStore';

const mapStore = useMapStore();
const { aroundList, isAround } = storeToRefs(mapStore);
const { travel, camp } = mapStore;
const route = useRoute();
const router = useRouter();
const contentId = ref(route.query.contentId);

onBeforeRouteLeave((to, from) => {
  isAround.value = false;
});

onMounted(async () => {
  if (contentId.value === '40') {
    await camp(idx);
  } else {
    await travel(idx, contentId.value);
  }
});

watch(
  () => contentId.value,
  (newValue, oldValue) => {
    contentId.value = newValue;
    if (newValue == '40') {
      camp(idx);
    } else {
      travel(idx, newValue);
    }
  }
);

const type = route.query.type;
const idx = route.query.idx;
const activeMenu = ref(contentId); // 메뉴 클릭시 효과 변수

function setActiveMenu(menu, id) {
  // 메뉴함수
  router.push({ name: menu, query: { type: type, idx, contentId: id } });
  activeMenu.value = id;
}
</script>

<template>
  <section class="mapContent">
    <ul class="aroundMenu">
      <li
        :class="{ active12: activeMenu === '12' }"
        @click="setActiveMenu('tour', '12')"
      >
        관광
      </li>
      <li
        :class="{ active14: activeMenu === '14' }"
        @click="setActiveMenu('culture', '14')"
      >
        문화
      </li>
      <li
        :class="{ active15: activeMenu === '15' }"
        @click="setActiveMenu('festival', '15')"
      >
        축제
      </li>
      <li
        :class="{ active25: activeMenu === '25' }"
        @click="setActiveMenu('travel', '25')"
      >
        여행
      </li>
      <li
        :class="{ active28: activeMenu === '28' }"
        @click="setActiveMenu('Leisure', '28')"
      >
        레포츠
      </li>
      <li
        :class="{ active32: activeMenu === '32' }"
        @click="setActiveMenu('sleep', '32')"
      >
        숙박
      </li>
      <li
        :class="{ active38: activeMenu === '38' }"
        @click="setActiveMenu('shop', '38')"
      >
        쇼핑
      </li>
      <li
        :class="{ active39: activeMenu === '39' }"
        @click="setActiveMenu('food', '39')"
      >
        음식
      </li>
      <li
        :class="{ active40: activeMenu === '40' }"
        @click="setActiveMenu('camp', '40')"
      >
        캠핑
      </li>
    </ul>
    <div class="aroundDiv container-fluid">
      <div class="wrap row">
        <AroundItem
          class="col-6 col-sm-2"
          v-for="around in aroundList"
          :key="around.idx"
          :data="around"
        />
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
  height: 500px;
  margin: 20px auto 0;
}
/* 아래는 주변메뉴 */
.aroundMenu {
  width: 95%;
  margin: 0 auto;
  list-style: none;
  color: white;
  font-size: 20px;
  font-weight: bold;
  display: flex;
}
.aroundMenu > li {
  margin-right: 10px;
  cursor: pointer;
  color: #757575;
}
.active12 {
  color: white !important;
  border-bottom: 4px solid #f3512c;
}
.active14 {
  color: white !important;
  border-bottom: 4px solid #7eb347;
}
.active15 {
  color: white !important;
  border-bottom: 4px solid #0888d0;
}
.active25 {
  color: white !important;
  border-bottom: 4px solid #2a3a87;
}
.active28 {
  color: white !important;
  border-bottom: 4px solid #fd8b69;
}
.active32 {
  color: white !important;
  border-bottom: 4px solid #ff7800;
}
.active38 {
  color: white !important;
  border-bottom: 4px solid #9b2cf9;
}
.active39 {
  color: white !important;
  border-bottom: 4px solid #ffce49;
}
.active40 {
  color: white !important;
  border-bottom: 4px solid #690b0b;
}

/* 주변 정보 */
.aroundDiv {
  height: 470px;
  overflow: auto;
}
.aroundDiv::-webkit-scrollbar {
  display: none;
}
</style>
