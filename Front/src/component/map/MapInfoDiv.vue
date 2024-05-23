<script setup>
import { useRoute, useRouter } from 'vue-router';
import { RouterView } from 'vue-router';
import { ref, watch } from 'vue';

const route = useRoute();
const router = useRouter();

const type = route.query.type;
const idx = route.query.idx;
const activeMenu = ref(type); // 메뉴 클릭시 효과 변수

console.log(type);

function setActiveMenu(menu) {
  // 메뉴함수
  if (menu === 'placeAround') {
    router.push({ name: menu, query: { type: menu, idx, contentId: '12' } });
    activeMenu.value = menu;
  } else {
    router.push({ name: menu, query: { type: menu, idx } });
    activeMenu.value = menu;
  }
}

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
  <div class="menuBox">
    <ul class="myMenu">
      <li
        :class="{ active: activeMenu === 'placeInfo' }"
        @click="setActiveMenu('placeInfo')"
      >
        명소 정보
      </li>
      <li
        :class="{ active: activeMenu === 'placeAround' }"
        @click="setActiveMenu('placeAround')"
      >
        주변 정보
      </li>
      <li
        :class="{ active: activeMenu === 'placeReview' }"
        @click="setActiveMenu('placeReview')"
      >
        명소 리뷰
      </li>
    </ul>
    <RouterView />
  </div>
</template>

<style scoped>
@media (max-width: 1147px) {
  .myMenu {
    font-size: 20px !important;
  }
}
@media (max-width: 480px) {
  .myMenu {
    font-size: 16px !important;
  }
}
.menuBox {
  width: 100%;
  height: 100%;
  margin-top: 30px;
  border-radius: 15px;
  background-color: rgba(0, 0, 0, 0.4);
}

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
  border-bottom: 4px solid #d30000;
}
</style>
