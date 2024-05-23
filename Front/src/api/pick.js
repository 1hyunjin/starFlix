import { localAxios } from '@/util/http-commons';

const local = localAxios();

async function unPick(param, success, fail) {
  // 명소 찜 취소
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local
    .post(`jjims/unJjim?starPlace=${param.starPlace}&userId=${param.userId}`)
    .then(success)
    .catch(fail);
}
async function Pick(param, success, fail) {
  // 명소 찜
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local
    .post(`jjims/jjim?starPlace=${param.starPlace}&userId=${param.userId}`)
    .then(success)
    .catch(fail);
}
async function pickList(id, success, fail) {
  // 명소 찜 리스트
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`jjims?userId=${id}`).then(success).catch(fail);
}

export { unPick, Pick, pickList };
