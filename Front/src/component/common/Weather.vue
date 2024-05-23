<template>
  <div class="contentDiv" @click="openModal">
    <div
      class="weather"
      v-for="(weather, index) in weatherList[0]"
      :key="weather.region"
    >
      <div ref="weatherText">
        Today Weather : {{ weather.region }}
        <img
          v-if="weather.weatherState != 0"
          :src="getImageUrlSky(weather.weatherState)"
          alt="rr"
          width="30px"
        />
        <img
          v-else
          class="weatherIcon"
          :src="getImageUrlSky(weather.rainyState)"
          alt="rr"
          width="30px"
        />
      </div>
    </div>
  </div>
  <div class="modal" v-if="showModal">
    <div class="modal-content">
      <span class="close" @click="closeModal">&times;</span>
      <ul>
        <li @click="weather(0)">오늘날씨</li>
        <li @click="weather(1)">내일날씨</li>
        <li @click="weather(2)">모레날씨</li>
        <li @click="asteOn">별 잘보이는 시간</li>
      </ul>
      <div class="weatherContent" v-show="flag">
        <div class="box w1">
          서울
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][0].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][0].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][0].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w2">
          강원
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][16].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][16].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][16].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w3">
          충북
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][9].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][9].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][9].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w4">
          충남
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][10].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][10].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][10].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w5">
          전북
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][11].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][11].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][11].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w6">
          전남
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][12].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][12].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][12].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w7">
          광주
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][4].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][4].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][4].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w8">
          경남
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][14].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][14].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][14].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w9">
          대구
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][2].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][2].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][2].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w10">
          경북
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][13].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][13].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][13].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w11">
          대전
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][5].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][5].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][5].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w12">
          세종
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][7].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][7].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][7].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w13">
          부산
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][1].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][1].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][1].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w14">
          울산
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][6].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][6].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][6].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w15">
          인천
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][3].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][3].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][3].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w16">
          제주
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][15].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][15].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][15].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
        <div class="box w17">
          경기
          <div class="weatherBox">
            <img
              v-if="weatherList[weatherIdx][8].weatherState != 0"
              class="weatherIcon"
              :src="getImageUrlSky(weatherList[weatherIdx][8].weatherState)"
              alt="rr"
              width="30px"
            />
            <img
              v-else
              class="weatherIcon"
              :src="getImageUrlRain(weatherList[weatherIdx][8].rainyState)"
              alt="rr"
              width="30px"
            />
          </div>
        </div>
      </div>
      <div class="asteContent" v-show="!flag">
        <div class="box2 w1">
          서울
          <div class="weatherBox">
            {{ asteList[0].aste.substring(0, 2) }}:{{
              asteList[0].aste.substring(2, 4)
            }}
          </div>
        </div>
        <div class="box2 w2">
          강릉
          <div class="weatherBox">
            {{ asteList[5].aste.substring(0, 2) }}:{{
              asteList[5].aste.substring(2, 4)
            }}
          </div>
        </div>
        <div class="box2 w4">
          홍성
          <div class="weatherBox">
            {{ asteList[4].aste.substring(0, 2) }}:{{
              asteList[4].aste.substring(2, 4)
            }}
          </div>
        </div>
        <div class="box2 w5">
          전주
          <div class="weatherBox">
            {{ asteList[2].aste.substring(0, 2) }}:{{
              asteList[2].aste.substring(2, 4)
            }}
          </div>
        </div>
        <div class="box2 w6">
          목포
          <div class="weatherBox">
            {{ asteList[10].aste.substring(0, 2) }}:{{
              asteList[10].aste.substring(2, 4)
            }}
          </div>
        </div>
        <div class="box2 w7">
          광주
          <div class="weatherBox">
            {{ asteList[9].aste.substring(0, 2) }}:{{
              asteList[9].aste.substring(2, 4)
            }}
          </div>
        </div>
        <div class="box2 w9">
          대구
          <div class="weatherBox">
            {{ asteList[6].aste.substring(0, 2) }}:{{
              asteList[6].aste.substring(2, 4)
            }}
          </div>
        </div>
        <div class="box2 w11">
          대전
          <div class="weatherBox">
            {{ asteList[3].aste.substring(0, 2) }}:{{
              asteList[3].aste.substring(2, 4)
            }}
          </div>
        </div>
        <div class="box2 w13">
          부산
          <div class="weatherBox">
            {{ asteList[8].aste.substring(0, 2) }}:{{
              asteList[8].aste.substring(2, 4)
            }}
          </div>
        </div>
        <div class="box2 w14">
          울산
          <div class="weatherBox">
            {{ asteList[7].aste.substring(0, 2) }}:{{
              asteList[7].aste.substring(2, 4)
            }}
          </div>
        </div>
        <div class="box2 w15">
          인천
          <div class="weatherBox">
            {{ asteList[1].aste.substring(0, 2) }}:{{
              asteList[1].aste.substring(2, 4)
            }}
          </div>
        </div>
        <div class="box2 w16">
          제주
          <div class="weatherBox">
            {{ asteList[11].aste.substring(0, 2) }}:{{
              asteList[11].aste.substring(2, 4)
            }}
          </div>
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

