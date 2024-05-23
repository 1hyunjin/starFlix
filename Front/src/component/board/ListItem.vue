<script setup>
import { useRoute, useRouter } from 'vue-router';
defineProps({
  test: Object,
});

const router = useRouter();
const route = useRoute();
const pg = route.query.pg;
const movePage = (val) => {
  console.log(val);
  router.push({ name: 'read', query: { pg, bno: val } });
};
</script>

<template>
  <tbody>
    <tr
      class="boardItem"
      v-for="t in test"
      :key="t.bno"
      @click="movePage(t.bno)"
    >
      <td class="t1">{{ t.bno }}</td>
      <td class="t2">{{ t.title }}</td>
      <td class="t3">{{ t.writer }}</td>
      <td class="t4">{{ t.readCount }}</td>
      <td class="t5">{{ t.hitCount }}</td>
    </tr>
  </tbody>
</template>

<style scoped>
.boardItem {
  cursor: pointer;
}
</style>
