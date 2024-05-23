<template>
  <div class="testDiv">
    <div ref="editor"></div>
    <div class="btnArea">
      <Btn :sty="'redBtn'" :text="'등록'" @click="submit" />
      <Btn :sty="'blackBtn'" :text="'이전'" @click="movePage('list')" />
    </div>
  </div>
</template>

<script>
import Btn from '@/component/common/Btn.vue';
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';
import Quill from 'quill';

export default {
  components: {
    Btn,
  },
  setup() {
    const router = useRouter();
    const movePage = (val) => {
      console.log('rr');
      router.push({ name: val });
    };
    const editor = ref(null);
    let quill = null;

    onMounted(() => {
      quill = new Quill(editor.value, {
        theme: 'snow', // 테마 설정
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'], // <strong>, <em>, <u>, <s>
            ['blockquote', 'code-block'], // <blockquote>, <pre class="ql-syntax" spellcheck="false">
            [{ header: 1 }, { header: 2 }], // <h1>, <h2>
            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ size: ['small', false, 'large', 'huge'] }], //class 제어 - html로 되도록 확인
            [{ header: [1, 2, 3, 4, 5, 6, false] }], // <h1>, <h2>, <h3>, <h4>, <h5>, <h6>, normal
            [{ color: [] }, { background: [] }], //style="color: rgb(230, 0, 0);", style="background-color: rgb(230, 0, 0);"
            [{ align: [] }], // class
            ['link', 'image', 'video'],
          ],
        },
      });
    });

    const submit = () => {
      console.log(quill.root.innerHTML);
    };

    return {
      editor,
      submit,
      movePage,
    };
  },
};
</script>

<style>
/* @media (max-width: 500px) {
  .btnArea {
    flex-direction: column;
  }
} */
.ql-editor {
  color: white;
  background-color: #333333;
  /* height: 400px; */
  overflow-x: auto;
  overflow-y: auto;
}
.ql-toolbar {
  background-color: white;
}

.testDiv {
  width: 80%;
  height: 600px;
  padding-bottom: 200px;
  /* height: 400px; */
  margin: 0 auto;
}
.btnArea {
  width: 80%;
  margin: 0 auto 50px;
  display: flex;
  justify-content: end;
  /* margin-bottom: 50px; */
}
</style>
