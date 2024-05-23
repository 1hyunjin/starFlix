<script setup>
import Btn from '@/component/common/Btn.vue';
import { useRoute, useRouter } from 'vue-router';
import { onMounted, ref } from 'vue';

import { storeToRefs } from 'pinia';
import { useReviewStore } from '@/stores/review';
import { useUserStore } from '@/stores/user';
import { useMapStore } from '@/stores/mapStore';
const reviewStore = useReviewStore();
const mapStore = useMapStore();
const userStore = useUserStore();
const { review } = storeToRefs(reviewStore);
const { place } = storeToRefs(mapStore);
const { userInfo } = storeToRefs(userStore);
const { delReview } = reviewStore;

const route = useRoute();
const router = useRouter();

const idx = route.query.idx;
const reviewInfo = ref({});
onMounted(() => {
  reviewInfo.value = review.value;
});

const movePage = (val, rno) => {
  if (val === 'modify') {
    router.push({
      name: 'ModifyReview',
      query: { type: 'placeReview', idx, rno: rno },
    });
  } else {
    router.push({ name: 'placeInfo', query: { type: 'placeInfo', idx } });
  }
};
const deleteReview = async (rno) => {
  const flag = confirm('정말로 삭제하시겠습니까?');
  if (flag) {
    await delReview(rno);
    router.push({ name: 'mapInfo', query: { type: 'placeInfo', idx } });
  }
};
</script>

<template>
  <section class="mapContent">
    <h1 class="title">{{ place.placeInfo.title }}</h1>
    <h5 class="text">{{ place.placeInfo.addr }}</h5>
    <p class="text">작성시간 : {{ review.registerTime }}</p>
    <div class="textBox">
      <input
        class="rTitle"
        type="text"
        placeholder="제목을 입력하세요"
        v-model="reviewInfo.title"
      />
      <div class="rContent" v-html="reviewInfo.content"></div>
    </div>
    <div class="btnArea">
      <Btn
        v-if="userInfo.userId === reviewInfo.writer"
        :sty="'redBtn'"
        :text="'수정'"
        @click="movePage('modify', review.rno)"
      />
      <Btn
        v-if="userInfo.userId === reviewInfo.writer"
        :sty="'redBtn'"
        :text="'삭제'"
        @click="deleteReview(review.rno)"
      />
      <Btn :sty="'blackBtn'" :text="'이전'" @click="movePage()" />
    </div>
  </section>
</template>

<style scoped>
@media (max-width: 1000px) {
  .textBox {
    width: 100% !important;
    height: fit-content;
  }
  .rTitle {
    width: 100% !important;
  }
  .rContent {
    width: 100% !important;
  }
}
.mapContent {
  width: 90%;
  height: 100%;
  margin: 20px auto 30px;
}

/* 아래는 리뷰작성 */
.title,
.text {
  color: white;
}
.title {
  font-weight: bold;
}
.textBox {
  text-align: center;
}
.rTitle {
  width: 80%;
  min-width: 220px;
  height: 45px;
  background-color: #333333;
  border-radius: 10px;
  border: none;
  padding-left: 10px;
  padding-right: 10px;
  margin-bottom: 20px;
  margin: 0 auto 20px;
  color: white;
}
.rContent {
  width: 80%;
  min-width: 200px;
  height: 520px;
  background-color: #333333;
  border-radius: 10px;
  border: none;
  padding: 10px;
  margin: 0 auto 20px;
  color: white;
  text-align: start;
  overflow: auto;
}
.testDiv {
  width: 100%;
  height: 1000px;
  background-color: aquamarine;
}
</style>
