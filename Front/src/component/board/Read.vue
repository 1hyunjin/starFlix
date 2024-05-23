<script setup>
import Btn from '@/component/common/Btn.vue';
import commentList from '../comment/commentList.vue';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/user';
import { useBoardStore } from '@/stores/boardStore';

const boardStore = useBoardStore();
const memberStore = useUserStore();
const { board, likeFlag, unlikeFlag } = storeToRefs(boardStore);
const { userInfo } = memberStore;
const { read, delBoard, checkLike, like, unlike } = boardStore;

const route = useRoute();
const router = useRouter();

const likeText = ref('좋아요');

const pg = route.query.pg;
const bno = route.query.bno; // 내가 읽은 글의 bno
const boardInfo = ref({});
onMounted(async () => {
  console.log('받을 글 ' + bno);
  await read(bno);
  boardInfo.value = board.value;
  await checkLike(bno, userInfo.userId);
  if (likeFlag.value) {
    likeText.value = '해제';
  }
});
const movePage = (val) => {
  if (val === 'modify') {
    router.push({ name: val, query: { pg, bno: bno } });
  } else {
    router.push({ name: val, query: { pg } });
  }
};
const deleteBoard = () => {
  const flag = confirm('정말로 삭제하시겠습니까?');
  if (flag) {
    delBoard(bno, pg);
  }
};

const btnAction = () => {
  console.log('동작');
  if (likeFlag.value) {
    unlike(bno, userInfo.userId);
    likeFlag.value = !likeFlag.value;
    unlikeFlag.value = !unlikeFlag.value;
    likeText.value = '좋아요';
  } else {
    like(bno, userInfo.userId);
    likeFlag.value = !likeFlag.value;
    unlikeFlag.value = !unlikeFlag.value;
    likeText.value = '해제';
  }
};
onMounted(() => {
  const images = document.querySelectorAll('.bContent p img');
  images.forEach((img) => {
    img.style.maxWidth = '200px';
    img.style.height = 'auto';
  });
});
</script>

<template>
  <section class="container-fluid">
    <div class="boardArea">
      <div>
        <input
          type="text"
          class="bTitle"
          placeholder="제목을 입력하세요"
          v-model="boardInfo.title"
          readonly
        />
        <input
          type="text"
          class="bWriter"
          placeholder="작성자"
          readonly
          v-model="boardInfo.writer"
        />
      </div>
      <div
        class="bContent"
        placeholder="내용을 입력하세요"
        v-html="boardInfo.content"
      ></div>
    </div>
    <div class="btnArea">
      <Btn
        :sty="{ redBtn: unlikeFlag, blackBtn: likeFlag }"
        :text="likeText"
        @click="btnAction"
      />
      <Btn
        :sty="'redBtn'"
        :text="'수정'"
        v-if="userInfo.userId === boardInfo.writer"
        @click="movePage('modify')"
      />
      <Btn
        :sty="'blackBtn'"
        :text="'삭제'"
        v-if="userInfo.userId === boardInfo.writer"
        @click="deleteBoard"
      />
      <Btn :sty="'blackBtn'" :text="'이전'" @click="movePage('list')" />
    </div>
  </section>
  <commentList />
</template>

<style scoped>
@media (max-width: 820px) {
  .bContent {
    width: 100% !important;
  }
}
@media (max-width: 790px) {
  .btnArea {
    flex-direction: column;
  }
}

.content {
  min-height: 100vh;
  background-image: url('@/assets/img/boardImg.png');
  background-repeat: no-repeat;
  background-size: 100%;
  background-position: top;
  overflow: auto;
}
.PageTitle {
  height: 350px;
}
.titleBox {
  width: 90%;
  padding-left: 20px;
  position: relative;
  display: flex;
  justify-content: space-between;
  top: 230px;
  left: 60px;
}
.title {
  color: white;
  font-weight: bold;
}
.text {
  color: white;
}
section {
  width: 100%;
  margin-bottom: 20px;
}

/*  */

.boardArea {
  width: 100%;
  text-align: center;
  margin: 0 auto 20px;
}
.boardArea + div {
  margin: 0 auto;
  width: 100%;
}

.bTitle {
  width: 65%;
  min-width: 220px;
  height: 45px;
  background-color: #333333;
  border-radius: 10px;
  border: none;
  padding-left: 10px;
  padding-right: 10px;
  margin-bottom: 20px;
  color: white;
}
.bWriter {
  width: 13%;
  min-width: 80px;
  height: 45px;
  margin-left: 2%;
  text-align: center;
  border-radius: 10px;
  border: none;
  padding-left: 10px;
  padding-right: 10px;
}
.bContent {
  width: 80%;
  min-width: 300px;
  min-height: 313px;
  height: 100%;
  background-color: #333333;
  border-radius: 10px;
  border: none;
  padding: 10px;
  color: white;
  margin: 0 auto;
  overflow: auto;
}
/* 토스트 UI 에디터에서 생성되는 이미지의 스타일을 수정 */
.tui-editor-contents img {
  max-width: 100%; /* 이미지의 최대 너비를 부모 요소의 너비에 맞게 설정 */
  height: auto; /* 비율 유지를 위해 height 속성을 auto로 설정 */
}
.btnArea {
  width: 80% !important;
  margin: 0 auto 50px;
  display: flex;
  justify-content: end;
}
</style>
