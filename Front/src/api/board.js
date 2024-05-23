import { localAxios } from '@/util/http-commons';

const local = localAxios();

async function listBoard(param, idx, success, fail) {
  // 게시글 목록
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`/boards?page=${idx}`, param, idx).then(success).catch(fail);
}

async function searchBoard(param, idx, success, fail) {
  // 게시글 목록
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local
    .get(
      `/boards?type=${param.type}&keyword=${param.keyword}&page=${idx}`,
      param,
      idx
    )
    .then(success)
    .catch(fail);
}

async function readBoard(bno, success, fail) {
  // 게시글 읽기
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`/boards/${bno}`).then(success).catch(fail);
}
async function updateBoard(board, success, fail) {
  // 게시글 수정
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.put(`/boards/${board.bno}`, board).then(success).catch(fail);
}
async function deleteBoard(bno, success, fail) {
  // 게시글 삭제
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.delete(`/boards/${bno}`).then(success).catch(fail);
}
async function likeCheck(info, success, fail) {
  // 게시글 좋아요 여부 확인
  const params = {
    userId: info.userId,
  };
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local
    .get(`/boards/${info.bno}/like`, { params })
    .then(success)
    .catch(fail);
}
async function likeBoard(info, success, fail) {
  // 좋아요 증가
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local
    .post(`/boards/${info.bno}/like?userId=${info.userId}`)
    .then(success)
    .catch(fail);
}
async function unlikeBoard(info, success, fail) {
  // 좋아요 감소
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local
    .post(`/boards/${info.bno}/dislike?userId=${info.userId}`)
    .then(success)
    .catch(fail);
}
async function writeBoard(board, success, fail) {
  // 게시글 작성
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.post(`/boards`, board).then(success).catch(fail);
}

export {
  listBoard,
  readBoard,
  updateBoard,
  deleteBoard,
  likeCheck,
  likeBoard,
  unlikeBoard,
  writeBoard,
  searchBoard,
};
