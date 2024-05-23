<script setup>
import { useRoute, useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/user';
import { onMounted, ref, watch } from 'vue';
import emailjs from 'emailjs-com';

const { VITE_EMAILJS_PUBLICKEY, VITE_EMAILJS_SERVICE, VITE_EMAILJS_TEMPLATE } =
  import.meta.env;

const router = useRouter();
const route = useRoute();
const type = ref(route.query.type);

const memberStore = useUserStore();

const { userEmail } = storeToRefs(memberStore);
const { check, passwordFind } = memberStore;

const userId = ref('');

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

const sendEmail = (email, pw) => {
  emailjs
    .send(
      VITE_EMAILJS_SERVICE,
      VITE_EMAILJS_TEMPLATE,
      {
        message: pw,
        user_email: email,
      },
      VITE_EMAILJS_PUBLICKEY
    )
    .then((response) => {
      console.log('이메일이 성공적으로 보내졌습니다!', response);
      // 이메일이 성공적으로 보내졌음을 사용자에게 알림
    })
    .catch((error) => {
      console.error('이메일 보내기에 실패했습니다:', error);
      // 이메일 보내기에 실패했음을 사용자에게 알림
    });
};

const onSubmit = async () => {
  generateRandomPassword();
  console.log(password.value);
  await check(userId.value);
  await passwordFind(userId.value, password.value);
  sendEmail(userEmail.value, password.value);
  //여기서 처리해야함
};

const password = ref('');
const generateRandomPassword = () => {
  let pw = '';
  const regex =
    /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[a-zA-Z\d!@#$%^&*]{8,15}$/;

  const possibleCharacters =
    'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@$%^&*';

  while (!regex.test(pw)) {
    pw = '';
    for (let i = 0; i < 15; i++) {
      const randomIndex = Math.floor(Math.random() * possibleCharacters.length);
      pw += possibleCharacters.charAt(randomIndex);
    }
  }
  password.value = pw;
};
</script>

<template>
  <form>
    <h1 class="formTitle">임시 비밀번호 발급</h1>
    <input :class="type" type="text" placeholder="아이디" v-model="userId" />
    <input type="hidden" name="contact_number" value="697483" />
    <div
      :class="{
        text: true,
        none: password,
      }"
    >
      가입시 입력된 이메일정보로 임시비밀번호를 발급해드렸습니다.
    </div>
    <div></div>
    <button @click.prevent="onSubmit">비밀번호 재발급</button>
  </form>
</template>

<style scoped>
form {
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
  margin-bottom: 40px;
}
input::placeholder {
  color: #d3d3d3;
}

.text {
  color: white;
  font-size: 20px;
  font-weight: bold;
  /* display: none; */
  margin-bottom: 20px;
}
.none {
  display: block;
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
}
</style>
