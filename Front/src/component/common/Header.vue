<script setup>
import homeImg from '@/assets/img/homeImg.png';
import smallLogo from '@/assets/img/smallLogo.png';
import logoutIcon from '@/assets/img/logoutIcon.png';
import { useRoute, useRouter } from 'vue-router';

import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/user';

const route = useRoute();
const router = useRouter();
const memberStore = useUserStore();
const { logout } = memberStore;
const { userInfo } = storeToRefs(memberStore);

const userLogout = () => {
  logout(userInfo.value.userId);
};

const movePage = (val) => {
  if (val === 'mypage') {
    router.push({ name: val, query: { type: 'userInfo' } });
  } else if (val === 'map') {
    router.push({ name: val, query: { type: 'mapHome' } });
  } else if (val === 'boardList') {
    router.push({ name: val, query: { pg: 1 } });
  } else {
    router.push({ name: val });
  }
};
</script>

<template>
  <header class="container-fluid">
    <div class="row headerDiv">
      <div class="col-sm-12 col-md-2 col-lg-2 imgBox">
        <img
          class="logo"
          :src="smallLogo"
          alt="smallLogo"
          @click="movePage('home')"
        />
      </div>
      <nav class="col-sm-10 col-md-9 col-lg-8">
        <ul class="navMenu row">
          <li class="col-12 col-sm-3" @click="movePage('map')">
            <span>별명소 지도</span>
          </li>
          <li class="col-12 col-sm-3" @click="movePage('pick')">
            <span>내가 찜한 곳</span>
          </li>
          <li class="col-12 col-sm-3" @click="movePage('boardList')">
            <span>유저 커뮤니티</span>
          </li>
          <li class="col-12 col-sm-3" @click="movePage('mypage')">
            <span>마이페이지</span>
          </li>
        </ul>
      </nav>
      <div class="col-sm-1 col-md-1 col-lg-2 searchBox">
        <img :src="logoutIcon" alt="검색" @click="userLogout" width="30px" />
      </div>
    </div>
  </header>
</template>

<style scoped>
@media (max-width: 790px) {
  .navMenu {
    font-size: 15px !important;
  }
}
@media (max-width: 576px) {
  .searchBox {
    width: 50px;
    position: absolute;
    right: 0px;
  }
  .logo {
    margin-top: 20px !important;
  }
}
header {
  width: 100%;
  height: 100%;
  min-height: 80px;
  padding: 0;
  display: flex;
  align-items: center;
}
.headerDiv {
  position: relative;
  width: 100%;
  margin: 0;
}
.logo {
  display: block;
  width: 150px;
  min-width: 100px;
  margin: 0 auto;
  cursor: pointer;
}
.navMenu {
  color: white;
  font-size: 20px;
  list-style: none;
  display: flex;
  line-height: 50px;
  font-weight: bold;
  margin: 0;
  padding: 0;
}
.navMenu > li {
  text-align: center;
}
.navMenu > li > span {
  cursor: pointer;
}
.searchBox {
  text-align: right;
  padding-right: 40px;
  line-height: 50px;
}
</style>
