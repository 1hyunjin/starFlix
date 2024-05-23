<script setup>
import Btn from '../common/Btn.vue';
import { useRoute, useRouter, onBeforeRouteLeave } from 'vue-router';
import { onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/user';
import { useMapStore } from '@/stores/mapStore';
import { useReviewStore } from '@/stores/review';
import { useEtcStore } from '@/stores/etcStore';
import { storeToRefs } from 'pinia';
const userStore = useUserStore();
const reviewStore = useReviewStore();
const mapStore = useMapStore();
const etcStore = useEtcStore();
const { place } = storeToRefs(mapStore);
const { userInfo } = storeToRefs(userStore);
const { reviewList } = storeToRefs(reviewStore);
const { gptReview } = storeToRefs(etcStore);
const { gpt } = etcStore;
const { placeReview, read } = reviewStore;
const route = useRoute();
const router = useRouter();

const idx = route.query.idx;
onMounted(async () => {
  await placeReview(place.value.placeInfo.idx);
});

onBeforeRouteLeave(() => {
  gptReview.value = null;
});

const movePage = async (val) => {
  await read(val);
  router.push({
    name: 'readReview',
    query: { type: 'placeReview', idx, id: val },
  });
};

const type = route.params.type;
const activeMenu = ref(type); // 메뉴 클릭시 효과 변수

const gptFlag = ref(false);
const gptOn = async () => {
  await gpt(place.value.placeInfo.idx);
  alert('분석이 완료되었습니다.');
};
</script>

<template>
  <section class="mapContent">
    <div class="textArea">
      <table class="titleTable">
        <tr>
          <th class="c1">제목</th>
          <th class="c2">작성자</th>
          <th class="c3">날짜</th>
        </tr>
      </table>
    </div>
    <div class="aroundDiv">
      <table class="titleTable">
        <tr class="contentTable" v-for="r in reviewList">
          <td class="c1 clickTitle" @click="movePage(r.rno)">{{ r.title }}</td>
          <td class="c2" style="text-align: center">{{ r.writer }}</td>
          <td class="c3 dateText" style="text-align: center">
            {{ r.registerTime }}
          </td>
        </tr>
      </table>
    </div>
    <div class="gptBox">
      <div class="text" v-if="gptReview != null">
        <p><span>평점 : </span>{{ gptReview.rate }}</p>
        <p><span>요약 : </span>{{ gptReview.summary }}</p>
        <p><span>Good : </span>{{ gptReview.pros }}</p>
        <p><span>BAD : </span>{{ gptReview.cons }}</p>
      </div>
      <Btn
        class="gptBtn"
        :sty="'redBtn'"
        :text="'GPT 리뷰분석'"
        @click="gptOn"
      />
    </div>
  </section>
</template>

<style scoped>
@media (max-width: 790px) {
  .titleTable {
    font-size: 20px !important;
  }
  .contentTable {
    font-size: 15px !important;
  }
}

.mapContent {
  width: 90%;
  height: 100%;
  margin: 20px auto 0;
}
/* 아래는 홈 */

/* 리뮤목록 */
.aroundDiv {
  height: 470px;
  overflow: auto;
}
.aroundDiv::-webkit-scrollbar {
  display: none;
}

.titleTable {
  color: white;
  font-size: 30px;
  margin: 0 auto;
}

th {
  text-align: center;
}

.c1 {
  width: 400px;
}
.c2 {
  width: 150px;
}
.c3 {
  width: 200px;
}
.dateText {
  font-size: 14px;
}

.clickTitle {
  cursor: pointer;
}

.contentTable {
  height: 50px;
  font-size: 20px;
  border-bottom: 1px solid white;
}

.text {
  color: white;
}
.gptBox {
  width: 90%;
  margin: 20px auto;
}
.gptBtn {
  margin-top: 20px;
}
</style>
