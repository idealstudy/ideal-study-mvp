// apiClient.js

import axios from "axios";

const apiClient = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL, // 기본 URL 설정
  timeout: 5000, // 요청 제한 시간 설정
  headers: { "Content-Type": "application/json" }, // 기본 헤더 설정
});

console.log(
  "요청주소 url (localhost 나오면 망함)",
  process.env.REACT_APP_API_BASE_URL
);

/**
 * 서버에 요청시 토큰이 있으면 헤더에 삽입하여 보냄
 */
apiClient.interceptors.request.use(
  (req) => {
    const token = localStorage.getItem("jwtToken"); // localStorage에서 토큰 가져오기
    if (token) {
      req.headers["Authorization"] = `${token}`; // 토큰을 헤더에 추가
    }

    // 디버깅
    console.log("Request Headers:", req.headers);
    return req;
  },
  (error) => {
    return Promise.reject(error);
  }
);

/**
 * 서버에서의 응답 헤더에 토큰이 포함되어있는지 검사
 */
apiClient.interceptors.response.use((res) => {
  console.log("Response Headers:", res.headers);

  return res;
});

export default apiClient;
