import { localAxios } from '@/util/http-commons';

const local = localAxios();

async function listReviewPlace(idx, response, error) {
  //리뷰 검색 (명소)
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`/reviews?starPlace=${idx}`).then(response).catch(error);
}
async function listReviewUser(id, response, error) {
  //리뷰 검색 (유저아이디)
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`/reviews?userId=${id}`).then(response).catch(error);
}

async function writeReview(param, response, error) {
  //리뷰 등록
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.post('/reviews', param).then(response).catch(error);
}

async function modifyReview(param, response, error) {
  //리뷰 수정
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.put(`/reviews/${param.rno}`, param).then(response).catch(error);
}

async function deleteReview(rno, response, error) {
  //리뷰 삭제
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.delete(`/reviews/${rno}`).then(response).catch(error);
}

async function readReview(rno, response, error) {
  //리뷰 삭제
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`/reviews/${rno}`).then(response).catch(error);
}

export {
  listReviewPlace,
  listReviewUser,
  writeReview,
  modifyReview,
  deleteReview,
  readReview,
};
