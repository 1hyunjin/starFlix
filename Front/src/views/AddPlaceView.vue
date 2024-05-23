<script setup>
import Header from '@/component/common/Header.vue';
import Btn from '@/component/common/Btn.vue';
import SelectBox from '@/component/common/SelectBox.vue';
import { useRoute, useRouter } from 'vue-router';
import { onMounted, ref, onUnmounted } from 'vue';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/user';
import { useMapStore } from '@/stores/mapStore';

const useStore = useUserStore();
const mapSotre = useMapStore();
const { userInfo } = storeToRefs(useStore);
const { add } = mapSotre;

// 파베
import {
  getStorage,
  ref as storageRef,
  uploadBytesResumable,
  getDownloadURL,
} from 'firebase/storage';
import { v4 as uuidv4 } from 'uuid';

const route = useRoute();
const router = useRouter();
const lati = ref('');
const longj = ref('');
const addr = ref('');

const map = ref(null);
const searchType = ref('address'); // 기본값은 주소
const searchKeyword = ref('');

let marker = null;

onMounted(() => {
  const container = map.value;
  const options = {
    center: new kakao.maps.LatLng(37.5665, 126.978), // 초기 중심 위치 (서울 시청)
    level: 8, // 초기 확대 수준
  };
  const kakaoMap = new kakao.maps.Map(container, options);

  // 검색어로 지도 이동 및 마커 표시 함수
  const searchLocation = () => {
    const keyword = searchKeyword.value;
    if (keyword.trim() !== '') {
      const geocoder = new kakao.maps.services.Geocoder();
      const ps = new kakao.maps.services.Places();
      if (searchType.value === 'address') {
        // 주소 검색
        geocoder.addressSearch(keyword, function (result, status) {
          handleSearchResult(result, status, kakaoMap);
        });
      } else {
        // 키워드 검색
        ps.keywordSearch(keyword, function (result, status) {
          handleSearchResult(result, status, kakaoMap);
        });
      }
    }
  };

  // 클릭한 위치에 마커 추가 및 정보 출력
  kakao.maps.event.addListener(kakaoMap, 'click', function (mouseEvent) {
    const latLng = mouseEvent.latLng;
    if (marker) {
      marker.setMap(null); // 이전 마커 삭제
    }
    marker = new kakao.maps.Marker({
      position: latLng,
      map: kakaoMap,
    });

    // 좌표를 주소로 변환
    const geocoder = new kakao.maps.services.Geocoder();
    geocoder.coord2Address(
      latLng.getLng(),
      latLng.getLat(),
      function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
          const address = result[0].address.address_name;
          addr.value = address;
          console.log('클릭한 위치의 주소:', address);
        } else {
          console.error('주소 변환 실패:', status);
        }
      }
    );
    lati.value = latLng.getLat();
    longj.value = latLng.getLng();
    console.log('클릭한 위치의 위도:', latLng.getLat());
    console.log('클릭한 위치의 경도:', latLng.getLng());
  });

  // 검색 결과에 따라 처리하는 함수
  const handleSearchResult = (result, status, map) => {
    if (status === kakao.maps.services.Status.OK) {
      const latLng = new kakao.maps.LatLng(result[0].y, result[0].x);
      map.setCenter(latLng); // 검색 결과 위치로 지도 이동
      if (marker) {
        marker.setMap(null); // 이전 마커 삭제
      }
      marker = new kakao.maps.Marker({
        position: latLng,
        map: map,
      });
    } else {
      alert('검색 결과가 없습니다.');
    }
  };

  // 엔터 키 입력시 검색 실행
  const handleEnterKey = (event) => {
    if (event.key === 'Enter') {
      searchLocation();
    }
  };

  window.addEventListener('keydown', handleEnterKey);
});

// 컴포넌트가 제거될 때 이벤트 리스너 제거
// onUnmounted(() => {
//   window.removeEventListener('keydown', handleEnterKey);
// });

// 파베
const selectedFile = ref(null);
const storage = getStorage();
const uploadedFileName = ref('');
const url = ref('');

// 파일 선택을 위한 함수
const openFilePicker = () => {
  const input = document.createElement('input');
  input.type = 'file';
  input.accept = 'image/*'; // 이미지 파일만 선택 가능하도록 설정
  input.onchange = handleFileSelected;
  input.click(); // 파일 선택 창 열기
};

const handleFileSelected = (event) => {
  selectedFile.value = event.target.files[0];
  uploadImage(); // 파일 선택 후 업로드 함수 호출
};

