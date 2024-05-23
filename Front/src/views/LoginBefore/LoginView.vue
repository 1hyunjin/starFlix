<script setup>
import LoginRegistForm from '@/component/common/LoginRegistForm.vue';
import { useRoute } from 'vue-router';

const route = useRoute();

const type = route.params.type;
</script>

<template>
  <div class="content">
    <section class="formBox">
      <LoginRegistForm :type="type" />
    </section>
  </div>
</template>

<style scoped>
.content {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background-image: url('@/assets/img/main_Img.png');
  background-repeat: none;
  background-position: center center;
  background-size: cover;
  overflow: auto;
}
.formBox {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 50%;
  min-width: 400px;
  height: 700px;
  background-color: rgba(0, 0, 0, 0.5);
  margin: 50px auto;
}
</style>
