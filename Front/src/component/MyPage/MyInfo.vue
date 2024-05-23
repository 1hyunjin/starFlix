<script setup>
import LoginRegistForm from '../common/LoginRegistForm.vue';
import Btn from '@/component/common/Btn.vue';
import { defineStore, storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/user';

const memberStore = useUserStore();
const { Update, Delete } = memberStore;
const { userInfo, newInfo } = storeToRefs(memberStore);

const delUser = () => {
  const pw = prompt(
    '정말로 회원탈퇴 하시겠습니까?\n탈퇴하시려면 아래 비밀번호를 입력해주세요'
  );
  if (userInfo.value.userPw === pw) {
    Delete(userInfo.value.userId);
  } else if (pw != null && userInfo.value.userPw !== pw) {
    alert('입력한 정보가 일치하지 않습니다.');
  }
};

const updateUser = () => {
  if (
    userInfo.value.userName === newInfo.value.userName &&
    userInfo.value.userEmail === newInfo.value.userEmail &&
    userInfo.value.userPw === newInfo.value.userPw
  ) {
    alert('변경된 정보가 없습니다.');
  } else {
    console.log('다름');
    if (newInfo.value.userPw === newInfo.value.CheckPw) {
      newInfo.value.userId = userInfo.value.userId;
      console.log(newInfo.value);
      Update(newInfo.value);
    } else {
      alert('비밀번호 정보가 일치하지 않습니다.');
    }
  }
};
</script>

<template>
  <div class="InfoBox">
    <LoginRegistForm :type="'Info'" class="InfoDiv" :info="userInfo" />
    <div>
      <Btn :text="'수정하기'" :sty="'redBtn'" @click="updateUser" />
      <Btn :text="'회원탈퇴'" :sty="'blackBtn'" @click="delUser" />
    </div>
  </div>
</template>

<style scoped>
@media (max-width: 530px) {
  .InfoBox {
    flex-direction: column;
  }
  .InfoDiv {
    width: 100% !important;
    min-width: 0px !important;
    margin: 0 auto !important;
  }
}

.InfoBox {
  display: flex;
  justify-content: space-between;
}
.InfoDiv {
  width: 40%;
  min-width: 350px;
  margin: 0;
}
</style>
