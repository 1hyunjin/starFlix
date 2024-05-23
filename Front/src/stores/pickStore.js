import { ref } from 'vue';
import { defineStore } from 'pinia';
import { unPick, Pick, pickList } from '@/api/pick';
import { useRouter } from 'vue-router';

import { httpStatusCode } from '@/util/http-status';

const router = useRouter();
export const usePickStore = defineStore(
  'pickStore',
  () => {
    const Jjimlist = ref([]);

    const list = async (id) => {
      await pickList(
        id,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            Jjimlist.value = resp.data;
            console.log('성공');
          } else {
            console.log('실행 중 에러');
          }
        },
        (error) => {
          console.log('실패');
          console.log(error);
        }
      );
    };

    const addPick = async (idx, id) => {
      const param = {
        starPlace: idx,
        userId: id,
      };
      await Pick(
        param,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            list(id);
          } else {
            console.log('실행 중 에러');
          }
        },
        (error) => {
          console.log('실패');
          console.log(error);
        }
      );
    };

    const delPick = async (idx, id) => {
      const param = {
        starPlace: idx,
        userId: id,
      };
      console.log('넘어갈 데이터 : ' + param);
      await unPick(
        param,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
          } else {
            console.log('실행 중 에러');
          }
        },
        (error) => {
          console.log('실패');
          console.log(error);
        }
      );
      await list(id);
    };

    return { Jjimlist, list, addPick, delPick };
  },
  { persist: { storage: localStorage } }
);
