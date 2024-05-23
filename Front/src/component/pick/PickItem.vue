<script setup>
import Btn from '../common/Btn.vue';
import { useRoute, useRouter } from 'vue-router';

import { storeToRefs } from 'pinia';
import { useMapStore } from '@/stores/mapStore';
import { usePickStore } from '@/stores/pickStore';
import { useUserStore } from '@/stores/user';

const pickStore = usePickStore();
const useStore = useUserStore();
const mapStore = useMapStore();

const { userInfo } = storeToRefs(useStore);
const { delPick } = pickStore;
const { place, isSearch, isAround, isResult, isResultDetail } =
  storeToRefs(mapStore);
const { info, delPlace, isPublic, isPrivate } = mapStore;

const props = defineProps({
  data: Object,
  type: String,
});

const route = useRoute();
const router = useRouter();
const type = props.type;
const movePage = async (val) => {
  console.log(val + ' 이 넘어갑니다. pick Item');
  await info(val);
  isSearch.value = true;
  isResult.value = false;
  isAround.value = false;
  isResultDetail.value = false;
  router.push({ name: 'placeInfo', query: { type: 'placeInfo', idx: val } });
};

const deleteJjim = async (val) => {
  await delPick(val, userInfo.value.userId);
  alert('찜 목록에서 삭제되었습니다.');
};

const deletePlace = async (val) => {
  await delPlace(val, userInfo.value.userId);
};

const togglePublic = async (idx, flag) => {
  console.log(idx, flag);
  if (flag === 'Y') {
    await isPrivate(idx);
  } else {
    await isPublic(idx);
  }
};
</script>

<template>
  <div class="Item">
    <div class="Img" :style="{ backgroundImage: `url(${data.img})` }">
      <div class="title">{{ data.title }}</div>
    </div>
    <div class="btns" v-if="type === 'pick'">
      <Btn
        :sty="'redBtn'"
        style="margin: 0"
        :text="'상세보기'"
        @click="movePage(data.starPlace)"
      />
      <Btn
        :sty="'blackBtn'"
        style="margin: 0"
        :text="'삭제하기'"
        @click="deleteJjim(data.starPlace)"
      />
    </div>
    <div v-if="type === 'addPlace'">
      <div>
        <div class="form-check form-switch checkBox">
          <input
            class="form-check-input ckeck"
            type="checkbox"
            role="switch"
            id="flexSwitchCheckDefault"
            :checked="data.isPublic === 'Y'"
            @change="togglePublic(data.idx, data.isPublic)"
          />
          <label class="form-check-label checkText" for="flexSwitchCheckDefault"
            >공개여부</label
          >
        </div>
        <div class="btns">
          <Btn
            :sty="'redBtn'"
            style="margin: 0"
            :text="'상세보기'"
            @click="movePage(data.idx)"
          />
          <Btn
            :sty="'blackBtn'"
            style="margin: 0"
            :text="'삭제하기'"
            @click="deletePlace(data.idx)"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.Item {
  margin-top: 20px;
}
.Img {
  position: relative;
  height: 300px;
  margin-bottom: 20px;
  cursor: pointer;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
}
.Img:hover .title {
  opacity: 1; /* 마우스를 올렸을 때 보이도록 변경 */
}
.title {
  height: 50px;
  width: 100%;
  position: absolute;
  bottom: 0;
  background-color: #d30000;
  text-align: center;
  color: white;
  font-size: 20px;
  font-weight: bold;
  line-height: 50px;
  transition: opacity 0.3s ease; /* 투명도 변경 시 애니메이션 효과 추가 */
  opacity: 0; /* 초기에는 숨김 */
}
.btns {
  display: flex;
  justify-content: space-between;
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
</style>
