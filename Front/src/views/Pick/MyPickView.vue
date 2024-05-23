<script setup>
import Header from '@/component/common/Header.vue';
import InputBox from '@/component/common/InputBox.vue';
import PickItem from '@/component/pick/PickItem.vue';
import Weather from '@/component/common/Weather.vue';
import Meteo from '@/component/common/Meteo.vue';
import { storeToRefs } from 'pinia';
import { usePickStore } from '@/stores/pickStore';
import { useUserStore } from '@/stores/user';

const pickStore = usePickStore();
const useStore = useUserStore();

const { Jjimlist } = storeToRefs(pickStore);
const { userInfo } = storeToRefs(useStore);
const { list } = pickStore;
import { ref, onMounted } from 'vue';

onMounted(async () => {
  await list(userInfo.value.userId);
});

const test = 10;
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
          <h1 class="title">나의 찜리스트</h1>
          <p class="text">
            사용자가 찜 버튼으로 등록한 리스트들을 보여드립니다.
          </p>
        </div>
        <InputBox class="col-12 col-sm-5" />
      </div>
    </section>
    <section class="container-fluid">
      <div v-if="Jjimlist.length == 0" class="text">
        찜 목록이 존재하지 않습니다.
      </div>
      <div v-else class="conetntBox row">
        <PickItem
          v-for="j in Jjimlist"
          class="col-12 col-sm-6 col-md-3"
          :data="j"
          :type="'pick'"
        />
      </div>
    </section>
  </div>
</template>

<style scoped>
@media (max-width: 790px) {
  .content {
    background-size: 300% !important;
  }
  .PageTitle {
    height: 250px !important;
  }
  .titleBox {
    width: 100% !important;
    top: 80px !important;
    left: 0px !important;
  }
  table {
    font-size: 10px !important;
  }
  .btnArea {
    flex-direction: column;
  }
}
.content {
  min-height: 100vh;
  background-image: url('@/assets/img/pickImg.png');
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

/*  */
.conetntBox {
  padding-left: 30px;
  padding-right: 30px;
  margin-bottom: 40px;
}
</style>
