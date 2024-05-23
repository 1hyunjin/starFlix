import { localAxios } from '@/util/http-commons';

const local = localAxios();

async function userLogin(param, success, fail) {
  // 로그인
  await local.post(`/users/login`, param).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  // 회원정보 (강의에서 회원인증)
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`/users/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  // ac 재발급
  local.defaults.headers['refreshToken'] =
    sessionStorage.getItem('refreshToken');
  await local.post(`users/refresh`, user).then(success).catch(fail);
}

async function userlogout(id, success, fail) {
  // 로그아웃
  await local.get(`/users/logout?userId=${id}`).then(success).catch(fail);
}

async function userInsert(user, success, fail) {
  //회원가입
  await local.post(`/users`, user).then(success).catch(fail);
}

async function userUpdate(user, success, fail) {
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  //회원정보 수정
  await local.put(`/users/${user.userId}`, user).then(success).catch(fail);
}

async function userDelete(userid, success, fail) {
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  // 회원정보 삭제
  await local.delete(`/users/${userid}`).then(success).catch(fail);
}

async function userCheck(userid, success, fail) {
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  // 회원인지 체크
  await local.get(`/users?userId=${userid}`).then(success).catch(fail);
}
async function changePassword(id, pw, success, fail) {
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  // 비밀번호 발급
  await local.put(`/users?userId=${id}&userPw=${pw}`).then(success).catch(fail);
}

export {
  userLogin,
  findById,
  tokenRegeneration,
  userlogout,
  userInsert,
  userUpdate,
  userDelete,
  userCheck,
  changePassword,
};
