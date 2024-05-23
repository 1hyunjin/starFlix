<script setup>
import Btn from '@/component/common/Btn.vue';
import ListItem from '@/component/board/ListItem.vue';
import { useRoute, useRouter } from 'vue-router';
import { onMounted, ref } from 'vue';

import { storeToRefs } from 'pinia';
import { useBoardStore } from '@/stores/boardStore';

const router = useRouter();
const route = useRoute();

const pg = route.query.pg;

const boardStore = useBoardStore();
const { boardList } = storeToRefs(boardStore);
const { blist } = boardStore;

onMounted(async () => {
  console.log('나왔다');
  await blist('', pg);
});

const movePage = (val) => {
  router.push({ name: val, query: { pg } });
};

// 페이지네이션
function range(start, end) {
  const list = [];
  for (let i = start; i <= end; i++) list.push(i);
  return list;
  //   return Array(end - start + 1)
  //     .fill()
  //     .map((val, i) => start + i);
}

const listPre = async (val) => {
  await blist('', val - 1);
};
const listNext = async (val) => {
  await blist('', val + 1);
};
const listPage = async (idx) => {
  await blist('', idx);
};
</script>

<template>
  <section class="tableDiv container-fluid">
    <table class="boardTitle">
      <tr>
        <th class="t1">글번호</th>
        <th class="t2">제목</th>
        <th class="t3">작성자</th>
        <th class="t4">조회수</th>
        <th class="t5">좋아요</th>
      </tr>
    </table>
    <div class="hr"></div>
    <div class="noList" v-if="boardList.length == 0">작성된 글이 없습니다.</div>
    <table v-else class="board">
      <tr>
        <th class="t1"></th>
        <th class="t2"></th>
        <th class="t3"></th>
        <th class="t4"></th>
        <th class="t5"></th>
      </tr>
      <ListItem :test="boardList.boardList" />
    </table>
    <div class="pgDiv">
      <template v-if="boardList.startPage > 1">
        <button class="pgBtn2" @click="listPre(boardList.startPage)">
          이전
        </button> </template
      >=1
      <template v-for="p in range(boardList.startPage, boardList.endPage)">
        <button class="pgBtn" @click="listPage(p)">{{ p }}</button>
      </template>
      <template v-if="boardList.endPage < boardList.totalPage">
        <button class="pgBtn2" @click="listNext(boardList.endPage)">
          다음
        </button>
      </template>
    </div>
    <div class="btnArea">
      <Btn :text="'글쓰기'" :sty="'redBtn'" @click="movePage('write')" />
    </div>
  </section>
</template>

<style>
@media (max-width: 790px) {
  table {
    font-size: 10px !important;
  }
  .tableDiv {
    background-color: #000;
  }
}
.boardTitle {
  color: white;
  margin: 0 auto;
  width: 80%;
  text-align: center;
}
.hr {
  width: 90%;
  margin: 10px auto 0px;
  border-bottom: 5px double #fff;
}

.board {
  color: white;
  margin: 0 auto;
  width: 80%;
  text-align: center;
}

.noList {
  height: 400px;
  color: #fff;
  font-size: 20px;
  text-align: center;
  padding-top: 50px;
}

.board > tbody > tr {
  border-bottom: 1px solid #fff;
  height: 40px;
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
.btnArea {
  width: 80%;
  margin: 20px auto 50px;
  display: flex;
  justify-content: end;
}
.pgDiv {
  width: 100%;
  margin: 10px 0 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.pgBtn {
  width: 35px;
  height: 35px;
  font-size: 12px;
  color: white;
  background-color: #d30000;
  border-radius: 10px;
}
.pgBtn2 {
  width: 35px;
  height: 35px;
  font-size: 12px;
  color: white;
  background-color: #000;
  border-radius: 10px;
  border: 1px solid white;
}
</style>
