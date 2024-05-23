import { ref } from 'vue';
import { defineStore } from 'pinia';
import {
  deleteComment,
  listComment,
  modifyComment,
  writeComment,
} from '@/api/comment';

import { httpStatusCode } from '@/util/http-status';

export const useCommentStore = defineStore(
  'commentStore',
  () => {
    const commentList = ref([]);
    const reple = ref(null);

    const list = async (bno) => {
      await listComment(
        bno,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            commentList.value = resp.data;
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

    const write = async (param) => {
      await writeComment(
        param,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            list(param.bno);
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

    const update = async (comment) => {
      await modifyComment(
        comment,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            alert('수정되었습니다.');
            list(comment.bno);
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

    const del = async (comment) => {
      await deleteComment(
        comment.cno,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            alert('삭제되었습니다.');
            list(comment.bno);
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

    return { commentList, reple, list, write, update, del };
  },
  { persist: { storage: localStorage } }
);
