<script setup>
import { ref, onMounted } from 'vue';
import mainTitle from '@/assets/img/main_title.png';
import mainSubTitle from '@/assets/img/main_subtitle.png';
import smallLogo from '@/assets/img/smallLogo.png';
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';
import { useMapStore } from '@/stores/mapStore';

const mapStore = useMapStore();
const { myAddress, myLocation, mapInfo } = storeToRefs(mapStore);

const router = useRouter();
onMounted(() => {
  if ('geolocation' in navigator) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude, longitude } = position.coords;
        myLocation.value.latitude = latitude;
        myLocation.value.longitude = longitude;
        mapInfo.value.latitude = latitude;
        mapInfo.value.longitude = longitude;
        console.log(latitude, longitude);
      },
      (error) => {
        console.error('위치 정보를 가져오는 데 실패했습니다.', error);
      }
    );
  }
});

///////////////////////////////////////////////주소뽑기
let map = null;
let geocoder = null;
const latitude = myLocation.value.latitude; // 위도 값
const longitude = myLocation.value.longitude; // 경도 값
const address = ref('');

onMounted(() => {
  // 카카오맵 초기화
  initMap();

  // 위도, 경도 값을 주소로 변환
  getAddressFromCoords();
});

function initMap() {
  // 카카오맵을 생성하고 지도를 표시하는 코드
  const container = document.getElementById('map');
  const options = {
    center: new kakao.maps.LatLng(latitude, longitude),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  // 카카오맵 API의 Geocoder 객체 생성
  geocoder = new kakao.maps.services.Geocoder();
}

function getAddressFromCoords() {
  // 주어진 위도와 경도 값을 주소로 변환
  const coords = new kakao.maps.LatLng(latitude, longitude);
  geocoder.coord2Address(coords.getLng(), coords.getLat(), (result, status) => {
    if (status === kakao.maps.services.Status.OK) {
      address.value = result[0].address.address_name;
      console.log(address.value);
      myAddress.value = address.value;
    } else {
      console.error('Failed to get address from coordinates.');
    }
  });
}

///////////////////////////////////////////////////////

const movePage = (val) => {
  if (val === 'login') {
    router.push({ name: 'login', query: { type: val } });
  } else if (val === 'regist')
    router.push({ name: 'regist', query: { type: val } });
};
</script>

<template>
  <div id="map" ref="map"></div>
  <div class="content">
    <header>
      <img class="logo" :src="smallLogo" alt="smallLogo" />
    </header>
    <img class="titleImg img-fluid" :src="mainTitle" alt="title1" width="40%" />
    <img class="sub img-fluid" :src="mainSubTitle" alt="title2" width="25%" />
    <div class="btnBox container row sm-12 md-6 lg-6">
      <button @click="movePage('login')">로그인</button>
      <button @click="movePage('regist')">회원가입</button>
    </div>
  </div>
</template>

<style scoped>
header {
  display: flex;
  align-items: center;
  width: 100%;
  height: 100px;
  position: absolute;
  top: 0;
}
.logo {
  display: block;
  margin-left: 50px;
  width: 10%;
  min-width: 100px;
}

.content {
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-image: url('@/assets/img/main_Img.png');
  background-repeat: none;
  background-position: center center;
  overflow: auto;
}

.titleImg {
  margin-bottom: 30px;
  min-width: 300px;
}
.sub {
  min-width: 250px;
}

.btnBox {
  margin-top: 50px;
  width: 60%;
  display: flex;
  justify-content: space-around;
}

button {
  width: 200px;
  height: 50px;
  border-radius: 10px;
  margin: 10px;
  background-color: #db0000;
  border: none;
  color: white;
  font-size: 20px;
  font-family: '프리텐더';
}
</style>
