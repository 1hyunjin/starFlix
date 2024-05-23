<script setup>
import Btn from '@/component/common/Btn.vue';
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();

const fileName = ref('');
const addAttachment = () => {
  // 파일 업로드 input을 트리거합니다.
  const fileInput = document.getElementById('fileInput');
  fileInput.click();
};

const handleFileChange = (event) => {
  // 파일이 선택되었을 때의 처리 로직을 여기에 작성합니다.
  const files = event.target.files;
  console.log(files); // 선택된 파일 확인
  fileName.value = files[0].name;
};
</script>

<template>
  <div class="menuBox">
    <div class="textBox">
      <input class="rTitle" type="text" placeholder="제목을 입력하세요" />
      <textarea class="rContent" placeholder="내용을 입력해주세요."></textarea>
    </div>
    <div class="form-check form-switch checkBox">
      <input
        class="form-check-input ckeck"
        type="checkbox"
        role="switch"
        id="flexSwitchCheckDefault"
      />
      <label class="form-check-label checkText" for="flexSwitchCheckDefault"
        >공개여부</label
      >
    </div>
    <div class="btnArea">
      <!-- 파일 업로드 input 숨김 처리 -->
      <input
        type="file"
        id="fileInput"
        style="display: none"
        @change="handleFileChange"
      />
      <div>{{ fileName }}</div>
      <Btn :sty="'redBtn'" :text="'첨부파일'" @click="addAttachment" />
    </div>
  </div>
</template>

<style scoped>
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
