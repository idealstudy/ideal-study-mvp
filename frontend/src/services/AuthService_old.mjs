import apiClient from "./apiClient.mjs";

/**
 * 로그인
 */
export const loginUser = async (username, password) => {
  console.log("로그인 API 시도:", username, password);
  try {
    const response = await apiClient.post("/auth/login", {
      username,
      password,
    });
    console.log("로그인 API 성공:", response);
    // 서버에서 받은 JWT 토큰과 유저 정보 반환
    const { token, user } = response.data; // 서버에서 반환된 데이터 구조에 맞게 수정 필요
    return { token, user }; // 토큰과 유저 정보를 객체로 반환
  } catch (error) {
    console.log("로그인 API 실패", error);

    return null;
  }
};

/**
 * 로그아웃
 */
export const logoutUser = async (username, password) => {
  console.log("로그아웃 API 시도:", username, password);
  try {
    const response = await apiClient.get("/auth/logout/{userId}");
    console.log("로그아웃 API 성공:", response);
    return response.data;
  } catch (err) {
    console.log("로그아웃 API 실패:", err);
  }
};
