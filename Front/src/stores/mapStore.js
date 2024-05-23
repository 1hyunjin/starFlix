import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import { defineStore } from 'pinia';
import {
  onPublic,
  onPrivate,
  listPlace1,
  listPlace2,
  addPlace,
  infoPlace,
  deletePlace,
  aroundPlace,
  aroundCamp,
  bestPlace,
  userAddInfo,
} from '@/api/map';

import { httpStatusCode } from '@/util/http-status';

export const useMapStore = defineStore(
  'mapStore',
  () => {
    const router = useRouter();

    const placeList = ref([]); //처음 사용자 접속 지역 명소들 객체
    const bestList = ref([]); //베스트 정보 객체
    const aroundList = ref([]); //주변정보 객체
    const searchList = ref([]); //검색정보 객체
    const userPlaceList = ref([]); //유저가 등록한 명소들 객체
    const place = ref(null); //상세정보
    const myLocation = ref({ latitude: '', longitude: '' }); //내 위치
    const myAddress = ref(''); // 내 주소
    const mapInfo = ref({ latitude: '', longitude: '' });

    //아래는 flag로 true값은 오로지 3개 중 하나만 가질 수 있음
    const isSearch = ref(false); //검색했을 때 리스트를 보여줄 것
    const isResult = ref(false); //검색리스트에서 상세보기하면 보여줄 것
    const isAround = ref(false); //주변정보 리스트를 보여줄 것
    const isResultDetail = ref(false);

    watch(
      () => aroundList.value,
      (nv, ov) => {
        console.log('값 변경 : ', nv + ' ' + ov);
      }
    );

    const searchPlace = async (param) => {
      await listPlace2(
        param,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            searchList.value = resp.data;
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

    const maplist = async (param) => {
      // 이거 파라미터 들어가야함
      if (param === '') {
        console.log('파라미터없음');
        await listPlace1(
          (resp) => {
            if (resp.status === httpStatusCode.OK) {
              placeList.value = resp.data;
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
      } else {
        console.log('검색');
        await listPlace2(
          param,
          (resp) => {
            if (resp.status === httpStatusCode.OK) {
              placeList.value = resp.data;
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
      }
    };

    const add = async (place) => {
      await addPlace(
        place,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            alert('명소가 등록되었습니다.');
            router.replace({ name: 'home' });
            console.log('성공');
          } else {
            alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
            console.log('실행 중 에러');
          }
        },
        (error) => {
          alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
          console.log('실패');
          console.log(error);
        }
      );
    };

    const info = async (idx) => {
      await infoPlace(
        idx,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            place.value = resp.data;
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
    const delPlace = async (idx, id) => {
      await deletePlace(
        idx,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            alert('삭제되었습니다.');
            // router.replace({ name: 'boardList' });
          } else {
            alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
            console.log('실행 중 에러');
          }
        },
        (error) => {
          alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
          console.log('실패');
          console.log(error);
        }
      );
      await addInfo(id);
    };

    const travel = async (idx, type) => {
      const param = {
        idx,
        type,
      };
      await aroundPlace(
        param,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            aroundList.value = resp.data;
            console.log(param.type + '성공');
          } else {
            alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
            console.log('실행 중 에러');
          }
        },
        (error) => {
          alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
          console.log('실패');
          console.log(error);
        }
      );
      for (let i = 0; i < aroundList.value.length; i++) {
        aroundList.value[i].infoWindow = {
          content: aroundList.value[i].title,
          visible: false,
        };
      }
    };

    const camp = async (idx) => {
      await aroundCamp(
        idx,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            aroundList.value = resp.data;
            console.log('캠프성공');
          } else {
            alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
            console.log('실행 중 에러');
          }
        },
        (error) => {
          alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
          console.log('실패');
          console.log(error);
        }
      );
      for (let i = 0; i < aroundList.value.length; i++) {
        aroundList.value[i].infoWindow = {
          content: aroundList.value[i].title,
          visible: false,
        };
      }
    };

    const best = async () => {
      await bestPlace(
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            bestList.value = resp.data;
          } else {
            alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
            console.log('실행 중 에러');
          }
        },
        (error) => {
          alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
          console.log('실패');
          console.log(error);
        }
      );
    };

    const isPublic = async (idx) => {
      await onPublic(
        idx,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
          } else {
            alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
            console.log('실행 중 에러');
          }
        },
        (error) => {
          alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
          console.log('실패');
          console.log(error);
        }
      );
    };
    const isPrivate = async (idx) => {
      await onPrivate(
        idx,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
          } else {
            alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
            console.log('실행 중 에러');
          }
        },
        (error) => {
          alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
          console.log('실패');
          console.log(error);
        }
      );
    };

    const addInfo = async (id) => {
      await userAddInfo(
        id,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            userPlaceList.value = resp.data;
          } else {
            alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
            console.log('실행 중 에러');
          }
        },
        (error) => {
          alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
          console.log('실패');
          console.log(error);
        }
      );
    };

    return {
      placeList,
      bestList,
      place,
      aroundList,
      searchList,
      myLocation,
      myAddress,
      mapInfo,
      userPlaceList,
      isSearch,
      isResult,
      isAround,
      isResultDetail,
      searchPlace,
      maplist,
      add,
      info,
      delPlace,
      travel,
      camp,
      best,
      isPublic,
      isPrivate,
      addInfo,
    };
  },
  { persist: { storage: localStorage } }
);
