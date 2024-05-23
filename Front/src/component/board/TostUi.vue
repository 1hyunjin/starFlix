<script setup>
import { onMounted, ref, defineProps, defineEmits } from 'vue';
import Editor from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';

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

//마운트될때 Editor 생성
onMounted(() => {
  editorValid = new Editor({
    el: editorRef.value,
    height: '500px',
    //'wysiwyg', 'markdown' 택 1
    initialEditType: 'wysiwyg',
    events: {
      change: () => emit('update:modelValue', editorValid.getMarkdown()),
    },
  });
});

//작성한 내용 불러와서 html 적용
const testValid = () => {
  if (editorValid !== null) {
    testHtml.value = editorValid.getHTML();
  }
};
</script>

<template>
  <div ref="editorRef" />
  <button @click="testValid">버튼</button>
  <div v-html="testHtml"></div>
</template>

<style scoped></style>
