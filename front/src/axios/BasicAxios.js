import Axios from 'axios';

// axios 인스턴스를 생성합니다.
const axios = Axios.create({
  baseURL: process.env.REACT_APP_SERVER_IP, // 서버의 기본 URL을 지정
  withCredentials: true, // 쿠키를 포함해서 요청을 보냄
  headers: {
    'Content-Type': 'application/json',
  },
  // 다른 기본 설정 추가 가능
  //
});

export default axios;