<script setup>
import { RouterView } from 'vue-router';
</script>

<template>
  <div id="content" class="container-fluid">
    <RouterView />
  </div>
</template>

<style scoped>
#content {
  padding: 0;
  min-height: 100vh;
  background-color: black;
  font-family: '프리텐더';
}
</style>
