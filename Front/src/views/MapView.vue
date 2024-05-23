<script setup>
import Header from '@/component/common/Header.vue';
import Btn from '@/component/common/Btn.vue';
import SelectBox from '@/component/common/SelectBox.vue';
import mapVue from '@/component/kakaoMap/mapVue.vue';
import { useRoute, useRouter, onBeforeRouteLeave } from 'vue-router';
import { RouterView } from 'vue-router';
import { ref } from 'vue';

import { storeToRefs } from 'pinia';
import { useMapStore } from '@/stores/mapStore';
const mapStore = useMapStore();
const {
  place,
  aroundList,
  searchList,
  myLocation,
  mapInfo,
  userPlaceList,
  isSearch,
  isResult,
  isAround,
  isResultDetail,
} = storeToRefs(mapStore);
const { info, searchPlace } = mapStore;

import { listPlace2 } from '@/api/map';

const route = useRoute();
const router = useRouter();
const mode = route.query.type;

onBeforeRouteLeave((to, from, next) => {
  console.log(to.name, from.name, next);
  // 맵을 나가면 모든 기록들 초기화
  if (to.name != 'addPlace' && from.name != 'placeInfo') {
    place.value = null;
    aroundList.value = null;
    searchList.value = null;
    mapInfo.value.latitude = myLocation.value.latitude; //맵을 나가면 얘는 내 기준으로 다시 설정
    mapInfo.value.longitude = myLocation.value.longitude;
    console.log('삭제');
  }
  next();
});

const movePage = (val) => {
  if (val === 'pre') {
    router.go(-1);
  } else {
    router.push({ name: val, query: { root: val } });
  }
};

const options = ref([
  { value: '', text: '검색조건' },
  { value: 'addr', text: '주소' },
  { value: 'title', text: '검색어' },
]);

const Param1 = ref(''); //검색조건
const changeKey = (val) => {
  // 검색조건 함수
  Param1.value = val;
  console.log(Param1.value);
};
const k = ref('');

const search = ref([]);
const handleEnter = async () => {
  if (Param1.value == '') {
    alert('검색조건을 입력해주세요.');
  } else {
    const param = ref({
      type: Param1.value,
      keyword: k.value,
    });
    console.log('넘기는 데이터 : ' + param.value);
    await listPlace2(
      param.value,
      (resp) => {
        if (resp.status === 200) {
          searchList.value = resp.data;
          if (searchList.value.length >= 1) {
            mapInfo.value.latitude = searchList.value[0].lati;
            mapInfo.value.longitude = searchList.value[0].longj;
          }
          router.push({ name: 'map', query: { type: 'mapHome' } });
          console.log(search.value);
        }
      },
      (error) => {
        alert('에러가 발생했습니다. 잠시후 다시 시도해주세요.');
      }
    );
  }
};

const type = route.params.type;
const activeMenu = ref(type); // 메뉴 클릭시 효과 변수

function setActiveMenu(menu) {
  // 메뉴함수
  router.push({ name: menu, params: { type: menu } });
  activeMenu.value = menu;
}
</script>

<template>
  <div class="content">
    <Header />
    <section class="mainContent">
      <div class="mapBox">
        <div class="seaechBox">
          <SelectBox :options="options" @onKeySelect="changeKey" />
          <div class="input">
            <input
              type="text"
              placeholder="검색어를 입력해주세요"
              v-model="k"
              @keydown.enter="handleEnter"
            />
            <img src="@/assets/img/inputSearch.png" alt="검색" />
          </div>
        </div>
        <mapVue class="map" />
        <div class="Info">
          <div>
            <h1 class="InfoTitle">전국 별자리 명소 검색</h1>
            <p class="InfoText">
              사용자가 임의로 별 명소를 등록 및 공유할 수 있습니다.
            </p>
          </div>
          <div class="Area">
            <Btn :sty="'redBtn'" :text="'추가'" @click="movePage('addPlace')" />
            <Btn :sty="'blackBtn'" :text="'이전'" @click="movePage('pre')" />
          </div>
        </div>
      </div>
      <RouterView />
    </section>
  </div>
</template>

<style scoped>
@media (max-width: 790px) {
  .content {
    background-size: 300% !important;
  }
  .Area {
    margin: 10px;
    width: 40% !important;
    height: fit-content;
  }
}

@media (max-width: 480px) {
  .map {
    height: 400px !important;
  }
  .Info {
    flex-direction: column;
  }
  .InfoText {
    font-size: 12px !important;
  }
  .Area {
    width: 80% !important;
    height: fit-content;
  }
  .seaechBox {
    font-size: 12px;
  }
  .inputBox {
    width: 60% !important;
  }
}

@media (max-width: 1000px) {
  .mainContent {
    flex-direction: column;
  }
  .Area {
    flex-direction: row !important;
  }
  .menuBox {
    margin: 0 auto;
  }
}
@media (max-width: 1324px) {
  .Area {
    display: flex;
    flex-direction: column;
  }
}

@media (max-width: 910px) {
  .input {
    margin-top: 0px !important;
  }
}
.input {
  display: flex;
  align-items: center;
  border-radius: 10px;
  padding-left: 10px;
  width: 330px;
  height: 40px;
  background-color: #fff;
  border: none;
  margin-left: 10px;
}
input {
  width: 90%;
  border: none;
  outline: none;
}
img {
  cursor: pointer;
}
/* 여기까지 인풋 */

.content {
  min-height: 100vh;
  background-image: url('@/assets/img/mapImg.png');
  background-repeat: no-repeat;
  background-size: 100%;
  background-position: top;
  overflow: auto;
}
.mainContent {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  display: flex;
}
.mapBox {
  width: 100%;
  height: 100%;
  position: relative;
  padding: 20px;
}
.seaechBox {
  width: fit-content;
  display: flex;
  margin-bottom: 20px;
}

.inputBox {
  margin: 0 0 0 10px;
}

.map {
  width: 100%;
  min-height: 500px;
  margin: 20px 0;
}

.Info {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}
.InfoTitle {
  color: white;
  font-weight: bold;
}
.InfoText {
  color: white;
}
.Area {
  position: relative;
}

/* 오른쪽 */
.menuBox {
  width: 100%;
  height: 100%;
  margin-top: 30px;
  border-radius: 15px;
  background-color: rgba(0, 0, 0, 0.4);
}

.myMenu {
  width: 95%;
  margin: 0 auto;
  list-style: none;
  color: white;
  font-size: 30px;
  font-weight: bold;
  display: flex;
}
.myMenu > li {
  margin-right: 30px;
  cursor: pointer;
  color: #757575;
}
.active {
  color: white !important;
  border-bottom: 4px solid #d30000;
}
</style>
