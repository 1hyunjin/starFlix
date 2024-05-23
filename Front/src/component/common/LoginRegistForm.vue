<script setup>
import { useRoute, useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/user';
import { onMounted, ref, watch } from 'vue';

const props = defineProps({
  info: Object,
});
const router = useRouter();
const route = useRoute();
// const type = route.param.type; //폼 타입
const type = ref(route.query.type);

const memberStore = useUserStore();

const { isLogin, isLoginError, newInfo } = storeToRefs(memberStore);
const { login, getUserInfo, regist } = memberStore;

const user = ref({
  userId: '',
  userPw: '',
  CheckPw: '',
  userName: '',
  userEmail: '',
});

watch(
  //동적라우팅 화면전환 안되는 부분 watch로 해결
  () => route.query.type,
  (nv, ov) => {
    console.log('??' + ov, nv);
    type.value = nv;
  },
  {
    immediate: true,
  }
);

onMounted(() => {
  console.log('등장');
  console.log(props.type);
  console.log(props.info);
  if (props.info != null) {
    user.value.userName = props.info.userName;
    user.value.userEmail = props.info.userEmail;
    user.value.userPw = props.info.userPw;
    newInfo.value = user.value;
    // user.value = props.info;
    console.log(user.value);
  }
});

const userlogin = async () => {
  await login(user.value);
  let token = sessionStorage.getItem('accessToken');
  console.log('가져온 토큰 ' + token);
  if (isLogin.value) {
    getUserInfo(token);
    router.replace({ name: 'splash' });
  }
};

const userRegist = async () => {
  await regist(user.value);
};

const changeInfo = () => {
  newInfo.value = user.value;
};

const onSubmit = () => {
  if (type.value === 'login') {
    userlogin();
  } else if (type.value === 'regist') {
    if (
      user.value.userId === '' ||
      user.value.userEmail === '' ||
      user.value.userName === '' ||
      user.value.CheckPw === '' ||
      user.value.userPw === ''
    ) {
      alert('입력 정보 중 누락된 정보가 있습니다.');
    } else if (
      !userNameWarning.value ||
      !userIdWarning.value ||
      !userPwWarning.value ||
      !userEmailWarning.value ||
      !checkPwWarning.value
    ) {
      console.log(
        userNameWarning.value +
          ' ' +
          userIdWarning.value +
          ' ' +
          userPwWarning.value +
          ' ' +
          userEmailWarning.value +
          ' ' +
          checkPwWarning.value
      );
      alert('입력 정보가 유효하지 않습니다.');
    } else {
      userRegist();
    }
  } else if (type === 'info') {
    const flag = confirm('수정하시겠습니까?');
    if (flag) {
      //수정
    }
  }
};

const movePage = (val) => {
  if (val == 'find') {
    router.push({ name: 'find', query: { type: 'find' } });
  } else {
    router.push({ name: 'regist', query: { type: 'regist' } });
  }
};

const userNameWarning = ref(true);
const userIdWarning = ref(true);
const userPwWarning = ref(true);
const userEmailWarning = ref(true);
const checkPwWarning = ref(true);

// 이름 유효성 검사 정규식
const userNameRegex = /^[가-힣a-zA-Z0-9]{1,10}$/;

// 아이디 유효성 검사 정규식
const userIdRegex = /^[a-zA-Z0-9]{4,12}$/;

// 비밀번호 유효성 검사 정규식
const userPwRegex =
  /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Z\d!@#$%^&*]{8,15}$/;

// 이메일 주소 유효성 검사 정규식
const userEmailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

// 입력값의 유효성을 검사하는 함수
const validateInputs = () => {
  validateName();
  validateUserId();
  validateUserPw();
  validateUserEmail();
};

// 이름 유효성 검사 함수
const validateName = () => {
  if (!userNameRegex.test(user.value.userName)) {
    userNameWarning.value = false;
  } else {
    userNameWarning.value = true;
  }
};

// 아이디 유효성 검사 함수
const validateUserId = () => {
  if (!userIdRegex.test(user.value.userId)) {
    console.log('통과실패');
    userIdWarning.value = false;
  } else {
    userIdWarning.value = true;
  }
};

// 비밀번호 유효성 검사 함수
const validateUserPw = () => {
  if (!userPwRegex.test(user.value.userPw)) {
    userPwWarning.value = false;
  } else {
    userPwWarning.value = true;
  }
};

// 비밀번호 재입력 유효성 검사 함수
const validatePasswordCheck = () => {
  if (user.value.userPw !== user.value.CheckPw) {
    checkPwWarning.value = false;
  } else {
    checkPwWarning.value = true;
  }
};

// 이메일 주소 유효성 검사 함수
const validateUserEmail = () => {
  if (!userEmailRegex.test(user.value.userEmail)) {
    userEmailWarning.value = false;
  } else {
    userEmailWarning.value = true;
  }
};

watch(
  // name
  () => user.value.userName,
  () => {
    validateName();
  }
);
watch(
  // id
  () => user.value.userId,
  () => {
    validateUserId();
  }
);
watch(
  // email
  () => user.value.userEmail,
  () => {
    validateUserEmail();
  }
);
watch(
  // pw
  () => user.value.userPw,
  () => {
    validateUserPw();
  }
);
watch(
  // checkPw
  () => user.value.CheckPw,
  () => {
    validatePasswordCheck();
  }
);
</script>

<template>
  <form>
    <h1 class="formTitle" v-if="type !== 'userInfo'">
      {{ type === 'login' ? '로그인' : '회원가입' }}
    </h1>
    <span v-if="type === 'userInfo'" class="inputTitle">사용자 이름</span>
    <input
      v-if="type === 'regist' || type === 'userInfo'"
      type="text"
      placeholder="사용자 이름"
      v-model="user.userName"
      @change="changeInfo()"
    />
    <span
      v-if="type === 'regist'"
      :class="{ warning: true, none: userNameWarning }"
      class="warning"
      >이름은 한글, 영문자, 숫자포함 10자 이내입니다.</span
    >
    <input
      v-if="type === 'regist' || type === 'login'"
      :class="type"
      type="text"
      placeholder="아이디"
      v-model="user.userId"
    />
    <span v-if="type === 'userInfo'" class="inputTitle">이메일 주소</span>
    <span
      v-if="type === 'regist'"
      :class="{ warning: true, none: userIdWarning }"
      class="warning"
      >아이디는 영문자와 숫자를 포함한 8~12자여야 합니다.</span
    >
    <input
      v-if="type === 'regist' || type === 'userInfo'"
      type="text"
      placeholder="이메일 주소"
      v-model="user.userEmail"
    />
    <span
      v-if="type === 'regist'"
      :class="{ warning: true, none: userEmailWarning }"
      class="warning"
      >올바른 이메일 주소를 입력하세요.</span
    >
    <span v-if="type === 'userInfo'" class="inputTitle">비밀번호</span>
    <input
      :class="type"
      type="password"
      placeholder="비밀번호"
      v-model="user.userPw"
    />
    <span
      v-if="type === 'regist'"
      :class="{ warning: true, none: userPwWarning }"
      >비밀번호는 영문자, 숫자, 특수문자를 포함한 8~15자여야 합니다.</span
    >
    <span v-if="type === 'userInfo'" class="inputTitle">비밀번호 재입력</span>
    <input
      v-if="type === 'regist' || type === 'userInfo'"
      type="password"
      placeholder="비밀번호 재입력"
      v-model="user.CheckPw"
    />
    <span
      v-if="type === 'regist'"
      :class="{ warning: true, none: checkPwWarning }"
      class="warning"
      >비밀번호가 일치하지않습니다.</span
    >
    <div></div>
    <button v-if="type !== 'userInfo'" @click.prevent="onSubmit">
      {{ type === 'login' ? '로그인' : '회원가입' }}
    </button>
    <div v-if="type === 'login'" class="helpMsg" @click="movePage('find')">
      도움이 필요하신가요?
    </div>
    <a v-if="type === 'login'" class="registMsg" @click="movePage">
      스타플릭스 회원이 아닌가요? <b>지금 가입하세요.</b>
    </a>
  </form>
</template>

<style scoped>
.none {
  visibility: hidden;
}

form {
  /* border: 1px solid red; */
  margin: 0 auto;
  width: 80%;
  min-width: 300px;
  text-align: center;
}
.formTitle {
  color: #fff;
  width: 100%;
  font-weight: 900;
  text-align: left;
  margin-bottom: 30px;
}

input {
  display: inline-block;
  width: 70%;
  min-width: 200px;
  height: 50px;
  background-color: #333333;
  border: none;
  color: white;
  font-size: 20px;
  padding-left: 20px;
  border-radius: 15px;
  outline: none;
  margin-bottom: 5px;
}
input::placeholder {
  color: #d3d3d3;
}
.login {
  margin-bottom: 40px;
}

.warning {
  text-align: left;
  display: block;
  width: 70%;
  min-width: 200px;
  margin: 0 auto;
  color: #d30000;
  font-size: 12px;
  font-weight: bold;
  margin-bottom: 20px;
}

.inputTitle {
  text-align: left;
  display: block;
  width: 70%;
  min-width: 200px;
  margin: 0 auto;
  color: #fff;
  font-size: 15px;
  font-weight: bold;
  margin-bottom: 5px;
}

button {
  width: 70%;
  min-width: 200px;
  height: 50px;
  color: #fff;
  background-color: #d30000;
  border: none;
  border-radius: 15px;
  font-size: 20px;
  font-weight: bold;
}
.helpMsg {
  margin: 10px auto;
  width: 70%;
  min-width: 200px;
  text-align: right;
  color: #fff;
  cursor: pointer;
}
.registMsg {
  margin-top: 50px;
  color: #fff;
}
a {
  cursor: pointer;
}
</style>
