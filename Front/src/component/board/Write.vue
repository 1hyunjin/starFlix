<script setup>
import Btn from '@/component/common/Btn.vue';
import {
  getStorage,
  ref as storageRef,
  uploadBytesResumable,
  getDownloadURL,
} from 'firebase/storage';
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import Editor from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import { v4 as uuidv4 } from 'uuid';
import { useBoardStore } from '@/stores/boardStore';
import { useUserStore } from '@/stores/user';

const storage = getStorage(); // getStorage() 함수를 호출하여 Storage 인스턴스를 가져와 변수에 할당

const boardStore = useBoardStore();
const memberStore = useUserStore();
const { userInfo } = memberStore;
const { write } = boardStore;
const router = useRouter();
const route = useRoute();
const pg = route.query.pg;

const board = ref({
  title: '',
  writer: userInfo.userId,
  content: '',
});

let editorValid = null;
const editorRef = ref(null);
const uploading = ref(false);

const boardWrite = async () => {
  await write(board.value);
};

// 이미지를 최대 300px로 조절하는 함수
const resizeImage = async (blob) => {
  const canvas = document.createElement('canvas');
  const ctx = canvas.getContext('2d');

  const img = new Image();
  img.src = URL.createObjectURL(blob);

  await new Promise((resolve, reject) => {
    img.onload = () => resolve();
    img.onerror = reject;
  });

  const maxWidth = 500;

  let width = img.width;
  let height = img.height;

  if (width > maxWidth) {
    height *= maxWidth / width;
    width = maxWidth;
  }

  canvas.width = width;
  canvas.height = height;

  ctx.drawImage(img, 0, 0, width, height);

  return new Promise((resolve) => {
    canvas.toBlob((blob) => {
      resolve(blob);
    }, 'image/jpeg');
  });
};

onMounted(() => {
  editorValid = new Editor({
    el: editorRef.value,
    height: '500px',
    initialEditType: 'wysiwyg',
    hooks: {
      // 이미지 삽입 시 발생하는 이벤트
      addImageBlobHook: async (blob, callback) => {
        const uuid = uuidv4(); // UUID 생성
        const fileName = `${uuid}-${blob.name}`; // UUID와 파일 이름을 결합하여 유일한 파일 이름 생성

        // 이미지를 최대 500px로 조절
        const resizedBlob = await resizeImage(blob);

        const storageRefValue = storageRef(storage, `uploads/${fileName}`);
        const uploadTask = uploadBytesResumable(storageRefValue, resizedBlob);

        uploading.value = true;
        uploadTask.on(
          'state_changed',
          (snapshot) => {
            const progress =
              (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
            console.log(`Upload is ${progress}% done for ${fileName}`);
          },
          (error) => {
            console.error(`업로드 실패(${fileName}):`, error);
          },
          async () => {
            const downloadURL = await getDownloadURL(uploadTask.snapshot.ref);
            console.log(
              `${fileName} 파일이 성공적으로 업로드되었습니다.`,
              downloadURL
            );

            // Firebase에서 가져온 URL을 사용하여 이미지 삽입
            callback(downloadURL, blob.name);

            uploading.value = false;
          }
        );
      },
    },
  });
});

const addBoard = () => {
  board.value.content = editorValid.getHTML();
  if (board.value.title == '' || board.value.content == '<p><br></p>') {
    console.log(board.value);
    alert('제목 또는 내용을 입력해주세요');
  } else {
    console.log(board.value);
    boardWrite();
  }
};

const movePage = (val) => {
  router.push({ name: val, query: { pg } });
};
</script>

<template>
  <section class="container-fluid">
    <div class="boardArea">
      <div>
        <input
          type="text"
          class="bTitle"
          placeholder="제목을 입력하세요"
          v-model="board.title"
        />
        <input
          type="text"
          class="bWriter"
          placeholder="작성자"
          v-model="userInfo.userId"
          readonly
        />
      </div>
      <div class="bContent">
        <img
          class="loading"
          v-show="uploading"
          src="../../assets/img/loding.gif"
          alt="로딩"
        />
        <div class="textBox" ref="editorRef"></div>
      </div>
    </div>
    <div class="btnArea">
      <Btn :sty="'redBtn'" :text="'등록'" @click="addBoard" />
      <Btn :sty="'blackBtn'" :text="'이전'" @click="movePage('list')" />
    </div>
  </section>
</template>

<style scoped>
@media (max-width: 500px) {
  .btnArea {
    flex-direction: column;
  }
}

.content {
  min-height: 100vh;
  background-image: url('@/assets/img/boardImg.png');
  background-repeat: no-repeat;
  background-size: 100%;
  background-position: top;
  overflow: auto;
}
.loading {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}
.PageTitle {
  height: 350px;
}
.titleBox {
  width: 90%;
  padding-left: 20px;
  position: relative;
  display: flex;
  justify-content: space-between;
  top: 230px;
  left: 60px;
}
.title {
  color: white;
  font-weight: bold;
}
.text {
  color: white;
}
section {
  width: 100%;
  margin-bottom: 20px;
}

/*  */

.boardArea {
  width: 100%;
  text-align: center;
  margin: 0 auto 20px;
  height: 100%;
}
.boardArea + div {
  margin: 0 auto;
  width: 100%;
}

.bTitle {
  width: 65%;
  min-width: 220px;
  height: 45px;
  background-color: #333333;
  border-radius: 10px;
  border: none;
  padding-left: 10px;
  padding-right: 10px;
  margin-bottom: 20px;
  color: white;
}
.bWriter {
  width: 13%;
  min-width: 80px;
  height: 45px;
  margin-left: 2%;
  text-align: center;
  border-radius: 10px;
  border: none;
  padding-left: 10px;
  padding-right: 10px;
}
.bContent {
  position: relative;
  width: 80%;
  min-width: 300px;
  height: 500px;
  background-color: #333333;
  border-radius: 10px;
  border: none;
  padding: 10px;
  margin: 0 auto;
  color: white;
  text-align: start;
}
.btnArea {
  width: 80% !important;
}

.textBox {
  height: 480px !important;
  color: white !important;
}
</style>
