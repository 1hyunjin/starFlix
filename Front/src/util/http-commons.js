// import axios from 'axios';

// const { VITE_VUE_API_URL } = import.meta.env;

// function localAxios() {
//   const instance = axios.create({
//     baseURL: VITE_VUE_API_URL,
//     crossDomain: true,
//     // withCredentials: true,
//     // headers: {
//     //   'Content-Type': 'application/json;charset=utf-8',
//     // },
//   });

//   instance.defaults.headers.common['Authorization'] = '';
//   instance.defaults.headers.post['Content-Type'] = 'application/json';
//   instance.defaults.headers.put['Content-Type'] = 'application/json';

//   return instance;
// }

// export { localAxios };
import axios from 'axios';

const { VITE_VUE_API_URL } = import.meta.env;

function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    // withCredentials: true, // 인증 정보를 요청에 포함
    // headers: {
    //   'Content-Type': 'application/json;charset=utf-8',
    // },
  });

  instance.defaults.headers.common['Authorization'] = '';
  instance.defaults.headers.post['Content-Type'] = 'application/json';
  instance.defaults.headers.put['Content-Type'] = 'application/json';

  return instance;
}

export { localAxios };
