<script setup>
defineProps({
  text: String,
  sty: String,
});
</script>

<template>
  <button :class="sty">{{ text }}</button>
</template>

<style scoped>
@media (max-width: 790px) {
  .redBtn,
  .blackBtn {
    width: 100% !important;
    margin-right: 0px !important;
  }
}
.redBtn {
  color: white;
  background-color: #d30000;
  width: 120px;
  height: 55px;
  margin-right: 20px;
}
.blackBtn {
  color: white;
  background-color: black;
  border: 1px solid white;
  width: 120px;
  height: 55px;
  margin-right: 20px;
}
</style>
