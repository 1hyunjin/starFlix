<script setup>
defineProps({
  data: Object,
});
</script>

<template>
  <div class="Item">
    <div v-if="data.img === ''" class="Img noImg">
      <div class="title">{{ data.title }}</div>
    </div>
    <div v-else class="Img" :style="{ backgroundImage: `url(${data.img})` }">
      <div class="title">{{ data.title }}</div>
    </div>
  </div>
</template>

<style scoped>
.Item {
  margin-top: 20px;
}
.noImg {
  background-image: url('@/assets/img/noImg.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
}
.Img {
  position: relative;
  width: 100%;
  height: 100px;
  margin-bottom: 20px;
  cursor: pointer;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
}
.Img:hover .title {
  opacity: 1; /* 마우스를 올렸을 때 보이도록 변경 */
}
.title {
  height: 30px;
  width: 100%;
  position: absolute;
  bottom: 0;
  background-color: #d30000;
  text-align: center;
  color: white;
  font-size: 15px;
  font-weight: bold;
  line-height: 30px;
  transition: opacity 0.3s ease; /* 투명도 변경 시 애니메이션 효과 추가 */
  opacity: 0; /* 초기에는 숨김 */
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.btns {
  display: flex;
  justify-content: space-between;
}
</style>
