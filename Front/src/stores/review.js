import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { defineStore } from 'pinia';
import {
  listReviewPlace,
  listReviewUser,
  writeReview,
  modifyReview,
  deleteReview,
  readReview,
} from '@/api/review';

import { httpStatusCode } from '@/util/http-status';

export const useReviewStore = defineStore(
  'reviewStore',
  () => {
    const router = useRouter();

    const reviewList = ref([]);
    const review = ref(null);

    const placeReview = async (idx) => {
      await listReviewPlace(
        idx,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            reviewList.value = resp.data;
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

    const userReview = async (id) => {
      await listReviewUser(
        id,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            reviewList.value = resp.data;
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

    const write = async (board) => {
      await writeReview(
        board,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            alert('리뷰가 등록되었습니다.');
            // router.replace({ name: 'boardList' });
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
    const read = async (rno) => {
      await readReview(
        rno,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            review.value = resp.data;
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
    const modify = async (param) => {
      await modifyReview(
        param,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            alert('수정되었습니다.');
            // router.replace({ name: 'boardList' });
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
    const delReview = async (rno) => {
      await deleteReview(
        rno,
        (resp) => {
          if (resp.status === httpStatusCode.OK) {
            console.log('성공');
            alert('삭제되었습니다.');
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
      reviewList,
      review,
      placeReview,
      userReview,
      write,
      read,
      modify,
      delReview,
    };
  },
  { persist: { storage: localStorage } }
);
