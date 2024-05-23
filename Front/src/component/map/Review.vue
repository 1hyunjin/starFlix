<script setup>
import Btn from '@/component/common/Btn.vue';
import { useRoute, useRouter } from 'vue-router';
import { storeToRefs } from 'pinia';
import { useMapStore } from '@/stores/mapStore';
import { useUserStore } from '@/stores/user';
import { useReviewStore } from '@/stores/review';

const userStore = useUserStore();
const reviewStore = useReviewStore();
const mapStore = useMapStore();
const { write } = reviewStore;
const { place } = mapStore;
const { userInfo } = storeToRefs(userStore);

// 여기부터 토스트
import { onMounted, ref, defineProps, defineEmits } from 'vue';
import Editor from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';

//파베
import {
  getStorage,
  ref as storageRef,
  uploadBytesResumable,
  getDownloadURL,
} from 'firebase/storage';
import { v4 as uuidv4 } from 'uuid';
const storage = getStorage();

const props = defineProps({
  modelValue: {
    type: String,
    required: false,
    default: '',
  },
});

const emit = defineEmits(['update:modelValue']);
const editorRef = ref(null);
let editorValid = null;
const testHtml = ref('');
const text = ref('');
const uploading = ref(false);

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

//마운트될때 Editor 생성
onMounted(() => {
  editorValid = new Editor({
    el: editorRef.value,
    height: '500px',
    //'wysiwyg', 'markdown' 택 1
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
    events: {
      change: () => emit('update:modelValue', editorValid.getMarkdown()),
    },
  });
});
const review = ref({
  idx: place.placeInfo.idx,
  title: '',
  writer: userInfo.value.userId,
  content: '',
});
const ReviewWrite = async () => {
  await write(review.value);
};

const addBoard = () => {
  review.value.content = editorValid.getHTML();
  if (review.value.title == '' || review.value.content == '<p><br></p>') {
    alert('제목 또는 내용을 입력해주세요');
  } else {
    ReviewWrite();
    router.push({ name: 'placeReview', query: { type: 'placeReview', idx } });
  }
};

// 여기까지 토스트

const route = useRoute();
const idx = route.query.idx;
const router = useRouter();
</script>

<template>
  <section class="mapContent">
    <h1 class="title">{{ place.placeInfo.title }}</h1>
    <h5 class="text">{{ place.placeInfo.addr }}</h5>
    <div class="textBox">
      <input
        class="rTitle"
        type="text"
        placeholder="제목을 입력하세요"
        v-model="review.title"
      />
      <div class="rContent">
        <img
          class="loading"
          v-show="uploading"
          src="../../assets/img/loding.gif"
          alt="로딩"
        />
        <div class="Editor" ref="editorRef" />
      </div>
    </div>
    <div class="btnArea">
      <Btn :sty="'redBtn'" :text="'등록'" @click="addBoard" />
    </div>
  </section>
</template>

<style scoped>
@media (max-width: 1000px) {
  .textBox {
    /* margin: 10px; */
    width: 100% !important;
    height: fit-content;
  }
  .rTitle {
    width: 100% !important;
  }
  .rContent {
    width: 100% !important;
  }
}
.loading {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}
.mapContent {
  width: 90%;
  height: 100%;
  margin: 20px auto 30px;
}

/* 아래는 리뷰작성 */
.title,
.text {
  color: white;
}
.title {
  font-weight: bold;
}
.textBox {
  text-align: center;
}
.rTitle {
  width: 90%;
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
  width: 90%;
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

.Editor {
  height: 500px !important;
  color: white !important;
}
</style>
