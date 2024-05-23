<script setup>
import { onMounted, ref } from 'vue';

import { useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/user';
import { useCommentStore } from '@/stores/commentStore';

const memberStore = useUserStore();
const commentStore = useCommentStore();

const { commentList, reple } = storeToRefs(commentStore);
const { list, update, write, del } = commentStore;
const { userInfo } = memberStore;

const route = useRoute();
const bno = route.query.bno;
const comment = ref('');
const addComment = ref({
  bno: bno,
  writer: userInfo.userId,
  content: '',
});

const openIndexes = ref([]);

onMounted(async () => {
  await list(bno);
});

const addc = () => {
  write(addComment.value);
  addComment.value.content = '';
};

//여기까지 수정함

const modifyToggle = (index) => {
  if (openIndexes.value.includes(index)) {
    openIndexes.value = openIndexes.value.filter((i) => i !== index);
  } else {
    openIndexes.value.push(index);
  }
};

const Cmodify = (cno, index) => {
  const content = document.getElementById(`${cno}`).value;
  const mod = ref({
    bno,
    cno,
    writer: userInfo.userId,
    content,
  });
  console.log(mod.value);
  update(mod.value);
  modifyToggle(index);
};

const Cdelete = (val) => {
  const comment = {
    cno: val,
    bno: bno,
  };
  const flag = confirm('삭제하시겠습니까?');
  if (flag) {
    del(comment);
  }
};
</script>

<template>
  <div class="noComment" v-if="commentList.length === 0">
    작성된 댓글이 없습니다.
  </div>
  <div class="mainDiv" v-for="(c, index) in commentList" :key="c.cno">
    <div class="commentDiv">
      <span>{{ c.writer }}</span>
      <input class="list" type="text" :value="c.content" readonly />
      <div v-show="userInfo.userId === c.writer">
        <button @click="modifyToggle(index)">
          {{ openIndexes.includes(index) ? '닫기' : '수정' }}
        </button>
        <button @click="Cdelete(c.cno)">삭제</button>
      </div>
    </div>
    <form v-show="openIndexes.includes(index)">
      <span>{{ c.writer }}</span>
      <input class="modifyInput" type="text" :value="c.content" :id="c.cno" />
      <button @click.prevent="Cmodify(c.cno, index)">수정</button>
    </form>
  </div>
  <section>
    <span>{{ userInfo.userId }}</span>
    <input
      type="text"
      v-model="addComment.content"
      placeholder="댓글 내용을 입력하세요."
    />
    <button @click="addc">등록</button>
  </section>
</template>

<style scoped>
.noComment {
  width: 80%;
  margin: 0 auto;
  color: white;
}
section {
  /* margin-top: 15px; */
  width: 80%;
  margin: 15px auto 0;
}
.commentDiv {
  border-bottom: 1px solid white;
  border-radius: 0;
  height: 40px;
  display: flex;
  align-items: center;
}
.mainDiv {
  width: 80%;
  background-color: #333333;
  /* border-bottom: 1px solid lightgray; */
  margin: 0 auto;
  color: white;
}
.modifyInput {
  background-color: #555555;
}
span {
  display: inline-block;
  text-align: center;
  width: 100px;
  margin: 0 10px;
  color: white;
}
button {
  color: white;
  background-color: black;
  border: 1px solid white;
  width: 50px;
  height: 40px;
  margin-left: 10px;
}
.list {
  background-color: #333333;
  width: 300px;
  margin: 0 10px;
  border: none;
  outline: none;
}
input {
  width: 300px;
  margin: 0 10px;
  background-color: #333333;
  border: none;
  color: white;
}
</style>
