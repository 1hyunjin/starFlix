<template>
  <div class="contentsDiv" @click="openModal">
    <img src="@/assets/img/starIcon.png" alt="" width="20px" />
    <p class="title">올해의 유성우 NEWS</p>
  </div>
  <div class="modal" v-if="showModal">
    <div class="modal-content">
      <span class="close" @click="closeModal">&times;</span>
      <div>
        <h2 class="atitle">올해의 유성우 NEWS</h2>
        <div class="meteoDiv" v-for="meteo in meteoList">
          날짜 : {{ meteo.locdate.substring(0, 4) }}년
          {{ meteo.locdate.substring(4, 6) }}월호
          <p class="mainText">{{ meteo.astroTitle }}</p>
          <p class="subText">{{ meteo.astroEvent }}</p>
        </div>
      </div>
      <button @click="closeModal">닫기</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useEtcStore } from '@/stores/etcStore';
const etcStore = useEtcStore();

const { meteoList } = storeToRefs(etcStore);
const { meteoInfo } = etcStore;

const showModal = ref(false);
const openModal = async () => {
  showModal.value = true;
  const date = new Date();
  await meteoInfo(date.getFullYear());
};

const closeModal = () => {
  showModal.value = false;
};
</script>

<style scoped>
.contentsDiv {
  width: 150px;
  height: 40px;
  font-size: 12px;
  cursor: pointer;
  color: white;
  border-radius: 10px;
  background-color: rgba(0, 0, 0, 0.8);
  text-align: center;
  line-height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.title {
  margin: 0 0 0 5px;
}
.atitle {
  font-weight: bold;
}
.mainText {
  font-weight: bold;
  font-size: 18px;
}
.subText {
  font-size: 13px;
}
.meteoDiv {
  border: 1px solid #f7f7f7;
  padding: 10px;
  border-radius: 10px;
  background-color: gainsboro;
  margin-top: 5px;
}

.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  width: 100%;
  height: 100%;
  margin: 0 auto;
  background-color: rgba(0, 0, 0, 0.5); /* 배경을 반투명하게 */
}

.modal-content {
  min-width: 500px;
  width: 500px;
  background-color: rgb(255, 255, 255);
  padding: 20px;
  border-radius: 10px;
  margin: 0 auto;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>
