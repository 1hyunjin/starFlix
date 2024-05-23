<script setup>
import { ref } from 'vue';
defineProps({
  options: Array,
});

const emit = defineEmits(['onKeySelect']);
const key = ref('');
const onSelect = () => {
  emit('onKeySelect', key.value);
};
</script>

<template>
  <select v-model="key" @change="onSelect">
    <option
      v-for="o in options"
      :key="o.value"
      :value="o.value"
      :disabled="o.value === '' ? true : false"
    >
      {{ o.text }}
    </option>
  </select>
</template>

<style scoped>
select {
  text-align: center;
  width: 80px;
  height: 40px;
  border-radius: 10px;
}
</style>
