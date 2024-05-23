import { localAxios } from '@/util/http-commons';

const local = localAxios();

async function onPublic(idx, success, fail) {
  // 공개처리
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.put(`/star-places/${idx}/public`).then(success).catch(fail);
}
async function onPrivate(idx, success, fail) {
  // 비공개처리
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.put(`/star-places/${idx}/private`).then(success).catch(fail);
}
async function listPlace1(success, fail) {
  // 명소 검색(전체)
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`/star-places`).then(success).catch(fail);
}
async function listPlace2(param, success, fail) {
  // 명소 검색(조건 검색)
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local
    .get(`/star-places?type=${param.type}&keyword=${param.keyword}`)
    .then(success)
    .catch(fail);
}
async function addPlace(param, success, fail) {
  // 명소 등록
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.post(`/star-places`, param).then(success).catch(fail);
}
async function infoPlace(idx, success, fail) {
  // 명소 상세
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`/star-places/${idx}`).then(success).catch(fail);
}
async function deletePlace(idx, success, fail) {
  // 명소 삭제
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.delete(`/star-places/${idx}`).then(success).catch(fail);
}
async function aroundPlace(param, success, fail) {
  // 주변 검색(관광)
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local
    .get(`/star-places/${param.idx}/travels?type=${param.type}`)
    .then(success)
    .catch(fail);
}
async function aroundCamp(idx, success, fail) {
  // 주변 검색(캠핑)
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`/star-places/${idx}/camping`).then(success).catch(fail);
}

async function bestPlace(success, fail) {
  // 주변 검색(캠핑)
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`/star-places/best`).then(success).catch(fail);
}

async function userAddInfo(id, success, fail) {
  // 주변 검색(캠핑)
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`/star-places/userId?userId=${id}`).then(success).catch(fail);
}

export {
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
};