const uploadImage = async () => {
  if (!selectedFile.value) {
    alert('파일을 선택하세요.');
    return;
  }

  const fileName = `${uuidv4()}-${selectedFile.value.name}`; // UUID와 파일 이름 결합
  const storageRefValue = storageRef(storage, `uploads/${fileName}`); // storageRef 초기화

  const uploadTask = uploadBytesResumable(storageRefValue, selectedFile.value);

  uploadTask.on(
    'state_changed',
    (snapshot) => {
      // 업로드 진행 상황 추적
      const progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
      console.log(`Upload is ${progress}% done`);
    },
    (error) => {
      // 업로드 실패 처리
      console.error('업로드 실패:', error);
      alert('업로드 중 오류가 발생했습니다.');
    },
    () => {
      // 업로드 성공 처리
      getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
        url.value = downloadURL;
        console.log('파일이 성공적으로 업로드되었습니다.', downloadURL);
        uploadedFileName.value = fileName; // 업로드된 파일 이름 저장
        alert('파일이 성공적으로 업로드되었습니다.');
      });
    }
  );
};
//

const movePage = (val) => {
  if (val === 'pre') {
    router.go(-1);
  } else {
    router.push({ name: val, query: { root: val } });
  }
};

const check = ref('');

const param = ref({
  title: '',
  addr: '',
  writer: userInfo.value.userId,
  content: '',
  img: '',
  lati: '',
  longj: '',
  isPublic: 'N',
});

const addMe = async () => {
  //저장
  if (
    addr.value == '' ||
    lati.value == '' ||
    longj.value == '' ||
    url.value == ''
  ) {
    alert('등록할 곳의 좌표를 설정하고 사진을 첨부해주세요.');
  } else {
    param.value.addr = addr.value;
    param.value.lati = lati.value;
    param.value.longj = longj.value;
    param.value.img = url.value;
    param.value.isPublic = check.value ? 'Y' : 'N';
    console.log(param.value);
    await add(param.value);
  }
};

const options = ref([
  { value: '', text: '검색조건' },
  { value: 'address', text: '주소' },
  { value: 'keyword', text: '검색어' },
]);
</script>

<template>
  <div class="content">
    <Header />
    <section class="mainContent">
      <div class="mapBox">
        <div class="seaechBox">
          <SelectBox
            :options="options"
            @onKeySelect="changeKey"
            v-model="searchType"
          />
          <div class="input">
            <input
              type="text"
              placeholder="검색어를 입력해주세요"
              v-model="searchKeyword"
              @keydown.enter="searchLocation"
            />
            <img src="@/assets/img/inputSearch.png" alt="검색" />
          </div>
        </div>
        <div ref="map" class="map" />
        <div class="Info">
          <div>
            <h1 class="InfoTitle">전국 별자리 명소 검색</h1>
            <p class="InfoText">
              사용자가 임의로 별 명소를 등록 및 공유할 수 있습니다.
            </p>
          </div>
          <div class="Area">
            <Btn :sty="'redBtn'" :text="'추가'" @click="addMe" />
            <Btn :sty="'blackBtn'" :text="'이전'" @click="movePage('pre')" />
          </div>
        </div>
      </div>
      <div class="menuBox">
        <div class="textBox">
          <input
            class="rTitle"
            type="text"
            placeholder="제목을 입력하세요"
            v-model="param.title"
          />
          <textarea
            class="rContent"
            placeholder="내용을 입력해주세요."
            v-model="param.content"
          ></textarea>
        </div>
        <div class="form-check form-switch checkBox">
          <input
            class="form-check-input ckeck"
            type="checkbox"
            role="switch"
            id="flexSwitchCheckDefault"
            v-model="check"
          />
          <label class="form-check-label checkText" for="flexSwitchCheckDefault"
            >공개여부</label
          >
        </div>
        <div class="btnArea">
          <!-- 파일 업로드 input 숨김 처리 -->
          <div class="text" v-if="uploadedFileName">첨부완료</div>
          <div class="text" v-else>파일 미첨부</div>
          <Btn :sty="'redBtn'" :text="'첨부파일'" @click="openFilePicker" />
        </div>
      </div>
      <!-- <AddPlace /> -->
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

@media (max-width: 790px) {
  .btnArea {
    flex-direction: column;
    margin-top: 20px;
  }
}
.menuBox {
  width: 100%;
  height: 100%;
  margin-top: 30px;
  border-radius: 15px;
  background-color: rgba(0, 0, 0, 0.4);
  margin: 0 auto;
}
.textBox {
  text-align: center;
}
.rTitle {
  width: 80%;
  min-width: 220px;
  height: 45px;
  background-color: #333333;
  border-radius: 10px;
  border: none;
  padding-left: 10px;
  padding-right: 10px;
  margin-bottom: 20px;
  margin: 0 auto 20px;
  color: white;
}
.rContent {
  width: 80%;
  min-width: 200px;
  height: 520px;
  background-color: #333333;
  border-radius: 10px;
  border: none;
  padding: 10px;
  margin: 0 auto 20px;
  color: white;
  text-align: start;
}

.btnArea {
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.checkBox {
  width: fit-content;
  margin: 0 auto;
}
.form-check-input:checked {
  background-color: #d30000;
  border-color: #d30000;
}
.checkText {
  color: white;
}

.form-control {
  width: 300px;
}
</style>
