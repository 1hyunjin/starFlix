<script setup>
import PickItem from '@/component/pick/PickItem.vue';
import { storeToRefs } from 'pinia';
import { useUserStore } from '@/stores/user';
import { useMapStore } from '@/stores/mapStore';
import { onMounted } from 'vue';

const userStore = useUserStore();
const mapStore = useMapStore();

const { userInfo } = storeToRefs(userStore);
const { userPlaceList } = storeToRefs(mapStore);
const { addInfo } = mapStore;

onMounted(async () => {
  await addInfo(userInfo.value.userId);
});
</script>

<template>
  <section class="container-fluid">
    <div v-if="userPlaceList.length == 0" class="text">
      등록한 명소가 존재하지 않습니다.
    </div>
    <div v-else class="conetntBox row">
      <PickItem
        v-for="u in userPlaceList"
        class="col-12 col-sm-6 col-md-3"
        :data="u"
        :type="'addPlace'"
      />
    </div>
  </section>
</template>

<style scoped>
.conetntBox {
  padding-left: 30px;
  padding-right: 30px;
  margin-bottom: 40px;
}
.text {
  color: white;
}
</style>
