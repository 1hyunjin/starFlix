<script setup>
import Header from '@/component/common/Header.vue';
import Meteo from '@/component/common/Meteo.vue';
import Weather from '@/component/common/Weather.vue';
import SelectBox from '@/component/common/SelectBox.vue';
import { RouterView } from 'vue-router';
import { useBoardStore } from '@/stores/boardStore';
import { ref } from 'vue';

const boardStore = useBoardStore();
const { blist } = boardStore;

const options = ref([
  { value: '', text: '검색조건' },
  { value: 'title', text: '제목' },
  { value: 'writer', text: '작성자' },
]);
const Param1 = ref(''); //검색조건
const Param2 = ref(''); //검색어
const changeKey = (val) => {
  // 검색조건 함수
  Param1.value = val;
  console.log(Param1.value);
};

const handleEnter = async () => {
  if (Param1.value == '') {
    alert('검색조건을 입력해주세요.');
  } else {
    const param = ref({
      type: Param1.value,
      keyword: Param2.value,
    });
    console.log(
      '넘기는 데이터 : ' + param.value.type + ' ' + param.value.keyword
    );
    await blist(param.value, 1);
  }
};
</script>

<template>
  <div class="content">
    <Header />
    <section class="PageTitle container-fruid">
      <div class="modalDiv">
        <Meteo />
        <Weather />
      </div>
      <div class="titleBox row">
        <div class="col-12 col-sm-7">
          <h1 class="title">유저 커뮤니티</h1>
          <p class="text">
            별자리 정보 및 다양한 정보들을 공유하는 게시판입니다.
          </p>
        </div>
        <div class="box">
          <SelectBox class="sbox" :options="options" @onKeySelect="changeKey" />
          <div class="input">
            <input
              type="text"
              placeholder="검색어를 입력해주세요"
              v-model="Param2"
              @keydown.enter="handleEnter"
            />
            <img src="@/assets/img/inputSearch.png" alt="검색" />
          </div>
        </div>
      </div>
    </section>
    <RouterView />
  </div>
</template>

<style scoped>
@media (max-width: 790px) {
  .content {
    background-size: 300% !important;
  }
  .PageTitle {
    height: 280px !important;
  }
  .text {
    font-size: 12px !important;
  }
  .titleBox {
    width: 100% !important;
    top: 120px !important;
    left: 0px !important;
  }
  table {
    font-size: 10px !important;
  }
}

@media (max-width: 910px) {
  .PageTitle {
    height: 300px !important;
  }
  .titleBox {
    top: 155px !important;
    left: 0px !important;
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
  border-bottom: 5px double white;
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
.modalDiv {
  position: absolute;
  width: 500px;
  right: 0;
  height: 50px;
  display: flex;
  justify-content: space-around;
}
.box {
  display: flex;
  flex-direction: row;
  width: fit-content;
  align-items: center;
}
.input {
  display: flex;
  align-items: center;
  border-radius: 10px;
  padding-left: 10px;
  width: 330px;
  height: 40px;
  background-color: #fff;
  border: none;
  margin-left: 10px;
}
input {
  width: 90%;
  border: none;
  outline: none;
}

/*  */

.boardArea {
  width: 100%;
  text-align: center;
  margin: 0 auto 20px;
}
.boardArea > div {
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
  color: white;
}
.bContent {
  width: 80%;
  min-width: 300px;
  height: 313px;
  background-color: #333333;
  border-radius: 10px;
  border: none;
  padding: 10px;
  color: white;
}
.btnArea {
  width: 80%;
  margin: 0 auto 50px;
  display: flex;
  justify-content: end;
}
</style>
