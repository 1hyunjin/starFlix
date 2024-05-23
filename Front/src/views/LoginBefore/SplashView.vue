<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

// 지도 정보 받아오기
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/user';
import { useMapStore } from '@/stores/mapStore';
import { useEtcStore } from '@/stores/etcStore';

const mapStore = useMapStore();
const etcStore = useEtcStore();
const userStore = useUserStore();
const { myAddress } = storeToRefs(mapStore);
const { logout } = userStore;
const { weather, aste } = etcStore;
const { best, maplist } = mapStore;

// 날씨 정보 받아오기

const router = useRouter();

const param = ref({});
if (myAddress.value === '') {
  param.value = {
    type: 'addr',
    keyword: '서울',
  };
} else {
  param.value = {
    type: 'addr',
    keyword: myAddress.value.substring(0, 2),
  };
}

onMounted(async () => {
  const today = new Date(); // 년도
  const year = today.getFullYear(); // 월
  const month = (today.getMonth() + 1).toString().padStart(2, '0'); //일
  const day = today.getDate().toString().padStart(2, '0');
  const date = ref(`${year}${month}${day}`);

  if (sessionStorage.getItem('accessToken') != null) {
    console.log();
    await weather(date.value);
    await aste(date.value);
    await maplist(param.value); // 기본 서울
    await best();
    router.push({ name: 'home' });
  } else {
    logout();
    router.push({ name: 'main' });
  }
});
</script>

<template>
  <div class="content">
    <img
      class="img-fluid logo"
      src="@/assets/img/ssafyPJ_logo.png"
      width="40%"
      height="20%"
      alt="logo"
    />
    <p>로딩중입니다. 잠시만 기다려주세요.</p>
  </div>
</template>

<style scoped>
.content {
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.logo {
  display: block;
}
p {
  text-align: center;
  color: white;
  font-weight: bold;
  font-size: 15px;
  margin-top: 20px;
}
</style>
