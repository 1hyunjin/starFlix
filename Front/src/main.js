import './assets/main.css';

import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

import App from './App.vue';
import router from './router';

import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import '@/util/firebase';

import { useKakao } from 'vue3-kakao-maps';

import { quillEditor } from 'vue3-quill';

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPluginPersistedstate);
app.use(pinia);
app.use(router);

app.use(quillEditor);

useKakao('dac3f257356c454a8dc49e74570e3e2f', [
  'clusterer',
  'services',
  'drawing',
]);

app.mount('#app');
