import { localAxios } from '@/util/http-commons';

const local = localAxios();

async function listComment(bno, response, error) {
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.get(`/comments?bno=${bno}`).then(response).catch(error);
}

async function writeComment(param, response, error) {
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.post('/comments', param).then(response).catch(error);
}

async function modifyComment(param, response, error) {
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.put(`/comments/${param.cno}`, param).then(response).catch(error);
}

async function deleteComment(cno, response, error) {
  local.defaults.headers['Authorization'] =
    sessionStorage.getItem('accessToken');
  await local.delete(`comments/${cno}`).then(response).catch(error);
}

export { listComment, writeComment, modifyComment, deleteComment };
