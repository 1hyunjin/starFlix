<template>
  <h2>{{ title }}</h2>
  <div class="wrap" ref="wrap">
    <MainListItem class="" :params="info" />
  </div>
</template>

<script setup>
import MainListItem from './MainListItem.vue';
import { ref, onMounted } from 'vue';

const props = defineProps({
  title: String,
  data: Object,
});

const info = ref([]);
info.value = props.data;

const wrapRef = ref(null); // .wrap 요소에 대한 참조를 생성

let isMouseDown = false;
let startX = null;
let scrollLeft = null;

onMounted(() => {
  const wrapElement = document.querySelector('.wrap');
  if (wrapElement) {
    wrapRef.value = wrapElement;
    wrapElement.addEventListener('mousedown', handleMouseDown);
    wrapElement.addEventListener('mousemove', handleMouseMove);
    wrapElement.addEventListener('mouseup', handleMouseUp);
  } else {
    console.error('.wrap 요소를 찾을 수 없습니다.');
  }
});

const handleMouseDown = (event) => {
  isMouseDown = true;
  startX = event.pageX - wrapRef.value.getBoundingClientRect().left;
  scrollLeft = wrapRef.value.scrollLeft;
};

const handleMouseMove = (event) => {
  if (!isMouseDown) return;
  event.preventDefault();
  const x = event.pageX - wrapRef.value.getBoundingClientRect().left;
  const walk = (x - startX) * 3; // 조절 가능한 스크롤 속도
  wrapRef.value.scrollLeft = scrollLeft - walk;
};

const handleMouseUp = () => {
  isMouseDown = false;
};
</script>

<style scoped>
h2 {
  font-weight: bold;
  color: white;
}
.wrap {
  display: flex;
  flex-direction: row;
  overflow-x: scroll; /* 가로 스크롤을 가능하게 함 */
  overflow-y: hidden; /* 세로 스크롤을 숨김 */
  white-space: nowrap; /* 자식 요소들이 가로로 일렬로 나열되도록 함 */
  -webkit-overflow-scrolling: touch; /* iOS에서 부드러운 스크롤을 지원 */
  scrollbar-width: none; /* 스크롤바 숨김 */
}
.wrap::-webkit-scrollbar {
  display: none; /* 스크롤바 숨김 */
}
</style>
