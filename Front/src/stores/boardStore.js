import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { defineStore } from 'pinia';
import {
  deleteBoard,
  likeBoard,
  listBoard,
  likeCheck,
  readBoard,
  unlikeBoard,
  updateBoard,
  writeBoard,
  searchBoard,
} from '@/api/board';

import { httpStatusCode } from '@/util/http-status';

export const useBoardStore = defineStore(
  'boardStore',
  () => {
    const router = useRouter();

    const boardList = ref([]);
    const board = ref(null);
    const likeFlag = ref(false);
    const unlikeFlag = ref(true);

    const blist = async (param, idx) => {
      // 이거 파라미터 들어가야함
      if (param === '') {
        console.log('파라미터없음');
        await listBoard(
          param,
          idx,
          (resp) => {
            if (resp.status === httpStatusCode.OK) {
              boardList.value = resp.data;
              router.push({ name: 'boardList', query: { pg: idx } });
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
        await searchBoard(
          param,
          idx,
          (resp) => {
            if (resp.status === httpStatusCode.OK) {
              console.log('성공22');
              boardList.value = resp.data;
              router.push({
                name: 'boardList',
                query: { type: param.type, keyword: param.keyword, pg: idx },
              });
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

    const write = async (board) => {
      await writeBoard(
        board,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            alert('글이 등록되었습니다.');
            router.replace({ name: 'list', query: { pg: 1 } });
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
    const read = async (bno) => {
      await readBoard(
        bno,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            board.value = resp.data;
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
    const modify = async (board, pg) => {
      await updateBoard(
        board,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            alert('수정되었습니다.');
            router.replace({ name: 'boardList', query: { pg } });
          } else {
            console.log('실행 중 에러');
            alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
          }
        },
        (error) => {
          console.log('실패');
          console.log(error);
          alert('에러가 발생했습니다. 잠시 후 다시 시도해주세요.');
        }
      );
    };
    const delBoard = async (bno, pg) => {
      await deleteBoard(
        bno,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            alert('삭제되었습니다.');
            router.replace({ name: 'boardList', query: { pg } });
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
    const checkLike = async (idx, id) => {
      const Info = {
        bno: idx,
        userId: id,
      };
      await likeCheck(
        Info,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            if (resp.data == 1) {
              likeFlag.value = true;
              unlikeFlag.value = false;
            } else {
              likeFlag.value = false;
              unlikeFlag.value = true;
            }
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
    const like = async (idx, id) => {
      const Info = {
        bno: idx,
        userId: id,
      };
      await likeBoard(
        Info,
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
    };
    const unlike = async (idx, id) => {
      const Info = {
        bno: idx,
        userId: id,
      };
      await unlikeBoard(
        Info,
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
    };

    return {
      boardList,
      board,
      likeFlag,
      unlikeFlag,
      blist,
      write,
      read,
      modify,
      delBoard,
      checkLike,
      like,
      unlike,
    };
  },
  { persist: { storage: localStorage } }
);
