<script setup>
import Btn from '@/component/common/Btn.vue';
import { useRoute, useRouter } from 'vue-router';
import { onMounted, ref } from 'vue';

import { storeToRefs } from 'pinia';
import { useMapStore } from '@/stores/mapStore';
import { usePickStore } from '@/stores/pickStore';
import { useUserStore } from '@/stores/user';
const mapStore = useMapStore();
const pickStore = usePickStore();
const userStore = useUserStore();
const { place, isSearch, isAround, isResult, isResultDetail } =
  storeToRefs(mapStore);
const { userInfo } = storeToRefs(userStore);
const { info } = mapStore;
const { addPick, delPick } = pickStore;

const route = useRoute();
const router = useRouter();

const pickCheck = ref(false);

const idx = route.query.idx;
onMounted(async () => {
  await info(idx);
  // isSearch.value = true;
  // isResult.value = false;
  // isAround.value = false;
  if (place.value.isJjim == 1) {
    pickCheck.value = true;
  }
  console.log(place.value.isJjim + ' ' + pickCheck.value);
});

const placePick = async (val) => {
  if (pickCheck.value) {
    await delPick(val, userInfo.value.userId);
    alert('찜 목록에서 삭제되었습니다.');
    pickCheck.value = !pickCheck.value;
  } else {
    await addPick(val, userInfo.value.userId);
    alert('찜 목록에 추가되었습니다.');
    pickCheck.value = !pickCheck.value;
  }
};

const movePage = (val) => {
  router.push({ name: val, query: { type: 'placeReview', idx } });
};
</script>

<template>
  <section class="mapContent">
    <div
      class="img"
      :style="{ backgroundImage: `url(${place.placeInfo.img})` }"
    ></div>
    <div class="placeBox">
      <div class="textArea">
        <h2 class="title">{{ place.placeInfo.title }}</h2>
        <p class="text">{{ place.placeInfo.addr }}</p>
        <p class="text" v-if="place.placeInfo.content !== '설명'">
          {{ place.placeInfo.content }}
        </p>
      </div>
      <div class="btnArea">
        <Btn
          :sty="{ redBtn: !pickCheck, blackBtn: pickCheck }"
          :text="'♥'"
          @click="placePick(place.placeInfo.idx)"
        />
        <Btn :sty="'redBtn'" :text="'리뷰작성'" @click="movePage('Review')" />
      </div>
    </div>
  </section>
</template>

<style scoped>
@media (max-width: 790px) {
  .btnArea {
    margin: 10px;
    width: 40% !important;
    height: fit-content;
  }
}
@media (max-width: 480px) {
  .placeBox {
    flex-direction: column;
  }
  .btnArea {
    margin: 10px;
    width: 80% !important;
    height: fit-content;
  }
}
.mapContent {
  width: 90%;
  height: 500px;
  margin: 20px auto 0;
}
/* 아래는 명소정보 */
.img {
  width: 80%;
  height: 300px;
  margin: 10px auto;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
}
.placeBox {
  width: 90%;
  display: flex;
  justify-content: space-between;
  margin: 0 auto;
}
.title,
.text {
  color: white;
}
.title {
  font-weight: bold;
}
.btnArea {
  width: fit-content;
  height: fit-content;
  margin: 0;
}
</style>
