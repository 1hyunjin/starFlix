<script setup>
import Btn from '../common/Btn.vue';
import { onMounted } from 'vue';
import { useUserStore } from '@/stores/user';
import { useReviewStore } from '@/stores/review';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import { useMapStore } from '@/stores/mapStore';
const userStore = useUserStore();
const reivewStore = useReviewStore();
const mapStore = useMapStore();
const { userInfo } = storeToRefs(userStore);
const { reviewList } = storeToRefs(reivewStore);
const { userReview } = reivewStore;
const { info } = mapStore;
const { place, isSearch, isAround, isResult, isResultDetail } =
  storeToRefs(mapStore);

const router = useRouter();
onMounted(async () => {
  await userReview(userInfo.value.userId);
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
  <table class="boardTitle">
    <tr>
      <th class="t1">명소번호</th>
      <th class="t2">제목</th>
      <th class="t3">작성일시</th>
    </tr>
  </table>
  <div class="hr"></div>
  <div class="noList" v-if="reviewList.length == 0">작성된 글이 없습니다.</div>
  <div class="InfoDiv" v-else>
    <table class="board">
      <tbody>
        <tr class="reviewTr" v-for="r in reviewList" @click="movePage(r.idx)">
          <td class="t1">{{ r.idx }}</td>
          <td class="t2">{{ r.title }}</td>
          <td class="t3">{{ r.registerTime }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
div {
  width: 95%;
  color: white;
  font-size: 20px;
  border-bottom: 1px solid white;
  margin: 5px auto;
}
.text {
  color: white;
}
.t1 {
  text-align: center;
}
.t1,
.t4,
.t5 {
  width: 100px;
}
.t3 {
  width: 150px;
}
.t2 {
  width: 400px;
  text-align: left;
  padding-left: 20px;
}
.InfoDiv {
  height: 410px;
  overflow: auto;
}
.InfoDiv::-webkit-scrollbar {
  display: none;
}
.board {
  width: 85%;
}
.reviewTr {
  cursor: pointer;
}
</style>
