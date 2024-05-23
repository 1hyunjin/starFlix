<script setup>
import Btn from '@/component/common/Btn.vue';
import Editor from '@/component/board/Editor.vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const movePage = (val) => {
  router.push({ name: val });
};
</script>

<template>
  <section class="container-fluid">
    <div class="boardArea">
      <div>
        <input type="text" class="bTitle" placeholder="제목을 입력하세요" />
        <input type="text" class="bWriter" placeholder="작성자" readonly />
      </div>
      <!-- 에디터 적용 전 원래 textarea -->
      <!-- <textarea class="bContent" placeholder="내용을 입력하세요"></textarea> -->
      <!-- <div class="bContent">
        <Editor />
      </div> -->
      <Editor class="textBox" />
    </div>
    <!-- <div class="btnArea">
      <Btn :sty="'redBtn'" :text="'등록'" />
      <Btn :sty="'blackBtn'" :text="'이전'" @click="movePage('list')" />
    </div> -->
  </section>
</template>

<style scoped>
@media (max-width: 500px) {
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
  border: 1px solid red;
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
  border: 3px solid white;
  width: 100%;
  margin-bottom: 20px;
}

/*  */

.boardArea {
  width: 100%;
  text-align: center;
  margin: 0 auto 20px;
  border: 5px solid red;
  height: 100%;
}
.boardArea + div {
  margin: 0 auto;
  width: 100%;
  border: 1px solid yellow;
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
  color: white;
}
.bContent {
  width: 80%;
  min-width: 300px;
  height: 600px;
  background-color: #333333;
  border-radius: 10px;
  border: none;
  padding: 10px;
  margin: 0 auto;
  color: white;
}

.textBox {
  position: relative;
}
</style>