const { weatherList, asteList } = storeToRefs(etcStore);

const showModal = ref(false);
const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};

function getImageUrlSky(name) {
  return new URL(`../../assets/img/sky/${name}.png`, import.meta.url).href;
}
function getImageUrlRain(name) {
  return new URL(`../../assets/img/pky/${name}.png`, import.meta.url).href;
}

const flag = ref(true);
const asteOn = () => {
  flag.value = false;
};

onMounted(() => {
  autoScroll();
});

function autoScroll() {
  const contentDiv = document.querySelector('.contentDiv');
  const scrollInterval = 2000; // 2초 간격으로 자동 스크롤링 실행

  function getMaxWeatherHeight(contentDiv) {
    let maxWeatherHeight = 0;
    const weatherItems = contentDiv.querySelectorAll('.weather');
    weatherItems.forEach((item) => {
      const height = item.clientHeight;
      maxWeatherHeight = Math.max(maxWeatherHeight, height);
    });
    return maxWeatherHeight;
  }
  if (contentDiv) {
    setInterval(() => {
      const maxWeatherHeight = getMaxWeatherHeight(contentDiv);
      const scrollTop = getNextScrollTop(contentDiv, maxWeatherHeight);
      scrollToPosition(contentDiv, scrollTop);
    }, scrollInterval);
  }
}

function getNextScrollTop(contentDiv, maxWeatherHeight) {
  let scrollTop = contentDiv.scrollTop;
  const weatherItemsCount = contentDiv.querySelectorAll('.weather').length;
  const lastItemHeight = maxWeatherHeight;

  if (scrollTop >= (weatherItemsCount - 1) * lastItemHeight - 10) {
    scrollTop = 0;
  } else {
    scrollTop += lastItemHeight;
  }
  return scrollTop;
}

function scrollToPosition(contentDiv, scrollTop) {
  contentDiv.scrollTo({
    top: scrollTop,
    behavior: 'smooth',
  });
}

const weatherIdx = ref(0);

const weather = (val) => {
  weatherIdx.value = val;
  flag.value = true;
};
</script>

<style scoped>
.weatherBox {
  position: relative;
  /* border: 1px solid red; */
  width: 100%;
  height: 25px;
}
.weatherIcon {
  position: absolute;
  left: 9px;
  bottom: 0;
}

ul {
  display: flex;
  list-style: none;
}
li {
  font-size: 12px;
  margin-left: 5px;
  width: 90px;
  text-align: center;
  height: 30px;
  line-height: 30px;
  border-radius: 10px;
  background-color: #d7d7d7;
  cursor: pointer;
}
.box {
  width: 50px;
  height: 50px;
  border: 1px solid black;
  position: absolute;
  background-color: white;
  text-align: center;
  border-radius: 10px;
}
.box2 {
  width: 60px;
  height: 50px;
  border: 1px solid black;
  position: absolute;
  background-color: white;
  text-align: center;
  border-radius: 10px;
}

.w1 {
  top: 20px;
  left: 100px;
}
.w2 {
  top: 20px;
  left: 300px;
}
.w3 {
  top: 120px;
  left: 200px;
}
.w4 {
  top: 180px;
  left: 40px;
}
.w5 {
  top: 300px;
  left: 100px;
}
.w6 {
  top: 400px;
  left: 140px;
}
.w7 {
  top: 400px;
  left: 50px;
}
.w8 {
  top: 370px;
  left: 220px;
}
.w9 {
  top: 270px;
  left: 290px;
}
.w10 {
  top: 190px;
  left: 350px;
}
.w11 {
  top: 240px;
  left: 150px;
}
.w12 {
  top: 170px;
  left: 120px;
}
.w13 {
  bottom: 50px;
  right: 100px;
}
.w14 {
  bottom: 120px;
  right: 40px;
}
.w15 {
  top: 20px;
  left: 30px;
}
.w16 {
  bottom: 0px;
  left: 100px;
}
.w17 {
  top: 70px;
  left: 150px;
}
.contentDiv {
  width: 250px;
  height: 38px;
  border-radius: 10px;
  cursor: pointer;
  color: white;
  background-color: rgba(0, 0, 0, 0.8);
  text-align: center;
  overflow: hidden; /* 스크롤바를 보이지 않게 함 */
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
}

.weather {
  margin: 0;
  padding: 0;
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

.weatherContent {
  position: relative;
  width: 100%;
  border: 1px solid red;
  background-image: url('../../assets/img/weather.png');
  height: 500px;
  background-position: center center;
  background-repeat: no-repeat;
}
.asteContent {
  position: relative;
  width: 100%;
  border: 1px solid red;
  background-image: url('../../assets/img/weather.png');
  height: 500px;
  background-position: center center;
  background-repeat: no-repeat;
}
</style>
